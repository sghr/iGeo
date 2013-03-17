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
/*
  Interactive Geometry
  Copyright (c) 2005 - 2011 Satoru Sugihara
*/

package igeo;

import igeo.gui.*;

import java.util.ArrayList;

/**
   Geometry class of trim curve on a surface.
   A trim curve is either outer trim curve (outside edge) or inner trim curve (edge of hole). 
   ITrimCurve always need to be contained by surface instance and same instance of ITrimCurve should not be shared by multiple surfaces.
   
   @author Satoru Sugihara
*/
public class ITrimCurve extends ICurveGeo implements ITrimCurveI{
    
    public ISurfaceI surface = null;
    
    public ITrimCurve(){}
    
    public ITrimCurve(IVecI[] cpts, int degree, double[] knots, double ustart, double uend){
	super(cpts,degree,knots,ustart,uend);
    }
    
    public ITrimCurve(IVecI[] cpts, int degree, double[] knots){ super(cpts,degree,knots); }
    
    public ITrimCurve(IVecI[] cpts, int degree){ super(cpts,degree); }
    
    public ITrimCurve(IVecI[] cpts){ super(cpts); }
    
    public ITrimCurve(IVecI[] cpts, int degree, boolean close){ super(cpts,degree,close); }
    
    public ITrimCurve(IVecI[] cpts, boolean close){ super(cpts,close); }
    
    public ITrimCurve(IVecI pt1, IVecI pt2){ super(pt1,pt2); }
    
    public ITrimCurve(double x1, double y1, double x2, double y2){ super(x1,y1,0,x2,y2,0); }
    
    public ITrimCurve(double[][] xyValues){ super(xyValues); }
    
    public ITrimCurve(double[][] xyValues, int degree){ super(xyValues, degree); }
    
    public ITrimCurve(double[][] xyValues, boolean close){ super(xyValues,close); }
    
    public ITrimCurve(double[][] xyValues, int degree, boolean close){ super(xyValues, degree,close); }
    
    public ITrimCurve(ICurveGeo crv){ super(crv); }
    
    public ITrimCurve(ICurve crv){ this(crv.curve); }
    
    public ITrimCurve(ICurveI crv){ this(crv.get()); }
    
    public ITrimCurve(ITrimCurve crv){
	super(crv);surface=crv.surface;
    }
    
    public ITrimCurve(ITrimCurve crv, ISurfaceI surf){
	super(crv);surface=surf;
    }
    
    
    // now ISurfaceI is set after instantiated inside surface when it's added to surface 
    /*
    public ITrimCurve(ISurfaceI srf){ surface=srf; }
    public ITrimCurve(ISurfaceI srf, IVecI[] cpts, int degree,
		      double[] knots, double ustart, double uend){
	super(cpts,degree,knots,ustart,uend); surface=srf;
    }
    public ITrimCurve(ISurfaceI srf, IVecI[] cpts, int degree, double[] knots){
	super(cpts,degree,knots); surface=srf;
    }
    public ITrimCurve(ISurfaceI srf, IVecI[] cpts, int degree){
	super(cpts,degree); surface=srf;
    }
    public ITrimCurve(ISurfaceI srf, IVecI[] cpts){ super(cpts); surface=srf; }
    public ITrimCurve(ISurfaceI srf, IVecI[] cpts, int degree, boolean close){
	super(cpts,degree,close); surface=srf;
    }
    public ITrimCurve(ISurfaceI srf, IVecI[] cpts, boolean close){
	super(cpts,close); surface=srf;
    }
    public ITrimCurve(ISurfaceI srf, IVecI pt1, IVecI pt2){ super(pt1,pt2); surface=srf; }
    public ITrimCurve(ISurfaceI srf, double x1, double y1, double x2, double y2){
	super(x1,y1,0,x2,y2,0); surface=srf;
    }
    public ITrimCurve(ISurfaceI srf, double[][] xyValues){ super(xyValues); surface=srf; }
    public ITrimCurve(ISurfaceI srf, double[][] xyValues, int degree){
	super(xyValues, degree); surface=srf;
    }
    public ITrimCurve(ISurfaceI srf, double[][] xyValues, boolean close){
	super(xyValues,close); surface=srf;
    }
    public ITrimCurve(ISurfaceI srf, double[][] xyValues, int degree, boolean close){
	super(xyValues, degree,close); surface=srf;
    }
    public ITrimCurve(ISurfaceI srf, ICurveGeo crv){ super(crv); surface=srf; }
    public ITrimCurve(ISurfaceI srf, ICurve crv){ this(srf,crv.curve); }
    public ITrimCurve(ISurfaceI srf, ICurveI crv){ this(srf,crv.get()); }
    */
    
    
    /**
       Setting surface of the trim curve. Giving error when the surface already set.
    */
    public ITrimCurve surface(ISurfaceI srf){
	// not checking in case of use of dup()
	if(surface!=null && surface!=srf){ IOut.err("surface is already set. overwritten."); }
	surface=srf;
	return this;
    }
    
