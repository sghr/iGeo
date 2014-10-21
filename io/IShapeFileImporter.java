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

package igeo.io;

import java.io.*;
import java.util.ArrayList;
//import java.nio.ByteBuffer;
import java.text.*;

import igeo.*;
import igeo.gui.IView;

/**
   ESRI Shapefile Importer
   
   @author Satoru Sugihara
*/


public class IShapeFileImporter{
    
    static public class Constraint{
	public boolean validate(double x, double y, double z){
	    return true;
	}
    }
    
    static public class GridConstraint extends Constraint{
	public boolean validate(double x, double y, double z){
	    double origx = 501002.66259577964;
	    double origy = 4793122.462032901;
	    //double origz = 8.5600004196167;
	    
	    double interval = 4.99785186;
	    
	    int xidx = (int)(Math.abs(x-origx)/interval+0.5);
	    int yidx = (int)(Math.abs(y-origy)/interval+0.5);
	    
	    int gridSpacing=10; //4;
	    
	    if(xidx%gridSpacing==0 && yidx%gridSpacing==0){
		return true;
	    }
	    return false;
	}
    }
    
    
    //static Constraint constraint=new GridConstraint(); //null;
    static Constraint constraint=null;

    
    
    static public byte[] copy(byte[] buf, int idx, int len){
	byte[] buf2 = new byte[len];
	for(int i=0; i<len; i++){
	    buf2[i] = buf[i+idx];
	}
	return buf2;
    }
    
    static public boolean checkBufLen(byte[] buf, int idx, int len){
	if(buf.length-idx<len){
	    IG.err("too short buffer (buf.len="+buf.length+", idx="+idx+", len="+len);
	    return false;
	}
	return true;
    }
    
    static public long readIntBE(InputStream is) throws IOException{
	int i1 = is.read();
	//if(i1<0) return 0;
	int i2 = is.read();
	//if(i2<0) return 0;
	int i3 = is.read();
	//if(i3<0) return 0;
	int i4 = is.read();
	if(i4<0) return -1;
	return (long)( (((long)(i1&0xff))<<24) | (((long)(i2&0xff))<<16) | (((long)(i3&0xff))<<8) | (long)(i4&0xff));
    }
    
    static public int readShortBE(InputStream is) throws IOException{
	int i1 = is.read();
	//if(i1<0) return 0;
	int i2 = is.read();
	if(i2<0) return -1;
	return (int)( ((i1&0xff)<<8) | (i2&0xff) );
    }
    
    
    /** big endian integer */
    static public long readIntBE(byte[] buf, int idx){
	// 4 byte
	if(!checkBufLen(buf,idx,4)){
	    IG.err();
	    return 0;
	}
	return (long)(((long)(buf[idx]<<24))&0xff000000 | ((long)(buf[idx+1]<<16))&0xff0000 | ((long)(buf[idx+2]<<8))&0xff00 | ((long)buf[idx+3])&0xff);
    }

    /** little endian integer */
    static public long readIntLE(InputStream is) throws IOException{
	int i1 = is.read();
	//if(i1<0) return 0;
	int i2 = is.read();
	//if(i2<0) return 0;
	int i3 = is.read();
	//if(i3<0) return 0;
	int i4 = is.read();
	if(i4<0) return -1;
	return (long)( (((long)(i4&0xff))<<24) | (((long)(i3&0xff))<<16) | (((long)(i2&0xff))<<8) | (long)(i1&0xff));
    }
    
    /** little endian integer */
    static public int readShortLE(InputStream is) throws IOException{
	int i1 = is.read();
	//if(i1<0) return 0;
	int i2 = is.read();
	if(i2<0) return -1;
	return (int)(((i2&0xff)<<8) | (i1&0xff));
    }
    
    
    /** little endian integer */
    static public long readIntLE(byte[] buf, int idx){
	// 4 byte
	if(!checkBufLen(buf,idx,4)){
	    IG.err();
	    return 0;
	}
	return (long)(((long)(buf[idx+3]<<24))&0xff000000 | (((long)(buf[idx+2])<<16))&0xff0000 | ((long)(buf[idx+1]<<8))&0xff00 | (long)(buf[idx])&0xff);
    }
    
    static public double readDoubleLE(InputStream is) throws IOException{
	int b1 = is.read();
	//if(b1<0) return 0;
	int b2 = is.read();
	//if(b2<0) return 0;
	int b3 = is.read();
	//if(b3<0) return 0;
	int b4 = is.read();
	//if(b4<0) return 0;
	int b5 = is.read();
	//if(b5<0) return 0;
	int b6 = is.read();
	//if(b6<0) return 0;
	int b7 = is.read();
	//if(b7<0) return 0;
	int b8 = is.read();
	if(b8<0) return 0;
	
	long longVal = (long)(( (long)(b8&0xff)<<(8*7)) |
			      ( (long)(b7&0xff)<<(8*6)) |
			      ( (long)(b6&0xff)<<(8*5)) |
			      ( (long)(b5&0xff)<<(8*4)) |
			      ( (long)(b4&0xff)<<(8*3)) |
			      ( (long)(b3&0xff)<<(8*2)) |
			      ( (long)(b2&0xff)<<(8*1)) |
			      (long)(b1&0xff));
	return Double.longBitsToDouble(longVal);
    }
    
    static public double readDoubleLE(byte[] buf, int idx){
	if(!checkBufLen(buf,idx,8)){
	    IG.err();
	    return 0;
	}
	long longVal = (long)(( (long)(buf[idx+7]&0xff)<<(8*7)) |
			      ( (long)(buf[idx+6]&0xff)<<(8*6)) |
			      ( (long)(buf[idx+5]&0xff)<<(8*5)) |
			      ( (long)(buf[idx+4]&0xff)<<(8*4)) |
			      ( (long)(buf[idx+3]&0xff)<<(8*3)) |
			      ( (long)(buf[idx+2]&0xff)<<(8*2)) |
			      ( (long)(buf[idx+1]&0xff)<<(8*1)) |
			      (long)(buf[idx]&0xff));
	return Double.longBitsToDouble(longVal);
    }
    
    /*
    static public double readDoubleLE2(byte[] buf, int idx){
	byte[] buf2 = copy(buf, idx, 8);
	return ByteBuffer.wrap(buf2).getDouble();
    }
    */
    
