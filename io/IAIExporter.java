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

package igeo.io;

import java.io.*;




import java.util.ArrayList;
import java.text.*;

import igeo.*;
import igeo.gui.IView;

/**
   Adobe Illustrator file exporter
   
   @author Satoru Sugihara
*/

class IAIExporter {
    
    public static double minimumPoint = 0.00001f; //0.001f; //0.01f;
    
    //public static double defaultLineWeight=0.25;
    //public static double defaultLineWeight=0.05;
    //public static double defaultLineWeight=0.025;
    public static double defaultLineWeight=0.25; //0.1;
    public static double defaultPointWeight=0.5;
    
    public static boolean writeSubsurfaceAsMesh=false; //true; // mesh or bezier shape
    public static boolean writeOutlineWhenMesh=false; //
    
    public static double internalScaleFactor=4.667;
    public static double internalXShift=-49.9; // inch
    public static double internalYShift=-39.9; // inch
    
    static public enum CapType { Butt, Round, Square };
    static public enum JoinType { Miter, Round, Bevel };
    
    
    public static NumberFormat f;
    
    public static void initFormat(){
	f = NumberFormat.getInstance();
	if(f instanceof DecimalFormat){
	    ((DecimalFormat)f).setDecimalSeparatorAlwaysShown(false);
	    ((DecimalFormat)f).setMaximumFractionDigits(50);
	    ((DecimalFormat)f).setMinimumFractionDigits(0);
	    ((DecimalFormat)f).setGroupingUsed(false);	    
	}
	else{
	    IOut.err("Not DecimalFormat"); //
	}
	
    }
    
    public static void writeHeader(PrintStream ps){
	
	ps.println("%!PS-Adobe-3.0");
	ps.println("%%Creator: Java");
	//ps.println("%%Title: ("+filename+")");
	//ps.println("%%BoundingBox: "+(int)x1+" "+(int)y1+" "+(int)(x2+.5+1.0)+" "+(int)(y2+.5+1.0));
	ps.println("%%BoundingBox: 0 0 1000 1000");
	ps.println("%%DocumentProcessColors: Black");
	ps.println("%%DocumentNeededResources: procset Adobe_packedarray 2.0 0");
	ps.println("%%+ procset Adobe_cmykcolor 1.1 0");
	ps.println("%%+ procset Adobe_cshow 1.1 0");
	ps.println("%%+ procset Adobe_customcolor 1.0 0");
	ps.println("%%+ procset Adobe_typography_AI3 1.0 0");
	ps.println("%%+ procset Adobe_IllustratorA_AI3 1.0 0");
	ps.println("%AI3_ColorUsage: Color");
	//ps.println("%AI3_TemplateBox: 288 384 288 384"); //
	//ps.println("%AI3_TileBox: 0 0 576 768"); //
	
	ps.println("%AI3_DocumentPreview: None");
	ps.println("%%Template:");
	ps.println("%%PageOrigin:0 0");
	ps.println("%%EndComments");
	ps.println("%%BeginProlog");
	ps.println("%%IncludeResource: procset Adobe_packedarray 2.0 0");
	ps.println("Adobe_packedarray /initialize get exec");
	ps.println("%%IncludeResource: procset Adobe_cmykcolor 1.1 0");
	ps.println("%%IncludeResource: procset Adobe_cshow 1.1 0");
	ps.println("%%IncludeResource: procset Adobe_customcolor 1.0 0");
	ps.println("%%IncludeResource: procset Adobe_typography_AI3 1.0 0");
	ps.println("%%IncludeResource: procset Adobe_IllustratorA_AI3 1.0 0");
	ps.println("%%EndProlog");
	ps.println("%%BeginSetup");
	ps.println("Adobe_cmykcolor /initialize get exec");
	ps.println("Adobe_cshow /initialize get exec");
	ps.println("Adobe_customcolor /initialize get exec");
	ps.println("Adobe_typography_AI3 /initialize get exec");
	ps.println("Adobe_IllustratorA_AI3 /initialize get exec");
	ps.println("%%EndSetup");
    }
    
    
    public static void convertCoordinates(IVec2 pt, double scale, IView view){
	pt.y = (double) view.screenHeight - pt.y; // upside down
	if(view.isAxonometric()){
	    pt.x *= scale*72.0/view.getAxonometricRatio(); // 72 pt / inch
	    pt.y *= scale*72.0/view.getAxonometricRatio(); // 72 pt / inch
	}
	else{
	    pt.x *= scale; // 1 pixel = 1*scale pt
	    pt.y *= scale; // 1 pixel = 1*scale pt
	}
	
	// to adjust AI drawing
	pt.x *= internalScaleFactor; // 
	pt.y *= internalScaleFactor; // 
	
	pt.x += internalXShift*72; // pt, inch
	pt.y += internalYShift*72; // pt, inch
    }
    
