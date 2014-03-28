/******************************************************************
*
*	VRML97Saver for CyberVRML97
*
*	Copyright (C) Satoshi Konno 1999
*
*	File:	VRML97Saver.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import java.awt.image.*;
import java.util.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.Debug;

public class VRML97Saver extends org.cybergarage.x3d.SceneGraph {
	
	public VRML97Saver() {
	}
	
	public void setBranchGroup(BranchGroup bgroup) {
		initializeVRML97NodeStack();
		clear();
		addJ3dGroupNode(bgroup);
	}

	public void addJ3dGroupNode(Group j3dGroup) {
		if (j3dGroup == null)
			return;
			
		org.cybergarage.x3d.node.Node vrml97Node = addJ3dNode(j3dGroup);
		
		if (vrml97Node == null)
			return;

		pushVRML97Node(vrml97Node);
		
		setCapability(j3dGroup, Group.ALLOW_CHILDREN_READ);
		
		int numChildren = j3dGroup.numChildren(); 
		for (int n=0; n<numChildren; n++) {
			javax.media.j3d.Node j3dChildNode = j3dGroup.getChild(n);
			if (j3dChildNode instanceof javax.media.j3d.Group)
				addJ3dGroupNode((javax.media.j3d.Group)j3dChildNode);
			else
				addJ3dNode(j3dChildNode);
		}

		popVRML97Node();
	}
	
	public org.cybergarage.x3d.node.Node addJ3dNode(javax.media.j3d.Node j3dNode) {
		org.cybergarage.x3d.node.Node vrml97Node = createVRML97Node(j3dNode);
		if (vrml97Node != null)
			getCurrentVRML97Node().addChildNode(vrml97Node);
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// Node Stack
	/////////////////////////////////////////////
	
	private Stack mVRML97NodeStack = new Stack();
	
	public void initializeVRML97NodeStack() {
		mVRML97NodeStack.clear();
	}
	
	public void pushVRML97Node(org.cybergarage.x3d.node.Node node) {
		mVRML97NodeStack.push(node);
	}

	public org.cybergarage.x3d.node.Node popVRML97Node() {
		return (org.cybergarage.x3d.node.Node)mVRML97NodeStack.pop();
	}

	public org.cybergarage.x3d.node.Node getCurrentVRML97Node() {
		org.cybergarage.x3d.node.Node stackNode = null;
		try {
			stackNode = (org.cybergarage.x3d.node.Node)mVRML97NodeStack.peek();
		}
		catch (EmptyStackException ese) {
			return getRootNode();
		}
		if (stackNode != null)
			return stackNode;
		return getRootNode();
	}

	public void setCapability(javax.media.j3d.SceneGraphObject j3dNode, int bit) 
	{
		try {
			j3dNode.setCapability(bit);
		}
		catch (RestrictedAccessException rae) {}
	}
	
	/////////////////////////////////////////////
	// J3D Node ---> VRML97 Node
	/////////////////////////////////////////////
	
	public org.cybergarage.x3d.node.Node createVRML97Node(javax.media.j3d.Node j3dNode) {
		
		if (j3dNode instanceof Background)
			return createVRML97BackgroundNode((Background)j3dNode);
			
		if (j3dNode instanceof Billboard)
			return createVRML97BillboardNode((Billboard)j3dNode);

		if (j3dNode instanceof LinearFog)
			return createVRML97FogNode((LinearFog)j3dNode);

		if (j3dNode instanceof ExponentialFog)
			return createVRML97FogNode((ExponentialFog)j3dNode);
			
		if (j3dNode instanceof Switch)
			return createVRML97SwitchNode((Switch)j3dNode);
			
		if (j3dNode instanceof DirectionalLight)
			return createVRML97DirectionalLightNode((DirectionalLight)j3dNode);

		if (j3dNode instanceof PointLight)
			return createVRML97PointLightNode((PointLight)j3dNode);

		if (j3dNode instanceof SpotLight)
			return createVRML97SpotLightNode((SpotLight)j3dNode);

		if (j3dNode instanceof TransformGroup)
			return createVRML97TransformNode((TransformGroup)j3dNode);
			
		if (j3dNode instanceof AmbientLight)
			return createVRML97PointLightNode((AmbientLight)j3dNode);
			
		if (j3dNode instanceof Shape3D)
			return createVRML97ShapeNode((Shape3D)j3dNode);
			
		if (j3dNode instanceof Group)
			return new GroupNode();

		return null;
	}

	/////////////////////////////////////////////
	// Background ---> BackgroundNode 
	/////////////////////////////////////////////
	
	public BackgroundNode createVRML97BackgroundNode(Background j3dNode) {

		setCapability(j3dNode, Background.ALLOW_COLOR_READ);
 		
		BackgroundNode	vrml97Node	= new BackgroundNode();

		Color3f color = new Color3f();
		j3dNode.getColor(color);
		vrml97Node.addSkyColor(color.x, color.y, color.z);
				
		return vrml97Node;
	}
	
	/////////////////////////////////////////////
	// Billboard ---> BillboardNode 
	/////////////////////////////////////////////
	
	public BillboardNode createVRML97BillboardNode(Billboard j3dNode) {
		
		BillboardNode	vrml97Node	= new BillboardNode();

		if (j3dNode.getAlignmentMode() == Billboard.ROTATE_ABOUT_AXIS) {
			Vector3f axis = new Vector3f();
			j3dNode.getAlignmentAxis(axis);
			vrml97Node.setAxisOfRotation(axis.x, axis.y, axis.z);
		}
		else
			vrml97Node.setAxisOfRotation(0.0f, 0.0f, 0.0f);
				
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// LinearFog ---> FogNode 
	/////////////////////////////////////////////
	
	public FogNode createVRML97FogNode(LinearFog j3dNode) {
		
		setCapability(j3dNode, LinearFog.ALLOW_COLOR_READ);
		setCapability(j3dNode, LinearFog.ALLOW_DISTANCE_READ);
		 
		FogNode	vrml97Node	= new FogNode();

		vrml97Node.setFogType("LINEAR");
		
		Color3f color = new Color3f();
		j3dNode.getColor(color);
		vrml97Node.setColor(color.x, color.y, color.z);
				
		vrml97Node.setVisibilityRange((float)j3dNode.getBackDistance());
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// ExponentialFog ---> FogNode 
	/////////////////////////////////////////////
	
	public FogNode createVRML97FogNode(ExponentialFog j3dNode) {
		
		setCapability(j3dNode, ExponentialFog.ALLOW_COLOR_READ);
		setCapability(j3dNode, ExponentialFog.ALLOW_DENSITY_READ);
		 
		FogNode	vrml97Node	= new FogNode();

		vrml97Node.setFogType("EXPONENTIAL");
		
		Color3f color = new Color3f();
		j3dNode.getColor(color);
		vrml97Node.setColor(color.x, color.y, color.z);
				
		vrml97Node.setVisibilityRange(j3dNode.getDensity());
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// DirectionalLight ---> DirectionalLightNode 
	/////////////////////////////////////////////
	
	public DirectionalLightNode createVRML97DirectionalLightNode(DirectionalLight j3dNode) {
		
		setCapability(j3dNode, DirectionalLight.ALLOW_COLOR_READ);
		setCapability(j3dNode, DirectionalLight.ALLOW_STATE_READ);
		setCapability(j3dNode, DirectionalLight.ALLOW_DIRECTION_READ);
		 
		DirectionalLightNode vrml97Node = new DirectionalLightNode();

		vrml97Node.setOn(j3dNode.getEnable());

		Color3f color = new Color3f();
		j3dNode.getColor(color);
		vrml97Node.setColor(color.x, color.y, color.z);
		
		Vector3f dir = new Vector3f();
		j3dNode.getDirection(dir);
		vrml97Node.setDirection(dir.x, dir.y, dir.y);
		
		vrml97Node.setIntensity(1.0f);
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// PointLight ---> PointLightNode 
	/////////////////////////////////////////////
	
	public PointLightNode createVRML97PointLightNode(PointLight j3dNode) 
	{
		setCapability(j3dNode, PointLight.ALLOW_COLOR_READ);
		setCapability(j3dNode, PointLight.ALLOW_STATE_READ);
		setCapability(j3dNode, PointLight.ALLOW_ATTENUATION_READ);
		setCapability(j3dNode, PointLight.ALLOW_POSITION_READ);
		setCapability(j3dNode, PointLight.ALLOW_INFLUENCING_BOUNDS_READ);
		 
		PointLightNode vrml97Node = new PointLightNode();

		vrml97Node.setOn(j3dNode.getEnable());
		
		Color3f color = new Color3f();
		j3dNode.getColor(color);
		vrml97Node.setColor(color.x, color.y, color.z);
		
		Point3f pos = new Point3f();
		j3dNode.getPosition(pos);
		vrml97Node.setLocation(pos.x, pos.y, pos.y);

		Point3f att = new Point3f();
		j3dNode.getAttenuation(att);
		vrml97Node.setAttenuation(att.x, att.y, att.y);
		
		BoundingSphere boundSphere = new BoundingSphere(j3dNode.getInfluencingBounds());
		vrml97Node.setRadius((float)boundSphere.getRadius());
		
		vrml97Node.setIntensity(1.0f);
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// SpotLight ---> SpotLightNode 
	/////////////////////////////////////////////
	
	public SpotLightNode createVRML97SpotLightNode(SpotLight j3dNode) 
	{
		setCapability(j3dNode, SpotLight.ALLOW_COLOR_READ);
		setCapability(j3dNode, SpotLight.ALLOW_STATE_READ);
		setCapability(j3dNode, SpotLight.ALLOW_DIRECTION_READ);
		 
		SpotLightNode vrml97Node = new SpotLightNode();
		vrml97Node.setOn(j3dNode.getEnable());
		
		Color3f color = new Color3f();
		j3dNode.getColor(color);
		vrml97Node.setColor(color.x, color.y, color.z);
		
		Vector3f dir = new Vector3f();
		j3dNode.getDirection(dir);
		vrml97Node.setDirection(dir.x, dir.y, dir.y);
		
		Point3f pos = new Point3f();
		j3dNode.getPosition(pos);
		vrml97Node.setLocation(pos.x, pos.y, pos.y);

		Point3f att = new Point3f();
		j3dNode.getAttenuation(att);
		vrml97Node.setAttenuation(att.x, att.y, att.y);
		
		BoundingSphere boundSphere = new BoundingSphere(j3dNode.getInfluencingBounds());
		vrml97Node.setRadius((float)boundSphere.getRadius());
		
		vrml97Node.setIntensity(1.0f);
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// View ---> ViewpointNode 
	/////////////////////////////////////////////
	
	public ViewpointNode createVRML97ViewpointNode(View j3dNode) {
		
		ViewpointNode	vrml97Node	= new ViewpointNode();

		vrml97Node.setFieldOfView((float)j3dNode.getFieldOfView());
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// Switch ---> SwitchNode 
	/////////////////////////////////////////////
	
	public SwitchNode createVRML97SwitchNode(Switch j3dNode) 
	{
		setCapability(j3dNode, Switch.ALLOW_SWITCH_READ);
		 
		SwitchNode	vrml97Node	= new SwitchNode();

		vrml97Node.setWhichChoice(j3dNode.getWhichChild());
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// TransformGroup ---> TransformNode 
	/////////////////////////////////////////////
	
	public TransformNode createVRML97TransformNode(TransformGroup j3dNode) 
	{
		setCapability(j3dNode, TransformGroup.ALLOW_TRANSFORM_READ);
		
		TransformNode	vrml97Node	= new TransformNode();
		Transform3D		trans3D			= new Transform3D();
		
		j3dNode.getTransform(trans3D);
		
		Vector3f trans = new Vector3f();
		trans3D.get(trans);
		vrml97Node.setTranslation(trans.x, trans.y, trans.z);

		Quat4f q = new Quat4f();
		trans3D.get(q);
		AxisAngle4f axisAngle = new AxisAngle4f();
		axisAngle.set(q);
		vrml97Node.setRotation(axisAngle.x, axisAngle.y, axisAngle.z, axisAngle.angle);
		
		Vector3d scale = new Vector3d();
		trans3D.getScale(scale);
		vrml97Node.setScale((float)scale.x, (float)scale.y, (float)scale.z);
		
		j3dNode.getTransform(trans3D);
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// AmbientLight ---> PointLightNode 
	/////////////////////////////////////////////
	
	public PointLightNode createVRML97PointLightNode(AmbientLight j3dNode) 
	{
		setCapability(j3dNode, AmbientLight.ALLOW_COLOR_READ);
		
		PointLightNode vrml97Node	= new PointLightNode();
		
		Color3f color = new Color3f();
		j3dNode.getColor(color);
		
		vrml97Node.setColor(color.x, color.y, color.z);
		vrml97Node.setAmbientIntensity(1.0f);
		vrml97Node.setIntensity(0.0f);
		vrml97Node.setRadius(0.0f);
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// Shape3D ---> ShapeNode
	/////////////////////////////////////////////
	
	/** Fixes bug in original method <code>createVRML97ShapeNode()</code>,
	 * which should return <code>GroupNode</code> as it can contain
	 * several <code>Geometry</code>s.
	 * Thanks for <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
	public org.cybergarage.x3d.node.Node createVRML97ShapeNode(Shape3D j3dNode) 
	{
		int nGeom = j3dNode.numGeometries();
		if (1 < nGeom)
			return createVRML97ShapeGroupNode(j3dNode);
		
		setCapability(j3dNode, Shape3D.ALLOW_APPEARANCE_READ);
		setCapability(j3dNode, Shape3D.ALLOW_GEOMETRY_READ);
		
		ShapeNode vrml97Node = new ShapeNode();
		
		Appearance j3dApp = j3dNode.getAppearance();
		if (j3dApp != null)
			vrml97Node.addChildNode(createVRML97AppearanceNode(j3dApp)); 

		Geometry j3dGeom = j3dNode.getGeometry();
		if (j3dGeom != null) {
			Geometry3DNode geomNode = createVRML97Geometry3DNode(j3dGeom); 
			if (geomNode != null)
				vrml97Node.addChildNode(geomNode); 
		}
		
		return vrml97Node;
	}

	public GroupNode createVRML97ShapeGroupNode(Shape3D j3dNode) {
		setCapability(j3dNode, Shape3D.ALLOW_APPEARANCE_READ);
		setCapability(j3dNode, Shape3D.ALLOW_GEOMETRY_READ);	

		GroupNode vrml97Node = new GroupNode();		
		Appearance j3dApp = j3dNode.getAppearance();
		
		ShapeNode shapeNode;
		Geometry j3dGeom;
		Geometry3DNode geomNode;
		
		int nGeom = j3dNode.numGeometries();
		for (int i = 0; i < nGeom; i++) {
			shapeNode = new ShapeNode(); 
			vrml97Node.addChildNode(shapeNode);
			if (j3dApp != null)
				shapeNode.addChildNode(createVRML97AppearanceNode(j3dApp)); 
			j3dGeom = j3dNode.getGeometry(i);
			if (j3dGeom != null) {
				geomNode = createVRML97Geometry3DNode(j3dGeom);
				if (geomNode != null)
					shapeNode.addChildNode(geomNode); 
			}
		}
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// Appearance ---> AppearanceNode 
	/////////////////////////////////////////////
	
	/** Fixes bug in <code>createVRML97AppearanceNode</code>,
	 * which should handle transparency
	 * Thanks for <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
	public AppearanceNode createVRML97AppearanceNode(Appearance j3dNode) 
	{
		setCapability(j3dNode, Appearance.ALLOW_MATERIAL_READ);
		setCapability(j3dNode, Appearance.ALLOW_TEXTURE_READ);
		
		AppearanceNode vrml97Node	= new AppearanceNode();
		
		Material j3dMaterial = j3dNode.getMaterial();
		if (j3dMaterial != null)
			vrml97Node.addChildNode(createVRML97MaterialNode(j3dMaterial)); 

		TransparencyAttributes transparencyAtt = j3dNode.getTransparencyAttributes();
		if (j3dMaterial != null) {
			MaterialNode material = createVRML97MaterialNode(j3dMaterial);
			if (transparencyAtt != null)
				material.setTransparency(transparencyAtt.getTransparency());
			vrml97Node.addChildNode(material);
		} 

		Texture j3dTexture = j3dNode.getTexture();
		if (j3dTexture != null && j3dTexture instanceof Texture2D) {
			PixelTextureNode texNode = createVRML97PixelTextureNode((Texture2D)j3dTexture);
			if (texNode != null)
				vrml97Node.addChildNode(texNode); 
		}
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// Material ---> MaterialNode 
	/////////////////////////////////////////////
	
	public MaterialNode createVRML97MaterialNode(Material j3dNode) 
	{
		setCapability(j3dNode, Material.ALLOW_COMPONENT_READ);
		
		MaterialNode vrml97Node	= new MaterialNode();
		
		Color3f color = new Color3f();
		
		vrml97Node.setAmbientIntensity(0.2f);
		
		j3dNode.getDiffuseColor(color);
		vrml97Node.setDiffuseColor(color.x, color.y, color.z);
		
		j3dNode.getEmissiveColor(color);
		vrml97Node.setEmissiveColor(color.x, color.y, color.z);
		
		j3dNode.getSpecularColor(color);
		vrml97Node.setSpecularColor(color.x, color.y, color.z);
		
		vrml97Node.setShininess(j3dNode.getShininess());
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// Texture2D ---> PixelTexture 
	/////////////////////////////////////////////
	
	public PixelTextureNode createVRML97PixelTextureNode(Texture2D j3dNode) 
	{
		setCapability(j3dNode, Texture.ALLOW_IMAGE_READ);
		
		ImageComponent imgComp = j3dNode.getImage(0);

		if (imgComp == null)
			return null;

		if ((imgComp instanceof ImageComponent2D) == false)
			return null;

		ImageComponent2D imgComp2D = (ImageComponent2D)imgComp;
		
		setCapability(imgComp2D, ImageComponent.ALLOW_FORMAT_READ);
		setCapability(imgComp2D, ImageComponent.ALLOW_IMAGE_READ);
		setCapability(imgComp2D, ImageComponent.ALLOW_SIZE_READ);
		
		int compSize = 0;
		
		int format = imgComp2D.getFormat();
		if (format == ImageComponent.FORMAT_RGB)
			compSize = 3;				
		if (format == ImageComponent.FORMAT_RGBA)
			compSize = 4;				
			
		if (compSize == 0)
			return null;	
			
		BufferedImage bufImage = imgComp2D.getImage();
		
		if (bufImage == null)
			return null;
			
		int width	= bufImage.getWidth();
		int height	= bufImage.getHeight();
		
		PixelTextureNode vrml97Node	= new PixelTextureNode();
		
		vrml97Node.addImage(width);
		vrml97Node.addImage(height);
		vrml97Node.addImage(compSize);
		
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) 
				vrml97Node.addImage(bufImage.getRGB(x, ((height-1) - y)));
		}
		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// Geometry ---> Geometry3DNode 
	/////////////////////////////////////////////
	
	public Geometry3DNode createVRML97Geometry3DNode(Geometry j3dNode) {
		
		Geometry3DNode vrml97Node = null;
		
		if (j3dNode instanceof Text3D)
			vrml97Node = createVRML97TextNode((Text3D)j3dNode);
		if (j3dNode instanceof PointArray)
			vrml97Node = createVRML97PointSetNode((PointArray)j3dNode);
		if (j3dNode instanceof LineArray)
			vrml97Node = createVRML97LineSetNode((LineArray)j3dNode);
		if (j3dNode instanceof LineStripArray)
			vrml97Node = createVRML97LineSetNode((LineStripArray)j3dNode);
		if (j3dNode instanceof TriangleArray)
			vrml97Node = createVRML97IndexedFaceSetNode((TriangleArray)j3dNode);
		if (j3dNode instanceof TriangleStripArray)
			vrml97Node = createVRML97IndexedFaceSetNode((TriangleStripArray)j3dNode);
		if (j3dNode instanceof TriangleFanArray)
			vrml97Node = createVRML97IndexedFaceSetNode((TriangleFanArray)j3dNode);
		if (j3dNode instanceof QuadArray)
			vrml97Node = createVRML97IndexedFaceSetNode((QuadArray)j3dNode);
		if (j3dNode instanceof IndexedTriangleArray)
			vrml97Node = createVRML97IndexedFaceSetNode((IndexedTriangleArray)j3dNode);
		if (j3dNode instanceof IndexedQuadArray)
			vrml97Node = createVRML97IndexedFaceSetNode((IndexedQuadArray)j3dNode);
		
		if (vrml97Node == null)
			Debug.message("Couldn't convert the geometry node (" + j3dNode +")");
			
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// Text3D ---> TextNode 
	/////////////////////////////////////////////

	public TextNode createVRML97TextNode(Text3D j3dNode) 
	{
		setCapability(j3dNode, Text3D.ALLOW_STRING_READ);
		
		TextNode vrml97Node = new TextNode();
		
		String string = j3dNode.getString();
		if (string != null)
			vrml97Node.addString(string);		
 			
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// PointArray ---> PointSet 
	/////////////////////////////////////////////

	public PointSetNode createVRML97PointSetNode(PointArray j3dNode) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, GeometryArray.ALLOW_COORDINATE_READ);
		setCapability(j3dNode, GeometryArray.ALLOW_COLOR_READ);
		
		PointSetNode vrml97Node = new PointSetNode();

		int vertexCount = j3dNode.getVertexCount();
		
		if (vertexCount <= 0)
			return vrml97Node;
			
		if ((j3dNode.getVertexFormat() & GeometryArray.COORDINATES) != 0) {
			CoordinateNode coordNode = new CoordinateNode();
			float coord[] = new float[3];
			for (int n=0; n<vertexCount; n++) {
				j3dNode.getCoordinate(n, coord);
				coordNode.addPoint(coord);
			}
			vrml97Node.addChildNode(coordNode);
		}
		
		int vertexFormat = j3dNode.getVertexFormat(); 
		if ((vertexFormat & GeometryArray.COLOR_3) != 0 || (vertexFormat & GeometryArray.COLOR_4) != 0) {
			ColorNode colorNode = new ColorNode();
			float color[] = new float[4];
			for (int n=0; n<vertexCount; n++) {
				j3dNode.getColor(n, color);
				colorNode.addColor(color);
			}
			vrml97Node.addChildNode(colorNode);
		}
		 		
		return vrml97Node;
	}

	/////////////////////////////////////////////
	// LineArray ---> IndexedLineSetNode 
	/////////////////////////////////////////////

	public IndexedLineSetNode createVRML97LineSetNode(LineArray j3dNode) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, GeometryArray.ALLOW_COORDINATE_READ);
		setCapability(j3dNode, GeometryArray.ALLOW_COLOR_READ);
		
		IndexedLineSetNode vrml97Node = new IndexedLineSetNode();

		int vertexCount = j3dNode.getVertexCount();
		
		if (vertexCount <= 0)
			return vrml97Node;
			
		if ((j3dNode.getVertexFormat() & GeometryArray.COORDINATES) != 0) {
			CoordinateNode coordNode = new CoordinateNode();
			float coord[] = new float[3];
			for (int n=0; n<vertexCount; n++) {
				j3dNode.getCoordinate(n, coord);
				coordNode.addPoint(coord);
			}
			vrml97Node.addChildNode(coordNode);
			
			for (int n=0; n<vertexCount; n+=2) {
				vrml97Node.addCoordIndex(n);
				vrml97Node.addCoordIndex(n+1);
				vrml97Node.addCoordIndex(-1);
			}
		}
					
		int vertexFormat = j3dNode.getVertexFormat(); 
		if ((vertexFormat & GeometryArray.COLOR_3) != 0 || (vertexFormat & GeometryArray.COLOR_4) != 0) {
			ColorNode colorNode = new ColorNode();
			float color[] = new float[4];
			for (int n=0; n<vertexCount; n++) {
				j3dNode.getColor(n, color);
				colorNode.addColor(color);
			}
			vrml97Node.addChildNode(colorNode);
			vrml97Node.setColorPerVertex(true);
 		}

		return vrml97Node;
	}

	/////////////////////////////////////////////
	// LineStripArray ---> IndexedLineSetNode 
	/////////////////////////////////////////////

	public IndexedLineSetNode createVRML97LineSetNode(LineStripArray j3dNode) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, GeometryArray.ALLOW_COORDINATE_READ);
		setCapability(j3dNode, GeometryArray.ALLOW_COLOR_READ);
		
		IndexedLineSetNode vrml97Node = new IndexedLineSetNode();

		int vertexCount = j3dNode.getVertexCount();
		
		if (vertexCount <= 0)
			return vrml97Node;

		if ((j3dNode.getVertexFormat() & GeometryArray.COORDINATES) != 0) {
			CoordinateNode coordNode = new CoordinateNode();
			float coord[] = new float[3];
			for (int n=0; n<vertexCount; n++) {
				j3dNode.getCoordinate(n, coord);
				coordNode.addPoint(coord);
			}
			vrml97Node.addChildNode(coordNode);

			int numStrips = j3dNode.getNumStrips();
			int stripVertexCounts[] = new int[numStrips];
			j3dNode.getStripVertexCounts(stripVertexCounts);
			
			int offset = 0;
			for (int i=0; i<numStrips; i++) { 
				int stripVertexCount = stripVertexCounts[i];
				for (int n=0; n<(stripVertexCount-1); n++) {
					vrml97Node.addCoordIndex(n   + offset);
					vrml97Node.addCoordIndex(n+1 + offset);
					vrml97Node.addCoordIndex(-1);
				}
				offset += stripVertexCount;
			}
		}
					
		int vertexFormat = j3dNode.getVertexFormat(); 
		if ((vertexFormat & GeometryArray.COLOR_3) != 0 || (vertexFormat & GeometryArray.COLOR_4) != 0) {
			ColorNode colorNode = new ColorNode();
			float color[] = new float[4];
			for (int n=0; n<vertexCount; n++) {
				j3dNode.getColor(n, color);
				colorNode.addColor(color);
			}
			vrml97Node.addChildNode(colorNode);
			vrml97Node.setColorPerVertex(true);
 		}

		return vrml97Node;
	}

	/////////////////////////////////////////////
	// GeometryArray ---> IndexedLineFaceNode 
	/////////////////////////////////////////////

	public boolean addGeometryArrayCoordinateToIndexedFaceSetNode(IndexedFaceSetNode vrml97Node, GeometryArray j3dNode) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, GeometryArray.ALLOW_COORDINATE_READ);
		
		if ((j3dNode.getVertexFormat() & GeometryArray.COORDINATES) == 0) 
			return false;
		
		int vertexCount = j3dNode.getVertexCount();
		if (vertexCount <= 0)
			return false;
		
		CoordinateNode coordNode = new CoordinateNode();
		float coord[] = new float[3];
		for (int n=0; n<vertexCount; n++) {
			j3dNode.getCoordinate(n, coord);
			coordNode.addPoint(coord);
		}
		vrml97Node.addChildNode(coordNode);
		
		return true;
	}

	public boolean addGeometryArrayColorToIndexedFaceSetNode(IndexedFaceSetNode vrml97Node, GeometryArray j3dNode) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, GeometryArray.ALLOW_COLOR_READ);
		
		int vertexFormat = j3dNode.getVertexFormat(); 		
		if ((vertexFormat & GeometryArray.COLOR_3) == 0 && (vertexFormat & GeometryArray.COLOR_4) == 0) 
			return false;
		
		int vertexCount = j3dNode.getVertexCount();
		if (vertexCount <= 0)
			return false;
		
		ColorNode colorNode = new ColorNode();
		float color[] = new float[4];
		for (int n=0; n<vertexCount; n++) {
			j3dNode.getColor(n, color);
			colorNode.addColor(color);
		}
		vrml97Node.addChildNode(colorNode);
		
		return true;
	}

	public boolean addGeometryArrayNormalToIndexedFaceSetNode(IndexedFaceSetNode vrml97Node, GeometryArray j3dNode) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, GeometryArray.ALLOW_NORMAL_READ);
		
		if ((j3dNode.getVertexFormat() & GeometryArray.NORMALS) == 0) 
			return false;
		
		int vertexCount = j3dNode.getVertexCount();
		if (vertexCount <= 0)
			return false;

		NormalNode normalNode = new NormalNode();		
		float normal[] = new float[3];
		for (int n=0; n<vertexCount; n++) {
			j3dNode.getNormal(n, normal);
			normalNode.addVector(normal);
		}
		vrml97Node.addChildNode(normalNode);
		
		return true;
	}

	public boolean addGeometryArrayTextureCoordinateToIndexedFaceSetNode(IndexedFaceSetNode vrml97Node, GeometryArray j3dNode) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, GeometryArray.ALLOW_TEXCOORD_READ);
		
		if ((j3dNode.getVertexFormat() & GeometryArray.TEXTURE_COORDINATE_2) == 0) 
			return false;
		
		int vertexCount = j3dNode.getVertexCount();
		if (vertexCount <= 0)
			return false;
			
		TextureCoordinateNode texCoordNode = new TextureCoordinateNode();
		float texCoord[] = new float[2];
		for (int n=0; n<vertexCount; n++) {
			j3dNode.getTextureCoordinate(n, texCoord);
			texCoordNode.addPoint(texCoord);
		}
		vrml97Node.addChildNode(texCoordNode);
		
		return true;
	}

	/////////////////////////////////////////////
	// IndexedGeometryArray ---> IndexedLineFaceNode 
	/////////////////////////////////////////////

	public static int	IDXGEOMTYPE_TRIANGLE	= 3;
	public static int	IDXGEOMTYPE_QUAD		= 4;
	
	public boolean addGeometryArrayCoordinateIndexToIndexedFaceSetNode(IndexedFaceSetNode vrml97Node, IndexedGeometryArray j3dNode, int type) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, IndexedGeometryArray.ALLOW_COORDINATE_INDEX_READ);

		if ((j3dNode.getVertexFormat() & GeometryArray.COORDINATES) == 0) 
			return false;
			
		int idxCount = j3dNode.getIndexCount();
		if (idxCount <= 0)
			return false;
			
		for (int i=0; i<idxCount; i+=type) {
			for (int j=0; j<type; j++) {
				int index = j3dNode.getCoordinateIndex(i + j);
				vrml97Node.addCoordIndex(index);
			}
			vrml97Node.addCoordIndex(-1);
		}
		
		return true;
	}

	public boolean addGeometryArrayColorIndexToIndexedFaceSetNode(IndexedFaceSetNode vrml97Node, IndexedGeometryArray j3dNode, int type) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, IndexedGeometryArray.ALLOW_COLOR_INDEX_READ);

		int vertexFormat = j3dNode.getVertexFormat(); 
		if ((vertexFormat & GeometryArray.COLOR_3) == 0 && (vertexFormat & GeometryArray.COLOR_4) == 0) 
			return false;
			
		int idxCount = j3dNode.getIndexCount();
		if (idxCount <= 0)
			return false;
		
		for (int i=0; i<idxCount; i+=type) {
			for (int j=0; j<type; j++) {
				int index = j3dNode.getColorIndex(i + j );
				vrml97Node.addColorIndex(index);
			}
			vrml97Node.addColorIndex(-1);
		}
		
		return true;
	}

	public boolean addGeometryArrayNormalIndexToIndexedFaceSetNode(IndexedFaceSetNode vrml97Node, IndexedGeometryArray j3dNode, int type) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, IndexedGeometryArray.ALLOW_NORMAL_INDEX_READ);
		
		if ((j3dNode.getVertexFormat() & GeometryArray.NORMALS) == 0) 
			return false;
			
		int idxCount = j3dNode.getIndexCount();
		if (idxCount <= 0)
			return false;
		
		for (int i=0; i<idxCount; i+=type) {
			for (int j=0; j<type; j++) {
				int index = j3dNode.getNormalIndex(i + j);
				vrml97Node.addNormalIndex(index);
			}
			vrml97Node.addNormalIndex(-1);
		}
		
		return true;
	}

	public boolean addGeometryArrayTextureCoordinateIndexToIndexedFaceSetNode(IndexedFaceSetNode vrml97Node, IndexedGeometryArray j3dNode, int type) 
	{
		setCapability(j3dNode, GeometryArray.ALLOW_FORMAT_READ);
		setCapability(j3dNode, IndexedGeometryArray.ALLOW_TEXCOORD_INDEX_READ);
		
		if ((j3dNode.getVertexFormat() & GeometryArray.TEXTURE_COORDINATE_2) == 0) 
			return false;
		
		int idxCount = j3dNode.getIndexCount();
		if (idxCount <= 0)
			return false;
		
		for (int i=0; i<idxCount; i+=type) {
			for (int j=0; j<type; j++) {
				int index = j3dNode.getTextureCoordinateIndex(i + j);
				vrml97Node.addTexCoordIndex(index);
			}
			vrml97Node.addTexCoordIndex(-1);
		}
		
		return true;
	}
	
	/////////////////////////////////////////////
	// TriangleArray ---> IndexedLineFaceNode 
	/////////////////////////////////////////////

	public IndexedFaceSetNode createVRML97IndexedFaceSetNode(TriangleArray j3dNode) {

		IndexedFaceSetNode vrml97Node = new IndexedFaceSetNode();

		if (addGeometryArrayCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode) == true) {
			int vertexCount = j3dNode.getVertexCount(); 
			for (int n=0; n<vertexCount; n+=3) {
				vrml97Node.addCoordIndex(n);
				vrml97Node.addCoordIndex(n+1);
				vrml97Node.addCoordIndex(n+2);
				vrml97Node.addCoordIndex(-1);
			}
		}

		if (addGeometryArrayColorToIndexedFaceSetNode(vrml97Node, j3dNode) == true) 
			vrml97Node.setColorPerVertex(true);
			
		if (addGeometryArrayNormalToIndexedFaceSetNode(vrml97Node, j3dNode) == true) 
			vrml97Node.setNormalPerVertex(true);

		addGeometryArrayTextureCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode); 

		return vrml97Node;
	}

	/////////////////////////////////////////////
	// TriangleStripArray  ---> IndexedLineFaceNode 
	/////////////////////////////////////////////

	public IndexedFaceSetNode createVRML97IndexedFaceSetNode(TriangleStripArray j3dNode) {

		IndexedFaceSetNode vrml97Node = new IndexedFaceSetNode();

		Debug.message("createVRML97IndexedFaceSetNode");
		
		if (addGeometryArrayCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode) == true) {
			int numStrips = j3dNode.getNumStrips();
			int stripVertexCounts[] = new int[numStrips];
			j3dNode.getStripVertexCounts(stripVertexCounts);
			
			int offset = 0;
			for (int i=0; i<numStrips; i++) { 
				int vertexCount = stripVertexCounts[i];
				for (int n=0; n<(vertexCount-2); n++) {
					if ((n % 2) == 0) {
						vrml97Node.addCoordIndex(n   + offset);
						vrml97Node.addCoordIndex(n+1 + offset);
						vrml97Node.addCoordIndex(n+2 + offset);
						vrml97Node.addCoordIndex(-1);

					}
					else {
						vrml97Node.addCoordIndex(n   + offset);
						vrml97Node.addCoordIndex(n+2 + offset);
						vrml97Node.addCoordIndex(n+1 + offset);
						vrml97Node.addCoordIndex(-1);
					}
				}
				offset += vertexCount;
			}
		}

		if (addGeometryArrayColorToIndexedFaceSetNode(vrml97Node, j3dNode) == true) 
			vrml97Node.setColorPerVertex(true);
			
		if (addGeometryArrayNormalToIndexedFaceSetNode(vrml97Node, j3dNode) == true) 
			vrml97Node.setNormalPerVertex(true);

		addGeometryArrayTextureCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode); 

		return vrml97Node;
	}

	/////////////////////////////////////////////
	// TriangleFanArray ---> IndexedLineFaceNode 
	/////////////////////////////////////////////

	public IndexedFaceSetNode createVRML97IndexedFaceSetNode(TriangleFanArray j3dNode) {

		IndexedFaceSetNode vrml97Node = new IndexedFaceSetNode();

		if (addGeometryArrayCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode) == true) {
			int numStrips = j3dNode.getNumStrips();
			int stripVertexCounts[] = new int[numStrips];
			j3dNode.getStripVertexCounts(stripVertexCounts);
			
			int offset = 0;
			for (int i=0; i<numStrips; i++) { 
				int vertexCount = stripVertexCounts[i];
				for (int n=1; n<(vertexCount-2); n++) {
					vrml97Node.addCoordIndex(0);
					vrml97Node.addCoordIndex(n+1 + offset);
					vrml97Node.addCoordIndex(n   + offset);
					vrml97Node.addCoordIndex(-1);
				}
				offset += vertexCount;
			}
		}	

		if (addGeometryArrayColorToIndexedFaceSetNode(vrml97Node, j3dNode) == true) 
			vrml97Node.setColorPerVertex(true);
			
		if (addGeometryArrayNormalToIndexedFaceSetNode(vrml97Node, j3dNode) == true) 
			vrml97Node.setNormalPerVertex(true);

		addGeometryArrayTextureCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode); 

		return vrml97Node;
	}

	/////////////////////////////////////////////
	// QuadArray ---> IndexedLineFaceNode 
	/////////////////////////////////////////////

	public IndexedFaceSetNode createVRML97IndexedFaceSetNode(QuadArray j3dNode) {
		
		IndexedFaceSetNode vrml97Node = new IndexedFaceSetNode();

		if (addGeometryArrayCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode) == true) {
			int vertexCnt = j3dNode.getVertexCount();
			for (int n=0; n<vertexCnt; n+=4) {
				vrml97Node.addCoordIndex(n);
				vrml97Node.addCoordIndex(n+1);
				vrml97Node.addCoordIndex(n+2);
				vrml97Node.addCoordIndex(n+3);
				vrml97Node.addCoordIndex(-1);
			}
		}
							
		if (addGeometryArrayColorToIndexedFaceSetNode(vrml97Node, j3dNode) == true) 
			vrml97Node.setColorPerVertex(true);
			
		if (addGeometryArrayNormalToIndexedFaceSetNode(vrml97Node, j3dNode) == true) 
			vrml97Node.setNormalPerVertex(true);

		addGeometryArrayTextureCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode); 

		return vrml97Node;
	}

	/////////////////////////////////////////////
	// IndexedTriangleArray ---> IndexedLineFaceNode 
	/////////////////////////////////////////////

	public IndexedFaceSetNode createVRML97IndexedFaceSetNode(IndexedTriangleArray j3dNode) {

		IndexedFaceSetNode vrml97Node = new IndexedFaceSetNode();

		if (addGeometryArrayCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode) == true) 
			addGeometryArrayCoordinateIndexToIndexedFaceSetNode(vrml97Node, j3dNode, IDXGEOMTYPE_TRIANGLE); 
		

		if (addGeometryArrayColorToIndexedFaceSetNode(vrml97Node, j3dNode) == true) {
			if (addGeometryArrayColorIndexToIndexedFaceSetNode(vrml97Node, j3dNode, IDXGEOMTYPE_TRIANGLE) == true) 
				vrml97Node.setColorPerVertex(true);
		}
			
		if (addGeometryArrayNormalToIndexedFaceSetNode(vrml97Node, j3dNode) == true) {
			if (addGeometryArrayNormalIndexToIndexedFaceSetNode(vrml97Node, j3dNode, IDXGEOMTYPE_TRIANGLE) == true) 
				vrml97Node.setNormalPerVertex(true);
		}

		addGeometryArrayTextureCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode); 
		addGeometryArrayTextureCoordinateIndexToIndexedFaceSetNode(vrml97Node, j3dNode, IDXGEOMTYPE_TRIANGLE); 

		return vrml97Node;
	}

	/////////////////////////////////////////////
	// IndexedQuadArray ---> IndexedLineFaceNode 
	/////////////////////////////////////////////

	public IndexedFaceSetNode createVRML97IndexedFaceSetNode(IndexedQuadArray j3dNode) {
		
		IndexedFaceSetNode vrml97Node = new IndexedFaceSetNode();

		if (addGeometryArrayCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode) == true) 
			addGeometryArrayCoordinateIndexToIndexedFaceSetNode(vrml97Node, j3dNode, IDXGEOMTYPE_QUAD); 
		

		if (addGeometryArrayColorToIndexedFaceSetNode(vrml97Node, j3dNode) == true) {
			if (addGeometryArrayColorIndexToIndexedFaceSetNode(vrml97Node, j3dNode, IDXGEOMTYPE_QUAD) == true) 
				vrml97Node.setColorPerVertex(true);
		}
			
		if (addGeometryArrayNormalToIndexedFaceSetNode(vrml97Node, j3dNode) == true) {
			if (addGeometryArrayNormalIndexToIndexedFaceSetNode(vrml97Node, j3dNode, IDXGEOMTYPE_QUAD) == true) 
				vrml97Node.setNormalPerVertex(true);
		}

		addGeometryArrayTextureCoordinateToIndexedFaceSetNode(vrml97Node, j3dNode); 
		addGeometryArrayTextureCoordinateIndexToIndexedFaceSetNode(vrml97Node, j3dNode, IDXGEOMTYPE_QUAD); 

		return vrml97Node;
	}
}
	