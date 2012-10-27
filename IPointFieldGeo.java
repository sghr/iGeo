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

/**
   3D vector filed defined by a point.
   
   @author Satoru Sugihara
*/

public class IPointFieldGeo extends IFieldGeo implements I3DFieldI{
    public IVecI pos;
    public IVecI dir;
    
    public IPointFieldGeo(IVecI p, IVecI d){ pos=p; dir=d; }
    
    /** get original field value out of curve parameter u */
    public IVecI get(IVecI v, IVecI orig){ return dir.dup(); }
        
    /** get 3D vector field value */
    public IVecI get(IVecI v){
	double r = intensity;
	if(pos!=null){
	    if(decay == Decay.Linear){
		double dist = pos.dist(v);
		if(dist >= threshold) return new IVec(); // zero
		if(threshold>0) r *= (threshold-dist)/threshold;
	    }
	    else if(decay == Decay.Gaussian){
		double dist = pos.dist(v);
		if(threshold>0) r *= Math.exp(-2*dist*dist/(threshold*threshold));
	    }
	}
	IVecI vec = get(v,pos);
	if(constantIntensity){
	    double len = vec.len();
	    if(len<IConfig.tolerance){ return vec.zero(); }
	    return vec.len(r);
	}
	return vec.mul(r);
    }
    
    public IVec pos(){ return pos.get(); }
    public IVec dir(){ return dir.get(); }
    
    /** set no decay */
    public IPointFieldGeo noDecay(){ super.noDecay(); return this; }
    /** set linear decay; When distance is equal to threshold, output is zero.*/
    public IPointFieldGeo linearDecay(double threshold){
	super.linearDecay(threshold); return this;
    }
    public IPointFieldGeo linear(double threshold){
	super.linear(threshold); return this;
    }
    /** set Gaussian decay; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
     */
    public IPointFieldGeo gaussianDecay(double threshold){
	super.gaussianDecay(threshold); return this;
    }
    public IPointFieldGeo gaussian(double threshold){
	super.gaussian(threshold); return this;
    }
    public IPointFieldGeo threshold(double t){ super.threshold(t); return this; }
    public IPointFieldGeo intensity(double i){ super.intensity(i); return this; }

    
    
    public void del(){
	if(pos!=null && pos instanceof IObject){ ((IObject)pos).del(); }
	if(dir!=null && dir instanceof IObject){ ((IObject)dir).del(); }
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
