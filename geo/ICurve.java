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

package igeo.geo;

import java.awt.Color;

import igeo.core.*;
import igeo.gui.*;


/**
   Class of NURBS curve object.
   It contains ICurveGeo instance inside.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ICurve extends IObject implements ICurveI{
    public ICurveGeo curve;
    //public ICurveI curve; // public?
    

    public ICurve(){ this((IServerI)null); }
    public ICurve(IVecI[] cpts, int degree, double[] knots, double ustart, double uend){
	this((IServerI)null, cpts, degree, knots, ustart, uend);
    }
    public ICurve(IVecI[] cpts, int degree, double[] knots){
	this((IServerI)null, cpts, degree, knots);
    }
    public ICurve(IVecI[] cpts, int degree){ this((IServerI)null, cpts, degree); }
    public ICurve(IVecI[] cpts){ this((IServerI)null, cpts); }
    public ICurve(IVecI[] cpts, int degree, boolean close){
	this((IServerI)null, cpts, degree,close);
    }
    public ICurve(IVecI[] cpts, boolean close){ this((IServerI)null, cpts, close); }
    public ICurve(IVecI pt1, IVecI pt2){ this((IServerI)null, pt1, pt2); }
    public ICurve(double x1, double y1, double z1, double x2, double y2, double z2){
	this((IServerI)null, x1,y1,z1,x2,y2,z2);
    }
    public ICurve(double[][] xyzValues){ this((IServerI)null, xyzValues); }
    public ICurve(double[][] xyzValues, int degree){
	this((IServerI)null, xyzValues, degree);
    }
    public ICurve(double[][] xyzValues, boolean close){
	this((IServerI)null, xyzValues, close);
    }
    public ICurve(double[][] xyzValues, int degree, boolean close){
	this((IServerI)null, xyzValues, degree, close);
    }
        
    
    //public ICurve(ICurveGeo crv){ curve = new ICurveGeo(crv); initCurve(null); }
    public ICurve(ICurveGeo crv){ this((IServerI)null, crv); }
    public ICurve(ICurveI crv){ this((IServerI)null, crv); }
    public ICurve(ICurve crv){
	super(crv);
        //curve = new ICurveGeo(crv.get());
	curve = crv.curve.dup();
	initCurve(crv.server); //?
	setColor(crv.getColor());
    }
    
    
    public ICurve(IServerI s){ super(s); curve = new ICurveGeo(); initCurve(s); }
    public ICurve(IServerI s, IVecI[] cpts, int degree, double[] knots, double ustart, double uend){
	super(s);
	curve = new ICurveGeo(cpts,degree,knots,ustart,uend);
	initCurve(s); 
    }
    public ICurve(IServerI s, IVecI[] cpts, int degree, double[] knots){
	super(s); curve = new ICurveGeo(cpts,degree,knots); initCurve(s); 
    }
    public ICurve(IServerI s, IVecI[] cpts, int degree){
        super(s); curve = new ICurveGeo(cpts,degree); initCurve(s); 
    }
    public ICurve(IServerI s, IVecI[] cpts){
        super(s); curve = new ICurveGeo(cpts); initCurve(s); 
    }
    public ICurve(IServerI s, IVecI[] cpts, int degree, boolean close){
        super(s); curve = new ICurveGeo(cpts,degree,close); initCurve(s); 
    }
    public ICurve(IServerI s, IVecI[] cpts, boolean close){
        super(s); curve = new ICurveGeo(cpts,close); initCurve(s); 
    }
    public ICurve(IServerI s, IVecI pt1, IVecI pt2){
	super(s); curve = new ICurveGeo(pt1,pt2); initCurve(s); 
    }
    public ICurve(IServerI s, double x1, double y1, double z1, double x2, double y2, double z2){
	super(s); curve = new ICurveGeo(new IVec(x1,y1,z1),new IVec(x2,y2,z2));initCurve(s); 
    }
    
    public ICurve(IServerI s, double[][] xyzValues){
	super(s); curve = new ICurveGeo(xyzValues); initCurve(s); 
    }
    public ICurve(IServerI s, double[][] xyzValues, int degree){
	super(s); curve = new ICurveGeo(xyzValues,degree); initCurve(s); 
    }
    public ICurve(IServerI s, double[][] xyzValues, boolean close){
	super(s); curve = new ICurveGeo(xyzValues,close); initCurve(s); 
    }
    public ICurve(IServerI s, double[][] xyzValues, int degree, boolean close){
	super(s); curve = new ICurveGeo(xyzValues,degree,close); initCurve(s); 
    }
    
    //public ICurve(IServerI s, ICurveGeo crv){ super(s); curve = new ICurveGeo(crv); initCurve(s); }
    public ICurve(IServerI s, ICurveGeo crv){ super(s); curve = crv; initCurve(s); }
    public ICurve(IServerI s, ICurveI crv){ super(s); curve = crv.get(); initCurve(s); }
    
    public ICurve(IServerI s, ICurve crv){
	super(s,crv);
        //curve = new ICurveGeo(crv.get());
	curve = crv.curve.dup();
	initCurve(s); //?
	setColor(crv.getColor());
    }
    
    /*
    public ICurve(){ curve = new ICurveGeo(); }
    public ICurve(IVecI[] cpts, int degree, double[] knots, double ustart, double uend){
	curve = new ICurveGeo(cpts,degree,knots,ustart,uend); initCurve(null); 
    }
    public ICurve(IVecI[] cpts, int degree, double[] knots){
	curve = new ICurveGeo(cpts,degree,knots); initCurve(null); 
    }
    public ICurve(IVecI[] cpts, int degree){
        curve = new ICurveGeo(cpts,degree); initCurve(null); 
    }
    public ICurve(IVecI[] cpts){
        curve = new ICurveGeo(cpts); initCurve(null); 
    }
    public ICurve(IVecI[] cpts, int degree, boolean close){
        curve = new ICurveGeo(cpts,degree,close); initCurve(null); 
    }
    public ICurve(IVecI[] cpts, boolean close){
        curve = new ICurveGeo(cpts,close); initCurve(null); 
    }
    public ICurve(IVecI pt1, IVecI pt2){
	curve = new ICurveGeo(pt1,pt2);initCurve(null); 
    }
    public ICurve(double x1, double y1, double z1, double x2, double y2, double z2){
	curve = new ICurveGeo(new IVec(x1,y1,z1),new IVec(x2,y2,z2));initCurve(null); 
    }
    public ICurve(double[][] xyzValues){
	curve = new ICurveGeo(xyzValues); initCurve(null); 
    }
    public ICurve(double[][] xyzValues, int degree){
	curve = new ICurveGeo(xyzValues,degree); initCurve(null); 
    }
    public ICurve(double[][] xyzValues, boolean close){
	curve = new ICurveGeo(xyzValues,close); initCurve(null); 
    }
    public ICurve(double[][] xyzValues, int degree, boolean close){
	curve = new ICurveGeo(xyzValues,degree,close); initCurve(null); 
    }
    //public ICurve(ICurveGeo crv){ curve = new ICurveGeo(crv); initCurve(null); }
    public ICurve(ICurveGeo crv){ curve = crv; initCurve(null); }
    public ICurve(ICurveI crv){ curve = crv.get(); initCurve(null); }
    public ICurve(ICurve crv){
	super(crv);
        curve = new ICurveGeo(crv.get());
	initCurve(crv.server); //?
	setColor(crv.getColor());
    }
    */
    
    
    public void initCurve(IServerI s){
	if(curve instanceof ICurveGeo){ parameter = (ICurveGeo)curve; }
	if(graphics==null) initGraphic(s);
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
	if(m.isGL()) return new ICurveGraphicGL(this); 
	return null;
    }
    
    public ICurveGeo get(){ return curve.get(); } //?
    
    public ICurve dup(){ return new ICurve(this); }    
    
    
    public IVec pt(IDoubleI u){ return curve.pt(u); }
    public IVec pt(double u){ return curve.pt(u); }
    //public void pt(double u, IVec retval){ curve.pt(u,retval); }
    
    public IVec tan(IDoubleI u){ return curve.tan(u); }
    public IVec tan(double u){ return curve.tan(u); }
    //public void tan(double u, IVec retval){ curve.tan(u,retval); }
    
    public IVec cp(int i){ return curve.cp(i); }
    public IVecI cp(IIntegerI i){ return curve.cp(i); }
    
    public IVec ep(int i){ return curve.ep(i); }
    public IVec ep(IIntegerI i){ return curve.ep(i); }
    
    public IVec start(){ return curve.start(); }
    public IVec end(){ return curve.end(); }
    public IVec startCP(){ return curve.startCP(); }
    public IVec endCP(){ return curve.endCP(); }
    
    public double knot(int i){ return curve.knot(i); }
    public IDouble knot(IIntegerI i){ return curve.knot(i); }
    
    public int knotNum(){ return curve.knotNum(); }
    //public IInteger knotNumR(){ return curve.knotNumR(); }
    public int knotNum(ISwitchE e){ return knotNum(); }
    public IInteger knotNum(ISwitchR r){ return new IInteger(knotNum()); }
    
    public boolean isRational(){ return curve.isRational(); }
    public boolean isRational(ISwitchE e){ return isRational(); }
    public IBool isRational(ISwitchR r){ return new IBool(isRational()); }
    
    public int deg(){ return curve.deg(); }
    //public IInteger degR(){ return curve.degR(); }
    public int deg(ISwitchE e){ return deg(); }
    public IInteger deg(ISwitchR r){ return new IInteger(deg()); }
    
    public int num(){ return curve.num(); }
    //public IInteger numR(){ return curve.numR(); }
    public int num(ISwitchE e){ return num(); }
    public IInteger num(ISwitchR r){ return new IInteger(num()); }
    
    public int cpNum(){ return curve.cpNum(); }
    //public IInteger cpNumR(){ return curve.cpNumR(); }
    public int cpNum(ISwitchE e){ return cpNum(); }
    public IInteger cpNum(ISwitchR r){ return new IInteger(cpNum()); }
    
    public int epNum(){ return curve.epNum(); }
    //public IInteger epNumR(){ return curve.epNumR(); }
    public int epNum(ISwitchE e){ return epNum(); }
    public IInteger epNum(ISwitchR r){ return new IInteger(epNum()); }
    
    public double len(){ return curve.len(); }
    //public IDouble lenR(){ return curve.lenR(); }
    public double len(ISwitchE e){ return len(); }
    public IDouble len(ISwitchR r){ return new IDouble(len()); }
    
    public double u(int epIdx, double epFraction){
	return curve.u(epIdx,epFraction);
    }
    public IDouble u(IInteger epIdx, IDouble epFraction){
	return curve.u(epIdx,epFraction);
    }
    
    public double ustart(){ return curve.ustart(); }
    public double uend(){ return curve.uend(); }
    //public IDouble ustartR(){ return curve.ustartR(); }
    //public IDouble uendR(){ return curve.uendR(); }
    public double ustart(ISwitchE e){ return ustart(); }
    public double uend(ISwitchE e){ return uend(); }
    public IDouble ustart(ISwitchR r){ return new IDouble(ustart()); }
    public IDouble uend(ISwitchR r){ return new IDouble(uend()); }
    
    
    public boolean isClosed(){ return curve.isClosed(); }
    //public IBool isClosedR(){ return curve.isClosedR(); }
    public boolean isClosed(ISwitchE e){ return isClosed(); }
    public IBool isClosed(ISwitchR r){ return new IBool(isClosed()); }

    /** reverse self curve ; not creating a new object
     */
    public ICurve rev(){ curve.rev(); return this; }
    
    
    public ICurve name(String nm){ super.name(nm); return this; }
    public ICurve layer(ILayer l){ super.layer(l); return this; }
    
    
    public ICurve hide(){ super.hide(); return this; }
    public ICurve show(){ super.show(); return this; }
    
    public ICurve clr(Color c){ super.clr(c); return this; }
    public ICurve clr(int gray){ super.clr(gray); return this; }
    public ICurve clr(float fgray){ super.clr(fgray); return this; }
    public ICurve clr(double dgray){ super.clr(dgray); return this; }
    public ICurve clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public ICurve clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public ICurve clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public ICurve clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public ICurve clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public ICurve clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public ICurve clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public ICurve clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public ICurve clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public ICurve hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public ICurve hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public ICurve hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public ICurve hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public ICurve setColor(Color c){ super.setColor(c); return this; }
    public ICurve setColor(int gray){ super.setColor(gray); return this; }
    public ICurve setColor(float fgray){ super.setColor(fgray); return this; }
    public ICurve setColor(double dgray){ super.setColor(dgray); return this; }
    public ICurve setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public ICurve setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public ICurve setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public ICurve setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public ICurve setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public ICurve setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public ICurve setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public ICurve setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public ICurve setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public ICurve setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public ICurve setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public ICurve setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public ICurve setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    
}
