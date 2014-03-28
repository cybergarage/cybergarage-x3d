/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Appearance.java
*
*	Revisions:
*
*	12/01/02
*		- Added the follwing new X3D fields.
*			lineProperties,  fillProperties
*	12/05/03
*		- shen shenyang@163.net <shenyang@163.net>
*		- Fixed a output bugs using getType() instead of getTypeString().
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class AppearanceNode extends Node 
{
	private final static String materialExposedFieldName = "material";
	private final static String textureExposedFieldName = "texture";
	private final static String textureTransformExposedFieldName = "textureTransform";
	private final static String linePropertiesExposedFieldName = "lineProperties";
	private final static String fillPropertiesExposedFieldName = "fillProperties";

	private SFNode materialField;
	private SFNode textureField;
	private SFNode texTransformField;
	private SFNode linePropertiesField;
	private SFNode fillPropertiesField;

	public AppearanceNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.APPEARANCE);

		///////////////////////////
		// VRML97 Field 
		///////////////////////////
		
		materialField = new SFNode();
		addExposedField(materialExposedFieldName, materialField);

		textureField = new SFNode();
		addExposedField(textureExposedFieldName, textureField);

		texTransformField = new SFNode();
		addExposedField(textureTransformExposedFieldName, texTransformField);

		///////////////////////////
		// X3D Field 
		///////////////////////////
		
		linePropertiesField = new SFNode();
		addExposedField(linePropertiesExposedFieldName, linePropertiesField);

		fillPropertiesField = new SFNode();
		addExposedField(fillPropertiesExposedFieldName, fillPropertiesField);
	}

	public AppearanceNode(AppearanceNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	SFNode Fields
	////////////////////////////////////////////////

	public SFNode getMaterialField() {
		if (isInstanceNode() == false)
			return materialField;
		return (SFNode)getExposedField(materialExposedFieldName);
	}

	public SFNode getTextureField() {
		if (isInstanceNode() == false)
			return textureField;
		return (SFNode)getExposedField(textureExposedFieldName);
	}

	public SFNode getTextureTransformField() {
		if (isInstanceNode() == false)
			return texTransformField;
		return (SFNode)getExposedField(textureTransformExposedFieldName);
	}

	public SFNode getLinePropertiesField() {
		if (isInstanceNode() == false)
			return linePropertiesField;
		return (SFNode)getExposedField(linePropertiesExposedFieldName);
	}

	public SFNode getFillPropertiesField() {
		if (isInstanceNode() == false)
			return fillPropertiesField;
		return (SFNode)getExposedField(fillPropertiesExposedFieldName);
	}

	public void updateMaterialField() {
		getMaterialField().setValue(getMaterialNodes());
	}

	public void updateTextureField() {
		getTextureField().setValue(getTextureNode());
	}

	public void updateTextureTransformlField() {
		getTextureTransformField().setValue(getTextureTransformNodes());
	}

	public void updateLinePropertiesField() {
		//getLinePropertiesField().setValue(getLinePropertiesNodes());
	}

	public void updateFillPropertiesField() {
		//getFillPropertiesField().setValue(getFillPropertiesNodes());
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isMaterialNode() || node.isTextureNode() || node.isTextureTransformNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();
		updateMaterialField();
		updateTextureField();
		updateTextureTransformlField();
		updateLinePropertiesField();
		updateFillPropertiesField();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		MaterialNode material = getMaterialNodes();
		if (material != null) {
			if (material.isInstanceNode() == false) {
				String nodeName = material.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "material DEF " + material.getName() + " Material {");
				else
			printStream.println(indentString + "\t" + "material Material {");
			material.outputContext(printStream, indentString + "\t");
			printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "material USE " + material.getName());
		}

		Node texture = getTextureNode();
		if (texture != null) {
			if (texture.isInstanceNode() == false) {
				String nodeName = texture.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "texture DEF " + texture.getName() + " " + texture.getTypeString() + " {");
				else
					printStream.println(indentString + "\t" + "texture " + texture.getTypeString() + " {");
				texture.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "texture USE " + texture.getName());
		}

		TextureTransformNode textureTransform = getTextureTransformNodes();
		if (textureTransform != null) {
			if (textureTransform.isInstanceNode() == false) {
				String nodeName = textureTransform.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "textureTransform DEF " + textureTransform.getName() + " TextureTransform {");
				else
				printStream.println(indentString + "\t" + "textureTransform TextureTransform {");
			textureTransform.outputContext(printStream, indentString + "\t");
			printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "textureTransform USE " + textureTransform.getName());
		}
	}
}