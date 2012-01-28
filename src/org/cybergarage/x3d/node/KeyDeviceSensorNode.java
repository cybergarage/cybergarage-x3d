/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : KeyDeviceSensorNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class KeyDeviceSensorNode extends Node {
	
	private String actionKeyPressFieldName = "actionKeyPress";
	private String actionKeyReleaseFieldName = "actionKeyRelease";
	private String keyPressFieldName = "keyPress";
	private String keyReleaseFieldName = "keyRelease";
	private String altKeyFieldName = "altKey";
	private String controlKeyFieldName = "controlKey";
	private String shiftKeyFieldName = "shiftKey";

	private SFInt32 actionKeyPressField;
	private SFInt32 actionKeyReleaseField;
	private SFInt32 keyPressField;
	private SFInt32 keyReleaseField;
	private SFBool altKeyField;
	private SFBool controlKeyField;
	private SFBool shiftKeyField;

	public KeyDeviceSensorNode() 
	{
		// actionKeyPressField eventOut field
		actionKeyPressField = new SFInt32(0);
		addEventOut(actionKeyPressFieldName, actionKeyPressField);

		// actionKeyReleaseField eventOut field
		actionKeyReleaseField = new SFInt32(0);
		addEventOut(actionKeyReleaseFieldName, actionKeyReleaseField);

		// keyPressField eventOut field
		keyPressField = new SFInt32(0);
		addEventOut(keyPressFieldName, keyPressField);

		// keyReleaseField eventOut field
		keyReleaseField = new SFInt32(0);
		addEventOut(keyReleaseFieldName, keyReleaseField);

		// altKeyField eventOut field
		altKeyField = new SFBool(false);
		addEventOut(altKeyFieldName, altKeyField);

		// controlKeyField eventOut field
		controlKeyField = new SFBool(false);
		addEventOut(controlKeyFieldName, controlKeyField);

		// shiftKeyField eventOut field
		shiftKeyField = new SFBool(false);
		addEventOut(shiftKeyFieldName, shiftKeyField);
	}

	////////////////////////////////////////////////
	//	actionKeyPress
	////////////////////////////////////////////////

	public SFInt32 getActionKeyPressField() {
		if (isInstanceNode() == false)
			return actionKeyPressField;
		return (SFInt32)getEventOut(actionKeyPressFieldName);
	}
	
	public void setActionKeyPress(int value) {
		getActionKeyPressField().setValue(value);
	}

	public int getActionKeyPress() {
		return getActionKeyPressField().getValue();
	}

	////////////////////////////////////////////////
	//	actionKeyRelease
	////////////////////////////////////////////////

	public SFInt32 getActionKeyReleaseField() {
		if (isInstanceNode() == false)
			return actionKeyReleaseField;
		return (SFInt32)getEventOut(actionKeyReleaseFieldName);
	}
	
	public void setActionKeyRelease(int value) {
		getActionKeyReleaseField().setValue(value);
	}

	public int getActionKeyRelease() {
		return getActionKeyReleaseField().getValue();
	}

	////////////////////////////////////////////////
	//	keyPress
	////////////////////////////////////////////////

	public SFInt32 getKeyPressField() {
		if (isInstanceNode() == false)
			return keyPressField;
		return (SFInt32)getEventOut(keyPressFieldName);
	}
	
	public void setKeyPress(int value) {
		getKeyPressField().setValue(value);
	}

	public int getKeyPress() {
		return getKeyPressField().getValue();
	}

	////////////////////////////////////////////////
	//	keyRelease
	////////////////////////////////////////////////

	public SFInt32 getKeyReleaseField() {
		if (isInstanceNode() == false)
			return keyReleaseField;
		return (SFInt32)getEventOut(keyReleaseFieldName);
	}
	
	public void setKeyRelease(int value) {
		getKeyReleaseField().setValue(value);
	}

	public int getKeyRelease() {
		return getKeyReleaseField().getValue();
	}

	////////////////////////////////////////////////
	//	altKey
	////////////////////////////////////////////////

	public SFBool getAltKeyField() {
		if (isInstanceNode() == false)
			return altKeyField;
		return (SFBool)getEventOut(altKeyFieldName);
	}
	
	public void setAltKey(boolean value) {
		getAltKeyField().setValue(value);
	}

	public void setAltKey(String value) {
		getAltKeyField().setValue(value);
	}
	
	public boolean getAltKey() {
		return getAltKeyField().getValue();
	}

	////////////////////////////////////////////////
	//	controlKey
	////////////////////////////////////////////////

	public SFBool getControlKeyField() {
		if (isInstanceNode() == false)
			return controlKeyField;
		return (SFBool)getEventOut(controlKeyFieldName);
	}
	
	public void setControlKey(boolean value) {
		getControlKeyField().setValue(value);
	}

	public void setControlKey(String value) {
		getControlKeyField().setValue(value);
	}
	
	public boolean getControlKey() {
		return getControlKeyField().getValue();
	}

	////////////////////////////////////////////////
	//	shiftKey
	////////////////////////////////////////////////

	public SFBool getShiftKeyField() {
		if (isInstanceNode() == false)
			return shiftKeyField;
		return (SFBool)getEventOut(shiftKeyFieldName);
	}
	
	public void setShiftKey(boolean value) {
		getShiftKeyField().setValue(value);
	}

	public void setShiftKey(String value) {
		getShiftKeyField().setValue(value);
	}
	
	public boolean getShiftKey() {
		return getShiftKeyField().getValue();
	}
}
