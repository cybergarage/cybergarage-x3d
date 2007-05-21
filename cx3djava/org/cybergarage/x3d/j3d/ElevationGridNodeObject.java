/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ElevationGridNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.Debug;
import org.cybergarage.x3d.util.Geometry3D;

public class ElevationGridNodeObject extends IndexedTriangleArray implements NodeObject {
	
	public ElevationGridNodeObject(ElevationGridNode elevGridNode) {
		super(elevGridNode.getVertexCount(), getVertexFormat(elevGridNode), elevGridNode.getNTriangleCoordIndices());
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
		setCapability(ALLOW_COLOR_INDEX_READ);
		setCapability(ALLOW_COLOR_INDEX_WRITE);
		setCapability(ALLOW_COORDINATE_INDEX_READ);
		setCapability(ALLOW_COORDINATE_INDEX_WRITE);
		setCapability(ALLOW_NORMAL_INDEX_READ);
		setCapability(ALLOW_NORMAL_INDEX_WRITE);
		setCapability(ALLOW_TEXCOORD_INDEX_READ);
		setCapability(ALLOW_TEXCOORD_INDEX_WRITE);
		setCapability(ALLOW_INTERSECT);
		initialize(elevGridNode);
	}

	static public int getVertexFormat(ElevationGridNode elevGridNode) {
		return (COORDINATES | NORMALS | COLOR_3 | TEXTURE_COORDINATE_2);
	}

	public void getNormalVector(int coordIndex1, int coordIndex2, int coordIndex3, float normal[]) {
		float vertex[][]	= new float[3][3];
		getCoordinate(coordIndex1, vertex[0]); 
		getCoordinate(coordIndex2, vertex[1]); 
		getCoordinate(coordIndex3, vertex[2]); 
		Geometry3D.getNormalVector(vertex, normal);				
	}

	public void getNormalVector(float normal[][], int nNormal, float resultVector[]) {
		Geometry3D.initialize(resultVector);
		for (int n=0; n<nNormal; n++) {
			Geometry3D.add(resultVector, normal[n]);
			Geometry3D.normalize(resultVector);
		}
	}
	
	public void setCoordinateIndexforDebug(int n, int nCoord) {
		Debug.message("\t\t[" + n + "] = " + nCoord);
		setCoordinateIndex(n, nCoord);
	}
	
	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		Debug.message("ElevationGridNodeObject::initialize");
		
		ElevationGridNode elevGridNode = (ElevationGridNode)node;
		
		int	x, z;
		int	nPoint;
		int	nTriangle;	
		
		int	xDimension			= elevGridNode.getXDimension();
		int	zDimension			= elevGridNode.getZDimension();
		int	nDimensionPoints	= xDimension * zDimension;

		float xSpacing = elevGridNode.getXSpacing();
		float zSpacing = elevGridNode.getZSpacing();

		/**** Coordinate *********************************************/
		Debug.message("\tinitialize coordinate points ..... ");
		float	point[] = new float[3];
		nPoint = 0;
		for (z=0; z<zDimension; z++) {
			for (x=0; x<xDimension; x++) {
				point[0] = xSpacing * x;
				point[1] = elevGridNode.getHeight(x + z*xDimension);
				point[2] = zSpacing * z;
				setCoordinate(nPoint, point);
				Debug.message("\t\tpoint["+ nPoint + "] = " + point[0] + ", " + point[1] + ", " + point[2]);
				nPoint++;
			}
		}
		
