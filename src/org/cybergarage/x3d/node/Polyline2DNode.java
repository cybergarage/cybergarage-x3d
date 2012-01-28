/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Polyline2DNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class Polyline2DNode extends Geometry2DNode {
	
	private String lineSegmentsFieldName = "lineSegments";

	private MFVec2f lineSegmentsField;
	
	public Polyline2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.POLYLINE2D);

		// lineSegments field
		lineSegmentsField = new MFVec2f();
		addField(lineSegmentsFieldName, lineSegmentsField);
	}

	public Polyline2DNode(Polyline2DNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	LineSegments
	////////////////////////////////////////////////

	public MFVec2f getLineSegmentsField() {
		if (isInstanceNode() == false)
			return lineSegmentsField;
		return (MFVec2f)getField(lineSegmentsFieldName);
	}

	public int getNLineSegments() {
		return getLineSegmentsField().getSize();
	}

	public void addLineSegment(float point[]) {
		getLineSegmentsField().addValue(point);
	}

	public void addLineSegment(float x, float y) {
		getLineSegmentsField().addValue(x, y);
	}

	public void setLineSegment(int index, float point[]) {
		getLineSegmentsField().set1Value(index, point);
	}

	public void setLineSegment(int index, float x, float y) {
		getLineSegmentsField().set1Value(index, x, y);
	}

	public void setLineSegments(String value) {
		getLineSegmentsField().setValues(value);
	}

	public void setLineSegments(String value[]) {
		getLineSegmentsField().setValues(value);
	}

	public void getLineSegment(int index, float point[]) {
		getLineSegmentsField().get1Value(index, point);
	}

	public float[] getLineSegment(int index) {
		float value[] = new float[2];
		getLineSegment(index, value);
		return value;
	}

	public void removeLineSegment(int index) {
		getLineSegmentsField().removeValue(index);
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
