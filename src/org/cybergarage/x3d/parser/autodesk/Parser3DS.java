/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1999
*
*	File : 3DSParser.java
*
******************************************************************/

package org.cybergarage.x3d.parser.autodesk;

import java.io.*;

import org.cybergarage.x3d.node.*;
import org.cybergarage.x3d.util.Debug;

public class Parser3DS extends Object implements Parser3DSConstants {

	///////////////////////////////////////////////
	//	 Constructor
	///////////////////////////////////////////////
	
	public Parser3DS() {
		setRootGroupNode(new GroupNode());
		setMaterialRootNode(new GroupNode());
		setCurrentShapeNode(null);
		setCurrentMaterialNode(null);
	}

	///////////////////////////////////////////////
	//	GroupNode 
	///////////////////////////////////////////////
	
	private GroupNode			mRootGroupNode;
	
	private void setRootGroupNode(GroupNode node) {
		mRootGroupNode = node;
	}

	public GroupNode getRootGroupNode() {
		return mRootGroupNode;
	}

	///////////////////////////////////////////////
	//	Material Root Node 
	///////////////////////////////////////////////
	
	private GroupNode			mMaterialRootNode;
	
	private void setMaterialRootNode(GroupNode node) {
		mMaterialRootNode = node;
	}

	private GroupNode getMaterialRootNode() {
		return mMaterialRootNode;
	}
	
	private void addMaterialNode(MaterialNode matNode) {
		getMaterialRootNode().addChildNode(matNode);
	}

	private MaterialNode getMaterialNode(String name) {
		if (name == null)
			return null;
		for (MaterialNode matNode = (MaterialNode)getMaterialRootNode().getChildNodes(); matNode != null; matNode = (MaterialNode)matNode.next()) {
			if (name.equalsIgnoreCase(matNode.getName()) == true)
				return matNode;
		}
		return null;
	}
	
	///////////////////////////////////////////////
	//	Current Node 
	///////////////////////////////////////////////
	
	private MaterialNode	mCurrentMaterialNode;
	private ShapeNode			mCurrentShapeNode;
	
	
	private void setCurrentMaterialNode(MaterialNode node) {
		mCurrentMaterialNode = node;
	}

	private MaterialNode getCurrentMaterialNode() {
		return mCurrentMaterialNode;
	}

	private void setCurrentShapeNode(ShapeNode node) {
		mCurrentShapeNode = node;
	}

	private ShapeNode getCurrentShapeNode() {
		return mCurrentShapeNode;
	}

	private AppearanceNode getCurrentAppearanceNode() {
		return getCurrentShapeNode().getAppearanceNodes();
	}

	private IndexedFaceSetNode getCurrentIndexedFaceSetNode() {
		return getCurrentShapeNode().getIndexedFaceSetNodes();
	}

	private CoordinateNode getCurrentCoordinateNode() {
		return getCurrentIndexedFaceSetNode().getCoordinateNodes();
	}

	private NormalNode getCurrentNormalNode() {
		return getCurrentIndexedFaceSetNode().getNormalNodes();
	}

	///////////////////////////////////////////////
	//	Load 
	///////////////////////////////////////////////

	private float	color[]	= new float[3];
	private float	shininess;
			
	public boolean load(InputStream inputStream) {
		try {
			DataInputStream dataIn = new DataInputStream(inputStream);
			while (true) {
				int id	= readUnsignedShort(dataIn);
				int len	= readInt(dataIn);
				readChunk(dataIn, id, len);
			}
		}
		catch (EOFException eofe) {
		}
		catch (IOException ioe) {
			Debug.warning("Parser3DS.load");
			Debug.warning("\tIOException");
			return false;
		}
		return true;
	}

