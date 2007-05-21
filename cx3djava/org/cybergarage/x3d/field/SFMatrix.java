/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFMatrix.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFMatrix extends Field {

	private double mValue[][] = new double[4][4]; 

	public SFMatrix() {
		init();
	}

	public SFMatrix(float value[][]) {
		setValue(value);
	}

	public SFMatrix(double value[][]) {
		setValue(value);
	}

	public SFMatrix(SFMatrix value) {
		setValue(value);
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (double[][])object;
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

	public void setValue(double value[][]) {
		synchronized (mValue) {
			for (int i=0; i<4; i++) {
				for (int j=0; j<4; j++)
					mValue[i][j] = value[i][j];
			}
		}
	}
	
	public void setValue(float value[][]) {
		synchronized (mValue) {
			for (int i=0; i<4; i++) {
				for (int j=0; j<4; j++)
					mValue[i][j] = value[i][j];
			}
		}
	}

	public void setValue(SFMatrix matrix) {
		double value[][] = new double[4][4];
		matrix.getValue(value);
		setValue(value);
	}

	public void setValue(String string) {
	}

	public void setValue(Field field) {
		setValue((SFMatrix)field);
	}
	
	////////////////////////////////////////////////
	//	set as scaling value 
	////////////////////////////////////////////////

	public void setValueAsScaling(float value[]) {
		init();
		setValueAsScaling(value[0], value[1], value[2]);
	}

	public void setValueAsScaling(double value[]) {
		init();
		setValueAsScaling(value[0], value[1], value[2]);
	}

	public void setValueAsScaling(double x, double y, double z) {
		init();
		synchronized (mValue) {
			mValue[0][0] = x;
			mValue[1][1] = y;
			mValue[2][2] = z;
		}
	}

	////////////////////////////////////////////////
	//	set as translation value 
	////////////////////////////////////////////////

	public void setValueAsTranslation(SFVec3f vector) {
		setValueAsTranslation(vector.getX(), vector.getY(), vector.getZ());
	}

	public void setValueAsTranslation(double value[]) {
		setValueAsTranslation(value[0], value[1], value[2]);
	}

	public void setValueAsTranslation(float value[]) {
		setValueAsTranslation(value[0], value[1], value[2]);
	}

	public void setValueAsTranslation(double x, double y, double z) {
		init();
		synchronized (mValue) {
			mValue[3][0] = x;
			mValue[3][1] = y;
			mValue[3][2] = z;
		}
	}

	////////////////////////////////////////////////
	//	set as rotation value 
	////////////////////////////////////////////////

	public void setValueAsRotation(SFRotation rotation) {
		setValueAsRotation(rotation.getX(), rotation.getY(), rotation.getZ(), rotation.getAngle());
	}

	public void setValueAsRotation(float value[]) {
		setValueAsRotation(value[0], value[1], value[2], value[3]);
	}

	public void setValueAsRotation(double value[]) {
		setValueAsRotation(value[0], value[1], value[2], value[3]);
	}

	public void setValueAsRotation(double x, double y, double z, double rot) {
		SFRotation rotation = new SFRotation(x, y, z, rot);
		SFMatrix matrix = new SFMatrix();
		rotation.getSFMatrix(matrix);
		double value[][] = new double[4][4];
		matrix.getValue(value); 
		setValue(value);
	}

	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public double[][] getValue() {
		double value[][] = new double[4][4];
		getValue(value);
		return value;
	}

	public void getValue(double value[][]) {
		synchronized (mValue) {
			for (int i=0; i<4; i++) {
				for (int j=0; j<4; j++)
					value[i][j] = mValue[i][j];
			}
		}
	}

	public void getValue(float value[][]) {
		synchronized (mValue) {
			for (int i=0; i<4; i++) {
				for (int j=0; j<4; j++)
					value[i][j] = (float)mValue[i][j];
			}
		}
	}

	public int getValueCount()
	{
		return 16;
	}

	////////////////////////////////////////////////
	//	get value only translation
	////////////////////////////////////////////////

	public double[] getValueOnlyTranslation() {
		double value[] = new double[3];
		getValueOnlyTranslation(value);
		return value;
	}

	public void getValueOnlyTranslation(float value[]) {
		synchronized (mValue) {
			value[0] = (float)mValue[3][0];
			value[1] = (float)mValue[3][1];
			value[2] = (float)mValue[3][2];
		}
	}

	public void getValueOnlyTranslation(double value[]) {
		synchronized (mValue) {
			value[0] = mValue[3][0];
			value[1] = mValue[3][1];
			value[2] = mValue[3][2];
		}
	}

	////////////////////////////////////////////////
	//	add 
	////////////////////////////////////////////////

	public void add(SFMatrix matrix)
	{
		double ma1[][] = new double[4][4];
		double ma2[][] = new double[4][4];
		double maOut[][] = new double[4][4]; 

		getValue(ma1); 
		matrix.getValue(ma2); 
		
		for (int i = 0; i<4; i++) {
			for (int j = 0; j<4; j++) {
				maOut[j][i] = 0.0f;
				for (int k = 0; k<4; k++)
					maOut[j][i] += ma1[k][i]*ma2[j][k];
			}
		}
		
		setValue(maOut);
	}

	////////////////////////////////////////////////
	//	multi 
	////////////////////////////////////////////////

	public void multi(double vector[])
	{
		double vectorIn[] = new double[4];

		vectorIn[0] = vector[0];
		vectorIn[1] = vector[1];
		vectorIn[2] = vector[2];
		vectorIn[3] = 1.0f;

		double vectorOut[] = new double[4];

		double ma[][] = getValue(); 
		for (int n = 0; n<4; n++)
			vectorOut[n] = ma[0][n]*vectorIn[0] + ma[1][n]*vectorIn[1] + ma[2][n]*vectorIn[2] + ma[3][n]*vectorIn[3];

		vector[0] = vectorOut[0];
		vector[1] = vectorOut[1];
		vector[2] = vectorOut[2];
	}

	public void multi(float vector[])
	{
		double dvector[] = new double[3];
		dvector[0] = vector[0];
		dvector[1] = vector[1];
		dvector[2] = vector[2];
		multi(dvector);
		vector[0] = (float)dvector[0];
		vector[1] = (float)dvector[1];
		vector[2] = (float)dvector[2];
	}
	
	public void multi(SFVec3f vector)
	{
		float value[] = new float[3];
		vector.getValue(value);
		multi(value);
		vector.setValue(value);
	}

	////////////////////////////////////////////////
	//	convert
	////////////////////////////////////////////////

	public void getSFRotation(SFRotation rotation)
	{
		double x, y, z, w;

		double m[][] = new double[4][4];
		getValue(m);

		double w2 = 1.0f/4.0f*(1.0f + m[0][0] + m[1][1] + m[2][2]);
		if (0.0f < w2) {
			w = Math.sqrt(w2);
			x = (m[1][2] - m[2][1])/(4.0f*w);
			y = (m[2][0] - m[0][2])/(4.0f*w);
			z = (m[0][1] - m[1][0])/(4.0f*w);
		}
		else {
			w = 0.0f;
			double x2 = 0.0f;

			double m1122 = m[1][1] + m[2][2];

			if (m1122 != 0.0f)
				x2 = -1.0f/(2.0f*m1122);
				
			if (0.0f < x2) {
				x = Math.sqrt(x2);
				y = m[0][1] / (2.0f*x);
				z = m[0][2] / (2.0f*x);
			}
			else {
				x = 0.0f;
				double y2 = 0.0f;
				double m22 = 1.0f - m[2][2];
				if (m22 != 0.0f)
					y2 = 1.0f / (2.0f*m22);
				if (0.0f < y2) {
					y = Math.sqrt(y2);
					z = m[1][2] / (2.0f*y);
				}
				else {
					y = 0.0f;
					z = 1.0f;
				}
			}
		}

		double angle = Math.acos(2.0f*w*w - 1.0f);

		double value[] = new double[4];
		if (angle != 0.0) {
			value[0] = x / Math.sin(angle);
			value[1] = y / Math.sin(angle);
			value[2] = z / Math.sin(angle);
			value[3] = angle;
		}
		else {
			value[0] = 0.0;
			value[1] = 0.0;
			value[2] = 1.0;
			value[3] = 0.0;
		}

		rotation.setValue(value);
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		String string;
		synchronized (mValue) {
			string =	mValue[0][0] + " " + mValue[0][1] + " " + mValue[0][2] + " " + mValue[0][3] + "\n" +
						mValue[1][0] + " " + mValue[1][1] + " " + mValue[1][2] + " " + mValue[1][3] + "\n" +
						mValue[2][0] + " " + mValue[2][1] + " " + mValue[2][2] + " " + mValue[2][3] + "\n" +
						mValue[3][0] + " " + mValue[3][1] + " " + mValue[3][2] + " " + mValue[3][3];
		}
		return string;
	}

	////////////////////////////////////////////////
	//	other
	////////////////////////////////////////////////

	public void init()
	{
		synchronized (mValue) {
			for (int i=0; i<4; i++) {
				for (int j=0; j<4; j++)
					mValue[i][j] = 0.0;
			}
			for (int n=0; n<4; n++)
				mValue[n][n] = 1.0;
		}
	}
}