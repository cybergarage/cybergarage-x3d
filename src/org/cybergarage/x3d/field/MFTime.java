/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFTime.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class MFTime extends MField {
	
	public MFTime() {
		setType(FieldType.MFTIME);
	}

	public MFTime(MFTime times) {
		setType(FieldType.MFTIME);
		copy(times);
	}

	public void addValue(double value) {
		SFTime sfvalue = new SFTime(value);
		add(sfvalue);
	}

	public void addValue(String value) {
		SFTime sfvalue = new SFTime(value);
		add(sfvalue);
	}

	public void addValue(SFTime sfvalue) {
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFTime sfvalue = new SFTime(value);
		insert(index, sfvalue);
	}
	
	public void insertValue(int index, double value) {
		SFTime sfvalue = new SFTime(value);
		insert(index, sfvalue);
	}

	public double get1Value(int index) {
		SFTime sfvalue = (SFTime)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return 0.0;
	}

	public void set1Value(int index, double value) {
		SFTime sfvalue = (SFTime)getField(index);
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