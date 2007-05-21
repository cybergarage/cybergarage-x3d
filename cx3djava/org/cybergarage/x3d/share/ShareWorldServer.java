/******************************************************************
*
*	CyberVRML97 for Java3D
*
*	Copyright (C) Satoshi Konno 1997-2000
*
*	File:	ShareWorldServer.java
*
******************************************************************/

package org.cybergarage.x3d.share;

import java.io.*;
import java.net.*;

import org.cybergarage.x3d.util.*;

public class ShareWorldServer extends ShareWorld
{
	public ShareWorldServer(int port)
	{
		ServerSocket serverSocket = createServerSocket(port);
		setServerSocket(serverSocket);
	}

	public ShareWorldServer()
	{
		this(getDefaultSeverSocketPort());
	}

	////////////////////////////////////////////////
	//	ServerSocket
	////////////////////////////////////////////////

	private ServerSocket serverSocket;

	private ServerSocket createServerSocket(int port)
	{
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(port);
		}
		catch (IOException ioe) {};
		
		return serverSocket;
	}		
	
	private void setServerSocket(ServerSocket socket)
	{
		serverSocket = socket;
	}

	public ServerSocket getServerSocket()
	{
		return serverSocket;
	}

	////////////////////////////////////////////////
	//	ShareWorldClientSocket
	////////////////////////////////////////////////

	ShareWorldClientSocketList clientSocketList = new ShareWorldClientSocketList();
	
	public int getNClientSockets()
	{
		return clientSocketList.size();
	}

	public void addClientSocket(ShareWorldClientSocket clientSocket)
	{
		clientSocketList.add(clientSocket);
	}

	public void removeClientSocket(ShareWorldClientSocket clientSocket)
	{
		clientSocketList.remove(clientSocket);
	}

	public ShareWorldClientSocket getClientSocket(int n)
	{
		return clientSocketList.getClientSocket(n);
	}

	public ShareWorldClientSocket[] getClientSockets()
	{
		return clientSocketList.getClientSockets();
	}

	////////////////////////////////////////////////
	//	distributeShareObject
	////////////////////////////////////////////////
	
	public void distributeShareObject(ShareWorldClientSocket srcClient, ShareObject shareObject)
	{
		ShareWorldClientSocket clientSocket[] = getClientSockets();
		int nCliendSockets = clientSocket.length;
		for (int n=0; n<nCliendSockets; n++) {
			ShareWorldClientSocket client = clientSocket[n];
			if (client == srcClient) 
				continue;
			if (client.postShreObject(shareObject) == false)
				removeClientSocket(client);
		}
	}
	
	////////////////////////////////////////////////
	//	execute
	////////////////////////////////////////////////

	public void execute()
	{
		ServerSocket serverSocket = getServerSocket();
		
		try {
			Debug.message("accepting .... ");
				
			Socket socket = serverSocket.accept();
				
			Debug.message("  InetAddress = " + socket.getInetAddress());
			Debug.message("  port = " + socket.getPort());
				
			ShareWorldClientSocket clientSocket = new ShareWorldClientSocket(this, socket);
			addClientSocket(clientSocket);
			
			clientSocket.executeThread();
		}
		catch (IOException ioe) {}
	}
}
