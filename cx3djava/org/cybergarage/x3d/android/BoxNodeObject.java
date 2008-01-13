/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : BoxNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.android;

import javax.microedition.khronos.opengles.GL10;

import org.cybergarage.x3d.node.*;

public class BoxNodeObject extends GeometryNodeObject {

	////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////
	
	public BoxNodeObject() 
	{
	}

	////////////////////////////////////////////////
	//	Methods
	////////////////////////////////////////////////

	public void draw(GL10 gl, Node node)
	{
		BoxNode box = (BoxNode)node;
	    drawBox(
	    		gl,
	    		-box.getX()/2.0f, 
	    		box.getX()/2.0f, 
	    		-box.getY()/2.0f, 
	    		box.getY()/2.0f, 
	    		-box.getZ()/2.0f, 
	    		box.getZ()/2.0f);
	}
	
	private void drawBox(GL10 gl, float x0, float x1, float y0, float y1, float z0, float z1)
	{
	    float n[][] = {
				{0.0f, 0.0f, 1.0f}, {0.0f, -1.0f, 0.0f}, {0.0f, 0.0f, 1.0f},
				{0.0f, 1.0f, 0.0f}, {1.0f, 0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f}};

	    int faces[][] = {
				{ 3, 2, 1, 0 }, { 7, 6, 2, 3 }, { 4, 5, 6, 7 },
				{ 0, 1, 5, 4 }, { 1, 2, 6, 5 }, { 3, 0, 4, 7 }};

	    float t[][] = {
				{ 0.0f, 1.0f }, { 1.0f, 1.0f },
				{ 1.0f, 0.0f }, { 0.0f, 0.0f } };

	    float	v[][] = new float[8][3];

		v[0][0] = v[3][0] = v[4][0] = v[7][0] = x0;
		v[1][0] = v[2][0] = v[5][0] = v[6][0] = x1;
		v[2][1] = v[3][1] = v[6][1] = v[7][1] = y0;
		v[0][1] = v[1][1] = v[4][1] = v[5][1] = y1;
		v[4][2] = v[5][2] = v[6][2] = v[7][2] = z0;
		v[0][2] = v[1][2] = v[2][2] = v[3][2] = z1;

		gl.glFrontFace(GL10.GL_CCW);

	    for (int i = 0; i < 6; i++) {
	    	gl.glNormal3f(n[i][0], n[i][1], n[i][2]);
	    	//gl.glTexCoord2fv(t[0]);
	    	gl.glVertex3f(v[faces[i][0]]);
	    	//gl.glTexCoord2fv(t[1]);
	    	gl.glVertex3f(v[faces[i][1]]);
	    	//gl.glTexCoord2fv(t[2]);
	    	gl.glVertex3f(v[faces[i][2]]);
	    	//gl.glTexCoord2fv(t[3]);
	    	gl.glVertex3f(v[faces[i][3]]);
	    }
	}
	
}