    public ISurfaceI surface(){ return surface; }
    
    /**
       When duplicated, surface is cleared and need to be re-set later.
    */
    public ITrimCurve dup(){
	//return new ITrimCurve(this);
	ITrimCurve tr = new ITrimCurve(this);
	tr.surface = null;
	return tr;
    }
    
    public ITrimCurve get(){ return this; }
    
    
    /** Gets trim curve in 3d space mapped via the surface
	(Currently very rough approximation)
    */
    public ICurveGeo get3d(){
	// map only control points for the moment (approximation is very rough)
	
	if(surface==null){
	    IOut.err("surface is not set");
	    return null;
	}

	/* this should not happen
	// default trim edges
	if(this.num()==5){
	    IVecI[] cps1 = new IVecI[4];
	    cps1[0] = cp(0); cps1[1] = cp(1); cps1[2] = cp(2); cps1[3] = cp(3);
	    if(IVec.isArrayEqual(cps1, new IVec[]{ new IVec(0,0,0), new IVec(1.0,0,0),
						   new IVec(1.0,1.,0), new IVec(0,1.0,0) },
				 true, true)){
	    }
	}
	*/
	
	// default trim edges
	if(this.num()==2){
	    IVec p00 = new IVec(0.,0,0);
	    IVec p10 = new IVec(1.,0,0);
	    IVec p01 = new IVec(0,1.,0);
	    IVec p11 = new IVec(1.,1.,0);
	    
	    if(cp(0).eq(p00)){
		if(cp(1).eq(p10)){ // (0,0) - (1,0)
		    int num = surface.unum();
		    IVecI[] cps = new IVecI[num];
		    for(int i=0; i<num; i++){ cps[i] = surface.cp(i,0); }
		    return new ICurveGeo(cps, surface.udeg(), surface.uknots());
		}
		else if(cp(1).eq(p01)){ // (0,0) - (0,1)
		    int num = surface.vnum();
		    IVecI[] cps = new IVecI[num];
		    for(int i=0; i<num; i++){ cps[i] = surface.cp(0,i); }
		    return new ICurveGeo(cps, surface.vdeg(), surface.vknots());
		}
	    }
	    else  if(cp(0).eq(p10)){
		if(cp(1).eq(p00)){ // (1,0) - (0,0)
		    int num = surface.unum();
		    IVecI[] cps = new IVecI[num];
		    for(int i=0; i<num; i++){ cps[i] = surface.cp(num-1-i,0); }
		    return new ICurveGeo(cps, surface.udeg(), INurbsGeo.invertKnots(surface.uknots()));
		}
		else if(cp(1).eq(p11)){ // (1,0) - (1,1)
		    int num = surface.vnum();
		    IVecI[] cps = new IVecI[num];
		    for(int i=0; i<num; i++){ cps[i] = surface.cp(surface.unum()-1,i); }
		    return new ICurveGeo(cps, surface.vdeg(), surface.vknots());
		}
	    }
	    else  if(cp(0).eq(p01)){
		if(cp(1).eq(p00)){ // (0,1) - (0,0)
		    int num = surface.vnum();
		    IVecI[] cps = new IVecI[num];
		    for(int i=0; i<num; i++){ cps[i] = surface.cp(0,num-1-i); }
		    return new ICurveGeo(cps, surface.vdeg(), INurbsGeo.invertKnots(surface.vknots()));
		}
		else if(cp(1).eq(p11)){ // (0,1) - (1,1)
		    int num = surface.unum();
		    IVecI[] cps = new IVecI[num];
		    for(int i=0; i<num; i++){ cps[i] = surface.cp(i,surface.vnum()-1); }
		    return new ICurveGeo(cps, surface.udeg(), surface.uknots());
		}
	    }
	    else  if(cp(0).eq(p11)){
		if(cp(1).eq(p10)){ // (1,1) - (1,0)
		    int num = surface.vnum();
		    IVecI[] cps = new IVecI[num];
		    for(int i=0; i<num; i++){ cps[i] = surface.cp(surface.unum()-1,num-1-i); }
		    return new ICurveGeo(cps, surface.vdeg(), INurbsGeo.invertKnots(surface.vknots()));
		}
		else if(cp(1).eq(p01)){ // (1,1) - (0,1)
		    int num = surface.unum();
		    IVecI[] cps = new IVecI[num];
		    for(int i=0; i<num; i++){ cps[i] = surface.cp(num-1-i,surface.vnum()-1); }
		    return new ICurveGeo(cps, surface.udeg(), INurbsGeo.invertKnots(surface.uknots()));
		}
	    }
	}
	
	// when surface is planar & deg is 1 & parallelogram (if skew, straight line become curve)
	if(surface.udeg()==1 && surface.vdeg()==1 &&
	   surface.unum()==2 && surface.vnum()==2 &&
	   Math.abs(surface.cp(0,0).dist(surface.cp(1,0)) -
		    surface.cp(0,1).dist(surface.cp(1,1))) < IConfig.tolerance &&
	   Math.abs(surface.cp(0,0).dist(surface.cp(0,1)) -
		    surface.cp(1,0).dist(surface.cp(1,1))) < IConfig.tolerance &&
	   surface.isFlat()){
	    
	    IVecI[] cpts2 = this.cps();
	    
	    if(cpts2.length==2 || IVec.isStraight(cpts2)){
		IVec cp2_1 = cpts2[0].get();
		IVec cp2_2 = cpts2[cpts2.length-1].get();
		IVec p1 = surface.pt(cp2_1.x,cp2_1.y).get();
		IVec p2 = surface.pt(cp2_2.x,cp2_2.y).get();
		return new ICurveGeo(p1,p2);
	    }
	    
	    IVec[] cpts = new IVec[cpts2.length];
	    for(int i=0; i<cpts2.length; i++){
		//IVecI cp = this.cp(i);
		if(this.defaultWeights[i]){
		    //IVec cp2 = cp.get();
		    IVec cp2 = cpts2[i].get();
		    cpts[i] = surface.pt(cp2.x,cp2.y).get();
		}
		else{
		    //IVec cp2 = cp.get();
		    IVec cp2 = cpts2[i].get();
		    IVec4 cp4 = surface.pt(cp2.x,cp2.y).to4d().get();
		    //if(cp instanceof IVec4I) cp4.w = ((IVec4I)cp).w();
		    if(cpts2[i] instanceof IVec4I) cp4.w = ((IVec4I)cpts2[i]).w();
		    else cp4.w = 1.0;
		    cpts[i] = cp4;
		}
	    }
	    
	    int deg = this.deg();
	    
	    double[] knots = new double[this.knotNum()];
	    for(int i=0; i<knots.length; i++) knots[i] = this.knot(i);
	    
	    double ustart = this.ustart();
	    double uend = this.uend();
	    
	    //return new ICurveGeo(cpts, deg, knots, ustart, uend);
	    // avoid normalizing knots
	    ICurveGeo crv3 = new ICurveGeo(cpts, deg, knots, 0., 1.);
	    crv3.ustart = ustart;
	    crv3.uend = uend;
	    
	    return crv3;
	}
	
	// when surface is not planar
	// approximation with polyline
	
	int uepnum = surface.unum(); //surface.ucpNum();
	int vepnum = surface.vnum(); //surface.vcpNum();
	
	// when trim curve is deg 1
	if(deg()==1){
	    // interpolate between each control points
	    
	    ArrayList<IVec> pts = new ArrayList<IVec>();
	    IVec cp2 = this.cp(0).get();
	    pts.add(surface.pt(cp2.x,cp2.y).get());
	    IVec prevCp2=cp2;
	    
	    for(int i=1; i<num(); i++){
		cp2 = cp(i).get();
		
		double urange = Math.abs(cp2.x-prevCp2.x);
		double vrange = Math.abs(cp2.y-prevCp2.y);
		int unum = (int)(urange*uepnum*IConfig.trimApproximationResolution);
		int vnum = (int)(vrange*vepnum*IConfig.trimApproximationResolution);
		int num = Math.max(unum,vnum);
		
		for(int j=0; j<num; j++){
		    IVec pt2 = prevCp2.sum(cp2, (double)j/(num+1));
		    pts.add(surface.pt(pt2.x,pt2.y).get());
		}
		pts.add(surface.pt(cp2.x,cp2.y).get());
		prevCp2 = cp2;
	    }
	    
	    return new ICurveGeo(pts.toArray(new IVec[pts.size()]));
	}
	
	
	// check boundary by control points
	double minu=1., minv=1., maxu=0., maxv=0.;
	for(int i=0; i<num(); i++){
	    IVec cp = cp(i).get();
	    if(cp.x < minu){ minu = cp.x; }
	    if(cp.x > maxu){ maxu = cp.x; }
	    if(cp.y < minv){ minv = cp.y; }
	    if(cp.y > maxv){ maxv = cp.y; }
	}
	
	if(minu<0.){ minu=0.; }
	if(maxu>1.){ maxu=1.; }
	if(minv<0.){ minv=0.; }
	if(maxv>1.){ maxv=1.; }
	
	double urange = maxu - minu;
	double vrange = maxv - minv;
	int unum = (int)(urange*uepnum*IConfig.trimApproximationResolution);
	int vnum = (int)(vrange*vepnum*IConfig.trimApproximationResolution);
	int cnum = this.epNum()*IConfig.trimApproximationResolution;
	int num = Math.max(cnum, Math.max(unum,vnum));
	
	IVec[] cpts = new IVec[num+1];
	for(int i=0; i<=num; i++){ cpts[i] = this.pt((double)i/num); }
	
	return new ICurveGeo(cpts); 
    }
    
    
    /**
       because surface knots are normalized, control points in u-v coordinates need to be normalized too.
    */
    public void normalizeControlPoints(){ normalizeControlPoints(surface); }
    public void normalizeControlPoints(ISurfaceI srf){
	double ustart = srf.ustart();
	double uend = srf.uend();
	double vstart = srf.vstart();
	double vend = srf.vend();
	if(ustart!=0.0 || uend!=1.0 || vstart!=0.0 || vend!=1.0 )
	    for(IVecI v : controlPoints)
		v.set((v.x()-ustart)/(uend-ustart),(v.y()-vstart)/(vend-vstart),v.z());
	
	//double x = (v.x()-surface.ustart())/(surface.uend()-surface.ustart());
	//double y = (v.y()-surface.vstart())/(surface.vend()-surface.vstart());
	//if(x<0.) x=0.; else if(x>1.) x=1.; // control points could be outside 
	//if(y<0.) y=0.; else if(y>1.) y=1.; // control points could be outside 
	//v.set(x,y,v.z());
	
	//for(int i=0; i<srf.uknotNum(); i++){ IG.err("uknots["+i+"]="+srf.uknot(i)); }//
    }
    
