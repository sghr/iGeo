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
   Class of an implementation of IDynamics to have physical attributes of point on a plane.
   
   @author Satoru Sugihara
*/
public class IParticleOnPlaneGeo extends IParticleGeo /*implements IParticleOnPlaneI*/{
    public IVecI planeDir;
    public IVecI planePt;
    
    public IParticleOnPlaneGeo(IVecI planeDir, IVecI planePt, IVecI pos, IVecI vel){
	super(pos,vel);
	this.planeDir = planeDir;
	this.planePt = planePt;
    }
    
    public IParticleOnPlaneGeo(IVecI planeDir, IVecI planePt, IVecI pos){
	super(pos);
	this.planeDir = planeDir;
	this.planePt = planePt;
    }
    
    public IParticleOnPlaneGeo(IVecI planeDir, IVecI planePt, IVecI pos, IVecI vel, IObject parent){
	super(pos,vel,parent);
	this.planeDir = planeDir;
	this.planePt = planePt;
    }
    
    public IParticleOnPlaneGeo(IVecI planeDir, IVecI planePt, IVecI pos, IObject parent){
	super(pos,parent);
	this.planeDir = planeDir;
	this.planePt = planePt;
    }
    
    public IParticleOnPlaneGeo(IParticleOnPlaneGeo p){
	this(p.planeDir, p.planePt, p.pos.dup(), p.parent());
    }
    
    public IParticleOnPlaneGeo dup(){ return new IParticleOnPlaneGeo(this); }

    
    synchronized public IParticleOnPlaneGeo fix(){ super.fix(); return this; }
    synchronized public IParticleOnPlaneGeo unfix(){ super.unfix(); return this; }
        
    synchronized public IParticleOnPlaneGeo skipUpdateOnce(boolean f){ super.skipUpdateOnce(f); return this; }
        
    synchronized public IParticleOnPlaneGeo mass(double mass){ super.mass(mass); return this; }
    synchronized public IParticleOnPlaneGeo position(IVecI v){ super.pos(v); return this; }
    synchronized public IParticleOnPlaneGeo pos(IVecI v){ super.pos(v); return this; }
    synchronized public IParticleOnPlaneGeo velocity(IVecI v){ super.vel(v); return this; }
    synchronized public IParticleOnPlaneGeo vel(IVecI v){ super.vel(v); return this; }
    synchronized public IParticleOnPlaneGeo force(IVecI v){ super.frc(v); return this; }
    synchronized public IParticleOnPlaneGeo frc(IVecI v){ super.frc(v); return this; }
    synchronized public IParticleOnPlaneGeo friction(double friction){ super.fric(friction); return this; }
    synchronized public IParticleOnPlaneGeo fric(double friction){ super.fric(friction); return this; }
    /* alias of friction */
    synchronized public IParticleOnPlaneGeo decay(double d){ return fric(d); }
    
    synchronized public IParticleOnPlaneGeo push(IVecI f){ super.push(f); return this; }
    synchronized public IParticleOnPlaneGeo pull(IVecI f){ super.pull(f); return this; }
    synchronized public IParticleOnPlaneGeo addForce(IVecI f){ super.addForce(f); return this; }
    synchronized public IParticleOnPlaneGeo reset(){ super.reset(); return this; }
    synchronized public IParticleOnPlaneGeo resetForce(){ super.resetForce(); return this; }
    

    //synchronized public void interact(ArrayList<IDynamics> dynamics){}
    
    
    /** update of velocity is done in preupdate (updated 2012/08/26) */
    synchronized public void preupdate(){
	if(fixed) return;
	frc.projectToPlane(planeDir);
        vel.add(frc, IConfig.updateRate/mass).mul(1.0-friction);
	vel.projectToPlane(planeDir);
	if( !(IConfig.enablePostupdate && IConfig.clearParticleForceInPostupdate)){
            frc.zero(); 
        }
    }
    
    /** update of velocity is done in preupdate and update of position is done in update() (updated 2012/08/26) */
    synchronized public void update(){
	if(skipUpdateOnce){ skipUpdateOnce=false; return; } // added 20120827
	if(fixed) return;
	
	pos.add(vel, IConfig.updateRate);
	pos.projectToPlane(planeDir, planeDir, planePt);
    }
    
    synchronized public void postupdate(){
        if(IConfig.enablePostupdate && IConfig.clearParticleForceInPostupdate){
            frc.zero();
        }
    }
    

    
    
    /****************************************************************************
     * implementation of IVecI
     ***************************************************************************/

    public IParticleOnPlaneGeo x(double vx){ pos.x(vx); return this; }
    public IParticleOnPlaneGeo y(double vy){ pos.y(vy); return this; }
    public IParticleOnPlaneGeo z(double vz){ pos.z(vz); return this; }
    
    public IParticleOnPlaneGeo x(IDoubleI vx){ pos.x(vx); return this; }
    public IParticleOnPlaneGeo y(IDoubleI vy){ pos.y(vy); return this; }
    public IParticleOnPlaneGeo z(IDoubleI vz){ pos.z(vz); return this; }
    
    public IParticleOnPlaneGeo x(IVecI vx){ pos.x(vx); return this; }
    public IParticleOnPlaneGeo y(IVecI vy){ pos.y(vy); return this; }
    public IParticleOnPlaneGeo z(IVecI vz){ pos.z(vz); return this; }
    
