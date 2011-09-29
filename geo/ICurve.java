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

    /** getting i-th control point */
    public IVec cp(int i){ return curve.cp(i).get(); }
    /** getting i-th control point */
    public IVecI cp(IIntegerI i){ return curve.cp(i); }
    
    public IVecI[] cps(){ return curve.cps(); }

    /** getting i-th edit point */
    public IVec ep(int i){ return curve.ep(i); }
    /** getting i-th edit point */
    public IVec ep(IIntegerI i){ return curve.ep(i); }

    /** start point of the curve */
    public IVec start(){ return curve.start(); }
    /** end point of the curve */
    public IVec end(){ return curve.end(); }
    /** start control point of the curve */
    public IVec startCP(){ return curve.startCP(); }
    /** end control point of the curve */
    public IVec endCP(){ return curve.endCP(); }
    
    public double knot(int i){ return curve.knot(i); }
    public IDouble knot(IIntegerI i){ return curve.knot(i); }
    
    public double[] knots(){ return curve.knots(); }
    public double[] knots(ISwitchE e){ return knots(); }
    public IDoubleI[] knots(ISwitchR r){ return curve.knots(r); }
    
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


    /******************************************************************************
     * transformation methods; API of ITransformable interface
     ******************************************************************************/
    
    public ICurve add(double x, double y, double z){
	curve.add(x,y,z); return this;
    }
    public ICurve add(IDoubleI x, IDoubleI y, IDoubleI z){
	curve.add(x,y,z); return this;
    }
    public ICurve add(IVecI v){ curve.add(v); return this; }
    public ICurve sub(double x, double y, double z){
	curve.sub(x,y,z); return this;
    }
    public ICurve sub(IDoubleI x, IDoubleI y, IDoubleI z){
	curve.sub(x,y,z); return this;
    }
    public ICurve sub(IVecI v){ curve.sub(v); return this; }
    public ICurve mul(IDoubleI v){ curve.mul(v); return this; }
    public ICurve mul(double v){ curve.mul(v); return this; }
    public ICurve div(IDoubleI v){ curve.div(v); return this; }
    public ICurve div(double v){ curve.div(v); return this; }
    
    public ICurve neg(){ curve.neg(); return this; }
    public ICurve flip(){ curve.neg(); return this; }
    
    
    /** scale add */
    public ICurve add(IVecI v, double f){ curve.add(v,f); return this; }
    public ICurve add(IVecI v, IDoubleI f){ curve.add(v,f); return this; }
    
    public ICurve rot(IVecI axis, IDoubleI angle){ curve.rot(axis,angle); return this; }
    public ICurve rot(IVecI axis, double angle){ curve.rot(axis,angle); return this; }
    public ICurve rot(IVecI center, IVecI axis, IDoubleI angle){
	curve.rot(center,axis,angle); return this;
    }
    public ICurve rot(IVecI center, IVecI axis, double angle){
	curve.rot(center,axis,angle); return this;
    }
    
    /** rotate to destination direction vector */
    public ICurve rot(IVecI axis, IVecI destDir){
	curve.rot(axis,destDir); return this;
    }
    /** rotate to destination point location */    
    public ICurve rot(IVecI center, IVecI axis, IVecI destPt){
	curve.rot(center,axis,destPt); return this;
    }
    
    
    /** alias of mul */
    public ICurve scale(IDoubleI f){ return mul(f); }
    public ICurve scale(double f){ return mul(f); }
    public ICurve scale(IVecI center, IDoubleI f){ curve.scale(center,f); return this; }
    public ICurve scale(IVecI center, double f){ curve.scale(center,f); return this; }
    
    /** scale only in 1 direction */
    public ICurve scale1d(IVecI axis, double f){ curve.scale1d(axis,f); return this; }
    public ICurve scale1d(IVecI axis, IDoubleI f){ curve.scale1d(axis,f); return this; }
    public ICurve scale1d(IVecI center, IVecI axis, double f){
	curve.scale1d(center,axis,f); return this;
    }
    public ICurve scale1d(IVecI center, IVecI axis, IDoubleI f){
	curve.scale1d(center,axis,f); return this;
    }
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public ICurve ref(IVecI planeDir){ curve.ref(planeDir); return this; }
    public ICurve ref(IVecI center, IVecI planeDir){ curve.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    public ICurve mirror(IVecI planeDir){ return ref(planeDir); }
    public ICurve mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    public ICurve shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	curve.shear(sxy, syx, syz, szy, szx, sxz); return this;
    }
    public ICurve shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	curve.shear(sxy, syx, syz, szy, szx, sxz); return this;
    }
    public ICurve shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	curve.shear(center, sxy, syx, syz, szy, szx, sxz); return this;
    }
    public ICurve shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	curve.shear(center, sxy, syx, syz, szy, szx, sxz); return this;
    }
    
    public ICurve shearXY(double sxy, double syx){ curve.shearXY(sxy,syx); return this; }
    public ICurve shearXY(IDoubleI sxy, IDoubleI syx){ curve.shearXY(sxy,syx); return this; }
    public ICurve shearXY(IVecI center, double sxy, double syx){ curve.shearXY(center,sxy,syx); return this; }
    public ICurve shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){ curve.shearXY(center,sxy,syx); return this; }
    
    public ICurve shearYZ(double syz, double szy){ curve.shearYZ(syz,szy); return this; }
    public ICurve shearYZ(IDoubleI syz, IDoubleI szy){ curve.shearYZ(syz,szy); return this; }
    public ICurve shearYZ(IVecI center, double syz, double szy){ curve.shearYZ(center,syz,szy); return this; }
    public ICurve shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){ curve.shearYZ(center,syz,szy); return this; }
    
    public ICurve shearZX(double szx, double sxz){ curve.shearZX(szx,sxz); return this; }
    public ICurve shearZX(IDoubleI szx, IDoubleI sxz){ curve.shearZX(szx,sxz); return this; }
    public ICurve shearZX(IVecI center, double szx, double sxz){ curve.shearZX(center,szx,sxz); return this; }
    public ICurve shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){ curve.shearZX(center,szx,sxz); return this; }
    
    
    /** translate is alias of add() */
    public ICurve translate(double x, double y, double z){ return add(x,y,z); }
    public ICurve translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ICurve translate(IVecI v){ return add(v); }
    
    /** mv() is alias of add() */
    public ICurve mv(double x, double y, double z){ return add(x,y,z); }
    public ICurve mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ICurve mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public ICurve cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public ICurve cp(double x, double y, double z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    public ICurve cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    public ICurve cp(IVecI v){ return dup().add(v); }
    
    
    public ICurve transform(IMatrix3I mat){ curve.transform(mat); return this; }
    public ICurve transform(IMatrix4I mat){ curve.transform(mat); return this; }
    public ICurve transform(IVecI xvec, IVecI yvec, IVecI zvec){
	curve.transform(xvec,yvec,zvec); return this;
    }
    public ICurve transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	curve.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /******************************************************************************
     * IObject methods
     ******************************************************************************/

    
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
