/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Geometry3D.java
*
******************************************************************/

package org.cybergarage.x3d.util;

import org.cybergarage.x3d.field.SFRotation;

public class Geometry3D {

	static public void initialize(float vector[]) {
		vector[0] = 0.0f;
		vector[1] = 0.0f;
		vector[2] = 0.0f;
	}
	
	static public void normalize(float vector[]) {
		float mag = (float)Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1] + vector[2]*vector[2]);
		if (mag != 0.0f) {
			vector[0] /= mag;
			vector[1] /= mag;
			vector[2] /= mag;
		}
		else {
			vector[0] = 0.0f;
			vector[1] = 0.0f;
			vector[2] = 1.0f;
		}
	}

	static public void scale(float vector[], float factor) {
		vector[0] *= factor;
		vector[1] *= factor;
		vector[2] *= factor;
	}

	static public void scale(float vector[], float factor[]) {
		vector[0] *= factor[0];
		vector[1] *= factor[1];
		vector[2] *= factor[2];
	}

	static public void scale(float vector[], float factorx, float factory, float factorz) {
		vector[0] *= factorx;
		vector[1] *= factory;
		vector[2] *= factorz;
	}

	static public float length(float vector[]) {
		return (float)Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
	}

	static public float distance(float vector1[], float vector2[]) {
		float x = vector1[0] - vector2[0];
		float y = vector1[1] - vector2[1];
		float z = vector1[2] - vector2[2];
		return (float)Math.sqrt(x*x + y*y + z*z);
	}

	static public void inverse(float vector[]) {
		vector[0] = -vector[0];
		vector[1] = -vector[1];
		vector[2] = -vector[2];
	}

	static public void add(float vector1[], float vector2[]) {
		vector1[0] += vector2[0];
		vector1[1] += vector2[1];
		vector1[2] += vector2[2];
	}

	static public void add(float vector1[], float vector2[], float resultVector[]) {
		resultVector[0] = vector1[0] + vector2[0];
		resultVector[1] = vector1[1] + vector2[1];
		resultVector[2] = vector1[2] + vector2[2];
	}

	static public void sub(float vector1[], float vector2[], float resultVector[]) {
		resultVector[0] = vector1[0] - vector2[0];
		resultVector[1] = vector1[1] - vector2[1];
		resultVector[2] = vector1[2] - vector2[2];
	}

	static public void sub(float vector1[], float vector2[]) {
		vector1[0] -= vector2[0];
		vector1[1] -= vector2[1];
		vector1[2] -= vector2[2];
	}

	static public void rotate(float point[], float frame[][]) {
		float x = point[0]*frame[0][0]+point[1]*frame[1][0]+point[2]*frame[2][0];
		float y = point[0]*frame[0][1]+point[1]*frame[1][1]+point[2]*frame[2][1];
		float z = point[0]*frame[0][2]+point[1]*frame[1][2]+point[2]*frame[2][2];
		point[0] = x;
		point[1] = y;
		point[2] = z;
	}

	static public void rotate(float point[], float x, float y, float z, float angle) {
		SFRotation rotation = new SFRotation(x, y, z, angle);
		rotation.multi(point);
	}

	static public void rotate(float point[], float orientation[]) {
		SFRotation rotation = new SFRotation(orientation);
		rotation.multi(point);
	}

	static public void getVector(float point1[], float point2[], float resultVector[]) {
		sub(point1, point2, resultVector);
		normalize(resultVector);
	}
	
	static public float[] getVector(float point1[], float point2[]) {
		float resultVector[] = new float[3];
		getVector(point1, point2, resultVector);
		return resultVector;
	}

	static public void getCross(float vector1[], float vector2[], float resultVector[]) {
		resultVector[0] = vector1[1]*vector2[2] - vector1[2]*vector2[1];
		resultVector[1] = vector1[2]*vector2[0] - vector1[0]*vector2[2];
		resultVector[2] = vector1[0]*vector2[1] - vector1[1]*vector2[0];
		normalize(resultVector);
	}
	
	static public float[] getCross(float vector1[], float vector2[]) {
		float resultVector[] = new float[3];
		getCross(vector1, vector2, resultVector);
		return resultVector;
	}

	static public void getNormalVector(float point[][], float resultVector[]) {
		float vector1[] = new float[3];
		float vector2[] = new float[3];
		for (int n=0; n<3; n++) {
			vector1[n] = point[2][n] - point[1][n];
			vector2[n] = point[0][n] - point[1][n];
		}	
		getCross(vector1, vector2, resultVector);
	}
	
	static public float[] getNormalVector(float point[][]) {
		float resultVector[] = new float[3];
		getNormalVector(point, resultVector);
		return resultVector;
	}

	static public float getDot(float vector1[], float vector2[]) {
		return vector1[0]*vector2[0] + vector1[1]*vector2[1] + vector1[2]*vector2[2];
	}

	static public float getAngle(float vector1[], float vector2[]) {
		float angle;
		angle = getDot(vector1, vector2) / (length(vector1) * length(vector2));
		angle = (float)Math.acos(angle);
		return angle;
	}

	static public boolean equals(float vector1[], float vector2[]) {
		if (vector1[0] == vector2[0] && vector1[1] == vector2[1] && vector1[2] == vector2[2])
			return true;
		else
			return false;
	}
}
