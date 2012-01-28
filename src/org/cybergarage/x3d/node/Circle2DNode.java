/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Circle2D.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class Circle2DNode extends Geometry2DNode {
	
	private String radiusFieldName = "radius";

	private SFFloat radiusField;
	
	public Circle2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.CIRCLE2D);

		// radius field
		radiusField = new SFFloat(1);
		addField(radiusFieldName, radiusField);
	}

	public Circle2DNode(Circle2DNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Radius
	////////////////////////////////////////////////

	public SFFloat getRadiusField() {
		if (isInstanceNode() == false)
			return radiusField;
		return (SFFloat)getField(radiusFieldName);
	}
	
	public void setRadius(float value) {
		getRadiusField().setValue(value);
	}

	public void setRadius(String value) {
		getRadiusField().setValue(value);
	}
	
	public float getRadius() {
		return getRadiusField().getValue();
	} 

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
	}
}
