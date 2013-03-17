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
   Class of an implementation of IDynamics to limit particles to be spacified direction from a center.
   
   @author Satoru Sugihara
*/
public class IParticleDistanceLink extends IDynamicsBase{
    
    public IParticleGeo particle;
    public IVecI center;
    public double distance;
    
    public IParticleDistanceLink(IParticleGeo ptcl, IVecI cntr, IObject parent){
	super(parent);
	particle = ptcl;
	center = cntr;
	distance = center.dist(particle);
	initParticleDistanceLink();
    }
    
    public IParticleDistanceLink(IParticleGeo ptcl, IVecI cntr){
	super();
	particle = ptcl;
	center = cntr;
	distance = center.dist(particle);
	initParticleDistanceLink();
    }
    
    public IParticleDistanceLink(IParticleGeo ptcl, IVecI cntr, double dist, IObject parent){
	super(parent);
	particle = ptcl;
	center = cntr;
	distance = dist;
	ptcl.pos.set(ptcl.pos.diff(center).len(distance).add(center));
	if(distance<0) distance=-distance;
	initParticleDistanceLink();
    }
    
    public IParticleDistanceLink(IParticleGeo ptcl, double dist, IVecI cntr){
	super();
	particle = ptcl;
	center = cntr;
	distance = dist;
	ptcl.pos.set(ptcl.pos.diff(center).len(distance).add(center));
	if(distance<0) distance=-distance;
	initParticleDistanceLink();
    }
    
    public void initParticleDistanceLink(){
	particle.fix(); // to take control of location of particle;
    }
        
    //synchronized public void interact(ArrayList<IDynamics> dynamics){}
    
    synchronized public void update(){
	
	// taking care of the whole update of particle here.
	IVec dir = particle.pos.diff(center);
	particle.frc.set(particle.frc.perpendicularVecToLine(dir).neg()); // cancelling force along dir // need to flip? (20120215)
	particle.frc.mul(IConfig.updateRate/particle.mass);
	particle.vel.add(particle.frc).mul(1.0-particle.friction);
	
	particle.vel.set(particle.vel.perpendicularVecToLine(dir).neg()); // cancelling vel along dir // need to flip? (20120215)
	
	particle.pos.add(particle.vel.dup().mul(IConfig.updateRate));
	
	dir = particle.pos.diff(center); // again
	
	dir.len(distance).add(center);
	
 	particle.pos.set(dir);

	//if(particle.parent!=null) particle.parent.updateGraphic();
	particle.updateTarget();
	//if(parent!=null) parent.updateGraphic();
	//updateTarget();// moved to IDynamicsBase.postUpdate()
    }
    
        
}
