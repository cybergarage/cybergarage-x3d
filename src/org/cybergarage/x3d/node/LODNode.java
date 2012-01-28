/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : LODNode.java
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

public class LODNode extends BoundedGroupingNode 
{
	private final static String centerFieldName	= "center";
	private final static String rangeFieldName		= "range";
	private final static String levelExposedField	= "level";

	private SFVec3f	centerField;
	private MFFloat	rangeField;
	private MFNode	levelField;
	
	public LODNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.LOD);

		// center field
		centerField = new SFVec3f(0.0f, 0.0f, 0.0f);
		addField(centerFieldName, centerField);

		// range field
		rangeField = new MFFloat();
		addField(rangeFieldName, rangeField);

		// level exposedField
		levelField = new MFNode();
		addExposedField(levelExposedField, levelField);
	}

	public LODNode(LODNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	center
	////////////////////////////////////////////////
	
	public SFVec3f getCenterField() {
		if (isInstanceNode() == false)
			return centerField;
		return (SFVec3f)getField(centerFieldName);
	}

	public void setCenter(float value[]) {
		getCenterField().setValue(value);
	}

	public void setCenter(float x, float y, float z) {
		getCenterField().setValue(x, y, z);
	}

	public void setCenter(String value) {
		getCenterField().setValue(value);
	}

	public void getCenter(float value[]) {
		getCenterField().getValue(value);
	}

	////////////////////////////////////////////////
	//	range 
	////////////////////////////////////////////////

	public MFFloat getRangeField() {
		if (isInstanceNode() == false)
			return rangeField;
		return (MFFloat)getField(rangeFieldName);
	}

	public void addRange(float value) {
		getRangeField().addValue(value);
	}

	public int getNRanges() {
		return getRangeField().getSize();
	}

	public void setRange(int index, float value) {
		getRangeField().set1Value(index, value);
	}

	public void setRanges(String value) {
		getRangeField().setValues(value);
	}

	public void setRanges(String value[]) {
		getRangeField().setValues(value);
	}

	public float getRange(int index) {
		return getRangeField().get1Value(index);
	}

	public void removeRange(int index) {
		getRangeField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	level
	////////////////////////////////////////////////

	public MFNode getLevelField() {
		if (isInstanceNode() == false)
			return levelField;
		return (MFNode)getExposedField(levelExposedField);
	}

	public void updateLevelField() {
		MFNode levelField = getLevelField();
		levelField.removeAllValues();
		for (Node node=getChildNodes(); node != null; node=node.next()) 
			levelField.addValue(node);
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
		updateLevelField();
		//updateBoundingBox();
	}

	public void uninitialize() 
	{
	}

	public void update() 
	{
		//updateBoundingBox();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFVec3f center = getCenterField();
		printStream.println(indentString + "\t" + "center " + center);

		MFFloat range = getRangeField();
		printStream.println(indentString + "\t" + "range [");
		range.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
