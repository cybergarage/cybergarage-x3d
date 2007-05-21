/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : BaseNode.java
*
******************************************************************/

package org.cybergarage.x3d;

import org.cybergarage.x3d.util.*;

public abstract class BaseNode extends LinkedListNode  implements Constants {

	public BaseNode() 
	{
		setHeaderFlag(true);
		setType(null);
		setName(null);
	}
	
	////////////////////////////////////////////////
	//	Name
	////////////////////////////////////////////////

	protected String	mName;

	public String checkName(String name) 
	{
		if (name == null)
			return null;
		StringBuffer newName = new StringBuffer(name);
		for (int n=0; n<newName.length(); n++) {
			char c = newName.charAt(n);
			if (c == ' ')
				newName.setCharAt(n, '_');
		}
		return newName.toString();
	}
	
	public void setName(String name)
	{
		mName = checkName(name);
	}

	public String getName() 
	{
		return mName;
	}

	public boolean hasName() 
	{
		String name = getName();
		if (name == null)
			return false;
		if (name.length() == 0)
			return false;
		return true;
	}

	////////////////////////////////////////////////
	//	Type
	////////////////////////////////////////////////

	protected NodeType mType;
	
	public void setType(NodeType type) 
	{
		mType = type;
	}

	public NodeType getType() 
	{
		return mType;
	}

	public String getTypeString() 
	{
		return mType.toString();
	}


	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() 
	{
		String name = getName();
		if (name != null) {
			if (0 < name.length() )
				return getTypeString() + " - " + name;
			else
				return getTypeString();
		}
		return getTypeString();
	}
}