    static public void readSHPHeader(InputStream is){
	
	byte[] buf = new byte[100];
	try{
	    is.read(buf,0,100);
	}catch(IOException e){ e.printStackTrace(); }
	
	long fileCode = readIntBE(buf, 0);
	long fileLen = readIntBE(buf, 24);
	long fileVersion = readIntLE(buf, 28);
	
	long shapeType = readIntLE(buf, 32);
	
	//IG.p("fileCode = "+fileCode);
	//IG.p("fileLen = "+fileLen);
	//IG.p("fileVersion = "+fileVersion);
	//IG.p("shapetype = "+shapeType);
	
	/*
	double minx = readDoubleLE(buf, 36);
	double miny = readDoubleLE(buf, 44);
	double maxx = readDoubleLE(buf, 52);
	double maxy = readDoubleLE(buf, 60);
	double minz = readDoubleLE(buf, 68);
	double maxz = readDoubleLE(buf, 76);
	double minm = readDoubleLE(buf, 84);
	double maxm = readDoubleLE(buf, 92);

	IG.p("minx = "+minx);
	IG.p("miny = "+miny);
	IG.p("minz = "+minz);
	
	IG.p("maxx = "+maxx);
	IG.p("maxy = "+maxy);
	IG.p("maxz = "+maxz);
	
	IG.p("minm = "+minm);
	IG.p("maxm = "+maxm);
	*/
	
	//for(int i=0; i<100; i++){
	//   IG.p("byte "+i+": "+buf[i]);
	//}
	
    }

    
    /*
    static public boolean readSHPRecord(InputStream is){
	byte[] buf1 = new byte[8];
	int res = -1;
	
	try{
	    res = is.read(buf1, 0, 8);
	}
	catch(IOException e){ e.printStackTrace(); }
	
	if(res<0) return false;
	
	long recordNumber = readIntBE(buf1,0);
	long contentLength = readIntBE(buf1,4);
	
	if(contentLength<0){
	    IG.err("contentLength is negative "+contentLength);
	    return false;
	}
	
	IOut.debug(30,"recordNumber = "+recordNumber);
	IOut.debug(30,"contentLength = "+recordNumber);	
	
	byte[] buf2 = new byte[contentLength*2];
	res = -1;
	
	try{
	    res = is.read(buf2, 0, contentLength*2);
	}
	catch(IOException e){ e.printStackTrace(); }
	if(res<0) return false;
	
	long shapeType = readIntLE(buf2, 0);
	
	switch(shapeType){
	case 0: // Null Shape Record
	    //IOut.debug(30, "reading Null Shape");
	    break;
	case 1: // Point Record
	    //IOut.debug(30, "reading Point Shape");
	    makePoint(buf2, (int)recordNumber);
	    break;
	case 8: // MultiPoint Record
	    //IOut.debug(30, "reading MultiPoint Shape");
	    makeMultiPoint(buf2, (int)recordNumber);
	    break;
	case 3: // Polyline Record
	    //IOut.debug(30, "reading Polyline Shape");
	    makePolyline(buf2, (int)recordNumber);
	    break;
	case 5: // Polygon Record
	    //IOut.debug(30, "reading Polygon Shape");
	    makePolyline(buf2, (int)recordNumber); // polygon is same with polyline
	    break;
	case 21: // PointM Record
	    //IOut.debug(30, "reading PointM Shape");
	    makePoint(buf2, (int)recordNumber); // Measurement is ignored
	    break;
	case 28: // MultiPointM Record
	    //IOut.debug(30, "reading MultiPointM Shape");
	    makeMultiPoint(buf2, (int)recordNumber); // Measurement is ignored
	    break;
	case 23: // PolylineM Record
	    //IOut.debug(30, "reading PolylineM Shape");
	    makePolyline(buf2, (int)recordNumber); // Measurement is ignored
	    break;
	case 25: // PolygonM Record
	    //IOut.debug(30, "reading PolygonM Shape");
	    makePolyline(buf2, (int)recordNumber); // Measurement is ignored,  // polygon is same with polyline
	    break;
	case 11: // PointZ Record
	    //IOut.debug(30, "reading PointZ Shape");
	    makePointZ(buf2, (int)recordNumber); // Measurement is ignored
	    break;
	case 18: // MultiPointZ Record
	    //IOut.debug(30, "reading MultiPointZ Shape");
	    makeMultiPointZ(buf2, (int)recordNumber); // Measurement is ignored
	    break;
	case 13: // PolylineZ Record
	    //IOut.debug(30, "reading PolylineZ Shape");
	    makePolylineZ(buf2, (int)recordNumber); // Measurement is ignored
	    break;
	case 15: // PolygonZ Record
	    //IOut.debug(30, "reading PolygonZ Shape");
	    makePolylineZ(buf2, (int)recordNumber); // Measurement is ignored  // polygon is same with polyline
	    break;
	case 31: // MultiPatch Record
	    //IOut.debug(30, "reading MultiPatch Shape");
	    makeMultiPatch(buf2, (int)recordNumber); // Measurement is ignored
	    break;
	default:
	    IG.err("unknown shape type "+shapeType);
	}
	return true;
    }
    */
    
    
    static public boolean readSHPRecord(InputStream is, boolean skipRecord, ArrayList<IObject[]> createdObjects){
	
	long recordNumber = 0;
	long contentLength = 0;
	long shapeType=0;
	
	try{
	    recordNumber = readIntBE(is);
	    if(recordNumber<0){
		return false;
	    }
	    
	    contentLength = readIntBE(is);
	    
	    
	    contentLength*=2; // length is per 16bit, byte len is double of it.
	    
	    if(contentLength<0){
		IG.err("contentLength is negative "+contentLength);
		return false;
	    }
	    
	    if(skipRecord){
		skip(is, contentLength);
		return true;
	    }
	    
	    IOut.debug(30,"recordNumber = "+recordNumber);
	    IOut.debug(30,"contentLength = "+contentLength);
	    
	    shapeType = readIntLE(is);
	    contentLength -= 4; // 4 byte read as integer
	    
	}
	catch(IOException e){ e.printStackTrace(); }
	
	//IOut.debug(30, "shapeType = "+shapeType);
	
	int readLen=0;
	switch((int)shapeType){
	case 0: // Null Shape Record
	    IOut.debug(30, "reading Null Shape");
	    break;
	case 1: // Point Record
	    IOut.debug(30, "reading Point Shape");
	    readLen = makePoint(is, (int)recordNumber, (int)contentLength, createdObjects);
	    break;
	case 8: // MultiPoint Record
	    IOut.debug(30, "reading MultiPoint Shape");
	    readLen = makeMultiPoint(is, (int)recordNumber, (int)contentLength, createdObjects);
	    break;
	case 3: // Polyline Record
	    IOut.debug(30, "reading Polyline Shape");
	    readLen = makePolyline(is, (int)recordNumber, (int)contentLength, createdObjects);
	    break;
	case 5: // Polygon Record
	    IOut.debug(30, "reading Polygon Shape");
	    readLen = makePolyline(is, (int)recordNumber, (int)contentLength, createdObjects); // same with polyline
	    break;
	case 21: // PointM Record
	    IOut.debug(30, "reading PointM Shape");
	    readLen = makePoint(is, (int)recordNumber, (int)contentLength, createdObjects); // Measurement is ignored
	    break;
	case 28: // MultiPointM Record
	    IOut.debug(30, "reading MultiPointM Shape");
	    readLen = makeMultiPoint(is, (int)recordNumber, (int)contentLength, createdObjects); // Measurement is ignored
	    break;
	case 23: // PolylineM Record
	    IOut.debug(30, "reading PolylineM Shape");
	    readLen = makePolyline(is, (int)recordNumber, (int)contentLength, createdObjects); // Measurement is ignored
	    break;
	case 25: // PolygonM Record
	    IOut.debug(30, "reading PolygonM Shape");
	    readLen = makePolyline(is, (int)recordNumber, (int)contentLength, createdObjects); // Measurement is ignored
	    break;
	case 11: // PointZ Record
	    IOut.debug(30, "reading PointZ Shape");
	    readLen = makePointZ(is, (int)recordNumber, (int)contentLength, createdObjects); // Measurement is ignored
	    break;
	case 18: // MultiPointZ Record
	    IOut.debug(30, "reading MultiPointZ Shape");
	    readLen = makeMultiPointZ(is, (int)recordNumber, (int)contentLength, createdObjects); // Measurement is ignored
	    break;
	case 13: // PolylineZ Record
	    IOut.debug(30, "reading PolylineZ Shape");
	    readLen = makePolylineZ(is, (int)recordNumber, (int)contentLength, createdObjects); // Measurement is ignored
	    break;
	case 15: // PolygonZ Record
	    IOut.debug(30, "reading PolygonZ Shape");
	    readLen = makePolylineZ(is, (int)recordNumber, (int)contentLength, createdObjects); // Measurement is ignored
	    break;
	case 31: // MultiPatch Record
	    IOut.debug(30, "reading MultiPatch Shape");
	    readLen = makeMultiPatch(is, (int)recordNumber, (int)contentLength, createdObjects); // Measurement is ignored
	    break;
	default:
	    IG.err("unknown shape type "+shapeType);
	}
	
	contentLength -= readLen;
	if(contentLength>0){
	    try{
		skip(is, contentLength);
	    }catch(IOException e){ e.printStackTrace(); } 
	}
	else if(contentLength<0){
	    IG.err("over read the stream");
	}
	return true;
    }
    
    
    
