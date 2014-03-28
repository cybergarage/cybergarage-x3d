/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFVec2d.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFVec2d extends Field {

	private Vec2dValue mVector = new Vec2dValue(); 

	public SFVec2d() {
		setType(FieldType.SFVEC2D);
		setValue(0.0, 0.0);
	}

	public SFVec2d(SFVec2d vector) {
		this();
		setValue(vector);
	}

	public SFVec2d(double x, double y) {
		this();
		setValue(x, y);
	}

	public SFVec2d(double value[]) {
		this();
		setValue(value);
	}

	public SFVec2d(String value) {
		this();
		setValue(value);
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mVector) {
			mVector = (Vec2dValue)object;
		}
	}

	public Object getObject() {
		Object object;
		synchronized (mVector) {
			object = mVector;
		}
		return object;
	}
	
	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public void getValue(double value[]) 
	{
		mVector.getValue(value);
	}

	public double[] getValue() 
	{
		return mVector.getValue();
	}

	public double getX() 
	{
		return mVector.getX();
	}

	public double getY() 
	{
		return mVector.getY();
	}

	public int getValueCount()
	{
		return 2;
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(double x, double y, boolean doShare) 
	{
		mVector.setValue(x, y);
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(double x, double y) 
	{
		setValue(x, y, true);
	}

	public void setValue(double value[]) 
	{
		if (value.length < 2)
			return;
		setValue(value[0], value[1]);
	}

	public void setValue(SFVec2d vector, boolean doShare) 
	{
		setValue(vector.getX(), vector.getY(), doShare);
	}

	public void setValue(SFVec2d vector) 
	{
		setValue(vector, true);
	}

	public void setValue(String string) 
	{
		mVector.setValue(string);
	}

	public void setValue(Field field, boolean doShare) 
	{
		if (field instanceof SFVec2d)
			setValue((SFVec2d)field, doShare);
	}

	public void setValue(Field field) 
	{
		setValue(field, true);
	}

	public void setX(double x) {
		setValue(x, getY());
	}

	public void setY(double y) {
		setValue(getX(), y);
	}

	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(double x, double y) 
	{
		mVector.add(x, y);
	}

	public void add(double value[]) 
	{
		mVector.add(value);
	}

	public void add(SFVec2d value) {
		add(value.getValue());
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(double x, double y) 
	{
		mVector.sub(x, y);
	}

	public void sub(double value[]) 
	{
		mVector.sub(value);
	}

	public void sub(SFVec2d value) 
	{
		sub(value.getValue());
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public void scale(double scale) 
	{
		mVector.scale(scale);
	}

	////////////////////////////////////////////////
	//	invert
	////////////////////////////////////////////////

	public void invert() 
	{
		mVector.invert();
	}

	////////////////////////////////////////////////
	//	scalar
	////////////////////////////////////////////////

	public double getScalar()
	{
		return mVector.getScalar();
	}

	////////////////////////////////////////////////
	//	normalize
	////////////////////////////////////////////////

	public void normalize()
	{
		mVector.normalize();
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return getX() + " " + getY();
	}
}