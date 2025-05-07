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
import javax.imageio.*;
import igeo.*;


/**
   A class to provide an interface to all File I/O.
   
   @author Satoru Sugihara
*/
public class IIO{
    
    public enum FileType{ RHINO, OBJ, _3DXML, AI, SHP, OTHER };
    
    public static final String extensionObj = "obj";
    public static final String extensionRhino = "3dm";
    public static final String extension3DXML = "3dxml";
    public static final String extensionAI = "ai";
    public static final String extensionSHP = "shp";
    
    
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
    
    public static String getFilenameWithoutExtension(String filename){
	int idx = filename.lastIndexOf('.');
	if(idx<0) return filename;
	return filename.substring(0,idx);
    }
    
    public static FileType getFileType(String filename){
	if(isExtension(filename, extensionRhino)) return FileType.RHINO;
	if(isExtension(filename, extensionObj)) return FileType.OBJ;
	if(isExtension(filename, extension3DXML)) return FileType._3DXML;
	if(isExtension(filename, extensionAI)) return FileType.AI;
	if(isExtension(filename, extensionSHP)) return FileType.SHP;
	return FileType.OTHER;
    }
    
    
    public static ArrayList<IObject> open(String filename, IServerI server){
	return open(new File(filename), server);
    }
    
    public static ArrayList<IObject> open(File file, IServerI server){
	IOut.debug(0,"opening "+file.getPath());
	if(!file.exists()){
	    IOut.err("the input file doesn't exist : "+file.getPath());
	    return null;
	}
	FileType type = getFileType(file.getName());
	if(type == FileType.OBJ) return openOBJ(file,server);
	if(type == FileType.RHINO) return openRhino(file,server);
	if(type == FileType._3DXML) return open3DXML(file,server);
	if(type == FileType.SHP) return openShape(file,server);
	IOut.err("file extension ."+getExtension(file.getName())+" is not supported");
	return null;
    }

    
    public static ArrayList<IObject> open(String filename, IServerI server, IInputWrapper wrapper){
	return open(new File(filename), server, wrapper);
    }
    
    public static ArrayList<IObject> open(File file, IServerI server, IInputWrapper wrapper){
	if(wrapper==null) return open(file,server);
	IOut.debug(0,"opening "+file.getPath()); 
	
	InputStream is = wrapper.getStream(file);
	if(is == null){
	    if(!file.exists()){
		IOut.err("the input file doesn't exist : "+file.getPath());
		return null;
	    }
	    else{
		return open(file,server);
	    }
	}

	ArrayList<IObject> objects=null;
	
	FileType type = getFileType(file.getName());
	if(type == FileType.OBJ){
	    objects = openOBJ(is,server);
	    try{ is.close(); } catch(IOException e){ e.printStackTrace(); }
	    return objects;
	}
	if(type == FileType.RHINO){
	    objects = openRhino(is,server);
	    try{ is.close(); } catch(IOException e){ e.printStackTrace(); }
	    return objects;
	}
	if(type == FileType._3DXML){
	    objects = open3DXML(is,file.getName(),server);
	    try{ is.close(); } catch(IOException e){ e.printStackTrace(); }
	    return objects;
	    //filename = IG.current().basePath + "\\"+filename;
	    //open3DXML(filename,server); // InputStream version to be implemented
	    //return true; // ?
	}
	if(type == FileType.SHP){

	    File dbfFile1 = IShapeFileImporter.getFileWithExtension(file, "dbf");
	    InputStream dbfis = wrapper.getStream(dbfFile1);
	    
	    if(dbfis==null){
		File dbfFile2 = IShapeFileImporter.getFileWithExtension(file, "DBF");
		dbfis = wrapper.getStream(dbfFile2);
	    }
	    if(dbfis==null){
		File dbfFile3 = IShapeFileImporter.getFileWithExtension(file, "Dbf");
		dbfis = wrapper.getStream(dbfFile3);
	    }
	    if(dbfis==null){
		IG.err("DBF file does not exist : "+dbfFile1.getAbsolutePath());
	    }
	    
	    objects = openShape(is, dbfis, server);
	    try{ is.close(); } catch(IOException e){ e.printStackTrace(); }
	    return objects;
	}
	
	IOut.err("file extension ."+getExtension(file.getName())+" is not supported");
	return null;
    }
    
    public static ArrayList<IObject> openOBJ(File file, IServerI server){
	IOut.debug(0, "opening obj file "+file);
	ArrayList<IObject> objects = IObjFileImporter.read(file, server);
	if(objects!=null){
	    IOut.debug(0,"opening complete");
	    return objects;
	}
	IOut.err("error occured in opening file "+file.toString());
	return null;
    }
    
    public static ArrayList<IObject> openOBJ(InputStream istream, IServerI server){
	ArrayList<IObject> objects = IObjFileImporter.read(istream, server);
        if(objects!=null){
	    IOut.debug(0,"opening complete");
	    return objects;
	}
	IOut.err("error occured in opening OBJ file");
	return null;
    }
    
    public static ArrayList<IObject> openRhino(File file, IServerI server){
	IOut.debug(0, "opening 3dm file "+file);
	ArrayList<IObject> objects = IRhino3dmImporter.read(file,server);
	if(objects!=null){
	    IOut.debug(0,"opening complete");
	    return objects;
	}
	IOut.err("error occured in opening file "+file.toString());
	return objects;
    }
    
    public static ArrayList<IObject> openRhino(InputStream istream, IServerI server){
	ArrayList<IObject> objects = IRhino3dmImporter.read(istream,server);
	if(objects!=null){
	    IOut.debug(0,"opening complete");
	    return objects;
	}
	IOut.err("error occured in opening Rhino file"); 
        return objects;
    }
    
