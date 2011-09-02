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
    
    public ICurveGeo get(){ return curve.get(); } //?
    
    public ICurve dup(){ return new ICurve(this); }    
    
    
    public IVecI pt(IDoubleI u){ return curve.pt(u); }
    public IVecI pt(double u){ return curve.pt(u); }
    //public void pt(double u, IVec retval){ curve.pt(u,retval); }
    
    public IVecI tan(IDoubleI u){ return curve.tan(u); }
    public IVecI tan(double u){ return curve.tan(u); }
    //public void tan(double u, IVec retval){ curve.tan(u,retval); }
    
    public IVecI cp(int i){ return curve.cp(i); }
    public IVecI cp(IIntegerI i){ return curve.cp(i); }
    
    public IVecI ep(int i){ return curve.ep(i); }
    public IVecI ep(IIntegerI i){ return curve.ep(i); }
    
    public IVecI start(){ return curve.start(); }
    public IVecI end(){ return curve.end(); }
    public IVecI startCP(){ return curve.startCP(); }
    public IVecI endCP(){ return curve.endCP(); }

    
    public double knot(int i){ return curve.knot(i); }
    public IDoubleI knot(IIntegerI i){ return curve.knot(i); }
    
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
    
    public ICurveR name(String nm){ super.name(nm); return this; }
    public ICurveR layer(ILayer l){ super.layer(l); return this; }

    public ICurveR hide(){ super.hide(); return this; }
    public ICurveR show(){ super.show(); return this; }
        
   
    
    
    
    
    
    public ICurveR clr(Color c){ super.clr(c); return this; }
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
