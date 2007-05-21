/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : IS300.java
*
******************************************************************/

package org.cybergarage.x3d.sensor;

public class IS300 extends Polhemus {

	public IS300(String deviceName, int baudrate) {
		super(deviceName, baudrate);
	}

	public IS300(int device, int baudrate) {
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
	}

	public int getDeviceDataLength() {
		return 47;
	}

	public int getDevicePositionDataOffset() {
		return 3;
	}

	public int getDeviceRotationDataOffset() {
		return 24;
	}
	
	public static void main(String args[]) {
		IS300 frak = new IS300(Polhemus.SERIALPORT1, 115200);
		System.out.println("ActiveNReceiver = " + frak.getActiveReceivers());
	    for (int n=0; n<100; n++) {
			float pos[] = new float[3];
			frak.getOrientation(1, pos);
			System.out.println("R1 : " + pos[0] + ", " + pos[1] + ", " + pos[2]);
		};
	}
}
