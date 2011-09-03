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
   Class of NURBS surface object.
   It contains ISurfaceGeo instance inside.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ISurface extends IObject implements ISurfaceI{
    public ISurfaceGeo surface;
    //public ISurfaceI surface; // public?
    
    public ISurface(){ surface = new ISurfaceGeo(); }
    
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
    
    /** triangle
     */
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
    
    
    //public ISurface(ISurfaceGeo srf){ surface = new ISurfaceGeo(srf); initSurface(null); }
    public ISurface(ISurfaceGeo srf){ surface = srf; initSurface(null); }
    
    
    public ISurface(ISurfaceI srf){ surface = srf.get(); initSurface(null); }
    
    public ISurface(IServerI s){ super(s); surface = new ISurfaceGeo(); /*initSurface(s);*/ }
    
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
    
    //public ISurface(IServerI s, ISurfaceGeo srf){ super(s); surface = new ISurfaceGeo(srf); initSurface(s); }
    public ISurface(IServerI s, ISurfaceGeo srf){ super(s); surface = srf; initSurface(s); }
    
    public ISurface(IServerI s, ISurfaceI srf){ super(s); surface = srf.get(); initSurface(s); }
    
    public ISurface(ISurface srf){
	super(srf);
	//surface = new ISurfaceGeo(srf.surface.get());
	surface = srf.surface.dup();
	initSurface(srf.server);
	setColor(srf.getColor());
    }
    
    public ISurface(IServerI s, ISurface srf){
	super(s,srf);
	//surface = new ISurfaceGeo(srf.surface.get());
	surface = srf.surface.dup();
	initSurface(s);
	setColor(srf.getColor());
    }
    
    public void initSurface(IServerI s){
	if(surface instanceof ISurfaceGeo){
	    parameter = (ISurfaceGeo)surface;
	}
	if(graphics==null) initGraphic(s);
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
        if(m.isGL()){
	    return new ISurfaceGraphicGL(this);
	    //if(m.isFill()) return new ISurfaceGraphicGL(this);
	    //if(m.isFill()) return new ISurfaceGraphicFillGL(this);
	    //else if(m.isWireframe()) return new ISurfaceGraphicWireframeGL(this);
        }
        return null;
    }
    
    public ISurfaceGeo get(){ return surface.get(); } // ?
    
    public ISurface dup(){ return new ISurface(this); }    
    
    public IVec pt(IVec2I v){ return surface.pt(v); }
    public IVec pt(IDoubleI u, IDoubleI v){ return surface.pt(u,v); }
    public IVec pt(double u, double v){ return surface.pt(u,v); }
    
    //public void pt(double u, double v, IVec retval){ surface.pt(u,v,retval); }
    
    public IVec utan(IVec2I v){ return surface.utan(v); }
    public IVec utan(IDoubleI u, IDoubleI v){ return surface.utan(u,v); }
    public IVec utan(double u, double v){ return surface.utan(u,v); }
    //public void utan(double u, double v, IVec retval){ surface.utan(u,v,retval); }

    public IVec vtan(IVec2I v){ return surface.vtan(v); }
    public IVec vtan(IDoubleI u, IDoubleI v){ return surface.vtan(u,v); }
    public IVec vtan(double u, double v){ return surface.vtan(u,v); }
    //public void vtan(double u, double v, IVec retval){ surface.vtan(u,v,retval); }
    
    public IVec normal(IVec2I v){ return surface.normal(v); }
    public IVec normal(IDoubleI u, IDoubleI v){ return surface.normal(u,v); }
    public IVec normal(double u, double v){ return surface.normal(u,v); }
    //public void normal(double u, double v, IVec retval){ surface.normal(u,v,retval); }
    
    public IVec cp(int i, int j){ return surface.cp(i,j); }
    public IVecI cp(IIntegerI i, IIntegerI j){ return surface.cp(i,j); }
    
    public IVec ep(int i, int j){ return surface.ep(i,j); }
    public IVec ep(IIntegerI i, IIntegerI j){ return surface.ep(i,j); }
    
    public IVec corner(int i, int j){ return surface.corner(i,j); }
    public IVec corner(IIntegerI i, IIntegerI j){ return surface.corner(i,j); }
    public IVec cornerCP(int i, int j){ return surface.cornerCP(i,j); }
    public IVecI cornerCP(IIntegerI i, IIntegerI j){ return surface.cornerCP(i,j); }
    
    
    public double uknot(int i){ return surface.uknot(i); }
    public IDouble uknot(IIntegerI i){ return surface.uknot(i); }
    public double vknot(int i){ return surface.vknot(i); }
    public IDouble vknot(IIntegerI i){ return surface.vknot(i); }
    
    public int uknotNum(){ return surface.uknotNum(); }
    public int vknotNum(){ return surface.vknotNum(); }
    //public IInteger uknotNumR(){ return surface.uknotNumR(); }
    //public IInteger vknotNumR(){ return surface.vknotNumR(); }
    public int uknotNum(ISwitchE e){ return uknotNum(); }
    public int vknotNum(ISwitchE e){ return vknotNum(); }
    public IInteger uknotNum(ISwitchR r){ return new IInteger(uknotNum()); }
    public IInteger vknotNum(ISwitchR r){ return new IInteger(vknotNum()); }
    
    public boolean isRational(){ return surface.isRational(); }
    public boolean isRational(ISwitchE e){ return surface.isRational(); }
    public IBool isRational(ISwitchR r){ return new IBool(isRational()); }
    
    
    public int udeg(){ return surface.udeg(); }
    public int vdeg(){ return surface.vdeg(); }
    //public IInteger udegR(){ return surface.udegR(); }
    //public IInteger vdegR(){ return surface.vdegR(); }
    public int udeg(ISwitchE e){ return udeg(); }
    public int vdeg(ISwitchE e){ return vdeg(); }
    public IInteger udeg(ISwitchR r){ return new IInteger(udeg()); }
    public IInteger vdeg(ISwitchR r){ return new IInteger(vdeg()); }
    
    public int unum(){ return surface.unum(); }
    public int vnum(){ return surface.vnum(); }
    //public IInteger unumR(){ return surface.unumR(); }
    //public IInteger vnumR(){ return surface.vnumR(); }
    public int unum(ISwitchE e){ return unum(); }
    public int vnum(ISwitchE e){ return vnum(); }
    public IInteger unum(ISwitchR r){ return new IInteger(unum()); }
    public IInteger vnum(ISwitchR r){ return new IInteger(vnum()); }
    
    public int ucpNum(){ return surface.ucpNum(); } // equals to unum()
    public int vcpNum(){ return surface.vcpNum(); } // equals to vnum()
    //public IInteger ucpNumR(){ return surface.ucpNumR(); } // equals to unum()
    //public IInteger vcpNumR(){ return surface.vcpNumR(); } // equals to vnum()
    public int ucpNum(ISwitchE e){ return ucpNum(); }
    public int vcpNum(ISwitchE e){ return vcpNum(); }
    public IInteger ucpNum(ISwitchR r){ return new IInteger(ucpNum()); }
    public IInteger vcpNum(ISwitchR r){ return new IInteger(vcpNum()); }
    
    public int uepNum(){ return surface.uepNum(); }
    public int vepNum(){ return surface.vepNum(); }
    //public IInteger uepNumR(){ return surface.uepNumR(); }
    //public IInteger vepNumR(){ return surface.vepNumR(); }
    public int uepNum(ISwitchE e){ return uepNum(); }
    public int vepNum(ISwitchE e){ return vepNum(); }
    public IInteger uepNum(ISwitchR r){ return new IInteger(uepNum()); }
    public IInteger vepNum(ISwitchR r){ return new IInteger(vepNum()); }
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double u(int epIdx, double epFraction){ return surface.u(epIdx,epFraction); }
    public IDouble u(IIntegerI epIdx, IDoubleI epFraction){ return surface.u(epIdx,epFraction); }
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double v(int epIdx, double epFraction){ return surface.v(epIdx,epFraction); }
    public IDouble v(IIntegerI epIdx, IDoubleI epFraction){ return surface.v(epIdx,epFraction); }
    
    public double ustart(){ return surface.ustart(); }
    public double uend(){ return surface.uend(); }
    public double vstart(){ return surface.vstart(); }
    public double vend(){ return surface.vend(); }
    //public IDouble ustartR(){ return surface.ustartR(); }
    //public IDouble uendR(){ return surface.uendR(); }
    //public IDouble vstartR(){ return surface.vstartR(); }
    //public IDouble vendR(){ return surface.vendR(); }
    public double ustart(ISwitchE e){ return ustart(); }
    public double uend(ISwitchE e){ return uend(); }
    public double vstart(ISwitchE e){ return vstart(); }
    public double vend(ISwitchE e){ return vend(); }
    public IDouble ustart(ISwitchR r){ return new IDouble(ustart()); }
    public IDouble uend(ISwitchR r){ return new IDouble(uend()); }
    public IDouble vstart(ISwitchR r){ return new IDouble(vstart()); }
    public IDouble vend(ISwitchR r){ return new IDouble(vend()); }
    
    public boolean hasTrim(){ return surface.hasTrim(); }
    //public IBool hasTrimR(){ return surface.hasTrimR(); }
    public boolean hasTrim(ISwitchE e){ return hasTrim(); }
    public IBool hasTrim(ISwitchR r){ return new IBool(hasTrim()); }
    
    public boolean hasInnerTrim(){ return surface.hasInnerTrim(); }
    //public IBool hasInnerTrimR(){ return surface.hasInnerTrimR(); }
    public boolean hasInnerTrim(ISwitchE e){ return hasInnerTrim(); }
    public IBool hasInnerTrim(ISwitchR r){ return new IBool(hasInnerTrim()); }
    
    public boolean hasOuterTrim(){ return surface.hasOuterTrim(); }
    //public IBool hasOuterTrimR(){ return surface.hasOuterTrimR(); }
    public boolean hasOuterTrim(ISwitchE e){ return hasOuterTrim(); }
    public IBool hasOuterTrim(ISwitchR r){ return new IBool(hasOuterTrim()); }
    
    public int innerTrimLoopNum(){ return surface.innerTrimLoopNum(); }
    //public IInteger innerTrimLoopNumR(){ return surface.innerTrimLoopNumR(); }
    public int innerTrimLoopNum(ISwitchE e){ return innerTrimLoopNum(); }
    public IInteger innerTrimLoopNum(ISwitchR r){ return new IInteger(innerTrimLoopNum()); }
    
    public int innerTrimNum(int i){ return surface.innerTrimNum(i); }
    public IInteger innerTrimNum(IIntegerI i){ return surface.innerTrimNum(i); }
    public ITrimCurveI[] innerTrimLoop(int i){ return surface.innerTrimLoop(i); }
    public ITrimCurveI[] innerTrimLoop(IIntegerI i){ return surface.innerTrimLoop(i); }
    public ITrimCurveI innerTrim(int i, int j){ return surface.innerTrim(i,j); }
    public ITrimCurveI innerTrim(IIntegerI i, IIntegerI j){ return surface.innerTrim(i,j); }
    
    public int outerTrimLoopNum(){ return surface.outerTrimLoopNum(); }
    //public IInteger outerTrimLoopNumR(){ return surface.outerTrimLoopNumR(); }
    public int outerTrimLoopNum(ISwitchE e){ return outerTrimLoopNum(); }
    public IInteger outerTrimLoopNum(ISwitchR r){ return new IInteger(outerTrimLoopNum()); }
    
    public int outerTrimNum(int i){ return surface.outerTrimNum(i); }
    public IIntegerI outerTrimNum(IIntegerI i){ return surface.outerTrimNum(i); }
    public ITrimCurveI[] outerTrimLoop(int i){ return surface.outerTrimLoop(i); }
    public ITrimCurveI[] outerTrimLoop(IIntegerI i){ return surface.outerTrimLoop(i); }
    public ITrimCurveI outerTrim(int i, int j){ return surface.outerTrim(i,j); }
    public ITrimCurveI outerTrim(IIntegerI i, IIntegerI j){ return surface.outerTrim(i,j); }
    
    public boolean hasDefaultTrim(){ return surface.hasDefaultTrim(); }
    //public IBool hasDefaultTrimR(){ return surface.hasDefaultTrimR(); }
    public boolean hasDefaultTrim(ISwitchE e){ return hasDefaultTrim(); }
    public IBool hasDefaultTrim(ISwitchR r){ return new IBool(hasDefaultTrim()); }
    
    public ISurface addInnerTrimLoop(ITrimCurveI trim){
	surface.addInnerTrimLoop(trim); updateGraphic(); return this; 
    }
    public ISurface addOuterTrimLoop(ITrimCurveI trim){
	surface.addOuterTrimLoop(trim); updateGraphic(); return this;
    }
    
    public ISurface addInnerTrimLoop(ITrimCurveI[] trim){
	surface.addInnerTrimLoop(trim); updateGraphic(); return this; 
    }
    public ISurface addOuterTrimLoop(ITrimCurveI[] trim){
	surface.addOuterTrimLoop(trim); updateGraphic(); return this;
    }
    
    public ISurface addInnerTrimLoop(ICurveI trim){
	surface.addInnerTrimLoop(trim); updateGraphic(); return this;
    }
    public ISurface addOuterTrimLoop(ICurveI trim){
	surface.addOuterTrimLoop(trim); updateGraphic(); return this;
    }
    
    public ISurface addInnerTrimLoop(ICurveI trim, boolean deleteInput){
	surface.addInnerTrimLoop(trim,deleteInput); updateGraphic(); return this;
    }
    public ISurface addOuterTrimLoop(ICurveI trim, boolean deleteInput){
	surface.addOuterTrimLoop(trim,deleteInput); updateGraphic(); return this;
    }
    
    public ISurface addInnerTrimLoop(ICurveI[] trim){
	surface.addInnerTrimLoop(trim); updateGraphic(); return this;
    }
    public ISurface addOuterTrimLoop(ICurveI[] trim){
	surface.addOuterTrimLoop(trim); updateGraphic(); return this;
    }
    
    public ISurface addInnerTrimLoop(ICurveI[] trim, boolean deleteInput){
	surface.addInnerTrimLoop(trim,deleteInput); updateGraphic(); return this;
    }
    public ISurface addOuterTrimLoop(ICurveI[] trim, boolean deleteInput){
	surface.addOuterTrimLoop(trim,deleteInput); updateGraphic(); return this;
    }
    
    
    public ISurface clearInnerTrim(){ surface.clearInnerTrim(); return this; }
    public ISurface clearOuterTrim(){ surface.clearOuterTrim(); return this; }
    public ISurface clearTrim(){ surface.clearTrim(); return this; }
    
    
    public boolean isFlat(){ return surface.isFlat(); }
    //public IBool isFlatR(){ return surface.isFlatR(); }
    public boolean isFlat(ISwitchE e){ return isFlat(); }
    public IBool isFlat(ISwitchR r){ return new IBool(isFlat()); }
    
    
    public ISurface name(String nm){ super.name(nm); return this; }
    public ISurface layer(ILayer l){ super.layer(l); return this; }

    public ISurface hide(){ super.hide(); return this; }
    public ISurface show(){ super.show(); return this; }
    
    
    public ISurface clr(Color c){ super.clr(c); return this; }
    public ISurface clr(int gray){ super.clr(gray); return this; }
    public ISurface clr(float fgray){ super.clr(fgray); return this; }
    public ISurface clr(double dgray){ super.clr(dgray); return this; }
    public ISurface clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public ISurface clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public ISurface clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public ISurface clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public ISurface clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public ISurface clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public ISurface clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public ISurface clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public ISurface clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public ISurface hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public ISurface hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public ISurface hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public ISurface hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public ISurface setColor(Color c){ super.setColor(c); return this; }
    public ISurface setColor(int gray){ super.setColor(gray); return this; }
    public ISurface setColor(float fgray){ super.setColor(fgray); return this; }
    public ISurface setColor(double dgray){ super.setColor(dgray); return this; }
    public ISurface setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public ISurface setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public ISurface setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public ISurface setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public ISurface setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public ISurface setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public ISurface setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public ISurface setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public ISurface setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public ISurface setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public ISurface setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public ISurface setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public ISurface setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
}
