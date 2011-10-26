/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

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

package igeo.geo;

import java.awt.Color;
import java.util.ArrayList;

import igeo.core.*;
import igeo.gui.*;

/**
   Class of an implementation of IDynamicObject to have physical attributes of point.
   It has attributes of position, velocity, acceleration, force, and mass.
   Position is provided from outside to be linked.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IParticleDistanceLink extends IDynamicObjectBase{
    
    public IParticle particle;
    public IVecI center;
    public double distance;
    
    public IParticleDistanceLink(IParticle ptcl, IVecI cntr, IObject parent){
	super(parent);
	particle = ptcl;
	center = cntr;
	distance = center.dist(particle);
	initParticleDistanceLink();
    }
    
    public IParticleDistanceLink(IParticle ptcl, IVecI cntr){
	super();
	particle = ptcl;
	center = cntr;
	distance = center.dist(particle);
	initParticleDistanceLink();
    }
    
    public IParticleDistanceLink(IParticle ptcl, IVecI cntr, double dist, IObject parent){
	super(parent);
	particle = ptcl;
	center = cntr;
	distance = dist;
	ptcl.pos.set(ptcl.pos.diff(center).len(distance).add(center));
	if(distance<0) distance=-distance;
	initParticleDistanceLink();
    }
    
    public IParticleDistanceLink(IParticle ptcl, double dist, IVecI cntr){
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
        
    //synchronized public void interact(ArrayList<IDynamicObject> dynamics){}
    
    synchronized public void update(){
	
	// taking care of the whole update of particle here.
	IVec dir = particle.pos.diff(center);
	particle.frc.set(particle.frc.perpendicularVectorToVector(dir)); // cancelling force along dir
	particle.frc.mul(IConfig.dynamicsSpeed/particle.mass);
	particle.vel.add(particle.frc).mul(1.0-particle.friction);
	
	particle.vel.set(particle.vel.perpendicularVectorToVector(dir)); // cancelling vel along dir
	
	particle.pos.add(particle.vel.dup().mul(IConfig.dynamicsSpeed));
	
	dir = particle.pos.diff(center); // again
	
	dir.len(distance).add(center);
	
 	particle.pos.set(dir);

	//if(particle.parent!=null) particle.parent.updateGraphic();
	particle.updateTarget();
	//if(parent!=null) parent.updateGraphic();
	updateTarget();
    }
    
        
}
