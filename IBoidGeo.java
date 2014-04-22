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
   Class of boid algorithm (swarm algorithm).
   
   @author Satoru Sugihara
*/
public class IBoidGeo extends IParticleGeo implements IBoidI{
    public double cohesionDist = 100;
    public double cohesionRatio = 5;
    public double cohesionLimit = -1; // no limit
    public double separationDist = 50;
    public double separationRatio = 5;
    public double separationLimit = -1; // no limit
    public double alignmentDist = 20;
    public double alignmentRatio = 5;
    public double alignmentLimit = -1; // no limit
    
    IVec cohesionForce = new IVec();
    IVec separationForce = new IVec();
    IVec alignmentForce = new IVec();

    public ArrayList<Class<? extends IBoidI>> targetClasses;
    

    
    public IBoidGeo(){ super(); initBoid(); }
    public IBoidGeo(IVecI pt){ super(pt); initBoid(); }
    public IBoidGeo(IVec pt){ super(pt); initBoid(); }
    public IBoidGeo(IVecI pt, IVecI vel){ super(pt,vel); initBoid(); }
    public IBoidGeo(IVec pt, IVec vel){ super(pt,vel); initBoid(); }
    public IBoidGeo(double x, double y, double z){ super(x,y,z); initBoid(); }
    public IBoidGeo(double x, double y, double z, double vx, double vy, double vz){
	super(x,y,z,vx,vy,vz); initBoid();
    }
    public IBoidGeo(IBoidGeo b){
	super(b);
	cohesionDist = b.cohesionDist;	
	cohesionRatio = b.cohesionRatio;
	cohesionLimit = b.cohesionLimit;
	separationDist = b.separationDist;
	separationRatio = b.separationRatio;
	separationLimit = b.separationLimit;
	alignmentDist = b.alignmentDist;
	alignmentRatio = b.alignmentRatio;
	alignmentLimit = b.alignmentLimit;
	initBoid();
    }
    
    public void initBoid(){
	cohesionForce = new IVec();
        separationForce = new IVec();
	alignmentForce = new IVec();
    }
    
    public double cohDist(){ return cohesionDist; }
    public double cohesionDist(){ return cohDist(); }
    public IBoidGeo cohDist(double dist){ cohesionDist=dist; return this; }
    public IBoidGeo cohesionDist(double dist){ return cohDist(dist); }
    public double cohRatio(){ return cohesionRatio; }
    public double cohesionRatio(){ return cohRatio(); }
    public IBoidGeo cohRatio(double ratio){ cohesionRatio=ratio; return this; }
    public IBoidGeo cohesionRatio(double ratio){ return cohRatio(ratio); }
    public double cohLimit(){ return cohesionLimit; }
    public double cohesionLimit(){ return cohLimit(); }
    public IBoidGeo cohLimit(double limit){ cohesionLimit = limit; return this; }
    public IBoidGeo cohesionLimit(double limit){ return cohLimit(limit); }
    
    public IBoidGeo coh(double ratio, double dist){ cohesionRatio=ratio; cohesionDist=dist; return this; }
    public IBoidGeo cohesion(double ratio, double dist){ return coh(ratio,dist); }
    
    public double sepDist(){ return separationDist; }
    public double separationDist(){ return sepDist(); }
    public IBoidGeo sepDist(double dist){ separationDist=dist; return this; }
    public IBoidGeo separationDist(double dist){ return sepDist(dist); }
    public double sepRatio(){ return separationRatio; }
    public double separationRatio(){ return sepRatio(); }
    public IBoidGeo sepRatio(double ratio){ separationRatio=ratio; return this; }
    public IBoidGeo separationRatio(double ratio){ return sepRatio(ratio); }
    public double sepLimit(){ return separationLimit; }
    public double separationLimit(){ return sepLimit(); }
    public IBoidGeo sepLimit(double limit){ separationLimit = limit; return this; }
    public IBoidGeo separationLimit(double limit){ return sepLimit(limit); }
    
    public IBoidGeo sep(double ratio, double dist){ separationRatio=ratio; separationDist=dist; return this; }
    public IBoidGeo separation(double ratio, double dist){ return sep(ratio,dist); }
    
