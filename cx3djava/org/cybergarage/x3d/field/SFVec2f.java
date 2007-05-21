/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFVec2f.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFVec2f extends Field {

	private Vec2fValue mVector = new Vec2fValue(); 

	public SFVec2f() {
		setType(FieldType.SFVEC2F);
		setValue(0.0f, 0.0f);
	}

	public SFVec2f(SFVec2f vector) {
		this();
		setValue(vector);
	}

	public SFVec2f(float x, float y) {
		this();
		setValue(x, y);
	}

	public SFVec2f(float value[]) {
		this();
		setValue(value);
	}

	public SFVec2f(String value) {
		this();
		setValue(value);
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mVector) {
			mVector = (Vec2fValue)object;
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

	public void getValue(float value[]) 
	{
		mVector.getValue(value);
	}

	public float[] getValue() 
	{
		return mVector.getValue();
	}

	public float getX() 
	{
		return mVector.getX();
	}

	public float getY() 
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

	public void setValue(float x, float y, boolean doShare) 
	{
		mVector.setValue(x, y);
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(float x, float y) 
	{
		setValue(x, y, true);
	}

	public void setValue(float value[]) 
	{
		if (value.length < 2)
			return;
		setValue(value[0], value[1]);
	}

	public void setValue(SFVec2f vector, boolean doShare) 
	{
		setValue(vector.getX(), vector.getY(), doShare);
	}

	public void setValue(SFVec2f vector) 
	{
		setValue(vector, true);
	}

	public void setValue(String string) 
	{
		mVector.setValue(string);
	}

	public void setValue(Field field, boolean doShare) 
	{
		if (field instanceof SFVec2f)
			setValue((SFVec2f)field, doShare);
	}

	public void setValue(Field field) 
	{
		setValue(field, true);
	}

	public void setX(float x) {
		setValue(x, getY());
	}

	public void setY(float y) {
		setValue(getX(), y);
	}

	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(float x, float y) 
	{
		mVector.add(x, y);
	}

	public void add(float value[]) 
	{
		mVector.add(value);
	}

	public void add(SFVec2f value) {
		add(value.getValue());
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(float x, float y) 
	{
		mVector.sub(x, y);
	}

	public void sub(float value[]) 
	{
		mVector.sub(value);
	}

	public void sub(SFVec2f value) 
	{
		sub(value.getValue());
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public void scale(float scale) 
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

	public float getScalar()
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