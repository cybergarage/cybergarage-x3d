/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : SFColorRGBA.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFColorRGBA extends Field {

	private ColorRGBAValue mValue = new ColorRGBAValue(); 

	public SFColorRGBA() {
		setType(FieldType.SFCOLORRGBA);
		setValue(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public SFColorRGBA(SFColor color) {
		setType(FieldType.SFCOLORRGBA);
		setValue(color);
	}

	public SFColorRGBA(float r, float g, float b, float a) {
		setType(FieldType.SFCOLORRGBA);
		setValue(r, g, b, a);
	}

	public SFColorRGBA(float value[]) {
		setType(FieldType.SFCOLORRGBA);
		setValue(value);
	}

	public SFColorRGBA(String value) {
		setType(FieldType.SFCOLORRGBA);
		setValue(value);
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (ColorRGBAValue)object;
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

	public float getAlpha() 
	{
		return mValue.getAlpha();
	}

	public int getValueCount()
	{
		return 4;
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(float r, float g, float b, float a, boolean doShare) 
	{
		mValue.setValue(r, g, b, a);
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(float r, float g, float b, float a) 
	{
		setValue(r, g, b, a, true);
	}

	public void setValue(float value[]) 
	{
		if (value.length < 4)
			return;
		setValue(value[0], value[1], value[2], value[3]);
	}

	public void setValue(SFColorRGBA color, boolean doShare) 
	{
		setValue(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), doShare);
	}

	public void setValue(SFColorRGBA color)
	{
		setValue(color, true);
	}

	public void setValue(String string) 
	{
		mValue.setValue(string);
	}

	public void setValue(Field field, boolean doShare) 
	{
		if (field instanceof SFColorRGBA)
			setValue((SFColorRGBA)field, doShare);
	}

	public void setValue(Field field) 
	{
		setValue(field, true);
	}
	
	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(float r, float g, float b, float a) 
	{
		mValue.add(r, g, b, a);
	}

	public void add(float value[]) 
	{
		mValue.add(value);
	}

	public void add(SFColorRGBA value) 
	{
		add(value.getValue());
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(float r, float g, float b, float a) 
	{
		mValue.sub(r, g, b, a);
	}

	public void sub(float value[]) 
	{
		mValue.sub(value);
	}

	public void sub(SFColorRGBA value) 
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
		return getRed() + " " + getGreen() + " " + getBlue() + " " + getAlpha();
	}
}