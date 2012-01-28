/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFVec3f.java
*
******************************************************************/

package org.cybergarage.x3d.field;

import org.cybergarage.x3d.*;

public class SFVec3f extends Field {

	private Vec3fValue mVector = new Vec3fValue(); 

	public SFVec3f() {
		setType(FieldType.SFVEC3F);
		setValue(0.0f, 0.0f, 0.0f);
	}

	public SFVec3f(SFVec3f vector) {
		this();
		setValue(vector);
	}

	public SFVec3f(float x, float y, float z) {
		this();
		setValue(x, y, z);
	}

	public SFVec3f(float value[]) {
		this();
		setValue(value);
	}

	public SFVec3f(String value) {
		this();
		setValue(value);
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mVector) {
			mVector = (Vec3fValue)object;
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

	public void getValue(float value[]) 
	{
		mVector.getValue(value);
	}

	public float[] getValue() 
	{
		return mVector.getValue();
	}

	public float getX() 
	{
		return mVector.getX();
	}

	public float getY() 
	{
		return mVector.getY();
	}

	public float getZ() 
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

	public void setValue(float x, float y, float z, boolean doShare) 
	{
		mVector.setValue(x, y, z);
		if (doShare == true)
			postShareField(this);
	}

	public void setValue(float x, float y, float z) 
	{
		setValue(x, y, z, true);
	}

	public void setValue(float value[]) 
	{
		if (value.length < 3)
			return;
		setValue(value[0], value[1], value[2]);
	}

	public void setValue(SFVec3f vector, boolean doShare) 
	{
		setValue(vector.getX(), vector.getY(), vector.getZ(), doShare);
	}

	public void setValue(SFVec3f vector) 
	{
		setValue(vector, true);
	}

	public void setValue(String string) 
	{
		mVector.setValue(string);
	}

	public void setValue(Field field, boolean doShare) 
	{
		if (field instanceof SFVec3f)
			setValue((SFVec3f)field, doShare);
	}

	public void setValue(Field field) 
	{
		setValue(field, true);
	}

	public void setX(float x) {
		setValue(x, getY(), getZ());
	}

	public void setY(float y) {
		setValue(getX(), y, getZ());
	}

	public void setZ(float z) {
		setValue(getX(), getY(), z);
	}

	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(float x, float y, float z) 
	{
		mVector.add(x, y, z);
	}

	public void add(float value[]) 
	{
		mVector.add(value);
	}

	public void add(SFVec3f value) {
		add(value.getValue());
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(float x, float y, float z) 
	{
		mVector.sub(x, y, z);
	}

	public void sub(float value[]) 
	{
		mVector.sub(value);
	}

	public void sub(SFVec3f value) 
	{
		sub(value.getValue());
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public void scale(float scale) 
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

	public float getScalar()
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
			
		float x = in.readFloat();
		float y = in.readFloat();
		float z = in.readFloat();

		Debug.message("  x = " + x);
		Debug.message("  y = " + y);
		Debug.message("  z = " + z);
		
		setValue(x, y, z);
	}
*/
}