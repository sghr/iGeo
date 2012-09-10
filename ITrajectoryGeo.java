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
   IDyanmics class to have trajectory curve object.
   In every update, specified vector position is added to the trajectory curve.
   
   @author Satoru Sugihara
*/
public class ITrajectoryGeo extends IDynamicsBase implements ITrajectoryI{
    public int degree;
    public ICurve curve;
    public IVecI pos;
    
    public ITrajectoryGeo(IVecI pos){ this.pos = pos; degree=1; }
    public ITrajectoryGeo(IVecI pos, int curveDegree){ this.pos = pos; degree=curveDegree; }
    
    public ITrajectoryGeo(IObject obj, IVecI pos){ super(obj); this.pos = pos; degree=1; }
    public ITrajectoryGeo(IObject obj, IVecI pos, int curveDegree){ super(obj); this.pos = pos; degree=curveDegree; }
    
    public ITrajectoryGeo(IPointAgent agent){ super(agent); pos = agent.pos(); degree=1; }
    public ITrajectoryGeo(IPointAgent agent, int curveDegree){ super(agent); pos = agent.pos(); degree=curveDegree; }
    
    public int deg(){ return degree; }
    public ITrajectoryGeo deg(int deg){ degree=deg; return this; }
    
    public void hide(){ curve.hide(); }
    public void show(){ curve.show(); }
    public void del(){ if(curve!=null){ curve.del(); } if(parent!=null){ parent.deleteDynamics(this); } }
    
    public ICurve curve(){ return curve; }
    public ICurve trajectory(){ return curve(); }
    
    /** add a point at the end of trajectory curve */
    public ITrajectoryGeo addCP(IVecI pt){
	if(curve==null){
	    curve = new ICurve(pt);
	    if(parent!=null){
		if(parent.attr()!=null){ curve.attr(parent.attr()); }
		else if(parent.clr()!=null){ curve.clr(parent.clr()); }
	    }
	}
	else{ curve.addCP(pt, degree); }
	return this;
    }
    
    public void update(){
	addCP(pos.get().dup());
	/*
	if(curve==null){
	    curve = new ICurve(pos.get().dup());
	    if(parent!=null){
		if(parent.attr()!=null){ curve.attr(parent.attr()); }
		else if(parent.clr()!=null){ curve.clr(parent.clr()); }
	    }
	}
	else{ curve.addCP(pos.get().dup(), degree); }
	*/
    }
}