    static public void skip(InputStream is, long len) throws IOException{
	is.skip(len);
    }

    static public IVec readPoint(InputStream is) throws IOException{
	double x = readDoubleLE(is);
	double y = readDoubleLE(is);
	return new IVec(x,y,0);
    }
    
    static public IVec readPoint(byte[] buf, int idx){
	if(!checkBufLen(buf,idx,16)){
	    IG.err();
	    return new IVec();
	}
	double x = readDoubleLE(buf,idx);
	double y = readDoubleLE(buf,idx+8);
	return new IVec(x,y,0);
    }
    
    static public IVec readPointZ(InputStream is, int byteLen) throws IOException{
	double x = readDoubleLE(is);
	double y = readDoubleLE(is);
	double z = readDoubleLE(is);
	if(byteLen>24){
	    double m = readDoubleLE(is);
	}
	return new IVec(x,y,z);
    }
    
    static public IVec readPointZ(byte[] buf, int idx){
	if(!checkBufLen(buf,idx,24)){
	    IG.err();
	    return new IVec();
	}
	double x = readDoubleLE(buf,idx);
	double y = readDoubleLE(buf,idx+8);
	double z = readDoubleLE(buf,idx+16);
	return new IVec(x,y,z);
    }
    
    static public IVec[] readPoints(InputStream is, int num) throws IOException{
	IVec[] pts = new IVec[num];
	for(int i=0; i<num; i++){
	    pts[i] = readPoint(is);
	}
	return pts;
    }
    
    static public IVec[] readPoints(byte[] buf, int num, int idx){
	if(!checkBufLen(buf,idx,16*num)){
	    IG.err();
	    return null;
	}
	IVec[] pts = new IVec[num];
	for(int i=0; i<num; i++){
	    pts[i] = readPoint(buf, idx+16*i);
	}
	return pts;
    }
    
    
    static public int[] readIntegers(byte[] buf, int num, int idx){
	if(!checkBufLen(buf,idx,4*num)){
	    IG.err();
	    return null;
	}
	int[] val = new int[num];
	for(int i=0; i<num; i++){
	    val[i] = (int)readIntLE(buf, idx+4*i);
	}
	return val;
    }
    
    static public int[] readIntegers(InputStream is, int num) throws IOException{
	int[] val = new int[num];
	for(int i=0; i<num; i++){
	    val[i] = (int)readIntLE(is);
	}
	return val;
    }
    
    static public double[] readDoubles(InputStream is, int num) throws IOException{
	double[] val = new double[num];
	for(int i=0; i<num; i++){
	    val[i] = readDoubleLE(is);
	}
	return val;
    }
    
    static public double[] readDoubles(byte[] buf, int num, int idx){
	if(!checkBufLen(buf,idx,8*num)){
	    IG.err();
	    return null;
	}
	double[] val = new double[num];
	for(int i=0; i<num; i++){
	    val[i] = readDoubleLE(buf, idx+8*i);
	}
	return val;
    }
    
    static public void makePoint(byte[] buf, int id){
	if(!checkBufLen(buf,0,20)){
	    IG.err();
	    return;
	}
	
	IVec v = readPoint(buf,4);
	//double x = readDoubleLE(buf,4);
	//double y = readDoubleLE(buf,12);
	
	IPoint pt = new IPoint(v);
	pt.name(String.valueOf(id));
    }
    
    static public int makePoint(InputStream is, int id, int byteLen, ArrayList<IObject[]> createdObjects){
	int idx=0;
	try{
	    IVec v = readPoint(is);
	    idx+=16;
	    IPoint pt = new IPoint(v);
	    pt.name(String.valueOf(id));
	    
	    createdObjects.add(new IObject[]{ pt });
	    
	}catch(IOException e){ e.printStackTrace(); }
	return idx;
    }
    
    static public void makePointZ(byte[] buf, int id){
	if(!checkBufLen(buf,0,36)){
	    IG.err();
	    return;
	}
	IVec v = readPointZ(buf,4);
	IPoint pt = new IPoint(v);
	pt.name(String.valueOf(id));
    }
    
