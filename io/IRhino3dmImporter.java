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
import java.util.ArrayList;
import java.util.zip.*;


/**
   Rhinoceros 3dm importer class.
   
   @author Satoru Sugihara
*/
public class IRhino3dmImporter extends IRhino3dm{
    
    // NOTE; 3dm file is Lilliputian (little endian), Java is Blefuscudian (big endian)
        
    //BufferedReader reader;
    public InputStream istream;
    
    //int version=-1;
    
    public boolean eof=false;
    
    public Rhino3dmFile file; // store data when reading or writing
    
    public IServerI server;
    
    public ArrayList<IObject> objects;
    
    /***************************************************************************
     * non static methods
     ***************************************************************************/
    
    //public IRhino3dmImporter(BufferedReader r){ reader = r; }
    public IRhino3dmImporter(InputStream istr, IServerI svr){ istream = istr; server = svr; }
        
    
    public void readFileHeader() throws IOException{
	
	final int headerTextLen = 24;
	final int headerVersionNumLen = 8;
	
	byte[] txtBuf = new byte[headerTextLen];
	byte[] versionBuf = new byte[headerVersionNumLen];
	
	istream.read(txtBuf, 0, headerTextLen);
	istream.read(versionBuf, 0, headerVersionNumLen);
	
	try{
	    String vstr = new String(versionBuf);
	    String spl[] = vstr.split("\\s+");
	    vstr = spl[spl.length-1];
	    if(vstr.equals("X")) file.version = 2;
	    else file.version = Integer.parseInt(vstr);
	    IOut.debug(10,"3DM FILE VERSION = "+file.version); // tmp
	}catch(NumberFormatException nfe){ nfe.printStackTrace(); }
	
    }
    
    
    
    /***************************************************************************    
     * static methods
     ***************************************************************************/
    
    public static byte[] read(InputStream is, int len) throws IOException{
	byte[] buf = new byte[len];
	int ptr=0;
	int res=0;
	while(ptr<len){ // is this safe?
	    res = is.read(buf,ptr,len-ptr);
	    if(res<0){
		if(ptr>0){ //if(ptr+res)<len){
		    byte[] buf2 = new byte[ptr];
		    System.arraycopy(buf,0,buf2,0,ptr);
		    IOut.err("unexpected end of stream : len="+len+", ptr="+ptr+", res="+res);
		    printAsciiOrHex(buf2,IOut.err);
		    throw new IOException();
		}
		else{ throw new EOFException(); }
	    }
	    ptr += res;
	}
	/*
	if( (res = is.read(buf,0,len)) != len ){
	    if(res<0) throw new EOFException();
	    else{
		if(res>0){
		    byte[] buf2 = new byte[res];
		    System.arraycopy(buf,0,buf2,0,res);
		    IOut.err("unexpected end of stream :");
		    printAsciiOrHex(buf2,IOut.err);
		}
		else{ IOut.err("unexpected end of stream"); }
		throw new IOException();
	    }
	}
	*/
	return buf;
    }
    
    public static char readChar(InputStream is)throws IOException{
	int ret = is.read();
	if(ret<0) throw new EOFException();
	return (char)ret;
    }
    
    public static byte readByte(InputStream is)throws IOException{
	int ret = is.read();
	if(ret<0) throw new EOFException();
	return (byte)ret;
    }
    
    public static byte[] readBytes(InputStream is, int len)throws IOException{
	return read(is,len);
    }
    
    public static boolean readBool(InputStream is)throws IOException{
	return readByte(is) != 0;
    }
    
    public static short readShort(InputStream is)throws IOException{
	return readInt16(is);
    }
    
    public static short readInt16(InputStream is)throws IOException{
	return readInt16(read(is,2));
    }
    
    public static short readInt16(byte[] b){
	return (short)((b[1]&0xFF)<<8 | b[0]&0xFF);
    }
    
    public static int readInt(InputStream is)throws IOException{
	return readInt32(is);
    }
    
    public static int readInt32(InputStream is)throws IOException{
	return readInt32(read(is,4));
    }
    
    public static int readInt32(byte[] b){
	return b[3]<<24 | (b[2]&0xFF)<<16 | (b[1]&0xFF)<<8 | b[0]&0xFF;
    }
    
    public static long readLong(InputStream is)throws IOException{
	return readInt64(is);
    }
    
    public static long readInt64(InputStream is)throws IOException{
	return readInt64(read(is,8));
    }
    
    public static long readInt64(byte[] b){
	long l = 0L;
	for(int i=7; i>=0; i--) l = (l<<8)|(b[i]&0xFFL);
	return l;
    }
    
    public static float readFloat(InputStream is)throws IOException{
	return readFloat(read(is,4));
    }
    
    public static float readFloat(byte[] b){
	//IOut.err("float :"+hex(b)); //
	return Float.intBitsToFloat(readInt32(b));
    }
    
    public static double readDouble(InputStream is)throws IOException{
	return readDouble(read(is,8));
    }
    
    public static double readDouble(byte[] b){
	return Double.longBitsToDouble(readInt64(b));
    }
    
    
    public static IColor readColor(InputStream is)throws IOException{
	return readColor(readInt32(is));
    }
    
    public static IColor readColor(int i){
	int r = i&0xFF;
	int g = (i>>8)&0xFF;
	int b = (i>>16)&0xFF;
	int a = (i>>24)&0xFF;
	//return new Color(r,g,b,a);
	return new IColor(r,g,b,255-a); // !
    }
    
    public static String readString(InputStream is)throws IOException{
	final int maxStringLength = 65535;
	int len = readInt(is);
	if(len==0) return ""; // zero length string seems to happen regularly
	if(len < 65535){ // no more reality check?
	    byte[] b = read(is,len*2); // 1 character is 2 byte // the end of b is supposed to be null value
	    return new String(b, 0, len*2-2, "UTF-16LE"); // 3dm is little endian, remove last null 2 bytes
	}
	IOut.err("invalid string length: len = "+len); //
	return null;
    }
    
    public static UUID readUUID(Chunk chunk) throws IOException{
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	return readUUID(bais);
    }
    
    public static UUID readUUID(InputStream is) throws IOException{
	UUID uuid = new UUID();
	uuid.data1 = readInt(is);
	uuid.data2 = readShort(is);
	uuid.data3 = readShort(is);
	byte[] b = read(is, 8);
	
	//IOut.p("buf = "+hex(b)); //
	
	uuid.data4 = new byte[8];
	for(int i=0; i<8; i++) uuid.data4[i] = b[i];
	return uuid;
    }
    
    
    public static IVec2 readPoint2(InputStream is) throws IOException{
	IVec2 pt = new IVec2();
	pt.x = readDouble(is);
	pt.y = readDouble(is);
	return pt;
    }
    public static IVec2 readPoint2f(InputStream is) throws IOException{
	IVec2 pt = new IVec2();
	pt.x = readFloat(is);
	pt.y = readFloat(is);
	return pt;
    }
    public static IVec readPoint3(InputStream is) throws IOException{
	IVec pt = new IVec();
	pt.x = readDouble(is);
	pt.y = readDouble(is);
	pt.z = readDouble(is);
	return pt;
    }
    public static IVec readPoint3f(InputStream is) throws IOException{
	IVec pt = new IVec();
	pt.x = readFloat(is);
	pt.y = readFloat(is);
	pt.z = readFloat(is);
	return pt;
    }
    public static IVec readPoint4(InputStream is) throws IOException{
	IVec4 pt = new IVec4();
	pt.x = readDouble(is);
	pt.y = readDouble(is);
	pt.z = readDouble(is);
	pt.w = readDouble(is);
	return pt;
    }

    public static Line readLine(InputStream is) throws IOException{
	Line l = new Line();
	l.from = readPoint3(is);
	l.to = readPoint3(is);
	return l;
    }
    
