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

import java.util.ArrayList;

/**
   Class of a particle which sits and moves on a surface. 
   
   @author Satoru Sugihara
*/
public class IParticleOnSurfaceGeo extends IParticleGeo implements IParticleOnSurfaceI{
    
    public ISurfaceI surface;
    public IVec2 uvpos;
    public IVec2 uvvel;
    public IVec2 uvfrc;
    
    public IVec utan;
    public IVec vtan;

    public boolean ufixed=false;
    public boolean vfixed=false;
    
    public IParticleOnSurfaceGeo(ISurfaceI srf){ this(srf,0,0); }
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v){
	super(srf.pt(u,v).get()); surface=srf; uvpos = new IVec2(u,v);
    }
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, double uvel, double vvel){
	this(srf,u,v); uvvel = new IVec2(uvel,vvel);
    }
    public IParticleOnSurfaceGeo(ISurfaceI srf, IVec pos){ this(srf,0,0,pos); }
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, IVec pos){
	this(srf,u,v,0,0,pos);
    }
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, double uvel, double vvel, IVec pos){
	super(pos);
	pos.set(srf.pt(u,v).get());
	surface = srf;
	uvpos = new IVec2(u,v);
	uvvel = new IVec2(uvel,vvel);
	uvfrc = new IVec2();
    }
    
    public IParticleOnSurfaceGeo(ISurfaceI srf, IObject parent){
	this(srf,0,0,parent);
    }
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, IObject parent){
	super(srf.pt(u,v).get(),parent);
	surface=srf;
	uvpos = new IVec2(u,v);
    }
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, double uvel, double vvel, IObject parent){
	this(srf,u,v,parent); uvvel=new IVec2(uvel,vvel);
    }
    
    public IParticleOnSurfaceGeo(ISurfaceI srf, IVec pos, IObject parent){ this(srf,0,0,pos,parent); }
    
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, IVec pos, IObject parent){
	this(srf,u,v,0,0,pos,parent);
    }
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, double uvel, double vvel, IVec pos, IObject parent){
	super(pos,parent);
	pos.set(srf.pt(u,v).get());
	surface=srf;
	uvpos = new IVec2(u,v);
	uvvel=new IVec2(uvel,vvel);
	uvfrc=new IVec2();
    }
    
    
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, IPoint pt){ this(srf,u,v,pt.pos,pt); }
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, double uvel, double vvel, IPoint pt){ this(srf,u,v,uvel,vvel,pt.pos,pt); }
    public IParticleOnSurfaceGeo(ISurfaceI srf, IPoint pt){ this(srf,0,0,pt.pos,pt); }
    public IParticleOnSurfaceGeo(ISurfaceI srf, IPointR pt){ this(srf,0,0,pt.pos.get(),pt); }
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, IPointR pt){ this(srf,u,v,pt.pos.get(),pt); }
    public IParticleOnSurfaceGeo(ISurfaceI srf, double u, double v, double uvel, double vvel, IPointR pt){ this(srf,u,v,uvel,vvel,pt.pos.get(),pt); }
    
    
    public IParticleOnSurfaceGeo(ISurface srf){ this(srf,0,0,srf); }
    public IParticleOnSurfaceGeo(ISurface srf, double u,double v){ this(srf,u,v,srf); }
    public IParticleOnSurfaceGeo(ISurface srf, double u, double v, double uvel, double vvel){ this(srf,u,v,uvel,vvel,srf); }
    public IParticleOnSurfaceGeo(ISurface srf, IVec pos){ this(srf,0,0,pos,srf); }
    public IParticleOnSurfaceGeo(ISurface srf, double u, double v, IVec pos){ this(srf,u,v,pos,srf); }
    public IParticleOnSurfaceGeo(ISurface srf, double u, double v, double uvel, double vvel, IVec pos){ this(srf,u,v,uvel,vvel,pos,srf); }
    
    public IParticleOnSurfaceGeo(IParticleOnSurfaceGeo p){
	this(p.surface, p.uvpos.x, p.uvpos.y, p.pos.dup(), p.parent());
    }
    public IParticleOnSurfaceGeo(IParticleOnSurfaceGeo p, IVec pos){
	this(p.surface, p.uvpos.x, p.uvpos.y, pos, p.parent());
    }
    public IParticleOnSurfaceGeo(IParticleOnSurfaceGeo p, IObject parent){
	this(p.surface, p.uvpos.x, p.uvpos.y, p.pos.dup(), parent);
    }
    public IParticleOnSurfaceGeo(IParticleOnSurfaceGeo p, IVec pos, IObject parent){
	this(p.surface, p.uvpos.x, p.uvpos.y, pos, parent);
    }
    
    public IParticleOnSurfaceGeo dup(){ return new IParticleOnSurfaceGeo(this); }
    
    public ISurfaceI surface(){ return surface; }
    
    
    
    synchronized public IParticleOnSurfaceGeo fix(){ super.fix(); return this; }
    synchronized public IParticleOnSurfaceGeo unfix(){ super.unfix(); return this; }
    
    
    synchronized public IParticleOnSurfaceGeo skipUpdateOnce(boolean f){ super.skipUpdateOnce(f); return this; }
    
    
    synchronized public IParticleOnSurfaceGeo mass(double mass){ super.mass(mass); return this; }
    synchronized public IParticleOnSurfaceGeo position(IVecI v){ super.pos(v); return this; }
    synchronized public IParticleOnSurfaceGeo pos(IVecI v){ super.pos(v); return this; }
    synchronized public IParticleOnSurfaceGeo velocity(IVecI v){ super.vel(v); return this; }
    synchronized public IParticleOnSurfaceGeo vel(IVecI v){ super.vel(v); return this; }
    synchronized public IParticleOnSurfaceGeo force(IVecI v){ super.frc(v); return this; }
    synchronized public IParticleOnSurfaceGeo frc(IVecI v){ super.frc(v); return this; }
    synchronized public IParticleOnSurfaceGeo friction(double friction){ super.fric(friction); return this; }
    synchronized public IParticleOnSurfaceGeo fric(double friction){ super.fric(friction); return this; }
    /* alias of friction */
    synchronized public IParticleOnSurfaceGeo decay(double d){ return fric(d); }
    
    synchronized public IParticleOnSurfaceGeo push(IVecI f){ super.push(f); return this; }
    synchronized public IParticleOnSurfaceGeo pull(IVecI f){ super.pull(f); return this; }
    synchronized public IParticleOnSurfaceGeo addForce(IVecI f){ super.addForce(f); return this; }
    synchronized public IParticleOnSurfaceGeo reset(){ super.reset(); return this; }
    synchronized public IParticleOnSurfaceGeo resetForce(){ super.resetForce(); return this; }
    
    synchronized public IParticleOnSurfaceGeo uv(double u, double v){ uvpos.set(u,v); return this; }
    synchronized public IVec2 uv(){ return uvpos.cp(); }
    synchronized public IParticleOnSurfaceGeo uvvel(double uvel, double vvel){ uvvel.set(uvel,vvel); return this; }
    synchronized public IVec2 uvvel(){ return uvvel.cp(); }
    synchronized public IParticleOnSurfaceGeo uvfrc(double ufrc, double vfrc){ uvfrc.set(ufrc,vfrc); return this; }
    synchronized public IVec2 uvfrc(){ return uvfrc.cp(); }
    
    synchronized public IParticleOnSurfaceGeo addUVForce(double ufrc, double vfrc){ return uvpush(ufrc,vfrc); }
    synchronized public IParticleOnSurfaceGeo resetUVForce(){ return uvreset(); }
    
    synchronized public IParticleOnSurfaceGeo uvpush(double ufrc,double vfrc){ uvfrc.add(ufrc,vfrc); return this; }
    synchronized public IParticleOnSurfaceGeo uvpull(double ufrc,double vfrc){ uvfrc.sub(ufrc,vfrc); return this; }
    synchronized public IParticleOnSurfaceGeo uvreset(){ uvfrc.zero(); return this; }

    public IParticleOnSurfaceGeo fixU(){ ufixed=true; return this; }
    public IParticleOnSurfaceGeo fixV(){ vfixed=true; return this; }
    public IParticleOnSurfaceGeo unfixU(){ ufixed=false; return this; }
    public IParticleOnSurfaceGeo unfixV(){ vfixed=false; return this; }
    public boolean uFixed(){ return ufixed; }
    public boolean vFixed(){ return vfixed; }

    
        
    synchronized public void interact(ArrayList<IDynamics> dynamics){}
    
    
    /** update of velocity is done in preupdate (updated 2012/08/26) */
    synchronized public void preupdate(){
	if(fixed || surface==null) return;
	
	utan = surface.utan(uvpos).get();
	vtan = surface.vtan(uvpos).get();
	
	double[] coef = frc.projectTo2Vec(utan,vtan);
	if(coef!=null){
	    
	    uvfrc.add(coef[0], coef[1]);
	    uvvel.add(uvfrc.mul(IConfig.updateRate/mass)).mul(1.0-friction);
	}
	
	if(ufixed) uvvel.x=0;
	if(vfixed) uvvel.y=0;
	
	// reset
        if( !(IConfig.enablePostupdate && IConfig.clearParticleForceInPostupdate)){
            // this is done here again because force in update cannot be reflected (updated 2014/03/10)
	    frc.zero();
	    uvfrc.zero(); // also reset uvfrc
        }
    }
    
    /** update of velocity is done in preupdate and update of position is done in update() (updated 2012/08/26) */
    synchronized public void update(){
	if(skipUpdateOnce){ skipUpdateOnce=false; return; }
	if(fixed || surface==null) return;
	
	// out of range of u 0.0-1.0
	if( (uvpos.x + uvvel.x*IConfig.updateRate) < 0.0 ){
	    if( surface.isUClosed() ){ // cyclic
		uvpos.x += uvvel.x*IConfig.updateRate;
		uvpos.x -= Math.floor(uvpos.x); // fit within 0.0 - 1.0.
	    }
	    else{ uvpos.x=0.0; uvvel.x=0.0; }
	}
	else if( (uvpos.x + uvvel.x*IConfig.updateRate) > 1.0 ){
	    if( surface.isUClosed() ){ // cyclic
		uvpos.x += uvvel.x*IConfig.updateRate;
		uvpos.x -= Math.floor(uvpos.x); // fit within 0.0 - 1.0.
	    }
	    else{ uvpos.x=1.0; uvvel.x=0.0; }
	}
	else{ uvpos.x += uvvel.x*IConfig.updateRate; }
	
	// out of range of v 0.0-1.0
	if( (uvpos.y + uvvel.y*IConfig.updateRate) < 0.0 ){
	    if( surface.isVClosed() ){ // cyclic
		uvpos.y += uvvel.y*IConfig.updateRate;
		uvpos.y -= Math.floor(uvpos.y); // fit within 0.0 - 1.0.
	    }
	    else{ uvpos.y=0.0; uvvel.y=0.0; }
	}
	else if( (uvpos.y + uvvel.y*IConfig.updateRate) > 1.0 ){
	    if( surface.isVClosed() ){ // cyclic
		uvpos.y += uvvel.y*IConfig.updateRate;
		uvpos.y -= Math.floor(uvpos.y); // fit within 0.0 - 1.0.
	    }
	    else{ uvpos.y=1.0; uvvel.y=0.0; }
	}
	else{ uvpos.y += uvvel.y*IConfig.updateRate; }
	
	pos.set(surface.pt(uvpos));
	
    }
    
    synchronized public void postupdate(){
        if(IConfig.enablePostupdate && IConfig.clearParticleForceInPostupdate){
            frc.zero();
	    uvfrc.zero(); // also reset ufrc
        }
    }
    
    
    /****************************************************************************
     * implementation of IVecI
     ***************************************************************************/

    public IParticleOnSurfaceGeo x(double vx){ pos.x(vx); return this; }
    public IParticleOnSurfaceGeo y(double vy){ pos.y(vy); return this; }
    public IParticleOnSurfaceGeo z(double vz){ pos.z(vz); return this; }
    
    public IParticleOnSurfaceGeo x(IDoubleI vx){ pos.x(vx); return this; }
    public IParticleOnSurfaceGeo y(IDoubleI vy){ pos.y(vy); return this; }
    public IParticleOnSurfaceGeo z(IDoubleI vz){ pos.z(vz); return this; }
    
    public IParticleOnSurfaceGeo x(IVecI vx){ pos.x(vx); return this; }
    public IParticleOnSurfaceGeo y(IVecI vy){ pos.y(vy); return this; }
    public IParticleOnSurfaceGeo z(IVecI vz){ pos.z(vz); return this; }
    
    public IParticleOnSurfaceGeo x(IVec2I vx){ pos.x(vx); return this; }
    public IParticleOnSurfaceGeo y(IVec2I vy){ pos.y(vy); return this; }
    
    public IParticleOnSurfaceGeo set(IVecI v){ pos.set(v); return this; }
    public IParticleOnSurfaceGeo set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticleOnSurfaceGeo set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }

    public IParticleOnSurfaceGeo add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticleOnSurfaceGeo add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticleOnSurfaceGeo add(IVecI v){ pos.add(v); return this; }
    
    public IParticleOnSurfaceGeo sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticleOnSurfaceGeo sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticleOnSurfaceGeo sub(IVecI v){ pos.sub(v); return this; }
    public IParticleOnSurfaceGeo mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticleOnSurfaceGeo mul(double v){ pos.mul(v); return this; }
    public IParticleOnSurfaceGeo div(IDoubleI v){ pos.div(v); return this; }
    public IParticleOnSurfaceGeo div(double v){ pos.div(v); return this; }
    public IParticleOnSurfaceGeo neg(){ pos.neg(); return this; }
    public IParticleOnSurfaceGeo rev(){ return neg(); }
    public IParticleOnSurfaceGeo flip(){ return neg(); }
    public IParticleOnSurfaceGeo zero(){ pos.zero(); return this; }
    
    
    public IParticleOnSurfaceGeo add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticleOnSurfaceGeo add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    public IParticleOnSurfaceGeo add(double f, IVecI v){ pos.add(f,v); return this; }
    public IParticleOnSurfaceGeo add(IDoubleI f, IVecI v){ pos.add(f,v); return this; }
    
    
    public IParticleOnSurfaceGeo unit(){ pos.unit(); return this; }
    
    public IParticleOnSurfaceGeo rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticleOnSurfaceGeo rot(double angle){ pos.rot(angle); return this; }
    public IParticleOnSurfaceGeo rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticleOnSurfaceGeo rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticleOnSurfaceGeo rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticleOnSurfaceGeo rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnSurfaceGeo rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticleOnSurfaceGeo rot(double centerX, double centerY, double centerZ,
				double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX, centerY, centerZ, axisX, axisY, axisZ, angle); return this;
    }
    
    /** Rotate to destination direction vector. */
    public IParticleOnSurfaceGeo rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    /** Rotate to destination point location. */
    public IParticleOnSurfaceGeo rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    /** rotation on xy-plane; alias of rot(double) */
    public IParticleOnSurfaceGeo rot2(double angle){ pos.rot2(angle); return this; }
    /** rotation on xy-plane; alias of rot(IDoubleI) */
    public IParticleOnSurfaceGeo rot2(IDoubleI angle){ pos.rot2(angle); return this; }
    
    /** rotation on xy-plane */
    public IParticleOnSurfaceGeo rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IParticleOnSurfaceGeo rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IParticleOnSurfaceGeo rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    /** rotation on xy-plane towards destDir */
    public IParticleOnSurfaceGeo rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    /** rotation on xy-plane towards destPt */
    public IParticleOnSurfaceGeo rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    public IParticleOnSurfaceGeo scale(IDoubleI f){ pos.scale(f); return this; }
    /** alias of mul */
    public IParticleOnSurfaceGeo scale(double f){ pos.scale(f); return this; }
    
    public IParticleOnSurfaceGeo scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticleOnSurfaceGeo scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticleOnSurfaceGeo scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticleOnSurfaceGeo scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticleOnSurfaceGeo scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticleOnSurfaceGeo scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticleOnSurfaceGeo scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnSurfaceGeo scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticleOnSurfaceGeo scale1d(double centerX, double centerY, double centerZ,
				    double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnSurfaceGeo ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnSurfaceGeo ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnSurfaceGeo ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnSurfaceGeo ref(double centerX, double centerY, double centerZ,
				double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnSurfaceGeo mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnSurfaceGeo mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnSurfaceGeo mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticleOnSurfaceGeo mirror(double centerX, double centerY, double centerZ,
				   double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    /** shear operation */
    public IParticleOnSurfaceGeo shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnSurfaceGeo shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnSurfaceGeo shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticleOnSurfaceGeo shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticleOnSurfaceGeo shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnSurfaceGeo shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticleOnSurfaceGeo shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticleOnSurfaceGeo shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticleOnSurfaceGeo shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnSurfaceGeo shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticleOnSurfaceGeo shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticleOnSurfaceGeo shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticleOnSurfaceGeo shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnSurfaceGeo shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticleOnSurfaceGeo shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticleOnSurfaceGeo shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public IParticleOnSurfaceGeo translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticleOnSurfaceGeo translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticleOnSurfaceGeo translate(IVecI v){ pos.translate(v); return this; }
    
    
    public IParticleOnSurfaceGeo transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticleOnSurfaceGeo transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticleOnSurfaceGeo transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticleOnSurfaceGeo transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /** mv() is alias of add() */
    public IParticleOnSurfaceGeo mv(double x, double y, double z){ return add(x,y,z); }
    public IParticleOnSurfaceGeo mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticleOnSurfaceGeo mv(IVecI v){ return add(v); }
    
    /** cp() is alias of dup() */ 
    public IParticleOnSurfaceGeo cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IParticleOnSurfaceGeo cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticleOnSurfaceGeo cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticleOnSurfaceGeo cp(IVecI v){ return dup().add(v); }
    
        
}
