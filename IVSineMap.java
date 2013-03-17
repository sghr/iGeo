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
   A subclass of IMap defined by sine curve in v direction.
   
   @author Satoru Sugihara
*/
public class IVSineMap extends IMap{
    public double minValue, maxValue;
    public double frequency;
    public double phase;
    
    public IVSineMap(double minamp, double maxamp, double freq, double phs){
	minValue = minamp;
	maxValue = maxamp;
	frequency = freq;
	phase = phs;
    }
    public double get(double u, double v){ return get(v); }
    public double get(double x){
	return (Math.sin( (x-phase)*frequency*2*Math.PI)+1)/2*(maxValue-minValue)+minValue;
    }
}
