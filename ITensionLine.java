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
import java.awt.Color;

/**
   Class of IDynamicObject to simulate tension force between two particles.
   
   @author Satoru Sugihara
*/
public class ITensionLine extends ICurve implements ITensionI, IDynamics /*!!! added 20120303 */{
    
    public ITension tensionDynamics;

    // for IDynamics
    /** target objects to be updated by dynamic object */
    public ArrayList<IObject> targets;
    
    
    public ITensionLine(IParticleI p1, IParticleI p2){
	super(p1.pos(), p2.pos());
	initTensionLine(p1,p2);
    }
    
    public ITensionLine(IParticleI p1, IParticleI p2, double tension){
	super(p1.pos(), p2.pos());
	initTensionLine(p1,p2,tension);
    }
    /*
    public ITensionLine(IParticleGeo p1, IParticleGeo p2){
	super(p1.pos(), p2.pos());
	initTensionLine(p1,p2);
    }
    
    public ITensionLine(IParticleGeo p1, IParticleGeo p2, double tension){
	super(p1.pos(), p2.pos());
	initTensionLine(p1,p2,tension);
    }
    
    public ITensionLine(IParticle p1, IParticle p2){
	super(p1.pos(), p2.pos());
	initTensionLine(p1,p2);
    }
    
    public ITensionLine(IParticle p1, IParticle p2, double tension){
	super(p1.pos(), p2.pos());
	initTensionLine(p1,p2,tension);
    }
    
    public ITensionLine(IVec p1, IVec p2){
	super(p1, p2);
	initTensionLine(new IParticleGeo(p1),new IParticleGeo(p2));
    }
    
    public ITensionLine(IVec p1, IVec p2, double tension){
	super(p1, p2);
	initTensionLine(new IParticleGeo(p1),new IParticleGeo(p2),tension);
    }
    */    
    public ITensionLine(IVecI p1, IVecI p2){
	super(p1, p2);
	initTensionLine(new IParticleGeo(p1.get()),new IParticleGeo(p2.get()));
    }
    
    public ITensionLine(IVecI p1, IVecI p2, double tension){
	super(p1, p2);
	initTensionLine(new IParticleGeo(p1.get()),new IParticleGeo(p2.get()),tension);
    }
    
    public void initTensionLine(IParticleI p1, IParticleI p2){
	tensionDynamics = new ITension(p1,p2,this);
	addDynamics(tensionDynamics);
	addDynamics(this); // added in 20120303
    }
    public void initTensionLine(IParticleI p1, IParticleI p2, double tension){
	tensionDynamics = new ITension(p1,p2,tension,this);
	addDynamics(tensionDynamics);
	addDynamics(this); // added in 20120303
    }
    
    public double tension(){ return tensionDynamics.tension(); }
    public ITensionLine tension(double tensionIntensity){
	tensionDynamics.tension(tensionIntensity); return this;
    }
    
    public boolean constant(){ return tensionDynamics.constant(); }
    public ITensionLine constant(boolean cnst){
	tensionDynamics.constant(cnst); return this;
    }
    
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension. if constant is set, maxTension is ignored. */
    public double maxTension(){ return tensionDynamics.maxTension(); }
    /** if maxTension is set to be positive number, it limits the force (distance * tension) is cut off at maxTension if constant is set, maxTension is ignored. */
    public ITensionLine maxTension(double maxTension){ tensionDynamics.maxTension(maxTension); return this; }
    
    
    /** getting end point. i==0 or i==1. if i is other value, returns first point. */
    public IParticleI pt(int i){ return tensionDynamics.pt(i); }
    /** alias of pt(int) */
    public IParticleI particle(int i){ return pt(i); }
    /** position of particle(i) */
    public IVec pos(int i){ return tensionDynamics.pos(i); }
    
    /** getting end point1. */
    public IParticleI pt1(){ return tensionDynamics.pt1(); }
    /** alias of pt1() */
    public IParticleI particle1(){ return pt1(); }
    /** position of particle1 */
    public IVec pos1(){ return tensionDynamics.pos1(); }
    /** getting end point2. */
    public IParticleI pt2(){ return tensionDynamics.pt2(); }
    /** alias of pt2() */
    public IParticleI particle2(){ return pt2(); }
    /** position of particle2 */
    public IVec pos2(){ return tensionDynamics.pos2(); }
    
    
    /*******************************
     * for IDynamics
     ******************************/
    public ITensionLine parent(){ return this; }
    public ITensionLine parent(IObject par){ return this; } // cannot change parent. ignored.
    /** add terget object to be updated by this dynamic object. */
    
    
    /** IDynamicsBase doesn't implement local dynamics feature */
    public ArrayList<IDynamics> localDynamics(){ return null; }
    
