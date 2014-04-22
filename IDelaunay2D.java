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
   Class to calculate Delaunay triangulation out of 2D points.
   
   @author Satoru Sugihara
*/
public class IDelaunay2D {
    
    public static double maxDistToCheck = -1;
    
    /**
       Calculates Delaunay triangles out of array of 2D points
       
       @param innerPts points inside edges defined by edgePts
       @param edgePts points on the edge (the edge should be naked having only one triangle touching); order of edge points needs to be counter clockwise for outer trim loop, clockwise for inner hole trim loop.
       @return array of triangles, which consist of array of 3 points of IVec2
       
    */
    public static IVec2[][] getTriangles(IVec2[] innerPts, IVec2[][] edgePts){
	
	int innerPtsNum = 0;
	int edgePtsNum = 0;
	if(innerPts!=null) innerPtsNum=innerPts.length;
	if(edgePts!=null){
	    for(int i=0; i<edgePts.length; i++) edgePtsNum+=edgePts[i].length;
	}
	
	IVec2[] pts = new IVec2[innerPtsNum+edgePtsNum];
	if(innerPts!=null){
	    for(int i=0; i<innerPts.length; i++){
		pts[i] = innerPts[i];
		//IOut.p("pts["+i+"] is innerPts "+innerPts[i]); //
	    }
	}
	
	int[][] edgePtIdx = null;
	if(edgePts!=null){
	    edgePtIdx = new int[edgePts.length][];
	    int ptIdx = innerPtsNum;
	    for(int i=0; i<edgePts.length; i++){
		edgePtIdx[i] = new int[edgePts[i].length];
		for(int j=0; j<edgePts[i].length; j++){
		    //IOut.p("ptIdx="+ptIdx+" / pts.length="+pts.length); //
		    //IOut.p("pts["+ptIdx+"] is edgePts "+edgePts[i][j]); //
		    pts[ptIdx] = edgePts[i][j];
		    edgePtIdx[i][j] = ptIdx;
		    ptIdx++;
		}
	    }
	}
	
	if(pts.length==3){
	    if(isClockwise(pts[0],pts[1],pts[2]))
		return new IVec2[][]{ new IVec2[]{ pts[0],pts[2],pts[1] } };
	    return new IVec2[][]{ new IVec2[]{ pts[0],pts[1],pts[2] } };
	}
	
	ArrayList<IVec2[]> triangles = new ArrayList<IVec2[]>();

	ArrayList<FaceIndex> triangleIndex = new ArrayList<FaceIndex>();
	
	
	//IOut.p("pts.length = "+pts.length); //
	//for(int i=0; i<pts.length; i++) IOut.p("pts["+i+"] = "+ pts[i]); //
	
	EdgeCounter edgeCount = new EdgeCounter(pts.length);
	
	for(int i=0; i<edgePtIdx.length; i++){
	    for(int j=0; j<edgePtIdx[i].length; j++){
		edgeCount.addEdge(edgePtIdx[i][j],edgePtIdx[i][(j+1)%edgePtIdx[i].length]);
	    }
	}
	
	
	// in case a point on edges are on other loop's edge
	for(int i=0; i<edgePtIdx.length; i++){
	    for(int j=0; j<edgePtIdx[i].length; j++){
		int ept1 = edgePtIdx[i][j];
		int ept2 = edgePtIdx[i][(j+1)%edgePtIdx[i].length];
		for(int k=0; k<edgePtIdx.length; k++){
		    if(k!=i){
			for(int l=0; l<edgePtIdx[k].length; l++){
			    if(pts[edgePtIdx[k][l]].isStraight(pts[ept1],pts[ept2]) &&
			       pts[edgePtIdx[k][l]].isBetween(pts[ept1],pts[ept2])){
				edgeCount.addEdge(ept1, edgePtIdx[k][l]);
				edgeCount.addEdge(ept2, edgePtIdx[k][l]);
			    }
			}
		    }
		}
	    }
	}
	
	
	for(int i=0; i<pts.length-2; i++){

	    //IOut.p((i+1)+"/"+(pts.length-2)); //
	    
	    for(int j=i+1; j<pts.length-1; j++){
		
		for(int k=j+1; k<pts.length && edgeCount.isEdgeOpen(i,j); k++){
		    boolean anyInside=false;
		    
		    //IOut.p("<"+i+","+j+","+k+">"); //
		    //IOut.p("isFaceCrossing = "+ isFaceCrossing(pts, edgeCount, i, j, k));
		    //IOut.p("checkDirectionOnEdgePoints = "+checkDirectionOnEdgePoints(pts,edgePts,i,j,k,innerPtsNum) );
		    //IOut.p("isStraight = "+pts[i].isStraight(pts[j],pts[k])); //
		    
		    if(edgeCount.isEdgeOpen(i,k) && edgeCount.isEdgeOpen(j,k)
		       && !isFaceCrossing(pts, edgeCount, i, j, k)
		       //&& checkFaceDirection(pts, edgeCount, i, j, k)
		       && checkDirectionOnEdgePoints(pts,edgePts,i,j,k,innerPtsNum) // added
		       && !pts[i].isStraight(pts[j],pts[k]) // added
		       ){
			
			for(int l=0; l<pts.length && !anyInside; l++)
			    if(l!=i && l!=j && l!=k){
				//IOut.p("<"+i+","+j+","+k+","+l+">"); //
				
				if(isInsideCircumcircle(pts[l],pts[i],pts[j],pts[k])){
				    
				    // check if case pts[l] is crossing edge of trim lines
				    
				    if(checkDirectionOfInnerCircumcirclePoint(pts,edgePts,
									      i,j,k,l,
									      innerPtsNum)){
					anyInside=true;
					//IOut.p(pts[l] + " is inside <"+pts[i]+", "+pts[j]+", "+pts[k]+">"); //
					//IOut.p("pt["+l+"] is INside pt["+i+"], pt["+j+"], pt["+k+"]"); //
				    }
				    //else; // when pts[l] is crossing the edge of trim
				 
				}
				else{
				    //IOut.p(pts[l] + " is outside <"+pts[i]+", "+pts[j]+", "+pts[k]+">"); //
				    //IOut.p("pt["+l+"] is OUTside pt["+i+"], pt["+j+"], pt["+k+"]"); //
				}
			    }
			if(!anyInside){ // nothing is inside
			    //IOut.p("<"+i+","+j+","+k+">: triangle is added at "+triangles.size()); //
			    if(isClockwise(pts[i],pts[j],pts[k])){
				triangles.add(new IVec2[]{ pts[i], pts[k], pts[j] });
				triangleIndex.add(new FaceIndex(i,k,j));
			    }
			    else{
				triangles.add(new IVec2[]{ pts[i], pts[j], pts[k] });
				triangleIndex.add(new FaceIndex(i,j,k));
			    }
			    
			    /*
			    if(i==0&&j==1&&k==4){
				new IGCurve(new IVec[]{ new IVec(pts[i]),
							  new IVec(pts[j]),
							  new IVec(pts[k])});
			    }
			    */
			    
			    edgeCount.addFace(i,j,k);
			    
			    //IOut.p("edgeCount.isEdgeOnOutline("+i+","+j+") = "+edgeCount.isEdgeOnOutline(i,j));
			    //IOut.p("edgeCount.getEdgeNum("+i+","+j+") = "+edgeCount.getEdgeNum(i,j));
			    //IOut.p("edgeCount.getFaceVertexIndex("+i+","+j+") = "+edgeCount.getFaceVertexIndex(i,j));
			    
			    //edgeCount.addEdge(i,j);
			    //edgeCount.addEdge(i,k);
			    //edgeCount.addEdge(j,k);
			}
			//else IOut.p("pt out"); //
		    }
		}
	    }
	}
	
	// filling holes
	checkNakedEdge(pts, triangleIndex, triangles, edgeCount);
	
	//IOut.p("trinangles.size()="+triangles.size()); //
	
	return triangles.toArray(new IVec2[triangles.size()][]);
    }
    
