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
import java.awt.Color;

/**
   Class of a curve which consists of multiple curves.
   Implementation is very temporary.
   To be completed later.
   
   @author Satoru Sugihara
*/

public class IPolycurve extends IObject /*implements ICurveI*/{
    
    // currently just implemented as a group of ICurve
    
    public ArrayList<ICurve> curves; // public? // not ArrayList<ICurveI>?
    //public ArrayList<IDouble> knots;
    
    public IPolycurve(){ this((IServerI)null); }
    public IPolycurve(IServerI s){ super(s); }
    
    public IPolycurve(ICurve[] crvs){ this(null,crvs); }
    public IPolycurve(IServerI s, ICurve[] crvs){
	super(s);
	curves = new ArrayList<ICurve>();
	for(int i=0; i<crvs.length; i++){ curves.add(crvs[i]); }
    }
    
    public IPolycurve(ArrayList<ICurve> crvs){ this(null,crvs); }
    public IPolycurve(IServerI s, ArrayList<ICurve> crvs){
	super(s);
	curves = crvs;
    }
    public IPolycurve(IPolycurve crv){
	super(crv);
	curves = new ArrayList<ICurve>(crv.curves.size());
	for(int i=0; i<crv.curves.size(); i++){ curves.add(crv.curves.get(i)); }
    }
    public IPolycurve(IServerI s, IPolycurve crv){
	super(s,crv);
	curves = new ArrayList<ICurve>(crv.curves.size());
	for(int i=0; i<crv.curves.size(); i++){ curves.add(crv.curves.get(i)); }
    }
    
    
    @Override public IPolycurve dup(){ return new IPolycurve(this); }
    
