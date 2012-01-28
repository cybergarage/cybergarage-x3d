/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SceneGraphJ3dObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import java.io.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.picking.*;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.Debug;

public class SceneGraphJ3dObject extends Object implements org.cybergarage.x3d.SceneGraphObject {

	private VirtualUniverse	mUniverse3D				= null;
	private Locale				mLocale					= null;
	private RootNodeObject	mRootNode				= null;	
	private Background		mBackground				= null;
	private boolean			mIsBranchGroupAdded	= false;
	private AmbientLight		mAmbientLight			= null;
	private PointLight		mPointLight				= null;

	private View				mView[]						= null;
	private ViewPlatform		mViewPlatform[]				= null;
	private TransformGroup		mViewTransformGroup[]		= null;
	private TransformGroup		mCanvas3DTransformGroup[]	= null;
	private Canvas3D			mCanvas3D[]					= null;

	public SceneGraphJ3dObject(SceneGraph sg) 
	{
		// Create a root node
		initializeRootNode(sg);
		setCanvas3D(new Canvas3D[0]);
	}
							
	public SceneGraphJ3dObject(Canvas3D canvases[], SceneGraph sg, boolean initScene) {
		initialize(canvases, sg, initScene);
	}

	public SceneGraphJ3dObject(Canvas3D canvases[], SceneGraph sg) {
		initialize(canvases, sg, true);
	}

	public SceneGraphJ3dObject(Canvas3D canvas3D, SceneGraph sg, boolean initScene) {
		Canvas3D canvases[] = new Canvas3D[1];
		canvases[0] = canvas3D;
		initialize(canvases, sg, initScene);
	}

	public SceneGraphJ3dObject(Canvas3D canvas3D, SceneGraph sg) {
		Canvas3D canvases[] = new Canvas3D[1];
		canvases[0] = canvas3D;
		initialize(canvases, sg, true);
	}

	///////////////////////////////////////////////
	// initializeRootNode/Scene
	///////////////////////////////////////////////

	public void initialize(Canvas3D canvases[], SceneGraph sg, boolean initScene) {
		initializeRootNode(sg);
		setCanvas3D(canvases);
		if (initScene == true)
			initializeScene(canvases, sg);
	}

	private void initializeRootNode(SceneGraph sg) {
		// Create a root node
		RootNode rootNode = sg.getRootNode();
		mRootNode = new RootNodeObject(rootNode);	
		rootNode.setObject(mRootNode); 
	}
	
	private void initializeScene(Canvas3D canvas3D[], SceneGraph sg) 
	{

		mUniverse3D = new VirtualUniverse();
		mLocale		= new Locale(mUniverse3D);
		
		int nCanvas3D = canvas3D.length;
		
		mView					= new View[nCanvas3D];
		mViewPlatform			= new ViewPlatform[nCanvas3D];
		mViewTransformGroup		= new TransformGroup[nCanvas3D];
		mCanvas3DTransformGroup	= new TransformGroup[nCanvas3D];
		
		// Common physical body and environment
		PhysicalBody body = new PhysicalBody();
		PhysicalEnvironment environment = new PhysicalEnvironment();
		
		for (int n=0; n<nCanvas3D; n++) {
/*
			// TransformGroup for Canvas3D
			mCanvas3DTransformGroup[n] = new TransformGroup();
			mCanvas3DTransformGroup[n].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
			mCanvas3DTransformGroup[n].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			mRootNode.addChild(mCanvas3DTransformGroup[n]);

			// TransformGroup for View
			mViewTransformGroup[n] = new TransformGroup();
			mViewTransformGroup[n].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
			mViewTransformGroup[n].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			mCanvas3DTransformGroup[n].addChild(mViewTransformGroup[n]);

			// ViewPlatform
			mViewPlatform[n] = new ViewPlatform();
			mViewPlatform[n].setActivationRadius(1000.0f);
			mViewTransformGroup[n].addChild(mViewPlatform[n]);
*/
			// TransformGroup for View
			mViewTransformGroup[n] = new TransformGroup();
			mViewTransformGroup[n].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
			mViewTransformGroup[n].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			mRootNode.addChild(mViewTransformGroup[n]);

			// TransformGroup for Canvas3D
			mCanvas3DTransformGroup[n] = new TransformGroup();
			mCanvas3DTransformGroup[n].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
			mCanvas3DTransformGroup[n].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			mViewTransformGroup[n].addChild(mCanvas3DTransformGroup[n]);

			// ViewPlatform
			mViewPlatform[n] = new ViewPlatform();
			mViewPlatform[n].setActivationRadius(Float.MAX_VALUE);
			mCanvas3DTransformGroup[n].addChild(mViewPlatform[n]);
			
			// Create the view
			mView[n] = new View();
			//mView[n].setProjectionPolicy(View.PARALLEL_PROJECTION);
			//mView[n].setMonoscopicViewPolicy(View.LEFT_EYE_VIEW);
			//mView[n].setViewPolicy(View.HMD_VIEW);
			mView[n].addCanvas3D(canvas3D[n]);
			mView[n].setPhysicalBody(body);
			mView[n].setPhysicalEnvironment(environment);
			mView[n].attachViewPlatform(mViewPlatform[n]);
		}
			
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);
		// Add a background color
		Color3f bgColor = new Color3f(0.0f, 0.0f, 0.0f);
		mBackground = new Background(bgColor);
		mBackground.setApplicationBounds(bounds);
		mBackground.setCapability(Background.ALLOW_APPLICATION_BOUNDS_READ);
		mBackground.setCapability(Background.ALLOW_APPLICATION_BOUNDS_WRITE);
		mBackground.setCapability(Background.ALLOW_COLOR_READ);
		mBackground.setCapability(Background.ALLOW_COLOR_WRITE);
		mRootNode.addChild(mBackground);
	
