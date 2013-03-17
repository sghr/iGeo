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

/**
   A class to provide default static error output stream.
   
   @see IOut
   
   @author Satoru Sugihara
*/
public class IErr {
    public static PrintStream ps = System.err;
    public static boolean printPrefix=true; //false;
    public static boolean enabled=true; //false; //true; 
    public static String errPrefix = "ERROR: ";
    
    public static void setStream(PrintStream pstr){ ps=pstr; }
    
    public static void enablePrefix(){ printPrefix=true; }
    public static void disablePrefix(){ printPrefix=false; }
    public static void enablePrint(){ enabled=true; }
    public static void disablePrint(){ enabled=false; }
    
    public static void p(Object str){
	if(enabled){
	    if(printPrefix) IOut.printCurrentStack(ps);
	    ps.print(errPrefix);
	    ps.println(str);
	}
    }
    
    public static void p(){
	if(enabled){
	    if(printPrefix) IOut.printCurrentStack(ps); // added
	    ps.print(errPrefix);
	    ps.println();
	}
    }
    
    public static void println(Object str){ if(enabled) ps.println(str); }
    public static void println(boolean str){ if(enabled) ps.println(str); }
    public static void println(char str){ if(enabled) ps.println(str); }
    public static void println(char[] str){ if(enabled) ps.println(str); }
    public static void println(double str){ if(enabled) ps.println(str); }
    public static void println(float str){ if(enabled) ps.println(str); }
    public static void println(int str){ if(enabled) ps.println(str); }
    public static void println(long str){ if(enabled) ps.println(str); }
    public static void println(String str){ if(enabled) ps.println(str); }
    public static void println(){ if(enabled) ps.println(); }
    
    public static void print(Object str){ if(enabled) ps.print(str); }
    public static void print(boolean str){ if(enabled) ps.print(str); }
    public static void print(char str){ if(enabled) ps.print(str); }
    public static void print(char[] str){ if(enabled) ps.print(str); }
    public static void print(double str){ if(enabled) ps.print(str); }
    public static void print(float str){ if(enabled) ps.print(str); }
    public static void print(int str){ if(enabled) ps.print(str); }
    public static void print(long str){ if(enabled) ps.print(str); }
    public static void print(String str){ if(enabled) ps.print(str); }
    
    public static void flush(){ if(enabled) ps.flush(); }
    
    public static PrintStream get(){ return ps; }
    
}
