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
   3D vector filed defined by a NURBS curve position (vector from the origin).
   
   @author Satoru Sugihara
*/

public class ICurvePositionField extends I3DField{
    public ICurvePositionField(ICurveI crv){ super(new ICurvePositionFieldGeo(crv)); }
    static public class ICurvePositionFieldGeo extends ICurveFieldGeo{
	public ICurvePositionFieldGeo(ICurveI crv){ super(crv,crv); }
    }
    
    public ICurvePositionField noDecay(){ super.noDecay(); return this; }
    public ICurvePositionField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public ICurvePositionField linear(double threshold){ super.linear(threshold); return this; }
    public ICurvePositionField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public ICurvePositionField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public ICurvePositionField gauss(double threshold){ super.gauss(threshold); return this; }
    public ICurvePositionField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public ICurvePositionField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public ICurvePositionField threshold(double t){ super.threshold(t); return this; }
    public ICurvePositionField intensity(double i){ super.intensity(i); return this; }
    
}
