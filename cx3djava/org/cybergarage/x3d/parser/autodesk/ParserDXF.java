/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1999
*
*	File : ParserDXF.java
*
******************************************************************/

package org.cybergarage.x3d.parser.autodesk;

import java.io.*;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.Debug;

public class ParserDXF extends Object implements ParserDXFConstants {

	private RootNode		mRootNode;
	private ShapeNode		mCurrentShapeNode;
	
	private boolean		mHasFaceColor;
	private float			mPrevFaceColor[] = new float[3];
	private int				mSolidFaceCount;
		
	private boolean		mUseSeparatedShapeNode;
	
	public ParserDXF () {
	}

	public void initialize() {
		mRootNode = new RootNode();
		setCurrentShapeNode(null);
		setCurrentLine(0);
		setMarkedLine(0);
		setThrowable(true);
		setSeparatedShapeNodeFlag(false);
	}

	public ShapeNode createIdxFaceSetShapeNode() {
		ShapeNode shape = new ShapeNode();
		IndexedFaceSetNode idxFaceSetNode = new IndexedFaceSetNode();
		shape.addChildNode(idxFaceSetNode);
		idxFaceSetNode.addChildNode(new CoordinateNode());
		idxFaceSetNode.addChildNode(new ColorNode());
//		idxFaceSetNode.addChildNode(new NormalNode());
		idxFaceSetNode.setColorPerVertex(false);
		idxFaceSetNode.setNormalPerVertex(false);
		return shape;
	}

	public ShapeNode createIdxLineSetShapeNode() {
		ShapeNode shape = new ShapeNode();
		IndexedLineSetNode idxLineSetNode = new IndexedLineSetNode();
		shape.addChildNode(idxLineSetNode);
		idxLineSetNode.addChildNode(new CoordinateNode());
		idxLineSetNode.addChildNode(new ColorNode());
		idxLineSetNode.setColorPerVertex(false);
		return shape;
	}

	public int getNShapeNodes() {
		return mRootNode.getNChildNodes();
	}

	public ShapeNode getShapeNodes() {
		return mRootNode.getShapeNodes();
	}

	public ShapeNode getShapeNode(String name) {
		if (name != null)
			return mRootNode.getShapeNode(name);
		for (ShapeNode shapeNode = mRootNode.getShapeNodes(); shapeNode != null; shapeNode = (ShapeNode)shapeNode.nextSameType()) {			String shapeNodeName = shapeNode.getName();
			if (shapeNodeName == null || shapeNodeName.length() == 0)
				return shapeNode;
		}
		return null;
	}

	public void addShapeNode(ShapeNode node) {
		mRootNode.addChildNode(node);
	}

	public void setCurrentShapeNode(ShapeNode node) {
		mCurrentShapeNode = node;
	}

	public ShapeNode getCurrentShapeNode() {
		return mCurrentShapeNode;
	}

	private Geometry3DNode getCurrentGeometry3DNode() {
		return getCurrentShapeNode().getGeometry3DNode();
	} 

	private CoordinateNode getCurrentCoordinateNode() {
		return getCurrentGeometry3DNode().getCoordinateNodes();
	}

	private ColorNode getCurrentColorNode() {
		return getCurrentGeometry3DNode().getColorNodes();
	}

	private NormalNode getCurrentNormalNode() {
		return getCurrentGeometry3DNode().getNormalNodes();
	}

	private int getNCurrentCoordinateNodePoints() {
		return getCurrentGeometry3DNode().getCoordinateNodes().getNPoints();
	}
	
	private void addCoordinate(float x, float y, float z) {
		getCurrentCoordinateNode().addPoint(x, y, z);
	}

	private void addCoordinate(float point[]) {
		getCurrentCoordinateNode().addPoint(point);
	}

	private void addColor(float r, float g, float b) {
		getCurrentColorNode().addColor(r, g, b);
	}

	private void addColor(float color[]) {
		getCurrentColorNode().addColor(color);
	}

	private void addNormal(float x, float y, float z) {
		getCurrentNormalNode().addVector(x, y, z);
	}


