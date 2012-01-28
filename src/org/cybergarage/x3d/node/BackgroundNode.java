/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Background.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class BackgroundNode extends BindableNode {
	
	private String	groundColorFieldName	= "groundColor";
	private String	skyColorFieldName		= "skyColor";
	private String	groundAngleFieldName	= "groundAngle";
	private String	skyAngleFieldName		= "skyAngle";
	private String	frontURLFieldName		= "frontUrl";
	private String	backURLFieldName		= "backUrl";
	private String	leftURLFieldName		= "leftUrl";
	private String	rightURLFieldName		= "rightUrl";
	private String	topURLFieldName			= "topUrl";
	private String	bottomURLFieldName		= "bottomUrl";

	private MFColor 	groundColorField;
	private MFColor 	skyColorField;
	private MFFloat 	groundAngleField;
	private MFFloat 	skyAngleField;
	private MFString frontURLField;
	private MFString backURLField;
	private MFString leftURLField;
	private MFString rightURLField;
	private MFString topURLField;
	private MFString bottomURLField;

	public BackgroundNode() {
		setHeaderFlag(false);
		setType(NodeType.BACKGROUND);

		// groundColor exposed field
		groundColorField = new MFColor();
		addExposedField(groundColorFieldName, groundColorField);

		// skyColor exposed field
		skyColorField = new MFColor();
		addExposedField(skyColorFieldName, skyColorField);

		// groundAngle exposed field
		groundAngleField = new MFFloat();
		addExposedField(groundAngleFieldName, groundAngleField);

		// skyAngle exposed field
		skyAngleField = new MFFloat();
		addExposedField(skyAngleFieldName, skyAngleField);

		// url exposed field
		frontURLField = new MFString();
		addExposedField(frontURLFieldName, frontURLField);

		// url exposed field
		backURLField = new MFString();
		addExposedField(backURLFieldName, backURLField);

		// url exposed field
		leftURLField = new MFString();
		addExposedField(leftURLFieldName, leftURLField);

		// url exposed field
		rightURLField = new MFString();
		addExposedField(rightURLFieldName, rightURLField);

		// url exposed field
		topURLField = new MFString();
		addExposedField(topURLFieldName, topURLField);

		// url exposed field
		bottomURLField = new MFString();
		addExposedField(bottomURLFieldName, bottomURLField);
	}

	public BackgroundNode(BackgroundNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	// groundColor
	////////////////////////////////////////////////

	public MFColor getGroundColorField() {
		if (isInstanceNode() == false)
			return groundColorField;
		return (MFColor)getExposedField(groundColorFieldName);
	}

	public void addGroundColor(float value[]) {
		getGroundColorField().addValue(value);
	}
	
	public void addGroundColor(float r, float g, float b) {
		getGroundColorField().addValue(r, g, b);
	}
	
	public int getNGroundColors() {
		return getGroundColorField().getSize();
	}
	
	public void setGroundColor(int index, float value[]) {
		getGroundColorField().set1Value(index, value);
	}
	
	public void setGroundColor(int index, float r, float g, float b) {
		getGroundColorField().set1Value(index, r, g, b);
	}
	
	public void setGroundColors(String value) {
		getGroundColorField().setValues(value);
	}
	
	public void setGroundColors(String value[]) {
		getGroundColorField().setValues(value);
	}
	
	public void getGroundColor(int index, float value[]) {
		getGroundColorField().get1Value(index, value);
	}
	
	public float[] getGroundColor(int index) {
		float value[] = new float[3];
		getGroundColor(index, value);
		return value;
	}
	
	public void removeGroundColor(int index) {
		getGroundColorField().removeValue(index);
	}

	////////////////////////////////////////////////
	// skyColor
	////////////////////////////////////////////////

	public MFColor getSkyColorField() {
		if (isInstanceNode() == false)
			return skyColorField;
		return (MFColor)getExposedField(skyColorFieldName);
	}

	public void addSkyColor(float value[]) {
		getSkyColorField().addValue(value);
	}
	
	public void addSkyColor(float r, float g, float b) {
		getSkyColorField().addValue(r, g, b);
	}
	
	public int getNSkyColors() {
		return getSkyColorField().getSize();
	}
	
	public void setSkyColor(int index, float value[]) {
		getSkyColorField().set1Value(index, value);
	}
	
	public void setSkyColor(int index, float r, float g, float b) {
		getSkyColorField().set1Value(index, r, g, b);
	}
	
	public void setSkyColors(String value) {
		getSkyColorField().setValues(value);
	}

	public void setSkyColors(String value[]) {
		getSkyColorField().setValues(value);
	}
	
	public void getSkyColor(int index, float value[]) {
		getSkyColorField().get1Value(index, value);
	}
	
	public float[] getSkyColor(int index) {
		float value[] = new float[3];
		getSkyColor(index, value);
		return value;
	}
	
	public void removeSkyColor(int index) {
		getSkyColorField().removeValue(index);
	}

	////////////////////////////////////////////////
	// groundAngle
	////////////////////////////////////////////////

	public MFFloat getGroundAngleField() {
		if (isInstanceNode() == false)
			return groundAngleField;
		return (MFFloat)getExposedField(groundAngleFieldName);
	}

	public void addGroundAngle(float value) {
		getGroundAngleField().addValue(value);
	}
	
	public int getNGroundAngles() {
		return getGroundAngleField().getSize();
	}
	
	public void setGroundAngle(int index, float value) {
		getGroundAngleField().set1Value(index, value);
	}

	public void setGroundAngles(String value) {
		getGroundAngleField().setValues(value);
	}
	
	public void setGroundAngles(String value[]) {
		getGroundAngleField().setValues(value);
	}
	
	public float getGroundAngle(int index) {
		return getGroundAngleField().get1Value(index);
	}
	
	public void removeGroundAngle(int index) {
		getGroundAngleField().removeValue(index);
	}

	////////////////////////////////////////////////
	// skyAngle
	////////////////////////////////////////////////

	public MFFloat getSkyAngleField() {
		if (isInstanceNode() == false)
			return skyAngleField;
		return (MFFloat)getExposedField(skyAngleFieldName);
	}

	public void addSkyAngle(float value) {
		getSkyAngleField().addValue(value);
	}
	
	public int getNSkyAngles() {
		return getSkyAngleField().getSize();
	}
	
	public void setSkyAngle(int index, float value) {
		getSkyAngleField().set1Value(index, value);
	}
	
	public void setSkyAngles(String value) {
		getSkyAngleField().setValues(value);
	}
	
	public void setSkyAngles(String value[]) {
		getSkyAngleField().setValues(value);
	}
	
	public float getSkyAngle(int index) {
		return getSkyAngleField().get1Value(index);
	}
	
	public void removeSkyAngle(int index) {
		getSkyAngleField().removeValue(index);
	}

	////////////////////////////////////////////////
	// frontURL
	////////////////////////////////////////////////

	public MFString getFrontURLField() {
		if (isInstanceNode() == false)
			return frontURLField;
		return (MFString)getExposedField(frontURLFieldName);
	}

	public void addFrontURL(String value) {
		getFrontURLField().addValue(value);
	}
	
	public int getNFrontURLs() {
		return getFrontURLField().getSize();
	}
	
	public void setFrontURL(int index, String value) {
		getFrontURLField().setValues(value);
	}
	
	public void setFrontURLs(String value) {
		getFrontURLField().setValues(value);
	}
	
	public void setFrontURLs(String value[]) {
		getFrontURLField().setValues(value);
	}
	
	public String getFrontURL(int index) {
		return getFrontURLField().get1Value(index);
	}
	
	public void removeFrontURL(int index) {
		getFrontURLField().removeValue(index);
	}

	////////////////////////////////////////////////
	// backURL
	////////////////////////////////////////////////

	public MFString getBackURLField() {
		if (isInstanceNode() == false)
			return backURLField;
		return (MFString)getExposedField(backURLFieldName);
	}
	
	public void addBackURL(String value) {
		getBackURLField().addValue(value);
	}
	
	public int getNBackURLs() {
		return getBackURLField().getSize();
	}
	
	public void setBackURL(int index, String value) {
		getBackURLField().set1Value(index, value);
	}
	
	public void setBackURLs(String value) {
		getBackURLField().setValues(value);
	}
	
	public void setBackURLs(String value[]) {
		getBackURLField().setValues(value);
	}
	
	public String getBackURL(int index) {
		return getBackURLField().get1Value(index);
	}
	
	public void removeBackURL(int index) {
		getBackURLField().removeValue(index);
	}

	////////////////////////////////////////////////
	// leftURL
	////////////////////////////////////////////////

	public MFString getLeftURLField() {
		if (isInstanceNode() == false)
			return leftURLField;
		return (MFString)getExposedField(leftURLFieldName);
	}
	
	public void addLeftURL(String value) {
		getLeftURLField().addValue(value);
	}
	
	public int getNLeftURLs() {
		return getLeftURLField().getSize();
	}
	
	public void setLeftURL(int index, String value) {
		getLeftURLField().set1Value(index, value);
	}
	
	public void setLeftURLs(String value) {
		getLeftURLField().setValues(value);
	}

	public void setLeftURLs(String value[]) {
		getLeftURLField().setValues(value);
	}
	
	public String getLeftURL(int index) {
		return getLeftURLField().get1Value(index);
	}
	
	public void removeLeftURL(int index) {
		getLeftURLField().removeValue(index);
	}

	////////////////////////////////////////////////
	// rightURL
	////////////////////////////////////////////////

	public MFString getRightURLField() {
		if (isInstanceNode() == false)
			return rightURLField;
		return (MFString)getExposedField(rightURLFieldName);
	}
	
	public void addRightURL(String value) {
		getRightURLField().addValue(value);
	}
	
	public int getNRightURLs() {
		return getRightURLField().getSize();
	}
	
	public void setRightURL(int index, String value) {
		getRightURLField().set1Value(index, value);
	}

	public void setRightURLs(String value) {
		getRightURLField().setValues(value);
	}
	
	public void setRightURLs(String value[]) {
		getRightURLField().setValues(value);
	}
	
	public String getRightURL(int index) {
		return getRightURLField().get1Value(index);
	}
	
	public void removeRightURL(int index) {
		getRightURLField().removeValue(index);
	}

	////////////////////////////////////////////////
	// topURL
	////////////////////////////////////////////////

	public MFString getTopURLField() {
		if (isInstanceNode() == false)
			return topURLField;
		return (MFString)getExposedField(topURLFieldName);
	}

	public void addTopURL(String value) {
		getTopURLField().addValue(value);
	}
	
	public int getNTopURLs() {
		return getTopURLField().getSize();
	}
	
	public void setTopURL(int index, String value) {
		getTopURLField().set1Value(index, value);
	}

	public void setTopURLs(String value) {
		getTopURLField().setValues(value);
	}
	
	public void setTopURLs(String value[]) {
		getTopURLField().setValues(value);
	}
	
	public String getTopURL(int index) {
		return getTopURLField().get1Value(index);
	}
	
	public void removeTopURL(int index) {
		getTopURLField().removeValue(index);
	}

	////////////////////////////////////////////////
	// bottomURL
	////////////////////////////////////////////////

	public MFString getBottomURLField() {
		if (isInstanceNode() == false)
			return bottomURLField;
		return (MFString)getExposedField(bottomURLFieldName);
	}
	
	public void addBottomURL(String value) {
		getBottomURLField().addValue(value);
	}
	
	public int getNBottomURLs() {
		return getBottomURLField().getSize();
	}
	
	public void setBottomURL(int index, String value) {
		getBottomURLField().set1Value(index, value);
	}

	public void setBottomURLs(String value) {
		getBottomURLField().setValues(value);
	}
	
	public void setBottomURLs(String value[]) {
		getBottomURLField().setValues(value);
	}
	
	public String getBottomURL(int index) {
		return getBottomURLField().get1Value(index);
	}
	
	public void removeBottomURL(int index) {
		getBottomURLField().removeValue(index);
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

		MFColor groundColor = getGroundColorField();
		printStream.println(indentString + "\t" + "groundColor [");
		groundColor.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFColor skyColor = getSkyColorField();
		printStream.println(indentString + "\t" + "skyColor [");
		skyColor.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");


		MFFloat groundAngle = getGroundAngleField();
		printStream.println(indentString + "\t" + "groundAngle [");
		groundAngle.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFFloat skyAngle = getSkyAngleField();
		printStream.println(indentString + "\t" + "skyAngle [");
		skyAngle.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");


		MFString frontURL = getFrontURLField();
		printStream.println(indentString + "\t" + "frontUrl [");
		frontURL.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString backURL = getBackURLField();
		printStream.println(indentString + "\t" + "backUrl [");
		backURL.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString leftURL = getLeftURLField();
		printStream.println(indentString + "\t" + "leftUrl [");
		leftURL.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString rightURL = getRightURLField();
		printStream.println(indentString + "\t" + "rightUrl [");
		rightURL.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString topURL = getTopURLField();
		printStream.println(indentString + "\t" + "topUrl [");
		topURL.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString bottomURL = getBottomURLField();
		printStream.println(indentString + "\t" + "bottomUrl [");
		bottomURL.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
