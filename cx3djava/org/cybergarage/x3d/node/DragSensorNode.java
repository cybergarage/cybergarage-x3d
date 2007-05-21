/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : DragSensorNode.java
*
*	Revisions:
*
*	12/07/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class DragSensorNode extends PointingDeviceSensorNode 
{
	private final static String autoOffsetFieldName		= "autoOffset";
	private final static String trackPointEventOutName	= "trackPoint";

	private SFBool autoOffsetField;
	private SFVec3f trackPointField;
	
	public DragSensorNode() 
	{
		// autoOffset exposed field
		autoOffsetField = new SFBool(true);
		addExposedField(autoOffsetFieldName, autoOffsetField);

		// trackPoint eventOut field
		trackPointField = new SFVec3f(0.0f, 0.0f, 0.0f);
		addEventOut(trackPointEventOutName, trackPointField);
	}

	////////////////////////////////////////////////
	//	AutoOffset
	////////////////////////////////////////////////

	public SFBool getAutoOffsetField() {
		if (isInstanceNode() == false)
			return autoOffsetField;
		return (SFBool)getExposedField(autoOffsetFieldName);
	}
	
	public void setAutoOffset(boolean value) {
		getAutoOffsetField().setValue(value);
	}

	public void setAutoOffset(String value) {
		getAutoOffsetField().setValue(value);
	}
	
	public boolean getAutoOffset() {
		return getAutoOffsetField().getValue();
	}
	
	public boolean isAutoOffset() {
		return getAutoOffset();
	}

	////////////////////////////////////////////////
	//	TrackPoint
	////////////////////////////////////////////////

	public SFVec3f getTrackPointChangedField() {
		if (isInstanceNode() == false)
			return trackPointField;
		return (SFVec3f)getEventOut(trackPointEventOutName);
	}
	
	public void setTrackPointChanged(float value[]) {
		getTrackPointChangedField().setValue(value);
	}
	
	public void setTrackPointChanged(float x, float y, float z) {
		getTrackPointChangedField().setValue(x, y, z);
	}

	public void setTrackPointChanged(String value) {
		getTrackPointChangedField().setValue(value);
	}
	
	public void getTrackPointChanged(float value[]) {
		getTrackPointChangedField().getValue(value);
	}

	public SFVec3f getTrackPointField() {
		return getTrackPointChangedField();
	}
	
	public void setTrackPoint(float value[]) {
		getTrackPointField().setValue(value);
	}
	
	public void setTrackPoint(float x, float y, float z) {
		getTrackPointField().setValue(x, y, z);
	}

	public void setTrackPoint(String value) {
		getTrackPointField().setValue(value);
	}
	
	public void getTrackPoint(float value[]) {
		getTrackPointField().getValue(value);
	}

}

