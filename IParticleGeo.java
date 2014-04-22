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
   Class of an implementation of IDynamics to have physical attributes of point.
   It has attributes of position, velocity, acceleration, force, and mass.
   Position is provided from outside to be linked.
   
   @author Satoru Sugihara
*/
public class IParticleGeo extends IDynamicsBase implements IParticleI, IVecI{
    
    static double defaultFriction = 0.0;
    
    //public IObject parent=null;
    
    public double mass=1.0;
    public IVec pos;
    public IVec vel;
    //public IVec acc;
    public IVec frc;
    public boolean fixed=false;
    public double friction = defaultFriction;
    
    /** when other agent set locatin, this skips to update the position by the velocity */
    public boolean skipUpdateOnce;
    
    
    
    public IParticleGeo(){ pos = new IVec(); initParticle(); }
    public IParticleGeo(IVec pos){ this.pos = pos; initParticle(); }
    public IParticleGeo(IVec pos, IObject parent){ super(parent); this.pos = pos; initParticle(); }
    
    public IParticleGeo(IVecI p){ pos = p.get(); initParticle(); }
    public IParticleGeo(IVecI p, IObject parent){ super(parent); pos = p.get(); initParticle(); }
    
    public IParticleGeo(double x, double y, double z){ pos = new IVec(x,y,z); initParticle(); }
    public IParticleGeo(double x, double y, double z, IObject parent){ super(parent); pos = new IVec(x,y,z); initParticle(); }
    
    public IParticleGeo(IPoint pt){ super(pt); pos = pt.pos; initParticle(); }
    public IParticleGeo(IPointR pt){ super(pt); pos = pt.pos.get(); initParticle(); }
    
    public IParticleGeo(IParticleGeo ptcl){
	super(ptcl.parent);
	pos = ptcl.pos.dup();
	initParticle();
    }
    
    public IParticleGeo(IParticleGeo ptcl, IObject parent){
	super(parent);
	pos = ptcl.pos.dup();
	initParticle();
    }
    
    
    
    public IParticleGeo(IVec pos, IVec vel){ this.pos = pos; initParticle(vel); }
    public IParticleGeo(IVec pos, IVec vel, IObject parent){ super(parent); this.pos = pos; initParticle(vel); }
    
    public IParticleGeo(IVecI p, IVecI v){ pos = p.get(); initParticle(v); }
    public IParticleGeo(IVecI p, IVecI v, IObject parent){ super(parent); pos = p.get(); initParticle(v); }
    
    public IParticleGeo(double x, double y, double z, double vx, double vy, double vz){
	pos = new IVec(x,y,z); initParticle(new IVec(vx,vy,vz));
    }
    public IParticleGeo(double x, double y, double z, double vx, double vy, double vz,
		     IObject parent){
	super(parent); pos = new IVec(x,y,z); initParticle(new IVec(vx,vy,vz));
    }
    
    public IParticleGeo(IPoint pt, IVecI v){ super(pt); pos = pt.pos; initParticle(v); }
    public IParticleGeo(IPointR pt, IVecI v){ super(pt); pos = pt.pos.get(); initParticle(v); }
    
    public IParticleGeo(IParticleGeo ptcl, IVecI v){
	super(ptcl.parent);
	pos = ptcl.pos.dup();
	initParticle(v);
    }
    
    public IParticleGeo(IParticleGeo ptcl, IVecI v, IObject parent){
	super(parent);
	pos = ptcl.pos.dup();
	initParticle(v);
    }
    
    synchronized public void initParticle(){
	vel = new IVec();
	//acc = new IVec();
	frc = new IVec();
    }
    synchronized public void initParticle(IVec v){
	vel = v;
	frc = new IVec();
    }
    synchronized public void initParticle(IVecI v){
	vel = v.get();
	frc = new IVec();
    }
    
    public IParticleGeo dup(){ return new IParticleGeo(this); }
    // temporary
    //public IParticleGeo dup(){ return this; }
    
    
    // implementation of IDynamics
    //public IObject parent(){ return parent; }
    //public ISubobject parent(IObject parent){ this.parent=parent; return this; }
    
