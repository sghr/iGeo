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
   Class of an implementation of IDynamics to have physical attributes of point on a curve.
   
   @author Satoru Sugihara
*/
public class IParticleOnCurveGeo extends IParticleGeo implements IParticleOnCurveI{
    
    public ICurveI curve;
    public double upos;
    public double uvel;
    //public double uacc;
    public double ufrc;
    
    public IVec utan;
    
    public IParticleOnCurveGeo(ICurveI curve){ this(curve,0); }
    public IParticleOnCurveGeo(ICurveI curve, double u){
	super(curve.pt(u).get()); this.curve=curve; upos=u; 
    }
    public IParticleOnCurveGeo(ICurveI curve, double u, double uvl){
	this(curve,u); uvel=uvl;
    }
    public IParticleOnCurveGeo(ICurveI curve, IVec pos){ this(curve,0,pos); }
    public IParticleOnCurveGeo(ICurveI curve, double u, IVec pos){
	super(pos);
	pos.set(curve.pt(u).get());
	this.curve=curve;
	upos=u;
    }
    public IParticleOnCurveGeo(ICurveI curve, double u, double uvl, IVec pos){
	this(curve,u,pos); uvel=uvl;
    }
    
    public IParticleOnCurveGeo(ICurveI curve, IObject parent){
	this(curve,0,parent);
    }
    public IParticleOnCurveGeo(ICurveI curve, double u, IObject parent){
	super(curve.pt(u).get(),parent);
	this.curve=curve;
	upos=u;
    }
    public IParticleOnCurveGeo(ICurveI curve, double u, double uvl, IObject parent){
	this(curve,u,parent); uvel=uvl;
    }
    
    public IParticleOnCurveGeo(ICurveI curve, IVec pos, IObject parent){ this(curve,0,pos,parent); }
    
    public IParticleOnCurveGeo(ICurveI curve, double u, IVec pos, IObject parent){
	super(pos,parent);
	pos.set(curve.pt(u).get());
	this.curve=curve;
	upos=u;
    }
    public IParticleOnCurveGeo(ICurveI curve, double u, double uvl, IVec pos, IObject parent){
	this(curve,u,pos,parent); uvel=uvl;
    }
    
    
    public IParticleOnCurveGeo(ICurveI curve, double u, IPoint pt){ this(curve,u,pt.pos,pt); }
    public IParticleOnCurveGeo(ICurveI curve, double u, double uvl, IPoint pt){ this(curve,u,uvl,pt.pos,pt); }
    public IParticleOnCurveGeo(ICurveI curve, IPoint pt){ this(curve,0,pt.pos,pt); }
    public IParticleOnCurveGeo(ICurveI curve, IPointR pt){ this(curve,0,pt.pos.get(),pt); }
    public IParticleOnCurveGeo(ICurveI curve, double u, IPointR pt){ this(curve,u,pt.pos.get(),pt); }
    public IParticleOnCurveGeo(ICurveI curve, double u, double uvl, IPointR pt){ this(curve,u,uvl,pt.pos.get(),pt); }
    
    
    public IParticleOnCurveGeo(ICurve curve){ this(curve,0,curve); }
    public IParticleOnCurveGeo(ICurve curve, double u){ this(curve,u,curve); }
    public IParticleOnCurveGeo(ICurve curve, double u, double uvl){ this(curve,u,uvl,curve); }
    public IParticleOnCurveGeo(ICurve curve, IVec pos){ this(curve,0,pos,curve); }
    public IParticleOnCurveGeo(ICurve curve, double u, IVec pos){ this(curve,u,pos,curve); }
    public IParticleOnCurveGeo(ICurve curve, double u, double uvl, IVec pos){ this(curve,u,uvl,pos,curve); }
    
    public IParticleOnCurveGeo(IParticleOnCurveGeo p){
	this(p.curve, p.upos, p.pos.dup(), p.parent());
    }
    public IParticleOnCurveGeo(IParticleOnCurveGeo p, IVec pos){
	this(p.curve, p.upos, pos, p.parent());
    }
    public IParticleOnCurveGeo(IParticleOnCurveGeo p, IObject parent){
	this(p.curve, p.upos, p.pos.dup(), parent);
    }
    public IParticleOnCurveGeo(IParticleOnCurveGeo p, IVec pos, IObject parent){
	this(p.curve, p.upos, pos, parent);
    }
    
