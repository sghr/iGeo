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
   Class of IDynamics to simulate fixed length relationship between two points
   
   @author Satoru Sugihara
*/
public class IStick extends IDynamicsBase{
    
    public IParticleI pt1, pt2;
    /** just to store force difference in each time frame */
    //public IVec frc1=new IVec(), frc2=new IVec(); // only for the first time frame
    public IVec frc = new IVec(); // instance for the first time frame
    
    public double len;
    public double maxTension = -1; // default negative, meaning no limit
    
    public IStick(IParticleI p1, IParticleI p2, IObject parent){
	super(parent);
	pt1=p1; pt2=p2;
	len = pt1.pos().dist(pt2.pos());
    }
    
    public IStick(IVecI p1, IVecI p2, IObject parent){
	this(new IParticleGeo(p1.get()), new IParticleGeo(p2.get()));
    }
    
    public IStick(IParticleI p1, IParticleI p2){
	super();
	pt1=p1; pt2=p2;
	len = pt1.pos().dist(pt2.pos());
    }
    
    public IStick(IVecI p1, IVecI p2){
	this(new IParticleGeo(p1.get()), new IParticleGeo(p2.get()));
    }
    
    
    /** getting end point. i==0 or i==1. if i is other value, returns first point. */
    public IParticleI pt(int i){ if(i==1){ return pt2; } return pt1; }
    /** alias of pt1() */
    public IParticleI particle(int i){ return pt(i); }
    /** position of particle(i) */
    public IVec pos(int i){ return pt(i).pos(); }
    /** getting end point1. */
    public IParticleI pt1(){ return pt1; }
    /** alias of pt1() */
    public IParticleI particle1(){ return pt1(); }
    /** position of particle1() */
    public IVec pos1(){ return pt1().pos(); }
    /** getting end point2. */
    public IParticleI pt2(){ return pt2; }
    /** alias of pt2() */
    public IParticleI particle2(){ return pt2(); }
    /** position of particle2() */
    public IVec pos2(){ return pt2().pos(); }
    
    
    public IStick parent(IObject par){ super.parent(par); return this; }
    public IStick target(IObject targetObj){ super.target(targetObj); return this; }
    public IStick removeTarget(int i){ super.removeTarget(i); return this; }
    public IStick removeTarget(IObject obj){ super.removeTarget(obj); return this; }
    
    /** after each particle receive all forces */
    //synchronized public void postinteract(ArrayList<IDynamics> dynamics){
    synchronized public void preupdate(){
	
	// skip when pt1.frc < tolerance && pt2.frc < tolerance ?
	
	IVec dif = pt2.pos().dif(pt1.pos());
	double l = dif.len();
	if(l<IConfig.tolerance){
	    //frc1 = pt1.frc().cp();
	    //frc2 = pt2.frc().cp();
	    frc = pt2.frc().dif(pt1.frc());
	}
	else{
	    dif.div(l);
	    
	    double d1=0, d2=0;
	    
	    if(!pt1.fixed()){ d1 = pt1.frc().dot(dif); }
	    if(!pt2.fixed()){ d2 = pt2.frc().dot(dif); }
	    
	    //frc1 = dif.cp().mul(d1);
	    //frc2 = dif.cp().mul(d2);
	    
	    frc = dif.mul(d2-d1);
	    
	    //IG.p(parent.name()+": "+((IParticle)pt1).name()+".frc = "+pt1.frc().z);
	    //IG.p(parent.name()+": "+((IParticle)pt1).name()+".frc.dot = "+d1);
	    //IG.p(parent.name()+": "+((IParticle)pt2).name()+".frc = "+pt2.frc().z);
	    //IG.p(parent.name()+": "+((IParticle)pt2).name()+".frc.dot = "+d2);
	    /*
	    if(d1*d2 < 0){ // diffrent dir
	    }
	    else{ // same dir
	    }
	    */
	}
	
	// what should be done for force and velocity?
    }
    
    //synchronized public void preupdate(){ // preupdate comes before postinteract
    synchronized public void postinteract(ArrayList<IDynamics> agents){
	//IG.p(parent.name()+": "+((IParticle)pt1).name()+".frc = "+frc1.z);
	//IG.p(parent.name()+": "+((IParticle)pt2).name()+".frc = "+frc2.z);
	
	if(!pt1.fixed()) pt1.push(frc);
	if(!pt2.fixed()) pt2.pull(frc);
	
	/*
	if(pt1.fixed()){
	    if(!pt2.fixed()){
		pt2.pull(frc2);
	    }
	}
	else if(pt2.fixed()){ // && !pt1.fixed()
	    pt1.pull(frc1);
	}
	else{ // !pt1.fixed() && !pt2.fixed()
	    pt1.push(frc2);
	    pt2.push(frc1);
	}
	*/
	
	//if(pt2.fixed()){ pt1.pull(frc1); }
	//else{ pt1.push(frc2); }
	//if(pt1.fixed()){ pt2.pull(frc2); }
	//else{ pt2.push(frc1); }
	
	
	IVec dif = pt2.pos().dif(pt1.pos());
	double l = dif.len();
	
	// velocity constraint
	// IParticleGeo's velocity is also updated in preupdate. it depends on order
	if(l<IConfig.tolerance){
	    IVec v = pt1.vel().mid(pt2.vel());
	    if(!pt1.fixed()) pt1.vel(v);
	    if(!pt2.fixed()) pt2.vel(v);
	}
	else{
	    if(!pt1.fixed() || !pt2.fixed()){
		dif.div(l);
		
		IVec v1n = pt1.vel().cp();
		IVec v2n = pt2.vel().cp();
		IVec v1t = dif.cp().mul(dif.dot(v1n));
		v1n.sub(v1t);
		IVec v2t = dif.cp().mul(dif.dot(v2n));
		v2n.sub(v2t);
		
		if(pt1.fixed()){
		    pt2.vel(v2n);
		}
		else if(pt2.fixed()){
		    pt1.vel(v1n);
		}
		else{ // both moving
		    v1t.add(v2t).div(2); // mid
		    pt1.vel(v1n.add(v1t));
		    pt2.vel(v2n.add(v1t));
		}
	    }
	}
    }
    
    
    synchronized public void postupdate(){
	
	IVec dif = pt2.pos().dif(pt1.pos());
	double l = dif.len();
	

	//if(pt1.dist(pt2) > len+IConfig.tolerance){
	//IG.p(this+": "+pt1.pos().dist(pt2.pos()) + "/"+len); // debug
	//}
	
	if(!pt1.fixed()&&!pt2.fixed()){
	    IVec mid = pt1.pos().mid(pt2.pos());
	    dif.len(len);
	    pt1.set(mid.add(dif,-0.5));
	    pt2.set(mid.add(dif));
	}
	else if(!pt2.fixed()){
	    dif.len(len);
	    pt2.set(dif.add(pt1.pos()));
	}
	else if(!pt1.fixed()){
	    dif.len(len);
	    pt1.set(dif.neg().add(pt2.pos()));
	}
	
	//IG.p("post: "+pt1.pos().dist(pt2.pos()) + "/"+len); // debug
    }
    
    
    //synchronized public void update(){}
}
