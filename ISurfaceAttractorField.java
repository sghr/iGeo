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
   3D vector filed defined by a NURBS surface to attract particle to the closest point on the surface.
   
   @author Satoru Sugihara
*/

public class ISurfaceAttractorField extends I3DField{
    public ISurfaceAttractorField(ISurfaceI srf){ super(new ISurfaceAttractorFieldGeo(srf)); }
    static public class ISurfaceAttractorFieldGeo extends ISurfaceFieldGeo{
	public ISurfaceAttractorFieldGeo(ISurfaceI srf){ super(srf,srf); }
	public IVecI get(IVecI pos, IVec2I uv){
	    return fieldSurface.pt(uv).sub(pos);
	}
    }
    
    public ISurfaceAttractorField noDecay(){ super.noDecay(); return this; }
    public ISurfaceAttractorField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public ISurfaceAttractorField linear(double threshold){ super.linear(threshold); return this; }
    public ISurfaceAttractorField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public ISurfaceAttractorField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public ISurfaceAttractorField gauss(double threshold){ super.gauss(threshold); return this; }
    public ISurfaceAttractorField decay(IDecay decay, double threshold){ super.decay(decay,threshold); return this; }
    public ISurfaceAttractorField constantIntensity(boolean b){ super.constantIntensity(b); return this; }

    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public ISurfaceAttractorField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public ISurfaceAttractorField threshold(double t){ super.threshold(t); return this; }
    public ISurfaceAttractorField intensity(double i){ super.intensity(i); return this; }
    
}
