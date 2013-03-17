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
   Class of NURBS surface object.
   It contains ISurfaceGeo instance inside.
   
   @author Satoru Sugihara
*/
public class ISurface extends IGeometry implements ISurfaceI{
    public ISurfaceGeo surface;
    //public ISurfaceI surface; // public?
    
    public ISurface(){ /*surface = new ISurfaceGeo();*/ }
    
    public ISurface(IVecI[][] cpts, int udegree, int vdegree, double[] uknots, double[] vknots,
		    double ustart, double uend, double vstart, double vend){
        surface = new ISurfaceGeo(cpts,udegree,vdegree,uknots,vknots,ustart,uend,vstart,vend);
	initSurface(null); 
    }
    
    public ISurface(IVecI[][] cpts, int udegree, int vdegree, double[] uknots, double[] vknots){
	surface = new ISurfaceGeo(cpts,udegree,vdegree,uknots,vknots);
	initSurface(null); 
    }
    
    public ISurface(IVecI[][] cpts, int udegree, int vdegree){
        surface = new ISurfaceGeo(cpts,udegree,vdegree);
	initSurface(null); 
    }
    
    public ISurface(IVecI[][] cpts){
        surface = new ISurfaceGeo(cpts); initSurface(null); 
    }
    
    public ISurface(IVecI[][] cpts, int udegree, int vdegree, boolean closeU, boolean closeV){
	surface = new ISurfaceGeo(cpts,udegree,vdegree,closeU,closeV); initSurface(null); 
    }
    
    public ISurface(IVecI[][] cpts, int udegree, int vdegree, boolean closeU, double[] vk){
	surface = new ISurfaceGeo(cpts,udegree,vdegree,closeU,vk); initSurface(null); 
    }
    
    public ISurface(IVecI[][] cpts, int udegree, int vdegree, double[] uk, boolean closeV){
	surface = new ISurfaceGeo(cpts,udegree,vdegree,uk,closeV); initSurface(null); 
    }
    
    public ISurface(IVecI[][] cpts, boolean closeU, boolean closeV){
	surface = new ISurfaceGeo(cpts,closeU,closeV); initSurface(null);
    }
    
    public ISurface(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4){
	surface = new ISurfaceGeo(pt1,pt2,pt3,pt4); initSurface(null); 
    }
    
    /** triangle */
    public ISurface(IVecI pt1, IVecI pt2, IVecI pt3){
	surface = new ISurfaceGeo(pt1,pt2,pt3,pt1); initSurface(null); 
    }
    
    public ISurface(double x1, double y1, double z1, double x2, double y2, double z2,
		    double x3, double y3, double z3, double x4, double y4, double z4){
	surface = new ISurfaceGeo(x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4);
	initSurface(null); 
    }
    
    public ISurface(double x1, double y1, double z1, double x2, double y2, double z2,
		    double x3, double y3, double z3){
	surface = new ISurfaceGeo(x1,y1,z1,x2,y2,z2,x3,y3,z3);
	initSurface(null); 
    }
    
    public ISurface(double[][][] xyzValues){
	surface = new ISurfaceGeo(xyzValues); initSurface(null); 
    }
    
    public ISurface(double[][][] xyzValues, int udeg, int vdeg){
	surface = new ISurfaceGeo(xyzValues,udeg,vdeg); initSurface(null); 
    }
    
    public ISurface(double[][][] xyzValues, boolean closeU, boolean closeV){
	surface = new ISurfaceGeo(xyzValues,closeU,closeV); initSurface(null); 
    }
    
    public ISurface(double[][][] xyzValues, int udeg, int vdeg, boolean closeU, boolean closeV){
	surface = new ISurfaceGeo(xyzValues,udeg,vdeg,closeU,closeV); initSurface(null); 
    }
    
    public ISurface(ICurveI trimCurve){
	surface = new ISurfaceGeo(trimCurve); initSurface(null);
    }
    public ISurface(ICurveI trimCurve, ICurveI[] innerTrimCurves){
	surface = new ISurfaceGeo(trimCurve, innerTrimCurves);
    }
    public ISurface(ICurveI trimCurve, ICurveI innerTrimCurve){
	surface = new ISurfaceGeo(trimCurve, innerTrimCurve);
    }
    public ISurface(ICurveI[] trimCurves){
	surface = new ISurfaceGeo(trimCurves); initSurface(null);
    }
    public ISurface(IVecI[] trimCrvPts){
	surface = new ISurfaceGeo(trimCrvPts); initSurface(null);
    }
    public ISurface(IVecI[] trimCrvPts, int trimCrvDeg){
	surface = new ISurfaceGeo(trimCrvPts, trimCrvDeg); initSurface(null);
    }
    public ISurface(IVecI[] trimCrvPts, int trimCrvDeg, double[] trimCrvKnots){
	surface = new ISurfaceGeo(trimCrvPts, trimCrvDeg, trimCrvKnots); initSurface(null);
    }
    
    
    //public ISurface(ISurfaceGeo srf){ surface = new ISurfaceGeo(srf); initSurface(null); }
    public ISurface(ISurfaceGeo srf){ surface = srf; initSurface(null); }
    
    
    public ISurface(ISurfaceI srf){ surface = srf.get(); initSurface(null); }
    
