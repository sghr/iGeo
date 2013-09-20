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

public class IPointCurlField extends I3DField{
    IPoint point; // just to visualize when it has position
    
    public IPointCurlField(IVecI pos, IVecI axis){
	super(new IPointCurlFieldGeo(pos,axis));
	point = new IPoint(pos);
    }
    
    public IPointCurlField(double xpos, double ypos, double zpos, double xaxis, double yaxis, double zaxis){
	super(new IPointCurlFieldGeo(new IVec(xpos,ypos,zpos), new IVec(xaxis,yaxis,zaxis)));
	point = new IPoint(((IPointFieldGeo)field).pos());
    }
    
    //public IVecI get(IVecI v){ return gravity; }
    
    // those have no effect
    public IPointCurlField noDecay(){ super.noDecay(); return this; }
    public IPointCurlField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public IPointCurlField linear(double threshold){ super.linear(threshold); return this; }
    public IPointCurlField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public IPointCurlField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public IPointCurlField gauss(double threshold){ super.gauss(threshold); return this; }
    public IPointCurlField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IPointCurlField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public IPointCurlField threshold(double t){ super.threshold(t); return this; }
    public IPointCurlField intensity(double i){ super.intensity(i); return this; }
    
    
    public IPointCurlField name(String nm){ super.name(nm); point.name(nm); return this; }
    public IPointCurlField layer(ILayer l){ super.layer(l); point.layer(l); return this; }
    public IPointCurlField show(){ point.show(); return this; }
    public IPointCurlField hide(){ point.hide(); return this; }

    public void del(){ point.del(); super.del(); }
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IPointCurlField setSize(double sz){ return size(sz); }
    public IPointCurlField size(double sz){ point.size(sz); return this; }
    public double getSize(){ return point.size(); }
    public double size(){ return point.size(); }
    
    
    public IPointCurlField clr(Color c){ super.clr(c); point.clr(c); return this; }
    public IPointCurlField clr(Color c, int alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    public IPointCurlField clr(int gray){ super.clr(gray); point.clr(gray); return this; }
    public IPointCurlField clr(float fgray){ super.clr(fgray); point.clr(fgray); return this; }
    public IPointCurlField clr(double dgray){ super.clr(dgray); point.clr(dgray); return this; }
    public IPointCurlField clr(int gray, int alpha){ super.clr(gray,alpha); point.clr(gray,alpha); return this; }
    public IPointCurlField clr(float fgray, float falpha){ super.clr(fgray,falpha); point.clr(fgray,falpha); return this; }
    public IPointCurlField clr(double dgray, double dalpha){ super.clr(dgray,dalpha); point.clr(dgray,dalpha); return this; }
    public IPointCurlField clr(int r, int g, int b){ super.clr(r,g,b); point.clr(r,g,b); return this; }
    public IPointCurlField clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); point.clr(fr,fg,fb); return this; }
    public IPointCurlField clr(double dr, double dg, double db){ super.clr(dr,dg,db); point.clr(dr,dg,db); return this; }
    public IPointCurlField clr(int r, int g, int b, int a){
        super.clr(r,g,b,a); point.clr(r,g,b,a); return this;
    }
    public IPointCurlField clr(float fr, float fg, float fb, float fa){
        super.clr(fr,fg,fb,fa); point.clr(fr,fg,fb,fa); return this;
    }
    public IPointCurlField clr(double dr, double dg, double db, double da){
        super.clr(dr,dg,db,da); point.clr(dr,dg,db,da); return this;
    }
    public IPointCurlField hsb(float h, float s, float b, float a){
        super.hsb(h,s,b,a); point.hsb(h,s,b,a); return this;
    }
    public IPointCurlField hsb(double h, double s, double b, double a){
        super.hsb(h,s,b,a); point.hsb(h,s,b,a); return this;
    }
    public IPointCurlField hsb(float h, float s, float b){
        super.hsb(h,s,b); point.hsb(h,s,b); return this;
    }
    public IPointCurlField hsb(double h, double s, double b){
        super.hsb(h,s,b); point.hsb(h,s,b); return this;
    }
    public IPointCurlField setColor(Color c){ return clr(c); }
    public IPointCurlField setColor(Color c, int alpha){ return clr(c,alpha); }
    public IPointCurlField setColor(int gray){ return clr(gray); }
    public IPointCurlField setColor(float fgray){ return clr(fgray); }
    public IPointCurlField setColor(double dgray){ return clr(dgray); }
    public IPointCurlField setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IPointCurlField setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IPointCurlField setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IPointCurlField setColor(int r, int g, int b){ return clr(r,g,b); }
    public IPointCurlField setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IPointCurlField setColor(double dr, double dg, double db){ return clr(dr,dg,db); }
    public IPointCurlField setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IPointCurlField setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IPointCurlField setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IPointCurlField setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IPointCurlField setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IPointCurlField setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IPointCurlField setHSBColor(double h, double s, double b){ return hsb(h,s,b); }

    public IPointCurlField weight(double w){ super.weight(w); point.weight(w); return this; }
    public IPointCurlField weight(float w){ super.weight(w); point.weight(w); return this; }
    
}
