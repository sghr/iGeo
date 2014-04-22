/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2013 Satoru Sugihara

    This file is part of iGeo.

    iGeo is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, version 3.

    iGeo is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with iGeo.  If not, see <http://www.gnu.org/licenses/>.

---*/
/*
  
    The Java code on this file is produced by tranlation and modification
    of open source codes of openNURBS toolkit written in C++. 
    For more detail of openNURBS toolkit, please see 
    http://www.opennurbs.org/
    
    openNURBS toolkit is copyrighted by Robert McNeel & Associates.
    openNURBS is a trademark of Robert McNeel & Associates.
    Rhinoceros is a registered trademark of Robert McNeel & Associates.
    
*/


package igeo.io;

import igeo.*;
import igeo.gui.*;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;
//import java.awt.Color;

import static igeo.io.IRhino3dmImporter.*;
import static igeo.io.IRhino3dmExporter.*;


/**
   Rhinoceros 3dm importer/exporter class.
   This class defines OpenNURBS objects to be used in importer and exporter.
   
   @author Satoru Sugihara
*/
public class IRhino3dm{
    
    public static final int tcodeStartSection = 0x00000001;
    
    public static final int tcodeCommentBlock        = 0x00000001;
    public static final int tcodeEndOfFile           = 0x00007FFF;
    public static final int tcodeEndOfFileGoo        = 0x00007FFE;
    
    public static final int tcodeLegacyGeometry      = 0x00010000;
    public static final int tcodeOpenNurbsObject     = 0x00020000;
    public static final int tcodeGeometry            = 0x00100000;
    public static final int tcodeAnnotation          = 0x00200000;
    public static final int tcodeDisplay             = 0x00400000;
    public static final int tcodeRender              = 0x00800000;
    public static final int tcodeInterface           = 0x02000000;
    public static final int tcodeTolerance           = 0x08000000;
    public static final int tcodeTable               = 0x10000000;
    public static final int tcodeTableRec            = 0x20000000;
    public static final int tcodeUser                = 0x40000000;
    public static final int tcodeShort               = 0x80000000;
    
    public static final int tcodeCRC                 = 0x8000;
    
    public static final int tcodeAnonymousChunk      = (tcodeUser | tcodeCRC | 0x0000 );
    
    public static final int tcodeMaterialTable    = (tcodeTable | 0x0010);
    public static final int tcodeLayerTable       = (tcodeTable | 0x0011);
    public static final int tcodeLightTable      = (tcodeTable | 0x0012);
    public static final int tcodeObjectTable     = (tcodeTable | 0x0013);
    public static final int tcodePropertiesTable  = (tcodeTable | 0x0014);
	
    public static final int tcodeSettingsTable    = (tcodeTable | 0x0015);
    
    
    public static final int tcodeBitmapTable     = (tcodeTable | 0x0016);
    public static final int tcodeUserTable       = (tcodeTable | 0x0017);
	
    public static final int tcodeGroupTable      = (tcodeTable | 0x0018);
	
    public static final int tcodeFontTable       = (tcodeTable | 0x0019);
    public static final int tcodeDimStyleTable   = (tcodeTable | 0x0020);
	
    public static final int tcodeInstanceDefinitionTable = (tcodeTable | 0x0021);
	
    public static final int tcodeHatchPatternTable = (tcodeTable | 0x0022);

    public static final int tcodeLinetypeTable = (tcodeTable | 0x0023);

    public static final int tcodeObsoleteLayerSetTable = (tcodeTable | 0x0024);
    
    public static final int tcodeTextureMappingTable = (tcodeTable | 0x0025);

    public static final int tcodeHistoryRecordTable = (tcodeTable | 0x0026);
	
    public static final int tcodeEndOfTable        =  0xFFFFFFFF;

    
    public static final int tcodePropertiesRevisionHistory = (tcodeTableRec | tcodeCRC | 0x0021);
    public static final int tcodePropertiesNotes           = (tcodeTableRec | tcodeCRC | 0x0022);
    public static final int tcodePropertiesPreviewImage    = (tcodeTableRec | tcodeCRC | 0x0023);
    public static final int tcodePropertiesApplication     = (tcodeTableRec | tcodeCRC | 0x0024);
    public static final int tcodePropertiesCompressedPreviewImage = (tcodeTableRec | tcodeCRC | 0x0025);
    public static final int tcodePropertiesOpenNurbsVersion = (tcodeTableRec | tcodeShort | 0x0026);
	
    public static final int tcodeSettingsPluginList             = (tcodeTableRec | tcodeCRC   | 0x0135);
    public static final int tcodeSettingsUnitsAndTols          = (tcodeTableRec | tcodeCRC   | 0x0031);
    public static final int tcodeSettingsRenderMesh             = (tcodeTableRec | tcodeCRC   | 0x0032);
    public static final int tcodeSettingsAnalysisMesh           = (tcodeTableRec | tcodeCRC   | 0x0033);
    public static final int tcodeSettingsAnnotation             = (tcodeTableRec | tcodeCRC   | 0x0034);
    public static final int tcodeSettingsNamedCPlaneList        = (tcodeTableRec | tcodeCRC   | 0x0035);
    public static final int tcodeSettingsNamedViewList          = (tcodeTableRec | tcodeCRC   | 0x0036);
    public static final int tcodeSettingsViewList               = (tcodeTableRec | tcodeCRC   | 0x0037);
    public static final int tcodeSettingsCurrentLayerIndex      = (tcodeTableRec | tcodeShort | 0x0038);
    public static final int tcodeSettingsCurrentMaterialIndex   = (tcodeTableRec | tcodeCRC   | 0x0039);
    public static final int tcodeSettingsCurrentColor           = (tcodeTableRec | tcodeCRC   | 0x003A);
    public static final int tcodeSettings_Never_Use_This        = (tcodeTableRec | tcodeCRC   | 0x003E);
    public static final int tcodeSettingsCurrentWireDensity     = (tcodeTableRec | tcodeShort | 0x003C);
    public static final int tcodeSettingsRender                 = (tcodeTableRec | tcodeCRC   | 0x003D);
    public static final int tcodeSettingsGridDefaults           = (tcodeTableRec | tcodeCRC   | 0x003F);
    public static final int tcodeSettingsModelURL               = (tcodeTableRec | tcodeCRC   | 0x0131);
    public static final int tcodeSettingsCurrentFontIndex       = (tcodeTableRec | tcodeShort | 0x0132);
    public static final int tcodeSettingsCurrentDimStyleIndex   = (tcodeTableRec | tcodeShort | 0x0133);
    public static final int tcodeSettingsAttributes             = (tcodeTableRec | tcodeCRC   | 0x0134);



    public static final int tcodeViewRecord            = (tcodeTableRec | tcodeCRC   | 0x003B);
    public static final int tcodeViewCPlane            = (tcodeTableRec | tcodeCRC   | 0x013B);
    public static final int tcodeViewViewport          = (tcodeTableRec | tcodeCRC   | 0x023B);
    public static final int tcodeViewShowConGrid       = (tcodeTableRec | tcodeShort | 0x033B);
    public static final int tcodeViewShowConAxes       = (tcodeTableRec | tcodeShort | 0x043B);
    public static final int tcodeViewShowWorldAxes     = (tcodeTableRec | tcodeShort | 0x053B);
    public static final int tcodeViewTraceImage        = (tcodeTableRec | tcodeCRC   | 0x063B);
    public static final int tcodeViewWallPaper         = (tcodeTableRec | tcodeCRC   | 0x073B);
    
    public static final int tcodeViewWallPaperV3       = (tcodeTableRec | tcodeCRC   | 0x074B);
    public static final int tcodeViewTarget            = (tcodeTableRec | tcodeCRC   | 0x083B);
    public static final int tcodeViewDisplayMode       = (tcodeTableRec | tcodeShort | 0x093B);
    public static final int tcodeViewName              = (tcodeTableRec | tcodeCRC   | 0x0A3B);
    public static final int tcodeViewPosition          = (tcodeTableRec | tcodeCRC   | 0x0B3B);
    
    
    public static final int tcodeViewAttributes       = (tcodeTableRec | tcodeCRC   | 0x0C3B);
    
    public static final int tcodeViewViewportUserData = (tcodeTableRec | tcodeCRC   | 0x0D3B);
    
    public static final int tcodeBitmapRecord = (tcodeTableRec | tcodeCRC | 0x0090);
    
    public static final int tcodeMaterialRecord = (tcodeTableRec | tcodeCRC | 0x0040);
    
    public static final int tcodeLayerRecord    = (tcodeTableRec | tcodeCRC | 0x0050);
    
    public static final int tcodeLightRecord            = (tcodeTableRec | tcodeCRC | 0x0060);
    public static final int tcodeLightRecordAttributes = (tcodeInterface | tcodeCRC   | 0x0061);
    public static final int tcodeLightRecordAttributesUserData = (tcodeInterface | 0x0062);
    
    public static final int tcodeLightRecordEnd        = (tcodeInterface | tcodeShort | 0x006F);
    
    public static final int tcodeUserTableUUID          = (tcodeTableRec | tcodeCRC | 0x0080);
    public static final int tcodeUserTableRecordHeader = (tcodeTableRec | tcodeCRC | 0x0082);
    
    public static final int tcodeUserRecord              = (tcodeTableRec | 0x0081) ;
    
    
    public static final int tcodeGroupRecord             = (tcodeTableRec  | tcodeCRC   | 0x0073);
    
    public static final int tcodeFontRecord             = (tcodeTableRec  | tcodeCRC   | 0x0074);
    
    public static final int tcodeDimStyleRecord          = (tcodeTableRec  | tcodeCRC   | 0x0075);
    
    public static final int tcodeInstanceDefinitionRecord  = (tcodeTableRec  | tcodeCRC   | 0x0076);
    
    public static final int tcodeHatchPatternRecord  = (tcodeTableRec  | tcodeCRC   | 0x0077);
    
    public static final int tcodeLineTypeRecord  = (tcodeTableRec  | tcodeCRC   | 0x0078);
    
    public static final int tcodeObsoleteLayerSetRecord  = (tcodeTableRec  | tcodeCRC   | 0x0079);
    
    public static final int tcodeTextureMappingRecord  = (tcodeTableRec  | tcodeCRC   | 0x007A);
    
    public static final int tcodeHistoryRecordRecord  = (tcodeTableRec  | tcodeCRC   | 0x007B);
    
    public static final int tcodeObjectRecord           = (tcodeTableRec  | tcodeCRC   | 0x0070);
    public static final int tcodeObjectRecordType       = (tcodeInterface | tcodeShort | 0x0071);
    public static final int tcodeObjectRecordAttributes = (tcodeInterface | tcodeCRC   | 0x0072);
    public static final int tcodeObjectRecordAttributesUserData = (tcodeInterface | 0x0073);
    public static final int tcodeObjectRecordHistory    = (tcodeInterface | tcodeCRC   | 0x0074);
    public static final int tcodeObjectRecordHistoryHeader = (tcodeInterface | tcodeCRC | 0x0075);
    public static final int tcodeObjectRecordHistoryData   = (tcodeInterface | tcodeCRC | 0x0076);
    public static final int tcodeObjectRecordEnd        = (tcodeInterface | tcodeShort | 0x007F);
    
    public static final int tcodeOpenNurbsClass          = (tcodeOpenNurbsObject               | 0x7FFA);
    public static final int tcodeOpenNurbsClassUUID     = (tcodeOpenNurbsObject | tcodeCRC   | 0x7FFB);
    public static final int tcodeOpenNurbsClassData     = (tcodeOpenNurbsObject | tcodeCRC   | 0x7FFC);
    public static final int tcodeOpenNurbsClassUserData = (tcodeOpenNurbsObject               | 0x7FFD);
    public static final int tcodeOpenNurbsClassUserDataHeader = (tcodeOpenNurbsObject | tcodeCRC | 0x7FF9);
    public static final int tcodeOpenNurbsClassEnd      = (tcodeOpenNurbsObject | tcodeShort | 0x7FFF);;;
    
    
    public static final int tcodeAnnotationSettings = (tcodeAnnotation | 0x0001);
    
    public static final int tcodeTextBlock          = (tcodeAnnotation | 0x0004);
    public static final int tcodeAnnotationLeader   = (tcodeAnnotation | 0x0005);
    public static final int tcodeLinearDimension    = (tcodeAnnotation | 0x0006);
    public static final int tcodeAngularDimension   = (tcodeAnnotation | 0x0007);
    public static final int tcodeRadialDimension    = (tcodeAnnotation | 0x0008);
    
    public static final int tcodeRhinoIOObjectNurbsCurve   = (tcodeOpenNurbsObject | 0x0008);
    public static final int tcodeRhinoIOObjectNurbsSurface = (tcodeOpenNurbsObject | 0x0009);
    public static final int tcodeRhinoIOObjectBrep         = (tcodeOpenNurbsObject | 0x000B);
    public static final int tcodeRhinoIOObjectData         = (tcodeOpenNurbsObject | 0xFFFE);
    public static final int tcodeRhinoIOObjectEnd          = (tcodeOpenNurbsObject | 0xFFFF);
    
    public static final int tcodeLegacyASM          = (tcodeLegacyGeometry | 0x0001);
    public static final int tcodeLegacyPRT          = (tcodeLegacyGeometry | 0x0002);
    public static final int tcodeLegacySHL          = (tcodeLegacyGeometry | 0x0003);
    public static final int tcodeLegacyFAC          = (tcodeLegacyGeometry | 0x0004);
    public static final int tcodeLegacyBND          = (tcodeLegacyGeometry | 0x0005);
    public static final int tcodeLegacyTRM          = (tcodeLegacyGeometry | 0x0006);
    public static final int tcodeLegacySRF          = (tcodeLegacyGeometry | 0x0007);
    public static final int tcodeLegacyCRV          = (tcodeLegacyGeometry | 0x0008);
    public static final int tcodeLegacySPL          = (tcodeLegacyGeometry | 0x0009);
    public static final int tcodeLegacyPNT          = (tcodeLegacyGeometry | 0x000A);
    
    public static final int tcodeStuff             =  0x0100;
    
    public static final int tcodeLegacyASMStuff     = (tcodeLegacyGeometry | tcodeStuff | tcodeLegacyASM);
    public static final int tcodeLegacyPRTStuff     = (tcodeLegacyGeometry | tcodeStuff | tcodeLegacyPRT);
    public static final int tcodeLegacySHLStuff     = (tcodeLegacyGeometry | tcodeStuff | tcodeLegacySHL);
    public static final int tcodeLegacyFACStuff     = (tcodeLegacyGeometry | tcodeStuff | tcodeLegacyFAC);
    public static final int tcodeLegacyBNDStuff     = (tcodeLegacyGeometry | tcodeStuff | tcodeLegacyBND);
    public static final int tcodeLegacyTRMStuff     = (tcodeLegacyGeometry | tcodeStuff | tcodeLegacyTRM);
    public static final int tcodeLegacySRFStuff     = (tcodeLegacyGeometry | tcodeStuff | tcodeLegacySRF);
    public static final int tcodeLegacyCRVStuff     = (tcodeLegacyGeometry | tcodeStuff | tcodeLegacyCRV);
    public static final int tcodeLegacySPLStuff     = (tcodeLegacyGeometry | tcodeStuff | tcodeLegacySPL);
    public static final int tcodeLegacyPNTStuff     = (tcodeLegacyGeometry | tcodeStuff | tcodeLegacyPNT);
    
    public static final int tcodeRhPoint            = (tcodeGeometry | 0x0001);
    
    public static final int tcodeRhSpotLight         = (tcodeRender   | 0x0001);
    
    public static final int tcodeOldRhTrimesh          = (tcodeGeometry | 0x0011);
    public static final int tcodeOldMeshVertexNormals  = (tcodeGeometry | 0x0012);
    public static final int tcodeOldMeshUV             = (tcodeGeometry | 0x0013);
    public static final int tcodeOldFullMesh           = (tcodeGeometry | 0x0014);
    
    public static final int tcodeMeshObject            = (tcodeGeometry | 0x0015);
    public static final int tcodeCompressedMeshGeometry = (tcodeGeometry | 0x0017);
    public static final int tcodeAnalysisMesh         = (tcodeGeometry | 0x0018);
    
    public static final int tcodeName                = (tcodeInterface | 0x0001);
    public static final int tcodeView                = (tcodeInterface | 0x0002);
    public static final int tcodeCPlane              = (tcodeInterface | 0x0003);
    
    public static final int tcodeNamedCPlane         = (tcodeInterface | 0x0004);
    public static final int tcodeNamedView           = (tcodeInterface | 0x0005);
    public static final int tcodeViewport            = (tcodeInterface | 0x0006);
    
    public static final int tcodeShowGrid            = (tcodeShort | tcodeInterface | 0x0007);
    public static final int tcodeShowGridAxes        = (tcodeShort | tcodeInterface | 0x0008);
    public static final int tcodeShowWorldAxes       = (tcodeShort | tcodeInterface | 0x0009);
    
    public static final int tcodeViewportPosition   = (tcodeInterface | 0x000A);
    public static final int tcodeViewportTraceInfo  = (tcodeInterface | 0x000B);
    public static final int tcodeSnapSide           = (tcodeInterface | 0x000C);
    public static final int tcodeNearClipPlane      = (tcodeInterface | 0x000D);
    public static final int tcodeHideTrace          = (tcodeInterface | 0x000E);
    
    public static final int tcodeNotes              = (tcodeInterface | 0x000F);
    public static final int tcodeUnitAndTolerances  = (tcodeInterface | 0x0010);
    
    public static final int tcodeMaximizedViewport  = (tcodeShort | tcodeInterface | 0x0011);
    public static final int tcodeViewportWallPaper  = (tcodeInterface | 0x0012);
    
    
    public static final int tcodeSummary             = (tcodeInterface | 0x0013);
    public static final int tcodeBitmapPreview       = (tcodeInterface | 0x0014);
    public static final int tcodeViewportDisplayMode = (tcodeShort | tcodeInterface | 0x0015);
    
    
    //public static final int tcodeLayerTable          = (tcodeShort   | tcodeTable    | 0x0001) ;
    public static final int tcodeLayerRef            = (tcodeShort   | tcodeTableRec | 0x0001);
    
    public static final int tcodeXData               = (tcodeUser | 0x0001);
    
    public static final int tcodeRGB                 = (tcodeShort   | tcodeDisplay | 0x0001);
    public static final int tcodeTextureMap          = (tcodeDisplay | 0x0002);
    public static final int tcodeBumpMap             = (tcodeDisplay | 0x0003);
    public static final int tcodeTransparency        = (tcodeShort   | tcodeDisplay | 0x0004);
    public static final int tcodeDispAmResolution    = (tcodeShort   | tcodeDisplay | 0x0005);
    public static final int tcodeRGBDisplay          = (tcodeShort   | tcodeDisplay | 0x0006);
    public static final int tcodeRenderMaterialID    = (tcodeDisplay | 0x0007);
    public static final int tcodeLayer               = (tcodeDisplay | 0x0010);
    
    
    public static final int tcodeLayerObselete1    = (tcodeShort   | tcodeDisplay | 0x0013);
    public static final int tcodeLayerObselete2    = (tcodeShort   | tcodeDisplay | 0x0014);
    public static final int tcodeLayerObselete3    = (tcodeShort   | tcodeDisplay | 0x0015);
    
    
    public static final int tcodeLayerOn             = (tcodeShort   | tcodeDisplay | 0x0016);
    public static final int tcodeLayerThawed         = (tcodeShort   | tcodeDisplay | 0x0017);
    public static final int tcodeLayerLocked         = (tcodeShort   | tcodeDisplay | 0x0018);
    
    
    public static final int tcodeLayerVisible        = (tcodeShort   | tcodeDisplay | 0x0012);
    public static final int tcodeLayerPickable       = (tcodeShort   | tcodeDisplay | 0x0030);
    public static final int tcodeLayerSnapable       = (tcodeShort   | tcodeDisplay | 0x0031);
    public static final int tcodeLayerRenderable     = (tcodeShort   | tcodeDisplay | 0x0032);
    
    
    public static final int tcodeLayerState          = (tcodeShort   | tcodeDisplay | 0x0033);
    public static final int tcodeLayerIndex          = (tcodeShort   | tcodeDisplay | 0x0034);
    public static final int tcodeLayerMaterialIndex  = (tcodeShort   | tcodeDisplay | 0x0035);
    
    public static final int tcodeRenderMeshParams    = (tcodeDisplay | 0x0020);
    
    
    public static final int tcodeDispCPLines        = (tcodeShort   | tcodeDisplay | 0x0022);
    public static final int tcodeDispMaxLength      = (tcodeDisplay | 0x0023);
    
    public static final int tcodeCurrentLayer        = (tcodeShort   | tcodeDisplay | 0x0025 );
    
    public static final int tcodeLayerName           = (tcodeDisplay | 0x0011);
    
    public static final int tcodeLegacyTolFit      = (tcodeTolerance | 0x0001);
    public static final int tcodeLegacyTolAngle    = (tcodeTolerance | 0x0002);
    
    public static final int tcodeDictionary          = (tcodeUser | tcodeCRC   | 0x0010);
    public static final int tcodeDictionaryID       = (tcodeUser | tcodeCRC   | 0x0011);
    public static final int tcodeDictionaryEntry    = (tcodeUser | tcodeCRC   | 0x0012);
    public static final int tcodeDictionaryEnd      = (tcodeUser | tcodeShort | 0x0013);
    
    
    
    public enum Endian{ LittleEndian, BigEndian }
    public static Endian endian(int i){ return (i<=0)?Endian.LittleEndian:Endian.BigEndian; }
    public static Endian endian(){
	int i=1;
	if( ((i>>>24)&0xFF) == 1) return Endian.LittleEndian;
	return Endian.BigEndian;
    }
    
    
    public enum AnnotationType{
	dtNothing,
	    dtDimLinear,
	    dtDimAligned,
	    dtDimAngular,
	    dtDimDiameter,
	    dtDimRadius,
	    dtLeader,
	    dtTextBlock,
	    dtDimOrdinate
	    };

    public static int annotationType(AnnotationType t){
	switch(t){
	case dtNothing: return 0;
	case dtDimLinear: return 1;
	case dtDimAligned: return 2;
	case dtDimAngular: return 3;
	case dtDimDiameter: return 4;
	case dtDimRadius: return 5;
	case dtLeader: return 6;
	case dtTextBlock: return 7;
	case dtDimOrdinate: return 8;
	}
	return 0;
    }
    
    public static AnnotationType annotationType(int i){
	switch(i){
	case 0: return AnnotationType.dtNothing;
	case 1: return AnnotationType.dtDimLinear;
	case 2: return AnnotationType.dtDimAligned;
	case 3: return AnnotationType.dtDimAngular;
	case 4: return AnnotationType.dtDimDiameter;
	case 5: return AnnotationType.dtDimRadius;
	case 6: return AnnotationType.dtLeader;
	case 7: return AnnotationType.dtTextBlock;
	case 8: return AnnotationType.dtDimOrdinate;
	}
	return AnnotationType.dtNothing;
    }
    
    public enum TextDisplayMode{
	dtNormal,
	    dtHorizontal,
	    dtAboveLine,
	    dtInLine
	    };
    
    public static int textDisplayMode(TextDisplayMode m){
	switch(m){
	case dtNormal: return 0;
	case dtHorizontal: return 1;
	case dtAboveLine: return 2;
	case dtInLine: return 3;
	}
	return 0;
    }
    
    public static TextDisplayMode textDisplayMode(int i){
	switch(i){
	case 0: return TextDisplayMode.dtNormal;
	case 1: return TextDisplayMode.dtHorizontal;
	case 2: return TextDisplayMode.dtAboveLine;
	case 3: return TextDisplayMode.dtInLine;
	}
	return TextDisplayMode.dtNormal;
    }
    
    
    /***************************************************************
      static methods
    ****************************************************************/
    
    // little endian byte to big endian integer
    static int btoi(byte[] b){
	int i=0;
	for(int j=0; j<b.length; j++) i |= (b[i]&0xFF)<<(8*j);
	return i;
    }
    /*
    // big endian 32 bit integer to little endian byte
    static byte[] itob(int i){
	byte[] b = new byte[4];
	b[0] = (byte)(i&0xFF);
	b[1] = (byte)((i&0xFF00)>>8);
	b[2] = (byte)((i&0xFF0000)>>16);
	b[3] = (byte)((i&0xFF000000)>>24);
	return b;
    }
    */
    public static byte[] stob(short s){ // 16 bit
	byte[] buf = new byte[2];
	for(int d=0; d<buf.length; d++){ buf[d] = (byte)(s&0xFF); s>>>=8; }
	return buf;
    }
    public static byte[] itob(int i){ // 32 bit
	byte[] buf = new byte[4];
	for(int d=0; d<buf.length; d++){ buf[d] = (byte)(i&0xFF); i>>>=8; }
	return buf;
    }
    public static byte[] ltob(long l){ // 64 bit
	byte[] buf = new byte[8];
	for(int d=0; d<buf.length; d++){ buf[d] = (byte)(l&0xFF); l>>>=8; }
	return buf;
    }
    
    public static String hex(int i){
	String str = new String();
	for(int j=0; j<4; j++) str += hex( (byte) ((i>>(3-j)*8)&0xFF) );
	return str;
    }
    
    public static String hex(short i){
	String str = new String();
	for(int j=0; j<2; j++) str += hex( (byte) ((i>>(1-j)*8)&0xFF) );
	return str;
    }
    
    public static String hex(byte c){
	//String str = new String("^");
	String str = new String();
	for(int j=1; j>=0; j--){
	    int h = (c>>(j*4))&0x0F;
	    if(h<10) str += String.valueOf((char)('0'+h));
	    else str += String.valueOf((char)('A'+h-10));
	}
	return str;
    }
    
    public static String hex(char c){
	//String str = new String("^");
	String str = new String(); 
	for(int j=1; j>=0; j--){
	    int h = (c>>(j*4))&0x0F;
	    if(h<10) str += String.valueOf((char)('0'+h));
	    else str += String.valueOf((char)('A'+h-10));
	}
	return str;
    }
    
    public static String hex(byte[] c){
	String str = new String();
	for(int i=0; i<c.length; i++) str += hex(c[i]);
	return str;
    }
    
    public static String hex(char[] c){
	String str = new String();
	for(int i=0; i<c.length; i++) str += hex(c[i]);
	return str;
    }
    
    public static String asciiOrHex(byte[] c){
	String str = new String();
	for(int i=0; i<c.length; i++){
	    if( c[i]>32&&c[i]<127 || c[i]==9 || c[i]==10 || c[i]==13){
		//str+=String.valueOf(c[i]);
		str += (char)c[i];
	    }
	    else str+=hex(c[i]);
	}
	return str;
    }
    
    public static void printHex(byte[] c, PrintStream ps){
	for(int i=0; i<c.length; i++) ps.print(hex(c[i]));
    }
    
    public static void printAsciiOrHex(byte[] c, PrintStream ps){
	for(int i=0; i<c.length; i++){
	    if( c[i]>=32&&c[i]<127 || c[i]==9 || c[i]==10 || c[i]==13) ps.print((char)c[i]);
	    else ps.print(hex(c[i]));
	}
    }
        
    public static byte[] hexStringToByte(String text){
	// if text.length()%2==1, last character is truncated
	int num = text.length()/2;
	byte[] buf = new byte[num];
	for(int i=0; i<num; i++) buf[i]=hexCharToByte(text.charAt(i*2),text.charAt(i*2+1));
	return buf;
    }
    
    public static byte hexCharToByte(char c){
	byte b=0;
	if(c >='0' && c <='9'){ b = (byte)(c-'0'); }
	else if(c >='A' && c <='F'){ b = (byte)(c-'A' + 10); }
	else if(c >='a' && c <='f'){ b = (byte)(c-'a' + 10); }
	return b;
    }
    
    public static byte hexCharToByte(char c1, char c2){
	byte b = hexCharToByte(c2);
	byte b2 = hexCharToByte(c1);
	b|=(b2<<4)&0xF0;
	return b;
    }
    
    
    /***************************************************************
     * static classes
     ***************************************************************/

    /**
       Rhino 3dm chunk objects.
       3dm file is basically written as series of and nested chunks. 
    */
    public static class Chunk{
	//byte[] header;
	//byte[] body;
	public int header;
	public int body;
	public byte[] content = null;
	//public Chunk chunk = null;
	
	public ArrayList<byte[]> contents = null; // for test. not really used yet
	
	public CRC32 crc;
	
	//boolean enableCRC=true; //false;
	
	
	//public Chunk(byte[] h, byte[] b){
	public Chunk(int h, int b){ header = h; body = b; }
	
	public Chunk(int h, int b, byte[] c, CRC32 crc){
	    header = h; body = b; content = c; this.crc = crc;
	}
	
	//Chunk(byte[] h, byte[] b, byte[] c){
	public Chunk(int h, int b, byte[] c){
	    header = h; body = b; content = c;
	    if(doCRC()){ crc = new CRC32(); crc.update(content); }
	}
	
	public Chunk(int h, byte[] c, CRC32 crc){
	    header = h; content = c; body = content.length; this.crc = crc;
	}
	
	public Chunk(int h, byte[] c){
	    header = h; content = c; body = content.length;
	    if(doCRC()){ crc = new CRC32(); crc.update(content); }
	}
	
	public Chunk(int h, String str){
	    header = h; content = str.getBytes(); body = content.length;
	    if(doCRC()){ crc = new CRC32(); crc.update(content); }
	}
	
	/*
	public Chunk(int h, Chunk c){
	    header = h;
	    chunk = c;
	    content = chunk.getBytes();
	    body = content.length;
	    if(c.crc!=null) crc = crc;
	}
	*/
	
	public Chunk(int h, int b, int i){
	    header = h;
	    body = b;
	    content = itob(i);
	    if(doCRC()){ crc = new CRC32(); crc.update(content); }
	}
	
	
	public Chunk(int h, int b, ArrayList<byte[]> c, CRC32 crc){
	    //header = h; body = b; contents = c; this.crc = crc;
	    header = h; body = b;
	    /*
	    int len=0;
	    for(int i=0; c!=null && i<c.size(); i++) len+=c.get(i).length;
	    contents = new byte[len];
	    for(int i=0, idx=0; i<c.size(); i++){
		byte[] buf = c.get(i);
	    }
	    */
	    contents = c;
	    this.crc = crc;
	}
	
	public Chunk(int h, int b, ArrayList<byte[]> c){
	    header = h; body = b; contents = c;
	    if(doCRC()){
		crc = new CRC32();
		for(int i=0; c!=null && i<c.size(); i++) crc.update(c.get(i));
	    }
	}
	
	public Chunk(int h, ArrayList<byte[]> c, CRC32 crc){
	    header = h; contents = c;
	    body=0;
	    for(int i=0; c!=null && i<c.size(); i++) body+=c.get(i).length;
	    this.crc = crc;
	}
	
	public Chunk(int h, ArrayList<byte[]> c){
	    header = h; contents = c;
	    body=0;
	    for(int i=0; c!=null && i<c.size(); i++) body+=c.get(i).length;
	    if(doCRC()){
		crc = new CRC32();
		for(int i=0; c!=null && i<c.size(); i++) crc.update(c.get(i));
	    }
	}
	
	
	
	public void setContentLength(){
	    if(content!=null) body = content.length;
	    else body = 0;
	}
	
	public int getHeader(){ return header; }
	public int getBody(){ return body; }
	public byte[] getContent(){ return content; }
	//public int contentLength(){ return content.length; }
	public int contentLength(){
	    if(contents!=null){
		int len = 0;
		for(int i=0; i<contents.size(); i++) len+=contents.get(i).length;
		return len;
	    }
	    if(content!=null) return content.length;
	    return 0;
	}
	
	public ArrayList<byte[]> getContents(){ return contents; }
	
	public int chunkLength(){
	    int sz= 8; // 4 byte int header + 4 byte int body
	    if(content!=null) sz += content.length;
	    return sz;
	}
	
	public byte[] getBytes(){
	    byte[] b=null;
	    
	    if(content==null) b = new byte[8]; else b = new byte[8+content.length];
	    
	    System.arraycopy(itob(header),0,b,0,4);
	    System.arraycopy(itob(body),0,b,4,4);
	    if(content!=null) System.arraycopy(content,0,b,8,content.length);
	    
	    return b;
	}
	
	public boolean isShort(){
	    final int shortChunkMask = 0x80000000;
	    return (header & shortChunkMask) != 0;
	}
	
	public boolean doCRC(){
	    return (header&tcodeCRC)!=0;
	    //||(header == (tcodeOpenNurbsObject | tcodeCRC | 0x7FFD));
	}
	
	public boolean isEndOfTable(){ return header == tcodeEndOfTable; }
	
	//public void enableCRC(){ enableCRC=true; }
	//public void disableCRC(){ enableCRC=false; }
	//public boolean doCRC(){ return enableCRC; }
	
	public void setCRC(CRC32 crc){ this.crc = crc; }
	
	public int getCRC(){ return (int)crc.getValue(); }
	
	/*
	static boolean isShort(byte[] header){
	    //IOut.print("isShort("+hex(header[0])+"): ");
	    //if((header[0] & 0x80) != 0) IOut.p("true");
	    //else IOut.p("false");
	    //return (header[0] & 0x80) != 0;
	    return (header[3] & 0x80) != 0;
	}
	*/
	
	public String toString(){
	    String str =
		"header: " + hex(header) + "\n" +
		"body: " + hex(body) + "\n";
	    if(content!=null){
		str +=
		    "length: " + content.length +"\n"+
		    "content: "+ hex(content) + "\n" + 
		    "content: "+ asciiOrHex(content) + "\n";
		    
	    }
	    return str;
	}
	//public void print(){ IOut.print(toString()); } // debug
	
	public void clear(){ content = null; }
	
    }
    
    
    public static class ChunkTable extends Chunk{
	public ArrayList<Chunk> chunks;
	public int endTCode = tcodeEndOfTable;
	public boolean serialized=false;

	// to use IRandomAccessOutputStream
	public long bodyPointer=-1;
	
	public ChunkTable(int tcode, int endTCode){
	    super(tcode,0);
	    this.endTCode = endTCode;
	    chunks = new ArrayList<Chunk>();
	    if(doCRC()){ crc = new CRC32(); }
	}
	public ChunkTable(int tcode){
	    super(tcode,0);
	    chunks = new ArrayList<Chunk>();
	    if(doCRC()){ crc = new CRC32(); }
	}
	
	public void add(Chunk c){ chunks.add(c); serialized=false; }
	
	public void serialize()throws IOException{
	    if(!serialized){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for(int i=0; i<chunks.size(); i++){ writeChunk(baos,chunks.get(i)); }
		writeChunk(baos,new Chunk(endTCode,0));
		content = baos.toByteArray();
		body = content.length;
		serialized=true;
	    }
	}
	
	public boolean doCRC(){ return false; } // ?
	
	
	// to use IRandomAccessOutputStream instead of using serialize
	public void writeTableHeader(IRandomAccessOutputStream ros)throws IOException{
	    writeInt32(ros,header,null);
	    bodyPointer = ros.pointer(); // record to seek later
	    writeInt32(ros,body,null); // body (content length) is not set yet. 0 is written here temporarily.
	}
	
	public void writeTableEntry(IRandomAccessOutputStream ros, Chunk c)throws IOException{
	    writeChunk(ros, c);
	}
	
	public void writeTableEnd(IRandomAccessOutputStream ros)throws IOException{
	    writeChunk(ros,new Chunk(endTCode,0));
	    if(doCRC()) writeInt32(ros,getCRC(),null); // ever do this?
	    long currentPointer = ros.pointer();
	    body = (int)(currentPointer - bodyPointer - 4); // excludes 4byte of body itself
	    ros.seek(bodyPointer);
	    writeInt32(ros,body,null); // seek and write the length of content
	    ros.seek(currentPointer);
	}
	
	
    }
    
    
    public static class Rhino3dmFile{
	public int version;
	public int openNurbsVersion;
	
	public StartSection startSection;
	public Properties properties;
	public Settings settings;
	public Bitmap[] bitmaps;
	public TextureMapping[] textureMappings;
	//public Material[] materials;
	
	public ArrayList<Material> materials;
	public ArrayList<IMaterial> imaterials; // to check index number of IMaterial attributes
	
	public Linetype[] linetypes;
	public Layer[] layers;
	public Group[] groups;
	public Font[] fonts;
	public DimStyle[] dimStyles;
	public Light[] lights;
	public HatchPattern[] hatchPatterns;
	public InstanceDefinition[] instanceDefinitions;
	public RhinoObject[] rhinoObjects;
	public HistoryRecord[] historyRecords;
	public UserData[] userData;
	
