/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFColor.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class MFColor extends MField {

	public MFColor() {
		setType(FieldType.MFCOLOR);
	}

	public MFColor(MFColor colors) {
		setType(FieldType.MFCOLOR);
		copy(colors);
	}

	public void addValue(float r, float g, float b) {
		SFColor color = new SFColor(r, g, b);
		add(color);
	}

	public void addValue(float value[]) {
		SFColor color = new SFColor(value);
		add(color);
	}

	public void addValue(String value) {
		SFColor sfvalue = new SFColor(value);
		add(sfvalue);
	}

	public void addValue(SFColor color) {
		add(color);
	}

	public void insertValue(int index, float r, float g, float b) {
		SFColor color = new SFColor(r, g, b);
		insert(index, color);
	}

	public void insertValue(int index, float value[]) {
		SFColor color = new SFColor(value);
		insert(index, color);
	}

	public void insertValue(int index, String value) {
		SFColor color = new SFColor(value);
		insert(index, color);
	}
	
	public void insertValue(int index, SFColor color) {
		insert(index, color);
	}

	public void get1Value(int index, float value[]) {
		SFColor color = (SFColor)getField(index);
		if (color != null)
			color.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
			value[2] = 0.0f;
		}
	}

	public void set1Value(int index, float value[]) {
		SFColor color = (SFColor)getField(index);
		if (color != null)
			color.setValue(value);
	}

	public void set1Value(int index, float r, float g, float b) {
		SFColor color = (SFColor)getField(index);
		if (color != null)
			color.setValue(r, g, b);
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