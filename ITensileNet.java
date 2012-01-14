/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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
import java.awt.Color;

/**
   Class to create tensile network which consists of IParticle and ITensionLine.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ITensileNet{
    
    // physical attributes
    /** friction of node points (of IParticleAgent) */
    public static double friction = 0.005; //0.0; 
    /** strength of tension (of ITensionLine) */
    public static double tension = 1.0;
    /** strength of tension between points on the same rail curves */
    public static double onRailTension = 1.0;
    /** strength of tension to straighten lines */
    public static double straightenerTension = 1.0;
    /** strength of tension to equalize spacing on the same rail curves */
    public static double equalizerTension = 1.0;
    
    /** boolean switch to make tension (of ITensionLine) constant not depending on the distance of two points*/
    public static boolean constantTension = false;
    
    
    /** tolerance to check which points are identical and which lines are connected */
    public static double tolerance = IConfig.tolerance;
    
    /** tolerance to check which points are on rail */
    public static double railTolerance = IConfig.tolerance;
    
    
    /** color of node points */
    public static Color pointColor = new Color(255,255,255);
    /** color of node points on rail curve */
    public static Color railPointColor = new Color(192,192,192);
    /** color of fixed node points */
    public static Color fixedPointColor = new Color(255,255,0);
    /** color of straightener curve */
    public static Color straightenerColor = new Color(255,128,0);
    
    
    public static String pointLayer = "nodes";
    public static String fixedPointLayer = "fixedNodes";
    public static String straightenerLayer = "straightener";
    public static String equalizerLayer = "equalizer";
    
    
    // boolean options for create() methods
    /** boolean switch to fix open end points of lines which are not connected any other line */
    public static boolean fixOpenLinePoint=true;
    
    /** boolean switch to delete input lines */
    public static boolean deleteInputLine=true;
    
    /** in case with rail curves, boolean switch to put tension between points on the same rail curve */
    public static boolean tensionOnSameRail=true;
    
    /** in case with rail curves, boolean switch to fix points which are on end points of rail curves */
    public static boolean fixPointOnRailEnd=true;
    
    /** in case with rail curves, boolean switch to fix points which are not on any rail curves */
    public static boolean fixPointNotOnRail=true;
    
    /** boolean switch to put straightening force on connected lines */
    public static boolean enableStraightener=false; //true;
    
    /** if the connected line is less than this angle, it put straightening force on those lines */
    public static double straightenerThresholdAngle=Math.PI/3;
    
    /** remove straighteners whose two points are shared and branching. */
    public static boolean noBranchingStraightener=true;
    
    
    /** boolean switch to put spacing equalizer force on the same rail curves */
    public static boolean enableSpacingEqualizer=false; // default false
    
    
    
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
    */
    public static ITensileNet create(ICurveI[] linkLines, IVecI[] fixedPoints){
	// boolean fixOpenEndPoint, boolean deleteLines, double connectionTolerance){
	
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
	if(deleteInputLine){
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
	    if(epts1[1].eq(epts1[0], tolerance)){ epts1[1] = epts1[0]; }
	    for(int j=i+1; j<endPts.length; j++){
		IVec[] epts2 = endPts[j];
		for(int k=0; k<2; k++){
		    if(epts2[k].eq(epts1[0], tolerance)) epts2[k] = epts1[0];
		    else if(epts2[k].eq(epts1[1], tolerance)) epts2[k] = epts1[1];
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
	
	if(fixOpenLinePoint){
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
		pa.clr(pointColor);
		particles.add(pa);
		if(pointLayer!=null) pa.layer(IG.layer(pointLayer).clr(pointColor));
	    }
	    
	    // fixing particle
	    if(fixedPoints!=null){
		for(int i=0; i<fixedPoints.length; i++){
		    for(int j=0; j<particles.size(); j++){
			if(fixedPoints[i].eq(particles.get(j), tolerance)){
			    particles.get(j).fix();
			    particles.get(j).clr(fixedPointColor);
			    if(fixedPointLayer!=null) particles.get(j).layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
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
		    tl.constant(constantTension);
		    tlines.add(tl);
		}
		else{
		    IOut.err("end point is not found");
		}
	    }
	    
	    if(enableStraightener){
		ArrayList<IStraightenerCurve> straighteners = new ArrayList<IStraightenerCurve>();
		
		for(int i=0; i<tlines.size(); i++){
		    IParticleI p11 = tlines.get(i).pt(0);
		    IParticleI p12 = tlines.get(i).pt(1);
		    for(int j=i+1; j<tlines.size(); j++){
			IParticleI p21 = tlines.get(j).pt(0);
			IParticleI p22 = tlines.get(j).pt(1);
			
			IParticleI p1=null,p2=null,p3=null;
			if(p11==p21){ p1=p12; p2=p11; p3=p22; }
			else if(p11==p22){ p1=p12; p2=p11; p3=p21; }
			else if(p12==p21){ p1=p11; p2=p12; p3=p22; }
			else if(p12==p22){ p1=p11; p2=p12; p3=p21; }
			
			if( p1!=null && p2!=null && p3!=null && 
			    Math.abs(p2.pos().diff(p1.pos()).angle(p3.pos().diff(p2.pos()))) <
			    straightenerThresholdAngle){
			    IStraightenerCurve straightener = new IStraightenerCurve(p1,p2,p3);
			    straightener.tension(straightenerTension);
			    straightener.clr(straightenerColor);
			    straightener.layer(IG.layer(straightenerLayer).clr(straightenerColor));
			    straighteners.add(straightener);
			}
		    }
		}
		
		if(noBranchingStraightener){
		    for(int i=0; i<straighteners.size(); i++){
			IParticleI p11 = straighteners.get(i).pt(0);
			IParticleI p12 = straighteners.get(i).pt(1);
			IParticleI p13 = straighteners.get(i).pt(2);
			boolean ideleted=false;
			for(int j=i+1; j<straighteners.size() && !ideleted; j++){
			    IParticleI p21 = straighteners.get(j).pt(0);
			    IParticleI p22 = straighteners.get(j).pt(1);
			    IParticleI p23 = straighteners.get(j).pt(2);
			    
			    if(p12==p22 &&
			       (p11==p21 || p11==p23 || p13==p21 || p13==p23) ){
				
				double angle1=p12.pos().diff(p11.pos()).angle(p13.pos().diff(p12.pos()));
				double angle2=p22.pos().diff(p21.pos()).angle(p23.pos().diff(p22.pos()));
				
				if(Math.abs(angle1) < Math.abs(angle2)){
				    straighteners.get(j).del();
				    straighteners.remove(j);
				    j--;
				}
				else{
				    straighteners.get(i).del();
				    straighteners.remove(i);
				    i--;
				    j--;
				    ideleted=true;
				}
			    }
			}
		    }
		}
		
	    }
	    
	    network = new ITensileNet(tlines, particles);
	}
	
	return network;
    }
    
    /*
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
    */
    


    
    
    /*
    public static ITensileNet create(ICurveI[] railCurves, ICurveI[] linkLines){
	return create(railCurves,linkLines,null,
		      true, false, true,true,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints){
	return create(railCurves,linkLines,fixedPoints,
		      true, false, true,true,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean deleteLines ){
	return create(railCurves,linkLines,fixedPoints,
		      true, fixOpenEndLinkPoint, fixOpenEndRailPoint,
		      deleteLines,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean deleteLines,
				     double connectionTolerance ){
	return create(railCurves,linkLines,fixedPoints,
		      true, fixOpenEndLinkPoint, fixOpenEndRailPoint,
		      deleteLines,connectionTolerance);
    }
    
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean tensionOnSameRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean deleteLines){
	return create(railCurves,linkLines,fixedPoints,
		      tensionOnSameRail, fixOpenEndLinkPoint, fixOpenEndRailPoint,
		      deleteLines,IConfig.tolerance);
    }
    */
    
    /**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnSameRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
       @param connectionTolerance tolerance to if two end points are connected or not
    */
    /*
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean tensionOnSameRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean deleteLines,
				     double connectionTolerance ){
	return create(railCurves,linkLines,fixedPoints,
		      tensionOnSameRail,fixOpenEndLinkPoint,
		      true, deleteLines, connectionTolerance);
    }
    */
    
    /**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnSameRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param fixPointNotOnRail fix points which are not on the rail curve
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
    */
    /*
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean tensionOnSameRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean fixPointNotOnRail,
				     boolean deleteLines){
	return create(railCurves,linkLines,fixedPoints,
		      tensionOnSameRail,fixOpenEndLinkPoint,
		      fixPointNotOnRail, deleteLines, IConfig.tolerance);
    }
    */
    /**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnSameRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param fixPointNotOnRail fix points which are not on the rail curve
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
       @param connectionTolerance tolerance to if two end points are connected or not
    */
    
    
    /**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed. this can be null.
    */
    public static ITensileNet create(ICurveI[] railCurves, ICurveI[] linkLines, IVecI[] fixedPoints){
	//boolean tensionOnSameRail, boolean fixOpenEndLinkPoint, boolean fixOpenEndRailPoint,
	//boolean fixPointNotOnRail, boolean deleteLines, double connectionTolerance ){
	
	if(railCurves==null || railCurves.length==0){
	    //IOut.err("no rail curve input found");
	    // use create method without railCurves
	    return create(linkLines, fixedPoints);
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
	if(deleteInputLine){
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
	    if(epts1[1].eq(epts1[0], tolerance)){ epts1[1] = epts1[0]; }
	    for(int j=i+1; j<endPts.length; j++){
		IVec[] epts2 = endPts[j];
		for(int k=0; k<2; k++){
		    if(epts2[k].eq(epts1[0], tolerance)) epts2[k] = epts1[0];
		    else if(epts2[k].eq(epts1[1], tolerance)) epts2[k] = epts1[1];
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
	
	
	ArrayList<IVecI> openPts = null;
	if(fixOpenLinePoint){
	    // cheking naked end point
	    openPts = new ArrayList<IVecI>();
	    for(int i=0; i<uniquePts.size(); i++){
		int count=0;
		for(int j=0; j<endPts.length; j++){
		    if(endPts[j][0] == uniquePts.get(i)) count++;
		    if(endPts[j][1] == uniquePts.get(i)) count++;
		}
		if(count==1){ openPts.add(uniquePts.get(i)); }
	    }
	    //if(fixedPoints!=null) for(int i=0; i<fixedPoints.length; i++) openPts.add(fixedPoints[i]);
	    //fixedPoints = openPts.toArray(new IVecI[openPts.size()]);
	}
	
	boolean[] fixlist = new boolean[uniquePts.size()];
	for(int i=0; i<uniquePts.size(); i++){
	    fixlist[i] = false;
	    if(fixedPoints!=null){
		for(int j=0; j<fixedPoints.length && !fixlist[i]; j++){
		    if(fixedPoints[j].eq(uniquePts.get(i), tolerance)){
			fixlist[i]=true;
		    }
		}
	    }
	}
	
	ArrayList<IParticleOnCurve>[] particleOnRail = null;
	
	
	if(tensionOnSameRail || enableSpacingEqualizer){
	    // !!!
	    @SuppressWarnings("unchecked")
		ArrayList<IParticleOnCurve>[] pos = new ArrayList[railCurves.length];
	    particleOnRail = pos;
	    
	    for(int i=0; i<particleOnRail.length; i++){
		particleOnRail[i] = new ArrayList<IParticleOnCurve>();
	    }
	}
	
	
	ITensileNet network = null;
	synchronized(server.dynamicServer()){
	    
	    final double uCurveTolerance = 1.0/1000;
	    
	    // find a rail curve to be on for each uniquePts
	    ArrayList<IParticleAgent> particles = new ArrayList<IParticleAgent>();
	    
	    for(int i=0; i<uniquePts.size(); i++){
		if(uniquePts.size()>100 && i%100==0){
		    IOut.debug(0, "finding curve for point to be on ("+i+"/"+uniquePts.size()+")"); //
		}
		
		IParticleOnCurve poc = createParticleOnClosestCurve(railCurves,
								    uniquePts.get(i),
								    railTolerance, //tolerance,
								    uCurveTolerance);
		IParticleAgent pa=null;
		if(poc!=null){
		    pa = new IParticleAgent(poc);
		    pa.clr(railPointColor);
		    if(pointLayer!=null) pa.layer(IG.layer(pointLayer).clr(pointColor));
		    
		    //unfix if it's 
		}
		else{
		    pa = new IParticleAgent(uniquePts.get(i));
		    if(fixPointNotOnRail ||
		       fixOpenLinePoint &&openPts!=null && openPts.contains(uniquePts.get(i))){ // in case the point is open point and fixOpenLinePoint is true.
			pa.fix(); // if not on the curve, fixed.
			pa.clr(fixedPointColor);
			if(fixedPointLayer!=null) pa.layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
		    }
		    else{
			pa.clr(pointColor);
			if(pointLayer!=null) pa.layer(IG.layer(pointLayer).clr(pointColor));
		    }
		}
		
		pa.fric(friction);
		particles.add(pa);
		
		if(fixlist[i]){
		    pa.fix();
		    pa.clr(fixedPointColor);
		    if(fixedPointLayer!=null) pa.layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
		}
		
		if(poc!=null){
		    if(tensionOnSameRail || enableSpacingEqualizer){
			// put particle on each list of rail
			int sectIdx = -1;
			for(int j=0; j<railCurves.length && sectIdx<0; j++){
			    if(railCurves[j]==poc.curve()) sectIdx=j;
			}
			if(sectIdx>=0){ particleOnRail[sectIdx].add(poc); }
		    }
		    
		    if(fixPointOnRailEnd){
			if(poc.upos()<IConfig.parameterTolerance ||
			   poc.upos()>1.0-IConfig.parameterTolerance){
			    poc.fix();
			    if(pa!=null){
				pa.clr(fixedPointColor);
				if(fixedPointLayer!=null) pa.layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
			    }
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
		    tl.constant(constantTension);
		    tlines.add(tl);
		}
		else{
		    IOut.err("end point is not found");
		}
	    }
	    
	    if(tensionOnSameRail || enableSpacingEqualizer){
		// sort particleoncurve and create tension
		
		for(int i=0; i<railCurves.length; i++){
		    if(particleOnRail[i].size()>1){
			ISort.sort(particleOnRail[i], new IParticleOnCurveComparator());
			
			boolean closed=railCurves[i].isClosed();
			
			if(tensionOnSameRail){
			    for(int j=0; !closed && j<particleOnRail[i].size()-1 ||
				    closed && j<particleOnRail[i].size(); j++){
				//new ITensionLineOnCurve(particleOnRail[i].get(j),particleOnRail[i].get(j+1),onRailTension).clr(IRand.clr());
				ITensionOnCurve toc =
				    new ITensionOnCurve(particleOnRail[i].get(j),
							particleOnRail[i].get((j+1)%particleOnRail[i].size()),
							onRailTension);
				//toc.constant(constantTension); // not for tension on curves
			    }
			}
			
			if(enableSpacingEqualizer){
			    for(int j=0; !closed && j<particleOnRail[i].size()-2 ||
				    closed && j<particleOnRail[i].size(); j++){
				//new ITensionLineOnCurve(particleOnRail[i].get(j),particleOnRail[i].get(j+1),onRailTension).clr(IRand.clr());
				ISpacingEqualizer se = 
				    new ISpacingEqualizer(particleOnRail[i].get(j),
							  particleOnRail[i].get((j+1)%particleOnRail[i].size()),
							  particleOnRail[i].get((j+2)%particleOnRail[i].size())).tension(equalizerTension);
				
				//toc.constant(constantTension); // not for tension on curves
			    }
			    
			}
		    }
		}
	    }
	    
	    if(enableStraightener){
		
		ArrayList<IStraightenerCurve> straighteners = new ArrayList<IStraightenerCurve>();
		
		for(int i=0; i<tlines.size(); i++){
		    IParticleI p11 = tlines.get(i).pt(0);
		    IParticleI p12 = tlines.get(i).pt(1);
		    for(int j=i+1; j<tlines.size(); j++){
			IParticleI p21 = tlines.get(j).pt(0);
			IParticleI p22 = tlines.get(j).pt(1);
			
			IParticleI p1=null,p2=null,p3=null;
			if(p11==p21){ p1=p12; p2=p11; p3=p22; }
			else if(p11==p22){ p1=p12; p2=p11; p3=p21; }
			else if(p12==p21){ p1=p11; p2=p12; p3=p22; }
			else if(p12==p22){ p1=p11; p2=p12; p3=p21; }
			
			if( p1!=null && p2!=null && p3!=null && 
			    Math.abs(p2.pos().diff(p1.pos()).angle(p3.pos().diff(p2.pos()))) <
			    straightenerThresholdAngle){
			    IStraightenerCurve straightener = new IStraightenerCurve(p1,p2,p3);
			    straightener.tension(straightenerTension);
			    straightener.clr(straightenerColor);
			    straightener.layer(IG.layer(straightenerLayer).clr(straightenerColor));
			    
			    straighteners.add(straightener);
			}
			
		    }
		}
		
		if(noBranchingStraightener){
		    for(int i=0; i<straighteners.size(); i++){
			IParticleI p11 = straighteners.get(i).pt(0);
			IParticleI p12 = straighteners.get(i).pt(1);
			IParticleI p13 = straighteners.get(i).pt(2);
			boolean ideleted=false;
			for(int j=i+1; j<straighteners.size() && !ideleted; j++){
			    IParticleI p21 = straighteners.get(j).pt(0);
			    IParticleI p22 = straighteners.get(j).pt(1);
			    IParticleI p23 = straighteners.get(j).pt(2);
			    
			    if(p12==p22 &&
			       (p11==p21 || p11==p23 || p13==p21 || p13==p23) ){
				
				double angle1=p12.pos().diff(p11.pos()).angle(p13.pos().diff(p12.pos()));
				double angle2=p22.pos().diff(p21.pos()).angle(p23.pos().diff(p22.pos()));
				
				if(Math.abs(angle1) < Math.abs(angle2)){
				    straighteners.get(j).del();
				    straighteners.remove(j);
				    j--;
				}
				else{
				    straighteners.get(i).del();
				    straighteners.remove(i);
				    i--;
				    j--;
				    ideleted=true;
				}
			    }
			}
		    }
		}
	    }
	    
	    
	    network = new ITensileNet(tlines, particles);
	}
	
	/*
	//tmp hiding rail
	for(int i=0; i<railCurves.length; i++){
	    if(railCurves[i] instanceof IObject) ((IObject)railCurves[i]).hide();  //
	}
	*/
	
	return network;
    }
    
    /*
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints){
	return create(railCurves,linkLines,fixedLines,fixedPoints,
		      true,false,true,true,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints,
				     boolean tensionOnSameRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint){
	return create(railCurves,linkLines,fixedLines,fixedPoints,
		      tensionOnSameRail,fixOpenEndLinkPoint,
		      fixOpenEndRailPoint,true,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints,
				     boolean tensionOnSameRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean deleteLines){
	return create(railCurves,linkLines,fixedLines,fixedPoints,
		      tensionOnSameRail,fixOpenEndLinkPoint,
		      fixOpenEndRailPoint,deleteLines,IConfig.tolerance);
    }
    */
    
    /**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnSameRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
       @param connectionTolerance tolerance to if two end points are connected or not
    */
    /*
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints,
				     boolean tensionOnSameRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean deleteLines,
				     double connectionTolerance ){
	return create(railCurves,linkLines,fixedLines,fixedPoints,
		      tensionOnSameRail,fixOpenEndLinkPoint,
		      fixOpenEndRailPoint,true,deleteLines,connectionTolerance);
    }
    */
/**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnSameRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param fixPointNotOnRail fix points which are not on the rail curve
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
*/
    /*
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints,
				     boolean tensionOnSameRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean fixPointNotOnRail,
				     boolean deleteLines ){
	
	return create(railCurves,linkLines,fixedLines,fixedPoints,
		      tensionOnSameRail,fixOpenEndLinkPoint,
		      fixOpenEndRailPoint,fixPointNotOnRail,deleteLines,IConfig.tolerance);
    }
    */
    
    /* ***old API
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnSameRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param fixPointNotOnRail fix points which are not on the rail curve
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
       @param connectionTolerance tolerance to if two end points are connected or not
    */
    /**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedLines end points of fixedLines are added to fixedPoints.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
    */
    public static ITensileNet create(ICurveI[] railCurves, ICurveI[] linkLines,
				     ICurveI[] fixedLines,IVecI[] fixedPoints){
	// //boolean tensionOnSameRail,
	//boolean fixOpenEndLinkPoint, boolean fixOpenEndRailPoint,
	// boolean fixPointNotOnRail, boolean deleteLines,double  connectionTolerance ){
		
	ArrayList<IVecI> fixedPts = new ArrayList<IVecI>();
	
	for(int i=0; i<fixedLines.length; i++){
	    IVec spt = fixedLines[i].start().get();
	    IVec ept = fixedLines[i].end().get();
	    if(spt.eq(ept, tolerance)){ ept = null; }
	    
	    for(int j=0; j<fixedPts.size() && spt!=null; j++){
		if(spt.eq(fixedPts.get(j),tolerance)) spt=null;
	    }
	    if(spt!=null) fixedPts.add(spt);
	    
	    for(int j=0; j<fixedPts.size() && ept!=null; j++){
		if(ept.eq(fixedPts.get(j), tolerance)) ept=null;
	    }
	    if(ept!=null) fixedPts.add(ept);
	}
	
	for(int i=0; fixedPoints!=null && i<fixedPoints.length; i++){
	    fixedPts.add(fixedPoints[i]);
	}
	
	return create(railCurves,linkLines,fixedPts.toArray(new IVecI[fixedPts.size()]));
	//tensionOnSameRail,fixOpenEndLinkPoint,fixOpenEndRailPoint,fixPointNotOnRail,deleteLines,
	//connectionTolerance);
    }
    
    /**
       find the closest curve to the point pos out of railsCurves.
       @param railCurves target curves to search
       @param pos point to measure the distance to check how close
       @param searchResolution resolution per a curve specifying how many points per a curve to be checked
    */
    public static ICurveI findClosestCurve(ICurveI[] railCurves, IVec pos, int searchResolution){
	double minDist=-1;
	int minCurveIdx=-1;
	for(int i=0; i<railCurves.length; i++){
	    for(int j=0; j<=searchResolution; j++){
		double dist = railCurves[i].pt((double)j/searchResolution).dist(pos);
		if(minCurveIdx<0 || dist<minDist){
		    minCurveIdx = i;
		    minDist = dist;
		}
	    }
	}
	if(minCurveIdx<0) return null; // when railCurves.length==0?
	return railCurves[minCurveIdx];
    }
    
    /**
       find the closest curve to the point and put particle on the curve.
       if the curve is not close enough within closenessTolerance, particle is not created.
       @param railCurves target curves to search
       @param pos point to measure the distance to check how close
       @param closenessTolerance tolerance to determine if the point is on the curve or not
       @param uTolerance tolerance in u parameter to specify how precise u parameter of particle shoould be
    */
    public static IParticleOnCurve createParticleOnClosestCurve(ICurveI[] railCurves,
								IVec pos,
								double closenessTolerance,
								double uTolerance){
	final int roughSearchResolution = 20;
	IParticleOnCurve particle = null;
	do{
	    ICurveI closestCrv = null;
	    if(railCurves.length==1) closestCrv = railCurves[0];
	    else closestCrv = findClosestCurve(railCurves, pos, roughSearchResolution);
	    
	    particle = createParticleOnCurve(closestCrv, pos, closenessTolerance, uTolerance);
	    
	    if(particle==null){
		if(railCurves.length>1){
		    ICurveI[] railCurves2 = new ICurveI[railCurves.length-1];
		    for(int i=0, j=0; i<railCurves.length; i++, j++){
			if(railCurves[i]!=closestCrv){ railCurves2[j] = railCurves[i]; }
			else{ j--; }
		    }
		    railCurves = railCurves2;
		}
		else{ return null; } // after checking all curves, there is nothing close enough
	    }
	}while(particle == null && railCurves.length>1);
	
	return particle;
    }
    
    
    /**
       put particle on the curve.
       if the curve is not close enough within closenessTolerance, particle is not created.
       @param curve target curves to be on.
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
