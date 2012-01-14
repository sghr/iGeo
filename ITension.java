/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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
   @version 0.7.0.0;
*/
public class ITension extends IDynamicsBase implements ITensionI{
    //public static double defaultTension=1.0;
    
    public IParticleI pt1, pt2;
    /** tension is a coefficient to convert distance of two points to amount of force. */
    public double tension = defaultTension;
    /** if constantTension is true, amount of force is always constant and it's equals to tension.
	Only direction of force changes. But if the distance is zero, force is also zero. */
    public boolean constantTension = false;
    
    public ITension(IParticleI p1, IParticleI p2, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
    }
    
    public ITension(IParticle p1, IParticle p2, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
    }
    
    public ITension(IVec p1, IVec p2, IObject parent){
	super(parent);
	pt1 = new IParticle(p1); pt2=new IParticle(p2);
    }
    
    public ITension(IVecI p1, IVecI p2, IObject parent){
	super(parent);
	pt1 = new IParticle(p1.get()); pt2=new IParticle(p2.get());
    }
    
    public ITension(IParticleI p1, IParticleI p2){
	super();
	pt1=p1; pt2=p2;
    }
    
    public ITension(IParticle p1, IParticle p2){
	super();
	pt1=p1; pt2=p2;
    }
    
    public ITension(IVec p1, IVec p2){
	super();
	pt1 = new IParticle(p1); pt2=new IParticle(p2);
    }
    
    public ITension(IVecI p1, IVecI p2){
	super();
	pt1 = new IParticle(p1.get()); pt2=new IParticle(p2.get());
    }
    
    public double tension(){ return tension; }
    public ITension tension(double tension){ this.tension = tension; return this; }
    
    public boolean constant(){ return constantTension; }
    public ITension constant(boolean cnst){ constantTension = cnst; return this; }
    
    public IParticleI pt(int i){ if(i==0) return pt1; return pt2; }
    
    public ITension parent(IObject par){ super.parent(par); return this; }
    public ITension target(IObject targetObj){ super.target(targetObj); return this; }
    public ITension removeTarget(int i){ super.removeTarget(i); return this; }
    public ITension removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
    
    synchronized public void interact(ArrayList<IDynamics> dynamics){
	IVec diff = pt2.pos().diff(pt1.pos());
	if(constantTension){
	    if(diff.len2()>0) diff.len(tension);
	}
	else{ diff.mul(tension); }
	pt1.addForce(diff);
	pt2.addForce(diff.neg()); // opposite dir
    }
    
    synchronized public void update(){
    }
    
}
