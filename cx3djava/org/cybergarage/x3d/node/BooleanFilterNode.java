/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2003
*
*	File : BooleanFilterNode.java
*
*	Revisions:
*
*	01/08/03
*		- first revision
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class BooleanFilterNode extends Node
{
	private final static String set_booleanFieldName = "set_boolean";
	private final static String inputFalseFieldName = "inputFalse";
	private final static String inputNegateFieldName = "inputNegate";
	private final static String inputTrueFieldName = "inputTrue";

	private 	SFBool set_booleanField;
	private	SFBool inputFalseField;
	private	SFBool inputNegateField;
	private	SFBool inputTrueField;
	
	public BooleanFilterNode()
	{
		setHeaderFlag(false);
		setType(NodeType.BOOLEANFILTER);

		// set_boolean eventIn field
		set_booleanField = new SFBool();
		addEventIn(set_booleanFieldName, set_booleanField);

		// inputFalse eventOut field
		inputFalseField = new SFBool();
		addEventOut(inputFalseFieldName, inputFalseField);

		// inputNegate eventOut field
		inputNegateField = new SFBool();
		addEventOut(inputNegateFieldName, inputNegateField);

		// inputTrue eventOut field
		inputTrueField = new SFBool();
		addEventOut(inputTrueFieldName, inputTrueField);

	}

	public BooleanFilterNode(BooleanFilterNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	set_boolean
	////////////////////////////////////////////////

	public SFBool getBooleanField() {
		if (isInstanceNode() == false)
			return set_booleanField;
		return (SFBool)getEventIn(set_booleanFieldName);
	}

	public void setBoolean(boolean value) {
		getBooleanField().setValue(value);
	}

	public void setBoolean(String value) {
		getBooleanField().setValue(value);
	}

	public boolean getBoolean() {
		return getBooleanField().getValue();
	}

	public boolean isBoolean() {
		return getBoolean();
	}

	////////////////////////////////////////////////
	//	inputFalse
	////////////////////////////////////////////////

	public SFBool getInputFalseField() {
		if (isInstanceNode() == false)
			return inputFalseField;
		return (SFBool)getEventOut(inputFalseFieldName);
	}

	public void setInputFalse(boolean value) {
		getInputFalseField().setValue(value);
	}

	public void setInputFalse(String value) {
		getInputFalseField().setValue(value);
	}

	public boolean getInputFalse() {
		return getInputFalseField().getValue();
	}

	public boolean isInputFalse() {
		return getInputFalse();
	}

	////////////////////////////////////////////////
	//	inputNegate
	////////////////////////////////////////////////

	public SFBool getInputNegateField() {
		if (isInstanceNode() == false)
			return inputNegateField;
		return (SFBool)getEventOut(inputNegateFieldName);
	}

	public void setInputNegate(boolean value) {
		getInputNegateField().setValue(value);
	}

	public void setInputNegate(String value) {
		getInputNegateField().setValue(value);
	}

	public boolean getInputNegate() {
		return getInputNegateField().getValue();
	}

	public boolean isInputNegate() {
		return getInputNegate();
	}

	////////////////////////////////////////////////
	//	inputTrue
	////////////////////////////////////////////////

	public SFBool getInputTrueField() {
		if (isInstanceNode() == false)
			return inputTrueField;
		return (SFBool)getEventOut(inputTrueFieldName);
	}

	public void setInputTrue(boolean value) {
		getInputTrueField().setValue(value);
	}

	public void setInputTrue(String value) {
		getInputTrueField().setValue(value);
	}

	public boolean getInputTrue() {
		return getInputTrueField().getValue();
	}

	public boolean isInputTrue() {
		return getInputTrue();
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
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}
