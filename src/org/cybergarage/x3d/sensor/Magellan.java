/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1999
*
*	File : Magellan.java
*
******************************************************************/

package org.cybergarage.x3d.sensor;

public class Magellan extends SerialPort implements Runnable {

	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;

	private static final String zeroingCommand			= "z\r";
	private static final String dataRateSetupCommand	= "p00\r";
	private static final String isDataRateCommand		= "pQ\r";
	private static final String dataRequestCommand		= "dQ\r";
	private static final String versionCommand			= "vQ\r";
	private static final String setModeCommand			= "m3\r";
	private static final String isModeCommand				= "mQ\r";
	private static final String keyboardCommand			= "kQ\r";

	public static final int BUTTON1 = 0x0001;
	public static final int BUTTON2 = 0x0002;
	public static final int BUTTON3 = 0x0004;
	public static final int BUTTON4 = 0x0008;
	public static final int BUTTON5 = 0x0010;
	public static final int BUTTON6 = 0x0020;
	public static final int BUTTON7 = 0x0040;
	public static final int BUTTON8 = 0x0080;
	public static final int BUTTONA = 0x0100;

	private Thread 	thread;
	private boolean	isRunning;
		
	public RawData	mRawData = new RawData();
	/**
	 *	@param deviceName	name of the port to open.  ex. "COM1", "/dev/ttyd1"
	 *	@see org.cybergarage.x3d.sensor.SerialPort#SerialPort(String deviceName, int baudrate, int dataBits, int stopBits, int parity)
	 */
	public Magellan(String deviceName) {
		super(deviceName, 9600, DATABITS_8, STOPBITS_2, PARITY_NONE);
		initialize();
	}

	/**
	 *	@param device		number of the port to open.  ex. "COM1", "/dev/ttyd1"
	 *	<UL>
	 *	SERIAL1<BR>
	 *	SERIAL2<BR>
	 *	SERIAL3<BR>
	 *	SERIAL4<BR> 
	 *	</UL>
	 *	@see org.cybergarage.x3d.sensor.SerialPort#SerialPort(int serialport, int baudrate, int dataBits, int stopBits, int parity)
	 */
	public Magellan(int device) {
		super(device, 9600, DATABITS_8, STOPBITS_2, PARITY_NONE);
		initialize();
	}

	private void initialize() {
		flushReadBuffer();
		write(zeroingCommand);		read(2);
		write(dataRateSetupCommand);
		write(setModeCommand);		read(3);
	}

	private byte []toHex(byte data[]) {
		for (int n=0; n<data.length; n++) {
			switch (data[n]) {
			case 'A': data[n] = (byte)'1'; break;
			case 'B': data[n] = (byte)'2'; break;
			case 'D': data[n] = (byte)'4'; break;
			case 'G': data[n] = (byte)'7'; break;
			case 'H': data[n] = (byte)'8'; break;
			case ':': data[n] = (byte)'A'; break;
			case 'K': data[n] = (byte)'B'; break;
			case '<': data[n] = (byte)'C'; break;
			case 'M': data[n] = (byte)'D'; break;
			case 'N': data[n] = (byte)'E'; break;
			case '?': data[n] = (byte)'F'; break;
			}
		}
		return data;
	}
	
	private int toInteger(byte hexData) {
		switch (hexData) {
		case '0': return 0;
		case '1': return 1;
		case '2': return 2;
		case '3': return 3;
		case '4': return 4;
		case '5': return 5;
		case '6': return 6;
		case '7': return 7;
		case '8': return 8;
		case '9': return 9;
		case 'A': return 10;
		case 'B': return 11;
		case 'C': return 12;
		case 'D': return 13;
		case 'E': return 14;
		case 'F': return 15;
		}
		return 0;
	}
	
	private int parseInt(byte data[], int offset, int length) {
		int value = 0;
		for (int n=0; n<length; n++)
			value += toInteger(data[offset+n]) * (1 << ((length - n - 1)*4));
//			value += toInteger(data[offset+n]) * (int)Math.pow(2.0, (double)((length - n - 1)*4));
		return value;
	}
	
