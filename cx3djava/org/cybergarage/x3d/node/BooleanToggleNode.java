/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2003
*
*	File : BooleanToggleNode.java
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

public class BooleanToggleNode extends Node
{
	private final static String set_booleanFieldName = "set_boolean";
	private final static String toggleFieldName = "toggle";

	private 	SFBool set_booleanField;
	private	SFBool toggleField;
	
	public BooleanToggleNode()
	{
		setHeaderFlag(false);
		setType(NodeType.BOOLEANTOGGLE);

		// set_boolean eventIn field
		set_booleanField = new SFBool();
		addEventIn(set_booleanFieldName, set_booleanField);

		// toggle eventOut field
		toggleField = new SFBool();
		addExposedField(toggleFieldName, toggleField);
	}

	public BooleanToggleNode(BooleanToggleNode node) 
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
	//	toggle
	////////////////////////////////////////////////

	public SFBool getToggleField() {
		if (isInstanceNode() == false)
			return toggleField;
		return (SFBool)getExposedField(toggleFieldName);
	}

	public void setToggle(boolean value) {
		getToggleField().setValue(value);
	}

	public void setToggle(String value) {
		getToggleField().setValue(value);
	}

	public boolean getToggle() {
		return getToggleField().getValue();
	}

	public boolean isToggle() {
		return getToggle();
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
