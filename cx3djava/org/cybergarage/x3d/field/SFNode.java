/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFNode.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;

public class SFNode extends Field {

	private Node mValue; 

	public SFNode() {
		setType(FieldType.SFNODE);
		mValue = null;
	}

	public SFNode(SFNode node) {
		setType(FieldType.SFNODE);
		setValue(node);
	}

	public SFNode(Node node) {
		setType(FieldType.SFNODE);
		setValue(node);
	}

	public void setValue(Node node) {
		if (mValue != null) {
			synchronized (mValue) {
				mValue = node;
			}
		}
		else
			mValue = node;
	}

	public void setValue(SFNode node) {
		setValue(node.getValue());
	}

	public void setValue(String string) {
	}

	public void setValue(Field field) {
		if (field instanceof SFNode)
			setValue((SFNode)field);
	}
	
	public Node getValue() {
		Node value;
		if (mValue != null) {
			synchronized (mValue) {
				value = mValue;
			}
		}
		else
			value = mValue;
		
		return value;
	}

	public int getValueCount()
	{
		return 1;
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (Node)object;
		}
	}

	public Object getObject() {
		Object object;
		synchronized (mValue) {
			object = mValue;
		}
		return object;
	}
	
	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		Node node = getValue();
		if (node == null)
			return null;
		return node.toString();
	}
}
