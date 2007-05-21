/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: ComposedGeometryNode.java
*
*	Revisions:
*
*	11/25/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.field.*;

public abstract class ComposedGeometryNode extends Geometry3DNode 
{
	//// Field ////////////////
	private final static String ccwFieldName					= "ccw";
	private final static String colorPerVertexFieldName		= "colorPerVertex";
	private final static String normalPerVertexFieldName		= "normalPerVertex";
	private final static String solidFieldName					= "solid";

	//// ExposedField ////////////////
	private final static String colorExposedFieldName			= "color";
	private final static String coordExposedFieldName		= "coord";
	private final static String normalExposedFieldName		= "normal";
	private final static String texCoordExposedFieldName	= "texCoord";

	private SFBool ccwField;
	private SFBool colorPerVertexField;
	private SFBool normalPerVertexField;
	private SFBool solidField;

	private SFNode colorField;
	private SFNode coordField;
	private SFNode normalField;
	private SFNode texCoordField;
		
	public ComposedGeometryNode() 
	{
		///////////////////////////
		// Field 
		///////////////////////////

		// ccw  field
		ccwField = new SFBool(true);
		ccwField.setName(ccwFieldName);
		addField(ccwField);

		// colorPerVertex  field
		colorPerVertexField = new SFBool(true);
		colorPerVertexField.setName(colorPerVertexFieldName);
		addField(colorPerVertexField);

		// normalPerVertex  field
		normalPerVertexField = new SFBool(true);
		normalPerVertexField.setName(normalPerVertexFieldName);
		addField(normalPerVertexField);

		// solid  field
		solidField = new SFBool(true);
		solidField.setName(solidFieldName);
		addField(solidField);

		///////////////////////////
		// ExposedField 
		///////////////////////////

		// color field
		colorField = new SFNode();
		addExposedField(colorExposedFieldName, colorField);

		// coord field
		coordField = new SFNode();
		addExposedField(coordExposedFieldName, coordField);

		// normal field
		normalField = new SFNode();
		addExposedField(normalExposedFieldName, normalField);

		// texCoord field
		texCoordField = new SFNode();
		addExposedField(texCoordExposedFieldName, texCoordField);
	}

	public ComposedGeometryNode(ComposedGeometryNode node) {
		this();
		setFieldValues(node);
	}
	
	////////////////////////////////////////////////
	//	Color
	////////////////////////////////////////////////

	public SFNode getColorField() {
		if (isInstanceNode() == false)
			return colorField;
		return (SFNode)getExposedField(colorExposedFieldName);
	}
	
	public void updateColorField() {
		getColorField().setValue(getColorNodes());
	}

	////////////////////////////////////////////////
	//	Coord
	////////////////////////////////////////////////

	public SFNode getCoordField() {
		if (isInstanceNode() == false)
			return coordField;
		return (SFNode)getExposedField(coordExposedFieldName);
	}
	
	public void updateCoordField() {
		getCoordField().setValue(getCoordinateNodes());
	}

	////////////////////////////////////////////////
	//	Normal
	////////////////////////////////////////////////

	public SFNode getNormalField() {
		if (isInstanceNode() == false)
			return normalField;
		return (SFNode)getExposedField(normalExposedFieldName);
	}
	
	public void updateNormalField() {
		getNormalField().setValue(getNormalNodes());
	}

	////////////////////////////////////////////////
	//	texCoord
	////////////////////////////////////////////////

	public SFNode getTexCoordField() {
		if (isInstanceNode() == false)
			return texCoordField;
		return (SFNode)getExposedField(texCoordExposedFieldName);
	}
	
	public void updateTexCoordField() {
		getTexCoordField().setValue(getTextureCoordinateNodes());
	}

	////////////////////////////////////////////////
	//	CCW
	////////////////////////////////////////////////

	public SFBool getCCWField() {
		if (isInstanceNode() == false)
			return ccwField;
		return (SFBool)getField(ccwFieldName);
	}
	
	public void setCCW(boolean value) {
		getCCWField().setValue(value);
	}

	public void setCCW(String value) {
		getCCWField().setValue(value);
	}

	public boolean getCCW() {
		return getCCWField().getValue();
	}

	public boolean isCCW() {
		return getCCW();
	}

	////////////////////////////////////////////////
	//	ColorPerVertex
	////////////////////////////////////////////////

	public SFBool getColorPerVertexField() {
		if (isInstanceNode() == false)
			return colorPerVertexField;
		return (SFBool)getField(colorPerVertexFieldName);
	}
	
	public void setColorPerVertex(boolean value) {
		getColorPerVertexField().setValue(value);
	}

	public void setColorPerVertex(String value) {
		getColorPerVertexField().setValue(value);
	}

	public boolean getColorPerVertex() {
		return getColorPerVertexField().getValue();
	}

	public boolean isColorPerVertex() {
		return getColorPerVertex();
	}
	
	////////////////////////////////////////////////
	//	NormalPerVertex
	////////////////////////////////////////////////

	public SFBool getNormalPerVertexField() {
		if (isInstanceNode() == false)
			return normalPerVertexField;
		return (SFBool)getField(normalPerVertexFieldName);
	}
	
	public void setNormalPerVertex(boolean value) {
		getNormalPerVertexField().setValue(value);
	}

	public void setNormalPerVertex(String value) {
		getNormalPerVertexField().setValue(value);
	}

	public boolean getNormalPerVertex() {
		return getNormalPerVertexField().getValue();
	}

	public boolean isNormalPerVertex() {
		return getNormalPerVertex();
	}
	
	////////////////////////////////////////////////
	//	Solid
	////////////////////////////////////////////////

	public SFBool getSolidField() {
		if (isInstanceNode() == false)
			return solidField;
		return (SFBool)getField(solidFieldName);
	}
	
	public void setSolid(boolean value) {
		getSolidField().setValue(value);
	}

	public void setSolid(String value) {
		getSolidField().setValue(value);
	}

	public boolean getSolid() {
		return getSolidField().getValue();
	}

	public boolean isSolid() {
		return getSolid();
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public void updateBoundingBox() 
	{
		if (isInitialized() == true)
			return;
			
		CoordinateNode coordinate = getCoordinateNodes();
		if (coordinate == null) {
			setBoundingBoxCenter(0.0f, 0.0f, 0.0f);
			setBoundingBoxSize(-1.0f, -1.0f, -1.0f);
			return;
		}
		
		BoundingBox bbox = new BoundingBox(); 
		
		float point[] = new float[3];
		int nCoordinatePoint = coordinate.getNPoints();

		for (int n=0; n<nCoordinatePoint; n++) {
			coordinate.getPoint(n, point);
			bbox.addPoint(point);
		}
		
		setBoundingBoxCenter(bbox.getCenter());
		setBoundingBoxSize(bbox.getSize());
	}
}