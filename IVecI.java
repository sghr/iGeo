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
   Abstract interface of 3 dimensional vector.
   
   @author Satoru Sugihara
*/
public interface IVecI extends IVecOp, ITransformable{
    //public static final IVec xaxis = new IVec(1,0,0);
    //public static final IVec yaxis = new IVec(0,1,0);
    //public static final IVec zaxis = new IVec(0,0,1);
    
    /** getting x component */
    public double x();
    /** getting y component */
    public double y();
    /** getting z component */
    public double z();

    /** setting x component */
    public IVecI x(double vx);
    /** setting y component */
    public IVecI y(double vy);
    /** setting z component */
    public IVecI z(double vz);
    
    /** setting x component */
    public IVecI x(IDoubleI vx);
    /** setting y component */
    public IVecI y(IDoubleI vy);
    /** setting z component */
    public IVecI z(IDoubleI vz);
    
    /** setting x component by x component of input vector*/
    public IVecI x(IVecI v);
    /** setting y component by y component of input vector*/
    public IVecI y(IVecI v);
    /** setting z component by z component of input vector*/
    public IVecI z(IVecI v);
    
    /** setting x component by x component of input vector*/
    public IVecI x(IVec2I v);
    /** setting y component by y component of input vector*/
    public IVecI y(IVec2I v);
    
    
    /** getting x component */
    public double x(ISwitchE e);
    /** getting y component */
    public double y(ISwitchE e);
    /** getting z component */
    public double z(ISwitchE e);
    
    /** getting x component */
    public IDoubleI x(ISwitchR r);
    /** getting y component */
    public IDoubleI y(ISwitchR r);
    /** getting z component */
    public IDoubleI z(ISwitchR r);
    
    
    
    public IVec get();
    
    public IVecI dup();
    
    public IVec2I to2d();
    public IVec2I to2d(IVecI projectionDir);
    public IVec2I to2d(IVecI xaxis, IVecI yaxis);
    public IVec2I to2d(IVecI xaxis, IVecI yaxis, IVecI origin);
    
    
    public IVec4I to4d();
    public IVec4I to4d(double w);
    public IVec4I to4d(IDoubleI w);
    
    public IDoubleI getX();
    public IDoubleI getY();
    public IDoubleI getZ();
        
    
    public IVecI set(IVecI v);
    public IVecI set(double x, double y, double z);
    public IVecI set(IDoubleI x, IDoubleI y, IDoubleI z);
    
    public IVecI add(double x, double y, double z);
    public IVecI add(IDoubleI x, IDoubleI y, IDoubleI z);
    public IVecI add(IVecI v);
    public IVecI sub(double x, double y, double z);
    public IVecI sub(IDoubleI x, IDoubleI y, IDoubleI z);
    public IVecI sub(IVecI v);
    public IVecI mul(IDoubleI v);
    public IVecI mul(double v);
    public IVecI div(IDoubleI v);
    public IVecI div(double v);
    public IVecI neg();
    /** alias of neg() */
    public IVecI rev();
    /** alias of neg() */
    public IVecI flip();
    
    /** setting all zero */
    public IVecI zero();
    
    /** scale add */
    public IVecI add(IVecI v, double f);
    /** scale add */
    public IVecI add(IVecI v, IDoubleI f); 
    /** scale add */
    public IVecI add(double f, IVecI v);
    /** scale add */
    public IVecI add(IDoubleI f, IVecI v);
    
    /** dot product */
    public double dot(IVecI v);
    /** dot product */
    public double dot(double vx, double vy, double vz);
    /** dot product */
    public double dot(ISwitchE e, IVecI v);
    /** dot product */
    public IDoubleI dot(ISwitchR r, IVecI v);
    
    /** cross product, creating a new instance */
    public IVecI cross(IVecI v);
    /** cross product, creating a new instance */
    public IVecI cross(double vx, double vy, double vz);

    /** getting length of the vector */
    public double len();
    /** getting length of the vector */
    public double len(ISwitchE e);
    /** getting length of the vector */
    public IDoubleI len(ISwitchR r);
    
    /** getting squared length of the vector */
    public double len2();
    /** getting squared length of the vector */
    public double len2(ISwitchE e);
    /** getting squared length of the vector */
    public IDoubleI len2(ISwitchR r);
    
    
    //public IVecI setLen(IDoubleI l); // -> len(double l)
    //public IVecI setLen(double l); // -> len(double l);

