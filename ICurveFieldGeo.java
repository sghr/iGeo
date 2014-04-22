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

/**
   3D vector filed defined by a NURBS curve.
   
   @author Satoru Sugihara
*/

public class ICurveFieldGeo extends IFieldGeo implements I3DFieldI{
    
    public ICurveI curve;
    public ICurveI fieldCurve;
    
    public ICurveFieldGeo(ICurveI crv, ICurveI fieldCrv){ curve = crv; fieldCurve = fieldCrv; }
        
    /** get original field value out of curve parameter u */
    public IVecI get(IVecI v, double u){ return fieldCurve.pt(u); }
    
    /** get original field value out of curve parameter u */
    public IVecI get(IVecI pos, IVecI v, double u){ return get(pos,u); }
    
    //public IVecI get(double u){ return fieldCurve.tan(u).len(100); }
    
    /** get 3D vector field value */
    public IVecI get(IVecI v){ return get(v,null); }
    
    /** get 3D vector field value */
    public IVecI get(IVecI pos, IVecI vel){
	
	double u = curve.u(pos);
	
	double r = intensity;
	if(decay == Decay.Linear){
	    double dist = curve.pt(u).dist(pos);
	    if(dist >= threshold) return new IVec(); // zero
	    if(threshold>0) r *= (threshold-dist)/threshold;
	}
	else if(decay == Decay.Gaussian){
	    double dist = curve.pt(u).dist(pos);
	    if(threshold>0) r *= Math.exp(-2*dist*dist/(threshold*threshold));
	}
	
	IVecI vec = get(pos,vel,u);
	
	if(bidirectional && vec.get().dot(vel) < 0){ r=-r; }
	
	if(constantIntensity){
	    double len = vec.len();
	    if(len<IConfig.tolerance){ return vec.zero(); }
	    return vec.len(r);
	}
	
	return vec.mul(r);
	
	/*
	switch(decay){
	case None:{
	    if(constantIntensity){
		IVecI vec = get(v,curve.u(v));
		double len = vec.len();
		if(len<IConfig.tolerance){ return vec.zero(); }
		return vec.len(intensity);
	    }
	    return get(v,curve.u(v)).mul(intensity); // variable intensity
	}
	case Linear:{
	    double u = curve.u(v);
	    double dist = curve.pt(u).dist(v);
	    if(dist >= threshold) return new IVec(); // zero
	    if(constantIntensity){
		IVecI vec = get(v,u);
		double len = vec.len();
		if(len<IConfig.tolerance){ return vec.zero(); }
		return vec.len(intensity*dist/threshold);
	    }
	    return get(v,u).mul(intensity*dist/threshold);
	}
	case Gaussian:{
	    double u = curve.u(v);
	    double dist = curve.pt(u).dist(v);
	    if(constantIntensity){
		IVecI vec = get(v,u);
		double len = vec.len();
		if(len<IConfig.tolerance){ return vec.zero(); }
		return vec.len(intensity*Math.exp(-2*dist*dist/(threshold*threshold)));
	    }
	    return get(v,u).mul(intensity*Math.exp(-2*dist*dist/(threshold*threshold)));
	}
	}
	return new IVec(); // should not reach here.
	*/
    }
    
    /** if output vector is besed on constant length (intensity) or variable depending geometry when curve or surface tangent is used */
    public ICurveFieldGeo constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public ICurveFieldGeo bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    
    /** set no decay */
    public ICurveFieldGeo noDecay(){ super.noDecay(); return this; }
    /** set linear decay; When distance is equal to threshold, output is zero.*/
    public ICurveFieldGeo linearDecay(double threshold){
	super.linearDecay(threshold); return this;
    }
    public ICurveFieldGeo linear(double threshold){
	super.linear(threshold); return this;
    }
    /** set Gaussian decay; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
     */
    public ICurveFieldGeo gaussianDecay(double threshold){
	super.gaussianDecay(threshold); return this;
    }
    public ICurveFieldGeo gaussian(double threshold){
	super.gaussian(threshold); return this;
    }
    public ICurveFieldGeo gauss(double threshold){
	super.gauss(threshold); return this;
    }
    public ICurveFieldGeo threshold(double t){ super.threshold(t); return this; }
    public ICurveFieldGeo intensity(double i){ super.intensity(i); return this; }
    
    
    public void del(){
	if(curve!=null && curve==fieldCurve){
	    if(curve instanceof IObject){ ((IObject)curve).del(); }
	}
	else{
	    if(curve!=null && curve instanceof IObject){ ((IObject)curve).del(); }
	    if(fieldCurve!=null && fieldCurve instanceof IObject){ ((IObject)fieldCurve).del(); }
	}
    }
}
