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
   Class of an agent with IParticleOnCurveGeo.
   
   @author Satoru Sugihara
*/
public class IParticleOnCurve extends IPointAgent implements IParticleOnCurveI{
    
    public IParticleOnCurveGeo particle;
    /** only to refer to particle.vel and particle.frc */
    public IVec vel, frc; 
    
    public IParticleOnCurve(ICurveI curve, double upos, double uvel){
	super(); initParticleOnCurveAgent(curve,this.pos,upos,uvel);
    }
    public IParticleOnCurve(ICurveI curve, double upos){
	super(); initParticleOnCurveAgent(curve,this.pos,upos,0);
    }
    public IParticleOnCurve(ICurveI curve){
	super(); initParticleOnCurveAgent(curve,this.pos,0,0);
    }
    public IParticleOnCurve(ICurveI curve, double upos, double uvel, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,upos,uvel);
    }
    public IParticleOnCurve(ICurveI curve, double upos, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,upos,0);
    }
    public IParticleOnCurve(ICurveI curve, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,0,0);
    }
    
    public IParticleOnCurve(ICurve curve, double upos, double uvel){
	super(); initParticleOnCurveAgent(curve,this.pos,upos,uvel);
    }
    public IParticleOnCurve(ICurve curve, double upos){
	super(); initParticleOnCurveAgent(curve,this.pos,upos,0);
    }
    public IParticleOnCurve(ICurve curve){
	super(); initParticleOnCurveAgent(curve,this.pos,0,0);
    }
    public IParticleOnCurve(ICurve curve, double upos, double uvel, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,upos,uvel);
    }
    public IParticleOnCurve(ICurve curve, double upos, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,upos,0);
    }
    public IParticleOnCurve(ICurve curve, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,0,0);
    }
    
    public IParticleOnCurve(IParticleOnCurveGeo p){ super(p.pos); initParticleOnCurveAgent(p); }
    public IParticleOnCurve(IParticleOnCurve p){
	super(p.pos.dup()); initParticleOnCurveAgent(p.particle.curve,this.pos,p.particle.upos,p.particle.uvel);
    }
    
    
    // will put methods to attach geometry? 
    
    
    public void initParticleOnCurveAgent(ICurveI curve, IVec pos, double upos, double uvel){
	particle = new IParticleOnCurveGeo(curve,upos,uvel,pos);
	this.pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    public void initParticleOnCurveAgent(ICurve curve, IVec pos, double upos, double uvel){
	particle = new IParticleOnCurveGeo(curve,upos,uvel,pos);
	this.pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    public void initParticleOnCurveAgent(IParticleOnCurveGeo ptcl){
	particle = ptcl;
	pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    
    /**************************************
     * IParticleI API
     **************************************/
    
    synchronized public IParticleOnCurve fix(){ particle.fix(); return this; }
    synchronized public IParticleOnCurve unfix(){ particle.unfix(); return this; }
    
    public IParticleOnCurve skipUpdateOnce(boolean f){ particle.skipUpdateOnce(f); return this; }
    public boolean skipUpdateOnce(){ return particle.skipUpdateOnce(); }
    
    synchronized public boolean fixed(){ return particle.fixed(); }
    
    synchronized public double mass(){ return particle.mass(); }
    synchronized public IParticleOnCurve mass(double mass){ particle.mass(mass); return this; }
    
    synchronized public IVec position(){ return particle.position(); }
    synchronized public IParticleOnCurve position(IVecI v){ particle.position(v); return this; }
    
    synchronized public IVec pos(){ return particle.pos(); }
    synchronized public IParticleOnCurve pos(IVecI v){ particle.pos(v); return this; }
    
    synchronized public IVec velocity(){ return particle.velocity(); }
    synchronized public IParticleOnCurve velocity(IVecI v){ particle.velocity(v); return this; }
    
    synchronized public IVec vel(){ return particle.vel(); }
    synchronized public IParticleOnCurve vel(IVecI v){ particle.vel(v); return this; }
    
    synchronized public IVec acceleration(){ return particle.acceleration(); }
    //synchronized public IParticleOnCurve acceleration(IVec v){ particle.acceleration(v); return this; }
    synchronized public IVec acc(){ return particle.acc(); }
    //synchronized public IParticleOnCurve acc(IVec v){ particle.acc(v); return this; }
    
    synchronized public IVec force(){ return particle.force(); }
    synchronized public IParticleOnCurve force(IVecI v){ particle.force(v); return this; }
    
    synchronized public IVec frc(){ return particle.frc(); }
    synchronized public IParticleOnCurve frc(IVecI v){ particle.frc(v); return this; }
    
    synchronized public double friction(){ return particle.friction(); }
    synchronized public IParticleOnCurve friction(double friction){ particle.friction(friction); return this; }
    
    synchronized public double fric(){ return particle.fric(); }
    synchronized public IParticleOnCurve fric(double friction){ particle.fric(friction); return this; }
    /* alias of friction */
    synchronized public double decay(){ return fric(); }
    /* alias of friction */
    synchronized public IParticleOnCurve decay(double d){ return fric(d); }
    
    synchronized public IParticleOnCurve push(IVecI f){ particle.push(f); return this; }
    synchronized public IParticleOnCurve push(double fx, double fy, double fz){ particle.push(fx,fy,fz); return this; }
    synchronized public IParticleOnCurve pull(IVecI f){ particle.pull(f); return this; }
    synchronized public IParticleOnCurve pull(double fx, double fy, double fz){ particle.pull(fx,fy,fz); return this; }
    synchronized public IParticleOnCurve addForce(IVecI f){ particle.addForce(f); return this; }
    synchronized public IParticleOnCurve addForce(double fx, double fy, double fz){ particle.addForce(fx,fy,fz); return this; }
    
    
    
    synchronized public IParticleOnCurve reset(){ particle.reset(); return this; }
    synchronized public IParticleOnCurve resetForce(){ particle.resetForce(); return this; }
    
    /** IParticleOnCurve methods; temporarily renamed */
    //synchronized public ICurveI curve(){ return particle.curve(); }
    synchronized public ICurveI railCurve(){ return particle.curve(); }
    
    synchronized public IParticleOnCurve uposition(double u){ particle.upos(u); return this; }
    synchronized public IParticleOnCurve upos(double u){ particle.upos(u); return this; }
    synchronized public double uposition(){ return particle.upos(); }
    synchronized public double upos(){ return particle.upos(); }
    synchronized public IParticleOnCurve uvelocity(double uv){ particle.uvel(uv); return this; }
    synchronized public IParticleOnCurve uvel(double uv){ particle.uvel(uv); return this; }
    synchronized public double uvelocity(){ return particle.uvel(); }
    synchronized public double uvel(){ return particle.uvel(); }
    synchronized public IParticleOnCurve uforce(double uf){ particle.ufrc(uf); return this; }
    synchronized public IParticleOnCurve ufrc(double uf){ particle.ufrc(uf); return this; }
    synchronized public double uforce(){ return particle.ufrc(); }
    synchronized public double ufrc(){ return particle.ufrc(); }
    
    synchronized public IParticleOnCurve addUForce(double uforce){ particle.upush(uforce); return this; }
    synchronized public IParticleOnCurve resetUForce(){ particle.ureset(); return this; }
    
    synchronized public IParticleOnCurve upush(double uforce){ particle.upush(uforce); return this; }
    synchronized public IParticleOnCurve upull(double uforce){ particle.upull(uforce); return this; }
    synchronized public IParticleOnCurve ureset(){ particle.ureset(); return this; }
    
    
    // partial methods of IDynamics  
    /** add terget object to be updated by this dynamic object. */
    public IParticleOnCurve target(IObject targetObj){ super.target(targetObj); return this; }
    /** get total target number. */
    public int targetNum(){ return super.targetNum(); }
    /** get target object. */
    public IObject target(int i){ return super.target(i); }
    /** get all target objects. */
    public ArrayList<IObject> targets(){ return super.targets(); }
    public IParticleOnCurve removeTarget(int i){ super.removeTarget(i); return this; }
    /** remove target object. */
    public IParticleOnCurve removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    /** update all terget objects (should be called when the dynamic object is updated). */
    public void updateTarget(){ super.updateTarget(); }
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public IParticleOnCurve x(double vx){ pos.x(vx); return this; }
    public IParticleOnCurve y(double vy){ pos.y(vy); return this; }
    public IParticleOnCurve z(double vz){ pos.z(vz); return this; }
    
    public IParticleOnCurve x(IDoubleI vx){ pos.x(vx); return this; }
    public IParticleOnCurve y(IDoubleI vy){ pos.y(vy); return this; }
    public IParticleOnCurve z(IDoubleI vz){ pos.z(vz); return this; }
    
    public IParticleOnCurve x(IVecI vx){ pos.x(vx); return this; }
    public IParticleOnCurve y(IVecI vy){ pos.y(vy); return this; }
    public IParticleOnCurve z(IVecI vz){ pos.z(vz); return this; }

    public IParticleOnCurve x(IVec2I vx){ pos.x(vx); return this; }
    public IParticleOnCurve y(IVec2I vy){ pos.y(vy); return this; }
    

    public IParticleOnCurve dup(){ return new IParticleOnCurve(this); }
    
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

    public IParticleOnCurve zero(){ pos.zero(); return this; }
    
    public IParticleOnCurve add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticleOnCurve add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IParticleOnCurve add(double f, IVecI v){ return add(v,f); }
    public IParticleOnCurve add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public IParticleOnCurve len(IDoubleI l){ pos.len(l); return this; }
    public IParticleOnCurve len(double l){ pos.len(l); return this; }
    
    public IParticleOnCurve unit(){ pos.unit(); return this; }
    
    public IParticleOnCurve rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticleOnCurve rot(double angle){ pos.rot(angle); return this; }
    
    public IParticleOnCurve rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticleOnCurve rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticleOnCurve rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnCurve rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnCurve rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnCurve rot(double centerX, double centerY, double centerZ,
			      double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnCurve rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IParticleOnCurve rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IParticleOnCurve rot2(IDoubleI angle){ return rot(angle); }
    public IParticleOnCurve rot2(double angle){ return rot(angle); }
    public IParticleOnCurve rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IParticleOnCurve rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IParticleOnCurve rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    public IParticleOnCurve rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    public IParticleOnCurve rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    public IParticleOnCurve scale(IDoubleI f){ pos.scale(f); return this; }
    public IParticleOnCurve scale(double f){ pos.scale(f); return this; }
    
    public IParticleOnCurve scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticleOnCurve scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticleOnCurve scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleOnCurve scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticleOnCurve scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticleOnCurve scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleOnCurve scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnCurve scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnCurve scale1d(double centerX, double centerY, double centerZ,
				  double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IParticleOnCurve ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticleOnCurve ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IParticleOnCurve ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticleOnCurve ref(double centerX, double centerY, double centerZ,
			      double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IParticleOnCurve mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticleOnCurve mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX, planeY, planeZ); return this;
    }
    public IParticleOnCurve mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticleOnCurve mirror(double centerX, double centerY, double centerZ,
				 double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
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
    
    public IParticleOnCurve mv(double x, double y, double z){ return add(x,y,z); }
    public IParticleOnCurve mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticleOnCurve mv(IVecI v){ return add(v); }
    
    public IParticleOnCurve cp(){ return dup(); }
    public IParticleOnCurve cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleOnCurve cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleOnCurve cp(IVecI v){ return dup().add(v); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IParticleOnCurve setSize(double sz){ return size(sz); }
    public IParticleOnCurve size(double sz){ point.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IParticleOnCurve name(String nm){ super.name(nm); return this; }
    public IParticleOnCurve layer(ILayer l){ super.layer(l); return this; }
    
    public IParticleOnCurve show(){ super.show(); return this; }
    public IParticleOnCurve hide(){ super.hide(); return this; }
    
    
    public IParticleOnCurve clr(IColor c){ super.clr(c); return this; }
    public IParticleOnCurve clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurve clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurve clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurve clr(Color c){ super.clr(c); return this; }
    public IParticleOnCurve clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurve clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurve clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurve clr(int gray){ super.clr(gray); return this; }
    public IParticleOnCurve clr(float fgray){ super.clr(fgray); return this; }
    public IParticleOnCurve clr(double dgray){ super.clr(dgray); return this; }
    public IParticleOnCurve clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IParticleOnCurve clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IParticleOnCurve clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IParticleOnCurve clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IParticleOnCurve clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IParticleOnCurve clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IParticleOnCurve clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IParticleOnCurve clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IParticleOnCurve clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IParticleOnCurve hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IParticleOnCurve hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IParticleOnCurve hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IParticleOnCurve hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IParticleOnCurve setColor(IColor c){ super.setColor(c); return this; }
    public IParticleOnCurve setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnCurve setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnCurve setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnCurve setColor(Color c){ super.setColor(c); return this; }
    public IParticleOnCurve setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnCurve setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnCurve setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnCurve setColor(int gray){ super.setColor(gray); return this; }
    public IParticleOnCurve setColor(float fgray){ super.setColor(fgray); return this; }
    public IParticleOnCurve setColor(double dgray){ super.setColor(dgray); return this; }
    public IParticleOnCurve setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IParticleOnCurve setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IParticleOnCurve setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IParticleOnCurve setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IParticleOnCurve setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IParticleOnCurve setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IParticleOnCurve setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IParticleOnCurve setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IParticleOnCurve setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IParticleOnCurve setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticleOnCurve setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticleOnCurve setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IParticleOnCurve setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public IParticleOnCurve weight(double w){ super.weight(w); return this; }
    public IParticleOnCurve weight(float w){ super.weight(w); return this; }
    
}
