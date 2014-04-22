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
   Point cache of a surface for proximity search.
   
   @author Satoru Sugihara
*/
public class ISurfaceCache{
    
    public ISurfaceGeo surface;
    public int uresolution,vresolution;
    public int udeg, vdeg;
    public IVec[][] pts;
    public IVec2[][] pts2;
    
    public ISurfaceCache(ISurfaceGeo surf, int resolution){
	surface = surf;
	uresolution = resolution;
	vresolution = resolution;
	udeg = surf.udeg();
	vdeg = surf.vdeg();
	//init();
    }
    public ISurfaceCache(ISurfaceGeo surf){
	this(surf,IConfig.surfaceCacheResolution);
    }
    
    public void init(){
	//if(udeg==1){ uresolution=1; }
	//if(vdeg==1){ vresolution=1; }
	int unum = (surface.uepNum()-1)*uresolution + 1;
	int vnum = (surface.vepNum()-1)*vresolution + 1;
	pts = new IVec[unum][vnum];
	int uepnum = surface.uepNum();
	int vepnum = surface.vepNum();
	for(int i=0; i<uepnum; i++){
	    for(int j=0; j<uresolution; j++){
		if(i<uepnum-1 || j==0){
		    for(int k=0; k<vepnum; k++){
			for(int l=0; l<vresolution; l++){
			    if(k<vepnum-1 || l==0){
				pts[i*uresolution+j][k*vresolution+l] =
				    surface.pt(surface.u(i, (double)j/uresolution),
					       surface.v(k, (double)l/vresolution));
			    }
			}
		    }
		}
	    }
	}
    }
    
    public void init2(){
	int unum = (surface.uepNum()-1)*uresolution + 1;
	int vnum = (surface.vepNum()-1)*vresolution + 1;
	pts2 = new IVec2[unum][vnum];
	int uepnum = surface.uepNum();
	int vepnum = surface.vepNum();
	for(int i=0; i<uepnum; i++){
	    for(int j=0; j<uresolution; j++){
		if(i<uepnum-1 || j==0){
		    for(int k=0; k<vepnum; k++){
			for(int l=0; l<vresolution; l++){
			    if(k<vepnum-1 || l==0){
				pts2[i*uresolution+j][k*vresolution+l] =
				    surface.pt(surface.u(i, (double)j/uresolution),
					       surface.v(k, (double)l/vresolution)).to2d();
			    }
			}
		    }
		}
	    }
	}
    }    
    
    
    public double u(int uindex){
	return surface.u(uindex/uresolution, (double)(uindex%uresolution)/uresolution);
    }
    
    public double v(int vindex){
	return surface.v(vindex/vresolution, (double)(vindex%vresolution)/vresolution);
    }
    
    /** center of a cell in pts grid */
    /*
    public IVec centerAt(int uidx, int vidx){
	return pts[uidx][vidx].mid(pts[uidx+1][vidx]).mid(pts[uidx][vidx+1].mid(pts[uidx+1][vidx+1]));
    }
    public int[] getCloserCenter(IVec pt, int uidx1, int vidx1, int uidx2, int vidx2){
	// debug
	//new ICurve(centerAt(uidx1,vidx1),pt).clr(0,0,1.);
	//new ICurve(centerAt(uidx2,vidx2),pt).clr(0,0,1.);
	if(centerAt(uidx1,vidx1).dist(pt) <= centerAt(uidx2,vidx2).dist(pt)){ return new int[]{ uidx1, vidx1 }; }
	return new int[]{ uidx2, vidx2 };
    }
    public int[] getCloserCenter(IVec pt, int uidx1, int vidx1, int uidx2, int vidx2,
				 int uidx3, int vidx3, int uidx4, int vidx4){
	double[] dist = new double[4];
	dist[0] = centerAt(uidx1,vidx1).dist(pt);
	dist[1] = centerAt(uidx2,vidx2).dist(pt);
	dist[2] = centerAt(uidx3,vidx3).dist(pt);
	dist[3] = centerAt(uidx4,vidx4).dist(pt);
	int minIdx=0;
	double minDist=0;
	for(int i=0; i<dist.length; i++){
	    if(i==0 || dist[i]<minDist){ minDist = dist[i]; minIdx=i; }
	}
	if(minIdx==0) return new int[]{ uidx1, vidx1 };
	if(minIdx==1) return new int[]{ uidx2, vidx2 };
	if(minIdx==2) return new int[]{ uidx3, vidx3 };
	return new int[]{ uidx4, vidx4 };
    }
    */
    
