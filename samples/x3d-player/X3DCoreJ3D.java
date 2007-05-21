/******************************************************************
*
*	VRML Viewer for Java3D
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File:	VRMLCoreJ3D.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;

import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.j3d.*;

public class X3DCoreJ3D implements Constants, MouseListener, MouseMotionListener {

	private SceneGraph			mSceneGraph			= null;					
	private SceneGraphJ3dObject	mSceneGraphObject	= null;
	private Canvas3D				mCanvas3D			= null;

	public SceneGraph getSceneGraph() {
		return mSceneGraph;
	}

	public SceneGraphJ3dObject getSceneGraphJ3dObject() {
		return mSceneGraphObject;
	}

	public Canvas3D getCanvas3D() {
		return mCanvas3D;
	}

	public X3DCoreJ3D() 
	{
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		mCanvas3D = new Canvas3D(config);

		mSceneGraph = new SceneGraph();
		mSceneGraphObject = new SceneGraphJ3dObject(mCanvas3D, mSceneGraph);
		mSceneGraph.setObject(mSceneGraphObject);
		mSceneGraph.setOption(SceneGraph.USE_PREPROCESSOR);
	}

	//////////////////////////////////////////////////
	// Viewpoint
	//////////////////////////////////////////////////

	public void zoomAllViewpoint()
	{
		SceneGraph sg = getSceneGraph();
		ViewpointNode view = sg.getViewpointNode();
		if (view == null)
			view = sg.getDefaultViewpointNode();
	
		sg.updateBoundingBox();
			
		float bboxCenter[]	= sg.getBoundingBoxCenter();
		float bboxSize[]	= sg.getBoundingBoxSize();
			
		float fov = view.getFieldOfView();
		float zoffset = bboxSize[0] / (float)Math.tan(fov);
			
		view.setPosition(bboxCenter[0], bboxCenter[1], bboxCenter[2] + zoffset*3.0f);
		view.setOrientation(0.0f, 0.0f, 1.0f, 0.0f);
	}
	
	//////////////////////////////////////////////////
	// Simulation
	//////////////////////////////////////////////////

	public void startSimulation() {
		SceneGraph sg = getSceneGraph();
		if (sg.isSimulationRunning() == false) {
			//System.out.println(getSceneGraphJ3dObject());
			sg.initialize();
			sg.startSimulation();
			sg.setHeadlightState(true);
			zoomAllViewpoint();
		}
	}

  	public void stopSimulation() {
		SceneGraph sg = getSceneGraph();
		if (sg.isSimulationRunning() == true) {
			sg.stopSimulation();
		}
	}
	
	////////////////////////////////////////////////
	//	mouse
	////////////////////////////////////////////////

	private int	mMouseX = 0;
	private int	mMouseY = 0;
	private int	mMouseButton = 0;

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getModifiers() == MouseEvent.BUTTON1_MASK)
			mMouseButton = 1;
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getModifiers() == MouseEvent.BUTTON1_MASK)
			mMouseButton = 0;
	}

	public void mouseDragged(MouseEvent e) {
		mMouseX = e.getX();
		mMouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mMouseX = e.getX();
		mMouseY = e.getY();
	}

	public int getMouseX() {
		return mMouseX;
	}

	public int getMouseY() {
		return mMouseY;
	}

	public int getMouseButton() {
		return mMouseButton;
	}

	////////////////////////////////////////////////
	//	Viewpoint
	////////////////////////////////////////////////
	
	public void updateViewpoint(int width, int heght) {

		// get mouse infomations
		float	width2 = (float)width / 2.0f;
		float	height2 = (float)heght /2.0f;

		int		mx = getMouseX();
		int		my = getMouseY();
		int		mbutton = getMouseButton();
		
		float	vector[] = new float[3];
		float	yrot = 0.0f;

		if (mbutton == 1) {
			vector[Z] = (((float)my - height2) / height2) * 0.1f;
			yrot = -(((float)mx - width2) / width2 * 0.01f);
		}

		// update viewpoint position
		SceneGraph sceneGraph = getSceneGraph();
		if (sceneGraph == null)
			return;
		ViewpointNode view = sceneGraph.getViewpointNode();
		if (view == null)
			view = sceneGraph.getDefaultViewpointNode();
		float viewOrienataion[] = new float[4];
		viewOrienataion[0] = 0.0f;
		viewOrienataion[1] = 1.0f;
		viewOrienataion[2] = 0.0f;
		viewOrienataion[3] = yrot;
		view.addOrientation(viewOrienataion);

		float viewFrame[][] = new float[3][3];
		view.getFrame(viewFrame);
		for (int n=X; n<=Z; n++) {
			viewFrame[n][X] *= vector[n];
			viewFrame[n][Y] *= vector[n];
			viewFrame[n][Z] *= vector[n];
			view.addPosition(viewFrame[n]);
		}
	}
}
