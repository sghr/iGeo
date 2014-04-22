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
//import java.awt.Color;
import java.util.*;
import java.util.zip.*;

/**
   Rhinoceros 3dm exporter class.
   The current implementation is inefficient for large data.
   To be improved.
   
   @author Satoru Sugihara
*/
public class IRhino3dmExporter extends IRhino3dm{
    
    // NOTE: 3dm file is little endian, Java is big endian
    
    public final static int version = 4; // Rhino version 4 format
    public final static int openNurbsVersion = 201004095;
    	
    public OutputStream ostream;
    
    public Rhino3dmFile file; // store data when reading or writing
    
    public IServerI server;
    
    public int currentPos;
    
    
    public IRhino3dmExporter(OutputStream ostr, IServerI svr){ ostream = ostr; server = svr; }
    
    
    
    /**
       Writing the content of server out to 3dm file.
       The main entry of the exporter class.
       @param file An exporting file object.
       @param server A server interface containing exporting data.
       @return Boolean true if writing is successful. Otherwise false.
    */
    public static boolean write(File file, IServerI server){
	try{
	    //FileOutputStream fos = new FileOutputStream(file);
	    IRandomAccessOutputStream fos = new IRandomAccessOutputStream(file);
	    if(fos==null) return false;
	    boolean retval = write(fos, server);
	    fos.close();
	    return retval;
	}catch(IOException e){ e.printStackTrace(); }
	return false;
    }
    
    
    public static boolean write(OutputStream os, IServerI server) throws IOException{
	IRhino3dmExporter exporter = new IRhino3dmExporter(os,server);
	exporter.write();
	return true;
    }
    
    
    public void write() throws IOException{
	
	file = new Rhino3dmFile(version,openNurbsVersion,server);
	
	currentPos = 0;
	
	// Start Section
	writeStartSection();
	
	// Properties
	writeProperties();
	
	// Setting
	
	writeSettings();
	
	// Bitmap
	writeBitmapTable();
	
	// Texture Mapping
	writeTextureMappingTable();
	
	// Material
	writeMaterialTable();
	
	// Linetype
	writeLinetypeTable();
	
	// Layer
	writeLayerTable();
	
	// Group
	writeGroupTable();
	
	// Font
	writeFontTable();
	
	// Dimstyle
	writeDimStyleTable();
	
	// Light
	writeLightTable();
	
	// Hatch Pattern
	writeHatchPatternTable();
	
	// Instance Definition
	writeInstanceDefinitionTable();
	
	// Object
	writeObjectTable();
	
	// History Record
	writeHistoryRecordTable();
	
	
	// User Data
	writeUserDataTable();
	
	writeEndMark();
	
    }
    
    
    public void writeStartSection() throws IOException{
	String versionNumText = String.valueOf(version);
	final int numTextWidth = 8;
	for(int i=0; i<numTextWidth && versionNumText.length() < numTextWidth;
	    i++, versionNumText = " "+versionNumText);
	
	final String versionString = "3D Geometry File Format " + versionNumText;
	write(versionString.getBytes(), 32, null);
	
	// info chunk
	final String infoString = " IGeo 3D geometry library : version "+IG.version();
	byte[] infoBytes = infoString.getBytes();
	byte[] infoBytes2 = new byte[infoBytes.length+2];
	System.arraycopy(infoBytes,0,infoBytes2,0,infoBytes.length);
	infoBytes2[infoBytes2.length-2] = 26;
	infoBytes2[infoBytes2.length-1] = 0;
	
	writeChunk(new Chunk(tcodeCommentBlock, infoBytes2));
    }
    
    public void writeProperties() throws IOException{
	Chunk[] chunks = new Chunk[]{
	    new Chunk(tcodePropertiesOpenNurbsVersion, openNurbsVersion)
	};
	writeChunkTable(tcodePropertiesTable, chunks);
    }
    
    
    public void writeSettings() throws IOException{

	if(file!=null && file.settings!=null && file.settings.unitsAndTolerances!=null &&
	   file.settings.unitsAndTolerances.unitSystem!=null){
	    
	    Chunk[] chunks = new Chunk[1];
	    
	    ChunkOutputStream cos = new ChunkOutputStream(tcodeSettingsUnitsAndTols);
	    file.settings.unitsAndTolerances.write(file, cos, cos.getCRC());
	    chunks[0] = cos.getChunk();
	    
	    writeChunkTable(tcodeSettingsTable, chunks);
	}
	else{
	    writeChunkTable(tcodeSettingsTable);
	}
    }
    
    public void writeBitmapTable() throws IOException{
	writeChunkTable(tcodeBitmapTable);
    }
    
    public void writeTextureMappingTable() throws IOException{
	writeChunkTable(tcodeTextureMappingTable);
    }
    
