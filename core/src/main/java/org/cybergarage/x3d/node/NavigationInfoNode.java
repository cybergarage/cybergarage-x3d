/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : NavigationInfo.java
*
*	03/25/04
*		- Joerg Scheurich aka MUFTI <rusmufti@helpdesk.rus.uni-stuttgart.de>
*		- Fixed the default value of the headlight field to "true".
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class NavigationInfoNode extends BindableNode {
	
	//// Exposed Field ////////////////
	private String	visibilityLimitExposedFieldName	= "visibilityLimit";
	private String	avatarSizeExposedFieldName		= "avatarSize";
	private String	typeExposedFieldName			= "type";
	private String	headlightExposedFieldName		= "headlight";
	private String	speedExposedFieldName			= "speed";

	private SFFloat		visibilityLimitField;
	private MFFloat		avatarSizeField;
	private MFString		typeField;
	private SFBool		headlightField;
	private SFFloat		speedField;

	public NavigationInfoNode() {
		setHeaderFlag(false);
		setType(NodeType.NAVIGATIONINFO);

		///////////////////////////
		// Exposed Field 
		///////////////////////////

		// visibilityLimit exposed field
		visibilityLimitField = new SFFloat(0);
		addExposedField(visibilityLimitExposedFieldName, visibilityLimitField);

		// avatarSize exposed field
		avatarSizeField = new MFFloat();
		addExposedField(avatarSizeExposedFieldName, avatarSizeField);

		// type exposed field
		typeField = new MFString();
		addExposedField(typeExposedFieldName, typeField);

		// headlight exposed field
		// Thanks for Joerg Scheurich aka MUFTI (03/25/04)
		headlightField = new SFBool(true);
		addExposedField(headlightExposedFieldName, headlightField);

		// speed exposed field
		speedField = new SFFloat(1);
		addExposedField(speedExposedFieldName, speedField);
	}

	public NavigationInfoNode(NavigationInfoNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	// Type
	////////////////////////////////////////////////

	public MFString getTypeField() {
		if (isInstanceNode() == false)
			return typeField;
		return (MFString)getExposedField(typeExposedFieldName);
	}

	public void addType(String value) {
		getTypeField().addValue(value);
	}
	
	public int getNTypes() {
		return getTypeField().getSize();
	}
	
	public void setType(int index, String value) {
		getTypeField().set1Value(index, value);
	}

	public void setTypes(String value) {
		getTypeField().setValues(value);
	}

	public void setTypes(String value[]) {
		getTypeField().setValues(value);
	}
	
	public String getType(int index) {
		return getTypeField().get1Value(index);
	}
	
	public void removeType(int index) {
		getTypeField().removeValue(index);
	}

	////////////////////////////////////////////////
	// avatarSize
	////////////////////////////////////////////////

	public MFFloat getAvatarSizeField() {
		if (isInstanceNode() == false)
			return avatarSizeField;
		return (MFFloat)getExposedField(avatarSizeExposedFieldName);
	}

	public void addAvatarSize(float value) {
		getAvatarSizeField().addValue(value);
	}
	
	public int getNAvatarSizes() {
		return getAvatarSizeField().getSize();
	}
	
	public void setAvatarSize(int index, float value) {
		getAvatarSizeField().set1Value(index, value);
	}

	public void setAvatarSizes(String value) {
		getAvatarSizeField().setValues(value);
	}

	public void setAvatarSizes(String value[]) {
		getAvatarSizeField().setValues(value);
	}
	
	public float getAvatarSize(int index) {
		return getAvatarSizeField().get1Value(index);
	}
	
	public void removeAvatarSize(int index) {
		getAvatarSizeField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	Headlight
	////////////////////////////////////////////////

	public SFBool getHeadlightField() {
		if (isInstanceNode() == false)
			return headlightField;
		return (SFBool)getExposedField(headlightExposedFieldName);
	}
	
	public void setHeadlight(boolean value) {
		getHeadlightField().setValue(value);
	}

	public void setHeadlight(String value) {
		getHeadlightField().setValue(value);
	}
	
	public boolean getHeadlight() {
		return getHeadlightField().getValue();
	}

	////////////////////////////////////////////////
	//	VisibilityLimit
	////////////////////////////////////////////////

	public SFFloat getVisibilityLimitField() {
		if (isInstanceNode() == false)
			return visibilityLimitField;
		return (SFFloat)getExposedField(visibilityLimitExposedFieldName);
	}

	public void setVisibilityLimit(float value) {
		getVisibilityLimitField().setValue(value);
	}

	public void setVisibilityLimit(String value) {
		getVisibilityLimitField().setValue(value);
	}
	
	public float getVisibilityLimit() {
		return getVisibilityLimitField().getValue();
	}

	////////////////////////////////////////////////
	//	Speed
	////////////////////////////////////////////////

	public SFFloat getSpeedField() {
		if (isInstanceNode() == false)
			return speedField;
		return (SFFloat)getExposedField(speedExposedFieldName);
	}
	
	public void setSpeed(float value) {
		getSpeedField().setValue(value);
	}

	public void setSpeed(String value) {
		getSpeedField().setValue(value);
	}
	
	public float getSpeed() {
		return getSpeedField().getValue();
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
	//	infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool headlight = getHeadlightField();

		printStream.println(indentString + "\t" + "visibilityLimit " + getVisibilityLimit());
		printStream.println(indentString + "\t" + "headlight " + headlight );
		printStream.println(indentString + "\t" + "speed " + getSpeed() );

		MFString type = getTypeField();
		printStream.println(indentString + "\t" + "type [");
		type.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFFloat avatarSize = getAvatarSizeField();
		printStream.println(indentString + "\t" + "avatarSize [");
		avatarSize.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
