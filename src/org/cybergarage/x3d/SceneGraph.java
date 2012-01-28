/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SceneGraph.java
*
******************************************************************/

package org.cybergarage.x3d;

import java.io.*;
import java.util.Vector;
import java.util.Date;
import java.util.NoSuchElementException;
import java.net.*;

import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.route.*;
import org.cybergarage.x3d.share.*;
import org.cybergarage.x3d.share.object.*;
import org.cybergarage.x3d.parser.*;
import org.cybergarage.x3d.parser.x3d.*;
import org.cybergarage.x3d.parser.vrml97.*;
import org.cybergarage.x3d.parser.wavefront.*;
import org.cybergarage.x3d.parser.autodesk.*;
import org.cybergarage.x3d.parser.sense8.*;
import org.cybergarage.x3d.parser.threedsystems.*;

public class SceneGraph extends Scene implements Constants, Runnable {

	static public final int	USE_PREPROCESSOR			= 0x01;
	static public final int	NORMAL_GENERATION			= 0x02;
	static public final int	TEXTURE_GENERATION			= 0x04;
  
	public void initializeMember() {
		setHeaderFlag(false);
		setSceneGraph(this);
		setOption(0);
		setViewpointStartPosition(0.0f, 0.0f, 0.0f);
		setViewpointStartOrientation(0.0f, 0.0f, 1.0f, 0.0f);
	}

	public SceneGraph() {
		initializeMember();
	}

	public SceneGraph(int option) {
		initializeMember();
		setOption(option);
	}
	
	public SceneGraph(String filename) {
		initializeMember();
		load(filename);
	}

	public SceneGraph(File file) {
		initializeMember();
		load(file);
	}

	////////////////////////////////////////////////
	//	initialize
	////////////////////////////////////////////////

	public void initialize() {
	
		stop();

		Debug.message("Node::initialize nodes .....");
		
		for (Node node = getNodes(); node != null; node = node.nextTraversal()) {
			node.setSceneGraph(this);
			node.initialize();
		}
		
		Debug.message("Node::initialize nodes ..... done");
		
		setBackgroundNode(findBackgroundNode(), true);
		setFogNode(findFogNode(), true);
		setNavigationInfoNode(findNavigationInfoNode(), true);
		setViewpointNode(findViewpointNode(), true);
		
		updateBoundingBox();
		
		Debug.message("Node::initialize object .....");
		
		initializeObject();
		
		Debug.message("Node::initialize object ..... done");
		
		for (Node node = getNodes(); node != null; node = node.nextTraversal()) {
			if (node.isRunnable() == true) {
				if (node.getRunnableType() == Node.RUNNABLE_TYPE_ALWAYS) 
					node.start();
			}
		}
		
		start();

	}

	public void uninitialize() {
		stop();

		for (Node node = getNodes(); node != null; node = node.nextTraversal()) {
			if (node.isRunnable() == true) {
				if (node.getRunnableType() == Node.RUNNABLE_TYPE_ALWAYS) 
					node.stop();
			}
		}
		
		for (Node node = getNodes(); node != null; node = node.nextTraversal())
			node.uninitialize();
	}

	////////////////////////////////////////////////
	//	loadInlineNodeURLs
	////////////////////////////////////////////////

	public void loadInlineNodeURLs() {
	
		for (InlineNode node=findInlineNode(); node != null; node = (InlineNode)node.nextTraversalSameType())
			node.loadURL();
			
	}

	////////////////////////////////////////////////
	//	update
	////////////////////////////////////////////////

	public void updateSimulation() {
		for (Node node = getNodes(); node != null; node = node.nextTraversal()) {
			if (node.isTimeSensorNode() == true)
				node.update();
		}
		updateObject();
	}

	public void updateRoute(Node eventOutNode, Field eventOutField) {
		//Debug.message("SceneGraph::updateRoute = " + eventOutNode + ", " + eventOutField);
		if (eventOutNode == null || eventOutField == null)
			return;
		for (Route route = getRoutes(); route != null; route = route.next()) {
			if (route.getEventOutNode() == eventOutNode && route.getEventOutField() == eventOutField) {
				Node eventInNode = route.getEventInNode();
				Field eventInField = route.getEventInField();
				if (eventInNode != null && eventInField != null) {
					//Debug.message("\t" + route);
					route.update();
					eventInNode.update();
					updateRoute(eventInNode, eventInField);
				}
			}
		}
	}

	////////////////////////////////////////////////
	//	Thread
	////////////////////////////////////////////////

	private boolean mIsSimulationRunning = false;
	
	public void startSimulation() {
		if (isSimulationRunning() == false) {
			for (Node node = getNodes(); node != null; node = node.nextTraversal()) {
				if (node.isRunnable() == true) {
					if (node.getRunnableType() != Node.RUNNABLE_TYPE_ALWAYS)
						node.start();
				}
			}
			setSimulationRunningFlag(true);
		}
	}

	public void stopSimulation() {
		if (isSimulationRunning() == true) {
			for (Node node = getNodes(); node != null; node = node.nextTraversal()) {
				if (node.isRunnable() == true) {
					if (node.getRunnableType() != Node.RUNNABLE_TYPE_ALWAYS)
						node.stop();
				}
			}
			setSimulationRunningFlag(false);
		}
	}
	
	public boolean isSimulationRunning() {
		return mIsSimulationRunning;
	}

	public void setSimulationRunningFlag(boolean flag) {
		mIsSimulationRunning = flag;
	}


	///////////////////////////////////////////////
	//	Load
	///////////////////////////////////////////////
   
   public URL toURL(File file) throws MalformedURLException {
		String path = file.getAbsolutePath();
		if (File.separatorChar != '/') {
		    path = path.replace(File.separatorChar, '/');
		}
		if (!path.startsWith("/")) {
		    path = "/" + path;
		}
		if (!path.endsWith("/") && file.isDirectory()) {
		    path = path + "/";
		}
		return new URL("file", "", path);
	}
			
	///////////////////////////////////////////////
	//	moveSceneNodes/Routes
	///////////////////////////////////////////////

	public void moveSceneNodes(Scene scene) 
	{
		Node node = scene.getNodes();
		if (node.isXMLNode() == true) {
			Node sceneNode = scene.findSceneNode();
			if (sceneNode != null)
				node = sceneNode.getChildNodes();
			else
				node = null;
		}

		while (node != null) {
			Node nextNode = node.next();
			moveNode(node);
			node.setSceneGraph(this);
			node = nextNode;
		}
	}

	public void moveSceneRoutes(Scene scene) 
	{
		Route route = scene.getRoutes();
		while (route != null) {
			Route nextRoute = route.next();
			route.remove();
			addRoute(route);
			route = nextRoute;
		}
	}

