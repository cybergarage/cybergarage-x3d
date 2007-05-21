/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : RotationValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.Serializable;

public class RotationValue extends Object implements Serializable {

	private Vec3fValue	mVector	= new Vec3fValue(0.0f, 1.0f, 0.0f);
	private FloatValue	mAngle	= new FloatValue(0.0f);

	public RotationValue() 
	{
		setValue(0.0f, 0.0f, 1.0f, 0.0f);
	}

	public RotationValue(float x, float y, float z, float rot) 
	{
		setValue(x, y, z, rot);
	}

	public RotationValue(float value[]) 
	{
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(float x, float y, float z, float rot) 
	{
		mVector.setValue(x, y, z);
		mVector.normalize();
		setAngle(rot);
	}

	public void setValue(float value[]) 
	{
		setValue(value[0], value[1], value[2], value[3]);
	}

	public void setAngle(float angle) 
	{
		synchronized (mAngle) {
			mAngle.setValue(angle);
		}
	}

	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public void getValue(float value[]) {
		mVector.getValue(value);
		value[3] = getAngle();
	}

	public float[] getValue() {
		float value[] = new float[4];
		getValue(value);
		return value;
	}

	public void getVector(float vector[]) {
		mVector.getValue(vector);
	}

	public float[] getVector() {
		float vector[] = new float[3];
		mVector.getValue(vector);
		return vector;
	}

	float getX() {
		return mVector.getX();
	}

	float getY() {
		return mVector.getY();
	}

	float getZ() {
		return mVector.getZ();
	}

	public float getAngle() {
		float angle;
		synchronized (mAngle) {
			angle = mAngle.getValue();
		}
		return angle;
	}
}