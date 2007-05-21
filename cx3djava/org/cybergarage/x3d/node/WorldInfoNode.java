/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : WorldInfoNode.java
*
*	11/20/02
*		- Changed the super class from Node to InfoNode.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class WorldInfoNode extends InfoNode 
{
	private final static String titleExposedFieldName	= "title";
	private final static String infoExposedFieldName		= "info";

	private SFString titleField;
	private MFString infoField;

	public WorldInfoNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.WORLDINFO);

		// title exposed field
		titleField = new SFString();
		addExposedField(titleExposedFieldName, titleField);

		// info exposed field
		infoField = new MFString();
		addExposedField(infoExposedFieldName, infoField);
	}

	public WorldInfoNode(WorldInfoNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Title
	////////////////////////////////////////////////

	public SFString getTitleField() {
		if (isInstanceNode() == false)
			return titleField;
		return (SFString)getExposedField(titleExposedFieldName);
	}
	
	public void setTitle(String value) {
		getTitleField().setValue(value);
	}
	
	public String getTitle() {
		return getTitleField().getValue();
	}

	////////////////////////////////////////////////
	// Info
	////////////////////////////////////////////////

	public MFString getInfoField() {
		if (isInstanceNode() == false)
			return infoField;
		return (MFString)getExposedField(infoExposedFieldName);
	}

	public void addInfo(String value) {
		getInfoField().addValue(value);
	}
	
	public int getNInfos() {
		return getInfoField().getSize();
	}
	
	public void setInfo(int index, String value) {
		getInfoField().set1Value(index, value);
	}

	public void setInfos(String value) {
		getInfoField().setValues(value);
	}

	public void setInfos(String value[]) {
		getInfoField().setValues(value);
	}
	
	public String getInfo(int index) {
		return getInfoField().get1Value(index);
	}
	
	public void removeInfo(int index) {
		getInfoField().removeValue(index);
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
		SFString title = getTitleField();
		printStream.println(indentString + "\t" + "title " + title );

		MFString info = getInfoField();
		printStream.println(indentString + "\t" + "info [");
		info.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