    /** setting length */
    public IVecI len(IDoubleI l);
    /** setting length */
    public IVecI len(double l);

    //public IVecI normalize();
    /** unitize */
    public IVecI unit();
    
    /** distance of two vectors */
    public double dist(IVecI v);
    /** distance of two vectors */
    public double dist(double vx, double vy, double vz);
    /** distance of two vectors */
    public double dist(ISwitchE e, IVecI v);
    /** distance of two vectors */
    public IDoubleI dist(ISwitchR r, IVecI v);

    /** squared distance of two vectors */
    public double dist2(IVecI v);
    /** squared distance of two vectors */
    public double dist2(double vx, double vy, double vz);
    /** squared distance of two vectors */
    public double dist2(ISwitchE e, IVecI v);
    /** squared distance of two vectors */
    public IDoubleI dist2(ISwitchR r, IVecI v);
    
    /** check whether location is same or not */
    public boolean eq(IVecI v);
    /** check whether location is same or not */
    public boolean eq(double vx, double vy, double vz);
    /** check whether location is same or not */
    public boolean eq(ISwitchE e, IVecI v);
    /** check whether location is same or not */
    public IBoolI eq(ISwitchR r, IVecI v);
    
    /** check whether location is same or not with tolerance */
    public boolean eq(IVecI v, double tolerance);
    /** check whether location is same or not with tolerance */
    public boolean eq(double vx, double vy, double vz, double tolerance);
    /** check whether location is same or not with tolerance */
    public boolean eq(ISwitchE e, IVecI v, double tolerance);
    /** check whether location is same or not with tolerance */
    public IBoolI eq(ISwitchR r, IVecI v, IDoubleI tolerance);

    /** check if same in X */
    public boolean eqX(IVecI v);
    /** check if same in Y */
    public boolean eqY(IVecI v);
    /** check if same in Z */
    public boolean eqZ(IVecI v);
    /** check if same in X */
    public boolean eqX(double vx);
    /** check if same in Y */
    public boolean eqY(double vy);
    /** check if same in Z */
    public boolean eqZ(double vz);
    /** check if same in X */
    public boolean eqX(ISwitchE e, IVecI v);
    /** check if same in Y */
    public boolean eqY(ISwitchE e, IVecI v);
    /** check if same in Z */
    public boolean eqZ(ISwitchE e, IVecI v);
    /** check if same in X */
    public IBoolI eqX(ISwitchR r, IVecI v);
    /** check if same in Y */
    public IBoolI eqY(ISwitchR r, IVecI v);
    /** check if same in Z */
    public IBoolI eqZ(ISwitchR r, IVecI v);
    
    /** check if same in X with tolerance */
    public boolean eqX(IVecI v, double tolerance);
    /** check if same in Y with tolerance */
    public boolean eqY(IVecI v, double tolerance);
    /** check if same in Z with tolerance */
    public boolean eqZ(IVecI v, double tolerance);
    /** check if same in X with tolerance */
    public boolean eqX(double vx, double tolerance);
    /** check if same in Y with tolerance */
    public boolean eqY(double vy, double tolerance);
    /** check if same in Z with tolerance */
    public boolean eqZ(double vz, double tolerance);
    /** check if same in X with tolerance */
    public boolean eqX(ISwitchE e, IVecI v, double tolerance);
    /** check if same in Y with tolerance */
    public boolean eqY(ISwitchE e, IVecI v, double tolerance);
    /** check if same in Z with tolerance */
    public boolean eqZ(ISwitchE e, IVecI v, double tolerance);
    /** check if same in X with tolerance */
    public IBoolI eqX(ISwitchR r, IVecI v, IDoubleI tolerance);
    /** check if same in Y with tolerance */
    public IBoolI eqY(ISwitchR r, IVecI v, IDoubleI tolerance);
    /** check if same in Z with tolerance */
    public IBoolI eqZ(ISwitchR r, IVecI v, IDoubleI tolerance);
    
    
    /** get angle between two vector from 0 to Pi */
    public double angle(IVecI v);
    /** get angle between two vector from 0 to Pi */
    public double angle(double vx, double vy, double vz);
    /** get angle between two vector from 0 to Pi */
    public double angle(ISwitchE e, IVecI v);
    /** get angle between two vector from 0 to Pi */
    public IDoubleI angle(ISwitchR r, IVecI v);
    
