/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : TimeTriggerNode.java
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

public class TimeTriggerNode extends Node 
{
	private String set_booleanFieldName = "set_boolean";
	private String triggerTimeFieldName = "triggerTime";
	
	private SFBool set_booleanField;
	private SFTime triggerTimeField;
	
	public TimeTriggerNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.TIMETRIGGER);

		// set_boolean eventIn field
		set_booleanField = new SFBool(false);
		addEventIn(set_booleanFieldName, set_booleanField);

		// triggerTime eventIn field
		triggerTimeField = new SFTime();
		addEventOut(triggerTimeFieldName, triggerTimeField);
	}

	public TimeTriggerNode(TimeTriggerNode node) 
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
	// TriggerTime
	////////////////////////////////////////////////

	public SFTime getTriggerTimeField() {
		if (isInstanceNode() == false)
			return triggerTimeField;
		return (SFTime)getEventOut(triggerTimeFieldName);
	}

	public void setTriggerTime(double value) {
		getTriggerTimeField().setValue(value);
	}
	
	public double getTriggerTime() {
		return getTriggerTimeField().getValue();
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