    public int[] getCloserTriangle(IVec pt, int uidx, int vidx){
	double[][] dist = new double[2][2];
	
	IVec p0 = pts[uidx][vidx];
	IVec pu1=null, pu2=null, pv1=null, pv2=null;
	
	if(uidx>0) pu1 = pts[uidx-1][vidx];
	if(uidx<pts.length-1) pu2 = pts[uidx+1][vidx];
	if(vidx>0) pv1 = pts[uidx][vidx-1];
	if(vidx<pts[0].length-1) pv2 = pts[uidx][vidx+1];
	
	double minDist=-1;
	int[] minIdx=new int[2];
	if(pu1!=null && pv1!=null){
	    dist[0][0] = pt.distToTriangle(p0,pv1,pu1);
	    minDist = dist[0][0]; minIdx[0]=uidx-1; minIdx[1]=vidx-1;
	}
	
	if(pu2!=null && pv1!=null){
	    dist[1][0] = pt.distToTriangle(p0,pu2,pv1);
	    if(minDist<0 || dist[1][0] < minDist){ minDist = dist[1][0]; minIdx[0]=uidx; minIdx[1]=vidx-1; }
	}
	if(pu1!=null && pv2!=null){
	    dist[0][1] = pt.distToTriangle(p0,pv2,pu1);
	    if(minDist<0 || dist[0][1] < minDist){ minDist = dist[0][1]; minIdx[0]=uidx-1; minIdx[1]=vidx; }
	}
	
	if(pu2!=null && pv2!=null){
	    dist[1][1] = pt.distToTriangle(p0,pu2,pv2);
	    if(minDist<0 || dist[1][1] < minDist){ minDist = dist[1][1]; minIdx[0]=uidx; minIdx[1]=vidx; }
	}
	return minIdx;
    }

    public int[] getCloserTriangle(IVec2 pt, int uidx, int vidx){
	double[][] dist = new double[2][2];
	
	IVec2 p0 = pts2[uidx][vidx];
	IVec2 pu1=null, pu2=null, pv1=null, pv2=null;
	
	if(uidx>0) pu1 = pts2[uidx-1][vidx];
	if(uidx<pts2.length-1) pu2 = pts2[uidx+1][vidx];
	if(vidx>0) pv1 = pts2[uidx][vidx-1];
	if(vidx<pts2[0].length-1) pv2 = pts2[uidx][vidx+1];
	
	double minDist=-1;
	int[] minIdx=new int[2];
	if(pu1!=null && pv1!=null){
	    dist[0][0] = pt.distToTriangle(p0,pv1,pu1);
	    minDist = dist[0][0]; minIdx[0]=uidx-1; minIdx[1]=vidx-1;
	}
	if(pu2!=null && pv1!=null){
	    dist[1][0] = pt.distToTriangle(p0,pu2,pv1);
	    if(minDist<0 || dist[1][0] < minDist){ minDist = dist[1][0]; minIdx[0]=uidx; minIdx[1]=vidx-1; }
	}
	if(pu1!=null && pv2!=null){
	    dist[0][1] = pt.distToTriangle(p0,pv2,pu1);
	    if(minDist<0 || dist[0][1] < minDist){ minDist = dist[0][1]; minIdx[0]=uidx-1; minIdx[1]=vidx; }
	}
	if(pu2!=null && pv2!=null){
	    dist[1][1] = pt.distToTriangle(p0,pu2,pv2);
	    if(minDist<0 || dist[1][1] < minDist){ minDist = dist[1][1]; minIdx[0]=uidx; minIdx[1]=vidx; }
	}
	return minIdx;
    }
    
    
    /** approximate invert projection from 3D location to interanl parameter U (closest point on curve) */
    public IVec2 uv(IVec pt){
	if(pts==null) init();
	int[] idx = closest(pt);
	int[] cellIdx = getCloserTriangle(pt, idx[0], idx[1]);
	double minU = u(cellIdx[0]);
	double minV = v(cellIdx[1]);
	double maxU = u(cellIdx[0]+1);
	double maxV = v(cellIdx[1]+1);
	//double midU = (minU+maxU)/2;
	//double midV = (minV+maxV)/2;
	//if(cellIdx[0] < idx[0]){ minU = midU; } else{ maxU = midU; }
	//if(cellIdx[1] < idx[1]){ minV = midV; } else{ maxV = midV; }
	return recursiveSearch(pt,minU,minV,maxU,maxV,null,0);
    }
    
    
    
    
    /** approximate invert projection from 3D location to interanl parameter U (closest point on curve) */
    public IVec2 uv(IVec2 pt){
	if(pts2==null) init2();
	
	int[] idx = closest(pt);
	int[] cellIdx = getCloserTriangle(pt, idx[0], idx[1]);
	
	double minU = u(cellIdx[0]);
	double minV = v(cellIdx[1]);
	double maxU = u(cellIdx[0]+1);
	double maxV = v(cellIdx[1]+1);
	//double midU = (minU+maxU)/2;
	//double midV = (minV+maxV)/2;
	//if(cellIdx[0] < idx[0]){ minU = midU; } else{ maxU = midU; }
	//if(cellIdx[1] < idx[1]){ minV = midV; } else{ maxV = midV; }
	return recursiveSearch(pt,minU,minV,maxU,maxV,null,0);
    }
    
