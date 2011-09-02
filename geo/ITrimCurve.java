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
/*
  Interactive Geometry
  Copyright (c) 2005 - 2011 Satoru Sugihara
*/

package igeo.geo;

import igeo.core.*;
import igeo.gui.*;

/**
   Geometry class of trim curve on a surface.
   A trim curve is either outer trim curve (outside edge) or inner trim curve (edge of hole). 
   ITrimCurve always need to be contained by surface instance and same instance of ITrimCurve should not be shared by multiple surfaces.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
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
    
    public ITrimCurve(ITrimCurve crv){ super(crv);surface=crv.surface; }
    
    
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
	
	IVec[] cpts = new IVec[this.num()];
	for(int i=0; i<cpts.length; i++){
	    IVecI cp = this.cp(i);
	    if(this.defaultWeights[i]){
		IVec cp2 = cp.get();
		cpts[i] = surface.pt(cp2.x,cp2.y).get();
	    }
	    else{
		IVec cp2 = cp.get();
		IVec4 cp4 = surface.pt(cp2.x,cp2.y).to4d().get();
		if(cp instanceof IVec4I) cp4.w = ((IVec4I)cp).w();
		else cp4.w = 1.0;
		cpts[i] = cp4;
	    }
	}
	
	int deg = this.deg();
	
	double[] knots = new double[this.knotNum()];
	for(int i=0; i<knots.length; i++) knots[i] = this.knot(i);
	
	double ustart = this.ustart();
	double uend = this.uend();
	
	return new ICurveGeo(cpts, deg, knots, ustart, uend);
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
    }
    
    public void pt(double u, IVec retval){
	IVec uv=new IVec();
	super.pt(u,uv);
	retval.set(surface.pt(uv.x,uv.y));
    }
    
    /**
       it returns uv coordinates.
    */
    public void pt2d(double u, IVec2 retval){
	IVec uv=new IVec();
	super.pt(u,uv);
	retval.set(uv.x,uv.y);
    }
    
    public IVec2I pt2d(double u){
	IVec2 retval = new IVec2();
	pt2d(u,retval);
	return retval;
    }
    
    public IVec2I pt2d(IDoubleI u){
	IVec2 retval = new IVec2();
	pt2d(u.x(),retval);
	return retval;
    }
    
    public IVec2 start2d(){
	IVec p = new IVec();
	super.pt(0.,p);
	return new IVec2(p);
    }
    public IVec2 end2d(){
	IVec p = new IVec();
	super.pt(1.,p);
	return new IVec2(p);	
    }
    public IVec2 startCP2d(){ return new IVec2(cp(0)); }
    public IVec2 endCP2d(){ return new IVec2(cp(num()-1)); }
    
    
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
            if(cp(0).eq(cp(cpNum()-1))) return true;
            return false;
        }
	// check by pt
        if(pt2d(0.).eq(pt2d(1.))) return true;
        return false;
    }
    
    
    public ITrimCurve rev(){ super.rev(); return this; }
    
}
