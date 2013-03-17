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
   Reference class of NURBS surface to contain any instance of ISurfaceI.
   
   @author Satoru Sugihara
*/
public class ISurfaceR extends IObject implements ISurfaceI{
    
    public ISurfaceI surface; // public?
    
    public ISurfaceR(){ }
    
    public ISurfaceR(ISurfaceI srf){ surface = srf; initSurface(null); }
    
    public ISurfaceR(IServerI s){ super(s); }
    
    public ISurfaceR(IServerI s, ISurfaceI srf){ super(s); surface = srf; initSurface(s); }
    
    public ISurfaceR(ISurfaceR srf){
	super(srf);
	surface = srf.surface.dup(); // deep copy ?
	initSurface(srf.server);
	setColor(srf.getColor());
    }
    
    public ISurfaceR(IServerI s, ISurfaceR srf){
	super(s,srf);
	surface = srf.surface.dup(); // deep copy ?
	initSurface(s);
	setColor(srf.getColor());
    }
    
    public void initSurface(IServerI s){
	if(surface instanceof ISurfaceGeo){ parameter = (ISurfaceGeo)surface; }
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
    
    public boolean isValid(){ if(surface==null){ return false; } return surface.isValid(); }
    
    public ISurfaceGeo get(){ return surface.get(); } // ?
    
    public ISurfaceR dup(){ return new ISurfaceR(this); }    
    
    //public IVecI pt(IVec2I v){ return surface.pt(v); }
    //public IVecI pt(IDoubleI u, IDoubleI v){ return surface.pt(u,v); }
    //public IVecI pt(double u, double v){ return surface.pt(u,v); }
    public IVecR pt(IVec2I v){ return new IVecR(new Pt(surface,v)); }
    public IVecR pt(IDoubleI u, IDoubleI v){ return new IVecR(new Pt(surface,new IVec2R(u,v))); }
    public IVecR pt(double u, double v){ return new IVecR(new Pt(surface,new IVec2(u,v))); }
    
    /**
       @param u u coordinates in uv parameter space
       @param v v coordinates in uv parameter space
       @param n length in normal direction in 3D space
    */
    //public IVecR pt(double u, double v, double n){ return surface.pt(u,v,n); }
    //public IVecR pt(IDoubleI u, IDoubleI v, IDoubleI n){ return surface.pt(u,v,n); }
    //public IVecR pt(IVecI v){ return surface.pt(v); }
    public IVecR pt(double u, double v, double n){ return pt(u,v).add(nml(u,v).len(n)); }
    public IVecR pt(IDoubleI u, IDoubleI v, IDoubleI n){ return pt(u,v).add(nml(u,v).len(n)); }
    public IVecR pt(IVecI v){
	IDoubleI x = v.x((Ir)null);
	IDoubleI y = v.y((Ir)null);
	return pt(x,y).add(nml(x,y).len(v.z((Ir)null)));
    }
    
    //public IVecI utan(IVec2I v){ return surface.utan(v); }
    //public IVecI utan(IDoubleI u, IDoubleI v){ return surface.utan(u,v); }
    //public IVecI utan(double u, double v){ return surface.utan(u,v); }
    public IVecR utan(IVec2I v){ return new IVecR(new UTan(surface,v)); }
    public IVecR utan(IDoubleI u, IDoubleI v){ return new IVecR(new UTan(surface,new IVec2R(u,v))); }
    public IVecR utan(double u, double v){ return new IVecR(new UTan(surface,new IVec2(u,v))); }
    
    //public IVecI vtan(IVec2I v){ return surface.vtan(v); }
    //public IVecI vtan(IDoubleI u, IDoubleI v){ return surface.vtan(u,v); }
    //public IVecI vtan(double u, double v){ return surface.vtan(u,v); }
    public IVecR vtan(IVec2I v){ return new IVecR(new VTan(surface,v)); }
    public IVecR vtan(IDoubleI u, IDoubleI v){ return new IVecR(new VTan(surface,new IVec2R(u,v))); }
    public IVecR vtan(double u, double v){ return new IVecR(new VTan(surface,new IVec2(u,v))); }
    
    public IVecR normal(IVec2I v){ return nml(v); }
    public IVecR normal(IDoubleI u, IDoubleI v){ return nml(u,v); }
    public IVecR normal(double u, double v){ return nml(u,v); }
    
    public IVecR nrml(IVec2I v){ return nml(v); }
    public IVecR nrml(IDoubleI u, IDoubleI v){ return nml(u,v); }
    public IVecR nrml(double u, double v){ return nml(u,v); }
    
    //public IVecI nml(IVec2I v){ return surface.nml(v); }
    //public IVecI nml(IDoubleI u, IDoubleI v){ return surface.nml(u,v); }
    //public IVecI nml(double u, double v){ return surface.nml(u,v); }
    public IVecR nml(IVec2I v){ return new IVecR(new Normal(surface,v)); }
    public IVecR nml(IDoubleI u, IDoubleI v){ return new IVecR(new Normal(surface,new IVec2R(u,v))); }
    public IVecR nml(double u, double v){ return new IVecR(new Normal(surface,new IVec2(u,v))); }
    
    
    public IVecI cp(int i, int j){ return surface.cp(i,j); }
    public IVecI cp(IIntegerI i, IIntegerI j){ return surface.cp(i,j); }
    
    public IVecI[][] cps(){ return surface.cps(); }

    //public IVecI ep(int i, int j){ surface.ep(i,j); }
    //public IVecI ep(IIntegerI i, IIntegerI j){ return surface.ep(i,j); }
    public IVecR ep(int i, int j){ return pt(uknot(i+udeg()),vknot(j+vdeg())); } // test this.
    public IVecR ep(IIntegerI i, IIntegerI j){ return pt(uknot(i.cp(udeg((Ir)null))),vknot(j.cp(vdeg((Ir)null)))); } // test this.
    
    //public IVecI corner(int i, int j){ return surface.corner(i,j); }
    //public IVecI corner(IIntegerI i, IIntegerI j){ return surface.corner(i,j); }
    public IVecR corner(int i, int j){
	if(i!=0) i=1;
	if(j!=0) j=1;
	return pt((double)i,(double)j);
    } // test this.
    public IVecR corner(IIntegerI i, IIntegerI j){
	//return surface.corner(i,j);
	// how about if(u!=0) u=1 ?
	return pt(new IDoubleR(i), new IDoubleR(j));
    } // test this.
    
    public IVecI cornerCP(int i, int j){ return surface.cornerCP(i,j); }
    public IVecI cornerCP(IIntegerI i, IIntegerI j){ return surface.cornerCP(i,j); }
    
    /** mid in UV parameter (u=0.5, v=0.5) point on a surface */
    public IVecR mid(){ return pt(0.5,0.5); }
    
    /** returns center of geometry object */
    public IVecI center(){ return surface.center(); } // not parameterized yet
	
    
    /** approximate invert projection from 3D location to interanl UV parameter (closest point on surface) */
    public IVec2R uv(IVecI pt){ return new IVec2R(new UV(this,pt)); }
    /** approximate invert projection from 2D location to interanl UV parameter (closest point on surface) */
    public IVec2R uv(IVec2I pt){ return new IVec2R(new UV2(this,pt)); }
    
    
    
    /** find approximately closest point on a surface */
    public IVecR closePt(IVecI pt){ return pt(uv(pt)); }
    
    /** find approximately closest point on a surface on 2D */
    public IVecR closePt(IVec2I pt){ return pt(uv(pt)); }
    
    /** distance to the closest point on a surface */
    public double dist(IVecI pt){ return surface.dist(pt); }
    /** distance to the closest point on a surface on 2D*/
    public double dist(IVec2I pt){ return surface.dist(pt); }
    
    
    public double uknot(int i){ return surface.uknot(i); }
    public IDoubleI uknot(IIntegerI i){ return surface.uknot(i); }
    public double vknot(int i){ return surface.vknot(i); }
    public IDoubleI vknot(IIntegerI i){ return surface.vknot(i); }
    
    public double[] uknots(){ return surface.uknots(); }
    public double[] vknots(){ return surface.vknots(); }
    public double[] uknots(ISwitchE e){ return uknots(); }
    public double[] vknots(ISwitchE e){ return vknots(); }
    public IDoubleI[] uknots(ISwitchR r){ return surface.uknots(r); }
    public IDoubleI[] vknots(ISwitchR r){ return surface.vknots(r); }
    
    public int uknotNum(){ return surface.uknotNum(); }
    public int vknotNum(){ return surface.vknotNum(); }
    //public IIntegerI uknotNumR(){ return surface.uknotNumR(); }
    //public IIntegerI vknotNumR(){ return surface.vknotNumR(); }
    public int uknotNum(ISwitchE e){ return uknotNum(); }
    public int vknotNum(ISwitchE e){ return vknotNum(); }
    public IIntegerI uknotNum(ISwitchR r){ return surface.uknotNum(r); }
    public IIntegerI vknotNum(ISwitchR r){ return surface.vknotNum(r); }
    
    public boolean isRational(){ return surface.isRational(); }
    public boolean isRational(ISwitchE e){ return surface.isRational(); }
    public IBoolI isRational(ISwitchR r){ return surface.isRational(r); }
    
    public int udeg(){ return surface.udeg(); }
    public int vdeg(){ return surface.vdeg(); }
    //public IIntegerI udegR(){ return surface.udegR(); }
    //public IIntegerI vdegR(){ return surface.vdegR(); }
    public int udeg(ISwitchE e){ return udeg(); }
    public int vdeg(ISwitchE e){ return vdeg(); }
    public IIntegerI udeg(ISwitchR r){ return surface.udeg(r); }
    public IIntegerI vdeg(ISwitchR r){ return surface.vdeg(r); }
    
    public int unum(){ return surface.unum(); }
    public int vnum(){ return surface.vnum(); }
    //public IIntegerI unumR(){ return surface.unumR(); }
    //public IIntegerI vnumR(){ return surface.vnumR(); }
    public int unum(ISwitchE e){ return unum(); }
    public int vnum(ISwitchE e){ return vnum(); }
    public IIntegerI unum(ISwitchR r){ return surface.unum(r); }
    public IIntegerI vnum(ISwitchR r){ return surface.vnum(r); }
    
    public int ucpNum(){ return surface.ucpNum(); } // equals to unum()
    public int vcpNum(){ return surface.vcpNum(); } // equals to vnum()
    //public IIntegerI ucpNumR(){ return surface.ucpNumR(); } // equals to unum()
    //public IIntegerI vcpNumR(){ return surface.vcpNumR(); } // equals to vnum()
    public int ucpNum(ISwitchE e){ return ucpNum(); }
    public int vcpNum(ISwitchE e){ return vcpNum(); }
    public IIntegerI ucpNum(ISwitchR r){ return surface.ucpNum(r); }
    public IIntegerI vcpNum(ISwitchR r){ return surface.vcpNum(r); }
    
    public int uepNum(){ return surface.uepNum(); }
    public int vepNum(){ return surface.vepNum(); }
    //public IIntegerI uepNumR(){ return surface.uepNumR(); }
    //public IIntegerI vepNumR(){ return surface.vepNumR(); }
    public int uepNum(ISwitchE e){ return uepNum(); }
    public int vepNum(ISwitchE e){ return vepNum(); }
    public IIntegerI uepNum(ISwitchR r){ return surface.uepNum(r); }
    public IIntegerI vepNum(ISwitchR r){ return surface.vepNum(r); }
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double u(int epIdx, double epFraction){ return surface.u(epIdx,epFraction); }
    public IDoubleI u(IIntegerI epIdx, IDoubleI epFraction){ return surface.u(epIdx,epFraction); }
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double v(int epIdx, double epFraction){ return surface.v(epIdx,epFraction); }
    public IDoubleI v(IIntegerI epIdx, IDoubleI epFraction){ return surface.v(epIdx,epFraction); }
    
    public double ustart(){ return surface.ustart(); }
    public double uend(){ return surface.uend(); }
    public double vstart(){ return surface.vstart(); }
    public double vend(){ return surface.vend(); }
    //public IDoubleI ustartR(){ return surface.ustartR(); }
    //public IDoubleI uendR(){ return surface.uendR(); }
    //public IDoubleI vstartR(){ return surface.vstartR(); }
    //public IDoubleI vendR(){ return surface.vendR(); }
    public double ustart(ISwitchE e){ return ustart(); }
    public double uend(ISwitchE e){ return uend(); }
    public double vstart(ISwitchE e){ return vstart(); }
    public double vend(ISwitchE e){ return vend(); }
    public IDoubleI ustart(ISwitchR r){ return surface.ustart(r); }
    public IDoubleI uend(ISwitchR r){ return surface.uend(r); }
    public IDoubleI vstart(ISwitchR r){ return surface.vstart(r); }
    public IDoubleI vend(ISwitchR r){ return surface.vend(r); }
    
    /** reverse U parameter internally without creating a new instance */
    public ISurfaceR revU(){ surface.revU(); return this; }
    /** reverse V parameter internally without creating a new instance */
    public ISurfaceR revV(){ surface.revV(); return this; }
    /** reverse U and V parameter internally without creating a new instance */
    public ISurfaceR revUV(){ surface.revUV(); return this; }
    /** reverse normal direction by reversing V direction (UV and normal is dependent */
    public ISurfaceR revN(){ surface.revN(); return this; }
    /** alias of revU() */
    public ISurfaceR flipU(){ return revU(); }
    /** alias of revV() */
    public ISurfaceR flipV(){ return revV(); }
    /** alias of revUV() */
    public ISurfaceR flipUV(){ return revUV(); }
    /** alias of flipN() */
    public ISurfaceR flipN(){ return revN(); }
        
    /** swap U and V parameter */
    public ISurfaceR swapUV(){ surface.swapUV(); return this; }
    
    public boolean hasTrim(){ return surface.hasTrim(); }
    //public IBoolI hasTrimR(){ return surface.hasTrimR(); }
    public boolean hasTrim(ISwitchE e){ return hasTrim(); }
    public IBoolI hasTrim(ISwitchR r){ return surface.hasTrim(r); }
    
    public boolean hasInnerTrim(){ return surface.hasInnerTrim(); }
    //public IBoolI hasInnerTrimR(){ return surface.hasInnerTrimR(); }
    public boolean hasInnerTrim(ISwitchE e){ return hasInnerTrim(); }
    public IBoolI hasInnerTrim(ISwitchR r){ return surface.hasInnerTrim(r); }
    
    public boolean hasOuterTrim(){ return surface.hasOuterTrim(); }
    //public IBoolI hasOuterTrimR(){ return surface.hasOuterTrimR(); }
    public boolean hasOuterTrim(ISwitchE e){ return hasOuterTrim(); }
    public IBoolI hasOuterTrim(ISwitchR r){ return surface.hasOuterTrim(r); }
    
    public int innerTrimLoopNum(){ return surface.innerTrimLoopNum(); }
    //public IIntegerI innerTrimLoopNumR(){ return surface.innerTrimLoopNumR(); }
    public int innerTrimLoopNum(ISwitchE e){ return innerTrimLoopNum(); }
    public IIntegerI innerTrimLoopNum(ISwitchR r){ return surface.innerTrimLoopNum(r); }
    
    public int innerTrimNum(int i){ return surface.innerTrimNum(i); }
    public IIntegerI innerTrimNum(IIntegerI i){ return surface.innerTrimNum(i); }
    public ITrimCurveI[] innerTrimLoop(int i){ return surface.innerTrimLoop(i); }
    public ITrimCurveI[] innerTrimLoop(IIntegerI i){ return surface.innerTrimLoop(i); }
    public ITrimCurveI innerTrim(int i, int j){ return surface.innerTrim(i,j); }
    public ITrimCurveI innerTrim(IIntegerI i, IIntegerI j){ return surface.innerTrim(i,j); }
    
    public int outerTrimLoopNum(){ return surface.outerTrimLoopNum(); }
    //public IIntegerI outerTrimLoopNumR(){ return surface.outerTrimLoopNumR(); }
    public int outerTrimLoopNum(ISwitchE e){ return outerTrimLoopNum(); }
    public IIntegerI outerTrimLoopNum(ISwitchR r){ return surface.outerTrimLoopNum(r); }
    
    public int outerTrimNum(int i){ return surface.outerTrimNum(i); }
    public IIntegerI outerTrimNum(IIntegerI i){ return surface.outerTrimNum(i); }
    public ITrimCurveI[] outerTrimLoop(int i){ return surface.outerTrimLoop(i); }
    public ITrimCurveI[] outerTrimLoop(IIntegerI i){ return surface.outerTrimLoop(i); }
    public ITrimCurveI outerTrim(int i, int j){ return surface.outerTrim(i,j); }
    public ITrimCurveI outerTrim(IIntegerI i, IIntegerI j){ return surface.outerTrim(i,j); }
    
    public boolean hasDefaultTrim(){ return surface.hasDefaultTrim(); }
    //public IBoolI hasDefaultTrimR(){ return surface.hasDefaultTrimR(); }
    public boolean hasDefaultTrim(ISwitchE e){ return hasDefaultTrim(); }
    public IBoolI hasDefaultTrim(ISwitchR r){ return surface.hasDefaultTrim(r); }
    
    public ISurfaceR addInnerTrimLoop(ITrimCurveI trim){
	surface.addInnerTrimLoop(trim); updateGraphic(); return this;
    }
    public ISurfaceR addOuterTrimLoop(ITrimCurveI trim){
	surface.addOuterTrimLoop(trim); updateGraphic(); return this;
    }
    
    public ISurfaceR addInnerTrimLoop(ITrimCurveI[] trim){
	surface.addInnerTrimLoop(trim); updateGraphic(); return this;
    }
    public ISurfaceR addOuterTrimLoop(ITrimCurveI[] trim){
	surface.addOuterTrimLoop(trim); updateGraphic(); return this;
    }
    
    public ISurfaceR addInnerTrimLoop(ICurveI trim){
	surface.addInnerTrimLoop(trim); updateGraphic(); return this;
    }
    public ISurfaceR addOuterTrimLoop(ICurveI trim){
	surface.addOuterTrimLoop(trim); updateGraphic(); return this;
    }
    
    public ISurfaceR addInnerTrimLoop(ICurveI trim, boolean deleteInput){
	surface.addInnerTrimLoop(trim,deleteInput); updateGraphic(); return this;
    }
    public ISurfaceR addOuterTrimLoop(ICurveI trim, boolean deleteInput){
	surface.addOuterTrimLoop(trim,deleteInput); updateGraphic(); return this;
    }
    
    public ISurfaceR addInnerTrimLoop(ICurveI[] trim){
	surface.addInnerTrimLoop(trim); updateGraphic(); return this;
    }
    public ISurfaceR addOuterTrimLoop(ICurveI[] trim){
	surface.addOuterTrimLoop(trim); updateGraphic(); return this;
    }
    
    public ISurfaceR addInnerTrimLoop(ICurveI[] trim, boolean deleteInput){
	surface.addInnerTrimLoop(trim,deleteInput); updateGraphic(); return this;
    }
    public ISurfaceR addOuterTrimLoop(ICurveI[] trim, boolean deleteInput){
	surface.addOuterTrimLoop(trim,deleteInput); updateGraphic(); return this;
    }
    
    
    public ISurfaceR clearInnerTrim(){ surface.clearInnerTrim(); return this; }
    public ISurfaceR clearOuterTrim(){ surface.clearOuterTrim(); return this; }
    public ISurfaceR clearTrim(){ surface.clearTrim(); return this; }
    
    
    public boolean isFlat(){ return surface.isFlat(); }
    //public IBoolI isFlatR(){ return surface.isFlatR(); }
    public boolean isFlat(ISwitchE e){ return surface.isFlat(); }
    //public IBoolI isFlat(ISwitchR r){ return surface.isFlat(r); }
    public IBoolI isFlat(ISwitchR r){ return new IBoolR(new IsFlat(surface)); } // need to be checked
    
    public boolean isUClosed(){ return surface.isUClosed(); }
    public boolean isUClosed(ISwitchE e){ return surface.isUClosed(); }
    //public IBoolI isUClosed(ISwitchR r){ return surface.isUClosed(r); } // should have new UClosed(this)
    public IBoolI isUClosed(ISwitchR r){ return new IBoolR(new IsUClosed(surface)); } // need to be checked
    
    public boolean isVClosed(){ return surface.isVClosed(); }
    public boolean isVClosed(ISwitchE e){ return surface.isVClosed(); }
    //public IBoolI isVClosed(ISwitchR r){ return surface.isVClosed(r); } // should have new VClosed(this)
    public IBoolI isVClosed(ISwitchR r){ return new IBoolR(new IsVClosed(surface)); } // need to be checked
    
    //public boolean isInsideTrim(double u, double v);
    //public boolean isInsideTrim(IVec2 v);
    //public boolean isInsideTrim(IVec2I v);
    
    
    /*********************************************************************************
     * transformation methods; API of ITransformable interface
     *********************************************************************************/
    
    public ISurfaceR add(double x, double y, double z){ surface.add(x,y,z); return this; }
    public ISurfaceR add(IDoubleI x, IDoubleI y, IDoubleI z){ surface.add(x,y,z); return this; }
    public ISurfaceR add(IVecI v){ surface.add(v); return this; }
    public ISurfaceR sub(double x, double y, double z){ surface.sub(x,y,z); return this; }
    public ISurfaceR sub(IDoubleI x, IDoubleI y, IDoubleI z){ surface.sub(x,y,z); return this; }
    public ISurfaceR sub(IVecI v){ surface.sub(v); return this; }
    public ISurfaceR mul(IDoubleI v){ surface.mul(v); return this; }
    public ISurfaceR mul(double v){ surface.mul(v); return this; }
    public ISurfaceR div(IDoubleI v){ surface.div(v); return this; }
    public ISurfaceR div(double v){ surface.div(v); return this; }
    
    public ISurfaceR neg(){ surface.neg(); return this; }
    /** alias of neg */
    public ISurfaceR flip(){ return neg(); }
    
    
    /** scale add */
    public ISurfaceR add(IVecI v, double f){ surface.add(v,f); return this; }
    public ISurfaceR add(IVecI v, IDoubleI f){ surface.add(v,f); return this; }
    /** scale add alias */
    public ISurfaceR add(double f, IVecI v){ return add(v,f); }
    public ISurfaceR add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public ISurfaceR rot(IDoubleI angle){ surface.rot(angle); return this; }
    public ISurfaceR rot(double angle){ surface.rot(angle); return this; }
    
    public ISurfaceR rot(IVecI axis, IDoubleI angle){ surface.rot(axis,angle); return this; }
    public ISurfaceR rot(IVecI axis, double angle){ surface.rot(axis,angle); return this; }
    
    public ISurfaceR rot(IVecI center, IVecI axis, IDoubleI angle){ surface.rot(center,axis,angle); return this; }
    public ISurfaceR rot(IVecI center, IVecI axis, double angle){ surface.rot(center,axis,angle); return this; }
    
    /** rotate to destination direction vector */
    public ISurfaceR rot(IVecI axis, IVecI destDir){ surface.rot(axis,destDir); return this; }
    /** rotate to destination point location */
    public ISurfaceR rot(IVecI center, IVecI axis, IVecI destPt){ surface.rot(center,axis,destPt); return this; }
    
    public ISurfaceR rot2(IDoubleI angle){ surface.rot(angle); return this; }
    public ISurfaceR rot2(double angle){ surface.rot(angle); return this; }
    public ISurfaceR rot2(IVecI center, IDoubleI angle){ surface.rot2(center,angle); return this; }
    public ISurfaceR rot2(IVecI center, double angle){ surface.rot2(center,angle); return this; }
    /** rotation on xy-plane to destination direction vector */
    public ISurfaceR rot2(IVecI destDir){ surface.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */
    public ISurfaceR rot2(IVecI center, IVecI destPt){ surface.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    public ISurfaceR scale(IDoubleI f){ return mul(f); }
    public ISurfaceR scale(double f){ return mul(f); }
    
    public ISurfaceR scale(IVecI center, IDoubleI f){ surface.scale(center,f); return this; }
    public ISurfaceR scale(IVecI center, double f){ surface.scale(center,f); return this; }
    
    /** scale only in 1 direction */
    public ISurfaceR scale1d(IVecI axis, double f){ surface.scale1d(axis,f); return this; }
    public ISurfaceR scale1d(IVecI axis, IDoubleI f){ surface.scale1d(axis,f); return this; }
    public ISurfaceR scale1d(IVecI center, IVecI axis, double f){
	surface.scale1d(center,axis,f); return this;
    }
    public ISurfaceR scale1d(IVecI center, IVecI axis, IDoubleI f){
	surface.scale1d(center,axis,f); return this;
    }
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public ISurfaceR ref(IVecI planeDir){ surface.ref(planeDir); return this; }
    public ISurfaceR ref(IVecI center, IVecI planeDir){ surface.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    public ISurfaceR mirror(IVecI planeDir){ return ref(planeDir); }
    public ISurfaceR mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    public ISurfaceR shear(double sxy, double syx, double syz,
			  double szy, double szx, double sxz){
	surface.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    public ISurfaceR shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			  IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	surface.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public ISurfaceR shear(IVecI center, double sxy, double syx, double syz,
			  double szy, double szx, double sxz){
	surface.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public ISurfaceR shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			  IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	surface.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    public ISurfaceR shearXY(double sxy, double syx){
	surface.shearXY(sxy,syx); return this;
    }
    public ISurfaceR shearXY(IDoubleI sxy, IDoubleI syx){
	surface.shearXY(sxy,syx); return this;
    }	
    public ISurfaceR shearXY(IVecI center, double sxy, double syx){
	surface.shearXY(center,sxy,syx); return this;
    }
    public ISurfaceR shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	surface.shearXY(center,sxy,syx); return this;
    }
    
    public ISurfaceR shearYZ(double syz, double szy){
	surface.shearYZ(syz,szy); return this;
    }
    public ISurfaceR shearYZ(IDoubleI syz, IDoubleI szy){
	surface.shearYZ(syz,szy); return this;
    }
    public ISurfaceR shearYZ(IVecI center, double syz, double szy){
	surface.shearYZ(center,syz,szy); return this;
    }
    public ISurfaceR shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	surface.shearYZ(center,syz,szy); return this;
    }
    
    public ISurfaceR shearZX(double szx, double sxz){
	surface.shearZX(szx,sxz); return this;
    }
    public ISurfaceR shearZX(IDoubleI szx, IDoubleI sxz){
	surface.shearZX(szx,sxz); return this;
    }
    public ISurfaceR shearZX(IVecI center, double szx, double sxz){
	surface.shearZX(center,szx,sxz); return this;
    }
    public ISurfaceR shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	surface.shearZX(center,szx,sxz); return this;
    }
    
    
    /** mv() is alias of add() */
    public ISurfaceR mv(double x, double y, double z){ return add(x,y,z); }
    public ISurfaceR mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ISurfaceR mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public ISurfaceR cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public ISurfaceR cp(double x, double y, double z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    public ISurfaceR cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    public ISurfaceR cp(IVecI v){ return dup().add(v); }
    
    /** translate() is alias of add() */
    public ISurfaceR translate(double x, double y, double z){ return add(x,y,z); }
    public ISurfaceR translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ISurfaceR translate(IVecI v){ return add(v); }
    
    public ISurfaceR transform(IMatrix3I mat){ surface.transform(mat); return this; }
    public ISurfaceR transform(IMatrix4I mat){ surface.transform(mat); return this; }
    public ISurfaceR transform(IVecI xvec, IVecI yvec, IVecI zvec){
	surface.transform(xvec,yvec,zvec); return this;
    }
    public ISurfaceR transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	surface.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /*********************************************************************************
     * methods of IObject
     *********************************************************************************/
    
    
    public ISurfaceR name(String nm){ super.name(nm); return this; }
    public ISurfaceR layer(ILayer l){ super.layer(l); return this; }
    
    public ISurfaceR hide(){ super.hide(); return this; }
    public ISurfaceR show(){ super.show(); return this; }
    
    
    
    public ISurfaceR clr(IColor c){ super.clr(c); return this; }
    public ISurfaceR clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public ISurfaceR clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public ISurfaceR clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public ISurfaceR clr(Color c){ super.clr(c); return this; }
    public ISurfaceR clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public ISurfaceR clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public ISurfaceR clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public ISurfaceR clr(int gray){ super.clr(gray); return this; }
    public ISurfaceR clr(float fgray){ super.clr(fgray); return this; }
    public ISurfaceR clr(double dgray){ super.clr(dgray); return this; }
    public ISurfaceR clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public ISurfaceR clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public ISurfaceR clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public ISurfaceR clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public ISurfaceR clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public ISurfaceR clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public ISurfaceR clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public ISurfaceR clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public ISurfaceR clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public ISurfaceR hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public ISurfaceR hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public ISurfaceR hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public ISurfaceR hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public ISurfaceR setColor(Color c){ super.setColor(c); return this; }
    public ISurfaceR setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public ISurfaceR setColor(int gray){ super.setColor(gray); return this; }
    public ISurfaceR setColor(float fgray){ super.setColor(fgray); return this; }
    public ISurfaceR setColor(double dgray){ super.setColor(dgray); return this; }
    public ISurfaceR setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public ISurfaceR setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public ISurfaceR setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public ISurfaceR setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public ISurfaceR setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public ISurfaceR setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public ISurfaceR setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public ISurfaceR setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public ISurfaceR setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public ISurfaceR setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public ISurfaceR setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public ISurfaceR setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public ISurfaceR setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public ISurfaceR weight(double w){ super.weight(w); return this; }
    public ISurfaceR weight(float w){ super.weight(w); return this; }
    
    
    
    static public class IsFlat extends IParameterObject implements IBoolOp{
        public ISurfaceOp srf;
        public IsFlat(ISurfaceOp s){ srf=s; }
        public boolean x(){ return srf.get().isFlat(); }
        public IBool get(){ return srf.get().isFlat((Ir)null); }
    }
    
    static public class IsUClosed extends IParameterObject implements IBoolOp{
        public ISurfaceOp srf;
        public IsUClosed(ISurfaceOp s){ srf=s; }
        public boolean x(){ return srf.get().isUClosed(); }
        public IBool get(){ return srf.get().isUClosed((Ir)null); }
    }
    
    static public class IsVClosed extends IParameterObject implements IBoolOp{
        public ISurfaceOp srf;
        public IsVClosed(ISurfaceOp s){ srf=s; }
        public boolean x(){ return srf.get().isVClosed(); }
        public IBool get(){ return srf.get().isVClosed((Ir)null); }
    }
    
    static public class UV extends IParameterObject implements IVec2Op{
        public ISurfaceOp srf;
        public IVecI pt;
        public UV(ISurfaceOp s, IVecI p){ srf=s; pt=p; }
	public IVec2 get(){ return srf.get().uv(pt); }
    }
    
    static public class UV2 extends IParameterObject implements IVec2Op{
        public ISurfaceOp srf;
        public IVec2I pt;
        public UV2(ISurfaceOp s, IVec2I p){ srf=s; pt=p; }
	public IVec2 get(){ return srf.get().uv(pt); }
    }
    
    static public class Pt extends IParameterObject implements IVecOp{
	public ISurfaceOp srf;
	public IVec2Op uv;
	public Pt(ISurfaceOp s, IVec2Op uvval){ srf=s; uv=uvval; }
	public IVec get(){ return srf.get().pt(uv.get()); }
    }
    
    static public class UTan extends IParameterObject implements IVecOp{
	public ISurfaceOp srf;
	public IVec2Op uv;
	public UTan(ISurfaceOp s, IVec2Op uvval){ srf=s; uv=uvval; }
	public IVec get(){ return srf.get().utan(uv.get()); }
    }
    
    static public class VTan extends IParameterObject implements IVecOp{
	public ISurfaceOp srf;
	public IVec2Op uv;
	public VTan(ISurfaceOp s, IVec2Op uvval){ srf=s; uv=uvval; }
	public IVec get(){ return srf.get().vtan(uv.get()); }
    }
    
    static public class Normal extends IParameterObject implements IVecOp{
	public ISurfaceOp srf;
	public IVec2Op uv;
	public Normal(ISurfaceOp s, IVec2Op uvval){ srf=s; uv=uvval; }
	public IVec get(){ return srf.get().nml(uv.get()); }
    }
    
}



