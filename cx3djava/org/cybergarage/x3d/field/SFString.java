/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFString.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFString extends Field {

	private StringValue mValue = new StringValue(null); 

	public SFString() {
		setType(FieldType.SFSTRING);
	}

	public SFString(SFString string) {
		setType(FieldType.SFSTRING);
		setValue(string);
	}

	public SFString(String value) {
		setType(FieldType.SFSTRING);
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set*
	////////////////////////////////////////////////

	public void setValue(String value, boolean doShare) {
		synchronized (mValue) {
			mValue.setValue(value);
		}
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(String value) {
		setValue(value, true);
	}

	public void setValue(SFString value, boolean doShare) {
		setValue(value.getValue(), doShare);
	}

	public void setValue(SFString value) {
		setValue(value, true);
	}

	public void setValue(Field field, boolean doShare) {
		if (field instanceof SFString)
			setValue((SFString)field, doShare);
	}

	public void setValue(Field field) {
		setValue(field, true);
	}

	////////////////////////////////////////////////
	//	get*
	////////////////////////////////////////////////
	
	public String getValue() {
		String value;
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
			mValue = (StringValue)object;
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
		String value = getValue();
		if (value == null)
			return new String("\"\""); 
		return "\"" + value + "\"";
	}

	public String toXMLString()
	{
		String value = getValue();
		if (value == null)
			return "";
		return value;
	}
}
