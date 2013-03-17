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
   Class of NURBS curve object.
   It contains ICurveGeo instance inside.
   
   @author Satoru Sugihara
*/
public class ICurve extends IGeometry implements ICurveI{
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
    /** this creates a line between a same point */
    public ICurve(IVecI pt){ this((IServerI)null, pt); }
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
    public ICurve(IEdge edge){ this((IServerI)null,edge); }
    
    //public ICurve(ICurveGeo crv){ curve = new ICurveGeo(crv); initCurve(null); }
    public ICurve(ICurveGeo crv){ this((IServerI)null, crv); }
    public ICurve(ICurveI crv){ this((IServerI)null, crv); }
    public ICurve(ICurve crv){
	super(crv);
        //curve = new ICurveGeo(crv.get());
	curve = crv.curve.dup();
	initCurve(crv.server); //?
	//setColor(crv.getColor());
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
    /** this creates a line between a same point */
    public ICurve(IServerI s, IVecI pt){
	super(s); curve = new ICurveGeo(pt); initCurve(s); 
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
    public ICurve(IServerI s, IEdge edge){ super(s); curve = new ICurveGeo(edge); initCurve(s); }
    
    
    //public ICurve(IServerI s, ICurveGeo crv){ super(s); curve = new ICurveGeo(crv); initCurve(s); }
    public ICurve(IServerI s, ICurveGeo crv){ super(s); curve = crv; initCurve(s); }
    public ICurve(IServerI s, ICurveI crv){ super(s); curve = crv.get(); initCurve(s); }
    
    public ICurve(IServerI s, ICurve crv){
	super(s,crv);
        //curve = new ICurveGeo(crv.get());
	curve = crv.curve.dup();
	initCurve(s); //?
	//setColor(crv.getColor());
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
	if(m.isNone()) return null;
	if(m.isGraphic3D()) return new ICurveGraphicGL(this); 
	return null;
    }
    
    synchronized public boolean isValid(){ if(curve==null){ return false; } return curve.isValid(); }
    
    synchronized public ICurveGeo get(){ return curve.get(); } //?
    
    synchronized public ICurve dup(){ return new ICurve(this); }    
    
    
    synchronized public IVec pt(IDoubleI u){ return curve.pt(u); }
    synchronized public IVec pt(double u){ return curve.pt(u); }
    //synchronized public void pt(double u, IVec retval){ curve.pt(u,retval); }
    
    synchronized public IVec tan(IDoubleI u){ return curve.tan(u); }
    synchronized public IVec tan(double u){ return curve.tan(u); }
    //synchronized public void tan(double u, IVec retval){ curve.tan(u,retval); }

    /** getting i-th control point */
    synchronized public IVec cp(int i){ return curve.cp(i).get(); }
    /** getting i-th control point */
    synchronized public IVecI cp(IIntegerI i){ return curve.cp(i); }
    
    synchronized public IVecI[] cps(){ return curve.cps(); }

    /** getting i-th edit point */
    synchronized public IVec ep(int i){ return curve.ep(i); }
    /** getting i-th edit point */
    synchronized public IVec ep(IIntegerI i){ return curve.ep(i); }

    /** start point of the curve */
    synchronized public IVec start(){ return curve.start(); }
    /** end point of the curve */
    synchronized public IVec end(){ return curve.end(); }
    /** start control point of the curve */
    synchronized public IVec startCP(){ return curve.startCP(); }
    /** end control point of the curve */
    synchronized public IVec endCP(){ return curve.endCP(); }
    
    
    /** parametrically mid point of a curve */
    synchronized public IVec mid(){ return curve.mid(); }
    
    /** returns center of geometry object */
    synchronized public IVec center(){ return curve.center(); }
    
    
    /** add control point at the end and rebuild the curve and change degree.
	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve addCP(IVecI pt, int newDegree){
	curve.addCP(pt,newDegree); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }

    /** add control point at the end and rebuild the curve.
	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve addCP(IVecI pt){
	curve.addCP(pt); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    
    /** add control point at i and rebuild the curve and change degree.
     	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve addCP(int index, IVecI pt, int newDegree){
	curve.addCP(index,pt,newDegree); updateGraphic(); if(server!=null){ server.updateState(); }
	return this; 
    }
    
    /** add control point at i and rebuild the curve.
     	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve addCP(int index, IVecI pt){
	curve.addCP(index,pt); updateGraphic(); if(server!=null){ server.updateState(); }
	return this; 
    }
    
    /** add control points at the end and rebuild the curve and change degree.
	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve addCP(IVecI[] pts, int newDegree){
	curve.addCP(pts,newDegree); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    
    /** add control points at the end and rebuild the curve.
	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve addCP(IVecI[] pts){
	curve.addCP(pts); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    
    /** add control points at i and rebuild the curve and newDegree.
     	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve addCP(int index, IVecI[] pts, int newDegree){
	curve.addCP(index,pts,newDegree); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    
    /** add control points at i and rebuild the curve.
     	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve addCP(int index, IVecI[] pts){
	curve.addCP(index,pts); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    
    /** alias of addCP(int,IVecI) */
    synchronized public ICurve insertCP(int index, IVecI pt){
	curve.insertCP(index,pt); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    /** alias of addCP(int,IVecI[]) */
    synchronized public ICurve insertCP(int index, IVecI[] pts){
	curve.insertCP(index,pts); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    
    /** removing control point at the end and rebuild the curve.
	note that a knots is rebuilt with default equal interval
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve removeCP(){
	curve.removeCP(); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    
    /** removing control point at i and rebuild the curve 
	note that a knots is rebuilt with default equal interval
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve removeCP(int index){
	curve.removeCP(index); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    
    /** removing control point from indexFrom to indexTo-1 and rebuild the curve 
	note that a knots is rebuilt with default equal interval
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ICurve removeCP(int indexFrom, int indexTo){
	curve.removeCP(indexFrom, indexTo); updateGraphic();
	if(server!=null){ server.updateState(); }
	return this;
    }
    
    /** close curve with the current control points.
	it changes total number of control points and knot vector dependng on the degree.
	new knot vector has equal default intervals destroying original variable intervals.
    */
    //synchronized public ICurve close();


    /** approximate invert projection from 3D location to interanl parameter U (closest point on curve) */
    synchronized public double u(IVecI pt){ return curve.u(pt); }
    synchronized public double u(ISwitchE r, IVecI pt){ return curve.u(r,pt); }
    synchronized public IDouble u(ISwitchR r, IVecI pt){ return curve.u(r,pt); }

    /** approximate invert projection from 2D location to interanl parameter U */
    synchronized public double u(IVec2I pt){ return curve.u(pt); }
    synchronized public double u(ISwitchE r, IVec2I pt){ return curve.u(r,pt); }
    synchronized public IDouble u(ISwitchR r, IVec2I pt){ return curve.u(r,pt); }
    
    
    /** find approximately closest point on a curve */
    synchronized public IVec closePt(IVecI pt){ return curve.closePt(pt); }
    
    /** find approximately closest point on a curve on 2D*/
    synchronized public IVec closePt(IVec2I pt){ return curve.closePt(pt); }

    /** distance to the closest point on a curve */
    synchronized public double dist(IVecI pt){ return curve.dist(pt); }
    /** distance to the closest point on a curve on 2D*/
    synchronized public double dist(IVec2I pt){ return curve.dist(pt); }
    
    
    
    synchronized public double knot(int i){ return curve.knot(i); }
    synchronized public IDouble knot(IIntegerI i){ return curve.knot(i); }
    
    synchronized public double[] knots(){ return curve.knots(); }
    synchronized public double[] knots(ISwitchE e){ return knots(); }
    synchronized public IDoubleI[] knots(ISwitchR r){ return curve.knots(r); }
    
    synchronized public int knotNum(){ return curve.knotNum(); }
    //synchronized public IInteger knotNumR(){ return curve.knotNumR(); }
    synchronized public int knotNum(ISwitchE e){ return knotNum(); }
    synchronized public IInteger knotNum(ISwitchR r){ return new IInteger(knotNum()); }
    
    synchronized public boolean isRational(){ return curve.isRational(); }
    synchronized public boolean isRational(ISwitchE e){ return isRational(); }
    synchronized public IBool isRational(ISwitchR r){ return new IBool(isRational()); }
    
    synchronized public int deg(){ return curve.deg(); }
    //synchronized public IInteger degR(){ return curve.degR(); }
    synchronized public int deg(ISwitchE e){ return deg(); }
    synchronized public IInteger deg(ISwitchR r){ return new IInteger(deg()); }
    
    synchronized public int num(){ return curve.num(); }
    //synchronized public IInteger numR(){ return curve.numR(); }
    synchronized public int num(ISwitchE e){ return num(); }
    synchronized public IInteger num(ISwitchR r){ return new IInteger(num()); }
    
    synchronized public int cpNum(){ return curve.cpNum(); }
    //synchronized public IInteger cpNumR(){ return curve.cpNumR(); }
    synchronized public int cpNum(ISwitchE e){ return cpNum(); }
    synchronized public IInteger cpNum(ISwitchR r){ return new IInteger(cpNum()); }
    
    synchronized public int epNum(){ return curve.epNum(); }
    //synchronized public IInteger epNumR(){ return curve.epNumR(); }
    synchronized public int epNum(ISwitchE e){ return epNum(); }
    synchronized public IInteger epNum(ISwitchR r){ return new IInteger(epNum()); }
    
    synchronized public double len(){ return curve.len(); }
    //synchronized public IDouble lenR(){ return curve.lenR(); }
    synchronized public double len(ISwitchE e){ return len(); }
    synchronized public IDouble len(ISwitchR r){ return new IDouble(len()); }
    
    synchronized public double u(int epIdx, double epFraction){
	return curve.u(epIdx,epFraction);
    }
    synchronized public IDouble u(IInteger epIdx, IDouble epFraction){
	return curve.u(epIdx,epFraction);
    }
    
    synchronized public double ustart(){ return curve.ustart(); }
    synchronized public double uend(){ return curve.uend(); }
    //synchronized public IDouble ustartR(){ return curve.ustartR(); }
    //synchronized public IDouble uendR(){ return curve.uendR(); }
    synchronized public double ustart(ISwitchE e){ return ustart(); }
    synchronized public double uend(ISwitchE e){ return uend(); }
    synchronized public IDouble ustart(ISwitchR r){ return new IDouble(ustart()); }
    synchronized public IDouble uend(ISwitchR r){ return new IDouble(uend()); }
    
    
    synchronized public boolean isClosed(){ return curve.isClosed(); }
    //synchronized public IBool isClosedR(){ return curve.isClosedR(); }
    synchronized public boolean isClosed(ISwitchE e){ return isClosed(); }
    synchronized public IBool isClosed(ISwitchR r){ return new IBool(isClosed()); }

    /** reverse self curve ; not creating a new object
     */
    synchronized public ICurve rev(){ curve.rev(); return this; }
    
    synchronized public ICurve revU(){ return rev(); }
    synchronized public ICurve flipU(){ return rev(); }
    
    
    
    /** check if the input point is inside a closed curve. if not closed, it supposes closed by connecting the start and end point */
    public boolean isInside2d(IVecI pt, IVecI projectionDir){ return curve.isInside2d(pt,projectionDir); }
    
    
    /** check if the input point is inside a closed curve. if not closed, it supposes closed by connecting the start and end point */
    public boolean isInside2d(IVecI pt){ return curve.isInside2d(pt); }
    
    
    
    /******************************************************************************
     * transformation methods; API of ITransformable interface
     ******************************************************************************/
    
    synchronized public ICurve add(double x, double y, double z){
	curve.add(x,y,z); return this;
    }
    synchronized public ICurve add(IDoubleI x, IDoubleI y, IDoubleI z){
	curve.add(x,y,z); return this;
    }
    synchronized public ICurve add(IVecI v){ curve.add(v); return this; }
    synchronized public ICurve sub(double x, double y, double z){
	curve.sub(x,y,z); return this;
    }
    synchronized public ICurve sub(IDoubleI x, IDoubleI y, IDoubleI z){
	curve.sub(x,y,z); return this;
    }
    synchronized public ICurve sub(IVecI v){ curve.sub(v); return this; }
    synchronized public ICurve mul(IDoubleI v){ curve.mul(v); return this; }
    synchronized public ICurve mul(double v){ curve.mul(v); return this; }
    synchronized public ICurve div(IDoubleI v){ curve.div(v); return this; }
    synchronized public ICurve div(double v){ curve.div(v); return this; }
    
    synchronized public ICurve neg(){ curve.neg(); return this; }
    synchronized public ICurve flip(){ curve.neg(); return this; }
    
    
    /** scale add */
    synchronized public ICurve add(IVecI v, double f){ curve.add(v,f); return this; }
    synchronized public ICurve add(IVecI v, IDoubleI f){ curve.add(v,f); return this; }
    
    /** scale add alias */
    synchronized public ICurve add(double f, IVecI v){ return add(v,f); }
    synchronized public ICurve add(IDoubleI f, IVecI v){ return add(v,f); }
    
    synchronized public ICurve rot(IDoubleI angle){ curve.rot(angle); return this; }
    synchronized public ICurve rot(double angle){ curve.rot(angle); return this; }
    
    synchronized public ICurve rot(IVecI axis, IDoubleI angle){ curve.rot(axis,angle); return this; }
    synchronized public ICurve rot(IVecI axis, double angle){ curve.rot(axis,angle); return this; }
    synchronized public ICurve rot(IVecI center, IVecI axis, IDoubleI angle){
	curve.rot(center,axis,angle); return this;
    }
    synchronized public ICurve rot(IVecI center, IVecI axis, double angle){
	curve.rot(center,axis,angle); return this;
    }
    
    /** rotate to destination direction vector */
    synchronized public ICurve rot(IVecI axis, IVecI destDir){
	curve.rot(axis,destDir); return this;
    }
    /** rotate to destination point location */    
    synchronized public ICurve rot(IVecI center, IVecI axis, IVecI destPt){
	curve.rot(center,axis,destPt); return this;
    }
    
    synchronized public ICurve rot2(IDoubleI angle){ return rot(angle); }
    synchronized public ICurve rot2(double angle){ return rot(angle); }
    synchronized public ICurve rot2(IVecI center, IDoubleI angle){ curve.rot2(center,angle); return this; }
    synchronized public ICurve rot2(IVecI center, double angle){ curve.rot2(center,angle); return this; }
    /** rotation on xy-plane to destination direction vector */
    synchronized public ICurve rot2(IVecI destDir){ curve.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */    
    synchronized public ICurve rot2(IVecI center, IVecI destPt){ curve.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    synchronized public ICurve scale(IDoubleI f){ return mul(f); }
    synchronized public ICurve scale(double f){ return mul(f); }
    synchronized public ICurve scale(IVecI center, IDoubleI f){ curve.scale(center,f); return this; }
    synchronized public ICurve scale(IVecI center, double f){ curve.scale(center,f); return this; }
    
    /** scale only in 1 direction */
    synchronized public ICurve scale1d(IVecI axis, double f){ curve.scale1d(axis,f); return this; }
    synchronized public ICurve scale1d(IVecI axis, IDoubleI f){ curve.scale1d(axis,f); return this; }
    synchronized public ICurve scale1d(IVecI center, IVecI axis, double f){
	curve.scale1d(center,axis,f); return this;
    }
    synchronized public ICurve scale1d(IVecI center, IVecI axis, IDoubleI f){
	curve.scale1d(center,axis,f); return this;
    }
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    synchronized public ICurve ref(IVecI planeDir){ curve.ref(planeDir); return this; }
    synchronized public ICurve ref(IVecI center, IVecI planeDir){ curve.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    synchronized public ICurve mirror(IVecI planeDir){ return ref(planeDir); }
    synchronized public ICurve mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    synchronized public ICurve shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	curve.shear(sxy, syx, syz, szy, szx, sxz); return this;
    }
    synchronized public ICurve shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	curve.shear(sxy, syx, syz, szy, szx, sxz); return this;
    }
    synchronized public ICurve shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	curve.shear(center, sxy, syx, syz, szy, szx, sxz); return this;
    }
    synchronized public ICurve shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	curve.shear(center, sxy, syx, syz, szy, szx, sxz); return this;
    }
    
    synchronized public ICurve shearXY(double sxy, double syx){ curve.shearXY(sxy,syx); return this; }
    synchronized public ICurve shearXY(IDoubleI sxy, IDoubleI syx){ curve.shearXY(sxy,syx); return this; }
    synchronized public ICurve shearXY(IVecI center, double sxy, double syx){ curve.shearXY(center,sxy,syx); return this; }
    synchronized public ICurve shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){ curve.shearXY(center,sxy,syx); return this; }
    
    synchronized public ICurve shearYZ(double syz, double szy){ curve.shearYZ(syz,szy); return this; }
    synchronized public ICurve shearYZ(IDoubleI syz, IDoubleI szy){ curve.shearYZ(syz,szy); return this; }
    synchronized public ICurve shearYZ(IVecI center, double syz, double szy){ curve.shearYZ(center,syz,szy); return this; }
    synchronized public ICurve shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){ curve.shearYZ(center,syz,szy); return this; }
    
    synchronized public ICurve shearZX(double szx, double sxz){ curve.shearZX(szx,sxz); return this; }
    synchronized public ICurve shearZX(IDoubleI szx, IDoubleI sxz){ curve.shearZX(szx,sxz); return this; }
    synchronized public ICurve shearZX(IVecI center, double szx, double sxz){ curve.shearZX(center,szx,sxz); return this; }
    synchronized public ICurve shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){ curve.shearZX(center,szx,sxz); return this; }
    
    
    /** translate is alias of add() */
    synchronized public ICurve translate(double x, double y, double z){ return add(x,y,z); }
    synchronized public ICurve translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    synchronized public ICurve translate(IVecI v){ return add(v); }
    
