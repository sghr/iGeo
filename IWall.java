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

import java.lang.reflect.*;
import java.util.ArrayList;

/**
   Infinite plane wall to bounce particle
   @author Satoru Sugihara
*/

public class IWall extends IAgent{
    
    public ArrayList<Class<? extends IParticleI>> targetClasses;
    
    public IWall targetClass(Class<? extends IParticleI> targetClass){
	targetClasses = new ArrayList<Class<? extends IParticleI>>();
	targetClasses.add(targetClass);
	return this;
    }
    public IWall targetClass(Class<? extends IParticleI>... targets){
	targetClasses = new ArrayList<Class<? extends IParticleI>>();
	for(Class<? extends IParticleI> tgt : targets){ targetClasses.add(tgt); }
	return this;
    }
    public boolean isTargetClass(Object obj){
	for(int i=0; i<targetClasses.size(); i++){
	    if(targetClasses.get(i).isInstance(obj)) return true;
	}
	return false;
    }
    
    
    public IVecI planeDir;
    public IVecI planePt;
    
    public IVec planeDirCache;

    /** coefficent for bouncing. when elasticity = 1.0, it bounce back with same velocity and when
	elasticity = 0.0, it doesn't bounce but just stop at the wall */
    public double elasticity=1.0;
    /** coefficent for tangent part of bouncing. when friction is zero, the tangent part of bounced velocity
	doesn't change. if friction is 1.0, the tangent part of bounced velocity becomes zero */
    public double friction = 0.0;

    /** A boolean option to force all particles behind the plane (opposite direction of plane normal) to be moved to be on the wall */
    public boolean forceToMoveToFront = false;
    
    
    public IWall(IVecI planeDir, IVecI planePt){
	this.planeDir=planeDir;
	this.planePt = planePt;
    }
    
    public IWall(IWall wall){
	this.planeDir = wall.planeDir;
	this.planePt = wall.planePt;
	
	elasticity = wall.elasticity;
	friction = wall.friction;
	
	if(wall.targetClasses!=null){
	    targetClasses = new ArrayList<Class<? extends IParticleI>>();
	    for(int i=0; i<wall.targetClasses.size(); i++){
		targetClasses.add(wall.targetClasses.get(i));
	    }
	}
    }
    
    
    public IWall fric(double fric){ friction=fric; return this; }
    public double fric(){ return friction; }
    public IWall friction(double fric){ return fric(fric); }
    public double friction(){ return fric(); }
    
    public IWall elast(double el){ elasticity = el; return this; }
    public double elast(){ return elasticity; }
    public IWall elasticity(double el){ return elast(el); }
    public double elasticity(){ return elast(); }
    
    public IWall forceToMoveToFront(boolean flag){
	forceToMoveToFront=flag;
	return this;
    }
    
    
    /** in case somebody needs to measure distance to the wall. */
    //public double dist(IVecI pt){ return pt.get().distToPlane(planeDir,planePt); }
    
    
    /** in case somebody needs to measure distance to the wall. */
    public double distToCrossing(IParticleI particle){
	IVec isct = IVec.intersectPlaneAndLine(planeDir.get(), planePt.get(), particle.vel(), particle.pos());
	return isct.dist(particle.pos());
    }
    
    public IVec intersect(IVec pt1, IVec pt2){
	return IVec.intersectPlaneAndLine(planeDir.get(), planePt.get(), pt2.dif(pt1), pt1);
    }
    
    public boolean isCrossing(IParticleI particle){
	double vlen2=particle.vel().len2();
	if(vlen2==0) return false;
	
	IVec curPos = particle.pos().dup();
	IVec nextPos = curPos.dup().add(particle.vel(), IConfig.updateRate);
	return isCrossing(curPos,nextPos);
	//curPos.sub(planePt);
	//nextPos.sub(planePt);
	//double curDot = curPos.dot(planeDir);
	//double nextDot = nextPos.dot(planeDir);
	//return curDot==0 || curDot*nextDot < 0; // intersecting (except nextPos is exactly on the plane)
    }
    
    public boolean isCrossing(IVec pt1, IVec pt2){
	IVec d1 = pt1.dif(planePt);
	IVec d2 = pt2.dif(planePt);
	double dot1 = d1.dot(planeDir);
	double dot2 = d2.dot(planeDir);
	return dot1 == 0 || dot1*dot2 < 0; // intersecting (except nextPos is exactly on the plane)
    }
    
    
    //public void interact(ArrayList<IDynamics> agents){
    // postinteract is executed after preupdate if IConfig is set right and IParticle already finish updating velocity
    public void postinteract(ArrayList<IDynamics> agents){
	//planeDirCache = planeDir.get().dup().unit();
	for(int i=0; i<agents.size(); i++){
	    if(targetClasses==null && agents.get(i) instanceof IParticleI ||
	       targetClasses!=null && isTargetClass(agents.get(i))){
		bounce((IParticleI)agents.get(i), agents);
	    }
	}
	if(planeDirCache!=null) planeDirCache=null;
    }
    
