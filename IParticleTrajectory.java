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
   Particle agent with trajectory curve.
   
   @author Satoru Sugihara
*/
public class IParticleTrajectory extends IParticle implements ITrajectoryI{
    public ITrajectoryGeo trajectory;
    
    public IParticleTrajectory(){ super(); initTrajectory(); }
    public IParticleTrajectory(double x, double y, double z){ super(x,y,z); initTrajectory(); }
    public IParticleTrajectory(IVec p){ super(p); initTrajectory(); }
    public IParticleTrajectory(IVecI p){ super(p); initTrajectory(); }
    public IParticleTrajectory(IParticleGeo ptcl){ super(ptcl); initTrajectory(); }
    public IParticleTrajectory(IParticle p){ super(p); initTrajectory(); }
    public IParticleTrajectory(double x, double y, double z, double vx, double vy, double vz){ super(x,y,z,vx,vy,vz); initTrajectory(); }
    public IParticleTrajectory(IVec p, IVec vel){ super(p,vel); initTrajectory(); }
    public IParticleTrajectory(IVecI p, IVecI vel){ super(p,vel); initTrajectory(); }
    public IParticleTrajectory(IParticleGeo ptcl, IVecI vel){ super(ptcl,vel); initTrajectory(); }
    public IParticleTrajectory(IParticle p, IVecI vel){ super(p,vel); initTrajectory(); }
    
    // out of attached geometries
    public IParticleTrajectory(IGeometry... geometries){
	super(geometries);
	initTrajectory();
    }
    
    // out of attached geometries
    public IParticleTrajectory(IVecI geometryOrigin, IGeometry... geometries){
	super(geometryOrigin,geometries);
	initTrajectory();
    }
    
    public IParticleTrajectory(IVecI geometryOrigin, IVecI geometryOrientation, IGeometry... geometries){
	super(geometryOrigin, geometryOrientation, geometries);
	initTrajectory();
    }
    
    public void initTrajectory(){ trajectory = new ITrajectoryGeo(this); }
    
    public int deg(){ if(trajectory!=null){ return trajectory.deg(); } return 1; }
    public IParticleTrajectory deg(int newDegree){ if(trajectory!=null){ trajectory.deg(newDegree); } return this; }
    
    /** get trajectory curve */
    public ICurve curve(){ if(trajectory!=null){ return trajectory.curve(); } return null; }
    /** alias of curve() */
    public ICurve trajectory(){ return curve(); }
    public IParticleTrajectory addCP(IVecI pt){ if(trajectory!=null){ trajectory.addCP(pt); } return this; }
    
    
    /**************************************
     * IParticleI API
     **************************************/
    
    synchronized public IParticleTrajectory fix(){ super.fix(); return this; }
    synchronized public IParticleTrajectory unfix(){ super.unfix(); return this; }
    synchronized public IParticleTrajectory mass(double mass){ super.mass(mass); return this; }
    synchronized public IParticleTrajectory position(IVecI v){ super.position(v); return this; }
    synchronized public IParticleTrajectory pos(IVecI v){ super.pos(v); return this; }
    synchronized public IParticleTrajectory velocity(IVecI v){ super.velocity(v); return this; }
    synchronized public IParticleTrajectory vel(IVecI v){ super.vel(v); return this; }
    synchronized public IParticleTrajectory force(IVecI v){ super.force(v); return this; }
    synchronized public IParticleTrajectory frc(IVecI v){ super.frc(v); return this; }
    synchronized public IParticleTrajectory friction(double friction){ super.friction(friction); return this; }
    synchronized public IParticleTrajectory fric(double friction){ super.fric(friction); return this; }
    synchronized public IParticleTrajectory decay(double d){ super.decay(d); return this; }
    
    synchronized public IParticleTrajectory push(IVecI f){ super.push(f); return this; }
    synchronized public IParticleTrajectory push(double fx, double fy, double fz){ super.push(fx,fy,fz); return this; }
    synchronized public IParticleTrajectory pull(IVecI f){ super.pull(f); return this; }
    synchronized public IParticleTrajectory pull(double fx, double fy, double fz){ super.pull(fx,fy,fz); return this; }
    synchronized public IParticleTrajectory addForce(IVecI f){ super.addForce(f); return this; }
    synchronized public IParticleTrajectory addForce(double fx, double fy, double fz){ super.addForce(fx,fy,fz); return this; }
    
