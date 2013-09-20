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
   3 point curve with straightener force inside.
   
   @author Satoru Sugihara
*/
public class IStraightenerCurve extends ICurve implements IStraightenerI{
    
    IStraightener straightener;
    
    public IStraightenerCurve(IParticleI p1, IParticleI p2, IParticleI p3){
	super(new IVec[]{ p1.pos(), p2.pos(), p3.pos()}, 2);
	straightener = new IStraightener(p1,p2,p3);
	straightener.target(this);
    }
    
    public IStraightenerCurve(IParticleGeo p1, IParticleGeo p2, IParticleGeo p3){
	super(new IVec[]{ p1.pos(), p2.pos(), p3.pos()}, 2);
	straightener = new IStraightener(p1,p2,p3);
	straightener.target(this);
    }
    
    public IStraightenerCurve(IVec p1, IVec p2, IVec p3){
	super(new IVec[]{ p1, p2, p3 }, 2);
	straightener = new IStraightener(p1,p2,p3);
	straightener.target(this);
    }
    
    public IStraightenerCurve(IVecI p1, IVecI p2, IVecI p3){
	super(new IVecI[]{ p1, p2, p3 }, 2);
	straightener = new IStraightener(p1,p2,p3);
	straightener.target(this);
    }
    
    public double tension(){ return straightener.tension(); }
    public IStraightenerCurve tension(double tension){ straightener.tension(tension); return this; }

    public boolean constant(){ return straightener.constant(); }
    public IStraightenerCurve constant(boolean cnst){ straightener.constant(cnst); return this; }
    
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension. if constant is set, maxTension is ignored. */
    public double maxTension(){ return straightener.maxTension(); }
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension if constant is set, maxTension is ignored. */
    public IStraightenerCurve maxTension(double maxTension){ straightener.maxTension(maxTension); return this; }
    

    /** getting end point. i==0 or i==1 or i==2*/
    public IParticleI pt(int i){ return straightener.pt(i); }
    /** alias of pt(int) */
    public IParticleI particle(int i){ return straightener.particle(i); }
    /** position of particle(i) */
    public IVec pos(int i){ return straightener.pos(i); }
    
    /** getting end point1. */
    public IParticleI pt1(){ return straightener.pt1(); }
    /** alias of pt1() */
    public IParticleI particle1(){ return straightener.particle1(); }
    /** position of particle1() */
    public IVec pos1(){  return straightener.pos1(); }
    
    /** getting end point2. */
    public IParticleI pt2(){ return straightener.pt2(); }
    /** alias of pt2() */
    public IParticleI particle2(){ return straightener.particle2(); }
    /** position of particle2() */
    public IVec pos2(){  return straightener.pos2(); }
    
    /** getting end point3. */
    public IParticleI pt3(){ return straightener.pt3(); }
    /** alias of pt3() */
    public IParticleI particle3(){ return straightener.particle3(); }
    /** position of particle3() */
    public IVec pos3(){  return straightener.pos3(); }
    
    
    
    public IStraightenerCurve parent(IObject par){ straightener.parent(par); return this; }
    public IStraightenerCurve target(IObject targetObj){ straightener.target(targetObj); return this; }
    public IStraightenerCurve removeTarget(int i){ straightener.removeTarget(i); return this; }
    public IStraightenerCurve removeTarget(IObject obj){ straightener.removeTarget(obj); return this; }
    
    
    /******************************************************************************
     * IObject methods
     ******************************************************************************/
    
    public IStraightenerCurve name(String nm){ super.name(nm); return this; }
    public IStraightenerCurve layer(ILayer l){ super.layer(l); return this; }
    public IStraightenerCurve layer(String l){ super.layer(l); return this; }

    public IStraightenerCurve attr(IAttribute at){ super.attr(at); return this; }
    
    
    public IStraightenerCurve hide(){ super.hide(); return this; }
    public IStraightenerCurve show(){ super.show(); return this; }
    
    public IStraightenerCurve clr(IColor c){ super.clr(c); return this; }
    public IStraightenerCurve clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IStraightenerCurve clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IStraightenerCurve clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    
    public IStraightenerCurve clr(Color c){ super.clr(c); return this; }
    public IStraightenerCurve clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IStraightenerCurve clr(int gray){ super.clr(gray); return this; }
    public IStraightenerCurve clr(float fgray){ super.clr(fgray); return this; }
    public IStraightenerCurve clr(double dgray){ super.clr(dgray); return this; }
    public IStraightenerCurve clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IStraightenerCurve clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IStraightenerCurve clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IStraightenerCurve clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IStraightenerCurve clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IStraightenerCurve clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IStraightenerCurve clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IStraightenerCurve clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IStraightenerCurve clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IStraightenerCurve hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IStraightenerCurve hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IStraightenerCurve hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IStraightenerCurve hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IStraightenerCurve setColor(Color c){ super.setColor(c); return this; }
    public IStraightenerCurve setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IStraightenerCurve setColor(int gray){ super.setColor(gray); return this; }
    public IStraightenerCurve setColor(float fgray){ super.setColor(fgray); return this; }
    public IStraightenerCurve setColor(double dgray){ super.setColor(dgray); return this; }
    public IStraightenerCurve setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IStraightenerCurve setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IStraightenerCurve setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IStraightenerCurve setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IStraightenerCurve setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IStraightenerCurve setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IStraightenerCurve setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IStraightenerCurve setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IStraightenerCurve setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IStraightenerCurve setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IStraightenerCurve setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IStraightenerCurve setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IStraightenerCurve setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public IStraightenerCurve weight(double w){ super.weight(w); return this; }
    public IStraightenerCurve weight(float w){ super.weight(w); return this; }
    
    
}
