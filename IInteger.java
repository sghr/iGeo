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
   Entity class of integer to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public class IInteger extends IParameterObject implements IIntegerI, IEntityParameter{
    public int x;
    
    public IInteger(){}
    public IInteger(int i){ x=i; }
    public IInteger(IInteger i){ x=i.x; }
    public IInteger(IIntegerI i){ x=i.x(); }
    public IInteger(double v){ x=(int)v; }
    public IInteger(IDoubleI v){ x=(int)v.x(); }
    
    public IInteger(IServerI s){ super(s); }
    public IInteger(IServerI s, int i){ super(s); x=i; }
    public IInteger(IServerI s, IInteger i){ super(s); x=i.x; }
    public IInteger(IServerI s, IIntegerI i){ super(s); x=i.x(); }
    public IInteger(IServerI s, double v){ super(s); x=(int)v; }
    public IInteger(IServerI s, IDoubleI v){ super(s); x=(int)v.x(); }
    
    public int x(){ return x; }
    //public IInteger get(){ return this; }
    public IInteger get(){ return new IInteger(x); }
    public IInteger getX(){ return this; }
    public IInteger dup(){ return new IInteger(x); }
    
    /** alias of dup() */
    public IInteger cp(){ return dup(); }
    
    /** duplicate and add */
    public IInteger cp(int v){ return dup().add(v); }
    /** duplicate and add */
    public IInteger cp(IInteger v){ return dup().add(v); }
    /** duplicate and add */
    public IInteger cp(IIntegerI v){ return dup().add(v); }
    
    
    public IInteger set(int v){ x=v; return this; }
    public IInteger set(IInteger v){ x=v.x; return this; }
    public IInteger set(IIntegerI v){ x=v.x(); return this; }
    
    public IInteger set(double v){ x=(int)v; return this; }
    public IInteger set(IDoubleI v){ x=(int)v.x(); return this; }
    
    public IInteger add(int i){ x+=i; return this; }
    public IInteger add(IInteger v){ x+=v.x; return this; }
    public IInteger add(IIntegerI v){ x+=v.x(); return this; }
    
    public IInteger sub(int i){ x-=i; return this; }
    public IInteger sub(IInteger v){ x-=v.x; return this; }
    public IInteger sub(IIntegerI v){ x-=v.x(); return this; }
    
    public IInteger mul(int i){ x*=i; return this; }
    public IInteger mul(IInteger v){ x*=v.x; return this; }
    public IInteger mul(IIntegerI v){ x*=v.x(); return this; }
    
    public IInteger div(int i){ x/=i; return this; }
    public IInteger div(IInteger v){ x/=v.x; return this; }
    public IInteger div(IIntegerI v){ x/=v.x(); return this; }
    
    public IInteger neg(){ x=-x; return this; }
    
    public IInteger mod(int i){ x=x%i; return this; }
    public IInteger mod(IInteger v){ x=x%v.x; return this; }
    public IInteger mod(IIntegerI v){ x=x%v.x(); return this; }

    public boolean eq(int v){ return x==v; }
    public boolean eq(IInteger v){ return x==v.x; }
    public boolean eq(IIntegerI v){ return x==v.x(); }
    //public IBool eqR(IIntegerI v){ return new IBool(eq(v)); }
    public boolean eq(ISwitchE e, IIntegerI v){ return eq(v); }
    public IBool eq(ISwitchR r, IIntegerI v){ return new IBool(eq(v)); }
    
}
