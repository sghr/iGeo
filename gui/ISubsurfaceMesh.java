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

import igeo.*;

/**
   Rectangular cell of a surface with or without trim curve.
   This class is used by ISubsurfaceMesh to create drawable polygon mesh of NURBS surface.
   
   @see ISurfaceMesh
   
   @author Satoru Sugihara
*/
public class ISubsurfaceMesh {
    
    // at the level which can confuse calculation of determinant in double floating num precision
    public static double subsurfaceResolution = 0.000000000000001; // 1-E15
    
    public int uindex, vindex;
    public double u1, u2, v1, v2;
    
    public boolean outerTrimAdded=false;
    
    public ArrayList<IVec2[]> trimCurves=null;
    public ArrayList<IVec2[]> trimLoops=null;
    
    public ISubsurfaceMesh(int uidx, int vidx, double u1, double u2, double v1, double v2){
	uindex = uidx;
	vindex = vidx;
	this.u1 = u1;
	this.u2 = u2;
	this.v1 = v1;
	this.v2 = v2;
    }
    
    public ISubsurfaceMesh(int uidx, int vidx, double[] uvals, double[] vvals){
	uindex = uidx;
	vindex = vidx;
	this.u1 = uvals[uidx];
	this.u2 = uvals[uidx+1];
	this.v1 = vvals[vidx];
	this.v2 = vvals[vidx+1];
    }
    
    public ISubsurfaceMesh(int uidx, int vidx){
	uindex = uidx;
	vindex = vidx;
    }
    
    public void addTrimCurve(IVec2[] trimCrv){
	//if(uindex==2&&vindex==19)for(int i=0; i<trimCrv.length; i++) IOut.p("trimCrv["+i+"] = "+trimCrv[i]);
	
	if(trimCurves==null) trimCurves = new ArrayList<IVec2[]>();
	trimCurves.add(trimCrv);
    }
    
    public void addTrimLoop(IVec2[] trimLoop, boolean isOuterTrim){
	if(isOuterTrim) outerTrimAdded=true;
	if(trimLoops==null) trimLoops = new ArrayList<IVec2[]>();
	trimLoops.add(trimLoop);
    }
    
    public boolean isPointsOnlyTouchingEdge(ArrayList<IVec2> polyline){
	
	// quick check
	IVec2 testPt = polyline.get(1);
	if(testPt.x > u1 && testPt.x < u2 && testPt.y > v1 && testPt.y < v2) return false;
	
	
	boolean onLeft=true;
	for(int i=1; i<polyline.size()-1 && onLeft; i++){
	    if(polyline.get(i).x > u1) onLeft=false;
	}
	// check direction of start and end vector
	if(onLeft){
	    if(polyline.get(0).x<=u1 && polyline.get(polyline.size()-1).x<=u1) return true;
	    return false;
	}
	
	boolean onRight=true;
	for(int i=1; i<polyline.size()-1 && onRight; i++){
	    if(polyline.get(i).x < u2) onRight=false;
	}
	// check direction of start and end vector
	if(onRight){
	    if(polyline.get(0).x>=u2 && polyline.get(polyline.size()-1).x>=u2) return true;
	    return false;
	}
	
	boolean onBottom=true;
	for(int i=1; i<polyline.size()-1 && onBottom; i++){
	    if(polyline.get(i).y > v1) onBottom=false;
	}
	// check direction of start and end vector
	if(onBottom){
	    if(polyline.get(0).y<=v1 && polyline.get(polyline.size()-1).y<=v1) return true;
	    return false;
	}
	
	boolean onTop=true;
	for(int i=1; i<polyline.size()-1 && onTop; i++){
	    if(polyline.get(i).y < v2) onTop=false;
	}
	// check direction of start and end vector
	if(onTop){
	    if(polyline.get(0).y>=v2 && polyline.get(polyline.size()-1).y>=v2) return true;
	    return false;
	}
	
	return false;
    }
    
    /**
       @param polyline The first and the last points in polyline is outside of the region. Others are inside. The size of polyline needs to be more than 3.
    */
    public void addTrimPolyline(ArrayList<IVec2> polyline){
	
	if(polyline.size()<3){
	    IOut.err("polyline's point number is too small ("+polyline.size()+")");
	    return; //
	}
	
	//IOut.p(this);
	for(int i=0; i<polyline.size(); i++){
	    //IOut.p("polyline["+i+"]"+polyline.get(i)); //
	    
	    if(i>0&&i<polyline.size()-1)
	    if(polyline.get(i).x < u1 ||polyline.get(i).x > u2 ||
	       polyline.get(i).y < v1 ||polyline.get(i).y > v2 ){
		IOut.err("point is out of area");
	    }
	    
	}
	
	if(isPointsOnlyTouchingEdge(polyline)){
	    //IOut.p("polyline is only touching the edge"); //
	    return;
	}
	
	
	IVec2 startPt = intersectWithEdge(polyline.get(1),polyline.get(0));
	IVec2 endPt = intersectWithEdge(polyline.get(polyline.size()-2),
					polyline.get(polyline.size()-1));

	if(startPt==null){ IOut.err("no start point found"); return; }
	if(endPt==null){ IOut.err("no end point found"); return; }
	//IOut.p("startPt = "+startPt); //
	//IOut.p("endPt = "+endPt); //
	
	if(startPt.x==polyline.get(1).x && startPt.y==polyline.get(1).y){
	    polyline.remove(1);
	}
	if(endPt.x==polyline.get(polyline.size()-2).x &&
	   endPt.y==polyline.get(polyline.size()-2).y ){
	    polyline.remove(polyline.size()-2);
	}
	
	// check if anything exactly on the edges
	int splitIdx=0;
	for(int i=1; i<polyline.size()-1; i++){
	    if(polyline.get(i).x <= u1 || polyline.get(i).x >= u2 ||
	       polyline.get(i).y <= v1 || polyline.get(i).y >= v2 ){
		IVec2[] trimCrv = new IVec2[i-splitIdx+1];
		for(int j=splitIdx; j<=i; j++){
		    if(j==0) trimCrv[j-splitIdx] = startPt;
		    else trimCrv[j-splitIdx] = polyline.get(j);
		}
		
		//IOut.p("split");
		//for(int j=0; j<trimCrv.length; j++) IOut.p("split trimCrv["+j+"] = "+trimCrv[j]);
		
		addTrimCurve(trimCrv);
		splitIdx = i;
	    }
	}
	if(splitIdx!=0){
	    IVec2[] trimCrv = new IVec2[polyline.size()-splitIdx];
	    for(int j=splitIdx; j<polyline.size(); j++){
		if(j==polyline.size()-1) trimCrv[j-splitIdx] = endPt;
		else trimCrv[j-splitIdx] = polyline.get(j);
	    }
	    
	    //IOut.p("split2");
	    //for(int j=0; j<trimCrv.length; j++) IOut.p("split trimCrv["+j+"] = "+trimCrv[j]);
	    
	    addTrimCurve(trimCrv);
	    return;
	}
	
	
	IVec2[] trimCrv = new IVec2[polyline.size()];
	trimCrv[0] = startPt;
	for(int i=1; i<polyline.size()-1; i++) trimCrv[i] = polyline.get(i);
	trimCrv[polyline.size()-1] = endPt;
	
	addTrimCurve(trimCrv);
    }
    
    
    public void addUTrimLine(IVec2 pt1, IVec2 pt2){
	// not checking if pt1 and pt2 is outside of the subsurface, which should be done beforehand
	if( pt1.y==v1 && pt2.y==v1 || pt1.y==v2 && pt2.y==v2 ) return; // not adding
	IVec2[] pts = new IVec2[2];
	int idx1 = 0, idx2 = 1;
	if(pt1.x > pt2.x){ idx1 = 1; idx2 = 0; }
	pts[idx1] = intersectOnLeft(pt1,pt2);
	pts[idx2] = intersectOnRight(pt1,pt2);
	addTrimCurve(pts);
    }
    