    /**
       Getting delaunay triangles out of array of 3D points interpreted as 2D points on XY plane.
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVecI[][] getTriangles(IVecI[] pts){
	return getTriangles(pts, null);
    }
    
    /**
       Getting delaunay triangles out of array of 3D points interpreted as 2D points on a projected plane.
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVecI[][] getTriangles(IVecI[] pts, IVecI projectionDir){
	IVec2[] pts2 = new IVec2[pts.length];
	for(int i=0; i<pts2.length; i++){
	    if(projectionDir!=null){ pts2[i] = pts[i].get().to2d(projectionDir); }
	    else{ pts2[i] = pts[i].get().to2d(); }
	}
	IVec2[][] ret2 = getTriangles(pts2);
	IVecI[][] ret = new IVecI[ret2.length][];
	List<IVec2> pts2list = Arrays.asList(pts2);
	for(int i=0; i<ret2.length; i++){
	    ret[i] = new IVecI[ret2[i].length];
	    for(int j=0; j<ret2[i].length; j++){
		int idx = pts2list.indexOf(ret2[i][j]);
		if(idx>=0){ ret[i][j] = pts[idx]; }
	    }
	}
	return ret;
    }
    
    /**
       Getting delaunay triangles out of array of 3D points interpreted as 2D points on XY plane.
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVec[][] getTriangles(IVec[] pts){
	return getTriangles(pts, null);
    }
    
    /**
       Getting delaunay triangles out of array of 3D points interpreted as 2D points on a projected plane.
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVec[][] getTriangles(IVec[] pts, IVecI projectionDir){
	IVec2[] pts2 = new IVec2[pts.length];
	for(int i=0; i<pts2.length; i++){
	    if(projectionDir!=null){ pts2[i] = pts[i].to2d(projectionDir); }
	    else{ pts2[i] = pts[i].to2d(); }
	}
	IVec2[][] ret2 = getTriangles(pts2);
	IVec[][] ret = new IVec[ret2.length][];
	List<IVec2> pts2list = Arrays.asList(pts2);
	for(int i=0; i<ret2.length; i++){
	    ret[i] = new IVec[ret2[i].length];
	    for(int j=0; j<ret2[i].length; j++){
		int idx = pts2list.indexOf(ret2[i][j]);
		if(idx>=0){ ret[i][j] = pts[idx]; }
	    }
	}
	return ret;
    }
    
    
    /**
       Getting delaunay triangles out of array of 2D points
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVec2[][] getTriangles(IVec2[] pts){
	
	if(pts.length==3){
	    if(isClockwise(pts[0],pts[1],pts[2]))
		return new IVec2[][]{ new IVec2[]{ pts[0],pts[2],pts[1] } };
	    return new IVec2[][]{ new IVec2[]{ pts[0],pts[1],pts[2] } };
	}
	
	ArrayList<IVec2[]> triangles = new ArrayList<IVec2[]>();
	
	//IOut.p("pts.length = "+pts.length); //
	//for(int i=0; i<pts.length; i++) IOut.p("pts["+i+"] = "+ pts[i]); //
	
	//int[] edgeCount = createEdgeCounter(pts.length);
	
	for(int i=0; i<pts.length-2; i++){
	    //IOut.p((i+1)+"/"+(pts.length-2)); //
	    IOut.debug(40, (i+1)+"/"+(pts.length-2)); //
	    for(int j=i+1; j<pts.length-1; j++){
		
		if(maxDistToCheck < 0 ||
		   maxDistToCheck >= 0 && pts[i].dist(pts[j]) <= maxDistToCheck){
		    
		    
		for(int k=j+1; k<pts.length; k++){

		    if(maxDistToCheck < 0 ||
		       maxDistToCheck >= 0 && pts[i].dist(pts[k]) <= maxDistToCheck){
			
			
		    boolean anyInside=false;
		    for(int l=0; l<pts.length && !anyInside; l++)
			
			
			if(l!=i && l!=j && l!=k){
			    //IOut.p("<"+i+","+j+","+k+","+l+">"); //
			    if(isInsideCircumcircle(pts[l],pts[i],pts[j],pts[k])){
				anyInside=true;
				//IOut.p(pts[l] + " is inside <"+pts[i]+", "+pts[j]+", "+pts[k]+">"); //
				//IOut.p("pt["+l+"] is INside pt["+i+"], pt["+j+"], pt["+k+"]"); //
			    }
			    else{
				//IOut.p(pts[l] + " is outside <"+pts[i]+", "+pts[j]+", "+pts[k]+">"); //
				//IOut.p("pt["+l+"] is OUTside pt["+i+"], pt["+j+"], pt["+k+"]"); //
			    }
			}
		    if(!anyInside){ // nothing is inside
			//IOut.p("<"+i+","+j+","+k+">: triangle is added"); //
			if(isClockwise(pts[i],pts[j],pts[k]))
			    triangles.add(new IVec2[]{ pts[i], pts[k], pts[j] });
			else
			    triangles.add(new IVec2[]{ pts[i], pts[j], pts[k] });
		    }
		    //else IOut.p("pt out"); //
		    }
		}
		    
		}
	    }
	}
	//IOut.p("trinangles.size()="+triangles.size()); //
	return triangles.toArray(new IVec2[triangles.size()][]);
    }

    /**
       Getting edges of delaunay triangles out of array of 2D points
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVec2[][] getEdges(IVec2[] pts){
	IVec2[][] tri = getTriangles(pts);
	ArrayList<IVec2[]> pairs = new ArrayList<IVec2[]>();
	for(int i=0; i<tri.length; i++){
	    for(int j=0; j<tri[i].length; j++){
		boolean exists=false;
		for(int k=0; k<pairs.size()&&!exists; k++){
		    if(pairs.get(k)[0] == tri[i][j] &&
		       pairs.get(k)[1] == tri[i][(j+1)%tri[i].length] ||
		       pairs.get(k)[1] == tri[i][j] &&
		       pairs.get(k)[0] == tri[i][(j+1)%tri[i].length]){
			exists=true;
		    }
		}
		if(!exists){
		    pairs.add(new IVec2[]{ tri[i][j], tri[i][(j+1)%tri[i].length] });
		}
	    }
	}
	return pairs.toArray(new IVec2[pairs.size()][]);
    }
    
    /**
       Getting edges of 2D delaunay triangles out of array of 3D points and projection vector.
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVecI[][] getEdges(IVecI[] pts){ return getEdges(pts,null); }
    
    
    /**
       Getting edges of 2D delaunay triangles out of array of 3D points and projection vector.
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVecI[][] getEdges(IVecI[] pts, IVecI projectionDir){
	IVec2[] pts2 = new IVec2[pts.length];
	for(int i=0; i<pts2.length; i++){
	    if(projectionDir!=null){ pts2[i] = pts[i].get().to2d(projectionDir); }
	    else{ pts2[i] = pts[i].get().to2d(); }
	}
	IVec2[][] ret2 = getEdges(pts2);
	IVecI[][] ret = new IVecI[ret2.length][];
	List<IVec2> pts2list = Arrays.asList(pts2);
	for(int i=0; i<ret2.length; i++){
	    ret[i] = new IVecI[ret2[i].length];
	    for(int j=0; j<ret2[i].length; j++){
		int idx = pts2list.indexOf(ret2[i][j]);
		if(idx>=0){ ret[i][j] = pts[idx]; }
	    }
	}
	return ret;
    }

    /**
       Getting edges of 2D delaunay triangles out of array of 3D points and projection vector.
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVec[][] getEdges(IVec[] pts){ return getEdges(pts,null); }
    
    /**
       Getting edges of 2D delaunay triangles out of array of 3D points and projection vector.
       @return array of triangles, which consist of array of 3 points of IVec2
    */
    public static IVec[][] getEdges(IVec[] pts, IVecI projectionDir){
	IVec2[] pts2 = new IVec2[pts.length];
	for(int i=0; i<pts2.length; i++){
	    if(projectionDir!=null){ pts2[i] = pts[i].to2d(projectionDir); }
	    else{ pts2[i] = pts[i].to2d(); }
	}
	IVec2[][] ret2 = getEdges(pts2);
	IVec[][] ret = new IVec[ret2.length][];
	List<IVec2> pts2list = Arrays.asList(pts2);
	for(int i=0; i<ret2.length; i++){
	    ret[i] = new IVec[ret2[i].length];
	    for(int j=0; j<ret2[i].length; j++){
		int idx = pts2list.indexOf(ret2[i][j]);
		if(idx>=0){ ret[i][j] = pts[idx]; }
	    }
	}
	return ret;
    }
    
    
    
