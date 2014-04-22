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

/**
   Class to calculate Delaunay triangulation out of 2D points.
   
   @author Satoru Sugihara
*/
public class IVoronoi2D {
    
    /**
       Getting voronoi polygons out of array of 2D points
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVec2[][] getPolygons(IVec2[] pts){
	
	IVec2[][] tri = IDelaunay2D.getTriangles(pts);
	IVec2[] cnt = new IVec2[tri.length];
	for(int i=0; i<tri.length; i++){
	    cnt[i] = circumcenter(tri[i]);
	    /*
	    // debug
	    new ICurve(c.to3d(),tri[i][0].mid(tri[i][1]).to3d()).clr(0,1,1.);
	    new ICurve(c.to3d(),tri[i][1].mid(tri[i][2]).to3d()).clr(0,1,1.);
	    new ICurve(c.to3d(),tri[i][2].mid(tri[i][0]).to3d()).clr(0,1,1.);
	    //new ICurve(tri[i],true);
	    new ICurve(IG.v(tri[i][0].to3d(),tri[i][1].to3d(),tri[i][2].to3d()),true);
	    */
	}
	
	IVec2[][] cellPts = new IVec2[pts.length][];
	
	for(int i=0; i<pts.length; i++){
	    
	    ArrayList<IVec2> cpts = new ArrayList<IVec2>();
	    
	    for(int j=0; j<tri.length; j++){
		boolean match = false;
		for(int k=0; k<tri[j].length && cnt[j]!=null && !match; k++){
		    if(pts[i]==tri[j][k]){
			cpts.add(cnt[j]); match=true;
			/*
			if(!cpts.contains(tri[j][(k-1+tri[j].length)%tri[j].length])){
			    cpts.add(tri[j][(k-1+tri[j].length)%tri[j].length]);
			}
			if(!cpts.contains(tri[j][(k+1)%tri[j].length])){
			    cpts.add(tri[j][(k+1)%tri[j].length]);
			}
			*/
		    }
		}
	    }
	    ISort.sort(cpts, new RadialComparator(pts[i]));
	    cellPts[i] = cpts.toArray(new IVec2[cpts.size()]);
	}
	
	return cellPts;
    }
    
    
    /**
       Getting voronoi polygons out of array of 2D points.
       @param minUV minimum corner of boundary rectangle
       @param maxUV maximum corner of boundary rectangle
       @return array of polygons and triangles; IVec2[0] is polygons and IVec[1] is triangles.
    */
    public static IVec2[][] getPolygons(IVec2[] pts,
					IVec2 minUV, IVec2 maxUV){
	//IVec2[] boundary){ // irregular boundary will be implemented later.
	
	IVec2[][] tri = IDelaunay2D.getTriangles(pts);
	IVec2[] cnt = new IVec2[tri.length];
	for(int i=0; i<tri.length; i++){ cnt[i] = circumcenter(tri[i]); }
	
	IVec2[][] cellPts = new IVec2[pts.length][];

	IVec2[][][] connectedTriangles = new IVec2[pts.length][][];
	
	for(int i=0; i<pts.length; i++){
	    
	    //ArrayList<IVec2> cpts = new ArrayList<IVec2>();
	    ArrayList<IVec2[]> triangles = new ArrayList<IVec2[]>();
	    
	    for(int j=0; j<tri.length; j++){
		boolean match = false;
		for(int k=0; k<tri[j].length && !match; k++){
		    if(pts[i]==tri[j][k]){ match=true; }
		}
		if(match) triangles.add(tri[j]);
	    }
	    
	    ArrayList<IVec2> edgePts = sortTriangleEdgePoint(pts[i],triangles);
	    int ednum = edgePts.size();
	    if(edgePts.get(0)==edgePts.get(ednum-1)){ // closed
		IVec2[] circumcenters = new IVec2[ednum-1];
		for(int j=0; j<ednum-1; j++){
		    circumcenters[j] = circumcenter(pts[i],edgePts.get(j),edgePts.get(j+1));
		}
		//cellPts[i] = circumcenters;
		cellPts[i] = pointsInsideBoundary(circumcenters, minUV, maxUV,true);
		
		//cellPts[i] = null; // for debug
	    }
	    else{ // open
		if(ednum>=3){
		IVec2[] circumcenters = new IVec2[ednum-1+2];
		/*
		for(int j=0; j<edgePts.size()-1; j++){
		    circumcenters[j] = circumcenter(pts[i],edgePts.get(j),edgePts.get(j+1));
		}
		//cellPts[i] = closeWithBoundary(circumcenters, boundary);
		//cellPts[i] = closeWithBoundary(circumcenters, minUV,maxUV);
		cellPts[i]=circumcenters;
		*/
		
		for(int j=0; j<ednum-1; j++){
		    circumcenters[j+1] = circumcenter(pts[i],edgePts.get(j),edgePts.get(j+1));
		}
		
		
		//if(insideTriangle(circumcenters[1], pts[i],edgePts.get(0),edgePts.get(1))){
		IVec2 cdir1 = pts[i].mid(edgePts.get(0)).dif(circumcenters[1]);
		if(cdir1.dot(edgePts.get(0).dif(edgePts.get(1)))<0) cdir1.rev();
		circumcenters[0] = circumcenters[1].cp(cdir1);
		//}
		//else{
		//circumcenters[0] = pts[i].mid(edgePts.get(0)).dif(circumcenters[1]).rev().add(circumcenters[1]);
		//}
		
		//if(insideTriangle(circumcenters[ednum-1], pts[i],edgePts.get(ednum-2),edgePts.get(ednum-1))){
		IVec2 cdir2 = pts[i].mid(edgePts.get(ednum-1)).dif(circumcenters[ednum-1]);
		if(cdir2.dot(edgePts.get(ednum-1).dif(edgePts.get(ednum-2)))<0) cdir2.rev();
		circumcenters[ednum] = circumcenters[ednum-1].cp(cdir2);
		//}
		//else{
		//circumcenters[ednum] = pts[i].mid(edgePts.get(ednum-1)).dif(circumcenters[ednum-1]).rev().add(circumcenters[ednum-1]);
		//}
		
		//cellPts[i] = circumcenters;
		//cellPts[i] = pointsInsideBoundary(circumcenters, minUV, maxUV,false);
		//cellPts[i] = closeWithBoundary(circumcenters, minUV,maxUV);
		
		cellPts[i] = removeDuplicatedPoint(closeWithBoundary(circumcenters, minUV,maxUV),
						   IConfig.parameterTolerance);
		}
		else{
		    cellPts[i]=null;
		}
	    }
	    
	    //ISort.sort(cpts, new RadialComparator(pts[i]));
	    //cellPts[i] = cpts.toArray(new IVec2[cpts.size()]);
	}
	return cellPts;
    }


    public static IVec2[][] getEdges(IVec2[] pts){
	IVec2[][] poly = getPolygons(pts);
	ArrayList<IVec2[]> pairs = new ArrayList<IVec2[]>();
	for(int i=0; i<poly.length; i++){
	    for(int j=0; j<poly[i].length; j++){
		boolean exists=false;
		for(int k=0; k<pairs.size()&&!exists; k++){
		    if(pairs.get(k)[0] == poly[i][j] &&
		       pairs.get(k)[1] == poly[i][(j+1)%poly[i].length] ||
		       pairs.get(k)[1] == poly[i][j] &&
		       pairs.get(k)[0] == poly[i][(j+1)%poly[i].length]){
			exists=true;
		    }
		}
		if(!exists){
		    pairs.add(new IVec2[]{ poly[i][j], poly[i][(j+1)%poly[i].length] });
		}
	    }
	}
	return pairs.toArray(new IVec2[pairs.size()][]);
    }
    

    
    public static IVec2[] removeDuplicatedPoint(IVec2[] pts, double tolerance){
	if(pts==null) return null;
	ArrayList<IVec2> pts2 = new ArrayList<IVec2>();
	for(int i=0; i<pts.length; i++){ // closed
	    if(i==0 ||
	       i>0 && i<pts.length-1 &&
	       !pts2.get(pts2.size()-1).eq(pts[i],tolerance) ||
	       i==pts.length-1 &&
	       !pts2.get(pts2.size()-1).eq(pts[i],tolerance) &&
	       !pts2.get(0).eq(pts[i],tolerance) ){
		pts2.add(pts[i]);
	    }
	}
	return pts2.toArray(new IVec2[pts2.size()]);
    }
    
    public static boolean insideTriangle(IVec2 pt, IVec2 tr1, IVec2 tr2, IVec2 tr3){
	IVec2 dir = pt.dif(tr1);
	IVec2 dir1 = tr2.dif(tr1);
	IVec2 dir2 = tr3.dif(tr1);
	
	double[] coef = dir.projectTo2Vec(dir1,dir2);
	return coef[0]>=0 && coef[0]<=1 && coef[1]>=0 && coef[1]<=1;
    }
    
    public static IVec2 intersectWithBoundary(IVec2 ptIn, IVec2 ptOut, IVec2[] boundary){
	IVec2 pt1 = ptIn;
	IVec2 dir = ptOut.dif(ptIn);
	
	IVec2 isct = null;
	double idist = 0;
	int iidx = -1;
	int bnum = boundary.length;
	for(int i=0; i<bnum; i++){
	    IVec2 bpt = boundary[i];
	    IVec2 bdir = boundary[(i+1)%bnum].dif(boundary[i]);
	    IVec2 is = IVec2.intersect(pt1,dir,bpt,bdir);
	    if(is!=null &&
	       is.dif(pt1).dot(dir) >= -IConfig.parameterTolerance && is.dist(pt1) <= dir.len()+IConfig.parameterTolerance && /* within pt1,pt2 */
	       is.dif(bpt).dot(bdir) >= -IConfig.parameterTolerance &&
	       is.dist(bpt) <= bdir.len()+IConfig.parameterTolerance){ /* within boundary segment */
		double d = is.dist(pt1);
		if(isct==null || d<idist){ isct=is; idist=d; iidx=i; } // min dist
	    }
	}
	if(isct==null){ IOut.err("no intersection found"); }
	return isct;
    }
    
    public static boolean insideBoundary(IVec2 pt, IVec2 minUV, IVec2 maxUV){
	return pt.x >= minUV.x && pt.x <= maxUV.x && pt.y >= minUV.y && pt.y <= maxUV.y;
    }
    
    
    /** check all points in pts is in boundary and if not, remove and insert an intersection point with the boundary */
    public static IVec2[] pointsInsideBoundary(IVec2[] pts, IVec2 minUV, IVec2 maxUV, boolean close){
	//IVec2[] boundary){
	IVec2[] boundary = new IVec2[4];
	boundary[0] = minUV;
	boundary[1] = minUV.dup().x(maxUV.x);
	boundary[2] = maxUV;
	boundary[3] = maxUV.dup().x(minUV.x);
	
	int num = pts.length;
	ArrayList<IVec2> pts2 = new ArrayList<IVec2>();
	IVec2 isct=null;
	boolean prevOut=false;
	if(close) prevOut=!insideBoundary(pts[num-1],minUV,maxUV);
	for(int i=0; i<num; i++){
	    if(insideBoundary(pts[i],minUV,maxUV)){
		if(!prevOut){ pts2.add(pts[i]); }
		else{
		    isct = intersectWithBoundary(pts[i], pts[(i-1+num)%num], boundary);
		    if(isct!=null){
			if(pts2.size()==0 ||
			   !isct.eq(pts2.get(pts2.size()-1),IConfig.parameterTolerance)){
			    pts2.add(isct);
			}
		    }
		    if(isct==null || !isct.eq(pts[i],IConfig.parameterTolerance)){
			pts2.add(pts[i]);
		    }
		    prevOut=false;
		}
	    }
	    else{
		if(close&&!prevOut || !close&&i>0&&!prevOut){
		    isct = intersectWithBoundary(pts[(i-1+num)%num], pts[i], boundary);
		    if(isct!=null){
			if(pts2.size()==0 ||
			   !isct.eq(pts2.get(pts2.size()-1),IConfig.parameterTolerance)){
			    pts2.add(isct);
			}			
		    }
		}
		prevOut=true;
	    }
	}

	// when closed, could it have duplicated point in the first and the end?
	
	return pts2.toArray(new IVec2[pts2.size()]); 
    }
    
    
    public static IVec2[] closeWithBoundary(IVec2[] openPts, IVec2 minUV, IVec2 maxUV){ //IVec2[] boundary){
	if(openPts==null||openPts.length<=1) return null;
	
	IVec2[] boundary = new IVec2[4];
	boundary[0] = minUV;
	boundary[1] = minUV.dup().x(maxUV.x);
	boundary[2] = maxUV;
	boundary[3] = maxUV.dup().x(minUV.x);
	
	// check all points in openPts is in boundary
	openPts = pointsInsideBoundary(openPts, minUV, maxUV, false);
	if(openPts==null||openPts.length<=1) return null;
	
	IVec2 pt1 = openPts[0];
	IVec2 dir1 = openPts[0].dif(openPts[1]);
	IVec2 pt2 = openPts[openPts.length-1];
	IVec2 dir2 = openPts[openPts.length-1].dif(openPts[openPts.length-2]);
	
	IVec2 isct1 = null, isct2 = null;
	double idist1 = 0, idist2 = 0;
	int iidx1 = -1, iidx2 = -1;
	int bnum = boundary.length;
	for(int i=0; i<bnum; i++){
	    IVec2 bpt = boundary[i];
	    IVec2 bdir = boundary[(i+1)%bnum].dif(boundary[i]);
	    IVec2 is1 = IVec2.intersect(pt1,dir1,bpt,bdir);
	    if(is1!=null &&
	       is1.dif(openPts[1]).dot(dir1) >= 0 && /* same direction */
	       is1.dif(bpt).dot(bdir) >= -IConfig.parameterTolerance &&
	       is1.dist(bpt) <= bdir.len()+IConfig.parameterTolerance){ /* within boundary segment */
		double d1 = is1.dist(pt1);
		if(isct1==null || d1<idist1){ isct1=is1; idist1=d1; iidx1=i; } // min dist
	    }
	    
	    IVec2 is2 = IVec2.intersect(pt2,dir2,bpt,bdir);
	    if(is2!=null &&
	       is2.dif(openPts[openPts.length-2]).dot(dir2) >= 0 && /* same direction */
	       is2.dif(bpt).dot(bdir) >= -IConfig.parameterTolerance && is2.dist(bpt) <= bdir.len() +IConfig.parameterTolerance){ /* within boundary segment */
		double d2 = is2.dist(pt2);
		if(isct2==null || d2<idist2){ isct2=is2; idist2=d2; iidx2=i; } // min dist
	    }
	}
	
	if(isct1==null || isct2==null){
	    IOut.err("no intersection found. original points returned.");
	    return openPts;
	}

	if(iidx1==iidx2){
	    IVec2[] retval = new IVec2[openPts.length+2];
	    retval[0] = isct1;
	    for(int i=0; i<openPts.length; i++){ retval[i+1]=openPts[i]; }
	    retval[openPts.length+1] = isct2;
	    return retval;
	}
	
	IVec2 center = new IVec2();
	for(int i=0; i<openPts.length; i++) center.add(openPts[i]);
	center.div(openPts.length);
	
	IVec2 idir = isct1.dif(center).bisect(isct2.dif(center)).unit();
	
	double dot1=0, dot2=0;
	for(int i=0; i<bnum; i++){
	    double d = boundary[i].dif(center).dot(idir);;
	    if(iidx1<iidx2){
		if(i > iidx1 && i <= iidx2){ dot1 += d; }
		else{ dot2 += d; }
	    }
	    else{ // iidx1 > iidx2
		if(i > iidx2 && i <= iidx1){ dot2 += d; }
		else{ dot1 += d; }
	    }
	}
	
	ArrayList<IVec2> insertPts = new ArrayList<IVec2>();
	if(dot1 >= dot2){
	    if(iidx1<iidx2){
		for(int i=iidx1+1; i<=iidx2; i++){ insertPts.add(boundary[i]); }
	    }
	    else{
		for(int i=iidx1+1; i<bnum; i++){ insertPts.add(boundary[i]); }
		for(int i=0; i<=iidx2; i++){ insertPts.add(boundary[i]); }
	    }
	}
	else{
	    if(iidx1>iidx2){
		for(int i=iidx1; i>iidx2; i--){ insertPts.add(boundary[i]); }
	    }
	    else{
		for(int i=iidx1; i>=0; i--){ insertPts.add(boundary[i]); }
		for(int i=bnum-1; i>iidx2; i--){ insertPts.add(boundary[i]); }
	    }
	}
	
	
	
	
	if(insertPts.size()>0 &&
	   insertPts.get(insertPts.size()-1).eq(isct1,IConfig.parameterTolerance)){
		insertPts.remove(insertPts.size()-1);
	}
	if(insertPts.size()>0 && insertPts.get(0).eq(isct2,IConfig.parameterTolerance)){
	    insertPts.remove(0);
	}
	if(isct1.eq(openPts[0],IConfig.parameterTolerance)){
	    isct1 = null;
	}
	if(isct2.eq(openPts[openPts.length-1],IConfig.parameterTolerance)){
	    isct2 = null;
	}
	
	int isctNum=0;
	if(isct1!=null) isctNum++;
	if(isct2!=null) isctNum++;
	
	IVec2[] retval = new IVec2[openPts.length+isctNum+insertPts.size()];
	int idx=0;
	for(int i=0; i<insertPts.size(); idx++,i++){ retval[idx] = insertPts.get(i); }
	if(isct1!=null){ retval[idx] = isct1; idx++; }
	for(int i=0; i<openPts.length; idx++, i++){ retval[idx]=openPts[i]; }
	if(isct2!=null){ retval[idx] = isct2; }
	
	return retval;
    }
    
    /** when a point is surrounded by triangles, sort triangles in the order they are touching. 
	The sort direction is not defined. It can be either clockwise or counterclockwise.
	@return sorted array of edge points. if it's closed, the first and the last points are same
    */
    public static ArrayList<IVec2> sortTriangleEdgePoint(IVec2 center, ArrayList<IVec2[]> triangles){
	if(triangles==null || triangles.size()==0) return null;
	
	ArrayList<IVec2> edgePtList = new ArrayList<IVec2>();
	boolean[] checked = new boolean[triangles.size()];
	int checkedNum=0;
	
	IVec2[] edgePt = edgePoint(center, triangles.get(0));
	edgePtList.add(edgePt[0]);
	edgePtList.add(edgePt[1]);
	checked[0]=true;
	checkedNum++;
	
	boolean connectionFound=true;
	while(checkedNum<triangles.size() && connectionFound){
	    connectionFound=false;
	    for(int i=0; i<triangles.size()&&!connectionFound; i++){
		if(!checked[i]){
		    edgePt = edgePoint(center, triangles.get(i));
		    if(edgePt!=null){
			if(edgePt[0] == edgePtList.get(0)){
			    edgePtList.add(0,edgePt[1]); // insert at the head
			    connectionFound=true;
			    checked[i]=true;
			    checkedNum++;
			}
			else if(edgePt[0] == edgePtList.get(edgePtList.size()-1)){
			    edgePtList.add(edgePt[1]); // add at the tail
			    connectionFound=true;
			    checked[i]=true;
			    checkedNum++;
			}
			else if(edgePt[1] == edgePtList.get(0)){
			    edgePtList.add(0,edgePt[0]); // insert at the head
			    connectionFound=true;
			    checked[i]=true;
			    checkedNum++;
			}
			else if(edgePt[1] == edgePtList.get(edgePtList.size()-1)){
			    edgePtList.add(edgePt[0]); // add at the tail
			    connectionFound=true;
			    checked[i]=true;
			    checkedNum++;
			}
		    }
		}
	    }
	}
	
	if(checkedNum<triangles.size()){
	    IOut.err("some triangles are separated");
	}
	
	return edgePtList;
	/*
	if(edgePtList.get(0) == edgePtList.get(edgePtList.size()-1)){
	    // closed
	    return null;
	}
	return new IVec2[]{ edgePtList.get(0), edgePtList.get(edgePtList.size()-1) };
	*/
    }
    
    
    /** get two other edge points of a triangle other than the given vertex */
    public static IVec2[] edgePoint(IVec2 vertex, IVec2[] triangle){
	if(triangle==null||triangle.length!=3){
	    IOut.err("input is not a triangle");
	    return null;
	}
	if(vertex==triangle[0]) return new IVec2[]{ triangle[1], triangle[2] };
	if(vertex==triangle[1]) return new IVec2[]{ triangle[2], triangle[0] };
	if(vertex==triangle[2]) return new IVec2[]{ triangle[0], triangle[1] };
	IOut.err("the vertex is not included in the triangle"); 
	return null;
    }
    
    public static IVec2 circumcenter(IVec2 t1, IVec2 t2, IVec2 t3){
	IVec2 m1 = t1.mid(t2);
	IVec2 m2 = t2.mid(t3);
	IVec2 dir1 = t2.dif(t1);
	IVec2 dir2 = t3.dif(t2);
	dir1.rot(Math.PI/2);
	dir2.rot(Math.PI/2);
	return IVec2.intersect(m1,dir1,m2,dir2);
    }
    
    public static IVec2 circumcenter(IVec2[] t){
	if(t.length!=3) return null;
	return circumcenter(t[0],t[1],t[2]);
	/*
	IVec2 m1 = t[0].mid(t[1]);
	IVec2 m2 = t[1].mid(t[2]);
	IVec2 dir1 = t[1].dif(t[0]);
	IVec2 dir2 = t[2].dif(t[1]);
	dir1.rot(Math.PI/2);
	dir2.rot(Math.PI/2);
	return IVec2.intersect(m1,dir1,m2,dir2);
	*/
    }
    
    /*
    public static IVec2 orthocenter(IVec2[] t){
	if(t.length!=3) return null;
	IMatrix3 m0 = new IMatrix3(t[0].x, t[0].y, 1,
				   t[1].x, t[1].y, 1,
				   t[2].x, t[2].y, 1);
	double det = m0.determinant();
	IMatrix3 m1 = new IMatrix3(-t[1].x*t[2].x-t[0].y*t[0].y, t[0].y, 1,
				   -t[0].x*t[2].x-t[1].y*t[1].y, t[1].y, 1,
				   -t[0].x*t[1].x-t[2].y*t[2].y, t[2].y, 1);
	IMatrix3 m2 = new IMatrix3(t[0].x, -t[0].x*t[0].x-t[1].y*t[2].y, 1,
				   t[1].x, -t[1].x*t[1].x-t[0].y*t[2].y, 1,
				   t[2].x, -t[2].x*t[2].x-t[0].y*t[1].y, 1);
	
	return new IVec2(m1.determinant()/det, m2.determinant()/det);
    }
    */
    
    
    public static class RadialComparator implements IComparator<IVec2>{
	IVec2 center;
	public RadialComparator(IVec2 center){ this.center = center; }
	public int compare(IVec2 v1, IVec2 v2){ // return >0, <0, ==0
	    final IVec2 yaxis = new IVec2(0,1);
	    double a1 = v1.dif(center).angle(yaxis);
	    double a2 = v2.dif(center).angle(yaxis);
	    if(a1 < a2) return -1;
	    if(a1 > a2) return 1;
	    return 0;
	}
	
    }
    
    
}
    
    
