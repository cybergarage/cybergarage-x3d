/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: IndexdFaceSetNode.java
*
*	Revisions:
*
*	12/03/02
*		- Changed the super class from Geometry3DNode to ComposedGeometryNode.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.field.*;

public class IndexedFaceSetNode extends ComposedGeometryNode 
{
	//// Field ////////////////
	private final static String convexFieldName				= "convex";
	private final static String creaseAngleFieldName			= "creaseAngle";
	private final static String coordIndexFieldName			= "coordIndex";
	private final static String texCoordIndexFieldName		= "texCoordIndex";
	private final static String colorIndexFieldName			= "colorIndex";
	private final static String normalIndexFieldName			= "normalIndex";

	//// EventIn ////////////////
	private final static String coordIndexEventInName		= "coordIndex";
	private final static String texCoordIndexEventInName		= "texCoordIndex";
	private final static String colorIndexEventInName			= "colorIndex";
	private final static String normalIndexEventInName		= "normalIndex";


	private SFBool convexField;
	private SFFloat creaseAngleField;
	private MFInt32 coordIndexField;
	private MFInt32 texCoordIndexField;
	private MFInt32 colorIndexField;
	private MFInt32 normalIndexField;
	private MFInt32 setCoordIndexField;
	private MFInt32 setTexCoordIndexField;
	private MFInt32 setColorIndexField;
	private MFInt32 setNormalIndexField;
		
	public IndexedFaceSetNode() {
		setHeaderFlag(false);
		setType(NodeType.INDEXEDFACESET);

		///////////////////////////
		// Field 
		///////////////////////////

		// convex  field
		convexField = new SFBool(true);
		convexField.setName(convexFieldName);
		addField(convexField);

		// creaseAngle  field
		creaseAngleField = new SFFloat(0.0f);
		creaseAngleField.setName(creaseAngleFieldName);
		addField(creaseAngleField);

		// coordIndex  field
		coordIndexField = new MFInt32();
		coordIndexField.setName(coordIndexFieldName);
		addField(coordIndexField);

		// texCoordIndex  field
		texCoordIndexField = new MFInt32();
		texCoordIndexField.setName(texCoordIndexFieldName);
		addField(texCoordIndexField);

		// colorIndex  field
		colorIndexField = new MFInt32();
		colorIndexField.setName(colorIndexFieldName);
		addField(colorIndexField);

		// normalIndex  field
		normalIndexField = new MFInt32();
		normalIndexField.setName(normalIndexFieldName);
		addField(normalIndexField);

		///////////////////////////
		// EventIn
		///////////////////////////

		// coordIndex  EventIn
		setCoordIndexField = new MFInt32();
		setCoordIndexField.setName(coordIndexEventInName);
		addEventIn(setCoordIndexField);

		// texCoordIndex  EventIn
		setTexCoordIndexField = new MFInt32();
		setTexCoordIndexField.setName(texCoordIndexEventInName);
		addEventIn(setTexCoordIndexField);

		// colorIndex  EventIn
		setColorIndexField = new MFInt32();
		setColorIndexField.setName(colorIndexEventInName);
		addEventIn(setColorIndexField);

		// normalIndex  EventIn
		setNormalIndexField = new MFInt32();
		setNormalIndexField.setName(normalIndexEventInName);
		addEventIn(setNormalIndexField);
	}

	public IndexedFaceSetNode(IndexedFaceSetNode node) {
		this();
		setFieldValues(node);
	}
	
	////////////////////////////////////////////////
	//	Convex
	////////////////////////////////////////////////

	public SFBool getConvexField() {
		if (isInstanceNode() == false)
			return convexField;
		return (SFBool)getField(convexFieldName);
	}
	
	public void setConvex(boolean value) {
		getConvexField().setValue(value);
	}

	public void setConvex(String value) {
		getConvexField().setValue(value);
	}

	public boolean getConvex() {
		return getConvexField().getValue();
	}

