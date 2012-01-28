/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ImageTextureLoader.java
*
******************************************************************/

package org.cybergarage.x3d.j3d;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

import java.net.*;

import javax.media.j3d.*;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.Debug;

public class ImageTextureLoader extends Object {
	
	private ImageComponent2D mImageComponent = null;

	public ImageTextureLoader(ImageTextureNode imgTex, Component comp) {
		loadImageComponent(imgTex, comp);
	}

	public boolean hasComponent() {
		return (mImageComponent != null) ? true : false;
	}
	
	private Image getImage(Component comp, String filename) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Image image = toolkit.getImage(filename);
    
		MediaTracker mt = new MediaTracker(comp);
		mt.addImage (image, 0);
		try { mt.waitForAll(); }
		catch (InterruptedException e) {
			Debug.warning("ImageTextureLoader::getImage = " + e.getMessage());
			return null;
		}
		if (mt.isErrorAny()) {
			return null;
		}
		
		return image;
	}

	private Image getImage(Component comp, URL urlFilename) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Image image = toolkit.getImage(urlFilename);
    
		MediaTracker mt = new MediaTracker(comp);
		mt.addImage (image, 0);
		try { mt.waitForAll(); }
		catch (InterruptedException e) {
			Debug.warning("ImageTextureLoader::getImage = " + e.getMessage());
			return null;
		}
		if (mt.isErrorAny()) {
			return null;
		}
		
		return image;
	}
	
	private void loadImageComponent(ImageTextureNode imgTex, Component comp) {
			
		String url = imgTex.getURL(0);
		
		Image image = null;
		//Image image = getImage(comp, url);
		
		Debug.message("Image URL = " + url);
		
		if (image == null) {
			SceneGraph sg = imgTex.getSceneGraph();
			if (sg != null) {
				URL baseURL = sg.getBaseURL();
				Debug.message("Base URL = " + baseURL);
				if (baseURL != null) {
					try {
						URL imgURL = new URL(baseURL.toString() + url);
						Debug.message("Loading Texture (" + imgURL + ") .....");
						image = getImage(comp, imgURL);
					} catch (MalformedURLException mue) {}
				}
			}
		}
		
		if (image == null) {
			mImageComponent = null;
			org.cybergarage.x3d.util.Debug.warning("Texture (" + url + ") is not found");
			return;
		}
			
		int width = image.getWidth(comp);
		int height = image.getHeight(comp);
    
		// Create imageComponent from image
		mImageComponent = createImageComponent(createBufferedImage(image, comp));
	}

	private ImageComponent2D createImageComponent(BufferedImage bufferedImage) {
		ImageComponent2D imgComp = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bufferedImage);
		imgComp.setCapability(ImageComponent.ALLOW_FORMAT_READ);
		imgComp.setCapability(ImageComponent.ALLOW_IMAGE_READ);
		imgComp.setCapability(ImageComponent.ALLOW_SIZE_READ);
		return imgComp;
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

	private BufferedImage getScaledImage(BufferedImage origImage, float xScale, float yScale) {
		if (xScale == 1.0f && yScale == 1.0f)
			return origImage;
        
		AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
		AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage scaledImage  = atop.filter(origImage, null);
		return scaledImage;
	}

	private BufferedImage createBufferedImage(Image image, Component observer) {
		int status;
		observer.prepareImage(image, null);
		while(true) {
			status = observer.checkImage(image, null);
			if ((status & ImageObserver.ERROR) != 0) {
				Debug.warning("Couldn't load a image in ImageTextureLoader::createBufferedImage");
				return null;
			} else if ((status & ImageObserver.ALLBITS) != 0) {
				break;
			} 
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}

		int width = image.getWidth(observer);
		int height = image.getHeight(observer);

		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		int[] intPixels = ((DataBufferInt)bImage.getRaster().getDataBuffer()).getData();

		// retrieve image data using PixelGrabber
		PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, intPixels, 0, width);
		try {
			pg.grabPixels();
		}
		catch (InterruptedException e) {}

		float xScale = (float)getClosestPowerOf2(width) / (float)width;
		float yScale = (float)getClosestPowerOf2(height) / (float)height;
		bImage = getScaledImage(bImage, xScale, yScale);		
		
		return bImage;
	}


	public ImageComponent2D getImageComponent() {
		return mImageComponent;
	}
		
	public int getWidth() {
		if (mImageComponent == null)
			return 0;
		return mImageComponent.getWidth();
	}
	
	public int getHeight() {
		if (mImageComponent == null)
			return 0;
		return mImageComponent.getHeight();
	}
}
