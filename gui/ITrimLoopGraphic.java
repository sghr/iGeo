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
   Class to convert loop of trim curves to IPolyline and IPolyline2D.
   Mainly used in ISurfaceGraphicFillGL and ISurfaceGraphicWireframeGL.
   
   @see ISurfaceGraphicFillGL
   @see ISurfaceGraphicWireframeGL   
   
   @author Satoru Sugihara
*/
public class ITrimLoopGraphic{
    public ISurfaceI surface;
    public ITrimCurveI[] curves;
    
    public IPolyline2D polyline2=null;
    public IPolyline polyline=null;

    public int resolution;
    
    public boolean isOuterTrim=true;
    
    public boolean reversed=false;
    
    
    public ITrimLoopGraphic(ITrimCurveI[] curves, boolean isOuter, int reso){
	this.curves=curves;
	surface = curves[0].surface();
	isOuterTrim = isOuter;
	resolution=reso;
    }
    public ITrimLoopGraphic(int sz, boolean isOuter, int reso){
	curves = new ITrimCurve[sz];
	isOuterTrim = isOuter;
	resolution=reso;
    }
    
    /**
       for generating rectangular boundary
    */
    public ITrimLoopGraphic(ISurfaceI surf){
	surface = surf;
    }
    
    public int num(){ return curves.length; }
    
    public void set(int i, ITrimCurveI crv){
	curves[i] = crv;
	surface = crv.surface();
    }
    
    public IPolyline2D getPolyline2D(){
	if(polyline2==null) setup2D();
	return polyline2;
    }
    
    public IPolyline getPolyline(){
	if(polyline==null) setup3D();
	return polyline;
    }
    
    public boolean reversed(){
	if(polyline2==null) setup2D();
	return reversed;
    }
    
    public void setup2D(){
	// making default rectangular border
	if(curves==null && surface!=null){
	    polyline2 = new IPolyline2D(4);
	    double umin=0., umax=1., vmin=0., vmax=1.;
	    /*
	    if(!IConfig.normalizeKnots){
		umin = surface.ustart(); umax = surface.uend();
		vmin = surface.vstart(); vmax = surface.vend();
	    }
	    */
	    polyline2.set(0,new IVec2(umin,vmin));
	    polyline2.set(1,new IVec2(umax,vmin));
	    polyline2.set(2,new IVec2(umax,vmax));
	    polyline2.set(3,new IVec2(umin,vmax));
	    polyline2.close();
	    return;
	}
	
	polyline2 = new IPolyline2D();
	for(int i=0; i<curves.length; i++){
	    ITrimCurveGraphic curvg = new ITrimCurveGraphic(curves[i]);
	    curvg.setup2D(resolution);
	    
	    if(i==0 ||
	       polyline2.get(polyline2.num()-1).dist(curvg.polyline2.get(0)) <
	       polyline2.get(polyline2.num()-1).dist(curvg.polyline2.get(curvg.polyline2.num()-1)))
		polyline2.append(curvg.polyline2);
	    else polyline2.append(curvg.polyline2,true); // reverse
	}
	
	polyline2.close();
	
	// remove very close points (risky if resolution is too big in core.IConfig)
	polyline2.removeDuplicatedPoints(); 
	
	// remove points on straigt lines (necessary for calculation of intersection later in IPolyline2D)
	// but if straight lines on 2d become curve in 3d, no way to points in-between. to be solved later.
	polyline2.removeStraightPoints(); 
	
	// normal positive means counter clockwise
	boolean dir = polyline2.isNormalPositive();
	if(isOuterTrim && !dir || !isOuterTrim && dir){
	    polyline2.reverse();
	    reversed = true;
	}
	
	
	//debug
	//IVec[] cpts = new IVec[polyline2.num()];
	//for(int i=0; i<polyline2.num(); i++) cpts[i] = new IVec(polyline2.get(i));
	//new IGCurve(cpts);
    }
    
