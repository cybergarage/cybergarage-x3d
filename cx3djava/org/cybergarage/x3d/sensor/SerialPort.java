/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SerialPort.java
*
******************************************************************/

package org.cybergarage.x3d.sensor;

import java.io.*;
import java.util.*;
import javax.comm.*;

public class SerialPort extends Object {

	public final static int SERIALPORT1		= 0;
	public final static int SERIALPORT2		= 1;
	public final static int SERIALPORT3		= 2;
	public final static int SERIALPORT4		= 3;
	
	public final static int DATABITS_5		= javax.comm.SerialPort.DATABITS_5;
	public final static int DATABITS_6		= javax.comm.SerialPort.DATABITS_6;
	public final static int DATABITS_7		= javax.comm.SerialPort.DATABITS_7;
	public final static int DATABITS_8		= javax.comm.SerialPort.DATABITS_8;
	
	public final static int STOPBITS_1		= javax.comm.SerialPort.STOPBITS_1;
	public final static int STOPBITS_2		= javax.comm.SerialPort.STOPBITS_2;
	public final static int STOPBITS_1_5	= javax.comm.SerialPort.STOPBITS_1_5;

	public final static int PARITY_NONE		= javax.comm.SerialPort.PARITY_NONE;
	public final static int PARITY_ODD		= javax.comm.SerialPort.PARITY_ODD;
	public final static int PARITY_EVEN		= javax.comm.SerialPort.PARITY_EVEN;
		
	private javax.comm.SerialPort	mSerialPort = null;
	private OutputStream				mOutputStream = null;
	private InputStream				mInputStream = null;

	/**
	 *	@param deviceName	name of the port to open.  ex. "COM1", "/dev/ttyd1"
	 *	@param baudrate		baudrate of the port.
	 *	<UL>
	 *	9600<BR>
	 *	19200<BR>
	 *	38400<BR>
	 *	....<BR> 
	 *	</UL>
	 *	@param dataBits
	 *  <UL>
     *	DATABITS_5: 5 bits<BR>
	 *	DATABITS_6: 6 bits<BR>
	 *	DATABITS_7: 7 bits<BR>
	 *	DATABITS_8: 8 bits<BR>
	 *  </UL>
	 *	@param stopBits 
	 *	<UL> 
	 *	STOPBITS_1: 1 stop bit<BR>
	 *	STOPBITS_2: 2 stop bits<BR>
	 *	STOPBITS_1_5: 1.5 stop bits<BR>
	 *	</UL>
	 *	@param parity
	 *	<UL> 
	 *	PARITY_NONE: no parity<BR>
	 *	PARITY_ODD: odd parity<BR>
	 *	PARITY_EVEN: even parity<BR>
	 *	</UL>
	 */
	public SerialPort(String deviceName, int baudrate, int dataBits, int stopBits, int parity) {
		open(deviceName, baudrate, dataBits, stopBits, parity);
	}

	/**
	 *	@param device		number of the port to open.  ex. "COM1", "/dev/ttyd1"
	 *	<UL>
	 *	SERIAL1<BR>
	 *	SERIAL2<BR>
	 *	SERIAL3<BR>
	 *	SERIAL4<BR> 
	 *	</UL>
	 *	@param baudrate		baudrate of the port.
	 *	<UL>
	 *	9600<BR>
	 *	19200<BR>
	 *	38400<BR>
	 *	....<BR> 
	 *	</UL>
	 *	@param dataBits
	 *  <UL>
     *	DATABITS_5: 5 bits<BR>
	 *	DATABITS_6: 6 bits<BR>
	 *	DATABITS_7: 7 bits<BR>
	 *	DATABITS_8: 8 bits<BR>
	 *  </UL>
	 *	@param stopBits 
	 *	<UL> 
	 *	STOPBITS_1: 1 stop bit<BR>
	 *	STOPBITS_2: 2 stop bits<BR>
	 *	STOPBITS_1_5: 1.5 stop bits<BR>
	 *	</UL>
	 *	@param parity
	 *	<UL> 
	 *	PARITY_NONE: no parity<BR>
	 *	PARITY_ODD: odd parity<BR>
	 *	PARITY_EVEN: even parity<BR>
	 *	</UL>
	 */
	public SerialPort(int device, int baudrate, int dataBits, int stopBits, int parity) {
		String deviceName = null;
		int nSerialPort = 0;
		for (Enumeration e = CommPortIdentifier.getPortIdentifiers(); e.hasMoreElements() ;) {
			CommPortIdentifier comm = (CommPortIdentifier)e.nextElement();
			if (comm.getPortType() == CommPortIdentifier.PORT_SERIAL) 
				nSerialPort++;
			if (device < nSerialPort) {
				deviceName = comm.getName();
				break;
			}
		}
		if (deviceName != null)
			open(deviceName, baudrate, dataBits, stopBits, parity);
		else
			System.out.println("SerialPort::SerialPort : Couldn't open the serial port !!");
	}

	private void open(String deviceName, int baudrate, int dataBits, int stopBits, int parity) {
		try {
			CommPortIdentifier port = (CommPortIdentifier)CommPortIdentifier.getPortIdentifier(deviceName);
			mSerialPort = (javax.comm.SerialPort)port.open("GenericSerialReader", 5000);
			mSerialPort.setSerialPortParams(baudrate, dataBits, stopBits, parity);
			mSerialPort.setFlowControlMode(javax.comm.SerialPort.FLOWCONTROL_NONE);     
			mSerialPort.enableReceiveThreshold(1);
			mSerialPort.enableReceiveTimeout(3000);        
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void close() {
		try {
			if (mOutputStream != null)
				mOutputStream.close();
			if (mInputStream != null)
				mInputStream.close();
			if (mSerialPort != null);
				mSerialPort.close();
		} catch (IOException e) {};
	}
	
	public void write(String value) {
		if (mOutputStream == null)
			return;
		try {
			mOutputStream.write(value.getBytes(), 0, value.length());
			String str = new String(value.getBytes(), 0, value.length());
			mOutputStream.flush();
		} catch (IOException e) {};
	}

	public String read(int ndata, long timeOut) {
		if (mInputStream == null)
			return null;
		if (ndata <= 0)
			return null;
		
		long	startTime	= System.currentTimeMillis();
		long	spendTime	= 0;
		int	nread			= 0;
		byte	buffer[]		= new byte[ndata];
		
		try {
			while (nread < ndata && spendTime < timeOut) {
				if (0 < nToRead())
					nread = mInputStream.read(buffer);
				spendTime = System.currentTimeMillis() - startTime;
			}
		} catch (IOException e) {};

		return new String(buffer, 0, nread);
	}

	public String read(int ndata) {
		return read(ndata, 3000);
	}
	
	public int nToRead() {
		if (mInputStream == null)
			return 0;
		int nData;
		try{
			nData = mInputStream.available();
		} catch (IOException e) {
			nData = -1;
		};
		return nData;
	}

	public void waitData(int nWaitData) {
		int nData = 0;
		while (true) {
			nData = nToRead();
			if (nWaitData <= nData)
				break;
			waitTime(100);
		}
	}

	public void waitTime(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException ie) {}
	}
	
}