    synchronized public IParticleGeo fix(){
	fixed=true;
	frc.zero(); // added 20120119
	vel.zero(); // added 20120119
	return this;
    }
    synchronized public IParticleGeo unfix(){
	frc.zero(); // added 20120119 in case frc is added up during it's been fixed.
	vel.zero(); // added 20120119
	fixed=false;
	return this;
    }
    
    
    /** check if it's fixed */
    public boolean fixed(){ return fixed; }
    
    
    /** for other agent to control particle */
    public IParticleGeo skipUpdateOnce(boolean f){ skipUpdateOnce=f; return this; }
    /** for other agent to control particle */
    public boolean skipUpdateOnce(){ return skipUpdateOnce; }
    
    
    
    synchronized public double mass(){ return mass; }
    synchronized public IParticleGeo mass(double mass){ this.mass=mass; return this; }
    
    synchronized public IVec position(){ return pos(); }
    synchronized public IParticleGeo position(IVecI v){ return pos(v); }
    
    synchronized public IVec pos(){ return pos; }
    synchronized public IParticleGeo pos(IVecI v){ pos.set(v); return this; }
    
    synchronized public IVec velocity(){ return vel(); }
    synchronized public IParticleGeo velocity(IVecI v){ return vel(v); }
    
    synchronized public IVec vel(){ return vel; }
    synchronized public IParticleGeo vel(IVecI v){ vel.set(v); return this; }
    
    /** get acceleration; acceleration is calculated from frc and mass */
    synchronized public IVec acceleration(){ return acc(); }
    /** get acceleration; acceleration is calculated from frc and mass */
    synchronized public IVec acc(){ return frc.dup().div(mass); } 
    
    //synchronized public IVec acceleration(){ return acc(); }
    //synchronized public IParticleGeo acceleration(IVec v){ acc(v); return this; }
    //synchronized public IVec acc(){ return acc; }
    //synchronized public IParticleGeo acc(IVec v){ acc.set(v); return this; }
    
    synchronized public IVec force(){ return frc(); }
    synchronized public IParticleGeo force(IVecI v){ return frc(v); }
    
    synchronized public IVec frc(){ return frc; }
    synchronized public IParticleGeo frc(IVecI v){ frc.set(v); return this; }
    
    synchronized public double friction(){ return fric(); }
    synchronized public IParticleGeo friction(double friction){ return fric(friction); }
    
    synchronized public double fric(){ return friction; }
    synchronized public IParticleGeo fric(double friction){ this.friction=friction; return this; }
    /* alias of friction */
    public double decay(){ return fric(); }
    /* alias of friction */
    public IParticleGeo decay(double d){ return fric(d); }
    
    /** adding force */
    synchronized public IParticleGeo push(IVecI f){
	//IG.p(IOut.currentStack(5)); //
	//IG.p(parent.name()+": f = "+f.z()); //
	if(!fixed){ frc.add(f); }
	//IG.p(parent.name()+": frc = "+frc.z); //
	return this;
    }
    /** adding force */
    synchronized public IParticleGeo push(double fx, double fy, double fz){
	if(!fixed){ frc.add(fx,fy,fz); } return this;
    }
    /** adding negative force */
    synchronized public IParticleGeo pull(IVecI f){
	//IG.p(IOut.currentStack(5)); //
	//IG.p(parent.name()+": f = "+f.z()); //
	if(!fixed){ frc.sub(f); }
	//IG.p(parent.name()+": frc = "+frc.z); //
	return this;
	
    }
    /** adding negative force */
    synchronized public IParticleGeo pull(double fx, double fy, double fz){
	if(!fixed){ frc.sub(fx,fy,fz); } return this;
    }
    /** adding force (alias of push) */
    synchronized public IParticleGeo addForce(IVecI f){ return push(f); }
    /** adding force (alias of push) */
    synchronized public IParticleGeo addForce(double fx, double fy, double fz){ return push(fx,fy,fz); }
    
    /** setting force zero */
    synchronized public IParticleGeo reset(){ frc.zero(); return this; }
    /** setting force zero (alias of reset()) */
    synchronized public IParticleGeo resetForce(){ return reset(); }
    
    //synchronized public void interact(ArrayList<IDynamics> dynamics){}

