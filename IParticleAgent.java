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
   Alias class of IParticle for backward compatibility. Use IParticle instead.
   
   @author Satoru Sugihara
*/
public class IParticleAgent extends IParticle{
    
    public IParticleAgent(){ super(); }
    public IParticleAgent(double x, double y, double z){ super(x,y,z); }
    public IParticleAgent(IVec p){ super(p); }
    public IParticleAgent(IVecI p){ super(p); }
    public IParticleAgent(IParticleGeo ptcl){ super(ptcl); }
    public IParticleAgent(IParticle p){ super(p); }
    public IParticleAgent(double x, double y, double z, double vx, double vy, double vz){ super(x,y,z,vx,vy,vz); }
    public IParticleAgent(IVec p, IVec vel){ super(p,vel); }
    public IParticleAgent(IVecI p, IVecI vel){ super(p,vel); }
    public IParticleAgent(IParticleGeo ptcl, IVecI vel){ super(ptcl,vel); }
    public IParticleAgent(IParticle p, IVecI vel){ super(p,vel); }
    
    
    /**************************************
     * IParticleI API
     **************************************/
    
    synchronized public IParticleAgent fix(){ super.fix(); return this; }
    synchronized public IParticleAgent unfix(){ super.unfix(); return this; }
    synchronized public IParticleAgent mass(double mass){ super.mass(mass); return this; }
    synchronized public IParticleAgent position(IVecI v){ super.position(v); return this; }
    synchronized public IParticleAgent pos(IVecI v){ super.pos(v); return this; }
    synchronized public IParticleAgent velocity(IVecI v){ super.velocity(v); return this; }
    synchronized public IParticleAgent vel(IVecI v){ super.vel(v); return this; }
    synchronized public IParticleAgent force(IVecI v){ super.force(v); return this; }
    synchronized public IParticleAgent frc(IVecI v){ super.frc(v); return this; }
    synchronized public IParticleAgent friction(double friction){ super.friction(friction); return this; }
    synchronized public IParticleAgent fric(double friction){ super.fric(friction); return this; }
    synchronized public IParticleAgent decay(double d){ super.decay(d); return this; }
    
    synchronized public IParticleAgent push(IVecI f){ super.push(f); return this; }
    synchronized public IParticleAgent push(double fx, double fy, double fz){ super.push(fx,fy,fz); return this; }
    synchronized public IParticleAgent pull(IVecI f){ super.pull(f); return this; }
    synchronized public IParticleAgent pull(double fx, double fy, double fz){ super.pull(fx,fy,fz); return this; }
    synchronized public IParticleAgent addForce(IVecI f){ super.addForce(f); return this; }
    synchronized public IParticleAgent addForce(double fx, double fy, double fz){ super.addForce(fx,fy,fz); return this; }
    
    synchronized public IParticleAgent reset(){ super.reset(); return this; }
    synchronized public IParticleAgent resetForce(){ super.resetForce(); return this; }
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public IParticleAgent x(double vx){ super.x(vx); return this; }
    public IParticleAgent y(double vy){ super.y(vy); return this; }
    public IParticleAgent z(double vz){ super.z(vz); return this; }
    
    public IParticleAgent x(IDoubleI vx){ super.x(vx); return this; }
    public IParticleAgent y(IDoubleI vy){ super.y(vy); return this; }
    public IParticleAgent z(IDoubleI vz){ super.z(vz); return this; }
        
    public IParticleAgent dup(){ return new IParticleAgent(this); }
    
    public IParticleAgent set(IVecI v){ super.set(v); return this; }
    public IParticleAgent set(double x, double y, double z){ super.set(x,y,z); return this;}
    public IParticleAgent set(IDoubleI x, IDoubleI y, IDoubleI z){ super.set(x,y,z); return this; }
    public IParticleAgent add(double x, double y, double z){ super.add(x,y,z); return this; }
    public IParticleAgent add(IDoubleI x, IDoubleI y, IDoubleI z){ super.add(x,y,z); return this; }    
    public IParticleAgent add(IVecI v){ super.add(v); return this; }
    public IParticleAgent sub(double x, double y, double z){ super.sub(x,y,z); return this; }
    public IParticleAgent sub(IDoubleI x, IDoubleI y, IDoubleI z){ super.sub(x,y,z); return this; }
    public IParticleAgent sub(IVecI v){ super.sub(v); return this; }
    public IParticleAgent mul(IDoubleI v){ super.mul(v); return this; }
    public IParticleAgent mul(double v){ super.mul(v); return this; }
    public IParticleAgent div(IDoubleI v){ super.div(v); return this; }
    public IParticleAgent div(double v){ super.div(v); return this; }
    public IParticleAgent neg(){ super.neg(); return this; }
    public IParticleAgent rev(){ super.rev(); return this; }
    public IParticleAgent flip(){ super.flip(); return this; }
    public IParticleAgent zero(){ super.zero(); return this; }
    