    public static boolean isInsideCircumcircle(IVec2 pt, IVec2 trianglePt1,
					       IVec2 trianglePt2, IVec2 trianglePt3){
	IVec2 tpt1=null,tpt2=null,tpt3=null;
	if(isClockwise(trianglePt1, trianglePt2, trianglePt3)){
	    tpt1 = trianglePt1; tpt2 = trianglePt3; tpt3 = trianglePt2;
	}
	else{ tpt1 = trianglePt1; tpt2 = trianglePt2; tpt3 = trianglePt3; }
	
	double det = 
	    determinant(tpt1.x - pt.x, tpt1.y - pt.y,
			(tpt1.x*tpt1.x - pt.x*pt.x) + (tpt1.y*tpt1.y - pt.y*pt.y),
			tpt2.x - pt.x, tpt2.y - pt.y,
			(tpt2.x*tpt2.x - pt.x*pt.x) + (tpt2.y*tpt2.y - pt.y*pt.y),
			tpt3.x - pt.x, tpt3.y - pt.y,
			(tpt3.x*tpt3.x - pt.x*pt.x) + (tpt3.y*tpt3.y - pt.y*pt.y) );
	//IOut.p("determinant = "+det); //
	//return det > 0;
	//final double minDeterminant = 0.000001; // is this number valid?
	final double minDeterminant = 0; //
	return det > minDeterminant;
	
	/*
	// triangle points needs to be counter clockwise
	if(isClockwise(trianglePt1, trianglePt2, trianglePt3)){
	    IVec2 tmp = trianglePt2;
	    trianglePt2 = trianglePt3;
	    trianglePt3 = tmp;
	}
	return determinant(trianglePt1.x - pt.x, trianglePt1.y - pt.y,
			   (trianglePt1.x*trianglePt1.x - pt.x*pt.x) -
			   (trianglePt1.y*trianglePt1.y - pt.y*pt.y),
			   trianglePt2.x - pt.x, trianglePt2.y - pt.y,
			   (trianglePt2.x*trianglePt2.x - pt.x*pt.x) -
			   (trianglePt2.y*trianglePt2.y - pt.y*pt.y),
			   trianglePt3.x - pt.x, trianglePt3.y - pt.y,
			   (trianglePt3.x*trianglePt3.x - pt.x*pt.x) -
			   (trianglePt3.y*trianglePt3.y - pt.y*pt.y) ) >= 0; // > or >=?
	*/
    }
    