    static public int makePointZ(InputStream is, int id, int byteLen, ArrayList<IObject[]> createdObjects){
	int idx=0;
	try{
	    IVec v = readPointZ(is,byteLen);
	    
	    if(byteLen>24){ idx+=32; }
	    else { idx+=24; }
	    
	    IPoint pt = new IPoint(v);
	    pt.name(String.valueOf(id));
	    
	    createdObjects.add(new IObject[]{ pt });
	    
	}catch(IOException e){ e.printStackTrace(); }
	return idx;
    }
    
    static public void makeMultiPoint(byte[] buf, int id){
	long num = readIntLE(buf, 36);
	IVec[] v = readPoints(buf, (int)num, 40);
	if(v==null){
	    IG.err();
	    return; // error
	}
	for(int i=0; i<num; i++){
	    if(v[i]!=null){
		IPoint pt = new IPoint(v[i]);
		pt.name(String.valueOf(id));
	    }
	}
    }
    
    static public int makeMultiPoint(InputStream is, int id, int byteLenh, ArrayList<IObject[]> createdObjects){
	int idx=0;
	try{
	    skip(is,32);
	    idx+=32;
	    long num = readIntLE(is);
	    idx+=4;
	    IVec[] v = readPoints(is, (int)num);
	    idx+=16*num;
	    if(v==null){
		IG.err();
		return idx; // error
	    }

	    ArrayList<IObject> pts = new ArrayList<IObject>();
	    
	    for(int i=0; i<num; i++){
		if(v[i]!=null){
		    IPoint pt = new IPoint(v[i]);
		    pt.name(String.valueOf(id));
		    pts.add( pt );
		}
	    }
	    
	    createdObjects.add(pts.toArray(new IObject[pts.size()]));
	    
	}catch(IOException e){ e.printStackTrace(); }
	return idx;
    }
    
    static public void makeMultiPointZ(byte[] buf, int id){
	long num = readIntLE(buf, 36);
	IVec[] v = readPoints(buf, (int)num, 40);
	if(v==null){
	    IG.err();
	    return; // error
	}
	
	double[] z = readDoubles(buf, (int)num, (int)(40+num*16));
	if(z==null){
	    IG.err();
	    return; // error
	}
	
	for(int i=0; i<num; i++){
	    if(v[i]!=null){
		v[i].z = z[i];
		IPoint pt = new IPoint(v[i]);
		pt.name(String.valueOf(id));
	    }
	}
    }
    
    static public int makeMultiPointZ(InputStream is, int id, int byteLenh, ArrayList<IObject[]> createdObjects){
	int idx=0;
	try{
	    skip(is,32);
	    idx+=32;
	    
	    long num = readIntLE(is);
	    idx+=4;
	    
	    IVec[] v = readPoints(is, (int)num);
	    idx+=num*16;
	    if(v==null){
		IG.err();
		return idx; // error
	    }
	    
	    skip(is,16); // zmin & zmax
	    idx+=16;
	    
	    double[] z = readDoubles(is, (int)num);
	    idx+=8*num;
	    
	    if(z==null){
		IG.err();
		return idx; // error
	    }

	    ArrayList<IObject> pts = new ArrayList<IObject>();
	    
	    for(int i=0; i<num; i++){
		if(v[i]!=null){
		    if(constraint==null ||
		       constraint!=null && constraint.validate(v[i].x,v[i].y,v[i].z)){
			   
			   v[i].z = z[i];
			   IPoint pt = new IPoint(v[i]);
			   pt.name(String.valueOf(id));

			   pts.add(pt);
		       }
		}
	    }
	    
	    createdObjects.add(pts.toArray(new IObject[pts.size()]));
	    
	}catch(IOException e){ e.printStackTrace(); }
	return idx;
    }
    
    static public void makePolyline(byte[] buf, int id){
	long numParts = readIntLE(buf, 36);
	long numPoints = readIntLE(buf, 40);

	int[] partIdx = readIntegers(buf, (int)numParts, 44);
	if(partIdx==null){
	    IG.err();
	    return; //error
	}
	
	IVec[] pts = readPoints(buf, (int)numPoints, (int)(44+4*numParts));
	if(pts==null){
	    IG.err();
	    return; // error
	}
	
	for(int i=0; i<numParts; i++){
	    int len = 0;
	    if(partIdx[i]<numParts-1){
		len = partIdx[i+1]-partIdx[i];
	    }
	    else{
		len = (int)numPoints - partIdx[i];
	    }

	    if(len>0 && (len+partIdx[i])<numPoints){
		IVec[] plinePts = new IVec[len];
		for(int j=0; j<len; j++){
		    plinePts[j] = pts[partIdx[i]+j];
		}

		ICurve pline = new ICurve(plinePts);
		pline.name(String.valueOf(id));
	    }
	}
    }
    
    
    static public int makePolyline(InputStream is, int id, int byteLenh, ArrayList<IObject[]> createdObjects){
	int idx=0;
	try{
	    skip(is, 32);
	    idx+=32;
	    long numParts = readIntLE(is);
	    idx+=4;
	    long numPoints = readIntLE(is);
	    idx+=4;
	    
	    int[] partIdx = readIntegers(is, (int)numParts);
	    idx+=numParts*4;
	    
	    if(partIdx==null){
		IG.err();
		return idx; //error
	    }
	    
	    IVec[] pts = readPoints(is, (int)numPoints);
	    idx+=numPoints*16;
	    
	    if(pts==null){
		IG.err();
		return idx; // error
	    }
	    
	    ArrayList<IObject> plines = new ArrayList<IObject>();
	    
	    for(int i=0; i<numParts; i++){
		int len = 0;
		if(i<numParts-1){
		    len = partIdx[i+1]-partIdx[i];
		}
		else{
		    len = (int)numPoints - partIdx[i];
		}
		
		if(len>0 && (len+partIdx[i])<=numPoints){
		    IVec[] plinePts = new IVec[len];
		    for(int j=0; j<len; j++){
			plinePts[j] = pts[partIdx[i]+j];
		    }
		    
		    ICurve pline = new ICurve(plinePts);
		    pline.name(String.valueOf(id));

		    plines.add(pline);
		}
	    }
	    
	    createdObjects.add(plines.toArray(new IObject[plines.size()]));
	    
	}catch(IOException e){ e.printStackTrace(); }
	return idx;
    }
    
    
    static public void makePolylineZ(byte[] buf, int id){
	long numParts = readIntLE(buf, 36);
	long numPoints = readIntLE(buf, 40);
	
	int[] partIdx = readIntegers(buf, (int)numParts, 44);
	if(partIdx==null){
	    IG.err();
	    return; // error
	}
	
	IVec[] pts = readPoints(buf, (int)numPoints, (int)(44+4*numParts));
	if(pts==null){
	    IG.err();
	    return; // error
	}
	
	double[] z = readDoubles(buf, (int)numPoints, (int)(44+4*numParts + 16*numPoints + 16));
	if(z==null){
	    IG.err();
	    return; // error
	}
	
	for(int i=0; i<numPoints; i++){
	    if(pts[i]!=null){
		pts[i].z = z[i];
	    }
	}

	for(int i=0; i<numParts; i++){
	    int len = 0;
	    if(partIdx[i]<numParts-1){
		len = partIdx[i+1]-partIdx[i];
	    }
	    else{
		len = (int)numPoints - partIdx[i];
	    }
	    
	    if(len>0 && (len+partIdx[i])<numPoints){
		IVec[] plinePts = new IVec[len];
		for(int j=0; j<len; j++){
		    plinePts[j] = pts[partIdx[i]+j];
		}
		
		ICurve pline = new ICurve(plinePts);
		pline.name(String.valueOf(id));
	    }
	}
    }
    
    
    
