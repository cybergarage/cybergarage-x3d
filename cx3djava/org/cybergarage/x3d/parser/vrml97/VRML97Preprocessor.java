/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : VRML97Preprocessor.java
*
******************************************************************/

package org.cybergarage.x3d.parser.vrml97;

import java.io.*;
import java.text.*;

import org.cybergarage.x3d.util.*;

public class VRML97Preprocessor {

	public VRML97Preprocessor() { 
		setOkFlag(false);
	}
	
	public VRML97Preprocessor(File file) throws FileNotFoundException, IOException { 
		open(file);
	}

	public VRML97Preprocessor(String file) throws FileNotFoundException, IOException { 
		open(file);
	}

	////////////////////////////////////////////////
	//	PROTO
	////////////////////////////////////////////////

	private LinkedList	mVRML97ProtoList = new LinkedList();
	
	public void addVRML97Proto(VRML97Proto proto) {
		mVRML97ProtoList.addNode(proto);
	}
	
	public VRML97Proto getVRML97Protos() {
		return (VRML97Proto)mVRML97ProtoList.getNodes();
	}
	
	public VRML97Proto getVRML97Proto(String name) {
		for (VRML97Proto proto=getVRML97Protos(); proto != null; proto=proto.next()) {
			String protoName = proto.getName();
			if (protoName.compareTo(name) == 0)
				return proto;
		}
		return null;
	}
	
	////////////////////////////////////////////////
	//	Flag 
	////////////////////////////////////////////////

	private boolean		mOK	= false;

	public void setOkFlag(boolean flag) {
		mOK = flag;
	}
	
	public boolean isOk() {
		return mOK;
	}
	
	public String getTempFilename() {
		return "__temp__filename__";
	}

	////////////////////////////////////////////////
	//	Utility 
	////////////////////////////////////////////////

	private final static DecimalFormat number2StringDecimalFormat = new DecimalFormat("#############################0.0#############################");

	private final static boolean isIntegerValue(double value) 
	{
		if ((value % 1.0) == 0.0)
			return true;
		return false;
	}
	
	public final static String number2String(double value) 
	{
		String str = null;
		if (isIntegerValue(value) == true) {
			int ivalue = (int)value;
			str = Integer.toString(ivalue);
		}
		else {
			//str = Double.toString(value);
			str = number2StringDecimalFormat.format(value);
		}
		
		return str;
	}
		
	////////////////////////////////////////////////
	//	getInputStream (Result)
	////////////////////////////////////////////////
	
	public InputStream	mInputStream	= null;

	public InputStream getInputStream() throws FileNotFoundException {
		if (isOk()) {
			mInputStream= new FileInputStream(getTempFilename());
			return mInputStream;
		}
		else
			throw new FileNotFoundException(getTempFilename());
	}

	////////////////////////////////////////////////
	//	close
	////////////////////////////////////////////////
	
	public void close() throws IOException {
		if (mInputStream == null)
			throw new IOException(getTempFilename());
		mInputStream.close();
	}

	////////////////////////////////////////////////
	//	delete
	////////////////////////////////////////////////
	
	public boolean delete() throws IOException {
		close();
		File file = new File(getTempFilename());
		return file.delete();
	}
				
	////////////////////////////////////////////////
	//	open
	////////////////////////////////////////////////

	public InputStream open(File file) throws FileNotFoundException, IOException { 
		setOkFlag(false);
		replace(file);
		return getInputStream();
	}
	
	public InputStream open(String file) throws FileNotFoundException, IOException { 
		setOkFlag(false);
		replace(file);
		return getInputStream();
	}
				
	////////////////////////////////////////////////
	//	Replace
	////////////////////////////////////////////////

	public void replace(File file) throws FileNotFoundException, IOException {
		InputStream inputStream = new FileInputStream(file);
		Reader r = new BufferedReader(new InputStreamReader(inputStream));
		VRML97ProtoTokenizer st = new VRML97ProtoTokenizer(r);
		replace(st);
		inputStream.close();
	}

	public void replace(String file) throws FileNotFoundException, IOException {
		InputStream inputStream = new FileInputStream(file);
		Reader r = new BufferedReader(new InputStreamReader(inputStream));
		VRML97ProtoTokenizer st = new VRML97ProtoTokenizer(r);
		replace(st);
  		inputStream.close();
	}

