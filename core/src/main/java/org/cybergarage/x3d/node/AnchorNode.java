/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Anchor.java
*
*	Revisions:
*
*	11/18/02
*		- Changed the super class from GroupingNode to BoundedGroupingNode.
*
*	11/14/02
*		- Added the follwing new X3D fields.
*			center, enabled
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class AnchorNode extends BoundedGroupingNode 
{

	//// VRML97Field ////////////////
	
	private static final String descriptionExposedFieldName = "description";
	private static final String parameterExposedFieldName = "parameter";
	private static final String urlExposedFieldName = "url";

	private SFString descriptionField;
	private MFString parameterField;
	private MFString urlField;
	
	//// X3D Field ////////////////
	
	private static final String enabledFieldName = "enabled";
	private static final String centerFieldName = "center";

	private SFBool enabledField;
	private SFVec3f centerField;
	
	public AnchorNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.ANCHOR);

		///////////////////////////
		// VRML97 Field 
		///////////////////////////

		// description exposed field
		descriptionField = new SFString();
		addExposedField(descriptionExposedFieldName, descriptionField);

		// parameter exposed field
		parameterField = new MFString();
		addExposedField(parameterExposedFieldName, parameterField);

		// url exposed field
		urlField = new MFString();
		addExposedField(urlExposedFieldName, urlField);

		///////////////////////////
		// X3D Field 
		///////////////////////////

		// center exposed field
		centerField = new SFVec3f(0.0f, 0.0f, 0.0f);
		addExposedField(centerFieldName, centerField);
		
		// enabled exposed field
		enabledField = new SFBool(true);
		addExposedField(enabledFieldName, enabledField);
	}

	public AnchorNode(AnchorNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Description
	////////////////////////////////////////////////

	public SFString getDescriptionField() {
		if (isInstanceNode() == false)
			return descriptionField;
		return (SFString)getExposedField(descriptionExposedFieldName);
	}

	public void setDescription(String value) {
		getDescriptionField().setValue(value);
	}

	public String getDescription() {
		return getDescriptionField().getValue();
	}

	////////////////////////////////////////////////
	// Parameter
	////////////////////////////////////////////////

	public MFString getParameterField() {
		if (isInstanceNode() == false)
			return parameterField;
		return (MFString)getExposedField(parameterExposedFieldName);
	}

	public void addParameter(String value) {
		getParameterField().addValue(value);
	}
	
	public int getNParameters() {
		return getParameterField().getSize();
	}
	
	public void setParameter(int index, String value) {
		getParameterField().set1Value(index, value);
	}
	
	public void setParameters(String value) {
		getParameterField().setValues(value);
	}
	
	public void setParameters(String value[]) {
		getParameterField().setValues(value);
	}
	
	public String getParameter(int index) {
		return getParameterField().get1Value(index);
	}
	
	public void removeParameter(int index) {
		getParameterField().removeValue(index);
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
	public void removeURL(int index) {
		getURLField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	Center (X3D)
	////////////////////////////////////////////////

	public SFVec3f getCenterField() {
		if (isInstanceNode() == false)
			return centerField;
		return (SFVec3f)getExposedField(centerFieldName);
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
	
	public void addCenter(float value[]) {
		getCenterField().add(value);
	}
	
	public void addCenter(float x, float y, float z) {
		getCenterField().add(x, y, z);
	}

	////////////////////////////////////////////////
	//	Enabled (X3D)
	////////////////////////////////////////////////

	public SFBool getEnabledField() {
		if (isInstanceNode() == false)
			return enabledField;
		return (SFBool)getExposedField(enabledFieldName);
	}
	
	public void setEnabled(boolean value) {
		getEnabledField().setValue(value);
	}

	public void setEnabled(String value) {
		getEnabledField().setValue(value);
	}
	
	public boolean getEnabled() {
		return getEnabledField().getValue();
	}
	
	public boolean isEnabled() {
		return getEnabled();
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
		updateChildrenField();
		updateBoundingBox();
	}

	public void uninitialize() 
	{
	}

	public void update() 
	{
		//updateChildrenField();
		//updateBoundingBox();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {

		SFString description = getDescriptionField();
		printStream.println(indentString + "\t" + "description " + description);
		
		MFString parameter = getParameterField();
		printStream.println(indentString + "\t" + "parameter [");
		parameter.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString url = getURLField();
		printStream.println(indentString + "\t" + "url [");
		url.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
