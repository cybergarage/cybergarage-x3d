/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : PlaneSensorNode.java
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

public class PlaneSensorNode extends DragSensorNode 
{
	private final static String minPositionFieldName	= "minPosition";
	private final static String maxPositionFieldName	= "maxPosition";
	private final static String offsetFieldName			= "offset";
	private final static String translationEventOutName	= "translation";

	private SFVec2f minPositionField;
	private SFVec2f maxPositionField;
	private SFVec3f offsetField;
	private SFVec3f translationField;

	public PlaneSensorNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.PLANESENSOR);

		// minPosition exposed field
		minPositionField = new SFVec2f(0.0f, 0.0f);
		addExposedField(minPositionFieldName, minPositionField);

		// maxAngle exposed field
		maxPositionField = new SFVec2f(-1.0f, -1.0f);
		addExposedField(maxPositionFieldName, maxPositionField);

		// offset exposed field
		offsetField = new SFVec3f(0.0f, 0.0f, 0.0f);
		addExposedField(offsetFieldName, offsetField);

		// translation eventOut field
		translationField = new SFVec3f(0.0f, 0.0f, 0.0f);
		addEventOut(translationEventOutName, translationField);
	}
	
	public PlaneSensorNode(PlaneSensorNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	MinPosition
	////////////////////////////////////////////////

	public SFVec2f getMinPositionField() {
		if (isInstanceNode() == false)
			return minPositionField;
		return (SFVec2f)getExposedField(minPositionFieldName);
	}

	public void setMinPosition(float value[]) {
		getMinPositionField().setValue(value);
	}
	
	public void setMinPosition(float x, float y) {
		getMinPositionField().setValue(x, y);
	}

	public void setMinPositions(String value) {
		getMinPositionField().setValue(value);
	}

	public void getMinPosition(float value[]) {
		getMinPositionField().getValue();
	}

	////////////////////////////////////////////////
	//	MaxPosition
	////////////////////////////////////////////////

	public SFVec2f getMaxPositionField() {
		if (isInstanceNode() == false)
			return maxPositionField;
		return (SFVec2f)getExposedField(maxPositionFieldName);
	}
	
	public void setMaxPosition(float value[]) {
		getMaxPositionField().setValue(value);
	}
	
	public void setMaxPosition(float x, float y) {
		getMaxPositionField().setValue(x, y);
	}

	public void setMaxPosition(String value) {
		getMaxPositionField().setValue(value);
	}
	
	public void getMaxPosition(float value[]) {
		getMaxPositionField().getValue();
	}

	////////////////////////////////////////////////
	//	Offset
	////////////////////////////////////////////////

	public SFVec3f getOffsetField() {
		if (isInstanceNode() == false)
			return offsetField;
		return (SFVec3f)getExposedField(offsetFieldName);
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
	//	Translation
	////////////////////////////////////////////////
	
	public SFVec3f getTranslationChangedField() {
		if (isInstanceNode() == false)
			return translationField;
		return (SFVec3f)getEventOut(translationEventOutName);
	}
	
	public void setTranslationChanged(float value[]) {
		getTranslationChangedField().setValue(value);
	}
	
	public void setTranslationChanged(float x, float y, float z) {
		getTranslationChangedField().setValue(x, y, z);
	}

	public void setTranslationChanged(String value) {
		getTranslationChangedField().setValue(value);
	}
	
	public void getTranslationChanged(float value[]) {
		getTranslationChangedField().getValue(value);
	}

	public SFVec3f getTranslationdField() {
		return getTranslationChangedField();
	}
	
	public void setTranslationd(float value[]) {
		getTranslationdField().setValue(value);
	}
	
	public void setTranslationd(float x, float y, float z) {
		getTranslationdField().setValue(x, y, z);
	}

	public void setTranslationd(String value) {
		getTranslationdField().setValue(value);
	}
	
	public void getTranslationd(float value[]) {
		getTranslationdField().getValue(value);
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
		SFVec2f maxpos = getMaxPositionField();
		SFVec2f minpos = getMinPositionField();
		SFVec3f offset = getOffsetField();

		printStream.println(indentString + "\t" + "autoOffset " + autoOffset );
		printStream.println(indentString + "\t" + "enabled " + enabled );
		printStream.println(indentString + "\t" + "maxPosition " + maxpos );
		printStream.println(indentString + "\t" + "minPosition " + minpos );
		printStream.println(indentString + "\t" + "offset " + offset );
	}
}
