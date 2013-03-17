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
   A subclass of IMap defined by z depth of surface. 
   
   @author Satoru Sugihara
*/
public class ISurfaceZDepthMap extends ISurfaceDepthMap{
    public ISurfaceZDepthMap(ISurfaceI surf, double minZ, double maxZ){
	super(surf, new IVec(0,0,1), new IVec(0,0,minZ), new IVec(0,0,maxZ));
    }
    
    public ISurfaceZDepthMap(ISurfaceI surf){
	super(surf, new IVec(0,0,1), new IVec(0,0,0), new IVec(0,0,1));
	
	final int usampleNum=20;
	final int vsampleNum=20;
	double minz=0,maxz=0;
	// check limit by control points
	for(int i=0; i<usampleNum; i++){
	    for(int j=0; j<vsampleNum; j++){
		double z = surface.pt((double)i/usampleNum,(double)j/vsampleNum).z();
		if(i==0&&j==0) minz = maxz = z;
		else{
		    if(z<minz) minz=z;
		    if(z>maxz) maxz=z;
		}
	    }
	}
	minDepthPt.set(0,0,minz);
	maxDepth = maxz-minz;
    }
}
