/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: ExtrusionNode.java
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class ExtrusionNode extends Geometry3DNode 
{
	//// Field ////////////////
	private final static String beginCapFieldName		= "beginCap";
	private final static String endCapFieldName		= "endCap";
	private final static String convexFieldName		= "convex";
	private final static String creaseAngleFieldName	= "creaseAngle";
	private final static String orientationFieldName	= "orientation";
	private final static String scaleFieldName			= "scale";
	private final static String crossSectionFieldName	= "crossSection";
	private final static String spineFieldName			= "spine";
	private final static String ccwFieldName			= "ccw";
	private final static String solidFieldName			= "solid";

	//// EventIn ////////////////
	private final static String crossSectionEventInName	= "crossSection";
	private final static String orientationEventInName		= "orientation";
	private final static String scaleEventInName			= "scale";
	private final static String spineEventInName			= "spine";

	private SFBool		beginCapField;
	private SFBool		endCapField;
	private SFBool		convexField;
	private SFFloat		creaseAngleField;
	private SFBool		ccwField;
	private SFBool		solidField;
	private MFRotation	orientationField;
	private MFVec2f		scaleField;
	private MFVec2f		crossSectionField;
	private MFVec3f		spineField;
	private MFRotation	setOrientationField;
	private MFVec2f		setScaleField;
	private MFVec2f		setCrossSectionField;
	private MFVec3f		setSpineField;
			
	public ExtrusionNode() {

		setHeaderFlag(false);
		setType(NodeType.EXTRUSION);

		///////////////////////////
		// Field 
		///////////////////////////
		
		// beginCap field
		beginCapField = new SFBool(true);
		addField(beginCapFieldName, beginCapField);

		// endCap field
		endCapField = new SFBool(true);
		addField(endCapFieldName, endCapField);

		// convex field
		convexField = new SFBool(true);
		convexField.setName(convexFieldName);
		addField(convexField);

		// creaseAngle field
		creaseAngleField = new SFFloat(0.0f);
		creaseAngleField.setName(creaseAngleFieldName);
		addField(creaseAngleField);

		// orientation field
		orientationField = new MFRotation();
		orientationField.setName(orientationFieldName);
		addField(orientationField);

		// scale field
		scaleField = new MFVec2f();
		scaleField.setName(scaleFieldName);
		addField(scaleField);

		// crossSection field
		crossSectionField = new MFVec2f();
		addField(crossSectionFieldName, crossSectionField);

		// spine field
		spineField = new MFVec3f();
		addField(spineFieldName, spineField);

		// ccw  field
		ccwField = new SFBool(true);
		ccwField.setName(ccwFieldName);
		addField(ccwField);

		// solid  field
		solidField = new SFBool(true);
		solidField.setName(solidFieldName);
		addField(solidField);

		///////////////////////////
		// EventIn
		///////////////////////////

		// orientation EventIn
		setOrientationField = new MFRotation();
		setOrientationField.setName(orientationEventInName);
		addEventIn(setOrientationField);

		// scale EventIn
		setScaleField = new MFVec2f();
		setScaleField.setName(scaleEventInName);
		addEventIn(setScaleField);

		// crossSection EventIn
		setCrossSectionField = new MFVec2f();
		addEventIn(crossSectionEventInName, setCrossSectionField);

		// spine EventIn
		setSpineField = new MFVec3f();
		addEventIn(spineEventInName, setSpineField);
	}

	public ExtrusionNode(ExtrusionNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	BeginCap
	////////////////////////////////////////////////

	public SFBool getBeginCapField() {
		if (isInstanceNode() == false)
			return beginCapField;
		return (SFBool)getField(beginCapFieldName);
	}
	
	public void setBeginCap(boolean value) {
		getBeginCapField().setValue(value);
	}

	public void setBeginCap(String value) {
		getBeginCapField().setValue(value);
	}

	public boolean getBeginCap() {
		return getBeginCapField().getValue();
	}

	////////////////////////////////////////////////
	//	EndCap
	////////////////////////////////////////////////
	
	public SFBool getEndCapField() {
		if (isInstanceNode() == false)
			return endCapField;
		return (SFBool)getField(endCapFieldName);
	}
	
	public void setEndCap(boolean value) {
		getEndCapField().setValue(value);
	}

	public void setEndCap(String value) {
		getEndCapField().setValue(value);
	}

	public boolean getEndCap() {
		return getEndCapField().getValue();
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
	// orientation
	////////////////////////////////////////////////
	
	public MFRotation getOrientationField() {
		if (isInstanceNode() == false)
			return orientationField;
		return (MFRotation)getField(orientationFieldName);
	}

	public void addOrientation(float value[]) {
		getOrientationField().addValue(value);
	}
	
	public void addOrientation(float x, float y, float z, float angle) {
		getOrientationField().addValue(x, y, z, angle);
	}
	
	public int getNOrientations() {
		return getOrientationField().getSize();
	}
	
	public void setOrientation(int index, float value[]) {
		getOrientationField().set1Value(index, value);
	}
	
	public void setOrientation(int index, float x, float y, float z, float angle) {
		getOrientationField().set1Value(index, x, y, z, angle);
	}

	public void setOrientations(String value) {
		getOrientationField().setValues(value);
	}

	public void setOrientations(String value[]) {
		getOrientationField().setValues(value);
	}
	
	public void getOrientation(int index, float value[]) {
		getOrientationField().get1Value(index, value);
	}
	
	public float[] getOrientation(int index) {
		float value[] = new float[4];
		getOrientation(index, value);
		return value;
	}
	
	public void removeOrientation(int index) {
		getOrientationField().removeValue(index);
	}

	////////////////////////////////////////////////
	// scale
	////////////////////////////////////////////////

	public MFVec2f getScaleField() {
		if (isInstanceNode() == false)
			return scaleField;
		return (MFVec2f)getField(scaleFieldName);
	}

	public void addScale(float value[]) {
		getScaleField().addValue(value);
	}
	
	public void addScale(float x, float y) {
		getScaleField().addValue(x, y);
	}
	
	public int getNScales() {
		return getScaleField().getSize();
	}
	
	public void setScale(int index, float value[]) {
		getScaleField().set1Value(index, value);
	}
	
	public void setScale(int index, float x, float y) {
		getScaleField().set1Value(index, x, y);
	}

	public void setScales(String value) {
		getScaleField().setValues(value);
	}
	
	public void setScales(String value[]) {
		getScaleField().setValues(value);
	}
	
	public void getScale(int index, float value[]) {
		getScaleField().get1Value(index, value);
	}
	
	public float[] getScale(int index) {
		float value[] = new float[2];
		getScale(index, value);
		return value;
	}
	
	public void removeScale(int index) {
		getScaleField().removeValue(index);
	}

	////////////////////////////////////////////////
	// crossSection
	////////////////////////////////////////////////

	public MFVec2f getCrossSectionField() {
		if (isInstanceNode() == false)
			return crossSectionField;
		return (MFVec2f)getField(crossSectionFieldName);
	}
	
	public void addCrossSection(float value[]) {
		getCrossSectionField().addValue(value);
	}
	
	public void addCrossSection(float x, float y) {
		getCrossSectionField().addValue(x, y);
	}
	
	public int getNCrossSections() {
		return getCrossSectionField().getSize();
	}
	
	public void setCrossSection(int index, float value[]) {
		getCrossSectionField().set1Value(index, value);
	}
	
	public void setCrossSection(int index, float x, float y) {
		getCrossSectionField().set1Value(index, x, y);
	}

	public void setCrossSections(String value) {
		getCrossSectionField().setValues(value);
	}

	public void setCrossSections(String value[]) {
		getCrossSectionField().setValues(value);
	}
	
	public void getCrossSection(int index, float value[]) {
		getCrossSectionField().get1Value(index, value);
	}
	
	public float[] getCrossSection(int index) {
		float value[] = new float[2];
		getCrossSection(index, value);
		return value;
	}
	
	public void removeCrossSection(int index) {
		getCrossSectionField().removeValue(index);
	}

	////////////////////////////////////////////////
	// spine
	////////////////////////////////////////////////

	public MFVec3f getSpineField() {
		if (isInstanceNode() == false)
			return spineField;
		return (MFVec3f)getField(spineFieldName);
	}

	public void addSpine(float value[]) {
		getSpineField().addValue(value);
	}
	
	public void addSpine(float x, float y, float z) {
		getSpineField().addValue(x, y, z);
	}
	
	public int getNSpines() {
		return getSpineField().getSize();
	}
	
	public void setSpine(int index, float value[]) {
		getSpineField().set1Value(index, value);
	}
	
	public void setSpine(int index, float x, float y, float z) {
		getSpineField().set1Value(index, x, y, z);
	}

	public void setSpines(String value) {
		getSpineField().setValues(value);
	}

	public void setSpines(String value[]) {
		getSpineField().setValues(value);
	}
	
	public void getSpine(int index, float value[]) {
		getSpineField().get1Value(index, value);
	}
	
	public float[] getSpine(int index) {
		float value[] = new float[3];
		getSpine(index, value);
		return value;
	}
	
	public void removeSpine(int index) {
		getSpineField().removeValue(index);
	}

	////////////////////////////////////////////////
	// set_orientation
	////////////////////////////////////////////////

	public MFRotation getSetSetOrientationField() {
		if (isInstanceNode() == false)
			return setOrientationField;
		return (MFRotation)getEventIn(orientationEventInName);
	}

	public void addSetSetOrientation(float value[]) {
		getSetSetOrientationField().addValue(value);
	}
	
	public void addSetSetOrientation(float x, float y, float z, float angle) {
		getSetSetOrientationField().addValue(x, y, z, angle);
	}
	
	public int getNSetSetOrientations() {
		return getSetSetOrientationField().getSize();
	}
	
	public void setSetSetOrientation(int index, float value[]) {
		getSetSetOrientationField().set1Value(index, value);
	}
	
	public void setSetSetOrientation(int index, float x, float y, float z, float angle) {
		getSetSetOrientationField().set1Value(index, x, y, z, angle);
	}

	public void setSetSetOrientations(String value) {
		getSetSetOrientationField().setValues(value);
	}

	public void setSetSetOrientations(String value[]) {
		getSetSetOrientationField().setValues(value);
	}
	
	public void getSetSetOrientation(int index, float value[]) {
		getSetSetOrientationField().get1Value(index, value);
	}
	
	public float[] getSetSetOrientation(int index) {
		float value[] = new float[4];
		getSetSetOrientation(index, value);
		return value;
	}
	
	public void removeSetSetOrientation(int index) {
		getSetSetOrientationField().removeValue(index);
	}

	////////////////////////////////////////////////
	// set_scale
	////////////////////////////////////////////////

	public MFVec2f getSetScaleField() {
		if (isInstanceNode() == false)
			return setScaleField;
		return (MFVec2f)getEventIn(scaleEventInName);
	}
	
	public void addSetScale(float value[]) {
		getSetScaleField().addValue(value);
	}
	
	public void addSetScale(float x, float y) {
		getSetScaleField().addValue(x, y);
	}
	
	public int getNSetScales() {
		return getSetScaleField().getSize();
	}
	
	public void setSetScale(int index, float value[]) {
		getSetScaleField().set1Value(index, value);
	}
	
	public void setSetScale(int index, float x, float y) {
		getSetScaleField().set1Value(index, x, y);
	}

	public void setSetScales(String value) {
		getSetScaleField().setValues(value);
	}

	public void setSetScales(String value[]) {
		getSetScaleField().setValues(value);
	}
	
	public void getSetScale(int index, float value[]) {
		getSetScaleField().get1Value(index, value);
	}
	
	public float[] getSetScale(int index) {
		float value[] = new float[2];
		getSetScale(index, value);
		return value;
	}
	
	public void removeSetScale(int index) {
		getSetScaleField().removeValue(index);
	}

	////////////////////////////////////////////////
	// set_crossSection
	////////////////////////////////////////////////

	public MFVec2f getSetCrossSectionField() {
		if (isInstanceNode() == false)
			return setCrossSectionField;
		return (MFVec2f)getEventIn(crossSectionEventInName);
	}

	public void addSetCrossSection(float value[]) {
		getSetCrossSectionField().addValue(value);
	}
	
	public void addSetCrossSection(float x, float y) {
		getSetCrossSectionField().addValue(x, y);
	}
	
	public int getNSetCrossSections() {
		return getSetCrossSectionField().getSize();
	}
	
	public void setSetCrossSection(int index, float value[]) {
		getSetCrossSectionField().set1Value(index, value);
	}
	
	public void setSetCrossSection(int index, float x, float y) {
		getSetCrossSectionField().set1Value(index, x, y);
	}

	public void setSetCrossSections(String value) {
		getSetCrossSectionField().setValues(value);
	}

	public void setSetCrossSections(String value[]) {
		getSetCrossSectionField().setValues(value);
	}
	
	public void getSetCrossSection(int index, float value[]) {
		getSetCrossSectionField().get1Value(index, value);
	}
	
	public float[] getSetCrossSection(int index) {
		float value[] = new float[2];
		getSetCrossSection(index, value);
		return value;
	}
	
	public void removeSetCrossSection(int index) {
		getSetCrossSectionField().removeValue(index);
	}

	////////////////////////////////////////////////
	// set_spine
	////////////////////////////////////////////////

	public MFVec3f getSetSpineField() {
		if (isInstanceNode() == false)
			return setSpineField;
		return (MFVec3f)getEventIn(spineEventInName);
	}

	public void addSetSpine(float value[]) {
		getSetSpineField().addValue(value);
	}
	
	public void addSetSpine(float x, float y, float z) {
		getSetSpineField().addValue(x, y, z);
	}
	
	public int getNSetSpines() {
		return getSetSpineField().getSize();
	}
	
	public void setSetSpine(int index, float value[]) {
		getSetSpineField().set1Value(index, value);
	}
	
	public void setSetSpine(int index, float x, float y, float z) {
		getSetSpineField().set1Value(index, x, y, z);
	}

	public void setSetSpines(String value) {
		getSetSpineField().setValues(value);
	}

	public void setSetSpines(String value[]) {
		getSetSpineField().setValues(value);
	}

	public void getSetSpine(int index, float value[]) {
		getSetSpineField().get1Value(index, value);
	}
	
	public float[] getSetSpine(int index) {
		float value[] = new float[3];
		getSetSpine(index, value);
		return value;
	}
	
	public void removeSetSpine(int index) {
		getSetSpineField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void addDefaultParameters() {
		if (getNCrossSections() == 0) {
			addCrossSection(1.0f, 1.0f);
			addCrossSection(1.0f, -1.0f);
			addCrossSection(-1.0f, -1.0f);
			addCrossSection(-1.0f, 1.0f);
			addCrossSection(1.0f, 1.0f);
		}
		if (getNSpines() == 0) {
			addSpine(0.0f, 0.0f, 0.0f);
			addSpine(0.0f, 1.0f, 0.0f);
		}
	}
	
	public void initialize() {
		super.initialize();
		updateBoundingBox();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {

		SFBool beginCap = getBeginCapField();
		SFBool endCap = getEndCapField();
		SFBool ccw = getCCWField();
		SFBool convex = getConvexField();
		SFBool solid = getSolidField();

		printStream.println(indentString + "\t" + "beginCap " + beginCap);
		printStream.println(indentString + "\t" + "endCap " + endCap);
		printStream.println(indentString + "\t" + "solid " + solid);
		printStream.println(indentString + "\t" + "ccw " + ccw);
		printStream.println(indentString + "\t" + "convex " + convex);
		printStream.println(indentString + "\t" + "creaseAngle " + getCreaseAngle());

		MFVec2f crossSection = getCrossSectionField();
		printStream.println(indentString + "\t" + "crossSection [");
		crossSection.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFRotation orientation = getOrientationField();
		printStream.println(indentString + "\t" + "orientation [");
		orientation.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFVec2f scale = getScaleField();
		printStream.println(indentString + "\t" + "scale [");
		scale.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFVec3f spine = getSpineField();
		printStream.println(indentString + "\t" + "spine [");
		spine.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public void updateBoundingBox() {
		// Not implemented
		setBoundingBoxCenter(0.0f, 0.0f, 0.0f);
		setBoundingBoxSize(-1.0f, -1.0f, -1.0f);
	}

	////////////////////////////////////////////////
	//	For Java3D object
	////////////////////////////////////////////////
	
	public int getVertexCount() {
		return getNCrossSections() * getNSpines();
	}
	
	public int getNTriangleCoordIndices() {
		int nCoordinateIndices = (getNCrossSections()*2) * (getNSpines()-1) * 3;
		
		if (getConvex() == true) {
			if (getBeginCap() == true) 
				nCoordinateIndices += getNCrossSections() * 3;
			if (getEndCap() == true) 
				nCoordinateIndices += getNCrossSections() * 3;
		}
		
		return nCoordinateIndices;
	}
}
