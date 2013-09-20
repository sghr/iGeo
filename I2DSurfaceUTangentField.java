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
   2D vector filed defined by a NURBS surface tangent in U.
   
   @author Satoru Sugihara
*/

public class I2DSurfaceUTangentField extends I2DField{
    public I2DSurfaceUTangentField(ISurfaceI srf){ super(new I2DSurfaceUTangentFieldGeo(srf)); }
    static public class I2DSurfaceUTangentFieldGeo extends I2DSurfaceFieldGeo{
	public I2DSurfaceUTangentFieldGeo(ISurfaceI srf){ super(srf,srf); }
	public IVec2I get(IVecI v, IVec2I uv){ return fieldSurface.utan(uv).to2d(); }
    }
    
    public I2DSurfaceUTangentField noDecay(){ super.noDecay(); return this; }
    public I2DSurfaceUTangentField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public I2DSurfaceUTangentField linear(double threshold){ super.linear(threshold); return this; }
    public I2DSurfaceUTangentField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public I2DSurfaceUTangentField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public I2DSurfaceUTangentField gauss(double threshold){ super.gauss(threshold); return this; }
    
    public I2DSurfaceUTangentField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
/** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public I2DSurfaceUTangentField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public I2DSurfaceUTangentField threshold(double t){ super.threshold(t); return this; }
    public I2DSurfaceUTangentField intensity(double i){ super.intensity(i); return this; }
    
}