    /**
       @param axis axis to determin sign of angle following right-handed screw rule.
       @return angle between two vector from -Pi to Pi. Sign follows right-handed screw rule along axis
    */
    public double angle(IVecI v, IVecI axis);
    /**
       @return angle between two vector from -Pi to Pi. Sign follows right-handed screw rule along axis
    */
    public double angle(double vx, double vy, double vz, double axisX, double axisY, double axisZ);
    /**
       @param axis axis to determin sign of angle following right-handed screw rule.
       @return angle between two vector from -Pi to Pi. Sign follows right-handed screw rule along axis
    */
    public double angle(ISwitchE e, IVecI v, IVecI axis);
    /**
       @param axis axis to determin sign of angle following right-handed screw rule.
       @return angle between two vector from -Pi to Pi. Sign follows right-handed screw rule along axis
    */
    public IDoubleI angle(ISwitchR r, IVecI v, IVecI axis);
    
    /** rotation around axis vector */
    public IVecI rot(IVecI axis, IDoubleI angle);
    /** rotation around axis vector */
    public IVecI rot(IVecI axis, double angle);
    /** rotation around axis vector */
    public IVecI rot(double axisX, double axisY, double axisZ, double angle);
    /** rotation on XY plane */
    public IVecI rot(double angle);
    /** rotation on XY plane */
    public IVecI rot(IDoubleI angle);
    
    /** rotation around axis vector and center point */
    public IVecI rot(IVecI center, IVecI axis, IDoubleI angle);
    /** rotation around axis vector and center point */
    public IVecI rot(IVecI center, IVecI axis, double angle);
    /** rotation around axis vector and center point */
    public IVecI rot(double centerX, double centerY, double centerZ,
		     double axisX, double axisY, double axisZ, double angle);
    
    /** rotate to destination direction vector */
    public IVecI rot(IVecI axis, IVecI destDir);
    /** rotate to destination point location */
    public IVecI rot(IVecI center, IVecI axis, IVecI destPt);
    
    
    /** rotation on xy-plane; alias of rot(double) */
    public IVecI rot2(double angle);
    /** rotation on xy-plane; alias of rot(IDoubleI) */
    public IVecI rot2(IDoubleI angle);
    
    /** rotation on xy-plane */
    public IVecI rot2(IVecI center, double angle);
    /** rotation on xy-plane */
    public IVecI rot2(IVecI center, IDoubleI angle);
    /** rotation on xy-plane */
    public IVecI rot2(double centerX, double centerY, double angle);
    
    /** rotation on xy-plane towards destDir */
    public IVecI rot2(IVecI destDir);
    /** rotation on xy-plane towards destPt */
    public IVecI rot2(IVecI center, IVecI destPt);
    
    
    /** alias of mul */
    public IVecI scale(IDoubleI f);
    /** alias of mul */
    public IVecI scale(double f);
    /** scale from a center */
    public IVecI scale(IVecI center, IDoubleI f);
    /** scale from a center */
    public IVecI scale(IVecI center, double f);
    /** scale from a center */
    public IVecI scale(double centerX, double centerY, double centerZ, double f);
    
    
    /** scale only in 1 direction */
    public IVecI scale1d(IVecI axis, double f);
    /** scale only in 1 direction */
    public IVecI scale1d(IVecI axis, IDoubleI f);
    /** scale only in 1 direction */
    public IVecI scale1d(double axisX, double axisY, double axisZ, double f);
    /** scale only in 1 direction from a center */
    public IVecI scale1d(IVecI center, IVecI axis, double f);
    /** scale only in 1 direction from a center */
    public IVecI scale1d(IVecI center, IVecI axis, IDoubleI f);
    /** scale only in 1 direction from a center */
    public IVecI scale1d(double centerX, double centerY, double centerZ,
			 double axisX, double axisY, double axisZ, double f);
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IVecI ref(IVecI planeDir);
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IVecI ref(double planeX, double planeY, double planeZ);
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IVecI ref(IVecI center, IVecI planeDir);
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IVecI ref(double centerX, double centerY, double centerZ, double planeX, double planeY, double planeZ);
    /** alias of ref */
    public IVecI mirror(IVecI planeDir);
    /** alias of ref */
    public IVecI mirror(double planeX, double planeY, double planeZ);
    /** alias of ref */
    public IVecI mirror(IVecI center, IVecI planeDir);
    /** alias of ref */
    public IVecI mirror(double centerX, double centerY, double centerZ, double planeX, double planeY, double planeZ);
    
    
    /** shear operation */
    public IVecI shear(double sxy, double syx, double syz,
		       double szy, double szx, double sxz);
    /** shear operation */
    public IVecI shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    /** shear operation */
    public IVecI shear(IVecI center, double sxy, double syx, double syz,
		       double szy, double szx, double sxz);
    /** shear operation */
    public IVecI shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    
    /** shear operation on XY*/
    public IVecI shearXY(double sxy, double syx);
    /** shear operation on XY*/
    public IVecI shearXY(IDoubleI sxy, IDoubleI syx);
    /** shear operation on XY*/
    public IVecI shearXY(IVecI center, double sxy, double syx);
    /** shear operation on XY*/
    public IVecI shearXY(IVecI center, IDoubleI sxy, IDoubleI syx);