    public double aliDist(){ return alignmentDist; }
    public double alignmentDist(){ return aliDist(); }
    public IBoidGeo aliDist(double dist){ alignmentDist=dist; return this; }
    public IBoidGeo alignmentDist(double dist){ return aliDist(dist); }
    public double aliRatio(){ return alignmentRatio; }
    public double alignmentRatio(){ return aliRatio(); }
    public IBoidGeo aliRatio(double ratio){ alignmentRatio=ratio; return this; }
    public IBoidGeo alignmentRatio(double ratio){ return aliRatio(ratio); }
    public double aliLimit(){ return alignmentLimit; }
    public double alignmentLimit(){ return aliLimit(); }
    public IBoidGeo aliLimit(double limit){ alignmentLimit = limit; return this; }
    public IBoidGeo alignmentLimit(double limit){ return aliLimit(limit); }
    
    public IBoidGeo ali(double ratio, double dist){ alignmentRatio=ratio; alignmentDist=dist; return this; }
    public IBoidGeo alignment(double ratio, double dist){ return ali(ratio,dist); }
    
    public IBoidGeo parameter(double cohRatio, double cohDist, 
			      double sepRatio, double sepDist, 
			      double aliRatio, double aliDist){
	return param(cohRatio,cohDist,sepRatio,sepDist,aliRatio,aliDist); 
    }
    
    public IBoidGeo param(double cohRatio, double cohDist, 
			  double sepRatio, double sepDist, 
			  double aliRatio, double aliDist){
	cohesionRatio = cohRatio;
	cohesionDist = cohDist;
	separationRatio = sepRatio;
	separationDist = sepDist;
	alignmentRatio = aliRatio;
	alignmentDist = aliDist;
	return this;
    }




    /** make the field applicable only to the specified target class */
    public IBoidGeo targetClass(Class<? extends IBoidI> targetClass){
	targetClasses = new ArrayList<Class<? extends IBoidI>>();
	targetClasses.add(targetClass);
	return this;
    }
    /** alias */
    public IBoidGeo target(Class<? extends IBoidI> targetClass){ return targetClass(targetClass); }
    
    
    /** make the field applicable only to the specified target classes */
    public IBoidGeo targetClass(Class<? extends IBoidI>... targets){
	targetClasses = new ArrayList<Class<? extends IBoidI>>();
	for(Class<? extends IBoidI> tgt : targets){ targetClasses.add(tgt); }
	return this;
    }
    /** alias */
    public IBoidGeo target(Class<? extends IBoidI>... targets){ return targetClass(targets); }
    
    public boolean isTargetClass(Object obj){
	for(int i=0; i<targetClasses.size(); i++){
	    if(targetClasses.get(i).isInstance(obj)) return true;
	}
	return false;
    }
    
    /** alias */
    public boolean isTarget(Object obj){ return isTargetClass(obj); }



    
    synchronized public void interact(ArrayList<IDynamics> dynamics){
	
	if(cohesionRatio==0 && separationRatio==0 && alignmentRatio==0 ||
	   cohesionDist<=0 && separationDist<=0 && alignmentDist<=0 ) return; // skip // added 20120905
	
	
	//super.interact(dynamics); // for other local interaction
	int cohesionCount = 0;
	int separationCount = 0;
	int alignmentCount = 0;
	
	cohesionForce.zero(); // reset
	separationForce.zero(); // reset
	alignmentForce.zero(); // reset
	
	IDynamics dy=null;
	for(int i=0; i<dynamics.size(); i++){
	    
	    // what happens if IBoidGeo is grandchild of IAgent?
	    // (boid in local dynamics of IAgent which is also local dynamics of IAgent)
	    dy = dynamics.get(i);
	    if( ( (targetClasses==null && dy instanceof IBoidI) || targetClasses!=null && isTargetClass(dy) )
		&& dy != this && dy != parent ){
		
		IBoidI b = (IBoidI)dy;
		IVec dif = b.pos().dif(pos());
		double dist = dif.len();
		// cohere
		if(dist < cohesionDist){
		    cohesionForce.add(dif);
		    cohesionCount++;
		}
		// separate
		if(dist < separationDist){
		    if(dist==0){
			if(vel().len()>0) separationForce.add(vel().dup().len(separationDist));
			else  separationForce.add(new IVec(separationDist,0,0));
		    }
		    else separationForce.add( dif.len(dist-separationDist) );
		    separationCount++;
		    //separationForce.sub(dif); // too simple
		} // content of dif is changed
		// align
		if(dist < alignmentDist){
		    alignmentForce.add(b.vel()); // velocity
		    alignmentCount++;
		}
	    }
	}
	
	if(cohesionCount>0){
	    cohesionForce.mul(cohesionRatio/cohesionCount);
	    if(cohesionLimit >= 0 && cohesionForce.len() > cohesionLimit)
		cohesionForce.len(cohesionLimit);
	    push(cohesionForce);
	}
	if(separationCount>0){
	    separationForce.mul(separationRatio/separationCount);
	    //separationForce.mul(separationRatio);
	    if(separationLimit >= 0 && separationForce.len() > separationLimit)
		separationForce.len(separationLimit);
	    push(separationForce);
	}
	if(alignmentCount>0){
	    // averagbe velocity minus this velocity
	    alignmentForce.div(alignmentCount).sub(vel()).mul(alignmentRatio);
	    if(alignmentLimit >= 0 && alignmentForce.len() > alignmentLimit)
		alignmentForce.len(alignmentLimit);
	    push(alignmentForce);
	}
	/*
	boolean interactOverridden=false;
	// to take care of possible definition of interact(IDynamics) in a child class.
	try{
	    interactOverridden = 
	    getClass().getMethod("interact", IDynamics.class).getDeclaringClass() != IAgent.class;
	}catch(NoSuchMethodException e){}
	
	if(interactOverridden){
	    for(int i=0; i<dynamics.size(); i++){
		if(dynamics.get(i) != this) interact(dynamics.get(i));
	    }
	}
	*/
    }
    
    
    /**************************************
     * IParticleI API
     **************************************/
    
