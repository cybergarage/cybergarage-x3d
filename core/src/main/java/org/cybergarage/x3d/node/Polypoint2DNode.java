/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Polypoint2DNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class Polypoint2DNode extends Geometry2DNode {
	
	private String pointsFieldName = "points";

	private MFVec2f pointsField;
	
	public Polypoint2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.POLYPOINT2D);

		// points field
		pointsField = new MFVec2f();
		addField(pointsFieldName, pointsField);
	}

	public Polypoint2DNode(Polypoint2DNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Points
	////////////////////////////////////////////////

	public MFVec2f getPointsField() {
		if (isInstanceNode() == false)
			return pointsField;
		return (MFVec2f)getField(pointsFieldName);
	}

	public int getNPoints() {
		return getPointsField().getSize();
	}

	public void addPoint(float point[]) {
		getPointsField().addValue(point);
	}

	public void addPoint(float x, float y) {
		getPointsField().addValue(x, y);
	}

	public void setPoint(int index, float point[]) {
		getPointsField().set1Value(index, point);
	}

	public void setPoint(int index, float x, float y) {
		getPointsField().set1Value(index, x, y);
	}

	public void setPoints(String value) {
		getPointsField().setValues(value);
	}

	public void setPoints(String value[]) {
		getPointsField().setValues(value);
	}

	public void getPoint(int index, float point[]) {
		getPointsField().get1Value(index, point);
	}

	public float[] getPoint(int index) {
		float value[] = new float[2];
		getPoint(index, value);
		return value;
	}

	public void removePoint(int index) {
		getPointsField().removeValue(index);
	}
	
	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node)
	{
		return false;
	}

	public void initialize() 
	{
	}

	public void uninitialize() 
	{
	}

	public void update() 
	{
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}
