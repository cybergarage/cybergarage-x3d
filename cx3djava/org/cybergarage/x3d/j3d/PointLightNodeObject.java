/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : PointLightNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import org.cybergarage.x3d.node.*;

public class PointLightNodeObject extends PointLight implements NodeObject {

	public PointLightNodeObject(PointLightNode node) {
		setCapability(ALLOW_COLOR_READ);
		setCapability(ALLOW_COLOR_WRITE);
		setCapability(ALLOW_INFLUENCING_BOUNDS_READ);
		setCapability(ALLOW_INFLUENCING_BOUNDS_WRITE);
		setCapability(ALLOW_STATE_READ);
		setCapability(ALLOW_STATE_WRITE);
		setCapability(ALLOW_ATTENUATION_READ);
		setCapability(ALLOW_ATTENUATION_WRITE);
		setCapability(ALLOW_POSITION_READ);
		setCapability(ALLOW_POSITION_WRITE);
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
			
	public boolean update(org.cybergarage.x3d.node.Node node) {
		PointLightNode light = (PointLightNode)node;
		
		setEnable(light.isOn());
		
		float color[] = new float[3];
		light.getColor(color);
		float intensity = light.getIntensity();
		color[0] *= intensity;
		color[1] *= intensity;
		color[2] *= intensity;
		Color3f color3f = new Color3f(color);
		setColor(color3f);

		float pos[] = new float[3];
		light.getLocation(pos);
		setPosition(pos[0], pos[1], pos[2]);
		
		float attenuation[] = new float[3];
		light.getAttenuation(attenuation);
		setAttenuation(attenuation[0], attenuation[1], attenuation[2]);

		setInfluencingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), light.getRadius()));
		 		 
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
