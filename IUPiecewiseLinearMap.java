/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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
   A subclass of IMap defined by multiple u domain and multiple values.
   A value on the map is calculated by interpolation of assigned values.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IUPiecewiseLinearMap extends IMap{
    public ArrayList<Double> domains;
    public ArrayList<Double> values;
    public IUPiecewiseLinearMap(){
	domains = new ArrayList<Double>();
	values = new ArrayList<Double>();
    }
    /**
       this needs to be called in order of domain
       @param domain [0,1]
    */
    public void addValue(double domain, double value){
	domains.add(domain);
	values.add(value);
    }
    public double get(double u, double v){ return get(u); }
    public double get(double x){
	if(domains.size()==0) return 0;
	
	if(x <= domains.get(0)) return values.get(0);
	
	for(int i=0; i<domains.size()-1; i++){
	    //if(i==0 && x <= domains.get(i)) return values.get(i);
	    if(x>=domains.get(i) && x<domains.get(i+1))
		return (values.get(i+1)-values.get(i))*
		    (x-domains.get(i))/(domains.get(i+1)-domains.get(i)) +
		    values.get(i);
	    //if(i==domains.size()-2 && x>=domains.get(i+1)) return values.get(i+1);
	}
	
	return values.get(values.size()-1);
	//return 0;
    }
    public void flipU(){
	ArrayList<Double> newdomains = new ArrayList<Double>();
	ArrayList<Double> newvalues = new ArrayList<Double>();
	for(int i=domains.size()-1; i>=0; i--){
	    newdomains.add(1.0-domains.get(i));
	    newvalues.add(values.get(i));
	}
	domains = newdomains;
	values = newvalues;
    }
}

