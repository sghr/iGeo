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

import igeo.gui.*;
import java.util.ArrayList;
import java.awt.Color;

/**
   Class BRep (Boundary Representation) of IObject.
   Implementation of BRep is not complete yet.
   To be completed later.
   
   @author Satoru Sugihara
*/
public class IBrep extends IGeometry implements ITransformable{
    //public ArrayList<ISurface> surfaces;
    //public ArrayList<ISurfaceGeo> surfaces;
    public ISurfaceGeo[] surfaces;
    
    /** seams defines connection of surfaces (?) */
    //public ArrayList<ICurveGeo> seams;
    //public ICurveGeo[] seams; 
    
    //public ArrayList<IVec> seamPoints;
    //public IVec[] seamPoints;
    
    public boolean solid=false;
    
    
    //public IBrep(){ this((IServerI)null); }
    
    //public IBrep(ISurfaceGeo[] srfs, ICurveGeo[] seams, IVec[] seamPts){ this(null, srfs, seams, seamPts); }
    
    //public IBrep(IServerI s){ super(s); surfaces = new ArrayList<ISurfaceGeo>(); }
    
    /*
    public IBrep(IServerI s, ISurfaceGeo[] srfs, ICurveGeo[] seams, IVec[] seamPts){
	super(s);
	surfaces = srfs;
	this.seams = seams;
	seamPoints = seamPts;
	initBrep(s);
    }
    */
    
    public IBrep(IServerI s, ISurfaceGeo[] srfs){ super(s); initBrepWithSurfaceGeo(s, srfs); }
    
    public IBrep(IServerI s, ISurface[] srfs){ super(s); initBrepWithSurface(s,srfs); }
    
    public IBrep(IServerI s, ICurveI[] outlines){ super(s); initBrepWithCurve(s, outlines, null); }
    
    public IBrep(IServerI s, ICurveI[] outlines, ICurveI[][] holes){ super(s); initBrepWithCurve(s, outlines, holes); }
    
    public IBrep(IServerI s, IVecI[][] outlinePoints){ super(s); initBrepWithPoint(s, outlinePoints); }
    
    
    public IBrep(IServerI s, IBrep brep){
	super(s,brep);
	//surfaces = new ArrayList<ISurfaceGeo>(brep.surfaces.size());
	//for(int i=0; i<brep.surfaces.length; i++)surfaces.add(brep.surfaces.get(i).dup()); // deep copy
	if(brep.surfaces!=null){
	    surfaces = new ISurfaceGeo[brep.surfaces.length];
	    for(int i=0; i<surfaces.length; i++) surfaces[i] = brep.surfaces[i].dup(); // deep copy
	}
	solid = brep.solid;
	/*
	if(brep.seams!=null){
	    seams = new ICurveGeo[brep.seams.length];
	    for(int i=0; i<seams.length; i++) seams[i] = brep.seams[i].dup(); // deep copy
	}
	if(brep.seamPoints!=null){
	    seamPoints = new IVec[brep.seamPoints.length];
	    for(int i=0; i<seamPoints.length; i++) seamPoints[i] = brep.seamPoints[i].dup(); // deep copy
	}
	*/
	initBrep(s);
    }
    
    public IBrep(ISurfaceGeo[] srfs){ this(null, srfs); }
    
    public IBrep(IBrep brep){ this(brep.server,brep); }
    
    public IBrep(ISurface[] srfs){ this(null,srfs); }
    public IBrep(ICurveI[] outlines){ this(null,outlines); }
    public IBrep(ICurveI[] outlines, ICurveI[][] holes){ this(null,outlines,holes); }
    public IBrep(IVecI[][] outlinePoints){ this(null,outlinePoints); }
    
    
    public void initBrep(IServerI s){
	parameter = null; // !!! what should this be? IBrepGeo?
	if(graphics == null) initGraphic(s);
	
	if(IConfig.checkDuplicatedControlPoint){ checkDuplicatedPoints(); }
    }
    
    public void initBrepWithSurfaceGeo(IServerI s, ISurfaceGeo[] srfs){
	if(srfs==null){
	    IOut.err("input surface is null");
	    return;
	}
	surfaces = srfs;
	initBrep(s);
    }
    
    public void initBrepWithSurface(IServerI s, ISurface[] srfs){
	if(srfs==null){
	    IOut.err("input surface is null");
	    return;
	}
	surfaces = new ISurfaceGeo[srfs.length];
	for(int i=0; i<srfs.length; i++){
	    surfaces[i] = srfs[i].surface;
	}
	initBrep(s);
    }
    
