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
   A Class of rendering material properties for IObject geometries.
   In the current version, this material information is not used yet.
   
   @author Satoru Sugihara
*/

public class IBasicMaterial extends IMaterial{
    public IColor ambient;
    public IColor diffuse;
    public IColor emission;
    public IColor specular;
    public IColor reflection;
    public IColor transparent;
    public double refraction = 1.;
    public double reflectivity = 0.;
    public double shine = 0.;
    public double transparency = 0.;
}
