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
   3D vector filed defined by a plane.
   
   @author Satoru Sugihara
*/

public class IPlaneFieldGeo extends IFieldGeo implements I3DFieldI{
    public IVecI pos;
    public IVecI nml;
    
    public IPlaneFieldGeo(IVecI p, IVecI n){ pos=p; nml=n; }
    
    /** get original field value out of curve parameter u */
    public IVecI getForce(IVecI v){ return nml.dup(); } // default direction force (gravity)
    
    /** get original field value out of curve parameter u */
    public IVecI getForce(IVecI v, IVecI vel){ return getForce(v); } // ignore vel as default
    
    /** get 3D vector field value */
    public IVecI get(IVecI v){ return get(v, null); }
    
    /** get 3D vector field value */
    public IVecI get(IVecI pt, IVecI vel){
	double r = intensity;
	if(this.pos!=null){
	    if(decay == Decay.Linear){
		double dist = pt.get().distToPlane(nml, this.pos);
		if(dist >= threshold) return new IVec(); // zero
		if(threshold>0) r *= (threshold-dist)/threshold;
	    }
	    else if(decay == Decay.Gaussian){
		double dist = pt.get().distToPlane(nml, this.pos);
		if(threshold>0) r *= Math.exp(-2*dist*dist/(threshold*threshold));
	    }
	    else if(decay == Decay.Custom && customDecay!=null){
		double dist = pt.get().distToPlane(nml, this.pos);
		r = customDecay.decay(intensity, dist, threshold);
	    }
	    
	}
	IVecI vec = getForce(pt,vel);
	if(bidirectional && vec.get().dot(vel) < 0){ r=-r; }
	if(constantIntensity){
	    double len = vec.len();
	    if(len<IConfig.tolerance){ return vec.zero(); }
	    return vec.len(r);
	}
	return vec.mul(r);
    }
    
    public IVec pos(){ return pos.get(); }
    public IVec nml(){ return nml.get(); }
    
    /** set no decay */
    public IPlaneFieldGeo noDecay(){ super.noDecay(); return this; }
    /** set linear decay; When distance is equal to threshold, output is zero.*/
    public IPlaneFieldGeo linearDecay(double threshold){
	super.linearDecay(threshold); return this;
    }
    public IPlaneFieldGeo linear(double threshold){
	super.linear(threshold); return this;
    }
    /** set Gaussian decay; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
     */
    public IPlaneFieldGeo gaussianDecay(double threshold){
	super.gaussianDecay(threshold); return this;
    }
    public IPlaneFieldGeo gaussian(double threshold){
	super.gaussian(threshold); return this;
    }
    public IPlaneFieldGeo gauss(double threshold){ super.gauss(threshold); return this; }
    
    public IPlaneFieldGeo threshold(double t){ super.threshold(t); return this; }
    public IPlaneFieldGeo intensity(double i){ super.intensity(i); return this; }

    
    
    public void del(){
	if(pos!=null && pos instanceof IObject){ ((IObject)pos).del(); }
	if(nml!=null && nml instanceof IObject){ ((IObject)nml).del(); }
    }
    
    /*
    public static void main(String[] a){
	try{
	    double threshold = 1.0;
	    double dist = Double.valueOf(a[0]);
	    IG.p(Math.exp(-2*dist*dist/(threshold*threshold)));
	}catch(Exception e){}
    }
    */
    
    
    
}
