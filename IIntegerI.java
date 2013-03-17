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
   Interface of integer to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public interface IIntegerI extends IIntegerOp{
    /**
       @return Primitive value.
    */
    public int x();
    
    /**
       @return Fixed value but in wrapper class.
    */
    public IInteger get();

    /**
       @return Generic parameter of the value (either of entity class or reference class).
    */
    public IIntegerI getX();
    
    /**
       @return Duplicated instance.
    */
    public IIntegerI dup();
    
    /** alias of dup() */
    public IIntegerI cp();
    
    /** duplicate and add */
    public IIntegerI cp(int v);
    
    /** duplicate and add */
    public IIntegerI cp(IIntegerI v);
    
    /**
       convert reference tree to one constant value
    */
    //public IIntegerI const(); 
    
    public IIntegerI set(IIntegerI v);
    public IIntegerI set(int v);
    
    public IIntegerI set(double v);
    public IIntegerI set(IDoubleI v);
    
    public IIntegerI add(IIntegerI v);
    public IIntegerI add(int v);
    public IIntegerI sub(IIntegerI v);
    public IIntegerI sub(int v);
    public IIntegerI mul(IIntegerI v);
    public IIntegerI mul(int v);
    public IIntegerI div(IIntegerI v);
    public IIntegerI div(int v);
    public IIntegerI neg();
    
    public IIntegerI mod(IIntegerI v);
    public IIntegerI mod(int v);

    public boolean eq(int v);
    public boolean eq(IIntegerI v);
    //public IBoolI eqR(IIntegerI v);
    public boolean eq(ISwitchE e, IIntegerI v);
    public IBoolI eq(ISwitchR r, IIntegerI v);
    
    
    
}
