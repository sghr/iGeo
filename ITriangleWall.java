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
   Infinite plane wall to bounce particle
   @author Satoru Sugihara
*/

public class ITriangleWall extends IWall{
    
    public IVecI[] pts;
    // chache
    public IVec dir1, dir2; 
    
    public ITriangleWall(IVecI pt1, IVecI pt2, IVecI pt3){
	super(pt1.nml(pt2,pt3), pt1);
	pts = new IVecI[3];
	pts[0] = pt1;
	pts[1] = pt2;
	pts[2] = pt3;
	
    }
    
    public ITriangleWall(IVecI[] pts){
	super(pts[0].nml(pts[1],pts[2]), pts[0]);
	this.pts = new IVecI[3];
	this.pts[0] = pts[0];
	this.pts[1] = pts[1];
	this.pts[2] = pts[2];
    }

    public ITriangleWall(ITriangleWall wall){
	super(wall);
	pts = new IVecI[3];
	pts[0] = wall.pts[0].dup();
	pts[1] = wall.pts[1].dup();
	pts[2] = wall.pts[2].dup();
    }
    
    
    public ITriangleWall fric(double fric){ friction=fric; return this; }
    public ITriangleWall friction(double fric){ return fric(fric); }
    
    public ITriangleWall elast(double el){ elasticity = el; return this; }
    public ITriangleWall elasticity(double el){ return elast(el); }
    
    
    /** in case somebody needs to measure distance to the wall. if it misses to intersect, it returns -1.*/
    public double distToCrossing(IParticleI particle){
	IVec isct = IVec.intersectPlaneAndLine(planeDir.get(), planePt.get(), particle.vel(), particle.pos());
	if(isInside(isct)) return isct.dist(particle.pos());
	return -1; // -1?
    }
    
    
    public IVec intersect(IParticleI particle){
	IVec isct=IVec.intersectPlaneAndLine(planeDir.get(), planePt.get(), particle.vel(), particle.pos());
	if(isInside(isct)) return isct;
	return null;
    }
    
    public IVec intersect(IVec pt1, IVec pt2){
	IVec isct = IVec.intersectPlaneAndLine(planeDir.get(), planePt.get(), pt2.dif(pt1), pt1);
	if(isInside(isct)) return isct;
	return null;
    }
    
    /** check if the point is inside the triangle */
    public boolean isInside(IVec pt){ return isInsideLocal(pt.dif(pts[0])); }
    
    /** check if the point is inside the triangle ralatively from planePt */
    public boolean isInsideLocal(IVec pt){
	if(dir1==null) dir1 = pts[1].get().dif(pts[0]);
	if(dir2==null) dir2 = pts[2].get().dif(pts[0]);
	double[] coef = pt.projectTo2Vec(dir1, dir2);
	return coef[0]>=0. && coef[0]<=1. && coef[1]>=0. && coef[1]<=1. && (coef[0]+coef[1])<=1.;
    }
    
    public boolean isCrossing(IParticleI particle){
	double vlen2=particle.vel().len2();
	if(vlen2==0) return false;
	IVec curPos = particle.pos().dup();
	IVec nextPos = curPos.dup().add(particle.vel(), IConfig.updateRate);
	return isCrossing(curPos,nextPos);
    }
    
    public boolean isCrossing(IVec pt1, IVec pt2){
	IVec d1 = pt1.dif(planePt);
	IVec d2 = pt2.dif(planePt);
	double dot1 = d1.dot(planeDir);
	double dot2 = d2.dot(planeDir);
	if(dot1 == 0 || dot1*dot2 < 0){ // intersecting (except nextPos is exactly on the plane)
	    IVec isct = IVec.intersectPlaneAndLine(planeDir.get(), planePt.get(), pt2.dif(pt1), pt1);
	    return isInside(isct);
	}
	return false;
    }
    
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
    
    public void postinteract(ArrayList<IDynamics> agents){
	//dir1 = pts[1].get().dif(pts[0]);
	//dir2 = pts[2].get().dif(pts[0]);
	
	planeDir = pts[0].get().nml(pts[1],pts[2]); // update dir in case triangle is moved.
	
	super.postinteract(agents);
	
	dir1=null;
	dir2=null;
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
	
	double vlen2=particle.vel().len2();
	
	if(vlen2>0){
	    IVec curPos = particle.pos().dup();
	    IVec nextPos = curPos.dup().add(particle.vel(), IConfig.updateRate);
	    curPos.sub(planePt);
	    nextPos.sub(planePt);
	    double curDot = curPos.dot(planeDir);
	    double nextDot = nextPos.dot(planeDir);
	    if(curDot==0 || curDot*nextDot < 0){ // intersecting (except nextPos is exactly on the plane)
		
		IVec isct = IVec.intersectPlaneAndLine(planeDir.get(),nextPos.dif(curPos),curPos);
		if(isInsideLocal(isct)){
		    
		    if(IConfig.checkAdjacentWalls){
			//double dist = dist(particle);
			double dist = distToCrossing(particle);
			for(int i=0; i<agents.size(); i++){
			    if(agents.get(i) instanceof IWall && agents.get(i)!=this){
				double dist2 = ((IWall)agents.get(i)).distToCrossing(particle);
				if(dist2 >= 0 && dist2 < dist){
				    return; // particle bounce with other
				}
			    }
			}
		    }
		    
		    if(elasticity==1.0 && friction == 0.0){ //if(elasticity>=1.0 && friction <= 0.0){
			particle.vel().ref(planeDir); // any IParticleI returns copy of internal vel?
			isct.add(planePt);
			if(IConfig.checkAdjacentWalls){
			    
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
				((ITrajectoryI)particle).addCP(isct);
			    }
			    particle.pos(nextPos.ref(planeDir).add(planePt));
			    particle.skipUpdateOnce(true); // not to add another velocity
			}
		    }
		    else{
			if(planeDirCache==null){ planeDirCache = planeDir.get().dup().unit(); }
			
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
    }
}