    public IVec2 recursiveSearch(IVec pt, double minU, double minV, double maxU, double maxV,
				 IVec[][] cornerPts, int depthCount){
	
	/*
	if(cornerPts==null){ cornerPts = new IVec[2][2]; }
	if(cornerPts[0][0]==null){ cornerPts[0][0] = surface.pt(minU,minV); }
	if(cornerPts[1][0]==null){ cornerPts[1][0] = surface.pt(maxU,minV); }
	if(cornerPts[0][1]==null){ cornerPts[0][1] = surface.pt(minU,maxV); }
	if(cornerPts[1][1]==null){ cornerPts[1][1] = surface.pt(maxU,maxV); }
	*/
	
	double midU1 = (3*minU+maxU)/4;
	double midU2 = (minU+3*maxU)/4;
	double midV1 = (3*minV+maxV)/4;
	double midV2 = (minV+3*maxV)/4;
	
	
	IVec[][] midPts = new IVec[2][2];
	midPts[0][0] = surface.pt(midU1,midV1);
	midPts[1][0] = surface.pt(midU2,midV1);
	midPts[0][1] = surface.pt(midU1,midV2);
	midPts[1][1] = surface.pt(midU2,midV2);
	
	// debug
	//new ICurve(new IVec[]{ cornerPts[0][0], cornerPts[1][0], cornerPts[1][1], cornerPts[0][1] },true).clr(1.0);//
	//new ICurve(new IVec[]{ midPts[0][0], midPts[1][0], midPts[1][1], midPts[0][1] },true).clr(1.0);//
	
	double minDist=0;
	int minUIdx=0, minVIdx=0;
	for(int i=0; i<2; i++){
	    for(int j=0; j<2; j++){
		//double dist = cornerPts[i][j].dist(pt);
		double dist = midPts[i][j].dist(pt);
		if( (i==0&&j==0) || dist < minDist){
		    minDist = dist;
		    minUIdx=i; minVIdx=j;
		}
	    }
	}

	/*
	boolean cornerCloseEnough =
	    cornerPts[0][0].dist(cornerPts[1][1])<IConfig.tolerance &&
	    cornerPts[1][0].dist(cornerPts[0][1])<IConfig.tolerance ;
	*/
	boolean cornerCloseEnough =
	    midPts[0][0].dist(midPts[1][1])<IConfig.tolerance && midPts[1][0].dist(midPts[0][1])<IConfig.tolerance ;
	
	double midU = (minU+maxU)/2;
	double midV = (minV+maxV)/2;
	
	depthCount++;
	if(minUIdx==0){
	    if(minVIdx==0){
		if(minDist<IConfig.tolerance || cornerCloseEnough || depthCount>=IConfig.cacheRecursionMaxDepth) return new IVec2(minU,minV);
		//cornerPts[0][1]=cornerPts[1][0]=cornerPts[1][1]=null;
		//return recursiveSearch(pt, minU, minV, midU,midV, cornerPts, depthCount);
		return recursiveSearch(pt, minU, minV, midU,midV, null, depthCount);
	    }
	    if(minDist<IConfig.tolerance || cornerCloseEnough || depthCount>=IConfig.cacheRecursionMaxDepth) return new IVec2(minU,maxV);
	    //cornerPts[0][0]=cornerPts[1][0]=cornerPts[1][1]=null;
	    //return recursiveSearch(pt, minU, midV, midU, maxV, cornerPts, depthCount);
	    return recursiveSearch(pt, minU, midV, midU, maxV, null, depthCount);
	}
	
	if(minVIdx==0){
	    if(minDist<IConfig.tolerance || cornerCloseEnough || depthCount>=IConfig.cacheRecursionMaxDepth) return new IVec2(maxU,minV);
	    //cornerPts[0][1]=cornerPts[0][0]=cornerPts[1][1]=null;
	    //return recursiveSearch(pt, midU, minV, maxU,midV, cornerPts, depthCount);
	    return recursiveSearch(pt, midU, minV, maxU,midV, null, depthCount);
	}
	if(minDist<IConfig.tolerance || cornerCloseEnough || depthCount>=IConfig.cacheRecursionMaxDepth) return new IVec2(maxU,maxV);
	//cornerPts[0][1]=cornerPts[1][0]=cornerPts[0][0]=null;
	//return recursiveSearch(pt, midU, midV, maxU,maxV, cornerPts, depthCount);
	return recursiveSearch(pt, midU, midV, maxU,maxV, null, depthCount);
	
    }
    