    /** update of velocity is done in preupdate (updated 2012/08/26) */
    synchronized public void preupdate(){
	if(fixed) return;
	//vel.add(frc.mul(IConfig.updateRate/mass)).mul(1.0-friction);
	vel.add(frc, IConfig.updateRate/mass).mul(1.0-friction);
	//frc.zero(); // now this is done in postupdate
	if( !(IConfig.enablePostupdate && IConfig.clearParticleForceInPostupdate)){
	    frc.zero(); // this is done here again because force in update cannot be reflected (updated 2014/03/10)
	}
    }
    
    /** update of velocity is done in preupdate and update of position is done in update() (updated 2012/08/26) */
    synchronized public void update(){
	if(skipUpdateOnce){ skipUpdateOnce=false; return; } // added 20120827
	
	if(fixed) return;
	//vel.add(frc.mul(IConfig.updateRate/mass)).mul(1.0-friction);
	//pos.add(vel.dup().mul(IConfig.updateRate));
	
	pos.add(vel, IConfig.updateRate);
	//frc.zero();
	
	//if(parent!=null) parent.updateGraphic(); // graphic update
	//super.update();
	//updateTarget(); //moved to IDynamics.postUpdate()
    }
    
    /** clearing frc is done in postupdate (updated 2013/12/08) */
    /** this is changed again because force in update cannot be reflected (updated 2014/03/10) */
    
    synchronized public void postupdate(){
	if(IConfig.enablePostupdate && IConfig.clearParticleForceInPostupdate){
	    frc.zero();
	}
    }
    
    
    /************************************************************************
     * implements of IVecI
     ***********************************************************************/
    
    public double x(){ return pos.x(); }
    public double y(){ return pos.y(); }
    public double z(){ return pos.z(); }

    public IParticleGeo x(double vx){ pos.x(vx); return this; }
    public IParticleGeo y(double vy){ pos.y(vy); return this; }
    public IParticleGeo z(double vz){ pos.z(vz); return this; }
    
    public IParticleGeo x(IDoubleI vx){ pos.x(vx); return this; }
    public IParticleGeo y(IDoubleI vy){ pos.y(vy); return this; }
    public IParticleGeo z(IDoubleI vz){ pos.z(vz); return this; }
    
    /** setting x component by x component of input vector*/
    public IParticleGeo x(IVecI v){ pos.x(v); return this; }
    /** setting y component by y component of input vector*/
    public IParticleGeo y(IVecI v){ pos.y(v); return this; }
    /** setting z component by z component of input vector*/
    public IParticleGeo z(IVecI v){ pos.z(v); return this; }
    
    /** setting x component by x component of input vector*/
    public IParticleGeo x(IVec2I v){ pos.x(v); return this; }
    /** setting y component by y component of input vector*/
    public IParticleGeo y(IVec2I v){ pos.y(v); return this; }


    
    public double x(ISwitchE e){ return pos.x(e); }
    public double y(ISwitchE e){ return pos.y(e); }
    public double z(ISwitchE e){ return pos.z(e); }
    
    public IDoubleI x(ISwitchR r){ return pos.x(r); }
    public IDoubleI y(ISwitchR r){ return pos.y(r); }
    public IDoubleI z(ISwitchR r){ return pos.z(r); }
    
    
    public IVec get(){ return pos.get(); }
    
    public IVec2 to2d(){ return pos.to2d(); }
    public IVec2 to2d(IVecI projectionDir){ return pos.to2d(projectionDir); }
    public IVec2 to2d(IVecI xaxis, IVecI yaxis){ return pos.to2d(xaxis,yaxis); }
    public IVec2 to2d(IVecI xaxis, IVecI yaxis, IVecI origin){ return pos.to2d(xaxis,yaxis,origin); }
    
    public IVec4 to4d(){ return pos.to4d(); }
    public IVec4 to4d(double w){ return pos.to4d(w); }
    public IVec4 to4d(IDoubleI w){ return pos.to4d(w); }
    
    public IDouble getX(){ return pos.getX(); }
    public IDouble getY(){ return pos.getY(); }
    public IDouble getZ(){ return pos.getZ(); }
    
