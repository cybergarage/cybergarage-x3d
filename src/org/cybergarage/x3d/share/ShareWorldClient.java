/******************************************************************
*
*	CyberVRML97 for Java3D
*
*	Copyright (C) Satoshi Konno 1997-2000
*
*	File:	ShareWorldClient.java
*
******************************************************************/

package org.cybergarage.x3d.share;

import java.io.*;
import java.net.*;

import org.cybergarage.x3d.util.*;

public class ShareWorldClient extends ShareWorld
{
	public ShareWorldClient()
	{
		setSocket(null);
		setObjectInputStream(null);
		setObjectInputStream(null);	
	}
	
	public ShareWorldClient(String hostName, int port)
	{
		this();
		Socket socket = createSocket(hostName, port);
		setSocket(socket);
	}

	public ShareWorldClient(String hostName)
	{
		this(hostName, getDefaultSeverSocketPort());
	}

	////////////////////////////////////////////////
	//	Socket
	////////////////////////////////////////////////

	private Socket mSocket = null;

	private Socket createSocket(String hostName, int port)
	{
		Socket socket = null;
		
		try {
			socket = new Socket(hostName, port);
		}
		catch (IOException ioe) {};
		
		return socket;
	}		
	
	public void setSocket(Socket socket, boolean closeSocket)
	{
		if (closeSocket == true) {
			if (isConnected() == true)
				closeSocket();
		}
		mSocket = socket;
	}

	public void setSocket(Socket socket)
	{
		setSocket(socket, true);
	}
	
	public Socket getSocket()
	{
		return mSocket;
	}

	public boolean isConnected()
	{
		if (getSocket() != null)
			return true;
		return false;
	}

	public void closeSocket()
	{
		Socket socket = getSocket();
		if (socket == null)
			return;
			
		try {
			socket.close();
			setSocket(null, false);
			
			ObjectInputStream objIn = getObjectInputStream();
			if (objIn != null) {
				objIn.close();
				setObjectInputStream(null);
			}

			ObjectOutputStream objOut = getObjectOutputStream();
			if (objOut != null) {
				objOut.close();
				setObjectOutputStream(null);
			}
		}
		catch (IOException ioe) {};
	}

	////////////////////////////////////////////////
	//	ObjectIn/OutStream
	////////////////////////////////////////////////

	private ObjectInputStream	objIn;
	private ObjectOutputStream	objOut;
	
	public void setObjectInputStream(ObjectInputStream in)
	{
		objIn = in;
	}

	public void setObjectOutputStream(ObjectOutputStream out)
	{
		objOut = out;
	}

	public ObjectInputStream getObjectInputStream() throws IOException
	{
		if (isConnected() == false)
			return null;
		if (objIn == null)
			objIn = new ObjectInputStream(getSocket().getInputStream());
		return objIn;
	}

	public ObjectOutputStream getObjectOutputStream() throws IOException
	{
		if (isConnected() == false)
			return null;
		if (objOut == null)
			objOut = new ObjectOutputStream(getSocket().getOutputStream());
		return objOut;
	}
	
	////////////////////////////////////////////////
	//	Share Object
	////////////////////////////////////////////////
	
	public boolean postShreObject(ShareObject shareObject)
	{
		if (isConnected() == false)
			return false;

		Debug.message("postShreObject");

		try {
			ObjectOutputStream out = getObjectOutputStream();
			out.reset();
			out.writeObject(shareObject);
			shareObject.writeData(out);
			out.flush();
		}
		catch (Exception ioe) {
			Debug.warning("ShareWorldClient.postShreObject");
			Debug.warning(ioe.getMessage());
			return false;
		}

		Debug.message("  done");
		
		return true;
	}

	public ShareObject readShareObject()
	{
		Debug.message("readShareObject");

		if (isConnected() == false)
			return null;
			
		ShareObject shareObject = null;
		try {
			ObjectInputStream in = getObjectInputStream();
			Debug.message("  in = " + in);
			shareObject = (ShareObject)in.readObject();
			Debug.message("  shareObject = " + shareObject);
			shareObject.readData(in);
			Debug.message("  readData");
			shareObject.update(getSceneGraph());
			Debug.message("  update");
		}
		catch (IOException ioe) {
			Debug.warning("ShareWorldClient.readShareObject - == IOException");
			Debug.warning("  IOException");
			closeSocket();
		}
		catch (ClassNotFoundException cnfe) {
			Debug.warning("ShareWorldClient.readShareObject");
			Debug.warning("  ClassNotFoundException");
			setSocket(null);
		}

		Debug.message("  done");
		
		return shareObject;
	}
	
	////////////////////////////////////////////////
	//	execute
	////////////////////////////////////////////////
	
	public void execute()
	{
		if (isConnected() == false)
			return;

		ShareObject shareObject = readShareObject();
		
		if (shareObject == null)
			return;
			
		shareObject.update(getSceneGraph());
	}
}
