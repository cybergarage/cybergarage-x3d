/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Vec3dValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.Serializable;

public class Vec3dValue extends Object implements Serializable {

	private double mValue[] = new double[3]; 

	public Vec3dValue() 
	{
		setValue(0.0, 0.0, 0.0);
	}

	public Vec3dValue(double x, double y, double z) 
	{
		setValue(x, y, z);
	}

	public Vec3dValue(double value[]) 
	{
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(double x, double y, double z) 
	{
		synchronized (mValue) {
			mValue[0] = x;
			mValue[1] = y;
			mValue[2] = z;
		}
	}

	public void setValue(double value[]) 
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
					setValue(x.doubleValue(), y.doubleValue(), z.doubleValue());
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
			value[2] = mValue[2];
		}
	}

	public double[] getValue() 
	{
		double value[] = new double[3];
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

	public double getZ() {
		double z;
		synchronized (mValue) {
			z = mValue[2];
		}
		return z;
	}
	
	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(double x, double y, double z) 
	{
		synchronized (mValue) {
			mValue[0] += x;
			mValue[1] += y;
			mValue[2] += z;
		}
	}

	public void add(double value[]) 
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

	public void sub(double x, double y, double z) 
	{
		synchronized (mValue) {
			mValue[0] -= x;
			mValue[1] -= y;
			mValue[2] -= z;
		}
	}

	public void sub(double value[]) 
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

	public void scale(double scale)
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

	public double getScalar()
	{
		double scalar;
		synchronized (mValue) {
			scalar = (double)Math.sqrt(mValue[0]*mValue[0]+mValue[1]*mValue[1]+mValue[2]*mValue[2]);
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
				mValue[2] /= scale;
			}
		}
	}
}