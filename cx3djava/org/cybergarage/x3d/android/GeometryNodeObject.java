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
import org.cybergarage.x3d.node.BoxNode;

import javax.microedition.khronos.opengles.GL10;

public class GeometryNodeObject {

	////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////
	
	public GeometryNodeObject() 
	{
	}

	////////////////////////////////////////////////
	//	Methods
	////////////////////////////////////////////////

	final static public void draw(GL10 gl, Node node)
	{
		if (node instanceof BoxNode)
			BoxNodeObject.draw(gl, node);
	}
}