    public void addVTrimLine(IVec2 pt1, IVec2 pt2){
	// not checking if pt1 and pt2 is outside of the subsurface, which should be done beforehand
	if( pt1.x==u1 && pt2.x==u1 || pt1.x==u2 && pt2.x==u2 ) return; // not adding
	IVec2[] pts = new IVec2[2];
	int idx1 = 0, idx2 = 1;
	if(pt1.y > pt2.y){ idx1 = 1; idx2 = 0; }
	pts[idx1] = intersectOnBottom(pt1,pt2);
	pts[idx2] = intersectOnTop(pt1,pt2);
	addTrimCurve(pts);
    }
    
    public void addTrimLineAtBottomLeft(IVec2 pt1, IVec2 pt2){
	//IOut.p(this); //
	//IOut.p("pt1 = "+pt1); //
	//IOut.p("pt2 = "+pt2); //
	
	IVec2 leftPt = intersectOnLeft(pt1,pt2);
	if(leftPt==null || leftPt.y<=v1) return; // not adding when just touching corner
	IVec2 bottomPt = intersectOnBottom(pt1,pt2);
	if(bottomPt==null || bottomPt.x<=u1) return; // not adding when just touching corner
	
	IVec2[] pts = new IVec2[2];
	if(pt1.x < pt2.x){ pts[0] = leftPt; pts[1] = bottomPt; }
	else{ pts[0] = bottomPt; pts[1] = leftPt; }
	addTrimCurve(pts);
    }
    
    public void addTrimLineAtBottomRight(IVec2 pt1, IVec2 pt2){
	IVec2 rightPt = intersectOnRight(pt1,pt2);
	if(rightPt==null || rightPt.y<=v1) return; // not adding when just touching corner
	IVec2 bottomPt = intersectOnBottom(pt1,pt2);
	if(bottomPt==null || bottomPt.x>=u2) return; // not adding when just touching corner
	
	IVec2[] pts = new IVec2[2];
	if(pt1.x < pt2.x){ pts[0] = bottomPt; pts[1] = rightPt; }
	else{ pts[0] = rightPt; pts[1] = bottomPt; }
	addTrimCurve(pts);
    }
    
    public void addTrimLineAtTopLeft(IVec2 pt1, IVec2 pt2){
	IVec2 leftPt = intersectOnLeft(pt1,pt2);
	if(leftPt==null || leftPt.y>=v2) return; // not adding when just touching corner
	IVec2 topPt = intersectOnTop(pt1,pt2);
	if(topPt==null || topPt.x<=u1) return; // not adding when just touching corner
	
	IVec2[] pts = new IVec2[2];
	if(pt1.x < pt2.x){ pts[0] = leftPt; pts[1] = topPt; }
	else{ pts[0] = topPt; pts[1] = leftPt; }
	addTrimCurve(pts);
    }
    
    public void addTrimLineAtTopRight(IVec2 pt1, IVec2 pt2){
	IVec2 rightPt = intersectOnRight(pt1,pt2);
	if(rightPt==null || rightPt.y>=v2) return; // not adding when just touching corner
	IVec2 topPt = intersectOnTop(pt1,pt2);
	if(topPt==null || topPt.x>=u2) return; // not adding when just touching corner
	
	IVec2[] pts = new IVec2[2];
	if(pt1.x < pt2.x){ pts[0] = topPt; pts[1] = rightPt; }
	else{ pts[0] = rightPt; pts[1] = topPt; }
	addTrimCurve(pts);
    }
    
    
    public IVec2 intersectOnLeft(IVec2 linePt1, IVec2 linePt2){
	return IVec2.intersectYLine(linePt1, linePt2.diff(linePt1), u1);
    }
    public IVec2 intersectOnRight(IVec2 linePt1, IVec2 linePt2){
	return IVec2.intersectYLine(linePt1, linePt2.diff(linePt1), u2);
    }
    public IVec2 intersectOnBottom(IVec2 linePt1, IVec2 linePt2){
	return IVec2.intersectXLine(linePt1, linePt2.diff(linePt1), v1);
    }
    public IVec2 intersectOnTop(IVec2 linePt1, IVec2 linePt2){
	return IVec2.intersectXLine(linePt1, linePt2.diff(linePt1), v2);
    }
    
    public boolean isCrossingXLine(IVec2 pt1, IVec2 pt2, double yOfXLine){
	if(pt1.y == yOfXLine && pt2.y == yOfXLine) return false; //
	return pt1.y<=yOfXLine && pt2.y>=yOfXLine || pt1.y>=yOfXLine && pt2.y<=yOfXLine; 
    }
    public boolean isCrossingYLine(IVec2 pt1, IVec2 pt2, double xOfYLine){
	if(pt1.x == xOfYLine && pt2.x == xOfYLine) return false; //
	return pt1.x<=xOfYLine && pt2.x>=xOfYLine || pt1.x>=xOfYLine && pt2.x<=xOfYLine; 
    }
    
    
    public boolean isCrossingLeft(IVec2 pt1, IVec2 pt2){
	return isCrossingYLine(pt1,pt2,u1);
    }
    public boolean isCrossingRight(IVec2 pt1, IVec2 pt2){
	return isCrossingYLine(pt1,pt2,u2);
    }
    public boolean isCrossingBottom(IVec2 pt1, IVec2 pt2){
	return isCrossingXLine(pt1,pt2,v1);
    }
    public boolean isCrossingTop(IVec2 pt1, IVec2 pt2){
	return isCrossingXLine(pt1,pt2,v2);
    }
    
    public boolean isInside(IVec2 pt){
	return pt.x >= u1 && pt.x <= u2 && pt.y >= v1 && pt.y <= v2;
	//return pt.x >= u1 && pt.x < u2 && pt.y >= v1 && pt.y < v2;
    }
    