    public ISurface(IServerI s){ super(s); /*surface = new ISurfaceGeo();*/ /*initSurface(s);*/ }
    
    public ISurface(IServerI s,
		    IVecI[][] cpts, int udegree, int vdegree,
		    double[] uknots, double[] vknots,
		    double ustart, double uend,
		    double vstart, double vend){
	super(s);
        surface = new ISurfaceGeo(cpts,udegree,vdegree,uknots,vknots,ustart,uend,vstart,vend);
	initSurface(s); 
    }
    
    public ISurface(IServerI s, IVecI[][] cpts, int udegree, int vdegree,
		    double[] uknots, double[] vknots){
	super(s);
	surface = new ISurfaceGeo(cpts,udegree,vdegree,uknots,vknots);
	initSurface(s); 
    }
    
    public ISurface(IServerI s, IVecI[][] cpts, int udegree, int vdegree){
	super(s); surface = new ISurfaceGeo(cpts,udegree,vdegree); initSurface(s); 
    }
    
    public ISurface(IServerI s, IVecI[][] cpts){
	super(s); surface = new ISurfaceGeo(cpts); initSurface(s); 
    }
    
    public ISurface(IServerI s, IVecI[][] cpts, int udegree, int vdegree, boolean closeU, boolean closeV){
	super(s); surface = new ISurfaceGeo(cpts,udegree,vdegree,closeU,closeV); initSurface(s); 
    }
    
    public ISurface(IServerI s, IVecI[][] cpts, int udegree, int vdegree, boolean closeU, double[] vk){
	super(s); surface = new ISurfaceGeo(cpts,udegree,vdegree,closeU,vk); initSurface(s); 
    }
    
    public ISurface(IServerI s, IVecI[][] cpts, int udegree, int vdegree, double[] uk, boolean closeV){
	super(s); surface = new ISurfaceGeo(cpts,udegree,vdegree,uk,closeV); initSurface(s); 
    }
    
    public ISurface(IServerI s, IVecI[][] cpts, boolean closeU, boolean closeV){
	super(s); surface = new ISurfaceGeo(cpts,closeU,closeV); initSurface(s);
    }
    
    public ISurface(IServerI s, IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4){
	super(s); surface = new ISurfaceGeo(pt1,pt2,pt3,pt4); initSurface(s); 
    }
    
    public ISurface(IServerI s, IVecI pt1, IVecI pt2, IVecI pt3){
	super(s); surface = new ISurfaceGeo(pt1,pt2,pt3,pt1); initSurface(s); 
    }
    
    public ISurface(IServerI s,double x1,double y1,double z1,double x2,double y2,double z2,
		    double x3,double y3,double z3,double x4,double y4,double z4){
	super(s);
	surface = new ISurfaceGeo(x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4);
	initSurface(s); 
    }
    
    public ISurface(IServerI s,double x1,double y1,double z1,double x2,double y2,double z2,
		    double x3,double y3,double z3){
	super(s);
	surface = new ISurfaceGeo(x1,y1,z1,x2,y2,z2,x3,y3,z3);
	initSurface(s); 
    }
    
    public ISurface(IServerI s, double[][][] xyzValues){
	super(s); surface = new ISurfaceGeo(xyzValues); initSurface(s); 
    }
    
    public ISurface(IServerI s, double[][][] xyzValues, int udeg, int vdeg){
	super(s); surface = new ISurfaceGeo(xyzValues,udeg,vdeg); initSurface(s); 
    }
    
    public ISurface(IServerI s, double[][][] xyzValues, boolean closeU, boolean closeV){
	super(s); surface = new ISurfaceGeo(xyzValues,closeU,closeV); initSurface(s); 
    }
    
    public ISurface(IServerI s, double[][][] xyzValues, int udeg, int vdeg, boolean closeU, boolean closeV){
	super(s); surface = new ISurfaceGeo(xyzValues,udeg,vdeg,closeU,closeV); initSurface(s); 
    }
    
    public ISurface(IServerI s, ICurveI trimCurve){
	super(s); surface = new ISurfaceGeo(trimCurve); initSurface(s);
    }
    public ISurface(IServerI s, ICurveI trimCurve, ICurveI[] innerTrimCurves){
	super(s); surface = new ISurfaceGeo(trimCurve, innerTrimCurves);
    }
    public ISurface(IServerI s, ICurveI trimCurve, ICurveI innerTrimCurve){
	super(s); surface = new ISurfaceGeo(trimCurve, innerTrimCurve);
    }
    public ISurface(IServerI s, ICurveI[] trimCurves){
	super(s); surface = new ISurfaceGeo(trimCurves); initSurface(s);
    }
    public ISurface(IServerI s, IVecI[] trimCrvPts){
	super(s); surface = new ISurfaceGeo(trimCrvPts); initSurface(s);
    }
    public ISurface(IServerI s, IVecI[] trimCrvPts, int trimCrvDeg){
	super(s); surface = new ISurfaceGeo(trimCrvPts, trimCrvDeg); initSurface(s);
    }
    public ISurface(IServerI s, IVecI[] trimCrvPts, int trimCrvDeg, double[] trimCrvKnots){
	super(s); surface = new ISurfaceGeo(trimCrvPts, trimCrvDeg, trimCrvKnots); initSurface(s);
    }
    
    
    //public ISurface(IServerI s, ISurfaceGeo srf){ super(s); surface = new ISurfaceGeo(srf); initSurface(s); }
    public ISurface(IServerI s, ISurfaceGeo srf){ super(s); surface = srf; initSurface(s); }
    
