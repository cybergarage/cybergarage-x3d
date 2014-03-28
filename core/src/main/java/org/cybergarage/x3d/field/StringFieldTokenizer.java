/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : StringFieldTokenizer.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.*;
import java.util.*;

public class StringFieldTokenizer extends Object {
	
	public final static String[] getTokens(String value, String whitespaceChars, String wordChars) {
		FieldTokenizer stream = new FieldTokenizer(value, whitespaceChars, wordChars);
		Vector tokenBuffer = new Vector();
		
		try {
			stream.nextToken();
			while (stream.ttype != StreamTokenizer.TT_EOF) {
				switch (stream.ttype) {
				case StreamTokenizer.TT_WORD: 
					{
						tokenBuffer.addElement(new String(stream.sval));
					}
					break;
				case StreamTokenizer.TT_NUMBER:
					{
						if ((stream.nval % 1.0) == 0.0)
							tokenBuffer.addElement(Integer.toString((int)stream.nval));
						else
							tokenBuffer.addElement(Double.toString(stream.nval));
					}
					break;
				}
				stream.nextToken();
			}
		}
		catch (IOException e) {
			return null;
		}
		
		String tokens[] = new String[tokenBuffer.size()];
		for (int n=0; n<tokenBuffer.size(); n++) {
			tokens[n] = (String)tokenBuffer.elementAt(n);
		}
		
		return tokens;
	}	

	public final static String[] getTokens(String value) {
		return getTokens(value, ", ", "%");
	}	

}
