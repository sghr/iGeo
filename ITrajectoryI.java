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
   Interface of trajectory API 
   @author Satoru Sugihara
*/
public interface ITrajectoryI{
    /** returns trajectory curve object */
    public ICurve curve();
    /** alias of curve() */
    public ICurve trajectory();
    
    /** add a point at the end of trajectory curve */
    public ITrajectoryI addCP(IVecI pt);
}
