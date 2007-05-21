/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Sphere.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class SphereNode extends Geometry3DNode {
	
	//// Field ////////////////
	private final static String radiusFieldName = "radius";

	private SFFloat radiusField;
	
	public SphereNode() {
		setHeaderFlag(false);
		setType(NodeType.SPHERE);

		///////////////////////////
		// Exposed Field 
		///////////////////////////

		// radius exposed field
		radiusField = new SFFloat(1);
		addExposedField(radiusFieldName, radiusField);
	}

	public SphereNode(SphereNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Radius
	////////////////////////////////////////////////

	public SFFloat getRadiusField() {
		if (isInstanceNode() == false)
			return radiusField;
		return (SFFloat)getExposedField(radiusFieldName);
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
		super.initialize();
		updateBoundingBox();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public void updateBoundingBox() {
		setBoundingBoxCenter(0.0f, 0.0f, 0.0f);
		setBoundingBoxSize(getRadius(), getRadius(), getRadius());
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		printStream.println(indentString + "\t" + "radius " + getRadius() );
	}
}
