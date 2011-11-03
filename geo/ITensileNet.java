/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

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


package igeo.geo;

import java.util.*;
import java.awt.Color;

import igeo.util.*;
import igeo.core.*;

/**
   Class to create tensile network which consists of IParticle and ITensionLine.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ITensileNet{
    
    public static double friction=0.0;
    public static double tension=1.0;
    public static double curveTension=1.0;
    
    
    public ArrayList<ITensionLine> links;
    public ArrayList<IParticleAgent> nodes;
    
    ITensileNet(){}
    ITensileNet(ArrayList<ITensionLine> links, ArrayList<IParticleAgent> nodes){
	this.links = links; this.nodes = nodes;
    }
    
    
    /**
       create a network of tension line.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
       @param connectionTolerance tolerance to if two end points are connected or not
    */
    public static ITensileNet create(ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean fixOpenEndPoint,
				     boolean deleteLines,
				     double connectionTolerance ){
	
	if(linkLines==null || linkLines.length==0){
	    IOut.err("no link line input found");
	    return null;
	}
	
	//ArrayList<IVec[]> endPts = new ArrayList<IVec[]>();
	IVec[][] endPts = new IVec[linkLines.length][2];
	
	for(int i=0; i<linkLines.length; i++){
	    //endPts.add(new IVec[]{ line.start().get(), line.end().get() });
	    endPts[i][0] = linkLines[i].start().get();
	    endPts[i][1] = linkLines[i].end().get();
	}
	
	Color[] lineColors = new Color[linkLines.length];
	IServer server=null;
	if(deleteLines){
	    for(int i=0; i<linkLines.length; i++){
		if(linkLines[i] instanceof IObject){
		    lineColors[i] = ((IObject)linkLines[i]).clr();
		    ((IObject)linkLines[i]).del();
		    if(server==null) server = ((IObject)linkLines[i]).server();
		}
	    }
	}
	
	if(server==null){ server = IG.current().server(); }
	
	// find connection and remove duplicates
	for(int i=0; i<endPts.length; i++){
	    IVec[] epts1 = endPts[i];
	    if(epts1[1].eq(epts1[0], connectionTolerance)){ epts1[1] = epts1[0]; }
	    for(int j=i+1; j<endPts.length; j++){
		IVec[] epts2 = endPts[j];
		for(int k=0; k<2; k++){
		    if(epts2[k].eq(epts1[0], connectionTolerance)) epts2[k] = epts1[0];
		    else if(epts2[k].eq(epts1[1], connectionTolerance)) epts2[k] = epts1[1];
		}
	    }
	}
	
	ArrayList<IVec> uniquePts = new ArrayList<IVec>();
	for(int i=0; i<endPts.length; i++){
	    for(int j=0; j<2; j++){
		IVec pt = endPts[i][j];
		if(!uniquePts.contains(pt)) uniquePts.add(pt);
	    }
	}
	
	if(fixOpenEndPoint){
	    // cheking naked end point
	    ArrayList<IVecI> openPts = new ArrayList<IVecI>();
	    for(int i=0; i<uniquePts.size(); i++){
		int count=0;
		for(int j=0; j<endPts.length; j++){
		    if(endPts[j][0] == uniquePts.get(i)) count++;
		    if(endPts[j][1] == uniquePts.get(i)) count++;
		}
		if(count==1){ openPts.add(uniquePts.get(i)); }
	    }
	    if(fixedPoints!=null){
		for(int i=0; i<fixedPoints.length; i++) openPts.add(fixedPoints[i]);
	    }
	    fixedPoints = openPts.toArray(new IVecI[openPts.size()]);
	}
	
	ITensileNet network = null;
	synchronized(server.dynamicServer()){
	    
	    ArrayList<IParticleAgent> particles = new ArrayList<IParticleAgent>();
	    for(int i=0; i<uniquePts.size(); i++){
		IParticleAgent pa = new IParticleAgent(uniquePts.get(i));
		pa.fric(friction);
		particles.add(pa);
	    }
	    
	    // fixing particle
	    if(fixedPoints!=null){
		for(int i=0; i<fixedPoints.length; i++){
		    for(int j=0; j<particles.size(); j++){
			if(fixedPoints[i].eq(particles.get(j), connectionTolerance)){
			    particles.get(j).fix();
			}
		    }
		}
	    }
	    
	    ArrayList<ITensionLine> tlines = new ArrayList<ITensionLine>();
	    for(int i=0; i<endPts.length; i++){
		IVec[] epts = endPts[i];
		int index1 = uniquePts.indexOf(epts[0]);
		int index2 = uniquePts.indexOf(epts[1]);
		if(index1>=0 && index2>=0){
		    IParticleI pa1 = particles.get(index1);
		    IParticleI pa2 = particles.get(index2);
		    ITensionLine tl = new ITensionLine(pa1, pa2, tension);
		    tl.tension(tension);
		    tlines.add(tl);
		}
		else{
		    IOut.err("end point is not found");
		}
	    }
	    network = new ITensileNet(tlines, particles);
	}
	
	return network;
    }
    
    
    public static ITensileNet create(ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean fixOpenEndLinkPoint,
				     boolean deleteLines){
	return create(linkLines, fixedPoints, fixOpenEndLinkPoint,deleteLines, IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean fixOpenEndLinkPoint){
	return create(linkLines, fixedPoints, fixOpenEndLinkPoint, true, IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] linkLines, boolean fixOpenEndLinkPoint){
	return create(linkLines, null, fixOpenEndLinkPoint, true, IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] linkLines, IVecI[] fixedPoints){
	return create(linkLines, fixedPoints, false, true, IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] linkLines){
	return create(linkLines, null, true, true, IConfig.tolerance);
    }
    
    
    
    public static ITensileNet create(ICurveI[] sectionCurves, ICurveI[] linkLines){
	return create(sectionCurves,linkLines,null,
		      true, false, true,true,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] sectionCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints){
	return create(sectionCurves,linkLines,fixedPoints,
		      true, false, true,true,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] sectionCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndSectionPoint,
				     boolean deleteLines ){
	return create(sectionCurves,linkLines,fixedPoints,
		      true, fixOpenEndLinkPoint, fixOpenEndSectionPoint,
		      deleteLines,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] sectionCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndSectionPoint,
				     boolean deleteLines,
				     double connectionTolerance ){
	return create(sectionCurves,linkLines,fixedPoints,
		      true, fixOpenEndLinkPoint, fixOpenEndSectionPoint,
		      deleteLines,connectionTolerance);
    }
    
    public static ITensileNet create(ICurveI[] sectionCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean tensionOnSameSection,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndSectionPoint,
				     boolean deleteLines){
	return create(sectionCurves,linkLines,fixedPoints,
		      tensionOnSameSection, fixOpenEndLinkPoint, fixOpenEndSectionPoint,
		      deleteLines,IConfig.tolerance);
    }
    
    /**
       create a network of tension line on section curve.
       @param sectionCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnSameSection when this is true, tension between points on the same curve is created.
       @param fixOpenEndPoint when this is true open end of link lines are fixed
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
       @param connectionTolerance tolerance to if two end points are connected or not
    */
    public static ITensileNet create(ICurveI[] sectionCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean tensionOnSameSection,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndSectionPoint,
				     boolean deleteLines,
				     double connectionTolerance ){
	if(sectionCurves==null || sectionCurves.length==0){
	    IOut.err("no section curve input found");
	    return null;
	}
	if(linkLines==null || linkLines.length==0){
	    IOut.err("no link line input found");
	    return null;
	}
	
	//ArrayList<IVec[]> endPts = new ArrayList<IVec[]>();
	IVec[][] endPts = new IVec[linkLines.length][2];
	
	for(int i=0; i<linkLines.length; i++){
	    //endPts.add(new IVec[]{ line.start().get(), line.end().get() });
	    endPts[i][0] = linkLines[i].start().get();
	    endPts[i][1] = linkLines[i].end().get();
	}
	
	Color[] lineColors = new Color[linkLines.length];
	ILayer[] lineLayer = new ILayer[linkLines.length];
	
	IServer server=null;
	if(deleteLines){
	    //for(ICurveI line : linkLines){
	    for(int i=0; i<linkLines.length; i++){
		if(linkLines[i] instanceof IObject){
		    lineColors[i] = ((IObject)linkLines[i]).clr();
		    lineLayer[i] = ((IObject)linkLines[i]).layer();
		    ((IObject)linkLines[i]).del();
		    if(server==null) server = ((IObject)linkLines[i]).server();
		}
	    }
	}
	
	if(server==null){ server = IG.current().server(); }
	
	// find connection and remove duplicates
	for(int i=0; i<endPts.length; i++){
	    IVec[] epts1 = endPts[i];
	    if(epts1[1].eq(epts1[0], connectionTolerance)){ epts1[1] = epts1[0]; }
	    for(int j=i+1; j<endPts.length; j++){
		IVec[] epts2 = endPts[j];
		for(int k=0; k<2; k++){
		    if(epts2[k].eq(epts1[0], connectionTolerance)) epts2[k] = epts1[0];
		    else if(epts2[k].eq(epts1[1], connectionTolerance)) epts2[k] = epts1[1];
		}
	    }
	}
	
	ArrayList<IVec> uniquePts = new ArrayList<IVec>();
	for(int i=0; i<endPts.length; i++){
	    for(int j=0; j<2; j++){
		IVec pt = endPts[i][j];
		if(!uniquePts.contains(pt)) uniquePts.add(pt);
	    }
	}
	
	if(fixOpenEndLinkPoint){
	    // cheking naked end point
	    ArrayList<IVecI> openPts = new ArrayList<IVecI>();
	    for(int i=0; i<uniquePts.size(); i++){
		int count=0;
		for(int j=0; j<endPts.length; j++){
		    if(endPts[j][0] == uniquePts.get(i)) count++;
		    if(endPts[j][1] == uniquePts.get(i)) count++;
		}
		if(count==1){ openPts.add(uniquePts.get(i)); }
	    }
	    if(fixedPoints!=null){
		for(int i=0; i<fixedPoints.length; i++) openPts.add(fixedPoints[i]);
	    }
	    fixedPoints = openPts.toArray(new IVecI[openPts.size()]);
	}
	
	boolean[] fixlist = new boolean[uniquePts.size()];
	for(int i=0; i<uniquePts.size(); i++){
	    fixlist[i] = false;
	    if(fixedPoints!=null){
		for(int j=0; j<fixedPoints.length && !fixlist[i]; j++){
		    if(fixedPoints[j].eq(uniquePts.get(i), connectionTolerance)){
			fixlist[i]=true;
		    }
		}
	    }
	}
	
	ArrayList<IParticleOnCurve>[] particleOnSection = null;
	
	
	if(tensionOnSameSection){
	    // !!!
	    @SuppressWarnings("unchecked")
		ArrayList<IParticleOnCurve>[] pos = new ArrayList[sectionCurves.length];
	    particleOnSection = pos;
	    
	    for(int i=0; i<particleOnSection.length; i++){
		particleOnSection[i] = new ArrayList<IParticleOnCurve>();
	    }
	}
	
	
	ITensileNet network = null;
	synchronized(server.dynamicServer()){
	    
	    final double uCurveTolerance = 1.0/1000;
	    
	    // find a section curve to be on for each uniquePts
	    ArrayList<IParticleAgent> particles = new ArrayList<IParticleAgent>();
	    
	    for(int i=0; i<uniquePts.size(); i++){
		if(uniquePts.size()>100 && i%100==0){
		    IOut.debug(0, "finding curve for point to be on ("+i+"/"+uniquePts.size()+")"); //
		}
		
		IParticleOnCurve poc = createParticleOnClosestCurve(sectionCurves,
								    uniquePts.get(i),
								    connectionTolerance,
								    uCurveTolerance);
		IParticleAgent pa=null;
		if(poc!=null){
		    pa = new IParticleAgent(poc);
		}
		else{
		    pa = new IParticleAgent(uniquePts.get(i));
		    pa.fix(); // if not on the curve, fixed.
		    pa.clr(1.0,1.0,0); // yellow if not on the curve
		}
		
		pa.fric(friction);
		particles.add(pa);
		
		if(fixlist[i]){ pa.fix(); pa.clr(1.0,1.0,0); }
		
		if(poc!=null && tensionOnSameSection){
		    // put particle on each list of section
		    int sectIdx = -1;
		    for(int j=0; j<sectionCurves.length && sectIdx<0; j++){
			if(sectionCurves[j]==poc.curve()) sectIdx=j;
		    }
		    if(sectIdx>=0){ particleOnSection[sectIdx].add(poc); }
		    
		    if(fixOpenEndSectionPoint){
			if(poc.upos()<IConfig.parameterTolerance ||
			   poc.upos()>1.0-IConfig.parameterTolerance){
			    poc.fix();
			    if(pa!=null) pa.clr(1.0,1.0,0);
			}
		    }
		}
	    }
	    
	    
	    
	    ArrayList<ITensionLine> tlines = new ArrayList<ITensionLine>();
	    for(int i=0; i<endPts.length; i++){
		IVec[] epts = endPts[i];
		int index1 = uniquePts.indexOf(epts[0]);
		int index2 = uniquePts.indexOf(epts[1]);
		if(index1>=0 && index2>=0){
		    IParticleI pa1 = particles.get(index1);
		    IParticleI pa2 = particles.get(index2);
		    ITensionLine tl = new ITensionLine(pa1, pa2, tension);
		    if(lineColors[i]!=null) tl.clr(lineColors[i]);
		    if(lineLayer[i]!=null) tl.layer(lineLayer[i]);
		    tl.tension(tension);
		    tlines.add(tl);
		}
		else{
		    IOut.err("end point is not found");
		}
	    }
	    
	    
	    if(tensionOnSameSection){
		
		// sort particleoncurve and create tension
		
		for(int i=0; i<sectionCurves.length; i++){
		    if(particleOnSection[i].size()>1){
			ISort.sort(particleOnSection[i], new IParticleOnCurveComparator());
			
			for(int j=0; j<particleOnSection[i].size()-1; j++){
			    //new ITensionLineOnCurve(particleOnSection[i].get(j),particleOnSection[i].get(j+1),curveTension).clr(IRand.clr());
			    new ITensionOnCurve(particleOnSection[i].get(j),particleOnSection[i].get(j+1),curveTension);
			}
		    }
		}
		
	    }
	    
	    
	    network = new ITensileNet(tlines, particles);
	}

	/*
	//tmp hiding section
	for(int i=0; i<sectionCurves.length; i++){
	    if(sectionCurves[i] instanceof IObject) ((IObject)sectionCurves[i]).hide();  //
	}
	*/
	
	return network;
	
    }
    
    
    public static ITensileNet create(ICurveI[] sectionCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints){
	return create(sectionCurves,linkLines,fixedLines,fixedPoints,
		      true,false,true,true,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] sectionCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints,
				     boolean tensionOnSameSection,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndSectionPoint){
	return create(sectionCurves,linkLines,fixedLines,fixedPoints,
		      tensionOnSameSection,fixOpenEndLinkPoint,
		      fixOpenEndSectionPoint,true,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] sectionCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints,
				     boolean tensionOnSameSection,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndSectionPoint,
				     boolean deleteLines){
	return create(sectionCurves,linkLines,fixedLines,fixedPoints,
		      tensionOnSameSection,fixOpenEndLinkPoint,
		      fixOpenEndSectionPoint,deleteLines,IConfig.tolerance);
    }
    
    /**
       create a network of tension line on section curve.
       @param sectionCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnSameSection when this is true, tension between points on the same curve is created.
       @param fixOpenEndPoint when this is true open end of link lines are fixed
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
       @param connectionTolerance tolerance to if two end points are connected or not
    */
    public static ITensileNet create(ICurveI[] sectionCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints,
				     boolean tensionOnSameSection,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndSectionPoint,
				     boolean deleteLines,
				     double connectionTolerance ){
	
	ArrayList<IVecI> fixedPts = new ArrayList<IVecI>();
	
	for(int i=0; i<fixedLines.length; i++){
	    IVec spt = fixedLines[i].start().get();
	    IVec ept = fixedLines[i].end().get();
	    if(spt.eq(ept, connectionTolerance)){ ept = null; }
	    
	    for(int j=0; j<fixedPts.size() && spt!=null; j++){
		if(spt.eq(fixedPts.get(j),connectionTolerance)) spt=null;
	    }
	    if(spt!=null) fixedPts.add(spt);
	    
	    for(int j=0; j<fixedPts.size() && ept!=null; j++){
		if(ept.eq(fixedPts.get(j), connectionTolerance)) ept=null;
	    }
	    if(ept!=null) fixedPts.add(ept);
	}
	
	for(int i=0; fixedPoints!=null && i<fixedPoints.length; i++){
	    fixedPts.add(fixedPoints[i]);
	}
	
	
	return create(sectionCurves,linkLines,
		      fixedPts.toArray(new IVecI[fixedPts.size()]),
		      tensionOnSameSection,
		      fixOpenEndLinkPoint,
		      fixOpenEndSectionPoint,
		      deleteLines,
		      connectionTolerance);
	
    }
    
    /**
       find the closest curve to the point pos out of sectionsCurves.
       @param sectionCurves target curves to search
       @param pos point to measure the distance to check how close
       @param searchResolution resolution per a curve specifying how many points per a curve to be checked
    */
    public static ICurveI findClosestCurve(ICurveI[] sectionCurves, IVec pos, int searchResolution){
	double minDist=-1;
	int minCurveIdx=-1;
	for(int i=0; i<sectionCurves.length; i++){
	    for(int j=0; j<=searchResolution; j++){
		double dist = sectionCurves[i].pt((double)j/searchResolution).dist(pos);
		if(minCurveIdx<0 || dist<minDist){
		    minCurveIdx = i;
		    minDist = dist;
		}
	    }
	}
	if(minCurveIdx<0) return null; // when sectionCurves.length==0?
	return sectionCurves[minCurveIdx];
    }
    
    /**
       find the closest curve to the point and put particle on the curve.
       if the curve is not close enough within closenessTolerance, particle is not created.
       @param sectionCurves target curves to search
       @param pos point to measure the distance to check how close
       @param closenessTolerance tolerance to determine if the point is on the curve or not
       @param uTolerance tolerance in u parameter to specify how precise u parameter of particle shoould be
    */
    public static IParticleOnCurve createParticleOnClosestCurve(ICurveI[] sectionCurves,
								IVec pos,
								double closenessTolerance,
								double uTolerance){
	final int roughSearchResolution = 20;
	IParticleOnCurve particle = null;
	do{
	    ICurveI closestCrv = null;
	    if(sectionCurves.length==1) closestCrv = sectionCurves[0];
	    else closestCrv = findClosestCurve(sectionCurves, pos, roughSearchResolution);
	    
	    particle = createParticleOnCurve(closestCrv, pos, closenessTolerance, uTolerance);
	    
	    if(particle==null){
		if(sectionCurves.length>1){
		    ICurveI[] sectionCurves2 = new ICurveI[sectionCurves.length-1];
		    for(int i=0, j=0; i<sectionCurves.length; i++, j++){
			if(sectionCurves[i]!=closestCrv){ sectionCurves2[j] = sectionCurves[i]; }
			else{ j--; }
		    }
		    sectionCurves = sectionCurves2;
		}
		else{ return null; } // after checking all curves, there is nothing close enough
	    }
	}while(particle == null && sectionCurves.length>1);
	
	return particle;
    }
    
    
    /**
       put particle on the curve.
       if the curve is not close enough within closenessTolerance, particle is not created.
       @param sectionCurve target curves to be on.
       @param pos point to specify location of particle on the curve and this point is embed inside IParticleOnCurve and controlled by it.
       @param closenessTolerance tolerance to determine if the point is on the curve or not. if this is negative, it will create any closest particle to the point even if it's far.
       @param uTolerance tolerance in u parameter to specify how precise u parameter of particle shoould be
    */
    public static IParticleOnCurve createParticleOnCurve(ICurveI curve,
							 IVec pos,
							 double closenessTolerance,
							 double uTolerance){
	final int maxResolution = 10000;
	int resolution=0;
	if(curve==null) return null;
	
	if(uTolerance==0){ resolution = maxResolution; }
	else{ resolution = (int)(1.0/uTolerance + 0.5); }
	
	double minDist=-1;
	double minU=-1;
	for(int i=0; i<=resolution; i++){
	    double u = (double)i/resolution;
	    double dist = curve.pt(u).dist(pos);
	    if(minU<0 || dist<minDist){
		minDist = dist;
		minU = u;
	    }
	}
	
	// re-project pos on curve
	if(minU>=0){
	    IVec pt = curve.pt(minU).get();
	    IVec tan = curve.tan(minU).get();
	    double tlen2 = tan.len2();
	    
	    double udiff = pos.diff(pt).dot(tan)/tlen2;
	    
	    minU += udiff;
	    if(minU < 0.0) minU = 0.0;
	    if(minU > 1.0) minU = 1.0;
	    
	    minDist = curve.pt(minU).dist(pos);
	}
	
	if(closenessTolerance >=0 && minDist > closenessTolerance) return null; // no particle found
	
	return new IParticleOnCurve(curve, minU, pos);
    }
    
    
    public static class IParticleOnCurveComparator implements IComparator<IParticleOnCurve>{
	public int compare(IParticleOnCurve p1, IParticleOnCurve p2){
	    if(p1.upos()<p2.upos()) return -1;
	    if(p1.upos()>p2.upos()) return 1;
	    return 0;
	}
    }
}
