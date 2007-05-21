/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : NullAppearanceObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;

public class NullAppearanceObject extends Appearance {

	public NullAppearanceObject() {
		setCapability(ALLOW_POLYGON_ATTRIBUTES_READ);
		setCapability(ALLOW_POLYGON_ATTRIBUTES_WRITE);
		setCapability(ALLOW_MATERIAL_READ);
		setCapability(ALLOW_MATERIAL_WRITE);
	
		PolygonAttributes polyAttr = new PolygonAttributes();
		polyAttr.setCapability(PolygonAttributes.ALLOW_MODE_READ);
		polyAttr.setCapability(PolygonAttributes.ALLOW_MODE_WRITE);
		setPolygonAttributes(polyAttr);

		setMaterial(new NullMaterialObject());
	}
}
