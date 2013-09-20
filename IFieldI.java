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
   abstract field function
   
   @author Satoru Sugihara
*/

public interface IFieldI{// extends ITransformable{
    /** get field value at 3D location */
    public IVal get(IVecI pos);
    
    /** get field value at 3D location with velocity */
    public IVal get(IVecI pos, IVecI vel);
    
    
    /** set no decay */
    public IFieldI noDecay();
    /** set linear decay with threshold; When distance is equal to threshold, output is zero.*/
    public IFieldI linearDecay(double threshold);
    /** alias of linearDecay */
    public IFieldI linear(double threshold);
    
    /** set Gaussian decay with threshold; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
    */    
    public IFieldI gaussianDecay(double threshold);
    /** alias of gaussianDecay */
    public IFieldI gaussian(double threshold);
    /** alias of gaussianDecay */
    public IFieldI gauss(double threshold);
    
    /** this returns current decay type */
    //public Decay decay();
    
    /** if output vector is besed on constant length (intensity) or variable depending geometry when curve or surface tangent is used */
    public IFieldI constantIntensity(boolean b);
    
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IFieldI bidirectional(boolean b);
    
    /** set decay threshold */
    public IFieldI threshold(double t);
    /** get decay threshold */
    public double threshold();
    
    /** set output intensity */
    public IFieldI intensity(double i);
    /** get output intensity */
    public double intensity();

    // delete associated geometry, etc.
    public void del();
    
}
