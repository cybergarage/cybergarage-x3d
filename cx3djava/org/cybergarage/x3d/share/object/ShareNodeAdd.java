/******************************************************************
*
*	CyberVRML97 for Java3D
*
*	Copyright (C) Satoshi Konno 1997-2000 
*Å@
*	File:	ShareNodeAdd.java
*
******************************************************************/

package org.cybergarage.x3d.share.object;

import java.io.*;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.*;

public class ShareNodeAdd extends ShareNode
{
	///////////////////////////////////////////////
	// Constractor
	///////////////////////////////////////////////

	public ShareNodeAdd()
	{
	}

	public ShareNodeAdd(Node node)
	{
		super(node);
	}

	///////////////////////////////////////////////
	// Abstract Methods
	///////////////////////////////////////////////
	
	public boolean writeData(ObjectOutputStream out) throws IOException
	{
		Debug.message("ShareNodeAdd::writeData");
		
		Node		node						= getNode();
		boolean	isParentNodeRootNode	= isParentNodeRootNode();
		String	parentNodeName			= getParentNodeName();

		Debug.message("  node = " + node);
		Debug.message("  parentNodeName = " + parentNodeName);
		Debug.message("  isParentNodeRootNode = " + isParentNodeRootNode);
		
		out.writeObject(node);
		out.writeObject(parentNodeName);
		out.writeBoolean(isParentNodeRootNode);
		
		return true;
	}

	public boolean readData(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		Debug.message("ShareNodeAdd::readData");
		
		Node		node						= (Node)in.readObject();
		String 	parentNodeName			= (String)in.readObject();
		boolean	isParentNodeRootNode	= in.readBoolean();

		Debug.message("  node = " + node);
		Debug.message("  parentNodeName = " + parentNodeName);
		Debug.message("  isParentNodeRootNode = " + isParentNodeRootNode);
		
		setParentNodeRootNodeFlag(isParentNodeRootNode);
		setParentNodeName(parentNodeName);
		setNode(node);

		return true;
	}

	///////////////////////////////////////////////
	// Update
	///////////////////////////////////////////////
	
	public boolean update(SceneGraph sg)
	{
		Debug.message("ShareNodeAdd.update");
		
		if (sg == null)
			return false;

		boolean	isParentNodeRootNode		= isParentNodeRootNode();
		String	parentNodeName	= getParentNodeName();
		Node 		addNode 			= getNode();

		Debug.message("  addNode = " + addNode);
		Debug.message("  parentNodeName = " + parentNodeName);
		Debug.message("  isParentNodeRootNode = " + isParentNodeRootNode);

		if (addNode == null)
			return false;

		if (isParentNodeRootNode == false) {
			Node		parentNode		= sg.findNode(parentNodeName);
			if (parentNode == null)
				return false;
			parentNode.addChildNode(addNode, false);
		}
		else
			sg.addNode(addNode, false);

		sg.initialize();
		sg.print();
				
		return true;
	}
}