    /*
    public void bounce(IParticleI particle, ArrayList<IDynamics> agents){
	//check if there's any other close wall within reach of the next particle update
	double vlen2=particle.vel().len2();
	if(vlen2>0){
	    IVec curPos = particle.pos().dup();
	    IVec nextPos = curPos.dup().add(particle.vel(), IConfig.updateRate);
	    curPos.sub(planePt);
	    nextPos.sub(planePt);
	    double curDot = curPos.dot(planeDir);
	    double nextDot = nextPos.dot(planeDir);
	    if(curDot==0 || curDot*nextDot < 0){ // intersecting (except nextPos is exactly on the plane)
		//double dist = dist(particle);
		double dist = distToCrossing(particle);
		for(int i=0; i<agents.size(); i++){
		    if(agents.get(i) instanceof IWall && agents.get(i)!=this){
			if(((IWall)agents.get(i)).distToCrossing(particle) < dist) return; // particle bounce with other
		    }
		}
		bounce(particle);
	    }
	}
    }
    */
    
    public IVec closerIntersection(IVec pt1, IVec pt2, ArrayList<IDynamics> agents){
	for(int i=0; i<agents.size(); i++){
	    if(agents.get(i) instanceof IWall && agents.get(i)!=this){
		IWall wall = (IWall)agents.get(i);
		if(wall.isCrossing(pt1, pt2)){
		    pt2 = wall.intersect(pt1, pt2);
		}
	    }
	}
	return pt2;
    }
    
    
    /**
       Calculate bouncing behavior of particle.
       With IConfig.checkAdjacentWalls=true, it checks other walls if the particle could collide into
       others within the same time frame and find which one to collide first.
       When bouncing, a particle doesn't necesarily stop at the intersection point on the wall because
       within one time frame, the particle moves distance of particle.vel() * IConfig.updateRate and
       IWall move the corresponding distance after bouncing. So if a particle is fast, user might not
       see the moment the particle is touching the wall. Instead, if the particle inherets ITrajectoryI,
       IWall inserts an intersection point into the trajectory curve.
    */
    public void bounce(IParticleI particle, ArrayList<IDynamics> agents){

	
	if(forceToMoveToFront){
	    IVec curPos = particle.pos().dup();
	    if(curPos.dif(planePt).dot(planeDir) < 0){ // particle is on the back side of the plane
		curPos.projectToPlane(planeDir, planeDir, planePt);
		particle.pos(curPos);
	    }
	}
	
	double vlen2=particle.vel().len2();
	if(vlen2>0){
	    IVec curPos = particle.pos().dup();
	    IVec nextPos = curPos.dup().add(particle.vel(), IConfig.updateRate);
	    curPos.sub(planePt);
	    nextPos.sub(planePt);
	    double curDot = curPos.dot(planeDir);
	    double nextDot = nextPos.dot(planeDir);
	    if(curDot==0 || curDot*nextDot < 0){ // intersecting (except nextPos is exactly on the plane)
		if(IConfig.checkAdjacentWalls){
		    //double dist = dist(particle);
		    double dist = distToCrossing(particle);
		    for(int i=0; i<agents.size(); i++){
			if(agents.get(i) instanceof IWall && agents.get(i)!=this){
			    if(((IWall)agents.get(i)).distToCrossing(particle) < dist){
				return; // particle bounce with other
			    }
			}
		    }
		}
		
		if(elasticity==1.0 && friction == 0.0){ //if(elasticity>=1.0 && friction <= 0.0){
		    particle.vel().ref(planeDir); // any IParticleI returns copy of internal vel?
		    if(IConfig.checkAdjacentWalls){
			IVec isct = IVec.intersectPlaneAndLine(planeDir.get(),nextPos.dif(curPos),curPos).add(planePt);
			if(IConfig.insertBouncePointInTrajectory && particle instanceof ITrajectoryI){
			    ((ITrajectoryI)particle).addCP(isct);
			}
			IVec pos2 = nextPos.ref(planeDir).add(planePt);
			pos2 = closerIntersection(isct, pos2, agents);
			particle.pos(pos2);
			particle.skipUpdateOnce(true); // not to add another velocity
		    }
		    else{ // simplest case
			if(IConfig.insertBouncePointInTrajectory && particle instanceof ITrajectoryI){
			    IVec isct = IVec.intersectPlaneAndLine(planeDir.get(),nextPos.dif(curPos),curPos).add(planePt);
			    ((ITrajectoryI)particle).addCP(isct);
			}
			particle.pos(nextPos.ref(planeDir).add(planePt));
			particle.skipUpdateOnce(true); // not to add another velocity
		    }
		}
		else{
		    if(planeDirCache==null){ planeDirCache = planeDir.get().dup().unit(); }
		    IVec isct = IVec.intersectPlaneAndLine(planeDirCache, nextPos.dif(curPos), curPos);
		    double locationRatio = Math.sqrt( nextPos.dist2(isct) / nextPos.dist2(curPos) );
		    
		    IVec nmlVel= planeDirCache.dup().mul(-planeDirCache.dot(particle.vel()));
		    IVec tanVel = particle.vel().dup().add(nmlVel);
		    
		    nmlVel.mul(elasticity);
		    if(nmlVel.len2()==0){ // if normal vector is zero, add normal to shift particle away from wall
			nmlVel.set(planeDirCache).mul(IConfig.tolerance/IConfig.updateRate);
			if(planeDirCache.dot(particle.vel())>0) nmlVel.neg();
		    }
		    tanVel.mul(1.-friction);
		    
		    if(IConfig.checkAdjacentWalls){
			nmlVel.add(tanVel); // next velocity
			particle.vel(nmlVel);
			isct.add(planePt);
			
			if(IConfig.insertBouncePointInTrajectory && particle instanceof ITrajectoryI){
			    ((ITrajectoryI)particle).addCP(isct);
			}
			IVec pos2 = isct.dup().add(nmlVel, IConfig.updateRate*locationRatio);
			pos2 = closerIntersection(isct, pos2, agents);
			particle.pos(pos2);
			particle.skipUpdateOnce(true); // not to add another velocity
		    }
		    else{
			isct.add(planePt);
			if(IConfig.insertBouncePointInTrajectory && particle instanceof ITrajectoryI){
			    ((ITrajectoryI)particle).addCP(isct.dup());
			}
			nmlVel.add(tanVel); // next velocity
			particle.vel(nmlVel);
			particle.pos(isct.add(nmlVel, IConfig.updateRate*locationRatio));
			//particle.pos(curPos.add(planePt).add(nmlVel, IConfig.updateRate));
			particle.skipUpdateOnce(true); // not to add another velocity
		    }
		}
	    }
	}
    }
    
    
    /*
    public void bounce(IParticleI particle, ArrayList<IDynamics> agents){
	double vlen2=particle.vel().len2();
	if(vlen2>0){
	    IVec curPos = particle.pos().dup();
	    IVec nextPos = curPos.dup().add(particle.vel(), IConfig.updateRate);
	    curPos.sub(planePt);
	    nextPos.sub(planePt);
	    double curDot = curPos.dot(planeDir);
	    double nextDot = nextPos.dot(planeDir);
	    if(curDot==0 || curDot*nextDot < 0){ // intersecting (except nextPos is exactly on the plane)
		if(IConfig.checkAdjacentWalls){
		    //double dist = dist(particle);
		    double dist = distToCrossing(particle);
		    for(int i=0; i<agents.size(); i++){
			if(agents.get(i) instanceof IWall && agents.get(i)!=this){
			    if(((IWall)agents.get(i)).distToCrossing(particle) < dist){
				return; // particle bounce with other
			    }
			}
		    }
		}
		
		//curPos.dot(planeDir)*nextPos.dot(planeDir) <= 0 ){ // intersecting
		//if(elasticity>=1.0 && friction <= 0.0){
		if(elasticity==1.0 && friction == 0.0){
		    particle.vel().ref(planeDir); // any IParticleI returns copy of internal vel?
		    
		    if(IConfig.forceStayOnWallOnce){
			// it puts particle at the intersect location but if it's exactly there, wall cannot tell which side to bounce. So it's moving the amount of tolerance
			
			IVec isct = IVec.intersectPlaneAndLine(planeDir.get(),nextPos.sub(curPos),curPos).add(planePt);
			
			if(IConfig.checkAdjacentWalls){
			    // this could stuck when the entrance angle is very low
			    // this should move to tangent direction with full amount at least
			    
			    IVec pos2 = isct.dup().add(particle.vel(),IConfig.tolerance/Math.sqrt(vlen2));
			    pos2 = closerIntersection(isct, pos2, agents);
			    particle.pos(pos2);
			    particle.skipUpdateOnce(true); // not to add another velocity
			}
			else{
			    // this could stuck when the entrance angle is very low
			    // this should move to tangent direction with full amount at least
			    
			    particle.pos(isct.add(particle.vel(),IConfig.tolerance/Math.sqrt(vlen2)));
			    particle.skipUpdateOnce(true); // not to add another velocity
			}
		    }
		    else{
			if(IConfig.checkAdjacentWalls){
			    IVec isct = IVec.intersectPlaneAndLine(planeDir.get(),nextPos.sub(curPos),curPos).add(planePt);
			    IVec pos2 = nextPos.ref(planeDir).add(planePt);
			    pos2 = closerIntersection(isct, pos2, agents);
			    particle.pos(pos2);
			    particle.skipUpdateOnce(true); // not to add another velocity
			}
			else{ // simplest case
			    particle.pos(nextPos.ref(planeDir).add(planePt));
			    particle.skipUpdateOnce(true); // not to add another velocity
			}
		    }
		}
		else{
		    if(planeDirCache==null){ planeDirCache = planeDir.get().dup().unit(); }
		    IVec isct = IVec.intersectPlaneAndLine(planeDirCache, nextPos.dif(curPos), curPos);
		    double locationRatio = Math.sqrt( nextPos.dist2(isct) / nextPos.dist2(curPos) );
		    IVec nmlVel= planeDirCache.dup().mul(-planeDirCache.dot(particle.vel()));
		    IVec tanVel = particle.vel().dup().add(nmlVel);
		    
		    nmlVel.mul(elasticity);
		    if(nmlVel.len2()==0){ // if normal vector is zero, add normal to shift particle away from wall
			nmlVel.set(planeDirCache).mul(IConfig.tolerance/IConfig.updateRate);
			if(planeDirCache.dot(particle.vel())>0) nmlVel.neg();
		    }
		    tanVel.mul(1.-friction);
		    
		    if(IConfig.forceStayOnWallOnce){
			double nextVLen2 = nmlVel.len2();
			if(nextVLen2>0){
			    
			    if(IConfig.checkAdjacentWalls){
				isct.add(planePt);
				IVec pos2 = isct.add(nmlVel,IConfig.tolerance/Math.sqrt(nextVLen2)).add(tanVel,IConfig.updateRate);
				pos2 = closerIntersection(isct, pos2, agents);
				particle.pos(pos2);
				particle.skipUpdateOnce(true); // not to add another velocity
			    }
			    else{
				particle.pos(isct.add(planePt).add(nmlVel,IConfig.tolerance/Math.sqrt(nextVLen2)).add(tanVel,IConfig.updateRate));
				
				nmlVel.add(tanVel); // next velocity
				particle.vel(nmlVel);
				particle.skipUpdateOnce(true); // not to add another velocity
			    }
			}
			else{ // move to normal // does this ever happen? only when digital precision lost?
			    nmlVel.add(tanVel); // next velocity
			    if(IConfig.checkAdjacentWalls){
				isct.add(planePt);
				if(planeDirCache.dot(particle.vel())>0){
				    particle.vel(nmlVel);
				    
				    IVec pos2 = isct.add(planeDirCache,-IConfig.tolerance);
				    pos2 = closerIntersection(isct, pos2, agents);
				    particle.pos(pos2);
				    particle.skipUpdateOnce(true); // not to add another velocity
				}
				else{
				    particle.vel(nmlVel);
				    
				    IVec pos2 = isct.add(planeDirCache,IConfig.tolerance);
				    pos2 = closerIntersection(isct, pos2, agents);
				    particle.pos(pos2);
				    particle.skipUpdateOnce(true); // not to add another velocity
				}
			    }
			    else{
				if(planeDirCache.dot(particle.vel())>0){
				    particle.vel(nmlVel);
				    particle.pos(isct.add(planePt).add(planeDirCache,-IConfig.tolerance));
				    particle.skipUpdateOnce(true); // not to add another velocity
				}
				else{
				    particle.vel(nmlVel);
				    particle.pos(isct.add(planePt).add(planeDirCache,IConfig.tolerance));
				    particle.skipUpdateOnce(true); // not to add another velocity
				}
			    }
			}
		    }
		    else{
			if(IConfig.checkAdjacentWalls){
			    nmlVel.add(tanVel); // next velocity
			    particle.vel(nmlVel);
			    isct.add(planePt);
			    
			    IVec pos2 = isct.add(nmlVel, IConfig.updateRate*locationRatio);
			    pos2 = closerIntersection(isct, pos2, agents);
			    particle.pos(pos2);
			    particle.skipUpdateOnce(true); // not to add another velocity
			}
			else{
			    nmlVel.add(tanVel); // next velocity
			    particle.vel(nmlVel);
			    particle.pos(isct.add(planePt).add(nmlVel, IConfig.updateRate*locationRatio));
			    //particle.pos(curPos.add(planePt).add(nmlVel, IConfig.updateRate));
			    particle.skipUpdateOnce(true); // not to add another velocity
			}
		    }
		}
	    }
	}
    }
    */
    


    /**************************************
     * methods of IObject
     *************************************/
    
    public IWall name(String nm){ super.name(nm); return this; }
    public IWall layer(ILayer l){ super.layer(l); return this; }
    
    public IWall hide(){ super.hide(); return this; }
    public IWall show(){ super.show(); return this; }
    
}
