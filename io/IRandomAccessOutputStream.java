/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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
   @version 0.7.0.0;
*/
public class IRandomAccessOutputStream extends OutputStream{
    public RandomAccessFile file;
    
    public IRandomAccessOutputStream(String f) throws IOException{
	file = new RandomAccessFile(new File(f), "rw");
    }
    
    public IRandomAccessOutputStream(File f) throws IOException{
	file = new RandomAccessFile(f, "rw");
    }
    
    public void write(byte[] b)throws IOException{ file.write(b); }
    
    public void write(byte[] b, int off, int len)throws IOException{ file.write(b,off,len); }
    
    public void write(int i)throws IOException{ file.write(i); }
    
    public void seek(long l)throws IOException{ file.seek(l); }
    
    public long pointer()throws IOException{ return file.getFilePointer(); }
    
    
/*
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
*/
    
}
