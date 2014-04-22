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
   3D vector filed agent.
   
   @author Satoru Sugihara
*/

public class I3DField extends IField implements I3DFieldI{
    public I3DFieldI field;
    public I3DField(I3DFieldI f){ field=f; }
    public IVecI get(IVecI pt){ if(field==null){ return null; } return field.get(pt); }
    
    public IVecI get(IVecI pt, IVecI vel){ if(field==null){ return null; } return field.get(pt,vel); }
    
    //public void applyField(IParticleI p){ p.push(get(p.pos())); }
    public void applyField(IParticleI p){
	p.push(get(p.pos(),p.vel()));
	//IVecI force = get(p.pos(),p.vel());
	//IG.p("force = "+force); 
	//p.push(force);
    }
        
    public I3DFieldI field(){ return field; }
    /** set no decay */
    public I3DField noDecay(){ if(field!=null){ field.noDecay(); } return this; }
    /** set linear decay with threshold; When distance is equal to threshold, output is zero.*/
    public I3DField linearDecay(double threshold){ if(field!=null){ field.linearDecay(threshold); } return this; }
    /** alias of linearDecay */
    public I3DField linear(double threshold){ return linearDecay(threshold); }
    
    /** set Gaussian decay with threshold; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
    */
    public I3DField gaussianDecay(double threshold){ if(field!=null){ field.gaussianDecay(threshold); } return this; }
    /** alias of gaussianDecay */
    public I3DField gaussian(double threshold){ return gaussianDecay(threshold); }
    /** alias of gaussianDecay */
    public I3DField gauss(double threshold){ return gaussianDecay(threshold); }
    /** this returns current decay type */
    //public Decay decay();
    
    
    /** if output vector is besed on constant length (intensity) or variable depending geometry when curve or surface tangent is used */
    public I3DField constantIntensity(boolean b){ if(field!=null){ field.constantIntensity(b); } return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public I3DField bidirectional(boolean b){ if(field!=null){ field.bidirectional(b); } return this; }
    
    
    /** set decay threshold */
    public I3DField threshold(double t){ if(field!=null){ field.threshold(t); } return this; }
    /** get decay threshold */
    public double threshold(){ if(field==null){ return 0; } return field.threshold(); }
    
    /** set output intensity */
    public I3DField intensity(double i){ if(field!=null){ field.intensity(i); } return this; }
    /** get output intensity */
    public double intensity(){ if(field==null){ return 0; } return field.intensity(); }
    
    public void del(){ if(field!=null){ field.del(); } super.del(); }
    /** stop agent with option of deleting/keeping the geometry the agent owns */
    public void del(boolean deleteGeometry){ 
	if(deleteGeometry && field!=null) field.del();
	super.del(deleteGeometry);
    }
    
}