	public IServerI server;
	public void setServer(IServerI srv){ server = srv; }
	
	public Rhino3dmFile(){}
	public Rhino3dmFile(int ver, int openNurbsVer, IServerI serv){
	    version = ver; openNurbsVersion = openNurbsVer; server = serv;
	    
	    settings = new Settings(serv);
	    
	}
	
	
	public int sizeOfChunkLength(){
	    if(version<50) return 4;
	    return 8; // in Rhino v5 format, chunk length is 8 bytes
	}
    }
    
    
    public static class UUID{
	public static UUID nilValue = new UUID(0,(short)0,(short)0,new byte[]{0,0,0,0,0,0,0,0});
	
	public int data1;
	public short data2;
	public short data3;
	public byte[] data4;


	static UUID randomUUID(){
	    java.util.UUID uuid = java.util.UUID.randomUUID();
	    return new UUID(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
	}
	
	
	public UUID(){}
	public UUID(String uuidStr){ initWithString(uuidStr); }
	public UUID(int d1, short d2, short d3, byte[] d4){
	    data1 = d1; data2 = d2; data3 = d3; data4 = d4;
	}
	public UUID(long l1, long l2){
	    data1 = (int)((l1>>>32)&0xFFFFFFFF);
	    data2 = (short)((l1>>>16)&0xFFFF);
	    data3 = (short)(l1&0xFFFF);
	    data4 = new byte[8];
	    for(int i=0; i<8; i++) data4[i] = (byte)((l2>>>(8*(7-i)))&0xFF);
	}
	
	public boolean equals(Object obj){
	    if(!(obj instanceof UUID)) return super.equals(obj);
	    UUID uuid = (UUID)obj;
	    if(data1 != uuid.data1 ||
	       data2 != uuid.data2 ||
	       data3 != uuid.data3 ) return false;
	    for(int i=0; i<data4.length; i++)
		if(data4[i] != uuid.data4[i]) return false;
	    return true;
	}
	
	public int hashCode(){ return data1; }
	
	public String toString(){
	    return "UUID: "+hex(data1)+"-"+hex(data2)+"-"+hex(data3)+"-"+hex(data4);
	}
	
	
	public void initWithString(String uuidStr){
	    // ex. "A828C015-09F5-477c-8665-F0482F5D6996"
	    uuidStr = uuidStr.replaceAll("-","");
	    if(uuidStr.length()!=32){
		IOut.err("wrong input string for UUID : "+uuidStr);
		return;
	    }
	    
	    String str1 = uuidStr.substring(0,8);
	    String str2 = uuidStr.substring(8,12);
	    String str3 = uuidStr.substring(12,16);
	    String str4 = uuidStr.substring(16,32);
	    
	    //uuid.data1 = Integer.decode(str1,16).intValue();
	    //uuid.data2 = Short.parseShort(str2,16);
	    //uuid.data3 = Short.parseShort(str3,16);
	    //uuid.data4 = new byte[8];
	    //for(int i=0; i<8; i++) uuid.data4[i] = Byte.parseByte("0x"+str4.substring(i*2,i*2+2),16);
	    try{
		data1 = hexStringToInt(str1);
		data2 = hexStringToShort(str2);
		data3 = hexStringToShort(str3);
		data4 = hexStringToBytes(str4);
	    }
	    catch(NumberFormatException e){
		e.printStackTrace();
	    }
	}
	
	public void write(OutputStream os, CRC32 crc) throws IOException{
	    writeInt32(os, data1, crc);
	    writeInt16(os, data2, crc);
	    writeInt16(os, data3, crc);
	    writeBytes(os, data4, crc);
	}
	
	public static byte[] hexStringToBytes(String str) throws NumberFormatException{
	    byte[] b = new byte[str.length()/2];
	    for(int i=0; i<str.length()/2; i++) b[i] = hexStringToByte(str.substring(i*2,i*2+2));
	    return b;
	}
	public static int hexStringToInt(String str)throws NumberFormatException{
	    if(str.length()!=8) throw new NumberFormatException("invalid string \""+str+"\"");
	    int val=0;
	    for(int i=0; i<str.length(); i++){
		val = val<<4; val|=charToByte(str.charAt(i));
	    }
	    return val;
	}
	public static short hexStringToShort(String str)throws NumberFormatException{
	    if(str.length()!=4) throw new NumberFormatException("invalid string \""+str+"\"");
	    short val=0;
	    for(int i=0; i<str.length(); i++){
		val = (short)(val<<4); val|=charToByte(str.charAt(i));
	    }
	    return val;
	}
	public static byte hexStringToByte(String str){
	    if(str.length()!=2) throw new NumberFormatException("invalid string \""+str+"\"");
	    return (byte) (charToByte(str.charAt(0)) << 4 | charToByte(str.charAt(1)));
	}
	
	public static byte charToByte(char c) throws NumberFormatException{
	    if(c>='0' && c<='9') return (byte)(c-'0');
	    if(c>='a' && c<='f') return (byte)(c-'a'+10);
	    if(c>='A' && c<='F') return (byte)(c-'A'+10);
	    throw new NumberFormatException("invalid character \""+c+"\"");
	}
	
    }
    
    public static class UUIDIndex{
	public UUID id;
	public int i;
    }
    
    public static class Interval{
	public double v1,v2;
	
	public Interval(){}
	public Interval(double v1,double v2){ this.v1=v1; this.v2=v2; }
	public Interval(Interval interval){ v1=interval.v1; v2=interval.v2; }
	
	public boolean isIncreasing(){ return v1<v2; }
	//public boolean isIncreasing(){ return v1<=v2; }
	
	public boolean isValid(){ return true; } // no check; (!)
	
	public boolean includes(double t){ return includes(t,false); }
	public boolean includes(double t, boolean testOpenInterval){
	    if(testOpenInterval) return Math.min(v1,v2) < t && t < Math.max(v1,v2);
	    return Math.min(v1,v2) <= t && t <= Math.max(v1,v2);
	}
	public boolean includes(Interval interval){ return includes(interval,false); }
	public boolean includes(Interval interval, boolean properSubset){
	    if( !includes(interval.v1) || !includes(interval.v2) ) return false;
	    if(properSubset)
		if( !includes(interval.v1,true) && !includes(interval.v2,true) ) return false;
	    return true;
	}
	
	public void set(double v1, double v2){ this.v1=v1; this.v2=v2; }
	public void set(Interval i){ this.v1=i.v1; this.v2=i.v2; }
	
	public boolean equals(Interval interval){
	    if(v1 != interval.v1) return false;
	    if(v2 != interval.v2) return false;
	    return true;
	}
	
	public void reverse(){
	    double tmp = v1;
	    v1 = v2;
	    v2 = tmp;
	}
	
	public double normalizedParameterAt(double t){
	    double x = v1;
	    if(v1!=v2) x = (t==v2)?1.0:(t-v1)/(v2-v1);
	    return x;
	}
	
	public double parameterAt(double t){ return (1.0-t)*v1 + t*v2; }
	
	public double min(){ return Math.min(v1,v2); }
	public double max(){ return Math.max(v1,v2); }
	
	public double length(){ return v2-v1; }
	
	public void intersection(Interval interval){
	    double a = min();
	    double b = interval.min();
	    double mn = (a>=b)?a:b;
	    a = max();
	    b = interval.max();
	    double mx = (a<=b)?a:b;
	    if(mn<=mx) set(mn,mx);
	    //else set(0,0);
	}
	
	public String toString(){ return "["+v1+","+v2+"]"; }
	
    }
    
    public static class Xform{
	public double[][] xform;
	public Xform(){ xform = new double[4][4]; }
	public Xform(double[][] xf){ xform = xf; }
	
	public boolean isZero(){
	    for(int i=0; i<4; i++)
		for(int j=0; j<4; j++) if(xform[i][j]!=0) return false;
	    return true;
	}
	
	public void identity(){
	    for(int i=0; i<4; i++)
		for(int j=0; j<4; j++)
		    if(i==j) xform[i][j]=1.0;
		    else xform[i][j] = 0.0;
	}

	public String toString(){
	    String str = "[";
	    for(int i=0; i<4; i++){
		for(int j=0; j<4; j++){
		    str += String.valueOf(xform[i][j]);
		    if(i<3 || j<3){ str += ","; }
		}
	    }
	    str += "]";
	    return str;
	}
    }
    
    public static class Rect{
	int left;
	int top;
	int right;
	int bottom;
    }
    
    public static class Annotation2Text{
	public String text;
	public Rect rect;
	public Annotation2Text(String s){ 
	    //text=s;
	    text = insertCRToLF(s);
	}
	public String insertCRToLF(String s){
	    if(s==null) return s;
	    String out = "";
	    int start = 0;
	    int idx = s.indexOf("\n", start);
	    while(idx>=0 && idx<s.length() && start<s.length()){
		out+=s.substring(start, idx);
		if(idx==0 || idx>0 && s.charAt(idx-1)!='\r'){ out+="\r"; }
		out+="\n";
		start = idx+1;
		if(start<s.length()){ idx = s.indexOf("\n", start); }
		else{ idx=-1; }
	    }
	    if(idx<0 && start<s.length()){ out+=s.substring(start); }
	    return out;
	}
    }
    
    
    public static class ClassRegistry{
	public static HashMap<UUID,Class<? extends RhinoObject> > map;
	
	public static void init(){
	    map = new HashMap<UUID,Class<? extends RhinoObject> >();
	    
	    put(RhinoObject.uuid,RhinoObject.class);
	    put(ObjectAttributes.uuid,ObjectAttributes.class);
	    put(Material.uuid,Material.class);
	    put(Texture.uuid,Texture.class);
	    put(Bitmap.uuid,Bitmap.class);
	    put(TextureMapping.uuid,TextureMapping.class);
	    put(Linetype.uuid,Linetype.class);
	    put(Layer.uuid,Layer.class);
	    put(Group.uuid,Group.class);
	    put(Font.uuid,Font.class);
	    put(DimStyle.uuid,DimStyle.class);
	    put(Light.uuid,Light.class);
	    put(HatchPattern.uuid,HatchPattern.class);
	    put(InstanceDefinition.uuid,InstanceDefinition.class);
	    put(Geometry.uuid,Geometry.class);
	    put(HistoryRecord.uuid,HistoryRecord.class);
	    put(UserData.uuid,UserData.class);
	    put(Annotation.uuid,Annotation.class);
	    put(Annotation2.uuid,Annotation2.class);
	    put(Curve.uuid,Curve.class);
	    put(Surface.uuid,Surface.class);
	    put(LinearDimension.uuid,LinearDimension.class);
	    put(RadialDimension.uuid,RadialDimension.class);
	    put(AngularDimension.uuid,AngularDimension.class);
	    put(TextEntity.uuid,TextEntity.class);
	    put(Leader.uuid,Leader.class);
	    put(AnnotationTextDot.uuid,AnnotationTextDot.class);
	    put(AnnotationArrow.uuid,AnnotationArrow.class);
	    put(TextExtra.uuid,TextExtra.class);
	    put(DimensionExtra.uuid,DimensionExtra.class);
	    put(LinearDimension2.uuid,LinearDimension2.class);
	    put(RadialDimension2.uuid,RadialDimension2.class);
	    put(AngularDimension2.uuid,AngularDimension2.class);
	    put(TextEntity2.uuid,TextEntity2.class);
	    put(Leader2.uuid,Leader2.class);
	    put(TextDot.uuid,TextDot.class);
	    put(OrdinateDimension2.uuid,OrdinateDimension2.class);
	    put(AngularDimension2Extra.uuid,AngularDimension2Extra.class);
	    put(ArcCurve.uuid,ArcCurve.class);
	    put(Extrusion.uuid,Extrusion.class);
	    put(WindowsBitmap.uuid,WindowsBitmap.class);
	    put(EmbeddedBitmap.uuid,EmbeddedBitmap.class);
	    put(WindowsBitmapEx.uuid,WindowsBitmapEx.class);
	    put(BrepVertex.uuid,BrepVertex.class);
	    put(BrepEdge.uuid,BrepEdge.class);
	    put(BrepTrim.uuid,BrepTrim.class);
	    put(BrepLoop.uuid,BrepLoop.class);
	    put(BrepFace.uuid,BrepFace.class);
	    put(Brep.uuid,Brep.class);
	    put(BrepRegionTopologyUserData.uuid,BrepRegionTopologyUserData.class);
	    put(BrepFaceSide.uuid,BrepFaceSide.class);
	    put(BrepRegion.uuid,BrepRegion.class);
	    put(CurveOnSurface.uuid,CurveOnSurface.class);
	    put(DetailView.uuid,DetailView.class);
	    put(DimStyleExtra.uuid,DimStyleExtra.class);
	    put(HatchExtra.uuid,HatchExtra.class);
	    put(Hatch.uuid,Hatch.class);
	    put(InstanceRef.uuid,InstanceRef.class);
	    put(LayerExtensions.uuid,LayerExtensions.class);
	    put(CurveProxy.uuid,CurveProxy.class);
	    put(LineCurve.uuid,LineCurve.class);
	    put(Mesh.uuid,Mesh.class);
	    put(MeshVertexRef.uuid,MeshVertexRef.class);
	    put(MeshEdgeRef.uuid,MeshEdgeRef.class);
	    put(MeshFaceRef.uuid,MeshFaceRef.class);
	    put(MeshNgonUserData.uuid,MeshNgonUserData.class);
	    put(NurbsCurve.uuid,NurbsCurve.class);
	    put(NurbsSurface.uuid,NurbsSurface.class);
	    put(NurbsCage.uuid,NurbsCage.class);
	    put(MorphControl.uuid,MorphControl.class);
	    put(OffsetSurface.uuid,OffsetSurface.class);
	    put(PlaneSurface.uuid,PlaneSurface.class);
	    put(ClippingPlaneSurface.uuid,ClippingPlaneSurface.class);
	    put(PointCloud.uuid,PointCloud.class);
	    put(Point.uuid,Point.class);
	    put(PointGrid.uuid,PointGrid.class);
	    put(PolyCurve.uuid,PolyCurve.class);
	    put(PolyEdgeSegment.uuid,PolyEdgeSegment.class);
	    put(PolyEdgeCurve.uuid,PolyEdgeCurve.class);
	    put(PolylineCurve.uuid,PolylineCurve.class);
	    put(RevSurface.uuid,RevSurface.class);
	    put(SumSurface.uuid,SumSurface.class);
	    put(SurfaceProxy.uuid,SurfaceProxy.class);
	    put(UnknownUserData.uuid,UnknownUserData.class);
	    put(Viewport.uuid,Viewport.class);
	    
	    // old open nurbs classes         
	    final String tlNurbsCurveUUID = "5EAF1119-0B51-11d4-BFFE-0010830122F0";
	    final String oldNurbsCurveUUID = "76A709D5-1550-11d4-8000-0010830122F0";
	    final String tlNurbsSurfaceUUID = "4760C817-0BE3-11d4-BFFE-0010830122F0";
	    final String oldNurbsSurfaceUUID = "FA4FD4B5-1613-11d4-8000-0010830122F0";
	    final String oldPolyCurveUUID = "EF638317-154B-11d4-8000-0010830122F0";
	    final String oldTrimmedSurfaceUUID = "0705FDEF-3E2A-11d4-800E-0010830122F0";
	    final String oldBRepUUID = "2D4CFEDB-3E2A-11d4-800E-0010830122F0";
	    final String tlBRepUUID = "F06FC243-A32A-4608-9DD8-A7D2C4CE2A36";
	    final String tlRevSurfaceUUID = "0A8401B6-4D34-4b99-8615-1B4E723DC4E5";
	    final String tlSumSurfaceUUID = "665F6331-2A66-4cce-81D0-B5EEBD9B5417";
	    
	    put(tlNurbsCurveUUID, NurbsCurve.class);
	    put(oldNurbsCurveUUID, NurbsCurve.class);
	    put(tlNurbsSurfaceUUID, NurbsSurface.class);
	    put(oldNurbsSurfaceUUID, NurbsSurface.class);
	    put(oldPolyCurveUUID, PolyCurve.class);
	    put(oldTrimmedSurfaceUUID, Brep.class);
	    put(oldBRepUUID, Brep.class);
	    put(tlBRepUUID, Brep.class);
	    put(tlRevSurfaceUUID, RevSurface.class);
	    put(tlSumSurfaceUUID, SumSurface.class);
	    
	}
	
	public static void put(String uuid, Class<? extends RhinoObject> cls){
	    put(new UUID(uuid), cls);
	}
	
	public static void put(UUID uuid, Class<? extends RhinoObject> cls){
	    if(map.containsKey(uuid)) IOut.err("UUID "+ uuid +" is already set @"+cls); //
	    //if(map.containsValue(cls)) IOut.p("Class "+ cls +" is already set"); //
	    map.put(uuid,cls);
	}
	
	public static Class<? extends RhinoObject> get(UUID uuid){
	    return map.get(uuid);
	    //IOut.p("get: map.size()="+map.size()); //
	    //Class<? extends RhinoObject> cls = map.get(uuid);
	    //IOut.p("class = "+cls); //
	    //return cls;
	}
	
    }
    
    
    public static class StartSection{
	public String information;
	public StartSection(String info){ information=info; }
    }
    
    
    
    public static class RhinoObject{
	public static final String uuid = "60B5DBBD-E660-11d3-BFE4-0010830122F0";
	
	
	final static int objectTypeUnknown =                   0;
	final static int objectTypePoint =                     1;
	final static int objectTypePointset =                  2;
	final static int objectTypeCurve =                     4;
	final static int objectTypeSurface =                   8;
	final static int objectTypeBrep =                   0x10;
	final static int objectTypeMesh =                   0x20;
	final static int objectTypeLayer =                  0x40;
	final static int objectTypeMaterial =               0x80;
	final static int objectTypeLight =                 0x100;
	final static int objectTypeAnnotation =            0x200;
	final static int objectTypeUserData =              0x400;
	final static int objectTypeInstanceDefinition =    0x800;
	final static int objectTypeInstanceReference =    0x1000;
	final static int objectTypeTextDot =              0x2000;
	final static int objectTypeGrip =                 0x4000;
	final static int objectTypeDetail =               0x8000;
	final static int objectTypeHatch =               0x10000;
	final static int objectTypeMorphControl =        0x20000;
	final static int objectTypeLoop =                0x80000;
	final static int objectTypePolysrfFilter =      0x200000;
	final static int objectTypeEdgeFilter =         0x400000;
	final static int objectTypePolyledgeFilter =    0x800000;
	final static int objectTypeMeshVertex =        0x1000000;
	final static int objectTypeMeshEdge =          0x2000000;
	final static int objectTypeMeshFace =          0x4000000;
	final static int objectTypeCage =              0x8000000;
	final static int objectTypePhantom =          0x10000000;
	final static int objectTypeClipPlane =        0x20000000;
	final static int objectTypeBeam =             0x40000000; // obsolete
	final static int objectTypeExtrusion =        0x40000000;
	final static int objectTypeAny =              0xFFFFFFFF;
	
	
	
	public ObjectAttributes attributes=null;
	
	public UserData[] userDataList;
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	
	
	public int getType(){ return objectTypeUnknown; }
	
	
	public void read(Rhino3dmFile context, byte[] b)throws IOException{
	    read(context, new ByteArrayInputStream(b));
	}
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{}
	
	
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{}
	
	
	
	public void setAttributes(ObjectAttributes attr){ attributes = attr; }
	
	public IObject createIObject(Rhino3dmFile context, IServerI s){ return null; } // instantiate for igeo
	
	public void setAttributesToIObject(Rhino3dmFile context, IObject e){
	    if(attributes==null){
		IOut.err("no attributes is set");
		return;
	    }
	    
	    ILayer layer=null;
	    if(attributes.name!=null){
		e.name(attributes.name);
		IOut.debug(10,"object name : "+attributes.name); //
	    }
	    if(context!=null &&context.layers != null&&
	       attributes.layerIndex >= 0 &&
	       attributes.layerIndex < context.layers.length){
		layer = context.layers[attributes.layerIndex].ilayer;
		layer.add(e);
		IOut.debug(10,"layer name : "+layer.name()); //
		
		if(!layer.isVisible()) e.hide();
	    }
	    
	    if(attributes.colorSource==colorSourceFromObject &&
	       attributes.color != null){
		e.setColor(attributes.color);
		IOut.debug(10,"set object color : "+attributes.color);
		IOut.debug(10,"set object color : <"+attributes.color.getRed()+","+
			   attributes.color.getGreen()+","+
			   attributes.color.getBlue()+","+
			   attributes.color.getAlpha()+">");
		       
	    }
	    else if(layer!=null){ // if(attributes.colorSource==ObjectAttributes.colorSourceFromLayer){ // all other
		e.setColor(layer.getColor());
		IOut.debug(10,"set layer color : "+layer.getColor()); //
	    }
	    
	    if(!attributes.visible){
		e.hide();
	    }
	    
	    IOut.debug(10,"object color : "+e.getColor()); //
	}
	
    }
    
    
    public static class Material extends RhinoObject{
	public static final String uuid = "60B5DBBC-E660-11d3-BFE4-0010830122F0";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	
	public int getType(){ return objectTypeMaterial; }
	
	public UUID materialId;
	public int materialIndex;
	public String materialName;
	public String flamingoLibrary;
	public IColor ambient;
	public IColor diffuse;
	public IColor emission;
	public IColor specular;
	public IColor reflection;
	public IColor transparent;
	public double indexOfRefraction;
	public double reflectivity;
	public double shine;
	public double transparency;
	public boolean shared;
	public ArrayList<Texture> textures;
	public ArrayList<UUIDIndex> materialChannel;
	public UUID pluginId;
	
	
	IMaterial imaterial;
	
	
	public Material(){
	    materialIndex=0;
	    materialId = UUID.nilValue;
	    materialName = null;
	    flamingoLibrary = null;
	    ambient = new IColor(0,0,0);
	    diffuse = new IColor(128,128,128);
	    emission = new IColor(0,0,0);
	    specular = new IColor(255,255,255);
	    reflection = new IColor(255,255,255);
	    transparent = new IColor(255,255,255);
	    indexOfRefraction = 1.0;
	    reflectivity = 0.0;
	    shine = 0.0;
	    transparency = 0.0;
	    textures = null;
	    pluginId = UUID.nilValue;
	    shared=false;
	    materialChannel = null;
	}
	
	public Material(IBasicMaterial mat){
	    this();
	    
	    if(mat.ambient!=null) ambient = mat.ambient;
	    if(mat.diffuse!=null) diffuse = mat.diffuse;
	    if(mat.emission!=null) emission = mat.emission;
	    if(mat.specular!=null) specular = mat.specular;
	    if(mat.reflection!=null) reflection = mat.reflection;
	    if(mat.transparent!=null) transparent = mat.transparent;
	    indexOfRefraction = mat.refraction;
	    reflectivity = mat.reflectivity;
	    shine = mat.shine;
	    transparency = mat.transparency;

	    imaterial = mat;
	}
	
	
	public void read(Rhino3dmFile context, InputStream is) throws IOException{
	    
	    int[] version = readChunkVersion(is);
	    
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    if(majorVersion==1){
		readV3(context,is,minorVersion);
	    }
	    else if(majorVersion==2){
		// v4 material
		
		Chunk ck = readChunk(is);
		if(ck.content==null){
		    IOut.err("chunk content is null"); //
		    throw new IOException("chunk content is null");
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(ck.content);
		majorVersion = readInt(bais);
		minorVersion = readInt(bais);
		
		materialId = readUUID(bais);
		materialIndex = readInt(bais);
		materialName = readString(bais);
		pluginId = readUUID(bais);
		ambient = readColor(bais);
		diffuse = readColor(bais);
		emission = readColor(bais);
		specular = readColor(bais);
		reflection = readColor(bais);
		transparent = readColor(bais);

		if(context.openNurbsVersion < 200912010 &&
		   transparent.getRed()==128 &&
		   transparent.getGreen()==128 &&
		   transparent.getBlue()==128 ){
		    transparent = diffuse;
		}
		
		indexOfRefraction = readDouble(bais);
		reflectivity = readDouble(bais);
		shine = readDouble(bais);
		transparency = readDouble(bais);
		
		
		IOut.debug(100,"materialId = "+materialId);
		IOut.debug(100,"materialIndex = "+materialIndex);
		IOut.debug(100,"materialName = "+materialName);
		IOut.debug(100,"pluginId = "+pluginId);
		IOut.debug(100,"ambient = "+ambient);
		IOut.debug(100,"diffuse = "+diffuse);
		IOut.debug(100,"emission = " + emission);
		IOut.debug(100,"specular = " + specular);
		IOut.debug(100,"reflaction = " + reflection);
		IOut.debug(100,"transparent = " + transparent);
		
		IOut.debug(100,"indexOfRefraction = " + indexOfRefraction);
		IOut.debug(100,"reflectivity = " + reflectivity);
		IOut.debug(100,"shine = " + shine);
		IOut.debug(100,"transparency = " + transparency);
		
		
		
		Chunk textureChunk = readChunk(bais);
		if(ck.content==null){
		    IOut.err("chunk content is null"); //
		    throw new IOException("chunk content is null");
		}
		ByteArrayInputStream tis = new ByteArrayInputStream(textureChunk.content);
		
		int textureMajorVersion = readInt(tis);
		int textureMinorVersion = readInt(tis);
		
		if(textureMajorVersion==1){
		    int count = readInt(tis);
		    for(int i=0; i<count; i++){
			// reading texture
			// not implemented
		    }
		}

		if(minorVersion >= 1){
		    flamingoLibrary = readString(bais);
		    
		    if(minorVersion>=2){
			materialChannel = readArrayUUIDIndex(bais);
		    }
		}
		
	    }

	    
	}
	
	public void readV3(Rhino3dmFile context, InputStream is, int minorVersion) throws IOException{
	    
	    //IOut.err(); //
	    IOut.debug(0, "reading Rhino V3 file");
	    
	    ambient = readColor(is);
	    diffuse = readColor(is);
	    emission = readColor(is);
	    specular = readColor(is);
	    
	    shine = readDouble(is);
	    final double maxShine=255.0;
	    if(shine<0) shine=0; else if(shine>maxShine) shine=maxShine;
	    
	    transparency = readDouble(is);
	    if(transparency<0) transparency=0; else if(transparency>1.) transparency=1.;
	    
	    readByte(is); // OBSOLETE; skipped; m_casts_shadows
	    readByte(is); // OBSOLETE; skipped; m_shows_shadows
	    readByte(is); // OBSOLETE; skipped; m_wire_mode
	    readByte(is); // OBSOLETE; skipped; m_wire_density
	    
	    readColor(is); // OBSOLETE; skipped; m_wire_color
	    
	    readShort(is); // OBSOLETE;
	    readShort(is); // OBSOLETE;
	    readDouble(is); // OBSOLETE;
	    readDouble(is); // OBSOLETE;

	    String str = readString(is); // textureBitmapFileName
	    int i = readInt(is);
	    int j = readInt(is); // textureBitmapIndex
	    
	    if(str!=null && str.length()>0){
		// adding texture
		// not implemented yet
	    }
	    
	    str = readString(is); // bumpBitmapFileName
	    i = readInt(is);
	    j = readInt(is); // bumpBitmapIndex
	    
	    double bumpScale = readDouble(is);
	    if(str!=null && str.length()>0){
		// adding bump map texture
		// not implemented yet
	    }
	    
	    str = readString(is); // emapBitmapFileName
	    i = readInt(is);
	    j = readInt(is);  // emapBitmapIndex
	    
	    if(str!=null && str.length()>0){
		// adding emap texture
		// not implemented yet
	    }
	    
	    materialIndex = readInt(is);
	    pluginId = readUUID(is);
	    flamingoLibrary = readString(is);
	    materialName = readString(is);
	    
	    if(minorVersion>=1){
		// v 1.1
		materialId = readUUID(is);
		reflection = readColor(is);
		transparent = readColor(is);
		indexOfRefraction = readDouble(is);
	    }
	    else{
		materialId = UUID.randomUUID();
	    }
	}
	
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc) throws IOException{
	    
	    // write only V4 file format. V2, V3 is ignored.
	    
	    writeChunkVersion(os, 2, 0, crc);
	    
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk, 1, 2);
	    
	    writeUUID(cos, materialId, cos.getCRC());
	    writeInt(cos, materialIndex, cos.getCRC());
	    writeString(cos, materialName, cos.getCRC());
	    
	    writeUUID(cos, pluginId, cos.getCRC());
	    
	    writeColor(cos, ambient, cos.getCRC());
	    writeColor(cos, diffuse, cos.getCRC());
	    writeColor(cos, emission, cos.getCRC());
	    writeColor(cos, specular, cos.getCRC());
	    writeColor(cos, reflection, cos.getCRC());
	    writeColor(cos, transparent, cos.getCRC());
	    
	    writeDouble(cos, indexOfRefraction, cos.getCRC());
	    writeDouble(cos, reflectivity, cos.getCRC());
	    writeDouble(cos, shine, cos.getCRC());
	    writeDouble(cos, transparency, cos.getCRC());
	    
	    // textures
	    ChunkOutputStream tos = new ChunkOutputStream(tcodeAnonymousChunk, 1, 0);
	    // skipped; no texture
	    writeInt( tos, 0,  tos.getCRC());
	    
	    writeChunk(cos, tos.getChunk());
	    
	    writeString(cos, flamingoLibrary, cos.getCRC());
	    writeArrayUUIDIndex(context, cos, materialChannel, cos.getCRC());
	    
	    writeChunk(os, cos.getChunk());
	    
	}
	
	
	public String toString(){
	    return
		"materialId = " + materialId +"\n"+
		"materialIndex = " + materialIndex + "\n"+
		"materialName = " + materialName + "\n" +
		"flamingoLibrary = "+ flamingoLibrary + "\n" +
		"ambient = "+ambient + "\n" +
		"diffuse = "+diffuse + "\n" +
		"emission = "+emission + "\n" +
		"specular = "+specular + "\n" +
		"reflection = "+reflection + "\n" +
		"transparent = "+transparent + "\n" +
		"indexOfRefraction = " + indexOfRefraction +"\n"+
		"reflectivity = "+reflectivity + "\n" +
		"shine = " + shine + "\n" +
		"transparency = " + transparency + "\n" +
		"shared = " + shared + "\n" +
		"textures num = "+(textures==null?0:textures.size()) + "\n" +
		"materialChannel num = "+(materialChannel==null?0:materialChannel.size()) + "\n"+
		"pluginId = "+pluginId ;
	}
	
    }
    
    public static class MaterialRef{
	public UUID pluginId;
	public UUID materialId;
	public UUID materialBackfaceId;
	/*char*/ public int materialSource;
	public char reserved1;
	public char reserved2;
	public char reserved3;
	public int materialIndex;
	public int materialBackfaceIndex;
	
	public MaterialRef(){
	    materialIndex = -1;
	    materialBackfaceIndex=-1;
	    materialSource = materialSourceFromLayer;
	}
	
	
	public void read(Rhino3dmFile context, InputStream is) throws IOException{
	    Chunk ck = readChunk(is);
	    if(ck.content==null){
		IOut.err("chunk content is null"); //
		throw new IOException("chunk content is null");
	    }
	    ByteArrayInputStream bais = new ByteArrayInputStream(ck.content);
	    int majorVersion = readInt(bais);
	    int minorVersion = readInt(bais);
	    if(majorVersion!=1){
		IOut.err("invalid major version : "+majorVersion); //
		throw new IOException("invalid major version"+String.valueOf(majorVersion));
	    }
	    pluginId = readUUID(bais);
	    materialId = readUUID(bais);
	    // skip obsolete mappingChannel data
	    int mappingChannelCount = readInt(bais);
	    for(int i=0; i<mappingChannelCount; i++){
		//readMappingChannel(bais);
		MappingChannel mchan = new MappingChannel();
		mchan.read(context,bais);
	    }
	    if(minorVersion>=1){
		materialBackfaceId = readUUID(bais);
		
		// materialSource should be read
		materialSource = readInt(bais);
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc) throws IOException{
	    
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk, 1, 1);
	    writeUUID(cos, pluginId, cos.getCRC());
	    writeUUID(cos, materialId, cos.getCRC());
	    writeInt32(cos, 0, cos.getCRC());
	    writeUUID(cos, materialBackfaceId, cos.getCRC());
	    writeInt32(cos, materialSource, cos.getCRC());
	    /*
	    writeUUID(cos, pluginId, crc);
	    writeUUID(cos, materialId, crc);
	    writeInt32(cos, 0, crc);
	    writeUUID(cos, materialBackfaceId, crc);
	    writeInt32(cos, materialSource, crc);
	    */
	    
	    writeChunk(os, cos.getChunk());
	}
	
    }
    
    
    public static class MappingChannel{
	public UUID mappingId;
	public int mappingIndex;
	public int mappingChannelId;
	public Xform objectXform;
	
	public void read(Rhino3dmFile context, InputStream is) throws IOException{
	    Chunk ck = readChunk(is);
	    if(ck.content==null){
		IOut.err("chunk content is null"); //
		throw new IOException("chunk content is null");
	    }
	    ByteArrayInputStream bais = new ByteArrayInputStream(ck.content);
	    int majorVersion = readInt(bais);
	    int minorVersion = readInt(bais);
	    if(majorVersion!=1){
		IOut.err("invalid majorVersion : "+majorVersion);
		throw new IOException("invalid major version"+String.valueOf(majorVersion));
	    }
	    mappingChannelId = readInt(bais);
	    mappingId = readUUID(bais);
	    if(minorVersion >= 1){
		objectXform = readXform(bais);
		if(context.openNurbsVersion < 200610030 && objectXform.isZero()){
		    objectXform.identity();
		}
	    }
	}
    }
    
    public static class Texture extends RhinoObject{
	public static final String uuid = "D6FF106D-329B-4f29-97E2-FD282A618020";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	
	//public int getType(){ return objectTypeUnknown; }
		
	public enum MappingChannel { TCChannel, DefaultChannel, SrfPChannel, EMapChannel }
	public enum Type { NoTextureType, BumpTexture, TransparencyTexture, EMapTexture, Force32BitTextureType };
	public enum Mode { NoTextureMode, ModulateTexture, DecalTexture, BlendTexture, Force32BitTextureMode }
	public enum Filter { NearestFilter, LinearFilter, Force32BitTextureFilter};
	public enum Wrap { RepeatWrap, ClampWrap, Force32BitTextureWrap };
	
	public UUID textureId;
	public int mappingChannelId;
	public String filename;
	public boolean filenameRelativePath;
	public boolean on;
	public Type type;
	public Mode mode;
	public Filter minFilter;
	public Filter magFilter;
	public Wrap wrapU, wrapV, wrapW;
	public boolean applyUVW;
	public Xform uvw;
	public IColor borderColor;
	public IColor transparentColor;
	public UUID transparencyTextureId;
	public Interval bumpScale;
	
	public double blendConstantA;
	public double[] blendA;
	public double blendConstantRGB;
	public double[] blendRGB;
	public int blendOrder;
    }
	
    
    
    public static class RenderingAttributes{
	public ArrayList<MaterialRef> materials;
	public RenderingAttributes(){ materials = new ArrayList<MaterialRef>(); }
	
