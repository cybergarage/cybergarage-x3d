/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Transform.java
*
*	Revisions:
*
*	11/18/02
*		- Changed the super class from GroupingNode to BoundedGroupingNode.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class TransformNode extends BoundedGroupingNode 
{
	private String	translationFieldName		= "translation";
	private String	scaleFieldName				= "scale";
	private String	centerFieldName				= "center";
	private String	rotationFieldName			= "rotation";
	private String	scaleOrientationFieldName	= "scaleOrientation";

	private SFVec3f translationField;
	private SFVec3f scaleField;
	private SFVec3f centerField;
	private SFRotation rotationField;
	private SFRotation scaleOrientationField;
	
	public TransformNode() {
		super();

		setHeaderFlag(false);
		setType(NodeType.TRANSFORM);

		// translation exposed field
		translationField = new SFVec3f(0.0f, 0.0f, 0.0f);
		translationField.setName(translationFieldName);
		addExposedField(translationField);

		// scale exposed field
		scaleField = new SFVec3f(1.0f, 1.0f, 1.0f);
		scaleField.setName(scaleFieldName);
		addExposedField(scaleField);

		// center exposed field
		centerField = new SFVec3f(0.0f, 0.0f, 0.0f);
		centerField.setName(centerFieldName);
		addExposedField(centerField);

		// rotation exposed field
		rotationField = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		rotationField.setName(rotationFieldName);
		addExposedField(rotationField);

		// scaleOrientation exposed field
		scaleOrientationField = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		scaleOrientationField.setName(scaleOrientationFieldName);
		addExposedField(scaleOrientationField);
	}

	public TransformNode(TransformNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Translation
	////////////////////////////////////////////////

	public SFVec3f getTranslationField() {
		if (isInstanceNode() == false)
			return translationField;
		return (SFVec3f)getExposedField(translationFieldName);
	}

	public void setTranslation(float value[]) {
		getTranslationField().setValue(value);
	}
	
	public void setTranslation(float x, float y, float z) {
		getTranslationField().setValue(x, y, z);
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
	
	public void addTranslation(float x, float y, float z) {
		getTranslationField().add(x, y, z);
	}

	////////////////////////////////////////////////
	//	Scale
	////////////////////////////////////////////////

	public SFVec3f getScaleField() {
		if (isInstanceNode() == false)
			return scaleField;
		return (SFVec3f)getExposedField(scaleFieldName);
	}

	public void setScale(float value[]) {
		getScaleField().setValue(value);
	}
	
	public void setScale(float x, float y, float z) {
		getScaleField().setValue(x, y, z);
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

	public void addScale(float x, float y, float z) {
		getScaleField().add(x, y, z);
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
		getCenterField().getValue(value);
	}
	
	public void addCenter(float value[]) {
		getCenterField().add(value);
	}
	
	public void addCenter(float x, float y, float z) {
		getCenterField().add(x, y, z);
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
	}

	public SFMatrix getSFMatrix() {
		SFMatrix mx = new SFMatrix();
		getSFMatrix(mx);
		return mx;
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isCommonNode() || node.isBindableNode() ||node.isInterpolatorNode() || node.isSensorNode() || node.isGroupingNode() || node.isSpecialGroupNode())
			return true;
		else
			return false;
	}

	public void initialize() 
	{
		super.initialize();
		updateChildrenField();
		updateBoundingBox();
	}

	public void uninitialize() 
	{
	}

	public void update() 
	{
		//updateChildrenField();
		//updateBoundingBox();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float vec[] = new float[3];
		float rot[] = new float[4];
		getTranslation(vec);		printStream.println(indentString + "\t" + "translation " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getRotation(rot);			printStream.println(indentString + "\t" + "rotation " + rot[X] + " "+ rot[Y] + " " + rot[Z] + " " + rot[W] );
		getScale(vec);				printStream.println(indentString + "\t" + "scale " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getScaleOrientation(rot);	printStream.println(indentString + "\t" + "scaleOrientation " + rot[X] + " "+ rot[Y] + " " + rot[Z] + " " + rot[W] );
		getCenter(vec);				printStream.println(indentString + "\t" + "center " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
	}
}
