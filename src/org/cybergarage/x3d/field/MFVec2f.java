/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFVec2f.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class MFVec2f extends MField {

	public MFVec2f() {
		setType(FieldType.MFVEC2F);
	}

	public MFVec2f(MFVec2f vectors) {
		setType(FieldType.MFVEC2F);
		copy(vectors);
	}

	public void addValue(float x, float y) {
		SFVec2f vector = new SFVec2f(x, y);
		add(vector);
	}

	public void addValue(float value[]) {
		SFVec2f vector = new SFVec2f(value);
		add(vector);
	}

	public void addValue(String value) {
		SFVec2f sfvalue = new SFVec2f(value);
		add(sfvalue);
	}
	
	public void addValue(SFVec2f vector) {
		add(vector);
	}

	public void insertValue(int index, float x, float y) {
		SFVec2f vector = new SFVec2f(x, y);
		insert(index, vector);
	}

	public void insertValue(int index, float value[]) {
		SFVec2f vector = new SFVec2f(value);
		insert(index, vector);
	}

	public void insertValue(int index, String value) {
		SFVec2f vector = new SFVec2f(value);
		insert(index, vector);
	}
	
	public void insertValue(int index, SFVec2f vector) {
		insert(index, vector);
	}

	public void get1Value(int index, float value[]) {
		SFVec2f vector = (SFVec2f)getField(index);
		if (vector != null)
			vector.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
		}
	}

	public void set1Value(int index, float value[]) {
		SFVec2f vector = (SFVec2f)getField(index);
		if (vector != null)
			vector.setValue(value);
	}

	public void set1Value(int index, float x, float y) {
		SFVec2f vector = (SFVec2f)getField(index);
		if (vector != null)
			vector.setValue(x, y);
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
		float value[][] = new float[nValues][2];
		for (int n=0; n<nValues; n++) 
			get1Value(n, value[n]);
		return value;
	}

	public int getValueCount()
	{
		return 2;
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float value[] = new float[2];
		for (int n=0; n<getSize(); n++) {
			get1Value(n, value);
			if (n < getSize()-1)
				printStream.println(indentString + value[X] + " " + value[Y] + ",");
			else	
				printStream.println(indentString + value[X] + " " + value[Y]);
		}
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return null;
	}
}