    /*
    public static void writePolygon(PrintStream ps, IVecI[] cpts,
				    Color color, Color edgeColor, double scale, double opacity, IView view){
	
	IVec2 pt;
	ArrayList<IVec2> pts = new ArrayList<IVec2>();
	
	for(int i=0; i<cpts.length; i++){
	    pt = new IVec2();
	    if(view.convert(cpts[i], pt)){
		convertCoordinates(pt, scale, view);
		pts.add(pt);
	    }
	}
	if(pts.size()>1){
	    ps.println("0 A");
	    ps.println("0 R");
	    
	    if(color==null){ ps.println("0 0 0 Xa"); }
	    else{
		ps.println( f.format((double)color.getRed()/255) + " " +
			    f.format((double)color.getGreen()/255) + " " +
			    f.format((double)color.getBlue()/255) + " " +
			    "Xa");
	    }
	    if(edgeColor==null){ ps.println("0 0 0 XA"); }
	    else{
		ps.println( f.format((double)edgeColor.getRed()/255) + " " +
			    f.format((double)edgeColor.getGreen()/255) + " " +
			    f.format((double)edgeColor.getBlue()/255) + " " +
			    "XA");
	    }
	    
	    ps.println("0 "+f.format(opacity)+" 0 0 0 Xy");
	    ps.println("0 J 0 j 1 w 4 M []0 d");
	    
	    for(int i=0; i<=pts.size(); i++){
		//if(i>0){ ps.println("m"); }
		pt = (IGPoint2d)pts.get(i%pts.size());
		ps.print(f.format(pt.x) + " "+ f.format(pt.y) + " ");
		if(i==0) ps.println("m"); 
		else ps.println("l");
	    }
	    //ps.println("l");
	    if( color!=null && edgeColor!=null ){
		ps.println("b"); // closed curve with filling & stroke
	    }
	    else if( color!=null && edgeColor==null ){
		ps.println("f"); // closed curve with filling
	    }
	    else if( color==null && edgeColor!=null ){
		ps.println("s"); // closed curve with stroke
	    }
	    else{ // color==null && edgeColor==null
		ps.println("n"); // closed curve without filling nor stroke
	    }
	}
    }
    
    public static void writePolygon2D(PrintStream ps,
				      IVec2[] points,
				      Color color, Color edgeColor, double scale, double opacity, IView view){
	IVec2 pt;
	ArrayList<IVec2> pts = new ArrayList<IVec2>();
	
	for(int i=0; i<points.length; i++){
	    pt = new IVec2();
	    pt.x = points[i].x;
	    pt.y = points[i].y;
	    convertCoordinates(pt,scale,view);
	    pts.add(pt);
	}
	
	if(pts.size()>1){
	    ps.println("0 A");
	    ps.println("0 R");
	    
	    if(color==null){ ps.println("0 0 0 Xa"); }
	    else{
		ps.println( f.format((double)color.getRed()/255) + " " +
			    f.format((double)color.getGreen()/255) + " " +
			    f.format((double)color.getBlue()/255) + " " +
			    "Xa");
	    }
	    if(edgeColor==null){ ps.println("0 0 0 XA"); }
	    else{
		ps.println( f.format((double)edgeColor.getRed()/255) + " " +
			    f.format((double)edgeColor.getGreen()/255) + " " +
			    f.format((double)edgeColor.getBlue()/255) + " " +
			    "XA");
	    }
	    
	    ps.println("0 "+f.format(opacity)+" 0 0 0 Xy");
	    ps.println("0 J 0 j 1 w 4 M []0 d");
	    
	    for(int i=0; i<=pts.size(); i++){
		//if(i>0){ ps.println("m"); }
		pt = (IGPoint2d)pts.get(i%pts.size());
		ps.print(f.format(pt.x) + " "+ f.format(pt.y) + " ");
		if(i==0) ps.println("m"); 
		else ps.println("l");
	    }
	    //ps.println("l");
	    if( color!=null && edgeColor!=null ){
		ps.println("b"); // closed curve with filling & stroke
	    }
	    else if( color!=null && edgeColor==null ){
		ps.println("f"); // closed curve with filling
	    }
	    else if( color==null && edgeColor!=null ){
		ps.println("s"); // closed curve with stroke
	    }
	    else{ // color==null && edgeColor==null
		ps.println("n"); // closed curve without filling nor stroke
	    }
	}
    }
    
    public static ArrayList<IVec2> getPolylinePoints(IVecI[] controlPoints,
						     double scale, IView view){
	IVec2 pt;
	ArrayList<IVec2> pts = new ArrayList<IVec2>();
	for(int i=0; i<controlPoints.length; i++){
	    pt = new IVec2();
	    if(view.convert(controlPoints[i],pt)){
		convertCoordinates(pt,scale,view);
		pts.add(pt);
	    }
	}
	if(pts.size()==0){ return null; }
	
	for(int i=1; i<pts.size(); i++){
	    IVec2 pt_prev = pts.get(i-1);
	    pt = pts.get(i);
	    if( pt.distance(pt_prev) < minimumPoint ){
		pts.remove(i);
		i--;
	    }
	}
	
	if(pts.size()<2){ return null; }
	return pts;
    }
    
    public static ArrayList<IVec2> getBezierPoints(int degree,
						   IVecI[] controlPoints,
						   IVecI[] editPoints,
						   IVecI[] tangents,
						   double scale,
						   IView view){
	
	IVec2 pt;

	ArrayList<IVec2> v_cpts = new ArrayList<IVec2>();
	ArrayList<IVec2> v_epts = new ArrayList<IVec2>();
	ArrayList<IVec2> v_tangents = new ArrayList<IVec2>();
	
	boolean inview = false;
	for(int i=0; i<controlPoints.length; i++){
	    pt = new IVec2();
	    boolean f = view.convert(controlPoints[i],pt);
	    if(f){ inview = true; }
	    convertCoordinates(pt,scale,view);
	    v_cpts.add(pt);
	}
	
	if(!inview){ return(null); }
	
	for(int i=0; i<editPoints.length; i++){
	    pt = new IVec2();
	    view.convert(editPoints[i],pt);
	    convertCoordinates(pt,scale);
	    v_epts.add(pt);
	}
	
	for(int i=0; i<tangents.length; i++){
	    IVec tangent = new IVec();
	    tangent.scale(100); // length doesn't matter...
	    tangent.add(editPoints[i]); // actual point in space (not direction vector)
	    
	    pt = new IVec2();
	    view.convert(tangent,pt);
	    convertCoordinates(pt,scale,view);
	    v_tangents.add(pt);
	}
	
	ArrayList<IVec2> pts = new ArrayList<IVec2>();
	
	// check too close or identica
	if(v_cpts.size()>=5){
	    for(int i=degree+1; i<(v_epts.size()-degree-1); i++){
		IVec2 ept1 = v_epts.get(i-1);
		IVec2 ept2 = v_epts.get(i);
		if(ept1.dist(ept2) < minimumPoint ){
		    v_epts.remove(i);
		    v_tangents.remove(i);
		    v_cpts.remove(i-degree+1);
		    i--;
		}
	    }
	}
	
	if(v_cpts.size()>=5){
	    
	    pts.add(v_cpts.get(0));
	    
	    for(int i=degree; i<(v_epts.size()-degree-1); i++){
		if(i==degree){
		    
		    IVec2 cpt1 = v_cpts.get(i-degree+1);
		    IVec2 cpt2 = v_cpts.get(i-degree+2);
		    
		    IVec2 dir1 = new IVec2();
		    dir1.x = cpt2.x - cpt1.x;
		    dir1.y = cpt2.y - cpt1.y;
		    
		    IVec2 ept2 = v_epts.get(i+1);
		    IVec2 tpt2 = v_tangents.get(i+1);
		    IVec2 tdir2 = new IVec2(tpt2.x-ept2.x,tpt2.y-ept2.y);
		    
		    IVec2 ipt2 = getIntersection(cpt1,dir1,ept2,tdir2);
		    
		    pts.add(cpt1);
		    pts.add(ipt2);
		    pts.add(ept2);
		}
		else if(i==(v_epts.size()-degree-2)){
		    IVec2 cpt1 = v_cpts.get(i-degree+1);
		    IVec2 cpt2 = v_cpts.get(i-degree+2);
		    
		    IVec2 dir1 = new IVec2();
		    dir1.x = cpt2.x - cpt1.x;
		    dir1.y = cpt2.y - cpt1.y;
		    
		    IVec2 ept1 = v_epts.get(i);
		    IVec2 tpt1 = v_tangents.get(i);
		    IVec2 tdir1 = new IVec2(tpt1.x-ept1.x,tpt1.y-ept1.y);
		    
		    IVec2 ipt1 = getIntersection(cpt1,dir1,ept1,tdir1);
		    
		    pts.add(ipt1);
		    pts.add(cpt2);
		}
		else{
		    IVec2 cpt1 = v_cpts.get(i-degree+1);
		    IVec2 cpt2 = v_cpts.get(i-degree+2);
		    
		    IVec2 dir1 = new IVec2();
		    dir1.x = cpt2.x - cpt1.x;
		    dir1.y = cpt2.y - cpt1.y;
		    
		    IVec2 ept1 = v_epts.get(i);
		    IVec2 tpt1 = v_tangents.get(i);
		    IVec2 tdir1 = new IVec2(tpt1.x-ept1.x,tpt1.y-ept1.y);
		    
		    IVec2 ipt1 = getIntersection(cpt1,dir1,ept1,tdir1);
		    
		    IVec2 ept2 = v_epts.get(i+1);
		    IVec2 tpt2 = v_tangents.get(i+1);
		    IVec2 tdir2 = new IVec2(tpt2.x-ept2.x,tpt2.y-ept2.y);
		    
		    IVec2 ipt2 = getIntersection(cpt1,dir1,ept2,tdir2);
		    
		    pts.add(ipt1);
		    pts.add(ipt2);
		    pts.add(ept2);
		}
	    }
	    pts.add(v_cpts.get(v_cpts.size()-1));
	    
	}
	else if(v_cpts.size()==4){
	    pts.add(v_cpts.get(0));
	    pts.add(v_cpts.get(1));
	    pts.add(v_cpts.get(2));
	    pts.add(v_cpts.get(3));
	}
	
	if(pts.size()<4){ return null; }
	
	if(pts.size()%3==1){
	    for(int i=1; i<(int)(pts.size()/3); i++){
		IVec2 pt_prev = pts.get((i-1)*3);
		pt = pts.get(i*3);
		
		IVec2 pt1 = pts.get(i*3-2);
		IVec2 pt2 = pts.get(i*3-1);
		
		if( (pt.dist(pt_prev) < minimumPoint) ||
		    Double.isNaN(pt.x) || Double.isNaN(pt.y) ||
		    Double.isNaN(pt1.x) || Double.isNaN(pt1.y) ||
		    Double.isNaN(pt2.x) || Double.isNaN(pt2.y) ||
		    Double.isInfinite(pt.x) || Double.isInfinite(pt.y) ||
		    Double.isInfinite(pt1.x) || Double.isInfinite(pt1.y) ||
		    Double.isInfinite(pt2.x) || Double.isInfinite(pt2.y)
		    ){
		    pts.remove(i*3);
		    pts.remove(i*3-1);
		    pts.remove(i*3-2);
		    i--;
		}
	    }
	}
	else{
	    IOut.err("number of points is wrong: pts.size = "+pts.size()); //
	}
	return pts;
    }
    
    public static void writeBezierCurvePaths(PrintStream ps,
					     ArrayList<ArrayList<IVec2>> pts,
					     boolean newline){
	for(int i=0;i<pts.size();i++) writeBezierCurvePath(ps,pts.get(i),newline&&i==0?true:false);
    }
    
    public static void writeBezierCurvePath(PrintStream ps, ArrayList<IVec2> pts, boolean newline){
	if(pts.size()%3!=1) IOut.err("wrong number of points "+pts.size());
	
	for(int j=0; j<(pts.size()-1)/3; j++){
	    if(j==0){
		if(newline){
		    ps.print(f.format(pts.get(j*3).x) + " "+
			     f.format(pts.get(j*3).y) + " ");
		    ps.println("m");
		}
		else{
		    ps.print("%%"); //
		    ps.print(f.format(pts.get(j*3).x) + " "+
			     f.format(pts.get(j*3).y) + " ");
		    ps.println("m");
		}
	    }
	    
	    ps.print(f.format(pts.get(j*3+1).x)+" "+
		     f.format(pts.get(j*3+1).y) + " ");
	    ps.print(f.format(pts.get(j*3+2).x)+" "+
		     f.format(pts.get(j*3+2).y) + " ");
	    ps.print(f.format(pts.get(j*3+3).x)+" "+
		     f.format(pts.get(j*3+3).y) + " ");
	    if(j==(pts.size()-1)/3-1) ps.println("C");
	    else ps.println("c");
	    //else ps.println("C");
	}
    }
    */
    
