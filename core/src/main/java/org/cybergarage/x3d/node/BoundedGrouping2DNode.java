/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: BoundedGrouping2DNode.java
*
*	Revisions:
*
*	12/03/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.field.*;

public abstract class BoundedGrouping2DNode extends Grouping2DNode implements Bounded2DObject
{
	private static final String bboxCenterFieldName	= "bboxCenter";
	private static final String bboxSizeFieldName		= "bboxSize";

	private SFVec2f bboxCenterField;
	private SFVec2f bboxSizeField;

	public BoundedGrouping2DNode() 
	{
		// bboxBBoxCenter field
		bboxCenterField = new SFVec2f(0.0f, 0.0f);
		bboxCenterField.setName(bboxCenterFieldName);
		addField(bboxCenterField);

		// bboxSize field
		bboxSizeField = new SFVec2f(-1.0f, -1.0f);
		bboxSizeField.setName(bboxSizeFieldName);
		addField(bboxSizeField);
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

	////////////////////////////////////////////////
	//	abstract method
	////////////////////////////////////////////////
	
	public void updateBoundingBox() 
	{
		BoundingBox2D bbox = new BoundingBox2D();
		for (Node node=getChildNodes(); node != null; node=node.next())
			Bounded2DNode.updateBoundingBox(node, bbox);
		setBoundingBoxCenter(bbox.getCenter());
		setBoundingBoxSize(bbox.getSize());
	}
}
