/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: MultiTextureNode.java
*
*	Revisions:
*
*	12/05/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class MultiTextureNode extends TextureNode 
{
	private final static String materialColorFieldString		= "materialColor";
	private final static String materialAlphaFieldString	= "materialAlpha";
	private final static String transparentFieldString		= "transparent";
	private final static String nomipmapFieldString		= "nomipmap";
	private static final String modeFieldString 			= "mode";
	private final static String textureFieldString 			= "texture";
	private final static String textureTransformFieldString = "textureTransform";
	private final static String alphaFieldString				= "alpha";
	private final static String colorFieldString				= "color";

	private SFBool		materialColorField;
	private SFBool		materialAlphaField;
	private SFBool		transparentField;
	private SFBool		nomipmapField;
	private MFString	modeField;
	private SFNode 	textureField;
	private SFNode 	texTransformField;
	private SFFloat 	alphaField;
	private SFColor 	colorField;

	public MultiTextureNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.MULTITEXTURE);

		// materialColor exposed field
		materialColorField = new SFBool(true);
		materialColorField.setName(materialColorFieldString);
		addExposedField(materialColorField);

		// materialAlpha exposed field
		materialAlphaField = new SFBool(true);
		materialAlphaField.setName(materialAlphaFieldString);
		addExposedField(materialAlphaField);

		// transparent exposed field
		transparentField = new SFBool(true);
		transparentField.setName(transparentFieldString);
		addExposedField(transparentField);

		// nomipmap exposed field
		nomipmapField = new SFBool(true);
		nomipmapField.setName(nomipmapFieldString);
		addExposedField(nomipmapField);

		// mode exposed field
		modeField = new MFString();
		modeField.setName(modeFieldString);
		addExposedField(modeField);

		// texture exposed field
		textureField = new SFNode();
		textureField.setName(textureFieldString);
		addExposedField(textureField);

		// textureTransform exposed field
		texTransformField = new SFNode();
		texTransformField.setName(textureTransformFieldString);
		addExposedField(texTransformField);

		// alpha exposed field
		alphaField = new SFFloat(1.0f);
		alphaField.setName(alphaFieldString);
		addExposedField(alphaField);

		// color exposed field
		colorField = new SFColor(1.0f, 1.0f, 1.0f);
		colorField.setName(colorFieldString);
		addExposedField(colorField);
	}

	////////////////////////////////////////////////
	//	SFNode Fields
	////////////////////////////////////////////////

	public SFNode getTextureField() {
		if (isInstanceNode() == false)
			return textureField;
		return (SFNode)getExposedField(textureFieldString);
	}

	public SFNode getTextureTransformField() {
		if (isInstanceNode() == false)
			return texTransformField;
		return (SFNode)getExposedField(textureTransformFieldString);
	}


	////////////////////////////////////////////////
	//	materialColor
	////////////////////////////////////////////////

	public SFBool getMaterialColorField() {
		if (isInstanceNode() == false)
			return materialColorField;
		return (SFBool)getExposedField(materialColorFieldString);
	}
	
	public void setMaterialColor(boolean on) {
		getMaterialColorField().setValue(on);
	}

	public boolean getMaterialColor() {
		return getMaterialColorField().getValue();
	}
	
	public boolean isMaterialColor() {
		return getMaterialColorField().getValue();
	}

	////////////////////////////////////////////////
	//	materialAlpha
	////////////////////////////////////////////////

	public SFBool getMaterialAlphaField() {
		if (isInstanceNode() == false)
			return materialAlphaField;
		return (SFBool)getExposedField(materialAlphaFieldString);
	}
	
	public void setMaterialAlpha(boolean on) {
		getMaterialAlphaField().setValue(on);
	}

	public boolean getMaterialAlpha() {
		return getMaterialAlphaField().getValue();
	}
	
	public boolean isMaterialAlpha() {
		return getMaterialAlphaField().getValue();
	}

	////////////////////////////////////////////////
	//	transparent
	////////////////////////////////////////////////

	public SFBool getTransparentField() {
		if (isInstanceNode() == false)
			return transparentField;
		return (SFBool)getExposedField(transparentFieldString);
	}
	
	public void setTransparent(boolean on) {
		getTransparentField().setValue(on);
	}

	public boolean getTransparent() {
		return getTransparentField().getValue();
	}
	
	public boolean isTransparent() {
		return getTransparentField().getValue();
	}

	////////////////////////////////////////////////
	//	nomipmap
	////////////////////////////////////////////////

	public SFBool getNomipmapField() {
		if (isInstanceNode() == false)
			return nomipmapField;
		return (SFBool)getExposedField(nomipmapFieldString);
	}
	
	public void setNomipmap(boolean on) {
		getNomipmapField().setValue(on);
	}

	public boolean getNomipmap() {
		return getNomipmapField().getValue();
	}
	
	public boolean isNomipmap() {
		return getNomipmapField().getValue();
	}

	////////////////////////////////////////////////
	// Mode
	////////////////////////////////////////////////

	public MFString getModeField() {
		if (isInstanceNode() == false)
			return modeField;
		return (MFString)getExposedField(modeFieldString);
	}

	public void addMode(String value) {
		getModeField().addValue(value);
	}
	
	public int getNModes() {
		return getModeField().getSize();
	}
	
	public void setMode(int index, String value) {
		getModeField().set1Value(index, value);
	}
	
	public void setModes(String value) {
		getModeField().setValues(value);
	}

	////////////////////////////////////////////////
	//	Alpha
	////////////////////////////////////////////////

	public SFFloat getAlphaField() {
		if (isInstanceNode() == false)
			return alphaField;
		return (SFFloat)getExposedField(alphaFieldString);
	}
	
	public void setAlpha(float value) {
		getAlphaField().setValue(value);
	}

	public void setAlpha(String value) {
		getAlphaField().setValue(value);
	}
	
	public float getAlpha() {
		return getAlphaField().getValue();
	}

	////////////////////////////////////////////////
	//	Color
	////////////////////////////////////////////////

	public SFColor getColorField() {
		if (isInstanceNode() == false)
			return colorField;
		return (SFColor)getExposedField(colorFieldString);
	}

	public void setColor(float value[]) {
		getColorField().setValue(value);
	}
	
	public void setColor(float r, float g, float b) {
		getColorField().setValue(r, g, b);
	}

	public void setColor(String value) {
		getColorField().setValue(value);
	}
	
	public void getColor(float value[]) {
		getColorField().getValue(value);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node)
	{
		return false;
	}

	public void initialize() 
	{
	}

	public void uninitialize() 
	{
	}

	public void update()
	{
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}

