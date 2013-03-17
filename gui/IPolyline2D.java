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

import java.util.ArrayList;

import igeo.*;

import static igeo.IConfig.parameterTolerance;

/**
   2D polyline geometry to assist graphic classes of curves and surfaces.
   
   @author Satoru Sugihara
*/
public class IPolyline2D{
    public IVec2[] pts;
    public boolean closed=false;
    
    public IVec2 min=null, max=null;
    
    public IPolyline2D(){}
    public IPolyline2D(int num){ pts = new IVec2[num]; }
    public IPolyline2D(IVec2[] pts){ this.pts = pts; }
    public IPolyline2D(IVec2 pt1, IVec2 pt2){
	pts = new IVec2[2];
	pts[0] = pt1;
	pts[1] = pt2;
    }
    public IPolyline2D(IVec2 pt){
	pts = new IVec2[1];
	pts[0] = pt;
    }
        
    public IPolyline2D(IPolyline2D pl){
	pts = new IVec2[pl.num()];
	for(int i=0; i<pts.length; i++) pts[i]=pl.get(i);
	closed = pl.isClosed();
	min = pl.min;
	max = pl.max;
    }
    
    public IPolyline2D dup(){ return new IPolyline2D(this); }
    
    public int num(){ return pts.length; }
    
    public IVec2 get(int i){ return pts[i]; }
    
    public IVec2 start(){ return pts[0]; }
    public IVec2 end(){ return pts[pts.length-1]; }
    
    public IVec2[] get(){ return pts; }
    
    public void set(int i, IVec2I pt){ pts[i]=pt.get(); }
    public void set(int i, IVec2 pt){ pts[i]=pt; }
    
    public int indexOf(IVec2 pt){
	for(int i=0; i<pts.length; i++) if(pts[i] == pt) return i;
	return -1; //
    }
    
    public void checkBoundary(){
	for(int i=0; i<pts.length; i++){
	    if(i==0){
		min = pts[i].dup();
		max = pts[i].dup();
	    }
	    else{
		if(pts[i].x < min.x) min.x = pts[i].x;
		else if(pts[i].x > max.x) max.x = pts[i].x;
		
		if(pts[i].y < min.y) min.y = pts[i].y;
		else if(pts[i].y > max.y) max.y = pts[i].y;
	    }
	}
    }
    public IVec2 getMinBoundary(){ if(min==null) checkBoundary(); return min; }
    public IVec2 getMaxBoundary(){ if(max==null) checkBoundary(); return max; }
    
    public boolean isClosed(){ return closed; }
    public void close(){
	closed=true;
	// not checking end point because too dengerous when the resolution is very low
	/*
	// check if there's duplicates in the beginning and the end
	if(pts[0].eq(pts[pts.length-1],parameterTolerance)){
	    IVec2[] pts2 = new IVec2[pts.length-1];
	    for(int i=0; i<pts.length-1; i++) pts2[i] = pts[i];
	    pts = pts2;
	}
	*/
    }
    
    public void open(){ closed=false; }
    
    public void add(IVec2 pt){ insert(pts.length,pt); }
    
    public void insert(int idx, IVec2 pt){
	IVec2[] pts2 = new IVec2[pts.length+1];
	for(int i=0; i<idx; i++) pts2[i] = pts[i];
	pts2[idx] = pt;
	for(int i=idx; i<pts.length; i++) pts2[i+1]=pts[i];
	pts = pts2;
    }
    
    public void append(IPolyline2D l){ append(l.pts); }
    
    public void append(IPolyline2D l, boolean reverseDir){ append(l.pts, reverseDir); }
    
    public void append(IVec2[] pts2){ append(pts2,false); }
    
    public void append(IVec2[] pts2, boolean reverseDir){
	if(pts==null || pts.length==0){
	    pts = new IVec2[pts2.length];
	    if(!reverseDir) for(int i=0; i<pts.length; i++) pts[i] = pts2[i];
	    else for(int i=0; i<pts.length; i++) pts[i] = pts2[pts2.length-1-i];
	    return;
	}
	
	if(reverseDir){
	    int startIdx=pts2.length-1;
	    int endIdx = 0;
	    if(pts[pts.length-1].eq(pts2[pts2.length-1],parameterTolerance)) startIdx=pts2.length-2;
	    if(pts[0].eq(pts2[0],parameterTolerance)){ endIdx=1; } // remove duplicated closing pt
	    
	    IVec2[] pts3 = new IVec2[pts.length+startIdx-endIdx+1];
	    
	    for(int i=0; i<pts.length; i++) pts3[i] = pts[i];
	    for(int i=startIdx; i>=endIdx; i--) pts3[pts.length + (startIdx-i) ] = pts2[i];
	    
	    pts = pts3;
	}
	else{
	    int startIdx=0;
	    int endIdx = pts2.length-1;
	    if(pts[pts.length-1].eq(pts2[0],parameterTolerance)) startIdx=1;
	    if(pts[0].eq(pts2[pts2.length-1],parameterTolerance)){ endIdx=pts2.length-2; } // remove duplicated closing pt
	    
	    IVec2[] pts3 = new IVec2[pts.length+endIdx-startIdx+1];
	    
	    for(int i=0; i<pts.length; i++) pts3[i] = pts[i];
	    for(int i=startIdx; i<=endIdx; i++) pts3[i-startIdx+pts.length] = pts2[i];
	    
	    pts = pts3;
	}
    }
    
    
    public void reverse(){
	IVec2[] pts2 = new IVec2[pts.length];
	for(int i=0; i<pts.length; i++) pts2[i] = pts[pts.length-i-1];
	pts = pts2;
    }
    
    
    /**
       join polylines without checking end points but removing end point of each line replaced with next start point
    */
    public static IPolyline2D join(IPolyline2D[] lines, boolean close){
	int num = 0;
	for(int i=0; i<lines.length; i++) num += lines[i].num()-1;
	if(!close) num++;
	
	IPolyline2D polyline = new IPolyline2D(num);
	int idx=0;
	for(int i=0; i<lines.length; i++){
	    for(int j=0; j<lines[i].num()-1; j++){
		polyline.set(idx, lines[i].get(j));
		idx++;
	    }
	}
	if(!close) polyline.set(num-1, lines[lines.length-1].end());
	if(close) polyline.closed=true;
	
	IOut.p("joined polyline: "+polyline); //
	return polyline;
    }
    
    
    public IVec2[] intersect(IPolyline2D line){
	
	ArrayList<IVec2> intersects = new ArrayList<IVec2>();
	for(int i=0; closed&&i<pts.length || !closed&&i<pts.length-1; i++){
	    IVec2 isct = IVec2.intersectPolyline(pts[i], pts[(i+1)%pts.length], line.get());
	    if(isct!=null) intersects.add(isct);
	}
	
	if(intersects.size()==0) return null;
	
	IVec2[] retval = new IVec2[intersects.size()];
	for(int i=0; i<intersects.size(); i++) retval[i] = intersects.get(i);
	return retval;
    }
    
    
    public void removeStraightPoints(){
	IVec2I[] pts2 = IVec2.removeStraightPoints(pts,closed);
	pts = new IVec2[pts2.length];
	for(int i=0; i<pts2.length; i++) pts[i] = pts2[i].get();
    }
    
