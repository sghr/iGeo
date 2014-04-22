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

import java.util.ArrayList;
import java.awt.*;
import igeo.gui.*;

/**
   A class of server to contain and manage instances of IObject.
   Drawing function is separated to IGraphicServer.
   
   @see IGraphicServer
   
   @author Satoru Sugihara
*/
public class IServer implements IServerI{
    
    public ArrayList<IObject> objects; // elements
    //ArrayList<IGraphicObject> graphics;
    //public ArrayList<IDynamicObject> dynamics;
    
    public ArrayList<ILayer> layers;
    
    public IGraphicServer graphicServer; // non null in graphic mode
    public IDynamicServer dynamicServer; // non null when dynamic subobject is used
    
    public IG ig; // parent
    //IPanelI panel; // non null in graphic mode
    
    // for updating logic of other object referring IServer
    public int stateCount=0; // incremented when any change happens in the state
    
    
    public IUnit unit;
    
    
    // non graphic mode
    public IServer(IG ig){
	this.ig =ig;
	objects = new ArrayList<IObject>();
	//graphics = new ArrayList<IGraphicObject>();
	//dynamics = new ArrayList<IDynamicObject>();
	//graphicServer = new IGraphicServer(this);
	dynamicServer = new IDynamicServer(this);
	layers = new ArrayList<ILayer>();
	
	unit = new IUnit(); // default
    }
    
    // graphic mode
    public IServer(IG ig, IPanelI panel){
	this.ig =ig;
	objects = new ArrayList<IObject>();
	//graphics = new ArrayList<IGraphicObject>();
	//dynamics = new ArrayList<IDynamicObject>();
	//graphicServer = new IGraphicServer(this);
	graphicServer = new IGraphicServer(this, panel);
	dynamicServer = new IDynamicServer(this);
	layers = new ArrayList<ILayer>();
	
	unit = new IUnit(); // default
    }
    
    public IServer server(){ return this; }
    public IDynamicServer dynamicServer(){ return dynamicServer; }
    
    public IGraphicServer graphicServer(){ return graphicServer; }
    
    
    public IUnit unit(){ return unit; }
    public void unit(IUnit u){ unit = u; }
    public void unit(IUnit.Type u){ unit = new IUnit(u); }
    public void unit(String unitName){ unit = new IUnit(unitName); }
    
    public void add(IObject e){
	//synchronized(IG.lock){
	synchronized(ig){
	    if(!objects.contains(e)) objects.add(e);
	    e.server = this;
	    if(e instanceof ILayer){ layers.add((ILayer)e); }
	    //if(isGraphicMode()) graphicServer.add(e);
	    if(e.dynamicsNum()>0){ dynamicServer.add(e); }
	    updateState();
	}
    }
    
    /*
    public void add(IGraphicObject e){
	synchronized(IG.lock){ graphics.add(e); }
    }
    */

    /*
    public void add(IDynamicObject e){
	synchronized(ig){
	    //dynamics.add(e);
	    //updateState();
	    dynamicServer.add(e);
	    //updateState(); // adding IObject matters but not IDynamicObject?
	}
    }
    */
    
    //public IPanelI getPanel(){ return panel; }
    
    public boolean isGraphicMode(){ return graphicServer!=null; }
    
    public void setGraphicMode(IGraphicMode mode){
	if(graphicServer!=null) graphicServer.setMode(mode);
	else{ IOut.err("graphicServer is null"); }
    }
    
    public void bg(IColor c1, IColor c2, IColor c3, IColor c4){
	if(graphicServer!=null) graphicServer.background(c1,c2,c3,c4);
	else{ IOut.err("graphicServer is null"); }
    }
    public void background(IColor c1, IColor c2, IColor c3, IColor c4){ bg(c1,c2,c3,c4); }

    /*
    public void bg(Image img){
	if(graphicServer!=null) graphicServer.background(img);
	else{ IOut.err("graphicServer is null"); }
    }
    public void background(Image img){ bg(img); }
    */
    public void bg(String imageFilename){
	if(graphicServer!=null) graphicServer.background(imageFilename);
	else{ IOut.err("graphicServer is null"); }
    }
    public void background(String imageFilename){ bg(imageFilename); }
    
    
    public ArrayList<IObject> getAllObjects(){ return allObjects(); }
    public ArrayList<IObject> allObjects(){ return objects; }
    
