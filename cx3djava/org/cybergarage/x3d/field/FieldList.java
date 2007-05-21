/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-2000
*
*	File : FieldList.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.util.*;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;

public class FieldList extends Vector {

	private Node	mNode;
	
	public FieldList(Node node) 
	{
		mNode = node;
	}
	
	public Node getNode()
	{
		return mNode;
	}
	
	public void addField(Field field)
	{
		addElement(field);
		field.setNode(getNode());
	}

	public void removeField(Field field)
	{
		removeElement(field);
		field.setNode(null);
	}
}