    public Chunk getMaterialChunk(Material mat) throws IOException{
	ChunkOutputStream cos = new ChunkOutputStream(tcodeMaterialRecord);
	mat.write(file, cos, cos.getCRC());
	return cos.getChunk();
    }
    
    public void writeMaterialTable() throws IOException{
	
	file.materials = new ArrayList<Material>();
	file.imaterials = new ArrayList<IMaterial>();
	
	for(int i=0; i<server.server().objectNum(); i++){
	    IAttribute attr = server.server().object(i).attr();
	    if(attr!=null){
		if(attr.material!=null){
		    if(!file.imaterials.contains(attr.material)){
			if(attr.material instanceof IBasicMaterial){
			    IBasicMaterial bmat = (IBasicMaterial)attr.material;
			    Material rmat = new Material(bmat);
			    rmat.materialIndex = file.materials.size();
			    file.imaterials.add(bmat);
			    file.materials.add(rmat);
			}
		    }
		}
		else if(attr.color!=null){
		    IBasicMaterial mat = new IBasicMaterial();
		    mat.diffuse = attr.color; // only this? // to be rendered, diffuse needs to be set
		    //mat.ambient = attr.color; // 
		    //mat.specular = attr.color; // 
		    //mat.emission = attr.color; // 
		    mat.transparency = (double)(255 - attr.color.getAlpha())/255;
		    
		    Material rmat = new Material(mat);
		    rmat.materialIndex = file.materials.size();
		    file.imaterials.add(mat);
		    file.materials.add(rmat);
		    
		    attr.material = mat;
		}
	    }
	}
	
	ChunkTable materialTable = new ChunkTable(tcodeMaterialTable);
	
	for(int i=0; i<file.materials.size(); i++){
	    //Chunk ck = getMaterialChunk(file.materials.get(i));
	    materialTable.add(nestChunk(tcodeMaterialRecord,
					getObjectChunk(file.materials.get(i))));
	    //materialTable.add(ck);
	}
	
	//writeChunkTable(tcodeMaterialTable);
	writeChunkTable(materialTable);
    }
    
    public void writeLinetypeTable() throws IOException{
	writeChunkTable(tcodeLinetypeTable);
    }
    
    public void writeLayerTable() throws IOException{
	//writeChunkTable(tcodeLayerTable);
	ChunkTable layerTable = new ChunkTable(tcodeLayerTable);
	for(int i=0; i<server.server().layerNum(); i++){
	    //IOut.p("layer["+i+"] = "+server.server().getLayer(i).name());
	    
	    Layer l = new Layer(server.server().getLayer(i), i);
	    layerTable.add(nestChunk(tcodeLayerRecord,getObjectChunk(l)));
	}
	writeChunkTable(layerTable);
    }
    
    
    public void writeGroupTable() throws IOException{
	writeChunkTable(tcodeGroupTable);
    }
    
    public void writeFontTable() throws IOException{
	writeChunkTable(tcodeFontTable);
    }
    
    public void writeDimStyleTable() throws IOException{
	writeChunkTable(tcodeDimStyleTable);
    }
    
    public void writeLightTable() throws IOException{
	writeChunkTable(tcodeLightTable);
    }
    
    public void writeHatchPatternTable() throws IOException{
	writeChunkTable(tcodeHatchPatternTable);
    }
    
    public void writeInstanceDefinitionTable() throws IOException{
	writeChunkTable(tcodeInstanceDefinitionTable);
    }
    
    public void writeObjectTable() throws IOException{
	//writeChunkTable(tcodeObjectTable);
	
	ChunkTable objectTable = new ChunkTable(tcodeObjectTable);
	
	IRandomAccessOutputStream raostream = null;
	
	if(ostream instanceof IRandomAccessOutputStream){
	    raostream = (IRandomAccessOutputStream)ostream;
	    objectTable.writeTableHeader(raostream);
	}
	
	int objectNum = server.server().objectNum();
	
	IOut.debug(0, "writing " + objectNum + "objects");
	
	int objCount=0;
	for(int i=0; i<objectNum; i++){
	    
	    //if(i%100==0&&i>0){ IOut.debug(1, "object #"+i+"/"+objectNum); } //
	    if(i%100==0&&i>0){ IOut.debug(0, "object #"+i+"/"+objectNum); } //
	    
	    IObject obj = server.server().getObject(i);
	    if(obj.isValid()){
		//Chunk objChunk = getObjectChunk(server.server().getObject(i));
		Chunk objChunk = getObjectChunk(obj);
		
		/*
		if(obj instanceof IMesh){ // somehow this prevent saving meshes from crashing. why!? timing of GC?
		    //IOut.debug(100,i+": chunk ="+objChunk); // ??
		    //if(objChunk!=null) IOut.debug(100,i+": chunk size="+objChunk.contentLength()); // ??
		}
		*/
		
		if(objChunk!=null){
		    if(raostream!=null){

			objectTable.writeTableEntry(raostream,objChunk);
			
			objChunk.clear();
			objChunk = null;
		    }
		    else{ objectTable.add(objChunk); }
		    
		    objCount++;
		}
	    }
	    else{
		IOut.err("invalid object " + obj + " is skipped.");
	    }
	}
	if(raostream!=null){
	    objectTable.writeTableEnd(raostream);
	}
	else{
	    objectTable.serialize();
	    writeChunkTable(objectTable);
	}
	IOut.debug(1, objCount + " objects are wrote"); //
    }
    