    public static boolean isClockwise(IVec2 pt1, IVec2 pt2, IVec2 pt3){
	return pt2.diff(pt1).cross(pt3.diff(pt2)).z < 0;
    }
    
    public static double determinant(double v11, double v12, double v13,
				     double v21, double v22, double v23,
				     double v31, double v32, double v33){
	/*
	IOut.p("[["+v11+","+v12+","+v13+"],["+
		v21+","+v22+","+v23+"],["+
		v31+","+v32+","+v33+"]]"); //
	
	IOut.p("v11*v22*v33 = "+v11*v22*v33); //
	IOut.p("v12*v23*v31 = "+v12*v23*v31); //
	IOut.p("v13*v21*v32 = "+v13*v21*v32); //
	IOut.p("v11*v23*v32 = "+v11*v23*v32); //
	IOut.p("v12*v21*v33 = "+v12*v21*v33); //
	IOut.p("v13*v22*v31 = "+v13*v22*v31); //
	
	IOut.p("first term = "+(v11*v22*v33 + v12*v23*v31 + v13*v21*v32));
	IOut.p("second term = "+(- v11*v23*v32 - v12*v21*v33 - v13*v22*v31));
	*/
	return v11*v22*v33 + v12*v23*v31 + v13*v21*v32 
	    - v11*v23*v32 - v12*v21*v33 - v13*v22*v31;
    }
    
    
    public static boolean isFaceCrossing(IVec2[] pts, EdgeCounter edgeCount,
					 int ptIdx1, int ptIdx2, int ptIdx3){
	if(edgeCount.hasOneFace(ptIdx1, ptIdx2)){
	    if(isVertexOnSameSide(pts[ptIdx1],pts[ptIdx2],pts[ptIdx3],
				  pts[edgeCount.getFaceVertexIndex(ptIdx1,ptIdx2)]))
		return true;
	}
	
	if(edgeCount.hasOneFace(ptIdx1, ptIdx3)){
	    if(isVertexOnSameSide(pts[ptIdx1],pts[ptIdx3],pts[ptIdx2],
				  pts[edgeCount.getFaceVertexIndex(ptIdx1,ptIdx3)]))
		return true;
	}
	
	if(edgeCount.hasOneFace(ptIdx2, ptIdx3)){
	    if(isVertexOnSameSide(pts[ptIdx2],pts[ptIdx3],pts[ptIdx1],
				  pts[edgeCount.getFaceVertexIndex(ptIdx2,ptIdx3)]))
		return true;
	}
	return false;
    }
    

    
    public static boolean isVertexOnSameSide(IVec2 edgePt1, IVec2 edgePt2,
					     IVec2 vertexPt1, IVec2 vertexPt2){
	IVec2 diff = edgePt2.diff(edgePt1);
	return diff.cross(vertexPt1.diff(edgePt1)).z*diff.cross(vertexPt2.diff(edgePt1)).z>=0;
    }
    
    
    public static boolean isFaceDirectionOnEdgeCorrect(IVec2 edgePt1, IVec2 edgePt2,
						       IVec2 vertexPt){
	// this is under assumption of outer trim loop goes counter-clockwise and
	// inner trim hole loop goes clockwise always.
	//return edgePt2.diff(edgePt1).cross(vertexPt.diff(edgePt1)).z > 0;
	double z = edgePt2.diff(edgePt1).cross(vertexPt.diff(edgePt1)).z;
	
	//IOut.p("edge1 = "+edgePt1 +", edge2 = "+edgePt2+", vertexPt = "+vertexPt);
	//if(z>0) IOut.p("correct direction"); //
	//else IOut.p("wrong direction"); //
	
	//return z > 0;
	return z >= 0; // changed 2011/07/11
    }
    
    public static boolean isFaceDirectionOnEdgeCorrect(IVec2[][] edgePts,
						       IVec2 pt,
						       IVec2 vertexPt1, IVec2 vertexPt2){
	IVec2[] adjacentPts = getAdjacentPointsOnEdgeLoop(edgePts, pt);
	if(adjacentPts==null) return false; // ERROR
	
	if(adjacentPts[0]==vertexPt1)
	    return isFaceDirectionOnEdgeCorrect(vertexPt1, pt, vertexPt2);
	if(adjacentPts[0]==vertexPt2)
	    return isFaceDirectionOnEdgeCorrect(vertexPt2, pt, vertexPt1);
	
	if(adjacentPts[1]==vertexPt1)
	    return isFaceDirectionOnEdgeCorrect(pt, vertexPt1, vertexPt2);
	if(adjacentPts[1]==vertexPt2)
	    return isFaceDirectionOnEdgeCorrect(pt, vertexPt2, vertexPt1);

	/*
	if(!isFaceDirectionOnEdgeCorrect(adjacentPts[0], pt, vertexPt1) &&
	   !isFaceDirectionOnEdgeCorrect(pt, adjacentPts[1], vertexPt1)) return false;
	if(!isFaceDirectionOnEdgeCorrect(adjacentPts[0], pt, vertexPt2) &&
	   !isFaceDirectionOnEdgeCorrect(pt, adjacentPts[1], vertexPt2)) return false;
	*/
	
	if(!isPointInsideEdge(vertexPt1, adjacentPts[0], pt, adjacentPts[1])) return false;
	
	if(!isPointInsideEdge(vertexPt2, adjacentPts[0], pt, adjacentPts[1])) return false;

	if(isPointsIntersectiongWithEdge(vertexPt1, vertexPt2,
					 adjacentPts[0], pt, adjacentPts[1])) return false;
	
	/*
	IVec2 vertexPtMid = vertexPt1.mid(vertexPt2);
	if(!isFaceDirectionOnEdgeCorrect(adjacentPts[0], pt, vertexPtMid)&&
	   !isFaceDirectionOnEdgeCorrect(pt, adjacentPts[1], vertexPtMid)) return false;
	*/
	
	return true;
    }
    
