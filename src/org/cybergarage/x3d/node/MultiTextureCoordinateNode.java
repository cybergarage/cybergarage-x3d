/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : MultiTextureCoordinateNode.java
*
*	Revisions:
*
*	12/06/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class MultiTextureCoordinateNode extends Node 
{
	private final static String texCoordFieldName = "texCoord";

	private MFNode	texCoordField;
	
	public MultiTextureCoordinateNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.MULTITEXTURECOORD);

		// texCoord exposedField
		texCoordField = new MFNode();
		addExposedField(texCoordFieldName, texCoordField);
	}

	public MultiTextureCoordinateNode(MultiTextureCoordinateNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	texCoord
	////////////////////////////////////////////////

	public MFNode getTexCoordField() {
		if (isInstanceNode() == false)
			return texCoordField;
		return (MFNode)getExposedField(texCoordFieldName);
	}

	public void updateTexCoordField() {
		MFNode texCoordField = getTexCoordField();
		texCoordField.removeAllValues();
		for (Node node=getChildNodes(); node != null; node=node.next()) 
			texCoordField.addValue(node);
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
