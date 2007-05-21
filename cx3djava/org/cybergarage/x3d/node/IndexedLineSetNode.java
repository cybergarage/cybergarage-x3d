/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: IndexedLineSet.java
*
*	Revisions:
*
*	11/25/02
*		- Added a lineWidth field.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.field.*;

public class IndexedLineSetNode extends Geometry3DNode {
	
	//// Field ////////////////
	private final static String colorPerVertexFieldName		= "colorPerVertex";
	private final static String coordIndexFieldName			= "coordIndex";
	private final static String colorIndexFieldName			= "colorIndex";
	private final static String lineWidthFieldName				= "lineWidth";

	//// ExposedField ////////////////
	private final static String colorExposedFieldName			= "color";
	private final static String coordExposedFieldName		= "coord";

	//// EventIn ////////////////
	private final static String coordIndexEventInName		= "coordIndex";
	private final static String colorIndexEventInName			= "colorIndex";

	private SFBool colorPerVertexField;
	private MFInt32 coordIndexField;
	private MFInt32 colorIndexField;
	private SFNode colorField;
	private SFNode coordField;
	private MFInt32 setCoordIndexField;
	private MFInt32 setColorIndexField;
	private SFFloat lineWidthField;

	public IndexedLineSetNode() {
		setHeaderFlag(false);
		setType(NodeType.INDEXEDLINESET);

		///////////////////////////
		// Field 
		///////////////////////////

		// colorPerVertex  field
		colorPerVertexField = new SFBool(true);
		colorPerVertexField.setName(colorPerVertexFieldName);
		addField(colorPerVertexField);

		// coordIndex  field
		coordIndexField = new MFInt32();
		coordIndexField.setName(coordIndexFieldName);
		addField(coordIndexField);

		// colorIndex  field
		colorIndexField = new MFInt32();
		colorIndexField.setName(colorIndexFieldName);
		addField(colorIndexField);

		// lineWidth  field
		lineWidthField = new SFFloat(1.0f);
		lineWidthField.setName(lineWidthFieldName);
		addField(lineWidthField);

		///////////////////////////
		// ExposedField 
		///////////////////////////

		// color field
		colorField = new SFNode();
		addExposedField(colorExposedFieldName, colorField);

		// coord field
		coordField = new SFNode();
		addExposedField(coordExposedFieldName, coordField);


		///////////////////////////
		// EventIn
		///////////////////////////

		// coordIndex  EventIn
		setCoordIndexField = new MFInt32();
		setCoordIndexField.setName(coordIndexEventInName);
		addEventIn(setCoordIndexField);

		// colorIndex  EventIn
		setColorIndexField = new MFInt32();
		setColorIndexField.setName(colorIndexEventInName);
		addEventIn(setColorIndexField);
	}

