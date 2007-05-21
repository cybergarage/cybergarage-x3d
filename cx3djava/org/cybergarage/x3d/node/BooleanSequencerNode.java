/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : BooleanSequencerNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class BooleanSequencerNode extends SequencerNode 
{
	private String keyValueFieldName = "keyValue";

	private MFBool keyValueField;
	private SFBool valueField;

	public BooleanSequencerNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.BOOLEANSEQUENCER);

		// keyValue exposed field
		keyValueField = new MFBool();
		addExposedField(keyValueFieldName, keyValueField);

		// value_changed eventOut field
		valueField = new SFBool();
		addEventOut(valueFieldName, valueField);
	}

	public BooleanSequencerNode(BooleanSequencerNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	keyValue
	////////////////////////////////////////////////

	public MFBool getKeyValueField() {
		if (isInstanceNode() == false)
			return keyValueField;
		return (MFBool)getExposedField(keyValueFieldName);
	}

	public void addKeyValue(boolean value) {
		getKeyValueField().addValue(value);
	}

	public int getNKeyValues() {
		return getKeyValueField().getSize();
	}
	
	public void setKeyValue(int index, boolean value) {
		getKeyValueField().set1Value(index, value);
	}

	public void setKeyValues(String value) {
		getKeyValueField().setValues(value);
	}

	public void setKeyValues(String value[]) {
		getKeyValueField().setValues(value);
	}

	public boolean getKeyValue(int index) {
		return getKeyValueField().get1Value(index);
	}

	public void removeKeyValue(int index) {
		getKeyValueField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	value
	////////////////////////////////////////////////

	public SFBool getValueField() {
		if (isInstanceNode() == false)
			return valueField;
		return (SFBool)getEventOut(valueFieldName);
	}
	
	public void setValue(boolean value) {
		getValueField().setValue(value);
	}

	public void setValue(String value) {
		getValueField().setValue(value);
	}

	public boolean getValue() {
		return getValueField().getValue();
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
/*
		float fraction = getFraction();
		int index = -1;
		for (int n=0; n<(getNKeys()-1); n++) {
			if (getKey(n) <= fraction && fraction <= getKey(n+1)) {
				index = n;
				break;
			}
		}
		if (index == -1)
			return;

		float scale = (fraction - getKey(index)) / (getKey(index+1) - getKey(index));


		float value1 = getKeyValue(index);
		float value2 = getKeyValue(index+1);
		float valueOut = value1 + (value2 - value1)*scale;

		setValue(valueOut);
		sendEvent(getValueField());
*/
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}