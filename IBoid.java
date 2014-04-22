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
   Class of an agent based on one point, extending IPoint and implements IDynamics
   
   @author Satoru Sugihara
*/
public class IBoid extends IParticle implements IBoidI{
    
    public IBoid(){ super(new IBoidGeo()); }
    public IBoid(IVecI pt){ super(new IBoidGeo(pt)); }
    public IBoid(IVec pt){ super(new IBoidGeo(pt)); }
    public IBoid(IVecI pt, IVecI vel){ super(new IBoidGeo(pt,vel)); }
    public IBoid(IVec pt, IVec vel){ super(new IBoidGeo(pt,vel)); }
    public IBoid(double x, double y, double z){ super(new IBoidGeo(x,y,z)); }
    public IBoid(double x, double y, double z, double vx, double vy, double vz){
	super(new IBoidGeo(x,y,z,vx,vy,vz)); 
    }
    public IBoid(IBoid b){ super(new IBoidGeo((IBoidGeo)b.particle));}
    
    
    // out of attached geometries
    public IBoid(IGeometry... geometries){
	super(geometries);
	initParticleAgent(new IBoidGeo(pos));
    }
    
    // out of attached geometries
    public IBoid(IVecI geometryOrigin, IGeometry... geometries){
	super(geometryOrigin,geometries);
	initParticleAgent(new IBoidGeo(pos));
    }
    
    public IBoid(IVecI geometryOrigin, IVecI geometryOrientation, IGeometry... geometries){
	super(geometryOrigin, geometryOrientation, geometries);
	initParticleAgent(new IBoidGeo(pos));
    }
    
    
    
    public IBoidGeo boid(){ return (IBoidGeo)particle; } // (IParticleGeo)particle in IParticle
    
    public double cohDist(){ return boid().cohDist(); }
    public double cohesionDist(){ return cohDist(); }
    public IBoid cohDist(double dist){ boid().cohDist(dist); return this; }
    public IBoid cohesionDist(double dist){ return cohDist(dist); }
    public double cohRatio(){ return boid().cohRatio(); }
    public double cohesionRatio(){ return cohRatio(); }
    public IBoid cohRatio(double ratio){ boid().cohRatio(ratio); return this; }
    public IBoid cohesionRatio(double ratio){ return cohRatio(ratio); }
    public double cohLimit(){ return boid().cohLimit(); }
    public double cohesionLimit(){ return cohLimit(); }
    public IBoid cohLimit(double limit){ boid().cohLimit(limit); return this; }
    public IBoid cohesionLimit(double limit){ return cohLimit(limit); }
    
    public IBoid coh(double ratio, double dist){ boid().coh(ratio,dist); return this; }
    public IBoid cohesion(double ratio, double dist){ return coh(ratio,dist); }
    
    public double sepDist(){ return boid().sepDist(); }
    public double separationDist(){ return sepDist(); }
    public IBoid sepDist(double dist){ boid().sepDist(dist); return this; }
    public IBoid separationDist(double dist){ return sepDist(dist); }
    public double sepRatio(){ return boid().sepRatio(); }
    public double separationRatio(){ return sepRatio(); }
    public IBoid sepRatio(double ratio){ boid().sepRatio(ratio); return this; }
    public IBoid separationRatio(double ratio){ return sepRatio(ratio); }
    public double sepLimit(){ return boid().sepLimit(); }
    public double separationLimit(){ return sepLimit(); }
    public IBoid sepLimit(double limit){ boid().sepLimit(limit); return this; }
    public IBoid separationLimit(double limit){ return sepLimit(limit); }

    public IBoid sep(double ratio, double dist){ boid().sep(ratio,dist); return this; }
    public IBoid separation(double ratio, double dist){ return sep(ratio,dist); }
    
