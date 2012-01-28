/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SceneGraph.java
*
*	03/12/04
*		- Added findLastNode();
*	03/17/04
*		- Added findDEFNode() but not implemented yet.
*
******************************************************************/

package org.cybergarage.x3d;

import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.route.*;

public class Scene extends LinkedListNode implements Constants {

	public Scene() 
	{
	}

	////////////////////////////////////////////////
	//	Node List
	////////////////////////////////////////////////

	private RootNode mRootNode = new RootNode();

	public RootNode getRootNode() {
		return mRootNode;		
	}

	public int getNNodes() {
		return getRootNode().getNChildNodes();		
	}

	public Node getNode(int n) {
		return getRootNode().getChildNode(n);		
	}

	public Node getNodes() {
		return getRootNode().getChildNodes();		
	}

	public void clearNodes() {
		getRootNode().removeChildNodes();		
	}

	public Node getNodes(NodeType type) {
		Node node = getNodes();
		if (node == null)
			return null;
		NodeType nodeType = node.getType();
		if (nodeType.equals(type) == true)
			return node;
		return node.next(type);
	}

	///////////////////////////////////////////////
	//	SceneGraph
	///////////////////////////////////////////////

	public void setSceneGraph(SceneGraph sg) 
	{
		RootNode rootNode = getRootNode();
		rootNode.setSceneGraph(sg);
	}

	///////////////////////////////////////////////
	//	addNode/moveNode
	///////////////////////////////////////////////

	public void addNode(Node node, boolean postShareEvent) 
	{
		RootNode rootNode = getRootNode();
		rootNode.addChildNode(node, postShareEvent);
	}

	public void moveNode(Node node, boolean postShareEvent) 
	{
		RootNode rootNode = getRootNode();
		rootNode.moveChildNode(node, postShareEvent);
	}
	
	public void addNode(Node node) 
	{
		addNode(node, true);
	}

	public void moveNode(Node node) 
	{
		moveNode(node, true);
	}

	///////////////////////////////////////////////
	//	Route
	///////////////////////////////////////////////

	private LinkedList	mRouteList	= new LinkedList();

	public Route getRoutes() {
		return (Route)mRouteList.getNodes();
	}

	public Route getRoute(Node eventOutNode, Field eventOutField, Node eventInNode, Field eventInField)
	{
		for (Route route=getRoutes(); route!=null; route=route.next()) {
			if (eventOutNode == route.getEventOutNode() && eventOutField == route.getEventOutField() &&
				eventInNode == route.getEventInNode() && eventInField == route.getEventInField() ) {
				return route;
			}
		}
		return null;
	}

	public Route addRoute(Route route) {
		if (route.getEventOutNode() == route.getEventInNode()) {
			Debug.warning("Invalidate route infomation = " + route);
			return null;
		}
		if (getRoute(route.getEventOutNode(), route.getEventOutField(), route.getEventInNode(), route.getEventInField()) != null) {
			Debug.message("The same route infomation is already added = " + route);
			return null;
		}
		mRouteList.addNode(route);
		return route;
	}

	public Route addRoute(Node eventOutNode, Field eventOutField, Node eventInNode, Field eventInField)
	{
		Route route = new Route(eventOutNode, eventOutField, eventInNode, eventInField);
		return addRoute(route);
	}

	public Route addRoute(String eventOutNodeName, String eventOutFieldName, String eventInNodeName, String eventInFieldName)
	{
		Node eventInNode = findNode(eventInNodeName);
		Node eventOutNode = findNode(eventOutNodeName);

		Field eventOutField = null;

		if (eventOutNode != null) {
			try {
				eventOutField = ((Node)eventOutNode).getEventOut(eventOutFieldName);
			}
			catch (InvalidEventOutException eventOutException) {
				try {
					eventOutField = eventOutNode.getExposedField(eventOutFieldName);
				}
				catch (InvalidExposedFieldException exposedFieldException) {}
			}
		}

		if (eventOutField == null)
			Debug.warning("Couldn't a field (" + eventOutNodeName + "::" + eventOutFieldName + ")");

		Field eventInField = null;

		if (eventInNode != null) {
			try {
				eventInField = eventInNode.getEventIn(eventInFieldName);
			}
			catch (InvalidEventInException eventInException) {
				try {
					eventInField = eventInNode.getExposedField(eventInFieldName);
				}
				catch (InvalidExposedFieldException exposedFieldException) {}
			}
		}

		if (eventInField == null)
			Debug.warning("Couldn't a field (" + eventInNodeName + "::" + eventInFieldName + ")");

		return addRoute(eventOutNode, eventOutField, eventInNode, eventInField);
	}

	public void removeRoute(Node eventOutNode, Field eventOutField, Node eventInNode, Field eventInField)
	{
		Route route = getRoute(eventOutNode, eventOutField, eventInNode, eventInField);
		if (route!=null)
			route.remove();
	}

	void removeEventInFieldRoutes(Node node, Field field) {
		Route	route = getRoutes();
		while (route != null) {
			Route nextRoute = route.next();
			if (route.getEventInNode() == node && route.getEventInField() == field)
				route.remove();
			route = nextRoute;
		}
	}

	public void removeEventOutFieldRoutes(Node node, Field field) {
		Route	route = getRoutes();
		while (route != null) {
			Route nextRoute = route.next();
			if (route.getEventOutNode() == node && route.getEventOutField() == field)
				route.remove();
			route = nextRoute;
		}
	}

	public void removeNodeRoutes(Node node) {
		Route route = getRoutes();
		while (route != null) {
			Route nextRoute = route.next();
			if (node == route.getEventInNode() || node == route.getEventOutNode())
				route.remove();
			route = nextRoute;
		}
	}

	public void removeNodeRoutes(Node node, Field field) {
		removeEventInFieldRoutes(node, field);
		removeEventOutFieldRoutes(node, field);
	}

	public void removeRoute(Route removeRoute)
	{
		for (Route route=getRoutes(); route!=null; route=route.next()) {
			if (removeRoute == route) {
				route.remove();
				return;
			}
		}
	}

	////////////////////////////////////////////////
	//	find node
	////////////////////////////////////////////////

	public Node findNode(NodeType type) {
		Node rootNode = getRootNode();
		return rootNode.nextTraversalByType(type);
	}

