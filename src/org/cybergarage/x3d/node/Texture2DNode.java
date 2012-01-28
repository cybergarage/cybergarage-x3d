/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: Texture2DNode.java
*
*	Revisions:
*
*	12/05/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class Texture2DNode extends TextureNode 
{
	private final static String repeatSFieldName = "repeatS";
	private final static String repeatTFieldName = "repeatT";

	private SFBool repeatSField;
	private SFBool repeatTField;

	public Texture2DNode() 
	{
		///////////////////////////
		// Field 
		///////////////////////////

		// repeatS field
		repeatSField = new SFBool(true);
		addField(repeatSFieldName, repeatSField);

		// repeatT field
		repeatTField = new SFBool(true);
		addField(repeatTFieldName, repeatTField);
	}

	////////////////////////////////////////////////
	//	RepeatS
	////////////////////////////////////////////////

	public SFBool getRepeatSField() {
		if (isInstanceNode() == false)
			return repeatSField;
		return (SFBool)getField(repeatSFieldName);
	}
	
	public void setRepeatS(boolean value) {
		getRepeatSField().setValue(value);
	}

	public void setRepeatS(String value) {
		getRepeatSField().setValue(value);
	}
	
	public boolean getRepeatS() {
		return getRepeatSField().getValue();
	}

	////////////////////////////////////////////////
	//	RepeatT
	////////////////////////////////////////////////

	public SFBool getRepeatTField() {
		if (isInstanceNode() == false)
			return repeatTField;
		return (SFBool)getField(repeatTFieldName);
	}
	
	public void setRepeatT(boolean value) {
		getRepeatTField().setValue(value);
	}

	public void setRepeatT(String value) {
		getRepeatTField().setValue(value);
	}
	
	public boolean getRepeatT() {
		return getRepeatTField().getValue();
	}


}

