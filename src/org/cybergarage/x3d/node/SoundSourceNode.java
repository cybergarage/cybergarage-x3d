/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : SoundSourceNode.java
*
*	Revisions:
*
*	12/04/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class SoundSourceNode extends TimeDependentNode
{
	private final static String descriptionExposedFieldName	= "description";
	private final static String pitchExposedFieldName			= "pitch";
	private final static String urlExposedFieldName			= "url";
	private final static String isActiveEventInName			= "isActive";
	private final static String durationEventOutName			= "duration";

	private SFString		descriptionField;
	private SFFloat			pitchField;
	private MFString		urlField;
	private SFBool			isActiveField;
	private SFTime			durationField;

	public SoundSourceNode() 
	{
		// description exposed field
		descriptionField = new SFString();
		addExposedField(descriptionExposedFieldName, descriptionField);

		// pitch exposed field
		pitchField = new SFFloat(1.0f);
		addExposedField(pitchExposedFieldName, pitchField);

		// url exposed field
		urlField = new MFString();
		addExposedField(urlExposedFieldName, urlField);
		
		// isActive eventOut field
		isActiveField = new SFBool(false);
		addEventOut(isActiveEventInName, isActiveField);

		// duration eventOut field
		durationField = new SFTime(-1.0f);
		addEventOut(durationEventOutName, durationField);
	}

	////////////////////////////////////////////////
	//	Description
	////////////////////////////////////////////////

	public SFString getDescriptionField() {
		if (isInstanceNode() == false)
			return descriptionField;
		return (SFString)getExposedField(descriptionExposedFieldName);
	}

	public void setDescription(String value) {
		getDescriptionField().setValue(value);
	}

	public String getDescription() {
		return getDescriptionField().getValue();
	}

	////////////////////////////////////////////////
	//	Pitch
	////////////////////////////////////////////////

	public SFFloat getPitchField() {
		if (isInstanceNode() == false)
			return pitchField;
		return (SFFloat)getExposedField(pitchExposedFieldName);
	}
	
	public void setPitch(float value) {
		getPitchField().setValue(value);
	}

	public void setPitch(String value) {
		getPitchField().setValue(value);
	}

	public float getPitch() {
		return getPitchField().getValue();
	}

	////////////////////////////////////////////////
	//	isActive
	////////////////////////////////////////////////

	public SFBool getIsActiveField() {
		if (isInstanceNode() == false)
			return isActiveField;
		return (SFBool)getEventOut(isActiveEventInName);
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

	////////////////////////////////////////////////
	//	duration_changed
	////////////////////////////////////////////////
	
	public SFTime getDurationChangedField() {
		if (isInstanceNode() == false)
			return durationField;
		return (SFTime)getEventOut(durationEventOutName);
	}
	
	public void setDurationChanged(double value) {
		getDurationChangedField().setValue(value);
	}

	public void setDurationChanged(String value) {
		getDurationChangedField().setValue(value);
	}

	public double getDurationChanged() {
		return getDurationChangedField().getValue();
	}

	public SFTime getDurationField() {
		return getDurationChangedField();
	}
	
	public void setDuration(double value) {
		setDurationChanged(value);
	}

	public void setDuration(String value) {
		setDurationChanged(value);
	}

	public double getDuration() {
		return getDurationChanged();
	}

	////////////////////////////////////////////////
	// URL
	////////////////////////////////////////////////

	public MFString getURLField() {
		if (isInstanceNode() == false)
			return urlField;
		return (MFString)getExposedField(urlExposedFieldName);
	}
	
	public void addURL(String value) {
		getURLField().addValue(value);
	}
	
	public int getNURLs() {
		return getURLField().getSize();
	}
	
	public void setURL(int index, String value) {
		getURLField().set1Value(index, value);
	}
	
	public void setURLs(String value) {
		getURLField().setValues(value);
	}

	public void setURLs(String value[]) {
		getURLField().setValues(value);
	}
	
	public String getURL(int index) {
		return getURLField().get1Value(index);
	}
	
	public void removeURL(int index) {
		getURLField().removeValue(index);
	}
}
