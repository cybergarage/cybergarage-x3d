/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File : ParserStackNode.java
*
******************************************************************/

package org.cybergarage.x3d.parser;

import java.io.*;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.*;

public abstract class Parser extends Scene
{
	
	public Parser()
	{
	}

	///////////////////////////////////////////////
	//	ParserResult
	///////////////////////////////////////////////

	private ParserResult parserResult = new ParserResult();
	
	public void setResult(ParserResult result)
	{
		parserResult = result;
	}
	
	public ParserResult getResult()
	{
		return parserResult;
	}

	public void initResult()
	{
		setParseResult(false);
		setErrorMessage("");
	}

	public void setParseResult(boolean flag) {
		parserResult.setResult(flag);
	}

	public boolean getParseResult() {
		return parserResult.getResult();
	}

	public void setErrorMessage(String msg) {
		 parserResult.setErrorMessage(msg);
	}
	
	public String getErrorMessage() {
		return parserResult.getErrorMessage();
	}

	///////////////////////////////////////////////
	//	Input Source
	///////////////////////////////////////////////

	abstract public boolean parse(Reader reader);
		
	///////////////////////////////////////////////
	//	Praser action
	///////////////////////////////////////////////

	public LinkedList mNodeStackList = new LinkedList();

	public void pushNode(Node node)
	{
		ParserStackNode parserNode = new ParserStackNode(node);
		mNodeStackList.addNode(parserNode);
	}

	public void pushNode(Node node, int type)
	{
		ParserStackNode parserNode = new ParserStackNode(node, type);
		mNodeStackList.addNode(parserNode);
	}

	public Node popNode()
	{
		ParserStackNode lastNode = (ParserStackNode)mNodeStackList.getLastNode(); 
		lastNode.remove();
		return lastNode.getObject();
	}

	public Node getCurrentNode() {
		ParserStackNode lastNode = (ParserStackNode)mNodeStackList.getLastNode(); 
		if (lastNode == null)
			return null;
		else
			return lastNode.getObject();
	}

	public int getCurrentType() {
		ParserStackNode lastNode = (ParserStackNode)mNodeStackList.getLastNode(); 
		if (lastNode == null)
			return 0;
		else
			return lastNode.getType();
	}

	///////////////////////////////////////////////
	//	Praser action
	///////////////////////////////////////////////
	
	public void addNode(Node node) {
		Node parentNode = getCurrentNode();
		if (parentNode == null)
			super.addNode(node);
		else
			parentNode.addChildNode(node);

		node.setParentNode(parentNode);
	}

	///////////////////////////////////////////////
	//	Instance Node
	///////////////////////////////////////////////
	
	public void setCurrentNodeAsInstance(String nodeName)
	{
		Node currNode = getCurrentNode();
		
		if (currNode != null)
			currNode.remove();
		
		popNode();
				
		Node defNode = findNode(nodeName);

		Node instanceNode = null;
		if (defNode != null) {
			instanceNode = defNode.createInstanceNode();
			addNode(instanceNode);
		}
				
		pushNode(instanceNode);
	}

	///////////////////////////////////////////////
	//	DEF
	///////////////////////////////////////////////

	public String mDefName = null;
	
	public void setDefName(String name) {
		mDefName = name;
	}

	public String getDefName() {
		String defName = mDefName;
		mDefName = null;
		return defName;
	}
	
	///////////////////////////////////////////////
	//	ROUTE INFO
	///////////////////////////////////////////////
/*
	public RouteInfo routeInfo = null;

	public void setRouteInfo(RouteInfo info)
	{
		routeInfo = info;
	}
	
	public RouteInfo getRouteInfo()
	{
		return routeInfo;
	}
*/
}

