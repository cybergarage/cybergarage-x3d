/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : LinePropertiesNode.java
*
*	Revisions:
*
*	12/02/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class LinePropertiesNode extends AppearanceChildNode 
{
	private final static String lineStyleFieldName	= "lineStyle";
	private final static String widthFieldName		= "width";

	private SFInt32 lineStyleField;
	private SFFloat widthField;

	public LinePropertiesNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.LINEPROPERTIES);

		// lineStyle exposed field
		lineStyleField = new SFInt32(0);
		lineStyleField.setName(lineStyleFieldName);
		addExposedField(lineStyleField);

		// width exposed field
		widthField = new SFFloat(0);
		widthField.setName(widthFieldName);
		addExposedField(widthField);
	}

	public LinePropertiesNode(LinePropertiesNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	LineStyle
	////////////////////////////////////////////////

	public SFInt32 getLineStyleField() {
		if (isInstanceNode() == false)
			return lineStyleField;
		return (SFInt32)getExposedField(lineStyleFieldName);
	}
	
	public void setLineStyle(int value) {
		getLineStyleField().setValue(value);
	}

	public int getLineStyle() {
		return getLineStyleField().getValue();
	}

	////////////////////////////////////////////////
	//	Width
	////////////////////////////////////////////////

	public SFFloat getWidthField() {
		if (isInstanceNode() == false)
			return widthField;
		return (SFFloat)getExposedField(widthFieldName);
	}
	
	public void setWidth(float value) {
		getWidthField().setValue(value);
	}

	public float getWidth() {
		return getWidthField().getValue();
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node) {
		return false;
	}

	public void initialize() {
		super.initialize();
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
