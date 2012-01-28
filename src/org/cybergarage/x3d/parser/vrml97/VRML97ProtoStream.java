/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : VRML97ProtoStream.java
*
******************************************************************/

package org.cybergarage.x3d.parser.vrml97;

import java.io.*;

public class VRML97ProtoStream extends InputStream {

	public VRML97ProtoStream() {
		rewind();
	}
	
	////////////////////////////////////////////////
	//	Token Buffer
	////////////////////////////////////////////////

	protected StringBuffer	mTokenBuffer = new StringBuffer();

	public void addToken(String token) {
		mTokenBuffer.append(token);
		if (token.compareTo("\n") != 0)
			mTokenBuffer.append(' ');
	}

	public void addToken(double token) {
		mTokenBuffer.append(token);
		mTokenBuffer.append(' ');
	}

	public String getTokenBuffer() {
		return mTokenBuffer.toString();
	}

	public int getTokenBufferLength() {
		return mTokenBuffer.toString().length();
	}
		
	////////////////////////////////////////////////
	//	InpuStream methods
	////////////////////////////////////////////////
	
	private int	mPos;
	
	public void setTokenBufferPos(int pos) {
		mPos = pos;
	}
	
	public int getTokenBufferPos() {
		return mPos;
	}

	public void rewind() {
		setTokenBufferPos(0);
	}
		
	////////////////////////////////////////////////
	//	InpuStream methods
	////////////////////////////////////////////////
	
	public int read() throws IOException {
		int pos = getTokenBufferPos();
		int c = -1;
		if (pos < getTokenBufferLength()) {
			c = getTokenBuffer().charAt(pos);
			setTokenBufferPos(pos+1);
		}
		return c;
	}

	public int available() throws IOException {
		return getTokenBufferLength() - getTokenBufferPos();
	}

	public void close() throws IOException {
		rewind();
	}
}
