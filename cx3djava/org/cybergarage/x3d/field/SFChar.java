/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : SFChar.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFChar extends Field {

	private CharValue mValue = new CharValue(' '); 

	public SFChar() {
		setType(FieldType.SFCHAR);
		setValue(' ');
	}

	public SFChar(SFChar value) {
		setType(FieldType.SFCHAR);
		setValue(value);
	}

	public SFChar(char value) {
		setType(FieldType.SFCHAR);
		setValue(value);
	}

	public SFChar(String value) {
		setType(FieldType.SFCHAR);
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set*
	////////////////////////////////////////////////

	public void setValue(char value, boolean doShare) {
		synchronized (mValue) {
			mValue.setValue(value);
		}
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(char value) {
		setValue(value, true);
	}

	public void setValue(SFChar value, boolean doShare) {
		setValue(value.getValue(), doShare);
	}

	public void setValue(SFChar value) {
		setValue(value, true);
	}

	public void setValue(String value) {
		if (value == null)
			return;
		if (value.length() <= 0)
			return;
		setValue(value.charAt(0));
	}

	public void setValue(Field field, boolean doShare) {
		if (field instanceof SFChar)
			setValue((SFChar)field, doShare);
	}

	public void setValue(Field field) {
		setValue(field, true);
	}

	////////////////////////////////////////////////
	//	get*
	////////////////////////////////////////////////

	public char getValue() {
		char value;
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
			mValue = (CharValue)object;
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
		Character value = new Character(getValue());
		return value.toString();
	}
}