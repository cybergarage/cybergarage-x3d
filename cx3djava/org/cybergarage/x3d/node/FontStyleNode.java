/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : FontStyle.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class FontStyleNode extends Node {
	
	//// Field ////////////////
	private final static String familyFieldName		= "family";
	private final static String styleFieldName			= "style";
	private final static String languageFieldName		= "language";
	private final static String justifyFieldName		= "justify";
	private final static String sizeFieldName			= "size";
	private final static String spacingFieldName		= "spacing";
	private final static String horizontalFieldName	= "horizontal";
	private final static String leftToRightFieldName	= "leftToRight";
	private final static String topToBottomFieldName	= "topToBottom";

	private MFString familyField;
	private SFString styleField;
	private SFString languageField;
	private MFString justifyField;
	private SFFloat sizeField;
	private SFFloat spacingField;
	private SFBool horizontalField;
	private SFBool leftToRightField;
	private SFBool topToBottomField;

	public FontStyleNode() {
		setHeaderFlag(false);
		setType(NodeType.FONTSTYLE);

		///////////////////////////
		// Field 
		///////////////////////////

		// family field
		familyField = new MFString();
		familyField.setName(familyFieldName);
		addField(familyField);

		// style field
		styleField = new SFString("PLAIN");
		styleField.setName(styleFieldName);
		addField(styleField);

		// language field
		languageField = new SFString();
		languageField.setName(languageFieldName);
		addField(languageField);

		// justify field
		justifyField = new MFString();
		justifyField.setName(justifyFieldName);
		addField(justifyField);

		// size field
		sizeField = new SFFloat(1);
		addField(sizeFieldName, sizeField);

		// spacing field
		spacingField = new SFFloat(1);
		addField(spacingFieldName, spacingField);

		// horizontal field
		horizontalField = new SFBool(true);
		addField(horizontalFieldName, horizontalField);

		// leftToRight field
		leftToRightField = new SFBool(true);
		addField(leftToRightFieldName, leftToRightField);

		// topToBottom field
		topToBottomField = new SFBool(true);
		addField(topToBottomFieldName, topToBottomField);
	}

	public FontStyleNode(FontStyleNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Size
	////////////////////////////////////////////////

	public SFFloat getSizeField() {
		if (isInstanceNode() == false)
			return sizeField;
		return (SFFloat)getField(sizeFieldName);
	}

	public void setSize(float value) {
		getSizeField().setValue(value);
	}

	public void setSize(String value) {
		getSizeField().setValue(value);
	}
	
	public float getSize() {
		return getSizeField().getValue();
	}

	////////////////////////////////////////////////
	// Family
	////////////////////////////////////////////////

	public MFString getFamilyField() {
		if (isInstanceNode() == false)
			return familyField;
		return (MFString)getField(familyFieldName);
	}

	public void addFamily(String value) {
		getFamilyField().addValue(value);
	}
	
	public int getNFamilies() {
		return getFamilyField().getSize();
	}
	
	public void setFamily(int index, String value) {
		getFamilyField().set1Value(index, value);
	}

	public void setFamilies(String value) {
		getFamilyField().setValues(value);
	}

	public void setFamilies(String value[]) {
		getFamilyField().setValues(value);
	}
	
	public String getFamily(int index) {
		return getFamilyField().get1Value(index);
	}
	
	public void removeFamily(int index) {
		getFamilyField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	Style
	////////////////////////////////////////////////
	
	public SFString getStyleField() {
		if (isInstanceNode() == false)
			return styleField;
		return (SFString)getField(styleFieldName);
	}
	
	public void setStyle(String value) {
		getStyleField().setValue(value);
	}
	
	public String getStyle() {
		return getStyleField().getValue();
	}

	////////////////////////////////////////////////
	//	Language
	////////////////////////////////////////////////
	
	public SFString getLanguageField() {
		if (isInstanceNode() == false)
			return languageField;
		return (SFString)getField(languageFieldName);
	}
	
	public void setLanguage(String value) {
		getLanguageField().setValue(value);
	}
	
	public String getLanguage() {
		return getLanguageField().getValue();
	}

	////////////////////////////////////////////////
	//	Horizontal
	////////////////////////////////////////////////
	
	public SFBool getHorizontalField() {
		if (isInstanceNode() == false)
			return horizontalField;
		return (SFBool)getField(horizontalFieldName);
	}
	
	public void setHorizontal(boolean value) {
		getHorizontalField().setValue(value);
	}

	public void setHorizontal(String value) {
		getHorizontalField().setValue(value);
	}
	
	public boolean getHorizontal() {
		return getHorizontalField().getValue();
	}

	public boolean isHorizontal() {
		return getHorizontal();
	}

	////////////////////////////////////////////////
	//	LeftToRight
	////////////////////////////////////////////////
	
	public SFBool getLeftToRightField() {
		if (isInstanceNode() == false)
			return leftToRightField;
		return (SFBool)getField(leftToRightFieldName);
	}

	public void setLeftToRight(boolean value) {
		getLeftToRightField().setValue(value);
	}
	
	public void setLeftToRight(String value) {
		getLeftToRightField().setValue(value);
	}
	
	public boolean getLeftToRight() {
		return getLeftToRightField().getValue();
	}

	public boolean isLeftToRight() {
		return getLeftToRight();
	}

	////////////////////////////////////////////////
	//	TopToBottom
	////////////////////////////////////////////////

	public SFBool getTopToBottomField() {
		if (isInstanceNode() == false)
			return topToBottomField;
		return (SFBool)getField(topToBottomFieldName);
	}

	public void setTopToBottom(boolean value) {
		getTopToBottomField().setValue(value);
	}
	
	public void setTopToBottom(String value) {
		getTopToBottomField().setValue(value);
	}

	public boolean getTopToBottom() {
		return getTopToBottomField().getValue();
	}

	public boolean isTopToBottom() {
		return getTopToBottom();
	}

	////////////////////////////////////////////////
	// Justify
	////////////////////////////////////////////////

	public MFString getJustifyField() {
		if (isInstanceNode() == false)
			return justifyField;
		return (MFString)getField(justifyFieldName);
	}

	public void addJustify(String value) {
		getJustifyField().addValue(value);
	}
	
	public int getNJustifies() {
		return getJustifyField().getSize();
	}
	
	public void setJustify(int index, String value) {
		getJustifyField().set1Value(index, value);
	}

	public void setJustifies(String value) {
		getJustifyField().setValues(value);
	}

	public void setJustifies(String value[]) {
		getJustifyField().setValues(value);
	}
	
	public String getJustify(int index) {
		return getJustifyField().get1Value(index);
	}
	
	public void removeJustify(int index) {
		getJustifyField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	Spacing
	////////////////////////////////////////////////

	public SFFloat getSpacingField() {
		if (isInstanceNode() == false)
			return spacingField;
		return (SFFloat)getField(spacingFieldName);
	}

	public void setSpacing(float value) {
		getSpacingField().setValue(value);
	}

	public void setSpacing(String value) {
		getSpacingField().setValue(value);
	}
	
	public float getSpacing() {
		return getSpacingField().getValue();
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
	//	Justifymation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool horizontal = getHorizontalField();
		SFBool leftToRight = getLeftToRightField();
		SFBool topToBottom = getTopToBottomField();
		SFString style = getStyleField();
		SFString language = getLanguageField();

		printStream.println(indentString + "\t" + "size " + getSize() );
		printStream.println(indentString + "\t" + "style " + style );
		printStream.println(indentString + "\t" + "horizontal " + horizontal );
		printStream.println(indentString + "\t" + "leftToRight " + leftToRight );
		printStream.println(indentString + "\t" + "topToBottom " + topToBottom );
		printStream.println(indentString + "\t" + "language " + language );
		printStream.println(indentString + "\t" + "spacing " + getSpacing() );

		MFString family = getFamilyField();
		printStream.println(indentString + "\t" + "family [");
		family.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString justify = getJustifyField();
		printStream.println(indentString + "\t" + "justify [");
		justify.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
