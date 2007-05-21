/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : TextNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import java.awt.*;

import javax.media.j3d.*;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.Debug;

public class TextNodeObject extends Text3D implements NodeObject {
	
	public TextNodeObject(TextNode node) {
		setCapability(ALLOW_STRING_READ);
		setCapability(ALLOW_STRING_WRITE); 
		setCapability(ALLOW_INTERSECT);
		initialize(node);
	}
	
	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		
		Debug.message("TextNodeObject::initialize = " + node);
		
		TextNode textNode = (TextNode)node;
		
		String	fontName 	= "SansSerif";
		float		fontSize		= 1.0f;
		int		fontStyle	= Font.PLAIN;
		
		// Set a font style
		FontStyleNode fontStyleNode = textNode.getFontStyleNodes();
		if (fontStyleNode != null) {
			if (1 <= fontStyleNode.getNFamilies()) { 
				String family = fontStyleNode.getFamily(0);
				if (family.equals("SERIF") == true)
					fontName = "SansSerif";
				else if (family.equals("SANS") == true)
					fontName = "Helvetica";
				else if (family.equals("TYPEWRITER") == true)
					fontName = "Courier";
			}

			fontSize = fontStyleNode.getSize();
			
			String style = fontStyleNode.getStyle();
			if (style.equals("PLAIN") == true)
				fontStyle = Font.PLAIN;
			else if (style.equals("BOLD") == true)
				fontStyle = Font.BOLD;
			else if (style.equals("ITALIC") == true)
				fontStyle = Font.ITALIC;
			else if (style.equals("BOLDITALIC") == true)
				fontStyle = Font.BOLD | Font.ITALIC;
				
			//setCharacterSpacing(fontStyleNode.getSpacing());
			
			if (1 <= fontStyleNode.getNJustifies()) {
				String justify = fontStyleNode.getJustify(0);
				if (justify != null) {
					if (justify.equals("MIDDLE") == true)
						setAlignment(ALIGN_CENTER);
					if (justify.equals("FIRST") == true)
						setAlignment(ALIGN_FIRST);
					if (justify.equals("BEGIN") == true)
						setAlignment(ALIGN_FIRST);
					if (justify.equals("END") == true)
						setAlignment(ALIGN_LAST);
				}
			}
		}
		
		setFont3D(new Font3D(new Font(fontName, fontStyle, (int)fontSize), new FontExtrusion()));
		
		// Set a font text
		update(node);
					
		return true;
	}
	
	public boolean uninitialize(org.cybergarage.x3d.node.Node node) {
		return true;
	}
			
	public boolean update(org.cybergarage.x3d.node.Node node) {
		TextNode textNode = (TextNode)node;
		
		String textString = null;
		
		int nStrings = textNode.getNStrings();
		if (1 <= nStrings) 
			textString = textNode.getString(0);
		
		String textNodeString = getString();
		
		if (textString == null && textNodeString == null)
			return true;
		
		if (textString != null) {
			if (textString.equals(textNodeString) == false) 
				setString(textString);
		}			
		else 
			setString("");
			
		return true;
	}
	
	public boolean add(org.cybergarage.x3d.node.Node node) {

		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setGeometry(this);
				}
			}
		}
		
		return true;
	}

	public boolean remove(org.cybergarage.x3d.node.Node node) {
	
		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setGeometry(null);
				}
			}
		}
		
		return true;
	}
}
