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

package igeo;

import java.util.ArrayList;

/**
   Class of IDynamics to simulate tension force between two particles on curve based on distance in u parameter space.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ITensionOnCurve extends IDynamicsBase{
    public static double defaultTension=1.0;
    
    public IParticleOnCurve pt1, pt2;
    public double tension = defaultTension;
    
    public boolean isCurveClosed;
    
    public ITensionOnCurve(IParticleOnCurve p1, IParticleOnCurve p2, double tension, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
	this.tension = tension;
	isCurveClosed = pt1.curve.isClosed();
	// curve of p1 and p2 has to be same. but not checked.
    }
    
    public ITensionOnCurve(IParticleOnCurve p1, IParticleOnCurve p2, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
	
	isCurveClosed = pt1.curve.isClosed();
	// curve of p1 and p2 has to be same. but not checked.
    }
    
    public ITensionOnCurve(IParticleOnCurve p1, IParticleOnCurve p2, double tension){
	super();
	pt1=p1; pt2=p2;
	this.tension = tension;
	isCurveClosed = pt1.curve.isClosed();
	// curve of p1 and p2 has to be same. but not checked.
    }
    
    public ITensionOnCurve(IParticleOnCurve p1, IParticleOnCurve p2){
	super();
	pt1=p1; pt2=p2;
	
	isCurveClosed = pt1.curve.isClosed();
	// curve of p1 and p2 has to be same. but not checked.
    }
    
    public double tension(){ return tension; }
    public ITensionOnCurve tension(double tension){ this.tension = tension; return this; }


    public ITensionOnCurve parent(IObject par){ super.parent(par); return this; }
    public ITensionOnCurve target(IObject targetObj){ super.target(targetObj); return this; }
    public ITensionOnCurve removeTarget(int i){ super.removeTarget(i); return this; }
    public ITensionOnCurve removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
    
    synchronized public void interact(ArrayList<IDynamics> dynamics){
	
	double udiff = pt2.upos - pt1.upos;
	if(isCurveClosed){
	    if(udiff>0.5) udiff -= 1.0; // cyclic
	    else if(udiff<-0.5) udiff += 1.0; // cyclic
	}
	udiff*=tension;
	pt1.addUForce(udiff);
	pt2.addUForce(-udiff); // opposite
	
    }
    
    synchronized public void update(){
    }
    
}
