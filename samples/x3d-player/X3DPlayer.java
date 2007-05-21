/******************************************************************
*
*	X3D Player for Java3D
*
*	Copyright (C) Satoshi Konno 1997-2003
*
*	File:	X3DPlayer.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import javax.media.j3d.*;

import org.cybergarage.x3d.*;

public class X3DPlayer extends Frame implements Constants, MouseListener, MouseMotionListener, Runnable {

	private X3DCoreJ3D vrmlCore = new X3DCoreJ3D();
	private Thread mThread = null;

	public SceneGraph getSceneGraph() {
		return vrmlCore.getSceneGraph();
	}
	
	private Frame getParentFrame() {
		return this;
	}
	
	public X3DPlayer()
	{
		super("CyberX3D Player");
		
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
		
		setLayout(new BorderLayout());

		Canvas3D canvas3d = vrmlCore.getCanvas3D();

		canvas3d.addMouseListener(this);
		canvas3d.addMouseMotionListener(this);

		setMenuBar(createMenuBar());
		add(canvas3d, BorderLayout.CENTER);

		addWindowListener( new WindowAdapter() {public void windowClosing(WindowEvent e) {System.exit(0);}});

		setSize(320,320);
		show();
		repaint();
	}

	////////////////////////////////////////////////
	//	Event
	////////////////////////////////////////////////
	
	public MenuBar createMenuBar() {
	
		MenuBar menubar = new MenuBar();

		Menu		menu;		
		Menu		subMenu;		
		MenuItem menuItem;
		
		
		menu = new Menu("File");
		
		menuItem = new MenuItem("New");
		menu.add(menuItem);
		menuItem.addActionListener(new NewSceneGraphAction());
		
		menuItem = new MenuItem("Load");
		menu.add(menuItem);
		menuItem.addActionListener(new LoadSceneGraphAction());

		subMenu = new Menu("Save");
		menu.add(subMenu);
		menuItem = new MenuItem("VRML97");
		subMenu.add(menuItem);
		menuItem.addActionListener(new SaveSceneGraphAsVRMLAction());
		menuItem = new MenuItem("X3D");
		subMenu.add(menuItem);
		menuItem.addActionListener(new SaveSceneGraphAsX3DAction());

		subMenu = new Menu("Print");
		menu.add(subMenu);
		menuItem = new MenuItem("VRML97");
		subMenu.add(menuItem);
		menuItem.addActionListener(new PrintSceneGraphAsVRMLAction());
		menuItem = new MenuItem("X3D");
		subMenu.add(menuItem);
		menuItem.addActionListener(new PrintSceneGraphAsX3DAction());
		
		menuItem = new MenuItem("Quit");
		menu.add(menuItem);
		menuItem.addActionListener(new QuitSceneGraphAction());
		
		menubar.add(menu);

	
		menu = new Menu("View");
		
		menuItem = new MenuItem("Reset");
		menu.add(menuItem);
		menuItem.addActionListener(new ResetViewpointAction());

		menubar.add(menu);
		
		
		return menubar;
	}
	
	////////////////////////////////////////////////
	//	Event
	////////////////////////////////////////////////

	private class NewSceneGraphAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			stopSimulation();
			SceneGraph sg = getSceneGraph();
			int result = JOptionPane.showConfirmDialog(getParentFrame(), "Are you sure you want to clear all scenegraph objects ?", "", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.YES_OPTION) 
				sg.clear();
			else
				startSimulation();
		}
    }

	public class VRMLFileFilter extends FileFilter {
		final static String wrl = "wrl";
		public boolean accept(File f) {
			if(f.isDirectory())
				return true;
			String s = f.getName();
			int i = s.lastIndexOf('.');
			if(i > 0 &&  i < s.length() - 1) {
				String extension = s.substring(i+1).toLowerCase();
				if (wrl.equals(extension) == true) 
					return true;
				else
					return false;
			}
			return false;
		}
		public String getDescription() {
			return "VRML Files (*.wrl)";
		}
	}

	public class X3DFileFilter extends FileFilter {
		final static String x3d = "x3d";
		public boolean accept(File f) {
			if(f.isDirectory())
				return true;
			String s = f.getName();
			int i = s.lastIndexOf('.');
			if(i > 0 &&  i < s.length() - 1) {
				String extension = s.substring(i+1).toLowerCase();
				if (x3d.equals(extension) == true) 
					return true;
				else
					return false;
			}
			return false;
		}
		public String getDescription() {
			return "X3D Files (*.x3d)";
		}
	}

	private class LoadSceneGraphAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			stopSimulation();
			SceneGraph sg = getSceneGraph();
			sg.setOption(SceneGraph.USE_PREPROCESSOR);
			String userDir = System.getProperty("user.dir");
			JFileChooser filechooser = new JFileChooser(new File(userDir));
			VRMLFileFilter vrmlFilter = new VRMLFileFilter();
			filechooser.addChoosableFileFilter(vrmlFilter);
			filechooser.addChoosableFileFilter(new X3DFileFilter());
			filechooser.setFileFilter(vrmlFilter);
			if(filechooser.showOpenDialog(getParentFrame()) == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				if (file != null) {
					if (file.isDirectory() == false) {
						sg.load(file);
					}
				}
			}
			//System.out.println(getSceneGraphJ3dObject());
			startSimulation();
		}
	}

	private class SaveSceneGraphAsVRMLAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
			stopSimulation();
			SceneGraph sg = getSceneGraph();
			String userDir = System.getProperty("user.dir");
			JFileChooser filechooser = new JFileChooser(new File(userDir));
			filechooser.addChoosableFileFilter(new VRMLFileFilter());
			if(filechooser.showSaveDialog(getParentFrame()) == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				if (file != null) {
					if (file.isDirectory() == false) {
						sg.saveVRML(file);
					}
				}
			}
			startSimulation();
		}
	}

	private class SaveSceneGraphAsX3DAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
			stopSimulation();
			SceneGraph sg = getSceneGraph();
			String userDir = System.getProperty("user.dir");
			JFileChooser filechooser = new JFileChooser(new File(userDir));
			filechooser.addChoosableFileFilter(new X3DFileFilter());
			if(filechooser.showSaveDialog(getParentFrame()) == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				if (file != null) {
					if (file.isDirectory() == false) {
						sg.saveXML(file);
					}
				}
			}
			startSimulation();
		}
	}

	private class PrintSceneGraphAsVRMLAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getSceneGraph().print();
		}
	}

	private class PrintSceneGraphAsX3DAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getSceneGraph().printXML();
		}
	}

	private class QuitSceneGraphAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}

	private class ResetViewpointAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			vrmlCore.zoomAllViewpoint();
			getParentFrame().repaint();
		}
	}

	////////////////////////////////////////////////
	//	Viewpoint
	////////////////////////////////////////////////
	
	public void updateViewpoint() 
	{
		int width = getWidth();
		int height = getHeight();
		vrmlCore.updateViewpoint(width, height);
	}
	
	//////////////////////////////////////////////////
	// Simulation
	//////////////////////////////////////////////////

	public void startSimulation() 
	{
		SceneGraph sg = getSceneGraph();
		if (sg.isSimulationRunning() == false) {
			vrmlCore.startSimulation();
			start();
		}
	}

  	public void stopSimulation() {
		SceneGraph sg = getSceneGraph();
		if (sg.isSimulationRunning() == true) {
			vrmlCore.stopSimulation();
			stop();
		}
	}
	
	////////////////////////////////////////////////
	//	paint
	////////////////////////////////////////////////

	public void paint(Graphics g) 
	{
	}

	////////////////////////////////////////////////
	//	mouse
	////////////////////////////////////////////////

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		vrmlCore.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		vrmlCore.mouseReleased(e);
	}

	public void mouseDragged(MouseEvent e) {
		vrmlCore.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
		vrmlCore.mouseMoved(e);
	}

	////////////////////////////////////////////////
	//	runnable
	////////////////////////////////////////////////

	public void start() 
	{
		if (mThread == null) {
			mThread = new Thread(this);
			mThread.start();
		}
	}

	public void stop() 
	{
		if (mThread != null) {
			//mThread.stop();
			mThread = null;
		}
	}

	public void run() 
	{
		Thread thisThread = Thread.currentThread();
		while (thisThread == mThread) {
			updateViewpoint();
			repaint();
			//Thread.yield();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}

	////////////////////////////////////////////////
	//	main
	////////////////////////////////////////////////
	
	public static void main(String args[]) {
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception exc) {
			System.err.println("Error loading L&F: " + exc);
		}
		new X3DPlayer();
	}
}
