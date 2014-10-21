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

import java.util.ArrayList;

/**
   Class of an implementation of IDynamics to have physical attributes of point on a cureve.
   
   @author Satoru Sugihara
*/
public class IParticleOnMeshGeo extends IParticleGeo /*implements IParticleOnCurveI*/{

    public IMeshI mesh;
    public IFace face; // current face on which particle is
        
    public IParticleOnMeshGeo(IMeshI mesh){ this.mesh = mesh; }
        
}
