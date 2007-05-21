/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : IndexedLineSetNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.util.Debug;

public class IndexedLineSetNodeObject extends IndexedLineArray implements NodeObject {
	
	public IndexedLineSetNodeObject(IndexedLineSetNode node) {
		super(getVertexCount(node), getVertexFormat(node), node.getNLines()*2);
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
		initialize(node);
	}

	static public int getVertexCount(IndexedLineSetNode node) {
		return (node.getCoordinateNodes() != null ? node.getCoordinateNodes().getNPoints() : 0);
	}
	
	static public int getVertexFormat(IndexedLineSetNode node) {
		int vertexFormat = COORDINATES;// | NORMALS;
		if (node.getColorNodes() != null)
			vertexFormat |= COLOR_3;
		return vertexFormat;
	}

	private void disableLightInMaterialNode(org.cybergarage.x3d.node.Node node) {
		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					Appearance app = parentShape3DNode.getAppearance();
					if (app != null) {
						Material material = app.getMaterial();
						if (material != null) {
							material.setLightingEnable(false);
						}
					}
				}
			}
		}
	}
	
	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		IndexedLineSetNode idxLineSetNode = (IndexedLineSetNode)node;
		
		MFInt32	index = new MFInt32();

		int nLines					= 0;
		int nCoordinatePoints	= 0;	
		
		int nCoordIndices = idxLineSetNode.getNCoordIndices();

		/**** Coordinate *********************************************/
		CoordinateNode coordNode = idxLineSetNode.getCoordinateNodes();
		if (coordNode != null) {
			float point[] = new float[3];
			nCoordinatePoints = coordNode.getNPoints();
			for (int n=0; n<nCoordinatePoints; n++) {
				coordNode.getPoint(n, point);
				setCoordinate(n, point);
			}
			index.clear();
			nLines = 0;
			for (int n=0; n<nCoordIndices; n++) {
				int id = idxLineSetNode.getCoordIndex(n);
				Debug.message("IndexedLineSetNodeObject::initialize = " + nCoordIndices + ", " + n + ", " + id);
				if (id != -1)
					index.addValue(id);
				if (id == -1 || n == (nCoordIndices-1)) {
					int indexSize = index.getSize();
					for (int i=0; i<(indexSize-1); i++) {
						setCoordinateIndex(nLines, index.get1Value(i)); nLines++;
						setCoordinateIndex(nLines, index.get1Value(i+1)); nLines++;
					}
					index.clear();
				}
			}
		}
		
		/**** Color *********************************************/
		ColorNode colorNode = idxLineSetNode.getColorNodes();
		if (colorNode != null) {
			float color[] = new float[3];
			int nColors = colorNode.getNColors();
			for (int n=0; n<nColors && n<nCoordinatePoints; n++) {
				colorNode.getColor(n, color);
				setColor(n, color);
			}
			index.clear();
			nLines = 0;
			if (idxLineSetNode.isColorPerVertex() == true) {
				int nColorIndices = idxLineSetNode.getNColorIndices();
				boolean hasColorIndices = (nCoordIndices <= nColorIndices) ? true : false;
				if (hasColorIndices == false)
					 nColorIndices = nCoordIndices;
				for (int n=0; n<nColorIndices; n++) {
					int id = 0;
					if (hasColorIndices == true)
						id = idxLineSetNode.getColorIndex(n);
					else
						id = idxLineSetNode.getCoordIndex(n);
						
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nColorIndices-1)) {
						int indexSize = index.getSize();
						for (int i=0; i<(indexSize-1); i++) {
							setColorIndex(nLines++, index.get1Value(i));
							setColorIndex(nLines++, index.get1Value(i+1));
						}
						index.clear();
					}
				}
			}
			else {
				int nColorIndices = idxLineSetNode.getNColorIndices();
				boolean hasColorIndices = (idxLineSetNode.getNLines() <= nColorIndices) ? true : false;
				int lineNum = 0;
				for (int n=0; n<nCoordIndices; n++) {
					int id = idxLineSetNode.getCoordIndex(n);
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nCoordIndices-1)) {
						int indexSize = index.getSize();
						for (int i=0; i<(indexSize-1); i++) {
							int normalIndex = 0;
							if (hasColorIndices)
								normalIndex = idxLineSetNode.getColorIndex(lineNum);
							else
								normalIndex = lineNum;
							setColorIndex(nLines++, normalIndex);
							setColorIndex(nLines++, normalIndex);
						}
						index.clear();
						lineNum++;
					}
				}
			}
		}
		
		disableLightInMaterialNode(node);

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
					disableLightInMaterialNode(node);
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
