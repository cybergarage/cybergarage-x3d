/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : DoubleValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.Serializable;

public class DoubleValue extends Object implements Serializable {
	
	private double mValue; 

	public DoubleValue(double value) {
		setValue(value);
	}
	
	public void setValue(double value) {
		mValue = value;
	}
	
	public double getValue() {
		return mValue;
	}	
}
