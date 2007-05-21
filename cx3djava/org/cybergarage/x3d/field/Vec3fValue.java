/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-2000
*
*	File : Vec3fValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.Serializable;

public class Vec3fValue extends Object implements Serializable {

	private float mValue[] = new float[3]; 

	public Vec3fValue() 
	{
		setValue(0.0f, 0.0f, 0.0f);
	}

	public Vec3fValue(float x, float y, float z) 
	{
		setValue(x, y, z);
	}

	public Vec3fValue(float value[]) 
	{
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(float x, float y, float z) 
	{
		synchronized (mValue) {
			mValue[0] = x;
			mValue[1] = y;
			mValue[2] = z;
		}
	}

	public void setValue(float value[]) 
	{
		if (value.length < 3)
			return;
		setValue(value[0], value[1], value[2]);
	}

	public void setValue(String string) 
	{
		StringValue value = new StringValue(string);
		String token[] = value.getTokens();
		if (token != null) {
			if (token.length == 3) {
				try {
					Float x = new Float(token[0]);
					Float y = new Float(token[1]);
					Float z = new Float(token[2]);
					setValue(x.floatValue(), y.floatValue(), z.floatValue());
				}
				catch (NumberFormatException e) {}
			}
		}
	}

	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public void getValue(float value[]) 
	{
		synchronized (mValue) {
			value[0] = mValue[0];
			value[1] = mValue[1];
			value[2] = mValue[2];
		}
	}

	public float[] getValue() 
	{
		float value[] = new float[3];
		getValue(value);
		return value;
	}

	public float getX() {
		float x;
		synchronized (mValue) {
			x = mValue[0];
		}
		return x;
	}

	public float getY() {
		float y;
		synchronized (mValue) {
			y = mValue[1];
		}
		return y;
	}

	public float getZ() {
		float z;
		synchronized (mValue) {
			z = mValue[2];
		}
		return z;
	}
	
	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(float x, float y, float z) 
	{
		synchronized (mValue) {
			mValue[0] += x;
			mValue[1] += y;
			mValue[2] += z;
		}
	}

	public void add(float value[]) 
	{
		synchronized (mValue) {
			mValue[0] += value[0];
			mValue[1] += value[1];
			mValue[2] += value[2];
		}
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(float x, float y, float z) 
	{
		synchronized (mValue) {
			mValue[0] -= x;
			mValue[1] -= y;
			mValue[2] -= z;
		}
	}

	public void sub(float value[]) 
	{
		synchronized (mValue) {
			mValue[0] -= value[0];
			mValue[1] -= value[1];
			mValue[2] -= value[2];
		}
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public void scale(float scale)
	{
		synchronized (mValue) {
			mValue[0] *= scale;
			mValue[1] *= scale;
			mValue[2] *= scale;
		}
	}

	////////////////////////////////////////////////
	//	invert
	////////////////////////////////////////////////

	public void invert() 
	{
		synchronized (mValue) {
			mValue[0] = -mValue[0];
			mValue[1] = -mValue[1];
			mValue[2] = -mValue[2];
		}
	}

	////////////////////////////////////////////////
	//	scalar
	////////////////////////////////////////////////

	public float getScalar()
	{
		float scalar;
		synchronized (mValue) {
			scalar = (float)Math.sqrt(mValue[0]*mValue[0]+mValue[1]*mValue[1]+mValue[2]*mValue[2]);
		}
		return scalar;
	}

	////////////////////////////////////////////////
	//	normalize
	////////////////////////////////////////////////

	public void normalize()
	{
		float scale = getScalar();
		if (scale != 0.0f) {
			synchronized (mValue) {
				mValue[0] /= scale;
				mValue[1] /= scale;
				mValue[2] /= scale;
			}
		}
	}
}