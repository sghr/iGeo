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

import java.awt.Container;
import java.awt.Component;
import java.util.*;
import java.io.*;

import igeo.gui.*;
import igeo.geo.*;
import igeo.io.*;

/**
   A main interface to background process of iGeo.
   A single IG instance can be accessed through static methods although
   multiple IG instance also can be contained in the static variable iglist in case
   multiple object servers are needed or simultaneous execution of multiple applets. 
   One IG instance contains one IServer as object database and one IPanel as
   display window. The member variable of IPanel can be null when no display
   window is needed. 
   
   @see IServer
   @see IPanel
   
   @author Satoru Sugihara
   @version 0.7.1.0
*/
public class IG implements IServerI{
    
    public static int majorVersion(){ return 0; }
    public static int minorVersion(){ return 7; }
    public static int buildVersion(){ return 0; }
    public static int revisionVersion(){ return 0; }
    public static Calendar versionDate(){ return new GregorianCalendar(2011, 8, 31); }
    public static String version(){
	return String.valueOf(majorVersion())+"."+String.valueOf(minorVersion())+"."+
	    String.valueOf(buildVersion())+"."+String.valueOf(revisionVersion());
    }
    
    /************************************
     * static variables
     ************************************/
    public static final Object lock = new Object();
    
    public static final String GL = "igeo.p.PIGraphicsGL"; // for processing graphics
    //public static final String JAVA = "igeo.p.PIGraphicsJava"; // for processing graphics
    
    protected static ArrayList<IG> iglist=null;
    protected static int currentId = -1;
    
    /************************************
     * object variables
     ************************************/
    /*protected*/ public IServer server;
    /*protected*/ public IPanel panel = null;
    
    /*protected*/ public String inputFile;
    /*protected*/ public String outputFile;
    
    public String basePath = ".";
    
    /* *
       initialize whole IG system with IServer and graphical components
       instance of IG should be held by IGPane
       
       @param
       owner: if owner contains IGPane, the instance of IG is kept by it.
              if not, IGPane is instantiated and the instance of IG is kept by it.
	      if the ownwer is null, IGPane and all other graphical components are not instantiated.
       
    */
    /*
    public static IG init(Container owner){
	if(iglist==null) iglist = new ArrayList<IG>();
	
	IG ig = null;
	if(owner != null){
	    IGPane p = findIGPane(owner);
	    if(p==null) p = new IGPane(owner);
	    ig = new IG(p);
	}
	else ig = new IG();
	
	iglist.add(ig);
	currentId = iglist.size()-1;
	
	return ig;
    }
    
    
    public static IGPane findIGPane(Container container){
	final int defaultSearchDepth = 0;
	return findIGPane(container, defaultSearchDepth);
    }
    
    public static IGPane findIGPane(Container container, int searchDepth){
	Component[] components = container.getComponents();
	for(Component c : components){
	    if(c instanceof IGPane) return (IGPane)c;
	    else if(searchDepth>0 && c instanceof Container){
		IGPane p = findIGPane((Container)c, searchDepth-1);
		if(p!=null) return p;
	    }
	}
	return null;
    }
    */
    
    
    /***********************************************************************
     * static methods
     ***********************************************************************/
    
    /**
       Initialize whole IG system in non-graphic mode.
    */
    public static IG init(){
	if(iglist==null) iglist = new ArrayList<IG>();
	IG ig = new IG();
	iglist.add(ig);
	currentId = iglist.size()-1;
	return ig;
    }
    
    /**
       Initialize whole IG system in graphic mode.
       Please instantiate IPanel beforehand.
    */
    public static IG init(IPanel owner){
	if(iglist==null) iglist = new ArrayList<IG>();
	IG ig = new IG(owner);
	iglist.add(ig);
	currentId = iglist.size()-1;
	return ig;
    }
    
    
    /**
       Find the IG instance which is likely to be the current.
    */
    
    public static IG current(){
	if(iglist==null || currentId<0 || currentId>=iglist.size()) return null;
	return iglist.get(currentId);
    }
    
    public static void setCurrent(IG ig){
	int idx = iglist.indexOf(ig);
	if(idx>=0 && idx<iglist.size()) currentId = idx;
	else{ // not in the list
	    // add? really?
	    iglist.add(ig);
	    currentId = iglist.size()-1;
	}
    }
    
    public static void setCurrent(IPanel owner){
	for(int i=0; i<iglist.size(); i++){
	    if(iglist.get(i).panel == owner){
		currentId = i;
		return;
	    }
	}
	IOut.err("no IG instance found for "+owner);
    }
    
    
    /**
       Find IG instance linked with the specified IPanel instance.
    */
    public static IG getIG(IPanel owner){
	for(IG ig : iglist) if(ig.panel == owner) return ig;
	return null;
    }
    
    
    public static boolean open(String file){
	IG ig = current();
	if(ig==null) return false;
	return ig.openFile(file);
    }
    
    public static boolean save(String file){
	IG ig = current();
	if(ig==null) return false;
	return ig.saveFile(file);
    }
    
    /** to set the name first and save later (likely by key event)
     */
    public static void outputFile(String filename){
	IG ig = current();
	if(ig!=null) ig.setOutputFile(filename);
    }
    public static String outputFile(){
	IG ig = current();
	if(ig==null) return null;
	return ig.getOutputFile();
    }
    public static void inputFile(String filename){
	IG ig = current();
	if(ig!=null) ig.setInputFile(filename);
    }
    public static String inputFile(){
	IG ig = current();
	if(ig==null) return null;
	return ig.getInputFile();
    }
    
