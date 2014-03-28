/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : PositionInterpolator.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class PositionInterpolator2DNode extends InterpolatorNode {

	private String	keyValueFieldName	= "keyValue";

	private MFVec2f keyValueField;
	private SFVec2f valueField;

	public PositionInterpolator2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.POSITIONINTERPOLATOR2D);

		// keyValue exposed field
		keyValueField = new MFVec2f();
		addExposedField(keyValueFieldName, keyValueField);

		// value_changed eventOut field
		valueField = new SFVec2f(0.0f, 0.0f);
		addEventOut(valueFieldName, valueField);
	}

	public PositionInterpolator2DNode(PositionInterpolator2DNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	keyValue
	////////////////////////////////////////////////

	public MFVec2f getKeyValueField() {
		if (isInstanceNode() == false)
			return keyValueField;
		return (MFVec2f)getExposedField(keyValueFieldName);
	}
	
	public void addKeyValue(float vector[]) {
		getKeyValueField().addValue(vector);
	}

	public void addKeyValue(float x, float y) {
		getKeyValueField().addValue(x, y);
	}

	public int getNKeyValues() {
		return getKeyValueField().getSize();
	}

	public void setKeyValue(int index, float vector[]) {
		getKeyValueField().set1Value(index, vector);
	}
	
	public void setKeyValue(int index, float x, float y) {
		getKeyValueField().set1Value(index, x, y);
	}

	public void setKeyValues(String value) {
		getKeyValueField().setValues(value);
	}

	public void setKeyValues(String value[]) {
		getKeyValueField().setValues(value);
	}

	public void getKeyValue(int index, float vector[]) {
		getKeyValueField().get1Value(index, vector);
	}

	public float[] getKeyValue(int index) {
		float value[] = new float[2];
		getKeyValue(index, value);
		return value;
	}

	public void removeKeyValue(int index) {
		getKeyValueField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	value
	////////////////////////////////////////////////

	public SFVec2f getValueField() {
		if (isInstanceNode() == false)
			return valueField;
		return (SFVec2f)getEventOut(valueFieldName);
	}
	
	public void setValue(float vector[]) {
		getValueField().setValue(vector);
	}

	public void setValue(String vector) {
		getValueField().setValue(vector);
	}

	public void getValue(float vector[]) {
		getValueField().getValue(vector);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
	}

	public void uninitialize() {
	}

	public void update() {

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

		float vector1[] = new float[2];
		float vector2[] = new float[2];
		float vectorOut[] = new float[2];

		getKeyValue(index, vector1);
		getKeyValue(index+1, vector2);
		for (int n=0; n<2; n++)
			vectorOut[n] = vector1[n] + (vector2[n] - vector1[n])*scale;

		setValue(vectorOut);
		sendEvent(getValueField());
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}