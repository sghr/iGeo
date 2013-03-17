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
   A subobject of IObject to contain numerical geometry data
   
   @author Satoru Sugihara
*/
public class IParameterObject /*extends*/ implements ISubobject{
    
    public IObject parent;
    
    /**
       default constractor doesn't instantiate parent IObject
    */
    public IParameterObject(){}
    
    /**
       constractor with IServerI arument instantiate parent element and stores in
       the IServer in the holder if the argument is not null.
       if the argument is null, it's stored in default (static current) IServer
    */
    public IParameterObject(IServerI holder){
	createObject(holder);
    }
    
    protected void createObject(IServerI holder){
	parent = new IObject(holder);
	parent.setParameter(this);
    }

    // implementation of ISubobject
    public IObject parent(){ return parent; }
    public ISubobject parent(IObject parent){ this.parent=parent; return this; }
    

    
}
