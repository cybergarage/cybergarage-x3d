/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : VRML97Proto.java
*
*	Revision;
*
*	12/11/03
*		- Thuan Truong <tqthuan@tma.com.vn>
*		- Fixed getString() using VRML97Preprocessor.number2String.
*
******************************************************************/

package org.cybergarage.x3d.parser.vrml97;

import java.io.*;

import org.cybergarage.x3d.util.*;

public class VRML97Proto extends LinkedListNode {

	public VRML97Proto(String name) {
		setHeaderFlag(false);
		setName(name);
	}
	
	////////////////////////////////////////////////
	//	Name
	////////////////////////////////////////////////

	protected String	mName;

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	////////////////////////////////////////////////
	//	Parameter
	////////////////////////////////////////////////

	protected VRML97ProtoParameterList	mParameterList = new VRML97ProtoParameterList();

	public VRML97ProtoParameterList getParameterList() {
		return mParameterList;
	}
	
	public void addParameter(String type, String name, String defalutValue) {
		mParameterList.addParameter(type, name, defalutValue);
	}

	public VRML97ProtoParameter getParameters() {
		return mParameterList.getParameters();
	}
	
	public VRML97ProtoParameter getParameter(String name) {
		return mParameterList.getParameter(name);
	}

	public boolean hasParameter(String name) {
		return mParameterList.hasParameter(name);
	}

	////////////////////////////////////////////////
	//	Token Buffer
	////////////////////////////////////////////////

	protected VRML97ProtoStream	mVRML97ProtoStream = new VRML97ProtoStream();

	public void addToken(String token) {
		mVRML97ProtoStream.addToken(token);
	}

	public void addToken(double token) {
		mVRML97ProtoStream.addToken(token);
	}

	public VRML97ProtoStream getTokenStream() {
		return mVRML97ProtoStream;
	}
	
	////////////////////////////////////////////////
	//	Parameter Value
	////////////////////////////////////////////////

	public String getParameterString(VRML97ProtoParameterList paramList, String name) {
		String value = null;
		if (paramList != null) { 
			VRML97ProtoParameter param = paramList.getParameter(name);
			if (param != null)
				value = param.getValue();
		}
		if (value == null) {
			VRML97ProtoParameter param = getParameter(name);
			if (param != null)
				value = param.getValue();
		}
		return value;
	}

	public String getString(VRML97ProtoParameterList paramList) throws IOException {
		VRML97ProtoStream protoStream = getTokenStream();
		protoStream.rewind();
		Reader r = new BufferedReader(new InputStreamReader(protoStream));
		VRML97ProtoTokenizer stream = new VRML97ProtoTokenizer(r);

		StringBuffer protoString = new StringBuffer();
		
		int nToken = 0;
		while (stream.nextToken() != StreamTokenizer.TT_EOF) {
			switch (stream.ttype) {
			case StreamTokenizer.TT_NUMBER:
				double dvalue = stream.nval; 
				String valStr = VRML97Preprocessor.number2String(dvalue);
				protoString.append(valStr + " ");
				nToken++;
				break;
			case StreamTokenizer.TT_WORD:
				if (stream.sval.compareTo("IS") == 0) {
					stream.nextToken();
					if (stream.ttype == StreamTokenizer.TT_WORD) {
						String param = getParameterString(paramList, stream.sval);
						if (param != null)
							protoString.append(param + " ");
					}
				}
				else
					protoString.append(stream.sval + " ");
				nToken++;
				break;
			case StreamTokenizer.TT_EOL:
				if (0 < nToken)
					protoString.append("\n");
				nToken = 0;
				break;
			}
		}
		
		return protoString.toString();
	}
	 
	////////////////////////////////////////////////
	//	Next node 
	////////////////////////////////////////////////

	public VRML97Proto next() {
		return (VRML97Proto)getNextNode();
	}
	
	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		StringBuffer protoString = new StringBuffer();
		protoString.append(getName() + " ");
		protoString.append("[");
		for (VRML97ProtoParameter param=getParameters(); param != null; param=param.next()) {
			protoString.append(param.getType() + " ");
			protoString.append(param.getName() + " ");
			protoString.append(param.getValue() + " ");
		}
		protoString.append("]\n");
		protoString.append("{");
		protoString.append(getTokenStream().getTokenBuffer() + "\n");
		protoString.append("}\n");
		
		return protoString.toString();
	}
}
