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

import igeo.gui.*;

/**
   Sphere surface class
   
   @author Satoru Sugihara
*/
public class ISphere extends ISurface{
    public IVecI center;
    public IDoubleI radius;
    
    public static double[] sphereKnots(){
	return new double[]{ 0.,0.,0.,.5,.5,1.,1.,1. };
    }
    
    public ISphere(double x, double y, double z, double radius){
	this(null,new IVec(x,y,z),radius);
    }
    
    public ISphere(IServerI s, double x, double y, double z, double radius){
	this(s,new IVec(x,y,z),radius);
    }
    
    public ISphere(IVecI center, double radius){
	this(null,center,radius);
    }
    
    public ISphere(IServerI s, IVecI center, double radius){
	this(s,center,new IDouble(radius));
    }
    
    public ISphere(IVecI center, IDoubleI radius){
	this(null,center,radius);
    }
    
    public ISphere(IServerI s, IVecI center, IDoubleI radius){
	super(s);
	this.center = center; this.radius = radius;
	initSphere(s);
    }
    
    public void initSphere(IServerI s){
	IVec4[][] cpts = new IVec4[9][5];
	double sqrt2 = Math.sqrt(2)/2;
	double r = radius.x();
	IVec cnt = center.get();
	
	IVec4 tpt = cnt.to4d().add(0,0,r);
	IVec4 tptw = tpt.dup(); tptw.w = sqrt2;
	IVec4 bpt = cnt.get().to4d().add(0,0,-r);
	IVec4 bptw = bpt.dup(); bptw.w = sqrt2;
	IVec4[] circlePts = ICircleGeo.circleCP(cnt,new IVec(0,0,1),r);
	
	for(int i=0; i<cpts.length; i++){
	    if(i%2==0) cpts[i][0] = bpt.dup(); else cpts[i][0] = bptw.dup();
	    cpts[i][1] = circlePts[i].dup().add(0,0,-r);
	    cpts[i][1].w *= sqrt2;
	    cpts[i][2] = circlePts[i];
	    cpts[i][3] = circlePts[i].dup().add(0,0,r);
	    cpts[i][3].w *= sqrt2;
	    if(i%2==0) cpts[i][4] = tpt.dup(); else cpts[i][4] = tptw.dup();
	}
	
	surface = new ISurfaceGeo(cpts,
				  ICircleGeo.circleDeg(),ICircleGeo.circleDeg(),
				  ICircleGeo.circleKnots(),
				  sphereKnots());
	super.initSurface(s);
    }
    
    
    
    // name(), layer(), clr() etc.
    
    public ISphere name(String nm){ super.name(nm); return this; }
    public ISphere layer(ILayer l){ super.layer(l); return this; }
    
    public ISphere hide(){ super.hide(); return this; }
    public ISphere show(){ super.show(); return this; }
    
    public ISphere clr(IColor c){ super.clr(c); return this; }
    public ISphere clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public ISphere clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public ISphere clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    
    public ISphere clr(Color c){ super.clr(c); return this; }
    public ISphere clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public ISphere clr(int gray){ super.clr(gray); return this; }
    public ISphere clr(float fgray){ super.clr(fgray); return this; }
    public ISphere clr(double dgray){ super.clr(dgray); return this; }
    public ISphere clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public ISphere clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public ISphere clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public ISphere clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public ISphere clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public ISphere clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public ISphere clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public ISphere clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public ISphere clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public ISphere hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public ISphere hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public ISphere hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public ISphere hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public ISphere setColor(Color c){ super.setColor(c); return this; }
    public ISphere setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public ISphere setColor(int gray){ super.setColor(gray); return this; }
    public ISphere setColor(float fgray){ super.setColor(fgray); return this; }
    public ISphere setColor(double dgray){ super.setColor(dgray); return this; }
    public ISphere setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public ISphere setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public ISphere setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public ISphere setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public ISphere setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public ISphere setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public ISphere setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public ISphere setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public ISphere setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public ISphere setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public ISphere setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public ISphere setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public ISphere setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public ISphere weight(double w){ super.weight(w); return this; }
    public ISphere weight(float w){ super.weight(w); return this; }
    
}
