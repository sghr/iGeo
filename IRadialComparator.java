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
   A class to define order of IVec in angle around a specified center to be used in sorting with ISort.
   
   @see ISort
   
   @author Satoru Sugihara
*/
public class IRadialComparator implements IComparator<IVec>{
    public IVec center = new IVec(0,0,0);
    public IVec normal = new IVec(0,0,1);
    public IVec startDir = new IVec(1,0,0);
    
    public IRadialComparator(){}
    public IRadialComparator(IVec center){ this.center = center; }
    public IRadialComparator(IVec center, IVec normal){
	this.center = center; this.normal = normal;
	double a = startDir.angle(normal);
	if(a < IConfig.angleTolerance ||
	   Math.abs(Math.abs(a)-Math.PI) < IConfig.angleTolerance ){
	    startDir = normal.cross(new IVec(0,0,1)).cross(normal);
	}
	else{ startDir = normal.cross(startDir).cross(normal); }
    }
    public IRadialComparator(IVec center, IVec normal, IVec startDir){
	this.center = center; this.normal = normal; this.startDir=startDir;
	if( Math.abs(Math.abs(startDir.angle(normal)) - Math.PI/2) >=
	    IConfig.angleTolerance){
	    startDir = normal.cross(startDir.dup()).cross(normal); 
	}
    }
    
    public double angle(IVec v){
	double a = startDir.angle(normal.cross(v.diff(center)).cross(normal), normal);
	if(a<0){ a += Math.PI*2; } // angle() returns -Math.PI to +Math.PI.
	return a;
    }
    
    public int compare(IVec v1, IVec v2){ // return >0, <0, ==0
	double a1 = angle(v1);
	double a2 = angle(v2);
	if(a1 < a2) return -1;
	if(a1 > a2) return 1;
	return 0;
    }
    
}

