/******************************************************************
*
*	CyberX3D for Java
*
*	Copyright (C) Satoshi Konno 1997-2002
*
*	File : SFVec3d.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFVec3d extends Field {

	private Vec3dValue mVector = new Vec3dValue(); 

	public SFVec3d() {
		setType(FieldType.SFVEC3D);
		setValue(0.0, 0.0, 0.0);
	}

	public SFVec3d(SFVec3d vector) {
		this();
		setValue(vector);
	}

	public SFVec3d(double x, double y, double z) {
		this();
		setValue(x, y, z);
	}

	public SFVec3d(double value[]) {
		this();
		setValue(value);
	}

	public SFVec3d(String value) {
		this();
		setValue(value);
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mVector) {
			mVector = (Vec3dValue)object;
		}
	}

	public Object getObject() {
		Object object;
		synchronized (mVector) {
			object = mVector;
		}
		return object;
	}
	
	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public void getValue(double value[]) 
	{
		mVector.getValue(value);
	}

	public double[] getValue() 
	{
		return mVector.getValue();
	}

	public double getX() 
	{
		return mVector.getX();
	}

	public double getY() 
	{
		return mVector.getY();
	}

	public double getZ() 
	{
		return mVector.getZ();
	}

	public int getValueCount()
	{
		return 3;
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(double x, double y, double z, boolean doShare) 
	{
		mVector.setValue(x, y, z);
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(double x, double y, double z) 
	{
		setValue(x, y, z, true);
	}

	public void setValue(double value[]) 
	{
		if (value.length < 3)
			return;
		setValue(value[0], value[1], value[2]);
	}

	public void setValue(SFVec3d vector, boolean doShare) 
	{
		setValue(vector.getX(), vector.getY(), vector.getZ(), doShare);
	}

	public void setValue(SFVec3d vector) 
	{
		setValue(vector, true);
	}

	public void setValue(String string) 
	{
		mVector.setValue(string);
	}

	public void setValue(Field field, boolean doShare) 
	{
		if (field instanceof SFVec3d)
			setValue((SFVec3d)field, doShare);
	}

	public void setValue(Field field) 
	{
		setValue(field, true);
	}

	public void setX(double x) {
		setValue(x, getY(), getZ());
	}

	public void setY(double y) {
		setValue(getX(), y, getZ());
	}

	public void setZ(double z) {
		setValue(getX(), getY(), z);
	}

	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(double x, double y, double z) 
	{
		mVector.add(x, y, z);
	}

	public void add(double value[]) 
	{
		mVector.add(value);
	}

	public void add(SFVec3d value) {
		add(value.getValue());
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(double x, double y, double z) 
	{
		mVector.sub(x, y, z);
	}

	public void sub(double value[]) 
	{
		mVector.sub(value);
	}

	public void sub(SFVec3d value) 
	{
		sub(value.getValue());
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public void scale(double scale) 
	{
		mVector.scale(scale);
	}

	////////////////////////////////////////////////
	//	invert
	////////////////////////////////////////////////

	public void invert() 
	{
		mVector.invert();
	}

	////////////////////////////////////////////////
	//	scalar
	////////////////////////////////////////////////

	public double getScalar()
	{
		return mVector.getScalar();
	}

	////////////////////////////////////////////////
	//	normalize
	////////////////////////////////////////////////

	public void normalize()
	{
		mVector.normalize();
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return getX() + " " + getY() + " " + getZ();
	}

	////////////////////////////////////////////////
	//	Serialize
	////////////////////////////////////////////////

/*
	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		Debug.message("writeObject");

//		synchronized (mValue) {
			out.writeFloat(mValue[0]);
			out.writeFloat(mValue[1]);
			out.writeFloat(mValue[2]);
			Debug.message("  x = " + mValue[0]);
			Debug.message("  y = " + mValue[1]);
			Debug.message("  z = " + mValue[2]);
//		}
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		Debug.message("readObject");
			
		double x = in.readFloat();
		double y = in.readFloat();
		double z = in.readFloat();

		Debug.message("  x = " + x);
		Debug.message("  y = " + y);
		Debug.message("  z = " + z);
		
		setValue(x, y, z);
	}
*/
}