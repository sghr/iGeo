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
import java.awt.Color;

import java.lang.reflect.*;

/**
   Class to create tensile network which consists of IParticle and ITensionLine.
   
   @author Satoru Sugihara
*/
public class ITensileNet{
    
    // physical attributes
    /** friction of node points (of IParticle) */
    public static double friction = 0.005; //0.0;
    
    /** friction of node points (of IParticle) */
    public static void friction(double f){ friction = f; }
    /** alias of frction(double) */
    public static void fric(double f){ friction(f); }
    
    
    /** strength of tension (of ITensionLine) */
    public static double tension = 1.0;
    
    /** strength of tension (of ITensionLine) */
    public static void tension(double t){ tension = t; }
    
    /** strength of tension between points on the same rail curves */
    public static double onRailTension = 1.0;
    
    /** strength of tension between points on the same rail curves */
    public static void onRailTension(double t){ onRailTension = t; }
    
    /** strength of tension to straighten lines */
    public static double straightenerTension = 1.0;
    
    /** strength of tension to straighten lines */
    public static void straightenerTension(double t){ straightenerTension=t; }
    
    /** strength of tension to equalize spacing on the same rail curves */
    public static double equalizerTension = 1.0;
    
    /** strength of tension to equalize spacing on the same rail curves */
    public static void equalizerTension(double t){ equalizerTension = t; }
    
    /** boolean switch to make tension (of ITensionLine) constant not depending on the distance of two points*/
    public static boolean constantTension = false;
    
    /** boolean switch to make tension (of ITensionLine) constant not depending on the distance of two points*/
    public static void constantTension(boolean c){ constantTension = c; }
    public static void enableConstantTension(){ constantTension = true; }
    public static void disableConstantTension(){ constantTension = false; }
    
    
    
    /** tolerance to check which points are identical and which lines are connected */
    public static double tolerance = IConfig.tolerance;
    
    /** tolerance to check which points are identical and which lines are connected */
    public static void tolerance(double t){ tolerance=t; }
    
    /** tolerance to check which points are on rail */
    public static double railTolerance = IConfig.tolerance;
    
    /** tolerance to check which points are identical and which lines are connected */
    public static void railTolerance(double t){ railTolerance=t; }
    
    
    /** color of node points */
    public static IColor pointColor = new IColor(255,255,255);
    /** color of node points */
    public static void pointColor(IColor c){ pointColor = c; }
    /** color of node points */
    public static void pointColor(int r, int g, int b, int a){ pointColor =new IColor(r,g,b,a); }
    /** color of node points */
    public static void pointColor(int r, int g, int b){ pointColor = new IColor(r,g,b); }
    /** color of node points */
    public static void pointColor(int gray, int a){ pointColor = new IColor(gray,a); }
    /** color of node points */
    public static void pointColor(int gray){ pointColor = new IColor(gray); }
    /** color of node points */
    public static void pointColor(float r, float g, float b, float a){ pointColor = new IColor(r,g,b,a); }
    /** color of node points */
    public static void pointColor(float r, float g, float b){ pointColor = new IColor(r,g,b); }
    /** color of node points */
    public static void pointColor(float gray, float a){ pointColor = new IColor(gray,a); }
    /** color of node points */
    public static void pointColor(float gray){ pointColor = new IColor(gray); }
    
    
    /** color of node points on rail curve */
    public static IColor railPointColor = new IColor(192,192,192);
    /** color of node points on rail curve */
    public static void railPointColor(IColor c){ railPointColor = c; }
    /** color of node points on rail curve */
    public static void railPointColor(int r, int g, int b, int a){ railPointColor = new IColor(r,g,b,a); }
    /** color of node points on rail curve */
    public static void railPointColor(int r, int g, int b){ railPointColor = new IColor(r,g,b); }
    /** color of node points on rail curve */
    public static void railPointColor(int gray, int a){ railPointColor = new IColor(gray,a); }
    /** color of node points on rail curve */
    public static void railPointColor(int gray){ railPointColor = new IColor(gray); }
    /** color of node points on rail curve */
    public static void railPointColor(float r, float g, float b, float a){ railPointColor = new IColor(r,g,b,a); }
    /** color of node points on rail curve */
    public static void railPointColor(float r, float g, float b){ railPointColor = new IColor(r,g,b); }
    /** color of node points on rail curve */
    public static void railPointColor(float gray, float a){ railPointColor = new IColor(gray,a); }
    /** color of node points on rail curve */
    public static void railPointColor(float gray){ railPointColor = new IColor(gray); }
    
    
    /** color of fixed node points */
    public static IColor fixedPointColor = new IColor(255,255,0);
    /** color of fixed node points */
    public static void fixedPointColor(IColor c){ fixedPointColor = c; }
    /** color of fixed node points */
    public static void fixedPointColor(int r, int g, int b, int a){ fixedPointColor = new IColor(r,g,b,a); }
    /** color of fixed node points */
    public static void fixedPointColor(int r, int g, int b){ fixedPointColor = new IColor(r,g,b); }
    /** color of fixed node points */
    public static void fixedPointColor(int gray, int a){ fixedPointColor = new IColor(gray,a); }
    /** color of fixed node points */
    public static void fixedPointColor(int gray){ fixedPointColor = new IColor(gray); }
    /** color of fixed node points */
    public static void fixedPointColor(float r, float g, float b, float a){ fixedPointColor = new IColor(r,g,b,a); }
    /** color of fixed node points */
    public static void fixedPointColor(float r, float g, float b){ fixedPointColor = new IColor(r,g,b); }
    /** color of fixed node points */
    public static void fixedPointColor(float gray, float a){ fixedPointColor = new IColor(gray,a); }
    /** color of fixed node points */
    public static void fixedPointColor(float gray){ fixedPointColor = new IColor(gray); }
    
    
    
    /** color of straightener curve */
    public static IColor straightenerColor = new IColor(255,128,0);
    /** color of straightener curve */
    public static void straightenerColor(IColor c){ straightenerColor = c; }
    /** color of straightener curve */
    public static void straightenerColor(int r, int g, int b, int a){ straightenerColor = new IColor(r,g,b,a); }
    /** color of straightener curve */
    public static void straightenerColor(int r, int g, int b){ straightenerColor = new IColor(r,g,b); }
    /** color of straightener curve */
    public static void straightenerColor(int gray, int a){ straightenerColor = new IColor(gray,a); }
    /** color of straightener curve */
    public static void straightenerColor(int gray){ straightenerColor = new IColor(gray); }
    /** color of straightener curve */
    public static void straightenerColor(float r, float g, float b, float a){ straightenerColor = new IColor(r,g,b,a); }
    /** color of straightener curve */
    public static void straightenerColor(float r, float g, float b){ straightenerColor = new IColor(r,g,b); }
    /** color of straightener curve */
    public static void straightenerColor(float gray, float a){ straightenerColor = new IColor(gray,a); }
    /** color of straightener curve */
    public static void straightenerColor(float gray){ straightenerColor = new IColor(gray); }
    
    
    /** color of tension curve; default (null) is the original color of an input geometry */
    public static IColor tensionColor = null;
    /** color of tension curve; default is the original color of an input geometry */
    public static void tensionColor(IColor c){ tensionColor = c; }
    /** color of tension curve; default is the original color of an input geometry */
    public static void tensionColor(int r, int g, int b, int a){ tensionColor = new IColor(r,g,b,a); }
    /** color of tension curve; default is the original color of an input geometry */
    public static void tensionColor(int r, int g, int b){ tensionColor = new IColor(r,g,b); }
    /** color of tension curve; default is the original color of an input geometry */
    public static void tensionColor(int gray, int a){ tensionColor = new IColor(gray,a); }
    /** color of tension curve; default is the original color of an input geometry */
    public static void tensionColor(int gray){ tensionColor = new IColor(gray); }
    /** color of tension curve; default is the original color of an input geometry */
    public static void tensionColor(float r, float g, float b, float a){ tensionColor = new IColor(r,g,b,a); }
    /** color of tension curve; default is the original color of an input geometry */
    public static void tensionColor(float r, float g, float b){ tensionColor = new IColor(r,g,b); }
    /** color of tension curve; default is the original color of an input geometry */
    public static void tensionColor(float gray, float a){ tensionColor = new IColor(gray,a); }
    /** color of tension curve; default is the original color of an input geometry */
    public static void tensionColor(float gray){ tensionColor = new IColor(gray); }
    
    
    /** layer to put node points */
    public static String pointLayer = "nodes";
    /** layer to put node points */
    public static void pointLayer(String l){ pointLayer = l; }
    
    /** layer to put fixed node points */
    public static String fixedPointLayer = "fixedNodes";
    /** layer to put fixed node points */
    public static void fixedPointLayer(String l){ fixedPointLayer = l; }

    /** layer to put straighterner lines */
    public static String straightenerLayer = "straightener";
    /** layer to put straighterner lines */
    public static void straightenerLayer(String l){ straightenerLayer = l; }
    
    /** layer to put equalizer lines */
    public static String equalizerLayer = "equalizer";
    /** layer to put equalizer lines */
    public static void equalizerLayer(String l){ equalizerLayer = l; }
    
    /** layer to put tension lines; default (when null) is to put the original layer of an input geometry  */
    public static String tensionLayer = null;
    /** layer to put tension lines; default (when null) is to put the original layer of an input geometry  */
    public static void tensionLayer(String l){ tensionLayer = l; }
    
    
    // boolean options for create() methods
    /** boolean switch to fix open end points of lines which are not connected any other line */
    //public static boolean fixOpenLinePoint=true;
    public static boolean fixOpenEnd=true;
    /** boolean switch to fix open end points of lines which are not connected any other line */
    public static void fixOpenEnd(boolean f){ fixOpenEnd=f; }
    /** boolean switch to fix open end points of lines which are not connected any other line */
    public static void enableFixOpenEnd(){ fixOpenEnd=true; }
    /** boolean switch to fix open end points of lines which are not connected any other line */
    public static void disableFixOpenEnd(){ fixOpenEnd=false; }
    
    /** boolean switch to delete input lines; if embedTensionInLine is true, this variable is ignored. */
    public static boolean deleteInputLine=true;
    /** boolean switch to delete input lines */
    public static void deleteInputLine(boolean f){ deleteInputLine=f; }
    /** boolean switch to delete input lines */
    public static void enableDeleteInputLine(){ deleteInputLine=true; }
    /** boolean switch to delete input lines */
    public static void disableDeleteInputLine(){ deleteInputLine=false; }
    
    /** if input line is instance of ICurve (not ICurveR), put tension as dynamics inside the curve keeping the original line. this switch precedes deleteInputLine */
    public static boolean embedTensionInLine=true;
    /** if input line is instance of ICurve (not ICurveR), put tension as dynamics inside the curve keeping the original line. this switch precedes deleteInputLine */
    public static void embedTensionInLine(boolean f){ embedTensionInLine=f; }
    /** if input line is instance of ICurve (not ICurveR), put tension as dynamics inside the curve keeping the original line. this switch precedes deleteInputLine */
    public static void enableEmbedTensionInLine(){ embedTensionInLine=true; }
    /** if input line is instance of ICurve (not ICurveR), put tension as dynamics inside the curve keeping the original line. this switch precedes deleteInputLine */
    public static void disableEmbedTensionInLine(){ embedTensionInLine=false; }
    
    
    /** boolean switch to delete input fix points */
    public static boolean deleteInputPoint=true;
    /** boolean switch to delete input fix points */
    public static void deleteInputPoint(boolean f){ deleteInputPoint=f; }
    /** boolean switch to delete input fix points */
    public static void enableDeleteInputPoint(){ deleteInputPoint=true; }
    /** boolean switch to delete input fix points */
    public static void disableDeleteInputPoint(){ deleteInputPoint=false; }
    
    /** in case with rail curves, boolean switch to put tension between points on the same rail curve */
    //public static boolean tensionOnSameRail=true;
    public static boolean tensionOnRail=true;
    /** in case with rail curves, boolean switch to put tension between points on the same rail curve */
    public static void tensionOnRail(boolean f){ tensionOnRail=f; }
    /** in case with rail curves, boolean switch to put tension between points on the same rail curve */
    public static void enableTensionOnRail(){ tensionOnRail=true; }
    /** in case with rail curves, boolean switch to put tension between points on the same rail curve */
    public static void disableTensionOnRail(){ tensionOnRail=false; }
    
    
    /** in case with rail curves, boolean switch to fix points which are on end points of rail curves */
    //public static boolean fixPointOnRailEnd=true;
    public static boolean fixAtRailEnd=true;
    /** in case with rail curves, boolean switch to fix points which are on end points of rail curves */
    public static void fixAtRailEnd(boolean f){ fixAtRailEnd=f; }
    /** in case with rail curves, boolean switch to fix points which are on end points of rail curves */
    public static void enableFixAtRailEnd(){ fixAtRailEnd=true; }
    /** in case with rail curves, boolean switch to fix points which are on end points of rail curves */
    public static void disableFixAtRailEnd(){ fixAtRailEnd=false; }
    
    /** in case with rail curves, boolean switch to fix points which are not on any rail curves */
    //public static boolean fixPointNotOnRail=true;
    public static boolean fixPointOffRail=true;
    /** in case with rail curves, boolean switch to fix points which are not on any rail curves */
    public static void fixPointOffRail(boolean f){ fixPointOffRail=f; }
    /** in case with rail curves, boolean switch to fix points which are not on any rail curves */
    public static void enableFixPointOffRail(boolean f){ fixPointOffRail=true; }
    /** in case with rail curves, boolean switch to fix points which are not on any rail curves */
    public static void disableFixPointOffRail(boolean f){ fixPointOffRail=false; }
    
    
    /** boolean switch to put straightening force on connected lines */
    public static boolean straightener=false; //true;
    /** boolean switch to put straightening force on connected lines */
    public static void straightener(boolean s){ straightener=s; }
    /** boolean switch to put straightening force on connected lines */
    public static void enableStraightener(){ straightener=true; }
    /** boolean switch to put straightening force on connected lines */
    public static void disableStraightener(){ straightener=false; }
    
    /** if the connected line is less than this angle, it put straightening force on those lines */
    public static double straightenerThresholdAngle=Math.PI/3;
    /** if the connected line is less than this angle, it put straightening force on those lines */
    public static void straightenerThresholdAngle(double angle){ straightenerThresholdAngle=angle; }
    
