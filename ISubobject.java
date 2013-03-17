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
   A base class of subobjects to be contained by IObject.
   
   @see IObject
   
   @author Satoru Sugihara
*/
public /*class*/ interface ISubobject /*implements ISubelementI*/{
    //public IObject parent=null;
    ////public ISubobject(){}
    ////public ISubobject(IServerI holder){}
    //public IObject parent(){ return parent; }
    //public ISubobject parent(IObject parent){ this.parent=parent; return this; }
    
    /** getting parent object */
    public IObject parent();
    /** setting parent object */
    public ISubobject parent(IObject parent);
    
}