	public Node findNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		Node rootNode = getRootNode();
		return rootNode.nextTraversalByName(name);
	}

	public Node findNode(NodeType type, String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (Node node = findNode(type); node != null; node = node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public Node findLastNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		Node findNode = null;
		for (Node node = getRootNode().nextTraversal(); node != null; node = node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					findNode = node;
			}
		}
		return findNode;
	}

	public Node findDEFNode(String name) {
		return findNode(name);
	}

	////////////////////////////////////////////////
	//	child node list
	////////////////////////////////////////////////

	public GroupingNode getGroupingNodes() {
		for (Node node = getNodes(); node != null; node = node.next()) {
			if (node.isGroupingNode())
				return (GroupingNode)node;
		}
		return null;
	}

	public AnchorNode getAnchorNodes() {
		return (AnchorNode)getNodes(NodeType.ANCHOR );
	}

	public AppearanceNode getAppearanceNodes() {
		return (AppearanceNode)getNodes(NodeType.APPEARANCE);
	}

	public AudioClipNode getAudioClipNodes() {
		return (AudioClipNode)getNodes(NodeType.AUDIOCLIP);
	}

	public BackgroundNode getBackgroundNodes() {
		return (BackgroundNode)getNodes(NodeType.BACKGROUND);
	}

	public BillboardNode getBillboardNodes() {
		return (BillboardNode)getNodes(NodeType.BILLBOARD);
	}

	public BoxNode getBoxNodes() {
		return (BoxNode)getNodes(NodeType.BOX);
	}

	public CollisionNode getCollisionNodes() {
		return (CollisionNode)getNodes(NodeType.COLLISION);
	}

	public ColorNode getColorNodes() {
		return (ColorNode)getNodes(NodeType.COLOR);
	}

	public ColorInterpolatorNode getColorInterpolatorNodes() {
		return (ColorInterpolatorNode)getNodes(NodeType.COLORINTERP);
	}

	public ConeNode getConeNodes() {
		return (ConeNode)getNodes(NodeType.CONE);
	}

	public CoordinateNode getCoordinateNodes() {
		return (CoordinateNode)getNodes(NodeType.COORD);
	}

	public CoordinateInterpolatorNode getCoordinateInterpolatorNodes() {
		return (CoordinateInterpolatorNode)getNodes(NodeType.COORDINTERP);
	}

	public CylinderNode getCylinderNodes() {
		return (CylinderNode)getNodes(NodeType.CYLINDER);
	}

	public CylinderSensorNode getCylinderSensorNodes() {
		return (CylinderSensorNode)getNodes(NodeType.CYLINDERSENSOR);
	}

	public DirectionalLightNode getDirectionalLightNodes() {
		return (DirectionalLightNode)getNodes(NodeType.DIRLIGHT);
	}

	public ElevationGridNode getElevationGridNodes() {
		return (ElevationGridNode)getNodes(NodeType.ELEVATIONGRID);
	}

	public ExtrusionNode getExtrusionNodes() {
		return (ExtrusionNode)getNodes(NodeType.EXTRUSION);
	}

	public FogNode getFogNodes() {
		return (FogNode)getNodes(NodeType.FOG);
	}

	public FontStyleNode getFontStyleNodes() {
		return (FontStyleNode)getNodes(NodeType.FONTSTYLE);
	}

	public GroupNode getGroupNodes() {
		return (GroupNode)getNodes(NodeType.GROUP);
	}

	public ImageTextureNode getImageTextureNodes() {
		return (ImageTextureNode)getNodes(NodeType.IMAGETEXTURE);
	}

	public IndexedFaceSetNode getIndexedFaceSetNodes() {
		return (IndexedFaceSetNode)getNodes(NodeType.INDEXEDFACESET);
	}

	public IndexedLineSetNode getIndexedLineSetNodes() {
		return (IndexedLineSetNode)getNodes(NodeType.INDEXEDLINESET);
	}

	public InlineNode getInlineNodes() {
		return (InlineNode)getNodes(NodeType.INLINE);
	}

	public LODNode getLODNodes() {
		return (LODNode)getNodes(NodeType.LOD);
	}

	public MaterialNode getMaterialNodes() {
		return (MaterialNode)getNodes(NodeType.MATERIAL);
	}

	public MovieTextureNode getMovieTextureNodes() {
		return (MovieTextureNode)getNodes(NodeType.MOVIETEXTURE);
	}

	public NavigationInfoNode getNavigationInfoNodes() {
		return (NavigationInfoNode)getNodes(NodeType.NAVIGATIONINFO);
	}

	public NormalNode getNormalNodes() {
		return (NormalNode)getNodes(NodeType.NORMAL);
	}

	public NormalInterpolatorNode getNormalInterpolatorNodes() {
		return (NormalInterpolatorNode)getNodes(NodeType.NORMALINTERP);
	}

	public OrientationInterpolatorNode getOrientationInterpolatorNodes() {
		return (OrientationInterpolatorNode)getNodes(NodeType.ORIENTATIONINTERP);
	}

	public PixelTextureNode getPixelTextureNodes() {
		return (PixelTextureNode)getNodes(NodeType.PIXELTEXTURE);
	}

	public PlaneSensorNode getPlaneSensorNodes() {
		return (PlaneSensorNode)getNodes(NodeType.PLANESENSOR);
	}

	public PointLightNode getPointLightNodes() {
		return (PointLightNode)getNodes(NodeType.POINTLIGHT);
	}

	public PointSetNode getPointSetNodes() {
		return (PointSetNode)getNodes(NodeType.POINTSET);
	}

	public PositionInterpolatorNode getPositionInterpolatorNodes() {
		return (PositionInterpolatorNode)getNodes(NodeType.POSITONINTERP);
	}

	public ProximitySensorNode getProximitySensorNodes() {
		return (ProximitySensorNode)getNodes(NodeType.PROXIMITYSENSOR);
	}

	public ProxyNode getProxyNodeNodes() {
		return (ProxyNode)getNodes(NodeType.PROXY);
	}

	public ScalarInterpolatorNode getScalarInterpolatorNodes() {
		return (ScalarInterpolatorNode)getNodes(NodeType.SCALARINTERP);
	}

	public ScriptNode getScriptNodes() {
		return (ScriptNode)getNodes(NodeType.SCRIPT);
	}

	public ShapeNode getShapeNodes() {
		return (ShapeNode)getNodes(NodeType.SHAPE);
	}

	public SoundNode getSoundNodes() {
		return (SoundNode)getNodes(NodeType.SOUND);
	}

	public SphereNode getSphereNodes() {
		return (SphereNode)getNodes(NodeType.SPHERE);
	}

	public SphereSensorNode getSphereSensorNodes() {
		return (SphereSensorNode)getNodes(NodeType.SPHERESENSOR);
	}

	public SpotLightNode getSpotLightNodes() {
		return (SpotLightNode)getNodes(NodeType.SPOTLIGHT);
	}

	public SwitchNode getSwitchNodes() {
		return (SwitchNode)getNodes(NodeType.SWITCH);
	}

	public TextNode getTextNodes() {
		return (TextNode)getNodes(NodeType.TEXT);
	}

	public TextureCoordinateNode getTextureCoordinateNodes() {
		return (TextureCoordinateNode)getNodes(NodeType.TEXTURECOORD);
	}

	public TextureTransformNode getTextureTransformNodes() {
		return (TextureTransformNode)getNodes(NodeType.TEXTURETRANSFORM);
	}

	public TimeSensorNode getTimeSensorNodes() {
		return (TimeSensorNode)getNodes(NodeType.TIMESENSOR);
	}

	public TouchSensorNode getTouchSensorNodes() {
		return (TouchSensorNode)getNodes(NodeType.TOUCHSENSOR);
	}

	public TransformNode getTransformNodes() {
		return (TransformNode)getNodes(NodeType.TRANSFORM);
	}

	public ViewpointNode getViewpointNodes() {
		return (ViewpointNode)getNodes(NodeType.VIEWPOINT);
	}

	public VisibilitySensorNode getVisibilitySensorNodes() {
		return (VisibilitySensorNode)getNodes(NodeType.VISIBILITYSENSOR);
	}

	public WorldInfoNode getWorldInfoNodes() {
		return (WorldInfoNode)getNodes(NodeType.WORLDINFO);
	}

	// 9. Networking component (X3D)

	public LoadSensorNode getLoadSensorNodes() {
		return (LoadSensorNode)getNodes(NodeType.LOADSENSOR);
	}

	// 10. Grouping component (X3D)

	public StaticGroupNode getStaticGroupNodes() {
		return (StaticGroupNode)getNodes(NodeType.STATICGROUP);
	}

	// 11. Rendering component (X3D)

	public ColorRGBANode getColorRGBANodes() {
		return (ColorRGBANode)getNodes(NodeType.COLORRGBA);
	}

	public TriangleSetNode getTriangleSetNodes() {
		return (TriangleSetNode)getNodes(NodeType.TRIANGLESET);
	}

	public TriangleFanSetNode getTriangleFanSetNodes() {
		return (TriangleFanSetNode)getNodes(NodeType.TRIANGLEFANSET);
	}

	public TriangleStripSetNode getTriangleStripSetNodes() {
		return (TriangleStripSetNode)getNodes(NodeType.TRIANGLESTRIPSET);
	}
	
	// 12. Shape component (X3D)

	public FillPropertiesNode getFillPropertiesNodes() {
		return (FillPropertiesNode)getNodes(NodeType.FILLPROPERTIES);
	}

	public LinePropertiesNode getLinePropertiesNodes() {
		return (LinePropertiesNode)getNodes(NodeType.LINEPROPERTIES);
	}

	// 14. Geometry2D component (X3D)

	public Arc2DNode getArc2DNodes() {
		return (Arc2DNode)getNodes(NodeType.ARC2D);
	}

	public ArcClose2DNode getArcClose2DNodes() {
		return (ArcClose2DNode)getNodes(NodeType.ARCCLOSE2D);
	}

	public Circle2DNode getCircle2DNodes() {
		return (Circle2DNode)getNodes(NodeType.CIRCLE2D);
	}

	public Disk2DNode getDisk2DNodes() {
		return (Disk2DNode)getNodes(NodeType.DISK2D);
	}

	public Polyline2DNode getPolyline2DNodes() {
		return (Polyline2DNode)getNodes(NodeType.POLYLINE2D);
	}

	public Polypoint2DNode getPolypoint2DNodes() {
		return (Polypoint2DNode)getNodes(NodeType.POLYPOINT2D);
	}

	public Rectangle2DNode getRectangle2DNodes() {
		return (Rectangle2DNode)getNodes(NodeType.RECTANGLE2D);
	}

	public TriangleSet2DNode getTriangleSet2DNodes() {
		return (TriangleSet2DNode)getNodes(NodeType.TRIANGLESET2D);
	}
	
	// 18. Texturing component (x3D)

	public MultiTextureNode getMultiTextureNodes() {
		return (MultiTextureNode)getNodes(NodeType.MULTITEXTURE);
	}

	public MultiTextureCoordinateNode getMultiTextureCoordinateNodes() {
		return (MultiTextureCoordinateNode)getNodes(NodeType.MULTITEXTURECOORD);
	}

	public MultiTextureTransformNode getMultiTextureTransformNodes() {
		return (MultiTextureTransformNode)getNodes(NodeType.MULTITEXTURETRANSFORM);
	}
	
	public TextureCoordinateGeneratorNode getTextureCoordinateGeneratorNodes() {
		return (TextureCoordinateGeneratorNode)getNodes(NodeType.TEXCOORDGEN);
	}
	
	// 19. Interpolation component (X3D)

	public CoordinateInterpolator2DNode getCoordinateInterpolator2DNodes() {
		return (CoordinateInterpolator2DNode)getNodes(NodeType.COORDINATEINTERPOLATOR2D);
	}

	public PositionInterpolator2DNode getPositionInterpolator2DNodes() {
		return (PositionInterpolator2DNode)getNodes(NodeType.POSITIONINTERPOLATOR2D);
	}

	// 21. Key device sensor component (X3D)

	public KeySensorNode getKeySensorNodes() {
		return (KeySensorNode)getNodes(NodeType.KEYSENSOR);
	}

	public StringSensorNode getStringSensorNodes() {
		return (StringSensorNode)getNodes(NodeType.STRINGSENSOR);
	}

	// 30. Event Utilities component (X3D)

	public BooleanFilterNode getBooleanFilterNodes() {
		return (BooleanFilterNode)getNodes(NodeType.BOOLEANFILTER);
	}

	public BooleanToggleNode getBooleanToggleNodes() {
		return (BooleanToggleNode)getNodes(NodeType.BOOLEANTOGGLE);
	}

	public BooleanTriggerNode getBooleanTriggerNodes() {
		return (BooleanTriggerNode)getNodes(NodeType.BOOLEANTRIGGER);
	}

	public BooleanSequencerNode getBooleanSequencerNodes() {
		return (BooleanSequencerNode)getNodes(NodeType.BOOLEANSEQUENCER);
	}

	public IntegerTriggerNode getIntegerTriggerNodes() {
		return (IntegerTriggerNode)getNodes(NodeType.INTEGERTRIGGER);
	}

	public IntegerSequencerNode getIntegerSequencerNodes() {
		return (IntegerSequencerNode)getNodes(NodeType.INTEGERSEQUENCER);
	}

	public TimeTriggerNode getTimeTriggerNodes() {
		return (TimeTriggerNode)getNodes(NodeType.TIMETRIGGER);
	}
	
	// Deprecated components (X3D)

	public NodeSequencerNode getNodeSequencerNodes() {
		return (NodeSequencerNode)getNodes(NodeType.NODESEQUENCER);
	}

	public Shape2DNode getShape2DNodes() {
		return (Shape2DNode)getNodes(NodeType.SHAPE2D);
	}

	public BooleanTimeTriggerNode getBooleanTimeTriggerNodes() {
		return (BooleanTimeTriggerNode)getNodes(NodeType.BOOLEANTIMETRIGGER);
	}

	public Transform2DNode getTransform2DNodes() {
		return (Transform2DNode)getNodes(NodeType.TRANSFORM2D);
	}

	////////////////////////////////////////////////
	//	find a node by type (Common)
	////////////////////////////////////////////////

	public GroupingNode findGroupingNode() {
		for (Node node = (getRootNode()).nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.isGroupingNode())
				return (GroupingNode)node;
		}
		return null;
	}

	public LightNode findLightNode() {
		for (Node node = (getRootNode()).nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.isLightNode())
				return (LightNode)node;
		}
		return null;
	}

	public Geometry3DNode findGeometry3DNode() {
		for (Node node = (getRootNode()).nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.isGeometry3DNode())
				return (Geometry3DNode)node;
		}
		return null;
	}

	public InterpolatorNode findInterpolatorNode() {
		for (Node node = (getRootNode()).nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.isInterpolatorNode())
				return (InterpolatorNode)node;
		}
		return null;
	}

	////////////////////////////////////////////////
	//	find a node by type
	////////////////////////////////////////////////
	
	public AnchorNode findAnchorNode() {
		return (AnchorNode)findNode(NodeType.ANCHOR );
	}

	public AppearanceNode findAppearanceNode() {
		return (AppearanceNode)findNode(NodeType.APPEARANCE);
	}

	public AudioClipNode findAudioClipNode() {
		return (AudioClipNode)findNode(NodeType.AUDIOCLIP);
	}

	public BackgroundNode findBackgroundNode() {
		return (BackgroundNode)findNode(NodeType.BACKGROUND);
	}

	public BillboardNode findBillboardNode() {
		return (BillboardNode)findNode(NodeType.BILLBOARD);
	}

	public BoxNode findBoxNode() {
		return (BoxNode)findNode(NodeType.BOX);
	}

	public CollisionNode findCollisionNode() {
		return (CollisionNode)findNode(NodeType.COLLISION);
	}

	public ColorNode findColorNode() {
		return (ColorNode)findNode(NodeType.COLOR);
	}

	public ColorInterpolatorNode findColorInterpolatorNode() {
		return (ColorInterpolatorNode)findNode(NodeType.COLORINTERP);
	}

	public ConeNode findConeNode() {
		return (ConeNode)findNode(NodeType.CONE);
	}

	public CoordinateNode findCoordinateNode() {
		return (CoordinateNode)findNode(NodeType.COORD);
	}

	public CoordinateInterpolatorNode findCoordinateInterpolatorNode() {
		return (CoordinateInterpolatorNode)findNode(NodeType.COORDINTERP);
	}

	public CylinderNode findCylinderNode() {
		return (CylinderNode)findNode(NodeType.CYLINDER);
	}

	public CylinderSensorNode findCylinderSensorNode() {
		return (CylinderSensorNode)findNode(NodeType.CYLINDERSENSOR);
	}

	public DirectionalLightNode findDirectionalLightNode() {
		return (DirectionalLightNode)findNode(NodeType.DIRLIGHT);
	}

	public ElevationGridNode findElevationGridNode() {
		return (ElevationGridNode)findNode(NodeType.ELEVATIONGRID);
	}

	public ExtrusionNode findExtrusionNode() {
		return (ExtrusionNode)findNode(NodeType.EXTRUSION);
	}

	public FogNode findFogNode() {
		return (FogNode)findNode(NodeType.FOG);
	}

	public FontStyleNode findFontStyleNode() {
		return (FontStyleNode)findNode(NodeType.FONTSTYLE);
	}

	public GroupNode findGroupNode() {
		return (GroupNode)findNode(NodeType.GROUP);
	}

	public ImageTextureNode findImageTextureNode() {
		return (ImageTextureNode)findNode(NodeType.IMAGETEXTURE);
	}

	public IndexedFaceSetNode findIndexedFaceSetNode() {
		return (IndexedFaceSetNode)findNode(NodeType.INDEXEDFACESET);
	}

	public IndexedLineSetNode findIndexedLineSetNode() {
		return (IndexedLineSetNode)findNode(NodeType.INDEXEDLINESET);
	}

	public InlineNode findInlineNode() {
		return (InlineNode)findNode(NodeType.INLINE);
	}

	public LODNode findLODNode() {
		return (LODNode)findNode(NodeType.LOD);
	}

	public MaterialNode findMaterialNode() {
		return (MaterialNode)findNode(NodeType.MATERIAL);
	}

	public MovieTextureNode findMovieTextureNode() {
		return (MovieTextureNode)findNode(NodeType.MOVIETEXTURE);
	}

	public NavigationInfoNode findNavigationInfoNode() {
		return (NavigationInfoNode)findNode(NodeType.NAVIGATIONINFO);
	}

	public NormalNode findNormalNode() {
		return (NormalNode)findNode(NodeType.NORMAL);
	}

	public NormalInterpolatorNode findNormalInterpolatorNode() {
		return (NormalInterpolatorNode)findNode(NodeType.NORMALINTERP);
	}

	public OrientationInterpolatorNode findOrientationInterpolatorNode() {
		return (OrientationInterpolatorNode)findNode(NodeType.ORIENTATIONINTERP);
	}

	public PixelTextureNode findPixelTextureNode() {
		return (PixelTextureNode)findNode(NodeType.PIXELTEXTURE);
	}

	public PlaneSensorNode findPlaneSensorNode() {
		return (PlaneSensorNode)findNode(NodeType.PLANESENSOR);
	}

	public PointLightNode findPointLightNode() {
		return (PointLightNode)findNode(NodeType.POINTLIGHT);
	}

	public PointSetNode findPointSetNode() {
		return (PointSetNode)findNode(NodeType.POINTSET);
	}

	public PositionInterpolatorNode findPositionInterpolatorNode() {
		return (PositionInterpolatorNode)findNode(NodeType.POSITONINTERP);
	}

	public ProximitySensorNode findProximitySensorNode() {
		return (ProximitySensorNode)findNode(NodeType.PROXIMITYSENSOR);
	}

	public ProxyNode findProxyNode() {
		return (ProxyNode)findNode(NodeType.PROXY);
	}

	public ScalarInterpolatorNode findScalarInterpolatorNode() {
		return (ScalarInterpolatorNode)findNode(NodeType.SCALARINTERP);
	}

	public ScriptNode findScriptNode() {
		return (ScriptNode)findNode(NodeType.SCRIPT);
	}

	public ShapeNode findShapeNode() {
		return (ShapeNode)findNode(NodeType.SHAPE);
	}

	public SoundNode findSoundNode() {
		return (SoundNode)findNode(NodeType.SOUND);
	}

	public SphereNode findSphereNode() {
		return (SphereNode)findNode(NodeType.SPHERE);
	}

	public SphereSensorNode findSphereSensorNode() {
		return (SphereSensorNode)findNode(NodeType.SPHERESENSOR);
	}

	public SpotLightNode findSpotLightNode() {
		return (SpotLightNode)findNode(NodeType.SPOTLIGHT);
	}

	public SwitchNode findSwitchNode() {
		return (SwitchNode)findNode(NodeType.SWITCH);
	}

	public TextNode findTextNode() {
		return (TextNode)findNode(NodeType.TEXT);
	}

	public TextureCoordinateNode findTextureCoordinateNode() {
		return (TextureCoordinateNode)findNode(NodeType.TEXTURECOORD);
	}

	public TextureTransformNode findTextureTransformNode() {
		return (TextureTransformNode)findNode(NodeType.TEXTURETRANSFORM);
	}

	public TimeSensorNode findTimeSensorNode() {
		return (TimeSensorNode)findNode(NodeType.TIMESENSOR);
	}

	public TouchSensorNode findTouchSensorNode() {
		return (TouchSensorNode)findNode(NodeType.TOUCHSENSOR);
	}

	public TransformNode findTransformNode() {
		return (TransformNode)findNode(NodeType.TRANSFORM);
	}

	public ViewpointNode findViewpointNode() {
		return (ViewpointNode)findNode(NodeType.VIEWPOINT);
	}

	public VisibilitySensorNode findVisibilitySensorNode() {
		return (VisibilitySensorNode)findNode(NodeType.VISIBILITYSENSOR);
	}

	public WorldInfoNode findWorldInfoNode() {
		return (WorldInfoNode)findNode(NodeType.WORLDINFO);
	}

	// Scene (X3D)

	public SceneNode findSceneNode() {
		return (SceneNode)findNode(NodeType.SCENE);
	}

	// 9. Networking component (X3D)

	public LoadSensorNode findLoadSensorNode() {
		return (LoadSensorNode)findNode(NodeType.LOADSENSOR);
	}

	// 10. Grouping component (X3D)

	public StaticGroupNode findStaticGroupNode() {
		return (StaticGroupNode)findNode(NodeType.STATICGROUP);
	}

	// 11. Rendering component (X3D)

	public ColorRGBANode findColorRGBANode() {
		return (ColorRGBANode)findNode(NodeType.COLORRGBA);
	}

	public TriangleSetNode findTriangleSetNode() {
		return (TriangleSetNode)findNode(NodeType.TRIANGLESET);
	}

	public TriangleFanSetNode findTriangleFanSetNode() {
		return (TriangleFanSetNode)findNode(NodeType.TRIANGLEFANSET);
	}

	public TriangleStripSetNode findTriangleStripSetNode() {
		return (TriangleStripSetNode)findNode(NodeType.TRIANGLESTRIPSET);
	}
	
	// 12. Shape component (X3D)

	public FillPropertiesNode findFillPropertiesNode() {
		return (FillPropertiesNode)findNode(NodeType.FILLPROPERTIES);
	}

	public LinePropertiesNode findLinePropertiesNode() {
		return (LinePropertiesNode)findNode(NodeType.LINEPROPERTIES);
	}

	// 14. Geometry2D component (X3D)

	public Arc2DNode findArc2DNode() {
		return (Arc2DNode)findNode(NodeType.ARC2D);
	}

	public ArcClose2DNode findArcClose2DNode() {
		return (ArcClose2DNode)findNode(NodeType.ARCCLOSE2D);
	}

	public Circle2DNode findCircle2DNode() {
		return (Circle2DNode)findNode(NodeType.CIRCLE2D);
	}

	public Disk2DNode findDisk2DNode() {
		return (Disk2DNode)findNode(NodeType.DISK2D);
	}

	public Polyline2DNode findPolyline2DNode() {
		return (Polyline2DNode)findNode(NodeType.POLYLINE2D);
	}

	public Polypoint2DNode findPolypoint2DNode() {
		return (Polypoint2DNode)findNode(NodeType.POLYPOINT2D);
	}

	public Rectangle2DNode findRectangle2DNode() {
		return (Rectangle2DNode)findNode(NodeType.RECTANGLE2D);
	}

	public TriangleSet2DNode findTriangleSet2DNode() {
		return (TriangleSet2DNode)findNode(NodeType.TRIANGLESET2D);
	}
	
	// 18. Texturing component (x3D)

	public MultiTextureNode findMultiTextureNode() {
		return (MultiTextureNode)findNode(NodeType.MULTITEXTURE);
	}

	public MultiTextureCoordinateNode findMultiTextureCoordinateNode() {
		return (MultiTextureCoordinateNode)findNode(NodeType.MULTITEXTURECOORD);
	}

	public MultiTextureTransformNode findMultiTextureTransformNode() {
		return (MultiTextureTransformNode)findNode(NodeType.MULTITEXTURETRANSFORM);
	}
	
	public TextureCoordinateGeneratorNode findTextureCoordinateGeneratorNode() {
		return (TextureCoordinateGeneratorNode)findNode(NodeType.TEXCOORDGEN);
	}
	
	// 19. Interpolation component (X3D)

	public CoordinateInterpolator2DNode findCoordinateInterpolator2DNode() {
		return (CoordinateInterpolator2DNode)findNode(NodeType.COORDINATEINTERPOLATOR2D);
	}

	public PositionInterpolator2DNode findPositionInterpolator2DNode() {
		return (PositionInterpolator2DNode)findNode(NodeType.POSITIONINTERPOLATOR2D);
	}

	// 21. Key device sensor component (X3D)

	public KeySensorNode findKeySensorNode() {
		return (KeySensorNode)findNode(NodeType.KEYSENSOR);
	}

	public StringSensorNode findStringSensorNode() {
		return (StringSensorNode)findNode(NodeType.STRINGSENSOR);
	}

	// 30. Event Utilities component (X3D)

	public BooleanFilterNode findBooleanFilterNode() {
		return (BooleanFilterNode)findNode(NodeType.BOOLEANFILTER);
	}

	public BooleanToggleNode findBooleanToggleNode() {
		return (BooleanToggleNode)findNode(NodeType.BOOLEANTOGGLE);
	}

	public BooleanTriggerNode findBooleanTriggerNode() {
		return (BooleanTriggerNode)findNode(NodeType.BOOLEANTRIGGER);
	}

	public BooleanSequencerNode findBooleanSequencerNode() {
		return (BooleanSequencerNode)findNode(NodeType.BOOLEANSEQUENCER);
	}

	public IntegerTriggerNode findIntegerTriggerNode() {
		return (IntegerTriggerNode)findNode(NodeType.INTEGERTRIGGER);
	}

	public IntegerSequencerNode findIntegerSequencerNode() {
		return (IntegerSequencerNode)findNode(NodeType.INTEGERSEQUENCER);
	}

	public TimeTriggerNode findTimeTriggerNode() {
		return (TimeTriggerNode)findNode(NodeType.TIMETRIGGER);
	}
	
	// Deprecated components (X3D)

	public NodeSequencerNode findNodeSequencerNode() {
		return (NodeSequencerNode)findNode(NodeType.NODESEQUENCER);
	}

	public Shape2DNode findShape2DNode() {
		return (Shape2DNode)findNode(NodeType.SHAPE2D);
	}

	public BooleanTimeTriggerNode findBooleanTimeTriggerNode() {
		return (BooleanTimeTriggerNode)findNode(NodeType.BOOLEANTIMETRIGGER);
	}

	public Transform2DNode findTransform2DNode() {
		return (Transform2DNode)findNode(NodeType.TRANSFORM2D);
	}

	////////////////////////////////////////////////
	//	find a node by name (Common)
	////////////////////////////////////////////////

	public GroupingNode findGroupingNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (Node node = findGroupingNode(); node != null; node = node.nextTraversal()) {
			if (node.isGroupingNode() == true) {
				String nodeName = node.getName();
				if (nodeName != null) {
					if (name.equals(nodeName) == true)
						return (GroupingNode)node;
				}
			}
		}
		return null;
	}

	public LightNode findLightNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (Node node = findLightNode(); node != null; node = node.nextTraversal()) {
			if (node.isLightNode() == true) {
				String nodeName = node.getName();
				if (nodeName != null) {
					if (name.equals(nodeName) == true)
						return (LightNode)node;
				}
			}
		}
		return null;
	}

	public Geometry3DNode findGeometry3DNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (Node node = findGeometry3DNode(); node != null; node = node.nextTraversal()) {
			if (node.isGeometry3DNode() == true) {
				String nodeName = node.getName();
				if (nodeName != null) {
					if (name.equals(nodeName) == true)
						return (Geometry3DNode)node;
				}
			}
		}
		return null;
	}

	public InterpolatorNode findInterpolatorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (Node node = findInterpolatorNode(); node != null; node = node.nextTraversal()) {
			if (node.isInterpolatorNode() == true) {
				String nodeName = node.getName();
				if (nodeName != null) {
					if (name.equals(nodeName) == true)
						return (InterpolatorNode)node;
				}
			}
		}
		return null;
	}

	////////////////////////////////////////////////
	//	find a node by name
	////////////////////////////////////////////////

	public AnchorNode findAnchorNode(String name) {
		return (AnchorNode)findNode(NodeType.ANCHOR, name);
	}

	public AppearanceNode findAppearanceNode(String name) {
		return (AppearanceNode)findNode(NodeType.APPEARANCE, name);
	}

	public AudioClipNode findAudioClipNode(String name) {
		return (AudioClipNode)findNode(NodeType.AUDIOCLIP, name);
	}

	public BackgroundNode findBackgroundNode(String name) {
		return (BackgroundNode)findNode(NodeType.BACKGROUND, name);
	}

	public BillboardNode findBillboardNode(String name) {
		return (BillboardNode)findNode(NodeType.BILLBOARD, name);
	}

	public BoxNode findBoxNode(String name) {
		return (BoxNode)findNode(NodeType.BOX, name);
	}

	public CollisionNode findCollisionNode(String name) {
		return (CollisionNode)findNode(NodeType.COLLISION, name);
	}

	public ColorNode findColorNode(String name) {
		return (ColorNode)findNode(NodeType.COLOR, name);
	}

	public ColorInterpolatorNode findColorInterpolatorNode(String name) {
		return (ColorInterpolatorNode)findNode(NodeType.COLORINTERP, name);
	}

	public ConeNode findConeNode(String name) {
		return (ConeNode)findNode(NodeType.CONE, name);
	}

	public CoordinateNode findCoordinateNode(String name) {
		return (CoordinateNode)findNode(NodeType.COORD, name);
	}

	public CoordinateInterpolatorNode findCoordinateInterpolatorNode(String name) {
		return (CoordinateInterpolatorNode)findNode(NodeType.COORDINTERP, name);
	}

	public CylinderNode findCylinderNode(String name) {
		return (CylinderNode)findNode(NodeType.CYLINDER, name);
	}

	public CylinderSensorNode findCylinderSensorNode(String name) {
		return (CylinderSensorNode)findNode(NodeType.CYLINDERSENSOR, name);
	}

	public DirectionalLightNode findDirectionalLightNode(String name) {
		return (DirectionalLightNode)findNode(NodeType.DIRLIGHT, name);
	}

	public ElevationGridNode findElevationGridNode(String name) {
		return (ElevationGridNode)findNode(NodeType.ELEVATIONGRID, name);
	}

	public ExtrusionNode findExtrusionNode(String name) {
		return (ExtrusionNode)findNode(NodeType.EXTRUSION, name);
	}

	public FogNode findFogNode(String name) {
		return (FogNode)findNode(NodeType.FOG, name);
	}

	public FontStyleNode findFontStyleNode(String name) {
		return (FontStyleNode)findNode(NodeType.FONTSTYLE, name);
	}

	public GroupNode findGroupNode(String name) {
		return (GroupNode)findNode(NodeType.GROUP, name);
	}

	public ImageTextureNode findImageTextureNode(String name) {
		return (ImageTextureNode)findNode(NodeType.IMAGETEXTURE, name);
	}

	public IndexedFaceSetNode findIndexedFaceSetNode(String name) {
		return (IndexedFaceSetNode)findNode(NodeType.INDEXEDFACESET, name);
	}

	public IndexedLineSetNode findIndexedLineSetNode(String name) {
		return (IndexedLineSetNode)findNode(NodeType.INDEXEDLINESET, name);
	}

	public InlineNode findInlineNode(String name) {
		return (InlineNode)findNode(NodeType.INLINE, name);
	}

	public LODNode findLODNode(String name) {
		return (LODNode)findNode(NodeType.LOD, name);
	}

	public MaterialNode findMaterialNode(String name) {
		return (MaterialNode)findNode(NodeType.MATERIAL, name);
	}

	public MovieTextureNode findMovieTextureNode(String name) {
		return (MovieTextureNode)findNode(NodeType.MOVIETEXTURE, name);
	}

	public NavigationInfoNode findNavigationInfoNode(String name) {
		return (NavigationInfoNode)findNode(NodeType.NAVIGATIONINFO, name);
	}

	public NormalNode findNormalNode(String name) {
		return (NormalNode)findNode(NodeType.NORMAL, name);
	}

	public NormalInterpolatorNode findNormalInterpolatorNode(String name) {
		return (NormalInterpolatorNode)findNode(NodeType.NORMALINTERP, name);
	}

	public OrientationInterpolatorNode findOrientationInterpolatorNode(String name) {
		return (OrientationInterpolatorNode)findNode(NodeType.ORIENTATIONINTERP, name);
	}

	public PixelTextureNode findPixelTextureNode(String name) {
		return (PixelTextureNode)findNode(NodeType.PIXELTEXTURE, name);
	}

	public PlaneSensorNode findPlaneSensorNode(String name) {
		return (PlaneSensorNode)findNode(NodeType.PLANESENSOR, name);
	}

	public PointLightNode findPointLightNode(String name) {
		return (PointLightNode)findNode(NodeType.POINTLIGHT, name);
	}

	public PointSetNode findPointSetNode(String name) {
		return (PointSetNode)findNode(NodeType.POINTSET, name);
	}

	public PositionInterpolatorNode findPositionInterpolatorNode(String name) {
		return (PositionInterpolatorNode)findNode(NodeType.POSITONINTERP, name);
	}

	public ProximitySensorNode findProximitySensorNode(String name) {
		return (ProximitySensorNode)findNode(NodeType.PROXIMITYSENSOR, name);
	}

	public ProxyNode findProxyNode(String name) {
		return (ProxyNode)findNode(NodeType.PROXY, name);
	}

	public ScalarInterpolatorNode findScalarInterpolatorNode(String name) {
		return (ScalarInterpolatorNode)findNode(NodeType.SCALARINTERP, name);
	}

	public ScriptNode findScriptNode(String name) {
		return (ScriptNode)findNode(NodeType.SCRIPT, name);
	}

	public ShapeNode findShapeNode(String name) {
		return (ShapeNode)findNode(NodeType.SHAPE, name);
	}

	public SoundNode findSoundNode(String name) {
		return (SoundNode)findNode(NodeType.SOUND, name);
	}

	public SphereNode findSphereNode(String name) {
		return (SphereNode)findNode(NodeType.SPHERE, name);
	}

	public SphereSensorNode findSphereSensorNode(String name) {
		return (SphereSensorNode)findNode(NodeType.SPHERESENSOR, name);
	}

	public SpotLightNode findSpotLightNode(String name) {
		return (SpotLightNode)findNode(NodeType.SPOTLIGHT, name);
	}

	public SwitchNode findSwitchNode(String name) {
		return (SwitchNode)findNode(NodeType.SWITCH, name);
	}

	public TextNode findTextNode(String name) {
		return (TextNode)findNode(NodeType.TEXT, name);
	}

	public TextureCoordinateNode findTextureCoordinateNode(String name) {
		return (TextureCoordinateNode)findNode(NodeType.TEXTURECOORD, name);
	}

	public TextureTransformNode findTextureTransformNode(String name) {
		return (TextureTransformNode)findNode(NodeType.TEXTURETRANSFORM, name);
	}

	public TimeSensorNode findTimeSensorNode(String name) {
		return (TimeSensorNode)findNode(NodeType.TIMESENSOR, name);
	}

	public TouchSensorNode findTouchSensorNode(String name) {
		return (TouchSensorNode)findNode(NodeType.TOUCHSENSOR, name);
	}

	public TransformNode findTransformNode(String name) {
		return (TransformNode)findNode(NodeType.TRANSFORM, name);
	}

	public ViewpointNode findViewpointNode(String name) {
		return (ViewpointNode)findNode(NodeType.VIEWPOINT, name);
	}

	public VisibilitySensorNode findVisibilitySensorNode(String name) {
		return (VisibilitySensorNode)findNode(NodeType.VISIBILITYSENSOR, name);
	}

	public WorldInfoNode findWorldInfoNode(String name) {
		return (WorldInfoNode)findNode(NodeType.WORLDINFO, name);
	}

	// 9. Networking component (X3D)

	public LoadSensorNode findLoadSensorNode(String name) {
		return (LoadSensorNode)findNode(NodeType.LOADSENSOR, name);
	}

	// 10. Grouping component (X3D)

	public StaticGroupNode findStaticGroupNode(String name) {
		return (StaticGroupNode)findNode(NodeType.STATICGROUP, name);
	}

	// 11. Rendering component (X3D)

	public ColorRGBANode findColorRGBANode(String name) {
		return (ColorRGBANode)findNode(NodeType.COLORRGBA, name);
	}

	public TriangleSetNode findTriangleSetNode(String name) {
		return (TriangleSetNode)findNode(NodeType.TRIANGLESET, name);
	}

	public TriangleFanSetNode findTriangleFanSetNode(String name) {
		return (TriangleFanSetNode)findNode(NodeType.TRIANGLEFANSET, name);
	}

	public TriangleStripSetNode findTriangleStripSetNode(String name) {
		return (TriangleStripSetNode)findNode(NodeType.TRIANGLESTRIPSET, name);
	}
	
	// 12. Shape component (X3D)

	public FillPropertiesNode findFillPropertiesNode(String name) {
		return (FillPropertiesNode)findNode(NodeType.FILLPROPERTIES, name);
	}

	public LinePropertiesNode findLinePropertiesNode(String name) {
		return (LinePropertiesNode)findNode(NodeType.LINEPROPERTIES, name);
	}

	// 14. Geometry2D component (X3D)

	public Arc2DNode findArc2DNode(String name) {
		return (Arc2DNode)findNode(NodeType.ARC2D, name);
	}

	public ArcClose2DNode findArcClose2DNode(String name) {
		return (ArcClose2DNode)findNode(NodeType.ARCCLOSE2D, name);
	}

	public Circle2DNode findCircle2DNode(String name) {
		return (Circle2DNode)findNode(NodeType.CIRCLE2D, name);
	}

	public Disk2DNode findDisk2DNode(String name) {
		return (Disk2DNode)findNode(NodeType.DISK2D, name);
	}

	public Polyline2DNode findPolyline2DNode(String name) {
		return (Polyline2DNode)findNode(NodeType.POLYLINE2D, name);
	}

	public Polypoint2DNode findPolypoint2DNode(String name) {
		return (Polypoint2DNode)findNode(NodeType.POLYPOINT2D, name);
	}

	public Rectangle2DNode findRectangle2DNode(String name) {
		return (Rectangle2DNode)findNode(NodeType.RECTANGLE2D, name);
	}

	public TriangleSet2DNode findTriangleSet2DNode(String name) {
		return (TriangleSet2DNode)findNode(NodeType.TRIANGLESET2D, name);
	}
	
	// 18. Texturing component (x3D)

	public MultiTextureNode findMultiTextureNode(String name) {
		return (MultiTextureNode)findNode(NodeType.MULTITEXTURE, name);
	}

	public MultiTextureCoordinateNode findMultiTextureCoordinateNode(String name) {
		return (MultiTextureCoordinateNode)findNode(NodeType.MULTITEXTURECOORD, name);
	}

	public MultiTextureTransformNode findMultiTextureTransformNode(String name) {
		return (MultiTextureTransformNode)findNode(NodeType.MULTITEXTURETRANSFORM, name);
	}
	
	public TextureCoordinateGeneratorNode findTextureCoordinateGeneratorNode(String name) {
		return (TextureCoordinateGeneratorNode)findNode(NodeType.TEXCOORDGEN, name);
	}
	
	// 19. Interpolation component (X3D)

	public CoordinateInterpolator2DNode findCoordinateInterpolator2DNode(String name) {
		return (CoordinateInterpolator2DNode)findNode(NodeType.COORDINATEINTERPOLATOR2D, name);
	}

	public PositionInterpolator2DNode findPositionInterpolator2DNode(String name) {
		return (PositionInterpolator2DNode)findNode(NodeType.POSITIONINTERPOLATOR2D, name);
	}

	// 21. Key device sensor component (X3D)

	public KeySensorNode findKeySensorNode(String name) {
		return (KeySensorNode)findNode(NodeType.KEYSENSOR, name);
	}

	public StringSensorNode findStringSensorNode(String name) {
		return (StringSensorNode)findNode(NodeType.STRINGSENSOR, name);
	}

	// 30. Event Utilities component (X3D)

	public BooleanFilterNode findBooleanFilterNode(String name) {
		return (BooleanFilterNode)findNode(NodeType.BOOLEANFILTER, name);
	}

	public BooleanToggleNode findBooleanToggleNode(String name) {
		return (BooleanToggleNode)findNode(NodeType.BOOLEANTOGGLE, name);
	}

	public BooleanTriggerNode findBooleanTriggerNode(String name) {
		return (BooleanTriggerNode)findNode(NodeType.BOOLEANTRIGGER, name);
	}

	public BooleanSequencerNode findBooleanSequencerNode(String name) {
		return (BooleanSequencerNode)findNode(NodeType.BOOLEANSEQUENCER, name);
	}

	public IntegerTriggerNode findIntegerTriggerNode(String name) {
		return (IntegerTriggerNode)findNode(NodeType.INTEGERTRIGGER, name);
	}

	public IntegerSequencerNode findIntegerSequencerNode(String name) {
		return (IntegerSequencerNode)findNode(NodeType.INTEGERSEQUENCER, name);
	}

	public TimeTriggerNode findTimeTriggerNode(String name) {
		return (TimeTriggerNode)findNode(NodeType.TIMETRIGGER, name);
	}
	
	// Deprecated components (X3D)

	public NodeSequencerNode findNodeSequencerNode(String name) {
		return (NodeSequencerNode)findNode(NodeType.NODESEQUENCER, name);
	}

	public Shape2DNode findShape2DNode(String name) {
		return (Shape2DNode)findNode(NodeType.SHAPE2D, name);
	}

	public BooleanTimeTriggerNode findBooleanTimeTriggerNode(String name) {
		return (BooleanTimeTriggerNode)findNode(NodeType.BOOLEANTIMETRIGGER, name);
	}

	public Transform2DNode findTransform2DNode(String name) {
		return (Transform2DNode)findNode(NodeType.TRANSFORM2D);
	}
	
	////////////////////////////////////////////////
	//	getNAllNodes
	////////////////////////////////////////////////

	public int getNAllNodes(NodeType nodeType) {
		int nNodes = 0;
		for (Node node=findNode(nodeType); node != null; node = node.nextTraversalSameType())
			nNodes++;
		return nNodes;
	}

	public int getNAllGeometry3DNodes() {
		int nNodes = 0;
		for (Node node=getNodes(); node != null; node = node.nextTraversal()) {
			if (node.isGeometry3DNode() == true)
				nNodes++;
		}
		return nNodes;
	}

	public int getNAllAnchorNodes() {
		return getNAllNodes(NodeType.ANCHOR );
	}

	public int getNAllAppearanceNodes() {
		return getNAllNodes(NodeType.APPEARANCE);
	}

	public int getNAllAudioClipNodes() {
		return getNAllNodes(NodeType.AUDIOCLIP);
	}

	public int getNAllBackgroundNodes() {
		return getNAllNodes(NodeType.BACKGROUND);
	}

	public int getNAllBillboardNodes() {
		return getNAllNodes(NodeType.BILLBOARD);
	}

	public int getNAllBoxNodes() {
		return getNAllNodes(NodeType.BOX);
	}

	public int getNAllCollisionNodes() {
		return getNAllNodes(NodeType.COLLISION);
	}

	public int getNAllColorNodes() {
		return getNAllNodes(NodeType.COLOR);
	}

	public int getNAllColorInterpolatorNodes() {
		return getNAllNodes(NodeType.COLORINTERP);
	}

	public int getNAllConeNodes() {
		return getNAllNodes(NodeType.CONE);
	}

	public int getNAllCoordinateNodes() {
		return getNAllNodes(NodeType.COORD);
	}

	public int getNAllCoordinateInterpolatorNodes() {
		return getNAllNodes(NodeType.COORDINTERP);
	}

	public int getNAllCylinderNodes() {
		return getNAllNodes(NodeType.CYLINDER);
	}

	public int getNAllCylinderSensorNodes() {
		return getNAllNodes(NodeType.CYLINDERSENSOR);
	}

	public int getNAllDirectionalLightNodes() {
		return getNAllNodes(NodeType.DIRLIGHT);
	}

	public int getNAllElevationGridNodes() {
		return getNAllNodes(NodeType.ELEVATIONGRID);
	}

	public int getNAllExtrusionNodes() {
		return getNAllNodes(NodeType.EXTRUSION);
	}

	public int getNAllFogNodes() {
		return getNAllNodes(NodeType.FOG);
	}

	public int getNAllFontStyleNodes() {
		return getNAllNodes(NodeType.FONTSTYLE);
	}

	public int getNAllGroupNodes() {
		return getNAllNodes(NodeType.GROUP);
	}

	public int getNAllImageTextureNodes() {
		return getNAllNodes(NodeType.IMAGETEXTURE);
	}

	public int getNAllIndexedFaceSetNodes() {
		return getNAllNodes(NodeType.INDEXEDFACESET);
	}

	public int getNAllIndexedLineSetNodes() {
		return getNAllNodes(NodeType.INDEXEDLINESET);
	}

	public int getNAllInlineNodes() {
		return getNAllNodes(NodeType.INLINE);
	}

	public int getNAllLODNodes() {
		return getNAllNodes(NodeType.LOD);
	}

	public int getNAllMaterialNodes() {
		return getNAllNodes(NodeType.MATERIAL);
	}

	public int getNAllMovieTextureNodes() {
		return getNAllNodes(NodeType.MOVIETEXTURE);
	}

	public int getNAllNavigationInfoNodes() {
		return getNAllNodes(NodeType.NAVIGATIONINFO);
	}

	public int getNAllNormalNodes() {
		return getNAllNodes(NodeType.NORMAL);
	}

	public int getNAllNormalInterpolatorNodes() {
		return getNAllNodes(NodeType.NORMALINTERP);
	}

	public int getNAllOrientationInterpolatorNodes() {
		return getNAllNodes(NodeType.ORIENTATIONINTERP);
	}

	public int getNAllPixelTextureNodes() {
		return getNAllNodes(NodeType.PIXELTEXTURE);
	}

	public int getNAllPlaneSensorNodes() {
		return getNAllNodes(NodeType.PLANESENSOR);
	}

	public int getNAllPointLightNodes() {
		return getNAllNodes(NodeType.POINTLIGHT);
	}

	public int getNAllPointSetNodes() {
		return getNAllNodes(NodeType.POINTSET);
	}

	public int getNAllPositionInterpolatorNodes() {
		return getNAllNodes(NodeType.POSITONINTERP);
	}

	public int getNAllProximitySensorNodes() {
		return getNAllNodes(NodeType.PROXIMITYSENSOR);
	}

	public int getNAllProxyNodes() {
		return getNAllNodes(NodeType.PROXY);
	}

	public int getNAllScalarInterpolatorNodes() {
		return getNAllNodes(NodeType.SCALARINTERP);
	}

	public int getNAllScriptNodes() {
		return getNAllNodes(NodeType.SCRIPT);
	}

	public int getNAllShapeNodes() {
		return getNAllNodes(NodeType.SHAPE);
	}

	public int getNAllSoundNodes() {
		return getNAllNodes(NodeType.SOUND);
	}

	public int getNAllSphereNodes() {
		return getNAllNodes(NodeType.SPHERE);
	}

	public int getNAllSphereSensorNodes() {
		return getNAllNodes(NodeType.SPHERESENSOR);
	}

	public int getNAllSpotLightNodes() {
		return getNAllNodes(NodeType.SPOTLIGHT);
	}

	public int getNAllSwitchNodes() {
		return getNAllNodes(NodeType.SWITCH);
	}

	public int getNAllTextNodes() {
		return getNAllNodes(NodeType.TEXT);
	}

	public int getNAllTextureCoordinateNodes() {
		return getNAllNodes(NodeType.TEXTURECOORD);
	}

	public int getNAllTextureTransformNodes() {
		return getNAllNodes(NodeType.TEXTURETRANSFORM);
	}

	public int getNAllTimeSensorNodes() {
		return getNAllNodes(NodeType.TIMESENSOR);
	}

	public int getNAllTouchSensorNodes() {
		return getNAllNodes(NodeType.TOUCHSENSOR);
	}

	public int getNAllTransformNodes() {
		return getNAllNodes(NodeType.TRANSFORM);
	}

	public int getNAllViewpointNodes() {
		return getNAllNodes(NodeType.VIEWPOINT);
	}

	public int getNAllVisibilitySensorNodes() {
		return getNAllNodes(NodeType.VISIBILITYSENSOR);
	}

	public int getNAllWorldInfoNodes() {
		return getNAllNodes(NodeType.WORLDINFO);
	}

	// 9. Networking component (X3D)

	public int getNAllLoadSensorNodes() {
		return getNAllNodes(NodeType.LOADSENSOR);
	}

	// 10. Grouping component (X3D)

	public int getNAllStaticGroupNodes() {
		return getNAllNodes(NodeType.STATICGROUP);
	}

	// 11. Rendering component (X3D)

	public int getNAllColorRGBANodes() {
		return getNAllNodes(NodeType.COLORRGBA);
	}

	public int getNAllTriangleSetNodes() {
		return getNAllNodes(NodeType.TRIANGLESET);
	}

	public int getNAllTriangleFanSetNodes() {
		return getNAllNodes(NodeType.TRIANGLEFANSET);
	}

	public int getNAllTriangleStripSetNodes() {
		return getNAllNodes(NodeType.TRIANGLESTRIPSET);
	}
	
	// 12. Shape component (X3D)

	public int getNAllFillPropertiesNodes() {
		return getNAllNodes(NodeType.FILLPROPERTIES);
	}

	public int getNAllLinePropertiesNodes() {
		return getNAllNodes(NodeType.LINEPROPERTIES);
	}

	// 14. Geometry2D component (X3D)

	public int getNAllArc2DNodes() {
		return getNAllNodes(NodeType.ARC2D);
	}

	public int getNAllArcClose2DNodes() {
		return getNAllNodes(NodeType.ARCCLOSE2D);
	}

	public int getNAllCircle2DNodes() {
		return getNAllNodes(NodeType.CIRCLE2D);
	}

	public int getNAllDisk2DNodes() {
		return getNAllNodes(NodeType.DISK2D);
	}

	public int getNAllPolyline2DNodes() {
		return getNAllNodes(NodeType.POLYLINE2D);
	}

	public int getNAllPolypoint2DNodes() {
		return getNAllNodes(NodeType.POLYPOINT2D);
	}

	public int getNAllRectangle2DNodes() {
		return getNAllNodes(NodeType.RECTANGLE2D);
	}

	public int getNAllTriangleSet2DNodes() {
		return getNAllNodes(NodeType.TRIANGLESET2D);
	}
	
	// 18. Texturing component (x3D)

	public int getNAllMultiTextureNodes() {
		return getNAllNodes(NodeType.MULTITEXTURE);
	}

	public int getNAllMultiTextureCoordinateNodes() {
		return getNAllNodes(NodeType.MULTITEXTURECOORD);
	}

	public int getNAllMultiTextureTransformNodes() {
		return getNAllNodes(NodeType.MULTITEXTURETRANSFORM);
	}
	
	public int getNAllTextureCoordinateGeneratorNodes() {
		return getNAllNodes(NodeType.TEXCOORDGEN);
	}
	
	// 19. Interpolation component (X3D)

	public int getNAllCoordinateInterpolator2DNodes() {
		return getNAllNodes(NodeType.COORDINATEINTERPOLATOR2D);
	}

	public int getNAllPositionInterpolator2DNodes() {
		return getNAllNodes(NodeType.POSITIONINTERPOLATOR2D);
	}

	// 21. Key device sensor component (X3D)

	public int getNAllKeySensorNodes() {
		return getNAllNodes(NodeType.KEYSENSOR);
	}

	public int getNAllStringSensorNodes() {
		return getNAllNodes(NodeType.STRINGSENSOR);
	}

	// 30. Event Utilities component (X3D)

	public int getNAllBooleanFilterNodes() {
		return getNAllNodes(NodeType.BOOLEANFILTER);
	}

	public int getNAllBooleanToggleNodes() {
		return getNAllNodes(NodeType.BOOLEANTOGGLE);
	}

	public int getNAllBooleanTriggerNodes() {
		return getNAllNodes(NodeType.BOOLEANTRIGGER);
	}

	public int getNAllBooleanSequencerNodes() {
		return getNAllNodes(NodeType.BOOLEANSEQUENCER);
	}

	public int getNAllIntegerTriggerNodes() {
		return getNAllNodes(NodeType.INTEGERTRIGGER);
	}

	public int getNAllIntegerSequencerNodes() {
		return getNAllNodes(NodeType.INTEGERSEQUENCER);
	}

	public int getNAllTimeTriggerNodes() {
		return getNAllNodes(NodeType.TIMETRIGGER);
	}
	
	// Deprecated components (X3D)

	public int getNAllNodeSequencerNodes() {
		return getNAllNodes(NodeType.NODESEQUENCER);
	}

	public int getNAllShape2DNodes() {
		return getNAllNodes(NodeType.SHAPE2D);
	}

	public int getNAllBooleanTimeTriggerNodes() {
		return getNAllNodes(NodeType.BOOLEANTIMETRIGGER);
	}

	public int getNAllTransform2DNodes() {
		return getNAllNodes(NodeType.TRANSFORM2D);
	}

	///////////////////////////////////////////////
	//	Delete/Remove Node
	///////////////////////////////////////////////

	public void removeNode(Node node) {
		removeNodeRoutes(node);
		node.remove();
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	private float mBoundingBoxCenter[]	= new float[3];
	private float mBoundingBoxSize[]	= new float[3];

	public void setBoundingBoxCenter(float center[]) {
		for (int n=0; n<3; n++)
			mBoundingBoxCenter[n] = center[n];
	}

	public void setBoundingBoxCenter(float x, float y, float z) {
		mBoundingBoxCenter[0] = x;
		mBoundingBoxCenter[1] = y;
		mBoundingBoxCenter[2] = z;
	}

	public void getBoundingBoxCenter(float center[]) {
		for (int n=0; n<3; n++)
			center[n] = mBoundingBoxCenter[n];
	}

	public float[] getBoundingBoxCenter() {
		float center[] = new float[3];
		getBoundingBoxCenter(center);
		return center;
	}

	public void setBoundingBoxSize(float size[]) {
		for (int n=0; n<3; n++)
			mBoundingBoxSize[n] = size[n];
	}
		
	public void setBoundingBoxSize(float x, float y, float z) {
		mBoundingBoxSize[0] = x;
		mBoundingBoxSize[1] = y;
		mBoundingBoxSize[2] = z;
	}

	public void getBoundingBoxSize(float size[]) {
		for (int n=0; n<3; n++)
			size[n] = mBoundingBoxSize[n];
	}

	public float[] getBoundingBoxSize() {
		float size[] = new float[3];
		getBoundingBoxSize(size);
		return size;
	}

	public void updateBoundingBox(Node node, BoundingBox bbox) {
		//if (node.isGroupingNode() == true) {
		if (node.isGeometry3DNode() == true) {
			//GroupingNode gnode = (GroupingNode)node;
			Geometry3DNode gnode = (Geometry3DNode)node;

			float bboxCenter[]	= new float[3];
			float bboxSize[]	= new float[3];
			float point[]		= new float[3];

			gnode.getBoundingBoxCenter(bboxCenter);
			gnode.getBoundingBoxSize(bboxSize);
			
			if (bboxSize[0] >= 0.0f && bboxSize[1] >= 0.0f && bboxSize[2] >= 0.0f) {
				SFMatrix nodemx = node.getTransformMatrix();
				for (int n=0; n<8; n++) {
					point[0] = (n < 4)				? bboxCenter[0] - bboxSize[0] : bboxCenter[0] + bboxSize[0];
					point[1] = ((n % 2) != 0)	? bboxCenter[1] - bboxSize[1] : bboxCenter[1] + bboxSize[1];
					point[2] = ((n % 4) < 2)		? bboxCenter[2] - bboxSize[2] : bboxCenter[2] + bboxSize[2];
					nodemx.multi(point);
					bbox.addPoint(point);
				}
			}
		}
		
		for (Node cnode=node.getChildNodes(); cnode != null; cnode=cnode.next()) 
			updateBoundingBox(cnode, bbox);
	}
	
	public void updateBoundingBox() {
		BoundingBox bbox = new BoundingBox();
		for (Node node=getNodes(); node != null; node=node.next())
			updateBoundingBox(node, bbox);
		setBoundingBoxCenter(bbox.getCenter());
		setBoundingBoxSize(bbox.getSize());
	}
				
	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public float getRadius() {
		SFVec3f bboxSize = new SFVec3f(getBoundingBoxSize());
		return bboxSize.getScalar(); 
	}
	
}