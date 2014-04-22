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
   2D vector filed defined by a NURBS surface slope in respect to a given vector.
   
   @author Satoru Sugihara
*/

public class I2DSurfaceSlopeField extends I2DField{
    public I2DSurfaceSlopeField(ISurfaceI srf){
	super(new I2DSurfaceSlopeFieldGeo(srf));
    }
    static public class I2DSurfaceSlopeFieldGeo extends I2DSurfaceFieldGeo{
	public static IVec axis = new IVec(0,0,-1);
	public I2DSurfaceSlopeFieldGeo(ISurfaceI srf){ super(srf,srf); constantIntensity=false; }
	
	//public IVec2I get(IVecI v, IVecI vel, IVec2I uv){ return get(v,uv); }
		
	public IVec2I get(IVecI v, IVec2I uv){
	    IVec n = fieldSurface.nml(uv).get().unit();
	    return n.cross(axis).icross(n).to2d();
	    // should steeper slop creates larger 2d force although projected force is not large?
	    //IVec t = n.cross(axis).icross(n);
	    //double len = t.len(); if(len>0){ return t.to2d().len(len); } return t.to2d();
	}
    }
    public I2DSurfaceSlopeField noDecay(){ super.noDecay(); return this; }
    public I2DSurfaceSlopeField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public I2DSurfaceSlopeField linear(double threshold){ super.linear(threshold); return this; }
    public I2DSurfaceSlopeField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public I2DSurfaceSlopeField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public I2DSurfaceSlopeField gauss(double threshold){ super.gauss(threshold); return this; }
    
    public I2DSurfaceSlopeField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
/** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public I2DSurfaceSlopeField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public I2DSurfaceSlopeField threshold(double t){ super.threshold(t); return this; }
    public I2DSurfaceSlopeField intensity(double i){ super.intensity(i); return this; }
}
