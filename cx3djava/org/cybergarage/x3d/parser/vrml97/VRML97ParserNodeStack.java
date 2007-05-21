/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : VRML97ParserNodeStack.java
*
******************************************************************/

package org.cybergarage.x3d.parser.vrml97;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.*;

public class VRML97ParserNodeStack extends LinkedListNode {
	private Node	mNode;
	private int		mType;

	public VRML97ParserNodeStack(Node node, int type) { 
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
