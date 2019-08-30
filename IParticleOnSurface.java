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

import java.awt.Color;
import java.util.ArrayList;

/**
   Class of an agent with IParticleOnSurfaceGeo.
   
   @author Satoru Sugihara
*/
public class IParticleOnSurface extends IPointAgent implements IParticleOnSurfaceI{
    
    public IParticleOnSurfaceGeo particle;
    /** only to refer to particle.vel and particle.frc */
    public IVec vel, frc; 
    
    public IParticleOnSurface(ISurfaceI srf, double upos, double vpos, double uvel, double vvel){
	super(); initParticleOnSurfaceAgent(srf,this.pos,upos,vpos,uvel,vvel);
    }
    public IParticleOnSurface(ISurfaceI srf, double upos, double vpos){
	super(); initParticleOnSurfaceAgent(srf,this.pos,upos,vpos,0,0);
    }
    public IParticleOnSurface(ISurfaceI srf){
	super(); initParticleOnSurfaceAgent(srf,this.pos,0,0,0,0);
    }
    public IParticleOnSurface(ISurfaceI srf, double upos,double vpos, double uvel,double vvel, IVec pos){
	super(pos); initParticleOnSurfaceAgent(srf,pos,upos,vpos,uvel,vvel);
    }
    public IParticleOnSurface(ISurfaceI srf, double upos, double vpos, IVec pos){
	super(pos); initParticleOnSurfaceAgent(srf,pos,upos,vpos,0,0);
    }
    public IParticleOnSurface(ISurfaceI srf, IVec pos){
	super(pos); initParticleOnSurfaceAgent(srf,pos,0,0,0,0);
    }
    
    public IParticleOnSurface(ISurface srf, double upos, double vpos, double uvel, double vvel){
	super(); initParticleOnSurfaceAgent(srf,this.pos,upos,vpos,uvel,vvel);
    }
    public IParticleOnSurface(ISurface srf, double upos, double vpos){
	super(); initParticleOnSurfaceAgent(srf,this.pos,upos,vpos,0,0);
    }
    public IParticleOnSurface(ISurface srf){
	super(); initParticleOnSurfaceAgent(srf,this.pos,0,0,0,0);
    }
    public IParticleOnSurface(ISurface srf, double upos, double vpos, double uvel, double vvel, IVec pos){
	super(pos); initParticleOnSurfaceAgent(srf,pos,upos,vpos,uvel,vvel);
    }
    public IParticleOnSurface(ISurface srf, double upos, double vpos, IVec pos){
	super(pos); initParticleOnSurfaceAgent(srf,pos,upos,vpos,0,0);
    }
    public IParticleOnSurface(ISurface srf, IVec pos){
	super(pos); initParticleOnSurfaceAgent(srf,pos,0,0,0,0);
    }
    
