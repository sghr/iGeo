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
   3D vector filed defined by normal vector of mesh vertices
   
   @author Satoru Sugihara
*/

public class IMeshNormalField extends I3DField{
    public IMeshNormalField(IMeshI m){ super(new IMeshNormalFieldGeo(m)); }
    public static class IMeshNormalFieldGeo extends IMeshFieldGeo{
	public IMeshNormalFieldGeo(IMeshI m){ super(m); }
	public IVecI get(int vertexIndex){
	    return mesh.vertex(vertexIndex).nml();
	}
    }
    
    public IMeshNormalField noDecay(){ super.noDecay(); return this; }
    public IMeshNormalField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public IMeshNormalField linear(double threshold){ super.linear(threshold); return this; }
    public IMeshNormalField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public IMeshNormalField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public IMeshNormalField gauss(double threshold){ super.gauss(threshold); return this; }
    public IMeshNormalField decay(IDecay decay, double threshold){ super.decay(decay,threshold); return this; }
    public IMeshNormalField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IMeshNormalField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public IMeshNormalField threshold(double t){ super.threshold(t); return this; }
    public IMeshNormalField intensity(double i){ super.intensity(i); return this; }
}
