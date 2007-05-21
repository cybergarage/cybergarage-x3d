/******************************************************************
*
*	CyberVRML97 for Java3D
*
*	Copyright (C) Satoshi Konno 1997-2000
*
*	File:	ShareWorldClientSocket.java
*
******************************************************************/

package org.cybergarage.x3d.share;

import java.net.*;

public class ShareWorldClientSocket extends ShareWorldClient
{
	private ShareWorldServer	worldServer		= null;
	
	public ShareWorldClientSocket(ShareWorldServer server, Socket socket)
	{
		setServer(server);
		setSocket(socket);
	}

	////////////////////////////////////////////////
	//	Member
	////////////////////////////////////////////////

	public void setServer(ShareWorldServer server)
	{
		worldServer = server;
	}

	public ShareWorldServer getServer()
	{
		return worldServer;
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

		getServer().distributeShareObject(this, shareObject);
	}

}
