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
   Class of foldable geometry.
   
   @author Satoru Sugihara
*/
public class IFoldableStrip implements IFoldable{
    
    ArrayList<IVecI[]> pts;
    ArrayList<ITransformable> faces;
    
    public IFoldableStrip(IVecI[][] foldLinePts){
	setFold(foldLinePts);
    }
    public IFoldableStrip(ArrayList<IVecI[]> foldLinePts){ 
	setFold(foldLinePts);
    }
    
    public IFoldableStrip setFold(IVecI[][] foldLinePts){
	if(foldLinePts==null){ IOut.err("fold line points are null"); return this; }
	for(int i=0; i<foldLinePts.length; i++){
	    if(foldLinePts[i].length!=2){
		IOut.err("number of fold line points needs to be 2 (at ["+i+"].length="+foldLinePts[i].length+")");
		return this;
	    }
	}
	pts = new ArrayList<IVecI[]>();
	for(int i=0; i<foldLinePts.length; i++) pts.add(foldLinePts[i]);
	return this;
    }
    
    public IFoldableStrip setFold(ArrayList<IVecI[]> foldLinePts){
	if(foldLinePts==null){ IOut.err("fold line points are null"); return this; }
	for(int i=0; i<foldLinePts.size(); i++){
	    if(foldLinePts.get(i).length!=2){
		IOut.err("number of fold line points needs to be 2 (at ["+i+"].length="+foldLinePts.get(i).length+")");
		return this;
	    }
	}
	pts = foldLinePts;
	return this;
    }
    
    public IFoldableStrip addFold(IVecI linePt1, IVecI linePt2){
	return addFold(new IVecI[]{ linePt1, linePt2 });
    }
    
    public IFoldableStrip addFold(IVecI[] linePts){
	if(linePts==null){ IOut.err("fold line points are null"); return this; }
	if(linePts.length!=2){ IOut.err("number of fold line points needs to be 2 ("+linePts.length+")"); return this; }
	pts.add(linePts);
	return this;
    }

    public IFoldableStrip add(IVecI v){
	if(pts==null) return this;
	for(int i=0; i<pts.size(); i++){
	    pts.get(i)[0].add(v);
	    pts.get(i)[1].add(v);
	}
	return this;
    }
    
    public IFoldableStrip rot(IVecI axis, double angle){
	if(pts==null) return this;
	for(int i=0; i<pts.size(); i++){
	    pts.get(i)[0].rot(axis,angle);
	    pts.get(i)[1].rot(axis,angle);
	}
	return this;
    }
    
    /** unfold at the location of the first face on its plane normal. */
    public IFoldableStrip unfold(){
	IVecI n0 = nml(0);
	for(int i=1; i<pts.size()-1; i++){
	    IVecI n = nml(i);
	    IVecI center = pts.get(i)[0];
	    IVecI axis = pts.get(i)[1].dif(center);
	    double angle = n.angle(n0, axis);
	    IG.p("unfold angle = "+angle); //
	    fold(i, angle);
	}
	return this;
    }
    
    /** unfold at the location of the first face on the specified plane normal. */
    public IFoldableStrip unfold(IVecI planeNormal){ return unfold(planeNormal,null); }
    
    /** unfold at the location of the first face on the specified plane normal. */
    public IFoldableStrip unfold(IVecI planeNormal, IVecI stripTangent){
	// orient first face
	IVecI n = nml(0);
	IVecI axis = n.cross(planeNormal);
	double angle = n.angle(planeNormal,axis);
	rot(axis,angle);
	if(pts.size()>0 && stripTangent!=null){
	    // orient tangent dir
	    IVecI t = pts.get(1)[0].dif(pts.get(0)[0]);
	    if(t.len()<IConfig.tolerance){ t = pts.get(1)[1].dif(pts.get(0)[1]); }
	    double tangle = t.angle(stripTangent, planeNormal);
	    rot(planeNormal, tangle);
	}
	return unfold();
    }
    /** unfold at the specified point on the the specified plane normal */
    //public IFoldableStrip unfold(IVecI planePt, IVecI planeNormal){} //
    
    /** unfold at the specified point on the the specified plane normal */
    public IFoldableStrip unfold(IVecI planePt, IVecI planeNormal, IVecI stripTangent){
	if(pts==null||pts.size()==0){ IOut.err("fold line points are null"); return this; }
	IVecI dif = planePt.dif(pts.get(0)[0]);
	for(int i=0; i<pts.size(); i++){
	    pts.get(i)[0].add(dif);
	    pts.get(i)[1].add(dif);
	}
	return unfold(planeNormal, stripTangent);
    }
    
    public IFoldableStrip fold(int foldLineIndex, double angle){
	if(pts==null){ IOut.err("fold line points are null"); return this; }
	if(foldLineIndex<0 || foldLineIndex>=pts.size()-1){
	    IOut.err("invalid index("+foldLineIndex+") for "+pts.size()+" folding lines");
	    return null;
	}
	
	//IVec n = nml(foldLineIndex);
 	IVecI center = pts.get(foldLineIndex)[0];
	IVecI axis = pts.get(foldLineIndex)[1].dif(center);
	
	for(int i=foldLineIndex+1; i<pts.size(); i++){
	    pts.get(i)[0].rot(center, axis, angle);
	    pts.get(i)[1].rot(center, axis, angle);
	}
	return this;
    }
    
    /* get a normal vector of specified face at foldinLineIndex */
    public IVecI nml(int foldLineIndex){
	if(foldLineIndex<0 || foldLineIndex>=pts.size()-1){
	    IOut.err("invalid index("+foldLineIndex+") for "+pts.size()+" folding lines");
	    return null;
	}
	if(pts.get(foldLineIndex)[0].eq(pts.get(foldLineIndex+1)[0])){
	    // [0][0], [0][1], [1][1]
	    return pts.get(foldLineIndex)[0].nml(pts.get(foldLineIndex)[1], pts.get(foldLineIndex+1)[1]);
	}
	// [0][0], [0][1], [1][0]
	return pts.get(foldLineIndex)[0].nml(pts.get(foldLineIndex)[1], pts.get(foldLineIndex+1)[0]);
    }
    
}
