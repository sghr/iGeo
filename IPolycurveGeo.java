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

import java.util.ArrayList;

/**
   Class of a curve which consists of multiple curves.
   Because implementation is not completed yet, it's abstract class. 
   To be completed later.
   
   @author Satoru Sugihara
*/
abstract public class IPolycurveGeo implements ICurveI{
    
    public ArrayList<ICurveI> curves; // public?
    public ArrayList<IDoubleI> knots;
    
    public IPolycurveGeo(){ }
    
    public IPolycurveGeo(ICurveI[] crvs){
	curves = new ArrayList<ICurveI>();
	for(int i=0; i<crvs.length; i++){ curves.add(crvs[i]); }
    }
    
    public IPolycurveGeo(ArrayList<ICurveI> crvs){
	curves = crvs;
    }
    
    public IPolycurveGeo(IPolycurveGeo crv){
	curves = new ArrayList<ICurveI>(crv.curves.size());
	for(int i=0; i<crv.curves.size(); i++){ curves.add(crv.curves.get(i)); }
    }
    
    // to be implemented...
    /*
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
    
    public double knot(int i){ return curve.knot(i); }
    public IDoubleI knot(IIntegerI i){ return curve.knot(i); }
    
    public int knotNum(){ return curve.knotNum(); }
    //public IIntegerI knotNumR(){ return curve.knotNumR(); }
    public int knotNum(ISwitchE e){ return knotNum(); }
    public IIntegerI knotNum(ISwitchR r){ return curve.knotNum(r); }
    
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
    
    
    public ICurveR setColor(Color c){ super.setColor(c); return this; }
    public ICurveR setColor(int gray){ super.setColor(gray); return this; }
    public ICurveR setColor(float fgray){ super.setColor(fgray); return this; }
    public ICurveR setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public ICurveR setColor(float fgray, int falpha){ super.setColor(fgray,falpha); return this; }
    public ICurveR setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public ICurveR setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public ICurveR setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public ICurveR setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    
    */
}
