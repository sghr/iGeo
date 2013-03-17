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
import java.awt.Color;

/**
   A class of a layer to contain and organize objects. Layers are managed by IServer.

   @see IServer
   
   @author Satoru Sugihara
*/
public class ILayer extends IObject{
    
    public ArrayList<IObject> objects;
    //public ILayer parentLayer = null;
    //public Color color;
    //public IMaterial material;
    //public boolean visible=true;
    
    public ILayer(){
	attribute = new IAttribute();
	objects = new ArrayList<IObject>();
    }
    public ILayer(String name){ this(); attribute.name = name; }
    public ILayer(IServerI s){
	super(s);
	attribute = new IAttribute();
	objects = new ArrayList<IObject>();
    }
    public ILayer(IServerI s, String name){ this(s); attribute.name = name; }
    
    
    public int num(){ return objects.size(); }
    public IObject get(int i){ return objects.get(i); }
    public synchronized ILayer add(IObject e){
	if(!objects.contains(e)){
	    objects.add(e);
	    if(e.layer()!=this) e.layer(this);
	    //e.layer = this;
	    // if e is ILayer, e.layer means parent layer
	}
	//if(e instanceof ILayer){ ((ILayer)e).parentLayer = this; }
	return this;
    }
    public synchronized ILayer remove(int i){ objects.remove(i); return this; }
    public synchronized ILayer remove(IObject e){ objects.remove(e); return this; }
    
    public boolean contains(IObject e){ return objects.contains(e); }
    
    public ArrayList<IObject> allObjects(){ return objects; }
    public ArrayList<IObject> getAllObjects(){ return allObjects(); }
    
    /***********************************************************************
     * Object Selection
     **********************************************************************/
    
    /** Returns all point objects contained in a layer.
        IPointR objects are not included.
     */
    public IPoint[] points(){
        ArrayList<IPoint> points = new ArrayList<IPoint>();
        synchronized(server){
            for(int i=0; i<objects.size(); i++)
                if(objects.get(i) instanceof IPoint)
                    points.add((IPoint)objects.get(i));
        }
        return points.toArray(new IPoint[points.size()]);
    }
    /** alias of points() */
    public IPoint[] getPoints(){ return points(); }
    /** alias of points() */
    public IPoint[] pts(){ return points(); }
    
    
    /** Returns all curve objects contained in a layer.
        ICurveR objects are not included.
    */
    public ICurve[] curves(){
        ArrayList<ICurve> curves = new ArrayList<ICurve>();
        synchronized(server){
            for(int i=0; i<objects.size(); i++)
                if(objects.get(i) instanceof ICurve)
                    curves.add((ICurve)objects.get(i));
        }
        return curves.toArray(new ICurve[curves.size()]);
    }
    /** alias of curves() */
    public ICurve[] getCurves(){ return curves(); }
    /** alias of curves() */
    public ICurve[] crvs(){ return curves(); }
    
    /** Returns all surface objects contained in a layer.
        ISurfaceR objects are not included.
    */
    public ISurface[] surfaces(){
        ArrayList<ISurface> surfaces = new ArrayList<ISurface>();
        synchronized(server){
            for(int i=0; i<objects.size(); i++)
                if(objects.get(i) instanceof ISurface)
                    surfaces.add((ISurface)objects.get(i));
        }
        return surfaces.toArray(new ISurface[surfaces.size()]);
    }
    /** alias of surfaces() */
    public ISurface[] getSurfaces(){ return surfaces(); }
    /** alias of surfaces() */
    public ISurface[] srfs(){ return surfaces(); }
    
    
    /** Returns all mesh objects contained in a layer.
        IMeshR objects are not included.
    */
    public IMesh[] meshes(){
        ArrayList<IMesh> meshes = new ArrayList<IMesh>();
        synchronized(server){
            for(int i=0; i<objects.size(); i++)
                if(objects.get(i) instanceof IMesh)
                    meshes.add((IMesh)objects.get(i));
        }
        return meshes.toArray(new IMesh[meshes.size()]);
    }
    /** alias of meshes() */
    public IMesh[] getMeshes(){ return meshes(); }
    
    /** Returns all brep objects contained in a layer.
     */
    public IBrep[] breps(){
        ArrayList<IBrep> breps = new ArrayList<IBrep>();
        synchronized(server){
            for(int i=0; i<objects.size(); i++)
                if(objects.get(i) instanceof IBrep)
                    breps.add((IBrep)objects.get(i));
        }
        return breps.toArray(new IBrep[breps.size()]);
    }
    /** alias of breps() */
    public IBrep[] getBreps(){ return breps(); }
    