    static public RhinoObject getRhinoObject(IObject e, Rhino3dmFile context){
	RhinoObject obj=null;
	if(e instanceof IPoint){
	    obj = getRhinoPoint(((IPoint)e).get());
	}
	else if(e instanceof IPointR){
	    obj = getRhinoPoint(((IPointR)e).get());
	}
	else if(e instanceof ICurve){
	    obj =  getRhinoCurve(((ICurve)e).get());
	}
	else if(e instanceof ICurveR){
	    obj = getRhinoCurve(((ICurveR)e).get());
	}
	else if(e instanceof ISurface){
	    obj =  getRhinoSurface(((ISurface)e).get());
	}
	else if(e instanceof ISurfaceR){
	    obj =  getRhinoSurface(((ISurfaceR)e).get());
	}
	else if(e instanceof IMesh){
	    obj = getRhinoMesh( ((IMesh)e).mesh );
	}
	else if(e instanceof IMeshR){
	    obj = getRhinoMesh( ((IMeshR)e).mesh );
	}
	else if(e instanceof IBrep){
	    obj = getRhinoBrep( (IBrep)e );
	}
	else if(e instanceof IText){
	    obj = getRhinoText( (IText)e );
	}
	
	if(obj!=null){
	    obj.setAttributes(new ObjectAttributes(e,context));
	    
	    if(e.userData!=null){
		// currently only one string list is stored; if multiple hashmaps exist, all merged.
		
		UserStringList stringList = new UserStringList();
		
		for(int i=0; i<e.userData.length; i++){
		    if(e.userData[i] instanceof HashMap){
			HashMap map = (HashMap)e.userData[i];
			Set keys = map.keySet();
			if(keys!=null){
			    Iterator it = keys.iterator();
			    if(it!=null){
				while(it.hasNext()){
				    Object k = it.next();
				    if(k instanceof String){
					Object v = map.get(k);
					if(v instanceof String){
					    stringList.add(new UserString((String)k,(String)v));
					}
				    }
				}
			    }
			}
		    }
		}
		if(stringList.size()>0){
		    obj.userDataList = new UserData[1];
		    obj.userDataList[0] = stringList;
		}
	    }
	}
	return obj;
    }
    
    static public Point getRhinoPoint(IVec p){ return new Point(p); }
    
    static public NurbsCurve getRhinoCurve(ICurveGeo crv){ return new NurbsCurve(crv); }
    
    static public /*NurbsSurface*/ RhinoObject getRhinoSurface(ISurfaceGeo srf){
	if(!srf.hasTrim() || (srf.hasDefaultTrim() && !srf.hasInnerTrim()))
	    return new NurbsSurface(srf);
	// BRep
	return new Brep(srf);
	// tmp
	//return new NurbsSurface(srf);
    }
    
    static public Brep getRhinoBrep(IBrep brep){ return new Brep(brep); }
    
    static public Mesh getRhinoMesh(IMeshI mesh){ return new Mesh(mesh); }
    
    static public TextEntity2 getRhinoText(IText text){ return new TextEntity2(text); }
    
    public Chunk getObjectChunk(IObject e) throws IOException{
	//IOut.p("e = "+e); //
	
	RhinoObject obj = getRhinoObject(e, file);
	
	if(obj==null) return null;
	
	ChunkTable ctable = new ChunkTable(tcodeObjectRecord, tcodeObjectRecordEnd);
	
	// object type
	ctable.add(new Chunk(tcodeObjectRecordType, obj.getType()));
	
	// object
	Chunk objChunk = getObjectChunk(obj);
	if(objChunk==null) return null; // no obj data
	
	//IOut.p("objChunk = "+objChunk); //
	
	ctable.add(objChunk);
	
	// attributes
	if(obj.attributes!=null){
	    ChunkOutputStream cosAttr = new ChunkOutputStream(tcodeObjectRecordAttributes);
	    obj.attributes.write(file, cosAttr, cosAttr.getCRC());
	    Chunk attrChunk = cosAttr.getChunk();
	    if(attrChunk!=null) ctable.add(attrChunk);
	    
	    if(obj.userDataList!=null){
		ChunkOutputStream cosAttrUserData = new ChunkOutputStream(tcodeObjectRecordAttributesUserData);
		//writeobjectusredata
		
		for(int i=0; i<obj.userDataList.length; i++){
		    if(obj.userDataList[i]!=null){
			ChunkOutputStream cosUserData = new ChunkOutputStream(tcodeOpenNurbsClassUserData);
			obj.userDataList[i].write(file, cosUserData, cosUserData.getCRC());
			Chunk userDataChunk = cosUserData.getChunk();
			if(userDataChunk.content!=null && userDataChunk.content.length>0){
			    //IG.err("writing userDataChunk"); //
			    writeChunk(cosAttrUserData, userDataChunk);
			}
		    }
		}
		
		
		ChunkOutputStream cosEnd = new ChunkOutputStream(tcodeOpenNurbsClassEnd);
		writeChunk(cosAttrUserData, cosEnd.getChunk());
		ctable.add(cosAttrUserData.getChunk());
	    }
	
	}

	ctable.serialize();
	
	return ctable;
	
    }
    
    
    