    //public IObject getObject(int i){ return objects.get(i); }
    public IObject getObject(int i){ return object(i); }
    public IObject object(int i){
	if(i<0||i>=objects.size()) return null;
	return objects.get(i);
    }
    //public IGraphicObject getGraphicObject(int i){ return graphics.get(i); }
    //public IDynamicObject getDynamicObject(int i){ return dynamics.get(i); }
    //public IDynamicObject getDynamicObject(int i){ return dynamicObject(i); }
    //public IDynamicObject dynamicObject(int i){ if(i<0||i>=dynamics.size()){ return null; } return dynamics.get(i); }
    
    public int objectNum(){ return objects.size(); }
    /** alias of objectNum() */
    public int getObjectNum(){ return objectNum(); }
    //public int graphicObjectNum(){ return graphics.size(); }
    //public int dynamicObjectNum(){ return dynamics.size(); }
    
    
    
    public void remove(int i){
	if(i<0||i>=objects.size()) return;
	//synchronized(IG.lock){
	synchronized(ig){
	    if(graphicServer!=null && objects.get(i).graphics!=null){
		IObject e = objects.get(i);
		for(int j=0; j<e.graphics.size(); j++) graphicServer.remove(e.graphics.get(j));
	    }
	    if(objects.get(i).dynamics!=null){
		IObject e = objects.get(i);
		for(int j=0; j<e.dynamics.size(); j++) dynamicServer.remove(e.dynamics.get(j)); //removeDynamicObject(e.dynamics.get(j));
	    }
	    objects.remove(i);
	    updateState();
	}
    }
    
    public void remove(IObject e){
	//synchronized(IG.lock){
	synchronized(ig){
	    if(graphicServer!=null && e.graphics!=null){
		for(int j=0; j<e.graphics.size(); j++) graphicServer.remove(e.graphics.get(j));
	    }
	    if(e.dynamics!=null){
		for(int j=0; j<e.dynamics.size(); j++) dynamicServer.remove(e.dynamics.get(j)); //removeDynamicObject(e.dynamics.get(j));
	    }
	    if(e.attribute!=null && e.attribute.layer!=null){
		e.attribute.layer.remove(e); // 20111217
	    }
	    objects.remove(e);
	    if(e instanceof ILayer){ layers.remove(e); }
	    updateState();
	}
    }
    
    
    //public void removeGraphicObject(int i){ graphics.remove(i); }
    //public void removeGraphicObject(IGraphicObject g){ graphics.remove(g); }
    /*
    public void removeGraphicObject(IGraphicObject g){
	if(graphicServer!=null) graphicServer.remove(g);
    }
    */
    /*
    public void removeDynamicObject(int i){
	dynamics.remove(i);
	updateState();
    }
    public void removeDynamicObject(IDynamicObject d){
	dynamics.remove(d);
	updateState();
    }
    */
    
    //public void getGraphic(IGraphicObject e, IView v){ graphicServer.add(e,v); }
    
    //public void delete(){ objects.clear(); }
    public void clear(){
	objects.clear();
	//dynamics.clear();
	if(graphicServer!=null) graphicServer.clearObjects();
	if(dynamicServer!=null) dynamicServer.clear();
	layers.clear();
	updateState(); // not clearing the number
    }
    
    public int stateCount(){ return stateCount; }
    public void updateState(){ stateCount++; }
    
    /*
    public void setGraphicMode(IGraphicMode mode){
	//for(IObject e:objects) e.setGraphicMode(mode);
	graphicServer.setGraphicMode(mode);
    }
    */
    /*
    public void draw(IGraphics g){
	for(int i=0; i<graphics.size(); i++){
	    graphics.get(i).draw(g);
	}
    }
    */
    
    
    public void duration(int dur){ dynamicServer.duration(dur); }
    public int duration(){ return dynamicServer.duration(); }
    
    public void time(int tm){ dynamicServer.time(tm); }
    public int time(){ return dynamicServer.time(); }
    
