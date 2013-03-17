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
   Alias class of IParticleOnCurve for backward compatibility; Use IParticleOnCurve instead.
   
   @author Satoru Sugihara
*/
public class IParticleOnCurveAgent extends IParticleOnCurve{
    
    public IParticleOnCurveAgent(ICurveI curve, double upos, double uvel){ super(curve,upos,uvel); }
    public IParticleOnCurveAgent(ICurveI curve, double upos){ super(curve,upos); }
    public IParticleOnCurveAgent(ICurveI curve){ super(curve); }
    public IParticleOnCurveAgent(ICurveI curve, double upos, double uvel, IVec pos){ super(curve,upos,uvel,pos); }
    public IParticleOnCurveAgent(ICurveI curve, double upos, IVec pos){ super(curve,upos,pos); }
    public IParticleOnCurveAgent(ICurveI curve, IVec pos){ super(curve,pos); }
    public IParticleOnCurveAgent(ICurve curve, double upos, double uvel){ super(curve,upos,uvel); }
    public IParticleOnCurveAgent(ICurve curve, double upos){ super(curve,upos); }
    public IParticleOnCurveAgent(ICurve curve){ super(curve); }
    public IParticleOnCurveAgent(ICurve curve, double upos, double uvel, IVec pos){ super(curve,upos,uvel,pos); }
    public IParticleOnCurveAgent(ICurve curve, double upos, IVec pos){ super(curve,upos,pos); }
    public IParticleOnCurveAgent(ICurve curve, IVec pos){ super(curve,pos); }
    public IParticleOnCurveAgent(IParticleOnCurveGeo p){ super(p); }
    public IParticleOnCurveAgent(IParticleOnCurve p){ super(p); }
    
    /**************************************
     * IParticleI API
     **************************************/
    
    synchronized public IParticleOnCurveAgent fix(){ super.fix(); return this; }
    synchronized public IParticleOnCurveAgent unfix(){ super.unfix(); return this; }
    synchronized public IParticleOnCurveAgent mass(double mass){ super.mass(mass); return this; }
    synchronized public IParticleOnCurveAgent position(IVecI v){ super.position(v); return this; }
    synchronized public IParticleOnCurveAgent pos(IVecI v){ super.pos(v); return this; }
    synchronized public IParticleOnCurveAgent velocity(IVecI v){ super.velocity(v); return this; }
    synchronized public IParticleOnCurveAgent vel(IVecI v){ super.vel(v); return this; }
    synchronized public IParticleOnCurveAgent force(IVecI v){ super.force(v); return this; }
    synchronized public IParticleOnCurveAgent frc(IVecI v){ particle.frc(v); return this; }
    synchronized public IParticleOnCurveAgent friction(double friction){ super.friction(friction); return this; }
    synchronized public IParticleOnCurveAgent fric(double friction){ super.fric(friction); return this; }
    synchronized public IParticleOnCurveAgent decay(double d){ super.decay(d); return this; }
    
    synchronized public IParticleOnCurveAgent push(IVecI f){ super.push(f); return this; }
    synchronized public IParticleOnCurveAgent push(double fx, double fy, double fz){ super.push(fx,fy,fz); return this; }
    synchronized public IParticleOnCurveAgent pull(IVecI f){ super.pull(f); return this; }
    synchronized public IParticleOnCurveAgent pull(double fx, double fy, double fz){ super.pull(fx,fy,fz); return this; }
    synchronized public IParticleOnCurveAgent addForce(IVecI f){ super.addForce(f); return this; }
    synchronized public IParticleOnCurveAgent addForce(double fx, double fy, double fz){ super.addForce(fx,fy,fz); return this; }
    synchronized public IParticleOnCurveAgent reset(){ super.reset(); return this; }
    synchronized public IParticleOnCurveAgent resetForce(){ super.resetForce(); return this; }
    
