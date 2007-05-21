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

public class InvalidRouteException extends IllegalArgumentException {
	public InvalidRouteException(){
		super();
	}
	public InvalidRouteException(String s){
		super(s);
	}
}