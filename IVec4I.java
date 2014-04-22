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
   Abstract interface of 4 dimensional vector.
   4 dimensional vector is mainly used for control points of NURBS geometry
   to include weights
   
   @author Satoru Sugihara
*/
public interface IVec4I extends IVec4Op, IVecI{

    
    /** getting x component */
    public double x();
    /** getting y component */
    public double y();
    /** getting z component */
    public double z();
    /** getting w component */
    public double w();
    
    /** setting x component */
    public IVec4I x(double vx);
    /** setting y component */
    public IVec4I y(double vy);
    /** setting z component */
    public IVec4I z(double vz);
    /** setting w component */
    public IVec4I w(double vz);
    
    /** setting x component */
    public IVec4I x(IDoubleI vx);
    /** setting y component */
    public IVec4I y(IDoubleI vy);
    /** setting z component */
    public IVec4I z(IDoubleI vz);
    /** setting w component */
    public IVec4I w(IDoubleI vz);
    
    /** setting x component by x component of input vector*/
    public IVec4I x(IVecI v);
    /** setting y component by y component of input vector*/
    public IVec4I y(IVecI v);
    /** setting z component by z component of input vector*/
    public IVec4I z(IVecI v);
    /** setting w component by w component of input vector*/
    public IVec4I w(IVec4I v);
    
    /** setting x component by x component of input vector*/
    public IVec4I x(IVec2I v);
    /** setting y component by y component of input vector*/
    public IVec4I y(IVec2I v);
    
    
    /** getting x component */
    public double x(ISwitchE e);
    /** getting y component */
    public double y(ISwitchE e);
    /** getting z component */
    public double z(ISwitchE e);
    /** getting z component */
    public double w(ISwitchE e);
    
    /** getting x component */
    public IDoubleI x(ISwitchR r);
    /** getting y component */
    public IDoubleI y(ISwitchR r);
    /** getting z component */
    public IDoubleI z(ISwitchR r);
    /** getting z component */
    public IDoubleI w(ISwitchR r);
    
    

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
    /** alias of neg() */
    public IVec4I rev();
    /** alias of neg() */
    public IVec4I flip();
    /** setting all zero */
    public IVec4I zero();

    /** scale add */
    public IVec4I add(IVecI v, double f);
    /** scale add */
    public IVec4I add(IVecI v, IDoubleI f); 
    /** scale add alias */
    public IVec4I add(double f, IVecI v);
    /** scale add alias */
    public IVec4I add(IDoubleI f, IVecI v); 

    /** setting length */
    public IVec4I len(IDoubleI l);
    /** setting length */
    public IVec4I len(double l);
    
    public IVec4I unit();
    
    public IVec4I cross(IVecI v);
    public IVec4I cross(double vx, double vy, double vz);
    
    
    //public boolean eqX(IVecI v);
    //public boolean eqY(IVecI v);
    //public boolean eqZ(IVecI v);
    public boolean eqW(IVec4I v);
    public boolean eqW(double vw);
    //public boolean eqX(ISwitchE e, IVecI v);
    //public boolean eqY(ISwitchE e, IVecI v);
    //public boolean eqZ(ISwitchE e, IVecI v);
    public boolean eqW(ISwitchE e, IVec4I v);
    //public IBoolI eqX(ISwitchR r, IVecI v);
    //public IBoolI eqY(ISwitchR r, IVecI v);
    //public IBoolI eqZ(ISwitchR r, IVecI v);
    public IBoolI eqW(ISwitchR r, IVec4I v);
    