    public ISurface(IServerI s, ISurfaceI srf){ super(s); surface = srf.get(); initSurface(s); }
    
    public ISurface(ISurface srf){
	super(srf);
	//surface = new ISurfaceGeo(srf.surface.get());
	surface = srf.surface.dup();
	initSurface(srf.server);
	//setColor(srf.getColor());
    }
    
    public ISurface(IServerI s, ISurface srf){
	super(s,srf);
	//surface = new ISurfaceGeo(srf.surface.get());
	surface = srf.surface.dup();
	initSurface(s);
	//setColor(srf.getColor());
    }
    
    public void initSurface(IServerI s){
	if(surface instanceof ISurfaceGeo){
	    parameter = (ISurfaceGeo)surface;
	}
	if(graphics==null) initGraphic(s);
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
	if(m.isNone()) return null;
        //if(m.isGL()){
	if(m.isGraphic3D()){
	    return new ISurfaceGraphicGL(this);
	    //if(m.isFill()) return new ISurfaceGraphicGL(this);
	    //if(m.isFill()) return new ISurfaceGraphicFillGL(this);
	    //else if(m.isWireframe()) return new ISurfaceGraphicWireframeGL(this);
        }
        return null;
    }

    
    synchronized public boolean isValid(){ if(surface==null){ return false; } return surface.isValid(); }
    
    synchronized public ISurfaceGeo get(){ return surface.get(); } // ?
    
    synchronized public ISurface dup(){ return new ISurface(this); }    
    
    synchronized public IVec pt(IVec2I v){ return surface.pt(v); }
    synchronized public IVec pt(IDoubleI u, IDoubleI v){ return surface.pt(u,v); }
    synchronized public IVec pt(double u, double v){ return surface.pt(u,v); }
    
    //synchronized public void pt(double u, double v, IVec retval){ surface.pt(u,v,retval); }
    
    /**
       @param u u coordinates in uv parameter space
       @param v v coordinates in uv parameter space
       @param n length in normal direction in 3D space
    */
    synchronized public IVec pt(double u, double v, double n){ return surface.pt(u,v,n); }
    synchronized public IVec pt(IDoubleI u, IDoubleI v, IDoubleI n){ return surface.pt(u,v,n); }
    synchronized public IVec pt(IVecI v){ return surface.pt(v); }
    
    
    synchronized public IVec utan(IVec2I v){ return surface.utan(v); }
    synchronized public IVec utan(IDoubleI u, IDoubleI v){ return surface.utan(u,v); }
    synchronized public IVec utan(double u, double v){ return surface.utan(u,v); }
    //synchronized public void utan(double u, double v, IVec retval){ surface.utan(u,v,retval); }

    synchronized public IVec vtan(IVec2I v){ return surface.vtan(v); }
    synchronized public IVec vtan(IDoubleI u, IDoubleI v){ return surface.vtan(u,v); }
    synchronized public IVec vtan(double u, double v){ return surface.vtan(u,v); }
    //synchronized public void vtan(double u, double v, IVec retval){ surface.vtan(u,v,retval); }
    
    /** alias of nml */
    synchronized public IVec normal(IVec2I v){ return surface.normal(v); }
    /** alias of nml */
    synchronized public IVec normal(IDoubleI u, IDoubleI v){ return surface.normal(u,v); }
    /** alias of nml */
    synchronized public IVec normal(double u, double v){ return surface.normal(u,v); }
    //synchronized public void normal(double u, double v, IVec retval){ surface.normal(u,v,retval); }
    /** alias of nml */
    synchronized public IVec nrml(IVec2I v){ return surface.nrml(v); }
    /** alias of nml */
    synchronized public IVec nrml(IDoubleI u, IDoubleI v){ return surface.nrml(u,v); }
    /** alias of nml */
    synchronized public IVec nrml(double u, double v){ return surface.nrml(u,v); }

    /** getting normal vector */
    synchronized public IVec nml(IVec2I v){ return surface.nml(v); }
    /** getting normal vector */
    synchronized public IVec nml(IDoubleI u, IDoubleI v){ return surface.nml(u,v); }
    /** getting normal vector */
    synchronized public IVec nml(double u, double v){ return surface.nml(u,v); }
    
    
    /** getting control point at i and j */
    synchronized public IVec cp(int i, int j){ return surface.cp(i,j).get(); }
    /** getting control point at i and j */
    synchronized public IVecI cp(IIntegerI i, IIntegerI j){ return surface.cp(i,j); }

    synchronized public IVecI[][] cps(){ return surface.cps(); }
    
    /** getting edit point at i and j */
    synchronized public IVec ep(int i, int j){ return surface.ep(i,j); }
    /** getting edit point at i and j */
    synchronized public IVec ep(IIntegerI i, IIntegerI j){ return surface.ep(i,j); }
    
