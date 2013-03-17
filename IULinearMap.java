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
   A subclass of IMap defined by two value to generate gradient map in u direction.
   
   @author Satoru Sugihara
*/
public class IULinearMap extends IMap{
    public double start, end; //uval1, uval2;
    public ArrayList<Double> domains;
    public ArrayList<Double> values;

    public IULinearMap(){ start = 0; end = 0; }
    public IULinearMap(double value){ start = value; end = value; }
    public IULinearMap(double u0, double u1){ start = u0; end = u1; }
    public IULinearMap(double u0, double u05, double u1){
	start = u0; end = u1;
	domains = new ArrayList<Double>();
	values =  new ArrayList<Double>();
	domains.add(0.5);
	values.add(u05);
    }
    public IULinearMap(double u0, double u033, double u066, double u1){
	start = u0; end = u1;
	domains = new ArrayList<Double>();
	values =  new ArrayList<Double>();
	domains.add(1./3);
	values.add(u033);
	domains.add(2./3);
	values.add(u066);
    }
    
    /**
       adding (inserting) value between the domain of 0 - 1
    */
    public void add(double u, double value){
	if(IG.eq(u, 0.0, IConfig.parameterTolerance)){ start = value; return; }
	if(IG.eq(u, 1.0, IConfig.parameterTolerance)){ end = value; return; }
	
	if(u < 0.0 || u > 1.0){ IOut.err("u ("+u+") is out of range. skipped"); return; }
	
	if(domains==null){
	    domains = new ArrayList<Double>();
	    values = new ArrayList<Double>();
	    domains.add(u);
	    values.add(value);
	    return;
	}
	
	for(int i=0; i<domains.size(); i++){
	    if(IG.eq(u, domains.get(i), IConfig.parameterTolerance)){
		domains.set(i, u);
		values.set(i, value);
		return;
	    }
	    else if(u < domains.get(i)){
		domains.add(i, u);
		values.add(i, value);
		return;
	    }
	}
	domains.add(u);
	values.add(value);
    }
    /** alias of add(double,double) */
    public void addValue(double u, double value){ add(u,value); }
    /** alias of add(double,double) */
    public void insert(double u, double value){ add(u,value); }
    
    
    public double get(double u, double v){ return get(u); }
    
    public double get(double x){
	if(x < 0.) return start;
	if(x > 1.) return end;
	if(domains==null || domains.size()==0) return (end-start)*x+start;
	
	if( x <= domains.get(0) ){
	    return (values.get(0)-start)*x/domains.get(0) + start;
	}
	int num = domains.size();
	if( x >= domains.get(num-1) ){
	    return (end-values.get(num-1))* (x-domains.get(num-1))/(1.0-domains.get(num-1)) + values.get(num-1);
	}
	for(int i=0; i<num-1; i++){
	    if(x>=domains.get(i) && x<domains.get(i+1))
		return (values.get(i+1)-values.get(i))*
		    (x-domains.get(i))/(domains.get(i+1)-domains.get(i)) + values.get(i);
	}
	
	return end; // error?
	//return values.get(values.size()-1);
	//return 0;
    }
    
    
    public void flipU(){
	double tmp = start; start = end; end = tmp;
	if(domains!=null && domains.size()>1){
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
}
