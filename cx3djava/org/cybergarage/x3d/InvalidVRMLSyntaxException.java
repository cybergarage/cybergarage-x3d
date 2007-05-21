/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : InvalidRouteException.java
*
******************************************************************/

package org.cybergarage.x3d;

public class InvalidVRMLSyntaxException extends Exception {
	public InvalidVRMLSyntaxException(){
		super();
	}
	public InvalidVRMLSyntaxException(String s){
		super(s);
	}
	
	public String getMessage() {  // This overrides a method in Exception
		return "not yet implemented !!";
	}
}