    public void removeDuplicatedPoints(){
	//IVec2.removeDuplicatedPoints(pts,closed);
	IVec2I[] pts2 = IVec2.removeDuplicatedPoints(pts,closed);
	pts = new IVec2[pts2.length];
	for(int i=0; i<pts2.length; i++) pts[i] = pts2[i].get();
    }
    
    /*
    static IVec2[] getPairsInsidePolygon(IVec2[] pts, IVec2[] polygon){
	if(pts.length==1) return null;
	
	ArrayList<IVec2> segments = new ArrayList<IVec2>();
	for(int i=0; i<pts.length; i++) segments.add(pts[i]);
	
	ISort.sort(segments, new XComparator());
	
	for(int i=0; i<pts.length; i++) pts[i] = segments.get(i);
	
	segments.clear();
	for(int j=0; j<pts.length-1; j++){
	    IVec2 pt1 = null;
	    if(j==0) pt1 = pts[j].dup().add(pts[j].diff(pts[j+1]).div(2));
	    else pt1 = pts[j-1].mid(pts[j]);
	    IVec2 pt2 = pts[j].mid(pts[j+1]);
	    IVec2 pt3 = null;
	    if(j+2 < pts.length) pt3 = pts[j+1].mid(pts[j+2]);
	    else pt3 = pts[j+1].dup().add(pts[j+1].diff(pts[j]).div(2));
	    
	    boolean inside1 = pt1.isInside(polygon);
	    boolean inside2 = pt2.isInside(polygon);
	    boolean inside3 = pt3.isInside(polygon);
	    
	    if(!inside1&&inside2&&!inside3){
		segments.add(pts[j]);
		segments.add(pts[j+1]);
		j++;
	    }
	    // else ; // in all other cases, pts[j] is skipped
	}
	
	if(segments.size()==0) return null;
	
	IVec2[] retval = new IVec2[segments.size()];
	for(int i=0; i<segments.size(); i++) retval[i] = segments.get(i);
	return retval;
    }
    */
    
    
    public static Intersection[] getPairsInsidePolygon(Intersection[] pts, IVec2[] polygon, boolean xOrY){
	if(pts.length==1) return null;
	
	ArrayList<Intersection> segments = new ArrayList<Intersection>();
	for(int i=0; i<pts.length; i++) segments.add(pts[i]);
	
	if(xOrY) ISort.sort(segments, new XComparator());
	else  ISort.sort(segments, new YComparator());
	
	for(int i=0; i<pts.length; i++) pts[i] = segments.get(i);
	
	segments.clear();
	for(int j=0; j<pts.length-1; j++){
	    IVec2 pt1 = null;
	    if(j==0) pt1 = pts[j].dup().add(pts[j].diff(pts[j+1]).div(2));
	    else pt1 = pts[j-1].mid(pts[j]);
	    IVec2 pt2 = pts[j].mid(pts[j+1]);
	    IVec2 pt3 = null;
	    if(j+2 < pts.length) pt3 = pts[j+1].mid(pts[j+2]);
	    else pt3 = pts[j+1].dup().add(pts[j+1].diff(pts[j]).div(2));
	    
	    boolean inside1 = pt1.isInside(polygon);
	    boolean inside2 = pt2.isInside(polygon);
	    boolean inside3 = pt3.isInside(polygon);
	    
	    if(!inside1&&inside2&&!inside3){
		segments.add(pts[j]);
		segments.add(pts[j+1]);
		j++;
	    }
	    // else ; // in all other cases, pts[j] is skipped
	}
	
	if(segments.size()==0) return null;
	
	Intersection[] retval = new Intersection[segments.size()];
	for(int i=0; i<segments.size(); i++) retval[i] = segments.get(i);
	return retval;

    }
    
    
    public static IPolyline2D[] yLineInside(double x, IPolyline2D[] outlines, IPolyline2D[] holes){
	
	if(outlines==null || outlines.length==0) return null;
	
	//ArrayList<IVec2> iscts = new ArrayList<IVec2>();
	ArrayList<Intersection> iscts = new ArrayList<Intersection>();
	for(int i=0; i<outlines.length; i++){
	    //IVec2[] isct = intersectPolylineAndYLine(outlines[i].get(),true,false,x);
	    Intersection[] isct = outlines[i].intersectPolylineAndYLine(false,x);
	    if(isct!=null && isct.length%2!=0){
		IOut.err("(x="+x+") number of intersection with outlines["+i+"] is not even (#="+isct.length+"). it must miss some intersection");
		// trying to salvage pair of points inside the outline
		isct = getPairsInsidePolygon(isct, outlines[i].get(),false);
	    }
	    for(int j=0; isct!=null && j<isct.length; j++) iscts.add(isct[j]);
	}
	
	for(int i=0; holes!=null && i<holes.length; i++){
	    //IVec2[] isct = intersectPolylineAndYLine(holes[i].get(),true,true,x);
	    Intersection[] isct = holes[i].intersectPolylineAndYLine(true,x);
	    if(isct!=null && isct.length%2!=0){
		IOut.err("(x="+x+") number of intersection with holes["+i+"] is not even (#="+isct.length+"). it must miss some intersection");
		// trying to salvage pair of points inside the outline
		isct = getPairsInsidePolygon(isct, holes[i].get(),false);
	    }
	    for(int j=0; isct!=null && j<isct.length; j++) iscts.add(isct[j]);
	}
	
	if(iscts.size()%2 != 0) IOut.err("num of intersection should be even number ("+iscts.size()+")");
	
	if(iscts.size()<=1) return null;
	
	ISort.sort(iscts,new YComparator());
	
	IPolyline2D[] lines = new IPolyline2D[iscts.size()/2];
	for(int i=0; i<iscts.size()/2; i++)
	    lines[i] = new IPolyline2D(iscts.get(i*2), iscts.get(i*2+1));
	return lines;
    }
    