	public IndexedLineSetNode(IndexedLineSetNode node) {
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
	// CoordIndex
	////////////////////////////////////////////////

	public MFInt32 getCoordIndexField() {
		if (isInstanceNode() == false)
			return coordIndexField;
		return (MFInt32)getField(coordIndexFieldName);
	}
	
	public void addCoordIndex(int value) {
		getCoordIndexField().addValue(value);
	}
	
	public int getNCoordIndices() {
		return getCoordIndexField().getSize();
	}
	
	public void setCoordIndex(int index, int value) {
		getCoordIndexField().set1Value(index, value);
	}

	public void setCoordIndices(String value) {
		getCoordIndexField().setValues(value);
	}

	public void setCoordIndices(String value[]) {
		getCoordIndexField().setValues(value);
	}
	
	public int getCoordIndex(int index) {
		return getCoordIndexField().get1Value(index);
	}
	
	public void removeCoordIndex(int index) {
		getCoordIndexField().removeValue(index);
	}
	
	////////////////////////////////////////////////
	// ColorIndex
	////////////////////////////////////////////////

	public MFInt32 getColorIndexField() {
		if (isInstanceNode() == false)
			return colorIndexField;
		return (MFInt32)getField(colorIndexFieldName);
	}

	public void addColorIndex(int value) {
		getColorIndexField().addValue(value);
	}
	
	public int getNColorIndices() {
		return getColorIndexField().getSize();
	}
	
	public void setColorIndex(int index, int value) {
		getColorIndexField().set1Value(index, value);
	}

	public void setColorIndices(String value) {
		getColorIndexField().setValues(value);
	}

	public void setColorIndices(String value[]) {
		getColorIndexField().setValues(value);
	}
	
	public int getColorIndex(int index) {
		return getColorIndexField().get1Value(index);
	}
	
	public void removeColorIndex(int index) {
		getColorIndexField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	LineWidth
	////////////////////////////////////////////////

	public SFFloat getLineWidthField() {
		if (isInstanceNode() == false)
			return lineWidthField;
		return (SFFloat)getField(lineWidthFieldName);
	}
	
	public void setLineWidth(float value) {
		getLineWidthField().setValue(value);
	}

	public void setLineWidth(String value) {
		getLineWidthField().setValue(value);
	}

	public float getLineWidth() {
		return getLineWidthField().getValue();
	}

	////////////////////////////////////////////////
	// set_coordIndex
	////////////////////////////////////////////////

	public MFInt32 getSetCoordIndexField() {
		if (isInstanceNode() == false)
			return setCoordIndexField;
		return (MFInt32)getEventIn(coordIndexEventInName);
	}

	public void addSetCoordIndex(int value) {
		getSetCoordIndexField().addValue(value);
	}
	
	public int getNSetCoordIndices() {
		return getSetCoordIndexField().getSize();
	}
	
	public void setSetCoordIndex(int index, int value) {
		getSetCoordIndexField().set1Value(index, value);
	}

	public void setSetCoordIndices(String value) {
		getSetCoordIndexField().setValues(value);
	}

	public void setSetCoordIndices(String value[]) {
		getSetCoordIndexField().setValues(value);
	}
	
	public int getSetCoordIndex(int index) {
		return getSetCoordIndexField().get1Value(index);
	}
	
	public void removeSetCoordIndex(int index) {
		getSetCoordIndexField().removeValue(index);
	}
	
	public int getNSetTriangleCoordIndices() {
		return getSetCoordIndexField().getNTriangleIndices();
	}
	
	////////////////////////////////////////////////
	// set_colorIndex
	////////////////////////////////////////////////

	public MFInt32 getSetColorIndexField() {
		if (isInstanceNode() == false)
			return setColorIndexField;
		return (MFInt32)getEventIn(colorIndexEventInName);
	}

	public void addSetColorIndex(int value) {
		getSetColorIndexField().addValue(value);
	}
	
	public int getNSetColorIndices() {
		return getSetColorIndexField().getSize();
	}
	
	public void setSetColorIndex(int index, int value) {
		getSetColorIndexField().set1Value(index, value);
	}

	public void setSetColorIndices(String value) {
		getSetColorIndexField().setValues(value);
	}

	public void setSetColorIndices(String value[]) {
		getSetColorIndexField().setValues(value);
	}
	
	public int getSetColorIndex(int index) {
		return getSetColorIndexField().get1Value(index);
	}
	
	public void removeSetColorIndex(int index) {
		getSetColorIndexField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isColorNode() || node.isCoordinateNode())
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
	//	Line
	////////////////////////////////////////////////

	public int getNLines() 
	{
		CoordinateNode coordinate = getCoordinateNodes();
		if (coordinate == null)
			return 0;

		int nTotalCoordIndex = 0;
		int nCoordIndex = 0;
		for (int n=0; n<getNCoordIndices(); n++) {
			if (getCoordIndex(n) != -1) 
				nCoordIndex++;
			if (getCoordIndex(n) == -1 || n == (getNCoordIndices()-1)) {
				if (2 <= nCoordIndex)
					nTotalCoordIndex += nCoordIndex - 1;
				nCoordIndex = 0;
			}
		}
			
		return nTotalCoordIndex;
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool colorPerVertex = getColorPerVertexField();

		printStream.println(indentString + "\t" + "colorPerVertex " + colorPerVertex);

		ColorNode color = getColorNodes();
		if (color != null) {
			if (0 < color.getNColors()) {
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
		}

		CoordinateNode coord = getCoordinateNodes();
		if (coord != null) {
			if (0 < coord.getNPoints()) {
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

		if (0 < getNCoordIndices()) {
			MFInt32 coordIndex = getCoordIndexField();
			printStream.println(indentString + "\t" + "coordIndex [");
			coordIndex.outputIndex(printStream, indentString + "\t\t");
			printStream.println(indentString + "\t" + "]");
		}
		
		if (0 < getNColorIndices()) {
			MFInt32 colorIndex = getColorIndexField();
			printStream.println(indentString + "\t" + "colorIndex [");
			colorIndex.outputIndex(printStream, indentString + "\t\t");
			printStream.println(indentString + "\t" + "]");
		}
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
}