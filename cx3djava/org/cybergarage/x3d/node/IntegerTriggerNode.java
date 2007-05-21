/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : IntegerTriggerNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class IntegerTriggerNode extends Node 
{
	private String set_booleanFieldName = "set_boolean";
	private String integerKeyFieldName = "integerKey";
	private String integerValueFieldName = "integerValue";
	
	private SFBool set_booleanField;
	private SFInt32 integerKeyField;
	private SFInt32 integerValueField;
	
	public IntegerTriggerNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.INTEGERTRIGGER);

		// set_boolean eventIn field
		set_booleanField = new SFBool(false);
		addEventIn(set_booleanFieldName, set_booleanField);

		// integerKey exposed field
		integerKeyField = new SFInt32(-1);
		addExposedField(integerKeyFieldName, integerKeyField);

		// integerValue eventIn field
		integerValueField = new SFInt32();
		addEventOut(integerValueFieldName, integerValueField);
	}

	public IntegerTriggerNode(IntegerTriggerNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	// Boolean
	////////////////////////////////////////////////

	public SFBool getBooleanField() {
		if (isInstanceNode() == false)
			return set_booleanField;
		return (SFBool)getEventIn(set_booleanFieldName);
	}

	public void setBoolean(boolean value) {
		getBooleanField().setValue(value);
	}
	
	public boolean getBoolean() {
		return getBooleanField().getValue();
	}

	////////////////////////////////////////////////
	// IntegetKey
	////////////////////////////////////////////////

	public SFInt32 getIntegetKeyField() {
		if (isInstanceNode() == false)
			return integerKeyField;
		return (SFInt32)getExposedField(integerKeyFieldName);
	}

	public void setIntegetKey(int value) {
		getIntegetKeyField().setValue(value);
	}
	
	public int getIntegetKey() {
		return getIntegetKeyField().getValue();
	}

	////////////////////////////////////////////////
	// TriggerValue
	////////////////////////////////////////////////

	public SFInt32 getTriggerValueField() {
		if (isInstanceNode() == false)
			return integerValueField;
		return (SFInt32)getEventOut(integerValueFieldName);
	}

	public void setTriggerValue(int value) {
		getTriggerValueField().setValue(value);
	}
	
	public int getTriggerValue() {
		return getTriggerValueField().getValue();
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
