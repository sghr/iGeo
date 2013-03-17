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
   Class of an implementation of IDynamicObject to have physical attributes of point.
   It has attributes of position, velocity, acceleration, force, and mass.
   Position is provided from outside to be linked.
   
   @author Satoru Sugihara
*/
public interface IParticleI extends IVecI{

    /** get mass */
    public double mass();
    /** set mass */
    public IParticleI mass(double m);
    
    /** get position */
    public IVec position();
    /** get position */
    public IVec pos();
    /** set position */
    public IParticleI position(IVecI v);
    /** set position */
    public IParticleI pos(IVecI v);
    
    /** get velocity */
    public IVec velocity();
    /** get velocity */
    public IVec vel();
    /** set velocity */
    public IParticleI velocity(IVecI v);
    /** set velocity */
    public IParticleI vel(IVecI v);
    
    /** get acceleration. no method to set acceleration. set force instead */
    public IVec acceleration();
    /** get acceleration. no method to set acceleration. set force instead */
    public IVec acc();
    
    /** get force */
    public IVec force();
    /** get force */
    public IVec frc();
    /** set force */
    public IParticleI force(IVecI v);
    /** set force */
    public IParticleI frc(IVecI v);
    
    /** get friction */
    public double friction();
    /** get friction */
    public double fric();
    /** set friction */
    public IParticleI friction(double f);
    /** set friction */
    public IParticleI fric(double f);
    
    /* alias of friction */
    public double decay();
    /* alias of friction */
    public IParticleI decay(double d);
    
    
    /** adding force to particle */
    public IParticleI push(IVecI f);
    /** adding force to particle */
    public IParticleI push(double fx, double fy, double fz);
    /** equivalent to push(f.dup().neg()) */
    public IParticleI pull(IVecI f);
    /** equivalent to push(f.dup().neg()) */
    public IParticleI pull(double fx, double fy, double fz);
    /** alias of push */
    public IParticleI addForce(IVecI f);
    /** alias of push */
    public IParticleI addForce(double fx, double fy, double fz);
    
    /** setting force zero */
    public IParticleI reset();
    /** alias of reset */
    public IParticleI resetForce();
    
    /** fix movement */
    public IParticleI fix();
    /** unfix movement */
    public IParticleI unfix();
    
    /** check if it's fixed */
    public boolean fixed();
    
    /** for other agent to control particle */
    public IParticleI skipUpdateOnce(boolean f);
    /** for other agent to control particle */
    public boolean skipUpdateOnce();
    
    // partial methods of IDynamics // how necessary?
    /** add terget object to be updated by this dynamic object. */
    public IParticleI target(IObject targetObj);
    /** get total target number. */
    public int targetNum();
    /** get target object. */
    public IObject target(int i);
    /** get all target objects. */
    public ArrayList<IObject> targets();
    /** remove target object. */
    public IParticleI  removeTarget(int i);
    /** remove target object. */
    public IParticleI  removeTarget(IObject obj);
    /** update all terget objects (should be called when the dynamic object is updated). */
    public void updateTarget();
    
}