    public IParticleOnCurveGeo dup(){ return new IParticleOnCurveGeo(this); }
    
    public ICurveI curve(){ return curve; }
    
    
    
    synchronized public IParticleOnCurveGeo fix(){ super.fix(); return this; }
    synchronized public IParticleOnCurveGeo unfix(){ super.unfix(); return this; }
    
    
    synchronized public IParticleOnCurveGeo skipUpdateOnce(boolean f){ super.skipUpdateOnce(f); return this; }
    
    
    synchronized public IParticleOnCurveGeo mass(double mass){ super.mass(mass); return this; }
    synchronized public IParticleOnCurveGeo position(IVecI v){ super.pos(v); return this; }
    synchronized public IParticleOnCurveGeo pos(IVecI v){ super.pos(v); return this; }
    synchronized public IParticleOnCurveGeo velocity(IVecI v){ super.vel(v); return this; }
    synchronized public IParticleOnCurveGeo vel(IVecI v){ super.vel(v); return this; }
    synchronized public IParticleOnCurveGeo force(IVecI v){ super.frc(v); return this; }
    synchronized public IParticleOnCurveGeo frc(IVecI v){ super.frc(v); return this; }
    synchronized public IParticleOnCurveGeo friction(double friction){ super.fric(friction); return this; }
    synchronized public IParticleOnCurveGeo fric(double friction){ super.fric(friction); return this; }
    /* alias of friction */
    synchronized public IParticleOnCurveGeo decay(double d){ return fric(d); }
    
    synchronized public IParticleOnCurveGeo push(IVecI f){ super.push(f); return this; }
    synchronized public IParticleOnCurveGeo pull(IVecI f){ super.pull(f); return this; }
    synchronized public IParticleOnCurveGeo addForce(IVecI f){ super.addForce(f); return this; }
    synchronized public IParticleOnCurveGeo reset(){ super.reset(); return this; }
    synchronized public IParticleOnCurveGeo resetForce(){ super.resetForce(); return this; }
    
    synchronized public IParticleOnCurveGeo uposition(double u){ return upos(u); }
    synchronized public IParticleOnCurveGeo upos(double u){ upos=u; return this; }
    synchronized public double uposition(){ return upos(); }
    synchronized public double upos(){ return upos; }
    synchronized public IParticleOnCurveGeo uvelocity(double uv){ return uvel(uv); }
    synchronized public IParticleOnCurveGeo uvel(double uv){ uvel=uv; return this; }
    synchronized public double uvelocity(){ return uvel(); }
    synchronized public double uvel(){ return uvel; }
    synchronized public IParticleOnCurveGeo uforce(double uf){ return ufrc(uf); }
    synchronized public IParticleOnCurveGeo ufrc(double uf){ ufrc=uf; return this; }
    synchronized public double uforce(){ return ufrc(); }
    synchronized public double ufrc(){ return ufrc; }
    
    synchronized public IParticleOnCurveGeo addUForce(double uforce){ return upush(uforce); }
    synchronized public IParticleOnCurveGeo resetUForce(){ return ureset(); }
    
    synchronized public IParticleOnCurveGeo upush(double uforce){ ufrc+=uforce; return this; }
    synchronized public IParticleOnCurveGeo upull(double uforce){ ufrc-=uforce; return this; }
    synchronized public IParticleOnCurveGeo ureset(){ ufrc=0; return this; }
    
    
    
    synchronized public void interact(ArrayList<IDynamics> dynamics){}
    
    
    /** update of velocity is done in preupdate (updated 2012/08/26) */
    synchronized public void preupdate(){
	if(fixed || curve==null) return;
	
	utan = curve.tan(upos).get();
	
	if(frc.projectToVec(utan) > 0) ufrc += frc.len()/utan.len();
	else ufrc += -frc.len()/utan.len();
	
	uvel += ufrc/mass*IConfig.updateRate;
	uvel *= 1.0-friction;
	
	// reset
        if( !(IConfig.enablePostupdate && IConfig.clearParticleForceInPostupdate)){
            // this is done here again because force in update cannot be reflected (updated 2014/03/10)
	    frc.zero();
	    ufrc=0; // also reset ufrc
        }
    }
    
