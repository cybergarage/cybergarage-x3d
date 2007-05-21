/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : FieldTokenizer.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import java.io.*;

public class FieldTokenizer extends StreamTokenizer {

	public FieldTokenizer(Reader reader) { 
		super(reader);
		initializeTokenizer();
	}

	public FieldTokenizer(Reader reader, String whitespaceChars) { 
		this(reader);
		addWhiteSpaceChars(whitespaceChars);
	}

	public FieldTokenizer(Reader reader, String whitespaceChars, String wordChars) { 
		this(reader, whitespaceChars);
		addWordChars(wordChars);
	}

	public FieldTokenizer(String string) { 
		this(new StringReader(string));
	}

	public FieldTokenizer(String string, String whitespaceChars) {
		this(string);
		addWhiteSpaceChars(whitespaceChars);
	} 

	public FieldTokenizer(String string, String whitespaceChars, String wordChars) {
		this(string, whitespaceChars);
		addWordChars(wordChars);
	} 

	public void initializeTokenizer() {
		eolIsSignificant(false);
		resetSyntax();	
		wordChars('A', 'Z');
		wordChars('a', 'z');
		wordChars('0', '9');
		wordChars('+', '+');
		wordChars('-', '-');
		wordChars('_', '_');
		wordChars('.', '.');
		
		whitespaceChars(',', ',');
		whitespaceChars(' ', ' ');
		
		quoteChar('"');
	}

	public void addWhiteSpaceChars(String whitespaceChars) {
		if (whitespaceChars == null)
			return;
		int length = whitespaceChars.length();
		for (int n=0; n<length; n++) {
			int c = (int)whitespaceChars.charAt(n);
			whitespaceChars(c, c);
		}
	}

	public void addWordChars(String wordChars) {
		if (wordChars == null)
			return;
		int length = wordChars.length();
		for (int n=0; n<length; n++) {
			int c = (int)wordChars.charAt(n);
			wordChars(c, c);
		}
	}
};
