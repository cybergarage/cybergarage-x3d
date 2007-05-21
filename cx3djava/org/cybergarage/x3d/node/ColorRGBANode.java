/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ColorRGBANode.java
*
*	Revisions:
*
*	11/25/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class ColorRGBANode extends GeometricPropertyNode 
{
	private final static String colorFieldName = "color";

	private MFColorRGBA colorField;
	
	public ColorRGBANode() {
		setHeaderFlag(false);
		setType(NodeType.COLORRGBA);

		// color exposed field
		colorField = new MFColorRGBA();
		colorField.setName(colorFieldName);
		addExposedField(colorField);
	}

	public ColorRGBANode(ColorRGBANode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	color
	////////////////////////////////////////////////

	public MFColorRGBA gddColorField() {
		if (isInstanceNode() == false)
			return colorField;
		return (MFColorRGBA)getExposedField(colorFieldName);
	}
	
	public void addColor(float color[]) {
		gddColorField().addValue(color);
	}
	
	public void addColor(float r, float g, float b, float a) {
		gddColorField().addValue(r, g, b, a);
	}
	
	public int getNColors() {
		return gddColorField().getSize();
	}
	
	public void setColor(int index, float color[]) {
		gddColorField().set1Value(index, color);
	}
	
	public void setColor(int index, float r, float g, float b, float a) {
		gddColorField().set1Value(index, r, g, b, a);
	}

	public void setColors(String value) {
		gddColorField().setValues(value);
	}

	public void setColors(String value[]) {
		gddColorField().setValues(value);
	}
	
	public void getColor(int index, float color[]) {
		gddColorField().get1Value(index, color);
	}
	
	public float[] getColor(int index) {
		float value[] = new float[4];
		getColor(index, value);
		return value;
	}
	
	public void removeColor(int index) {
		gddColorField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
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
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}