    //public boolean eqX(IVecI v, double tolerance);
    //public boolean eqY(IVecI v, double tolerance);
    //public boolean eqZ(IVecI v, double tolerance);
    public boolean eqW(IVec4I v, double tolerance);
    public boolean eqW(double vw, double tolerance);
    //public boolean eqX(ISwitchE e, IVecI v, double tolerance);
    //public boolean eqY(ISwitchE e, IVecI v, double tolerance);
    //public boolean eqZ(ISwitchE e, IVecI v, double tolerance);
    public boolean eqW(ISwitchE e, IVec4I v, double tolerance);
    //public IBoolI eqX(ISwitchR r, IVecI v, IDoubleI tolerance);
    //public IBoolI eqY(ISwitchR r, IVecI v, IDoubleI tolerance);
    //public IBoolI eqZ(ISwitchR r, IVecI v, IDoubleI tolerance);
    public IBoolI eqW(ISwitchR r, IVec4I v, IDoubleI tolerance);
    
    
    /** rotation on xy-plane */
    public IVec4I rot(IDoubleI angle);
    /** rotation on xy-plane */
    public IVec4I rot(double angle);
    /** rotation around axis */
    public IVec4I rot(IVecI axis, IDoubleI angle);
    /** rotation around axis */
    public IVec4I rot(IVecI axis, double angle);
    /** rotation around axis */
    public IVec4I rot(double vx, double vy, double vz, double angle);
    /** rotation around axis and center*/
    public IVec4I rot(IVecI center, IVecI axis, IDoubleI angle);
    /** rotation around axis and center*/
    public IVec4I rot(IVecI center, IVecI axis, double angle);
    /** rotation around axis and center*/
    public IVec4I rot(double centerX, double centerY, double centerZ,
		      double axisX, double axisY, double axisZ, double angle);
    
    /** rotation around axis towards destination direction */
    public IVec4I rot(IVecI axis, IVecI destDir);
    /** rotation around axis and center towards destination point */
    public IVec4I rot(IVecI center, IVecI axis, IVecI destPt);
    
    /** rotation on xy-plane; alias of rot(IDoubleI) */
    public IVec4I rot2(IDoubleI angle);
    /** rotation on xy-plane; alias of rot(double) */
    public IVec4I rot2(double angle);
    /** rotation on xy-plane at center */
    public IVec4I rot2(IVecI center, IDoubleI angle);
    /** rotation on xy-plane at center */
    public IVec4I rot2(IVecI center, double angle);
    /** rotation on xy-plane at center */
    public IVec4I rot2(double centerX, double centerY, double angle);
    
    /** rotation on xy-plane towards destination direction */
    public IVec4I rot2(IVecI destDir);
    /** rotation on xy-plane at center towards destination point */
    public IVec4I rot2(IVecI center, IVecI destPt);
        
    /** alias of mul */
    public IVec4I scale(IDoubleI f);
    /** alias of mul */
    public IVec4I scale(double f);

