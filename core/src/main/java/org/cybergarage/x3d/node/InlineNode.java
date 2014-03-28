/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Inline.java
*
*	Revisions:
*
*	11/15/02
*		- Added the follwing new X3D fields.
*			load
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import java.net.*;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.util.*;

public class InlineNode extends BoundedNode {

	//// VRML97Field ////////////////
	private final static String urlFieldName = "url";
	private MFString urlField;

	//// X3D Field ////////////////
	private final static String loadFieldName = "load";
	private SFBool loadField;
	
	public InlineNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.INLINE);

		///////////////////////////
		// VRML97 Field 
		///////////////////////////

		// url exposed field
		urlField = new MFString();
		addExposedField(urlFieldName, urlField);

		///////////////////////////
		// X3D Field 
		///////////////////////////
		
		// load exposed field
		loadField = new SFBool();
		addExposedField(loadFieldName, loadField);
	}

	public InlineNode(InlineNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	// URL
	////////////////////////////////////////////////

	public MFString getURLField() {
		if (isInstanceNode() == false)
			return urlField;
		return (MFString)getExposedField(urlFieldName);
	}

	public void addURL(String value) {
		getURLField().addValue(value);
	}
	
	public int getNURLs() {
		return getURLField().getSize();
	}
	
	public void setURL(int index, String value) {
		getURLField().set1Value(index, value);
	}

	public void setURLs(String value) {
		getURLField().setValues(value);
	}

	public void setURLs(String value[]) {
		getURLField().setValues(value);
	}
	
	public String getURL(int index) {
		return getURLField().get1Value(index);
	}
	
	public void removeURL(int index) {
		getURLField().removeValue(index);
	}

	////////////////////////////////////////////////
	//	Load (X3D)
	////////////////////////////////////////////////

	public SFBool getLoadField() {
		if (isInstanceNode() == false)
			return loadField;
		return (SFBool)getExposedField(loadFieldName);
	}
	
	public void setLoad(boolean value) {
		getLoadField().setValue(value);
	}

	public boolean getLoad() {
		return getLoadField().getValue();
	}
	
	public boolean isLoad() {
		return getLoad();
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////

	public boolean loadURL() {
	
		Debug.message("InlineNode::loadURL");
		
		removeChildNodes();
		
		if (getNURLs() <= 0)
			return false;
			
		String urlName = getURL(0);
		
		if (urlName == null)
			return true;
			
		SceneGraph sgLoad = new SceneGraph();
		
		if (sgLoad.load(urlName) == false) {
			SceneGraph sg = getSceneGraph();
			if (sg == null)
				return false;
			URL baseURL = sg.getBaseURL();
			if (baseURL != null) {
				try {
					if (sgLoad.load(new URL(baseURL.toString() + urlName)) == false) {
						Debug.message("\tLoading is Failed !!");
						return false;
					}
				} catch (MalformedURLException mue) {
					return false;
				}
			}
		}
		
		Debug.message("\tLoading is OK !!");
		
		sgLoad.initialize();
		
		Node sgNode = sgLoad.getNodes();
		while (sgNode != null) {
			sgNode.remove();
			addChildNode(sgNode);			
			sgNode = sgLoad.getNodes();
		}
		
		return true;
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		MFString url = getURLField();
		printStream.println(indentString + "\t" + "url [");
		url.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