    synchronized public IParticleOnCurveAgent uposition(double u){ super.upos(u); return this; }
    synchronized public IParticleOnCurveAgent upos(double u){ super.upos(u); return this; }
    synchronized public IParticleOnCurveAgent uvelocity(double uv){ super.uvel(uv); return this; }
    synchronized public IParticleOnCurveAgent uvel(double uv){ super.uvel(uv); return this; }
    synchronized public IParticleOnCurveAgent uforce(double uf){ super.ufrc(uf); return this; }
    synchronized public IParticleOnCurveAgent ufrc(double uf){ super.ufrc(uf); return this; }
    synchronized public IParticleOnCurveAgent addUForce(double uforce){ super.upush(uforce); return this; }
    synchronized public IParticleOnCurveAgent resetUForce(){ super.ureset(); return this; }
    synchronized public IParticleOnCurveAgent upush(double uforce){ super.upush(uforce); return this; }
    synchronized public IParticleOnCurveAgent upull(double uforce){ super.upull(uforce); return this; }
    synchronized public IParticleOnCurveAgent ureset(){ super.ureset(); return this; }
        
    // partial methods of IDynamics  
    /** add terget object to be updated by this dynamic object. */
    public IParticleOnCurveAgent target(IObject targetObj){ super.target(targetObj); return this; }
    public IParticleOnCurveAgent removeTarget(int i){ super.removeTarget(i); return this; }
    /** remove target object. */
    public IParticleOnCurveAgent removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public IParticleOnCurveAgent x(double vx){ super.x(vx); return this; }
    public IParticleOnCurveAgent y(double vy){ super.y(vy); return this; }
    public IParticleOnCurveAgent z(double vz){ super.z(vz); return this; }
    
    public IParticleOnCurveAgent x(IDoubleI vx){ super.x(vx); return this; }
    public IParticleOnCurveAgent y(IDoubleI vy){ super.y(vy); return this; }
    public IParticleOnCurveAgent z(IDoubleI vz){ super.z(vz); return this; }
    

    public IParticleOnCurveAgent dup(){ return new IParticleOnCurveAgent(this); }
    
    public IParticleOnCurveAgent set(IVecI v){ super.set(v); return this; }
    public IParticleOnCurveAgent set(double x, double y, double z){ super.set(x,y,z); return this;}
    public IParticleOnCurveAgent set(IDoubleI x, IDoubleI y, IDoubleI z){ super.set(x,y,z); return this; }
    
    public IParticleOnCurveAgent add(double x, double y, double z){ super.add(x,y,z); return this; }
    public IParticleOnCurveAgent add(IDoubleI x, IDoubleI y, IDoubleI z){ super.add(x,y,z); return this; }    
    public IParticleOnCurveAgent add(IVecI v){ super.add(v); return this; }
    
    public IParticleOnCurveAgent sub(double x, double y, double z){ super.sub(x,y,z); return this; }
    public IParticleOnCurveAgent sub(IDoubleI x, IDoubleI y, IDoubleI z){ super.sub(x,y,z); return this; }
    public IParticleOnCurveAgent sub(IVecI v){ super.sub(v); return this; }
    public IParticleOnCurveAgent mul(IDoubleI v){ super.mul(v); return this; }
    public IParticleOnCurveAgent mul(double v){ super.mul(v); return this; }
    public IParticleOnCurveAgent div(IDoubleI v){ super.div(v); return this; }
    public IParticleOnCurveAgent div(double v){ super.div(v); return this; }
    public IParticleOnCurveAgent neg(){ super.neg(); return this; }
    public IParticleOnCurveAgent rev(){ super.rev(); return this; }
    public IParticleOnCurveAgent flip(){ super.flip(); return this; }

    public IParticleOnCurveAgent zero(){ super.zero(); return this; }
    
    public IParticleOnCurveAgent add(IVecI v, double f){ super.add(v,f); return this; }
    public IParticleOnCurveAgent add(IVecI v, IDoubleI f){ super.add(v,f); return this; }
    
    public IParticleOnCurveAgent add(double f, IVecI v){ super.add(f,v); return this; }
    public IParticleOnCurveAgent add(IDoubleI f, IVecI v){ super.add(f,v); return this; }
    
    public IParticleOnCurveAgent len(IDoubleI l){ super.len(l); return this; }
    public IParticleOnCurveAgent len(double l){ super.len(l); return this; }
    
