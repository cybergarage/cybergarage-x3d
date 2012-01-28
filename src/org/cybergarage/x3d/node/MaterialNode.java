/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : MaterialNode.java
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

public class MaterialNode extends AppearanceChildNode 
{
	private final static String transparencyFieldName			= "transparency";
	private final static String ambientIntensityFieldName		= "ambientIntensity";
	private final static String shininessFieldName				= "shininess";
	private final static String diffuseColorFieldName			= "diffuseColor";
	private final static String specularColorFieldName		= "specularColor";
	private final static String emissiveColorFieldName		= "emissiveColor";

	private SFFloat transparencyField;
	private SFFloat ambientIntensityField;
	private SFFloat shininessField;
	private SFColor diffuseColorField;
	private SFColor specularColorField;
	private SFColor emissiveColorField;

	public MaterialNode() {
		setHeaderFlag(false);
		setType(NodeType.MATERIAL);

		// tranparency exposed field
		transparencyField = new SFFloat(0.0f);
		transparencyField.setName(transparencyFieldName);
		addExposedField(transparencyField);

		// ambientIntesity exposed field
		ambientIntensityField = new SFFloat(0.2f);
		ambientIntensityField.setName(ambientIntensityFieldName);
		addExposedField(ambientIntensityField);

		// shininess exposed field
		shininessField = new SFFloat(0.2f);
		shininessField.setName(shininessFieldName);
		addExposedField(shininessField);

		// diffuseColor exposed field
		diffuseColorField = new SFColor(0.8f, 0.8f, 0.8f);
		diffuseColorField.setName(diffuseColorFieldName);
		addExposedField(diffuseColorField);

		// specularColor exposed field
		specularColorField = new SFColor(0.0f, 0.0f, 0.0f);
		specularColorField.setName(specularColorFieldName);
		addExposedField(specularColorField);

		// emissiveColor exposed field
		emissiveColorField = new SFColor(0.0f, 0.0f, 0.0f);
		emissiveColorField.setName(emissiveColorFieldName);
		addExposedField(emissiveColorField);
	}

	public MaterialNode(MaterialNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Transparency
	////////////////////////////////////////////////

	public SFFloat getTransparencyField() {
		if (isInstanceNode() == false)
			return transparencyField;
		return (SFFloat)getExposedField(transparencyFieldName);
	}
	
	public void setTransparency(float value) {
		getTransparencyField().setValue(value);
	}

	public void setTransparency(String value) {
		getTransparencyField().setValue(value);
	}
	
	public float getTransparency() {
		return getTransparencyField().getValue();
	}

	////////////////////////////////////////////////
	//	AmbientIntensity
	////////////////////////////////////////////////

	public SFFloat getAmbientIntensityField() {
		if (isInstanceNode() == false)
			return ambientIntensityField;
		return (SFFloat)getExposedField(ambientIntensityFieldName);
	}
	
	public void setAmbientIntensity(float intensity) {
		getAmbientIntensityField().setValue(intensity);
	}

	public void setAmbientIntensity(String intensity) {
		getAmbientIntensityField().setValue(intensity);
	}
	
	public float getAmbientIntensity() {
		return getAmbientIntensityField().getValue();
	}

	////////////////////////////////////////////////
	//	Shininess
	////////////////////////////////////////////////

	public SFFloat getShininessField() {
		if (isInstanceNode() == false)
			return shininessField;
		return (SFFloat)getExposedField(shininessFieldName);
	}
	
	public void setShininess(float value) {
		getShininessField().setValue(value);
	}

	public void setShininess(String value) {
		getShininessField().setValue(value);
	}
	
	public float getShininess() {
		return getShininessField().getValue();
	}

	////////////////////////////////////////////////
	//	AmbientColor
	////////////////////////////////////////////////

	public void getAmbientColor(float value[]) {
		float ambientIntensity = getAmbientIntensity();
		getDiffuseColor(value);
		value[0] *= ambientIntensity;
		value[1] *= ambientIntensity;
		value[2] *= ambientIntensity;
	}

	////////////////////////////////////////////////
	//	DiffuseColor
	////////////////////////////////////////////////

	public SFColor getDiffuseColorField() {
		if (isInstanceNode() == false)
			return diffuseColorField;
		return (SFColor)getExposedField(diffuseColorFieldName);
	}

	public void setDiffuseColor(float value[]) {
		getDiffuseColorField().setValue(value);
	}
	
	public void setDiffuseColor(float r, float g, float b) {
		getDiffuseColorField().setValue(r, g, b);
	}

	public void setDiffuseColor(String value) {
		getDiffuseColorField().setValue(value);
	}
	
	public void getDiffuseColor(float value[]) {
		getDiffuseColorField().getValue(value);
	}

	////////////////////////////////////////////////
	//	SpecularColor
	////////////////////////////////////////////////

	public SFColor getSpecularColorField() {
		if (isInstanceNode() == false)
			return specularColorField;
		return (SFColor)getExposedField(specularColorFieldName);
	}

	public void setSpecularColor(float value[]) {
		getSpecularColorField().setValue(value);
	}
	
	public void setSpecularColor(float r, float g, float b) {
		getSpecularColorField().setValue(r, g, b);
	}

	public void setSpecularColor(String value) {
		getSpecularColorField().setValue(value);
	}
	
	public void getSpecularColor(float value[]) {
		getSpecularColorField().getValue(value);
	}

	////////////////////////////////////////////////
	//	EmissiveColor
	////////////////////////////////////////////////

	public SFColor getEmissiveColorField() {
		if (isInstanceNode() == false)
			return emissiveColorField;
		return (SFColor)getExposedField(emissiveColorFieldName);
	}

	public void setEmissiveColor(float value[]) {
		getEmissiveColorField().setValue(value);
	}
	
	public void setEmissiveColor(float r, float g, float b) {
		getEmissiveColorField().setValue(r, g, b);
	}
	
	public void setEmissiveColor(String value) {
		getEmissiveColorField().setValue(value);
	}
	
	public void getEmissiveColor(float value[]) {
		getEmissiveColorField().getValue(value);
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
		float color[] = new float[3];
		getDiffuseColor(color);		printStream.println(indentString + "\t" + "diffuseColor " + color[X] + " "+ color[Y] + " " + color[Z] );
		printStream.println(indentString + "\t" + "ambientIntensity " + getAmbientIntensity() );
		getSpecularColor(color);	printStream.println(indentString + "\t" + "specularColor " + color[X] + " "+ color[Y] + " " + color[Z] );
		getEmissiveColor(color);	printStream.println(indentString + "\t" + "emissiveColor " + color[X] + " "+ color[Y] + " " + color[Z] );
		printStream.println(indentString + "\t" + "shininess " + getShininess() );
		printStream.println(indentString + "\t" + "transparency " + getTransparency() );
	}
}
