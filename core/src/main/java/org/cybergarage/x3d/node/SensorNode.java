/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SensorNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class SensorNode extends Node {
	
	private static final String enabledFieldName = "enabled";

	private SFBool enabledField;
	private SFBool isActiveField;

	public SensorNode() {
	
		// enabled exposed field
		enabledField = new SFBool(true);
		addExposedField(enabledFieldName, enabledField);

		// isActive eventOut field
		isActiveField = new SFBool(false);
		addEventOut(isActiveFieldName, isActiveField);
	}

	////////////////////////////////////////////////
	//	Enabled
	////////////////////////////////////////////////

	public SFBool getEnabledField() {
		if (isInstanceNode() == false)
			return enabledField;
		return (SFBool)getExposedField(enabledFieldName);
	}
	
	public void setEnabled(boolean value) {
		getEnabledField().setValue(value);
	}

	public void setEnabled(String value) {
		getEnabledField().setValue(value);
	}
	
	public boolean getEnabled() {
		return getEnabledField().getValue();
	}
	
	public boolean isEnabled() {
		return getEnabled();
	}

	////////////////////////////////////////////////
	//	isActive
	////////////////////////////////////////////////

	public SFBool getIsActiveField() {
		if (isInstanceNode() == false)
			return isActiveField;
		return (SFBool)getEventOut(isActiveFieldName);
	}
	
	public void setIsActive(boolean value) {
		getIsActiveField().setValue(value);
	}

	public void setIsActive(String value) {
		getIsActiveField().setValue(value);
	}
	
	public boolean getIsActive() {
		return getIsActiveField().getValue();
	}
	
	public boolean isActive() {
		return getIsActive();
	}

}
