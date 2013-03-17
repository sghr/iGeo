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

//import java.awt.Color;
import java.util.ArrayList;

/**
   Class of an agent based on one point, extending IPoint and implements IDynamics
   
   @author Satoru Sugihara
*/
public class IAgent extends IObject implements IDynamics{
    
    public IObject parent=null;
    
    /** target objects to be updated by dynamic object */
    public ArrayList<IObject> targets;
    
    public ArrayList<IDynamics> localDynamics;
    
    public int time=-1; //0; // changed to -1 to become 0 after first update() is executed.
    public int duration=-1; //lifetime=-1;
    public boolean alive=true;
    
    /** check if a child class overrides void interact(IDynamics) or not */
    public boolean interactOverridden=false;
    
    public IAgent(){ super(); initAgent(); }
    
    public IAgent(IObject parent){ super(); this.parent=parent; initAgent(); }
    
    public void initAgent(){
	super.addDynamics(this);
	/** check if a child class overrides void interact(IDynamics) or not */
	try{
	    interactOverridden = 
		getClass().getMethod("interact", IDynamics.class).getDeclaringClass() !=
		IAgent.class;
	}catch(NoSuchMethodException e){}
    }
    
    
    /** override IObject.addDynamics to manage dynamics locally.
	Only IAgent is added at IObject */
    @Override public void addDynamics(IDynamics dyna){
        if(localDynamics==null) localDynamics = new ArrayList<IDynamics>();
        if(!localDynamics.contains(dyna)){
	    localDynamics.add(dyna);
	    if(dyna.parent()!=this) dyna.parent(this);
	}
    }
    @Override public IDynamics getDynamics(int i){
        if(localDynamics==null) return null;
        return localDynamics.get(i);
    }
    @Override public int dynamicsNum(){
	if(localDynamics==null) return 0; return localDynamics.size();
    }
    
    @Override public void deleteDynamics(){
        if(server!=null && server.dynamicServer!=null && localDynamics!=null){
            for(IDynamics dyn:localDynamics) server.dynamicServer.remove(dyn);
        }
    }
    @Override public void deletDynamics(int index){
	if(localDynamics==null||index<0||index>=localDynamics.size()) return;
        if(server!=null && server.dynamicServer!=null)
            server.dynamicServer.remove(localDynamics.get(index));
        localDynamics.remove(index);
    }
    @Override public void deleteDynamics(IDynamics dyn){
	if(localDynamics==null || !localDynamics.contains(dyn)) return;
        if(server!=null && server.dynamicServer!=null) server.dynamicServer.remove(dyn);
        localDynamics.remove(dyn);
    }

    /** delete agent and stop it by removing it from dynamics server */
    @Override public void del(){
	alive=false;
	this.deleteDynamics();
	super.del();
    }
    
    /** stop agent with option of deleting/keeping the geometry the agent owns */
    public void del(boolean deleteGeometry){ 
	alive=false;
	this.deleteDynamics();
	super.del();
    }
    
    @Override public void updateGraphic(){
	if(parent!=null) parent.updateGraphic();
    }
    
    
    // implementation of IDynamics
    public IObject parent(){ return parent; }
    public ISubobject parent(IObject par){
	//this.parent=parent; return this;
	if(this.parent!=null){// necessary?
	    this.parent.deleteDynamics(this);
	    removeTarget(this.parent); // removing from target too.
	}
	
	this.parent=par;
	if(this.parent!=null){
	    this.parent.addDynamics(this);
	    target(this.parent); // adding to target too.
	}
	return this;
    }
    
    
    /** local dynamics are updated by IDynamics server too but not show up in interact's argument. only its parent show up */
    public ArrayList<IDynamics> localDynamics(){ return localDynamics; }
    
    
    /** add terget object to be updated by this dynamic object. */
    public IAgent target(IObject targetObj){
	if(targets==null) targets = new ArrayList<IObject>();
	targets.add(targetObj);
	return this;
    }
    /** get total target number. */
    public int targetNum(){ return targets==null?0:targets.size(); }
    /** get target object. */
    public IObject target(int i){ if(i<0||i>=targets.size()) return null; return targets.get(i); }
    /** get all target objects. */
    public ArrayList<IObject> targets(){ return targets; }
    /** remove target object. */
    public IAgent removeTarget(int i){
	if(i<0||i>=targets.size()) return null;
	targets.remove(i);
	return this;
    }
    /** remove target object. */
    public IAgent removeTarget(IObject obj){ targets.remove(obj); return this; }
    
