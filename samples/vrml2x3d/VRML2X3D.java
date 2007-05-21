/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1996-2003
*
*	File:	VRML2X3D.java
*
******************************************************************/

import org.cybergarage.x3d.*;

public class VRML2X3D
{
	public static void main(String args[]) 
	{
		int argc = args.length;
/*		
		if (argc < 1){
			System.out.println("Usage: X3D2VRML <filename>");
			return;
		}
*/
	
		SceneGraph sceneGraph = new SceneGraph();
	
//		if (sceneGraph.load(args[0]) == true)
		String fname = "c:/src/CyberX3D/worlds/sthenge.wrl";
		if (sceneGraph.load(fname) == true)
			sceneGraph.printXML();
		else
			System.out.println("\t" + sceneGraph.getParserErrorMessage());
	}
}

