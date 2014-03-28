/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFRotation.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class MFRotation extends MField {

	public MFRotation() {
		setType(FieldType.MFROTATION);
	}

	public MFRotation(MFRotation rotations) {
		setType(FieldType.MFROTATION);
		copy(rotations);
	}

	public void addValue(float x, float y, float z, float rot) {
		SFRotation rotation = new SFRotation(x, y, z, rot);
		add(rotation);
	}

	public void addValue(float value[]) {
		SFRotation rotation = new SFRotation(value);
		add(rotation);
	}

	public void addValue(String value) {
		SFRotation sfvalue = new SFRotation(value);
		add(sfvalue);
	}
	
	public void addValue(SFRotation rotation) {
		add(rotation);
	}

	public void insertValue(int index, float x, float y, float z, float rot) {
		SFRotation rotation = new SFRotation(x, y, z, rot);
		insert(index, rotation);
	}

	public void insertValue(int index, float value[]) {
		SFRotation rotation = new SFRotation(value);
		insert(index, rotation);
	}

	public void insertValue(int index, String value) {
		SFRotation rotation = new SFRotation(value);
		insert(index, rotation);
	}
	
	public void insertValue(int index, SFRotation rotation) {
		insert(index, rotation);
	}

	public void get1Value(int index, float value[]) {
		SFRotation rotation = (SFRotation)getField(index);
		if (rotation != null)
			rotation.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
			value[2] = 1.0f;
			value[3] = 0.0f;
		}
	}

	public void set1Value(int index, float value[]) {
		SFRotation rotation = (SFRotation)getField(index);
		if (rotation != null)
			rotation.setValue(value);
	}

	public void set1Value(int index, float x, float y, float z, float angle) {
		SFRotation rotation = (SFRotation)getField(index);
		if (rotation != null)
			rotation.setValue(x, y, z, angle);
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
		float value[][] = new float[nValues][4];
		for (int n=0; n<nValues; n++) 
			get1Value(n, value[n]);
		return value;
	}

	public int getValueCount()
	{
		return 4;
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float value[] = new float[4];
		for (int n=0; n<getSize(); n++) {
			get1Value(n, value);
			if (n < getSize()-1)
				printStream.println(indentString + value[X] + " " + value[Y] + " " + value[Z] + " " + value[3] + ",");
			else	
				printStream.println(indentString + value[X] + " " + value[Y] + " " + value[Z] + " " + value[3]);
		}
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return null;
	}
}