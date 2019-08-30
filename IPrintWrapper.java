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

package igeo;

import java.io.*;
import java.util.Arrays;
    
public class IPrintWrapper extends OutputStream{
    public IPrintWrapper(){}
    
    public void p(String str) {}
    
    public void write(byte[] b){
	p(new String(b));
    }
    
    public void write(byte[] b, int off, int len){
	//p(new String(Arrays.copyOfRange(b, off, off+len))); // for jdk1.6+
	byte[] b2 = new byte[len];
	for(int i=0; i<len; i++) b2[i] = b[off+i];
	p(new String(b2));
    }
    
    public void write(int b){
	p(String.valueOf((char)b));
    }
}
