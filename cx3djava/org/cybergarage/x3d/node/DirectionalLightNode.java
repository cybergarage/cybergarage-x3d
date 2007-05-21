/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : DirectionalLightNode.java
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

public class DirectionalLightNode extends LightNode {
	
	private final static String directionFieldName = "direction";

	private SFVec3f directionField;

	public DirectionalLightNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.DIRLIGHT);

		// direction exposed field
		directionField = new SFVec3f(0.0f, 0.0f, -1.0f);
		directionField.setName(directionFieldName);
		addExposedField(directionField);
	}

	public DirectionalLightNode(DirectionalLightNode node) 
	{
		this();
		setFieldValues(node);
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
		float rot[] = new float[4];

		SFBool bon = getOnField();
		printStream.println(indentString + "\t" + "on " + bon );

		printStream.println(indentString + "\t" + "intensity " + getIntensity() );
		printStream.println(indentString + "\t" + "ambientIntensity " + getAmbientIntensity() );
		getColor(vec);			printStream.println(indentString + "\t" + "color " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getDirection(vec);		printStream.println(indentString + "\t" + "direction " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
	}
}
