/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : BoxNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import org.cybergarage.x3d.node.*;

public class BoxNodeObject extends QuadArray implements NodeObject {

	public BoxNodeObject(BoxNode node) {
		super(24, COORDINATES | NORMALS | TEXTURE_COORDINATE_2);

		setCapability(ALLOW_COORDINATE_READ);
		setCapability(ALLOW_COORDINATE_WRITE);
		setCapability(ALLOW_COLOR_READ);
		setCapability(ALLOW_COLOR_WRITE);
		setCapability(ALLOW_NORMAL_READ);
		setCapability(ALLOW_NORMAL_WRITE);
		setCapability(ALLOW_TEXCOORD_READ);
		setCapability(ALLOW_TEXCOORD_WRITE);
		setCapability(ALLOW_COUNT_READ);
		setCapability(ALLOW_COUNT_WRITE);
		setCapability(ALLOW_FORMAT_READ);
		setCapability(ALLOW_INTERSECT);
	
		float xsize = node.getX();
		float ysize = node.getY();
		float zsize = node.getZ();
	
		float xmin = -xsize/2.0f;
		float xmax =  xsize/2.0f;
		float ymin = -ysize/2.0f;
		float ymax =  ysize/2.0f;
		float zmin = -zsize/2.0f;
		float zmax =  zsize/2.0f;

		createBox(xmin, xmax, ymin, ymax, zmin, zmax);
	}
	
	//	   4+--------+5
	//	   /|       /|
	//	  / |      / |
	//	0+--------+1 |
	//	 |  |     |  |
	//	 | 7+-----|--+6
	//	 | /      | /
	//	 |/       |/
	//	3+--------+2

	private void createBox(float x0, float x1, float y0, float y1, float z0, float z1) {

		float n[][] = {
			{0.0f, 0.0f, 1.0f}, {0.0f, -1.0f, 0.0f}, {0.0f, 0.0f, 1.0f},
			{0.0f, 1.0f, 0.0f}, {1.0f, 0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f}};

		int faces[][] = {
			{ 3, 2, 1, 0 }, { 2, 3, 7, 6 }, { 6, 7, 4, 5 },
			{ 0, 1, 5, 4 }, { 2, 6, 5, 1 }, { 7, 3, 0, 4 }};

		float t[][] = {
			{ 0.0f, 0.0f }, { 1.0f, 0.0f },
			{ 1.0f, 1.0f }, { 0.0f, 1.0f } };

		float v[][] = new float[8][3];

		v[0][0] = v[3][0] = v[4][0] = v[7][0] = x0;
		v[1][0] = v[2][0] = v[5][0] = v[6][0] = x1;
		v[2][1] = v[3][1] = v[6][1] = v[7][1] = y0;
		v[0][1] = v[1][1] = v[4][1] = v[5][1] = y1;
		v[4][2] = v[5][2] = v[6][2] = v[7][2] = z0;
		v[0][2] = v[1][2] = v[2][2] = v[3][2] = z1;

		Point3f verts[]		= new Point3f[24];
		Vector3f norms[]	= new Vector3f[24];
		Point2f texCoords[]	= new Point2f[24];
		
	    for (int i = 0; i < 6; i++) {
			for (int j=0; j<4; j++) {
				norms[(i*4)+j] = new Vector3f(n[i]);
				verts[(i*4)+j] = new Point3f(v[faces[i][j]]);
				texCoords[(i*4)+j] = new Point2f(t[j]);
			}
		}

		setCoordinates(0, verts);
		setNormals(0, norms);
		setTextureCoordinates(0, texCoords);
	}

	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		return true;
	}
	
	public boolean uninitialize(org.cybergarage.x3d.node.Node node) {
		return true;
	}
			
	public boolean update(org.cybergarage.x3d.node.Node node) {
		return true;
	}
	
	public boolean add(org.cybergarage.x3d.node.Node node) {

		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setGeometry(this);
				}
			}
		}
		
		return true;
	}

	public boolean remove(org.cybergarage.x3d.node.Node node) {
	
		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setGeometry(new NullGeometryObject());
				}
			}
		}
		
		return true;
	}
}