    public static void writePolylinePaths(PrintStream ps, ArrayList<ArrayList<IVec2>> pts,
					  boolean newline){
	for(int i=0; i<pts.size(); i++) writePolylinePath(ps,pts.get(i),newline&&i==0?true:false);
    }
    
    public static void writePolylinePath(PrintStream ps, ArrayList<IVec2> pts, boolean newline){
	for(int j=0; j<pts.size(); j++){
	    IVec2 pt = pts.get(j);
	    
	    if(j==0){
		if(newline){
		    ps.print(f.format(pt.x) + " "+ f.format(pt.y) + " ");
		    ps.println("m");
		}
		else{
		    ps.print("%%");
		    ps.print(f.format(pt.x) + " "+ f.format(pt.y) + " ");
		    ps.println("m");
		}
	    }
	    else{
		ps.print(f.format(pt.x) + " "+ f.format(pt.y) + " ");
		if(j==pts.size()-1) ps.println("L");
		else ps.println("l");
	    }
	}
    }
    
    public static void writePointPath(PrintStream ps, IVec2 pt){
	ps.print(f.format(pt.x) + " "+ f.format(pt.y) + " ");
	ps.println("m");
	ps.print(f.format(pt.x) + " "+ f.format(pt.y) + " ");
	ps.println("L");
    }

    /*
    // remove null points out of screen
    public static ArrayList<ArrayList<IVec2>> createBezierPointArrayList(IVec2[] pts){
	ArrayList<ArrayList<IVec2>> pts2 = new ArrayList<ArrayList<IVec2>>();
	pts2.add(new ArrayList<IVec2>());
	
	for(int i=0; i<(pts.length-1)/3; i++){
	    if(pts[i*3]!=null&& pts[i*3+1]!=null&& pts[i*3+2]!=null&& pts[i*3+3]!=null){
		ArrayList<IVec2> last = pts2.get(pts2.size()-1);
		if(last.size()==0) last.add(pts[i*3]);
		last.add(pts[i*3+1]);
		last.add(pts[i*3+2]);
		last.add(pts[i*3+3]);
	    }
	    else if(pts2.get(pts2.size()-1).size()>0){
		pts2.add(new ArrayList<IVec2>());
	    }
	}
	return pts2;
    }
    */
    
    // remove null points out of screen
    public static ArrayList<ArrayList<IVec2>> createPolylinePointArrayList(IVec2[] pts){
	
	ArrayList<ArrayList<IVec2>> pts2 = new ArrayList<ArrayList<IVec2>>();
	pts2.add(new ArrayList<IVec2>());
	for(int i=0; i<pts.length; i++){
	    if(pts[i]!=null) pts2.get(pts2.size()-1).add(pts[i]);
	    else if(pts2.get(pts2.size()-1).size()>0) pts2.add(new ArrayList<IVec2>());
	}
	return pts2;
    }

    
    public static void writePaintStyle(PrintStream ps,
				       IColor fillColor, IColor strokeColor, double lineWidth,
				       CapType capType, JoinType joinType){
	ps.println("0 A"); // lock
	if(fillColor!=null) ps.println("0 O"); // overprint color
	if(strokeColor!=null) ps.println("0 R"); // overprint color
	
	double opacity=1.0;
	
	if(fillColor!=null){
	    ps.println( f.format((double)fillColor.getRed()/255) + " " +
			f.format((double)fillColor.getGreen()/255) + " " +
			f.format((double)fillColor.getBlue()/255) + " " +
			"Xa");
	    opacity = (double)fillColor.getAlpha()/255;
	}
	if(strokeColor!=null){
	    ps.println( f.format((double)strokeColor.getRed()/255) + " " +
			f.format((double)strokeColor.getGreen()/255) + " " +
			f.format((double)strokeColor.getBlue()/255) + " " +
			"XA");
	    if(fillColor==null) opacity = (double)strokeColor.getAlpha()/255;
	}
	ps.println("0 "+f.format(opacity)+" 0 0 0 Xy");
	
	
	int capValue = 0;
	switch(capType){
	case Butt: capValue = 0; break; 
	case Round: capValue = 1; break; 
	case Square: capValue = 2; break; 
	}
	
	int joinValue = 0;
	switch(joinType){
	case Miter: joinValue = 0; break; 
	case Round: joinValue = 1; break; 
	case Bevel: joinValue = 2; break; 
	}
	
	ps.println(capValue+" J "+joinValue+" j "+f.format(lineWidth)+" w 4 M []0 d");
    }
    
