/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : MFVec2d.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class MFVec2d extends MField {

	public MFVec2d() {
		setType(FieldType.MFVEC2D);
	}

	public MFVec2d(MFVec2d vectors) {
		setType(FieldType.MFVEC2D);
		copy(vectors);
	}

	public void addValue(double x, double y) {
		SFVec2d vector = new SFVec2d(x, y);
		add(vector);
	}

	public void addValue(double value[]) {
		SFVec2d vector = new SFVec2d(value);
		add(vector);
	}

	public void addValue(String value) {
		SFVec2d sfvalue = new SFVec2d(value);
		add(sfvalue);
	}
	
	public void addValue(SFVec2d vector) {
		add(vector);
	}

	public void insertValue(int index, double x, double y) {
		SFVec2d vector = new SFVec2d(x, y);
		insert(index, vector);
	}

	public void insertValue(int index, double value[]) {
		SFVec2d vector = new SFVec2d(value);
		insert(index, vector);
	}

	public void insertValue(int index, String value) {
		SFVec2d vector = new SFVec2d(value);
		insert(index, vector);
	}
	
	public void insertValue(int index, SFVec2d vector) {
		insert(index, vector);
	}

	public void get1Value(int index, double value[]) {
		SFVec2d vector = (SFVec2d)getField(index);
		if (vector != null)
			vector.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
		}
	}

	public void set1Value(int index, double value[]) {
		SFVec2d vector = (SFVec2d)getField(index);
		if (vector != null)
			vector.setValue(value);
	}

	public void set1Value(int index, double x, double y) {
		SFVec2d vector = (SFVec2d)getField(index);
		if (vector != null)
			vector.setValue(x, y);
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
		double value[][] = new double[nValues][2];
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
		double value[] = new double[2];
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