	private boolean isMField(String fieldName)
	{
		if (fieldName == null)
			return false;
		String beginTwoChars = new String(fieldName.getBytes(), 0, 2);
		if (beginTwoChars == null)
			return false;
		if (beginTwoChars.equals("MF") == true)
			return true;
		return false;
	}
	
	public String getParameterValue(String fieldName, VRML97ProtoTokenizer stream) throws IOException {
		if (fieldName.compareTo("SFBool") == 0) {
			stream.nextToken(); String value = stream.sval;
			return value;
		}
		else if (fieldName.compareTo("SFFloat") == 0) {
			stream.nextToken();	Double value = new Double(stream.nval);
			return value.toString();
		}
		else if (fieldName.compareTo("SFColor") == 0) {
			stream.nextToken();	double r = stream.nval;
			stream.nextToken();	double g = stream.nval;
			stream.nextToken();	double b = stream.nval;
			return r + " " + g + " " + b;
		}
		else if (fieldName.compareTo("SFImage") == 0) { // Not supported
			System.out.println("PROTO SFImage is not suppoted !!");
			return null;
		}
		else if (fieldName.compareTo("SFInt32") == 0) {
			stream.nextToken();Integer value = new Integer((int)stream.nval);
			return value.toString();
		}
		else if (fieldName.compareTo("SFString") == 0) {
			stream.nextToken(); 
			String value = stream.sval;
			return value;
		}
		else if (fieldName.compareTo("SFTime") == 0) {
			stream.nextToken();	Double value = new Double(stream.nval);
			return value.toString();
		}
		else if (fieldName.compareTo("SFVec2f") == 0) {
			stream.nextToken();	double x = stream.nval;
			stream.nextToken();	double y = stream.nval;
			return x + " " + y;
		}
		else if (fieldName.compareTo("SFVec3f") == 0) {
			stream.nextToken();	double x = stream.nval;
			stream.nextToken();	double y = stream.nval;
			stream.nextToken();	double z = stream.nval;
			return x + " " + y + " " + z;
		}
		else if (fieldName.compareTo("SFRotation") == 0) {
			stream.nextToken();	double x = stream.nval;
			stream.nextToken();	double y = stream.nval;
			stream.nextToken();	double z = stream.nval;
			stream.nextToken();	double r = stream.nval;
			return x + " " + y + " " + z + " " + r;
		}
		else if (fieldName.compareTo("SFNode") == 0) {
			stream.nextToken(); 
			String value = stream.sval;
			return value;
		}
		else if (isMField(fieldName) == true) {
			int	bigBracket = 0;
			int	smallBracket = 0;
			
			StringBuffer value = new StringBuffer();
			
			stream.nextToken();
			while (stream.ttype != StreamTokenizer.TT_EOF) {
				if (stream.ttype == StreamTokenizer.TT_EOL) {
					if (bigBracket == 0 && smallBracket == 0) {
						String appendedStr = value.toString();
						if (0 < appendedStr.length())
							return appendedStr;
						return "[]";
					}
					value.append("\n");
				}
				if (stream.ttype == StreamTokenizer.TT_WORD) {
					String str = stream.sval; 
					if (str.compareTo("[]") == 0)
						return new String("[]");
					if (str.compareTo("[") == 0) {
						bigBracket++;
						value.append("[ ");
						stream.nextToken();
						break;
					}
					if (str.compareTo("{") == 0) {
						smallBracket++;
						value.append("{ ");
						stream.nextToken();
						break;
					}
					value.append(str + " ");
				}
				stream.nextToken();
			}
			
			while (stream.ttype != StreamTokenizer.TT_EOF) {
				if (stream.ttype == StreamTokenizer.TT_WORD) {
					String str = stream.sval; 
					if (str.compareTo("]") == 0) {
						bigBracket--;
						if (bigBracket == 0 && smallBracket == 0) {
							value.append("]");
							break;
						}
					}
					if (str.compareTo("}") == 0) {
						smallBracket--;
						if (smallBracket == 0 && bigBracket == 0) {
							value.append("}");
							break;
						}
					}
					if (str.compareTo("[") == 0)
						bigBracket++;
					if (str.compareTo("{") == 0)
						smallBracket++;
					value.append(str + " ");	
				}
				if (stream.ttype == StreamTokenizer.TT_NUMBER) {
					double dvalue = stream.nval; 
					String str = number2String(dvalue);
					value.append(str + " ");
				}
				if (stream.ttype == StreamTokenizer.TT_EOL) {
					value.append("\n");
				}
				stream.nextToken();
			}
			
			return value.toString();
		}

		
		return null;
	}

