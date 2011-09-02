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

package igeo.core;

import java.util.ArrayList;
import java.awt.Color;
import igeo.gui.*;


/**
   A Class of rendering material properties for IObject geometries.
   In the current version, this material information is not used yet.
   
   @author Satoru Sugihara
   @version 0.7.0.0
*/

public class IBasicMaterial extends IMaterial{
    Color ambient;
    Color diffuse;
    Color emission;
    Color specular;
    Color reflection;
    Color transparent;
    double refraction = 1.;
    double reflectivity = 0.;
    double shine = 0.;
    double transparency = 0.;
}
