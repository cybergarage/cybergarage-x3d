/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Vec2dValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.Serializable;

public class Vec2dValue extends Object implements Serializable {

	private double mValue[] = new double[3]; 

	public Vec2dValue() 
	{
		setValue(0.0, 0.0);
	}

	public Vec2dValue(double x, double y) 
	{
		setValue(x, y);
	}

	public Vec2dValue(double value[]) 
	{
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(double x, double y) 
	{
		synchronized (mValue) {
			mValue[0] = x;
			mValue[1] = y;
		}
	}

	public void setValue(double value[]) 
	{
		if (value.length < 2)
			return;
		setValue(value[0], value[1]);
	}

	public void setValue(String string) 
	{
		StringValue value = new StringValue(string);
		String token[] = value.getTokens();
		if (token != null) {
			if (token.length == 3) {
				try {
					Double x = new Double(token[0]);
					Double y = new Double(token[1]);
					setValue(x.doubleValue(), y.doubleValue());
				}
				catch (NumberFormatException e) {}
			}
		}
	}

	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public void getValue(double value[]) 
	{
		synchronized (mValue) {
			value[0] = mValue[0];
			value[1] = mValue[1];
		}
	}

	public double[] getValue() 
	{
		double value[] = new double[2];
		getValue(value);
		return value;
	}

	public double getX() {
		double x;
		synchronized (mValue) {
			x = mValue[0];
		}
		return x;
	}

	public double getY() {
		double y;
		synchronized (mValue) {
			y = mValue[1];
		}
		return y;
	}

	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(double x, double y) 
	{
		synchronized (mValue) {
			mValue[0] += x;
			mValue[1] += y;
		}
	}

	public void add(double value[]) 
	{
		synchronized (mValue) {
			mValue[0] += value[0];
			mValue[1] += value[1];
		}
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(double x, double y) 
	{
		synchronized (mValue) {
			mValue[0] -= x;
			mValue[1] -= y;
		}
	}

	public void sub(double value[]) 
	{
		synchronized (mValue) {
			mValue[0] -= value[0];
			mValue[1] -= value[1];
		}
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public void scale(double scale)
	{
		synchronized (mValue) {
			mValue[0] *= scale;
			mValue[1] *= scale;
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
		}
	}

	////////////////////////////////////////////////
	//	scalar
	////////////////////////////////////////////////

	public double getScalar()
	{
		double scalar;
		synchronized (mValue) {
			scalar = (double)Math.sqrt(mValue[0]*mValue[0]+mValue[1]*mValue[1]);
		}
		return scalar;
	}

	////////////////////////////////////////////////
	//	normalize
	////////////////////////////////////////////////

	public void normalize()
	{
		double scale = getScalar();
		if (scale != 0.0f) {
			synchronized (mValue) {
				mValue[0] /= scale;
				mValue[1] /= scale;
			}
		}
	}
}