    public static boolean isPointsIntersectiongWithEdge(IVec2 pt1, IVec2 pt2,
							IVec2 edgePt1, IVec2 edgePt2,
							IVec2 edgePt3){
	IVec2 diff1 = edgePt2.diff(edgePt1);
	IVec2 diff2 = edgePt3.diff(edgePt2);
	
	// edge bent inward; no intersection
	if(diff1.cross(diff2).z >= 0) return false;
	
	// edge bent outward
	if(isFaceDirectionOnEdgeCorrect(edgePt1,edgePt2,pt1)&&
	   isFaceDirectionOnEdgeCorrect(edgePt1,edgePt2,pt2)) return false;
	if(isFaceDirectionOnEdgeCorrect(edgePt2,edgePt3,pt1)&&
	   isFaceDirectionOnEdgeCorrect(edgePt2,edgePt3,pt2)) return false;

	IVec2 bisector1 = diff1.neg().bisect(diff2);
	IVec2 bisector2 = pt1.diff(edgePt2).bisect(pt2.diff(edgePt2));
	
	if(bisector1.dot(bisector2)>0) return true;
	
	return false;
    }
    
    public static boolean isPointInsideEdge(IVec2 pt,
					    IVec2 edgePt1, IVec2 edgePt2, IVec2 edgePt3){
	// inside of edge is left side of the edge formed by edgePt1, edgePt2, edgePt3
	IVec2 diff1 = edgePt2.diff(edgePt1);
	IVec2 diff2 = edgePt3.diff(edgePt2);
	
	// edge bent inward
	if(diff1.cross(diff2).z >= 0){
	    if(!isFaceDirectionOnEdgeCorrect(edgePt1, edgePt2, pt) ||
	       !isFaceDirectionOnEdgeCorrect(edgePt2, edgePt3, pt)) return false;
	    return true;
	}
	if(!isFaceDirectionOnEdgeCorrect(edgePt1, edgePt2, pt) &&
	   !isFaceDirectionOnEdgeCorrect(edgePt2, edgePt3, pt)) return false;
	return true;
    }
    
    
    public static boolean checkFaceDirection(IVec2[] pts, EdgeCounter edgeCount,
					     int ptIdx1, int ptIdx2, int ptIdx3){

	//IOut.p("<"+ptIdx1+", "+ptIdx2+", "+ptIdx3+">"); //
	if(edgeCount.isEdgeOnOutline(ptIdx1, ptIdx2)){
	    if(ptIdx2-ptIdx1 != 1){
		// to be sequential difference should be 1, if not, it's closing poitn of loop
		int tmp = ptIdx2; ptIdx2 = ptIdx1; ptIdx1 = tmp;
	    }
	    if(!isFaceDirectionOnEdgeCorrect(pts[ptIdx1],pts[ptIdx2],pts[ptIdx3]))
		return false;
	}
	
	if(edgeCount.isEdgeOnOutline(ptIdx1, ptIdx3)){
	    if(ptIdx3-ptIdx1 != 1){
		// to be sequential difference should be 1, if not, it's closing poitn of loop
		int tmp = ptIdx1; ptIdx1 = ptIdx3; ptIdx3 = tmp;
	    }
	    if(!isFaceDirectionOnEdgeCorrect(pts[ptIdx1],pts[ptIdx3],pts[ptIdx2]))
		return false;
	}
	
	if(edgeCount.isEdgeOnOutline(ptIdx2, ptIdx3)){
	    if(ptIdx3-ptIdx2 != 1){
		// to be sequential difference should be 1, if not, it's closing poitn of loop
		int tmp = ptIdx3; ptIdx3 = ptIdx2; ptIdx2 = tmp;
	    }
	    if(!isFaceDirectionOnEdgeCorrect(pts[ptIdx2],pts[ptIdx3],pts[ptIdx1]))
		return false;
	}
	
	return true;
    }
    
    
    static public boolean checkDirectionOnEdgePoints(IVec2[] pts,
						     IVec2[][] edgePts,
						     int ptIdx1,
						     int ptIdx2,
						     int ptIdx3,
						     int innerPtNum){
	
	if(ptIdx1>=innerPtNum){ // on the edgePts
	    if(!isFaceDirectionOnEdgeCorrect(edgePts,pts[ptIdx1],pts[ptIdx2],pts[ptIdx3])){
		return false;
	    }
	}
	
	if(ptIdx2>=innerPtNum){ // on the edgePts
	    if(!isFaceDirectionOnEdgeCorrect(edgePts,pts[ptIdx2],pts[ptIdx1],pts[ptIdx3])){
		return false;
	    }
	}
	
	if(ptIdx3>=innerPtNum){ // on the edgePts
	    if(!isFaceDirectionOnEdgeCorrect(edgePts,pts[ptIdx3],pts[ptIdx1],pts[ptIdx2])){
		return false;
	    }
	}
	
	return true;
    }
    
    static public IVec2[] getAdjacentPointsOnEdgeLoop(IVec2[][] edgePts, IVec2 pt){
	for(int i=0; i<edgePts.length; i++){
	    for(int j=0; j<edgePts[i].length; j++){
		if(edgePts[i][j] == pt){
		    return new IVec2[]{edgePts[i][(j-1+edgePts[i].length)%edgePts[i].length],
					edgePts[i][(j+1)%edgePts[i].length]};
		}
	    }
	}
	IOut.err("no point found in edgePts : pt = "+pt);
	return null;
    }
    