    protected boolean isPointOnAnyEdge(IVec2 pt){
	return pt.x == u1 || pt.x == u2 || pt.y == v1 || pt.y == v2;
    }
    protected boolean isPointLeftEdge(IVec2 pt){ return pt.x == u1; }
    protected boolean isPointRightEdge(IVec2 pt){ return pt.x == u2; }
    protected boolean isPointBottomEdge(IVec2 pt){ return pt.y == v1; }
    protected boolean isPointTopEdge(IVec2 pt){ return pt.y == v2; }
    
    
    /**
       @param pointInside pointInside needs to be inside the subsurface region
       @param pointOutside pointOutside needs to be inside the subsurface region
    */
    public IVec2 intersectWithEdge(IVec2 pointInside, IVec2 pointOutside){
	
	// when pointOutside is outside the subsurface
	if(pointOutside.x <= u1){
	    
	    // in case pointOutside & pointInside is on X edge
	    if(pointOutside.y==v1 && pointInside.y==v1 ||
	       pointOutside.y==v2 && pointInside.y==v2 ) return pointInside;
	    
	    IVec2 pt=null;
	    if(pointOutside.x==u1){
		if(pointInside.x==u1) return pointInside; // in case both points are on Y edge
		pt = pointOutside;
	    }
	    else pt = intersectOnLeft(pointInside, pointOutside);
	    
	    
	    if(pt.y < v1){
		
		return intersectOnBottom(pointInside, pointOutside);
	    }
	    if(pt.y > v2){
		
		return intersectOnTop(pointInside, pointOutside);
	    }
	    
	    return pt;
	}
	if(pointOutside.x >= u2){
	    // in case pointOutside & pointInside is on X edge
	    if(pointOutside.y==v1 && pointInside.y==v1 ||
	       pointOutside.y==v2 && pointInside.y==v2 ) return pointInside;
	    
	    IVec2 pt=null;
	    if(pointOutside.x==u2){
		if(pointInside.x==u2) return pointInside; // in case both points are on Y edge
		pt = pointOutside;
	    }
	    else pt = intersectOnRight(pointInside,pointOutside);
	    
	    if(pt==null){ IOut.err("no intersection found"); return pt; }
	    
	    if(pt.y < v1) return intersectOnBottom(pointInside, pointOutside);
	    if(pt.y > v2) return intersectOnTop(pointInside, pointOutside);
	    return pt;
	}
	
	// pointOutside.x >= u1 && pointOutside.x <= u2
	if(pointOutside.y <= v1){
	    if(pointOutside.y == v1){
		if(pointInside.y == v1) return pointInside; // when both points are on X edge
		return pointOutside;
	    }
	    return intersectOnBottom(pointInside, pointOutside);
	}
	if(pointOutside.y >= v2){
	    if(pointOutside.y == v2){
		if(pointInside.y == v2) return pointInside; // when both points are on X edge
		return pointOutside;
	    }
	    return intersectOnTop(pointInside, pointOutside);
	}
	
	IOut.err("no intersection found"); //
	//IOut.p("subsurf< ("+u1+","+v1+"), ("+u2+","+v2+") >"); //
	//IOut.p("pointInside = "+pointInside); //
	//IOut.p("pointOutside = "+pointOutside); //
	
	return null;
    }
    
    
    public boolean equals(int uidx, int vidx){ return uindex==uidx&&vindex==vidx; }
    
    
    public void addIfNoClosePoint(ArrayList<EdgePoint> edgePts,
				  EdgePoint pt, double resolutionDistance){
	// if existing point is close, move the point in edgePts to the location of pt. (!)
	boolean anyClosePoint=false;
	for(int i=0; i<edgePts.size(); i++){
	    if( Math.abs(edgePts.get(i).point.x - pt.point.x) < resolutionDistance &&
		Math.abs(edgePts.get(i).point.y - pt.point.y) < resolutionDistance){
		anyClosePoint = true;
		edgePts.get(i).point.x = pt.point.x;
		edgePts.get(i).point.y = pt.point.y;
	    }
	}
	if(!anyClosePoint) edgePts.add(pt);
    }
    
    /**
       create loop out of trimCurves and rectangular boundary
    */
    public void setupLoop(){
	
	if(trimCurves==null || trimCurves.size()==0){
	    //IOut.p("no trimCurves at "+this); //

	    // setting default outer trim
	    if(!outerTrimAdded){
		addTrimLoop(new IVec2[]{ new IVec2(u1,v1), new IVec2(u2,v1),
					  new IVec2(u2,v2), new IVec2(u1,v2) }, true);
	    }
	    
	    return;
	}
	
	ArrayList<EdgePoint> edgePts = new ArrayList<EdgePoint>();
	
	for(int i=0; i<trimCurves.size(); i++){
	    edgePts.add(new EdgePoint(trimCurves.get(i)[0],trimCurves.get(i),true));
	    edgePts.add(new EdgePoint(trimCurves.get(i)[trimCurves.get(i).length-1],
				      trimCurves.get(i),false));
	}
	
	//corner points
	//edgePts.add(new EdgePoint(new IVec2(u1,v1),null,true));
	//edgePts.add(new EdgePoint(new IVec2(u2,v1),null,true));
	//edgePts.add(new EdgePoint(new IVec2(u2,v2),null,true));
	//edgePts.add(new EdgePoint(new IVec2(u1,v2),null,true));
	
	addIfNoClosePoint(edgePts, new EdgePoint(new IVec2(u1,v1),null,true),
			  subsurfaceResolution);
	addIfNoClosePoint(edgePts, new EdgePoint(new IVec2(u2,v1),null,true),
			  subsurfaceResolution);
	addIfNoClosePoint(edgePts, new EdgePoint(new IVec2(u2,v2),null,true),
			  subsurfaceResolution);
	addIfNoClosePoint(edgePts, new EdgePoint(new IVec2(u1,v2),null,true),
			  subsurfaceResolution);
	
	
	ISort.sort(edgePts, new EdgePointComparator(this));
	
	
	//IOut.p("edgePts sorted"); 
	//for(int i=0; i<edgePts.size(); i++) IOut.p("edgePts.get("+i+") : intersect="+ (edgePts.get(i).polyline!=null) +", start pt = "+edgePts.get(i).start+", pt = "+edgePts.get(i).point);
	
	
	EdgePoint startPt = findStartPoint(edgePts);
	boolean errorFlag=false;
	while(edgePts.size()>0 && startPt!=null &&!errorFlag){
	    //edgePts.remove(startPt); // do not remove, it's used as a mark
	    IVec2[] loop = extractLoop(edgePts, startPt);
	    
	    if(loop==null) errorFlag=true;
	    else{
		//IOut.p("got loop ");
		//for(int j=0; j<loop.length; j++) IOut.p("loop["+j+"] = "+loop[j]); //
		if(loop.length>2){
		    addTrimLoop(loop,false); // if trim is outer or inner doesn't matter here.
		}
		else{ IOut.err("too short loop"); }
		
		startPt = findStartPoint(edgePts);
	    }
	}
	
	if(edgePts.size()>0){
	    boolean anyIntersectionPt = false;
	    for(int i=0; i<edgePts.size(); i++)
		if(edgePts.get(i).polyline!=null) anyIntersectionPt=true;
	    if(anyIntersectionPt) IOut.err("some points remaining for loops");
	}
	
    }
    
    
    
    public EdgePoint findStartPoint(ArrayList<EdgePoint> edgePts){
	for(int i=0; i<edgePts.size(); i++)
	    if(edgePts.get(i).polyline!=null && edgePts.get(i).start) return edgePts.get(i);
	return null;
    }
    
    public EdgePoint extractEndPoint(ArrayList<EdgePoint> edgePts, IVec2[] polyline){
	//for(int i=0; i<edgePts.size(); i++) if(edgePts.get(i).point == pt)return edgePts.get(i);
	for(int i=0; i<edgePts.size(); i++)
	    if(edgePts.get(i).polyline == polyline &&
	       edgePts.get(i).point == polyline[polyline.length-1] &&
	       !edgePts.get(i).start) return edgePts.get(i);

	IOut.err("no end point found: polyline.length="+polyline.length+", polyline["+
		 (polyline.length-1)+"] = "+polyline[polyline.length-1]); //
	
	return null;
    }
    
