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

package igeo.gui;

import java.util.*;
import java.awt.*;

import igeo.*;

/**
   Class to generate drawable polygon mesh of NURBS surface with or without trim curves.
   This class is used by ISurfaceGraphicFillGL.
   
   @see ISurfaceGraphicFillGL
   @see ISubsurfaceMesh
   
   @author Satoru Sugihara
*/
public class ISurfaceMesh {
    
    public static IVec2[][] getTriangles(IVec2[][] surfPts,
					 IVec2[][] outerLoops,
					 IVec2[][] innerLoops){
	
	/*
	// debug
	double[] upos = new double[surfPts.length];
	double[] vpos = new double[surfPts[0].length];
	for(int i=0; i<surfPts.length; i++){
	    upos[i] = surfPts[i][0].x;
	    IOut.p("upos["+i+"]="+upos[i]); //
	}
	for(int i=0; i<surfPts[0].length; i++){
	    vpos[i] = surfPts[0][i].y;
	    IOut.p("vpos["+i+"]="+vpos[i]); //
	}
	ArrayList<IVec2[]> tris = new ArrayList<IVec2[]>();
	
	for(int i=0; outerLoops!=null && i<outerLoops.length; i++){
	    SubsurfaceIndexGroup grp = new SubsurfaceIndexGroup(upos,vpos);
	    grp.setPolyline(outerLoops[i], true);
	    for(int j=0; j<grp.num(); j++){
		IVec2[][] corners = grp.getSubsurfaceCorners(j);
		if(corners!=null){
		    tris.add(new IVec2[]{ corners[0][0],corners[1][0],corners[1][1] });
		    tris.add(new IVec2[]{ corners[0][0],corners[1][1],corners[0][1] });
		}
	    }
	}
	for(int i=0; innerLoops!=null && i<innerLoops.length; i++){
	    SubsurfaceIndexGroup grp = new SubsurfaceIndexGroup(upos,vpos);
	    grp.setPolyline(innerLoops[i], true);
	    for(int j=0; j<grp.num(); j++){
		IVec2[][] corners = grp.getSubsurfaceCorners(j);
		if(corners!=null){
		    tris.add(new IVec2[]{ corners[0][0],corners[1][0],corners[1][1] });
		    tris.add(new IVec2[]{ corners[0][0],corners[1][1],corners[0][1] });
		}
	    }
	}
	if(true) return tris.toArray(new IVec2[tris.size()][]);
	*/
	
	
	//debug ISubsurfaceMesh
	double[] upos = new double[surfPts.length];
	double[] vpos = new double[surfPts[0].length];
	for(int i=0; i<surfPts.length; i++){
	    upos[i] = surfPts[i][0].x;
	    IOut.debug(20, "upos["+i+"]="+upos[i]); //
	}
	for(int i=0; i<surfPts[0].length; i++){
	    vpos[i] = surfPts[0][i].y;
	    IOut.debug(20, "vpos["+i+"]="+vpos[i]); //
	}
	
	ISubsurfaceMesh.SubsurfaceMatrix matrix =
	    new ISubsurfaceMesh.SubsurfaceMatrix(upos,vpos);
	
	for(int i=0; outerLoops!=null && i<outerLoops.length; i++){
	    matrix.setLoop(outerLoops[i],true);
	}
	for(int i=0; innerLoops!=null && i<innerLoops.length; i++){
	    //if(i==2) // debug
	    matrix.setLoop(innerLoops[i],false);
	}
	
	if(true) return matrix.getTriangles(); //
	
	
	
	ArrayList<IVec2> spts = new ArrayList<IVec2>();
	
	// skipping points which are not inside the trim;
	// (condition: not allowing to have nested outer loops)
	for(int i=0; i<surfPts.length; i++){
	    for(int j=0; j<surfPts[i].length; j++){
		
		// if surfPts is on trim loop, it should be on pts array of the loop
		IVec2 pt = surfPts[i][j];
		boolean inside=true;
		
		for(int k=0; outerLoops!=null && k<outerLoops.length&&inside; k++){
		    IVec2[] epts = outerLoops[k];
		    for(int l=0; l<epts.length && inside; l++){
			if(l==0&&pt.eq(epts[l],IConfig.parameterTolerance)||
			   pt.eq(epts[(l+1)%epts.length],IConfig.parameterTolerance)){ // omit point
			    inside=false;
			}
			else if(pt.isStraight(epts[l], epts[(l+1)%epts.length]) &&
				pt.isBetween(epts[l], epts[(l+1)%epts.length])){
			    outerLoops[k] = insertPoint(epts, l+1, pt);
			    inside=false;
			}
		    }
		}
		
		for(int k=0; innerLoops!=null && k<innerLoops.length&&inside; k++){
		    IVec2[] epts = innerLoops[k];
		    for(int l=0; l<epts.length && inside; l++){
			
			if(l==0&&pt.eq(epts[l],IConfig.parameterTolerance)||
			   pt.eq(epts[(l+1)%epts.length],IConfig.parameterTolerance)){ // omit point
			    inside=false;
			}
			else if(pt.isStraight(epts[l], epts[(l+1)%epts.length]) &&
				pt.isBetween(epts[l], epts[(l+1)%epts.length])){
			    innerLoops[k] = insertPoint(epts, l+1, pt);
			    inside=false;
			}
		    }
		}
		
		for(int k=0; outerLoops!=null && k<outerLoops.length && inside; k++){
		    if(!pt.isInside(outerLoops[k])){
			inside=false;
		    }
		    /*
		    else{
			//IOut.print(surfPts[i][j] +" is inside ");
			//for(int l=0; l<outerLoops[k].length; l++) IOut.print(outerLoops[k][l]+", ");
			//IOut.p();
			for(int l=0; l<outerLoops[k].length; l++){
			    if(outerLoops[k][l].eq(pt,IConfig.parameterTolerance)) inside=false;
			}
			
		    }
		    */
		}
		for(int k=0; innerLoops!=null && k<innerLoops.length && inside; k++){
		    if(pt.isInside(innerLoops[k])) inside=false;
		    /*
		    else{
			for(int l=0; l<innerLoops[k].length; l++){
			    if(innerLoops[k][l].eq(pt,IConfig.parameterTolerance)) inside=false;
			}
		    }
		    */
		}
		
		if(inside) spts.add(pt);
	    }
	}
	
	return getTriangles(spts.toArray(new IVec2[spts.size()]), outerLoops, innerLoops);
    }
    