		// Create an ambient Light
		mAmbientLight = new AmbientLight(new Color3f(0.0f, 0.0f, 0.0f));
		mAmbientLight.setCapability(AmbientLight.ALLOW_INFLUENCING_BOUNDS_READ);
		mAmbientLight.setCapability(AmbientLight.ALLOW_INFLUENCING_BOUNDS_WRITE);
		mAmbientLight.setCapability(AmbientLight.ALLOW_COLOR_READ);
		mAmbientLight.setCapability(AmbientLight.ALLOW_COLOR_WRITE);
		mAmbientLight.setEnable(false);
		mRootNode.addChild(mAmbientLight);

		// Create a headlight
		mPointLight = new PointLight();
		mPointLight.setColor(new Color3f(0.8f, 0.8f, 0.8f));
		mPointLight.setCapability(PointLight.ALLOW_INFLUENCING_BOUNDS_READ);
		mPointLight.setCapability(PointLight.ALLOW_INFLUENCING_BOUNDS_WRITE);
		mPointLight.setCapability(PointLight.ALLOW_STATE_READ);
		mPointLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		mPointLight.setCapability(PointLight.ALLOW_ATTENUATION_READ);
		mPointLight.setCapability(PointLight.ALLOW_ATTENUATION_WRITE);
		mPointLight.setCapability(PointLight.ALLOW_POSITION_READ);
		mPointLight.setCapability(PointLight.ALLOW_POSITION_WRITE);
		mPointLight.setAttenuation(1, 0, 0);
		mPointLight.setEnable(false);
		BoundingSphere bound = new BoundingSphere(new Point3d(), Double.MAX_VALUE);
		mPointLight.setInfluencingBounds(bound);
		mRootNode.addChild(mPointLight);
	
