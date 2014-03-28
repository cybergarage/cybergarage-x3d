/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Route.java
*
******************************************************************/

package org.cybergarage.x3d.route;

import java.io.*;

import org.cybergarage.x3d.util.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.field.*;
import org.cybergarage.x3d.*;

public class Route extends LinkedListNode implements Constants {
	private Node		mEventOutNode		= null;
	private Node		mEventInNode			= null;
	private Field	mEventOutField		= null;
	private Field	mEventInField		= null;
	private Object	mUserData				= null;

	public Route(Node eventOutNode, Field eventOutField, Node eventInNode, Field eventInField) {
		setHeaderFlag(false);
		setEventOutNode(eventOutNode);
		setEventInNode(eventInNode);
		setEventOutField(eventOutField);
		setEventInField(eventInField);
		setData(null);
	}

	public void		setEventOutNode(Node node)		{ mEventOutNode = node; }
	public void		setEventInNode(Node node)		{ mEventInNode = node; }
	public Node		getEventOutNode()					{ return mEventOutNode; }
	public Node		getEventInNode()					{ return mEventInNode; }
	public void		setEventOutField(Field field)	{ mEventOutField = field; }
	public Field	getEventOutField()				{ return mEventOutField; }
	public void		setEventInField(Field field)	{ mEventInField = field; }
	public Field	getEventInField()					{ return mEventInField; }

	public void set(Route route) {
		setEventOutNode(route.getEventOutNode());
		setEventInNode(route.getEventInNode());
		setEventOutField(route.getEventOutField());
		setEventInField(route.getEventInField());
	}
	
	////////////////////////////////////////////////
	//	next node list
	////////////////////////////////////////////////

	public Route next () {
		return (Route)getNextNode();
	}

	////////////////////////////////////////////////
	//	update
	////////////////////////////////////////////////

