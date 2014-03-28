/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ProxyNode.java
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

public class ProxyNode extends BoundedGroupingNode 
{
	public ProxyNode() {
		super();

		setHeaderFlag(false);
		setType(NodeType.PROXY);
	}

	public ProxyNode(ProxyNode node) {
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

	public void initialize() {
		super.initialize();
		updateChildrenField();
		updateBoundingBox();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateChildrenField();
		//updateBoundingBox();
	}
}
