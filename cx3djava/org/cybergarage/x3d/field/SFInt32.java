/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFInt32.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFInt32 extends Field {

	private IntValue mValue = new IntValue(0); 

	public SFInt32() {
		setType(FieldType.SFINT32);
		setValue(0);
	}

	public SFInt32(SFInt32 value) {
		setType(FieldType.SFINT32);
		setValue(value);
	}

	public SFInt32(int value) {
		setType(FieldType.SFINT32);
		setValue(value);
	}

	public SFInt32(String value) {
		setType(FieldType.SFINT32);
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set*
	////////////////////////////////////////////////

	public void setValue(int value, boolean doShare) {
		synchronized (mValue) {
			mValue.setValue(value);
		}
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(int value) {
		setValue(value, true);
	}

	public void setValue(SFInt32 value, boolean doShare) {
		setValue(value.getValue(), doShare);
	}

	public void setValue(SFInt32 value) {
		setValue(value, true);
	}

	public void setValue(String value) {
		try {
			Float ivalue = new Float(value);
			setValue(ivalue.intValue());
		}
		catch (NumberFormatException e) {}
	}

	public void setValue(Field field, boolean doShare) {
		if (field instanceof SFInt32)
			setValue((SFInt32)field, doShare);
	}

	public void setValue(Field field) {
		setValue(field, true);
	}

	////////////////////////////////////////////////
	//	get*
	////////////////////////////////////////////////

	public int getValue() {
		int value;
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
			mValue = (IntValue)object;
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

	public String toString() {
		Integer value = new Integer(getValue());
		return value.toString();
	}
}