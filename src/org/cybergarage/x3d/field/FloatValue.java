/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : FloatValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.Serializable;

public class FloatValue extends Object implements Serializable {
	
	private float mValue; 

	public FloatValue(float value) {
		setValue(value);
	}
	
	public void setValue(float value) {
		mValue = value;
	}
	
	public float getValue() {
		return mValue;
	}	
}
