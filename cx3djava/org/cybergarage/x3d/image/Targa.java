/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File:	Targa.java
*
******************************************************************/

package org.cybergarage.x3d.image;

import java.io.*;
import java.net.*;
import java.awt.image.*;
import java.util.zip.*;

public final class Targa {

	public static final int	FORMAT_RGB_16	= 16;
	public static final int	FORMAT_RGB_24	= 24;
	public static final int	FORMAT_RGB_32	= 32;
	
	public Targa() {
		setHeader(new Header());
		setBufferedImage(null);
	}
	
	public Targa(BufferedImage bufImage) {
		setHeader(new Header());
		setBufferedImage(bufImage);
	}

	////////////////////////////////////////////////
	//	BufferedImage
	////////////////////////////////////////////////
	
	private BufferedImage	mBufferedImage;
	
	public void setBufferedImage(BufferedImage bufImage) {
		mBufferedImage = bufImage;

		Header header = getHeader();

		if (bufImage == null) {
			header.setPixelSize(0);
			header.setWidth(0);
			header.setHeight(0);
			return;		
		}
		
		header.setPixelSize(0);
		if (bufImage.getType() == BufferedImage.TYPE_INT_ARGB)
			header.setPixelSize(32);
		if (bufImage.getType() == BufferedImage.TYPE_INT_RGB)
			header.setPixelSize(24);
		
		header.setWidth(bufImage.getWidth());
		header.setHeight(bufImage.getHeight());
	}

	public BufferedImage getBufferedImage() {
		return mBufferedImage;
	}

	public void printBufferedImageInfo() {
		System.out.println("==== BufferedImage Infomation ====");
		System.out.println("    mBufferedImage = " + mBufferedImage);
	}
	
	////////////////////////////////////////////////
	//	Header
	////////////////////////////////////////////////

	private Header	mHeader;
	
	private void setHeader(Header bufImage) {
		mHeader = bufImage;
	}

	public Header getHeader() {
		return mHeader;
	}

	public class Header {
		public byte		idLength;
		public byte		coMapType;
		public byte		imgType;
		public short	index;	
		public short	length;	
		public byte		coSize;	
		public short	xOrg;	
		public short	yOrg;	
		public short	width;	
		public short	height;	
		public byte		pixelSize;
		public byte		attBits;
		
		public Header() {
			idLength		= 0;
			coMapType	= 0;
			imgType		= 2;
			index			= 0;	
			length		= 0;	
			coSize		= 0;	
			xOrg			= 0;	
			yOrg			= 0;	
			width			= 0;	
			height		= 0;	
			pixelSize	= 0;
			attBits		= 0;
		}

		public Header(int width, int height) {
			this();
			setWidth(width);
			setHeight(height);
		}
		
		public void setWidth(int width) {
			this.width = (short)width;
		}

		public int getWidth() {
			return this.width;
		}

		public void setHeight(int height) {
			this.height = (short)height;
		}

		public int getHeight() {
			return this.height;
		}

		public void setPixelSize(int pixelSize) {
			this.pixelSize = (byte)pixelSize;
		}

		public int getPixelSize() {
			return this.pixelSize;
		}
		
		public void print() {
			System.out.println("==== TARGA Header Infomation ====");
			System.out.println("    idLength  = " + idLength);
			System.out.println("    coMapType = " + coMapType);
			System.out.println("    index     = " + index);
			System.out.println("    length    = " + length);
			System.out.println("    coSize    = " + coSize);
			System.out.println("    xOrg      = " + xOrg);
			System.out.println("    yOrg      = " + yOrg);
			System.out.println("    width     = " + width);
			System.out.println("    height    = " + height);
			System.out.println("    pixelSize = " + pixelSize);
			System.out.println("    attBits   = " + attBits);
		}
	}

	////////////////////////////////////////////////
	//	Load
	////////////////////////////////////////////////

	public boolean isGNUZipped(URL url) {
		boolean isZipped = false;
		try {
			InputStream	in	= url.openStream();
		
			byte header[] = new byte[2];
			header[0] = readByte(in);
			header[1] = readByte(in);
			
			if (header[0] == 31 /*0x1F*/ && header[1] == -117 /*0x8B*/)
				isZipped = true;
				
			in.close();
		}
		catch (IOException ioe) {} 
		
		return isZipped;
	}

	public boolean isZipped(URL url) {
		boolean isZipped = false;
		try {
			InputStream	in	= url.openStream();
		
			byte header[] = new byte[2];
			header[0] = readByte(in);
			header[1] = readByte(in);
			
			if (header[0] == 'P' && header[1] == 'K')
				isZipped = true;
				
			in.close();
		}
		catch (IOException ioe) {} 
		
		return isZipped;
	}

