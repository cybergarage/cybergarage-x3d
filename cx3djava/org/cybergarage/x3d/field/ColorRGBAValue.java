/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-2000
*
*	File : ColorValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.Serializable;

public class ColorRGBAValue extends Object implements Serializable {

	private float mValue[] = new float[4]; 

	public ColorRGBAValue() 
	{
		setValue(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public ColorRGBAValue(float r, float g, float b, float a) 
	{
		setValue(r, g, b, a);
	}

	public ColorRGBAValue(float value[]) 
	{
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(float r, float g, float b, float a) 
	{
		synchronized (mValue) {
			mValue[0] = r;
			mValue[1] = g;
			mValue[2] = b;
			mValue[3] = a;
		}
	}

	public void setValue(float value[]) 
	{
		if (value.length < 4)
			return;
		setValue(value[0], value[1], value[2], value[3]);
	}

	public void setValue(String string) {
		StringValue value = new StringValue(string);
		String token[] = value.getTokens();
		if (token != null) {
			if (token.length == 4) {
				try {
					Float r = new Float(token[0]);
					Float g = new Float(token[1]);
					Float b = new Float(token[2]);
					Float a = new Float(token[2]);
					setValue(r.floatValue(), g.floatValue(), b.floatValue(), a.floatValue());
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
			value[3] = mValue[3];
		}
	}

	public float[] getValue() 
	{
		float value[] = new float[4];
		getValue(value);
		return value;
	}

	public float getRed() {
		float r;
		synchronized (mValue) {
			r = mValue[0];
		}
		return r;
	}

	public float getGreen() {
		float g;
		synchronized (mValue) {
			g = mValue[1];
		}
		return g;
	}

	public float getBlue() {
		float b;
		synchronized (mValue) {
			b = mValue[2];
		}
		return b;
	}

	public float getAlpha() {
		float a;
		synchronized (mValue) {
			a = mValue[3];
		}
		return a;
	}


	////////////////////////////////////////////////
	//	check
	////////////////////////////////////////////////

	public void checkRange() {
		synchronized (mValue) {
			for (int n=0; n<4; n++) {
				if (1.0f < mValue[n])
					mValue[n] = 1.0f;
				if (mValue[n] < 0.0f)
					mValue[n] = 0.0f;
			}
		}
	}
	
	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(float r, float g, float b, float a) 
	{
		synchronized (mValue) {
			mValue[0] += r;
			mValue[1] += g;
			mValue[2] += b;
			mValue[3] += a;
			mValue[0] /= 2.0f;
			mValue[1] /= 2.0f;
			mValue[2] /= 2.0f;
			mValue[3] /= 2.0f;
		}
		checkRange();
	}

	public void add(float value[]) 
	{
		if (value.length < 4)
			return;
		add(value[0], value[1], value[2], value[3]);
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(float r, float g, float b, float a) 
	{
		synchronized (mValue) {
			mValue[0] -= r;
			mValue[1] -= g;
			mValue[2] -= b;
			mValue[3] -= a;
			mValue[0] /= 2.0f;
			mValue[1] /= 2.0f;
			mValue[2] /= 2.0f;
			mValue[3] /= 2.0f;
		}
		checkRange();
	}

	public void sub(float value[]) 
	{
		if (value.length < 4)
			return;
		sub(value[0], value[1], value[2], value[3]);
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
			mValue[3] *= scale;
		}
		checkRange();
	}
}