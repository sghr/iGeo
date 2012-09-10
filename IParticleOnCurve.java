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
public class IParticleOnCurveAgent extends IPointAgent implements IParticleOnCurveI{
    
    public IParticleOnCurve particle;
    /** only to refer to particle.vel and particle.frc */
    public IVec vel, frc; 
    
    public IParticleOnCurveAgent(ICurveI curve, double upos, double uvel){
	super(); initParticleOnCurveAgent(curve,this.pos,upos,uvel);
    }
    public IParticleOnCurveAgent(ICurveI curve, double upos){
	super(); initParticleOnCurveAgent(curve,this.pos,upos,0);
    }
    public IParticleOnCurveAgent(ICurveI curve){
	super(); initParticleOnCurveAgent(curve,this.pos,0,0);
    }
    public IParticleOnCurveAgent(ICurveI curve, double upos, double uvel, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,upos,uvel);
    }
    public IParticleOnCurveAgent(ICurveI curve, double upos, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,upos,0);
    }
    public IParticleOnCurveAgent(ICurveI curve, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,0,0);
    }
    
    public IParticleOnCurveAgent(ICurve curve, double upos, double uvel){
	super(); initParticleOnCurveAgent(curve,this.pos,upos,uvel);
    }
    public IParticleOnCurveAgent(ICurve curve, double upos){
	super(); initParticleOnCurveAgent(curve,this.pos,upos,0);
    }
    public IParticleOnCurveAgent(ICurve curve){
	super(); initParticleOnCurveAgent(curve,this.pos,0,0);
    }
    public IParticleOnCurveAgent(ICurve curve, double upos, double uvel, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,upos,uvel);
    }
    public IParticleOnCurveAgent(ICurve curve, double upos, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,upos,0);
    }
    public IParticleOnCurveAgent(ICurve curve, IVec pos){
	super(pos); initParticleOnCurveAgent(curve,pos,0,0);
    }
    
    public IParticleOnCurveAgent(IParticleOnCurve p){ super(p.pos); initParticleOnCurveAgent(p); }
    public IParticleOnCurveAgent(IParticleOnCurveAgent p){
	super(p.pos.dup()); initParticleOnCurveAgent(p.particle.curve,this.pos,p.particle.upos,p.particle.uvel);
    }
    
    
    public void initParticleOnCurveAgent(ICurveI curve, IVec pos, double upos, double uvel){
	particle = new IParticleOnCurve(curve,upos,uvel,pos);
	this.pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    public void initParticleOnCurveAgent(ICurve curve, IVec pos, double upos, double uvel){
	particle = new IParticleOnCurve(curve,upos,uvel,pos);
	this.pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    public void initParticleOnCurveAgent(IParticleOnCurve ptcl){
	particle = ptcl;
	pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    
    /**************************************
     * IParticleI API
     **************************************/
    
    synchronized public IParticleOnCurveAgent fix(){ particle.fix(); return this; }
    synchronized public IParticleOnCurveAgent unfix(){ particle.unfix(); return this; }
    
    synchronized public double mass(){ return particle.mass(); }
    synchronized public IParticleOnCurveAgent mass(double mass){ particle.mass(mass); return this; }
    
    synchronized public IVec position(){ return particle.position(); }
    synchronized public IParticleOnCurveAgent position(IVecI v){ particle.position(v); return this; }
    
    synchronized public IVec pos(){ return particle.pos(); }
    synchronized public IParticleOnCurveAgent pos(IVecI v){ particle.pos(v); return this; }
    
    synchronized public IVec velocity(){ return particle.velocity(); }
    synchronized public IParticleOnCurveAgent velocity(IVecI v){ particle.velocity(v); return this; }
    
    synchronized public IVec vel(){ return particle.vel(); }
    synchronized public IParticleOnCurveAgent vel(IVecI v){ particle.vel(v); return this; }
    
    synchronized public IVec acceleration(){ return particle.acceleration(); }
    //synchronized public IParticleOnCurveAgent acceleration(IVec v){ particle.acceleration(v); return this; }
    synchronized public IVec acc(){ return particle.acc(); }
    //synchronized public IParticleOnCurveAgent acc(IVec v){ particle.acc(v); return this; }
    
    synchronized public IVec force(){ return particle.force(); }
    synchronized public IParticleOnCurveAgent force(IVecI v){ particle.force(v); return this; }
    
    synchronized public IVec frc(){ return particle.frc(); }
    synchronized public IParticleOnCurveAgent frc(IVecI v){ particle.frc(v); return this; }
    
    synchronized public double friction(){ return particle.friction(); }
    synchronized public IParticleOnCurveAgent friction(double friction){ particle.friction(friction); return this; }
    
    synchronized public double fric(){ return particle.fric(); }
    synchronized public IParticleOnCurveAgent fric(double friction){ particle.fric(friction); return this; }
    /* alias of friction */
    synchronized public double decay(){ return fric(); }
    /* alias of friction */
    synchronized public IParticleOnCurveAgent decay(double d){ return fric(d); }
    
    synchronized public IParticleOnCurveAgent push(IVecI f){ particle.push(f); return this; }
    synchronized public IParticleOnCurveAgent push(double fx, double fy, double fz){ particle.push(fx,fy,fz); return this; }
    synchronized public IParticleOnCurveAgent pull(IVecI f){ particle.pull(f); return this; }
    synchronized public IParticleOnCurveAgent pull(double fx, double fy, double fz){ particle.pull(fx,fy,fz); return this; }
    synchronized public IParticleOnCurveAgent addForce(IVecI f){ particle.addForce(f); return this; }
    synchronized public IParticleOnCurveAgent addForce(double fx, double fy, double fz){ particle.addForce(fx,fy,fz); return this; }
    
    
    
    synchronized public IParticleOnCurveAgent reset(){ particle.reset(); return this; }
    synchronized public IParticleOnCurveAgent resetForce(){ particle.resetForce(); return this; }
    
    /** IParticleOnCurve methods */
    synchronized public ICurveI curve(){ return particle.curve(); }
    
    synchronized public IParticleOnCurveAgent uposition(double u){ particle.upos(u); return this; }
    synchronized public IParticleOnCurveAgent upos(double u){ particle.upos(u); return this; }
    synchronized public double uposition(){ return particle.upos(); }
    synchronized public double upos(){ return particle.upos(); }
    synchronized public IParticleOnCurveAgent uvelocity(double uv){ particle.uvel(uv); return this; }
    synchronized public IParticleOnCurveAgent uvel(double uv){ particle.uvel(uv); return this; }
    synchronized public double uvelocity(){ return particle.uvel(); }
    synchronized public double uvel(){ return particle.uvel(); }
    synchronized public IParticleOnCurveAgent uforce(double uf){ particle.ufrc(uf); return this; }
    synchronized public IParticleOnCurveAgent ufrc(double uf){ particle.ufrc(uf); return this; }
    synchronized public double uforce(){ return particle.ufrc(); }
    synchronized public double ufrc(){ return particle.ufrc(); }
    
    synchronized public IParticleOnCurveAgent addUForce(double uforce){ particle.upush(uforce); return this; }
    synchronized public IParticleOnCurveAgent resetUForce(){ particle.ureset(); return this; }
    
    synchronized public IParticleOnCurveAgent upush(double uforce){ particle.upush(uforce); return this; }
    synchronized public IParticleOnCurveAgent upull(double uforce){ particle.upull(uforce); return this; }
    synchronized public IParticleOnCurveAgent ureset(){ particle.ureset(); return this; }
    
    
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public IParticleOnCurveAgent x(double vx){ pos.x(vx); return this; }
    public IParticleOnCurveAgent y(double vy){ pos.y(vy); return this; }
    public IParticleOnCurveAgent z(double vz){ pos.z(vz); return this; }
    
    public IParticleOnCurveAgent x(IDoubleI vx){ pos.x(vx); return this; }
    public IParticleOnCurveAgent y(IDoubleI vy){ pos.y(vy); return this; }
    public IParticleOnCurveAgent z(IDoubleI vz){ pos.z(vz); return this; }
    

    public IParticleOnCurveAgent dup(){ return new IParticleOnCurveAgent(this); }
    
    public IParticleOnCurveAgent set(IVecI v){ pos.set(v); return this; }
    public IParticleOnCurveAgent set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticleOnCurveAgent set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IParticleOnCurveAgent add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticleOnCurveAgent add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticleOnCurveAgent add(IVecI v){ pos.add(v); return this; }
    
    public IParticleOnCurveAgent sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticleOnCurveAgent sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticleOnCurveAgent sub(IVecI v){ pos.sub(v); return this; }
    public IParticleOnCurveAgent mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticleOnCurveAgent mul(double v){ pos.mul(v); return this; }
    public IParticleOnCurveAgent div(IDoubleI v){ pos.div(v); return this; }
    public IParticleOnCurveAgent div(double v){ pos.div(v); return this; }
    public IParticleOnCurveAgent neg(){ pos.neg(); return this; }
    public IParticleOnCurveAgent rev(){ return neg(); }
    public IParticleOnCurveAgent flip(){ return neg(); }

    public IParticleOnCurveAgent zero(){ pos.zero(); return this; }
    
    public IParticleOnCurveAgent add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticleOnCurveAgent add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IParticleOnCurveAgent add(double f, IVecI v){ return add(v,f); }
    public IParticleOnCurveAgent add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public IParticleOnCurveAgent len(IDoubleI l){ pos.len(l); return this; }
    public IParticleOnCurveAgent len(double l){ pos.len(l); return this; }
    
    public IParticleOnCurveAgent unit(){ pos.unit(); return this; }
    
    public IParticleOnCurveAgent rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticleOnCurveAgent rot(double angle){ pos.rot(angle); return this; }
    
    public IParticleOnCurveAgent rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticleOnCurveAgent rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticleOnCurveAgent rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnCurveAgent rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnCurveAgent rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnCurveAgent rot(double centerX, double centerY, double centerZ,
			      double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnCurveAgent rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IParticleOnCurveAgent rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IParticleOnCurveAgent rot2(IDoubleI angle){ return rot(angle); }
    public IParticleOnCurveAgent rot2(double angle){ return rot(angle); }
    public IParticleOnCurveAgent rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IParticleOnCurveAgent rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IParticleOnCurveAgent rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    public IParticleOnCurveAgent rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    public IParticleOnCurveAgent rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    public IParticleOnCurveAgent scale(IDoubleI f){ pos.scale(f); return this; }
    public IParticleOnCurveAgent scale(double f){ pos.scale(f); return this; }
    
    public IParticleOnCurveAgent scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticleOnCurveAgent scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticleOnCurveAgent scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleOnCurveAgent scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticleOnCurveAgent scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticleOnCurveAgent scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleOnCurveAgent scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnCurveAgent scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnCurveAgent scale1d(double centerX, double centerY, double centerZ,
				  double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IParticleOnCurveAgent ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticleOnCurveAgent ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IParticleOnCurveAgent ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticleOnCurveAgent ref(double centerX, double centerY, double centerZ,
			      double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IParticleOnCurveAgent mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticleOnCurveAgent mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX, planeY, planeZ); return this;
    }
    public IParticleOnCurveAgent mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticleOnCurveAgent mirror(double centerX, double centerY, double centerZ,
				 double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    public IParticleOnCurveAgent shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurveAgent shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurveAgent shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurveAgent shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleOnCurveAgent shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnCurveAgent shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnCurveAgent shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticleOnCurveAgent shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleOnCurveAgent shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnCurveAgent shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnCurveAgent shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticleOnCurveAgent shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleOnCurveAgent shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnCurveAgent shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnCurveAgent shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticleOnCurveAgent shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    public IParticleOnCurveAgent translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticleOnCurveAgent translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticleOnCurveAgent translate(IVecI v){ pos.translate(v); return this; }
    
    public IParticleOnCurveAgent transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticleOnCurveAgent transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticleOnCurveAgent transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticleOnCurveAgent transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IParticleOnCurveAgent mv(double x, double y, double z){ return add(x,y,z); }
    public IParticleOnCurveAgent mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticleOnCurveAgent mv(IVecI v){ return add(v); }
    
    public IParticleOnCurveAgent cp(){ return dup(); }
    public IParticleOnCurveAgent cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleOnCurveAgent cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleOnCurveAgent cp(IVecI v){ return dup().add(v); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IParticleOnCurveAgent setSize(double sz){ return size(sz); }
    public IParticleOnCurveAgent size(double sz){ point.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IParticleOnCurveAgent name(String nm){ super.name(nm); return this; }
    public IParticleOnCurveAgent layer(ILayer l){ super.layer(l); return this; }
    
    public IParticleOnCurveAgent show(){ super.show(); return this; }
    public IParticleOnCurveAgent hide(){ super.hide(); return this; }
    
    
    public IParticleOnCurveAgent clr(Color c){ super.clr(c); return this; }
    public IParticleOnCurveAgent clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurveAgent clr(int gray){ super.clr(gray); return this; }
    public IParticleOnCurveAgent clr(float fgray){ super.clr(fgray); return this; }
    public IParticleOnCurveAgent clr(double dgray){ super.clr(dgray); return this; }
    public IParticleOnCurveAgent clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IParticleOnCurveAgent clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IParticleOnCurveAgent clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IParticleOnCurveAgent clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IParticleOnCurveAgent clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IParticleOnCurveAgent clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IParticleOnCurveAgent clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IParticleOnCurveAgent clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IParticleOnCurveAgent clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IParticleOnCurveAgent hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IParticleOnCurveAgent hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IParticleOnCurveAgent hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IParticleOnCurveAgent hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IParticleOnCurveAgent setColor(Color c){ super.setColor(c); return this; }
    public IParticleOnCurveAgent setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnCurveAgent setColor(int gray){ super.setColor(gray); return this; }
    public IParticleOnCurveAgent setColor(float fgray){ super.setColor(fgray); return this; }
    public IParticleOnCurveAgent setColor(double dgray){ super.setColor(dgray); return this; }
    public IParticleOnCurveAgent setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IParticleOnCurveAgent setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IParticleOnCurveAgent setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IParticleOnCurveAgent setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IParticleOnCurveAgent setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IParticleOnCurveAgent setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IParticleOnCurveAgent setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IParticleOnCurveAgent setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IParticleOnCurveAgent setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IParticleOnCurveAgent setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticleOnCurveAgent setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticleOnCurveAgent setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IParticleOnCurveAgent setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public IParticleOnCurveAgent weight(double w){ super.weight(w); return this; }
    public IParticleOnCurveAgent weight(float w){ super.weight(w); return this; }
    
}
