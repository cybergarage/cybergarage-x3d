/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : IndexedFaseSetNodeObject.java
*
*	Revision;
*
*	12/11/03
*		- Thuan Truong <tqthuan@tma.com.vn>
*		- Fixed initialize() to set correct TextureCoordinates.
*
******************************************************************/

package org.cybergarage.x3d.android;

import javax.media.j3d.*;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.util.Debug;

public class IndexedFaceSetNodeObject GeometryNodeObject {
	
	public IndexedFaceSetNodeObject(IndexedFaceSetNode node) {
		super(getVertexCount(node), getVertexFormat(node), node.getNTriangleCoordIndices());
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
		initialize(node);
	}

	static public int getVertexCount(IndexedFaceSetNode node) {
		int count = 0;
		
		if (node.getCoordinateNodes() != null)
			count = node.getCoordinateNodes().getNPoints();
		
		if (node.getNormalNodes() != null) {
			int normalCount = node.getNormalNodes().getNVectors();
			if (count < normalCount)
				count = normalCount;
		}
		else {
			int nTriangle = node.getNTriangleCoordIndices() / 3;
			if (count < nTriangle)
				count = nTriangle;
		}
		
		if (node.getColorNodes() != null) {
			int colorCount = node.getColorNodes().getNColors();
			if (count < colorCount)
				count = colorCount;
		}
		
		Debug.message("\tgetVertexCount() = " + count);
		return count;
	}
	
	static public int getVertexFormat(IndexedFaceSetNode node) {
		int vertexFormat = COORDINATES | NORMALS;
		if (node.getColorNodes() != null)
			vertexFormat |= COLOR_3;
		if (node.getTextureCoordinateNodes() != null)
			vertexFormat |= TEXTURE_COORDINATE_2;
		return vertexFormat;
	}
	
	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		IndexedFaceSetNode idxFaceSetNode = (IndexedFaceSetNode)node;
		
		MFInt32	index = new MFInt32();

		int		nTriangle			= 0;
		int		nCoordinatePoints	= 0;	
		
		int nCoordIndices = idxFaceSetNode.getNCoordIndices();
		
		Debug.message("\tnCoordIndices = " + nCoordIndices);

		/**** Coordinate *********************************************/
		CoordinateNode coordNode = idxFaceSetNode.getCoordinateNodes();
		if (coordNode != null) {// && 0 < coordNode.getNPoints()) {
			nCoordinatePoints = coordNode.getNPoints();
			float point[] = new float[3];
			for (int n=0; n<nCoordinatePoints; n++) {
				coordNode.getPoint(n, point);
				setCoordinate(n, point);
			}
			index.clear();
			nTriangle = 0;
			for (int n=0; n<nCoordIndices; n++) {
				int id = idxFaceSetNode.getCoordIndex(n);
				if (id != -1)
					index.addValue(id);
				if (id == -1 || n == (nCoordIndices-1)) {
					int indexSize = index.getSize();
					for (int i=0; i<(indexSize-2); i++) {
						setCoordinateIndex(nTriangle*3,		index.get1Value(0));
						setCoordinateIndex(nTriangle*3+1,	index.get1Value(i+1));
						setCoordinateIndex(nTriangle*3+2,	index.get1Value(i+2));
						nTriangle++;
					}
					index.clear();
				}
			}
		}
		
