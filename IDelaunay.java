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

import java.util.*;

import igeo.gui.*;

/**
   Class to calculate Delaunay triangulation out of 3D points.
   
   @author Satoru Sugihara
*/
public class IDelaunay {
    
    public static double maxDistToCheck = -1;
    
    /**
       Getting delaunay tetrahedron cells out of array of 3D points
       @return array of tetrahedron, which consist of array of 4 points of IVec
    */
    public static IVec[][] getTetrahedron(IVec[] pts){
	if(pts.length<=4){
	    return new IVec[][]{ pts };
	}
	
	ArrayList<IVec[]> tetra = new ArrayList<IVec[]>();
	
	for(int i=0; i<pts.length-3; i++){
	    IOut.debug(40, (i+1)+"/"+(pts.length-3)); //
	    for(int j=i+1; j<pts.length-2; j++){
		
		if(maxDistToCheck < 0 ||
		   maxDistToCheck >= 0 && pts[i].dist(pts[j]) <= maxDistToCheck){
		    
		    for(int k=j+1; k<pts.length-1; k++){
			
			if(maxDistToCheck < 0 ||
			   maxDistToCheck >= 0 && pts[i].dist(pts[k]) <= maxDistToCheck){
			    
			    for(int l=k+1; l<pts.length; l++){
				
				if(maxDistToCheck < 0 ||
				   maxDistToCheck >= 0 && pts[i].dist(pts[l]) <= maxDistToCheck){
				    
				    boolean anyInside=false;
				    for(int m=0; m<pts.length && !anyInside; m++){
					
					//IOut.p("checking "+i+","+j+","+k+","+l);
					if(m!=i && m!=j && m!=k && m!=l){
					    // maxDist check?
					    
					    if(maxDistToCheck < 0 ||
					       maxDistToCheck >= 0 && pts[i].dist(pts[m]) <= maxDistToCheck){
						
						if(isInsideCircumsphere(pts[m],pts[i],pts[j],pts[k],pts[l])){
						    anyInside=true;
						    //IOut.p(m+" is inside");
						}
					    }
					}
				    }
				    if(!anyInside){ // nothing is inside
					//IOut.p(i+","+j+","+k+","+l+" has no inside pt");
					
					tetra.add(new IVec[]{ pts[i], pts[j], pts[k], pts[l] });
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return tetra.toArray(new IVec[tetra.size()][]);
    }

    public static IVec circumsphereCenter(IVec pt1, IVec pt2, IVec pt3, IVec pt4){
	IVec dir1 = pt2.dif(pt1);
	IVec dir2 = pt3.dif(pt1);
	IVec dir3 = pt4.dif(pt1);
	IVec mid1 = pt2.mid(pt1);
	IVec mid2 = pt3.mid(pt1);
	IVec mid3 = pt4.mid(pt1);
	
	IVec[] itxnLine = IVec.intersectPlane(dir1,mid1,dir2,mid2);
	if(itxnLine==null){
	    IOut.err("plane intersection failed: most likely two edges are parallel");
	    return null; // dir1/dir2 parallel, no circumsphere
	}
	//if(Math.abs(dir3.dot(itxnLine[0]))/(dir3.len()*itxnLine[0].len()) < IConfig.tolerance){
	if(dir3.dot(itxnLine[0])==0){
	    IOut.err("plane line intersection failed: most likely fourth point is on a plane of 3 poitns"); 
	    return null;  // plane and line are parallel
	}
	return IVec.intersectPlaneAndLine(dir3, mid3, itxnLine[0], itxnLine[1]);
    }
    
    public static boolean isInsideCircumsphere(IVec pt, IVec tetraPt1, IVec tetraPt2, IVec tetraPt3, IVec tetraPt4){

	IVec center = circumsphereCenter(tetraPt1, tetraPt2, tetraPt3, tetraPt4);
	if(center==null){
	    //IOut.p("center is null");
	    return true; // suppose circumsphere is infinetely large
	}
	
	return center.dist(pt) < center.dist(tetraPt1);
	
    }
    
}
