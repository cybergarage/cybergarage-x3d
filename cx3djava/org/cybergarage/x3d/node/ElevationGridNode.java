/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: ElevataionGridNode.java
*
*	Revisions:
*
*	12/03/02
*		- Changed the super class from Geometry3DNode to ComposedGeometryNode.
*	03/29/04
*		- Joerg Scheurich aka MUFTI <rusmufti@helpdesk.rus.uni-stuttgart.de>
*		- Fixed the default value of the xSpacing and the zSpacing field to "1.0".
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.util.Debug;

public class ElevationGridNode extends ComposedGeometryNode {

	private final static String setHeightEventInName	= "height";
	private final static String xDimensionFieldName		= "xDimension";
	private final static String zDimensionFieldName		= "zDimension";
	private final static String xSpacingFieldName		= "xSpacing";
	private final static String zSpacingFieldName		= "zSpacing";
	private final static String heightFieldName			= "height";
	private final static String creaseAngleFieldName	= "creaseAngle";

	private SFFloat xSpacingField;
	private SFFloat zSpacingField;
	private SFInt32 xDimensionField;
	private SFInt32 zDimensionField;
	private SFFloat creaseAngleField;
	private MFFloat heightField;
	private MFFloat setHeightField;
	
	public ElevationGridNode() {

		setHeaderFlag(false);
		setType(NodeType.ELEVATIONGRID);

		// xSpacing field
		// Thanks for Joerg Scheurich aka MUFTI (03/29/04)
		xSpacingField = new SFFloat(1.0f);
		addField(xSpacingFieldName, xSpacingField);

		// zSpacing field
		// Thanks for Joerg Scheurich aka MUFTI (03/29/04)
		zSpacingField = new SFFloat(1.0f);
		addField(zSpacingFieldName, zSpacingField);

		// xDimension field
		xDimensionField = new SFInt32(0);
		addField(xDimensionFieldName, xDimensionField);

		// zDimension field
		zDimensionField = new SFInt32(0);
		addField(zDimensionFieldName, zDimensionField);

		// creaseAngle exposed field
		creaseAngleField = new SFFloat(0.0f);
		creaseAngleField.setName(creaseAngleFieldName);
		addField(creaseAngleField);

		// height exposed field
		heightField = new MFFloat();
		addField(heightFieldName, heightField);

		// height eventIn
		setHeightField = new MFFloat();
		addEventIn(setHeightEventInName, setHeightField);
	}

