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
   Point cache of a curve for proximity search.
   
   @author Satoru Sugihara
*/
public class ICurveCache{
    
    public ICurveGeo curve;
    public int resolution;
    public int deg;
    public IVec[] pts; // for 3d search
    public IVec2[] pts2; // for 2d search
    
    public ICurveCache(ICurveGeo crv, int resolution){
	curve = crv;
	this.resolution = resolution;
	deg = curve.deg();
	//init();
    }
    public ICurveCache(ICurveGeo crv){
	this(crv, IConfig.curveCacheResolution);
    }
    
    public void init(){
	if(deg==1){
	    pts = new IVec[curve.cpNum()];
	    for(int i=0; i<pts.length; i++){ pts[i] = curve.cp(i).get(); }
	}
	else{
	    int epnum = curve.epNum() ;
            int num = (epnum-1)*resolution+1;
	    pts = new IVec[num];
	    for(int i=0; i<epnum; i++){
                for(int j=0; j<resolution; j++){
                    if(i<epnum-1 || j==0){
                        pts[i*resolution + j] = curve.pt(curve.u(i,(double)j/resolution));
                    }
                }
            }
	}
    }
    
    
    public void init2(){
	if(deg==1){
	    pts2 = new IVec2[curve.cpNum()];
	    for(int i=0; i<pts2.length; i++){ pts2[i] = curve.cp(i).get().to2d(); }
	}
	else{
	    int epnum = curve.epNum() ;
            int num = (epnum-1)*resolution+1;
	    pts2 = new IVec2[num];
	    for(int i=0; i<epnum; i++){
                for(int j=0; j<resolution; j++){
                    if(i<epnum-1 || j==0){
                        pts2[i*resolution + j] = curve.pt(curve.u(i,(double)j/resolution)).to2d();
                    }
                }
            }
	}
    }
    
    
    /** approximate invert projection from 3D location to interanl parameter U (closest point on curve) */
    public double u(IVec pt){
	if(pts==null) init();
	
	int idx = closest(pt);
	if(deg==1){
	    if(idx==0){
		double r = pt.ratioOnSegment(pts[idx],pts[idx+1]);
		if(r<0){ r=0; } else if(r>1.0){ r=1.0; } // inside segment // added 20120826!
		return curve.u(idx,r);
	    }
	    else if(idx==pts.length-1){
		double r = pt.ratioOnSegment(pts[idx-1],pts[idx]);
		if(r<0){ r=0; } else if(r>1.0){ r=1.0; } // inside segment // added 20120826!
		return curve.u(idx-1,r);
	    }
	    else{
		double r1 = pt.ratioOnSegment(pts[idx-1],pts[idx]);
		double r2 = pt.ratioOnSegment(pts[idx],pts[idx+1]);
		if(r1<0){ r1=0; } else if(r1>1.0){ r1=1.0; } // inside segment
		if(r2<0){ r2=0; } else if(r2>1.0){ r2=1.0; } // inside segment
		double dist1 = pt.dist(pts[idx-1].sum(pts[idx],r1));
		double dist2 = pt.dist(pts[idx].sum(pts[idx+1],r2));
		if(dist1<=dist2) return curve.u(idx-1,r1);
		return curve.u(idx,r2);
	    }
	}
	else if(resolution==1){
	    if(idx==0){
		return recursiveSearch(pt, curve.u(idx,0), pts[idx], curve.u(idx+1,0), pts[idx+1], 0);
		//return recursiveSearch(pt, curve.u(idx,0), curve.u(idx,0.5)); 
	    }
	    else if(idx==pts.length-1){
		return recursiveSearch(pt, curve.u(idx-1,0), pts[idx-1], curve.u(idx,0), pts[idx], 0);
		//return recursiveSearch(pt, curve.u(idx-1,0.5), curve.u(idx,0));
	    }
	    else{
		int idx2 = closerIndexOnLine(pt,idx);
		return recursiveSearch(pt, curve.u(idx2,0), pts[idx2],
				       curve.u(idx2+1,0), pts[idx2+1], 0);
		// not sure about 0.5 but it'd be faster
		//if(idx2<idx) return recursiveSearch(pt, curve.u(idx2,0.5), curve.u(idx2+1,0));
		//else return recursiveSearch(pt, curve.u(idx,0), curve.u(idx,0.5));
	    }
	}
	else{
	    if(idx==0){
		return recursiveSearch(pt, curve.u(idx,0), pts[idx],
				       curve.u(idx,1.0/resolution), pts[idx+1], 0);
		//return recursiveSearch(pt, curve.u(idx,0), curve.u(idx,0.5)); 
	    }
	    else if(idx==pts.length-1){
		int epIdx = idx/resolution;
		double epFrac = (double)(idx%resolution)/resolution;
		return recursiveSearch(pt, curve.u(epIdx,epFrac-1.0/resolution), pts[idx-1],
				       curve.u(epIdx,epFrac), pts[idx], 0);
		//return recursiveSearch(pt, curve.u(idx-1,0.5), curve.u(idx,0));
	    }
	    else{
		int idx2 = closerIndexOnLine(pt,idx);
		int epIdx = idx2/resolution;
		double epFrac = (double)(idx2%resolution)/resolution;
		return recursiveSearch(pt, curve.u(epIdx,epFrac), pts[idx2],
				       curve.u(epIdx,epFrac+1.0/resolution),pts[idx2+1], 0);
		
		// not sure about 0.5 but it'd be faster
		//if(idx2<idx) return recursiveSearch(pt, curve.u(idx2,0.5), curve.u(idx2+1,0));
		//else return recursiveSearch(pt, curve.u(idx,0), curve.u(idx,0.5));
	    }
	}
    }
    
    
    /** approximate invert projection from 2D location to interanl parameter U */
    public double u(IVec2 pt){
	if(pts2==null) init2();
	
	int idx = closest(pt);
	if(deg==1){
	    if(idx==0){
		double r = pt.ratioOnSegment(pts2[idx],pts2[idx+1]);
		return curve.u(idx,r);
	    }
	    else if(idx==pts2.length-1){
		double r = pt.ratioOnSegment(pts2[idx-1],pts2[idx]);
		return curve.u(idx-1,r);
	    }
	    else{
		double r1 = pt.ratioOnSegment(pts2[idx-1],pts2[idx]);
		double r2 = pt.ratioOnSegment(pts2[idx],pts2[idx+1]);
		if(r1<0){ r1=0; } else if(r1>1.0){ r1=1.0; } // inside segment
		if(r2<0){ r2=0; } else if(r2>1.0){ r2=1.0; } // inside segment
		double dist1 = pt.dist(pts2[idx-1].sum(pts2[idx],r1));
		double dist2 = pt.dist(pts2[idx].sum(pts2[idx+1],r2));
		if(dist1<=dist2) return curve.u(idx-1,r1);
		return curve.u(idx,r2);
	    }
	}
	else if(resolution==1){
	    if(idx==0){
		return recursiveSearch(pt, curve.u(idx,0), pts2[idx], curve.u(idx+1,0), pts2[idx+1], 0);
	    }
	    else if(idx==pts2.length-1){
		return recursiveSearch(pt, curve.u(idx-1,0), pts2[idx-1], curve.u(idx,0), pts2[idx], 0);
	    }
	    else{
		int idx2 = closerIndexOnLine(pt,idx);
		return recursiveSearch(pt, curve.u(idx2,0), pts2[idx2],
				       curve.u(idx2+1,0), pts2[idx2+1], 0);
	    }
	}
	else{
	    if(idx==0){
		return recursiveSearch(pt, curve.u(idx,0), pts2[idx],
				       curve.u(idx,1.0/resolution), pts2[idx+1], 0);
	    }
	    else if(idx==pts2.length-1){
		int epIdx = idx/resolution;
		double epFrac = (double)(idx%resolution)/resolution;
		return recursiveSearch(pt, curve.u(epIdx,epFrac-1.0/resolution), pts2[idx-1],
				       curve.u(epIdx,epFrac), pts2[idx], 0);
	    }
	    else{
		int idx2 = closerIndexOnLine(pt,idx);
		int epIdx = idx2/resolution;
		double epFrac = (double)(idx2%resolution)/resolution;
		return recursiveSearch(pt, curve.u(epIdx,epFrac), pts2[idx2],
				       curve.u(epIdx,epFrac+1.0/resolution),pts2[idx2+1], 0);
	    }
	}
    }
    
    
    public int closerIndexOnLine(IVec pt, int index){
	double dist1 = pt.distToSegment(pts[index-1], pts[index]);
	double dist2 = pt.distToSegment(pts[index], pts[index+1]);
	if(dist1<=dist2) return index-1;
	return index;
    }
    