	private void readChunk(DataInputStream dataIn, int id, int len) throws IOException {
		switch (id) {
		case ID_3DS_FILE:
			Debug.message("ID_3DS_FILE");
			return;
		case ID_EDIT3DS:
			Debug.message("ID_EDIT3DS");
			break;
		
		//// MATERIAL /////////////////////////////////////////////
		case ID_MATERIAL:
			{
				Debug.message("ID_MATERIAL");
				MaterialNode matNode = new MaterialNode();
				matNode.setAmbientIntensity(0.0f);
				addMaterialNode(matNode);
				setCurrentMaterialNode(matNode);
			}
			break;
		case ID_MATERIAL_NAME:
			{
				Debug.message("ID_MATERIAL_NAME");
				String matName = readString(dataIn);
				Debug.message("\tname = " + matName);
				getCurrentMaterialNode().setName(matName);
			}
			break;
		case ID_MATERIAL_AMBIENT: 
			{
				readColor(dataIn, color);
				Debug.message("ID_MATERIAL_AMBIENT = (" + color[0] + ", " + color[1] + ", " + color[2] + ")");
				getCurrentMaterialNode().setEmissiveColor(color);
			}
			break;
		case ID_MATERIAL_DIFFUSE: 
			{
				readColor(dataIn, color);
				Debug.message("ID_MATERIAL_DIFFUSE = (" + color[0] + ", " + color[1] + ", " + color[2] + ")");
				getCurrentMaterialNode().setDiffuseColor(color);
			}
			break;
		case ID_MATERIAL_SPECULAR: 
			{
				readColor(dataIn, color);
				Debug.message("ID_MATERIAL_SPECULAR = (" + color[0] + ", " + color[1] + ", " + color[2] + ")");
				getCurrentMaterialNode().setSpecularColor(color);
			}
			break;
		case ID_MATERIAL_SHININESS:
			{
				shininess = readPercentage(dataIn);
				Debug.message("ID_MATERIAL_SHININESS = " + shininess);
			}
			break;
		case ID_MAT_SHININESS_STRENGTH:
			{
				float shininessStrength = readPercentage(dataIn);
				Debug.message("ID_MATERIAL_SHININESS = " + shininessStrength);
				float matShiness = (1.0f - ((shininess + shininessStrength) / 2.0f)) * 128;
				Debug.message("\tshininess = " + matShiness);
				getCurrentMaterialNode().setShininess(matShiness);
			}
			break;
		case ID_MATERIAL_TRANSPARENCY:
			{
				getCurrentMaterialNode().setTransparency(readPercentage(dataIn));
			}
			break;

		//// OBJECT ///////////////////////////////////////////////
		case ID_NAMED_OBJECT:
			{
				Debug.message("ID_NAMED_OBJECT");
				String objName = readString(dataIn);
				Debug.message("\tname = " + objName);
				ShapeNode	shapeNode	= new ShapeNode();
				shapeNode.setName(objName);
				shapeNode.addChildNode(new AppearanceNode());
				getRootGroupNode().addChildNode(shapeNode);
				setCurrentShapeNode(shapeNode);
			}
			break;
		case ID_TRIANGLE_SET:
			{
				Debug.message("ID_TRIANGLE_SET");
				IndexedFaceSetNode idxFaceSetNode = new IndexedFaceSetNode();
				idxFaceSetNode.addChildNode(new CoordinateNode());
				//idxFaceSetNode.addChildNode(new NormalNode());
				getCurrentShapeNode().addChildNode(idxFaceSetNode);
			}
			break;
		case ID_POINT_LIST:
			{
				Debug.message("ID_POINT_LIST");
				int nPoints = readUnsignedShort(dataIn);
				Debug.message("\tnPoints = " + nPoints);
				CoordinateNode coordNode = getCurrentCoordinateNode();
				for(int n=0; n<nPoints; n++) {
					float x = readFloat(dataIn);
					float y = readFloat(dataIn);
					float z = readFloat(dataIn);
					coordNode.addPoint(x, y, z);
					Debug.message("\t\t" + x + ", " + y + ", " + z);
				}
			}
			break;
		case ID_FACE_LIST:
			{
				Debug.message("ID_FACE_LIST");
				int nIndices = readUnsignedShort(dataIn);
				Debug.message("\tnIndices = " + nIndices);
				IndexedFaceSetNode idxFaceSetNode = getCurrentIndexedFaceSetNode();
				for(int n=0; n<nIndices; n++) {
					int idx1 = readUnsignedShort(dataIn);
					int idx2 = readUnsignedShort(dataIn);
					int idx3 = readUnsignedShort(dataIn);
					idxFaceSetNode.addCoordIndex(idx1);
					idxFaceSetNode.addCoordIndex(idx2);
					idxFaceSetNode.addCoordIndex(idx3);
					idxFaceSetNode.addCoordIndex(-1);
					Debug.message("\t\t" + idx1 + ", " + idx2 + ", " + idx3);
					readUnsignedShort(dataIn);
        	}
        }
        break;
		case ID_FACE_MATERIAL:
			{
				Debug.message("ID_FACE_MATERIAL");
				String matName = readString(dataIn);
				Debug.message("\tname = " + matName);
				MaterialNode matNode = getMaterialNode(matName);
				if (matNode != null) 
					getCurrentAppearanceNode().addChildNode(new MaterialNode(matNode));
				int nCounts = readUnsignedShort(dataIn);
				for (int n=0; n<nCounts; n++) 
					readUnsignedShort(dataIn);
			}
			break;
		default:
			Debug.message("ID : 0x" + Integer.toHexString(id) + "  LEN : " + len);
			skipChunk(dataIn, len);
		}			
	}

