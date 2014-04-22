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
   Class of an agent with IParticleOnPlaneGeo.
   
   @author Satoru Sugihara
*/
public class IParticleOnPlane extends IPointAgent implements IParticleI /*IParticleOnCurveI*/{
    
    public IParticleOnPlaneGeo particle;
    /** only to refer to particle.vel and particle.frc */
    public IVec vel, frc; 
    
    public IParticleOnPlane(IVecI planeDir,IVecI planePt, IVecI pos, IVecI vel){
	super(pos); initParticleOnPlaneAgent(planeDir, planePt, pos, vel);
    }
    public IParticleOnPlane(IVecI planeDir, IVecI planePt, IVecI pos){
	super(pos); initParticleOnPlaneAgent(planeDir, planePt, pos);
    }
    
    public IParticleOnPlane(IParticleOnPlaneGeo p){ super(p.pos); initParticleOnPlaneAgent(p); }
    public IParticleOnPlane(IParticleOnPlane p){
	super(p.pos.dup()); initParticleOnPlaneAgent(p.particle.planeDir, p.particle.planePt,
						     this.pos,p.particle.vel);
    }
            
    public void initParticleOnPlaneAgent(IVecI planeDir,IVecI planePt, IVecI pos, IVecI vel){
	particle = new IParticleOnPlaneGeo(planeDir,planePt,pos,vel);
	this.pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    public void initParticleOnPlaneAgent(IVecI planeDir,IVecI planePt, IVecI pos){
	particle = new IParticleOnPlaneGeo(planeDir,planePt,pos);
	this.pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    public void initParticleOnPlaneAgent(IParticleOnPlaneGeo ptcl){
	particle = ptcl;
	pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    
    
    /**************************************
     * IParticleI API
     **************************************/
    
    synchronized public IParticleOnPlane fix(){ particle.fix(); return this; }
    synchronized public IParticleOnPlane unfix(){ particle.unfix(); return this; }
    
    public IParticleOnPlane skipUpdateOnce(boolean f){ particle.skipUpdateOnce(f); return this; }
    public boolean skipUpdateOnce(){ return particle.skipUpdateOnce(); }
    
    synchronized public boolean fixed(){ return particle.fixed(); }
    
    synchronized public double mass(){ return particle.mass(); }
    synchronized public IParticleOnPlane mass(double mass){ particle.mass(mass); return this; }
    
    synchronized public IVec position(){ return particle.position(); }
    synchronized public IParticleOnPlane position(IVecI v){ particle.position(v); return this; }
    
    synchronized public IVec pos(){ return particle.pos(); }
    synchronized public IParticleOnPlane pos(IVecI v){ particle.pos(v); return this; }
    
    synchronized public IVec velocity(){ return particle.velocity(); }
    synchronized public IParticleOnPlane velocity(IVecI v){ particle.velocity(v); return this; }
    
    synchronized public IVec vel(){ return particle.vel(); }
    synchronized public IParticleOnPlane vel(IVecI v){ particle.vel(v); return this; }
    
    synchronized public IVec acceleration(){ return particle.acceleration(); }
    //synchronized public IParticleOnPlane acceleration(IVec v){ particle.acceleration(v); return this; }
    synchronized public IVec acc(){ return particle.acc(); }
    //synchronized public IParticleOnPlane acc(IVec v){ particle.acc(v); return this; }
    
    synchronized public IVec force(){ return particle.force(); }
    synchronized public IParticleOnPlane force(IVecI v){ particle.force(v); return this; }
    
    synchronized public IVec frc(){ return particle.frc(); }
    synchronized public IParticleOnPlane frc(IVecI v){ particle.frc(v); return this; }
    
    synchronized public double friction(){ return particle.friction(); }
    synchronized public IParticleOnPlane friction(double friction){ particle.friction(friction); return this; }
    
    synchronized public double fric(){ return particle.fric(); }
    synchronized public IParticleOnPlane fric(double friction){ particle.fric(friction); return this; }
    /* alias of friction */
    synchronized public double decay(){ return fric(); }
    /* alias of friction */
    synchronized public IParticleOnPlane decay(double d){ return fric(d); }
    
    synchronized public IParticleOnPlane push(IVecI f){ particle.push(f); return this; }
    synchronized public IParticleOnPlane push(double fx, double fy, double fz){ particle.push(fx,fy,fz); return this; }
    synchronized public IParticleOnPlane pull(IVecI f){ particle.pull(f); return this; }
    synchronized public IParticleOnPlane pull(double fx, double fy, double fz){ particle.pull(fx,fy,fz); return this; }
    synchronized public IParticleOnPlane addForce(IVecI f){ particle.addForce(f); return this; }
    synchronized public IParticleOnPlane addForce(double fx, double fy, double fz){ particle.addForce(fx,fy,fz); return this; }
    
    
    
    synchronized public IParticleOnPlane reset(){ particle.reset(); return this; }
    synchronized public IParticleOnPlane resetForce(){ particle.resetForce(); return this; }
    
    // partial methods of IDynamics  
    /** add terget object to be updated by this dynamic object. */
    public IParticleOnPlane target(IObject targetObj){ super.target(targetObj); return this; }
    /** get total target number. */
    public int targetNum(){ return super.targetNum(); }
    /** get target object. */
    public IObject target(int i){ return super.target(i); }
    /** get all target objects. */
    public ArrayList<IObject> targets(){ return super.targets(); }
    public IParticleOnPlane removeTarget(int i){ super.removeTarget(i); return this; }
    /** remove target object. */
    public IParticleOnPlane removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    /** update all terget objects (should be called when the dynamic object is updated). */
    public void updateTarget(){ super.updateTarget(); }
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public IParticleOnPlane x(double vx){ pos.x(vx); return this; }
    public IParticleOnPlane y(double vy){ pos.y(vy); return this; }
    public IParticleOnPlane z(double vz){ pos.z(vz); return this; }
    
    public IParticleOnPlane x(IDoubleI vx){ pos.x(vx); return this; }
    public IParticleOnPlane y(IDoubleI vy){ pos.y(vy); return this; }
    public IParticleOnPlane z(IDoubleI vz){ pos.z(vz); return this; }
    
    public IParticleOnPlane x(IVecI vx){ pos.x(vx); return this; }
    public IParticleOnPlane y(IVecI vy){ pos.y(vy); return this; }
    public IParticleOnPlane z(IVecI vz){ pos.z(vz); return this; }

    public IParticleOnPlane x(IVec2I vx){ pos.x(vx); return this; }
    public IParticleOnPlane y(IVec2I vy){ pos.y(vy); return this; }
    

    public IParticleOnPlane dup(){ return new IParticleOnPlane(this); }
    
    public IParticleOnPlane set(IVecI v){ pos.set(v); return this; }
    public IParticleOnPlane set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticleOnPlane set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IParticleOnPlane add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticleOnPlane add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticleOnPlane add(IVecI v){ pos.add(v); return this; }
    
    public IParticleOnPlane sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticleOnPlane sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticleOnPlane sub(IVecI v){ pos.sub(v); return this; }
    public IParticleOnPlane mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticleOnPlane mul(double v){ pos.mul(v); return this; }
    public IParticleOnPlane div(IDoubleI v){ pos.div(v); return this; }
    public IParticleOnPlane div(double v){ pos.div(v); return this; }
    public IParticleOnPlane neg(){ pos.neg(); return this; }
    public IParticleOnPlane rev(){ return neg(); }
    public IParticleOnPlane flip(){ return neg(); }

    public IParticleOnPlane zero(){ pos.zero(); return this; }
    
    public IParticleOnPlane add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticleOnPlane add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IParticleOnPlane add(double f, IVecI v){ return add(v,f); }
    public IParticleOnPlane add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public IParticleOnPlane len(IDoubleI l){ pos.len(l); return this; }
    public IParticleOnPlane len(double l){ pos.len(l); return this; }
    
    public IParticleOnPlane unit(){ pos.unit(); return this; }
    
    public IParticleOnPlane rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticleOnPlane rot(double angle){ pos.rot(angle); return this; }
    
    public IParticleOnPlane rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticleOnPlane rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticleOnPlane rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnPlane rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnPlane rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnPlane rot(double centerX, double centerY, double centerZ,
			      double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnPlane rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IParticleOnPlane rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IParticleOnPlane rot2(IDoubleI angle){ return rot(angle); }
    public IParticleOnPlane rot2(double angle){ return rot(angle); }
    public IParticleOnPlane rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IParticleOnPlane rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IParticleOnPlane rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    public IParticleOnPlane rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    public IParticleOnPlane rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    public IParticleOnPlane scale(IDoubleI f){ pos.scale(f); return this; }
    public IParticleOnPlane scale(double f){ pos.scale(f); return this; }
    
    public IParticleOnPlane scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticleOnPlane scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticleOnPlane scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleOnPlane scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticleOnPlane scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticleOnPlane scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleOnPlane scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnPlane scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnPlane scale1d(double centerX, double centerY, double centerZ,
				  double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IParticleOnPlane ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticleOnPlane ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IParticleOnPlane ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticleOnPlane ref(double centerX, double centerY, double centerZ,
			      double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IParticleOnPlane mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticleOnPlane mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX, planeY, planeZ); return this;
    }
    public IParticleOnPlane mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticleOnPlane mirror(double centerX, double centerY, double centerZ,
				 double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    public IParticleOnPlane shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnPlane shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnPlane shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnPlane shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleOnPlane shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnPlane shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnPlane shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticleOnPlane shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleOnPlane shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnPlane shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnPlane shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticleOnPlane shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleOnPlane shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnPlane shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnPlane shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticleOnPlane shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    public IParticleOnPlane translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticleOnPlane translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticleOnPlane translate(IVecI v){ pos.translate(v); return this; }
    
    public IParticleOnPlane transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticleOnPlane transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticleOnPlane transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticleOnPlane transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IParticleOnPlane mv(double x, double y, double z){ return add(x,y,z); }
    public IParticleOnPlane mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticleOnPlane mv(IVecI v){ return add(v); }
    
    public IParticleOnPlane cp(){ return dup(); }
    public IParticleOnPlane cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleOnPlane cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleOnPlane cp(IVecI v){ return dup().add(v); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IParticleOnPlane setSize(double sz){ return size(sz); }
    public IParticleOnPlane size(double sz){ point.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IParticleOnPlane name(String nm){ super.name(nm); return this; }
    public IParticleOnPlane layer(ILayer l){ super.layer(l); return this; }
    
    public IParticleOnPlane show(){ super.show(); return this; }
    public IParticleOnPlane hide(){ super.hide(); return this; }
    
    
    public IParticleOnPlane clr(IColor c){ super.clr(c); return this; }
    public IParticleOnPlane clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleOnPlane clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IParticleOnPlane clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IParticleOnPlane clr(Color c){ super.clr(c); return this; }
    public IParticleOnPlane clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleOnPlane clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public IParticleOnPlane clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IParticleOnPlane clr(int gray){ super.clr(gray); return this; }
    public IParticleOnPlane clr(float fgray){ super.clr(fgray); return this; }
    public IParticleOnPlane clr(double dgray){ super.clr(dgray); return this; }
    public IParticleOnPlane clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IParticleOnPlane clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IParticleOnPlane clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IParticleOnPlane clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IParticleOnPlane clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IParticleOnPlane clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IParticleOnPlane clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IParticleOnPlane clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IParticleOnPlane clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IParticleOnPlane hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IParticleOnPlane hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IParticleOnPlane hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IParticleOnPlane hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IParticleOnPlane setColor(IColor c){ super.setColor(c); return this; }
    public IParticleOnPlane setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnPlane setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnPlane setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnPlane setColor(Color c){ super.setColor(c); return this; }
    public IParticleOnPlane setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnPlane setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnPlane setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    public IParticleOnPlane setColor(int gray){ super.setColor(gray); return this; }
    public IParticleOnPlane setColor(float fgray){ super.setColor(fgray); return this; }
    public IParticleOnPlane setColor(double dgray){ super.setColor(dgray); return this; }
    public IParticleOnPlane setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IParticleOnPlane setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IParticleOnPlane setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IParticleOnPlane setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IParticleOnPlane setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IParticleOnPlane setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IParticleOnPlane setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IParticleOnPlane setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IParticleOnPlane setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IParticleOnPlane setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticleOnPlane setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticleOnPlane setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IParticleOnPlane setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public IParticleOnPlane weight(double w){ super.weight(w); return this; }
    public IParticleOnPlane weight(float w){ super.weight(w); return this; }
    
}
