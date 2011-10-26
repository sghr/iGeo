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

package igeo.core;

import java.util.ArrayList;

/**
   Implementation of IDynamicObject. Only management of parent IObject.
   
   @author Satoru Sugihara
   @version 0.7.0.0
*/
public class IDynamicObjectBase implements IDynamicObject{
    /** parent */
    public IObject parent=null;
    
    /** target objects to be updated by dynamic object */
    public ArrayList<IObject> targets;
    
    /** automatically registered in default IDynamicServer */
    public IDynamicObjectBase(){ initDynamicObjectBase(); }
    
    /** registered in specified IDynamicServer. not registered if the server is null. */
    public IDynamicObjectBase(IServerI server){ initDynamicObjectBase(server); }
    
    public IDynamicObjectBase(IObject parent){ parent(parent); }
    
    public void initDynamicObjectBase(){
	// default server
	initDynamicObjectBase(IG.cur());
    }
    
    public void initDynamicObjectBase(IServerI server){
	if(server!=null) server.server().dynamicServer().add(this);
    }
    
    public IObject parent(){ return parent; }
    public IDynamicObjectBase parent(IObject par){
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
    
    /** add terget object to be updated by this dynamic object. */
    public IDynamicObjectBase target(IObject targetObj){
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
    public IDynamicObjectBase removeTarget(int i){
	if(i<0||i>=targets.size()) return null;
	targets.remove(i);
	return this;
    }
    /** remove target object. */
    public IDynamicObjectBase removeTarget(IObject obj){ targets.remove(obj); return this; }
    
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
    //public void interact(IDynamicObject obj){}
    
    /** behavior definition of interaction with other dynamic objects.
	The server puts all dynamic objects including itself.
    */
    public void interact(ArrayList<IDynamicObject> dynamics){}
    /** behavior definition of updating dynamics in each time frame */
    public void update(){}
    
}
