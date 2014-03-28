/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : SpotLightNode.java
*
*	Revisions:
*
*	12/05/02
*		- Removed a ambientIntensity field.
*		- Removed getDiffuseColor(), getAmbientColor()
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class SpotLightNode extends LightNode {
	
	private String	locationFieldName			= "location";
	private String	directionFieldName			= "direction";
	private String	radiusFieldName				= "radius";
	private String	attenuationFieldName		= "attenuation";
	private String	beamWidthFieldName			= "beamWidth";
	private String	cutOffAngleFieldName		= "cutOffAngle";

	private SFVec3f locationField;
	private SFVec3f directionField;
	private SFFloat radiusField;
	private SFVec3f attenuationField;
	private SFFloat beamWidthField;
	private SFFloat cutOffAngleField;

	public SpotLightNode() {
		setHeaderFlag(false);
		setType(NodeType.SPOTLIGHT);

		// location exposed field
		locationField = new SFVec3f(0.0f, 0.0f, 0.0f);
		locationField.setName(locationFieldName);
		addExposedField(locationField);

		// direction exposed field
		directionField = new SFVec3f(0.0f, 0.0f, -1.0f);
		directionField.setName(directionFieldName);
		addExposedField(directionField);

		// radius exposed field
		radiusField = new SFFloat(100.0f);
		radiusField.setName(radiusFieldName);
		addExposedField(radiusField);

		// attenuation exposed field
		attenuationField = new SFVec3f(1.0f, 0.0f, 0.0f);
		attenuationField.setName(attenuationFieldName);
		addExposedField(attenuationField);

		// beamWidth exposed field
		beamWidthField = new SFFloat(1.570796f);
		beamWidthField.setName(beamWidthFieldName);
		addExposedField(beamWidthField);

		// cutOffAngle exposed field
		cutOffAngleField = new SFFloat(0.785398f);
		cutOffAngleField.setName(cutOffAngleFieldName);
		addExposedField(cutOffAngleField);
	}

	public SpotLightNode(SpotLightNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Location
	////////////////////////////////////////////////

	public SFVec3f getLocationField() {
		if (isInstanceNode() == false)
			return locationField;
		return (SFVec3f)getExposedField(locationFieldName);
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
	//	Direction
	////////////////////////////////////////////////

	public SFVec3f getDirectionField() {
		if (isInstanceNode() == false)
			return directionField;
		return (SFVec3f)getExposedField(directionFieldName);
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
	//	Radius
	////////////////////////////////////////////////

	public SFFloat getRadiusField() {
		if (isInstanceNode() == false)
			return radiusField;
		return (SFFloat)getExposedField(radiusFieldName);
	}
	
	public void setRadius(float value) {
		getRadiusField().setValue(value);
	}

	public void setRadius(String value) {
		getRadiusField().setValue(value);
	}

	public float getRadius() {
		return getRadiusField().getValue();
	}

	////////////////////////////////////////////////
	//	Attenuation
	////////////////////////////////////////////////

	public SFVec3f getAttenuationField() {
		if (isInstanceNode() == false)
			return attenuationField;
		return (SFVec3f)getExposedField(attenuationFieldName);
	}

	public void setAttenuation(float value[]) {
		getAttenuationField().setValue(value);
	}
	
	public void setAttenuation(float x, float y, float z) {
		getAttenuationField().setValue(x, y, z);
	}

	public void setAttenuation(String value) {
		getAttenuationField().setValue(value);
	}
	
	public void getAttenuation(float value[]) {
		getAttenuationField().getValue(value);
	}

	////////////////////////////////////////////////
	//	BeamWidth
	////////////////////////////////////////////////

	public SFFloat getBeamWidthField() {
		if (isInstanceNode() == false)
			return beamWidthField;
		return (SFFloat)getExposedField(beamWidthFieldName);
	}
	
	public void setBeamWidth(float value) {
		getBeamWidthField().setValue(value);
	}

	public void setBeamWidth(String value) {
		getBeamWidthField().setValue(value);
	}
	
	public float getBeamWidth() {
		return getBeamWidthField().getValue();
	}


	////////////////////////////////////////////////
	//	CutOffAngle
	////////////////////////////////////////////////

	public SFFloat getCutOffAngleField() {
		if (isInstanceNode() == false)
			return cutOffAngleField;
		return (SFFloat)getExposedField(cutOffAngleFieldName);
	}
	
	public void setCutOffAngle(float value) {
		getCutOffAngleField().setValue(value);
	}

	public void setCutOffAngle(String value) {
		getCutOffAngleField().setValue(value);
	}
	
	public float getCutOffAngle() {
		return getCutOffAngleField().getValue();
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
		SFBool bon = getOnField();
		float vec[] = new float[3];
		float rot[] = new float[4];

		printStream.println(indentString + "\t" + "on " + bon );
		printStream.println(indentString + "\t" + "intensity " + getIntensity() );
		printStream.println(indentString + "\t" + "ambientIntensity " + getAmbientIntensity() );
		getColor(vec);			printStream.println(indentString + "\t" + "color " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getDirection(vec);		printStream.println(indentString + "\t" + "direction " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getLocation(vec);		printStream.println(indentString + "\t" + "location " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		printStream.println(indentString + "\t" + "beamWidth " + getBeamWidth() );
		printStream.println(indentString + "\t" + "cutOffAngle " + getCutOffAngle() );
		printStream.println(indentString + "\t" + "radius " + getRadius() );
		getAttenuation(vec);	printStream.println(indentString + "\t" + "attenuation " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
	}
}
