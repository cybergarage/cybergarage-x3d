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
*	11/18/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public interface BoundedObject 
{
	////////////////////////////////////////////////
	//	BBoxSize
	////////////////////////////////////////////////

	public SFVec3f getBoundingBoxSizeField();
	public void setBoundingBoxSize(float value[]);
	public void setBoundingBoxSize(float x, float y, float z);
	public void setBoundingBoxSize(String value);
	public void getBoundingBoxSize(float value[]);
	public float[] getBoundingBoxSize();

	////////////////////////////////////////////////
	//	BBoxCenter
	////////////////////////////////////////////////

	public SFVec3f getBoundingBoxCenterField();
	public void setBoundingBoxCenter(float value[]);
	public void setBoundingBoxCenter(float x, float y, float z);
	public void setBoundingBoxCenter(String value);
	public void getBoundingBoxCenter(float value[]);
	public float[] getBoundingBoxCenter();

	////////////////////////////////////////////////
	//	updateBoundingBox
	////////////////////////////////////////////////
	
	public void updateBoundingBox();
}
