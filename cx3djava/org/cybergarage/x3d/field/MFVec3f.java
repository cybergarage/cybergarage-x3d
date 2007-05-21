/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFVec3f.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class MFVec3f extends MField {

	public MFVec3f() {
		setType(FieldType.MFVEC3F);
	}

	public MFVec3f(MFVec3f vectors) {
		setType(FieldType.MFVEC3F);
		copy(vectors);
	}

	public void addValue(float x, float y, float z) {
		SFVec3f vector = new SFVec3f(x, y, z);
		add(vector);
	}

	public void addValue(float value[]) {
		SFVec3f vector = new SFVec3f(value);
		add(vector);
	}

	public void addValue(String value) {
		SFVec3f sfvalue = new SFVec3f(value);
		add(sfvalue);
	}
	
	public void addValue(SFVec3f vector) {
		add(vector);
	}

	public void insertValue(int index, float x, float y, float z) {
		SFVec3f vector = new SFVec3f(x, y, z);
		insert(index, vector);
	}

	public void insertValue(int index, float value[]) {
		SFVec3f vector = new SFVec3f(value);
		insert(index, vector);
	}

	public void insertValue(int index, String value) {
		SFVec3f vector = new SFVec3f(value);
		insert(index, vector);
	}
	
	public void insertValue(int index, SFVec3f vector) {
		insert(index, vector);
	}
	
	public void get1Value(int index, float value[]) {
		SFVec3f vector = (SFVec3f)getField(index);
		if (vector != null)
			vector.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
			value[2] = 0.0f;
		}
	}

	public void set1Value(int index, float value[]) {
		SFVec3f vector = (SFVec3f)getField(index);
		if (vector != null)
			vector.setValue(value);
	}

	public void set1Value(int index, float x, float y, float z) {
		SFVec3f vector = (SFVec3f)getField(index);
		if (vector != null)
			vector.setValue(x, y, z);
	}

	public void setValues(float value[][]) {
		if (value == null)
			return;
		clear();
		int size = value.length;
		for (int n=0; n<size; n++)
			addValue(value[n]);
	}

	public float[][] getValues() {
		int nValues = getSize();
		float value[][] = new float[nValues][3];
		for (int n=0; n<nValues; n++) 
			get1Value(n, value[n]);
		return value;
	}

	public int getValueCount()
	{
		return 3;
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float value[] = new float[3];
		for (int n=0; n<getSize(); n++) {
			get1Value(n, value);
			if (n < getSize()-1)
				printStream.println(indentString + value[X] + " " + value[Y] + " " + value[Z] + ",");
			else	
				printStream.println(indentString + value[X] + " " + value[Y] + " " + value[Z]);
		}
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return null;
	}

}