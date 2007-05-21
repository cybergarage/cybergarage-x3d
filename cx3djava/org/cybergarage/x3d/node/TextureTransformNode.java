/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : TextureTransform.java
*
*	Revisions:
*
*	12/02/02
*		- Changed the super class from Node to AppearanceChildNode.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class TextureTransformNode extends AppearanceChildNode 
{

	private final static String translationFieldName	= "translation";
	private final static String scaleFieldName			= "scale";
	private final static String centerFieldName		= "center";
	private final static String rotationFieldName		= "rotation";

	private SFVec2f translationField;
	private SFVec2f scaleField;
	private SFVec2f centerField;
	private SFFloat rotationField;
	
	public TextureTransformNode() {
		setHeaderFlag(false);
		setType(NodeType.TEXTURETRANSFORM);

		// translation exposed field
		translationField = new SFVec2f(0.0f, 0.0f);
		translationField.setName(translationFieldName);
		addExposedField(translationField);

		// scale exposed field
		scaleField = new SFVec2f(1.0f, 1.0f);
		scaleField.setName(scaleFieldName);
		addExposedField(scaleField);

		// center exposed field
		centerField = new SFVec2f(0.0f, 0.0f);
		centerField.setName(centerFieldName);
		addExposedField(centerField);

		// rotation exposed field
		rotationField = new SFFloat(0.0f);
		rotationField.setName(rotationFieldName);
		addExposedField(rotationField);
	}

	public TextureTransformNode(TextureTransformNode node) {
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

	////////////////////////////////////////////////
	//	Center
	////////////////////////////////////////////////

	public SFVec2f getCenterField() {
		if (isInstanceNode() == false)
			return centerField;
		return (SFVec2f)getExposedField(centerFieldName);
	}

	public void setCenter(float value[]) {
		getCenterField().setValue(value);
	}

	public void setCenter(float x, float y) {
		getCenterField().setValue(x, y);
	}

	public void setCenter(String value) {
		getCenterField().setValue(value);
	}

	public void getCenter(float value[]) {
		getCenterField().getValue(value);
	}

	////////////////////////////////////////////////
	//	Rotation
	////////////////////////////////////////////////

	public SFFloat getRotationField() {
		if (isInstanceNode() == false)
			return rotationField;
		return (SFFloat)getExposedField(rotationFieldName);
	}

	public void setRotation(float value) {
		getRotationField().setValue(value);
	}

	public void setRotation(String value) {
		getRotationField().setValue(value);
	}

	public float getRotation() {
		return getRotationField().getValue();
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float vec[] = new float[2];
		getTranslation(vec);		printStream.println(indentString + "\t" + "translation " + vec[X] + " "+ vec[Y]);
		printStream.println(indentString + "\t" + "rotation " + getRotation());
		getScale(vec);				printStream.println(indentString + "\t" + "scale " + vec[X] + " "+ vec[Y]);
		getCenter(vec);				printStream.println(indentString + "\t" + "center " + vec[X] + " "+ vec[Y]);
	}
}