	public ElevationGridNode(ElevationGridNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	// set_height
	////////////////////////////////////////////////

	public MFFloat getSetHeightField() {
		if (isInstanceNode() == false)
			return setHeightField;
		return (MFFloat)getEventIn(setHeightEventInName);
	}

	public void addSetHeight(float value) {
		getSetHeightField().addValue(value);
	}
	
	public int getNSetHeights() {
		return getSetHeightField().getSize();
	}
	
	public void setSetHeight(int index, float value) {
		getSetHeightField().set1Value(index, value);
	}

	public void setSetHeights(String value) {
		getSetHeightField().setValues(value);
	}

	public void setSetHeights(String value[]) {
		getSetHeightField().setValues(value);
	}
	
	public float getSetHeight(int index) {
		return getSetHeightField().get1Value(index);
	}
	
	public void removeSetHeight(int index) {
		getSetHeightField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	xSpacing
	////////////////////////////////////////////////

	public SFFloat getXSpacingField() {
		if (isInstanceNode() == false)
			return xSpacingField;
		return (SFFloat)getField(xSpacingFieldName);
	}

	public void setXSpacing(float value) {
		getXSpacingField().setValue(value);
	}

	public void setXSpacing(String value) {
		getXSpacingField().setValue(value);
	}
	
	public float getXSpacing() {
		return getXSpacingField().getValue();
	}

	////////////////////////////////////////////////
	//	zSpacing
	////////////////////////////////////////////////

	public SFFloat getZSpacingField() {
		if (isInstanceNode() == false)
			return zSpacingField;
		return (SFFloat)getField(zSpacingFieldName);
	}

	public void setZSpacing(float value) {
		getZSpacingField().setValue(value);
	}

	public void setZSpacing(String value) {
		getZSpacingField().setValue(value);
	}
	
	public float getZSpacing() {
		return getZSpacingField().getValue();
	}

	////////////////////////////////////////////////
	//	xDimension
	////////////////////////////////////////////////

	public SFInt32 getXDimensionField() {
		if (isInstanceNode() == false)
			return xDimensionField;
		return (SFInt32)getField(xDimensionFieldName);
	}

	public void setXDimension(int value) {
		getXDimensionField().setValue(value);
	}

	public void setXDimension(String value) {
		getXDimensionField().setValue(value);
	}
	
	public int getXDimension() {
		return getXDimensionField().getValue();
	}

	////////////////////////////////////////////////
	//	zDimension
	////////////////////////////////////////////////

	public SFInt32 getZDimensionField() {
		if (isInstanceNode() == false)
			return zDimensionField;
		return (SFInt32)getField(zDimensionFieldName);
	}

	public void setZDimension(int value) {
		getZDimensionField().setValue(value);
	}
	
	public void setZDimension(String value) {
		getZDimensionField().setValue(value);
	}
	
	public int getZDimension() {
		return getZDimensionField().getValue();
	}

	////////////////////////////////////////////////
	//	CreaseAngle
	////////////////////////////////////////////////
	
	public SFFloat getCreaseAngleField() {
		if (isInstanceNode() == false)
			return creaseAngleField;
		return (SFFloat)getField(creaseAngleFieldName);
	}
	
	public void setCreaseAngle(float value) {
		getCreaseAngleField().setValue(value);
	}

	public void setCreaseAngle(String value) {
		getCreaseAngleField().setValue(value);
	}

	public float getCreaseAngle() {
		return getCreaseAngleField().getValue();
	}

	////////////////////////////////////////////////
	// height
	////////////////////////////////////////////////

	public MFFloat getHeightField() {
		if (isInstanceNode() == false)
			return heightField;
		return (MFFloat)getField(heightFieldName);
	}

	public void addHeight(float value) {
		getHeightField().addValue(value);
	}
	
	public int getNHeights() {
		return getHeightField().getSize();
	}
	
	public void setHeight(int index, float value) {
		getHeightField().set1Value(index, value);
	}

	public void setHeights(String value) {
		getHeightField().setValues(value);
	}

	public void setHeights(String value[]) {
		getHeightField().setValues(value);
	}
	
	public float getHeight(int index) {
		return getHeightField().get1Value(index);
	}
	
	public void removeHeight(int index) {
		getHeightField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isColorNode() || node.isNormalNode() || node.isTextureCoordinateNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();
		updateColorField();
		updateNormalField();
		updateTexCoordField();
		updateBoundingBox();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateColorField();
		//updateNormalField();
		//updateTexCoordField();
		//updateBoundingBox();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool ccw = getCCWField();
		SFBool solid = getSolidField();
		SFBool colorPerVertex = getColorPerVertexField();
		SFBool normalPerVertex = getNormalPerVertexField();

		printStream.println(indentString + "\t" + "xDimension " + getXDimension());
		printStream.println(indentString + "\t" + "xSpacing " + getXSpacing());
		printStream.println(indentString + "\t" + "zDimension " + getZDimension());
		printStream.println(indentString + "\t" + "zSpacing " + getZSpacing());

		MFFloat height = getHeightField();
		printStream.println(indentString + "\t" + "height [");
		height.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		printStream.println(indentString + "\t" + "colorPerVertex " + colorPerVertex);
		printStream.println(indentString + "\t" + "normalPerVertex " + normalPerVertex);
		printStream.println(indentString + "\t" + "ccw " + ccw);
		printStream.println(indentString + "\t" + "solid " + solid);
		printStream.println(indentString + "\t" + "creaseAngle " + getCreaseAngle());
		
		NormalNode normal = getNormalNodes();
		if (normal != null) {
			if (normal.isInstanceNode() == false) {
				String nodeName = normal.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "normal DEF " + normal.getName() + " Normal {");
				else
					printStream.println(indentString + "\t" + "normal Normal {");
				normal.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "normal USE " + normal.getName());
		}

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

		TextureCoordinateNode texCoord = getTextureCoordinateNodes();
		if (texCoord != null) {
			if (texCoord.isInstanceNode() == false) {
				String nodeName = texCoord.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "texCoord DEF " + texCoord.getName() + " TextureCoordinate {");
				else
					printStream.println(indentString + "\t" + "texCoord TextureCoordinate {");
				texCoord.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "texCoord USE " + texCoord.getName());
		}

	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public void updateBoundingBox() {
		float xSize = (getXDimension()-1) * getXSpacing();
		float zSize = (getZDimension()-1) * getZSpacing();
		float minHeight = 0.0f;
		float maxHeight = 0.0f;
		int nHeights = getNHeights();
		if (0 < nHeights) {
			minHeight = maxHeight = getHeight(0);
			for (int n=1; n<nHeights; n++) {
				float height = getHeight(n);
				if (height < minHeight)
					minHeight = height;
				if (maxHeight < height)
					maxHeight = height;
			}
		}
		setBoundingBoxCenter(xSize/2.0f, (maxHeight+minHeight)/2.0f, zSize/2.0f);
		setBoundingBoxSize(xSize/2.0f, (float)Math.abs(maxHeight+minHeight)/2.0f, zSize/2.0f);
		
		float bboxCenter[] = getBoundingBoxCenter();
		float bboxSize[] = getBoundingBoxSize();
		Debug.message("ElevatoinGridNode::updateBoundingBox");
		Debug.message("\tbboxCenter = " + bboxCenter[0] + ", " + bboxCenter[1] + ", " + bboxCenter[2]);
		Debug.message("\tbboxSize   = " + bboxSize[0] + ", " + bboxSize[1] + ", " + bboxSize[2]);
	}

	////////////////////////////////////////////////
	//	For Java3D object
	////////////////////////////////////////////////
	
	public int getVertexCount() {
		return getXDimension() * getZDimension();
	}
	
	public int getNTriangleCoordIndices() {
		return (3 * 2 * (getXDimension()-1) * (getZDimension()-1));
	}
}