		addBranchGroup();
	}
	
	///////////////////////////////////////////////
	// Access Members
	///////////////////////////////////////////////
	
	public RootNodeObject getRootNode() {
		return mRootNode;
	}

	public BranchGroup getBranchGroup() {
		return mRootNode;
	}

	public Locale getLocale() {
		return mLocale;
	}

	public Background getBackground() {
		return mBackground;
	}

	private void setCanvas3D(Canvas3D canvas3D[])
	{
		mCanvas3D = canvas3D;
	}

	public int getNCanvas3Ds() {
		if (mCanvas3D == null)
			return 0;
		return mCanvas3D.length;
	}
	
	public Canvas3D[] getCanvas3Ds() {
		return mCanvas3D;
	}
	
	public Canvas3D getCanvas3D(int n) {
		return mCanvas3D[n];
	}


	public View[] getViews() {
		return mView;
	}

	public View getView(int n) {
		return mView[n];
	}

	public TransformGroup[] getViewTransformGroups() {
		return mViewTransformGroup;
	}

	public void setViewTransformGroup(int n, Transform3D t) {
		mViewTransformGroup[n].setTransform(t);
	}

	public TransformGroup getViewTransformGroup(int n) {
		return mViewTransformGroup[n];
	}


	public TransformGroup[] getCanvas3DTransformGroups() {
		return mCanvas3DTransformGroup;
	}

	public void setCanvas3DTransformGroup(int n, Transform3D t) {
		mCanvas3DTransformGroup[n].setTransform(t);
	}
	
	public TransformGroup getCanvas3DTransformGroup(int n) {
		return mCanvas3DTransformGroup[n];
	}


	public AmbientLight getAmbientLight() {
		return mAmbientLight;
	}

	public PointLight getHeadlight() {
		return mPointLight;
	}

	///////////////////////////////////////////////
	// Headlight
	///////////////////////////////////////////////
	
	public void setHeadlightState(boolean state) {
		if (mPointLight == null)
			return;
		mPointLight.setEnable(state);
	}

	public boolean isHeadlightOn() {
		if (mPointLight == null)
			return false;
		return mPointLight.getEnable();
	}

	///////////////////////////////////////////////
	// Add/Remove BranchGroup
	///////////////////////////////////////////////
	
	public void addBranchGroup() {
		if (mLocale == null)
			return;
		if (mIsBranchGroupAdded == false) {
			mLocale.addBranchGraph(mRootNode);
			mIsBranchGroupAdded = true;
		}
	}

	public void removeBranchGroup() {
		if (mLocale == null)
			return;
		if (mIsBranchGroupAdded == true) {
			mLocale.removeBranchGraph(mRootNode);
			mIsBranchGroupAdded = false;
		}
	}

	///////////////////////////////////////////////
	// Add/Remove Node
	///////////////////////////////////////////////
	
	public boolean addNode(SceneGraph sg, org.cybergarage.x3d.node.Node node) {
		Debug.message("SceneGraphJ3dObject::addNode = " + sg + ", " + node);
		if (node.isRootNode() == true) {
			//Debug.warning("\tCouldn't add a root node !!");
			return false;
		}
		removeBranchGroup();
		boolean ret = node.addObject();
		addBranchGroup();
		return ret;
	}
	
	public boolean removeNode(SceneGraph sg, org.cybergarage.x3d.node.Node node) {
		Debug.message("SceneGraphJ3dObject::removeNode = " + sg + ", " + node);
		if (node.isRootNode() == true) {
			//Debug.warning("\tCouldn't remove a root node !!");
			return false;
		}
		removeBranchGroup();
		boolean ret = node.removeObject();
		addBranchGroup();
		return ret;
	}
								
	///////////////////////////////////////////////
	// Initialize / Uninitialize
	///////////////////////////////////////////////
	
	public boolean initialize(SceneGraph sg) {

		Debug.message("SceneGraphJ3DObject::initialize");
		
		removeBranchGroup();
		
		for (org.cybergarage.x3d.node.Node node = sg.getNodes(); node != null; node = node.nextTraversal()) {
			if (node.hasObject() == false)
				node.recreateNodeObject();
		}

		update(sg);
		
		float sceneGraphRadius = sg.getRadius();

		Debug.message("\tradius = " + sceneGraphRadius);
		
		int nViews = getNCanvas3Ds();
		for (int n=0; n<nViews; n++) {
			View view = getView(n);
			//view.setBackClipPolicy(View.VIRTUAL_EYE);
			//view.setFrontClipPolicy(View.VIRTUAL_EYE);
			//view.setVisibilityPolicy(View.VISIBILITY_DRAW_ALL);
			// X view.setBackClipPolicy(View.PHYSICAL_SCREEN);
			// X view.setFrontClipPolicy(View.PHYSICAL_SCREEN);
			//view.setBackClipPolicy(View.VIRTUAL_SCREEN);
			//view.setFrontClipPolicy(View.VIRTUAL_SCREEN);
			//view.setBackClipPolicy(View.PHYSICAL_EYE);
			//view.setFrontClipPolicy(View.PHYSICAL_EYE);
			//view.setBackClipPolicy(View.VIRTUAL_SCREEN);
			//view.setFrontClipPolicy(View.VIRTUAL_SCREEN);
			view.setBackClipDistance(sceneGraphRadius * 10.0f);
			view.setFrontClipDistance(sceneGraphRadius / 1000.0f);
			Debug.message("\tfrontClipPolicy = " + view.getFrontClipPolicy());
			Debug.message("\tbackClipPolicy = " + view.getBackClipPolicy());
			Debug.message("\tfrontClipDistance = " + view.getFrontClipDistance());
			Debug.message("\tbackClipDistance = " + view.getBackClipDistance());
		}
		
		Background bg = getBackground();
		if (bg != null) {
			float bboxCenter[] = sg.getBoundingBoxCenter();
			Point3d center = new Point3d(bboxCenter[0], bboxCenter[1], bboxCenter[2]);
			BoundingSphere bounds = new BoundingSphere(center, sceneGraphRadius);
			bg.setApplicationBounds(bounds);
		}
			
		addBranchGroup();
		
		return true;
	}

	public boolean uninitialize(SceneGraph sg) {
		return true;
	}
	
	///////////////////////////////////////////////
	// Update
	///////////////////////////////////////////////

	public Bounds getSceneGraphBounds(SceneGraph sg, ViewpointNode viewNode) {
		float viewPos[] = new float[3];
		viewNode.getPosition(viewPos);
		float sgRadius = sg.getRadius() * 1000.0f;
		if (sgRadius == 0.0) 
			sgRadius = 1000.0f;
		return new BoundingSphere(new Point3d(viewPos[0], viewPos[1], viewPos[2]), sgRadius);
	}	
	
	public void updateHeadlight(SceneGraph sg, ViewpointNode viewNode) {
	
		PointLight light = getHeadlight();
		
		if (light == null)
			return;
			
		// Update Position
		float pos[]	= new float[3];
		viewNode.getPosition(pos);
		light.setPosition(new Point3f(pos));
		
		// Update InfluencingBounds
		light.setInfluencingBounds(getSceneGraphBounds(sg, viewNode));
	}
	
	public void updateViewInfomation(ViewpointNode viewNode) {
	
		float position[]	= new float[3];
		float orientation[]	= new float[4];
		viewNode.getPosition(position);
		viewNode.getOrientation(orientation);
		float fov = viewNode.getFieldOfView();
		
		int nViews = getNCanvas3Ds();
		for (int n=0; n<nViews; n++) {				
			View view = getView(n);
			
			TransformGroup	viewTrans = getViewTransformGroup(n);
			Transform3D trans3D = new Transform3D();
			viewTrans.getTransform(trans3D);
			Vector3f vector = new Vector3f(position);
			trans3D.setTranslation(vector);
			AxisAngle4f axisAngle = new AxisAngle4f(orientation);
			trans3D.setRotation(axisAngle);
			viewTrans.setTransform(trans3D);
	
			view.setFieldOfView(fov);

			Debug.message("SceneGraphJ3dObject::updateViewInfomation");
			Debug.message("\tviewpos = " + position[0] + ", " + position[1] + ", " + position[2]);
			Debug.message("\tfrontClipDistance = " + view.getFrontClipDistance());
			Debug.message("\tbackClipDistance = " + view.getBackClipDistance());
		}
		
	}

	public void updateBackground(SceneGraph sg, BackgroundNode bgNode, ViewpointNode viewNode) {
		
		Background bg = getBackground();
		if (bg == null)
			return;
			
		// Update Color
		Color3f bgColor = null;
		if (0 < bgNode.getNSkyColors()) {
			float color[] = new float[3];
			bgNode.getSkyColor(0, color);
			bgColor = new Color3f(color);
		}
		else
			bgColor = new Color3f(0.0f, 0.0f, 0.0f);
		bg.setColor(bgColor);

		// Update ApplicationBounds
		bg.setApplicationBounds(getSceneGraphBounds(sg, viewNode));
	}
	
	public void updateNodes(SceneGraph sg) {
		for (org.cybergarage.x3d.node.Node node = sg.getNodes(); node != null; node = node.nextTraversal())
			node.updateObject();
	}
		
	public boolean update(SceneGraph sg) {
		
		ViewpointNode view = sg.getViewpointNode();
		if (view == null)
			view = sg.getDefaultViewpointNode(); 
		
		updateViewInfomation(view);

		NavigationInfoNode navInfo = sg.getNavigationInfoNode();
		if (navInfo == null)
			navInfo = sg.getDefaultNavigationInfoNode();
			
		boolean headlightOn = navInfo.getHeadlight();
		setHeadlightState(headlightOn);
		if (headlightOn == true)
			updateHeadlight(sg, view);
		
		BackgroundNode bg = sg.getBackgroundNode();
		if (bg == null)
			bg = sg.getDefaultBackgroundNode();
		updateBackground(sg, bg, view);

		updateNodes(sg);
		return true;
	}
	
	///////////////////////////////////////////////
	// remove
	///////////////////////////////////////////////
	
	public boolean remove(SceneGraph sg) {
		removeBranchGroup();
		
		for (org.cybergarage.x3d.node.Node node = sg.getNodes(); node != null; node = node.nextTraversal()) {
			NodeObject nodeObject = node.getObject();
			if (nodeObject != null)
				nodeObject.remove(node);
		}

		addBranchGroup();
		
		return true;
	}

	///////////////////////////////////////////////
	// Start / Stop
	///////////////////////////////////////////////

	public boolean	start(SceneGraph sg) {
		Debug.message("SceneGraphJ3dObject.start");
		
		int nCanvas3D = getNCanvas3Ds();
		for (int n=0; n<nCanvas3D; n++) {
			View view = getView(n);
			view.startBehaviorScheduler();
			view.startView();
 			getCanvas3D(n).startRenderer();
		}
			
		return true;
	}
	
	public boolean	stop(SceneGraph sg) {
		Debug.message("SceneGraphJ3dObject.stop");

		int nCanvas3D = getNCanvas3Ds();
		for (int n=0; n<nCanvas3D; n++) {
			View view = getView(n);
			view.stopBehaviorScheduler();
			view.stopView();
 			getCanvas3D(n).stopRenderer();
		}
						
		return true;
	}
	
	///////////////////////////////////////////////
	// Create J3D Node
	///////////////////////////////////////////////
	
	public NodeObject createNodeObject(SceneGraph sg, org.cybergarage.x3d.node.Node node) {
		NodeObject nodeObject = null;
			
		if (node.isAnchorNode())
			nodeObject = new AnchorNodeObject((AnchorNode)node);
		else if (node.isAppearanceNode()) 
			nodeObject = new AppearanceNodeObject((AppearanceNode)node);
/*
		else if (isAudioClipNode())
			nodeObject = new AudioClipNode();
*/
		else if (node.isBackgroundNode())
			nodeObject = null;
		else if (node.isBillboardNode())
			nodeObject = new BillboardNodeObject((BillboardNode)node);
		else if (node.isBoxNode())
			nodeObject = new BoxNodeObject((BoxNode)node);
		else if (node.isCollisionNode())
			nodeObject = new CollisionNodeObject((CollisionNode)node);
		else if (node.isColorNode())
			nodeObject = null;
		else if (node.isColorInterpolatorNode())
			nodeObject = null;
		else if (node.isConeNode())
			nodeObject = new ConeNodeObject((ConeNode)node);
		else if (node.isCoordinateNode())
			nodeObject = null;
		else if (node.isCoordinateInterpolatorNode())
			nodeObject = null;
		else if (node.isCylinderNode())
			nodeObject = new CylinderNodeObject((CylinderNode)node);
/*
		else if (isCylinderSensorNode())
			nodeObject = new CylinderSensorNode();
*/
		else if (node.isDirectionalLightNode())
			nodeObject = new DirectionalLightNodeObject((DirectionalLightNode)node);

		else if (node.isElevationGridNode())
			nodeObject = new ElevationGridNodeObject((ElevationGridNode)node);
		else if (node.isExtrusionNode())
			nodeObject = new ExtrusionNodeObject((ExtrusionNode)node);
		else if (node.isFogNode()) {
			FogNode fogNode = (FogNode)node;
			if (fogNode.isLiner() == true)
				nodeObject = new LinerFogNodeObject(fogNode);
			if (fogNode.isExponential() == true)
				nodeObject = new ExponentialFogNodeObject(fogNode);
		}
		else if (node.isFontStyleNode())
			nodeObject = null;
		else if (node.isGroupNode())
			nodeObject = new GroupNodeObject((GroupNode)node);
		else if (node.isImageTextureNode()) {
			nodeObject = null;
			ImageTextureNode imgTex = (ImageTextureNode)node;
			Canvas3D canvas3d[] = getCanvas3Ds();
			if (0 < canvas3d.length) {
				ImageTextureLoader imgTexLoader = new ImageTextureLoader(imgTex, canvas3d[0]);
				if (imgTexLoader.hasComponent() == true)
					nodeObject = new ImageTextureNodeObject(imgTexLoader);
			}
		}
		else if (node.isIndexedFaceSetNode())
			nodeObject = new IndexedFaceSetNodeObject((IndexedFaceSetNode)node);
		else if (node.isIndexedLineSetNode()) 
			nodeObject = new IndexedLineSetNodeObject((IndexedLineSetNode)node);
		else if (node.isInlineNode()) 
			nodeObject = new InlineNodeObject((InlineNode)node);
		else if (node.isLODNode())
			nodeObject = new LODNodeObject((LODNode)node);
		else if (node.isMaterialNode())
			nodeObject = new MaterialNodeObject((MaterialNode)node);
/*
		else if (isMovieTextureNode())
			nodeObject = new MovieTextureNode();
*/
		else if (node.isNavigationInfoNode())
			nodeObject = null;
		else if (node.isNormalNode())
			nodeObject = null;
		else if (node.isNormalInterpolatorNode())
			nodeObject = null;
		else if (node.isOrientationInterpolatorNode())
			nodeObject = null;
		else if (node.isPixelTextureNode())
			nodeObject = new PixelTextureNodeObject((PixelTextureNode)node);
/*
		else if (isPlaneSensorNode())
			nodeObject = new PlaneSensorNode();
*/
		else if (node.isPointLightNode())
			nodeObject = new PointLightNodeObject((PointLightNode)node);
		else if (node.isPointSetNode())
			nodeObject = new PointSetNodeObject((PointSetNode)node);
		else if (node.isPositionInterpolatorNode())
			nodeObject = null;
/*
		else if (isProximitySensorNode())
			nodeObject = new ProximitySensorNode();
*/
		else if (node.isScalarInterpolatorNode())
			nodeObject = null;
		else if (node.isScriptNode())
			nodeObject = null;
		else if (node.isShapeNode())
			nodeObject = new ShapeNodeObject((ShapeNode)node);
/*
		else if (isSoundNode())
			nodeObject = new SoundNode();
*/
		else if (node.isSphereNode())
			nodeObject = new SphereNodeObject((SphereNode)node);
/*
		else if (isSphereSensorNode())
			nodeObject = new SphereSensorNode();
*/
		else if (node.isSpotLightNode())
			nodeObject = new SpotLightNodeObject((SpotLightNode)node);
		else if (node.isSwitchNode())
			nodeObject = new SwitchNodeObject((SwitchNode)node);
		else if (node.isTextNode())
			nodeObject = new TextNodeObject((TextNode)node);
/*
		else if (isTextureCoordinateNode())
			nodeObject = new TextureCoordinateNode();
*/
		else if (node.isTextureTransformNode())
			nodeObject = new TextureTransformNodeObject((TextureTransformNode)node);
/*
		else if (isTimeSensorNode())
			nodeObject = new TimeSensorNode();
		else if (isTouchSensorNode())
			nodeObject = new TouchSensorNode();
*/
		else if (node.isTransformNode())
			nodeObject = new TransformNodeObject((TransformNode)node);
		else if (node.isViewpointNode())
			nodeObject = null;
/*
		else if (isVisibilitySensorNode())
			nodeObject = new VisibilitySensorNode();
*/
		else if (node.isWorldInfoNode())
			nodeObject = null;
			
		return nodeObject;
	}

	////////////////////////////////////////////////
	// Redering mode
	////////////////////////////////////////////////

	public boolean setRenderingMode(SceneGraph sg, int mode) {
		
		for (ShapeNode shapeNode = sg.findShapeNode(); shapeNode != null; shapeNode = (ShapeNode)shapeNode.nextTraversalSameType()) {
			ShapeNodeObject shapeObject = (ShapeNodeObject)shapeNode.getObject();
			if (shapeNode == null)
				continue;
			Appearance app = shapeObject.getAppearance();
			if (app == null)
				continue;
			PolygonAttributes polyAttr = app.getPolygonAttributes(); 
			if (polyAttr == null)
				continue;
			if (mode == SceneGraph.RENDERINGMODE_LINE) 
				polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
			else if (mode == SceneGraph.RENDERINGMODE_FILL)
				polyAttr.setPolygonMode(PolygonAttributes.POLYGON_FILL);
			app.setPolygonAttributes(polyAttr);
		}
		
		return true;
	}
	
	////////////////////////////////////////////////
	// pick object
	////////////////////////////////////////////////

	public Shape3D pickShape3D(Canvas3D canvas3D, int mx, int my) 
	{
		PickCanvas pickObject = new PickCanvas(canvas3D, mRootNode);
		pickObject.setShapeLocation(mx, my);
		PickResult pickResult = pickObject.pickClosest();

		if (pickResult == null)
			return null;
		Shape3D shape3d = (Shape3D)pickResult.getNode(PickResult.SHAPE3D);
		
		return shape3d;
	}

	public ShapeNode pickShapeNode(SceneGraph sg, Canvas3D canvas3D, int mx, int my) {
		Debug.message("SceneGraphJ3dObject::pickShapeNode = " + mx + ", " + my);		
		
		Shape3D pickedShape3D = pickShape3D(canvas3D, mx, my);
		
		ShapeNode pickedShapeNode = null;
		
		if (pickedShape3D != null) {
			for (ShapeNode shape = sg.findShapeNode(); shape != null; shape = (ShapeNode)shape.nextTraversalSameType()) {
				if (shape.getObject() == pickedShape3D) {
					pickedShapeNode = shape;
					break;
				}
			}
		}
		
		Debug.message("\tPicked Shape3D = " + pickedShape3D);		
		Debug.message("\tPicked Shape   = " + pickedShapeNode);
		
		return pickedShapeNode;
	}

	////////////////////////////////////////////////
	// Infomation
	////////////////////////////////////////////////

	public void addNodeString(StringBuffer stringBuffer, javax.media.j3d.Node node, int indent) {
		int n;
		
		for (n=0; n<indent; n++) 
			stringBuffer.append('\t');
		stringBuffer.append(node.toString());
		stringBuffer.append('\n');
		
		try {
			Group gnode = (Group)node;
			for (n=0; n<gnode.numChildren(); n++)
				addNodeString(stringBuffer, gnode.getChild(n), indent + 1);
		}
		catch (CapabilityNotSetException e) {}
		catch (ClassCastException e) {}
	}
	
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		addNodeString(stringBuffer, getRootNode(), 0);
		return stringBuffer.toString();
	}

	////////////////////////////////////////////////
	// print
	////////////////////////////////////////////////

	public void outputNode(PrintStream out, org.cybergarage.x3d.node.Node node, int indentLevel) 
	{
		for (int n=0; n<indentLevel; n++)
			out.print("\t");
			
		String nodeName = node.getName();
		String nodeType = node.getTypeString();
		NodeObject nodeObj = node.getObject();

		if (node.isInstanceNode() == false) {
			if (nodeName != null && 0 < nodeName.length())
				out.print("DEF " + nodeName + " ");
			out.println(nodeType + " = " + nodeObj);
		}
		else
			out.println("USE " + nodeType + " ");
			
		for (org.cybergarage.x3d.node.Node cnode = node.getChildNodes(); cnode != null; cnode = cnode.next())
			outputNode(out, cnode, indentLevel+1);
	}

	public void print(SceneGraph sg)
	{
		for (org.cybergarage.x3d.node.Node node = sg.getNodes(); node != null; node = node.next())
			outputNode(System.out, node, 0);
	}
}
