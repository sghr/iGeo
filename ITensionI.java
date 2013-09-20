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
   Interface API of tension classes
   
   @author Satoru Sugihara
*/
public interface ITensionI{
    public static double defaultTension=1.0;
    
    /** tension is a coefficient to convert distance of two points to amount of force. */
    public double tension();
    /** tension is a coefficient to convert distance of two points to amount of force. */
    public ITensionI tension(double tension);
    
    /** if constantTension is true, amount of force is always constant and it's equals to tension.
        Only direction of force changes. But if the distance is zero, force is also zero. */
    public boolean constant();
    /** if constantTension is true, amount of force is always constant and it's equals to tension.
        Only direction of force changes. But if the distance is zero, force is also zero. */
    public ITensionI constant(boolean cnst);
    
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension if constant is set, maxTension is ignored. */
    public double maxTension();
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension if constant is set, maxTension is ignored. */
    public ITensionI maxTension(double maxTension);
    
    
    /** getting end point. i==0 or i==1 */
    public IParticleI pt(int i);
    /** alias of pt(int) */
    public IParticleI particle(int i);
    /** position of particle(i) */
    public IVec pos(int i);
    /** getting end point1. */
    public IParticleI pt1();
    /** alias of pt1() */
    public IParticleI particle1();
    /** position of particle1 */
    public IVec pos1();
    /** getting end point2. */
    public IParticleI pt2();
    /** alias of pt2() */
    public IParticleI particle2();
    /** position of particle1 */
    public IVec pos2();
    
}