    public static IPolyline2D[] xLineInside(double y, IPolyline2D[] outlines, IPolyline2D[] holes){
	if(outlines==null || outlines.length==0) return null;
	
	//ArrayList<IVec2> iscts = new ArrayList<IVec2>();
	ArrayList<Intersection> iscts = new ArrayList<Intersection>();
	for(int i=0; i<outlines.length; i++){
	    //IVec2[] isct = intersectPolylineAndXLine(outlines[i].get(),true,false,y);
	    Intersection[] isct = outlines[i].intersectPolylineAndXLine(false,y);
	    if(isct!=null && isct.length%2!=0){
		IOut.err("(y="+y+") number of intersection with outlines["+i+"] is not even (#="+isct.length+"). it must miss some intersection");
		
		// trying to salvage pair of points inside the outline
		isct = getPairsInsidePolygon(isct, outlines[i].get(),true);
	    }
	    for(int j=0; isct!=null && j<isct.length; j++) iscts.add(isct[j]);
	}
	
	for(int i=0; holes!=null && i<holes.length; i++){
	    //IVec2[] isct = intersectPolylineAndXLine(holes[i].get(),true,true,y);
	    Intersection[] isct = holes[i].intersectPolylineAndXLine(true,y);
	    if(isct!=null && isct.length%2!=0){
		IOut.err("(y="+y+") number of intersection with holes["+i+"] is not even (#="+isct.length+"). it must miss some intersection");
		
		// trying to salvage pair of points inside the hole
		isct = getPairsInsidePolygon(isct, holes[i].get(),true);
	    }
	    for(int j=0; isct!=null && j<isct.length; j++) iscts.add(isct[j]);
	}

	if(iscts.size()%2 != 0) IOut.err("num of intersection should be even number ("+iscts.size()+")");
	
	if(iscts.size()<=1) return null;
	
	ISort.sort(iscts,new XComparator());
	
	IPolyline2D[] lines = new IPolyline2D[iscts.size()/2];
	for(int i=0; i<iscts.size()/2; i++) lines[i] = new IPolyline2D(iscts.get(i*2), iscts.get(i*2+1));
	return lines;
    }
    
