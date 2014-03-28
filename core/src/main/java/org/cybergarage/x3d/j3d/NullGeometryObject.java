/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : NullGeometryObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import javax.media.j3d.*;

public class NullGeometryObject extends Text3D {
	public NullGeometryObject() {
		super();
		setCapability(ALLOW_INTERSECT);
	}
}

/*
public class NullGeometryObject extends PointArray {
	public NullGeometryObject() {
		super(1, COORDINATES | COLOR_4);

		float point[] = new float[3];
		point[0] = 0.0f;		
		point[1] = 0.0f;		
		point[2] = 0.0f;		
		setCoordinate(0, point);

		float color[] = new float[4];
		color[0] = 0.0f;		
		color[1] = 0.0f;		
		color[2] = 0.0f;		
		color[3] = 0.0f;		
		setColor(0, color);

		float color[] = new float[4];
		color[0] = 0.0f;		
		color[1] = 0.0f;		
		color[2] = 0.0f;		
		color[3] = 0.0f;		
		setColor(0, color);
	}
}
*/