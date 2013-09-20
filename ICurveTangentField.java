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
   3D vector filed defined by a NURBS curve tanget.
   
   @author Satoru Sugihara
*/

public class ICurveTangentField extends I3DField{
    public ICurveTangentField(ICurveI crv){ super(new ICurveTangentFieldGeo(crv)); }
    static public class ICurveTangentFieldGeo extends ICurveFieldGeo{
	public ICurveTangentFieldGeo(ICurveI crv){ super(crv,crv); }
	public IVecI get(IVecI v, double u){ return fieldCurve.tan(u); }
    }
    
    public ICurveTangentField noDecay(){ super.noDecay(); return this; }
    public ICurveTangentField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public ICurveTangentField linear(double threshold){ super.linear(threshold); return this; }
    public ICurveTangentField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public ICurveTangentField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public ICurveTangentField gauss(double threshold){ super.gauss(threshold); return this; }
    public ICurveTangentField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public ICurveTangentField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public ICurveTangentField threshold(double t){ super.threshold(t); return this; }
    public ICurveTangentField intensity(double i){ super.intensity(i); return this; }
    
}
