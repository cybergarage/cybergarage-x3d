/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: TriangleSetNode.java
*
*	Revisions:
*
*	11/27/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class TriangleSetNode extends ComposedGeometryNode 
{
	//// Field ////////////////
	private final static String convexFieldName = "convex";
	private final static String creaseAngleFieldName = "creaseAngle";

	private SFBool convexField;
	private SFFloat creaseAngleField;
		
	public TriangleSetNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.TRIANGLESET);

		///////////////////////////
		// Field 
		///////////////////////////

		// convex  field
		convexField = new SFBool(true);
		convexField.setName(convexFieldName);
		addField(convexField);

		// creaseAngle  field
		creaseAngleField = new SFFloat(0.0f);
		creaseAngleField.setName(creaseAngleFieldName);
		addField(creaseAngleField);
	}

	public TriangleSetNode(TriangleSetNode node) 
	{
		this();
		setFieldValues(node);
	}
	
	////////////////////////////////////////////////
	//	Convex
	////////////////////////////////////////////////

	public SFBool getConvexField() {
		if (isInstanceNode() == false)
			return convexField;
		return (SFBool)getField(convexFieldName);
	}
	
	public void setConvex(boolean value) {
		getConvexField().setValue(value);
	}

	public void setConvex(String value) {
		getConvexField().setValue(value);
	}

	public boolean getConvex() {
		return getConvexField().getValue();
	}

	public boolean isConvex() {
		return getConvex();
	}

	////////////////////////////////////////////////
	//	CreaseAngle
	////////////////////////////////////////////////
	
	public SFFloat getCreaseAngleField() {
		if (isInstanceNode() == false)
			return creaseAngleField;
		return (SFFloat)getField(creaseAngleFieldName);
	}
	
	public void setCreaseAngle(float value) {
		getCreaseAngleField().setValue(value);
	}

	public void setCreaseAngle(String value) {
		getCreaseAngleField().setValue(value);
	}

	public float getCreaseAngle() {
		return getCreaseAngleField().getValue();
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node)
	{
		if (node.isColorNode() || node.isCoordinateNode() || node.isNormalNode() || node.isTextureCoordinateNode())
			return true;
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
