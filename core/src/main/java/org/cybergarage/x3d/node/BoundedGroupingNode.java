/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: BoundedGroupingNode.java
*
*	Revisions:
*
*	11/18/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.field.*;

public abstract class BoundedGroupingNode extends GroupingNode implements BoundedObject
{
	private static final String bboxCenterFieldName	= "bboxCenter";
	private static final String bboxSizeFieldName		= "bboxSize";

	private SFVec3f bboxCenterField;
	private SFVec3f bboxSizeField;

	public BoundedGroupingNode() 
	{
		// bboxBBoxCenter field
		bboxCenterField = new SFVec3f(0.0f, 0.0f, 0.0f);
		bboxCenterField.setName(bboxCenterFieldName);
		addField(bboxCenterField);

		// bboxSize field
		bboxSizeField = new SFVec3f(-1.0f, -1.0f, -1.0f);
		bboxSizeField.setName(bboxSizeFieldName);
		addField(bboxSizeField);
	}

	////////////////////////////////////////////////
	//	BBoxSize
	////////////////////////////////////////////////

	public SFVec3f getBoundingBoxSizeField() {
		if (isInstanceNode() == false)
			return bboxSizeField;
		return (SFVec3f)getField(bboxSizeFieldName);
	}

	public void setBoundingBoxSize(float value[]) {
		getBoundingBoxSizeField().setValue(value);
	}
	
	public void setBoundingBoxSize(float x, float y, float z) {
		getBoundingBoxSizeField().setValue(x, y, z);
	}
	
	public void setBoundingBoxSize(String value) {
		getBoundingBoxSizeField().setValue(value);
	}
	
	public void getBoundingBoxSize(float value[]) {
		getBoundingBoxSizeField().getValue(value);
	}

	public float[] getBoundingBoxSize() {
		float size[] = new float[3];
		getBoundingBoxSize(size);
		return size;
	}

	////////////////////////////////////////////////
	//	BBoxCenter
	////////////////////////////////////////////////

	public SFVec3f getBoundingBoxCenterField() {
		if (isInstanceNode() == false)
			return bboxCenterField;
		return (SFVec3f)getField(bboxCenterFieldName);
	}

	public void setBoundingBoxCenter(float value[]) {
		getBoundingBoxCenterField().setValue(value);
	}
	
	public void setBoundingBoxCenter(float x, float y, float z) {
		getBoundingBoxCenterField().setValue(x, y, z);
	}
	
	public void setBoundingBoxCenter(String value) {
		getBoundingBoxCenterField().setValue(value);
	}
	
	public void getBoundingBoxCenter(float value[]) {
		getBoundingBoxCenterField().getValue(value);
	}

	public float[] getBoundingBoxCenter() {
		float center[] = new float[3];
		getBoundingBoxCenter(center);
		return center;
	}

	////////////////////////////////////////////////
	//	abstract method
	////////////////////////////////////////////////
	
	public void updateBoundingBox() 
	{
		BoundingBox bbox = new BoundingBox();
		for (Node node=getChildNodes(); node != null; node=node.next())
			BoundedNode.updateBoundingBox(node, bbox);
		setBoundingBoxCenter(bbox.getCenter());
		setBoundingBoxSize(bbox.getSize());
	}
}
