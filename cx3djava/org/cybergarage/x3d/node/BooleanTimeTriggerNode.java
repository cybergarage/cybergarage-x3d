/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : BooleanTimeTriggerNode.java

*	Revisions:
*
*	10/08/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class BooleanTimeTriggerNode extends TriggerNode 
{
	private String set_booleanTrueFieldName = "set_booleanTrue";
	private String set_booleanFalseFieldName = "set_booleanFalse";
	private String trueTriggerFieldName = "trueTrigger";
	private String falseTriggerFieldName = "falseTrigger";

	private SFBool set_booleanTrueField;
	private SFBool set_booleanFalseField;
	private SFBool trueTriggerField;
	private SFBool falseTriggerField;
	
	public BooleanTimeTriggerNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.BOOLEANTIMETRIGGER);

		// set_booleanTrueField eventIn field
		set_booleanTrueField = new SFBool(false);
		addEventIn(set_booleanTrueFieldName, set_booleanTrueField);

		// set_booleanFalseField eventIn field
		set_booleanFalseField = new SFBool(true);
		addEventIn(set_booleanFalseFieldName, set_booleanFalseField);

		// trueTrigger eventOut field
		trueTriggerField = new SFBool();
		addEventOut(trueTriggerFieldName, trueTriggerField);

		// falseTrigger eventOut field
		falseTriggerField = new SFBool();
		addEventOut(falseTriggerFieldName, falseTriggerField);
	}

	public BooleanTimeTriggerNode(BooleanTimeTriggerNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	// SetBooleanTrue
	////////////////////////////////////////////////

	public SFBool getSetBooleanTrueField() {
		if (isInstanceNode() == false)
			return set_booleanTrueField;
		return (SFBool)getEventIn(set_booleanTrueFieldName);
	}

	public void setSetBooleanTrue(boolean value) {
		getSetBooleanTrueField().setValue(value);
	}
	
	public boolean getSetBooleanTrue() {
		return getSetBooleanTrueField().getValue();
	}

	////////////////////////////////////////////////
	// SetBooleanFalse
	////////////////////////////////////////////////

	public SFBool getSetBooleanFalseField() {
		if (isInstanceNode() == false)
			return set_booleanFalseField;
		return (SFBool)getEventIn(set_booleanFalseFieldName);
	}

	public void setSetBooleanFalse(boolean value) {
		getSetBooleanFalseField().setValue(value);
	}
	
	public boolean getSetBooleanFalse() {
		return getSetBooleanFalseField().getValue();
	}

	////////////////////////////////////////////////
	// TrueTrigger
	////////////////////////////////////////////////

	public SFBool getTrueTriggerField() {
		if (isInstanceNode() == false)
			return trueTriggerField;
		return (SFBool)getEventOut(trueTriggerFieldName);
	}

	public void setTrueTrigger(boolean value) {
		getTrueTriggerField().setValue(value);
	}
	
	public boolean getTrueTrigger() {
		return getTrueTriggerField().getValue();
	}

	////////////////////////////////////////////////
	// FalseTrigger
	////////////////////////////////////////////////

	public SFBool getFalseTriggerField() {
		if (isInstanceNode() == false)
			return falseTriggerField;
		return (SFBool)getEventOut(falseTriggerFieldName);
	}

	public void setFalseTrigger(boolean value) {
		getFalseTriggerField().setValue(value);
	}
	
	public boolean getFalseTrigger() {
		return getFalseTriggerField().getValue();
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