	private void addCoordIndex(int index) {
		Geometry3DNode geom = getCurrentGeometry3DNode();
		if (geom instanceof IndexedFaceSetNode)
			((IndexedFaceSetNode)geom).addCoordIndex(index);
		if (geom instanceof IndexedLineSetNode)
			((IndexedLineSetNode)geom).addCoordIndex(index);
	}

	public int getNCoordIndices() {
		Geometry3DNode geom = getCurrentGeometry3DNode();
		if (geom instanceof IndexedFaceSetNode)
			return ((IndexedFaceSetNode)geom).getNCoordIndices();
		if (geom instanceof IndexedLineSetNode)
			return ((IndexedLineSetNode)geom).getNCoordIndices();
		return 0;
	}

	public int getCoordIndex(int n) {
		Geometry3DNode geom = getCurrentGeometry3DNode();
		if (geom instanceof IndexedFaceSetNode)
			return ((IndexedFaceSetNode)geom).getCoordIndex(n);
		if (geom instanceof IndexedLineSetNode)
			return ((IndexedLineSetNode)geom).getCoordIndex(n);
		return -1;
	}
			
	
	private void addNormalIndex(int index) {
		Geometry3DNode geom = getCurrentGeometry3DNode();
		if (geom instanceof IndexedFaceSetNode)
			((IndexedFaceSetNode)geom).addNormalIndex(index);
	}
	
	public int getNNormalIndices() {
		Geometry3DNode geom = getCurrentGeometry3DNode();
		if (geom instanceof IndexedFaceSetNode)
			((IndexedFaceSetNode)geom).getNNormalIndices();
		return 0;
	}

	public int getNormalIndex(int n) {
		Geometry3DNode geom = getCurrentGeometry3DNode();
		if (geom instanceof IndexedFaceSetNode)
			return ((IndexedFaceSetNode)geom).getNormalIndex(n);
		return -1;
	}


	private void addTexCoordIndex(int index) {
		Geometry3DNode geom = getCurrentGeometry3DNode();
		if (geom instanceof IndexedFaceSetNode)
			((IndexedFaceSetNode)geom).addTexCoordIndex(index);
	}

	public int getNTexCoordIndices() {
		Geometry3DNode geom = getCurrentGeometry3DNode();
		if (geom instanceof IndexedFaceSetNode)
			((IndexedFaceSetNode)geom).getNTexCoordIndices();
		return 0;
	}

	public int getTexCoordIndex(int n) {
		Geometry3DNode geom = getCurrentGeometry3DNode();
		if (geom instanceof IndexedFaceSetNode)
			((IndexedFaceSetNode)geom).getTexCoordIndex(n);
		return -1;
	}


	public int getN3DFaceVertices(float point[][]) {
		for (int n=0; n<3; n++) {
			if (point[2][n] != point[3][n])
				return 4;
		}
		return 3;
	}

	///////////////////////////////////////////////
	// Flag
	///////////////////////////////////////////////

	private void setThrowable(boolean isOK) 
	{
		mThrowable = isOK;
	}

	private boolean isThrowable() 
	{
		return mThrowable;
	}

	private void setSeparatedShapeNodeFlag(boolean flag) {
		mUseSeparatedShapeNode = flag;
	}
	
	private boolean useSeparatedShapeNode() {
		return mUseSeparatedShapeNode;
	}
	
	///////////////////////////////////////////////
	// BufferedReader
	///////////////////////////////////////////////

	private BufferedReader	mBufferedReader;
	private int					mCurrentLine;
	private int					mMarkedLine;
	private boolean			mThrowable;

	private void setBufferedReader(BufferedReader buf) 
	{
		mBufferedReader = buf;
	}

	private BufferedReader getBufferedReader() 
	{
		return mBufferedReader;
	}

	private void setCurrentLine(int line) 
	{
		mCurrentLine = line;
	}

	private int getCurrentLine() 
	{
		return mCurrentLine;
	}

	private void setMarkedLine(int line) 
	{
		mMarkedLine = line;
	}

	private int getMarkedLine() 
	{
		return mMarkedLine;
	}

