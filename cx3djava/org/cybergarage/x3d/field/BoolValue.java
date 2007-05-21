/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : BoolValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.Serializable;

public class BoolValue extends Object implements Serializable {
	
	private boolean mValue; 

	public BoolValue(boolean value) {
		setValue(value);
	}
	
	public void setValue(boolean value) {
		mValue = value;
	}
	
	public boolean getValue() {
		return mValue;
	}	
}