    /** scale around center */
    public IVec4I scale(IVecI center, IDoubleI f);
    /** scale around center */
    public IVec4I scale(IVecI center, double f);
    /** scale around center */
    public IVec4I scale(double centerX, double centerY, double centerZ, double f);
    
    
    /** scale only in 1 direction */
    public IVec4I scale1d(IVecI axis, double f);
    /** scale only in 1 direction */
    public IVec4I scale1d(IVecI axis, IDoubleI f);
    /** scale only in 1 direction */
    public IVec4I scale1d(double axisX, double axisY, double axisZ, double f);
    /** scale only in 1 direction from a center */
    public IVec4I scale1d(IVecI center, IVecI axis, double f);
    /** scale only in 1 direction from a center */
    public IVec4I scale1d(IVecI center, IVecI axis, IDoubleI f);
    /** scale only in 1 direction from a center */
    public IVec4I scale1d(double centerX, double centerY, double centerZ,
			 double axisX, double axisY, double axisZ, double f);
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IVec4I ref(IVecI planeDir);
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IVec4I ref(double planeX, double planeY, double planeZ);
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IVec4I ref(IVecI center, IVecI planeDir);
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IVec4I ref(double centerX, double centerY, double centerZ, double planeX, double planeY, double planeZ);
    /** alias of ref */
    public IVec4I mirror(IVecI planeDir);
    /** alias of ref */
    public IVec4I mirror(double planeX, double planeY, double planeZ);
    /** alias of ref */
    public IVec4I mirror(IVecI center, IVecI planeDir);
    /** alias of ref */
    public IVec4I mirror(double centerX, double centerY, double centerZ, double planeX, double planeY, double planeZ);
    
    
    /** shear operation */
    public IVec4I shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz);
    /** shear operation */
    public IVec4I shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    /** shear operation */
    public IVec4I shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz);
    /** shear operation */
    public IVec4I shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    
    /** shear operation on XY*/
    public IVec4I shearXY(double sxy, double syx);
    /** shear operation on XY*/
    public IVec4I shearXY(IDoubleI sxy, IDoubleI syx);
    /** shear operation on XY*/
    public IVec4I shearXY(IVecI center, double sxy, double syx);
    /** shear operation on XY*/
    public IVec4I shearXY(IVecI center, IDoubleI sxy, IDoubleI syx);
    
    /** shear operation on YZ*/
    public IVec4I shearYZ(double syz, double szy);
    /** shear operation on YZ*/
    public IVec4I shearYZ(IDoubleI syz, IDoubleI szy);
    /** shear operation on YZ*/
    public IVec4I shearYZ(IVecI center, double syz, double szy);
    /** shear operation on YZ*/
    public IVec4I shearYZ(IVecI center, IDoubleI syz, IDoubleI szy);
    
    /** shear operation on ZX*/
    public IVec4I shearZX(double szx, double sxz);
    /** shear operation on ZX*/
    public IVec4I shearZX(IDoubleI szx, IDoubleI sxz);
    /** shear operation on ZX*/
    public IVec4I shearZX(IVecI center, double szx, double sxz);
    /** shear operation on ZX*/
    public IVec4I shearZX(IVecI center, IDoubleI szx, IDoubleI sxz);
    
    /** alias of add() */
    public IVec4I translate(double x, double y, double z);
    /** alias of add() */
    public IVec4I translate(IDoubleI x, IDoubleI y, IDoubleI z);
    /** alias of add() */
    public IVec4I translate(IVecI v);
    
    /** transform with a transformation matrix */
    public IVec4I transform(IMatrix3I mat);
    /** transform with a transformation matrix */
    public IVec4I transform(IMatrix4I mat);
    /** transform with a transformation vectors */
    public IVec4I transform(IVecI xvec, IVecI yvec, IVecI zvec);
    /** transform with a transformation vectors */
    public IVec4I transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate);
    
    
    /** mv() is alias of add() */
    public IVec4I mv(double x, double y, double z);
    /** mv() is alias of add() */
    public IVec4I mv(IDoubleI x, IDoubleI y, IDoubleI z);
    /** mv() is alias of add() */
    public IVec4I mv(IVecI v);
    
    /** cp() is alias of dup() */ 
    public IVec4I cp();
    
    /** cp() is alias of dup().add() */
    public IVec4I cp(double x, double y, double z);
    /** cp() is alias of dup().add() */
    public IVec4I cp(IDoubleI x, IDoubleI y, IDoubleI z);
    /** cp() is alias of dup().add() */
    public IVec4I cp(IVecI v);

    
    // methods creating new instance
    // use these carefully. w is set to the current instance's w (not input or addition with input's)
    /** create a new instance of difference */
    public IVec4I dif(IVecI v);
    /** create a new instance of difference */
    public IVec4I dif(double vx, double vy, double vz);
    /** alias of dif */
    public IVec4I diff(IVecI v);
    /** alias of dif */
    public IVec4I diff(double vx, double vy, double vz);
    /** create a new instance of midpoint */
    public IVec4I mid(IVecI v);
    /** create a new instance of midpoint */
    public IVec4I mid(double vx, double vy, double vz);
    /** create a new instance of total summation */
    public IVec4I sum(IVecI v);
    /** create a new instance of total summation */
    public IVec4I sum(double vx, double vy, double vz);
    /** create a new instance of total summation */
    public IVec4I sum(IVecI... v);
    
    /** create a new instance of bisector */
    public IVec4I bisect(IVecI v);
    /** create a new instance of bisector */
    public IVec4I bisect(double vx, double vy, double vz);
    
    /** create a new instance of weighted sum */
    public IVec4I sum(IVecI v2, double w1, double w2);
    /** create a new instance of weighted sum */
    public IVec4I sum(IVecI v2, double w2);
    /** create a new instance of weighted sum */
    public IVec4I sum(IVecI v2, IDoubleI w1, IDoubleI w2);
    /** create a new instance of weighted sum */
    public IVec4I sum(IVecI v2, IDoubleI w2);
    
    
    /** alias of cross */
    public IVec4I nml(IVecI v);
    /** alias of cross */
    public IVec4I nml(double vx, double vy, double vz);
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVec4I nml(IVecI pt1, IVecI pt2);
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVec4I nml(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2);
    
    
    /** checking x, y, and z is valid number (not Infinite, nor NaN). */
    //public boolean isValid(); // defined in IVecI
    
    
}
