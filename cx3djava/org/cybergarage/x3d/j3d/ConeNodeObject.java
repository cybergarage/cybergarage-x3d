/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConeNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import org.cybergarage.x3d.node.*;

public class ConeNodeObject extends TriangleArray implements NodeObject {

	public ConeNodeObject(ConeNode node) {
		super(getVertexCount(node), COORDINATES | NORMALS | TEXTURE_COORDINATE_2);
		
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
		
		createCone(node);
	}
	
	static private int getNDivide() {
		return 20;
	}
	
	static private int getVertexCount(ConeNode cone) {
		int count = 0;
		if (cone.getSide())
			count += getNDivide();
		if (cone.getBottom())
			count += getNDivide();
		
		count *= 3;
		
		return count;
	}
	
	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		return true;
	}

	private void createCone(ConeNode cone) {
		int offset = 0;
		if (cone.getSide()) {
			createSide(cone, getNDivide(), offset);
			offset += getNDivide()*3;
		}
		if (cone.getBottom())
			createBottom(cone, getNDivide(), offset);
	}
		
	private void createSide(ConeNode cone, int nDivide, int offset) {
		Point3f verts[]		= new Point3f[nDivide*3];
		Vector3f norms[]	= new Vector3f[nDivide*3];
		Point2f texCoords[]	= new Point2f[nDivide*3];
		
		float	height = cone.getHeight();
		float	radius = cone.getBottomRadius();
		
		for (int n=0; n<nDivide; n++) {
			float endAngle		= ((float)Math.PI*2.0f/(float)nDivide) * (float)n;
			float startAngle	= ((float)Math.PI*2.0f/(float)nDivide) * (float)(n+1);
			float midAngle		= (startAngle + endAngle) / 2.0f;
			
			float sx = (float)Math.cos(startAngle);
			float sz = (float)Math.sin(startAngle);
			float ex = (float)Math.cos(endAngle);
			float ez = (float)Math.sin(endAngle);
			float mx = (float)Math.cos(midAngle);
			float mz = (float)Math.sin(midAngle);

			verts[n*3+0] = new Point3f(0.0f, height/2.0f, 0.0f);
			verts[n*3+1] = new Point3f(sx*radius, -height/2.0f, sz*radius);
			verts[n*3+2] = new Point3f(ex*radius, -height/2.0f, ez*radius);

			norms[n*3+0] = new Vector3f(mx, 0.0f, mz); norms[n*3+0].normalize();
			norms[n*3+1] = new Vector3f(sx, 0.0f, sz); norms[n*3+1].normalize();
			norms[n*3+2] = new Vector3f(ex, 0.0f, ez); norms[n*3+2].normalize();
			
			texCoords[n*3+0] = new Point2f((float)(n+(n+1))/2.0f /(float)nDivide, 1.0f);
			texCoords[n*3+1] = new Point2f((float)(n+1)          /(float)nDivide, 0.0f);
			texCoords[n*3+2] = new Point2f((float)n              /(float)nDivide, 0.0f);
		}
		
		setCoordinates(offset, verts);
		setNormals(offset, norms);
		setTextureCoordinates(offset, texCoords);
	}

	private float cosValueToTexCoordX(float cosValue) {
		return (cosValue+1.0f)/2.0f;
	}

	private float sinValueToTexCoordY(float sinValue) {
		return 0.5f - sinValue/2.0f;
	}					   
		
	private void createBottom(ConeNode cone, int nDivide, int offset) {
		Point3f verts[]		= new Point3f[nDivide*3];
		Vector3f norms[]	= new Vector3f[nDivide*3];
		Point2f texCoords[]	= new Point2f[nDivide*3];
		
		float	height = cone.getHeight();
		float	radius = cone.getBottomRadius();
		
		for (int n=0; n<nDivide; n++) {
			float startAngle	= ((float)Math.PI*2.0f/(float)nDivide) * (float)n;
			float endAngle		= ((float)Math.PI*2.0f/(float)nDivide) * (float)(n+1);
			
			float sx = (float)Math.cos(startAngle);
			float sz = (float)Math.sin(startAngle);
			float ex = (float)Math.cos(endAngle);
			float ez = (float)Math.sin(endAngle);
			
			verts[n*3+0] = new Point3f(0.0f, -height/2.0f, 0.0f);
			verts[n*3+1] = new Point3f(sx*radius, -height/2.0f, sz*radius);
			verts[n*3+2] = new Point3f(ex*radius, -height/2.0f, ez*radius);

			norms[n*3+0] = new Vector3f(0.0f, -1.0f, 0.0f); 
			norms[n*3+1] = new Vector3f(0.0f, -1.0f, 0.0f);
			norms[n*3+2] = new Vector3f(0.0f, -1.0f, 0.0f);
			
			texCoords[n*3+0] = new Point2f(0.5f, 0.5f);
			texCoords[n*3+1] = new Point2f(cosValueToTexCoordX(sx), sinValueToTexCoordY(sz));
			texCoords[n*3+2] = new Point2f(cosValueToTexCoordX(ex), sinValueToTexCoordY(ez));
		}
		
		setCoordinates(offset, verts);
		setNormals(offset, norms);
		setTextureCoordinates(offset, texCoords);
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