    public static ArrayList<IObject> open3DXML(File file, IServerI server){
	IOut.debug(0, "opening 3dxmm file "+file);
	try{
	    I3DXML doc = I3DXMLImporter.read(file);
	    IOut.debug(0,"opening complete");
	    ArrayList<IObject> objects = new ArrayList<IObject>();
	    for(int i=0; i<doc.objects.size(); i++){ objects.add(doc.objects.get(i)); }
	    return objects;
	}catch(IOException e){ e.printStackTrace(); }
	return null;
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
    public static ArrayList<IObject> open3DXML(InputStream istream, String filename,
					       IServerI server){
	try{
	    I3DXML doc = I3DXMLImporter.read(istream, filename);
	    IOut.debug(0,"opening complete");
	    ArrayList<IObject> objects = new ArrayList<IObject>();
	    for(int i=0; i<doc.objects.size(); i++){ objects.add(doc.objects.get(i)); }
	    return objects;
	}catch(IOException e){ e.printStackTrace(); }
	return null;
    }


    public static ArrayList<IObject> openShape(File file, IServerI server){
	IOut.debug(0, "opening obj file "+file);
	ArrayList<IObject> objects = IShapeFileImporter.read(file, server);
	if(objects!=null){
	    IOut.debug(0,"opening complete");
	    return objects;
	}
	IOut.err("error occured in opening file "+file.toString());
	return null;
    }
    
    public static ArrayList<IObject> openShape(InputStream shpStream, InputStream dbfStream, IServerI server){
	ArrayList<IObject> objects = IShapeFileImporter.read(shpStream, dbfStream, server);
        if(objects!=null){
	    IOut.debug(0,"opening complete");
	    return objects;
	}
	IOut.err("error occured in opening Shape file");
	return null;
    }
    
    
    public static boolean save(String filename, IServerI server){
	return save(filename,server,false);
    }
    public static boolean save(String filename, IServerI server, boolean saveAllTexture){
	if(saveAllTexture){ saveTextures(filename, server); } 
	FileType type = getFileType(filename);
	if(type == FileType.OBJ) return saveOBJ(new File(filename),server);
	if(type == FileType.RHINO) return saveRhino(new File(filename),server);
	if(type == FileType.AI) return saveAI(new File(filename),server);
	IOut.err("file extension ."+getExtension(filename)+" is not supported");
	return false;
    }
    
    public static boolean save(File file, IServerI server){
	return save(file,server,false);
    }
    public static boolean save(File file, IServerI server, boolean saveAllTexture){
	if(saveAllTexture){ saveTextures(file, server); } 
	FileType type = getFileType(file.getName());
	if(type == FileType.OBJ) return saveOBJ(file,server);
	if(type == FileType.RHINO) return saveRhino(file,server);
	if(type == FileType.AI) return saveAI(file,server); 
	IOut.err("file extension ."+getExtension(file.getName())+" is not supported");
	return false;
    }
    
    public static boolean saveOBJ(String filename, IServerI server){
	return saveOBJ(new File(filename), server);
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
    
    public static boolean saveRhino(String filename, IServerI server){
	return saveRhino(new File(filename), server);
    }
    
    public static boolean saveRhino(File file, IServerI server){ return saveRhino5(file,server); } // v5 is default
    
    public static boolean saveRhino4(String filename, IServerI server){
	return saveRhino4(new File(filename), server);
    }
    public static boolean saveRhino4(File file, IServerI server){
	IOut.debug(0,"saving 3dm file(v4): "+file);
	int version = 4;
	if(IRhino3dmExporter.write(file,server,version)){
	    IOut.debug(0,"saving complete"); 
	    return true;
	}
	IOut.err("error occured in saving file "+file.toString());
	return false;
    }
    
    public static boolean saveRhino5(String filename, IServerI server){
	return saveRhino5(new File(filename), server);
    }
    public static boolean saveRhino5(File file, IServerI server){
	IOut.debug(0,"saving 3dm file(v5): "+file);
	int version = 5;
	if(IRhino3dmExporter.write(file,server,version)){
	    IOut.debug(0,"saving complete"); 
	    return true;
	}
	IOut.err("error occured in saving file "+file.toString());
	return false;
    }
    
    public static boolean saveAI(String filename, IServerI server){
	return saveAI(new File(filename), server);
    }
    public static boolean saveAI(File file, IServerI server){
	return saveAI(file,server,IConfig.defaultAIExportScale);
    }
    
    public static boolean saveAI(String filename, IServerI server, double scale){
	return saveAI(new File(filename), server, scale);
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
    
    public static boolean saveTextures(File file, IServerI server){
	return saveTexturesAsPNG(file.getName(), server);
    }
    public static boolean saveTextures(String filename, IServerI server){
	return saveTexturesAsPNG(filename, server);
    }
    public static boolean saveTexturesAsPNG(String filename, IServerI server){
	int objectNum = server.server().objectNum();
	
	String basename = getFilenameWithoutExtension(filename);
	int objCount=0;
	boolean success = true;
	for(int i=0; i<objectNum; i++){
	    IObject obj = server.server().getObject(i);
	    IAttribute attr = obj.attr();
	    if(attr!=null && attr.texture!=null && attr.texture.image!=null){
		String filePath = basename+"_"+String.valueOf(i)+".png";
		filePath = IG.cur().formatOutputFilePath(filePath);
		IOut.debug(0,"saving png file: "+filePath);
		try{ ImageIO.write(attr.texture.image,"png",new File(filePath)); }
		catch(IOException ioe){ ioe.printStackTrace(); success=false; }
	    }
	}
	return success;
    }
}
