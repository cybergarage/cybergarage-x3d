/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFBool.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFBool extends Field {

	private BoolValue mValue = new BoolValue(true); 

	public SFBool() {
		setType(FieldType.SFBOOL);
		setValue(true);
	}

	public SFBool(SFBool value) {
		setType(FieldType.SFBOOL);
		setValue(value);
	}

	public SFBool(boolean value) {
		setType(FieldType.SFBOOL);
		setValue(value);
	}

	public SFBool(String value) {
		setType(FieldType.SFBOOL);
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set*
	////////////////////////////////////////////////

	public void setValue(boolean value, boolean doShare) {
		synchronized (mValue) {
			mValue.setValue(value);
		}
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(boolean value) {
		 setValue(value, true);
	}

	public void setValue(SFBool value, boolean doShare) {
		setValue(value.getValue(), doShare);
	}

	public void setValue(SFBool value) {
		setValue(value, true);
	}

	public void setValue(String value) {
		if (value.equalsIgnoreCase("TRUE") == true)
			setValue(true);
		else
			setValue(false);
	}

	public void setValue(Field field, boolean doShare) {
		if (field instanceof SFBool)
			setValue((SFBool)field, doShare);
	}

	public void setValue(Field field) {
		setValue(field, true);
	}

	////////////////////////////////////////////////
	//	get*
	////////////////////////////////////////////////

	public boolean getValue() {
		boolean value;
		synchronized (mValue) {
			value = mValue.getValue();
		}
		return value;
	}

	public int getValueCount()
	{
		return 1;
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (BoolValue)object;
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
	//	toString
	////////////////////////////////////////////////

	public String toString() 
	{
		if (getValue() == true)
			return "TRUE";
		return "FALSE";
	}

	public String toXMLString()
	{
		if (getValue() == true)
			return "TRUE";
		return "FALSE";
	}
}