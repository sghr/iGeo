/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

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

package igeo.geo;

import igeo.core.*;
import igeo.gui.*;
import java.util.ArrayList;
import java.awt.Color;


/**
   Class BRep (Boundary Representation) of IObject.
   Implementation of BRep is not complete yet.
   To be completed later.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IBrep extends IObject implements ITransformable{
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
    
    public IBrep(ISurfaceGeo[] srfs){ this(null, srfs); }
    
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
    
    public IBrep(IServerI s, ISurfaceGeo[] srfs){ super(s); surfaces = srfs; initBrep(s); }
    
    public IBrep(IBrep brep){ this(brep.server,brep); }
    
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
    
    
    public void initBrep(IServerI s){
	parameter = null; // !!! what should this be? IBrepGeo?
	if(graphics == null) initGraphic(s);
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
        if(m.isGL()) return new IBrepGraphicGL(this);
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
    
    @Override public IBrep dup(){ return new IBrep(this); }
    
    /*
    @Override public void del(){
	super.del();
        for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).del();
    }
    */
    
    public IBrep name(String nm){ super.name(nm); return this; }
    public IBrep layer(ILayer l){ super.layer(l); return this; }
    
    public IBrep hide(){ super.hide(); return this; }
    public IBrep show(){ super.show(); return this; }
    
    public IBrep clr(Color c){ super.clr(c); return this; }
    public IBrep clr(int gray){ super.clr(gray); return this; }
    public IBrep clr(float fgray){ super.clr(fgray); return this; }
    public IBrep clr(double dgray){ super.clr(dgray); return this; }
    public IBrep clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IBrep clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IBrep clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IBrep clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IBrep clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IBrep clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IBrep clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IBrep clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IBrep clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IBrep hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IBrep hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IBrep hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IBrep hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IBrep setColor(Color c){ super.setColor(c); return this; }
    public IBrep setColor(int gray){ super.setColor(gray); return this; }
    public IBrep setColor(float fgray){ super.setColor(fgray); return this; }
    public IBrep setColor(double dgray){ super.setColor(dgray); return this; }
    public IBrep setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IBrep setColor(float fgray, int falpha){ super.setColor(fgray,falpha); return this; }
    public IBrep setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IBrep setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IBrep setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IBrep setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IBrep setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IBrep setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IBrep setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IBrep setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IBrep setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IBrep setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IBrep setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }


    /*******************************************************************************
     * implementation of ITransformable interface
     ******************************************************************************/
        
    public IBrep add(double x, double y, double z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].add(x,y,z);
	return this;
    }
    public IBrep add(IDoubleI x, IDoubleI y, IDoubleI z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].add(x,y,z);
	return this;
    }
    public IBrep add(IVecI v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].add(v);
	return this;
    }
    public IBrep sub(double x, double y, double z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].sub(x,y,z);
	return this;
    }
    public IBrep sub(IDoubleI x, IDoubleI y, IDoubleI z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].sub(x,y,z);
	return this;
    }
    public IBrep sub(IVecI v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].sub(v);
	return this;
    }
    public IBrep mul(IDoubleI v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mul(v);
	return this;
    }
    public IBrep mul(double v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mul(v);
	return this;
    }
    public IBrep div(IDoubleI v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].div(v);
	return this;
    }
    public IBrep div(double v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].div(v);
	return this;
    }
    
    public IBrep neg(){
	for(int i=0; i<surfaces.length; i++) surfaces[i].neg();
	return this;
    }
    /** alias of neg */
    //public IBrep rev(); // rev is used in curve to revrse u parameter
    /** alias of neg */
    public IBrep flip(){
	for(int i=0; i<surfaces.length; i++) surfaces[i].flip();
	return this;
    }
    
    
    /** scale add */
    public IBrep add(IVecI v, double f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].add(v,f);
	return this;
    }
    public IBrep add(IVecI v, IDoubleI f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].add(v,f);
	return this;
    }
    
    public IBrep rot(IVecI axis, IDoubleI angle){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(axis,angle);
	return this;
    }
    public IBrep rot(IVecI axis, double angle){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(axis,angle);
	return this;
    }
    
    public IBrep rot(IVecI center, IVecI axis, IDoubleI angle){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(center,axis,angle);
	return this;
    }
    public IBrep rot(IVecI center, IVecI axis, double angle){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(center,axis,angle);
	return this;
    }
    
    /** rotate to destination direction vector */
    public IBrep rot(IVecI axis, IVecI destDir){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(axis,destDir);
	return this;
    }
    /** rotate to destination point location */    
    public IBrep rot(IVecI center, IVecI axis, IVecI destPt){
	for(int i=0; i<surfaces.length; i++) surfaces[i].rot(center,axis,destPt);
	return this;
    }
    
    
    /** alias of mul */
    public IBrep scale(IDoubleI f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale(f);
	return this;
    }
    public IBrep scale(double f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale(f);
	return this;
    }
    public IBrep scale(IVecI center, IDoubleI f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale(center,f);
	return this;
    }
    public IBrep scale(IVecI center, double f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale(center,f);
	return this;
    }
    
    
    /** scale only in 1 direction */
    public IBrep scale1d(IVecI axis, double f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale1d(axis,f);
	return this;
    }
    public IBrep scale1d(IVecI axis, IDoubleI f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale1d(axis,f);
	return this;
    }
    public IBrep scale1d(IVecI center, IVecI axis, double f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale1d(center,axis,f);
	return this;
    }
    public IBrep scale1d(IVecI center, IVecI axis, IDoubleI f){
	for(int i=0; i<surfaces.length; i++) surfaces[i].scale1d(center,axis,f);
	return this;
    }
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IBrep ref(IVecI planeDir){
	for(int i=0; i<surfaces.length; i++) surfaces[i].ref(planeDir);
	return this;
    }
    public IBrep ref(IVecI center, IVecI planeDir){
	for(int i=0; i<surfaces.length; i++) surfaces[i].ref(center,planeDir);
	return this;
    }
    /** mirror is alias of ref */
    public IBrep mirror(IVecI planeDir){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mirror(planeDir);
	return this;
    }
    public IBrep mirror(IVecI center, IVecI planeDir){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mirror(center,planeDir);
	return this;
    }
    
    
    /** shear operation */
    public IBrep shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IBrep shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IBrep shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IBrep shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    public IBrep shearXY(double sxy, double syx){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearXY(sxy,syx);
	return this;
    }
    public IBrep shearXY(IDoubleI sxy, IDoubleI syx){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearXY(sxy,syx);
	return this;
    }
    public IBrep shearXY(IVecI center, double sxy, double syx){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearXY(center,sxy,syx);
	return this;
    }
    public IBrep shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearXY(center,sxy,syx);
	return this;
    }
    
    public IBrep shearYZ(double syz, double szy){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearYZ(syz,szy);
	return this;
    }
    public IBrep shearYZ(IDoubleI syz, IDoubleI szy){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearYZ(syz,szy);
	return this;
    }
    public IBrep shearYZ(IVecI center, double syz, double szy){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearYZ(center,syz,szy);
	return this;
    }
    public IBrep shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearYZ(center,syz,szy);
	return this;
    }
    
    public IBrep shearZX(double szx, double sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearZX(szx,sxz);
	return this;
    }
    public IBrep shearZX(IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearZX(szx,sxz);
	return this;
    }
    public IBrep shearZX(IVecI center, double szx, double sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearZX(center,szx,sxz);
	return this;
    }
    public IBrep shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<surfaces.length; i++) surfaces[i].shearZX(center,szx,sxz);
	return this;
    }
    
    /** mv() is alias of add() */
    public IBrep mv(double x, double y, double z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mv(x,y,z);
	return this;
    }
    public IBrep mv(IDoubleI x, IDoubleI y, IDoubleI z){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mv(x,y,z);
	return this;
    }
    public IBrep mv(IVecI v){
	for(int i=0; i<surfaces.length; i++) surfaces[i].mv(v);
	return this;
    }
    
    /** cp() is alias of dup() */ 
    public IBrep cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IBrep cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IBrep cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IBrep cp(IVecI v){ return dup().add(v); }
    
    
    /** translate() is alias of add() */
    public IBrep translate(double x, double y, double z){ return add(x,y,z); }
    public IBrep translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IBrep translate(IVecI v){ return add(v); }
    
    
    public IBrep transform(IMatrix3I mat){
	for(int i=0; i<surfaces.length; i++) surfaces[i].transform(mat);
	return this;
    }
    public IBrep transform(IMatrix4I mat){
	for(int i=0; i<surfaces.length; i++) surfaces[i].transform(mat);
	return this;
    }
    public IBrep transform(IVecI xvec, IVecI yvec, IVecI zvec){
	for(int i=0; i<surfaces.length; i++) surfaces[i].transform(xvec,yvec,zvec);
	return this;
    }
    public IBrep transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	for(int i=0; i<surfaces.length; i++) surfaces[i].transform(xvec,yvec,zvec,translate);
	return this;
    }
    
}