    public ITensionLine target(IObject targetObj){
        if(targets==null) targets = new ArrayList<IObject>();
        targets.add(targetObj);
        return this;
    }
    /** get total target number. */
    public int targetNum(){ return targets==null?0:targets.size(); }
    /** get target object. */
    public IObject target(int i){ if(targets==null||i<0||i>=targets.size()) return null; return targets.get(i); }
    /** get all target objects. */
    public ArrayList<IObject> targets(){ return targets; }
    /** remove target object. */
    public ITensionLine removeTarget(int i){
        if(i<0||i>=targets.size()) return null;
        targets.remove(i);
        return this;
    }
    /** remove target object. */
    public ITensionLine removeTarget(IObject obj){ targets.remove(obj); return this; }

    /** update all terget objects (should be called when the dynamic object is updated). */
    public void updateTarget(){
        if(targets!=null)
            for(int i=0; i<targets.size(); i++)
                if(targets.get(i).server()!=null)
                    targets.get(i).updateGraphic();
    }
    /** behavior definition of interaction with other dynamic objects.
        The server puts all dynamic objects including itself.
    */
    public void interact(ArrayList<IDynamics> dynamics){}
    /** behavior definition of updating dynamics in each time frame */
    public void update(){}
    
    public void preinteract(ArrayList<IDynamics> dynamics){}
    public void postinteract(ArrayList<IDynamics> dynamics){}
    public void preupdate(){}
    public void postupdate(){ updateTarget(); }
    

    /******************************************************************************
     * IObject methods
     ******************************************************************************/
    
    public ITensionLine name(String nm){ super.name(nm); return this; }
    public ITensionLine layer(ILayer l){ super.layer(l); return this; }
    public ITensionLine layer(String l){ super.layer(l); return this; }

    public ITensionLine attr(IAttribute at){ super.attr(at); return this; }
    
    
    public ITensionLine hide(){ super.hide(); return this; }
    public ITensionLine show(){ super.show(); return this; }
    
    public ITensionLine clr(IColor c){ super.clr(c); return this; }
    public ITensionLine clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public ITensionLine clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public ITensionLine clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public ITensionLine clr(Color c){ super.clr(c); return this; }
    public ITensionLine clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public ITensionLine clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public ITensionLine clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public ITensionLine clr(int gray){ super.clr(gray); return this; }
    public ITensionLine clr(float fgray){ super.clr(fgray); return this; }
    public ITensionLine clr(double dgray){ super.clr(dgray); return this; }
    public ITensionLine clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public ITensionLine clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public ITensionLine clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public ITensionLine clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public ITensionLine clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public ITensionLine clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public ITensionLine clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public ITensionLine clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public ITensionLine clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public ITensionLine hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public ITensionLine hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public ITensionLine hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public ITensionLine hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public ITensionLine setColor(IColor c){ super.setColor(c); return this; }
    public ITensionLine setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    public ITensionLine setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    public ITensionLine setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    public ITensionLine setColor(Color c){ super.setColor(c); return this; }
    public ITensionLine setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public ITensionLine setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    public ITensionLine setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    public ITensionLine setColor(int gray){ super.setColor(gray); return this; }
    public ITensionLine setColor(float fgray){ super.setColor(fgray); return this; }
    public ITensionLine setColor(double dgray){ super.setColor(dgray); return this; }
    public ITensionLine setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public ITensionLine setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public ITensionLine setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public ITensionLine setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public ITensionLine setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public ITensionLine setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public ITensionLine setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public ITensionLine setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public ITensionLine setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public ITensionLine setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public ITensionLine setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public ITensionLine setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public ITensionLine setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public ITensionLine weight(double w){ super.weight(w); return this; }
    public ITensionLine weight(float w){ super.weight(w); return this; }
    

}
