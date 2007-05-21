/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Rectangle2DNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class Rectangle2DNode extends Geometry2DNode {
	
	private String sizeFieldName = "size";
	private String isFilledFieldName = "isFilled";

	private SFVec2f sizeField;
	private SFBool 	isFilledField;
	
	public Rectangle2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.RECTANGLE2D);

		// size field
		sizeField = new SFVec2f(2, 2);
		addField(sizeFieldName, sizeField);

		// isFilled field
		isFilledField = new SFBool(true);
		addExposedField(isFilledFieldName, isFilledField);
	}

	public Rectangle2DNode(Rectangle2DNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Size
	////////////////////////////////////////////////

	public SFVec2f getSizeField() {
		if (isInstanceNode() == false)
			return sizeField;
		return (SFVec2f)getField(sizeFieldName);
	}
	
	public void setSize(float x, float y) {
		getSizeField().setValue(x, y);
	}

	public void setSize(float value[]) {
		getSizeField().setValue(value);
	}

	public void setSize(String value) {
		getSizeField().setValue(value);
	}
	
	public float getX() {
		return getSizeField().getX();
	} 

	public float getY() {
		return getSizeField().getY();
	} 

	public void getSize(float value[]) {
		getSizeField().getValue(value);
	} 

	public float[] getSize() {
		float value[] = new float[2];
		getSizeField().getValue(value);
		return value;
	} 

	////////////////////////////////////////////////
	//	isFilled
	////////////////////////////////////////////////

	public SFBool getIsFilledField() {
		if (isInstanceNode() == false)
			return isFilledField;
		return (SFBool)getExposedField(isFilledFieldName);
	}

	public void setIsFilled(boolean value) {
		getIsFilledField().setValue(value);
	}

	public void setIsFilled(String value) {
		getIsFilledField().setValue(value);
	}
	
	public boolean getIsFilled() {
		return getIsFilledField().getValue();
	}

	public boolean isFilled() {
		return getIsFilledField().getValue();
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
