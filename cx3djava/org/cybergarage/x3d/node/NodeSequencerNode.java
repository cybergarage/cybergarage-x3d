/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : NodeSequencerNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class NodeSequencerNode extends InterpolatorNode 
{
	private String keyValueFieldName = "keyValue";

	private MFNode keyValueField;
	private SFNode valueField;

	public NodeSequencerNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.NODESEQUENCER);

		// keyValue exposed field
		keyValueField = new MFNode();
		addExposedField(keyValueFieldName, keyValueField);

		// value_changed eventOut field
		valueField = new SFNode();
		addEventOut(valueFieldName, valueField);
	}

	public NodeSequencerNode(NodeSequencerNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	keyValue
	////////////////////////////////////////////////

	public MFNode getKeyValueField() {
		if (isInstanceNode() == false)
			return keyValueField;
		return (MFNode)getExposedField(keyValueFieldName);
	}

	public void addKeyValue(Node value) {
		getKeyValueField().addValue(value);
	}

	public int getNKeyValues() {
		return getKeyValueField().getSize();
	}
	
	public void setKeyValue(int index, Node value) {
		getKeyValueField().set1Value(index, value);
	}

	public void setKeyValues(String value) {
		getKeyValueField().setValues(value);
	}

	public void setKeyValues(String value[]) {
		getKeyValueField().setValues(value);
	}

	public Node getKeyValue(int index) {
		return getKeyValueField().get1Value(index);
	}

	public void removeKeyValue(int index) {
		getKeyValueField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	value
	////////////////////////////////////////////////

	public SFNode getValueField() {
		if (isInstanceNode() == false)
			return valueField;
		return (SFNode)getEventOut(valueFieldName);
	}
	
	public void setValue(Node value) {
		getValueField().setValue(value);
	}

	public void setValue(String value) {
		getValueField().setValue(value);
	}

	public Node getValue() {
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

		setValue((int)valueOut);
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