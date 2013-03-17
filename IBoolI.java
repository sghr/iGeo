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
   Interface of boolean to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public interface IBoolI extends IBoolOp{
    /**
       @return primitive value
    */
    public boolean x();
    
    /**
       @return fixed value but in wrapper class
    */
    public IBool get();
    
    /**
       @return generic parameter of the value (either of entity class or reference class) (in one dimensional data, it's same with the instance itself)
    */
    public IBoolI getX();
    
    /**
       @return duplicated instance
    */
    public IBoolI dup();
    
    /** alias of dup() */
    public IBoolI cp();
    
    /**
       convert reference tree to one constant value
    */
    //public IBoolI const(); 
    
    public IBoolI set(IBoolI v);
    public IBoolI set(boolean v);
    
    public IBoolI and(IBoolI v);
    public IBoolI and(boolean v);
    public IBoolI or(IBoolI v);
    public IBoolI or(boolean v);
    public IBoolI not();
    
    public boolean eq(IBoolI v);
    //public IBoolI eqR(IBoolI v);
    public boolean eq(ISwitchE e, IBoolI v);
    public IBoolI eq(ISwitchR r, IBoolI v);
    
}
