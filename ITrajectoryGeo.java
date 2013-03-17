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
   IDyanmics class to have trajectory curve object.
   In every update, specified vector position is added to the trajectory curve.
   
   @author Satoru Sugihara
*/
public class ITrajectoryGeo extends IDynamicsBase implements ITrajectoryI{
    public int degree;
    public ICurve curve;
    public IVecI pos;
    
    public ITrajectoryGeo(IVecI pos){ this.pos = pos; degree=1; initTrajectory(); }
    public ITrajectoryGeo(IVecI pos, int curveDegree){ this.pos = pos; degree=curveDegree; initTrajectory(); }
    
    public ITrajectoryGeo(IObject obj, IVecI pos){ super(obj); this.pos = pos; degree=1; initTrajectory(); }
    public ITrajectoryGeo(IObject obj, IVecI pos, int curveDegree){ super(obj); this.pos = pos; degree=curveDegree; initTrajectory(); }
    
    public ITrajectoryGeo(IPointAgent agent){ super(agent); pos = agent.pos(); degree=1; initTrajectory(); }
    public ITrajectoryGeo(IPointAgent agent, int curveDegree){ super(agent); pos = agent.pos(); degree=curveDegree; initTrajectory(); }
    
    public void initTrajectory(){
	update(); // add first point // added 2013/02/13
    }
    
    public int deg(){ return degree; }
    public ITrajectoryGeo deg(int deg){ degree=deg; return this; }
    
    public void hide(){ if(curve!=null) curve.hide(); }
    public void show(){ if(curve!=null) curve.show(); }
    public void del(){ if(curve!=null){ curve.del(); } if(parent!=null){ parent.deleteDynamics(this); } }
    
    public ICurve curve(){ return curve; }
    public ICurve trajectory(){ return curve(); }
    
    /** add a point at the end of trajectory curve */
    public ITrajectoryGeo addCP(IVecI pt){
	if(curve==null){
	    curve = new ICurve(pt);
	    if(parent!=null){
		if(parent.attr()!=null){ curve.attr(parent.attr()); }
		else if(parent.clr()!=null){ curve.clr(parent.clr()); }
	    }
	}
	else{ curve.addCP(pt, degree); }
	return this;
    }
    
    public void update(){
	addCP(pos.get().dup());
	/*
	if(curve==null){
	    curve = new ICurve(pos.get().dup());
	    if(parent!=null){
		if(parent.attr()!=null){ curve.attr(parent.attr()); }
		else if(parent.clr()!=null){ curve.clr(parent.clr()); }
	    }
	}
	else{ curve.addCP(pos.get().dup(), degree); }
	*/
    }
    
    
    // Curve Object methods
    
    public ITrajectoryGeo clr(IColor c){ if(curve!=null) curve.clr(c); return this; }
    public ITrajectoryGeo clr(IColor c, int alpha){ if(curve!=null) curve.clr(c,alpha); return this; }
    public ITrajectoryGeo clr(IColor c, float alpha){ if(curve!=null) curve.clr(c,alpha); return this; }
    public ITrajectoryGeo clr(IColor c, double alpha){ if(curve!=null) curve.clr(c,alpha); return this; }
    //public ITrajectoryGeo clr(Color c){ if(curve!=null) curve.clr(c); return this; }
    //public ITrajectoryGeo clr(Color c, int alpha){ if(curve!=null) curve.clr(c,alpha); return this; }
    //public ITrajectoryGeo clr(Color c, float alpha){ if(curve!=null) curve.clr(c,alpha); return this; }
    //public ITrajectoryGeo clr(Color c, double alpha){ if(curve!=null) curve.clr(c,alpha); return this; }
    public ITrajectoryGeo clr(int gray){ if(curve!=null) curve.clr(gray); return this; }
    public ITrajectoryGeo clr(float fgray){ if(curve!=null) curve.clr(fgray); return this; }
    public ITrajectoryGeo clr(double dgray){ if(curve!=null) curve.clr(dgray); return this; }
    public ITrajectoryGeo clr(int gray, int alpha){ if(curve!=null) curve.clr(gray,alpha); return this; }
    public ITrajectoryGeo clr(float fgray, float falpha){ if(curve!=null) curve.clr(fgray,falpha); return this; }
    public ITrajectoryGeo clr(double dgray, double dalpha){ if(curve!=null) curve.clr(dgray,dalpha); return this; }
    public ITrajectoryGeo clr(int r, int g, int b){ if(curve!=null) curve.clr(r,g,b); return this; }
    public ITrajectoryGeo clr(float fr, float fg, float fb){ if(curve!=null) curve.clr(fr,fg,fb); return this; }
    public ITrajectoryGeo clr(double dr, double dg, double db){ if(curve!=null) curve.clr(dr,dg,db); return this; }
    public ITrajectoryGeo clr(int r, int g, int b, int a){ if(curve!=null) curve.clr(r,g,b,a); return this; }
    public ITrajectoryGeo clr(float fr, float fg, float fb, float fa){ if(curve!=null) curve.clr(fr,fg,fb,fa); return this; }
    public ITrajectoryGeo clr(double dr, double dg, double db, double da){ if(curve!=null) curve.clr(dr,dg,db,da); return this; }
    public ITrajectoryGeo hsb(float h, float s, float b, float a){ if(curve!=null) curve.hsb(h,s,b,a); return this; }
    public ITrajectoryGeo hsb(double h, double s, double b, double a){ if(curve!=null) curve.hsb(h,s,b,a); return this; }
    public ITrajectoryGeo hsb(float h, float s, float b){ if(curve!=null) curve.hsb(h,s,b); return this; }
    public ITrajectoryGeo hsb(double h, double s, double b){ if(curve!=null) curve.hsb(h,s,b); return this; }
    
