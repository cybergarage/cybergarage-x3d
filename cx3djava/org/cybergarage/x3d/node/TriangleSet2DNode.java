/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : TriangleSet2DNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class TriangleSet2DNode extends Geometry2DNode {
	
	private String verticesFieldName = "vertices";

	private MFVec2f verticesField;
	
	public TriangleSet2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.TRIANGLESET2D);

		// vertices field
		verticesField = new MFVec2f();
		addField(verticesFieldName, verticesField);
	}

	public TriangleSet2DNode(TriangleSet2DNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Vertices
	////////////////////////////////////////////////

	public MFVec2f getVerticesField() {
		if (isInstanceNode() == false)
			return verticesField;
		return (MFVec2f)getField(verticesFieldName);
	}

	public int getNVertices() {
		return getVerticesField().getSize();
	}

	public void addVertex(float vertex[]) {
		getVerticesField().addValue(vertex);
	}

	public void addVertex(float x, float y) {
		getVerticesField().addValue(x, y);
	}

	public void setVertex(int index, float Vertex[]) {
		getVerticesField().set1Value(index, Vertex);
	}

	public void setVertex(int index, float x, float y) {
		getVerticesField().set1Value(index, x, y);
	}

	public void setVertices(String value) {
		getVerticesField().setValues(value);
	}

	public void setVertices(String value[]) {
		getVerticesField().setValues(value);
	}

	public void getVertex(int index, float Vertex[]) {
		getVerticesField().get1Value(index, Vertex);
	}

	public float[] getVertex(int index) {
		float value[] = new float[2];
		getVertex(index, value);
		return value;
	}

	public void removeVertex(int index) {
		getVerticesField().removeValue(index);
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
