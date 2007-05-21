/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: Grouping2DNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class Grouping2DNode extends Node {

	private String	addChildrenEventIn		= "addChildren";
	private String	removeChildrenEventIn	= "removeChildren";
	private String	childrenExposedField		= "children";
	
	private String	bboxCenterFieldName		= "bboxCenter";
	private String	bboxSizeFieldName		= "bboxSize";

	private MFNode addChildrenField;
	private MFNode removeChildrenField;
	private MFNode childrenField;
	
	private SFVec2f bboxCenterField;
	private SFVec2f bboxSizeField;

	public Grouping2DNode(boolean bAddChildrenField, boolean bAddBBoxField) {
		setHeaderFlag(false);

		if (bAddChildrenField == true) {
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
		
		if (bAddBBoxField == true) {
			// bboxBBoxCenter field
			bboxCenterField = new SFVec2f(0.0f, 0.0f);
			bboxCenterField.setName(bboxCenterFieldName);
			addField(bboxCenterField);

			// bboxSize field
			bboxSizeField = new SFVec2f(-1.0f, -1.0f);
			bboxSizeField.setName(bboxSizeFieldName);
			addField(bboxSizeField);
		}
	}

	public Grouping2DNode() {
		this(true);
	}

	public Grouping2DNode(boolean bAddChildrenField) {
		this(bAddChildrenField, true);
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

	////////////////////////////////////////////////
	//	BBoxSize
	////////////////////////////////////////////////

	public SFVec2f getBoundingBoxSizeField() {
		if (isInstanceNode() == false)
			return bboxSizeField;
		return (SFVec2f)getField(bboxSizeFieldName);
	}

	public void setBoundingBoxSize(float value[]) {
		getBoundingBoxSizeField().setValue(value);
	}
	
	public void setBoundingBoxSize(float x, float y) {
		getBoundingBoxSizeField().setValue(x, y);
	}
	
	public void setBoundingBoxSize(String value) {
		getBoundingBoxSizeField().setValue(value);
	}
	
	public void getBoundingBoxSize(float value[]) {
		getBoundingBoxSizeField().getValue(value);
	}

	public float[] getBoundingBoxSize() {
		float size[] = new float[2];
		getBoundingBoxSize(size);
		return size;
	}

	////////////////////////////////////////////////
	//	BBoxCenter
	////////////////////////////////////////////////

	public SFVec2f getBoundingBoxCenterField() {
		if (isInstanceNode() == false)
			return bboxCenterField;
		return (SFVec2f)getField(bboxCenterFieldName);
	}

	public void setBoundingBoxCenter(float value[]) {
		getBoundingBoxCenterField().setValue(value);
	}
	
	public void setBoundingBoxCenter(float x, float y) {
		getBoundingBoxCenterField().setValue(x, y);
	}
	
	public void setBoundingBoxCenter(String value) {
		getBoundingBoxCenterField().setValue(value);
	}
	
	public void getBoundingBoxCenter(float value[]) {
		getBoundingBoxCenterField().getValue(value);
	}

	public float[] getBoundingBoxCenter() {
		float center[] = new float[2];
		getBoundingBoxCenter(center);
		return center;
	}
}