    public static IVec2[][] getTriangles(IVec2[] surfPts,
					  IVec2[][] outerLoops,
					  IVec2[][] innerLoops){
	ArrayList<IVec2> pts = new ArrayList<IVec2>();
	ArrayList<IVec2[]> edgePts = new ArrayList<IVec2[]>();
	
	for(int i=0; outerLoops!=null&&i<outerLoops.length; i++)
	    edgePts.add(outerLoops[i]);
	for(int i=0; innerLoops!=null&&i<innerLoops.length; i++)
	    edgePts.add(innerLoops[i]);
	
	/*
	if(outerLoops!=null)
	    for(int i=0; i<outerLoops.length; i++)
		for(int j=0; j<outerLoops[i].length; j++){
		    pts.add(outerLoops[i][j]);
		    
		    // debug
		    IPoint p = new IPoint(outerLoops[i][j].x,outerLoops[i][j].y,0);
		    p.setColor(Color.blue);
		}
	
	if(innerLoops!=null)
	    for(int i=0; i<innerLoops.length; i++)
		for(int j=0; j<innerLoops[i].length; j++){
		    pts.add(innerLoops[i][j]);
		    
		    // debug
		    IPoint p = new IPoint(innerLoops[i][j].x,innerLoops[i][j].y,0);
		    p.setColor(Color.red);
		}
	*/
	
	if(surfPts!=null){
	    for(int i=0; i<surfPts.length; i++){
		pts.add(surfPts[i]);
		
	    }
	}

	/*
	// if surfPts is on trim loop, it should be on pts array of the loop
	for(int i=0; i<pts.size(); i++){
	    IVec2 pt = pts.get(i);
	    boolean removed=false;
	    for(int j=0; j<edgePts.size()&&!removed; j++){
		IVec2[] epts = edgePts.get(j);
		for(int k=0; k<epts.length && !removed; k++){
		    if(pt.isStraight(epts[k], epts[(k+1)%epts.length]) &&
		       pt.isBetween(epts[k], epts[(k+1)%epts.length])){
			epts = insertPoint(epts, k+1, pt);
			pts.remove(i);
			i--;
			removed=true;
			edgePts.set(j, epts);
		    }
		}
	    }
	}
	*/
	
	/*
	// debug
	for(int i=0; i<pts.size(); i++){
	    IPoint p = new IPoint(pts.get(i).x,pts.get(i).y,0);
	    p.setColor(Color.yellow);
	}
	*/
	
	//IVec2[][] triangles = IDelaunay2D.getTriangles(pts.toArray(new IVec2[pts.size()]));
	
	IVec2[][] triangles =
	    IDelaunay2D.getTriangles(pts.toArray(new IVec2[pts.size()]),
				      edgePts.toArray(new IVec2[edgePts.size()][]));
	
	return triangles;
	
	/*
	//IOut.p("delaunay triangle num = "+triangles.length); 
	ArrayList<IVec2[]> innerTriangles = new ArrayList<IVec2[]>();
	for(int i=0; i<triangles.length; i++){
	    IVec2 center = getCenter(triangles[i]);
	    
	    boolean inside=true;
	    if(outerLoops!=null)
		for(int j=0; j<outerLoops.length && inside; j++)
		    if(!center.isInside(outerLoops[j])) inside=false;
	    
	    if(innerLoops!=null)
		for(int j=0; j<innerLoops.length && inside; j++)
		    if(center.isInside(innerLoops[j])) inside=false;
	    
	    //if(inside) innerTriangles.add(triangles[i]);
	    //debug
	    innerTriangles.add(triangles[i]);
	}
	
	//IOut.p("innerTriangles.size() = "+innerTriangles.size()); //
	
	return innerTriangles.toArray(new IVec2[innerTriangles.size()][]);
	*/
    }
    
