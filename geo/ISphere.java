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

import java.awt.Color;

import igeo.core.*;
import igeo.gui.*;

/**
   Sphere surface class
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ISphere extends ISurface{
    public IVec center;
    public double radius;
    
    public static double[] sphereKnots(){
	return new double[]{ 0.,0.,0.,.5,.5,1.,1.,1. };
    }
    
    public ISphere(double x, double y, double z, double radius){
	this(null,new IVec(x,y,z),radius);
    }
    
    public ISphere(IServerI s, double x, double y, double z, double radius){
	this(s,new IVec(x,y,z),radius);
    }
    
    public ISphere(IVec center, double radius){
	this(null,center,radius);
    }
    public ISphere(IServerI s, IVec center, double radius){
	super(s);
	this.center = center; this.radius = radius;
	initSphere(s);
    }
    
    public void initSphere(IServerI s){
	IVec4[][] cpts = new IVec4[9][5];
	double sqrt2 = Math.sqrt(2)/2;
	
	IVec4 tpt = center.to4d().add(0,0,radius);
	IVec4 tptw = tpt.dup(); tptw.w = sqrt2;
	IVec4 bpt = center.to4d().add(0,0,-radius);
	IVec4 bptw = bpt.dup(); bptw.w = sqrt2;
	IVec4[] circlePts = ICircleGeo.circleCP(center,new IVec(0,0,1),radius);
	
	for(int i=0; i<cpts.length; i++){
	    if(i%2==0) cpts[i][0] = bpt; else cpts[i][0] = bptw;
	    cpts[i][1] = circlePts[i].dup().add(0,0,-radius);
	    cpts[i][1].w *= sqrt2;
	    cpts[i][2] = circlePts[i];
	    cpts[i][3] = circlePts[i].dup().add(0,0,radius);
	    cpts[i][3].w *= sqrt2;
	    if(i%2==0) cpts[i][4] = tpt; else cpts[i][4] = tptw;
	}
	
	surface = new ISurfaceGeo(cpts,
				  ICircleGeo.circleDeg(),ICircleGeo.circleDeg(),
				  ICircleGeo.circleKnots(),
				  sphereKnots());
	super.initSurface(s);
    }
    

}