	public void moveScene(Scene scene) 
	{
		moveSceneNodes(scene);
		moveSceneRoutes(scene);
	}

	///////////////////////////////////////////////
	//	Load (VRML97)
	///////////////////////////////////////////////

	public boolean addVRML97(Reader reader) {
		stop();

		VRML97Parser parser = new VRML97Parser(reader);
		ParserResult parserResult = getParserResultObject();
		parser.setResult(parserResult);
		
		if (parser.parse(reader) == false) {
			start();
			return false;
		}

		moveScene(parser);
		initialize();

		// Save Start Viewpoint Position / Orientation
		saveViewpointStartPositionAndOrientation();
		
		start();
		return true;
	}

	public boolean addVRML97(InputStream inputStream) {
		return addVRML97(new InputStreamReader(inputStream));
	}
	
	public boolean addVRML97(URL url) {

		setBaseURL(url.toString());
		
		Debug.message("SceneGraph::add = Loading a url file (" + url + ") ..... ");
		
		try {
			InputStream	inputStream	= url.openStream();
			addVRML97(inputStream);
		}
		catch (IOException ioe) {
			Debug.warning("Loading Error (IOException) = " + url);
			System.out.println(ioe.getMessage());
		} 
		
		return isLoadingOK();
	}

	public boolean addVRML97(File file) {

		try {
			setBaseURL(toURL(file).toString());
		}
		catch (MalformedURLException mue) {}

		VRML97Preprocessor	prepro		= null;
		
		try {
			File						inputFile	= null;
			InputStream				inputStream	= null;
			
			boolean isPreprocessorOn = getOption(USE_PREPROCESSOR);
			Debug.message("\tPreprocessor = " + isPreprocessorOn);
			if (isPreprocessorOn == true) {
				prepro = new VRML97Preprocessor(file);
				inputFile = new File(prepro.getTempFilename());
				inputStream = new FileInputStream(inputFile);
			}
			else {
				inputFile = file;
				inputStream = new FileInputStream(file);
			}

			//addVRML97(inputFile.toURL());
			addVRML97(inputStream);
		}
		catch (MalformedURLException mue) {
			Debug.warning("Loading Error (MalformedURLException) = " + file);
			System.out.println(mue.getMessage());
		}
		catch (IOException ioe) {
			Debug.warning("Loading Error (IOException) = " + file);
			System.out.println(ioe.getMessage());
		}
		finally {
			if (getOption(USE_PREPROCESSOR)) {
				/*
				if (prepro != null) {
					try {
						prepro.delete();
					}
					catch (IOException ioe) {
						Debug.warning("Loading Error (IOException) = " + file);
						System.out.println(ioe.getMessage());
					}
				}
				*/
			}
		}
		
		return isLoadingOK();
	}

	///////////////////////////////////////////////
	//	Load (X3D)
	///////////////////////////////////////////////

	public boolean addX3D(Reader reader) {
		stop();

		X3DParser parser = new X3DParser();
		ParserResult parserResult = getParserResultObject();
		parser.setResult(parserResult);
		
		if (parser.parse(reader) == false) {
			start();
			return false;
		}

		moveScene(parser);
		initialize();

		// Save Start Viewpoint Position / Orientation
		saveViewpointStartPositionAndOrientation();
		
		start();
		return true;
	}

	public boolean addX3D(InputStream inputStream) {
		return addX3D(new InputStreamReader(inputStream));
	}
	
	public boolean addX3D(URL url) {

		setBaseURL(url.toString());
		
		Debug.message("SceneGraph::add = Loading a url file (" + url + ") ..... ");
		
		try {
			InputStream	inputStream	= url.openStream();
			addX3D(inputStream);
		}
		catch (IOException ioe) {
			Debug.warning("Loading Error (IOException) = " + url);
			System.out.println(ioe.getMessage());
		} 
		
		return isLoadingOK();
	}

	public boolean addX3D(File file) {

		try {
			setBaseURL(toURL(file).toString());
		}
		catch (MalformedURLException mue) {}

		try {
			InputStream	inputStream	= new FileInputStream(file);
			addX3D(inputStream);
		}
		catch (IOException ioe) {
			Debug.warning("Loading Error (IOException) = " + file);
			System.out.println(ioe.getMessage());
		} 
		
		return isLoadingOK();
	}

	///////////////////////////////////////////////
	//	Load (OBJ)
	///////////////////////////////////////////////