    public void initBrepWithCurve(IServerI s, ICurveI[] outlines, ICurveI[][] holes){
	if(outlines==null){
	    IOut.err("input curve is null");
	    return;
	}
	
	surfaces = new ISurfaceGeo[outlines.length];
	
	if(holes!=null && holes.length!=outlines.length){
	    IOut.err("number of holes does not match with outlines");
	}
	
	for(int i=0; i<outlines.length; i++){
	    if(holes!=null && i < holes.length && holes[i]!=null){
		surfaces[i] = new ISurfaceGeo(outlines[i], holes[i]);
	    }
	    else{
		surfaces[i] = new ISurfaceGeo(outlines[i]);
	    }
	}
	initBrep(s);
    }
    
    public void initBrepWithPoint(IServerI s, IVecI[][] points){
	if(points==null){
	    IOut.err("input points are null");
	    return;
	}
	surfaces = new ISurfaceGeo[points.length];
	for(int i=0; i<points.length; i++){
	    surfaces[i] = new ISurfaceGeo(points[i]);
	}
	initBrep(s);
    }
    
    
    public void checkDuplicatedPoints(){
	boolean changed=false;
	for(int i=0; i<surfaces.length; i++){
	    
	    for(int j=0; j<surfaces[i].ucpNum(); j++){
		for(int k=0; k<surfaces[i].vcpNum(); k++){
		    IVecI cp1 = surfaces[i].cp(j,k);
		    for(int l=i+1; l<surfaces.length; l++){
			for(int m=0; m<surfaces[l].ucpNum(); m++){
			    for(int n=0; n<surfaces[l].vcpNum(); n++){
				if(surfaces[l].cp(m,n) == cp1){ // same instance
				    surfaces[l].controlPoints[m][n] = cp1.dup();
				    changed=true;
				}
			    }
			}
		    }
		}
	    }
	}
	if(changed){ updateGraphic(); }
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
	if(m.isNone()) return null;
        if(m.isGraphic3D()) return new IBrepGraphicGL(this);
        return null;
    }
    
    /*
    public IBrep add(ISurfaceGeo s){ surfaces.add(s); return this; }
    
    public IBrep addSeam(ICurveGeo crv){
	if(seams==null) seams = new ArrayList<ICurveGeo>();
	seams.add(crv);
	return this;
    }
    
    public IBrep addSeamPoint(IVec pt){
	if(seamPoints==null) seamPoints = new ArrayList<IVec>();
	seamPoints.add(pt);
	return this;
    }
    */
    
    public ISurfaceGeo surface(int i){ return surfaces[i]; }
    
    public int surfaceNum(){ return surfaces.length; }

    public ISurfaceGeo[] surfaces(){ return surfaces; }
    
    @Override public IBrep dup(){ return new IBrep(this); }
    
    /** calculate center by taking average of centers of all surfaces */
    synchronized public IVecI center(){
	if(surfaces==null || surfaces.length==0) return null;
	IVecI cnt = surfaces[0].center();
	for(int i=1; i<surfaces.length; i++) cnt.add(surfaces[i].center());
	cnt.div(surfaces.length);
	return cnt;
    }
    
    
    /** checking parameters validity. */
    synchronized public boolean isValid(){
	if(surfaces==null || surfaces.length==0) return false;
	for(int i=0; i<surfaces.length; i++){
	    if(!surfaces[i].isValid()) return false;
	}
	return true;
    }
    
    /*
    @Override public void del(){
	super.del();
        for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).del();
    }
    */
    
    synchronized public IBrep name(String nm){ super.name(nm); return this; }
    synchronized public IBrep layer(ILayer l){ super.layer(l); return this; }
    synchronized public IBrep layer(String l){ super.layer(l); return this; }
    
    synchronized public IBrep attr(IAttribute at){ super.attr(at); return this; }
    
    synchronized public IBrep hide(){ super.hide(); return this; }
    synchronized public IBrep show(){ super.show(); return this; }
    
    synchronized public IBrep clr(IColor c){ super.clr(c); return this; }
    synchronized public IBrep clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public IBrep clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    synchronized public IBrep clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    synchronized public IBrep clr(Color c){ super.clr(c); return this; }
    synchronized public IBrep clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public IBrep clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    synchronized public IBrep clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    synchronized public IBrep clr(int gray){ super.clr(gray); return this; }
    synchronized public IBrep clr(float fgray){ super.clr(fgray); return this; }
    synchronized public IBrep clr(double dgray){ super.clr(dgray); return this; }
    synchronized public IBrep clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    synchronized public IBrep clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    synchronized public IBrep clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    synchronized public IBrep clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    synchronized public IBrep clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    synchronized public IBrep clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    synchronized public IBrep clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    synchronized public IBrep clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    synchronized public IBrep clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    synchronized public IBrep hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    synchronized public IBrep hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    synchronized public IBrep hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    synchronized public IBrep hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    synchronized public IBrep setColor(IColor c){ super.setColor(c); return this; }
    synchronized public IBrep setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public IBrep setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    synchronized public IBrep setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    synchronized public IBrep setColor(Color c){ super.setColor(c); return this; }
    synchronized public IBrep setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public IBrep setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    synchronized public IBrep setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    synchronized public IBrep setColor(int gray){ super.setColor(gray); return this; }
    synchronized public IBrep setColor(float fgray){ super.setColor(fgray); return this; }
    synchronized public IBrep setColor(double dgray){ super.setColor(dgray); return this; }
    synchronized public IBrep setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    synchronized public IBrep setColor(float fgray, int falpha){ super.setColor(fgray,falpha); return this; }
    synchronized public IBrep setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    synchronized public IBrep setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    synchronized public IBrep setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    synchronized public IBrep setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    synchronized public IBrep setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    synchronized public IBrep setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    synchronized public IBrep setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    synchronized public IBrep setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public IBrep setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public IBrep setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    synchronized public IBrep setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    synchronized public IBrep weight(double w){ super.weight(w); return this; }
    synchronized public IBrep weight(float w){ super.weight(w); return this; }
        
    

