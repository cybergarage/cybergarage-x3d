/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFRotation.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFRotation extends Field {

	private RotationValue mValue= new RotationValue();

	public SFRotation() {
		setType(FieldType.SFROTATION);
		setValue(0.0f, 0.0f, 1.0f, 0.0f);
	}

	public SFRotation(SFRotation rotation) {
		setType(FieldType.SFROTATION);
		setValue(rotation);
	}

	public SFRotation(float x, float y, float z, float rot) {
		setType(FieldType.SFROTATION);
		setValue(x, y, z, rot);
	}

	public SFRotation(double x, double y, double z, double rot) {
		setType(FieldType.SFROTATION);
		setValue(x, y, z, rot);
	}

	public SFRotation(float value[]) {
		setType(FieldType.SFROTATION);
		setValue(value);
	}

	public SFRotation(double value[]) {
		setType(FieldType.SFROTATION);
		setValue(value);
	}

	public SFRotation(float vector[], float angle) {
		setType(FieldType.SFROTATION);
		setValue(vector, angle);
	}

	public SFRotation(double vector[], double angle) {
		setType(FieldType.SFROTATION);
		setValue(vector[0], vector[1], vector[2], angle);
	}

	public SFRotation(String value) {
		setType(FieldType.SFROTATION);
		setValue(value);
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (RotationValue)object;
		}
	}

	public Object getObject() {
		Object object;
		synchronized (mValue) {
			object = mValue;
		}
		return object;
	}
	
	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(float x, float y, float z, float rot, boolean doShare) 
	{
		mValue.setValue(x, y, z, rot);
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(float x, float y, float z, float rot) 
	{
		setValue(x, y, z, rot, true);
	}
	
	public void setValue(double x, double y, double z, double rot) {
		setValue((float)x, (float)y, (float)z, (float)rot);
	}

	public void setValue(double value[]) {
		if (value.length != 4)
			return;
		setValue(value[0], value[1], value[2], value[3]);
	}

	public void setValue(float value[]) {
		if (value.length != 4)
			return;
		setValue(value[0], value[1], value[2], value[3]);
	}

	public void setValue(float vector[], float angle) {
		setValue(vector[0], vector[1], vector[2], angle);
	}

	public void setValue(double vector[], float angle) {
		setValue(vector[0], vector[1], vector[2], angle);
	}

	public void setValue(SFRotation rotation, boolean doShare) 
	{
		setValue(rotation.getX(), rotation.getY(), rotation.getZ(), rotation.getAngle(), doShare);
	}

	public void setValue(SFRotation rotation) 
	{
		setValue(rotation, true);
	}

	public void setValue(String string) {
		StringValue value = new StringValue(string);
		String token[] = value.getTokens();
		if (token != null) {
			if (token.length == 4) {
				try {
					Float x = new Float(token[0]);
					Float y = new Float(token[1]);
					Float z = new Float(token[2]);
					Float a = new Float(token[3]);
					setValue(x.floatValue(), y.floatValue(), z.floatValue(), a.floatValue());
				}
				catch (NumberFormatException e) {}
			}
		}
	}

	public void setValue(Field field, boolean doShare) 
	{
		if (field instanceof SFRotation)
			setValue((SFRotation)field, doShare);
	}

	public void setValue(Field field) 
	{
		setValue(field, true);
	}

	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public void getValue(float value[]) {
		mValue.getValue(value);
	}

	public float[] getValue() {
		float value[] = new float[4];
		getValue(value);
		return value;
	}

	public void getVector(float vector[]) {
		mValue.getVector(vector);
	}

	public float[] getVector() {
		float vector[] = new float[3];
		getVector(vector);
		return vector;
	}

	public float getX() {
		return mValue.getX();
	}

	public float getY() {
		return mValue.getY();
	}

	public float getZ() {
		return mValue.getZ();
	}

	public float getAngle() {
		return mValue.getAngle();
	}

	public int getValueCount()
	{
		return 4;
	}

	////////////////////////////////////////////////
	//	add 
	////////////////////////////////////////////////

	public void add(SFRotation rot)
	{
		SFMatrix m1 = new SFMatrix();
		getSFMatrix(m1);

		SFMatrix m2 = new SFMatrix();
		rot.getSFMatrix(m2);
		
		m1.add(m2);

		SFRotation newRotation = new SFRotation();
		m1.getSFRotation(newRotation);

		setValue(newRotation);
	}

	public void add(float rotationValue[]) {
		SFRotation rotation = new SFRotation(rotationValue);
		add(rotation);
	}

	public void add(float x, float y, float z, float rot) {
		SFRotation rotation = new SFRotation(x, y, z, rot);
		add(rotation);
	}

	////////////////////////////////////////////////
	//	multi 
	////////////////////////////////////////////////

	public void multi(float vector[])
	{
		SFMatrix m = new SFMatrix();
		getSFMatrix(m);
		m.multi(vector);
	}

	public void multi(double vector[])
	{
		SFMatrix m = new SFMatrix();
		getSFMatrix(m);
		m.multi(vector);
	}

	public void multi(SFVec3f vector)
	{
		SFMatrix m = new SFMatrix();
		getSFMatrix(m);
		m.multi(vector);
	}

	////////////////////////////////////////////////
	//	convert
	////////////////////////////////////////////////

	public void getSFMatrix(SFMatrix matrix)
	{
		float vector[] = getVector();
		float rotation = getAngle();

		double k = (1.0 - Math.cos(rotation));
		double s = Math.sin(rotation);
		double c = Math.cos(rotation);
		double ax = vector[0];
		double ay = vector[1];
		double az = vector[2];
		double ax2 = ax * ax;
		double ay2 = ay * ay;
		double az2 = az * az;

		double ma[][] = new double[4][4];
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++)
				ma[i][j] = 0.0f;
		}

		ma[0][0] = k*ax2 + c;
		ma[0][1] = k*ax*ay + s*az;
		ma[0][2] = k*ax*az - s*ay;
		ma[1][0] = k*ax*ay - s*az;
		ma[1][1] = k*ay2 + c;
		ma[1][2] = k*ay*az + s*ax;
		ma[2][0] = k*ax*az + s*ay;
		ma[2][1] = k*ay*az - s*ax;
		ma[2][2] = k*az2 + c;
		ma[3][3] = 1.0f;

		matrix.setValue(ma);
	}

	////////////////////////////////////////////////
	//	invert
	////////////////////////////////////////////////

	public void invert() {
		mValue.setAngle(-mValue.getAngle());
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		float vector[] = new float[3];
		getVector(vector);
		return vector[0] + " " + vector[1] + " " + vector[2] + " " + getAngle();
	}
}