/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File : ParserResult.java
*
******************************************************************/

package org.cybergarage.x3d.parser;

public class ParserResult
{
	private boolean mResult = false;
	private String mErrMsg;

	public ParserResult()
	{
		mErrMsg = "";
	}
		
	public void setResult(boolean flag) {
		mResult = flag;
	}

	public boolean getResult() {
		return mResult;
	}
	
	public boolean isOK() {
		return getResult();
	}

	public void setErrorMessage(String msg) {
		mErrMsg = msg;
	}
	
	public String getErrorMessage() {
		return mErrMsg;
	}
};
