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

import java.awt.Color;

/**
   Attractor field defined by a center point.
   
   @author Satoru Sugihara
*/

public class IAttractor extends I3DField{
    IPoint point; // just to visualize
    
    public IAttractor(IVecI p, double intensity){
	this(p); intensity(intensity);
    }
    
    public IAttractor(IVecI p){
	super(new IAttractorGeo(p));
	if(p instanceof IPoint){ point = (IPoint)p; }
	else if(p instanceof IPointAgent){ point = ((IPointAgent)p).point; }
	else{ point = new IPoint(p); }
    }
    
    public IAttractor(double x, double y, double z, double intensity){
	this(new IVec(x,y,z),intensity);
    }
    
    public IAttractor(double x, double y, double z){
	this(new IVec(x,y,z));
	//super(new IAttractorGeo(new IVec(x,y,z)));
	//point = new IPoint(((IAttractorGeo)field).pos.get());
    }
    /*
    static public class IAttractorGeo extends IPointFieldGeo{
	public IAttractorGeo(IVecI p){ super(p,null); }
	public IVecI get(IVecI v, IVecI orig){ return orig.dif(v); }
    }
    */
    
    public IVec pos(){ return ((IAttractorGeo)field).pos(); }
    
    public IAttractor noDecay(){ super.noDecay(); return this; }
    public IAttractor linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public IAttractor linear(double threshold){ super.linear(threshold); return this; }
    public IAttractor gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public IAttractor gaussian(double threshold){ super.gaussian(threshold); return this; }
    public IAttractor gauss(double threshold){ super.gauss(threshold); return this; }
    public IAttractor constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IAttractor bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public IAttractor threshold(double t){ super.threshold(t); return this; }
    public IAttractor intensity(double i){ super.intensity(i); return this; }
    
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IAttractor name(String nm){ super.name(nm); point.name(nm); return this; }
    public IAttractor layer(ILayer l){ super.layer(l); point.layer(l); return this; }
    public IAttractor show(){ point.show(); return this; }
    public IAttractor hide(){ point.hide(); return this; }
    
    public void del(){ point.del(); super.del(); }
    
    /** stop agent with option of deleting/keeping the geometry the agent owns */
    public void del(boolean deleteGeometry){ 
	if(deleteGeometry){ point.del(); }
	super.del(deleteGeometry);
    }
    
    
    public IAttractor clr(IColor c){ super.clr(c); point.clr(c); return this; }
    public IAttractor clr(IColor c, int alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    public IAttractor clr(IColor c, float alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    //public IAttractor clr(Color c){ super.clr(c); point.clr(c); return this; }
    //public IAttractor clr(Color c, int alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    public IAttractor clr(int gray){ super.clr(gray); point.clr(gray); return this; }
    public IAttractor clr(float fgray){ super.clr(fgray); point.clr(fgray); return this; }
    public IAttractor clr(double dgray){ super.clr(dgray); point.clr(dgray); return this; }
    public IAttractor clr(int gray, int alpha){ super.clr(gray,alpha); point.clr(gray,alpha); return this; }
    public IAttractor clr(float fgray, float falpha){ super.clr(fgray,falpha); point.clr(fgray,falpha); return this; }
    public IAttractor clr(double dgray, double dalpha){ super.clr(dgray,dalpha); point.clr(dgray,dalpha); return this; }
    public IAttractor clr(int r, int g, int b){ super.clr(r,g,b); point.clr(r,g,b); return this; }
    public IAttractor clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); point.clr(fr,fg,fb); return this; }
    public IAttractor clr(double dr, double dg, double db){ super.clr(dr,dg,db); point.clr(dr,dg,db); return this; }
    public IAttractor clr(int r, int g, int b, int a){
        super.clr(r,g,b,a); point.clr(r,g,b,a); return this;
    }
    public IAttractor clr(float fr, float fg, float fb, float fa){
        super.clr(fr,fg,fb,fa); point.clr(fr,fg,fb,fa); return this;
    }
    public IAttractor clr(double dr, double dg, double db, double da){
        super.clr(dr,dg,db,da); point.clr(dr,dg,db,da); return this;
    }
    public IAttractor hsb(float h, float s, float b, float a){
        super.hsb(h,s,b,a); point.hsb(h,s,b,a); return this;
    }
    public IAttractor hsb(double h, double s, double b, double a){
        super.hsb(h,s,b,a); point.hsb(h,s,b,a); return this;
    }
    public IAttractor hsb(float h, float s, float b){
        super.hsb(h,s,b); point.hsb(h,s,b); return this;
    }
    public IAttractor hsb(double h, double s, double b){
        super.hsb(h,s,b); point.hsb(h,s,b); return this;
    }
    public IAttractor setColor(IColor c){ return clr(c); }
    public IAttractor setColor(IColor c, int alpha){ return clr(c,alpha); }
    //public IAttractor setColor(Color c){ return clr(c); }
    //public IAttractor setColor(Color c, int alpha){ return clr(c,alpha); }
    public IAttractor setColor(int gray){ return clr(gray); }
    public IAttractor setColor(float fgray){ return clr(fgray); }
    public IAttractor setColor(double dgray){ return clr(dgray); }
    public IAttractor setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IAttractor setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IAttractor setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IAttractor setColor(int r, int g, int b){ return clr(r,g,b); }
    public IAttractor setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IAttractor setColor(double dr, double dg, double db){ return clr(dr,dg,db); }
    public IAttractor setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IAttractor setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IAttractor setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IAttractor setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IAttractor setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IAttractor setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IAttractor setHSBColor(double h, double s, double b){ return hsb(h,s,b); }
    
    public IAttractor weight(double w){ super.weight(w); point.weight(w); return this; }
    public IAttractor weight(float w){ super.weight(w); point.weight(w); return this; }
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IAttractor setSize(double sz){ return size(sz); }
    public IAttractor size(double sz){ point.size(sz); return this; }
    public double getSize(){ return point.size(); }
    public double size(){ return point.size(); }
    
    
    
}
