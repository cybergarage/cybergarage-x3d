/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2003
*
*	File : SequencerNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class SequencerNode extends Node 
{
	private final static String keyFieldName = "key";

	private MFFloat keyField;
	private SFFloat fractionField;

	public SequencerNode() 
	{
		setHeaderFlag(false);

		// key exposed field
		keyField = new MFFloat();
		addExposedField(keyFieldName, keyField);

		// set_fraction eventIn field
		fractionField = new SFFloat(0.0f);
		addEventIn(fractionFieldName, fractionField);
	}

	////////////////////////////////////////////////
	//	key
	////////////////////////////////////////////////

	public MFFloat getKeyField() {
		if (isInstanceNode() == false)
			return keyField;
		return (MFFloat)getExposedField(keyFieldName);
	}
	
	public void addKey(float value) {
		getKeyField().addValue(value);
	}
	
	public int getNKeys() {
		return getKeyField().getSize();
	}
	
	public void setKey(int index, float value) {
		getKeyField().set1Value(index, value);
	}

	public void setKeys(String value) {
		getKeyField().setValues(value);
	}

	public void setKeys(String value[]) {
		getKeyField().setValues(value);
	}
	
	public float getKey(int index) {
		return getKeyField().get1Value(index);
	}
	
	public void removeKey(int index) {
		getKeyField().removeValue(index);
	}
	
	////////////////////////////////////////////////
	//	fraction
	////////////////////////////////////////////////

	public SFFloat getFractionField() {
		if (isInstanceNode() == false)
			return fractionField;
		return (SFFloat)getEventIn(fractionFieldName);
	}
	
	public void setFraction(float value) {
		getFractionField().setValue(value);
	}

	public void setFraction(String value) {
		getFractionField().setValue(value);
	}

	public float getFraction() {
		return getFractionField().getValue();
	}

}