    public static IVec2[] insertPoint(IVec2[] pts, int i, IVec2 pt){
	IVec2[] pts2 = new IVec2[pts.length+1];
	if(i>0) System.arraycopy(pts, 0, pts2, 0, i);
	pts2[i] = pt;
	if(i<pts.length) System.arraycopy(pts, i, pts2, i+1, pts.length-i);
	return pts2;
    }
    
    public static IVec2 getCenter(IVec2[] triangle){
	IVec2 cnt = new IVec2();
	for(int i=0; i<triangle.length; i++) cnt.add(triangle[i]);
	cnt.div(triangle.length);
	return cnt;
    }
    
    
    public static class SubsurfaceIndexGroup{
	public ArrayList<SubsurfaceIndex> indices;
	public int unum, vnum;
	public double[] uvalues; // uvalues is not necessarily equally spaced
	public double[] vvalues; // vvalues is not necessarily equally spaced
	
	//public SubsurfaceIndexGroup(int unum, int vnum){
	public SubsurfaceIndexGroup(double[] uval, double[] vval){
	    unum=uval.length-1; vnum=vval.length-1;
	    uvalues = uval;
	    vvalues = vval;
	    indices = new ArrayList<SubsurfaceIndex>();
	}
	public void add(int u, int v){ indices.add(new SubsurfaceIndex(u,v)); } // no error check
	public int num(){ return indices.size(); }
	public boolean contains(int u, int v){
	    for(int i=0; i<indices.size(); i++)
		if(indices.get(i).equals(u,v)) return true;
	    return false;
	}
	
	public SubsurfaceIndex getIndex(int i){ return indices.get(i); }
	public IVec2[][] getSubsurfaceCorners(int i){
	    SubsurfaceIndex idx = indices.get(i);
	    IVec2[][] corners = new IVec2[2][2];
	    if(idx.uindex<unum && idx.vindex<vnum){
		corners[0][0] = getPoint(idx);
		corners[1][0] = getPoint(idx.uindex+1, idx.vindex);
		corners[0][1] = getPoint(idx.uindex, idx.vindex+1);
		corners[1][1] = getPoint(idx.uindex+1, idx.vindex+1);
		return corners;
	    }
	    return null;
	}
	
