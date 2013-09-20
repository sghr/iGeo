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
   scalar filed agent.
   
   @author Satoru Sugihara
*/

abstract public class IScalarField extends IField implements IScalarFieldI{
    public IScalarFieldI field;
    
    public IScalarField(IScalarFieldI f){ field = f; }
    
    /** convert scalar value to 3d vector */
    abstract public IVecI to3d(IScalar v);
    public void applyForce(IParticleI p){ p.push(to3d(get(p.pos()))); }
    public IScalarFieldI field(){ return field; }
    
    /** set no decay */
    public IScalarField noDecay(){ field.noDecay(); return this; }
    /** set linear decay with threshold; When distance is equal to threshold, output is zero.*/
    public IScalarField linearDecay(double threshold){ field.linearDecay(threshold); return this; }
    public IScalarField linear(double threshold){ field.linear(threshold); return this; }
    /** set Gaussian decay with threshold; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
    */
    public IScalarField gaussianDecay(double threshold){ field.gaussianDecay(threshold); return this; }
    public IScalarField gaussian(double threshold){ field.gaussian(threshold); return this; }
    public IScalarField gauss(double threshold){ field.gauss(threshold); return this; }
    /** this returns current decay type */
    //public Decay decay();
    
    /** if output vector is besed on constant length (intensity) or variable depending geometry when curve or surface tangent is used */
    public IScalarField constantIntensity(boolean b){ field.constantIntensity(b); return this; }
    
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IScalarField bidirectional(boolean b){ field.bidirectional(b); return this; }
    
    
    
    /** set decay threshold */
    public IScalarField threshold(double t){ field.threshold(t); return this; }
    /** get decay threshold */
    public double threshold(){ return field.threshold(); }
    
    /** set output intensity */
    public IScalarField intensity(double i){ field.intensity(i); return this; }
    /** get output intensity */
    public double intensity(){ return field.intensity(); }
    
}