    public ITrajectoryGeo setColor(IColor c){ if(curve!=null) curve.setColor(c); return this; }
    public ITrajectoryGeo setColor(IColor c, int alpha){ if(curve!=null) curve.setColor(c,alpha); return this; }
    public ITrajectoryGeo setColor(IColor c, float alpha){ if(curve!=null) curve.setColor(c,alpha); return this; }
    public ITrajectoryGeo setColor(IColor c, double alpha){ if(curve!=null) curve.setColor(c,alpha); return this; }
    //public ITrajectoryGeo setColor(Color c){ if(curve!=null) curve.setColor(c); return this; }
    //public ITrajectoryGeo setColor(Color c, int alpha){ if(curve!=null) curve.setColor(c,alpha); return this; }
    //public ITrajectoryGeo setColor(Color c, float alpha){ if(curve!=null) curve.setColor(c,alpha); return this; }
    //public ITrajectoryGeo setColor(Color c, double alpha){ if(curve!=null) curve.setColor(c,alpha); return this; }
    public ITrajectoryGeo setColor(int gray){ if(curve!=null) curve.setColor(gray); return this; }
    public ITrajectoryGeo setColor(float fgray){ if(curve!=null) curve.setColor(fgray); return this; }
    public ITrajectoryGeo setColor(double dgray){ if(curve!=null) curve.setColor(dgray); return this; }
    public ITrajectoryGeo setColor(int gray, int alpha){ if(curve!=null) curve.setColor(gray,alpha); return this; }
    public ITrajectoryGeo setColor(float fgray, float falpha){ if(curve!=null) curve.setColor(fgray,falpha); return this; }
    public ITrajectoryGeo setColor(double dgray, double dalpha){ if(curve!=null) curve.setColor(dgray,dalpha); return this; }
    public ITrajectoryGeo setColor(int r, int g, int b){ if(curve!=null) curve.setColor(r,g,b); return this; }
    public ITrajectoryGeo setColor(float fr, float fg, float fb){ if(curve!=null) curve.setColor(fr,fg,fb); return this; }
    public ITrajectoryGeo setColor(double dr, double dg, double db){ if(curve!=null) curve.setColor(dr,dg,db); return this; }
    public ITrajectoryGeo setColor(int r, int g, int b, int a){ if(curve!=null) curve.setColor(r,g,b,a); return this; }
    public ITrajectoryGeo setColor(float fr, float fg, float fb, float fa){ if(curve!=null) curve.setColor(fr,fg,fb,fa); return this; }
    public ITrajectoryGeo setColor(double dr, double dg, double db, double da){ if(curve!=null) curve.setColor(dr,dg,db,da); return this; }
    public ITrajectoryGeo setHSBColor(float h, float s, float b, float a){ if(curve!=null) curve.setHSBColor(h,s,b,a); return this; }
    public ITrajectoryGeo setHSBColor(double h, double s, double b, double a){ if(curve!=null) curve.setHSBColor(h,s,b,a); return this; }
    public ITrajectoryGeo setHSBColor(float h, float s, float b){ if(curve!=null) curve.setHSBColor(h,s,b); return this; }
    public ITrajectoryGeo setHSBColor(double h, double s, double b){ if(curve!=null) curve.setHSBColor(h,s,b); return this; }
    
    public ITrajectoryGeo weight(double w){ if(curve!=null) curve.weight(w); return this; }
    public ITrajectoryGeo weight(float w){ if(curve!=null) curve.weight(w); return this; }

}