    synchronized public IVec corner(int i, int j){ return surface.corner(i,j); }
    synchronized public IVec corner(IIntegerI i, IIntegerI j){ return surface.corner(i,j); }
    synchronized public IVec cornerCP(int i, int j){ return surface.cornerCP(i,j); }
    synchronized public IVecI cornerCP(IIntegerI i, IIntegerI j){ return surface.cornerCP(i,j); }



    /** add u control point at the end and rebuild the surface.
	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ISurface addUCP(IVecI[] pts){
	surface.addUCP(pts); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    
    /** add v control point at the end and rebuild the surface.
	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    synchronized public ISurface addVCP(IVecI[] pts){
	surface.addVCP(pts); updateGraphic(); if(server!=null){ server.updateState(); }
	return this;
    }
    
    
    /** mid in UV parameter (u=0.5, v=0.5) point on a surface */
    synchronized public IVec mid(){ return surface.mid(); }
    
    /** returns center of geometry object */
    synchronized public IVec center(){ return surface.center(); }
    
    
    
    /** approximate invert projection from 3D location to interanl UV parameter (closest point on surface) */
    synchronized public IVec2 uv(IVecI pt){ return surface.uv(pt); }
    /** approximate invert projection from 2D location to interanl UV parameter (closest point on surface) */
    synchronized public IVec2 uv(IVec2I pt){ return surface.uv(pt); }
    
    
    /** find approximately closest point on a surface */
    synchronized public IVec closePt(IVecI pt){ return surface.closePt(pt); }
    /** find approximately closest point on a surface on 2D */
    synchronized public IVec closePt(IVec2I pt){return surface.closePt(pt); }
    
    /** distance to the closest point on a surface */
    synchronized public double dist(IVecI pt){ return surface.dist(pt); }
    /** distance to the closest point on a surface on 2D*/
    synchronized public double dist(IVec2I pt){ return surface.dist(pt); }
    
    
    
    synchronized public double uknot(int i){ return surface.uknot(i); }
    synchronized public IDouble uknot(IIntegerI i){ return surface.uknot(i); }
    synchronized public double vknot(int i){ return surface.vknot(i); }
    synchronized public IDouble vknot(IIntegerI i){ return surface.vknot(i); }
    
    synchronized public double[] uknots(){ return surface.uknots(); }
    synchronized public double[] vknots(){ return surface.vknots(); }
    synchronized public double[] uknots(ISwitchE e){ return uknots(); }
    synchronized public double[] vknots(ISwitchE e){ return vknots(); }
    synchronized public IDoubleI[] uknots(ISwitchR r){ return surface.uknots(r); }
    synchronized public IDoubleI[] vknots(ISwitchR r){ return surface.vknots(r); }
    
    synchronized public int uknotNum(){ return surface.uknotNum(); }
    synchronized public int vknotNum(){ return surface.vknotNum(); }
    //synchronized public IInteger uknotNumR(){ return surface.uknotNumR(); }
    //synchronized public IInteger vknotNumR(){ return surface.vknotNumR(); }
    synchronized public int uknotNum(ISwitchE e){ return uknotNum(); }
    synchronized public int vknotNum(ISwitchE e){ return vknotNum(); }
    synchronized public IInteger uknotNum(ISwitchR r){ return new IInteger(uknotNum()); }
    synchronized public IInteger vknotNum(ISwitchR r){ return new IInteger(vknotNum()); }
    
    synchronized public boolean isRational(){ return surface.isRational(); }
    synchronized public boolean isRational(ISwitchE e){ return surface.isRational(); }
    synchronized public IBool isRational(ISwitchR r){ return new IBool(isRational()); }
    
    
    synchronized public int udeg(){ return surface.udeg(); }
    synchronized public int vdeg(){ return surface.vdeg(); }
    //synchronized public IInteger udegR(){ return surface.udegR(); }
    //synchronized public IInteger vdegR(){ return surface.vdegR(); }
    synchronized public int udeg(ISwitchE e){ return udeg(); }
    synchronized public int vdeg(ISwitchE e){ return vdeg(); }
    synchronized public IInteger udeg(ISwitchR r){ return new IInteger(udeg()); }
    synchronized public IInteger vdeg(ISwitchR r){ return new IInteger(vdeg()); }
    
    synchronized public int unum(){ return surface.unum(); }
    synchronized public int vnum(){ return surface.vnum(); }
    //synchronized public IInteger unumR(){ return surface.unumR(); }
    //synchronized public IInteger vnumR(){ return surface.vnumR(); }
    synchronized public int unum(ISwitchE e){ return unum(); }
    synchronized public int vnum(ISwitchE e){ return vnum(); }
    synchronized public IInteger unum(ISwitchR r){ return new IInteger(unum()); }
    synchronized public IInteger vnum(ISwitchR r){ return new IInteger(vnum()); }
    
    synchronized public int ucpNum(){ return surface.ucpNum(); } // equals to unum()
    synchronized public int vcpNum(){ return surface.vcpNum(); } // equals to vnum()
    //synchronized public IInteger ucpNumR(){ return surface.ucpNumR(); } // equals to unum()
    //synchronized public IInteger vcpNumR(){ return surface.vcpNumR(); } // equals to vnum()
    synchronized public int ucpNum(ISwitchE e){ return ucpNum(); }
    synchronized public int vcpNum(ISwitchE e){ return vcpNum(); }
    synchronized public IInteger ucpNum(ISwitchR r){ return new IInteger(ucpNum()); }
    synchronized public IInteger vcpNum(ISwitchR r){ return new IInteger(vcpNum()); }
    