    static public boolean checkDirectionOfInnerCircumcirclePoint(IVec2[] pts,
								 IVec2[][] edgePts,
								 int ptIdx1,
								 int ptIdx2,
								 int ptIdx3,
								 int ptIdx4,
								 int innerPtNum){
	IVec2[] adjacentPts1 = null;
	if(ptIdx1>=innerPtNum && ptIdx2>=innerPtNum){
	    adjacentPts1 = getAdjacentPointsOnEdgeLoop(edgePts,pts[ptIdx1]);
	    if( adjacentPts1[1]==pts[ptIdx2] &&
		!isFaceDirectionOnEdgeCorrect(pts[ptIdx1], pts[ptIdx2], pts[ptIdx4]) ||
		adjacentPts1[0]==pts[ptIdx2] &&
		!isFaceDirectionOnEdgeCorrect(pts[ptIdx2], pts[ptIdx1], pts[ptIdx4]) ){
		return false;
	    }
	}
	
	if(ptIdx1>=innerPtNum && ptIdx3>=innerPtNum){
	    if(adjacentPts1==null)
		adjacentPts1 = getAdjacentPointsOnEdgeLoop(edgePts,pts[ptIdx1]);
	    if( adjacentPts1[1]==pts[ptIdx3] &&
		!isFaceDirectionOnEdgeCorrect(pts[ptIdx1], pts[ptIdx3], pts[ptIdx4]) ||
		adjacentPts1[0]==pts[ptIdx3] &&
		!isFaceDirectionOnEdgeCorrect(pts[ptIdx3], pts[ptIdx1], pts[ptIdx4])){
		return false;
	    }
	}
	
	if(ptIdx2>=innerPtNum && ptIdx3>=innerPtNum){
	    IVec2[] adjacentPts2 = getAdjacentPointsOnEdgeLoop(edgePts,pts[ptIdx2]);
	    if( adjacentPts2[1]==pts[ptIdx3] &&
		!isFaceDirectionOnEdgeCorrect(pts[ptIdx2], pts[ptIdx3], pts[ptIdx4]) ||
		adjacentPts2[0]==pts[ptIdx3] &&
		!isFaceDirectionOnEdgeCorrect(pts[ptIdx3], pts[ptIdx2], pts[ptIdx4]) ){
		return false;
	    }
	}
	
	return true;
    }
    
