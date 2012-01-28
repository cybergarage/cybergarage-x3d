/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Shape2DNode.java
*
*	Revisions:
*
*	12/02/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class Shape2DNode extends Bounded2DNode 
{
	//// ExposedField ////////////////
	private final static String appearanceExposedFieldName		= "appearance";
	private final static String geometryExposedFieldName		= "geometry";

	SFNode appField;
	SFNode geomField;

	public Shape2DNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.SHAPE2D);

		///////////////////////////
		// ExposedField 
		///////////////////////////

		// appearance field
		appField = new SFNode();
		addExposedField(appearanceExposedFieldName, appField);

		// geometry field
		geomField = new SFNode();
		addExposedField(geometryExposedFieldName, geomField);
	}

	public Shape2DNode(Shape2DNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Appearance
	////////////////////////////////////////////////

	public SFNode getAppearanceField() {
		if (isInstanceNode() == false)
			return appField;
		return (SFNode)getExposedField(appearanceExposedFieldName);
	}
	
	public void updateAppearanceField() {
		getAppearanceField().setValue(getAppearanceNodes());
	}

	////////////////////////////////////////////////
	//	Geometry
	////////////////////////////////////////////////

	public SFNode getGeometryField() {
		if (isInstanceNode() == false)
			return geomField;
		return (SFNode)getExposedField(geometryExposedFieldName);
	}
	
	public void updateGeometryField() {
		getGeometryField().setValue(getGeometry3DNode());
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return true;
	}

	public void initialize() {
		super.initialize();
		updateAppearanceField();
		updateGeometryField();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateAppearanceField();
		//updateGeometryField();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}
