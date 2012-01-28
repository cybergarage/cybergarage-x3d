/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : PointLightNode.java
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

public class PointLightNode extends LightNode {

	private String	locationFieldName				= "location";
	private String	radiusFieldName				= "radius";
	private String	attenuationFieldName			= "attenuation";

	private SFVec3f locationField;
	private SFFloat radiusField;
	private SFVec3f attenuationField;
	
	public PointLightNode() {
		setHeaderFlag(false);
		setType(NodeType.POINTLIGHT);

		// location exposed field
		locationField = new SFVec3f(0.0f, 0.0f, 0.0f);
		locationField.setName(locationFieldName);
		addExposedField(locationField);

		// radius exposed field
		radiusField = new SFFloat(100.0f);
		radiusField.setName(radiusFieldName);
		addExposedField(radiusField);

		// attenuation exposed field
		attenuationField = new SFVec3f(1.0f, 0.0f, 0.0f);
		attenuationField.setName(attenuationFieldName);
		addExposedField(attenuationField);
	}

	public PointLightNode(PointLightNode node) {
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
		float vec[] = new float[3];

		SFBool bon = getOnField();
		printStream.println(indentString + "\t" + "on " + bon );

		printStream.println(indentString + "\t" + "intensity " + getIntensity() );
		printStream.println(indentString + "\t" + "ambientIntensity " + getAmbientIntensity() );
		getColor(vec);			printStream.println(indentString + "\t" + "color " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getLocation(vec);		printStream.println(indentString + "\t" + "location " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		printStream.println(indentString + "\t" + "radius " + getRadius() );
		getAttenuation(vec);	printStream.println(indentString + "\t" + "attenuation " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
	}
}
