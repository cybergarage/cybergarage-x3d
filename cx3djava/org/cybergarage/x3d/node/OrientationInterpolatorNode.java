/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : OrientationInterpolator.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class OrientationInterpolatorNode extends InterpolatorNode {

	private String	keyValueFieldName	= "keyValue";

	private MFRotation keyValueField;
	private SFRotation valueField;

	public OrientationInterpolatorNode() {
		setHeaderFlag(false);
		setType(NodeType.ORIENTATIONINTERP);

		// keyValue exposed field
		keyValueField = new MFRotation();
		addExposedField(keyValueFieldName, keyValueField);

		// value_changed eventOut field
		valueField = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		addEventOut(valueFieldName, valueField);
	}

	public OrientationInterpolatorNode(OrientationInterpolatorNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	keyValue
	////////////////////////////////////////////////

	public MFRotation getKeyValueField() {
		if (isInstanceNode() == false)
			return keyValueField;
		return (MFRotation)getExposedField(keyValueFieldName);
	}

	public void addKeyValue(float rotation[]) {
		getKeyValueField().addValue(rotation);
	}

	public void addKeyValue(float x, float y, float z, float angle) {
		getKeyValueField().addValue(x, y, z, angle);
	}

	public int getNKeyValues() {
		return getKeyValueField().getSize();
	}
	
	public void setKeyValue(int index, float rotation[]) {
		getKeyValueField().set1Value(index, rotation);
	}

	public void setKeyValue(int index, float x, float y, float z, float angle) {
		getKeyValueField().set1Value(index, x, y, z, angle);
	}

	public void setKeyValues(String value) {
		getKeyValueField().setValues(value);
	}

	public void setKeyValues(String value[]) {
		getKeyValueField().setValues(value);
	}

	public void getKeyValue(int index, float rotation[]) {
		getKeyValueField().get1Value(index, rotation);
	}

	public float[] getKeyValue(int index) {
		float value[] = new float[4];
		getKeyValue(index, value);
		return value;
	}

	public void removeKeyValue(int index) {
		getKeyValueField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	value
	////////////////////////////////////////////////
	
	public SFRotation getValueField() {
		if (isInstanceNode() == false)
			return valueField;
		return (SFRotation)getEventOut(valueFieldName);
	}
	
	public void setValue(float rotation[]) {
		getValueField().setValue(rotation);
	}

	public void setValue(String rotation) {
		getValueField().setValue(rotation);
	}

	public void getValue(float rotation[]) {
		getValueField().getValue(rotation);
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

		float rotation1[] = new float[4];
		float rotation2[] = new float[4];
		float rotationOut[] = new float[4];

		getKeyValue(index, rotation1);
		getKeyValue(index+1, rotation2);
		for (int n=0; n<4; n++)
			rotationOut[n] = rotation1[n] + (rotation2[n] - rotation1[n])*scale;

		setValue(rotationOut);
		sendEvent(getValueField());
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {

		printStream.println(indentString + "\tkey [");
		for (int n=0; n<getNKeys(); n++) {
			if (n < getNKeys()-1)
				printStream.println(indentString + "\t\t" + getKey(n));
			else	
				printStream.println(indentString + "\t\t" + getKey(n));
		}
		printStream.println(indentString + "\t]");
	
		float rotation[] = new float[4];
		printStream.println(indentString + "\tkeyValue [");
		for (int n=0; n<getNKeyValues(); n++) {
			getKeyValue(n, rotation);
			if (n < getNKeyValues()-1)
				printStream.println(indentString + "\t\t" + rotation[X] + " " + rotation[Y] + " " + rotation[Z] + " " + rotation[3] + ",");
			else	
				printStream.println(indentString + "\t\t" + rotation[X] + " " + rotation[Y] + " " + rotation[Z] + " " + rotation[3]);
		}
		printStream.println(indentString + "\t]");
	}
}