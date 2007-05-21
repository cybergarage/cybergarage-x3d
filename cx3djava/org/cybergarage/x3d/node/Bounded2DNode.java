/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: Bounded2DNode.java
*
*	Revisions:
*
*	12/02/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.field.*;

public abstract class Bounded2DNode extends Node implements Bounded2DObject
{
	private static final String bboxCenterFieldName	= "bboxCenter";
	private static final String bboxSizeFieldName		= "bboxSize";

	private SFVec2f bboxCenterField;
	private SFVec2f bboxSizeField;

	public Bounded2DNode() 
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
	
	public final static void updateBoundingBox(Node node, BoundingBox2D bbox) 
	{
		if (node instanceof Bounded2DNode) {
			Bounded2DNode b2dNode = (Bounded2DNode)node;
			b2dNode.updateBoundingBox();

			float bboxCenter[]	= new float[2];
			float bboxSize[]	= new float[2];
			float point[]		= new float[2];

			b2dNode.getBoundingBoxCenter(bboxCenter);
			b2dNode.getBoundingBoxSize(bboxSize);
						
			if (bboxSize[0] >= 0.0f && bboxSize[1] >= 0.0f) {
				SFMatrix mx = b2dNode.getTransformMatrix();
				for (int n=0; n<4; n++) {
					point[0] = (n < 4)			? bboxCenter[0] - bboxSize[0] : bboxCenter[0] + bboxSize[0];
					point[1] = ((n % 2) != 0)	? bboxCenter[1] - bboxSize[1] : bboxCenter[1] + bboxSize[1];
					mx.multi(point);
					bbox.addPoint(point);
				}
			}
		}
		
		for (Node cnode=node.getChildNodes(); cnode != null; cnode=cnode.next())
			updateBoundingBox(cnode, bbox);
	}
	
	public void updateBoundingBox() {
		BoundingBox2D bbox = new BoundingBox2D();
		for (Node node=getChildNodes(); node != null; node=node.next())
			updateBoundingBox(node, bbox);
		setBoundingBoxCenter(bbox.getCenter());
		setBoundingBoxSize(bbox.getSize());
	}
}
