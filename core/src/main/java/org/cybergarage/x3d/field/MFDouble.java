/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : MFDouble.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;

public class MFDouble extends MField {

	public MFDouble() {
		setType(FieldType.MFDOUBLE);
	}

	public MFDouble(MFDouble values) {
		setType(FieldType.MFDOUBLE);
		copy(values);
	}

	public void addValue(double value) {
		SFDouble sfvalue = new SFDouble(value);
		add(sfvalue);
	}

	public void addValue(String value) {
		SFDouble sfvalue = new SFDouble(value);
		add(sfvalue);
	}
	
	public void addValue(SFDouble sfvalue) {
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFDouble sfvalue = new SFDouble(value);
		insert(index, sfvalue);
	}
	
	public void insertValue(int index, double value) {
		SFDouble sfvalue = new SFDouble(value);
		insert(index, sfvalue);
	}

	public double get1Value(int index) {
		SFDouble sfvalue = (SFDouble)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return 0.0;		
	}

	public void set1Value(int index, double value) {
		SFDouble sfvalue = (SFDouble)getField(index);
		if (sfvalue != null)
			sfvalue.setValue(value);
	}

	public void setValues(double value[]) {
		if (value == null)
			return;
		clear();
		int size = value.length;
		for (int n=0; n<size; n++)
			addValue(value[n]);
	}

	public double[] getValues() {
		int nValues = getSize();
		double value[] = new double[nValues];
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