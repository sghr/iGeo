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
   3D field attracting only on the planar direction.
   
   @author Satoru Sugihara
*/

public class IPlanarAttractor extends I3DField{
    public IPlanarAttractor(IVecI pos, IVecI planeDir){ super(new IPlanarAttractorGeo(pos,planeDir)); }
    static public class IPlanarAttractorGeo extends IPointFieldGeo{
	public IPlanarAttractorGeo(IVecI pos, IVecI planeDir){ super(pos,planeDir); }
	public IVecI getForce(IVecI v, IVecI orig){ return orig.get().dif(v).projectToPlane(dir); }
    }
    
    public IPlanarAttractor noDecay(){ super.noDecay(); return this; }
    public IPlanarAttractor linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public IPlanarAttractor linear(double threshold){ super.linear(threshold); return this; }
    public IPlanarAttractor gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public IPlanarAttractor gaussian(double threshold){ super.gaussian(threshold); return this; }
    public IPlanarAttractor gauss(double threshold){ super.gauss(threshold); return this; }
    public IPlanarAttractor decay(IDecay decay, double threshold){ super.decay(decay,threshold); return this; }
    public IPlanarAttractor constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IPlanarAttractor bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public IPlanarAttractor threshold(double t){ super.threshold(t); return this; }
    public IPlanarAttractor intensity(double i){ super.intensity(i); return this; }
    
}
