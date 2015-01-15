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
   Attractor field defined by a plane.
   
   @author Satoru Sugihara
*/

public class IPlaneAttractorField extends I3DField{
    IPoint point; // just to visualize when it has position

    static public class IPlaneAttractorFieldGeo extends IPlaneFieldGeo{
	public IPlaneAttractorFieldGeo(IVecI pos, IVecI nml){ super(pos,nml); }
	public IVecI getForce(IVecI v){
	    IVec proj = v.get().cp().projectToPlane(nml,nml,this.pos);
	    return proj.dif(v);
	}
    }
    
    public IPlaneAttractorField(IVecI pos, IVecI nml){
	super(new IPlaneAttractorFieldGeo(pos,nml));
	point = new IPoint(pos);
    }
    
    public IPlaneAttractorField(double xpos, double ypos, double zpos, double xnml, double ynml, double znml){
	super(new IPlaneAttractorFieldGeo(new IVec(xpos,ypos,zpos), new IVec(xnml,ynml,znml)));
	point = new IPoint(((IPlaneFieldGeo)field).pos());
    }
    
    // those have no effect
    public IPlaneAttractorField noDecay(){ super.noDecay(); return this; }
    public IPlaneAttractorField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public IPlaneAttractorField linear(double threshold){ super.linear(threshold); return this; }
    public IPlaneAttractorField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public IPlaneAttractorField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public IPlaneAttractorField gauss(double threshold){ super.gauss(threshold); return this; }
    public IPlaneAttractorField decay(IDecay decay, double threshold){ super.decay(decay,threshold); return this; }
    public IPlaneAttractorField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IPlaneAttractorField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public IPlaneAttractorField threshold(double t){ super.threshold(t); return this; }
    public IPlaneAttractorField intensity(double i){ super.intensity(i); return this; }
    
    
    public IPlaneAttractorField name(String nm){ super.name(nm); point.name(nm); return this; }
    public IPlaneAttractorField layer(ILayer l){ super.layer(l); point.layer(l); return this; }
    public IPlaneAttractorField show(){ point.show(); return this; }
    public IPlaneAttractorField hide(){ point.hide(); return this; }

    public void del(){ point.del(); super.del(); }
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IPlaneAttractorField setSize(double sz){ return size(sz); }
    public IPlaneAttractorField size(double sz){ point.size(sz); return this; }
    public double getSize(){ return point.size(); }
    public double size(){ return point.size(); }
    
    
    public IPlaneAttractorField clr(Color c){ super.clr(c); point.clr(c); return this; }
    public IPlaneAttractorField clr(Color c, int alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    public IPlaneAttractorField clr(int gray){ super.clr(gray); point.clr(gray); return this; }
    public IPlaneAttractorField clr(float fgray){ super.clr(fgray); point.clr(fgray); return this; }
    public IPlaneAttractorField clr(double dgray){ super.clr(dgray); point.clr(dgray); return this; }
    public IPlaneAttractorField clr(int gray, int alpha){ super.clr(gray,alpha); point.clr(gray,alpha); return this; }
    public IPlaneAttractorField clr(float fgray, float falpha){ super.clr(fgray,falpha); point.clr(fgray,falpha); return this; }
    public IPlaneAttractorField clr(double dgray, double dalpha){ super.clr(dgray,dalpha); point.clr(dgray,dalpha); return this; }
    public IPlaneAttractorField clr(int r, int g, int b){ super.clr(r,g,b); point.clr(r,g,b); return this; }
    public IPlaneAttractorField clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); point.clr(fr,fg,fb); return this; }
    public IPlaneAttractorField clr(double dr, double dg, double db){ super.clr(dr,dg,db); point.clr(dr,dg,db); return this; }
    public IPlaneAttractorField clr(int r, int g, int b, int a){
        super.clr(r,g,b,a); point.clr(r,g,b,a); return this;
    }
    public IPlaneAttractorField clr(float fr, float fg, float fb, float fa){
        super.clr(fr,fg,fb,fa); point.clr(fr,fg,fb,fa); return this;
    }
    public IPlaneAttractorField clr(double dr, double dg, double db, double da){
        super.clr(dr,dg,db,da); point.clr(dr,dg,db,da); return this;
    }
    public IPlaneAttractorField hsb(float h, float s, float b, float a){
        super.hsb(h,s,b,a); point.hsb(h,s,b,a); return this;
    }
    public IPlaneAttractorField hsb(double h, double s, double b, double a){
        super.hsb(h,s,b,a); point.hsb(h,s,b,a); return this;
    }
    public IPlaneAttractorField hsb(float h, float s, float b){
        super.hsb(h,s,b); point.hsb(h,s,b); return this;
    }
    public IPlaneAttractorField hsb(double h, double s, double b){
        super.hsb(h,s,b); point.hsb(h,s,b); return this;
    }
    public IPlaneAttractorField setColor(Color c){ return clr(c); }
    public IPlaneAttractorField setColor(Color c, int alpha){ return clr(c,alpha); }
    public IPlaneAttractorField setColor(int gray){ return clr(gray); }
    public IPlaneAttractorField setColor(float fgray){ return clr(fgray); }
    public IPlaneAttractorField setColor(double dgray){ return clr(dgray); }
    public IPlaneAttractorField setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IPlaneAttractorField setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IPlaneAttractorField setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IPlaneAttractorField setColor(int r, int g, int b){ return clr(r,g,b); }
    public IPlaneAttractorField setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IPlaneAttractorField setColor(double dr, double dg, double db){ return clr(dr,dg,db); }
    public IPlaneAttractorField setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IPlaneAttractorField setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IPlaneAttractorField setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IPlaneAttractorField setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IPlaneAttractorField setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IPlaneAttractorField setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IPlaneAttractorField setHSBColor(double h, double s, double b){ return hsb(h,s,b); }

    public IPlaneAttractorField weight(double w){ super.weight(w); point.weight(w); return this; }
    public IPlaneAttractorField weight(float w){ super.weight(w); point.weight(w); return this; }
    
}
