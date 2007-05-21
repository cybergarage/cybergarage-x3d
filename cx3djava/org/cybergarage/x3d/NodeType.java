/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File : NodeType.java
*
******************************************************************/

package org.cybergarage.x3d;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.xml.*;

public class NodeType implements Constants 
{
	private static int nodeTypeCnt = 0;
	private final int nodeNum = nodeTypeCnt++;
	
	private String name;
	
	private NodeType(String name)
	{
		this.name = name;
	}

	public boolean equals(Object obj) 
	{
		if (obj instanceof String) {
			if (name.compareTo((String)obj) == 0)
				return true;
			return false;
		}
		return super.equals(obj);
	}
	
	public String toString()
	{
		return name;
	}
	
	public int toInteger()
	{
		return nodeNum;
	}

	public static final NodeType ROOT = new NodeType(rootTypeName);
	
	public static final NodeType ANCHOR = new NodeType(anchorTypeName);
	public static final NodeType APPEARANCE = new NodeType(appearanceTypeName);
	public static final NodeType AUDIOCLIP = new NodeType(audioClipTypeName);
	public static final NodeType BACKGROUND = new NodeType(backgroundTypeName);
	public static final NodeType BILLBOARD = new NodeType(billboardTypeName);
	public static final NodeType BOX = new NodeType(boxTypeName);
	public static final NodeType COLLISION = new NodeType(collisionTypeName);
	public static final NodeType COLOR = new NodeType(colorTypeName);
	public static final NodeType COLORINTERP = new NodeType(colorInterpolatorTypeName);
	public static final NodeType CONE = new NodeType(coneTypeName);
	public static final NodeType COORD = new NodeType(coordinateTypeName);
	public static final NodeType COORDINTERP = new NodeType(coordinateInterpolatorTypeName);
	public static final NodeType CYLINDER = new NodeType(cylinderTypeName);
	public static final NodeType CYLINDERSENSOR = new NodeType(cylinderSensorTypeName);
	public static final NodeType DIRLIGHT = new NodeType(directionalLightTypeName);
	public static final NodeType ELEVATIONGRID = new NodeType(elevationGridTypeName);
	public static final NodeType EXTRUSION = new NodeType(extrusionTypeName);
	public static final NodeType FOG = new NodeType(fogTypeName);
	public static final NodeType FONTSTYLE = new NodeType(fontStyleTypeName);
	public static final NodeType GROUP = new NodeType(groupTypeName);
	public static final NodeType IMAGETEXTURE = new NodeType(imageTextureTypeName);
	public static final NodeType INDEXEDFACESET = new NodeType(indexedFaceSetTypeName);
	public static final NodeType INDEXEDLINESET = new NodeType(indexedLineSetTypeName);
	public static final NodeType INLINE = new NodeType(inlineTypeName);
	public static final NodeType LOD = new NodeType(lodTypeName);
	public static final NodeType MATERIAL = new NodeType(materialTypeName);
	public static final NodeType MOVIETEXTURE = new NodeType(movieTextureTypeName);
	public static final NodeType NAVIGATIONINFO = new NodeType(navigationInfoTypeName);
	public static final NodeType NORMAL = new NodeType(normalTypeName);
	public static final NodeType NORMALINTERP = new NodeType(normalInterpolatorTypeName);
	public static final NodeType ORIENTATIONINTERP = new NodeType(orientationInterpolatorTypeName);
	public static final NodeType PIXELTEXTURE = new NodeType(pixelTextureTypeName);
	public static final NodeType PLANESENSOR = new NodeType(planeSensorTypeName);
	public static final NodeType POINTLIGHT = new NodeType(pointLightTypeName	);
	public static final NodeType POINTSET = new NodeType(pointSetTypeName);
	public static final NodeType POSITONINTERP = new NodeType(positionInterpolatorTypeName);
	public static final NodeType PROXIMITYSENSOR = new NodeType(proximitySensorTypeName);
	public static final NodeType PROXY = new NodeType(proxyTypeName);
	public static final NodeType SCALARINTERP = new NodeType(scalarInterpolatorTypeName);
	public static final NodeType SCRIPT = new NodeType(scriptTypeName);
	public static final NodeType SHAPE = new NodeType(shapeTypeName);
	public static final NodeType SOUND = new NodeType(soundTypeName);
	public static final NodeType SPHERE = new NodeType(sphereTypeName);
	public static final NodeType SPHERESENSOR = new NodeType(sphereSensorTypeName);
	public static final NodeType SPOTLIGHT = new NodeType(spotLightTypeName);
	public static final NodeType SWITCH = new NodeType(switchTypeName);
	public static final NodeType TEXT = new NodeType(textTypeName);
	public static final NodeType TEXTURECOORD = new NodeType(textureCoordinateTypeName);
	public static final NodeType TEXTURETRANSFORM = new NodeType(textureTransformTypeName);
	public static final NodeType TIMESENSOR = new NodeType(timeSensorTypeName);
	public static final NodeType TOUCHSENSOR = new NodeType(touchSensorTypeName);
	public static final NodeType TRANSFORM = new NodeType(transformTypeName);
	public static final NodeType VIEWPOINT = new NodeType(viewpointTypeName);
	public static final NodeType VISIBILITYSENSOR = new NodeType(visibilitySensorTypeName);
	public static final NodeType WORLDINFO = new NodeType(worldInfoTypeName);
	
