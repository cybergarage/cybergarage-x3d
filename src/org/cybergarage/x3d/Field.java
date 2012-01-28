/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Field.java
*
******************************************************************/

package org.cybergarage.x3d;

import java.io.Serializable;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.share.object.*;

public abstract class Field implements Cloneable, Constants, Serializable
{
	private 				String		mName = null;
	private 				FieldType	mType = null;
	private transient	Node		mNode	= null;
	
	////////////////////////////////////////
	// Constructor
	////////////////////////////////////////
	
	public Field() 
	{
		setName(null);
		setType(FieldType.NONE);
		setNode(null);
	}

	public Field(Field field) 
	{
		setObject(field.getObject());
	}
	
	////////////////////////////////////////
	// Node
	////////////////////////////////////////

	public void setNode(Node node)
	{
		mNode = node;
	}
	
	public Node getNode()
	{
		return mNode;
	}

	public SceneGraph getSceneGraph()
	{
		Node node = getNode();
		if (node == null)
			return null;
		return node.getSceneGraph();
	}

	public void postShareField(Field field)
	{
		SceneGraph sg = getSceneGraph();
		if (sg == null)
			return;
		ShareField shareField = new ShareField(field);
		sg.postShareObject(shareField);
	}
	
	////////////////////////////////////////
	// setType
	////////////////////////////////////////
	
	public void setType(FieldType type) 
	{
		mType = type;
	}

	public FieldType getType() {
		return mType;
	}

	public String getTypeName() {
		return getType().toString();
	}

	public boolean isSameType(Field field) {
		return (getType() == field.getType() ? true : false);
	}

	public boolean isSameValueType(Field field) {
		return getType().equals(field.getType());
	}
	
	public boolean isSField() {
		return !isMultiField();
	}

	public boolean isMField() {
		if (this instanceof MField)
			return true;
		return false;
	}

	public boolean isSingleField() {
		return isSField();
	}

	public boolean isMultiField() {
		return isMField();
	}

	public boolean isSingleValueMField()
	{
		if (this instanceof MFFloat)
			return true;
		if (this instanceof MFInt32)
			return true;
		if (this instanceof MFNode)
			return true;
		if (this instanceof MFString)
			return true;
		if (this instanceof MFTime)
			return true;
		return false;
	}
	
	public void setName(String name) {
		mName = name;	
	}

	public String getName() {
		return mName;
	}

	abstract public void setValue(String value);
	abstract public void setValue(Field field);
	abstract public String toString();
	abstract public int getValueCount();

	public void setValue(Field field, boolean doShare)
	{
	}
	
	public String toXMLString()
	{
		return toString();
	}

	abstract public void setObject(Object obj);
	abstract public Object getObject();
}