    public IVec2[] extractLoop(ArrayList<EdgePoint> edgePts, EdgePoint loopStartPt){
	
	ArrayList<IVec2> loopPts=new ArrayList<IVec2>();
	
	boolean loopClosed=false;
	EdgePoint startPt = loopStartPt;
	while(edgePts.size()>0 && !loopClosed){
	    
	    for(int i=0; i<startPt.polyline.length; i++){
		loopPts.add(startPt.polyline[i]);
		//IOut.p("@"+this+"; "+"loopPts.get("+(loopPts.size()-1)+") = "+loopPts.get(loopPts.size()-1));
	    }
	    
	    EdgePoint endPt = extractEndPoint(edgePts,startPt.polyline);
	    
	    ArrayList<EdgePoint> nextSeg = getNextStartPoint(edgePts,endPt,loopStartPt);
	    
	    if(nextSeg==null){
		IOut.err("no next segment found");
		return null;
	    }
	    
	    for(int i=0; i<nextSeg.size(); i++){
		//if( i==(nextSeg.size()-1) && nextSeg.get(i).point==loopPts.get(0) )
		if( i==(nextSeg.size()-1) && nextSeg.get(i)==loopStartPt){
		    loopClosed=true;
		    //edgePts.remove(loopStartPt); // already removed in getNextStartPoint
		    //IOut.p("removed : "+loopStartPt.point+", intersection = "+(loopStartPt.polyline!=null) +", start = "+loopStartPt.start); //
		}
		else{
		    loopPts.add(nextSeg.get(i).point);
		    //IOut.p("@"+this+"; "+ "loopPts.get("+(loopPts.size()-1)+") = "+nextSeg.get(i).point+", intersect="+(nextSeg.get(i).polyline!=null)+", start="+nextSeg.get(i).start);//
		}
	    }
	    
	    if(!loopClosed){
		startPt = nextSeg.get(nextSeg.size()-1);
		edgePts.remove(startPt);
		//IOut.p("removed : "+startPt.point+", intersection = "+ (startPt.polyline!=null) +", start = "+startPt.start); //
	    }
	}
	
	// remove exactly duplicated points
	ArrayList<IVec2> loopPts2 = new ArrayList<IVec2>();
	for(int i=0; i<loopPts.size(); i++){
	    if(loopPts.get(i).x != loopPts.get((i+1)%loopPts.size()).x ||
	       loopPts.get(i).y != loopPts.get((i+1)%loopPts.size()).y)
		loopPts2.add(loopPts.get(i));
	}
	
	//return loopPts.toArray(new IVec2[loopPts.size()]);
	return loopPts2.toArray(new IVec2[loopPts2.size()]);
    }
    
    public ArrayList<EdgePoint> getNextStartPoint(ArrayList<EdgePoint> edgePts,
						  EdgePoint endPt,
						  EdgePoint loopStartPt){
	int idx = edgePts.indexOf(endPt);
	
	// rewind in case points with identical position is stacked up in edgePts
	int idx2 = idx;
	boolean samePt=true;
	for(int i=idx-1; i>=0 && samePt; i--){
	    if(edgePts.get(i).point.x == endPt.point.x &&
	       edgePts.get(i).point.y == endPt.point.y ) idx2=i;
	    else samePt=false;
	}
	
	if(idx2<0) idx2=idx;
	
	ArrayList<EdgePoint> pts = new ArrayList<EdgePoint>();
	
	//IOut.p("endPt = "+endPt.point+", intersection="+(endPt.polyline!=null)+", start="+endPt.start); //
	//IOut.p("idx2 = "+idx2); //
	
	
	if(idx2<idx || (endPt.point.x==edgePts.get((idx2+1)%edgePts.size()).point.x &&
			endPt.point.y==edgePts.get((idx2+1)%edgePts.size()).point.y )){
	    
	    //IOut.p("duplicated points found"); //
	    int idx3 = idx2;
	    for(int i=0; i<edgePts.size()&&
		    ((idx2+i)==idx ||
		     edgePts.get((idx2+i)%edgePts.size()).point.x == endPt.point.x &&
		     edgePts.get((idx2+i)%edgePts.size()).point.y == endPt.point.y) ;i++){
		idx3 = idx2+i;
		
		if( idx3!=idx ){
		    if(edgePts.get(idx3%edgePts.size()).polyline != null &&
		       edgePts.get(idx3%edgePts.size()).start){
			if(IDelaunay2D.isFaceDirectionOnEdgeCorrect
			   (endPt.polyline[endPt.polyline.length-2], endPt.point,
			    edgePts.get(idx3%edgePts.size()).polyline[1])){
			    pts.add(edgePts.get(idx3%edgePts.size()));
			    
			    //IOut.p("returning duplicated start point : "+ edgePts.get(idx3%edgePts.size()).point);
			    
			    edgePts.remove(pts.get(pts.size()-1));
			    edgePts.remove(endPt);
			    
			    return pts;
			}
		    }
		}
	    }
	    idx2 = idx3+1;
	    //IOut.p("duplicated points skipped");
	}
	
	
	boolean startPointFound=false;
	//for(int i=1; i<edgePts.size()&&!startPointFound; i++){
	for(int i=0; i<edgePts.size()&&!startPointFound; i++){
	    if((idx2+i) != idx){
		EdgePoint p = edgePts.get( (idx2+i)%edgePts.size());
		// ignore p.polyline!=null && p.end , which should belong to another loop
		if(p.polyline==null || p.start){
		    pts.add(p);
		    //IOut.p("edgePts.get("+((idx2+i)%edgePts.size())+") = "+ p.point +", intersection = "+(p.polyline!=null)+", start = "+p.start); //
		    if( (p.polyline != null) && p.start ) startPointFound=true;
		}
	    }
	}
	
	// in case multiple points are on the location of loopStartPt.
	// avoid other points and pick loopStartPt
	if(pts.get(pts.size()-1) != loopStartPt &&
	   pts.get(pts.size()-1).point.x == loopStartPt.point.x &&
	   pts.get(pts.size()-1).point.y == loopStartPt.point.y ){
	    pts.set(pts.size()-1, loopStartPt);
	    
	    //IOut.p("duplicated end point is replaced with loopStartPoint: "+loopStartPt.point); //
	}
	
	// remove end point out of the loop
	edgePts.remove(idx);
	for(int i=0; i<pts.size(); i++) edgePts.remove(pts.get(i));
	
	if(!startPointFound){
	    IOut.err("no start point found");
	    return null;
	}
	return pts;
    }
    
    public IVec2[][] getDefaultTriangles(){
	// just 4 corners
	IVec2 pt11 = new IVec2(u1,v1);
	IVec2 pt12 = new IVec2(u1,v2);
	IVec2 pt21 = new IVec2(u2,v1);
	IVec2 pt22 = new IVec2(u2,v2);
	IVec2[][] tri = new IVec2[2][3];
	tri[0][0] = pt11;
	tri[0][1] = pt21;
	tri[0][2] = pt22;
	tri[1][0] = pt11;
	tri[1][1] = pt22;
	tri[1][2] = pt12;
	return tri;
    }
    