    synchronized public IParticleTrajectory reset(){ super.reset(); return this; }
    synchronized public IParticleTrajectory resetForce(){ super.resetForce(); return this; }
    
    
    /*********************************************
     * geometry object attachment to track agent
     ********************************************/
    
    /** attach geometry object to agent to track its location.
	@param geometries one or more geometry objects. object's center is moved to the agent's location.  */
    public IParticleTrajectory attach(IGeometry... geometries){ super.attach(geometries); return this; }
    /** attach geometry object to agent to track its location.
	@param geometryOrigin origin of geometry object which is moved to the agent's location.
	@param geometries one or more geometry objects.
    */
    public IParticleTrajectory attach(IVecI geometryOrigin, IGeometry... geometries){
	super.attach(geometryOrigin,geometries); return this;
    }
    /** attach geometry object to agent to track its location.
	@param geometryOrigin origin of geometry object which is moved to the agent's location.
	@param geometryOrientation original orientation of geometry which is matched with particle's velocity direction; if its's null, orientatin matching is disabled.
	@param geometries one or more geometry objects.
    */
    public IParticleTrajectory attach(IVecI geometryOrigin, IVecI geometryOrientation, IGeometry... geometries){
	super.attach(geometryOrigin,geometryOrientation,geometries); return this;
    }
    
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public IParticleTrajectory x(double vx){ super.x(vx); return this; }
    public IParticleTrajectory y(double vy){ super.y(vy); return this; }
    public IParticleTrajectory z(double vz){ super.z(vz); return this; }
    
    public IParticleTrajectory x(IDoubleI vx){ super.x(vx); return this; }
    public IParticleTrajectory y(IDoubleI vy){ super.y(vy); return this; }
    public IParticleTrajectory z(IDoubleI vz){ super.z(vz); return this; }
        
    public IParticleTrajectory dup(){ return new IParticleTrajectory(this); }
    
    public IParticleTrajectory set(IVecI v){ super.set(v); return this; }
    public IParticleTrajectory set(double x, double y, double z){ super.set(x,y,z); return this;}
    public IParticleTrajectory set(IDoubleI x, IDoubleI y, IDoubleI z){ super.set(x,y,z); return this; }
    public IParticleTrajectory add(double x, double y, double z){ super.add(x,y,z); return this; }
    public IParticleTrajectory add(IDoubleI x, IDoubleI y, IDoubleI z){ super.add(x,y,z); return this; }    
    public IParticleTrajectory add(IVecI v){ super.add(v); return this; }
    public IParticleTrajectory sub(double x, double y, double z){ super.sub(x,y,z); return this; }
    public IParticleTrajectory sub(IDoubleI x, IDoubleI y, IDoubleI z){ super.sub(x,y,z); return this; }
    public IParticleTrajectory sub(IVecI v){ super.sub(v); return this; }
    public IParticleTrajectory mul(IDoubleI v){ super.mul(v); return this; }
    public IParticleTrajectory mul(double v){ super.mul(v); return this; }
    public IParticleTrajectory div(IDoubleI v){ super.div(v); return this; }
    public IParticleTrajectory div(double v){ super.div(v); return this; }
    public IParticleTrajectory neg(){ super.neg(); return this; }
    public IParticleTrajectory rev(){ super.rev(); return this; }
    public IParticleTrajectory flip(){ super.flip(); return this; }
    public IParticleTrajectory zero(){ super.zero(); return this; }
    
    public IParticleTrajectory add(IVecI v, double f){ super.add(v,f); return this; }
    public IParticleTrajectory add(IVecI v, IDoubleI f){ super.add(v,f); return this; }
    public IParticleTrajectory add(double f, IVecI v){ super.add(f,v); return this; }
    public IParticleTrajectory add(IDoubleI f, IVecI v){ super.add(f,v); return this; }
    
