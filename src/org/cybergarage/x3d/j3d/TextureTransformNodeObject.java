/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : TextureTransformNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;

import org.cybergarage.x3d.node.*;

public class TextureTransformNodeObject extends TextureAttributes implements NodeObject {
	
	public TextureTransformNodeObject(TextureTransformNode node) {
		setCapability(TextureAttributes.ALLOW_TRANSFORM_READ);
		setCapability(TextureAttributes.ALLOW_TRANSFORM_WRITE);
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
			
	private float scale[]				= new float[2];
	private float center[]				= new float[2];
	private float rotation;
	private float translation[]		= new float[2];

	private Vector3f		vector			= new Vector3f();
	private Vector3d		factor			= new Vector3d();
	private AxisAngle4f	axisAngle		= new AxisAngle4f();
	
	private Transform3D		trans3D						= new Transform3D();
	private Transform3D		translationTrans3D	= new Transform3D();
	private Transform3D		centerTrans3D			= new Transform3D();
	private Transform3D		rotationrTrans3D		= new Transform3D();
	private Transform3D		scaleTrans3D				= new Transform3D();
	private Transform3D		minusCenterTrans3D	= new Transform3D();
	
	private Transform3D transform 	= new Transform3D();
	
	public boolean update(org.cybergarage.x3d.node.Node node) {
		TextureTransformNode	texTransNode = (TextureTransformNode)node;
		
		texTransNode.getScale(scale);
		texTransNode.getCenter(center);
		texTransNode.getTranslation(translation);
		rotation = texTransNode.getRotation();

		trans3D.setIdentity();
		
		// Transform T
		translationTrans3D.setIdentity();
		vector.x = translation[0];
		vector.y = translation[1];
		vector.z = 0.0f;
		translationTrans3D.set(vector);
		trans3D.mul(translationTrans3D);
		
		// Transform C
		centerTrans3D.setIdentity();
		vector.x = center[0];
		vector.y = center[1];
		vector.z = 0.0f;
		centerTrans3D.set(vector);
		trans3D.mul(centerTrans3D);

		// Transform R
		rotationrTrans3D.setIdentity();
		axisAngle.x 			= 0.0f;
		axisAngle.y 			= 0.0f;
		axisAngle.z 			= 1.0f;
		axisAngle.angle	= rotation;
		rotationrTrans3D.set(axisAngle);
		trans3D.mul(rotationrTrans3D);

		// Transform C
		scaleTrans3D.setIdentity();
		factor.x = scale[0];
		factor.y = scale[1];
		factor.z = 1.0f;
		scaleTrans3D.set(factor);
		trans3D.mul(scaleTrans3D);
		
		// Transform -C
		centerTrans3D.setIdentity();
		vector.x = -center[0];
		vector.y = -center[1];
		vector.z = 0.0f;
		centerTrans3D.set(vector);
		trans3D.mul(centerTrans3D);

		try {
			setTextureTransform(trans3D);
		}
		catch (BadTransformException bte) {
		}
		
		return true;
	}
	
	public boolean add(org.cybergarage.x3d.node.Node node) {

		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isAppearanceNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Appearance parentAppearanceNode = (Appearance)parentNodeObject;
					parentAppearanceNode.setTextureAttributes(this);
				}
			}
		}
		
		return true;
	}

	public boolean remove(org.cybergarage.x3d.node.Node node) {
	
		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isAppearanceNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Appearance parentAppearanceNode = (Appearance)parentNodeObject;
					parentAppearanceNode.setTextureAttributes(null);
				}
			}
		}
		
		return true;
	}
}
