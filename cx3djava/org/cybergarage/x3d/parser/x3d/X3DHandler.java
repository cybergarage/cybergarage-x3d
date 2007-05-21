/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File : X3DHandler.java
*
******************************************************************/

package org.cybergarage.x3d.parser.x3d;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.xml.*;
import org.cybergarage.x3d.parser.Parser;

public class X3DHandler extends DefaultHandler 
{
	private Parser parser;
	
	public X3DHandler(Parser parser) 
	{
		this.parser = parser;
	}

	public Parser getParser()
	{
		return parser;
	};
	
	public void startDocument() throws SAXException 
	{
	}

	public void endDocument() throws SAXException 
	{
	}

	///////////////////////////////////////////////
	//	startElement
	///////////////////////////////////////////////

	private void addXMLElement(XMLNode xmlNode,  String attrName, String attrValue)
	{
		xmlNode.addElement(attrName, attrValue);
	}

	private void addX3DElement(Node x3dNode, String attrName, String attrValue)
	{
		Field field = x3dNode.findField(attrName);
		if (field == null)
			return;

		if (field.isSField() == true) {
			field.setValue(attrValue);
			return;
		}

		MField mfield = (MField)field;
		int mfieldCnt = mfield.getValueCount();

		X3DParserTokenizer attrToken = new X3DParserTokenizer(attrValue);
		int tokenCnt = 0;
		String fieldTokenStr = "";
		while (attrToken.hasMoreTokens() == true) { 
			tokenCnt++;
			String token = attrToken.nextToken();
			if (0 < fieldTokenStr.length())
				fieldTokenStr += " ";
			fieldTokenStr += token;
			if (mfieldCnt <= tokenCnt) {
				mfield.addValue(fieldTokenStr);
				tokenCnt = 0;
				fieldTokenStr = "";
			}
		}
	}

	public void startElement(String uri, String local, String qname, Attributes attributes) throws SAXException
	{
		String elemName = qname;
	
		Node node = NodeType.CreateX3DNode(elemName);

		if (node.isXMLNode() == true)
			node.setName(elemName);

		int len = attributes.getLength();
		for (int index = 0; index < len; index++) {
			String attrName = attributes.getQName(index);
			String attrValue = attributes.getValue(index);
			if (node.isXMLNode() == true) {
				XMLNode xmlNode = (XMLNode)node;
				addXMLElement(xmlNode, attrName, attrValue);
			}
			else 
				addX3DElement(node, attrName, attrValue);
		}

		getParser().addNode(node);
		getParser().pushNode(node);
	}

	///////////////////////////////////////////////
	//	endElement
	///////////////////////////////////////////////

	public void endElement(String uri, String local, String qname) throws SAXException
	{
		getParser().popNode();
	}
	
/*
    public void startElement(String uri, String local, String raw, Attributes attrs) throws SAXException {
        fElements++;
        fTagCharacters++; // open angle bracket
        fTagCharacters += raw.length();
        if (attrs != null) {
            int attrCount = attrs.getLength();
            fAttributes += attrCount;
            for (int i = 0; i < attrCount; i++) {
                fTagCharacters++; // space
                fTagCharacters += attrs.getQName(i).length();
                fTagCharacters++; // '='
                fTagCharacters++; // open quote
                fOtherCharacters += attrs.getValue(i).length();
                fTagCharacters++; // close quote
            }
        }
        fTagCharacters++; // close angle bracket

    }

    public void characters(char ch[], int start, int length)
        throws SAXException {

        fCharacters += length;

    } // characters(char[],int,int);

    public void ignorableWhitespace(char ch[], int start, int length)
        throws SAXException {

        fIgnorableWhitespace += length;

    } // ignorableWhitespace(char[],int,int);

    public void processingInstruction(String target, String data)
        throws SAXException {
        fTagCharacters += 2; // "<?"
        fTagCharacters += target.length();
        if (data != null && data.length() > 0) {
            fTagCharacters++; // space
            fOtherCharacters += data.length();
        }
        fTagCharacters += 2; // "?>"
    } // processingInstruction(String,String)

    //
    // ErrorHandler methods
    //

    public void warning(SAXParseException ex) throws SAXException 
    {
        printError("Warning", ex);
    }

    public void error(SAXParseException ex) throws SAXException 
    {
        printError("Error", ex);
    }

    public void fatalError(SAXParseException ex) throws SAXException 
    {
        printError("Fatal Error", ex);
    }

    //
    // Protected methods
    //

    protected void printError(String type, SAXParseException ex) {

        System.err.print("[");
        System.err.print(type);
        System.err.print("] ");
        if (ex== null) {
            System.out.println("!!!");
        }
        String systemId = ex.getSystemId();
        if (systemId != null) {
            int index = systemId.lastIndexOf('/');
            if (index != -1)
                systemId = systemId.substring(index + 1);
            System.err.print(systemId);
        }
        System.err.print(':');
        System.err.print(ex.getLineNumber());
        System.err.print(':');
        System.err.print(ex.getColumnNumber());
        System.err.print(": ");
        System.err.print(ex.getMessage());
        System.err.println();
        System.err.flush();

    }
*/
} // class X3DHandler