    public int closerIndexOnLine(IVec2 pt, int index){
	double dist1 = pt.distToSegment(pts2[index-1], pts2[index]);
	double dist2 = pt.distToSegment(pts2[index], pts2[index+1]);
	if(dist1<=dist2) return index-1;
	return index;
    }
    
    public double recursiveSearch(IVec pt,  double minU, IVec minPt, double maxU, IVec maxPt, int depthCount){
	if(minPt==null){ minPt = curve.pt(minU); }
	if(maxPt==null){ maxPt = curve.pt(maxU); }
	double dist1 = pt.dist(minPt);
	double dist2 = pt.dist(maxPt);
	depthCount++;
	if(dist1<=dist2){
	    if(dist1 < IConfig.tolerance || minPt.dist(maxPt) < IConfig.tolerance ||
	       depthCount >= IConfig.cacheRecursionMaxDepth){ return minU; }
	    return recursiveSearch(pt, minU, minPt, (minU+maxU)/2, null, depthCount);
	}
	if(dist2 < IConfig.tolerance || minPt.dist(maxPt) < IConfig.tolerance ||
	   depthCount >= IConfig.cacheRecursionMaxDepth){ return maxU; }
	return recursiveSearch(pt, (minU+maxU)/2, null, maxU, maxPt, depthCount);
    }
    
