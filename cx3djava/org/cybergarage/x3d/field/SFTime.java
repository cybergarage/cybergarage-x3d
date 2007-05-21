/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFTime.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFTime extends Field {

	private DoubleValue mValue = new DoubleValue(0); 
	
	public SFTime() {
		setType(FieldType.SFTIME);
		setValue(-1);
	}

	public SFTime(SFTime time) {
		setType(FieldType.SFTIME);
		setValue(time);
	}

	public SFTime(double value) {
		setType(FieldType.SFTIME);
		setValue(value);
	}

	public SFTime(String value) {
		setType(FieldType.SFTIME);
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set*
	////////////////////////////////////////////////
	
	public void setValue(double value, boolean doShare) {
		synchronized (mValue) {
			mValue.setValue(value);
		}
		if (doShare == true)
			postShareField(this);
	}
	
	public void setValue(double value) {
		setValue(value, true);
	}

	public void setValue(SFTime value, boolean doShare) {
		setValue(value.getValue(), doShare);
	}

	public void setValue(SFTime value) {
		setValue(value, true);
	}

	public void setValue(String value) {
		try {
			Double dvalue = new Double(value);
			setValue(dvalue.doubleValue());
		}
		catch (NumberFormatException e) {}
	}

	public void setValue(Field field, boolean doShare) {
		if (field instanceof SFTime)
			setValue((SFTime)field);
	}

	public void setValue(Field field) {
		setValue(field, true);
	}

	////////////////////////////////////////////////
	//	get*
	////////////////////////////////////////////////

	public double getValue() {
		double value;
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
			mValue = (DoubleValue)object;
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
		Double value = new Double(getValue());
		return value.toString();
	}
}