/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File: LoadSensorNode.java
*
*	Revisions:
*
*	11/14/02
*		- The first revision.
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class LoadSensorNode extends NetworkSensorNode 
{
	private static final String watchListFieldName = "watchList";
	private static final String timeoutFieldName = "timeout";
	private static final String isLoadedFieldName = "isLoaded";
	private static final String loadTimeFieldName = "loadTime";
	private static final String progressFieldName = "progress";

	private MFNode watchListField;
	private SFTime timeoutField;
	private SFBool isLoadedField;
	private SFTime loadTimeField;
	private SFFloat progressField;

	public LoadSensorNode() 
	{
		setHeaderFlag(false);
		setType(NodeType.LOADSENSOR);

		// watchList exposed field
		watchListField = new MFNode();
		addExposedField(watchListFieldName, watchListField);

		// timeout exposed field
		timeoutField = new SFTime(0.0f);
		addExposedField(timeoutFieldName, timeoutField);

		// isLoaded eventOut field
		isLoadedField = new SFBool(false);
		addEventOut(isLoadedFieldName, isLoadedField);
		
		// loadTime exposed field
		loadTimeField = new SFTime(0);
		addEventOut(loadTimeFieldName, loadTimeField);
		
		// progress field
		progressField = new SFFloat(0.0f);
		addEventOut(progressFieldName, progressField);
		
	}

	////////////////////////////////////////////////
	//	watchList field
	////////////////////////////////////////////////

	public MFNode getWatchListField() {
		if (isInstanceNode() == false)
			return watchListField;
		return (MFNode)getExposedField(watchListFieldName);
	}

	////////////////////////////////////////////////
	//	timeout field
	////////////////////////////////////////////////
	
	public SFTime getTimeoutField() {
		if (isInstanceNode() == false)
			return timeoutField;
		return (SFTime)getExposedField(timeoutFieldName);
	}

	public void setTimeout(double value) {
		getTimeoutField().setValue(value);
	}

	public double getTimeout() {
		return getTimeoutField().getValue();
	}

	////////////////////////////////////////////////
	//	isActive field
	////////////////////////////////////////////////

	public SFBool getIsLoadedField() {
		if (isInstanceNode() == false)
			return isLoadedField;
		return (SFBool)getEventOut(isLoadedFieldName);
	}
	
	public void setIsLoaded(boolean value) {
		getIsLoadedField().setValue(value);
	}

	public boolean getIsLoaded() {
		return getIsLoadedField().getValue();
	}
	
	public boolean isLoaded() {
		return getIsLoaded();
	}

	////////////////////////////////////////////////
	//	loadTime field
	////////////////////////////////////////////////

	public SFTime getLoadTimeField() {
		if (isInstanceNode() == false)
			return loadTimeField;
		return (SFTime)getEventOut(loadTimeFieldName);
	}
	
	public void setLoadTime(double value) {
		getLoadTimeField().setValue(value);
	}

	public double getLoadTime() {
		return getLoadTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	progress field
	////////////////////////////////////////////////

	public SFFloat getProgressField() {
		if (isInstanceNode() == false)
			return progressField;
		return (SFFloat)getEventOut(progressFieldName);
	}
	
	public void setProgress(float value) {
		getProgressField().setValue(value);
	}

	public float getProgress() {
		return getProgressField().getValue();
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node)
	{
		return false;
	}

	public void initialize() 
	{
	}

	public void uninitialize() 
	{
	}

	public void update() 
	{
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) 
	{
	}
}
