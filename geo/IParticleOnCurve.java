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
import java.util.ArrayList;

import igeo.core.*;
import igeo.gui.*;

/**
   Class of an implementation of IDynamicObject to have physical attributes of point.
   It has attributes of position, velocity, acceleration, force, and mass.
   Position is provided from outside to be linked.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IParticleOnCurve extends IParticle{
    
    public ICurveI curve;
    public double upos;
    public double uvel;
    //public double uacc;
    public double ufrc;
    
    public IVec utan;
    
    public IParticleOnCurve(ICurveI curve){ this(curve,0); }
    public IParticleOnCurve(ICurveI curve, double u){
	super(curve.pt(u).get()); this.curve=curve; upos=u; 
    }
    public IParticleOnCurve(ICurveI curve, IVec pos){ this(curve,0,pos); }
    public IParticleOnCurve(ICurveI curve, double u, IVec pos){
	super(pos);
	pos.set(curve.pt(u).get());
	this.curve=curve;
	upos=u;
    }
    
    public IParticleOnCurve(ICurveI curve, IObject parent){
	this(curve,0,parent);
    }
    public IParticleOnCurve(ICurveI curve, double u, IObject parent){
	super(curve.pt(u).get(),parent);
	this.curve=curve;
	upos=u;
    }
    
    public IParticleOnCurve(ICurveI curve, IVec pos, IObject parent){ this(curve,0,pos,parent); }

    public IParticleOnCurve(ICurveI curve, double u, IVec pos, IObject parent){
	super(pos,parent);
	pos.set(curve.pt(u).get());
	this.curve=curve;
	upos=u;
    }
    
    
    public IParticleOnCurve(ICurveI curve, double u, IPoint pt){ this(curve,u,pt.pos,pt); }
    public IParticleOnCurve(ICurveI curve, IPoint pt){ this(curve,0,pt.pos,pt); }
    public IParticleOnCurve(ICurveI curve, IPointR pt){ this(curve,0,pt.pos.get(),pt); }
    public IParticleOnCurve(ICurveI curve, double u, IPointR pt){ this(curve,u,pt.pos.get(),pt); }
    
    
    public IParticleOnCurve(ICurve curve){ this(curve,0,curve); }
    public IParticleOnCurve(ICurve curve, double u){ this(curve,u,curve); }
    public IParticleOnCurve(ICurve curve, IVec pos){ this(curve,0,pos,curve); }
    public IParticleOnCurve(ICurve curve, double u, IVec pos){ this(curve,u,pos,curve); }
    
    public IParticleOnCurve(IParticleOnCurve p){
	this(p.curve, p.upos, p.pos.dup(), p.parent());
    }
    public IParticleOnCurve(IParticleOnCurve p, IVec pos){
	this(p.curve, p.upos, pos, p.parent());
    }
    public IParticleOnCurve(IParticleOnCurve p, IObject parent){
	this(p.curve, p.upos, p.pos.dup(), parent);
    }
    public IParticleOnCurve(IParticleOnCurve p, IVec pos, IObject parent){
	this(p.curve, p.upos, pos, parent);
    }
    
    public IParticleOnCurve dup(){ return new IParticleOnCurve(this); }

    public ICurveI curve(){ return curve; }
    
    
    
    synchronized public IParticleOnCurve fix(){ super.fix(); return this; }
    synchronized public IParticleOnCurve unfix(){ super.unfix(); return this; }
    
    synchronized public IParticleOnCurve mass(double mass){ super.mass(mass); return this; }
    synchronized public IParticleOnCurve position(IVec v){ super.pos(v); return this; }
    synchronized public IParticleOnCurve pos(IVec v){ super.pos(v); return this; }
    synchronized public IParticleOnCurve velocity(IVec v){ super.vel(v); return this; }
    synchronized public IParticleOnCurve vel(IVec v){ super.vel(v); return this; }
    synchronized public IParticleOnCurve force(IVec v){ super.frc(v); return this; }
    synchronized public IParticleOnCurve frc(IVec v){ super.frc(v); return this; }
    synchronized public IParticleOnCurve friction(double friction){ super.fric(friction); return this; }
    synchronized public IParticleOnCurve fric(double friction){ super.fric(friction); return this; }
    synchronized public IParticleOnCurve addForce(IVec f){ super.addForce(f); return this; }
    synchronized public IParticleOnCurve resetForce(){ super.resetForce(); return this; }
    
    synchronized public IParticleOnCurve uposition(double u){ return upos(u); }
    synchronized public IParticleOnCurve upos(double u){ upos=u; return this; }
    synchronized public double uposition(){ return upos(); }
    synchronized public double upos(){ return upos; }
    synchronized public IParticleOnCurve uvelocity(double uv){ return uvel(uv); }
    synchronized public IParticleOnCurve uvel(double uv){ uvel=uv; return this; }
    synchronized public double uvelocity(){ return uvel(); }
    synchronized public double uvel(){ return uvel; }
    synchronized public IParticleOnCurve uforce(double uf){ return ufrc(uf); }
    synchronized public IParticleOnCurve ufrc(double uf){ ufrc=uf; return this; }
    synchronized public double uforce(){ return ufrc(); }
    synchronized public double ufrc(){ return ufrc; }
    
    synchronized public IParticleOnCurve addUForce(double uforce){ ufrc+=uforce; return this; }
    synchronized public IParticleOnCurve resetUForce(){ ufrc=0; return this; }
    
    
    
    synchronized public void interact(ArrayList<IDynamicObject> dynamics){
	
    }
    
    synchronized public void update(){
	//IOut.err();//
	//super.update();
	
	if(fixed || curve==null) return;
	
	utan = curve.tan(upos).get();
	
	if(frc.projectToVec(utan) > 0) ufrc += frc.len()/utan.len();
	else ufrc += -frc.len()/utan.len();
	
	uvel += ufrc/mass*IConfig.dynamicsSpeed;
	uvel *= 1.0-friction;
	
	// out of range of u 0.0-1.0
	if( (upos + uvel*IConfig.dynamicsSpeed) < 0.0 ){
	    if( curve.isClosed() ){ // cyclic
		upos += uvel*IConfig.dynamicsSpeed;
		upos -= Math.floor(upos); // fit within 0.0 - 1.0.
	    }
	    else{ upos=0.0; uvel=0.0; }
	}
	else if( (upos + uvel*IConfig.dynamicsSpeed) > 1.0 ){
	    if( curve.isClosed() ){ // cyclic
		upos += uvel*IConfig.dynamicsSpeed;
		upos -= Math.floor(upos); // fit within 0.0 - 1.0.
	    }
	    else{ upos=1.0; uvel=0.0; }
	}
	else{ upos += uvel*IConfig.dynamicsSpeed; }
	
	pos.set(curve.pt(upos));

	// reset
	frc.set(0,0,0);
	ufrc=0; // also reset ufrc
	
	//if(parent!=null) parent.updateGraphic();
	updateTarget();
    }
    
    
    /****************************************************************************
     * implementation of IVecI
     ***************************************************************************/
    
    public IParticleOnCurve set(IVecI v){ pos.set(v); return this; }
    public IParticleOnCurve set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticleOnCurve set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }

    public IParticleOnCurve add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticleOnCurve add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticleOnCurve add(IVecI v){ pos.add(v); return this; }
    
    public IParticleOnCurve sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticleOnCurve sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticleOnCurve sub(IVecI v){ pos.sub(v); return this; }
    public IParticleOnCurve mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticleOnCurve mul(double v){ pos.mul(v); return this; }
    public IParticleOnCurve div(IDoubleI v){ pos.div(v); return this; }
    public IParticleOnCurve div(double v){ pos.div(v); return this; }
    public IParticleOnCurve neg(){ pos.neg(); return this; }
    public IParticleOnCurve rev(){ return neg(); }
    public IParticleOnCurve flip(){ return neg(); }
    
    public IParticleOnCurve add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticleOnCurve add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    
    public IParticleOnCurve unit(){ pos.unit(); return this; }
    
    public IParticleOnCurve rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticleOnCurve rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    
    public IParticleOnCurve rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnCurve rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    
    
    /** Rotate to destination direction vector. */
    public IParticleOnCurve rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    /** Rotate to destination point location. */
    public IParticleOnCurve rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    /** alias of mul */
    public IParticleOnCurve scale(IDoubleI f){ pos.scale(f); return this; }
    /** alias of mul */
    public IParticleOnCurve scale(double f){ pos.scale(f); return this; }
    
    public IParticleOnCurve scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticleOnCurve scale(IVecI center, double f){ pos.scale(center,f); return this; }
    
    /** scale only in 1 direction */
    public IParticleOnCurve scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticleOnCurve scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticleOnCurve scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnCurve scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurve ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurve ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurve mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurve mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    
    /** shear operation */
    public IParticleOnCurve shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurve shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurve shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurve shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleOnCurve shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnCurve shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnCurve shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticleOnCurve shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleOnCurve shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnCurve shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnCurve shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticleOnCurve shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleOnCurve shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnCurve shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnCurve shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticleOnCurve shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public IParticleOnCurve translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticleOnCurve translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticleOnCurve translate(IVecI v){ pos.translate(v); return this; }
    
    
    public IParticleOnCurve transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticleOnCurve transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticleOnCurve transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticleOnCurve transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /** mv() is alias of add() */
    public IParticleOnCurve mv(double x, double y, double z){ return add(x,y,z); }
    public IParticleOnCurve mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticleOnCurve mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IParticleOnCurve cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IParticleOnCurve cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleOnCurve cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleOnCurve cp(IVecI v){ return dup().add(v); }
    
        
}
