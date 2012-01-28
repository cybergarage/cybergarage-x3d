/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : NullMaterialObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;

public class NullMaterialObject extends Material {
	
	public NullMaterialObject() {
		setCapability(ALLOW_COMPONENT_READ);
		setCapability(ALLOW_COMPONENT_WRITE);
		setAmbientColor(0.8f * 0.2f, 0.8f * 0.2f, 0.8f * 0.2f);
		setDiffuseColor(0.8f, 0.8f, 0.8f);
		setEmissiveColor(0.0f, 0.0f, 0.0f);
		setSpecularColor(0.0f, 0.0f, 0.0f);
		setShininess(0.2f);
	}
	
}
