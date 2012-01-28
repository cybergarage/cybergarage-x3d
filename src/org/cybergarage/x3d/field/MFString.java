/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFString.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;

public class MFString extends MField {

	public MFString() {
		setType(FieldType.MFSTRING);
	}

	public MFString(MFString strings) {
		setType(FieldType.MFSTRING);
		copy(strings);
	}

	public void addValue(String value) {
		SFString sfvalue = new SFString(value);
		add(sfvalue);
	}

	public void addValue(SFString sfvalue) {
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFString sfvalue = new SFString(value);
		insert(index, sfvalue);
	}

	public String get1Value(int index) {
		SFString sfvalue = (SFString)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return null;
	}

	public void set1Value(int index, String value) {
		SFString sfvalue = (SFString)getField(index);
		if (sfvalue != null)
			sfvalue.setValue(value);
	}

	public void setValues(String value[]) {
		if (value == null)
			return;
		clear();
		int size = value.length;
		for (int n=0; n<size; n++)
			addValue(value[n]);
	}

	public String[] getValues() {
		int nValues = getSize();
		String value[] = new String[nValues];
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
				printStream.println(indentString + "\"" + get1Value(n) + "\"" + ",");
			else	
				printStream.println(indentString + "\"" + get1Value(n) + "\"");
		}
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return null;
	}
}