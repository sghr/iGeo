/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

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

package igeo.core;

import java.io.*;


/**
   A class to provide default static output stream.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IOut {
    public static PrintStream ps = System.out;
    public static PrintStream err = System.err;
    public static PrintStream debug = System.out; //System.err;
    public static boolean printPrefix=true; //false;
    public static boolean enabled=true; //false; //true; 
    public static boolean errEnabled=true;
    public static boolean debugEnabled=true;
    public static int debugLevel=0;
    public static boolean printErrorPrefix=true; //false;
    public static boolean printDebugPrefix=true; //false;
    public static String errPrefix = "ERROR: ";
    public static String debugPrefix = "DEBUG: ";
    
    public static void setStream(PrintStream pstr){ ps=pstr; }
    public static void setErrStream(PrintStream pstr){ err=pstr; }
    
    public static void enablePrint(){ enabled=true; }
    public static void disablePrint(){ enabled=false; }
    public static void enableErr(){ errEnabled=true; }
    public static void disableErr(){ errEnabled=false; }
    public static void enableDebug(){ debugEnabled=true; }
    public static void disableDebug(){ debugEnabled=false; }
    
    public static void enablePrefix(){ printPrefix=true; }
    public static void disablePrefix(){ printPrefix=false; }
    public static void enableErrorPrefix(){ printErrorPrefix=true; }
    public static void disableErrorPrefix(){ printErrorPrefix=false; }
    public static void enableDebugPrefix(){ printDebugPrefix=true; }
    public static void disableDebugPrefix(){ printDebugPrefix=false; }
    
    public static void debugLevel(int level){ debugLevel=level; }
    public static int debugLevel(){ return debugLevel; }
    
    //protected static void printCurrentStack(){ printCurrentStack(ps); }
    
    protected static void printCurrentStack(PrintStream p){
	//final int stackNum=3; //4
	//final StackTraceElement[] stk = Thread.currentThread().getStackTrace();
	//if(stk!=null && stk.length>stackNum) printStack(p,stk[stackNum]);
	final String separator=": ";
	String stk = currentStack();
	p.print(stk+separator);
    }
    protected static void printStack(PrintStream p, StackTraceElement stk){
	//String className = stk.getClassName();
	//int idx = className.lastIndexOf('.');
	//if(idx>=0) className = className.substring(idx+1);
	//p.print(className+"."+stk.getMethodName()+": "); //
	final String separator=": ";
	p.print(stack(stk)+separator);
    }
    
    public static String currentStack(){
	final int stackNum=3; //4
	final StackTraceElement[] stk = Thread.currentThread().getStackTrace();
	if(stk!=null && stk.length>stackNum) return stack(stk[stackNum]);
	return ""; // null // null or ""?
    }
    public static String stack(StackTraceElement stk){
	String className = stk.getClassName();
	int idx = className.lastIndexOf('.');
	if(idx>=0) className = className.substring(idx+1);
	return className+"."+stk.getMethodName();
    }
    
    public static void p(Object str){
	if(enabled){
	    if(printPrefix) printCurrentStack(ps);
	    ps.println(str);
	}
    }
    
    public static void p(){
	if(enabled){
	    if(printPrefix) printCurrentStack(ps); // added
	    ps.println();
	}
    }
    
    public static void err(Object str){
	if(errEnabled){
	    if(printErrorPrefix){
		printCurrentStack(err);
		err.print(errPrefix);
	    }
	    err.println(str);
	}
    }
    
    public static void err(){
	if(errEnabled){
	    if(printErrorPrefix){
		printCurrentStack(err); // added
		err.print(errPrefix);
	    }
	    err.println();
	}
    }
    
    public static void debug(int level, Object str){
	if(debugEnabled && (debugLevel<0 || level<=debugLevel)){
	    if(printDebugPrefix){
		printCurrentStack(debug);
		debug.print(debugPrefix);
	    }
	    debug.println(str);
	}
    }
    
    public static void debug(int level){
	if(debugEnabled && (debugLevel<0 || level<=debugLevel)){
	    if(printDebugPrefix){
		printCurrentStack(debug);
		debug.print(debugPrefix);
	    }
	    debug.println();
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