    synchronized public int uepNum(){ return surface.uepNum(); }
    synchronized public int vepNum(){ return surface.vepNum(); }
    //synchronized public IInteger uepNumR(){ return surface.uepNumR(); }
    //synchronized public IInteger vepNumR(){ return surface.vepNumR(); }
    synchronized public int uepNum(ISwitchE e){ return uepNum(); }
    synchronized public int vepNum(ISwitchE e){ return vepNum(); }
    synchronized public IInteger uepNum(ISwitchR r){ return new IInteger(uepNum()); }
    synchronized public IInteger vepNum(ISwitchR r){ return new IInteger(vepNum()); }
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    synchronized public double u(int epIdx, double epFraction){ return surface.u(epIdx,epFraction); }
    synchronized public IDouble u(IIntegerI epIdx, IDoubleI epFraction){ return surface.u(epIdx,epFraction); }
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    synchronized public double v(int epIdx, double epFraction){ return surface.v(epIdx,epFraction); }
    synchronized public IDouble v(IIntegerI epIdx, IDoubleI epFraction){ return surface.v(epIdx,epFraction); }
    
    synchronized public double ustart(){ return surface.ustart(); }
    synchronized public double uend(){ return surface.uend(); }
    synchronized public double vstart(){ return surface.vstart(); }
    synchronized public double vend(){ return surface.vend(); }
    //synchronized public IDouble ustartR(){ return surface.ustartR(); }
    //synchronized public IDouble uendR(){ return surface.uendR(); }
    //synchronized public IDouble vstartR(){ return surface.vstartR(); }
    //synchronized public IDouble vendR(){ return surface.vendR(); }
    synchronized public double ustart(ISwitchE e){ return ustart(); }
    synchronized public double uend(ISwitchE e){ return uend(); }
    synchronized public double vstart(ISwitchE e){ return vstart(); }
    synchronized public double vend(ISwitchE e){ return vend(); }
    synchronized public IDouble ustart(ISwitchR r){ return new IDouble(ustart()); }
    synchronized public IDouble uend(ISwitchR r){ return new IDouble(uend()); }
    synchronized public IDouble vstart(ISwitchR r){ return new IDouble(vstart()); }
    synchronized public IDouble vend(ISwitchR r){ return new IDouble(vend()); }

    /** reverse U parameter internally without creating a new instance */
    synchronized public ISurface revU(){ surface.revU(); return this; }
    /** reverse V parameter internally without creating a new instance */
    synchronized public ISurface revV(){ surface.revV(); return this; }
    /** reverse U and V parameter internally without creating a new instance */
    synchronized public ISurface revUV(){ surface.revUV(); return this; }
    /** reverse normal direction by reversing V direction (UV and normal is dependent */
    synchronized public ISurface revN(){ surface.revN(); return this; }
    /** alias of revU() */
    synchronized public ISurface flipU(){ return revU(); }
    /** alias of revV() */
    synchronized public ISurface flipV(){ return revV(); }
    /** alias of revUV() */
    synchronized public ISurface flipUV(){ return revUV(); }
    /** alias of flipN() */
    synchronized public ISurface flipN(){ return revN(); }
    
    /** swap U and V parameter */
    synchronized public ISurface swapUV(){ surface.swapUV(); return this; }
    
    
    synchronized public boolean hasTrim(){ return surface.hasTrim(); }
    //synchronized public IBool hasTrimR(){ return surface.hasTrimR(); }
    synchronized public boolean hasTrim(ISwitchE e){ return hasTrim(); }
    synchronized public IBool hasTrim(ISwitchR r){ return new IBool(hasTrim()); }
    
    synchronized public boolean hasInnerTrim(){ return surface.hasInnerTrim(); }
    //synchronized public IBool hasInnerTrimR(){ return surface.hasInnerTrimR(); }
    synchronized public boolean hasInnerTrim(ISwitchE e){ return hasInnerTrim(); }
    synchronized public IBool hasInnerTrim(ISwitchR r){ return new IBool(hasInnerTrim()); }
    
    synchronized public boolean hasOuterTrim(){ return surface.hasOuterTrim(); }
    //synchronized public IBool hasOuterTrimR(){ return surface.hasOuterTrimR(); }
    synchronized public boolean hasOuterTrim(ISwitchE e){ return hasOuterTrim(); }
    synchronized public IBool hasOuterTrim(ISwitchR r){ return new IBool(hasOuterTrim()); }
    
    synchronized public int innerTrimLoopNum(){ return surface.innerTrimLoopNum(); }
    //synchronized public IInteger innerTrimLoopNumR(){ return surface.innerTrimLoopNumR(); }
    synchronized public int innerTrimLoopNum(ISwitchE e){ return innerTrimLoopNum(); }
    synchronized public IInteger innerTrimLoopNum(ISwitchR r){ return new IInteger(innerTrimLoopNum()); }
    