    public void writeHistoryRecordTable() throws IOException{
	writeChunkTable(tcodeHistoryRecordTable);
    }
    
    public void writeUserDataTable() throws IOException{
	writeChunkTable(tcodeUserTable);

	//ChunkOutputStream cos = new ChunkOutputStream(tcodeUserTableUUID);
	
    }
    
    public void writeEndMark() throws IOException{
	//writeChunk(new Chunk(tcodeEndOfFile, itob(currentPos + 4*3 /*header+body+int content*/)));
	
	ChunkOutputStream cos = new ChunkOutputStream(tcodeEndOfFile);
	
	if(file.sizeOfChunkLength()==4){
	    int len = currentPos + 4*3; /*header+body+int content*/
	    writeInt32(cos, len, cos.getCRC());
	}
	else{
	    int len = currentPos + 8*3; /*header+body+int content*/
	    writeInt64(cos, (long)len, cos.getCRC());
	}
	
	writeChunk(cos.getChunk());
	
	//writeChunk(new Chunk(tcodeEndOfFile, itob(currentPois+4*3)));
    }
    
    
    public Chunk getObjectChunk(RhinoObject obj)throws IOException{
	
	UUID uuid = obj.getClassUUID();
	
	ChunkTable objChunkTable = new ChunkTable(tcodeOpenNurbsClass);
	
	ChunkOutputStream cosUUID = new ChunkOutputStream(tcodeOpenNurbsClassUUID);
	uuid.write(cosUUID,cosUUID.getCRC());
	objChunkTable.add(cosUUID.getChunk());
	
	ChunkOutputStream cosData = new ChunkOutputStream(tcodeOpenNurbsClassData);
	obj.write(file,cosData,cosData.getCRC());
	Chunk dataChunk = cosData.getChunk();
	if(dataChunk.content==null || dataChunk.content.length==0) return null; // no data 
	objChunkTable.add(dataChunk);

	/*
	// user data
	//...
	if(obj.userDataList!=null){
	    for(int i=0; i<obj.userDataList.length; i++){
		if(obj.userDataList[i]!=null){
		    ChunkOutputStream cosUserData = new ChunkOutputStream(tcodeOpenNurbsClassUserData);
		    obj.userDataList[i].write(file, cosUserData, cosUserData.getCRC());
		    Chunk userDataChunk = cosUserData.getChunk();
		    if(userDataChunk.content!=null && userDataChunk.content.length>0){
			objChunkTable.add(userDataChunk);

			IG.err("writing userDataChunk"); //
		    }
		}
	    }
	}
	*/
	
	//obj.write(file,cosData,cosData.getCRC());
	//Chunk dataChunk = cosData.getChunk();
	//if(dataChunk.content==null || dataChunk.content.length==0) return null; // no data 
	//objChunkTable.add(dataChunk);
	
	// skipped
	
	Chunk endChunk = new Chunk(tcodeOpenNurbsClassEnd,0);
	objChunkTable.add(endChunk);
	
	objChunkTable.serialize();
	return objChunkTable;
    }
    
