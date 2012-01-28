/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : MFBool.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;

public class MFBool extends MField {

	public MFBool() {
		setType(FieldType.MFBOOL);
	}

	public MFBool(MFBool values) {
		setType(FieldType.MFBOOL);
		copy(values);
	}

	public void addValue(boolean value) {
		SFBool sfvalue = new SFBool(value);
		add(sfvalue);
	}

	public void addValue(String value) {
		SFBool sfvalue = new SFBool(value);
		add(sfvalue);
	}
	
	public void addValue(SFBool sfvalue) {
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFBool sfvalue = new SFBool(value);
		insert(index, sfvalue);
	}
	
	public void insertValue(int index, boolean value) {
		SFBool sfvalue = new SFBool(value);
		insert(index, sfvalue);
	}

	public boolean get1Value(int index) {
		SFBool sfvalue = (SFBool)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return false;		
	}

	public void set1Value(int index, boolean value) {
		SFBool sfvalue = (SFBool)getField(index);
		if (sfvalue != null)
			sfvalue.setValue(value);
	}

	public void setValues(boolean value[]) {
		if (value == null)
			return;
		clear();
		int size = value.length;
		for (int n=0; n<size; n++)
			addValue(value[n]);
	}

	public boolean[] getValues() {
		int nValues = getSize();
		boolean value[] = new boolean[nValues];
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
			String strValue = (get1Value(n) == true) ? "TRUE" : "FALSE"; 
			if (n < getSize()-1)
				printStream.println(indentString + strValue + ",");
			else	
				printStream.println(indentString + strValue);
		}
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return null;
	}
}