    synchronized public int innerTrimNum(int i){ return surface.innerTrimNum(i); }
    synchronized public IInteger innerTrimNum(IIntegerI i){ return surface.innerTrimNum(i); }
    synchronized public ITrimCurveI[] innerTrimLoop(int i){ return surface.innerTrimLoop(i); }
    synchronized public ITrimCurveI[] innerTrimLoop(IIntegerI i){ return surface.innerTrimLoop(i); }
    synchronized public ITrimCurveI innerTrim(int i, int j){ return surface.innerTrim(i,j); }
    synchronized public ITrimCurveI innerTrim(IIntegerI i, IIntegerI j){ return surface.innerTrim(i,j); }
    
    synchronized public int outerTrimLoopNum(){ return surface.outerTrimLoopNum(); }
    //synchronized public IInteger outerTrimLoopNumR(){ return surface.outerTrimLoopNumR(); }
    synchronized public int outerTrimLoopNum(ISwitchE e){ return outerTrimLoopNum(); }
    synchronized public IInteger outerTrimLoopNum(ISwitchR r){ return new IInteger(outerTrimLoopNum()); }
    
    synchronized public int outerTrimNum(int i){ return surface.outerTrimNum(i); }
    synchronized public IIntegerI outerTrimNum(IIntegerI i){ return surface.outerTrimNum(i); }
    synchronized public ITrimCurveI[] outerTrimLoop(int i){ return surface.outerTrimLoop(i); }
    synchronized public ITrimCurveI[] outerTrimLoop(IIntegerI i){ return surface.outerTrimLoop(i); }
    synchronized public ITrimCurveI outerTrim(int i, int j){ return surface.outerTrim(i,j); }
    synchronized public ITrimCurveI outerTrim(IIntegerI i, IIntegerI j){ return surface.outerTrim(i,j); }
    
    synchronized public boolean hasDefaultTrim(){ return surface.hasDefaultTrim(); }
    //synchronized public IBool hasDefaultTrimR(){ return surface.hasDefaultTrimR(); }
    synchronized public boolean hasDefaultTrim(ISwitchE e){ return hasDefaultTrim(); }
    synchronized public IBool hasDefaultTrim(ISwitchR r){ return new IBool(hasDefaultTrim()); }
    
    synchronized public ISurface addInnerTrimLoop(ITrimCurveI trim){
	surface.addInnerTrimLoop(trim); resetGraphic(); return this; 
    }
    synchronized public ISurface addOuterTrimLoop(ITrimCurveI trim){
	surface.addOuterTrimLoop(trim); resetGraphic(); return this;
    }
    
    synchronized public ISurface addInnerTrimLoop(ITrimCurveI[] trim){
	surface.addInnerTrimLoop(trim); resetGraphic(); return this; 
    }
    synchronized public ISurface addOuterTrimLoop(ITrimCurveI[] trim){
	surface.addOuterTrimLoop(trim); resetGraphic(); return this;
    }
    
    synchronized public ISurface addInnerTrimLoop(ICurveI trim){
	surface.addInnerTrimLoop(trim); resetGraphic(); return this;
    }
    synchronized public ISurface addOuterTrimLoop(ICurveI trim){
	surface.addOuterTrimLoop(trim); resetGraphic(); return this;
    }
    
    synchronized public ISurface addInnerTrimLoop(ICurveI trim, boolean deleteInput){
	surface.addInnerTrimLoop(trim,deleteInput); resetGraphic(); return this;
    }
    synchronized public ISurface addOuterTrimLoop(ICurveI trim, boolean deleteInput){
	surface.addOuterTrimLoop(trim,deleteInput); resetGraphic(); return this;
    }
    
    synchronized public ISurface addInnerTrimLoop(ICurveI[] trim){
	surface.addInnerTrimLoop(trim); resetGraphic(); return this;
    }
    synchronized public ISurface addOuterTrimLoop(ICurveI[] trim){
	surface.addOuterTrimLoop(trim); resetGraphic(); return this;
    }
    
    synchronized public ISurface addInnerTrimLoop(ICurveI[] trim, boolean deleteInput){
	surface.addInnerTrimLoop(trim,deleteInput); resetGraphic(); return this;
    }
    synchronized public ISurface addOuterTrimLoop(ICurveI[] trim, boolean deleteInput){
	surface.addOuterTrimLoop(trim,deleteInput); resetGraphic(); return this;
    }
    
    
    synchronized public ISurface clearInnerTrim(){ surface.clearInnerTrim(); return this; }
    synchronized public ISurface clearOuterTrim(){ surface.clearOuterTrim(); return this; }
    synchronized public ISurface clearTrim(){ surface.clearTrim(); return this; }
    
    
    synchronized public boolean isFlat(){ return surface.isFlat(); }
    //synchronized public IBool isFlatR(){ return surface.isFlatR(); }
    synchronized public boolean isFlat(ISwitchE e){ return isFlat(); }
    synchronized public IBool isFlat(ISwitchR r){ return surface.isFlat(r); }
    
    synchronized public boolean isUClosed(){ return surface.isUClosed(); }
    synchronized public boolean isUClosed(ISwitchE e){ return surface.isUClosed(); }
    synchronized public IBool isUClosed(ISwitchR r){ return surface.isUClosed(r); }
    