    /** mv() is alias of add() */
    synchronized public ICurve mv(double x, double y, double z){ return add(x,y,z); }
    synchronized public ICurve mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    synchronized public ICurve mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    synchronized public ICurve cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    synchronized public ICurve cp(double x, double y, double z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    synchronized public ICurve cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    synchronized public ICurve cp(IVecI v){ return dup().add(v); }
    
    
    synchronized public ICurve transform(IMatrix3I mat){ curve.transform(mat); return this; }
    synchronized public ICurve transform(IMatrix4I mat){ curve.transform(mat); return this; }
    synchronized public ICurve transform(IVecI xvec, IVecI yvec, IVecI zvec){
	curve.transform(xvec,yvec,zvec); return this;
    }
    synchronized public ICurve transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	curve.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /******************************************************************************
     * IObject methods
     ******************************************************************************/

    
    synchronized public ICurve name(String nm){ super.name(nm); return this; }
    synchronized public ICurve layer(ILayer l){ super.layer(l); return this; }
    synchronized public ICurve layer(String l){ super.layer(l); return this; }

    synchronized public ICurve attr(IAttribute at){ super.attr(at); return this; }
    
    
    synchronized public ICurve hide(){ super.hide(); return this; }
    synchronized public ICurve show(){ super.show(); return this; }
    
    synchronized public ICurve clr(IColor c){ super.clr(c); return this; }
    synchronized public ICurve clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public ICurve clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    synchronized public ICurve clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    synchronized public ICurve clr(Color c){ super.clr(c); return this; }
    synchronized public ICurve clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public ICurve clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    synchronized public ICurve clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    synchronized public ICurve clr(int gray){ super.clr(gray); return this; }
    synchronized public ICurve clr(float fgray){ super.clr(fgray); return this; }
    synchronized public ICurve clr(double dgray){ super.clr(dgray); return this; }
    synchronized public ICurve clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    synchronized public ICurve clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    synchronized public ICurve clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    synchronized public ICurve clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    synchronized public ICurve clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    synchronized public ICurve clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    synchronized public ICurve clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    synchronized public ICurve clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    synchronized public ICurve clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    synchronized public ICurve hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    synchronized public ICurve hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    synchronized public ICurve hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    synchronized public ICurve hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    synchronized public ICurve setColor(IColor c){ super.setColor(c); return this; }
    synchronized public ICurve setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public ICurve setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    synchronized public ICurve setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    synchronized public ICurve setColor(Color c){ super.setColor(c); return this; }
    synchronized public ICurve setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public ICurve setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    synchronized public ICurve setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    synchronized public ICurve setColor(int gray){ super.setColor(gray); return this; }
    synchronized public ICurve setColor(float fgray){ super.setColor(fgray); return this; }
    synchronized public ICurve setColor(double dgray){ super.setColor(dgray); return this; }
    synchronized public ICurve setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    synchronized public ICurve setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    synchronized public ICurve setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    synchronized public ICurve setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    synchronized public ICurve setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    synchronized public ICurve setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    synchronized public ICurve setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    synchronized public ICurve setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    synchronized public ICurve setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    synchronized public ICurve setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public ICurve setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public ICurve setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    synchronized public ICurve setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    synchronized public ICurve weight(double w){ super.weight(w); return this; }
    synchronized public ICurve weight(float w){ super.weight(w); return this; }
    
}
