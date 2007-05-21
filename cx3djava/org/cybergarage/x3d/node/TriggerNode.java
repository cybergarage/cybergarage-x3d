/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : TriggerNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class TriggerNode extends Node 
{
	private String set_triggerTimeFieldName = "set_triggerTime";
	private String triggerTimeFieldName = "triggerTime";

	private SFTime set_triggerTimeField;
	private SFTime triggerTimeField;

	public TriggerNode() 
	{
		// enabled exposed field
		set_triggerTimeField = new SFTime(0);
		addEventIn(set_triggerTimeFieldName, set_triggerTimeField);

		// isActive eventOut field
		triggerTimeField = new SFTime();
		addEventOut(triggerTimeFieldName, triggerTimeField);
	}

	////////////////////////////////////////////////
	//	SetTriggerTime
	////////////////////////////////////////////////

	public SFTime getSetTriggerTimeField() 
	{
		if (isInstanceNode() == false)
			return set_triggerTimeField;
		return (SFTime)getEventIn(set_triggerTimeFieldName);
	}
	
	public void setSetTriggerTime(double value) 
	{
		getSetTriggerTimeField().setValue(value);
	}
	
	public double getSetTriggerTime() 
	{
		return getSetTriggerTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	TriggerTime
	////////////////////////////////////////////////

	public SFTime getTriggerTimeField() 
	{
		if (isInstanceNode() == false)
			return triggerTimeField;
		return (SFTime)getEventOut(triggerTimeFieldName);
	}
	
	public void setTriggerTime(double value) 
	{
		getTriggerTimeField().setValue(value);
	}
	
	public double getTriggerTime() 
	{
		return getTriggerTimeField().getValue();
	}

}