    public IParticleOnPlaneGeo x(IVec2I vx){ pos.x(vx); return this; }
    public IParticleOnPlaneGeo y(IVec2I vy){ pos.y(vy); return this; }
    
    public IParticleOnPlaneGeo set(IVecI v){ pos.set(v); return this; }
    public IParticleOnPlaneGeo set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticleOnPlaneGeo set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }

    public IParticleOnPlaneGeo add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticleOnPlaneGeo add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticleOnPlaneGeo add(IVecI v){ pos.add(v); return this; }
    
    public IParticleOnPlaneGeo sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticleOnPlaneGeo sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticleOnPlaneGeo sub(IVecI v){ pos.sub(v); return this; }
    public IParticleOnPlaneGeo mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticleOnPlaneGeo mul(double v){ pos.mul(v); return this; }
    public IParticleOnPlaneGeo div(IDoubleI v){ pos.div(v); return this; }
    public IParticleOnPlaneGeo div(double v){ pos.div(v); return this; }
    public IParticleOnPlaneGeo neg(){ pos.neg(); return this; }
    public IParticleOnPlaneGeo rev(){ return neg(); }
    public IParticleOnPlaneGeo flip(){ return neg(); }
    public IParticleOnPlaneGeo zero(){ pos.zero(); return this; }
    
    
    public IParticleOnPlaneGeo add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticleOnPlaneGeo add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    public IParticleOnPlaneGeo add(double f, IVecI v){ pos.add(f,v); return this; }
    public IParticleOnPlaneGeo add(IDoubleI f, IVecI v){ pos.add(f,v); return this; }
    
    
    public IParticleOnPlaneGeo unit(){ pos.unit(); return this; }
    
    public IParticleOnPlaneGeo rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticleOnPlaneGeo rot(double angle){ pos.rot(angle); return this; }
    public IParticleOnPlaneGeo rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticleOnPlaneGeo rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticleOnPlaneGeo rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnPlaneGeo rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnPlaneGeo rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnPlaneGeo rot(double centerX, double centerY, double centerZ,
				double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX, centerY, centerZ, axisX, axisY, axisZ, angle); return this;
    }
    
    /** Rotate to destination direction vector. */
    public IParticleOnPlaneGeo rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    /** Rotate to destination point location. */
    public IParticleOnPlaneGeo rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    /** rotation on xy-plane; alias of rot(double) */
    public IParticleOnPlaneGeo rot2(double angle){ pos.rot2(angle); return this; }
    /** rotation on xy-plane; alias of rot(IDoubleI) */
    public IParticleOnPlaneGeo rot2(IDoubleI angle){ pos.rot2(angle); return this; }
    
    /** rotation on xy-plane */
    public IParticleOnPlaneGeo rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IParticleOnPlaneGeo rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IParticleOnPlaneGeo rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    /** rotation on xy-plane towards destDir */
    public IParticleOnPlaneGeo rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    /** rotation on xy-plane towards destPt */
    public IParticleOnPlaneGeo rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    public IParticleOnPlaneGeo scale(IDoubleI f){ pos.scale(f); return this; }
    /** alias of mul */
    public IParticleOnPlaneGeo scale(double f){ pos.scale(f); return this; }
    
    public IParticleOnPlaneGeo scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticleOnPlaneGeo scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticleOnPlaneGeo scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleOnPlaneGeo scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticleOnPlaneGeo scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticleOnPlaneGeo scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleOnPlaneGeo scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnPlaneGeo scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnPlaneGeo scale1d(double centerX, double centerY, double centerZ,
				    double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnPlaneGeo ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnPlaneGeo ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnPlaneGeo ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnPlaneGeo ref(double centerX, double centerY, double centerZ,
				double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnPlaneGeo mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnPlaneGeo mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnPlaneGeo mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnPlaneGeo mirror(double centerX, double centerY, double centerZ,
				   double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    /** shear operation */
    public IParticleOnPlaneGeo shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnPlaneGeo shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnPlaneGeo shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnPlaneGeo shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleOnPlaneGeo shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnPlaneGeo shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnPlaneGeo shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticleOnPlaneGeo shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleOnPlaneGeo shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnPlaneGeo shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnPlaneGeo shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticleOnPlaneGeo shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleOnPlaneGeo shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnPlaneGeo shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnPlaneGeo shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticleOnPlaneGeo shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public IParticleOnPlaneGeo translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticleOnPlaneGeo translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticleOnPlaneGeo translate(IVecI v){ pos.translate(v); return this; }
    
    
    public IParticleOnPlaneGeo transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticleOnPlaneGeo transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticleOnPlaneGeo transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticleOnPlaneGeo transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /** mv() is alias of add() */
    public IParticleOnPlaneGeo mv(double x, double y, double z){ return add(x,y,z); }
    public IParticleOnPlaneGeo mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticleOnPlaneGeo mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IParticleOnPlaneGeo cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IParticleOnPlaneGeo cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleOnPlaneGeo cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleOnPlaneGeo cp(IVecI v){ return dup().add(v); }
    
        
}
