/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : CoordinateInterpolator2DNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.util.*;

public class CoordinateInterpolator2DNode extends InterpolatorNode 
{

	private String	keyValueFieldName	= "keyValue";

	private MFVec2f keyValueField;
	private MFVec2f valueField;

	public CoordinateInterpolator2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.COORDINATEINTERPOLATOR2D);

		// keyValue exposed field
		keyValueField = new MFVec2f();
		addExposedField(keyValueFieldName, keyValueField);

		// value_changed eventOut field
		valueField = new MFVec2f();
		addEventOut(valueFieldName, valueField);
	}

	public CoordinateInterpolator2DNode(CoordinateInterpolator2DNode node) {
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

	public MFVec2f getValueField() {
		if (isInstanceNode() == false)
			return valueField;
		return (MFVec2f)getEventOut(valueFieldName);
	}
	
	public void setValue(float vector[][]) {
		getValueField().setValues(vector);
	}

	public float[][] getValue() {
		return getValueField().getValues();
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

		Debug.message("CoordinateInterpolator2DNode::update");
		
		float fraction = getFraction();
		int index = -1;
		int nKeys = getNKeys();

		//Debug.message("fraction = " + fraction);
		//Debug.message("nKeys = " + nKeys);

		for (int n=0; n<(nKeys-1); n++) {
			if (getKey(n) <= fraction && fraction <= getKey(n+1)) {
				index = n;
				break;
			}
		}

		Debug.message("index = " + index);

		if (index == -1)
			return;

		float scale = (fraction - getKey(index)) / (getKey(index+1) - getKey(index));

		int nKeyValues = getNKeyValues();
		int nCoodPerKeys = nKeyValues / nKeys;

		//Debug.message("scale = " + scale);
		//Debug.message("nKeyValues = " + nKeyValues);
		//Debug.message("nCoodPerKeys = " + nCoodPerKeys);

		int beginCoodIdx = index * nCoodPerKeys	;
		int endCoordIdx = (index+1) * nCoodPerKeys;

		//Debug.message("scale = " + scale);
		//Debug.message("nKeyValues = " + nKeyValues);
		//Debug.message("nCoodPerKeys = " + nCoodPerKeys);
		//Debug.message("beginCoodIdx = " + beginCoodIdx);
		//Debug.message("endCoordIdx = " + endCoordIdx);

		float vector1[] = new float[2];
		float vector2[] = new float[2];
		float vectorOut[][] = new float[nCoodPerKeys][2];
		
		for (int i=0; i<nCoodPerKeys; i++) {
			getKeyValue(beginCoodIdx+i, vector1);
			getKeyValue(endCoordIdx+i, vector2);
			for (int n=0; n<2; n++)
				vectorOut[i][n] = vector1[n] + (vector2[n] - vector1[n])*scale;
		}

		setValue(vectorOut);
		sendEvent(getValueField());
		
		//PrintWriter pw = new PrintWriter(System.out);
		//getValueField().outputContext(pw, "");
		//pw.flush();
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}