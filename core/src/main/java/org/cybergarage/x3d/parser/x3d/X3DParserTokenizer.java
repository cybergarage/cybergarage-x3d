/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : X3DParserTokenizer.java
*
******************************************************************/

package org.cybergarage.x3d.parser.x3d;

import java.util.*;

public class X3DParserTokenizer extends StringTokenizer
{
	private final static String DELIM_TOKENS = ",\"\t\r\n";
	
	public X3DParserTokenizer(String value) { 
		super(value, DELIM_TOKENS);
	}

	public String nextToken()
	{
		String token = super.nextToken();
		
		if (token.startsWith("\"") == true)
			token = token.substring(1);

		if (token.endsWith("\"") == true)
			token = token.substring(0, token.length()-1-1);
			
		return token;
	}
};
