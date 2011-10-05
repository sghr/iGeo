/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

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

package igeo.util;

import igeo.geo.*;
import igeo.gui.*;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;


/**
   A subclass of IMap defined by two value to generate gradient map in v direction.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IVLinearMap extends IMap{
    public double vval1, vval2;
    public IVLinearMap(double v1, double v2){ vval1 = v1; vval2 = v2; }
    public double get(double u, double v){ return (vval2-vval1)*v+vval1; }	
    public void flipV(){ double tmp = vval1; vval1 = vval2; vval2 = tmp; }
}
