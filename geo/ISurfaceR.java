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
   Reference class of NURBS surface to contain any instance of ISurfaceI.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
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
    
    public ISurfaceGeo get(){ return surface.get(); } // ?
    
    public ISurfaceR dup(){ return new ISurfaceR(this); }    
    
    public IVecI pt(IVec2I v){ return surface.pt(v); }
    public IVecI pt(IDoubleI u, IDoubleI v){ return surface.pt(u,v); }
    public IVecI pt(double u, double v){ return surface.pt(u,v); }
    
    /**
       @param u u coordinates in uv parameter space
       @param v v coordinates in uv parameter space
       @param n length in normal direction in 3D space
    */
    public IVecI pt(double u, double v, double n){ return surface.pt(u,v,n); }
    public IVecI pt(IDoubleI u, IDoubleI v, IDoubleI n){ return surface.pt(u,v,n); }
    public IVecI pt(IVecI v){ return surface.pt(v); }
    
    
    public IVecI utan(IVec2I v){ return surface.utan(v); }
    public IVecI utan(IDoubleI u, IDoubleI v){ return surface.utan(u,v); }
    public IVecI utan(double u, double v){ return surface.utan(u,v); }
    
    public IVecI vtan(IVec2I v){ return surface.vtan(v); }
    public IVecI vtan(IDoubleI u, IDoubleI v){ return surface.vtan(u,v); }
    public IVecI vtan(double u, double v){ return surface.vtan(u,v); }
    
    public IVecI normal(IVec2I v){ return surface.normal(v); }
    public IVecI normal(IDoubleI u, IDoubleI v){ return surface.normal(u,v); }
    public IVecI normal(double u, double v){ return surface.normal(u,v); }
    
    public IVecI nrml(IVec2I v){ return surface.nrml(v); }
    public IVecI nrml(IDoubleI u, IDoubleI v){ return surface.nrml(u,v); }
    public IVecI nrml(double u, double v){ return surface.nrml(u,v); }
    
    public IVecI cp(int i, int j){ return surface.cp(i,j); }
    public IVecI cp(IIntegerI i, IIntegerI j){ return surface.cp(i,j); }
    
    public IVecI ep(int i, int j){ return surface.ep(i,j); }
    public IVecI ep(IIntegerI i, IIntegerI j){ return surface.ep(i,j); }
    
    public IVecI corner(int i, int j){ return surface.corner(i,j); }
    public IVecI corner(IIntegerI i, IIntegerI j){ return surface.corner(i,j); }
    public IVecI cornerCP(int i, int j){ return surface.cornerCP(i,j); }
    public IVecI cornerCP(IIntegerI i, IIntegerI j){ return surface.cornerCP(i,j); }
    
    public double uknot(int i){ return surface.uknot(i); }
    public IDoubleI uknot(IIntegerI i){ return surface.uknot(i); }
    public double vknot(int i){ return surface.vknot(i); }
    public IDoubleI vknot(IIntegerI i){ return surface.vknot(i); }
    
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
    public IBoolI isFlat(ISwitchR r){ return surface.isFlat(r); }
    
    
    public ISurfaceR name(String nm){ super.name(nm); return this; }
    public ISurfaceR layer(ILayer l){ super.layer(l); return this; }
    
    public ISurfaceR hide(){ super.hide(); return this; }
    public ISurfaceR show(){ super.show(); return this; }
    
    
    
    public ISurfaceR clr(Color c){ super.clr(c); return this; }
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
    

}
