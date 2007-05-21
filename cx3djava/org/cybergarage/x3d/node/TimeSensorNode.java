/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : TimeSensor.java
*
*	Revisions:
*
*	11/13/02
*		- Added the follwing new X3D fields.
*			isPaused, pauseTime, resumeTime, elapsedTime, numLoops
*
******************************************************************/

package org.cybergarage.x3d.node;

import java.io.PrintWriter;
import java.util.Date;
import org.cybergarage.x3d.*;
import org.cybergarage.x3d.field.*;

public class TimeSensorNode extends SensorNode implements TimeDependentObject 
{
	static private final String	loopFieldName				= "loop";
	static private final String	cybleIntervalFieldName	= "cycleInterval";
	static private final String	startTimeFieldName		= "startTime";
	static private final String	stopTimeFieldName		= "stopTime";
	static private final String	cycleTimeFieldName		= "cycleTime";
	static private final String	timeFieldName				= "time";

	static private final String	isPausedFieldName		= "isPaused";
	static private final String	pauseTimeFieldName		= "pauseTime";
	static private final String	resumeTimeFieldName		= "resumeTime";
	static private final String	elapsedTimeFieldName	= "elapsedTime";
	static private final String	numLoopsFieldName		= "numLoops";

	private SFBool 		loopField;
	private SFTime 	cybleIntervalField;
	private SFTime 	startTimeField;
	private SFTime 	stopTimeField;
	private SFTime 	cycleTimeField;
	private SFTime 	timeField;
	private SFFloat 	fractionField;

	private SFBool 		isPausedField;
	private SFTime 	pauseTimeField;
	private SFTime 	resumeTimeField;
	private SFTime 	elapsedTimeField;
	private SFFloat	 	numLoopsField;

	public TimeSensorNode() {
		setHeaderFlag(false);
		setType(NodeType.TIMESENSOR);
		//setRunnable(true);

		///////////////////////////////////////
		// VRML97 Fields
		///////////////////////////////////////
		
		// loop exposed field
		loopField = new SFBool(true);
		addExposedField(loopFieldName, loopField);

		// cybleInterval exposed field
		cybleIntervalField = new SFTime(1.0);
		addExposedField(cybleIntervalFieldName, cybleIntervalField);

		// startTime exposed field
		startTimeField = new SFTime(0.0f);
		addExposedField(startTimeFieldName, startTimeField);

		// stopTime exposed field
		stopTimeField = new SFTime(0.0f);
		addExposedField(stopTimeFieldName, stopTimeField);

	
		// cycleTime eventOut field
		cycleTimeField = new SFTime(-1.0f);
		addEventOut(cycleTimeFieldName, cycleTimeField);

		// time eventOut field
		timeField = new SFTime(-1.0f);
		addEventOut(timeFieldName, timeField);

		// fraction_changed eventOut field
		fractionField = new SFFloat(0.0f);
		addEventOut(fractionFieldName, fractionField);

		///////////////////////////////////////
		// X3D Fields
		///////////////////////////////////////
		
		// isPaused eventOut field
		isPausedField = new SFBool(false);
		addEventOut(isPausedFieldName, isPausedField);

		// pauseTime exposed field
		pauseTimeField = new SFTime(0);
		addExposedField(pauseTimeFieldName, pauseTimeField);

		// resumeTime exposed field
		resumeTimeField = new SFTime(0);
		addExposedField(resumeTimeFieldName, resumeTimeField);

		// elapsedTime eventOut field
		elapsedTimeField = new SFTime(0);
		addEventOut(elapsedTimeFieldName, elapsedTimeField);

		// numLoops exposed field
		numLoopsField = new SFFloat(1.0f);
		addExposedField(numLoopsFieldName, numLoopsField);
	}

	public TimeSensorNode(TimeSensorNode node) {
		this();
		setFieldValues(node);
	}

	////////////////////////////////////////////////
	//	Loop
	////////////////////////////////////////////////