    public double aliDist(){ return boid().aliDist(); }
    public double alignmentDist(){ return aliDist(); }
    public IBoid aliDist(double dist){ boid().aliDist(dist); return this; }
    public IBoid alignmentDist(double dist){ return aliDist(dist); }
    public double aliRatio(){ return boid().aliRatio(); }
    public double alignmentRatio(){ return aliRatio(); }
    public IBoid aliRatio(double ratio){ boid().aliRatio(ratio); return this; }
    public IBoid alignmentRatio(double ratio){ return aliRatio(ratio); }
    public double aliLimit(){ return boid().aliLimit(); }
    public double alignmentLimit(){ return aliLimit(); }
    public IBoid aliLimit(double limit){ boid().aliLimit(limit); return this; }
    public IBoid alignmentLimit(double limit){ return aliLimit(limit); }
    
    public IBoid ali(double ratio, double dist){ boid().ali(ratio,dist); return this; }
    public IBoid alignment(double ratio, double dist){ return ali(ratio,dist); }
    
    public IBoid parameter(double cohRatio, double cohDist, 
			   double sepRatio, double sepDist, 
			   double aliRatio, double aliDist){
	return param(cohRatio,cohDist,sepRatio,sepDist,aliRatio,aliDist); 
    }
    
    public IBoid param(double cohRatio, double cohDist, 
		       double sepRatio, double sepDist, 
		       double aliRatio, double aliDist){
	boid().param(cohRatio,cohDist,sepRatio,sepDist,aliRatio,aliDist);
	return this;
    }
    
    /**************************************
     * IParticleI API
     **************************************/
    
    public IBoid fix(){ super.fix(); return this; }
    public IBoid unfix(){ super.unfix(); return this; }
    public IBoid skipUpdateOnce(boolean f){ super.skipUpdateOnce(f); return this; }
    public IBoid mass(double mass){ super.mass(mass); return this; }
    public IBoid position(IVecI v){ super.position(v); return this; }
    public IBoid pos(IVecI v){ super.pos(v); return this; }
    public IBoid velocity(IVecI v){ super.velocity(v); return this; }
    public IBoid vel(IVecI v){ super.vel(v); return this; }
    public IBoid force(IVecI v){ super.force(v); return this; }
    public IBoid frc(IVecI v){ super.frc(v); return this; }
    public IBoid friction(double friction){ super.friction(friction); return this; }
    public IBoid fric(double friction){ super.fric(friction); return this; }
    public IBoid decay(double d){ return fric(d); }
    
    public IBoid push(IVecI f){ super.push(f); return this; }
    public IBoid push(double fx, double fy, double fz){ super.push(fx,fy,fz); return this; }
    public IBoid pull(IVecI f){ super.pull(f); return this; }
    public IBoid pull(double fx, double fy, double fz){ super.pull(fx,fy,fz); return this; }
    public IBoid addForce(IVecI f){ super.addForce(f); return this; }
    public IBoid addForce(double fx, double fy, double fz){ super.addForce(fx,fy,fz); return this; }
    public IBoid reset(){ super.reset(); return this; }
    public IBoid resetForce(){ super.resetForce(); return this; }
    

    /*********************************************
     * geometry object attachment to track agent
     ********************************************/
    
    /** attach geometry object to agent to track its location.
	@param geometries one or more geometry objects. object's center is moved to the agent's location.  */
    public IBoid attach(IGeometry... geometries){ super.attach(geometries); return this; }
    /** attach geometry object to agent to track its location.
	@param geometryOrigin origin of geometry object which is moved to the agent's location.
	@param geometries one or more geometry objects.
    */
    public IBoid attach(IVecI geometryOrigin, IGeometry... geometries){
	super.attach(geometryOrigin,geometries); return this;
    }
    /** attach geometry object to agent to track its location.
	@param geometryOrigin origin of geometry object which is moved to the agent's location.
	@param geometryOrientation original orientation of geometry which is matched with particle's velocity direction; if its's null, orientatin matching is disabled.
	@param geometries one or more geometry objects.
    */
    public IBoid attach(IVecI geometryOrigin, IVecI geometryOrientation, IGeometry... geometries){
	super.attach(geometryOrigin,geometryOrientation,geometries); return this;
    }
    
    

