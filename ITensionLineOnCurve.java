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
   Class of IDynamicObject to simulate tension force between two particles.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ITensionLineOnCurve extends ICurve implements ITensionI{
    
    public ITensionOnCurve tension;
    
    public ITensionLineOnCurve(IParticleOnCurve p1, IParticleOnCurve p2){
	super(p1.pos(), p2.pos());
	initTensionLine(p1,p2);
    }
    
    public ITensionLineOnCurve(IParticleOnCurve p1, IParticleOnCurve p2, double tension){
	super(p1.pos(), p2.pos());
	initTensionLine(p1,p2);
	tension(tension);
    }
    
    public void initTensionLine(IParticleOnCurve p1, IParticleOnCurve p2){
	tension = new ITensionOnCurve(p1,p2,this);
	addDynamics(tension);
    }
    
    public double tension(){ return tension.tension(); }
    public ITensionLineOnCurve tension(double tensionIntensity){
	tension.tension(tensionIntensity); return this;
    }
    
    public boolean constant(){ return tension.constant(); }
    public ITensionLineOnCurve constant(boolean cnst){
	tension.constant(cnst); return this;
    }
    
    public IParticleI pt(int i){ return tension.pt(i); }
    
}
