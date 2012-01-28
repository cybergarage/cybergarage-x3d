/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Isotrak2.java
*
******************************************************************/

package org.cybergarage.x3d.sensor;

public class Isotrak2 extends Polhemus {

	public Isotrak2(String deviceName, int baudrate) {
		super(deviceName, baudrate);
	}

	public Isotrak2(int device, int baudrate) {
		super(device, baudrate);
	}

	public int readActiveReceivers() {
		int nReceiver = 0;
		write(activeStationStateCommand);
		byte data[] = read(7).getBytes();
		if (data.length == 7) {
			for (int n=0; n<4; n++) {
				if (data[n+3] == '1')
			    nReceiver++;	
			}
		}
		return nReceiver;
	}

	public void setReceiverOutputFormat() {
		String commSetRecvN = "O4,2\r";
		write(commSetRecvN);
	}

	public int getDeviceDataLength() {
		return 45;
	}

	public int getDevicePositionDataOffset() {
		return 3;
	}

	public int getDeviceRotationDataOffset() {
		return 25;
	}
	
	public static void main(String args[]) {
		Isotrak2 frak = new Isotrak2(Polhemus.SERIALPORT1, 19200);
		System.out.println("ActiveNReceiver = " + frak.getActiveReceivers());
	    for (int n=0; n<20; n++) {
			float pos[] = new float[3];
			frak.getPosition(1, pos);
			System.out.println("R1 : " + pos[0] + ", " + pos[1] + ", " + pos[2]);
		};
	}
}
