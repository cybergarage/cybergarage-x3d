/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFieldSingleValueTokenizer.java
*
******************************************************************/

package org.cybergarage.x3d.field;

public class MFieldSingleValueTokenizer {

	public final static String[] getTokens(String value) {
		return StringFieldTokenizer.getTokens(value, ", ", "%");
	}	

}