    public static void writePaintStyle(PrintStream ps,
				       IColor fillColor, IColor strokeColor, double lineWidth){
	ps.println("0 A"); // lock
	if(fillColor!=null) ps.println("0 O"); // overprint color
	if(strokeColor!=null) ps.println("0 R"); // overprint color
	
	double opacity=1.0;
	
	if(fillColor!=null){
	    ps.println( f.format((double)fillColor.getRed()/255) + " " +
			f.format((double)fillColor.getGreen()/255) + " " +
			f.format((double)fillColor.getBlue()/255) + " " +
			"Xa");
	    opacity = (double)fillColor.getAlpha()/255;
	}
	if(strokeColor!=null){
	    ps.println( f.format((double)strokeColor.getRed()/255) + " " +
			f.format((double)strokeColor.getGreen()/255) + " " +
			f.format((double)strokeColor.getBlue()/255) + " " +
			"XA");
	    if(fillColor==null) opacity = (double)strokeColor.getAlpha()/255;
	}
	ps.println("0 "+f.format(opacity)+" 0 0 0 Xy");
	ps.println("0 J 0 j "+f.format(lineWidth)+" w 4 M []0 d");
    }

    
    public static ArrayList<ArrayList<IVec2>> convertTo2DPoints(ICurveI curve,double scale, IView view){
	IVec2[] pts=null;
	boolean inscreen=false;
	
	if(curve.deg()==1 && (!(curve instanceof ITrimCurveI) ||
			      (curve instanceof ITrimCurveI) && ((ITrimCurveI)curve).surface().isFlat()) ){
	    
	    pts = new IVec2[curve.cpNum()];
	    for(int i=0; i<pts.length; i++){
		pts[i] = new IVec2();
		boolean flag = view.convert(curve.cp(i), pts[i]);
		if(!flag){ pts[i] = null; }
		if(i>0 && pts[i-1]!=null && pts[i]!=null) inscreen=true;
	    }
	}
	
	/*
	if(curve.deg()>1 || curve instanceof ITrimCurveI && !((ITrimCurveI)curve).surface().isFlat()){
	    boolean flag=false;
	    pts = new IVec2[curve.samplePts.length*3-2];
	    for(int i=0; i<curve.samplePts.length; i++){
		pts[3*i] = new IVec2();
		flag = view.convert(curve.samplePts[i], pts[3*i]);
		if(!flag) pts[3*i]=null;
		if(i>0){
		    pts[3*i-2] = new IVec2();
		    pts[3*i-1] = new IVec2();
		    flag = view.convert(curve.sampleDir1[i-1], pts[3*i-2]);
		    if(!flag) pts[3*i-2]=null;
		    flag = view.convert(curve.sampleDir2[i-1], pts[3*i-1]);
		    if(!flag) pts[3*i-1]=null;
		    if(pts[3*i-3]!=null && pts[3*i-2]!=null &&
		       pts[3*i-1]!=null && pts[3*i]!=null){ inscreen=true; }
		}
	    }
	}
	else{
	    pts = new IVec2[curve.samplePts.length];
	    for(int i=0; i<curve.samplePts.length; i++){
		pts[i] = IGView.view.convert(curve.samplePts[i]);
		if(i>0 && pts[i-1]!=null && pts[i]!=null) inscreen=true;
	    }
	}
	*/
	
	if(!inscreen) return null;
	for(int i=0; i<pts.length; i++) if(pts[i]!=null) convertCoordinates(pts[i], scale, view);
	
	/*
	if(curve.deg()>1||
	   curve.deg()>1&& // is this OK? added in 20101210
	   curve instanceof ITrimCurveI &&
	   !((ITrimCurveI)curve).straightOnEdge) return createBezierPointArrayList(pts);
	*/
	
	return createPolylinePointArrayList(pts);
    }
    
    
    public static IVec2 convertTo2DPoint(IVecI pt,double scale, IView view){
	IVec2 pt2=new IVec2();
	boolean inscreen=false;
	
	boolean flag = view.convert(pt, pt2);
	if(!flag) return null;
	
	convertCoordinates(pt2, scale, view);
	return pt2;
    }
    
    
    public static void writePoint(PrintStream ps, IVecI point, double scale, IView view){
	IVec2 pt2=convertTo2DPoint(point,scale,view);
	if(pt2==null) return;
	
	double lineWeight = defaultPointWeight;
	IColor color = null;
	if(point instanceof IObject) color = ((IObject)point).clr();
	
	writePaintStyle(ps,null,color,lineWeight,CapType.Round,JoinType.Round);
	writePointPath(ps,pt2);
	ps.println("S"); // open curve without filling
    }
    
    
    
    public static void writeNurbsCurve(PrintStream ps, ICurveI curve, double scale, IView view){
	//if(IGNurbsElement.saveMemoryMode) curve.updateGeometry();
	
	ArrayList<ArrayList<IVec2>> pts=convertTo2DPoints(curve,scale,view);
	if(pts==null) return;
	
	final boolean fillClosedCurve=true; // false;
	final boolean useCurveColor=true; //false;
	
	double lineWeight = defaultLineWeight;
	
	IColor color = null;
	if(curve instanceof IObject) color = ((IObject)curve).clr();
	
	if(fillClosedCurve && curve.isClosed()){
	    
	    writePaintStyle(ps,color,null,lineWeight);
	    
	    /*
	    if(useCurveColor)
		writePaintStyle(ps,curve.getColor(),null,lineWeight);
	    else{
		IGShader shader = new IGShader();
		IVec normal = curve.getPlanarNormal();
		if(IVec.innerProduct(IGView.view.getDirection(),normal)>0) normal.negate();
		Color color = shader.getShadingColor(new Color(128,128,128),normal);
		writePaintStyle(ps,color,null,lineWeight);
	    }
	    */
	}
	else{
	    writePaintStyle(ps,null,color,lineWeight);
	}
	
	writePolylinePaths(ps,pts,true);
	
	/*
	if(curve.deg()>1||curve instanceof ITrimCurveI && !((ITrimCurveI)curve).surface().isFlat())
	    writeBezierCurvePaths(ps,pts,true);
	else writePolylinePaths(ps,pts,true);
	*/
	
	if(fillClosedCurve && curve.isClosed()){
	    ps.println("f"); // closed curve with filling
	}
	else{
	    ps.println("S"); // open curve without filling
	}
	//if(IGNurbsElement.saveMemoryMode) curve.clearGeometry();
    }
    
    public static void startCompoundPath(PrintStream ps){ ps.println("*u"); }
    public static void endCompoundPath(PrintStream ps){ ps.println("*U"); }

