/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFColor.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFColor extends Field {

	private ColorValue mValue = new ColorValue(); 

	public SFColor() {
		setType(FieldType.SFCOLOR);
		setValue(1.0f, 1.0f, 1.0f);
	}

	public SFColor(SFColor color) {
		setType(FieldType.SFCOLOR);
		setValue(color);
	}

	public SFColor(float r, float g, float b) {
		setType(FieldType.SFCOLOR);
		setValue(r, g, b);
	}

	public SFColor(float value[]) {
		setType(FieldType.SFCOLOR);
		setValue(value);
	}

	public SFColor(String value) {
		setType(FieldType.SFCOLOR);
		setValue(value);
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (ColorValue)object;
		}
	}

	public Object getObject() {
		Object object;
		synchronized (mValue) {
			object = mValue;
		}
		return object;
	}
	
	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public void getValue(float value[]) 
	{
		mValue.getValue(value);
	}

	public float[] getValue() 
	{
		return mValue.getValue();
	}

	public float getRed() 
	{
		return mValue.getRed();
	}

	public float getGreen() 
	{
		return mValue.getGreen();
	}

	public float getBlue() 
	{
		return mValue.getBlue();
	}

	public int getValueCount()
	{
		return 3;
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(float r, float g, float b, boolean doShare) 
	{
		mValue.setValue(r, g, b);
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(float r, float g, float b) 
	{
		setValue(r, g, b, true);
	}

	public void setValue(float value[]) 
	{
		if (value.length < 3)
			return;
		setValue(value[0], value[1], value[2]);
	}

	public void setValue(SFColor color, boolean doShare) 
	{
		setValue(color.getRed(), color.getGreen(), color.getBlue(), doShare);
	}

	public void setValue(SFColor color)
	{
		setValue(color, true);
	}

	public void setValue(String string) 
	{
		mValue.setValue(string);
	}

	public void setValue(Field field, boolean doShare) 
	{
		if (field instanceof SFColor)
			setValue((SFColor)field, doShare);
	}

	public void setValue(Field field) 
	{
		setValue(field, true);
	}
	
	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(float r, float g, float b) 
	{
		mValue.add(r, g, b);
	}

	public void add(float value[]) 
	{
		mValue.add(value);
	}

	public void add(SFColor value) 
	{
		add(value.getValue());
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(float r, float g, float b) 
	{
		mValue.sub(r, g, b);
	}

	public void sub(float value[]) 
	{
		mValue.sub(value);
	}

	public void sub(SFColor value) 
	{
		sub(value.getValue());
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public void scale(float scale) 
	{
		mValue.scale(scale);
	}
	
	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return getRed() + " " + getGreen() + " " + getBlue();
	}
}