	/**
	   add subsurface index where loop lines sit
	*/
	public void setPolyline(IVec2[] polyline, boolean closed){
	    for(int i=0; i<polyline.length; i++){
		IOut.debug(30,"polyline["+i+"]="+polyline[i]);//
	    }
	    
	    
	    SubsurfaceIndex prevIndex = getIndexOnPoint(polyline[0]);
	    indices.add(prevIndex);
	    
	    for(int i=1; i<=polyline.length; i++){
		SubsurfaceIndex index = getIndexOnPoint(polyline[i%polyline.length]);
		
		IOut.debug(30, "i="+i+", prevIndex=<"+prevIndex.uindex+","+prevIndex.vindex+">, index=<"+index.uindex+","+index.vindex+">"); //
		
		if(!index.equals(prevIndex)){
		    
		    if(i<polyline.length || closed ){
			//IOut.p("polyline["+i+"]="+polyline[i] +" to polyline["+((i+1)%polyline.length) + "]="+polyline[(i+1)%polyline.length]);
			IOut.debug(30,"polyline["+(i-1)+"] to polyline["+(i%polyline.length) + "]"); //
			SubsurfaceIndex[] array =
			    getIndexOnLine(polyline[i-1],polyline[i%polyline.length]);
			
			if(array==null){
			    IOut.debug(30,"array == null"); //
			}
			else{
			    IOut.debug(30,"array.length = "+array.length); //
			    for(int j=0; j<array.length; j++){
				IOut.debug(30,"array["+j+"]=<"+array[j].uindex+","+array[j].vindex+">"); //
			    }
			}
			if(array!=null){
			    for(int j=0; j<array.length; j++){
				boolean exists=false;
				for(int k=0; k<indices.size()&&!exists; k++)
				    if(indices.get(k).equals(array[j])) exists=true;
				if(!exists) indices.add(array[j]);
				//if(!indices.contains(array[j])) indices.add(array[j]);
			    }
			}

			if(i<polyline.length){
			    boolean exists=false;
			    for(int j=0; j<indices.size()&&!exists; j++)
				if(indices.get(j).equals(index)) exists=true;
			    if(!exists) indices.add(index);
			}
			/*
			//IOut.p("checking contains"); //
			if(i<polyline.length && !indices.contains(index))
			    indices.add(index);
			//IOut.p("end checking contains"); //
			*/
			IOut.debug(30,"index=<"+index.uindex+","+index.vindex+">"); //
			
		    }
		    prevIndex=index;
		}
	    }
	}
	
	
	protected int getUIndex(IVec2 pt){
	    // uvalues[0] must be 0
	    for(int i=0; i<unum; i++) if(pt.x < uvalues[i+1]) return i;
	    return unum-1; //return unum;
	    //return (int)(pt.x*unum);
	}
	protected int getVIndex(IVec2 pt){
	    // vvalues[0] must be 0
	    for(int i=0; i<vnum; i++) if(pt.y < vvalues[i+1]) return i;
	    return vnum-1; //return vnum;
	    //return (int)(pt.y*vnum);
	}
	
	protected double getU(int uidx){
	    return uvalues[uidx];
	    //return (double)uidx/unum;
	}
	protected double getV(int vidx){
	    return vvalues[vidx];
	    //return (double)vidx/vnum;
	}
	protected IVec2 getPoint(int uidx, int vidx){
	    return new IVec2(getU(uidx), getV(vidx));
	}
	protected IVec2 getPoint(SubsurfaceIndex idx){
	    return new IVec2(getU(idx.uindex), getV(idx.vindex));
	}
	
	protected boolean isOnCorner(IVec2 pt){ return isOnUEdge(pt) && isOnVEdge(pt); }
	protected boolean isOnEdge(IVec2 pt){ return isOnUEdge(pt) || isOnVEdge(pt); }
	protected boolean isOnUEdge(IVec2 pt){
	    return pt.y == getV(getVIndex(pt));
	    //return pt.y*vnum == getVIndex(pt);
	}
	protected boolean isOnVEdge(IVec2 pt){
	    return pt.x == getU(getUIndex(pt));
	    //return pt.x*unum == getUIndex(pt);
	}
	
