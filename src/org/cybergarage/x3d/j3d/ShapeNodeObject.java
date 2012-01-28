/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ShapeNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;

import org.cybergarage.x3d.node.*;

public class ShapeNodeObject extends Shape3D implements NodeObject {

	public ShapeNodeObject(ShapeNode node) {
		setCapability(ALLOW_APPEARANCE_READ);
		setCapability(ALLOW_APPEARANCE_WRITE);
		setCapability(ALLOW_GEOMETRY_READ);
		setCapability(ALLOW_GEOMETRY_WRITE);
		setCapability(ALLOW_COLLISION_BOUNDS_READ);
		setCapability(ALLOW_COLLISION_BOUNDS_WRITE);

		setCapability(ALLOW_BOUNDS_READ);
		setCapability(ALLOW_PICKABLE_READ);
		
		setBoundsAutoCompute(true);
		setCollidable(true); 
		setPickable(true);
		
		initialize(node);
	}
	
	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		setAppearance(new NullAppearanceObject());
		return true;
	}
	
	public boolean uninitialize(org.cybergarage.x3d.node.Node node) {
		return true;
	}
			
	public boolean update(org.cybergarage.x3d.node.Node node) {
		return true;
	}
	
	public Group getParentGroup(org.cybergarage.x3d.node.Node node) {
		org.cybergarage.x3d.node.Node	parentNode		= node.getParentNode();
		Group			parentGroupNode	= null;
		if (parentNode != null) {
			NodeObject parentNodeObject  = parentNode.getObject();
			if (parentNodeObject != null)
				parentGroupNode = (Group)parentNodeObject;
		}
		else {
			org.cybergarage.x3d.SceneGraph sg = node.getSceneGraph();
			if (sg != null) {
				SceneGraphJ3dObject	sgObject = (SceneGraphJ3dObject)sg.getObject();
				if (sgObject != null)
					parentGroupNode = sgObject.getRootNode();
			}
		}
		return parentGroupNode;
	}
	
	public boolean add(org.cybergarage.x3d.node.Node node) {
		if (getParent() == null) {
			Group parentGroupNode = getParentGroup(node);
			if (parentGroupNode != null) {
					parentGroupNode.addChild(this);
			}
		}
		return true;
	}

	public boolean remove(org.cybergarage.x3d.node.Node node) {
		Group parentGroupNode = getParentGroup(node);
		if (parentGroupNode != null) {
			for (int n=0; n<parentGroupNode.numChildren(); n++) {
				if (parentGroupNode.getChild(n) == this) {
					parentGroupNode.removeChild(n);
					return true;
				}
			}
		}
		return false;
	}
}
