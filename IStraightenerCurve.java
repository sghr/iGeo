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

import java.util.ArrayList;

/**
   3 point curve with straightener force inside.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IStraightenerCurve extends ICurve{
    
    IStraightener straightener;
    
    public IStraightenerCurve(IParticleI p1, IParticleI p2, IParticleI p3){
	super(new IVec[]{ p1.pos(), p2.pos(), p3.pos()}, 2);
	straightener = new IStraightener(p1,p2,p3);
	straightener.target(this);
    }
    
    public IStraightenerCurve(IParticle p1, IParticle p2, IParticle p3){
	super(new IVec[]{ p1.pos(), p2.pos(), p3.pos()}, 2);
	straightener = new IStraightener(p1,p2,p3);
	straightener.target(this);
    }
    
    public IStraightenerCurve(IVec p1, IVec p2, IVec p3){
	super(new IVec[]{ p1, p2, p3 }, 2);
	straightener = new IStraightener(p1,p2,p3);
	straightener.target(this);
    }
    
    public IStraightenerCurve(IVecI p1, IVecI p2, IVecI p3){
	super(new IVecI[]{ p1, p2, p3 }, 2);
	straightener = new IStraightener(p1,p2,p3);
	straightener.target(this);
    }
    
    public double tension(){ return straightener.tension(); }
    public IStraightenerCurve tension(double tension){ straightener.tension(tension); return this; }

    public boolean constant(){ return straightener.constant(); }
    public IStraightenerCurve constant(boolean cnst){ straightener.constant(cnst); return this; }
    
    public IParticleI pt(int i){ return straightener.pt(i); }
    
    
    public IStraightenerCurve parent(IObject par){ straightener.parent(par); return this; }
    public IStraightenerCurve target(IObject targetObj){ straightener.target(targetObj); return this; }
    public IStraightenerCurve removeTarget(int i){ straightener.removeTarget(i); return this; }
    public IStraightenerCurve removeTarget(IObject obj){ straightener.removeTarget(obj); return this; }
    
}
