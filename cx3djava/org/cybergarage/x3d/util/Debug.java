/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File:	Debug.java
*
******************************************************************/

package org.cybergarage.x3d.util;

public final class Debug {
	public static boolean enabled = false;
	public static final void on() {
		enabled = true;
	}
	public static final void off() {
		enabled = false;
	}
	public static boolean isOn() {
		return enabled;
	}
	public static final void assert(boolean b, String s) {
		if (enabled && !b)
			throw new AssertionException("CyberX3D assertion failed : " + s);
	}
	public static final void message(String s) {
		if (enabled == true)
			System.out.println("CyberX3D message : " + s);
	}
	public static final void warning(String s) {
		System.out.println("CyberX3D warning : " + s);
	}
}