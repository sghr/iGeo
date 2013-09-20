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
   3D vector filed defined by a NURBS curve tanget causing curling force.
   
   @author Satoru Sugihara
*/

public class ICurveCurlField extends I3DField{
    public ICurveCurlField(ICurveI crv){ super(new ICurveCurlFieldGeo(crv)); }
    static public class ICurveCurlFieldGeo extends ICurveFieldGeo{
	public ICurveCurlFieldGeo(ICurveI crv){ super(crv,crv); }
	public IVecI get(IVecI v, double u){
	    //return fieldCurve.tan(u).get().icross(fieldCurve.pt(u).sub(v));
	    return fieldCurve.pt(u).get().sub(v).icross(fieldCurve.tan(u));
	}
    }
    
    public ICurveCurlField noDecay(){ super.noDecay(); return this; }
    public ICurveCurlField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public ICurveCurlField linear(double threshold){ super.linear(threshold); return this; }
    public ICurveCurlField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public ICurveCurlField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public ICurveCurlField gauss(double threshold){ super.gauss(threshold); return this; }
    public ICurveCurlField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public ICurveCurlField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public ICurveCurlField threshold(double t){ super.threshold(t); return this; }
    public ICurveCurlField intensity(double i){ super.intensity(i); return this; }
    
}
