/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : LODNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.util.*;

public class LODNodeObject extends SwitchNodeObject implements NodeObject {

	private SFMatrix	viewMatrix		= new SFMatrix();
	private float		viewPosition[]	= new float[3];

	private SFMatrix	lodMatrix		= new SFMatrix();
	private float		lodCenter[]		= new float[3];
		
	public LODNodeObject(LODNode node) {
	}

	public boolean update(org.cybergarage.x3d.node.Node node) {
		SceneGraph sg = node.getSceneGraph();
		if (sg == null)
			return false;

		ViewpointNode view = sg.getViewpointNode();
		if (view == null)
			view = sg.getDefaultViewpointNode();
			
		LODNode LODNode = (LODNode)node;

		view.getTransformMatrix(viewMatrix);
		view.getPosition(viewPosition);
		viewMatrix.multi(viewPosition);

		LODNode.getTransformMatrix(lodMatrix);
		LODNode.getCenter(lodCenter);
		lodMatrix.multi(lodCenter);

		float distance = Geometry3D.distance(viewPosition, lodCenter);
		
		int numRanges = LODNode.getNRanges();
		int nRange;
		for (nRange=0; nRange<numRanges; nRange++) {
			if (distance < LODNode.getRange(nRange))
				break;
		}

		if (nRange == getWhichChild())
			return true;

		int numChildren = numChildren();
		
		if (nRange < numChildren)
			setWhichChild(nRange);
		else
			setWhichChild(numChildren - 1);
	
		return true;
	}
	
}
