/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFColorRGBA.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class MFColorRGBA extends MField {

	public MFColorRGBA() {
		setType(FieldType.MFCOLORRGBA);
	}

	public MFColorRGBA(MFColorRGBA colors) {
		setType(FieldType.MFCOLORRGBA);
		copy(colors);
	}

	public void addValue(float r, float g, float b, float a) {
		SFColorRGBA color = new SFColorRGBA(r, g, b, a);
		add(color);
	}

	public void addValue(float value[]) {
		SFColorRGBA color = new SFColorRGBA(value);
		add(color);
	}

	public void addValue(String value) {
		SFColorRGBA sfvalue = new SFColorRGBA(value);
		add(sfvalue);
	}

	public void addValue(SFColorRGBA color) {
		add(color);
	}

	public void insertValue(int index, float r, float g, float b, float a) {
		SFColorRGBA color = new SFColorRGBA(r, g, b, a);
		insert(index, color);
	}

	public void insertValue(int index, float value[]) {
		SFColorRGBA color = new SFColorRGBA(value);
		insert(index, color);
	}

	public void insertValue(int index, String value) {
		SFColorRGBA color = new SFColorRGBA(value);
		insert(index, color);
	}
	
	public void insertValue(int index, SFColorRGBA color) {
		insert(index, color);
	}

	public void get1Value(int index, float value[]) {
		SFColorRGBA color = (SFColorRGBA)getField(index);
		if (color != null)
			color.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
			value[2] = 0.0f;
			value[3] = 0.0f;
		}
	}

	public void set1Value(int index, float value[]) {
		SFColorRGBA color = (SFColorRGBA)getField(index);
		if (color != null)
			color.setValue(value);
	}

	public void set1Value(int index, float r, float g, float b, float a) {
		SFColorRGBA color = (SFColorRGBA)getField(index);
		if (color != null)
			color.setValue(r, g, b, a);
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
		float value[] = new float[3];
		for (int n=0; n<getSize(); n++) {
			get1Value(n, value);
			if (n < getSize()-1)
				printStream.println(indentString + value[0] + " " + value[1] + " " + value[2] + "," + value[3] + ",");
			else	
				printStream.println(indentString + value[0] + " " + value[1] + " " + value[2] + "," + value[3]);
		}
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return null;
	}
}