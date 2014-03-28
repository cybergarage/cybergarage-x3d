/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : IntValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.Serializable;

public class IntValue extends Object implements Serializable {
	
	private int mValue; 

	public IntValue(int value) {
		setValue(value);
	}

	public void setValue(int value) {
		mValue = value;
	}
	
	public int getValue() {
		return mValue;
	}	
}
