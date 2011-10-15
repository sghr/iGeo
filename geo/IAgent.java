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

import java.util.ArrayList;

import igeo.core.*;
import igeo.util.*;

/**
   Class of an agent based on one point, extending IPoint and implements IDynamicObjectI
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IAgent extends IObject implements IDynamicObjectI{
    
    public IObject parent=null;
    
    public ArrayList<IDynamicObjectI> localDynamics;
    
    int time=0;
    int lifetime=-1;
    boolean alive=true;
    
    public IAgent(){ super(); initAgent(); }
    
    public void initAgent(){ super.addDynamics(this); }
    
    /** override IObject.addDynamics to manage dynamics locally.
	Only IAgent is added at IObject */
    public void addDynamics(IDynamicObjectI dyna){
        if(localDynamics==null) localDynamics = new ArrayList<IDynamicObjectI>();
        if(!localDynamics.contains(dyna))localDynamics.add(dyna);
    }
    
    // implementation of IDynamicObject
    public IObject parent(){ return parent; }
    public ISubobject parent(IObject parent){ this.parent=parent; return this; }
    
    public boolean alive(){ return alive; }
    
    synchronized public void interact(IDynamicObjectI obj){
	if(localDynamics!=null) for(IDynamicObjectI d:localDynamics) d.interact(obj); 
    }
    
    synchronized public void update(){
	if(localDynamics!=null) for(IDynamicObjectI d:localDynamics) d.update();
	
	time++;
	if(lifetime>0&&time>=lifetime){
	    alive=false;
	    del();
	}
    }
    
}
