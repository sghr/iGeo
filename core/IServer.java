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

import java.util.ArrayList;
import java.awt.Color;
import igeo.gui.*;
import igeo.geo.*;

/**
   A class of server to contain and manage instances of IObject.
   Drawing function is separated to IGraphicServer.
   
   @see IGraphicServer
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IServer implements IServerI{
    
    public ArrayList<IObject> objects; // elements
    //ArrayList<IGraphicObject> graphics;
    //public ArrayList<IDynamicObject> dynamics;
    
    public ArrayList<ILayer> layers;
    
    public IGraphicServer graphicServer; // non null in graphic mode
    public IDynamicServer dynamicServer; // non null when dynamic subobject is used
    
    public IG ig; // parent
    //IPanel panel; // non null in graphic mode
    
    // for updating logic of other object referring IServer
    public int statusCount=0; // incremented when any change happens in the state
    
    
    // non graphic mode
    public IServer(IG ig){
	this.ig =ig;
	objects = new ArrayList<IObject>();
	//graphics = new ArrayList<IGraphicObject>();
	//dynamics = new ArrayList<IDynamicObject>();
	//graphicServer = new IGraphicServer(this);
	dynamicServer = new IDynamicServer(this);
	layers = new ArrayList<ILayer>();
    }
    
    // graphic mode
    public IServer(IG ig, IPanel panel){
	this.ig =ig;
	objects = new ArrayList<IObject>();
	//graphics = new ArrayList<IGraphicObject>();
	//dynamics = new ArrayList<IDynamicObject>();
	//graphicServer = new IGraphicServer(this);
	graphicServer = new IGraphicServer(this, panel);
	dynamicServer = new IDynamicServer(this);
	layers = new ArrayList<ILayer>();
    }
    
    public IServer server(){ return this; }
    public IDynamicServer dynamicServer(){ return dynamicServer; }
    
    public IGraphicServer graphicServer(){ return graphicServer; }
    
    public void add(IObject e){
	//synchronized(IG.lock){
	synchronized(ig){
	    if(!objects.contains(e)) objects.add(e);
	    e.server = this;
	    if(e instanceof ILayer){ layers.add((ILayer)e); }
	    //if(isGraphicMode()) graphicServer.add(e);
	    if(e.dynamicsNum()>0){ dynamicServer.add(e); }
	    update();
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
	    //update();
	    dynamicServer.add(e);
	    //update(); // adding IObject matters but not IDynamicObject?
	}
    }
    */
    
    //public IPanel getPanel(){ return panel; }
    
    public boolean isGraphicMode(){ return graphicServer!=null; }
    
    public void setGraphicMode(IGraphicMode mode){
	if(graphicServer!=null) graphicServer.setMode(mode);
	else{ IOut.err("graphicServer is null"); }
    }
    
    public void bg(Color c1, Color c2, Color c3, Color c4){
	if(graphicServer!=null) graphicServer.background(c1,c2,c3,c4);
	else{ IOut.err("graphicServer is null"); }
    }
    public void background(Color c1, Color c2, Color c3, Color c4){ bg(c1,c2,c3,c4); }
    
        
    public ArrayList<IObject> getObjects(){ return objects(); }
    public ArrayList<IObject> objects(){ return objects; }
    
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
	    update();
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
	    objects.remove(e);
	    if(e instanceof ILayer){ layers.remove(e); }
	    update();
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
	update();
    }
    public void removeDynamicObject(IDynamicObject d){
	dynamics.remove(d);
	update();
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
	update(); // not clearing the number
    }
    
    public int statusCount(){ return statusCount; }
    public void update(){ statusCount++; }
    
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
    
    
    
    
    /***********************************************************************
     * Object Selection
     **********************************************************************/
    
    /** Returns all point objects contained in objects.
	IPointR objects are not included.
    */
    public IPoint[] getPoints(){
	ArrayList<IPoint> points = new ArrayList<IPoint>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IPoint)
		    points.add((IPoint)objects.get(i));
	}
	return points.toArray(new IPoint[points.size()]);
    }
    
    /** Returns all curve objects contained in objects.
	ICurveR objects are not included.
    */
    public ICurve[] getCurves(){
	ArrayList<ICurve> curves = new ArrayList<ICurve>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof ICurve)
		    curves.add((ICurve)objects.get(i));
	}
	return curves.toArray(new ICurve[curves.size()]);
    }
    
    /** Returns all surface objects contained in objects.
	ISurfaceR objects are not included.
    */
    public ISurface[] getSurfaces(){
	ArrayList<ISurface> surfaces = new ArrayList<ISurface>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof ISurface)
		    surfaces.add((ISurface)objects.get(i));
	}
	return surfaces.toArray(new ISurface[surfaces.size()]);
    }
    
    /** Returns all meshe objects contained in objects.
	IMeshR objects are not included.
    */
    public IMesh[] getMeshes(){
	ArrayList<IMesh> meshes = new ArrayList<IMesh>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(objects.get(i) instanceof IMesh)
		    meshes.add((IMesh)objects.get(i));
	}
	return meshes.toArray(new IMesh[meshes.size()]);
    }
    
    
    /** Returns all objects of specified class contained in objects.
     */
    public IObject[] getObjects(Class cls){
	ArrayList<IObject> objs = new ArrayList<IObject>();
	synchronized(ig){
	    for(int i=0; i<objects.size(); i++)
		if(cls.isInstance(objects.get(i))) objs.add(objects.get(i));
	}
	return objs.toArray(new IObject[objs.size()]);
    }

    /** Returns all objects contained in objects.
     */
    public IObject[] getAllObjects(){ return objects.toArray(new IObject[objects.size()]); }
    
    
    public int layerNum(){ return layers.size(); }

    public ILayer layer(int i){ return layers.get(i); }
    public ILayer getLayer(int i){ return layer(i); }

    public ILayer[] layers(){ return layers.toArray(new ILayer[layers.size()]); }
    public ILayer[] getAllLayers(){ return layers(); }

    public ILayer getLayer(String layerName){ return layer(layerName); }
    public ILayer layer(String layerName){
	for(ILayer l:layers) if(l.name().equals(layerName)) return l;
	//return null;
	// if not found, create a new one.
	//update();
	return new ILayer(this, layerName);
	//add(l); // layer is added in constractor of IObject
    }
    
    public void removeLayer(String layerName){
	for(ILayer l:layers) if(l.name().equals(layerName)) remove(l);
    }
    
}
