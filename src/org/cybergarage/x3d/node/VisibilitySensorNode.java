/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : VisibilitySensorNode.java
*
*	Revisions:
*
*	12/08/02
*		- Changed the super class from SensorNode to EnvironmentalSensorNode.
*		- Moved the following fields to EnvironmentalSensorNode.
*			center, size, enterTime, exitTime
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class VisibilitySensorNode extends EnvironmentalSensorNode
{
	public VisibilitySensorNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.VISIBILITYSENSOR);
	}

	public VisibilitySensorNode(VisibilitySensorNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node)
	{
		return false;
	}

	public void initialize() 
	{
	}

	public void uninitialize() 
	{
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
		SFBool enabled = getEnabledField();
		SFVec3f center = getCenterField();
		SFVec3f size = getSizeField();

		printStream.println(indentString + "\t" + "enabled " + enabled );
		printStream.println(indentString + "\t" + "center " + center );
		printStream.println(indentString + "\t" + "size " + size );
	}
}
