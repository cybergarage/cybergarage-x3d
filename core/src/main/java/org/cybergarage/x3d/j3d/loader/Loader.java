/******************************************************************
*
*	VRML97Saver for CyberX3D
*
*	Copyright (C) Satoshi Konno 1999-2002
*
*	File:	VRML97Loader.java
*
*	11/25/02
*		- Josh Richmond <JRICHMON@mdrobotics.ca>
*		- Added a canvas so textures load correctly.
*
******************************************************************/

package org.cybergarage.x3d.j3d.loader;

import java.io.*;
import java.net.*;
import javax.media.j3d.*;
import java.awt.*;
import com.sun.j3d.utils.universe.*;

import org.cybergarage.x3d.SceneGraph;
import org.cybergarage.x3d.j3d.*;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.LoaderBase;

public abstract class Loader extends LoaderBase
{
	///////////////////////////////////////////////
	// Constructor
	///////////////////////////////////////////////

	public Loader() 
	{
		SceneGraph sg = getSceneGraph();
		SceneGraphJ3dObject sgObject = new SceneGraphJ3dObject(sg);
		sg.setObject(sgObject);		
	}

	public Loader(Canvas3D canvas3D)
	{
		SceneGraph sg = getSceneGraph();
		SceneGraphJ3dObject sgObject = new SceneGraphJ3dObject(canvas3D, sg, false);
		sg.setObject(sgObject);
		sgObject.removeBranchGroup();	
	}


	///////////////////////////////////////////////
	// SceneGraph
	///////////////////////////////////////////////

	private SceneGraph sg = new SceneGraph();
	
	public SceneGraph getSceneGraph()
	{
		return sg;
	}
	
	///////////////////////////////////////////////
	// BranchGroup/Scene
	///////////////////////////////////////////////

	public BranchGroup getBranchGroup() 
	{
		SceneGraph sg = getSceneGraph();
		SceneGraphJ3dObject sgObj = (SceneGraphJ3dObject)sg.getObject();
		
		// 11/25/02 - Josh Richmond 
		sgObj.getBranchGroup().detach(); 
		
		return sgObj.getBranchGroup();
	}

	public Scene getScene() 
	{
		SceneBase sceneBase = new SceneBase();
		BranchGroup bg = getBranchGroup();
		sceneBase.setSceneGroup(bg);
		return sceneBase;
	}

	///////////////////////////////////////////////
	// load
	///////////////////////////////////////////////

	public Scene load(Reader reader)
	{
		return null;
	}

	private void setJava3DObjet(SceneGraph sg)
	{
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D mCanvas3D = new Canvas3D(config);
		SceneGraph mSceneGraph = new SceneGraph();
		SceneGraphJ3dObject mSceneGraphObject = new SceneGraphJ3dObject(mCanvas3D, sg);
		sg.setObject(mSceneGraphObject);
	}
		
	public Scene load(String fileName)
	{
		SceneGraph sg = getSceneGraph();
		
		// 11/25/02 - Josh Richmond 
		setJava3DObjet(sg);
		
		sg.load(fileName);
		return getScene();	
	}
	
	public Scene load(URL url)
	{
		SceneGraph sg = getSceneGraph();
		
		// 11/25/02 - Josh Richmond 
		setJava3DObjet(sg);
		
		sg.load(url);
		return getScene();	
	}

	///////////////////////////////////////////////
	// Output
	///////////////////////////////////////////////

	public void print()
	{
		getSceneGraph().print();
	}
}

