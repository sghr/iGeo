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
   Entity class of boolean to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public class IBool extends IParameterObject implements IBoolI, IEntityParameter{
    public boolean x;
    
    /**
       default constructor doesn't instantiate parent IGElement
    */
    public IBool(){}
    public IBool(boolean i){ x=i; }
    public IBool(IBool i){ x=i.x; }
    public IBool(IBoolI i){ x=i.x(); }
    
    /**
       constructor with IServerI instantiates parent IGElement
    */
    public IBool(IServerI s){ super(s); }
    public IBool(IServerI s, boolean i){ super(s);x=i; }
    public IBool(IServerI s, IBool i){ super(s);x=i.x; }
    public IBool(IServerI s, IBoolI i){ super(s);x=i.x(); }
    
    public boolean x(){ return x; }
    //public IBool get(){ return this; }
    public IBool get(){ return new IBool(x); }
    public IBool getX(){ return this; }
    public IBool dup(){ return new IBool(x); }
    public IBool cp(){ return dup(); }
    
    public IBool set(IBoolI v){ x=v.x(); return this; }
    public IBool set(IBool v){ x=v.x; return this; }
    public IBool set(boolean v){ x=v; return this; }
    
    public IBool and(IBoolI v){ x&=v.x(); return this; }
    public IBool and(IBool v){ x&=v.x; return this; }
    public IBool and(boolean v){ x&=v; return this; }
    public IBool or(IBoolI v){ x|=v.x(); return this; }
    public IBool or(IBool v){ x|=v.x; return this; }
    public IBool or(boolean v){ x&=v; return this; }
    public IBool not(){ x=!x; return this; }
    
    public boolean eq(IBoolI v){ return x==v.x(); }
    //public IBool eqR(IBoolI v){ return new IBool(eq(v)); }
    public boolean eq(ISwitchE e, IBoolI v){ return eq(v); }
    public IBool eq(ISwitchR r, IBoolI v){ return new IBool(eq(v)); }
    
}