	public static final NodeType XML = new NodeType(genericXMLNodeTypeName);

	// 9. Networking component (X3D)
	public static final NodeType LOADSENSOR  = new NodeType("LoadSensor");
	
	// 10. Grouping component (X3D)
	public static final NodeType STATICGROUP  = new NodeType("StaticGroup");

	// 11. Rendering component (X3D)
	public static final NodeType COLORRGBA  = new NodeType("ColorRGBA");
	public static final NodeType TRIANGLEFANSET = new NodeType("TriangleFanSet");
	public static final NodeType TRIANGLESET = new NodeType("TriangleSet");
	public static final NodeType TRIANGLESTRIPSET = new NodeType("TriangleStripSet");

	// 12. Shape component (X3D)
	public static final NodeType FILLPROPERTIES = new NodeType("FillProperties");
	public static final NodeType LINEPROPERTIES = new NodeType("LineProperties");

	// 14. Geometry2D component (X3D)
	public static final NodeType ARC2D = new NodeType("Arc2D");
	public static final NodeType ARCCLOSE2D = new NodeType("ArcClose2D");
	public static final NodeType CIRCLE2D = new NodeType("Circle2D");
	public static final NodeType DISK2D = new NodeType("Disk2D");
	public static final NodeType POLYLINE2D = new NodeType("Polyline2D");
	public static final NodeType POLYPOINT2D = new NodeType("Polypoint2D");
	public static final NodeType RECTANGLE2D = new NodeType("Rectangle2D");
	public static final NodeType TRIANGLESET2D = new NodeType("TriangleSet2D");

	// 18. Texturing component (x3D)
	public static final NodeType MULTITEXTURE = new NodeType("MultiTexture");
	public static final NodeType MULTITEXTURECOORD = new NodeType("MultiTextureCoordinate");
	public static final NodeType MULTITEXTURETRANSFORM = new NodeType("MultiTextureTransform");
	public static final NodeType TEXCOORDGEN = new NodeType("TextureCoordinateGenerator");

	// 19. Interpolation component (X3D)
	public static final NodeType COORDINATEINTERPOLATOR2D = new NodeType("CoordinateInterpolator2D");
	public static final NodeType POSITIONINTERPOLATOR2D = new NodeType("PositionInterpolator2D");

	// 21. Key device sensor component (X3D)
	public static final NodeType KEYSENSOR = new NodeType("KeySensor");
	public static final NodeType STRINGSENSOR = new NodeType("StringSensor");

	// 30. Event Utilities component (X3D)
	public static final NodeType BOOLEANFILTER = new NodeType("BooleanFilter");
	public static final NodeType BOOLEANSEQUENCER  = new NodeType("BooleanSequencer");
	public static final NodeType BOOLEANTOGGLE = new NodeType("BooleanToggle");
	public static final NodeType BOOLEANTRIGGER = new NodeType("BooleanTrigger");
	public static final NodeType INTEGERSEQUENCER  = new NodeType("IntegerSequencer");
	public static final NodeType INTEGERTRIGGER = new NodeType("IntegerTrigger");
	public static final NodeType TIMETRIGGER = new NodeType("TimeTrigger");
	
	// Deprecated components (X3D)
	public static final NodeType TRANSFORM2D = new NodeType("Transform2D");
	public static final NodeType BOOLEANTIMETRIGGER = new NodeType("BooleanTimeTrigger");
	public static final NodeType NODESEQUENCER  = new NodeType("NodeSequencer");
	public static final NodeType SHAPE2D = new NodeType("Shape2D");

	// Scene(X3D)
	public static final NodeType SCENE = new NodeType("Scene");

	// Scene(X3D)
	public static final NodeType ROUTE = new NodeType("ROUTE");
	
	////////////////////////////////////////////////
	//	Node List
	////////////////////////////////////////////////

