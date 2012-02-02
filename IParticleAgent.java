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
   Class of an agent with IParticle.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IParticleAgent extends IPointAgent implements IParticleI{
    
    public IParticle particle;
    /** only to refer to particle.vel and particle.frc */
    public IVec vel, frc; 
    
    public IParticleAgent(){ super(); initParticleAgent(); }
    public IParticleAgent(double x, double y, double z){ super(x,y,z); initParticleAgent(); }
    public IParticleAgent(IVec p){ super(p); initParticleAgent(); }
    public IParticleAgent(IVecI p){ super(p); initParticleAgent(); }
    public IParticleAgent(IParticle ptcl){ super(ptcl.pos); initParticleAgent(ptcl); }
    public IParticleAgent(IParticleAgent p){ super((IPointAgent)p); initParticleAgent(p); }    
    
    public IParticleAgent(double x, double y, double z, double vx, double vy, double vz){ super(x,y,z); initParticleAgent(new IVec(vx,vy,vz)); }
    public IParticleAgent(IVec p, IVec vel){ super(p); initParticleAgent(vel); }
    public IParticleAgent(IVecI p, IVecI vel){ super(p); initParticleAgent(vel); }
    public IParticleAgent(IParticle ptcl, IVecI vel){ super(ptcl.pos); initParticleAgent(ptcl,vel); }
    public IParticleAgent(IParticleAgent p, IVecI vel){ super((IPointAgent)p); initParticleAgent(vel); }    
    
    public void initParticleAgent(){
	particle = new IParticle(pos, (IObject)this);
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    public void initParticleAgent(IParticle ptcl){
	particle = ptcl;
	pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    public void initParticleAgent(IParticleAgent ptcl){
	particle = new IParticle(this.pos, ptcl.vel().dup(), (IObject)this);
	pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    public void initParticleAgent(IVecI vel){
	particle = new IParticle(pos, vel, this);
	this.vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    public void initParticleAgent(IParticle ptcl, IVecI vel){
	particle = ptcl;
	particle.vel(vel);
	pos = particle.pos;
	this.vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }

    /**************************************
     * IParticleI API
     **************************************/
    
    synchronized public IParticleAgent fix(){ particle.fix(); return this; }
    synchronized public IParticleAgent unfix(){ particle.unfix(); return this; }
    
    synchronized public double mass(){ return particle.mass(); }
    synchronized public IParticleAgent mass(double mass){ particle.mass(mass); return this; }
    
    synchronized public IVec position(){ return particle.position(); }
    synchronized public IParticleAgent position(IVecI v){ particle.position(v); return this; }
    
    synchronized public IVec pos(){ return particle.pos(); }
    synchronized public IParticleAgent pos(IVecI v){ particle.pos(v); return this; }
    
    synchronized public IVec velocity(){ return particle.velocity(); }
    synchronized public IParticleAgent velocity(IVecI v){ particle.velocity(v); return this; }
    
    synchronized public IVec vel(){ return particle.vel(); }
    synchronized public IParticleAgent vel(IVecI v){ particle.vel(v); return this; }
    
    synchronized public IVec acceleration(){ return particle.acceleration(); }
    //synchronized public IParticleAgent acceleration(IVec v){ particle.acceleration(v); return this; }
    synchronized public IVec acc(){ return particle.acc(); }
    //synchronized public IParticleAgent acc(IVec v){ particle.acc(v); return this; }
    
    synchronized public IVec force(){ return particle.force(); }
    synchronized public IParticleAgent force(IVecI v){ particle.force(v); return this; }
    
    synchronized public IVec frc(){ return particle.frc(); }
    synchronized public IParticleAgent frc(IVecI v){ particle.frc(v); return this; }
    
    synchronized public double friction(){ return particle.friction(); }
    synchronized public IParticleAgent friction(double friction){ particle.friction(friction); return this; }
    
    synchronized public double fric(){ return particle.fric(); }
    synchronized public IParticleAgent fric(double friction){ particle.fric(friction); return this; }
    /* alias of friction */
    synchronized public double decay(){ return fric(); }
    /* alias of friction */
    synchronized public IParticleAgent decay(double d){ return fric(d); }
    
    synchronized public IParticleAgent push(IVecI f){ particle.push(f); return this; }
    synchronized public IParticleAgent push(double fx, double fy, double fz){ particle.push(fx,fy,fz); return this; }
    synchronized public IParticleAgent pull(IVecI f){ particle.pull(f); return this; }
    synchronized public IParticleAgent pull(double fx, double fy, double fz){ particle.pull(fx,fy,fz); return this; }
    synchronized public IParticleAgent addForce(IVecI f){ particle.addForce(f); return this; }
    synchronized public IParticleAgent addForce(double fx, double fy, double fz){ particle.addForce(fx,fy,fz); return this; }
    
    
    
    synchronized public IParticleAgent reset(){ particle.reset(); return this; }
    synchronized public IParticleAgent resetForce(){ particle.resetForce(); return this; }
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public IParticleAgent x(double vx){ pos.x(vx); return this; }
    public IParticleAgent y(double vy){ pos.y(vy); return this; }
    public IParticleAgent z(double vz){ pos.z(vz); return this; }
    
    public IParticleAgent x(IDoubleI vx){ pos.x(vx); return this; }
    public IParticleAgent y(IDoubleI vy){ pos.y(vy); return this; }
    public IParticleAgent z(IDoubleI vz){ pos.z(vz); return this; }
    

    public IParticleAgent dup(){ return new IParticleAgent(this); }
    
    public IParticleAgent set(IVecI v){ pos.set(v); return this; }
    public IParticleAgent set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticleAgent set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IParticleAgent add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticleAgent add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticleAgent add(IVecI v){ pos.add(v); return this; }
    
    public IParticleAgent sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticleAgent sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticleAgent sub(IVecI v){ pos.sub(v); return this; }
    public IParticleAgent mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticleAgent mul(double v){ pos.mul(v); return this; }
    public IParticleAgent div(IDoubleI v){ pos.div(v); return this; }
    public IParticleAgent div(double v){ pos.div(v); return this; }
    public IParticleAgent neg(){ pos.neg(); return this; }
    public IParticleAgent rev(){ return neg(); }
    public IParticleAgent flip(){ return neg(); }

    public IParticleAgent zero(){ pos.zero(); return this; }
    
    public IParticleAgent add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticleAgent add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IParticleAgent add(double f, IVecI v){ return add(v,f); }
    public IParticleAgent add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public IParticleAgent len(IDoubleI l){ pos.len(l); return this; }
    public IParticleAgent len(double l){ pos.len(l); return this; }
    
    public IParticleAgent unit(){ pos.unit(); return this; }
    
    public IParticleAgent rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticleAgent rot(double angle){ pos.rot(angle); return this; }
    
    public IParticleAgent rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticleAgent rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticleAgent rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleAgent rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleAgent rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleAgent rot(double centerX, double centerY, double centerZ,
			      double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleAgent rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IParticleAgent rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IParticleAgent rot2(IDoubleI angle){ return rot(angle); }
    public IParticleAgent rot2(double angle){ return rot(angle); }
    public IParticleAgent rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IParticleAgent rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IParticleAgent rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    public IParticleAgent rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    public IParticleAgent rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    public IParticleAgent scale(IDoubleI f){ pos.scale(f); return this; }
    public IParticleAgent scale(double f){ pos.scale(f); return this; }
    
    public IParticleAgent scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticleAgent scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticleAgent scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleAgent scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticleAgent scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticleAgent scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleAgent scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleAgent scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleAgent scale1d(double centerX, double centerY, double centerZ,
				  double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IParticleAgent ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticleAgent ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IParticleAgent ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticleAgent ref(double centerX, double centerY, double centerZ,
			      double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IParticleAgent mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticleAgent mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX, planeY, planeZ); return this;
    }
    public IParticleAgent mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticleAgent mirror(double centerX, double centerY, double centerZ,
				 double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    public IParticleAgent shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleAgent shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleAgent shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleAgent shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleAgent shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleAgent shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleAgent shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticleAgent shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleAgent shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleAgent shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleAgent shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticleAgent shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleAgent shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleAgent shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleAgent shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticleAgent shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    public IParticleAgent translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticleAgent translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticleAgent translate(IVecI v){ pos.translate(v); return this; }
    
    public IParticleAgent transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticleAgent transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticleAgent transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticleAgent transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IParticleAgent mv(double x, double y, double z){ return add(x,y,z); }
    public IParticleAgent mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticleAgent mv(IVecI v){ return add(v); }
    
    public IParticleAgent cp(){ return dup(); }
    public IParticleAgent cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleAgent cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleAgent cp(IVecI v){ return dup().add(v); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IParticleAgent setSize(double sz){ return size(sz); }
    public IParticleAgent size(double sz){ point.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IParticleAgent name(String nm){ super.name(nm); return this; }
    public IParticleAgent layer(ILayer l){ super.layer(l); return this; }
    
    public IParticleAgent show(){ super.show(); return this; }
    public IParticleAgent hide(){ super.hide(); return this; }
    
    
    public IParticleAgent clr(Color c){ super.clr(c); return this; }
    public IParticleAgent clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleAgent clr(int gray){ super.clr(gray); return this; }
    public IParticleAgent clr(float fgray){ super.clr(fgray); return this; }
    public IParticleAgent clr(double dgray){ super.clr(dgray); return this; }
    public IParticleAgent clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IParticleAgent clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IParticleAgent clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IParticleAgent clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IParticleAgent clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IParticleAgent clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IParticleAgent clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IParticleAgent clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IParticleAgent clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IParticleAgent hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IParticleAgent hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IParticleAgent hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IParticleAgent hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IParticleAgent setColor(Color c){ super.setColor(c); return this; }
    public IParticleAgent setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticleAgent setColor(int gray){ super.setColor(gray); return this; }
    public IParticleAgent setColor(float fgray){ super.setColor(fgray); return this; }
    public IParticleAgent setColor(double dgray){ super.setColor(dgray); return this; }
    public IParticleAgent setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IParticleAgent setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IParticleAgent setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IParticleAgent setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IParticleAgent setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IParticleAgent setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IParticleAgent setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IParticleAgent setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IParticleAgent setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IParticleAgent setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticleAgent setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticleAgent setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IParticleAgent setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    
}