    public IParticleOnSurface(IParticleOnSurfaceGeo p){ super(p.pos); initParticleOnSurfaceAgent(p); }
    public IParticleOnSurface(IParticleOnSurface p){
	super(p.pos.dup()); initParticleOnSurfaceAgent(p.particle.surface,this.pos,p.particle.uvpos.x,p.particle.uvpos.y,p.particle.uvvel.x, p.particle.uvvel.y);
    }
    
    
    // methods to attach geometry? 
    
    
    public void initParticleOnSurfaceAgent(ISurfaceI srf, IVec pos, double upos, double vpos, double uvel, double vvel){
	particle = new IParticleOnSurfaceGeo(srf,upos,vpos,uvel,vvel,pos);
	this.pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    public void initParticleOnSurfaceAgent(ISurface srf, IVec pos, double upos, double vpos, double uvel, double vvel){
	particle = new IParticleOnSurfaceGeo(srf,upos,vpos,uvel,vvel,pos);
	this.pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    public void initParticleOnSurfaceAgent(IParticleOnSurfaceGeo ptcl){
	particle = ptcl;
	pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    
    /**************************************
     * IParticleI API
     **************************************/
    
    synchronized public IParticleOnSurface fix(){ particle.fix(); return this; }
    synchronized public IParticleOnSurface unfix(){ particle.unfix(); return this; }
    
    public IParticleOnSurface skipUpdateOnce(boolean f){ particle.skipUpdateOnce(f); return this; }
    public boolean skipUpdateOnce(){ return particle.skipUpdateOnce(); }
    
    synchronized public boolean fixed(){ return particle.fixed(); }
    
    synchronized public double mass(){ return particle.mass(); }
    synchronized public IParticleOnSurface mass(double mass){ particle.mass(mass); return this; }
    
    synchronized public IVec position(){ return particle.position(); }
    synchronized public IParticleOnSurface position(IVecI v){ particle.position(v); return this; }
    
    synchronized public IVec pos(){ return particle.pos(); }
    synchronized public IParticleOnSurface pos(IVecI v){ particle.pos(v); return this; }
    
    synchronized public IVec velocity(){ return particle.velocity(); }
    synchronized public IParticleOnSurface velocity(IVecI v){ particle.velocity(v); return this; }
    
    synchronized public IVec vel(){ return particle.vel(); }
    synchronized public IParticleOnSurface vel(IVecI v){ particle.vel(v); return this; }
    
    synchronized public IVec acceleration(){ return particle.acceleration(); }
    //synchronized public IParticleOnSurface acceleration(IVec v){ particle.acceleration(v); return this; }
    synchronized public IVec acc(){ return particle.acc(); }
    //synchronized public IParticleOnSurface acc(IVec v){ particle.acc(v); return this; }
    
    synchronized public IVec force(){ return particle.force(); }
    synchronized public IParticleOnSurface force(IVecI v){ particle.force(v); return this; }
    
    synchronized public IVec frc(){ return particle.frc(); }
    synchronized public IParticleOnSurface frc(IVecI v){ particle.frc(v); return this; }
    
    synchronized public double friction(){ return particle.friction(); }
    synchronized public IParticleOnSurface friction(double friction){ particle.friction(friction); return this; }
    
    synchronized public double fric(){ return particle.fric(); }
    synchronized public IParticleOnSurface fric(double friction){ particle.fric(friction); return this; }
    /* alias of friction */
    synchronized public double decay(){ return fric(); }
    /* alias of friction */
    synchronized public IParticleOnSurface decay(double d){ return fric(d); }
    
    synchronized public IParticleOnSurface push(IVecI f){ particle.push(f); return this; }
    synchronized public IParticleOnSurface push(double fx, double fy, double fz){ particle.push(fx,fy,fz); return this; }
    synchronized public IParticleOnSurface pull(IVecI f){ particle.pull(f); return this; }
    synchronized public IParticleOnSurface pull(double fx, double fy, double fz){ particle.pull(fx,fy,fz); return this; }
    synchronized public IParticleOnSurface addForce(IVecI f){ particle.addForce(f); return this; }
    synchronized public IParticleOnSurface addForce(double fx, double fy, double fz){ particle.addForce(fx,fy,fz); return this; }
    
    
    
    synchronized public IParticleOnSurface reset(){ particle.reset(); return this; }
    synchronized public IParticleOnSurface resetForce(){ particle.resetForce(); return this; }

    // surface() conflict with IPointAgent.surface
    synchronized public ISurfaceI baseSurface(){ return particle.surface(); }
    
    synchronized public IParticleOnSurface uv(double u, double v){ particle.uv(u,v); return this; }
    synchronized public IVec2 uv(){ return particle.uv(); }
    synchronized public IParticleOnSurface uvvel(double uvel, double vvel){ particle.uvvel(uvel,vvel); return this; }
    synchronized public IVec2 uvvel(){ return particle.uvvel(); }
    synchronized public IParticleOnSurface uvfrc(double ufrc,double vfrc){ particle.uvfrc(ufrc,vfrc); return this; }
    synchronized public IVec2 uvfrc(){ return particle.uvfrc(); }
    
    synchronized public IParticleOnSurface addUVForce(double ufrc,double vfrc){ particle.uvpush(ufrc,vfrc); return this; }
    synchronized public IParticleOnSurface resetUVForce(){ particle.uvreset(); return this; }
    
    synchronized public IParticleOnSurface uvpush(double ufrc, double vfrc){ particle.uvpush(ufrc,vfrc); return this; }
    synchronized public IParticleOnSurface uvpull(double ufrc,double vfrc){ particle.uvpull(ufrc,vfrc); return this; }
    synchronized public IParticleOnSurface uvreset(){ particle.uvreset(); return this; }
    
    public IParticleOnSurface fixU(){ particle.fixU(); return this; }
    public IParticleOnSurface fixV(){ particle.fixV(); return this; }
    public IParticleOnSurface unfixU(){ particle.unfixU(); return this; }
    public IParticleOnSurface unfixV(){ particle.unfixV(); return this; }
    public boolean uFixed(){ return particle.uFixed(); }
    public boolean vFixed(){ return particle.vFixed(); }
    
    // partial methods of IDynamics  
    /** add terget object to be updated by this dynamic object. */
    public IParticleOnSurface target(IObject targetObj){ super.target(targetObj); return this; }
    /** get total target number. */
    public int targetNum(){ return super.targetNum(); }
    /** get target object. */
    public IObject target(int i){ return super.target(i); }
    /** get all target objects. */
    public ArrayList<IObject> targets(){ return super.targets(); }
    public IParticleOnSurface removeTarget(int i){ super.removeTarget(i); return this; }
    /** remove target object. */
    public IParticleOnSurface removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    /** update all terget objects (should be called when the dynamic object is updated). */
    public void updateTarget(){ super.updateTarget(); }
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public IParticleOnSurface x(double vx){ pos.x(vx); return this; }
    public IParticleOnSurface y(double vy){ pos.y(vy); return this; }
    public IParticleOnSurface z(double vz){ pos.z(vz); return this; }
    
    public IParticleOnSurface x(IDoubleI vx){ pos.x(vx); return this; }
    public IParticleOnSurface y(IDoubleI vy){ pos.y(vy); return this; }
    public IParticleOnSurface z(IDoubleI vz){ pos.z(vz); return this; }
    
    public IParticleOnSurface x(IVecI vx){ pos.x(vx); return this; }
    public IParticleOnSurface y(IVecI vy){ pos.y(vy); return this; }
    public IParticleOnSurface z(IVecI vz){ pos.z(vz); return this; }

    public IParticleOnSurface x(IVec2I vx){ pos.x(vx); return this; }
    public IParticleOnSurface y(IVec2I vy){ pos.y(vy); return this; }
    

    public IParticleOnSurface dup(){ return new IParticleOnSurface(this); }
    
    public IParticleOnSurface set(IVecI v){ pos.set(v); return this; }
    public IParticleOnSurface set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticleOnSurface set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IParticleOnSurface add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticleOnSurface add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticleOnSurface add(IVecI v){ pos.add(v); return this; }
    
    public IParticleOnSurface sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticleOnSurface sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticleOnSurface sub(IVecI v){ pos.sub(v); return this; }
    public IParticleOnSurface mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticleOnSurface mul(double v){ pos.mul(v); return this; }
    public IParticleOnSurface div(IDoubleI v){ pos.div(v); return this; }
    public IParticleOnSurface div(double v){ pos.div(v); return this; }
    public IParticleOnSurface neg(){ pos.neg(); return this; }
    public IParticleOnSurface rev(){ return neg(); }
    public IParticleOnSurface flip(){ return neg(); }

    public IParticleOnSurface zero(){ pos.zero(); return this; }
    
    public IParticleOnSurface add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticleOnSurface add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IParticleOnSurface add(double f, IVecI v){ return add(v,f); }
    public IParticleOnSurface add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public IParticleOnSurface len(IDoubleI l){ pos.len(l); return this; }
    public IParticleOnSurface len(double l){ pos.len(l); return this; }
    
    public IParticleOnSurface unit(){ pos.unit(); return this; }
    
    public IParticleOnSurface rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticleOnSurface rot(double angle){ pos.rot(angle); return this; }
    
    public IParticleOnSurface rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticleOnSurface rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticleOnSurface rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnSurface rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnSurface rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnSurface rot(double centerX, double centerY, double centerZ,
			      double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnSurface rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IParticleOnSurface rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IParticleOnSurface rot2(IDoubleI angle){ return rot(angle); }
    public IParticleOnSurface rot2(double angle){ return rot(angle); }
    public IParticleOnSurface rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IParticleOnSurface rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IParticleOnSurface rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    public IParticleOnSurface rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    public IParticleOnSurface rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    public IParticleOnSurface scale(IDoubleI f){ pos.scale(f); return this; }
    public IParticleOnSurface scale(double f){ pos.scale(f); return this; }
    
    public IParticleOnSurface scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticleOnSurface scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticleOnSurface scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleOnSurface scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticleOnSurface scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticleOnSurface scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleOnSurface scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnSurface scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnSurface scale1d(double centerX, double centerY, double centerZ,
				  double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IParticleOnSurface ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticleOnSurface ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IParticleOnSurface ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticleOnSurface ref(double centerX, double centerY, double centerZ,
			      double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IParticleOnSurface mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticleOnSurface mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX, planeY, planeZ); return this;
    }
    public IParticleOnSurface mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticleOnSurface mirror(double centerX, double centerY, double centerZ,
				 double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    public IParticleOnSurface shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnSurface shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnSurface shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnSurface shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleOnSurface shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnSurface shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnSurface shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticleOnSurface shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleOnSurface shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnSurface shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnSurface shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticleOnSurface shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleOnSurface shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnSurface shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnSurface shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticleOnSurface shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    public IParticleOnSurface translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticleOnSurface translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticleOnSurface translate(IVecI v){ pos.translate(v); return this; }
    
    public IParticleOnSurface transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticleOnSurface transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticleOnSurface transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticleOnSurface transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IParticleOnSurface mv(double x, double y, double z){ return add(x,y,z); }
    public IParticleOnSurface mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticleOnSurface mv(IVecI v){ return add(v); }
    
    public IParticleOnSurface cp(){ return dup(); }
    public IParticleOnSurface cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleOnSurface cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleOnSurface cp(IVecI v){ return dup().add(v); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IParticleOnSurface setSize(double sz){ return size(sz); }
    public IParticleOnSurface size(double sz){ point.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IParticleOnSurface name(String nm){ super.name(nm); return this; }
    public IParticleOnSurface layer(ILayer l){ super.layer(l); return this; }
    
    public IParticleOnSurface show(){ super.show(); return this; }
    public IParticleOnSurface hide(){ super.hide(); return this; }
    
    
    public IParticleOnSurface clr(IColor c){ super.clr(c); return this; }
    public IParticleOnSurface clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleOnSurface clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IParticleOnSurface clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IParticleOnSurface clr(IObject o){ super.clr(o); return this; }
    public IParticleOnSurface clr(Color c){ super.clr(c); return this; }
    public IParticleOnSurface clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleOnSurface clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public IParticleOnSurface clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IParticleOnSurface clr(int gray){ super.clr(gray); return this; }
    public IParticleOnSurface clr(float fgray){ super.clr(fgray); return this; }
    public IParticleOnSurface clr(double dgray){ super.clr(dgray); return this; }
    public IParticleOnSurface clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IParticleOnSurface clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IParticleOnSurface clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IParticleOnSurface clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IParticleOnSurface clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IParticleOnSurface clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IParticleOnSurface clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IParticleOnSurface clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IParticleOnSurface clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IParticleOnSurface hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IParticleOnSurface hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IParticleOnSurface hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IParticleOnSurface hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IParticleOnSurface setColor(IColor c){ super.setColor(c); return this; }
    public IParticleOnSurface setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnSurface setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnSurface setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnSurface setColor(Color c){ super.setColor(c); return this; }
    public IParticleOnSurface setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnSurface setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnSurface setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnSurface setColor(int gray){ super.setColor(gray); return this; }
    public IParticleOnSurface setColor(float fgray){ super.setColor(fgray); return this; }
    public IParticleOnSurface setColor(double dgray){ super.setColor(dgray); return this; }
    public IParticleOnSurface setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IParticleOnSurface setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IParticleOnSurface setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IParticleOnSurface setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IParticleOnSurface setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IParticleOnSurface setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IParticleOnSurface setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IParticleOnSurface setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IParticleOnSurface setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IParticleOnSurface setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticleOnSurface setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticleOnSurface setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IParticleOnSurface setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public IParticleOnSurface weight(double w){ super.weight(w); return this; }
    public IParticleOnSurface weight(float w){ super.weight(w); return this; }
    
}
