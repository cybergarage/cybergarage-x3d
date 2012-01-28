/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : InvalidEventOutException.java
*
******************************************************************/

package org.cybergarage.x3d;

public class InvalidEventOutException extends IllegalArgumentException {
	public InvalidEventOutException(){
		super();
	}
	public InvalidEventOutException(String s){
		super(s);
	}
}
