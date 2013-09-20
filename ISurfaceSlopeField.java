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
   3D vector filed defined by a NURBS surface slope in respect to a given vector.
   
   @author Satoru Sugihara
*/

public class ISurfaceSlopeField extends I3DField{
    public ISurfaceSlopeField(ISurfaceI srf){ super(new ISurfaceSlopeFieldGeo(srf)); }
    public ISurfaceSlopeField(ISurfaceI srf, IVec gravityDir){ super(new ISurfaceSlopeFieldGeo(srf,gravityDir)); }
    static public class ISurfaceSlopeFieldGeo extends ISurfaceFieldGeo{
	IVec gravity;
	public ISurfaceSlopeFieldGeo(ISurfaceI srf){ super(srf,srf); gravity=new IVec(0,0,-1); }
	public ISurfaceSlopeFieldGeo(ISurfaceI srf, IVec gravityDir){ super(srf,srf); gravity=gravityDir; }
	public IVecI get(IVecI v, IVec2I uv){
	    IVec n = fieldSurface.nml(uv).get();
	    return n.cross(gravity).icross(n);
	}
    }
    
    public ISurfaceSlopeField noDecay(){ super.noDecay(); return this; }
    public ISurfaceSlopeField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public ISurfaceSlopeField linear(double threshold){ super.linear(threshold); return this; }
    public ISurfaceSlopeField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public ISurfaceSlopeField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public ISurfaceSlopeField gauss(double threshold){ super.gauss(threshold); return this; }
    public ISurfaceSlopeField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public ISurfaceSlopeField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public ISurfaceSlopeField threshold(double t){ super.threshold(t); return this; }
    public ISurfaceSlopeField intensity(double i){ super.intensity(i); return this; }
    
}