    static public void checkNakedEdge(IVec2[] pts,
				      ArrayList<FaceIndex> faceIdx,
				      ArrayList<IVec2[]> triangles,
				      EdgeCounter edgeCount){
	
	for(int i=0; i<pts.length-1; i++){
	    //IOut.p(i+"/"+pts.length); //
	    for(int j=i+1; j<pts.length; j++){
		
		if(edgeCount.getEdgeNum(i,j)==1){
		    boolean added=false;
		    //for(int k=j+1; k<pts.length && !added; k++){
		    for(int k=0; k<pts.length && !added; k++){
			
			// fill triangle at the edge or, fill triangle in an obvious triangular hole
			// not filling when 3 edges already form a triangle
			FaceIndex fidx=null;
			
			boolean addFace=false;
			
			if(k!=i && k!=j){
			    int idx1=i;
			    int idx2=j;
			    int idx3=k;
			    
			    // re-order
			    if(k<i){ idx1=k; idx2=i; idx3=j; }
			    else if(k<j){ idx1=i; idx2=k; idx3=j; }
			    
			    /*
			    IOut.p("checking <"+i+","+j+","+k+">"); //
			    IOut.p("isEdgeOnOutline("+idx1+","+idx2+") = "+
				    edgeCount.isEdgeOnOutline(idx1,idx2));
			    IOut.p("isEdgeOnOutline("+idx1+","+idx3+") = "+
				    edgeCount.isEdgeOnOutline(idx1,idx3));
			    IOut.p("isEdgeOnOutline("+idx2+","+idx3+") = "+
				    edgeCount.isEdgeOnOutline(idx2,idx3));
			    IOut.p("isFaceDirectionOnEdgeCorrect("+idx1+","+idx2+","+idx3+") = "+
				    isFaceDirectionOnEdgeCorrect(pts[idx1],pts[idx2],pts[idx3]));
			    IOut.p("isFaceDirectionOnEdgeCorrect("+idx1+","+idx3+","+idx2+") = "+
				    isFaceDirectionOnEdgeCorrect(pts[idx1],pts[idx3],pts[idx2]));
			    IOut.p("isFaceDirectionOnEdgeCorrect("+idx2+","+idx3+","+idx1+") = "+
				    isFaceDirectionOnEdgeCorrect(pts[idx2],pts[idx3],pts[idx1]));
			    IOut.p("isEdgeOpen("+idx1+","+idx2+") = "+
				    edgeCount.isEdgeOpen(idx1,idx2));
			    IOut.p("isEdgeOpen("+idx1+","+idx3+") = "+
				    edgeCount.isEdgeOpen(idx1,idx3));
			    IOut.p("isEdgeOpen("+idx2+","+idx3+") = "+
				    edgeCount.isEdgeOpen(idx2,idx3));
			    */
			    
			    if(edgeCount.isEdgeOnOutline(idx1,idx2) &&
			       edgeCount.isEdgeOnOutline(idx2,idx3) &&
			       edgeCount.hasOneFace(idx1,idx3) &&
			       //isFaceDirectionOnEdgeCorrect(pts[idx1],pts[idx2],pts[idx3])){
			       // if idx1==0, idx2 is last point on outline and order should be idx2->idx1
			       isFaceDirectionOnEdgeCorrect(pts[idx1==0?idx2:idx1],pts[idx1==0?idx1:idx2],pts[idx3])){
				
				//IOut.p("addFace 4"); //
				addFace=true;
			    }
			    else if(edgeCount.isEdgeOnOutline(idx1,idx3) &&
				    edgeCount.isEdgeOnOutline(idx2,idx3) &&
				    edgeCount.hasOneFace(idx1,idx2) &&
				    //isFaceDirectionOnEdgeCorrect(pts[idx1],pts[idx3],pts[idx2])){
				    isFaceDirectionOnEdgeCorrect(pts[idx1==0?idx3:idx1],pts[idx1==0?idx1:idx3],pts[idx2])){
				//IOut.p("addFace 5"); //
				addFace=true;
			    }
			    else if(edgeCount.isEdgeOnOutline(idx1,idx3) &&
				    edgeCount.isEdgeOnOutline(idx1,idx2) &&
				    edgeCount.hasOneFace(idx2,idx3) &&
				    //isFaceDirectionOnEdgeCorrect(pts[idx2],pts[idx3],pts[idx1])){
				    isFaceDirectionOnEdgeCorrect(pts[idx2==0?idx3:idx2],pts[idx2==0?idx2:idx3],pts[idx1])){
				//IOut.p("addFace 6"); //
				addFace=true;
			    }
			    else if(edgeCount.isEdgeOnOutline(idx1,idx2) &&
				    //(edgeCount.isEdgeOpen(idx1,idx3) || edgeCount.isEdgeOpen(idx2,idx3))){
				    edgeCount.isEdgeOpen(idx1,idx3) && edgeCount.isEdgeOpen(idx2,idx3)){
				if(isFaceDirectionOnEdgeCorrect(pts[idx1==0?idx2:idx1],pts[idx1==0?idx1:idx2],pts[idx3])
				   && !isFaceCrossing(pts, edgeCount, idx1, idx2, idx3)){
				    //IOut.p("addFace 1"); //
				    addFace=true;
				}
			    }
			    else if(edgeCount.isEdgeOnOutline(idx1,idx3) &&
				    //(edgeCount.isEdgeOpen(idx1,idx2)||edgeCount.isEdgeOpen(idx2,idx3))){
				    edgeCount.isEdgeOpen(idx1,idx2)&&edgeCount.isEdgeOpen(idx2,idx3)){
				if(isFaceDirectionOnEdgeCorrect(pts[idx1==0?idx3:idx1],pts[idx1==0?idx1:idx3],pts[idx2])
				   && !isFaceCrossing(pts, edgeCount, idx1, idx2, idx3)){
				    //IOut.p("addFace 2"); //
				    addFace=true;
				}
			    }
			    else if(edgeCount.isEdgeOnOutline(idx2,idx3) &&
				    //(edgeCount.isEdgeOpen(idx1,idx2)||edgeCount.isEdgeOpen(idx1,idx3))){
				    edgeCount.isEdgeOpen(idx1,idx2)&&edgeCount.isEdgeOpen(idx1,idx3)){
				//IOut.p("isFaceCrossing("+idx1+","+idx2+","+idx3+") = "+isFaceCrossing(pts, edgeCount, idx1, idx2, idx3)); //
				
				if(isFaceDirectionOnEdgeCorrect(pts[idx2==0?idx3:idx2],pts[idx2==0?idx2:idx3],pts[idx1])
				   && !isFaceCrossing(pts, edgeCount, idx1, idx2, idx3)){
				    //IOut.p("addFace 3 <"+idx2+","+idx3+","+idx1+">"); //
				    addFace=true;
				}
			    }
			    else if((edgeCount.getEdgeNum(idx1,idx2)==1&&
				     edgeCount.getEdgeNum(idx1,idx3)==1&&
				     edgeCount.getEdgeNum(idx2,idx3)==1 &&
				     edgeCount.getFaceVertexIndex(idx1,idx2)!=-1 &&
				     edgeCount.getFaceVertexIndex(idx1,idx3)!=-1 &&
				     edgeCount.getFaceVertexIndex(idx2,idx3)!=-1 &&
				     ((fidx=findTriangleIndexWithEdge(faceIdx,idx1,idx2))==null ||
				      fidx.getOtherIndex(idx1,idx2) != idx3))){
				//IOut.p("addFace 7"); //
				addFace=true;
			    }
			    
			    if(addFace){
				//IOut.p("face added at <"+i+","+j+","+k+">"); //
				//edgeCount.addFace(i,j,k);
				//triangles.add(new IVec2[]{ pts[i],pts[j],pts[k]});
				//faceIdx.add(new FaceIndex(i,j,k));
				
				//IOut.p("face added at <"+idx1+","+idx2+","+idx3+">"); //
				edgeCount.addFace(idx1,idx2,idx3);
				triangles.add(new IVec2[]{ pts[idx1],pts[idx2],pts[idx3]});
				faceIdx.add(new FaceIndex(idx1,idx2,idx3));
				added=true;
			    }
			}
			
			/*
			if((edgeCount.getEdgeNum(i,k)==1&&edgeCount.getEdgeNum(j,k)==1 ||
			    edgeCount.getEdgeNum(i,k)==1&&edgeCount.getEdgeNum(j,k)==0 ||
			    edgeCount.getEdgeNum(i,k)==0&&edgeCount.getEdgeNum(j,k)==1 ) &&
			   (edgeCount.getFaceVertexIndex(i,j)==-1 ||
			    edgeCount.getFaceVertexIndex(i,k)==-1 ||
			    edgeCount.getFaceVertexIndex(j,k)==-1 )
			   ||
			   ( edgeCount.getEdgeNum(i,k)==1&&edgeCount.getEdgeNum(j,k)==1 &&
			     edgeCount.getFaceVertexIndex(i,j)!=-1 &&
			     edgeCount.getFaceVertexIndex(i,k)!=-1 &&
			     edgeCount.getFaceVertexIndex(j,k)!=-1 &&
			     ((fidx = findTriangleIndexWithEdge(faceIdx, i, j)) ==null ||
			      fidx.getOtherIndex(i,j) != k))
			   ){
			    
			    IOut.p("face added at <"+i+","+j+","+k+">"); //
			    
			    edgeCount.addFace(i,j,k);
			    triangles.add(new IVec2[]{ pts[i],pts[j],pts[k]});
			    faceIdx.add(new FaceIndex(i,j,k));
			    added=true;
			}
			*/
		    }
		}
	    }
	}
	
	
	// remove triangles which have naked edge but not on the outline
	// (likely to be inside of inner holes or outside of outer trim
	for(int i=0; i<pts.length-1; i++){
	    for(int j=i+1; j<pts.length; j++){
		if(edgeCount.getEdgeNum(i,j)==1 &&
		   edgeCount.getFaceVertexIndex(i,j)!=-1){
		    //IOut.p("naked edge found at <"+i+","+j+">"); //
		    FaceIndex fidx = findTriangleIndexWithEdge(faceIdx, i, j);
		    if(fidx!=null){
			int k = fidx.getOtherIndex(i,j);
			IVec2[] tr = findTriangleWithIndex(pts, triangles, i,j,k);
			if(tr!=null){
			    triangles.remove(tr);
			    //IOut.p("face removed at <"+i+","+j+","+k+">"); //
			}
		    }
		}
	    }
	}
	
	
    }
    
    static public IVec2[] findTriangleWithIndex(IVec2[] pts, ArrayList<IVec2[]> triangles,
						 int index1, int index2, int index3){
	
	for(int i=0; i<triangles.size(); i++){
	    if(isSameTriangle(triangles.get(i), pts[index1], pts[index2], pts[index3]))
		return triangles.get(i);
	}
	return null;
    }
    
