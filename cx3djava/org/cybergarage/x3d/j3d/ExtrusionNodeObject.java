/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ExtrusionNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.Debug;
import org.cybergarage.x3d.util.Geometry3D;
import org.cybergarage.x3d.util.BoundingBox;

public class ExtrusionNodeObject extends IndexedTriangleArray implements NodeObject {
	
	public ExtrusionNodeObject(ExtrusionNode exNode) {
		super(exNode.getVertexCount(), getVertexFormat(exNode), exNode.getNTriangleCoordIndices());
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
		initialize(exNode);
	}

	static public int getVertexFormat(ExtrusionNode exNode) {
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
		float	coord[] = new float[3];
		getCoordinate(nCoord, coord);
		Debug.message("\t\t[" + n + "] = " + nCoord + " (" + coord[0] + ", " + coord[1] + ", " + coord[2] + ")");
		setCoordinateIndex(n, nCoord);
	}
	
	private void transformPoint(float point[], float scale[], float scp[][], float orientation[], float spine[]) {
		Geometry3D.scale(point, scale[0], 1.0f, scale[1]);
		if (0.0f < Geometry3D.length(scp[0]) && 0.0f < Geometry3D.length(scp[1]) && 0.0f < Geometry3D.length(scp[2])) 
			Geometry3D.rotate(point, scp);
		Geometry3D.add(point, spine);
		Geometry3D.rotate(point, orientation);
	}
	
	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		Debug.message("ExtrusionNodeObject::initialize");
		
		ExtrusionNode exNode = (ExtrusionNode)node;

		exNode.addDefaultParameters();

		int	i, j;
		int	nTriangle;	
		
		/**** Coordinate *********************************************/
		Debug.message("\tinitialize Coordinate ..... ");
		Debug.message("\t\tTotal Coordinates = " + exNode.getVertexCount());
		int nCrossSections	= exNode.getNCrossSections();
		int nOrientations		= exNode.getNOrientations();
		int nScales				= exNode.getNScales();
		int nSpines				= exNode.getNSpines();

		boolean	bClosed;
		float		spineStart[]	= new float[3];
		float		spineEnd[]		= new float[3];
		
		exNode.getSpine(0,			spineStart);
		exNode.getSpine(nSpines-1, spineEnd);
		bClosed = Geometry3D.equals(spineStart, spineEnd);
	
		float scale[]			= new float[2];
		float orientation[]	= new float[4];
		float	spine[]			= new float[3];
		float	spine0[]			= new float[3];
		float	spine1[]			= new float[3];
		float	spine2[]			= new float[3];
		float	scp[][]			= new float[3][3];
		float	vector1[]		= new float[3];
		float vector2[]		= new float[3];
		float csPoint[]		= new float[2];
		float point[]	 		= new float[3];

		BoundingBox	bbox		= new BoundingBox();
						 	
		for (i=0; i<nSpines; i++) {
		
			if (nScales == 1)
				exNode.getScale(0, scale);
			else  if (i < nScales) 
				exNode.getScale(i, scale);
			else {
				scale[0] = 1.0f; 
				scale[1] = 1.0f;
			}

			if (nOrientations == 1)
				exNode.getOrientation(0, orientation);
			else if (i < nOrientations)
				exNode.getOrientation(i, orientation);
			else {
				orientation[0] = 0.0f; 
				orientation[1] = 0.0f; 
				orientation[2] = 1.0f; 
				orientation[3] = 0.0f;
			}

			// SCP Y
			if (nSpines <= 2) {
				exNode.getSpine(1, spine1);
				exNode.getSpine(0, spine2);
			}
			if (bClosed && (i == 0 || i == (nSpines-1))) {
				exNode.getSpine(1,			spine1);
				exNode.getSpine(nSpines-2,	spine2);
			}
			else if (i == 0) {
				exNode.getSpine(1, spine1);
				exNode.getSpine(0, spine2);
			}
			else if (i == (nSpines-1)) {
				exNode.getSpine(nSpines-1, spine1);
				exNode.getSpine(nSpines-2, spine2);
			}
			else {
				exNode.getSpine(i+1, spine1);
				exNode.getSpine(i-1, spine2);
			}
			Geometry3D.sub(spine1, spine2, scp[1]);
			Geometry3D.normalize(scp[1]);
			
			// SCP Z
			if (nSpines <= 2) {
				exNode.getSpine(0, spine0);
				exNode.getSpine(1, spine1);
				exNode.getSpine(1, spine2);
			}
			else if (bClosed && (i == 0 || i == (nSpines-1))) {
				exNode.getSpine(0,				spine0);
				exNode.getSpine(1,				spine1);
				exNode.getSpine(nSpines-2,	spine2);
			}
			else if (i == 0) {
				exNode.getSpine(1,	spine0);
				exNode.getSpine(2,	spine1);
				exNode.getSpine(0,	spine2);
			}
			else if (i == (nSpines-1)) {
				exNode.getSpine(nSpines-2, spine1);
				exNode.getSpine(nSpines-1, spine1);
				exNode.getSpine(nSpines-3, spine2);
			}
			else {
				exNode.getSpine(i,	spine0);
				exNode.getSpine(i+1,	spine1);
				exNode.getSpine(i-1,	spine2);
			}
			Geometry3D.sub(spine1, spine0, vector1);
			Geometry3D.sub(spine2, spine0, vector2);
			Geometry3D.getCross(vector1, vector2, scp[2]);

			// SCP X
			Geometry3D.getCross(scp[1], scp[2], scp[0]);

			Debug.message("scp[" + i + "] = " + scp[0][0] + " " + scp[0][1] + " " + scp[0][2] + ", "
			                                  + scp[1][0] + " " + scp[1][1] + " " + scp[1][2] + ", "  
			                                  + scp[2][0] + " " + scp[2][1] + " " + scp[2][2]);
			
			exNode.getSpine(i, spine);
			for (j=0; j<nCrossSections; j++) {
				exNode.getCrossSection(j, csPoint);
				point[0] = csPoint[0];
				point[1] = 0.0f;
				point[2] = csPoint[1];
				transformPoint(point, scale, scp, orientation, spine);
				setCoordinate((i*nCrossSections + j), point);
				Debug.message("\t\tpoint["+ (i*nCrossSections + j) + "] = " + point[0] + ", " + point[1] + ", " + point[2]);
				bbox.addPoint(point);
			}
		}
		