    public boolean isDefaultTrim(IVec2[] pts){
	if(pts.length!=4) return false;
	int stIdx=-1;
	for(int i=0; i<4 && stIdx<0; i++)
	    if( pts[i].x == u1 && pts[i].y == v1) stIdx=i;
	if(stIdx<0) return false;
	if(pts[(stIdx+1)%4].x == u2 && pts[(stIdx+1)%4].y == v1 &&
	   pts[(stIdx+2)%4].x == u2 && pts[(stIdx+2)%4].y == v2 &&
	   pts[(stIdx+3)%4].x == u1 && pts[(stIdx+3)%4].y == v2 ) return true;
	return false;
    }
    
    
    static public boolean isOuterLoop(IVec2[] loop){
	return IPolyline2D.isNormalPositive(loop,true);
    }

    static public IVec2[][] getTrianglesOfQuadrilateral(IVec2[] pts){
	IVec2[][] retval = new IVec2[2][];
	if(pts[0].dist(pts[2]) <= pts[1].dist(pts[3])){
	    retval[0] = new IVec2[]{ pts[0], pts[1], pts[2] };
	    retval[1] = new IVec2[]{ pts[0], pts[2], pts[3] };
	    return retval;
	}
	retval[0] = new IVec2[]{ pts[0], pts[1], pts[3] };
	retval[1] = new IVec2[]{ pts[1], pts[2], pts[3] };
	return retval;
    }
    
    public static IVec2[] removeDuplicatedPoints(IVec2[] pts){
	ArrayList<IVec2> pts2 = new ArrayList<IVec2>();
	pts2.add(pts[0]);
	for(int i=1; i<pts.length; i++){
	    if(pts[i-1].x!=pts[i].x || pts[i-1].y!=pts[i].y) pts2.add(pts[i]);
	}
	return pts2.toArray(new IVec2[pts2.size()]);
    }
    public void removeDuplicatedPointsInLoops(){
	if(trimLoops==null) return;
	for(int i=0; i<trimLoops.size(); i++){
	    IVec2[] loop = trimLoops.get(i);
	    boolean anyDuplicate=false;
	    for(int j=0; j<loop.length&&!anyDuplicate; j++){
		if(loop[i].x == loop[(i+1)%loop.length].x &&
		   loop[i].y == loop[(i+1)%loop.length].y) anyDuplicate=true;
	    }
	    if(anyDuplicate) trimLoops.set(i, removeDuplicatedPoints(loop));
	}
    }
    
    
    public IVec2[][] getTriangles(){
	
	
	//if(trimLoops==null || trimLoops.size()==0) return getDefaultTriangles();
	if(trimLoops==null || trimLoops.size()==0) return null; // to let SubsufaceMatrix to decide if this should have default triangles or not
	
	
	//removeDuplicatedPointsInLoops(); // not really necessary, yet
	
	
	if(trimLoops.size()==1 && trimLoops.get(0).length==4){
	    if(isDefaultTrim(trimLoops.get(0))) return null;
	    if(isOuterLoop(trimLoops.get(0)))
		return getTrianglesOfQuadrilateral(trimLoops.get(0));
	}
	
	
	boolean anyInnerLoop=false;
	for(int i=0; i<trimLoops.size()&&!anyInnerLoop; i++){
	    if(!isOuterLoop(trimLoops.get(i))) anyInnerLoop=true;
	}
	if(!anyInnerLoop){
	    ArrayList<IVec2[]> triangles = new ArrayList<IVec2[]>();
	    for(int i=0; i<trimLoops.size(); i++){
		IVec2[] loop = trimLoops.get(i);
		if(loop.length == 3) triangles.add(loop);
		else if(loop.length == 4){
		    IVec2[][] tri = getTrianglesOfQuadrilateral(loop);
		    triangles.add(tri[0]);
		    triangles.add(tri[1]);
		}
		else if(loop.length > 4){
		    IVec2[][] tri = IDelaunay2D.getTriangles(null,new IVec2[][]{ loop });
		    for(int j=0; j<tri.length; j++) triangles.add(tri[j]);
		}
	    }
	    return triangles.toArray(new IVec2[triangles.size()][]);
	}
	
	/*
	ArrayList<IVec2[]>  innerLoop = new ArrayList<IVec2[]>();
	ArrayList<IVec2[]>  outerLoop = new ArrayList<IVec2[]>();
	for(int i=0; i<trimLoops.size(); i++){
	    if(getDirectionOfLoop(trimLoops.get(i))) outerLoop.add(trimLoops.get(i));
	    else innerLoop.add(trimLoops.get(i));
	}
	*/
	
	//IOut.p("trimLoops.size()=="+trimLoops.size()); //
	//for(int i=0; i<trimLoops.size(); i++) for(int j=0; j<trimLoops.get(i).length; j++) IOut.p("trimLoops.get("+i+")["+j+"] = "+trimLoops.get(i)[j]); //
	
	return IDelaunay2D.getTriangles(null,trimLoops.toArray(new IVec2[trimLoops.size()][]));
    }
    
    
    public String toString(){
	return "index<"+uindex+","+vindex+">, location<("+u1+","+v1+"),("+u2+","+v2+")>"; //
    }
    
    static public class EdgePoint{
	public IVec2 point;
	public boolean start; // if it's intersection, whether start or end point
	public IVec2[] polyline=null; // if the point is intersection, polyline is set
	public EdgePoint(IVec2 pt, IVec2[] parentPolyline, boolean start){
	    point = pt; polyline = parentPolyline; this.start = start;
	}
    }
    
    
    static public class EdgePointComparator implements IComparator<EdgePoint>{
	public ISubsurfaceMesh surf;
	public EdgePointComparator(ISubsurfaceMesh subsurf){ surf=subsurf; }
	/**
	   @return [0-3], 0:bottom edge, 1:right edge, 2:top edge, 3:left edge
	*/
	public int edgePointIsOn(IVec2 pt){
	    boolean diagonal1 =
		pt.y <= (surf.v2-surf.v1)/(surf.u2-surf.u1)*(pt.x-surf.u1)+surf.v1;
	    boolean diagonal2 =
		pt.y <= (surf.v1-surf.v2)/(surf.u2-surf.u1)*(pt.x-surf.u1)+surf.v2;
	    if(diagonal1){
		if(diagonal2) return 0; // bottom
		return 1; // right
	    }
	    if(diagonal2) return 3; // left
	    return 2; // top
	}
	
	public double distanceOnLoop(IVec2 pt){
	    int edgeIdx = edgePointIsOn(pt);
	    if(edgeIdx==0) return pt.x - surf.u1;
	    if(edgeIdx==1) return (surf.u2-surf.u1) + pt.y-surf.v1;
	    if(edgeIdx==2) return (surf.u2-surf.u1) + (surf.v2-surf.v1) + (surf.u2-pt.x);
	    // edgeIdx==3
	    return (surf.u2-surf.u1)*2 + (surf.v2-surf.v1) + (surf.v2-pt.y);
	}
	
	public int compare(EdgePoint pt1, EdgePoint pt2){
	    double dist1 = distanceOnLoop(pt1.point);
	    double dist2 = distanceOnLoop(pt2.point);
	    if(dist1<dist2) return -1; //1;
	    if(dist1>dist2) return 1; //-1;
	    return 0;
	}
    }
    
    
    static public class SubsurfaceMatrix{
	public ISubsurfaceMesh[][] matrix;
	public double[] uvalues;
	public double[] vvalues;
	public int unum, vnum;
	
