/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: TriangleStripSetNode.java
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

public class TriangleStripSetNode extends TriangleSetNode 
{
	private final static String stripCountFieldName = "stripCount";

	private MFInt32 stripCountField;
		
	public TriangleStripSetNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.TRIANGLESTRIPSET);

		///////////////////////////
		// Field 
		///////////////////////////

		// stripCount  field
		stripCountField = new MFInt32();
		stripCountField.setName(stripCountFieldName);
		addField(stripCountField);
	}

	public TriangleStripSetNode(TriangleStripSetNode node) 
	{
		this();
		setFieldValues(node);
	}
	
	////////////////////////////////////////////////
	// StripCount
	////////////////////////////////////////////////

	public MFInt32 getStripCountField() {
		if (isInstanceNode() == false)
			return stripCountField;
		return (MFInt32)getField(stripCountFieldName);
	}

	public void addStripCount(int value) {
		getStripCountField().addValue(value);
	}
	
	public int getNColorIndices() {
		return getStripCountField().getSize();
	}
	
	public void setStripCount(int index, int value) {
		getStripCountField().set1Value(index, value);
	}

	public void setColorIndices(String value) {
		getStripCountField().setValues(value);
	}
	
	public int getStripCount(int index) {
		return getStripCountField().get1Value(index);
	}
	
	public void removeStripCount(int index) {
		getStripCountField().removeValue(index);
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