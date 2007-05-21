/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : RootNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;

import org.cybergarage.x3d.node.*;

public class RootNodeObject extends BranchGroup implements NodeObject {

	public RootNodeObject(RootNode node) {
		setCapability(ALLOW_BOUNDS_READ);
		setCapability(ALLOW_CHILDREN_READ);
		setCapability(ALLOW_CHILDREN_WRITE);
		setCapability(ALLOW_CHILDREN_EXTEND);
		setCapability(ALLOW_DETACH);
	}
	
	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		return true;
	}
	
	public boolean uninitialize(org.cybergarage.x3d.node.Node node) {
		return true;
	}
			
	public boolean update(org.cybergarage.x3d.node.Node node) {
		return true;
	}
	
	public boolean add(org.cybergarage.x3d.node.Node node) {
		return true;
	}

	public boolean remove(org.cybergarage.x3d.node.Node node) {
		return true;
	}
}