    /*
    public static void writeTrimLoops(PrintStream ps, ISurfaceI surf, double scale){
	
	ps.println("0 D");
	ps.println("1 XR"); // 1=even-odd; (0=non-zero is curve-direction-sensitive)
	
	startCompoundPath(ps);
	
	for(int i=0; surf.outerTrimLoop!=null && i<surf.outerTrimLoop.size(); i++){
	    //ps.println("0 0 Xd");
	    //ps.println("6 () XW");
	    
	    writeTrimLoop(ps,surf.outerTrimLoop.get(i),scale);
	    //ps.println("s"); // open curve without filling // ?
	    ps.println("h");
	    ps.println("W");
	    //ps.println("F"); // ? F ? n?
	    ps.println("n"); // ?
	}
	
	for(int i=0; surf.innerTrimLoop!=null && i<surf.innerTrimLoop.size(); i++){
	    // need compound path
	    
	    //writePaintStyle(ps,null,Color.red,defaultLineWeight);
	    writeTrimLoop(ps,surf.innerTrimLoop.get(i),scale);
	    //ps.println("s"); // open curve without filling // ?
	    
	    ps.println("h");
	    ps.println("W");
	    ps.println("n");
	    
	}
	endCompoundPath(ps);
	//ps.println("f"); // closed curve with filling
    }
    
    
    public static void writeTrimLoop(PrintStream ps,ArrayList<ITrimCurveI> loop, double scale, IView view){
	
	for(int i=0; i<loop.size(); i++){
	    ITrimCurveI curve = loop.get(i);
	    ArrayList<ArrayList<IVec2>> pts=convertTo2DPoints(curve,scale,view);
	    if(pts!=null){
		if(curve.deg()==1&&curve.straightOnEdge) writePolylinePaths(ps,pts,i==0?true:false);
		else writeBezierCurvePaths(ps,pts,i==0?true:false);
	    }
	}
	
    }
    
    public static void writeMesh(PrintStream ps, IVec[][] pts, Color[][] colors,
				 double opacity, double scale, IView view){
	
	// pts has to include handles
	if(pts.length%3!=1 || pts[0].length%3!=1 ){
	    IOut.err("pts doesn't include handle points");
	}
	
	int xnum = (pts.length-1)/3;
	int ynum = (pts[0].length-1)/3;
	
	if(colors.length!=xnum+1 || colors[0].length!=ynum+1){
	    IG.err("color size doesn't match with pts");
	}
	
	IVec2[][] pts2 = new IVec2[pts.length][pts[0].length];
	
	for(int i=0; i<pts.length; i++){
	    for(int j=0; j<pts[0].length; j++){
		pts2[i][j] = new IVec2();
		if(!view.convert(pts[i][j], pts2[i][j])) return; // don't write
		convertCoordinates(pts2[i][j],scale,view);
	    }
	}
	
	ps.println("0 Xw");
	ps.println("0 "+f.format(opacity)+" 0 0 0 Xy");
	ps.println("/Mesh X!");
	ps.println("%_1 /Version X#");
	ps.println("%_/Cartesian /Type X#");
	ps.println("%_["+String.valueOf(xnum)+" "+String.valueOf(ynum)+"] /Size X#");
	ps.println("%_/Data X#");
	
	for(int i=0; i<xnum; i++){
	    for(int j=0; j<ynum; j++){
		ps.println("%_["+String.valueOf(i)+" "+String.valueOf(j)+"] /P X#");
		if(i==0&&j==0) ps.println("%_/DeviceRGB /CS X#");
		
		ps.println("%_["+
			   f.format((double)colors[i][j].getRed()/255)+" "+
			   f.format((double)colors[i][j].getGreen()/255)+" "+
			   f.format((double)colors[i][j].getBlue()/255)+" "+
			   f.format(pts[3*i][3*j].x) + " "+ f.format(pts[3*i][3*j].y) + " "+
			   f.format(pts[3*i+1][3*j].x) + " "+ f.format(pts[3*i+1][3*j].y) + " "+ "1" + " "+
			   f.format(pts[3*i][3*j+1].x) + " "+ f.format(pts[3*i][3*j+1].y) + " "+ "1" +
			   "] /N X#");
		
		ps.println("%_["+
			   f.format((double)colors[i+1][j].getRed()/255)+" "+
			   f.format((double)colors[i+1][j].getGreen()/255)+" "+
			   f.format((double)colors[i+1][j].getBlue()/255)+" "+
			   f.format(pts[3*i+3][3*j].x) + " "+ f.format(pts[3*i+3][3*j].y) + " "+
			   f.format(pts[3*i+2][3*j].x) + " "+ f.format(pts[3*i+2][3*j].y) + " "+ "1" + " "+
			   f.format(pts[3*i+3][3*j+1].x) + " "+ f.format(pts[3*i+3][3*j+1].y) + " "+ "1" +
			   "] /N X#");
		
		ps.println("%_["+
			   f.format((double)colors[i][j+1].getRed()/255)+" "+
			   f.format((double)colors[i][j+1].getGreen()/255)+" "+
			   f.format((double)colors[i][j+1].getBlue()/255)+" "+
			   f.format(pts[3*i][3*j+3].x) + " "+ f.format(pts[3*i][3*j+3].y) + " "+
			   f.format(pts[3*i+1][3*j+3].x) + " "+ f.format(pts[3*i+1][3*j+3].y) + " "+ "1" + " "+
			   f.format(pts[3*i][3*j+2].x) + " "+ f.format(pts[3*i][3*j+2].y) + " "+ "1" +
			   "] /N X#");
		
		ps.println("%_["+
			   f.format((double)colors[i+1][j+1].getRed()/255)+" "+
			   f.format((double)colors[i+1][j+1].getGreen()/255)+" "+
			   f.format((double)colors[i+1][j+1].getBlue()/255)+" "+
			   f.format(pts[3*i+3][3*j+3].x) + " "+ f.format(pts[3*i+3][3*j+3].y) + " "+
			   f.format(pts[3*i+2][3*j+3].x) + " "+ f.format(pts[3*i+2][3*j+3].y) + " "+ "1" + " "+
			   f.format(pts[3*i+3][3*j+2].x) + " "+ f.format(pts[3*i+3][3*j+2].y) + " "+ "1" +
			   "] /N X#");
		
		ps.println("%_/E X#");
	    }
	}
	ps.println("/End X!");
    }
    
    
    public static void writeMesh(PrintStream ps, IGNurbsSurfaceGraphic.Subsurface surf, double scale){
	IVec[][] pts = surf.getOutline();
	IVec2[][] pts2 = new IVec2[pts.length][];
	
	Color[] colors = new Color[4];
	colors[0] = surf.getColor(0,0);
	colors[1] = surf.getColor(1,0);
	colors[2] = surf.getColor(1,1);
	colors[3] = surf.getColor(0,1);
	
	//double opacity = (double)surf.opacity/255;
	
	double opacity=0;
	for(int i=0; i<colors.length; i++){
	    opacity+=colors[i].getAlpha();
	}
	opacity/=colors.length;
	opacity/=255;
	
	
	if(pts.length!=4)
	    IG.err(" point size is invalid");
	
	for(int i=0; i<pts.length; i++){
	    pts2[i] = new IVec2[pts[i].length];
	    for(int j=0; j<pts[i].length; j++){
		pts2[i][j] = IGView.view.convert(pts[i][j]);
		if(pts2[i][j]==null) return; // don't write
		convertCoordinates(pts2[i][j],scale);
	    }
	}
	
	ps.println("0 Xw");
	//ps.println("0 "+String.valueOf(opacity)+" 0 0 0 Xy");
	
	ps.println("u"); //
	
	ps.println("/Mesh X!");
	ps.println("%_1 /Version X#");
	ps.println("%_/Cartesian /Type X#");
	ps.println("%_[1 1] /Size X#");
	ps.println("%_/Data X#");
	
	ps.println("%_[0 0] /P X#");
	ps.println("%_/DeviceRGB /CS X#");
	
	int num = pts2.length;
	for(int i=0; i<num; i++){
	    if(i==0||i==num-1){ // somehow, need to flip order at the begining and the end
		for(int j=(pts2[(i+num-1)%num].length-1)/3 -1; j>=1; j--)
		    ps.println("%_["+
			       f.format(pts2[(i+num-1)%num][j*3].x)+" "+f.format(pts2[(i+num-1)%num][j*3].y)+" "+
			       f.format(pts2[(i+num-1)%num][j*3+1].x)+" "+f.format(pts2[(i+num-1)%num][j*3+1].y)+" "+
			       f.format(pts2[(i+num-1)%num][j*3-1].x)+" "+f.format(pts2[(i+num-1)%num][j*3-1].y)+" 1"+
			       "] /A X#");
	    }
	    else{
		for(int j=1; j<(pts2[(i+num-1)%num].length-1)/3; j++)
		    ps.println("%_["+
			       f.format(pts2[(i+num-1)%num][j*3].x)+" "+f.format(pts2[(i+num-1)%num][j*3].y)+" "+
			       f.format(pts2[(i+num-1)%num][j*3-1].x)+" "+f.format(pts2[(i+num-1)%num][j*3-1].y)+" "+
			       f.format(pts2[(i+num-1)%num][j*3+1].x)+" "+f.format(pts2[(i+num-1)%num][j*3+1].y)+" 1"+
			       "] /A X#");
	    }
	    
	    ps.println("%_["+
		       f.format((double)colors[i].getRed()/255)+" "+
		       f.format((double)colors[i].getGreen()/255)+" "+
		       f.format((double)colors[i].getBlue()/255)+" "+
		       f.format(pts2[i][0].x)+" "+f.format(pts2[i][0].y)+" "+
		       f.format(pts2[i][1].x)+" "+f.format(pts2[i][1].y)+" "+"1"+" "+
		       f.format(pts2[(i+num-1)%num][pts2[(i+num-1)%num].length-2].x)+" "+
		       f.format(pts2[(i+num-1)%num][pts2[(i+num-1)%num].length-2].y)+" "+"1"+
		       "] /N X#");
	}
	ps.println("%_/E X#");
	ps.println("/End X!");
	
	ps.println("U"); // 
	//ps.println("0 "+String.valueOf(opacity)+" 0 0 0 Xy"); //
	ps.println("0 "+f.format(opacity)+" 0 2 0 Xy"); //
	ps.println("0 0 Xd"); //
	ps.println("6 () XW"); //
	
	
    }
    
    public static void writeNurbsSurface(PrintStream ps,
					 IGNurbsSurfaceGraphic surface,
					 double scale){
	
	if(IGNurbsElement.saveMemoryMode) surface.updateGeometry();
	
	//ps.println("0 Xw");
	//ps.println("0 "+0.45+" 0 0 0 Xy");
	//ps.println("u");
	
	//if(surface.hasTrimLoop()) startMask(ps);
	if(surface.hasTrimLoop()) startLayer(ps,"trimmed surface",0,true);
	//if(surface.subsurfaces!=null)
	if(surface.subsurfaces!=null && !surface.hasTrimLoop()) // added 2010/07/13
	    for(int j=0; j<surface.subsurfaces.size(); j++)
		if(writeSubsurfaceAsMesh) writeMesh(ps, surface.subsurfaces.get(j), scale);
		else writeNurbsSubsurfaceOutline(ps, surface.subsurfaces.get(j), scale);
	else if(!surface.hasTrimLoop()){ // no trim loop, no subsurface
	    writeNurbsSurfaceOutline(ps, surface, scale);
	}
	if(surface.hasTrimLoop()){
	    
	    writePaintStyle(ps, surface.getColor(), null, 0); //
	    
	    writeTrimLoops(ps,surface,scale);
	    //endMask(ps);
	    endLayer(ps);
	}
	//ps.println("U"); //
	
	if(surface.getStrokeColor()!=null){
	    if(surface.hasTrimLoop()){
		
		for(int i=0;surface.outerTrimLoop!=null&&i<surface.outerTrimLoop.size(); i++){
		    //ps.println("0 0 Xd");
		    //ps.println("6 () XW");
		    //writePaintStyle(ps,null,Color.red,defaultLineWeight);
		    
		    writePaintStyle(ps,null,surface.getStrokeColor(),defaultLineWeight);
		    
		    writeTrimLoop(ps,surface.outerTrimLoop.get(i),scale);
		    ps.println("s"); // open curve without filling // ?
		}
		
	    }
	    else if(writeSubsurfaceAsMesh&&writeOutlineWhenMesh) writeNurbsSurfaceOutline(ps,surface,scale); 
	}
	
	//if(IGNurbsElement.saveMemoryMode) surface.clearGeometry();
    }
    
    public static void writeNurbsSurfaceOutline(PrintStream ps, ISurfaceI surface,
						double scale){

	writeNurbsSurfaceOutline(ps, surface.getOutline(),
				 surface.getUDegree(),
				 surface.getVDegree(),
				 surface.getColor(),
				 surface.getStrokeColor(),
				 defaultLineWeight,
				 scale);
    }
    
    public static void writeNurbsSubsurfaceOutline(PrintStream ps,
						   ISurfaceI.Subsurface subsurface,
						   double scale){
	writeNurbsSurfaceOutline(ps, subsurface.getOutline(),
				 subsurface.getUDegree(),
				 subsurface.getVDegree(),
				 subsurface.getColor(),
				 subsurface.getStrokeColor(),
				 defaultLineWeight,
				 scale);
	
    }
    */
    
