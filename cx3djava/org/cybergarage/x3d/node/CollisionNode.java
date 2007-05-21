/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : Collision.java
*
*	Revisions:
*
*	11/18/02
*		- Changed the super class from GroupingNode to BoundedGroupingNode.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class CollisionNode extends BoundedGroupingNode {

	private final static String collideFieldName		= "collide";
	private final static String collideTimeEventOut		= "collideTime";

	private 	SFBool collideField;
	private	SFTime collideTimeField;
	
	public CollisionNode() {
		super();

		setHeaderFlag(false);
		setType(NodeType.COLLISION);

		// collide exposed field
		collideField = new SFBool(true);
		addExposedField(collideFieldName, collideField);

		// collide event out
		collideTimeField = new SFTime(-1.0);
		addEventOut(collideTimeEventOut, collideTimeField);
	}

	public CollisionNode(CollisionNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	collide
	////////////////////////////////////////////////

	public SFBool getCollideField() {
		if (isInstanceNode() == false)
			return collideField;
		return (SFBool)getExposedField(collideFieldName);
	}

	public void setCollide(boolean value) {
		getCollideField().setValue(value);
	}

	public void setCollide(String value) {
		getCollideField().setValue(value);
	}

	public boolean getCollide() {
		return getCollideField().getValue();
	}

	////////////////////////////////////////////////
	//	collideTime
	////////////////////////////////////////////////

	public SFTime getCollideTimeField() {
		if (isInstanceNode() == false)
			return collideTimeField;
		return (SFTime)getEventOut(collideTimeEventOut);
	}

	public void setCollideTime(double value) {
		getCollideTimeField().setValue(value);
	}

	public void setCollideTime(String value) {
		getCollideTimeField().setValue(value);
	}

	public double getCollideTime() {
		return getCollideTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isCommonNode() || node.isBindableNode() ||node.isInterpolatorNode() || node.isSensorNode() || node.isGroupingNode() || node.isSpecialGroupNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();
		updateChildrenField();
		updateBoundingBox();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateChildrenField();
		//updateBoundingBox();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool collide = getCollideField();
		printStream.println(indentString + "\t" + "collide " + collide);
	}
}