	public boolean addProtoParameter(VRML97ProtoParameterList paramList, VRML97ProtoTokenizer stream) throws IOException {
		stream.nextToken();	
		if (stream.ttype != StreamTokenizer.TT_WORD)
			return false;
		String fieldName = stream.sval;
		
		stream.nextToken();	
		if (stream.ttype != StreamTokenizer.TT_WORD)
			return false;
		String name = stream.sval;
		
		String value = getParameterValue(fieldName, stream);
		if (name != null && value != null) {
			//Debug.message("  " + fieldName + " " + name + " = " + value);
			paramList.addParameter(fieldName, name, value);
			return true;
		}
		
		return false;
	}

	private boolean isFieldName(String fieldName)
	{
		if (fieldName == null)
			return false;
		if (fieldName.compareTo("field") == 0)
			return true;
		if (fieldName.compareTo("exposedField") == 0)
			return true;
		if (fieldName.compareTo("eventIn") == 0)
			return true;
		if (fieldName.compareTo("eventOut") == 0)
			return true;
		return false;		
	}
	
	public boolean addProtoParameters(VRML97ProtoParameterList paramList, VRML97ProtoTokenizer stream)  throws IOException {
		stream.nextToken();
		while (stream.ttype != StreamTokenizer.TT_EOF) {
			if (stream.ttype == StreamTokenizer.TT_WORD) {
				if (stream.sval.compareTo("[") == 0) 
					break;
			}
			stream.nextToken();
		}

		if (stream.ttype == StreamTokenizer.TT_EOF)
			return false;
					
		stream.nextToken();
		while (stream.ttype != StreamTokenizer.TT_EOF) {
			if (stream.ttype == StreamTokenizer.TT_WORD) {
				String str = stream.sval; 
				if (str.compareTo("]") == 0) 
					return true;
				else if (isFieldName(str) == true) { 
					if (addProtoParameter(paramList, stream) == false)
						return false;
				}
			}
			stream.nextToken();
		}
		
		return false;
	}
			
	public VRML97Proto createProto(VRML97ProtoTokenizer stream) throws IOException {
		stream.nextToken();
		String protoName = stream.sval;
		
		Debug.message("PROTO");
		Debug.message("  name = " + protoName);
		
		VRML97Proto proto = new VRML97Proto(protoName);

		if (addProtoParameters(proto.getParameterList(), stream) == false)
			return null;

		int nest = 0;
		
		stream.nextToken();
		while (stream.ttype != StreamTokenizer.TT_EOF) {
			if (stream.ttype == StreamTokenizer.TT_WORD) {
				if (stream.sval.compareTo("{") == 0) { 
					nest++;
					break;
				}
			}
			stream.nextToken();
		}

		stream.nextToken();
		while (stream.ttype != StreamTokenizer.TT_EOF && 0 < nest) {
			switch (stream.ttype) {
			case StreamTokenizer.TT_NUMBER:
				double dvalue = stream.nval; 
				String valStr = number2String(dvalue);
				proto.addToken(valStr);
				break;
			case StreamTokenizer.TT_WORD:
				if (stream.sval.compareTo("{") == 0) 
					nest++;
				if (stream.sval.compareTo("}") == 0) 
					nest--;
				if (0 < nest)
					proto.addToken(stream.sval);
				break;
			case StreamTokenizer.TT_EOL:
				proto.addToken("\n");
				break;
			}
			stream.nextToken();
		}
		
		return proto;
	}
	
	public boolean hasProto(VRML97Proto proto, String value) throws IOException 
	{
		StringReader strReader = new StringReader(value);
		VRML97ProtoTokenizer stream = new VRML97ProtoTokenizer(strReader);

		stream.nextToken();
		while (stream.ttype != StreamTokenizer.TT_EOF) {
			if (stream.ttype == StreamTokenizer.TT_WORD) {
				String token = stream.sval;
				if (proto.hasParameter(token) == true)
					return true;
			}
			stream.nextToken();
		}
		
		return false;
	}
	