	public static final Node CreateVRML97Node(String typeName)
	{
		if (ANCHOR.equals(typeName) == true)
			return new AnchorNode();
		if (APPEARANCE.equals(typeName) == true)
			return new AppearanceNode();
		if (AUDIOCLIP.equals(typeName) == true)
			return new AudioClipNode();
		if (BACKGROUND.equals(typeName) == true)
			return new BackgroundNode();
		if (BILLBOARD.equals(typeName) == true)
			return new BillboardNode();
		if (BOX.equals(typeName) == true)
			return new BoxNode();
		if (COLLISION.equals(typeName) == true)
			return new CollisionNode();
		if (COLOR.equals(typeName) == true)
			return new ColorNode();
		if (COLORINTERP.equals(typeName) == true)
			return new ColorInterpolatorNode();
		if (CONE.equals(typeName) == true)
			return new ConeNode();
		if (COORD.equals(typeName) == true)
			return new CoordinateNode();
		if (COORDINTERP.equals(typeName) == true)
			return new CoordinateInterpolatorNode();
		if (CYLINDER.equals(typeName) == true)
			return new CylinderNode();
		if (CYLINDERSENSOR.equals(typeName) == true)
			return new CylinderSensorNode();
		if (DIRLIGHT.equals(typeName) == true)
			return new DirectionalLightNode();
		if (ELEVATIONGRID.equals(typeName) == true)
			return new ElevationGridNode();
		if (EXTRUSION.equals(typeName) == true)
			return new ExtrusionNode();
		if (FOG.equals(typeName) == true)
			return new FogNode();
		if (FONTSTYLE.equals(typeName) == true)
			return new FontStyleNode();
		if (GROUP.equals(typeName) == true)
			return new GroupNode();
		if (IMAGETEXTURE.equals(typeName) == true)
			return new ImageTextureNode();
		if (INDEXEDFACESET.equals(typeName) == true)
			return new IndexedFaceSetNode();
		if (INDEXEDLINESET.equals(typeName) == true)
			return new IndexedLineSetNode();
		if (INLINE.equals(typeName) == true)
			return new InlineNode();
		if (LOD.equals(typeName) == true)
			return new LODNode();
		if (MATERIAL.equals(typeName) == true)
			return new MaterialNode();
		if (MOVIETEXTURE.equals(typeName) == true)
			return new MovieTextureNode();
		if (NAVIGATIONINFO.equals(typeName) == true)
			return new NavigationInfoNode();
		if (NORMAL.equals(typeName) == true)
			return new NormalNode();
		if (NORMALINTERP.equals(typeName) == true)
			return new NormalInterpolatorNode();
		if (ORIENTATIONINTERP.equals(typeName) == true)
			return new OrientationInterpolatorNode();
		if (PIXELTEXTURE.equals(typeName) == true)
			return new PixelTextureNode();
		if (PLANESENSOR.equals(typeName) == true)
			return new PlaneSensorNode();
		if (POINTLIGHT.equals(typeName) == true)
			return new PointLightNode();
		if (POINTSET.equals(typeName) == true)
			return new PointSetNode();
		if (POSITONINTERP.equals(typeName) == true)
			return new PositionInterpolatorNode();
		if (PROXIMITYSENSOR.equals(typeName) == true)
			return new ProximitySensorNode();
		if (PROXY.equals(typeName) == true)
			return new ProxyNode();
		if (SCALARINTERP.equals(typeName) == true)
			return new ScalarInterpolatorNode();
		if (SCRIPT.equals(typeName) == true)
			return new ScriptNode();
		if (SHAPE.equals(typeName) == true)
			return new ShapeNode();
		if (SOUND.equals(typeName) == true)
			return new SoundNode();
		if (SPHERE.equals(typeName) == true)
			return new SphereNode();
		if (SPHERESENSOR.equals(typeName) == true)
			return new SphereSensorNode();
		if (SPOTLIGHT.equals(typeName) == true)
			return new SpotLightNode();
		if (SWITCH.equals(typeName) == true)
			return new SwitchNode();
		if (TEXT.equals(typeName) == true)
			return new TextNode();
		if (TEXTURECOORD.equals(typeName) == true)
			return new TextureCoordinateNode();
		if (TEXTURETRANSFORM.equals(typeName) == true)
			return new TextureTransformNode();
		if (TIMESENSOR.equals(typeName) == true)
			return new TimeSensorNode();
		if (TOUCHSENSOR.equals(typeName) == true)
			return new TouchSensorNode();
		if (TRANSFORM.equals(typeName) == true)
			return new TransformNode();
		if (VIEWPOINT.equals(typeName) == true)
			return new ViewpointNode();
		if (VISIBILITYSENSOR.equals(typeName) == true)
			return new VisibilitySensorNode();
		if (WORLDINFO.equals(typeName) == true)
			return new WorldInfoNode();
		return null;
	}

