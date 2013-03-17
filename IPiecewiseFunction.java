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
   Implementation of piecewise function out of multiple IFunction.
   
   @author Satoru Sugihara
*/
public class IPiecewiseFunction implements IFunction{
    public double[] domains;
    public IFunction[] functions;
    
    public IPiecewiseFunction(double[] dom, IFunction[] funcs){
	domains = dom;
	functions = funcs;
    }
    
    public IPiecewiseFunction(int numOfInterval){
	domains = new double[numOfInterval+1];
	functions = new IFunction[numOfInterval+2];
	// functions[0]: domain: -inifinete to domain[0]
	// functions[1]: domain: domain[0] to domain[1]
	// ...
	// functions[numOfInterval+1] : domain: domain[numOfInterval] to infinite
    }
    
    public IPiecewiseFunction(double[] dom){
	domains = dom;
	functions = new IFunction[domains.length+1];
    }
    
    public IPiecewiseFunction(IPiecewiseFunction f){ init(f); }
    
    public IPiecewiseFunction(){}
    
    public IPiecewiseFunction dup(){ return new IPiecewiseFunction(this); }
    
    public void init(int numOfInterval){
	domains = new double[numOfInterval+1];
	functions = new IFunction[numOfInterval+2];
    }
    
    public void init(double[] dom, IFunction[] funcs){
	domains = dom;
	functions = funcs;
    }
    
    public void init(double[] dom){
	domains = dom;
	functions = new IFunction[domains.length+1];
    }
    
    public void init(IPiecewiseFunction f){
	domains = new double[f.domains.length];
	for(int i=0; i<domains.length; i++) domains[i] = f.domains[i];
	functions = new IFunction[f.functions.length];
	for(int i=0; i<functions.length; i++){
	    //functions[i]=f.functions[i];
	    if(f.functions[i]!=null) functions[i]=f.functions[i].dup();
	    else functions[i]=null;
	}
    }
    
    public void setDomain(int idx, double dom){ domains[idx] = dom; }
    
    public void setFunction(int idx, IFunction f){
	//functions[idx] = f;
	functions[idx+1] = f; // !!! +1 !!! this matches with index from getIndexOfDomain
    }
    
    public int getNumberOfDomains(){ return domains.length; }
    
    public int getIndexOfDomain(double x){
	/*
	if(x<domains[0]) return -1; // index -1 means from -infinity to domains[0] 
	if(x>=domains[domains.length-1]) return domains.length-1;
	
	int min = 0;
	int max = domains.length-1;
	int mid = (min+max)/2;
	
	for(; min<max && ( x<domains[mid] || x>=domains[mid+1] ); mid = (min+max)/2 ){
	    if(x<domains[mid]) max = mid;
	    else min = mid;
	}
	return mid;
	*/
	return getIndexOfDomain(x,domains);
    }
    
    public static int getIndexOfDomain(double x, double[] domains){
	if(x<domains[0]) return -1; // index -1 means from -infinity to domains[0] 
	if(x>=domains[domains.length-1]) return domains.length-1;
	
	int min = 0;
	int max = domains.length-1;
	int mid = (min+max)/2;
	
	for(; min<max && ( x<domains[mid] || x>=domains[mid+1] ); mid = (min+max)/2 ){
	    if(x<domains[mid]) max = mid;
	    else min = mid;
	}
	return mid;
    }
    
    
    public double upperBoundOfDomain(int index){
	if(index==domains.length-1){  return Double.POSITIVE_INFINITY; }
	return domains[index+1];
    }
    
    public double lowerBoundOfDomain(int index){
	if(index==-1){  return Double.NEGATIVE_INFINITY; }
	return domains[index];
    }
    
    public double eval(double x){
	int index = getIndexOfDomain(x);
	return functions[index+1].eval(x);
    }
    
}