    /** update of velocity is done in preupdate and update of position is done in update() (updated 2012/08/26) */
    synchronized public void update(){
	if(skipUpdateOnce){ skipUpdateOnce=false; return; } // added 20120827
	if(fixed || curve==null) return;
	
	// out of range of u 0.0-1.0
	if( (upos + uvel*IConfig.updateRate) < 0.0 ){
	    if( curve.isClosed() ){ // cyclic
		upos += uvel*IConfig.updateRate;
		upos -= Math.floor(upos); // fit within 0.0 - 1.0.
	    }
	    else{ upos=0.0; uvel=0.0; }
	}
	else if( (upos + uvel*IConfig.updateRate) > 1.0 ){
	    if( curve.isClosed() ){ // cyclic
		upos += uvel*IConfig.updateRate;
		upos -= Math.floor(upos); // fit within 0.0 - 1.0.
	    }
	    else{ upos=1.0; uvel=0.0; }
	}
	else{ upos += uvel*IConfig.updateRate; }
	pos.set(curve.pt(upos));
	
	
	/*
	utan = curve.tan(upos).get();
	if(frc.projectToVec(utan) > 0) ufrc += frc.len()/utan.len();
	else ufrc += -frc.len()/utan.len();
	uvel += ufrc/mass*IConfig.updateRate;
	uvel *= 1.0-friction;
	// out of range of u 0.0-1.0
	if( (upos + uvel*IConfig.updateRate) < 0.0 ){
	    if( curve.isClosed() ){ // cyclic
		upos += uvel*IConfig.updateRate;
		upos -= Math.floor(upos); // fit within 0.0 - 1.0.
	    }
	    else{ upos=0.0; uvel=0.0; }
	}
	else if( (upos + uvel*IConfig.updateRate) > 1.0 ){
	    if( curve.isClosed() ){ // cyclic
		upos += uvel*IConfig.updateRate;
		upos -= Math.floor(upos); // fit within 0.0 - 1.0.
	    }
	    else{ upos=1.0; uvel=0.0; }
	}
	else{ upos += uvel*IConfig.updateRate; }
	
	pos.set(curve.pt(upos));
	
	// reset
	frc.zero();
	ufrc=0; // also reset ufrc
	*/
	
	//if(parent!=null) parent.updateGraphic();
	//updateTarget(); //moved to IDynamics.postupdate()
    }
    
    synchronized public void postupdate(){
        if(IConfig.enablePostupdate && IConfig.clearParticleForceInPostupdate){
            frc.zero();
	    ufrc=0; // also reset ufrc
        }
    }
    

    
    
    /****************************************************************************
     * implementation of IVecI
     ***************************************************************************/

    public IParticleOnCurveGeo x(double vx){ pos.x(vx); return this; }
    public IParticleOnCurveGeo y(double vy){ pos.y(vy); return this; }
    public IParticleOnCurveGeo z(double vz){ pos.z(vz); return this; }
    
    public IParticleOnCurveGeo x(IDoubleI vx){ pos.x(vx); return this; }
    public IParticleOnCurveGeo y(IDoubleI vy){ pos.y(vy); return this; }
    public IParticleOnCurveGeo z(IDoubleI vz){ pos.z(vz); return this; }
    
    public IParticleOnCurveGeo x(IVecI vx){ pos.x(vx); return this; }
    public IParticleOnCurveGeo y(IVecI vy){ pos.y(vy); return this; }
    public IParticleOnCurveGeo z(IVecI vz){ pos.z(vz); return this; }
    
    public IParticleOnCurveGeo x(IVec2I vx){ pos.x(vx); return this; }
    public IParticleOnCurveGeo y(IVec2I vy){ pos.y(vy); return this; }
    
