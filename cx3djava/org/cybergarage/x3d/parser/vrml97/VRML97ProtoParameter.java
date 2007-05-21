/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : VRML97ProtoParameter.java
*
******************************************************************/

package org.cybergarage.x3d.parser.vrml97;

import org.cybergarage.x3d.util.*;

public class VRML97ProtoParameter extends LinkedListNode {

	public VRML97ProtoParameter(String type, String name, String value) {
		setType(type);
		setName(name);
		setValue(value);
	}
	
	////////////////////////////////////////////////
	//	Type
	////////////////////////////////////////////////

	protected String	mType;

	public void setType(String type) {
		mType = type;
	}

	public String getType() {
		return mType;
	}
	
	////////////////////////////////////////////////
	//	Name
	////////////////////////////////////////////////

	protected String	mName;

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	////////////////////////////////////////////////
	//	Defalut Value
	////////////////////////////////////////////////

	protected String	mDefalutValue;

	public void setValue(String value) {
		mDefalutValue = value;
	}

	public String getValue() {
		return mDefalutValue;
	}

	////////////////////////////////////////////////
	//	Next node 
	////////////////////////////////////////////////

	public VRML97ProtoParameter next() {
		return (VRML97ProtoParameter)getNextNode();
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return "field " + getType() + " " + getName() + " " + getValue();
	}
}
