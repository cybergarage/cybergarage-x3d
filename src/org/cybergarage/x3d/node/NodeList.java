/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : NodeList.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.util.*;

public class NodeList extends LinkedList {

	public NodeList() {
		RootNode rootNode = new RootNode();
		setRootNode(rootNode);
	}

	public void removeNodes() {
		LinkedListNode rootNode = getRootNode();
		while (rootNode.getNextNode() != null) {
			Node nextNode = (Node)rootNode.getNextNode();
			nextNode.remove();
		}
	}
}