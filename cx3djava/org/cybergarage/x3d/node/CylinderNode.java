/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: Cylinder.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class CylinderNode extends Geometry3DNode {

	private final static String radiusFieldName	= "radius";
	private final static String heightFieldName	= "height";
	private final static String topFieldName		= "top";
	private final static String sideFieldName		= "side";
	private final static String bottomFieldName	= "bottom";

	private SFFloat	radiusField;
	private SFFloat	heightField;
	private SFBool	topField;
	private SFBool	sideField;
	private SFBool	bottomField;
	
	public CylinderNode() {

		setHeaderFlag(false);
		setType(NodeType.CYLINDER);

		// radius field
		radiusField = new SFFloat(1.0f);
		addExposedField(radiusFieldName, radiusField);

		// height field
		heightField = new SFFloat(2.0f);
		addExposedField(heightFieldName, heightField);

		// top field
		topField = new SFBool(true);
		addExposedField(topFieldName, topField);

		// side field
		sideField = new SFBool(true);
		addExposedField(sideFieldName, sideField);

		// bottom field
		bottomField = new SFBool(true);
		addExposedField(bottomFieldName, bottomField);
	}

	public CylinderNode(CylinderNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	radius
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
	//	height
	////////////////////////////////////////////////

	public SFFloat getHeightField() {
		if (isInstanceNode() == false)
			return heightField;
		return (SFFloat)getExposedField(heightFieldName);
	}

	public void setHeight(float value) {
		getHeightField().setValue(value);
	}

	public void setHeight(String value) {
		getHeightField().setValue(value);
	}
	
	public float getHeight() {
		return getHeightField().getValue();
	}

	////////////////////////////////////////////////
	//	top
	////////////////////////////////////////////////

	public SFBool getTopField() {
		if (isInstanceNode() == false)
			return topField;
		return (SFBool)getExposedField(topFieldName);
	}

	public void setTop(boolean value) {
		getTopField().setValue(value);
	}

	public void setTop(String value) {
		getTopField().setValue(value);
	}
	
	public boolean getTop() {
		return getTopField().getValue();
	}

	////////////////////////////////////////////////
	//	side
	////////////////////////////////////////////////

	public SFBool getSideField() {
		if (isInstanceNode() == false)
			return sideField;
		return (SFBool)getExposedField(sideFieldName);
	}

	public void setSide(boolean value) {
		getSideField().setValue(value);
	}

	public void setSide(String value) {
		getSideField().setValue(value);
	}

	public boolean getSide() {
		return getSideField().getValue();
	}

	////////////////////////////////////////////////
	//	bottom
	////////////////////////////////////////////////

	public SFBool getBottomField() {
		if (isInstanceNode() == false)
			return bottomField;
		return (SFBool)getExposedField(bottomFieldName);
	}

	public void setBottom(boolean value) {
		getBottomField().setValue(value);
	}

	public void setBottom(String value) {
		getBottomField().setValue(value);
	}
	
	public boolean getBottom() {
		return getBottomField().getValue();
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
		setBoundingBoxSize(getRadius(), getHeight()/2.0f, getRadius());
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool top = getTopField();
		SFBool side = getSideField();
		SFBool bottom = getBottomField();

		printStream.println(indentString + "\t" + "radius " + getRadius());
		printStream.println(indentString + "\t" + "height " + getHeight());
		printStream.println(indentString + "\t" + "side " + side);
		printStream.println(indentString + "\t" + "top " + top);
		printStream.println(indentString + "\t" + "bottom " + bottom);
	}
}