	private void updateData() {
		byte data[];
		
		waitData(1);
		data = read(1).getBytes();
		
		switch (data[0]) {
		case 'k':
			{
				waitData(4);	
				data = toHex(read(4, 60).getBytes());
				synchronized (mRawData) {
					mRawData.button = 0;
					for (int n = 0; n<4; n++) {
						if ((toInteger(data[0]) & (1 << n)) != 0)
							mRawData.button |= (1 << n);
						if ((toInteger(data[1]) & (1 << n)) != 0)
							mRawData.button |= (1 << (n+4));
					}
					if ((toInteger(data[2]) & 0x01) != 0)
						mRawData.button |= BUTTONA;
				}				
			}
			break;
		case 'd':
			{
				waitData(25);	
				data = toHex(read(25, 60).getBytes());
				//String strData = new String(data, 0, 24);
				//System.out.println(strData);
				synchronized (mRawData) {
					mRawData.trans[X] = parseInt(data,  0, 4) - 32768;
					mRawData.trans[Y] = parseInt(data,  4, 4) - 32768;
					mRawData.trans[Z] = parseInt(data,  8, 4) - 32768;
					mRawData.rot[X]   = parseInt(data, 12, 4) - 32768;
					mRawData.rot[Y]   = parseInt(data, 16, 4) - 32768;
					mRawData.rot[Z]   = parseInt(data, 20, 4) - 32768;
				}
			}
			break;
		}
		
		//System.out.println(mRawData);
	}

	private void flushReadBuffer() {
		write(versionCommand);
		waitTime(500);
		int nData = nToRead();
		while (0 < nData) {
			String data = read(nData);
			nData = nToRead();
		}
	}

	public void getTranslation(int trans[]) {
		synchronized (mRawData) {
			trans[X] = mRawData.trans[X];
			trans[Y] = mRawData.trans[Y];
			trans[Z] = mRawData.trans[Z];
		}
	}

	public void getRotation(float rot[]) {
		synchronized (mRawData) {
			rot[X] = (float)mRawData.rot[X] / 360.0f * (float)Math.PI;
			rot[Y] = (float)mRawData.rot[Y] / 360.0f * (float)Math.PI;
			rot[Z] = (float)mRawData.rot[Z] / 360.0f * (float)Math.PI;
		}
	}

	public int getButton() {
		int button;
		synchronized (mRawData) {
			button = mRawData.button;
		}
		return button;
	}
	
/*
	public void write(String data) {
		System.out.println("write = \"" + data + "\"");
		super.write(data);
	}

	public String read(int ndata) {
		String data = super.read(ndata);
		System.out.println("read  = \"" + data + "\"");
		return data; 
	}

	public String read(int ndata, long timeOut) {
		String data = super.read(ndata, timeOut);
		System.out.println("read  = \"" + data + "\"");
		return data; 
	}
*/

	public void start() {
		isRunning = true;
		if (thread == null)
			thread = new Thread(this); 
		thread.start();
	}
	
	public void stop() {
		isRunning = false;
		if (thread != null) {
			//thread.interrupt();
			//thread.stop();
			thread = null;
		}
	}

	public void run() {
		while (isRunning == true)
			updateData();
	}

	private class RawData extends Object {
		public	int trans[]	= new int[3];
		public	int rot[]	= new int[3];
		public	int button;
		
		public RawData() {
			trans[X] = 0;
			trans[Y] = 0;
			trans[Z] = 0;
			rot[X] = 0;
			rot[Y] = 0;
			rot[Z] = 0;
			button = 0;
		}
		
		public String toString() {
			String msg = trans[X] + " " + trans[Y] + " " + trans[Z] + " "  + rot[X] + " " + rot[Y] + " " + rot[Z] + " " + Integer.toHexString(button);
			return msg;
		}
	}
}	

