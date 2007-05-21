/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Transform2DNode.java
*
*	Revisions:
*
*	12/03/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class Transform2DNode extends BoundedGrouping2DNode 
{
	private String	translationFieldName		= "translation";
	private String	scaleFieldName				= "scale";
	private String	rotationFieldName			= "rotation";
	private String	scaleOrientationFieldName	= "scaleOrientation";

	private SFVec2f translationField;
	private SFVec2f scaleField;
	private SFRotation rotationField;
	private SFRotation scaleOrientationField;
	
	public Transform2DNode() {
		super();

		setHeaderFlag(false);
		setType(NodeType.TRANSFORM2D);

		// translation exposed field
		translationField = new SFVec2f(0.0f, 0.0f);
		translationField.setName(translationFieldName);
		addExposedField(translationField);

		// scale exposed field
		scaleField = new SFVec2f(1.0f, 1.0f);
		scaleField.setName(scaleFieldName);
		addExposedField(scaleField);

		// rotation exposed field
		rotationField = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		rotationField.setName(rotationFieldName);
		addExposedField(rotationField);

		// scaleOrientation exposed field
		scaleOrientationField = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		scaleOrientationField.setName(scaleOrientationFieldName);
		addExposedField(scaleOrientationField);
	}

	public Transform2DNode(Transform2DNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Translation
	////////////////////////////////////////////////

	public SFVec2f getTranslationField() {
		if (isInstanceNode() == false)
			return translationField;
		return (SFVec2f)getExposedField(translationFieldName);
	}

	public void setTranslation(float value[]) {
		getTranslationField().setValue(value);
	}
	
	public void setTranslation(float x, float y) {
		getTranslationField().setValue(x, y);
	}

	public void setTranslation(String value) {
		getTranslationField().setValue(value);
	}
	
	public void getTranslation(float value[]) {
		getTranslationField().getValue(value);
	}
	
	public void addTranslation(float value[]) {
		getTranslationField().add(value);
	}
	
	public void addTranslation(float x, float y) {
		getTranslationField().add(x, y);
	}

	////////////////////////////////////////////////
	//	Scale
	////////////////////////////////////////////////

	public SFVec2f getScaleField() {
		if (isInstanceNode() == false)
			return scaleField;
		return (SFVec2f)getExposedField(scaleFieldName);
	}

	public void setScale(float value[]) {
		getScaleField().setValue(value);
	}
	
	public void setScale(float x, float y) {
		getScaleField().setValue(x, y);
	}

	public void setScale(String value) {
		getScaleField().setValue(value);
	}

	public void getScale(float value[]) {
		getScaleField().getValue(value);
	}

	public void addScale(float value[]) {
		getScaleField().add(value);
	}

	public void addScale(float x, float y) {
		getScaleField().add(x, y);
	}

	////////////////////////////////////////////////
	//	Rotation
	////////////////////////////////////////////////
	
	public SFRotation getRotationField() {
		if (isInstanceNode() == false)
			return rotationField;
		return (SFRotation)getExposedField(rotationFieldName);
	}

	public void setRotation(float value[]) {
		getRotationField().setValue(value);
	}

	public void setRotation(float x, float y, float z, float w) {
		getRotationField().setValue(x, y, z, w);
	}

	public void setRotation(String value) {
		getRotationField().setValue(value);
	}

	public void getRotation(float value[]) {
		getRotationField().getValue(value);
	}

	public void addRotation(float value[]) {
		getRotationField().add(value);
	}

	public void addRotation(float x, float y, float z, float w) {
		getRotationField().add(x, y, z, w);
	}

	////////////////////////////////////////////////
	//	ScaleOrientation
	////////////////////////////////////////////////

	public SFRotation getScaleOrientationField() {
		if (isInstanceNode() == false)
			return scaleOrientationField;
		return (SFRotation)getExposedField(scaleOrientationFieldName);
	}

	public void setScaleOrientation(float value[]) {
		getScaleOrientationField().setValue(value);
	}
	
	public void setScaleOrientation(float x, float y, float z, float w) {
		getScaleOrientationField().setValue(x, y, z, w);
	}

	public void setScaleOrientation(String value) {
		getScaleOrientationField().setValue(value);
	}
	
	public void getScaleOrientation(float value[]) {
		getScaleOrientationField().getValue(value);
	}
	
	public void addScaleOrientation(float value[]) {
		getScaleOrientationField().add(value);
	}
	
	public void addScaleOrientation(float x, float y, float z, float w) {
		getScaleOrientationField().add(x, y, z, w);
	}

	////////////////////////////////////////////////
	//	Matrix
	////////////////////////////////////////////////

	public void getSFMatrix(SFMatrix mOut)
	{
/*
		float	center[]	= new float[3];
		float	rotation[]	= new float[4];
		float	scale[]		= new float[3];
		float	scaleOri[]	= new float[4];
		float	trans[]		= new float[3];

		SFMatrix	mSRI	= new SFMatrix();
		SFMatrix	mSR		= new SFMatrix();
		SFMatrix	mCI		= new SFMatrix();
		SFMatrix	mC		= new SFMatrix();
		SFMatrix	mT		= new SFMatrix();
		SFMatrix	mR		= new SFMatrix();
		SFMatrix	mS		= new SFMatrix();

		getTranslation(trans); 
		mT.setValueAsTranslation(trans);

		getCenter(center); 
		mC.setValueAsTranslation(center);

		getRotation(rotation);
		mR.setValueAsRotation(rotation);

		getScaleOrientation(scaleOri); 
		mSR.setValueAsRotation(scaleOri);

		getScale(scale);
		mS.setValueAsScaling(scale);

		getScaleOrientation(scaleOri); 
		scaleOri[3] = -scaleOri[3]; 
		mSRI.setValueAsRotation(scaleOri);

		getCenter(center); 
		center[X] = -center[X]; 
		center[Y] = -center[Y]; 
		center[Z] = -center[Z]; 
		mCI.setValueAsTranslation(center);

		mOut.add(mT);
		mOut.add(mC);
		mOut.add(mR);
		mOut.add(mSR);
		mOut.add(mS);
		mOut.add(mSRI);
		mOut.add(mCI);
*/
	}

	public SFMatrix getSFMatrix() {
		SFMatrix mx = new SFMatrix();
		getSFMatrix(mx);
		return mx;
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node)
	{
/*
		if (node.isCommonNode() || node.isBindableNode() ||node.isInterpolatorNode() || node.isSensorNode() || node.isGroupingNode() || node.isSpecialGroupNode())
			return true;
		return false;
*/
		return true;
	}

	public void initialize() 
	{
	}

	public void uninitialize() {
	}

	public void update() 
	{
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}
