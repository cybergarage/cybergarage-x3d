/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Group.java
*
*	Revisions:
*
*	11/18/02
*		- Changed the super class from GroupingNode to BoundedGroupingNode.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class SceneNode extends Node {

	public SceneNode() 
	{
		super();

		setHeaderFlag(false);
		setType(NodeType.SCENE);
	}

	public SceneNode(SceneNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node)
	{
			return true;
	}

	public void initialize() 
	{
		super.initialize();
	}

	public void uninitialize() 
	{
	}

	public void update() 
	{
	}
}
