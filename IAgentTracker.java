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

import java.lang.reflect.Array;

/**
   dynamic behavior for a geometry object to track particle location.
   this class needs to be added under point agent (IPointAgent class) or its subclasses like IParticle and IBoid.
   
   @author Satoru Sugihara
*/
public class IAgentTracker extends IDynamicsBase{
    /** set agent's attributes to geometry objects */
    static public boolean setAgentAttributes=true;
    static public IVec defaultOrientation = new IVec(0,1,0);
    
    public IGeometry[] geometries;
    public IPointAgent agent;
    /** if agent is particle, it's casted to particle variable */
    public IParticleI particle;
    public IVec prevPos;
    public IVec prevVel;
    public IVec rotAxis;
    public double rotAngle=0;
    
    
    public boolean orient=true;
    
    /*
    public IAgentTracker(IGeometry... geometries){
	this.geometries = geometries;
    }
    */
    public IAgentTracker(IPointAgent p, IGeometry... geometries){
	this.geometries = geometries;
	initTracker(p);
    }
    
    /*
    public IAgentTracker(IVecI geometryOrigin, IGeometry... geometries){
	this.geometries = geometries;
    }
    */
    public IAgentTracker(IPointAgent p, IVecI geometryOrigin, IGeometry[] geometries){
	this.geometries = geometries;
	initTracker(geometryOrigin, p);
    }
    
    public IAgentTracker(IPointAgent p, IVecI geometryOrigin, IVecI geometryOrientation, IGeometry[] geometries){
	this.geometries = geometries;
	initTracker(geometryOrigin, geometryOrientation, p);
    }
    
    /** total number of geometries */
    public int geometryNum(){ return geometries.length; }
    /** returns first geomery */
    public IGeometry geometry(){ if(geometries.length==0){ return null; } return geometries[0]; }
    /** returns n-th geometry */
    public IGeometry geometry(int i){ return geometries[i]; }
    /** returns all geometries */
    public IGeometry[] geometries(){ return geometries; }
    
    
    /** returns all contained geometries in a specified type */
    @SuppressWarnings({"unchecked"})
    public <T extends IGeometry> T[] subgeometries(Class<T> cls){
	//T[] geo = new T[subgeometryNum<T>()];
	T[] geo = (T[])Array.newInstance(cls, subgeometryNum(cls));
	for(int i=0, idx=0; i<geometries.length; i++) 
	    if(cls.isInstance(geometries[i])) geo[idx++] = (T)geometries[i];
	return geo;
    }
    
    /** returns first geometry in a specified type */
    @SuppressWarnings({"unchecked"})
    public <T extends IGeometry> T subgeometry(Class<T> cls){
	for(int i=0; i<geometries.length; i++) if(cls.isInstance(geometries[i])) return (T)geometries[i];
	return null; // not found
    }
    
    /** returns n-th geometry in a specified type */
    @SuppressWarnings({"unchecked"})
	public <T extends IGeometry> T subgeometry(Class<T> cls, int index){
	for(int i=0, idx=0; i<geometries.length; i++){
	    if(cls.isInstance(geometries[i])){
		if(idx == index) return (T)geometries[i];
		idx++;
	    }
	}
	return null; // not found
    }
    