    public static Chunk getObjectChunk(Rhino3dmFile context, RhinoObject obj)throws IOException{
	
	UUID uuid = obj.getClassUUID();
	
	ChunkTable objChunkTable = new ChunkTable(tcodeOpenNurbsClass);
	
	ChunkOutputStream cosUUID = new ChunkOutputStream(tcodeOpenNurbsClassUUID);
	uuid.write(cosUUID,cosUUID.getCRC());
	objChunkTable.add(cosUUID.getChunk());
	
	ChunkOutputStream cosData = new ChunkOutputStream(tcodeOpenNurbsClassData);
	obj.write(context,cosData,cosData.getCRC());
	Chunk dataChunk = cosData.getChunk();
	if(dataChunk.content==null || dataChunk.content.length==0) return null; // no data 
	objChunkTable.add(dataChunk);
	
	// user data
	// skipped
	
	Chunk endChunk = new Chunk(tcodeOpenNurbsClassEnd,0);
	objChunkTable.add(endChunk);
	
	objChunkTable.serialize();
	return objChunkTable;
    }
    
    
    public Chunk nestChunk(int tcode, Chunk c)throws IOException{
	ChunkOutputStream cos = new ChunkOutputStream(tcode);
	writeChunk(cos,c);
	return cos.getChunk();
    }
    
    
    public void write(byte[] data, CRC32 crc) throws IOException{
	ostream.write(data); currentPos += data.length; if(crc!=null) crc.update(data);
    }
    public void write(byte[] data, int len, CRC32 crc) throws IOException{
	ostream.write(data, 0, len); currentPos += len; if(crc!=null) crc.update(data,0,len);
    }
    public void writeBytes(byte[] data, CRC32 crc) throws IOException{ write(data,crc); }
    public void writeByte(byte b, CRC32 crc) throws IOException{ ostream.write(b); currentPos++; if(crc!=null) crc.update(b); }
    public void writeChar(char c, CRC32 crc) throws IOException{ ostream.write(c); currentPos++; if(crc!=null) crc.update(c); }
    public void writeInt32(int i, CRC32 crc) throws IOException{ write(itob(i),crc); }
    public void writeInt16(short s, CRC32 crc) throws IOException{ write(stob(s),crc); }
    public void writeInt64(long l, CRC32 crc) throws IOException{ write(ltob(l),crc); }
    public void writeShort(short s, CRC32 crc) throws IOException{ writeInt16(s,crc); }
    public void writeLong(long l, CRC32 crc) throws IOException{ writeInt64(l,crc); }
    
    
    public static void write(OutputStream os, byte[] data, CRC32 crc) throws IOException{ os.write(data); if(crc!=null) crc.update(data); }
    public static void write(OutputStream os, byte[] data, int len, CRC32 crc) throws IOException{
	os.write(data, 0, len); if(crc!=null) crc.update(data);
    }
    public static void writeBytes(OutputStream os, byte[] data, CRC32 crc) throws IOException{ write(os,data,crc); }
    public static void writeByte(OutputStream os, byte b, CRC32 crc) throws IOException{ os.write(b); if(crc!=null) crc.update(b); }
    public static void writeChar(OutputStream os, char c, CRC32 crc) throws IOException{ os.write(c); if(crc!=null) crc.update(c); }
    public static void writeInt32(OutputStream os, int i, CRC32 crc) throws IOException{ write(os,itob(i),crc); }
    public static void writeInt16(OutputStream os, short s, CRC32 crc) throws IOException{ write(os,stob(s),crc); }
    public static void writeInt64(OutputStream os, long l, CRC32 crc) throws IOException{ write(os,ltob(l),crc); }
    public static void writeShort(OutputStream os, short s, CRC32 crc) throws IOException{ writeInt16(os,s,crc); }
    public static void writeInt(OutputStream os, int i, CRC32 crc) throws IOException{ write(os,itob(i),crc); }
    public static void writeLong(OutputStream os, long l, CRC32 crc) throws IOException{ writeInt64(os,l,crc); }

    public static void writeBool(OutputStream os, boolean b, CRC32 crc) throws IOException{
	writeByte(os,(byte)(b?1:0),crc);
    }
    
    public static void writeDouble(OutputStream os, double d, CRC32 crc) throws IOException{
	writeInt64(os, Double.doubleToLongBits(d), crc);
    }
    public static void writeFloat(OutputStream os, float f, CRC32 crc) throws IOException{
	writeInt32(os, Float.floatToIntBits(f), crc);
    }
    
    public static void writePoint(OutputStream os, IVecI v, CRC32 crc) throws IOException{
	writeDouble(os,v.x(),crc);
	writeDouble(os,v.y(),crc);
	writeDouble(os,v.z(),crc);
    }
    public static void writePoint3f(OutputStream os, IVecI v, CRC32 crc) throws IOException{
	writeFloat(os,(float)v.x(),crc);
	writeFloat(os,(float)v.y(),crc);
	writeFloat(os,(float)v.z(),crc);
    }
    public static void writePoint2(OutputStream os, IVec2I v, CRC32 crc) throws IOException{
	writeDouble(os,v.x(),crc);
	writeDouble(os,v.y(),crc);
    }
    public static void writePoint2f(OutputStream os, IVec2I v, CRC32 crc) throws IOException{
	writeFloat(os,(float)v.x(),crc);
	writeFloat(os,(float)v.y(),crc);
    }
    
