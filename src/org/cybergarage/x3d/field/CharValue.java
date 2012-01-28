/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : CharValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.Serializable;

public class CharValue extends Object implements Serializable {
	
	private char mValue; 

	public CharValue(char value) {
		setValue(value);
	}

	public void setValue(char value) {
		mValue = value;
	}
	
	public char getValue() {
		return mValue;
	}	
}
