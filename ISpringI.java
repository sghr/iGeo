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
   Interface API of straightener (straightening force) classes
   
   @author Satoru Sugihara
*/
public interface ISpringI extends ITensionI{
    
    /** get length */
    public double len();
    /** set length */
    public ISpringI len(double length);
    /** set length by two points */
    public ISpringI len(IVecI p1, IVecI p2);

    /** alias of len */
    public double length();
    /** alias of len */
    public ISpringI length(double length);
    /** alias of len */
    public ISpringI length(IVecI p1, IVecI p2);
    
}
