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
   3D vector filed defined by a NURBS surface normal.
   
   @author Satoru Sugihara
*/

public class ISurfaceNormalField extends I3DField{
    public ISurfaceNormalField(ISurfaceI srf){ super(new ISurfaceNormalFieldGeo(srf)); }
    static public class ISurfaceNormalFieldGeo extends ISurfaceFieldGeo{
	public ISurfaceNormalFieldGeo(ISurfaceI srf){ super(srf,srf); }
	public IVecI get(IVecI v, IVec2I uv){ return fieldSurface.nml(uv); }
    }
    
    public ISurfaceNormalField noDecay(){ super.noDecay(); return this; }
    public ISurfaceNormalField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public ISurfaceNormalField linear(double threshold){ super.linear(threshold); return this; }
    public ISurfaceNormalField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public ISurfaceNormalField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public ISurfaceNormalField gauss(double threshold){ super.gauss(threshold); return this; }
    public ISurfaceNormalField constantIntensity(boolean b){ super.constantIntensity(b); return this; }

    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public ISurfaceNormalField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public ISurfaceNormalField threshold(double t){ super.threshold(t); return this; }
    public ISurfaceNormalField intensity(double i){ super.intensity(i); return this; }
    
}
