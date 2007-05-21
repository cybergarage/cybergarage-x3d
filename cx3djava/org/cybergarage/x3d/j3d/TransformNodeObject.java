/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : TransformNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;

import org.cybergarage.x3d.node.*;

public class TransformNodeObject extends TransformGroup implements NodeObject {

	public TransformNodeObject() {
		setCapability(ALLOW_CHILDREN_READ);
		setCapability(ALLOW_CHILDREN_WRITE);
		setCapability(ALLOW_TRANSFORM_READ);
		setCapability(ALLOW_TRANSFORM_WRITE);
	}

	public TransformNodeObject(TransformNode node) {
		this();
		initialize(node);
	}
	
	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		node.setRunnable(true);
		node.setRunnableType(org.cybergarage.x3d.node.Node.RUNNABLE_TYPE_ALWAYS);
		update(node);
		return true;
	}

	public boolean uninitialize(org.cybergarage.x3d.node.Node node) {
		return true;
	}
		
	private float translation[]			= new float[3];
	private float rotation[]				= new float[4];
	private float scale[]					= new float[3];
	private float center[]					= new float[3];
	private float scaleOrientation[]	= new float[4];

	private Vector3f		vector			= new Vector3f();
	private Vector3d		factor			= new Vector3d();
	private AxisAngle4f	axisAngle		= new AxisAngle4f();
	
	private Transform3D trans3D 						= new Transform3D();
	private Transform3D minusCenterTrans3D		= new Transform3D();
	private Transform3D minusScaleOriTrans3D	= new Transform3D();
	private Transform3D scaleTrans3D					= new Transform3D();
	private Transform3D scaleOriTrans3D			= new Transform3D();
	private Transform3D rotationTrans3D			= new Transform3D();
	private Transform3D centerTrans3D				= new Transform3D();
	private Transform3D translationTrans3D		= new Transform3D();
		
	public boolean update(org.cybergarage.x3d.node.Node node) {
		TransformNode transNode = (TransformNode)node;
		
		transNode.getTranslation(translation);	
		transNode.getScale(scale);
		transNode.getCenter(center);
		transNode.getRotation(rotation);
		transNode.getScaleOrientation(scaleOrientation);
		
		trans3D.setIdentity();

		// Transform T
		translationTrans3D.setIdentity();
		vector.x = translation[0];
		vector.y = translation[1];
		vector.z = translation[2];
		translationTrans3D.set(vector);
		trans3D.mul(translationTrans3D);
		
		// Transform C
		centerTrans3D.setIdentity();
		vector.x = center[0];
		vector.y = center[1];
		vector.z = center[2];
		centerTrans3D.set(vector);
		trans3D.mul(centerTrans3D);
		
		// Transform R
		rotationTrans3D.setIdentity();
		axisAngle.x 			= rotation[0];
		axisAngle.y 			= rotation[1];
		axisAngle.z 			= rotation[2];
		axisAngle.angle	= rotation[3];
		rotationTrans3D.set(axisAngle);
		trans3D.mul(rotationTrans3D);
		
		// Transform SR
		scaleOriTrans3D.setIdentity();
		axisAngle.x 			= scaleOrientation[0];
		axisAngle.y 			= scaleOrientation[1];
		axisAngle.z 			= scaleOrientation[2];
		axisAngle.angle	= scaleOrientation[3];
		scaleOriTrans3D.set(axisAngle);
		trans3D.mul(scaleOriTrans3D);
		
		// Transform S
		scaleTrans3D.setIdentity();
		factor.x = scale[0];
		factor.y = scale[1];
		factor.z = scale[2];
		scaleTrans3D.setScale(factor);
		
		// Transform -SR
		minusScaleOriTrans3D.setIdentity();
		axisAngle.x 			= scaleOrientation[0];
		axisAngle.y 			= scaleOrientation[1];
		axisAngle.z 			= scaleOrientation[2];
		axisAngle.angle	= -scaleOrientation[3];
		minusScaleOriTrans3D.set(axisAngle);
		trans3D.mul(minusScaleOriTrans3D);
		
		// Transform -C
		minusCenterTrans3D.setIdentity();
		vector.x = -center[0];
		vector.y = -center[1];
		vector.z = -center[2];
		minusCenterTrans3D.set(vector);
		trans3D.mul(minusCenterTrans3D);

		trans3D.mul(scaleTrans3D);

		try {
			setTransform(trans3D);
		}
		catch (BadTransformException bte) {
		}
			
		return true;
	}

	public Group getParentGroup(org.cybergarage.x3d.node.Node node) {
		org.cybergarage.x3d.node.Node	parentNode		= node.getParentNode();
		Group			parentGroupNode	= null;
		if (parentNode != null) {
			NodeObject parentNodeObject  = parentNode.getObject();
			if (parentNodeObject != null)
				parentGroupNode = (Group)parentNodeObject;
		}
		else {
			org.cybergarage.x3d.SceneGraph sg = node.getSceneGraph();
			if (sg != null) {
				SceneGraphJ3dObject	sgObject = (SceneGraphJ3dObject)sg.getObject();
				if (sgObject != null)
					parentGroupNode = sgObject.getRootNode();
			}
		}
		return parentGroupNode;
	}
	
	public boolean add(org.cybergarage.x3d.node.Node node) {
		if (getParent() == null) {
			Group parentGroupNode = getParentGroup(node);
			if (parentGroupNode != null) {
					parentGroupNode.addChild(this);
			}
		}
		return true;
	}

	public boolean remove(org.cybergarage.x3d.node.Node node) {
		Group parentGroupNode = getParentGroup(node);
		if (parentGroupNode != null) {
			for (int n=0; n<parentGroupNode.numChildren(); n++) {
				if (parentGroupNode.getChild(n) == this) {
					parentGroupNode.removeChild(n);
					return true;
				}
			}
		}
		return false;
	}
}