    /**************************************
     * target class
     *************************************/
    /** make the field applicable only to the specified target class */
    public IBoid targetClass(Class<? extends IBoidI> targetClass){
	boid().targetClass(targetClass); return this;
    }
    /** alias */
    public IBoid target(Class<? extends IBoidI> targetClass){ return targetClass(targetClass); }
    
    
    /** make the field applicable only to the specified target classes */
    public IBoid targetClass(Class<? extends IBoidI>... targets){
	boid().targetClass(targets); return this;
    }
    /** alias */
    public IBoid target(Class<? extends IBoidI>... targets){ return targetClass(targets); }
    
    public boolean isTargetClass(Object obj){ return boid().isTargetClass(obj); }
    /** alias */
    public boolean isTarget(Object obj){ return isTargetClass(obj); }
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    public IBoid x(double vx){ pos.x(vx); return this; }
    public IBoid y(double vy){ pos.y(vy); return this; }
    public IBoid z(double vz){ pos.z(vz); return this; }
    
    public IBoid x(IDoubleI vx){ pos.x(vx); return this; }
    public IBoid y(IDoubleI vy){ pos.y(vy); return this; }
    public IBoid z(IDoubleI vz){ pos.z(vz); return this; }
    
    /** setting x component by x component of input vector*/
    public IBoid x(IVecI v){ pos.x(v); return this; }
    /** setting y component by y component of input vector*/
    public IBoid y(IVecI v){ pos.y(v); return this; }
    /** setting z component by z component of input vector*/
    public IBoid z(IVecI v){ pos.z(v); return this; }
    
    /** setting x component by x component of input vector*/
    public IBoid x(IVec2I v){ pos.x(v); return this; }
    /** setting y component by y component of input vector*/
    public IBoid y(IVec2I v){ pos.y(v); return this; }
    
    
    public IBoid dup(){ return new IBoid(this); }
    
    public IBoid set(IVecI v){ pos.set(v); return this; }
    public IBoid set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IBoid set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IBoid add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IBoid add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IBoid add(IVecI v){ pos.add(v); return this; }
    
    public IBoid sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IBoid sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IBoid sub(IVecI v){ pos.sub(v); return this; }
    public IBoid mul(IDoubleI v){ pos.mul(v); return this; }
    public IBoid mul(double v){ pos.mul(v); return this; }
    public IBoid div(IDoubleI v){ pos.div(v); return this; }
    public IBoid div(double v){ pos.div(v); return this; }
    public IBoid neg(){ pos.neg(); return this; }
    public IBoid rev(){ return neg(); }
    public IBoid flip(){ return neg(); }

    public IBoid zero(){ pos.zero(); return this; }
    
    public IBoid add(IVecI v, double f){ pos.add(v,f); return this; }
    public IBoid add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IBoid add(double f, IVecI v){ pos.add(f,v); return this; }
    public IBoid add(IDoubleI f, IVecI v){ pos.add(f,v); return this; }
    
    public IBoid len(IDoubleI l){ pos.len(l); return this; }
    public IBoid len(double l){ pos.len(l); return this; }
    
    public IBoid unit(){ pos.unit(); return this; }
    
    public IBoid rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IBoid rot(double angle){ pos.rot(angle); return this; }
    
    public IBoid rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IBoid rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IBoid rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IBoid rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IBoid rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IBoid rot(double centerX, double centerY, double centerZ,
		     double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    public IBoid rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IBoid rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }

    /** rotation on xy-plane; alias of rot(double) */
    public IBoid rot2(double angle){ pos.rot2(angle); return this; }
    /** rotation on xy-plane; alias of rot(IDoubleI) */
    public IBoid rot2(IDoubleI angle){ pos.rot2(angle); return this; }
    
