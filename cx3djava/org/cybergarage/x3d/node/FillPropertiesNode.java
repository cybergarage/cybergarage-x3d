/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : FillPropertiesNode.java
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

public class FillPropertiesNode extends AppearanceChildNode 
{
	private final static String fillStyleFieldName			= "fillStyle";
	private final static String hatchStyleFieldName		= "hatchStyle";
	private final static String hatchColorFieldName		= "hatchColor";

	private SFString fillStyleField;
	private SFInt32 hatchStyleField;
	private SFColor hatchColorField;

	public final static String NONE = "NONE";
	public final static String HATCHED = "HATCHED";

	public FillPropertiesNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.FILLPROPERTIES);

		// fillStyle exposed field
		fillStyleField = new SFString(NONE);
		fillStyleField.setName(fillStyleFieldName);
		addExposedField(fillStyleField);

		// hatchStyle exposed field
		hatchStyleField = new SFInt32(1);
		hatchStyleField.setName(hatchStyleFieldName);
		addExposedField(hatchStyleField);

		// hatchColor exposed field
		hatchColorField = new SFColor(1.0f, 1.0f, 1.0f);
		hatchColorField.setName(hatchColorFieldName);
		addExposedField(hatchColorField);
	}

	public FillPropertiesNode(FillPropertiesNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	FillStyle
	////////////////////////////////////////////////

	public SFString getFillStyleField() {
		if (isInstanceNode() == false)
			return fillStyleField;
		return (SFString)getExposedField(fillStyleFieldName);
	}
	
	public void setFillStyle(String value) {
		getFillStyleField().setValue(value);
	}

	public String getFillStyle() {
		return getFillStyleField().getValue();
	}

	////////////////////////////////////////////////
	//	HatchStyle
	////////////////////////////////////////////////

	public SFInt32 getHatchStyleField() {
		if (isInstanceNode() == false)
			return hatchStyleField;
		return (SFInt32)getExposedField(hatchStyleFieldName);
	}
	
	public void setHatchStyle(int value) {
		getHatchStyleField().setValue(value);
	}

	public int getHatchStyle() {
		return getHatchStyleField().getValue();
	}

	////////////////////////////////////////////////
	//	HatchColor
	////////////////////////////////////////////////

	public SFColor getHatchColorField() {
		if (isInstanceNode() == false)
			return hatchColorField;
		return (SFColor)getExposedField(hatchColorFieldName);
	}

	public void setHatchColor(float value[]) {
		getHatchColorField().setValue(value);
	}
	
	public void setHatchColor(float r, float g, float b) {
		getHatchColorField().setValue(r, g, b);
	}

	public void setHatchColor(String value) {
		getHatchColorField().setValue(value);
	}
	
	public void getHatchColor(float value[]) {
		getHatchColorField().getValue(value);
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