    /** Returns all brep objects contained in a layer.
     */
    public IGeometry[] geometries(){
        ArrayList<IGeometry> geos = new ArrayList<IGeometry>();
        synchronized(server){
            for(int i=0; i<objects.size(); i++)
                if(objects.get(i) instanceof IGeometry)
                    geos.add((IGeometry)objects.get(i));
        }
        return geos.toArray(new IGeometry[geos.size()]);
    }
    /** alias of geometries() */
    public IGeometry[] getGeometries(){ return geometries(); }
    /** alias of geometries() */
    public IGeometry[] geos(){ return geometries(); }
    
    /** Returns all objects of specified class contained in a layer.
     */
    public IObject[] objects(Class cls){
        ArrayList<IObject> objs = new ArrayList<IObject>();
        synchronized(server){
            for(int i=0; i<objs.size(); i++)
                if(cls.isInstance(objects.get(i))) objs.add(objects.get(i));
        }
        return objs.toArray(new IObject[objs.size()]);
    }
    /** alias of objects(Class) */
    public IObject[] getObjects(Class cls){ return objects(cls); }
    /** alias of objects(Class) */
    public IObject[] objs(Class cls){ return objects(cls); }
    
    /** Returns all objects contained in a layer.
     */
    public IObject[] objects(){
	return objects.toArray(new IObject[objects.size()]);
    }
    /** alias of objects() */
    public IObject[] getObjects(){ return objects(); }
    /** alias of objects() */
    public IObject[] objs(){ return objects(); }
    
    
    
    /** Returns i-th IPoint object contained in objects or null if not found.
        IPointR objects are not included.
    */
    public synchronized IPoint point(int i){
        int curIdx=0;
	for(int j=0; j<objects.size(); j++)
	    if(objects.get(j) instanceof IPoint)
		if(i==curIdx++) return (IPoint)objects.get(j);
        return null;
    }
    /** alias of point(int) */
    public IPoint getPoint(int i){ return point(i); }
    /** alias of point(int) */
    public IPoint pt(int i){ return point(i); }
    
    
    /** Returns i-th ICurve object contained in objects or null if not found.
        ICurveR objects are not included.
    */
    public synchronized ICurve curve(int i){
        int curIdx=0;
	for(int j=0; j<objects.size(); j++)
	    if(objects.get(j) instanceof ICurve)
		if(i==curIdx++) return (ICurve)objects.get(j);
        return null;
    }
    /** alias of curve(int) */
    public ICurve getCurve(int i){ return curve(i); }
    /** alias of curve(int) */
    public ICurve crv(int i){ return curve(i); }
    
    /** Returns i-th ISurface object contained in objects or null if not found.
        ISurfaceR objects are not included.
    */
    public synchronized ISurface surface(int i){
        int curIdx=0;
	for(int j=0; j<objects.size(); j++)
	    if(objects.get(j) instanceof ISurface)
		if(i==curIdx++) return (ISurface)objects.get(j);
	return null;
    }
    /** alias of surface(int) */
    public ISurface getSurface(int i){ return surface(i); }
    /** alias of surface(int) */
    public ISurface srf(int i){ return surface(i); }
    
    
    /** Returns i-th IMesh object contained in objects or null if not found.
        IMeshR objects are not included.
    */
    public synchronized IMesh mesh(int i){
        int curIdx=0;
	for(int j=0; j<objects.size(); j++)
	    if(objects.get(j) instanceof IMesh)
		if(i==curIdx++) return (IMesh)objects.get(j);
        return null;
    }
    /** alias of mesh(int) */
    public IMesh getMesh(int i){ return mesh(i); }
    
    /** Returns i-th IBrep object contained in objects or null if not found.
    */
    public synchronized IBrep brep(int i){
        int curIdx=0;
	for(int j=0; j<objects.size(); j++)
	    if(objects.get(j) instanceof IBrep)
		if(i==curIdx++) return (IBrep)objects.get(j);
        return null;
    }
    /** alias of brep(int) */
    public IBrep getBrep(int i){ return brep(i); }
    