    static public int makePolylineZ(InputStream is, int id, int byteLenh, ArrayList<IObject[]> createdObjects){
	int idx=0;
	try{
	    skip(is, 32);
	    idx+=32;
	    
	    long numParts = readIntLE(is);
	    idx+=4;
	    long numPoints = readIntLE(is);
	    idx+=4;

	    //IG.p("numParts = "+numParts);
	    //IG.p("numPoints = "+numPoints);
	    
	    int[] partIdx = readIntegers(is, (int)numParts);
	    idx+=4*numParts;
	    if(partIdx==null){
		IG.err();
		return idx; // error
	    }
	    
	    IVec[] pts = readPoints(is, (int)numPoints);
	    idx+= 16*numPoints;
	    if(pts==null){
		IG.err();
		return idx; // error
	    }
	    
	    skip(is, 16); // skip zmin zmax
	    idx+=16;
	    
	    double[] z = readDoubles(is, (int)numPoints);
	    idx+=numPoints*8;
	    
	    if(z==null){
		IG.err();
		return idx; // error
	    }
	    
	    for(int i=0; i<numPoints; i++){
		if(pts[i]!=null){
		    pts[i].z = z[i];
		}
	    }
	    
	    ArrayList<IObject> plines = new ArrayList<IObject>();
	    
	    for(int i=0; i<numParts; i++){
		int len = 0;
		if(i<numParts-1){
		    len = partIdx[i+1]-partIdx[i];
		}
		else{
		    len = (int)numPoints - partIdx[i];
		}
		
		if(len>0 && (len+partIdx[i])<=numPoints){
		    IVec[] plinePts = new IVec[len];
		    for(int j=0; j<len; j++){
			plinePts[j] = pts[partIdx[i]+j];
		    }
		    
		    ICurve pline = new ICurve(plinePts);
		    pline.name(String.valueOf(id));

		    plines.add(pline);
		}
	    }
	    
	    createdObjects.add(plines.toArray(new IObject[plines.size()]));
	    
	}catch(IOException e){ e.printStackTrace(); }
	return idx;
    }
    
    /*
    static public void makePolygon(byte[] buf, int id){
        long numParts = readIntLE(buf, 36);
	long numPoints = readIntLE(buf, 40);
	
	int[] partIdx = readIntegers(buf, numParts, 44);
	if(partIdx==null){
	    IG.err();
	    return; // error
	}
	
	IVec[] pts = readPoints(buf, (int)numPoints, (int)(44+4*numParts));
	if(pts==null){
	    IG.err();
	    return; // error
	}
	for(int i=0; i<numParts; i++){
	    int len = 0;
	    if(partIdx[i]<numParts-1){
		len = partIdx[i+1]-partIdx[i];
	    }
	    else{
		len = numPoints - partIdx[i];
	    }
	    if(len>0 && (len+partIdx[i])<numPoints){
		IVec[] plinePts = new IVec[len];
		for(int j=0; j<len; j++){
		    plinePts[j] = pts[partIdx[i]+j];
		}

		ICurve pline = new ICurve(plinePts);
		pline.name(String.valueOf(id));
	    }
	}
    }
    
    static public void makePolygonZ(byte[] buf, int id){
	int numParts = readIntLE(buf, 36);
	int numPoints = readIntLE(buf, 40);
	
	int[] partIdx = readIntegers(buf, numParts, 44);
	if(partIdx==null){
	    IG.err();
	    return; //error
	}
	
	IVec[] pts = readPoints(buf, (int)numPoints, (int)(44+4*numParts));
	if(pts==null){
	    IG.err();
	    return; // error
	}
	
	double[] z = readDoubles(buf, numPoints, 44+4*numParts + 16*numPoints + 16);
	if(z==null){
	    IG.err();
	    return; // error
	}

	for(int i=0; i<numPoints; i++){
	    if(pts[i]!=null){
		pts[i].z = z[i];
	    }
	}
	
	for(int i=0; i<numParts; i++){
	    int len = 0;
	    if(partIdx[i]<numParts-1){
		len = partIdx[i+1]-partIdx[i];
	    }
	    else{
		len = numPoints - partIdx[i];
	    }
	    
	    if(len>0 && (len+partIdx[i])<numPoints){
		IVec[] plinePts = new IVec[len];
		for(int j=0; j<len; j++){
		    plinePts[j] = pts[partIdx[i]+j];
		}
		
		ICurve pline = new ICurve(plinePts);
		pline.name(String.valueOf(id));
	    }
	}
    }
    */
    