	private String readNextLineString(boolean setMark) throws IOException 
	{
		int currentLine = getCurrentLine();
		
		if (setMark == true) {
			setMarkedLine(currentLine);
			mBufferedReader.mark(0);
		}
		
		String data = mBufferedReader.readLine();

//		Debug.message("readNextLineString (" + currentLine + ") = " + data);
			
		if (data == null)
			return null;

		currentLine++;
		setCurrentLine(currentLine);

		StringBuffer str = new StringBuffer();
		
		for (int n=0; n<data.length(); n++) {
			char c = data.charAt(n);
			if (' ' < c)
				str.append(c);
		}
							
		return str.toString();
	}

	private int readNextLineInteger(boolean setMark) throws IOException 
	{
		int value = 0;
		try {
			value = Integer.parseInt(readNextLineString(setMark));
		}
		catch (NumberFormatException nfe) {};
		return value;
	}

	private float readNextLineFloat(boolean setMark) throws IOException 
	{
		float value = 0.0f;
		try {
			Float floatObj = new Float(readNextLineString(setMark));
			value = floatObj.floatValue();
			//value = Float.parseFloat(readNextLineString(setMark));
		}
		catch (NumberFormatException nfe) {};
		return value;
	}

	private String readNextLineString() throws IOException 
	{
		return readNextLineString(true);
	}

	private int readNextLineInteger() throws IOException 
	{
		return readNextLineInteger(true);
	}

	private float readNextLineFloat() throws IOException 
	{
		return readNextLineFloat(true);
	}

	private void pushCUrrentLine() throws IOException 
	{
		mBufferedReader.reset();
		setCurrentLine(getMarkedLine());
	}

	private void skipNextLine() throws IOException 
	{
		readNextLineString(true);
	}

	public int getID(String str) throws ParserDXFException
	{
		if (str == null)
			return -1;
		for (int n=0; n<ID.length; n++) {
			if (ID[n].equals(str) == true)
				return n;
		}
		if (isThrowable() == true && false)
			throw new ParserDXFException(getCurrentLine(), str);
		return -1;
	}
	
	private int readNextLineStringID() throws IOException, ParserDXFException
	{
		return getID(readNextLineString());
	}

	///////////////////////////////////////////////
	// input
	///////////////////////////////////////////////
	
	public boolean input(Reader reader) throws ParserDXFException
	{
   	setBufferedReader(new BufferedReader(reader));

		try {
			String nextLineString = readNextLineString();
			while (nextLineString != null) {
				parseGeometries(nextLineString);
				nextLineString = readNextLineString();
			}
		}
		catch (IOException iod) {
			return false;
		}   	
		
   	return true;
	}	

	public boolean input(InputStream in) throws ParserDXFException
	{
		return input(new InputStreamReader(in));
	}	

	///////////////////////////////////////////////
	// Color
	///////////////////////////////////////////////
	
