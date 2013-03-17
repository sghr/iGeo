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
   Interface of double (1 dimensional vector) to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public interface IDoubleI extends IDoubleOp{
    /**
       @return primitive value
    */
    public double x();
    
    /**
       @return fixed value but in wrapper class
    */
    public IDouble get();
    
    /**
       @return generic parameter of the value (either of entity class or reference class)
    */
    public IDoubleI getX();
    
    /**
       @return duplicated instance
    */
    public IDoubleI dup();

    /** alias of dup() */
    public IDoubleI cp();

    /** duplicate and add */
    public IDoubleI cp(double v);
    /** duplicate and add */
    public IDoubleI cp(IDoubleI v);
    
    public IDoubleI set(double v);
    public IDoubleI set(IDoubleI v);
    public IDoubleI set(IIntegerI v);
    
    public IDoubleI add(IDoubleI v);
    public IDoubleI add(double v);
    public IDoubleI sub(IDoubleI v);
    public IDoubleI sub(double v);
    public IDoubleI mul(IDoubleI v);
    public IDoubleI mul(double v);
    public IDoubleI div(IDoubleI v);
    public IDoubleI div(double v);
    public IDoubleI neg();
    
    public IDoubleI inv();
    
    public IDoubleI abs();
    
    public IDoubleI pow(double n);
    public IDoubleI pow(IDoubleI n);
    
    public IDoubleI sq();
    public IDoubleI sqrt();
    
    public IDoubleI exp();
    public IDoubleI log();
    
    public IDoubleI sin();
    public IDoubleI cos();
    public IDoubleI tan();
    
    public IDoubleI asin();
    public IDoubleI acos();
    public IDoubleI atan();
    public IDoubleI atan2(double x2);
    public IDoubleI atan2(IDoubleI x2);
    
    public IDoubleI deg();
    public IDoubleI rad();

    public boolean eq(double v);
    public boolean eq(IDoubleI v);
    //public IBoolI eqR(IDoubleI v);
    public boolean eq(ISwitchE e, IDoubleI v);
    public IBoolI eq(ISwitchR r, IDoubleI v);
    
    public boolean eq(double v, double resolution);
    public boolean eq(IDoubleI v, double resolution);
    //public IBoolI eqR(IDoubleI v, IDoubleI resolution);
    public boolean eq(ISwitchE e, IDoubleI v, double resolution);
    public IBoolI eq(ISwitchR r, IDoubleI v, IDoubleI resolution);
    
}
