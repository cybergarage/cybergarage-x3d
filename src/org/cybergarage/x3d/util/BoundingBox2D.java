/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : BoundingBox.java
*
*	Revisions:
*
*	12/03/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.util;

public class BoundingBox2D extends Object {

	private float mCenter[]	= new float[2];
	private float mSize[]	= new float[2];

	public BoundingBox2D() {
		clear();
	}

	public void clear() {
		for (int n=0; n<2; n++) {
			mCenter[n] = 0.0f;
			mSize[n] = -1.0f;
		}
	}	
	
	public void setCenter(float center[]) {
		for (int n=0; n<2; n++)
			mCenter[n] = center[n];
	}

	public void setCenter(float x, float y) {
		mCenter[0] = x;
		mCenter[1] = y;
	}

	public void getCenter(float center[]) {
		for (int n=0; n<2; n++)
			center[n] = mCenter[n];
	}

	public float[] getCenter() {
		float center[] = new float[2];
		getCenter(center);
		return center;
	}

	public void setSize(float size[]) {
		for (int n=0; n<2; n++)
			mSize[n] = size[n];
	}
		
	public void setSize(float x, float y) {
		mSize[0] = x;
		mSize[1] = y;
	}

	public void getSize(float size[]) {
		for (int n=0; n<2; n++)
			size[n] = mSize[n];
	}

	public float[] getSize() {
		float size[] = new float[2];
		getSize(size);
		return size;
	}
		
	public void set(float minPos[], float maxPos[]) {
		for (int n=0; n<2; n++) {
			mCenter[n] = (minPos[n] + maxPos[n]) / 2.0f;
			mSize[n] = Math.abs(maxPos[n] - mCenter[n]);
		}
	}
	
	public void getMaxPosition(float pos[]) {
		if (mSize[0] <= 0.0f && mSize[1] <= 0.0f) {
			for (int n=0; n<2; n++)
				pos[n] = Float.MIN_VALUE;
		}
		else {
			for (int n=0; n<2; n++)
				pos[n] = mCenter[n] + mSize[n];
		}
	}

	public void getMinPosition(float pos[]) {
		if (mSize[0] <= 0.0f && mSize[1] <= 0.0f) {
			for (int n=0; n<2; n++)
				pos[n] = Float.MAX_VALUE;
		}
		else {
			for (int n=0; n<2; n++)
				pos[n] = mCenter[n] - mSize[n];
		}
	}
		
	public void addPoint(float pos[]) {
		float minPos[] = new float[2];
		float maxPos[] = new float[2];
		
		getMinPosition(minPos);
		getMaxPosition(maxPos);
		
		for (int n=0; n<2; n++) {
			if (pos[n] < minPos[n])
				minPos[n] = pos[n];
			if (maxPos[n] < pos[n])
				maxPos[n] = pos[n];
		}
		
		set(minPos, maxPos);
	}
	
	public void addPoint(float x, float y, float z) {
		float pos[] = new float[2];
		pos[0] = x;
		pos[1] = y;
		pos[2] = z;
		addPoint(pos);
	}
}
