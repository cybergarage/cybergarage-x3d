/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : ShapeNode.java
*
*	Revisions:
*
*	12/02/02
*		- Changed the super class from Node to BoundedNode.
*	12/05/03
*		- shen shenyang@163.net <shenyang@163.net>
*		- Fixed a output bugs using getType() instead of getTypeString().
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class ShapeNode extends BoundedNode 
{
	//// ExposedField ////////////////
	private final static String appearanceExposedFieldName		= "appearance";
	private final static String geometryExposedFieldName		= "geometry";

	SFNode appField;
	SFNode geomField;

	public ShapeNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.SHAPE);

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

	public ShapeNode(ShapeNode node) 
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
		if (node.isAppearanceNode() || node.isGeometry3DNode())
			return true;
		else
			return false;
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
		AppearanceNode appearance = getAppearanceNodes();
		if (appearance != null) {
			if (appearance.isInstanceNode() == false) {
				String nodeName = appearance.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "appearance DEF " + appearance.getName() + " Appearance {");
				else
					printStream.println(indentString + "\t" + "appearance Appearance {");
				appearance.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "appearance USE " + appearance.getName());
		}
		
		Node geonode = getGeometry3DNode();
		if (geonode != null) {
			if (geonode.isInstanceNode() == false) {
				String nodeName = geonode.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "geometry DEF " + geonode.getName() + " " + geonode.getTypeString() + " {");
				else
					printStream.println(indentString + "\t" + "geometry " + geonode.getTypeString() + " {");
				geonode.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "geometry USE " + geonode.getName());
		}
	}
}
