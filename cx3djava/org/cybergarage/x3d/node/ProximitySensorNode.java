/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : ProximitySensorNode.java
*
*	Revisions:
*
*	12/08/02
*		- Changed the super class from SensorNode to EnvironmentalSensorNode.
*		- Moved the following fields to EnvironmentalSensorNode.
*			center, size, enterTime, exitTime
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import java.util.Date;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class ProximitySensorNode extends EnvironmentalSensorNode 
{
	private final static String positionEventOutName	= "position";
	private final static String orientationEventOutName	= "orientation";

	private SFVec3f positionField;
	private SFRotation orientationField;
	
	public ProximitySensorNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.PROXIMITYSENSOR);
		setRunnable(true);

		// position eventOut field
		positionField = new SFVec3f(0.0f, 0.0f, 0.0f);
		addEventOut(positionEventOutName, positionField);

		// orientation eventOut field
		orientationField = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		addEventOut(orientationEventOutName, orientationField);
	}

	public ProximitySensorNode(ProximitySensorNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Position
	////////////////////////////////////////////////
	
	public SFVec3f getPositionChangedField() {
		if (isInstanceNode() == false)
			return positionField;
		return (SFVec3f)getEventOut(positionEventOutName);
	}
	
	public void setPositionChanged(float value[]) {
		getPositionChangedField().setValue(value);
	}
	
	public void setPositionChanged(float x, float y, float z) {
		getPositionChangedField().setValue(x, y, z);
	}

	public void setPositionChanged(String value) {
		getPositionChangedField().setValue(value);
	}
	
	public void getPositionChanged(float value[]) {
		getPositionChangedField().getValue(value);
	}

	public SFVec3f getPositionField() {
		return getPositionChangedField();
	}
	
	public void setPosition(float value[]) {
		setPositionChanged(value);
	}
	
	public void setPosition(float x, float y, float z) {
		setPositionChanged(x, y, z);
	}

	public void setPosition(String value) {
		setPositionChanged(value);
	}
	
	public void getPosition(float value[]) {
		getPositionChanged(value);
	}

	////////////////////////////////////////////////
	//	Orientation
	////////////////////////////////////////////////

	public SFRotation getOrientationChangedField() {
		if (isInstanceNode() == false)
			return orientationField;
		return (SFRotation)getEventOut(orientationEventOutName);
	}
	
	public void setOrientationChanged(float value[]) {
		getOrientationChangedField().setValue(value);
	}
	
	public void setOrientationChanged(float x, float y, float z, float rot) {
		getOrientationChangedField().setValue(x, y, z, rot);
	}

	public void setOrientationChanged(String value) {
		getOrientationChangedField().setValue(value);
	}
	
	public void getOrientationChanged(float value[]) {
		SFRotation orientation = (SFRotation)getEventOut(orientationEventOutName);
		orientation.getValue(value);
	}

	public SFRotation getOrientationField() {
		return getOrientationChangedField();
	}

	public void setOrientation(float value[]) {
		setOrientationChanged(value);
	}
	
	public void setOrientation(float x, float y, float z, float rot) {
		setOrientationChanged(x, y, z, rot);
	}

	public void setOrientation(String value) {
		setOrientationChanged(value);
	}
	
	public void getOrientation(float value[]) {
		getOrientationChanged(value);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
		setInRegion(false);
		setRunnableIntervalTime(250);
	}

	public void uninitialize() {
	}

	public double getCurrentSystemTime() {
		Date	date = new Date();
		return (double)date.getTime() / 1000.0;
	}

	private float vpos[]	= new float[3];
	private float center[]	= new float[3];
	private float size[]	= new float[3];
		
	public void update() 
	{
		if (isEnabled() == false)
			return;

		SceneGraph sg = getSceneGraph();
		if (sg == null)
			return;

		ViewpointNode view = sg.getViewpointNode();
		if (view == null)
			view = sg.getDefaultViewpointNode();

		view.getPosition(vpos);
		
		getCenter(center);
		getSize(size);

		if (inRegion() == false) {
			if (isRegion(vpos, center, size) == true) {
				setInRegion(true);
				double time = getCurrentSystemTime();
				setEnterTime(time);
				sendEvent(getEnterTimeField());
				setIsActive(true);
				sendEvent(getIsActiveField());
			}
		}
		else {
			if (isRegion(vpos, center, size) == false) {
				setInRegion(false);
				double time = getCurrentSystemTime();
				setExitTime(time);
				sendEvent(getExitTimeField());
				setIsActive(false);
				sendEvent(getIsActiveField());
			}
		}
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
		SFBool enabled = getEnabledField();
		SFVec3f center = getCenterField();
		SFVec3f size = getSizeField();

		printStream.println(indentString + "\t" + "enabled " + enabled );
		printStream.println(indentString + "\t" + "center " + center );
		printStream.println(indentString + "\t" + "size " + size );
	}
}