    public void pt(double u, IVec retval){
	IVec uv=new IVec();
	super.pt(u,uv);
	retval.set(surface.pt(uv.x,uv.y));
    }
    
    /** it returns uv coordinates. */
    public void pt2(double u, IVec2 retval){
	IVec uv=new IVec();
	super.pt(u,uv);
	retval.set(uv.x,uv.y);
    }
    /** alias */
    public void pt2d(double u, IVec2 retval){ pt2(u,retval); }
    
    public IVec2I pt2(double u){
	IVec2 retval = new IVec2();
	pt2d(u,retval);
	return retval;
    }
    /** alias */
    public IVec2I pt2d(double u){ return pt2(u); }
    
    public IVec2I pt2(IDoubleI u){
	IVec2 retval = new IVec2();
	pt2d(u.x(),retval);
	return retval;
    }
    /** alias */
    public IVec2I pt2d(IDoubleI u){ return pt2(u); }
    
    public IVec2 start2(){
	IVec p = new IVec();
	super.pt(0.,p);
	return new IVec2(p);
    }
    /** alias */
    public IVec2 start2d(){ return start2(); }
    
    public IVec2 end2(){
	IVec p = new IVec();
	super.pt(1.,p);
	return new IVec2(p);	
    }
    /** alias */
    public IVec2 end2d(){ return end2(); }
    
