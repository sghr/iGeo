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

/**
   Byte Output Stream keeping byte framgents as fragments never integrating into one buffer
   
   @author Satoru Sugihara
*/
public class IByteListOutputStream extends OutputStream{
    ArrayList<byte[]> bytes;
    public void write(byte[] b){
	if(bytes==null) bytes = new ArrayList<byte[]>();
	bytes.add(b);
    }
    public void write(byte[] b, int off, int len){
	if(bytes==null) bytes = new ArrayList<byte[]>();
	// this method is not efficient because this duplicates memory 
	byte[] buf = new byte[len];
	for(int i=0; i<len; i++) buf[i] = b[i+off];
	bytes.add(buf);
    }
    public void write(int i){
	if(bytes==null) bytes = new ArrayList<byte[]>();
        byte[] buf = new byte[4];
        for(int d=0; d<buf.length; d++){ buf[d] = (byte)(i&0xFF); i>>>=8; }
        bytes.add(buf);
    }
    public void write(ArrayList<byte[]> b){
	if(bytes==null) bytes = new ArrayList<byte[]>();
	bytes.addAll(b);
    }
    
    public ArrayList<byte[]> toByteList(){ return bytes; }
    
    
    public byte[] toByteArray(){
	int len = length();
	byte[] buf = new byte[len];
	for(int i=0, idx=0; i<bytes.size(); i++){
	    byte[] b = (byte[])bytes.get(i);
	    for(int j=0; j<b.length; j++,idx++) buf[idx] = b[j];
	}
	return buf;
    }
    
    public int length(){
	if(bytes==null) return 0;
	int len = 0;
	for(int i=0; i<bytes.size(); i++) len+=bytes.get(i).length;
	return len;
    }
    
}
