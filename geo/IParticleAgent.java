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

import java.util.ArrayList;

import igeo.core.*;
import igeo.util.*;

/**
   Class of an agent with IParticle.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IParticleAgent extends IPointAgent{

    IParticle particle;
    
    public IParticleAgent(){ super(); initParticleAgent(); }
    public IParticleAgent(double x, double y, double z){ super(x,y,z); initParticleAgent(); }
    public IParticleAgent(IVec p){ super(p); initParticleAgent(); }
    public IParticleAgent(IVecI p){ super(p); initParticleAgent(); }
    
    public void initParticleAgent(){
	particle = new IParticle(pos);
	addDynamics(particle);
    }
    
    synchronized public IParticleAgent fix(){ particle.fix(); return this; }
    synchronized public IParticleAgent unfix(){ particle.unfix(); return this; }
    
    synchronized public double mass(){ return particle.mass(); }
    synchronized public IParticleAgent mass(double mass){ particle.mass(mass); return this; }
    
    synchronized public IVec position(){ return particle.position(); }
    synchronized public IParticleAgent position(IVec v){ particle.position(v); return this; }
    
    synchronized public IVec velocity(){ return particle.velocity(); }
    synchronized public IParticleAgent velocity(IVec v){ particle.velocity(v); return this; }
    
    synchronized public IVec acceleration(){ return particle.acceleration(); }
    synchronized public IParticleAgent acceleration(IVec v){ particle.acceleration(v); return this; }
    
    synchronized public IVec force(){ return particle.force(); }
    synchronized public IParticleAgent force(IVec v){ particle.force(v); return this; }

    synchronized public double friction(){ return particle.friction(); }
    synchronized public IParticleAgent friction(double friction){ particle.friction(friction); return this; }
    
    synchronized public IParticleAgent addForce(IVec f){ particle.addForce(f); return this; }
    
    synchronized public IParticleAgent resetForce(){ particle.resetForce(); return this; }
    
    
    
    
    
    
}