	public static final Node CreateX3DNode(String typeName)
	{
		Node node = CreateVRML97Node(typeName);
		if (node != null)
			return node;

		// Scene (X3D)
		if (SCENE.equals(typeName) == true)
			return new SceneNode();
			
		// 9. Networking component (X3D)
		if (LOADSENSOR.equals(typeName) == true)
			return new LoadSensorNode();

		// 10. Grouping component (X3D)
		if (STATICGROUP.equals(typeName) == true)
			return new StaticGroupNode();

		// 11. Rendering component (X3D)
		if (COLORRGBA.equals(typeName) == true)
			return new ColorRGBANode();
		if (TRIANGLEFANSET.equals(typeName) == true)
			return new TriangleFanSetNode();
		if (TRIANGLESET.equals(typeName) == true)
			return new TriangleSetNode();
		if (TRIANGLESTRIPSET.equals(typeName) == true)
			return new TriangleStripSetNode();

		// 12. Shape component (X3D)
		if (FILLPROPERTIES.equals(typeName) == true)
			return new FillPropertiesNode();
		if (LINEPROPERTIES.equals(typeName) == true)
			return new LinePropertiesNode();
	
		// 14. Geometry2D component (X3D)
		if (ARC2D.equals(typeName) == true)
			return new Arc2DNode();
		if (ARCCLOSE2D.equals(typeName) == true)
			return new ArcClose2DNode();
		if (CIRCLE2D.equals(typeName) == true)
			return new Circle2DNode();
		if (DISK2D.equals(typeName) == true)
			return new Disk2DNode();
		if (POLYLINE2D.equals(typeName) == true)
			return new Polyline2DNode();
		if (POLYPOINT2D.equals(typeName) == true)
			return new Polypoint2DNode();
		if (RECTANGLE2D.equals(typeName) == true)
			return new Rectangle2DNode();
		if (TRIANGLESET2D.equals(typeName) == true)
			return new TriangleSet2DNode();

		// 18. Texturing component (x3D)
		if (MULTITEXTURE.equals(typeName) == true)
			return new MultiTextureNode();
		if (MULTITEXTURECOORD.equals(typeName) == true)
			return new MultiTextureCoordinateNode();
		if (MULTITEXTURETRANSFORM.equals(typeName) == true)
			return new MultiTextureTransformNode();
		if (TEXCOORDGEN.equals(typeName) == true)
			return new TextureCoordinateGeneratorNode();

		// 19. Interpolation component (X3D)
		if (COORDINATEINTERPOLATOR2D.equals(typeName) == true)
			return new CoordinateInterpolator2DNode();
		if (POSITIONINTERPOLATOR2D.equals(typeName) == true)
			return new PositionInterpolator2DNode();

		// 21. Key device sensor component (X3D)
		if (KEYSENSOR.equals(typeName) == true)
			return new KeySensorNode();
		if (STRINGSENSOR.equals(typeName) == true)
			return new StringSensorNode();

		// 30. Event Utilities component (X3D)
		if (BOOLEANFILTER.equals(typeName) == true)
			return new BooleanFilterNode();
		if (BOOLEANTOGGLE.equals(typeName) == true)
			return new BooleanToggleNode();
		if (BOOLEANTRIGGER.equals(typeName) == true)
			return new BooleanTriggerNode();
		if (BOOLEANSEQUENCER.equals(typeName) == true)
			return new BooleanSequencerNode();
		if (INTEGERSEQUENCER.equals(typeName) == true)
			return new IntegerSequencerNode();
		if (INTEGERTRIGGER.equals(typeName) == true)
			return new IntegerTriggerNode();
		if (TIMETRIGGER.equals(typeName) == true)
			return new TimeTriggerNode();
		
		// Deprecated components
		if (TRANSFORM2D.equals(typeName) == true)
			return new Transform2DNode();
		if (BOOLEANTIMETRIGGER.equals(typeName) == true)
			return new BooleanTimeTriggerNode();
		if (NODESEQUENCER.equals(typeName) == true)
			return new NodeSequencerNode();
		if (SHAPE2D.equals(typeName) == true)
			return new Shape2DNode();

		// ROUTE node
		if (ROUTE.equals(typeName) == true)
			return new RouteNode();
			
		return new XMLNode();
	}		
}
