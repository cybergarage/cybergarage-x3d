/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: Box.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class BoxNode extends Geometry3DNode {

	private final static String sizeFieldName = "size";
	
	private SFVec3f sizeField;
	
	public BoxNode() {

		setHeaderFlag(false);
		setType(NodeType.BOX);

		// size exposed field
		sizeField = new SFVec3f(2.0f, 2.0f, 2.0f);
		sizeField.setName(sizeFieldName);
		addExposedField(sizeField);
	}

	public BoxNode(BoxNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	size
	////////////////////////////////////////////////

	public SFVec3f getSizeField() {
		if (isInstanceNode() == false)
			return sizeField;
		return (SFVec3f)getExposedField(sizeFieldName);
	}

	public void setSize(float value[]) {
		getSizeField().setValue(value);
	}
	
	public void setSize(float x, float y, float z) {
		getSizeField().setValue(x, y, z);
	}

	public void setSize(String value) {
		getSizeField().setValue(value);
	}
	
	public void getSize(float value[]) {
		getSizeField().getValue(value);
	}
	
	public float getX() {
		return getSizeField().getX();
	}
	
	public float getY() {
		return getSizeField().getY();
	}
	
	public float getZ() {
		return getSizeField().getZ();
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
		setBoundingBoxSize(getX()/2.0f, getY()/2.0f, getZ()/2.0f);
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float value[] = new float[3];
		getSize(value);	printStream.println(indentString + "\t" + "size " + value[X] + " "+ value[Y] + " " + value[Z] );
	}
}
