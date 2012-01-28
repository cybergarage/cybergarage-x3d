/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ScriptNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class ScriptNode extends Node { 
 
	private String	urlExposedFieldName		= "url";
	private String	directOutputFieldName	= "directOutput";
	private String	mustEvaluateFieldName	= "mustEvaluate";

	private SFBool 	directOutputField;
	private SFBool 	mustEvaluateField;
	private MFString urlField;

	public ScriptNode() {
		setHeaderFlag(false);
		setType(NodeType.SCRIPT);

		// directOutput exposed field
		directOutputField = new SFBool(false);
		addField(directOutputFieldName, directOutputField);

		// directOutput exposed field
		mustEvaluateField = new SFBool(false);
		addField(mustEvaluateFieldName, mustEvaluateField);

		// url exposed field
		urlField = new MFString();
		addExposedField(urlExposedFieldName, urlField);
	}

	public ScriptNode(ScriptNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	// DirectOutput
	////////////////////////////////////////////////

	public SFBool getDirectOutputField() {
		if (isInstanceNode() == false)
			return directOutputField;
		return (SFBool)getField(directOutputFieldName);
	}

	public void setDirectOutput(boolean value) {
		getDirectOutputField().setValue(value);
	}

	public void setDirectOutput(String value) {
		getDirectOutputField().setValue(value);
	}
	
	public boolean getDirectOutput() {
		return getDirectOutputField().getValue();
	}

	public boolean isDirectOutput() {
		return getDirectOutput();
	}

	////////////////////////////////////////////////
	// MustEvaluate
	////////////////////////////////////////////////

	public SFBool getMustEvaluateField() {
		if (isInstanceNode() == false)
			return mustEvaluateField;
		return (SFBool)getField(mustEvaluateFieldName);
	}
	
	public void setMustEvaluate(boolean value) {
		getMustEvaluateField().setValue(value);
	}

	public void setMustEvaluate(String value) {
		getMustEvaluateField().setValue(value);
	}

	public boolean getMustEvaluate() {
		return getMustEvaluateField().getValue();
	}

	public boolean isMustEvaluate() {
		return getMustEvaluate();
	}

	////////////////////////////////////////////////
	// URL
	////////////////////////////////////////////////

	public MFString getURLField() {
		if (isInstanceNode() == false)
			return urlField;
		return (MFString)getExposedField(urlExposedFieldName);
	}

	public void addURL(String value) {
		getURLField().addValue(value);
	}
	
	public int getNURLs() {
		return getURLField().getSize();
	}
	
	public void setURL(int index, String value) {
		getURLField().set1Value(index, value);
	}

	public void setURLs(String value) {
		getURLField().setValues(value);
	}

	public void setURLs(String value[]) {
		getURLField().setValues(value);
	}
	
	public String getURL(int index) {
		return getURLField().get1Value(index);
	}
	
	public void removetURL(int index) {
		getURLField().removeValue(index);
	}

	////////////////////////////////////////////////
	// Add Field
	////////////////////////////////////////////////

	public void addEventOut(String fieldType, String name) {
		Field field = createFieldFromString(fieldType);
		if (field != null) {
			addEventOut(name, field);
		}
	}

	public void addEventIn(String fieldType, String name) {
		Field field = createFieldFromString(fieldType);
		if (field != null) {
			addEventIn(name, field);
		}
	}

	public void addField(String fieldType, String name, String value) {
		Field field = createFieldFromString(fieldType);

		if (field != null) {
			if (fieldType.compareTo("SFBool") == 0)
				((SFBool)field).setValue(value);
/*
			else if (fieldType.compareTo("SFColor") == 0)
				return new SFColor(0.0f, 0.0f, 0.0f);
*/
			else if (fieldType.compareTo("SFFloat") == 0)
				((SFFloat)field).setValue(value);
			else if (fieldType.compareTo("SFInt32") == 0)
				((SFInt32)field).setValue(value);
/*
			else if (fieldType.compareTo("SFRotation") == 0)
				return new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
*/
			else if (fieldType.compareTo("SFString") == 0)
				((SFString)field).setValue(value);
			else if (fieldType.compareTo("SFTime") == 0)
				((SFTime)field).setValue(value);
/*
			else if (fieldType.compareTo("SFVec2f") == 0)
				return new SFVec2f(0.0f, 0.0f);
*/
			else if (fieldType.compareTo("SFVec3f") == 0)
				((SFVec3f)field).setValue(value);

			addField(name, field);
		}
	}

	////////////////////////////////////////////////
	//	abstruct functions
	////////////////////////////////////////////////

	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
			
		SFBool directOutput = getDirectOutputField();
		SFBool mustEvaluate = getMustEvaluateField();

		printStream.println(indentString + "\t" + "directOutput " + directOutput);
		printStream.println(indentString + "\t" + "mustEvaluate " + mustEvaluate);

		MFString url = getURLField();
		printStream.println(indentString + "\t" + "url [");
		url.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		for (int n=0; n<getNEventIn(); n++) {
			Field field = getEventIn(n);
			printStream.println(indentString + "\t" + "eventIn " + field.getTypeName() + " " + field.getName());
		}

		for (int n=0; n<getNEventOut(); n++) {
			Field field = getEventOut(n);
			printStream.println(indentString + "\t" + "eventOut " + field.getTypeName() + " " + field.getName());
		}

		for (int n=0; n<getNFields(); n++) {
			Field field = getField(n);
			String fieldName = field.getName();
			if (fieldName.compareTo(directOutputFieldName) != 0 && fieldName.compareTo(mustEvaluateFieldName) != 0) {
				if (field.getType().equals(FieldType.SFNODE) == true) {
					BaseNode	node = ((SFNode)field).getValue();
					String		nodeName = null;
					if (node != null)
						nodeName = node.getName();
					if (nodeName != null) 
						printStream.println(indentString + "\t" + "field SFNode" + " " + field.getName() + " USE " + nodeName);
					else
						printStream.println(indentString + "\t" + "field SFNode" + " " + field.getName() + " NULL");
				}
				else
					printStream.println(indentString + "\t" + "field " + field.getTypeName() + " " + field.getName() + " " + field.toString());
			}
		}

	}
}