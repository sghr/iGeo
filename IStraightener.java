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
   Class of IDynamicsBase to simulate tension force to make 3 points in straight
   
   @author Satoru Sugihara
*/
public class IStraightener extends IDynamicsBase implements IStraightenerI{
    //public static double defaultTension=1.0;
    
    public IParticleI pt1, pt2, pt3;
    /** tension is a coefficient to convert distance of two points to amount of force. */
    public double tension = defaultTension;
    public boolean constantTension=false;
    public double maxTension = -1; // default negative, meaning no maximum limit
    
    public IStraightener(IParticleI p1, IParticleI p2, IParticleI p3, IObject parent){
	super(parent);
	pt1=p1; pt2=p2; pt3=p3;
    }
    
    public IStraightener(IParticleGeo p1, IParticleGeo p2, IParticleGeo p3, IObject parent){
	super(parent);
	pt1=p1; pt2=p2; pt3=p3;
    }
    
    public IStraightener(IVec p1, IVec p2, IVec p3, IObject parent){
	super(parent);
	pt1 = new IParticleGeo(p1); pt2=new IParticleGeo(p2); pt3 = new IParticleGeo(p3);
    }
    
    public IStraightener(IVecI p1, IVecI p2, IVecI p3, IObject parent){
	super(parent);
	pt1 = new IParticleGeo(p1.get()); pt2=new IParticleGeo(p2.get()); pt3=new IParticleGeo(p3.get());
    }
    
    public IStraightener(IParticleI p1, IParticleI p2, IParticleI p3){
	super();
	pt1=p1; pt2=p2; pt3=p3;
    }
    
    public IStraightener(IParticleGeo p1, IParticleGeo p2, IParticleGeo p3){
	super();
	pt1=p1; pt2=p2; pt3=p3;
    }
    
    public IStraightener(IVec p1, IVec p2, IVec p3){
	super();
	pt1 = new IParticleGeo(p1); pt2=new IParticleGeo(p2); pt3=new IParticleGeo(p3);
    }
    
    public IStraightener(IVecI p1, IVecI p2, IVecI p3){
	super();
	pt1 = new IParticleGeo(p1.get()); pt2=new IParticleGeo(p2.get()); pt3=new IParticleGeo(p3.get());
    }
    
    public double tension(){ return tension; }
    public IStraightener tension(double tension){ this.tension = tension; return this; }
    
    public boolean constant(){ return constantTension; }
    public IStraightener constant(boolean cnst){ constantTension = cnst; return this; }
    
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension. if constant is set, maxTension is ignored. */
    public double maxTension(){ return maxTension; }
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension if constant is set, maxTension is ignored. */
    public IStraightener maxTension(double maxTension){ this.maxTension = maxTension; return this; }
    
    /** getting end point. i==0 , i==1 or i==2 */
    public IParticleI pt(int i){
	if(i==2) return pt3; if(i==1) return pt2; return pt1;
    }
    /** alias of pt(int) */
    public IParticleI particle(int i){ return pt(i); }
    /** position of particle(int i) */
    public IVec pos(int i){ return pt(i).pos(); }
    
    /** getting end point1. */
    public IParticleI pt1(){ return pt1; }
    /** alias of pt1() */
    public IParticleI particle1(){ return pt1(); }
    /** position of particle1 */
    public IVec pos1(){ return pt1().pos(); }
    
    /** getting end point2. */
    public IParticleI pt2(){ return pt2; }
    /** alias of pt2() */
    public IParticleI particle2(){ return pt2(); }
    /** position of particle2 */
    public IVec pos2(){ return pt2().pos(); }
    
    /** getting end point3. */
    public IParticleI pt3(){ return pt3; }
    /** alias of pt3() */
    public IParticleI particle3(){ return pt3(); }
    /** position of particle3() */
    public IVec pos3(){ return pt3().pos(); }
    
    
    public IStraightener parent(IObject par){ super.parent(par); return this; }
    public IStraightener target(IObject targetObj){ super.target(targetObj); return this; }
    public IStraightener removeTarget(int i){ super.removeTarget(i); return this; }
    public IStraightener removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
    synchronized public void interact(ArrayList<IDynamics> dynamics){
	IVec p1 = pt1.pos(), p2 = pt2.pos(), p3 = pt3.pos();
	IVec dif = p3.diff(p1);
	
	
	dif.mul(p2.diff(p1).dot(dif)/dif.len2()).add(p1).sub(p2);
	if(!constantTension){
	    dif.mul(tension);
	    if(maxTension>=0 && dif.len()>maxTension){
		dif.len(maxTension);
	    }
	}
	else if(dif.len2()>0){ dif.len(tension); }
	
	// adding force to the middle point
	pt2.addForce(dif);
	// adding force to the end points, half in the opposite direction.
	dif.mul(-0.5);
	pt1.addForce(dif);
	pt3.addForce(dif);
    }
    
    //synchronized public void update(){ updateTarget(); }// moved to IDynamicsBase.postUpdate()
    
}