	public boolean isConvex() {
		return getConvex();
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
	
	public int getCoordIndex(int index) {
		return getCoordIndexField().get1Value(index);
	}
	
	public void removeCoordIndex(int index) {
		getCoordIndexField().removeValue(index);
	}
	
	public int getNTriangleCoordIndices() {
		return getCoordIndexField().getNTriangleIndices();
	}
	
	////////////////////////////////////////////////
	// TexCoordIndex
	////////////////////////////////////////////////

	public MFInt32 getTexCoordIndexField() {
		if (isInstanceNode() == false)
			return texCoordIndexField;
		return (MFInt32)getField(texCoordIndexFieldName);
	}
	
	public void addTexCoordIndex(int value) {
		getTexCoordIndexField().addValue(value);
	}
	
	public int getNTexCoordIndices() {
		return getTexCoordIndexField().getSize();
	}
	
	public void setTexCoordIndex(int index, int value) {
		getTexCoordIndexField().set1Value(index, value);
	}

	public void setTexCoordIndices(String value) {
		getTexCoordIndexField().setValues(value);
	}

	public void setTexCoordIndices(String value[]) {
		getTexCoordIndexField().setValues(value);
	}
	
	public int getTexCoordIndex(int index) {
		return getTexCoordIndexField().get1Value(index);
	}
	
	public void removeTexCoordIndex(int index) {
		getTexCoordIndexField().removeValue(index);
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
	
	public int getColorIndex(int index) {
		return getColorIndexField().get1Value(index);
	}
	
	public void removeColorIndex(int index) {
		getColorIndexField().removeValue(index);
	}

	////////////////////////////////////////////////
	// NormalIndex
	////////////////////////////////////////////////

	public MFInt32 getNormalIndexField() {
		if (isInstanceNode() == false)
			return normalIndexField;
		return (MFInt32)getField(normalIndexFieldName);
	}
	
	public void addNormalIndex(int value) {
		getNormalIndexField().addValue(value);
	}
	
	public int getNNormalIndices() {
		return getNormalIndexField().getSize();
	}
	
	public void setNormalIndex(int index, int value) {
		getNormalIndexField().set1Value(index, value);
	}

	public void setNormalIndices(String value) {
		getNormalIndexField().setValues(value);
	}
	
	public int getNormalIndex(int index) {
		return getNormalIndexField().get1Value(index);
	}
	
	public void removeNormalIndex(int index) {
		getNormalIndexField().removeValue(index);
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
	// set_texCoordIndex
	////////////////////////////////////////////////

	public MFInt32 getSetTexCoordIndexField() {
		if (isInstanceNode() == false)
			return setTexCoordIndexField;
		return (MFInt32)getEventIn(texCoordIndexEventInName);
	}

	public void addSetTexCoordIndex(int value) {
		getSetTexCoordIndexField().addValue(value);
	}
	
	public int getNSetTexCoordIndices() {
		return getSetTexCoordIndexField().getSize();
	}
	
	public void setSetTexCoordIndex(int index, int value) {
		getSetTexCoordIndexField().set1Value(index, value);
	}

	public void setSetTexCoordIndices(String value) {
		getSetTexCoordIndexField().setValues(value);
	}
	
	public int getSetTexCoordIndex(int index) {
		return getSetTexCoordIndexField().get1Value(index);
	}
	
	public void removeSetTexCoordIndex(int index) {
		getSetTexCoordIndexField().removeValue(index);
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
	
	public int getSetColorIndex(int index) {
		return getSetColorIndexField().get1Value(index);
	}
	
	public void removeSetColorIndex(int index) {
		getSetColorIndexField().removeValue(index);
	}

	////////////////////////////////////////////////
	// set_normalIndex
	////////////////////////////////////////////////

	public MFInt32 getSetNormalIndexField() {
		if (isInstanceNode() == false)
			return setNormalIndexField;
		return (MFInt32)getEventIn(normalIndexEventInName);
	}
	
	public void addSetNormalIndex(int value) {
		getSetNormalIndexField().addValue(value);
	}
	
	public int getNSetNormalIndices() {
		return getSetNormalIndexField().getSize();
	}
	
	public void setNSetormalIndex(int index, int value) {
		getSetNormalIndexField().set1Value(index, value);
	}

	public void setNSetormalIndices(String value) {
		getSetNormalIndexField().setValues(value);
	}
	
	public int getNSetormalIndex(int index) {
		return getSetNormalIndexField().get1Value(index);
	}
	
	public void removeSetNormalIndex(int index) {
		getSetNormalIndexField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isColorNode() || node.isCoordinateNode() || node.isNormalNode() || node.isTextureCoordinateNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();

		SceneGraph sg = getSceneGraph();
		if (sg == null)
			return;

		if (isInitialized() == false) {
			if (sg.getOption(SceneGraph.NORMAL_GENERATION))
				generateNormals();
			if (sg.getOption(SceneGraph.TEXTURE_GENERATION)) {
				Node parentNode = getParentNode();
				if (parentNode != null) {
					AppearanceNode appearance = parentNode.getAppearanceNodes();
					if (appearance != null) {
						if (appearance.getTextureNode() != null)
							generateTextureCoordinate();
					}
				}
			}
			updateBoundingBox();
			setInitializationFlag(true);
		}

		updateColorField();
		updateCoordField();
		updateNormalField();
		updateTexCoordField();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateColorField();
		//updateCoordField();
		//updateNormalField();
		//updateTexCoordField();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool convex = getConvexField();
		SFBool solid = getSolidField();
		SFBool normalPerVertex = getNormalPerVertexField();
		SFBool colorPerVertex = getColorPerVertexField();
		SFBool ccw = getCCWField();

		printStream.println(indentString + "\t" + "ccw " + ccw);
		printStream.println(indentString + "\t" + "colorPerVertex " + colorPerVertex);
		printStream.println(indentString + "\t" + "normalPerVertex " + normalPerVertex);
		printStream.println(indentString + "\t" + "convex " + convex);
		printStream.println(indentString + "\t" + "creaseAngle " + getCreaseAngle());
		printStream.println(indentString + "\t" + "solid " + solid);

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

		NormalNode normal = getNormalNodes();
		if (normal != null) {
			if (0 < normal.getNVectors()) {
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
		}

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

		TextureCoordinateNode texCoord = getTextureCoordinateNodes();
		if (texCoord != null) {
			if (0 < texCoord.getNPoints()) {
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
		
		if (0 < getNNormalIndices()) {
			MFInt32 normalIndex = getNormalIndexField();
			printStream.println(indentString + "\t" + "normalIndex [");
			normalIndex.outputIndex(printStream, indentString + "\t\t");
			printStream.println(indentString + "\t" + "]");
		}
		
		if (0 < getNTexCoordIndices()) {
			MFInt32 texCoordIndex = getTexCoordIndexField();
			printStream.println(indentString + "\t" + "texCoordIndex [");
			texCoordIndex.outputIndex(printStream, indentString + "\t\t");
			printStream.println(indentString + "\t" + "]");
		}
	}

	////////////////////////////////////////////////////////////
	//	generateNormals
	////////////////////////////////////////////////////////////

	public void getNormalFromVertices(float vpoint[][], float vector[])	{
		vector[0] = (vpoint[1][1] - vpoint[0][1])*(vpoint[2][2] - vpoint[1][2]) - (vpoint[1][2] - vpoint[0][2])*(vpoint[2][1] - vpoint[1][1]);
		vector[1] = (vpoint[1][2] - vpoint[0][2])*(vpoint[2][0] - vpoint[1][0]) - (vpoint[1][0] - vpoint[0][0])*(vpoint[2][2] - vpoint[1][2]);
		vector[2] = (vpoint[1][0] - vpoint[0][0])*(vpoint[2][1] - vpoint[1][1]) - (vpoint[1][1] - vpoint[0][1])*(vpoint[2][0] - vpoint[1][0]);
		float mag = (float)Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1] + vector[2]*vector[2]);
		vector[0] /= mag;
		vector[1] /= mag;
		vector[2] /= mag;
	}

	public boolean generateNormals() 
	{
		NormalNode normal = getNormalNodes();
		if (normal != null)
			return false;

		CoordinateNode coordinate = getCoordinateNodes();
		if (coordinate == null)
			return false;

		normal = new NormalNode();

		int		nPolygon = 0;
		int		nVertex = 0;
		float	vpoint[][] = new float[3][3];
		float	vector[] = new float[3];

		int		nCoordIndexes = getNCoordIndices();

		for (int nCoordIndex=0; nCoordIndex<nCoordIndexes; nCoordIndex++) {
			int coordIndex = getCoordIndex(nCoordIndex);
			if (coordIndex != -1) {
				if (nVertex < 3)
					coordinate.getPoint(coordIndex, vpoint[nVertex]);
				nVertex++;
			}
			else {
				getNormalFromVertices(vpoint, vector);
				normal.addVector(vector);
				
				nVertex = 0;
				nPolygon++;
			}
		}

		addChildNode(normal);
		setNormalPerVertex(false);

		return true;
	}

	////////////////////////////////////////////////////////////
	//	generateTextureCoordinate
	////////////////////////////////////////////////////////////

	public void setExtents(SFVec3f maxExtents, SFVec3f minExtents, float vpoint[])
	{
		if (maxExtents.getX() < vpoint[0])
			maxExtents.setX(vpoint[0]);
		if (maxExtents.getY() < vpoint[1])
			maxExtents.setY(vpoint[1]);
		if (maxExtents.getZ() < vpoint[2])
			maxExtents.setZ(vpoint[2]);
		if (minExtents.getX() > vpoint[0])
			minExtents.setX(vpoint[0]);
		if (minExtents.getY() > vpoint[1])
			minExtents.setY(vpoint[1]);
		if (minExtents.getZ() > vpoint[2])
			minExtents.setZ(vpoint[2]);
	}

	public int getNPolygons() 
	{
		CoordinateNode coordinate = getCoordinateNodes();
		if (coordinate == null)
			return 0;

		int nCoordIndex = 0;
		for (int n=0; n<getNCoordIndices(); n++) {
			if (getCoordIndex(n) == -1 || n == (getNCoordIndices()-1))
				nCoordIndex++;
		}
		return nCoordIndex;
	}

	public void getRotateMatrixFromNormal(float normal[], SFMatrix matrix) {
		SFMatrix	mx = new SFMatrix();
		SFMatrix	my = new SFMatrix();
		float		mxValue[][] = new float[4][4];
		float		myValue[][] = new float[4][4];

		mx.getValue(mxValue);
		my.getValue(myValue);

		float d = (float)Math.sqrt(normal[1]*normal[1] + normal[2]*normal[2]);

		if (d != 0.0f) {
			float cosa = normal[2] / d;
			float sina = normal[1] / d;
			mxValue[0][0] = 1.0f;
			mxValue[0][1] = 0.0f;
			mxValue[0][2] = 0.0f;
			mxValue[1][0] = 0.0f;
			mxValue[1][1] = cosa;
			mxValue[1][2] = sina;
			mxValue[2][0] = 0.0f;
			mxValue[2][1] = -sina;
			mxValue[2][2] = cosa;
		}
	
		float cosb = d;
		float sinb = normal[0];
	
		myValue[0][0] = cosb;
		myValue[0][1] = 0.0f;
		myValue[0][2] = sinb;
		myValue[1][0] = 0.0f;
		myValue[1][1] = 1.0f;
		myValue[1][2] = 0.0f;
		myValue[2][0] = -sinb;
		myValue[2][1] = 0.0f;
		myValue[2][2] = cosb;

		mx.setValue(mxValue);
		my.setValue(myValue);

		matrix.init();
		matrix.add(my);
		matrix.add(mx);
	}

	public boolean generateTextureCoordinate() {
		TextureCoordinateNode texCoord = getTextureCoordinateNodes();
		if (texCoord != null)
			return false;

		CoordinateNode coordinate = getCoordinateNodes();
		if (coordinate == null)
			return false;

		texCoord = new TextureCoordinateNode();

		int nPolygon = getNPolygons();

		float	normal[][] = new float[nPolygon][3];
		SFVec3f	center[] = new SFVec3f[nPolygon];
		SFVec3f	maxExtents[] = new SFVec3f[nPolygon];
		SFVec3f	minExtents[] = new SFVec3f[nPolygon];
		for (int n=0; n<nPolygon; n++) {
			center[n] = new SFVec3f();
			maxExtents[n] = new SFVec3f();
			minExtents[n] = new SFVec3f();
		}

		boolean	bPolygonBegin;
		int		polyn;

		float	vpoint[][] = new float[3][3];
		float	coord[] = new float[3];

		int		vertexn = 0;

		bPolygonBegin = true;
		polyn = 0;

		int nCoordIndexes = getNCoordIndices();

		for (int n=0; n<nCoordIndexes; n++) {
			int coordIndex = getCoordIndex(n);
			if (coordIndex != -1) {

				if (vertexn < 3)
					coordinate.getPoint(coordIndex, vpoint[vertexn]);

				float point[] = new float[3];
				coordinate.getPoint(coordIndex, point);
				if (bPolygonBegin) {
					maxExtents[polyn].setValue(point);
					minExtents[polyn].setValue(point);
					center[polyn].setValue(point);
					bPolygonBegin = false;
				}
				else {
					setExtents(maxExtents[polyn], minExtents[polyn], point);
					center[polyn].add(point);
				}

				vertexn++;
			}
			else {
				getNormalFromVertices(vpoint, normal[polyn]);
				center[polyn].scale(1.0f / (float)vertexn);
				maxExtents[polyn].sub(center[polyn]);
				minExtents[polyn].sub(center[polyn]);
				vertexn = 0;
				bPolygonBegin = true;
				polyn++;
			}
		}

		float		minx, miny, maxx, maxy, xlength, ylength;
		SFMatrix	matrix = new SFMatrix();

		bPolygonBegin = true;
		polyn = 0;
		minx = miny = maxx = maxy = xlength = ylength = 0.0f;

		for (int n=0; n<nCoordIndexes; n++) {
			int coordIndex = getCoordIndex(n);
			if (coordIndex != -1) {

				if (bPolygonBegin) {
					getRotateMatrixFromNormal(normal[polyn], matrix);
					matrix.multi(minExtents[polyn]);
					matrix.multi(maxExtents[polyn]);
					minx = minExtents[polyn].getX();
					miny = minExtents[polyn].getY();
					maxx = maxExtents[polyn].getX();
					maxy = maxExtents[polyn].getY();
					xlength = (float)Math.abs(maxExtents[polyn].getX() - minExtents[polyn].getX());
					ylength = (float)Math.abs(maxExtents[polyn].getY() - minExtents[polyn].getY());

					if (xlength == 0.0f || ylength == 0.0f)
						return false;

					bPolygonBegin = false;
				}

				coordinate.getPoint(coordIndex, coord);
	
				coord[0] -= center[polyn].getX();
				coord[1] -= center[polyn].getY();
				coord[2] -= center[polyn].getZ();

				matrix.multi(coord);

				coord[0] = (float)Math.abs(coord[0] - minx) / xlength;
				coord[1] = (float)Math.abs(coord[1] - miny) / ylength;

				texCoord.addPoint(coord);
			}
			else {
				bPolygonBegin = true;
				polyn++;
			}
		}

		addChildNode(texCoord);

		return true;
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

