/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1999
*
*	File : ParserDXFException.java
*
******************************************************************/

package org.cybergarage.x3d.parser.autodesk;

public class ParserDXFException extends Exception {

	private String message;
	
	public ParserDXFException() {
		message = null;
	}

	public ParserDXFException(String msg) {
		message = msg;
	}

	public ParserDXFException(int line, String unknownToken) {
		message =  "Encountered \"" + unknownToken + "\" at line " + line;
	}

	public String getMessage() {
		if (message == null)
			return message;
		return super.getMessage();
	}
}
