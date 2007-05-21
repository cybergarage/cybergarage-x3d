/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File : FieldType.java
*
******************************************************************/

package org.cybergarage.x3d;

public class FieldType implements Constants 
{
	private static int fieldTypeCnt = 0;
	private final int nodeNum = fieldTypeCnt++;
	
	private String name;
	
	private FieldType(String name)
	{
		this.name = name;
	}

	public boolean equals(String value)
	{
		if (name.compareTo(value) == 0)
			return true;
		return false;
	}

	public String toString()
	{
		return name;
	}
	
	public int toInteger()
	{
		return nodeNum;
	}

	public static final FieldType NONE = new FieldType("");
	
	public static final FieldType SFBOOL = new FieldType(fieldTypeSFBoolName);
	public static final FieldType SFCOLOR = new FieldType(fieldTypeSFColorName);
	public static final FieldType SFFLOAT = new FieldType(fieldTypeSFFloatName);
	public static final FieldType SFINT32 = new FieldType(fieldTypeSFInt32Name);
	public static final FieldType SFROTATION = new FieldType(fieldTypeSFRotationName);
	public static final FieldType SFSTRING = new FieldType(fieldTypeSFStringName);
	public static final FieldType SFTIME = new FieldType(fieldTypeSFTimeName);
	public static final FieldType SFVEC2F = new FieldType(fieldTypeSFVec2fName);
	public static final FieldType SFVEC3F = new FieldType(fieldTypeSFVec3fName);
	public static final FieldType SFNODE = new FieldType(fieldTypeSFNodeName);
	public static final FieldType SFIMAGE = new FieldType(fieldTypeSFImageName);

	public static final FieldType MFBOOL = new FieldType(fieldTypeMFBoolName);
	public static final FieldType MFCOLOR = new FieldType(fieldTypeMFColorName);
	public static final FieldType MFFLOAT = new FieldType(fieldTypeMFFloatName);
	public static final FieldType MFINT32 = new FieldType(fieldTypeMFInt32Name);
	public static final FieldType MFROTATION = new FieldType(fieldTypeMFRotationName);
	public static final FieldType MFSTRING = new FieldType(fieldTypeMFStringName);
	public static final FieldType MFTIME = new FieldType(fieldTypeMFTimeName);
	public static final FieldType MFVEC2F = new FieldType(fieldTypeMFVec2fName);
	public static final FieldType MFVEC3F = new FieldType(fieldTypeMFVec3fName);
	public static final FieldType MFNODE = new FieldType(fieldTypeMFNodeName);
	public static final FieldType MFIMAGE = new FieldType(fieldTypeMFImageName);
	
	public static final FieldType XML = new FieldType(fieldTypeXMLElementName);

	public static final FieldType SFDOUBLE = new FieldType(fieldTypeSFDoubleName);
	public static final FieldType MFDOUBLE = new FieldType(fieldTypeMFDoubleName);
	public static final FieldType SFCHAR = new FieldType(fieldTypeSFCharName);
	public static final FieldType MFCHAR = new FieldType(fieldTypeMFCharName);
	public static final FieldType SFVEC2D = new FieldType(fieldTypeSFVec2dName);
	public static final FieldType MFVEC2D = new FieldType(fieldTypeMFVec2dName);
	public static final FieldType SFVEC3D = new FieldType(fieldTypeSFVec3dName);
	public static final FieldType MFVEC3D = new FieldType(fieldTypeMFVec3dName);

	public static final FieldType SFCOLORRGBA = new FieldType(fieldTypeSFColorRGBAName);
	public static final FieldType MFCOLORRGBA = new FieldType(fieldTypeMFColorRGBAName);
	
}