	public SFBool getLoopField() {
		if (isInstanceNode() == false)
			return loopField;
		return (SFBool)getExposedField(loopFieldName);
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
	//	Cyble Interval
	////////////////////////////////////////////////

	public SFTime getCycleIntervalField() {
		if (isInstanceNode() == false)
			return cybleIntervalField;
		return (SFTime)getExposedField(cybleIntervalFieldName);
	}
	
	public void setCycleInterval(double value) {
		getCycleIntervalField().setValue(value);
	}

	public void setCycleInterval(String value) {
		getCycleIntervalField().setValue(value);
	}
	
	public double getCycleInterval() {
		return getCycleIntervalField().getValue();
	}
	
	////////////////////////////////////////////////
	//	Start time
	////////////////////////////////////////////////
	
	public SFTime getStartTimeField() {
		if (isInstanceNode() == false)
			return startTimeField;
		return (SFTime)getExposedField(startTimeFieldName);
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
		return (SFTime)getExposedField(stopTimeFieldName);
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
	//	fraction_changed
	////////////////////////////////////////////////

	public SFFloat getFractionChangedField() {
		if (isInstanceNode() == false)
			return fractionField;
		return (SFFloat)getEventOut(fractionFieldName);
	}
	
	public void setFractionChanged(float value) {
		getFractionChangedField().setValue(value);
	}

	public void setFractionChanged(String value) {
		getFractionChangedField().setValue(value);
	}

	public float getFractionChanged() {
		return getFractionChangedField().getValue();
	}
	
	public SFFloat getFractionField() {
		return getFractionChangedField();
	}

	public void setFraction(float value) {
		setFractionChanged(value);
	}

	public void setFraction(String value) {
		setFractionChanged(value);
	}

	public float getFraction() {
		return getFractionChanged();
	}
	
	////////////////////////////////////////////////
	//	Cycle time
	////////////////////////////////////////////////

	public SFTime getCycleTimeField() {
		if (isInstanceNode() == false)
			return cycleTimeField;
		return (SFTime)getEventOut(cycleTimeFieldName);
	}
	
	public void setCycleTime(double value) {
		getCycleTimeField().setValue(value);
	}

	public void setCycleTime(String value) {
		getCycleTimeField().setValue(value);
	}
	
	public double getCycleTime() {
		return getCycleTimeField().getValue();
	}
	
	////////////////////////////////////////////////
	//	Time
	////////////////////////////////////////////////

	public SFTime getTimeField() {
		if (isInstanceNode() == false)
			return timeField;
		return (SFTime)getEventOut(timeFieldName);
	}
	
	public void setTime(double value) {
		getTimeField().setValue(value);
	}

	public void setTime(String value) {
		getTimeField().setValue(value);
	}
	
	public double getTime() {
		return getTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	IsPaused (X3D)
	////////////////////////////////////////////////

	public SFBool getIsPausedField() {
		if (isInstanceNode() == false)
			return isPausedField;
		return (SFBool)getEventOut(isPausedFieldName);
	}
	
	public void setIsPaused(boolean value) {
		getIsPausedField().setValue(value);
	}

	public void setIsPaused(String value) {
		getIsPausedField().setValue(value);
	}
	
	public boolean getIsPaused() {
		return getIsPausedField().getValue();
	}
	
	public boolean isPaused() {
		return getIsPaused();
	}

	////////////////////////////////////////////////
	//	Elapsed time (X3D)
	////////////////////////////////////////////////

	public SFTime getElapsedTimeField() {
		if (isInstanceNode() == false)
			return elapsedTimeField;
		return (SFTime)getEventOut(elapsedTimeFieldName);
	}
	
	public void setElapsedTime(double value) {
		getElapsedTimeField().setValue(value);
	}

	public void setElapsedTime(String value) {
		getElapsedTimeField().setValue(value);
	}
	
	public double getElapsedTime() {
		return getElapsedTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	Pause time (X3D)
	////////////////////////////////////////////////

	public SFTime getPauseTimeField() {
		if (isInstanceNode() == false)
			return pauseTimeField;
		return (SFTime)getExposedField(pauseTimeFieldName);
	}
	
	public void setPauseTime(double value) {
		getPauseTimeField().setValue(value);
	}

	public void setPauseTime(String value) {
		getPauseTimeField().setValue(value);
	}
	
	public double getPauseTime() {
		return getPauseTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	Resume time (X3D)
	////////////////////////////////////////////////

	public SFTime getResumeTimeField() {
		if (isInstanceNode() == false)
			return resumeTimeField;
		return (SFTime)getExposedField(resumeTimeFieldName);
	}
	
	public void setResumeTime(double value) {
		getResumeTimeField().setValue(value);
	}

	public void setResumeTime(String value) {
		getResumeTimeField().setValue(value);
	}
	
	public double getResumeTime() {
		return getResumeTimeField().getValue();
	}

	////////////////////////////////////////////////
	//	numLoops (X3D)
	////////////////////////////////////////////////

	public SFFloat getNumLoopsField() {
		if (isInstanceNode() == false)
			return numLoopsField;
		return (SFFloat)getExposedField(numLoopsFieldName);
	}
	
	public void setNumLoops(float value) {
		getNumLoopsField().setValue(value);
	}

	public void setNumLoops(String value) {
		getNumLoopsField().setValue(value);
	}
	
	public float getNumLoops() {
		return getNumLoopsField().getValue();
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
		setCycleTime(-1.0f);
		setIsActive(false);
		setRunnableIntervalTime((int)(getCycleInterval() * 1000));
	}

	public void uninitialize() {
	}

	public double getCurrentSystemTime() {
		Date date = new Date();
		return (double)date.getTime() / 1000.0;
	}
	
	private double currentTime = 0;
	
	public void update() {

		double startTime = getStartTime();
		double stopTime = getStopTime();
		double cycleInterval = getCycleInterval();

		boolean bActive	= isActive();
		boolean bEnable	= isEnabled();
		boolean bLoop		= isLoop();

		if (currentTime == 0)
			currentTime = getCurrentSystemTime();

		// isActive 
		if (bEnable == false && bActive == true) {
			setIsActive(false);
			sendEvent(getIsActiveField());
			return;
		}

		if (bActive == false && bEnable == true) {
			if (startTime <= currentTime) {
				if (bLoop == true && stopTime <= startTime)
					bActive = true;
				else if (bLoop == false && stopTime <= startTime)
					bActive = true;
				else if (currentTime <= stopTime) {
					if (bLoop == true && startTime < stopTime)
						bActive = true;
					else if	(bLoop == false && startTime < (startTime + cycleInterval) && (startTime + cycleInterval) <= stopTime)
						bActive = true;
					else if (bLoop == false && startTime < stopTime && stopTime < (startTime + cycleInterval))
						bActive = true;
				}
			}
			if (bActive) {
				setIsActive(true);
				sendEvent(getIsActiveField());
				setCycleTime(currentTime);
				sendEvent(getCycleTimeField());
			}
		}

		currentTime = getCurrentSystemTime();
	
		if (bActive == true && bEnable == true) {
			if (bLoop == true && startTime < stopTime) {
				if (stopTime < currentTime)
					bActive = false;
			}
			else if (bLoop == false && stopTime <= startTime) {
				if (startTime + cycleInterval < currentTime)
					bActive = false;
			}
			else if (bLoop == false && startTime < (startTime + cycleInterval) && (startTime + cycleInterval) <= stopTime) {
				if (startTime + cycleInterval < currentTime)
					bActive = false;
			}
			else if (bLoop == false && startTime < stopTime && stopTime < (startTime + cycleInterval)) {
				if (stopTime < currentTime)
					bActive = false;
			}

			if (bActive == false) {
				setIsActive(false);
				sendEvent(getIsActiveField());
			}
		}

		if (bEnable == false || isActive() == false)
			return;

		// fraction_changed 
		double	fraction = (currentTime - startTime) % cycleInterval;
		if (fraction == 0.0 && startTime < currentTime)
			fraction = 1.0;
		else
			fraction /= cycleInterval;
		setFractionChanged((float)fraction);
		sendEvent(getFractionChangedField());

		// cycleTime
		double	cycleTime		= getCycleTime();
		double	cycleEndTime	= cycleTime + cycleInterval;
		while (cycleEndTime < currentTime) {
			setCycleTime(cycleEndTime);
			cycleEndTime += cycleInterval;
			setCycleTime(currentTime);
			sendEvent(getCycleTimeField());
		}

		// time
		setTime(currentTime);
		sendEvent(getTimeField());
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool bEnabled = getEnabledField();
		SFBool loop = getLoopField();

		printStream.println(indentString + "\t" + "cycleInterval " + getCycleInterval() );
		printStream.println(indentString + "\t" + "enabled " + bEnabled );
		printStream.println(indentString + "\t" + "loop " + loop );
		printStream.println(indentString + "\t" + "startTime " + getStartTime() );
		printStream.println(indentString + "\t" + "stopTime " + getStopTime() );
	}
}