    /** update all terget objects (should be called when the dynamic object is updated). */
    public void updateTarget(){
	if(targets!=null)
	    for(int i=0; i<targets.size(); i++)
		if(targets.get(i).server()!=null)
		    targets.get(i).updateGraphic();
    }
    
    
    
    public boolean alive(){ return alive; }
    
    public int time(){ return time; }
    /** not recommended to use. use carefully if you use */
    public IAgent time(int tm){ time=tm; return this; }
    
    public int duration(){ return duration; }
    public IAgent duration(int dur){ duration=dur; return this; }
    
    //public void initInteract(ArrayList<IDynamics> agents){
    synchronized public void preinteract(ArrayList<IDynamics> agents){
	time++; // time needs to be updated here to have same value in interact() and update()
	//if(localDynamics!=null) for(IDynamics d:localDynamics) d.interact(agents); // commented out 20120826; now localDynamics are managed by IDynamicServer
    }
    
    synchronized public void interact(ArrayList<IDynamics> agents){ // could be overridden
	//initInteract(agents);
	
	/*
	// to take care of possible definition of interact(IDynamics) in a child class.
	boolean interactOverridden=false;
	try{
	    interactOverridden = 
		getClass().getMethod("interact", IDynamics.class).getDeclaringClass() !=
		IAgent.class;
	}catch(NoSuchMethodException e){}
	*/
	
	if(interactOverridden){
	    IDynamics d=null;
	    for(int i=0; i<agents.size(); i++){
		d = agents.get(i);
		if(d != this) interact(d);
	    }
	}
    }
    
    synchronized public void interact(IDynamics agent){}
    
    synchronized public void postinteract(ArrayList<IDynamics> agents){} // not used
    
    //synchronized public void initUpdate(){
    synchronized public void preupdate(){
	//if(localDynamics!=null) for(IDynamics d:localDynamics) d.update(); // commented out 20120826; now localDynamics are managed by IDynamicServer
	if( duration>=0 && time>=duration ){ del(); }
	else if(server!=null) server.updateState();
    }
    
    synchronized public void update(){ // to be overridden
	//initUpdate();
	// localDynamics will update parent directly. So no need to update parent here.
	//if(parent!=null) parent.updateGraphic(); // did anything change?
    }
    
    synchronized public void postupdate(){ updateTarget(); }
    
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IAgent name(String nm){ super.name(nm); return this; }
    public IAgent layer(ILayer l){ super.layer(l); return this; }
    
    public IAgent hide(){ super.hide(); return this; }
    public IAgent show(){ super.show(); return this; }
    
    public IAgent clr(IColor c){ super.clr(c); return this; }
    public IAgent clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IAgent clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IAgent clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    //public IAgent clr(Color c){ super.clr(c); return this; }
    //public IAgent clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    //public IAgent clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    //public IAgent clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IAgent clr(int gray){ super.clr(gray); return this; }
    public IAgent clr(float fgray){ super.clr(fgray); return this; }
    public IAgent clr(double dgray){ super.clr(dgray); return this; }
    public IAgent clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IAgent clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IAgent clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IAgent clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IAgent clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IAgent clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IAgent clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IAgent clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IAgent clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IAgent hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IAgent hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IAgent hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IAgent hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }

    public IAgent weight(float w){ super.weight(w); return this; }
    public IAgent weight(double w){ super.weight(w); return this; }
    
    public IAgent setColor(IColor c){ super.setColor(c); return this; }
    public IAgent setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    public IAgent setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    public IAgent setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    //public IAgent setColor(Color c){ super.setColor(c); return this; }
    //public IAgent setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    //public IAgent setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    //public IAgent setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    public IAgent setColor(int gray){ super.setColor(gray); return this; }
    public IAgent setColor(float fgray){ super.setColor(fgray); return this; }
    public IAgent setColor(double dgray){ super.setColor(dgray); return this; }
    public IAgent setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IAgent setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IAgent setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IAgent setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IAgent setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IAgent setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IAgent setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IAgent setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IAgent setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IAgent setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IAgent setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IAgent setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IAgent setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    
}
