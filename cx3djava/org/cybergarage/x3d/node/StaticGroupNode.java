/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : StaticGroupNode.java
*
*	Revisions:
*
*	11/20/02
*		- The first release.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class StaticGroupNode extends BoundedGroupingNode {

	public StaticGroupNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.STATICGROUP);
	}

	public StaticGroupNode(StaticGroupNode node) {
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
