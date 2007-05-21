/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : PixelTextureNodeObject.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import java.awt.image.*;
import java.awt.geom.*;

import javax.media.j3d.*;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.Debug;

public class PixelTextureNodeObject extends Texture2D implements NodeObject {
	
	public PixelTextureNodeObject(PixelTextureNode node) {
		super(BASE_LEVEL, getComponentType(node), getClosestPowerOf2(getWidth(node)), getClosestPowerOf2(getHeight(node)));
		
		setCapability(ALLOW_IMAGE_READ);
		setCapability(ALLOW_ENABLE_READ);
		setCapability(ALLOW_ENABLE_WRITE);
		
		setMinFilter(BASE_LEVEL_LINEAR);
		setMagFilter(BASE_LEVEL_LINEAR);
		
		initialize(node);
	}

	private static int getWidth(PixelTextureNode node) {
		if (node.getNImages() < 3)
			return 0;
		return node.getImage(0);
	}

	private static int getHeight(PixelTextureNode node) {
		if (node.getNImages() < 3)
			return 0;
		return node.getImage(1);
	}

	private static int getComponentType(PixelTextureNode node) {
		if (node.getNImages() < 3)
			return RGB;
		int numComponents = node.getImage(2);
		if (numComponents == 4)
			return RGBA;
		return RGB;
	}

	private static int getClosestPowerOf2(int value) {
		if (value < 1)
		    return value;
	
		int powerValue = 1;
		for (;;) {
	   	 powerValue *= 2;
	   	 if (value < powerValue) {
				int minBound = powerValue/2;
				if ((powerValue - value) > (value - minBound))
					return minBound;
				else
					return powerValue;
			}
		}
	}

	public boolean initialize(org.cybergarage.x3d.node.Node node) {
		Debug.message("PixelTextureNodeObject.initialize");		
		
		PixelTextureNode texNode = (PixelTextureNode)node;
		
		BufferedImage bufImage = createBufferedImage(texNode);

		if (bufImage == null) {
			setEnable(false);
			return false;
		}
					
		int width = getWidth(texNode);
		int height = getHeight(texNode);

		Debug.message("\tj3d width  = " + getClosestPowerOf2(width));		
		Debug.message("\tj3d height = " + getClosestPowerOf2(height));		
		
		float xScale = (float)getClosestPowerOf2(width) / (float)width;
		float yScale = (float)getClosestPowerOf2(height) / (float)height;
		
		bufImage = getScaledImage(bufImage, xScale, yScale);
		
		ImageComponent2D imgComp = null;

		int compType = getComponentType(texNode);
		switch (compType) {
		case RGB:
			imgComp = new ImageComponent2D(ImageComponent.FORMAT_RGB, bufImage);
			break;
		case RGBA:
			imgComp = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bufImage);
			break;
		}
		
		if (imgComp == null) {
			setEnable(false);
			return false;
		}
		
		imgComp.setCapability(ImageComponent.ALLOW_FORMAT_READ);
		imgComp.setCapability(ImageComponent.ALLOW_IMAGE_READ);
		imgComp.setCapability(ImageComponent.ALLOW_SIZE_READ);
		
		setImage(0, imgComp);
		setEnable(true);
		
		return true;
	}

	public int RGBA2ARGB(int rgba) {
		int argb = 0;
		argb |= ((rgba & 0xFFFFFF00) >>  8) & 0x00FFFFFF;
		argb |= ((rgba & 0x000000FF) << 24) & 0xFF000000;
		return argb;
	}
	
	public BufferedImage createBufferedImage(PixelTextureNode node) {
		BufferedImage bufImage = null;
		
		int width = getWidth(node);
		int height = getHeight(node);

		Debug.message("\twidth  = " + width);		
		Debug.message("\theight = " + height);		
		
		int compType = getComponentType(node);
		switch (compType) {
		case RGB:
			{
				bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				for (int x=0; x<width; x++) {
					for (int y=0; y<height; y++) {
						//Debug.message("(x, y) = (" + x + ", " + ((height-1) - y) + ")");
						bufImage.setRGB(x, ((height-1) - y), node.getImage((x + y*width) + 3));
					}
				}
			}
			break;
		case RGBA:
			{
				bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				for (int x=0; x<width; x++) {
					for (int y=0; y<height; y++) {
						//Debug.message("(x, y) = (" + x + ", " + ((height-1) - y) + ")");
						bufImage.setRGB(x, ((height-1) - y), RGBA2ARGB(node.getImage((x + y*width) + 3)) );
					}
				}
			}
			break;
		}
				
		return bufImage;
	}

	private BufferedImage getScaledImage(BufferedImage origImage, float xScale, float yScale) {
		if (xScale == 1.0f && yScale == 1.0f)
			return origImage;
        
		AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
		AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage scaledImage  = atop.filter(origImage, null);
		return scaledImage;
	}
	
	public boolean uninitialize(org.cybergarage.x3d.node.Node node) {
		return true;
	}
			
	public boolean update(org.cybergarage.x3d.node.Node node) {
		return true;
	}
	
	public boolean add(org.cybergarage.x3d.node.Node node) {

		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isAppearanceNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Appearance parentAppearanceNode = (Appearance)parentNodeObject;
					parentAppearanceNode.setTexture(this);
				}
			}
		}
		
		return true;
	}

	public boolean remove(org.cybergarage.x3d.node.Node node) {
	
		org.cybergarage.x3d.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isAppearanceNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Appearance parentAppearanceNode = (Appearance)parentNodeObject;
					parentAppearanceNode.setTexture(null);
				}
			}
		}
		
		return true;
	}
}
