/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFFloat.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;

public class MFFloat extends MField {

	public MFFloat() {
		setType(FieldType.MFFLOAT);
	}

	public MFFloat(MFFloat values) {
		setType(FieldType.MFFLOAT);
		copy(values);
	}

	public void addValue(float value) {
		SFFloat sfvalue = new SFFloat(value);
		add(sfvalue);
	}

	public void addValue(String value) {
		SFFloat sfvalue = new SFFloat(value);
		add(sfvalue);
	}
	
	public void addValue(SFFloat sfvalue) {
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFFloat sfvalue = new SFFloat(value);
		insert(index, sfvalue);
	}
	
	public void insertValue(int index, float value) {
		SFFloat sfvalue = new SFFloat(value);
		insert(index, sfvalue);
	}

	public float get1Value(int index) {
		SFFloat sfvalue = (SFFloat)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return 0.0f;		
	}

	public void set1Value(int index, float value) {
		SFFloat sfvalue = (SFFloat)getField(index);
		if (sfvalue != null)
			sfvalue.setValue(value);
	}

	public void setValues(float value[]) {
		if (value == null)
			return;
		clear();
		int size = value.length;
		for (int n=0; n<size; n++)
			addValue(value[n]);
	}

	public float[] getValues() {
		int nValues = getSize();
		float value[] = new float[nValues];
		for (int n=0; n<nValues; n++) 
			value[n] = get1Value(n);
		return value;
	}

	public int getValueCount()
	{
		return 1;
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		for (int n=0; n<getSize(); n++) {
			if (n < getSize()-1)
				printStream.println(indentString + get1Value(n) + ",");
			else	
				printStream.println(indentString + get1Value(n));
		}
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return null;
	}
}