	public boolean load(URL url) {
		boolean isZipped = isGNUZipped(url);
		
		System.out.println("isZipped = " + isZipped);
		
		try {
			InputStream	in	= url.openStream();
			if (isZipped == true)
				in = new GZIPInputStream(in);
						
			Header header = getHeader();
			
			readHeader(in, header);
			
			int width		= header.getWidth();
			int height		= header.getHeight();
			int pixelSize	= header.getPixelSize();

			if (width <= 0 || height <= 0) {
				in.close();
				return false;
			}
			
			BufferedImage bufImage = null;
			if (pixelSize == 24)
				bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			if (pixelSize == 16 || pixelSize == 32)
				bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
			if (bufImage == null) {
				in.close();
				return false;
			}
			
			switch (pixelSize) {
			case 16:
				readPixel16(in, bufImage, width, height); break;
			case 24:
				readPixel24(in, bufImage, width, height); break;
			case 32:
				readPixel32(in, bufImage, width, height); break;
			}
			
			setBufferedImage(bufImage);
			
			in.close();
		}
		catch (IOException ioe) {
			return false;
		} 
		
		return true;
	}

	private void readPixel16(InputStream in, BufferedImage bufImage, int width, int height) throws IOException {
		int		color;
		short		color16;
		byte		r, g, b;
		boolean	useAlpha;
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {

				color16 = readShort(in);
			
				b = (byte)( (float)((color16 & 0x7C00) >> 10) / 31.0f * 255.0f);				
				g = (byte)( (float)((color16 & 0x03E0) >>  5) / 31.0f * 255.0f);				
				r = (byte)( (float)((color16 & 0x001F) >>  0) / 31.0f * 255.0f);	
				useAlpha = ((color16 & 0x8000) == 0) ? true : false;

				color = 0;
				color |= (r << 16) & 0x00FF0000;				
				color |= (g <<  8) & 0x0000FF00;				
				color |= (b <<  0) & 0x000000FF;	
				if (useAlpha == false)
					color |= 0xFF000000;	
				
				bufImage.setRGB(x, y, color);			
			}
		}
	}

	private void readPixel24(InputStream in, BufferedImage bufImage, int width, int height) throws IOException {
		int color;
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				color = 0;
				color |= (readByte(in) << 16) & 0x00FF0000;				
				color |= (readByte(in) <<  8) & 0x0000FF00;				
				color |= (readByte(in) <<  0) & 0x000000FF;	
				bufImage.setRGB(x, y, color);			
			}
		}
	}

	private void readPixel32(InputStream in, BufferedImage bufImage, int width, int height) throws IOException {
		int color;
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				color = 0;
				color |= (readByte(in) << 16) & 0x00FF0000;				
				color |= (readByte(in) <<  8) & 0x0000FF00;				
				color |= (readByte(in) <<  0) & 0x000000FF;	
				color |= (readByte(in) << 24) & 0xFF000000;	
				bufImage.setRGB(x, y, color);			
			}
		}
	}
	
	private byte readByte(InputStream in) throws IOException {
		return (byte)in.read();
	}

	private short readShort(InputStream in) throws IOException {
		//return (short)( ((in.read() << 8) & 0xFF00) | (in.read() & 0x00FF) );
		return (short)( (in.read() & 0x00FF) | ((in.read() << 8) & 0xFF00) );
	}

	private void readHeader(InputStream in, Header header) throws IOException {
		header.idLength	= readByte(in);
		header.coMapType	= readByte(in);
		header.imgType		= readByte(in);
		header.index		= readShort(in);
		header.length		= readShort(in);
		header.coSize		= readByte(in);
		header.xOrg			= readShort(in);
		header.yOrg			= readShort(in);
		header.width		= readShort(in);
		header.height		= readShort(in);
		header.pixelSize	= readByte(in);
		header.attBits		= readByte(in);
	}

	public boolean load(File file) {
		try {
			return load(file.toURL());
		}
		catch (MalformedURLException mURLe) {}
		return false;
	}

	public boolean load(String filename) {
		return load(new File(filename));
	}
	
	////////////////////////////////////////////////
	//	Save
	////////////////////////////////////////////////

	public boolean checkHeader() {
		BufferedImage bufImage = getBufferedImage();
		
		if (bufImage == null)
			return false;

		Header header = getHeader();
			
		int width		= header.getWidth();
		int height		= header.getHeight();
		if (width <= 0 || height <= 0) 
			return false;
			
		return true;
	}
	
	public boolean save(OutputStream out, int formatType) {
		
		BufferedImage	bufImage	= getBufferedImage();
		Header			header	= getHeader();
		
		int	width		= header.getWidth();
		int	height	= header.getHeight();

		if (formatType != 16 && formatType != 24 && formatType != 32)
			return false;

		header.setPixelSize(formatType);
		
		try {
			writeHeader(out, header);
			
			switch (formatType) {
			case 16:
				writePixel16(out, bufImage, width, height); break;
			case 24:
				writePixel24(out, bufImage, width, height); break;
			case 32:
				writePixel32(out, bufImage, width, height); break;
			}
			
			out.flush();
		}
		catch (IOException ioe) {
			return false;
		}
		
		
		return true;
	}

	public boolean save(String filename, int formatType, boolean doCompress) {

		if (checkHeader() == false) 
			return false;
					
		try {
			OutputStream out = new FileOutputStream(filename);
			if (doCompress == true)
				out = new GZIPOutputStream(out);
			
			save(out, formatType);
						
			out.close();
		}
		catch (FileNotFoundException fnfe) {
			return false;
		}
		catch (IOException ioe) {
			return false;
		}
		
		return true;
	}

	public boolean save(String filename, int formatType) {
		return save(filename, formatType, false);
	}
	
	private void writePixel16(OutputStream out, BufferedImage bufImage, int width, int height) throws IOException {
		int 		color;
		byte		r, g, b;
		boolean	useAlpha;
		short		argb;
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				color = bufImage.getRGB(x, y);		
				r = (byte)( (float)((color & 0x00FF0000) >> 16) / 255.0f * 31.0f);				
				g = (byte)( (float)((color & 0x0000FF00) >>  8) / 255.0f * 31.0f);				
				b = (byte)( (float)((color & 0x000000FF) >>  0) / 255.0f * 31.0f);	
				useAlpha = (((color & 0xFF000000) >> 24) == 0) ? true : false;
				argb = 0;
				argb |= (b << 10) & 0x7C00;
				argb |= (g <<  5) & 0x03E0;
				argb |= (r <<  0) & 0x001F;
				argb |= (useAlpha == true) ? 0x0000 : 0x8000;
				writeShort(out, argb);
			}
		}
	}

	private void writePixel24(OutputStream out, BufferedImage bufImage, int width, int height) throws IOException {
		int color;
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				color = bufImage.getRGB(x, y);			
				writeByte(out, (byte)((color & 0x00FF0000) >> 16));				
				writeByte(out, (byte)((color & 0x0000FF00) >>  8));				
				writeByte(out, (byte)((color & 0x000000FF) >>  0));	
			}
		}
	}

	private void writePixel32(OutputStream out, BufferedImage bufImage, int width, int height) throws IOException {
		int color;
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				color = bufImage.getRGB(x, y);			
				writeByte(out, (byte)((color & 0x00FF0000) >> 16));				
				writeByte(out, (byte)((color & 0x0000FF00) >>  8));				
				writeByte(out, (byte)((color & 0x000000FF) >>  0));	
				writeByte(out, (byte)((color & 0xFF000000) >> 24));				
			}
		}
	}

	private void writeByte(OutputStream out, byte data) throws IOException {
		out.write(data);
	}

	private void writeShort(OutputStream out, short data) throws IOException {
		out.write((byte)(data & 0x00FF));
		out.write((byte)((data & 0xFF00) >> 8));
	}

	private void writeHeader(OutputStream out, Header header) throws IOException {
		writeByte(out, header.idLength);
		writeByte(out, header.coMapType);
		writeByte(out, header.imgType);
		writeShort(out, header.index);
		writeShort(out, header.length);
		writeByte(out, header.coSize);
		writeShort(out, header.xOrg);
		writeShort(out, header.yOrg);
		writeShort(out, header.width);
		writeShort(out, header.height);
		writeByte(out, header.pixelSize);
		writeByte(out, header.attBits);
	}

	public static void main(String args[]) {
		boolean ret;
		
		Targa inTarga = new Targa();
		ret = inTarga.load("wood.tga");
		inTarga.getHeader().print();
		inTarga.printBufferedImageInfo();
		System.out.println("Loading ..... " + ret);
		
		Targa outTarga = new Targa();
		outTarga.setBufferedImage(inTarga.getBufferedImage());
		outTarga.getHeader().print();
		ret = outTarga.save("output.gz", Targa.FORMAT_RGB_32, true);
		System.out.println("Saving ..... " + ret);

		Targa inTarga2 = new Targa();
		ret = inTarga2.load("output.gz");
		inTarga2.getHeader().print();
		inTarga2.printBufferedImageInfo();
		System.out.println("Loading ..... " + ret);
		
		Targa outTarga2 = new Targa();
		outTarga2.setBufferedImage(inTarga2.getBufferedImage());
		outTarga2.getHeader().print();
		ret = outTarga2.save("output.tga", Targa.FORMAT_RGB_24);
		System.out.println("Saving ..... " + ret);
	}

}