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
   A subclass of IMap defined by extracting part of another map.
   
   @author Satoru Sugihara
*/
public class ISubMap extends IMap{
    public IMap map;
    public double u1,u2,v1,v2;
    public ISubMap(IMap mp, double ustart, double uend, double vstart, double vend){
	map=mp;
	u1=ustart;
	u2=uend;
	v1=vstart;
	v2=vend;
    }
    public double get(double u, double v){ return map.get((u2-u1)*u+u1,(v2-v1)*v+v1); }
}
