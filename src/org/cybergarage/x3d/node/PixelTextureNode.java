/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : PixelTextureNode.java
*
*	Revisions:
*
*	12/05/02
*		- Changed the super class from TextureNode to Texture2DNode.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class PixelTextureNode extends Texture2DNode {
	
	//// Exposed Field ////////////////
	private final static String imageExposedFieldName	= "image";

	private SFImage imageField;

	public PixelTextureNode()
	{
		setHeaderFlag(false);
		setType(NodeType.PIXELTEXTURE);

		///////////////////////////
		// Exposed Field 
		///////////////////////////

		// image field
		imageField = new SFImage();
		addExposedField(imageExposedFieldName, imageField);
	}

	public PixelTextureNode(PixelTextureNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	// Image
	////////////////////////////////////////////////

	public SFImage getImageField() {
		if (isInstanceNode() == false)
			return imageField;
		return (SFImage)getExposedField(imageExposedFieldName);
	}

	public void addImage(int value) {
		getImageField().addValue(value);
	}

	public void setImages(String value) {
		getImageField().addValue(value);
	}

	public int getNImages() {
		return getImageField().getSize();
	}
	
	public int getImage(int index) {
		return getImageField().get1Value(index);
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
	//	Imagemation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool repeatS = getRepeatSField();
		SFBool repeatT = getRepeatTField();
		SFImage image = getImageField();
		
		int imgSize = image.getSize();
		if (3 < imgSize) { 
			int width		= image.get1Value(0);
			int height		= image.get1Value(1);
			int compSize	= image.get1Value(2);
			
			printStream.println(indentString + "\t" + "image " + width + " " + height + " " + compSize);

			int linePixels = 0;
			for (int n=3; n<imgSize; n++) {
				
				if (linePixels == 0)
					printStream.print(indentString + "\t\t");

				printStream.print("0x" + Integer.toHexString(image.get1Value(n)) + " ");

				linePixels++;
				
				if (16 < linePixels || n == (imgSize-1)) {
					printStream.println("");
					linePixels = 0;
				}
			}
		}
		
		printStream.println(indentString + "\t" + "repeatS " + repeatS );
		printStream.println(indentString + "\t" + "repeatT " + repeatT );
	}
}
