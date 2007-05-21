/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : SoundNode.java
*
*	Revisions:
*
*	12/04/02
*		- Added new source field of X3D.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class SoundNode extends Node 
{
	//// Exposed Field ////////////////
	private final static String minFrontExposedFieldName		= "minFront";
	private final static String maxFrontExposedFieldName	= "maxFront";
	private final static String minBackExposedFieldName		= "minBack";
	private final static String maxBackExposedFieldName		= "maxBack";
	private final static String intensityExposedFieldName		= "intensity";
	private final static String priorityExposedFieldName		= "priority";
	private final static String directionExposedFieldName		= "direction";
	private final static String locationExposedFieldName		= "location";
	private final static String sourceExposedFieldName		= "source";

	//// Field ////////////////
	private final static String spatializeFieldName				= "spatialize";

	// minFront exposed field
	private SFFloat minFrontField;
	private SFFloat maxFrontField;
	private SFFloat minBackField;
	private SFFloat maxBackField;
	private SFFloat intensityField;
	private SFFloat priorityField;
	private SFVec3f directionField;
	private SFVec3f locationField;
	private SFBool spatializeField;
	private SFNode sourceField;

	public SoundNode() {
		setHeaderFlag(false);
		setType(NodeType.SOUND);

		///////////////////////////
		// Exposed Field 
		///////////////////////////

		// minFront exposed field
		minFrontField = new SFFloat(1);
		addExposedField(minFrontExposedFieldName, minFrontField);

		// maxFront exposed field
		maxFrontField = new SFFloat(10);
		addExposedField(maxFrontExposedFieldName, maxFrontField);

		// minBack exposed field
		minBackField = new SFFloat(1);
		addExposedField(minBackExposedFieldName, minBackField);

		// maxBack exposed field
		maxBackField = new SFFloat(10);
		addExposedField(maxBackExposedFieldName, maxBackField);

		// intensity exposed field
		intensityField = new SFFloat(10);
		addExposedField(intensityExposedFieldName, intensityField);

		// priority exposed field
		priorityField = new SFFloat(10);
		addExposedField(priorityExposedFieldName, priorityField);

		// direction exposed field
		directionField = new SFVec3f(0, 0, 1);
		addExposedField(directionExposedFieldName, directionField);

		// location exposed field
		locationField = new SFVec3f(0, 0, 0);
		addExposedField(locationExposedFieldName, locationField);

		// source exposed field
		sourceField = new SFNode();
		addExposedField(sourceExposedFieldName, sourceField);

		///////////////////////////
		// Field 
		///////////////////////////

		// spatialize exposed field
		spatializeField = new SFBool(true);
		addField(spatializeFieldName, spatializeField);
	}

	public SoundNode(SoundNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Source
	////////////////////////////////////////////////

	public SFNode getSourceField() {
		if (isInstanceNode() == false)
			return sourceField;
		return (SFNode)getExposedField(sourceExposedFieldName);
	}
	
	public void updateSourceField() {
		//getSourceField().setValue(getSourceNodes());
	}
	
	////////////////////////////////////////////////
	//	Direction
	////////////////////////////////////////////////

	public SFVec3f getDirectionField() {
		if (isInstanceNode() == false)
			return directionField;
		return (SFVec3f)getExposedField(directionExposedFieldName);
	}

	public void setDirection(float value[]) {
		getDirectionField().setValue(value);
	}
	
	public void setDirection(float x, float y, float z) {
		getDirectionField().setValue(x, y, z);
	}

	public void setDirection(String value) {
		getDirectionField().setValue(value);
	}
	
	public void getDirection(float value[]) {
		getDirectionField().getValue(value);
	}

	////////////////////////////////////////////////
	//	Location
	////////////////////////////////////////////////

	public SFVec3f getLocationField() {
		if (isInstanceNode() == false)
			return locationField;
		return (SFVec3f)getExposedField(locationExposedFieldName);
	}

	public void setLocation(float value[]) {
		getLocationField().setValue(value);
	}

	public void setLocation(float x, float y, float z) {
		getLocationField().setValue(x, y, z);
	}

	public void setLocation(String value) {
		getLocationField().setValue(value);
	}
	
	public void getLocation(float value[]) {
		getLocationField().getValue(value);
	}

	////////////////////////////////////////////////
	//	MinFront
	////////////////////////////////////////////////

	public SFFloat getMinFrontField() {
		if (isInstanceNode() == false)
			return minFrontField;
		return (SFFloat)getExposedField(minFrontExposedFieldName);
	}
	
	public void setMinFront(float value) {
		getMinFrontField().setValue(value);
	}

	public void setMinFront(String value) {
		getMinFrontField().setValue(value);
	}
	
	public float getMinFront() {
		return getMinFrontField().getValue();
	}

	////////////////////////////////////////////////
	//	MaxFront
	////////////////////////////////////////////////
	
	public SFFloat getMaxFrontField() {
		if (isInstanceNode() == false)
			return maxFrontField;
		return (SFFloat)getExposedField(maxFrontExposedFieldName);
	}
	
	public void setMaxFront(float value) {
		getMaxFrontField().setValue(value);
	}

	public void setMaxFront(String value) {
		getMaxFrontField().setValue(value);
	}
	
	public float getMaxFront() {
		return getMaxFrontField().getValue();
	}

	////////////////////////////////////////////////
	//	MinBack
	////////////////////////////////////////////////

	public SFFloat getMinBackField() {
		if (isInstanceNode() == false)
			return minBackField;
		return (SFFloat)getExposedField(minBackExposedFieldName);
	}
	
	public void setMinBack(float value) {
		getMinBackField().setValue(value);
	}

	public void setMinBack(String value) {
		getMinBackField().setValue(value);
	}
	
	public float getMinBack() {
		return getMinBackField().getValue();
	}

	////////////////////////////////////////////////
	//	MaxBack
	////////////////////////////////////////////////

	public SFFloat getMaxBackField() {
		if (isInstanceNode() == false)
			return maxBackField;
		return (SFFloat)getExposedField(maxBackExposedFieldName);
	}
	
	public void setMaxBack(float value) {
		getMaxBackField().setValue(value);
	}

	public void setMaxBack(String value) {
		getMaxBackField().setValue(value);
	}

	public float getMaxBack() {
		return getMaxBackField().getValue();
	}

	////////////////////////////////////////////////
	//	Intensity
	////////////////////////////////////////////////
	
	public SFFloat getIntensityField() {
		if (isInstanceNode() == false)
			return intensityField;
		return (SFFloat)getExposedField(intensityExposedFieldName);
	}
	
	public void setIntensity(float value) {
		getIntensityField().setValue(value);
	}

	public void setIntensity(String value) {
		getIntensityField().setValue(value);
	}
	
	public float getIntensity() {
		return getIntensityField().getValue();
	}

	////////////////////////////////////////////////
	//	Priority
	////////////////////////////////////////////////

	public SFFloat getPriorityField() {
		if (isInstanceNode() == false)
			return priorityField;
		return (SFFloat)getExposedField(priorityExposedFieldName);
	}
	
	public void setPriority(float value) {
		getPriorityField().setValue(value);
	}

	public void setPriority(String value) {
		getPriorityField().setValue(value);
	}
	
	public float getPriority() {
		return getPriorityField().getValue();
	}

	////////////////////////////////////////////////
	//	Spatialize
	////////////////////////////////////////////////

	public SFBool getSpatializeField() {
		if (isInstanceNode() == false)
			return spatializeField;
		return (SFBool)getField(spatializeFieldName);
	}
	
	public void setSpatialize(boolean value) {
		getSpatializeField().setValue(value);
	}

	public void setSpatialize(String value) {
		getSpatializeField().setValue(value);
	}

	public boolean getSpatialize() {
		return getSpatializeField().getValue();
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isAudioClipNode() || node.isMovieTextureNode())
			return true;
		else
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
		SFBool spatialize = getSpatializeField();
		SFVec3f direction = getDirectionField();
		SFVec3f location = getLocationField();

		printStream.println(indentString + "\t" + "direction " + direction );
		printStream.println(indentString + "\t" + "location " + location );
		printStream.println(indentString + "\t" + "maxFront " + getMaxFront() );
		printStream.println(indentString + "\t" + "minFront " + getMinFront() );
		printStream.println(indentString + "\t" + "maxBack " + getMaxBack() );
		printStream.println(indentString + "\t" + "minBack " + getMinBack() );
		printStream.println(indentString + "\t" + "intensity " + getIntensity() );
		printStream.println(indentString + "\t" + "priority " + getPriority() );
		printStream.println(indentString + "\t" + "spatialize " + spatialize );

		AudioClipNode aclip = getAudioClipNodes();
		if (aclip != null) {
			if (aclip.isInstanceNode() == false) {
				String nodeName = aclip.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "source DEF " + aclip.getName() + " AudioClip {");
				else
					printStream.println(indentString + "\t" + "source AudioClip {");
				aclip.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "source USE " + aclip.getName());
		}

		MovieTextureNode mtexture = getMovieTextureNodes();
		if (mtexture != null) {
			if (mtexture.isInstanceNode() == false) {
				String nodeName = mtexture.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "source DEF " + mtexture.getName() + " MovieTexture {");
				else
					printStream.println(indentString + "\t" + "source MovieTexture {");
				mtexture.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "source USE " + mtexture.getName());
		}
	}
}