    /*
    static Intersection[] yIntersectionInside(double x, IPolyline2D[] outlines, IPolyline2D[] holes){
	
	if(outlines==null || outlines.length==0) return null;
	
	ArrayList<Intersection> iscts = new ArrayList<Intersection>();
		
	for(int i=0; i<outlines.length; i++){
	    Intersection[] isct = outlines[i].intersectPolylineAndYLine(false,x);
	    if(isct!=null && isct.length%2!=0){
		IOut.err("(x="+x+") number of intersection with outlines["+i+"] is not even (#="+isct.length+"). it must miss some intersection");
		// trying to salvage pair of points inside the outline
		isct = getPairsInsidePolygon(isct, outlines[i].get());
	    }
	    for(int j=0; isct!=null && j<isct.length; j++) iscts.add(isct[j]);
	}
	
	for(int i=0; holes!=null && i<holes.length; i++){
	    Intersection[] isct = holes[i].intersectPolylineAndYLine(true,x);
	    if(isct!=null && isct.length%2!=0){
		IOut.err("(x="+x+") number of intersection with holes["+i+"] is not even (#="+isct.length+"). it must miss some intersection");
		// trying to salvage pair of points inside the outline
		isct = getPairsInsidePolygon(isct, holes[i].get());
	    }
	    for(int j=0; isct!=null && j<isct.length; j++) iscts.add(isct[j]);
	}
	
	ISort.sort(iscts,new YIntersectionComparator());
	
	if(iscts.size()%2 != 0) IOut.err("num of intersection should be even number ("+iscts.size()+")");
	
	Intersection[] retval = new Intersection[(iscts.size()/2)*2];
	for(int i=0; i<(iscts.size()/2)*2; i++) retval[i] = iscts.get(i);
	return retval;
    }
    
    static Intersection[] xIntersectionInside(double y, IPolyline2D[] outlines, IPolyline2D[] holes){
	if(outlines==null || outlines.length==0) return null;
	
	ArrayList<Intersection> iscts = new ArrayList<Intersection>();
	for(int i=0; i<outlines.length; i++){
	    Intersection[] isct = outlines[i].intersectPolylineAndXLine(false,y);
	    if(isct!=null && isct.length%2!=0){
		IOut.err("(y="+y+") number of intersection with outlines["+i+"] is not even (#="+isct.length+"). it must miss some intersection");
		
		// trying to salvage pair of points inside the outline
		isct = getPairsInsidePolygon(isct, outlines[i].get());
	    }
	    for(int j=0; isct!=null && j<isct.length; j++) iscts.add(isct[j]);
	}
	
	for(int i=0; holes!=null && i<holes.length; i++){
	    Intersection[] isct = holes[i].intersectPolylineAndXLine(true,y);
	    if(isct!=null && isct.length%2!=0){
		IOut.err("(y="+y+") number of intersection with holes["+i+"] is not even (#="+isct.length+"). it must miss some intersection");
		
		// trying to salvage pair of points inside the hole
		isct = getPairsInsidePolygon(isct, holes[i].get());
	    }
	    for(int j=0; isct!=null && j<isct.length; j++) iscts.add(isct[j]);
	}
	
	ISort.sort(iscts,new XIntersectionComparator());
	
	if(iscts.size()%2 != 0) IOut.err("num of intersection should be even number ("+iscts.size()+")");
	
	Intersection[] retval = new Intersection[(iscts.size()/2)*2];
	for(int i=0; i<(iscts.size()/2)*2; i++) retval[i] = iscts.get(i);
	return retval;
    }
    */
    
    
    /**
       when pt2 and pt3 are on X line, get a point which is inside/outisde intersection
       pt1 and pt4 can be null
       
       @param pt1 first point of polyline
       @param pt2 second point of polyline; pt2 and pt3 are both on the same x
       @param pt3 third point of polyline; pt2 and pt3 are both on the same x
       @param pt4 fourth point of polyline
       @param intersectPt intersecting point between pt2 and pt3
       @param normalDir direction of closing polygon if it's turning right screw direction or not
       @param inside getting inner or outer intersection point depending on normalDir
    */
    public static IVec2I intersectXEdgeAndXLine(IVec2I pt1, IVec2I pt2,
						IVec2I pt3, IVec2I pt4,
						IVec2I intersectPt,
						boolean normalDir,
						boolean inside){
	double yi=intersectPt.y();
	double y2=pt2.y();
	double y3=pt3.y();
	if(Math.abs(y2-y3)>=IConfig.tolerance){
	    IOut.err("pt2 and pt3 are not on x line");
	    return null;
	}
	if(pt1==null && pt4==null) return pt2; // whichever point
	
	double y1=0, y4=0;
	if(pt1!=null) y1 = pt1.y();
	if(pt4!=null) y4 = pt4.y();
	
	if( pt1!=null && pt4!=null && (y1 < yi && y4 < yi ||  y1 > yi && y4 > yi) )
	    return null; // not intersecting
	
	if(Math.abs(pt2.x()-pt3.x()) < IConfig.tolerance) return intersectPt;
	
	IVec2I upperPt = pt2;
	IVec2I lowerPt = pt3;
	if(pt2.x()<pt3.x()){ upperPt=pt3; lowerPt=pt2; }
	
	if(  (pt1==null || y1<y2) && (pt4==null || y4>y2) ){
	    if(normalDir&&inside || !normalDir&&!inside) return upperPt;
	    return lowerPt;
	}
	if(normalDir&&inside || !normalDir&&!inside) return lowerPt;
	return upperPt;
    }
    
    
    /**
       when pt2 and pt3 are on y line, get a point which is inside/outisde intersection
       pt1 and pt4 can be null
       
       @param pt1 first point of polyline
       @param pt2 second point of polyline; pt2 and pt3 are both on the same y
       @param pt3 third point of polyline; pt2 and pt3 are both on the same y
       @param pt4 fourth point of polyline
       @param intersectPt intersecting point between pt2 and pt3
       @param normalDir direction of closing polygon if it's turning right screw direction or not
       @param inside getting inner or outer intersection point depending on normalDir
    */
    public static IVec2I intersectYEdgeAndYLine(IVec2I pt1, IVec2I pt2,
						IVec2I pt3, IVec2I pt4,
						IVec2I intersectPt,
						boolean normalDir,
						boolean inside){
	double xi = intersectPt.x();
	double x2=pt2.x();
	double x3=pt3.x();
	if(Math.abs(x2-x3)>=IConfig.tolerance){
	    IOut.err("pt2 and pt3 are not on y line");
	    return null;
	}
	if(pt1==null && pt4==null) return intersectPt; //return pt2; // whichever point
	
	double x1=0, x4=0;
	if(pt1!=null) x1 = pt1.x();
	if(pt4!=null) x4 = pt4.x();
	
	if( pt1!=null && pt4!=null && (x1 < xi && x4 < xi ||  x1 > xi && x4 > xi) )
	    return null; // not intersecting
	
	if(Math.abs(pt2.y()-pt3.y()) < IConfig.tolerance) return intersectPt;
	
	IVec2I upperPt = pt2;
	IVec2I lowerPt = pt3;
	if(pt2.y()<pt3.y()){ upperPt=pt3; lowerPt=pt2; }
	
	if(  (pt1==null || x1<x2) && (pt4==null || x4>x2) ){
	    if(normalDir&&inside || !normalDir&&!inside) return upperPt;
	    return lowerPt;
	}
	if(normalDir&&inside || !normalDir&&!inside) return lowerPt;
	return upperPt;
    }
    
    
    /**
       @param inside when the polyline has an edge parallel to x axis, if argument inside is true, it returns inner end point of the edge, otherwise outside
    */
    /*
    public static IVec2[] intersectPolylineAndXLine(IVec2I[] pts,
						     boolean closed,
						     boolean inside,
						     double y){
	int num = pts.length;
	boolean normalDir = isNormalPositive(pts,closed);
	ArrayList<IVec2> ipts = new ArrayList<IVec2>();
	for(int i=0; i<num-1 || closed&&i<num; i++){
	    IVec2 isct = IVec2.intersectSegmentAndXLine(pts[i],pts[(i+1)%num],y);
	    if(isct!=null){
		if(isct.eqY(pts[(i+1)%num],parameterTolerance)){
		    if( closed && i==num-1 && ipts.size()>0 && ipts.get(0).eq(isct,parameterTolerance)); // nothing
		    else if((!closed && i<num-2 || closed ) &&
			    pts[(i+1)%num].get().eqY(pts[(i+2)%num],parameterTolerance)){
			IVec2I pt3 = null;
			if(!closed&&i+3<num || closed) pt3 = pts[(i+3)%num];
			// pts[i+1] and pts[i+2] is straight in X direction
			IVec2I ept = intersectXEdgeAndXLine(pts[i], pts[(i+1)%num],
							     pts[(i+2)%num], pt3,
							     isct,normalDir, inside);
			if(ept!=null) ipts.add(ept.get().dup());
			i += 2; // skip next two edges
		    }
		    else if(i==num-2&&!closed ||
			    pts[i].y() < y && pts[(i+2)%num].y() > y ||
			    pts[i].y() > y && pts[(i+2)%num].y() < y ){
			ipts.add(isct);
			i++;
		    }
		    else{ i++; }// skip if it's just touching at the kink
		}
		//else if(isct.eq(pts[i],parameterTolerance)){
		else if(isct.eqY(pts[i],parameterTolerance)){
		    if( pts[i].get().eqY(pts[(i+1)%num],parameterTolerance)){
			if(!closed){
			    IVec2I pt0 = null, pt3 = null;
			    if(i>0) pt0 = pts[i-1];
			    if(i+2<num) pt3=pts[i+2];
			    IVec2I ept = intersectXEdgeAndXLine(pt0, pts[i], pts[i+1], pt3,
								 isct,normalDir, inside);
			    if(ept!=null) ipts.add(ept.get().dup());
			}
			//else{} // if it's closed, this will be revisited at the end of the loop
			i+=2; // skip next two edges
		    }
		    // skip if it's just touching at the kink
		    else if( i==0&&!closed ||
			     pts[(i-1+num)%num].y() < y && pts[(i+1)%num].y() > y ||
			     pts[(i-1+num)%num].y() > y && pts[(i+1)%num].y() < y ){
			ipts.add(isct); // crossing
		    }
		    else{} // not crosing; just touching
		}
		else{ // intersect in the middle
		    ipts.add(isct);
		}
	    }
	}
	
	if(ipts.size()==0) return null;
	IVec2[] retval = new IVec2[ipts.size()];
	for(int i=0; i<ipts.size(); i++) retval[i] = ipts.get(i);
	return retval;
    }
    */
    
    
    /**
       @param inside when the polyline has an edge parallel to y axis, if argument inside is true, it returns inner end point of the edge, otherwise outside
    */
    /*
    public static IVec2[] intersectPolylineAndYLine(IVec2I[] pts,
						     boolean closed,
						     boolean inside,
						     double x){
	int num = pts.length;
	boolean normalDir = isNormalPositive(pts,closed);
	ArrayList<IVec2> ipts = new ArrayList<IVec2>();
	for(int i=0; i<num-1 || closed&&i<num; i++){
	    IVec2 isct = IVec2.intersectSegmentAndYLine(pts[i],pts[(i+1)%num],x);
	    if(isct!=null){
		//if(isct.eq(pts[(i+1)%num],parameterTolerance)){
		if( isct.eqX(pts[(i+1)%num],parameterTolerance) ){
		    if( closed && i==num-1 && ipts.size()>0 && ipts.get(0).eq(isct,parameterTolerance)); // do nothing
		    else if((!closed && i<num-2 || closed ) &&
			    pts[(i+1)%num].get().eqX(pts[(i+2)%num],parameterTolerance) ){
			IVec2I pt3 = null;
			if(!closed&&i+3<num || closed) pt3 = pts[(i+3)%num];
			// pts[i+1] and pts[i+2] is straight in Y direction
			IVec2I ept = intersectYEdgeAndYLine(pts[i], pts[(i+1)%num],
							     pts[(i+2)%num], pt3,
							     isct,normalDir, inside);
			if(ept!=null) ipts.add(ept.get().dup());
			i += 2; // skip next two edges
		    }
		    else if(i==num-2&&!closed ||
			    pts[i].x() < x && pts[(i+2)%num].x() > x ||
			    pts[i].x() > x && pts[(i+2)%num].x() < x ){
			ipts.add(isct);
			i++;
		    }
		    else{ i++; } // skip if it's just touching at the kink
		}
		//else if(isct.eq(pts[i],parameterTolerance)){
		else if(isct.eqX(pts[i],parameterTolerance)){
		    if( pts[i].get().eqX(pts[(i+1)%num],parameterTolerance)){
			if(!closed){
			    IVec2I pt0 = null, pt3 = null;
			    if(i>0) pt0 = pts[i-1];
			    if(i+2<num) pt3=pts[i+2];
			    IVec2I ept = intersectYEdgeAndYLine(pt0, pts[i], pts[i+1], pt3,
								 isct,normalDir, inside);
			    if(ept!=null) ipts.add(ept.get().dup());
			}
			else{} // if it's closed, this will be revisited at the end of the loop
			i+=2; // skip next two edges
		    }
		    // skip if it's just touching at the kink
		    else if( i==0&&!closed ||
			     pts[(i-1+num)%num].x() < x && pts[(i+1)%num].x() > x ||
			     pts[(i-1+num)%num].x() > x && pts[(i+1)%num].x() < x ){
			ipts.add(isct); // crossing
		    }
		    //else{} // not crosing; just touching
		}
		else{ // intersect in the middle of edge
		    ipts.add(isct);
		}
	    }
	}
	if(ipts.size()==0) return null;
	IVec2[] retval = new IVec2[ipts.size()];
	for(int i=0; i<ipts.size(); i++) retval[i] = ipts.get(i);
	return retval;
    }
    */
    


