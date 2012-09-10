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
    
    
    public int geometryNum(){ return geometries.length; }
    public IGeometry geometry(int i){ return geometries[i]; }
    /** returns first geomery */
    public IGeometry geometry(){ if(geometries.length==0){ return null; } return geometries[0]; }
    /** returns first curve */
    public ICurve curve(){
	for(int i=0; i<geometries.length; i++) if(geometries[i] instanceof ICurve) return (ICurve)geometries[i];
	return null; // no curve found
    }
    /** returns first surface */
    public ISurface surface(){
	for(int i=0; i<geometries.length; i++) if(geometries[i] instanceof ISurface) return (ISurface)geometries[i];
	return null; // no surface found
    }
    /** returns first brep */
    public IBrep brep(){
	for(int i=0; i<geometries.length; i++) if(geometries[i] instanceof IBrep) return (IBrep)geometries[i];
	return null; // no brep found
    }
    /** returns first mesh */
    public IMesh mesh(){
	for(int i=0; i<geometries.length; i++) if(geometries[i] instanceof IMesh) return (IMesh)geometries[i];
	return null; // no mesh found
    }
    
    
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
		for(int i=0; i<geometries.length; i++) geometries[i].attr(agent.attr());
	    }
	    else if(agent.clr()!=null){
		for(int i=0; i<geometries.length; i++) geometries[i].clr(agent.clr());
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
