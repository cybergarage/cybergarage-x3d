/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : MFVec3d.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class MFVec3d extends MField {

	public MFVec3d() {
		setType(FieldType.SFVEC3D);
	}

	public MFVec3d(MFVec3d vectors) {
		setType(FieldType.SFVEC3D);
		copy(vectors);
	}

	public void addValue(double x, double y, double z) {
		SFVec3d vector = new SFVec3d(x, y, z);
		add(vector);
	}

	public void addValue(double value[]) {
		SFVec3d vector = new SFVec3d(value);
		add(vector);
	}

	public void addValue(String value) {
		SFVec3d sfvalue = new SFVec3d(value);
		add(sfvalue);
	}
	
	public void addValue(SFVec3d vector) {
		add(vector);
	}

	public void insertValue(int index, double x, double y, double z) {
		SFVec3d vector = new SFVec3d(x, y, z);
		insert(index, vector);
	}

	public void insertValue(int index, double value[]) {
		SFVec3d vector = new SFVec3d(value);
		insert(index, vector);
	}

	public void insertValue(int index, String value) {
		SFVec3d vector = new SFVec3d(value);
		insert(index, vector);
	}
	
	public void insertValue(int index, SFVec3d vector) {
		insert(index, vector);
	}
	
	public void get1Value(int index, double value[]) {
		SFVec3d vector = (SFVec3d)getField(index);
		if (vector != null)
			vector.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
			value[2] = 0.0f;
		}
	}

	public void set1Value(int index, double value[]) {
		SFVec3d vector = (SFVec3d)getField(index);
		if (vector != null)
			vector.setValue(value);
	}

	public void set1Value(int index, double x, double y, double z) {
		SFVec3d vector = (SFVec3d)getField(index);
		if (vector != null)
			vector.setValue(x, y, z);
	}

	public void setValues(double value[][]) {
		if (value == null)
			return;
		clear();
		int size = value.length;
		for (int n=0; n<size; n++)
			addValue(value[n]);
	}

	public double[][] getValues() {
		int nValues = getSize();
		double value[][] = new double[nValues][3];
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
		double value[] = new double[3];
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