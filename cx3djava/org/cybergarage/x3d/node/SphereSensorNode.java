/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : SphereSensorNode.java
*
*	Revisions:
*
*	12/07/02
*		- Changed the super class from SensorNode to DragSensorNode.
*		- Moved the following fields to DragSensorNode.
*			autoOffset, trackPoint
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class SphereSensorNode extends DragSensorNode 
{
	private final static String offsetFieldName				= "offset";
	private final static String rotationEventOutName		= "rotation";

	private SFRotation	offsetField;
	private SFRotation	rotationField;

	public SphereSensorNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.SPHERESENSOR);

		// offset exposed field
		offsetField = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		addExposedField(offsetFieldName, offsetField);
		
		// rotation eventOut field
		rotationField = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		addEventOut(rotationEventOutName, rotationField);
	}

	public SphereSensorNode(SphereSensorNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Offset
	////////////////////////////////////////////////

	public SFRotation getOffsetField() {
		if (isInstanceNode() == false)
			return offsetField;
		return (SFRotation)getExposedField(offsetFieldName);
	}
	
	public void setOffset(float value[]) {
		getOffsetField().setValue(value);
	}

	public void setOffset(String value) {
		getOffsetField().setValue(value);
	}

	public void getOffset(float value[]) {
		getOffsetField().getValue(value);
	}

	////////////////////////////////////////////////
	//	Rotation
	////////////////////////////////////////////////

	public SFRotation getRotationChangedField() {
		if (isInstanceNode() == false)
			return rotationField;
		return (SFRotation)getEventOut(rotationEventOutName);
	}
	
	public void setRotationChanged(float value[]) {
		getRotationChangedField().setValue(value);
	}
	
	public void setRotationChanged(float x, float y, float z, float rot) {
		getRotationChangedField().setValue(x, y, z, rot);
	}

	public void setRotationChanged(String value) {
		getRotationChangedField().setValue(value);
	}
	
	public void getRotationChanged(float value[]) {
		getRotationChangedField().getValue(value);
	}

	public SFRotation getRotationField() {
		return getRotationChangedField();
	}

	public void setRotation(float value[]) {
		setRotationChanged(value);
	}
	
	public void setRotation(float x, float y, float z, float rot) {
		setRotationChanged(x, y, z, rot);
	}

	public void setRotation(String value) {
		setRotationChanged(value);
	}
	
	public void getRotation(float value[]) {
		getRotationChanged(value);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
		setIsActive(false);
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
		SFBool autoOffset = getAutoOffsetField();
		SFBool enabled = getEnabledField();
		SFRotation offset = getOffsetField();

		printStream.println(indentString + "\t" + "autoOffset " + autoOffset );
		printStream.println(indentString + "\t" + "enabled " + enabled );
		printStream.println(indentString + "\t" + "offset " + offset );
	}
}
