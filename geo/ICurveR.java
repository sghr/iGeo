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
   Reference class of NURBS curve to contain any instance of ICurveI.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ICurveR extends IObject implements ICurveI{
    public ICurveI curve; // public?
    
    public ICurveR(){ }
    
    public ICurveR(ICurveI crv){ curve = crv; initCurve(null); }
    
    public ICurveR(IServerI s){ super(s); }
    
    public ICurveR(IServerI s, ICurveI crv){ super(s); curve = crv; initCurve(s); }
    
    
    public ICurveR(ICurveR crv){
	super(crv);
        curve = crv.curve.dup(); // deep copy?
	initCurve(crv.server); //?
	setColor(crv.getColor());
    }
    public ICurveR(IServerI s, ICurveR crv){
	super(s,crv);
        curve = crv.curve.dup(); // deep copy?
	initCurve(s); //?
	setColor(crv.getColor());
    }
    
    public void initCurve(IServerI s){
	if(curve instanceof ICurveGeo){ parameter = (ICurveGeo)curve; }
	if(graphics==null) initGraphic(s);
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
	if(m.isGL()) return new ICurveGraphicGL(this); 
	return null;
    }
    
    public boolean isValid(){ return curve.isValid(); }
    
    public ICurveGeo get(){ return curve.get(); } //?
    
    public ICurveR dup(){ return new ICurveR(this); }    
    
    
    public IVecI pt(IDoubleI u){ return curve.pt(u); }
    public IVecI pt(double u){ return curve.pt(u); }
    //public void pt(double u, IVec retval){ curve.pt(u,retval); }
    
    public IVecI tan(IDoubleI u){ return curve.tan(u); }
    public IVecI tan(double u){ return curve.tan(u); }
    //public void tan(double u, IVec retval){ curve.tan(u,retval); }
    
    /** getting i-th control point */
    public IVecI cp(int i){ return curve.cp(i); }
    /** getting i-th control point */
    public IVecI cp(IIntegerI i){ return curve.cp(i); }
    
    public IVecI[] cps(){ return curve.cps(); }

    /** getting i-th edit point */
    public IVecI ep(int i){ return curve.ep(i); }
    /** getting i-th edit point */
    public IVecI ep(IIntegerI i){ return curve.ep(i); }
    
    public IVecI start(){ return curve.start(); }
    public IVecI end(){ return curve.end(); }
    public IVecI startCP(){ return curve.startCP(); }
    public IVecI endCP(){ return curve.endCP(); }

    
    public double knot(int i){ return curve.knot(i); }
    public IDoubleI knot(IIntegerI i){ return curve.knot(i); }
    
    public double[] knots(){ return curve.knots(); }
    public double[] knots(ISwitchE e){ return knots(); }
    public IDoubleI[] knots(ISwitchR r){ return curve.knots(r); }
    
    public int knotNum(){ return curve.knotNum(); }
    //public IIntegerI knotNumR(){ return curve.knotNumR(); }
    public int knotNum(ISwitchE e){ return knotNum(); }
    public IIntegerI knotNum(ISwitchR r){ return curve.knotNum(r); }
    
    public boolean isRational(){ return curve.isRational(); }
    public boolean isRational(ISwitchE e){ return isRational(); }
    public IBoolI isRational(ISwitchR r){ return curve.isRational(r); }
    
    public int deg(){ return curve.deg(); }
    //public IIntegerI degR(){ return curve.degR(); }
    public int deg(ISwitchE e){ return deg(); }
    public IIntegerI deg(ISwitchR r){ return curve.deg(r); }
    
    public int num(){ return curve.num(); }
    //public IIntegerI numR(){ return curve.numR(); }
    public int num(ISwitchE e){ return num(); }
    public IIntegerI num(ISwitchR r){ return curve.num(r); }
    
    public int cpNum(){ return curve.cpNum(); }
    //public IIntegerI cpNumR(){ return curve.cpNumR(); }
    public int cpNum(ISwitchE e){ return cpNum(); }
    public IIntegerI cpNum(ISwitchR r){ return curve.cpNum(r); }
    
    public int epNum(){ return curve.epNum(); }
    //public IIntegerI epNumR(){ return curve.epNumR(); }
    public int epNum(ISwitchE e){ return epNum(); }
    public IIntegerI epNum(ISwitchR r){ return curve.epNum(r); }
    
    public double len(){ return curve.len(); }
    //public IDouble lenR(){ return curve.lenR(); }
    public double len(ISwitchE e){ return len(); }
    public IDouble len(ISwitchR r){ return curve.len(r); }
    
    public double u(int epIdx, double epFraction){
	return curve.u(epIdx,epFraction);
    }
    public IDoubleI u(IInteger epIdx, IDouble epFraction){
	return curve.u(epIdx,epFraction);
    }
    
    public double ustart(){ return curve.ustart(); }
    public double uend(){ return curve.uend(); }
    //public IDoubleI ustartR(){ return curve.ustartR(); }
    //public IDoubleI uendR(){ return curve.uendR(); }
    public double ustart(ISwitchE e){ return ustart(); }
    public double uend(ISwitchE e){ return uend(); }
    public IDoubleI ustart(ISwitchR r){ return curve.ustart(r); }
    public IDoubleI uend(ISwitchR r){ return curve.uend(r); }
    
    
    public boolean isClosed(){ return curve.isClosed(); }
    //public IBoolI isClosedR(){ return curve.isClosedR(); }
    public boolean isClosed(ISwitchE e){ return isClosed(); }
    public IBoolI isClosed(ISwitchR r){ return curve.isClosed(r); }
    
    public ICurveR rev(){ curve.rev(); return this; }
    
    
    /******************************************************************************
     * transformation methods; API of ITransformable interface
     ******************************************************************************/
    
    public ICurveR add(double x, double y, double z){
	curve.add(x,y,z); return this;
    }
    public ICurveR add(IDoubleI x, IDoubleI y, IDoubleI z){
	curve.add(x,y,z); return this;
    }
    public ICurveR add(IVecI v){ curve.add(v); return this; }
    public ICurveR sub(double x, double y, double z){
	curve.sub(x,y,z); return this;
    }
    public ICurveR sub(IDoubleI x, IDoubleI y, IDoubleI z){
	curve.sub(x,y,z); return this;
    }
    public ICurveR sub(IVecI v){ curve.sub(v); return this; }
    public ICurveR mul(IDoubleI v){ curve.mul(v); return this; }
    public ICurveR mul(double v){ curve.mul(v); return this; }
    public ICurveR div(IDoubleI v){ curve.div(v); return this; }
    public ICurveR div(double v){ curve.div(v); return this; }
    
    public ICurveR neg(){ curve.neg(); return this; }
    public ICurveR flip(){ curve.neg(); return this; }
    
    
    /** scale add */
    public ICurveR add(IVecI v, double f){ curve.add(v,f); return this; }
    public ICurveR add(IVecI v, IDoubleI f){ curve.add(v,f); return this; }
    
    public ICurveR rot(IVecI axis, IDoubleI angle){ curve.rot(axis,angle); return this; }
    public ICurveR rot(IVecI axis, double angle){ curve.rot(axis,angle); return this; }
    public ICurveR rot(IVecI center, IVecI axis, IDoubleI angle){
	curve.rot(center,axis,angle); return this;
    }
    public ICurveR rot(IVecI center, IVecI axis, double angle){
	curve.rot(center,axis,angle); return this;
    }
    
    /** rotate to destination direction vector */
    public ICurveR rot(IVecI axis, IVecI destDir){
	curve.rot(axis,destDir); return this;
    }
    /** rotate to destination point location */    
    public ICurveR rot(IVecI center, IVecI axis, IVecI destPt){
	curve.rot(center,axis,destPt); return this;
    }
    
    
    /** alias of mul */
    public ICurveR scale(IDoubleI f){ return mul(f); }
    public ICurveR scale(double f){ return mul(f); }
    public ICurveR scale(IVecI center, IDoubleI f){ curve.scale(center,f); return this; }
    public ICurveR scale(IVecI center, double f){ curve.scale(center,f); return this; }
    
    /** scale only in 1 direction */
    public ICurveR scale1d(IVecI axis, double f){ curve.scale1d(axis,f); return this; }
    public ICurveR scale1d(IVecI axis, IDoubleI f){ curve.scale1d(axis,f); return this; }
    public ICurveR scale1d(IVecI center, IVecI axis, double f){
	curve.scale1d(center,axis,f); return this;
    }
    public ICurveR scale1d(IVecI center, IVecI axis, IDoubleI f){
	curve.scale1d(center,axis,f); return this;
    }
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public ICurveR ref(IVecI planeDir){ curve.ref(planeDir); return this; }
    public ICurveR ref(IVecI center, IVecI planeDir){ curve.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    public ICurveR mirror(IVecI planeDir){ return ref(planeDir); }
    public ICurveR mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    public ICurveR shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	curve.shear(sxy, syx, syz, szy, szx, sxz); return this;
    }
    public ICurveR shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	curve.shear(sxy, syx, syz, szy, szx, sxz); return this;
    }
    public ICurveR shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	curve.shear(center, sxy, syx, syz, szy, szx, sxz); return this;
    }
    public ICurveR shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	curve.shear(center, sxy, syx, syz, szy, szx, sxz); return this;
    }
    
    public ICurveR shearXY(double sxy, double syx){ curve.shearXY(sxy,syx); return this; }
    public ICurveR shearXY(IDoubleI sxy, IDoubleI syx){ curve.shearXY(sxy,syx); return this; }
    public ICurveR shearXY(IVecI center, double sxy, double syx){ curve.shearXY(center,sxy,syx); return this; }
    public ICurveR shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){ curve.shearXY(center,sxy,syx); return this; }
    
    public ICurveR shearYZ(double syz, double szy){ curve.shearYZ(syz,szy); return this; }
    public ICurveR shearYZ(IDoubleI syz, IDoubleI szy){ curve.shearYZ(syz,szy); return this; }
    public ICurveR shearYZ(IVecI center, double syz, double szy){ curve.shearYZ(center,syz,szy); return this; }
    public ICurveR shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){ curve.shearYZ(center,syz,szy); return this; }
    
    public ICurveR shearZX(double szx, double sxz){ curve.shearZX(szx,sxz); return this; }
    public ICurveR shearZX(IDoubleI szx, IDoubleI sxz){ curve.shearZX(szx,sxz); return this; }
    public ICurveR shearZX(IVecI center, double szx, double sxz){ curve.shearZX(center,szx,sxz); return this; }
    public ICurveR shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){ curve.shearZX(center,szx,sxz); return this; }
    
    
    /** translate is alias of add() */
    public ICurveR translate(double x, double y, double z){ return add(x,y,z); }
    public ICurveR translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ICurveR translate(IVecI v){ return add(v); }
    
    
    /** mv() is alias of add() */
    public ICurveR mv(double x, double y, double z){ return add(x,y,z); }
    public ICurveR mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ICurveR mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public ICurveR cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public ICurveR cp(double x, double y, double z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    public ICurveR cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    public ICurveR cp(IVecI v){ return dup().add(v); }
    
    
    public ICurveR transform(IMatrix3I mat){ curve.transform(mat); return this; }
    public ICurveR transform(IMatrix4I mat){ curve.transform(mat); return this; }
    public ICurveR transform(IVecI xvec, IVecI yvec, IVecI zvec){
	curve.transform(xvec,yvec,zvec); return this;
    }
    public ICurveR transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	curve.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /******************************************************************************
     * IObject methods
     ******************************************************************************/
    
    
    public ICurveR name(String nm){ super.name(nm); return this; }
    public ICurveR layer(ILayer l){ super.layer(l); return this; }

    public ICurveR hide(){ super.hide(); return this; }
    public ICurveR show(){ super.show(); return this; }
    
    
    public ICurveR clr(Color c){ super.clr(c); return this; }
    public ICurveR clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public ICurveR clr(int gray){ super.clr(gray); return this; }
    public ICurveR clr(float fgray){ super.clr(fgray); return this; }
    public ICurveR clr(double dgray){ super.clr(dgray); return this; }
    public ICurveR clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public ICurveR clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public ICurveR clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public ICurveR clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public ICurveR clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public ICurveR clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public ICurveR clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public ICurveR clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public ICurveR clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public ICurveR hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public ICurveR hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public ICurveR hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public ICurveR hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public ICurveR setColor(Color c){ super.setColor(c); return this; }
    public ICurveR setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public ICurveR setColor(int gray){ super.setColor(gray); return this; }
    public ICurveR setColor(float fgray){ super.setColor(fgray); return this; }
    public ICurveR setColor(double dgray){ super.setColor(dgray); return this; }
    public ICurveR setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public ICurveR setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public ICurveR setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public ICurveR setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public ICurveR setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public ICurveR setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public ICurveR setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public ICurveR setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public ICurveR setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public ICurveR setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public ICurveR setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public ICurveR setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public ICurveR setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    
}
