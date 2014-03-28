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

public class GroupNode extends BoundedGroupingNode {

	public GroupNode() {
		super();

		setHeaderFlag(false);
		setType(NodeType.GROUP);
	}

	public GroupNode(GroupNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isCommonNode() || node.isBindableNode() ||node.isInterpolatorNode() || node.isSensorNode() || node.isGroupingNode() || node.isSpecialGroupNode())
			return true;
		else
			return false;
	}

	public void initialize() 
	{
		super.initialize();
		updateChildrenField();
		updateBoundingBox();
	}

	public void uninitialize() 
	{
	}

	public void update() 
	{
		//updateChildrenField();
		//updateBoundingBox();
	}
}