    public IParticleOnCurveAgent unit(){ super.unit(); return this; }
    
    public IParticleOnCurveAgent rot(IDoubleI angle){ super.rot(angle); return this; }
    public IParticleOnCurveAgent rot(double angle){ super.rot(angle); return this; }
    
    public IParticleOnCurveAgent rot(IVecI axis, IDoubleI angle){ super.rot(axis,angle); return this; }
    public IParticleOnCurveAgent rot(IVecI axis, double angle){ super.rot(axis,angle); return this; }
    public IParticleOnCurveAgent rot(double axisX, double axisY, double axisZ, double angle){
	super.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnCurveAgent rot(IVecI center, IVecI axis, double angle){
	super.rot(center, axis,angle); return this;
    }
    public IParticleOnCurveAgent rot(IVecI center, IVecI axis, IDoubleI angle){
	super.rot(center, axis,angle); return this;
    }
    public IParticleOnCurveAgent rot(double centerX, double centerY, double centerZ,
			      double axisX, double axisY, double axisZ, double angle){
	super.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnCurveAgent rot(IVecI axis, IVecI destDir){ super.rot(axis,destDir); return this; }
    public IParticleOnCurveAgent rot(IVecI center, IVecI axis, IVecI destPt){
	super.rot(center,axis,destPt); return this;
    }
    
    public IParticleOnCurveAgent rot2(IDoubleI angle){ super.rot2(angle); return this; }
    public IParticleOnCurveAgent rot2(double angle){ super.rot2(angle); return this; }
    public IParticleOnCurveAgent rot2(IVecI center, double angle){ super.rot2(center,angle); return this; }
    public IParticleOnCurveAgent rot2(IVecI center, IDoubleI angle){ super.rot2(center,angle); return this; }
    public IParticleOnCurveAgent rot2(double centerX, double centerY, double angle){
	super.rot2(centerX,centerY,angle); return this;
    }
    public IParticleOnCurveAgent rot2(IVecI destDir){ super.rot2(destDir); return this; }
    public IParticleOnCurveAgent rot2(IVecI center, IVecI destPt){ super.rot2(center,destPt); return this; }
    
    public IParticleOnCurveAgent scale(IDoubleI f){ super.scale(f); return this; }
    public IParticleOnCurveAgent scale(double f){ super.scale(f); return this; }
    
    public IParticleOnCurveAgent scale(IVecI center, IDoubleI f){ super.scale(center,f); return this; }
    public IParticleOnCurveAgent scale(IVecI center, double f){ super.scale(center,f); return this; }
    public IParticleOnCurveAgent scale(double centerX, double centerY, double centerZ, double f){
	super.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleOnCurveAgent scale1d(IVecI axis, double f){ super.scale1d(axis,f); return this; }
    public IParticleOnCurveAgent scale1d(IVecI axis, IDoubleI f){ super.scale1d(axis,f); return this; }
    public IParticleOnCurveAgent scale1d(double axisX, double axisY, double axisZ, double f){
	super.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleOnCurveAgent scale1d(IVecI center, IVecI axis, double f){
	super.scale1d(center,axis,f); return this;
    }
    public IParticleOnCurveAgent scale1d(IVecI center, IVecI axis, IDoubleI f){
	super.scale1d(center,axis,f); return this;
    }
    public IParticleOnCurveAgent scale1d(double centerX, double centerY, double centerZ,
				  double axisX, double axisY, double axisZ, double f){
	super.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IParticleOnCurveAgent ref(IVecI planeDir){ super.ref(planeDir); return this; }
    public IParticleOnCurveAgent ref(double planeX, double planeY, double planeZ){
	super.ref(planeX,planeY,planeZ); return this;
    }
    public IParticleOnCurveAgent ref(IVecI center, IVecI planeDir){
	super.ref(center,planeDir); return this;
    }
    public IParticleOnCurveAgent ref(double centerX, double centerY, double centerZ,
			      double planeX, double planeY, double planeZ){
	super.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IParticleOnCurveAgent mirror(IVecI planeDir){ super.ref(planeDir); return this; }
    public IParticleOnCurveAgent mirror(double planeX, double planeY, double planeZ){
	super.ref(planeX, planeY, planeZ); return this;
    }
    public IParticleOnCurveAgent mirror(IVecI center, IVecI planeDir){
	super.ref(center,planeDir); return this;
    }
    public IParticleOnCurveAgent mirror(double centerX, double centerY, double centerZ,
				 double planeX, double planeY, double planeZ){
	super.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    public IParticleOnCurveAgent shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurveAgent shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurveAgent shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnCurveAgent shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleOnCurveAgent shearXY(double sxy, double syx){ super.shearXY(sxy,syx); return this; }
    public IParticleOnCurveAgent shearXY(IDoubleI sxy, IDoubleI syx){ super.shearXY(sxy,syx); return this; }
    public IParticleOnCurveAgent shearXY(IVecI center, double sxy, double syx){
	super.shearXY(center,sxy,syx); return this;
    }
    public IParticleOnCurveAgent shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	super.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleOnCurveAgent shearYZ(double syz, double szy){ super.shearYZ(syz,szy); return this; }
    public IParticleOnCurveAgent shearYZ(IDoubleI syz, IDoubleI szy){ super.shearYZ(syz,szy); return this; }
    public IParticleOnCurveAgent shearYZ(IVecI center, double syz, double szy){
	super.shearYZ(center,syz,szy); return this;
    }
    public IParticleOnCurveAgent shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	super.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleOnCurveAgent shearZX(double szx, double sxz){ super.shearZX(szx,sxz); return this; }
    public IParticleOnCurveAgent shearZX(IDoubleI szx, IDoubleI sxz){ super.shearZX(szx,sxz); return this; }
    public IParticleOnCurveAgent shearZX(IVecI center, double szx, double sxz){
	super.shearZX(center,szx,sxz); return this;
    }
    public IParticleOnCurveAgent shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	super.shearZX(center,szx,sxz); return this;
    }
    
    public IParticleOnCurveAgent translate(double x, double y, double z){ super.translate(x,y,z); return this; }
    public IParticleOnCurveAgent translate(IDoubleI x, IDoubleI y, IDoubleI z){ super.translate(x,y,z); return this; }
    public IParticleOnCurveAgent translate(IVecI v){ super.translate(v); return this; }
    
    public IParticleOnCurveAgent transform(IMatrix3I mat){ super.transform(mat); return this; }
    public IParticleOnCurveAgent transform(IMatrix4I mat){ super.transform(mat); return this; }
    public IParticleOnCurveAgent transform(IVecI xvec, IVecI yvec, IVecI zvec){
	super.transform(xvec,yvec,zvec); return this;
    }
    public IParticleOnCurveAgent transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	super.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IParticleOnCurveAgent mv(double x, double y, double z){ super.mv(x,y,z); return this; }
    public IParticleOnCurveAgent mv(IDoubleI x, IDoubleI y, IDoubleI z){ super.mv(x,y,z); return this; }
    public IParticleOnCurveAgent mv(IVecI v){ super.mv(v); return this; }
    
    public IParticleOnCurveAgent cp(){ return dup(); }
    public IParticleOnCurveAgent cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleOnCurveAgent cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleOnCurveAgent cp(IVecI v){ return dup().add(v); }
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IParticleOnCurveAgent setSize(double sz){ super.setSize(sz); return this; }
    public IParticleOnCurveAgent size(double sz){ super.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IParticleOnCurveAgent name(String nm){ super.name(nm); return this; }
    public IParticleOnCurveAgent layer(ILayer l){ super.layer(l); return this; }
    
    public IParticleOnCurveAgent show(){ super.show(); return this; }
    public IParticleOnCurveAgent hide(){ super.hide(); return this; }
    
    
    public IParticleOnCurveAgent clr(IColor c){ super.clr(c); return this; }
    public IParticleOnCurveAgent clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurveAgent clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurveAgent clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurveAgent clr(Color c){ super.clr(c); return this; }
    public IParticleOnCurveAgent clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurveAgent clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public IParticleOnCurveAgent clr(Color c, double alpha){ super.clr(c,alpha); return this; }
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
