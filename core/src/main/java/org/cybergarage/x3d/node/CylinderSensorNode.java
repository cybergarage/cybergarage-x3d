/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : CylinderSensorNode.java
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

public class CylinderSensorNode extends DragSensorNode 
{
	private final static String diskAngleFieldName	= "diskAngle";
	private final static String minAngleFieldName		=  "minAngle";
	private final static String maxAngleFieldName	= "maxAngle";
	private final static String offsetFieldName			= "offset";
	private final static String rotationEventOutName	= "rotation";

	private SFFloat diskAngleField;
	private SFFloat minAngleField;
	private SFFloat maxAngleField;
	private SFFloat offsetField;
	
	private SFRotation rotationField;

	public CylinderSensorNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.CYLINDERSENSOR);

		// diskAngle exposed field
		diskAngleField = new SFFloat(0.262f);
		addExposedField(diskAngleFieldName, diskAngleField);

		// minAngle exposed field
		minAngleField = new SFFloat(0.0f);
		addExposedField(minAngleFieldName, minAngleField);

		// maxAngle exposed field
		maxAngleField = new SFFloat(-1.0f);
		addExposedField(maxAngleFieldName, maxAngleField);

		// offset exposed field
		offsetField = new SFFloat(0.0f);
		addExposedField(offsetFieldName, offsetField);


		// rotation eventOut field
		rotationField = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		addEventOut(rotationEventOutName, rotationField);
	}

	public CylinderSensorNode(CylinderSensorNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	DiskAngle
	////////////////////////////////////////////////

	public SFFloat getDiskAngleField() {
		if (isInstanceNode() == false)
			return diskAngleField;
		return (SFFloat)getExposedField(diskAngleFieldName);
	}

	public void setDiskAngle(float value) {
		getDiskAngleField().setValue(value);
	}

	public void setDiskAngle(String value) {
		getDiskAngleField().setValue(value);
	}
	
	public float getDiskAngle() {
		return getDiskAngleField().getValue();
	}

	////////////////////////////////////////////////
	//	MinAngle
	////////////////////////////////////////////////

	public SFFloat getMinAngleField() {
		if (isInstanceNode() == false)
			return minAngleField;
		return (SFFloat)getExposedField(minAngleFieldName);
	}
	
	public void setMinAngle(float value) {
		getMinAngleField().setValue(value);
	}

	public void setMinAngle(String value) {
		getMinAngleField().setValue(value);
	}
	
	public float getMinAngle() {
		return getMinAngleField().getValue();
	}

	////////////////////////////////////////////////
	//	MaxAngle
	////////////////////////////////////////////////

	public SFFloat getMaxAngleField() {
		if (isInstanceNode() == false)
			return maxAngleField;
		return (SFFloat)getExposedField(maxAngleFieldName);
	}
	
	public void setMaxAngle(float value) {
		getMaxAngleField().setValue(value);
	}

	public void setMaxAngle(String value) {
		getMaxAngleField().setValue(value);
	}
	
	public float getMaxAngle() {
		return getMaxAngleField().getValue();
	}

	////////////////////////////////////////////////
	//	Offset
	////////////////////////////////////////////////

	public SFFloat getOffsetField() {
		if (isInstanceNode() == false)
			return offsetField;
		return (SFFloat)getExposedField(offsetFieldName);
	}
	
	public void setOffset(float value) {
		getOffsetField().setValue(value);
	}

	public void setOffset(String value) {
		getOffsetField().setValue(value);
	}
	
	public float getOffset() {
		return getOffsetField().getValue();
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
		getRotationField().setValue(value);
	}
	
	public void setRotation(float x, float y, float z, float rot) {
		getRotationField().setValue(x, y, z, rot);
	}

	public void setRotation(String value) {
		getRotationField().setValue(value);
	}
	
	public void getRotation(float value[]) {
		getRotationField().getValue(value);
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

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool autoOffset = getAutoOffsetField();
		SFBool enabled = getEnabledField();

		printStream.println(indentString + "\t" + "autoOffset " + autoOffset );
		printStream.println(indentString + "\t" + "diskAngle " + getDiskAngle() );
		printStream.println(indentString + "\t" + "enabled " + enabled );
		printStream.println(indentString + "\t" + "maxAngle " + getMaxAngle() );
		printStream.println(indentString + "\t" + "minAngle " + getMinAngle() );
		printStream.println(indentString + "\t" + "offset " + getOffset() );
	}
}