    /*
    public static void writeNurbsSurfaceOutline(PrintStream ps,
						IVec[][] outlinePts,
						int udegree,
						int vdegree, 
						Color fillColor,
						Color strokeColor,
						double lineWeight,
						double scale){
	
	IVec[][] pts = outlinePts;
	IVec2[][] pts2 = new IVec2[pts.length][];
	
	for(int i=0; i<pts.length; i++){
	    pts2[i] = new IVec2[pts[i].length];
	    for(int j=0; j<pts[i].length; j++){
		pts2[i][j] = IGView.view.convert(pts[i][j]);
		if(pts2[i][j]==null) return; // don't write
		convertCoordinates(pts2[i][j],scale);
	    }
	}
	
	ArrayList<IVec2>[] pts3 = new ArrayList[pts2.length];
	for(int i=0; i<pts2.length; i++){
	    pts3[i] = new ArrayList();
	    for(int j=0; j<pts2[i].length; j++) pts3[i].add(pts2[i][j]);
	}
	
	writePaintStyle(ps,fillColor,strokeColor,lineWeight);
	
	if(udegree>1){ writeBezierCurvePath(ps,pts3[0],true); }
	else{ writePolylinePath(ps,pts3[0],true); }
	
	if(vdegree>1){ writeBezierCurvePath(ps,pts3[1],false); }
	else{ writePolylinePath(ps,pts3[1],false); }
	
	if(udegree>1){ writeBezierCurvePath(ps,pts3[2],false); }
	else{ writePolylinePath(ps,pts3[2],false); }
	
	if(vdegree>1){ writeBezierCurvePath(ps,pts3[3],false); }
	else{ writePolylinePath(ps,pts3[3],false); }
	
	if( fillColor!=null && strokeColor!=null ){
	    ps.println("b"); // closed curve with filling & stroke
	}
	else if( fillColor!=null && strokeColor==null ){
	    ps.println("f"); // closed curve with filling
	}
	else if( fillColor==null && strokeColor!=null ){
	    ps.println("s"); // closed curve with stroke
	}
	else{
	    ps.println("n"); // closed curve without filling nor stroke
	}
	
    }
    */
    
    /*
    public static void writeImagePlane(PrintStream ps,
				       IGImagePlane imagePlane, 
				       double scale){
	
	final boolean ignoreTrimLoop = false; //true; //false;
	
	
	IVec2 pt = imagePlane.pt2.duplicate();
	IVec2 w = imagePlane.w2.duplicate();
	IVec2 h = imagePlane.h2.duplicate();
	
	w.add(pt);
	h.add(pt);
	
	convertCoordinates(pt, scale);
	convertCoordinates(w, scale);
	convertCoordinates(h, scale);
	
	w.sub(pt);
	h.sub(pt);
	
	w.div(imagePlane.imageWidth); // width vector per pixel
	h.div(-imagePlane.imageHeight); // height vector per pixel
	
	if(!ignoreTrimLoop&&imagePlane.hasTrimLoop()){
	    //startLayer(ps,"trimmed surface0",0,true);
	    
	    //startLayer(ps,"trimmed surface",0,true);
	    
	    startMask(ps);
	    
	    writePaintStyle(ps, imagePlane.getColor(), null, 0); //
	    writeTrimLoops(ps,imagePlane,scale);
	    
	    //startGroup(ps);
	}
	
	writeImage(ps, imagePlane.image, imagePlane.imageWidth, imagePlane.imageHeight, pt, w, h);
	
	if(!ignoreTrimLoop&&imagePlane.hasTrimLoop()){
	    //writePaintStyle(ps, imagePlane.getColor(), null, 0); //
	    //writeTrimLoops(ps,imagePlane,scale);
	    
	    //endGroup(ps);
	    
	    endMask(ps);
	    //endLayer(ps);
	    //endLayer(ps);
	}
    }

    public static void writeImage(PrintStream ps,
				  Image image,
				  int width,
				  int height,
				  IVec2 pt,
				  IVec2 xvec,
				  IVec2 yvec){
	
	
	int w = image.getWidth(AppletBase.currentApplet);
	int h = image.getHeight(AppletBase.currentApplet);
	
	ps.println("%AI5_File:");
	ps.println("%AI5_BeginRaster");
	ps.println("[ " +
		   f.format(xvec.x) + " " + f.format(xvec.y) + " " +
		   f.format(yvec.x) + " " + f.format(yvec.y) + " " +
		   f.format(pt.x) + " " + f.format(pt.y) + " ] " +
		   "0" + " " +  "0" + " " + String.valueOf(w) + " " + String.valueOf(h) + " " +
		   String.valueOf(w) + " " + String.valueOf(h) + " " +
		   "8" + " " + // 8bit per color
		   "3" + " " + // rgb
		   "0" + " " + // alpha channel count
		   "0" + " " + // reserved
		   "0" + " " + // bin-ascii: ascii
		   "0" + " " + // non image mask
		   "XI");
	
	int[] pix = new int[w*h];
	PixelGrabber pg = new PixelGrabber(image, 0, 0, -1, -1, pix, 0, w);
	if(pg!=null) try{ pg.grabPixels(); } catch(Exception e){ e.printStackTrace(); }
	
	int maxRGBInLine = 10; //60/6;
	boolean newline=true;
	int rgbCount=0;
	
	for(int i=0; i<h; i++){
	    for(int j=0; j<w; j++){
		
		if(newline){ ps.print("%"); newline=false; }
		
		String rgbstr = getRGBString(pix, j, i, w);
		ps.print(rgbstr);
		rgbCount++;
		if(rgbCount>=maxRGBInLine){
		    ps.println();
		    rgbCount=0;
		    newline=true;
		}
	    }
	}
	if(!newline) ps.println();
	
	ps.println("%AI5_EndRaster");
	
	//ps.println("F"); // fill
	ps.println("N"); // no fill
	//ps.println("n"); // no fill
	
    }
    */
    
    public static String getRGBString(int[] pixel, int x, int y, int w){
	int rgba = pixel[ ( w * ( y  )) + x ] ;
	int a = ( rgba >> 24 ) & 0xff ;
	int r = ( rgba >> 16 ) & 0xff ;
	int g = ( rgba >> 8 ) & 0xff ;
	int b = ( rgba ) & 0xff ;
	return getByteString(r)+getByteString(g)+getByteString(b);
    }
    
