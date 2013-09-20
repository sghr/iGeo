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
   2D vector filed defined by a NURBS surface normal.
   
   @author Satoru Sugihara
*/

public class I2DSurfaceNormalField extends I2DField{
    public I2DSurfaceNormalField(ISurfaceI srf){ super(new I2DSurfaceNormalField(srf)); }
    public static class I2DSurfaceNormalFieldGeo extends I2DSurfaceFieldGeo{
	public I2DSurfaceNormalFieldGeo(ISurfaceI srf){ super(srf,srf); }
	public IVec2I get(IVecI v, IVec2I uv){ return fieldSurface.nml(uv).to2d(); }
    }
    public I2DSurfaceNormalField noDecay(){ super.noDecay(); return this; }
    public I2DSurfaceNormalField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public I2DSurfaceNormalField linear(double threshold){ super.linear(threshold); return this; }
    public I2DSurfaceNormalField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public I2DSurfaceNormalField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public I2DSurfaceNormalField gauss(double threshold){ super.gauss(threshold); return this; }
    public I2DSurfaceNormalField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public I2DSurfaceNormalField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public I2DSurfaceNormalField threshold(double t){ super.threshold(t); return this; }
    public I2DSurfaceNormalField intensity(double i){ super.intensity(i); return this; }
}
