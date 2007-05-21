/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : XMLElement.java
*
******************************************************************/

package org.cybergarage.x3d.xml;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class XMLElement extends Field {

	private StringValue mValue = new StringValue(null); 

	public XMLElement() 
	{
		setType(FieldType.XML);
	}

	public XMLElement(XMLElement string) 
	{
		setType(FieldType.XML);
		setValue(string);
	}

	public XMLElement(String value) 
	{
		setType(FieldType.XML);
		setValue(value);
	}

	public void setValue(String value) 
	{
		synchronized (mValue) {
			mValue.setValue(value);
		}
	}

	public void setValue(XMLElement ele) 
	{
		setValue(ele.getValue());
	}

	public String getValue() 
	{
		String value;
		synchronized (mValue) {
			value = mValue.getValue();
		}
		return value;
	}

	public int getValueCount()
	{
		return 1;
	}
	
	////////////////////////////////////////////////
	//	abstract methods
	////////////////////////////////////////////////

	public void setValue(Field field)
	{
		if (field instanceof XMLElement)
			setValue((XMLElement)field);
	}

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (StringValue)object;
		}
	}

	public Object getObject() {
		Object object;
		synchronized (mValue) {
			object = mValue;
		}
		return object;
	}
	
	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() 
	{
		String value = getValue();
		if (value == null)
			return new String("\"\""); 
		return "\"" + value + "\"";
	}

	public String toXMLString()
	{
		String value = getValue();
		if (value == null)
			return "";
		return value;
	}
}