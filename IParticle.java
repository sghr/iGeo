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

//import java.awt.Color;
import java.util.ArrayList;

/**
   Class of an agent with IParticleGeo.
   
   @author Satoru Sugihara
*/
public class IParticle extends IPointAgent implements IParticleI{
    
    public IParticleGeo particle;
    /** only to refer to particle.vel and particle.frc */
    public IVec vel, frc; 
    
    public IParticle(){ super(); initParticleAgent(); }
    public IParticle(double x, double y, double z){ super(x,y,z); initParticleAgent(); }
    public IParticle(IVec p){ super(p); initParticleAgent(); }
    public IParticle(IVecI p){ super(p); initParticleAgent(); }
    public IParticle(IParticleGeo ptcl){ super(ptcl.pos); initParticleAgent(ptcl); }
    public IParticle(IParticle p){ super((IPointAgent)p); initParticleAgent(p); }    
    
    public IParticle(double x, double y, double z, double vx, double vy, double vz){ super(x,y,z); initParticleAgent(new IVec(vx,vy,vz)); }
    public IParticle(IVec p, IVec vel){ super(p); initParticleAgent(vel); }
    public IParticle(IVecI p, IVecI vel){ super(p); initParticleAgent(vel); }
    public IParticle(IParticleGeo ptcl, IVecI vel){ super(ptcl.pos); initParticleAgent(ptcl,vel); }
    public IParticle(IParticle p, IVecI vel){ super((IPointAgent)p); initParticleAgent(vel); }
    
    // out of attached geometries
    public IParticle(IGeometry... geometries){
	this();
        if(geometries==null||geometries.length==0){
            pos = new IVec();
        }
        else{
            IVec center = new IVec();
            for(int i=0; i<geometries.length; i++) center.add(geometries[i].center());
            center.div(geometries.length);
            pos(center);
            attach(center, geometries);
        }
	if(point!=null){ point.hide(); } // hide point!
    }
    
    // out of attached geometries
    public IParticle(IVecI geometryOrigin, IGeometry... geometries){
	this();
	pos(geometryOrigin);
        attach(geometryOrigin, geometries);
	if(point!=null){ point.hide(); } // hide point!
    }
    