    static public void makeMultiPatch(byte[] buf, int id){
	
	long numParts = readIntLE(buf, 36);
	long numPoints = readIntLE(buf, 40);
	
	int[] partIdx = readIntegers(buf, (int)numParts, 44);
	if(partIdx==null){
	    IG.err();
	    return; // error
	}
	
	int[] partTypes = readIntegers(buf, (int)numParts, (int)(44+numParts*4));
	if(partTypes==null){
	    IG.err();
	    return; // error
	}
	
	IVec[] pts = readPoints(buf, (int)numPoints, (int)(44+4*numParts+4*numParts));
	if(pts==null){
	    IG.err();
	    return; // error
	}
	
	double[] z = readDoubles(buf, (int)numPoints, (int)(44+4*numParts +4*numParts + 16*numPoints + 16));
	if(z==null){
	    IG.err();
	    return; // error
	}
	
	for(int i=0; i<numPoints; i++){
	    if(pts[i]!=null){
		pts[i].z = z[i];
	    }
	}
	
	for(int i=0; i<numParts; i++){
	    int len = 0;
	    if(i<numParts-1){
		len = partIdx[i+1]-partIdx[i];
	    }
	    else{
		len = (int)numPoints - partIdx[i];
	    }
	    
	    if(len>0 && (len+partIdx[i])<=numPoints){
		IVec[] plinePts = new IVec[len];
		for(int j=0; j<len; j++){
		    plinePts[j] = pts[partIdx[i]+j];
		}
		
		if(partTypes[i]==0){ // triangle strip
		    IVec[] outline = new IVec[len+1];
		    int outIdx=0;
		    outline[outIdx] = plinePts[0];
		    outIdx++;
		    int j=1;
		    for(; j<len; j+=2){
			outline[outIdx] = plinePts[j];
			outIdx++;
		    }
		    if(len%2==0){ j=len-2; }
		    else{ j=len-1; }
		    for(; j>0; j-=2){
			outline[outIdx] = plinePts[j];
		    }
		    outline[len] = plinePts[0];
		    
		    ICurve pline = new ICurve(outline);
		    pline.name(String.valueOf(id));
		    
		}
		else if(partTypes[i] == 1){// triangle fan
		    IVec[] outline = new IVec[len+1];
		    for(int j=0; j<len; j++){
			outline[j] = plinePts[j];
		    }
		    outline[len] = plinePts[0];
		    
		    ICurve pline = new ICurve(outline);
		    pline.name(String.valueOf(id));
		}
		else if(partTypes[i] == 2){// outer ring
		    ICurve pline = new ICurve(plinePts);
		    pline.name(String.valueOf(id));
		}
		else if(partTypes[i] == 3){// inner ring
		    ICurve pline = new ICurve(plinePts);
		    pline.name(String.valueOf(id));
		}
		else if(partTypes[i] == 4){// first ring
		    ICurve pline = new ICurve(plinePts);
		    pline.name(String.valueOf(id));
		}
		else if(partTypes[i] == 5){// ring
		    ICurve pline = new ICurve(plinePts);
		    pline.name(String.valueOf(id));
		}
		else{
		    IG.err("unknown part type " +partTypes[i]);
		}
	    }
	}
    }

    
    static public int makeMultiPatch(InputStream is, int id, int byteLenh, ArrayList<IObject[]> createdObjects){
	int idx=0;
	try{
	    skip(is, 32);
	    idx+=32;
	    long numParts = readIntLE(is);
	    idx+=4;
	    long numPoints = readIntLE(is);
	    idx+=4;
	    
	    int[] partIdx = readIntegers(is, (int)numParts);
	    idx+=numParts*4;
	    
	    if(partIdx==null){
		IG.err();
		return idx; // error
	    }
	    
	    int[] partTypes = readIntegers(is, (int)numParts);
	    idx+=numParts*4;
	    
	    if(partTypes==null){
		IG.err();
		return idx; // error
	    }
	    
	    IVec[] pts = readPoints(is, (int)numPoints);
	    idx+=numPoints*16;
	    
	    if(pts==null){
		IG.err();
		return idx; // error
	    }
	    
	    skip(is, 16); // skip zmin, zmax
	    idx+=16;
	    
	    double[] z = readDoubles(is, (int)numPoints);
	    idx+=numPoints*8;
	    
	    if(z==null){
		IG.err();
		return idx; // error
	    }
	    
	    for(int i=0; i<numPoints; i++){
		if(pts[i]!=null){
		    pts[i].z = z[i];
		}
	    }

	    
	    ArrayList<IObject> plines = new ArrayList<IObject>();
	    
	    for(int i=0; i<numParts; i++){
		int len = 0;
		if(partIdx[i]<numParts-1){
		    len = partIdx[i+1]-partIdx[i];
		}
		else{
		    len = (int)numPoints - partIdx[i];
		}
		
		if(len>0 && (len+partIdx[i])<numPoints){
		    IVec[] plinePts = new IVec[len];
		    for(int j=0; j<len; j++){
			plinePts[j] = pts[partIdx[i]+j];
		    }
		    
		    if(partTypes[i]==0){ // triangle strip
			IVec[] outline = new IVec[len+1];
			int outIdx=0;
			outline[outIdx] = plinePts[0];
			outIdx++;
			int j=1;
			for(; j<len; j+=2){
			    outline[outIdx] = plinePts[j];
			    outIdx++;
			}
			if(len%2==0){ j=len-2; }
			else{ j=len-1; }
			for(; j>0; j-=2){
			    outline[outIdx] = plinePts[j];
			}
			outline[len] = plinePts[0];
			
			ICurve pline = new ICurve(outline);
			pline.name(String.valueOf(id));

			plines.add(pline);
		    }
		    else if(partTypes[i] == 1){// triangle fan
			IVec[] outline = new IVec[len+1];
			for(int j=0; j<len; j++){
			    outline[j] = plinePts[j];
			}
			outline[len] = plinePts[0];
			
			ICurve pline = new ICurve(outline);
			pline.name(String.valueOf(id));

			plines.add(pline);
		    }
		    else if(partTypes[i] == 2){// outer ring
			ICurve pline = new ICurve(plinePts);
			pline.name(String.valueOf(id));

			plines.add(pline);
		    }
		    else if(partTypes[i] == 3){// inner ring
			ICurve pline = new ICurve(plinePts);
			pline.name(String.valueOf(id));

			plines.add(pline);
		    }
		    else if(partTypes[i] == 4){// first ring
			ICurve pline = new ICurve(plinePts);
			pline.name(String.valueOf(id));

			plines.add(pline);
		    }
		    else if(partTypes[i] == 5){// ring
			ICurve pline = new ICurve(plinePts);
			pline.name(String.valueOf(id));

			plines.add(pline);
		    }
		    else{
			IG.err("unknown part type " +partTypes[i]);
		    }
		}
	    }
	    
	    createdObjects.add(plines.toArray(new IObject[plines.size()]));
	    
	}catch(IOException e){ e.printStackTrace(); }
	return idx;
    }



    static public String getFilePrefix(String filename){
	int dotPos = filename.lastIndexOf('.');
	return filename.substring(0,dotPos);
    }

    static public String getFileNameWithExtension(String filename, String extension){
	return getFilePrefix(filename) + "." + extension;
    }

    static public File getFileWithExtension(File file, String extension){
	String filePrefix = getFilePrefix(file.getName());
	return new File(file.getParentFile(), filePrefix+"."+extension);
    }
    
