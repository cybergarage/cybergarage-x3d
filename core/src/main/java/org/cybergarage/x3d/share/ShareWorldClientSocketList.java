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

import java.util.*;

public class ShareWorldClientSocketList extends Vector
{
	public ShareWorldClientSocketList()
	{
	}

	public ShareWorldClientSocket getClientSocket(int n)
	{
		return (ShareWorldClientSocket)get(n);
	}

	public ShareWorldClientSocket[] getClientSockets()
	{
		Object obj[] = toArray();
		int nObj = obj.length;
		ShareWorldClientSocket client[] = new ShareWorldClientSocket[nObj];
		for (int n=0; n<nObj; n++)
			client[n] = (ShareWorldClientSocket)obj[n];
		return client;
	}
}
