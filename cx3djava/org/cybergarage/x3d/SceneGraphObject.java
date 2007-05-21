/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SceneGraphObject.java
*
******************************************************************/

package org.cybergarage.x3d;

import org.cybergarage.x3d.node.*;

public interface SceneGraphObject 
{
	public boolean		initialize(SceneGraph sg);
	public boolean		uninitialize(SceneGraph sg);

	public NodeObject	createNodeObject(SceneGraph sg, org.cybergarage.x3d.node.Node node);
	public boolean		addNode(SceneGraph sg, Node node);
	public boolean		removeNode(SceneGraph sg, Node node);
	
	public boolean		update(SceneGraph sg);
	public boolean		remove(SceneGraph sg);

	public boolean		start(SceneGraph sg);
	public boolean		stop(SceneGraph sg);
	
	public boolean		setRenderingMode(SceneGraph sg, int mode);

	public void			print(SceneGraph sg);
}
