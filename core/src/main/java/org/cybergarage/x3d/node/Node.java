/************************************f******************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: Node.java
*
*	03/17/04
*	- Thanks for Simon Goodall <sg02r@ecs.soton.ac.uk>
*	- Changed getChildNode(NodeType) to check the reference node type if the child node is the instance node.
*	01/21/08
*	- Added Node::getTransformMatrix(float[]).
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.*;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.route.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.field.SFMatrix;

public abstract class Node extends BaseNode implements Runnable, NodeConstatns { 

	public static final int		RUNNABLE_TYPE_NONE					= 0;
	public static final int		RUNNABLE_TYPE_ALWAYS					= 1;
	public static final int		RUNNABLE_DEFAULT_INTERVAL_TIME	= 100;

	////////////////////////////////////////////////
	//	Member
	////////////////////////////////////////////////

	private FieldList	mFieldList;
	private FieldList	mPrivateFieldList;
	private FieldList	mExposedFieldList;
	private FieldList	mEventInFieldList;
	private FieldList	mEventOutFieldList;
	
	private boolean		mInitializationFlag		= false;
	private NodeObject	mObject						= null;
	private Thread		mThreadObject				= null;

	private boolean		mRunnable					= false;			
	private int				mRunnableType				= RUNNABLE_TYPE_NONE;			
	private int				mRunnableIntervalTime	= RUNNABLE_DEFAULT_INTERVAL_TIME;
				
	private Node			mParentNode					= null;
	private LinkedList	mChildNodes					= new LinkedList();
	private SceneGraph	mSceneGraph					= null;
	private Object		mUserData					= null;
	private Node			mReferenceNode			= null;
	
	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////

	abstract public boolean	isChildNodeType(Node node);
	abstract public void	update();
	
	public void recreateNodeObject() {
		if (isRootNode() == false) {
			SceneGraph sg = getSceneGraph();
			if (sg == null)
				return;
			SceneGraphObject sgObject = sg.getObject();
			if (sgObject == null)
				return;
			NodeObject nodeObject = getObject();
			if (nodeObject != null)
				sgObject.removeNode(sg, this);
			nodeObject = sg.createNodeObject(this);
			setObject(nodeObject);
			if (nodeObject != null)
				sgObject.addNode(sg, this);
			Debug.message("Node::recreateNodeObject = " + this + ", " + nodeObject);
		}
	}
	
	public void initialize() {
		//recreateNodeObject();
	}
	
	abstract public void	uninitialize();

	////////////////////////////////////////////////
	//	Constractor
	////////////////////////////////////////////////

	public Node() {
		setHeaderFlag(true);
		setParentNode(null);
		setSceneGraph(null);
		setReferenceNode(null);
		setObject(null);
		setInitializationFlag(false);
		setThreadObject(null);
		setRunnable(false);

		createFiledLists();
	}
	
	public void createFiledLists()
	{
		mFieldList				= new FieldList(this);
		mPrivateFieldList		= new FieldList(this);
		mExposedFieldList		= new FieldList(this);
		mEventInFieldList		= new FieldList(this);
		mEventOutFieldList	= new FieldList(this);
	}
	
	public Node(NodeType nodeType, String nodeName) {
		this();
		setType(nodeType);
		setName(nodeName);
	}

	public Node(Node node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	List management
	////////////////////////////////////////////////

	public void removeChildNodes() {
		Node node=getChildNodes();
		while (node != null) {
			Node nextNode = node.next();
			node.remove();
			node = nextNode;
		}
	}

	public void removeRoutes() {
		SceneGraph sg = getSceneGraph();
		if (sg != null) {
			Route route=sg.getRoutes();
			while (route != null) {
				Route nextRoute = route.next();
				if (route.getEventInNode() == this || route.getEventOutNode() == this)
					route.remove();
				route = nextRoute;
			}
		}
	}

	public void removeSFNodes() {
		SceneGraph sg = getSceneGraph();
		if (sg != null) {
			for (ScriptNode script = sg.findScriptNode(); script != null; script=(ScriptNode)script.nextTraversalSameType()) {
				for (int n=0; n<script.getNFields(); n++) {
					Field field = script.getField(n);
					if (field.getType().equals(FieldType.SFNODE) == true) {
						SFNode sfnode = (SFNode)field;
						if (sfnode.getValue() == this)
							sfnode.setValue((Node)null);
					}
				}
			}
		}
	}

	public void removeInstanceNodes() {
		SceneGraph sg = getSceneGraph();
		if (sg != null && isInstanceNode() == false) {
			Node node = sg.getNodes();
			while (node != null) {
				Node nextNode = node.nextTraversal();
				if (node.isInstanceNode() == true) {
					Node refNode = node.getReferenceNode();
					while (refNode.isInstanceNode() == true)
						refNode = refNode.getReferenceNode();
					if (refNode == this) {
						node.removeChildNodes();
						nextNode = node.nextTraversal();
						node.remove();
					}
				}
				node = nextNode;
			}
		}
	}

	public void remove(boolean postShareEvent) 
	{ 
		SceneGraph sg = getSceneGraph();
		
		if (sg != null) 
			sg.removeNodeObject(this);
		
		super.remove();

		if (isInstanceNode() == false) {
			removeRoutes();
			removeSFNodes();
			removeInstanceNodes();

			if (isBindableNode() == true) {
				if (sg != null)
					sg.setBindableNode((BindableNode)this, false);			
			}
		}
		
		if (postShareEvent == true) {
			if (sg != null) 
				sg.postShareNodeRemoveEvent(this);
		}
				
		setParentNode(null);
		setSceneGraph(null);
	}

	public void remove() 
	{ 
		remove(true);
	}

	////////////////////////////////////////////////
	//	findField
	////////////////////////////////////////////////

	public Field findField(String name)
	{
		Field field = null;
		
		try {
			field = getField(name);
		} catch (InvalidFieldException e){};
		if (field != null)
			return field;
		
		try {
			field = getExposedField(name);
		} catch (InvalidExposedFieldException e){};
		if (field != null)
			return field;

		try {
			field = getEventIn(name);
		} catch (InvalidEventInException e){};
		if (field != null)
			return field;

		try {
			field = getEventOut(name);
		} catch (InvalidEventOutException e){};
		if (field != null)
			return field;
			
		try {
			field = getPrivateField(name);
		} catch (InvalidPrivateFieldException e){};
		if (field != null)
			return field;
		
		return null;
	}

	public boolean hasMField()
	{
		int fieldSize = getNFields();
		int exposedfieldSize = getNExposedFields();
		int eventInSize = getNEventIn();
		int eventOutSize = getNEventOut();
		int privateFieldSize = getNPrivateFields();

		for (int n=0; n<fieldSize; n++) {
			Field field = getField(n);
			if (field.isMField() == true)
				return true;
		}

		for (int n=0; n<exposedfieldSize; n++) {
			Field field = getExposedField(n);
			if (field.isMField() == true)
				return true;
		}

		for (int n=0; n<eventInSize; n++) {
			Field field = getEventIn(n);
			if (field.isMField() == true)
				return true;
		}

		for (int n=0; n<eventOutSize; n++) {
			Field field = getEventOut(n);
			if (field.isMField() == true)
				return true;
		}

		for (int n=0; n<privateFieldSize; n++) {
			Field field = getPrivateField(n);
			if (field.isMField() == true)
				return true;
		}

		return false;
	}
	
	////////////////////////////////////////////////
	//	Field
	////////////////////////////////////////////////

	// Get an exposed field by name. 
	//   Throws an InvalidExposedFieldException if fieldName isn't a valid
	//   exposed field name for a node of this type.

	public final Field getField(String fieldName) {
		if (fieldName == null)
			throw new InvalidFieldException(fieldName + " is not found.");
		for (int n=0; n<getNFields(); n++) {
			Field field = getField(n);
			if (fieldName.compareTo(field.getName()) == 0)
				return field;
		}
		throw new InvalidFieldException(fieldName + " is not found.");
//		return null;
	}

	public final int getNFields() {
		return mFieldList.size();
	}

	public final void addField(Field field) {
		mFieldList.addField(field);
	}

	public final void addField(String name, Field field) {
		field.setName(name);
		addField(field);
	}

	public final Field getField(int index) {
		return (Field)mFieldList.elementAt(index);
	}

	public final boolean removeField(Field removeField) {
		for (int n=0; n<getNFields(); n++) {
			Field field = getField(n);
			if (field == removeField) {
				mFieldList.removeField(field);
				return true;
			}
		}
		return false;
	}

	public final boolean removeField(String fieldName) {
		return removeField(getField(fieldName));
	}

	public int getFieldNumber(Field field) {
		for (int n=0; n<getNFields(); n++) {
			if (getField(n) == field)
				return n;
		}
		return -1;
	}
		
	////////////////////////////////////////////////
	//	Private Field
	////////////////////////////////////////////////

	public final Field getPrivateField(String fieldName) {
		if (fieldName == null)
			throw new InvalidPrivateFieldException(fieldName + " is not found.");
		for (int n=0; n<getNPrivateFields(); n++) {
			Field field = getPrivateField(n);
			if (fieldName.compareTo(field.getName()) == 0)
				return field;
		}
		throw new InvalidPrivateFieldException(fieldName + " is not found.");
//		return null;
	}

	public final int getNPrivateFields() {
		return mPrivateFieldList.size();
	}

	public final void addPrivateField(Field field) {
		mPrivateFieldList.addField(field);
	}

	public final void addPrivateField(String name, Field field) {
		field.setName(name);
		addPrivateField(field);
	}

	public final Field getPrivateField(int index) {
		return (Field)mPrivateFieldList.elementAt(index);
	}

	public final boolean removePrivateField(Field removeField) {
		for (int n=0; n<getNPrivateFields(); n++) {
			Field field = getPrivateField(n);
			if (field == removeField) {
				mPrivateFieldList.removeField(field);
				return true;
			}
		}
		return false;
	}

	public final boolean removePrivateField(String fieldName) {
		return removePrivateField(getPrivateField(fieldName));
	}

	public int getPrivateFieldNumber(Field field) {
		for (int n=0; n<getNPrivateFields(); n++) {
			if (getPrivateField(n) == field)
				return n;
		}
		return -1;
	}

	////////////////////////////////////////////////
	//	ExposedField
	////////////////////////////////////////////////

	// Get an exposed field by name. 
	//   Throws an InvalidExposedFieldException if fieldName isn't a valid
	//   exposed field name for a node of this type.

	public final Field getExposedField(String fieldName) {
		if (fieldName == null)
			throw new InvalidExposedFieldException(fieldName + " is not found.");
			
		for (int n=0; n<getNExposedFields(); n++) {
			Field field = getExposedField(n);
			if (fieldName.compareTo(field.getName()) == 0)
				return field;
			if (fieldName.startsWith(eventInStripString)) {
				if (fieldName.endsWith(field.getName()))
					return field;
			}
			if (fieldName.endsWith(eventOutStripString)) {
				if (fieldName.startsWith(field.getName()))
					return field;
			}
		}
		throw new InvalidExposedFieldException(fieldName + " is not found.");
//		return null;
	}

	public final int getNExposedFields() {
		return mExposedFieldList.size();
	}

	public final void addExposedField(Field field) {
		mExposedFieldList.addField(field);
	}

	public final void addExposedField(String name, Field field) {
		field.setName(name);
		addExposedField(field);
	}

	public final Field getExposedField(int index) {
		return (Field)mExposedFieldList.elementAt(index);
	}

	public final boolean removeExposedField(Field removeField) {
		for (int n=0; n<getNExposedFields(); n++) {
			Field field = getExposedField(n);
			if (field == removeField) {
				mExposedFieldList.removeField(field);
				return true;
			}
		}
		return false;
	}

	public final boolean removeExposedField(String fieldName) {
		return removeExposedField(getExposedField(fieldName));
	}

	public int getExposedFieldNumber(Field field) {
		for (int n=0; n<getNExposedFields(); n++) {
			if (getExposedField(n) == field)
				return n;
		}
		return -1;
	}

	////////////////////////////////////////////////
	//	EventIn
	////////////////////////////////////////////////

	// Get an EventIn by name. Return value is write-only.
	//   Throws an InvalidEventInException if eventInName isn't a valid
	//   event in name for a node of this type.

	public final Field getEventIn(String fieldName) {
		if (fieldName == null)
			throw new InvalidEventInException(fieldName + " is not found.");

		for (int n=0; n<getNEventIn(); n++) {
			Field field = getEventIn(n);
			if (fieldName.compareTo(field.getName()) == 0)
				return field;
			if (fieldName.startsWith(eventInStripString)) {
				if (fieldName.endsWith(field.getName()))
					return field;
			}
		}
		throw new InvalidEventInException(fieldName + " is not found.");
		//return null;
	}

	public final int getNEventIn() {
		return mEventInFieldList.size();
	}

	public final void addEventIn(Field field) {
		mEventInFieldList.addField(field);
	}

	public final void addEventIn(String name, Field field) {
		field.setName(name);
		addEventIn(field);
	}

	public final Field getEventIn(int index) {
		return (Field)mEventInFieldList.elementAt(index);
	}

	public final boolean removeEventIn(Field removeField) {
		for (int n=0; n<getNEventIn(); n++) {
			Field field = getEventIn(n);
			if (field == removeField) {
				mEventInFieldList.removeField(field);
				return true;
			}
		}
		return false;
	}

	public final boolean removeEventIn(String fieldName) {
		return removeEventIn(getEventIn(fieldName));
	}

	public int getEventInNumber(Field field) {
		for (int n=0; n<getNEventIn(); n++) {
			if (getEventIn(n) == field)
				return n;
		}
		return -1;
	}

	////////////////////////////////////////////////
	//	EventOut
	////////////////////////////////////////////////

	// Get an EventOut by name. Return value is read-only.
	//   Throws an InvalidEventOutException if eventOutName isn't a valid
	//   event out name for a node of this type.

	public final Field getEventOut(String fieldName) {
		if (fieldName == null)
			throw new InvalidEventOutException(fieldName + " is not found.");
		for (int n=0; n<getNEventOut(); n++) {
			Field field = getEventOut(n);
			if (fieldName.compareTo(field.getName()) == 0)
				return field;
			if (fieldName.endsWith(eventOutStripString)) {
				if (fieldName.startsWith(field.getName()))
					return field;
			}
		}
		throw new InvalidEventOutException(fieldName + " is not found.");
//		return null;
	}

	public final int getNEventOut() {
		return mEventOutFieldList.size();
	}

	public final void addEventOut(Field field) {
		mEventOutFieldList.addField(field);
	}

	public final void addEventOut(String name, Field field) {
		field.setName(name);
		addEventOut(field);
	}

	public final Field getEventOut(int index) {
		return (Field)mEventOutFieldList.elementAt(index);
	}

	public final boolean removeEventOut(Field removeField) {
		for (int n=0; n<getNEventOut(); n++) {
			Field field = getEventOut(n);
			if (field == removeField) {
				mEventOutFieldList.removeField(field);
				return true;
			}
		}
		return false;
	}

	public final boolean removeEventOut(String fieldName) {
		return removeEventOut(getEventOut(fieldName));
	}

	public int getEventOutNumber(Field field) {
		for (int n=0; n<getNEventOut(); n++) {
			if (getEventOut(n) == field)
				return n;
		}
		return -1;
	}

	////////////////////////////////////////////////
	//	Parent node
	////////////////////////////////////////////////

	public void setParentNode(Node parentNode) {
		mParentNode = parentNode;
	}

	public Node getParentNode() {
		return mParentNode;
	}
	
	public boolean isParentNode(Node node) {
		return (getParentNode() == node) ? true : false;
	}

	public boolean isAncestorNode(Node node) {
		for (Node parentNode = getParentNode(); parentNode != null; parentNode = parentNode.getParentNode()) {
			if (node == parentNode)
					return true;
		}
		return false;
	}

	////////////////////////////////////////////////
	//	find node list
	////////////////////////////////////////////////

	public Node nextTraversal() {
		Node nextNode = getChildNodes();
		if (nextNode != null)
			return nextNode;

		nextNode = next();
		if (nextNode == null) {
			Node parentNode = getParentNode();
			while (parentNode != null) { 
				Node parentNextNode = parentNode.next();
				if (parentNextNode != null)
					return parentNextNode;
				parentNode = parentNode.getParentNode();
			}
		}
		return nextNode;
	}

	public Node nextTraversalByType(NodeType type) {
		if (type == null)
			return null;

		for (Node node = nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.getType() != null) {
				if (type.equals(node.getType()) == true)
					return node;
			}
		}
		return null;
	}

	public Node nextTraversalByName(String name) {
		if (name == null)
			return null;

		for (Node node = nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.getName() != null) {
				if (name.compareTo(node.getName()) == 0)
					return node;
			}
		}
		return null;
	}

	public Node nextTraversalSameType() {
		return nextTraversalByType(getType());
	}

	////////////////////////////////////////////////
	//	next node list
	////////////////////////////////////////////////

	public Node next() {
		return (Node)getNextNode();
	}

	public Node next(NodeType type) {
		for (Node node = next(); node != null; node = node.next()) {
			if (type.equals(node.getType()) == true)
				return node;
		}
		return null;
	}

	public Node nextSameType() {
		return next(getType());
	}

	////////////////////////////////////////////////
	//	child node list
	////////////////////////////////////////////////

	public Node getChildNodes() {
		return (Node)mChildNodes.getNodes();
	}

	public boolean hasChildNodes() {
		if (getChildNodes() == null)
			return false;
		return true;
	}

	public Node getFirstChildNodes() {
		int numChild = getNChildNodes();
		if (0 < numChild)
			return getChildNode(0);
		return null;
	}

	public Node getLastChildNodes() {
		int numChild = getNChildNodes();
		if (0 < numChild)
			return getChildNode(numChild - 1);
		return null;
	}

	public Node getChildNodes(NodeType type) {
		// Thanks for Simon Goodall (03/17/04)
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			NodeType nodeType = node.getType();
			if (node.isInstanceNode() == true) 
				nodeType = node.getReferenceNode().getType();
			if (type.equals(nodeType) == true)
				return node;
		}
		return null;
	}

	public Node getChildNodes(NodeType type, String name) {
		// Thanks for Simon Goodall (03/17/04)
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			NodeType nodeType = node.getType();
			if (node.isInstanceNode() == true) 
				nodeType = node.getReferenceNode().getType();
			if (type.equals(nodeType) == false)
				continue;
			String nodeName = node.getName();
			if (name == null)
				continue;
			if (nodeName.equals(name) == true)
				return node;
		}
		return null;
	}

	public Node getChildNode(int n) {
		return (Node)mChildNodes.getNode(n);
	}

	public int getNChildNodes() {
		return mChildNodes.getNNodes();
	}

	////////////////////////////////////////////////
	//	Add children 
	////////////////////////////////////////////////
	
	public void addChildNode(Node node, boolean postShareEvent) 
	{
		moveChildNode(node, postShareEvent);
		node.initialize();
	}

	public void addChildNodeAtFirst(Node node, boolean postShareEvent) 
	{
		moveChildNodeAtFirst(node, postShareEvent);
		node.initialize();
	}

	public void addChildNode(Node node) 
	{
		addChildNode(node, true);
	}

	public void addChildNodeAtFirst(Node node) 
	{
		addChildNodeAtFirst(node, true);
	}

	////////////////////////////////////////////////
	//	Move children 
	////////////////////////////////////////////////

	public void moveChildNode(Node node, boolean addAtFirst, boolean postShareEvent) 
	{
		SceneGraph sg = getSceneGraph();
		if (addAtFirst == true)
			mChildNodes.addNodeAtFirst(node); 
		else	
			mChildNodes.addNode(node); 
		node.setParentNode(this);
		node.setSceneGraph(sg);
		if (sg != null) {
			sg.removeNodeObject(this);
			sg.addNodeObject(this);
			// Supprot for ShareWorld
			sg.postShareNodeAddEvent(node);
		}
	}

	public void moveChildNode(Node node, boolean postShareEvent) 
	{
		moveChildNode(node, false, postShareEvent);
	}

	public void moveChildNodeAtFirst(Node node, boolean postShareEvent) 
	{
		moveChildNode(node, true, postShareEvent);
	}

	public void moveChildNode(Node node) 
	{
		moveChildNode(node, true);
	}

	public void moveChildNodeAtFirst(Node node) 
	{
		moveChildNodeAtFirst(node, true);
	}

	////////////////////////////////////////////////
	//	Add / Remove children (for Groupingnode)
	////////////////////////////////////////////////

	public boolean isChild(Node parentNode, Node node) {
		for (Node cnode = parentNode.getChildNodes(); cnode != null; cnode = cnode.next()) {
			if (cnode == node)
				return true;
		}
		return false;
	}

	public boolean isChild(Node node) {
		if (getChildNodes() != null) {
			for (Node cnode = getChildNodes(); cnode != null; cnode = cnode.next()) {
				if (cnode == node)
					return true;
				if (isChild(cnode, node))
					return true;
			}
		}
		return false;
	}

	public void	addChildren(Node node[]) {
		for (int n=0; n<node.length; n++) {
			if (!isChild(node[n]))
				addChildNode(node[n]);
		}

	}

	public void	removeChildren(Node node[]) {
		for (int n=0; n<node.length; n++) {
			if (isChild(node[n]))
				node[n].remove();
		}

	}

	////////////////////////////////////////////////
	//	get child node list
	////////////////////////////////////////////////

	public GroupingNode getGroupingNodes() {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isGroupingNode())
				return (GroupingNode)node;
		}
		return null;
	}

	public Geometry3DNode getGeometry3DNode() {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isGeometry3DNode())
				return (Geometry3DNode)node;
		}
		return null;
	}

	public Geometry2DNode getGeometry2DNode() {
/*
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isGeometry2DNode())
				return (Geometry2DNode)node;
		}
*/
		return null;
	}

	public GeometryNode getGeometryNode()
	{
		GeometryNode geomNode = getGeometry3DNode();
		if (geomNode != null)
			return geomNode;
		return getGeometry2DNode();
	}
	

	public TextureNode getTextureNode() {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isTextureNode())
				return (TextureNode)node;
		}
		return null;
	}

	public AnchorNode getAnchorNodes() {
		return (AnchorNode)getChildNodes(NodeType.ANCHOR );
	}

	public AppearanceNode getAppearanceNodes() {
		return (AppearanceNode)getChildNodes(NodeType.APPEARANCE);
	}

	public AudioClipNode getAudioClipNodes() {
		return (AudioClipNode)getChildNodes(NodeType.AUDIOCLIP);
	}

	public BackgroundNode getBackgroundNodes() {
		return (BackgroundNode)getChildNodes(NodeType.BACKGROUND);
	}

	public BillboardNode getBillboardNodes() {
		return (BillboardNode)getChildNodes(NodeType.BILLBOARD);
	}

	public BoxNode getBoxNodes() {
		return (BoxNode)getChildNodes(NodeType.BOX);
	}

	public CollisionNode getCollisionNodes() {
		return (CollisionNode)getChildNodes(NodeType.COLLISION);
	}

	public ColorNode getColorNodes() {
		return (ColorNode)getChildNodes(NodeType.COLOR);
	}

	public ColorInterpolatorNode getColorInterpolatorNodes() {
		return (ColorInterpolatorNode)getChildNodes(NodeType.COLORINTERP);
	}

	public ConeNode getConeNodes() {
		return (ConeNode)getChildNodes(NodeType.CONE);
	}

	public CoordinateNode getCoordinateNodes() {
		return (CoordinateNode)getChildNodes(NodeType.COORD);
	}

	public CoordinateInterpolatorNode getCoordinateInterpolatorNodes() {
		return (CoordinateInterpolatorNode)getChildNodes(NodeType.COORDINTERP);
	}

	public CylinderNode getCylinderNodes() {
		return (CylinderNode)getChildNodes(NodeType.CYLINDER);
	}

	public CylinderSensorNode getCylinderSensorNodes() {
		return (CylinderSensorNode)getChildNodes(NodeType.CYLINDERSENSOR);
	}

	public DirectionalLightNode getDirectionalLightNodes() {
		return (DirectionalLightNode)getChildNodes(NodeType.DIRLIGHT);
	}

	public ElevationGridNode getElevationGridNodes() {
		return (ElevationGridNode)getChildNodes(NodeType.ELEVATIONGRID);
	}

	public ExtrusionNode getExtrusionNodes() {
		return (ExtrusionNode)getChildNodes(NodeType.EXTRUSION);
	}

	public FogNode getFogNodes() {
		return (FogNode)getChildNodes(NodeType.FOG);
	}

	public FontStyleNode getFontStyleNodes() {
		return (FontStyleNode)getChildNodes(NodeType.FONTSTYLE);
	}

	public GroupNode getGroupNodes() {
		return (GroupNode)getChildNodes(NodeType.GROUP);
	}

	public ImageTextureNode getImageTextureNodes() {
		return (ImageTextureNode)getChildNodes(NodeType.IMAGETEXTURE);
	}

	public IndexedFaceSetNode getIndexedFaceSetNodes() {
		return (IndexedFaceSetNode)getChildNodes(NodeType.INDEXEDFACESET);
	}

	public IndexedLineSetNode getIndexedLineSetNodes() {
		return (IndexedLineSetNode)getChildNodes(NodeType.INDEXEDLINESET);
	}

	public InlineNode getInlineNodes() {
		return (InlineNode)getChildNodes(NodeType.INLINE);
	}

	public LODNode getLODNodes() {
		return (LODNode)getChildNodes(NodeType.LOD);
	}

	public MaterialNode getMaterialNodes() {
		return (MaterialNode)getChildNodes(NodeType.MATERIAL);
	}

	public MovieTextureNode getMovieTextureNodes() {
		return (MovieTextureNode)getChildNodes(NodeType.MOVIETEXTURE);
	}

	public NavigationInfoNode getNavigationInfoNodes() {
		return (NavigationInfoNode)getChildNodes(NodeType.NAVIGATIONINFO);
	}

	public NormalNode getNormalNodes() {
		return (NormalNode)getChildNodes(NodeType.NORMAL);
	}

	public NormalInterpolatorNode getNormalInterpolatorNodes() {
		return (NormalInterpolatorNode)getChildNodes(NodeType.NORMALINTERP);
	}

	public OrientationInterpolatorNode getOrientationInterpolatorNodes() {
		return (OrientationInterpolatorNode)getChildNodes(NodeType.ORIENTATIONINTERP);
	}

	public PixelTextureNode getPixelTextureNodes() {
		return (PixelTextureNode)getChildNodes(NodeType.PIXELTEXTURE);
	}

	public PlaneSensorNode getPlaneSensorNodes() {
		return (PlaneSensorNode)getChildNodes(NodeType.PLANESENSOR);
	}

	public PointLightNode getPointLightNodes() {
		return (PointLightNode)getChildNodes(NodeType.POINTLIGHT);
	}

	public PointSetNode getPointSetNodes() {
		return (PointSetNode)getChildNodes(NodeType.POINTSET);
	}

	public PositionInterpolatorNode getPositionInterpolatorNodes() {
		return (PositionInterpolatorNode)getChildNodes(NodeType.POSITONINTERP);
	}

	public ProximitySensorNode getProximitySensorNodes() {
		return (ProximitySensorNode)getChildNodes(NodeType.PROXIMITYSENSOR);
	}

	public ProxyNode getProxyNodes() {
		return (ProxyNode)getChildNodes(NodeType.PROXY);
	}

	public ScalarInterpolatorNode getScalarInterpolatorNodes() {
		return (ScalarInterpolatorNode)getChildNodes(NodeType.SCALARINTERP);
	}

	public ScriptNode getScriptNodes() {
		return (ScriptNode)getChildNodes(NodeType.SCRIPT);
	}

	public ShapeNode getShapeNodes() {
		return (ShapeNode)getChildNodes(NodeType.SHAPE);
	}

	public SoundNode getSoundNodes() {
		return (SoundNode)getChildNodes(NodeType.SOUND);
	}

	public SphereNode getSphereNodes() {
		return (SphereNode)getChildNodes(NodeType.SPHERE);
	}

	public SphereSensorNode getSphereSensorNodes() {
		return (SphereSensorNode)getChildNodes(NodeType.SPHERESENSOR);
	}

	public SpotLightNode getSpotLightNodes() {
		return (SpotLightNode)getChildNodes(NodeType.SPOTLIGHT);
	}

	public SwitchNode getSwitchNodes() {
		return (SwitchNode)getChildNodes(NodeType.SWITCH);
	}

	public TextNode getTextNodes() {
		return (TextNode)getChildNodes(NodeType.TEXT);
	}

	public TextureCoordinateNode getTextureCoordinateNodes() {
		return (TextureCoordinateNode)getChildNodes(NodeType.TEXTURECOORD);
	}

	public TextureTransformNode getTextureTransformNodes() {
		return (TextureTransformNode)getChildNodes(NodeType.TEXTURETRANSFORM);
	}

	public TimeSensorNode getTimeSensorNodes() {
		return (TimeSensorNode)getChildNodes(NodeType.TIMESENSOR);
	}

	public TouchSensorNode getTouchSensorNodes() {
		return (TouchSensorNode)getChildNodes(NodeType.TOUCHSENSOR);
	}

	public TransformNode getTransformNodes() {
		return (TransformNode)getChildNodes(NodeType.TRANSFORM);
	}

	public ViewpointNode getViewpointNodes() {
		return (ViewpointNode)getChildNodes(NodeType.VIEWPOINT);
	}

	public VisibilitySensorNode getVisibilitySensorNodes() {
		return (VisibilitySensorNode)getChildNodes(NodeType.VISIBILITYSENSOR);
	}

	public WorldInfoNode getWorldInfoNodes() {
		return (WorldInfoNode)getChildNodes(NodeType.WORLDINFO);
	}

	// 9. Networking component (X3D)

	public LoadSensorNode getLoadSensorNodes() {
		return (LoadSensorNode)getChildNodes(NodeType.LOADSENSOR);
	}

	// 10. Grouping component (X3D)

	public StaticGroupNode getStaticGroupNodes() {
		return (StaticGroupNode)getChildNodes(NodeType.STATICGROUP);
	}

	// 11. Rendering component (X3D)

	public ColorRGBANode getColorRGBANodes() {
		return (ColorRGBANode)getChildNodes(NodeType.COLORRGBA);
	}

	public TriangleSetNode getTriangleSetNodes() {
		return (TriangleSetNode)getChildNodes(NodeType.TRIANGLESET);
	}

	public TriangleFanSetNode getTriangleFanSetNodes() {
		return (TriangleFanSetNode)getChildNodes(NodeType.TRIANGLEFANSET);
	}

	public TriangleStripSetNode getTriangleStripSetNodes() {
		return (TriangleStripSetNode)getChildNodes(NodeType.TRIANGLESTRIPSET);
	}
	
	// 12. Shape component (X3D)

	public FillPropertiesNode getFillPropertiesNodes() {
		return (FillPropertiesNode)getChildNodes(NodeType.FILLPROPERTIES);
	}

	public LinePropertiesNode getLinePropertiesNodes() {
		return (LinePropertiesNode)getChildNodes(NodeType.LINEPROPERTIES);
	}

	// 14. Geometry2D component (X3D)

	public Arc2DNode getArc2DNodes() {
		return (Arc2DNode)getChildNodes(NodeType.ARC2D);
	}

	public ArcClose2DNode getArcClose2DNodes() {
		return (ArcClose2DNode)getChildNodes(NodeType.ARCCLOSE2D);
	}

	public Circle2DNode getCircle2DNodes() {
		return (Circle2DNode)getChildNodes(NodeType.CIRCLE2D);
	}

	public Disk2DNode getDisk2DNodes() {
		return (Disk2DNode)getChildNodes(NodeType.DISK2D);
	}

	public Polyline2DNode getPolyline2DNodes() {
		return (Polyline2DNode)getChildNodes(NodeType.POLYLINE2D);
	}

	public Polypoint2DNode getPolypoint2DNodes() {
		return (Polypoint2DNode)getChildNodes(NodeType.POLYPOINT2D);
	}

	public Rectangle2DNode getRectangle2DNodes() {
		return (Rectangle2DNode)getChildNodes(NodeType.RECTANGLE2D);
	}

	public TriangleSet2DNode getTriangleSet2DNodes() {
		return (TriangleSet2DNode)getChildNodes(NodeType.TRIANGLESET2D);
	}
	
	// 18. Texturing component (x3D)

	public MultiTextureNode getMultiTextureNodes() {
		return (MultiTextureNode)getChildNodes(NodeType.MULTITEXTURE);
	}

	public MultiTextureCoordinateNode getMultiTextureCoordinateNodes() {
		return (MultiTextureCoordinateNode)getChildNodes(NodeType.MULTITEXTURECOORD);
	}

	public MultiTextureTransformNode getMultiTextureTransformNodes() {
		return (MultiTextureTransformNode)getChildNodes(NodeType.MULTITEXTURETRANSFORM);
	}
	
	public TextureCoordinateGeneratorNode getTextureCoordinateGeneratorNodes() {
		return (TextureCoordinateGeneratorNode)getChildNodes(NodeType.TEXCOORDGEN);
	}
	
	// 19. Interpolation component (X3D)

	public CoordinateInterpolator2DNode getCoordinateInterpolator2DNodes() {
		return (CoordinateInterpolator2DNode)getChildNodes(NodeType.COORDINATEINTERPOLATOR2D);
	}

	public PositionInterpolator2DNode getPositionInterpolator2DNodes() {
		return (PositionInterpolator2DNode)getChildNodes(NodeType.POSITIONINTERPOLATOR2D);
	}

	// 21. Key device sensor component (X3D)

	public KeySensorNode getKeySensorNodes() {
		return (KeySensorNode)getChildNodes(NodeType.KEYSENSOR);
	}

	public StringSensorNode getStringSensorNodes() {
		return (StringSensorNode)getChildNodes(NodeType.STRINGSENSOR);
	}

	// 30. Event Utilities component (X3D)

	public BooleanFilterNode getBooleanFilterNodes() {
		return (BooleanFilterNode)getChildNodes(NodeType.BOOLEANFILTER);
	}

	public BooleanToggleNode getBooleanToggleNodes() {
		return (BooleanToggleNode)getChildNodes(NodeType.BOOLEANTOGGLE);
	}

	public BooleanTriggerNode getBooleanTriggerNodes() {
		return (BooleanTriggerNode)getChildNodes(NodeType.BOOLEANTRIGGER);
	}

	public BooleanSequencerNode getBooleanSequencerNodes() {
		return (BooleanSequencerNode)getChildNodes(NodeType.BOOLEANSEQUENCER);
	}

	public IntegerTriggerNode getIntegerTriggerNodes() {
		return (IntegerTriggerNode)getChildNodes(NodeType.INTEGERTRIGGER);
	}

	public IntegerSequencerNode getIntegerSequencerNodes() {
		return (IntegerSequencerNode)getChildNodes(NodeType.INTEGERSEQUENCER);
	}

	public TimeTriggerNode getTimeTriggerNodes() {
		return (TimeTriggerNode)getChildNodes(NodeType.TIMETRIGGER);
	}
	
	// Deprecated components (X3D)

	public NodeSequencerNode getNodeSequencerNodes() {
		return (NodeSequencerNode)getChildNodes(NodeType.NODESEQUENCER);
	}

	public Shape2DNode getShape2DNodes() {
		return (Shape2DNode)getChildNodes(NodeType.SHAPE2D);
	}

	public BooleanTimeTriggerNode getBooleanTimeTriggerNodes() {
		return (BooleanTimeTriggerNode)getChildNodes(NodeType.BOOLEANTIMETRIGGER);
	}

	public Transform2DNode getTransform2DNodes() {
		return (Transform2DNode)getChildNodes(NodeType.TRANSFORM2D);
	}

	////////////////////////////////////////////////
	//	get child node list (with name)
	////////////////////////////////////////////////

	public GroupingNode getGroupingNode(String name) {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isGroupingNode() == false)
				continue;
			String nodeName = node.getName();
			if (nodeName == null)
				continue;
			if (nodeName.equals(name) == true)
				return (GroupingNode)node;
		}
		return null;
	}

	public Geometry3DNode getGeometry3DNode(String name) {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isGeometry3DNode() == false)
				continue;
			String nodeName = node.getName();
			if (nodeName == null)
				continue;
			if (nodeName.equals(name) == true)
				return (Geometry3DNode)node;
		}
		return null;
	}

	public TextureNode getTextureNode(String name) {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isTextureNode() == false)
				continue;
			String nodeName = node.getName();
			if (nodeName == null)
				continue;
			if (nodeName.equals(name) == true)
				return (TextureNode)node;
		}
		return null;
	}

	public AnchorNode getAnchorNode(String name) {
		return (AnchorNode)getChildNodes(NodeType.ANCHOR , name);
	}

	public AppearanceNode getAppearanceNode(String name) {
		return (AppearanceNode)getChildNodes(NodeType.APPEARANCE, name);
	}

	public AudioClipNode getAudioClipNode(String name) {
		return (AudioClipNode)getChildNodes(NodeType.AUDIOCLIP, name);
	}

	public BackgroundNode getBackgroundNode(String name) {
		return (BackgroundNode)getChildNodes(NodeType.BACKGROUND, name);
	}

	public BillboardNode getBillboardNode(String name) {
		return (BillboardNode)getChildNodes(NodeType.BILLBOARD, name);
	}

	public BoxNode getBoxNode(String name) {
		return (BoxNode)getChildNodes(NodeType.BOX, name);
	}

	public CollisionNode getCollisionNode(String name) {
		return (CollisionNode)getChildNodes(NodeType.COLLISION, name);
	}

	public ColorNode getColorNode(String name) {
		return (ColorNode)getChildNodes(NodeType.COLOR, name);
	}

	public ColorInterpolatorNode getColorInterpolatorNode(String name) {
		return (ColorInterpolatorNode)getChildNodes(NodeType.COLORINTERP, name);
	}

	public ConeNode getConeNode(String name) {
		return (ConeNode)getChildNodes(NodeType.CONE, name);
	}

	public CoordinateNode getCoordinateNode(String name) {
		return (CoordinateNode)getChildNodes(NodeType.COORD, name);
	}

	public CoordinateInterpolatorNode getCoordinateInterpolatorNode(String name) {
		return (CoordinateInterpolatorNode)getChildNodes(NodeType.COORDINTERP, name);
	}

	public CylinderNode getCylinderNode(String name) {
		return (CylinderNode)getChildNodes(NodeType.CYLINDER, name);
	}

	public CylinderSensorNode getCylinderSensorNode(String name) {
		return (CylinderSensorNode)getChildNodes(NodeType.CYLINDERSENSOR, name);
	}

	public DirectionalLightNode getDirectionalLightNode(String name) {
		return (DirectionalLightNode)getChildNodes(NodeType.DIRLIGHT, name);
	}

	public ElevationGridNode getElevationGridNode(String name) {
		return (ElevationGridNode)getChildNodes(NodeType.ELEVATIONGRID, name);
	}

	public ExtrusionNode getExtrusionNode(String name) {
		return (ExtrusionNode)getChildNodes(NodeType.EXTRUSION, name);
	}

	public FogNode getFogNode(String name) {
		return (FogNode)getChildNodes(NodeType.FOG, name);
	}

	public FontStyleNode getFontStyleNode(String name) {
		return (FontStyleNode)getChildNodes(NodeType.FONTSTYLE, name);
	}

	public GroupNode getGroupNode(String name) {
		return (GroupNode)getChildNodes(NodeType.GROUP, name);
	}

	public ImageTextureNode getImageTextureNode(String name) {
		return (ImageTextureNode)getChildNodes(NodeType.IMAGETEXTURE, name);
	}

	public IndexedFaceSetNode getIndexedFaceSetNode(String name) {
		return (IndexedFaceSetNode)getChildNodes(NodeType.INDEXEDFACESET, name);
	}

	public IndexedLineSetNode getIndexedLineSetNode(String name) {
		return (IndexedLineSetNode)getChildNodes(NodeType.INDEXEDLINESET, name);
	}

	public InlineNode getInlineNode(String name) {
		return (InlineNode)getChildNodes(NodeType.INLINE, name);
	}

	public LODNode getLODNode(String name) {
		return (LODNode)getChildNodes(NodeType.LOD, name);
	}

	public MaterialNode getMaterialNode(String name) {
		return (MaterialNode)getChildNodes(NodeType.MATERIAL, name);
	}

	public MovieTextureNode getMovieTextureNode(String name) {
		return (MovieTextureNode)getChildNodes(NodeType.MOVIETEXTURE, name);
	}

	public NavigationInfoNode getNavigationInfoNode(String name) {
		return (NavigationInfoNode)getChildNodes(NodeType.NAVIGATIONINFO, name);
	}

	public NormalNode getNormalNode(String name) {
		return (NormalNode)getChildNodes(NodeType.NORMAL, name);
	}

	public NormalInterpolatorNode getNormalInterpolatorNode(String name) {
		return (NormalInterpolatorNode)getChildNodes(NodeType.NORMALINTERP, name);
	}

	public OrientationInterpolatorNode getOrientationInterpolatorNode(String name) {
		return (OrientationInterpolatorNode)getChildNodes(NodeType.ORIENTATIONINTERP, name);
	}

	public PixelTextureNode getPixelTextureNode(String name) {
		return (PixelTextureNode)getChildNodes(NodeType.PIXELTEXTURE, name);
	}

	public PlaneSensorNode getPlaneSensorNode(String name) {
		return (PlaneSensorNode)getChildNodes(NodeType.PLANESENSOR, name);
	}

	public PointLightNode getPointLightNode(String name) {
		return (PointLightNode)getChildNodes(NodeType.POINTLIGHT, name);
	}

	public PointSetNode getPointSetNode(String name) {
		return (PointSetNode)getChildNodes(NodeType.POINTSET, name);
	}

	public PositionInterpolatorNode getPositionInterpolatorNode(String name) {
		return (PositionInterpolatorNode)getChildNodes(NodeType.POSITONINTERP, name);
	}

	public ProximitySensorNode getProximitySensorNode(String name) {
		return (ProximitySensorNode)getChildNodes(NodeType.PROXIMITYSENSOR, name);
	}

	public ProxyNode getProxyNode(String name) {
		return (ProxyNode)getChildNodes(NodeType.PROXY, name);
	}

	public ScalarInterpolatorNode getScalarInterpolatorNode(String name) {
		return (ScalarInterpolatorNode)getChildNodes(NodeType.SCALARINTERP, name);
	}

	public ScriptNode getScriptNode(String name) {
		return (ScriptNode)getChildNodes(NodeType.SCRIPT, name);
	}

	public ShapeNode getShapeNode(String name) {
		return (ShapeNode)getChildNodes(NodeType.SHAPE, name);
	}

	public SoundNode getSoundNode(String name) {
		return (SoundNode)getChildNodes(NodeType.SOUND, name);
	}

	public SphereNode getSphereNode(String name) {
		return (SphereNode)getChildNodes(NodeType.SPHERE, name);
	}

	public SphereSensorNode getSphereSensorNode(String name) {
		return (SphereSensorNode)getChildNodes(NodeType.SPHERESENSOR, name);
	}

	public SpotLightNode getSpotLightNode(String name) {
		return (SpotLightNode)getChildNodes(NodeType.SPOTLIGHT, name);
	}

	public SwitchNode getSwitchNode(String name) {
		return (SwitchNode)getChildNodes(NodeType.SWITCH, name);
	}

	public TextNode getTextNode(String name) {
		return (TextNode)getChildNodes(NodeType.TEXT, name);
	}

	public TextureCoordinateNode getTextureCoordinateNode(String name) {
		return (TextureCoordinateNode)getChildNodes(NodeType.TEXTURECOORD, name);
	}

	public TextureTransformNode getTextureTransformNode(String name) {
		return (TextureTransformNode)getChildNodes(NodeType.TEXTURETRANSFORM, name);
	}

	public TimeSensorNode getTimeSensorNode(String name) {
		return (TimeSensorNode)getChildNodes(NodeType.TIMESENSOR, name);
	}

	public TouchSensorNode getTouchSensorNode(String name) {
		return (TouchSensorNode)getChildNodes(NodeType.TOUCHSENSOR, name);
	}

	public TransformNode getTransformNode(String name) {
		return (TransformNode)getChildNodes(NodeType.TRANSFORM, name);
	}

	public ViewpointNode getViewpointNode(String name) {
		return (ViewpointNode)getChildNodes(NodeType.VIEWPOINT, name);
	}

	public VisibilitySensorNode getVisibilitySensorNode(String name) {
		return (VisibilitySensorNode)getChildNodes(NodeType.VISIBILITYSENSOR, name);
	}

	public WorldInfoNode getWorldInfoNode(String name) {
		return (WorldInfoNode)getChildNodes(NodeType.WORLDINFO, name);
	}

	// 9. Networking component (X3D)

	public LoadSensorNode getLoadSensorNode(String name) {
		return (LoadSensorNode)getChildNodes(NodeType.LOADSENSOR, name);
	}

	// 10. Grouping component (X3D)

	public StaticGroupNode getStaticGroupNode(String name) {
		return (StaticGroupNode)getChildNodes(NodeType.STATICGROUP, name);
	}

	// 11. Rendering component (X3D)

	public ColorRGBANode getColorRGBANode(String name) {
		return (ColorRGBANode)getChildNodes(NodeType.COLORRGBA, name);
	}

	public TriangleSetNode getTriangleSetNode(String name) {
		return (TriangleSetNode)getChildNodes(NodeType.TRIANGLESET, name);
	}

	public TriangleFanSetNode getTriangleFanSetNode(String name) {
		return (TriangleFanSetNode)getChildNodes(NodeType.TRIANGLEFANSET, name);
	}

	public TriangleStripSetNode getTriangleStripSetNode(String name) {
		return (TriangleStripSetNode)getChildNodes(NodeType.TRIANGLESTRIPSET, name);
	}
	
	// 12. Shape component (X3D)

	public FillPropertiesNode getFillPropertiesNode(String name) {
		return (FillPropertiesNode)getChildNodes(NodeType.FILLPROPERTIES, name);
	}

	public LinePropertiesNode getLinePropertiesNode(String name) {
		return (LinePropertiesNode)getChildNodes(NodeType.LINEPROPERTIES, name);
	}

	// 14. Geometry2D component (X3D)

	public Arc2DNode getArc2DNode(String name) {
		return (Arc2DNode)getChildNodes(NodeType.ARC2D, name);
	}

	public ArcClose2DNode getArcClose2DNode(String name) {
		return (ArcClose2DNode)getChildNodes(NodeType.ARCCLOSE2D, name);
	}

	public Circle2DNode getCircle2DNode(String name) {
		return (Circle2DNode)getChildNodes(NodeType.CIRCLE2D, name);
	}

	public Disk2DNode getDisk2DNode(String name) {
		return (Disk2DNode)getChildNodes(NodeType.DISK2D, name);
	}

	public Polyline2DNode getPolyline2DNode(String name) {
		return (Polyline2DNode)getChildNodes(NodeType.POLYLINE2D, name);
	}

	public Polypoint2DNode getPolypoint2DNode(String name) {
		return (Polypoint2DNode)getChildNodes(NodeType.POLYPOINT2D, name);
	}

	public Rectangle2DNode getRectangle2DNode(String name) {
		return (Rectangle2DNode)getChildNodes(NodeType.RECTANGLE2D, name);
	}

	public TriangleSet2DNode getTriangleSet2DNode(String name) {
		return (TriangleSet2DNode)getChildNodes(NodeType.TRIANGLESET2D, name);
	}
	
	// 18. Texturing component (x3D)

	public MultiTextureNode getMultiTextureNode(String name) {
		return (MultiTextureNode)getChildNodes(NodeType.MULTITEXTURE, name);
	}

	public MultiTextureCoordinateNode getMultiTextureCoordinateNode(String name) {
		return (MultiTextureCoordinateNode)getChildNodes(NodeType.MULTITEXTURECOORD, name);
	}

	public MultiTextureTransformNode getMultiTextureTransformNode(String name) {
		return (MultiTextureTransformNode)getChildNodes(NodeType.MULTITEXTURETRANSFORM, name);
	}
	
	public TextureCoordinateGeneratorNode getTextureCoordinateGeneratorNode(String name) {
		return (TextureCoordinateGeneratorNode)getChildNodes(NodeType.TEXCOORDGEN, name);
	}
	
	// 19. Interpolation component (X3D)

	public CoordinateInterpolator2DNode getCoordinateInterpolator2DNode(String name) {
		return (CoordinateInterpolator2DNode)getChildNodes(NodeType.COORDINATEINTERPOLATOR2D, name);
	}

	public PositionInterpolator2DNode getPositionInterpolator2DNode(String name) {
		return (PositionInterpolator2DNode)getChildNodes(NodeType.POSITIONINTERPOLATOR2D, name);
	}

	// 21. Key device sensor component (X3D)

	public KeySensorNode getKeySensorNode(String name) {
		return (KeySensorNode)getChildNodes(NodeType.KEYSENSOR, name);
	}

	public StringSensorNode getStringSensorNode(String name) {
		return (StringSensorNode)getChildNodes(NodeType.STRINGSENSOR, name);
	}

	// 30. Event Utilities component (X3D)

	public BooleanFilterNode getBooleanFilterNode(String name) {
		return (BooleanFilterNode)getChildNodes(NodeType.BOOLEANFILTER, name);
	}

	public BooleanToggleNode getBooleanToggleNode(String name) {
		return (BooleanToggleNode)getChildNodes(NodeType.BOOLEANTOGGLE, name);
	}

	public BooleanTriggerNode getBooleanTriggerNode(String name) {
		return (BooleanTriggerNode)getChildNodes(NodeType.BOOLEANTRIGGER, name);
	}

	public BooleanSequencerNode getBooleanSequencerNode(String name) {
		return (BooleanSequencerNode)getChildNodes(NodeType.BOOLEANSEQUENCER, name);
	}

	public IntegerTriggerNode getIntegerTriggerNode(String name) {
		return (IntegerTriggerNode)getChildNodes(NodeType.INTEGERTRIGGER, name);
	}

	public IntegerSequencerNode getIntegerSequencerNode(String name) {
		return (IntegerSequencerNode)getChildNodes(NodeType.INTEGERSEQUENCER, name);
	}

	public TimeTriggerNode getTimeTriggerNode(String name) {
		return (TimeTriggerNode)getChildNodes(NodeType.TIMETRIGGER, name);
	}
	
	// Deprecated components (X3D)

	public NodeSequencerNode getNodeSequencerNode(String name) {
		return (NodeSequencerNode)getChildNodes(NodeType.NODESEQUENCER, name);
	}

	public Shape2DNode getShape2DNode(String name) {
		return (Shape2DNode)getChildNodes(NodeType.SHAPE2D, name);
	}

	public BooleanTimeTriggerNode getBooleanTimeTriggerNode(String name) {
		return (BooleanTimeTriggerNode)getChildNodes(NodeType.BOOLEANTIMETRIGGER, name);
	}

	public Transform2DNode getTransform2DNode(String name) {
		return (Transform2DNode)getChildNodes(NodeType.TRANSFORM2D, name);
	}


	////////////////////////////////////////////////
	//	get child node list
	////////////////////////////////////////////////

	public boolean hasGroupingNodes() {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isGroupingNode())
				return true;
		}
		return false;
	}

	public boolean hasGeometry3DNode() {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isGeometry3DNode())
				return true;
		}
		return false;
	}

	public boolean hasTextureNode() {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isTextureNode())
				return true;
		}
		return false;
	}

	public boolean hasAnchorNodes() {
		return (getChildNodes(NodeType.ANCHOR) != null ? true : false);
	}

	public boolean hasAppearanceNodes() {
		return (getChildNodes(NodeType.APPEARANCE) != null ? true : false);
	}

	public boolean hasAudioClipNodes() {
		return (getChildNodes(NodeType.AUDIOCLIP) != null ? true : false);
	}

	public boolean hasBackgroundNodes() {
		return (getChildNodes(NodeType.BACKGROUND) != null ? true : false);
	}

	public boolean hasBillboardNodes() {
		return (getChildNodes(NodeType.BILLBOARD) != null ? true : false);
	}

	public boolean hasBoxNodes() {
		return (getChildNodes(NodeType.BOX) != null ? true : false);
	}

	public boolean hasCollisionNodes() {
		return (getChildNodes(NodeType.COLLISION) != null ? true : false);
	}

	public boolean hasColorNodes() {
		return (getChildNodes(NodeType.COLOR) != null ? true : false);
	}

	public boolean hasColorInterpolatorNodes() {
		return (getChildNodes(NodeType.COLORINTERP) != null ? true : false);
	}

	public boolean hasConeNodes() {
		return (getChildNodes(NodeType.CONE) != null ? true : false);
	}

	public boolean hasCoordinateNodes() {
		return (getChildNodes(NodeType.COORD) != null ? true : false);
	}

	public boolean hasCoordinateInterpolatorNodes() {
		return (getChildNodes(NodeType.COORDINTERP) != null ? true : false);
	}

	public boolean hasCylinderNodes() {
		return (getChildNodes(NodeType.CYLINDER) != null ? true : false);
	}

	public boolean hasCylinderSensorNodes() {
		return (getChildNodes(NodeType.CYLINDERSENSOR) != null ? true : false);
	}

	public boolean hasDirectionalLightNodes() {
		return (getChildNodes(NodeType.DIRLIGHT) != null ? true : false);
	}

	public boolean hasElevationGridNodes() {
		return (getChildNodes(NodeType.ELEVATIONGRID) != null ? true : false);
	}

	public boolean hasExtrusionNodes() {
		return (getChildNodes(NodeType.EXTRUSION) != null ? true : false);
	}

	public boolean hasFogNodes() {
		return (getChildNodes(NodeType.FOG) != null ? true : false);
	}

	public boolean hasFontStyleNodes() {
		return (getChildNodes(NodeType.FONTSTYLE) != null ? true : false);
	}

	public boolean hasGroupNodes() {
		return (getChildNodes(NodeType.GROUP) != null ? true : false);
	}

	public boolean hasImageTextureNodes() {
		return (getChildNodes(NodeType.IMAGETEXTURE) != null ? true : false);
	}

	public boolean hasIndexedFaceSetNodes() {
		return (getChildNodes(NodeType.INDEXEDFACESET) != null ? true : false);
	}

	public boolean hasIndexedLineSetNodes() {
		return (getChildNodes(NodeType.INDEXEDLINESET) != null ? true : false);
	}

	public boolean hasInlineNodes() {
		return (getChildNodes(NodeType.INLINE) != null ? true : false);
	}

	public boolean hasLODNodes() {
		return (getChildNodes(NodeType.LOD) != null ? true : false);
	}

	public boolean hasMaterialNodes() {
		return (getChildNodes(NodeType.MATERIAL) != null ? true : false);
	}

	public boolean hasMovieTextureNodes() {
		return (getChildNodes(NodeType.MOVIETEXTURE) != null ? true : false);
	}

	public boolean hasNavigationInfoNodes() {
		return (getChildNodes(NodeType.NAVIGATIONINFO) != null ? true : false);
	}

	public boolean hasNormalNodes() {
		return (getChildNodes(NodeType.NORMAL) != null ? true : false);
	}

	public boolean hasNormalInterpolatorNodes() {
		return (getChildNodes(NodeType.NORMALINTERP) != null ? true : false);
	}

	public boolean hasOrientationInterpolatorNodes() {
		return (getChildNodes(NodeType.ORIENTATIONINTERP) != null ? true : false);
	}

	public boolean hasPixelTextureNodes() {
		return (getChildNodes(NodeType.PIXELTEXTURE) != null ? true : false);
	}

	public boolean hasPlaneSensorNodes() {
		return (getChildNodes(NodeType.PLANESENSOR) != null ? true : false);
	}

	public boolean hasPointLightNodes() {
		return (getChildNodes(NodeType.POINTLIGHT) != null ? true : false);
	}

	public boolean hasPointSetNodes() {
		return (getChildNodes(NodeType.POINTSET) != null ? true : false);
	}

	public boolean hasPositionInterpolatorNodes() {
		return (getChildNodes(NodeType.POSITONINTERP) != null ? true : false);
	}

	public boolean hasProximitySensorNodes() {
		return (getChildNodes(NodeType.PROXIMITYSENSOR) != null ? true : false);
	}

	public boolean hasProxyNodes() {
		return (getChildNodes(NodeType.PROXY) != null ? true : false);
	}

	public boolean hasScalarInterpolatorNodes() {
		return (getChildNodes(NodeType.SCALARINTERP) != null ? true : false);
	}

	public boolean hasScriptNodes() {
		return (getChildNodes(NodeType.SCRIPT) != null ? true : false);
	}

	public boolean hasShapeNodes() {
		return (getChildNodes(NodeType.SHAPE) != null ? true : false);
	}

	public boolean hasSoundNodes() {
		return (getChildNodes(NodeType.SOUND) != null ? true : false);
	}

	public boolean hasSphereNodes() {
		return (getChildNodes(NodeType.SPHERE) != null ? true : false);
	}

	public boolean hasSphereSensorNodes() {
		return (getChildNodes(NodeType.SPHERESENSOR) != null ? true : false);
	}

	public boolean hasSpotLightNodes() {
		return (getChildNodes(NodeType.SPOTLIGHT) != null ? true : false);
	}

	public boolean hasSwitchNodes() {
		return (getChildNodes(NodeType.SWITCH) != null ? true : false);
	}

	public boolean hasTextNodes() {
		return (getChildNodes(NodeType.TEXT) != null ? true : false);
	}

	public boolean hasTextureCoordinateNodes() {
		return (getChildNodes(NodeType.TEXTURECOORD) != null ? true : false);
	}

	public boolean hasTextureTransformNodes() {
		return (getChildNodes(NodeType.TEXTURETRANSFORM) != null ? true : false);
	}

	public boolean hasTimeSensorNodes() {
		return (getChildNodes(NodeType.TIMESENSOR) != null ? true : false);
	}

	public boolean hasTouchSensorNodes() {
		return (getChildNodes(NodeType.TOUCHSENSOR) != null ? true : false);
	}

	public boolean hasTransformNodes() {
		return (getChildNodes(NodeType.TRANSFORM) != null ? true : false);
	}

	public boolean hasViewpointNodes() {
		return (getChildNodes(NodeType.VIEWPOINT) != null ? true : false);
	}

	public boolean hasVisibilitySensorNodes() {
		return (getChildNodes(NodeType.VISIBILITYSENSOR) != null ? true : false);
	}

	public boolean hasWorldInfoNodes() {
		return (getChildNodes(NodeType.WORLDINFO) != null ? true : false);
	}

	// 9. Networking component (X3D)
	
	public boolean hasLoadSensorNode() {
		return (getChildNodes(NodeType.LOADSENSOR) != null ? true : false);
	}
	
	// 10. Grouping component (X3D)
	
	public boolean hasStaticGroupNode() {
		return (getChildNodes(NodeType.STATICGROUP) != null ? true : false);
	}

	// 11. Rendering component (X3D)
	
	public boolean hasColorRGBANode() {
		return (getChildNodes(NodeType.COLORRGBA) != null ? true : false);
	}

	public boolean hasTriangleSetNode() {
		return (getChildNodes(NodeType.TRIANGLESET) != null ? true : false);
	}

	public boolean hasTriangleFanSetNode() {
		return (getChildNodes(NodeType.TRIANGLEFANSET) != null ? true : false);
	}

	public boolean hasTriangleStripSetNode() {
		return (getChildNodes(NodeType.TRIANGLESTRIPSET) != null ? true : false);
	}

	// 12. Shape component (X3D)

	public boolean hasFillPropertiesNode() {
		return (getChildNodes(NodeType.FILLPROPERTIES) != null ? true : false);
	}

	public boolean hasLinePropertiesNode() {
		return (getChildNodes(NodeType.LINEPROPERTIES) != null ? true : false);
	}

	// 14. Geometry2D component (X3D)

	public boolean hasArc2DNode() {
		return (getChildNodes(NodeType.ARC2D) != null ? true : false);
	}

	public boolean hasArcClose2DNode() {
		return (getChildNodes(NodeType.ARCCLOSE2D) != null ? true : false);
	}

	public boolean hasCircle2DNode() {
		return (getChildNodes(NodeType.CIRCLE2D) != null ? true : false);
	}

	public boolean hasDisk2DNode() {
		return (getChildNodes(NodeType.DISK2D) != null ? true : false);
	}

	public boolean hasPolyline2DNode() {
		return (getChildNodes(NodeType.POLYLINE2D) != null ? true : false);
	}

	public boolean hasPolypoint2DNode() {
		return (getChildNodes(NodeType.POLYPOINT2D) != null ? true : false);
	}

	public boolean hasRectangle2DNode() {
		return (getChildNodes(NodeType.RECTANGLE2D) != null ? true : false);
	}

	public boolean hasTriangleSet2DNode() {
		return (getChildNodes(NodeType.TRIANGLESET2D) != null ? true : false);
	}

	// 18. Texturing component (x3D)

	public boolean hasMultiTextureNode() {
		return (getChildNodes(NodeType.MULTITEXTURE) != null ? true : false);
	}

	public boolean hasMultiTextureCoordinateNode() {
		return (getChildNodes(NodeType.MULTITEXTURECOORD) != null ? true : false);
	}

	public boolean hasMultiTextureTransformNode() {
		return (getChildNodes(NodeType.MULTITEXTURETRANSFORM) != null ? true : false);
	}

	public boolean hasTextureCoordinateGeneratorNode() {
		return (getChildNodes(NodeType.TEXCOORDGEN) != null ? true : false);
	}

	// 19. Interpolation component (X3D)

	public boolean hasCoordinateInterpolator2DNode() {
		return (getChildNodes(NodeType.COORDINATEINTERPOLATOR2D) != null ? true : false);
	}

	public boolean hasPositionInterpolator2DNode() {
		return (getChildNodes(NodeType.POSITIONINTERPOLATOR2D) != null ? true : false);
	}

	// 21. Key device sensor component (X3D)

	public boolean hasKeySensorNode() {
		return (getChildNodes(NodeType.KEYSENSOR) != null ? true : false);
	}

	public boolean hasStringSensorNode() {
		return (getChildNodes(NodeType.STRINGSENSOR) != null ? true : false);
	}

	// 30. Event Utilities component (X3D)

	public boolean hasBooleanFilterNode() {
		return (getChildNodes(NodeType.BOOLEANFILTER) != null ? true : false);
	}

	public boolean hasBooleanToggleNode() {
		return (getChildNodes(NodeType.BOOLEANTOGGLE) != null ? true : false);
	}

	public boolean hasBooleanTriggerNode() {
		return (getChildNodes(NodeType.BOOLEANTRIGGER) != null ? true : false);
	}

	public boolean hasBooleanSequencerNode() {
		return (getChildNodes(NodeType.BOOLEANSEQUENCER) != null ? true : false);
	}

	public boolean hasIntegerTriggerNode() {
		return (getChildNodes(NodeType.INTEGERTRIGGER) != null ? true : false);
	}

	public boolean hasIntegerSequencerNode() {
		return (getChildNodes(NodeType.INTEGERSEQUENCER) != null ? true : false);
	}

	public boolean hasTimeTriggerNode() {
		return (getChildNodes(NodeType.TIMETRIGGER) != null ? true : false);
	}

	// Deprecated components (X3D)

	public boolean hasNodeSequencerNode() {
		return (getChildNodes(NodeType.NODESEQUENCER) != null ? true : false);
	}

	public boolean hasShape2DNode() {
		return (getChildNodes(NodeType.SHAPE2D) != null ? true : false);
	}

	public boolean hasBooleanTimeTriggerNode() {
		return (getChildNodes(NodeType.BOOLEANTIMETRIGGER) != null ? true : false);
	}

	public boolean hasTransform2DNode() {
		return (getChildNodes(NodeType.TRANSFORM2D) != null ? true : false);
	}

	////////////////////////////////////////////////
	//	is(XML|VRML|X3D)Node
	////////////////////////////////////////////////
	
	public boolean isXMLNode() {
		return isNode(NodeType.XML);
	}
	
	public boolean isVRML97Node() {
		return !isXMLNode();
	}

	public boolean isX3DNode() {
		return !isXMLNode();
	}

	////////////////////////////////////////////////
	//	isNode
	////////////////////////////////////////////////

	public boolean isNode(NodeType type) {
		NodeType nodeType = getType();
		if (nodeType.equals(type) == true)
			return true;
		return false;
	}

	////////////////////////////////////////////////
	//	isNode (VRML97/Grouping)
	////////////////////////////////////////////////

	public boolean isGroupingNode() {
		if (isAnchorNode() || isBillboardNode() || isCollisionNode() || isGroupNode() || isTransformNode() || isInlineNode() || isSwitchNode())
			return true;
		else
			return false;
	}

	public boolean isSpecialGroupNode() {
		if (isInlineNode() || isLODNode() || isSwitchNode())
			return true;
		else
			return false;
	}

	public boolean isCommonNode() {
		if (isLightNode() || isAudioClipNode() || isScriptNode() || isShapeNode() || isSoundNode() || isWorldInfoNode())
			return true;
		else
			return false;
	}

	public boolean isLightNode() {
		if (isDirectionalLightNode() || isSpotLightNode() || isPointLightNode())
			return true;
		else
			return false;
	}

	public boolean isGeometry3DNode() {
		if (isBoxNode() || isConeNode() || isCylinderNode() || isElevationGridNode() || isExtrusionNode() || isIndexedFaceSetNode() || isIndexedLineSetNode() || isPointSetNode() || isSphereNode() || isTextNode())
			return true;
		else
			return false;
	}

	public boolean isGeometryPropertyNode() {
		if (isColorNode() || isCoordinateNode() || isNormalNode() || isTextureCoordinateNode())
			return true;
		else
			return false;
	}

	public boolean isTextureNode() {
		if (isMovieTextureNode() || isPixelTextureNode() || isImageTextureNode() )
			return true;
		else
			return false;
	}

	public boolean isSensorNode() {
		if (isCylinderSensorNode() || isPlaneSensorNode() || isSphereSensorNode() || isProximitySensorNode() || isTimeSensorNode() || isTouchSensorNode() || isVisibilitySensorNode())
			return true;
		else
			return false;
	}

	public boolean isInterpolatorNode() {
		if (isColorInterpolatorNode() || isCoordinateInterpolatorNode() || isNormalInterpolatorNode() || isOrientationInterpolatorNode() || isPositionInterpolatorNode() || isScalarInterpolatorNode())
			return true;
		else
			return false;
	}

	public boolean isAppearancePropertyNode() {
		if (isMaterialNode() || isTextureTransformNode() || isTextureNode())
			return true;
		else
			return false;
	}

	public boolean isAppearanceInfoNode() {
		if (isAppearanceNode() || isFontStyleNode() || isMaterialNode() || isTextureTransformNode() || isTextureNode())
			return true;
		else
			return false;
	}

	public boolean isBindableNode() {
		if (isBackgroundNode() || isFogNode() || isNavigationInfoNode() || isViewpointNode())
			return true;
		else
			return false;
	}

	////////////////////////////////////////////////
	//	isNode (VRML97)
	////////////////////////////////////////////////

	public boolean isRootNode() {
		return isNode(NodeType.ROOT);
	}
	
	public boolean isAnchorNode() {
		return isNode(NodeType.ANCHOR );
	}

	public boolean isAppearanceNode() {
		return isNode(NodeType.APPEARANCE);
	}

	public boolean isAudioClipNode() {
		return isNode(NodeType.AUDIOCLIP);
	}

	public boolean isBackgroundNode() {
		return isNode(NodeType.BACKGROUND);
	}

	public boolean isBillboardNode() {
		return isNode(NodeType.BILLBOARD);
	}

	public boolean isBoxNode() {
		return isNode(NodeType.BOX);
	}

	public boolean isCollisionNode() {
		return isNode(NodeType.COLLISION);
	}

	public boolean isColorNode() {
		return isNode(NodeType.COLOR);
	}

	public boolean isColorInterpolatorNode() {
		return isNode(NodeType.COLORINTERP);
	}

	public boolean isConeNode() {
		return isNode(NodeType.CONE);
	}

	public boolean isCoordinateNode() {
		return isNode(NodeType.COORD);
	}

	public boolean isCoordinateInterpolatorNode() {
		return isNode(NodeType.COORDINTERP);
	}

	public boolean isCylinderNode() {
		return isNode(NodeType.CYLINDER);
	}

	public boolean isCylinderSensorNode() {
		return isNode(NodeType.CYLINDERSENSOR);
	}

	public boolean isDirectionalLightNode() {
		return isNode(NodeType.DIRLIGHT);
	}

	public boolean isElevationGridNode() {
		return isNode(NodeType.ELEVATIONGRID);
	}

	public boolean isExtrusionNode() {
		return isNode(NodeType.EXTRUSION);
	}

	public boolean isFogNode() {
		return isNode(NodeType.FOG);
	}

	public boolean isFontStyleNode() {
		return isNode(NodeType.FONTSTYLE);
	}

	public boolean isGroupNode() {
		return isNode(NodeType.GROUP);
	}

	public boolean isImageTextureNode() {
		return isNode(NodeType.IMAGETEXTURE);
	}

	public boolean isIndexedFaceSetNode() {
		return isNode(NodeType.INDEXEDFACESET);
	}

	public boolean isIndexedLineSetNode() {
		return isNode(NodeType.INDEXEDLINESET);
	}

	public boolean isInlineNode() {
		return isNode(NodeType.INLINE);
	}

	public boolean isLODNode() {
		return isNode(NodeType.LOD);
	}

	public boolean isMaterialNode() {
		return isNode(NodeType.MATERIAL);
	}

	public boolean isMovieTextureNode() {
		return isNode(NodeType.MOVIETEXTURE);
	}

	public boolean isNavigationInfoNode() {
		return isNode(NodeType.NAVIGATIONINFO);
	}

	public boolean isNormalNode() {
		return isNode(NodeType.NORMAL);
	}

	public boolean isNormalInterpolatorNode() {
		return isNode(NodeType.NORMALINTERP);
	}

	public boolean isOrientationInterpolatorNode() {
		return isNode(NodeType.ORIENTATIONINTERP);
	}

	public boolean isPixelTextureNode() {
		return isNode(NodeType.PIXELTEXTURE);
	}

	public boolean isPlaneSensorNode() {
		return isNode(NodeType.PLANESENSOR);
	}

	public boolean isPointLightNode() {
		return isNode(NodeType.POINTLIGHT);
	}

	public boolean isPointSetNode() {
		return isNode(NodeType.POINTSET);
	}

	public boolean isPositionInterpolatorNode() {
		return isNode(NodeType.POSITONINTERP);
	}

	public boolean isProximitySensorNode() {
		return isNode(NodeType.PROXIMITYSENSOR);
	}

	public boolean isProxyNode() {
		return isNode(NodeType.PROXY);
	}

	public boolean isScalarInterpolatorNode() {
		return isNode(NodeType.SCALARINTERP);
	}

	public boolean isScriptNode() {
		return isNode(NodeType.SCRIPT);
	}

	public boolean isShapeNode() {
		return isNode(NodeType.SHAPE);
	}

	public boolean isSoundNode() {
		return isNode(NodeType.SOUND);
	}

	public boolean isSphereNode() {
		return isNode(NodeType.SPHERE);
	}

	public boolean isSphereSensorNode() {
		return isNode(NodeType.SPHERESENSOR);
	}

	public boolean isSpotLightNode() {
		return isNode(NodeType.SPOTLIGHT);
	}

	public boolean isSwitchNode() {
		return isNode(NodeType.SWITCH);
	}

	public boolean isTextNode() {
		return isNode(NodeType.TEXT);
	}

	public boolean isTextureCoordinateNode() {
		return isNode(NodeType.TEXTURECOORD);
	}

	public boolean isTextureTransformNode() {
		return isNode(NodeType.TEXTURETRANSFORM);
	}

	public boolean isTimeSensorNode() {
		return isNode(NodeType.TIMESENSOR);
	}

	public boolean isTouchSensorNode() {
		return isNode(NodeType.TOUCHSENSOR);
	}

	public boolean isTransformNode() {
		return isNode(NodeType.TRANSFORM);
	}

	public boolean isViewpointNode() {
		return isNode(NodeType.VIEWPOINT);
	}

	public boolean isVisibilitySensorNode() {
		return isNode(NodeType.VISIBILITYSENSOR);
	}

	public boolean isWorldInfoNode() {
		return isNode(NodeType.WORLDINFO);
	}

	// 9. Networking component (X3D)
	
	public boolean isLoadSensorNode() {
		return isNode(NodeType.LOADSENSOR);
	}
	
	// 10. Grouping component (X3D)
	
	public boolean isStaticGroupNode() {
		return isNode(NodeType.STATICGROUP);
	}

	// 11. Rendering component (X3D)
	
	public boolean isColorRGBANode() {
		return isNode(NodeType.COLORRGBA);
	}

	public boolean isTriangleSetNode() {
		return isNode(NodeType.TRIANGLESET);
	}

	public boolean isTriangleFanSetNode() {
		return isNode(NodeType.TRIANGLEFANSET);
	}

	public boolean isTriangleStripSetNode() {
		return isNode(NodeType.TRIANGLESTRIPSET);
	}

	// 12. Shape component (X3D)

	public boolean isFillPropertiesNode() {
		return isNode(NodeType.FILLPROPERTIES);
	}

	public boolean isLinePropertiesNode() {
		return isNode(NodeType.LINEPROPERTIES);
	}

	// 14. Geometry2D component (X3D)

	public boolean isArc2DNode() {
		return isNode(NodeType.ARC2D);
	}

	public boolean isArcClose2DNode() {
		return isNode(NodeType.ARCCLOSE2D);
	}

	public boolean isCircle2DNode() {
		return isNode(NodeType.CIRCLE2D);
	}

	public boolean isDisk2DNode() {
		return isNode(NodeType.DISK2D);
	}

	public boolean isPolyline2DNode() {
		return isNode(NodeType.POLYLINE2D);
	}

	public boolean isPolypoint2DNode() {
		return isNode(NodeType.POLYPOINT2D);
	}

	public boolean isRectangle2DNode() {
		return isNode(NodeType.RECTANGLE2D);
	}

	public boolean isTriangleSet2DNode() {
		return isNode(NodeType.TRIANGLESET2D);
	}

	// 18. Texturing component (x3D)

	public boolean isMultiTextureNode() {
		return isNode(NodeType.MULTITEXTURE);
	}

	public boolean isMultiTextureCoordinateNode() {
		return isNode(NodeType.MULTITEXTURECOORD);
	}

	public boolean isMultiTextureTransformNode() {
		return isNode(NodeType.MULTITEXTURETRANSFORM);
	}

	public boolean isTextureCoordinateGeneratorNode() {
		return isNode(NodeType.TEXCOORDGEN);
	}

	// 19. Interpolation component (X3D)

	public boolean isCoordinateInterpolator2DNode() {
		return isNode(NodeType.COORDINATEINTERPOLATOR2D);
	}

	public boolean isPositionInterpolator2DNode() {
		return isNode(NodeType.POSITIONINTERPOLATOR2D);
	}

	// 21. Key device sensor component (X3D)

	public boolean isKeySensorNode() {
		return isNode(NodeType.KEYSENSOR);
	}

	public boolean isStringSensorNode() {
		return isNode(NodeType.STRINGSENSOR);
	}

	// 30. Event Utilities component (X3D)

	public boolean isBooleanFilterNode() {
		return isNode(NodeType.BOOLEANFILTER);
	}

	public boolean isBooleanToggleNode() {
		return isNode(NodeType.BOOLEANTOGGLE);
	}

	public boolean isBooleanTriggerNode() {
		return isNode(NodeType.BOOLEANTRIGGER);
	}

	public boolean isBooleanSequencerNode() {
		return isNode(NodeType.BOOLEANSEQUENCER);
	}

	public boolean isIntegerTriggerNode() {
		return isNode(NodeType.INTEGERTRIGGER);
	}

	public boolean isIntegerSequencerNode() {
		return isNode(NodeType.INTEGERSEQUENCER);
	}

	public boolean isTimeTriggerNode() {
		return isNode(NodeType.TIMETRIGGER);
	}

	// Deprecated components (X3D)

	public boolean isNodeSequencerNode() {
		return isNode(NodeType.NODESEQUENCER);
	}

	public boolean isShape2DNode() {
		return isNode(NodeType.SHAPE2D);
	}

	public boolean isBooleanTimeTriggerNode() {
		return isNode(NodeType.BOOLEANTIMETRIGGER);
	}

	public boolean isTransform2DNode() {
		return isNode(NodeType.TRANSFORM2D);
	}

	// Route components (X3D)

	public boolean isRouteNode() {
		return isNode(NodeType.ROUTE);
	}

	////////////////////////////////////////////////
	//	output
	////////////////////////////////////////////////

	public String getSpaceString(int nSpaces) {
		StringBuffer str = new StringBuffer();
		for (int n=0; n<nSpaces; n++)
			str.append(' ');
		return str.toString();
	}

	public String getIndentLevelString(int nIndentLevel) {
		char indentString[] = new char[nIndentLevel];
		for (int n=0; n<nIndentLevel; n++)
			indentString[n] = '\t' ;
		return new String(indentString);
	}

	public void outputHead(PrintWriter ps, String indentString) {
		String nodeName = getName();
		if (nodeName != null && 0 < nodeName.length())
			ps.println(indentString + "DEF " + nodeName + " " + getType() + " {");
		else
			ps.println(indentString +  getType() + " {");
	}

	public void outputTail(PrintWriter ps, String indentString) {
		ps.println(indentString + "}");
	}

	////////////////////////////////////////////////
	//	output
	////////////////////////////////////////////////

	abstract public void outputContext(PrintWriter ps, String indentString);

	public void output(PrintWriter ps, int indentLevel) {

		String indentString = getIndentLevelString(indentLevel);

		if (isInstanceNode() == false) {

			outputHead(ps, indentString);
			outputContext(ps, indentString);
	
			if (!isElevationGridNode() && !isShapeNode() && !isSoundNode() && !isPointSetNode() && !isIndexedFaceSetNode() && 
				!isIndexedLineSetNode() && !isTextNode() && !isAppearanceNode() && !isScriptNode()) {
				if (getChildNodes() != null) {
					if (isLODNode()) 
						ps.println(indentString + "\tlevel [");
					else if (isSwitchNode()) 
						ps.println(indentString + "\tchoice [");
					else
						ps.println(indentString + "\tchildren [");
			
					for (Node cnode = getChildNodes(); cnode != null; cnode = cnode.next()) {
						cnode.output(ps, indentLevel+2);
					}
			
					ps.println(indentString + "\t]");
				}
			}
			outputTail(ps, indentString);
		}
		else
			ps.println(indentString + "USE " + getName());
	}

	public void save(FileOutputStream outputStream){
		PrintWriter pr = new PrintWriter(outputStream);
		output(pr, 0);
		pr.close();
	}

	public void save(String filename) {
		try {
			FileOutputStream outputStream = new FileOutputStream(filename);
			save(outputStream);
			outputStream.close();
		}
		catch (IOException e) {
			System.out.println("Couldn't open the file (" + filename + ")");
		}
	}

	public void print()
	{
		PrintWriter pr = new PrintWriter(System.out);
		output(pr, 0);
		pr.close();
	}
	
	////////////////////////////////////////////////
	//	output XML
	////////////////////////////////////////////////

	private boolean hasString(String str) 
	{
		if (str == null)
			return false;
		if (str.length() <= 0)
			return false;
		return true;
	}

	private boolean hasOutputXMLField(Field field) 
	{
		if (field instanceof SFNode || field instanceof MFNode)
			return false;
		
		if (field instanceof MField) {
			MField	mfield		= (MField)field;
			int		fieldSize 	= mfield.getSize();
			if (fieldSize == 0)
				return false;
		}

		return true;
	}

	private  boolean isOutputXMLFieldInSingleLine() 
	{
		if (getType() == NodeType.XML)
			return true;
		if (hasMField() == false)
			return true;
		return false;
	}
	
	public void outputXMLField(PrintWriter ps, Field field, int indentLevel, boolean isSingleLine) 
	{
//		String indentString		= getIndentLevelString(indentLevel) + " ";
		String indentString		= getIndentLevelString(indentLevel+1);
		String fieldName		= field.getName();
		String spaceString		= indentString + getSpaceString(fieldName.length()+2);

		if (hasOutputXMLField(field) == false)
			return;

		if (isSingleLine == true)
			ps.print(" ");
		else
			ps.println("");
					
		if (field.isSField() == true) {
			if (isSingleLine == false)
				ps.print(indentString);
			ps.print(fieldName + "=\"" + field.toXMLString() + "\"");
			return;
		}
		
		if (field.isMField() == true) {
			MField	mfield			= (MField)field;
			int		fieldSize 		= mfield.getSize();

			if (fieldSize == 0) {
				ps.print(indentString + fieldName + "=\"" + "\"");
				return;
			}
			
			if (fieldSize == 1) {
				Field eleField = (Field)mfield.get(0);
				String eleString = eleField.toXMLString();
				ps.print(indentString + fieldName + "=\"" + eleString + "\"");
				return;
			}
			
			for (int n=0; n<fieldSize; n++) {
				if (n==0)
					ps.print(indentString + fieldName + "=\"\n");
				ps.print(indentString + spaceString);
					
				Field eleField = (Field)mfield.get(n);
				String eleString = eleField.toXMLString();
				
				ps.print(eleString);
				
				if (n < (fieldSize-1)) {
					ps.println("");
					/*
					if (mfield.isSingleValueMField() == true)
						ps.println("");
					else
						ps.println(",");
					*/
				}
				else
					ps.print("\"");
			}
			return;
		}

	}
	
	public void outputXML(PrintWriter ps, int indentLevel) 
	{
		String indentString = getIndentLevelString(indentLevel);

		String tagName;

		if (isInstanceNode() == true) {
			String typeName = getTypeString();
			String nodeName = getName(); 			
			ps.println(indentString + "<" + typeName + " USE=\"" + nodeName + "\"/>");
			return;
		}
				
		if (isVRML97Node() == true) {
			tagName = getTypeString();
			String nodeName	= getName(); 			
			if (hasString(nodeName) == true)
				ps.print(indentString + "<" + tagName + " DEF=\"" + nodeName + "\"");
			else
				ps.print(indentString + "<" + tagName);
		}
		else {
			tagName = getName();
			ps.print(indentString + "<" + tagName);
		}

		boolean isSingleLine = isOutputXMLFieldInSingleLine();
		
		int fieldSize			= getNFields();
		int exposedfieldSize	= getNExposedFields();
		int eventInSize		= getNEventIn();
		int eventOutSize		= getNEventOut();
		
		for (int n=0; n<fieldSize; n++)
			outputXMLField(ps, getField(n), indentLevel, isSingleLine);

		for (int n=0; n<exposedfieldSize; n++)
			outputXMLField(ps, getExposedField(n), indentLevel, isSingleLine);

		for (int n=0; n<eventInSize; n++)
			outputXMLField(ps, getEventIn(n), indentLevel, isSingleLine);

		for (int n=0; n<eventOutSize; n++) 
			outputXMLField(ps, getEventOut(n), indentLevel, isSingleLine);
		
		if (hasChildNodes() == false) {
			ps.println("/>");
			return;
		}

		ps.println(">");
		
		for (Node cnode = getChildNodes(); cnode != null; cnode = cnode.next())
			cnode.outputXML(ps, indentLevel+1);

		ps.println(indentString + "</" + tagName + ">");
	}

	public void saveXML(FileOutputStream outputStream){
		PrintWriter pr = new PrintWriter(outputStream);
		outputXML(pr, 0);
		pr.close();
	}

	public void saveXML(String filename) {
		try {
			FileOutputStream outputStream = new FileOutputStream(filename);
			save(outputStream);
			outputStream.close();
		}
		catch (IOException e) {
			System.out.println("Couldn't open the file (" + filename + ")");
		}
	}

	public void printXML()
	{
		PrintWriter pr = new PrintWriter(System.out);
		outputXML(pr, 0);
		pr.close();
	}

	////////////////////////////////////////////////
	//	getTransformMatrix
	////////////////////////////////////////////////

	public void getTransformMatrix(SFMatrix mxOut) {
		mxOut.init();
		for (Node node=this; node != null ; node=node.getParentNode()) {
			if (node.isTransformNode()) {
				SFMatrix mxTransform = ((TransformNode)node).getSFMatrix();
				mxTransform.add(mxOut);
				mxOut.setValue(mxTransform);
			}
			else if (node.isBillboardNode()) {
				SFMatrix mxBillboard = ((BillboardNode)node).getSFMatrix();
				mxBillboard.add(mxOut);
				mxOut.setValue(mxBillboard);
			}
		}
	}

	public SFMatrix getTransformMatrix() {
		SFMatrix	mx = new SFMatrix();
		getTransformMatrix(mx);
		return mx;
	}

	public void getTransformMatrix(float value[][]) {
		SFMatrix	mx = new SFMatrix();
		getTransformMatrix(mx);
		mx.getValue(value);
	}

	public void getTransformMatrix(float value[]) {
		SFMatrix	mx = new SFMatrix();
		getTransformMatrix(mx);
		mx.getValue(value);
	}
	
	////////////////////////////////////////////////
	//	SceneGraph
	////////////////////////////////////////////////

	public void setSceneGraph(SceneGraph sceneGraph) {
		mSceneGraph = sceneGraph;
		for (Node node = getChildNodes(); node != null; node = node.next())
			node.setSceneGraph(sceneGraph);
	}
	
	public SceneGraph getSceneGraph() {
		return mSceneGraph;
	}

	////////////////////////////////////////////////
	//	Route
	////////////////////////////////////////////////

	public void sendEvent(Field eventOutField) {
		SceneGraph sg = getSceneGraph();
		if (sg != null)
			sg.updateRoute(this, eventOutField);
	}

	////////////////////////////////////////////////
	//	Initialized
	////////////////////////////////////////////////

	public void setInitializationFlag(boolean flag) {
		mInitializationFlag = flag; 
	}

	public boolean isInitialized() {
		return mInitializationFlag; 
	}

	////////////////////////////////////////////////
	//	user data
	////////////////////////////////////////////////

	public void setData(Object data) {
		mUserData = data;
	}

	public Object getData() {
		return mUserData;
	}

	////////////////////////////////////////////////
	//	Instance node
	////////////////////////////////////////////////

	public boolean isInstanceNode() {
		return (getReferenceNode() != null ? true : false);
	}

	public void setReferenceNodeMember(Node node) {
		if (node == null)
			return;
			
		mName					= node.mName;

		mFieldList					= node.mFieldList;
		mPrivateFieldList			= node.mPrivateFieldList;
		mExposedFieldList			= node.mExposedFieldList;
		mEventInFieldList			= node.mEventInFieldList;
		mEventOutFieldList			= node.mEventOutFieldList;
		mInitializationFlag		= node.mInitializationFlag;
		mObject					= node.mObject;
		mThreadObject			= node.mThreadObject;
	}
	
	public void setReferenceNode(Node node) {
		mReferenceNode = node;
	}
	
	public Node getReferenceNode() {
		return mReferenceNode;
	}

	public void setAsInstanceNode(Node node) {
		setReferenceNode(node);
		setReferenceNodeMember(node);
	}
	
	public Node createInstanceNode() {
		Node instanceNode = null;
		
		if (isAnchorNode())
			instanceNode = new AnchorNode();
		else if (isAppearanceNode()) 
			instanceNode = new AppearanceNode();
		else if (isAudioClipNode())
			instanceNode = new AudioClipNode();
		else if (isBackgroundNode())
			instanceNode = new BackgroundNode();
		else if (isBillboardNode())
			instanceNode = new BillboardNode();
		else if (isBoxNode())
			instanceNode = new BoxNode();
		else if (isCollisionNode())
			instanceNode = new CollisionNode();
		else if (isColorNode())
			instanceNode = new ColorNode();
		else if (isColorInterpolatorNode())
			instanceNode = new ColorInterpolatorNode();
		else if (isConeNode())
			instanceNode = new ConeNode();
		else if (isCoordinateNode())
			instanceNode = new CoordinateNode();
		else if (isCoordinateInterpolatorNode())
			instanceNode = new CoordinateInterpolatorNode();
		else if (isCylinderNode())
			instanceNode = new CylinderNode();
		else if (isCylinderSensorNode())
			instanceNode = new CylinderSensorNode();
		else if (isDirectionalLightNode())
			instanceNode = new DirectionalLightNode();
		else if (isElevationGridNode())
			instanceNode = new ElevationGridNode();
		else if (isExtrusionNode())
			instanceNode = new ExtrusionNode();
		else if (isFogNode())
			instanceNode = new FogNode();
		else if (isFontStyleNode())
			instanceNode = new FontStyleNode();
		else if (isGroupNode())
			instanceNode = new GroupNode();
		else if (isImageTextureNode())
			instanceNode = new ImageTextureNode();
		else if (isIndexedFaceSetNode())
			instanceNode = new IndexedFaceSetNode();
		else if (isIndexedLineSetNode()) 
			instanceNode = new IndexedLineSetNode();
		else if (isInlineNode()) 
			instanceNode = new InlineNode();
		else if (isLODNode())
			instanceNode = new LODNode();
		else if (isMaterialNode())
			instanceNode = new MaterialNode();
		else if (isMovieTextureNode())
			instanceNode = new MovieTextureNode();
		else if (isNavigationInfoNode())
			instanceNode = new NavigationInfoNode();
		else if (isNormalNode())
			instanceNode = new NormalNode();
		else if (isNormalInterpolatorNode())
			instanceNode = new NormalInterpolatorNode();
		else if (isOrientationInterpolatorNode())
			instanceNode = new OrientationInterpolatorNode();
		else if (isPixelTextureNode())
			instanceNode = new PixelTextureNode();
		else if (isPlaneSensorNode())
			instanceNode = new PlaneSensorNode();
		else if (isPointLightNode())
			instanceNode = new PointLightNode();
		else if (isPointSetNode())
			instanceNode = new PointSetNode();
		else if (isPositionInterpolatorNode())
			instanceNode = new PositionInterpolatorNode();
		else if (isProximitySensorNode())
			instanceNode = new ProximitySensorNode();
		else if (isProxyNode())
			instanceNode = new ProxyNode();
		else if (isScalarInterpolatorNode())
			instanceNode = new ScalarInterpolatorNode();
		else if (isScriptNode())
			instanceNode = new ScriptNode();
		else if (isShapeNode())
			instanceNode = new ShapeNode();
		else if (isSoundNode())
			instanceNode = new SoundNode();
		else if (isSphereNode())
			instanceNode = new SphereNode();
		else if (isSphereSensorNode())
			instanceNode = new SphereSensorNode();
		else if (isSpotLightNode())
			instanceNode = new SpotLightNode();
		else if (isSwitchNode())
			instanceNode = new SwitchNode();
		else if (isTextNode())
			instanceNode = new TextNode();
		else if (isTextureCoordinateNode())
			instanceNode = new TextureCoordinateNode();
		else if (isTextureTransformNode())
			instanceNode = new TextureTransformNode();
		else if (isTimeSensorNode())
			instanceNode = new TimeSensorNode();
		else if (isTouchSensorNode())
			instanceNode = new TouchSensorNode();
		else if (isTransformNode())
			instanceNode = new TransformNode();
		else if (isViewpointNode())
			instanceNode = new ViewpointNode();
		else if (isVisibilitySensorNode())
			instanceNode = new VisibilitySensorNode();
		else if (isWorldInfoNode())
			instanceNode = new WorldInfoNode();
			
		if (instanceNode != null) {
			instanceNode.setAsInstanceNode(this);
			for (Node cnode=getChildNodes(); cnode != null; cnode = cnode.next()) {
				Node childInstanceNode = cnode.createInstanceNode();
				instanceNode.addChildNode(childInstanceNode);
			}
		}		
		else
			System.out.println("Node::createInstanceNode : this = " + this + ", instanceNode = null");
		
		return instanceNode;
	}

	////////////////////////////////////////////////
	//	Node Object
	////////////////////////////////////////////////
	
	public void setObject(NodeObject object) {
		mObject = object;
	}
	
	public NodeObject getObject() {
		return mObject;
	}
	
	public boolean hasObject() {
		return (mObject != null ? true : false);
	}
	
	public boolean initializeObject() {
		if (hasObject() == true) {
			boolean ret;
			synchronized (mObject) {
				ret = mObject.initialize(this);
			}
			return ret;
		}
		return false;
	}
	
	public boolean uninitializeObject() {
		if (hasObject() == true) {
			boolean ret;
			synchronized (mObject) {
				ret = mObject.uninitialize(this);
			}
			return ret;
		}
		return false;
	}
	
	public boolean updateObject() {
		if (hasObject() == true) {
			boolean ret;
			synchronized (mObject) {
				ret = mObject.update(this);
			}
			return ret;
		}
		return false;
	}
	
	public boolean addObject() {
		Debug.message("Node::addObject = " + this + ", " + mObject);
		if (isRootNode() == true) {
			Debug.warning("\tThis node is a root node !!");
			return false;
		}
		if (hasObject() == true)
			return mObject.add(this);
		return false;
	}

	public boolean removeObject() {
		Debug.message("Node::addObject = " + this + ", " + mObject);
		if (isRootNode() == true) {
			Debug.warning("\tThis node is a root node !!");
			return false;
		}
		if (hasObject() == true)
			return mObject.remove(this);
		return false;
	}

	////////////////////////////////////////////////
	//	Runnable
	////////////////////////////////////////////////
	
	public void setRunnable(boolean value) {
		mRunnable = value;
	}
	
	public boolean isRunnable() {
		if (isInstanceNode() == true)
			return false;
		return mRunnable;
	}
	
	public void setRunnableType(int type) {
		mRunnableType = type;
	}

	public int getRunnableType() {
		return mRunnableType;
	}

	public void setRunnableIntervalTime(int time) {
		mRunnableIntervalTime = time;
	}

	public int getRunnableIntervalTime() {
		return mRunnableIntervalTime;
	}
				
	public void setThreadObject(Thread obj) {
		mThreadObject = obj;
	}

	public Thread getThreadObject() {
		return mThreadObject;
	}

	////////////////////////////////////////////////
	//	Thread
	////////////////////////////////////////////////

	public void run() 
	{
		Thread thisThread = Thread.currentThread();
		while (thisThread == getThreadObject()) {
			update();
			updateObject();
			Thread threadObject = getThreadObject();
			if (threadObject != null) { 
//				threadObject.yield();
				try {
					Thread.sleep(getRunnableIntervalTime());
				} catch (InterruptedException e) {}
			}
		}
	}
	
	public void start() 
	{
		Thread threadObject = getThreadObject();
		if (threadObject == null) {
			threadObject = new Thread(this);
			setThreadObject(threadObject);
			threadObject.start();
		}
	}
	
	public void stop() {
		Thread threadObject = getThreadObject();
		if (threadObject != null) {
			//threadObject.destroy();
			//threadObject.stop();
			setThreadObject(null);
		}
	}

	////////////////////////////////////////////////
	//	Field Operation
	////////////////////////////////////////////////

	protected void setFieldValues(Node node) {
		Field	thisField, nodeField;
		
		int nNodeFields = node.getNFields();
		for (int n=0; n<nNodeFields; n++) {
			nodeField = node.getField(n);
			thisField = getField(nodeField.getName());
			if (thisField != null)
				thisField.setValue(nodeField);
		}

		int nNodePrivateFields = node.getNPrivateFields();
		for (int n=0; n<nNodePrivateFields; n++) {
			nodeField = node.getPrivateField(n);
			thisField = getPrivateField(nodeField.getName());
			if (thisField != null)
				thisField.setValue(nodeField);
		}

		int nNodeExposedFields = node.getNExposedFields();
		for (int n=0; n<nNodeExposedFields; n++) {
			nodeField = node.getExposedField(n);
			thisField = getExposedField(nodeField.getName());
			if (thisField != null)
				thisField.setValue(nodeField);
		}

		int nNodeEventIn = node.getNEventIn();
		for (int n=0; n<nNodeEventIn; n++) {
			nodeField = node.getEventIn(n);
			thisField = getEventIn(nodeField.getName());
			if (thisField != null)
				thisField.setValue(nodeField);
		}

		int nNodeEventOut = node.getNEventOut();
		for (int n=0; n<nNodeEventOut; n++) {
			nodeField = node.getEventOut(n);
			thisField = getEventOut(nodeField.getName());
			if (thisField != null)
				thisField.setValue(nodeField);
		}
	}
	
	private Field createCopyField(Field field) {

		FieldType fieldType = field.getType();
		
		if (fieldType.equals(FieldType.SFBOOL) == true)
			return new SFBool((SFBool)field);
		if (fieldType.equals(FieldType.SFCOLOR) == true)
			return new SFColor((SFColor)field);
		if (fieldType.equals(FieldType.SFFLOAT) == true)
			return new SFFloat((SFFloat)field);
		if (fieldType.equals(FieldType.SFINT32) == true)
			return new SFInt32((SFInt32)field);
		if (fieldType.equals(FieldType.SFROTATION) == true)
			return new SFRotation((SFRotation)field);
		if (fieldType.equals(FieldType.SFSTRING) == true)
			return new SFString((SFString)field);
		if (fieldType.equals(FieldType.SFTIME) == true)
			return new SFTime((SFTime)field);
		if (fieldType.equals(FieldType.SFVEC2F) == true)
			return new SFVec2f((SFVec2f)field);
		if (fieldType.equals(FieldType.SFVEC3F) == true)
			return new SFVec3f((SFVec3f)field);
		if (fieldType.equals(FieldType.SFNODE) == true)
			return new SFNode((SFNode)field);
//		if (fieldType.equals(FieldType.SFIMAGE) == true)
//			return new SFImage((SFImage)field);

//		if (fieldType.equals(FieldType.MFBOOL) == true)
//			return new MFBool((MFBool)field);
		if (fieldType.equals(FieldType.MFCOLOR) == true)
			return new MFColor((MFColor)field);
		if (fieldType.equals(FieldType.MFFLOAT) == true)
			return new MFFloat((MFFloat)field);
		if (fieldType.equals(FieldType.MFINT32) == true)
			return new MFInt32((MFInt32)field);
		if (fieldType.equals(FieldType.MFROTATION) == true)
			return new MFRotation((MFRotation)field);
		if (fieldType.equals(FieldType.MFSTRING) == true)
			return new MFString((MFString)field);
		if (fieldType.equals(FieldType.MFTIME) == true)
			return new MFTime((MFTime)field);
		if (fieldType.equals(FieldType.MFVEC2F) == true)
			return new MFVec2f((MFVec2f)field);
		if (fieldType.equals(FieldType.MFVEC3F) == true)
			return new MFVec3f((MFVec3f)field);
		if (fieldType.equals(FieldType.MFNODE) == true)
			return new MFNode((MFNode)field);
//		if (fieldType.equals(FieldType.MFIMAGE) == true)
//			return new MFImage((MFImage)field);
		
		Debug.warning("Node.createCopyField");
		Debug.warning("\tCouldn't create a copy field of " + field);
		return null;
	}

	public Field createFieldFromString(String fieldType) {

		if (fieldType.compareTo("SFBool") == 0)
			return new SFBool(true);
		else if (fieldType.compareTo("SFColor") == 0)
			return new SFColor(0.0f, 0.0f, 0.0f);
		else if (fieldType.compareTo("SFFloat") == 0)
			return new SFFloat(0.0f);
		else if (fieldType.compareTo("SFInt32") == 0)
			return new SFInt32(0);
		else if (fieldType.compareTo("SFRotation") == 0)
			return new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		else if (fieldType.compareTo("SFString") == 0)
			return new SFString();
		else if (fieldType.compareTo("SFTime") == 0)
			return new SFTime(0.0);
		else if (fieldType.compareTo("SFVec2f") == 0)
			return new SFVec2f(0.0f, 0.0f);
		else if (fieldType.compareTo("SFVec3f") == 0)
			return new SFVec3f(0.0f, 0.0f, 0.0f);

		if (fieldType.compareTo("MFColor") == 0)
			return new MFColor();
		else if (fieldType.compareTo("MFFloat") == 0)
			return new MFFloat();
		else if (fieldType.compareTo("MFInt32") == 0)
			return new MFInt32();
		else if (fieldType.compareTo("MFRotation") == 0)
			return new MFRotation();
		else if (fieldType.compareTo("MFString") == 0)
			return new MFString();
		else if (fieldType.compareTo("MFTime") == 0)
			return new MFTime();
		else if (fieldType.compareTo("MFVec2f") == 0)
			return new MFVec2f();
		else if (fieldType.compareTo("MFVec3f") == 0)
			return new MFVec3f();

		return null;
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		String type = getTypeString();
		String name = getName();
		if (name != null) {
			if (0 < name.length() )
				pw.println("DEF " + name + " " + getType());
			else
				pw.println(type);
		}
		else
			pw.println(type);
			
		pw.println(" {");
		outputContext(pw, "");
		pw.println("}");
		
		return sw.toString();
	}
}