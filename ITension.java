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
   Class of IDynamics to simulate tension force between two particles.
   
   @author Satoru Sugihara
*/
public class ITension extends IDynamicsBase implements ITensionI{
    //public static double defaultTension=1.0;
    
    public IParticleI pt1, pt2;
    /** tension is a coefficient to convert distance of two points to amount of force. */
    public double tension = defaultTension;
    /** if constantTension is true, amount of force is always constant and it's equals to tension.
	Only direction of force changes. But if the distance is zero, force is also zero. */
    public boolean constantTension = false;
    public double maxTension = -1; // default negative, meaning no limit
    
    public ITension(IParticleI p1, IParticleI p2, double tension, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
	this.tension = tension;
    }
    /*
    public ITension(IParticleGeo p1, IParticleGeo p2, double tension, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
	this.tension = tension;
    }
    
    public ITension(IParticle p1, IParticle p2, double tension, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
	this.tension = tension;
    }
    public ITension(IVec p1, IVec p2, double tension, IObject parent){
	super(parent);
	pt1 = new IParticleGeo(p1); pt2=new IParticleGeo(p2);
	this.tension = tension;
    }
    */
    
    public ITension(IVecI p1, IVecI p2, double tension, IObject parent){
	super(parent);
	pt1 = new IParticleGeo(p1.get()); pt2=new IParticleGeo(p2.get());
	this.tension = tension;
    }
        
    public ITension(IParticleI p1, IParticleI p2, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
    }
    /*
    public ITension(IParticleGeo p1, IParticleGeo p2, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
    }
    
    public ITension(IParticle p1, IParticle p2, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
    }
    public ITension(IVec p1, IVec p2, IObject parent){
	super(parent);
	pt1 = new IParticleGeo(p1); pt2=new IParticleGeo(p2);
    }
    */
    
    public ITension(IVecI p1, IVecI p2, IObject parent){
	super(parent);
	pt1 = new IParticleGeo(p1.get()); pt2=new IParticleGeo(p2.get());
    }
    
    public ITension(IParticleI p1, IParticleI p2, double tension){
	super();
	pt1=p1; pt2=p2;
	this.tension = tension;
    }
    /*
    public ITension(IParticleGeo p1, IParticleGeo p2, double tension){
	super();
	pt1=p1; pt2=p2;
	this.tension = tension;
    }
    
    public ITension(IParticle p1, IParticle p2, double tension){
	super();
	pt1=p1; pt2=p2;
	this.tension = tension;
    }
    
    public ITension(IVec p1, IVec p2, double tension){
	super();
	pt1 = new IParticleGeo(p1); pt2=new IParticleGeo(p2);
	this.tension = tension;
    }
    */
    
    public ITension(IVecI p1, IVecI p2, double tension){
	super();
	pt1 = new IParticleGeo(p1.get()); pt2=new IParticleGeo(p2.get());
	this.tension = tension;
    }
    
    public ITension(IParticleI p1, IParticleI p2){ super(); pt1=p1; pt2=p2; }
    /*
    public ITension(IParticleGeo p1, IParticleGeo p2){ super(); pt1=p1; pt2=p2; }
    
    public ITension(IParticle p1, IParticle p2){ super(); pt1=p1; pt2=p2; }
    
    public ITension(IVec p1, IVec p2){ super(); pt1 = new IParticleGeo(p1); pt2=new IParticleGeo(p2); }
    */
    public ITension(IVecI p1, IVecI p2){
	super(); pt1 = new IParticleGeo(p1.get()); pt2=new IParticleGeo(p2.get());
    }
    
    
    public double tension(){ return tension; }
    public ITension tension(double tension){ this.tension = tension; return this; }
    
    public boolean constant(){ return constantTension; }
    public ITension constant(boolean cnst){ constantTension = cnst; return this; }

    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension. if constant is set, maxTension is ignored. */
    public double maxTension(){ return maxTension; }
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension if constant is set, maxTension is ignored. */
    public ITension maxTension(double maxTension){ this.maxTension = maxTension; return this; }
    
    
    /** getting end point. i==0 or i==1. if i is other value, returns first point. */
    public IParticleI pt(int i){ if(i==1){ return pt2; } return pt1; }
    /** alias of pt1() */
    public IParticleI particle(int i){ return pt(i); }
    /** position of particle(i) */
    public IVec pos(int i){ return pt(i).pos(); }
    /** getting end point1. */
    public IParticleI pt1(){ return pt1; }
    /** alias of pt1() */
    public IParticleI particle1(){ return pt1(); }
    /** position of particle1() */
    public IVec pos1(){ return pt1().pos(); }
    /** getting end point2. */
    public IParticleI pt2(){ return pt2; }
    /** alias of pt2() */
    public IParticleI particle2(){ return pt2(); }
    /** position of particle2() */
    public IVec pos2(){ return pt2().pos(); }
    
    
    public ITension parent(IObject par){ super.parent(par); return this; }
    public ITension target(IObject targetObj){ super.target(targetObj); return this; }
    public ITension removeTarget(int i){ super.removeTarget(i); return this; }
    public ITension removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
    synchronized public void interact(ArrayList<IDynamics> dynamics){
	IVec dif = pt2.pos().dif(pt1.pos());
	// excludes the case direction cannot be defined
	if(constantTension){
	    if(dif.len2()>0){
		dif.len(tension);
		pt1.push(dif);
		pt2.pull(dif); // opposite dir
	    }
	}
	else{
	    dif.mul(tension);
	    if(maxTension>=0 && dif.len()>maxTension){
		dif.len(maxTension);
	    }
	    pt1.push(dif);
	    pt2.pull(dif); // opposite dir
	}
    }
    
    //synchronized public void update(){}
    
}
