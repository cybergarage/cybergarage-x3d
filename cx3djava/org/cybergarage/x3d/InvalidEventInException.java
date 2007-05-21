/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : InvalidEventInException.java
*
******************************************************************/

package org.cybergarage.x3d;

public class InvalidEventInException extends IllegalArgumentException {
	/**
	 * Constructs an InvalidEventInException with no detail message.
	 */
	public InvalidEventInException(){
		super();
	}
	
	/**
	 * Constructs an InvalidEventInException with the specified detail message.
	 * A detail message is a String that describes this particular exception.
	 * @param s the detail message
	 */
	public InvalidEventInException(String s) {
		super(s);
	}
}