    static public boolean isSameTriangle(IVec2[] triangle, IVec2 pt1, IVec2 pt2, IVec2 pt3){
	return triangle[0]==pt1 && triangle[1]==pt2 && triangle[2]==pt3 ||
	    triangle[0]==pt2 && triangle[1]==pt3 && triangle[2]==pt1 ||
	    triangle[0]==pt3 && triangle[1]==pt1 && triangle[2]==pt2 ||
	    triangle[0]==pt1 && triangle[1]==pt3 && triangle[2]==pt2 ||
	    triangle[0]==pt2 && triangle[1]==pt1 && triangle[2]==pt3 ||
	    triangle[0]==pt3 && triangle[1]==pt2 && triangle[2]==pt1;
    }
    
    /**
       @param ptIdx1 requirement: ptIdx1 < ptIdx2
    */
    static public FaceIndex findTriangleIndexWithEdge(ArrayList<FaceIndex> faceIdx,
						      int ptIdx1, int ptIdx2){
	for(int i=0; i<faceIdx.size(); i++){
	    if(faceIdx.get(i).contains(ptIdx1,ptIdx2)) return faceIdx.get(i);
	}
	return null;
    }
    
    
    /*
    public static int[] createEdgeCounter(int ptNum){
	return new int[ptNum*(ptNum-1)/2];
    }
    
    public static void addEdge(int[] edgeCounter, int ptNum, int ptIdx1, int ptIdx2){
	edgeCounter[getEdgeIndex(ptNum, ptIdx1, ptIdx2)]++;
    }
    
    public static int getEdgeNum(int[] edgeCounter, int ptNum, int ptIdx1, int ptIdx2){
	return edgeCounter[getEdgeIndex(ptNum, ptIdx1, ptIdx2)];
    }
    */
    
    /**
       @param ptNum: total point num
       @param ptIdx1: [0 - (ptNum-2)]
       @param ptIdx2: [ptIdx1+1 - (ptNum-1)]
    */
    /*
    public static int getEdgeIndex(int ptNum, int ptIdx1, int ptIdx2){
	if(ptIdx1>ptIdx2){ int tmp=ptIdx1; ptIdx1=ptIdx2; ptIdx2=tmp; }
	return ptIdx1*(ptNum-1) - ptIdx1*(ptIdx1-1)/2 + (ptIdx2 - (ptIdx1+1));
    }
    */
    
    /*
    // debug
    public static void main(String[] args){
	int num=21;
	for(int i=0; i<num-1; i++)
	    for(int j=i+1; j<num; j++)
		IOut.p("<"+i+","+j+">="+getEdgeIndex(num,i,j));
    }
    */
    
    public static class FaceIndex{
	public int index1, index2, index3;
	public FaceIndex(int idx1, int idx2, int idx3){ index1=idx1; index2=idx2; index3=idx3; }
	public boolean contains(int idx1, int idx2){
	    return index1==idx1 && index2==idx2 || index2==idx1 && index1==idx2 ||
		index2==idx1 && index3==idx2 || index3==idx1 && index2==idx2 ||
		index3==idx1 && index1==idx2 || index1==idx1 && index3==idx2;
	}
	
	public int getOtherIndex(int idx1, int idx2){
	    if(index1==idx1 && index2==idx2 || index2==idx1 && index1==idx2) return index3;
	    if(index2==idx1 && index3==idx2 || index3==idx1 && index2==idx2) return index1;
	    if(index3==idx1 && index1==idx2 || index1==idx1 && index3==idx2) return index2;
	    return -1;
	}
    }
	
    public static class EdgeCounter{
	int[] counter;
	int ptNum;
	
	int[] faceVertexIndex; // point index number of other vertex in a triangle. only the latest triangle.
	
	public EdgeCounter(int ptNum){
	    this.ptNum=ptNum;
	    counter=new int[ptNum*(ptNum-1)/2];
	    faceVertexIndex=new int[ptNum*(ptNum-1)/2];
	}
	
	
	public void addEdge(int ptIdx1, int ptIdx2){
	    counter[getIndex(ptIdx1, ptIdx2)]++;
	    faceVertexIndex[getIndex(ptIdx1, ptIdx2)] = -1; // no face attached.
	}
	
	
	public void addFace(int ptIdx1, int ptIdx2, int ptIdx3){
	    counter[getIndex(ptIdx1, ptIdx2)]++;
	    counter[getIndex(ptIdx1, ptIdx3)]++;
	    counter[getIndex(ptIdx2, ptIdx3)]++;
	    
	    faceVertexIndex[getIndex(ptIdx1, ptIdx2)] = ptIdx3;
	    faceVertexIndex[getIndex(ptIdx1, ptIdx3)] = ptIdx2;
	    faceVertexIndex[getIndex(ptIdx2, ptIdx3)] = ptIdx1;
	}
	
	public int getEdgeNum(int ptIdx1, int ptIdx2){
	    return counter[getIndex(ptIdx1, ptIdx2)];
	}
	
	public int getFaceVertexIndex(int ptIdx1, int ptIdx2){
	    return faceVertexIndex[getIndex(ptIdx1, ptIdx2)];
	}
	
	public boolean isEdgeOpen(int ptIdx1, int ptIdx2){
	    return getEdgeNum(ptIdx1,ptIdx2) < 2;
	}
	
	public boolean hasOneFace(int ptIdx1, int ptIdx2){
	    return getEdgeNum(ptIdx1,ptIdx2) == 1 &&
		getFaceVertexIndex(ptIdx1,ptIdx2)>=0;
	}
	
	public boolean isEdgeOnOutline(int ptIdx1, int ptIdx2){
	    return getEdgeNum(ptIdx1,ptIdx2) == 1 &&
		getFaceVertexIndex(ptIdx1,ptIdx2)==-1;
	}
		
	
	protected int getIndex(int ptIdx1, int ptIdx2){
	    if(ptIdx1>ptIdx2){ int tmp=ptIdx1; ptIdx1=ptIdx2; ptIdx2=tmp; }
	    return ptIdx1*(ptNum-1) - ptIdx1*(ptIdx1-1)/2 + (ptIdx2 - (ptIdx1+1));
	}
	
    }
    
}
