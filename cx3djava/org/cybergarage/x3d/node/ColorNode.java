/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ColorNode.java
*
*	Revisions:
*
*	11/25/02
*		- Changed the super class from Node to GeometricPropertyNode.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class ColorNode extends GeometricPropertyNode 
{
	private final static String colorFieldName = "color";

	private MFColor	colorField;
	
	public ColorNode() {
		setHeaderFlag(false);
		setType(NodeType.COLOR);

		// color exposed field
		colorField = new MFColor();
		colorField.setName(colorFieldName);
		addExposedField(colorField);
	}

	public ColorNode(ColorNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	color
	////////////////////////////////////////////////

	public MFColor gddColorField() {
		if (isInstanceNode() == false)
			return colorField;
		return (MFColor)getExposedField(colorFieldName);
	}
	
	public void addColor(float color[]) {
		gddColorField().addValue(color);
	}
	
	public void addColor(float r, float g, float b) {
		gddColorField().addValue(r, g, b);
	}
	
	public int getNColors() {
		return gddColorField().getSize();
	}
	
	public void setColor(int index, float color[]) {
		gddColorField().set1Value(index, color);
	}
	
	public void setColor(int index, float r, float g, float b) {
		gddColorField().set1Value(index, r, g, b);
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
		float value[] = new float[3];
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

	public void outputContext(PrintWriter printStream, String indentString) {
		float color[] = new float[3];
		printStream.println(indentString + "\tcolor [");
		for (int n=0; n<getNColors(); n++) {
			getColor(n, color);
			if (n < getNColors()-1)
				printStream.println(indentString + "\t\t" + color[X] + " " + color[Y] + " " + color[Z] + ",");
			else	
				printStream.println(indentString + "\t\t" + color[X] + " " + color[Y] + " " + color[Z]);
		}
		printStream.println(indentString + "\t]");
	}
}