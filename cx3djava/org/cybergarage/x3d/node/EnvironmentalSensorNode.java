/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : EnvironmentalSensorNode.java
*
*	Revisions:
*
*	12/08/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class EnvironmentalSensorNode extends SensorNode 
{
	private final static String centerFieldName			= "center";
	private final static String sizeFieldName				= "size";
	private final static String enterTimeEventOutName	= "enterTime";
	private final static String exitTimeEventOutName		= "exitTime";

	private SFVec3f centerField;
	private SFVec3f sizeField;
	private SFTime enterTimeField;
	private SFTime exitTimeField;

	public EnvironmentalSensorNode() 
	{
		// center exposed field
		centerField = new SFVec3f(0.0f, 0.0f, 0.0f);
		addExposedField(centerFieldName, centerField);

		// size exposed field
		sizeField = new SFVec3f(0.0f, 0.0f, 0.0f);
		addExposedField(sizeFieldName, sizeField);

		// enterTime eventOut field
		enterTimeField = new SFTime(0.0f);
		addEventOut(enterTimeEventOutName, enterTimeField);

		// exitTime eventOut field
		exitTimeField = new SFTime(0.0f);
		addEventOut(exitTimeEventOutName, exitTimeField);
	}

	////////////////////////////////////////////////
	//	Center
	////////////////////////////////////////////////

	public SFVec3f getCenterField() {
		if (isInstanceNode() == false)
			return centerField;
		return (SFVec3f)getExposedField(centerFieldName);
	}
	
	public void setCenter(float value[]) {
		getCenterField().setValue(value);
	}
	
	public void setCenter(float x, float y, float z) {
		getCenterField().setValue(x, y, z);
	}

	public void setCenter(String value) {
		getCenterField().setValue(value);
	}
	
	public void getCenter(float value[]) {
		getCenterField().getValue();
	}

	////////////////////////////////////////////////
	//	Size
	////////////////////////////////////////////////

	public SFVec3f getSizeField() {
		if (isInstanceNode() == false)
			return sizeField;
		return (SFVec3f)getExposedField(sizeFieldName);
	}
	
	public void setSize(float value[]) {
		getSizeField().setValue(value);
	}
	
	public void setSize(float x, float y, float z) {
		getSizeField().setValue(x, y, z);
	}

	public void setSize(String value) {
		getSizeField().setValue(value);
	}
	
	public void getSize(float value[]) {
		getSizeField().getValue();
	}

	////////////////////////////////////////////////
	//	EnterTime
	////////////////////////////////////////////////

	public SFTime getEnterTimeField() {
		if (isInstanceNode() == false)
			return enterTimeField;
		return (SFTime)getEventOut(enterTimeEventOutName);
	}

	public void setEnterTime(double value) {
		getEnterTimeField().setValue(value);
	}

	public void setEnterTime(String value) {
		getEnterTimeField().setValue(value);
	}
	
	public double getEnterTime() {
		return getEnterTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	ExitTime
	////////////////////////////////////////////////

	public SFTime getExitTimeField() {
		if (isInstanceNode() == false)
			return exitTimeField;
		return (SFTime)getEventOut(exitTimeEventOutName);
	}
	
	public void setExitTime(double value) {
		getExitTimeField().setValue(value);
	}

	public void setExitTime(String value) {
		getExitTimeField().setValue(value);
	}
	
	public double getExitTime() {
		return getExitTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	 inRegion
	////////////////////////////////////////////////
	
	private boolean mInRegion = false;
	
	public void setInRegion(boolean value) {
		mInRegion = value;
	}

	public boolean inRegion() {
		return mInRegion;
	} 

	public boolean isRegion(float vpos[], float center[], float size[]) {
		for (int n=0; n<3; n++) {
			if (vpos[n] < center[n] - size[n]/2.0f)
				return false;
			if (center[n] + size[n]/2.0f < vpos[n])
				return false;
		}
		return true;
	}
}