	public boolean addOBJ(Reader reader) {
		stop();
		
		try {
			setLoadingResult(false);
			setParserErrorMessage("");

			Debug.message("\tReader = " + reader);
			
			Debug.message("\tStart parsering .....");
			
			OBJParser objParser = new OBJParser(reader);
			objParser.initialize();
			objParser.Input();
			
			ShapeNode shapeNode = objParser.getShapeNode();
			if (shapeNode != null) 
				addNode(shapeNode);
							
			initialize();

			reader.close();
		
			setLoadingResult(true);
		}
		catch (org.cybergarage.x3d.parser.wavefront.ParseException e) {
			Debug.warning("Loading Error (ParseException) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		}
		catch (org.cybergarage.x3d.parser.wavefront.TokenMgrError e) {
			Debug.warning("Loading Error (TokenMgrError) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		}
		catch (IOException e) {
			Debug.warning("Loading Error (IOException) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		} 
		catch (Exception e) {
			Debug.warning("Loading Error (Exception) = " + reader);
			e.printStackTrace();
			setParserErrorMessage(e.getMessage());
		} 
		finally {
			if (getLoadingResult() == true)
				Debug.message("\tLoading is OK !!");
			else
				Debug.message("\tLoading is failed !!");
			start();
			return isLoadingOK();
		}
	}

	public boolean addOBJ(InputStream inputStream) {
		return addOBJ(new InputStreamReader(inputStream));
	}
	
	public boolean addOBJ(URL url) {

		Debug.message("SceneGraph::add = Loading a url file (" + url + ") ..... ");
		
		try {
			InputStream	inputStream	= url.openStream();
			addOBJ(inputStream);
		}
		catch (IOException ioe) {
			Debug.warning("Loading Error (IOException) = " + url);
			System.out.println(ioe.getMessage());
		} 
		
		return isLoadingOK();
	}

	public boolean addOBJ(File file) {

		try {
			URL urlFile = toURL(file);
			addOBJ(urlFile);
		}
		catch (MalformedURLException mue) {
			Debug.warning("Loading Error (MalformedURLException) = " + file);
			System.out.println(mue.getMessage());
		}
		
		return isLoadingOK();
	}

	///////////////////////////////////////////////
	//	Load (NFF)
	///////////////////////////////////////////////

	public boolean addNFF(Reader reader) {
		stop();
		
		try {
			setLoadingResult(false);
			setParserErrorMessage("");

			Debug.message("\tReader = " + reader);
			
			Debug.message("\tStart parsering .....");
			
			NFFParser nffParser = new NFFParser(reader);
			nffParser.initialize();
			nffParser.Input();
			
			ShapeNode shapeNode = nffParser.getShapeNode();
			if (shapeNode != null) {
				TransformNode transNode = new TransformNode();
				transNode.setRotation(1, 0, 0, (float)Math.PI);
				transNode.addChildNode(shapeNode);
				addNode(transNode);
			}
							
			initialize();

			reader.close();
		
			setLoadingResult(true);
		}
		catch (org.cybergarage.x3d.parser.sense8.ParseException e) {
			Debug.warning("Loading Error (ParseException) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		}
		catch (org.cybergarage.x3d.parser.sense8.TokenMgrError e) {
			Debug.warning("Loading Error (TokenMgrError) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		}
		catch (IOException e) {
			Debug.warning("Loading Error (IOException) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		} 
		catch (Exception e) {
			Debug.warning("Loading Error (Exception) = " + reader);
			e.printStackTrace();
			setParserErrorMessage(e.getMessage());
		} 
		finally {
			if (getLoadingResult() == true)
				Debug.message("\tLoading is OK !!");
			else
				Debug.message("\tLoading is failed !!");
			start();
			return isLoadingOK();
		}
	}

	public boolean addNFF(InputStream inputStream) {
		return addNFF(new InputStreamReader(inputStream));
	}
	
	public boolean addNFF(URL url) {

		Debug.message("SceneGraph::add = Loading a url file (" + url + ") ..... ");
		
		try {
			InputStream	inputStream	= url.openStream();
			addNFF(inputStream);
		}
		catch (IOException ioe) {
			Debug.warning("Loading Error (IOException) = " + url);
			System.out.println(ioe.getMessage());
		} 
		
		return isLoadingOK();
	}

	public boolean addNFF(File file) {

		try {
			URL urlFile = toURL(file);
			addNFF(urlFile);
		}
		catch (MalformedURLException mue) {
			Debug.warning("Loading Error (MalformedURLException) = " + file);
			System.out.println(mue.getMessage());
		}
		
		return isLoadingOK();
	}


	///////////////////////////////////////////////
	//	Load (3DS)
	///////////////////////////////////////////////

	public boolean add3DS(InputStream inputStream) {
		stop();
		
		Debug.message("\tReader = " + inputStream);
		Debug.message("\tStart parsering .....");
		
		setParserErrorMessage("");
		
		Parser3DS parser3DS = new Parser3DS();
		boolean isLodingOK = parser3DS.load(inputStream);
		setLoadingResult(isLodingOK);
		
		if (isLodingOK) {
			GroupNode gnode = parser3DS.getRootGroupNode();
			addNode(gnode);
		}
		
		try {	
			inputStream.close();
		}
		catch (IOException ioe) {}
			
		initialize();
		
		start();
		
		return isLodingOK;
	}
	
	public boolean add3DS(URL url) {

		Debug.message("SceneGraph::add = Loading a url file (" + url + ") ..... ");
		
		try {
			InputStream	inputStream	= url.openStream();
			add3DS(inputStream);
		}
		catch (IOException ioe) {
			Debug.warning("Loading Error (IOException) = " + url);
			System.out.println(ioe.getMessage());
		} 
		
		return isLoadingOK();
	}

	public boolean add3DS(File file) {

		try {
			URL urlFile = toURL(file);
			add3DS(urlFile);
		}
		catch (MalformedURLException mue) {
			Debug.warning("Loading Error (MalformedURLException) = " + file);
			System.out.println(mue.getMessage());
		}
		
		return isLoadingOK();
	}

	///////////////////////////////////////////////
	//	Load (DXF)
	///////////////////////////////////////////////

	public boolean addDXF(Reader reader) {
		stop();
		
		try {
			setLoadingResult(false);
			setParserErrorMessage("");

			Debug.message("\tReader = " + reader);
			
			Debug.message("\tStart parsering .....");
			
			ParserDXF dxfParser = new ParserDXF();
			dxfParser.initialize();
			dxfParser.input(reader);
			reader.close();

			setLoadingResult(true);
			
			ShapeNode shapeNode = dxfParser.getShapeNodes();
			while (shapeNode != null) {
				TransformNode transNode = new TransformNode();
				transNode.addChildNode(shapeNode);
				addNode(transNode);
				shapeNode = dxfParser.getShapeNodes();
			}
			
			initialize();
		}
		catch (ParserDXFException e) {
			Debug.warning("Loading Error (IOException) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		} 
		catch (IOException e) {
			Debug.warning("Loading Error (IOException) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		} 
		catch (Exception e) {
			Debug.warning("Loading Error (Exception) = " + reader);
			e.printStackTrace();
			setParserErrorMessage(e.getMessage());
		} 
		finally {
			if (getLoadingResult() == true)
				Debug.message("\tLoading is OK !!");
			else
				Debug.message("\tLoading is failed !!");
			start();
			return isLoadingOK();
		}
	}

	public boolean addDXF(InputStream inputStream) {
		return addDXF(new InputStreamReader(inputStream));
	}
	
	public boolean addDXF(URL url) {

		Debug.message("SceneGraph::add = Loading a url file (" + url + ") ..... ");
		
		try {
			InputStream	inputStream	= url.openStream();
			addDXF(inputStream);
		}
		catch (IOException ioe) {
			Debug.warning("Loading Error (IOException) = " + url);
			System.out.println(ioe.getMessage());
		} 
		
		return isLoadingOK();
	}

	public boolean addDXF(File file) {

		try {
			URL urlFile = toURL(file);
			addDXF(urlFile);
		}
		catch (MalformedURLException mue) {
			Debug.warning("Loading Error (MalformedURLException) = " + file);
			System.out.println(mue.getMessage());
		}
		
		return isLoadingOK();
	}

	///////////////////////////////////////////////
	//	Load (STLAscii)
	///////////////////////////////////////////////

	public boolean addSTLAscii(Reader reader) {
		stop();
		
		try {
			setLoadingResult(false);
			setParserErrorMessage("");

			Debug.message("\tReader = " + reader);
			
			Debug.message("\tStart parsering .....");
			
			STLAsciiParser stlAsciiParser = new STLAsciiParser(reader);
			stlAsciiParser.initialize();
			stlAsciiParser.Input();
			reader.close();
			setLoadingResult(true);
			
			ShapeNode shapeNode = stlAsciiParser.getShapeNodes();
			while (shapeNode != null) {
				TransformNode transNode = new TransformNode();
				transNode.addChildNode(shapeNode);
				addNode(transNode);
				shapeNode = stlAsciiParser.getShapeNodes();
			}
							
			initialize();
		}
		catch (org.cybergarage.x3d.parser.threedsystems.ParseException e) {
			Debug.warning("Loading Error (ParseException) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		}
		catch (org.cybergarage.x3d.parser.threedsystems.TokenMgrError e) {
			Debug.warning("Loading Error (TokenMgrError) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		}
		catch (IOException e) {
			Debug.warning("Loading Error (IOException) = " + reader);
			System.out.println(e.getMessage());
			setParserErrorMessage(e.getMessage());
		} 
		catch (Exception e) {
			Debug.warning("Loading Error (Exception) = " + reader);
			e.printStackTrace();
			setParserErrorMessage(e.getMessage());
		} 
		finally {
			if (getLoadingResult() == true)
				Debug.message("\tLoading is OK !!");
			else
				Debug.message("\tLoading is failed !!");
			start();
			return isLoadingOK();
		}
	}

	public boolean addSTLAscii(InputStream inputStream) {
		return addSTLAscii(new InputStreamReader(inputStream));
	}
	
	public boolean addSTLAscii(URL url) {

		Debug.message("SceneGraph::add = Loading a url file (" + url + ") ..... ");
		
		try {
			InputStream	inputStream	= url.openStream();
			addSTLAscii(inputStream);
		}
		catch (IOException ioe) {
			Debug.warning("Loading Error (IOException) = " + url);
			System.out.println(ioe.getMessage());
		} 
		
		return isLoadingOK();
	}

	public boolean addSTLAscii(File file) {

		try {
			URL urlFile = toURL(file);
			addSTLAscii(urlFile);
		}
		catch (MalformedURLException mue) {
			Debug.warning("Loading Error (MalformedURLException) = " + file);
			System.out.println(mue.getMessage());
		}
		
		return isLoadingOK();
	}
	
	///////////////////////////////////////////////
	//	Load infomations
	///////////////////////////////////////////////

	private ParserResult parserResult = new ParserResult();
	
	private ParserResult getParserResultObject()
	{
		return parserResult;
	}
		
	private void setLoadingResult(boolean flag) {
		parserResult.setResult(flag);
	}

	public boolean getLoadingResult() {
		return parserResult.getResult();
	}
	
	public boolean isLoadingOK() {
		return parserResult.getResult();
	}

	public boolean getParserResult() {
		return parserResult.getResult();
	}

	public void setParserErrorMessage(String msg) {
		 parserResult.setErrorMessage(msg);
	}
	
	public String getParserErrorMessage() {
		return parserResult.getErrorMessage();
	}
	
	///////////////////////////////////////////////
	//	Load 
	///////////////////////////////////////////////
	
	public static final int FILE_FORMAT_NONE		= 0;
	public static final int FILE_FORMAT_WRL		= 1;
	public static final int FILE_FORMAT_OBJ		= 2;
	public static final int FILE_FORMAT_3DS		= 3;
	public static final int FILE_FORMAT_NFF		= 4;
	public static final int FILE_FORMAT_LWO		= 5;
	public static final int FILE_FORMAT_LWS		= 6;
	public static final int FILE_FORMAT_STLA		= 7;
	public static final int FILE_FORMAT_DXF		= 8;
	public static final int FILE_FORMAT_X3D		= 9;

	public static int getFileFormat(String fileName) {
		if (fileName == null)
			return FILE_FORMAT_NONE;
		int idx = fileName.lastIndexOf('.');
		if (idx < 0)
			return FILE_FORMAT_NONE;
		String ext = new String(fileName.getBytes(), (idx+1), fileName.length() - (idx+1));
		int formatType = FILE_FORMAT_NONE;
		if (ext.equalsIgnoreCase("WRL") == true)
			formatType = FILE_FORMAT_WRL;
		if (ext.equalsIgnoreCase("OBJ") == true)
			formatType = FILE_FORMAT_OBJ;
		if (ext.equalsIgnoreCase("3DS") == true)
			formatType = FILE_FORMAT_3DS;
		if (ext.equalsIgnoreCase("NFF") == true)
			formatType = FILE_FORMAT_NFF;
		if (ext.equalsIgnoreCase("LWO") == true)
			formatType = FILE_FORMAT_LWO;
		if (ext.equalsIgnoreCase("LWS") == true)
			formatType = FILE_FORMAT_LWS;
		if (ext.equalsIgnoreCase("SLP") == true)
			formatType = FILE_FORMAT_STLA;
		if (ext.equalsIgnoreCase("STL") == true)
			formatType = FILE_FORMAT_STLA;
		if (ext.equalsIgnoreCase("DXF") == true)
			formatType = FILE_FORMAT_DXF;
		if (ext.equalsIgnoreCase("X3D") == true)
			formatType = FILE_FORMAT_X3D;
		if (ext.equalsIgnoreCase("XML") == true)
			formatType = FILE_FORMAT_X3D;
		Debug.message("File format = " + formatType);
		return formatType;
	}
		
	public boolean add(URL url) {
		switch (getFileFormat(url.toString())) {
		case FILE_FORMAT_WRL:	
			return addVRML97(url);
		case FILE_FORMAT_OBJ:	
			return addOBJ(url);
		case FILE_FORMAT_3DS:	
			return add3DS(url);
		case FILE_FORMAT_NFF:	
			return addNFF(url);
		case FILE_FORMAT_STLA:	
			return addSTLAscii(url);
		case FILE_FORMAT_DXF:	
			return addDXF(url);
		case FILE_FORMAT_X3D:	
			return addX3D(url);
		}
		return false;
	}
	
	public boolean add(File file) {
		switch (getFileFormat(file.toString())) {
		case FILE_FORMAT_WRL:	
			return addVRML97(file);
		case FILE_FORMAT_OBJ:	
			return addOBJ(file);
		case FILE_FORMAT_3DS:	
			return add3DS(file);
		case FILE_FORMAT_NFF:	
			return addNFF(file);
		case FILE_FORMAT_STLA:	
			return addSTLAscii(file);
		case FILE_FORMAT_DXF:	
			return addDXF(file);
		case FILE_FORMAT_X3D:	
			return addX3D(file);
		}
		return false;
	}
	
	public boolean add(String filename) {
		try {
			File file = new File(filename);
			if (file.exists() == true)
				return add(file);
			else {
				try {
					URL url = new URL(filename);
					return add(url);
				}
				catch (MalformedURLException mue) {
					Debug.message("File not found (" + filename + ")");
				}
			}
		}
		catch (NullPointerException npe) {
			Debug.message("File not found (" + filename + ")");
		}
		return false;
	}

	public boolean load(String filename) {
		clear();
		return add(filename);
	}

	public boolean load(URL urlFilename) {
		clear();
		return add(urlFilename);
	}

	public boolean load(File file) {
		clear();
		return add(file);
	}

	///////////////////////////////////////////////
	//	Save infomations (VRML)
	///////////////////////////////////////////////
	
	public boolean save(OutputStream outputStream) {
	
		uninitialize();
		
		PrintWriter printStream = new PrintWriter(outputStream);

		printStream.println("#VRML V2.0 utf8");

		for (Node node = getNodes(); node != null; node = node.next()) {
			node.output(printStream, 0);
		}
			
		for (Route route = getRoutes(); route != null; route = route.next()) {
			route.output(printStream);
		}
		
		printStream.flush();
		if (outputStream.equals(System.out) == false)
			printStream.close();
		
		initialize();
		
		return true;
	}

	public boolean save(File file) {
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			save(outputStream);
			outputStream.close();
		}
		catch (IOException e) {
			Debug.warning("Couldn't open the file (" + file + ")");
			return false;
		}
		
		return true;
	}
	
	public boolean save(String filename) {
		try {
			FileOutputStream outputStream = new FileOutputStream(filename);
			save(outputStream);
			outputStream.close();
		}
		catch (IOException e) {
			Debug.warning("Couldn't open the file (" + filename + ")");
			return false;
		}
		
		return true;
	}

	///////////////////////////////////////////////
	//	Save infomations (VRML)
	///////////////////////////////////////////////

	public boolean saveVRML(OutputStream outputStream) {
		return save(outputStream);
	}

	public boolean saveVRML(File file) {
		return save(file);
	}
	
	public boolean saveVRML(String filename) {
		return save(filename);
	}

	///////////////////////////////////////////////
	//	Save infomations (XML)
	///////////////////////////////////////////////
	
	public boolean saveXML(OutputStream outputStream) {
	
		uninitialize();
		
		PrintWriter printStream = new PrintWriter(outputStream);

		printStream.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		printStream.println("<X3D>");
		printStream.println("\t<Scene>");

		for (Node node = getNodes(); node != null; node = node.next()) {
			node.outputXML(printStream, 2);
		}
		
		for (Route route = getRoutes(); route != null; route = route.next()) {
			route.outputXML(printStream, 2);
		}
		
		printStream.println("\t</Scene>");
		printStream.println("</X3D>");

		printStream.flush();
		if (outputStream.equals(System.out) == false)
			printStream.close();
		
		initialize();
		
		return true;
	}

	public boolean saveXML(File file) {
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			saveXML(outputStream);
			outputStream.close();
		}
		catch (IOException e) {
			Debug.warning("Couldn't open the file (" + file + ")");
			return false;
		}
		
		return true;
	}
	
	public boolean saveXML(String filename) {
		try {
			FileOutputStream outputStream = new FileOutputStream(filename);
			saveXML(outputStream);
			outputStream.close();
		}
		catch (IOException e) {
			Debug.warning("Couldn't open the file (" + filename + ")");
			return false;
		}
		
		return true;
	}

	///////////////////////////////////////////////
	//	Output node infomations
	///////////////////////////////////////////////
	
	public void print() {
		save(System.out);
	}

	public void printXML() {
		saveXML(System.out);
	}

	///////////////////////////////////////////////
	//	Clear infomations
	///////////////////////////////////////////////
	
	public void clear() {
		// Delete all nodes
		clearNodes();
	}

	///////////////////////////////////////////////
	//	Bindable Nodes
	///////////////////////////////////////////////
	
	private Vector	mBackgroundVector		= new Vector();
	private Vector	mFogVector				= new Vector();
	private Vector	mNavigationInfoVector	= new Vector();
	private Vector	mViewpointVector		= new Vector();	

	private BackgroundNode		mDefaultBackgroundNode		= new BackgroundNode();
	private FogNode				mDefaultFogNode				= new FogNode();
	private NavigationInfoNode	mDefaultNavigationInfoNode	= new NavigationInfoNode();
	private ViewpointNode		mDefaultViewpointNode		= new ViewpointNode();

	public BindableNode bindableGetTopNode(Vector vector) {
		BindableNode topNode;

		try {
			topNode = (BindableNode)vector.lastElement();
		} catch(NoSuchElementException e) { 
			topNode = null;
		}

		return topNode;
	}

	public void setBindableNode(Vector nodeVector, BindableNode node, boolean bind) {
		if (node == null)
			return;

		BindableNode topNode = bindableGetTopNode(nodeVector);

		if (bind) {
			if (topNode != node) {
				if (topNode != null) {
					topNode.setIsBound(false);
					topNode.sendEvent(topNode.getIsBoundField());
				}
	
				nodeVector.removeElement(node);
				nodeVector.addElement(node);
	
				node.setIsBound(true);
				node.sendEvent(node.getIsBoundField());
			}
		}
		else {
			if (topNode == node) {
				node.setIsBound(false);
				node.sendEvent(node.getIsBoundField());
	
				nodeVector.removeElement(node);
	
				BindableNode newTopNode = bindableGetTopNode(nodeVector);

				if (newTopNode != null) {
					newTopNode.setIsBound(true);
					newTopNode.sendEvent(newTopNode.getIsBoundField());
				}
			}
			else {
				nodeVector.removeElement(node);
			}
		}
	}

	public void setBindableNode(BindableNode node, boolean bind) {
		if (node.isBackgroundNode())		setBackgroundNode((BackgroundNode)node, bind);
		if (node.isFogNode())				setFogNode((FogNode)node, bind);
		if (node.isNavigationInfoNode())	setNavigationInfoNode((NavigationInfoNode)node, bind);
		if (node.isViewpointNode())			setViewpointNode((ViewpointNode)node, bind);
	}

	public void setBackgroundNode(BackgroundNode bg, boolean bind) {
		setBindableNode(mBackgroundVector, bg, bind);
	}

	public void setFogNode(FogNode fog, boolean bind) {
		setBindableNode(mFogVector, fog, bind);
	}

	public void setNavigationInfoNode(NavigationInfoNode navInfo, boolean bind) {
		setBindableNode(mNavigationInfoVector, navInfo, bind);
	}

	public void setViewpointNode(ViewpointNode view, boolean bind) {
		setBindableNode(mViewpointVector, view, bind);
	}

	public BackgroundNode getBackgroundNode() {
		return (BackgroundNode)bindableGetTopNode(mBackgroundVector);
	}

	public FogNode getFogNode() {
		return (FogNode)bindableGetTopNode(mFogVector);
	}

	public NavigationInfoNode getNavigationInfoNode() {
		return (NavigationInfoNode)bindableGetTopNode(mNavigationInfoVector);
	}

	public ViewpointNode getViewpointNode() {
		return (ViewpointNode)bindableGetTopNode(mViewpointVector);
	}

	public BackgroundNode getDefaultBackgroundNode() {
		return mDefaultBackgroundNode;
	}

	public FogNode getDefaultFogNode() {
		return mDefaultFogNode;
	}

	public NavigationInfoNode getDefaultNavigationInfoNode() {
		return mDefaultNavigationInfoNode;
	}

	public ViewpointNode getDefaultViewpointNode() {
		return mDefaultViewpointNode;
	}

	////////////////////////////////////////////////
	//	SceneGraph Object
	////////////////////////////////////////////////
	
	private SceneGraphObject	mObject = null;
	
	public void setObject(SceneGraphObject object) {
		mObject = object;
	}
	
	public SceneGraphObject getObject() {
		return mObject;
	}
	
	public boolean hasObject() {
		return (mObject != null ? true : false);
	}
	
	public boolean initializeObject() {
		if (hasObject())
			return mObject.initialize(this);
		return false;
	}

	public boolean uninitializeObject() {
		if (hasObject())
			return mObject.uninitialize(this);
		return false;
	}

	public boolean addNodeObject(Node node) {
		if (hasObject())
			return mObject.addNode(this, node);
		return false;
	}

	public boolean removeNodeObject(Node node) {
		if (hasObject())
			return mObject.removeNode(this, node);
		return false;
	}

	public boolean updateObject() {
		if (hasObject()) {
			boolean ret;
			synchronized (mObject) {
				ret = mObject.update(this);
			}
			return ret;
		}
		return false;
	}
	
	public boolean removeObject() {
		if (hasObject())
			return mObject.remove(this);
		return false;
	}

	public NodeObject	createNodeObject(Node node) {
		if (hasObject() == false)
			return null;
		return getObject().createNodeObject(this, node);
	}

	////////////////////////////////////////////////
	//	Option
	////////////////////////////////////////////////
	
	private int mOption	= 0;
	
	public void setOption(int option) {
		mOption = option;
	}
	
	public int getOption() {
		return mOption;
	}
	
	public boolean getOption(int option) {
		if ((mOption & option) == option)
			return true;
		return false;
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	private float mBoundingBoxCenter[]	= new float[3];
	private float mBoundingBoxSize[]	= new float[3];

	public void setBoundingBoxCenter(float center[]) {
		for (int n=0; n<3; n++)
			mBoundingBoxCenter[n] = center[n];
	}

	public void setBoundingBoxCenter(float x, float y, float z) {
		mBoundingBoxCenter[0] = x;
		mBoundingBoxCenter[1] = y;
		mBoundingBoxCenter[2] = z;
	}

	public void getBoundingBoxCenter(float center[]) {
		for (int n=0; n<3; n++)
			center[n] = mBoundingBoxCenter[n];
	}

	public float[] getBoundingBoxCenter() {
		float center[] = new float[3];
		getBoundingBoxCenter(center);
		return center;
	}

	public void setBoundingBoxSize(float size[]) {
		for (int n=0; n<3; n++)
			mBoundingBoxSize[n] = size[n];
	}
		
	public void setBoundingBoxSize(float x, float y, float z) {
		mBoundingBoxSize[0] = x;
		mBoundingBoxSize[1] = y;
		mBoundingBoxSize[2] = z;
	}

	public void getBoundingBoxSize(float size[]) {
		for (int n=0; n<3; n++)
			size[n] = mBoundingBoxSize[n];
	}

	public float[] getBoundingBoxSize() {
		float size[] = new float[3];
		getBoundingBoxSize(size);
		return size;
	}

	public void updateBoundingBox(Node node, BoundingBox bbox) {
		//if (node.isGroupingNode() == true) {
		if (node.isGeometry3DNode() == true) {
			//GroupingNode gnode = (GroupingNode)node;
			Geometry3DNode gnode = (Geometry3DNode)node;

			float bboxCenter[]	= new float[3];
			float bboxSize[]	= new float[3];
			float point[]		= new float[3];

			gnode.getBoundingBoxCenter(bboxCenter);
			gnode.getBoundingBoxSize(bboxSize);
			
			if (bboxSize[0] >= 0.0f && bboxSize[1] >= 0.0f && bboxSize[2] >= 0.0f) {
				SFMatrix nodemx = node.getTransformMatrix();
				for (int n=0; n<8; n++) {
					point[0] = (n < 4)				? bboxCenter[0] - bboxSize[0] : bboxCenter[0] + bboxSize[0];
					point[1] = ((n % 2) != 0)	? bboxCenter[1] - bboxSize[1] : bboxCenter[1] + bboxSize[1];
					point[2] = ((n % 4) < 2)		? bboxCenter[2] - bboxSize[2] : bboxCenter[2] + bboxSize[2];
					nodemx.multi(point);
					bbox.addPoint(point);
				}
			}
		}
		
		for (Node cnode=node.getChildNodes(); cnode != null; cnode=cnode.next()) 
			updateBoundingBox(cnode, bbox);
	}
	
	public void updateBoundingBox() {
		BoundingBox bbox = new BoundingBox();
		for (Node node=getNodes(); node != null; node=node.next())
			updateBoundingBox(node, bbox);
		setBoundingBoxCenter(bbox.getCenter());
		setBoundingBoxSize(bbox.getSize());
	}
				
	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public float getRadius() {
		SFVec3f bboxSize = new SFVec3f(getBoundingBoxSize());
		return bboxSize.getScalar(); 
	}
	
	////////////////////////////////////////////////
	//	Thread
	////////////////////////////////////////////////
	
	private Thread mThreadObject = null;
	
	public void setThreadObject(Thread obj) {
		mThreadObject = obj;
	}

	public Thread getThreadObject() {
		return mThreadObject;
	}

	public void run() 
	{
		Thread thisThread = Thread.currentThread();
		while (thisThread == getThreadObject()) {
			updateSimulation();
			Thread threadObject = getThreadObject();
			if (threadObject != null) { 
//				threadObject.yield();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	public void start() {
		Thread threadObject = getThreadObject();
		if (threadObject == null) {
			threadObject = new Thread(this);
			setThreadObject(threadObject);
			threadObject.start();
		}
	}
	
	public void stop() {
		Thread threadObject = getThreadObject();
		if (threadObject != null) { 
			//threadObject.destroy();
			//threadObject.stop();
			setThreadObject(null);
		}
	}

	////////////////////////////////////////////////
	//	Directory/URL
	////////////////////////////////////////////////
	
	private URL mBaseURL = null;

	public void setBaseURL(URL url) {
		mBaseURL = url;
	}

	public void setBaseURL(String urlString) {
		int index = urlString.lastIndexOf('/');
		if (0 <= index) { 
			try {
				URL baseURL = new URL(new String(urlString.getBytes(), 0, index + 1));
				setBaseURL(baseURL);
			}
			catch (MalformedURLException mue) {
				setBaseURL((URL)null);
			}
		}
		else
			setBaseURL((URL)null);
	}
	
	public URL getBaseURL() {
		return mBaseURL;
	}

	////////////////////////////////////////////////
	//	Rendering mode
	////////////////////////////////////////////////
	
	private int mRenderingMode = RENDERINGMODE_FILL;
	
	public boolean setRenderingMode(int mode) {
		mRenderingMode = mode;
		if (hasObject() == false)
			return false;
		return mObject.setRenderingMode(this, mode);
	}

	public int getRenderingMode() {
		return mRenderingMode;
	}

	////////////////////////////////////////////////
	//	Headlight
	////////////////////////////////////////////////

	public void setHeadlightState(boolean state) {
		NavigationInfoNode navInfo = getNavigationInfoNode();
		if (navInfo == null) 
			navInfo = getDefaultNavigationInfoNode();
		navInfo.setHeadlight(state);		
	}

	public boolean isHeadlightOn() {
		NavigationInfoNode navInfo = getNavigationInfoNode();
		if (navInfo == null) 
			navInfo = getDefaultNavigationInfoNode();
		return navInfo.getHeadlight();		
	}

	////////////////////////////////////////////////
	//	Navigation Speed
	////////////////////////////////////////////////

	public void setNavigationSpeed(float speed) {
		NavigationInfoNode navInfo = getNavigationInfoNode();
		if (navInfo == null) 
			navInfo = getDefaultNavigationInfoNode();
		navInfo.setSpeed(speed);		
	}

	public float getNavigationSpeed() {
		NavigationInfoNode navInfo = getNavigationInfoNode();
		if (navInfo == null) 
			navInfo = getDefaultNavigationInfoNode();
		return navInfo.getSpeed();		
	}

	////////////////////////////////////////////////
	//	TouchSensor 
	////////////////////////////////////////////////

	public double getCurrentTime() {
		Date	date = new Date();
		return (double)date.getTime() / 1000.0;
	}
	
	public void shapePressed(ShapeNode shapeNode, int mx, int my) {
		for (Node node=getNodes(); node != null; node=node.nextTraversal()) {
			if (node.isTouchSensorNode() == true) {
				Debug.message(node + " is active !!");
				TouchSensorNode touchSensor = (TouchSensorNode)node;
				Node parentNode = touchSensor.getParentNode();
				if (shapeNode.isAncestorNode(parentNode) == true) {
					SFBool isActive = touchSensor.getIsActiveField();
					isActive.setValue(true);
					touchSensor.sendEvent(isActive);
						
					SFTime touchTime = touchSensor.getTouchTimeField();
					touchTime.setValue(getCurrentTime());
					touchSensor.sendEvent(touchTime);
				}
			} 
			else if (node.isPlaneSensorNode() == true) {
				PlaneSensorNode planeSensor = (PlaneSensorNode)node;
				Node parentNode = planeSensor.getParentNode();
				if (shapeNode.isAncestorNode(parentNode) == true) {
					Debug.message(node + " is active !!");
					SFBool isActive = planeSensor.getIsActiveField();
					isActive.setValue(true);
					planeSensor.sendEvent(isActive);
				}
			}
		}
	}
	
	public void shapeReleased(ShapeNode shapeNode, int mx, int my) {
		for (Node node=getNodes(); node != null; node=node.nextTraversal()) {
			if (node.isTouchSensorNode() == true) {
				Debug.message(node + " is inactive !!");
				TouchSensorNode touchSensor = (TouchSensorNode)node;
				Node parentNode = touchSensor.getParentNode();
				if (shapeNode.isAncestorNode(parentNode) == true) {
					SFBool isActive = touchSensor.getIsActiveField();
					isActive.setValue(false);
					touchSensor.sendEvent(isActive);
				}
			} 
			else if (node.isPlaneSensorNode() == true) {
				PlaneSensorNode planeSensor = (PlaneSensorNode)node;
				Node parentNode = planeSensor.getParentNode();
				if (shapeNode.isAncestorNode(parentNode) == true) {
					Debug.message(node + " is inactive !!");
					SFBool isActive = planeSensor.getIsActiveField();
					isActive.setValue(false);
					planeSensor.sendEvent(isActive);
				}
			}
		}
	}
	
	public void shapeDragged(ShapeNode shapeNode, int mx, int my) {
	/*
			float world[3];
			int mx = point.x - getStartMousePositionX();
			int my = point.y - getStartMousePositionY();
			getWorldPosition(mx, my, shapeNode, world);
			PlaneSensorNode planeSensor = getPlaneSensorNode();
			planeSensor.setTranslationChanged(world[0], world[1], world[2]);
			planeSensor.sendEvent(planeSensor.getTranslationChangedField());
	*/
	}

	////////////////////////////////////////////////
	// Default Viewpoint Position/ Orientation 
	////////////////////////////////////////////////

	private float mStartViewPosition[]		= new float[3];
	private float mStartViewOrientation[]	= new float[4];

	public void setViewpointStartPosition(float pos[]) {
		mStartViewPosition[0] = pos[0];
		mStartViewPosition[1] = pos[1];
		mStartViewPosition[2] = pos[2];
	}

	public void setViewpointStartPosition(float x, float y, float z) {
		mStartViewPosition[0] = x;
		mStartViewPosition[1] = y;
		mStartViewPosition[2] = z;
	}

	public void getViewpointStartPosition(float pos[]) {
		pos[0] = mStartViewPosition[0];
		pos[1] = mStartViewPosition[1];
		pos[2] = mStartViewPosition[2];
	}

	public void setViewpointStartOrientation(float x, float y, float z, float angle) {
		mStartViewOrientation[0] = x;
		mStartViewOrientation[1] = y;
		mStartViewOrientation[2] = z;
		mStartViewOrientation[3] = angle;	
	}

	public void setViewpointStartOrientation(float ori[]) {
		mStartViewOrientation[0] = ori[0];
		mStartViewOrientation[1] = ori[1];
		mStartViewOrientation[2] = ori[2];
		mStartViewOrientation[3] = ori[3];
	}

	public void getViewpointStartOrientation(float ori[]) {
		ori[0] = mStartViewOrientation[0];
		ori[1] = mStartViewOrientation[1];
		ori[2] = mStartViewOrientation[2];
		ori[3] = mStartViewOrientation[3];
	}

	public void saveViewpointStartPositionAndOrientation() {
		ViewpointNode view = getViewpointNode();
		Debug.message("Start Viewpoint = " + view);
		if (view != null) {
			float viewPos[] = new float[3];
			float viewOri[] = new float[4];
			view.getPosition(viewPos);
			view.getOrientation(viewOri);
			Debug.message("\tPosition    = " + viewPos[0] + ", " + viewPos[1] + ", " + viewPos[2]);
			Debug.message("\tOrientation = " + viewOri[0] + ", " + viewOri[1] + ", " + viewOri[2] + ", " + viewOri[3]);
			setViewpointStartPosition(viewPos);
			setViewpointStartOrientation(viewOri);
		}
	}
			
	////////////////////////////////////////////////
	// Reset Viewpoint
	////////////////////////////////////////////////
	
	private final static int XZ_PLANE	= 0;
	private final static int XY_PLANE	= 1;
	private final static int YZ_PLANE	= 2;
	
	private final static float VIEWPOS_OFFSET_SCALE	= 3.0f;
		
	private void resetViewpointAlongXAxis(float bboxCenter[], float bboxSize[], ViewpointNode view, float fov) {
		float offset1 = bboxSize[1] / (float)Math.tan(fov);
		float offset2 = bboxSize[2] / (float)Math.tan(fov);
		float offset = Math.max(Math.max(offset1, offset2), bboxSize[0]) * VIEWPOS_OFFSET_SCALE;
		view.setPosition(bboxCenter[0] + offset, bboxCenter[1], bboxCenter[2]);
		view.setOrientation(0.0f, 1.0f, 0.0f, (float)(Math.PI/2.0));
	}

	private void resetViewpointAlongYAxis(float bboxCenter[], float bboxSize[], ViewpointNode view, float fov) {
		float offset1 = bboxSize[0] / (float)Math.tan(fov);
		float offset2 = bboxSize[2] / (float)Math.tan(fov);
		float offset = Math.max(Math.max(offset1, offset2), bboxSize[1]) * VIEWPOS_OFFSET_SCALE;
		view.setPosition(bboxCenter[0], bboxCenter[1] + offset, bboxCenter[2]);
		view.setOrientation(1.0f, 0.0f, 0.0f, -(float)(Math.PI/2.0));
	}

	private void resetViewpointAlongZAxis(float bboxCenter[], float bboxSize[], ViewpointNode view, float fov) {
		float offset1 = bboxSize[0] / (float)Math.tan(fov);
		float offset2 = bboxSize[1] / (float)Math.tan(fov);
		float offset = Math.max(Math.max(offset1, offset2), bboxSize[2]) * VIEWPOS_OFFSET_SCALE;
		view.setPosition(bboxCenter[0], bboxCenter[1], bboxCenter[2] + offset);
		view.setOrientation(0.0f, 0.0f, 1.0f, 0.0f);
	}

	public void resetViewpoint() {
		float viewPos[] = new float[3];
		float viewOri[] = new float[4];
									
		getViewpointStartPosition(viewPos);
		getViewpointStartOrientation(viewOri);

		ViewpointNode view = getViewpointNode();
		if (view == null)
			view = getDefaultViewpointNode();
										
		Debug.message("Reset Viewpoint = " + view);
		Debug.message("\tPosition    = " + viewPos[0] + ", " + viewPos[1] + ", " + viewPos[2]);
		Debug.message("\tOrientation = " + viewOri[0] + ", " + viewOri[1] + ", " + viewOri[2] + ", " + viewOri[3]);
									
		view.setPosition(viewPos);
		view.setOrientation(viewOri);
	}
	
	public void resetViewpoint(int plane) {
		ViewpointNode view = getViewpointNode();
		if (view == null)
			view = getDefaultViewpointNode();

		updateBoundingBox();
			
		float bboxCenter[]	= getBoundingBoxCenter();
		float bboxSize[]		= getBoundingBoxSize();
			
		float fov = view.getFieldOfView();
		
		Debug.message("Reset Viewpoint bboxCenter = " + bboxCenter[0] + ", " + bboxCenter[1] + ", " + bboxCenter[2]);
		Debug.message("                bboxSize   = " + bboxSize[0] + ", " + bboxSize[1] + ", " + bboxSize[2]);
		Debug.message("                fov        = " + fov);

		switch (plane) {
		case XY_PLANE:	resetViewpointAlongZAxis(bboxCenter, bboxSize, view, fov);	break;
		case XZ_PLANE:	resetViewpointAlongYAxis(bboxCenter, bboxSize, view, fov);	break;
		case YZ_PLANE:	resetViewpointAlongXAxis(bboxCenter, bboxSize, view, fov);	break;
		}			
	}

	public void resetViewpointAlongXAxis() {
		resetViewpoint(YZ_PLANE);
	}

	public void resetViewpointAlongYAxis() {
		resetViewpoint(XZ_PLANE);
	}

	public void resetViewpointAlongZAxis() {
		resetViewpoint(XY_PLANE);
	}

	////////////////////////////////////////////////
	// Share World
	////////////////////////////////////////////////

	private ShareWorldClient	mShareWolrdClient	= null;
	
	private void setShareWorldClient(ShareWorldClient client)
	{
		mShareWolrdClient = client;
	}
	
	public ShareWorldClient getShareWorldClient()
	{
		return mShareWolrdClient;
	}

	public boolean connectShareWorld(String hostName, int port)
	{
		ShareWorldClient client = new ShareWorldClient(hostName, port);
		if (client.isConnected() == false) {
			setShareWorldClient(null);
			return false;
		}

		client.setSceneGraph(this);
		setShareWorldClient(client);
		
		client.executeThread();

		return true;
	}

	public boolean connectShareWorld(String hostName)
	{
		return connectShareWorld(hostName, ShareWorld.getDefaultSeverSocketPort());
	}
	
	public boolean isConnectedShareWorld()
	{
		return (mShareWolrdClient != null) ? true : false;
	}
	
	public void postShareObject(ShareObject shareObject)
	{
		if (isConnectedShareWorld() == false)
			return;
		
		getShareWorldClient().postShreObject(shareObject);
	}

	////////////////////////////////////////////////
	//	Share Filed Operation
	////////////////////////////////////////////////

	public boolean postShareNodeRemoveEvent(Node node)
	{
		if (node.hasName() == false)
			return false;
			
		ShareNodeRemove removeNodeEvent = new ShareNodeRemove(node);
		postShareObject(removeNodeEvent);

		return true;
	}

	public boolean postShareNodeAddEvent(Node node)
	{
		Debug.message("postShareNodeAddEvent");
		
		Node parentNode = node.getParentNode();
		
		Debug.message("  parentNode = " + parentNode);
		Debug.message("  node = " + node);
/*		
		if (parentNode == null)
			return false;
			
		if (parentNode.hasName() == false)
			return false;
*/
					
		ShareNodeAdd addNodeEvent = new ShareNodeAdd(node);
		postShareObject(addNodeEvent);

		return true;
	}
}