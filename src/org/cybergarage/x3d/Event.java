/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Event.java
*
******************************************************************/

package org.cybergarage.x3d;

public class Event implements Cloneable {

	public Event(String name, double time, Field field) {
		setName(name);
		setTimeStamp(time);
		setValue(field);
	}

	////////////////////////////////////////////////
	//	Name
	////////////////////////////////////////////////

	private String mName = null;

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	////////////////////////////////////////////////
	//	Time
	////////////////////////////////////////////////
	
	private double mTime = 0.0;
	
	public void setTimeStamp(double time) {
		mTime = time;
	}

	public double getTimeStamp() {
		return mTime;
	}

	////////////////////////////////////////////////
	//	ConstField
	////////////////////////////////////////////////

	private Field mField = null;

	public void setValue(Field field) {
		mField = field;
	}

	public Field getValue() {
		return mField;
	}

	////////////////////////////////////////////////
	//	Clone
	////////////////////////////////////////////////

	public Object clone() {
		Event event = new Event(getName(), getTimeStamp(), getValue());
		return event;
	}
}