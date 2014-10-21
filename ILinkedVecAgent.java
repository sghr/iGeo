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

/**
   Agent class with a generic data with links to other data agents
   
   @author Satoru Sugihara
*/
public class ILinkedVecAgent extends ILinkedDataAgent<IVecI>{
    
    public ILinkedVecAgent(){ super(); }
    //public ILinkedVecAgent(IVecI val){ super(val); }
    public ILinkedVecAgent(IVecI pos, IVecI val){ super(pos, val); }
    //public ILinkedVecAgent(IObject parent){ super(parent); }
    //public ILinkedVecAgent(IVecI val, IObject parent){ super(val,parent); }

}
