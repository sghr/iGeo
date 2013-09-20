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
   Class of IDynamics to simulate tension force between two particles on curve based on distance in u parameter space.
   
   @author Satoru Sugihara
*/
public class ITensionOnCurve extends IDynamicsBase implements ITensionI{
    //public static double defaultTension=1.0;
    
    public IParticleOnCurveI pt1, pt2;
    /** tension is a coefficient to convert distance of two points to amount of force. */
    public double tension = defaultTension;
    /** if constantTension is true, amount of force is always constant and it's equals to tension.
        Only direction of force changes. But if the distance is zero, force is also zero. */
    public boolean constantTension = false;    
    public double maxTension = -1; // default negative, meaning no maximum limit
    public boolean isCurveClosed;
    
    public ITensionOnCurve(IParticleOnCurveI p1, IParticleOnCurveI p2, double tension, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
	this.tension = tension;
	isCurveClosed = pt1.curve().isClosed();
	// curve of p1 and p2 has to be same. but not checked.
    }
    
    public ITensionOnCurve(IParticleOnCurveI p1, IParticleOnCurveI p2, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
	
	isCurveClosed = pt1.curve().isClosed();
	// curve of p1 and p2 has to be same. but not checked.
    }
    
    public ITensionOnCurve(IParticleOnCurveI p1, IParticleOnCurveI p2, double tension){
	super();
	pt1=p1; pt2=p2;
	this.tension = tension;
	isCurveClosed = pt1.curve().isClosed();
	// curve of p1 and p2 has to be same. but not checked.
    }
    
    public ITensionOnCurve(IParticleOnCurveI p1, IParticleOnCurveI p2){
	super();
	pt1=p1; pt2=p2;
	
	isCurveClosed = pt1.curve().isClosed();
	// curve of p1 and p2 has to be same. but not checked.
    }
    
    public double tension(){ return tension; }
    public ITensionOnCurve tension(double tension){ this.tension = tension; return this; }
    
    public boolean constant(){ return constantTension; }
    public ITensionOnCurve constant(boolean cnst){ constantTension = cnst; return this; }
    
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension. if constant is set, maxTension is ignored. */
    public double maxTension(){ return maxTension; }
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension if constant is set, maxTension is ignored. */
    public ITensionOnCurve maxTension(double maxTension){ this.maxTension = maxTension; return this; }
    
    
    /** getting end point. i==0 or i==1 */
    public IParticleI pt(int i){ if(i==1) return pt2; return pt1; }
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
    
    
    public ITensionOnCurve parent(IObject par){ super.parent(par); return this; }
    public ITensionOnCurve target(IObject targetObj){ super.target(targetObj); return this; }
    public ITensionOnCurve removeTarget(int i){ super.removeTarget(i); return this; }
    public ITensionOnCurve removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
    
    synchronized public void interact(ArrayList<IDynamics> dynamics){
	
	double udiff = pt2.upos() - pt1.upos();
	if(isCurveClosed){
	    if(udiff>0.5) udiff -= 1.0; // cyclic
	    else if(udiff<-0.5) udiff += 1.0; // cyclic
	}
	if(constantTension){
	    if(udiff>0) udiff=tension;
	    else if(udiff<0) udiff=-tension;
	}
	else{
	    udiff*=tension;
	    if(maxTension>=0 && udiff>maxTension){
		udiff = maxTension;
	    }
	}
	
	pt1.addUForce(udiff);
	pt2.addUForce(-udiff); // opposite
    }
    
    synchronized public void update(){
    }
    
}
