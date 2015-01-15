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
   3D vector filed defined by a mesh.
   
   @author Satoru Sugihara
*/

public class IMeshFieldGeo extends IFieldGeo implements I3DFieldI{ //extends I3DFieldBase{
    
    public IMeshI mesh;
    
    public IMeshFieldGeo(IMeshI m){ mesh = m; }

    public IVecI get(int vertexIdx){
	return new IVec();
    }
    
    /** get 3D vector field value */
    public IVecI get(IVecI pos){ return get(pos,(IVecI)null); }
    
    /** get 3D vector field value
     @param pos positio of the input particle
     @param vel velocity of the input particle
    */
    public IVecI get(IVecI pos, IVecI vel){
	
        double minDist = 0;
        int minIdx = 0;
        for(int i=0; i<mesh.vertexNum(); i++){
            double dist = mesh.vertex(i).get().dist(pos);
            if(i==0 || dist<minDist){
                minDist = dist;
                minIdx = i;
            }
        }
	
        double r = intensity;
	
        if(decay == Decay.Linear){
            if(minDist >= threshold) return new IVec(); // zero
            if(threshold>0) r *= (threshold-minDist)/threshold;
        }
        else if(decay == Decay.Gaussian){
            if(threshold>0) r *= Math.exp(-2*minDist*minDist/(threshold*threshold));
        }
	else if(decay == Decay.Custom && customDecay!=null){
	    r = customDecay.decay(intensity, minDist, threshold);
	}
	
        IVecI vec = get(minIdx);
	
        if(bidirectional && vec.get().dot(vel) < 0){ r=-r; }

        if(constantIntensity){
            double len = vec.len();
            if(len<IConfig.tolerance){ return vec.zero(); }
            return vec.len(r);
        }
	
        return vec.mul(r);
    }
    
    /** if output vector is besed on constant length (intensity) or variable depending geometry when curve or surface tangent is used */
    public IMeshFieldGeo constantIntensity(boolean b){ super.constantIntensity(b); return this; }

    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public IMeshFieldGeo bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    
    /** set no decay */
    public IMeshFieldGeo noDecay(){ super.noDecay(); return this; }
    /** set linear decay; When distance is equal to threshold, output is zero.*/
    public IMeshFieldGeo linearDecay(double threshold){
	super.linearDecay(threshold); return this;
    }
    public IMeshFieldGeo linear(double threshold){
	super.linear(threshold); return this;
    }
    /** set Gaussian decay; Threshold is used as double of standard deviation (when distance is eqaul to threshold, output is 13.5% of original).
     */
    public IMeshFieldGeo gaussianDecay(double threshold){
	super.gaussianDecay(threshold); return this;
    }
    public IMeshFieldGeo gaussian(double threshold){
	super.gaussian(threshold); return this;
    }
    public IMeshFieldGeo gauss(double threshold){ super.gauss(threshold); return this; }
    
    public IMeshFieldGeo threshold(double t){ super.threshold(t); return this; }
    public IMeshFieldGeo intensity(double i){ super.intensity(i); return this; }
    
    public void del(){
	if(mesh instanceof IObject){
	    ((IObject)mesh).del();
	}
    }
}