		// Update BoundingBox
		exNode.setBoundingBoxCenter(bbox.getCenter());
		exNode.setBoundingBoxSize(bbox.getSize());
		
		nTriangle = 0;
		for (i=0; i<(nSpines-1); i++) {
			for (j=0; j<(nCrossSections-1); j++) {
				setCoordinateIndexforDebug(nTriangle*3+0, (i+1)*nCrossSections + (j+0));
				setCoordinateIndexforDebug(nTriangle*3+1, (i+0)*nCrossSections + (j+0));
				setCoordinateIndexforDebug(nTriangle*3+2, (i+1)*nCrossSections + (j+1));
				nTriangle++;
				
				setCoordinateIndexforDebug(nTriangle*3+0, (i+1)*nCrossSections + (j+1));
				setCoordinateIndexforDebug(nTriangle*3+1, (i+0)*nCrossSections + (j+0));
				setCoordinateIndexforDebug(nTriangle*3+2, (i+0)*nCrossSections + (j+1));
				nTriangle++;
			}
		}
		
		/**** Normal *********************************************/
		Debug.message("\tinitialize Normal ..... ");
		float	coord[][]	= new float[3][3];
		float normal[]		= new float[3];
		for (i=0; i<nSpines; i++) {
			for (j=0; j<nCrossSections; j++) {
				if (i < (nSpines-1)) {
					if (j < (nCrossSections-1)) {
						getCoordinate((i+1)*nCrossSections + (j+0), coord[0]);
						getCoordinate((i+1)*nCrossSections + (j+1), coord[1]);
						getCoordinate((i+0)*nCrossSections + (j+0), coord[2]);
					}
					else if (j < (nCrossSections-1)) {
						getCoordinate((i+1)*nCrossSections + (j-1), coord[0]);
						getCoordinate((i+0)*nCrossSections + (j+0), coord[1]);
						getCoordinate((i+0)*nCrossSections + (j-1), coord[2]);
					}
				}
				else {
					if (j < (nCrossSections-1)) {
						getCoordinate((i-1)*nCrossSections + (j+0), coord[0]);
						getCoordinate((i+0)*nCrossSections + (j+0), coord[1]);
						getCoordinate((i+0)*nCrossSections + (j+1), coord[2]);
					}
					else if (j < (nCrossSections-1)) {
						getCoordinate((i-1)*nCrossSections + (j-1), coord[0]);
						getCoordinate((i+0)*nCrossSections + (j+0), coord[1]);
						getCoordinate((i-1)*nCrossSections + (j+0), coord[2]);
					}
				}
				Geometry3D.getNormalVector(coord, normal);
				setNormal((i*nCrossSections + j), normal);
			}
		}

		nTriangle = 0;
		for (i=0; i<(nSpines-1); i++) {
			for (j=0; j<(nCrossSections-1); j++) {
				setNormalIndex(nTriangle*3+0, (i+1)*nCrossSections + (j+0));
				setNormalIndex(nTriangle*3+1, (i+0)*nCrossSections + (j+0));
				setNormalIndex(nTriangle*3+2, (i+1)*nCrossSections + (j+1));
				nTriangle++;
				
				setNormalIndex(nTriangle*3+0, (i+1)*nCrossSections + (j+1));
				setNormalIndex(nTriangle*3+1, (i+0)*nCrossSections + (j+0));
				setNormalIndex(nTriangle*3+2, (i+0)*nCrossSections + (j+1));
				nTriangle++;
			}
		}

		/**** Color *********************************************/
		Debug.message("\tinitialize Color ..... ");
		float	color[]	= {1.0f, 1.0f, 1.0f};
		for (i=0; i<nSpines; i++) {
			for (j=0; j<nCrossSections; j++)
				setColor((i*nCrossSections + j), color);
		}

		nTriangle = 0;
		for (i=0; i<(nSpines-1); i++) {
			for (j=0; j<(nCrossSections-1); j++) {
				setColorIndex(nTriangle*3+0, (i+1)*nCrossSections + (j+0));
				setColorIndex(nTriangle*3+1, (i+0)*nCrossSections + (j+0));
				setColorIndex(nTriangle*3+2, (i+1)*nCrossSections + (j+1));
				nTriangle++;
				
				setColorIndex(nTriangle*3+0, (i+1)*nCrossSections + (j+1));
				setColorIndex(nTriangle*3+1, (i+0)*nCrossSections + (j+0));
				setColorIndex(nTriangle*3+2, (i+0)*nCrossSections + (j+1));
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