    public IParticle(IVecI geometryOrigin, IVecI geometryOrientation, IGeometry... geometries){
	this();
	pos(geometryOrigin);
	attach(geometryOrigin, geometryOrientation, geometries);
	if(point!=null){ point.hide(); } // hide point!
    }
    
    
    public void initParticleAgent(){
	particle = new IParticleGeo(pos, (IObject)this);
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    public void initParticleAgent(IParticleGeo ptcl){
	particle = ptcl;
	pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    public void initParticleAgent(IParticle ptcl){
	particle = new IParticleGeo(this.pos, ptcl.vel().dup(), (IObject)this);
	pos = particle.pos;
	vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    public void initParticleAgent(IVecI vel){
	particle = new IParticleGeo(pos, vel, this);
	this.vel = particle.vel;
	frc = particle.frc;
	addDynamics(particle);
    }
    public void initParticleAgent(IParticleGeo ptcl, IVecI vel){
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
    
    synchronized public IParticle fix(){ particle.fix(); return this; }
    synchronized public IParticle unfix(){ particle.unfix(); return this; }
    /** check if it's fixed */
    public boolean fixed(){ return particle.fixed(); }

    
    /** for other agent to control particle */
    public IParticle skipUpdateOnce(boolean f){ particle.skipUpdateOnce(f); return this; }
    /** for other agent to control particle */
    public boolean skipUpdateOnce(){ return particle.skipUpdateOnce; }
    

    
    synchronized public double mass(){ return particle.mass(); }
    synchronized public IParticle mass(double mass){ particle.mass(mass); return this; }
    
    synchronized public IVec position(){ return particle.position(); }
    synchronized public IParticle position(IVecI v){ particle.position(v); return this; }
    
    synchronized public IVec pos(){ return particle.pos(); }
    synchronized public IParticle pos(IVecI v){ particle.pos(v); return this; }
    
    synchronized public IVec velocity(){ return particle.velocity(); }
    synchronized public IParticle velocity(IVecI v){ particle.velocity(v); return this; }
    
    synchronized public IVec vel(){ return particle.vel(); }
    synchronized public IParticle vel(IVecI v){ particle.vel(v); return this; }
    
    synchronized public IVec acceleration(){ return particle.acceleration(); }
    //synchronized public IParticle acceleration(IVec v){ particle.acceleration(v); return this; }
    synchronized public IVec acc(){ return particle.acc(); }
    //synchronized public IParticle acc(IVec v){ particle.acc(v); return this; }
    
    synchronized public IVec force(){ return particle.force(); }
    synchronized public IParticle force(IVecI v){ particle.force(v); return this; }
    
    synchronized public IVec frc(){ return particle.frc(); }
    synchronized public IParticle frc(IVecI v){ particle.frc(v); return this; }
    
    synchronized public double friction(){ return particle.friction(); }
    synchronized public IParticle friction(double friction){ particle.friction(friction); return this; }
    
    synchronized public double fric(){ return particle.fric(); }
    synchronized public IParticle fric(double friction){ particle.fric(friction); return this; }
    /* alias of friction */
    synchronized public double decay(){ return fric(); }
    /* alias of friction */
    synchronized public IParticle decay(double d){ return fric(d); }
    
    synchronized public IParticle push(IVecI f){ particle.push(f); return this; }
    synchronized public IParticle push(double fx, double fy, double fz){ particle.push(fx,fy,fz); return this; }
    synchronized public IParticle pull(IVecI f){ particle.pull(f); return this; }
    synchronized public IParticle pull(double fx, double fy, double fz){ particle.pull(fx,fy,fz); return this; }
    synchronized public IParticle addForce(IVecI f){ particle.addForce(f); return this; }
    synchronized public IParticle addForce(double fx, double fy, double fz){ particle.addForce(fx,fy,fz); return this; }
    
    
    
    synchronized public IParticle reset(){ particle.reset(); return this; }
    synchronized public IParticle resetForce(){ particle.resetForce(); return this; }
    
    
    /*********************************************
     * geometry object attachment to track agent
     ********************************************/
    
    /** attach geometry object to agent to track its location.
	@param geometries one or more geometry objects. object's center is moved to the agent's location.  */
    public IParticle attach(IGeometry... geometries){ super.attach(geometries); return this; }
    
    /** attach geometry object to agent to track its location.
	@param geometryOrigin origin of geometry object which is moved to the agent's location.
	@param geometries one or more geometry objects.
    */
    public IParticle attach(IVecI geometryOrigin, IGeometry... geometries){
	super.attach(geometryOrigin,geometries); return this;
    }
    
    /** attach geometry object to agent to track its location.
	@param geometryOrigin origin of geometry object which is moved to the agent's location.
	@param geometryOrientation original orientation of geometry which is matched with particle's velocity direction; if its's null, orientatin matching is disabled.
	@param geometries one or more geometry objects.
    */
    public IParticle attach(IVecI geometryOrigin, IVecI geometryOrientation, IGeometry... geometries){
	if(tracker!=null) tracker.del(); // replace
	tracker = new IAgentTracker(this,geometryOrigin,geometryOrientation,geometries);
	return this;
    }
    
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public IParticle x(double vx){ pos.x(vx); return this; }
    public IParticle y(double vy){ pos.y(vy); return this; }
    public IParticle z(double vz){ pos.z(vz); return this; }
    
    public IParticle x(IDoubleI vx){ pos.x(vx); return this; }
    public IParticle y(IDoubleI vy){ pos.y(vy); return this; }
    public IParticle z(IDoubleI vz){ pos.z(vz); return this; }

    /** setting x component by x component of input vector*/
    public IParticle x(IVecI v){ pos.x(v); return this; }
    /** setting y component by y component of input vector*/
    public IParticle y(IVecI v){ pos.y(v); return this; }
    /** setting z component by z component of input vector*/
    public IParticle z(IVecI v){ pos.z(v); return this; }
    
    /** setting x component by x component of input vector*/
    public IParticle x(IVec2I v){ pos.x(v); return this; }
    /** setting y component by y component of input vector*/
    public IParticle y(IVec2I v){ pos.y(v); return this; }

    

    public IParticle dup(){ return new IParticle(this); }
    
    public IParticle set(IVecI v){ pos.set(v); return this; }
    public IParticle set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticle set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IParticle add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticle add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticle add(IVecI v){ pos.add(v); return this; }
    
    public IParticle sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticle sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticle sub(IVecI v){ pos.sub(v); return this; }
    public IParticle mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticle mul(double v){ pos.mul(v); return this; }
    public IParticle div(IDoubleI v){ pos.div(v); return this; }
    public IParticle div(double v){ pos.div(v); return this; }
    public IParticle neg(){ pos.neg(); return this; }
    public IParticle rev(){ return neg(); }
    public IParticle flip(){ return neg(); }

    public IParticle zero(){ pos.zero(); return this; }
    
    public IParticle add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticle add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IParticle add(double f, IVecI v){ return add(v,f); }
    public IParticle add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public IParticle len(IDoubleI l){ pos.len(l); return this; }
    public IParticle len(double l){ pos.len(l); return this; }
    
    public IParticle unit(){ pos.unit(); return this; }
    
    public IParticle rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticle rot(double angle){ pos.rot(angle); return this; }
    
    public IParticle rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticle rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticle rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticle rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticle rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticle rot(double centerX, double centerY, double centerZ,
			      double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticle rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IParticle rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IParticle rot2(IDoubleI angle){ return rot(angle); }
    public IParticle rot2(double angle){ return rot(angle); }
    public IParticle rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IParticle rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IParticle rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    public IParticle rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    public IParticle rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    public IParticle scale(IDoubleI f){ pos.scale(f); return this; }
    public IParticle scale(double f){ pos.scale(f); return this; }
    
    public IParticle scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticle scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticle scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticle scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticle scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticle scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticle scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticle scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticle scale1d(double centerX, double centerY, double centerZ,
				  double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IParticle ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticle ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IParticle ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticle ref(double centerX, double centerY, double centerZ,
			      double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IParticle mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IParticle mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX, planeY, planeZ); return this;
    }
    public IParticle mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IParticle mirror(double centerX, double centerY, double centerZ,
				 double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    public IParticle shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticle shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticle shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticle shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticle shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticle shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticle shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticle shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticle shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticle shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticle shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticle shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticle shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticle shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticle shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticle shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    public IParticle translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticle translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticle translate(IVecI v){ pos.translate(v); return this; }
    
    public IParticle transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticle transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticle transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticle transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IParticle mv(double x, double y, double z){ return add(x,y,z); }
    public IParticle mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticle mv(IVecI v){ return add(v); }
    
    public IParticle cp(){ return dup(); }
    public IParticle cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticle cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticle cp(IVecI v){ return dup().add(v); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IParticle setSize(double sz){ return size(sz); }
    public IParticle size(double sz){ point.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IParticle name(String nm){ super.name(nm); return this; }
    public IParticle layer(ILayer l){ super.layer(l); return this; }
    
    public IParticle show(){ super.show(); return this; }
    public IParticle hide(){ super.hide(); return this; }

    public IParticle showPoint(){ super.showPoint(); return this; }
    public IParticle hidePoint(){ super.hidePoint(); return this; }
    public IParticle showGeometry(){ super.showGeometry(); return this; }
    public IParticle hideGeometry(){ super.hideGeometry(); return this; }
    
    
    
    
    public IParticle clr(IColor c){ super.clr(c); return this; }
    public IParticle clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IParticle clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IParticle clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    //public IParticle clr(Color c){ super.clr(c); return this; }
    //public IParticle clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    //public IParticle clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    //public IParticle clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IParticle clr(int gray){ super.clr(gray); return this; }
    public IParticle clr(float fgray){ super.clr(fgray); return this; }
    public IParticle clr(double dgray){ super.clr(dgray); return this; }
    public IParticle clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IParticle clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IParticle clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IParticle clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IParticle clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IParticle clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IParticle clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IParticle clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IParticle clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IParticle hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IParticle hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IParticle hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IParticle hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IParticle setColor(IColor c){ super.setColor(c); return this; }
    public IParticle setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticle setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    public IParticle setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    //public IParticle setColor(Color c){ super.setColor(c); return this; }
    //public IParticle setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    //public IParticle setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    //public IParticle setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    public IParticle setColor(int gray){ super.setColor(gray); return this; }
    public IParticle setColor(float fgray){ super.setColor(fgray); return this; }
    public IParticle setColor(double dgray){ super.setColor(dgray); return this; }
    public IParticle setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IParticle setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IParticle setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IParticle setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IParticle setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IParticle setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IParticle setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IParticle setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IParticle setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IParticle setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticle setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IParticle setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IParticle setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    
    public IParticle weight(double w){ super.weight(w); return this; }
    public IParticle weight(float w){ super.weight(w); return this; }

    
    // partial methods of IDynamics
    /** add terget object to be updated by this dynamic object. */
    public IParticle target(IObject targetObj){ super.target(targetObj); return this; }
    /** remove target object. */
    public IParticle  removeTarget(int i){ super.removeTarget(i); return this; }
    /** remove target object. */
    public IParticle  removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
}