    public IBoidGeo fix(){ super.fix(); return this; }
    public IBoidGeo unfix(){ super.unfix(); return this; }
    public IBoidGeo skipUpdateOnce(boolean f){ super.skipUpdateOnce(f); return this; }
    public IBoidGeo mass(double mass){ super.mass(mass); return this; }
    public IBoidGeo position(IVecI v){ super.position(v); return this; }
    public IBoidGeo pos(IVecI v){ super.pos(v); return this; }
    public IBoidGeo velocity(IVecI v){ super.velocity(v); return this; }
    public IBoidGeo vel(IVecI v){ super.vel(v); return this; }
    public IBoidGeo force(IVecI v){ super.force(v); return this; }
    public IBoidGeo frc(IVecI v){ super.frc(v); return this; }
    public IBoidGeo friction(double friction){ super.friction(friction); return this; }
    public IBoidGeo fric(double friction){ super.fric(friction); return this; }
    public IBoidGeo decay(double d){ return fric(d); }
    
    public IBoidGeo push(IVecI f){ super.push(f); return this; }
    public IBoidGeo push(double fx, double fy, double fz){ super.push(fx,fy,fz); return this; }
    public IBoidGeo pull(IVecI f){ super.pull(f); return this; }
    public IBoidGeo pull(double fx, double fy, double fz){ super.pull(fx,fy,fz); return this; }
    public IBoidGeo addForce(IVecI f){ super.addForce(f); return this; }
    public IBoidGeo addForce(double fx, double fy, double fz){ super.addForce(fx,fy,fz); return this; }
    public IBoidGeo reset(){ super.reset(); return this; }
    public IBoidGeo resetForce(){ super.resetForce(); return this; }
    
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    public IBoidGeo x(double vx){ pos.x(vx); return this; }
    public IBoidGeo y(double vy){ pos.y(vy); return this; }
    public IBoidGeo z(double vz){ pos.z(vz); return this; }
    
    public IBoidGeo x(IDoubleI vx){ pos.x(vx); return this; }
    public IBoidGeo y(IDoubleI vy){ pos.y(vy); return this; }
    public IBoidGeo z(IDoubleI vz){ pos.z(vz); return this; }
    
    /** setting x component by x component of input vector*/
    public IBoidGeo x(IVecI v){ pos.x(v); return this; }
    /** setting y component by y component of input vector*/
    public IBoidGeo y(IVecI v){ pos.y(v); return this; }
    /** setting z component by z component of input vector*/
    public IBoidGeo z(IVecI v){ pos.z(v); return this; }
    
    /** setting x component by x component of input vector*/
    public IBoidGeo x(IVec2I v){ pos.x(v); return this; }
    /** setting y component by y component of input vector*/
    public IBoidGeo y(IVec2I v){ pos.y(v); return this; }
    
    
    