    /** Returns i-th IGeometries object contained in objects or null if not found.
    */
    public synchronized IGeometry geometry(int i){
        int curIdx=0;
	for(int j=0; j<objects.size(); j++)
	    if(objects.get(j) instanceof IGeometry)
		if(i==curIdx++) return (IGeometry)objects.get(j);
        return null;
    }
    /** alias of geometry(int) */
    public IGeometry getGeometry(int i){ return geometry(i); }
    public IGeometry geo(int i){ return geometry(i); }
    
    /** Returns i-th object contained in objects or null if not found.
    */
    public synchronized IObject object(Class cls, int i){
        int curIdx=0;
	for(int j=0; j<objects.size(); j++)
	    if(cls.isInstance(objects.get(j))) 
		if(i==curIdx++) return objects.get(j);
        return null;
    }
    /** alias of object(Class,int) */
    public IObject getObject(Class cls, int i){ return object(cls,i); }
    /** alias of object(Class,int) */
    public IObject obj(Class cls, int i){ return object(cls,i); }
    
    /** Returns i-th object contained in objects or null if not found.
    */
    public synchronized IObject object(int i){
	return i<0||i>=objects.size()?null:objects.get(i);
    }
    /** alias of object(Class,int) */
    public IObject getObject(int i){ return object(i); }
    /** alias of object(Class,int) */
    public IObject obj(int i){ return object(i); }
    
    
    
    /** number of IPoint in objects */
    public synchronized int pointNum(){
        int num=0;
	for(int i=0; i<objects.size(); i++)
	    if(objects.get(i) instanceof IPoint) num++;
        return num;
    }
    /** alias of pointsNum() */
    public int getPointNum(){ return pointNum(); }
    /** alias of pointsNum() */
    public int ptNum(){ return pointNum(); }
    
    /** number of ICurve in objects */
    public synchronized int curveNum(){
        int num=0;
	for(int i=0; i<objects.size(); i++)
	    if(objects.get(i) instanceof ICurve) num++;
        return num;
    }
    /** alias of curveNum() */
    public int getCurveNum(){ return curveNum(); }
    /** alias of curveNum() */
    public int crvNum(){ return curveNum(); }
    
    
    /** number of ISurface in objects */
    public synchronized int surfaceNum(){
        int num=0;
	for(int i=0; i<objects.size(); i++)
	    if(objects.get(i) instanceof ISurface) num++;
        return num;
    }
    /** alias of surfaceNum() */
    public int getSurfaceNum(){ return surfaceNum(); }
    /** alias of surfaceNum() */
    public int srfNum(){ return surfaceNum(); }
    
    /** number of IMesh in objects */
    public synchronized int meshNum(){
        int num=0;
	for(int i=0; i<objects.size(); i++)
	    if(objects.get(i) instanceof IMesh) num++;
        return num;
    }
    /** alias of meshNum() */
    public int getMeshNum(){ return meshNum(); }
    
    /** number of IBrep in objects */
    public synchronized int brepNum(){
        int num=0;
	for(int i=0; i<objects.size(); i++)
	    if(objects.get(i) instanceof IBrep) num++;
        return num;
    }
    /** alias of brepNum() */
    public int getBrepNum(){ return brepNum(); }
    
    /** number of IBrep in objects */
    public synchronized int geometryNum(){
        int num=0;
	for(int i=0; i<objects.size(); i++)
	    if(objects.get(i) instanceof IGeometry) num++;
        return num;
    }
    /** alias of geometryNum() */
    public int getGeometryNum(){ return geometryNum(); }
    /** alias of geometryNum() */
    public int geoNum(){ return geometryNum(); }
    
    /** number of the specified class in objects */
    public synchronized int objectNum(Class cls){
        int num=0;
	for(int i=0; i<objects.size(); i++)
	    if(cls.isInstance(objects.get(i))) num++;
        return num;
    }
    /** alias of objectNum(Class) */
    public int getObjectNum(Class cls){ return objectNum(cls); }
    /** alias of objectNum(Class) */
    public int objNum(Class cls){ return objectNum(cls); }
    
    /** number of the specified class in objects */
    public synchronized int objectNum(){ return objects.size(); }
    /** alias of objectNum() */
    public int getObjectNum(){ return objectNum(); }
    /** alias of objectNum() */
    public int objNum(){ return objectNum(); }
    
    
    
    public ILayer name(String layerName){ attribute.name = layerName; return this; }
    
    @Override public boolean visible(){ return attribute.visible(); }
    
