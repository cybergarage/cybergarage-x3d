/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : GeometryNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.android;

import org.cybergarage.x3d.node.Node;

import javax.microedition.khronos.opengles.GL10;

public abstract class GeometryNodeObject {

	////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////
	
	public GeometryNodeObject() 
	{
	}

	////////////////////////////////////////////////
	//	Methods
	////////////////////////////////////////////////

	public abstract void draw(GL10 gl, Node node);
}