    public IVec2 recursiveSearch(IVec2 pt, double minU, double minV, double maxU, double maxV,
				 IVec2[][] cornerPts, int depthCount){
	
	double midU1 = (3*minU+maxU)/4;
	double midU2 = (minU+3*maxU)/4;
	double midV1 = (3*minV+maxV)/4;
	double midV2 = (minV+3*maxV)/4;
	
	IVec2[][] midPts = new IVec2[2][2];
	midPts[0][0] = surface.pt(midU1,midV1).to2d();
	midPts[1][0] = surface.pt(midU2,midV1).to2d();
	midPts[0][1] = surface.pt(midU1,midV2).to2d();
	midPts[1][1] = surface.pt(midU2,midV2).to2d();
	
	double minDist=0;
	int minUIdx=0, minVIdx=0;
	for(int i=0; i<2; i++){
	    for(int j=0; j<2; j++){
		double dist = midPts[i][j].dist(pt);
		if( (i==0&&j==0) || dist < minDist){
		    minDist = dist; minUIdx=i; minVIdx=j;
		}
	    }
	}
	
	boolean cornerCloseEnough =
	    midPts[0][0].dist(midPts[1][1])<IConfig.tolerance && midPts[1][0].dist(midPts[0][1])<IConfig.tolerance ;
	double midU = (minU+maxU)/2;
	double midV = (minV+maxV)/2;
	
	depthCount++;
	if(minUIdx==0){
	    if(minVIdx==0){
		if(minDist<IConfig.tolerance || cornerCloseEnough || depthCount>=IConfig.cacheRecursionMaxDepth) return new IVec2(minU,minV);
		return recursiveSearch(pt, minU, minV, midU,midV, null, depthCount);
	    }
	    if(minDist<IConfig.tolerance || cornerCloseEnough || depthCount>=IConfig.cacheRecursionMaxDepth) return new IVec2(minU,maxV);
	    return recursiveSearch(pt, minU, midV, midU, maxV, null, depthCount);
	}
	
	if(minVIdx==0){
	    if(minDist<IConfig.tolerance || cornerCloseEnough || depthCount>=IConfig.cacheRecursionMaxDepth) return new IVec2(maxU,minV);
	    return recursiveSearch(pt, midU, minV, maxU,midV, null, depthCount);
	}
	if(minDist<IConfig.tolerance || cornerCloseEnough || depthCount>=IConfig.cacheRecursionMaxDepth) return new IVec2(maxU,maxV);
	return recursiveSearch(pt, midU, midV, maxU,maxV, null, depthCount);
	
    }
    
    
    public int[] closest(IVec pt){
	int minIdxU=-1, minIdxV=-1;
	double dist=0, minDist=0;
	for(int i=0; i<pts.length; i++){
	    for(int j=0; j<pts[i].length; j++){
		dist = pt.dist(pts[i][j]);
		if(i==0&&j==0 || dist<minDist){
		    minDist = dist;
		    minIdxU = i;
		    minIdxV = j;
		}
	    }
	}
	return new int[]{ minIdxU, minIdxV };
    }
    
    public int[] closest(IVec2 pt){
	int minIdxU=-1, minIdxV=-1;
	double dist=0, minDist=0;
	for(int i=0; i<pts2.length; i++){
	    for(int j=0; j<pts2[i].length; j++){
		dist = pt.dist(pts2[i][j]);
		if(i==0&&j==0 || dist<minDist){
		    minDist = dist; minIdxU = i; minIdxV = j;
		}
	    }
	}
	return new int[]{ minIdxU, minIdxV };
    }
    
}
