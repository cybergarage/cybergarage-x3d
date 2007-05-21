/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1999
*
*	File : Parser3DSConstants.java
*
******************************************************************/

package org.cybergarage.x3d.parser.autodesk;

public interface ParserDXFConstants {

	static final String	ID[] = {
		"0",	
		"2",
		"8",
		"999",
		"SECTION",
		"ENDSEC",
		"ENTITIES",
		"HEADER",
		"3DFACE",
		"EOF",
		"62",
		"10",
		"20",
		"30",
		"11",
		"21",
		"31",
		"12",
		"22",
		"32",
		"13",
		"23",
		"33",
		"$ACADVER",
		"$ANGBASE",
		"$ANGDIR",
		"$ATTDIA",
		"$ATTMODE",
		"$ATTREQ",
		"$AUNITS",
		"$AUPREC",
		"$AXISMODE",
		"$AXISUNIT",
		"$BLIPMODE",
		"$CECOLOR",
		"$CELTYPE",
		"$CHAMFERA",
		"$CHAMFERB",
		"$CLAYER",
		"$COORDS",
		"$DIMALT",
		"$DIMALTD",
		"$DIMALTF",
		"$DIMAPOST",
		"$DIMASO",
		"$DIMASZ",
		"$DIMBLK",
		"$DIMBLK1",
		"$DIMBLK2",
		"$DIMCEN",
		"$DIMCLRD",
		"$DIMCLRE",
		"$DIMCLRT",
		"$DIMDLE",
		"$DIMDLI",
		"$DIMEXE",
		"$DIMEXO",
		"$DIMGAP",
		"$DIMLFAC",
		"$DIMLIM",
		"$DIMPOST",
		"$DIMRND",
		"$DIMSAH",
		"$DIMSCALE",
		"$DIMSE1",
		"$DIMSE2",
		"$DIMSHO",
		"$DIMSOXD",
		"$DIMSTYLE",
		"$DIMTFAC",
		"$DIMTIH",
		"$DIMTIX",
		"$DIMTM",
		"$DIMTOFL",
		"$DIMTOH",
		"$DIMTOL",
		"$DIMTP",
		"$DIMTSZ",
		"$DIMTVP",
		"$DIMTXT",
		"$DIMZIN",
		"$DRAGMODE",
		"$ELEVATION",
		"$EXTMAX",
		"$EXTMIN",
		"$FILLETRAD",
		"$FILLMODE",
		"$HANGLING",
		"$HANDSEED",
		"$INBASE",
		"$LIMCHECK",
		"$LIMMAX",
		"$LIMMIN",
		"$LTSCALE",
		"$LUNITS",
		"$LUPREC",
		"$MAXACTVP",
		"$MENU",
		"$DIMTAD",
		"$MIRRTEXT",
		"$ORTHOMODE",
		"$OSMODE",
		"$PDMODE",
		"$PDSIZE",
		"$PELEVATION",
		"$PEXTMAX",
		"$PEXTMIN",
		"$PLINCHECK",
		"$PLIMMAX",
		"$PLIMMIN",
		"$PLINEWID",
		"$PUCSNAME",
		"$PUCSORD",
		"$PUCSXDIR",
		"$PUCSYDIR",
		"$QTEXTMODE",
		"$REGENMODE",
		"$SHADEDGE",
		"$SHADEDIF",
		"$SKETCHINC",
		"$SKPOLY",
		"$SPLFRAME",
		"$SPLINESEGS",
		"$SPLINETYPE",
		"$SURFTAB1",
		"$SURFTAB2",
		"$SURTYPE",
		"$SURFU",
		"$SURFV",
		"$TDCREATE",
		"$TDINDWG",
		"$TDUPDATE",
		"$TDUSRTIMER",
		"$TEXTSIZE",
		"$TEXTSTYLE",
		"$THICKNESS",
		"$TILEMODE",
		"$TRACEWID",
		"$UCSNAME",
		"$UCSORG",
		"$UCSDIR",
		"$UCSYDIR",
		"$UNITMODE",
		"$USERI1",
		"$USERR1",
		"$USERTIMER",
		"$WORLDVIEW",
		"$FASTZOOM",
		"$GRIDMODE",
		"$GRIDUNIT",
		"$SNAPANG",
		"$SNAPBASE",
		"$SNAPISOPAIR",
		"$SNAPMODE",
		"$SNAPSTYLE",
		"$SNAPUNIT",
		"$VIEWCTR",
		"$VIEWDIR",
		"$VIEWSIZE",
		"SOLID",
		"39",
		"210",
		"220",
		"230",
		"100",
		"POLYLINE",
		"VERTEX",
		"70",
		"40",
		"41",
		"71",
		"72",
		"73",
		"74",
		"75",
		"66",
		"SEQEND",
		"50",
		"BLOCKS",
		"ENDBLK",
		"BLOCK",
		"ATTDEF",
		"6",
		"1",
		"2",
		"51",
		"7",
		"LINE",
	};

