/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : AppearanceNodeObject.java
*
*	Revisions:
*
*	11/11/02 
*		- David Hernandez <dahernan@telecable.es>
*		- Added the follwing capabilities.
*			ALLOW_TRANSPARENCY_ATTRIBUTES_READ
*			ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;

import org.cybergarage.x3d.node.*;

public class AppearanceNodeObject extends Appearance implements NodeObject {
	
	public AppearanceNodeObject(AppearanceNode node) {
		setCapability(ALLOW_POLYGON_ATTRIBUTES_READ);
		setCapability(ALLOW_POLYGON_ATTRIBUTES_WRITE);
		setCapability(ALLOW_MATERIAL_READ);
		setCapability(ALLOW_MATERIAL_WRITE);
		setCapability(ALLOW_TEXTURE_READ);
		setCapability(ALLOW_TEXTURE_WRITE);
		setCapability(ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
		setCapability(ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
		initialize(node);
	}

	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		PolygonAttributes polyAttr = new PolygonAttributes();
		polyAttr.setCapability(PolygonAttributes.ALLOW_MODE_READ);
		polyAttr.setCapability(PolygonAttributes.ALLOW_MODE_WRITE);
		setPolygonAttributes(polyAttr);
		return true;
	}
	
	public boolean uninitialize(org.cybergarage.x3d.node.Node node) {
		return true;
	}
			
	public boolean update(org.cybergarage.x3d.node.Node node) {
		return true;
	}
	
	public boolean add(org.cybergarage.x3d.node.Node node) {

		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setAppearance(this);
				}
			}
		}
		
		return true;
	}

	public boolean remove(org.cybergarage.x3d.node.Node node) {
	
		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setAppearance(new NullAppearanceObject());
				}
			}
		}
		
		return true;
	}
}
