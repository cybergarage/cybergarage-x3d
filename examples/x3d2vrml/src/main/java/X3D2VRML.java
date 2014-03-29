/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1996-2003
*
*	File:	x3d2vrml.java
*
******************************************************************/

import org.cybergarage.x3d.*;

public class X3D2VRML 
{
	public static void main(String args[]) 
	{
		int argc = args.length;

		if (argc < 1){
			System.out.println("Usage: X3D2VRML <filename>");
			return;
		}
	
		SceneGraph sceneGraph = new SceneGraph();
	
		if (!sceneGraph.load(args[0])) {
			System.out.println("\t" + sceneGraph.getParserErrorMessage());
			return;
		}

		sceneGraph.print();
	}
}