    /** returns number of geometries in a specified type */
    public int subgeometryNum(Class<? extends IGeometry> cls){
	int num=0;
	for(int i=0; i<geometries.length; i++) if(cls.isInstance(geometries[i])) num++;
	return num;
    }
    
    
    /** returns all contained points */
    public IPoint[] points(){ return this.<IPoint>subgeometries(IPoint.class); }
    /** returns first point */
    public IPoint point(){ return this.<IPoint>subgeometry(IPoint.class); }
    /** returns n-th point */
    public IPoint point(int index){ return this.<IPoint>subgeometry(IPoint.class, index); }
    /** returns point num */
    public int pointNum(){ return this.subgeometryNum(IPoint.class); }
    
    
    /** returns all contained curves */
    public ICurve[] curves(){ return this.<ICurve>subgeometries(ICurve.class); }
    /** returns first curve */
    public ICurve curve(){ return this.<ICurve>subgeometry(ICurve.class); }
    /** returns n-th curve */
    public ICurve curve(int index){ return this.<ICurve>subgeometry(ICurve.class, index); }
    /** returns curve num */
    public int curveNum(){ return this.subgeometryNum(ICurve.class); }
    
    
    /** returns all contained surfaces */
    public ISurface[] surfaces(){ return this.<ISurface>subgeometries(ISurface.class); }
    /** returns first surface */
    public ISurface surface(){ return this.<ISurface>subgeometry(ISurface.class); }
    /** returns n-th surface */
    public ISurface surface(int index){ return this.<ISurface>subgeometry(ISurface.class, index); }
    /** returns surface num */
    public int surfaceNum(){ return this.subgeometryNum(ISurface.class); }
    
    
    /** returns all contained breps */
    public IBrep[] breps(){ return this.<IBrep>subgeometries(IBrep.class); }
    /** returns first brep */
    public IBrep brep(){ return this.<IBrep>subgeometry(IBrep.class); }
    /** returns n-th surface */
    public IBrep brep(int index){ return this.<IBrep>subgeometry(IBrep.class, index); }
    /** returns brep num */
    public int brepNum(){ return this.subgeometryNum(IBrep.class); }
    
    /** returns all contained meshes */
    public IMesh[] meshes(){ return this.<IMesh>subgeometries(IMesh.class); }
    /** returns first mesh */
    public IMesh mesh(){ return this.<IMesh>subgeometry(IMesh.class); }
    /** returns n-th surface */
    public IMesh mesh(int index){ return this.<IMesh>subgeometry(IMesh.class, index); }
    /** returns mesh num */
    public int meshNum(){ return this.subgeometryNum(IMesh.class); }
    
    
    
    
    public IAgentTracker show(){
	for(int i=0; i<geometries.length; i++) geometries[i].show();
	return this; 
    }
    
    public IAgentTracker hide(){
	for(int i=0; i<geometries.length; i++) geometries[i].hide();
	return this; 
    }
    
