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

public class IGravity extends I3DField{
    IPoint point; // just to visualize when it has position
    
    //IVecI gravity;
    //public IGravity(){ super(null); gravity = new IVec(0,0,-10); } // default
    //public IGravity(IVecI p){ super(null); gravity = p; }
    //public IGravity(double x, double y, double z){ super(nnull); gravity = new IVec(x,y,z); }
    //public IGravity(double gravity){ super(null); this.gravity = new IVec(0,0,-gravity); }    
    
    public IGravity(){ super(new IPointFieldGeo(null, new IVec(0,0,-10))); field.constantIntensity(false); } // default
    public IGravity(IVecI gravity){ super(new IPointFieldGeo(null,gravity)); field.constantIntensity(false); }
    public IGravity(double x, double y, double z){ super(new IPointFieldGeo(null, new IVec(x,y,z))); field.constantIntensity(false); }
    public IGravity(double gravity){ super(new IPointFieldGeo(null, new IVec(0,0,-gravity))); field.constantIntensity(false); }
    
    public IGravity(IVecI pos, IVecI gravity){ 
	super(new IPointFieldGeo(pos,gravity)); field.constantIntensity(false); 
	point = new IPoint(pos);
    }
    public IGravity(double xpos, double ypos, double zpos, double xdir, double ydir, double zdir){
	super(new IPointFieldGeo(new IVec(xpos,ypos,zpos), new IVec(xdir,ydir,zdir))); 
	field.constantIntensity(false); 
	point = new IPoint(((IPointFieldGeo)field).pos());
    }
    
    //public IVecI get(IVecI v){ return gravity; }
    
    // those have no effect
    public IGravity noDecay(){ super.noDecay(); return this; }
    public IGravity linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public IGravity linear(double threshold){ super.linear(threshold); return this; }
    public IGravity gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public IGravity gaussian(double threshold){ super.gaussian(threshold); return this; }
    public IGravity gauss(double threshold){ super.gauss(threshold); return this; }
    public IGravity constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IGravity bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    
    public IGravity threshold(double t){ super.threshold(t); return this; }
    public IGravity intensity(double i){ super.intensity(i); return this; }
    
    
    public IGravity name(String nm){ super.name(nm); if(point!=null){ point.name(nm); } return this; }
    public IGravity layer(ILayer l){ super.layer(l); if(point!=null){ point.layer(l); } return this; }
    public IGravity show(){ if(point!=null){ point.show(); } return this; }
    public IGravity hide(){ if(point!=null){ point.hide(); } return this; }

    public void del(){ if(point!=null){ point.del(); } super.del(); }
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IGravity setSize(double sz){ return size(sz); }
    public IGravity size(double sz){ if(point!=null){ point.size(sz); } return this; }
    public double getSize(){ if(point!=null){ return point.size(); } return 0; }
    public double size(){ if(point!=null){ return point.size(); } return 0; }
    
    
    public IGravity clr(Color c){ super.clr(c); if(point!=null){ point.clr(c); } return this; }
    public IGravity clr(Color c, int alpha){ super.clr(c,alpha); if(point!=null){ point.clr(c,alpha); } return this; }
    public IGravity clr(int gray){ super.clr(gray); if(point!=null){ point.clr(gray); } return this; }
    public IGravity clr(float fgray){ super.clr(fgray); if(point!=null){ point.clr(fgray); } return this; }
    public IGravity clr(double dgray){ super.clr(dgray); if(point!=null){ point.clr(dgray); } return this; }
    public IGravity clr(int gray, int alpha){ super.clr(gray,alpha); if(point!=null){ point.clr(gray,alpha); } return this; }
    public IGravity clr(float fgray, float falpha){ super.clr(fgray,falpha); if(point!=null){ point.clr(fgray,falpha); } return this; }
    public IGravity clr(double dgray, double dalpha){ super.clr(dgray,dalpha); if(point!=null){ point.clr(dgray,dalpha); } return this; }
    public IGravity clr(int r, int g, int b){ super.clr(r,g,b); if(point!=null){ point.clr(r,g,b); } return this; }
    public IGravity clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); if(point!=null){ point.clr(fr,fg,fb); } return this; }
    public IGravity clr(double dr, double dg, double db){ super.clr(dr,dg,db); if(point!=null){ point.clr(dr,dg,db); } return this; }
    public IGravity clr(int r, int g, int b, int a){
        super.clr(r,g,b,a); if(point!=null){ point.clr(r,g,b,a); } return this;
    }
    public IGravity clr(float fr, float fg, float fb, float fa){
        super.clr(fr,fg,fb,fa); if(point!=null){ point.clr(fr,fg,fb,fa); } return this;
    }
    public IGravity clr(double dr, double dg, double db, double da){
        super.clr(dr,dg,db,da); if(point!=null){ point.clr(dr,dg,db,da); } return this;
    }
    public IGravity hsb(float h, float s, float b, float a){
        super.hsb(h,s,b,a); if(point!=null){ point.hsb(h,s,b,a); } return this;
    }
    public IGravity hsb(double h, double s, double b, double a){
        super.hsb(h,s,b,a); if(point!=null){ point.hsb(h,s,b,a); } return this;
    }
    public IGravity hsb(float h, float s, float b){
        super.hsb(h,s,b); if(point!=null){ point.hsb(h,s,b); } return this;
    }
    public IGravity hsb(double h, double s, double b){
        super.hsb(h,s,b); if(point!=null){ point.hsb(h,s,b); } return this;
    }
    public IGravity setColor(Color c){ return clr(c); }
    public IGravity setColor(Color c, int alpha){ return clr(c,alpha); }
    public IGravity setColor(int gray){ return clr(gray); }
    public IGravity setColor(float fgray){ return clr(fgray); }
    public IGravity setColor(double dgray){ return clr(dgray); }
    public IGravity setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IGravity setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IGravity setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IGravity setColor(int r, int g, int b){ return clr(r,g,b); }
    public IGravity setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IGravity setColor(double dr, double dg, double db){ return clr(dr,dg,db); }
    public IGravity setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IGravity setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IGravity setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IGravity setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IGravity setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IGravity setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IGravity setHSBColor(double h, double s, double b){ return hsb(h,s,b); }

    public IGravity weight(double w){ super.weight(w); if(point!=null){ point.weight(w); } return this; }
    public IGravity weight(float w){ super.weight(w); if(point!=null){ point.weight(w); } return this; }
    
    
}