    public IVec2 startCP2(){ return new IVec2(cp(0)); }
    /** alias */
    public IVec2 startCP2d(){ return startCP2(); }
    
    public IVec2 endCP2(){ return new IVec2(cp(num()-1)); }
    /** alias */
    public IVec2 endCP2d(){ return endCP2(); }
    
    
    public void tan(double u, IVec retval){
	IVec uv=new IVec();
	super.pt(u,uv);
	IVec t=new IVec();
	super.tan(u,t);
	IVec utan = surface.utan(uv.x,uv.y).get();
	IVec vtan = surface.vtan(uv.x,uv.y).get();
	utan.mul(t.x).add(vtan.mul(t.y));
	retval.set(utan);
    }
    
    // compare in 2d
    public boolean isClosed(){
	// check if start and end of parameter match with knots[0] and knots[knots.length-1]
        boolean knotsEndMatch=true;
        if(knots[0] != 0.0 || knots[knots.length-1] != 1.0) knotsEndMatch=false;
        if(knotsEndMatch){
            // check by cp
            if(cp(0).eq(cp(num()-1))) return true;
            return false;
        }
	// check by pt
        if(pt2d(0.).eq(pt2d(1.))) return true;
        return false;
    }
    
    
    public ITrimCurve rev(){ super.rev(); return this; }
    
