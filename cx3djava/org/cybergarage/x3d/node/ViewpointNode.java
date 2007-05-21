/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Viewpoint.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.lang.String;
import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class ViewpointNode extends BindableNode {
	
	private String	positionFieldName			= "position";
	private String	orientationFieldName		= "orientation";
	private String	fieldOfViewFieldName		= "fieldOfView";
	private String	descriptionFieldName		= "description";
	private String	jumpFieldName					= "jump";

	private SFVec3f		positionField;
	private SFRotation	orientationField;
	private SFString		descriptionField;
	private SFFloat		fovField;
	private SFBool		jumpField;

	public ViewpointNode() {
		setHeaderFlag(false);
		setType(NodeType.VIEWPOINT);

		// position exposed field
		positionField = new SFVec3f(0.0f, 0.0f, 0.0f);
		positionField.setName(positionFieldName);
		addExposedField(positionField);

		// orientation exposed field
		orientationField = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		orientationField.setName(orientationFieldName);
		addExposedField(orientationField);

		// description exposed field
		descriptionField = new SFString();
		descriptionField.setName(descriptionFieldName);
		addExposedField(descriptionField);

		// fov exposed field
		fovField = new SFFloat(0.785398f);
		fovField.setName(fieldOfViewFieldName);
		addExposedField(fovField);

		// jump exposed field
		jumpField = new SFBool(true);
		jumpField.setName(jumpFieldName);
		addExposedField(jumpField);
	}

	public ViewpointNode(ViewpointNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Jump
	////////////////////////////////////////////////
	
	public SFBool getJumpField() {
		if (isInstanceNode() == false)
			return jumpField;
		return (SFBool)getExposedField(jumpFieldName);
	}

	public void setJump(boolean value) {
		getJumpField().setValue(value);
	}

	public void setJump(String value) {
		getJumpField().setValue(value);
	}

	public boolean getJump() {
		return getJumpField().getValue();
	}

	////////////////////////////////////////////////
	//	FieldOfView
	////////////////////////////////////////////////

	public SFFloat getFieldOfViewField() {
		if (isInstanceNode() == false)
			return fovField;
		return (SFFloat)getExposedField(fieldOfViewFieldName);
	}
	
	public void setFieldOfView(float value) {
		getFieldOfViewField().setValue(value);
	}

	public void setFieldOfView(String value) {
		getFieldOfViewField().setValue(value);
	}
	
	public float getFieldOfView() {
		return getFieldOfViewField().getValue();
	}

	////////////////////////////////////////////////
	//	Description
	////////////////////////////////////////////////

	public SFString getDescriptionField() {
		if (isInstanceNode() == false)
			return descriptionField;
		return (SFString)getExposedField(descriptionFieldName);
	}
	
	public void setDescription(String value) {
		getDescriptionField().setValue(value);
	}
	
	public String getDescription() {
		return getDescriptionField().getValue();
	}

	////////////////////////////////////////////////
	//	Position
	////////////////////////////////////////////////

	public SFVec3f getPositionField() {
		if (isInstanceNode() == false)
			return positionField;
		return (SFVec3f)getExposedField(positionFieldName);
	}

	public void setPosition(float value[]) {
		getPositionField().setValue(value);
	}
	
	public void setPosition(float x, float y, float z) {
		getPositionField().setValue(x, y, z);
	}
	
	public void setPosition(String value) {
		getPositionField().setValue(value);
	}
	
	public void getPosition(float value[]) {
		getPositionField().getValue(value);
	}
	
	////////////////////////////////////////////////
	//	Orientation
	////////////////////////////////////////////////

	public SFRotation getOrientationField() {
		if (isInstanceNode() == false)
			return orientationField;
		return (SFRotation)getExposedField(orientationFieldName);
	}

	public void setOrientation(float value[]) {
		getOrientationField().setValue(value);
	}
	
	public void setOrientation(float x, float y, float z, float w) {
		getOrientationField().setValue(x, y, z, w);
	}

	public void setOrientation(String value) {
		getOrientationField().setValue(value);
	}
	
	public void getOrientation(float value[]) {
		getOrientationField().getValue(value);
	}
	
	////////////////////////////////////////////////
	//	Add position
	////////////////////////////////////////////////

	public void addPosition(float worldTranslation[]) { 
		getPositionField().add(worldTranslation);
	}

	public void addPosition(float localTranslation[], float frame[][]) { 
		SFVec3f position = getPositionField();
		float	translation[] = new float[3];
		for (int axis=0; axis<3; axis++) {
			SFVec3f vector = new SFVec3f(frame[axis]);
			vector.scale(localTranslation[axis]);
			vector.getValue(translation);
			position.add(translation);
		}
	}

	public void addPosition(float x, float y, float z, float frame[][]) { 
		float localTranslation[] = new float[3];
		localTranslation[0] = x;
		localTranslation[1] = y;
		localTranslation[2] = z;
		addPosition(localTranslation, frame);
	}

	////////////////////////////////////////////////
	//	Add orientation
	////////////////////////////////////////////////

	public void addOrientation(SFRotation rot) {
		getOrientationField().add(rot);
	}

	public void addOrientation(float rotationValue[]) {
		getOrientationField().add(rotationValue);
	}

	public void addOrientation(float x, float y, float z, float rot) {
		getOrientationField().add(x, y, z, rot);
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
		SFBool jump = getJumpField();
		SFString description = getDescriptionField();

		printStream.println(indentString + "\t" + "fieldOfView " + getFieldOfView() );
		printStream.println(indentString + "\t" + "jump " + jump);
		getPosition(vec);			printStream.println(indentString + "\t" + "position " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getOrientation(rot);		printStream.println(indentString + "\t" + "orientation " + rot[X] + " "+ rot[Y] + " " + rot[Z] + " " + rot[W] );
		printStream.println(indentString + "\t" + "description " + description );
	}

	////////////////////////////////////////////////
	//	Local frame
	////////////////////////////////////////////////

	public void getFrame(float frame[][]) {

		SFRotation orientation = getOrientationField();

		// local x frame
		frame[X][X] = 1.0f;
		frame[X][Y] = 0.0f;
		frame[X][Z] = 0.0f;
		orientation.multi(frame[X]);

		// local x frame
		frame[Y][X] = 0.0f;
		frame[Y][Y] = 1.0f;
		frame[Y][Z] = 0.0f;
		orientation.multi(frame[Y]);

		// local x frame
		frame[Z][X] = 0.0f;
		frame[Z][Y] = 0.0f;
		frame[Z][Z] = 1.0f;
		orientation.multi(frame[Z]);
	}

	public float[][] getFrame() {
		float frame[][] = new float[3][3];
		getFrame(frame);
		return frame;
	}

	////////////////////////////////////////////////
	//	Viewpoint Matrix
	////////////////////////////////////////////////

	public void getMatrix(SFMatrix matrix) {
		float	position[] = new float[3];
		float	rotation[] = new float[4];
		
		getPosition(position);
		SFVec3f	transView = new SFVec3f(position);
		transView.invert();

		getOrientation(rotation);
		SFRotation rotView = new SFRotation(rotation);
		rotView.invert();

		SFMatrix	mxTrans = new SFMatrix();
		SFMatrix	mxRot = new SFMatrix();
		mxTrans.setValueAsTranslation(transView);
		mxRot.setValueAsRotation(rotView);

		matrix.init();
		matrix.add(mxRot);
		matrix.add(mxTrans);
	}

	public SFMatrix getMatrix() {
		SFMatrix mx = new SFMatrix();
		getMatrix(mx);
		return mx;
	}

	public void getMatrix(float value[][]) {
		SFMatrix mx = new SFMatrix();
		getMatrix(mx);
		mx.getValue(value);
	}
}
