/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : StringValue.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.*;
import java.util.*;

public class StringValue extends Object implements Serializable {
	
	private String mValue; 
	private String mWhitespaceChars;
	private String mWordChars;
	
	public StringValue(String value) {
		setValue(value);
		setWhitespaceChars(null);
		setWordChars(null);
	}

	public StringValue(String value, String whitespaceChars) {
		setValue(value);
		setWhitespaceChars(whitespaceChars);
		setWordChars(null);
	}

	public StringValue(String value, String whitespaceChars, String wordChars) {
		setValue(value);
		setWhitespaceChars(whitespaceChars);
		setWordChars(wordChars);
	}

	public void setValue(String value) {
		mValue = value;
	}
	
	public String getValue() {
		return mValue;
	}	

	public void setWhitespaceChars(String value) {
		mWhitespaceChars = value;
	}
	
	public String getWhitespaceChars() {
		return mWhitespaceChars;
	}	

	public void setWordChars(String value) {
		mWordChars = value;
	}
	
	public String getWordChars() {
		return mWordChars;
	}	

	public String[] getTokens() {
		FieldTokenizer stream = new FieldTokenizer(getValue(), getWhitespaceChars(), getWordChars());
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
}