    synchronized public boolean isVClosed(){ return surface.isVClosed(); }
    synchronized public boolean isVClosed(ISwitchE e){ return surface.isVClosed(); }
    synchronized public IBool isVClosed(ISwitchR r){ return surface.isVClosed(r); }
    
    
    synchronized public boolean isInsideTrim(double u, double v){ return surface.isInsideTrim(u,v); }
    synchronized public boolean isInsideTrim(IVec2 v){ return surface.isInsideTrim(v); }
    synchronized public boolean isInsideTrim(IVec2I v){ return surface.isInsideTrim(v); }
    
    
    /*********************************************************************************
     * transformation methods; API of ITransformable interface
     *********************************************************************************/
    
    synchronized public ISurface add(double x, double y, double z){ surface.add(x,y,z); return this; }
    synchronized public ISurface add(IDoubleI x, IDoubleI y, IDoubleI z){ surface.add(x,y,z); return this; }
    synchronized public ISurface add(IVecI v){ surface.add(v); return this; }
    synchronized public ISurface sub(double x, double y, double z){ surface.sub(x,y,z); return this; }
    synchronized public ISurface sub(IDoubleI x, IDoubleI y, IDoubleI z){ surface.sub(x,y,z); return this; }
    synchronized public ISurface sub(IVecI v){ surface.sub(v); return this; }
    synchronized public ISurface mul(IDoubleI v){ surface.mul(v); return this; }
    synchronized public ISurface mul(double v){ surface.mul(v); return this; }
    synchronized public ISurface div(IDoubleI v){ surface.div(v); return this; }
    synchronized public ISurface div(double v){ surface.div(v); return this; }
    
    synchronized public ISurface neg(){ surface.neg(); return this; }
    /** alias of neg */
    synchronized public ISurface flip(){ return neg(); }
    
    
    /** scale add */
    synchronized public ISurface add(IVecI v, double f){ surface.add(v,f); return this; }
    synchronized public ISurface add(IVecI v, IDoubleI f){ surface.add(v,f); return this; }
    /** scale add alias */
    synchronized public ISurface add(double f, IVecI v){ return add(v,f); }
    synchronized public ISurface add(IDoubleI f, IVecI v){ return add(v,f); }
    
    synchronized public ISurface rot(IDoubleI angle){ surface.rot(angle); return this; }
    synchronized public ISurface rot(double angle){ surface.rot(angle); return this; }
    
    synchronized public ISurface rot(IVecI axis, IDoubleI angle){ surface.rot(axis,angle); return this; }
    synchronized public ISurface rot(IVecI axis, double angle){ surface.rot(axis,angle); return this; }
    
    synchronized public ISurface rot(IVecI center, IVecI axis, IDoubleI angle){ surface.rot(center,axis,angle); return this; }
    synchronized public ISurface rot(IVecI center, IVecI axis, double angle){ surface.rot(center,axis,angle); return this; }
    
    /** rotate to destination direction vector */
    synchronized public ISurface rot(IVecI axis, IVecI destDir){ surface.rot(axis,destDir); return this; }
    /** rotate to destination point location */    
    synchronized public ISurface rot(IVecI center, IVecI axis, IVecI destPt){ surface.rot(center,axis,destPt); return this; }
    
    synchronized public ISurface rot2(IDoubleI angle){ return rot(angle); }
    synchronized public ISurface rot2(double angle){ return rot(angle); }
    synchronized public ISurface rot2(IVecI center, IDoubleI angle){ surface.rot2(center,angle); return this; }
    synchronized public ISurface rot2(IVecI center, double angle){ surface.rot2(center,angle); return this; }
    