    public static String getByteString(int b){
	int b1 = b>>4 & 0x0f;
	int b2 = b & 0x0f;
	return String.valueOf(getHex(b1)) + String.valueOf(getHex(b2));
    }
    
    public static char getHex(int h){
	if(h>=0 && h<=9) return (char)('0' + h);
	switch(h){
	case 10: return 'A';
	case 11: return 'B';
	case 12: return 'C';
	case 13: return 'D';
	case 14: return 'E';
	case 15: return 'F';
	}
	return '\0'; // 
    }
    
    public static void startLayer(PrintStream ps, String layerName, int layerColorIndex,
			   boolean enableMask){
	//ps.println("1 1 1 1 0 0 "+String.valueOf(layerColorIndex)+" 0 0 0 Lb");
	//ps.println("("+layerName+") Ln");
	String mask = "0";
	if(enableMask) mask="1";
	
	ps.println("1 1 1 1 0 0 1 "+String.valueOf(layerColorIndex)+" 0 0 0 0 50 "+mask+" Lb");
	ps.println("("+layerName+") Ln");
    }
    public static void endLayer(PrintStream ps){ ps.println("LB"); }
    
    public static void startMask(PrintStream ps){ ps.println("q"); }
    public static void endMask(PrintStream ps){ ps.println("Q"); }
    
    public static void startGroup(PrintStream ps){ ps.println("u"); }
    public static void endGroup(PrintStream ps){ ps.println("U"); }

    /*
    public static void printPolylineParameter(PrintStream ps, ArrayList<IVec2> pts, boolean first){
	IVec2 pt;
	int i=0;
	if(!first){ i++; }
	
	i=0;
	if(!first){ i++; }
	
	for(; i<pts.size(); i++){
	    pt = pts.get(i);
	    if( !Double.isNaN(pt.x) && !Double.isNaN(pt.x)  &&
		!Double.isInfinite(pt.x) && !Double.isInfinite(pt.x)
		){
		ps.print(f.format(pt.x) + " "+ f.format(pt.y) + " ");
		if(i==0){ ps.println("m"); }
		else if(i==(pts.size()-1)){ ps.println("L"); }
		//else if(i==(pts.size()-1)){ ps.println("l"); }
		else{ ps.println("l"); }
	    }
	    else IGConsole.println("PSExporter.printPolylineParameter: NaN: pts["+i+"]=<"+
				   pt.x + "," + pt.y+">");
	}
    }
    
    public static void printBezierParameter(PrintStream ps, ArrayList pts, boolean first){
	IVec2 pt;
	
	int i=0;
	if(!first){ i++; }
	
	if( pts.size()%3==1 ){
	    
	    i=0;
	    if(first){
		pt = pts.get(0);
		ps.print(f.format(pt.x) + " "+ f.format(pt.y) + " ");
		ps.println("m");
	    }
	    
	    int lineNum = (int)(pts.size()/3);
	    for(int l=0; l<lineNum; l++){
		IVec2 pt1, pt2, pt3;
		
		pt1 = pts.get(l*3+1);
		pt2 = pts.get(l*3+2);
		pt3 = pts.get(l*3+3);
		
		if( !Double.isNaN(pt1.x) && !Double.isNaN(pt1.y) &&
		    !Double.isNaN(pt2.x) && !Double.isNaN(pt2.y) &&
		    !Double.isNaN(pt3.x) && !Double.isNaN(pt3.y) &&
		    !Double.isInfinite(pt1.x) && !Double.isInfinite(pt1.y) &&
		    !Double.isInfinite(pt2.x) && !Double.isInfinite(pt2.y) &&
		    !Double.isInfinite(pt3.x) && !Double.isInfinite(pt3.y)
		    ){
		    
		    ps.print(f.format(pt1.x) + " "+ f.format(pt1.y) + " ");
		    ps.print(f.format(pt2.x) + " "+ f.format(pt2.y) + " ");
		    ps.print(f.format(pt3.x) + " "+ f.format(pt3.y) + " ");
		    
		    if(i==(pts.size()-1)){ ps.println("C"); }
		    else{ ps.println("c"); }
		    
		}
		else{
		    IG.print("PSExporter.printPolylineParameter: NaN: ");
		    IG.print(pt1.x + " "+ pt1.y + " ");
		    IG.print(pt2.x + " "+ pt2.y + " ");
		    IG.println(pt3.x + " "+ pt3.y + " ");
		}
	    }
	}
	else{
	    IGConsole.println("PSExporter.printPolylineParameter: wrong number of points : "+
			      pts.size()); //
	}
    }
    */
    
    
    public static void writeFooter(PrintStream ps){
	
	ps.println("%%PageTrailer");
	ps.println("gsave annotatepage grestore showpage");
	ps.println("%%Trailer");
	ps.println("Adobe_IllustratorA_AI3 /terminate get exec");
	ps.println("Adobe_typography_AI3 /terminate get exec");
	ps.println("Adobe_customcolor /terminate get exec");
	ps.println("Adobe_cshow /terminate get exec");
	ps.println("Adobe_cmykcolor /terminate get exec");
	ps.println("Adobe_packedarray /terminate get exec");
	ps.println("%%EOF");
	
    }
    
    
    static public IVec2 getIntersection(IVec2 base1, IVec2 dir1,
						IVec2 base2, IVec2 dir2){
	double a =
	    (dir1.y*(base2.x-base1.x) - dir1.x*(base2.y-base1.y))/
	    (dir1.x*dir2.y - dir2.x*dir1.y);
	
	return new IVec2( (double)(a*dir2.x + base2.x),
				  (double)(a*dir2.y + base2.y) );
	
    }
    
    /*
    static public void writeText(PrintStream ps, String text, double x, double y, double z,
				 double scale ){
	
	IVec p = new IVec((double)x, (double)y, (double)z);
	IVec2 pt = new IVec2();
	
	if(IGView.view.convert(p,pt)){
	    convertCoordinates(pt,scale);
	    
	    ps.println("0 To");
	    ps.println("1 0 0 1 "+f.format(pt.x)+" "+f.format(pt.y)+" 0 Tp");
	    ps.println("0 Tv");
	    ps.println("TP");
	    ps.println("0 Tr");
	    ps.println("0 O");
	    ps.println("0 0 0 1 k");
	    ps.println("0 J 0 j 1 w 4 M []0 d");
	    ps.println("0 XR");
	    
	    ps.println("/_ArialMT 12 10.2958 -3.8965 Tf"); //
	    
	    ps.println("100 100 Tz");
	    ps.println("0 Tt");
	    ps.println("0 TV");
	    ps.println("0 Tc");
	    
	    ps.print("(");
	    for(int i=0; i<text.length(); i++) ps.print("\\"+getOctaString(text.charAt(i)));
	    ps.println(") Tx 1 0 Tk");
	    ps.println("TO");
	}
    }
    
    
    
    static public void writeText2D(PrintStream ps,
				   String text, double x, double y,
				   double scale){
	
	//IVec p = new IVec((double)x, (double)y, (double)z);
	
	IVec2 pt = new IVec2(x,y);
	
	convertCoordinates(pt,scale);
		
	ps.println("0 To");
	ps.println("1 0 0 1 "+f.format(pt.x)+" "+f.format(pt.y)+" 0 Tp");
	ps.println("0 Tv");
	ps.println("TP");
	ps.println("0 Tr");
	ps.println("0 O");
	ps.println("0 0 0 1 k");
	ps.println("0 J 0 j 1 w 4 M []0 d");
	ps.println("0 XR");
	
	//ps.println("/_Myraid-Roman 12 10.0439 -3 Tf");
	//ps.println("/_Myraid-Roman 12 0 0 Tf");
	ps.println("/_ArialMT 12 10.2958 -3.8965 Tf"); // font size 12
	
	ps.println("100 100 Tz");
	ps.println("0 Tt");
	ps.println("0 TV");
	ps.println("0 Tc");
	
	ps.print("(");
	for(int i=0; i<text.length(); i++) ps.print("\\"+getOctaString(text.charAt(i)));
	ps.println(") Tx 1 0 Tk");
	
	ps.println("TO");
    }
    


    static public void writeText3D(PrintStream ps, String text,
				   IVec pos,
				   IVec horizontalDir,
				   IVec verticalDir,
				   Color textColor,
				   double scale ){
	
	IVec2 pos2 = new IVec2();
	IVec2 hdir2 = new IVec2();
	IVec2 vdir2 = new IVec2();
	
	if(!IGView.view.convert(pos,pos2)) return;
	if(!IGView.view.convert(IVec.add(pos,horizontalDir),hdir2)) return;
	if(!IGView.view.convert(IVec.add(pos,verticalDir),vdir2)) return;
	
	convertCoordinates(pos2,scale);
	convertCoordinates(hdir2,scale);
	convertCoordinates(vdir2,scale);
	
	hdir2.sub(pos2);
	vdir2.sub(pos2);
	
	
	ps.println("0 To");
	//ps.println("1 0 0 1 "+pt.x+" "+pt.y+" 0 Tp");
	ps.println(f.format(hdir2.x) + " " + f.format(hdir2.y) + " " +
		   f.format(vdir2.x) + " " + f.format(vdir2.y) + " " +
		   f.format(pos2.x) + " " + f.format(pos2.y) + " 0 Tp");
	ps.println("0 Tv");
	ps.println("TP");
	ps.println("0 Tr");
	// RGB Color
	//ps.println("
	
	ps.println( f.format((double)textColor.getRed()/255) + " " +
		    f.format((double)textColor.getGreen()/255) + " " +
		    f.format((double)textColor.getBlue()/255) + " " +
		    "Xa");
	
	//ps.println("0 O");
	//ps.println("0 0 0 1 k");
	//ps.println("0 J 0 j 1 w 4 M []0 d");
	//ps.println("0 XR");
	
	//ps.println("/_Myraid-Roman 12 10.0439 -3 Tf");
	//ps.println("/_Myraid-Roman 12 0 0 Tf");
	//ps.println("/_ArialMT 12 10.2958 -3.8965 Tf"); //
	ps.println("/_ArialMT 1 0 0 Tf"); //
	
	ps.println("100 100 Tz");
	ps.println("0 Tt");
	ps.println("0 TV");
	ps.println("0 Tc");
	
	ps.print("(");
	for(int i=0; i<text.length(); i++) ps.print("\\"+getOctaString(text.charAt(i)));
	ps.println(") Tx 1 0 Tk");
	ps.println("TO");
	
    }
    */
    
