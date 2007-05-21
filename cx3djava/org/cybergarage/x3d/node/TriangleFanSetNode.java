/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: TriangleFanSetNode.java
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

public class TriangleFanSetNode extends TriangleSetNode 
{
	private final static String fanCountFieldName = "fanCount";

	private MFInt32 fanCountField;
		
	public TriangleFanSetNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.TRIANGLEFANSET);

		///////////////////////////
		// Field 
		///////////////////////////

		// fanCount  field
		fanCountField = new MFInt32();
		fanCountField.setName(fanCountFieldName);
		addField(fanCountField);
	}

	public TriangleFanSetNode(TriangleFanSetNode node) 
	{
		this();
		setFieldValues(node);
	}
	
	////////////////////////////////////////////////
	// FanCount
	////////////////////////////////////////////////

	public MFInt32 getFanCountField() {
		if (isInstanceNode() == false)
			return fanCountField;
		return (MFInt32)getField(fanCountFieldName);
	}

	public void addFanCount(int value) {
		getFanCountField().addValue(value);
	}
	
	public int getNColorIndices() {
		return getFanCountField().getSize();
	}
	
	public void setFanCount(int index, int value) {
		getFanCountField().set1Value(index, value);
	}

	public void setColorIndices(String value) {
		getFanCountField().setValues(value);
	}
	
	public int getFanCount(int index) {
		return getFanCountField().get1Value(index);
	}
	
	public void removeFanCount(int index) {
		getFanCountField().removeValue(index);
	}
	
	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
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