    public void setup3D(){
	// making default rectangular border
	if(curves==null&&surface!=null){
	    
	    double umin=0, umax=1, vmin=0, vmax=1;
	    /*
	    if(!IConfig.normalizeKnots){
		umin = surface.ustart(); umax = surface.uend();
		vmin = surface.vstart(); vmax = surface.vend();
	    }
	    */
	    ITrimCurveGraphic uline1 =
		new ITrimCurveGraphic(surface, new IPolyline2D(new IVec2(umin,vmin),
							       new IVec2(umax,vmin)));
	    ITrimCurveGraphic vline1 =
		new ITrimCurveGraphic(surface, new IPolyline2D(new IVec2(umax,vmin),
							       new IVec2(umax,vmax)));
	    ITrimCurveGraphic uline2 =
		new ITrimCurveGraphic(surface, new IPolyline2D(new IVec2(umax,vmax),
							       new IVec2(umin,vmax)));
	    ITrimCurveGraphic vline2 =
		new ITrimCurveGraphic(surface, new IPolyline2D(new IVec2(umin,vmax),
							       new IVec2(umin,vmin)));
	    
	    polyline = new IPolyline();
	    polyline.append(uline1.getPolyline(1)); 
	    polyline.append(vline1.getPolyline(1));
	    polyline.append(uline2.getPolyline(1));
	    polyline.append(vline2.getPolyline(1));
	    polyline.close();
	    // resolution is 1 because it's line and actually, doesn't matter

	    
	    // polyline2 is updated after execution of getPolyline() inside uline and vline
	    polyline2 = new IPolyline2D();
	    polyline2.append(uline1.getPolyline2D()); 
	    polyline2.append(vline1.getPolyline2D());
	    polyline2.append(uline2.getPolyline2D());
	    polyline2.append(vline2.getPolyline2D());
	    polyline2.close();
	    
	    return;
	    
	    /*
	    IPolyline plU1, plU2, plV1, plV2;
	    int reso = IConfig.surfaceIsoparmResolution*IConfig.surfaceWireframeResolution;
	    int uepnum = surface.uepNum();
	    if(surface.udeg()==1){
		plU1 = new IPolyline(uepnum);
		plU2 = new IPolyline(uepnum);
		for(int i=0; i<uepnum; i++){
		    plU1.set(i, surface.pt(surface.u(i,0),0));
		    plU2.set(i, surface.pt(surface.u(i,0),1));
		}
	    }
	    else{
		plU1 = new IPolyline((uepnum-1)*reso+1);
		plU2 = new IPolyline((uepnum-1)*reso+1);
		for(int i=0; i<uepnum; i++){
		    for(int j=0; j<reso&&i<uepnum-1 || j==0; j++){
			plU1.set(i*reso+j,surface.pt(surface.u(i,(double)j/reso),0));
			plU2.set(i*reso+j,surface.pt(surface.u(i,(double)j/reso),1));
		    }
		}
	    }
	    int vepnum = surface.vepNum();
	    if(surface.vdeg()==1){
		plV1 = new IPolyline(vepnum);
		plV2 = new IPolyline(vepnum);
		for(int i=0; i<vepnum; i++){
		    plV1.set(i,surface.pt(0,surface.v(i,0)));
		    plV2.set(i,surface.pt(1,surface.v(i,0)));
		}
	    }
	    else{
		plV1 = new IPolyline((vepnum-1)*reso+1);
		plV2 = new IPolyline((vepnum-1)*reso+1);
		for(int i=0; i<vepnum; i++){
		    for(int j=0; j<reso&&i<vepnum-1 || j==0; j++){
			plV1.set(i*reso+j,surface.pt(0,surface.v(i,(double)j/reso)));
			plV2.set(i*reso+j,surface.pt(1,surface.v(i,(double)j/reso)));
		    }
		}
	    }
	    polyline = new IPolyline();
	    polyline.append(plU1);
	    polyline.append(plV2);
	    polyline.append(plU2,true);
	    polyline.append(plV1,true);
	    polyline.close();
	    return;
	    */
	}
	
	
	if(surface.udeg()==1 && surface.vdeg()==1 && surface.ucpNum()==2 && surface.vcpNum()==2 &&
	   // needs to be parallelogram
	   Math.abs(surface.cp(0,0).dist(surface.cp(1,0)) - surface.cp(0,1).dist(surface.cp(1,1)))<=IConfig.tolerance &&
	   Math.abs(surface.cp(0,0).dist(surface.cp(0,1)) - surface.cp(1,0).dist(surface.cp(1,1)))<=IConfig.tolerance &&
	   surface.isFlat()){
	    // copy polyline2
	    if(polyline2==null) setup2D();
	    polyline = new IPolyline(polyline2.num());
	    for(int i=0; i<polyline2.num(); i++)
		polyline.set(i, surface.pt(polyline2.get(i)));
	}
	else{
	    polyline2 = new IPolyline2D(); // added 2011/10/18
	    polyline = new IPolyline();
	    for(int i=0; i<curves.length; i++){
		ITrimCurveGraphic curvg = new ITrimCurveGraphic(curves[i]);
		curvg.setup3D(resolution);
		if(i==0 ||
		   polyline.get(polyline.num()-1).dist(curvg.polyline.get(0)) <
		   polyline.get(polyline.num()-1).dist(curvg.polyline.get(curvg.polyline.num()-1))){
		    polyline.append(curvg.polyline);
		    polyline2.append(curvg.getPolyline2D());
		}
		else{
		    polyline.append(curvg.polyline,true); // reverse
		    polyline2.append(curvg.getPolyline2D(),true);
		}
	    }
	    
	    polyline.close();
	    polyline2.close(); //
	    
	    /*
	    boolean anyDeg1=false;
	    for(int i=0; i<curves.length&&!anyDeg1; i++)
		if(curves[i].deg()==1) anyDeg1=true;
	    if(anyDeg1){
		// rebuild everything
		polyline = new IPolyline();
		for(int i=0; i<curves.length; i++){
		    ITrimCurveGraphic curvg = new ITrimCurveGraphic(curves[i]);
		    IPolyline curvpl = curvg.getPolyline();
		    if(i==0 ||
		       polyline.get(polyline.num()-1).dist(curvpl.get(0)) <
		       polyline.get(polyline.num()-1).dist(curvpl.get(curvpl.num()-1)))
			polyline.append(curvpl);
		    else polyline.append(curvpl,true); // reverse
		}
	    }
	    else{
		// copy polyline2
		if(polyline2==null) setup2D();
		polyline = new IPolyline(polyline2.num());
		for(int i=0; i<polyline2.num(); i++)
		    polyline.set(i, surface.pt(polyline2.get(i)));
	    }
	    */
	    
	}
	polyline.close();
	
	if(polyline2!=null && !polyline2.isClosed()) polyline2.close(); //
    }
    
    
}
    
