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
   A subclass of IMap defined by depth of surface in the assigned depth direction.
   
   @author Satoru Sugihara
*/
public class ISurfaceDepthMap extends IMap{
    public ISurfaceI surface;
    public IVec orig, uvec, vvec;
    //double minDepth,maxDepth;
    
    /**
       maxDepthDir is a normal vector
    */
    public IVec minDepthPt=null, depthDir=null;
    public double maxDepth=0;
    
    /**
       @param surf surface should be rectangle
    */
    public ISurfaceDepthMap(ISurfaceI surf){
	surface=surf;
	initMap();
    }
    
    public ISurfaceDepthMap(ISurfaceI surf, IVec depthDir){
	surface=surf;
	this.depthDir = depthDir;
	initMap();
    }
    
    public ISurfaceDepthMap(ISurfaceI surf, IVec depthDir, IVec minPt, IVec maxPt){
	surface=surf;
	this.depthDir = depthDir.dup().unit();
	this.minDepthPt = minPt;
	this.maxDepth = maxPt.diff(minPt).dot(this.depthDir);
    }
    
    public ISurfaceDepthMap(ISurfaceI surf, ICurveI measureLine){
	surface=surf;
	
	minDepthPt = measureLine.start().get();
	depthDir = measureLine.end().get().diff(minDepthPt);
	maxDepth = depthDir.len();
	depthDir.unit();
	    
	initMap();
    }
    
    public void initMap(){
	if(minDepthPt==null||depthDir==null){
	    orig = surface.corner(0,0).get();
	    uvec = surface.corner(1,0).get().diff(orig);
	    vvec = surface.corner(0,1).get().diff(orig);
		
	    minDepthPt = orig;
	    if(depthDir==null) depthDir = uvec.cross(vvec).unit();
	    
	    double minz=0,maxz=0;
	    // check limit by control points
	    for(int i=0; i<surface.unum(); i++){
		for(int j=0; j<surface.vnum(); j++){
		    double depth = getDepth(surface.cp(i,j).get());
		    if(i==0&&j==0) minz = maxz = depth;
		    else{
			if(depth<minz) minz=depth;
			if(depth>maxz) maxz=depth;
		    }
		}
	    }
	    //minDepth=minz;
	    //maxDepth=maxz;
	    
	    maxDepth = maxz-minz;
	    if(minz<0) minDepthPt=depthDir.dup().len(minz).add(minDepthPt);
	    
	}
    }
    
    public double getDepth(double u, double v){ return getDepth(surface.pt(u,v).get()); }
    
    public double getDepth(IVec pt){
	IVec diff = pt.diff(minDepthPt);
	return depthDir.dot(diff);
    }
    
    public double get(double u, double v){
	IVec diff = surface.pt(u,v).get().diff(minDepthPt);
	return depthDir.dot(diff)/maxDepth;
    }
}
