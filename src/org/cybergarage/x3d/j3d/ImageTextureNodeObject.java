/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ImageTextureNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;

import org.cybergarage.x3d.node.*;

public class ImageTextureNodeObject extends Texture2D implements NodeObject {
	
	public ImageTextureNodeObject(ImageTextureLoader imgTexLoader) {
		super(BASE_LEVEL, RGBA, imgTexLoader.getWidth(), imgTexLoader.getHeight());

		setCapability(ALLOW_IMAGE_READ);
		setCapability(ALLOW_ENABLE_READ);
		setCapability(ALLOW_ENABLE_WRITE);
		
		setMinFilter(BASE_LEVEL_LINEAR);
		setMagFilter(BASE_LEVEL_LINEAR);
		
		setImage(0, imgTexLoader.getImageComponent());
		setEnable(true);
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

		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isAppearanceNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Appearance parentAppearanceNode = (Appearance)parentNodeObject;
					parentAppearanceNode.setTexture(this);
				}
			}
		}
		
		return true;
	}

	public boolean remove(org.cybergarage.x3d.node.Node node) {
	
		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isAppearanceNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Appearance parentAppearanceNode = (Appearance)parentNodeObject;
					parentAppearanceNode.setTexture(null);
				}
			}
		}
		
		return true;
	}
}