    /** rotation on xy-plane to destination direction vector */
    synchronized public ISurface rot2(IVecI destDir){ surface.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */    
    synchronized public ISurface rot2(IVecI center, IVecI destPt){ surface.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    synchronized public ISurface scale(IDoubleI f){ return mul(f); }
    synchronized public ISurface scale(double f){ return mul(f); }
    synchronized public ISurface scale(IVecI center, IDoubleI f){ surface.scale(center,f); return this; }
    synchronized public ISurface scale(IVecI center, double f){ surface.scale(center,f); return this; }

    /** scale only in 1 direction */
    synchronized public ISurface scale1d(IVecI axis, double f){ surface.scale1d(axis,f); return this; }
    synchronized public ISurface scale1d(IVecI axis, IDoubleI f){ surface.scale1d(axis,f); return this; }
    synchronized public ISurface scale1d(IVecI center, IVecI axis, double f){
	surface.scale1d(center,axis,f); return this;
    }
    synchronized public ISurface scale1d(IVecI center, IVecI axis, IDoubleI f){
	surface.scale1d(center,axis,f); return this;
    }
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    synchronized public ISurface ref(IVecI planeDir){ surface.ref(planeDir); return this; }
    synchronized public ISurface ref(IVecI center, IVecI planeDir){ surface.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    synchronized public ISurface mirror(IVecI planeDir){ return ref(planeDir); }
    synchronized public ISurface mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    synchronized public ISurface shear(double sxy, double syx, double syz,
			  double szy, double szx, double sxz){
	surface.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    synchronized public ISurface shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			  IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	surface.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public ISurface shear(IVecI center, double sxy, double syx, double syz,
			  double szy, double szx, double sxz){
	surface.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public ISurface shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			  IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	surface.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    synchronized public ISurface shearXY(double sxy, double syx){
	surface.shearXY(sxy,syx); return this;
    }
    synchronized public ISurface shearXY(IDoubleI sxy, IDoubleI syx){
	surface.shearXY(sxy,syx); return this;
    }	
    synchronized public ISurface shearXY(IVecI center, double sxy, double syx){
	surface.shearXY(center,sxy,syx); return this;
    }
    synchronized public ISurface shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	surface.shearXY(center,sxy,syx); return this;
    }
    
    synchronized public ISurface shearYZ(double syz, double szy){
	surface.shearYZ(syz,szy); return this;
    }
    synchronized public ISurface shearYZ(IDoubleI syz, IDoubleI szy){
	surface.shearYZ(syz,szy); return this;
    }
    synchronized public ISurface shearYZ(IVecI center, double syz, double szy){
	surface.shearYZ(center,syz,szy); return this;
    }
    synchronized public ISurface shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	surface.shearYZ(center,syz,szy); return this;
    }
    
    synchronized public ISurface shearZX(double szx, double sxz){
	surface.shearZX(szx,sxz); return this;
    }
    synchronized public ISurface shearZX(IDoubleI szx, IDoubleI sxz){
	surface.shearZX(szx,sxz); return this;
    }
    synchronized public ISurface shearZX(IVecI center, double szx, double sxz){
	surface.shearZX(center,szx,sxz); return this;
    }
    synchronized public ISurface shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	surface.shearZX(center,szx,sxz); return this;
    }
    
    
    /** mv() is alias of add() */
    synchronized public ISurface mv(double x, double y, double z){ return add(x,y,z); }
    synchronized public ISurface mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    synchronized public ISurface mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    synchronized public ISurface cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    synchronized public ISurface cp(double x, double y, double z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    synchronized public ISurface cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    synchronized public ISurface cp(IVecI v){ return dup().add(v); }
    
    /** translate() is alias of add() */
    synchronized public ISurface translate(double x, double y, double z){ return add(x,y,z); }
    synchronized public ISurface translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    synchronized public ISurface translate(IVecI v){ return add(v); }
    
    synchronized public ISurface transform(IMatrix3I mat){ surface.transform(mat); return this; }
    synchronized public ISurface transform(IMatrix4I mat){ surface.transform(mat); return this; }
    synchronized public ISurface transform(IVecI xvec, IVecI yvec, IVecI zvec){
	surface.transform(xvec,yvec,zvec); return this;
    }
    synchronized public ISurface transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	surface.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /*********************************************************************************
     * methods of IObject
     *********************************************************************************/
    
    synchronized public ISurface name(String nm){ super.name(nm); return this; }
    synchronized public ISurface layer(ILayer l){ super.layer(l); return this; }
    synchronized public ISurface layer(String l){ super.layer(l); return this; }

    synchronized public ISurface attr(IAttribute at){ super.attr(at); return this; }


    synchronized public ISurface hide(){ super.hide(); return this; }
    synchronized public ISurface show(){ super.show(); return this; }
    
    
    synchronized public ISurface clr(IColor c){ super.clr(c); return this; }
    synchronized public ISurface clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public ISurface clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    synchronized public ISurface clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    synchronized public ISurface clr(Color c){ super.clr(c); return this; }
    synchronized public ISurface clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public ISurface clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    synchronized public ISurface clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    synchronized public ISurface clr(int gray){ super.clr(gray); return this; }
    synchronized public ISurface clr(float fgray){ super.clr(fgray); return this; }
    synchronized public ISurface clr(double dgray){ super.clr(dgray); return this; }
    synchronized public ISurface clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    synchronized public ISurface clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    synchronized public ISurface clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    synchronized public ISurface clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    synchronized public ISurface clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    synchronized public ISurface clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    synchronized public ISurface clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    synchronized public ISurface clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    synchronized public ISurface clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    synchronized public ISurface hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    synchronized public ISurface hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    synchronized public ISurface hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    synchronized public ISurface hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    synchronized public ISurface setColor(IColor c){ super.setColor(c); return this; }
    synchronized public ISurface setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public ISurface setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    synchronized public ISurface setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    synchronized public ISurface setColor(Color c){ super.setColor(c); return this; }
    synchronized public ISurface setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public ISurface setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    synchronized public ISurface setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    synchronized public ISurface setColor(int gray){ super.setColor(gray); return this; }
    synchronized public ISurface setColor(float fgray){ super.setColor(fgray); return this; }
    synchronized public ISurface setColor(double dgray){ super.setColor(dgray); return this; }
    synchronized public ISurface setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    synchronized public ISurface setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    synchronized public ISurface setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    synchronized public ISurface setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    synchronized public ISurface setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    synchronized public ISurface setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    synchronized public ISurface setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    synchronized public ISurface setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    synchronized public ISurface setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    synchronized public ISurface setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public ISurface setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public ISurface setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    synchronized public ISurface setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    synchronized public ISurface weight(double w){ super.weight(w); return this; }
    synchronized public ISurface weight(float w){ super.weight(w); return this; }
    
}