    /** delete all geometries */
    public void del(){
	for(int i=0; i<geometries.length; i++) geometries[i].del(); // is this ok?
	geometries = new IGeometry[0]; // to be safe
	parent.deleteDynamics(this);
    }
    
    
    public IAgentTracker orient(boolean f){
	if(particle!=null){ orient = f; }
	else{ IOut.err("parent is not particle. orient is changed."); }
	return this;
    }
    
    
    public IAgentTracker clr(IColor c){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(c);
	return this;
    }
    public IAgentTracker clr(IColor c, int alpha){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(c,alpha);
	return this;
    }
    public IAgentTracker clr(IColor c, float alpha){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(c,alpha);
	return this;
    }
    public IAgentTracker clr(IColor c, double alpha){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(c,alpha);
	return this;
    }
    //public IAgentTracker clr(Color c){ super.clr(c); point.clr(c); return this; }
    //public IAgentTracker clr(Color c, int alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    //public IAgentTracker clr(Color c, float alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    //public IAgentTracker clr(Color c, double alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    public IAgentTracker clr(int gray){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(gray);
	return this;
    }
    public IAgentTracker clr(float fgray){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(fgray);
	return this;
    }
    public IAgentTracker clr(double dgray){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(dgray);
	return this;
    }
    public IAgentTracker clr(int gray, int alpha){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(gray,alpha);
	return this;
    }
    public IAgentTracker clr(float fgray, float falpha){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(fgray,falpha);
	return this;
    }
    public IAgentTracker clr(double dgray, double dalpha){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(dgray,dalpha);
	return this;
    }
    public IAgentTracker clr(int r, int g, int b){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(r,g,b);
	return this;
    }
    public IAgentTracker clr(float fr, float fg, float fb){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(fr,fg,fb);
	return this;
    }
    public IAgentTracker clr(double dr, double dg, double db){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(dr,dg,db);
	return this;
    }
    public IAgentTracker clr(int r, int g, int b, int a){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(r,g,b,a);
	return this;
    }
    public IAgentTracker clr(float fr, float fg, float fb, float fa){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(fr,fg,fb,fa);
	return this;
    }
    public IAgentTracker clr(double dr, double dg, double db, double da){
	for(int i=0; i<geometries.length; i++) geometries[i].clr(dr,dg,db,da);
	return this;
    }
    public IAgentTracker hsb(float h, float s, float b, float a){
	for(int i=0; i<geometries.length; i++) geometries[i].hsb(h,s,b,a);
	return this;
    }
    public IAgentTracker hsb(double h, double s, double b, double a){
	for(int i=0; i<geometries.length; i++) geometries[i].hsb(h,s,b,a);
	return this;
    }
    public IAgentTracker hsb(float h, float s, float b){
	for(int i=0; i<geometries.length; i++) geometries[i].hsb(h,s,b);
	return this;
    }
    public IAgentTracker hsb(double h, double s, double b){
	for(int i=0; i<geometries.length; i++) geometries[i].hsb(h,s,b);
	return this;
    }
    
    
    
    /** move geometries from its center to the current position of the parent agent */
    public void initTracker(IPointAgent p){
	IVec center = new IVec();
	for(int i=0; i<geometries.length; i++) center.add(geometries[i].center());
	center.div(geometries.length);
	initTracker(center, p);
    }
    
    public void initTracker(IVecI geometryOrigin, IPointAgent p){
	initTracker(geometryOrigin, defaultOrientation, p);
    }
    
    public void initTracker(IVecI geometryOrigin, IVecI geometryOrientation, IPointAgent p){
	agent = p;
	agent.addDynamics(this);
	
	if(agent instanceof IParticleI){ particle = (IParticleI)agent; }
	else{ orient=false; }
	if(geometryOrientation==null){ orient=false; }
	
	if(setAgentAttributes){
	    if(agent.attr()!=null){
		for(int i=0; i<geometries.length; i++){
		    if(geometries[i].attr()!=null){ geometries[i].attr().merge(agent.attr()); }
		    else{ geometries[i].attr(agent.attr()); }
		}
	    }
	    else if(agent.clr()!=null){
		if(agent.clr()!=null){
		    for(int i=0; i<geometries.length; i++) geometries[i].clr(agent.clr());
		}
	    }
	}
	
	IVec dif = agent.pos().dif(geometryOrigin);
	
	boolean modified=false;
	if(dif.len()>0){
	    for(int i=0; i<geometries.length; i++){ geometries[i].add(dif); }
	    modified=true;
	}
	
	prevPos = agent.pos().dup();
	if( particle!=null && geometryOrientation!=null ){
	    prevVel = particle.vel().dup();
	    if(prevVel.len()==0){ prevVel = geometryOrientation.get(); }
	    else if(geometryOrientation.len()>0){
		rotAngle = geometryOrientation.angle(prevVel);
		rotAxis = geometryOrientation.get().cross(prevVel);
		if(rotAngle!=0){ 
		    for(int i=0; i<geometries.length; i++){
			geometries[i].rot(agent.pos(),rotAxis,rotAngle);
		    }
		    modified=true;
		}
	    }
	}
	if(modified){
	    for(int i=0; i<geometries.length; i++){ geometries[i].updateGraphic(); }
	}
	
    }
    
    public void update(){
	prevPos.sub(agent.pos()).neg(); // setting difference
	if(orient && particle!=null){
	    if(particle.vel().len()==0) rotAngle=0;
	    //if(particle.vel().len()<IConfig.tolrance) rotAngle=0;
	    else{
		rotAngle = prevVel.angle(particle.vel());
		rotAxis = prevVel.cross(particle.vel());
		prevVel = particle.vel().dup(); // already updated
		
		if(rotAxis.len()==0){ rotAngle=0; }
	    }
	}
	
	boolean modified=false;
	if(prevPos.len()>0){
	    for(int i=0; i<geometries.length; i++){ geometries[i].add(prevPos); }
	    modified=true;
	}
	if(orient && rotAngle!=0){ 
	    for(int i=0; i<geometries.length; i++){
		if(orient && rotAngle!=0){ geometries[i].rot(agent.pos(),rotAxis,rotAngle); }
	    }
	    modified=true;
	}
	if(modified){
	    for(int i=0; i<geometries.length; i++){ geometries[i].updateGraphic(); }
	}
	
	prevPos.set(agent.pos());
    }
    
    
}