    /** remove straighteners whose two points are shared and branching. */
    public static boolean removeBranchStraightener=true;
    /** remove straighteners whose two points are shared and branching. */
    public static void removeBranchStraightener(boolean f){ removeBranchStraightener=f; }
    /** remove straighteners whose two points are shared and branching. */
    public static void enableRemoveBranchStraightener(){ removeBranchStraightener=true; }
    /** remove straighteners whose two points are shared and branching. */
    public static void disableRemoveBranchStraightener(){ removeBranchStraightener=false; }
    
    
    /** boolean switch to put spacing equalizer force on the same rail curves */
    public static boolean spacingEqualizer=false; // default false
    /** boolean switch to put spacing equalizer force on the same rail curves */
    public static void spacingEqualizer(boolean f){ spacingEqualizer=f; }
    /** boolean switch to put spacing equalizer force on the same rail curves */
    public static void enableSpacingEqualizer(){ spacingEqualizer=true; }
    /** boolean switch to put spacing equalizer force on the same rail curves */
    public static void disableSpacingEqualizer(){ spacingEqualizer=false; }
    
    //public static IParticleI particleClassReference=null;
    //public static ITensionI tensionClassReference=null;
    
    /** class for a custom particle class */
    public static Class<? extends IParticleI> particleClass=null;
    /** class for a custom particle class */
    public static void particleClass(Class<? extends IParticleI> cls){ particleClass = cls; }
    
    /** class for a custom tension class */
    public static Class<? extends ITensionI> tensionClass=null;
    /** class for a custom tension class */
    public static void tensionClass(Class<? extends ITensionI> cls){ tensionClass=cls; }
    
    /** class for a custom particle on curve class */
    public static Class<? extends IParticleOnCurveI> particleOnCurveClass=null;
    /** class for a custom particle on curve class */
    public static void particleOnCurveClass(Class<? extends IParticleOnCurveI> cls){ particleOnCurveClass=cls; }
    
    /** constructor for a custom particle class */
    //public static Constructor<? extends IParticleI> particleConstructor;
    /** constructor for a custom particle class */
    //public static void particleConstructor(Constructor<? extends IParticleI> c){ particleConstructor=c; }
    
    /** parameters of constructor for a custom particle class */
    //public static Class<?>[] particleConstructorParameters;
    /** parameters of constructor for a custom particle class */
    //public static void particleConstructorParameters(Class<?>[] p){ particleConstructorParameters=p; }
    
    /** constructor for a custom tension class */
    //public static Constructor<? extends ITensionI> tensionConstructor;
    /** constructor for a custom tension class */
    //public static void tensionConstructor(Constructor<? extends ITensionI> c){ tensionConstructor=c; }
    /** parameters of constructor for a custom tension class */
    //public static Class<?>[] tensionConstructorParameters;
    /** parameters of constructor for a custom tension class */
    //public static void tensionConstructorParameters(Class<?>[] p){ tensionConstructorParameters=p; }
    
    /** constructor for a custom particle on curve class */
    //public static Constructor<? extends IParticleOnCurveI> particleOnCurveConstructor;
    /** constructor for a custom particle on curve class */
    //public static void particleOnCurveConstructor(Constructor<? extends IParticleOnCurveI> c){ particleOnCurveConstructor=c; }
    /** parameters of constructor for a custom particle on curve class */
    //public static Class<?>[] particleOnCurveConstructorParameters;
    /** parameters of constructor for a custom particle on curve class */
    //public static void particleOnCurveConstructorParameters(Class<?>[] p){ particleOnCurveConstructorParameters=p; }
    
        
    /** if true, it rebuilds mesh before converting into tensile net by removing all duplicated vertices and
	edges. On the other hand, Two other parameter keepDuplicatedMeshEdge and keepDuplicatedMeshVertex
	doesn't change mesh itself and give you control on each of edge and vertex */
    public static boolean removeMeshDuplicates = true;
    public static void removeMeshDuplicates(boolean flag){ removeMeshDuplicates = flag; }
    
    /** for creating tensile net out of polygon mesh; if this is false, duplicated edge on adjacent faces are ignored as tension line (duplicated edges inside the polygon mesh are kept, only tension line is not created internally). */
    public static boolean keepDuplicatedMeshEdge = true; //false;
    /** for creating tensile net out of polygon mesh; if this is false, duplicated edge on adjacent faces are ignored as tension line (duplicated edges inside the polygon mesh are kept, only tension line is not created internally). */
    public static void keepDuplicatedMeshEdge(boolean keep){ keepDuplicatedMeshEdge=keep; }
    
    public static boolean keepDuplicatedMeshVertex = true;
    public static void keepDuplicatedMeshVertex(boolean keep){ keepDuplicatedMeshVertex=keep; }
    
    
    
    
    
    /******************************************************
     * instance members
     ******************************************************/

    
    //public ArrayList<ITensionLine> links;
    public ArrayList<ITensionI> links;
    //public ArrayList<IParticle> nodes;
    public ArrayList<IParticleI> nodes;
    
    public ArrayList<IStraightenerI> straighteners; // option
    
    public ITensileNet(){}
    /* // erasure issue
    public ITensileNet(ArrayList<ITensionLine> links, ArrayList<IParticle> nodes){
	this.links = new ArrayList<ITensionI>();
	this.nodes = new ArrayList<IParticleI>();
	for(int i=0; i<links.size(); i++){ this.links.add(links.get(i)); }
	for(int i=0; i<nodes.size(); i++){ this.nodes.add(nodes.get(i)); }
    }
    */
    public ITensileNet(ArrayList<ITensionI> links, ArrayList<IParticleI> nodes){
	this.links = links; this.nodes = nodes;
    }
    public ITensileNet(ArrayList<ITensionI> links, ArrayList<IParticleI> nodes, ArrayList<IStraightenerI> straighteners){
	this.links = links; this.nodes = nodes; this.straighteners = straighteners;
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
	
	IColor[] lineColors = new IColor[linkLines.length];
	ILayer[] lineLayers = new ILayer[linkLines.length];
	
	IServer server=null;
	for(int i=0; i<linkLines.length; i++){
	    if(linkLines[i] instanceof IObject){
		lineColors[i] = ((IObject)linkLines[i]).clr();
		lineLayers[i] = ((IObject)linkLines[i]).layer();
		if(server==null) server = ((IObject)linkLines[i]).server();
		if(deleteInputLine){ ((IObject)linkLines[i]).del(); }
	    }
	}
	
	if(server==null){ server = IG.current().server(); }
	
	/*
	// find connection and remove duplicates
	for(int i=0; i<endPts.length; i++){
	    IVec[] epts1 = endPts[i];
	    if(epts1[1].eq(epts1[0], tolerance)){ // case of line of length zero
		//epts1[1] = epts1[0];
		endPts[i] = null;  // skip
	    }
	    else{
		for(int j=i+1; j<endPts.length; j++){
		    IVec[] epts2 = endPts[j];
		    for(int k=0; k<2; k++){
			if(epts2[k].eq(epts1[0], tolerance)) epts2[k] = epts1[0];
			else if(epts2[k].eq(epts1[1], tolerance)) epts2[k] = epts1[1];
		    }
		}
	    }
	}
	
	ArrayList<IVec> uniquePts = new ArrayList<IVec>();
	for(int i=0; i<endPts.length; i++){
	    if(endPts[i]!=null){
		for(int j=0; j<2; j++){
		    IVec pt = endPts[i][j];
		    if(!uniquePts.contains(pt)) uniquePts.add(pt);
		}
	    }
	}
	
	if(fixOpenEnd){
	    // cheking naked end point
	    ArrayList<IVecI> openPts = new ArrayList<IVecI>();
	    for(int i=0; i<uniquePts.size(); i++){
		int count=0;
		for(int j=0; j<endPts.length; j++){
		    if(endPts[j]!=null){
			if(endPts[j][0] == uniquePts.get(i)) count++;
			if(endPts[j][1] == uniquePts.get(i)) count++;
		    }
		}
		if(count==1){ openPts.add(uniquePts.get(i)); }
	    }
	    if(fixedPoints!=null){
		for(int i=0; i<fixedPoints.length; i++) openPts.add(fixedPoints[i]);
	    }
	    fixedPoints = openPts.toArray(new IVecI[openPts.size()]);
	}
	
	ConstructorAndParameters<IParticleI> particleConst=null;
	ConstructorAndParameters<ITensionI> tensionConst=null;
	
	// check custom class
	if(particleClass!=null){
	    //searchParticleConstructor(particleClass);
	    particleConst = searchParticleConstructor(particleClass);
	}
	if(tensionClass!=null){
	    //searchTensionConstructor(tensionClass);
	    tensionConst = searchTensionConstructor(tensionClass);
	}

	*/
	
	
	ITensileNet network = null;
	synchronized(server.dynamicServer()){
	    
	    
	    /*
	    //ArrayList<IParticle> particles = new ArrayList<IParticle>();
	    ArrayList<IParticleI> particles = new ArrayList<IParticleI>();
	    for(int i=0; i<uniquePts.size(); i++){
		
		IParticleI ptcl=null;
		// custom particle class instantiation
		//if(particleConstructor!=null && particleConstructorParameters!=null){
		if(particleConst!=null){
		    //ptcl = getParticleInstance(uniquePts.get(i));
		    ptcl = getParticleInstance(uniquePts.get(i),particleConst.constructor,particleConst.parameters);
		    
		    if(ptcl!=null){
			ptcl.fric(friction);
			if(ptcl instanceof IObject){
			    ((IObject)ptcl).clr(pointColor);
			    if(pointLayer!=null) ((IObject)ptcl).layer(IG.layer(pointLayer).clr(pointColor));
			}
			particles.add(ptcl);
		    }
		}
		
		if(ptcl==null){
		    IParticle pa = new IParticle(uniquePts.get(i));
		    pa.fric(friction);
		    pa.clr(pointColor);
		    particles.add(pa);
		    if(pointLayer!=null) pa.layer(IG.layer(pointLayer).clr(pointColor));
		}
	    }
	    
	    IParticleI[][] linkParticles = new IParticleI[endPts.length][2];
	    for(int i=0; i<endPts.length; i++){
		IVec[] epts = endPts[i];
		if(epts!=null){
		    int index1 = uniquePts.indexOf(epts[0]);
		    int index2 = uniquePts.indexOf(epts[1]);
		    if(index1>=0 && index2>=0){
			linkParticles[i][0] = particles.get(index1);
			linkParticles[i][1] = particles.get(index2);
		    }
		}
	    }

	    fixParticles(particles, fixedPoints);
	    */
	    
	    IParticleI[][] linkParticles = new IParticleI[endPts.length][2];
	    ArrayList<IParticleI> particles = createParticleLink(endPts,
								 null,
								 fixedPoints,
								 linkParticles,
								 particleClass);
	    
	    ArrayList<ITensionI> tlines = createTensions(linkParticles,lineColors, lineLayers, tensionClass);
	    ArrayList<IStraightenerI> straighteners=null;
	    if(straightener){ straighteners = createStraighteners(tlines); }
	    
	    // delete point
	    if(fixedPoints!=null){
		for(int i=0; i<fixedPoints.length; i++){
		    if(deleteInputPoint && fixedPoints[i] instanceof IObject){
			((IObject)fixedPoints[i]).del();
		    }
		}
	    }
	    
	    
	    /*
	    // fixing particle
	    if(fixedPoints!=null){
		for(int i=0; i<fixedPoints.length; i++){
		    for(int j=0; j<particles.size(); j++){
			if(fixedPoints[i].eq(particles.get(j), tolerance)){
			    particles.get(j).fix();
			    if(particles.get(j) instanceof IObject){
				IObject obj = (IObject)particles.get(j);
				obj.clr(fixedPointColor);
				if(fixedPointLayer!=null){
				    obj.layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
				}
			    }
			}
		    }
		    if(deleteInputPoint && fixedPoints[i] instanceof IObject){
			((IObject)fixedPoints[i]).del();
		    }
		}
	    }
	    
	    //ArrayList<ITensionLine> tlines = new ArrayList<ITensionLine>();
	    ArrayList<ITensionI> tlines = new ArrayList<ITensionI>();
	    for(int i=0; i<endPts.length; i++){
		IVec[] epts = endPts[i];
		if(epts!=null){
		    int index1 = uniquePts.indexOf(epts[0]);
		    int index2 = uniquePts.indexOf(epts[1]);
		    if(index1>=0 && index2>=0){
			IParticleI pa1 = particles.get(index1);
			IParticleI pa2 = particles.get(index2);
			
			ITensionI tnsn = null;
			// custom tension class instantiation
			//if(tensionConstructor!=null && tensionConstructorParameters!=null){
			if(tensionConst!=null){
			    //tnsn = getTensionInstance(pa1,pa2);
			    tnsn = getTensionInstance(pa1,pa2,tensionConst.constructor,tensionConst.parameters);
			    
			    if(tnsn!=null){
				if(tnsn instanceof IObject){
				    
				    if(tensionColor!=null) ((IObject)tnsn).clr(tensionColor);
				    else if(lineColors[i]!=null) ((IObject)tnsn).clr(lineColors[i]);
				    
				    if(tensionLayer!=null) ((IObject)tnsn).layer(tensionLayer);
				    else if(lineLayer[i]!=null) ((IObject)tnsn).layer(lineLayer[i]);
			    }
				tnsn.tension(tension);
				tnsn.constant(constantTension);
				tlines.add(tnsn);
			    }
			}
			
			if(tnsn==null){
			    ITensionLine tl = new ITensionLine(pa1, pa2, tension);
			    if(tensionColor!=null) tl.clr(tensionColor);
			    else if(lineColors[i]!=null) tl.clr(lineColors[i]);
			    
			    if(tensionLayer!=null) tl.layer(tensionLayer);
			    else if(lineLayer[i]!=null) tl.layer(lineLayer[i]);
			    
			    tl.tension(tension);
			    tl.constant(constantTension);
			    tlines.add(tl);
			}
		    }
		    else{
			IOut.err("end point is not found");
		    }
		}
	    }
	    
	    if(straightener){
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
		
		if(removeBranchStraightener){
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
	    */
	    
	    network = new ITensileNet(tlines, particles, straighteners);
	}
	
	return network;
    }
    
    
    
