/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Constants.java
*
******************************************************************/

package org.cybergarage.x3d;

public interface Constants 
{
  static public final int X = 0;
  static public final int Y = 1;
  static public final int Z = 2;
  static public final int W = 3;
  static public final int R = 0;
  static public final int G = 1;
  static public final int B = 2;

  static public final int RENDERINGMODE_LINE	= 0;
  static public final int RENDERINGMODE_FILL	= 1;
  
  static public final String rootTypeName							= "Root";
  static public final String appearanceTypeName					= "Appearance";
  static public final String viewpointTypeName					= "Viewpoint";
  static public final String transformTypeName					= "Transform";
  static public final String textureCoordinateTypeName		= "TextureCoordinate";
  static public final String spotLightTypeName					= "SpotLight";
  static public final String shapeTypeName						= "Shape";
  static public final String pointLightTypeName					= "PointLight";
  static public final String normalTypeName						= "Normal";
  static public final String materialTypeName					= "Material";
  static public final String indexedFaceSetTypeName			= "IndexedFaceSet";
  static public final String groupTypeName						= "Group";
  static public final String billboardTypeName					= "Billboard";
  static public final String collisionTypeName					= "Collision";
  static public final String lodTypeName							= "LOD";
  static public final String switchTypeName						= "Switch";
  static public final String inlineTypeName						= "Inline";
  static public final String directionalLightTypeName			= "DirectionalLight";
  static public final String coordinateTypeName					= "Coordinate";
  static public final String colorTypeName						= "Color";
  static public final String worldInfoTypeName					= "WorldInfo";
  static public final String timeSensorTypeName					= "TimeSensor";
  static public final String scalarInterpolatorTypeName		= "ScalarInterpolator";
  static public final String positionInterpolatorTypeName	= "PositionInterpolator";
  static public final String coordinateInterpolatorTypeName	= "CoordinateInterpolator";
  static public final String orientationInterpolatorTypeName= "OrientationInterpolator";
  static public final String normalInterpolatorTypeName		= "NormalInterpolator";
  static public final String colorInterpolatorTypeName		= "ColorInterpolator";
  static public final String scriptTypeName						= "Script";
  static public final String cylinderSensorTypeName			= "CylinderSensor";
  static public final String cylinderTypeName					= "Cylinder";
  static public final String anchorTypeName						= "Anchor";
  static public final String audioClipTypeName					= "AudioClip";
  static public final String backgroundTypeName					= "Background";
  static public final String boxTypeName							= "Box";
  static public final String coneTypeName							= "Cone";
  static public final String elevationGridTypeName				= "ElevationGrid";
  static public final String extrusionTypeName					= "Extrusion";
  static public final String fogTypeName							= "Fog";
  static public final String fontStyleTypeName					= "FontStyle";
  static public final String imageTextureTypeName				= "ImageTexture";
  static public final String indexedLineSetTypeName			= "IndexedLineSet";
  static public final String movieTextureTypeName				= "MovieTexture";
  static public final String navigationInfoTypeName			= "NavigationInfo";
  static public final String pixelTextureTypeName				= "PixelTexture";
  static public final String pointSetTypeName					= "PointSet";
  static public final String soundTypeName						= "Sound";
  static public final String sphereTypeName						= "Sphere";
  static public final String textTypeName							= "Text";
  static public final String planeSensorTypeName				= "PlaneSensor";
  static public final String proximitySensorTypeName			= "ProximitySensor";
  static public final String sphereSensorTypeName				= "SphereSensor";
  static public final String touchSensorTypeName				= "TouchSensor";
  static public final String visibilitySensorTypeName			= "VisibilitySensor";
  static public final String textureTransformTypeName			= "TextureTransform";
  static public final String proxyTypeName						= "Proxy";
  static public final String genericXMLNodeTypeName			= "XML";

  static public final String instanceTypeName					= "Instance";

  static public final String eventInStripString			= "set_";
  static public final String eventOutStripString		= "_changed";

  static public final String isActiveFieldName			= "isActive";
  static public final String fractionFieldName			= "fraction";
  static public final String durationFieldName			= "duration";
  static public final String valueFieldName				= "value";

  static public final String	fieldTypeSFBoolName				= "SFBool";
  static public final String	fieldTypeSFColorName				= "SFColor";
  static public final String	fieldTypeSFFloatName				= "SFFloat";
  static public final String	fieldTypeSFInt32Name				= "SFInt32";
  static public final String	fieldTypeSFRotationName			= "SFRotation";
  static public final String	fieldTypeSFStringName			= "SFString";
  static public final String	fieldTypeSFTimeName				= "SFTime";
  static public final String	fieldTypeSFVec2fName			= "SFVec2f";
  static public final String	fieldTypeSFVec3fName			= "SFVec3f";
  static public final String	fieldTypeSFNodeName				= "SFNode";
  static public final String	fieldTypeSFImageName			= "SFImage";
  static public final String	fieldTypeMFBoolName				= "MFBool";
  static public final String	fieldTypeMFColorName			= "MFColor";
  static public final String	fieldTypeMFFloatName				= "MFFloat";
  static public final String	fieldTypeMFInt32Name			= "MFInt32";
  static public final String	fieldTypeMFRotationName			= "MFRotation";
  static public final String	fieldTypeMFStringName			= "MFString";
  static public final String	fieldTypeMFTimeName				= "MFTime";
  static public final String	fieldTypeMFVec2fName			= "MFVec2f";
  static public final String	fieldTypeMFVec3fName			= "MFVec3f";
  static public final String	fieldTypeMFNodeName			= "MFNode";
  static public final String	fieldTypeMFImageName			= "MFImage";
  
  static public final String	fieldTypeXMLElementName		= "XML";
  
  static public final String	fieldTypeSFCharName				= "SFChar";
  static public final String	fieldTypeMFCharName				= "MFChar";
  static public final String	fieldTypeSFDoubleName			= "SFDouble";
  static public final String	fieldTypeMFDoubleName			= "MFDouble";
  static public final String	fieldTypeSFVec2dName			= "SFVec2d";
  static public final String	fieldTypeMFVec2dName			= "MFVec2d";
  static public final String	fieldTypeSFVec3dName			= "SFVec3d";
  static public final String	fieldTypeMFVec3dName			= "MFVec3d";

  static public final String	fieldTypeSFColorRGBAName		= "SFColorRGBA";
  static public final String	fieldTypeMFColorRGBAName		= "MFColorRGBA";

}
