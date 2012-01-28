/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : InvalidPrivateFieldException.java
*
******************************************************************/

package org.cybergarage.x3d;

public class InvalidPrivateFieldException extends IllegalArgumentException { 
	public InvalidPrivateFieldException(){
		super();
	}
	public InvalidPrivateFieldException(String s){
		super(s);
	}
}
