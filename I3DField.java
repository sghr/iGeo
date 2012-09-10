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

/**
   3D vector filed agent.
   
   @author Satoru Sugihara
*/

abstract public class I3DField extends IField implements I3DFieldI{
    public I3DFieldI field;
    public I3DField(I3DFieldI f){ field=f; }
    public IVecI get(IVecI pt){ return field.get(pt); }
    public void applyField(IParticleI p){ p.push(get(p.pos())); }
    public I3DFieldI field(){ return field; }
    /** set no decay */
    public I3DField noDecay(){ field.noDecay(); return this; }
    /** set linear decay with threshold; When distance is equal to threshold, output is zero.*/
    public I3DField linearDecay(double threshold){ field.linearDecay(threshold); return this; }
    /** alias of linearDecay */
    public I3DField linear(double threshold){ return linearDecay(threshold); }
    
    /** set Gaussian decay with threshold; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
    */
    public I3DField gaussianDecay(double threshold){ field.gaussianDecay(threshold); return this; }
    /** alias of gaussianDecay */
    public I3DField gaussian(double threshold){ return gaussianDecay(threshold); }
    /** this returns current decay type */
    //public Decay decay();
    
    
    /** if output vector is besed on constant length (intensity) or variable depending geometry when curve or surface tangent is used */
    public I3DField constantIntensity(boolean b){ field.constantIntensity(b); return this; }
    
    
    /** set decay threshold */
    public I3DField threshold(double t){ field.threshold(t); return this; }
    /** get decay threshold */
    public double threshold(){ return field.threshold(); }
    
    /** set output intensity */
    public I3DField intensity(double i){ field.intensity(i); return this; }
    /** get output intensity */
    public double intensity(){ return field.intensity(); }
}