    @Override public void del(){
	super.del();
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).del();
    }
    
    
    public int curveNum(){ return curves.size(); }
    public ICurve curve(int i){
	return curves.get(i);
    }
    public ICurve[] curves(){ return curves.toArray(new ICurve[curves.size()]); }
    public boolean contains(ICurve c){ return curves.contains(c); }
    
    public boolean isClosed(){ 
	return curves.get(0).start().eq(curves.get(curves.size()-1).end());
    }
    
    
    

    /****************************************************
     * IObject methods
     ****************************************************/
    
    public IPolycurve name(String nm){
        super.name(nm);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).name(nm+"_s"+i);
        return this;
    }
    public IPolycurve layer(ILayer l){
        super.layer(l);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).layer(l);
        return this;
    }
    
    public boolean visible(){
	for(int i=0;curves!=null&&i<curves.size();i++) 
	    if(curves.get(i).visible()) return true;
	return false; // false if everything is false
    }
    
    public IPolycurve hide(){
        super.hide();
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).hide();
        return this;
    }
    public IPolycurve show(){
        super.show();
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).show();
        return this;
    }
    public IPolycurve clr(IColor c){
        super.clr(c);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(c);
        return this;
    }
    public IPolycurve clr(IColor c, int alpha){
        super.clr(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(c,alpha);
        return this;
    }
    public IPolycurve clr(IColor c, float alpha){
        super.clr(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(c,alpha);
        return this;
    }
    public IPolycurve clr(IColor c, double alpha){
        super.clr(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(c,alpha);
        return this;
    }
    public IPolycurve clr(Color c){
        super.clr(c);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(c);
        return this;
    }
    public IPolycurve clr(Color c, int alpha){
        super.clr(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(c,alpha);
        return this;
    }
    public IPolycurve clr(Color c, float alpha){
        super.clr(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(c,alpha);
        return this;
    }
    public IPolycurve clr(Color c, double alpha){
        super.clr(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(c,alpha);
        return this;
    }
    public IPolycurve clr(int gray){
        super.clr(gray);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(gray);
        return this;
    }
    public IPolycurve clr(float fgray){
        super.clr(fgray);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(fgray);
        return this;
    }
    public IPolycurve clr(double dgray){
        super.clr(dgray);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(dgray);
        return this;
    }
    public IPolycurve clr(int gray, int alpha){
        super.clr(gray,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(gray,alpha);
        return this;
    }
    public IPolycurve clr(float fgray, float falpha){
        super.clr(fgray,falpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(fgray,falpha);
        return this;
    }
    public IPolycurve clr(double dgray, double dalpha){
        super.clr(dgray,dalpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(dgray,dalpha);
        return this;
    }
    public IPolycurve clr(int r, int g, int b){
        super.clr(r,g,b);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(r,g,b);
        return this;
    }
    public IPolycurve clr(float fr, float fg, float fb){
        super.clr(fr,fg,fb);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(fr,fg,fb);
        return this;
    }
    public IPolycurve clr(double dr, double dg, double db){
        super.clr(dr,dg,db);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(dr,dg,db);
        return this;
    }
    public IPolycurve clr(int r, int g, int b, int a){
        super.clr(r,g,b,a);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(r,g,b,a);
        return this;
    }
    public IPolycurve clr(float fr, float fg, float fb, float fa){
        super.clr(fr,fg,fb,fa);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(fr,fg,fb,fa);
        return this;
    }
    public IPolycurve clr(double dr, double dg, double db, double da){
        super.clr(dr,dg,db,da);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).clr(dr,dg,db,da);
        return this;
    }
    public IPolycurve hsb(float h, float s, float b, float a){
        super.hsb(h,s,b,a);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).hsb(h,s,b,a);
        return this;
    }
    public IPolycurve hsb(double h, double s, double b, double a){
        super.hsb(h,s,b,a);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).hsb(h,s,b,a);
        return this;
    }
    public IPolycurve hsb(double h, double s, double b){
        super.hsb(h,s,b);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).hsb(h,s,b);
        return this;
    }
    
    
    public IPolycurve setColor(IColor c){
        super.setColor(c);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(c);
        return this;
    }
    public IPolycurve setColor(IColor c, int alpha){
        super.setColor(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(c,alpha);
        return this;
    }
    public IPolycurve setColor(IColor c, float alpha){
        super.setColor(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(c,alpha);
        return this;
    }
    public IPolycurve setColor(IColor c, double alpha){
        super.setColor(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(c,alpha);
        return this;
    }
    public IPolycurve setColor(Color c){
        super.setColor(c);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(c);
        return this;
    }
    public IPolycurve setColor(Color c, int alpha){
        super.setColor(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(c,alpha);
        return this;
    }
    public IPolycurve setColor(Color c, float alpha){
        super.setColor(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(c,alpha);
        return this;
    }
    public IPolycurve setColor(Color c, double alpha){
        super.setColor(c,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(c,alpha);
        return this;
    }
    public IPolycurve setColor(int gray){
        super.setColor(gray);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(gray);
        return this;
    }
    public IPolycurve setColor(float fgray){
        super.setColor(fgray);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(fgray);
        return this;
    }
    public IPolycurve setColor(double dgray){
        super.setColor(dgray);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(dgray);
        return this;
    }
    public IPolycurve setColor(int gray, int alpha){
        super.setColor(gray,alpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(gray,alpha);
        return this;
    }
    public IPolycurve setColor(float fgray, int falpha){
        super.setColor(fgray,falpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(fgray,falpha);
        return this;
    }
    public IPolycurve setColor(double dgray, double dalpha){
        super.setColor(dgray,dalpha);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(dgray,dalpha);
        return this;
    }
    public IPolycurve setColor(int r, int g, int b){
        super.setColor(r,g,b);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(r,g,b);
        return this;
    }
    public IPolycurve setColor(float fr, float fg, float fb){
        super.setColor(fr,fg,fb);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(fr,fg,fb);
        return this;
    }
    public IPolycurve setColor(double dr, double dg, double db){
        super.setColor(dr,dg,db);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(dr,dg,db);
        return this;
    }
    public IPolycurve setColor(int r, int g, int b, int a){
        super.setColor(r,g,b,a);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(r,g,b,a);
        return this;
    }
    public IPolycurve setColor(float fr, float fg, float fb, float fa){
        super.setColor(fr,fg,fb,fa);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(fr,fg,fb,fa);
        return this;
    }
    public IPolycurve setColor(double dr, double dg, double db, double da){
        super.setColor(dr,dg,db,da);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setColor(dr,dg,db,da);
        return this;
    }
    public IPolycurve setHSBColor(float h, float s, float b, float a){
        super.setHSBColor(h,s,b,a);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setHSBColor(h,s,b,a);
        return this;
    }
    public IPolycurve setHSBColor(double h, double s, double b, double a){
        super.setHSBColor(h,s,b,a);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setHSBColor(h,s,b,a);
        return this;
    }
    public IPolycurve setHSBColor(float h, float s, float b){
        super.setHSBColor(h,s,b);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setHSBColor(h,s,b);
        return this;
    }
    public IPolycurve setHSBColor(double h, double s, double b){
        super.setHSBColor(h,s,b);
        for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).setHSBColor(h,s,b);
        return this;
    }    
    
    public IPolycurve weight(double w){ return weight((float)w); }
    public IPolycurve weight(float w){
	super.weight(w);
	for(int i=0;curves!=null&&i<curves.size();i++) curves.get(i).weight(w);
	return this;
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
