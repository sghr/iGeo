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
   Interface of a subobject of IObject to control dynamic behavior of IObject.
   
   @author Satoru Sugihara
*/
public /*class*/interface IDynamics extends ISubobject{
    
    /* behavior definition of interaction with other dynamics.
       interaction happens between every two dynamic objects in a server.
       interact() is called for every combination of two but
       when A.interact(B) happens, B.interact(A) doesn't happen.
       Just once for each combination */
    //public void interact(IDynamics obj);
    
    /** behavior definition of interaction with other dynamic objects.
	The server puts all dynamic objects including itself.
    */
    public void interact(ArrayList<IDynamics> dynamics);
    /** behavior definition of updating dynamics in each time frame */
    public void update();
    
    /** executed before interact(ArrayList<IDynamics>). behavior can be changed by IConfig.enablePreinteract and IConfig.loopPreinteract. */
    public void preinteract(ArrayList<IDynamics> dynamics);
    /** executed after interact(ArrayList<IDynamics>). behavior can be changed by IConfig.enablePostinteract and IConfig.loopPostinteract.
     	If IConfig.loopPostinteract is false, preupdate is executed before post interact (updated 2012/08/26).
    */
    public void postinteract(ArrayList<IDynamics> dynamics);
    
    /** executed before update(). behavior can be changed by IConfig.enablePreupdate and IConfig.loopPreupdate.
	If IConfig.loopPreupdate is false, preupdate is executed before post interact (updated 2012/08/26).
    */
    public void preupdate();
    /** executed after update(). behavior can be changed by IConfig.enablePostupdate and IConfig.loopPostupdate. */
    public void postupdate();
    
    
    /** IDynamics can have local dynamics which is managed by parent dynamics. IAgent implements this feature */
    public ArrayList<IDynamics> localDynamics(); 
    
    
    /** add terget object to be updated by this dynamic object. */
    public IDynamics target(IObject targetObj);
    /** get total target number. */
    public int targetNum();
    /** get target object. */
    public IObject target(int i);
    /** get all target objects. */
    public ArrayList<IObject> targets();
    /** remove target object. */
    public IDynamics removeTarget(int i);
    /** remove target object. */
    public IDynamics removeTarget(IObject obj);
    /** update all terget objects (should be called when the dynamic object is updated). */
    public void updateTarget();
    

}