    public static void writeVector(OutputStream os, IVecI v, CRC32 crc) throws IOException{
	writePoint(os,v,crc);
    }
    public static void writeVector(OutputStream os, IVec2I v, CRC32 crc) throws IOException{
	writePoint2(os,v,crc);
    }
    public static void writePlane(OutputStream os, Plane plane, CRC32 crc) throws IOException{
	writePoint(os,plane.origin,crc);
	writeVector(os,plane.xaxis,crc);
	writeVector(os,plane.yaxis,crc);
	writeVector(os,plane.zaxis,crc);
	writeDouble(os,plane.planeEquation.x,crc);
	writeDouble(os,plane.planeEquation.y,crc);
	writeDouble(os,plane.planeEquation.z,crc);
	writeDouble(os,plane.planeEquation.d,crc);
    }
    
    public static void writeXform(OutputStream os, Xform xform, CRC32 crc) throws IOException{
	for(int i=0; i<4; i++)
	    for(int j=0; j<4; j++)
		writeDouble(os,xform.xform[i][j],crc);
    }
    
    public static void writeInterval(OutputStream os, Interval interval, CRC32 crc) throws IOException{
	writeDouble(os,interval.v1,crc);
	writeDouble(os,interval.v2,crc);
    }
    
    public static void writeString(OutputStream os, String s, CRC32 crc) throws IOException{
	/*
	if(s==null){
	    IOut.err("string is null"); //
	    return;
	}
	*/
	
	if(s==null){ s = ""; } // !! ??
	
	
	//IOut.p("input="+s);
	
	int len = s.length() + 1;
	
	byte[] b = s.getBytes("UTF-16LE");
	/*
	byte[] b = new byte[2*s.length()];
	for(int i=0; i<s.length(); i++){
	    b[i*2]=(byte)(s.charAt(i)&0xFF);
	    b[i*2+1] = 0;
	}
	//b[2*s.length()]=0;
	*/
	
	//IOut.p("byte[] = "+hex(b));
	
	//int len = b.length + 1 + 1;
	
	writeInt32(os,len,crc);
	writeBytes(os,b,crc);
	writeByte(os,(byte)0,crc); // 16 bit terminator
	writeByte(os,(byte)0,crc);
    }
    
    
    public static void writeColor(OutputStream os, IColor color, CRC32 crc) throws IOException{
	int r = 0;
	int g = 0;
	int b = 0;
	int a = 0;
	if(color!=null){
	    r = color.getRed();
	    g = color.getGreen();
	    b = color.getBlue();
	    a = 255 - color.getAlpha();
	    if(r<0) r=0; else if(r>255) r=255; // added 20130202
	    if(g<0) g=0; else if(g>255) g=255;
	    if(b<0) b=0; else if(b>255) b=255;
	    if(a<0) a=0; else if(a>255) a=255;
	}
	writeByte(os, (byte)(r&0xFF), crc);
	writeByte(os, (byte)(g&0xFF), crc);
	writeByte(os, (byte)(b&0xFF), crc);
	writeByte(os, (byte)(a&0xFF), crc);
    }
    
    public static void writeBoundingBox(OutputStream os, BoundingBox bbox, CRC32 crc)throws IOException{
	writeDouble(os,bbox.min.x,crc);
	writeDouble(os,bbox.min.y,crc);
	writeDouble(os,bbox.min.z,crc);
	writeDouble(os,bbox.max.x,crc);
	writeDouble(os,bbox.max.y,crc);
	writeDouble(os,bbox.max.z,crc);
    }
    public static void writeSurfaceCurvature(OutputStream os, SurfaceCurvature sc, CRC32 crc)throws IOException{
	writeDouble(os,sc.k1,crc);
	writeDouble(os,sc.k2,crc);
    }
    
    public static void writeArray(OutputStream os, ArrayList<? extends RhinoObject> array, CRC32 crc) throws IOException{
	writeArray(null,os,array,crc);
    }
    public static void writeArray(Rhino3dmFile context, OutputStream os, ArrayList<? extends RhinoObject> array, CRC32 crc) throws IOException{
	int count = 0; // write zero size when array is null
	if(array!=null) count = array.size(); 
	writeInt32(os,count,crc);
	for(int i=0; array!=null&&i<count; i++) array.get(i).write(context, os,crc);
    }
    
    public static void writeArrayInt(OutputStream os, ArrayList<Integer> array, CRC32 crc) throws IOException{
	int count = 0; // write zero size when array is null
	if(array!=null) count = array.size(); 
	writeInt32(os,count,crc);
	for(int i=0; array!=null && i<count; i++) writeInt32(os,array.get(i),crc);
    }
    
    public static void writeArrayPoint(OutputStream os, ArrayList<IVec> array, CRC32 crc) throws IOException{
	int count = 0; // write zero size when array is null
	if(array!=null) count = array.size(); 
	writeInt32(os,count,crc);
	for(int i=0; array!=null && i<count; i++) writePoint(os,array.get(i),crc);
    }
    