	static final int	GROUP0		= 0;	
	static final int	GROUP2		= 1;	
	static final int	LAYER			= 2;	
	static final int	COMMENT		= 3;	
	static final int	SECTION		= 4;	
	static final int	ENDSEC		= 5;	
	static final int	ENTITIES		= 6;	
	static final int	HEADER		= 7;	
	static final int	FACE3D		= 8;	
	static final int	EOF			= 9;	
	static final int	IDX_COLOR	= 10;	
	static final int	VERTEX0_X	= 11;	
	static final int	VERTEX0_Y	= 12;	
	static final int	VERTEX0_Z	= 13;	
	static final int	VERTEX1_X	= 14;	
	static final int	VERTEX1_Y	= 15;	
	static final int	VERTEX1_Z	= 16;	
	static final int	VERTEX2_X	= 17;	
	static final int	VERTEX2_Y	= 18;	
	static final int	VERTEX2_Z	= 19;	
	static final int	VERTEX3_X	= 20;	
	static final int	VERTEX3_Y	= 21;	
	static final int	VERTEX3_Z	= 22;	
	static final int	HEADER_ACADVER		= 23;
	static final int	HEADER_ANGBASE		= 24;
	static final int	HEADER_ANGDIR		= 25;
	static final int	HEADER_ATTDIA		= 26;
	static final int	HEADER_ATTMODE		= 27;
	static final int	HEADER_ATTREQ		= 28;
	static final int	HEADER_AUNITS		= 29;
	static final int	HEADER_AUPREC		= 30;
	static final int	HEADER_AXISMODE		= 31;
	static final int	HEADER_AXISUNIT		= 32;
	static final int	HEADER_BLIPMODE		= 33;
	static final int	HEADER_CECOLOR		= 34;
	static final int	HEADER_CELTYPE		= 35;
	static final int	HEADER_CHAMFERA		= 36;
	static final int	HEADER_CHAMFERB		= 37;
	static final int	HEADER_CLAYER		= 38;
	static final int	HEADER_COORDS		= 39;
	static final int	HEADER_DIMALT		= 40;
	static final int	HEADER_DIMALTD		= 41;
	static final int	HEADER_DIMALTF		= 42;
	static final int	HEADER_DIMAPOST		= 43;
	static final int	HEADER_DIMASO		= 44;
	static final int	HEADER_DIMASZ		= 45;
	static final int	HEADER_DIMBLK		= 46;
	static final int	HEADER_DIMBLK1		= 47;
	static final int	HEADER_DIMBLK2		= 48;
	static final int	HEADER_DIMCEN		= 49;
	static final int	HEADER_DIMCLRD		= 50;
	static final int	HEADER_DIMCLRE		= 51;
	static final int	HEADER_DIMCLRT		= 52;
	static final int	HEADER_DIMDLE		= 53;
	static final int	HEADER_DIMDLI		= 54;
	static final int	HEADER_DIMEXE		= 55;
	static final int	HEADER_DIMEXO		= 56;
	static final int	HEADER_DIMGAP		= 57;
	static final int	HEADER_DIMLFAC		= 58;
	static final int	HEADER_DIMLIM		= 59;
	static final int	HEADER_DIMPOST		= 60;
	static final int	HEADER_DIMRND		= 61;
	static final int	HEADER_DIMSAH		= 62;
	static final int	HEADER_DIMSCALE		= 63;
	static final int	HEADER_DIMSE1		= 64;
	static final int	HEADER_DIMSE2		= 65;
	static final int	HEADER_DIMSHO		= 66;
	static final int	HEADER_DIMSOXD		= 67;
	static final int	HEADER_DIMSTYLE		= 68;
	static final int	HEADER_DIMTFAC		= 69;
	static final int	HEADER_DIMTIH		= 70;
	static final int	HEADER_DIMTIX		= 71;
	static final int	HEADER_DIMTM			= 72;
	static final int	HEADER_DIMTOFL		= 73; 
	static final int	HEADER_DIMTOH		= 74;
	static final int	HEADER_DIMTOL		= 75;
	static final int	HEADER_DIMTP			= 76;
	static final int	HEADER_DIMTSZ		= 77; 
	static final int	HEADER_DIMTVP		= 78;
	static final int	HEADER_DIMTXT		= 79;
	static final int	HEADER_DIMZIN		= 80;
	static final int	HEADER_DRAGMODE		= 81;
	static final int	HEADER_ELEVATION	= 82;
	static final int	HEADER_EXTMAX		= 83;
	static final int	HEADER_EXTMIN		= 84;
	static final int	HEADER_FILLETRAD	= 85; 
	static final int	HEADER_FILLMODE		= 86;
	static final int	HEADER_HANGLING		= 87;
	static final int	HEADER_HANDSEED		= 88;
	static final int	HEADER_INBASE		= 89;
	static final int	HEADER_LIMCHECK		= 90;
	static final int	HEADER_LIMMAX		= 91;
	static final int	HEADER_LIMMIN		= 92;
	static final int	HEADER_LTSCALE		= 93;
	static final int	HEADER_LUNITS		= 94;
	static final int	HEADER_LUPREC		= 95;
	static final int	HEADER_MAXACTVP		= 96;
	static final int	HEADER_MENU			= 97;
	static final int	HEADER_DIMTAD		= 98;
	static final int	HEADER_MIRRTEXT		= 99;
	static final int	HEADER_ORTHOMODE	= 100; 
	static final int	HEADER_OSMODE		= 101;
	static final int	HEADER_PDMODE		= 102;
	static final int	HEADER_PDSIZE		= 103;
	static final int	HEADER_PELEVATION	= 104;
	static final int	HEADER_PEXTMAX		= 105;
	static final int	HEADER_PEXTMIN		= 106;
	static final int	HEADER_PLINCHECK	= 107;
	static final int	HEADER_PLIMMAX		= 108;
	static final int	HEADER_PLIMMIN		= 109;
	static final int	HEADER_PLINEWID		= 110;
	static final int	HEADER_PUCSNAME		= 111;
	static final int	HEADER_PUCSORD		= 112;
	static final int	HEADER_PUCSXDIR		= 113;
	static final int	HEADER_PUCSYDIR		= 114;
	static final int	HEADER_QTEXTMODE	= 115;
	static final int	HEADER_REGENMODE	= 116;
	static final int	HEADER_SHADEDGE		= 117;
	static final int	HEADER_SHADEDIF		= 118;
	static final int	HEADER_SKETCHINC	= 119;
	static final int	HEADER_SKPOLY		= 120;
	static final int	HEADER_SPLFRAME		= 121;
	static final int	HEADER_SPLINESEGS	= 122;
	static final int	HEADER_SPLINETYPE	= 123;
	static final int	HEADER_SURFTAB1		= 124;
	static final int	HEADER_SURFTAB2		= 125;
	static final int	HEADER_SURTYPE		= 126;
	static final int	HEADER_SURFU			= 127;
	static final int	HEADER_SURFV			= 128;
	static final int	HEADER_TDCREATE		= 129;
	static final int	HEADER_TDINDWG		= 130;
	static final int	HEADER_TDUPDATE		= 131;
	static final int	HEADER_TDUSRTIMER	= 132;
	static final int	HEADER_TEXTSIZE		= 133;
	static final int	HEADER_TEXTSTYLE	= 134;
	static final int	HEADER_THICKNESS	= 135;
	static final int	HEADER_TILEMODE		= 136;
	static final int	HEADER_TRACEWID		= 137;
	static final int	HEADER_UCSNAME		= 138;
	static final int	HEADER_UCSORG		= 139;
	static final int	HEADER_UCSDIR		= 140;
	static final int	HEADER_UCSYDIR		= 141;
	static final int	HEADER_UNITMODE		= 142;
	static final int	HEADER_USERI1		= 143;
	static final int	HEADER_USERR1		= 144;
	static final int	HEADER_USERTIMER	= 145;
	static final int	HEADER_WORLDVIEW	= 146;
	static final int	HEADER_FASTZOOM		= 147;
	static final int	HEADER_GRIDMODE		= 148;
	static final int	HEADER_GRIDUNIT		= 149;
	static final int	HEADER_SNAPANG		= 150;
	static final int	HEADER_SNAPBASE		= 151;
	static final int	HEADER_SNAPISOPAIR	= 152;
	static final int	HEADER_SNAPMODE		= 153;
	static final int	HEADER_SNAPSTYLE	= 154;
	static final int	HEADER_SNAPUNIT	= 155;
	static final int	HEADER_VIEWCTR		= 156;
	static final int	HEADER_VIEWDIR		= 157;
	static final int	HEADER_VIEWSIZE	= 158;
	static final int	SOLID					= 159;
	static final int	THICKNESS 			= 160;
	static final int	EXTRUSION_DIR_X	= 161;
	static final int	EXTRUSION_DIR_Y	= 162;
	static final int	EXTRUSION_DIR_Z	= 163;
	static final int	SUBCLASS_MARKER	= 164;
	static final int	POLYLINE				= 165;
	static final int	VERTEX				= 166;
	static final int	FLAG					= 167;
	static final int	STARTING_WIDTH		= 168;
	static final int	ENDING_WIDTH		= 169;
	static final int	POLYMESH_MVERTEX_COUNT 	= 170;
	static final int	POLYMESH_NVERTEX_COUNT 	= 171;
	static final int	SMOOTH_SURFACE_MDENSITY = 172;
	static final int	SMOOTH_SURFACE_NDENSITY = 173;
	static final int	CURVE_SMOOTH_TYPE 		= 174;
	static final int	SEQSTART				 		= 175;
	static final int	SEQEND				 		= 176;
	static final int	CURVE_FIT_TAN_DIRECTION	= 177;
	static final int	BLOCKS			= 178;
	static final int	ENDBLK			= 179;
	static final int	BLOCK				= 180;
	static final int	ATTDEF			= 181;
	static final int	GROUP6			= 182;
	static final int	DEFAULT_VALUE	= 183;
	static final int	PROMPT_STRING	= 184;
	static final int	OBLIQUE_ANGLE	= 185;
	static final int	TEXT_STYLE_NAME= 186;
	static final int	LINE				= 187;

	// ATTDEF
	static final int	TEXT_ROTATION				= CURVE_FIT_TAN_DIRECTION;
	static final int	TEXT_HEIGHT					= STARTING_WIDTH;
	static final int	X_SCALE_FACTOR				= ENDING_WIDTH;
	static final int	TEXT_GENERATION_FLAG		= POLYMESH_MVERTEX_COUNT;
	static final int	HORIZONTAL_TEXT_JUSTIFY	= POLYMESH_NVERTEX_COUNT;
	static final int	FIELD_LENGTH				= SMOOTH_SURFACE_MDENSITY;
	static final int	VERTEXL_TEXT_JUSTIFY		= SMOOTH_SURFACE_NDENSITY;
	static final int	TAG_STRING					= GROUP2;	

}
