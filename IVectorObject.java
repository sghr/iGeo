/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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
   Class only to visualize IVecI.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IVectorObject extends IObject implements IVecI{
    public IVecI vec;
    public IVecI root;
    
    public IVectorObject(IServerI s, IVecI vec){
	super(s);
	this.vec = vec;
	this.root = new IVec(0,0,0);
	initVector(s);
    }
    public IVectorObject(IVecI vec){ this((IServerI)null,vec); }
    
    public IVectorObject(IServerI s, IVecI vec, IVecI root){
	super(s);
	this.vec = vec;
	this.root = root;
	initVector(s);
    }
    public IVectorObject(IVecI vec, IVecI root){ this(null,vec,root); }

    public IVectorObject(IServerI s, IVectorObject vobj){
	super(s,vobj);
	vec = vobj.vec;
	root = vobj.root;
	initVector(s);
	setColor(vobj.getColor());
    }
    
    public IVectorObject(IVectorObject vobj){
	super(vobj);
	vec = vobj.vec;
	root = vobj.root;
	initVector(vobj.server);
	setColor(vobj.getColor());
    }
    
    public IVecI vector(){ return vec; }
    public IVectorObject vector(IVecI v){ vec=v; return this; }
    public IVecI root(){ return root; }
    public IVectorObject root(IVecI v){ root=v; return this; }
    
    
    protected void initVector(IServerI s){
	// costly to use instanceof?
	if(vec instanceof IVec) parameter = (IVec)vec;
	else if(vec instanceof IVecR) parameter = (IVecR)vec;
	else if(vec instanceof IVec4) parameter = (IVec4)vec;
	else if(vec instanceof IVec4R) parameter = (IVec4R)vec;
	
	//addGraphic(new IPointGraphic(this));
	if(graphics==null) initGraphic(s); // not init when using copy constructor
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
	return new IVectorGraphic(this);
    }
    
    public double x(){ return vec.x(); }
    public double y(){ return vec.y(); }
    public double z(){ return vec.z(); }
    public IVec get(){ return vec.get(); }
    
    public IVectorObject dup(){ return new IVectorObject(this); }
    
    public IVec2I to2d(){ return vec.to2d(); }
    public IVec4I to4d(){ return vec.to4d(); }
    public IVec4I to4d(double w){ return vec.to4d(w); }
    public IVec4I to4d(IDoubleI w){ return vec.to4d(w); }
    
    public IDoubleI getX(){ return vec.getX(); }
    public IDoubleI getY(){ return vec.getY(); }
    public IDoubleI getZ(){ return vec.getZ(); }
    
    public IVectorObject set(IVecI v){ vec.set(v); return this; }
    public IVectorObject set(double x, double y, double z){ vec.set(x,y,z); return this;}
    public IVectorObject set(IDoubleI x, IDoubleI y, IDoubleI z){ vec.set(x,y,z); return this; }
    
    public IVectorObject add(double x, double y, double z){ vec.add(x,y,z); return this; }
    public IVectorObject add(IDoubleI x, IDoubleI y, IDoubleI z){ vec.add(x,y,z); return this; }    
    public IVectorObject add(IVecI v){ vec.add(v); return this; }
    public IVectorObject sub(double x, double y, double z){ vec.sub(x,y,z); return this; }
    public IVectorObject sub(IDoubleI x, IDoubleI y, IDoubleI z){ vec.sub(x,y,z); return this; }
    public IVectorObject sub(IVecI v){ vec.sub(v); return this; }
    public IVectorObject mul(IDoubleI v){ vec.mul(v); return this; }
    public IVectorObject mul(double v){ vec.mul(v); return this; }
    public IVectorObject div(IDoubleI v){ vec.div(v); return this; }
    public IVectorObject div(double v){ vec.div(v); return this; }
    public IVectorObject neg(){ vec.neg(); return this; }
    public IVectorObject rev(){ return neg(); }
    public IVectorObject flip(){ return neg(); }
    
    public IVectorObject zero(){ vec.zero(); return this; }
    
    public IVectorObject add(IVecI v, double f){ vec.add(v,f); return this; }
    public IVectorObject add(IVecI v, IDoubleI f){ vec.add(v,f); return this; }
    
    public IVectorObject add(double f, IVecI v){ return add(v,f); }
    public IVectorObject add(IDoubleI f, IVecI v){ return add(v,f); }
    
    
    public double dot(IVecI v){ return vec.dot(v); }
    public double dot(ISwitchE e, IVecI v){ return vec.dot(e,v); }
    public IDoubleI dot(ISwitchR r, IVecI v){ return vec.dot(r,v); }
    
    // returns IVecI, not IVectorObject
    public IVecI cross(IVecI v){ return vec.cross(v); }
    //public IVectorObject cross(IVecI v){ return dup().set(vec.cross(v)); }
    
    public double len(){ return vec.len(); }
    public double len(ISwitchE e){ return vec.len(e); }
    public IDoubleI len(ISwitchR r){ return vec.len(r); }
    
    public double len2(){ return vec.len2(); }
    public double len2(ISwitchE e){ return vec.len2(e); }
    public IDoubleI len2(ISwitchR r){ return vec.len2(r); }
    
    public IVectorObject len(IDoubleI l){ vec.len(l); return this; }
    public IVectorObject len(double l){ vec.len(l); return this; }
    
    public IVectorObject unit(){ vec.unit(); return this; }
    
    public double dist(IVecI v){ return vec.dist(v); }
    public double dist(ISwitchE e, IVecI v){ return vec.dist(e,v); }
    public IDoubleI dist(ISwitchR r, IVecI v){ return vec.dist(r,v); }
    
    public double dist2(IVecI v){ return vec.dist2(v); }
    public double dist2(ISwitchE e, IVecI v){ return vec.dist2(e,v); }
    public IDoubleI dist2(ISwitchR r, IVecI v){ return vec.dist2(r,v); }
    
    public boolean eq(IVecI v){ return vec.eq(v); }
    public boolean eq(ISwitchE e, IVecI v){ return vec.eq(e,v); }
    public IBoolI eq(ISwitchR r, IVecI v){ return vec.eq(r,v); }
    
    public boolean eq(IVecI v, double resolution){ return vec.eq(v,resolution); }
    public boolean eq(ISwitchE e, IVecI v, double resolution){ return vec.eq(e,v,resolution); }
    public IBoolI eq(ISwitchR r, IVecI v, IDoubleI resolution){ return vec.eq(r,v,resolution); }
    
    public boolean eqX(IVecI v){ return vec.eqX(v); }
    public boolean eqY(IVecI v){ return vec.eqY(v); }
    public boolean eqZ(IVecI v){ return vec.eqZ(v); }
    public boolean eqX(ISwitchE e, IVecI v){ return vec.eqX(e,v); }
    public boolean eqY(ISwitchE e, IVecI v){ return vec.eqY(e,v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return vec.eqZ(e,v); }
    public IBoolI eqX(ISwitchR r, IVecI v){ return vec.eqX(r,v); }
    public IBoolI eqY(ISwitchR r, IVecI v){ return vec.eqY(r,v); }
    public IBoolI eqZ(ISwitchR r, IVecI v){ return vec.eqZ(r,v); }
    
    public boolean eqX(IVecI v, double resolution){ return vec.eqX(v,resolution); }
    public boolean eqY(IVecI v, double resolution){ return vec.eqY(v,resolution); }
    public boolean eqZ(IVecI v, double resolution){ return vec.eqZ(v,resolution); }
    public boolean eqX(ISwitchE e, IVecI v, double resolution){ return vec.eqX(e,v,resolution); }
    public boolean eqY(ISwitchE e, IVecI v, double resolution){ return vec.eqY(e,v,resolution); }
    public boolean eqZ(ISwitchE e, IVecI v, double resolution){ return vec.eqZ(e,v,resolution); }
    public IBoolI eqX(ISwitchR r, IVecI v, IDoubleI resolution){ return vec.eqX(r,v,resolution); }
    public IBoolI eqY(ISwitchR r, IVecI v, IDoubleI resolution){ return vec.eqY(r,v,resolution); }
    public IBoolI eqZ(ISwitchR r, IVecI v, IDoubleI resolution){ return vec.eqZ(r,v,resolution); }
    
    
    public double angle(IVecI v){ return vec.angle(v); }
    public double angle(ISwitchE e, IVecI v){ return vec.angle(e,v); }
    public IDoubleI angle(ISwitchR r, IVecI v){ return vec.angle(r,v); }
    
    public double angle(IVecI v, IVecI axis){ return vec.angle(v,axis); }
    public double angle(ISwitchE e, IVecI v, IVecI axis){ return vec.angle(e,v,axis); }
    public IDoubleI angle(ISwitchR r, IVecI v, IVecI axis){ return vec.angle(r,v,axis); }
    
    public IVectorObject rot(IDoubleI angle){ vec.rot(angle); return this; }
    public IVectorObject rot(double angle){ vec.rot(angle); return this; }
    
    public IVectorObject rot(IVecI axis, IDoubleI angle){ vec.rot(axis,angle); return this; }
    public IVectorObject rot(IVecI axis, double angle){ vec.rot(axis,angle); return this; }
    
    public IVectorObject rot(IVecI center, IVecI axis, double angle){
	vec.rot(center, axis,angle); return this;
    }
    public IVectorObject rot(IVecI center, IVecI axis, IDoubleI angle){
	vec.rot(center, axis,angle); return this;
    }
    
    /** rotate to destination direction vector */
    public IVectorObject rot(IVecI axis, IVecI destDir){ vec.rot(axis,destDir); return this; }
    /** rotate to destination point location */
    public IVectorObject rot(IVecI center, IVecI axis, IVecI destPt){
	vec.rot(center,axis,destPt); return this;
    }
    
    public IVectorObject rot2(IDoubleI angle){ return rot(angle); }
    public IVectorObject rot2(double angle){ return rot(angle); }
    public IVectorObject rot2(IVecI center, double angle){ vec.rot2(center,angle); return this; }
    public IVectorObject rot2(IVecI center, IDoubleI angle){ vec.rot2(center,angle); return this; }
    /** rotate to destination direction vector */
    public IVectorObject rot2(IVecI destDir){ vec.rot2(destDir); return this; }
    /** rotate to destination point location */
    public IVectorObject rot2(IVecI center, IVecI destPt){ vec.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    public IVectorObject scale(IDoubleI f){ vec.scale(f); return this; }
    public IVectorObject scale(double f){ vec.scale(f); return this; }
    public IVectorObject scale(IVecI center, IDoubleI f){ vec.scale(center,f); return this; }
    public IVectorObject scale(IVecI center, double f){ vec.scale(center,f); return this; }
    
    /** scale only in 1 direction */
    public IVectorObject scale1d(IVecI axis, double f){ vec.scale1d(axis,f); return this; }
    public IVectorObject scale1d(IVecI axis, IDoubleI f){ vec.scale1d(axis,f); return this; }
    public IVectorObject scale1d(IVecI center, IVecI axis, double f){
	vec.scale1d(center,axis,f); return this;
    }
    public IVectorObject scale1d(IVecI center, IVecI axis, IDoubleI f){
	vec.scale1d(center,axis,f); return this;
    }
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IVectorObject ref(IVecI planeDir){ vec.ref(planeDir); return this; }
    public IVectorObject ref(IVecI center, IVecI planeDir){
	vec.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IVectorObject mirror(IVecI planeDir){ vec.ref(planeDir); return this; }
    public IVectorObject mirror(IVecI center, IVecI planeDir){
	vec.ref(center,planeDir); return this;
    }
    
    
    /** shear operation */
    public IVectorObject shear(double sxy, double syx, double syz,
			       double szy, double szx, double sxz){
	vec.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVectorObject shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	vec.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVectorObject shear(IVecI center, double sxy, double syx, double syz,
			       double szy, double szx, double sxz){
	vec.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVectorObject shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	vec.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IVectorObject shearXY(double sxy, double syx){ vec.shearXY(sxy,syx); return this; }
    public IVectorObject shearXY(IDoubleI sxy, IDoubleI syx){ vec.shearXY(sxy,syx); return this; }
    public IVectorObject shearXY(IVecI center, double sxy, double syx){
	vec.shearXY(center,sxy,syx); return this;
    }
    public IVectorObject shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	vec.shearXY(center,sxy,syx); return this;
    }
    
    public IVectorObject shearYZ(double syz, double szy){ vec.shearYZ(syz,szy); return this; }
    public IVectorObject shearYZ(IDoubleI syz, IDoubleI szy){ vec.shearYZ(syz,szy); return this; }
    public IVectorObject shearYZ(IVecI center, double syz, double szy){
	vec.shearYZ(center,syz,szy); return this;
    }
    public IVectorObject shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	vec.shearYZ(center,syz,szy); return this;
    }
    
    public IVectorObject shearZX(double szx, double sxz){ vec.shearZX(szx,sxz); return this; }
    public IVectorObject shearZX(IDoubleI szx, IDoubleI sxz){ vec.shearZX(szx,sxz); return this; }
    public IVectorObject shearZX(IVecI center, double szx, double sxz){
	vec.shearZX(center,szx,sxz); return this;
    }
    public IVectorObject shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	vec.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public IVectorObject translate(double x, double y, double z){ vec.translate(x,y,z); return this; }
    public IVectorObject translate(IDoubleI x, IDoubleI y, IDoubleI z){ vec.translate(x,y,z); return this; }
    public IVectorObject translate(IVecI v){ vec.translate(v); return this; }
    
    
    /** mv() is alias of add() */
    public IVectorObject mv(double x, double y, double z){ return add(x,y,z); }
    public IVectorObject mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IVectorObject mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IVectorObject cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IVectorObject cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IVectorObject cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IVectorObject cp(IVecI v){ return dup().add(v); }
    
    
    public IVectorObject transform(IMatrix3I mat){ vec.transform(mat); return this; }
    public IVectorObject transform(IMatrix4I mat){ vec.transform(mat); return this; }
    public IVectorObject transform(IVecI xvec, IVecI yvec, IVecI zvec){
	vec.transform(xvec,yvec,zvec); return this;
    }
    public IVectorObject transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	vec.transform(xvec,yvec,zvec,translate); return this;
    }
    
    // methods creating new instance // returns IVectorObject?, not IVec?
    // returns IVec.
    //public IVectorObject diff(IVecI v){ return dup().sub(v); }
    public IVecI dif(IVecI v){ return vec.dif(v); }
    public IVecI diff(IVecI v){ return dif(v); }
    //public IVectorObject mid(IVecI v){ return dup().add(v).div(2); }
    public IVecI mid(IVecI v){ return vec.mid(v); }
    //public IVectorObject sum(IVecI v){ return dup().add(v); }
    public IVecI sum(IVecI v){ return vec.sum(v); }
    //public IVectorObject sum(IVecI... v){IVectorObject ret=this.dup();for(IVecI vi:v)ret.add(vi);return ret;}
    public IVecI sum(IVecI... v){ return vec.sum(v); }
    
    //public IVectorObject bisect(IVecI v){ return dup().unit().add(v.dup().unit()); }
    public IVecI bisect(IVecI v){ return vec.bisect(v); }
    
    
    /**
       weighted sum
    */
    //public IVectorObject sum(IVecI v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    public IVecI sum(IVecI v2, double w1, double w2){ return vec.sum(v2,w1,w2); }
    
    //public IVectorObject sum(IVecI v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    public IVecI sum(IVecI v2, double w2){ return vec.sum(v2,w2); }
    
    
    //public IVectorObject sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return dup().mul(w1).add(v2,w2); }
    public IVecI sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return vec.sum(v2,w1,w2); }
    
    //public IVectorObject sum(IVecI v2, IDoubleI w2){ return dup().mul(new IDouble(1.0).sub(w2)).add(v2,w2); }
    public IVecI sum(IVecI v2, IDoubleI w2){ return vec.sum(v2,w2); }
    
    
    /** alias of cross. (not unitized ... ?) return IVecI. */
    //public IVectorObject nml(IVecI v){ return cross(v); }
    public IVecI nml(IVecI v){ return vec.nml(v); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    //public IVectorObject nml(IVecI pt1, IVecI pt2){ return this.diff(pt1).cross(this.diff(pt2)).unit(); }
    public IVecI nml(IVecI pt1, IVecI pt2){ return vec.nml(pt1,pt2); }
    
    
    public boolean isValid(){ return vec.isValid(); }
    
    
    /**
       set size of dot in graphic 
    */
    public IVectorObject setSize(double sz){ return size(sz); }
    public IVectorObject size(double sz){
	for(int i=0; graphics!=null && i<graphics.size(); i++)
	    if(graphics.get(i) instanceof IVectorGraphic)
		((IVectorGraphic)graphics.get(i)).size(sz);
	return this;
    }
    
    public double getSize(){ return size(); }
    public double size(){
	if(graphics==null){
	    IOut.err("no graphics is set"); //
	    return -1;
	}
	for(int i=0; graphics!=null && i<graphics.size(); i++)
	    if(graphics.get(i) instanceof IVectorGraphic)
		return ((IVectorGraphic)graphics.get(i)).size();
	return -1;
    }
    
    public IVectorObject name(String nm){ super.name(nm); return this; }
    public IVectorObject layer(ILayer l){ super.layer(l); return this; }
    
    public IVectorObject hide(){ super.hide(); return this; }
    public IVectorObject show(){ super.show(); return this; }
    
    public IVectorObject clr(Color c){ super.clr(c); return this; }
    public IVectorObject clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IVectorObject clr(int gray){ super.clr(gray); return this; }
    public IVectorObject clr(float fgray){ super.clr(fgray); return this; }
    public IVectorObject clr(double dgray){ super.clr(dgray); return this; }
    public IVectorObject clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IVectorObject clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IVectorObject clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IVectorObject clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IVectorObject clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IVectorObject clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IVectorObject clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IVectorObject clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IVectorObject clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IVectorObject hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IVectorObject hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IVectorObject hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IVectorObject hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IVectorObject setColor(Color c){ super.setColor(c); return this; }
    public IVectorObject setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IVectorObject setColor(int gray){ super.setColor(gray); return this; }
    public IVectorObject setColor(float fgray){ super.setColor(fgray); return this; }
    public IVectorObject setColor(double dgray){ super.setColor(dgray); return this; }
    public IVectorObject setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IVectorObject setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IVectorObject setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IVectorObject setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IVectorObject setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IVectorObject setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IVectorObject setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IVectorObject setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IVectorObject setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IVectorObject setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IVectorObject setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IVectorObject setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IVectorObject setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
}