    public double recursiveSearch(IVec2 pt,  double minU, IVec2 minPt, double maxU, IVec2 maxPt, int depthCount){
	if(minPt==null){ minPt = curve.pt(minU).to2d(); }
	if(maxPt==null){ maxPt = curve.pt(maxU).to2d(); }
	double dist1 = pt.dist(minPt);
	double dist2 = pt.dist(maxPt);
	depthCount++;
	if(dist1<=dist2){
	    if(dist1 < IConfig.tolerance || minPt.dist(maxPt) < IConfig.tolerance ||
	       depthCount >= IConfig.cacheRecursionMaxDepth){ return minU; }
	    return recursiveSearch(pt, minU, minPt, (minU+maxU)/2, null, depthCount);
	}
	if(dist2 < IConfig.tolerance || minPt.dist(maxPt) < IConfig.tolerance ||
	   depthCount >= IConfig.cacheRecursionMaxDepth){ return maxU; }
	return recursiveSearch(pt, (minU+maxU)/2, null, maxU, maxPt, depthCount);
    }
    
        
    public int closest(IVec pt){
	int minIdx=-1;
	double dist=0, minDist=0;
	for(int i=0; i<pts.length; i++){
	    dist = pt.dist(pts[i]);
	    if(i==0 || dist<minDist){ minDist = dist; minIdx = i; }
	}
	return minIdx;
    }
    
    public int closest(IVec2 pt){
	int minIdx=-1;
	double dist=0, minDist=0;
	for(int i=0; i<pts2.length; i++){
	    dist = pt.dist(pts2[i]);
	    if(i==0 || dist<minDist){ minDist = dist; minIdx = i; }
	}
	return minIdx;
    }
    
}