	protected SubsurfaceIndex getIndexOnPoint(IVec2 pt){
	    return new SubsurfaceIndex(getUIndex(pt), getVIndex(pt));
	}
	
	/**
	   returning SubsurfaceIndex between 2 points not including the index where each point is
	*/
	protected SubsurfaceIndex[] getIndexOnLine(IVec2 pt1, IVec2 pt2){
	    int u1 = getUIndex(pt1);
	    int v1 = getVIndex(pt1);
	    int u2 = getUIndex(pt2);
	    int v2 = getVIndex(pt2);
	    
	    if(u1==u2 && v1==v2) return null;
	    
	    if(u1==u2){
		if(Math.abs(v1-v2)==1) return null;
		int inc=1;
		if(v1>v2) inc=-1;
		int num = Math.abs(v1-v2)-1;
		SubsurfaceIndex[] array = new SubsurfaceIndex[num];
		for(int i=0; i<num; i++){
		    array[i] = new SubsurfaceIndex(u1, v1+(i+1)*inc);
		}
		return array;
	    }
	    
	    if(v1==v2){
		if(Math.abs(u1-u2)==1) return null;
		int inc=1;
		if(u1>u2) inc=-1;
		int num = Math.abs(u1-u2)-1;
		SubsurfaceIndex[] array = new SubsurfaceIndex[num];
		for(int i=0; i<num; i++){
		    array[i] = new SubsurfaceIndex(u1+(i+1)*inc, v1);
		}
		return array;
	    }
	    
	    
	    SubsurfaceIndex sindex=getNextIndexOnLine(pt1, pt2, u1, v1);
	    ArrayList<SubsurfaceIndex> indices = new ArrayList<SubsurfaceIndex>();
	    while(sindex!=null &&
		  !sindex.equals(u2,v2) &&
		  pt1.diff(pt2).dot
		  (getPoint(sindex).mid
		   (getPoint(sindex.uindex+1,sindex.vindex+1)).diff(pt2)) > 0 ){
		indices.add(sindex);
		sindex=getNextIndexOnLine(pt1, pt2, sindex.uindex, sindex.vindex);
	    }
	    
	    return indices.toArray(new SubsurfaceIndex[indices.size()]);
	}
	
	
	protected SubsurfaceIndex getNextIndexOnLine(IVec2 linePt1, IVec2 linePt2,
						     int uidx, int vidx){
	    if(linePt1.x < linePt2.x){
		if(uidx<unum){
		    if(vidx<vnum && isLineOnVEdge(linePt1,linePt2,uidx+1,vidx,vidx+1))
			// horizontal
			return new SubsurfaceIndex(uidx+1, vidx);
		    if(isLineOnPoint(linePt1,linePt2,uidx+1,vidx)){
			// diagonal
			if(vidx>0 && linePt1.y > linePt2.y) return new SubsurfaceIndex(uidx+1, vidx-1);
			// horizontal
			return new SubsurfaceIndex(uidx+1, vidx);
		    }
		    if(vidx<vnum && isLineOnPoint(linePt1,linePt2,uidx+1,vidx+1)){
			// diagonal
			if(linePt1.y < linePt2.y) return new SubsurfaceIndex(uidx+1, vidx+1);
			// horizontal
			return new SubsurfaceIndex(uidx+1, vidx);
		    }
		}
	    }
	    else{
		if(uidx>0){
		    if(vidx<vnum && isLineOnVEdge(linePt1,linePt2,uidx,vidx,vidx+1))
			// horizontal
			return new SubsurfaceIndex(uidx-1, vidx);
		    if(vidx>0 && isLineOnPoint(linePt1,linePt2,uidx,vidx)){
			// diagonal
			if(linePt1.y > linePt2.y) return new SubsurfaceIndex(uidx-1, vidx-1);
			// horizontal
			return new SubsurfaceIndex(uidx-1, vidx);
		    }
		    if(vidx<vnum && isLineOnPoint(linePt1,linePt2,uidx,vidx+1)){
			// diagonal
			if(linePt1.y < linePt2.y) return new SubsurfaceIndex(uidx-1, vidx+1);
			// horizontal
			return new SubsurfaceIndex(uidx-1, vidx);
		    }
		}
	    }
	    	    
	    if(linePt1.y < linePt2.y){
		if(vidx<vnum){
		    if(uidx<unum && isLineOnUEdge(linePt1,linePt2,uidx,uidx+1,vidx+1))
			// vertical
			return new SubsurfaceIndex(uidx, vidx+1);
		    if(isLineOnPoint(linePt1,linePt2,uidx,vidx+1)){
			// diagonal
			if(linePt1.x > linePt2.x) return new SubsurfaceIndex(uidx-1, vidx+1);
			// vertical
			return new SubsurfaceIndex(uidx, vidx+1);
		    }
		    if(uidx<unum && isLineOnPoint(linePt1,linePt2,uidx+1,vidx+1)){
			// diagonal
			if(linePt1.x < linePt2.x) return new SubsurfaceIndex(uidx+1, vidx+1);
			// vertical
			return new SubsurfaceIndex(uidx, vidx+1);
		    }
		}
	    }
	    else{
		if(vidx>0){
		    if(uidx<unum && isLineOnUEdge(linePt1,linePt2,uidx,uidx+1,vidx))
			// vertical
			return new SubsurfaceIndex(uidx, vidx-1);
		    if(isLineOnPoint(linePt1,linePt2,uidx,vidx)){
			// diagonal
			if(linePt1.x > linePt2.x) return new SubsurfaceIndex(uidx-1, vidx-1);
			// vertical
			return new SubsurfaceIndex(uidx, vidx-1);
		    }
		    if(uidx<unum && isLineOnPoint(linePt1,linePt2,uidx+1,vidx)){
			// diagonal
			if(linePt1.x < linePt2.x) return new SubsurfaceIndex(uidx+1, vidx-1);
			// vertical
			return new SubsurfaceIndex(uidx, vidx-1);
		    }
		}
	    }
	    
	    //IOut.p("ERROR: no edge crossing at index <"+uidx+", "+vidx+">");
	    return null;
	}
	
