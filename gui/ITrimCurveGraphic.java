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

/**
   Class to convert trim curves to IPolyline and IPolyline2D.
   Used in ITrimLoopGraphic.
   
   @see ITrimLoopGraphic
   
   @author Satoru Sugihara
*/
public class ITrimCurveGraphic{
    
    public ITrimCurveI curve;
    public ISurfaceI surface;
    
    //IVec2[] uvpts=null;
    //IVec[] pts=null;
    
    public IPolyline2D polyline2=null;
    public IPolyline polyline=null;
    
    public ITrimCurveGraphic(ITrimCurveI crv){
	curve = crv;
	surface = curve.surface();
    }
    
    /**
       For creating curve without ITrimCurve (mainly for rectangular borders)
    */
    public ITrimCurveGraphic(ISurfaceI surf, IPolyline2D poly2){
	curve = null;
	surface = surf;
	polyline2 = poly2;
    }
    
    
    public IPolyline2D getPolyline2D(){
	//if(polyline2==null) setup2D();
	return polyline2;
    }
    
    
    public IPolyline getPolyline(int reso){
	if(polyline==null) setup3D(reso);
	return polyline;
    }
    
    /*
    public void setup2D(){
	setup2D(IConfig.surfaceTrimEdgeResolution);
    }
    */
    
    public void setup2D(int reso){
	//IOut.p("curve="+curve);
	//IOut.p("surface="+surface);
	if(curve.deg()==1 &&
	   // if surface is flat but skewed, line needs to be a curve
	   (surface.udeg()>1 || surface.vdeg()>1  ||
	    surface.udeg()==1 && surface.vdeg()==1 && 
	    surface.ucpNum()==2 && surface.vcpNum()==2 &&
	    Math.abs(surface.cp(0,0).dist(surface.cp(1,0))-surface.cp(0,1).dist(surface.cp(1,1)))<=IConfig.tolerance &&
	    Math.abs(surface.cp(0,0).dist(surface.cp(0,1))-surface.cp(1,0).dist(surface.cp(1,1)))<=IConfig.tolerance ) // this condition needs to be elaborated
	   ){
	    int num = curve.cpNum();
	    polyline2 = new IPolyline2D(num);
	    for(int i=0; i<num; i++){
		IVec pt = curve.cp(i).get();
		polyline2.set(i,  new IVec2(pt.x, pt.y));
	    }
	}
	else{
	    int epnum = curve.epNum();
	    polyline2 = new IPolyline2D((epnum-1)*reso+1);
	    for(int i=0; i<epnum; i++){
		for(int j=0; j<reso && i<(epnum-1) || j==0; j++){
		    //polyline2.set(i*reso+j, curve.pt2d(curve.u(i,(double)j/reso)));
		    IVec2 pt = curve.pt2d(curve.u(i,(double)j/reso)).get();
		    // sometimes, points could get outside of the unitized range
		    if(pt.x<0.) pt.x=0.; else if(pt.x>1.) pt.x=1.;
		    if(pt.y<0.) pt.y=0.; else if(pt.y>1.) pt.y=1.;
		    polyline2.set(i*reso+j, pt);
		}
	    }
	}
    }
    
    
    public void setup3D(int reso){
	if(polyline2==null) setup2D(reso);
	
	if(surface.udeg()==1 && surface.vdeg()==1 &&
	   // needs to be parallelogram
	   Math.abs(surface.cp(0,0).dist(surface.cp(1,0))-surface.cp(0,1).dist(surface.cp(1,1)))<=IConfig.tolerance &&
	   Math.abs(surface.cp(0,0).dist(surface.cp(0,1))-surface.cp(1,0).dist(surface.cp(1,1)))<=IConfig.tolerance &&
	   surface.ucpNum()==2 && surface.vcpNum()==2 && surface.isFlat()){
	    // if curve.deg()==1, uvpts.length == cpNum
	    polyline = new IPolyline(polyline2.num());
	    for(int i=0; i<polyline2.num(); i++)
		polyline.set(i, surface.pt(polyline2.get(i)));
	}
	else{
	    
	    ArrayList<IVec2> pts2 = new ArrayList<IVec2>();
	    // fill points
	    for(int i=0; i<polyline2.num()-1; i++){
		pts2.add(polyline2.get(i));
		IVec2[] fill = fillPointsBetween(surface,polyline2.get(i),polyline2.get(i+1));
		
		//if(fill!=null)IOut.p("fill point # = "+fill.length); //
		//else IOut.p("fill is null"); //
		
		for(int j=0; fill!=null && j<fill.length; j++) pts2.add(fill[j]);
		if(i==polyline2.num()-2) pts2.add(polyline2.get(i+1));
	    }
	    
	    // update polyline2
	    polyline2 = new IPolyline2D(pts2.toArray(new IVec2[pts2.size()]));
	    
	    polyline = new IPolyline(pts2.size());
	    for(int i=0; i<pts2.size(); i++) polyline.set(i, surface.pt(pts2.get(i)));
	    
	    /*
	    int reso = IConfig.surfaceTrimEdgeResolution;
	    if(curve.deg()==1){
		int epnum = curve.epNum();
		polyline = new IPolyline((epnum-1)*reso+1);
		for(int i=0; i<epnum; i++){
		    for(int j=0; j<reso && i<(epnum-1) || j==0; j++){
			polyline.set(i*reso+j, surface.pt(curve.pt2d(curve.u(i,(double)j/reso))));
		    }
		}
	    }
	    else{
		polyline = new IPolyline(polyline2.num());
		for(int i=0; i<polyline2.num(); i++)
		    polyline.set(i, surface.pt(polyline2.get(i)));
	    }
	    */
	    
	}
    }
    
    
    /**
       fill points to match with graphical resolution on the surface
    */
    public static IVec2[] fillPointsBetween(ISurfaceI surf, IVec2 pt1, IVec2 pt2){
	// check only u
	if(Math.abs(pt1.y-pt2.y)<IConfig.tolerance) return fillUBetween(surf,pt1,pt2);
	
	// check only v
	if(Math.abs(pt1.x-pt2.x)<IConfig.tolerance) return fillVBetween(surf,pt1,pt2);
	
	IVec2[] ufill = fillUBetween(surf,pt1,pt2);
	IVec2[] vfill = fillVBetween(surf,pt1,pt2);
	
	if(ufill==null && vfill==null) return null;
	if(ufill!=null && vfill==null) return ufill;
	if(ufill==null && vfill!=null) return vfill;
	if(ufill.length >= vfill.length) return ufill;
	return vfill;
    }
    
    
    public static IVec2[] fillUBetween(ISurfaceI surf, IVec2 pt1, IVec2 pt2){
	double[] fill = fillUBetween(surf, pt1.x, pt2.x);
	if(fill==null) return null;
	int num = fill.length;
	if(pt1.x > pt2.x){ // reverse the order
	    double[] tmp = new double[num];
	    for(int i=0; i<num; i++) tmp[i] = fill[num-1-i];
	    fill = tmp;
	}
	IVec2[] pts = new IVec2[num];
	for(int i=0; i<num; i++) pts[i] = pt1.sum(pt2, (fill[i]-pt1.x)/(pt2.x-pt1.x));
	return pts;
    }
    