    /**
       @return array of 3 points whose first element is the intersection point, the second is adjacent point of intersection in input pts, the third is another adjacent point. The second or third can be null when the intersection is happenning at the end of open polyline.
    */
    /*
    public static IVec2[][] intersectPolylineAndXLineWithAdjacentPoints(IVec2I[] pts,
									 boolean closed,
									 boolean inside,
									 double y){
	int num = pts.length;
	boolean normalDir = isNormalPositive(pts,closed);
	ArrayList<IVec2[]> ipts = new ArrayList<IVec2[]>();
	for(int i=0; i<num-1 || closed&&i<num; i++){
	    IVec2[] isct = new IVec2[3];
	    isct[0] = IVec2.intersectSegmentAndXLine(pts[i],pts[(i+1)%num],y);
	    isct[1] = pts[i].get();
	    isct[2] = pts[(i+1)%num].get();
	    
	    if(isct[0]!=null){
		if(isct[0].eqY(pts[(i+1)%num],parameterTolerance)){
		    if( closed && i==num-1 && ipts.size()>0 && ipts.get(0)[0].eq(isct[0],parameterTolerance)); // nothing
		    else if((!closed && i<num-2 || closed ) &&
			    pts[(i+1)%num].get().eqY(pts[(i+2)%num],parameterTolerance)){
			IVec2I pt3 = null;
			if(!closed&&i+3<num || closed) pt3 = pts[(i+3)%num];
			// pts[i+1] and pts[i+2] is straight in X direction
			IVec2I ept = intersectXEdgeAndXLine(pts[i], pts[(i+1)%num],
							     pts[(i+2)%num], pt3,
							     isct[0],normalDir, inside);
			if(ept!=null){
			    isct[0] = ept.get().dup();
			    if(ept==pts[(i+1)%num]){
				isct[1] = pts[i].get();
				isct[2] = pts[(i+2)%num].get();
			    }
			    else if(ept==pts[(i+2)%num]){
				isct[1] = pts[(i+1)%num].get();
				isct[2] = pt3.get();
			    }
			    else{ // ?
				isct[1] = pts[i].get();
				//isct[2] = pts[(i+1)%num].get();
				isct[2] = pts[(i+2)%num].get();
			    }
			    ipts.add(isct);
			}
			i += 2; // skip next two edges
		    }
		    else if(i==num-2&&!closed ||
			    pts[i].y() < y && pts[(i+2)%num].y() > y ||
			    pts[i].y() > y && pts[(i+2)%num].y() < y ){
			isct[1] = pts[i].get();
			//isct[2] = pts[(i+1)%num].get();
			isct[2] = pts[(i+2)%num].get();
			
			ipts.add(isct);
			
			i++;
		    }
		    else{ i++; }// skip if it's just touching at the kink
		}
		//else if(isct[0].eq(pts[i],parameterTolerance)){
		else if(isct[0].eqY(pts[i],parameterTolerance)){
		    if(i==0) isct[1] = null;
		    else isct[1] = pts[(i-1+num)%num].get();
		    
		    isct[2] = pts[(i+1)%num].get();
		    
		    if( pts[i].get().eqY(pts[(i+1)%num],parameterTolerance)){
			if(!closed){
			    IVec2I pt0 = null, pt3 = null;
			    if(i>0) pt0 = pts[i-1];
			    if(i+2<num) pt3=pts[i+2];
			    IVec2I ept = intersectXEdgeAndXLine(pt0, pts[i], pts[(i+1)%num], pt3,
								 isct[0],normalDir, inside);
			    if(ept!=null){
				isct[0] = ept.get().dup();
				
				if(ept==pts[i]){
				    isct[1] = pt0.get();
				    isct[2] = pts[(i+2)%num].get();
				}
				else if(ept==pts[(i+2)%num]){
				    isct[1] = pts[i].get();
				    isct[2] = pt3.get();
				}
				else{ // ?
				    if(i==0) isct[1] = null;
				    else isct[1] = pts[(i-1+num)%num].get();
				    isct[2] = pts[(i+1)%num].get();
				}
				
				ipts.add(isct);
			    }
			}
			//else{} // if it's closed, this will be revisited at the end of the loop
			i+=2; // skip next two edges
		    }
		    // skip if it's just touching at the kink
		    else if( i==0&&!closed ||
			     pts[(i-1+num)%num].y() < y && pts[(i+1)%num].y() > y ||
			     pts[(i-1+num)%num].y() > y && pts[(i+1)%num].y() < y ){
			
			if(i==0) isct[1] = null;
			else isct[1] = pts[(i-1+num)%num].get();
			isct[2] = pts[(i+1)%num].get();
			
			ipts.add(isct); // crossing
		    }
		    else{} // not crosing; just touching
		}
		else{ // intersect in the middle
		    ipts.add(isct);
		}
	    }
	}
	
	if(ipts.size()==0) return null;
	IVec2[][] retval = new IVec2[ipts.size()][];
	for(int i=0; i<ipts.size(); i++) retval[i] = ipts.get(i);
	return retval;
    }
    */
    
    
    /**
       @return array of 3 points whose first element is the intersection point, the second is adjacent point of intersection in input pts, the third is another adjacent point. The second or third can be null when the intersection is happenning at the end of open polyline.
    */
    /*
    public static IVec2[][] intersectPolylineAndYLineWithAdjacentPoints(IVec2I[] pts,
									 boolean closed,
									 boolean inside,
									 double x){
	int num = pts.length;
	boolean normalDir = isNormalPositive(pts,closed);
	ArrayList<IVec2[]> ipts = new ArrayList<IVec2[]>();
	for(int i=0; i<num-1 || closed&&i<num; i++){
	    IVec2[] isct = new IVec2[3];
	    isct[0] = IVec2.intersectSegmentAndYLine(pts[i],pts[(i+1)%num],x);
	    isct[1] = pts[i].get();
	    isct[2] = pts[(i+1)%num].get();
	    
	    if(isct[0]!=null){
		if(isct[0].eqX(pts[(i+1)%num],parameterTolerance)){
		    if( closed && i==num-1 && ipts.size()>0 && ipts.get(0)[0].eq(isct[0],parameterTolerance)); // nothing
		    else if((!closed && i<num-2 || closed ) &&
			    pts[(i+1)%num].get().eqX(pts[(i+2)%num],parameterTolerance)){
			IVec2I pt3 = null;
			if(!closed&&i+3<num || closed) pt3 = pts[(i+3)%num];
			// pts[i+1] and pts[i+2] is straight in Y direction
			IVec2I ept = intersectYEdgeAndYLine(pts[i], pts[(i+1)%num],
							     pts[(i+2)%num], pt3,
							     isct[0],normalDir, inside);
			if(ept!=null){
			    isct[0] = ept.get().dup();
			    if(ept==pts[(i+1)%num]){
				isct[1] = pts[i].get();
				isct[2] = pts[(i+2)%num].get();
			    }
			    else if(ept==pts[(i+2)%num]){
				isct[1] = pts[(i+1)%num].get();
				isct[2] = pt3.get();
			    }
			    else{ // ?
				isct[1] = pts[i].get();
				//isct[2] = pts[(i+1)%num].get();
				isct[2] = pts[(i+2)%num].get();
			    }
			    ipts.add(isct);
			}
			i += 2; // skip next two edges
		    }
		    else if(i==num-2&&!closed ||
			    pts[i].x() < x && pts[(i+2)%num].x() > x ||
			    pts[i].x() > x && pts[(i+2)%num].x() < x ){
			isct[1] = pts[i].get();
			//isct[2] = pts[(i+1)%num].get();
			isct[2] = pts[(i+2)%num].get();
			
			ipts.add(isct);
			i++;
		    }
		    else{ i++; }// skip if it's just touching at the kink
		}
		//else if(isct[0].eq(pts[i],parameterTolerance)){
		else if(isct[0].eqX(pts[i],parameterTolerance)){
		    if(i==0) isct[1] = null;
		    else isct[1] = pts[(i-1+num)%num].get();
		    
		    isct[2] = pts[(i+1)%num].get();
		    
		    if( pts[i].get().eqX(pts[(i+1)%num],parameterTolerance)){
			if(!closed){
			    IVec2I pt0 = null, pt3 = null;
			    if(i>0) pt0 = pts[i-1];
			    if(i+2<num) pt3=pts[i+2];
			    IVec2I ept = intersectYEdgeAndYLine(pt0, pts[i], pts[(i+1)%num], pt3,
								 isct[0],normalDir, inside);
			    if(ept!=null){
				isct[0] = ept.get().dup();
				
				if(ept==pts[i]){
				    isct[1] = pt0.get();
				    isct[2] = pts[(i+2)%num].get();
				}
				else if(ept==pts[(i+2)%num]){
				    isct[1] = pts[i].get();
				    isct[2] = pt3.get();
				}
				else{ // ?
				    if(i==0) isct[1] = null;
				    else isct[1] = pts[(i-1+num)%num].get();
				    isct[2] = pts[(i+1)%num].get();
				}
				
				ipts.add(isct);
			    }
			}
			//else{} // if it's closed, this will be revisited at the end of the loop
			i+=2; // skip next two edges
		    }
		    // skip if it's just touching at the kink
		    else if( i==0&&!closed ||
			     pts[(i-1+num)%num].x() < x && pts[(i+1)%num].x() > x ||
			     pts[(i-1+num)%num].x() > x && pts[(i+1)%num].x() < x ){
			
			if(i==0) isct[1] = null;
			else isct[1] = pts[(i-1+num)%num].get();
			isct[2] = pts[(i+1)%num].get();
			
			ipts.add(isct); // crossing
		    }
		    else{} // not crosing; just touching
		}
		else{ // intersect in the middle
		    ipts.add(isct);
		}
	    }
	}
	
	if(ipts.size()==0) return null;
	IVec2[][] retval = new IVec2[ipts.size()][];
	for(int i=0; i<ipts.size(); i++) retval[i] = ipts.get(i);
	return retval;
    }
    */
    