		/**** Color *********************************************/
		ColorNode colorNode = idxFaceSetNode.getColorNodes();
		Debug.message("\tcolorNode = " + colorNode);
		if (colorNode != null) {// && 0 < colorNode.getNColors()) {
			float color[] = new float[3];
			int nColors = colorNode.getNColors();
			for (int n=0; n<nColors; n++) {
				colorNode.getColor(n, color);
				setColor(n, color);
			}
			index.clear();
			nTriangle = 0;
			boolean isColorPerVertex = idxFaceSetNode.isColorPerVertex();
			Debug.message("\tisColorPerVertex = " + isColorPerVertex);
			if (isColorPerVertex == true) {
				int nColorIndices = idxFaceSetNode.getNColorIndices();
				Debug.message("\tnColorIndices = " + nColorIndices);
				boolean hasColorIndices = (nCoordIndices <= nColorIndices) ? true : false;
				if (hasColorIndices == false)
					 nColorIndices = nCoordIndices;
				for (int n=0; n<nColorIndices; n++) {
					int id = 0;
					if (hasColorIndices == true)
						id = idxFaceSetNode.getColorIndex(n);
					else
						id = idxFaceSetNode.getCoordIndex(n);
						
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nColorIndices-1)) {
						int indexSize = index.getSize();
						for (int i=0; i<(indexSize-2); i++) {
							setColorIndex(nTriangle*3,		index.get1Value(0));
							setColorIndex(nTriangle*3+1,	index.get1Value(i+1));
							setColorIndex(nTriangle*3+2,	index.get1Value(i+2));
							nTriangle++;
						}
						index.clear();
					}
				}
			}
			else {
				int nColorIndices = idxFaceSetNode.getNColorIndices();
				boolean hasColorIndices = (idxFaceSetNode.getNPolygons() <= nColorIndices) ? true : false;
				int nPolygon = 0;
				for (int n=0; n<nCoordIndices; n++) {
					int id = idxFaceSetNode.getCoordIndex(n);
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nCoordIndices-1)) {
						int indexSize = index.getSize();
						for (int i=0; i<(indexSize-2); i++) {
							int colorlIndex = 0;
							if (hasColorIndices)
								colorlIndex = idxFaceSetNode.getColorIndex(nPolygon);
							else
								colorlIndex = nPolygon;
							setColorIndex(nTriangle*3,		colorlIndex);
							setColorIndex(nTriangle*3+1,	colorlIndex);
							setColorIndex(nTriangle*3+2,	colorlIndex);
							nTriangle++;
						}
						index.clear();
						nPolygon++;
					}
				}
			}
		}
	
		/**** Normal *********************************************/
		NormalNode normalNode = idxFaceSetNode.getNormalNodes();
		Debug.message("\tnormalNode = " + normalNode);
		if (normalNode != null) {// && 0 < normalNode.getNVectors()) {
			float vector[] = new float[3];
			int nVectors = normalNode.getNVectors();
			for (int n=0; n<nVectors; n++) {
				normalNode.getVector(n, vector);
				setNormal(n, vector);
			}
			index.clear();
			nTriangle = 0;
			boolean isNormalPerVertex = idxFaceSetNode.isNormalPerVertex();
			Debug.message("\tisNormalPerVertex = " + isNormalPerVertex);
			if (isNormalPerVertex == true) {
				int nNormalIndices = idxFaceSetNode.getNNormalIndices();
				boolean hasNormalIndices = (nCoordIndices <= nNormalIndices) ? true : false;
				Debug.message("\thasNormalIndices = " + hasNormalIndices);
				if (hasNormalIndices == false)
					 nNormalIndices = nCoordIndices;
				Debug.message("\tnNormalIndices = " + nNormalIndices);
				for (int n=0; n<nNormalIndices; n++) {
					int id = 0;
					if (hasNormalIndices == true)
						id = idxFaceSetNode.getNormalIndex(n);
					else
						id = idxFaceSetNode.getCoordIndex(n);
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nNormalIndices-1)) {
						int indexSize = index.getSize();
						for (int i=0; i<(indexSize-2); i++) {
							setNormalIndex(nTriangle*3,	index.get1Value(0));
							setNormalIndex(nTriangle*3+1,	index.get1Value(i+1));
							setNormalIndex(nTriangle*3+2,	index.get1Value(i+2));
							nTriangle++;
						}	
						index.clear();
					}
				}
			}
			else {
				int nNormalIndices = idxFaceSetNode.getNNormalIndices();
				boolean hasNormalIndices = (idxFaceSetNode.getNPolygons() <= nNormalIndices) ? true : false;
				int nPolygon = 0;
				for (int n=0; n<nCoordIndices; n++) {
					int id = idxFaceSetNode.getCoordIndex(n);
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nCoordIndices-1)) {
						for (int i=0; i<(index.getSize()-2); i++) {
							int normalIndex = 0;
							if (hasNormalIndices)
								normalIndex = idxFaceSetNode.getNormalIndex(nPolygon);
							else
								normalIndex = nPolygon;
							setNormalIndex(nTriangle*3,		normalIndex);
							setNormalIndex(nTriangle*3+1,	normalIndex);
							setNormalIndex(nTriangle*3+2,	normalIndex);
							nTriangle++;
						}
						index.clear();
						nPolygon++;
					}
				}
			}
		}
		else { // normalNode == null
			int nPoly = 0;
			int nPoint = 0;
			float point[][] = new float[3][3];
			float vector[] = new float[3];
			for (int n=0; n<nCoordIndices; n++) {
				int id = idxFaceSetNode.getCoordIndex(n);
				if (id != -1) {
					if (nPoint < 3) 
						coordNode.getPoint(id, point[nPoint]);
					nPoint++;
				}
				if (id == -1 || n == (nCoordIndices-1)) {
					Geometry3D.getNormalVector(point, vector);
					setNormal(nPoly, vector);
					nPoint = 0;
					nPoly++;
				}
			}

			nTriangle = 0;
			nPoly = 0;
			for (int n=0; n<nCoordIndices; n++) {
				int id = idxFaceSetNode.getCoordIndex(n);
				if (id != -1) 
					nPoint++;
				if (id == -1 || n == (nCoordIndices-1)) {
					int nTrianglePoly = nPoint - 2;
					for (int i=0; i<nTrianglePoly; i++) {
						setNormalIndex(nTriangle*3,		nPoly);
						setNormalIndex(nTriangle*3+1,	nPoly);
						setNormalIndex(nTriangle*3+2,	nPoly);
						nTriangle++;
					}
					nPoint = 0;
					nPoly++;
				}
			}
			nTriangle = 0;
		}
		
		/**** TexCoord *********************************************/
		TextureCoordinateNode texCoordNode = idxFaceSetNode.getTextureCoordinateNodes();
		if (texCoordNode != null) {// && 0 < texCoordNode.getNPoints()) {
			float point[] = new float[2];
			int nTexCoordPoints = texCoordNode.getNPoints();
			for (int n=0; n<nTexCoordPoints && n<nCoordinatePoints; n++) {
				texCoordNode.getPoint(n, point);
				setTextureCoordinate(n, point);
			}
			index.clear();
			nTriangle = 0;
			int nTexCoordIndices = idxFaceSetNode.getNTexCoordIndices();
			boolean hasTexCoordIndices = (nCoordIndices <= nTexCoordIndices) ? true : false;
			if (hasTexCoordIndices == false)
				 nTexCoordIndices = nCoordIndices;
			for (int n=0; n<nTexCoordIndices; n++) {
				int id = 0;
				if (hasTexCoordIndices == true)
					id = idxFaceSetNode.getTexCoordIndex(n);
				else
					id = idxFaceSetNode.getCoordIndex(n);
				if (id != -1)
					index.addValue(id);
				if (id == -1 || n == (nTexCoordIndices-1)) {
					int indexSize = index.getSize();
					for (int i=0; i<(indexSize-2); i++) {
						setTextureCoordinateIndex(nTriangle*3,		index.get1Value(0));
						setTextureCoordinateIndex(nTriangle*3+1,	index.get1Value(i+1));
						setTextureCoordinateIndex(nTriangle*3+2,	index.get1Value(i+2));
						nTriangle++;
					}	
					index.clear();
				}
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
