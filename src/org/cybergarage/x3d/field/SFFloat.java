/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFFloat.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFFloat extends Field {

	private FloatValue mValue = new FloatValue(0.0f); 

	public SFFloat() {
		setType(FieldType.SFFLOAT);
		setValue(0.0f);
	}

	public SFFloat(SFFloat value) {
		setType(FieldType.SFFLOAT);
		setValue(value);
	}

	public SFFloat(float value) {
		setType(FieldType.SFFLOAT);
		setValue(value);
	}

	public SFFloat(String value) {
		setType(FieldType.SFFLOAT);
		setValue(value);
	}
	
	////////////////////////////////////////////////
	//	set*
	////////////////////////////////////////////////

	public void setValue(float value, boolean doShare) {
		synchronized (mValue) {
			mValue.setValue(value);
		}
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(float value) {
		setValue(value, true);
	}

	public void setValue(SFFloat value, boolean doShare) {
		setValue(value.getValue(), doShare);
	}

	public void setValue(SFFloat value) {
		setValue(value, true);
	}

	public void setValue(String value) {
		try {
			Float fvalue = new Float(value);
			setValue(fvalue.floatValue());
		}
		catch (NumberFormatException e) {}
	}

	public void setValue(Field field, boolean doShare) {
		if (field instanceof SFFloat)
			setValue((SFFloat)field, doShare);
	}

	public void setValue(Field field) {
		setValue(field, true);
	}

	////////////////////////////////////////////////
	//	get*
	////////////////////////////////////////////////

	public float getValue() {
		float value;
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
			mValue = (FloatValue)object;
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
		Float value = new Float(getValue());
		return value.toString();
	}
}