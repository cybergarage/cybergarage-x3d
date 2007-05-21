/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : TextureCoordinateGeneratorNode.java
*
*	Revisions:
*
*	12/07/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class TextureCoordinateGeneratorNode extends Node 
{
	private static final String modeFieldName = "mode";
	private static final String parameterFieldName = "parameter";

	private SFString modeField;
	private MFFloat parameterField;
	
	public TextureCoordinateGeneratorNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.TEXCOORDGEN);

		// mode exposed field
		modeField = new SFString();
		addExposedField(modeFieldName, modeField);
		
		// parameter exposed field
		parameterField = new MFFloat();
		addExposedField(parameterFieldName, parameterField);
	}

	public TextureCoordinateGeneratorNode(TextureCoordinateGeneratorNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Mode
	////////////////////////////////////////////////

	public SFString getModeField() {
		if (isInstanceNode() == false)
			return modeField;
		return (SFString)getExposedField(modeFieldName);
	}

	public void setMode(String value) {
		getModeField().setValue(value);
	}

	public String getMode() {
		return getModeField().getValue();
	}

	////////////////////////////////////////////////
	//	parameter
	////////////////////////////////////////////////

	public MFFloat getParameterField() {
		if (isInstanceNode() == false)
			return parameterField;
		return (MFFloat)getExposedField(parameterFieldName);
	}
	
	public void addParameter(float value) {
		getParameterField().addValue(value);
	}
	
	public int getNParameters() {
		return getParameterField().getSize();
	}
	
	public void setParameter(int index, float value) {
		getParameterField().set1Value(index, value);
	}

	public void setParameters(String value) {
		getParameterField().setValues(value);
	}

	public void setParameters(String value[]) {
		getParameterField().setValues(value);
	}
	
	public float getParameter(int index) {
		return getParameterField().get1Value(index);
	}
	
	public void removeParameter(int index) {
		getParameterField().removeValue(index);
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