    public static Plane readPlane(InputStream is) throws IOException{
	Plane plane = new Plane();
	plane.origin = readPoint3(is);
	plane.xaxis = readPoint3(is);
	plane.yaxis = readPoint3(is);
	plane.zaxis = readPoint3(is);
	PlaneEquation peq = new PlaneEquation();
	peq.x = readDouble(is);
	peq.y = readDouble(is);
	peq.z = readDouble(is);
	peq.d = readDouble(is);
	plane.planeEquation = peq;
	return plane;
    }

    public static SurfaceCurvature readSurfaceCurvature(InputStream is)throws IOException{
	SurfaceCurvature k = new SurfaceCurvature();
	k.k1 = readDouble(is);
	k.k2 = readDouble(is);
	return k;
    }

    public static ArrayList<Integer> readArrayInt(InputStream is) throws IOException{
	int count = readInt(is);
	ArrayList<Integer> array = new ArrayList<Integer>(count);
	for(int i=0; i<count; i++){ array.add(readInt(is)); }
	return array;
    }
    
    public static ArrayList<Double> readArrayDouble(InputStream is) throws IOException{
	int count = readInt(is);
	ArrayList<Double> array = new ArrayList<Double>(count);
	for(int i=0; i<count; i++){ array.add(readDouble(is)); }
	return array;
    }
    
    public static ArrayList<IVec> readArrayPoint(InputStream is) throws IOException{
	int count = readInt(is);
	ArrayList<IVec> array = new ArrayList<IVec>(count);
	for(int i=0; i<count; i++){ array.add(readPoint3(is)); }
	return array;
    }
    public static ArrayList<IVec2> readArrayPoint2(InputStream is) throws IOException{
	int count = readInt(is);
	ArrayList<IVec2> array = new ArrayList<IVec2>(count);
	for(int i=0; i<count; i++){ array.add(readPoint2(is)); }
	return array;
    }
    public static ArrayList<IVec2> readArrayPoint2(byte[] buf, int count) throws IOException{
	ByteArrayInputStream bais = new ByteArrayInputStream(buf);
	ArrayList<IVec2> array = new ArrayList<IVec2>(count);
	for(int i=0; i<count; i++){ array.add(readPoint2(bais)); }
	return array;
    }
    
    
    public static ArrayList<IColor> readArrayColor(InputStream is) throws IOException{
	int count = readInt(is);
	ArrayList<IColor> array = new ArrayList<IColor>(count);
	for(int i=0; i<count; i++){ array.add(readColor(is)); }
	return array;
    }
    public static ArrayList<IColor> readArrayColor(byte[] buf, int count) throws IOException{
	ByteArrayInputStream bais = new ByteArrayInputStream(buf);
	ArrayList<IColor> array = new ArrayList<IColor>(count);
	for(int i=0; i<count; i++){ array.add(readColor(bais)); }
	return array;
    }
    
    public static ArrayList<IVec2> readArrayPoint2f(InputStream is) throws IOException{
	int count = readInt(is);
	ArrayList<IVec2> array = new ArrayList<IVec2>(count);
	for(int i=0; i<count; i++){ array.add(readPoint2f(is)); }
	return array;
    }
    
    public static ArrayList<IVec2> readArrayPoint2f(byte[] buf, int count) throws IOException{
	ByteArrayInputStream bais = new ByteArrayInputStream(buf);
	ArrayList<IVec2> array = new ArrayList<IVec2>(count);
	for(int i=0; i<count; i++){ array.add(readPoint2f(bais)); }
	return array;
    }
    
    public static ArrayList<IVec> readArrayPoint3f(InputStream is) throws IOException{
	int count = readInt(is);
	ArrayList<IVec> array = new ArrayList<IVec>(count);
	for(int i=0; i<count; i++){ array.add(readPoint3f(is)); }
	return array;
    }
    
    public static ArrayList<IVec> readArrayPoint3f(byte[] buf, int count) throws IOException{
	ByteArrayInputStream bais = new ByteArrayInputStream(buf);
	ArrayList<IVec> array = new ArrayList<IVec>(count);
	for(int i=0; i<count; i++){ array.add(readPoint3f(bais)); }
	return array;
    }
    
    
    public static ArrayList<SurfaceCurvature> readArraySurfaceCurvature(InputStream is) throws IOException{
	int count = readInt(is);
	ArrayList<SurfaceCurvature> array = new ArrayList<SurfaceCurvature>(count);
	for(int i=0; i<count; i++){ array.add(readSurfaceCurvature(is)); }
	return array;
	
    }
    
    public static ArrayList<SurfaceCurvature> readArraySurfaceCurvature(byte[] buf, int count) throws IOException{
	ByteArrayInputStream bais = new ByteArrayInputStream(buf);
	ArrayList<SurfaceCurvature> array = new ArrayList<SurfaceCurvature>(count);
	for(int i=0; i<count; i++){ array.add(readSurfaceCurvature(bais)); }
	return array;
	
    }
    
    
    public static ArrayList<UUIDIndex> readArrayUUIDIndex(InputStream is) throws IOException{
	int count = readInt(is);
	ArrayList<UUIDIndex> array = new ArrayList<UUIDIndex>(count);
	for(int i=0; i<count; i++){
	    UUIDIndex uuididx = new UUIDIndex();
	    uuididx.id = readUUID(is);
	    uuididx.i = readInt(is);
	    array.add(uuididx);
	}
	return array;
    }
    
    public static ArrayList<UUIDIndex> readArrayUUIDIndex(byte[] buf, int count) throws IOException{
	ByteArrayInputStream bais = new ByteArrayInputStream(buf);
	ArrayList<UUIDIndex> array = new ArrayList<UUIDIndex>(count);
	for(int i=0; i<count; i++){
	    UUIDIndex uuididx = new UUIDIndex();
	    uuididx.id = readUUID(bais);
	    uuididx.i = readInt(bais);
	    array.add(uuididx);
	}
	return array;
    }
    
    public static PointArray readPointArray(InputStream is) throws IOException{
	int count = readInt(is);
	PointArray array = new PointArray(count);
	for(int i=0; i<count; i++){ array.add(readPoint3(is)); }
	return array;
    }
    
    public static Polyline readPolyline(InputStream is) throws IOException{
	int count = readInt(is);
	Polyline array = new Polyline(count);
	for(int i=0; i<count; i++){ array.add(readPoint3(is)); }
	return array;
    }
    
    public static Circle readCircle(InputStream is) throws IOException{
	Circle circle = new Circle();
	circle.plane = readPlane(is);
	circle.radius = readDouble(is);
	IVec scratch;
	scratch = readPoint3(is);
	scratch = readPoint3(is);
	scratch = readPoint3(is);
	return circle;
    }
    
    public static Arc readArc(InputStream is) throws IOException{
	Arc arc = new Arc();
	arc.plane = readPlane(is);
	arc.radius = readDouble(is);
	IVec scratch;
	scratch = readPoint3(is);
	scratch = readPoint3(is);
	scratch = readPoint3(is);
	
	arc.angle = readInterval(is);
	return arc;
    }
    
    
    public static Interval readInterval(InputStream is) throws IOException{
	Interval interval = new Interval();
	interval.v1 = readDouble(is);
	interval.v2 = readDouble(is);
	return interval;
    }
    
    public static Xform readXform(InputStream is) throws IOException{
	double[][] xf = new double[4][4];
	for(int i=0; i<4; i++)
	    for(int j=0; j<4; j++) xf[i][j] = readDouble(is);
	return new Xform(xf);
    }
    
