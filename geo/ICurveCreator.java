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

import java.util.ArrayList;

import igeo.core.*;
import igeo.util.*;

/**
   class with collection of static methods to create various type of curve.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ICurveCreator{
    /** state variable of a server to store surfaces created in the methods in this class */
    public static IServerI server = null;
    /** set a server to store surfaces created in the methods in this class */
    public static void server(IServerI s){ server=s; }
    public static IServerI server(){ return server; }
    
    
    public static ICurve curve(IVecI[] cpts, int degree, double[] knots, double ustart, double uend){
	return new ICurve(server,cpts,degree,knots,ustart,uend);
    }
    
    public static ICurve curve(IVecI[] cpts, int degree, double[] knots){
	return new ICurve(server,cpts,degree,knots);
    }
    
    public static ICurve curve(IVecI[] cpts, int degree){
	return new ICurve(server,cpts,degree);
    }
    
    public static ICurve curve(IVecI[] cpts){
	return new ICurve(server,cpts);
    }
    
    public static ICurve curve(IVecI[] cpts, int degree, boolean close){
	return new ICurve(server,cpts,degree,close);
    }
    public static ICurve curve(IVecI[] cpts, boolean close){
	return new ICurve(server,cpts,close);
    }
    public static ICurve curve(IVecI pt1, IVecI pt2){ return new ICurve(server,pt1,pt2); }
    
    public static ICurve curve(double x1, double y1, double z1, double x2, double y2, double z2){
	return new ICurve(server, x1,y1,z1,x2,y2,z2);
    }
    public static ICurve curve(double[][] xyzValues){ return new ICurve(server, xyzValues); }
    public static ICurve curve(double[][] xyzValues, int degree){
	return new ICurve(server, xyzValues, degree);
    }
    public static ICurve curve(double[][] xyzValues, boolean close){
	return new ICurve(server, xyzValues, close);
    }
    public static ICurve curve(double[][] xyzValues, int degree, boolean close){
	return new ICurve(server, xyzValues, degree, close);
    }
    public static ICurve curve(ICurveI crv){ return new ICurve(server, crv); }
    
    // rect, circle, oval, arc, 
    public static ICurve rect(){
	return null;
    }
    
}
