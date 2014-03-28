/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : VRML97ParserType.java
*
******************************************************************/
package org.cybergarage.x3d.parser.vrml97;

public interface VRML97ParserType {

  static public final int parserTypeNone = 0;
  static public final int parserTypeTransform = 1;
  static public final int parserTypeGroup = 2;
  static public final int parserTypeShape = 3;
  static public final int parserTypeIdxFaceSet = 4;
  static public final int parserTypeIdxFaceSetCoordIndex = 5;
  static public final int parserTypeIdxFaceSetTexCoordIndex = 6;
  static public final int parserTypeIdxFaceSetColorIndex = 7;
  static public final int parserTypeIdxFaceSetNormalIndex = 8;
  static public final int parserTypeCoordinate = 9;
  static public final int parserTypeNormal = 10;
  static public final int parserTypeColor = 11;
  static public final int parserTypeTextureCoordinate = 12;
  static public final int parserTypeViewpoint = 13;
  static public final int parserTypeWorldInfo = 14;
  static public final int parserTypeWorldInfoInfo = 15;
  static public final int parserTypeDirectionalLight = 16;
  static public final int parserTypePointLight = 17;
  static public final int parserTypeSpotLight = 18;
  static public final int parserTypeAppearance = 19;
  static public final int parserTypeMaterial = 20;
  static public final int parserTypeSwitch = 22;
  static public final int parserTypeAnchor = 24;
  static public final int parserTypeCollision = 25;
  static public final int parserTypeBillboard = 26;
  static public final int parserTypeTimeSensor = 27;
  static public final int parserTypeScalarInterpolator = 28;
  static public final int parserTypePositionInterpolator = 29;
  static public final int parserTypeCoordinateInterpolator = 30;
  static public final int parserTypeOrientationInterpolator = 31;
  static public final int parserTypeNormalInterpolator = 32;
  static public final int parserTypeColorInterpolator = 33;
  static public final int parserTypeScalarInterpolatorKey = 34;
  static public final int parserTypeScalarInterpolatorKeyValue = 35;
  static public final int parserTypePositionInterpolatorKey = 36;
  static public final int parserTypePositionInterpolatorKeyValue = 37;
  static public final int parserTypeCoordinateInterpolatorKey = 38;
  static public final int parserTypeCoordinateInterpolatorKeyValue = 39;
  static public final int parserTypeOrientationInterpolatorKey = 40;
  static public final int parserTypeOrientationInterpolatorKeyValue = 41;
  static public final int parserTypeNormalInterpolatorKey = 42;
  static public final int parserTypeNormalInterpolatorKeyValue = 43;
  static public final int parserTypeColorInterpolatorKey = 44;
  static public final int parserTypeColorInterpolatorKeyValue = 45;
  static public final int parserTypeScript = 46;
  static public final int parserTypeScriptURL = 47;
  static public final int parserTypeCylinderSensor = 48;
  static public final int parserTypePlaneSensor = 49;
  static public final int parserTypeProximitySensor = 50;
  static public final int parserTypeSphereSensor = 51;
  static public final int parserTypeTouchSensor = 52;
  static public final int parserTypeVisibilitySensor = 53;
  static public final int parserTypeAnchorURL = 54;
  static public final int parserTypeAnchorParameter = 55;
  static public final int parserTypeInline = 56;
  static public final int parserTypeInlineURL = 57;
  static public final int parserTypeLOD= 58;
  static public final int parserTypeLODRange = 59;
  static public final int parserTypeAudioClip = 60;
  static public final int parserTypeAudioClipURL = 61;
  static public final int parserTypeElevationGrid = 62;
  static public final int parserTypeElevationGridHeight = 63;
  static public final int parserTypeSound = 64;
  static public final int parserTypeBox = 65;
  static public final int parserTypeCone = 66;
  static public final int parserTypeCylinder = 67;
  static public final int parserTypeSphere = 68;
  static public final int parserTypePointSet = 69;
  static public final int parserTypeExtrusion = 70;
  static public final int parserTypeExtrusionCrossSection = 71;
  static public final int parserTypeExtrusionOrientation = 72;
  static public final int parserTypeExtrusionScale = 73;
  static public final int parserTypeExtrusionSpine = 74;
  static public final int parserTypeIdxLineSet = 75;
  static public final int parserTypeIdxLineSetCoordIndex = 76;
  static public final int parserTypeIdxLineSetColorIndex = 77;
  static public final int parserTypeText = 78;
  static public final int parserTypeTextString = 79;
  static public final int parserTypeTextLength = 80;
  static public final int parserTypeFontStyle = 81;
  static public final int parserTypeFontStyleJustify = 82;
  static public final int parserTypeTextureTransform = 83;
  static public final int parserTypeImageTexture = 84;
  static public final int parserTypeImageTextureURL = 85;
  static public final int parserTypeMovieTexture = 86;
  static public final int parserTypeMovieTextureURL = 87;
  static public final int parserTypePixelTexture = 88;
  static public final int parserTypePixelTextureImage = 89;
  static public final int parserTypeBackground = 90;
  static public final int parserTypeBackgroundBackURL = 91;
  static public final int parserTypeBackgroundBottomURL = 92;
  static public final int parserTypeBackgroundLeftURL = 93;
  static public final int parserTypeBackgroundFrontURL = 94;
  static public final int parserTypeBackgroundRightURL = 95;
  static public final int parserTypeBackgroundTopURL = 96;
  static public final int parserTypeBackgroundGroundAngle = 97;
  static public final int parserTypeBackgroundSkyAngle = 98;
  static public final int parserTypeBackgroundGroundColor = 99;
  static public final int parserTypeBackgroundSkyColor = 100;
  static public final int parserTypeFog = 101;
  static public final int parserTypeNavigationInfo = 102;
  static public final int parserTypeNavigationInfoType = 103;
  static public final int parserTypeNavigationInfoAvatarSize = 104;
  static public final int parserTypeFontStyleFamily = 105;
}
