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
import igeo.geo.*;

/**
   A class of a layer to contain and organize objects. Layers are managed by IServer.

   @see IServer
   
   @author Satoru Sugihara
   @version 0.7.0.0;
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
    public ILayer add(IObject e){
	if(!objects.contains(e)){
	    objects.add(e);
	    if(e.layer()!=this) e.layer(this);
	    //e.layer = this;
	    // if e is ILayer, e.layer means parent layer
	}
	//if(e instanceof ILayer){ ((ILayer)e).parentLayer = this; }
	return this;
    }
    public ILayer remove(int i){ objects.remove(i); return this; }
    public ILayer remove(IObject e){ objects.remove(e); return this; }
    
    public boolean contains(IObject e){ return objects.contains(e); }
    
    
    
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
    
    /** Returns all meshe objects contained in a layer.
        IMeshR objects are not included.
    */
    public IMesh[] getMeshes(){
        ArrayList<IMesh> meshes = new ArrayList<IMesh>();
        synchronized(server){
            for(int i=0; i<objects.size(); i++)
                if(objects.get(i) instanceof IMesh)
                    meshes.add((IMesh)objects.get(i));
        }
        return meshes.toArray(new IMesh[meshes.size()]);
    }
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
    /** Returns all objects contained in a layer.
     */
    public IObject[] objects(){
	return objects.toArray(new IObject[objects.size()]);
    }
    
    
    
    public ILayer name(String layerName){ attribute.name = layerName; return this; }
    
    @Override public boolean visible(){ return attribute.visible; }
    
    public ILayer setVisible(boolean v){ return visible(v); }
    public ILayer visible(boolean v){
	attribute.visible=v;
	if(attribute.visible) show(); else hide();
	return this;
    }
    public ILayer hide(){
	super.hide();
	attribute.visible=false;
	for(IObject o: objects) o.hide();
	return this;
    }
    public ILayer show(){
	super.show();
	attribute.visible=true;
	for(IObject o: objects) o.show();
	return this;
    }
    
    
    public Color clr(){ return attribute.clr(); }
    public ILayer clr(Color c){ attribute.clr(c); return this; }
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
    
    public Color getColor(){ return clr(); }
    public ILayer setColor(Color c){ return clr(c); }
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
    
    
    /*
    public Color clr(){ return color; }
    public ILayer clr(Color c){ color = c; return this; }
    public ILayer clr(int gray){ color = IGraphicObject.getColor(gray); return this; }
    public ILayer clr(float fgray){ color = IGraphicObject.getColor(fgray); return this; }
    public ILayer clr(double dgray){ clr((float)dgray); return this; }
    public ILayer clr(int gray, int alpha){ color = IGraphicObject.getColor(gray,alpha); return this; }
    public ILayer clr(float fgray, float falpha){ color = IGraphicObject.getColor(fgray,falpha); return this; }
    public ILayer clr(double dgray, double dalpha){ clr((float)dgray,(float)dalpha); return this; }
    public ILayer clr(int r, int g, int b){ color = IGraphicObject.getColor(r,g,b); return this; }
    public ILayer clr(float fr, float fg, float fb){ color = IGraphicObject.getColor(fr,fg,fb); return this; }
    public ILayer clr(double dr, double dg, double db){ clr((float)dr,(float)dg,(float)db); return this; }
    public ILayer clr(int r, int g, int b, int a){ color = IGraphicObject.getColor(r,g,b,a); return this; }
    public ILayer clr(float fr, float fg, float fb, float fa){ color = IGraphicObject.getColor(fr,fg,fb,fa); return this; }
    public ILayer clr(double dr, double dg, double db, double da){ clr((float)dr,(float)dg,(float)db,(float)da); return this; }
    public ILayer hsb(float h, float s, float b, float a){ color = IGraphicObject.getHSBColor(h,s,b,a); return this; }
    public ILayer hsb(double h, double s, double b, double a){ hsb((float)h,(float)s,(float)b,(float)a); return this; }
    public ILayer hsb(float h, float s, float b){ color = IGraphicObject.getHSBColor(h,s,b); return this; }
    public ILayer hsb(double h, double s, double b){ hsb((float)h,(float)s,(float)b); return this; }
    
    public Color getColor(){ return clr(); }
    public ILayer setColor(Color c){ return clr(c); }
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
    */
    
    
    public ILayer setMaterial(IMaterial mat){ attribute.material = mat; return this; }
    
}
