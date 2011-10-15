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

package igeo.geo;

/**
   Abstract interface of 4 dimensional vector.
   4 dimensional vector is mainly used for control points of NURBS geometry
   to include weights
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public interface IVec4I extends IVec4Op, IVecI{
    public double x();
    public double y();
    public double z();
    public double w();
    public IVec4 get();
    public IVec4I dup();
    
    public IVec2I to2d();
    public IVecI to3d();
    
    public IDoubleI getX();
    public IDoubleI getY();
    public IDoubleI getZ();
    public IDoubleI getW();
    
    public IVec4I set(double x, double y, double z, double w);
    public IVec4I set(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w);
    public IVec4I set(IVecI v, double w);
    public IVec4I set(IVecI v, IDoubleI w);
    
    public IVec4I set(IVec4I v);
    public IVec4I set(IVecI v);
    public IVec4I set(double x, double y, double z);
    public IVec4I set(IDoubleI x, IDoubleI y, IDoubleI z);
    
    
    public IVec4I add(IVecI v);
    public IVec4I sub(IVecI v);
    public IVec4I mul(IDoubleI v);
    public IVec4I mul(double v);
    public IVec4I div(IDoubleI v);
    public IVec4I div(double v);
    public IVec4I neg();
    public IVec4I rev();
    
    public IVec4I add(IVecI v, double f);
    public IVec4I add(IVecI v, IDoubleI f); 
    
    public IVec4I len(IDoubleI l);
    public IVec4I len(double l);
    
    public IVec4I unit();
    
    public IVec4I cross(IVecI v);
    
    
    public boolean eqX(IVecI v);
    public boolean eqY(IVecI v);
    public boolean eqZ(IVecI v);
    public boolean eqW(IVec4I v);
    //public IBoolI eqXR(IVecI v);
    //public IBoolI eqYR(IVecI v);
    //public IBoolI eqZR(IVecI v);
    //public IBoolI eqWR(IVec4I v);
    public boolean eqX(ISwitchE e, IVecI v);
    public boolean eqY(ISwitchE e, IVecI v);
    public boolean eqZ(ISwitchE e, IVecI v);
    public boolean eqW(ISwitchE e, IVec4I v);
    public IBoolI eqX(ISwitchR r, IVecI v);
    public IBoolI eqY(ISwitchR r, IVecI v);
    public IBoolI eqZ(ISwitchR r, IVecI v);
    public IBoolI eqW(ISwitchR r, IVec4I v);
    
    public boolean eqX(IVecI v, double resolution);
    public boolean eqY(IVecI v, double resolution);
    public boolean eqZ(IVecI v, double resolution);
    public boolean eqW(IVec4I v, double resolution);
    //public IBoolI eqXR(IVecI v, IDoubleI resolution);
    //public IBoolI eqYR(IVecI v, IDoubleI resolution);
    //public IBoolI eqZR(IVecI v, IDoubleI resolution);
    //public IBoolI eqWR(IVec4I v, IDoubleI resolution);
    public boolean eqX(ISwitchE e, IVecI v, double resolution);
    public boolean eqY(ISwitchE e, IVecI v, double resolution);
    public boolean eqZ(ISwitchE e, IVecI v, double resolution);
    public boolean eqW(ISwitchE e, IVec4I v, double resolution);
    public IBoolI eqX(ISwitchR r, IVecI v, IDoubleI resolution);
    public IBoolI eqY(ISwitchR r, IVecI v, IDoubleI resolution);
    public IBoolI eqZ(ISwitchR r, IVecI v, IDoubleI resolution);
    public IBoolI eqW(ISwitchR r, IVec4I v, IDoubleI resolution);
    
    
    public IVec4I rot(IVecI axis, IDoubleI angle);
    public IVec4I rot(IVecI axis, double angle);
    public IVec4I rot(IVecI center, IVecI axis, IDoubleI angle);
    public IVec4I rot(IVecI center, IVecI axis, double angle);
    
    public IVec4I rot(IVecI axis, IVecI destDir);
    public IVec4I rot(IVecI center, IVecI axis, IVecI destPt);
    
    public IVec4I scale(IDoubleI f);
    public IVec4I scale(double f);
    
    public IVec4I scale(IVecI center, IDoubleI f);
    public IVec4I scale(IVecI center, double f);
    
    public IVec4I mirror(IVecI planeDir);
    public IVec4I mirror(IVecI center, IVecI planeDir);
    
    public IVec4I transform(IMatrix3I mat);
    public IVec4I transform(IMatrix4I mat);
    public IVec4I transform(IVecI xvec, IVecI yvec, IVecI zvec);
    public IVec4I transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate);
    
    
    // methods creating new instance
    // use these carefully. w is set to the object's w (not input or addition with input's)
    public IVec4I diff(IVecI v);
    public IVec4I mid(IVecI v);
    public IVec4I sum(IVecI v);
    public IVec4I sum(IVecI... v);
    
    public IVec4I bisect(IVecI v);
    
    public IVec4I sum(IVecI v2, double w1, double w2);
    public IVec4I sum(IVecI v2, double w2);
    
    public IVec4I sum(IVecI v2, IDoubleI w1, IDoubleI w2);
    public IVec4I sum(IVecI v2, IDoubleI w2);


    /** checking x, y, and z is valid number (not Infinite, nor NaN). */
    public boolean isValid();
    
    
}