    public static double[] fillUBetween(ISurfaceI surf, double u1, double u2){
	double min = Math.min(u1,u2);
	double max = Math.max(u1,u2);
	//ISurfaceI surf = surface;
	int idx = 0;
	for(; idx<surf.uepNum() && surf.u(idx,0)<min; idx++);
	idx--;
	
	if(idx<0) idx=0; // ?
	
	if(idx>=surf.uepNum()-1) return null; // nothing to fill
	if(!(surf.u(idx,0)<=min && surf.u(idx+1,0)>=min) ) return null;
	
	int reso =
	    (IConfig.isoparmResolution>0?IConfig.isoparmResolution:1)*IConfig.segmentResolution;
	
	ArrayList<Double> ulist = new ArrayList<Double>();
	for(; idx<surf.uepNum() && surf.u(idx,0)<max; idx++){
	    for(int j=0; surf.u(idx,(double)j/reso)<max &&
		    (surf.udeg()==1&&j==0 || surf.udeg()>1 && j<reso) ; j++){
		double u = surf.u(idx,(double)j/reso);
		if( u>min && u<max ) ulist.add(u);
	    }
	}
	if(ulist.size()==0) return null;
	double[] retval = new double[ulist.size()];
	for(int i=0; i<ulist.size(); i++) retval[i] = ulist.get(i);
	return retval;
    }
    
    
    public static IVec2[] fillVBetween(ISurfaceI surf, IVec2 pt1, IVec2 pt2){
	double[] fill = fillVBetween(surf,pt1.y, pt2.y);
	if(fill==null) return null;
	int num = fill.length;
	if(pt1.y > pt2.y){ // reverse the order
	    double[] tmp = new double[num];
	    for(int i=0; i<num; i++) tmp[i] = fill[num-1-i];
	    fill = tmp;
	}
	IVec2[] pts = new IVec2[num];
	for(int i=0; i<num; i++) pts[i] = pt1.sum(pt2, (fill[i]-pt1.y)/(pt2.y-pt1.y));
	return pts;
    }
    