	private void getColor(int id, float color[]) {
		switch (id) {
			case 0:	color[0] = 0.0f; color[1] = 0.0f; color[2] = 0.0f; return;
			case 1:	color[0] = 1.0f; color[1] = 0.0f; color[2] = 0.0f; return;
			case 2:	color[0] = 1.0f; color[1] = 1.0f; color[2] = 0.0f; return;
			case 3:	color[0] = 0.0f; color[1] = 1.0f; color[2] = 0.0f; return;
			case 4:	color[0] = 0.0f; color[1] = 1.0f; color[2] = 1.0f; return;
			case 5:	color[0] = 0.0f; color[1] = 0.0f; color[2] = 1.0f; return;
			case 6:	color[0] = 1.0f; color[1] = 0.0f; color[2] = 1.0f; return;
			case 7:	color[0] = 1.0f; color[1] = 1.0f; color[2] = 1.0f; return;
		}

		if (10 <= id && id < 50) {
			float perHighColor	= (float)(40 - (id - 10)) / 40.0f;
			float perLowColor		= 1.0f - perHighColor;
			color[0] = (1.0f * perHighColor) + (1.0f * perLowColor);
			color[1] = (0.0f * perHighColor) + (1.0f * perLowColor);
			color[2] = (0.0f * perHighColor) + (0.0f * perLowColor);
			return;
		}

		if (50 <= id && id < 90) {
			float perHighColor	= (float)(40 - (id - 50)) / 40.0f;
			float perLowColor		= 1.0f - perHighColor;
			color[0] = (1.0f * perHighColor) + (0.0f * perLowColor);
			color[1] = (1.0f * perHighColor) + (1.0f * perLowColor);
			color[2] = (0.0f * perHighColor) + (0.0f * perLowColor);
			return;
		}

		if (90 <= id && id < 130) {
			float perHighColor	= (float)(40 - (id - 90)) / 40.0f;
			float perLowColor		= 1.0f - perHighColor;
			color[0] = (0.0f * perHighColor) + (0.0f * perLowColor);
			color[1] = (1.0f * perHighColor) + (1.0f * perLowColor);
			color[2] = (0.0f * perHighColor) + (1.0f * perLowColor);
			return;
		}

		if (130 <= id && id < 170) {
			float perHighColor	= (float)(40 - (id - 130)) / 40.0f;
			float perLowColor		= 1.0f - perHighColor;
			color[0] = (0.0f * perHighColor) + (0.0f * perLowColor);
			color[1] = (1.0f * perHighColor) + (0.0f * perLowColor);
			color[2] = (1.0f * perHighColor) + (1.0f * perLowColor);
			return;
		}

		if (170 <= id && id < 210) {
			float perHighColor	= (float)(40 - (id - 170)) / 40.0f;
			float perLowColor		= 1.0f - perHighColor;
			color[0] = (0.0f * perHighColor) + (1.0f * perLowColor);
			color[1] = (0.0f * perHighColor) + (0.0f * perLowColor);
			color[2] = (1.0f * perHighColor) + (1.0f * perLowColor);
			return;
		}

		if (210 <= id && id < 250) {
			float perHighColor	= (float)(40 - (id - 210)) / 40.0f;
			float perLowColor		= 1.0f - perHighColor;
			color[0] = (1.0f * perHighColor) + (1.0f * perLowColor);
			color[1] = (0.0f * perHighColor) + (0.0f * perLowColor);
			color[2] = (1.0f * perHighColor) + (0.0f * perLowColor);
			return;
		}

		color[0] = 1.0f; 
		color[1] = 1.0f; 
		color[2] = 1.0f;
	}
	
	///////////////////////////////////////////////
	// Geometry
	///////////////////////////////////////////////

	private void parseGeometries(String groupCodeString) throws IOException, ParserDXFException
	{
		if (groupCodeString == null)
			return;

		int id = getID(groupCodeString);

		switch (id) {
		case GROUP0:
			{
				int nextID = readNextLineStringID();
				switch (nextID) {
 				case POLYLINE:
					{
						parsePolyLine();
					}
					break;
				case FACE3D:
					{
						parse3DFace();
					}
					break;
				case LINE:
					{
						parseLine();
					}
					break;
 				case EOF:
					{
					}
					break;
				}
			}
			break;
		case COMMENT:
			{
				readNextLineString();
			}
			break;
		}
		
		parseGeometries(readNextLineString());
	}

	///////////////////////////////////////////////
	// GroupCode
	///////////////////////////////////////////////

	private void parseGroupCode(String groupCodeString) throws IOException, ParserDXFException
	{
		if (groupCodeString == null)
			return;

		int id = getID(groupCodeString);

		Debug.message("TOP GroupCode   = " + groupCodeString + " (" + id + ")");
					
		switch (id) {
		case GROUP0:
			{
				int nextID = readNextLineStringID();
				switch (nextID) {
 				case SECTION:
					{
						parseSection();
					}
					break;
 				case EOF:
					{
					}
					break;
				}
			}
			break;
		case COMMENT:
			{
				readNextLineString();
			}
			break;
		}
		
		parseGroupCode(readNextLineString());
	}

	///////////////////////////////////////////////
	// parseSection
	///////////////////////////////////////////////

	private void parseSection() throws IOException, ParserDXFException
	{
		String groupCode;
		String sectionString;

		groupCode = readNextLineString();
		sectionString = readNextLineString();

		Debug.message("SECTION GroupCode   = " + groupCode);
		Debug.message("SECTION SectionCode = " + sectionString);

		switch (getID(sectionString)) {
/*
		case HEADER:
			{
				parseHeaderSection();
			}
			break;
*/
/*
		case TABLES:
			{
				parseTablesSection();
			}
			break;
*/
		case BLOCKS:
			{
				parseBlocksSection();
			}
			break;
		case ENTITIES:
			{
				parseEntitiesSection();
			}
			break;
		default:
			{
				String nextLineString = readNextLineString();
				while (nextLineString != null && getID(nextLineString) != ENDSEC)
					nextLineString = readNextLineString();
			}
		}
	}

