/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: BoundedNode.java
*
*	Revisions:
*
*	11/14/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.field.*;

public abstract class BoundedNode extends Node implements BoundedObject
{
	private static final String bboxCenterFieldName	= "bboxCenter";
	private static final String bboxSizeFieldName		= "bboxSize";

	private SFVec3f bboxCenterField;
	private SFVec3f bboxSizeField;

	public BoundedNode(boolean bAddBBoxField) 
	{
		setHeaderFlag(false);

		// bboxBBoxCenter field
		bboxCenterField = new SFVec3f(0.0f, 0.0f, 0.0f);
		bboxCenterField.setName(bboxCenterFieldName);
		addField(bboxCenterField);

		bboxSizeField = new SFVec3f(-1.0f, -1.0f, -1.0f);
		bboxSizeField.setName(bboxSizeFieldName);
		addField(bboxSizeField);
	}

	public BoundedNode() 
	{
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
	
	public final static void updateBoundingBox(Node node, BoundingBox bbox) 
	{
		if (node.isGeometry3DNode() == true) {
			Geometry3DNode gnode = (Geometry3DNode)node;
			gnode.updateBoundingBox();

			float bboxCenter[]	= new float[3];
			float bboxSize[]	= new float[3];
			float point[]		= new float[3];

			gnode.getBoundingBoxCenter(bboxCenter);
			gnode.getBoundingBoxSize(bboxSize);
						
			if (bboxSize[0] >= 0.0f && bboxSize[1] >= 0.0f && bboxSize[2] >= 0.0f) {
				SFMatrix mx = gnode.getTransformMatrix();
				for (int n=0; n<8; n++) {
					point[0] = (n < 4)			? bboxCenter[0] - bboxSize[0] : bboxCenter[0] + bboxSize[0];
					point[1] = ((n % 2) != 0)	? bboxCenter[1] - bboxSize[1] : bboxCenter[1] + bboxSize[1];
					point[2] = ((n % 4) < 2)	? bboxCenter[2] - bboxSize[2] : bboxCenter[2] + bboxSize[2];
					mx.multi(point);
					bbox.addPoint(point);
				}
			}
		}
		
		for (Node cnode=node.getChildNodes(); cnode != null; cnode=cnode.next())
			updateBoundingBox(cnode, bbox);
	}
	
	public void updateBoundingBox() {
		BoundingBox bbox = new BoundingBox();
		for (Node node=getChildNodes(); node != null; node=node.next())
			updateBoundingBox(node, bbox);
		setBoundingBoxCenter(bbox.getCenter());
		setBoundingBoxSize(bbox.getSize());
	}
}
