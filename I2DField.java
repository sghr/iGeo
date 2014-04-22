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

/**
   2D vector filed agent.
   
   @author Satoru Sugihara
*/

public class I2DField extends IField implements I2DFieldI{
    public I2DFieldI field;
    public I2DField(I2DFieldI f){ field=f; }
    public IVec2I get(IVecI pt){ return field.get(pt); }
    public IVec2I get(IVecI pt, IVecI vel){ return field.get(pt,vel); }
    
    //public void applyField(IParticleI p){ p.push(get(p.pos()).to3d()); }
    public void applyField(IParticleI p){
	p.push(get(p.pos(),p.vel()).to3d());
    }
    
    public I2DFieldI field(){ return field; }
    /** set no decay */
    public I2DField noDecay(){ field.noDecay(); return this; }
    /** set linear decay with threshold; When distance is equal to threshold, output is zero.*/
    public I2DField linearDecay(double threshold){ field.linearDecay(threshold); return this; }
    /** alias of linearDecay */
    public I2DField linear(double threshold){ return linearDecay(threshold); }
    
    /** set Gaussian decay with threshold; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
    */
    public I2DField gaussianDecay(double threshold){ field.gaussianDecay(threshold); return this; }
    /** alias of gaussianDecay */
    public I2DField gaussian(double threshold){ return gaussianDecay(threshold); }
    public I2DField gauss(double threshold){ return gaussianDecay(threshold); }
    
    /** this returns current decay type */
    //public Decay decay();
    
    
    /** if output vector is besed on constant length (intensity) or variable depending geometry when curve or surface tangent is used */
    public I2DField constantIntensity(boolean b){ field.constantIntensity(b); return this; }
    
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public I2DField bidirectional(boolean b){ field.bidirectional(b); return this; }
        
    
    /** set decay threshold */
    public I2DField threshold(double t){ field.threshold(t); return this; }
    /** get decay threshold */
    public double threshold(){ return field.threshold(); }
    
    /** set output intensity */
    public I2DField intensity(double i){ field.intensity(i); return this; }
    /** get output intensity */
    public double intensity(){ return field.intensity(); }

    public void del(){ field.del(); super.del(); }
    /** stop agent with option of deleting/keeping the geometry the agent owns */
    public void del(boolean deleteGeometry){ 
	if(deleteGeometry) field.del();
	super.del(deleteGeometry);
    }
}
