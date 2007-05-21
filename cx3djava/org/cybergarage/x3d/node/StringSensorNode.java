/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : StringSensorNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class StringSensorNode extends KeyDeviceSensorNode 
{
	private String deletionCharacterFieldName = "deletionCharacter";
	private String profileFieldName = "profile";
	private String enteredTextFieldName = "enteredText";
	private String finalTextFieldName = "finalText";
	private String terminationTextFieldName = "terminationText";

	private SFInt32 deletionCharacterField;
	private SFInt32 profileField;
	private SFString enteredTextField;
	private SFString finalTextField;
	private SFString terminationTextField;

	public StringSensorNode()
	{
		super();
		setHeaderFlag(false);
		setType(NodeType.STRINGSENSOR);
		
		// deletionCharacterField eventOut field
		deletionCharacterField = new SFInt32(0);
		addEventOut(deletionCharacterFieldName, deletionCharacterField);

		// profileField eventOut field
		profileField = new SFInt32(0);
		addEventOut(profileFieldName, profileField);

		// enteredTextField eventOut field
		enteredTextField = new SFString();
		addEventOut(enteredTextFieldName, enteredTextField);

		// finalTextField eventOut field
		finalTextField = new SFString();
		addEventOut(finalTextFieldName, finalTextField);

		// terminationTextField eventOut field
		terminationTextField = new SFString();
		addEventOut(terminationTextFieldName, terminationTextField);
	}

	public StringSensorNode(StringSensorNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	deletionCharacter
	////////////////////////////////////////////////

	public SFInt32 getDeletionCharacterField() {
		if (isInstanceNode() == false)
			return deletionCharacterField;
		return (SFInt32)getEventOut(deletionCharacterFieldName);
	}
	
	public void setDeletionCharacter(int value) {
		getDeletionCharacterField().setValue(value);
	}

	public int getDeletionCharacter() {
		return getDeletionCharacterField().getValue();
	}

	////////////////////////////////////////////////
	//	profile
	////////////////////////////////////////////////

	public SFInt32 getProfileField() {
		if (isInstanceNode() == false)
			return profileField;
		return (SFInt32)getEventOut(profileFieldName);
	}
	
	public void setProfile(int value) {
		getProfileField().setValue(value);
	}

	public int getProfile() {
		return getProfileField().getValue();
	}

	////////////////////////////////////////////////
	//	enteredText
	////////////////////////////////////////////////

	public SFString getEnteredTextField() {
		if (isInstanceNode() == false)
			return enteredTextField;
		return (SFString)getEventOut(enteredTextFieldName);
	}
	
	public void setEnteredText(String value) {
		getEnteredTextField().setValue(value);
	}

	public String getEnteredText() {
		return getEnteredTextField().getValue();
	}

	////////////////////////////////////////////////
	//	finalText
	////////////////////////////////////////////////

	public SFString getFinalTextField() {
		if (isInstanceNode() == false)
			return finalTextField;
		return (SFString)getEventOut(finalTextFieldName);
	}
	
	public void setFinalText(String value) {
		getFinalTextField().setValue(value);
	}

	public String getFinalText() {
		return getFinalTextField().getValue();
	}

	////////////////////////////////////////////////
	//	terminationText
	////////////////////////////////////////////////

	public SFString getTerminationTextField() {
		if (isInstanceNode() == false)
			return terminationTextField;
		return (SFString)getEventOut(terminationTextFieldName);
	}
	
	public void setTerminationText(String value) {
		getTerminationTextField().setValue(value);
	}

	public String getTerminationText() {
		return getTerminationTextField().getValue();
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
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
}
