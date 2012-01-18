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

import java.awt.Color;
import java.util.ArrayList;

/**
   Class of an agent based on one point, extending IPoint and implements IDynamics
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IBoid extends IParticleAgent{
    
    /*
    public double neighborDist = 300; //20; //50; //100;
    public double separationDist = 10;
    public double separationRatio = 0.3; //0.01;
    public double alignmentDist = 20; //15;
    public double alignmentRatio = 0.03; //0.01;
    public double alignmentMaxVelocity = 2; //1;
    public double coherenceRatio = 0.02; //0.05;
    
    public double randomVelocityPercent = 0.01; //0.1;
    public double randomVelocityRange = 20; //10;
    */
    
    public double cohesionDist = 100;
    public double cohesionRatio = 5;
    public double cohesionLimit = -1; // no limit
    public double separationDist = 50;
    public double separationRatio = 5;
    public double separationLimit = -1; // no limit
    public double alignmentDist = 20;
    public double alignmentRatio = 5;
    public double alignmentLimit = -1; // no limit
    
    
    IVec cohesionForce = new IVec();
    IVec separationForce = new IVec();
    IVec alignmentForce = new IVec();
    
    //public ArrayList<IBoid> neighbors;
    
    public IBoid(){ super(); initBoid(); }
    public IBoid(IVecI pt){ super(pt); initBoid(); }
    public IBoid(IVec pt){ super(pt); initBoid(); }
    public IBoid(IVecI pt, IVecI vel){ super(pt,vel); initBoid(); }
    public IBoid(IVec pt, IVec vel){ super(pt,vel); initBoid(); }
    public IBoid(double x, double y, double z){ super(x,y,z); initBoid(); }
    public IBoid(double x, double y, double z, double vx, double vy, double vz){
	super(x,y,z,vx,vy,vz); initBoid();
    }
    public IBoid(IBoid b){ super((IParticleAgent)b);
	/*
	neighborDist = b.neighborDist;
	separationDist = b.separationDist;
	separationRatio = b.separationRatio;
	alignmentDist = b.alignmentDist;
	alignmentRatio = b.alignmentRatio;
	alignmentMaxVelocity = b.alignmentMaxVelocity;
	coherenceRatio = b.coherenceRatio;
	randomVelocityPercent = b.randomVelocityPercent;
	randomVelocityRange = b.randomVelocityRange;
	*/
	cohesionDist = b.cohesionDist;	
	cohesionRatio = b.cohesionRatio;
	cohesionLimit = b.cohesionLimit;
	separationDist = b.separationDist;
	separationRatio = b.separationRatio;
	separationLimit = b.separationLimit;
	alignmentDist = b.alignmentDist;
	alignmentRatio = b.alignmentRatio;
	alignmentLimit = b.alignmentLimit;
	
	initBoid();
    }
    
    public void initBoid(){
	cohesionForce = new IVec();
        separationForce = new IVec();
	alignmentForce = new IVec();
    }


    public double cohDist(){ return cohesionDist; }
    public double cohesionDist(){ return cohDist(); }
    public IBoid cohDist(double dist){ cohesionDist=dist; return this; }
    public IBoid cohesionDist(double dist){ return cohDist(dist); }
    public double cohRatio(){ return cohesionRatio; }
    public double cohesionRatio(){ return cohRatio(); }
    public IBoid cohRatio(double ratio){ cohesionRatio=ratio; return this; }
    public IBoid cohesionRatio(double ratio){ return cohRatio(ratio); }
    public double cohLimit(){ return cohesionLimit; }
    public double cohesionLimit(){ return cohLimit(); }
    public IBoid cohLimit(double limit){ cohesionLimit = limit; return this; }
    public IBoid cohesionLimit(double limit){ return cohLimit(limit); }
    
    public double sepDist(){ return separationDist; }
    public double separationDist(){ return sepDist(); }
    public IBoid sepDist(double dist){ separationDist=dist; return this; }
    public IBoid separationDist(double dist){ return sepDist(dist); }
    public double sepRatio(){ return separationRatio; }
    public double separationRatio(){ return sepRatio(); }
    public IBoid sepRatio(double ratio){ separationRatio=ratio; return this; }
    public IBoid separationRatio(double ratio){ return sepRatio(ratio); }
    public double sepLimit(){ return separationLimit; }
    public double separationLimit(){ return sepLimit(); }
    public IBoid sepLimit(double limit){ separationLimit = limit; return this; }
    public IBoid separationLimit(double limit){ return sepLimit(limit); }
    
    public double aliDist(){ return alignmentDist; }
    public double alignmentDist(){ return aliDist(); }
    public IBoid aliDist(double dist){ alignmentDist=dist; return this; }
    public IBoid alignmentDist(double dist){ return aliDist(dist); }
    public double aliRatio(){ return alignmentRatio; }
    public double alignmentRatio(){ return aliRatio(); }
    public IBoid aliRatio(double ratio){ alignmentRatio=ratio; return this; }
    public IBoid alignmentRatio(double ratio){ return aliRatio(ratio); }
    public double aliLimit(){ return alignmentLimit; }
    public double alignmentLimit(){ return aliLimit(); }
    public IBoid aliLimit(double limit){ alignmentLimit = limit; return this; }
    public IBoid alignmentLimit(double limit){ return aliLimit(limit); }
    
    
    /*
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
    */
    
    synchronized public void interact(ArrayList<IDynamics> dynamics){
	
	//super.interact(dynamics); // for other local interaction
	
	int cohesionCount = 0;
	int separationCount = 0;
	int alignmentCount = 0;
	
	cohesionForce.zero(); // reset
	separationForce.zero(); // reset
	alignmentForce.zero(); // reset
	
	for(int i=0; i<dynamics.size(); i++){
	    if(dynamics.get(i) instanceof IBoid && dynamics.get(i) != this){
		IBoid b = (IBoid)dynamics.get(i);
		IVec dif = b.pos().dif(pos());
		double dist = dif.len();
		// cohere
		if(dist < cohesionDist){
		    cohesionForce.add(dif);
		    cohesionCount++;
		}
		// separate
		if(dist < separationDist){
		    
		    if(dist==0){
			if(vel().len()>0) separationForce.add(vel().dup().len(separationDist));
			else  separationForce.add(new IVec(separationDist,0,0));
		    }
		    else separationForce.add( dif.len(dist-separationDist) );
		    separationCount++;
		    
		    //separationForce.sub(dif); // too simple
		    
		} // content of dif is changed
		
		// align
		if(dist < alignmentDist){
		    alignmentForce.add(b.vel()); // velocity
		    alignmentCount++;
		}
	    }
	}
	
	if(cohesionCount>0){
	    cohesionForce.mul(cohesionRatio/cohesionCount);
	    if(cohesionLimit >= 0 && cohesionForce.len() > cohesionLimit)
		cohesionForce.len(cohesionLimit);
	    push(cohesionForce);
	}
	if(separationCount>0){
	    separationForce.mul(separationRatio/separationCount);
	    //separationForce.mul(separationRatio);
	    if(separationLimit >= 0 && separationForce.len() > separationLimit)
		separationForce.len(separationLimit);
	    push(separationForce);
	}
	if(alignmentCount>0){
	    // averagbe velocity minus this velocity
	    alignmentForce.div(alignmentCount).sub(vel()).mul(alignmentRatio);
	    if(alignmentLimit >= 0 && alignmentForce.len() > alignmentLimit)
		alignmentForce.len(alignmentLimit);
	    push(alignmentForce);
	}
	
	
	/*
	for(IDynamics obj: dynamics){
	    
	    if(neighbors==null){ neighbors = new ArrayList<IBoid>(); }
	    neighbors.clear();
	    
	    if(obj != this && obj instanceof IBoid){
		IBoid a = (IBoid)obj;
		//double dist = a.pos.dist(this.pos);
		double dist = a.dist(this);
		if(dist < neighborDist){ neighbors.add(a); }
	    }
	    
	    flock();
	    
	    //if(IRandom.percent(0.1)) addForce(IRandom.pt(-4,4));
	    if(randomVelocityPercent>0 &&
	       IRandom.percent(randomVelocityPercent))
		addForce(IRandom.pt(-randomVelocityRange,randomVelocityRange));
	    */
	    
	    
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
	    
	//}
	
    }
    
    //IVec prevPt = null;
    //synchronized public void update(){
	//super.update();
	/*
	if(time%10==0){
	    IVec curPt = pos.dup();
	    if(prevPt!=null) new ICurve(prevPt,curPt).clr(this.clr());
	    prevPt=curPt;
	}
	*/
    //}
    
    
    
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

    public IBoid zero(){ pos.zero(); return this; }
    
    public IBoid add(IVecI v, double f){ pos.add(v,f); return this; }
    public IBoid add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IBoid add(double f, IVecI v){ pos.add(f,v); return this; }
    public IBoid add(IDoubleI f, IVecI v){ pos.add(f,v); return this; }
    
    public IBoid len(IDoubleI l){ pos.len(l); return this; }
    public IBoid len(double l){ pos.len(l); return this; }
    
    public IBoid unit(){ pos.unit(); return this; }
    
    public IBoid rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IBoid rot(double angle){ pos.rot(angle); return this; }
    
    public IBoid rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IBoid rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IBoid rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IBoid rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IBoid rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IBoid rot(double centerX, double centerY, double centerZ,
		     double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    public IBoid rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IBoid rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }

    /** rotation on xy-plane; alias of rot(double) */
    public IBoid rot2(double angle){ pos.rot2(angle); return this; }
    /** rotation on xy-plane; alias of rot(IDoubleI) */
    public IBoid rot2(IDoubleI angle){ pos.rot2(angle); return this; }
    
    /** rotation on xy-plane */
    public IBoid rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IBoid rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    /** rotation on xy-plane */
    public IBoid rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    /** rotation on xy-plane towards destDir */
    public IBoid rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    /** rotation on xy-plane towards destPt */
    public IBoid rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    
    public IBoid scale(IDoubleI f){ pos.scale(f); return this; }
    public IBoid scale(double f){ pos.scale(f); return this; }
    
    public IBoid scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IBoid scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IBoid scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IBoid scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IBoid scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IBoid scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IBoid scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IBoid scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IBoid scale1d(double centerX, double centerY, double centerZ,
			 double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IBoid ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IBoid ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IBoid ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IBoid ref(double centerX, double centerY, double centerZ,
		     double planeX, double planeY, double planeZ){
	pos.ref(centerX,planeY,planeZ,planeX,planeY,planeZ); return this;
    }
    public IBoid mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IBoid mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IBoid mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IBoid mirror(double centerX, double centerY, double centerZ,
			double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
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
    public IBoid clr(Color c, int alpha){ super.clr(c,alpha); return this; }
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
    public IBoid setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
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
