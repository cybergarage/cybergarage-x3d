/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : MFChar.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;

public class MFChar extends MField {

	public MFChar() {
		setType(FieldType.MFCHAR);
	}

	public MFChar(MFChar values) {
		setType(FieldType.MFCHAR);
		copy(values);
	}

	public void addValue(char value) {
		SFChar sfvalue = new SFChar(value);
		add(sfvalue);
	}

	public void addValue(String value) {
		SFChar sfvalue = new SFChar(value);
		add(sfvalue);
	}
	
	public void addValue(SFChar sfvalue) {
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFChar sfvalue = new SFChar(value);
		insert(index, sfvalue);
	}
	
	public void insertValue(int index, char value) {
		SFChar sfvalue = new SFChar(value);
		insert(index, sfvalue);
	}

	public char get1Value(int index) {
		SFChar sfvalue = (SFChar)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return ' ';		
	}

	public void set1Value(int index, char value) {
		SFChar sfvalue = (SFChar)getField(index);
		if (sfvalue != null)
			sfvalue.setValue(value);
	}

	public void setValues(char value[]) {
		if (value == null)
			return;
		clear();
		int size = value.length;
		for (int n=0; n<size; n++)
			addValue(value[n]);
	}

	public char[] getValues() {
		int nValues = getSize();
		char value[] = new char[nValues];
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