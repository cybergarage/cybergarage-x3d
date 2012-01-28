/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: LightNode.java
*
*	Revisions:
*
*	12/05/02
*		- Added a ambientIntensity field.
*		- Added getDiffuseColor(), getAmbientColor()
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class LightNode extends Node 
{
	private final static String ambientIntensityFieldName	= "ambientIntensity";
	private final static String onFieldString				= "on";
	private final static String intensityFieldString			= "intensity";
	private final static String colorFieldString				= "color";

	private SFFloat		ambientIntensityField;
	private SFBool		onField;
	private SFFloat 	intensityField;
	private SFColor 	colorField;

		// color exposed field

	public LightNode() 
	{
		setHeaderFlag(false);

		// ambient intensity exposed field
		ambientIntensityField = new SFFloat(0.0f);
		ambientIntensityField.setName(ambientIntensityFieldName);
		addExposedField(ambientIntensityField);

		// on exposed field
		onField = new SFBool(true);
		onField.setName(onFieldString);
		addExposedField(onField);

		// intensity exposed field
		intensityField = new SFFloat(1.0f);
		intensityField.setName(intensityFieldString);
		addExposedField(intensityField);

		// color exposed field
		colorField = new SFColor(1.0f, 1.0f, 1.0f);
		colorField.setName(colorFieldString);
		addExposedField(colorField);
	}

	////////////////////////////////////////////////
	//	AmbientIntensity
	////////////////////////////////////////////////

	public SFFloat getAmbientIntensityField() {
		if (isInstanceNode() == false)
			return ambientIntensityField;
		return (SFFloat)getExposedField(ambientIntensityFieldName);
	}
	
	public void setAmbientIntensity(float value) {
		getAmbientIntensityField().setValue(value);
	}

	public void setAmbientIntensity(String value) {
		getAmbientIntensityField().setValue(value);
	}

	public float getAmbientIntensity() {
		return getAmbientIntensityField().getValue();
	}

	////////////////////////////////////////////////
	//	On
	////////////////////////////////////////////////

	public SFBool getOnField() {
		if (isInstanceNode() == false)
			return onField;
		return (SFBool)getExposedField(onFieldString);
	}
	
	public void setOn(boolean on) {
		getOnField().setValue(on);
	}

	public void setOn(String on) {
		getOnField().setValue(on);
	}
	
	public boolean getOn() {
		return getOnField().getValue();
	}
	
	public boolean isOn() {
		return getOnField().getValue();
	}
	
	////////////////////////////////////////////////
	//	Intensity
	////////////////////////////////////////////////

	public SFFloat getIntensityField() {
		if (isInstanceNode() == false)
			return intensityField;
		return (SFFloat)getExposedField(intensityFieldString);
	}
	
	public void setIntensity(float value) {
		getIntensityField().setValue(value);
	}

	public void setIntensity(String value) {
		getIntensityField().setValue(value);
	}
	
	public float getIntensity() {
		return getIntensityField().getValue();
	}

	////////////////////////////////////////////////
	//	Diffuse Color
	////////////////////////////////////////////////

	public void getDiffuseColor(float value[]) {
		getColor(value);
		float	intensity = getIntensity();
		value[0] *= intensity;
		value[1] *= intensity;
		value[2] *= intensity;
	}

	////////////////////////////////////////////////
	//	Ambient Color
	////////////////////////////////////////////////

	public void getAmbientColor(float value[]) {
		getColor(value);
		float	intensity = getIntensity();
		float	ambientIntensity = getAmbientIntensity();
		value[0] *= intensity * ambientIntensity;
		value[1] *= intensity * ambientIntensity;
		value[2] *= intensity * ambientIntensity;
	}

	////////////////////////////////////////////////
	//	Color
	////////////////////////////////////////////////

	public SFColor getColorField() {
		if (isInstanceNode() == false)
			return colorField;
		return (SFColor)getExposedField(colorFieldString);
	}

	public void setColor(float value[]) {
		getColorField().setValue(value);
	}
	
	public void setColor(float r, float g, float b) {
		getColorField().setValue(r, g, b);
	}

	public void setColor(String value) {
		getColorField().setValue(value);
	}
	
	public void getColor(float value[]) {
		getColorField().getValue(value);
	}
}