	///////////////////////////////////////////////
	// HEADER SECTION
	///////////////////////////////////////////////

	private void parseHeaderSection() throws IOException, ParserDXFException
	{
		String groupCode = readNextLineString();
		String sectionString = readNextLineString();
		
		while (groupCode != null) {
			switch (getID(sectionString)) {
			case ENDSEC:
				{
					return;
				}
			}
			groupCode = readNextLineString();
			sectionString = readNextLineString();
		}
	}

	///////////////////////////////////////////////
	// TABLES SECTION
	///////////////////////////////////////////////

	private void parseTablesSection() throws IOException, ParserDXFException
	{
		String groupCode = readNextLineString();
		String sectionString = readNextLineString();
		
		while (groupCode != null) {
			switch (getID(sectionString)) {
			case ENDSEC:
				{
					return;
				}
			}
			groupCode = readNextLineString();
			sectionString = readNextLineString();
		}
	}

	///////////////////////////////////////////////
	// BLOCKS SECTION
	///////////////////////////////////////////////

	private void parseBlocksSection() throws IOException, ParserDXFException
	{
		String groupCode = readNextLineString();
		String sectionString = readNextLineString();
		
		while (groupCode != null) {
			switch (getID(sectionString)) {
			case BLOCK:
				{
					parseBlockSection();
				}
				break;
			case ENDSEC:
				{
					return;
				}
			}
			groupCode = readNextLineString();
			sectionString = readNextLineString();
		}
	}

	///////////////////////////////////////////////
	// BLOCK SECTION
	///////////////////////////////////////////////

	private void parseBlockSection() throws IOException, ParserDXFException
	{
		String	layerName	= null;
		int		colorIndex	= -1;
		int		flag			= -1;		
		float		basePoint[]	= new float[3];
		
		String groupCode = readNextLineString();

		while (groupCode != null) {
		
			int id = getID(groupCode);
			
			switch (id) {
			case GROUP0:
				{
					groupCode = readNextLineString(false);
					id = getID(groupCode);
					switch (id) {
					case FACE3D:
						{
							parse3DFace();
						}
						break;
					case SOLID:
						{
							parseSolid();
						}
						break;
					case POLYLINE:
						{
							parsePolyLine();
						}
						break;
					case ATTDEF:
						{
							parseAttDef();
						}
						break;
					case LINE:
						{
							parseLine();
						}
						break;
					default: 
						{
							pushCUrrentLine();
							groupCode = null;
							continue;
						}
					}
				}
				break;
			case LAYER:
				{
					layerName = readNextLineString();
				}
				break;
			case IDX_COLOR:
				{
					colorIndex = readNextLineInteger();
				}
				break;	
			case FLAG:
				{
					flag = readNextLineInteger();
				}
				break;	
			case VERTEX0_X:
			case VERTEX0_Y:
			case VERTEX0_Z:
				{
					int nPoint = id - VERTEX0_X;
					basePoint[nPoint] = readNextLineFloat();
				}
				break;
			case GROUP2:
				{
					skipNextLine();
				}
				break;
			default: 
				{
					pushCUrrentLine();
					groupCode = null;
					continue;
				}
			}
			
			groupCode = readNextLineString();
		}
	}

	///////////////////////////////////////////////
	// ENTITIES SECTION
	///////////////////////////////////////////////

	private void parseEntitiesSection() throws IOException, ParserDXFException
	{
		String groupCode = readNextLineString();
		String sectionString = readNextLineString();

		while (groupCode != null) {
//		Debug.message("ENTITY GroupCode   = " + groupCode);
//		Debug.message("ENTITY SectionCode = " + sectionString);
			switch (getID(sectionString)) {
			case FACE3D:
				{
					parse3DFace();
				}
				break;
			case SOLID:
				{
					parseSolid();
				}
				break;
			case POLYLINE:
				{
					parsePolyLine();
				}
				break;
			case LINE:
				{
					parseLine();
				}
				break;
			case ATTDEF:
				{
					parseAttDef();
				}
				break;
			case ENDSEC:
				{
					return;
				}
			}
			groupCode = readNextLineString();
			sectionString = readNextLineString();
		}
	}