    public IParticleAgent add(IVecI v, double f){ super.add(v,f); return this; }
    public IParticleAgent add(IVecI v, IDoubleI f){ super.add(v,f); return this; }
    public IParticleAgent add(double f, IVecI v){ super.add(f,v); return this; }
    public IParticleAgent add(IDoubleI f, IVecI v){ super.add(f,v); return this; }
    
    public IParticleAgent len(IDoubleI l){ super.len(l); return this; }
    public IParticleAgent len(double l){ super.len(l); return this; }
    public IParticleAgent unit(){ super.unit(); return this; }
    public IParticleAgent rot(IDoubleI angle){ super.rot(angle); return this; }
    public IParticleAgent rot(double angle){ super.rot(angle); return this; }
    public IParticleAgent rot(IVecI axis, IDoubleI angle){ super.rot(axis,angle); return this; }
    public IParticleAgent rot(IVecI axis, double angle){ super.rot(axis,angle); return this; }
    public IParticleAgent rot(double axisX, double axisY, double axisZ, double angle){
	super.rot(axisX,axisY,axisZ,angle); return this;
    }
    public IParticleAgent rot(IVecI center, IVecI axis, double angle){
	super.rot(center, axis,angle); return this;
    }
    public IParticleAgent rot(IVecI center, IVecI axis, IDoubleI angle){
	super.rot(center, axis,angle); return this;
    }
    public IParticleAgent rot(double centerX, double centerY, double centerZ,
			      double axisX, double axisY, double axisZ, double angle){
	super.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    public IParticleAgent rot(IVecI axis, IVecI destDir){ super.rot(axis,destDir); return this; }
    public IParticleAgent rot(IVecI center, IVecI axis, IVecI destPt){
	super.rot(center,axis,destPt); return this;
    }
    public IParticleAgent rot2(IDoubleI angle){ super.rot2(angle); return this; }
    public IParticleAgent rot2(double angle){ super.rot2(angle); return this; }
    public IParticleAgent rot2(IVecI center, double angle){ super.rot2(center,angle); return this; }
    public IParticleAgent rot2(IVecI center, IDoubleI angle){ super.rot2(center,angle); return this; }
    public IParticleAgent rot2(double centerX, double centerY, double angle){
	super.rot2(centerX,centerY,angle); return this;
    }
    public IParticleAgent rot2(IVecI destDir){ super.rot2(destDir); return this; }
    public IParticleAgent rot2(IVecI center, IVecI destPt){ super.rot2(center,destPt); return this; }
    
    public IParticleAgent scale(IDoubleI f){ super.scale(f); return this; }
    public IParticleAgent scale(double f){ super.scale(f); return this; }
    
    public IParticleAgent scale(IVecI center, IDoubleI f){ super.scale(center,f); return this; }
    public IParticleAgent scale(IVecI center, double f){ super.scale(center,f); return this; }
    public IParticleAgent scale(double centerX, double centerY, double centerZ, double f){
	super.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleAgent scale1d(IVecI axis, double f){ super.scale1d(axis,f); return this; }
    public IParticleAgent scale1d(IVecI axis, IDoubleI f){ super.scale1d(axis,f); return this; }
    public IParticleAgent scale1d(double axisX, double axisY, double axisZ, double f){
	super.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleAgent scale1d(IVecI center, IVecI axis, double f){
	super.scale1d(center,axis,f); return this;
    }
    public IParticleAgent scale1d(IVecI center, IVecI axis, IDoubleI f){
	super.scale1d(center,axis,f); return this;
    }
    public IParticleAgent scale1d(double centerX, double centerY, double centerZ,
				  double axisX, double axisY, double axisZ, double f){
	super.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IParticleAgent ref(IVecI planeDir){ super.ref(planeDir); return this; }
    public IParticleAgent ref(double planeX, double planeY, double planeZ){
	super.ref(planeX,planeY,planeZ); return this;
    }
    public IParticleAgent ref(IVecI center, IVecI planeDir){
	super.ref(center,planeDir); return this;
    }
    public IParticleAgent ref(double centerX, double centerY, double centerZ,
			      double planeX, double planeY, double planeZ){
	super.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IParticleAgent mirror(IVecI planeDir){ super.ref(planeDir); return this; }
    public IParticleAgent mirror(double planeX, double planeY, double planeZ){
	super.ref(planeX, planeY, planeZ); return this;
    }
    public IParticleAgent mirror(IVecI center, IVecI planeDir){
	super.ref(center,planeDir); return this;
    }
    public IParticleAgent mirror(double centerX, double centerY, double centerZ,
				 double planeX, double planeY, double planeZ){
	super.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    public IParticleAgent shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleAgent shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleAgent shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleAgent shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleAgent shearXY(double sxy, double syx){ super.shearXY(sxy,syx); return this; }
    public IParticleAgent shearXY(IDoubleI sxy, IDoubleI syx){ super.shearXY(sxy,syx); return this; }
    public IParticleAgent shearXY(IVecI center, double sxy, double syx){
	super.shearXY(center,sxy,syx); return this;
    }
    public IParticleAgent shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	super.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleAgent shearYZ(double syz, double szy){ super.shearYZ(syz,szy); return this; }
    public IParticleAgent shearYZ(IDoubleI syz, IDoubleI szy){ super.shearYZ(syz,szy); return this; }
    public IParticleAgent shearYZ(IVecI center, double syz, double szy){
	super.shearYZ(center,syz,szy); return this;
    }
    public IParticleAgent shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	super.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleAgent shearZX(double szx, double sxz){ super.shearZX(szx,sxz); return this; }
    public IParticleAgent shearZX(IDoubleI szx, IDoubleI sxz){ super.shearZX(szx,sxz); return this; }
    public IParticleAgent shearZX(IVecI center, double szx, double sxz){
	super.shearZX(center,szx,sxz); return this;
    }
    public IParticleAgent shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	super.shearZX(center,szx,sxz); return this;
    }
    
    public IParticleAgent translate(double x, double y, double z){ super.translate(x,y,z); return this; }
    public IParticleAgent translate(IDoubleI x, IDoubleI y, IDoubleI z){ super.translate(x,y,z); return this; }
    public IParticleAgent translate(IVecI v){ super.translate(v); return this; }
    
    public IParticleAgent transform(IMatrix3I mat){ super.transform(mat); return this; }
    public IParticleAgent transform(IMatrix4I mat){ super.transform(mat); return this; }
    public IParticleAgent transform(IVecI xvec, IVecI yvec, IVecI zvec){
	super.transform(xvec,yvec,zvec); return this;
    }
    public IParticleAgent transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	super.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IParticleAgent mv(double x, double y, double z){ super.mv(x,y,z); return this; }
    public IParticleAgent mv(IDoubleI x, IDoubleI y, IDoubleI z){ super.mv(x,y,z); return this; }
    public IParticleAgent mv(IVecI v){ super.mv(v); return this; }
    public IParticleAgent cp(){ return dup(); }
    public IParticleAgent cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleAgent cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleAgent cp(IVecI v){ return dup().add(v); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IParticleAgent setSize(double sz){ super.setSize(sz); return this; }
    public IParticleAgent size(double sz){ super.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IParticleAgent name(String nm){ super.name(nm); return this; }
    public IParticleAgent layer(ILayer l){ super.layer(l); return this; }
    
    public IParticleAgent show(){ super.show(); return this; }
    public IParticleAgent hide(){ super.hide(); return this; }
    
    
    public IParticleAgent clr(IColor c){ super.clr(c); return this; }
    public IParticleAgent clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleAgent clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IParticleAgent clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IParticleAgent clr(Color c){ super.clr(c); return this; }
    public IParticleAgent clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleAgent clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public IParticleAgent clr(Color c, double alpha){ super.clr(c,alpha); return this; }
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
    
    public IParticleAgent setColor(IColor c){ super.setColor(c); return this; }
    public IParticleAgent setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticleAgent setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    public IParticleAgent setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    public IParticleAgent setColor(Color c){ super.setColor(c); return this; }
    public IParticleAgent setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IParticleAgent setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    public IParticleAgent setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
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
    
    
    public IParticleAgent weight(double w){ super.weight(w); return this; }
    public IParticleAgent weight(float w){ super.weight(w); return this; }

    
    // partial methods of IDynamics
    /** add terget object to be updated by this dynamic object. */
    public IParticleAgent target(IObject targetObj){ super.target(targetObj); return this; }
    /** remove target object. */
    public IParticleAgent  removeTarget(int i){ super.removeTarget(i); return this; }
    /** remove target object. */
    public IParticleAgent  removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
}
