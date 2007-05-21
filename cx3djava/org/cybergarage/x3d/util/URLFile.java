/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File : URLFile.java
*
******************************************************************/

package org.cybergarage.x3d.util;

import java.net.*;
import java.io.*;

public class URLFile {

	private String mDownloadedFilename;
	private String mURL;

	public URLFile() {
		setDownloadedFilename(null);
		setURL(null);
	}

	public URLFile(String urlString) {
		setDownloadedFilename(null);
		setURL(urlString);
	}

	public void finalize() {
		String filename = getDownloadedFilename();
		if (filename != null) {
			File file = new File(filename);
			file.delete();
		}
	}
	
	public void setURL(String url) {
		mURL = url;
	}
	
	public String getURL() {
		return mURL;
	}

	public String getHost() {
		String url = getURL();
		if (url == null)
			return null;
		int index = url.lastIndexOf('/');
		if (index <= 0)
			return null;
		String hostString = new String(url.toCharArray(), 0, index+1);
		return hostString;
	}

	public void setDownloadedFilename(String filename) {
		mDownloadedFilename = filename;
	}
	
	public String getDownloadedFilename() {
		return mDownloadedFilename;
	}
		
	public boolean download() {
		
		URL	url = null;

		try {
			url = new URL(getURL());
		}			
		catch (MalformedURLException e) {
			return false;
		}
				
		DataInputStream inputStream = null;
		try {
			inputStream = new DataInputStream(url.openStream());
		}
		catch (IOException e) {
			return false;
		}

		File				outputFile		= null;
		FileOutputStream	outputStream	= null;
		try {
			outputFile = File.createTempFile("tmp", "tmp");
			outputStream = new FileOutputStream(outputFile);
		}
		catch (Exception e) {
			return false;
		}
		
		try {
			while (true) {
				outputStream.write(inputStream.readUnsignedByte());
			}
		}
		catch (IOException e) {
		}

		try {
			inputStream.close();
			outputStream.close();
		}
		catch (IOException e) {
			return false;
		}
		
		setDownloadedFilename(outputFile.getAbsolutePath());
		
		return true;
	}

	public static void main(String a[]) {
		if (0 < a.length) {
			URLFile url = new URLFile(a[0]);
			if (url.download() == true) {
				System.out.println("Dowload is OK : " + a[0] + " -> " + url.getDownloadedFilename());
				System.out.println("              : " + url.getHost());
			}
			else {	
				System.out.println("Dowload is failed : " + a[0]);
				System.out.println("              : " + url.getHost());
			}
		}
	}
}