    public static BoundingBox readBoundingBox(InputStream is)throws IOException{
	BoundingBox bb = new BoundingBox();
	bb.min.x = readDouble(is);
	bb.min.y = readDouble(is);
	bb.min.z = readDouble(is);
	bb.max.x = readDouble(is);
	bb.max.y = readDouble(is);
	bb.max.z = readDouble(is);
	return bb;
    }
    
    public static int[] readChunkVersion(InputStream is) throws IOException{
	return readChunkVersion(readByte(is));
    }
    
    public static int[] readChunkVersion(byte b){
	//IOut.p("b="+b); //
	//int minorVersion = b%16;
	int minorVersion = b&0x0F;
	int majorVersion = (b&0xF0)>>4;
	return new int[]{ majorVersion, minorVersion };
    }
    
    public static boolean isShortChunk(int header){
	//final int shortChunkMask = 0x80000000;
	//return (header & shortChunkMask) != 0;
	return (header & tcodeShort) != 0;
    }
    
    
    public static Chunk readChunk(InputStream is) throws IOException{
	//final int chunkHeaderLen = 4;
	//final int chunkBodyLen = 4;
	
	int header = readInt32(is);
	int body = readInt32(is);
	
	if(isShortChunk(header) ||
	   body==0 // added 20110924; some file use zero length in big chunk.
	   ) return new Chunk(header,body); // short chunk
	
	if(body<0){
	    IOut.err("length of content isn't positive: "+body);
	    return null;
	}
	else if(body > 1000000000){ // 1GB chunk // temporary fix: 20121122
	    IOut.err("length of content seems too big: "+body);
	    return null;
	}
	// if file is corrupted, body (byte length) would be enormous number
	// max int is 2,147,483,647
	byte[] content = read(is,body);
	
	return new Chunk(header,body,content); // big chunk
    }
    
    
    public static Chunk[] readChunkTable(Chunk chunk){
	return readChunkTable(chunk, tcodeEndOfTable);
    }
    
    public static Chunk[] readChunkTable(Chunk chunk, int endTCode){
	
	if(chunk==null){
	    IOut.err("input chunk is null"); //
	    return null;
	}
	if(chunk.content==null){
	    IOut.err("no content in the input chunk"); //
	    return null;
	}
	//IOut.p("table header = :"+hex(chunk.header)); //
	//IOut.p("table content:"); //
	//IOut.p(hex(chunk.content)); //
	
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	Chunk ck=null;
	ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	
	int i=1;
	do{
	    try{
		//IOut.p("reading chunk #"+i); //
		ck = readChunk(bais);
		//IOut.p("chunk #"+i+ " = "+ck); //
		
		if(ck==null){ IOut.err("no chunk is read"); return null; }
		else if(ck.header == endTCode) ck=null;
		
		i++;
	    }
	    catch(EOFException e){
		IOut.err("unexpected EOF of chunk");
		//e.printStackTrace();
		ck=null;
	    }
	    catch(IOException e){ e.printStackTrace(); ck=null; }
	    if(ck!=null) chunks.add(ck);
	}while(ck!=null);
	
	if(chunks.size()==0) return null;
	return chunks.toArray(new Chunk[chunks.size()]);
    }
    
    
    public static Chunk readNestedChunk(Chunk chunk){
	if(chunk==null){
	    IOut.err("input chunk is null"); //
	    return null;
	}
	if(chunk.content==null){
	    IOut.err("no content in the input chunk"); //
	    return null;
	}
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	try{
	    Chunk ck=readChunk(bais);
	    return ck;
	}
	catch(EOFException e){
	    IOut.err("unexpected EOF");
	    //e.printStackTrace();
	}
	catch(IOException e){ e.printStackTrace(); }
	return null;
    }
    
    /*
    public static Chunk[] readOpenNurbsClassChunks(Chunk chunk){
	if(chunk == null ||chunk.header!=tcodeOpenNurbsClass){
	    IOut.p("ERROR: no tcodeOpenNurbsClass block found");
	    return null;
	}
	//IOut.p("readObject: nested chunk : \n"+chunk); //
	return readChunkTable(chunk, tcodeOpenNurbsClassEnd);
    }
    
    public static UUID readOpenNurbsClassUUID(Chunk[] chunks){
	for(int i=0; i<chunks.length; i++){
	    if(chunks[i].header == tcodeOpenNurbsClassUUID){
		// read uuid
		return readUUID(chunks[i]);
	    }
	}
	IOut.p("ERROR: no UUID chunk is found"); //
	return null;
    }
    
    public static RhinoObject readOpenNurbsClassInstance(Chunk[] chunks){
	UUID uuid = readOpenNurbsClassUUID(chunks);
	if(uuid==null){
	    IOut.p("ERROR: no UUID is found"); //
	    return null;
	}
	Class<? extends RhinoObject> cls = ClassRegistry.get(uuid);
	if(cls==null){
	    IOut.p("ERROR: no class is found for UUID: "+uuid); //
	    return null;
	}
	
	RhinoObject object = null;
	try{
	    return cls.newInstance();
	}catch(Exception e){ e.printStackTrace(); }

	IOut.p("ERROR: failed to instantiate class "+cls);
	return null;
    }
    
    public static byte[] readOpenNurbsClassData(Chunk[] chunks){
	for(int i=0; i<chunks.length; i++){
	    if(chunks[i].header == tcodeOpenNurbsClassData){
		return chunks[i].content;
	    }
	}
	IOut.p("ERROR: no openNurbsClassData is found");
	return null;
    }
    */
    
    
    /** @return returns class data byte array
     */
    public RhinoObject readObject(InputStream is) throws IOException {
	return readObject(file,readChunk(is));
    }
    /** @return returns class data byte array
     */
    public RhinoObject readObject(Chunk chunk){ return readObject(file,chunk); }
    
    /** @return returns class data byte array
     */
    static public RhinoObject readObject(Rhino3dmFile context, InputStream is) throws IOException {
	return readObject(context, readChunk(is));
    }
    
    static public RhinoObject readObject(Rhino3dmFile context, Chunk chunk){
		
	if(chunk == null ||chunk.header!=tcodeOpenNurbsClass){
	    IOut.err("no tcodeOpenNurbsClass block found");
	    return null;
	}
	
	Chunk[] chunks = readChunkTable(chunk, tcodeOpenNurbsClassEnd);
	
	if(chunks == null){
	    IOut.err("chunk table is null");
	    return null;
	}
	
	try{
	    for(int i=0; i<chunks.length-1; i++){
		
		if(chunks[i].header == tcodeOpenNurbsClassUUID){
		    // read uuid
		    UUID uuid = readUUID(chunks[i]);
		    
		    Class<? extends RhinoObject> cls = ClassRegistry.get(uuid);
		    IOut.debug(10,"uuid class = "+cls); //
		    
		    if(cls!=null){
			RhinoObject object = null;
			try{
			    object = cls.newInstance();
			    //IOut.p("uuid class instance= "+object); //
			}catch(Exception e){ e.printStackTrace(); }
			
			if(object!=null &&
			   chunks[i+1].header == tcodeOpenNurbsClassData){
			    //return chunks[i+1].content;
			    //IOut.err("content: "+ hex(chunks[i+1].content)); //
			    
			    byte[] data = chunks[i+1].content;
			    object.read(context,data);
			    return object;
			}
		    }
		}
		else if(chunks[i].header == tcodeOpenNurbsClassUserData){ // user data
		    IOut.debug(20, "user data = \n"+chunks[i]);
		} // skipped
	    }
	}
	catch(EOFException e){
	    //IOut.err("End of stream");
	    //e.printStackTrace();
	}
	catch(IOException e){ e.printStackTrace(); }
	IOut.err("no UUID or ClassData is found"); //
	return null;
    }
    
    
    
