/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2003
*
*	File : RouteNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.field.*;

public class RouteNode extends Node {
	
	private static final String fromFieldName = "fromField";
	private static final String fromNodeName = "fromNode";
	private static final String toFieldName = "toField";
	private static final String toNodeName = "toNode";

	private SFString fromField;
	private SFString fromNode;
	private SFString toField;
	private SFString toNode;

	public RouteNode() 
	{
		// fromField
		fromField = new SFString();
		addExposedField(fromFieldName, fromField);

		// fromNode
		fromNode = new SFString();
		addExposedField(fromNodeName, fromNode);

		// toField
		toField = new SFString();
		addExposedField(toFieldName, toField);

		// toNode
		toNode = new SFString();
		addExposedField(toNodeName, toNode);
	}

	////////////////////////////////////////////////
	//	FromField
	////////////////////////////////////////////////

	public SFString getFromField() {
		if (isInstanceNode() == false)
			return fromField;
		return (SFString)getExposedField(fromFieldName);
	}
	
	public void setFromFieldName(String value) {
		getFromField().setValue(value);
	}

	public String getFromFieldName() {
		return getFromField().getValue();
	}

	////////////////////////////////////////////////
	//	FromNode
	////////////////////////////////////////////////

	public SFString getFromNode() {
		if (isInstanceNode() == false)
			return fromNode;
		return (SFString)getExposedField(fromNodeName);
	}
	
	public void setFromNodeName(String value) {
		getFromNode().setValue(value);
	}

	public String getFromNodeName() {
		return getFromNode().getValue();
	}

	////////////////////////////////////////////////
	//	ToField
	////////////////////////////////////////////////

	public SFString getToField() {
		if (isInstanceNode() == false)
			return toField;
		return (SFString)getExposedField(toFieldName);
	}
	
	public void setToFieldName(String value) {
		getToField().setValue(value);
	}

	public String getToFieldName() {
		return getToField().getValue();
	}

	////////////////////////////////////////////////
	//	ToNode
	////////////////////////////////////////////////

	public SFString getToNode() {
		if (isInstanceNode() == false)
			return toNode;
		return (SFString)getExposedField(toNodeName);
	}
	
	public void setToNodeName(String value) {
		getToNode().setValue(value);
	}

	public String getToNodeName() {
		return getToNode().getValue();
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
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
}
