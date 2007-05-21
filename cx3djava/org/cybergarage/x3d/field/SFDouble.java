/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFDouble.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFDouble extends Field {

	private DoubleValue mValue = new DoubleValue(0.0); 

	public SFDouble() {
		setType(FieldType.SFDOUBLE);
		setValue(0.0f);
	}

	public SFDouble(SFDouble value) {
		setType(FieldType.SFDOUBLE);
		setValue(value);
	}

	public SFDouble(double value) {
		setType(FieldType.SFDOUBLE);
		setValue(value);
	}

	public SFDouble(String value) {
		setType(FieldType.SFDOUBLE);
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

	public void setValue(SFDouble value, boolean doShare) {
		setValue(value.getValue(), doShare);
	}

	public void setValue(SFDouble value) {
		setValue(value, true);
	}

	public void setValue(String value) {
		try {
			Float fvalue = new Float(value);
			setValue(fvalue.doubleValue());
		}
		catch (NumberFormatException e) {}
	}

	public void setValue(Field field, boolean doShare) {
		if (field instanceof SFDouble)
			setValue((SFDouble)field, doShare);
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