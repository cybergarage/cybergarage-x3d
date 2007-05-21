/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2003
*
*	File : BooleanTriggerNode.java
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

public class BooleanTriggerNode extends Node
{
	private final static String set_triggerTimeFieldName = "set_triggerTime";
	private final static String triggerTrueFieldName = "triggerTrue";

	private 	SFTime set_triggerTimeField;
	private	SFBool triggerTrueField;
	
	public BooleanTriggerNode()
	{
		setHeaderFlag(false);
		setType(NodeType.BOOLEANTRIGGER);

		// set_triggerTime eventIn field
		set_triggerTimeField = new SFTime();
		addEventIn(set_triggerTimeFieldName, set_triggerTimeField);

		// triggerTrue eventOut field
		triggerTrueField = new SFBool();
		addEventOut(triggerTrueFieldName, triggerTrueField);
	}

	public BooleanTriggerNode(BooleanTriggerNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	set_triggerTime
	////////////////////////////////////////////////

	public SFTime getTriggerTimeField() {
		if (isInstanceNode() == false)
			return set_triggerTimeField;
		return (SFTime)getEventIn(set_triggerTimeFieldName);
	}

	public void setTriggerTime(double value) {
		getTriggerTimeField().setValue(value);
	}

	public double getTriggerTime() {
		return getTriggerTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	triggerTrue
	////////////////////////////////////////////////

	public SFBool getTriggerTrueField() {
		if (isInstanceNode() == false)
			return triggerTrueField;
		return (SFBool)getEventOut(triggerTrueFieldName);
	}

	public void setTriggerTrue(boolean value) {
		getTriggerTrueField().setValue(value);
	}

	public void setTriggerTrue(String value) {
		getTriggerTrueField().setValue(value);
	}

	public boolean getTriggerTrue() {
		return getTriggerTrueField().getValue();
	}

	public boolean isTriggerTrue() {
		return getTriggerTrue();
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