    public static double[] fillVBetween(ISurfaceI surf, double v1, double v2){
	double min = Math.min(v1,v2);
	double max = Math.max(v1,v2);
	//ISurfaceI surf = surface;
	int idx = 0;
	for(; idx<surf.vepNum() && surf.v(idx,0)<min; idx++);
	idx--;
	
	if(idx<0) idx=0; //?
	
	if(idx>=surf.vepNum()-1){ return null; } // nothing to fill
	if(!(surf.v(idx,0)<=min && surf.v(idx+1,0)>=min) ){ return null; }
	
	int reso =
	    (IConfig.isoparmResolution>0?IConfig.isoparmResolution:1)*IConfig.segmentResolution;
	
	ArrayList<Double> vlist = new ArrayList<Double>();
	for(; idx<surf.vepNum() && surf.v(idx,0)<max; idx++){
	    for(int j=0; surf.v(idx,(double)j/reso)<max &&
		    ((surf.vdeg()==1&&j==0) || (surf.vdeg()>1&&j<reso)); j++){
		double v = surf.v(idx,(double)j/reso);
		//IOut.p("j="+j+", vdeg="+surf.vdeg()); //
		if( v>min && v<max ){
		    //IOut.p("v added ("+idx+","+j+"/"+reso+") :"+v); //
		    vlist.add(v);
		}
		//else IOut.p("v not added :"+v); //
	    }
	}
	
	if(vlist.size()==0) return null;
	double[] retval = new double[vlist.size()];
	for(int i=0; i<vlist.size(); i++) retval[i] = vlist.get(i);
	return retval;
    }
    
    
    /*
    public IVec2[] getUVPoints(){
	if(uvpts==null) createUVPoints();
	return uvpts;
    }
    
    public IVec2[] createUVPoints(){
	int reso = IConfig.isoparmResolution*IConfig.segmentResolution;
	if(curve.deg()==1){
	    int num = curve.cpNum();
	    uvpts = new IVec2[num];
	    for(int i=0; i<num; i++){
		IVec pt = curve.cp(i).get();
		uvpts[i] = new IVec2(pt.x, pt.y);
	    }
	}
	else{
	    int epnum = curve.epNum();
	    uvpts = new IVec2[ (epnum-1)*reso+1];
	    for(int i=0; i<epnum; i++){
		for(int j=0; j<reso && i<(epnum-1) || j==0; j++){
		    IVec pt = curve.pt(curve.u(i,(double)j/reso)).get();
		    uvpts[i] = new IVec2(pt.x,pt.y);
		}
	    }
	}
	return uvpts;
    }
    
    public IVec[] createPoints(){
	int reso = IConfig.isoparmResolution*IConfig.segmentResolution;
	if(uvpts==null) createUVPoints();

	if(curve.surface.udeg()==1 && curve.surface.vdeg()==1 &&
	   curve.surface.ucpNum()==1 && curve.surface.vcpNum()==1 &&
	   curve.surface.isFlat()){
	    // if curve.deg()==1, uvpts.length == cpNum
	    pts = new IVec[uvpts.length];
	    for(int i=0; i<uvpts.length; i++)
		pts[i] = curve.surface.pt(uvpts[i].x,uvpts[i].y).get();
	}
	else{
	    if(curve.deg()==1){
		int epnum = curve.epNum();
		pts = new IVec[(epnum-1)*reso+1];
		for(int i=0; i<epnum; i++){
		    for(int j=0; j<reso && i<(epnum-1) || j==0; j++){
			IVec pt = curve.pt(curve.u(i,(double)j/reso)).get();
			pts[i] = curve.surface.pt(pt.x,pt.y).get();
		    }
		}
	    }
	    else{
		pts = new IVec[uvpts.length];
		for(int i=0; i<uvpts.length; i++)
		    pts[i] = curve.surface.pt(uvpts[i].x,uvpts[i].y).get();
	    }
	}
	
	return pts;
    }
    */
    
}
