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
   Implementation of piecewise function out of multiple IPolynominalFunction
   
   @author Satoru Sugihara
*/
public class IPiecewisePolynomialFunction extends IPiecewiseFunction{
    
    public IPiecewisePolynomialFunction(double[] dom){ super(dom); }
    public IPiecewisePolynomialFunction(){}
    
    public IPiecewisePolynomialFunction(IPiecewisePolynomialFunction p){
	super((IPiecewiseFunction)p);
    }
    
    public IPiecewisePolynomialFunction dup(){ return new IPiecewisePolynomialFunction(this); }
    
    public void init(IPiecewisePolynomialFunction p){
	super.init((IPiecewiseFunction)p);
    }
    
    public void setFunction(int idx, IPiecewisePolynomialFunction f){
	functions[idx+1] = f; // !!! +1 !!! this matches with index from getIndexOfDomain
    }
    
    public void mul(double a){
        for(int i=0; i<functions.length; i++){
	    if(functions[i]!=null) ((IPolynomialFunction)functions[i]).mul(a);
	}
    }
    
    public void mul(IPolynomialFunction p){
	for(int i=0; i<functions.length; i++){
	    if(functions[i]!=null)((IPolynomialFunction)functions[i]).mul(p);
	}
    }
    
    public void differentiate(){
	for(int i=0; i<functions.length; i++){
	    if(functions[i]!=null) ((IPolynomialFunction)functions[i]).differentiate();
	}
    }
    
    public void add(IPolynomialFunction p){
	for(int i=0; i<functions.length; i++){
	    if(functions[i]!=null) ((IPolynomialFunction)functions[i]).add(p);
	}
    }
    
    public void add(IPiecewisePolynomialFunction p){
	// check domains
	ArrayList<Double> domainPt = new ArrayList<Double>();
	ArrayList<IFunction> domainFunc1 = new ArrayList<IFunction>();
	ArrayList<IFunction> domainFunc2 = new ArrayList<IFunction>();
	
	for(int i=0, j=0; (i<domains.length) || (j<p.domains.length); ){
	    if(i==domains.length){
		domainPt.add(new Double(p.domains[j]));
		domainFunc1.add(functions[i]);
		domainFunc2.add(p.functions[j]);
		j++;
	    }
	    else if(j==p.domains.length){
		domainPt.add(new Double(domains[i]));
		domainFunc1.add(functions[i]);
		domainFunc2.add(p.functions[j]);
		i++;
	    }
	    else if(domains[i]==p.domains[j]){
		domainPt.add(new Double(domains[i]));
		domainFunc1.add(functions[i]);
		domainFunc2.add(p.functions[j]);
		i++;
		j++;
	    }
	    else if(domains[i] < p.domains[j]){
		domainPt.add(new Double(domains[i]));
		domainFunc1.add(functions[i]);
		domainFunc2.add(p.functions[j]);
		i++;
	    }
	    else{ // domains[i] > p.domains[j]
		domainPt.add(new Double(p.domains[j]));
		domainFunc1.add(functions[i]);
		domainFunc2.add(p.functions[j]);
		j++;
	    }
	    
	}
	
	domainFunc1.add(functions[functions.length-1]);
	domainFunc2.add(p.functions[p.functions.length-1]);
	
	double[] newdomains = new double[domainPt.size()];
	for(int i=0; i<domainPt.size(); i++){ newdomains[i] = domainPt.get(i).doubleValue(); }

	
	IFunction[] newfunc = new IFunction[domainPt.size()+1];
	for(int i=0; i<newfunc.length; i++){
	    if( domainFunc1.get(i)!=null && domainFunc2.get(i)!=null ){
		newfunc[i] = new IPolynomialFunction((IPolynomialFunction)domainFunc1.get(i));
		((IPolynomialFunction)newfunc[i]).add((IPolynomialFunction)domainFunc2.get(i));
	    }
	    else if( domainFunc1.get(i)!=null ){
		newfunc[i] = new IPolynomialFunction((IPolynomialFunction)domainFunc1.get(i));
	    }
	    else if( domainFunc2.get(i)!=null ){
		newfunc[i] = new IPolynomialFunction((IPolynomialFunction)domainFunc2.get(i));
	    }
	    else{ newfunc[i] = null; }
	}
	domains = newdomains;
	functions = newfunc;
    }
    
    public String toString(){
	if(functions==null || domains==null) return super.toString();
	String str = "";
	for(int i=0; i<functions.length; i++){
	    if(i==0){
		str += "domain: -infinity to "+String.valueOf(domains[i])+": ";
		
		if(functions[i]==null) str += "null";
		else str += ((IPolynomialFunction)functions[i]).toString();
	    }
	    else if(i==functions.length-1){
		str += "domain: "+String.valueOf(domains[i-1])+" to infinity: ";
		if(functions[i]==null) str += "null";
		else str += ((IPolynomialFunction)functions[i]).toString();
	    }
	    else{
		str += "domain: "+String.valueOf(domains[i-1])+" to "+String.valueOf(domains[i])+": ";
		if(functions[i]==null) str += "null";
		else str += ((IPolynomialFunction)functions[i]).toString();
	    }
	    str += "\n";
	}
	return str;
    }
    
}
