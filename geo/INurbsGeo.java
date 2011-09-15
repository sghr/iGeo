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

package igeo.geo;

import igeo.core.IParameterObject;
import igeo.core.IServerI;


/**
   Base class of NURBS geometry providing miscellaneous methods to be used in child classes.
   @see ICurveGeo
   @see ISurfaceGeo
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class INurbsGeo extends IParameterObject{
    
    
    // default constractor creates parent IGElement
    //INurbsGeo(){ super(null); }
    
    /** Default constractor doesn't create parent IGElement
     */
    INurbsGeo(){ super(); }
    
    /**
       @param h if h is null, this doesn't create parent IGElement
    */
    INurbsGeo(IServerI h){
	super();
	// when do you need to register Geo class in the server?
	if(h!=null) createObject(h);
    }
    
    
    public static void normalizeKnots(double[] knots, double ustart, double uend){
	for(int i=0; i<knots.length; i++){
            knots[i] -= ustart;
            knots[i] /= (uend-ustart);
        }
    }
    public static double[] createKnots(int degree, int num, boolean closed){
	if(closed) return createClosedKnots(degree,num);
	return createKnots(degree,num);
    }

    /**
       Creating generic knots. Knot values are already normalized.
    */
    public static double[] createKnots(int degree, int num){
        double knots[] = new double[degree+num+1];
        int k,m;
        for(k=0; k<=degree; k++) knots[k]=0;
        for(m=1; k<num; k++,m++) knots[k]=(double)m/(num-degree);
        for(; k<=(degree+num); k++) knots[k]=1.;
	return knots; 
    }
    public static double[] createClosedKnots(int degree, int num){
	double knots[] = new double[degree+num+1];
	for(int k=0,m=-degree; k<=(degree+num); k++,m++) knots[k]=(double)m/(num-degree);
	return knots; 
    }
    
    public static IVecI[] createClosedControlPoints(IVecI[] cpts, int degree){
	int headNum = (degree-1)/2;
	int tailNum = degree/2 + 1;
	int len = cpts.length;
	
	if(cpts[0]==cpts[cpts.length-1] || cpts[0].eq(cpts[cpts.length-1])){
	    if(degree==1) return cpts;
	    len--;
	}
	
	IVecI[] cpts2 = new IVecI[len+degree];
	for(int i=0; i<cpts2.length; i++){
	    if(i<headNum) cpts2[i] = cpts[(i-headNum+len)%len];
	    else if(i<cpts2.length-tailNum) cpts2[i] = cpts[i-headNum];
	    else cpts2[i] = cpts[(i-(cpts2.length-tailNum))%len];
	}
	return cpts2;
	
	/*
	if(cpts[0]==cpts[cpts.length-1] || cpts[0].eq(cpts[cpts.length-1])){
	    if(degree==1) return cpts;
	    IVecI[] cpts2 = new IVecI[cpts.length+degree-1];
	    int headNum = (degree-1)/2;
	    int tailNum = degree/2;
	    for(int i=0; i<cpts2.length; i++){
		if(i<headNum) cpts2[i] = cpts[(i-headNum+cpts.length-1)%(cpts.length-1)];
		else if(i<cpts2.length-tailNum) cpts2[i] = cpts[i-headNum];
		else cpts2[i] = cpts[(i+1-(cpts2.length-tailNum))%(cpts.length-1)];
	    }
	    return cpts2;
	}
	IVecI[] cpts2 = new IVecI[cpts.length+degree];
	int headNum = (degree-1)/2;
	int tailNum = degree/2 +1;
	for(int i=0; i<cpts2.length; i++){
	    if(i<headNum) cpts2[i] = cpts[(i-headNum + cpts.length)%cpts.length];
	    else if(i<cpts2.length-tailNum) cpts2[i] = cpts[i-headNum];
	    else cpts2[i] = cpts[(i-(cpts2.length-tailNum))%cpts.length];
	}
	return cpts2;
	*/
    }
    
    
}
