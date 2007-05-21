/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Fastrak.java
*
******************************************************************/

package org.cybergarage.x3d.sensor;

public class Fastrak extends Polhemus {

	public Fastrak(String deviceName, int baudrate) {
		super(deviceName, baudrate);
	}

	public Fastrak(int device, int baudrate) {
		super(device, baudrate);
	}

	public int readActiveReceivers() {
		int nReceiver = 0;
		write(activeStationStateCommand);
		byte data[] = read(9).getBytes();
		if (data.length == 9) {
			for (int n=0; n<4; n++) {
				if (data[n+3] == '1')
			    nReceiver++;	
			}
		}
		return nReceiver;
	}

	public void setReceiverOutputFormat() {
	    for (int n=1; n<=4; n++) {
			String commSetRecvN = "O" + n + ",2,0,4,1\r";
			write(commSetRecvN);
	    }
	}

	public int getDeviceDataLength() {
		return 48;
	}

	public int getDevicePositionDataOffset() {
		return 3;
	}

	public int getDeviceRotationDataOffset() {
		return 25;
	}
	
	public static void main(String args[]) {
		Fastrak frak = new Fastrak(Polhemus.SERIALPORT1, 19200);
		System.out.println("ActiveNReceiver = " + frak.getActiveReceivers());
	    for (int n=0; n<100; n++) {
			float pos[] = new float[3];
			frak.getOrientation(1, pos);
			System.out.println("R1 : " + pos[0] + ", " + pos[1] + ", " + pos[2]);
		};
	}
}
