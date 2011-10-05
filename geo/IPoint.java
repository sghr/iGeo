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
   Class of point object.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IPoint extends IObject implements IVecI{
    //public IVecI pos;
    public IVec pos;
    
    public IPoint(){ pos = new IVec(); initPoint(null); }
    public IPoint(IVec v){ pos = v; initPoint(null); }
    public IPoint(IVecI v){ pos = v.get(); initPoint(null); }
    public IPoint(double x, double y, double z){ pos = new IVec(x,y,z); initPoint(null); }
    
    public IPoint(IServerI s){ super(s); pos = new IVec(0,0,0); initPoint(s); }
    public IPoint(IServerI s, IVec v){ super(s); pos = v; initPoint(s); }
    public IPoint(IServerI s, IVecI v){ super(s); pos = v.get(); initPoint(s); }
    public IPoint(IServerI s, double x, double y, double z){
	super(s); pos = new IVec(x,y,z); initPoint(s); 
    }
    
    public IPoint(IPoint p){
	super(p);
	pos = p.pos.dup();
	initPoint(p.server);
	setColor(p.getColor());
    }
    
    public IPoint(IServerI s, IPoint p){
	super(s,p);
	pos = p.pos.dup();
	initPoint(s);
	setColor(p.getColor());
    }
    
    protected void initPoint(IServerI s){
	parameter = pos;
	
	// // costly to use instanceof?
	//if(pos instanceof IVec) parameter = (IVec)pos;
	//else if(pos instanceof IVecR) parameter = (IVecR)pos;
	//else if(pos instanceof IVec4) parameter = (IVec4)pos;
	//else if(pos instanceof IVec4R) parameter = (IVec4R)pos;
	
	//addGraphic(new IPointGraphic(this));
	if(graphics==null) initGraphic(s); // not init when using copy constructor
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
	return new IPointGraphic(this);
    }
    
    public double x(){ return pos.x(); }
    public double y(){ return pos.y(); }
    public double z(){ return pos.z(); }
    public IVec get(){ return pos.get(); }
    
    public IPoint dup(){ return new IPoint(this); }
    
    public IVec2 to2d(){ return pos.to2d(); }
    public IVec4 to4d(){ return pos.to4d(); }
    public IVec4 to4d(double w){ return pos.to4d(w); }
    public IVec4 to4d(IDoubleI w){ return pos.to4d(w); }
    
    public IDouble getX(){ return pos.getX(); }
    public IDouble getY(){ return pos.getY(); }
    public IDouble getZ(){ return pos.getZ(); }
    
    public IPoint set(IVecI v){ pos.set(v); return this; }
    public IPoint set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IPoint set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }

    public IPoint add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IPoint add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IPoint add(IVecI v){ pos.add(v); return this; }
    
    public IPoint sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IPoint sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IPoint sub(IVecI v){ pos.sub(v); return this; }
    public IPoint mul(IDoubleI v){ pos.mul(v); return this; }
    public IPoint mul(double v){ pos.mul(v); return this; }
    public IPoint div(IDoubleI v){ pos.div(v); return this; }
    public IPoint div(double v){ pos.div(v); return this; }
    public IPoint neg(){ pos.neg(); return this; }
    public IPoint rev(){ return neg(); }
    public IPoint flip(){ return neg(); }
    
    public IPoint add(IVecI v, double f){ pos.add(v,f); return this; }
    public IPoint add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    
    public double dot(IVecI v){ return pos.dot(v); }
    public double dot(ISwitchE e, IVecI v){ return pos.dot(e,v); }
    public IDouble dot(ISwitchR r, IVecI v){ return pos.dot(r,v); }
    
    //public IVec cross(IVecI v){ return pos.cross(v); }
    public IPoint cross(IVecI v){ return dup().set(pos.cross(v)); }
    
    public double len(){ return pos.len(); }
    public double len(ISwitchE e){ return pos.len(e); }
    public IDouble len(ISwitchR r){ return pos.len(r); }
    
    public double len2(){ return pos.len2(); }
    public double len2(ISwitchE e){ return pos.len2(e); }
    public IDouble len2(ISwitchR r){ return pos.len2(r); }
    
    public IPoint len(IDoubleI l){ pos.len(l); return this; }
    public IPoint len(double l){ pos.len(l); return this; }
    
    public IPoint unit(){ pos.unit(); return this; }
    
    public double dist(IVecI v){ return pos.dist(v); }
    public double dist(ISwitchE e, IVecI v){ return pos.dist(e,v); }
    public IDouble dist(ISwitchR r, IVecI v){ return pos.dist(r,v); }
    
    public double dist2(IVecI v){ return pos.dist2(v); }
    public double dist2(ISwitchE e, IVecI v){ return pos.dist2(e,v); }
    public IDouble dist2(ISwitchR r, IVecI v){ return pos.dist2(r,v); }
    
    public boolean eq(IVecI v){ return pos.eq(v); }
    public boolean eq(ISwitchE e, IVecI v){ return pos.eq(e,v); }
    public IBool eq(ISwitchR r, IVecI v){ return pos.eq(r,v); }
    
    public boolean eq(IVecI v, double resolution){ return pos.eq(v,resolution); }
    public boolean eq(ISwitchE e, IVecI v, double resolution){ return pos.eq(e,v,resolution); }
    public IBool eq(ISwitchR r, IVecI v, IDoubleI resolution){ return pos.eq(r,v,resolution); }
    
    public boolean eqX(IVecI v){ return pos.eqX(v); }
    public boolean eqY(IVecI v){ return pos.eqY(v); }
    public boolean eqZ(IVecI v){ return pos.eqZ(v); }
    public boolean eqX(ISwitchE e, IVecI v){ return pos.eqX(e,v); }
    public boolean eqY(ISwitchE e, IVecI v){ return pos.eqY(e,v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return pos.eqZ(e,v); }
    public IBool eqX(ISwitchR r, IVecI v){ return pos.eqX(r,v); }
    public IBool eqY(ISwitchR r, IVecI v){ return pos.eqY(r,v); }
    public IBool eqZ(ISwitchR r, IVecI v){ return pos.eqZ(r,v); }
    
    public boolean eqX(IVecI v, double resolution){ return pos.eqX(v,resolution); }
    public boolean eqY(IVecI v, double resolution){ return pos.eqY(v,resolution); }
    public boolean eqZ(IVecI v, double resolution){ return pos.eqZ(v,resolution); }
    public boolean eqX(ISwitchE e, IVecI v, double resolution){ return pos.eqX(e,v,resolution); }
    public boolean eqY(ISwitchE e, IVecI v, double resolution){ return pos.eqY(e,v,resolution); }
    public boolean eqZ(ISwitchE e, IVecI v, double resolution){ return pos.eqZ(e,v,resolution); }
    public IBool eqX(ISwitchR r, IVecI v, IDoubleI resolution){ return pos.eqX(r,v,resolution); }
    public IBool eqY(ISwitchR r, IVecI v, IDoubleI resolution){ return pos.eqY(r,v,resolution); }
    public IBool eqZ(ISwitchR r, IVecI v, IDoubleI resolution){ return pos.eqZ(r,v,resolution); }
    
    
    public double angle(IVecI v){ return pos.angle(v); }
    public double angle(ISwitchE e, IVecI v){ return pos.angle(e,v); }
    public IDouble angle(ISwitchR r, IVecI v){ return pos.angle(r,v); }
    
    public double angle(IVecI v, IVecI axis){ return pos.angle(v,axis); }
    public double angle(ISwitchE e, IVecI v, IVecI axis){ return pos.angle(e,v,axis); }
    public IDouble angle(ISwitchR r, IVecI v, IVecI axis){ return pos.angle(r,v,axis); }
    
    public IPoint rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IPoint rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    
    public IPoint rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IPoint rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    
    
    /**
       Rotate to destination direction vector.
    */
    public IPoint rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    /**
       Rotate to destination point location.
    */
    public IPoint rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    
    /** alias of mul */
    public IPoint scale(IDoubleI f){ pos.scale(f); return this; }
    /** alias of mul */
    public IPoint scale(double f){ pos.scale(f); return this; }
    
    public IPoint scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IPoint scale(IVecI center, double f){ pos.scale(center,f); return this; }
    
    /** scale only in 1 direction */
    public IPoint scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IPoint scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IPoint scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IPoint scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IPoint ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IPoint ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IPoint mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IPoint mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    
    /** shear operation */
    public IPoint shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IPoint shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IPoint shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IPoint shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IPoint shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IPoint shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IPoint shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IPoint shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IPoint shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IPoint shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IPoint shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IPoint shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IPoint shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IPoint shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IPoint shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IPoint shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public IPoint translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IPoint translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IPoint translate(IVecI v){ pos.translate(v); return this; }
    
    
    public IPoint transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IPoint transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IPoint transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IPoint transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /** mv() is alias of add() */
    public IPoint mv(double x, double y, double z){ return add(x,y,z); }
    public IPoint mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IPoint mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IPoint cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IPoint cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IPoint cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IPoint cp(IVecI v){ return dup().add(v); }
    
    
    
    // methods creating new instance // returns IPoint?, not IVec?
    public IPoint diff(IVecI v){ return dup().sub(v); }
    public IPoint mid(IVecI v){ return dup().add(v).div(2); }
    public IPoint sum(IVecI v){ return dup().add(v); }
    public IPoint sum(IVecI... v){
	IPoint ret = this.dup();
        for(IVecI vi: v) ret.add(vi);
        return ret;
    }
    
    public IPoint bisect(IVecI v){
	return dup().unit().add(v.dup().unit());
    }
    
    
    
    /**
       weighted sum
    */
    public IPoint sum(IVecI v2, double w1, double w2){
	return dup().mul(w1).add(v2,w2);
    }
    public IPoint sum(IVecI v2, double w2){
	return dup().mul(1.0-w2).add(v2,w2);
    }
    
    public IPoint sum(IVecI v2, IDoubleI w1, IDoubleI w2){
	return dup().mul(w1).add(v2,w2);
    }
    public IPoint sum(IVecI v2, IDoubleI w2){
	return dup().mul(new IDouble(1.0).sub(w2)).add(v2,w2);
    }
    
    /** alias of cross. (not unitized ... ?) */
    public IPoint nml(IVecI v){ return cross(v); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IPoint nml(IVecI pt1, IVecI pt2){
	return this.diff(pt1).cross(this.diff(pt2)).unit();
    }
    

    public String toString(){ return pos.toString(); }
    
    
    /**
       set size of dot in graphic 
    */
    public IPoint setSize(double sz){ return size(sz); }
    public IPoint size(double sz){
	for(int i=0; graphics!=null && i<graphics.size(); i++)
	    if(graphics.get(i) instanceof IPointGraphic)
		((IPointGraphic)graphics.get(i)).size(sz);
	return this;
    }
    
    public double getSize(){ return size(); }
    public double size(){
	if(graphics==null){
	    IOut.err("no graphics is set"); //
	    return -1;
	}
	for(int i=0; graphics!=null && i<graphics.size(); i++)
	    if(graphics.get(i) instanceof IPointGraphic)
		return ((IPointGraphic)graphics.get(i)).size();
	return -1;
    }
    
    public IPoint name(String nm){ super.name(nm); return this; }
    public IPoint layer(ILayer l){ super.layer(l); return this; }
    
    public IPoint hide(){ super.hide(); return this; }
    public IPoint show(){ super.show(); return this; }
    
    public IPoint clr(Color c){ super.clr(c); return this; }
    public IPoint clr(int gray){ super.clr(gray); return this; }
    public IPoint clr(float fgray){ super.clr(fgray); return this; }
    public IPoint clr(double dgray){ super.clr(dgray); return this; }
    public IPoint clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IPoint clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IPoint clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IPoint clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IPoint clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IPoint clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IPoint clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IPoint clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IPoint clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IPoint hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IPoint hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IPoint hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IPoint hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IPoint setColor(Color c){ super.setColor(c); return this; }
    public IPoint setColor(int gray){ super.setColor(gray); return this; }
    public IPoint setColor(float fgray){ super.setColor(fgray); return this; }
    public IPoint setColor(double dgray){ super.setColor(dgray); return this; }
    public IPoint setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IPoint setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IPoint setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IPoint setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IPoint setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IPoint setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IPoint setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IPoint setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IPoint setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IPoint setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IPoint setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IPoint setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IPoint setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
}