    public static void writeArrayPoint3f(OutputStream os, ArrayList<IVec> array, CRC32 crc) throws IOException{
	int count = 0; // write zero size when array is null
	if(array!=null) count = array.size(); 
	writeInt32(os,count,crc);
	for(int i=0; array!=null && i<count; i++) writePoint3f(os,array.get(i),crc);
    }
    
    public static byte[] writeArrayPoint3f(ArrayList<IVec> array, CRC32 crc) throws IOException{
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	for(int i=0; array!=null&&i<array.size(); i++) writePoint3f(baos,array.get(i),crc);
	return baos.toByteArray();
    }
    
    public static void writeArrayPoint2(OutputStream os, ArrayList<IVec2> array, CRC32 crc) throws IOException{
	int count = 0; // write zero size when array is null
	if(array!=null) count = array.size(); 
	writeInt32(os,count,crc);
	for(int i=0; array!=null && i<count; i++) writePoint2(os,array.get(i),crc);
    }
    
    public static void writeArrayPoint2f(OutputStream os, ArrayList<IVec2> array, CRC32 crc) throws IOException{
	int count = 0; // write zero size when array is null
	if(array!=null) count = array.size(); 
	writeInt32(os,count,crc);
	for(int i=0; array!=null && i<count; i++) writePoint2f(os,array.get(i),crc);
    }
    
    public static byte[] writeArrayPoint2f(ArrayList<IVec2> array, CRC32 crc) throws IOException{
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	for(int i=0; array!=null&&i<array.size(); i++) writePoint2f(baos,array.get(i),crc);
	return baos.toByteArray();
    }
    
    public static void writeArrayColor(OutputStream os, ArrayList<IColor> array, CRC32 crc) throws IOException{
	int count = 0; // write zero size when array is null
	if(array!=null) count = array.size(); 
	writeInt32(os,count,crc);
	for(int i=0; array!=null && i<count; i++) writeColor(os,array.get(i),crc);
    }
    
    public static byte[] writeArrayColor(ArrayList<IColor> array, CRC32 crc) throws IOException{
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	for(int i=0; array!=null && i<array.size(); i++) writeColor(baos,array.get(i),crc);
	return baos.toByteArray();
    }
    
    public static void writeArraySurfaceCurvature(OutputStream os, ArrayList<SurfaceCurvature> array, CRC32 crc) throws IOException{
	int count = 0; // write zero size when array is null
	if(array!=null) count = array.size(); 
	writeInt32(os,count,crc);
	for(int i=0; array!=null && i<count; i++) writeSurfaceCurvature(os,array.get(i),crc);
    }
    public static byte[] writeArraySurfaceCurvature(ArrayList<SurfaceCurvature> array, CRC32 crc) throws IOException{
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	for(int i=0; array!=null && i<array.size(); i++) writeSurfaceCurvature(baos,array.get(i),crc);
	return baos.toByteArray();
    }
    
    
    public static void writeArrayDisplayMaterialRef(Rhino3dmFile context, OutputStream os, ArrayList<DisplayMaterialRef> array, CRC32 crc) throws IOException{
	int count = 0; // write zero size when array is null
	if(array!=null) count = array.size(); 
	writeInt32(os,count,crc);
	for(int i=0; array!=null&&i<count; i++) array.get(i).write(context, os,crc);
    }
    
    
    public static void writeArrayUUIDIndex(Rhino3dmFile context, OutputStream os, ArrayList<UUIDIndex> array, CRC32 crc) throws IOException{
	int count = 0; // write zero size when array is null
	if(array!=null) count = array.size(); 
	writeInt32(os,count,crc);
	for(int i=0; array!=null&&i<count; i++){
	    writeUUID(os,array.get(i).id,crc);
	    writeInt(os,array.get(i).i,crc);
	}
    }
    
    
    
    
    public static void writeUUID(OutputStream os, UUID uuid, CRC32 crc) throws IOException{
	if(uuid!=null) uuid.write(os,crc);
	else UUID.nilValue.write(os,crc);
    }
    
    public static void writeChunkVersion(OutputStream os, int majorVersion, int minorVersion,CRC32 crc) throws IOException{
	byte b = (byte)((majorVersion<<4)&0xF0 | minorVersion&0x0F);
	writeByte(os, b, crc);
    }
    
    
    public void writeChunk(Chunk chunk) throws IOException{
	if(chunk==null) throw new IOException("chunk is null");
	if(!chunk.isShort()){
	    if(chunk.getContent()==null){
		throw new IOException("chunk is big chunk and chunk content is null : chunk = "+chunk.toString());
	    }
	    if(chunk.contentLength()!= chunk.getBody()){
		throw new IOException("chunk content length doesn't match : chunk = "+chunk.toString());
	    }
	}
	writeInt32(chunk.header,null);
	if(!chunk.isShort()&&chunk.doCRC()) writeInt32(chunk.body+4,null);
	else writeInt32(chunk.body,null);
	if(!chunk.isShort()){
	    writeBytes(chunk.content,null);
	    if(chunk.doCRC()) writeInt32(chunk.getCRC(),null);
	}
    }
    