    public void pause(){ dynamicServer.pause(); }
    public void resume(){ dynamicServer.resume(); }
    public boolean isRunning(){ return dynamicServer.isRunning(); }
    
    public void start(){ dynamicServer.start(); }
    public void stop(){ dynamicServer.stop(); }
    
    
    /***********************************************************************
     * Object Selection
     **********************************************************************/
    
    /** Returns all point objects contained in objects.
	IPointR objects are not included.
    */
    public IPoint[] points(){
	ArrayList<IPoint> points = new ArrayList<IPoint>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IPoint)
		    points.add((IPoint)objects.get(i));
	}
	return points.toArray(new IPoint[points.size()]);
    }
    /** alias of points() */
    public IPoint[] getPoints(){ return points(); }
    
    /** Returns all curve objects contained in objects.
	ICurveR objects are not included.
    */
    public ICurve[] curves(){
	ArrayList<ICurve> curves = new ArrayList<ICurve>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof ICurve)
		    curves.add((ICurve)objects.get(i));
	}
	return curves.toArray(new ICurve[curves.size()]);
    }
    /** alias of curves() */
    public ICurve[] getCurves(){ return curves(); }
    
    
    /** Returns all polycurve objects contained in objects.
	Note that IPolycurve contains multiple ICurve and they show up in curves() as well.
	ICurve - IPolycurve relationship is still under work. This is temporary measure.
    */
    public IPolycurve[] polycurves(){
	ArrayList<IPolycurve> polycurves = new ArrayList<IPolycurve>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IPolycurve)
		    polycurves.add((IPolycurve)objects.get(i));
	}
	return polycurves.toArray(new IPolycurve[polycurves.size()]);
    }
    /** alias of curves() */
    public IPolycurve[] getPolycurves(){ return polycurves(); }
    
    
    /** Returns all surface objects contained in objects.
	ISurfaceR objects are not included.
    */
    public ISurface[] surfaces(){
	ArrayList<ISurface> surfaces = new ArrayList<ISurface>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof ISurface)
		    surfaces.add((ISurface)objects.get(i));
	}
	return surfaces.toArray(new ISurface[surfaces.size()]);
    }
    /** alias of surfaces() */
    public ISurface[] getSurfaces(){ return surfaces(); }
    
    /** Returns all meshe objects contained in objects.
	IMeshR objects are not included.
    */
    public IMesh[] meshes(){
	ArrayList<IMesh> meshes = new ArrayList<IMesh>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IMesh)
		    meshes.add((IMesh)objects.get(i));
	}
	return meshes.toArray(new IMesh[meshes.size()]);
    }
    /** alias of meshes() */
    public IMesh[] getMeshes(){ return meshes(); }
    
    /** Returns all brep objects contained in objects. */
    public IBrep[] breps(){
	ArrayList<IBrep> breps = new ArrayList<IBrep>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IBrep)
		    breps.add((IBrep)objects.get(i));
	}
	return breps.toArray(new IBrep[breps.size()]);
    }
    /** alias of breps */
    public IBrep[] getBreps(){ return breps(); }
    
    /** Returns all text objects contained in objects. */
    public IText[] texts(){
	ArrayList<IText> texts = new ArrayList<IText>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IText)
		    texts.add((IText)objects.get(i));
	}
	return texts.toArray(new IText[texts.size()]);
    }
    /** alias of texts */
    public IText[] getTexts(){ return texts(); }
    
    /** Returns all geometry objects contained in objects. */
    public IGeometry[] geometries(){
	ArrayList<IGeometry> geoms = new ArrayList<IGeometry>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IGeometry)
		    geoms.add((IGeometry)objects.get(i));
	}
	return geoms.toArray(new IGeometry[geoms.size()]);
    }
    /** alias of breps */
    public IGeometry[] getGeometries(){ return breps(); }
    
    /** Returns all objects of specified class contained in objects.
     */
    public IObject[] objects(Class cls){
	ArrayList<IObject> objs = new ArrayList<IObject>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(cls.isInstance(objects.get(i))) objs.add(objects.get(i));
	}
	return objs.toArray(new IObject[objs.size()]);
    }
    /** alias of objects(Class) */
    public IObject[] getObjects(Class cls){ return objects(cls); }
    
    /** Returns all objects contained in objects.
     */
    public IObject[] objects(){ return objects.toArray(new IObject[objects.size()]); }
    public IObject[] getObjects(){ return objects(); }
    
    
    /** Returns i-th IPoint object contained in objects or null if not found.
	IPointR objects are not included.
    */
    public IPoint point(int i){
	int curIdx=0;
	synchronized(ig){
	    for(int j=0; j<objects.size(); j++)
		if(objects.get(j) instanceof IPoint)
		    if(i==curIdx++) return (IPoint)objects.get(j);
	}
	return null;
    }
    /** alias of point(int) */
    public IPoint getPoint(int i){ return point(i); }
    
    
    /** Returns i-th ICurve object contained in objects or null if not found.
	ICurveR objects are not included.
    */
    public ICurve curve(int i){
	int curIdx=0;
	synchronized(ig){
	    for(int j=0; j<objects.size(); j++)
		if(objects.get(j) instanceof ICurve)
		    if(i==curIdx++) return (ICurve)objects.get(j);
	}
	return null;
    }
    /** alias of curve(int) */
    public ICurve getCurve(int i){ return curve(i); }
    
    
    /** Returns i-th IPolycurve object contained in objects or null if not found.
	Note that IPolycurve contains multiple ICurve and they show up in curves() as well.
	ICurve - IPolycurve relationship is still under work. This is temporary measure.
    */
    public IPolycurve polycurve(int i){
	int curIdx=0;
	synchronized(ig){
	    for(int j=0; j<objects.size(); j++)
		if(objects.get(j) instanceof IPolycurve)
		    if(i==curIdx++) return (IPolycurve)objects.get(j);
	}
	return null;
    }
    /** alias of polycurve(int) */
    public IPolycurve getPolycurve(int i){ return polycurve(i); }
    
    
    /** Returns i-th ISurface object contained in objects or null if not found.
	ISurfaceR objects are not included.
    */
    public ISurface surface(int i){
	int curIdx=0;
	synchronized(ig){
	    for(int j=0; j<objects.size(); j++)
		if(objects.get(j) instanceof ISurface)
		    if(i==curIdx++) return (ISurface)objects.get(j);
	}
	return null;
    }
    /** alias of surface(int) */
    public ISurface getSurface(int i){ return surface(i); }
    
    
    /** Returns i-th IMesh object contained in objects or null if not found.
	IMeshR objects are not included.
    */
    public IMesh mesh(int i){
	int curIdx=0;
	synchronized(ig){
	    for(int j=0; j<objects.size(); j++)
		if(objects.get(j) instanceof IMesh)
		    if(i==curIdx++) return (IMesh)objects.get(j);
	}
	return null;
    }
    /** alias of mesh(int) */
    public IMesh getMesh(int i){ return mesh(i); }
    
    /** Returns i-th IBrep object contained in objects or null if not found.
    */
    public IBrep brep(int i){
	int curIdx=0;
	synchronized(ig){
	    for(int j=0; j<objects.size(); j++)
		if(objects.get(j) instanceof IBrep)
		    if(i==curIdx++) return (IBrep)objects.get(j);
	}
	return null;
    }
    /** alias of brep(int) */
    public IBrep getBrep(int i){ return brep(i); }
    
    /** Returns i-th IText object contained in objects or null if not found.
     */
    public IText text(int i){
	int curIdx=0;
	synchronized(ig){
	    for(int j=0; j<objects.size(); j++)
		if(objects.get(j) instanceof IText)
		    if(i==curIdx++) return (IText)objects.get(j);
	}
	return null;
    }
    /** alias of text(int) */
    public IText getText(int i){ return text(i); }
    
    
    /** Returns i-th IGeometry object contained in objects or null if not found.
    */
    public IGeometry geometry(int i){
	int curIdx=0;
	synchronized(ig){
	    for(int j=0; j<objects.size(); j++)
		if(objects.get(j) instanceof IGeometry)
		    if(i==curIdx++) return (IGeometry)objects.get(j);
	}
	return null;
    }
    /** alias of geometry(int) */
    public IGeometry getGeometry(int i){ return geometry(i); }
    
    /** Returns i-th object contained in objects or null if not found.
    */
    public IObject object(Class cls, int i){
	int curIdx=0;
	synchronized(ig){
	    for(int j=0; j<objects.size(); j++)
		if(cls.isInstance(objects.get(j))) 
		    if(i==curIdx++) return objects.get(j);
	}
	return null;
    }
    /** alias of object(Class,int) */
    public IObject getObject(Class cls, int i){ return object(cls,i); }
    
    
    
    /** number of IPoint in objects */
    public int pointNum(){
	int num=0;
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IPoint) num++;
	}
	return num;
    }
    /** alias of pointsNum() */
    public int getPointNum(){ return pointNum(); }
    
    /** number of ICurve in objects */
    public int curveNum(){
	int num=0;
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof ICurve) num++;
	}
	return num;
    }
    /** alias of curveNum() */
    public int getCurveNum(){ return curveNum(); }
    
    /** number of IPolycurve in objects 
	Note that IPolycurve contains multiple ICurve and they show up in curves() as well.
	ICurve - IPolycurve relationship is still under work. This is temporary measure.
    */
    public int polycurveNum(){
	int num=0;
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IPolycurve) num++;
	}
	return num;
    }
    /** alias of polycurveNum() */
    public int getPolycurveNum(){ return polycurveNum(); }
    
    /** number of ISurface in objects */
    public int surfaceNum(){
	int num=0;
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof ISurface) num++;
	}
	return num;
    }
    /** alias of surfaceNum() */
    public int getSurfaceNum(){ return surfaceNum(); }
    
    
    /** number of IMesh in objects */
    public int meshNum(){
	int num=0;
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IMesh) num++;
	}
	return num;
    }
    /** alias of meshNum() */
    public int getMeshNum(){ return meshNum(); }
    
    
    /** number of IBrep in objects */
    public int brepNum(){
	int num=0;
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IBrep) num++;
	}
	return num;
    }
    /** alias of brepNum() */
    public int getBrepNum(){ return brepNum(); }
    
    /** number of IText in objects */
    public int textNum(){
	int num=0;
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IText) num++;
	}
	return num;
    }
    /** alias of textNum() */
    public int getTextNum(){ return textNum(); }
    
    /** number of the IGeometry in objects */
    public int geometryNum(){
	int num=0;
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IGeometry) num++;
	}
	return num;
    }
    /** alias of geometryNum() */
    public int getGeometryNum(){ return geometryNum(); }
    
    /** number of the specified class in objects */
    public int objectNum(Class cls){
	int num=0;
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(cls.isInstance(objects.get(i))) num++;
	}
	return num;
    }
    /** alias of objectNum(Class) */
    public int getObjectNum(Class cls){ return objectNum(cls); }
    
    
    
    public int layerNum(){ return layers.size(); }

    public ILayer layer(int i){ return layers.get(i); }
    public ILayer getLayer(int i){ return layer(i); }

    public ILayer[] layers(){ return layers.toArray(new ILayer[layers.size()]); }
    public ILayer[] getAllLayers(){ return layers(); }

    public ILayer getLayer(String layerName){ return layer(layerName); }
    public ILayer layer(String layerName){
	//for(ILayer l:layers) if(l.name().equals(layerName)) return l;
	for(int i=0; i<layers.size(); i++){
	    if(layers.get(i).name().equals(layerName)) return layers.get(i);
	}
	//return null;
	// if not found, create a new one.
	//updateState();
	return new ILayer(this, layerName);
	//add(l); // layer is added in constractor of IObject
    }
    
    public void deleteLayer(String layerName){
	ILayer l=null;
	for(int i=0; i<layers.size() && l==null; i++){
	    if(layers.get(i).name().equals(layerName)) l = layers.get(i);
	}
	if(l!=null) remove(l);
	//for(ILayer l:layers) if(l.name().equals(layerName)) remove(l);
	// if not found, do nothing
    }
    public void deleteLayer(int i){ remove(layers.get(i)); }
    public void deleteLayer(ILayer l){ remove(l); }
    
}
