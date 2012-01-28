/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFNode.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;

public class MFNode extends MField {

	public MFNode() {
		setType(FieldType.MFNODE);
	}

	public MFNode(MFNode nodes) {
		setType(FieldType.MFNODE);
		copy(nodes);
	}

	public void addValue(String value) {
		SFNode sfvalue = new SFNode();
		add(sfvalue);
	}

	public void addValue(Node node) {
		SFNode sfvalue = new SFNode(node);
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFNode sfvalue = new SFNode();
		insert(index, sfvalue);
	}
	
	public void insertValue(int index, Node node) {
		SFNode sfvalue = new SFNode(node);
		insert(index, sfvalue);
	}

	public Node get1Value(int index) {
		SFNode sfvalue = (SFNode)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return null;
	}

	public void set1Value(int index, Node node) {
		SFNode sfvalue = (SFNode)getField(index);
		if (sfvalue != null)
			sfvalue.setValue(node);
	}

	public void setValues(Node value[]) {
		if (value == null)
			return;
		clear();
		int size = value.length;
		for (int n=0; n<size; n++)
			addValue(value[n]);
	}

	public Node[] getValues() {
		int nValues = getSize();
		Node value[] = new Node[nValues];
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
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return null;
	}
}