	///////////////////////////////////////////////
	// ATTDEF
	///////////////////////////////////////////////

	private void parseAttDef() throws IOException, ParserDXFException
	{
		String	layerName = null;
		float		ocs[][] = new float[2][3];
		int		colorIndex = -1;
		
		String groupCode = readNextLineString();

		while (groupCode != null) {
		
			int id = getID(groupCode);

			switch (id) {
			case LAYER:
				{
					layerName = readNextLineString();
				}
				break;
			case IDX_COLOR:
				{
					colorIndex = readNextLineInteger();
				}
				break;
			case VERTEX0_X:
			case VERTEX0_Y:
			case VERTEX0_Z:
				{
					int nPoint = id - VERTEX0_X;
					ocs[0][nPoint] = readNextLineFloat();
				}
				break;
			case VERTEX1_X:
			case VERTEX1_Y:
			case VERTEX1_Z:
				{
					int nPoint = id - VERTEX0_X;
					ocs[1][nPoint] = readNextLineFloat();
				}
				break;
			case GROUP6:
			case SUBCLASS_MARKER:
			case THICKNESS:
			case TEXT_HEIGHT:
			case TEXT_ROTATION:
			case X_SCALE_FACTOR:
			case OBLIQUE_ANGLE:
			case FLAG:
			case DEFAULT_VALUE:
			case TEXT_STYLE_NAME:
			case TEXT_GENERATION_FLAG:
			case HORIZONTAL_TEXT_JUSTIFY:
			case FIELD_LENGTH:
			case VERTEXL_TEXT_JUSTIFY:
			case EXTRUSION_DIR_X:
			case EXTRUSION_DIR_Y:
			case EXTRUSION_DIR_Z:
			case PROMPT_STRING:
			case TAG_STRING:
				{
					skipNextLine();
				}
				break;
			default: 
				{
					pushCUrrentLine();
					groupCode = null;
					continue;
				}
			}
			
			groupCode = readNextLineString();
		}
	}

	///////////////////////////////////////////////
	// SOLID
	///////////////////////////////////////////////

	private void parseSolid() throws IOException, ParserDXFException
	{
		String	layerName = null;
		float		coordPoint[][] = new float[4][3];
		int		colorIndex = -1;
		
		String groupCode = readNextLineString();

		while (groupCode != null) {
		
			int id = getID(groupCode);
			
			switch (id) {
			case LAYER:
			case SUBCLASS_MARKER:
			case IDX_COLOR:
			case VERTEX0_X:
			case VERTEX0_Y:
			case VERTEX0_Z:
			case VERTEX1_X:
			case VERTEX1_Y:
			case VERTEX1_Z:
			case VERTEX2_X:
			case VERTEX2_Y:
			case VERTEX2_Z:
			case VERTEX3_X:
			case VERTEX3_Y:
			case VERTEX3_Z:
			case THICKNESS:
			case EXTRUSION_DIR_X:
			case EXTRUSION_DIR_Y:
			case EXTRUSION_DIR_Z:
				{
					skipNextLine();
				}
				break;
			default: 
				{
					pushCUrrentLine();
					groupCode = null;
					continue;
				}
			}
			
			groupCode = readNextLineString();
		}
	}

	///////////////////////////////////////////////
	// 3DFACE
	///////////////////////////////////////////////

	private boolean is4Vertices(float point[][]) {
		for (int n=0; n<3; n++) {
			if (point[2][n] != point[3][n])
				return true;
		}
		return false;
	}

