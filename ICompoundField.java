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
import java.util.ArrayList;

/**
   manage multiple fields; included field should be point / curve / surface based field because it needs to measure distance to check which is closest.
   
   @author Satoru Sugihara
*/

public class ICompoundField extends I3DField{
    //public enum CompundType{ Closest, Sum };
    
    public ArrayList<IPointFieldGeo> pointFields;
    public ArrayList<ICurveFieldGeo> curveFields;
    public ArrayList<ISurfaceFieldGeo> surfaceFields;
    public ArrayList<I3DFieldI> otherFields;
    
    public ICompoundField(){
	super(null);
	pointFields = new ArrayList<IPointFieldGeo>();
	curveFields = new ArrayList<ICurveFieldGeo>();
	surfaceFields = new ArrayList<ISurfaceFieldGeo>();
	otherFields = new ArrayList<I3DFieldI>();
    }
    
    public ICompoundField add(I3DFieldI field){
	if(field instanceof I3DField){
	    //((I3DField)field).del(); // field is managed by this, not by server.
	    ((I3DField)field).del(false); // field is managed by this, not by server.
	    I3DFieldI f = ((I3DField)field).field();
	    if(f==null){ otherFields.add(field); }
	    else if(f instanceof IPointFieldGeo){ pointFields.add((IPointFieldGeo)f); }
	    else if(f instanceof ICurveFieldGeo){ curveFields.add((ICurveFieldGeo)f); }
	    else if(f instanceof ISurfaceFieldGeo){ surfaceFields.add((ISurfaceFieldGeo)f); }
	    else{ otherFields.add(f); }
	}
	else if(field instanceof IPointFieldGeo){ pointFields.add((IPointFieldGeo)field); }
	else if(field instanceof ICurveFieldGeo){ curveFields.add((ICurveFieldGeo)field); }
	else if(field instanceof ISurfaceFieldGeo){ surfaceFields.add((ISurfaceFieldGeo)field); }
	else{ otherFields.add(field); }
	return this;
    }
    
    public ICompoundField remove(I3DFieldI field){
	if(field instanceof I3DField){
	    I3DFieldI f = ((I3DField)field).field();
	    if(f==null){ otherFields.remove(field); }
	    else if(f instanceof IPointFieldGeo){ pointFields.remove((IPointFieldGeo)f); }
	    else if(f instanceof ICurveFieldGeo){ curveFields.remove((ICurveFieldGeo)f); }
	    else if(f instanceof ISurfaceFieldGeo){ surfaceFields.remove((ISurfaceFieldGeo)f); }
	    else{ otherFields.remove(f); }
	}
	else if(field instanceof IPointFieldGeo){ pointFields.remove((IPointFieldGeo)field); }
	else if(field instanceof ICurveFieldGeo){ curveFields.remove((ICurveFieldGeo)field); }
	else if(field instanceof ISurfaceFieldGeo){ surfaceFields.remove((ISurfaceFieldGeo)field); }
	else{ otherFields.remove(field); }
	return this;
    }
    
    @Override public IVecI get(IVecI pt){ return get(pt,null); }
    
    @Override public IVecI get(IVecI pt, IVecI vel){
	// find closest field
	I3DFieldI closestField=null;
	double minDist=0;
	double dist=0;
	
	//debug
	IVecI closestPt=null;
	for(int i=0; i<pointFields.size(); i++){
	    if(pointFields.get(i).pos!=null){
		dist = pointFields.get(i).pos.dist(pt);
		if(dist < minDist || closestField==null){
		    minDist = dist; closestField = pointFields.get(i);
		    //debug
		    //closestPt = pointFields.get(i).pos;
		}
	    }
	}
	for(int i=0; i<curveFields.size(); i++){
	    dist = curveFields.get(i).curve.dist(pt);
	    if(dist < minDist || closestField==null){
		minDist = dist; closestField = curveFields.get(i);
		//debug
		//closestPt = curveFields.get(i).curve.closePt(pt);
	    }
	}
	for(int i=0; i<surfaceFields.size(); i++){
	    dist = surfaceFields.get(i).surface.dist(pt);
	    if(dist < minDist || closestField==null){
		minDist = dist; closestField = surfaceFields.get(i);
		//debug
		//closestPt = surfaceFields.get(i).surface.closePt(pt);
	    }
	}
	
	if(closestField==null){ return new IVec(); }
	
	// debug
	//debugLine = new ICurve(pt.dup(), closestPt).clr(1.0,0.5); //
	
	//return closestField.get(pt);
	return closestField.get(pt,vel);
    }
    
    /* // child fields are aready deleted when added
    public void del(){ if(field!=null){ field.del(); } super.del(); }
    public void del(boolean deleteGeometry){ 
	if(deleteGeometry && field!=null) field.del();
	super.del(deleteGeometry);
    }
    */
    
}