    static public String getOctaString(char c){
	int oct1 = c%8;
	int oct2 = (int)(c/8)%8;
	int oct3 = (int)(c/64)%8;
	return String.valueOf(oct3) + String.valueOf(oct2) + String.valueOf(oct1);
    }

    static public boolean write(File file, IServerI server, double scale){
	
	PrintStream ps = null;
	try{
	    //ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)));
	    ps = new PrintStream(file);
	}
	catch(IOException e){
	    e.printStackTrace();
	    return false;
	}
	
	write(ps, server.server().allObjects(), scale, server.server().ig.panel.currentPane().getView());
	ps.close();
	return true;
    }


    static public ArrayList<IObject> sortObjectsByView(ArrayList<IObject> objects, IView view){
	
	ArrayList<IGeometry> geoms = new ArrayList<IGeometry>();
	
	for(int i=0; i<objects.size(); i++){
	    if(objects.get(i) instanceof IGeometry){
		geoms.add((IGeometry)objects.get(i));
	    }
	}
	ISort.sort(geoms, new IViewSort(view));
	ArrayList<IObject> objects2 = new ArrayList<IObject>();
	for(int i=0; i<geoms.size(); i++){
	    objects2.add(geoms.get(i));
	}
	return objects2;
    }
    
    static public class IViewSort implements IComparator<IGeometry>{
	public IView view;
	IViewSort(IView v){ view = v; }
	public int compare(IGeometry o1, IGeometry o2){
	    IVec pos1 =view.convert(o1.center());
	    IVec pos2 =view.convert(o2.center());
	    
	    if(pos1.z < pos2.z){ return 1; }
	    if(pos1.z > pos2.z){ return -1; }
	    return 0;
	}
    }
    
    static public void write(PrintStream ps, ArrayList<IObject> objects, double scale, IView view){
	
	initFormat(); //
	
	writeHeader(ps);
        startLayer(ps,"default",0,false);
	
	final boolean sortObjectsByView = true;
	if(sortObjectsByView){
	    objects = sortObjectsByView(objects, view);
	}
	
	final boolean updateGeometry=true; //false; //
	
	for(int i=0; objects!=null && i<objects.size(); i++){
	    
	    if(i>0 && i%100==0){
		IOut.debug(0, "exporting "+i+"/"+objects.size());
	    }
	    
	    if( objects.get(i) instanceof ICurveI && !(objects.get(i) instanceof ITrimCurveI) ){
                ICurveI curve = (ICurveI)objects.get(i);
		//if(updateGeometry) curve.updateGeometry(); //
                writeNurbsCurve(ps, curve, scale, view); //
            }
	    else if( objects.get(i) instanceof IPoint){ // how about IPointAgent, etc
                IPoint point = (IPoint)objects.get(i);
                writePoint(ps, point, scale, view); //
            }
	    else if( objects.get(i) instanceof IPointR){ // how about IPointAgent, etc
                IPointR point = (IPointR)objects.get(i);
                writePoint(ps, point, scale, view); //
            }
	    
	    /*
            else if(elements.get(i) instanceof TextLabel){
                TextLabel tx = (TextLabel)elements.get(i);
                writeText(ps,tx.label,tx.x(),tx.y(),tx.z(),scale);
            }	    
            else if(elements.get(i) instanceof IGText){
                IGText tx = (IGText)elements.get(i);
		String txt = "";
		for(int k=0 ;k<tx.text.length; k++){ txt += tx.text[k]; txt += "\n"; }
		writeText(ps,txt,tx.pos.x, tx.pos.y, tx.pos.z, scale);
            }	    
            else if(elements.get(i) instanceof IGText3d){
                IGText3d tx = (IGText3d)elements.get(i);
		String txt = "";
		for(int k=0 ;k<tx.text.length; k++){ txt += tx.text[k]; txt += "\n"; }
		writeText3D(ps,txt,tx.pos,tx.hdir,tx.vdir,tx.getColor(),scale);
            }	    
            else if(elements.get(i) instanceof IGQuadMesh){
                IGQuadMesh om =(IGQuadMesh)elements.get(i);
                for(int j=0; j<om.vertices.size(); j++){
                    IGQuadMesh.Vertex v = (IGQuadMesh.Vertex)om.vertices.get(j);
                }
            }
            else if(elements.get(i) instanceof IGImagePlane){
                IGImagePlane img = (IGImagePlane)elements.get(i);
		if(updateGeometry) img.updateGeometry(); //
                writeImagePlane(ps,img,scale);
            }
            else if(elements.get(i) instanceof ISurfaceI){
                ISurfaceI surf = (ISurfaceI)elements.get(i);
		if(updateGeometry) surf.updateGeometry(); //
                writeNurbsSurface(ps,surf,scale);
            }
	    */
	}

	/*
	for(int i=0; elements2D!=null && i<elements2D.size(); i++){
            if(elements2D.get(i) instanceof TextLabel2D){
                TextLabel2D tx = (TextLabel2D)elements2D.get(i);
                writeText2D(ps,tx.label,tx.x,tx.y,scale);
            }
            else if(elements2D.get(i) instanceof IGPolygon2D){
                IGPolygon2D polygon = (IGPolygon2D)elements2D.get(i);
                IVec2 pts[] = new IVec2[polygon.getSize()];
                for(int j=0; j<polygon.getSize(); j++){
                    IVec2 pt = polygon.getPoint(j);
                    pts[j] = new IVec2(pt.x,pt.y);
                }
                PSExporter.writePolygon2D(ps,pts,polygon.color,null,scale,1.0);
            }
	}
	*/
	
        endLayer(ps);
        writeFooter(ps);
    }
    
    
    //public static void main(String[] args){}
    
    
}