	private void parse3DFace() throws IOException, ParserDXFException
	{
		String	layerName = null;
		float		coordPoint[][] = new float[4][3];
		int		colorIndex = -1;
		float		color[] = {1.0f, 1.0f, 1.0f};
		
		String groupCode = readNextLineString();

		while (groupCode != null) {
		
			int id = getID(groupCode);

			switch (id) {
			case LAYER:
				{
					layerName = readNextLineString();
				}
				break;
			case IDX_COLOR:
				{
					colorIndex = readNextLineInteger();
					getColor(colorIndex, color);
				}
				break;
			case VERTEX0_X:
			case VERTEX0_Y:
			case VERTEX0_Z:
			case VERTEX1_X:
			case VERTEX1_Y:
			case VERTEX1_Z:
			case VERTEX2_X:
			case VERTEX2_Y:
			case VERTEX2_Z:
			case VERTEX3_X:
			case VERTEX3_Y:
			case VERTEX3_Z:
				{
					int nVertex = (id - VERTEX0_X) / 3;
					int nPoint = (id - VERTEX0_X) % 3;
					coordPoint[nVertex][nPoint] = readNextLineFloat();
				}
				break;
			case SUBCLASS_MARKER:
			case FLAG:
				{
					skipNextLine();
				}
				break;
			default: 
				{
					pushCUrrentLine();
					groupCode = null;
					continue;
				}
			}
			
			groupCode = readNextLineString();
		}

		ShapeNode shapeNode = getCurrentShapeNode();
		if (shapeNode == null || useSeparatedShapeNode() == true) {
			shapeNode = getShapeNode(layerName);
			if (shapeNode == null) {
				shapeNode = createIdxFaceSetShapeNode();
				shapeNode.setName(layerName);
				addShapeNode(shapeNode);
			}
		}
		setCurrentShapeNode(shapeNode);
	
		int n3DFaceVertices = (is4Vertices(coordPoint) == true) ? 4 : 3;
		for (int n=0; n<n3DFaceVertices; n++)
			addCoordinate(coordPoint[n]);
		int nCoordPoints = getNCurrentCoordinateNodePoints() - n3DFaceVertices;
		for (int n=0; n<n3DFaceVertices; n++)
			addCoordIndex(n + nCoordPoints);
		addCoordIndex(-1);

		addColor(color);
	}

	///////////////////////////////////////////////
	// POLYLINE
	///////////////////////////////////////////////

	private ShapeNode parsePolyLine() throws IOException, ParserDXFException
	{
		String	layerName = null;
		int		colorIndex = -1;
		int		polyLineFlag = 0;
		float		color[] = {1.0f, 1.0f, 1.0f};
		
Debug.message("POLYLINE = " + getCurrentLine());

		ShapeNode shapeNode = getCurrentShapeNode();
		if (shapeNode == null || useSeparatedShapeNode() == true) {
			shapeNode = createIdxFaceSetShapeNode();
			addShapeNode(shapeNode);
			setCurrentShapeNode(shapeNode);
		}
			
		String groupCode = readNextLineString();

		while (groupCode != null) {

			int id = getID(groupCode);

			switch (id) {
			case LAYER:
				{
					layerName = readNextLineString();
					if (useSeparatedShapeNode() == true)
						shapeNode.setName(layerName);
				}
				break;
			case IDX_COLOR:
				{
					colorIndex = readNextLineInteger();
					getColor(colorIndex, color);
				}
				break;
			case FLAG:
				{
					polyLineFlag = readNextLineInteger();
				}
				break;
			case GROUP0:
				{
					String nextLineString = readNextLineString(false);
					switch (getID(nextLineString)) {
					case VERTEX:
						{
							float vertexPoint[] = parseVertex();
							addCoordinate(vertexPoint);
							addCoordIndex(getNCurrentCoordinateNodePoints() - 1);
						}
						break;
					case SEQEND:
						{
						}
						break;
					default: 
						{
							pushCUrrentLine();
							groupCode = null;
							continue;
						}
					}
				}
				break;
			case SEQSTART:				
			case SEQEND:
				{
					skipNextLine();
				}
				break;
			case SUBCLASS_MARKER:
			case VERTEX0_X:
			case VERTEX0_Y:
			case VERTEX0_Z:
			case THICKNESS:
			case STARTING_WIDTH:
			case ENDING_WIDTH:
			case POLYMESH_MVERTEX_COUNT:
			case POLYMESH_NVERTEX_COUNT:
			case SMOOTH_SURFACE_MDENSITY:
			case SMOOTH_SURFACE_NDENSITY:
			case CURVE_SMOOTH_TYPE:
			case EXTRUSION_DIR_X:
			case EXTRUSION_DIR_Y:
			case EXTRUSION_DIR_Z:
			case GROUP6:
				{
					skipNextLine();
				}
				break;
			default: 
				{
					pushCUrrentLine();
					groupCode = null;
					continue;
				}
			}
			
			groupCode = readNextLineString();
		}

		addCoordIndex(-1);
		
		addColor(color);
		
		Debug.message("  SHAPE NODE NAME = " + shapeNode.getName());
		Debug.message("  VERTICES = " + getNCurrentCoordinateNodePoints());
	
		return shapeNode;
	}

