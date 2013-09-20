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
   abstract class of 2D vector filed.
   
   @author Satoru Sugihara
*/

abstract public class IFieldGeo implements IFieldI{
    /** type of decay from surface position */
    static public enum Decay{ None, Linear, Gaussian };
    
    public Decay decay = Decay.None; // default
    /** threshold for decay.
	When decay is None, threshold isn't used.
	In case of Linear, when distance is equal to threshold, output is zero.
	In Gaussian, threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
    */
    public double threshold; // = IConfig.defaultFieldThreshold;
    /** intensity of vector output */
    public double intensity = IConfig.defaultFieldIntensity;
    
    /** if output vector is besed on constant length (intensity) or variable depending geometry when curve or surface tangent is used */
    public boolean constantIntensity = IConfig.defaultConstantFieldIntensity; // default
    
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public boolean bidirectional = false; // default
    
    //public IFieldGeo(){}
    
    
    /** set no decay */
    public IFieldGeo noDecay(){ decay = Decay.None; return this; }
    
    /** set linear decay; When distance is equal to threshold, output is zero.*/
    public IFieldGeo linearDecay(double threshold){
	decay = Decay.Linear; this.threshold = threshold; return this;
    }
    /** alias of linearDecay */
    public IFieldGeo linear(double threshold){ return linearDecay(threshold); }
    
    /** set Gaussian decay; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
    */
    public IFieldGeo gaussianDecay(double threshold){
	decay = Decay.Gaussian; this.threshold = threshold; return this;
    }
    /** alias of gaussianDecay */
    public IFieldGeo gaussian(double threshold){ return gaussianDecay(threshold); }
    /** alias of gaussianDecay */
    public IFieldGeo gauss(double threshold){ return gaussianDecay(threshold); }
    
    public Decay decay(){ return decay; }
    
    
    /** if output vector is besed on constant length (intensity) or variable depending geometry when curve or surface tangent is used */
    public IFieldGeo constantIntensity(boolean b){ constantIntensity=b; return this; }

    
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IFieldGeo bidirectional(boolean b){ bidirectional=b; return this; }
    
    
    
    /** set decay threshold */
    public IFieldGeo threshold(double t){ threshold=t; return this; }
    public double threshold(){ return threshold; }
    
    /** set output intensity */
    public IFieldGeo intensity(double i){ intensity=i; return this; }
    public double intensity(){ return intensity; }
    
}