    /** alias of rev() */
    public ITrimCurve revU(){ return rev(); }
    /** alias of rev() */
    public ITrimCurve flipU(){ return rev(); }
    
    
    /********************************************************************************
     * transformation methods
     *******************************************************************************/
    
    public ITrimCurve add(double x, double y, double z){ super.add(x,y,z); return this; }
    public ITrimCurve add(IDoubleI x, IDoubleI y, IDoubleI z){ super.add(x,y,z); return this; }
    public ITrimCurve add(IVecI v){ super.add(v); return this; }
    public ITrimCurve sub(double x, double y, double z){ super.sub(x,y,z); return this; }
    public ITrimCurve sub(IDoubleI x, IDoubleI y, IDoubleI z){ super.sub(x,y,z); return this; }
    public ITrimCurve sub(IVecI v){ super.sub(v); return this; }
    public ITrimCurve mul(IDoubleI v){ super.mul(v); return this; }
    public ITrimCurve mul(double v){ super.mul(v); return this; }
    public ITrimCurve div(IDoubleI v){ super.div(v); return this; }
    public ITrimCurve div(double v){ super.div(v); return this; }
    
    public ITrimCurve neg(){ super.neg(); return this; }
    /** alias of neg */
    public ITrimCurve flip(){ return neg(); }
    
    
    /** scale add */
    public ITrimCurve add(IVecI v, double f){ super.add(v,f); return this; }
    public ITrimCurve add(IVecI v, IDoubleI f){ super.add(v,f); return this; }
    public ITrimCurve add(double f, IVecI v){ return add(v,f); }
    public ITrimCurve add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public ITrimCurve rot(IDoubleI angle){ super.rot(angle); return this; }
    public ITrimCurve rot(double angle){ super.rot(angle); return this; }
    public ITrimCurve rot(IVecI axis, IDoubleI angle){ super.rot(axis,angle); return this; }
    public ITrimCurve rot(IVecI axis, double angle){ super.rot(axis,angle); return this; }
    public ITrimCurve rot(IVecI center, IVecI axis, IDoubleI angle){
	super.rot(center,axis,angle); return this;
    }
    public ITrimCurve rot(IVecI center, IVecI axis, double angle){
	super.rot(center,axis,angle); return this;
    }
    /** rotate to destination direction vector */
    public ITrimCurve rot(IVecI axis, IVecI destDir){
	super.rot(axis,destDir); return this;
    }
    /** rotate to destination point location */
    public ITrimCurve rot(IVecI center, IVecI axis, IVecI destPt){
	super.rot(center,axis,destPt); return this;
    }
    
