/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : PointSet.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.field.*;

public class PointSetNode extends Geometry3DNode {

	//// ExposedField ////////////////
	private String	colorExposedFieldName		= "color";
	private String	coordExposedFieldName		= "coord";

	private SFNode colorField;
	private SFNode coordField;
	
	public PointSetNode() {
		setHeaderFlag(false);
		setType(NodeType.POINTSET);

		///////////////////////////
		// ExposedField 
		///////////////////////////

		// color field
		colorField = new SFNode();
		addExposedField(colorExposedFieldName, colorField);

		// coord field
		coordField = new SFNode();
		addExposedField(coordExposedFieldName, coordField);
	}

	public PointSetNode(PointSetNode node) {
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
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isCoordinateNode() || node.isColorNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();

		if (isInitialized() == false) {
			updateBoundingBox();
			setInitializationFlag(true);
		}

		updateColorField();
		updateCoordField();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateColorField();
		//updateCoordField();
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public void updateBoundingBox() {
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

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		ColorNode color = getColorNodes();
		if (color != null) {
			if (color.isInstanceNode() == false) {
				String nodeName = color.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "color DEF " + color.getName() + " Color {");
				else
					printStream.println(indentString + "\t" + "color Color {");
				color.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "color USE " + color.getName());
		}

		CoordinateNode coord = getCoordinateNodes();
		if (coord != null) {
			if (coord.isInstanceNode() == false) {
				String nodeName = coord.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "coord DEF " + coord.getName() + " Coordinate {");
				else
					printStream.println(indentString + "\t" + "coord Coordinate {");
				coord.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "coord USE " + coord.getName());
		}
	}
}