    /**
       gets intersection point with IPolyline2D instance and adjacent points
    */
    public Intersection[] intersectPolylineAndXLine(boolean inside, double y){
	int num = pts.length;
	boolean normalDir = isNormalPositive(pts,closed);
	ArrayList<Intersection> ipts = new ArrayList<Intersection>();
	for(int i=0; i<num-1 || closed&&i<num; i++){
	    IVec2 isctPt = IVec2.intersectSegmentAndXLine(pts[i],pts[(i+1)%num],y);
	    if(isctPt!=null){
		Intersection isct = new Intersection(this,isctPt,pts[i],pts[(i+1)%num]);
		isct.setDirectionX();
		if(isct.eqY(pts[(i+1)%num],parameterTolerance)){
		    if( closed && i==num-1 && ipts.size()>0 && ipts.get(0).eq(isct,parameterTolerance)); // nothing
		    else if((!closed && i<num-2 || closed ) && pts[(i+1)%num].eqY(pts[(i+2)%num],parameterTolerance)){
			IVec2 pt3 = null;
			if(!closed&&i+3<num || closed) pt3 = pts[(i+3)%num];
			// pts[i+1] and pts[i+2] is straight in X direction
			IVec2I ept = intersectXEdgeAndXLine(pts[i], pts[(i+1)%num],
							     pts[(i+2)%num], pt3,
							     isct,normalDir, inside);
			if(ept!=null){
			    isct.set(ept);
			    if(ept==pts[(i+1)%num]){
				isct.adjacent1 = pts[i];
				isct.adjacent2 = pts[(i+2)%num];
			    }
			    else if(ept==pts[(i+2)%num]){
				isct.adjacent1 = pts[(i+1)%num];
				isct.adjacent2 = pt3;
			    }
			    else{ // ?
				isct.adjacent1 = pts[i];
				// should pts[(i+1)%num] be skipped or not?
				//isct.adjacent2 = pts[(i+1)%num];
				isct.adjacent2 = pts[(i+2)%num];
			    }
			    ipts.add(isct);
			}
			i += 2; // skip next two edges
		    }
		    else if(i==num-2&&!closed ||
			    pts[i].y() < y && pts[(i+2)%num].y() > y ||
			    pts[i].y() > y && pts[(i+2)%num].y() < y ){
			isct.adjacent1 = pts[i];
			// should pts[(i+1)%num] be skipped or not?
			isct.adjacent2 = pts[(i+1)%num];
			//isct.adjacent2 = pts[(i+2)%num];
			ipts.add(isct);
			i++;
		    }
		    else{ i++; }// skip if it's just touching at the kink
		}
		//else if(isct.eq(pts[i],parameterTolerance)){
		else if(isct.eqY(pts[i],parameterTolerance)){
		    //if(i==0) isct.adjacent1 = null;
		    if(i==0&&!closed) isct.adjacent1 = null; // ?
		    else isct.adjacent1 = pts[(i-1+num)%num];
		    
		    isct.adjacent2 = pts[(i+1)%num];
		    
		    if( pts[i].eqY(pts[(i+1)%num],parameterTolerance)){
			if(!closed){
			    IVec2 pt0 = null, pt3 = null;
			    if(i>0) pt0 = pts[i-1];
			    if(i+2<num) pt3=pts[i+2];
			    IVec2I ept = intersectXEdgeAndXLine(pt0, pts[i], pts[(i+1)%num], pt3,
								 isct,normalDir, inside);
			    if(ept!=null){
				isct.set(ept);
				if(ept==pts[i]){
				    isct.adjacent1 = pt0;
				    isct.adjacent2 = pts[(i+2)%num];
				}
				else if(ept==pts[(i+2)%num]){
				    isct.adjacent1 = pts[i];
				    isct.adjacent2 = pt3;
				}
				else{ // ?
				    //if(i==0) isct.adjacent1 = null;
				    if(i==0&&!closed) isct.adjacent1 = null; // ?
				    else isct.adjacent1 = pts[(i-1+num)%num];
				    isct.adjacent2 = pts[(i+1)%num];
				}
				ipts.add(isct);
			    }
			}
			//else{} // if it's closed, this will be revisited at the end of the loop
			i+=2; // skip next two edges
		    }
		    // skip if it's just touching at the kink
		    else if( i==0&&!closed ||
			     pts[(i-1+num)%num].y() < y && pts[(i+1)%num].y() > y ||
			     pts[(i-1+num)%num].y() > y && pts[(i+1)%num].y() < y ){
			if(i==0&&!closed) isct.adjacent1 = null;
			else{
			    // should it skip pts[i] or not?
			    isct.adjacent1 = pts[i];
			    //isct.adjacent1 = pts[(i-1+num)%num];
			}
			isct.adjacent2 = pts[(i+1)%num];
			ipts.add(isct); // crossing
		    }
		    else{} // not crosing; just touching
		}
		else{ // intersect in the middle
		    ipts.add(isct);
		}
	    }
	}
	
	if(ipts.size()==0) return null;
	Intersection[] retval = new Intersection[ipts.size()];
	for(int i=0; i<ipts.size(); i++) retval[i] = ipts.get(i);
	return retval;
    }
    