		Debug.message("\tinitialize coordinate index ..... ");
		nTriangle = 0;	
		for (z=0; z<zDimension-1; z++) {
			for (x=0; x<xDimension-1; x++) {
				setCoordinateIndexforDebug(nTriangle*3+0, (x+0) + ((z+0)*xDimension));
				setCoordinateIndexforDebug(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
				setCoordinateIndexforDebug(nTriangle*3+2, (x+1) + ((z+0)*xDimension));
				nTriangle++;
				
				setCoordinateIndexforDebug(nTriangle*3+0, (x+1) + ((z+0)*xDimension));
				setCoordinateIndexforDebug(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
				setCoordinateIndexforDebug(nTriangle*3+2, (x+1) + ((z+1)*xDimension));
				nTriangle++;
			}
		}
		/**** Color *********************************************/
		Debug.message("\tinitialize Color ..... ");
		ColorNode colorNode = elevGridNode.getColorNodes();
		if (colorNode != null) {
			float color[] = new float[3];
			int nColors = colorNode.getNColors();
			for (int n=0; n<nColors; n++) {
				colorNode.getColor(n, color);
				setColor(n, color);
			}
			
			boolean bNormalPerVertex = elevGridNode.isColorPerVertex();
			nTriangle = 0;	
			for (z=0; z<zDimension; z++) {
				for (x=0; x<xDimension; x++) {
					if (bNormalPerVertex == true) {
						setColorIndex(nTriangle*3+0, (x+0) + ((z+0)*xDimension));
						setColorIndex(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
						setColorIndex(nTriangle*3+2, (x+1) + ((z+0)*xDimension));
						nTriangle++;
				
						setColorIndex(nTriangle*3+0, (x+1) + ((z+0)*xDimension));
						setColorIndex(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
						setColorIndex(nTriangle*3+2, (x+1) + ((z+1)*xDimension));
						nTriangle++;
					}
					else {
						setColorIndex(nTriangle*3+0, x + (z*xDimension));
						setColorIndex(nTriangle*3+1, x + (z*xDimension));
						setColorIndex(nTriangle*3+2, x + (z*xDimension));
						nTriangle++;
				
						setColorIndex(nTriangle*3+0, x + (z*xDimension));
						setColorIndex(nTriangle*3+1, x + (z*xDimension));
						setColorIndex(nTriangle*3+2, x + (z*xDimension));
						nTriangle++;
					}
				}
			}
		}
		else {
			Color3f color = new Color3f(1.0f, 1.0f, 1.0f);
			nPoint = 0;
			for (z=0; z<zDimension; z++) {
				for (x=0; x<xDimension; x++) {
					setColor(nPoint, color);
					nPoint++;
				}
			}
		
			nTriangle = 0;	
			for (z=0; z<zDimension-1; z++) {
				for (x=0; x<xDimension-1; x++) {
					setColorIndex(nTriangle*3+0, (x+0) + ((z+0)*xDimension));
					setColorIndex(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
					setColorIndex(nTriangle*3+2, (x+1) + ((z+0)*xDimension));
					nTriangle++;
				
					setColorIndex(nTriangle*3+0, (x+1) + ((z+0)*xDimension));
					setColorIndex(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
					setColorIndex(nTriangle*3+2, (x+1) + ((z+1)*xDimension));
					nTriangle++;
				}
			}
		}

		/**** Normal *********************************************/
		Debug.message("\tinitialize Normal ..... ");
		NormalNode normalNode = elevGridNode.getNormalNodes();
		if (normalNode != null) {
			float normal[] = new float[3];
			int nNormals = normalNode.getNVectors();
			for (int n=0; n<nNormals; n++) {
				normalNode.getVector(n, normal);
				setNormal(n, normal);
			}
			
			boolean bNormalPerVertex = elevGridNode.isNormalPerVertex();
			nTriangle = 0;	
			for (z=0; z<zDimension; z++) {
				for (x=0; x<xDimension; x++) {
					if (bNormalPerVertex == true) {
						setNormalIndex(nTriangle*3+0, (x+0) + ((z+0)*xDimension));
						setNormalIndex(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
						setNormalIndex(nTriangle*3+2, (x+1) + ((z+0)*xDimension));
						nTriangle++;
				
						setNormalIndex(nTriangle*3+0, (x+1) + ((z+0)*xDimension));
						setNormalIndex(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
						setNormalIndex(nTriangle*3+2, (x+1) + ((z+1)*xDimension));
						nTriangle++;
					}
					else {
						setNormalIndex(nTriangle*3+0, x + (z*xDimension));
						setNormalIndex(nTriangle*3+1, x + (z*xDimension));
						setNormalIndex(nTriangle*3+2, x + (z*xDimension));
						nTriangle++;
				
						setNormalIndex(nTriangle*3+0, x + (z*xDimension));
						setNormalIndex(nTriangle*3+1, x + (z*xDimension));
						setNormalIndex(nTriangle*3+2, x + (z*xDimension));
						nTriangle++;
					}
				}
			}
		}
		else {
			float normal[][] = new float[4][3];
			float coordNormal[]	= new float[3];
			nPoint = 0;
			for (z=0; z<zDimension; z++) {
				for (x=0; x<xDimension; x++) {
					if (x == 0) {
						if (z == 0) { 
							getNormalVector(nPoint, (nPoint + 1), (nPoint + xDimension), coordNormal);
						}
						else if (z == (zDimension-1)) {
							getNormalVector((nPoint - xDimension), (nPoint + 1), nPoint, coordNormal);
						}
						else {
							getNormalVector((nPoint - xDimension), (nPoint + 1), nPoint, normal[0]);
							getNormalVector(nPoint, (nPoint + 1), (nPoint + xDimension), normal[1]);
							getNormalVector(normal, 2, coordNormal);
						}
					}
					else if (x == (xDimension-1)) {
						if (z == 0) {
							getNormalVector((nPoint - 1), nPoint, (nPoint + xDimension), coordNormal);
						}
						else if (z == (zDimension-1)) {
							getNormalVector((nPoint - xDimension), nPoint, (nPoint - 1), coordNormal);
						}
						else {
							getNormalVector((nPoint - xDimension), nPoint, (nPoint - 1), normal[0]);
							getNormalVector((nPoint - 1), nPoint, (nPoint + xDimension), normal[1]);
							getNormalVector(normal, 2, coordNormal);
						}
					}
					else {
						if (z == 0) {
							getNormalVector((nPoint - 1), nPoint, (nPoint + xDimension), normal[0]);
							getNormalVector(nPoint, (nPoint + 1), (nPoint + xDimension), normal[1]);
							getNormalVector(normal, 2, coordNormal);
						}
						else if (z == (zDimension-1)) {
							getNormalVector((nPoint - xDimension), nPoint, (nPoint - 1), normal[0]);
							getNormalVector((nPoint - xDimension), (nPoint + 1), nPoint, normal[1]);
							getNormalVector(normal, 2, coordNormal);
						}
						else {
							getNormalVector((nPoint - xDimension), nPoint, (nPoint - 1), normal[0]);
							getNormalVector((nPoint - xDimension), (nPoint + 1), nPoint, normal[1]);
							getNormalVector((nPoint - 1), nPoint, (nPoint + xDimension), normal[2]);
							getNormalVector(nPoint, (nPoint + 1), (nPoint + xDimension), normal[3]);
							getNormalVector(normal, 4, coordNormal);
						}
					}
					setNormal(nPoint, coordNormal);
					Debug.message("\t\tnormal["+ x + "][" + z + "] = " + coordNormal[0] + ", " + coordNormal[1] + ", " + coordNormal[2]);
					nPoint++;
				}
			}
			
			nTriangle = 0;	
			for (z=0; z<zDimension-1; z++) {
				for (x=0; x<xDimension-1; x++) {
					setNormalIndex(nTriangle*3+0, (x+0) + ((z+0)*xDimension));
					setNormalIndex(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
					setNormalIndex(nTriangle*3+2, (x+1) + ((z+0)*xDimension));
					nTriangle++;
				
					setNormalIndex(nTriangle*3+0, (x+1) + ((z+0)*xDimension));
					setNormalIndex(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
					setNormalIndex(nTriangle*3+2, (x+1) + ((z+1)*xDimension));
					nTriangle++;
				}
			}
		}
		

		/**** TextureCoordinate *********************************************/
		Debug.message("\tinitialize TextureCoordinate ..... ");
		TextureCoordinateNode texCoordNode = elevGridNode.getTextureCoordinateNodes();
		float texCoord[] = new float[2];
		if (texCoordNode != null) {
			int nTexCoordPoints = texCoordNode.getNPoints();
			for (int n=0; n<nTexCoordPoints; n++) {
				texCoordNode.getPoint(n, texCoord);
				setTextureCoordinate(n, texCoord);
			}
		}
		else {
			int nTexCoord = 0;
			for (z=0; z<zDimension; z++) {
				for (x=0; x<xDimension; x++) {
					texCoord[0] = (float)x / (float)(xDimension-1);
					texCoord[1] = (float)z / (float)(zDimension-1);
					setTextureCoordinate(nTexCoord, texCoord);
					nTexCoord++;
				}
			}
		}
		
		nTriangle = 0;	
		for (z=0; z<zDimension-1; z++) {
			for (x=0; x<xDimension-1; x++) {
				setTextureCoordinateIndex(nTriangle*3+0, (x+0) + ((z+0)*xDimension));
				setTextureCoordinateIndex(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
				setTextureCoordinateIndex(nTriangle*3+2, (x+1) + ((z+0)*xDimension));
				nTriangle++;
				
				setTextureCoordinateIndex(nTriangle*3+0, (x+1) + ((z+0)*xDimension));
				setTextureCoordinateIndex(nTriangle*3+1, (x+0) + ((z+1)*xDimension));
				setTextureCoordinateIndex(nTriangle*3+2, (x+1) + ((z+1)*xDimension));
				nTriangle++;
			}
		}
				
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