	///////////////////////////////////////////////
	//	Read 
	///////////////////////////////////////////////
	
	private int readUnsignedByte(DataInputStream dataIn) throws IOException {
		return dataIn.readUnsignedByte();
	}
	
	private int readUnsignedShort(DataInputStream dataIn) throws IOException {
		int value = dataIn.readUnsignedShort();
		return ((value << 8) & 0xFF00) | ((value >> 8) & 0x00FF);
	}
	
	private int readInt(DataInputStream dataIn) throws IOException {
		int value = dataIn.readInt();
		return	((value << 24) & 0xFF000000) |
     				((value << 8)  & 0x00FF0000) |
					((value >> 8)  & 0x0000FF00) |
					((value >> 24) & 0x000000FF);
	}

	private float readFloat(DataInputStream dataIn) throws IOException {
		return Float.intBitsToFloat(readInt(dataIn));
	}

	private String readString(DataInputStream dataIn) throws IOException {
		StringBuffer sb = new StringBuffer();
		byte c = dataIn.readByte();
		while(c != (byte)0) {
			sb.append((char)c);
			c = dataIn.readByte();
		}
		return sb.toString();
	}

	private void readColor(DataInputStream dataIn, float color[]) throws IOException {
		int type	= readUnsignedShort(dataIn);
		int len		= readInt(dataIn);
		switch (type) {
		case ID_COLOR_FLOAT:
			color[0] = readFloat(dataIn);
			color[1] = readFloat(dataIn);
			color[2] = readFloat(dataIn);
			break;
		case ID_COLOR_24:
			color[0] = (float)readUnsignedByte(dataIn) / 255.0f;
			color[1] = (float)readUnsignedByte(dataIn) / 255.0f;
			color[2] = (float)readUnsignedByte(dataIn) / 255.0f;
			break;
		default:
			Debug.warning("Unknown Color Type : 0x"+ Integer.toHexString(type));
		}
	}

	private float readPercentage(DataInputStream dataIn) throws IOException {
		int type	= readUnsignedShort(dataIn);
		int len		= readInt(dataIn);
		switch (type) {
		case ID_INT_PERCENTAGE:
			return (float)readUnsignedShort(dataIn) / 100;
		case ID_FLOAT_PERCENTAGE:
			return readFloat(dataIn);
		default:
			Debug.warning("Unknown Percentage Type : 0x"+ Integer.toHexString(type));
		}
		return 0.0f;
	}

	private void skipChunk(DataInputStream dataIn, int len) throws IOException {
		int nSkipBytes = len - 6;
		if (0 < nSkipBytes)
			dataIn.skipBytes(nSkipBytes);
	}
}