	protected boolean isLineOnPoint(IVec2 linePt1, IVec2 linePt2,
					int uidx, int vidx){
	    return linePt2.diff(linePt1).cross(getPoint(uidx,vidx).diff(linePt1)).z == 0;
	}

	protected boolean isLineOnEdge(IVec2 linePt1, IVec2 linePt2,
				       IVec2 edgePt1, IVec2 edgePt2){
	    IVec2 ldiff = linePt2.diff(linePt1);
	    double z1 = ldiff.cross(edgePt1.diff(linePt1)).z;
	    double z2 = ldiff.cross(edgePt2.diff(linePt1)).z;
	    return z1*z2 < 0; // < for not including on the end points
	}
	
	protected boolean isLineOnUEdge(IVec2 linePt1, IVec2 linePt2,
					int uidx1, int uidx2, int vidx){
	    return isLineOnEdge(linePt1, linePt2,
				getPoint(uidx1,vidx), getPoint(uidx2,vidx));
	}
	
	protected boolean isLineOnVEdge(IVec2 linePt1, IVec2 linePt2,
					int uidx, int vidx1, int vidx2){
	    return isLineOnEdge(linePt1, linePt2,
				getPoint(uidx,vidx1), getPoint(uidx,vidx2));
	}
	
	protected boolean isPointOnLeft(IVec2 pt, IVec2 linePt1, IVec2 linePt2){
	    return linePt2.diff(linePt1).cross(pt.diff(linePt1)).z>0;
	}
    }
    
    public static class SubsurfaceIndex{
	public int uindex, vindex;
	public SubsurfaceIndex(int u, int v){ uindex=u; vindex=v; }
	public boolean equals(int u, int v){ return uindex==u&&vindex==v; }
	public boolean equals(SubsurfaceIndex idx){
	    return uindex==idx.uindex&&vindex==idx.vindex;
	}
	/*
	public boolean equals(Object idx){
	    //IOut.p("equals(Object)");
	    if(idx instanceof SubsurfaceIndex){
		return equals((SubsurfaceIndex)idx);
	    }
	    return super.equals(idx);
	}
	*/
    }
    
    
    
}
    
