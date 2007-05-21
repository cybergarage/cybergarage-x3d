/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: GroupingNode.java
*
*	Revisions:
*
*	11/18/02
*		- Changed the super class from GroupingNode to GroupingBoundedNode.
*
*	11/14/02
*		- Exported the follwing fields to BoundedNode.
*			bboxCenter, bboxSize
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class GroupingNode extends Node {

	private static final String addChildrenEventIn		= "addChildren";
	private static final String removeChildrenEventIn	= "removeChildren";
	private static final String childrenExposedField	= "children";
	
	private MFNode addChildrenField;
	private MFNode removeChildrenField;
	private MFNode childrenField;

	public GroupingNode() 
	{
		setHeaderFlag(false);

		// addChildren eventout field
		addChildrenField = new MFNode();
		addEventIn(addChildrenEventIn, addChildrenField);

		// removeChildren eventout field
		removeChildrenField = new MFNode();
		addEventIn(removeChildrenEventIn, removeChildrenField);

		// children exposedField
		childrenField = new MFNode();
		addExposedField(childrenExposedField, childrenField);
	}

	////////////////////////////////////////////////
	//	addChildren / removeChildren
	////////////////////////////////////////////////

	public MFNode getAddChildrenField() {
		if (isInstanceNode() == false)
			return addChildrenField;
		return (MFNode)getEventIn(addChildrenEventIn);
	}

	public MFNode getRemoveChildrenField() {
		if (isInstanceNode() == false)
			return removeChildrenField;
		return (MFNode)getEventIn(removeChildrenEventIn);
	}

	////////////////////////////////////////////////
	//	children
	////////////////////////////////////////////////

	public MFNode getChildrenField() {
		if (isInstanceNode() == false)
			return childrenField;
		return (MFNode)getExposedField(childrenExposedField);
	}

	public void updateChildrenField() {
		MFNode childrenField = getChildrenField();
		childrenField.removeAllValues();
		for (Node node=getChildNodes(); node != null; node=node.next()) 
			childrenField.addValue(node);
	}
}
