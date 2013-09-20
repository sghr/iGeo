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
   3D vector filed defined by a NURBS curve.
   
   @author Satoru Sugihara
*/

public class ICurveAttractorField extends I3DField{
    public ICurveAttractorField(ICurveI crv){ super(new ICurveAttractorFieldGeo(crv)); }
    static public class ICurveAttractorFieldGeo extends ICurveFieldGeo{
	public ICurveAttractorFieldGeo(ICurveI crv){ super(crv,crv); }
	public IVecI get(IVecI v, double u){ return fieldCurve.pt(u).sub(v); }
    }

    public ICurveAttractorField noDecay(){ super.noDecay(); return this; }
    public ICurveAttractorField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public ICurveAttractorField linear(double threshold){ super.linear(threshold); return this; }
    public ICurveAttractorField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public ICurveAttractorField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public ICurveAttractorField gauss(double threshold){ super.gauss(threshold); return this; }
    public ICurveAttractorField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public ICurveAttractorField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public ICurveAttractorField threshold(double t){ super.threshold(t); return this; }
    public ICurveAttractorField intensity(double i){ super.intensity(i); return this; }
}