	public ArrayList<IVec2[]> innerLoop=null;
	public ArrayList<IVec2[]> outerLoop=null;
	
	public SubsurfaceMatrix(double[] uval, double[] vval){
	    unum = uval.length-1;
	    vnum = vval.length-1;
	    uvalues = uval;
	    vvalues = vval;
	    matrix = new ISubsurfaceMesh[unum][vnum];
	}
	
	//public ISubsurfaceMesh getSubsurface(int uidx, int vidx){return matrix[uidx][vidx];}
	
	public boolean isInsideTrimLoop(int uidx, int vidx){
	    if( (innerLoop==null || innerLoop.size()==0) &&
		(outerLoop==null || outerLoop.size()==0) ) return true;
	    
	    IVec2 pt = new IVec2( (uvalues[uidx]+uvalues[uidx+1])/2,
				    (vvalues[vidx]+vvalues[vidx+1])/2 );
	    
	    if( innerLoop!=null && innerLoop.size()>0){
		for(int i=0; i<innerLoop.size(); i++){
		    if(pt.isInside(innerLoop.get(i))) return false;
		}
	    }
	    
	    if( outerLoop!=null && outerLoop.size()>0){
		for(int i=0; i<outerLoop.size(); i++){
		    if(pt.isInside(outerLoop.get(i))) return true;
		}
		return false;
	    }
	    return true;
	}
	
	public IVec2[][] getTriangles(){
	    
	    ArrayList<IVec2[]> triangles = new ArrayList<IVec2[]>();
	    for(int i=0; i<unum; i++){
		for(int j=0; j<vnum; j++){
		    
		    //if(i==5 && j==9 ) // debug
		    
		    if(matrix[i][j]!=null){
			matrix[i][j].setupLoop();
			IVec2[][] tr = matrix[i][j].getTriangles();
			if(tr==null && isInsideTrimLoop(i,j)){
			    tr = matrix[i][j].getDefaultTriangles();
			}
			if(tr!=null){
			    for(int k=0; k<tr.length; k++) triangles.add(tr[k]);
			}
		    }
		    else{
			if(isInsideTrimLoop(i,j)){
			    IVec2[][] tr = getSubsurface(i,j).getDefaultTriangles();
			    for(int k=0; k<tr.length; k++) triangles.add(tr[k]);
			}
		    }
		}
	    }
	    
	    return triangles.toArray(new IVec2[triangles.size()][]);
	}
	
	
	public ISubsurfaceMesh getSubsurface(int uidx, int vidx){
	    if(uidx<0 || uidx>=matrix.length || vidx<0 || vidx>=matrix[0].length)
		//return null;
		return new ISubsurfaceMesh(uidx,vidx); // validity of uidx,vidx is checked somewhere else
	    
	    if(matrix[uidx][vidx]==null){
		matrix[uidx][vidx] = new ISubsurfaceMesh(uidx,vidx,
							  uvalues[uidx],uvalues[uidx+1],
							  vvalues[vidx],vvalues[vidx+1]);
	    }
	    return matrix[uidx][vidx];
	}
	
	public boolean isSubsurfaceNull(int uidx, int vidx){ return matrix[uidx][vidx]==null;}
	
	public boolean isDefaultOuterLoop(IVec2[] loop){
	    if(loop.length!=4) return false;
	    int idx=-1;
	    for(int i=0; i<4&&idx<0; i++){
		if(loop[i].x == 0 && loop[i].y == 0) idx=i;
	    }
	    if(idx<0) return false;
	    if(loop[(idx+1)%4].x != 1.0 || loop[(idx+1)%4].y != 0.0) return false;
	    if(loop[(idx+2)%4].x != 1.0 || loop[(idx+2)%4].y != 1.0) return false;
	    if(loop[(idx+3)%4].x != 0.0 || loop[(idx+3)%4].y != 1.0) return false;

	    //IOut.p("default outer loop"); //
	    return true;
	}
	