    /**
       create a network of tension out of a polygon mesh. All the edges are converted into tension member. 
       @param mesh an input mesh whose edges are converted into tension lines and deform the original mesh geometry.
       @param fixedPoints if those points are on the vertices of the mesh, those vertices are fixed.
    */
    public static ITensileNet create(IMeshI mesh, IVecI[] fixedPoints){

	if(removeMeshDuplicates){
	    if(mesh instanceof IMesh){
		((IMesh)mesh).removeDuplicates();
	    }
	    else if(mesh instanceof IMeshGeo){
		((IMeshGeo)mesh).removeDuplicates();
	    }
	}
	
	ArrayList<IEdge> edges = new ArrayList<IEdge>();
	if(keepDuplicatedMeshEdge){ 
	    for(int i=0; i<mesh.edgeNum(); i++){ edges.add(mesh.edge(i)); }
	}
	else{
	    for(int i=0; i<mesh.edgeNum(); i++){
		boolean unique=true;
		for(int j=i+1; j<mesh.edgeNum()&&unique; j++){
		    if(mesh.edge(i).eq(mesh.edge(j),tolerance)) unique=false;
		}
		if(unique) edges.add(mesh.edge(i));
	    }
	}
	
	IVertex[][] edgeVtx = new IVertex[edges.size()][2];
	for(int i=0; i<edges.size(); i++){
	    edgeVtx[i][0] = edges.get(i).vertex(0);
	    edgeVtx[i][1] = edges.get(i).vertex(1);
	}
	
	ArrayList<IVecI> pointList = null;
	if(keepDuplicatedMeshVertex){
	    pointList = new ArrayList<IVecI>();
	    for(int i=0; i<mesh.vertexNum(); i++){ pointList.add(mesh.vertex(i)); }
	}
	
	IServer server=null;
	if(mesh instanceof IObject){ server = ((IObject)mesh).server(); }
	if(server==null){ server = IG.current().server(); }
	
	ITensileNet network=null;
	synchronized(server.dynamicServer()){	
	    
	    IParticleI[][] linkParticles = new IParticleI[edgeVtx.length][2];
	    ArrayList<IParticleI> particles = createParticleLink(edgeVtx, pointList, fixedPoints, linkParticles,
								 particleClass);
	    
	    Class<? extends ITensionI> tnsnClass=tensionClass;
	    if(tnsnClass==null){ tnsnClass = ITension.class; } // no actual line drawn
	    
	    ArrayList<ITensionI> tlines = createTensions(linkParticles, null, null, tnsnClass);
	    ArrayList<IStraightenerI> straighteners = null;
	    if(straightener){ straighteners = createStraighteners(tlines); }
	    
	    // delete point
	    if(fixedPoints!=null){
		for(int i=0; i<fixedPoints.length; i++){
		    if(deleteInputPoint && fixedPoints[i] instanceof IObject){
			((IObject)fixedPoints[i]).del();
		    }
		}
	    }
	    
	    if(keepDuplicatedMeshVertex && mesh.vertexNum()!=particles.size()){
		IOut.err("mesh.vertexNum() and pointList.size() don't match.");
	    }
	    
	    if(keepDuplicatedMeshVertex && mesh.vertexNum()==particles.size() ){
		for(int i=0; i<mesh.vertexNum(); i++){
		    // replace vertex's position instance (usually IVec) with IParticleI instance
		    mesh.vertex(i).pos = particles.get(i);
		    if(mesh instanceof IObject){
			particles.get(i).target((IObject)mesh); // for graphic update
		    }
		}
	    }
	    else{
		for(int i=0; i<mesh.vertexNum(); i++){
		    IVertex vtx = mesh.vertex(i);
		    IParticleI ptcl = null;
		    for(int j=0; j<particles.size()&&ptcl==null; j++){
			if(vtx.eq(particles.get(j),tolerance)) ptcl = particles.get(j);
		    }
		    if(ptcl!=null){
			// replace vertex's position instance (usually IVec) with IParticleI instance
			mesh.vertex(i).pos = ptcl;
			if(mesh instanceof IObject){
			    ptcl.target((IObject)mesh); // for graphic update
			}
			
		    }
		    else{ IOut.err("no particle found for mesh vertex("+i+")"); }
		}
	    }
	    network = new ITensileNet(tlines, particles,straighteners);
	}
	return network;
    }
    
    
    public static void fixParticles(ArrayList<IParticleI> particles, IVecI[] fixedPoints){
	// fixing particle
	if(fixedPoints!=null){
	    for(int i=0; i<fixedPoints.length; i++){
		for(int j=0; j<particles.size(); j++){
		    if(fixedPoints[i].eq(particles.get(j), tolerance)){
			particles.get(j).fix();
			if(particles.get(j) instanceof IObject){
			    IObject obj = (IObject)particles.get(j);
			    obj.clr(fixedPointColor);
			    if(fixedPointLayer!=null){
				obj.layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
			    }
			}
		    }
		}
		//if(deleteInputPoint && fixedPoints[i] instanceof IObject){ ((IObject)fixedPoints[i]).del(); }
	    }
	}
    }
    
    
    public static ArrayList<IParticleI> createParticleLink(IVecI[][] linePts,
							   ArrayList<IVecI> pointList,
							   IVecI[] fixedPoints,
							   IParticleI[][] linkParticlesResult,
							   Class<? extends IParticleI> customParticleClass){

	if(linkParticlesResult==null || linkParticlesResult.length!=linePts.length ||
	   linkParticlesResult.length==0 || linkParticlesResult[0].length!=2){
	    IOut.err("parameter linkParticleResult is return container. it needs to be instantiated as a 2D array whose size is same iwth linePts input parameter");
	    return null;
	}
	
	
	
	if(pointList==null){ // remove all duplicate points in linePts to create a list of unique points
	    
	    // find connection and remove duplicates
	    for(int i=0; i<linePts.length; i++){
		IVecI[] epts1 = linePts[i];
		if(epts1[1].eq(epts1[0], tolerance)){ // case of line of length zero
		    linePts[i] = null;  // skip
		}
		else{
		    for(int j=i+1; j<linePts.length; j++){
			IVecI[] epts2 = linePts[j];
			for(int k=0; k<2; k++){
			    if(epts2[k].eq(epts1[0], tolerance)) epts2[k] = epts1[0];
			    else if(epts2[k].eq(epts1[1], tolerance)) epts2[k] = epts1[1];
			}
		    }
		}
	    }
	    
	    pointList = new ArrayList<IVecI>();
	    for(int i=0; i<linePts.length; i++){
		if(linePts[i]!=null){
		    for(int j=0; j<2; j++){
			if(!pointList.contains(linePts[i][j])) pointList.add(linePts[i][j]);
		    }
		}
	    }
	}
	
	// in case to fix open end points later
	if(fixOpenEnd){
	    ArrayList<IVecI> openPts = new ArrayList<IVecI>();
	    // cheking naked end point
	    for(int i=0; i<pointList.size(); i++){
		int count=0;
		for(int j=0; j<linePts.length; j++){
		    if(linePts[j]!=null){
			if(linePts[j][0] == pointList.get(i)) count++;
			if(linePts[j][1] == pointList.get(i)) count++;
		    }
		}
		if(count==1){ openPts.add(pointList.get(i)); }
	    }
	    if(fixedPoints!=null){
		for(int i=0; i<fixedPoints.length; i++) openPts.add(fixedPoints[i]);
	    }
	    fixedPoints = openPts.toArray(new IVecI[openPts.size()]);
	}
	
	
	ConstructorAndParameters<IParticleI> particleConst=null;
	// check custom class
	if(customParticleClass!=null){
	    particleConst = searchParticleConstructor(particleClass);
	}
	
	
	ArrayList<IParticleI> particles = new ArrayList<IParticleI>();
	
	for(int i=0; i<pointList.size(); i++){
	    
	    IParticleI ptcl=null;
	    // custom particle class instantiation
	    //if(particleConstructor!=null && particleConstructorParameters!=null){
	    if(particleConst!=null){
		ptcl = getParticleInstance(pointList.get(i).get(), /**IVecI -> IVec **/
					   particleConst.constructor,particleConst.parameters);
		if(ptcl!=null){
		    ptcl.fric(friction);
		    if(ptcl instanceof IObject){
			((IObject)ptcl).clr(pointColor);
			if(pointLayer!=null) ((IObject)ptcl).layer(IG.layer(pointLayer).clr(pointColor));
		    }
		}
	    }
	    if(ptcl==null){
		IParticle pa = new IParticle(pointList.get(i));
		pa.fric(friction);
		pa.clr(pointColor);
		if(pointLayer!=null) pa.layer(IG.layer(pointLayer).clr(pointColor));
		ptcl = pa;
	    }
	    particles.add(ptcl);
	}
	
	for(int i=0; i<linePts.length; i++){
	    IVecI[] epts = linePts[i];
	    if(epts!=null){
		int index1 = pointList.indexOf(epts[0]);
		int index2 = pointList.indexOf(epts[1]);
		if(index1>=0 && index2>=0){
		    linkParticlesResult[i][0] = particles.get(index1);
		    linkParticlesResult[i][1] = particles.get(index2);
		}
		else{
		    IOut.err("line points not found in point list (index1="+index1+"), (index2="+index2+")");
		}
	    }
	}

	// fix points
	fixParticles(particles, fixedPoints);
	
	
	return particles;
	
    }
    
    
    public static ArrayList<ITensionI> createTensions(IParticleI[][] links, IColor[] linkColors, ILayer[] linkLayer,
						      Class<? extends ITensionI> customTensionClass){
	ArrayList<ITensionI> tlines = new ArrayList<ITensionI>();
	for(int i=0; i
		<links.length; i++){
	    IParticleI[] epts = links[i];
	    if(epts!=null && epts.length>=2){
		IParticleI pa1 = epts[0];
		IParticleI pa2 = epts[1];
		
		ITensionI tnsn = null;
		
		// custom tension class instantiation
		ConstructorAndParameters<ITensionI> tensionConst = null;
		if(customTensionClass!=null) tensionConst = searchTensionConstructor(customTensionClass);
		
		if(tensionConst!=null){
		    tnsn = getTensionInstance(pa1,pa2,tensionConst.constructor,tensionConst.parameters);
		    if(tnsn!=null){
			if(tnsn instanceof IObject){
			    if(tensionColor!=null) ((IObject)tnsn).clr(tensionColor);
			    else if(linkColors[i]!=null) ((IObject)tnsn).clr(linkColors[i]);
			    
			    if(tensionLayer!=null) ((IObject)tnsn).layer(tensionLayer);
			    else if(linkLayer[i]!=null) ((IObject)tnsn).layer(linkLayer[i]);
			}
			tnsn.tension(tension);
			tnsn.constant(constantTension);
			tlines.add(tnsn);
		    }
		}
		if(tnsn==null){ // when tensionConst==null or instantiation failed
		    ITensionLine tl = new ITensionLine(pa1, pa2, tension);
		    if(tensionColor!=null) tl.clr(tensionColor);
		    else if(linkColors[i]!=null) tl.clr(linkColors[i]);
		    
		    if(tensionLayer!=null) tl.layer(tensionLayer);
		    else if(linkLayer[i]!=null) tl.layer(linkLayer[i]);
		    
		    tl.tension(tension);
		    tl.constant(constantTension);
		    tlines.add(tl);
		}
	    }
	    else{ IOut.err("end point is not found"); }
	}
	
	
	return null;
    }
    
    
    /**
       create straighteners out of tension lines. currently creating IStraightenerCurve (not IStraightener).
    */
    public static ArrayList<IStraightenerI> createStraighteners(ArrayList<ITensionI> tlines){
	
	ArrayList<IStraightenerI> straighteners = new ArrayList<IStraightenerI>();
	
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
	
	if(removeBranchStraightener){
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
			    if(straighteners.get(j) instanceof IObject) ((IObject)straighteners.get(j)).del();
			    straighteners.remove(j);
			    j--;
			}
			else{
			    if(straighteners.get(j) instanceof IObject) ((IObject)straighteners.get(i)).del();
			    straighteners.remove(i);
			    i--;
			    j--;
			    ideleted=true;
			}
		    }
		}
	    }
	}
	return straighteners;
    }
    
    
    
    public static class ConstructorAndParameters<T>{
	public Constructor<? extends T> constructor;
	public Class<?>[] parameters;
	ConstructorAndParameters(Constructor<? extends T> cnst,	Class<?>... params){
	    constructor=cnst; parameters=params;
	    constructor.setAccessible(true); // doing here is OK?
	}
    }
    
    
    
    /** set constructor of particle class. if success returns true */
    @SuppressWarnings("unchecked")
    public static ConstructorAndParameters<IParticleI> searchParticleConstructor(Class<? extends IParticleI> pclass){
	if(pclass==null) return null;
	
	Constructor<? extends IParticleI> constructorIVecIVec1=null;
	Constructor<? extends IParticleI> constructorIVec1=null;
	Constructor<? extends IParticleI> constructorIVecIVec2=null;
	Constructor<? extends IParticleI> constructorIVec2=null;
	Constructor<? extends IParticleI> constructorIVecIVec3=null;
	Constructor<? extends IParticleI> constructorIVec3=null;
	Constructor<? extends IParticleI> constructorNull=null;
	Constructor<? extends IParticleI> constructorObjIVecIVec1=null; // inner class
	Constructor<? extends IParticleI> constructorObjIVec1=null; // inner class
	Constructor<? extends IParticleI> constructorObjIVecIVec2=null; // inner class
	Constructor<? extends IParticleI> constructorObjIVec2=null; // inner class
	Constructor<? extends IParticleI> constructorObjIVecIVec3=null; // inner class
	Constructor<? extends IParticleI> constructorObjIVec3=null; // inner class
	Constructor<? extends IParticleI> constructorObj=null; // inner class
	
	try{
	    Constructor[] constructors = pclass.getDeclaredConstructors();
	    if(constructors==null) return null;
	    
	    for(int i=0; i<constructors.length; i++){
		Class<?>[] param = constructors[i].getParameterTypes();
		
		if(param==null || param.length==0){
		    constructorNull = constructors[i];
		}
		else if(param.length==1 &&
			param[0] == IVecI.class){ 
		    constructorIVec1 = constructors[i]; // first candidate
		}
		else if(param.length==1 &&
			param[0].isAssignableFrom(IVec.class)){
		    constructorIVec2 = constructors[i]; // second candidate
		}
		else if(param.length==1 &&
			IVecI.class.isAssignableFrom(param[0])){
		    constructorIVec3 = constructors[i]; // third candidate
		}
		else if(param.length==2 &&
			param[0] == IVecI.class && 
			param[1] == IVecI.class){ // now specifically IVec, not IVecI
		    constructorIVecIVec1 = constructors[i];
		}
		else if(param.length==2 &&
			param[0].isAssignableFrom(IVec.class) &&
			param[1].isAssignableFrom(IVec.class)){ // now specifically IVec, not IVecI
		    constructorIVecIVec2 = constructors[i];
		}
		else if(param.length==2 &&
			IVecI.class.isAssignableFrom(param[0]) &&
			IVecI.class.isAssignableFrom(param[1])){
		    constructorIVecIVec3 = constructors[i];
		}
		else if(param.length==1 &&
			!IVecI.class.isAssignableFrom(param[0]) ){
		    constructorObj = constructors[i];
		}
		else if(param.length==2 &&
			!IVecI.class.isAssignableFrom(param[0]) &&
			param[1]==IVecI.class){
		    constructorObjIVec1 = constructors[i];
		}
		else if(param.length==2 &&
			!IVecI.class.isAssignableFrom(param[0]) &&
			param[1].isAssignableFrom(IVec.class)){ // now specifically IVec, not IVecI
		    constructorObjIVec2 = constructors[i];
		}
		else if(param.length==2 &&
			!IVecI.class.isAssignableFrom(param[0]) &&
			IVecI.class.isAssignableFrom(param[1])){
		    constructorObjIVec3 = constructors[i];
		}
		else if(param.length==3 &&
			!IVecI.class.isAssignableFrom(param[0]) &&
			//!IVecI.class.isAssignableFrom(param[0]) &&IVecI.class.isAssignableFrom(param[1])&&IVecI.class.isAssignableFrom(param[2])){
			param[1] == IVecI.class &&
			param[2] == IVecI.class){ // now specifically IVec, not IVecI
		    constructorObjIVecIVec1 = constructors[i];
		}
		else if(param.length==3 &&
			!IVecI.class.isAssignableFrom(param[0]) &&
			param[1].isAssignableFrom(IVec.class) &&
			param[2].isAssignableFrom(IVec.class)){ // now specifically IVec, not IVecI
		    constructorObjIVecIVec2 = constructors[i];
		}
		else if(param.length==3 &&
			!IVecI.class.isAssignableFrom(param[0]) &&
			IVecI.class.isAssignableFrom(param[1]) &&
			IVecI.class.isAssignableFrom(param[2])){
		    constructorObjIVecIVec3 = constructors[i];
		}
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    return null;
	}
	
	
	if(constructorIVecIVec1!=null){ // first priority
	    IOut.debug(20, "constructor parameters = { IVecI, IVecI }");
	    return new ConstructorAndParameters<IParticleI>(constructorIVecIVec1,IVecI.class, IVecI.class);
	}
	if(constructorIVec1!=null){ // second priority
	    IOut.debug(20, "constructor parameters = { IVecI }");
	    return new ConstructorAndParameters<IParticleI>(constructorIVec1,IVecI.class);
	}
	if(constructorIVecIVec2!=null){ // first priority
	    IOut.debug(20, "constructor parameters = { IVec, IVec }");
	    return new ConstructorAndParameters<IParticleI>(constructorIVecIVec2,IVecI.class, IVecI.class);
	}
	if(constructorIVec2!=null){ // second priority
	    IOut.debug(20, "constructor parameters = { IVec }");
	    return new ConstructorAndParameters<IParticleI>(constructorIVec2,IVecI.class);
	}
	if(constructorIVecIVec3!=null){ // first priority
	    IOut.debug(20, "constructor parameters = { Class<? extends IVecI>, Class<? extends IVec> }");
	    return new ConstructorAndParameters<IParticleI>(constructorIVecIVec3,IVecI.class, IVecI.class);
	}
	if(constructorIVec3!=null){ // second priority
	    IOut.debug(20, "constructor parameters = { Class<? extends IVec> }");
	    return new ConstructorAndParameters<IParticleI>(constructorIVec3,IVecI.class);
	}
	
	if(constructorNull!=null){ // third priority
	    IOut.debug(20, "constructor parameters = {}");
	    return new ConstructorAndParameters<IParticleI>(constructorNull,new Class<?>[]{});
	}
	
	if(constructorObjIVecIVec1!=null){
	    IOut.debug(20, "constructor parameters = { Object, IVecI, IVecI }");
	    return new ConstructorAndParameters<IParticleI>(constructorObjIVecIVec1,Object.class, IVecI.class, IVecI.class);
	}
	if(constructorObjIVec1!=null){
	    IOut.debug(20, "constructor parameters = { Object, IVecI }");
	    return new ConstructorAndParameters<IParticleI>(constructorObjIVec1, Object.class, IVecI.class );
	}
	if(constructorObjIVecIVec2!=null){
	    IOut.debug(20, "constructor parameters = { Object, IVec, IVec }");
	    return new ConstructorAndParameters<IParticleI>(constructorObjIVecIVec2,Object.class, IVecI.class, IVecI.class);
	}
	if(constructorObjIVec2!=null){
	    IOut.debug(20, "constructor parameters = { Object, IVec }");
	    return new ConstructorAndParameters<IParticleI>(constructorObjIVec2, Object.class, IVecI.class );
	}
	if(constructorObjIVecIVec3!=null){
	    IOut.debug(20, "constructor parameters = { Object, Class<? extends IVecI>, Class<? extends IVecI> }");
	    return new ConstructorAndParameters<IParticleI>(constructorObjIVecIVec3,Object.class, IVecI.class, IVecI.class);
	}
	if(constructorObjIVec3!=null){
	    IOut.debug(20, "constructor parameters = { Object, Class<? extends IVec> }");
	    return new ConstructorAndParameters<IParticleI>(constructorObjIVec3, Object.class, IVecI.class );
	}
	if(constructorObj!=null){
	    IOut.debug(20, "constructor parameters = { Object }");
	    return new ConstructorAndParameters<IParticleI>(constructorObj, Object.class);
	}
	
	IOut.err("no matching particle constructor found. it should be like constructor(IVec) or constructor(IVec,IVec)"); //
	return null;
	

	/*
	Constructor<? extends IParticleI> constructorIVecIVec=null;
	Constructor<? extends IParticleI> constructorIVec=null;
	Constructor<? extends IParticleI> constructorNull=null;
	Constructor<? extends IParticleI> constructorObjIVecIVec=null; // inner class
	Constructor<? extends IParticleI> constructorObjIVec=null; // inner class
	Constructor<? extends IParticleI> constructorObj=null; // inner class
	
	try{
	    Constructor[] constructors = pclass.getDeclaredConstructors();
	    if(constructors==null) return null;
	    
	    for(int i=0; i<constructors.length; i++){
		Class<?>[] param = constructors[i].getParameterTypes();
		
		if(param==null || param.length==0){
		    constructorNull = constructors[i];
		}
		else if(param.length==1 &&
			//IVecI.class.isAssignableFrom(param[0])){
			param[0].isAssignableFrom(IVec.class)){ // now specifically IVec, not IVecI
		    constructorIVec = constructors[i];
		}
		else if(param.length==2 &&
			//IVecI.class.isAssignableFrom(param[0]) && IVecI.class.isAssignableFrom(param[1])){
			param[0].isAssignableFrom(IVec.class) &&
			param[1].isAssignableFrom(IVec.class)){ // now specifically IVec, not IVecI
		    constructorIVecIVec = constructors[i];
		}
		else if(param.length==1 &&
			//!IVecI.class.isAssignableFrom(param[0])){
			!param[0].isAssignableFrom(IVec.class)){ // now specifically IVec, not IVecI
		    constructorObj = constructors[i];
		}
		else if(param.length==2 &&
			//!IVecI.class.isAssignableFrom(param[0]) && IVecI.class.isAssignableFrom(param[1])){
			!param[0].isAssignableFrom(IVec.class) &&
			param[1].isAssignableFrom(IVec.class)){ // now specifically IVec, not IVecI
		    constructorObjIVec = constructors[i];
		}
		else if(param.length==3 &&
			//!IVecI.class.isAssignableFrom(param[0]) &&IVecI.class.isAssignableFrom(param[1])&&IVecI.class.isAssignableFrom(param[2])){
			!param[0].isAssignableFrom(IVec.class) &&
			param[1].isAssignableFrom(IVec.class) &&
			param[2].isAssignableFrom(IVec.class)){ // now specifically IVec, not IVecI
		    constructorObjIVecIVec = constructors[i];
		}
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    return null;
	}
	
	if(constructorIVecIVec!=null){ // first priority
	    IOut.debug(20, "constructor parameters = { IVec, IVec }");
	    return new ConstructorAndParameters<IParticleI>(constructorIVecIVec,IVecI.class, IVecI.class);
	}
	if(constructorIVec!=null){ // second priority
	    IOut.debug(20, "constructor parameters = { IVec }");
	    return new ConstructorAndParameters<IParticleI>(constructorIVec,IVecI.class);
	}
	if(constructorNull!=null){ // third priority
	    IOut.debug(20, "constructor parameters = {}");
	    return new ConstructorAndParameters<IParticleI>(constructorNull,new Class<?>[]{});
	}
	if(constructorObjIVecIVec!=null){
	    IOut.debug(20, "constructor parameters = { Object, IVecI, IVecI }");
	    return new ConstructorAndParameters<IParticleI>(constructorObjIVecIVec,Object.class, IVecI.class, IVecI.class);
	}
	if(constructorObjIVec!=null){
	    IOut.debug(20, "constructor parameters = { Object, IVecI }");
	    return new ConstructorAndParameters<IParticleI>(constructorObjIVec, Object.class, IVecI.class );
	}
	if(constructorObj!=null){
	    IOut.debug(20, "constructor parameters = { Object }");
	    return new ConstructorAndParameters<IParticleI>(constructorObj, Object.class);
	}
	
	IOut.err("no matching particle constructor found. it should be like constructor(IVec) or constructor(IVec,IVec)"); //
	return null;
	*/
    }
    
    
    /** set constructor of particle class. if success returns true */
    /*
    //@SuppressWarnings("unchecked")
    public static boolean setParticleConstructor(Class<? extends IParticleI> pclass){
    
	ConstructorAndParameters<IParticleI> constructor = searchParticleConstructor(pclass);
	if(constructor==null) return false;
	
	particleConstructor = constructor.constructor;
	particleConstructorParameters = constructor.parameters;
	return true;
	
	// ////////////////////////////////////////////////////////////
	if(pclass==null) return false;
	
	Constructor<? extends IParticleI> constructorIVecIVec=null;
	Constructor<? extends IParticleI> constructorIVec=null;
	Constructor<? extends IParticleI> constructorNull=null;
	Constructor<? extends IParticleI> constructorObjIVecIVec=null; // inner class
	Constructor<? extends IParticleI> constructorObjIVec=null; // inner class
	Constructor<? extends IParticleI> constructorObj=null; // inner class
	
	try{
	    Constructor[] constructors = pclass.getDeclaredConstructors();
	    if(constructors==null) return false;
	    
	    for(int i=0; i<constructors.length; i++){
		Class<?>[] param = constructors[i].getParameterTypes();
		
		if(param==null || param.length==0){
		    constructorNull = constructors[i];
		}
		else if(param.length==1 && IVecI.class.isAssignableFrom(param[0])){
		    constructorIVec = constructors[i];
		}
		else if(param.length==2 &&
			IVecI.class.isAssignableFrom(param[0]) &&
			IVecI.class.isAssignableFrom(param[1])){
		    constructorIVecIVec = constructors[i];
		}
		else if(param.length==1 && !IVecI.class.isAssignableFrom(param[0])){
		    constructorObj = constructors[i];
		}
		else if(param.length==2 &&
			!IVecI.class.isAssignableFrom(param[0]) &&
			IVecI.class.isAssignableFrom(param[1])){
		    constructorObjIVec = constructors[i];
		}
		else if(param.length==3 &&
			!IVecI.class.isAssignableFrom(param[0]) &&
			IVecI.class.isAssignableFrom(param[1])&&
			IVecI.class.isAssignableFrom(param[2])){
		    constructorObjIVecIVec = constructors[i];
		}
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    return false;
	}
	
	if(constructorIVecIVec!=null){ // first priority
	    particleConstructor = constructorIVecIVec;
	    particleConstructor.setAccessible(true); // always ok?
	    particleConstructorParameters = new Class<?>[]{ IVecI.class, IVecI.class };
	    return true;
	}
	if(constructorIVec!=null){ // second priority
	    particleConstructor = constructorIVec;
	    particleConstructor.setAccessible(true); // always ok?
	    particleConstructorParameters = new Class<?>[]{ IVecI.class };
	    return true;
	}
	if(constructorNull!=null){ // third priority
	    particleConstructor = constructorNull;
	    particleConstructor.setAccessible(true); // always ok?
	    particleConstructorParameters = new Class<?>[]{};
	    return true;
	}
	
	if(constructorObjIVecIVec!=null){ 
	    particleConstructor = constructorObjIVecIVec;
	    particleConstructor.setAccessible(true); // always ok?
	    particleConstructorParameters = new Class<?>[]{ Object.class, IVecI.class, IVecI.class };
	    return true;
	}
	if(constructorObjIVec!=null){ 
	    particleConstructor = constructorObjIVec;
	    particleConstructor.setAccessible(true); // always ok?
	    particleConstructorParameters = new Class<?>[]{ Object.class, IVecI.class };
	    return true;
	}
	if(constructorObj!=null){ 
	    particleConstructor = constructorObj;
	    particleConstructor.setAccessible(true); // always ok?
	    particleConstructorParameters = new Class<?>[]{ Object.class };
	    return true;
	}
	
	IOut.err("no matching particle constructor found. it should be like constructor(IVec) or constructor(IVec,IVec)"); //
	
	return false;
    }
    */
    
    /** set constructor of tension class. if success returns true */
    @SuppressWarnings("unchecked")
    public static ConstructorAndParameters<ITensionI> searchTensionConstructor(Class<? extends ITensionI> tclass){
	if(tclass==null) return null;
	
	Constructor<? extends ITensionI> constructorIParticleIParticle1=null;
	Constructor<? extends ITensionI> constructorIParticleIParticleDouble1=null;
	Constructor<? extends ITensionI> constructorObjIParticleIParticle1=null;
	Constructor<? extends ITensionI> constructorObjIParticleIParticleDouble1=null;
	Constructor<? extends ITensionI> constructorIParticleIParticle2=null;
	Constructor<? extends ITensionI> constructorIParticleIParticleDouble2=null;
	Constructor<? extends ITensionI> constructorObjIParticleIParticle2=null;
	Constructor<? extends ITensionI> constructorObjIParticleIParticleDouble2=null;
	
	try{
	    Constructor[] constructors = tclass.getDeclaredConstructors();
	    if(constructors==null) return null;
	    
	    for(int i=0; i<constructors.length; i++){
		Class<?>[] param = constructors[i].getParameterTypes();
		if(param.length==2 &&
		   param[0] == IParticleI.class &&
		   param[1] == IParticleI.class){
		    constructorIParticleIParticle1 = constructors[i];
		}
		else if(param.length==2 &&
			IParticleI.class.isAssignableFrom(param[0]) &&
			IParticleI.class.isAssignableFrom(param[1])){
		    constructorIParticleIParticle2 = constructors[i];
		}
		else if(param.length==3 &&
			param[0] == IParticleI.class &&
			param[1] == IParticleI.class &&
			param[2] == double.class){
		    constructorIParticleIParticleDouble1 = constructors[i];
		}
		else if(param.length==3 &&
			IParticleI.class.isAssignableFrom(param[0]) &&
			IParticleI.class.isAssignableFrom(param[1]) &&
			double.class.isAssignableFrom(param[2])){
		    constructorIParticleIParticleDouble2 = constructors[i];
		}
		else if(param.length==3 &&
			!IParticleI.class.isAssignableFrom(param[0]) &&
			param[1] == IParticleI.class &&
			param[2] == IParticleI.class){
		    constructorObjIParticleIParticle1 = constructors[i];
		}
		else if(param.length==3 &&
			!IParticleI.class.isAssignableFrom(param[0]) &&
			IParticleI.class.isAssignableFrom(param[1]) &&
			IParticleI.class.isAssignableFrom(param[2])){
		    constructorObjIParticleIParticle2 = constructors[i];
		}
		else if(param.length==4 &&
			!IParticleI.class.isAssignableFrom(param[0]) &&
			param[1] == IParticleI.class &&
			param[2] == IParticleI.class &&
			param[3] == double.class){
		    constructorObjIParticleIParticleDouble1 = constructors[i];
		}
		else if(param.length==4 &&
			!IParticleI.class.isAssignableFrom(param[0]) &&
			IParticleI.class.isAssignableFrom(param[1]) &&
			IParticleI.class.isAssignableFrom(param[2]) &&
			double.class.isAssignableFrom(param[3])){
		    constructorObjIParticleIParticleDouble2 = constructors[i];
		}
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    return null;
	}
	
	if(constructorIParticleIParticleDouble1!=null){ // first priority
	    return new ConstructorAndParameters<ITensionI>(constructorIParticleIParticleDouble1,
							   IParticleI.class, IParticleI.class, double.class);
	}
	if(constructorIParticleIParticle1!=null){ // second priority
	    return new ConstructorAndParameters<ITensionI>(constructorIParticleIParticle1,
							   IParticleI.class, IParticleI.class);
	}
	if(constructorIParticleIParticleDouble2!=null){ // first priority
	    return new ConstructorAndParameters<ITensionI>(constructorIParticleIParticleDouble2,
							   IParticleI.class, IParticleI.class, double.class);
	}
	if(constructorIParticleIParticle2!=null){ // second priority
	    return new ConstructorAndParameters<ITensionI>(constructorIParticleIParticle2,
							   IParticleI.class, IParticleI.class);
	}
	if(constructorObjIParticleIParticleDouble1!=null){
	    return new ConstructorAndParameters<ITensionI>(constructorObjIParticleIParticleDouble1,
							   Object.class, IParticleI.class, IParticleI.class, double.class);
	}
	if(constructorObjIParticleIParticle1!=null){
	    return new ConstructorAndParameters<ITensionI>(constructorObjIParticleIParticle1,
							   Object.class, IParticleI.class, IParticleI.class);
	}
	if(constructorObjIParticleIParticleDouble2!=null){
	    return new ConstructorAndParameters<ITensionI>(constructorObjIParticleIParticleDouble2,
							   Object.class, IParticleI.class, IParticleI.class, double.class);
	}
	if(constructorObjIParticleIParticle2!=null){
	    return new ConstructorAndParameters<ITensionI>(constructorObjIParticleIParticle2,
							   Object.class, IParticleI.class, IParticleI.class);
	}
	
	IOut.err("no matching tension constructor found. it should be like constructor(IParticleI,IParticleI,double) or constructor(IParticleI,IParticleI)");
	
	return null;
	
	/*
	Constructor<? extends ITensionI> constructorIParticleIParticle=null;
	Constructor<? extends ITensionI> constructorIParticleIParticleDouble=null;
	Constructor<? extends ITensionI> constructorObjIParticleIParticle=null;
	Constructor<? extends ITensionI> constructorObjIParticleIParticleDouble=null;
	
	try{
	    Constructor[] constructors = tclass.getDeclaredConstructors();
	    if(constructors==null) return null;
	    
	    for(int i=0; i<constructors.length; i++){
		Class<?>[] param = constructors[i].getParameterTypes();
		if(param.length==2 &&
		   //IParticleI.class.isAssignableFrom(param[0]) && IParticleI.class.isAssignableFrom(param[1])){
		   param[0].isAssignableFrom(IParticleI.class) &&
		   param[1].isAssignableFrom(IParticleI.class)){
		    constructorIParticleIParticle = constructors[i];
		}
		else if(param.length==3 &&
			//IParticleI.class.isAssignableFrom(param[0]) && IParticleI.class.isAssignableFrom(param[1]) && double.class.isAssignableFrom(param[2])){
			param[0].isAssignableFrom(IParticleI.class) &&
			param[1].isAssignableFrom(IParticleI.class) &&
			param[2].isAssignableFrom(double.class)){
		    constructorIParticleIParticleDouble = constructors[i];
		}
		else if(param.length==3 &&
			//!IParticleI.class.isAssignableFrom(param[0]) &&IParticleI.class.isAssignableFrom(param[1]) &&IParticleI.class.isAssignableFrom(param[2])){
			!param[0].isAssignableFrom(IParticleI.class) &&
			param[1].isAssignableFrom(IParticleI.class) &&
			param[2].isAssignableFrom(IParticleI.class)){
		    constructorObjIParticleIParticle = constructors[i];
		}
		else if(param.length==4 &&
			//!IParticleI.class.isAssignableFrom(param[0]) &&IParticleI.class.isAssignableFrom(param[1]) &&IParticleI.class.isAssignableFrom(param[2]) &&double.class.isAssignableFrom(param[3])){
			!param[0].isAssignableFrom(IParticleI.class) &&
			param[1].isAssignableFrom(IParticleI.class) &&
			param[2].isAssignableFrom(IParticleI.class) &&
			param[3].isAssignableFrom(double.class)){
		    constructorObjIParticleIParticleDouble = constructors[i];
		}
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    return null;
	}
	
	if(constructorIParticleIParticleDouble!=null){ // first priority
	    return new ConstructorAndParameters<ITensionI>(constructorIParticleIParticleDouble,
							   IParticleI.class, IParticleI.class, double.class);
	}
	if(constructorIParticleIParticle!=null){ // second priority
	    return new ConstructorAndParameters<ITensionI>(constructorIParticleIParticle,
							   IParticleI.class, IParticleI.class);
	}
	if(constructorObjIParticleIParticleDouble!=null){
	    return new ConstructorAndParameters<ITensionI>(constructorObjIParticleIParticleDouble,
							   Object.class, IParticleI.class, IParticleI.class, double.class);
	}
	if(constructorObjIParticleIParticle!=null){
	    return new ConstructorAndParameters<ITensionI>(constructorObjIParticleIParticle,
							   Object.class, IParticleI.class, IParticleI.class);
	}
	
	IOut.err("no matching tension constructor found. it should be like constructor(IParticleI,IParticleI,double) or constructor(IParticleI,IParticleI)");
	
	return null;
	*/
    }
    
    
    
    /** set constructor of tension class. if success returns true */
    /*
    //@SuppressWarnings("unchecked")
    public static boolean setTensionConstructor(Class<? extends ITensionI> tclass){

	ConstructorAndParameters<ITensionI> constructor = searchTensionConstructor(tclass);
	if(constructor==null) return false;
	tensionConstructor = constructor.constructor;
	tensionConstructorParameters = constructor.parameters;
	return true;
	
	// /////////////////////////////////////
	if(tclass==null) return false;
	
	Constructor<? extends ITensionI> constructorIParticleIParticle=null;
	Constructor<? extends ITensionI> constructorIParticleIParticleDouble=null;
	Constructor<? extends ITensionI> constructorObjIParticleIParticle=null;
	Constructor<? extends ITensionI> constructorObjIParticleIParticleDouble=null;
	
	try{
	    Constructor[] constructors = tclass.getDeclaredConstructors();
	    if(constructors==null) return false;
	    
	    for(int i=0; i<constructors.length; i++){
		Class<?>[] param = constructors[i].getParameterTypes();
		if(param.length==2 &&
		   IParticleI.class.isAssignableFrom(param[0]) &&
		   IParticleI.class.isAssignableFrom(param[1])){
		    constructorIParticleIParticle = constructors[i];
		}
		else if(param.length==3 &&
			IParticleI.class.isAssignableFrom(param[0]) &&
			IParticleI.class.isAssignableFrom(param[1]) &&
			double.class.isAssignableFrom(param[2])){
		    constructorIParticleIParticleDouble = constructors[i];
		}
		else if(param.length==3 &&
			!IParticleI.class.isAssignableFrom(param[0]) &&
			IParticleI.class.isAssignableFrom(param[1]) &&
			IParticleI.class.isAssignableFrom(param[2])){
		    constructorObjIParticleIParticle = constructors[i];
		}
		else if(param.length==4 &&
			!IParticleI.class.isAssignableFrom(param[0]) &&
			IParticleI.class.isAssignableFrom(param[1]) &&
			IParticleI.class.isAssignableFrom(param[2]) &&
			double.class.isAssignableFrom(param[3])){
		    constructorObjIParticleIParticleDouble = constructors[i];
		}
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    return false;
	}
	
	if(constructorIParticleIParticleDouble!=null){ // first priority
	    tensionConstructor = constructorIParticleIParticleDouble;
	    tensionConstructor.setAccessible(true); // always ok?
	    tensionConstructorParameters = new Class<?>[]{ IParticleI.class, IParticleI.class, double.class };
	    return true;
	}
	if(constructorIParticleIParticle!=null){ // second priority
	    tensionConstructor = constructorIParticleIParticle;
	    tensionConstructor.setAccessible(true); // always ok?
	    tensionConstructorParameters = new Class<?>[]{ IParticleI.class, IParticleI.class};
	    return true;
	}
	
	if(constructorObjIParticleIParticleDouble!=null){
	    tensionConstructor = constructorObjIParticleIParticleDouble;
	    tensionConstructor.setAccessible(true); // always ok?
	    tensionConstructorParameters = new Class<?>[]{ Object.class, IParticleI.class, IParticleI.class, double.class };
	    return true;
	}
	if(constructorObjIParticleIParticle!=null){ 
	    tensionConstructor = constructorObjIParticleIParticle;
	    tensionConstructor.setAccessible(true); // always ok?
	    tensionConstructorParameters = new Class<?>[]{ Object.class, IParticleI.class, IParticleI.class};
	    return true;
	}
	
	IOut.err("no matching tension constructor found. it should be like constructor(IParticleI,IParticleI,double) or constructor(IParticleI,IParticleI)");
	
	return false;
    }
    */
    
    
    /** set constructor of particle class. if success returns true */
    @SuppressWarnings("unchecked")
    public static ConstructorAndParameters<IParticleOnCurveI> searchParticleOnCurveConstructor(Class<? extends IParticleOnCurveI> pclass){
	
	if(pclass==null) return null;
	
	Constructor<? extends IParticleOnCurveI> constructorICurveDouble1=null;
	Constructor<? extends IParticleOnCurveI> constructorICurveDoubleDouble1=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDouble1=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDoubleDouble1=null;
	Constructor<? extends IParticleOnCurveI> constructorICurveDoubleIVec1=null;
	Constructor<? extends IParticleOnCurveI> constructorICurveDoubleDoubleIVec1=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDoubleIVec1=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDoubleDoubleIVec1=null;
	Constructor<? extends IParticleOnCurveI> constructorICurveDouble2=null;
	Constructor<? extends IParticleOnCurveI> constructorICurveDoubleDouble2=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDouble2=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDoubleDouble2=null;
	Constructor<? extends IParticleOnCurveI> constructorICurveDoubleIVec2=null;
	Constructor<? extends IParticleOnCurveI> constructorICurveDoubleDoubleIVec2=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDoubleIVec2=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDoubleDoubleIVec2=null;
	
	try{
	    Constructor[] constructors = pclass.getDeclaredConstructors();
	    if(constructors==null) return null;
	    
	    for(int i=0; i<constructors.length; i++){
		Class<?>[] param = constructors[i].getParameterTypes();
		if(param.length==2 &&
		   param[0]==ICurveI.class &&
		   param[1].isAssignableFrom(double.class) ){
		    constructorICurveDouble1 = constructors[i];
		}
		else if(param.length==2 &&
			ICurveI.class.isAssignableFrom(param[0]) &&
			param[1].isAssignableFrom(double.class) ){
		    constructorICurveDouble2 = constructors[i];
		}
		else if(param.length==3 &&
			param[0] == ICurveI.class &&
			param[1].isAssignableFrom(double.class) &&
			param[2].isAssignableFrom(double.class) ){
		    constructorICurveDoubleDouble1 = constructors[i];
		}
		else if(param.length==3 &&
			ICurveI.class.isAssignableFrom(param[0]) &&
			param[1].isAssignableFrom(double.class) &&
			param[2].isAssignableFrom(double.class) ){
		    constructorICurveDoubleDouble2 = constructors[i];
		}
		else if(param.length==3 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			param[1]==ICurveI.class &&
			param[2].isAssignableFrom(double.class) ){
		    constructorObjICurveDouble1 = constructors[i];
		}
		else if(param.length==3 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			ICurveI.class.isAssignableFrom(param[1]) &&
			param[2].isAssignableFrom(double.class) ){
		    constructorObjICurveDouble2 = constructors[i];
		}
		else if(param.length==4 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			param[1]==ICurveI.class &&
			param[2].isAssignableFrom(double.class) &&
			param[3].isAssignableFrom(double.class) ){
		    constructorObjICurveDoubleDouble1 = constructors[i];
		}
		else if(param.length==4 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			ICurveI.class.isAssignableFrom(param[1]) &&
			param[2].isAssignableFrom(double.class) &&
			param[3].isAssignableFrom(double.class) ){
		    constructorObjICurveDoubleDouble2 = constructors[i];
		}
		else if(param.length==3 &&
			param[0]==ICurveI.class &&
			param[1].isAssignableFrom(double.class) &&
			param[2].isAssignableFrom(IVec.class) ){
		    constructorICurveDoubleIVec1 = constructors[i];
		}
		else if(param.length==3 &&
			ICurveI.class.isAssignableFrom(param[0]) &&
			param[1].isAssignableFrom(double.class) &&
			param[2].isAssignableFrom(IVec.class) ){
		    constructorICurveDoubleIVec2 = constructors[i];
		}
		else if(param.length==4 &&
			param[0] == ICurveI.class &&
			param[1].isAssignableFrom(double.class) &&
			param[2].isAssignableFrom(double.class) &&
			param[3].isAssignableFrom(IVec.class) ){
		    constructorICurveDoubleDoubleIVec1 = constructors[i];
		}
		else if(param.length==4 &&
			ICurveI.class.isAssignableFrom(param[0]) && 
			param[1].isAssignableFrom(double.class) &&
			param[2].isAssignableFrom(double.class) &&
			param[3].isAssignableFrom(IVec.class) ){
		    constructorICurveDoubleDoubleIVec2 = constructors[i];
		}
		else if(param.length==4 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			param[1] == ICurveI.class &&
			param[2].isAssignableFrom(double.class) &&
			param[3].isAssignableFrom(IVec.class) ){
		    constructorObjICurveDoubleIVec1 = constructors[i];
		}
		else if(param.length==4 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			ICurveI.class.isAssignableFrom(param[1]) &&
			param[2].isAssignableFrom(double.class) &&
			param[3].isAssignableFrom(IVec.class) ){
		    constructorObjICurveDoubleIVec2 = constructors[i];
		}
		else if(param.length==5 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			param[1] == ICurveI.class &&
			param[2].isAssignableFrom(double.class) &&
			param[3].isAssignableFrom(double.class) &&
			param[4].isAssignableFrom(IVec.class) ){
		    constructorObjICurveDoubleDoubleIVec1 = constructors[i];
		}
		else if(param.length==5 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			ICurveI.class.isAssignableFrom(param[1]) &&
			param[2].isAssignableFrom(double.class) &&
			param[3].isAssignableFrom(double.class) &&
			param[4].isAssignableFrom(IVec.class) ){
		    constructorObjICurveDoubleDoubleIVec2 = constructors[i];
		}
		
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    return null;
	}
	
	if(constructorICurveDoubleDoubleIVec1!=null){ // first priority
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorICurveDoubleDoubleIVec1,
								   ICurveI.class, double.class, double.class, IVec.class );
	}
	if(constructorICurveDoubleIVec1!=null){ // second priority
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorICurveDoubleIVec1,
								   ICurveI.class, double.class, IVec.class);
	}
	if(constructorICurveDouble1!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorICurveDouble1,
								   ICurveI.class, double.class);
	}
	if(constructorICurveDoubleDouble1!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorICurveDoubleDouble1,
								   ICurveI.class, double.class, double.class);
	}
	if(constructorICurveDoubleDoubleIVec2!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorICurveDoubleDoubleIVec2,
								   ICurveI.class, double.class, double.class, IVec.class );
	}
	if(constructorICurveDoubleIVec2!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorICurveDoubleIVec2,
								   ICurveI.class, double.class, IVec.class);
	}
	if(constructorICurveDouble2!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorICurveDouble2,
								   ICurveI.class, double.class);
	}
	if(constructorICurveDoubleDouble2!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorICurveDoubleDouble2,
								   ICurveI.class, double.class, double.class);
	}
	
	if(constructorObjICurveDoubleDouble1!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorObjICurveDoubleDouble1,
								   Object.class, ICurveI.class, double.class, double.class );
	}
	if(constructorObjICurveDoubleDoubleIVec1!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorObjICurveDoubleDoubleIVec1,
								   Object.class, ICurveI.class, double.class, double.class, IVec.class);
	}
	if(constructorObjICurveDoubleIVec1!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorObjICurveDoubleIVec1,
								   Object.class, ICurveI.class, double.class, IVec.class);
	}
	if(constructorObjICurveDouble1!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorObjICurveDouble1,
								   Object.class, ICurveI.class, double.class);
	}
	if(constructorObjICurveDoubleDouble2!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorObjICurveDoubleDouble2,
								   Object.class, ICurveI.class, double.class, double.class );
	}
	if(constructorObjICurveDoubleDoubleIVec2!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorObjICurveDoubleDoubleIVec2,
								   Object.class, ICurveI.class, double.class, double.class, IVec.class);
	}
	if(constructorObjICurveDoubleIVec2!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorObjICurveDoubleIVec2,
								   Object.class, ICurveI.class, double.class, IVec.class);
	}
	if(constructorObjICurveDouble2!=null){
	    return new ConstructorAndParameters<IParticleOnCurveI>(constructorObjICurveDouble2,
								   Object.class, ICurveI.class, double.class);
	}
	
	IOut.err("no matching particle on curve constructor found. it should be like constructor(ICurve,double) or constructor(ICurve,double,double)"); //
	return null;
    }
    

    /** set constructor of particle class. if success returns true */
    /*
    //@SuppressWarnings("unchecked")
    public static boolean setParticleOnCurveConstructor(Class<? extends IParticleOnCurveI> pclass){

	ConstructorAndParameters<IParticleOnCurveI> constructor = searchParticleOnCurveConstructor(pclass);
	if(constructor==null) return false;
	
	particleOnCurveConstructor = constructor.constructor;
	particleOnCurveConstructorParameters = constructor.parameters;
	return true;
	
	// //////////////////////////////////////
	if(pclass==null) return false;
	
	Constructor<? extends IParticleOnCurveI> constructorICurveDouble=null;
	Constructor<? extends IParticleOnCurveI> constructorICurveDoubleDouble=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDouble=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDoubleDouble=null;
	Constructor<? extends IParticleOnCurveI> constructorICurveDoubleIVec=null;
	Constructor<? extends IParticleOnCurveI> constructorICurveDoubleDoubleIVec=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDoubleIVec=null;
	Constructor<? extends IParticleOnCurveI> constructorObjICurveDoubleDoubleIVec=null;
	
	try{
	    Constructor[] constructors = pclass.getDeclaredConstructors();
	    if(constructors==null) return false;
	    
	    for(int i=0; i<constructors.length; i++){
		Class<?>[] param = constructors[i].getParameterTypes();
		if(param.length==2 &&
		   ICurveI.class.isAssignableFrom(param[0]) &&
		   double.class.isAssignableFrom(param[1]) ){
		    constructorICurveDouble = constructors[i];
		}
		else if(param.length==3 &&
			ICurveI.class.isAssignableFrom(param[0]) &&
			double.class.isAssignableFrom(param[1]) &&
			double.class.isAssignableFrom(param[2]) ){
		    constructorICurveDoubleDouble = constructors[i];
		}
		else if(param.length==3 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			ICurveI.class.isAssignableFrom(param[1]) &&
			double.class.isAssignableFrom(param[2]) ){
		    constructorObjICurveDouble = constructors[i];
		}
		else if(param.length==4 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			ICurveI.class.isAssignableFrom(param[1]) &&
			double.class.isAssignableFrom(param[2]) &&
			double.class.isAssignableFrom(param[3]) ){
		    constructorObjICurveDoubleDouble = constructors[i];
		}
		else if(param.length==3 &&
			ICurveI.class.isAssignableFrom(param[0]) &&
			double.class.isAssignableFrom(param[1]) &&
			IVec.class.isAssignableFrom(param[2]) ){
		    constructorICurveDoubleIVec = constructors[i];
		}
		else if(param.length==4 &&
			ICurveI.class.isAssignableFrom(param[0]) &&
			double.class.isAssignableFrom(param[1]) &&
			double.class.isAssignableFrom(param[2]) &&
			double.class.isAssignableFrom(param[3]) ){
		    constructorICurveDoubleDoubleIVec = constructors[i];
		}
		else if(param.length==4 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			ICurveI.class.isAssignableFrom(param[1]) &&
			double.class.isAssignableFrom(param[2]) &&
			double.class.isAssignableFrom(param[3]) ){
		    constructorObjICurveDoubleIVec = constructors[i];
		}
		else if(param.length==5 &&
			!ICurveI.class.isAssignableFrom(param[0]) &&
			ICurveI.class.isAssignableFrom(param[1]) &&
			double.class.isAssignableFrom(param[2]) &&
			double.class.isAssignableFrom(param[3]) &&
			double.class.isAssignableFrom(param[4]) ){
		    constructorObjICurveDoubleDoubleIVec = constructors[i];
		}
		
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    return false;
	}
	
	if(constructorICurveDoubleDoubleIVec!=null){ // first priority
	    particleOnCurveConstructor = constructorICurveDoubleDoubleIVec;
	    particleOnCurveConstructor.setAccessible(true); // always ok?
	    particleOnCurveConstructorParameters = new Class<?>[]{ ICurveI.class, double.class, double.class, IVec.class };
	    return true;
	}
	if(constructorICurveDoubleIVec!=null){ // second priority
	    particleOnCurveConstructor = constructorICurveDoubleIVec;
	    particleOnCurveConstructor.setAccessible(true); // always ok?
	    particleOnCurveConstructorParameters = new Class<?>[]{ ICurveI.class, double.class, IVec.class };
	    return true;
	}
	if(constructorObjICurveDoubleDoubleIVec!=null){
	    particleOnCurveConstructor = constructorObjICurveDoubleDoubleIVec;
	    particleOnCurveConstructor.setAccessible(true); // always ok?
	    particleOnCurveConstructorParameters = new Class<?>[]{ Object.class, ICurveI.class, double.class, double.class, IVec.class };
	    return true;
	}
	if(constructorObjICurveDoubleIVec!=null){ 
	    particleOnCurveConstructor = constructorObjICurveDoubleIVec;
	    particleOnCurveConstructor.setAccessible(true); // always ok?
	    particleOnCurveConstructorParameters = new Class<?>[]{ Object.class, ICurveI.class, double.class, IVec.class };
	    return true;
	}
	
	if(constructorICurveDoubleDouble!=null){
	    particleOnCurveConstructor = constructorICurveDoubleDouble;
	    particleOnCurveConstructor.setAccessible(true); // always ok?
	    particleOnCurveConstructorParameters = new Class<?>[]{ ICurveI.class, double.class, double.class };
	    return true;
	}
	if(constructorICurveDouble!=null){
	    particleConstructor = constructorICurveDouble;
	    particleConstructor.setAccessible(true); // always ok?
	    particleConstructorParameters = new Class<?>[]{ ICurveI.class, double.class };
	    return true;
	}
	if(constructorObjICurveDoubleDouble!=null){
	    particleOnCurveConstructor = constructorObjICurveDoubleDouble;
	    particleOnCurveConstructor.setAccessible(true); // always ok?
	    particleOnCurveConstructorParameters = new Class<?>[]{ Object.class, ICurveI.class, double.class, double.class };
	    return true;
	}
	if(constructorObjICurveDouble!=null){ 
	    particleOnCurveConstructor = constructorObjICurveDouble;
	    particleOnCurveConstructor.setAccessible(true); // always ok?
	    particleOnCurveConstructorParameters = new Class<?>[]{ Object.class, ICurveI.class, double.class };
	    return true;
	}
	
	IOut.err("no matching particle on curve constructor found. it should be like constructor(ICurve,double) or constructor(ICurve,double,double)"); //
	
	return false;
    }
    */
    
    /*
    public static IParticleI getParticleInstance(IVec pt){
	return getParticleInstance(pt, particleConstructor, particleConstructorParameters);
	// //////////////////////////////////////
	IParticleI ptcl=null;
	try{
	    if(particleConstructorParameters.length==0){
		ptcl =  particleConstructor.newInstance();
		ptcl.pos().set(pt); // would this always work?
	    }
	    else if(particleConstructorParameters.length==1 &&
		    particleConstructorParameters[0] == IVecI.class){
		ptcl =  particleConstructor.newInstance(pt);
	    }
	    else if(particleConstructorParameters.length==2 &&
		    particleConstructorParameters[0] == IVecI.class){
		// second parameter is velocity (zero)
		ptcl =  particleConstructor.newInstance(pt,new IVec());
	    }
	    else if(particleConstructorParameters.length==1 &&
		    particleConstructorParameters[0] == Object.class){
		ptcl =  particleConstructor.newInstance((Object)null);
		ptcl.pos().set(pt); // would this always work?
	    }
	    else if(particleConstructorParameters.length==2 &&
		    particleConstructorParameters[0] == Object.class){
		ptcl =  particleConstructor.newInstance(null, pt);
	    }
	    else if(particleConstructorParameters.length==3 &&
		    particleConstructorParameters[0] == Object.class){
		ptcl =  particleConstructor.newInstance(null, pt, new IVec());
	    }
	}catch(Exception e){ e.printStackTrace(); }
	return ptcl;
    }
    */
    
    
    public static IParticleI getParticleInstance(IVec pt,
						 Constructor<? extends IParticleI> ptclConstructor,
						 Class<?>[] ptclConstructorParameters){
	
	IParticleI ptcl=null;
	try{
	    if(ptclConstructorParameters.length==0){
		ptcl =  ptclConstructor.newInstance();
		ptcl.pos().set(pt); // would this always work?
	    }
	    else if(ptclConstructorParameters.length==1 &&
		    ptclConstructorParameters[0] == IVecI.class){
		ptcl =  ptclConstructor.newInstance(pt);
	    }
	    else if(ptclConstructorParameters.length==2 &&
		    ptclConstructorParameters[0] == IVecI.class){
		// second parameter is velocity (zero)
		ptcl =  ptclConstructor.newInstance(pt,new IVec());
	    }
	    else if(ptclConstructorParameters.length==1 &&
		    ptclConstructorParameters[0] == Object.class){
		ptcl =  ptclConstructor.newInstance((Object)null);
		ptcl.pos().set(pt); // would this always work?
	    }
	    else if(ptclConstructorParameters.length==2 &&
		    ptclConstructorParameters[0] == Object.class){
		ptcl =  ptclConstructor.newInstance(null, pt);
	    }
	    else if(ptclConstructorParameters.length==3 &&
		    ptclConstructorParameters[0] == Object.class){
		ptcl =  ptclConstructor.newInstance(null, pt, new IVec());
	    }
	}catch(Exception e){ e.printStackTrace(); }
	return ptcl;
    }
    
    

    /*
    public static ITensionI getTensionInstance(IParticleI pt1, IParticleI pt2){
	return getTensionInstance(pt1,pt2,tensionConstructor,tensionConstructorParameters);
	
	// //////////////////////////
	ITensionI tnsn=null;
	try{
	    if(tensionConstructorParameters.length==2 &&
	       tensionConstructorParameters[0]== IParticleI.class &&
	       tensionConstructorParameters[1]== IParticleI.class ){
		tnsn =  tensionConstructor.newInstance(pt1,pt2);
	    }
	    else if(tensionConstructorParameters.length==3 &&
		    tensionConstructorParameters[0]== IParticleI.class &&
		    tensionConstructorParameters[1]== IParticleI.class &&
		    tensionConstructorParameters[2]== double.class ){
		// third parameter is tension
		tnsn =  tensionConstructor.newInstance(pt1,pt2,tension);
	    }
	    else if(tensionConstructorParameters.length==3 &&
		    tensionConstructorParameters[0]== Object.class &&
		    tensionConstructorParameters[1]== IParticleI.class &&
		    tensionConstructorParameters[2]== IParticleI.class){
		// third parameter is tension
		tnsn =  tensionConstructor.newInstance((Object)null,pt1,pt2);
	    }
	    else if(tensionConstructorParameters.length==4 &&
		    tensionConstructorParameters[0]== Object.class &&
		    tensionConstructorParameters[1]== IParticleI.class &&
		    tensionConstructorParameters[2]== IParticleI.class &&
		    tensionConstructorParameters[3]== double.class){
		// third parameter is tension
		tnsn =  tensionConstructor.newInstance((Object)null,pt1,pt2,tension);
	    }
	}catch(Exception e){ e.printStackTrace(); }
	return tnsn;
    }
    */
    
    
    public static ITensionI getTensionInstance(IParticleI pt1, IParticleI pt2,
					       Constructor<? extends ITensionI> tnsnConstructor,
					       Class<?>[] tnsnConstructorParameters){
	ITensionI tnsn=null;
	try{
	    if(tnsnConstructorParameters.length==2 &&
	       tnsnConstructorParameters[0]== IParticleI.class &&
	       tnsnConstructorParameters[1]== IParticleI.class ){
		tnsn =  tnsnConstructor.newInstance(pt1,pt2);
	    }
	    else if(tnsnConstructorParameters.length==3 &&
		    tnsnConstructorParameters[0]== IParticleI.class &&
		    tnsnConstructorParameters[1]== IParticleI.class &&
		    tnsnConstructorParameters[2]== double.class ){
		// third parameter is tension
		tnsn =  tnsnConstructor.newInstance(pt1,pt2,tension);
	    }
	    else if(tnsnConstructorParameters.length==3 &&
		    tnsnConstructorParameters[0]== Object.class &&
		    tnsnConstructorParameters[1]== IParticleI.class &&
		    tnsnConstructorParameters[2]== IParticleI.class){
		// third parameter is tension
		tnsn =  tnsnConstructor.newInstance((Object)null,pt1,pt2);
	    }
	    else if(tnsnConstructorParameters.length==4 &&
		    tnsnConstructorParameters[0]== Object.class &&
		    tnsnConstructorParameters[1]== IParticleI.class &&
		    tnsnConstructorParameters[2]== IParticleI.class &&
		    tnsnConstructorParameters[3]== double.class){
		// third parameter is tension
		tnsn =  tnsnConstructor.newInstance((Object)null,pt1,pt2,tension);
	    }
	}catch(Exception e){ e.printStackTrace(); }
	return tnsn;
    }
    
    /*
    public static IParticleOnCurveI getParticleOnCurveInstance(ICurveI curve, double upos, IVec pos){
	return getParticleOnCurveInstance(curve,upos,pos,particleOnCurveConstructor,particleOnCurveConstructorParameters);
	// ///////////////////////////////
	IParticleOnCurveI ptcl=null;
	try{
	    if(particleOnCurveConstructorParameters.length==2 &&
	       particleOnCurveConstructorParameters[0]==ICurveI.class &&
	       particleOnCurveConstructorParameters[1]==double.class){
		ptcl =  particleOnCurveConstructor.newInstance(curve,upos); // pos not used
	    }
	    else if(particleOnCurveConstructorParameters.length==3 &&
		    particleOnCurveConstructorParameters[0]==ICurveI.class &&
		    particleOnCurveConstructorParameters[1]==double.class &&
		    particleOnCurveConstructorParameters[2]==double.class ){
		ptcl =  particleOnCurveConstructor.newInstance(curve,upos,0); // pos not used
	    }
	    else if(particleOnCurveConstructorParameters.length==3 &&
		    particleOnCurveConstructorParameters[0]==Object.class &&
		    particleOnCurveConstructorParameters[1]==ICurveI.class &&
		    particleOnCurveConstructorParameters[2]==double.class){
		ptcl =  particleOnCurveConstructor.newInstance((Object)null,curve,upos); // pos not used
	    }
	    else if(particleOnCurveConstructorParameters.length==4 &&
		    particleOnCurveConstructorParameters[0]==Object.class &&
		    particleOnCurveConstructorParameters[1]==ICurveI.class &&
		    particleOnCurveConstructorParameters[2]==double.class &&
		    particleOnCurveConstructorParameters[3]==double.class){
		ptcl =  particleOnCurveConstructor.newInstance((Object)null,curve,upos,0); // pos not used
	    }
	    else if(particleOnCurveConstructorParameters.length==3 &&
		    particleOnCurveConstructorParameters[0]==ICurveI.class &&
		    particleOnCurveConstructorParameters[1]==double.class &&
		    particleOnCurveConstructorParameters[2]==IVec.class){
		ptcl =  particleOnCurveConstructor.newInstance(curve,upos,pos); 
	    }
	    else if(particleOnCurveConstructorParameters.length==4 &&
		    particleOnCurveConstructorParameters[0]==ICurveI.class &&
		    particleOnCurveConstructorParameters[1]==double.class &&
		    particleOnCurveConstructorParameters[2]==double.class &&
		    particleOnCurveConstructorParameters[3]==IVec.class){
		ptcl =  particleOnCurveConstructor.newInstance(curve,upos,0,pos);
	    }
	    else if(particleOnCurveConstructorParameters.length==4 &&
		    particleOnCurveConstructorParameters[0]==Object.class &&
		    particleOnCurveConstructorParameters[1]==ICurveI.class &&
		    particleOnCurveConstructorParameters[2]==double.class &&
		    particleOnCurveConstructorParameters[3]==IVec.class){
		ptcl =  particleOnCurveConstructor.newInstance((Object)null,curve,upos,pos);
	    }
	    else if(particleOnCurveConstructorParameters.length==5 &&
		    particleOnCurveConstructorParameters[0]==Object.class &&
		    particleOnCurveConstructorParameters[1]==ICurveI.class &&
		    particleOnCurveConstructorParameters[2]==double.class &&
		    particleOnCurveConstructorParameters[3]==double.class &&
		    particleOnCurveConstructorParameters[4]==IVec.class){
		ptcl =  particleOnCurveConstructor.newInstance((Object)null,curve,upos,0,pos);
	    }
	}catch(Exception e){ e.printStackTrace(); }
	return ptcl;
    }
    */
    
    public static IParticleOnCurveI getParticleOnCurveInstance(ICurveI curve, double upos, IVec pos,
							       Constructor<? extends IParticleOnCurveI> pocConstructor,
							       Class<?>[] pocConstructorParameters){
	
	IParticleOnCurveI ptcl=null;
	try{
	    if(pocConstructorParameters.length==2 &&
	       pocConstructorParameters[0]==ICurveI.class &&
	       pocConstructorParameters[1]==double.class){
		ptcl =  pocConstructor.newInstance(curve,upos); // pos not used
	    }
	    else if(pocConstructorParameters.length==3 &&
		    pocConstructorParameters[0]==ICurveI.class &&
		    pocConstructorParameters[1]==double.class &&
		    pocConstructorParameters[2]==double.class ){
		ptcl =  pocConstructor.newInstance(curve,upos,0); // pos not used
	    }
	    else if(pocConstructorParameters.length==3 &&
		    pocConstructorParameters[0]==Object.class &&
		    pocConstructorParameters[1]==ICurveI.class &&
		    pocConstructorParameters[2]==double.class){
		ptcl =  pocConstructor.newInstance((Object)null,curve,upos); // pos not used
	    }
	    else if(pocConstructorParameters.length==4 &&
		    pocConstructorParameters[0]==Object.class &&
		    pocConstructorParameters[1]==ICurveI.class &&
		    pocConstructorParameters[2]==double.class &&
		    pocConstructorParameters[3]==double.class){
		ptcl =  pocConstructor.newInstance((Object)null,curve,upos,0); // pos not used
	    }
	    else if(pocConstructorParameters.length==3 &&
		    pocConstructorParameters[0]==ICurveI.class &&
		    pocConstructorParameters[1]==double.class &&
		    pocConstructorParameters[2]==IVec.class){
		ptcl =  pocConstructor.newInstance(curve,upos,pos); 
	    }
	    else if(pocConstructorParameters.length==4 &&
		    pocConstructorParameters[0]==ICurveI.class &&
		    pocConstructorParameters[1]==double.class &&
		    pocConstructorParameters[2]==double.class &&
		    pocConstructorParameters[3]==IVec.class){
		ptcl =  pocConstructor.newInstance(curve,upos,0,pos);
	    }
	    else if(pocConstructorParameters.length==4 &&
		    pocConstructorParameters[0]==Object.class &&
		    pocConstructorParameters[1]==ICurveI.class &&
		    pocConstructorParameters[2]==double.class &&
		    pocConstructorParameters[3]==IVec.class){
		ptcl =  pocConstructor.newInstance((Object)null,curve,upos,pos);
	    }
	    else if(pocConstructorParameters.length==5 &&
		    pocConstructorParameters[0]==Object.class &&
		    pocConstructorParameters[1]==ICurveI.class &&
		    pocConstructorParameters[2]==double.class &&
		    pocConstructorParameters[3]==double.class &&
		    pocConstructorParameters[4]==IVec.class){
		ptcl =  pocConstructor.newInstance((Object)null,curve,upos,0,pos);
	    }
	}catch(Exception e){ e.printStackTrace(); }
	return ptcl;
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
				     boolean tensionOnRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean deleteLines){
	return create(railCurves,linkLines,fixedPoints,
		      tensionOnRail, fixOpenEndLinkPoint, fixOpenEndRailPoint,
		      deleteLines,IConfig.tolerance);
    }
    */
    
    /**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
       @param connectionTolerance tolerance to if two end points are connected or not
    */
    /*
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean tensionOnRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean deleteLines,
				     double connectionTolerance ){
	return create(railCurves,linkLines,fixedPoints,
		      tensionOnRail,fixOpenEndLinkPoint,
		      true, deleteLines, connectionTolerance);
    }
    */
    
    /**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param fixPointOffRail fix points which are not on the rail curve
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
    */
    /*
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines, IVecI[] fixedPoints,
				     boolean tensionOnRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean fixPointOffRail,
				     boolean deleteLines){
	return create(railCurves,linkLines,fixedPoints,
		      tensionOnRail,fixOpenEndLinkPoint,
		      fixPointOffRail, deleteLines, IConfig.tolerance);
    }
    */
    /**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param fixPointOffRail fix points which are not on the rail curve
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
	//boolean tensionOnRail, boolean fixOpenEndLinkPoint, boolean fixOpenEndRailPoint,
	//boolean fixPointOffRail, boolean deleteLines, double connectionTolerance ){
	
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
	
	IColor[] lineColors = new IColor[linkLines.length];
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
	
	if(deleteInputPoint){
	    for(int i=0; i<fixedPoints.length; i++){
		if(fixedPoints[i] instanceof IObject){
		    ((IObject)fixedPoints[i]).del();
		}
	    }
	}
	
	if(server==null){ server = IG.current().server(); }
	
	// find connection and remove duplicates
	for(int i=0; i<endPts.length; i++){
	    IVec[] epts1 = endPts[i];
	    if(epts1[1].eq(epts1[0], tolerance)){
		//epts1[1] = epts1[0];
		endPts[i] = null; // skip
	    }
	    else{
		for(int j=i+1; j<endPts.length; j++){
		    IVec[] epts2 = endPts[j];
		    for(int k=0; k<2; k++){
			if(epts2[k].eq(epts1[0], tolerance)) epts2[k] = epts1[0];
			else if(epts2[k].eq(epts1[1], tolerance)) epts2[k] = epts1[1];
		    }
		}
	    }
	}
	
	ArrayList<IVec> uniquePts = new ArrayList<IVec>();
	for(int i=0; i<endPts.length; i++){
	    if(endPts[i]!=null){
		for(int j=0; j<2; j++){
		    IVec pt = endPts[i][j];
		    if(!uniquePts.contains(pt)) uniquePts.add(pt);
		}
	    }
	}
	
	
	ArrayList<IVecI> openPts = null;
	if(fixOpenEnd){
	    // cheking naked end point
	    openPts = new ArrayList<IVecI>();
	    for(int i=0; i<uniquePts.size(); i++){
		int count=0;
		for(int j=0; j<endPts.length; j++){
		    if(endPts[j]!=null){
			if(endPts[j][0] == uniquePts.get(i)) count++;
			if(endPts[j][1] == uniquePts.get(i)) count++;
		    }
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
	
	ArrayList<IParticleOnCurveI>[] particleOnRail = null;
	
	
	if(tensionOnRail || spacingEqualizer){
	    // !!!
	    @SuppressWarnings("unchecked")
		ArrayList<IParticleOnCurveI>[] pos = new ArrayList[railCurves.length];
	    particleOnRail = pos;
	    
	    for(int i=0; i<particleOnRail.length; i++){
		particleOnRail[i] = new ArrayList<IParticleOnCurveI>();
	    }
	}
	
	ConstructorAndParameters<IParticleI> particleConst=null;
	ConstructorAndParameters<ITensionI> tensionConst=null;
	ConstructorAndParameters<IParticleOnCurveI> particleOnCurveConst=null;
	
	// check custom class
	if(particleClass!=null){
	    //searchParticleConstructor(particleClass);
	    particleConst = searchParticleConstructor(particleClass);
	}
	if(tensionClass!=null){
	    //searchTensionConstructor(tensionClass);
	    tensionConst = searchTensionConstructor(tensionClass);
	}
	if(particleOnCurveClass!=null){
	    //searchParticleOnCurveConstructor(particleOnCurveClass);
	    particleOnCurveConst = searchParticleOnCurveConstructor(particleOnCurveClass);
	}
	
	
	ITensileNet network = null;
	synchronized(server.dynamicServer()){
	    
	    final double uCurveTolerance = 1.0/1000;
	    
	    // find a rail curve to be on for each uniquePts
	    //ArrayList<IParticle> particles = new ArrayList<IParticle>();
	    ArrayList<IParticleI> particles = new ArrayList<IParticleI>();
	    
	    for(int i=0; i<uniquePts.size(); i++){
		if(uniquePts.size()>100 && i%100==0){
		    IOut.debug(0, "finding curve for point to be on ("+i+"/"+uniquePts.size()+")"); //
		}
		
		IParticleOnCurveI poc = createParticleOnClosestCurve(railCurves,
								     uniquePts.get(i),
								     railTolerance, //tolerance,
								     uCurveTolerance,
								     particleOnCurveConst);
		
		IParticleI ptcl = poc;
		
		//if(ptcl != null){ particles.add(poc); }
		/*
		IParticle pa=null;
		if(poc!=null){
		    pa = new IParticle(poc);
		    pa.clr(railPointColor);
		    if(pointLayer!=null) pa.layer(IG.layer(pointLayer).clr(pointColor));
		    
		    //unfix if it's 
		}
		*/
		if(ptcl == null){
		    // just particle not on a rail curve
		    //if(particleConstructor!=null && particleConstructorParameters!=null){
		    if(particleConst!=null){
			//ptcl = getParticleInstance(uniquePts.get(i));
			ptcl = getParticleInstance(uniquePts.get(i),particleConst.constructor,particleConst.parameters);
			
			if(ptcl!=null){
			    ptcl.fric(friction);
			    
			    if(fixPointOffRail ||
			       fixOpenEnd &&openPts!=null && openPts.contains(uniquePts.get(i))){ // in case the point is open point and fixOpenEnd is true.
				ptcl.fix(); // if not on the curve, fixed.
				
				if(ptcl instanceof IObject){
				    if(fixedPointLayer!=null) ((IObject)ptcl).layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
				    ((IObject)ptcl).clr(fixedPointColor);
				}
			    }
			    else{
				if(ptcl instanceof IObject){
				    if(pointLayer!=null) ((IObject)ptcl).layer(IG.layer(pointLayer).clr(pointColor));
				    ((IObject)ptcl).clr(pointColor);
				}
			    }
			}
		    }
		    
		    if(ptcl == null){
			IParticle pa = new IParticle(uniquePts.get(i));
			pa.fric(friction);
			
			if(fixPointOffRail ||
			   fixOpenEnd &&openPts!=null && openPts.contains(uniquePts.get(i))){ // in case the point is open point and fixOpenEnd is true.
			    pa.fix(); // if not on the curve, fixed.
			    pa.clr(fixedPointColor);
			    if(fixedPointLayer!=null) pa.layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
			}
			else{
			    pa.clr(pointColor);
			    if(pointLayer!=null) pa.layer(IG.layer(pointLayer).clr(pointColor));
			}
			
			ptcl = pa;
		    }
		}
		
		particles.add(ptcl);
		
		if(fixlist[i]){
		    ptcl.fix();
		    if(ptcl instanceof IObject){
		    
			((IObject)ptcl).clr(fixedPointColor);
			if(fixedPointLayer!=null) ((IObject)ptcl).layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
		    }
		    //pa.clr(fixedPointColor);
		    //if(fixedPointLayer!=null) pa.layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
		}
		
		if(poc!=null){
		    if(tensionOnRail || spacingEqualizer){
			// put particle on each list of rail
			int sectIdx = -1;
			for(int j=0; j<railCurves.length && sectIdx<0; j++){
			    if(railCurves[j]==poc.curve()) sectIdx=j;
			}
			if(sectIdx>=0){ particleOnRail[sectIdx].add(poc); }
		    }
		    
		    if(fixAtRailEnd){
			if(poc.upos()<IConfig.parameterTolerance ||
			   poc.upos()>1.0-IConfig.parameterTolerance){
			    poc.fix();
			    
			    if(poc instanceof IObject){
				((IObject)poc).clr(fixedPointColor);
				if(fixedPointLayer!=null) ((IObject)poc).layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
			    }
			    //if(pa!=null){
			    //	pa.clr(fixedPointColor);
			    //	if(fixedPointLayer!=null) pa.layer(IG.layer(fixedPointLayer).clr(fixedPointColor));
			    //}
			}
		    }
		}
	    }
	    
	    
	    
	    //ArrayList<ITensionLine> tlines = new ArrayList<ITensionLine>();
	    ArrayList<ITensionI> tlines = new ArrayList<ITensionI>();
	    for(int i=0; i<endPts.length; i++){
		IVec[] epts = endPts[i];
		if(epts!=null){
		    int index1 = uniquePts.indexOf(epts[0]);
		    int index2 = uniquePts.indexOf(epts[1]);
		    
		    //IG.p("epts0 = "+((Object)epts[0]).toString());
		    //IG.p("epts1 = "+((Object)epts[1]).toString());
		    
		    if(index1>=0 && index2>=0){
			IParticleI pa1 = particles.get(index1);
			IParticleI pa2 = particles.get(index2);
			
			//IG.p("pa1 = "+((Object)pa1).toString());
			//IG.p("pa2 = "+((Object)pa2).toString());
			
			ITensionI tnsn = null;
			//if(tensionConstructor!=null && tensionConstructorParameters!=null){
			if(tensionConst!=null){
			    //tnsn = getTensionInstance(pa1,pa2);
			    tnsn = getTensionInstance(pa1,pa2,tensionConst.constructor,tensionConst.parameters);
			    if(tnsn!=null){
				if(tnsn instanceof IObject){
				    if(tensionColor!=null) ((IObject)tnsn).clr(tensionColor);
				    else if(lineColors[i]!=null) ((IObject)tnsn).clr(lineColors[i]);
				    
				    if(tensionLayer!=null) ((IObject)tnsn).layer(tensionLayer);
				    else if(lineLayer[i]!=null) ((IObject)tnsn).layer(lineLayer[i]);
				}
				tnsn.tension(tension);
				tnsn.constant(constantTension);
				tlines.add(tnsn);
			    }
			}
			
			if(tnsn==null){
			    ITensionLine tl = new ITensionLine(pa1, pa2, tension);
			    if(tensionColor!=null) tl.clr(tensionColor);
			    else if(lineColors[i]!=null) tl.clr(lineColors[i]);
			    
			    if(tensionLayer!=null) tl.layer(tensionLayer);
			    else if(lineLayer[i]!=null) tl.layer(lineLayer[i]);
			    
			    tl.tension(tension);
			    tl.constant(constantTension);
			    tlines.add(tl);
			    
			}
		    }
		    else{
			IOut.err("end point is not found");
		    }
		}
	    }
	    
	    if(tensionOnRail || spacingEqualizer){
		// sort particleoncurve and create tension
		
		for(int i=0; i<railCurves.length; i++){
		    if(particleOnRail[i].size()>1){
			ISort.sort(particleOnRail[i], new IParticleOnCurveComparator());
			
			boolean closed=railCurves[i].isClosed();
			
			if(tensionOnRail){
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
			
			if(spacingEqualizer){
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
	    
	    if(straightener){
		
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
		
		if(removeBranchStraightener){
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
				     boolean tensionOnRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint){
	return create(railCurves,linkLines,fixedLines,fixedPoints,
		      tensionOnRail,fixOpenEndLinkPoint,
		      fixOpenEndRailPoint,true,IConfig.tolerance);
    }
    
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints,
				     boolean tensionOnRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean deleteLines){
	return create(railCurves,linkLines,fixedLines,fixedPoints,
		      tensionOnRail,fixOpenEndLinkPoint,
		      fixOpenEndRailPoint,deleteLines,IConfig.tolerance);
    }
    */
    
    /**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnRail when this is true, tension between points on the same curve is created.
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
				     boolean tensionOnRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean deleteLines,
				     double connectionTolerance ){
	return create(railCurves,linkLines,fixedLines,fixedPoints,
		      tensionOnRail,fixOpenEndLinkPoint,
		      fixOpenEndRailPoint,true,deleteLines,connectionTolerance);
    }
    */
/**
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param fixPointOffRail fix points which are not on the rail curve
       @param deleteLines when this is true and if lines are subclass of IObject, lines are deleted.
*/
    /*
    public static ITensileNet create(ICurveI[] railCurves,
				     ICurveI[] linkLines,
				     ICurveI[] fixedLines,
				     IVecI[] fixedPoints,
				     boolean tensionOnRail,
				     boolean fixOpenEndLinkPoint,
				     boolean fixOpenEndRailPoint,
				     boolean fixPointOffRail,
				     boolean deleteLines ){
	
	return create(railCurves,linkLines,fixedLines,fixedPoints,
		      tensionOnRail,fixOpenEndLinkPoint,
		      fixOpenEndRailPoint,fixPointOffRail,deleteLines,IConfig.tolerance);
    }
    */
    
    /* ***old API
       create a network of tension line on rail curve.
       @param railCurves curves on which all the points sit.
       @param linkLines lines to create tension line. if it's curve, it's simplified to line.
       @param fixedPoints if those points are on the end point of linkLines, those end points of tension line are fixed.
       @param tensionOnRail when this is true, tension between points on the same curve is created.
       @param fixOpenEndLinkPoint when this is true open end of link lines are fixed
       @param fixOpenEndRailPoint when this is true points on the end of rail are fixed
       @param fixPointOffRail fix points which are not on the rail curve
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
	// //boolean tensionOnRail,
	//boolean fixOpenEndLinkPoint, boolean fixOpenEndRailPoint,
	// boolean fixPointOffRail, boolean deleteLines,double  connectionTolerance ){
		
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
	//tensionOnRail,fixOpenEndLinkPoint,fixOpenEndRailPoint,fixPointOffRail,deleteLines,
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
    public static IParticleOnCurveI createParticleOnClosestCurve(ICurveI[] railCurves,
								 IVec pos,
								 double closenessTolerance,
								 double uTolerance, 
								 ConstructorAndParameters<IParticleOnCurveI> particleOnCurveConst){
	final int roughSearchResolution = 20;
	IParticleOnCurveI particle = null;
	do{
	    ICurveI closestCrv = null;
	    if(railCurves.length==1) closestCrv = railCurves[0];
	    else closestCrv = findClosestCurve(railCurves, pos, roughSearchResolution);
	    
	    particle = createParticleOnCurve(closestCrv, pos, closenessTolerance, uTolerance, particleOnCurveConst);
	    
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
    public static IParticleOnCurveI createParticleOnCurve(ICurveI curve,
							  IVec pos,
							  double closenessTolerance,
							  double uTolerance,
							  ConstructorAndParameters<IParticleOnCurveI> particleOnCurveConst){
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
	
	if(closenessTolerance >= 0 && minDist > closenessTolerance) return null; // no particle found
	
	//if(particleOnCurveConstructor!=null && particleOnCurveConstructorParameters!=null){
	if(particleOnCurveConst!=null){
	    //IParticleOnCurveI ptcl = getParticleOnCurveInstance(curve,minU,pos);
	    IParticleOnCurveI ptcl = getParticleOnCurveInstance(curve,minU,pos,
								particleOnCurveConst.constructor,
								particleOnCurveConst.parameters);
	    if(ptcl!=null){
		ptcl.friction(friction);
		if(ptcl instanceof IObject){
		    if(pointLayer!=null) ((IObject)ptcl).layer(IG.layer(pointLayer).clr(pointColor));
		    ((IObject)ptcl).clr(railPointColor);
		    
		}
		return ptcl;
	    }
	}
	
	IParticleOnCurve poca = new IParticleOnCurve(curve, minU, pos);
	
	poca.fric(friction);
	if(pointLayer!=null) poca.layer(IG.layer(pointLayer).clr(pointColor));
	poca.clr(railPointColor);
	
	return poca;
	//return new IParticleOnCurve(curve, minU, pos);
    }

    public ITensileNet join(ITensileNet net){ return join(net, ITensileNet.tolerance); }
    public ITensileNet join(ITensileNet net, double tolerance){
	for(int i=0; i<nodes.size(); i++){
	    IParticleI pt1 = nodes.get(i);
	    for(int j=0; j<net.nodes.size(); j++){
		IParticleI pt2 = net.nodes.get(j);
		if(pt1.eq(pt2,tolerance)){
		    new IParticleCoupler(pt1,pt2);
		    /*
		    IVec pos1 = null;
		    if(pt1 instanceof IParticle){ pos1 = ((IParticle)pt1).pos; }
		    else if(pt1 instanceof IParticle){ pos1 = ((IParticle)pt1).particle.pos; }
		    if(pos1!=null){
			if(pt2 instanceof IParticle){ ((IParticle)pt2).pos = pos1; }
			else if(pt2 instanceof IParticle){ ((IParticle)pt2).particle.pos = pos1; }
		    }
		    */
		}
	    }
	}
	return this;
    }
    
    
    
    public static class IParticleOnCurveComparator implements IComparator<IParticleOnCurveI>{
	public int compare(IParticleOnCurveI p1, IParticleOnCurveI p2){
	    if(p1.upos()<p2.upos()) return -1;
	    if(p1.upos()>p2.upos()) return 1;
	    return 0;
	}
    }

    
    // this doesn't work with IParticle. works only with IParticle.
    public static class IParticleCoupler extends IDynamicsBase{
	public IParticleI particle1, particle2;
	public IParticleCoupler(IParticleI p1, IParticleI p2){
	    particle1 = p1;
	    particle2 = p2;
	    
	    IConfig.enablePreupdate=true; // ! is this ok here?
	    IConfig.loopPreupdate=true; // ! is this ok here?
	}
	public void preupdate(){
	    //IVec f1 = particle1.frc().dup();
	    //IVec f2 = particle2.frc().dup();
	    //particle1.push(f2);
	    //particle2.push(f1);
	    if(particle1.fixed()){ particle2.fix(); }
	    else if(particle2.fixed()){ particle1.fix(); }
	    else{
		//IVec f = particle1.frc().sum(particle2.frc());
		//particle1.frc(f);
		//particle2.frc(f);
		particle2.pos(particle1.pos());
		particle2.vel(particle1.vel());
	    }
	}
    }
}
