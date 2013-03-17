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
   Implementation of IDynamics. It provides management of parent IObject and targets to be updated.
   
   @author Satoru Sugihara
*/
public class IDynamicsBase implements IDynamics{
    /** parent */
    public IObject parent=null;
    
    /** target objects to be updated by dynamic object */
    public ArrayList<IObject> targets;
    
    /** automatically registered in default IDynamicServer */
    public IDynamicsBase(){ initDynamicsBase(); }
    
    /** registered in specified IDynamicServer. not registered if the server is null. */
    public IDynamicsBase(IServerI server){ initDynamicsBase(server); }
    
    public IDynamicsBase(IObject parent){ initDynamicsBase(parent); }
    
    public IDynamicsBase(IDynamicsBase d){
	if(d.parent!=null){ parent(d.parent); }
	else{ initDynamicsBase(); }
	for(int i=0; i<d.targetNum(); i++){ target(d.target(i)); }
    }
    
    public void initDynamicsBase(){
	// default server
	initDynamicsBase(IG.cur());
    }
    
    /** if null is provided at server, this will not be added to any server, not even the default one */
    public void initDynamicsBase(IServerI server){
	if(server!=null) server.server().dynamicServer().add(this);
    }
    
    public void initDynamicsBase(IObject parent){
	this.parent = parent;
	if(this.parent!=null){
	    this.parent.addDynamics(this);
	    target(this.parent);
	}
    }
    
    
    public IObject parent(){ return parent; }
    public IDynamicsBase parent(IObject par){
	if(this.parent==par) return this;
	
	if(this.parent==null){ // this would've been added to default dynamic server
	    IServerI srv = IG.cur();
	    if(srv!=null) srv.server().dynamicServer().remove(this); // necessary?
	}
	else{// necessary?
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
    
    
    /** IDynamicsBase doesn't implement local dynamics feature */
    public ArrayList<IDynamics> localDynamics(){ return null; }
    
    
    /** add terget object to be updated by this dynamic object. */
    public IDynamicsBase target(IObject targetObj){
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
    public IDynamicsBase removeTarget(int i){
	if(i<0||i>=targets.size()) return null;
	targets.remove(i);
	return this;
    }
    /** remove target object. */
    public IDynamicsBase removeTarget(IObject obj){ targets.remove(obj); return this; }
    
    /** update all terget objects (should be called when the dynamic object is updated). */
    public void updateTarget(){
	if(targets!=null)
	    for(int i=0; i<targets.size(); i++)
		if(targets.get(i).server()!=null)
		    targets.get(i).updateGraphic();
    }
    
    
    /* behavior definition of interaction with other dynamic objects.
       interaction happens between every two dynamic objects in a server.
       interact() is called for every combination of two but
       when A.interact(B) happens, B.interact(A) doesn't happen.
       Just once for each combination */
    //public void interact(IDynamics obj){}
    
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
    
}