	public void setLoop(IVec2[] loop, boolean isOuterTrim){
	    
	    if(isDefaultOuterLoop(loop)) return; // don't add the default rectangle trim loop
	    
	    if(isOuterTrim){
		if(outerLoop==null) outerLoop=new ArrayList<IVec2[]>();
		outerLoop.add(loop);
	    }
	    else{
		if(innerLoop==null) innerLoop=new ArrayList<IVec2[]>();
		innerLoop.add(loop);
	    }
	    
	    //for(int i=0; i<loop.length; i++) IOut.p("loop["+i+"] = "+loop[i]);
	    
	    
	    // finding first index where point changes location to next subsurface
	    ISubsurfaceMesh currentSubsurf=getSubsurfaceOnPoint(loop[0]);
	    int startIdx=-1;
	    
	    //IOut.p("current subsurf = : "+currentSubsurf);
	    for(int i=0; i<loop.length && startIdx<0 ; i++){
		//IOut.p("searching startIdx : "+i);
		
		ISubsurfaceMesh subsurf = getSubsurfaceOnPoint(loop[(i+1)%loop.length]);
		
		//IOut.p("searching startIdx subsurf = : "+subsurf);
		if(currentSubsurf != subsurf){
		    //IOut.p("currentSubsurf != subsurf; startIdx found ;"+i); //
		    startIdx = i;
		    currentSubsurf = subsurf; // ! important
		}
	    }
	    
	    if(startIdx<0){ // whole loop is inside the subsurface
		currentSubsurf.addTrimLoop(loop,isOuterTrim);
		return;
	    }
	    
	    //IOut.p("startIdx = "+startIdx+", subsurf = "+currentSubsurf); //
	    //IOut.p("loop["+startIdx+"] = "+loop[startIdx]);
	    //IOut.p("loop["+(startIdx+1)+"] = "+loop[(startIdx+1)%loop.length]);
	    //IOut.p(getSubsurfaceOnPoint(loop[(startIdx+1)%loop.length])); //
	    
	    ArrayList<IVec2> segment = new ArrayList<IVec2>();
	    for(int i=0; i<loop.length; i++){
		segment.add(loop[(i+startIdx)%loop.length]);
		
		ISubsurfaceMesh subsurf =
		    getSubsurfaceOnPoint(loop[(i+startIdx+1)%loop.length]);
		
		if(i==0 || currentSubsurf != subsurf){
		    //IOut.p("loop["+((i+startIdx)%loop.length)+"]: different subsurf"); //
		    
		    getSubsurfaceOnLine(loop[(i+startIdx)%loop.length],
					loop[(i+startIdx+1)%loop.length]);

		    if(i>0){
			segment.add(loop[(i+startIdx+1)%loop.length]);
			currentSubsurf.addTrimPolyline(segment);
			segment = new ArrayList<IVec2>();
			segment.add(loop[(i+startIdx)%loop.length]);
			currentSubsurf = subsurf;
		    }
		}
		else{
		    //IOut.p("loop["+((i+startIdx)%loop.length)+"]: same subsurf"); //
		    //IOut.p("subsurf = "+subsurf); //
		}
	    }
	    // last one
	    //IOut.p("last segment"); //
	    
	    segment.add(loop[startIdx]);
	    segment.add(loop[(startIdx+1)%loop.length]);
	    currentSubsurf.addTrimPolyline(segment);
	    
	}
	
	
	protected ISubsurfaceMesh getSubsurfaceOnPoint(IVec2 pt){
	    int uidx = getUIndex(pt);
	    int vidx = getVIndex(pt);
	    return getSubsurface(uidx,vidx);
	}

	
	protected boolean insideIndexRange(ISubsurfaceMesh subsurf,
					   int uIndexRange1, int vIndexRange1,
					   int uIndexRange2, int vIndexRange2){
	    if(subsurf.uindex < Math.min(uIndexRange1,uIndexRange2) ||
	       subsurf.uindex > Math.max(uIndexRange1,uIndexRange2) ||
	       subsurf.vindex < Math.min(vIndexRange1,vIndexRange2) ||
	       subsurf.vindex > Math.max(vIndexRange1,vIndexRange2) ) return false;
	    return true;
	}
	
	
	/**
	   @return aray of SubsurfaceIndex between 2 points not including the index where each point is
	*/
	protected ISubsurfaceMesh[] getSubsurfaceOnLine(IVec2 pt1, IVec2 pt2){
	    int u1 = getUIndex(pt1);
	    int v1 = getVIndex(pt1);
	    int u2 = getUIndex(pt2);
	    int v2 = getVIndex(pt2);
	    
	    //IOut.p("pt1 = "+pt1);
	    //IOut.p("pt2 = "+pt2);
	    //IOut.p("<"+u1+","+v1+">, <"+u2+","+v2+">"); //
	    
	    if(u1==u2 && v1==v2) return null;
	    
	    if(u1==u2){
		//IOut.p("u1==u2"); //
		
		if(Math.abs(v1-v2)==1) return null;
		int inc=1;
		if(v1>v2) inc=-1;
		int num = Math.abs(v1-v2)-1;
		ISubsurfaceMesh[] array = new ISubsurfaceMesh[num];
		for(int i=0; i<num; i++){
		    ISubsurfaceMesh subsurf = getSubsurface(u1, v1+(i+1)*inc);
		    subsurf.addVTrimLine(pt1,pt2);
		    array[i] = subsurf;
		}
		return array;
	    }
	    
	    if(v1==v2){
		//IOut.p("v1==v2"); //
		
		if(Math.abs(u1-u2)==1) return null;
		int inc=1;
		if(u1>u2) inc=-1;
		int num = Math.abs(u1-u2)-1;
		ISubsurfaceMesh[] array = new ISubsurfaceMesh[num];
		for(int i=0; i<num; i++){
		    ISubsurfaceMesh subsurf = getSubsurface(u1+(i+1)*inc, v1);
		    subsurf.addUTrimLine(pt1,pt2);
		    array[i] = subsurf;
		}
		return array;
	    }
	    
	    //IOut.p("u1!=u2 || v1!=v2"); //
	    
	    int prevU = u1, prevV = v1;
	    ISubsurfaceMesh subsurf = getNextSubsurfaceOnLine(pt1, pt2, u1, v1);
	    
	    //IOut.p("nextsurf = "+subsurf); //
	    
	    ArrayList<ISubsurfaceMesh> surfs = new ArrayList<ISubsurfaceMesh>();
	    while(subsurf!=null && !subsurf.equals(u2,v2)
		  && insideIndexRange(subsurf, u1, v1, u2, v2)
		  //&& pt1.diff(pt2).dot
		  //(getPoint(subsurf).mid
		  //(getPoint(subsurf.uindex+1,subsurf.vindex+1)).diff(pt2)) > 0 // for safty
		  ){
		ISubsurfaceMesh nextsurf =
		    getNextSubsurfaceOnLine(pt1,pt2,subsurf.uindex,subsurf.vindex);
		
		//IOut.p("nextsurf = "+nextsurf); //
		//if(nextsurf!=null){ // null is not returned at getSubsurface anymore
		addDiagonalTrimLine(pt1, pt2, subsurf,prevU,prevV,
				    nextsurf.uindex,nextsurf.vindex);
		surfs.add(subsurf);
		prevU = subsurf.uindex; prevV = subsurf.vindex;
		
		if(nextsurf.uindex<0 || nextsurf.uindex>matrix.length ||
		   nextsurf.vindex<0 || nextsurf.vindex>matrix[0].length)
		    nextsurf = null; // exiting the loop
		
		subsurf=nextsurf;
	    }
	    
	    //IOut.p("nextsurf(last) = "+subsurf); //
	    return surfs.toArray(new ISubsurfaceMesh[surfs.size()]);
	}
	
	
	protected void addDiagonalTrimLine(IVec2 pt1, IVec2 pt2,
					   ISubsurfaceMesh subsurf,
					   int prevU, int prevV, int nextU, int nextV){
	    
	    if(prevU == subsurf.uindex){
		if(prevV < subsurf.vindex){ // bottom
		    if(nextU == subsurf.uindex && nextV > subsurf.vindex){
			// bottom -> top
			subsurf.addVTrimLine(pt1,pt2); return;
		    }
		    if(nextU < subsurf.uindex && nextV >= subsurf.vindex){
			// bottom -> left or top left
			subsurf.addTrimLineAtBottomLeft(pt1,pt2); return;
		    }
		    if(nextU > subsurf.uindex && nextV >= subsurf.vindex){
			// bottom -> right or top right
			subsurf.addTrimLineAtBottomRight(pt1,pt2); return;
		    }
		}
		else if(prevV > subsurf.vindex){ // top
		    if(nextU == subsurf.uindex && nextV < subsurf.vindex){
			// top -> bottom
			subsurf.addVTrimLine(pt1,pt2); return;
		    }
		    if(nextU < subsurf.uindex && nextV <= subsurf.vindex){
			// top -> left or bottom left
			subsurf.addTrimLineAtTopLeft(pt1,pt2); return;
		    }
		    if(nextU > subsurf.uindex && nextV <= subsurf.vindex){
			// top -> right or bottom right
			subsurf.addTrimLineAtTopRight(pt1,pt2); return;
		    }
		}
	    }
	    else if(prevU < subsurf.uindex){
		if(prevV == subsurf.vindex){ // left
		    if(nextU > subsurf.uindex && nextV == subsurf.vindex){
			// left -> right
			subsurf.addUTrimLine(pt1,pt2); return;
		    }
		    if(nextU >= subsurf.uindex){
			if(nextV < subsurf.vindex){
			    // left -> bottom or bottom right
			    subsurf.addTrimLineAtBottomLeft(pt1,pt2); return;
			}
			if(nextV > subsurf.vindex){
			    // left -> top or top right
			    subsurf.addTrimLineAtTopLeft(pt1,pt2); return;
			}
		    }
		}
		else if(prevV < subsurf.vindex){ // bottom left 
		    if(nextU > subsurf.uindex && nextV >= subsurf.vindex){
			// bottom left -> right or top right
			subsurf.addUTrimLine(pt1,pt2); return;
		    }
		    if(nextU == subsurf.uindex && nextV > subsurf.vindex){
			// bottom left -> top
			subsurf.addVTrimLine(pt1,pt2); return;
		    }
		}
		else{ // prevV > subsurf.vindex // top left
		    if(nextU > subsurf.uindex && nextV <= subsurf.vindex){
			// top left -> right or bottom right
			subsurf.addUTrimLine(pt1,pt2); return;
		    }
		    if(nextU == subsurf.uindex && nextV < subsurf.vindex){
			// top left - > bottom
			subsurf.addVTrimLine(pt1,pt2); return;
		    }
		}
		
	    }
	    else{ // prevU > subsurf.uindex
		if(prevV == subsurf.vindex){ // right
		    if(nextU < subsurf.uindex && nextV == subsurf.vindex){
			// right -> left
			subsurf.addUTrimLine(pt1,pt2); return;
		    }
		    if(nextU <= subsurf.uindex){
			if(nextV < subsurf.vindex){
			    // right -> bottom or bottom left
			    subsurf.addTrimLineAtBottomRight(pt1,pt2); return;
			}
			if(nextV > subsurf.vindex){
			    // right -> top or top left
			    subsurf.addTrimLineAtTopRight(pt1,pt2); return;
			}
		    }
		}
		else if(prevV < subsurf.vindex){ // bottom right
		    if(nextU < subsurf.uindex && nextV >= subsurf.vindex){
			// bottom right -> left or top left
			subsurf.addUTrimLine(pt1,pt2); return;
		    }
		    if(nextU == subsurf.uindex && nextV > subsurf.vindex){
			// bottom right -> top
			subsurf.addVTrimLine(pt1,pt2); return;
		    }
		}
		else{ // prevV > subsurf.vindex // top right
		    if(nextU < subsurf.uindex && nextV <= subsurf.vindex){
			// top right -> left or bottom left
			subsurf.addUTrimLine(pt1,pt2); return;
		    }
		    if(nextU == subsurf.uindex && nextV < subsurf.vindex){
			// top right -> bottom
			subsurf.addVTrimLine(pt1,pt2); return;
		    }
		}
	    }
	    
	    IOut.err("neighboring location is invalid: prev=<"+
		     prevU+","+prevV+">, current=<"+subsurf.uindex+","+subsurf.vindex+
		     ">, next=<"+nextU+","+nextV+">"); 
	}
	
	
	
