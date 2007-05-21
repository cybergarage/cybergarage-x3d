/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: GeometryNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.field.*;

public abstract class GeometryNode extends Node {

	private String	displayListNumberPrivateFieldString	= "displayListNumber";

	private SFInt32 dispListField;

	public GeometryNode() {
		setHeaderFlag(false);

		// display list field
		dispListField = new SFInt32(0);
		dispListField.setName(displayListNumberPrivateFieldString);
		addPrivateField(dispListField);

		setDisplayListNumber(0);
	}

	////////////////////////////////////////////////
	//	DisplayListNumber
	////////////////////////////////////////////////

	public SFInt32 getDisplayListField() {
		if (isInstanceNode() == false)
			return dispListField;
		return (SFInt32)getPrivateField(displayListNumberPrivateFieldString);
	}

	public void setDisplayListNumber(int n) {
		getDisplayListField().setValue((int)n);
	}

	public int getDisplayListNumber() {
		return getDisplayListField().getValue();
	} 
}