	public void read(Rhino3dmFile context, InputStream is) throws IOException{
	    
	    Chunk chunk = readChunk(is);

	    //if(chunk.content==null){
	    if(chunk==null || chunk.content==null){ // chunk==null added 20121122
		IOut.err("no chunk content"); //
		throw new IOException("no chunk content"); 
	    }
	    
	    ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	    int majorVersion = readInt(bais);
	    int minorVersion = readInt(bais);
	    if(majorVersion!=1)
		throw new IOException("invalid major version : "+String.valueOf(majorVersion));
	    
	    int count = readInt(bais);
	    
	    for(int i=0; i<count; i++){
		MaterialRef mref = new MaterialRef();
		mref.read(context, bais);
		materials.add(mref);
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc) throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk,1,0);
	    int num=0;
	    if(materials!=null) num = materials.size();
	    writeInt32(cos,num,cos.getCRC());
	    for(int i=0; i<num; i++) materials.get(i).write(context, cos, cos.getCRC());
	    writeChunk(os, cos.getChunk());
	}
    }
    
    public static class Bitmap extends RhinoObject{
	public static final String uuid = "390465E9-3721-11d4-800B-0010830122F0";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    
    public static class TextureMapping extends RhinoObject{
	public static final String uuid = "32EC997A-C3BF-4ae5-AB19-FD572B8AD554";

	public enum Type{
	    NoMapping, SrfpMapping, PlaneMapping, CylinderMapping, SphereMapping, BoxMapping,
		MeshMappingPrimitive, SrfMappingPrimitive, BrepMappingPrimitive
		}
	
	public static Type type(int i){
	    switch(i){
	    case 0: return Type.NoMapping;
	    case 1: return Type.SrfpMapping;
	    case 2: return Type.PlaneMapping;
	    case 3: return Type.CylinderMapping;
	    case 4: return Type.SphereMapping;
	    case 5: return Type.BoxMapping;
	    case 6: return Type.MeshMappingPrimitive;
	    case 7: return Type.SrfMappingPrimitive;
	    case 8: return Type.BrepMappingPrimitive;
	    }
	    return Type.NoMapping;
	}
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    
    public static class Linetype extends RhinoObject{
	public static final String uuid = "26F10A24-7D13-4f05-8FDA-8E364DAF8EA6";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeUnknown; }
    }
    
    
    public static class Properties{
	public int openNurbsVersion=-1;
	public byte[] notes;
	public byte[] previewImage;
	public byte[] application;
	public byte[] compressedPreviewImage;
	public byte[] revisionHistory;
	
	public void setOpenNurbsVersion(Chunk c){ openNurbsVersion=c.body; }
	public void setNotes(Chunk c){ notes=c.content; }
	public void setPreviewImage(Chunk c){ previewImage=c.content; }
	public void setApplication(Chunk c){ application=c.content; }
	public void setCompressedPreviewImage(Chunk c){ compressedPreviewImage=c.content; }
	public void setRevisionHistory(Chunk c){ revisionHistory=c.content; }
    }


    public static class UnitSystem{
	
	
	/**********************************************************************
	 * unit types (int)
	 **********************************************************************/
	
	public final static int no_unit_system =  0;
	// atomic distances
	public final static int angstroms = 12;  // 1.0e-10 meters
	
	// SI units
	public final static int nanometers = 13;  // 1.0e-9 meters
	public final static int microns = 1;  // 1.0e-6 meters
	public final static int millimeters = 2;  // 1.0e-3 meters
	public final static int centimeters = 3;  // 1.0e-2 meters
	public final static int decimeters = 14;  // 1.0e-1 meters
	public final static int meters = 4;
	public final static int dekameters = 15;  // 1.0e+1 meters
	public final static int hectometers = 16;  // 1.0e+2 meters
	public final static int kilometers = 5;  // 1.0e+3 meters
	public final static int megameters = 17;  // 1.0e+6 meters
	public final static int gigameters = 18;  // 1.0e+9 meters
	
	// english distances
	public final static int microinches = 6;  //    2.54e-8 meters (1.0e-6 inches)
	public final static int mils = 7;  //    2.54e-5 meters (0.001 inches)
	public final static int inches = 8;  //    0.0254  meters
	public final static int feet = 9;  //    0.3408  meters (12 inches)
	public final static int yards = 19;  //    0.9144  meters (36 inches)
	public final static int miles = 10;  // 1609.344   meters (5280 feet)
	
	// printer distances
	public final static int printer_point = 20;  // 1/72 inches (computer points)
	public final static int printer_pica = 21;  // 1/6 inches  (computer picas)
	
	// terrestrial distances
	public final static int nautical_mile = 22; // 1852 meters
	// astronomical distances
	public final static int astronomical = 23; // 1.4959787e+11 // http://en.wikipedia.org/wiki/Astronomical_unit
	public final static int lightyears = 24; // 9.4607304725808e+15 // http://en.wikipedia.org/wiki/Light_year
	public final static int parsecs = 25; // 3.08567758e+16  // http://en.wikipedia.org/wiki/Parsec
	// Custom unit systems
	public final static int custom_unit_system = 11; // x meters with x defined in ON_3dmUnitsAndTolerances.m_custom_unit_sca
	


	/**********************************************************************
	 * unit scale
	 **********************************************************************/
	
	public final static double no_unit_system_scale =  1.0; //?
	// atomic distances
	public final static double angstroms_scale = 1.0E-10;  // 1.0e-10 meters
	
	// SI units
	public final static double nanometers_scale = 1.0E-9;  // 1.0e-9 meters
	public final static double microns_scale = 1.0E-6;  // 1.0e-6 meters
	public final static double millimeters_scale = 1.0E-3;  // 1.0e-3 meters
	public final static double centimeters_scale = 1.0E-2;  // 1.0e-2 meters
	public final static double decimeters_scale = 1.0E-1;  // 1.0e-1 meters
	public final static double meters_scale = 1.0;
	public final static double dekameters_scale = 1.0E1;  // 1.0e+1 meters
	public final static double hectometers_scale = 1.0E2;  // 1.0e+2 meters
	public final static double kilometers_scale = 1.0E3;  // 1.0e+3 meters
	public final static double megameters_scale = 1.0E6;  // 1.0e+6 meters
	public final static double gigameters_scale = 1.0E9;  // 1.0e+9 meters
	
	// english distances
	public final static double microinches_scale = 2.54E-8;  //    2.54e-8 meters (1.0e-6 inches)
	public final static double mils_scale = 2.54E-5;  //    2.54e-5 meters (0.001 inches)
	public final static double inches_scale = 0.0254;  //    0.0254  meters
	public final static double feet_scale = 0.3408;  //    0.3408  meters (12 inches)
	public final static double yards_scale = 0.9144;  //    0.9144  meters (36 inches)
	public final static double miles_scale = 1609.344;  // 1609.344   meters (5280 feet)
	
	// printer distances
	public final static double printer_point_scale = 0.0254/72;  // 1/72 inches (computer points)
	public final static double printer_pica_scale = 0.0254/6;  // 1/6 inches  (computer picas)
	
	// terrestrial distances
	public final static double nautical_mile_scale = 1852; // 1852 meters
	// astronomical distances
	public final static double astronomical_scale = 1.4959787e+11; // 1.4959787e+11 // http://en.wikipedia.org/wiki/Astronomical_unit
	public final static double lightyears_scale = 9.4607304725808e+15; // 9.4607304725808e+15 // http://en.wikipedia.org/wiki/Light_year
	public final static double parsecs_scale = 3.08567758e+16; // 3.08567758e+16  // http://en.wikipedia.org/wiki/Parsec
	// Custom unit systems
	public final static double custom_unit_system_scale = 1; // x meters with x defined in ON_3dmUnitsAndTolerances.m_custom_unit_sca
	
	
	
	/**********************************************************************
	 * unit name
	 **********************************************************************/
	public final static String no_unit_system_name = "No Unit";
	// atomic distances
	public final static String angstroms_name = "Angstroms";  // 1.0e-10 meters
	
	// SI units
	public final static String nanometers_name = "Nanometers";  // 1.0e-9 meters
	public final static String microns_name = "Microns";  // 1.0e-6 meters
	public final static String millimeters_name = "Millimeters";  // 1.0e-3 meters
	public final static String centimeters_name = "Centimeters";  // 1.0e-2 meters
	public final static String decimeters_name = "Decimeters";  // 1.0e-1 meters
	public final static String meters_name = "Meters";
	public final static String dekameters_name = "Dekameters";  // 1.0e+1 meters
	public final static String hectometers_name = "Hectometers";  // 1.0e+2 meters
	public final static String kilometers_name = "Kilometers";  // 1.0e+3 meters
	public final static String megameters_name = "Megameters";  // 1.0e+6 meters
	public final static String gigameters_name = "Gigameters";  // 1.0e+9 meters
	
	// english distances
	public final static String microinches_name = "Microinches";  //    2.54e-8 meters (1.0e-6 inches)
	public final static String mils_name = "Mils";  //    2.54e-5 meters (0.001 inches)
	public final static String inches_name = "Inches";  //    0.0254  meters
	public final static String feet_name = "Feet";  //    0.3408  meters (12 inches)
	public final static String yards_name = "Yards";  //    0.9144  meters (36 inches)
	public final static String miles_name = "Miles";  // 1609.344   meters (5280 feet)
	
	// printer distances
	public final static String printer_point_name = "Points";  // 1/72 inches (computer points)
	public final static String printer_pica_name = "Picas";  // 1/6 inches  (computer picas)
	
	// terrestrial distances
	public final static String nautical_mile_name = "Nautical miles"; // 1852 meters
	// astronomical distances
	public final static String astronomical_name = "Astronomical units"; // 1.4959787e+11 // http://en.wikipedia.org/wiki/Astronomical_unit
	public final static String lightyears_name = "Lightyears"; // 9.4607304725808e+15 // http://en.wikipedia.org/wiki/Light_year
	public final static String parsecs_name = "Parsecs"; // 3.08567758e+16  // http://en.wikipedia.org/wiki/Parsec
	// Custom unit systems
	public final static String custom_unit_system_name = "Custom units"; // x meters with x defined in ON_3dmUnitsAndTolerances.m_custom_unit_scale
	
	
	public static String getName(int unitType){
	    switch(unitType){
	    case no_unit_system: return no_unit_system_name;
	    case angstroms: return angstroms_name;
	    case nanometers: return nanometers_name;
	    case microns: return microns_name;
	    case millimeters: return millimeters_name;
	    case centimeters: return centimeters_name;
	    case decimeters: return decimeters_name;
	    case meters: return meters_name;
	    case dekameters: return dekameters_name;
	    case hectometers: return hectometers_name;
	    case kilometers: return kilometers_name;
	    case megameters: return megameters_name;
	    case gigameters: return gigameters_name;
	    case microinches: return microinches_name;
	    case mils: return mils_name;
	    case inches: return inches_name;
	    case feet: return feet_name;
	    case yards: return yards_name;
	    case miles: return miles_name;
	    case printer_point: return printer_point_name;
	    case printer_pica: return printer_pica_name;
	    case nautical_mile: return nautical_mile_name;
	    case astronomical: return astronomical_name;
	    case lightyears: return lightyears_name;
	    case parsecs: return parsecs_name;
	    case custom_unit_system: return custom_unit_system_name;
	    }
	    
	    IOut.err("no such unit system type "+unitType);
	    //custom_unit_system
	    return custom_unit_system_name;
	}
	
	public static double getScale(int unitType){
	    switch(unitType){
	    case no_unit_system: return no_unit_system_scale;
	    case angstroms: return angstroms_scale;
	    case nanometers: return nanometers_scale;
	    case microns: return microns_scale;
	    case millimeters: return millimeters_scale;
	    case centimeters: return centimeters_scale;
	    case decimeters: return decimeters_scale;
	    case meters: return meters_scale;
	    case dekameters: return dekameters_scale;
	    case hectometers: return hectometers_scale;
	    case kilometers: return kilometers_scale;
	    case megameters: return megameters_scale;
	    case gigameters: return gigameters_scale;
	    case microinches: return microinches_scale;
	    case mils: return mils_scale;
	    case inches: return inches_scale;
	    case feet: return feet_scale;
	    case yards: return yards_scale;
	    case miles: return miles_scale;
	    case printer_point: return printer_point_scale;
	    case printer_pica: return printer_pica_scale;
	    case nautical_mile: return nautical_mile_scale;
	    case astronomical: return astronomical_scale;
	    case lightyears: return lightyears_scale;
	    case parsecs: return parsecs_scale;
	    case custom_unit_system: return custom_unit_system_scale;
	    }
	    
	    IOut.err("no such unit system type "+unitType);
	    //custom_unit_system
	    return custom_unit_system_scale;
	}

	
	public static int getType(IUnit unit){
	    switch(unit.type){
	    case NoUnits: return no_unit_system;
	    case Angstroms: return angstroms;
	    case Nanometers: return nanometers;
	    case Microns: return microns;
	    case Millimeters: return millimeters;
	    case Centimeters: return centimeters;
	    case Decimeters: return decimeters;
	    case Meters: return meters;
	    case Dekameters: return dekameters;
	    case Hectometers: return hectometers;
	    case Kilometers: return kilometers;
	    case Megameters: return megameters;
	    case Gigameters: return gigameters;
	    case Microinches: return microinches;
	    case Mils: return mils;
	    case Inches: return inches;
	    case Feet: return feet;
	    case Yards: return yards;
	    case Miles: return miles;
	    case Points: return printer_point;
	    case Picas: return printer_pica;
	    case NauticalMiles: return nautical_mile;
	    case AstronomicalUnits: return astronomical;
	    case Lightyears: return lightyears;
	    case Parsecs: return parsecs;
	    case CustomUnits: return custom_unit_system;
	    }
	    
	    IOut.err("no such unit system type "+unit.type);
	    //custom_unit_system
	    return millimeters; // default
	}
	
	public static IUnit.Type getIUnitType(int type){
	    switch(type){
	    case no_unit_system: return IUnit.Type.NoUnits;
	    case angstroms: return IUnit.Type.Angstroms;
	    case nanometers: return IUnit.Type.Nanometers;
	    case microns: return IUnit.Type.Microns;
	    case millimeters: return IUnit.Type.Millimeters;
	    case centimeters: return IUnit.Type.Centimeters;
	    case decimeters: return IUnit.Type.Decimeters;
	    case meters: return IUnit.Type.Meters;
	    case dekameters: return IUnit.Type.Dekameters;
	    case hectometers: return IUnit.Type.Hectometers;
	    case kilometers: return IUnit.Type.Kilometers;
	    case megameters: return IUnit.Type.Megameters;
	    case gigameters: return IUnit.Type.Gigameters;
	    case microinches: return IUnit.Type.Microinches;
	    case mils: return IUnit.Type.Mils;
	    case inches: return IUnit.Type.Inches;
	    case feet: return IUnit.Type.Feet;
	    case yards: return IUnit.Type.Yards;
	    case miles: return IUnit.Type.Miles;
	    case printer_point: return IUnit.Type.Points;
	    case printer_pica: return IUnit.Type.Picas;
	    case nautical_mile: return IUnit.Type.NauticalMiles;
	    case astronomical: return IUnit.Type.AstronomicalUnits;
	    case lightyears: return IUnit.Type.Lightyears;
	    case parsecs: return IUnit.Type.Parsecs;
	    case custom_unit_system: return IUnit.Type.CustomUnits;
	    }
	    
	    IOut.err("no such unit system type "+type);
	    //custom_unit_system
	    return IUnit.Type.CustomUnits;
	}
	
	public int unitSystem;
	public double customUnitScale;
	public String customUnitName;
	
	
	public UnitSystem(){
	    unitSystem = millimeters; // default
	    customUnitScale=1.0;
	    //customUnitName = getName(unitSystem); // should it have regular name in custom unit?
	}
	
	public UnitSystem(int unit_sys){
	    unitSystem = unit_sys;
	    customUnitScale = getScale(unitSystem);
	    //customUnitName = getName(unitSystem); // should it have regular name in custom unit?
	}
	
	public UnitSystem(IUnit unit){
	    unitSystem = getType(unit);
	    customUnitScale = getScale(unitSystem);
	    //customUnitName = getName(unitSystem); // should it have regular name in custom unit?
	}
	
	public IUnit.Type getIUnitType(){
	    return getIUnitType(unitSystem);
	}
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    Chunk chunk = readChunk(is);
	    if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
	    
	    is = new ByteArrayInputStream(chunk.content);
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		
		unitSystem = readInt(is);
		
		customUnitScale = readDouble(is);
		customUnitName = readString(is);
		
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    writeChunkVersion(cos, 1, 0, cos.getCRC());
	    
	    writeInt(cos,unitSystem,cos.getCRC());
	    writeDouble(cos,customUnitScale,cos.getCRC());
	    writeString(cos,customUnitName,cos.getCRC());
	    
	    writeChunk(os, cos.getChunk());
	}

	
	public String toString(){ return getName(unitSystem); }
	
	
    }
    
    public static class UnitsAndTolerances{
	UnitSystem unitSystem;
	
	double absoluteTolerance = 0.001; // default
	double angleTolerance = Math.PI/180; // default
	double relativeTolerance = 0.01; // default
	
	static final int distanceDisplayModeDecimal = 0;
	static final int distanceDisplayModeFractional = 1;
	static final int distanceDisplayModeFeetInches = 2;
	
	int distanceDisplayMode = distanceDisplayModeDecimal; // default
	int distanceDisplayPrecision = 3; // default
	
	public UnitsAndTolerances(){
	    
	}
	
	
	public UnitsAndTolerances(IServerI srv){
	    
	    unitSystem = new UnitSystem(srv.server().unit);
	    absoluteTolerance = IConfig.tolerance;
	    angleTolerance = IConfig.angleTolerance;
	    relativeTolerance = IConfig.parameterTolerance;
	    
	}
	
	
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    int version = readInt(is);
	    
	    if(version >=100 && version < 200){
		
		int us = readInt(is);
		
		unitSystem = new UnitSystem(us);


		IOut.debug(20, "unit system = "+unitSystem);
		
		absoluteTolerance = readDouble(is);
		angleTolerance = readDouble(is);
		relativeTolerance = readDouble(is);
		
		if(version>=101){
		    
		    distanceDisplayMode = readInt(is);
		    if(distanceDisplayMode < 0 || distanceDisplayMode > 2 )
			distanceDisplayMode = distanceDisplayModeDecimal;
		    
		    distanceDisplayPrecision = readInt(is);
		    
		    if(distanceDisplayPrecision < 0 || distanceDisplayPrecision > 20 ){
			distanceDisplayPrecision = 3;
		    }
		    
		    if(version >= 102){
			unitSystem.customUnitScale = readDouble(is);
			unitSystem.customUnitName = readString(is);
		    }
		}
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{

	    int version = 102;
	    
	    writeInt(os, version, crc);
	    writeInt(os, unitSystem.unitSystem, crc);
	    writeDouble(os, absoluteTolerance, crc);
	    writeDouble(os, angleTolerance, crc);
	    writeDouble(os, relativeTolerance, crc);
	    
	    writeInt(os, distanceDisplayMode, crc);
	    
	    if(distanceDisplayPrecision < 0 || distanceDisplayPrecision > 20 ){
		distanceDisplayPrecision = 3;
	    }
	    writeInt(os, distanceDisplayPrecision, crc);
	    
	    writeDouble(os, unitSystem.customUnitScale, crc);
	    writeString(os, unitSystem.customUnitName, crc);
	    
	    //IOut.err("customUnitName = "+unitSystem.customUnitName); //
	    
	}
	
    }
    
    public static class Settings{
	
	public UnitsAndTolerances unitsAndTolerances;
	public UnitsAndTolerances pageUnitsAndTolerances;

	
	public Settings(){
	    
	    unitsAndTolerances = new UnitsAndTolerances();
	    pageUnitsAndTolerances = unitsAndTolerances;
	    
	}
	
	public Settings(IServerI serv){
	    
	    unitsAndTolerances = new UnitsAndTolerances(serv);
	    pageUnitsAndTolerances = unitsAndTolerances;
	    
	}
	
	public void setPluginList(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setPluginList: " + c);
	}
	public void setUnitsAndTols(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setUnitsAndTols: " + c);
	    
	    ByteArrayInputStream bais = new ByteArrayInputStream(c.content);
	    
	    unitsAndTolerances = new UnitsAndTolerances(file.server.server());
	    unitsAndTolerances.read(file, bais);
	    
	    if(unitsAndTolerances.unitSystem!=null){
		
		if(file.server!=null){
		    file.server.server().unit.type =
			unitsAndTolerances.unitSystem.getIUnitType();
		}
		
	    }
	    
	}
	public void setRenderMesh(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setRenderMesh: " + c);
	}
	public void setAnalysisMesh(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setAnalysisMesh: " + c);
	}
	public void setAnnotation(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setAnnotation: " + c);
	}
	public void setNamedCPlaneList(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setNamedCPlaneList: " + c);
	}
	public void setNamedViewList(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setNamedViewList: " + c);
	}
	public void setViewList(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setViewList: " + c);
	}
	public void setCurrentLayerIndex(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setCurrentLayerIndex: " + c);
	}
	public void setCurrentFontIndex(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setCurrentFontIndex: " + c);
	}
	public void setCurrentDimStyleIndex(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setCurrentDimStyleIndex: " + c);
	}
	public void setCurrentMaterialIndex(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setCurrentMaterialIndex: " + c);
	}
	public void setCurrentColor(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setCurrentColor: " + c);
	}
	public void setCurrentWireDensity(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setCurrentWireDensity: " + c);
	}
	public void setRender(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setRender: " + c);
	}
	public void setGridDefaults(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setGridDefaults: " + c);
	}
	public void setModelURL(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setModelURL: " + c);
	}
	public void setAttributes(Rhino3dmFile file, Chunk c) throws IOException{
	    //IOut.p("setAttributes: " + c);
	}
	
	
	// classes for settings
	//public static class UnitsAndTolerances{}
	public static class AnnotationSettings{
	}
	public static class ConstructionPlaneGridDefaults{
	}
	public static class ConstractionPlane{
	}
	public static class ViewPosition{
	}
	public static class ViewTraceImage{
	}
	public static class WallPaperImage{
	}
	public static class PageSettings{
	}
	public static class ViewSettings{
	}
	public static class RenderSettings{
	}
	public static class EarthAnchorPoint{
	}
	public static class IOSettings{
	}
    }
    
    
    public static class Layer extends RhinoObject{
	public static final String uuid = "95809813-E985-11d3-BFE5-0010830122F0";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeLayer; }
	
	public int layerIndex;
	public UUID layerId;
	public UUID parentLayerId;
	public int igesLevel;
	public int materialIndex;
	public int linetypeIndex;
	public IColor color;
	public UUID displayMaterialId;
	public IColor plotColor;
	public double plotWeightMm;
	public String name;
	public boolean visible;
	public boolean locked;
	public boolean expanded;
	public RenderingAttributes renderingAttributes;
		
	public ILayer ilayer; //
	
	public Layer(){
	    layerId=null;
	    parentLayerId=null;
	    layerIndex=-1;
	    igesLevel=-1;
	    materialIndex=-1;
	    linetypeIndex=-1;
	    color = new IColor(0,0,0);
	    displayMaterialId=null;
	    plotColor = new IColor(255,255,255);
	    plotWeightMm=0.;
	    visible=true;
	    locked=false;
	    expanded=true;
	    renderingAttributes = new RenderingAttributes();
	}
	
	
	public Layer(ILayer ilayer, int index){
	    //layerId=null;
	    layerId = UUID.randomUUID();
	    //new UUID("E64C7207-1EDB-47F1-9E97E2A714703BE4"); // debug
	    parentLayerId=null;
	    layerIndex= index;
	    igesLevel=-1;
	    materialIndex=-1;
	    linetypeIndex=-1;
	    color = ilayer.clr();
	    displayMaterialId=null;
	    plotColor = new IColor(255,255,255,0); // with 0xFFFFFF00 plot color, plot color follows layer color, which is currently desirable behavior.
	    plotWeightMm=0.;
	    visible=ilayer.visible(); //true;
	    locked=false;
	    expanded=true;
	    renderingAttributes = new RenderingAttributes();
	    
	    name = ilayer.name();
	    
	    this.ilayer = ilayer;
	}
	
	/*
	// for debug
	public void read(Rhino3dmFile context, byte[] b)throws IOException{
	    //IOut.p(hex(b));
	    super.read(context,b);
	}
	*/
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    //IOut.p();//
	    
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    //IOut.p("majorVersion = "+majorVersion);
	    //IOut.p("minorVersion = "+minorVersion);
	    
	    if(majorVersion==1){
		int mode = readInt(is);
		switch(mode){
		case 0:
		    visible=true;
		    locked=false;
		    break;
		case 1:
		    visible=false;
		    locked=false;
		    break;
		case 2:
		    visible=true;
		    locked=true;
		    break;
		default:
		    visible=true;
		    locked=false;
		    break;
		}
	    }
	    layerIndex = readInt(is);
	    igesLevel = readInt(is);
	    materialIndex = readInt(is);
	    int obsolete_value1 = readInt(is);
	    color = readColor(is);
	    // skipping obsolete fields
	    readShort(is);
	    readShort(is);
	    readDouble(is);
	    readDouble(is);
	    
	    name = readString(is);
	    
	    //IOut.p("name is read: name="+name); //
	    
	    if(minorVersion>=1){
		visible = readBool(is);
		if(minorVersion>=2){
		    linetypeIndex = readInt(is);
		    if(minorVersion>=3){
			plotColor = readColor(is);
			plotWeightMm = readDouble(is);
			if(minorVersion>=4){
			    locked = readBool(is);
			    if(minorVersion>=5){
				layerId = readUUID(is);
				if(minorVersion>=6 &&
				   context.openNurbsVersion > 200505110){
				    parentLayerId = readUUID(is);
				    expanded = readBool(is);
				}
				if(minorVersion>=7){
				    // render attributes
				    renderingAttributes = new RenderingAttributes();
				    renderingAttributes.read(context, is);
				    if(minorVersion>=8){
					displayMaterialId = readUUID(is);
				    }
				}
			    }
			}
		    }
		}
	    }
	    //IOut.p("layer : \n"+this); //
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    
	    writeChunkVersion(os,1,8, crc);
	    
	    int i=0;
	    if(visible) i=0;
	    else if(locked) i=2;
	    else i=1;
	    
	    writeInt32(os,i,crc);
	    
	    writeInt32(os, layerIndex, crc);
	    writeInt32(os, igesLevel, crc);
	    writeInt32(os, materialIndex, crc);
	    
	    i = 0;
	    writeInt32(os, i, crc);
	    
	    writeColor(os, color, crc);
	    
	    // filling obsolete item
	    short s = 0;
	    writeInt16(os, s, crc);
	    writeInt16(os, s, crc);
	    writeDouble(os, 0.0, crc);
	    writeDouble(os, 1.0, crc);
	    
	    writeString(os, name, crc);
	    writeBool(os, visible, crc);
	    writeInt32(os, linetypeIndex, crc);
	    writeColor(os, plotColor, crc);
	    writeDouble(os, plotWeightMm, crc);
	    writeBool(os, locked, crc);
	    writeUUID(os, layerId, crc);
	    writeUUID(os, parentLayerId, crc);
	    writeBool(os, expanded, crc);
	    
	    // render attributes
	    renderingAttributes.write(context,os,crc);
	    
	    writeUUID(os, displayMaterialId, crc);
	    
	    
	    //writeInt32(os, 0x2F6E4FA1); // ??
	}
	
	
	
	public ILayer createIObject(Rhino3dmFile context, IServerI s){
	    if(name == null){
		IOut.err("layer name is null. no layer is instantiated.");
		return null;
	    }
	    
	    //IOut.p("s="+s);
	    //IOut.p("s.server()="+s.server());
	    
	    ilayer = s.server().getLayer(name);
	    if(color!=null) ilayer.setColor(color);
	    ilayer.setVisible(visible);
	    
	    //IOut.err("LAYER: "+name+": visible="+visible); //
	    
	    return ilayer;
	}
	
	
	
	public String toString(){
	    return
		"Layer : "+
		"name "+ name +"\n"+
		"layerIndex = "+layerIndex+"\n" +
		"layerId = "+layerId +"\n"+
		"parentLayerId = " + parentLayerId + "\n"+
		"igesLevel = "+igesLevel + "\n" +
		"materialIndex = " +  materialIndex +"\n"+
		"linetypeIndex = "+ linetypeIndex +"\n" +
		"color = "+ color + "\n"+
		"displayMaterialId = " + displayMaterialId +"\n"+
		"plotColor = "+ plotColor + "\n"+
		"plotWeightMm = " + plotWeightMm +"\n"+
		"visible = "+ visible +"\n"+
		"locked = "+ locked + "\n"+
		"expanded = " + expanded + "\n"+
		"RenderingAttributes = "+renderingAttributes +"\n";
	}
	
    }
    
    
    public static final short activeSpaceNoSpace = 0;
    public static final short activeSpaceModelSpace = 1;
    public static final short activeSpacePageSpace = 2;
    
    public static final int objectModeNormalObject = 0;
    public static final int objectModeHiddenObject = 1;
    public static final int objectModeLockedObject = 2;
    public static final int objectModeIdefObject = 3;
    public static final int objectModeCount = 4;
    
    public static final int colorSourceFromLayer = 0;
    public static final int colorSourceFromObject = 1;
    public static final int colorSourceFromMaterial = 2;
    public static final int colorSourceFromParent = 3;
    
    public static final int plotColorSourceFromLayer = 0;
    public static final int plotColorSourceFromObject = 1;
    public static final int plotColorSourceFromMaterial = 2;
    public static final int plotColorSourceFromParent = 3;
    
    public static final int plotWeightSourceFromLayer = 0;
    public static final int plotWeightSourceFromObject = 1;
    public static final int plotWeightSourceFromParent = 3;
    
    public static final int linetypeSourceFromLayer = 0;
    public static final int linetypeSourceFromObject = 1;
    public static final int linetypeSourceFromParent = 3;
    
    public static final int materialSourceFromLayer = 0;
    public static final int materialSourceFromObject = 1;
    public static final int materialSourceFromParent = 3;

    public static final short objectDecorationNoDecolation = 0x00;
    public static final short objectDecorationStartArrowHead = 0x08;
    public static final short objectDecorationEndArrowHead = 0x10;
    public static final short objectDecorationBothArrowHead = 0x18;
    
    public static class ObjectAttributes extends RhinoObject{
	public static final String uuid = "A828C015-09F5-477c-8665-F0482F5D6996";

	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
	
	public static final UUID obsoletePageSpaceObjectId =
	    new UUID(0x9bbb37e9, (short)0x2131, (short)0x4fb8,
		     new byte[]{ (byte)0xb9, (byte)0xc6, (byte)0x55, (byte)0x24, (byte)0x85, (byte)0x9b, (byte)0x98, (byte)0xb8});
	
	
	public UUID objectUUID;
	public String name;
	public String url;
	public int layerIndex;
	public int linetypeIndex;
	public int materialIndex;
	public RenderingAttributes renderingAttributes;
	public IColor color;
	public IColor plotColor;
	public int displayOrder;
	public double plotWeightMm;
	public short objectDecoration;
	public int wireDensity;
	public UUID viewportId;
	public short activeSpace;
	
	public boolean visible;
	public byte mode;
	public byte colorSource;
	public byte plotColorSource;
	public byte plotWeightSource;
	public byte materialSource;
	public byte linetypeSource;
	
	public ArrayList<Integer> group;
	
	public ArrayList<DisplayMaterialRef> dmref;
	
	
	public ObjectAttributes(){
	    objectUUID = null;
	    name = null;
	    url = null;
	    layerIndex = 0;
	    linetypeIndex = -1;
	    materialIndex = -1;
	    renderingAttributes = new RenderingAttributes();
	    color = new IColor(0,0,0); //Color.black;
	    plotColor = new IColor(0,0,0); //Color.black;
	    displayOrder = 0;
	    plotWeightMm = 0.;
	    objectDecoration = 0;
	    wireDensity = 1;
	    viewportId = null;
	    activeSpace = activeSpaceModelSpace;
	    visible = true;
	    mode = objectModeNormalObject;
	    colorSource = colorSourceFromLayer;
	    plotColorSource = plotColorSourceFromLayer;
	    plotWeightSource = plotWeightSourceFromLayer;
	    materialSource = materialSourceFromLayer;
	    linetypeSource = linetypeSourceFromLayer;
	    group = null;
	    dmref = null;
	}
	
	
	public ObjectAttributes(IObject e, Rhino3dmFile file){
	    objectUUID = UUID.randomUUID(); //null;
	    name = e.name();
	    url = null;
	    
	    if(e.layer()!=null && e.server!=null && e.server.layers!=null){
		layerIndex = e.server.layers.indexOf(e.layer());
	    }
	    else{
		layerIndex = 0;
	    }
	    
	    //IOut.err("LAYER = "+e.layer); //
	    //IOut.err("LAYER INDEX = "+layerIndex); //
	    
	    linetypeIndex = -1;
	    
	    if(e.attr()!=null && e.attr().material!=null && file.imaterials!=null){
		
		materialIndex = file.imaterials.indexOf(e.attr().material);
				
		/*
		IMaterial mat = e.attr().material;
		for(int i=0; i<file.materials.size()&&materialIndex<0; i++){
		    if(file.materials.get(i).imaterial==mat) materialIndex=i;
		}
		*/
		//IOut.err("materialIndex = "+materialIndex); //
		
		//materialIndex=-1;
	    }
	    else materialIndex = -1;
	    
	    renderingAttributes = new RenderingAttributes();
	    color = e.getColor();
	    plotColor = e.getColor();
	    displayOrder = 0;
	    plotWeightMm = 0.;
	    objectDecoration = 0;
	    wireDensity = 1;
	    viewportId = null;
	    activeSpace = activeSpaceModelSpace;
	    visible = e.visible(); //true;
	    mode = objectModeNormalObject;
	    
	    //colorSource = colorSourceFromLayer; // ?
	    if(color!=null) colorSource = colorSourceFromObject;
	    else colorSource = colorSourceFromLayer; 
	    
	    plotColorSource = plotColorSourceFromLayer;
	    plotWeightSource = plotWeightSourceFromLayer;
	    
	    if(materialIndex>=0){
		materialSource = materialSourceFromObject;
	    }
	    else{
		materialSource = materialSourceFromLayer;
	    }
	    
	    linetypeSource = linetypeSourceFromLayer;
	    group = null;
	    dmref = null;
	}
	
	
	public void readV5(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    int itemid = 0xFF;
	    
	    objectUUID = readUUID(is);
	    layerIndex = readInt(is);
	    
	    itemid = readByte(is);
	    
	    if(itemid==0) return;
	    if(itemid==1){
		name = readString(is);
		itemid = readByte(is);
	    }
	    if(itemid==2){
		url = readString(is);
		itemid = readByte(is);
	    }
	    if(itemid==3){
		linetypeIndex = readInt(is);
		itemid = readByte(is);
	    }
	    if(itemid==4){
		materialIndex = readInt(is);
		itemid = readByte(is);
	    }
	    if(itemid==5){
		renderingAttributes.read(context,is);
		itemid = readByte(is);
	    }
	    if(itemid==6){
		color = readColor(is);
		itemid = readByte(is);
	    }
	    if(itemid==7){
		plotColor = readColor(is);
		itemid = readByte(is);
	    }
	    if(itemid==8){
		plotWeightMm = readDouble(is);
		itemid = readByte(is);
	    }
	    if(itemid==9){
		objectDecoration = readByte(is);
		itemid = readByte(is);
	    }
	    if(itemid==10){
		wireDensity = readInt(is);
		itemid = readByte(is);
	    }
	    if(itemid==11){
		visible = readBool(is);
		itemid = readByte(is);
	    }
	    if(itemid==12){
		mode = readByte(is);
		itemid = readByte(is);
	    }
	    if(itemid==13){
		colorSource = readByte(is);
		itemid = readByte(is);
	    }
	    if(itemid==14){
		plotColorSource = readByte(is);
		itemid = readByte(is);
	    }
	    if(itemid==15){
		plotWeightSource = readByte(is);
		itemid = readByte(is);
	    }
	    if(itemid==16){
		materialSource = readByte(is);
		itemid = readByte(is);
	    }
	    if(itemid==17){
		linetypeSource = readByte(is);
		itemid = readByte(is);
	    }
	    if(itemid==18){
		//int count = readInt(is);
		//group = new ArrayList<Integer>(count);
		//for(int i=0; i<count; i++){ group.add(readInt(is)); }
		group = readArrayInt(is);
		itemid = readByte(is);
	    }
	    if(itemid==19){
		activeSpace = readByte(is);
		itemid = readByte(is);
	    }
	    if(itemid==20){
		viewportId = readUUID(is);
		itemid = readByte(is);
	    }
	    if(itemid==21){
		int count = readInt(is);
		dmref =	new ArrayList<DisplayMaterialRef>(count);
		for(int i=0; i<count; i++){
		    DisplayMaterialRef dmr = new DisplayMaterialRef();
		    dmr.read(context,is);
		    dmref.add(dmr);
		}
		itemid = readByte(is);
	    }
	    if(minorVersion >= 1){
		
		if(itemid==22){
		    displayOrder = readInt(is);
		    itemid = readByte(is);
		}
		if(minorVersion >=2){
		    //...
		}
	    }
	}
	
	/*
	// for debug
	public void read(Rhino3dmFile context, byte[] b)throws IOException{
	    IOut.p(hex(b));
	    super.read(context,b);
	}
	*/
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    if(context.version >= 5 && context.openNurbsVersion >= 200712190 ){
		readV5(context,is);
		return;
	    }
	    
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    if(majorVersion!=1){
		IOut.err("wrong major version "+majorVersion); //
		throw new IOException("wrong major version "+majorVersion);
	    }
	    
	    objectUUID = readUUID(is);
	    layerIndex = readInt(is);
	    materialIndex = readInt(is);
	    color = readColor(is);
	    
	    //IOut.debug(20,"object attributes color = "+color); //
	    //IOut.debug(20,"materialIndex = "+materialIndex); //
	    
	    short s = readShort(is);
	    if(context.version < 4 || context.openNurbsVersion < 200503170){
		objectDecoration = (short)(s & objectDecorationBothArrowHead);
	    }
	    readShort(is);
	    readDouble(is);
	    readDouble(is);
	    
	    wireDensity = readInt(is);
	    mode = readByte(is);
	    colorSource =readByte(is);
	    linetypeSource = readByte(is);
	    materialSource = readByte(is);
	    
	    name = readString(is);
	    url = readString(is);
	    	    
	    visible = (mode&0x0F) != objectModeHiddenObject;
	    
	    if(minorVersion>=1){
		// readarray 
		//int count = readInt(is);
		//group = new ArrayList<Integer>(count);
		//for(int i=0; i<count; i++){ group.add(readInt(is)); }
		group = readArrayInt(is);
		
		if(minorVersion>=2){
		    visible = readBool(is);
		    
		    if(minorVersion>=3){
			int count = readInt(is);
			dmref =	new ArrayList<DisplayMaterialRef>(count);
			for(int i=0; i<count; i++){
			    DisplayMaterialRef dmr = new DisplayMaterialRef();
			    dmr.read(context,is);
			    dmref.add(dmr);
			}
			
			if(minorVersion>=4){
			    objectDecoration = (short)(readInt(is)&0xFF);
			    plotColorSource = readByte(is);
			    plotColor = readColor(is);
			    plotWeightSource = readByte(is);
			    plotWeightMm = readDouble(is);
			    
			    if(minorVersion>=5){
				linetypeIndex = readInt(is);
				
				if(minorVersion>=6){
				    byte uc = readByte(is);
				    
				    activeSpace = (uc==1)? activeSpacePageSpace : activeSpaceModelSpace;
				    dmref.clear();
				    count = readInt(is);
				    for(int i=0; i<count; i++){
					DisplayMaterialRef dmr = new DisplayMaterialRef();
					
					dmr.viewportId = readUUID(is);
					
					if(obsoletePageSpaceObjectId.equals(dmr.viewportId)){
					    viewportId = dmr.viewportId;
					}
					else{ dmref.add(dmr); }
				    }
				    if(minorVersion>=7){
					renderingAttributes.read(context, is);
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    
	    // version 4 3dm file format
	    //writeChunkVersion(os, 1,7, crc);
	    writeChunkVersion(os, 1, 6, crc);
	    
	    writeUUID(os, objectUUID, crc);
	    writeInt32(os, layerIndex, crc);
	    writeInt32(os, materialIndex, crc);
	    writeColor(os, color, crc);
	    
	    short s = /*(short)*/objectDecoration;
	    writeInt16(os, s, crc);
	    s = 0;
	    writeInt16(os, s, crc);
	    writeDouble(os, 0.0, crc);
	    writeDouble(os, 1.0, crc);
	    
	    writeInt32(os, wireDensity, crc);
	    writeByte(os, mode, crc);
	    writeByte(os, colorSource, crc);
	    writeByte(os, linetypeSource, crc);
	    writeByte(os, materialSource, crc);
	    writeString(os, name, crc);
	    //writeString(os, "test_name", crc);
	    writeString(os, url, crc);
	    //writeString(os, "test_url", crc);
	    
	    
	    writeArrayInt(os, group, crc);
	    
	    writeBool(os, visible, crc);
	    
	    writeArrayDisplayMaterialRef(context, os, dmref, crc);
	    
	    
	    //writeInt32(os, (int)objectDecoration, crc);
	    writeInt32(os, objectDecoration&0xFFFF, crc);
	    writeByte(os, plotColorSource, crc);
	    writeColor(os, plotColor, crc);
	    writeByte(os, plotWeightSource, crc);
	    writeDouble(os, plotWeightMm, crc);
	    
	    writeInt32(os, linetypeIndex, crc);
	    
	    
	    byte uc=0;
	    switch(activeSpace){
	    case activeSpaceNoSpace:
		uc = 0; break;
	    case activeSpaceModelSpace:
		uc = 0; break;
	    case activeSpacePageSpace:
		uc = 1; break;
	    }
	    
	    writeByte(os, uc, crc);
	    
	    int count = 0;
	    if(dmref!=null) count = dmref.size();
	    boolean addPageSpaceDMR =
		activeSpace==activeSpacePageSpace &&
		viewportId!=null && viewportId!=UUID.nilValue;
	    
	    writeInt32(os, addPageSpaceDMR?(count+1):count, crc);
	    if(addPageSpaceDMR){
		writeUUID(os,viewportId,crc);
		final UUID obsoletePageSpaceObjectId =
		    new UUID( 0x9bbb37e9, (short)0x2131, (short)0x4fb8,
			      new byte[]{ (byte)0xb9, (byte)0xc6, (byte)0x55, (byte)0x24,
					  (byte)0x85, (byte)0x9b, (byte)0x98, (byte)0xb8 });
		writeUUID(os,obsoletePageSpaceObjectId,crc);
	    }
	    for(int i=0; dmref!=null && i<count; i++){
		writeUUID(os,dmref.get(i).viewportId,crc);
		writeUUID(os,dmref.get(i).displayMaterialId,crc);
	    }
	    
	    
	    // somehow with chunk version 1.7, writing rendering attributs cause an error.
	    // so then skipped.
	    //renderingAttributes.write(context,os,crc);
	    
	    // debug
	    //writeBytes(os,hexStringToByte("4000800016000000010000000200000000000000000000000101D6BC3B07"),null);
	    
	}
		
	public String toString(){
	    return
		"objectUUID = " + objectUUID + "\n" +
		"name = " + name + "\n" +
		"url = "+ url + "\n" +
		"layerIndex = " + layerIndex + "\n" +
		"linetypeIndex = " + linetypeIndex + "\n" +
		"materialIndex = " + materialIndex + "\n" +
		"renderingAttributes = "+renderingAttributes + "\n" +
		"color = " + color + "\n" +
		"plotColor = "+ plotColor + "\n" +
		"displayOrder = " + displayOrder + "\n" +
		"plotWeightMm = " + plotWeightMm + "\n" +
		"objectDecoration = " + objectDecoration + "\n" +
		"wireDensity = " + wireDensity + "\n" +
		"viewportId = " + viewportId + "\n" +
		"activeSpace = " + activeSpace + "\n" +
		"visible = " + visible + "\n" + 
		"mode = " + mode + "\n" +
		"colorSource = "+colorSource + "\n" + 
		"plotColorSource = " + plotColorSource + "\n" +
		"plotWeightSource = " + plotWeightSource + "\n" + 
		"materialSource = " + materialSource + "\n" +
		"linetypeSource = " + linetypeSource + "\n" +
		"group = " + group + "\n" + 
		"dmref = " + dmref + "\n";
	}
	
    }
    
    public static class DisplayMaterialRef{
	public UUID viewportId;
	public UUID displayMaterialId;
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    viewportId = readUUID(is);
	    displayMaterialId = readUUID(is);
	}
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    writeUUID(os,viewportId,crc);
	    writeUUID(os,displayMaterialId,crc);
	}
    }
    
    public static class Group extends RhinoObject{
	public static final String uuid = "721D9F97-3645-44c4-8BE6-B2CF697D25CE";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    
    public static class Font extends RhinoObject{
	public static final String uuid = "4F0F51FB-35D0-4865-9998-6D2C6A99721D";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
	
	public String fontName;
	public int fontWeight;
	public boolean fontItalic;
	public boolean fontUnderlined;
	public double linefeedRatio;
	public int fontIndex;
	public UUID fontId;
	public char[] facename;
	
	/*
	public void read(Rhino3dmFile context, byte[] b)throws IOException{
	    IOut.p(hex(b));//
	    //read(context, new ByteArrayInputStream(b));
	}
	*/
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	}
	
    }
    
    public static class DimStyle extends RhinoObject{
	public static final String uuid = "81BD83D5-7120-41c4-9A57-C449336FF12C";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    
    public static class Light extends Geometry{
	public static final String uuid = "85A08513-F383-11d3-BFE7-0010830122F0";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeLight; }
    }

    
    public static class HatchPattern extends RhinoObject{
	public static final String uuid = "064E7C91-35F6-4734-A446-79FF7CD659E1";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    
    public static class InstanceDefinition extends Geometry{
	public static final String uuid = "26F8BFF6-2618-417f-A158-153D64A94989";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeInstanceDefinition; }
    }
    
    public static class Geometry extends RhinoObject{
	public static final String uuid = "4ED7D4DA-E947-11d3-BFE5-0010830122F0";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    
    public static class HistoryRecord extends RhinoObject{
	public static final String uuid = "ECD0FD2F-2088-49dc-9641-9CF7A28FFA6B";

	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    
    public static class UserData extends RhinoObject{
	public static final String uuid = "850324A7-050E-11d4-BFFA-0010830122F0";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
	
	public void writeBody(Rhino3dmFile context, OutputStream os, CRC32 crc) throws IOException{
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc) throws IOException{
	    // write only V4 file format. V2, V3 is ignored.
	    
	    writeChunkVersion(os, 2, 2, crc);
	    
	    ChunkOutputStream cosHeader = new ChunkOutputStream(tcodeOpenNurbsClassUserDataHeader);
	    
	    writeUUID(cosHeader, getClassUUID(), cosHeader.getCRC());
	    
	    //UUID userDataUUID = UUID.randomUUID();
	    UUID userDataUUID = getClassUUID();
	    writeUUID(cosHeader, userDataUUID, cosHeader.getCRC());
	    
	    int copycount=1;
	    writeInt(cosHeader, copycount, cosHeader.getCRC());
	    
	    Xform userDataXform = new Xform();
	    userDataXform.identity();
	    writeXform(cosHeader, userDataXform, cosHeader.getCRC());
	    
	    UUID applicationUUID = UUID.randomUUID();
	    writeUUID(cosHeader, applicationUUID, cosHeader.getCRC());
	    
	    boolean unknownUserData=false;
	    writeBool(cosHeader, unknownUserData, cosHeader.getCRC());
	    
	    int version = context.version;
	    writeInt(cosHeader, version, cosHeader.getCRC());
	    
	    int onVersion = context.openNurbsVersion;
	    writeInt(cosHeader, onVersion, cosHeader.getCRC());
	    
	    writeChunk(os, cosHeader.getChunk());
	    
	    
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    
	    this.writeBody(context,cos,cos.getCRC());
	    
	    writeChunk(os, cos.getChunk());
	}
    }
    
    public static class Annotation extends Geometry{
	public static final String uuid = "ABAF5873-4145-11d4-800F-0010830122F0";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeAnnotation; }
    }
    public static class Annotation2 extends Geometry{
	public static final String uuid = "8D820224-BC6C-46b4-9066-BF39CC13AEFB";
	
	public AnnotationType type;
	public TextDisplayMode textDisplayMode;
	public Plane plane;
	//public Point2Array points;
	public ArrayList<IVec2> points;
	public Annotation2Text userText;
	public boolean userPositionedText;
	public int index = 1; // default 1?
	public double textHeight;
	public int justification;	
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeAnnotation; }
	
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    if(context.version >= 5 && context.openNurbsVersion >= 200710180){
		Chunk chunk = readChunk(is);
		if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
		is = new ByteArrayInputStream(chunk.content);
		
		
	    }
	    else{
		
		int[] version = readChunkVersion(is);
		
		int majorVersion = version[0];
		int minorVersion = version[1];
		
		if(majorVersion!=1){ return; }
		
	    }
	    
	    int i;
	    i = readInt(is);
	    type = annotationType(i);
	    
	    i = readInt(is);
	    textDisplayMode = textDisplayMode(i);
	    
	    plane = readPlane(is);
	    
	    points = readArrayPoint2(is);
	    
	    String str = readString(is);
	    userText = new Annotation2Text(str);
	    
	    i = readInt(is);
	    userPositionedText = i!=0;
	    
	    index = readInt(is);
	    textHeight = readDouble(is);
	    
	    /*
	      IG.debug(20,"type = "+type);
	      IG.debug(20,"displayMode = "+textDisplayMode); 
	      IG.debug(20,"plane = "+plane.toString());
	      IG.debug(20,"points.size()="+points.size());
	      IG.debug(20,"userText = "+userText.text);
	      IG.debug(20,"userPositionedText = "+userPositionedText);
	      IG.debug(20,"index = "+index);
	      IG.debug(20,"textHeight = "+textHeight);
	    */
	    
	    switch(type){
	    case dtDimAligned:
	    case dtDimLinear:
		if(points.size()<5){ userPositionedText = false; }
		break;
	    case dtDimAngular:
		if(points.size() <= 0){ userPositionedText = false; }
		break;
	    case dtDimRadius:
	    case dtDimDiameter:
		if( points.size()==5){
		    points.remove(4);
		}
		userPositionedText = false;
		break;
	    default:
		userPositionedText = false;
	    }
	}
	
	
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    
	    boolean v5 = context.version >= 5;
	    OutputStream os2 = os;
	    CRC32 crc2 = crc;
	    if(v5){
		os2 = new ChunkOutputStream(tcodeAnonymousChunk, 1, 0);
		crc2 = ((ChunkOutputStream)os2).getCRC();
	    }
	    else{
		writeChunkVersion(os2, 1, 0, crc);
	    }
	    
	    writeInt32(os2, annotationType(type), crc2);
	    writeInt32(os2, textDisplayMode(textDisplayMode), crc2);
	    writePlane(os2, plane, crc2);
	    
	    boolean userPosText = userPositionedText;
	    
	    switch(type){
	    case dtDimAligned:
	    case dtDimLinear:
		if(points.size()==4){
		    IVec2 p = new IVec2(0.5*(points.get(0).x+points.get(2).x), points.get(1).y);
		    points.add(p);
		    userPosText=false;
		}
		break;
	    case dtDimAngular:
		break;
	    case dtDimRadius:
	    case dtDimDiameter:
		if(points.size()==4){
		    points.add(new IVec2());
		}
		if(points.size()>=5){
		    points.get(4).set(points.get(2));
		}
		userPosText=false;
		break;
	    default:
		userPosText=false;
	    }
	    
	    writeArrayPoint2(os2, points, crc2);
	    writeString(os2, userText.text, crc2);
	    writeInt32(os2, userPosText?1:0, crc2);
	    writeInt32(os2, index, crc2);
	    writeDouble(os2, textHeight, crc2);
	    
	    if(v5){
		writeInt32(os2, justification, crc2);
	    }
	    	    
	    if(v5){
		writeChunk(os, ((ChunkOutputStream)os2).getChunk());
	    }
	    
	}
	
    }
    public static abstract class Curve extends Geometry{
	public static final String uuid = "4ED7D4D7-E947-11d3-BFE5-0010830122F0";
	
	public ICurveGeo icurve; // for brep topology check
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeCurve; }
	
	abstract public Interval domain();
	abstract public boolean isValid();
	
	// for IPolycurve which is not child of ICurve (yet)
	public /*ICurve*/IObject createIObject(Rhino3dmFile context, IServerI s){ return null; }
	public ICurveGeo createIGeometry(Rhino3dmFile context, IServerI s){ return null; }
	public ITrimCurve createTrimCurve(Rhino3dmFile context, IServerI s, ISurfaceI srf){ return null; }
	
    }
    public static class Surface extends Geometry{
	public static final String uuid = "4ED7D4E1-E947-11d3-BFE5-0010830122F0";
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeSurface; }
	
	public enum ISO{ NotIso, XIso, YIso, WIso, SIso, EIso, NIso, IsoCount };
	
	static public ISO readIso(int i){
	    final int notIso = 0;
	    final int xIso = 1;
	    final int yIso = 2;
	    final int wIso = 3;
	    final int sIso = 4;
	    final int eIso = 5;
	    final int nIso = 6;
	    switch(i){
	    case notIso:
		return ISO.NotIso;
	    case xIso:
		return ISO.XIso;
	    case yIso:
		return ISO.YIso;
	    case wIso:
		return ISO.WIso;
	    case sIso:
		return ISO.SIso;
	    case eIso:
		return ISO.EIso;
	    case nIso:
		return ISO.NIso;
	    }
	    return ISO.NotIso;
	}
	static public int getInt(ISO iso){
	    final int notIso = 0;
	    final int xIso = 1;
	    final int yIso = 2;
	    final int wIso = 3;
	    final int sIso = 4;
	    final int eIso = 5;
	    final int nIso = 6;
	    switch(iso){
	    case NotIso:
		return notIso;
	    case XIso:
		return xIso;
	    case YIso:
		return yIso;
	    case WIso:
		return wIso;
	    case SIso:
		return sIso;
	    case EIso:
		return eIso;
	    case NIso:
		return nIso;
	    }
	    return notIso;
	}
	
	public Interval domain(int dir){ return new Interval(0,1); } // should be overwritten
	
	public ISurface createIObject(Rhino3dmFile context, IServerI s){ return null; }
	public ISurfaceGeo createIGeometry(Rhino3dmFile context, IServerI s){ return null; }
    }
    public static class LinearDimension extends Annotation{
	public static final String uuid = "5DE6B20D-486B-11d4-8014-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class RadialDimension extends Annotation{
	public static final String uuid = "5DE6B20E-486B-11d4-8014-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class AngularDimension extends Annotation{
	public static final String uuid = "5DE6B20F-486B-11d4-8014-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class TextEntity extends Annotation{
	public static final String uuid = "5DE6B210-486B-11d4-8014-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class Leader extends Annotation{
	public static final String uuid = "5DE6B211-486B-11d4-8014-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class AnnotationTextDot extends Point{
	public static final String uuid = "8BD94E19-59E1-11d4-8018-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class AnnotationArrow extends Geometry{
	public static final String uuid = "8BD94E1A-59E1-11d4-8018-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeAnnotation; }
    }
    public static class TextExtra extends UserData{
	public static final String uuid = "D90490A5-DB86-49f8-BDA1-9080B1F4E976";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    public static class DimensionExtra extends UserData{
	public static final String uuid = "8AD5B9FC-0D5C-47fb-ADFD-74C28B6F661E";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    public static class LinearDimension2 extends Annotation2{
	public static final String uuid = "BD57F33B-A1B2-46e9-9C6E-AF09D30FFDDE";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class RadialDimension2 extends Annotation2{
	public static final String uuid = "B2B683FC-7964-4e96-B1F9-9B356A76B08B";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class AngularDimension2 extends Annotation2{
	public static final String uuid = "841BC40B-A971-4a8e-94E5-BBA26D67348E";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class TextEntity2 extends Annotation2{
	public static final String uuid = "46F75541-F46B-48be-AA7E-B353BBE068A7";
	
	public TextEntity2(IText text){
	    
	    type = AnnotationType.dtTextBlock;
	    textDisplayMode = TextDisplayMode.dtNormal;
	    
	    plane = new Plane();
	    
	    
	    //plane.origin = text.pos();
	    plane.origin = text.corner(0,1).sub(text.vvec()); // left bottom of first line.
	    plane.xaxis = text.uvec().cp().unit();
	    plane.yaxis = text.vvec().cp().unit();
	    plane.zaxis = text.uvec().cross(text.vvec()).unit();
	    plane.planeEquation = new PlaneEquation(plane.zaxis,plane.origin);
	    
	    userText = new Annotation2Text(text.text());
	    userPositionedText = false;
	    index = 1; // this should be a font index number?
	    textHeight = text.vvec().len();
	    justification = 0; //?
	    
	}
	
	public TextEntity2(){
	}
	
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
	
	public IObject createIObject(Rhino3dmFile context, IServerI s){
	    if(type == AnnotationType.dtTextBlock &&plane!=null && userText.text!=null){
		//index; // font type; to be implemented later
		
		// rhino text origin is at the left bottom of th first line; it seems line height is not necessarily same with textHeight.
		plane.origin.add(plane.yaxis.cp().len(textHeight)); 
		
		IText text = new IText(userText.text, textHeight, plane.origin, plane.xaxis, plane.yaxis);
		text.alignLeft();
		text.alignTop();
		return text;
	    }
	    return null;
	}
	

	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    if(context.version >= 5 && context.openNurbsVersion >= 200710180){
		
		Chunk chunk = readChunk(is);
		if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
		is = new ByteArrayInputStream(chunk.content);
		super.read(context,is);
	    }
	    else{
		super.read(context,is);
	    }
	    
	}
	
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    if(context.version >= 5){
		ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk, 1, 0);
		super.write(context, cos, cos.getCRC());
		writeChunk(os, cos.getChunk());
	    }
	    else{
		super.write(context, os, crc);
	    }
	}
	
    }
    public static class Leader2 extends Annotation2{
	public static final String uuid = "14922B7A-5B65-4f11-8345-D415A9637129";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class TextDot extends Geometry{
	public static final String uuid = "74198302-CDF4-4f95-9609-6D684F22AB37";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeTextDot; }
    }
    public static class OrdinateDimension2 extends Annotation2{
	public static final String uuid = "C8288D69-5BD8-4f50-9BAF-525A0086B0C3";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    public static class AngularDimension2Extra extends UserData{
	public static final String uuid = "A68B151F-C778-4a6e-BCB4-23DDD1835677";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeAnnotation; }
    }
    
    public static class PlaneEquation{
	public double x,y,z,d;
	PlaneEquation(){}
	PlaneEquation(double x, double y, double z, double d){ this.x=x; this.y=y; this.z=z; this.d=d; }
	PlaneEquation(IVec planeNml, IVec planePt){
	    x = planeNml.x;
	    y = planeNml.y;
	    z = planeNml.z;
	    d = -planeNml.dot(planePt);
	}
	public String toString(){
	    return "plane equation {"+x+","+y+","+z+","+d+"}";
	}
    }
    public static class Plane{
	public IVec origin;
	public IVec xaxis;
	public IVec yaxis;
	public IVec zaxis;
	public PlaneEquation planeEquation;
	public boolean isValid(){ return true; } // skipped
	
	public String toString(){
	    return "orig="+origin+", x="+xaxis+", y="+yaxis+", z="+zaxis+", eq="+planeEquation;
	}
    }
    public static class Circle{
	public Plane plane;
	public double radius;
	public boolean isValid(){
	    return radius > 0. && plane.isValid();
	}
    }
    public static class Arc extends Circle{
	public Interval angle;
	public boolean isValid(){
	    return super.isValid() && angle.isValid() && angleRadians()>=0 && angleRadians()<=Math.PI*2;
	}
	public double angleRadians(){ return angle.v2-angle.v1; }
	public Interval domain(){ return angle; }
    }
    
    public static class ArcCurve extends Curve{
	public static final String uuid = "CF33BE2A-09B4-11d4-BFFB-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeCurve; }
	
	public Arc arc;
	public Interval t;
	public int dim;
	
	public Interval domain(){ return t; }
	public boolean isValid(){
	    if(!t.isIncreasing()) return false;
	    if(!arc.isValid()) return false;
	    return true;
	}
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		arc = readArc(is);
		t = readInterval(is);
		dim = readInt(is);
		if(dim!=2&&dim!=3) dim = 3;
	    }
	    
	    // debug
	    //IOut.p("Arc"); //
	}
	
	public ICurve createIObject(Rhino3dmFile context, IServerI s){
	    IVec center = arc.plane.origin;
	    IVec normal = arc.plane.zaxis;
	    
	    if( Math.abs(arc.angle.length())<2*Math.PI-IConfig.angleTolerance){
		// arc
		IVec startPt = arc.plane.xaxis.dup().len(arc.radius).add(center);
		startPt.rot(center, normal, arc.angle.v1);
		double a = arc.angle.v2 - arc.angle.v1;
		IArc ar = new IArc(s,center,normal,startPt,a);
		//setAttributesToIObject(context,ar);
		return ar;
	    }
	    
	    //circle
	    IVec rollDir = arc.plane.xaxis;
	    ICircle cir = new ICircle(s,center,normal,rollDir,arc.radius);
	    //setAttributesToIObject(context,cir);
	    return cir;
	}
	
	
	public ICurveGeo createIGeometry(Rhino3dmFile context, IServerI s){
	    IVec center = arc.plane.origin;
	    IVec normal = arc.plane.zaxis;
	    if( Math.abs(arc.angle.length())<2*Math.PI-IConfig.angleTolerance){
		// arc
		IVec startPt = arc.plane.xaxis.dup().len(arc.radius).add(center);
		startPt.rot(center, normal, arc.angle.v1);
		double a = arc.angle.v2 - arc.angle.v1;
		IArcGeo ar = new IArcGeo(center,normal,startPt,a);
		return ar;
	    }
	    //circle
	    IVec rollDir = arc.plane.xaxis;
	    ICircleGeo cir = new ICircleGeo(center,normal,rollDir,arc.radius);
	    return cir;
	}
	
	public ITrimCurve createTrimCurve(Rhino3dmFile context, IServerI s, ISurfaceI srf){
	    IVec center = arc.plane.origin;
	    IVec normal = arc.plane.zaxis;
	    if( Math.abs(arc.angle.length())<2*Math.PI-IConfig.angleTolerance){
		// arc
		IVec startPt = arc.plane.xaxis.dup().len(arc.radius).add(center);
		startPt.rot(center, normal, arc.angle.v1);
		double a = arc.angle.v2 - arc.angle.v1;
		IArcGeo ar = new IArcGeo(center,normal,startPt,a);
		//return new ITrimCurve(srf, ar);
		return new ITrimCurve(ar);
	    }
	    //circle
	    IVec rollDir = arc.plane.xaxis;
	    ICircleGeo cir = new ICircleGeo(center,normal,rollDir,arc.radius);
	    //return new ITrimCurve(srf, cir);
	    return new ITrimCurve(cir);
	}
	
    }
    public static class Extrusion extends Surface{
	public static final String uuid = "36F53175-72B8-4d47-BF1F-B4E6FC24F4B9";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeExtrusion; }
    }
    public static class WindowsBitmap extends Bitmap{
	public static final String uuid = "390465EB-3721-11d4-800B-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    public static class EmbeddedBitmap extends Bitmap{
	public static final String uuid = "772E6FC1-B17B-4fc4-8F54-5FDA511D76D2";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    public static class WindowsBitmapEx extends WindowsBitmap{
	public static final String uuid = "203AFC17-BCC9-44fb-A07B-7F5C31BD5ED9";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    
    public static class BrepVertex extends Point{
	public static final String uuid = "60B5DBC0-E660-11d3-BFE4-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypePoint; }
	
	public int vertexIndex;
	public ArrayList<Integer> edgeIndex;
	public double tolerance;
	
	public BrepVertex(){}
	
	public BrepVertex(int vidx, IVec pt){
	    super(pt);
	    vertexIndex = vidx;
	    edgeIndex = new ArrayList<Integer>();
	    tolerance = 0.; //
	    
	    //tolerance = IConfig.tolerance;
	}
	
	public void addEdgeIndex(int i){ edgeIndex.add(i); }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    vertexIndex = readInt(is);
	    point = readPoint3(is);
	    
	    //int count = readInt(is);
	    //edgeIndex = new ArrayList<Integer>(count);
	    //for(int i=0; i<count; i++) edgeIndex.add(readInt(is));
	    edgeIndex = readArrayInt(is);
	    
	    tolerance = readDouble(is);
	    
	    
	    IOut.debug(200,"vertexIndex = "+vertexIndex); //
	    IOut.debug(200,"point = "+point); //
	    String eidxStr = "";
	    for(int i=0; i<edgeIndex.size(); i++) eidxStr+= edgeIndex.get(i)+", ";
	    IOut.debug(200,"edgeIndex = "+eidxStr);
	    IOut.debug(200,"tolerance = "+tolerance); 
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    writeInt32(os,vertexIndex,crc);
	    writePoint(os,point,crc);
	    writeArrayInt(os,edgeIndex,crc);
	    writeDouble(os,tolerance,crc);
	}
	
    }
    public static class BrepEdge extends CurveProxy{
	public static final String uuid = "60B5DBC1-E660-11d3-BFE4-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeCurve; }
	
	public int edgeIndex;
	public int curve3Index;
	public int[] vertexIndex;
	public ArrayList<Integer> trimIndex;
	public double tolerance;
	public Brep brep;
	
	public BrepEdge(){}
	public BrepEdge(int eidx, int c3idx, int vidx1, int vidx2,
			Interval proxyDomain, Interval domain,
			Brep brep){
	    edgeIndex = eidx;
	    curve3Index = c3idx;
	    vertexIndex = new int[2];
	    vertexIndex[0] = vidx1;
	    vertexIndex[1] = vidx2;
	    this.brep = brep;
	    trimIndex = new ArrayList<Integer>();
	    tolerance = 0.; // ?
	    
	    setProxyCurveDomain(proxyDomain);
	    setDomain(domain);
	}
	
	public void addTrimIndex(int i){ trimIndex.add(i); }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    edgeIndex = readInt(is);
	    curve3Index = readInt(is);
	    int reversed = readInt(is);
	    Interval proxyDomain = readInterval(is);
	    vertexIndex = new int[2];
	    vertexIndex[0] = readInt(is);
	    vertexIndex[1] = readInt(is);
	    trimIndex = readArrayInt(is);
	    tolerance = readDouble(is);
	    Interval domain = proxyDomain;
	    
	    if(context.version >= 3 &&
	       context.openNurbsVersion >= 200206180 ){
		try{
		    domain = readInterval(is);
		}catch(IOException e){ domain = proxyDomain; }
	    }
	    
	    setProxyCurve(null, proxyDomain);
	    if(reversed!=0) reverse();
	    setDomain(domain);
	    
	    //
	    IOut.debug(200,"edgeIndex = "+edgeIndex);
	    IOut.debug(200,"curve3Index = "+curve3Index);
	    IOut.debug(200,"vertexIndex = "+vertexIndex[0] + ", "+vertexIndex[1]);
	    IOut.debug(200,"trimIndex = "+trimIndex);
	    IOut.debug(200,"tolerance = "+tolerance);
	    IOut.debug(200,"domain = "+domain());
	    
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    writeInt32(os,edgeIndex,crc);
	    writeInt32(os,curve3Index,crc);
	    
	    int i = proxyCurveIsReversed()? 1:0;
	    
	    writeInt32(os,i,crc);
	    writeInterval(os,proxyCurveDomain(),crc);
	    
	    writeInt32(os,vertexIndex[0],crc);
	    writeInt32(os,vertexIndex[1],crc);

	    writeArrayInt(os,trimIndex,crc);
	    writeDouble(os,tolerance,crc);
	    
	    if(context.version>=4){
		writeInterval(os,domain(),crc);
	    }
	}
	
    }

    public static class BrepTrimPoint{
	public IVec2 p;
	public double t;
	public double e;
    }
    
    public static class BrepTrim extends CurveProxy{
	public static final String uuid = "60B5DBC2-E660-11d3-BFE4-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeCurve; }
	
	public enum Type{ Unknown, Boundary, Mated, Seam, Singular,
		CrvOnSrf, PtOnSrf, Slit,TrimTypeCount };
	
	public static final int force32BitTrimType = 0xFFFFFFFF;
	
	public int trimIndex;
	
	public int curve2Index;
	public int edgeIndex;
	public int[] vertexIndex;
	public boolean rev3d;
	public Type type;
	public Surface.ISO iso;
	public int loopIndex;
	public double[] tolerance;
	//public ArrayList<BrepTrimPoint> pline;
	//public BoundingBox pbox;
	
	public double legacy2dTol;
	public double legacy3dTol;
	//public int legacyFlag;
	
	public Brep brep;
	
	public BrepTrim(){}
	
	public BrepTrim(int tidx, int c2idx, int eidx, int vidx1, int vidx2, int lidx,
			Interval proxyDomain, Interval domain, Brep brep,
			ITrimCurve trimCurve){
	    trimIndex = tidx;
	    curve2Index = c2idx;
	    edgeIndex = eidx;
	    vertexIndex = new int[2];
	    vertexIndex[0]=vidx1;
	    vertexIndex[1]=vidx2;
	    rev3d = false;
	    type = Type.Boundary; // default
	    loopIndex = lidx;
	    tolerance = new double[2];
	    tolerance[0] = 0; // ?
	    tolerance[1] = 0; // ?
	    legacy2dTol = 0; // ?
	    legacy3dTol = 0; // ?
	    
	    //iso = Surface.ISO.NotIso;
	    iso = getISOType(trimCurve);
	    
	    setProxyCurveDomain(proxyDomain);
	    setDomain(domain);
	    
	    this.brep = brep;
	}
	
	public Surface.ISO getISOType(ITrimCurve trimCurve){
	    
	    if(isStraightOnX(trimCurve)){
		if(trimCurve.num()==2){
		    if(trimCurve.cp(0).y() == 0.0) return Surface.ISO.SIso;
		    if(trimCurve.cp(0).y() == 1.0) return Surface.ISO.NIso;
		}
		return Surface.ISO.XIso;
	    }
	    if(isStraightOnY(trimCurve)){
		if(trimCurve.num()==2){
		    if(trimCurve.cp(0).x() == 0.0) return Surface.ISO.WIso;
		    if(trimCurve.cp(0).x() == 1.0) return Surface.ISO.EIso;
		}
		return Surface.ISO.YIso;
	    }
	    return Surface.ISO.NotIso;
	}
	
	public boolean isStraightOnX(ITrimCurve trimCurve){
	    for(int i=1; i<trimCurve.num(); i++)
		if( ! trimCurve.cp(0).eqY(trimCurve.cp(i))) return false;
	    return true;
	}
	
	public boolean isStraightOnY(ITrimCurve trimCurve){
	    for(int i=1; i<trimCurve.num(); i++)
		if( ! trimCurve.cp(0).eqX(trimCurve.cp(i))) return false;
	    return true;
	}
	
	
	static public Type readType(int i){
	    final int unknown = 0;
	    final int boundary = 1;
	    final int mated = 2;
	    final int seam = 3;
	    final int singular = 4;
	    final int crvonsrf = 5;
	    final int ptonsrf = 6;
	    final int slit = 7;
	    switch(i){
	    case unknown:
		return Type.Unknown;
	    case boundary:
		return Type.Boundary;
	    case mated:
		return Type.Mated;
	    case seam:
		return Type.Seam;
	    case singular:
		return Type.Singular;
	    case crvonsrf:
		return Type.CrvOnSrf;
	    case ptonsrf:
		return Type.PtOnSrf;
	    case slit:
		return Type.Slit;
	    }
	    return Type.Unknown;
	}
	static public int getInt(Type type){
	    final int unknown = 0;
	    final int boundary = 1;
	    final int mated = 2;
	    final int seam = 3;
	    final int singular = 4;
	    final int crvonsrf = 5;
	    final int ptonsrf = 6;
	    final int slit = 7;
	    switch(type){
	    case Unknown:
		return unknown;
	    case Boundary:
		return boundary;
	    case Mated:
		return mated;
	    case Seam:
		return seam;
	    case Singular:
		return singular;
	    case CrvOnSrf:
		return crvonsrf;
	    case PtOnSrf:
		return ptonsrf;
	    case Slit:
		return slit;
	    }
	    return unknown;
	}
	
	public boolean reverse(){
	    super.reverse();
	    rev3d=!rev3d;
	    return true;
	}
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    trimIndex = readInt(is);
	    curve2Index = readInt(is);
	    Interval d = readInterval(is);
	    setProxyCurveDomain(d);
	    setDomain(d);
	    edgeIndex = readInt(is);
	    vertexIndex = new int[2];
	    vertexIndex[0] = readInt(is);
	    vertexIndex[1] = readInt(is);
	    rev3d = readInt(is)!=0;
	    type = readType(readInt(is));
	    iso = Surface.readIso(readInt(is));
	    loopIndex = readInt(is);
	    tolerance = new double[2];
	    tolerance[0] = readDouble(is);
	    tolerance[1] = readDouble(is);
	    
	    if(context.version>=3 && context.openNurbsVersion >= 200206180){
		Interval pd = proxyCurveDomain();
		try{ pd = readInterval(is); }
		catch(IOException e){ pd = proxyCurveDomain(); }
		
		boolean proxyCurveIsReversed=false;
		byte[] b = readBytes(is, 8);
		if(b[0]==1) proxyCurveIsReversed=true;
		
		b = readBytes(is,24);
		
		if(proxyCurveIsReversed){ reverse(); }
		setDomain(pd);
	    }
	    else{
		IVec p0 = readPoint3(is);
		IVec p1 = readPoint3(is);
	    }
	    
	    legacy2dTol = readDouble(is);
	    legacy3dTol = readDouble(is);
	    
	    //IOut.p("reading complete");
	    //IOut.p(this);
	    
	    //
	    IOut.debug(200, "trimIndex = "+trimIndex);
	    IOut.debug(200, "curve2Index = "+curve2Index);
	    IOut.debug(200, "thisDomain = "+thisDomain);
	    IOut.debug(200, "realCurveDomain = "+realCurveDomain);
	    IOut.debug(200, "edgeIndex = "+edgeIndex);
	    IOut.debug(200, "vertexIndex = "+vertexIndex[0]+", "+vertexIndex[1]);
	    IOut.debug(200, "rev3d = "+rev3d); //
	    IOut.debug(200, "type = "+type);
	    IOut.debug(200, "iso = "+iso);
	    IOut.debug(200, "loopIndex = "+loopIndex);
	    IOut.debug(200, "tolerance = "+tolerance[0]+", "+tolerance[1]); 
	    
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    
	    writeInt32(os,trimIndex,crc);
	    writeInt32(os,curve2Index,crc);
	    writeInterval(os,proxyCurveDomain(),crc);
	    writeInt32(os,edgeIndex,crc);
	    writeInt32(os,vertexIndex[0],crc);
	    writeInt32(os,vertexIndex[1],crc);
	    writeInt32(os,rev3d?1:0,crc);
	    
	    writeInt32(os, getInt(type), crc);
	    writeInt32(os, Surface.getInt(iso), crc);
	    
	    writeInt32(os, loopIndex, crc);
	    
	    writeDouble(os, tolerance[0], crc);
	    writeDouble(os, tolerance[1], crc);
	    
	    if(context.version<3){
		IVec p = new IVec(0.,0.,0.);
		writePoint(os, p, crc);
		writePoint(os, p, crc);
	    }
	    else{
		
		writeInterval(os, domain(), crc);
		byte b = proxyCurveIsReversed()?(byte)1:(byte)0;
		
		writeByte(os, b, crc);
		for(int i=0; i<7; i++) writeByte(os, (byte)0, crc);
		
		for(int i=0; i<24; i++) writeByte(os, (byte)0, crc);
		
	    }
	    
	    writeDouble(os, legacy2dTol, crc);
	    writeDouble(os, legacy3dTol, crc);
	    
	}
	
	public String toString(){
	    return "trimIndex = "+trimIndex + "\n" +
		"curve2Index = "+curve2Index + "\n" +
		"edgeIndex = " + edgeIndex + "\n" +
		"vertexIndex[0] = "+vertexIndex[0] + "\n" +
		"vertexIndex[1] = "+vertexIndex[1] + "\n" +
		"rev3d = " + rev3d + "\n" +
		"type = "+type + "\n" +
		"iso = "+ iso + "\n" +
		"loopIndex = " + loopIndex + "\n" +
		"tolerance[0] = "+ tolerance[0] +  "\n" +
		"tolerance[1] = "+ tolerance[1] +  "\n" +
		"legacy2dTol = "+legacy2dTol + "\n" +
		"legacy3dTol = "+legacy3dTol + "\n" +
		"brep = "+brep ;
	}
    }
    
    public static class BrepLoop extends Geometry{
	public static final String uuid = "60B5DBC3-E660-11d3-BFE4-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnkown; }
	
	public enum Type{ Unknown, Outer, Inner, Slit, CrvOnSrf, PtOnSrf, TypeCount };
	
	public int loopIndex;
	
	public ArrayList<Integer> trimIndex;
	public Type type;
	public int faceIndex;
	
	public BoundingBox pbox;
	
	public Brep brep;
	
	public BrepLoop(){}
	public BrepLoop(int lidx, int fidx, Type type, Brep brep){
	    loopIndex = lidx;
	    faceIndex = fidx;
	    trimIndex = new ArrayList<Integer>();
	    this.type = type;
	    this.brep = brep;
	}
	
	public void addTrimIndex(int i){ trimIndex.add(i); }
	
	
	static public Type readType(int i){
	    final int unknown=0;
	    final int outer=1;
	    final int inner=2;
	    final int slit=3;
	    final int crvonsrf=4;
	    final int ptonsrf=5;
	    switch(i){
	    case unknown:
		return Type.Unknown;
	    case outer:
		return Type.Outer;
	    case inner:
		return Type.Inner;
	    case slit:
		return Type.Slit;
	    case crvonsrf:
		return Type.CrvOnSrf;
	    case ptonsrf:
		return Type.PtOnSrf;
	    }
	    return Type.Unknown;
	}
	static public int getInt(Type i){
	    final int unknown=0;
	    final int outer=1;
	    final int inner=2;
	    final int slit=3;
	    final int crvonsrf=4;
	    final int ptonsrf=5;
	    switch(i){
	    case Unknown:
		return unknown;
	    case Outer:
		return outer;
	    case Inner:
		return inner;
	    case Slit:
		return slit;
	    case CrvOnSrf:
		return crvonsrf;
	    case PtOnSrf:
		return ptonsrf;
	    }
	    return unknown;
	}
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    loopIndex = readInt(is);
	    trimIndex = readArrayInt(is);
	    type = readType(readInt(is));
	    faceIndex = readInt(is);
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    writeInt32(os,loopIndex,crc);
	    writeArrayInt(os,trimIndex,crc);
	    writeInt32(os,getInt(type),crc);
	    writeInt32(os,faceIndex,crc);
	}
	
    }
    public static class BrepFace extends SurfaceProxy{
	public static final String uuid = "60B5DBC4-E660-11d3-BFE4-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnkown; }
	
	public int faceIndex;
	public ArrayList<Integer> loopIndex;
	public int surfaceIndex;
	public boolean rev;
	
	public int faceMaterialChannel;
	
	public UUID faceUUID;
	public BoundingBox bbox;
	public Interval[] domain;
	public Mesh renderMesh;
	public Mesh analysisMesh;
	public Mesh previewMesh;
	
	public Brep brep;
	
	public BrepFace(){}
	
	public BrepFace(int fidx, int sidx, Surface surf, Brep brep){
	    faceIndex = fidx;
	    surfaceIndex = sidx;
	    this.brep = brep;
	    
	    loopIndex = new ArrayList<Integer>();
	    
	    faceUUID = surf.getClassUUID(); // class UUID? not object UUID?
	    
	    domain = new Interval[2];
	    domain[0] = surf.domain(0);
	    domain[1] = surf.domain(1);
	    
	    rev=false;
	    faceMaterialChannel=0;
	}
	
	public void addLoopIndex(int i){ loopIndex.add(i); }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    faceIndex = readInt(is);
	    loopIndex = readArrayInt(is);
	    surfaceIndex = readInt(is);
	    rev = readInt(is)!=0;
	    faceMaterialChannel = readInt(is);
	    if(faceMaterialChannel<0) faceMaterialChannel=0;
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    writeInt32(os,faceIndex,crc);
	    writeArrayInt(os,loopIndex,crc);
	    writeInt32(os,surfaceIndex,crc);
	    writeInt32(os,rev?1:0,crc);
	    writeInt32(os,faceMaterialChannel,crc);
	}
	
    }
    
    public static class CurveArray extends ArrayList<Curve>{
	public CurveArray(){ super(); }
	public CurveArray(int c){ super(c); }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    Chunk chunk = readChunk(is);
	    if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
	    is = new ByteArrayInputStream(chunk.content);
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		int count = readInt(is);
		for(int i=0; i<count; i++){
		    int flag = readInt(is);
		    if(flag==1){
			RhinoObject obj = readObject(context, is);
			if(obj==null) throw new IOException("instance is null");
			if(obj instanceof Curve){
			    add((Curve)obj);
			}
			else{ throw new IOException("invalid class of instance: "+obj); }
		    }
		}
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    writeChunkVersion(cos, 1, 0, cos.getCRC());
	    
	    int size = this.size();
	    writeInt32(cos, size, cos.getCRC());
	    for(int i=0; i<size; i++){
		if(this.get(i)!=null){
		    writeInt32(cos, 1, cos.getCRC());
		    Chunk objChunk = getObjectChunk(context,this.get(i));
		    writeChunk(cos, objChunk);
		}
		else{
		    writeInt32(cos, 0, cos.getCRC());
		}
	    }
	    writeChunk(os, cos.getChunk());
	}
	
    }
    
    public static class SurfaceArray extends ArrayList<Surface>{
	public SurfaceArray(){ super(); }
	public SurfaceArray(int c){ super(c); }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    Chunk chunk = readChunk(is);
	    if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
	    is = new ByteArrayInputStream(chunk.content);
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		int count = readInt(is);
		for(int i=0; i<count; i++){
		    int flag = readInt(is);
		    if(flag==1){
			RhinoObject obj = readObject(context, is);
			//IOut.p(obj); //
			
			if(obj==null) throw new IOException("instance is null");
			
			if(obj instanceof Surface){
			    add((Surface)obj);
			}
			else{ throw new IOException("invalid class of instance: "+obj); }
		    }
		}
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    writeChunkVersion(cos, 1, 0, cos.getCRC());
	    
	    int size = this.size();
	    writeInt32(cos, size, cos.getCRC());
	    for(int i=0; i<size; i++){
		if(this.get(i)!=null){
		    writeInt32(cos, 1, cos.getCRC());
		    Chunk objChunk = getObjectChunk(context,this.get(i));
		    writeChunk(cos, objChunk);
		}
		else{
		    writeInt32(cos, 0, cos.getCRC());
		}
	    }
	    writeChunk(os, cos.getChunk());
	}
	
    }
    public static class BrepVertexArray extends ArrayList<BrepVertex>{
	public BrepVertexArray(){ super(); }
	public BrepVertexArray(int c){ super(c); }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    Chunk chunk = readChunk(is);
	    if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
	    is = new ByteArrayInputStream(chunk.content);
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		int count = readInt(is);
		for(int i=0; i<count; i++){
		    BrepVertex vertex = new BrepVertex();
		    vertex.read(context,is);
		    add(vertex);
		}
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    writeChunkVersion(cos, 1, 0, cos.getCRC());
	    
	    int size = this.size();
	    writeInt32(cos, size, cos.getCRC());
	    for(int i=0; i<size; i++){
		this.get(i).write(context,cos,cos.getCRC());
		//Chunk objChunk = getObjectChunk(context,this.get(i));
		//writeChunk(cos, objChunk);
	    }
	    writeChunk(os, cos.getChunk());
	}
	
    }
    public static class BrepEdgeArray extends ArrayList<BrepEdge>{
	public BrepEdgeArray(){ super(); }
	public BrepEdgeArray(int c){ super(c); }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    Chunk chunk = readChunk(is);
	    if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
	    is = new ByteArrayInputStream(chunk.content);
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		int count = readInt(is);
		for(int i=0; i<count; i++){
		    BrepEdge edge = new BrepEdge();
		    edge.read(context,is);
		    add(edge);
		}
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    writeChunkVersion(cos, 1, 0, cos.getCRC());
	    
	    int size = this.size();
	    writeInt32(cos, size, cos.getCRC());
	    for(int i=0; i<size; i++){
		this.get(i).write(context,cos,cos.getCRC());
		//Chunk objChunk = getObjectChunk(context,this.get(i));
		//writeChunk(cos, objChunk);
	    }
	    writeChunk(os, cos.getChunk());
	}
	
    }
    
    public static class BrepTrimArray extends ArrayList<BrepTrim>{
	public BrepTrimArray(){ super(); }
	public BrepTrimArray(int c){ super(c); }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    Chunk chunk = readChunk(is);
	    if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
	    is = new ByteArrayInputStream(chunk.content);
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    if(majorVersion==1){
		int count = readInt(is);
		for(int i=0; i<count; i++){
		    BrepTrim trim = new BrepTrim();
		    trim.read(context,is);
		    add(trim);
		}
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    writeChunkVersion(cos, 1, 0, cos.getCRC());
	    
	    int size = this.size();
	    writeInt32(cos, size, cos.getCRC());
	    for(int i=0; i<size; i++){
		this.get(i).write(context,cos,cos.getCRC());
		//Chunk objChunk = getObjectChunk(context,this.get(i)); // ?
		//writeChunk(cos, objChunk);
	    }
	    writeChunk(os, cos.getChunk());
	}
	
    }
    public static class BrepLoopArray extends ArrayList<BrepLoop>{
	public BrepLoopArray(){ super(); }
	public BrepLoopArray(int c){ super(c); }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    Chunk chunk = readChunk(is);
	    if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
	    is = new ByteArrayInputStream(chunk.content);
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    if(majorVersion==1){
		int count = readInt(is);
		for(int i=0; i<count; i++){
		    BrepLoop loop = new BrepLoop();
		    loop.read(context,is);
		    add(loop);
		}
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    writeChunkVersion(cos, 1, 0, cos.getCRC());
	    
	    int size = this.size();
	    writeInt32(cos, size, cos.getCRC());
	    for(int i=0; i<size; i++){
		this.get(i).write(context,cos,cos.getCRC());
		//Chunk objChunk = getObjectChunk(context,this.get(i));
		//writeChunk(cos, objChunk);
	    }
	    writeChunk(os, cos.getChunk());
	}
	
    }
    public static class BrepFaceArray extends ArrayList<BrepFace>{
	public BrepFaceArray(){ super(); }
	public BrepFaceArray(int c){ super(c); }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    Chunk chunk = readChunk(is);
	    if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
	    is = new ByteArrayInputStream(chunk.content);
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    if(majorVersion==1){
		int count = readInt(is);
		for(int i=0; i<count; i++){
		    BrepFace face = new BrepFace();
		    face.read(context,is);
		    add(face);
		}
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    //writeChunkVersion(cos, 1, 0, cos.getCRC());
	    writeChunkVersion(cos, 1, 1, cos.getCRC());
	    
	    // version 1.0
	    int size = this.size();
	    writeInt32(cos, size, cos.getCRC());
	    for(int i=0; i<size; i++){
		this.get(i).write(context,cos,cos.getCRC());
		//Chunk objChunk = getObjectChunk(context,this.get(i));
		//writeChunk(cos, objChunk);
	    }
	    
	    // version 1.1 addition
	    for(int i=0; i<size; i++){
		writeUUID(cos, this.get(i).faceUUID, cos.getCRC());
	    }
	    
	    writeChunk(os, cos.getChunk());
	}
	
    }
    
    public static class Brep extends Geometry{
	public static final String uuid = "60B5DBC5-E660-11d3-BFE4-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeBrep; }
	
	public CurveArray curves2;
	public CurveArray curves3;
	public SurfaceArray surfaces;
	public BrepVertexArray vertices;
	public BrepEdgeArray edges;
	public BrepTrimArray trims;
	public BrepLoopArray loops;
	public BrepFaceArray faces;
	
	
	public BoundingBox bbox;
	public int isSolid;
	
	//public ArrayList<ICurveGeo> icurves3; // to check joined trim edge
	public ArrayList<ArrayList<ICurveGeo>> icurves3; // to check joined trim edge; icurves per surface
	//public ArrayList<IVec> ivertices; // to check shared vertices
	public ArrayList<ISurfaceGeo> isurfaces; 
	
	public Brep(){}
	
	public Brep(IBrep brep){
	    
	    curves2 = new CurveArray();
	    curves3 = new CurveArray();
	    surfaces = new SurfaceArray();
	    vertices = new BrepVertexArray();
	    edges = new BrepEdgeArray();
	    trims = new BrepTrimArray();
	    loops = new BrepLoopArray();
	    faces = new BrepFaceArray();
	    
	    bbox = new BoundingBox();
	    
	    //icurves3 = new ArrayList<ICurveGeo>();
	    icurves3 = new ArrayList<ArrayList<ICurveGeo>>();
	    //ivertices = new ArrayList<ICurveGeo>();
	    isurfaces = new ArrayList<ISurfaceGeo>();
	    
	    if(brep.solid) isSolid=1;
	    else isSolid=0;
	    
	    IBounds ibbox = new IBounds();
	    
	    if(brep.surfaces!=null){
		for(int i=0; i<brep.surfaces.length; i++){ addSurface(brep.surfaces[i], ibbox); }
	    }
	    
	    /*
	    //curves3
	    if(brep.seams!=null){
		for(int i=0; i<brep.seams.length; i++){
		    NurbsCurve ncrv3 = new NurbsCurve(brep.seams[i]);
		    curves3.add(ncrv3);
		}
	    }
	    */
	    
	    bbox.min = ibbox.min;
	    bbox.max = ibbox.max;

	    if(bbox.min==null){ // this should not happen. should be excluded by isValid beforehand
		IOut.err("minimum range of boundary box is null");
		bbox.min=new IVec();
	    }
	    if(bbox.max==null){ // this should not happen. should be excluded by isValid beforehand
		IOut.err("maximum range of boundary box is null");
		bbox.max=new IVec();
	    }
	}
	
	// trimmed surface
	public Brep(ISurfaceGeo srf){
	    
	    curves2 = new CurveArray();
	    curves3 = new CurveArray();
	    surfaces = new SurfaceArray();
	    vertices = new BrepVertexArray();
	    edges = new BrepEdgeArray();
	    trims = new BrepTrimArray();
	    loops = new BrepLoopArray();
	    faces = new BrepFaceArray();
	    
	    bbox = new BoundingBox();
	    isSolid=0;
	    
	    //icurves3 = new ArrayList<ICurveGeo>();
	    icurves3 = new ArrayList<ArrayList<ICurveGeo>>();
	    //ivertices = new ArrayList<IVec>();
	    isurfaces = new ArrayList<ISurfaceGeo>();
	    
	    IBounds ibbox = new IBounds();
	    
	    addSurface(srf, ibbox);
	    
	    bbox.min = ibbox.min;
	    bbox.max = ibbox.max;

	    if(bbox.min==null){ // this should not happen. should be excluded by isValid beforehand
		IOut.err("minimum range of boundary box is null");
		bbox.min=new IVec();
	    }
	    if(bbox.max==null){ // this should not happen. should be excluded by isValid beforehand
		IOut.err("maximum range of boundary box is null");
		bbox.max=new IVec();
	    }
	    
	    
	    /*
	    NurbsSurface nsrf = new NurbsSurface(srf);
	    surfaces.add(nsrf);
	    
	    BrepFace face = new BrepFace(faces.size(),surfaces.size()-1,nsrf,this);
	    faces.add(face);
	    
	    // untrim area should not be included in bounding box?
	    //for(int i=0; i<srf.unum(); i++) for(int j=0; j<srf.vnum(); j++) ibbox.comare(srf.cp(i,j));
	    
	    ArrayList<ArrayList<ITrimCurve>> trimLoops = new ArrayList<ArrayList<ITrimCurve>>();
	    ArrayList<BrepLoop.Type> loopType = new ArrayList<BrepLoop.Type>();
	    
	    //if(!srf.hasOuterTrim() && srf.hasInnerTrim()){
		// adding default outer trim
		ArrayList<ITrimCurve> defaultLoop = new ArrayList<ITrimCurve>();
		defaultLoop.add(new ITrimCurve(new IVec(0.,0.,0.),new IVec(1.,0.,0.)).surface(srf));
		defaultLoop.add(new ITrimCurve(new IVec(1.,0.,0.),new IVec(1.,1.,0.)).surface(srf));
		defaultLoop.add(new ITrimCurve(new IVec(1.,1.,0.),new IVec(0.,1.,0.)).surface(srf));
		defaultLoop.add(new ITrimCurve(new IVec(0.,1.,0.),new IVec(0.,0.,0.)).surface(srf));
		trimLoops.add(defaultLoop);
		loopType.add(BrepLoop.Type.Outer);
	    }
	    
	    for(int i=0; i<srf.outerTrimLoopNum(); i++){
		ArrayList<ITrimCurve> outLoop = new ArrayList<ITrimCurve>();
		for(int j=0; j<srf.outerTrimNum(i); j++) outLoop.add(srf.outerTrim(i,j).get());
		trimLoops.add(outLoop);
		loopType.add(BrepLoop.Type.Outer);
	    }
	    
	    for(int i=0; i<srf.innerTrimLoopNum(); i++){
		ArrayList<ITrimCurve> inLoop = new ArrayList<ITrimCurve>();
		for(int j=0; j<srf.innerTrimNum(i); j++) inLoop.add(srf.innerTrim(i,j).get());
		trimLoops.add(inLoop);
		loopType.add(BrepLoop.Type.Inner);
	    }
	    
	    // check direction
	    checkTrimLoopDirection(trimLoops, loopType);
	    	    
	    for(int i=0; i<trimLoops.size(); i++){
		BrepLoop loop =
		    new BrepLoop(loops.size(),faces.size()-1,loopType.get(i), this);
		loops.add(loop);
		face.addLoopIndex(loops.size()-1);
		BrepVertex vtx0 = null;
		for(int j=0; j<trimLoops.get(i).size(); j++){
		    ITrimCurve trim2d = trimLoops.get(i).get(j);
		    NurbsCurve ncrv = new NurbsCurve(trim2d);
		    curves2.add(ncrv);
		    ICurveGeo trim3d = trim2d.get3d();
		    NurbsCurve ncrv3 = new NurbsCurve(trim3d);
		    curves3.add(ncrv3);
		    
		    // update bounding box; only approximation
		    for(int k=0; k<trim3d.num(); k++) ibbox.compare(trim3d.cp(k));
		    
		    BrepVertex vtx1 = null;
		    if(j==0){
			vtx1 = new BrepVertex(vertices.size(),trim3d.start());
			vertices.add(vtx1);
			vtx0 = vtx1; // start point of loop
		    }
		    else{
			vtx1 = vertices.get(vertices.size()-1);
		    }
		    BrepVertex vtx2 = null;
		    if(j < trimLoops.get(i).size()-1){
			vtx2 = new BrepVertex(vertices.size(),trim3d.end());
			vertices.add(vtx2);
		    }
		    else{ // last one or closed one
			vtx2 = vtx0;
		    }
		    
		    Interval c3domain = ncrv3.domain();
		    BrepEdge edge = new BrepEdge (edges.size(), curves3.size()-1,
						  vtx1.vertexIndex, vtx2.vertexIndex,
						  c3domain, c3domain, this);
		    edges.add(edge);
		    
		    Interval c2domain = ncrv.domain();
		    BrepTrim trim =
			new BrepTrim(trims.size(),curves2.size()-1,edges.size()-1,
				     vtx1.vertexIndex, vtx2.vertexIndex, loops.size()-1,
				     c2domain, c2domain, this, trim2d);
		    trims.add(trim);
		    loop.addTrimIndex(trims.size()-1);
		    
		    edge.addTrimIndex(trims.size()-1);
		    
		    vtx1.addEdgeIndex(edges.size()-1);
		    vtx2.addEdgeIndex(edges.size()-1);
		}
	    }
	    */
	    
	    
	    /*
	    
	    for(int i=0; i<srf.outerTrimLoopNum(); i++){
		BrepLoop loop =
		    new BrepLoop(loops.size(),faces.size()-1,BrepLoop.Type.Outer, this);
		loops.add(loop);
		face.addLoopIndex(loops.size()-1);
		BrepVertex vtx0 = null;
		for(int j=0; j<srf.outerTrimNum(i); j++){
		    ITrimCurve trim2d = srf.outerTrim(i,j).get();
		    NurbsCurve ncrv = new NurbsCurve(trim2d);
		    curves2.add(ncrv);
		    ICurveGeo trim3d = srf.outerTrim(i,j).get3d();
		    NurbsCurve ncrv3 = new NurbsCurve(trim3d);
		    curves3.add(ncrv3);
		    
		    for(int k=0; k<trim3d.num(); k++) ibbox.compare(trim3d.cp(k));
		    
		    BrepVertex vtx1 = null;
		    if(j==0){
			vtx1 = new BrepVertex(vertices.size(),trim3d.start());
			vertices.add(vtx1);
			
			vtx0 = vtx1; // start point of loop
		    }
		    else{
			vtx1 = vertices.get(vertices.size()-1);
		    }
		    BrepVertex vtx2 = null;
		    if(j < srf.outerTrimNum(i)-1){
			vtx2 = new BrepVertex(vertices.size(),trim3d.end());
			vertices.add(vtx2);
		    }
		    else{ // last one or closed one
			//vtx2 = vertices.get(0);
			vtx2 = vtx0;
		    }
		    
		    Interval c3domain = ncrv3.domain();
		    BrepEdge edge = new BrepEdge (edges.size(), curves3.size()-1,
						  vtx1.vertexIndex, vtx2.vertexIndex,
						  c3domain, c3domain, this);
		    edges.add(edge);
		    
		    Interval c2domain = ncrv.domain();
		    BrepTrim trim =
			new BrepTrim(trims.size(),curves2.size()-1,edges.size()-1,
				     vtx1.vertexIndex, vtx2.vertexIndex, loops.size()-1,
				     c2domain, c2domain, this, trim2d);
		    trims.add(trim);
		    loop.addTrimIndex(trims.size()-1);
		    
		    edge.addTrimIndex(trims.size()-1);
		    
		    vtx1.addEdgeIndex(edges.size()-1);
		    vtx2.addEdgeIndex(edges.size()-1);
		}
	    }
	    
	    
	    for(int i=0; i<srf.innerTrimLoopNum(); i++){
		BrepLoop loop =
		    new BrepLoop(loops.size(),faces.size()-1,BrepLoop.Type.Inner, this);
		loops.add(loop);
		face.addLoopIndex(loops.size()-1);
		BrepVertex vtx0 = null;
		for(int j=0; j<srf.innerTrimNum(i); j++){
		    ITrimCurve trim2d = srf.innerTrim(i,j).get();
		    NurbsCurve ncrv = new NurbsCurve(trim2d);
		    curves2.add(ncrv);
		    ICurveGeo trim3d = srf.innerTrim(i,j).get3d();
		    NurbsCurve ncrv3 = new NurbsCurve(trim3d);
		    curves3.add(ncrv3);
		    
		    for(int k=0; k<trim3d.num(); k++) ibbox.compare(trim3d.cp(k));
		    
		    BrepVertex vtx1 = null;
		    if(j==0){
			vtx1 = new BrepVertex(vertices.size(),trim3d.start());
			vertices.add(vtx1);
			
			vtx0 = vtx1; // start point of loop
		    }
		    else{
			vtx1 = vertices.get(vertices.size()-1);
		    }
		    BrepVertex vtx2 = null;
		    if(j < srf.innerTrimNum(i)-1){
			vtx2 = new BrepVertex(vertices.size(),trim3d.end());
			vertices.add(vtx2);
		    }
		    else{ // last one or closed one
			//vtx2 = vertices.get(0);
			vtx2 = vtx0;
		    }
		    
		    Interval c3domain = ncrv3.domain();
		    BrepEdge edge = new BrepEdge (edges.size(), curves3.size()-1,
						  vtx1.vertexIndex, vtx2.vertexIndex,
						  c3domain, c3domain,
						  this);
		    edges.add(edge);
		    Interval c2domain = ncrv.domain();
		    BrepTrim trim =
			new BrepTrim(trims.size(),curves2.size()-1,edges.size()-1,
				     vtx1.vertexIndex, vtx2.vertexIndex, loops.size()-1,
				     c2domain, c2domain, this, trim2d);
		    trims.add(trim);
		    loop.addTrimIndex(trims.size()-1);
		    
		    edge.addTrimIndex(trims.size()-1);
		    
		    vtx1.addEdgeIndex(edges.size()-1);
		    vtx2.addEdgeIndex(edges.size()-1);
		}
	    }
	    */
	    
	    //bbox.min = ibbox.min;
	    //bbox.max = ibbox.max;
	}
	

	public void getTrimLoops(ISurfaceGeo srf,
				 ArrayList<ArrayList<ITrimCurve>> trimLoops,
				 ArrayList<BrepLoop.Type> loopType){
	    
	    //if(!srf.hasOuterTrim() && srf.hasInnerTrim()){
	    if(!srf.hasOuterTrim()){ // in brep, every surface needs trim curve
		// adding default outer trim
		boolean uclose = srf.isUClosed();
		boolean vclose = srf.isVClosed();
		if(!uclose && !uclose){
		    ArrayList<ITrimCurve> defaultLoop = new ArrayList<ITrimCurve>();
		    defaultLoop.add(new ITrimCurve(new IVec(0.,0.,0.),new IVec(1.,0.,0.)).surface(srf));
		    defaultLoop.add(new ITrimCurve(new IVec(1.,0.,0.),new IVec(1.,1.,0.)).surface(srf));
		    defaultLoop.add(new ITrimCurve(new IVec(1.,1.,0.),new IVec(0.,1.,0.)).surface(srf));
		    defaultLoop.add(new ITrimCurve(new IVec(0.,1.,0.),new IVec(0.,0.,0.)).surface(srf));
		    trimLoops.add(defaultLoop);
		    loopType.add(BrepLoop.Type.Outer);
		}
		else if(uclose){
		    ArrayList<ITrimCurve> loop1 = new ArrayList<ITrimCurve>();
		    loop1.add(new ITrimCurve(new IVec(0.,0.,0.),new IVec(1.,0.,0.)).surface(srf));
		    trimLoops.add(loop1);
		    loopType.add(BrepLoop.Type.Outer);
		    
		    ArrayList<ITrimCurve> loop2 = new ArrayList<ITrimCurve>();
		    loop2.add(new ITrimCurve(new IVec(1.,1.,0.),new IVec(0.,1.,0.)).surface(srf));
		    trimLoops.add(loop2);
		    loopType.add(BrepLoop.Type.Outer);
		}
		else if(vclose){
		    ArrayList<ITrimCurve> loop1 = new ArrayList<ITrimCurve>();
		    loop1.add(new ITrimCurve(new IVec(1.,0.,0.),new IVec(1.,1.,0.)).surface(srf));
		    trimLoops.add(loop1);
		    loopType.add(BrepLoop.Type.Outer);
		    
		    ArrayList<ITrimCurve> loop2 = new ArrayList<ITrimCurve>();
		    loop2.add(new ITrimCurve(new IVec(0.,1.,0.),new IVec(0.,0.,0.)).surface(srf));
		    trimLoops.add(loop2);
		    loopType.add(BrepLoop.Type.Outer);
		}
	    }
	    
	    for(int i=0; i<srf.outerTrimLoopNum(); i++){
		ArrayList<ITrimCurve> outLoop = new ArrayList<ITrimCurve>();
		for(int j=0; j<srf.outerTrimNum(i); j++){
		    if(srf.outerTrim(i,j).deg()==1){ // split into lines
			ITrimCurve tcrv = srf.outerTrim(i,j).get();
			for(int k=0; k<tcrv.num()-1; k++){
			    outLoop.add(new ITrimCurve(tcrv.cp(k), tcrv.cp(k+1)).surface(srf));
			}
		    }
		    else{
			outLoop.add(srf.outerTrim(i,j).get());
		    }
		}
		trimLoops.add(outLoop);
		loopType.add(BrepLoop.Type.Outer);
	    }
	    
	    for(int i=0; i<srf.innerTrimLoopNum(); i++){
		ArrayList<ITrimCurve> inLoop = new ArrayList<ITrimCurve>();
		for(int j=0; j<srf.innerTrimNum(i); j++){
		    if(srf.outerTrim(i,j).deg()==1){ // split into lines
			ITrimCurve tcrv = srf.innerTrim(i,j).get();
			for(int k=0; k<tcrv.num()-1; k++){
			    inLoop.add(new ITrimCurve(tcrv.cp(k), tcrv.cp(k+1)).surface(srf));
			}
		    }
		    else{
			inLoop.add(srf.innerTrim(i,j).get());
		    }
		}
		trimLoops.add(inLoop);
		loopType.add(BrepLoop.Type.Inner);
	    }
	    
	    // check direction
	    checkTrimLoopDirection(trimLoops, loopType);
	}
	
	
	public boolean isEdgeTouching(ICurveGeo crv1, ICurveGeo crv2){
	    return isEdgeTouching(crv1,crv2,IConfig.tolerance);
	}
	
	public boolean isEdgeTouching(ICurveGeo crv1, ICurveGeo crv2, double reso){
	    // check only start point and end point (!) (too rough?) // and midpoint (would it match?)
	    IVec pt1a = crv1.start();
	    IVec pt1b = crv1.end();
	    IVec pt1m = crv1.pt(0.5); //
	    IVec pt2a = crv2.start();
	    IVec pt2b = crv2.end();
	    IVec pt2m = crv2.pt(0.5); //
	    //if(pt1a.eq(pt2a,reso) && pt1b.eq(pt2b,reso)) return true;
	    //if(pt1a.eq(pt2b,reso) && pt1b.eq(pt2a,reso)) return true;
	    if(!pt1m.eq(pt2m,reso)) return false; // added 20120215
	    if(pt1a.eq(pt2a,reso) && pt1b.eq(pt2b,reso)) return true;
	    if(pt1a.eq(pt2b,reso) && pt1b.eq(pt2a,reso)) return true;
	    return false;
	}
	
	//public ICurveGeo getSharedICurve(ICurveGeo crv){
	public ICurveGeo getSharedICurve(ICurveGeo crv, ISurfaceGeo currentSrf){
	    if(icurves3==null || icurves3.size()==0) return null;
	    int idx = -1;
	    if(currentSrf!=null) idx = isurfaces.indexOf(currentSrf);
	    for(int i=0; i<icurves3.size(); i++){
		if(i != idx){ // check only other surfaces' trim curves
		    for(int j=0; j<icurves3.get(i).size(); j++){
			if(isEdgeTouching(crv, icurves3.get(i).get(j))) return icurves3.get(i).get(j);
		    }
		}
	    }
	    return null;
	}
	
	public Curve getCurve3(ICurveGeo crv){
	    for(int i=0; i<curves3.size(); i++){
		if(curves3.get(i).icurve==crv) return curves3.get(i);
	    }
	    return null;
	}
	
	public BrepEdge getBrepEdge(Curve c){
	    int idx = curves3.indexOf(c);
	    if(idx<0) return null;
	    for(int i=0; i<edges.size(); i++){
		if(edges.get(i).curve3Index == idx) return edges.get(i);
	    }
	    return null;
	}

	public BrepTrim getBrepTrim(BrepEdge edge){
	    int idx = edges.indexOf(edge);
	    if(idx<0) return null;
	    for(int i=0; i<trims.size(); i++){
		if(trims.get(i).edgeIndex == idx) return trims.get(i);
	    }
	    return null;
	}
	
	public BrepVertex getSharedVertex(IVec pt){
	    return getSharedVertex(pt, IConfig.tolerance);
	}
	public BrepVertex getSharedVertex(IVec pt, double reso){
	    if(vertices==null||vertices.size()==0) return null;
	    for(int i=0; i<vertices.size(); i++){
		if(vertices.get(i).point.eq(pt, reso)) return vertices.get(i);
	    }
	    return null;
	}
	
	public void addSurface(ISurfaceGeo srf, IBounds ibbox){
	    
	    NurbsSurface nsrf = new NurbsSurface(srf);
	    surfaces.add(nsrf);
	    
	    isurfaces.add(srf); //
	    icurves3.add(new ArrayList<ICurveGeo>()); //
	    
	    BrepFace face = new BrepFace(faces.size(),surfaces.size()-1,nsrf,this);
	    faces.add(face);
	    
	    // untrim area should not be included in bounding box?
	    //for(int i=0; i<srf.unum(); i++) for(int j=0; j<srf.vnum(); j++) ibbox.comare(srf.cp(i,j));
	    
	    ArrayList<ArrayList<ITrimCurve>> trimLoops = new ArrayList<ArrayList<ITrimCurve>>();
	    ArrayList<BrepLoop.Type> loopType = new ArrayList<BrepLoop.Type>();
	    
	    getTrimLoops(srf, trimLoops, loopType);
	    
	    for(int i=0; i<trimLoops.size(); i++){
		BrepLoop loop =
		    new BrepLoop(loops.size(),faces.size()-1,loopType.get(i), this);
		loops.add(loop);
		face.addLoopIndex(loops.size()-1);
		
		//BrepVertex vtx0 = null;
		
		for(int j=0; j<trimLoops.get(i).size(); j++){
		    ITrimCurve trim2d = trimLoops.get(i).get(j);
		    
		    NurbsCurve ncrv = new NurbsCurve(trim2d);
		    curves2.add(ncrv);
		    
		    ICurveGeo trim3d = trim2d.get3d();
		    
		    //ICurveGeo sharedICurve = getSharedICurve(trim3d, srf);
		    ICurveGeo sharedICurve = null; // shared edge algorithm not really working yet. skipped for the moment.
		    Curve sharedCurve3 = null;
		    BrepEdge sharedEdge = null;
		    
		    if(sharedICurve!=null){
			//IOut.err("("+i+","+j+"): sharedICurve!!!!"); //
			
			sharedCurve3 = getCurve3(sharedICurve);
			if(sharedCurve3!=null){
			    sharedEdge = getBrepEdge(sharedCurve3);
			}
		    }
		    
		    
		    if(sharedEdge==null){ // new edge
			
			//icurves3.add(trim3d);
			icurves3.get(icurves3.size()-1).add(trim3d);
			
			NurbsCurve ncrv3 = new NurbsCurve(trim3d);
			curves3.add(ncrv3);
			
			// update bounding box; only approximation
			for(int k=0; k<trim3d.num(); k++) ibbox.compare(trim3d.cp(k).get());
			
			IVec pt1 = trim3d.start();
			BrepVertex vtx1 = getSharedVertex(pt1);
			if(vtx1==null){
			    vtx1 = new BrepVertex(vertices.size(),pt1);
			    vertices.add(vtx1);
			}
			
			IVec pt2 = trim3d.end();
			BrepVertex vtx2 = getSharedVertex(pt2);
			if(vtx2==null){
			    vtx2 = new BrepVertex(vertices.size(),pt2);
			    vertices.add(vtx2);
			}
			
			//IOut.err("edges="+edges);
			//IOut.err("curves3="+curves3);
			//IOut.err("vtx1="+vtx1);
			//IOut.err("vtx2="+vtx2);
			
			Interval c3domain = ncrv3.domain();
			BrepEdge edge = new BrepEdge (edges.size(), curves3.indexOf(ncrv3), /*curves3.size()-1,*/
						      vtx1.vertexIndex, vtx2.vertexIndex,
						      c3domain, c3domain, this);
			edges.add(edge);
			
			vtx1.addEdgeIndex(edge.edgeIndex);
			vtx2.addEdgeIndex(edge.edgeIndex);
			
			Interval c2domain = ncrv.domain();
			Interval c2ProxyDomain = new Interval(c2domain);
			BrepTrim trim =
			    new BrepTrim(trims.size(),
					 curves2.indexOf(ncrv),
					 edges.indexOf(edge), /*edges.size()-1,*/
					 vtx1.vertexIndex, vtx2.vertexIndex, loop.loopIndex,/*loops.size()-1,*/
					 c2ProxyDomain, c2domain, this, trim2d);
			trims.add(trim);
			loop.addTrimIndex(trim.trimIndex);
			edge.addTrimIndex(trim.trimIndex);
			
		    }
		    else{
			Interval c2domain = ncrv.domain();
			Interval c2ProxyDomain = new Interval(c2domain);
			
			BrepVertex vtx1 = vertices.get(sharedEdge.vertexIndex[0]);
			BrepVertex vtx2 = vertices.get(sharedEdge.vertexIndex[1]);
						
			//IVec pt1 = trim3d.start();
			boolean reverse=false;
			//if(pt1.dist(vtx1.point) > pt1.dist(vtx2.point)){
			if(!trim3d.start().eq(sharedICurve.start())){
			    //vtx1 = vertices.get(sharedEdge.vertexIndex[1]);
			    //vtx2 = vertices.get(sharedEdge.vertexIndex[0]);
			    reverse=true;
			}
			
			BrepTrim trim =
			    new BrepTrim(trims.size(),
					 curves2.indexOf(ncrv),
					 sharedEdge.edgeIndex,
					 vtx1.vertexIndex, vtx2.vertexIndex, loop.loopIndex , /*loops.size()-1,*/
					 c2ProxyDomain, c2domain, this, trim2d);
			
			trim.type = BrepTrim.Type.Mated;
			BrepTrim sharedTrim = getBrepTrim(sharedEdge);
			if(sharedTrim!=null) sharedTrim.type = BrepTrim.Type.Mated;
			
			if(reverse) trim.reverse();
			
			trims.add(trim);
			
			loop.addTrimIndex(trim.trimIndex);
			sharedEdge.addTrimIndex(trim.trimIndex);
			
		    }
		}
	    }
	}
	
	
	public void addSingleSurface(ISurfaceGeo srf, IBounds ibbox){
	    
	    NurbsSurface nsrf = new NurbsSurface(srf);
	    surfaces.add(nsrf);
	    
	    BrepFace face = new BrepFace(faces.size(),surfaces.size()-1,nsrf,this);
	    faces.add(face);
	    
	    // untrim area should not be included in bounding box?
	    //for(int i=0; i<srf.unum(); i++) for(int j=0; j<srf.vnum(); j++) ibbox.comare(srf.cp(i,j));
	    
	    ArrayList<ArrayList<ITrimCurve>> trimLoops = new ArrayList<ArrayList<ITrimCurve>>();
	    ArrayList<BrepLoop.Type> loopType = new ArrayList<BrepLoop.Type>();
	    
	    //if(!srf.hasOuterTrim() && srf.hasInnerTrim()){
	    if(!srf.hasOuterTrim()){ // in brep, every surface needs trim curve
		// adding default outer trim
		ArrayList<ITrimCurve> defaultLoop = new ArrayList<ITrimCurve>();
		defaultLoop.add(new ITrimCurve(new IVec(0.,0.,0.),new IVec(1.,0.,0.)).surface(srf));
		defaultLoop.add(new ITrimCurve(new IVec(1.,0.,0.),new IVec(1.,1.,0.)).surface(srf));
		defaultLoop.add(new ITrimCurve(new IVec(1.,1.,0.),new IVec(0.,1.,0.)).surface(srf));
		defaultLoop.add(new ITrimCurve(new IVec(0.,1.,0.),new IVec(0.,0.,0.)).surface(srf));
		trimLoops.add(defaultLoop);
		loopType.add(BrepLoop.Type.Outer);
	    }
	    
	    for(int i=0; i<srf.outerTrimLoopNum(); i++){
		ArrayList<ITrimCurve> outLoop = new ArrayList<ITrimCurve>();
		for(int j=0; j<srf.outerTrimNum(i); j++) outLoop.add(srf.outerTrim(i,j).get());
		trimLoops.add(outLoop);
		loopType.add(BrepLoop.Type.Outer);
	    }
	    
	    for(int i=0; i<srf.innerTrimLoopNum(); i++){
		ArrayList<ITrimCurve> inLoop = new ArrayList<ITrimCurve>();
		for(int j=0; j<srf.innerTrimNum(i); j++) inLoop.add(srf.innerTrim(i,j).get());
		trimLoops.add(inLoop);
		loopType.add(BrepLoop.Type.Inner);
	    }
	    
	    
	    // bbox
	    //if(!srf.hasTrim()) for(int i=0; i<srf.unum(); i++) for(int j=0; j<srf.vnum(); j++) ibbox.compare(srf.cp(i,j));
	    
	    
	    // check direction
	    checkTrimLoopDirection(trimLoops, loopType);
	    
	    for(int i=0; i<trimLoops.size(); i++){
		BrepLoop loop =
		    new BrepLoop(loops.size(),faces.size()-1,loopType.get(i), this);
		loops.add(loop);
		face.addLoopIndex(loops.size()-1);
		BrepVertex vtx0 = null;
		for(int j=0; j<trimLoops.get(i).size(); j++){
		    ITrimCurve trim2d = trimLoops.get(i).get(j);
		    NurbsCurve ncrv = new NurbsCurve(trim2d);
		    curves2.add(ncrv);
		    ICurveGeo trim3d = trim2d.get3d();
		    
		    //icurves3.add(trim3d);
		    icurves3.get(icurves3.size()-1).add(trim3d);
		    
		    
		    NurbsCurve ncrv3 = new NurbsCurve(trim3d);
		    curves3.add(ncrv3);
		    
		    // update bounding box; only approximation
		    for(int k=0; k<trim3d.num(); k++) ibbox.compare(trim3d.cp(k).get());
		    
		    BrepVertex vtx1 = null;
		    if(j==0){
			vtx1 = new BrepVertex(vertices.size(),trim3d.start());
			vertices.add(vtx1);
			vtx0 = vtx1; // start point of loop
		    }
		    else{
			vtx1 = vertices.get(vertices.size()-1);
		    }
		    BrepVertex vtx2 = null;
		    if(j < trimLoops.get(i).size()-1){
			vtx2 = new BrepVertex(vertices.size(),trim3d.end());
			vertices.add(vtx2);
		    }
		    else{ // last one or closed one
			vtx2 = vtx0;
		    }
		    
		    Interval c3domain = ncrv3.domain();
		    BrepEdge edge = new BrepEdge (edges.size(), curves3.indexOf(ncrv3), /*curves3.size()-1,*/
						  vtx1.vertexIndex, vtx2.vertexIndex,
						  c3domain, c3domain, this);
		    edges.add(edge);
		    
		    Interval c2domain = ncrv.domain();
		    BrepTrim trim =
			new BrepTrim(trims.size(),
				     curves2.size()-1,
				     edges.indexOf(edge), /*edges.size()-1,*/
				     vtx1.vertexIndex, vtx2.vertexIndex, loops.size()-1,
				     c2domain, c2domain, this, trim2d);
		    trims.add(trim);
		    loop.addTrimIndex(trims.size()-1);
		    
		    edge.addTrimIndex(trims.size()-1);
		    
		    vtx1.addEdgeIndex(edges.size()-1);
		    vtx2.addEdgeIndex(edges.size()-1);
		}
	    }
	}
	
	
	static public void checkTrimLoopDirection(ArrayList<ArrayList<ITrimCurve>> trimLoops,
						  ArrayList<BrepLoop.Type> loopType){
	    for(int i=0; i<trimLoops.size(); i++){
		ITrimCurve[] loop =
		    trimLoops.get(i).toArray(new ITrimCurve[trimLoops.get(i).size()]);
		ITrimLoopGraphic loopGr =
		    new ITrimLoopGraphic(loop, loopType.get(i)==BrepLoop.Type.Outer, 1);
		if(loopGr.reversed()){
		    ArrayList<ITrimCurve> revLoop = new ArrayList<ITrimCurve>();
		    for(int j=0; j<loop.length; j++) revLoop.add(loop[loop.length-1-j].rev());
		    trimLoops.set( i, revLoop);
		}
	    }
	}
	
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    if(majorVersion == 2){
		readOld200(context, is);
		
	    }
	    else if(majorVersion == 3){
		int debugLv=100;
		
		// read curves2
		IOut.debug(debugLv, "read curves2"); 
		curves2 = new CurveArray();
		curves2.read(context,is);
		int curves2Count = curves2.size();
		IOut.debug(debugLv,"curves2Count = "+curves2Count); 
		
		// read curves
		IOut.debug(debugLv,"read curves3"); 
		curves3 = new CurveArray();
		curves3.read(context,is);
		int curves3Count = curves3.size();
		IOut.debug(debugLv,"curves3Count = "+curves3Count); 
		
		
		// read surfaces
		IOut.debug(debugLv,"read surface"); 
		surfaces = new SurfaceArray();
		surfaces.read(context,is);
		int surfacesCount = surfaces.size();
		IOut.debug(debugLv,"surfacesCount = "+surfacesCount);
		
		
		// read vertices
		IOut.debug(debugLv,"read vertices"); 
		vertices = new BrepVertexArray();
		vertices.read(context,is);
		IOut.debug(debugLv,"verticesCount = "+vertices.size());
		
		// read edges
		IOut.debug(debugLv,"read edges"); 
		edges = new BrepEdgeArray();
		edges.read(context,is);
		IOut.debug(debugLv,"edgeCount = "+edges.size());
		
		for(int i=0; i<edges.size(); i++){
		    BrepEdge e = edges.get(i);
		    e.brep = this;
		    if(e.curve3Index >= 0 && e.curve3Index < curves3Count){
			boolean proxyCurveIsReversed = e.proxyCurveIsReversed();
			Interval pdom = e.proxyCurveDomain();
			Interval edom = e.domain();
			e.setProxyCurve(curves3.get(e.curve3Index),pdom);
			if(proxyCurveIsReversed) ((CurveProxy)e).reverse();
			e.setDomain(edom);
		    }
		}
		
		// read trims
		IOut.debug(debugLv, "read trims"); 
		trims = new BrepTrimArray();
		trims.read(context,is);
		IOut.debug(debugLv, "trimCount = "+trims.size());
		
		for(int i=0; i<trims.size(); i++){
		    BrepTrim trim = trims.get(i);
		    trim.brep = this;
		    if(trim.curve2Index >= 0 && trim.curve2Index<curves2Count){
			boolean proxyCurveIsReversed = trim.proxyCurveIsReversed();
			Interval pdom = trim.proxyCurveDomain();
			Interval tdom = trim.domain();
			trim.setProxyCurve(curves2.get(trim.curve2Index), pdom);
			if(proxyCurveIsReversed) ((CurveProxy)trim).reverse();
			trim.setDomain(tdom);
		    }
		}
		
		// read loops
		IOut.debug(debugLv,"read loops"); 
		loops = new BrepLoopArray();
		loops.read(context,is);
		IOut.debug(debugLv,"loopCount="+loops.size()); 
		for(int i=0; i<loops.size(); i++){
		    loops.get(i).brep = this;
		}
		
		// read faces
		IOut.debug(debugLv,"read faces"); 
		faces = new BrepFaceArray();
		faces.read(context,is);
		IOut.debug(debugLv,"facesCount="+faces.size()); 
		for(int i=0; i<faces.size(); i++){
		    BrepFace face = faces.get(i);
		    face.brep = this;
		    if(face.surfaceIndex>=0 && face.surfaceIndex<surfacesCount){
			face.setProxySurface(surfaces.get(face.surfaceIndex));
		    }
		}
		
		// bounding box
		//IOut.debug(debugLv,"read boundingbox"); 
		bbox = new BoundingBox();
		bbox.min = readPoint3(is);
		bbox.max = readPoint3(is);
		//IOut.p(debugLv,"boundingbox = "+bbox.min+", "+bbox.max); 
		
		// read fill in missing boxes
		
		// read render mesh, etc
		
	    }
	    
	}
	
	public void readOld200(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    int faceCount = readInt(is);
	    int edgCount = readInt(is);
	    int loopCount = readInt(is);
	    int trimCount = readInt(is);
	    
	    int outerFlag = readInt(is);
	    
	    bbox.min = readPoint3(is);
	    bbox.max = readPoint3(is);
	    
	    // 2d curves
	    curves2 = new CurveArray(trimCount);
	    for(int i=0; i<trimCount; i++){
		PolyCurve curve = new PolyCurve();
		curve.read(context,is);
	    }
	}
	
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    
	    //writeChunkVersion(os,3,0,crc); // version 3.0 is standard
	    //writeChunkVersion(os,3,1,crc); // version 3.1 includes rendering meshes
	    writeChunkVersion(os,3,2,crc); // version 3.2 includes isSolid
	    
	    curves2.write(context,os,crc);
	    curves3.write(context,os,crc);
	    surfaces.write(context,os,crc);
	    vertices.write(context,os,crc);
	    edges.write(context,os,crc);
	    trims.write(context,os,crc);
	    loops.write(context,os,crc);
	    faces.write(context,os,crc);
	    writePoint(os,bbox.min,crc);
	    writePoint(os,bbox.max,crc);
	    
	    // data for version 3.1
	    // render mesh
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    byte b = 0; // skipped
	    for(int i=0; i<faces.size(); i++) writeByte(cos, b, cos.getCRC());
	    writeChunk(os, cos.getChunk());
	    
	    // analysis mesh
	    cos = new ChunkOutputStream(tcodeAnonymousChunk);
	    b = 0; // skipped
	    for(int i=0; i<faces.size(); i++) writeByte(cos, b, cos.getCRC());
	    writeChunk(os, cos.getChunk());
	    
	    // data for version 3.2 
	    writeInt(os, isSolid, crc);
	    
	}
	
	
	public BrepLoopArray getLoopsForFace(int faceIndex){
	    BrepLoopArray ret = new BrepLoopArray();
	    for(int i=0; i<loops.size(); i++){
		if(loops.get(i).faceIndex == faceIndex) ret.add(loops.get(i));
	    }
	    return ret;
	}
	
	public BrepTrimArray getTrimsForLoop(BrepLoop loop){
	    BrepTrimArray ret = new BrepTrimArray(); 
	    for(int i=0; i<loop.trimIndex.size(); i++){
		int trimIdx = loop.trimIndex.get(i);
		if(trimIdx>=0 && trimIdx<trims.size()) ret.add(trims.get(trimIdx));
		else IOut.err("trimIndex is out of range: "+trimIdx);
	    }
	    return ret;
	}
	
	
	public CurveArray getCurves2ForLoop(BrepLoop loop){
	    BrepTrimArray tr = getTrimsForLoop(loop);
	    CurveArray crv = new CurveArray();
	    for(int i=0; i<tr.size(); i++){
		// how about domain and reverse info?
		int crv2Idx = tr.get(i).curve2Index;
		if(crv2Idx >= 0 && crv2Idx < curves2.size()){
		    crv.add(curves2.get(crv2Idx));
		}
		else{
		    IOut.err("curve2Index is out of range: "+crv2Idx);
		}
	    }
	    return crv;
	}
	
	public ArrayList<ITrimCurve> getTrimCurvesForLoop(BrepLoop loop,
							  Rhino3dmFile context, IServerI s,
							  ISurfaceI srf){
	    CurveArray crv2 = getCurves2ForLoop(loop);
	    ArrayList<ITrimCurve> trims = new ArrayList<ITrimCurve>();
	    for(int i=0; i<crv2.size(); i++){
		ITrimCurve tr = crv2.get(i).createTrimCurve(context,s,srf);
		if(tr!=null){
		    //IOut.p("trim curve cp:");
		    //for(int j=0; j<tr.num(); j++) IOut.p("["+j+"]:"+tr.cp(j));
		    tr.normalizeControlPoints(srf); // important
		    trims.add(tr);
		}
		else{ IOut.err("failed to create trim curve "); }
	    }
	    return trims;
	}
	
	/*
	public BrepTrimArray getTrimsForFace(int faceIndex){
	    BrepLoopArray l = getLoopsForFace(faceIndex);
	    BrepTrimArray ret = new BrepTrimArray(); 
	    for(int i=0; i<l.size(); i++){
		for(int j=0; j<l.get(i).trimIndex.size(); j++){
		    int trimIdx = l.get(i).trimIndex.get(j);
		    if(trimIdx>=0 && trimIdx<trims.size()) ret.add(trims.get(trimIdx));
		    else IOut.err("trimIndex is out of range: "+trimIdx);
		}
	    }
	    return ret;
	}
	public CurveArray getCurves2ForFace(int faceIndex){
	    BrepTrimArray tr = getTrimsForFace(faceIndex);
	    CurveArray crv = new CurveArray();
	    for(int i=0; i<tr.size(); i++){
		// how about domain and reverse info?
		int crv2Idx = tr.get(i).curve2Index;
		if(crv2Idx >= 0 && crv2Idx < curves2.size()) crv.add(curves2.get(crv2Idx));
		else IOut.err("curve2Index is out of range: "+crv2Idx);
	    }
	    return crv;
	}
	*/
	
	
	public IObject createIObject(Rhino3dmFile context, IServerI s){
	    //CurveArray curves2;
	    //CurveArray curves3;
	    //SurfaceArray surfaces;
	    //BrepVertexArray vertices;
	    //BrepEdgeArray edges;
	    //BrepTrimArray trims;
	    //BrepLoopArray loops;
	    //BrepFaceArray faces;
	    
	    //ArrayList<ISurface> isurfaces = new ArrayList<ISurface>();
	    ArrayList<ISurfaceGeo> isurfgeo = new ArrayList<ISurfaceGeo>();
	    
	    for(int i=0; i<faces.size(); i++){
		BrepFace face = faces.get(i);
		
		//if(face.faceIndex>=0 && face.faceIndex<surfaces.size()){
		if(face.surfaceIndex>=0 && face.surfaceIndex<surfaces.size()){
		    IOut.debug(100, "creating surface "+i+"/"+faces.size()); //
		    //ISurfaceGeo surf = surfaces.get(face.faceIndex).createIGeometry(context,s);
		    ISurfaceGeo surf = surfaces.get(face.surfaceIndex).createIGeometry(context,s);
		    if(surf != null){
			BrepLoopArray faceLoop = getLoopsForFace(face.faceIndex);
			for(int j=0; j<faceLoop.size(); j++){
			    ArrayList<ITrimCurve> trimCrvs =
				getTrimCurvesForLoop(faceLoop.get(j),context,s,surf);
			    if(trimCrvs.size()>0){
				IOut.debug(100, trimCrvs.size()+" trim curves found"); //
				switch(faceLoop.get(j).type){
				case Inner:
				    IOut.debug(100, "adding inner trim loop"); //
				    surf.addInnerTrimLoop(trimCrvs.toArray(new ITrimCurve[trimCrvs.size()]));
				    break;
				case Outer:
				    IOut.debug(100, "adding outer trim loop"); //
				    surf.addOuterTrimLoop(trimCrvs.toArray(new ITrimCurve[trimCrvs.size()]));
				    break;
				default:
				    IOut.err("trim loop type is invalid : "+faceLoop.get(j).type);
				}
			    }
			    else{
				IOut.err("no trim found for BrepLoop: BrepLoop.Type = "+
					 faceLoop.get(j).type);
			    }
			}
			
			//ISurface isurf = new ISurface(s,surf);
			//isurfaces.add(isurf);
			
			//setAttributesToIObject(context, isurf);
			
			isurfgeo.add(surf);
		    }
		    else{
			//IOut.err("failed to instantiate: @ "+surfaces.get(face.faceIndex));
			IOut.err("failed to instantiate: @ "+surfaces.get(face.surfaceIndex));
		    }
		}
		else{
		    IOut.err("BrepFace surfaceIndex("+face.surfaceIndex+") of face ("+face.faceIndex+
			     ") is out of range(" +surfaces.size()+")" );
		}
		
	    }
	    
	    //if(isurfaces.size()==0) return null;
	    if(isurfgeo.size()==0) return null;
	    
	    // if num of surf is just 1, instantiate as ISurface
	    if(isurfgeo.size()==1){ return new ISurface(s, isurfgeo.get(0)); }
	    
	    // if num of surf more than 1, instantiate as IBrep
	    
	    //IBrep ibrep = new IBrep(s);
	    //for(int i=0; i<isurfgeo.size(); i++) ibrep.add(isurfgeo.get(i));
	    
	    // how about seams, vertices or etc?
	    //return new IBrep(s, isurfgeo.toArray(new ISurfaceGeo[isurfgeo.size()]),null,null);
	    return new IBrep(s, isurfgeo.toArray(new ISurfaceGeo[isurfgeo.size()]));
	}
	
    }
    public static class BrepRegionTopologyUserData extends UserData{
	public static final String uuid = "7FE23D63-E536-43f1-98E2-C807A2625AFF";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnkown; }
    }
    public static class BrepFaceSide extends RhinoObject{
	public static final String uuid = "30930370-0D5B-4ee4-8083-BD635C7398A4";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnkown; }
    }
    public static class BrepRegion extends RhinoObject{
	public static final String uuid = "CA7A0092-7EE6-4f99-B9D2-E1D6AA798AA1";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnkown; }
    }
    
    public static class CurveOnSurface extends Curve{
	public static final String uuid = "4ED7D4D8-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeCurve; }
	
	public Curve curve2;
	public Curve curve3;
	public Surface surface;
	
	public boolean isValid(){
	    if(curve2==null) return false;
	    if(surface==null) return false;
	    if(!curve2.isValid()) return false;
	    //if(curve2.dimension()!=2) return false;
	    //if(!surface.isValid()) return false;
	    if(curve3!=null){
		if(!curve3.isValid()) return false;
		//if(!curve3.dimension()!=surface.dimension()) return false;
	    }
	    return true;
	}
	public Interval domain(){ return curve2.domain(); }
    }
    public static class DetailView extends Geometry{
	public static final String uuid = "C8C66EFA-B3CB-4e00-9440-2AD66203379E";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeDetail; }
    }
    public static class DimStyleExtra extends UserData{
	public static final String uuid = "513FDE53-7284-4065-8601-06CEA8B28D6F";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnkown; }
    }
    public static class HatchExtra extends UserData{
	public static final String uuid = "3FF7007C-3D04-463f-84E3-132ACEB91062";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnkown; }
    }
    public static class Hatch extends Geometry{
	public static final String uuid = "0559733B-5332-49d1-A936-0532AC76ADE5";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeHatch; }
    }
    public static class InstanceRef extends Geometry{
	public static final String uuid = "F9CFB638-B9D4-4340-87E3-C56E7865D96A";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeInstanceReference; }
    }
    public static class LayerExtensions extends UserData{
	public static final String uuid = "3E4904E6-E930-4fbc-AA42-EBD407AEFE3B";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnkown; }
    }
    public static class CurveProxy extends Curve{
	public static final String uuid = "4ED7D4D9-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeCurve; }
	
	public Curve realCurve;
	public boolean reversed;
	public Interval realCurveDomain;
	public Interval thisDomain;
	
	public CurveProxy(){ realCurveDomain=new Interval(); thisDomain=new Interval(); }
	
	public void setProxyCurve(Curve realCurve, Interval realCurveSubdomain){
	    if(realCurve!=this){
		realCurve = null;
		realCurveDomain = null;
		thisDomain = null;
		reversed=false;
	    }
	    else{
		if( isValid() && thisDomain.includes(realCurveSubdomain)){
		    realCurve = this.realCurve;
		    double r0 = realCurveParameter(realCurveSubdomain.v1);
		    double r1 = realCurveParameter(realCurveSubdomain.v2);
		    realCurveSubdomain.set(r0,r1);
		}
		else{
		    realCurve=null;
		}
		this.realCurve=null;
	    }
	    
	    if(this.realCurve!=null){
		setProxyCurveDomain(realCurveSubdomain);
	    }
	    else{
		this.realCurveDomain = realCurveSubdomain;
	    }
	    this.thisDomain = this.realCurveDomain;
	}
	
	public boolean setProxyCurveDomain(Interval proxyCurveSubdomain){
	    if(!proxyCurveSubdomain.isIncreasing()) return false;
	    if(realCurve!=null){
		Interval cdom = realCurve.domain();
		cdom.intersection(proxyCurveSubdomain);
		if(cdom.isIncreasing()) realCurveDomain = cdom;
		//else{ IOut.err("domain is decreasing"); } //
	    }
	    else{
		realCurveDomain = proxyCurveSubdomain;
	    }
	    return true;
	}
	
	public Interval proxyCurveDomain(){ return realCurveDomain; }
	
	public double realCurveParameter(double t){
	    if(reversed || realCurveDomain.equals(thisDomain)){
		double s = thisDomain.normalizedParameterAt(t);
		if(reversed) s = 1.0-s;
		t = realCurveDomain.parameterAt(s);
	    }
	    return t;
	}
	
	public void setDomain(Interval domain){
	    if(domain.isIncreasing()) thisDomain.set(domain);
	    //else{ IOut.err("domain is decreasing"); //
	}
	
	public Interval domain(){ return thisDomain; }
	
	public boolean reverse(){
	    if(thisDomain.isIncreasing()){
		reversed = !reversed;
		thisDomain.reverse();
	    }
	    return true;
	}
	
	public boolean proxyCurveIsReversed(){ return reversed; }
	
	public boolean isValid(){
	    if(realCurve==null) return false;
	    if(!realCurve.isValid()) return false;
	    
	    if(!realCurveDomain.isIncreasing()) return false;
	    if(!realCurve.domain().includes(realCurveDomain)) return false;
	    if(!thisDomain.isIncreasing()) return false;
	    
	    return false;
	}
	
	
    }

    public static class Line{
	public IVec from, to;
	public double length(){ return from.dist(to); }
	public String toString(){ return "< "+from.toString()+", "+to.toString()+" >"; }
    }
    public static class LineCurve extends Curve{
	public static final String uuid = "4ED7D4DB-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeCurve; }
	
	public Line line;
	public Interval t;
	public int dim;
	public Interval domain(){ return t; }
	public boolean isValid(){
	    return t.isIncreasing() && line.length()>0;
	}
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    if(majorVersion==1){
		line = readLine(is);
		t = readInterval(is);
		dim = readInt32(is);

		IOut.debug(100,"line = "+line.from+", "+line.to);
		//IOut.p("line = "+line.from+", "+line.to);
		//IOut.p("interval = "+t.v1+", "+t.v2);
		//IOut.p("dim = "+dim);
	    }
	    else{
		IOut.err("wrong chunk major version :"+majorVersion);
	    }
	    
	}
	
	public ICurve createIObject(Rhino3dmFile context, IServerI s){
	    if(line==null) return null;
	    ICurve crv = new ICurve(s,line.from, line.to);
	    //setAttributesToIObject(context,crv);
	    return crv;
	}
	
	public ICurveGeo createIGeometry(Rhino3dmFile context, IServerI s){
	    if(line==null) return null;
	    return new ICurveGeo(line.from, line.to);
	}
	
	public ITrimCurve createTrimCurve(Rhino3dmFile context, IServerI s, ISurfaceI srf){
	    if(line==null) return null;
	    //return new ITrimCurve(srf, line.from, line.to);
	    return new ITrimCurve(line.from, line.to);
	}
	
    }
    
    public static class Mesh extends Geometry{
	public static final String uuid = "4ED7D4E4-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeMesh; }
	
	public ArrayList<IVec> vertices;
	public ArrayList<MeshFace> faces;
	public ArrayList<IVec> normals;
	public ArrayList<IVec> unitNormals;
	public MappingTag ttag;
	public ArrayList<IVec2> texture;
	public ArrayList<TextureCoordinates> textureCoordinates;
	public ArrayList<IVec2> surfaceParameter;
	public Interval[] surfaceDomain;
	public double[] surfaceScale;
	public Interval[] packedTextureDomain;
	public boolean packedTextureRotate;
	public ArrayList<SurfaceCurvature> surfaceCurvature;
	public MappingTag ctag;
	public ArrayList<IColor> color;
	public ArrayList<Boolean> hide;
	public int hiddenCount;
	public RhinoObject parent;
	//public MeshTopology topology;
	public MeshParameters meshParameters;
	public int invalidCount;
	public int quadCount;
	public int triangleCount;
	public int closed; // -1: unknown, 0:not closed, 1:closed, 2:closed with duplicated vertices
	public float[][] vbox;
	public float[][] nbox;
	public float[][] tbox;
	public MeshCurvatureStats[] curvatureStat;
	//public MeshPartition partition;
	//public MeshTree mtree;

	public Mesh(){}

	public Mesh(IMeshI mesh){
	    
	    ArrayList<IVertex> vtx = new ArrayList<IVertex>();
	    
	    vertices = new ArrayList<IVec>();
	    normals = new ArrayList<IVec>();
	    texture = new ArrayList<IVec2>();
	    color = new ArrayList<IColor>();
	    surfaceCurvature = new ArrayList<SurfaceCurvature>();
	    
	    for(int i=0; i<mesh.vertexNum(); i++){
		IVertex v = mesh.vertex(i);
		v.index = i;
		vtx.add(v);
		vertices.add(v.get());
		if(v.normal!=null){ normals.add(v.normal.get()); }// not calc a new normal
		else{ normals.add(v.getAverageNormal()); } //use average as default; added 20120725
		if(v.texture!=null) texture.add(v.texture.get());
		else{ texture.add(new IVec2(0,0)); } //default value; added 20120725
	    }

	    faces = new ArrayList<MeshFace>();
	    for(int i=0; i<mesh.faceNum(); i++){
		IFace f = mesh.face(i);
		if(f.vertexNum()==3){
		    //int vi1 = vtx.indexOf(f.vertex(0));
		    //int vi2 = vtx.indexOf(f.vertex(1));
		    //int vi3 = vtx.indexOf(f.vertex(2));
		    int vi1 = f.vertex(0).index;
		    int vi2 = f.vertex(1).index;
		    int vi3 = f.vertex(2).index;
		    if(vi1>=0 && vi2>=0 && vi3>=0) faces.add(new MeshFace(vi1,vi2,vi3));
		    else{
			IOut.err("vertex of the face is missing int the vertices array");
		    }
		}
		else if(f.vertexNum()==4){
		    //int vi1 = vtx.indexOf(f.vertex(0));
		    //int vi2 = vtx.indexOf(f.vertex(1));
		    //int vi3 = vtx.indexOf(f.vertex(2));
		    //int vi4 = vtx.indexOf(f.vertex(3));
		    int vi1 = f.vertex(0).index;
		    int vi2 = f.vertex(1).index;
		    int vi3 = f.vertex(2).index;
		    int vi4 = f.vertex(3).index;
		    if(vi1>=0 && vi2>=0 && vi3>=0 && vi4>=0) faces.add(new MeshFace(vi1,vi2,vi3,vi4));
		    else{
			IOut.err("vertex of the face is missing int the vertices array");
		    }
		}
		else if(f.vertexNum()>4){ // divide
		    //int vi1 = vtx.indexOf(f.vertex(0));
		    //int vi2 = vtx.indexOf(f.vertex(1));
		    int vi1 = f.vertex(0).index;
		    int vi2 = f.vertex(1).index;
		    if(vi1>=0&&vi2>=0){
			for(int j=2; j<f.vertexNum(); j+=2){
			    if(j<f.vertexNum()-1){
				//int vi3 = vtx.indexOf(f.vertex(j));
				//int vi4 = vtx.indexOf(f.vertex(j+1));
				int vi3 = f.vertex(j).index;
				int vi4 = f.vertex(j+1).index;
				if(vi3>=0&&vi4>=0) faces.add(new MeshFace(vi1,vi2,vi3,vi4));
				else{
				    IOut.err("vertex of the face is missing int the vertices array");
				}
				vi2 = vi4;
			    }
			    else{
				//int vi3 = vtx.indexOf(f.vertex(j));
				int vi3 = f.vertex(j).index;
				if(vi3>=0) faces.add(new MeshFace(vi1,vi2,vi3));
				else{
				    IOut.err("vertex of the face is missing int the vertices array");
				}
			    }
			    
			}
		    }
		    else{
			IOut.err("vertex of the face is missing int the vertices array");
		    }
		}
	    }
	    
	    surfaceDomain = new Interval[2];
	    surfaceDomain[0] = new Interval(0.,1.);
	    surfaceDomain[1] = new Interval(0.,1.);
	    surfaceScale = new double[2];
	    surfaceScale[0] = 0.;
	    surfaceScale[1] = 0.;
	    packedTextureDomain = new Interval[2];
	    packedTextureDomain[0] = new Interval(0.,1.);
	    packedTextureDomain[1] = new Interval(0.,1.);
	    packedTextureRotate = false;
	    
	    closed = mesh.isClosed()?1:0;
	    
	    vbox = new float[2][3];
	    nbox = new float[2][3];
	    tbox = new float[2][2];
	    for(int i=0; i<vtx.size(); i++){
		if(i==0){
		    IVec v = vtx.get(i).get();
		    
		    vbox[0][0] = vbox[1][0] = (float)v.x;
		    vbox[0][1] = vbox[1][1] = (float)v.y;
		    vbox[0][2] = vbox[1][2] = (float)v.z;
		    
		    if(vtx.get(i).normal!=null){
			IVec n = vtx.get(i).normal.get();
			nbox[0][0]=nbox[1][0]=(float)n.x;
			nbox[0][1]=nbox[1][1]=(float)n.y;
			nbox[0][2]=nbox[1][2]=(float)n.z;
		    }
		    else{
			nbox[0][0]=nbox[1][0]=0f;
			nbox[0][1]=nbox[1][1]=0f;
			nbox[0][2]=nbox[1][2]=0f;
		    }
		    
		    if(vtx.get(i).texture!=null){
			IVec2 t = vtx.get(i).texture.get();
			tbox[0][0]=tbox[1][0]=(float)t.x;
			tbox[0][1]=tbox[1][1]=(float)t.y;
		    }
		    else{
			tbox[0][0]=tbox[1][0]=0f;
			tbox[0][1]=tbox[1][1]=0f;
		    }
		}
		else{
		    IVec v = vtx.get(i).get();
		    
		    if(v.x < vbox[0][0]) vbox[0][0]=(float)v.x;
		    else if(v.x > vbox[1][0]) vbox[1][0]=(float)v.x;
		    if(v.y < vbox[0][1]) vbox[0][1]=(float)v.y;
		    else if(v.y > vbox[1][1]) vbox[1][1]=(float)v.y;
		    if(v.z < vbox[0][2]) vbox[0][2]=(float)v.z;
		    else if(v.z > vbox[1][2]) vbox[1][2]=(float)v.z;
		    
		    if(vtx.get(i).normal!=null){
			IVec n = vtx.get(i).normal.get();
			
			if(n.x < nbox[0][0]) nbox[0][0]=(float)n.x;
			else if(n.x > nbox[1][0]) nbox[1][0]=(float)n.x;
			if(n.y < nbox[0][1]) nbox[0][1]=(float)n.y;
			else if(n.y > nbox[1][1]) nbox[1][1]=(float)n.y;
			if(n.z < nbox[0][2]) nbox[0][2]=(float)n.z;
			else if(n.z > nbox[1][2]) nbox[1][2]=(float)n.z;
		    }
		    
		    if(vtx.get(i).texture!=null){
			IVec2 t = vtx.get(i).texture.get();
			
			if(t.x < tbox[0][0]) tbox[0][0]=(float)t.x;
			else if(t.x > tbox[1][0]) tbox[1][0]=(float)t.x;
			if(t.y < tbox[0][1]) tbox[0][1]=(float)t.y;
			else if(t.y > tbox[1][1]) tbox[1][1]=(float)t.y;
		    }
		}
	    }
	}
	
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    //IOut.err("reading start"); 
	    
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];

	    if(majorVersion==1 || majorVersion==3){
		int vcount = readInt(is);
		int fcount = readInt(is);
		
		packedTextureDomain = new Interval[2];
		packedTextureDomain[0] = readInterval(is);
		packedTextureDomain[1] = readInterval(is);
		surfaceDomain = new Interval[2];
		surfaceDomain[0] = readInterval(is);
		surfaceDomain[1] = readInterval(is);
		surfaceScale = new double[2];
		surfaceScale[0] = readDouble(is);
		surfaceScale[1] = readDouble(is);
		
		vbox = new float[2][3];
		for(int i=0; i<vbox.length; i++)
		    for(int j=0; j<vbox[i].length; j++) vbox[i][j] = readFloat(is);
		nbox = new float[2][3];
		for(int i=0; i<nbox.length; i++)
		    for(int j=0; j<nbox[i].length; j++) nbox[i][j] = readFloat(is);
		tbox = new float[2][2];
		for(int i=0; i<tbox.length; i++)
		    for(int j=0; j<tbox[i].length; j++) tbox[i][j] = readFloat(is);
		
		closed = readInt(is);
		
		byte b=0;
		b = readByte(is);
		
		if(b>0){
		    Chunk chunk = readChunk(is);
		    if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
		    ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
		    
		    meshParameters = new MeshParameters();
		    meshParameters.read(context,bais);
		}
		
		curvatureStat = new MeshCurvatureStats[4];
		for(int i=0; i<4; i++){
		    
		    b = readByte(is);
		    if(b>0){
			Chunk chunk = readChunk(is);
			if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));
			ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
			curvatureStat[i] = new MeshCurvatureStats();
			curvatureStat[i].read(context,bais);
		    }
		}
		
		readFaceArray(context, is, vcount, fcount);
		
		if(majorVersion==1){ read1(context, is); }
		else if(majorVersion==3){ read2(context, is, vcount); }
		
		
		if(minorVersion>=2){
		    int i = readInt(is);
		    packedTextureRotate = (i!=0)?true:false;
		}
		
		if(majorVersion == 3){
		    if(minorVersion >=3){
			
			ttag = new MappingTag();
			ttag.mappingId = readUUID(is);
			
			if(vcount>0){
			    
			    int sz = readCompressedBufferSize(is);
			    int ptsz = 2*8; // 2 dim doulbe points

			    if(sz > 0){ // added 20120203; sometimes when vcount>0, sz is 0
				if(sz != ptsz*vcount){
				    throw new IOException("buffer size ("+sz+") doesn't match with vcount("+vcount+")*surface_parameter_data_size("+ptsz+")");
				}
				
				surfaceParameter =
				    readArrayPoint2(readCompressedBuffer(is, sz), vcount);
				
				if(endian()==Endian.BigEndian){
				    // flip order?
				}
				
				if(minorVersion>=4 && context.openNurbsVersion >= 200606010){
				    ttag.read(context, is);
				}
			    }
			}
		    }
		}
		
		
		
		if(surfaceParameter==null &&
		   vertices!=null && vertices.size()>0){
		    
		    // calculating surfaceParameter for old file
		    // ... ignored.
		    
		}
		
		
	    }
	    
	    /*
	    IOut.err("reading end");
	    
	    IOut.err("vertices.size()="+vertices.size()); //
	    for(int i=0; i<vertices.size(); i++){
		IOut.err("vertices.get("+i+")="+vertices.get(i)); //
	    }
	    IOut.err("faces.size()="+faces.size()); //
	    for(int i=0; i<faces.size(); i++){
		IOut.err("faces.get("+i+")="+faces.get(i)); //
	    }
	    */
	}
	
	public void read1(Rhino3dmFile context, InputStream is)throws IOException{
	    //IOut.err(); //?
	    
	    vertices = readArrayPoint3f(is);
	    normals = readArrayPoint3f(is);
	    texture = readArrayPoint2f(is);
	    surfaceCurvature = readArraySurfaceCurvature(is);
	    color = readArrayColor(is);
	}
	
	public void read2(Rhino3dmFile context, InputStream is, int vcount)throws IOException{
	    //IOut.err();
	    
	    Endian e = endian();
	    
	    if(vcount>0){
		int failedCRC;
		
		int ptsz = 3*4; // 3 dim float points
		
		int sz = readCompressedBufferSize(is);
		
		if(sz>0){
		    if(sz!=vcount*ptsz) throw new IOException("buffer size ("+sz+") doesn't match with vcount("+vcount+")*vertex_data_size("+ptsz+")");
		    
		    vertices = readArrayPoint3f(readCompressedBuffer(is, sz), vcount);
		}
		
		sz = readCompressedBufferSize(is);
		if(sz>0){
		    if(sz!=vcount*ptsz) throw new IOException("buffer size ("+sz+") doesn't match with vcount("+vcount+")*normal_data_size("+ptsz+")");
		    
		    normals = readArrayPoint3f(readCompressedBuffer(is, sz), vcount);
		}
		
		ptsz = 2*4; // 2 dim float points
		sz = readCompressedBufferSize(is);
		if(sz>0){
		    if(sz!=vcount*ptsz) throw new IOException("buffer size ("+sz+") doesn't match with vcount("+vcount+")*texture_data_size("+ptsz+")");
		    
		    texture = readArrayPoint2f(readCompressedBuffer(is, sz), vcount);
		}
		
		ptsz = 2*8; // 2 double 
		sz = readCompressedBufferSize(is);
		if(sz>0){
		    if(sz!=vcount*ptsz) throw new IOException("buffer size ("+sz+") doesn't match with vcount("+vcount+")*curvature_data_size("+ptsz+")");
		    
		    surfaceCurvature = readArraySurfaceCurvature(readCompressedBuffer(is,sz),vcount);
		}
		
		ptsz = 4; // 32bit color
		sz = readCompressedBufferSize(is);
		if(sz>0){
		    if(sz!=vcount*ptsz) throw new IOException("buffer size ("+sz+") doesn't match with vcount("+vcount+")*color_data_size("+ptsz+")");
		    
		    color = readArrayColor(readCompressedBuffer(is, sz), vcount);
		}
		
		if(e==Endian.BigEndian){
		    // ... necessary?
		}
		
	    }
	    
	}
	
	public void readFaceArray(Rhino3dmFile context, InputStream is, int vcount, int fcount) throws IOException{
	    faces = new ArrayList<MeshFace>(fcount);
	    
	    int isize = 0;
	    isize = readInt(is);
	    
	    switch(isize){
	    case 1:
		byte[] cvi = new byte[4];
		for(int i=0; i<fcount; i++){
		    cvi[0] = readByte(is); cvi[1] = readByte(is);
		    cvi[2] = readByte(is); cvi[3] = readByte(is);
		    //faces.add(new MeshFace((int)cvi[0],(int)cvi[1],(int)cvi[2],(int)cvi[3]));
		    faces.add(new MeshFace(cvi[0]&0xFF, cvi[1]&0xFF, cvi[2]&0xFF, cvi[3]&0xFF));
		}
		break;
	    case 2:
		short[] svi = new short[4];
		for(int i=0; i<fcount; i++){
		    svi[0] = readShort(is); svi[1] = readShort(is);
		    svi[2] = readShort(is); svi[3] = readShort(is);
		    //faces.add(new MeshFace((int)svi[0],(int)svi[1],(int)svi[2],(int)svi[3]));
		    faces.add(new MeshFace(svi[0]&0xFFFF,svi[1]&0xFFFF,svi[2]&0xFFFF,svi[3]&0xFFFF));
		}
		break;
	    case 4:
		int[] vi = new int[4];
		for(int i=0; i<fcount; i++){
		    vi[0] = readInt(is); vi[1] = readInt(is);
		    vi[2] = readInt(is); vi[3] = readInt(is);
		    faces.add(new MeshFace(vi[0],vi[1],vi[2],vi[3]));
		}
		break;
	    }
	}
	
	
	public IMesh createIObject(Rhino3dmFile context, IServerI s){
	    return new IMesh(s,createIGeometry(context,s));
	}
	
	public IMeshGeo createIGeometry(Rhino3dmFile context, IServerI s){
	    if(vertices==null){
		IOut.err("mesh vertices is null");
		return null;
	    }
	    if(faces==null){
		IOut.err("mesh face is null");
		return null;
	    }
	    if(vertices.size()==0){
		IOut.err("no mesh vertices found");
		return null;
	    }
	    if(faces.size()==0){
		IOut.err("no mesh face found");
		return null;
	    }
	    
	    ArrayList<IVertex> ivertices = new ArrayList<IVertex>();
	    for(int i=0; i<vertices.size(); i++){
		IVertex vtx = new IVertex(vertices.get(i));
		if(normals!=null && normals.size()==vertices.size()){
		    vtx.setNormal(normals.get(i));
		}
		if(texture!=null && texture.size()==vertices.size()){
		    vtx.texture(texture.get(i));
		}
		ivertices.add(vtx);
	    }
	    
	    ArrayList<IFace> ifaces = new ArrayList<IFace>();
	    for(int i=0; i<faces.size(); i++){
		if(faces.get(i).vertexIndex!=null && faces.get(i).vertexIndex.length>=3){
		    int[] vidx = faces.get(i).vertexIndex;
		    ArrayList<Integer> vi = new ArrayList<Integer>();
		    for(int j=0; j<vidx.length; j++){
			if(j==0 ||
			   (j>0 && vidx[j]!=vi.get(vi.size()-1) &&
			    (j<vidx.length-1 || (j==vidx.length-1 && vidx[j]!=vi.get(0))))){
			    vi.add(vidx[j]);
			}
		    }
		    if(vi.size()>0){
			IVertex[] ivtx = new IVertex[vi.size()];
			for(int j=0; j<vi.size(); j++){
			    ivtx[j] = ivertices.get(vi.get(j));
			}
			ifaces.add(new IFace(ivtx));
		    }
		    else{ IOut.err("no vertices is created"); }
		}
	    }
	    
	    if(ifaces.size()==0){
		IOut.err("no face is created");
		return null;
	    }
	    
	    return new IMeshGeo(ifaces.toArray(new IFace[ifaces.size()]));
	}
	
	
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    final int majorVersion=3; // compressed
	    final int minorVersion=2; //4;
	    
	    writeChunkVersion(os, majorVersion, minorVersion, crc);
	    
	    int vcount = vertices.size();
	    int fcount = faces.size();
	    
	    writeInt(os, vcount, crc);
	    writeInt(os, fcount, crc);
	    
	    writeInterval(os, packedTextureDomain[0], crc);
	    writeInterval(os, packedTextureDomain[1], crc);
	    
	    writeInterval(os, surfaceDomain[0], crc);
	    writeInterval(os, surfaceDomain[1], crc);
	    
	    writeDouble(os, surfaceScale[0], crc);
	    writeDouble(os, surfaceScale[1], crc);
	    
	    for(int i=0; i<2; i++) for(int j=0; j<3; j++) writeFloat(os, vbox[i][j], crc);
	    for(int i=0; i<2; i++) for(int j=0; j<3; j++) writeFloat(os, nbox[i][j], crc);
	    for(int i=0; i<2; i++) for(int j=0; j<2; j++) writeFloat(os, tbox[i][j], crc);
	    
	    writeInt(os, closed, crc);
	    
	    byte b = 0;
	    //if(meshParameters!=null) b=1;
	    
	    writeByte(os, b, crc);
	    
	    /* // ignored
	    if(meshParameters!=null){
		ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
		meshParameters.write(context, cos, cos.getCrc());
		writeChunk(os, cos.getChunk());
	    }
	    */
	    
	    
	    for(int i=0; i<4; i++){
		b = 0;
		//if(curvatureStat[i]!=null) b=1;
		writeByte(os, b, crc);
		/* // ignored
		if(curvatureStat[i]!=null){
		    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
		    curvatureStat[i].write(context,cos,cos.getCRC()); 
		    writeChunk(os, cos.getChunk());
		}
		*/
	    }
	    
	    writeFaceArray(context, os, vcount, fcount, crc);
	    
	    if(majorVersion==1){
		write1(context, os, crc);
	    }
	    else if(majorVersion==3){
		write2(context, os, vcount, crc);
	    }
	    
	    // minor version 1.2 and 3.2
	    int i = packedTextureRotate?1:0;
	    writeInt(os, i, crc);
	    
	    
	    // minor version 3.3
	    // ttag.mappingId;
	    // surfaceParameter;
	    // ttaga.write;
	    // ignored...
	    
	}
	
	
	public void write1(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    writeArrayPoint3f(os, vertices, crc);
	    writeArrayPoint3f(os, normals, crc);
	    writeArrayPoint2f(os, texture, crc);
	    writeArraySurfaceCurvature(os, surfaceCurvature, crc);
	    writeArrayColor(os, color, crc);
	    
	}
	
	public void write2(Rhino3dmFile context, OutputStream os, int vcount, CRC32 crc)throws IOException{
	    Endian e = endian();
	    
	    if(vcount<=0) return;
	    
	    int ncount = normals.size()==vcount?vcount:0;
	    int tcount = texture.size()==vcount?vcount:0;
	    int kcount = surfaceCurvature.size()==vcount?vcount:0;
	    int ccount = color.size()==vcount?vcount:0;
	    
	    
	    byte[] buf = writeArrayPoint3f(vertices, null);
	    writeCompressedBuffer(os, buf, buf.length, crc);
	    
	    buf = writeArrayPoint3f(normals, null);
	    writeCompressedBuffer(os, buf, buf.length, crc);
	    
	    buf = writeArrayPoint2f(texture, null);
	    writeCompressedBuffer(os, buf, buf.length, crc);
	    
	    buf = writeArraySurfaceCurvature(surfaceCurvature, null);
	    writeCompressedBuffer(os, buf, buf.length, crc);
	    
	    buf = writeArrayColor(color, null);
	    writeCompressedBuffer(os, buf, buf.length, crc);
	    
	}
	
	
	public void writeFaceArray(Rhino3dmFile context, OutputStream os,
				   int vcount, int fcount, CRC32 crc) throws IOException{
	    
	    byte[] cvi = new byte[4];
	    short[] svi = new short[4];
	    
	    int isize = 0;
	    if(vcount<256){ isize=1; } // byte
	    else if(vcount<65536){ isize=2; } // short
	    else isize=4; // int
	    
	    writeInt(os,isize,crc);
	    
	    switch(isize){
	    case 1:
		for(int i=0; i<fcount; i++){
		    writeByte(os, (byte)faces.get(i).vertexIndex[0], crc);
		    writeByte(os, (byte)faces.get(i).vertexIndex[1], crc);
		    writeByte(os, (byte)faces.get(i).vertexIndex[2], crc);
		    writeByte(os, (byte)faces.get(i).vertexIndex[3], crc);
		}
		break;
	    case 2:
		for(int i=0; i<fcount; i++){
		    writeShort(os, (short)faces.get(i).vertexIndex[0], crc);
		    writeShort(os, (short)faces.get(i).vertexIndex[1], crc);
		    writeShort(os, (short)faces.get(i).vertexIndex[2], crc);
		    writeShort(os, (short)faces.get(i).vertexIndex[3], crc);
		}
		break;
	    case 4:
		for(int i=0; i<fcount; i++){
		    writeInt(os, faces.get(i).vertexIndex[0], crc);
		    writeInt(os, faces.get(i).vertexIndex[1], crc);
		    writeInt(os, faces.get(i).vertexIndex[2], crc);
		    writeInt(os, faces.get(i).vertexIndex[3], crc);
		}
		break;
	    }
	}
	
    }
    public static class SurfaceCurvature{
	public double k1,k2;
    }
    public static class MeshCurvatureStats{
	public enum CurvatureStyle{
	    UnknownCurvature, GaussianCurvature, MeanCurvature,
		MinCurvature, MaxCurvature
	};
	public static CurvatureStyle style(int i){
	    switch(i) {
	    case 1: return CurvatureStyle.GaussianCurvature;
	    case 2: return CurvatureStyle.MeanCurvature;
	    case 3: return CurvatureStyle.MinCurvature;
	    case 4: return CurvatureStyle.MaxCurvature;
	    }
	    return CurvatureStyle.UnknownCurvature;
	}
	
	public CurvatureStyle style;
	public double infinity;
	public int countInfinite;
	public int count;
	public double mode;
	public double average;
	public double adev;
	public Interval range;
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		int i=0;
		style= style( readInt(is) );
		infinity = readDouble(is);
		countInfinite = readInt(is);
		count = readInt(is);
		mode = readDouble(is);
		average = readDouble(is);
		adev = readDouble(is);
		range = readInterval(is);
	    }
	}
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	}
    }
    
    public static class MeshParameters{
	public boolean customSetting=false;
	public boolean computeCurvature=false;
	public boolean simplePlanes=false;
	public boolean refine = true;
	public boolean jaggedSeams = false;
	public byte reserved1 = 0;
	public byte reserved2 = 0;
	public byte mesher = 0;
	public int textureRange = 2;
	public double tolerance = 0.;
	public double relativeTolerance = 0.;
	public double minTolerance = 0.;
	public double minEdgeLength = 0.0001;
	public double maxEdgeLength = 0.;
	public double gridAspectRatio = 6.;
	public int gridMinCount = 0;
	public int gridMaxCount = 0;
	public double gridAngle = 20.*Math.PI/180;
	public double gridAmplification = 1.;
	public double refineAngle = 20.*Math.PI/180;
	public int faceType = 0;

	public static boolean readIntAsBoolean(InputStream is)throws IOException{
	    return readInt(is) != 0;
	}
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    if(majorVersion==1){
		int i=0;
		
		computeCurvature = readIntAsBoolean(is);
		simplePlanes = readIntAsBoolean(is);
		refine = readIntAsBoolean(is);
		jaggedSeams = readIntAsBoolean(is);
		
		int obsoleteWeld = readInt(is);
		
		tolerance = readDouble(is);
		minEdgeLength = readDouble(is);
		maxEdgeLength = readDouble(is);
		gridAspectRatio = readDouble(is);
		gridMinCount = readInt(is);
		gridMaxCount = readInt(is);
		gridAngle = readDouble(is);
		gridAmplification = readDouble(is);
		refineAngle = readDouble(is);
		double obsoleteCombineAngle = readDouble(is);
		faceType = readInt(is);
		if(faceType<0 || faceType>2){
		    faceType = 0;
		    throw new IOException("faceType out of range");
		}
		
		if(minorVersion>=1){
		    textureRange = readInt(is);
		    if(minorVersion>=2){
			customSetting = readBool(is);
			relativeTolerance = readDouble(is);
			if(minorVersion>=3){
			    mesher = readByte(is);
			}
		    }
		}
	    }
	}
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	}
    }
    public static class TextureCoordinates{
	public MappingTag tag;
	public int dim;
	public ArrayList<IVec> texture;
    }
    
    public static class MeshFace{
	public int[] vertexIndex; // size 4 array; [2] and [3] is same for a trinangle
	public MeshFace(){}
	public MeshFace(int[] v){
	    if(v==null){ IOut.err("input array is null"); }
	    if(v.length==4) vertexIndex=v;
	    else if(v.length==3){
		vertexIndex = new int[4];
		vertexIndex[0] = v[0];
		vertexIndex[1] = v[1];
		vertexIndex[2] = v[2];
		vertexIndex[3] = v[2];
	    }
	    else{
		IOut.err("wrong input array size ("+v.length+"). vertex number should be 3 or 4.");
	    }
	}
	public MeshFace(int v1, int v2, int v3, int v4){
	    vertexIndex = new int[4];
	    vertexIndex[0] = v1;
	    vertexIndex[1] = v2;
	    vertexIndex[2] = v3;
	    vertexIndex[3] = v4;
	}
	
	public MeshFace(int v1, int v2, int v3){
	    vertexIndex = new int[4];
	    vertexIndex[0] = v1;
	    vertexIndex[1] = v2;
	    vertexIndex[2] = v3;
	    vertexIndex[3] = v3;
	}
	
	public String toString(){
	    return vertexIndex[0]+"-"+vertexIndex[1]+"-"+ vertexIndex[2]+"-"+vertexIndex[3];
	}		
    }
    public static class MappingTag{
	public UUID mappingId;
	public TextureMapping.Type mappingType;
	public int mappingCRC;
	public Xform meshXform;
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    Chunk chunk = readChunk(is);
	    is = new ByteArrayInputStream(chunk.content);
	    
	    int majorVersion = readInt(is);
	    int minorVersion = readInt(is);
	    
	    if(majorVersion==1){
		mappingId = readUUID(is);
		
		// if(obsoleteDefaultSrfpMappingId.equals(mappingId)) mappingId=nilUUID;

		mappingCRC = readInt(is);
		meshXform = readXform(is);

		if(minorVersion >=1){
		    mappingType = TextureMapping.type(readInt(is));
		}
		
	    }
	    
	}
	
    }
    public static class MeshVertexRef extends Geometry{
	public static final String uuid = "C547B4BD-BDCD-49b6-A983-0C4A7F02E31A";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeMeshVertex; }
	
	public Mesh mesh;
	public int meshVertexIndex;
	public int meshTopVertexIndex;
    }
    public static class MeshEdgeRef extends Geometry{
	public static final String uuid = "ED727872-463A-4424-851F-9EC02CB0F155";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeMeshEdge; }
	
	public Mesh mesh;
	public int topEdgeIndex;
    }
    public static class MeshFaceRef extends Geometry{
	public static final String uuid = "4F529AA5-EF8D-4c25-BCBB-162D510AA280";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnkown; }
	
	public Mesh mesh;
	public int meshFaceIndex;
    }
    public static class MeshNgonUserData extends UserData{
	public static final String uuid = "31F55AA3-71FB-49f5-A975-757584D937FF";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnkown; }
    }

    public static class BoundingBox{
	public IVec min,max;
	public BoundingBox(){ min=new IVec(); max=new IVec(); }
    }
    
    public static class NurbsCurve extends Curve{
	public static final String uuid = "4ED7D4DD-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeCurve; }
	
	public int dim;
	public int isRat;
	public int order;
	public int cvCount;
	public int knotCapacity;
	public double[] knot;
	//public int cvStride;
	//public int cvCapacity;
	//public double[] cv;
	public IVec[] cv;
	
	public NurbsCurve(){}
	
	public NurbsCurve(ICurveGeo crv){
	    dim = 3;
	    isRat = crv.isRational()?1:0;
	    order = crv.deg()+1;
	    cvCount = crv.num();
	    double[] iknot = new double[crv.knotNum()];
	    for(int i=0; i<crv.knotNum(); i++) iknot[i] = crv.knot(i);
	    knot = getRhinoKnots(iknot);
	    cv = new IVec[crv.num()];
	    for(int i=0; i<crv.num(); i++) cv[i] = crv.cp(i).get();
	    
	    icurve = crv;
	}
	public NurbsCurve(ITrimCurve tcrv){
	    dim = 2;
	    isRat = tcrv.isRational()?1:0;
	    order = tcrv.deg()+1;
	    cvCount = tcrv.num();
	    double[] iknot = new double[tcrv.knotNum()];
	    for(int i=0; i<tcrv.knotNum(); i++) iknot[i] = tcrv.knot(i);
	    knot = getRhinoKnots(iknot);
	    cv = new IVec[tcrv.num()];
	    for(int i=0; i<tcrv.num(); i++) cv[i] = tcrv.cp(i).get();
	}
	
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    if(majorVersion==1){
		
		dim = readInt(is);
		isRat = readInt(is);
		order = readInt(is);
		cvCount = readInt(is);
		int reserved1 = readInt(is);
		int reserved2 = readInt(is);
		
		BoundingBox bb = readBoundingBox(is);
		
		//IOut.p("degree="+(order-1)); //
		
		//cvStride = (isRat!=0) ? dim+1:dim;
		
		int knotCount = readInt(is);
		knot = new double[knotCount];
		for(int i=0; i<knotCount; i++){
		    knot[i] = readDouble(is);
		    IOut.debug(100, "knot["+i+"] = "+knot[i]);
		}
		
		int cvCount = readInt(is);
		int cvSize = (isRat!=0) ? dim+1:dim;
		cv = new IVec[cvCount];
		for(int i=0; i<cvCount; i++){
		    if(isRat!=0) cv[i] = new IVec4();
		    else cv[i] = new IVec();
		    
		    if(dim>=1) cv[i].x = readDouble(is);
		    if(dim>=2) cv[i].y = readDouble(is);
		    if(dim>=3) cv[i].z = readDouble(is);
		    if(dim>=4){
			IOut.err(dim+" dimension point cannot be read");
			for(int j=0; j<dim-3; j++) readDouble(is);
		    }
		    if(isRat!=0){
			double w = readDouble(is);
			((IVec4)cv[i]).w = w;
			// rhino read rational points as already weighted points !!!
			cv[i].x /= w;
			cv[i].y /= w;
			cv[i].z /= w;
		    }
		    
		    IOut.debug(100,"cv["+i+"] = "+cv[i]);
		}
		
	    }
	    
	    // convert knot
	    // in 3dm format, knot length = order + cv_count -2
	    // but in IGeo (and OBJ), knot length = degree + cv_count + 1
	    /*
	    double[] knot2 = new double[knot.length+2];
	    if(order==2){
		knot2[0] = knot[0];
		for(int i=0; i<knot.length; i++) knot2[i+1] = knot[i];
		knot2[knot.length+1] = knot[knot.length-1];
	    }
	    else if(order>2 && knot.length>2){
		knot2[0] = knot[0] - (knot[1]-knot[0]);
		for(int i=0; i<knot.length; i++) knot2[i+1] = knot[i];
		knot2[knot.length+1] = knot[knot.length-1] + (knot[knot.length-1]-knot[knot.length-2]);
	    }
	    else{
		IOut.err("wrong knot length ("+knot.length+") at order = "+order +", and cv count = "+cvCount);
	    }
	    double ustart=0, uend=0;
	    if(knot.length>order-2){
		ustart = knot[order-2];
		uend = knot[knot.length-1-(order-2)];
	    }
	    IOut.p("cv num = "+cv.length);
	    for(int i=0; i<cv.length; i++) IOut.p("cv["+i+"] = "+cv[i]);
	    IOut.p("order = "+order);
	    IOut.p("knot2 num = "+knot2.length);
	    for(int i=0; i<knot2.length; i++) IOut.p("knot2["+i+"] = "+knot2[i]);
	    IOut.p("ustart = "+ustart);
	    IOut.p("uend = "+uend);
	    new ICurve(context.server, cv, order-1, knot2, ustart, uend);
	    //.createGraphic(IGraphicMode.glFill);
	    */
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    writeChunkVersion(os,1,0,crc);
	    
	    writeInt32(os, dim, crc);
	    writeInt32(os, isRat, crc);
	    writeInt32(os, order, crc);
	    writeInt32(os, cvCount, crc);
	    writeInt32(os, 0, crc); // reserved
	    writeInt32(os, 0, crc); // reserved
	    
	    writeBoundingBox(os, new BoundingBox(), crc); // zero value bounding box
	    
	    int knotNum = (knot==null)? 0 : knot.length;
	    writeInt32(os, knotNum, crc);
	    for(int i=0; i<knotNum; i++) writeDouble(os, knot[i], crc);
	    
	    //int cvSz = cvSize();
	    
	    int cvNum = (cv!=null&&(dim==2||dim==3))?cv.length:0;
	    writeInt32(os, cvNum, crc);
	    
	    for(int i=0; i<cvNum; i++){
		if(dim==2){
		    if(isRat==0){
			writeDouble(os, cv[i].x, crc);
			writeDouble(os, cv[i].y, crc);
		    }
		    else{
			double w = 1.0;
			if(cv[i] instanceof IVec4) w = ((IVec4)cv[i]).w;
			// rhino read rational points as already weighted points
			writeDouble(os, cv[i].x*w, crc);
			writeDouble(os, cv[i].y*w, crc);
			writeDouble(os, w, crc);
		    }
		}
		else if(dim==3){
		    if(isRat==0){
			writeDouble(os, cv[i].x, crc);
			writeDouble(os, cv[i].y, crc);
			writeDouble(os, cv[i].z, crc);
		    }
		    else{
			double w = 1.0;
			if(cv[i] instanceof IVec4) w = ((IVec4)cv[i]).w;
			// rhino read rational points as already weighted points
			writeDouble(os, cv[i].x*w, crc);
			writeDouble(os, cv[i].y*w, crc);
			writeDouble(os, cv[i].z*w, crc);
			writeDouble(os, w, crc);
		    }
		}
	    }
	}
	
	public double[] getIGKnots(){
	    // convert knot
	    // in 3dm format, knot length = order + cv_count -2
	    // but in IGeo (and OBJ), knot length = degree + cv_count + 1
	    
	    double[] knot2 = new double[knot.length+2];
	    if(order==2){
		knot2[0] = knot[0];
		for(int i=0; i<knot.length; i++) knot2[i+1] = knot[i];
		knot2[knot.length+1] = knot[knot.length-1];
	    }
	    else if(order>2 && knot.length>2){
		knot2[0] = knot[0] - (knot[1]-knot[0]);
		for(int i=0; i<knot.length; i++) knot2[i+1] = knot[i];
		knot2[knot.length+1] = knot[knot.length-1] + (knot[knot.length-1]-knot[knot.length-2]);
	    }
	    else{
		IOut.err("wrong knot length ("+knot.length+") at order = "+order +", and cv count = "+cvCount);
	    }
	    return knot2;
	}
	
	public static double[] getRhinoKnots(double[] iknot ){
	    if(iknot.length<=2){
		IOut.err("knot is too short");
	    }
	    double[] rknot = new double[iknot.length-2];
	    for(int i=0; i<rknot.length; i++) rknot[i] = iknot[i+1];
	    return rknot;
	}
	
	public ICurve createIObject(Rhino3dmFile context, IServerI s){
	    double[] knot2 = getIGKnots();
	    double ustart=0, uend=1;
	    if(knot.length>order-2){
		ustart = knot[order-2];
		uend = knot[knot.length-1-(order-2)];
	    }
	    else{
		IOut.err("knot is too short: knot.length="+knot.length+", order="+order);
		return null;
	    }
	    //IOut.p("cv num = "+cv.length);
	    //for(int i=0; i<cv.length; i++) IOut.p("cv["+i+"] = "+cv[i]);
	    //IOut.p("order = "+order);
	    //IOut.p("knot2 num = "+knot2.length);
	    //for(int i=0; i<knot2.length; i++) IOut.p("knot2["+i+"] = "+knot2[i]);
	    //IOut.p("ustart = "+ustart);
	    //IOut.p("uend = "+uend);
	    ICurve crv = new ICurve(s, cv, order-1, knot2, ustart, uend);
	    //setAttributesToIObject(context,crv);
	    return crv;
	}
	public ICurveGeo createIGeometry(Rhino3dmFile context, IServerI s){
	    double[] knot2 = getIGKnots();
	    double ustart=0, uend=1;
	    if(knot.length>order-2){
		ustart = knot[order-2];
		uend = knot[knot.length-1-(order-2)];
		
		//IOut.debug(20, "order="+order); //
		//for(int i=0; i<knot.length; i++) IOut.debug(20, "knot["+i+"]="+knot[i]);
		//for(int i=0; i<knot2.length; i++) IOut.debug(20, "knot2["+i+"]="+knot2[i]);
		//IOut.debug(20, "ustart="+ustart+", uend="+uend); //
	    }
	    else{
		IOut.err("knot is too short: knot.length="+knot.length+", order="+order);
		return null;
	    }
	    return new ICurveGeo(cv, order-1, knot2, ustart, uend);
	}
	
	public ITrimCurve createTrimCurve(Rhino3dmFile context, IServerI s, ISurfaceI srf){
	    double[] knot2 = getIGKnots();
	    double ustart=0, uend=1;
	    if(knot.length>order-2){
		ustart = knot[order-2];
		uend = knot[knot.length-1-(order-2)];
	    }
	    else{
		IOut.err("knot is too short: knot.length="+knot.length+", order="+order);
		return null;
	    }
	    //return new ITrimCurve(srf, cv, order-1, knot2, ustart, uend);
	    return new ITrimCurve(cv, order-1, knot2, ustart, uend);
	}
	
	public int cvSize(){ return (isRat!=0) ? dim+1:dim; }
	
	public static Interval getKnotVectorDomain(int order, int cvCount, double[] knot){
	    if(order<2 || cvCount<order || knot==null) return null;
	    Interval interval = new Interval();
	    interval.v1 = knot[order-2];
	    interval.v2 = knot[cvCount-1];
	    return interval;
	}
	public static boolean isValidKnotVector(int order, int cvCount, double[] knot){
	    if(order < 2 ||
	       cvCount < order ||
	       knot == null ||
	       knot[order-2] >= knot[order-1] ||
	       knot[cvCount-2] >= knot[cvCount-1]) return false;
	    for(int i=0; i<knot.length-1; i++){
		if(knot[i] > knot[i+1]) return false;
	    }
	    for(int i=0; i<knot.length-order+1; i++){
		if(knot[i] >= knot[i+order-1]) return false;
	    }
	    return true;
	}
	
	public Interval domain(){ return getKnotVectorDomain(order,cvCount,knot); }
	
	public boolean isValid(){
	    if(dim <= 0 ||
	       order < 2 ||
	       cvCount < order||
	       //cvStride < cvSize() ||
	       cv == null ||
	       knot == null ||
	       isValidKnotVector(order,cvCount,knot)) return false;
	    
	    // skipped
	    if(isRat!=0){
	    }
	    else{
	    }
	    return true; 
	}
    }
    
    public static class NurbsSurface extends Surface{
	public static final String uuid = "4ED7D4DE-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeSurface; }
	
	public int dim;
	public int isRat;
	public int[] order;
	public int[] cvCount;
	public int[] knotCapacity;
	public double[][] knot;
	//public int[] cvStride;
	//public int[] cvCapacity;
	public IVec[][] cv;
	
	public NurbsSurface(){}
	
	public NurbsSurface(ISurfaceGeo surf){
	    dim = 3;
	    isRat = surf.isRational()?1:0;
	    order = new int[2];
	    order[0] = surf.udeg()+1;
	    order[1] = surf.vdeg()+1;
	    cvCount = new int[2];
	    cvCount[0] = surf.unum();
	    cvCount[1] = surf.vnum();
	    knot = new double[2][];
	    double[] iuknot = new double[surf.uknotNum()];
	    for(int i=0;i<iuknot.length; i++) iuknot[i] = surf.uknot(i);
	    knot[0] = NurbsCurve.getRhinoKnots(iuknot);
	    
	    double[] ivknot = new double[surf.vknotNum()];
	    for(int i=0;i<ivknot.length; i++) ivknot[i] = surf.vknot(i);
	    knot[1] = NurbsCurve.getRhinoKnots(ivknot);
	    
	    cv = new IVec[cvCount[0]][cvCount[1]];
	    for(int i=0; i<cvCount[0]; i++){
		for(int j=0; j<cvCount[1]; j++){
		    cv[i][j] = surf.cp(i,j).get();
		}
	    }
	}
	
	public Interval domain(int dir){
	    Interval d = new Interval();
	    d.v1 = knot[dir][order[dir]-2];
	    d.v2 = knot[dir][cvCount[dir]-1];
	    return d;
	}
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    if(majorVersion==1){
		
		order = new int[2];
		cvCount = new int[2];
		//cvStride = new int[2];
		knotCapacity = new int[2];
		knot = new double[2][];
		//cvCapacity = new int[2];
		
				
		dim = readInt(is);
		isRat = readInt(is);
		order[0] = readInt(is);
		order[1] = readInt(is);
		cvCount[0] = readInt(is);
		cvCount[1] = readInt(is);
		int reserved1 = readInt(is);
		int reserved2 = readInt(is);
		
		BoundingBox bb = readBoundingBox(is);
		
		//cvStride[1] = (isRat!=0) ? dim+1:dim;
		//cvStride[0] = cvStride[1]*cvCount[1];
		
		int knotCount = readInt(is);
		knot[0] = new double[knotCount];
		for(int i=0; i<knotCount; i++){
		    knot[0][i] = readDouble(is);
		    IOut.debug(100, "uknot["+i+"]="+knot[0][i]);
		}
		
		knotCount = readInt(is);
		knot[1] = new double[knotCount];
		for(int i=0; i<knotCount; i++){
		    knot[1][i] = readDouble(is);
		    IOut.debug(100, "vknot["+i+"]="+knot[1][i]);
		}
		
		
		int count = readInt(is);
		int cvSize = (isRat!=0) ? dim+1:dim;
		
		if(count!=cvCount[0]*cvCount[1]){
		    IOut.err("cv count ("+cvCount+") doesn't match with cvCount[0]("+cvCount[0]+") * cvCount[1]("+cvCount[1]+")");
		}
		
		cv = new IVec[cvCount[0]][cvCount[1]];
		
		for(int i=0; i<cvCount[0]; i++){
		    for(int j=0; j<cvCount[1]; j++){
			if(isRat!=0) cv[i][j] = new IVec4();
			else cv[i][j] = new IVec();
			
			if(dim>=1) cv[i][j].x = readDouble(is);
			if(dim>=2) cv[i][j].y = readDouble(is);
			if(dim>=3) cv[i][j].z = readDouble(is);
			if(dim>=4){
			    IOut.err(dim+" dimension point cannot be read");
			    for(int k=0; k<dim-3; k++) readDouble(is);
			}
			if(isRat!=0){
			    double w = readDouble(is);
			    ((IVec4)cv[i][j]).w = w;
			    // rhino read rational points as already weighted points !!!
			    cv[i][j].x /= w;
			    cv[i][j].y /= w;
			    cv[i][j].z /= w;
			}
			
			//IOut.debug(110, "cv["+i+"]["+j+"]="+cv[i][j]); //
		    }
		}
	    }
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    writeChunkVersion(os,1,0,crc);
	    writeInt32(os, dim, crc);
	    writeInt32(os, isRat, crc);
	    writeInt32(os, order[0], crc);
	    writeInt32(os, order[1], crc);
	    writeInt32(os, cvCount[0], crc);
	    writeInt32(os, cvCount[1], crc);
	    
	    writeInt32(os, 0, crc); // reserved
	    writeInt32(os, 0, crc); // reserved
	    
	    writeBoundingBox(os, new BoundingBox(), crc); // zero value bounding box
	    
	    int uKnotNum = knot[0]!=null?knot[0].length:0;
	    writeInt32(os, uKnotNum, crc);
	    for(int i=0; i<uKnotNum; i++) writeDouble(os, knot[0][i], crc);
	    
	    int vKnotNum = knot[1]!=null?knot[1].length:0;
	    writeInt32(os, vKnotNum, crc);
	    for(int i=0; i<vKnotNum; i++) writeDouble(os, knot[1][i], crc);
	    
	    //int cvSz = cvSize();
	    
	    int cvNum = (cv!=null&&dim==3&&cvCount[0]>0&&cvCount[1]>0)?cvCount[0]*cvCount[1]:0;
	    writeInt32(os, cvNum, crc);
	    if(cvNum>0){
		for(int i=0; i<cvCount[0]; i++){
		    for(int j=0; j<cvCount[1]; j++){
			if(isRat==0){
			    writeDouble(os, cv[i][j].x, crc);
			    writeDouble(os, cv[i][j].y, crc);
			    writeDouble(os, cv[i][j].z, crc);
			}
			else{
			    double w = 1.0;
			    if(cv[i][j] instanceof IVec4) w = ((IVec4)cv[i][j]).w;
			    writeDouble(os, cv[i][j].x*w, crc);
			    writeDouble(os, cv[i][j].y*w, crc);
			    writeDouble(os, cv[i][j].z*w, crc);
			    writeDouble(os, w, crc);
			}
		    }
		}
	    }
	}
	
	
	public int cvSize(){ return (isRat!=0) ? dim+1:dim; }
	
	public double[] getIGUKnots(){
	    // convert knot
	    // in 3dm format, knot length = order + cv_count -2
	    // but in IGeo (and OBJ), knot length = degree + cv_count + 1
	    double[] uknot = new double[knot[0].length+2];
	    if(order[0]==2){
		uknot[0] = knot[0][0];
		for(int i=0; i<knot[0].length; i++) uknot[i+1] = knot[0][i];
		uknot[knot[0].length+1] = knot[0][knot[0].length-1];
	    }
	    else if(order[0]>2 && knot[0].length>2){
		uknot[0] = knot[0][0] - (knot[0][1]-knot[0][0]);
		for(int i=0; i<knot[0].length; i++) uknot[i+1] = knot[0][i];
		uknot[knot[0].length+1] = knot[0][knot[0].length-1] + (knot[0][knot[0].length-1]-knot[0][knot[0].length-2]);
	    }
	    else{
		IOut.err("wrong knot length ("+knot[0].length+") at order = "+order[0] +", and cv count = "+cvCount[0]);
	    }
	    return uknot;
	}
	
	public double[] getIGVKnots(){
	    // convert knot
	    // in 3dm format, knot length = order + cv_count -2
	    // but in IGeo (and OBJ), knot length = degree + cv_count + 1
	    double[] vknot = new double[knot[1].length+2];
	    if(order[1]==2){
		vknot[0] = knot[1][0];
		for(int i=0; i<knot[1].length; i++) vknot[i+1] = knot[1][i];
		vknot[knot[1].length+1] = knot[1][knot[1].length-1];
	    }
	    else if(order[1]>2 && knot[1].length>2){
		vknot[0] = knot[1][0] - (knot[1][1]-knot[1][0]);
		for(int i=0; i<knot[1].length; i++) vknot[i+1] = knot[1][i];
		vknot[knot[1].length+1] = knot[1][knot[1].length-1] + (knot[1][knot[1].length-1]-knot[1][knot[1].length-2]);
	    }
	    else{
		IOut.err("wrong knot length ("+knot[1].length+") at order = "+order[1] +", and cv count = "+cvCount[1]);
	    }
	    return vknot;
	}
	
	public ISurface createIObject(Rhino3dmFile context, IServerI s){
	    if(order==null){
		IOut.err("order is null");
		return null;
	    }
	    if(knot==null){
		IOut.err("knot is null");
		return null;
	    }
	    if(cv==null){
		IOut.err("cv is null");
		return null;
	    }
	    
	    double[] uknot = getIGUKnots();
	    double[] vknot = getIGVKnots();
	    
	    //double ustart=0, uend=0;
	    double ustart=0, uend=1; // 20120215
	    if(knot[0].length>order[0]-2){
		ustart = knot[0][order[0]-2];
		uend = knot[0][knot[0].length-1-(order[0]-2)];
	    }
	    
	    //double vstart=0, vend=0;
	    double vstart=0, vend=1; // 20120215
	    if(knot[1].length>order[1]-2){
		vstart = knot[1][order[1]-2];
		vend = knot[1][knot[1].length-1-(order[1]-2)];
	    }
	    
	    //IOut.p("cv num in u= "+cv.length);
	    //IOut.p("cv num in v= "+cv[0].length);
	    //for(int i=0; i<cv.length; i++) for(int j=0; j<cv[i].length; j++) IOut.p("cv["+i+"]["+j+"] = "+cv[i][j]);
	    //IOut.p("u order = "+order[0]);
	    //IOut.p("v order = "+order[1]);
	    //IOut.p("uknot num = "+uknot.length);
	    //IOut.p("vknot num = "+vknot.length);
	    //for(int i=0; i<uknot.length; i++) IOut.p("uknot["+i+"] = "+uknot[i]);
	    //for(int i=0; i<vknot.length; i++) IOut.p("vknot["+i+"] = "+vknot[i]);
	    //IOut.p("ustart = "+ustart);
	    //IOut.p("uend = "+uend);
	    //IOut.p("vstart = "+ustart);
	    //IOut.p("vend = "+uend);
	    
	    return new ISurface(s, cv, order[0]-1, order[1]-1,
				uknot, vknot, ustart, uend, vstart, vend);
	}
	
	public ISurfaceGeo createIGeometry(Rhino3dmFile context, IServerI s){
	    if(order==null){
		IOut.err("order is null");
		return null;
	    }
	    if(knot==null){
		IOut.err("knot is null");
		return null;
	    }
	    if(cv==null){
		IOut.err("cv is null");
		return null;
	    }
	    
	    double[] uknot = getIGUKnots();
	    double[] vknot = getIGVKnots();
	    
	    double ustart=0, uend=1;
	    if(knot[0].length>order[0]-2){
		ustart = knot[0][order[0]-2];
		uend = knot[0][knot[0].length-1-(order[0]-2)];
	    }
	    
	    //double vstart=0, vend=0;
	    double vstart=0, vend=1; // 20120215
	    if(knot[1].length>order[1]-2){
		vstart = knot[1][order[1]-2];
		vend = knot[1][knot[1].length-1-(order[1]-2)];
	    }
	    
	    return new ISurfaceGeo(cv, order[0]-1, order[1]-1,
				   uknot, vknot, ustart, uend, vstart, vend);
	    
	}
	
	
    }
    
    public static class NurbsCage extends Geometry{
	public static final String uuid = "06936AFB-3D3C-41ac-BF70-C9319FA480A1";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeCage; }
    }
    public static class MorphControl extends Geometry{
	public static final String uuid = "D379E6D8-7C31-4407-A913-E3B7040D034A";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeMorphControl; }
    }
    public static class OffsetSurface extends SurfaceProxy{
	public static final String uuid = "00C61749-D430-4ecc-83A8-29130A20CF9C";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeSurface; }
    }
    public static class PlaneSurface extends Surface{
	public static final String uuid = "4ED7D4DF-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeSurface; }
	
	public Plane plane;
	public Interval[] domain;
	public Interval[] extents;
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		plane = readPlane(is);
		domain = new Interval[2];
		domain[0] = readInterval(is);
		domain[1] = readInterval(is);
		extents = new Interval[2];
		extents[0] = domain[0];
		extents[1] = domain[1];
		if(minorVersion>=1){
		    extents[0] = readInterval(is);
		    extents[1] = readInterval(is);
		}
	    }
	    
	}
	
	public ISurface createIObject(Rhino3dmFile context, IServerI s){
	    if(plane==null){
		IOut.err("plane is null"); return null;
	    }
	    if(extents==null){
		IOut.err("extent is null"); return null;
	    }
	    
	    IVec origin = plane.origin;
	    IVec[][] cpts = new IVec[2][2];
	    cpts[0][0] = origin.dup().add(plane.xaxis,extents[0].v1).add(plane.yaxis,extents[1].v1);
	    cpts[1][0] = origin.dup().add(plane.xaxis,extents[0].v2).add(plane.yaxis,extents[1].v1);
	    cpts[0][1] = origin.dup().add(plane.xaxis,extents[0].v1).add(plane.yaxis,extents[1].v2);
	    cpts[1][1] = origin.dup().add(plane.xaxis,extents[0].v2).add(plane.yaxis,extents[1].v2);
	    
	    //IOut.p("plane = "+plane);
	    //IOut.p("domain[0] = "+domain[0]);
	    //IOut.p("domain[1] = "+domain[1]);
	    //IOut.p("extents[0] = "+extents[0]);
	    //IOut.p("extents[1] = "+extents[1]);
	    
	    //return new ISurface(s, cpts); //
	    double[] uknots = new double[]{ domain[0].v1,domain[0].v1,domain[0].v2,domain[0].v2 };
	    double[] vknots = new double[]{ domain[1].v1,domain[1].v1,domain[1].v2,domain[1].v2 };
	    return new ISurface(s, cpts, 1, 1, uknots, vknots,
				domain[0].v1, domain[0].v2, domain[1].v1, domain[1].v2);
	    
	}
	
	public ISurfaceGeo createIGeometry(Rhino3dmFile context, IServerI s){
	    if(plane==null){ IOut.err("plane is null"); return null; }
	    if(extents==null){ IOut.err("extent is null"); return null; }
	    IVec origin = plane.origin;
	    IVec[][] cpts = new IVec[2][2];
	    cpts[0][0] = origin.dup().add(plane.xaxis,extents[0].v1).add(plane.yaxis,extents[1].v1);
	    cpts[1][0] = origin.dup().add(plane.xaxis,extents[0].v2).add(plane.yaxis,extents[1].v1);
	    cpts[0][1] = origin.dup().add(plane.xaxis,extents[0].v1).add(plane.yaxis,extents[1].v2);
	    cpts[1][1] = origin.dup().add(plane.xaxis,extents[0].v2).add(plane.yaxis,extents[1].v2);
	    
	    //IOut.p("plane = "+plane);
	    //IOut.p("domain[0] = "+domain[0]);
	    //IOut.p("domain[1] = "+domain[1]);
	    //IOut.p("extents[0] = "+extents[0]);
	    //IOut.p("extents[1] = "+extents[1]);
	    
	    //return new ISurfaceGeo(cpts); //
	    
	    double[] uknots = new double[]{ domain[0].v1,domain[0].v1,domain[0].v2,domain[0].v2 };
	    double[] vknots = new double[]{ domain[1].v1,domain[1].v1,domain[1].v2,domain[1].v2 };
	    
	    return new ISurfaceGeo(cpts, 1, 1, uknots, vknots,
				   domain[0].v1, domain[0].v2, domain[1].v1, domain[1].v2);
	    
	}
	
	public Interval domain(int dir){
	    return dir!=0 ? domain[1]:domain[0];
	}
	public boolean isValid(){
	    return plane.isValid() &&
		domain[0].isIncreasing() && domain[1].isIncreasing() &&
		extents[0].isIncreasing() && extents[1].isIncreasing() ;
	}
    }
    public static class ClippingPlaneSurface extends PlaneSurface{
	public static final String uuid = "DBC5A584-CE3F-4170-98A8-497069CA5C36";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypeClipPlane; }
    }
    public static class PointCloud extends Geometry{
	public static final String uuid = "2488F347-F8FA-11d3-BFEC-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypePointset; }
    }
    public static class Point extends Geometry{
	public static final String uuid = "C3101A1D-F157-11d3-BFE7-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypePoint; }
	
	public IVec point;
	
	public Point(){}
	public Point(IVec p){ point = p; }
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    int[] version = readChunkVersion(is);
	    if(version[0]!=1) throw new IOException("invalid major version : "+String.valueOf(version[0]));
	    
	    //IG.err("chunkVersion = "+version[0]+", "+version[1]); //
	    
	    point = new IVec();
	    point.x = readDouble(is);
	    point.y = readDouble(is);
	    point.z = readDouble(is);
	    //IOut.p("Point.read: coordinates "+point);
	    //new IPoint(context.server, point);
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    writeChunkVersion(os,1,0,crc);
	    
	    if(point==null){
		IOut.err("point is null"); //
		point = new IVec(); // zero point is set
	    }
	    
	    writeDouble(os,point.x,crc);
	    writeDouble(os,point.y,crc);
	    writeDouble(os,point.z,crc);
	}
	
	
	public IPoint createIObject(Rhino3dmFile context, IServerI s){
	    IPoint p = new IPoint(s, point);
	    //setAttributesToIObject(context,p);
	    return p;
	}
	public IVec createIGeometry(Rhino3dmFile context, IServerI s){
	    return point.dup();
	}
	
    }
    public static class PointGrid extends Geometry{
	public static final String uuid = "4ED7D4E5-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	public int getType(){ return objectTypePointset; }
	
    }
    public static class PolyCurve extends Curve{
	public static final String uuid = "4ED7D4E0-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeCurve; }
	
	public static final double sqrtEpsilon = 1.490116119385000000e-8;
	public CurveArray segment;
	public ArrayList<Double> t;
	
	public Interval domain(){
	    int count = segment.size();
	    if(count>0 && t.get(0) < t.get(count)) return new Interval(t.get(0),t.get(count));
	    return null;
	}
	
	public Interval segmentDomain(int i){
	    Interval domain = new Interval();
	    if(i>=0 && i<count()){
		domain.v1 = t.get(i);
		domain.v2 = t.get(i+1);
	    }
	    return domain;
	}
	
	public boolean isValid(){
	    return isValid(false);
	}
	public boolean isValid(boolean allowGap){
	    //int count = segment.size();
	    
	    for(int i=0; i<segment.size(); i++) if(!segment.get(i).isValid()) return false;
	    
	    //skip other check
	    
	    return true;
	}
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    int segmentCount = readInt(is);
	    int reserved1 = readInt(is);
	    int reserved2 = readInt(is);
	    
	    BoundingBox bbox = readBoundingBox(is);
	    
	    t = readArrayDouble(is);
	    segment = new CurveArray(segmentCount);
	    
	    for(int segIdx = 0; segIdx <segmentCount; segIdx++){
		RhinoObject obj = readObject(context,is);
		if(obj!=null){
		    if(obj instanceof Curve){
			Curve crv = (Curve)obj;
			segment.add(crv);
		    }
		    else{ throw new IOException("invalid class of instance: "+obj); }
		}
	    }
	    
	    if(segment.size()==0) throw new IOException("number of segemnts is zero");
	    if(segment.size()!=segmentCount) throw new IOException("number of segemnts doesn't match with count");
	    if(t.size()!=segmentCount+1) throw new IOException("number of domain doesn't match with number of segment");
	    
	    Interval in0=null;
	    Interval in1 = segment.get(0).domain();
	    double d0=0;
	    double d1 = in1.length();
	    double fuzz=0;
	    double tval=0, s=0;
	    for(int segIdx=1; segIdx<segmentCount; segIdx++){
		tval = t.get(segIdx);
		in0 = in1;
		d0=d1;
		in1 = segment.get(segIdx).domain();
		if(in1==null){
		    //IOut.p("segment.get(segIdx).domain == null");
		    //IOut.p("segment.get(segIdx) = "+segment.get(segIdx));
		}
		d1 = in1.length();
		s = in0.v2;
		if( s!= tval && s == in1.v1 && tval > in0.v1 && tval < in1.v2 ){
		    fuzz = ((d0<=d1)?d0:d1)*sqrtEpsilon;
		    if(Math.abs(tval-s) <= fuzz) t.set(segIdx, s);
		}
	    }
	    fuzz = d1*sqrtEpsilon;
	    tval = t.get(segmentCount);
	    s = in1.v2;
	    if( s!=tval && tval > in1.v1 && Math.abs(s-tval) <= fuzz){
		t.set(segmentCount, s);
	    }
	    
	    if(context.openNurbsVersion < 200304080){
		removeNesting();
	    }
	}
	public int count(){
	    if(segment==null) return 0;
	    return segment.size();
	}
	
	public Curve segmentCurve(int i){ return segment.get(i); }
	
	public void removeNesting(){
	    int n = count();
	    ArrayList<Double> oldT = t;
	    CurveArray oldSegment = segment;
	    
	    t = new ArrayList<Double>();
	    t.add(oldT.get(0));
	    segment = new CurveArray();
	    
	    for(int i=0; i<n; i++){
		if(oldSegment.get(i) instanceof PolyCurve){
		    PolyCurve poly = (PolyCurve)oldSegment.get(i);
		    flatten(poly, new Interval(oldT.get(i),oldT.get(i+1)),t,segment);
		}
		else{
		    t.add(oldT.get(i+1));
		    segment.add(oldSegment.get(i));
		}
	    }
	}
	
	public void flatten(PolyCurve poly, Interval pdom, ArrayList<Double> newT, CurveArray newSeg){
	    int n = poly.count();
	    double t0 = pdom.v1;
	    Interval pcdom = poly.domain();
	    for(int i=0; i<n; i++){
		double sdom = poly.segmentDomain(i).v1;
		double ndom = pcdom.normalizedParameterAt(sdom);
		double t1 = pdom.parameterAt(ndom);
		Curve seg = poly.segmentCurve(i);
		if(seg instanceof PolyCurve){
		    flatten((PolyCurve)seg, new Interval(t0,t1),newT,newSeg);
		    poly.harvestSegment(i);
		}
		else{
		    newT.add(t1);
		    newSeg.add(seg);
		    poly.harvestSegment(i);
		}
		t0 = t1;
	    }
	}
	
	public Curve harvestSegment(int i){
	    Curve segmentCurve=null;
	    if(i>=0 && i<segment.size()){
		segmentCurve = segment.get(i);
		segment.set(i,null);
	    }
	    return segmentCurve;
	}
	
	
	public IObject/*ICurve*/ createIObject(Rhino3dmFile context, IServerI s){
	    
	    //for(int i=0; i<t.size(); i++){ IOut.p("t (domain) "+i+": "+t.get(i)); }
	    //for(int i=0; i<segment.size(); i++){ Interval d = segment.get(i).domain();IOut.p("segment["+i+"].domain = "+d.v1+" - "+d.v2); }
	    
	    // temporary implementation: this should be replaced by instantiation IPolycurve
	    
	    ArrayList<ICurve> icrvs = new ArrayList<ICurve>();
	    boolean curveDegree1 = true;
	    for(int i=0; i<segment.size(); i++){
		IObject obj = segment.get(i).createIObject(context,s);
		if(obj instanceof ICurve){
		    ICurve crv = (ICurve)obj;
		    icrvs.add(crv);
		    if(crv.deg()>1) curveDegree1=false;
		}
	    }
	    
	    if(curveDegree1){
		ArrayList<IVec> pts = new ArrayList<IVec>();
		for(int i=0; i<icrvs.size(); i++){
		    for(int j=0; j<icrvs.get(i).num(); j++){
			IVec pt = icrvs.get(i).cp(j);
			if( (j==0 && (i==0 || !pts.get(pts.size()-1).eq(pt))) || j>0 )
			    pts.add(pt); 
		    }
		    icrvs.get(i).del(); // delete
		}
		return new ICurve(s,pts.toArray(new IVec[pts.size()]));
	    }
	    
	    return new IPolycurve(icrvs);
	    //return null;
	}
	
	public ICurveGeo createIGeometry(Rhino3dmFile context, IServerI s){
	    if(segment==null ||segment.size()==0) return null;
	    
	    
	    ArrayList<ICurveGeo> icrvs = new ArrayList<ICurveGeo>();
	    boolean curveDegree1 = true;
	    for(int i=0; i<segment.size(); i++){
		ICurveGeo crv = segment.get(i).createIGeometry(context,s);
		icrvs.add(crv);
		if(crv.deg()>1) curveDegree1=false;
	    }
	    
	    if(curveDegree1){
		ArrayList<IVecI> pts = new ArrayList<IVecI>();
		for(int i=0; i<icrvs.size(); i++){
		    for(int j=0; j<icrvs.get(i).num(); j++){
			IVecI pt = icrvs.get(i).cp(j);
			if( (j==0 && (i==0 || !pts.get(pts.size()-1).eq(pt))) || j>0 )
			    pts.add(pt); 
		    }
		}
		return new ICurveGeo(pts.toArray(new IVec[pts.size()]));
	    }
	    
	    // return only first curve
	    //return segment.get(0).createIGeometry(context,s);
	    return icrvs.get(0);
	}

	/*
	// get geometry of each segment at index i.
	public ICurveGeo createIGeometryOfEach(Rhino3dmFile context, IServerI s, int i){
	    if(segment==null ||i>=segment.size()) return null;
	    return segment.get(i).createIGeometry(context,s);
	}
	*/
		
	// this needs to be implemented
	public ITrimCurve createTrimCurve(Rhino3dmFile context, IServerI s, ISurfaceI srf){
	    
	    if(segment==null ||segment.size()==0) return null;
	    
	    ArrayList<ITrimCurve> icrvs = new ArrayList<ITrimCurve>();
	    boolean curveDegree1 = true;
	    for(int i=0; i<segment.size(); i++){
		ITrimCurve crv = segment.get(i).createTrimCurve(context,s,srf);
		icrvs.add(crv);
		if(crv.deg()>1) curveDegree1=false;
	    }
	    
	    //if(curveDegree1){
	    if(true){ // temporary until IPolycurve is fully implemented.
		ArrayList<IVecI> pts = new ArrayList<IVecI>();
		for(int i=0; i<icrvs.size(); i++){
		    for(int j=0; j<icrvs.get(i).num(); j++){
			IVecI pt = icrvs.get(i).cp(j);
			if( (j==0 && (i==0 || !pts.get(pts.size()-1).eq(pt))) || j>0 )
			    pts.add(pt);
		    }
		}
		return new ITrimCurve(pts.toArray(new IVec[pts.size()]));
	    }
	    
	    return null; // !!! 
	}
	
    }
    public static class PolyEdgeSegment extends CurveProxy{
	public static final String uuid = "42F47A87-5B1B-4e31-AB87-4639D78325D6";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeCurve; }
    }
    public static class PolyEdgeCurve extends PolyCurve{
	public static final String uuid = "39FF3DD3-FE0F-4807-9D59-185F0D73C0E4";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeCurve; }
    }
    
    public static class PointArray extends ArrayList<IVec>{
	public PointArray(){ super(); }
	public PointArray(int c){ super(c); }
    }
    public static class Point2Array extends ArrayList<IVec2>{
	public Point2Array(){ super(); }
	public Point2Array(int c){ super(c); }
    }
    public static class Polyline extends PointArray{
	public boolean isValid(){ return true; } // skipped
	public Polyline(){ super(); }
	public Polyline(int c){ super(c); }
    }
    public static class PolylineCurve extends Curve{
	public static final String uuid = "4ED7D4E6-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeCurve; }
	
	public Polyline pline;
	public ArrayList<Double> t;
	public int dim;
	
	public boolean isValid(){
	    // skipping other checks
	    return pline.isValid();
	}
	
	public Interval domain(){
	    int count = pline.size();
	    if(count>=2 && t.get(0) < t.get(count-1)) return new Interval(t.get(0),t.get(count-1));
	    return null;
	}
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		pline = readPolyline(is);
		t = readArrayDouble(is);
		dim = readInt(is);
	    }
	    //IOut.p("polyline"); 
	}
	
	public ICurve createIObject(Rhino3dmFile context, IServerI s){
	    ICurve crv = new ICurve(s,pline.toArray(new IVec[pline.size()]));
	    //setAttributesToIObject(context,crv);
	    return crv;
	}
	public ICurveGeo createIGeometry(Rhino3dmFile context, IServerI s){
	    return new ICurveGeo(pline.toArray(new IVec[pline.size()]));
	}
	public ITrimCurve createTrimCurve(Rhino3dmFile context, IServerI s, ISurfaceI srf){
	    //return new ITrimCurve(srf, pline.toArray(new IVec[pline.size()]));
	    return new ITrimCurve(pline.toArray(new IVec[pline.size()]));
	}
    }
    public static class RevSurface extends Surface{
	public static final String uuid = "A16220D3-163B-11d4-8000-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeSurface; }
	
	public Curve curve;
	public Line axis;
	public Interval angle;
	public Interval t;
	public boolean transposed=false;
	public BoundingBox bbox;
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		axis = readLine(is);
		angle = readInterval(is);
		bbox = readBoundingBox(is);
		transposed = readInt(is)!=0;
		byte hasCurve = readByte(is);
		if(hasCurve!=0){
		    RhinoObject obj = readObject(context,is);
		    if(obj!=null){
			if(obj instanceof Curve) curve = (Curve)obj;
			else{ IOut.err("wrong instance of class : "+obj); } //
		    }
		}
		t = new Interval(angle.min(),angle.max());
	    }
	    else if(majorVersion==2){
		axis = readLine(is);
		angle = readInterval(is);
		t = readInterval(is);
		bbox = readBoundingBox(is);
		transposed = readInt(is)!=0;
		byte hasCurve = readByte(is);
		if(hasCurve!=0){
		    RhinoObject obj = readObject(context,is);
		    if(obj!=null){
			if(obj instanceof Curve) curve = (Curve)obj;
			else{ IOut.err("wrong instance of class : "+obj); } // 
		    }
		}
	    }
	    
	    //IOut.p("axis = "+axis);
	    //IOut.p("angle = "+angle); 
	}
	
	public ISurface createIObject(Rhino3dmFile context, IServerI s){

	    
	    ISurfaceGeo surf = createIGeometry(context,s);
	    return new ISurface(s,surf);
	}
	
	public ISurfaceGeo createIGeometry(Rhino3dmFile context, IServerI s){
	    
	    IVec axisCenter = axis.from;
	    IVec axisDir = axis.to.diff(axis.from);
	    
	    ICurveGeo profile = curve.createIGeometry(context,s);
	    if(profile == null){
		IOut.err("no valid profile curve");
		return null;
	    }
	    
	    IVec[] centers = new IVec[profile.cpNum()];
	    
	    for(int i=0; i<profile.cpNum(); i++){
		centers[i] = profile.cp(i).get().dup().projectToLine(axisCenter, axisDir);
	    }
	    
	    IVec4[][] cpts = new IVec4[profile.cpNum()][];
	    double[] uknots = new double[profile.knotNum()];
	    for(int i=0; i<profile.knotNum(); i++) uknots[i] = profile.knot(i);
	    double[] vknots = null;
	    int udeg = profile.deg();
	    int vdeg = 1;
	    if( Math.abs(angle.length())<2*Math.PI-IConfig.angleTolerance){
		// arc
		double a = angle.v2 - angle.v1;
		vknots = IArcGeo.arcKnots(a);
		vdeg = IArcGeo.arcDeg();
		for(int i=0; i<profile.cpNum(); i++){
		    IVec startPt = profile.cp(i).get().dup();
		    startPt.rot(centers[i], axisDir, angle.v1);
		    cpts[i] = IArcGeo.arcCP(centers[i], axisDir, startPt, a);
		}
	    }
	    else{
		//circle
		vknots = ICircleGeo.circleKnots();
		vdeg = ICircleGeo.circleDeg();
		for(int i=0; i<profile.cpNum(); i++){
		    IVec rollDir = profile.cp(i).get().diff(centers[i]);
		    cpts[i] = ICircleGeo.circleCP(centers[i],axisDir,rollDir,rollDir.len());
		}
	    }
	    
	    double ustart = profile.ustart();
	    double uend = profile.uend();
	    double vstart = t.v1;
	    double vend = t.v2;
	    
	    // u is profile, v is rotation
	    if(!transposed){
		// v is profile, u is rotation
		double[] tmpknots = uknots;
		uknots = vknots;
		vknots = tmpknots;
		
		double tmp;
		tmp = ustart;
		ustart = vstart;
		vstart = tmp;
		tmp = uend;
		uend = vend;
		vend = tmp;
		
		int itmp = udeg;
		udeg = vdeg;
		vdeg = itmp;
		
		IVec4[][] cpts2 = new IVec4[cpts[0].length][cpts.length];
		for(int i=0; i<cpts.length; i++)
		    for(int j=0; j<cpts[i].length; j++) cpts2[j][i] = cpts[i][j];
		
		cpts = cpts2;
	    }

	    // ustart=0, uend=1, vstart=0, vend=1 not to  normalize knots
	    ISurfaceGeo surf = new ISurfaceGeo(cpts, udeg, vdeg, uknots, vknots, 0.0, 1.0, 0.0, 1.0);
	    surf.ustart = ustart;
	    surf.uend = uend;
	    surf.vstart = vstart;
	    surf.vend = vend;
	    return surf;
	}
	
    }
    
    public static class SumSurface extends Surface{
	public static final String uuid = "C4CD5359-446D-4690-9FF5-29059732472B";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeSurface; }
	
	public Curve[] curve;
	public IVec basepoint;
	public BoundingBox bbox;
	
	public void read(Rhino3dmFile context, InputStream is)throws IOException{
	    int[] version = readChunkVersion(is);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    if(majorVersion==1){
		basepoint = readPoint3(is);
		bbox = readBoundingBox(is);
		curve = new Curve[2];
		RhinoObject obj = readObject(context,is);
		if(obj!=null){
		    if(obj instanceof Curve) curve[0] = (Curve)obj;
		    else{ IOut.err("wrong instance of class : "+obj); } // 
		}
		obj = readObject(context,is);
		if(obj!=null){
		    if(obj instanceof Curve) curve[1] = (Curve)obj;
		    else{ IOut.err("wrong instance of class : "+obj); } // 
		}
	    }
	}
	
	public ISurface createIObject(Rhino3dmFile context, IServerI s){
	    ISurfaceGeo surf = createIGeometry(context,s);
	    return new ISurface(s,surf);
	}
	
	public ISurfaceGeo createIGeometry(Rhino3dmFile context, IServerI s){
	    if(curve[0]==null || curve[1]==null || basepoint==null) return null;
	    
	    ICurveGeo crv1 = curve[0].createIGeometry(context, s);
	    ICurveGeo crv2 = curve[1].createIGeometry(context, s);
	    
	    //IOut.err("curve[0] == "+curve[0]);
	    //IOut.err("curve[1] == "+curve[1]);
	    
	    if(crv1==null||crv2==null) return null; //?
	    
	    int udeg = crv1.deg();
	    int vdeg = crv2.deg();
	    
	    double[] uknots = crv1.knots;
	    double[] vknots = crv2.knots;
	    
	    int unum = crv1.num();
	    int vnum = crv2.num();
	    
	    Interval udom = curve[0].domain();
	    Interval vdom = curve[1].domain();
	    
	    IVecI[][] cpts = new IVecI[unum][vnum];
	    
	    for(int i=0; i<unum; i++){
		for(int j=0; j<vnum; j++){
		    cpts[i][j] = basepoint.dup().add(crv1.cp(i)).add(crv2.cp(j));
		}
	    }

	    // putting ustart 0, uend 1, vstart 0, vend 1 is important. otherwise knots are normalized again especially when the surface is closed.
	    ISurfaceGeo isurf = new ISurfaceGeo(cpts, udeg, vdeg, uknots, vknots, 0.0, 1.0, 0.0, 1.0);
	    
	    isurf.ustart = udom.v1;
	    isurf.uend = udom.v2;
	    isurf.vstart = vdom.v1;
	    isurf.vend = vdom.v2;
	    
	    for(int i=0; i<uknots.length; i++){ IOut.debug(20, "uknots["+i+"]="+uknots[i]); }
	    for(int i=0; i<vknots.length; i++){ IOut.debug(20, "vknots["+i+"]="+vknots[i]); }
	    IOut.debug(20, "ustart="+isurf.ustart); //
	    IOut.debug(20, "uend="+isurf.uend); //
	    IOut.debug(20, "vstart="+isurf.vstart); //
	    IOut.debug(20, "vend="+isurf.vend); //
	    	    
	    
	    return isurf;
	}
	
    }
    
    public static class SurfaceProxy extends Surface{
	public static final String uuid = "4ED7D4E2-E947-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeSurface; }
	
	public Surface surface;
	public boolean transposed;
	
	public void setProxySurface(Surface proxySurface){
	    if(proxySurface==this) proxySurface=null;
	    surface = proxySurface;
	    transposed = false;
	}
	
    }
    public static class UnknownUserData extends UserData{
	public static final String uuid = "850324A8-050E-11d4-BFFA-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    
    public static class UserStringList extends UserData{
	public ArrayList<UserString> userStrings;
	
	public UserStringList(){
	    userStrings = new ArrayList<UserString>();
	}

	public void add(UserString str){
	    userStrings.add(str);
	}

	public int size(){
	    return userStrings.size();
	}
	
	public static final String uuid = "CE28DE29-F4C5-4faa-A50A-C3A6849B6329";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
	
	
	public void writeBody(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk, 1, 0);
	    
	    int count = 0;
	    if(userStrings!=null){
		count = userStrings.size();
	    }
	    
	    writeInt(cos, count, cos.getCRC());
	    	    
	    for(int i=0; i<count; i++){
		userStrings.get(i).write(context, cos, cos.getCRC());
	    }
	    
	    writeChunk(os, cos.getChunk());
	}
    }
    
    public static class UserString{
	String key, value;
	public UserString(String k, String v){
	    key=k; value=v;
	}
	
	public void write(Rhino3dmFile context, OutputStream os, CRC32 crc)throws IOException{
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk, 1, 0);
	    
	    writeString(cos, key, cos.getCRC());
	    	    
	    writeString(cos, value, cos.getCRC());
	    
	    writeChunk(os, cos.getChunk());
	}
    }
    
    public static class Viewport extends Geometry{
	public static final String uuid = "D66E5CCF-EA39-11d3-BFE5-0010830122F0";
	public UUID getClassUUID(){ return new UUID(uuid); }
	//public int getType(){ return objectTypeUnknown; }
    }
    
    /*
    // for debug
    public static void main(String[] args){
	//IOut.p("Viewport.uuid = "+Viewport.uuid);
	//UUID uuid = new UUID(Viewport.uuid);
	//IOut.p(uuid);
	ClassRegistry.init(); //
    }
    */
    
}
