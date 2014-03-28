/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: BindableNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class BindableNode extends Node {

	private String	setBindFieldString			= "bind";
	private String	bindTimeFieldString			= "bindTime";
	private String	isBoundFieldString			= "isBound";

	private SFBool setBindField;
	private 	SFTime bindTimeField;
	private 	SFBool isBoundField;

	public BindableNode() {
		setHeaderFlag(false);

		// set_bind
		setBindField = new SFBool(true);
		addEventIn(setBindFieldString, setBindField);

		// cybleInterval exposed field
		bindTimeField = new SFTime(1.0);
		addEventOut(bindTimeFieldString, bindTimeField);

		// isBind
		isBoundField = new SFBool(true);
		addEventOut(isBoundFieldString, isBoundField);
	}

	////////////////////////////////////////////////
	//	bind
	////////////////////////////////////////////////

	public SFBool getBindField() {
		if (isInstanceNode() == false)
			return setBindField;
		return (SFBool)getEventIn(setBindFieldString);
	}

	public void setBind(boolean value) {
		getBindField().setValue(value);
	}

	public void setBind(String value) {
		getBindField().setValue(value);
	}
	
	public boolean getBind() {
		return getBindField().getValue();
	}
	
	////////////////////////////////////////////////
	//	bindTime
	////////////////////////////////////////////////
	
	public SFTime getBindTimeField() {
		if (isInstanceNode() == false)
			return bindTimeField;
		return (SFTime)getEventOut(bindTimeFieldString);
	}

	public void setBindTime(double value) {
		getBindTimeField().setValue(value);
	}

	public void setBindTime(String value) {
		getBindTimeField().setValue(value);
	}
	
	public double getBindTime() {
		return getBindTimeField().getValue();
	}
	
	////////////////////////////////////////////////
	//	isBound
	////////////////////////////////////////////////

	public SFBool getIsBoundField() {
		if (isInstanceNode() == false)
			return isBoundField;
		return (SFBool)getEventOut(isBoundFieldString);
	}

	public void setIsBound(boolean value) {
		getIsBoundField().setValue(value);
	}

	public void setIsBound(String value) {
		getIsBoundField().setValue(value);
	}
	
	public boolean getIsBound() {
		return getIsBoundField().getValue();
	}
	
	public boolean isBound() {
		return getIsBound();
	}
	
}

