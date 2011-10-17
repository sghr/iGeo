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
public class IParticle extends IDynamicObjectBase{
    
    static double defaultFriction = 0.0;
    
    //public IObject parent=null;
    
    public double mass=1.0;
    public IVec pos;
    public IVec vel;
    public IVec acc;
    public IVec frc;
    boolean fixed=false;
    public double friction = defaultFriction;
    
    public IParticle(IVec pos){this. pos = pos; initParticle(); }
    
    synchronized public void initParticle(){
	vel = new IVec();
	acc = new IVec();
	frc = new IVec();
    }
    
    // implementation of IDynamicObject
    //public IObject parent(){ return parent; }
    //public ISubobject parent(IObject parent){ this.parent=parent; return this; }
        
    synchronized public IParticle fix(){ fixed=true; return this; }
    synchronized public IParticle unfix(){ fixed=false; return this; }
    
    synchronized public double mass(){ return mass; }
    synchronized public IParticle mass(double mass){ this.mass=mass; return this; }
    
    synchronized public IVec position(){ return pos(); }
    synchronized public IParticle position(IVec v){ pos(v); return this; }
    
    synchronized public IVec pos(){ return pos; }
    synchronized public IParticle pos(IVec v){ pos.set(v); return this; }
    
    synchronized public IVec velocity(){ return vel(); }
    synchronized public IParticle velocity(IVec v){ vel(v); return this; }
    
    synchronized public IVec vel(){ return vel; }
    synchronized public IParticle vel(IVec v){ vel.set(v); return this; }
    
    synchronized public IVec acceleration(){ return acc(); }
    synchronized public IParticle acceleration(IVec v){ acc(v); return this; }
    
    synchronized public IVec acc(){ return acc; }
    synchronized public IParticle acc(IVec v){ acc.set(v); return this; }
    
    synchronized public IVec force(){ return frc(); }
    synchronized public IParticle force(IVec v){ frc(v); return this; }

    synchronized public IVec frc(){ return frc; }
    synchronized public IParticle frc(IVec v){ frc.set(v); return this; }

    synchronized public double friction(){ return fric(); }
    synchronized public IParticle friction(double friction){ fric(friction); return this; }
    
    synchronized public double fric(){ return friction; }
    synchronized public IParticle fric(double friction){ this.friction=friction; return this; }
    
    synchronized public IParticle addForce(IVec f){ frc.add(f); return this; }
    
    synchronized public IParticle resetForce(){ frc.set(0,0,0); return this; }
    
    synchronized public void interact(ArrayList<IDynamicObject> dynamics){}
    
    synchronized public void update(){
	if(fixed) return;
	pos.add(vel.add(frc.mul((mass*IConfig.dynamicsUpdateSpeed)/1000)).mul(1.0-friction));
	frc.set(0,0,0);
    }
    
}