    public static IPoint[] points(){
	IG ig = current(); if(ig==null) return null; return ig.getPoints();
    }
    public static ICurve[] curves(){
	IG ig = current(); if(ig==null) return null; return ig.getCurves();
    }
    public static ISurface[] surfaces(){
	IG ig = current(); if(ig==null) return null; return ig.getSurfaces();
    }
    public static IMesh[] meshes(){
	IG ig = current(); if(ig==null) return null; return ig.getMeshes();
    }
    public static IObject[] objects(Class cls){
	IG ig = current(); if(ig==null) return null; return ig.getObjects(cls);
    }
    
    
    public static ILayer layer(String layerName){
	IG ig = current();
	if(ig==null) return null;
	return ig.getLayer(layerName);
    }
    public static ILayer[] layers(){
	IG ig = current();
	if(ig==null) return null;
	return ig.getAllLayers();
    }
    public static void delLayer(String layerName){
	IG ig = current();
	if(ig==null) return;
	ig.removeLayer(layerName);
    }
    
    public static void focus(){
	IG ig = current();
	if(ig==null) return;
	ig.focusView();
    }
    
    
    
    public static boolean isGL(){
	IG ig = current();
	if(ig==null){
	    IOut.err("no IG found");
	    return true; // GL is default
	}
	if(ig.server().graphicServer()==null){
	    IOut.err("no graphic server found");
	    return true; // GL is default
	}
	return ig.server().graphicServer().isGL();
    }
    public static void graphicMode(IGraphicMode mode){
	IG ig = current(); if(ig==null) return;
	ig.server().setGraphicMode(mode);
    }
    public static void wireframe(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.JAVA;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,false,true,false));
    }
    public static void fill(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.JAVA;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,true,false,false));
    }
    public static void fillWithWireframe(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.JAVA;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,true,true,false));
    }
    public static void transparentFill(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.JAVA;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,true,false,true));
    }
    public static void transparentFillWithWireframe(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.JAVA;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,true,true,true));
    }
        
    //public static void setBG(Color c){}
    //public static void setBG(Color c1, Color c2){}
    //public static void setBG(Color c1, Color c2, Color c3, Color c4){}
    //public static void setBG(Image img){}
    
    /** Print method.
	This is a wrapper of IOut.p(), which is 
	also a wrapper of System.out.println() in most part.
    */
    public static void p(Object obj){ IOut.p(obj); }
    public static void p(){ IOut.p(); }
    public static void enabePrintPrefix(){ IOut.enablePrefix(); }
    public static void disablePrintPrefix(){ IOut.disablePrefix(); }
    
    
    
    /*************************************************************************
     * object methods
     *************************************************************************/
    
    // anybody would want this in public?
    protected IG(){
	server = new IServer(this);
    }
    
    protected IG(IPanel p){
	server = new IServer(this, p);
	panel = p; // 
	p.setIG(this);
    }
    
    public boolean openFile(String file){
	File f = new File(file);
	if(!f.isAbsolute() && basePath!=null)
	    file = basePath + File.separator + file;
	boolean retval = IIO.open(file,this);
	inputFile = file;
	focusView();
	return retval;
    }
    
    public boolean saveFile(String file){
	File f = new File(file);
	if(!f.isAbsolute() && basePath!=null){
	    file = basePath + File.separator + file;
	    File baseDir = new File(basePath);
	    if(!baseDir.isDirectory()){
		IOut.debug(20, "creating directory"+baseDir.toString());
		if(!baseDir.mkdir()){
		    IOut.err("failed to create directory: "+baseDir.toString());
		}
	    }
	}
	return IIO.save(file,this);
    }
    
    public boolean save(){
	if(outputFile==null){
	    IOut.err("output filename is not set. not saved");
	    return false;
	}
	return saveFile(outputFile);
    }
    
    public void setInputFile(String filename){ inputFile=filename; }
    public void setOutputFile(String filename){ outputFile=filename; }
    public String getInputFile(){ return inputFile; }
    public String getOutputFile(){ return outputFile; }
    
    public String getBasePath(){ return basePath; }
    public String setBasePath(String path){ return basePath=path; }
    
    public ILayer getLayer(String layerName){ return server.getLayer(layerName); }
    public ILayer[] getAllLayers(){ return server.getAllLayers(); }
    public void removeLayer(String layerName){ server.removeLayer(layerName); }
    
    
    public IPoint[] getPoints(){ return server.getPoints(); }
    public ICurve[] getCurves(){ return server.getCurves(); }
    public ISurface[] getSurfaces(){ return server.getSurfaces(); }
    public IMesh[] getMeshes(){ return server.getMeshes(); }
    public IObject[] getObjects(Class cls){ return server.getObjects(cls); }

    
    public void focusView(){
	if(panel!=null) panel.focus(); // focus on all pane
    }
    
    public IServer server(){ return server; }
    
    //public void draw(IGraphics g){ server.draw(g); }
    
    //public IGPane pane(){ return pane; }
    //public IPanel panel(){ return panel; }
    
    //public void delete(){
    public void clear(){ server.clear(); }
    
}