	protected ISubsurfaceMesh getNextSubsurfaceOnLine(IVec2 linePt1, IVec2 linePt2,
							  int uidx, int vidx){
	    if(linePt1.x < linePt2.x){
		if(uidx<unum){
		    if(vidx<vnum && isLineOnVEdge(linePt1,linePt2,uidx+1,vidx,vidx+1)){
			// horizontal
			//IOut.p("1"); //
			return getSubsurface(uidx+1, vidx);
		    }
		    if(isLineOnPoint(linePt1,linePt2,uidx+1,vidx)){
			// diagonal
			if(vidx>0 && linePt1.y > linePt2.y){
			    //IOut.p("2"); //
			    return getSubsurface(uidx+1, vidx-1);
			}
			// horizontal
			//IOut.p("3"); //
			return getSubsurface(uidx+1, vidx);
		    }
		    if(vidx<vnum && isLineOnPoint(linePt1,linePt2,uidx+1,vidx+1)){
			// diagonal
			if(linePt1.y < linePt2.y){
			    //IOut.p("4"); //
			    return getSubsurface(uidx+1, vidx+1);
			}
			// horizontal
			//IOut.p("5"); //
			return getSubsurface(uidx+1, vidx);
		    }
		}
	    }
	    else{
		if(uidx>0){
		    if(vidx<vnum && isLineOnVEdge(linePt1,linePt2,uidx,vidx,vidx+1)){
			// horizontal
			//IOut.p("6"); //
			return getSubsurface(uidx-1, vidx);
		    }
		    if(vidx>0 && isLineOnPoint(linePt1,linePt2,uidx,vidx)){
			// diagonal
			if(linePt1.y > linePt2.y){
			    //IOut.p("7"); //
			    return getSubsurface(uidx-1, vidx-1);
			}
			// horizontal
			//IOut.p("8"); //
			return getSubsurface(uidx-1, vidx);
		    }
		    if(vidx<vnum && isLineOnPoint(linePt1,linePt2,uidx,vidx+1)){
			// diagonal
			if(linePt1.y < linePt2.y){
			    //IOut.p("9"); //
			    return getSubsurface(uidx-1, vidx+1);
			}
			// horizontal
			//IOut.p("10"); //
			return getSubsurface(uidx-1, vidx);
		    }
		}
	    }
	    
	    if(linePt1.y < linePt2.y){
		if(vidx<vnum){
		    if(uidx<unum && isLineOnUEdge(linePt1,linePt2,uidx,uidx+1,vidx+1)){
			// vertical
			//IOut.p("11"); //
			return getSubsurface(uidx, vidx+1);
		    }
		    if(isLineOnPoint(linePt1,linePt2,uidx,vidx+1)){
			// diagonal
			if(linePt1.x > linePt2.x){
			    //IOut.p("12"); //
			    return getSubsurface(uidx-1, vidx+1);
			}
			// vertical
			//IOut.p("13"); //
			return getSubsurface(uidx, vidx+1);
		    }
		    if(uidx<unum && isLineOnPoint(linePt1,linePt2,uidx+1,vidx+1)){
			// diagonal
			if(linePt1.x < linePt2.x){
			    //IOut.p("14"); //
			    return getSubsurface(uidx+1, vidx+1);
			}
			// vertical
			//IOut.p("15"); //
			return getSubsurface(uidx, vidx+1);
		    }
		}
	    }
	    else{
		if(vidx>0){
		    if(uidx<unum && isLineOnUEdge(linePt1,linePt2,uidx,uidx+1,vidx)){
			// vertical
			//IOut.p("16"); //
			return getSubsurface(uidx, vidx-1);
		    }
		    if(isLineOnPoint(linePt1,linePt2,uidx,vidx)){
			// diagonal
			if(linePt1.x > linePt2.x){
			    //IOut.p("17"); //
			    return getSubsurface(uidx-1, vidx-1);
			}
			// vertical
			//IOut.p("18"); //
			return getSubsurface(uidx, vidx-1);
		    }
		    if(uidx<unum && isLineOnPoint(linePt1,linePt2,uidx+1,vidx)){
			// diagonal
			if(linePt1.x < linePt2.x){
			    //IOut.p("19"); //
			    return getSubsurface(uidx+1, vidx-1);
			}
			// vertical
			//IOut.p("20"); //
			return getSubsurface(uidx, vidx-1);
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
	    return z1*z2 < 0; // "<" for not including on the end points
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
	
	/*
	protected boolean isPointOnLeft(IVec2 pt, IVec2 linePt1, IVec2 linePt2){
	    return linePt2.diff(linePt1).cross(pt.diff(linePt1)).z>0;
	}
	*/
	
	protected int getUIndex(IVec2 pt){
	    // uvalues[0] must be 0
	    for(int i=0; i<unum; i++) if(pt.x < uvalues[i+1]) return i;
	    return unum-1; //return unum;
	}
	protected int getVIndex(IVec2 pt){
	    // vvalues[0] must be 0
	    for(int i=0; i<vnum; i++) if(pt.y < vvalues[i+1]) return i;
	    return vnum-1; //return vnum;
	}
	
	protected double getU(int uidx){ return uvalues[uidx]; }
	protected double getV(int vidx){ return vvalues[vidx]; }
	protected IVec2 getPoint(int uidx, int vidx){
	    return new IVec2(getU(uidx), getV(vidx));
	}
	protected IVec2 getPoint(ISubsurfaceMesh idx){
	    return new IVec2(getU(idx.uindex), getV(idx.vindex));
	}
	
    }
    
}
    
