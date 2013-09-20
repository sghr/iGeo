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
   Curl field defined by a point and an axis vector.
   
   @author Satoru Sugihara
*/

public class IPointCurlFieldGeo extends IPointFieldGeo{
    public IPointCurlFieldGeo(IVecI pos, IVecI axis){ super(pos,axis); }
    
    public IVecI getForce(IVecI v, IVecI orig){ return orig.get().dif(v).icross(dir); }
    
    public IPointCurlFieldGeo noDecay(){ super.noDecay(); return this; }
    public IPointCurlFieldGeo linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public IPointCurlFieldGeo linear(double threshold){ super.linear(threshold); return this; }
    public IPointCurlFieldGeo gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public IPointCurlFieldGeo gaussian(double threshold){ super.gaussian(threshold); return this; }
    public IPointCurlFieldGeo gauss(double threshold){ super.gauss(threshold); return this; }
    public IPointCurlFieldGeo constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IPointCurlFieldGeo bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public IPointCurlFieldGeo threshold(double t){ super.threshold(t); return this; }
    public IPointCurlFieldGeo intensity(double i){ super.intensity(i); return this; }
}