    /** shear operation on YZ*/
    public IVecI shearYZ(double syz, double szy);
    /** shear operation on YZ*/
    public IVecI shearYZ(IDoubleI syz, IDoubleI szy);
    /** shear operation on YZ*/
    public IVecI shearYZ(IVecI center, double syz, double szy);
    /** shear operation on YZ*/
    public IVecI shearYZ(IVecI center, IDoubleI syz, IDoubleI szy);

    /** shear operation on ZX*/
    public IVecI shearZX(double szx, double sxz);
    /** shear operation on ZX*/
    public IVecI shearZX(IDoubleI szx, IDoubleI sxz);
    /** shear operation on ZX*/
    public IVecI shearZX(IVecI center, double szx, double sxz);
    /** shear operation on ZX*/
    public IVecI shearZX(IVecI center, IDoubleI szx, IDoubleI sxz);
    
    
    /** alias of add() */
    public IVecI translate(double x, double y, double z);
    /** alias of add() */
    public IVecI translate(IDoubleI x, IDoubleI y, IDoubleI z);
    /** alias of add() */
    public IVecI translate(IVecI v);
    
    /** transform with a transformation matrix */
    public IVecI transform(IMatrix3I mat);
    /** transform with a transformation matrix */
    public IVecI transform(IMatrix4I mat);
    /** transform with a transformation vectors */
    public IVecI transform(IVecI xvec, IVecI yvec, IVecI zvec);
    /** transform with a transformation vectors */
    public IVecI transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate);
    
    
    /** mv() is alias of add() */
    public IVecI mv(double x, double y, double z);
    /** mv() is alias of add() */
    public IVecI mv(IDoubleI x, IDoubleI y, IDoubleI z);
    /** mv() is alias of add() */
    public IVecI mv(IVecI v);
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IVecI cp();
    
    /** cp() is alias of dup().add() */
    public IVecI cp(double x, double y, double z);
    /** cp() is alias of dup().add() */
    public IVecI cp(IDoubleI x, IDoubleI y, IDoubleI z);
    /** cp() is alias of dup().add() */
    public IVecI cp(IVecI v);
    
    
    // methods creating new instance
    /** create a new instance of difference */
    public IVecI dif(IVecI v);
    /** create a new instance of difference */
    public IVecI dif(double vx, double vy, double vz);
    /** alias of dif */
    public IVecI diff(IVecI v);
    /** alias of dif */
    public IVecI diff(double vx, double vy, double vz);
    /** create a new instance of midpoint */
    public IVecI mid(IVecI v);
    /** create a new instance of midpoint */
    public IVecI mid(double vx, double vy, double vz);
    /** create a new instance of total summation */
    public IVecI sum(IVecI v);
    /** create a new instance of total summation */
    public IVecI sum(double vx, double vy, double vz);
    /** create a new instance of total summation */
    public IVecI sum(IVecI... v);
    
    /** create a new instance of bisector */
    public IVecI bisect(IVecI v);
    /** create a new instance of bisector */
    public IVecI bisect(double vx, double vy, double vz);
    
    /** create a new instance of weighted sum */
    public IVecI sum(IVecI v2, double w1, double w2);
    /** create a new instance of weighted sum */
    public IVecI sum(IVecI v2, double w2);
    /** create a new instance of weighted sum */
    public IVecI sum(IVecI v2, IDoubleI w1, IDoubleI w2);
    /** create a new instance of weighted sum */
    public IVecI sum(IVecI v2, IDoubleI w2);
    
    
    /** alias of cross */
    public IVecI nml(IVecI v);
    /** alias of cross */
    public IVecI nml(double vx, double vy, double vz);
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVecI nml(IVecI pt1, IVecI pt2);
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVecI nml(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2);
    
    /** check if the content of vector is valid; no NaN value */
    public boolean isValid();
    
}
