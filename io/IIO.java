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

package igeo.io;

import java.io.*;

import igeo.core.*;
import igeo.gui.*;
/**
   A class to provide an interface to all File I/O.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IIO{
    
    public enum FileType{ RHINO, OBJ, OTHER };
    
    public static final String extensionObj = "obj";
    public static final String extensionRhino = "3dm";
    
    
    public static boolean isExtension(String filename, String extension){
	String ext = getExtension(filename);
	if(ext==null) return false;
	return ext.toLowerCase().equals(extension.toLowerCase());
    }
    
    public static String getExtension(String filename){
	String[] seg = filename.split("\\.");
	if(seg.length<2) return null;
	return seg[seg.length-1];
    }
    
    public static FileType getFileType(String filename){
	if(isExtension(filename, extensionRhino)) return FileType.RHINO;
	if(isExtension(filename, extensionObj)) return FileType.OBJ;
	return FileType.OTHER;
    }
    
    
    public static boolean open(String filename, IServerI server){
	IOut.debug(0,"opening "+filename);
	FileType type = getFileType(filename);
	if(type == FileType.OBJ) return openOBJ(new File(filename),server);
	if(type == FileType.RHINO) return openRhino(new File(filename),server);
	
	IOut.err("file extension ."+getExtension(filename)+" is not supported");
	return false;
    }
    
    public static boolean open(File file, IServerI server){
	FileType type = getFileType(file.getName());
	if(type == FileType.OBJ) return openOBJ(file,server);
	if(type == FileType.RHINO) return openRhino(file,server);
	IOut.err("file extension ."+getExtension(file.getName())+" is not supported");
	return false;
    }
    
    public static boolean openOBJ(File file, IServerI server){
	IOut.debug(0, "opening obj file "+file);
	if(IObjFileImporter.read(file, server)!=null){
	    IOut.debug(0,"opening complete");
	    return true;
	}
	IOut.err("error occured in opening file "+file.toString());
	return false;
    }
    
    public static boolean openRhino(File file, IServerI server){
	IOut.debug(0, "opening 3dm file "+file);
	if(IRhino3dmImporter.read(file,server)){
	    IOut.debug(0,"opening complete");
	    return true;
	}
	IOut.err("error occured in opening file "+file.toString());
	return false;
    }
    
    public static boolean save(String filename, IServerI server){
	FileType type = getFileType(filename);
	if(type == FileType.OBJ){
	    boolean ret = saveOBJ(new File(filename),server);
	    return ret;
	}
	if(type == FileType.RHINO){
	    boolean ret = saveRhino(new File(filename),server);
	    return ret;
	}
	IOut.err("file extension ."+getExtension(filename)+" is not supported");
	return false;
    }
    
    public static boolean save(File file, IServerI server){
	FileType type = getFileType(file.getName());
	if(type == FileType.OBJ) return saveOBJ(file,server);
	if(type == FileType.RHINO) return saveRhino(file,server); 
	
	IOut.err("file extension ."+getExtension(file.getName())+" is not supported");
	return false;
    }
    
    
    public static boolean saveOBJ(File file, IServerI server){
	IOut.debug(0,"saving obj file: "+file);
	if(IObjFileExporter.write(file,server)){
	    IOut.debug(0,"saving complete"); 
	    return true;
	}
	IOut.err("error occured in saving file "+file.toString());
	return false;
    }
    
    public static boolean saveRhino(File file, IServerI server){
	IOut.debug(0,"saving 3dm file: "+file);
	if(IRhino3dmExporter.write(file,server)){
	    IOut.debug(0,"saving complete"); 
	    return true;
	}
	IOut.err("error occured in saving file "+file.toString());
	return false;
    }
    
}