	public void update() {
	
		Field	eventOutField	= getEventOutField();
		Field	eventInField	= getEventInField();

		if (eventOutField == null || eventInField == null)
			return;

		FieldType eventOutFieldType	= eventOutField.getType();
		FieldType eventInFieldType	= eventInField.getType();	

		////////////////////////////////////////////////
		//	SField
		////////////////////////////////////////////////

		if (eventOutFieldType.equals(FieldType.SFBOOL) == true) {
			SFBool boolOut = (SFBool)eventOutField;
			boolean value = boolOut.getValue();
			if (eventInFieldType.equals(FieldType.SFBOOL) == true) {
				SFBool boolIn = (SFBool)eventInField;
				boolIn.setValue(value);
			}
		}
		
		if (eventOutFieldType.equals(FieldType.SFFLOAT) == true) {
			SFFloat fieldOut = (SFFloat)eventOutField;
			float value = fieldOut.getValue();
			if (eventInFieldType.equals(FieldType.SFFLOAT) == true) {
				SFFloat fieldIn = (SFFloat)eventInField;
				fieldIn.setValue(value);
			}
		}

		if (eventOutFieldType.equals(FieldType.SFINT32) == true) {
			SFInt32 fieldOut = (SFInt32)eventOutField;
			int value = fieldOut.getValue();
			if (eventInFieldType.equals(FieldType.SFINT32) == true) {
				SFInt32 fieldIn = (SFInt32)eventInField;
				fieldIn.setValue(value);
			}
		}
		
		if (eventOutFieldType.equals(FieldType.SFTIME) == true) {
			SFTime fieldOut = (SFTime)eventOutField;
			double value = fieldOut.getValue();
			if (eventInFieldType.equals(FieldType.SFTIME) == true) {
				SFTime fieldIn = (SFTime)eventInField;
				fieldIn.setValue(value);
			}
		}
		
		if (eventOutFieldType.equals(FieldType.SFSTRING) == true) {
			SFString fieldOut = (SFString)eventOutField;
			String value = fieldOut.getValue();
			if (eventInFieldType.equals(FieldType.SFSTRING) == true) {
				SFString fieldIn = (SFString)eventInField;
				fieldIn.setValue(value);
			}
		}
		
		if (eventOutFieldType.equals(FieldType.SFVEC2F) == true) {
			SFVec2f fieldOut = (SFVec2f)eventOutField;
			float value[] = new float[2];
			fieldOut.getValue(value);
			if (eventInFieldType.equals(FieldType.SFVEC2F) == true) {
				SFVec2f fieldIn = (SFVec2f)eventInField;
				fieldIn.setValue(value);
			}
		}
		
		if (eventOutFieldType.equals(FieldType.SFVEC3F) == true) {
			SFVec3f fieldOut = (SFVec3f)eventOutField;
			float value[] = new float[3];
			fieldOut.getValue(value);
			if (eventInFieldType.equals(FieldType.SFVEC3F) == true) {
				SFVec3f fieldIn = (SFVec3f)eventInField;
				fieldIn.setValue(value);
			}
		}
		
		if (eventOutFieldType.equals(FieldType.SFCOLOR) == true) {
			SFColor fieldOut = (SFColor)eventOutField;
			float value[] = new float[3];
			fieldOut.getValue(value);
			if (eventInFieldType.equals(FieldType.SFCOLOR) == true) {
				SFColor fieldIn = (SFColor)eventInField;
				fieldIn.setValue(value);
			}
		}
		
		if (eventOutFieldType.equals(FieldType.SFROTATION) == true) {
			SFRotation fieldOut = (SFRotation)eventOutField;
			float value[] = new float[4];
			fieldOut.getValue(value);
			if (eventInFieldType.equals(FieldType.SFROTATION) == true) {
				SFRotation fieldIn = (SFRotation)eventInField;
				fieldIn.setValue(value);
			}
		}

		////////////////////////////////////////////////
		//	MField
		////////////////////////////////////////////////

		if (eventOutFieldType.equals(FieldType.MFNODE) == true) {
			MFNode outNode = (MFNode)eventOutField;
			if (eventInFieldType.equals(FieldType.MFNODE) == true) {
				MFNode inNode = (MFNode)eventInField;
				inNode.copy(outNode);
			}
		}
		
		if (eventOutFieldType.equals(FieldType.MFSTRING) == true) {
			MFString outString = (MFString)eventOutField;
			if (eventInFieldType.equals(FieldType.MFSTRING) == true) {
				MFString inString = (MFString)eventInField;
				inString.copy(outString);
			}
		}

		if (eventOutFieldType.equals(FieldType.MFCOLOR) == true) {
			MFColor outColor = (MFColor)eventOutField;
			if (eventInFieldType.equals(FieldType.MFCOLOR) == true) {
				MFColor inColor = (MFColor)eventInField;
				inColor.copy(outColor);
			}
		}

		if (eventOutFieldType.equals(FieldType.MFFLOAT) == true) {
			MFFloat outFloat = (MFFloat)eventOutField;
			if (eventInFieldType.equals(FieldType.MFFLOAT) == true) {
				MFFloat inFloat = (MFFloat)eventInField;
				inFloat.copy(outFloat);
			}
		}

		if (eventOutFieldType.equals(FieldType.MFINT32) == true) {
			MFInt32 outInt32 = (MFInt32)eventOutField;
			if (eventInFieldType.equals(FieldType.MFINT32) == true) {
				MFInt32 inInt32 = (MFInt32)eventInField;
				inInt32.copy(outInt32);
			}
		}

		if (eventOutFieldType.equals(FieldType.MFROTATION) == true) {
			MFRotation outRotation = (MFRotation)eventOutField;
			if (eventInFieldType.equals(FieldType.MFROTATION) == true) {
				MFRotation inRotation = (MFRotation)eventInField;
				inRotation.copy(outRotation);
			}
		}

		if (eventOutFieldType.equals(FieldType.MFTIME) == true) {
			MFTime outTime = (MFTime)eventOutField;
			if (eventInFieldType.equals(FieldType.MFTIME) == true) {
				MFTime inTime = (MFTime)eventInField;
				inTime.copy(outTime);
			}
		}

		if (eventOutFieldType.equals(FieldType.MFVEC2F) == true) {
			MFVec2f outVec2f = (MFVec2f)eventOutField;
			if (eventInFieldType.equals(FieldType.MFVEC2F) == true) {
				MFVec2f inVec2f = (MFVec2f)eventInField;
				inVec2f.copy(outVec2f);
			}
		}

		if (eventOutFieldType.equals(FieldType.MFVEC3F) == true) {
			MFVec3f outVec3f = (MFVec3f)eventOutField;
			if (eventInFieldType.equals(FieldType.MFVEC3F) == true) {
				MFVec3f inVec3f = (MFVec3f)eventInField;
				inVec3f.copy(outVec3f);
			}
		}

		Node eventInNode = getEventInNode();
		Node eventInParentNode = eventInNode.getParentNode();

		////////////////////////////////////////////////
		//	BindableNode
		////////////////////////////////////////////////

		if (eventInNode.isBindableNode()) {
			if (((BindableNode)eventInNode).getBindField() == eventInField) {
				SceneGraph sceneGraph = eventInNode.getSceneGraph();
				if (sceneGraph != null)
					sceneGraph.setBindableNode((BindableNode)eventInNode, ((SFBool)eventInField).getValue());			
			}
		}

		////////////////////////////////////////////////
		//	GeometoryNode (2001/11/29)
		////////////////////////////////////////////////

		Geometry3DNode geo = null;		
		if (eventInNode.isGeometry3DNode() == true)
			geo = (Geometry3DNode)eventInNode;
		else if (eventInParentNode.isGeometry3DNode() == true)
			geo = (Geometry3DNode)eventInParentNode;
/*
		else if (eventInNode.isAppearancePropertyNode() == true) {
			if (eventInParentNode != null) {
				Node eventInParentParentNode = eventInParentNode.getParentNode();
				if (eventInParentParentNode != null) {
					if (eventInParentParentNode.isGeometry3DNode() == true) {
						geo = (Geometry3DNode)eventInParentParentNode;
					}
				}
			} 
		}
*/
		
		if (geo != null) {
			geo.uninitializeObject();
			geo.uninitialize();
			geo.initialize();
			geo.initializeObject();
		}
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
	//	output
	////////////////////////////////////////////////

	public void output(PrintWriter ps) {
		ps.println(toString());
	}

	public String toString() {
		StringBuffer routeString = new StringBuffer();
		
		routeString.append("ROUTE ");
		
		if (getEventOutNode() != null)
			routeString.append(getEventOutNode().getName() + ".");
		else
			routeString.append(getEventOutNode() + ".");

		if (getEventOutField() != null)
			routeString.append(getEventOutField().getName() + " TO ");
		else
			routeString.append(getEventOutField() + " TO ");
		
		if (getEventInNode() != null)
			routeString.append(getEventInNode().getName() + ".");
		else
			routeString.append(getEventInNode() + ".");

		if (getEventInField() != null)
			routeString.append(getEventInField().getName());
		else
			routeString.append(getEventInField());
			
		return routeString.toString();
	}

	////////////////////////////////////////////////
	//	output (XML)
	////////////////////////////////////////////////

	public String getIndentLevelString(int nIndentLevel) 
	{
		char indentString[] = new char[nIndentLevel];
		for (int n=0; n<nIndentLevel; n++)
			indentString[n] = '\t' ;
		return new String(indentString);
	}
	
	public void outputXML(PrintWriter ps, int nIndentLevel) 
	{
		Node	fromNode		= getEventOutNode();
		Node	toNode		= getEventInNode();
		Field	fromField	= getEventOutField();
		Field	toField		= getEventInField();

		if (fromNode == null || toNode == null || fromField == null || toField == null)
			return;

		String indentString = getIndentLevelString(nIndentLevel);
		
		ps.print(indentString);
		ps.print("<Route ");
		ps.print("fromNode=\"" + fromNode.getName() + "\" fromField=\"" + fromField.getName() + "\" ");
		ps.print("toNode=\"" + toNode.getName() + "\" toField=\"" + toField.getName() + "\"");
		ps.println("/>");
	}

	public void outputXML(PrintWriter ps)
	{
		outputXML(ps, 0);
	}
};
