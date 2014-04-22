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

import igeo.*;
/**
   A class to provide an interface to all File I/O.
   
   @author Satoru Sugihara
*/
public class IIO{
    
    public enum FileType{ RHINO, OBJ, _3DXML, AI, OTHER };
    
    public static final String extensionObj = "obj";
    public static final String extensionRhino = "3dm";
    public static final String extension3DXML = "3dxml";
    public static final String extensionAI = "ai";
    
    
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
	if(isExtension(filename, extension3DXML)) return FileType._3DXML;
	if(isExtension(filename, extensionAI)) return FileType.AI;
	return FileType.OTHER;
    }
    
    
    public static boolean open(String filename, IServerI server){
	return open(new File(filename), server);
    }
    
    public static boolean open(File file, IServerI server){
	IOut.debug(0,"opening "+file.getPath());
	if(!file.exists()){
	    IOut.err("the input file doesn't exist : "+file.getPath());
	    return false;
	}
	FileType type = getFileType(file.getName());
	if(type == FileType.OBJ) return openOBJ(file,server);
	if(type == FileType.RHINO) return openRhino(file,server);
	if(type == FileType._3DXML) return open3DXML(file,server);
	IOut.err("file extension ."+getExtension(file.getName())+" is not supported");
	return false;
    }

    
    public static boolean open(String filename, IServerI server, IInputWrapper wrapper){
	return open(new File(filename), server, wrapper);
    }
    
    public static boolean open(File file, IServerI server, IInputWrapper wrapper){
	if(wrapper==null) return open(file,server);
	IOut.debug(0,"opening "+file.getPath()); 
	
	InputStream is = wrapper.getStream(file);
	if(is == null){
	    if(!file.exists()){
		IOut.err("the input file doesn't exist : "+file.getPath());
		return false;
	    }
	    else{
		return open(file,server);
	    }
	}
	
	FileType type = getFileType(file.getName());
	if(type == FileType.OBJ){
	    boolean retval = openOBJ(is,server);
	    try{ is.close(); } catch(IOException e){ e.printStackTrace(); }
	    return retval;
	}
	if(type == FileType.RHINO){
	    boolean retval = openRhino(is,server);
	    try{ is.close(); } catch(IOException e){ e.printStackTrace(); }
	    return retval;
	}
	if(type == FileType._3DXML){
	    boolean retval = open3DXML(is,file.getName(),server);
	    try{ is.close(); } catch(IOException e){ e.printStackTrace(); }
	    return retval;
	    
	    //filename = IG.current().basePath + "\\"+filename;
	    //open3DXML(filename,server); // InputStream version to be implemented
	    //return true; // ?
	}
	
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
    
    public static boolean openOBJ(InputStream istream, IServerI server){
	if(IObjFileImporter.read(istream, server)!=null){
	    IOut.debug(0,"opening complete");
	    return true;
	}
	IOut.err("error occured in opening OBJ file");
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
    
    public static boolean openRhino(InputStream istream, IServerI server){
	if(IRhino3dmImporter.read(istream,server)){
	    IOut.debug(0,"opening complete");
	    return true;
	}
	IOut.err("error occured in opening Rhino file"); 
	return false;
    }
    
    public static boolean open3DXML(File file, IServerI server){
	IOut.debug(0, "opening 3dxmm file "+file);
	try{
	    I3DXMLImporter.read(file);
	    IOut.debug(0,"opening complete");
	    return true;
	}catch(IOException e){ e.printStackTrace(); }
	return false;
	/*
	if(I3DXMLImporter.read(file)){
	    IOut.debug(0,"opening complete");
	    return true;
	}
	IOut.err("error occured in opening file "+file.toString());
	return false;
	*/
    }
    /*
    public static boolean open3DXML(String file, IServerI server){
	IOut.debug(0, "opening 3dxmm file "+file);
	try{
	    I3DXMLImporter.read(file);
	    IOut.debug(0,"opening complete");
	    return true;
	}catch(IOException e){ e.printStackTrace(); }
	return false;
    }
    */
    public static boolean open3DXML(InputStream istream, String filename,
				    IServerI server){
	try{
	    I3DXMLImporter.read(istream, filename);
	    IOut.debug(0,"opening complete");
	    return true;
	}catch(IOException e){ e.printStackTrace(); }
	return false;
    }
    
    public static boolean save(String filename, IServerI server){
	FileType type = getFileType(filename);
	if(type == FileType.OBJ) return saveOBJ(new File(filename),server);
	if(type == FileType.RHINO) return saveRhino(new File(filename),server);
	if(type == FileType.AI) return saveAI(new File(filename),server);
	IOut.err("file extension ."+getExtension(filename)+" is not supported");
	return false;
    }
    
    public static boolean save(File file, IServerI server){
	FileType type = getFileType(file.getName());
	if(type == FileType.OBJ) return saveOBJ(file,server);
	if(type == FileType.RHINO) return saveRhino(file,server);
	if(type == FileType.AI) return saveAI(file,server); 
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
    
    public static boolean saveAI(File file, IServerI server){
	return saveAI(file,server,IConfig.defaultAIExportScale);
    }
    
    public static boolean saveAI(File file, IServerI server, double scale){
	IOut.debug(0,"saving ai file: "+file);
	if(IAIExporter.write(file,server,scale)){
	    IOut.debug(0,"saving complete"); 
	    return true;
	}
	IOut.err("error occured in saving file "+file.toString());
	return false;
    }
    
}