    public IParticleTrajectory len(IDoubleI l){ super.len(l); return this; }
    public IParticleTrajectory len(double l){ super.len(l); return this; }
    public IParticleTrajectory unit(){ super.unit(); return this; }
    public IParticleTrajectory rot(IDoubleI angle){ super.rot(angle); return this; }
    public IParticleTrajectory rot(double angle){ super.rot(angle); return this; }
    public IParticleTrajectory rot(IVecI axis, IDoubleI angle){ super.rot(axis,angle); return this; }
    public IParticleTrajectory rot(IVecI axis, double angle){ super.rot(axis,angle); return this; }
    public IParticleTrajectory rot(double axisX, double axisY, double axisZ, double angle){
	super.rot(axisX,axisY,axisZ,angle); return this;
    }
    public IParticleTrajectory rot(IVecI center, IVecI axis, double angle){
	super.rot(center, axis,angle); return this;
    }
    public IParticleTrajectory rot(IVecI center, IVecI axis, IDoubleI angle){
	super.rot(center, axis,angle); return this;
    }
    public IParticleTrajectory rot(double centerX, double centerY, double centerZ,
			      double axisX, double axisY, double axisZ, double angle){
	super.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    public IParticleTrajectory rot(IVecI axis, IVecI destDir){ super.rot(axis,destDir); return this; }
    public IParticleTrajectory rot(IVecI center, IVecI axis, IVecI destPt){
	super.rot(center,axis,destPt); return this;
    }
    public IParticleTrajectory rot2(IDoubleI angle){ super.rot2(angle); return this; }
    public IParticleTrajectory rot2(double angle){ super.rot2(angle); return this; }
    public IParticleTrajectory rot2(IVecI center, double angle){ super.rot2(center,angle); return this; }
    public IParticleTrajectory rot2(IVecI center, IDoubleI angle){ super.rot2(center,angle); return this; }
    public IParticleTrajectory rot2(double centerX, double centerY, double angle){
	super.rot2(centerX,centerY,angle); return this;
    }
    public IParticleTrajectory rot2(IVecI destDir){ super.rot2(destDir); return this; }
    public IParticleTrajectory rot2(IVecI center, IVecI destPt){ super.rot2(center,destPt); return this; }
    
    public IParticleTrajectory scale(IDoubleI f){ super.scale(f); return this; }
    public IParticleTrajectory scale(double f){ super.scale(f); return this; }
    
    public IParticleTrajectory scale(IVecI center, IDoubleI f){ super.scale(center,f); return this; }
    public IParticleTrajectory scale(IVecI center, double f){ super.scale(center,f); return this; }
    public IParticleTrajectory scale(double centerX, double centerY, double centerZ, double f){
	super.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleTrajectory scale1d(IVecI axis, double f){ super.scale1d(axis,f); return this; }
    public IParticleTrajectory scale1d(IVecI axis, IDoubleI f){ super.scale1d(axis,f); return this; }
    public IParticleTrajectory scale1d(double axisX, double axisY, double axisZ, double f){
	super.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleTrajectory scale1d(IVecI center, IVecI axis, double f){
	super.scale1d(center,axis,f); return this;
    }
    public IParticleTrajectory scale1d(IVecI center, IVecI axis, IDoubleI f){
	super.scale1d(center,axis,f); return this;
    }
    public IParticleTrajectory scale1d(double centerX, double centerY, double centerZ,
				  double axisX, double axisY, double axisZ, double f){
	super.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IParticleTrajectory ref(IVecI planeDir){ super.ref(planeDir); return this; }
    public IParticleTrajectory ref(double planeX, double planeY, double planeZ){
	super.ref(planeX,planeY,planeZ); return this;
    }
    public IParticleTrajectory ref(IVecI center, IVecI planeDir){
	super.ref(center,planeDir); return this;
    }
    public IParticleTrajectory ref(double centerX, double centerY, double centerZ,
			      double planeX, double planeY, double planeZ){
	super.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IParticleTrajectory mirror(IVecI planeDir){ super.ref(planeDir); return this; }
    public IParticleTrajectory mirror(double planeX, double planeY, double planeZ){
	super.ref(planeX, planeY, planeZ); return this;
    }
    public IParticleTrajectory mirror(IVecI center, IVecI planeDir){
	super.ref(center,planeDir); return this;
    }
    public IParticleTrajectory mirror(double centerX, double centerY, double centerZ,
				 double planeX, double planeY, double planeZ){
	super.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    public IParticleTrajectory shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleTrajectory shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleTrajectory shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleTrajectory shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleTrajectory shearXY(double sxy, double syx){ super.shearXY(sxy,syx); return this; }
    public IParticleTrajectory shearXY(IDoubleI sxy, IDoubleI syx){ super.shearXY(sxy,syx); return this; }
    public IParticleTrajectory shearXY(IVecI center, double sxy, double syx){
	super.shearXY(center,sxy,syx); return this;
    }
    public IParticleTrajectory shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	super.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleTrajectory shearYZ(double syz, double szy){ super.shearYZ(syz,szy); return this; }
    public IParticleTrajectory shearYZ(IDoubleI syz, IDoubleI szy){ super.shearYZ(syz,szy); return this; }
    public IParticleTrajectory shearYZ(IVecI center, double syz, double szy){
	super.shearYZ(center,syz,szy); return this;
    }
    public IParticleTrajectory shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	super.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleTrajectory shearZX(double szx, double sxz){ super.shearZX(szx,sxz); return this; }
    public IParticleTrajectory shearZX(IDoubleI szx, IDoubleI sxz){ super.shearZX(szx,sxz); return this; }
    public IParticleTrajectory shearZX(IVecI center, double szx, double sxz){
	super.shearZX(center,szx,sxz); return this;
    }
    public IParticleTrajectory shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	super.shearZX(center,szx,sxz); return this;
    }
    
    public IParticleTrajectory translate(double x, double y, double z){ super.translate(x,y,z); return this; }
    public IParticleTrajectory translate(IDoubleI x, IDoubleI y, IDoubleI z){ super.translate(x,y,z); return this; }
    public IParticleTrajectory translate(IVecI v){ super.translate(v); return this; }
    
    public IParticleTrajectory transform(IMatrix3I mat){ super.transform(mat); return this; }
    public IParticleTrajectory transform(IMatrix4I mat){ super.transform(mat); return this; }
    public IParticleTrajectory transform(IVecI xvec, IVecI yvec, IVecI zvec){
	super.transform(xvec,yvec,zvec); return this;
    }
    public IParticleTrajectory transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	super.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IParticleTrajectory mv(double x, double y, double z){ super.mv(x,y,z); return this; }
    public IParticleTrajectory mv(IDoubleI x, IDoubleI y, IDoubleI z){ super.mv(x,y,z); return this; }
    public IParticleTrajectory mv(IVecI v){ super.mv(v); return this; }
    public IParticleTrajectory cp(){ return dup(); }
    public IParticleTrajectory cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleTrajectory cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleTrajectory cp(IVecI v){ return dup().add(v); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IParticleTrajectory setSize(double sz){ super.setSize(sz); return this; }
    public IParticleTrajectory size(double sz){ super.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IParticleTrajectory name(String nm){ super.name(nm); return this; }
    public IParticleTrajectory layer(ILayer l){ super.layer(l); return this; }
    
    public IParticleTrajectory show(){ super.show(); if(trajectory!=null){ trajectory.show(); } return this; }
    public IParticleTrajectory hide(){ super.hide(); if(trajectory!=null){ trajectory.hide(); } return this; }
    
    public IParticleTrajectory showPoint(){ super.showPoint(); return this; }
    public IParticleTrajectory hidePoint(){ super.hidePoint(); return this; }
    public IParticleTrajectory showGeometry(){ super.showGeometry(); return this; }
    public IParticleTrajectory hideGeometry(){ super.hideGeometry(); return this; }
    
    public IParticleTrajectory showTrajectory(){ if(trajectory!=null){ trajectory.show(); } return this; }
    public IParticleTrajectory hideTrajectory(){ if(trajectory!=null){ trajectory.hide(); } return this; }
    
    public void del(){ if(trajectory!=null){ trajectory.del(); } super.del(); }
    
    public void del(boolean deleteTrajectory){ if(trajectory!=null&&deleteTrajectory){ trajectory.del(); } super.del(); } // added 20121027
    
    public IParticleTrajectory clr(IColor c){ super.clr(c); if(trajectory!=null){ trajectory.clr(c); } return this; }
    public IParticleTrajectory clr(IColor c, int alpha){ super.clr(c,alpha); if(trajectory!=null){ trajectory.clr(c,alpha); } return this; }
    public IParticleTrajectory clr(IColor c, float alpha){ super.clr(c,alpha); if(trajectory!=null){ trajectory.clr(c,alpha); } return this; }
    public IParticleTrajectory clr(IColor c, double alpha){ super.clr(c,alpha); if(trajectory!=null){ trajectory.clr(c,alpha); } return this; }
    //public IParticleTrajectory clr(Color c){ super.clr(c); if(trajectory!=null){ trajectory.clr(c); } return this; }
    //public IParticleTrajectory clr(Color c, int alpha){ super.clr(c,alpha); if(trajectory!=null){ trajectory.clr(c,alpha); } return this; }
    //public IParticleTrajectory clr(Color c, float alpha){ super.clr(c,alpha); if(trajectory!=null){ trajectory.clr(c,alpha); } return this; }
    //public IParticleTrajectory clr(Color c, double alpha){ super.clr(c,alpha); if(trajectory!=null){ trajectory.clr(c,alpha); } return this; }
    public IParticleTrajectory clr(int gray){ super.clr(gray); if(trajectory!=null){ trajectory.clr(gray); }  return this; }
    public IParticleTrajectory clr(float fgray){ super.clr(fgray); if(trajectory!=null){ trajectory.clr(fgray); } return this; }
    public IParticleTrajectory clr(double dgray){ super.clr(dgray); if(trajectory!=null){ trajectory.clr(dgray); } return this; }
    public IParticleTrajectory clr(int gray, int alpha){ super.clr(gray,alpha); if(trajectory!=null){ trajectory.clr(gray,alpha); } return this; }
    public IParticleTrajectory clr(float fgray, float falpha){ super.clr(fgray,falpha); if(trajectory!=null){ trajectory.clr(fgray,falpha); } return this; }
    public IParticleTrajectory clr(double dgray, double dalpha){ super.clr(dgray,dalpha); if(trajectory!=null){ trajectory.clr(dgray,dalpha); } return this; }
    public IParticleTrajectory clr(int r, int g, int b){ super.clr(r,g,b); if(trajectory!=null){ trajectory.clr(r,g,b); } return this; }
    public IParticleTrajectory clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); if(trajectory!=null){ trajectory.clr(fr,fg,fb); } return this; }
    public IParticleTrajectory clr(double dr, double dg, double db){ super.clr(dr,dg,db); if(trajectory!=null){ trajectory.clr(dr,dg,db); } return this; }
    public IParticleTrajectory clr(int r, int g, int b, int a){ super.clr(r,g,b,a); if(trajectory!=null){ trajectory.clr(r,g,b,a); } return this; }
    public IParticleTrajectory clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); if(trajectory!=null){ trajectory.clr(fr,fg,fb,fa); } return this; }
    public IParticleTrajectory clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); if(trajectory!=null){ trajectory.clr(dr,dg,db,da); } return this; }
    public IParticleTrajectory hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); if(trajectory!=null){ trajectory.hsb(h,s,b,a); } return this; }
    public IParticleTrajectory hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); if(trajectory!=null){ trajectory.hsb(h,s,b,a); } return this; }
    public IParticleTrajectory hsb(float h, float s, float b){ super.hsb(h,s,b); if(trajectory!=null){ trajectory.hsb(h,s,b); } return this; }
    public IParticleTrajectory hsb(double h, double s, double b){ super.hsb(h,s,b); if(trajectory!=null){ trajectory.hsb(h,s,b); } return this; }
    
    public IParticleTrajectory setColor(IColor c){ super.setColor(c); if(trajectory!=null){ trajectory.setColor(c); } return this; }
    public IParticleTrajectory setColor(IColor c, int alpha){ super.setColor(c,alpha); if(trajectory!=null){ trajectory.setColor(c,alpha); } return this; }
    public IParticleTrajectory setColor(IColor c, float alpha){ super.setColor(c,alpha); if(trajectory!=null){ trajectory.setColor(c,alpha); } return this; }
    public IParticleTrajectory setColor(IColor c, double alpha){ super.setColor(c,alpha); if(trajectory!=null){ trajectory.setColor(c,alpha); } return this; }
    //public IParticleTrajectory setColor(Color c){ super.setColor(c); if(trajectory!=null){ trajectory.setColor(c); } return this; }
    //public IParticleTrajectory setColor(Color c, int alpha){ super.setColor(c,alpha); if(trajectory!=null){ trajectory.setColor(c,alpha); } return this; }
    //public IParticleTrajectory setColor(Color c, float alpha){ super.setColor(c,alpha); if(trajectory!=null){ trajectory.setColor(c,alpha); } return this; }
    //public IParticleTrajectory setColor(Color c, double alpha){ super.setColor(c,alpha); if(trajectory!=null){ trajectory.setColor(c,alpha); } return this; }
    public IParticleTrajectory setColor(int gray){ super.setColor(gray); if(trajectory!=null){ trajectory.setColor(gray); } return this; }
    public IParticleTrajectory setColor(float fgray){ super.setColor(fgray); if(trajectory!=null){ trajectory.setColor(fgray); } return this; }
    public IParticleTrajectory setColor(double dgray){ super.setColor(dgray); if(trajectory!=null){ trajectory.setColor(dgray); } return this; }
    public IParticleTrajectory setColor(int gray, int alpha){ super.setColor(gray,alpha);  if(trajectory!=null){ trajectory.setColor(gray,alpha); }return this; }
    public IParticleTrajectory setColor(float fgray, float falpha){ super.setColor(fgray,falpha);  if(trajectory!=null){ trajectory.setColor(fgray,falpha); }return this; }
    public IParticleTrajectory setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha);  if(trajectory!=null){ trajectory.setColor(dgray,dalpha); }return this; }
    public IParticleTrajectory setColor(int r, int g, int b){ super.setColor(r,g,b);  if(trajectory!=null){ trajectory.setColor(r,g,b); }return this; }
    public IParticleTrajectory setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb);  if(trajectory!=null){ trajectory.setColor(fr,fg,fb); }return this; }
    public IParticleTrajectory setColor(double dr, double dg, double db){ super.setColor(dr,dg,db);  if(trajectory!=null){ trajectory.setColor(dr,dg,db); }return this; }
    public IParticleTrajectory setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a);  if(trajectory!=null){ trajectory.setColor(r,g,b,a); }return this; }
    public IParticleTrajectory setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa);  if(trajectory!=null){ trajectory.setColor(fr,fg,fb,fa); }return this; }
    public IParticleTrajectory setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da);  if(trajectory!=null){ trajectory.setColor(dr,dg,db,da); }return this; }
    public IParticleTrajectory setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); if(trajectory!=null){ trajectory.setHSBColor(h,s,b,a); } return this; }
    public IParticleTrajectory setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); if(trajectory!=null){ trajectory.setHSBColor(h,s,b,a); } return this; }
    public IParticleTrajectory setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); if(trajectory!=null){ trajectory.setHSBColor(h,s,b); } return this; }
    public IParticleTrajectory setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); if(trajectory!=null){ trajectory.setHSBColor(h,s,b); } return this; }
    
    
    public IParticleTrajectory weight(double w){ super.weight(w); if(trajectory!=null){ trajectory.weight(w); } return this; }
    public IParticleTrajectory weight(float w){ super.weight(w); if(trajectory!=null){ trajectory.weight(w); } return this; }

    
    // partial methods of IDynamics
    /** add terget object to be updated by this dynamic object. */
    public IParticleTrajectory target(IObject targetObj){ super.target(targetObj); return this; }
    /** remove target object. */
    public IParticleTrajectory  removeTarget(int i){ super.removeTarget(i); return this; }
    /** remove target object. */
    public IParticleTrajectory  removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
}
