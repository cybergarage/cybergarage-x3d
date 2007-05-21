/******************************************************************
*
*	CyberVRML97 for Java3D
*
*	Copyright (C) Satoshi Konno 1997-2000 
*Å@
*	File:	ShareField.java
*
******************************************************************/

package org.cybergarage.x3d.share.object;

import java.io.*;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.share.*;

public class ShareField extends ShareObject
{
	private transient Field 	mField;
	private transient String	mNodeName;
		
	///////////////////////////////////////////////
	// Constractor
	///////////////////////////////////////////////

	public ShareField()
	{
		setField(null);
		setNodeName(null);
	}
	
	public ShareField(Field field)
	{
		this();
		setField(field);
	}

	///////////////////////////////////////////////
	// Field
	///////////////////////////////////////////////

	public void setField(Field field)
	{
		mField = field;
		
		if (field != null) {
			Node node = field.getNode();
			if (node != null) {
				String nodeName = node.getName();
				setNodeName(nodeName);
			}			
		}
	}
	
	public Field getField()
	{
		return mField;
	}

	///////////////////////////////////////////////
	// NodeName
	///////////////////////////////////////////////

	public void setNodeName(String name)
	{
		mNodeName = name;
	}
	
	public String getNodeName()
	{
		return mNodeName;
	}
	
	///////////////////////////////////////////////
	// Abstract Methods
	///////////////////////////////////////////////
	
	public boolean writeData(ObjectOutputStream out) throws IOException
	{
		String	nodeName	= getNodeName();
		Field		field		= getField();
		
		out.writeObject(nodeName);
		out.writeObject(field);
		
		return true;
	}

	public boolean readData(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		String 	nodeName	= (String)in.readObject();
		Field		field		= (Field)in.readObject();
		
		setNodeName(nodeName);
		setField(field);

		return true;
	}

	public boolean update(SceneGraph sg)
	{
		if (sg == null)
			return false;
			
		Field		srcField			= getField();
		String	srcFieldName	= srcField.getName();
		String	dstNodeName 	= getNodeName();
		
		Node dstNode = sg.findNode(dstNodeName);
		if (dstNode == null)
			return false;

		Field dstField = dstNode.findField(srcFieldName); 	

		if (dstField == null)
			return false;
			
		dstField.setValue(srcField, false);
		
		return true;
	}
}