    /*
    public static int readLittleEndianUnsignedInteger(byte[] buf){
	int i=0, d=0;
	//IOut.p("readLittelEndianUnsignedInteger: "+Chunk.hex(buf)); //
	for(int j=0; j<buf.length; j++){
	    //IOut.print(j+":"+(buf[j]&0xFF)+","); //
	    i += (buf[j]&0xFF)<<(8*j);
	}
	return i;
    }
    */
    
    
    
    /*
      @return two integer in an array; the first is major version, the second is minor. 
    */
    /*
    public static int[] readVersion(InputStream is)throws IOException{
	char c = readChar(is);
	int[] versions = new int[2];
	versions[0] = (c>>4)&0x0F;
	versions[1] = c&0x0F;
	return versions;
    }
    */
    
    public static int readCompressedBufferSize(InputStream is) throws IOException{
	return readSize(is); 
    }

    public static int readSize(InputStream is) throws IOException{ return readInt(is); }
    
    
    public static byte[] readCompressedBuffer(InputStream is, int len) throws IOException{
	
	int bufferCRC0=0;
	int bufferCRC1=0;
	byte method=0;
	
	bufferCRC0 = readInt(is);
	method = readByte(is);
	
	// uncompressed
	if(method==0) return readBytes(is,len);
	
	// compressed
	if(method==1) return readInflate(is,len);
	
	// no check of CRC
	
	// method is invalid value
	throw new IOException("method is invalid value: "+String.valueOf(method));
    }
    
    public static byte[] readInflate(InputStream is, int len)throws IOException{
	
	Chunk chunk = readChunk(is);
	if(chunk==null){ IOut.err("no chunk is read"); return null; }
	if(chunk.header != tcodeAnonymousChunk) throw new IOException("invalid type code = "+hex(chunk.header));	
	
	if(chunk.body <= 4)  throw new IOException("chunk length is too short = "+chunk.body);
	
	int inSize = chunk.body-4; // last 4 bytes for CRC
	
	Inflater inflater = new Inflater();
	inflater.setInput(chunk.content, 0, inSize);
	byte[] buf = new byte[len];
	try{ inflater.inflate(buf, 0, len); }
	catch(DataFormatException e){
	    throw new IOException("DataFormatException:"+e.getMessage());
	}
	return buf;
    }
    
    /**
       Reading an 3dm file and creating objects in a server.
       The main entry of the importer class.
       @param file An importing file object.
       @param server A server interface to put imported objects in. 
       @return True if reading is successful. Otherwise false.
    */
    public static boolean read(File file, IServerI server){
	try{
	    FileInputStream fis = new FileInputStream(file);
	    boolean retval = read(fis, server);
	    if(fis!=null) fis.close();
	    return retval;
	}catch(IOException e){ e.printStackTrace(); }
	return false;
    }
    
    public static boolean read(InputStream is, IServerI server){
	IRhino3dmImporter importer = new IRhino3dmImporter(is,server);
	importer.read();
	return true;
    }
    
    /**
       A method to have a main reading loop.
    */
    public void read(){
	
	file = new Rhino3dmFile();
	file.setServer(server);
	
	ClassRegistry.init(); // important
	
	objects = new ArrayList<IObject>();
	
	try{
	    readFileHeader();
	    
	    Chunk chunk=null;
	    
	    int chunkNum=0;
	    do{
		
		chunkNum++;
		IOut.debug(10,"chunk #"+chunkNum); //
		
		try{
		    chunk = readChunk(istream);
		    
		    if(chunk==null){ IOut.err("chunk is null"); }
		    else{
			
			switch(chunk.header){
			    
			case tcodeStartSection:
			    readStartSection(chunk);
			    break;
			case tcodePropertiesTable:
			    readPropertiesTable(chunk);
			    break;
			case tcodeSettingsTable:
			    readSettingsTable(chunk);
			    break;
			case tcodeBitmapTable:
			    readBitmapTable(chunk);
			    break;
			case tcodeTextureMappingTable:
			    readTextureMappingTable(chunk);
			    break;
			case tcodeMaterialTable:
			    readMaterialTable(chunk);
			    break;
			case tcodeLinetypeTable:
			    readLinetypeTable(chunk);
			    break;
			case tcodeLayerTable:
			    readLayerTable(chunk);
			    break;
			case tcodeGroupTable:
			    readGroupTable(chunk);
			    break;
			case tcodeFontTable:
			    readFontTable(chunk);
			break;
			case tcodeDimStyleTable:
			    readDimStyleTable(chunk);
			    break;
			case tcodeLightTable:
			    readLightTable(chunk);
			    break;
			case tcodeHatchPatternTable:
			    readHatchPatternTable(chunk);
			    break;
			case tcodeInstanceDefinitionTable:
			    readInstanceDefinitionTable(chunk);
			    break;
			case tcodeObjectTable:
			    readObjectTable(chunk);
			    break;
			case tcodeHistoryRecordTable:
			    readHistoryRecordTable(chunk);
			    break;
			case tcodeUserTable:
			    readUserDataTable(chunk);
			    break;
			case tcodeEndOfFile:
			    readEndMark(chunk);
			    break;
			    
			default:
			    IOut.err("unknown type code: "+hex(chunk.header));
			}
		    }
		}
		catch(EOFException e){
		    //IOut.debug(10,"EOF");
		    chunk=null;
		}
		catch(IOException e){ e.printStackTrace(); chunk=null; }
		
	    }while(chunk!=null);
	    
	    
	}catch(IOException e){ e.printStackTrace(); }
	
    }
    
    
    
    public void readStartSection(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readStartSection"); //
	int strlen = chunk.content.length;
	while(strlen>0 &&
	      (chunk.content[strlen-1] == 0x00 ||
	       chunk.content[strlen-1] == 0x1A) ) strlen--;
	
	String info = new String(chunk.content, 0, strlen);
	
	//IOut.p("information:");
	//IOut.p(info);
	
	file.startSection =  new StartSection(info);
    }
    
    
    public void readPropertiesTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readProperties"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	
	if(chunks==null){
	    IOut.err("no table item is found");
	    return; // ?
	}
	
	Properties prop = new Properties();
	
	for(Chunk ck:chunks){
	    
	    switch(ck.header){
	    case tcodePropertiesRevisionHistory:
		//prop.revisionHistory = ck.content;
		prop.setRevisionHistory(ck);
		//IOut.p("revisionHistory:");
		//IOut.p(asciiOrHex(ck.content));
		break;
	    case tcodePropertiesNotes:
		//prop.notes = ck.content;
		prop.setNotes(ck);
		//IOut.p("notes:");
		//IOut.p(asciiOrHex(ck.content));
		break;
	    case tcodePropertiesPreviewImage:
		//prop.previewImage = ck.content;
		prop.setPreviewImage(ck);
		//IOut.p("previewImage:");
		//IOut.p(asciiOrHex(ck.content));
		break;
	    case tcodePropertiesApplication:
		//prop.application = ck.content;
		prop.setApplication(ck);
		//IOut.p("application:");
		//IOut.p(asciiOrHex(ck.content));
		break;
	    case tcodePropertiesCompressedPreviewImage:
		//prop.compressedPreviewImage = ck.content;
		prop.setCompressedPreviewImage(ck);
		//IOut.p("compressedPreviewImage:");
		//IOut.p(asciiOrHex(ck.content));
		break;
	    case tcodePropertiesOpenNurbsVersion:
		//prop.openNurbsVersion = ck.body;
		prop.setOpenNurbsVersion(ck);
		file.openNurbsVersion = prop.openNurbsVersion; //
		//IOut.p("openNurbsVersion: "+prop.openNurbsVersion);
		break;
	    case tcodeEndOfTable:
		ck=null;
		break;
	    default:
		IOut.err("unknown type code: "+hex(ck.header));
	    }
	}
	
