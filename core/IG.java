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
	// default server for geometry creator
	ICurveCreator.server(ig);
	ISurfaceCreator.server(ig);
    }
    
    public static void setCurrent(IPanel owner){
	for(int i=0; i<iglist.size(); i++){
	    if(iglist.get(i).panel == owner){
		currentId = i;

		// default server for geometry creator
		ICurveCreator.server(iglist.get(i));
		ISurfaceCreator.server(iglist.get(i));
		
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
    public static void p(Object obj){ IOut.printlnWithOffset(obj,1); }
    public static void p(){ IOut.printlnWithOffset(1); }
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
	if(!f.isAbsolute() && basePath!=null) file = basePath + File.separator + file;
	boolean retval = IIO.open(file,this);
	server.update(); // update server status
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



    /*********************************************************************
     * Geometry Operations
     ********************************************************************/
    
    
    public static ICurve curve(IVecI[] cpts, int degree, double[] knots, double ustart, double uend){
	return ICurveCreator.curve(cpts,degree,knots,ustart,uend);
    }
    
    public static ICurve curve(IVecI[] cpts, int degree, double[] knots){
	return ICurveCreator.curve(cpts,degree,knots);
    }
    
    public static ICurve curve(IVecI[] cpts, int degree){
	return ICurveCreator.curve(cpts,degree);
    }
    
    public static ICurve curve(IVecI[] cpts){
	return ICurveCreator.curve(cpts);
    }
    
    public static ICurve curve(IVecI[] cpts, int degree, boolean close){
	return ICurveCreator.curve(cpts,degree,close);
    }
    public static ICurve curve(IVecI[] cpts, boolean close){
	return ICurveCreator.curve(cpts,close);
    }
    public static ICurve curve(IVecI pt1, IVecI pt2){
	return ICurveCreator.curve(pt1,pt2);
    }
    
    public static ICurve curve(double x1, double y1, double z1, double x2, double y2, double z2){
	return ICurveCreator.curve(x1,y1,z1,x2,y2,z2);
    }
    public static ICurve curve(double[][] xyzValues){
	return ICurveCreator.curve(xyzValues);
    }    
    public static ICurve curve(double[][] xyzValues, int degree){
	return ICurveCreator.curve(xyzValues,degree);
    }
    public static ICurve curve(double[][] xyzValues, boolean close){
	return ICurveCreator.curve(xyzValues,close);
    }
    public static ICurve curve(double[][] xyzValues, int degree, boolean close){
	return ICurveCreator.curve(xyzValues,degree,close);
    }
    public static ICurve curve(ICurveI crv){
	return ICurveCreator.curve(crv);
    }


    /***********
     * curve short name : crv
     **********/
    
    public static ICurve crv(IVecI[] cpts, int degree, double[] knots, double ustart, double uend){
	return curve(cpts,degree,knots,ustart,uend);
    }
    public static ICurve crv(IVecI[] cpts, int degree, double[] knots){
	return curve(cpts,degree,knots);
    }
    public static ICurve crv(IVecI[] cpts, int degree){
	return curve(cpts,degree);
    }
    public static ICurve crv(IVecI[] cpts){ return curve(cpts); }
    
    public static ICurve crv(IVecI[] cpts, int degree, boolean close){
	return curve(cpts,degree,close);
    }
    public static ICurve crv(IVecI[] cpts, boolean close){
	return curve(cpts,close);
    }
    public static ICurve crv(IVecI pt1, IVecI pt2){ return curve(pt1,pt2); }
    public static ICurve crv(double x1, double y1, double z1, double x2, double y2, double z2){
	return curve(x1,y1,z1,x2,y2,z2);
    }
    public static ICurve crv(double[][] xyzValues){ return curve(xyzValues); }
    public static ICurve crv(double[][] xyzValues, int degree){
	return curve(xyzValues,degree);
    }
    public static ICurve crv(double[][] xyzValues, boolean close){
	return curve(xyzValues,close);
    }
    public static ICurve crv(double[][] xyzValues, int degree, boolean close){
	return curve(xyzValues,degree,close);
    }
    public static ICurve crv(ICurveI crv){ return curve(crv); }
    
    
    /************
     * rectangle
     ***********/
    public static ICurve rect(IVecI corner, double xwidth, double yheight){
	return ICurveCreator.rect(corner,xwidth,yheight);
    }
    public static ICurve rect(IVecI corner, IVecI width, IVecI height){
	return ICurveCreator.rect(corner,width,height);
    }
    public static ICurve rect(double x, double y, double z, double xwidth, double yheight){
	return ICurveCreator.rect(x,y,z,xwidth,yheight);
    }
    /************
     * circle
     ***********/
    public static ICircle circle(IVecI center, IVecI normal, IDoubleI radius){
	return ICurveCreator.circle(center,normal,radius);
    }
    public static ICircle circle(IVecI center, IVecI normal, double radius){
        return ICurveCreator.circle(center,normal,radius);
    }
    public static ICircle circle(IVecI center, IDoubleI radius){
	return ICurveCreator.circle(center,radius);
    }
    public static ICircle circle(IVecI center, double radius){
        return ICurveCreator.circle(center,radius);
    }
    public static ICircle circle(double x, double y, double z, double radius){
        return ICurveCreator.circle(x,y,z,radius);
    }
    public static ICircle circle(IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius){
        return ICurveCreator.circle(center,normal,xradius,yradius);
    }
    public static ICircle circle(IVecI center, IVecI normal, double xradius, double yradius){
	return ICurveCreator.circle(center,normal,xradius,yradius);
    }
    public static ICircle circle(IVecI center, IDoubleI xradius, IDoubleI yradius){
        return ICurveCreator.circle(center,xradius,yradius);
    }
    public static ICircle circle(IVecI center, double xradius, double yradius){
	return ICurveCreator.circle(center,xradius,yradius);
    }
    public static ICircle circle(double x, double y, double z, double xradius, double yradius){
	return ICurveCreator.circle(x,y,z,xradius,yradius);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, double radius){
	return ICurveCreator.circle(center,normal,rollDir,radius);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius){
	return ICurveCreator.circle(center,normal,rollDir,radius);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius){
	return ICurveCreator.circle(center,normal,rollDir,xradius,yradius);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius){
        return ICurveCreator.circle(center,normal,rollDir,xradius,yradius);
    }
    public static ICircle circle(IVecI center, IVecI xradiusVec, IVecI yradiusVec){
        return ICurveCreator.circle(center,xradiusVec,yradiusVec);
    }
    public static ICircle circle(IVecI center, IVecI normal, IDoubleI radius, boolean approx){
        return ICurveCreator.circle(center,normal,radius,approx);
    }
    public static ICircle circle(IVecI center, IVecI normal, double radius, boolean approx){
	return ICurveCreator.circle(center,normal,radius,approx);
    }
    public static ICircle circle(IVecI center, IDoubleI radius, boolean approx){
	return ICurveCreator.circle(center,radius,approx);
    }
    public static ICircle circle(IVecI center, double radius, boolean approx){
	return ICurveCreator.circle(center,radius,approx);
    }
    public static ICircle circle(double x, double y, double z, double radius, boolean approx){
	return ICurveCreator.circle(x,y,z,radius,approx);
    }
    public static ICircle circle(IVecI center, IVecI normal, double xradius, double yradius, boolean approx){
	return ICurveCreator.circle(center,normal,xradius,yradius,approx);
    }
    public static ICircle circle(IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius, boolean approx){
	return ICurveCreator.circle(center,normal,xradius,yradius,approx);
    }
    public static ICircle circle(IVecI center, double xradius, double yradius, boolean approx){
	return ICurveCreator.circle(center,xradius,yradius,approx);
    }
    public static ICircle circle(IVecI center, IDoubleI xradius, IDoubleI yradius, boolean approx){
	return ICurveCreator.circle(center,xradius,yradius,approx);
    }
    public static ICircle circle(double x, double y, double z, double xradius, double yradius, boolean approx){
	return ICurveCreator.circle(x,y,z,xradius,yradius,approx);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, double radius, boolean approx){
        return ICurveCreator.circle(center,normal,rollDir,radius,approx);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius, boolean approx){
	return ICurveCreator.circle(center,normal,rollDir,radius,approx);
    }
    
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius, boolean approx){
	return ICurveCreator.circle(center,normal,rollDir,xradius,yradius,approx);
    }
    
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius, boolean approx){
	return ICurveCreator.circle(center,normal,rollDir,xradius,yradius,approx);
    }
    
    public static ICircle circle(IVecI center, IVecI xradiusVec, IVecI yradiusVec, boolean approx){
	return ICurveCreator.circle(center,xradiusVec,yradiusVec,approx);
    }
    
    
    /************
     * ellipse (alias of some of circle)
     ***********/
    public static ICircle ellipse(IVecI center, IVecI xradiusVec, IVecI yradiusVec){
	return ICurveCreator.ellipse(center,xradiusVec,yradiusVec);
    }
    public static ICircle ellipse(IVecI center, IDoubleI xradius, IDoubleI yradius){
        return ICurveCreator.ellipse(center,xradius,yradius);
    }
    public static ICircle ellipse(IVecI center, double xradius, double yradius){
	return ICurveCreator.ellipse(center,xradius,yradius);
    }
    public static ICircle ellipse(double x, double y, double z, double xradius, double yradius){
	return ICurveCreator.ellipse(x,y,z,xradius,yradius);
    }
    
    
    /************
     * arc
     ***********/
    public static IArc arc(IVecI center, IVecI normal, IVecI startPt, double angle){
        return ICurveCreator.arc(center,normal,startPt,angle);
    }
    public static IArc arc(IVecI center, IVecI normal, IVecI startPt, IDoubleI angle){
	return ICurveCreator.arc(center,normal,startPt,angle);
    }
    public static IArc arc(IVecI center, IVecI startPt, double angle){
	return ICurveCreator.arc(center,startPt,angle);
    }
    public static IArc arc(IVecI center, IVecI startPt, IDoubleI angle){
	return ICurveCreator.arc(center,startPt,angle);
    }
    public static IArc arc(double x, double y, double z, double startX, double startY, double startZ, double angle){
	return ICurveCreator.arc(x,y,z,startX,startY,startZ,angle);
    }
    public static IArc arc(IVecI center, IVecI startPt, IVecI endPt, IBoolI flipArcSide){
	return ICurveCreator.arc(center,startPt,endPt,flipArcSide);
    }
    public static IArc arc(IVecI center, IVecI startPt, IVecI endPt, boolean flipArcSide){
	return ICurveCreator.arc(center,startPt,endPt,flipArcSide);
    }
    public static IArc arc(IVecI center, IVecI startPt, IVecI midPt, IVecI endPt, IVecI normal){
	return ICurveCreator.arc(center,startPt,midPt,endPt,normal);
    }
    
    
    /************
     * offset curve
     ***********/
    public static ICurve offset(ICurveI curve, double width, IVecI planeNormal){
	return ICurveCreator.offset(curve,width,planeNormal);
    }
    
    public static ICurve offset(ICurveI curve, IDoubleI width, IVecI planeNormal){
	return ICurveCreator.offset(curve,width,planeNormal);
    }
    
    public static ICurve offset(ICurveI curve, double width){
	return ICurveCreator.offset(curve,width);
    }
    
    public static ICurve offset(ICurveI curve, IDoubleI width){
	return ICurveCreator.offset(curve,width);
    }
    
    
    
    
    /*****************************************************************
     * surfaces
     *****************************************************************/
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree,
				   double[] uknots, double[] vknots,
				   double ustart, double uend, double vstart, double vend){
	return ISurfaceCreator.surface(cpts,udegree,vdegree,uknots,vknots,
				       ustart,uend,vstart,vend);
    }
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree,
				   double[] uknots, double[] vknots){
	return ISurfaceCreator.surface(cpts,udegree,vdegree,uknots,vknots);
    }
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree){
        return ISurfaceCreator.surface(cpts,udegree,vdegree);
    }
    
    public static ISurface surface(IVecI[][] cpts){
	return ISurfaceCreator.surface(cpts);
    }
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree,
				   boolean closeU, boolean closeV){
	return ISurfaceCreator.surface(cpts,udegree,vdegree,closeU,closeV);
    }
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree,
				   boolean closeU, double[] vk){
	return ISurfaceCreator.surface(cpts,udegree,vdegree,closeU,vk);
    }
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree,
				   double[] uk, boolean closeV){
	return ISurfaceCreator.surface(cpts,udegree,vdegree,uk,closeV);
    }
    
    public static ISurface surface(IVecI[][] cpts, boolean closeU, boolean closeV){
	return ISurfaceCreator.surface(cpts,closeU,closeV);
    }
    
    public static ISurface surface(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4){
	return ISurfaceCreator.surface(pt1,pt2,pt3,pt4);
    }
    
    public static ISurface surface(IVecI pt1, IVecI pt2, IVecI pt3){
	return ISurfaceCreator.surface(pt1,pt2,pt3);
    }
    
    public static ISurface surface(double x1, double y1, double z1,
				   double x2, double y2, double z2,
				   double x3, double y3, double z3,
				   double x4, double y4, double z4){
	return ISurfaceCreator.surface(x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4);
    }
    
    public static ISurface surface(double x1, double y1, double z1,
				   double x2, double y2, double z2,
				   double x3, double y3, double z3){
	return ISurfaceCreator.surface(x1,y1,z1,x2,y2,z2,x3,y3,z3);
    }
    
    public static ISurface surface(double[][][] xyzValues){
	return ISurfaceCreator.surface(xyzValues);
    }
    
    public static ISurface surface(double[][][] xyzValues, int udeg, int vdeg){
	return ISurfaceCreator.surface(xyzValues,udeg,vdeg);
    }
    
    public static ISurface surface(double[][][] xyzValues, boolean closeU, boolean closeV){
	return ISurfaceCreator.surface(xyzValues,closeU,closeV);
    }
    
    public static ISurface surface(double[][][] xyzValues, int udeg, int vdeg, boolean closeU, boolean closeV){
	return ISurfaceCreator.surface(xyzValues,udeg,vdeg,closeU,closeV);
    }
    
    public static ISurface surface(ISurfaceI srf){
	return ISurfaceCreator.surface(srf);
    }
    
    // planar surface with trim
    public static ISurface surface(ICurveI trimCurve){
	return ISurfaceCreator.surface(trimCurve);
    }
    public static ISurface surface(ICurveI[] trimCurves){
	return ISurfaceCreator.surface(trimCurves);
    }
    public static ISurface surface(IVecI[] trimCrvPts){
	return ISurfaceCreator.surface(trimCrvPts);
    }
    public static ISurface surface(IVecI[] trimCrvPts, int trimCrvDeg){
	return ISurfaceCreator.surface(trimCrvPts,trimCrvDeg);
    }
    public static ISurface surface(IVecI[] trimCrvPts, int trimCrvDeg, double[] trimCrvKnots){
	return ISurfaceCreator.surface(trimCrvPts,trimCrvDeg,trimCrvKnots);
    }
    
    
    public static ISphere sphere(double x, double y, double z, double radius){
	return ISurfaceCreator.sphere(x,y,z,radius);
    }
    
    public static ISphere sphere(IVecI center, double radius){
	return ISurfaceCreator.sphere(center,radius);
    }
	
    public static ISphere sphere(IVecI center, IDoubleI radius){
	return ISurfaceCreator.sphere(center,radius);
    }
    
    public static ICylinder cylinder(IVecI pt1, IVecI pt2, double radius){
	return ISurfaceCreator.cylinder(pt1,pt2,radius);
    }
    
    public static ICylinder cylinder(IVecI pt1, IVecI pt2, IDoubleI radius){
	return ISurfaceCreator.cylinder(pt1,pt2,radius);
    }
    
    public static ICylinder cylinder(IVecI pt1, IVecI pt2, double radius1, double radius2){
	return ISurfaceCreator.cylinder(pt1,pt2,radius1,radius2);
    }
    
    public static ICylinder cylinder(IVecI pt1, IVecI pt2, IDoubleI radius1, IDoubleI radius2){
	return ISurfaceCreator.cylinder(pt1,pt2,radius1,radius2);
    }
    
    public static ICylinder cone(IVecI pt1, IVecI pt2, double radius){
	return ISurfaceCreator.cone(pt1,pt2,radius);
    }
    
    public static ICylinder cone(IVecI pt1, IVecI pt2, IDoubleI radius){
	return ISurfaceCreator.cone(pt1,pt2,radius);
    }
    
    public static ISurface plane(IVecI corner, double xwidth, double yheight){
	return ISurfaceCreator.plane(corner,xwidth,yheight);
    }
    
    public static ISurface plane(IVecI corner, IVecI widthVec, IVecI heightVec){
	return ISurfaceCreator.plane(corner,widthVec,heightVec);
    }
    
    
    /** one directional extrusion */
    
    public static ISurface extrude(IVecI[] profile, double extrudeDepth){
	return ISurfaceCreator.extrude(profile,extrudeDepth);
    }
    public static ISurface extrude(IVecI[] profile, IDoubleI extrudeDepth){
	return ISurfaceCreator.extrude(profile,extrudeDepth);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, double extrudeDepth){
	return ISurfaceCreator.extrude(profile,profileDeg,extrudeDepth);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, IDoubleI extrudeDepth){
	return ISurfaceCreator.extrude(profile,profileDeg,extrudeDepth);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, boolean closeProfile, double extrudeDepth){
	return ISurfaceCreator.extrude(profile,profileDeg,closeProfile,extrudeDepth);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, boolean closeProfile, IDoubleI extrudeDepth){
	return ISurfaceCreator.extrude(profile,profileDeg,closeProfile,extrudeDepth);
    }
    
    public static ISurface extrude(IVecI[] profile, IVecI extrudeDir){
	return ISurfaceCreator.extrude(profile,extrudeDir);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, IVecI extrudeDir){
	return ISurfaceCreator.extrude(profile,profileDeg,extrudeDir);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, boolean closeProfile, IVecI extrudeDir){
	return ISurfaceCreator.extrude(profile,profileDeg,closeProfile,extrudeDir);
    }
    
    
    public static ISurface extrude(IVecI[] profile, int profileDeg, double[] profileKnots,
				   IVecI extrudeDir){
	return ISurfaceCreator.extrude(profile,profileDeg,profileKnots,extrudeDir);
    }
    
    public static ISurface extrude(IVecI[] profile, ICurve rail){
	return ISurfaceCreator.extrude(profile,rail);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, ICurve rail){
	return ISurfaceCreator.extrude(profile,profileDeg,rail);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, boolean closeProfile,
				   ICurve rail){
	return ISurfaceCreator.extrude(profile,profileDeg,closeProfile,rail);
    }
    
    
    public static ISurface extrude(IVecI[] profile, int profileDeg, double[] profileKnots,
				   ICurve rail){
	return ISurfaceCreator.extrude(profile,profileDeg,profileKnots,rail);
    }
    

    public static ISurface extrude(ICurveI profile, IVecI extrudeDir){
	return ISurfaceCreator.extrude(profile,extrudeDir);
    }
    
    /** extrusion along path (profile control points are copied parallely) */
    public static ISurface extrude(IVecI[] profile, IVecI[] rail){
	return ISurfaceCreator.extrude(profile,rail);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, IVecI[] rail, int railDeg){
	return ISurfaceCreator.extrude(profile,profileDeg,rail,railDeg);
    }
    
    public static ISurface extrude(IVecI[] profile, int profileDeg, boolean closeProfile,
				   IVecI[] rail, int railDeg, boolean closeRail){
	return ISurfaceCreator.extrude(profile,profileDeg,closeProfile,
				       rail,railDeg,closeRail);
    }
    
    public static ISurface extrude(IVecI[] profile, int profileDeg, double[] profileKnots,
				   IVecI[] rail, int railDeg, double[] railKnots){
	return ISurfaceCreator.extrude(profile,profileDeg,profileKnots,
				       rail,railDeg,railKnots);
    }

    public static ISurface extrude(ICurveI profile, ICurveI rail){
	return ISurfaceCreator.extrude(profile,rail);
    }
    
    
    /** sweep (profile is redirected perpendicular to rail and centered(actually just on bisector of control points)) */
    public static ISurface sweep(IVecI[] profile, IVecI[] rail){
	return ISurfaceCreator.sweep(profile,rail);
    }
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, IVecI[] rail){
	return ISurfaceCreator.sweep(profile,profileCenter,rail);
    }
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI[] rail, int railDeg){
	return ISurfaceCreator.sweep(profile,profileDeg,rail,railDeg);
    }
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI profileCenter,
				 IVecI[] rail, int railDeg){
	return ISurfaceCreator.sweep(profile,profileDeg,profileCenter,
				     rail,railDeg);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI[] rail, int railDeg, boolean closeRail){
	return ISurfaceCreator.sweep(profile,profileDeg,closeProfile,
				     rail,railDeg,closeRail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, 
				 IVecI[] rail, int railDeg, boolean closeRail){
	return ISurfaceCreator.sweep(profile,profileDeg,closeProfile,
				     profileCenter,
				     rail,railDeg,closeRail);
    }

    public static ISurface sweep(IVecI[] profile, ICurveI rail){
	return ISurfaceCreator.sweep(profile,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileCenter,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileDeg,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI profileCenter,
				 ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileDeg,profileCenter,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileDeg,closeProfile,profileCenter,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileDeg,closeProfile,rail);
    }    
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI[] rail){
	return ISurfaceCreator.sweep(profile,profileCenter,rail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI[] rail){
	return ISurfaceCreator.sweep(profile,rail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI[] rail,int railDeg){
	return ISurfaceCreator.sweep(profile,profileCenter,rail,railDeg);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI[] rail, int railDeg){
	return ISurfaceCreator.sweep(profile,rail,railDeg);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI[] rail,
				 int railDeg, boolean closeRail){
	return ISurfaceCreator.sweep(profile,profileCenter,rail,railDeg,closeRail);
    }
        
    public static ISurface sweep(ICurveI profile, IVecI[] rail, int railDeg, boolean closeRail){
	return ISurfaceCreator.sweep(profile,rail,railDeg,closeRail);
    }
    
    
    public static ISurface sweep(ICurveI profile, ICurveI rail){
	return ISurfaceCreator.sweep(profile,rail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileCenter,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, double[] profileKnots,
				 IVecI[] rail, int railDeg, double[] railKnots){
	return ISurfaceCreator.sweep(profile,profileDeg,profileKnots,
				     rail,railDeg,railKnots);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, double[] profileKnots,
				 IVecI profileCenter,
				 IVecI[] rail, int railDeg, double[] railKnots){
	return ISurfaceCreator.sweep(profile,profileDeg,profileKnots,
				     profileCenter,
				     rail,railDeg,railKnots);
    }
    
    /*********************
     * pipe
     *********************/
    
    public static ISurface pipe(IVecI[] rail, double radius){
	return ISurfaceCreator.pipe(rail,radius);
    }
    public static ISurface pipe(IVecI[] rail, int railDeg, double radius){
	return ISurfaceCreator.pipe(rail,railDeg,radius);
    }
    public static ISurface pipe(IVecI[] rail, int railDeg, boolean close, double radius){
	return ISurfaceCreator.pipe(rail,railDeg,close,radius);
    }
    public static ISurface pipe(ICurveI rail, double radius){
	return ISurfaceCreator.pipe(rail,radius);
    }
    public static ISurface pipe(IVecI[] rail, int railDeg, double[] railKnots, double radius){
	return ISurfaceCreator.pipe(rail,railDeg,railKnots,radius);
    }
    
    
    public static ISurface squarePipe(IVecI[] rail, double size){
	return ISurfaceCreator.squarePipe(rail,size);
    }
    public static ISurface squarePipe(IVecI[] rail, int deg, double size){
	return ISurfaceCreator.squarePipe(rail,deg,size);
    }
    public static ISurface squarePipe(IVecI[] rail, int deg, boolean close, double size){
	return ISurfaceCreator.squarePipe(rail,deg,close,size);
    }
    public static ISurface squarePipe(ICurveI rail, double size){
	return ISurfaceCreator.squarePipe(rail,size);
    }
    public static ISurface squarePipe(IVecI[] rail, int deg, double[] knots, double size){
	return ISurfaceCreator.squarePipe(rail,deg,knots,size);
    }
    
    
    /**
       @param width size in the direction of offset of rail
       @param height size in the direction of normal of rail
    */
    public static ISurface rectPipe(IVecI[] rail, double width, double height){
	return ISurfaceCreator.rectPipe(rail,width,height);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, double width, double height){
	return ISurfaceCreator.rectPipe(rail,deg,width,height);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, boolean close, double width, double height){
	return ISurfaceCreator.rectPipe(rail,deg,close,width,height);
    }
    public static ISurface rectPipe(ICurveI rail, double width, double height){
	return ISurfaceCreator.rectPipe(rail,width,height);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, double[] knots, double width, double height){
	return ISurfaceCreator.rectPipe(rail,deg,knots,width,height);
    }
    
    
    
    /*********************
     * loft
     *********************/
    
    public static ISurface loft(ICurveI[] curves){
	return ISurfaceCreator.loft(curves);
    }
    
    public static ISurface loft(ICurveI[] curves, int deg){
	return ISurfaceCreator.loft(curves,deg);
    }
    
    public static ISurface loft(ICurveI[] curves, int deg, boolean close){
	return ISurfaceCreator.loft(curves,deg,close);
    }
    
    public static ISurface loft(IVecI[][] pts){
	return ISurfaceCreator.loft(pts);
    }
    
    public static ISurface loft(IVecI[][] pts, boolean closeLoft, boolean closePts){
	return ISurfaceCreator.loft(pts,closeLoft,closePts);
    }
    
    public static ISurface loft(IVecI[][] pts, int loftDeg, int ptsDeg){
	return ISurfaceCreator.loft(pts,loftDeg,ptsDeg);
    }
    
    public static ISurface loft(IVecI[][] pts, int loftDeg, int ptsDeg,
				boolean closeLoft, boolean closePts){
	return ISurfaceCreator.loft(pts,loftDeg,ptsDeg,closeLoft,closePts);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2){
	return ISurfaceCreator.loft(pts1,pts2);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, boolean closePts){
	return ISurfaceCreator.loft(pts1,pts2,closePts);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, int ptsDeg){
	return ISurfaceCreator.loft(pts1,pts2,ptsDeg);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, int ptsDeg, boolean closePts){
	return ISurfaceCreator.loft(pts1,pts2,ptsDeg,closePts);
    }
        
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, IVecI[] pts3){
	return ISurfaceCreator.loft(pts1,pts2,pts3);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, IVecI[] pts3,
				boolean closePts){
	return ISurfaceCreator.loft(pts1,pts2,pts3,closePts);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, IVecI[] pts3, int loftDeg, int ptsDeg){
	return ISurfaceCreator.loft(pts1,pts2,pts3,loftDeg,ptsDeg);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, IVecI[] pts3, int loftDeg, int ptsDeg, boolean closePts){
	return ISurfaceCreator.loft(pts1,pts2,pts3,loftDeg,ptsDeg,closePts);
    }
    
    /** loft with sorted curves in x */
    public static ISurface loftX(ICurveI[] curves){
	return ISurfaceCreator.loftX(curves);
    }
    public static ISurface loftX(ICurveI[] curves, int deg){
	return ISurfaceCreator.loftX(curves,deg);
    }
    public static ISurface loftX(ICurveI[] curves, int deg, boolean close){
	return ISurfaceCreator.loftX(curves,deg,close);
    }
    /** loft with sorted curves in y */
    public static ISurface loftY(ICurveI[] curves){
	return ISurfaceCreator.loftY(curves);
    }
    public static ISurface loftY(ICurveI[] curves, int deg){
	return ISurfaceCreator.loftY(curves,deg);
    }
    public static ISurface loftY(ICurveI[] curves, int deg, boolean close){
	return ISurfaceCreator.loftY(curves,deg,close);
    }
    /** loft with sorted curves in z */
    public static ISurface loftZ(ICurveI[] curves){
	return ISurfaceCreator.loftZ(curves);
    }
    public static ISurface loftZ(ICurveI[] curves, int deg){
	return ISurfaceCreator.loftZ(curves,deg);
    }
    public static ISurface loftZ(ICurveI[] curves, int deg, boolean close){
	return ISurfaceCreator.loftZ(curves,deg,close);
    }


    /*********************************************************
     * creating vector 
     ********************************************************/
    
    public static IVec vector(double x, double y, double z){
	return new IVec(x,y,z);
    }
    public static IVec vector(IVec v){ return new IVec(v); }
    public static IVec vector(IVecI v){ return new IVec(v); }
    public static IVec vector(IDoubleI x, IDoubleI y, IDouble z){
	return new IVec(x,y,z);
    }
    public static IVec vector(IVec2I v){
	return new IVec(v);
    }
    
    /*********************************************************
     * vector short name
     ********************************************************/
    public static IVec vec(double x, double y, double z){ return vector(x,y,z); }
    public static IVec vec(IVec v){ return vector(v); }
    public static IVec vec(IVecI v){ return vector(v); }
    public static IVec vec(IDoubleI x, IDoubleI y, IDouble z){ return vector(x,y,z); }
    public static IVec vec(IVec2I v){ return vector(v); }
    
    
    
}