    /**
       gets intersection point with IPolyline2D instance and adjacent points
    */
    public Intersection[] intersectPolylineAndYLine(boolean inside,double x){
	int num = pts.length;
	boolean normalDir = isNormalPositive(pts,closed);
	ArrayList<Intersection> ipts = new ArrayList<Intersection>();
	for(int i=0; i<num-1 || closed&&i<num; i++){
	    IVec2 isctPt = IVec2.intersectSegmentAndYLine(pts[i],pts[(i+1)%num],x);
	    if(isctPt!=null){
		Intersection isct = new Intersection(this,isctPt,pts[i],pts[(i+1)%num]);
		isct.setDirectionY();
		if(isct.eqX(pts[(i+1)%num],parameterTolerance)){
		    if( closed && i==num-1 && ipts.size()>0 && ipts.get(0).eq(isct,parameterTolerance)); // nothing
		    else if((!closed && i<num-2 || closed ) &&
			    pts[(i+1)%num].eqX(pts[(i+2)%num],parameterTolerance)){
			IVec2 pt3 = null;
			if(!closed&&i+3<num || closed) pt3 = pts[(i+3)%num];
			// pts[i+1] and pts[i+2] is straight in Y direction
			IVec2I ept = intersectYEdgeAndYLine(pts[i], pts[(i+1)%num],
							     pts[(i+2)%num], pt3,
							     isct,normalDir, inside);
			if(ept!=null){
			    isct.set(ept);
			    if(ept==pts[(i+1)%num]){
				isct.adjacent1 = pts[i];
				isct.adjacent2 = pts[(i+2)%num];
			    }
			    else if(ept==pts[(i+2)%num]){
				isct.adjacent1 = pts[(i+1)%num];
				isct.adjacent2 = pt3;
			    }
			    else{ // ?
				isct.adjacent1 = pts[i];
				// should pts[(i+1)%num] be skipped or not?
				//isct.adjacent2 = pts[(i+1)%num];
				isct.adjacent2 = pts[(i+2)%num];
			    }
			    
			    //IOut.p("intersect(1) = "+isct);
			    //IOut.p("adjacent1 = "+isct.adjacent1);
			    //IOut.p("adjacent2 = "+isct.adjacent2);
		    
			    ipts.add(isct);
			}
			i += 2; // skip next two edges
		    }
		    else if(i==num-2&&!closed ||
			    pts[i].x() < x && pts[(i+2)%num].x() > x ||
			    pts[i].x() > x && pts[(i+2)%num].x() < x ){
			isct.adjacent1 = pts[i];
			
			// should pts[(i+1)%num] be skipped or not?
			isct.adjacent2 = pts[(i+1)%num];
			//isct.adjacent2 = pts[(i+2)%num];
			
			//IOut.p("intersect(2) = "+isct);
			//IOut.p("adjacent1 = "+isct.adjacent1);
			//IOut.p("adjacent2 = "+isct.adjacent2);
			
			ipts.add(isct);
			i++;
		    }
		    else{ i++; }// skip if it's just touching at the kink
		}
		//else if(isct[0].eq(pts[i],parameterTolerance)){
		else if(isct.eqX(pts[i],parameterTolerance)){
		    //if(i==0) isct.adjacent1 = null;
		    if(i==0&&!closed) isct.adjacent1 = null;
		    else isct.adjacent1 = pts[(i-1+num)%num];
		    isct.adjacent2 = pts[(i+1)%num];
		    if( pts[i].eqX(pts[(i+1)%num],parameterTolerance)){
			if(!closed){
			    IVec2 pt0 = null, pt3 = null;
			    if(i>0) pt0 = pts[i-1];
			    if(i+2<num) pt3=pts[i+2];
			    IVec2I ept = intersectYEdgeAndYLine(pt0, pts[i], pts[(i+1)%num], pt3,
								 isct,normalDir, inside);
			    if(ept!=null){
				isct.set(ept);
				if(ept==pts[i]){
				    isct.adjacent1 = pt0;
				    isct.adjacent2 = pts[(i+2)%num];
				}
				else if(ept==pts[(i+2)%num]){
				    isct.adjacent1 = pts[i];
				    isct.adjacent2 = pt3;
				}
				else{ // ?
				    //if(i==0) isct.adjacent1 = null;
				    if(i==0&&!closed) isct.adjacent1 = null;
				    else isct.adjacent1 = pts[(i-1+num)%num];
				    isct.adjacent2 = pts[(i+1)%num];
				}
				
				//IOut.p("intersect(3) = "+isct);
				//IOut.p("adjacent1 = "+isct.adjacent1);
				//IOut.p("adjacent2 = "+isct.adjacent2);
				
				ipts.add(isct);
			    }
			}
			//else{} // if it's closed, this will be revisited at the end of the loop
			i+=2; // skip next two edges
		    }
		    // skip if it's just touching at the kink
		    else if( i==0&&!closed ||
			     pts[(i-1+num)%num].x() < x && pts[(i+1)%num].x() > x ||
			     pts[(i-1+num)%num].x() > x && pts[(i+1)%num].x() < x ){
			//if(i==0) isct.adjacent1 = null;
			if(i==0&&!closed) isct.adjacent1 = null;
			else{
			    // should it skip pts[i] or not?
			    isct.adjacent1 = pts[i];
			    //isct.adjacent1 = pts[(i-1+num)%num];
			}
			isct.adjacent2 = pts[(i+1)%num];
			
			//IOut.p("intersect(4) = "+isct);
			//IOut.p("adjacent1 = "+isct.adjacent1);
			//IOut.p("adjacent2 = "+isct.adjacent2);
			
			ipts.add(isct); // crossing
		    }
		    else{} // not crosing; just touching
		}
		else{ // intersect in the middle
		    
		    //IOut.p("intersect(5) = "+isct);
		    //IOut.p("adjacent1 = "+isct.adjacent1);
		    //IOut.p("adjacent2 = "+isct.adjacent2);
		    
		    ipts.add(isct);
		}
	    }
	}
	
	if(ipts.size()==0) return null;
	Intersection[] retval = new Intersection[ipts.size()];
	for(int i=0; i<ipts.size(); i++) retval[i] = ipts.get(i);
	return retval;
    }
    
    
    public static double sumOfExteriorAngles(IVec2I[] pts, boolean close){
	double angle=0;
	int num = pts.length;
	for(int i=0; close&&i<num || !close&&i<num-2; i++)
	    angle += pts[(i+1)%num].get().diff(pts[i]).angle(pts[(i+2)%num].get().diff(pts[(i+1)%num]));
	return angle;
    }
    /**
       checking if the direction of closed polygon normal is positive towards z direction or not
       direction follows right screw law.
    */
    public static boolean isNormalPositive(IVec2I[] pts, boolean close){
	return sumOfExteriorAngles(pts,close) > 0;
    }
    