    public ILayer setVisible(boolean v){ return visible(v); }
    public ILayer visible(boolean v){
	attribute.visible(v);
	if(attribute.visible()) show(); else hide();
	return this;
    }
    public ILayer hide(){
	super.hide();
	attribute.hide();
	for(IObject o: objects) o.hide();
	return this;
    }
    public ILayer show(){
	super.show();
	attribute.show();
	for(IObject o: objects) o.show();
	return this;
    }
    
    
    public IColor clr(){ return attribute.clr(); }
    public Color color(){ return attribute.color(); }
    public Color awtColor(){ return attribute.awtColor(); }
    public ILayer clr(IColor c){ attribute.clr(c); return this; }
    public ILayer clr(IColor c, int alpha){ attribute.clr(c,alpha); return this; }
    public ILayer clr(IColor c, float alpha){ attribute.clr(c,alpha); return this; }
    public ILayer clr(IColor c, double alpha){ attribute.clr(c,alpha); return this; }
    public ILayer clr(Color c){ attribute.clr(c); return this; }
    public ILayer clr(Color c, int alpha){ attribute.clr(c,alpha); return this; }
    public ILayer clr(Color c, float alpha){ attribute.clr(c,alpha); return this; }
    public ILayer clr(Color c, double alpha){ attribute.clr(c,alpha); return this; }
    public ILayer clr(int gray){ attribute.clr(gray); return this; }
    public ILayer clr(float fgray){ attribute.clr(fgray); return this; }
    public ILayer clr(double dgray){ attribute.clr(dgray); return this; }
    public ILayer clr(int gray, int alpha){ attribute.clr(gray,alpha); return this; }
    public ILayer clr(float fgray, float falpha){ attribute.clr(fgray,falpha); return this; }
    public ILayer clr(double dgray, double dalpha){ attribute.clr(dgray,dalpha); return this; }
    public ILayer clr(int r, int g, int b){ attribute.clr(r,g,b); return this; }
    public ILayer clr(float fr, float fg, float fb){ attribute.clr(fr,fg,fb); return this; }
    public ILayer clr(double dr, double dg, double db){ attribute.clr(dr,dg,db); return this; }
    public ILayer clr(int r, int g, int b, int a){ attribute.clr(r,g,b,a); return this; }
    public ILayer clr(float fr, float fg, float fb, float fa){ attribute.clr(fr,fg,fb,fa); return this; }
    public ILayer clr(double dr, double dg, double db, double da){ attribute.clr(dr,dg,db,da); return this; }
    public ILayer hsb(float h, float s, float b, float a){ attribute.hsb(h,s,b,a); return this; }
    public ILayer hsb(double h, double s, double b, double a){ attribute.hsb(h,s,b,a); return this; }
    public ILayer hsb(float h, float s, float b){ attribute.hsb(h,s,b); return this; }
    public ILayer hsb(double h, double s, double b){ attribute.hsb(h,s,b); return this; }
    
    public IColor getColor(){ return clr(); }
    public Color getAWTColor(){ return color(); }
    public ILayer setColor(IColor c){ return clr(c); }
    public ILayer setColor(IColor c, int alpha){ return clr(c, alpha); }
    public ILayer setColor(IColor c, float alpha){ return clr(c, alpha); }
    public ILayer setColor(IColor c, double alpha){ return clr(c, alpha); }
    public ILayer setColor(Color c){ return clr(c); }
    public ILayer setColor(Color c, int alpha){ return clr(c, alpha); }
    public ILayer setColor(Color c, float alpha){ return clr(c, alpha); }
    public ILayer setColor(Color c, double alpha){ return clr(c, alpha); }
    public ILayer setColor(int gray){  return clr(gray); }
    public ILayer setColor(float fgray){  return clr(fgray); }
    public ILayer setColor(double dgray){ return clr(dgray); }
    public ILayer setColor(int gray, int alpha){ return clr(gray,alpha); }
    public ILayer setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public ILayer setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public ILayer setColor(int r, int g, int b){ return clr(r,g,b); }
    public ILayer setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public ILayer setColor(double dr, double dg, double db){  return clr(dr,dg,db); }
    public ILayer setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public ILayer setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public ILayer setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public ILayer setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public ILayer setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public ILayer setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public ILayer setHSBColor(double h, double s, double b){ return hsb(h,s,b); }
    
    public ILayer weight(double w){ super.weight(w); return this; }
    public ILayer weight(float w){ super.weight(w); return this; }
    
    public ILayer setMaterial(IMaterial mat){ attribute.material = mat; return this; }
    
}
