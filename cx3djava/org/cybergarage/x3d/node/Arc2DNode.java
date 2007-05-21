/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: Arc2DNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class Arc2DNode extends Geometry2DNode {

	private static final String radiusFieldName = "radius";
	private static final String startAngleFieldName = "startAngle";
	private static final String endAngleFieldName = "endAngle";

	private SFFloat radiusField;
	private SFFloat startAngleField;
	private SFFloat endAngleField;
	
	public Arc2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.ARC2D);

		// radius field
		radiusField = new SFFloat(1.0f);
		radiusField.setName(radiusFieldName);
		addField(radiusField);

		// startAngle field
		startAngleField = new SFFloat(1.0f);
		startAngleField.setName(startAngleFieldName);
		addField(startAngleField);

		// endAngle field
		endAngleField = new SFFloat(1.0f);
		endAngleField.setName(endAngleFieldName);
		addField(endAngleField);
	}

	public Arc2DNode(Arc2DNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	radius
	////////////////////////////////////////////////

	public SFFloat getRadiusField() {
		if (isInstanceNode() == false)
			return radiusField;
		return (SFFloat)getField(radiusFieldName);
	}

	public void setRadius(float value) 
	{
		getRadiusField().setValue(value);
	}
	
	public float getRadius() 
	{
		return getRadiusField().getValue();
	}

	////////////////////////////////////////////////
	//	startAngleField
	////////////////////////////////////////////////

	public SFFloat getStartAngleField() {
		if (isInstanceNode() == false)
			return startAngleField;
		return (SFFloat)getField(startAngleFieldName);
	}

	public void setStartAngle(float value) 
	{
		getStartAngleField().setValue(value);
	}
	
	public float getStartAngle() 
	{
		return getStartAngleField().getValue();
	}

	////////////////////////////////////////////////
	//	endAngleField
	////////////////////////////////////////////////

	public SFFloat getEndAngleField() {
		if (isInstanceNode() == false)
			return endAngleField;
		return (SFFloat)getField(endAngleFieldName);
	}

	public void setEndAngle(float value) 
	{
		getEndAngleField().setValue(value);
	}
	
	public float getEndAngle() 
	{
		return getEndAngleField().getValue();
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
