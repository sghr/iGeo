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

import java.awt.*;
import java.util.*;
import java.io.*;

import java.lang.reflect.Array;

import igeo.gui.*;
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
*/
public class IG implements IServerI{
    
    public static int majorVersion(){ return 0; }
    public static int minorVersion(){ return 9; }
    public static int buildVersion(){ return 0; }
    public static int revisionVersion(){ return 9; }
    public static Calendar versionDate(){ return new GregorianCalendar(2014, 4, 22); }
    public static String version(){
	return String.valueOf(majorVersion())+"."+String.valueOf(minorVersion())+"."+
	    String.valueOf(buildVersion())+"."+String.valueOf(revisionVersion());
    }
    
    /************************************
     * static system variables
     ************************************/
    public static final Object lock = new Object();
    
    /** Processing Graphics using OpenGL to be put in size() method in Processing */
    public static final String GL1 = "igeo.p.PIGraphicsGL1";
    public static final String GL2 = "igeo.p.PIGraphicsGL2";
    //public static final String GL = GL1; // switch before compile for target // for processing 1.5
    public static final String GL = GL2; // switch before compile for target // for processing 2.0
    
    /** Processing Graphics using P3D to be put in size() method in Processing; under development. do not use yet. */
    //public static final String P3D = "igeo.p.PIGraphics3D";
    
    /** alias of IG.P3D */
    //public static final String P3 = P3D;
    
    /** Processing Graphics using JAVA to be put in size() method in Processing; to be implemented */
    //public static final String JAVA = "igeo.p.PIGraphicsJava";
    
    /** multiple IG instances are stored in iglist and switched by IG static methods
	in case of applet execution or other occasion but handling of multiple IG
	instances and switching are not really tested. */
    protected static ArrayList<IG> iglist=null;
    protected static int currentId = -1;
    
    
    /************************************
     * static geometry variables
     ************************************/
    /** x-axis vector. do not modify the content. */
    public static final IVec xaxis = IVec.xaxis;
    /** y-axis vector. do not modify the content. */
    public static final IVec yaxis = IVec.yaxis;
    /** z-axis vector. do not modify the content. */
    public static final IVec zaxis = IVec.zaxis;
    /** origin vector. do not modify the content. */
    public static final IVec origin = IVec.origin;
    /** alias of x-axis vector */
    public static final IVec x = IVec.xaxis;
    /** alias of y-axis vector */
    public static final IVec y = IVec.yaxis;
    /** alias of z-axis vector */
    public static final IVec z = IVec.zaxis;
    /** alias of origin vector */
    public static final IVec o = IVec.origin;
    
    
    /************************************
     * object variables
     ************************************/
    /*protected*/ public IServer server;
    /*protected*/ public IPanelI panel = null;
    
    /*protected*/ public String inputFile;
    /*protected*/ public String outputFile;
    
    /** base file path for file I/O */
    public String basePath = null; //".";
    
    /** wrapping inputs in different environment. replacing basePath. */
    public IInputWrapper inputWrapper=null;
    
    /** if it's applet. online == true */
    public boolean online=false; 
    
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
    
    /** Initialize whole IG system in graphic mode.
	Please instantiate IPanel beforehand.
    */
    public static IG init(IPanelI owner){
	if(iglist==null) iglist = new ArrayList<IG>();
	IG ig = new IG(owner);
	iglist.add(ig);
	currentId = iglist.size()-1;
	return ig;
    }
    
    
    /** alias of cur() */
    public static IG current(){ return cur(); }
    
    /** Find the IG instance which is likely to be the current. */
    public static IG cur(){
	if(iglist==null || currentId<0 || currentId>=iglist.size()) return null;
	return iglist.get(currentId);
    }

    /** object to be used to lock in "synchronized" statement */
    public static IG defaultThread(){ return cur(); }
    
