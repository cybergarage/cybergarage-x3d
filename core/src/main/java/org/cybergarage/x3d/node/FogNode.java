/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Fog.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class FogNode extends BindableNode {

	//// Exposed Field ////////////////
	private String	colorExposedFieldName			= "color";
	private String	fogTypeExposedFieldName			= "fogType";
	private String	visibilityRangeExposedFieldName	= "visibilityRange";

	private SFColor		colorField;
	private SFString		fogTypeField;
	private SFFloat		visibilityRangeField;

	public FogNode() {

		setHeaderFlag(false);
		setType(NodeType.FOG);

		///////////////////////////
		// Exposed Field 
		///////////////////////////
		
		// color exposed field
		colorField = new SFColor(1, 1, 1);
		addExposedField(colorExposedFieldName, colorField);

		// fogType exposed field
		fogTypeField = new SFString("LINEAR");
		addExposedField(fogTypeExposedFieldName, fogTypeField);

		// visibilityRange exposed field
		visibilityRangeField = new SFFloat(0);
		addExposedField(visibilityRangeExposedFieldName, visibilityRangeField);
	}

	public FogNode(FogNode node) {
		this();
		setFieldValues(node);
	}
	
	////////////////////////////////////////////////
	//	Color
	////////////////////////////////////////////////

	public SFColor getColorField() {
		if (isInstanceNode() == false)
			return colorField;
		return (SFColor)getExposedField(colorExposedFieldName);
	}

	public void setColor(float value[]) {
		getColorField().setValue(value);
	}
	
	public void setColor(float r, float g, float b) {
		getColorField().setValue(r, g, b);
	}

	public void setColor(String value) {
		getColorField().setValue(value);
	}

	public void getColor(float value[]) {
		getColorField().getValue(value);
	}

	////////////////////////////////////////////////
	//	FogType
	////////////////////////////////////////////////

	public SFString getFogTypeField() {
		if (isInstanceNode() == false)
			return fogTypeField;
		return (SFString)getExposedField(fogTypeExposedFieldName);
	}

	public void setFogType(String value) {
		getFogTypeField().setValue(value);
	}
	
	public String getFogType() {
		return getFogTypeField().getValue();
	}

	public boolean isLiner() {
		String linerString = "LINEAR";
		return linerString.equalsIgnoreCase(getFogType());
	}

	public boolean isExponential() {
		String linerString = "EXPONENTIAL";
		return linerString.equalsIgnoreCase(getFogType());
	}
	
	////////////////////////////////////////////////
	//	VisibilityRange
	////////////////////////////////////////////////

	public SFFloat getVisibilityRangeField() {
		if (isInstanceNode() == false)
			return visibilityRangeField;
		return (SFFloat)getExposedField(visibilityRangeExposedFieldName);
	}

	public void setVisibilityRange(float value) {
		getVisibilityRangeField().setValue(value);
	}

	public void setVisibilityRange(String value) {
		getVisibilityRangeField().setValue(value);
	}

	public float getVisibilityRange() {
		return getVisibilityRangeField().getValue();
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
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {

		SFColor color = getColorField();
		SFString fogType = getFogTypeField();

		printStream.println(indentString + "\t" + "color " + color);
		printStream.println(indentString + "\t" + "fogType " + fogType);
		printStream.println(indentString + "\t" + "visibilityRange " + getVisibilityRange());

	}
}