    /** rotation on xy-plane */
    public IBoid rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IBoid rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IBoid rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    /** rotation on xy-plane towards destDir */
    public IBoid rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    /** rotation on xy-plane towards destPt */
    public IBoid rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    
    public IBoid scale(IDoubleI f){ pos.scale(f); return this; }
    public IBoid scale(double f){ pos.scale(f); return this; }
    
    public IBoid scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IBoid scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IBoid scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IBoid scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IBoid scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IBoid scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IBoid scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IBoid scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IBoid scale1d(double centerX, double centerY, double centerZ,
			 double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IBoid ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IBoid ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IBoid ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IBoid ref(double centerX, double centerY, double centerZ,
		     double planeX, double planeY, double planeZ){
	pos.ref(centerX,planeY,planeZ,planeX,planeY,planeZ); return this;
    }
    public IBoid mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IBoid mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IBoid mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IBoid mirror(double centerX, double centerY, double centerZ,
			double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    public IBoid shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IBoid shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IBoid shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IBoid shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IBoid shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IBoid shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IBoid shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IBoid shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IBoid shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IBoid shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IBoid shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IBoid shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IBoid shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IBoid shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IBoid shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IBoid shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    public IBoid translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IBoid translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IBoid translate(IVecI v){ pos.translate(v); return this; }
    
    public IBoid transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IBoid transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IBoid transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IBoid transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IBoid mv(double x, double y, double z){ return add(x,y,z); }
    public IBoid mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IBoid mv(IVecI v){ return add(v); }
    
    public IBoid cp(){ return dup(); }
    public IBoid cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IBoid cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IBoid cp(IVecI v){ return dup().add(v); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IBoid setSize(double sz){ return size(sz); }
    public IBoid size(double sz){ point.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IBoid name(String nm){ super.name(nm); return this; }
    public IBoid layer(ILayer l){ super.layer(l); return this; }
    
    public IBoid show(){ super.show(); return this; }
    public IBoid hide(){ super.hide(); return this; }
    
    public IBoid showPoint(){ super.showPoint(); return this; }
    public IBoid hidePoint(){ super.hidePoint(); return this; }
    public IBoid showGeometry(){ super.showGeometry(); return this; }
    public IBoid hideGeometry(){ super.hideGeometry(); return this; }
    
    
    public IBoid clr(IColor c){ super.clr(c); return this; }
    public IBoid clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IBoid clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IBoid clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    //public IBoid clr(Color c){ super.clr(c); return this; }
    //public IBoid clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    //public IBoid clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    //public IBoid clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IBoid clr(int gray){ super.clr(gray); return this; }
    public IBoid clr(float fgray){ super.clr(fgray); return this; }
    public IBoid clr(double dgray){ super.clr(dgray); return this; }
    public IBoid clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IBoid clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IBoid clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IBoid clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IBoid clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IBoid clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IBoid clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IBoid clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IBoid clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IBoid hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IBoid hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IBoid hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IBoid hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IBoid setColor(IColor c){ super.setColor(c); return this; }
    public IBoid setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    public IBoid setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    public IBoid setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    //public IBoid setColor(Color c){ super.setColor(c); return this; }
    //public IBoid setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    //public IBoid setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    //public IBoid setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    public IBoid setColor(int gray){ super.setColor(gray); return this; }
    public IBoid setColor(float fgray){ super.setColor(fgray); return this; }
    public IBoid setColor(double dgray){ super.setColor(dgray); return this; }
    public IBoid setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IBoid setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IBoid setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IBoid setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IBoid setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IBoid setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IBoid setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IBoid setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IBoid setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IBoid setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IBoid setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IBoid setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IBoid setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public IBoid weight(double w){ super.weight(w); return this; }
    public IBoid weight(float w){ super.weight(w); return this; }
    
}
