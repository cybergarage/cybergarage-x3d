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

import org.cybergarage.x3d.field.*;

public interface Bounded2DObject 
{
	////////////////////////////////////////////////
	//	BBoxSize
	////////////////////////////////////////////////

	public SFVec2f getBoundingBoxSizeField();
	public void setBoundingBoxSize(float value[]);
	public void setBoundingBoxSize(float x, float y);
	public void setBoundingBoxSize(String value);
	public void getBoundingBoxSize(float value[]);
	public float[] getBoundingBoxSize();

	////////////////////////////////////////////////
	//	BBoxCenter
	////////////////////////////////////////////////

	public SFVec2f getBoundingBoxCenterField();
	public void setBoundingBoxCenter(float value[]);
	public void setBoundingBoxCenter(float x, float y);
	public void setBoundingBoxCenter(String value);
	public void getBoundingBoxCenter(float value[]);
	public float[] getBoundingBoxCenter();

	////////////////////////////////////////////////
	//	updateBoundingBox
	////////////////////////////////////////////////
	
	public void updateBoundingBox();
}
