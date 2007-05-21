/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: ArcClose2DNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class ArcClose2DNode extends Arc2DNode {

	private static final String closureTypeFieldName = "closureType";
	private SFString closureTypeField;
	
	public ArcClose2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.ARCCLOSE2D);

		// closureType field
		closureTypeField = new SFString("PIE");
		closureTypeField.setName(closureTypeFieldName);
		addField(closureTypeField);
	}

	public ArcClose2DNode(ArcClose2DNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	ClosureType
	////////////////////////////////////////////////

	public SFString getClosureTypeField() {
		if (isInstanceNode() == false)
			return closureTypeField;
		return (SFString)getField(closureTypeFieldName);
	}

	public void setClosureType(String value) 
	{
		getClosureTypeField().setValue(value);
	}
	
	public String getClosureType() 
	{
		return getClosureTypeField().getValue();
	}
	
	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node)
	{
		return false;
	}

	public void initialize() {
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
