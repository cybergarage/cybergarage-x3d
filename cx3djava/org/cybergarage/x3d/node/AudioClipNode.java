/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : AudioClipNode.java
*
*	Revisions:
*
*	12/04/02
*		- Changed the super class from Node to SoundSourceNode.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class AudioClipNode extends SoundSourceNode 
{
	private final static String loopExposedFieldName			= "loop";
	private final static String startTimeExposedFieldName	= "startTime";
	private final static String stopTimeExposedFieldName	= "stopTime";

	private SFBool 			loopField;
	private SFTime			startTimeField;
	private SFTime			stopTimeField;

	public AudioClipNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.AUDIOCLIP);

		// loop exposed field
		loopField = new SFBool(true);
		addExposedField(loopExposedFieldName, loopField);

		// startTime exposed field
		startTimeField = new SFTime(0.0f);
		addExposedField(startTimeExposedFieldName, startTimeField);

		// stopTime exposed field
		stopTimeField = new SFTime(0.0f);
		addExposedField(stopTimeExposedFieldName, stopTimeField);
	}

	public AudioClipNode(AudioClipNode node) 
	{
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Loop
	////////////////////////////////////////////////

	public SFBool getLoopField() {
		if (isInstanceNode() == false)
			return loopField;
		return (SFBool)getExposedField(loopExposedFieldName);
	}
	
	public void setLoop(boolean value) {
		getLoopField().setValue(value);
	}

	public void setLoop(String value) {
		getLoopField().setValue(value);
	}

	public boolean getLoop() {
		return getLoopField().getValue();
	}
	
	public boolean isLoop() {
		return getLoop();
	}

	////////////////////////////////////////////////
	//	Start time
	////////////////////////////////////////////////

	public SFTime getStartTimeField() {
		if (isInstanceNode() == false)
			return startTimeField;
		return (SFTime)getExposedField(startTimeExposedFieldName);
	}
	
	public void setStartTime(double value) {
		getStartTimeField().setValue(value);
	}

	public void setStartTime(String value) {
		getStartTimeField().setValue(value);
	}
	
	public double getStartTime() {
		return getStartTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	Stop time
	////////////////////////////////////////////////

	public SFTime getStopTimeField() {
		if (isInstanceNode() == false)
			return stopTimeField;
		return (SFTime)getExposedField(stopTimeExposedFieldName);
	}
	
	public void setStopTime(double value) {
		getStopTimeField().setValue(value);
	}

	public void setStopTime(String value) {
		getStopTimeField().setValue(value);
	}

	public double getStopTime() {
		return getStopTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
		SFString description = getDescriptionField();
		printStream.println(indentString + "\t" + "description " + description );

		printStream.println(indentString + "\t" + "pitch " + getPitch() );
		printStream.println(indentString + "\t" + "startTime " + getStartTime() );
		printStream.println(indentString + "\t" + "stopTime " + getStopTime() );

		SFBool loop = getLoopField();
		printStream.println(indentString + "\t" + "loop " + loop);

		MFString url = getURLField();
		printStream.println(indentString + "\t" + "url [");
		url.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
