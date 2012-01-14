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
import java.util.ArrayList;

/**
   Class of an agent based on one point.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IPointAgent extends IAgent implements IVecI{
    
    public IVec pos;
    public IPoint point;
    
    public IPointAgent(){ this(new IVec()); show(); }
    public IPointAgent(double x, double y, double z){ super(); pos=new IVec(x,y,z); show(); }
    public IPointAgent(IVec p){ super(); pos=p; show(); }
    public IPointAgent(IVecI p){ super(); pos=p.get(); show(); }
    public IPointAgent(IPointAgent pa){ super(); pos=pa.pos.dup(); show(); }
    
    
    public IVec position(){ return pos(); }
    public IPointAgent position(IVecI v){ return pos(v); }
    
    public IVec pos(){ return pos; }
    public IPointAgent pos(IVecI v){ pos.set(v); return this; }
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public double x(){ return pos.x(); }
    public double y(){ return pos.y(); }
    public double z(){ return pos.z(); }
    public IVec get(){ return pos.get(); }
    
    public IPointAgent dup(){ return new IPointAgent(this); }
    
    public IVec2 to2d(){ return pos.to2d(); }
    public IVec4 to4d(){ return pos.to4d(); }
    public IVec4 to4d(double w){ return pos.to4d(w); }
    public IVec4 to4d(IDoubleI w){ return pos.to4d(w); }
    
    public IDouble getX(){ return pos.getX(); }
    public IDouble getY(){ return pos.getY(); }
    public IDouble getZ(){ return pos.getZ(); }
    
    public IPointAgent set(IVecI v){ pos.set(v); return this; }
    public IPointAgent set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IPointAgent set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IPointAgent add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IPointAgent add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IPointAgent add(IVecI v){ pos.add(v); return this; }
    
    public IPointAgent sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IPointAgent sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IPointAgent sub(IVecI v){ pos.sub(v); return this; }
    public IPointAgent mul(IDoubleI v){ pos.mul(v); return this; }
    public IPointAgent mul(double v){ pos.mul(v); return this; }
    public IPointAgent div(IDoubleI v){ pos.div(v); return this; }
    public IPointAgent div(double v){ pos.div(v); return this; }
    public IPointAgent neg(){ pos.neg(); return this; }
    public IPointAgent rev(){ return neg(); }
    public IPointAgent flip(){ return neg(); }

    public IPointAgent zero(){ pos.zero(); return this; }
    
    public IPointAgent add(IVecI v, double f){ pos.add(v,f); return this; }
    public IPointAgent add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IPointAgent add(double f, IVecI v){ return add(v,f); }
    public IPointAgent add(IDoubleI f, IVecI v){ return add(v,f); }
    
    
    public double dot(IVecI v){ return pos.dot(v); }
    public double dot(ISwitchE e, IVecI v){ return pos.dot(e,v); }
    public IDouble dot(ISwitchR r, IVecI v){ return pos.dot(r,v); }
    
    // returns IVec
    public IVec cross(IVecI v){ return pos.cross(v); }
    
    public double len(){ return pos.len(); }
    public double len(ISwitchE e){ return pos.len(e); }
    public IDouble len(ISwitchR r){ return pos.len(r); }
    
    public double len2(){ return pos.len2(); }
    public double len2(ISwitchE e){ return pos.len2(e); }
    public IDouble len2(ISwitchR r){ return pos.len2(r); }
    
    public IPointAgent len(IDoubleI l){ pos.len(l); return this; }
    public IPointAgent len(double l){ pos.len(l); return this; }
    
    public IPointAgent unit(){ pos.unit(); return this; }
    
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
    
    public IPointAgent rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IPointAgent rot(double angle){ pos.rot(angle); return this; }
    
    public IPointAgent rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IPointAgent rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    
    public IPointAgent rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IPointAgent rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    
    public IPointAgent rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IPointAgent rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IPointAgent rot2(IDoubleI angle){ return rot(angle); }
    public IPointAgent rot2(double angle){ return rot(angle); }
    public IPointAgent rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IPointAgent rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IPointAgent rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    public IPointAgent rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    public IPointAgent scale(IDoubleI f){ pos.scale(f); return this; }
    public IPointAgent scale(double f){ pos.scale(f); return this; }
    
    public IPointAgent scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IPointAgent scale(IVecI center, double f){ pos.scale(center,f); return this; }
    
    /** scale only in 1 direction */
    public IPointAgent scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IPointAgent scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IPointAgent scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IPointAgent scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
        
    public IPointAgent ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IPointAgent ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IPointAgent mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IPointAgent mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    
    public IPointAgent shear(double sxy, double syx, double syz,
			     double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IPointAgent shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			     IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IPointAgent shear(IVecI center, double sxy, double syx, double syz,
			     double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IPointAgent shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			     IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IPointAgent shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IPointAgent shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IPointAgent shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IPointAgent shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IPointAgent shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IPointAgent shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IPointAgent shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IPointAgent shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IPointAgent shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IPointAgent shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IPointAgent shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IPointAgent shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    public IPointAgent translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IPointAgent translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IPointAgent translate(IVecI v){ pos.translate(v); return this; }
    
    public IPointAgent transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IPointAgent transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IPointAgent transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IPointAgent transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IPointAgent mv(double x, double y, double z){ return add(x,y,z); }
    public IPointAgent mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IPointAgent mv(IVecI v){ return add(v); }
    
    public IPointAgent cp(){ return dup(); }
    public IPointAgent cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IPointAgent cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IPointAgent cp(IVecI v){ return dup().add(v); }
    
    // returns IVec, not IPointAgent
    public IVec dif(IVecI v){ return pos.dif(v); }
    public IVec diff(IVecI v){ return dif(v); }
    public IVec mid(IVecI v){ return pos.mid(v); }
    public IVec sum(IVecI v){ return pos.sum(v); }
    public IVec sum(IVecI... v){ return pos.sum(v); }
    public IVec bisect(IVecI v){ return pos.bisect(v); }
    
    public IVec sum(IVecI v2, double w1, double w2){ return pos.sum(v2,w1,w2); }
    public IVec sum(IVecI v2, double w2){ return pos.sum(v2,w2); }
    public IVec sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return sum(v2,w1,w2); }
    public IVec sum(IVecI v2, IDoubleI w2){ return sum(v2,w2); }
    
    public IVec nml(IVecI v){ return pos.nml(v); }
    public IVec nml(IVecI pt1, IVecI pt2){ return pos.nml(pt1,pt2); }
    
    public boolean isValid(){ return pos.isValid(); }
    public String toString(){ return pos.toString(); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IPointAgent setSize(double sz){ return size(sz); }
    public IPointAgent size(double sz){ point.size(sz); return this; }
    public double getSize(){ return point.size(); }
    public double size(){ return point.size(); }
    
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IPointAgent name(String nm){ super.name(nm); point.name(nm); return this; }
    public IPointAgent layer(ILayer l){ super.layer(l); point.layer(l); return this; }
    
    
    public IPointAgent show(){
	if(point==null){ point = new IPoint(pos).clr(super.clr()); }
	else{ point.show(); }
	super.show();
	return this;
    }
    public IPointAgent hide(){ if(point!=null) point.hide(); super.hide(); return this; }
    
    
    public IPointAgent clr(Color c){ super.clr(c); point.clr(c); return this; }
    public IPointAgent clr(Color c, int alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    public IPointAgent clr(int gray){ super.clr(gray); point.clr(gray); return this; }
    public IPointAgent clr(float fgray){ super.clr(fgray); point.clr(fgray); return this; }
    public IPointAgent clr(double dgray){ super.clr(dgray); point.clr(dgray); return this; }
    public IPointAgent clr(int gray, int alpha){ super.clr(gray,alpha); point.clr(gray,alpha); return this; }
    public IPointAgent clr(float fgray, float falpha){ super.clr(fgray,falpha); point.clr(fgray,falpha); return this; }
    public IPointAgent clr(double dgray, double dalpha){ super.clr(dgray,dalpha); point.clr(dgray,dalpha); return this; }
    public IPointAgent clr(int r, int g, int b){ super.clr(r,g,b); point.clr(r,g,b); return this; }
    public IPointAgent clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); point.clr(fr,fg,fb); return this; }
    public IPointAgent clr(double dr, double dg, double db){ super.clr(dr,dg,db); point.clr(dr,dg,db); return this; }
    public IPointAgent clr(int r, int g, int b, int a){
	super.clr(r,g,b,a); point.clr(r,g,b,a); return this;
    }
    public IPointAgent clr(float fr, float fg, float fb, float fa){
	super.clr(fr,fg,fb,fa); point.clr(fr,fg,fb,fa); return this;
    }
    public IPointAgent clr(double dr, double dg, double db, double da){
	super.clr(dr,dg,db,da); point.clr(dr,dg,db,da); return this;
    }
    public IPointAgent hsb(float h, float s, float b, float a){
	super.hsb(h,s,b,a); point.hsb(h,s,b,a); return this;
    }
    public IPointAgent hsb(double h, double s, double b, double a){
	super.hsb(h,s,b,a); point.hsb(h,s,b,a); return this;
    }
    public IPointAgent hsb(float h, float s, float b){
	super.hsb(h,s,b); point.hsb(h,s,b); return this;
    }
    public IPointAgent hsb(double h, double s, double b){
	super.hsb(h,s,b); point.hsb(h,s,b); return this;
    }
    
    public IPointAgent setColor(Color c){ return clr(c); }
    public IPointAgent setColor(Color c, int alpha){ return clr(c,alpha); }
    public IPointAgent setColor(int gray){ return clr(gray); }
    public IPointAgent setColor(float fgray){ return clr(fgray); }
    public IPointAgent setColor(double dgray){ return clr(dgray); }
    public IPointAgent setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IPointAgent setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IPointAgent setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IPointAgent setColor(int r, int g, int b){ return clr(r,g,b); }
    public IPointAgent setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IPointAgent setColor(double dr, double dg, double db){ return clr(dr,dg,db); }
    public IPointAgent setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IPointAgent setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IPointAgent setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IPointAgent setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IPointAgent setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IPointAgent setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IPointAgent setHSBColor(double h, double s, double b){ return hsb(h,s,b); }
    
    
}
