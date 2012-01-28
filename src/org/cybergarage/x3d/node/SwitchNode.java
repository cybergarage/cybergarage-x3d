/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Switch.java
*
*	Revisions:
*
*	11/18/02
*		- Changed the super class from GroupingNode to BoundedGroupingNode.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class SwitchNode extends BoundedGroupingNode 
{
	private String	whichChoiceFieldName	= "whichChoice";
	private String	choiceExposedField		= "choice";

	private SFInt32	whichChoiceField;
	private MFNode	choiceField;
	
	public SwitchNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.SWITCH);

		// whichChoice field
		whichChoiceField = new SFInt32(-1);
		addField(whichChoiceFieldName, whichChoiceField);

		// choice exposedField
		choiceField = new MFNode();
		addExposedField(choiceExposedField, choiceField);
	}

	public SwitchNode(SwitchNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	whichChoice
	////////////////////////////////////////////////

	public SFInt32 getWhichChoiceField() {
		if (isInstanceNode() == false)
			return whichChoiceField;
		return (SFInt32)getField(whichChoiceFieldName);
	}

	public void setWhichChoice(int value) {
		getWhichChoiceField().setValue(value);
	}

	public void setWhichChoice(String value) {
		getWhichChoiceField().setValue(value);
	}

	public int getWhichChoice() {
		return getWhichChoiceField().getValue();
	}

	////////////////////////////////////////////////
	//	choice
	////////////////////////////////////////////////

	public MFNode getChoiceField() {
		if (isInstanceNode() == false)
			return choiceField;
		return (MFNode)getExposedField(choiceExposedField);
	}

	public void updateChoiceField() {
		MFNode choiceField = getChoiceField();
		choiceField.removeAllValues();
		for (Node node=getChildNodes(); node != null; node=node.next()) 
			choiceField.addValue(node);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isCommonNode() || node.isBindableNode() ||node.isInterpolatorNode() || node.isSensorNode() || node.isGroupingNode() || node.isSpecialGroupNode())
			return true;
		else
			return false;
	}

	public void initialize() 
	{
		super.initialize();
		updateChoiceField();
		updateBoundingBox();
	}

	public void uninitialize() 
	{
	}

	public void update() 
	{
		updateBoundingBox();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		printStream.println(indentString + "\t" + "whichChoice " + getWhichChoice());
	}
}