    public IParticleGeo set(IVecI v){ pos.set(v); return this; }
    public IParticleGeo set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticleGeo set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IParticleGeo add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticleGeo add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticleGeo add(IVecI v){ pos.add(v); return this; }
    
    public IParticleGeo sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticleGeo sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticleGeo sub(IVecI v){ pos.sub(v); return this; }
    public IParticleGeo mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticleGeo mul(double v){ pos.mul(v); return this; }
    public IParticleGeo div(IDoubleI v){ pos.div(v); return this; }
    public IParticleGeo div(double v){ pos.div(v); return this; }
    public IParticleGeo neg(){ pos.neg(); return this; }
    public IParticleGeo rev(){ return neg(); }
    public IParticleGeo flip(){ return neg(); }

    public IParticleGeo zero(){ pos.zero(); return this; }
    
    public IParticleGeo add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticleGeo add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IParticleGeo add(double f, IVecI v){ return add(v,f); }
    public IParticleGeo add(IDoubleI f, IVecI v){ return add(v,f); }
    
    
    public double dot(IVecI v){ return pos.dot(v); }
    public double dot(double vx, double vy, double vz){ return pos.dot(vx,vy,vz); }
    public double dot(ISwitchE e, IVecI v){ return pos.dot(e,v); }
    public IDouble dot(ISwitchR r, IVecI v){ return pos.dot(r,v); }
    
    // creating IParticleGeo is too much (in terms of memory occupancy)
    public IVec cross(IVecI v){ return pos.cross(v); }
    public IVec cross(double vx, double vy, double vz){ return pos.cross(vx,vy,vz); }
    //public IParticleGeo cross(IVecI v){ return dup().set(pos.cross(v)); }
    
    public double len(){ return pos.len(); }
    public double len(ISwitchE e){ return pos.len(e); }
    public IDouble len(ISwitchR r){ return pos.len(r); }
    
    public double len2(){ return pos.len2(); }
    public double len2(ISwitchE e){ return pos.len2(e); }
    public IDouble len2(ISwitchR r){ return pos.len2(r); }
    
    public IParticleGeo len(IDoubleI l){ pos.len(l); return this; }
    public IParticleGeo len(double l){ pos.len(l); return this; }
    
    public IParticleGeo unit(){ pos.unit(); return this; }
    
    public double dist(IVecI v){ return pos.dist(v); }
    public double dist(double vx, double vy, double vz){ return pos.dist(vx,vy,vz); }
    public double dist(ISwitchE e, IVecI v){ return pos.dist(e,v); }
    public IDouble dist(ISwitchR r, IVecI v){ return pos.dist(r,v); }
    
    public double dist2(IVecI v){ return pos.dist2(v); }
    public double dist2(double vx, double vy, double vz){ return pos.dist2(vx,vy,vz); }
    public double dist2(ISwitchE e, IVecI v){ return pos.dist2(e,v); }
    public IDouble dist2(ISwitchR r, IVecI v){ return pos.dist2(r,v); }
    
    public boolean eq(IVecI v){ return pos.eq(v); }
    public boolean eq(double vx, double vy, double vz){ return pos.eq(vx,vy,vz); }
    public boolean eq(ISwitchE e, IVecI v){ return pos.eq(e,v); }
    public IBool eq(ISwitchR r, IVecI v){ return pos.eq(r,v); }
    
    public boolean eq(IVecI v, double tolerance){ return pos.eq(v,tolerance); }
    public boolean eq(double vx, double vy, double vz, double tolerance){ return pos.eq(vx,vy,vz,tolerance); }
    public boolean eq(ISwitchE e, IVecI v, double tolerance){ return pos.eq(e,v,tolerance); }
    public IBool eq(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eq(r,v,tolerance); }
    
