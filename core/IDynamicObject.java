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
   Interface of a subobject of IObject to control dynamic behavior of IObject.
   
   @author Satoru Sugihara
   @version 0.7.0.0
*/
public /*class*/interface IDynamicObject extends ISubobject{
    
    
    /** add terget object to be updated by this dynamic object. */
    public IDynamicObject target(IObject targetObj);
    /** get total target number. */
    public int targetNum();
    /** get target object. */
    public IObject target(int i);
    /** get all target objects. */
    public ArrayList<IObject> targets();
    /** remove target object. */
    public IDynamicObject removeTarget(int i);
    /** remove target object. */
    public IDynamicObject removeTarget(IObject obj);
    /** update all terget objects (should be called when the dynamic object is updated). */
    public void updateTarget();
    
    
    /* behavior definition of interaction with other dynamic objects.
       interaction happens between every two dynamic objects in a server.
       interact() is called for every combination of two but
       when A.interact(B) happens, B.interact(A) doesn't happen.
       Just once for each combination */
    //public void interact(IDynamicObject obj);
    
    /** behavior definition of interaction with other dynamic objects.
	The server puts all dynamic objects including itself.
    */
    public void interact(ArrayList<IDynamicObject> dynamics);
    /** behavior definition of updating dynamics in each time frame */
    public void update();
}
