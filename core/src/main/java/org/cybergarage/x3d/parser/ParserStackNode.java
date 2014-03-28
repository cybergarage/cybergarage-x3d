/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File : ParserStackNode.java
*
******************************************************************/

package org.cybergarage.x3d.parser;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.*;

public class ParserStackNode extends LinkedListNode
{
	private Node	mNode;
	private int		mType;

	public ParserStackNode(Node node) 
	{ 
		setHeaderFlag(false); 
		mNode = node; 
		mType = 0;
	}

	public ParserStackNode(Node node, int type) 
	{ 
		setHeaderFlag(false); 
		mNode = node; 
		mType = type;
	}
	
	Node getObject() { 
		return mNode; 
	}

	int getType() { 
		return mType; 
	}
};
