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
   2D vector filed defined by a NURBS surface.
   
   @author Satoru Sugihara
*/

public class I2DSurfaceFieldGeo extends IFieldGeo implements I2DFieldI{
    
    public ISurfaceI surface;
    public ISurfaceI fieldSurface;
    
    public I2DSurfaceFieldGeo(ISurfaceI srf, ISurfaceI fieldSrf){
	surface = srf; fieldSurface = fieldSrf;
	// default of 2D surface field is linear decay with zero threshold.
	linearDecay(0);
    }
    
    
    /** get original field value out of surface parameter uv */
    public IVec2I get(IVecI pos, IVec2I uv){
	return fieldSurface.pt(uv).to2d();
    }
    
    /** get original field value out of surface parameter uv */
    public IVec2I get(IVecI pos, IVecI vel, IVec2I uv){
	//return fieldSurface.pt(uv).to2d();
	return get(pos,uv); // 20140323
    }
    
    /** get 3D vector field value */
    public IVec2I get(IVecI v){ return get(v,(IVecI)null); }
    
    /** get 3D vector field value */
    public IVec2I get(IVecI pos, IVecI vel){
	//IVec2I uv = surface.uv(pos.to2d());
	IVec2 uv = surface.uv(pos.to2d()).get();
	double r = intensity;
	if(decay == Decay.Linear){
	    double dist = surface.pt(uv).to2d().dist(pos.to2d());
	    if(dist >= threshold &&
	       (uv.x<=0||uv.y<=0||uv.x>=1.0||uv.y>=1.0)) return new IVec2(); // zero
	       //(uv.x<IConfig.parameterTolerance || uv.y<IConfig.parameterTolerance ||
	       //uv.x>1.0-IConfig.parameterTolerance || uv.y>1.0-IConfig.parameterTolerance)) // inside
	       //return new IVec2(); // zero
	    if(threshold>0) r *= (threshold-dist)/threshold;
	}
	else if(decay == Decay.Gaussian){
	    double dist = surface.pt(uv).to2d().dist(pos.to2d());
	    if(threshold>0) r *= Math.exp(-2*dist*dist/(threshold*threshold));
	}
	
	IVec2I vec = get(pos, vel, uv);
	
	if(bidirectional && vec.get().dot(vel.to2d()) < 0){ r=-r; }
	
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
		IVec2I vec = get(v,surface.uv(v.to2d()));
		double len = vec.len();
		if(len<IConfig.tolerance){ return vec.zero(); }
		return vec.len(intensity);
	    }
	    return get(v,surface.uv(v.to2d())).mul(intensity); // need get() in case of ISurfaceR ?
	} 
	case Linear:{
	    IVec2I uv = surface.uv(v.to2d());
	    double dist = surface.pt(uv).to2d().dist(v.to2d());
	    if(dist >= threshold) return new IVec2(); // zero
	    double r = intensity;
	    if(threshold>0) r *= (threshold-dist)/threshold;
	    if(constantIntensity){
		IVec2I vec = get(v,uv);
		double len = vec.len();
		if(len<IConfig.tolerance){ return vec.zero(); }
		return vec.len(r);
	    }
	    return get(v,uv).mul(r);
	}
	case Gaussian:{
	    IVec2I uv = surface.uv(v.to2d());
	    double dist = surface.pt(uv).to2d().dist(v.to2d());
	    double r = intensity;
	    if(threshold>0) r *= Math.exp(-2*dist*dist/(threshold*threshold));
	    if(constantIntensity){
		IVec2I vec = get(v,uv);
		double len = vec.len();
		if(len<IConfig.tolerance){ return vec.zero(); }
		return vec.len(r);
	    }
	    return get(v,uv).mul(r);
	}
	}
	return null;
	*/
    }
    
    
    /** if output vector is besed on constant length (intensity) or variable depending geometry when curve or surface tangent is used */
    public I2DSurfaceFieldGeo constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public I2DSurfaceFieldGeo bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    
    /** set no decay */
    public I2DSurfaceFieldGeo noDecay(){ super.noDecay(); return this; }
    /** set linear decay; When distance is equal to threshold, output is zero.*/
    public I2DSurfaceFieldGeo linearDecay(double threshold){
	super.linearDecay(threshold); return this;
    }
    public I2DSurfaceFieldGeo linear(double threshold){
	super.linear(threshold); return this;
    }
    /** set Gaussian decay; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
     */
    public I2DSurfaceFieldGeo gaussianDecay(double threshold){
	super.gaussianDecay(threshold); return this;
    }
    public I2DSurfaceFieldGeo gaussian(double threshold){
	super.gaussian(threshold); return this;
    }
    public I2DSurfaceFieldGeo gauss(double threshold){ super.gauss(threshold); return this; }
    
    public I2DSurfaceFieldGeo threshold(double t){ super.threshold(t); return this; }
    public I2DSurfaceFieldGeo intensity(double i){ super.intensity(i); return this; }
    
    public void del(){
	if(surface!=null && surface==fieldSurface){
	    if(surface instanceof IObject){ ((IObject)surface).del(); }
	}
	else{
	    if(surface!=null && surface instanceof IObject){ ((IObject)surface).del(); }
	    if(fieldSurface!=null && fieldSurface instanceof IObject){ ((IObject)fieldSurface).del(); }
	}
    }
}