    public IBoidGeo dup(){ return new IBoidGeo(this); }
    
    public IBoidGeo set(IVecI v){ pos.set(v); return this; }
    public IBoidGeo set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IBoidGeo set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IBoidGeo add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IBoidGeo add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IBoidGeo add(IVecI v){ pos.add(v); return this; }
    
    public IBoidGeo sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IBoidGeo sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IBoidGeo sub(IVecI v){ pos.sub(v); return this; }
    public IBoidGeo mul(IDoubleI v){ pos.mul(v); return this; }
    public IBoidGeo mul(double v){ pos.mul(v); return this; }
    public IBoidGeo div(IDoubleI v){ pos.div(v); return this; }
    public IBoidGeo div(double v){ pos.div(v); return this; }
    public IBoidGeo neg(){ pos.neg(); return this; }
    public IBoidGeo rev(){ return neg(); }
    public IBoidGeo flip(){ return neg(); }

    public IBoidGeo zero(){ pos.zero(); return this; }
    
    public IBoidGeo add(IVecI v, double f){ pos.add(v,f); return this; }
    public IBoidGeo add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IBoidGeo add(double f, IVecI v){ pos.add(f,v); return this; }
    public IBoidGeo add(IDoubleI f, IVecI v){ pos.add(f,v); return this; }
    
    public IBoidGeo len(IDoubleI l){ pos.len(l); return this; }
    public IBoidGeo len(double l){ pos.len(l); return this; }
    
    public IBoidGeo unit(){ pos.unit(); return this; }
    
    public IBoidGeo rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IBoidGeo rot(double angle){ pos.rot(angle); return this; }
    
    public IBoidGeo rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IBoidGeo rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IBoidGeo rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IBoidGeo rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IBoidGeo rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IBoidGeo rot(double centerX, double centerY, double centerZ,
		     double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    public IBoidGeo rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IBoidGeo rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }

    /** rotation on xy-plane; alias of rot(double) */
    public IBoidGeo rot2(double angle){ pos.rot2(angle); return this; }
    /** rotation on xy-plane; alias of rot(IDoubleI) */
    public IBoidGeo rot2(IDoubleI angle){ pos.rot2(angle); return this; }
    
    /** rotation on xy-plane */
    public IBoidGeo rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IBoidGeo rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IBoidGeo rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    /** rotation on xy-plane towards destDir */
    public IBoidGeo rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    /** rotation on xy-plane towards destPt */
    public IBoidGeo rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    
    public IBoidGeo scale(IDoubleI f){ pos.scale(f); return this; }
    public IBoidGeo scale(double f){ pos.scale(f); return this; }
    
    public IBoidGeo scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IBoidGeo scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IBoidGeo scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IBoidGeo scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IBoidGeo scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IBoidGeo scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IBoidGeo scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IBoidGeo scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IBoidGeo scale1d(double centerX, double centerY, double centerZ,
			 double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IBoidGeo ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IBoidGeo ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IBoidGeo ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IBoidGeo ref(double centerX, double centerY, double centerZ,
		     double planeX, double planeY, double planeZ){
	pos.ref(centerX,planeY,planeZ,planeX,planeY,planeZ); return this;
    }
    public IBoidGeo mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IBoidGeo mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IBoidGeo mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IBoidGeo mirror(double centerX, double centerY, double centerZ,
			double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    public IBoidGeo shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IBoidGeo shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IBoidGeo shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IBoidGeo shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IBoidGeo shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IBoidGeo shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IBoidGeo shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IBoidGeo shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IBoidGeo shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IBoidGeo shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IBoidGeo shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IBoidGeo shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IBoidGeo shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IBoidGeo shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IBoidGeo shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IBoidGeo shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    public IBoidGeo translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IBoidGeo translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IBoidGeo translate(IVecI v){ pos.translate(v); return this; }
    
    public IBoidGeo transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IBoidGeo transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IBoidGeo transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IBoidGeo transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IBoidGeo mv(double x, double y, double z){ return add(x,y,z); }
    public IBoidGeo mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IBoidGeo mv(IVecI v){ return add(v); }
    
    public IBoidGeo cp(){ return dup(); }
    public IBoidGeo cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IBoidGeo cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IBoidGeo cp(IVecI v){ return dup().add(v); }
    
}
