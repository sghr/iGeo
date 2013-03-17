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
   Class of an implementation of IDynamics to limit particles to be spacified direction from a center.
   
   @author Satoru Sugihara
*/
public class IParticleDirectionLink extends IDynamicsBase{
    
    public ArrayList<IParticleGeo> particles;
    public IVecI center;
    
    public IVec vel;
    
    public IParticleDirectionLink(ArrayList<IParticleGeo> ptcls, IVecI cntr, IObject parent){
	super(parent);
	particles = ptcls;
	center = cntr;
	initParticleDirectionLink();
    }
    
    public IParticleDirectionLink(IParticleGeo[] ptcls, IVecI cntr, IObject parent){
	super(parent);
	particles = new ArrayList<IParticleGeo>();
	for(int i=0; i<ptcls.length; i++) particles.add(ptcls[i]);
	center = cntr;
	initParticleDirectionLink();
    }
    
    public IParticleDirectionLink(IVecI cntr, IObject parent){
	super(parent);
	particles = new ArrayList<IParticleGeo>();
	center = cntr;
	initParticleDirectionLink();
    }
    
    public IParticleDirectionLink(ArrayList<IParticleGeo> ptcls, IVecI cntr){
	super();
	particles = ptcls;
	center = cntr;
	initParticleDirectionLink();
    }
    
    public IParticleDirectionLink(IParticleGeo[] ptcls, IVecI cntr){
	super();
	particles = new ArrayList<IParticleGeo>();
	for(int i=0; i<ptcls.length; i++) particles.add(ptcls[i]);
	center = cntr;
	initParticleDirectionLink();
    }
    
    public IParticleDirectionLink(IVecI cntr){
	super();
	particles = new ArrayList<IParticleGeo>();
	center = cntr;
	initParticleDirectionLink();
    }
    
    public void initParticleDirectionLink(){
	// to take control of location of particle;
	for(int i=0; i<particles.size(); i++) particles.get(i).fix(); 
    }

    public void add(IParticleGeo p){
	p.fix(); // to take control of location of particle;
	particles.add(p);
    }
    
    //synchronized public void interact(ArrayList<IDynamicObject> dynamics){}
    
    synchronized public void update(){
	
	IVec moment = new IVec();
	double inertia = 0;
	double[] r2 = new double[particles.size()];
	IVec[] dirs = new IVec[particles.size()];
	IVec angularVec = new IVec();
	
	for(int i=0; i<particles.size(); i++){
	    IParticleGeo p = particles.get(i);
	    dirs[i] = p.pos.diff(center);
	    moment.add(dirs[i].cross(p.frc));
	    r2[i] = dirs[i].len2();
	    inertia += p.mass*r2[i];
	    if(r2[i]>IConfig.tolerance) angularVec.add(dirs[i].cross(p.vel).div(r2[i]));
	}
	
	//moment.div(inertia);
	angularVec.div(particles.size()); // average
	angularVec.add(moment.mul(IConfig.updateRate/inertia));
	
	
	IVec avrgDir=new IVec();
	for(int i=0; i<particles.size(); i++){
	    IParticleGeo p = particles.get(i);
	    
	    p.frc.projectToVec(dirs[i]);
	    p.frc.mul(IConfig.updateRate/p.mass); // only radial dir
	    
	    p.vel.projectToVec(dirs[i]); // reset only to radial dir
	    p.vel.add(p.frc);
	    
	    p.vel.add(angularVec.cross(dirs[i]));
	    
	    p.vel.mul(1.0-p.friction);
	    
	    p.pos.add(p.vel.dup().mul(IConfig.updateRate));
	    
	    dirs[i] = p.pos.diff(center);
	    
	    if(avrgDir.len()<IConfig.tolerance){
		if(dirs[i].len()>IConfig.tolerance){ avrgDir.add(dirs[i]); }
	    }
	    else{
		if(avrgDir.dot(dirs[i]) < 0) avrgDir.add(dirs[i], -1.0);
		else avrgDir.add(dirs[i]);
	    }
	}

	if(avrgDir.len()<IConfig.tolerance){ avrgDir.set(0,0,1); } // default
	
	
	for(int i=0; i<particles.size(); i++){
	    IParticleGeo p = particles.get(i);
	    //p.pos.set(p.pos.projectToLine(center, avrgDir));
	    p.pos.projectToLine(center, avrgDir);
	    
	    //if(p.parent!=null) p.parent.updateGraphic();
	    p.updateTarget(); // necessary?
	    
	    p.frc.zero();
	}
	
	//if(parent!=null) parent.updateGraphic();
	//updateTarget();// moved to IDynamicsBase.postUpdate()
    }
    
        
}
