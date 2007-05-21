/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : MultiTextureTransformNode.java
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

public class MultiTextureTransformNode extends Node 
{
	private final static String textureTransformFieldName = "textureTransform";

	private MFNode	textureTransformField;
	
	public MultiTextureTransformNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.MULTITEXTURETRANSFORM);

		// textureTransform exposedField
		textureTransformField = new MFNode();
		addExposedField(textureTransformFieldName, textureTransformField);
	}

	public MultiTextureTransformNode(MultiTextureTransformNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	textureTransform
	////////////////////////////////////////////////

	public MFNode getTextureTransformField() {
		if (isInstanceNode() == false)
			return textureTransformField;
		return (MFNode)getExposedField(textureTransformFieldName);
	}

	public void updateTextureTransformField() {
		MFNode textureTransformField = getTextureTransformField();
		textureTransformField.removeAllValues();
		for (Node node=getChildNodes(); node != null; node=node.next()) 
			textureTransformField.addValue(node);
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