    /** object to be used to lock in "synchronized" statement */
    public static IDynamicServer dynamicThread(){
	IG ig = cur(); if(ig==null) return null; return ig.dynamicServer();
    }
    /** alias of dynamicThread() */
    public static IDynamicServer updateThread(){ return dynamicThread(); }
    
    
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
	IMeshCreator.server(ig);
    }
    
    public static void setCurrent(IPanelI owner){
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
    
    
    /** Find IG instance linked with the specified IPanel instance. */
    public static IG getIG(IPanelI owner){
	for(IG ig : iglist) if(ig.panel == owner) return ig;
	return null;
    }
    
    public static boolean open(String file){
	IG ig = cur();
	if(ig==null) return false;
	return ig.openFile(file);
    }
    
    public static boolean save(String file){
	IG ig = cur();
	if(ig==null) return false;
	return ig.saveFile(file);
    }
    
    
    // dynamics methods
    /** set duration of dynamics update */
    public static void duration(int dur){ IG ig=cur(); if(ig!=null) ig.setDuration(dur); }
    /** get duration of dynamics update */
    public static int duration(){ IG ig=cur(); return ig==null?0:ig.getDuration(); }
    
    /** set current time count of dynamics update. recommeded not to chage time. */
    public static void time(int tm){ IG ig=cur(); if(ig!=null) ig.setTime(tm); }
    /** get current time count of dynamics update */
    public static int time(){ IG ig=cur(); return ig==null?-1:ig.getTime(); }
    
    /** pause dynamics update. */
    public static void pause(){ IG ig=cur(); if(ig!=null) ig.pauseDynamics(); }
    /** resume dynamics update. */
    public static void resume(){ IG ig=cur(); if(ig!=null) ig.resumeDynamics(); }
    /** check if dynamics is running */
    public static boolean isRunning(){ IG ig=cur(); if(ig!=null){ return ig.isDynamicsRunning(); } return false; }
    
    /** start dynamics update. if IConfig.autoStart is true, this should not be used. */
    public static void start(){ IG ig=cur(); if(ig!=null) ig.startDynamics(); }
    /** stop dynamics update. recommended not to use this because stopping should be done by setting duration. */
    public static void stop(){ IG ig=cur(); if(ig!=null) ig.stopDynamics(); }
    
    
    /** setting update rate time interval in second */
    public static void updateRate(double second){ IConfig.updateRate=second; }
    /** alias of updateRate() */
    public static void updateSpeed(double second){ updateRate(second); }
    /** alias of updateRate() */
    public static void rate(double second){ updateRate(second); }
    /** alias of updateRate() */
    public static void speed(double second){ updateRate(second); }
    
    /** getting update rate time interval in second */
    public static double updateRate(){ return IConfig.updateRate; }
    /** alias of updateRate() */
    public static double updateSpeed(){ return updateRate(); }
    /** alias of updateRate() */
    public static double rate(){ return updateRate(); }
    /** alias of updateRate() */
    public static double speed(){ return updateRate(); }
    
    
    /** getting unit of current IG server */
    public static IUnit unit(){
	IG ig=cur(); if(ig!=null) return ig.server().unit(); 
	return null;
    }
    /** setting unit of current IG server */
    public static void unit(IUnit u){
	IG ig=cur(); if(ig!=null) ig.server().unit(u); 
    }

    /** setting unit of current IG server */
    public static void unit(IUnit.Type u){
	IG ig=cur(); if(ig!=null) ig.server().unit(u); 
    }

    /** setting unit of current IG server */
    public static void unit(String unitName){
	IG ig=cur(); if(ig!=null) ig.server().unit(unitName); 
    }
    
    
    /** to set the name first and save later (likely by key event) */
    public static void outputFile(String filename){
	IG ig = cur();
	if(ig!=null) ig.setOutputFile(filename);
    }
    public static String outputFile(){
	IG ig = cur();
	if(ig==null) return null;
	return ig.getOutputFile();
    }
    public static void inputFile(String filename){
	IG ig = cur();
	if(ig!=null) ig.setInputFile(filename);
    }
    public static String inputFile(){
	IG ig = cur();
	if(ig==null) return null;
	return ig.getInputFile();
    }
    
    /** get all points in the current server */
    public static IPoint[] points(){
	IG ig = cur(); return ig==null?null:ig.getPoints();
    }
    /** get all points in the current server; alias */
    public static IPoint[] pts(){ return points(); }
    
    /** get all curves in the current server */
    public static ICurve[] curves(){
	IG ig = cur(); return ig==null?null:ig.getCurves();
    }
    /** get all curves in the current server; alias */
    public static ICurve[] crvs(){ return curves(); }
    
    /** get all curves in the current server 
	Note that IPolycurve contains multiple ICurve and they show up in curves() as well.
	ICurve - IPolycurve relationship is still under work. This is temporary measure.
    */
    public static IPolycurve[] polycurves(){
	IG ig = cur(); return ig==null?null:ig.getPolycurves();
    }
    /** get all curves in the current server; alias.
	Note that IPolycurve contains multiple ICurve and they show up in curves() as well.
        ICurve - IPolycurve relationship is still under work. This is temporary measure.
    */
    //public static ICurve[] pcrvs(){ return polycurves(); }
    
    /** get all surfaces in the current server */
    public static ISurface[] surfaces(){
	IG ig = cur(); return ig==null?null:ig.getSurfaces();
    }
    /** get all surfaces in the current server; alias */
    public static ISurface[] srfs(){ return surfaces(); }
    
    /** get all meshes in the current server */
    public static IMesh[] meshes(){
	IG ig = cur(); return ig==null?null:ig.getMeshes();
    }
    /** get all breps in the current server */
    public static IBrep[] breps(){
	IG ig = cur(); return ig==null?null:ig.getBreps();
    }
    /** get all texts in the current server */
    public static IText[] texts(){
	IG ig = cur(); return ig==null?null:ig.getTexts();
    }
    /** get all geometry objects in the current server */
    public static IGeometry[] geometries(){
	IG ig = cur(); return ig==null?null:ig.getGeometries();
    }
    /** get all geometry objects in the current server */
    public static IGeometry[] geos(){ return geometries(); }
    
    /** get all objects of the specified class in the current server */
    public static IObject[] objects(Class cls){
	IG ig = cur(); return ig==null?null:ig.getObjects(cls);
    }
    /** get all objects of the specified class in the current server; alias */
    public static IObject[] objs(Class cls){ return objects(); }
    
    /** get all objects in the current server */
    public static IObject[] objects(){
	IG ig = cur(); return ig==null?null:ig.getObjects();
    }
    /** get all objects in the current server; alias */
    public static IObject[] objs(){ return objects(); }
    
    /** get a point in the current server */
    public static IPoint point(int i){
	IG ig = cur(); return ig==null?null:ig.getPoint(i);
    }
    /** get a point in the current server; alias */
    public static IPoint pt(int i){ return point(i); }
    
    /** get a curve in the current server */
    public static ICurve curve(int i){
	IG ig = cur(); return ig==null?null:ig.getCurve(i);
    }
    /** get a curve in the current server; alias */
    public static ICurve crv(int i){ return curve(i); }

    /** get a polycurve in the current server 
	Note that IPolycurve contains multiple ICurve and they show up in curves() as well.
	ICurve - IPolycurve relationship is still under work. This is temporary measure.
    */
    public static IPolycurve polycurve(int i){
	IG ig = cur(); return ig==null?null:ig.getPolycurve(i);
    }
    /** get a curve in the current server; alias.
	Note that IPolycurve contains multiple ICurve and they show up in curves() as well.
	ICurve - IPolycurve relationship is still under work. This is temporary measure.
    */
    //public static IPolycurve pcrv(int i){ return polycurve(i); }
    
    /** get a surface in the current server */
    public static ISurface surface(int i){
	IG ig = cur(); return ig==null?null:ig.getSurface(i);
    }
    /** get a surface in the current server; alias */
    public static ISurface srf(int i){ return surface(i); }
    
    /** get a mesh in the current server */
    public static IMesh mesh(int i){
	IG ig = cur(); return ig==null?null:ig.getMesh(i);
    }
    
    /** get a brep in the current server */
    public static IBrep brep(int i){
	IG ig = cur(); return ig==null?null:ig.getBrep(i);
    }

    /** get a text in the current server */
    public static IText text(int i){
	IG ig = cur(); return ig==null?null:ig.getText(i);
    }
    
    /** get a geometry in the current server */
    public static IGeometry geometry(int i){
	IG ig = cur(); return ig==null?null:ig.getGeometry(i);
    }
    /** get a geometry in the current server */
    public static IGeometry geo(int i){ return geometry(i); }
    
    /** get a object of the specified class in the current server */
    public static IObject object(Class cls, int i){
	IG ig = cur(); return ig==null?null:ig.getObject(cls,i);
    }
    /** get a object of the specified class in the current server; alias */
    public static IObject obj(Class cls, int i){ return object(cls,i); }
    
    /** get a object in the current server */
    public static IObject object(int i){
	IG ig = cur(); return ig==null?null:ig.getObject(i);
    }
    /** get a object in the current server; alias */
    public static IObject obj(int i){ return object(i); }
    
    /** number of points in the current server */
    public static int pointNum(){
	IG ig = cur(); return ig==null?0:ig.getPointNum();
    }
    /** number of points in the current server; alias */
    public static int ptNum(){ return pointNum(); }
    
    /** number of curves in the current server */
    public static int curveNum(){
	IG ig = cur(); return ig==null?0:ig.getCurveNum();
    }
    /** number of curves in the current server; alias */
    public static int crvNum(){ return curveNum(); }
    
    /** number of polycurves in the current server.
	Note that IPolycurve contains multiple ICurve and they show up in curves() as well.
	ICurve - IPolycurve relationship is still under work. This is temporary measure.
    */
    public static int polycurveNum(){
	IG ig = cur(); return ig==null?0:ig.getPolycurveNum();
    }
    /** number of polycurves in the current server; alias.
	Note that IPolycurve contains multiple ICurve and they show up in curves() as well.
	ICurve - IPolycurve relationship is still under work. This is temporary measure.
    */
    //fpublic static int pcrvNum(){ return polycurveNum(); }
    
    /** number of surfaces in the current server */
    public static int surfaceNum(){
	IG ig = cur(); return ig==null?0:ig.getSurfaceNum();
    }
    /** number of surfaces in the current server; alias */
    public static int srfNum(){ return surfaceNum(); }
    
    /** number of meshes in the current server */
    public static int meshNum(){
	IG ig = cur(); return ig==null?0:ig.getMeshNum();
    }
    /** number of breps in the current server */
    public static int brepNum(){
	IG ig = cur(); return ig==null?0:ig.getBrepNum();
    }
    /** number of texts in the current server */
    public static int textNum(){
	IG ig = cur(); return ig==null?0:ig.getTextNum();
    }
    /** number of geometries in the cubrrent server */
    public static int geometryNum(){
	IG ig = cur(); return ig==null?0:ig.getGeometryNum();
    }
    /** alias of geometryNum() */
    public static int geoNum(){ return geometryNum(); }
    
    /** number of objects of the specified class in the current server */
    public static int objectNum(Class cls){
	IG ig = cur(); return ig==null?0:ig.getObjectNum(cls);
    }
    /** number of objects of the specified class in the current server; alias */
    public static int objNum(Class cls){ return objectNum(cls); }
    
    /** number of objects in the current server */
    public static int objectNum(){
	IG ig = cur(); return ig==null?0:ig.getObjectNum();
    }
    /** number of objects in the current server; alias */
    public static int objNum(){ return objectNum(); }
    
    
    public static void del(IObject o){ o.del(); }
    public static void del(IObject[] o){
	for(int i=0; i<o.length; i++){ if(o[i]!=null) o[i].del(); }
    }
    public static void del(ArrayList<IObject> o){
	for(int i=0; i<o.size(); i++){ if(o.get(i)!=null) o.get(i).del(); }
    }
    /* delete all objects of specified class */
    public static void del(Class cls){
	IObject[] o = objects(cls);
	del(o);
    }
    /* alias of clear(): clear all not just objects but all dynamics, graphics and layers */
    public static void delAll(){ clear(); }
    
    /* clear all not just objects but all dynamics, graphics and layers */
    public static void clear(){
	IG ig = cur();
	if(ig!=null){ ig.clearServer(); }
    }
    
    
    public static ILayer layer(String layerName){
	IG ig = cur();
	if(ig==null) return null;
	return ig.getLayer(layerName);
    }
    public static ILayer layer(int i){
	IG ig = cur();
	if(ig==null) return null;
	return ig.getLayer(i);
    }
    public static ILayer[] layers(){
	IG ig = cur();
	if(ig==null) return null;
	return ig.getAllLayers();
    }
    public static void delLayer(String layerName){
	IG ig = cur();
	if(ig==null) return;
	ig.deleteLayer(layerName);
    }
    public static void delLayer(ILayer layer){
	IG ig = cur();
	if(ig==null) return;
	ig.deleteLayer(layer);
    }
    public static int layerNum(){
	IG ig = cur();
	if(ig==null) return 0;
	return ig.getLayerNum();
    }
    
    public static void focus(){
	IG ig = cur();
	if(ig==null) return;
	ig.focusView();
    }
    
    
    public static boolean isGL(){
	IG ig = cur();
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
	IG ig = cur(); if(ig==null) return;
	ig.server().setGraphicMode(mode);
    }
    
    /** set wireframe graphic mode */
    public static void wireframe(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.J2D;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,false,true,false));
    }
    /** alias of wireframe() */
    public static void wire(){ wireframe(); }
    
    /** set fill graphic mode */
    public static void fill(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.J2D;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,true,false,false));
    }
    
    /** set fill+wireframe graphic mode */
    public static void fillWithWireframe(){ wireframeFill(); }
    /** set fill+wireframe graphic mode */
    public static void fillWireframe(){ wireframeFill(); }
    /** set fill+wireframe graphic mode */
    public static void fillWire(){ wireframeFill(); }
    /** set fill+wireframe graphic mode */
    public static void wireframeFill(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.J2D;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,true,true,false));
    }
    /** set fill+wireframe graphic mode */
    public static void wireFill(){ wireframeFill(); }
    
    /** set transparent fill graphic mode */
    public static void transparentFill(){ transparent(); }
    /** set transparent fill graphic mode */
    public static void transFill(){ transparent(); }
    /** set transparent fill graphic mode */
    public static void transparent(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.J2D;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,true,false,true));
    }
    /** alias of transparent() */
    public static void trans(){ transparent(); }
    
    /** set transparent fill+wireframe graphic mode */
    public static void transparentFillWithWireframe(){ wireframeTransparent(); }
    /** set transparent fill+wireframe graphic mode */
    public static void transparentWireframe(){ wireframeTransparent(); }
    /** set transparent fill+wireframe graphic mode */
    public static void wireframeTransparent(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.J2D;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,true,true,true));
    }
    /** alias of wireframeTransparent() */
    public static void wireTrans(){ wireframeTransparent(); }
    /** alias of wireframeTransparent() */
    public static void transWire(){ wireframeTransparent(); }
    
    
    public static void noGraphic(){
	IGraphicMode.GraphicType gtype = IGraphicMode.GraphicType.J2D;
	if(isGL()) gtype = IGraphicMode.GraphicType.GL;
	graphicMode(new IGraphicMode(gtype,false,false,false));
    }
    
    
    
    public static IView view(int paneIndex){
	IG ig = cur(); if(ig==null) return null;
	if(ig==null || ig.panel==null || ig.panel.paneNum()>0 ||
	   ig.panel.paneNum() <= paneIndex || paneIndex<0 ){ return null; }
	if(ig.panel instanceof IScreenTogglePanel){
	    ((IScreenTogglePanel)(ig.panel)).enableFullScreen(ig.panel.pane(paneIndex));
	}
	return ig.panel.pane(paneIndex).getView();
    }
    
    
    /** put the specified pane on the full screen inside the window if the panel is IGridPanel with 2x2 grid */
    public static IPane gridPane(int xindex, int yindex){
	IG ig = cur(); if(ig==null) return null;
	if(ig.panel!=null && ig.panel instanceof IGridPanel){
	    IGridPanel gpanel = (IGridPanel)ig.panel;
	    if(xindex>=0 && xindex < gpanel.gridPanes.length &&
	       yindex>=0 && yindex < gpanel.gridPanes[xindex].length ){
		IPane pane = gpanel.gridPanes[xindex][yindex];
		gpanel.enableFullScreen(pane);
		return pane;
	    }
	}
	return null;
    }
    
    /** put top pane on the full screen inside the window if the panel is IGridPanel */
    public static IPane topPane(){ return gridPane(0,0); }
    
    /** bottom pane is identical with top pane in IGridPanel */
    public static IPane bottomPane(){ return topPane(); }
    
    /** put perspective pane on the full screen inside the window if the panel is IGridPanel */
    public static IPane perspectivePane(){ return gridPane(1,0); }
    
    /** axonometric pane is identical with perspective pane in IGridPanel */
    public static IPane axonometricPane(){ return perspectivePane(); }
    
    /** put front pane on the full screen inside the window if the panel is IGridPanel */
    public static IPane frontPane(){ return gridPane(0,1); }
    
    /** back pane is identical with front pane in IGridPanel */
    public static IPane backPane(){ return frontPane(); }
    
    /** put right pane on the full screen inside the window if the panel is IGridPanel */
    public static IPane rightPane(){ return gridPane(1,1); }
    
    /** left pane is identical with front pane in IGridPanel */
    public static IPane leftPane(){ return rightPane(); }
    
    
    
    /** put top view on the full screen inside the window */
    public static void top(){
	IPane pane = topPane();
	if(pane!=null){
	    pane.getView().setTop();
	    pane.focus(); // added 20120615
	}
    }
    public static void top(double x, double y){
	IPane pane = topPane();
	if(pane!=null){ pane.getView().setTop(x,y); }
    }
    public static void top(double x, double y, double z){
	IPane pane = topPane();
	if(pane!=null){ pane.getView().setTop(x,y,z); }
    }
    public static void top(double x, double y, double z, double axonRatio){
	IPane pane = topPane();
	if(pane!=null){ pane.getView().setTop(x,y,z,axonRatio); }
    }
    public static void topView(){ top(); }
    public static void topView(double x, double y){ top(x,y); }
    public static void topView(double x, double y, double z){ top(x,y,z); }
    public static void topView(double x, double y, double z, double axonRatio){
	top(x,y,z,axonRatio);
    }
    
    /** put bottom view on the full screen inside the window */
    public static void bottom(){
	IPane pane = bottomPane();
	if(pane!=null){
	    pane.getView().setBottom();
	    pane.focus(); // added 20120615
	}
    }
    public static void bottom(double x, double y){
	IPane pane = bottomPane();
	if(pane!=null){ pane.getView().setBottom(x,y); }
    }
    public static void bottom(double x, double y, double z){
	IPane pane = bottomPane();
	if(pane!=null){ pane.getView().setBottom(x,y,z); }
    }
    public static void bottom(double x, double y, double z, double axonRatio){
	IPane pane = bottomPane();
	if(pane!=null){ pane.getView().setBottom(x,y,z,axonRatio); }
    }
    public static void bottomView(){ bottom(); }
    public static void bottomView(double x, double y){ bottom(x,y); }
    public static void bottomView(double x, double y, double z){ bottom(x,y,z); }
    public static void bottomView(double x, double y, double z, double axonRatio){ bottom(x,y,z,axonRatio); }
    
    
    /** put perspective view on the full screen inside the window */
    public static void perspective(){
	IPane pane = perspectivePane();
	if(pane!=null){
	    pane.getView().setPerspective();
	    pane.focus(); // added 20120615
	}
    }
    public static void perspective(double x, double y, double z){
	IPane pane = perspectivePane();
	if(pane!=null){ pane.getView().setPerspective(x,y,z); }
    }
    public static void perspective(double x, double y, double z,
				   double yaw, double pitch){
	IPane pane = perspectivePane();
	if(pane!=null){ pane.getView().setPerspective(x,y,z,yaw,pitch); }
    }
    public static void perspectiveView(){ perspective(); }
    public static void perspectiveView(double x, double y, double z){ perspective(x,y,z); }
    public static void perspectiveView(double x, double y, double z,
				       double yaw, double pitch){ perspective(x,y,z,yaw,pitch); }
    public static void pers(){ perspective(); }
    public static void pers(double x, double y, double z){ perspective(x,y,z); }
    public static void pers(double x, double y, double z, double yaw, double pitch){ perspective(x,y,z,yaw,pitch); }
    
    
    /** put perspective view on the full screen inside the window */
    public static void perspective(double perspectiveAngle){
	IPane pane = perspectivePane();
	if(pane!=null){
	    pane.getView().setPerspective(perspectiveAngle);
	}
    }
    public static void perspective(double x, double y, double z,
				   double perspectiveAngle){
	IPane pane = perspectivePane();
	if(pane!=null){
	    pane.getView().setPerspective(x,y,z,perspectiveAngle);
	}
    }
    public static void perspective(double x, double y, double z,
				   double yaw, double pitch,
				   double perspectiveAngle){
	IPane pane = perspectivePane();
	if(pane!=null){
	    pane.getView().setPerspective(x,y,z,yaw,pitch,perspectiveAngle);
	}
    }
    public static void perspectiveView(double perspectiveAngle){ perspective(perspectiveAngle); }
    public static void perspectiveView(double x, double y, double z,
				       double perspectiveAngle){
	perspective(x,y,z,perspectiveAngle);
    }
    public static void perspectiveView(double x, double y, double z,
				       double yaw, double pitch,
				       double perspectiveAngle){
	perspective(x,y,z,yaw,pitch,perspectiveAngle);
    }
    public static void pers(double perspectiveAngle){ perspective(perspectiveAngle); }
    public static void pers(double x, double y, double z, double perspectiveAngle){
	perspective(x,y,z,perspectiveAngle);
    }
    public static void pers(double x, double y, double z, double yaw, double pitch, double perspectiveAngle){
	perspective(x,y,z,yaw,pitch,perspectiveAngle);
    }
    
    /** put axonometric view on the full screen inside the window */
    public static void axonometric(){
	IPane pane = axonometricPane();
	if(pane!=null){
	    pane.getView().setAxonometric();
	    pane.focus(); // added 20120615
	}
    }
    public static void axonometric(double x, double y, double z){
	IPane pane = axonometricPane();
	if(pane!=null){ pane.getView().setAxonometric(x,y,z); }
    }
    public static void axonometric(double x, double y, double z, double axonRatio){
	IPane pane = axonometricPane();
	if(pane!=null){ pane.getView().setAxonometric(x,y,z,axonRatio); }
    }
    public static void axonometric(double x, double y, double z, double yaw, double pitch){
	IPane pane = axonometricPane();
	if(pane!=null){ pane.getView().setAxonometric(x,y,z,yaw,pitch); }
    }
    public static void axonometric(double x, double y, double z, double yaw, double pitch, double axonRatio){
	IPane pane = axonometricPane();
	if(pane!=null){ pane.getView().setAxonometric(x,y,z,yaw,pitch,axonRatio); }
    }
    public static void axonometricView(){ axonometric(); }
    public static void axonometricView(double x, double y, double z){
	axonometric(x,y,z);
    }
    public static void axonometricView(double x, double y, double z, double axonRatio){
	axonometric(x,y,z,axonRatio);
    }
    public static void axonometricView(double x, double y, double z,
				       double yaw, double pitch){
	axonometric(x,y,z,yaw,pitch);
    }
    public static void axonometricView(double x, double y, double z,
				       double yaw, double pitch, double axonRatio){
	axonometric(x,y,z,yaw,pitch,axonRatio);
    }
    public static void axon(){ axonometric(); }
    public static void axon(double x, double y, double z){
	axonometric(x,y,z);
    }
    public static void axon(double x, double y, double z, double axonRatio){
	axonometric(x,y,z,axonRatio);
    }
    public static void axon(double x, double y, double z, double yaw, double pitch){
	axonometric(x,y,z,yaw,pitch);
    }
    public static void axon(double x, double y, double z, double yaw, double pitch, double axonRatio){
	axonometric(x,y,z,yaw,pitch,axonRatio);
    }
    
    
    /** put front view on the full screen inside the window */
    public static void front(){
	IPane pane = frontPane();
	if(pane!=null){
	    pane.getView().setFront();
	    pane.focus(); // added 20120615
	}
    }
    public static void front(double x, double z){
	IPane pane = frontPane();
	if(pane!=null){ pane.getView().setFront(x,z); }
    }
    public static void front(double x, double y, double z){
	IPane pane = frontPane();
	if(pane!=null){ pane.getView().setFront(x,y,z); }
    }
    public static void front(double x, double y, double z, double axonRatio){
	IPane pane = frontPane();
	if(pane!=null){ pane.getView().setFront(x,y,z,axonRatio); }
    }
    public static void frontView(){ front(); }
    public static void frontView(double x, double z){ front(x,z); }
    public static void frontView(double x, double y, double z){ front(x,y,z); }
    public static void frontView(double x, double y, double z, double axonRatio){ front(x,y,z,axonRatio); }
    
    /** put back view on the full screen inside the window */
    public static void back(){
	IPane pane = backPane();
	if(pane!=null){
	    pane.getView().setBack();
	    pane.focus(); // added 20120615
	}
    }
    public static void back(double x, double z){
	IPane pane = backPane();
	if(pane!=null){ pane.getView().setBack(x,z); }
    }
    public static void back(double x, double y, double z){
	IPane pane = backPane();
	if(pane!=null){ pane.getView().setBack(x,y,z); }
    }
    public static void back(double x, double y, double z, double axonRatio){
	IPane pane = backPane();
	if(pane!=null){ pane.getView().setBack(x,y,z,axonRatio); }
    }
    public static void backView(){ back(); }
    public static void backView(double x, double z){ back(x,z); }
    public static void backView(double x, double y, double z){ back(x,y,z); }
    public static void backView(double x, double y, double z, double axonRatio){ back(x,y,z,axonRatio); }
    
    /** put right view on the full screen inside the window */
    public static void right(){
	IPane pane = rightPane();
	if(pane!=null){
	    pane.getView().setRight();
	    pane.focus(); // added 20120615
	}
    }
    public static void right(double y, double z){
	IPane pane = rightPane();
	if(pane!=null){ pane.getView().setRight(y, z); }
    }
    public static void right(double x, double y, double z){
	IPane pane = rightPane();
	if(pane!=null){ pane.getView().setRight(x, y, z); }
    }
    public static void right(double x, double y, double z, double axonRatio){
	IPane pane = rightPane();
	if(pane!=null){ pane.getView().setRight(x, y, z, axonRatio); }
    }
    public static void rightView(){ right(); }
    public static void rightView(double y, double z){ right(y,z); }
    public static void rightView(double x, double y, double z){ right(x,y,z); }
    public static void rightView(double x, double y, double z, double axonRatio){ right(x,y,z,axonRatio); }
    
    /** put left view on the full screen inside the window */
    public static void left(){
	IPane pane = leftPane();
	if(pane!=null){
	    pane.getView().setLeft();
	    pane.focus(); // added 20120615
	}
    }
    public static void left(double y, double z){
	IPane pane = leftPane();
	if(pane!=null){ pane.getView().setLeft(y,z); }
    }
    public static void left(double x, double y, double z){
	IPane pane = leftPane();
	if(pane!=null){ pane.getView().setLeft(x,y,z); }
    }
    public static void left(double x, double y, double z, double axonRatio){
	IPane pane = leftPane();
	if(pane!=null){ pane.getView().setLeft(x,y,z,axonRatio); }
    }
    public static void leftView(){ left(); }
    public static void leftView(double y, double z){ left(y,z); }
    public static void leftView(double x, double y, double z){ left(x,y,z); }
    public static void leftView(double x, double y, double z, double axonRatio){ left(x,y,z,axonRatio); }
    
    
    /****************************
     * background color
     ***************************/
    
    //public static void setBG(Color c){}
    //public static void setBG(Color c1, Color c2){}
    //public static void setBG(Color c1, Color c2, Color c3, Color c4){}
    //public static void setBG(Image img){}
    
    public static void bg(IColor c1, IColor c2, IColor c3, IColor c4){
	IG ig = cur(); if(ig==null) return;
	ig.server().bg(c1,c2,c3,c4);
    }
    public static void background(IColor c1, IColor c2, IColor c3, IColor c4){ bg(c1,c2,c3,c4); }
    
    public static void bg(Color c1, Color c2, Color c3, Color c4){
	bg(new IColor(c1),new IColor(c2),new IColor(c3),new IColor(c4));
    }
    
    public static void background(Color c1, Color c2, Color c3, Color c4){ bg(c1,c2,c3,c4); }
    
    public static void bg(Color c){ bg(c,c,c,c); }
    public static void background(Color c){ bg(c); }
    

    /*
    public static void bg(Image img){
	IG ig = cur(); if(ig==null) return;
	ig.server().bg(img);
    }
    public static void background(Image img){ bg(img); }
    public static void bg(String filename){
	IG ig = cur(); if(ig==null) return;
	ig.server().bg(IImageLoader.getImage(filename));
    }
    public static void background(String filename){ bg(filename); }
    */
    public static void bg(String imageFilename){
	IG ig = cur(); if(ig==null) return;
	ig.server().bg(imageFilename);
    }
    public static void background(String imageFilename){ bg(imageFilename); }
    
    public static void defaultBG(){ blueBG(); }
    public static void blueBG(){ bg(IConfig.bgColor1,IConfig.bgColor2,IConfig.bgColor3,IConfig.bgColor4); } // added 2012/09/02
    public static void lightBG(){ bg(1.0, 1.0, 0.9, 0.8); } // added 2012/09/02
    public static void darkBG(){ bg(0.15, 0.15, 0.05, 0.0); } // added 2012/09/02
    public static void whiteBG(){ bg(1.0); } // added 2012/09/02
    public static void blackBG(){ bg(0.0); } // added 2012/09/02
    
    
    public static void bg(int r1, int g1, int b1,
			  int r2, int g2, int b2,
			  int r3, int g3, int b3,
			  int r4, int g4, int b4){
	bg(IGraphicObject.getColor(r1,g1,b1), IGraphicObject.getColor(r2,g2,b2),
	   IGraphicObject.getColor(r3,g3,b3), IGraphicObject.getColor(r4,g4,b4));
    }
    public static void background(int r1, int g1, int b1,
				  int r2, int g2, int b2,
				  int r3, int g3, int b3,
				  int r4, int g4, int b4){
	bg(r1,g1,b1,r2,g2,b2,r3,g3,b3,r4,b4,g4);
    }
    
    public static void bg(int r, int g, int b){
	bg(IGraphicObject.getColor(r,g,b));
    }
    public static void background(int r, int g, int b){ bg(r,g,b); }
    
    public static void bg(int gray1, int gray2, int gray3, int gray4){
	bg(IGraphicObject.getColor(gray1), IGraphicObject.getColor(gray2),
	   IGraphicObject.getColor(gray3), IGraphicObject.getColor(gray4));
    }
    public static void background(int gray1, int gray2, int gray3, int gray4){
	bg(gray1,gray2,gray3,gray4);
    }
    
    public static void bg(int gray){ bg(IGraphicObject.getColor(gray)); }
    
    public static void background(int gray){ bg(gray); }
    
    
    
    public static void bg(float r1, float g1, float b1,
			  float r2, float g2, float b2,
			  float r3, float g3, float b3,
			  float r4, float g4, float b4){
	bg(IGraphicObject.getColor(r1,g1,b1), IGraphicObject.getColor(r2,g2,b2),
	   IGraphicObject.getColor(r3,g3,b3), IGraphicObject.getColor(r4,g4,b4));
    }
    public static void background(float r1, float g1, float b1,
				  float r2, float g2, float b2,
				  float r3, float g3, float b3,
				  float r4, float g4, float b4){
	bg(r1,g1,b1,r2,g2,b2,r3,g3,b3,r4,b4,g4);
    }
    
    public static void bg(float r, float g, float b){
	bg(IGraphicObject.getColor(r,g,b));
    }
    
    public static void background(float r, float g, float b){ bg(r,g,b); }
    
    public static void bg(float gray1, float gray2, float gray3, float gray4){
	bg(IGraphicObject.getColor(gray1), IGraphicObject.getColor(gray2),
	   IGraphicObject.getColor(gray3), IGraphicObject.getColor(gray4));
    }
    public static void background(float gray1, float gray2, float gray3, float gray4){
	bg(gray1,gray2,gray3,gray4);
    }
    
    public static void bg(float gray){ bg(IGraphicObject.getColor(gray)); }
    
    public static void background(float gray){ bg(gray); }
    
    
    
    public static void bg(double r1, double g1, double b1,
			  double r2, double g2, double b2,
			  double r3, double g3, double b3,
			  double r4, double g4, double b4){
	bg(IGraphicObject.getColor((float)r1,(float)g1,(float)b1),
	   IGraphicObject.getColor((float)r2,(float)g2,(float)b2),
	   IGraphicObject.getColor((float)r3,(float)g3,(float)b3),
	   IGraphicObject.getColor((float)r4,(float)g4,(float)b4));
    }
    public static void background(double r1, double g1, double b1,
				  double r2, double g2, double b2,
				  double r3, double g3, double b3,
				  double r4, double g4, double b4){
	bg(r1,g1,b1,r2,g2,b2,r3,g3,b3,r4,b4,g4);
    }
    
    public static void bg(double r, double g, double b){
	bg(IGraphicObject.getColor((float)r,(float)g,(float)b));
    }
    
    public static void background(double r, double g, double b){ bg(r,g,b); }
    
    public static void bg(double gray1, double gray2, double gray3, double gray4){
	bg(IGraphicObject.getColor((float)gray1), IGraphicObject.getColor((float)gray2),
	   IGraphicObject.getColor((float)gray3), IGraphicObject.getColor((float)gray4));
    }
    public static void background(double gray1, double gray2, double gray3, double gray4){
	bg(gray1,gray2,gray3,gray4);
    }
    
    public static void bg(double gray){ bg(IGraphicObject.getColor((float)gray)); }
    public static void background(double gray){ bg(gray); }
    
    
    
    /** Print method with header and new line.
	This is a wrapper of IOut.p(), which is 
	also a wrapper of System.out.println().
    */
    public static void p(Object obj){ IOut.printlnWithOffset(obj,1); }
    /** Print method only with header and new line. */
    public static void p(){ IOut.printlnWithOffset(1); }
    
    /** Print method without header nor new line
	This is a wrapper of IOut.p(), which is 
	also a wrapper of System.out.print().
    */
    public static void print(Object obj){ IOut.print(obj); }
    
    /** enable print prefix (executing method name)*/
    public static void enabePrintPrefix(){ IOut.enablePrefix(); }
    /** disble print prefix (executing method name)*/
    public static void disablePrintPrefix(){ IOut.disablePrefix(); }
    
    /** Error print method with header and new line.
	This is a wrapper of IOut.err() */
    public static void err(Object obj){ IOut.errWithOffset(obj,1); }
    /** Error print method only with header and new line. */
    public static void err(){ IOut.errWithOffset(1); }
    /** Error print method without header nor new line. */
    public static void error(Object obj){ IOut.error(obj); }
    
    /** enable error print prefix (executing method name)*/
    public static void enabeErrorPrefix(){ IOut.enablePrefix(); }
    /** disable error print prefix (executing method name)*/
    public static void disableErrorPrefix(){ IOut.disablePrefix(); }
    
    /** change the debug level of IOut */
    public static void debugLevel(int level){ IOut.debugLevel(level); }
    /** alias of debugLevel */
    public static void debug(int level){ debugLevel(level); }
    /** turns on all debug output */
    public static void debug(){ debugLevel(-1); }
    
    /** returns the current debugLevel */
    public static int debugLevel(){ return IOut.debugLevel(); }
    
    
    
    /*************************************************************************
     * object methods
     *************************************************************************/
    
    // anybody would want this in public?
    //protected
    public IG(){ server = new IServer(this); }
    
    //protected
    public IG(IPanelI p){
	server = new IServer(this, p);
	panel = p; // 
	p.setIG(this);
    }
    
    public boolean openFile(String file){
	boolean retval = false;
	if(inputWrapper!=null){ retval = IIO.open(file,this,inputWrapper); }
	else{
	    File f = new File(file);
	    if(!f.isAbsolute() && basePath!=null){ file = basePath + File.separator + file; }
	    retval = IIO.open(file,this);
	}
	server.updateState(); // update server status
	inputFile = file;
	//focusView(); // instead of here, focused at the end of setup if IConfig.autoFocusAtStart is true
	return retval;
    }
    
    
    public String formatOutputFilePath(String file){
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
	return file;
    }
    
    public boolean saveFile(String file){
	file = formatOutputFilePath(file);
	/*
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
	*/
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
    
    public void setOnline(boolean f){ online=f; }
    public boolean isOnline(){ return online; }
    
    public void setInputWrapper(IInputWrapper wrapper){ inputWrapper = wrapper; }
    
    public ILayer getLayer(String layerName){ return server.getLayer(layerName); }
    public ILayer getLayer(int i){ return server.getLayer(i); }
    public ILayer[] getAllLayers(){ return server.getAllLayers(); }
    public void deleteLayer(String layerName){ server.deleteLayer(layerName); }
    public void deleteLayer(ILayer layer){ server.deleteLayer(layer); }
    public int getLayerNum(){ return server.layerNum(); }
    
    public IPoint[] getPoints(){ return server.points(); }
    public ICurve[] getCurves(){ return server.curves(); }
    public IPolycurve[] getPolycurves(){ return server.polycurves(); }
    public ISurface[] getSurfaces(){ return server.surfaces(); }
    public IMesh[] getMeshes(){ return server.meshes(); }
    public IBrep[] getBreps(){ return server.breps(); }
    public IText[] getTexts(){ return server.texts(); }
    public IGeometry[] getGeometries(){ return server.geometries(); }
    public IObject[] getObjects(Class cls){ return server.objects(cls); }
    public IObject[] getObjects(){ return server.objects(); }
    
    public IPoint getPoint(int i){ return server.point(i); }
    public ICurve getCurve(int i){ return server.curve(i); }
    public IPolycurve getPolycurve(int i){ return server.polycurve(i); }
    public ISurface getSurface(int i){ return server.surface(i); }
    public IMesh getMesh(int i){ return server.mesh(i); }
    public IBrep getBrep(int i){ return server.brep(i); }
    public IText getText(int i){ return server.text(i); }
    public IGeometry getGeometry(int i){ return server.geometry(i); }
    public IObject getObject(Class cls,int i){ return server.object(cls,i); }
    public IObject getObject(int i){ return server.object(i); }
    
    public int getPointNum(){ return server.pointNum(); }
    public int getCurveNum(){ return server.curveNum(); }
    public int getPolycurveNum(){ return server.polycurveNum(); }
    public int getSurfaceNum(){ return server.surfaceNum(); }
    public int getMeshNum(){ return server.meshNum(); }
    public int getBrepNum(){ return server.brepNum(); }
    public int getTextNum(){ return server.textNum(); }
    public int getGeometryNum(){ return server.geometryNum(); }
    public int getObjectNum(Class cls){ return server.objectNum(cls); }
    public int getObjectNum(){ return server.objectNum(); }
    
    
    public void focusView(){
	if(panel!=null) panel.focus(); // focus on all pane
    }
    
    public IServer server(){ return server; }
    public IDynamicServer dynamicServer(){ return server.dynamicServer(); }
    
    // dynamics
    public void setDuration(int dur){ server.duration(dur); }
    public int getDuration(){ return server.duration(); }
    
    public void setTime(int tm){ server.time(tm); }
    public int getTime(){ return server.time(); }
    
    public void pauseDynamics(){ server.pause(); }
    public void resumeDynamics(){ server.resume(); }
    /** check if dynamics is running */
    public boolean isDynamicsRunning(){ return server.isRunning(); }
    
    
    public void startDynamics(){ server.start(); }
    public void stopDynamics(){ server.stop(); }
    
    
    //public void draw(IGraphics g){ server.draw(g); }
    
    //public IGPane pane(){ return pane; }
    //public IPanelI panel(){ return panel; }
    
    //public void delete(){
    /* name is changed from clear() to clearServer(). delete objects, graphic objects, dynamics and layers */
    public void clearServer(){ server.clear(); }
    
    
    
    
    /*********************************************************************
     * Static Geometry Operations
     ********************************************************************/
    
    /** point creation */
    public static IPoint point(IVecI v){ return pt(v); }
    public static IPoint point(IVec v){ return pt(v); }
    public static IPoint point(double x, double y, double z){ return pt(x,y,z); }
    public static IPoint point(double x, double y){ return pt(x,y); }
        
    /** point creation shorter name */
    public static IPoint pt(IVecI v){ return new IPoint(v); }
    public static IPoint pt(IVec v){ return new IPoint(v); }
    public static IPoint pt(double x, double y, double z){ return new IPoint(x,y,z); }
    public static IPoint pt(double x, double y){ return new IPoint(x,y); }
    
    
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
    /** this creates a line between a same point */
    public static ICurve curve(IVecI pt){ return ICurveCreator.curve(pt); }
    
    
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
    /** this creates a line between a same point */
    public static ICurve crv(IVecI pt){ return curve(pt); }
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
    
    

    /***********
     * line : type of curve.
     **********/
    
    public static ICurve line(IVecI pt1, IVecI pt2){ return curve(pt1,pt2); }
    /** this creates a line between a same point */
    public static ICurve line(IVecI pt){ return curve(pt); }
    public static ICurve line(double x1, double y1, double z1, double x2, double y2, double z2){
	return curve(x1,y1,z1,x2,y2,z2);
    }
    
    
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
    
    
    /**
       offset points. normal direction is automatically detected.
     */
    public static IVec[] offset(IVec[] pts, double width){
	return IVec.offset(pts,width);
    }
    public static IVec[] offset(IVec[] pts, double width, boolean close){
	return IVec.offset(pts,width,close);
    }
    public static IVecI[] offset(IVecI[] pts, double width){
	return IVec.offset(pts,width);
    }       
    public static IVecI[] offset(IVecI[] pts, double width, boolean close){
	return IVec.offset(pts,width,close);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width){
	return IVec.offset(pts,width);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, boolean close){
	return IVec.offset(pts,width,close);
    }

    /**
       offset points with specific normal direction
    */
    public static IVec[] offset(IVec[] pts, double width, IVecI planeNormal){
	return IVec.offset(pts,width,planeNormal);
    }
    public static IVec[] offset(IVec[] pts, double width, IVecI planeNormal, boolean close){
	return IVec.offset(pts,width,planeNormal,close);
    }
    public static IVecI[] offset(IVecI[] pts, double width, IVecI planeNormal){
	return IVec.offset(pts,width,planeNormal);
    }
    public static IVecI[] offset(IVecI[] pts, double width, IVecI planeNormal, boolean close){
        return IVec.offset(pts,width,planeNormal,close);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, IVecI planeNormal){
	return IVec.offset(pts,width,planeNormal);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, IVecI planeNormal, boolean close){
	return IVec.offset(pts,width,planeNormal,close);
    }
    
    /**
       offset points with specific normal vector for each point
    */
    public static IVec[] offset(IVec[] pts, double width, IVecI[] normals){
	return IVec.offset(pts,width,normals);
    }
    public static IVec[] offset(IVec[] pts, double width, IVecI[] normals, boolean close){
	return IVec.offset(pts,width,normals,close);
    }
    public static IVecI[] offset(IVecI[] pts, double width, IVecI[] normals){
	return IVec.offset(pts,width,normals);
    }
    public static IVecI[] offset(IVecI[] pts, double width, IVecI[] normals, boolean close){
	return IVec.offset(pts,width,normals,close);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, IVecI[] normals){
        return IVec.offset(pts, width, normals);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, IVecI[] normals, boolean close){
        return IVec.offset(pts, width, normals, close);
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
    public static ISurface surface(ICurveI outerTrimCurve, ICurveI[] innerTrimCurves){
	return ISurfaceCreator.surface(outerTrimCurve, innerTrimCurves);
    }
    public static ISurface surface(ICurveI outerTrimCurve, ICurveI innerTrimCurve){
	return ISurfaceCreator.surface(outerTrimCurve, innerTrimCurve);
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
    
    

    
    /*****************************************************************
     * srf : short name of surfaces
     *****************************************************************/
    
    public static ISurface srf(IVecI[][] cpts, int udegree, int vdegree,
			       double[] uknots, double[] vknots,
			       double ustart, double uend, double vstart, double vend){
	return surface(cpts,udegree,vdegree,uknots,vknots,ustart,uend,vstart,vend);
    }
    
    public static ISurface srf(IVecI[][] cpts, int udegree, int vdegree,
			       double[] uknots, double[] vknots){
	return surface(cpts,udegree,vdegree,uknots,vknots);
    }
    
    public static ISurface srf(IVecI[][] cpts, int udegree, int vdegree){
        return surface(cpts,udegree,vdegree);
    }
    
    public static ISurface srf(IVecI[][] cpts){ return surface(cpts); }
    
    public static ISurface srf(IVecI[][] cpts, int udegree, int vdegree,
			       boolean closeU, boolean closeV){
	return surface(cpts,udegree,vdegree,closeU,closeV);
    }
    
    public static ISurface srf(IVecI[][] cpts, int udegree, int vdegree,
			       boolean closeU, double[] vk){
	return surface(cpts,udegree,vdegree,closeU,vk);
    }
    
    public static ISurface srf(IVecI[][] cpts, int udegree, int vdegree,
			       double[] uk, boolean closeV){
	return surface(cpts,udegree,vdegree,uk,closeV);
    }
    
    public static ISurface srf(IVecI[][] cpts, boolean closeU, boolean closeV){
	return surface(cpts,closeU,closeV);
    }
    
    public static ISurface srf(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4){
	return surface(pt1,pt2,pt3,pt4);
    }
    
    public static ISurface srf(IVecI pt1, IVecI pt2, IVecI pt3){
	return surface(pt1,pt2,pt3);
    }
    
    public static ISurface srf(double x1, double y1, double z1,
			       double x2, double y2, double z2,
			       double x3, double y3, double z3,
			       double x4, double y4, double z4){
	return surface(x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4);
    }
    
    public static ISurface srf(double x1, double y1, double z1,
			       double x2, double y2, double z2,
			       double x3, double y3, double z3){
	return surface(x1,y1,z1,x2,y2,z2,x3,y3,z3);
    }
    
    public static ISurface srf(double[][][] xyzValues){ return surface(xyzValues); }
    
    public static ISurface srf(double[][][] xyzValues, int udeg, int vdeg){
	return surface(xyzValues,udeg,vdeg);
    }
    
    public static ISurface srf(double[][][] xyzValues, boolean closeU, boolean closeV){
	return surface(xyzValues,closeU,closeV);
    }
    
    public static ISurface srf(double[][][] xyzValues, int udeg, int vdeg, boolean closeU, boolean closeV){
	return surface(xyzValues,udeg,vdeg,closeU,closeV);
    }
    
    public static ISurface srf(ISurfaceI srf){ return surface(srf); }
    
    
    /** planar surface with trim */
    public static ISurface srf(ICurveI trimCurve){ return surface(trimCurve); }
    public static ISurface srf(ICurveI outerTrimCurve, ICurveI[] innerTrimCurves){
	return surface(outerTrimCurve, innerTrimCurves);
    }
    public static ISurface srf(ICurveI outerTrimCurve, ICurveI innerTrimCurve){
	return surface(outerTrimCurve, innerTrimCurve);
    }
    public static ISurface srf(ICurveI[] trimCurves){ return surface(trimCurves); }
    public static ISurface srf(IVecI[] trimCrvPts){ return surface(trimCrvPts); }
    public static ISurface srf(IVecI[] trimCrvPts, int trimCrvDeg){
	return surface(trimCrvPts,trimCrvDeg);
    }
    public static ISurface srf(IVecI[] trimCrvPts, int trimCrvDeg, double[] trimCrvKnots){
	return surface(trimCrvPts,trimCrvDeg,trimCrvKnots);
    }
    
    
    

    /*****************************************************************
     * box
     *****************************************************************/
    public static IBox box(double x, double y, double z, double size){
	return ISurfaceCreator.box(x,y,z,size);
    }
    public static IBox box(double x, double y, double z, double width, double height, double depth){
	return ISurfaceCreator.box(x,y,z,width,height,depth);
    }
    public static IBox box(IVecI origin, double size){ return ISurfaceCreator.box(origin,size); }
    public static IBox box(IVecI origin, double width, double height, double depth){
	return ISurfaceCreator.box(origin,width,height,depth);
    }
    public static IBox box(IVecI origin, IVecI xvec, IVecI yvec, IVecI zvec){
	return ISurfaceCreator.box(origin,xvec,yvec,zvec);
    }
    public static IBox box(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4,
			   IVecI pt5, IVecI pt6, IVecI pt7, IVecI pt8 ){
	return ISurfaceCreator.box(pt1,pt2,pt3,pt4,pt5,pt6,pt7,pt8);
    }
    public static IBox box(IVecI[][][] corners){ return ISurfaceCreator.box(corners); }
    
    
    /*****************************************************************
     * sphere
     *****************************************************************/
    
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
    
    public static ISurface plane(double x, double y, double z, double xwidth, double yheight){
	return ISurfaceCreator.plane(new IVec(x,y,z),xwidth,yheight);
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
    
    public static ISurface extrude(ICurveI profile, double extrudeDepth){
	return ISurfaceCreator.extrude(profile,extrudeDepth);
    }
    public static ISurface extrude(ICurveI profile, IDoubleI extrudeDepth){
	return ISurfaceCreator.extrude(profile,extrudeDepth);
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
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, IVecI profileDir, IVecI[] rail){
	return ISurfaceCreator.sweep(profile,profileCenter,profileDir,rail);
    }
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI[] rail, int railDeg){
	return ISurfaceCreator.sweep(profile,profileDeg,rail,railDeg);
    }
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI profileCenter,
				 IVecI[] rail, int railDeg){
	return ISurfaceCreator.sweep(profile,profileDeg,profileCenter,
				     rail,railDeg);
    }
    public static ISurface sweep(IVecI[] profile, int profileDeg,
				 IVecI profileCenter, IVecI profileDir,
				 IVecI[] rail, int railDeg){
	return ISurfaceCreator.sweep(profile,profileDeg,profileCenter,profileDir,
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
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, IVecI profileDir,
				 IVecI[] rail, int railDeg, boolean closeRail){
	return ISurfaceCreator.sweep(profile,profileDeg,closeProfile,
				     profileCenter, profileDir,
				     rail,railDeg,closeRail);
    }

    public static ISurface sweep(IVecI[] profile, ICurveI rail){
	return ISurfaceCreator.sweep(profile,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileCenter,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, IVecI profileDir,
				 ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileCenter,profileDir,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileDeg,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI profileCenter,
				 ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileDeg,profileCenter,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg,
				 IVecI profileCenter, IVecI profileDir, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileDeg,profileCenter,profileDir,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileDeg,closeProfile,rail);
    }    
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileDeg,closeProfile,profileCenter,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, IVecI profileDir, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileDeg,closeProfile,
				     profileCenter,profileDir,rail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI[] rail){
	return ISurfaceCreator.sweep(profile,rail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI[] rail){
	return ISurfaceCreator.sweep(profile,profileCenter,rail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI profileDir, IVecI[] rail){
	return ISurfaceCreator.sweep(profile,profileCenter,profileDir,rail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI[] rail, int railDeg){
	return ISurfaceCreator.sweep(profile,rail,railDeg);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI[] rail,int railDeg){
	return ISurfaceCreator.sweep(profile,profileCenter,rail,railDeg);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI profileDir,
				 IVecI[] rail,int railDeg){
	return ISurfaceCreator.sweep(profile,profileCenter,profileDir,rail,railDeg);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI[] rail, int railDeg, boolean closeRail){
	return ISurfaceCreator.sweep(profile,rail,railDeg,closeRail);
    }
    public static ISurface sweep(ICurveI profile, IVecI profileCenter,
				 IVecI[] rail, int railDeg, boolean closeRail){
	return ISurfaceCreator.sweep(profile,profileCenter,rail,railDeg,closeRail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI profileDir,
				 IVecI[] rail, int railDeg, boolean closeRail){
	return ISurfaceCreator.sweep(profile,profileCenter,profileDir,rail,railDeg,closeRail);
    }
    
    public static ISurface sweep(ICurveI profile, ICurveI rail){
	return ISurfaceCreator.sweep(profile,rail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileCenter,rail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter,
				 IVecI profileDir, ICurveI rail){
	return ISurfaceCreator.sweep(profile,profileCenter,profileDir,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, double[] profileKnots,
				 IVecI[] rail, int railDeg, double[] railKnots){
	return ISurfaceCreator.sweep(profile,profileDeg,profileKnots,
				     rail,railDeg,railKnots);
    }
    
    /**
       sweep.
       @param profileCenter point on profile to be located at the points of rail
    */
    public static ISurface sweep(IVecI[] profile, int profileDeg, double[] profileKnots,
				 IVecI profileCenter,
				 IVecI[] rail, int railDeg, double[] railKnots){
	return ISurfaceCreator.sweep(profile,profileDeg,profileKnots,
				     profileCenter,
				     rail,railDeg,railKnots);
    }
    
    /**
       sweep.
       @param profileCenter point on profile to be located at the points of rail
       @param profileDir direction on profile to be aligned with the normal of rail
    */
    public static ISurface sweep(IVecI[] profile, int profileDeg, double[] profileKnots,
				 IVecI profileCenter, IVecI profileDir,
				 IVecI[] rail, int railDeg, double[] railKnots){
	return ISurfaceCreator.sweep(profile,profileDeg,profileKnots,
				     profileCenter, profileDir,
				     rail,railDeg,railKnots);
    }
    
    /*********************
     * pipe
     *********************/
    
    public static ISurface pipe(IVecI pt1, IVecI pt2, double radius){
	return ISurfaceCreator.pipe(pt1,pt2,radius);
    }
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
    
    
    public static ISurface squarePipe(IVecI pt1, IVecI pt2, double size){
	return ISurfaceCreator.squarePipe(pt1,pt2,size);
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
    public static ISurface rectPipe(IVecI pt1, IVecI pt2, double width, double height){
	return ISurfaceCreator.rectPipe(pt1,pt2,width,height);
    }
    public static ISurface rectPipe(IVecI pt1, IVecI pt2, double left, double right, double bottom, double top){
	return ISurfaceCreator.rectPipe(pt1,pt2,left,right,bottom,top);
    }
    public static ISurface rectPipe(IVecI[] rail, double width, double height){
	return ISurfaceCreator.rectPipe(rail,width,height);
    }
    public static ISurface rectPipe(IVecI[] rail, double left, double right, double bottom, double top){
	return ISurfaceCreator.rectPipe(rail,left,right,bottom,top);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, double width, double height){
	return ISurfaceCreator.rectPipe(rail,deg,width,height);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg,
				    double left, double right, double bottom, double top){
	return ISurfaceCreator.rectPipe(rail,deg,left,right,bottom,top);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, boolean close, double width, double height){
	return ISurfaceCreator.rectPipe(rail,deg,close,width,height);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, boolean close,
				    double left, double right, double bottom, double top){
	return ISurfaceCreator.rectPipe(rail,deg,close,left,right,bottom,top);
    }
    public static ISurface rectPipe(ICurveI rail, double width, double height){
	return ISurfaceCreator.rectPipe(rail,width,height);
    }
    public static ISurface rectPipe(ICurveI rail,
				    double left, double right, double bottom, double top){
	return ISurfaceCreator.rectPipe(rail,left,right,bottom,top);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, double[] knots, double width, double height){
	return ISurfaceCreator.rectPipe(rail,deg,knots,width,height);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, double[] knots,
				    double left, double right, double bottom, double top){
	return ISurfaceCreator.rectPipe(rail,deg,knots,left,right,bottom,top);
    }
    
    
    
    /*********************
     * loft
     *********************/
    
    public static ISurface loft(ICurveI[] curves){
	return ISurfaceCreator.loft(curves);
    }
    
    public static ISurface loft(ICurveI curve1, ICurveI curve2 ){
	return ISurfaceCreator.loft(curve1,curve2);
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
     * flattening
     ********************************************************/
    
    public static ICurve flatten(ICurveI curve, IVecI planeDir, IVecI planePt){
	return ICurveCreator.flatten(curve,planeDir,planePt);
    }
    public static ICurve flatten(ICurveI curve, IVecI planeDir){
	return ICurveCreator.flatten(curve,planeDir);
    }
    public static ICurve flatten(ICurveI curve){ return ICurveCreator.flatten(curve); }
    
    public static ISurface flatten(ISurfaceI surface, IVecI planeDir, IVecI planePt){
	return ISurfaceCreator.flatten(surface,planeDir,planePt);
    }
    public static ISurface flatten(ISurfaceI surface, IVecI planeDir){
	return ISurfaceCreator.flatten(surface,planeDir);
    }
    public static ISurface flatten(ISurfaceI surface){ return ISurfaceCreator.flatten(surface); }
    
    
    /** cap a surface which is cyclically closed in u or v direction. */
    public static IBrep cap(ISurfaceI surface){ return ISurfaceCreator.cap(surface); }
    
    
    /** surface defined by closed profile.if the profile is flat, planar surface is created.
	if not lofting profile into the center of the profile
    */
    public static ISurface profileSurface(IVecI[] cps, int deg, double[] knots){
	return ISurfaceCreator.profileSurface(cps,deg,knots);
    }
    /**
       surface defined by closed profile.if the profile is flat, planar surface is created.
       if not lofting profile into the center of the profile
    */
    public static ISurface profileSurface(ICurveI profile){
	return ISurfaceCreator.profileSurface(profile);
    }
    /**
       surface by lofting profile into the center of the profile
    */
    public static ISurface radialSurface(IVecI[] cps, int deg, double[] knots){
	return ISurfaceCreator.radialSurface(cps,deg,knots);
    }
    /**
       surface by lofting profile into the center of the profile
    */
    public static ISurface radialSurface(ICurveI profile){
	return ISurfaceCreator.radialSurface(profile);
    }
    /**
       surface by lofting profile into the specified point
    */
    public static ISurface pointLoft(IVecI[] cps, int deg, double[] knots, IVecI center){
	return ISurfaceCreator.pointLoft(cps,deg,knots,center);
    }
    /**
       surface by lofting profile into the specified point
    */
    public static ISurface pointLoft(ICurveI profile, IVecI center){
	return ISurfaceCreator.pointLoft(profile,center);
    }    
    
    
    /*********************************************************
     * creating mesh
     ********************************************************/
    
    /** set mesh type (class of vertex, edge, face) */
    public static void meshType(IMeshType type){ IMeshCreator.meshType(type); }
    
    /** get the current mesh type (class of vertex, edge, face) */
    public static IMeshType meshType(){ return IMeshCreator.meshType(); }
        
    public static IMesh mesh(){ return IMeshCreator.mesh(); }
    public static IMesh mesh(IMeshGeo m){ return IMeshCreator.mesh(m); }
    public static IMesh mesh(IMesh m){ return IMeshCreator.mesh(m); }
    public static IMesh mesh(ArrayList<ICurveI> lines){ return IMeshCreator.mesh(lines); }
    public static IMesh mesh(ICurveI[] lines){ return IMeshCreator.mesh(lines); }
    public static IMesh mesh(IVec[][] matrix){ return IMeshCreator.mesh(matrix); }
    public static IMesh mesh(IVec[][] matrix, boolean triangulateDir){
	return IMeshCreator.mesh(matrix,triangulateDir); 
    }
    public static IMesh mesh(IVec[][] matrix, int unum, int vnum, boolean triangulateDir){
	return IMeshCreator.mesh(matrix,triangulateDir); 
    }
    public static IMesh mesh(ArrayList<IVertex> v, ArrayList<IEdge> e, ArrayList<IFace> f){
	return IMeshCreator.mesh(v,e,f);
    }
    public static IMesh mesh(IVertex[] vtx, IEdge[] edg,IFace[] fcs){
	return IMeshCreator.mesh(vtx,edg,fcs);
    }
    public static IMesh mesh(IVec[] vert){ return IMeshCreator.mesh(vert); }
    public static IMesh mesh(IVertex[] vert){ return IMeshCreator.mesh(vert); }
    public static IMesh mesh(IVertex v1, IVertex v2, IVertex v3){ return IMeshCreator.mesh(v1,v2,v3); }
    public static IMesh mesh(IVertex v1, IVertex v2, IVertex v3, IVertex v4){
	return IMeshCreator.mesh(v1,v2,v3,v4);
    }
    public static IMesh mesh(IVecI v1, IVecI v2, IVecI v3){ return IMeshCreator.mesh(v1,v2,v3); }
    public static IMesh mesh(IVecI v1, IVecI v2, IVecI v3, IVecI v4){
	return IMeshCreator.mesh(v1,v2,v3,v4);
    }
    public static IMesh mesh(double x1, double y1, double z1, double x2, double y2, double z2,
			     double x3, double y3, double z3){
	return IMeshCreator.mesh(x1,y1,z1,x2,y2,z2,x3,y3,z3);
    }
    public static IMesh mesh(double x1, double y1, double z1, double x2, double y2, double z2,
			     double x3, double y3, double z3, double x4, double y4, double z4){
        return IMeshCreator.mesh(x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4);
    }
    public static IMesh mesh(IFace[] fcs){ return IMeshCreator.mesh(fcs); }
    
    


    /*********************************************************
     * creating solid mesh
     ********************************************************/
    
    /*********************************************************
     * creating mesh box
     ********************************************************/
    public static IMesh meshBox(double x, double y, double z, double size){
	return IMeshCreator.box(x,y,z,size);
    }
    public static IMesh meshBox(double x, double y, double z, double width, double height, double depth){
        return IMeshCreator.box(x,y,z,width,height,depth);
    }
    public static IMesh meshBox(IVecI origin, double size){ return IMeshCreator.box(origin,size); }
    public static IMesh meshBox(IVecI origin, double width, double height, double depth){
        return IMeshCreator.box(origin,width,height,depth);
    }
    public static IMesh meshBox(IVecI origin, IVecI xvec, IVecI yvec, IVecI zvec){
	return IMeshCreator.box(origin,xvec,yvec,zvec);
    }
    public static IMesh meshBox(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4,
				IVecI pt5, IVecI pt6, IVecI pt7, IVecI pt8 ){
	return IMeshCreator.box(pt1,pt2,pt3,pt4,pt5,pt6,pt7,pt8);
    }
    public static IMesh meshBox(IVecI[][][] corners){ return IMeshCreator.box(corners); }
    public static IMesh meshBox(IVertex[][][] corners){ return IMeshCreator.box(corners); }




/** mesh sphere
     @param topDir length of vector is radius in vertical direction
     @param sideDir length of vector is radius in holizontal direction. sideDir is re-orthogonalized to topDir
*/
    public static IMesh meshSphere(IVecI center, IVecI topDir, IVecI sideDir, int resolution){
	return IMeshCreator.sphere(center,topDir,sideDir,resolution);
    }
    
    /** mesh sphere
     @param topDir length of vector is radius in vertical direction
     @param sideDir length of vector is radius in holizontal direction. sideDir is re-orthogonalized to topDir
    */
    public static IMesh meshSphere(IVecI center, IVecI topDir, IVecI sideDir){
	return IMeshCreator.sphere(center,topDir,sideDir);
    }
    
    /** mesh sphere
     @param topDir length of vector is radius in vertical direction
    */
    public static IMesh meshSphere(IVecI center, IVecI topDir, int resolution){
	return IMeshCreator.sphere(center,topDir,resolution);
    }
    
    
    /** mesh sphere
     @param topDir length of vector is radius in vertical direction
    */
    public static IMesh meshSphere(IVecI center, IVecI topDir){
	return IMeshCreator.sphere(center,topDir);
    }
    
    /** mesh sphere */
    public static IMesh meshSphere(IVecI center, IVecI topDir, double radius, int resolution){
	return IMeshCreator.sphere(center,topDir,radius,resolution);
    }
    /** mesh sphere */
    public static IMesh meshSphere(IVecI center, IVecI topDir, double topRadius, double sideRadius, int resolution){
	return IMeshCreator.sphere(center,topDir,topRadius,sideRadius,resolution);
    }
    
    /** mesh sphere */
    public static IMesh meshSphere(IVecI center, IVecI topDir, double radius){
	return IMeshCreator.sphere(center,topDir,radius);
    }
    /** mesh sphere */
    public static IMesh meshSphere(IVecI center, IVecI topDir, double topRadius, double sideRadius){
	return IMeshCreator.sphere(center,topDir,topRadius,sideRadius);
    }
    /** mesh sphere */
    public static IMesh meshSphere(IVecI center, double radius, int resolution){
	return IMeshCreator.sphere(center,radius,resolution);
    }
    /** mesh sphere */
    public static IMesh meshSphere(IVecI center, double radius){
	return IMeshCreator.sphere(center,radius);
    }
    /** mesh sphere */
    public static IMesh meshSphere(IVecI center, double zradius, double xyradius, int resolution){
	return IMeshCreator.sphere(center,zradius,xyradius,resolution);
    }
    /** mesh sphere */
    public static IMesh meshSphere(IVecI center, double zradius, double xyradius){
	return IMeshCreator.sphere(center,zradius,xyradius);
    }
    

    
    public static IMesh meshRectStick(IVecI pt1, IVecI pt2, IVecI udir, IVecI vdir){
	return IMeshCreator.rectStick(pt1,pt2,udir,vdir);
    }
    /**
       creating closed mesh stick with 2 points and with and height
       @param heightDir it provides reference to the direction of height but actual direction is re-calculated to be perpendicular to pt1-pt2 direction.
    */
    public static IMesh meshRectStick(IVecI pt1, IVecI pt2, double width, double height, IVecI heightDir){
	return IMeshCreator.rectStick(pt1,pt2,width,height,heightDir);
    }
    /** creating closed mesh stick. reference height direction is z axis */
    public static IMesh meshRectStick(IVecI pt1, IVecI pt2, double width, double height){
	return IMeshCreator.rectStick(pt1,pt2,width,height);
    }
    /**
       creating closed mesh stick with square profile. 
       @param heightDir it provides reference to the direction of height but actual direction is re-calculated to be perpendicular to pt1-pt2 direction.
    */
    public static IMesh meshSquareStick(IVecI pt1, IVecI pt2, double width, IVecI heightDir){
	return IMeshCreator.squareStick(pt1,pt2,width,heightDir);
    }
    /** creating closed mesh stick. height direction is z axis */
    public static IMesh meshSquareStick(IVecI pt1, IVecI pt2, double width){
	return IMeshCreator.squareStick(pt1,pt2,width);
    }
    /**
       creates closed mesh stick with polygon profile
       @param pt1 center of one side of a stick
       @param pt2 center of another side of a stick
       @param polygonVertexNum number of vertex of profile polygon
    */
    public static IMesh meshPolygonStick(IVecI pt1, IVecI pt2, double radius, int polygonVertexNum){
	return IMeshCreator.polygonStick(pt1,pt2,radius,polygonVertexNum);
    }
    /**
       creates closed mesh stick with polygon profile
       @param pt1 center of one side of a stick
       @param pt2 center of another side of a stick
       @param polygonVertexNum number of vertex of profile polygon
       @param heightDir reference vector to locate one of vertex of polygon aligned with this direction
    */
    public static IMesh meshPolygonStick(IVecI pt1, IVecI pt2, double radius, int polygonVertexNum,
					 IVecI heightDir){
	return IMeshCreator.polygonStick(pt1,pt2,radius,polygonVertexNum,heightDir);
    }
    
    /**
       creates closed mesh stick with polygon profile
       @param pt1 center of one side of a stick
       @param pt2 center of another side of a stick
       @param polygonVertexNum number of vertex of profile polygon
    */
    public static IMesh meshPolygonStick(IVecI pt1, IVecI pt2, double radius1, double radius2,
					 int polygonVertexNum){
	return IMeshCreator.polygonStick(pt1,pt2,radius1,radius2,polygonVertexNum);
    }
    /**
       creates closed mesh stick with polygon profile
       @param pt1 center of one side of a stick
       @param pt2 center of another side of a stick
       @param polygonVertexNum number of vertex of profile polygon
       @param heightDir reference vector to locate one of vertex of polygon aligned with this direction
    */
    public static IMesh meshPolygonStick(IVecI pt1, IVecI pt2, double radius1, double radius2,
					 int polygonVertexNum, IVecI heightDir){
	return IMeshCreator.polygonStick(pt1,pt2,radius1,radius2,polygonVertexNum,heightDir);
    }

    public static IMesh meshPolygonStick(ICurveI railCurve, double radius, int polygonVertexNum,
					 int railSegmentNum){
	return IMeshCreator.polygonStick(railCurve,radius,polygonVertexNum,railSegmentNum);
    }
    
    public static IMesh meshPolygonStick(ICurveI railCurve, double radius, int polygonVertexNum){
	return IMeshCreator.polygonStick(railCurve,radius,polygonVertexNum);
    }
    
    /** round stick */
    public static IMesh meshStick(IVecI pt1, IVecI pt2, double radius){
	return IMeshCreator.stick(pt1,pt2,radius);
    }
    /** round stick */
    public static IMesh meshStick(IVecI pt1, IVecI pt2, double radius1, double radius2){
	return IMeshCreator.stick(pt1,pt2,radius1,radius2);
    }
    /** round stick */
    public static IMesh meshStick(ICurveI railCurve, double radius, int railSegmentNum){
	return IMeshCreator.roundStick(railCurve,radius,railSegmentNum);
    }
    /** round stick */
    public static IMesh meshStick(ICurveI railCurve, double radius){
	return IMeshCreator.roundStick(railCurve,radius);
    }
    
    /** round stick (alias of stick()) */
    public static IMesh meshRoundStick(IVecI pt1, IVecI pt2, double radius){
	return IMeshCreator.roundStick(pt1,pt2,radius);
    }
    /** round stick (alias of stick()) */
    public static IMesh meshRoundStick(IVecI pt1, IVecI pt2, double radius1, double radius2){
	return IMeshCreator.roundStick(pt1,pt2,radius1,radius2);
    }
    /** round stick */
    public static IMesh meshRoundStick(ICurveI railCurve, double radius, int railSegmentNum){
	return IMeshCreator.roundStick(railCurve,radius,railSegmentNum);
    }
    /** round stick */
    public static IMesh meshRoundStick(ICurveI railCurve, double radius){
	return IMeshCreator.roundStick(railCurve,radius);
    }
    
    /** square stick */
    public static IMesh meshSquareStick(ICurveI railCurve, double radius, int railSegmentNum){
	return IMeshCreator.squareStick(railCurve,radius,railSegmentNum);
    }
    /** square stick */
    public static IMesh meshSquareStick(ICurveI railCurve, double radius){
	return IMeshCreator.squareStick(railCurve,radius);
    }
    
    
    
    
    /*********************************************************
     * creating vector 
     ********************************************************/

    public static IVec vec(){ return v(); }
    public static IVec vec(double x, double y, double z){ return v(x,y,z); }
    public static IVec vec(double x, double y){ return v(x,y); }
    public static IVec vec(IVec v){ return v(v); }
    public static IVec vec(IVecI v){ return v(v); }
    public static IVec vec(IDoubleI x, IDoubleI y, IDoubleI z){ return v(x,y,z); }
    public static IVec vec(IDoubleI x, IDoubleI y){ return v(x,y); }
    public static IVec vec(IVec2I v){ return v(v); }
    
    /*********************************************************
     * vector shorter name 
     ********************************************************/
    
    public static IVec v(){ return new IVec(); }
    public static IVec v(double x, double y, double z){ return new IVec(x,y,z); }
    public static IVec v(double x, double y){ return new IVec(x,y); }
    public static IVec v(IVec v){ return new IVec(v); }
    public static IVec v(IVecI v){ return new IVec(v); }
    public static IVec v(IDoubleI x, IDoubleI y, IDoubleI z){
	return new IVec(x,y,z);
    }
    public static IVec v(IDoubleI x, IDoubleI y){ return new IVec(x,y,new IDouble(0)); }
    public static IVec v(IVec2I v){ return new IVec(v); }
    
    /*********************************************************
     * vector longer name
     ********************************************************/
    public static IVec vector(){ return v(); }
    public static IVec vector(double x, double y, double z){ return v(x,y,z); }
    public static IVec vector(double x, double y){ return v(x,y); }
    public static IVec vector(IVec v){ return v(v); }
    public static IVec vector(IVecI v){ return v(v); }
    public static IVec vector(IDoubleI x, IDoubleI y, IDoubleI z){ return v(x,y,z); }
    public static IVec vector(IDoubleI x, IDoubleI y){ return v(x,y); }
    public static IVec vector(IVec2I v){ return v(v); }
    
    
    
    /*********************************************************
     * creating 4 dimensional vector with weight
     ********************************************************/
    public static IVec4 vec4(){ return v4(); }
    public static IVec4 vec4(double x, double y, double z, double w){ return v4(x,y,z,w); }
    public static IVec4 vec4(IVec v, double w){ return v4(v,w); }
    public static IVec4 vec4(IVec4 v){ return v4(v); }
    public static IVec4 vec4(IVecI v){ return v4(v); }
    public static IVec4 vec4(IVecI v, double w){ return v4(v,w); }
    public static IVec4 vec4(IVecI v, IDoubleI w){ return v4(v,w); }
    public static IVec4 vec4(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w){ return v4(x,y,z,w); }
    
    /*********************************************************
     * 4d vector shorter name 
     ********************************************************/
    
    public static IVec4 v4(){ return new IVec4(); }
    public static IVec4 v4(double x, double y, double z, double w){ return new IVec4(x,y,z,w); }
    public static IVec4 v4(IVec v, double w){ return new IVec4(v,w); }
    public static IVec4 v4(IVec4 v){ return new IVec4(v); }
    public static IVec4 v4(IVecI v){ return new IVec4(v); }
    public static IVec4 v4(IVecI v, double w){ return new IVec4(v,w); }
    public static IVec4 v4(IVecI v, IDoubleI w){ return new IVec4(v,w); }
    public static IVec4 v4(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w){
	return new IVec4(x,y,z,w);
    }
    
    /*********************************************************
     * 4d vector longer name
     ********************************************************/
    public static IVec4 vector4(){ return v4(); }
    public static IVec4 vector4(double x, double y, double z, double w){ return v4(x,y,z,w); }
    public static IVec4 vector4(IVec v, double w){ return v4(v,w); }
    public static IVec4 vector4(IVec4 v){ return v4(v); }
    public static IVec4 vector4(IVecI v){ return v4(v); }
    public static IVec4 vector4(IVecI v, double w){ return v4(v,w); }
    public static IVec4 vector4(IVecI v, IDoubleI w){ return v4(v,w); }
    public static IVec4 vector4(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w){ return v4(x,y,z,w); }
    
    /*********************************************************
     * creating 2 dimensional vector 
     ********************************************************/
    
    public static IVec2 vec2(){ return v2(); }
    public static IVec2 vec2(double x, double y){ return v2(x,y); }
    public static IVec2 vec2(IVec2 v){ return v2(v); }
    public static IVec2 vec2(IVecI v){ return v2(v); }
    public static IVec2 vec2(IDoubleI x, IDoubleI y){ return v2(x,y); }
    
    /*********************************************************
     * 2d vector  shorter name
     ********************************************************/
    
    public static IVec2 v2(){ return new IVec2(); }
    public static IVec2 v2(double x, double y){ return new IVec2(x,y); }
    public static IVec2 v2(IVec2 v){ return new IVec2(v); }
    public static IVec2 v2(IVecI v){ return new IVec2(v); }
    public static IVec2 v2(IDoubleI x, IDoubleI y){ return new IVec2(x,y); }
    
    /*********************************************************
     * 2d vector  longer name
     ********************************************************/
    
    public static IVec2 vector2(){ return v2(); }
    public static IVec2 vector2(double x, double y){ return v2(x,y); }
    public static IVec2 vector2(IVec2 v){ return v2(v); }
    public static IVec2 vector2(IVecI v){ return v2(v); }
    public static IVec2 vector2(IDoubleI x, IDoubleI y){ return v2(x,y); }
    
    
    /*********************************************************
     * vector array
     ********************************************************/
    
    public static IVec[] vec(double x, double y, double z, double ... xyzvals){
	return v(x,y,z,xyzvals);
    }
    public static IVec[] vec(IVec ... v){ return v(v); }
    public static IVecI[] vec(IVecI ... v){ return v(v); }
    public static IVec[] vec(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI ... xyzvals){
	return v(x,y,z,xyzvals);
    }
    public static IVec[] vec(IVec2I ... v){ return v(v); }
    
    public static IVec[][] vec(IVec[] ... v){ return v(v); }
    public static IVecI[][] vec(IVecI[] ... v){ return v(v); }
    public static IVec[][][] vec(IVec[][] ... v){ return v(v); }
    public static IVecI[][][] vec(IVecI[][] ... v){ return v(v); }
    
    /*********************************************************
     * vector array shorter name
     ********************************************************/
    
    public static IVec[] v(double x, double y, double z, double ... xyzvals){
	int num = xyzvals.length/3 + 1;
	if(xyzvals.length%3>0) num++;
	IVec[] array = new IVec[num];
	array[0] = new IVec(x,y,z);
	for(int i=1; i<num; i++){
	    array[i] = new IVec(xyzvals[(i-1)*3],
				(i-1)*3+1<xyzvals.length?xyzvals[(i-1)*3+1]:0,
				(i-1)*3+2<xyzvals.length?xyzvals[(i-1)*3+2]:0);
	}
	return array;
    }
    public static IVec[] v(IVec ... v){ return v; }
    public static IVecI[] v(IVecI ... v){ return v; 
	/*
	if(v==null) return null;
	IVec[] array = new IVec[v.length];
	for(int i=0; i<v.length; i++){ array[i] = v[i].get(); }
	return array;
	*/
    }
    public static IVec[] v(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI ... xyzvals){
	int num = xyzvals.length/3 + 1;
	if(xyzvals.length%3>0) num++;
	IVec[] array = new IVec[num];
	array[0] = new IVec(x,y,z);
	for(int i=1; i<num; i++){
	    array[i] = new IVec(xyzvals[(i-1)*3],
				(i-1)*3+1<xyzvals.length?xyzvals[(i-1)*3+1]:new IDouble(0),
				(i-1)*3+2<xyzvals.length?xyzvals[(i-1)*3+2]:new IDouble(0));
	}
	return array;
    }
    public static IVec[] v(IVec2I ... v){
	if(v==null) return null;
	IVec[] array = new IVec[v.length];
	for(int i=0; i<v.length; i++){ array[i] = new IVec(v[i]); }
	return array;
    }
    
    /**
       IVec 2d array
    */
    public static IVec[][] v(IVec[] ... v){ return v; }
    public static IVecI[][] v(IVecI[] ... v){ return v; }
    
    /**
       IVec 3d array
    */
    public static IVec[][][] v(IVec[][] ... v){ return v; }
    public static IVecI[][][] v(IVecI[][] ... v){ return v; }
    
    /*********************************************************
     * vector array longer name
     ********************************************************/
    public static IVec[] vector(double x, double y, double z, double ... xyzvals){
	return v(x,y,z,xyzvals);
    }
    public static IVec[] vector(IVec ... v){ return v(v); }
    public static IVecI[] vector(IVecI ... v){ return v(v); }
    public static IVec[] vector(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI ... xyzvals){
	return v(x,y,z,xyzvals);
    }
    public static IVec[] vector(IVec2I ... v){ return v(v); }
    
    public static IVec[][] vector(IVec[] ... v){ return v(v); }
    public static IVecI[][] vector(IVecI[] ... v){ return v(v); }
    public static IVec[][][] vector(IVec[][] ... v){ return v(v); }
    public static IVecI[][][] vector(IVecI[][] ... v){ return v(v); }
    
    
    
    /*********************************************************
     * vector (IVec4) array
     ********************************************************/
    
    public static IVec4[] vec4(double x, double y, double z, double w, double ... xyzwvals){
	return v4(x,y,z,w,xyzwvals);
    }
    public static IVec4[] vec4(IVec4 ... v){ return v4(v); }
    public static IVec4I[] vec4(IVec4I ... v){ return v4(v); }
    public static IVec4[] vec4(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w, IDoubleI ... xyzwvals){
	return v4(x,y,z,w,xyzwvals);
    }
    
    /** IVec4 2d array */
    public static IVec4[][] vec4(IVec4[] ... v){ return v4(v); }
    public static IVec4I[][] vec4(IVec4I[] ... v){ return v4(v); }
    
    /** IVec4 3d array */
    public static IVec4[][][] vec4(IVec4[][] ... v){ return v4(v); }
    public static IVec4I[][][] vec4(IVec4I[][] ... v){ return v4(v); }
    
    public static IVec4[] v4(double x, double y, double z, double w, double ... xyzwvals){
	int num = xyzwvals.length/4 + 1;
	if(xyzwvals.length%4>0) num++;
	IVec4[] array = new IVec4[num];
	array[0] = new IVec4(x,y,z,w);
	for(int i=1; i<num; i++){
	    array[i] = new IVec4(xyzwvals[(i-1)*4],
				 (i-1)*4+1<xyzwvals.length?xyzwvals[(i-1)*4+1]:0,
				 (i-1)*4+2<xyzwvals.length?xyzwvals[(i-1)*4+2]:0,
				 (i-1)*4+3<xyzwvals.length?xyzwvals[(i-1)*4+3]:0);
	}
	return array;
    }
    public static IVec4[] v4(IVec4 ... v){ return v; }
    public static IVec4I[] v4(IVec4I ... v){ return v; }
    public static IVec4[] v4(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w, IDoubleI ... xyzwvals){
	int num = xyzwvals.length/4 + 1;
	if(xyzwvals.length%4>0) num++;
	IVec4[] array = new IVec4[num];
	array[0] = new IVec4(x,y,z,w);
	for(int i=1; i<num; i++){
	    array[i] = new IVec4(xyzwvals[(i-1)*4],
				 (i-1)*4+1<xyzwvals.length?xyzwvals[(i-1)*4+1]:new IDouble(0),
				 (i-1)*4+2<xyzwvals.length?xyzwvals[(i-1)*4+2]:new IDouble(0),
				 (i-1)*4+3<xyzwvals.length?xyzwvals[(i-1)*4+3]:new IDouble(0));
	}
	return array;
    }
    
    /** IVec4 2d array */
    public static IVec4[][] v4(IVec4[] ... v){ return v; }
    public static IVec4I[][] v4(IVec4I[] ... v){ return v; }
    
    /** IVec4 3d array */
    public static IVec4[][][] v4(IVec4[][] ... v){ return v; }
    public static IVec4I[][][] v4(IVec4I[][] ... v){ return v; }
    
    public static IVec4[] vector4(double x, double y, double z, double w, double ... xyzwvals){
	return v4(x,y,z,w,xyzwvals);
    }
    public static IVec4[] vector4(IVec4 ... v){ return v4(v); }
    public static IVec4I[] vector4(IVec4I ... v){ return v4(v); }
    public static IVec4[] vector4(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w, IDoubleI ... xyzwvals){
	return v4(x,y,z,w,xyzwvals);
    }
    
    /** IVec4 2d array */
    public static IVec4[][] vector4(IVec4[] ... v){ return v4(v); }
    public static IVec4I[][] vector4(IVec4I[] ... v){ return v4(v); }
    
    /** IVec4 3d array */
    public static IVec4[][][] vector4(IVec4[][] ... v){ return v4(v); }
    public static IVec4I[][][] vector4(IVec4I[][] ... v){ return v4(v); }
    
    
    /*********************************************************
     * vector (IVec2) array
     ********************************************************/
    public static IVec2[] vec2(double x, double y, double ... xyvals){ return v2(x,y,xyvals); }
    public static IVec2[] vec2(IVec2 ... v){ return v2(v); }
    public static IVec2I[] vec2(IVec2I ... v){ return v2(v); }
    public static IVec2[] vec2(IDoubleI x, IDoubleI y, IDoubleI... xyvals){ return v2(x,y,xyvals); }
    
    /** IVec2 2d array from IVecI*/
    public static IVec2I[] vec2(IVecI ... v){ return v2(v); }
    /** IVec2 2d array from IVecI*/
    public static IVec2[] vec2(IVec ... v){ return v2(v); }
    
    /** IVec2 2d array */
    public static IVec2[][] vec2(IVec2[] ... v){ return v2(v); }
    public static IVec2I[][] vec2(IVec2I[] ... v){ return v2(v); }
    
    /** IVec2 3d array */
    public static IVec2[][][] vec2(IVec2[][] ... v){ return v2(v); }
    public static IVec2I[][][] vec2(IVec2I[][] ... v){ return v2(v); }
    
    
    public static IVec2[] v2(double x, double y, double ... xyvals){
	int num = xyvals.length/2 + 1;
	if(xyvals.length%2>0) num++;
	IVec2[] array = new IVec2[num];
	array[0] = new IVec2(x,y);
	for(int i=1; i<num; i++){
	    array[i] = new IVec2(xyvals[(i-1)*2],(i-1)*2+1<xyvals.length?xyvals[(i-1)*2+1]:0);
	}
	return array;
    }
    public static IVec2[] v2(IVec2 ... v){ return v; }
    public static IVec2I[] v2(IVec2I ... v){ return v; }
    public static IVec2[] v2(IDoubleI x, IDoubleI y, IDoubleI... xyvals){
	int num = xyvals.length/2 + 1;
	if(xyvals.length%2>0) num++;
	IVec2[] array = new IVec2[num];
	array[0] = new IVec2(x,y);
	for(int i=1; i<num; i++){
	    array[i] =
		new IVec2(xyvals[(i-1)*2],(i-1)*2+1<xyvals.length?xyvals[(i-1)*2+1]:new IDouble(0));
	}
	return array;
    }
    /** IVec2 array from IVec (projection) */
    public static IVec2[] v2(IVec ... v){
	IVec2[] array = new IVec2[v.length];
	for(int i=0; i<v.length; i++){ array[i] = v[i].to2d(); }
	return array;
    }
    
    /** IVec2I array from IVecI (projection) */
    public static IVec2I[] v2(IVecI ... v){
	IVec2I[] array = new IVec2I[v.length];
	for(int i=0; i<v.length; i++){ array[i] = v[i].to2d(); }
	return array;
    }
    
    
    
    /** IVec4 2d array */
    public static IVec2[][] v2(IVec2[] ... v){ return v; }
    public static IVec2I[][] v2(IVec2I[] ... v){ return v; }
    
    /** IVec4 3d array */
    public static IVec2[][][] v2(IVec2[][] ... v){ return v; }
    public static IVec2I[][][] v2(IVec2I[][] ... v){ return v; }
    
    public static IVec2[] vector2(double x, double y, double ... xyvals){ return v2(x,y,xyvals); }
    public static IVec2[] vector2(IVec2 ... v){ return v2(v); }
    public static IVec2I[] vector2(IVec2I ... v){ return v2(v); }
    public static IVec2[] vector2(IDoubleI x, IDoubleI y, IDoubleI... xyvals){ return v2(x,y,xyvals); }
    
    /** IVec4 2d array */
    public static IVec2[][] vector2(IVec2[] ... v){ return v2(v); }
    public static IVec2I[][] vector2(IVec2I[] ... v){ return v2(v); }
    
    /** IVec4 3d array */
    public static IVec2[][][] vector2(IVec2[][] ... v){ return v2(v); }
    public static IVec2I[][][] vector2(IVec2I[][] ... v){ return v2(v); }
    
    
    /*********************************************************
     * generic array 
     ********************************************************/
    /** create array of any class. */
    public static <T> T[] array(T ... vals){ return a(vals); }
    
    /** create array of any class. */
    public static <T> T[] array(int length, T ... vals){ return a(length,vals); }
    
    /** create 2D array of any class. */
    public static <T> T[][] array2(int length1, int length2, T ... vals){
	return a2(length1,length2,vals);
    }
    
    /** create 2D array of any class. */
    public static <T> T[][] array2(int length2, T ... vals){ return a2(length2,vals); }
    
    /** create 3D array of any class. */
    public static <T> T[][][] array3(int length1, int length2, int length3, T ... vals){
	return a3(length1,length2,length3,vals);
    }
    
    /** create 3D array of any class. */
    public static <T> T[][][] array3(int length2, int length3, T ... vals){
	return a3(length2,length3,vals);
    }
    
    /*********************************************************
     * generic array short name
     ********************************************************/
    
    /** create array of any class. */
    public static <T> T[] arr(T ... vals){ return a(vals); }
    
    /** create array of any class. */
    public static <T> T[] arr(int length, T ... vals){ return a(length,vals); }

    /** create 2D array of any class. */
    public static <T> T[][] arr2(int length1, int length2, T ... vals){
	return a2(length1,length2,vals);
    }
    
    /** create 2D array of any class. */
    public static <T> T[][] arr2(int length2, T ... vals){ return a2(length2,vals); }
    
    /** create 3D array of any class. */
    public static <T> T[][][] arr3(int length1, int length2, int length3, T ... vals){
	return a3(length1,length2,length3,vals);
    }
    
    /** create 3D array of any class. */
    public static <T> T[][][] arr3(int length2, int length3, T ... vals){
	return a3(length2,length3,vals);
    }
    
    
    /*********************************************************
     * generic array much shorter name
     ********************************************************/
    /** create array of any class. */
    public static <T> T[] a(T ... vals){ return vals; }
    
    /** create array of any class. */
    @SuppressWarnings({"unchecked"})
    public static <T> T[] a(int length, T ... vals){
	if(vals==null) return null;
	if(vals.length!=length){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	T[] array = (T[])Array.newInstance(vals[0].getClass(),length);
	int i=0;
	for(; i<length && i<vals.length; i++) array[i] = vals[i];
	//for(; i<length; i++) array[i] = null;
	return vals;
    }
    
    /** create 2D array of any class. */
    @SuppressWarnings({"unchecked"})
    public static <T> T[][] a2(int length1, int length2, T ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	T[][] array = (T[][])Array.newInstance(vals.getClass(),length1);
	for(int i=0; i<length1; i++){
	    array[i] = (T[])Array.newInstance(vals[0].getClass(),length2);
	}
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		array[i][j] = vals[idx++];
	return array;
    }
    
    /** create 2D array of any class. */
    public static <T> T[][] a2(int length2, T ... vals){
	if(vals==null) return null;
	int length1 = vals.length/length2;
	if(vals.length!=length1*length2){
	    IOut.err("length2 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array2(length1,length2,vals);
    }
    
    /** create 3D array of any class. */
    @SuppressWarnings({"unchecked"})
    public static <T> T[][][] a3(int length1, int length2, int length3, T ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2*length3){
	    IOut.err("length1*length2*length3 doesn't match with number of values");
	}
	T[][][] array = (T[][][])Array.newInstance
	    ( ((T[][])Array.newInstance(vals.getClass(),0)).getClass(), length1); // zero?
	
	for(int i=0; i<length1; i++){
	    array[i] = (T[][])Array.newInstance(vals.getClass(),length2);
	}
	for(int i=0; i<length1; i++){
	    for(int j=0; j<length2; j++){
		array[i][j] = (T[])Array.newInstance(vals[0].getClass(),length3);
	    }
	}
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		for(int k=0; k<length3 && idx<vals.length; k++)
		    array[i][j][k] = vals[idx++];
	return array;
    }
    
    /** create 3D array of any class. */
    public static <T> T[][][] a3(int length2, int length3, T ... vals){
	if(vals==null) return null;
	int length1 = vals.length/(length2*length3);
	if(vals.length!=length1*length2*length3){
	    IOut.err("length2*length3 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array3(length1,length2,length3,vals);
    }
    
    
    
    
    /*********************************************************
     * primitive array 
     ********************************************************/
    /*********************************************************
     * int array 
     ********************************************************/
    /** create array of any class. */
    //public static int[] array(int ... vals){ return vals; }
    
    /** create 2D array of any class. */
    /*
    public static int[][] array2(int length1, int length2, int ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	int[][] array = new int[length1][length2];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++) array[i][j] = vals[idx++];
	return array;
    }
    */
    /** create 3D array of any class. */
    /*
    public static int[][][] array3(int length1, int length2, int length3, int ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2*length3){
	    IOut.err("length1*length2*length3 doesn't match with number of values");
	}
	int[][][] array = new int[length1][length2][length3];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		for(int k=0; k<length3 && idx<vals.length; k++) array[i][j][k] = vals[idx++];
	return array;
    }
    */
    
    /** create array of any class. */
    //public static int[] arr(int ... vals){ return array(vals); }
    /** create 2D array of any class. */
    /*
    public static int[][] arr2(int length1, int length2, int ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static int[][][] arr3(int length1, int length2, int length3, int ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    
    /** create array of any class. */
    //public static int[] a(int ... vals){ return array(vals); }
    //public static Integer[] ia(Integer ... vals){ return array(vals); }
    /** create 2D array of any class. */
    /*
    public static int[][] a2(int length1, int length2, int ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static int[][][] a3(int length1, int length2, int length3, int ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    
    /*********************************************************
     * double array 
     ********************************************************/
    /** create array of any class. */
    //public static double[] array(double ... vals){ return vals; }
    
    /** create array of any class. */
    /*
    public static double[] array(int length, double ... vals){
	if(vals==null) return null;
	if(vals.length!=length){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	double[] array = new double[length];
	int i=0;
	for(; i<length && i<vals.length; i++) array[i] = vals[i];
	return vals;
    }
    */
    
    /** create 2D array of any class. */
    /*
    public static double[][] array2(int length1, int length2, double ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	double[][] array = new double[length1][length2];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		array[i][j] = vals[idx++];
	return array;
    }
    */
    
    /** create 2D array of any class. */
    /*
    public static double[][] array2(int length2, double ... vals){
	if(vals==null) return null;
	int length1 = vals.length/length2;
	if(vals.length!=length1*length2){
	    IOut.err("length2 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array2(length1,length2,vals);
    }
    */
    
    /** create 3D array of any class. */
    /*
    public static double[][][] array3(int length1, int length2, int length3, double ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2*length3){
	    IOut.err("length1*length2*length3 doesn't match with number of values");
	}
	double[][][] array = new double[length1][length2][length3];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		for(int k=0; k<length3 && idx<vals.length; k++)
		    array[i][j][k] = vals[idx++];
	return array;
    }
    */
    
    /** create 3D array of any class. */
    /*
    public static double[][][] array3(int length2, int length3, double ... vals){
	if(vals==null) return null;
	int length1 = vals.length/(length2*length3);
	if(vals.length!=length1*length2*length3){
	    IOut.err("length2*length3 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array3(length1,length2,length3,vals);
    }
    */
    /** create array of any class. */
    //public static double[] arr(double ... vals){ return array(vals); }
    /** create array of any class. */
    //public static double[] arr(int length, double ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static double[][] arr2(int length1, int length2, double ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static double[][] arr2(int length2, double ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static double[][][] arr3(int length1, int length2, int length3, double ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static double[][][] arr3(int length2, int length3, double ... vals){
	return array3(length2,length3,vals); 
    }
    */
    
    /** create array of any class. */
    //public static double[] a(double ... vals){ return array(vals); }
    /** create array of any class. */
    //public static double[] a(int length, double ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static double[][] a2(int length1, int length2, double ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static double[][] a2(int length2, double ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static double[][][] a3(int length1, int length2, int length3, double ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static double[][][] a3(int length2, int length3, double ... vals){
	return array3(length2,length3,vals); 
    }
    */
    
    /*********************************************************
     * float array 
     ********************************************************/
    /** create array of any class. */
    //public static float[] array(float ... vals){ return vals; }
    
    /** create array of any class. */
    /*
    public static float[] array(int length, float ... vals){
	if(vals==null) return null;
	if(vals.length!=length){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	float[] array = new float[length];
	int i=0;
	for(; i<length && i<vals.length; i++) array[i] = vals[i];
	return vals;
    }
    */
    
    /** create 2D array of any class. */
    /*
    public static float[][] array2(int length1, int length2, float ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	float[][] array = new float[length1][length2];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		array[i][j] = vals[idx++];
	return array;
    }
    */
    
    /** create 2D array of any class. */
    /*
    public static float[][] array2(int length2, float ... vals){
	if(vals==null) return null;
	int length1 = vals.length/length2;
	if(vals.length!=length1*length2){
	    IOut.err("length2 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array2(length1,length2,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static float[][][] array3(int length1, int length2, int length3, float ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2*length3){
	    IOut.err("length1*length2*length3 doesn't match with number of values");
	}
	float[][][] array = new float[length1][length2][length3];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		for(int k=0; k<length3 && idx<vals.length; k++)
		    array[i][j][k] = vals[idx++];
	return array;
    }
    */
    
    /** create 3D array of any class. */
    /*
    public static float[][][] array3(int length2, int length3, float ... vals){
	if(vals==null) return null;
	int length1 = vals.length/(length2*length3);
	if(vals.length!=length1*length2*length3){
	    IOut.err("length2*length3 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array3(length1,length2,length3,vals);
    }
    */
    
    /** create array of any class. */
    //public static float[] arr(float ... vals){ return array(vals); }
    /** create array of any class. */
    //public static float[] arr(int length, float ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static float[][] arr2(int length1, int length2, float ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static float[][] arr2(int length2, float ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static float[][][] arr3(int length1, int length2, int length3, float ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static float[][][] arr3(int length2, int length3, float ... vals){
	return array3(length2,length3,vals); 
    }
    */
    /** create array of any class. */
    //public static float[] a(float ... vals){ return array(vals); }
    /** create array of any class. */
    //public static float[] a(int length, float ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static float[][] a2(int length1, int length2, float ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static float[][] a2(int length2, float ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static float[][][] a3(int length1, int length2, int length3, float ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static float[][][] a3(int length2, int length3, float ... vals){
	return array3(length2,length3,vals); 
    }
    */
    
    /*********************************************************
     * short array 
     ********************************************************/
    /** create array of any class. */
    //public static short[] array(short ... vals){ return vals; }
    /** create array of any class. */
    /*
    public static short[] array(int length, short ... vals){
	if(vals==null) return null;
	if(vals.length!=length){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	short[] array = new short[length];
	int i=0;
	for(; i<length && i<vals.length; i++) array[i] = vals[i];
	return vals;
    }
    */
    /** create 2D array of any class. */
    /*
    public static short[][] array2(int length1, int length2, short ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	short[][] array = new short[length1][length2];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		array[i][j] = vals[idx++];
	return array;
    }
    */
    /** create 2D array of any class. */
    /*
    public static short[][] array2(int length2, short ... vals){
	if(vals==null) return null;
	int length1 = vals.length/length2;
	if(vals.length!=length1*length2){
	    IOut.err("length2 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array2(length1,length2,vals);
    }
    */
    
    /** create 3D array of any class. */
    /*
    public static short[][][] array3(int length1, int length2, int length3, short ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2*length3){
	    IOut.err("length1*length2*length3 doesn't match with number of values");
	}
	short[][][] array = new short[length1][length2][length3];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		for(int k=0; k<length3 && idx<vals.length; k++)
		    array[i][j][k] = vals[idx++];
	return array;
    }
    */
    /** create 3D array of any class. */
    /*
    public static short[][][] array3(int length2, int length3, short ... vals){
	if(vals==null) return null;
	int length1 = vals.length/(length2*length3);
	if(vals.length!=length1*length2*length3){
	    IOut.err("length2*length3 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array3(length1,length2,length3,vals);
    }
    */
    
    /** create array of any class. */
    //public static short[] arr(short ... vals){ return array(vals); }
    /** create array of any class. */
    //public static short[] arr(int length, short ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static short[][] arr2(int length1, int length2, short ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static short[][] arr2(int length2, short ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static short[][][] arr3(int length1, int length2, int length3, short ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static short[][][] arr3(int length2, int length3, short ... vals){
	return array3(length2,length3,vals); 
	}
    */
    
    /** create array of any class. */
    //public static short[] a(short ... vals){ return array(vals); }
    /** create array of any class. */
    //public static short[] a(int length, short ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static short[][] a2(int length1, int length2, short ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static short[][] a2(int length2, short ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static short[][][] a3(int length1, int length2, int length3, short ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static short[][][] a3(int length2, int length3, short ... vals){
	return array3(length2,length3,vals); 
    }
    */

    
    
    
    /*********************************************************
     * long array 
     ********************************************************/
    /** create array of any class. */
    //public static long[] array(long ... vals){ return vals; }
    
    /** create array of any class. */
    /*
    public static long[] array(int length, long ... vals){
	if(vals==null) return null;
	if(vals.length!=length){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	long[] array = new long[length];
	int i=0;
	for(; i<length && i<vals.length; i++) array[i] = vals[i];
	return vals;
    }
    */
    /** create 2D array of any class. */
    /*
    public static long[][] array2(int length1, int length2, long ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	long[][] array = new long[length1][length2];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		array[i][j] = vals[idx++];
	return array;
    }
    */
    /** create 2D array of any class. */
    /*
    public static long[][] array2(int length2, long ... vals){
	if(vals==null) return null;
	int length1 = vals.length/length2;
	if(vals.length!=length1*length2){
	    IOut.err("length2 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array2(length1,length2,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static long[][][] array3(int length1, int length2, int length3, long ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2*length3){
	    IOut.err("length1*length2*length3 doesn't match with number of values");
	}
	long[][][] array = new long[length1][length2][length3];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		for(int k=0; k<length3 && idx<vals.length; k++)
		    array[i][j][k] = vals[idx++];
	return array;
    }
    */
    /** create 3D array of any class. */
    /*
    public static long[][][] array3(int length2, int length3, long ... vals){
	if(vals==null) return null;
	int length1 = vals.length/(length2*length3);
	if(vals.length!=length1*length2*length3){
	    IOut.err("length2*length3 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array3(length1,length2,length3,vals);
    }
    */
    /** create array of any class. */
    //public static long[] arr(long ... vals){ return array(vals); }
    /** create array of any class. */
    //public static long[] arr(int length, long ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static long[][] arr2(int length1, int length2, long ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static long[][] arr2(int length2, long ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static long[][][] arr3(int length1, int length2, int length3, long ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static long[][][] arr3(int length2, int length3, long ... vals){
	return array3(length2,length3,vals); 
    }
    */
    /** create array of any class. */
    //public static long[] a(long ... vals){ return array(vals); }
    /** create array of any class. */
    //public static long[] a(int length, long ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static long[][] a2(int length1, int length2, long ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static long[][] a2(int length2, long ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static long[][][] a3(int length1, int length2, int length3, long ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static long[][][] a3(int length2, int length3, long ... vals){
	return array3(length2,length3,vals); 
    }
    */

    
    
    /*********************************************************
     * byte array 
     ********************************************************/
    /** create array of any class. */
    //public static byte[] array(byte ... vals){ return vals; }
    
    /** create array of any class. */
    /*
    public static byte[] array(int length, byte ... vals){
	if(vals==null) return null;
	if(vals.length!=length){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	byte[] array = new byte[length];
	int i=0;
	for(; i<length && i<vals.length; i++) array[i] = vals[i];
	return vals;
    }
    */
    
    /** create 2D array of any class. */
    /*
    public static byte[][] array2(int length1, int length2, byte ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	byte[][] array = new byte[length1][length2];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		array[i][j] = vals[idx++];
	return array;
    }
    */
    /** create 2D array of any class. */
    /*
    public static byte[][] array2(int length2, byte ... vals){
	if(vals==null) return null;
	int length1 = vals.length/length2;
	if(vals.length!=length1*length2){
	    IOut.err("length2 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array2(length1,length2,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static byte[][][] array3(int length1, int length2, int length3, byte ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2*length3){
	    IOut.err("length1*length2*length3 doesn't match with number of values");
	}
	byte[][][] array = new byte[length1][length2][length3];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		for(int k=0; k<length3 && idx<vals.length; k++)
		    array[i][j][k] = vals[idx++];
	return array;
    }
    */
    /** create 3D array of any class. */
    /*
    public static byte[][][] array3(int length2, int length3, byte ... vals){
	if(vals==null) return null;
	int length1 = vals.length/(length2*length3);
	if(vals.length!=length1*length2*length3){
	    IOut.err("length2*length3 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array3(length1,length2,length3,vals);
    }
    */
    /** create array of any class. */
    //public static byte[] arr(byte ... vals){ return array(vals); }
    /** create array of any class. */
    //public static byte[] arr(int length, byte ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static byte[][] arr2(int length1, int length2, byte ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static byte[][] arr2(int length2, byte ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static byte[][][] arr3(int length1, int length2, int length3, byte ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static byte[][][] arr3(int length2, int length3, byte ... vals){
	return array3(length2,length3,vals); 
    }
    */
    /** create array of any class. */
    //public static byte[] a(byte ... vals){ return array(vals); }
    /** create array of any class. */
    //public static byte[] a(int length, byte ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static byte[][] a2(int length1, int length2, byte ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static byte[][] a2(int length2, byte ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static byte[][][] a3(int length1, int length2, int length3, byte ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */

    /*
    public static byte[][][] a3(int length2, int length3, byte ... vals){
	return array3(length2,length3,vals); 
    }
    */

    
    
    /*********************************************************
     * char array 
     ********************************************************/
    /** create array of any class. */
    //public static char[] array(char ... vals){ return vals; }
    
    /** create array of any class. */
    /*
    public static char[] array(int length, char ... vals){
	if(vals==null) return null;
	if(vals.length!=length){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	char[] array = new char[length];
	int i=0;
	for(; i<length && i<vals.length; i++) array[i] = vals[i];
	return vals;
    }
    */
    /** create 2D array of any class. */
    /*
    public static char[][] array2(int length1, int length2, char ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	char[][] array = new char[length1][length2];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		array[i][j] = vals[idx++];
	return array;
    }
    */
    /** create 2D array of any class. */
    /*
    public static char[][] array2(int length2, char ... vals){
	if(vals==null) return null;
	int length1 = vals.length/length2;
	if(vals.length!=length1*length2){
	    IOut.err("length2 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array2(length1,length2,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static char[][][] array3(int length1, int length2, int length3, char ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2*length3){
	    IOut.err("length1*length2*length3 doesn't match with number of values");
	}
	char[][][] array = new char[length1][length2][length3];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		for(int k=0; k<length3 && idx<vals.length; k++)
		    array[i][j][k] = vals[idx++];
	return array;
    }
    */
    /** create 3D array of any class. */
    /*
    public static char[][][] array3(int length2, int length3, char ... vals){
	if(vals==null) return null;
	int length1 = vals.length/(length2*length3);
	if(vals.length!=length1*length2*length3){
	    IOut.err("length2*length3 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array3(length1,length2,length3,vals);
    }
    */
    /** create array of any class. */
    //public static char[] arr(char ... vals){ return array(vals); }
    /** create array of any class. */
    //public static char[] arr(int length, char ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static char[][] arr2(int length1, int length2, char ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static char[][] arr2(int length2, char ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static char[][][] arr3(int length1, int length2, int length3, char ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static char[][][] arr3(int length2, int length3, char ... vals){
	return array3(length2,length3,vals); 
    }
    */
    /** create array of any class. */
    //public static char[] a(char ... vals){ return array(vals); }
    /** create array of any class. */
    //public static char[] a(int length, char ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static char[][] a2(int length1, int length2, char ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static char[][] a2(int length2, char ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static char[][][] a3(int length1, int length2, int length3, char ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static char[][][] a3(int length2, int length3, char ... vals){
	return array3(length2,length3,vals); 
    }
    */
    
    
    /*********************************************************
     * boolean array 
     ********************************************************/
    /** create array of any class. */
    //public static boolean[] array(boolean ... vals){ return vals; }
    
    /** create array of any class. */
    /*
    public static boolean[] array(int length, boolean ... vals){
	if(vals==null) return null;
	if(vals.length!=length){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	boolean[] array = new boolean[length];
	int i=0;
	for(; i<length && i<vals.length; i++) array[i] = vals[i];
	return vals;
    }
    */
    
    /** create 2D array of any class. */
    /*
    public static boolean[][] array2(int length1, int length2, boolean ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2){
	    IOut.err("length1*length2 doesn't match with number of values");
	}
	boolean[][] array = new boolean[length1][length2];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		array[i][j] = vals[idx++];
	return array;
    }
    */
    
    /** create 2D array of any class. */
    /*
    public static boolean[][] array2(int length2, boolean ... vals){
	if(vals==null) return null;
	int length1 = vals.length/length2;
	if(vals.length!=length1*length2){
	    IOut.err("length2 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array2(length1,length2,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static boolean[][][] array3(int length1, int length2, int length3, boolean ... vals){
	if(vals==null) return null;
	if(vals.length!=length1*length2*length3){
	    IOut.err("length1*length2*length3 doesn't match with number of values");
	}
	boolean[][][] array = new boolean[length1][length2][length3];
	int idx=0;
	for(int i=0; i<length1 && idx<vals.length; i++)
	    for(int j=0; j<length2 && idx<vals.length; j++)
		for(int k=0; k<length3 && idx<vals.length; k++)
		    array[i][j][k] = vals[idx++];
	return array;
    }
    */
    /** create 3D array of any class. */
    /*
    public static boolean[][][] array3(int length2, int length3, boolean ... vals){
	if(vals==null) return null;
	int length1 = vals.length/(length2*length3);
	if(vals.length!=length1*length2*length3){
	    IOut.err("length2*length3 doesn't match with number of values");
	}
	if(length1==0) length1=1;
	return array3(length1,length2,length3,vals);
    }
    */
    /** create array of any class. */
    //public static boolean[] arr(boolean ... vals){ return array(vals); }
    /** create array of any class. */
    //public static boolean[] arr(int length, boolean ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static boolean[][] arr2(int length1, int length2, boolean ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static boolean[][] arr2(int length2, boolean ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static boolean[][][] arr3(int length1, int length2, int length3, boolean ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static boolean[][][] arr3(int length2, int length3, boolean ... vals){
	return array3(length2,length3,vals); 
    }
    */
    /** create array of any class. */
    //public static boolean[] a(boolean ... vals){ return array(vals); }
    /** create array of any class. */
    //public static boolean[] a(int length, boolean ... vals){ return array(length,vals); }
    /** create 2D array of any class. */
    /*
    public static boolean[][] a2(int length1, int length2, boolean ... vals){
	return array2(length1,length2,vals);
    }
    */
    /** create 2D array of any class. */
    //public static boolean[][] a2(int length2, boolean ... vals){ return array2(length2,vals); }
    /** create 3D array of any class. */
    /*
    public static boolean[][][] a3(int length1, int length2, int length3, boolean ... vals){
	return array3(length1,length2,length3,vals);
    }
    */
    /** create 3D array of any class. */
    /*
    public static boolean[][][] a3(int length2, int length3, boolean ... vals){
	return array3(length2,length3,vals); 
    }
    */
    
    public static boolean eq(double v1, double v2){ return Math.abs(v1-v2)<=IConfig.tolerance; } 
    public static boolean eq(double v1, double v2, double tolerance){ return Math.abs(v1-v2)<=tolerance; } 





    /*********************************************************
     * random number
     ********************************************************/
    
    public static void initRand(int seed){ IRand.init(seed); }
    public static void initRandByTime(){ IRand.initByTime(); }
    public static void initRand(){ initRandByTime(); }
    
    public static void seed(int seed){ initRand(seed); }
    public static void seedByTime(){ initRandByTime(); }
    public static void seedRand(){ initRandByTime(); }
    
    
    
    public static double rand(){ return IRand.get(); }
    public static double rand(double max){ return IRand.get(max); }
    public static double rand(double min, double max){ return IRand.get(min,max); }
    
    public static float rand(float max){ return IRand.getf(max); }
    public static float rand(float min, float max){ return IRand.getf(min,max); }
    
    public static int rand(int max){ return IRand.geti(max); }
    public static int rand(int min, int max){ return IRand.geti(min,max); }
    
    public static IVec randPt(){ return IRand.pt(); }
    public static IVec randPt(double max){ return IRand.pt(max); }
    public static IVec randPt(double min, double max){ return IRand.pt(min,max); }
    public static IVec randPt(double maxx,double maxy,double maxz){
	return IRand.pt(maxx,maxy,maxz);
    }
    public static IVec randPt(double minx, double miny, double maxx, double maxy){
	return IRand.pt(minx,miny,maxx,maxy);
    }
    public static IVec randPt(double minx, double miny, double minz,
			      double maxx, double maxy, double maxz){
	return IRand.pt(minx,miny,minz,maxx,maxy,maxz);
    }
    
    public static IVec randDir(){ return IRand.dir(); }
    public static IVec randDir(double len){ return IRand.dir(len); }
    static public IVec randDir(IVecI perpendicularAxis){ return IRand.dir(perpendicularAxis); }
    static public IVec randDir(IVecI perpendicularAxis, double length){ return IRand.dir(perpendicularAxis,length); }
    
    
    public static IColor randClr(){ return IRand.clr(); }
    public static IColor randClr(float alpha){ return IRand.clr(alpha); }
    public static IColor randClr(int alpha){ return IRand.clr(alpha); }
    
    public static IColor randColor(){ return randClr(); }
    public static IColor randColor(float alpha){ return randClr(alpha); }
    public static IColor randColor(int alpha){ return randClr(alpha); }
    
    public static IColor randGray(){ return IRand.gray(); }
    public static IColor randGray(float alpha){ return IRand.gray(alpha); }
    public static IColor randGray(int alpha){ return IRand.gray(alpha); }
    
    public static <T> T rand(T[] array){ return IRand.get(array); }
    public static <T> T rand(java.util.List<T> array){ return IRand.get(array); }
    
    public static boolean randPercent(double percent){ return pct(percent); }
    public static boolean randPct(double percent){ return pct(percent); }
    public static boolean percent(double percent){ return pct(percent); }
    public static boolean pct(double percent){ return IRand.pct(percent); }
    
}