    public IParticleOnCurveGeo set(IVecI v){ pos.set(v); return this; }
    public IParticleOnCurveGeo set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticleOnCurveGeo set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }

    public IParticleOnCurveGeo add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticleOnCurveGeo add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticleOnCurveGeo add(IVecI v){ pos.add(v); return this; }
    
    public IParticleOnCurveGeo sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticleOnCurveGeo sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticleOnCurveGeo sub(IVecI v){ pos.sub(v); return this; }
    public IParticleOnCurveGeo mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticleOnCurveGeo mul(double v){ pos.mul(v); return this; }
    public IParticleOnCurveGeo div(IDoubleI v){ pos.div(v); return this; }
    public IParticleOnCurveGeo div(double v){ pos.div(v); return this; }
    public IParticleOnCurveGeo neg(){ pos.neg(); return this; }
    public IParticleOnCurveGeo rev(){ return neg(); }
    public IParticleOnCurveGeo flip(){ return neg(); }
    public IParticleOnCurveGeo zero(){ pos.zero(); return this; }
    
    
    public IParticleOnCurveGeo add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticleOnCurveGeo add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    public IParticleOnCurveGeo add(double f, IVecI v){ pos.add(f,v); return this; }
    public IParticleOnCurveGeo add(IDoubleI f, IVecI v){ pos.add(f,v); return this; }
    
    
    public IParticleOnCurveGeo unit(){ pos.unit(); return this; }
    
    public IParticleOnCurveGeo rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticleOnCurveGeo rot(double angle){ pos.rot(angle); return this; }
    public IParticleOnCurveGeo rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticleOnCurveGeo rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticleOnCurveGeo rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnCurveGeo rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnCurveGeo rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnCurveGeo rot(double centerX, double centerY, double centerZ,
				double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX, centerY, centerZ, axisX, axisY, axisZ, angle); return this;
    }
    
    /** Rotate to destination direction vector. */
    public IParticleOnCurveGeo rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    /** Rotate to destination point location. */
    public IParticleOnCurveGeo rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    /** rotation on xy-plane; alias of rot(double) */
    public IParticleOnCurveGeo rot2(double angle){ pos.rot2(angle); return this; }
    /** rotation on xy-plane; alias of rot(IDoubleI) */
    public IParticleOnCurveGeo rot2(IDoubleI angle){ pos.rot2(angle); return this; }
    
    /** rotation on xy-plane */
    public IParticleOnCurveGeo rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IParticleOnCurveGeo rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IParticleOnCurveGeo rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    /** rotation on xy-plane towards destDir */
    public IParticleOnCurveGeo rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    /** rotation on xy-plane towards destPt */
    public IParticleOnCurveGeo rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    public IParticleOnCurveGeo scale(IDoubleI f){ pos.scale(f); return this; }
    /** alias of mul */
    public IParticleOnCurveGeo scale(double f){ pos.scale(f); return this; }
    
    public IParticleOnCurveGeo scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticleOnCurveGeo scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticleOnCurveGeo scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleOnCurveGeo scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticleOnCurveGeo scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticleOnCurveGeo scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleOnCurveGeo scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnCurveGeo scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnCurveGeo scale1d(double centerX, double centerY, double centerZ,
				    double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurveGeo ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurveGeo ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurveGeo ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurveGeo ref(double centerX, double centerY, double centerZ,
				double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurveGeo mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurveGeo mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurveGeo mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnCurveGeo mirror(double centerX, double centerY, double centerZ,
				   double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    /** shear operation */
    public IParticleOnCurveGeo shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurveGeo shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurveGeo shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurveGeo shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleOnCurveGeo shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnCurveGeo shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnCurveGeo shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticleOnCurveGeo shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleOnCurveGeo shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnCurveGeo shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnCurveGeo shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticleOnCurveGeo shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleOnCurveGeo shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnCurveGeo shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnCurveGeo shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticleOnCurveGeo shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public IParticleOnCurveGeo translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticleOnCurveGeo translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticleOnCurveGeo translate(IVecI v){ pos.translate(v); return this; }
    
    
    public IParticleOnCurveGeo transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticleOnCurveGeo transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticleOnCurveGeo transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticleOnCurveGeo transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /** mv() is alias of add() */
    public IParticleOnCurveGeo mv(double x, double y, double z){ return add(x,y,z); }
    public IParticleOnCurveGeo mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticleOnCurveGeo mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IParticleOnCurveGeo cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IParticleOnCurveGeo cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleOnCurveGeo cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleOnCurveGeo cp(IVecI v){ return dup().add(v); }
    
        
}