	///////////////////////////////////////////////
	// VERTEX
	///////////////////////////////////////////////

	private float[] parseVertex() throws IOException, ParserDXFException
	{
		float		vertex[] = new float[3];
	
		String groupCode = readNextLineString();

		while (groupCode != null) {
		
			int id = getID(groupCode);

			switch (id) {
			case VERTEX0_X:
			case VERTEX0_Y:
			case VERTEX0_Z:
				{
					int nVertex = id - VERTEX0_X;
					vertex[nVertex] = readNextLineFloat();
				}
				break;
			case LAYER:
			case IDX_COLOR:
			case THICKNESS:
			case SUBCLASS_MARKER:
			case STARTING_WIDTH:
			case ENDING_WIDTH:
			case POLYMESH_MVERTEX_COUNT:
			case POLYMESH_NVERTEX_COUNT:
			case SMOOTH_SURFACE_MDENSITY:
			case SMOOTH_SURFACE_NDENSITY:
			case CURVE_FIT_TAN_DIRECTION:
			case FLAG:
			case GROUP6:
				{
					skipNextLine();
				}
				break;
			default: 
				{
					pushCUrrentLine();
					groupCode = null;
					continue;
				}
			}
			
			groupCode = readNextLineString();
		}
		
		return vertex;
	}

	///////////////////////////////////////////////
	// LINE
	///////////////////////////////////////////////

	private ShapeNode parseLine() throws IOException, ParserDXFException
	{
		float		point[][] = new float[2][3];
		String	layerName = null;
		int		colorIndex = -1;
		
Debug.message("LINE = " + getCurrentLine());

		String groupCode = readNextLineString();

		while (groupCode != null) {

			int id = getID(groupCode);

			switch (id) {
			case LAYER:
				{
					layerName = readNextLineString();
				}
				break;
			case IDX_COLOR:
				{
					colorIndex = readNextLineInteger();
				}
				break;
			case VERTEX0_X:
			case VERTEX0_Y:
			case VERTEX0_Z:
				{
					int nPoint = id - VERTEX0_X;
					point[0][nPoint] = readNextLineFloat();
				}
				break;
			case VERTEX1_X:
			case VERTEX1_Y:
			case VERTEX1_Z:
				{
					int nPoint = id - VERTEX1_X;
					point[1][nPoint] = readNextLineFloat();
				}
				break;
			case SUBCLASS_MARKER:
			case THICKNESS:
			case EXTRUSION_DIR_X:
			case EXTRUSION_DIR_Y:
			case EXTRUSION_DIR_Z:
				{
					skipNextLine();
				}
				break;
			default: 
				{
					pushCUrrentLine();
					groupCode = null;
					continue;
				}
			}
			
			groupCode = readNextLineString();
		}
/*
		ShapeNode shapeNode = createIdxLineSetShapeNode();
		shapeNode.setName(layerName);
		addShapeNode(shapeNode);
		setCurrentShapeNode(shapeNode);
		
		addCoordinate(point[0]);
		addCoordinate(point[1]);

		addCoordIndex(0);
		addCoordIndex(1);
		addCoordIndex(-1);
		
		float color[] = {1.0f, 1.0f, 1.0f};
		addColor(color);
		
		Debug.message("  SHAPE NODE NAME = " + shapeNode.getName());
		Debug.message("  VERTICES = " + getNCurrentCoordinateNodePoints());
	
		return shapeNode;
*/		
		return null;
	}


}