    public static void writeChunk(OutputStream os, Chunk chunk) throws IOException{
	if(chunk==null) throw new IOException("chunk is null");
	if(!chunk.isShort()){
	    if(chunk.getContent()==null){
		throw new IOException("chunk is big chunk and chunk content is null : chunk = "+chunk.toString());
	    }
	    if(chunk.contentLength()!= chunk.getBody()){
		throw new IOException("chunk content length doesn't match : chunk = "+chunk.toString());
	    }
	}
	writeInt32(os,chunk.header,null);
	if(!chunk.isShort()&&chunk.doCRC()) writeInt32(os,chunk.body+4,null);
	else writeInt32(os,chunk.body,null);
	if(!chunk.isShort()){
	    writeBytes(os,chunk.content,null);
	    if(chunk.doCRC()) writeInt32(os,chunk.getCRC(),null);
	}
    }
    
    public void writeChunkTable(int tableTypeCode) throws IOException{
	writeChunkTable(tableTypeCode,null);
    }
    
    public void writeChunkTable(int tableTypeCode, Chunk[] chunks) throws IOException{
	writeChunkTable(tableTypeCode, chunks, tcodeEndOfTable);
    }
    
    public void writeChunkTable(int tableTypeCode, Chunk[] chunks, int endTypeCode) throws IOException{
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	for(int i=0; chunks!=null && i<chunks.length; i++) writeChunk(bos, chunks[i]);
	writeChunk(bos, new Chunk(endTypeCode, 0));
	writeChunk(new Chunk(tableTypeCode, bos.toByteArray()));
    }
    
    
    public void writeChunkTable(ChunkTable chunkTable) throws IOException{
	chunkTable.serialize(); writeChunk(chunkTable);
    }
    public static void writeChunkTable(OutputStream os, ChunkTable chunkTable) throws IOException{
	chunkTable.serialize(); writeChunk(os,chunkTable);
    }
    
    /*
    public static void writeChunkVersion(OutputStream os, int majorVersion, int minorVersion)
	throws IOException{
	byte b = (byte)(((majorVersion&0x0F) <<4)&(minorVersion&0x0F));
	writeByte(os,b);
    }
    */
    
    
    public static void writeCompressedBuffer(OutputStream os, byte[] buf, int len, CRC32 crc) throws IOException {
	
	writeInt(os, len, crc);
	
	CRC32 bufCRC = new CRC32();
	bufCRC.update(buf);
	writeInt(os, (int)bufCRC.getValue(), crc); // crc?
	
	byte method = (len>128)? (byte)1:(byte)0;
	
	writeByte(os, method, crc);
	
	if(method==0){ // uncompressed
	    write(os, buf, len, crc);
	}
	else{ // compressed
	    writeDeflate(os,buf,len,crc);
	}
	
    }

    public static void writeDeflate(OutputStream os, byte[] buf, int len, CRC32 crc) throws IOException{
	
	Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
	//deflater.setInput(buf,0,len);
	//byte[] outbuf = new byte[len];
	//int dlen = deflater.deflate(buf, 0, len);
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	DeflaterOutputStream dos = new DeflaterOutputStream(baos, deflater);
	dos.write(buf,0,len);
	dos.close();
	baos.close();
	
	ChunkOutputStream cos = new ChunkOutputStream(tcodeAnonymousChunk);
	//write(cos, outbuf, dlen, cos.getCRC());
	byte[] outbuf = baos.toByteArray();
	write(cos, outbuf, outbuf.length, cos.getCRC());
	
	//IOut.err("input length="+len); //
	//IOut.err("deflated length="+outbuf.length); //
	
	writeChunk(os, cos.getChunk());
	
    }
    
    
    public static class ChunkOutputStream extends ByteArrayOutputStream{
	public int header;
	public CRC32 crc;
	
	public ChunkOutputStream(int header){ super(); this.header = header; crc = new CRC32(); }
	
	public ChunkOutputStream(int header, int majorVersion, int minorVersion) throws IOException{
	    super();
	    this.header = header;
	    crc = new CRC32();
	    writeInt32(this, majorVersion, crc);
	    writeInt32(this, minorVersion, crc);
	}
	
	// not embeding
	//public void write(byte[] b){ super.write(b); crc.update(b); }
	//public void write(byte[] b, int off, int len){ super.write(b,off,len); crc.update(b,off,len); }
	//public void write(byte b){ super.write(b); crc.updat(b); }
	
	public CRC32 getCRC(){ return crc; }
	
	public Chunk getChunk(){
	    byte[] content = super.toByteArray();
	    return new Chunk(header, content, crc);
	}
    }
    
}