    /*******************************************************************************
     * implementation of ITransformable interface
     ******************************************************************************/
        
    synchronized public IBrep add(double x, double y, double z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].add(x,y,z);
	return this;
    }
    synchronized public IBrep add(IDoubleI x, IDoubleI y, IDoubleI z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].add(x,y,z);
	return this;
    }
    synchronized public IBrep add(IVecI v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].add(v);
	return this;
    }
    synchronized public IBrep sub(double x, double y, double z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].sub(x,y,z);
	return this;
    }
    synchronized public IBrep sub(IDoubleI x, IDoubleI y, IDoubleI z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].sub(x,y,z);
	return this;
    }
    synchronized public IBrep sub(IVecI v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].sub(v);
	return this;
    }
    synchronized public IBrep mul(IDoubleI v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mul(v);
	return this;
    }
    synchronized public IBrep mul(double v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mul(v);
	return this;
    }
    synchronized public IBrep div(IDoubleI v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].div(v);
	return this;
    }
    synchronized public IBrep div(double v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].div(v);
	return this;
    }
    
    synchronized public IBrep neg(){
	for(int i=0; i<surfaces.length; i++) surfaces[i].neg();
	return this;
    }
    /** alias of neg */
    //synchronized public IBrep rev(); // rev is used in curve to revrse u parameter
    /** alias of neg */
    synchronized public IBrep flip(){
	for(int i=0; i<surfaces.length; i++) surfaces[i].flip();
	return this;
    }
    
    
    /** scale add */
    synchronized public IBrep add(IVecI v, double f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].add(v,f);
	return this;
    }
    synchronized public IBrep add(IVecI v, IDoubleI f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].add(v,f);
	return this;
    }
    /** scale add alias */
    synchronized public IBrep add(double f, IVecI v){ return add(v,f); }
    synchronized public IBrep add(IDoubleI f, IVecI v){ return add(v,f); }
    
    synchronized public IBrep rot(IDoubleI angle){
	for(int i=0; i<surfaces.length; i++){ surfaces[i].rot(angle); } return this;
    }
    synchronized public IBrep rot(double angle){
	for(int i=0; i<surfaces.length; i++){ surfaces[i].rot(angle); } return this;
    }
    
    synchronized public IBrep rot(IVecI axis, IDoubleI angle){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(axis,angle);
	return this;
    }
    synchronized public IBrep rot(IVecI axis, double angle){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(axis,angle);
	return this;
    }
    
    synchronized public IBrep rot(IVecI center, IVecI axis, IDoubleI angle){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(center,axis,angle);
	return this;
    }
    synchronized public IBrep rot(IVecI center, IVecI axis, double angle){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(center,axis,angle);
	return this;
    }
    
    /** rotate to destination direction vector */
    synchronized public IBrep rot(IVecI axis, IVecI destDir){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(axis,destDir);
	return this;
    }
    /** rotate to destination point location */    
    synchronized public IBrep rot(IVecI center, IVecI axis, IVecI destPt){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(center,axis,destPt);
	return this;
    }
    
    synchronized public IBrep rot2(IDoubleI angle){ return rot(angle); }
    synchronized public IBrep rot2(double angle){ return rot(angle); }
    synchronized public IBrep rot2(IVecI center, IDoubleI angle){
	for(int i=0; i<surfaces.length; i++){ surfaces[i].rot2(center,angle); }	return this;
    }
    synchronized public IBrep rot2(IVecI center, double angle){
	for(int i=0; i<surfaces.length; i++){ surfaces[i].rot2(center,angle); }	return this;
    }
    
    /** rotation on xy-plane to destination direction vector */
    synchronized public IBrep rot2(IVecI destDir){
	for(int i=0; i<surfaces.length; i++){ surfaces[i].rot2(destDir); } return this;
    }
    /** rotation on xy-plane to destination point location */    
    synchronized public IBrep rot2(IVecI center, IVecI destPt){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot2(center,destPt);
	return this;
    }
    
    
    /** alias of mul */
    synchronized public IBrep scale(IDoubleI f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale(f);
	return this;
    }
    synchronized public IBrep scale(double f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale(f);
	return this;
    }
    synchronized public IBrep scale(IVecI center, IDoubleI f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale(center,f);
	return this;
    }
    synchronized public IBrep scale(IVecI center, double f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale(center,f);
	return this;
    }
    
    
    /** scale only in 1 direction */
    synchronized public IBrep scale1d(IVecI axis, double f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale1d(axis,f);
	return this;
    }
    synchronized public IBrep scale1d(IVecI axis, IDoubleI f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale1d(axis,f);
	return this;
    }
    synchronized public IBrep scale1d(IVecI center, IVecI axis, double f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale1d(center,axis,f);
	return this;
    }
    synchronized public IBrep scale1d(IVecI center, IVecI axis, IDoubleI f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale1d(center,axis,f);
	return this;
    }
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    synchronized public IBrep ref(IVecI planeDir){
	for(int i=0; i<surfaces.length; i++) surfaces[i].ref(planeDir);
	return this;
    }
    synchronized public IBrep ref(IVecI center, IVecI planeDir){
	for(int i=0; i<surfaces.length; i++) surfaces[i].ref(center,planeDir);
	return this;
    }
    /** mirror is alias of ref */
    synchronized public IBrep mirror(IVecI planeDir){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mirror(planeDir);
	return this;
    }
    synchronized public IBrep mirror(IVecI center, IVecI planeDir){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mirror(center,planeDir);
	return this;
    }
    
    
    /** shear operation */
    synchronized public IBrep shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public IBrep shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public IBrep shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public IBrep shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    synchronized public IBrep shearXY(double sxy, double syx){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearXY(sxy,syx);
	return this;
    }
    synchronized public IBrep shearXY(IDoubleI sxy, IDoubleI syx){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearXY(sxy,syx);
	return this;
    }
    synchronized public IBrep shearXY(IVecI center, double sxy, double syx){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearXY(center,sxy,syx);
	return this;
    }
    synchronized public IBrep shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearXY(center,sxy,syx);
	return this;
    }
    
    synchronized public IBrep shearYZ(double syz, double szy){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearYZ(syz,szy);
	return this;
    }
    synchronized public IBrep shearYZ(IDoubleI syz, IDoubleI szy){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearYZ(syz,szy);
	return this;
    }
    synchronized public IBrep shearYZ(IVecI center, double syz, double szy){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearYZ(center,syz,szy);
	return this;
    }
    synchronized public IBrep shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearYZ(center,syz,szy);
	return this;
    }
    
    synchronized public IBrep shearZX(double szx, double sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearZX(szx,sxz);
	return this;
    }
    synchronized public IBrep shearZX(IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearZX(szx,sxz);
	return this;
    }
    synchronized public IBrep shearZX(IVecI center, double szx, double sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearZX(center,szx,sxz);
	return this;
    }
    synchronized public IBrep shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearZX(center,szx,sxz);
	return this;
    }
    
    /** mv() is alias of add() */
    synchronized public IBrep mv(double x, double y, double z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mv(x,y,z);
	return this;
    }
    synchronized public IBrep mv(IDoubleI x, IDoubleI y, IDoubleI z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mv(x,y,z);
	return this;
    }
    synchronized public IBrep mv(IVecI v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mv(v);
	return this;
    }
    
    /** cp() is alias of dup() */ 
    synchronized public IBrep cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    synchronized public IBrep cp(double x, double y, double z){ return dup().add(x,y,z); }
    synchronized public IBrep cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    synchronized public IBrep cp(IVecI v){ return dup().add(v); }
    
    
    /** translate() is alias of add() */
    synchronized public IBrep translate(double x, double y, double z){ return add(x,y,z); }
    synchronized public IBrep translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    synchronized public IBrep translate(IVecI v){ return add(v); }
    
    
    synchronized public IBrep transform(IMatrix3I mat){
	for(int i=0; i<surfaces.length; i++) surfaces[i].transform(mat);
	return this;
    }
    synchronized public IBrep transform(IMatrix4I mat){
	for(int i=0; i<surfaces.length; i++) surfaces[i].transform(mat);
	return this;
    }
    synchronized public IBrep transform(IVecI xvec, IVecI yvec, IVecI zvec){
	for(int i=0; i<surfaces.length; i++) surfaces[i].transform(xvec,yvec,zvec);
	return this;
    }
    synchronized public IBrep transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	for(int i=0; i<surfaces.length; i++) surfaces[i].transform(xvec,yvec,zvec,translate);
	return this;
    }
    
}
