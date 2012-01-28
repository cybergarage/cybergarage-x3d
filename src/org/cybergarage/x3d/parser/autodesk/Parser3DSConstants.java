/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1999
*
*	File : Parser3DSConstants.java
*
******************************************************************/

package org.cybergarage.x3d.parser.autodesk;

public interface Parser3DSConstants {
	static final int ID_3DS_FILE 				= 0x4D4D;
	static final int ID_EDIT3DS 				= 0x3D3D;
	
	static final int ID_MATERIAL 						= 0xAFFF;
	static final int ID_MATERIAL_NAME				= 0xA000;
	static final int ID_MATERIAL_AMBIENT 			= 0xA010;
	static final int ID_MATERIAL_DIFFUSE 			= 0xA020;
	static final int ID_MATERIAL_SPECULAR 		= 0xA030;
	static final int ID_MATERIAL_SHININESS 		= 0xA040;
	static final int ID_MAT_SHININESS_STRENGTH	= 0xA041;
	static final int ID_MATERIAL_TRANSPARENCY 	= 0xA050;

	static final int ID_NAMED_OBJECT				= 0x4000;
	static final int ID_TRIANGLE_SET				= 0x4100;
	static final int ID_POINT_LIST       		= 0x4110;
	static final int ID_FACE_LIST       		= 0x4120;
	static final int ID_FACE_MATERIAL		  = 0x4130;
	static final int ID_SMOOTH_GROUP     		= 0x4150;
	static final int ID_MESH_MATRIX      		= 0x4160;

	static final int ID_COLOR_FLOAT			= 0x0010;
	static final int ID_COLOR_24				= 0x0011;
	static final int ID_COLOR_LIN_24			= 0x0012;
	static final int ID_COLOR_LIN_FLOAT	= 0x0013;
	
	static final int ID_INT_PERCENTAGE		=	0x0030;
	static final int ID_FLOAT_PERCENTAGE	= 0x0031;
	
}