	public String getVRML97ProtoString(VRML97Proto proto, VRML97ProtoTokenizer stream) throws IOException 
	{
		VRML97ProtoParameterList paramList = new VRML97ProtoParameterList();
		
		int indent = 0;
		
		stream.nextToken();
		while (stream.ttype != StreamTokenizer.TT_EOF) {
			if (stream.ttype == StreamTokenizer.TT_WORD) {
				if (stream.sval.compareTo("{") == 0) {
					indent++;
					break;
				}
			}
			stream.nextToken();
		}

		if (stream.ttype == StreamTokenizer.TT_EOF)
			return null;
					
		stream.nextToken();
		while (stream.ttype != StreamTokenizer.TT_EOF) {
			switch (stream.ttype) {
			case StreamTokenizer.TT_WORD:
				{
					String strToken = stream.sval;
					if (strToken.compareTo("{") == 0)
						indent++;
					else if (strToken.compareTo("}") == 0) {
						indent--;
						if (indent == 0)
							return proto.getString(paramList);
					}
					else {
						String name = strToken;
						VRML97ProtoParameter protoParam = proto.getParameter(name);
						if (protoParam != null) {
							String fieldTypeName = protoParam.getType();
							String value = getParameterValue(fieldTypeName, stream);
							if (hasProto(proto, value) == true) {
								Debug.message("==== value ==== ");
								Debug.message(value);
								StringWriter strWriter = new StringWriter();  
								PrintWriter printWriter = new PrintWriter(strWriter);
								StringReader strReader = new StringReader(value);
								VRML97ProtoTokenizer valueStream = new VRML97ProtoTokenizer(strReader);
								try {
									replace(valueStream, printWriter);
									strWriter.flush();
									strWriter.close();
								}
								catch (IOException e) {}
								value = strWriter.toString();
								Debug.message("==== New value ==== ");
								Debug.message(value);
							}
							paramList.addParameter(fieldTypeName, name, value);
						}
					}
				}
				break;
			case StreamTokenizer.TT_EOL:
				{
				}
				break;
			}
			stream.nextToken();
		}
		
		return null;
	}

	public void replace(VRML97ProtoTokenizer stream, PrintWriter printStream) throws IOException
	{
		int nToken = 0;
		while (stream.nextToken() != StreamTokenizer.TT_EOF) {
			switch (stream.ttype) {
			case StreamTokenizer.TT_NUMBER:
				{
					double numValue = stream.nval;
					printStream.print(number2String(numValue));
					
					boolean isNextTokenFloatWorld = false;
										
					stream.nextToken();
					if (stream.ttype == StreamTokenizer.TT_WORD) {
						String strToken = stream.sval;
						if (strToken != null) {
							if (strToken.startsWith("E") || strToken.startsWith("e")) {
								if (isIntegerValue(numValue) == true)
									printStream.print(".0");
								printStream.print(strToken);
								isNextTokenFloatWorld = true;
							}
						}
					}
					
					if (isNextTokenFloatWorld == false)
						stream.pushBack();
						
					printStream.print(" ");
					
					nToken++;
				}
				break;
			case StreamTokenizer.TT_WORD:
				{
					String strToken = stream.sval;
					if (strToken.compareTo("PROTO") == 0) { 
						VRML97Proto proto = createProto(stream);
						if (proto != null) {
							addVRML97Proto(proto);
							String protoString = proto.getString(null);
							if (protoString != null) {
								printStream.println(protoString);
							}
						}
					}
					else if (strToken.compareTo("DEF") == 0) { 
						String nodeName = null;
						stream.nextToken();
						if (stream.ttype == StreamTokenizer.TT_WORD)
							nodeName = stream.sval;
						if (nodeName != null && 0 < nodeName.length())
							printStream.print("DEF " + nodeName + " ");
					}
					else {
						VRML97Proto proto = getVRML97Proto(strToken);
						if (proto != null) {
							String protoString = getVRML97ProtoString(proto, stream);
							if (protoString != null) {
								Debug.message(protoString);
								printStream.println(protoString);
							}
						}
						else {
							printStream.print(strToken + " ");
							nToken++;
						}
					}
				}
				break;
			case StreamTokenizer.TT_EOL:
				if (0 < nToken)
					printStream.println("");
				nToken = 0;
				break;
			}
		}

		if (printStream != null) {
			printStream.flush();
			printStream.close();
		}
	}
	
	public void replace(VRML97ProtoTokenizer stream) 
	{
		FileOutputStream outputStream = null;
		
		try {
			outputStream = new FileOutputStream(getTempFilename());
	
			PrintWriter printStream = new PrintWriter(outputStream);
		
			replace(stream, printStream);
			
			outputStream.flush();
			outputStream.close();
		}
		catch (IOException e) {
			System.out.println("Couldn't replace a file(" + getTempFilename() + ") !!");
		}
		
		setOkFlag(true);
	}
};
