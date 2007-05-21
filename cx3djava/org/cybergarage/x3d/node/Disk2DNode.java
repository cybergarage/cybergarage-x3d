/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: Disk2DNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class Disk2DNode extends Geometry2DNode {

	private static final String outerRadiusFieldName = "outerRadius";
	private static final String innerRadiusFieldName = "innerRadius";

	private SFFloat outerRadiusField;
	private SFFloat innerRadiusField;
	
	public Disk2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.DISK2D);
		
		// outerRadius field
		outerRadiusField = new SFFloat(1.0f);
		outerRadiusField.setName(outerRadiusFieldName);
		addField(outerRadiusField);

		// innerRadius field
		innerRadiusField = new SFFloat(1.0f);
		innerRadiusField.setName(innerRadiusFieldName);
		addField(innerRadiusField);
	}

	public Disk2DNode(Disk2DNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	outerRadiusField
	////////////////////////////////////////////////

	public SFFloat getOuterRadiusField() {
		if (isInstanceNode() == false)
			return outerRadiusField;
		return (SFFloat)getField(outerRadiusFieldName);
	}

	public void setOuterRadius(float value) 
	{
		getOuterRadiusField().setValue(value);
	}
	
	public float getOuterRadius() 
	{
		return getOuterRadiusField().getValue();
	}

	////////////////////////////////////////////////
	//	innerRadiusField
	////////////////////////////////////////////////

	public SFFloat getInnerRadiusField() {
		if (isInstanceNode() == false)
			return innerRadiusField;
		return (SFFloat)getField(innerRadiusFieldName);
	}

	public void setInnerRadius(float value) 
	{
		getInnerRadiusField().setValue(value);
	}
	
	public float getInnerRadius() 
	{
		return getInnerRadiusField().getValue();
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

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}
