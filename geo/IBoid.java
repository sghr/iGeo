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

import java.awt.Color;
import java.util.ArrayList;

import igeo.core.*;
import igeo.util.*;

/**
   Class of an agent based on one point, extending IPoint and implements IDynamicObjectI
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IBoid extends IParticleAgent{
    
    public double neighborDist = 300; //20; //50; //100;
    public double separationDist = 10;
    public double separationRatio = 0.3; //0.01;
    public double alignmentDist = 20; //15;
    public double alignmentRatio = 0.03; //0.01;
    public double alignmentMaxVelocity = 2; //1;
    public double coherenceRatio = 0.02; //0.05;
    
    public double randomVelocityPercent = 0.01; //0.1;
    public double randomVelocityRange = 20; //10;
    
    public ArrayList<IBoid> neighbors;
    
    public IBoid(){ super(); }
    public IBoid(IVecI pt){ super(pt); }
    public IBoid(double x, double y, double z){ super(x,y,z); }
    public IBoid(IBoid b){ super((IParticleAgent)b);
	neighborDist = b.neighborDist;
	separationDist = b.separationDist;
	separationRatio = b.separationRatio;
	alignmentDist = b.alignmentDist;
	alignmentRatio = b.alignmentRatio;
	alignmentMaxVelocity = b.alignmentMaxVelocity;
	coherenceRatio = b.coherenceRatio;
	randomVelocityPercent = b.randomVelocityPercent;
	randomVelocityRange = b.randomVelocityRange;
    }
    
    synchronized public void flock(){
	separate();
	align();
	cohere();
    }
    
    synchronized public void separate(){
	for(IBoid a: neighbors){
	    //double dist = this.pos.dist(a.pos);
	    double dist = this.dist(a);
	    if(dist < separationDist && dist > 0){
		double ratio = (separationDist - dist)/separationDist;
		
		//IVec diff = this.pos.diff(a.pos);
		IVec diff = this.diff(a);
		diff.len( separationRatio * ratio );
		
		addForce( diff );
	    }
	}
    }
    
    synchronized public void align(){
	
	if(neighbors.size()==0) return;
	
	IVec avrgVelocity = new IVec();
	
	for(IBoid a: neighbors){
	    //double dist = this.pos.dist(a.pos);
	    double dist = this.dist(a);
	    if(dist < alignmentDist){
		avrgVelocity.add( a.velocity() );
	    }
	}
	
	avrgVelocity.div(neighbors.size());
	
	if(avrgVelocity.len()>alignmentMaxVelocity) avrgVelocity.len(alignmentMaxVelocity);
	avrgVelocity.mul(alignmentRatio);
	addForce(avrgVelocity);
	
    }
    
    synchronized public void cohere(){
	
	if(neighbors.size()==0) return;
	
	IVec center = new IVec();
	
	for(IBoid a: neighbors){
	    //center.add( a.position() );
	    center.add(a);
	}
	
	center.div(neighbors.size());
	
	//IVec diff = center.diff(this.position());
	IVec diff = center.diff(this);
	
	double dist = diff.len();
	
	if(dist>0){
	    double ratio = dist/neighborDist;
	    diff.len(coherenceRatio*ratio);
	    addForce(diff);
	}
    }
    
    synchronized public void interact(ArrayList<IDynamicObject> dynamics){
	
	super.interact(dynamics); // for other local interaction
	
	for(IDynamicObject obj: dynamics){
	    
	    if(neighbors==null){ neighbors = new ArrayList<IBoid>(); }
	    neighbors.clear();
	    
	    if(obj != this && obj instanceof IBoid){
		IBoid a = (IBoid)obj;
		//double dist = a.pos.dist(this.pos);
		double dist = a.dist(this);
		if(dist < neighborDist){
		    neighbors.add(a);
		}
	    }
	    
	    flock();
	    
	    //if(IRandom.percent(0.1)) addForce(IRandom.pt(-4,4));
	    if(randomVelocityPercent>0 &&
	       IRandom.percent(randomVelocityPercent))
		addForce(IRandom.pt(-randomVelocityRange,randomVelocityRange));
	    
	    
	    /*
		    double ratio = (neighborDistance - dist)/neighborDistance;
		    
		    // separation
		    if(ratio>0.5){
			final double separationCoeff = 0.0008;
			//IVec sep = this.diff(a).get();
			//sep.mul( ratio * separationCoeff);
			//this.particle.addForce(sep);
			//a.particle.addForce(sep.neg());
			//IVec sep = this.pos.diff(a.pos);
			IVec sep = this.pos.diff(a.pos);
			//sep.x *= ratio * separationCoeff;
			//sep.y *= ratio * separationCoeff;
			//sep.z *= ratio * separationCoeff;
			sep.mul( (ratio-0.5)*2 * separationCoeff);
			this.particle.addForce(sep);
			//a.particle.addForce(sep.neg());
		    }
		    else{
			// cohesion
			final double cohesionCoeff = 0.001;
			//IVec coh = a.diff(this).get();
			//IVec coh = a.pos.diff(this.pos);
			IVec coh = a.pos.diff(this.pos);
			coh.mul( (0.5-ratio)*2 * cohesionCoeff);
			this.addForce(coh);
			//a.addForce(coh.neg());
			//IOut.p(".");
		    }
		    // alignment
		    final double alignmentCoeff = 0.2;
		    //IVec ali = a.velocity().dup().mul(ratio*alignmentCoeff);
		    IVec ali = a.velocity().dup();
		    ali.sub(this.velocity()).mul(ratio*alignmentCoeff);
		    this.addForce(ali);
		    //a.addForce(ali.neg());
		    
		    //IVec mvel = a.velocity().dup().add(this.velocity()).mul(0.01);
		    //a.addForce(mvel);
		}
	    }
	    */
	    
	}
	
    }
    
    //IVec prevPt = null;
    synchronized public void update(){
	super.update();
	/*
	if(time%10==0){
	    IVec curPt = pos.dup();
	    if(prevPt!=null) new ICurve(prevPt,curPt).clr(this.clr());
	    prevPt=curPt;
	}
	*/
    }
    
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    public IBoid dup(){ return new IBoid(this); }
    
    public IBoid set(IVecI v){ pos.set(v); return this; }
    public IBoid set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IBoid set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IBoid add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IBoid add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IBoid add(IVecI v){ pos.add(v); return this; }
    
    public IBoid sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IBoid sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IBoid sub(IVecI v){ pos.sub(v); return this; }
    public IBoid mul(IDoubleI v){ pos.mul(v); return this; }
    public IBoid mul(double v){ pos.mul(v); return this; }
    public IBoid div(IDoubleI v){ pos.div(v); return this; }
    public IBoid div(double v){ pos.div(v); return this; }
    public IBoid neg(){ pos.neg(); return this; }
    public IBoid rev(){ return neg(); }
    public IBoid flip(){ return neg(); }
    
    public IBoid add(IVecI v, double f){ pos.add(v,f); return this; }
    public IBoid add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IBoid len(IDoubleI l){ pos.len(l); return this; }
    public IBoid len(double l){ pos.len(l); return this; }
    
    public IBoid unit(){ pos.unit(); return this; }
    
    public IBoid rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IBoid rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    
    public IBoid rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IBoid rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    
    public IBoid rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IBoid rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IBoid scale(IDoubleI f){ pos.scale(f); return this; }
    public IBoid scale(double f){ pos.scale(f); return this; }
    
    public IBoid scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IBoid scale(IVecI center, double f){ pos.scale(center,f); return this; }
    
    /** scale only in 1 direction */
    public IBoid scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IBoid scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IBoid scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IBoid scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
        
    public IBoid ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IBoid ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IBoid mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IBoid mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    
    public IBoid shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IBoid shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IBoid shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IBoid shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IBoid shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IBoid shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IBoid shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IBoid shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IBoid shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IBoid shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IBoid shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IBoid shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IBoid shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IBoid shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IBoid shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IBoid shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    public IBoid translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IBoid translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IBoid translate(IVecI v){ pos.translate(v); return this; }
    
    public IBoid transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IBoid transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IBoid transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IBoid transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IBoid mv(double x, double y, double z){ return add(x,y,z); }
    public IBoid mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IBoid mv(IVecI v){ return add(v); }
    
    public IBoid cp(){ return dup(); }
    public IBoid cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IBoid cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IBoid cp(IVecI v){ return dup().add(v); }
    
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IBoid setSize(double sz){ return size(sz); }
    public IBoid size(double sz){ point.size(sz); return this; }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IBoid name(String nm){ super.name(nm); return this; }
    public IBoid layer(ILayer l){ super.layer(l); return this; }
    
    public IBoid show(){ super.show(); return this; }
    public IBoid hide(){ super.hide(); return this; }
    
    
    public IBoid clr(Color c){ super.clr(c); return this; }
    public IBoid clr(int gray){ super.clr(gray); return this; }
    public IBoid clr(float fgray){ super.clr(fgray); return this; }
    public IBoid clr(double dgray){ super.clr(dgray); return this; }
    public IBoid clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IBoid clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IBoid clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IBoid clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IBoid clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IBoid clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IBoid clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IBoid clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IBoid clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IBoid hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IBoid hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IBoid hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IBoid hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IBoid setColor(Color c){ super.setColor(c); return this; }
    public IBoid setColor(int gray){ super.setColor(gray); return this; }
    public IBoid setColor(float fgray){ super.setColor(fgray); return this; }
    public IBoid setColor(double dgray){ super.setColor(dgray); return this; }
    public IBoid setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IBoid setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IBoid setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IBoid setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IBoid setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IBoid setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IBoid setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IBoid setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IBoid setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IBoid setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IBoid setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IBoid setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IBoid setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    
}