    public ITrimCurve rot2(IDoubleI angle){ super.rot(angle); return this; }
    public ITrimCurve rot2(double angle){ super.rot(angle); return this; }
    public ITrimCurve rot2(IVecI center, IDoubleI angle){ super.rot2(center,angle); return this; }
    public ITrimCurve rot2(IVecI center, double angle){ super.rot2(center,angle); return this; }
    /** rotation on xy-plane to destination direction vector */
    public ITrimCurve rot2(IVecI destDir){ super.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */
    public ITrimCurve rot2(IVecI center, IVecI destPt){ super.rot2(center,destPt); return this; }
    
    /** same with mul */
    public ITrimCurve scale(IDoubleI f){ return mul(f); }
    public ITrimCurve scale(double f){ return mul(f); }
    public ITrimCurve scale(IVecI center, IDoubleI f){ super.scale(center,f); return this; }
    public ITrimCurve scale(IVecI center, double f){ super.scale(center,f); return this; }
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public ITrimCurve ref(IVecI planeDir){ super.ref(planeDir); return this; }
    public ITrimCurve ref(IVecI center, IVecI planeDir){ super.ref(center,planeDir); return this; }
    
    /** mirror is alias of ref */
    public ITrimCurve mirror(IVecI planeDir){ return ref(planeDir); }
    public ITrimCurve mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    public ITrimCurve shear(double sxy, double syx, double syz,
			    double szy, double szx, double sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public ITrimCurve shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			    IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public ITrimCurve shear(IVecI center, double sxy, double syx, double syz,
			    double szy, double szx, double sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public ITrimCurve shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			    IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public ITrimCurve shearXY(double sxy, double syx){
	super.shearXY(sxy,syx); return this;
    }
    public ITrimCurve shearXY(IDoubleI sxy, IDoubleI syx){
	super.shearXY(sxy,syx); return this;
    }
    public ITrimCurve shearXY(IVecI center, double sxy, double syx){
	super.shearXY(center,sxy,syx); return this;
    }
    public ITrimCurve shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	super.shearXY(center,sxy,syx); return this;
    }
    
    public ITrimCurve shearYZ(double syz, double szy){
	super.shearYZ(syz,szy); return this;
    }
    public ITrimCurve shearYZ(IDoubleI syz, IDoubleI szy){
	super.shearYZ(syz,szy); return this;
    }
    public ITrimCurve shearYZ(IVecI center, double syz, double szy){
	super.shearYZ(center,syz,szy); return this;
    }
    public ITrimCurve shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	super.shearYZ(center,syz,szy); return this;
    }
    
    public ITrimCurve shearZX(double szx, double sxz){
	super.shearZX(szx,sxz); return this;
    }
    public ITrimCurve shearZX(IDoubleI szx, IDoubleI sxz){
	super.shearZX(szx,sxz); return this;
    }
    public ITrimCurve shearZX(IVecI center, double szx, double sxz){
	super.shearZX(center,szx,sxz); return this;
    }
    public ITrimCurve shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	super.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public ITrimCurve translate(double x, double y, double z){ return add(x,y,z); }
    public ITrimCurve translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ITrimCurve translate(IVecI v){ return add(v); }
    
    
    public ITrimCurve transform(IMatrix3I mat){ super.transform(mat); return this; }
    public ITrimCurve transform(IMatrix4I mat){ super.transform(mat); return this; }
    public ITrimCurve transform(IVecI xvec, IVecI yvec, IVecI zvec){
	super.transform(xvec,yvec,zvec); return this;
    }
    public ITrimCurve transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	super.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /** mv() is alias of add() */
    public ITrimCurve mv(double x, double y, double z){ return add(x,y,z); }
    public ITrimCurve mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ITrimCurve mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public ITrimCurve cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public ITrimCurve cp(double x, double y, double z){ return dup().add(x,y,z); }
    public ITrimCurve cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public ITrimCurve cp(IVecI v){ return dup().add(v); }
    
    
}