	file.properties = prop;
	
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	Properties prop = new Properties();
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		switch(ck.header){
		case tcodePropertiesRevisionHistory:
		    //prop.revisionHistory = ck.content;
		    prop.setRevisionHistory(ck);
		    IOut.p("revisionHistory:");
		    IOut.p(asciiOrHex(ck.content));
		    break;
		case tcodePropertiesNotes:
		    //prop.notes = ck.content;
		    prop.setNotes(ck);
		    IOut.p("notes:");
		    IOut.p(asciiOrHex(ck.content));
		    break;
		case tcodePropertiesPreviewImage:
		    //prop.previewImage = ck.content;
		    prop.setPreviewImage(ck);
		    IOut.p("previewImage:");
		    IOut.p(asciiOrHex(ck.content));
		    break;
		case tcodePropertiesApplication:
		    //prop.application = ck.content;
		    prop.setApplication(ck);
		    IOut.p("application:");
		    IOut.p(asciiOrHex(ck.content));
		    break;
		case tcodePropertiesCompressedPreviewImage:
		    //prop.compressedPreviewImage = ck.content;
		    prop.setCompressedPreviewImage(ck);
		    IOut.p("compressedPreviewImage:");
		    IOut.p(asciiOrHex(ck.content));
		    break;
		case tcodePropertiesOpenNurbsVersion:
		    //prop.openNurbsVersion = ck.body;
		    prop.setOpenNurbsVersion(ck);
		    IOut.p("openNurbsVersion: "+prop.openNurbsVersion);
		    break;
		case tcodeEndOfTable:
		    ck=null;
		    break;
		default:
		    IOut.p("Rhino3dImporter.readProperties: ERROR unknown type code: "+hex(ck.header));
		}
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readProperties: End"); //
	return null;
	*/
    }
    
    
    public void readSettingsTable(Chunk chunk) throws IOException{
	IOut.debug(10,"Rhino3dmImporter.readSettings"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	
	if(chunks==null){
	    IOut.err("no table item is found");
	    return; // ?
	}
	
	Settings settings = new Settings();
	
	for(Chunk ck:chunks){
	    
	    switch(ck.header){
	    case tcodeSettingsPluginList:
		settings.setPluginList(file,ck);
		break;
	    case tcodeSettingsUnitsAndTols:
		settings.setUnitsAndTols(file,ck);
		break;
	    case tcodeSettingsRenderMesh:
		settings.setRenderMesh(file,ck);
		break;
	    case tcodeSettingsAnalysisMesh:
		settings.setAnalysisMesh(file,ck);
		break;
	    case tcodeSettingsAnnotation:
		settings.setAnnotation(file,ck);
		break;
	    case tcodeSettingsNamedCPlaneList:
		settings.setNamedCPlaneList(file,ck);
		break;
	    case tcodeSettingsNamedViewList:
		settings.setNamedViewList(file,ck);
		break;
	    case tcodeSettingsViewList:
		settings.setViewList(file,ck);
		break;
	    case tcodeSettings_Never_Use_This:
		// do nothing
		break;
	    case tcodeSettingsCurrentLayerIndex:
		settings.setCurrentLayerIndex(file,ck);
		break;
	    case tcodeSettingsCurrentFontIndex:
		settings.setCurrentFontIndex(file,ck);
		break;
	    case tcodeSettingsCurrentDimStyleIndex:
		settings.setCurrentDimStyleIndex(file,ck);
		break;
	    case tcodeSettingsCurrentMaterialIndex:
		settings.setCurrentMaterialIndex(file,ck);
		break;
	    case tcodeSettingsCurrentColor:
		settings.setCurrentColor(file,ck);
		break;
	    case tcodeSettingsCurrentWireDensity:
		settings.setCurrentWireDensity(file,ck);
		break;
	    case tcodeSettingsRender:
		settings.setRender(file,ck);
		break;
	    case tcodeSettingsGridDefaults:
		settings.setGridDefaults(file,ck);
		break;
	    case tcodeSettingsModelURL:
		settings.setModelURL(file,ck);
		break;
	    case tcodeSettingsAttributes:
		settings.setAttributes(file,ck);
		break;
	    default:
		IOut.err("unknown type code: "+hex(ck.header));
	    }
	    
	}
	
	file.settings = settings;
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	
	Settings settings = new Settings();
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		
		switch(ck.header){
		case tcodeSettingsPluginList:
		    settings.setPluginList(ck);
		    break;
		case tcodeSettingsUnitStandTols:
		    settings.setUnitStandTols(ck);
		    break;
		case tcodeSettingsRenderMesh:
		    settings.setRenderMesh(ck);
		    break;
		case tcodeSettingsAnalysisMesh:
		    settings.setAnalysisMesh(ck);
		    break;
		case tcodeSettingsAnnotation:
		    settings.setAnnotation(ck);
		    break;
		case tcodeSettingsNamedCPlaneList:
		    settings.setNamedCPlaneList(ck);
		    break;
		case tcodeSettingsNamedViewList:
		    settings.setNamedViewList(ck);
		    break;
		case tcodeSettingsViewList:
		    settings.setViewList(ck);
		    break;
		case tcodeSettings_Never_Use_This:
		    // do nothing
		    break;
		case tcodeSettingsCurrentLayerIndex:
		    settings.setCurrentLayerIndex(ck);
		    break;
		case tcodeSettingsCurrentFontIndex:
		    settings.setCurrentFontIndex(ck);
		    break;
		case tcodeSettingsCurrentDimStyleIndex:
		    settings.setCurrentDimStyleIndex(ck);
		    break;
		case tcodeSettingsCurrentMaterialIndex:
		    settings.setCurrentMaterialIndex(ck);
		    break;
		case tcodeSettingsCurrentColor:
		    settings.setCurrentColor(ck);
		    break;
		case tcodeSettingsCurrentWireDensity:
		    settings.setCurrentWireDensity(ck);
		    break;
		case tcodeSettingsRender:
		    settings.setRender(ck);
		    break;
		case tcodeSettingsGridDefaults:
		    settings.setGridDefaults(ck);
		    break;
		case tcodeSettingsModelURL:
		    settings.setModelURL(ck);
		    break;
		case tcodeSettingsAttributes:
		    settings.setAttributes(ck);
		    break;
		default:
		    IOut.p("Rhino3dImporter.readSettings: ERROR unknown type code: "+hex(ck.header));
		}
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	
	IOut.p("Rhino3dmImporter.readSettings: End"); //
	return null;
	*/
    }
    
    public void readBitmapTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readBitmapTable"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	
	if(chunks==null) return; // no bitmap
	
	ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
	
	for(Chunk c:chunks){
	    Bitmap bm = readBitmap(c);
	    if(bm!=null) bitmaps.add(bm);
	}
	
	if(bitmaps.size()>0)
	    file.bitmaps = bitmaps.toArray(new Bitmap[bitmaps.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		
		IOut.p("Bitmap data: "+ck); //
		
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readBitmap: End"); //
	return null;
	*/
    }
    
    public Bitmap readBitmap(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readBitmap"); //
	
	if(file.version==1) return null;
	
	Chunk c = readNestedChunk(chunk);
	if(c.header != tcodeBitmapRecord) return null;
	
	//readObject(c);
	
	RhinoObject robj = readObject(readNestedChunk(c));
	if(robj instanceof Bitmap){ return (Bitmap)robj; }
	
	IOut.err("got an instance of wrong class : "+robj);
	return null;
	
	//Chunk[] cks = readOpenNurbsClassChunks(readNestedChunk(c));
	//return readBitmap(readOpenNurbsClassInstance(cks),readOpenNurbsClassData(cks));
    }
    
    
    
    public void readTextureMappingTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readTextureMappingTable"); //
	Chunk[] chunks = readChunkTable(chunk);
	
	if(chunks==null) return; // no texture
	
	ArrayList<TextureMapping> textures = new ArrayList<TextureMapping>();
	
	for(Chunk c:chunks){
	    TextureMapping tm = readTextureMapping(c);
	    if(tm!=null) textures.add(tm);
	}
	
	if(textures.size()>0)
	    file.textureMappings = textures.toArray(new TextureMapping[textures.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		
		IOut.p("Texture data: "+ck); //
		
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readTextureMapping: End"); //
	return null;
	*/
    }
    
    public TextureMapping readTextureMapping(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readTextureMapping"); //
	
	//if(file.version==1) return null;
	
	Chunk c = readNestedChunk(chunk);
	
	return null;
    }
    
    public void readMaterialTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readMaterialTable"); //
	Chunk[] chunks = readChunkTable(chunk);
	
	if(chunks==null) return; // no texture
	
	ArrayList<Material> materials = new ArrayList<Material>();
	
	for(Chunk c:chunks){
	    Material m = readMaterial(c);
	    if(m!=null) materials.add(m);
	}
	
	IOut.debug(10,materials.size()+" materials imported"); 
	
	if(materials.size()>0) file.materials = materials;
	//file.materials = materials.toArray(new Material[materials.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		
		IOut.p("Material data: "+ck); //
		
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readMaterial: End"); //
	return null;
	*/
    }
    
    public Material readMaterial(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readMaterial"); //
	
	if(file.version==1){
	    IOut.err("Material of Rhino version 1 3dm file is not supported");
	    return null;
	}
	
	if(chunk.header != tcodeMaterialRecord){
	    IOut.err("wrong header of chunk : "+hex(chunk.header));
	    return null;
	}
	
	RhinoObject robj = readObject(readNestedChunk(chunk));
	if(robj instanceof Material){
	    return (Material) robj;
	}
	
	IOut.err("wrong type of object found " + robj); //
	return null;
    }
    
    
    public void readLinetypeTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readLinetypeTable"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	
	if(chunks==null) return; // no texture
	
	ArrayList<Linetype> linetypes = new ArrayList<Linetype>();
	
	for(Chunk c:chunks){
	    Linetype l = readLinetype(c);
	    if(l!=null) linetypes.add(l);
	}
	
	if(linetypes.size()>0)
	    file.linetypes = linetypes.toArray(new Linetype[linetypes.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		
		IOut.p("Linetype data: "+ck); //
		
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readLinetype: End"); //
	return null;
	*/
    }
    
    public Linetype readLinetype(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readLinetype"); //
	
	//if(file.version==1) return null;
	
	Chunk c = readNestedChunk(chunk);
	
	return null;
    }
    
    
    public void readLayerTable(Chunk chunk){
	
	IOut.debug(10,"Rhino3dmImporter.readLayerTable"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	
	if(chunks==null) return; // no texture
	
	ArrayList<Layer> layers = new ArrayList<Layer>();
	
	for(Chunk c:chunks){
	    Layer l = readLayer(c);
	    if(l!=null) layers.add(l);
	}
	
	if(layers.size()>0)
	    file.layers = layers.toArray(new Layer[layers.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	
	ArrayList<Layer> layers=new ArrayList<Layer>();
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		
		IOut.p("Layer data: "+ck); //
		
		if(ck.header == tcodeEndOfTable){ ck = null; }
		else{
		    
		    ByteArrayInputStream bais2 = new ByteArrayInputStream(ck.content);
		    Chunk ck2 = readChunk(bais2);
		    
		    Layer layer = new Layer();
		    layer.read(ck2);
		    layers.add(layer);
		    
		    IOut.p(layer); //
		}
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readLayer: End"); //
	
	if(layers.size()>0) return layers.toArray(new Layer[layers.size()]); //
	return null;
	*/
    }
    
    public Layer readLayer(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readLayer"); //
	
	if(file.version==1){
	    IOut.err("version 1 rhino file is not supported"); 
	    return null;
	}
	
	if(chunk.header != tcodeLayerRecord){
	    IOut.err("wrong type code : "+hex(chunk.header));
	    return null;
	}
	
	//Chunk c = readNestedChunk(chunk);
	//Chunk[] c = readChunkTable(chunk);
	//IOut.p("readLayer: table size = "+c.length); //
	//IOut.p("Rhino3dmImporter.readLayer; nested chunk = "+c); //
	
	//byte[] data = readObject(chunk);
	//byte[] data = readObject(readNestedChunk(chunk));
	
	//Chunk[] cks = readOpenNurbsClassChunks(readNestedChunk(chunk));
	//return readLayer(readOpenNurbsClassInstance(cks),readOpenNurbsClassData(cks));
	
	RhinoObject robj = readObject(readNestedChunk(chunk));
	
	if(robj instanceof Layer){
	    robj.createIObject(file,server);
	    return (Layer)robj;
	}
	IOut.err("readLayer: got an instance of wrong class : "+robj);
	return null;
	
	//try{ return readLayer(data);
	//}catch(IOException e){ e.printStackTrace(); }
	//return null;
	
	//RhinoObject robj = readOpenNurbsClassInstance(cks);
	//byte[] data = readOpenNurbsClassData(cks);
	
	
    }
    
    /*
    public Layer readLayer(RhinoObject robj, byte[] data)throws IOException{
	//public Layer readLayer(byte[] data) throws IOException {
	if(robj==null){
	    IOut.p("ERROR: robj is null");
	    return null;
	}
	if(data==null){
	    IOut.p("ERROR: data is null");
	    return null;
	}
	if( ! (robj instanceof Layer)){
	    IOut.p("ERROR: got an instance of wrong class : "+robj);
	    return null;
	}
	IOut.p("readLayer: class data : \n"+hex(data)); //
	//Layer layer = new Layer();
	Layer layer = (Layer)robj;
	ByteArrayInputStream bais = new ByteArrayInputStream(data);
	int[] version = readChunkVersion(bais);
	int majorVersion = version[0];
	int minorVersion = version[1];
	
	IOut.p("Layer.read: majorVersion = "+majorVersion);
	IOut.p("Layer.read: minorVersion = "+minorVersion);
	if(majorVersion==1){
	    int mode = readInt(bais);
	    switch(mode){
	    case 0:
		layer.visible=true;
		layer.locked=false;
		break;
	    case 1:
		layer.visible=false;
		layer.locked=false;
		break;
	    case 2:
		layer.visible=true;
		layer.locked=true;
		break;
	    default:
		layer.visible=true;
		layer.locked=false;
		break;
	    }
	}
	layer.layerIndex = readInt(bais);
	layer.igesLevel = readInt(bais);
	layer.materialIndex = readInt(bais);
	int obsolete_value1 = readInt(bais);
	layer.color = readColor(bais);
	// skipping obsolete fields
	readShort(bais);
	readShort(bais);
	readDouble(bais);
	readDouble(bais);
	layer.name = readString(bais);
	if(minorVersion>=1){
	    layer.visible = readBool(bais);
	    if(minorVersion>=2){
		layer.linetypeIndex = readInt(bais);
		if(minorVersion>=3){
		    layer.plotColor = readColor(bais);
		    layer.plotWeightMm = readDouble(bais);
		    if(minorVersion>=4){
			layer.locked = readBool(bais);
			if(minorVersion>=5){
			    layer.layerId = readUUID(bais);
			    if(minorVersion>=6 && file.openNurbsVersion > 200505110){
				layer.parentLayerId = readUUID(bais);
				layer.expanded = readBool(bais);
			    }
			    if(minorVersion>=7){
				// render attributes
				layer.renderingAttributes = readRenderingAttributes(bais);
				if(minorVersion>=8){
				    layer.displayMaterialId = readUUID(bais);
				}
			    }
			}
		    }
		}
	    }
	}
	IOut.p("readLayer: layer : \n"+layer); //
	return layer;
    }
    */
    
    /*
    public RenderingAttributes readRenderingAttributes(InputStream is){
	try{
	    Chunk chunk = readChunk(is);
	    if(chunk.content==null){
		IOut.p("readRenderingAttributes: ERROR: no chunk content"); //
		return null;
	    }
	    ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	    RenderingAttributes renderingAttributes = new RenderingAttributes();
	    int majorVersion = readInt(bais);
	    int minorVersion = readInt(bais);
	    if(majorVersion!=1) return null;
	    int count = readInt(bais);
	    for(int i=0; i<count; i++){
		//MaterialRef mref = new MaterialRef();
		// skip reading for now
		
		MaterialRef mref = readMaterialRef(bais);
		renderingAttributes.materials.add(mref);
	    }
	    IOut.p("readRenderingAttributes: majorVersion = "+majorVersion+", minorVersion = "+minorVersion +", count = "+count); //
	    
	    return renderingAttributes;
	}catch(IOException e){ e.printStackTrace(); }
	return null;
    }
    */
    
    /*
    public MaterialRef readMaterialRef(InputStream is) throws IOException{
	Chunk ck = readChunk(is);
	if(ck.content==null){
	    IOut.p("readMaterialRef: ERROR: chunk content is null"); //
	    return null;
	}
	ByteArrayInputStream bais = new ByteArrayInputStream(ck.content);
	int majorVersion = readInt(bais);
	int minorVersion = readInt(bais);
	if(majorVersion!=1){
	    IOut.p("readMaterialRef: ERROR: invalid major version : "+majorVersion); //
	    return null;
	}
	MaterialRef mref = new MaterialRef();
	mref.pluginId = readUUID(bais);
	mref.materialId = readUUID(bais);
	// skip obsolete mappingChannel data
	int mappingChannelCount = readInt(bais);
	for(int i=0; i<mappingChannelCount; i++){
	    readMappingChannel(bais);
	}
	if(minorVersion>=1){
	    mref.materialBackfaceId = readUUID(bais);
	    // materialSource should be read
	    readInt(bais);
	}
	return mref;
    }
    */
    
    /*
    public MappingChannel readMappingChannel(InputStream is) throws IOException{
	Chunk ck = readChunk(is);
	if(ck.content==null){
	    IOut.p("readMappingChannel: ERROR: chunk content is null"); //
	    return null;
	}
	ByteArrayInputStream bais = new ByteArrayInputStream(ck.content);
	int majorVersion = readInt(bais);
	int minorVersion = readInt(bais);
	if(majorVersion!=1){
	    IOut.p("readMappingChannel: ERROR: invalid majorVersion : "+majorVersion);
	    return null;
	}
	MappingChannel mchannel = new MappingChannel();
	mchannel.mappingChannelId = readInt(bais);
	mchannel.mappingId = readUUID(bais);
	if(minorVersion >= 1){
	    mchannel.objectXform = readXform(bais);
	    if(file.openNurbsVersion < 200610030 && mchannel.objectXform.isZero()){
		mchannel.objectXform.identity();
	    }
	}
	return mchannel;
    }
    */
    
    public void readGroupTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readGroupTable"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	
	if(chunks==null) return; // no texture
	
	ArrayList<Group> groups = new ArrayList<Group>();
	
	for(Chunk c:chunks){
	    Group g = readGroup(c);
	    if(g!=null) groups.add(g);
	}
	
	if(groups.size()>0)
	    file.groups = groups.toArray(new Group[groups.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		IOut.p("Group data: "+ck); //
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readGroup: End"); //
	return null;
	*/
    }
    
    public Group readGroup(Chunk chunk){
	return null;
    }
    
    
    public void readFontTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readFontTable"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	if(chunks==null) return; // no texture
	
	ArrayList<Font> fonts = new ArrayList<Font>();
	
	for(Chunk c:chunks){
	    Font f = readFont(c);
	    if(f!=null) fonts.add(f);
	}
	
	if(fonts.size()>0) file.fonts = fonts.toArray(new Font[fonts.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		IOut.p("Font data: "+ck); //
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readFont: End"); //
	return null;
	*/
    }
    
    public Font readFont(Chunk chunk){
	//IOut.p(chunk); //
	
	if(file.version<=2){
	    IOut.err("version is older than 2 : " + file.version);
	    return null;
	}
	if(file.openNurbsVersion < 200109180){
	    IOut.err("Open Nurbs version is older: " + file.openNurbsVersion);
	    return null;
	}
	
	if(chunk.header != tcodeFontRecord){
	    IOut.err("wrong header typecode: "+hex(chunk.header));
	    return null;
	}
	
	RhinoObject robj = readObject(readNestedChunk(chunk));
	
	if(robj instanceof Font){ return (Font)robj; }
	
	IOut.err("got an instance of wrong class : "+robj);
	return null;
    }
    
    public void readDimStyleTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readDimStyleTable"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	if(chunks==null) return; // no dimstyle
	
	ArrayList<DimStyle> dimStyles = new ArrayList<DimStyle>();
	
	for(Chunk c:chunks){
	    DimStyle ds = readDimStyle(c);
	    if(ds!=null) dimStyles.add(ds);
	}
	
	if(dimStyles.size()>0) file.dimStyles = dimStyles.toArray(new DimStyle[dimStyles.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		IOut.p("DimStyle data: "+ck); //
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readDimStyle: End"); //
	return null;
	*/
    }

    public DimStyle readDimStyle(Chunk chunk){
	return null; 
    }
    
    public void readLightTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readLightTable"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	if(chunks==null) return; // no dimstyle
	
	ArrayList<Light> lights = new ArrayList<Light>();
	
	for(Chunk c:chunks){
	    Light l = readLight(c);
	    if(l!=null) lights.add(l);
	}
	
	if(lights.size()>0) file.lights = lights.toArray(new Light[lights.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);

		IOut.p("Light data: "+ck); //
		
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readLight: End"); //
	return null;
	*/
    }

    public Light readLight(Chunk c){
	return null;
    }
    
    public void readHatchPatternTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readHatchPatternTable"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	if(chunks==null) return; // no dimstyle
	
	ArrayList<HatchPattern> hatches = new ArrayList<HatchPattern>();
	
	for(Chunk c:chunks){
	    HatchPattern h = readHatchPattern(c);
	    if(h!=null) hatches.add(h);
	}
	
	if(hatches.size()>0) file.hatchPatterns = hatches.toArray(new HatchPattern[hatches.size()]);
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);

		IOut.p("Hatch data: "+ck); //
		
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readHatchPattern: End"); //
	return null;
	*/
    }
    
    public HatchPattern readHatchPattern(Chunk c){
	return null;
    }
    
    public void readInstanceDefinitionTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readInstanceDefinitionTable"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	if(chunks==null) return; // no dimstyle
	
	ArrayList<InstanceDefinition> instances = new ArrayList<InstanceDefinition>();
	
	for(Chunk c:chunks){
	    InstanceDefinition id = readInstanceDefinition(c);
	    if(id!=null) instances.add(id);
	}
	
	if(instances.size()>0) file.instanceDefinitions = instances.toArray(new InstanceDefinition[instances.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);

		IOut.p("InstanceDefinition data: "+ck); //
		
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readInstanceDefinition: End"); //
	return null;
	*/
    }
        
    public InstanceDefinition readInstanceDefinition(Chunk chunk){ return null; }
    
    
    public void readObjectTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readObjectTable"); //
	
	Chunk[] chunks = readChunkTable(chunk);
	if(chunks==null) return; // no dimstyle
	
	ArrayList<RhinoObject> robjects = new ArrayList<RhinoObject>();
	
	IOut.debug(10,"num of rhino objects : "+chunks.length);
	
	int i=0;
	for(Chunk c:chunks){
	    if(i>0 && i%100==0) IOut.debug(1,(++i)+"/"+chunks.length);
	    //IOut.p("object #"+(++i));
	    //IOut.p(c);
	    
	    RhinoObject obj = readRhinoObject(c);
	    if(obj!=null) robjects.add(obj);
	}
	
	if(robjects.size()>0) file.rhinoObjects = robjects.toArray(new RhinoObject[robjects.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		IOut.p("Object data: "+ck); //
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readObject: End"); //
	return null;
	*/
    }
    
    public RhinoObject readRhinoObject(Chunk chunk){
	
	if(file.version==1){
	    return null; // version 1 not supported
	}
	
	if(chunk.header != tcodeObjectRecord){
	    IOut.err("chunk header is not tcodeObjectRecord\n" + chunk); //
	    return null;
	}
	
	Chunk[] chunks = readChunkTable(chunk, tcodeObjectRecordEnd);
	if(chunks==null){
	    IOut.err("no table item is found");
	    return null; // ?
	}
	
	
	//IOut.p("chunk table num = "+chunks.length);
	//for(int i=0; i<chunks.length; i++) IOut.p("chunks["+i+"] = \n"+chunks[i]); //
	
	if(chunks[0].header != tcodeObjectRecordType){
	    IOut.err("chunk header is not tcodeObjectRecordType\n" + chunks[0]); //
	    return null;
	}
	
	if(chunks.length<2){
	    IOut.err("object data is missing");
	    return null;
	}
	
	//byte[] data = readObject(chunks[1]);
	//IOut.p("rhino object data = "+hex(data));
	
	RhinoObject object = readObject(chunks[1]);
	
	if(object==null) return null;
	
	if(chunks.length>=3){
	    for(int i=2; i<chunks.length; i++){
		if(chunks[i].header == tcodeObjectRecordAttributes){ // object attributes
		    ObjectAttributes attributes = new ObjectAttributes();
		    try{ attributes.read(file,chunks[i].content); }
		    catch(IOException e){ e.printStackTrace(); }
		    
		    //IOut.p("attributes = "); //
		    //IOut.p(attributes); //
		    
		    object.setAttributes(attributes);
		}
		else if(chunks[i].header == tcodeObjectRecordAttributesUserData){
		    readObjectUserData(chunks[i]);
		}
	    }
	}
	
	// instantiate here
	IObject elem = object.createIObject(file,server);
	if(elem!=null){
	    object.setAttributesToIObject(file,elem);
	    objects.add(elem);
	}
	
	return object;
    }

    public void readObjectUserData(Chunk chunk){
	
	try{
	    ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	    
	    Chunk chunk2 = readChunk(bais);
	    
	    ByteArrayInputStream bais2 = new ByteArrayInputStream(chunk2.content);
	    
	    int[] version = readChunkVersion(bais2);
	    int majorVersion = version[0];
	    int minorVersion = version[1];
	    
	    ByteArrayInputStream headerBais = bais2;
	    if(majorVersion==2){
		Chunk headerChunk = readChunk(bais2);
		headerBais = new ByteArrayInputStream(headerChunk.content);
	    }
	    
	    UUID classID = readUUID(headerBais);
	    UUID itemID = readUUID(headerBais);
	    int copyCount = readInt(headerBais);
	    Xform xform = readXform(headerBais);
	    
	    IOut.err("classID = "+classID);
	    IOut.err("itemID = "+itemID);
	    IOut.err("copyCount = "+copyCount);
	    IOut.err("xform = "+xform);
	    
	    if(majorVersion==2){
		if(minorVersion>=1){
		    UUID appUUID = readUUID(headerBais);
		    IOut.err("appID = "+appUUID);
		    if(minorVersion>=2){
			boolean isUnknownData = readBool(headerBais);
			int version3dm = readInt(headerBais);
			int versionOpenNurbs = readInt(headerBais);
			
			IOut.err("unknown data = "+isUnknownData);
			IOut.err("3dm version = "+version3dm);
			IOut.err("ON version = "+versionOpenNurbs);
		    }
		}
	    }
	    
	    Chunk anonymousDataChunk = readChunk(bais2);
	    
	    IOut.err("anonymousDataChunk : "+anonymousDataChunk);
	    
	}
	catch(IOException e){ e.printStackTrace(); }
	
    }
    
    
    public void readHistoryRecordTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readHistoryRecordTable"); //
	Chunk[] chunks = readChunkTable(chunk);
	if(chunks==null) return; // no dimstyle
	
	ArrayList<HistoryRecord> historyRecords = new ArrayList<HistoryRecord>();
	
	for(Chunk c:chunks){
	    HistoryRecord h = readHistoryRecord(c);
	    if(h!=null) historyRecords.add(h);
	}
	
	if(historyRecords.size()>0) file.historyRecords = historyRecords.toArray(new HistoryRecord[historyRecords.size()]);
	
	/*
	ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	Chunk ck=null;
	do{
	    try{
		ck = readChunk(bais);
		IOut.p("History data: "+ck); //
		if(ck.header == tcodeEndOfTable){ ck = null; }
	    }
	    catch(EOFException e){ ck=null; }
	    catch(IOException e){ ck=null; e.printStackTrace(); }
	}while(ck!=null);
	IOut.p("Rhino3dmImporter.readHistoryRecord: End"); //
	return null;
	*/
    }

    public HistoryRecord readHistoryRecord(Chunk chunk){
	return null;
    }
    
    public void readUserDataTable(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readUserDataTable"); //
	Chunk[] chunks = readChunkTable(chunk);
	if(chunks==null) return; // no user data
	
	IOut.debug(10,chunks.length+" chunks"); //
	
	ArrayList<UserData> userData = new ArrayList<UserData>();
	
	for(Chunk c:chunks){
	    UserData ud = readUserData(c);
	    if(ud!=null) userData.add(ud);
	}
	
	if(userData.size()>0) file.userData = userData.toArray(new UserData[userData.size()]);
	/*
	  80800020
	  14000000
	  4818A83A
	  3556BB41
	  ABDB0EC0
	  69BC5519
	  36D91A7D
	  81000020
	  00000000
	  FFFFFFFF00000000
	*/
	
    }
    
    public UserData readUserData(Chunk chunk){
	
	IOut.debug(20, chunk.toString()); //
	
	return null;
    }
    
    public void readEndMark(Chunk chunk){
	IOut.debug(10,"Rhino3dmImporter.readEndMark"); //
	
	try{
	    ByteArrayInputStream bais = new ByteArrayInputStream(chunk.content);
	    if(file.sizeOfChunkLength()==4){
		int fileLength = readInt(bais);
		//IOut.debug(10,"file length = "+fileLength);
	    }
	    else{
		long fileLength = readLong(bais);
		//IOut.debug(10,"file length = "+fileLength);
	    }
	}catch(IOException e){ e.printStackTrace(); }
    }
    
    
    
    public static void main(String[] args){
	
	IOut.debugLevel(-1); //
	
	if(args.length>0){
	    IOut.p("opening "+args[0]);
	    //BufferedReader reader=null;
	    FileInputStream fis=null;
	    try{
		//reader = new BufferedReader(new FileReader(args[0]));
		fis = new FileInputStream(args[0]);
	    }catch(Exception e){ e.printStackTrace(); }
	    //IRhino3dmImporter importer = new IRhino3dmImporter(reader);
	    IG.init();
	    IRhino3dmImporter importer = new IRhino3dmImporter(fis,IG.current());
	    importer.read();
	    try{ if(fis!=null) fis.close(); }catch(Exception e){ e.printStackTrace(); }
	}
    }
    
    
}