    public boolean eqX(IVecI v){ return pos.eqX(v); }
    public boolean eqY(IVecI v){ return pos.eqY(v); }
    public boolean eqZ(IVecI v){ return pos.eqZ(v); }
    public boolean eqX(double vx){ return pos.eqX(vx); }
    public boolean eqY(double vy){ return pos.eqY(vy); }
    public boolean eqZ(double vz){ return pos.eqZ(vz); }
    public boolean eqX(ISwitchE e, IVecI v){ return pos.eqX(e,v); }
    public boolean eqY(ISwitchE e, IVecI v){ return pos.eqY(e,v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return pos.eqZ(e,v); }
    public IBool eqX(ISwitchR r, IVecI v){ return pos.eqX(r,v); }
    public IBool eqY(ISwitchR r, IVecI v){ return pos.eqY(r,v); }
    public IBool eqZ(ISwitchR r, IVecI v){ return pos.eqZ(r,v); }
    
    public boolean eqX(IVecI v, double tolerance){ return pos.eqX(v,tolerance); }
    public boolean eqY(IVecI v, double tolerance){ return pos.eqY(v,tolerance); }
    public boolean eqZ(IVecI v, double tolerance){ return pos.eqZ(v,tolerance); }
    public boolean eqX(double vx, double tolerance){ return pos.eqX(vx,tolerance); }
    public boolean eqY(double vy, double tolerance){ return pos.eqY(vy,tolerance); }
    public boolean eqZ(double vz, double tolerance){ return pos.eqZ(vz,tolerance); }
    public boolean eqX(ISwitchE e, IVecI v, double tolerance){ return pos.eqX(e,v,tolerance); }
    public boolean eqY(ISwitchE e, IVecI v, double tolerance){ return pos.eqY(e,v,tolerance); }
    public boolean eqZ(ISwitchE e, IVecI v, double tolerance){ return pos.eqZ(e,v,tolerance); }
    public IBool eqX(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqX(r,v,tolerance); }
    public IBool eqY(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqY(r,v,tolerance); }
    public IBool eqZ(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqZ(r,v,tolerance); }
    
    
    public double angle(IVecI v){ return pos.angle(v); }
    public double angle(double vx, double vy, double vz){ return pos.angle(vx,vy,vz); }
    public double angle(ISwitchE e, IVecI v){ return pos.angle(e,v); }
    public IDouble angle(ISwitchR r, IVecI v){ return pos.angle(r,v); }
    
    public double angle(IVecI v, IVecI axis){ return pos.angle(v,axis); }
    public double angle(double vx, double vy, double vz, double axisX, double axisY, double axisZ){
	return pos.angle(vx,vy,vz,axisX,axisY,axisZ);
    }
    public double angle(ISwitchE e, IVecI v, IVecI axis){ return pos.angle(e,v,axis); }
    public IDouble angle(ISwitchR r, IVecI v, IVecI axis){ return pos.angle(r,v,axis); }
    
    public IParticleGeo rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticleGeo rot(double angle){ pos.rot(angle); return this; }
    
    public IParticleGeo rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticleGeo rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticleGeo rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleGeo rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleGeo rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleGeo rot(double centerX, double centerY, double centerZ,
			 double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    /** Rotate to destination direction vector. */
    public IParticleGeo rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    /** Rotate to destination point location. */
    public IParticleGeo rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IParticleGeo rot2(IDoubleI angle){ return rot(angle); }
    public IParticleGeo rot2(double angle){ return rot(angle); }
    public IParticleGeo rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IParticleGeo rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IParticleGeo rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    /** Rotation on xy-plane to destination direction vector. */
    public IParticleGeo rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    /** Rotation on xy-plane to destination point location. */
    public IParticleGeo rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    public IParticleGeo scale(IDoubleI f){ pos.scale(f); return this; }
    /** alias of mul */
    public IParticleGeo scale(double f){ pos.scale(f); return this; }
    
    public IParticleGeo scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticleGeo scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticleGeo scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleGeo scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticleGeo scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticleGeo scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleGeo scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleGeo scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleGeo scale1d(double centerX, double centerY, double centerZ,
			     double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleGeo ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleGeo ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleGeo ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleGeo ref(double centerX, double centerY, double centerZ,
			 double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerY,planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleGeo mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleGeo mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleGeo mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleGeo mirror(double centerX, double centerY, double centerZ,
			    double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    /** shear operation */
    public IParticleGeo shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleGeo shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleGeo shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleGeo shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleGeo shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleGeo shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleGeo shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticleGeo shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleGeo shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleGeo shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleGeo shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticleGeo shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleGeo shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleGeo shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleGeo shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticleGeo shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public IParticleGeo translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticleGeo translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticleGeo translate(IVecI v){ pos.translate(v); return this; }
    
    
    public IParticleGeo transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticleGeo transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticleGeo transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticleGeo transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /** mv() is alias of add() */
    public IParticleGeo mv(double x, double y, double z){ return add(x,y,z); }
    public IParticleGeo mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticleGeo mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IParticleGeo cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IParticleGeo cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleGeo cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleGeo cp(IVecI v){ return dup().add(v); }
    
    
    
    // methods creating new instance // returns IParticleGeo?, not IVec?
    // returns IVec, not IParticleGeo (2011/10/12)
    //public IParticleGeo diff(IVecI v){ return dup().sub(v); }
    public IVec dif(IVecI v){ return pos.dif(v); }
    public IVec dif(double vx, double vy, double vz){ return pos.dif(vx,vy,vz); }
    public IVec diff(IVecI v){ return dif(v); }
    public IVec diff(double vx, double vy, double vz){ return dif(vx,vy,vz); }
    //public IParticleGeo mid(IVecI v){ return dup().add(v).div(2); }
    public IVec mid(IVecI v){ return pos.mid(v); }
    public IVec mid(double vx, double vy, double vz){ return pos.mid(vx,vy,vz); }
    //public IParticleGeo sum(IVecI v){ return dup().add(v); }
    public IVec sum(IVecI v){ return pos.sum(v); }
    public IVec sum(double vx, double vy, double vz){ return pos.sum(vx,vy,vz); }
    //public IParticleGeo sum(IVecI... v){ IParticleGeo ret = this.dup(); for(IVecI vi: v) ret.add(vi); return ret; }
    public IVec sum(IVecI... v){ return pos.sum(v); }
    //public IParticleGeo bisect(IVecI v){ return dup().unit().add(v.dup().unit()); }
    public IVec bisect(IVecI v){ return pos.bisect(v); }
    public IVec bisect(double vx, double vy, double vz){ return pos.bisect(vx,vy,vz); }
    
    
    /**
       weighted sum.
       @return IVec
    */
    //public IParticleGeo sum(IVecI v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    public IVec sum(IVecI v2, double w1, double w2){ return pos.sum(v2,w1,w2); }
    //public IParticleGeo sum(IVecI v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    public IVec sum(IVecI v2, double w2){ return pos.sum(v2,w2); }
    
    //public IParticleGeo sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return dup().mul(w1).add(v2,w2); }
    public IVec sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return sum(v2,w1,w2); }
    
    //public IParticleGeo sum(IVecI v2, IDoubleI w2){ return dup().mul(new IDouble(1.0).sub(w2)).add(v2,w2); }
    public IVec sum(IVecI v2, IDoubleI w2){ return sum(v2,w2); }
    
    
    /** alias of cross. (not unitized ... ?) */
    //public IParticleGeo nml(IVecI v){ return cross(v); }
    public IVec nml(IVecI v){ return pos.nml(v); }
    public IVec nml(double vx, double vy, double vz){ return pos.nml(vx,vy,vz); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    //public IParticleGeo nml(IVecI pt1, IVecI pt2){ return this.diff(pt1).cross(this.diff(pt2)).unit(); }
    public IVec nml(IVecI pt1, IVecI pt2){ return pos.nml(pt1,pt2); }
    public IVec nml(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2){
	return pos.nml(vx1,vy1,vz1,vx2,vy2,vz2);
    }
    
    /** checking x, y, and z is valid number (not Infinite, nor NaN). */
    public boolean isValid(){ if(pos==null){ return false; } return pos.isValid(); }
    
    
    public String toString(){
	if(pos==null) return super.toString();
	return pos.toString();
    }
    
    // partial methods of IDynamics
    /** add terget object to be updated by this dynamic object. */
    public IParticleGeo target(IObject targetObj){ super.target(targetObj); return this; }
    /** remove target object. */
    public IParticleGeo  removeTarget(int i){ super.removeTarget(i); return this; }
    /** remove target object. */
    public IParticleGeo  removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
}