    public boolean isNormalPositive(){ return isNormalPositive(pts,closed); }
    
    
    public String toString(){
	String retval = "";
	retval += "[";
	for(int i=0; i<pts.length; i++){
	    retval+=pts[i].toString();
	    if(i<pts.length-1) retval+=", ";
	}
	retval += "]";
	return retval;
    }
        
    
    public static class XComparator implements IComparator<Intersection>{
	public int compare(Intersection v1, Intersection v2){
	    if(v1.x>v2.x) return 1;
	    if(v1.x<v2.x) return -1;
	    return 0;
	}
    }
    public static class YComparator implements IComparator<Intersection>{
	public int compare(Intersection v1, Intersection v2){
	    if(v1.y>v2.y) return 1;
	    if(v1.y<v2.y) return -1;
	    return 0;
	}
    }
    
    
    public static class Intersection extends IVec2{
	public IPolyline2D polyline;
	public IVec2 adjacent1, adjacent2;
	public boolean orthogonal=false;
	public boolean orthogonalDirX=true; // Y if false
	public Intersection(IPolyline2D p, IVec2 pt){ super(pt); polyline=p; }
	public Intersection(IPolyline2D p, IVec2 pt, IVec2 a1, IVec2 a2){
	    super(pt); polyline=p; adjacent1=a1; adjacent2=a2;
	}
	public void setDirectionX(){ orthogonal=true; orthogonalDirX=true; }
	public void setDirectionY(){ orthogonal=true; orthogonalDirX=false; }
    }
    
    
}