    /**
       Reading a SHP file and creating objects in a server.
       The main entry of the importer class.
       @param file An importing file object.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public ArrayList<IObject> read(File file, IServerI server){
        
	if(!file.exists()){
	    IOut.err("the input file doesn't exist : "+file.getPath());
	    return null;
	}

        if(server!=null){ server.server().unit(IUnit.Meter); }
        else{ IG.unit(IUnit.Meter); }

        String filePrefix = getFilePrefix(file.getName());
        File dbfFile1 = new File(file.getParentFile(), filePrefix+".dbf");
        File dbfFile2 = new File(file.getParentFile(), filePrefix+".DBF");
        File dbfFile3 = new File(file.getParentFile(), filePrefix+".Dbf");

        File dbfFile = null;
        if(dbfFile1.exists()){
	    dbfFile = dbfFile1;
	    //IG.p(dbfFile1.getAbsolutePath() +" exists");
	}
        else if(dbfFile2.exists()){
	    dbfFile = dbfFile2;
	    //IG.p(dbfFile2.getAbsolutePath() +" exists");
	}
        else if(dbfFile3.exists()){
	    dbfFile = dbfFile3;
	    //IG.p(dbfFile3.getAbsolutePath() +" exists");
	}
        else{
	    IG.err("DBF file does not exist : "+dbfFile1.getAbsolutePath());
	}

        ArrayList<IObject[]> objects = null;
        try{
	    objects = readSHP(new FileInputStream(file), server);
	}
        catch(IOException e){ e.printStackTrace(); }
        
        if(dbfFile!=null){
	    IEsri.Attributes[] attributes = readDBF(dbfFile, server);
	    
	    if(attributes.length != objects.size()){
		IG.err("Shape count ("+objects.size()+") and DBF count ("+attributes.length+") does not match");
	    }
	    
	    for(int i=0; i<attributes.length && i<objects.size(); i++){
		for(int j=0; objects.get(i)!=null && j<objects.get(i).length; j++){
		    IObject obj = objects.get(i)[j];
		    if(obj!=null){
			obj.userData(new Object[]{ attributes[i] });
		    }
		}
	    }
	}

        ArrayList<IObject> retval = new ArrayList<IObject>();
        for(int i=0; i<objects.size(); i++){
	    for(int j=0; objects.get(i)!=null && j<objects.get(i).length; j++){
		IObject obj = objects.get(i)[j];
		if(obj!=null){
		    retval.add(obj);
		}
	    }
	}
        //
	return retval; 
    }






    static public ArrayList<IObject> read(InputStream shpStream, InputStream dbfStream, IServerI server){
        if(shpStream==null){
	    IG.err("no SHP file stream found");
	}

        ArrayList<IObject[]> objects = null;
        objects = readSHP(shpStream, server);
        

        if(server!=null){ server.server().unit(IUnit.Meter); }
        else{ IG.unit(IUnit.Meter); }

        if(dbfStream!=null){
	    IEsri.Attributes[] attributes = readDBF(dbfStream, server);

	    if(attributes.length != objects.size()){
		IG.err("Shape count ("+objects.size()+") and DBF count ("+attributes.length+") does not match");
	    }
	    
	    for(int i=0; i<attributes.length && i<objects.size(); i++){
		for(int j=0; objects.get(i)!=null && j<objects.get(i).length; j++){
		    IObject obj = objects.get(i)[j];
		    if(obj!=null){
			obj.userData(new Object[]{ attributes[i] });
		    }
		}
	    }
	}
        //else{ IG.err("no DBF file stream found"); }
	
        ArrayList<IObject> retval = new ArrayList<IObject>();
        for(int i=0; i<objects.size(); i++){
	    for(int j=0; objects.get(i)!=null && j<objects.get(i).length; j++){
		IObject obj = objects.get(i)[j];
		if(obj!=null){
		    retval.add(obj);
		}
	    }
	}
        //
	return retval; 
    }


    /**
       Reading a SHP file and creating objects in a server.
       The main entry of the importer class.
       @param file An importing file object.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public ArrayList<IObject> read(String filename, IServerI server){
        return read(new File(filename), server);
        //try{ return readSHP(new FileInputStream(filename), server); }
        //catch(IOException e){ e.printStackTrace(); }
	//return null; 
    }

    
    
    
    /**
       Reading a SHP file and creating objects in a server.
       The main entry of the importer class.
       @param file An importing file object.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public ArrayList<IObject[]> readSHP(File file, IServerI server){
        try{ return readSHP(new FileInputStream(file), server); }
        catch(IOException e){ e.printStackTrace(); }
	return null; 
    }
    
    /**
       Reading a SHP file and creating objects in a server.
       The main entry of the importer class.
       @param file An importing file object.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public ArrayList<IObject[]> readSHP(String filename, IServerI server){
        try{ return readSHP(new FileInputStream(filename), server); }
        catch(IOException e){ e.printStackTrace(); }
	return null; 
    }
    
    /**
       Reading an SHP file and creating objects in a server.
       @param is input stream of an importing file.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public ArrayList<IObject[]> readSHP(InputStream is, IServerI server){
	
	ArrayList<IObject[]> createdObjects = new ArrayList<IObject[]>();
        readSHPHeader(is);

        boolean res=false;
        int count=0;
        do{
	    //IG.p("record "+count);
	    if(count%100==0 && count>0){
		IOut.debug(20, count);
	    }
	    
	    IOut.debug(20, "record "+count);
	    
	    res = readSHPRecord(is, false, createdObjects);
	    
	    /*
	    if(count>=0 && count<700){
		res = readSHPRecord(is, false);
	    }
	    else{
		res = readSHPRecord(is, true);
	    }
	    */
            count++;
            //if(count==10){ return null; } // test
	}while(res);

        IOut.debug(20, "SHP import complete");

	return createdObjects;
    }
    
    public static void main(String args[]){
	try {
	    FileInputStream fis = (new FileInputStream(args[0]));
	    readSHP(fis,null);
	}catch(Exception e){ e.printStackTrace(); }
    }


    /*
    public static class DBFField{
	public String name;
	public char type;
	public int length;
	public int fractionLength;
	public int workspaceID;
    }

    public static class DBFRecord{
	public int length;
	public String[] text;
	public double[] number;
	public DBFRecord(int len){
	    length = len;
	    text = new String[len];
	    number = new double[len];
	}
    }
    
    public static class DBFHeader{
	public int recordNum;
	public int firstDataPos;
	public int recordLen;
	public int fieldNum;
	public IEsri.AttributeField[] fields;
    }
    */
    
    /**
       Reading a DBF file
       The main entry of the importer class.
       @param file An importing file object.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public IEsri.Attributes[] readDBF(File file, IServerI server){
        try{ return readDBF(new FileInputStream(file), server); }
        catch(IOException e){ e.printStackTrace(); }
	return null; 
    }
    
    /**
       Reading a DBF file 
       The main entry of the importer class.
       @param file An importing file object.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public IEsri.Attributes[] readDBF(String filename, IServerI server){
        try{ return readDBF(new FileInputStream(filename), server); }
        catch(IOException e){ e.printStackTrace(); }
	return null; 
    }
    
    /**
       Reading an DBF file
       @param is input stream of an importing file.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public IEsri.Attributes[] readDBF(InputStream is, IServerI server){
	
	IEsri.AttributeHeader header = readDBFHeader(is);
	
        int recordNum = (int)header.recordNum;
        long recordLen = header.recordLen;
	
        IEsri.Attributes[] records = new IEsri.Attributes[recordNum];
        
        for(int i=0; i<recordNum; i++){
        //for(int i=0; i<2; i++){
	    //IG.p("RECORD "+i); //
	    records[i] = readDBFRecord(is, header);
	    /*
	    if(i<10){
		for(int j=0; j<records[i].length; j++){
		    IG.p(header.fields[j].name+" ("+header.fields[j].type+"): "+records[i].text[j]+" ("+records[i].number[j]+")");
		}
	    }
	    */
	    if(i%100==0 && i>0){
		IOut.debug(20, i+"/"+recordNum);
	    }
        }
	
        IOut.debug(20, "DBF import complete");
	
	return(records);
    }
    
    static public String stringWithoutSpace(byte[] buf){
	int idx1 = 0;
	for(; idx1<buf.length && (buf[idx1]==' '||buf[idx1]=='\0'); idx1++);
	int idx2 = buf.length-1;
	for(; idx2>idx1 && (buf[idx2]==' '||buf[idx2]=='\0'); idx2--);
	
	if(idx1>idx2){
	    return new String();
	}

	return new String(buf, idx1, idx2-idx1+1);
	
	//byte[] buf2 = new byte[idx2-idx1+1];
	//for(int i=0; i<(idx2-idx1+1); i++){ buf2[i] = buf[i+idx1]; }
	//return new String(buf2);   
    }
    
    
    
    static public IEsri.Attributes readDBFRecord(InputStream is, IEsri.AttributeHeader header){
	
	IEsri.Attributes record = new IEsri.Attributes((int)header.fieldNum); // one row
	
	record.fields = header.fields;
	
	try{
	    
	    int b = is.read(); // delete flag
	    //if( (char)b == '*'){} // meaning deleted
	    
	    for(int i=0; i<header.fieldNum; i++){
		int len = header.fields[i].length;
		
		byte[] buf = new byte[len];
		is.read(buf, 0, len);
		
		record.text[i] = stringWithoutSpace(buf);
		
		
		if(header.fields[i].type == 'N' || // numeric
		   header.fields[i].type == 'F' ){ // floating point
		    if(record.text[i].length()==0){
			record.number[i]=0;
		    }
		    else{
			try{
			    record.number[i] = Double.parseDouble(record.text[i]);
			}catch(Exception e){
			    e.printStackTrace();
			    IG.err("record.text : \""+record.text[i]+"\""); //
			}
		    }
		}
		else if(header.fields[i].type == 'L'){ // logical
		    if(record.text[i].equals("Y") || record.text[i].equals("y") ||
		       record.text[i].equals("T") || record.text[i].equals("t")){
			record.number[i] = 1;
		    }
		    else{
			record.number[i] = 0;
		    }
		}
	    }
	    
	}catch(Exception e){
	    e.printStackTrace();
	}
	
	return record;
	
	/*
	byte[] buf = new byte[recordLen];
	try{
	    is.read(buf, 0, recordLen);
	}catch(Exception e){ e.printStackTrace(); }
	
	for(int i=0; i<recordLen; i++){
	    int b = (int)buf[i];
	    
	    IG.p(i+": " + b +" ("+(char)b+")");
	}
	*/
    }
    
    
    static public IEsri.AttributeHeader readDBFHeader(InputStream is){
	
	IEsri.AttributeHeader header = new IEsri.AttributeHeader();
	
	try{
	    is.skip(4);
	    
	    header.recordNum = readIntLE(is);
	    header.firstDataPos = readShortLE(is);
	    header.recordLen = readShortLE(is);
	    
	    //IG.p("recordNum = "+header.recordNum);
	    //IG.p("firstDataPos = "+header.firstDataPos);
	    //IG.p("recordLen = "+header.recordLen);
	    
	    is.skip(32-12);
	    
	    long fieldDescriptorNum = (header.firstDataPos-32-1)/32;
	    header.fieldNum = fieldDescriptorNum;
	    
	    header.fields = new IEsri.AttributeField[(int)fieldDescriptorNum];
	    
	    int totalFieldLen = 0;
	    
	    for(int i=0; i<fieldDescriptorNum; i++){
		header.fields[i] = new IEsri.AttributeField();
		
		byte[] fieldNameBuf = new byte[10];
		is.read(fieldNameBuf, 0, 10);
		is.skip(1); // one space at the end of field name
		
		char fieldType = (char)is.read();
		// C, D, F, L, M, N
		header.fields[i].type = fieldType;
		
		is.skip(4);
		
		int fieldLen = is.read();
		header.fields[i].length = fieldLen;
		
		totalFieldLen += fieldLen;
		
		int fieldFractionLen = is.read();
		header.fields[i].fractionLength = fieldFractionLen;
		
		is.skip(2);
		
		int workspaceID = is.read();
		header.fields[i].workspaceID = workspaceID;
		
		is.skip(10+1);
		
		int nameStart=0;
		int nameEnd = fieldNameBuf.length-1;
		for(; nameStart<fieldNameBuf.length && fieldNameBuf[nameStart]==0; nameStart++);
		for(; nameEnd>=0&& fieldNameBuf[nameEnd]==0; nameEnd--);
		
		String fieldName = new String(fieldNameBuf, nameStart, nameEnd-nameStart+1);
		header.fields[i].name = fieldName;
		
		/*
		if(fieldType=='F' || fieldName.equals("AFAF")){
		    IG.p("fieldLen = "+fieldLen);
		    IG.p("fieldFractionLen = "+fieldFractionLen);
		}
		*/

		
		//IG.p(i+" fieldName = "+fieldName);
		//IG.p(i+" fieldType = "+fieldType);
		//IG.p(i+" fieldLen = "+fieldLen);
		//IG.p(i+" fieldFractionLen = "+fieldFractionLen);
		//IG.p(i+" workspaceID = "+workspaceID);
	    }
	    
	    int b = is.read();
	    
	    //IG.p("header end: "+b+" ("+(char)b+")"); //
	    //IG.p("totalFieldLen = "+totalFieldLen); //
	    
	    /*
	    for(int i=12; true; i++){
		int b = is.read();
		IG.p(i+": "+b+" ("+(char)b+")"); //
		if(i > 32 && b==13 ){ // end mark of header 0x0D
		    break;
		}
	    }
	    */
	}catch(IOException e){ e.printStackTrace(); }
	
	return header;
    }
    
}
