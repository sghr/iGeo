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
   Abstract interface of 2 dimensional vector.
   
   @author Satoru Sugihara
*/
public interface IVec2I extends IVec2Op{
    public double x();
    public double y();
    
    /** setting x component */
    public IVec2I x(double vx);
    /** setting y component */
    public IVec2I y(double vy);
    
    /** setting x component */
    public IVec2I x(IDoubleI vx);
    /** setting y component */
    public IVec2I y(IDoubleI vy);
    
    /** getting x component */
    public double x(ISwitchE e);
    /** getting y component */
    public double y(ISwitchE e);
    
    /** getting x component */
    public IDoubleI x(ISwitchR r);
    /** getting y component */
    public IDoubleI y(ISwitchR r);
    

    public IVec2 get();
    public IVec2I dup();
    
    public IVecI to3d();
    public IVecI to3d(double z);
    public IVecI to3d(IDoubleI w);
    public IVec4I to4d();
    public IVec4I to4d(double z, double w);
    public IVec4I to4d(IDoubleI z, IDoubleI w);
    
    public IDoubleI getX();
    public IDoubleI getY();
    
    public IVec2I set(IVec2I v);
    public IVec2I set(double x, double y);
    public IVec2I set(IDoubleI x, IDoubleI y);

    public IVec2I add(double x, double y);
    public IVec2I add(IDoubleI x, IDoubleI y);
    public IVec2I add(IVec2I v);
    public IVec2I sub(double x, double y);
    public IVec2I sub(IDoubleI x, IDoubleI y);
    public IVec2I sub(IVec2I v);
    public IVec2I mul(IDoubleI v);
    public IVec2I mul(double v);
    public IVec2I div(IDoubleI v);
    public IVec2I div(double v);
    public IVec2I neg();
    /** alias of neg */
    public IVec2I rev();
    /** alias of neg */
    public IVec2I flip();
    
    /** setting all zero */
    public IVec2I zero();

    /** scale add */
    public IVec2I add(IVec2I v, double f);
    /** scale add */
    public IVec2I add(IVec2I v, IDoubleI f);
    
    /** scale add; alias */
    public IVec2I add(double f, IVec2I v);
    /** scale add; alias */
    public IVec2I add(IDoubleI f, IVec2I v);
    
    /** dot product */
    public double dot(IVec2I v);
    /** dot product */
    public double dot(double vx, double vy);
    /** dot product */
    public double dot(ISwitchE e, IVec2I v);
    /** dot product */
    public IDoubleI dot(ISwitchR r, IVec2I v);
    
    /** cross product, returning 3D vector */
    public IVecI cross(IVec2I v);
    /** cross product, returning 3D vector */
    public IVecI cross(double vx, double vy);

    /** getting length */
    public double len();
    /** getting length */
    public double len(ISwitchE e);
    /** getting length */
    public IDoubleI len(ISwitchR r);
    
    /** getting squared length */
    public double len2();
    /** getting squared length */
    public double len2(ISwitchE e);
    /** getting squared length */
    public IDoubleI len2(ISwitchR r);

    /** setting length */
    public IVec2I len(IDoubleI l);
    /** setting length */
    public IVec2I len(double l);

    /** unitize */
    public IVec2I unit();
    
    /** rotate 90 deg */
    public IVec2I ortho();
    
    /** distance */
    public double dist(IVec2I v);
    /** distance */
    public double dist(double vx, double vy);
    /** distance */
    public double dist(ISwitchE e, IVec2I v);
    /** distance */
    public IDoubleI dist(ISwitchR r, IVec2I v);
    
    /** squared distance */
    public double dist2(IVec2I v);
    /** squared distance */
    public double dist2(double vx, double vy);
    /** squared distance */
    public double dist2(ISwitchE e, IVec2I v);
    /** squared distance */
    public IDoubleI dist2(ISwitchR r, IVec2I v);
    
    /** whether location is same or not */
    public boolean eq(IVec2I v);
    /** whether location is same or not */
    public boolean eq(double vx, double vy);
    /** whether location is same or not */
    public boolean eq(ISwitchE e, IVec2I v);
    /** whether location is same or not */
    public IBoolI eq(ISwitchR r, IVec2I v);

    /** whether location is same or not with tolerance*/
    public boolean eq(IVec2I v, double tolerance);
    /** whether location is same or not with tolerance*/
    public boolean eq(double vx, double vy, double tolerance);
    /** whether location is same or not with tolerance*/
    public boolean eq(ISwitchE e, IVec2I v, double tolerance);
    /** whether location is same or not with tolerance*/
    public IBoolI eq(ISwitchR r, IVec2I v, IDoubleI tolerance);
    
    /** check if X is same */
    public boolean eqX(IVec2I v);
    /** check if Y is same */
    public boolean eqY(IVec2I v);
    /** check if X is same */
    public boolean eqX(double vx);
    /** check if Y is same */
    public boolean eqY(double vy);
    /** check if X is same */
    public boolean eqX(ISwitchE e, IVec2I v);
    /** check if Y is same */
    public boolean eqY(ISwitchE e, IVec2I v);
    /** check if X is same */
    public IBoolI eqX(ISwitchR r, IVec2I v);
    /** check if Y is same */
    public IBoolI eqY(ISwitchR r, IVec2I v);
    
    /** check if X is same within tolerance */
    public boolean eqX(IVec2I v, double tolerance);
    /** check if Y is same within tolerance */
    public boolean eqY(IVec2I v, double tolerance);
    /** check if X is same within tolerance */
    public boolean eqX(double vx, double tolerance);
    /** check if Y is same within tolerance */
    public boolean eqY(double vy, double tolerance);
    /** check if X is same within tolerance */
    public boolean eqX(ISwitchE e, IVec2I v, double tolerance);
    /** check if Y is same within tolerance */
    public boolean eqY(ISwitchE e, IVec2I v, double tolerance);
    /** check if X is same within tolerance */
    public IBoolI eqX(ISwitchR r, IVec2I v, IDoubleI tolerance);
    /** check if Y is same within tolerance */
    public IBoolI eqY(ISwitchR r, IVec2I v, IDoubleI tolerance);
    
    
    /** angle between two vectors */
    public double angle(IVec2I v);
    /** angle between two vectors */
    public double angle(double vx, double vy);
    /** angle between two vectors */
    public double angle(ISwitchE e, IVec2I v);
    /** angle between two vectors */
    public IDoubleI angle(ISwitchR r, IVec2I v);
    
    /** rotation */
    public IVec2I rot(double angle);
    /** rotation */
    public IVec2I rot(IDoubleI angle);

    /** rotation around a center */
    public IVec2I rot(IVec2I center, double angle);
    /** rotation around a center */
    public IVec2I rot(double centerX, double centerY, double angle);
    /** rotation around a center */
    public IVec2I rot(IVec2I center, IDoubleI angle);

    /** rotate towards destination direction */
    public IVec2I rot(IVec2I destDir);
    /** rotate around a center towards destination point */
    public IVec2I rot(IVec2I center, IVec2I destPt);
    
    /** alias of mul */
    public IVec2I scale(IDoubleI f);
    /** alias of mul */
    public IVec2I scale(double f);
    /** scale from a center */
    public IVec2I scale(IVec2I center, IDoubleI f);
    /** scale from a center */
    public IVec2I scale(IVec2I center, double f);
    /** scale from a center */
    public IVec2I scale(double centerX, double centerY, double f);

    
    
    /** scale only in 1 direction */
    public IVec2I scale1d(IVec2I axis, double f);
    /** scale only in 1 direction */
    public IVec2I scale1d(IVec2I axis, IDoubleI f);
    /** scale only in 1 direction */
    public IVec2I scale1d(double axisX, double axisY, double f);
    /** scale only in 1 direction from a center */
    public IVec2I scale1d(IVec2I center, IVec2I axis, double f);
    /** scale only in 1 direction from a center */
    public IVec2I scale1d(IVec2I center, IVec2I axis, IDoubleI f);
    /** scale only in 1 direction from a center */
    public IVec2I scale1d(double centerX, double centerY, double axisX, double axisY, double f);
    
    
    /** reflect (mirror) 2 dimensionally to the other side of the line */
    public IVec2I ref(IVec2I lineDir);
    /** reflect (mirror) 2 dimensionally to the other side of the line */
    public IVec2I ref(double lineX, double lineY);
    /** reflect (mirror) 2 dimensionally to the other side of the line at a center */
    public IVec2I ref(IVec2I center, IVec2I lineDir);
    /** reflect (mirror) 2 dimensionally to the other side of the line at a center */
    public IVec2I ref(double centerX, double centerY, double lineX, double lineY);
    /** reflect (mirror) 2 dimensionally to the other side of the line */
    public IVec2I mirror(IVec2I lineDir);
    /** reflect (mirror) 2 dimensionally to the other side of the line */
    public IVec2I mirror(double lineX, double lineY);
    /** reflect (mirror) 2 dimensionally to the other side of the line at a center */
    public IVec2I mirror(IVec2I center, IVec2I lineDir);
    /** reflect (mirror) 2 dimensionally to the other side of the line at a center */
    public IVec2I mirror(double centerX, double centerY, double lineX, double lineY);
    
    
    /** shear operation on XY*/
    public IVec2I shear(double sxy, double syx);
    /** shear operation on XY*/
    public IVec2I shear(IDoubleI sxy, IDoubleI syx);
    /** shear operation on XY*/
    public IVec2I shear(IVec2I center, double sxy, double syx);
    /** shear operation on XY*/
    public IVec2I shear(IVec2I center, IDoubleI sxy, IDoubleI syx);
    
    
    /** alias of add() */
    public IVec2I translate(double x, double y);
    /** alias of add() */
    public IVec2I translate(IDoubleI x, IDoubleI y);
    /** alias of add() */
    public IVec2I translate(IVec2I v);
    
    
    /** transform with 2x2 transform matrix */
    public IVec2I transform(IMatrix2I mat);
    /** transform with 3x3 transform matrix */
    public IVec2I transform(IMatrix3I mat);
    /** transform with transform vectors */
    public IVec2I transform(IVec2I xvec, IVec2I yvec);
    /** transform with transform vectors */
    public IVec2I transform(IVec2I xvec, IVec2I yvec, IVec2I translate);
    
    
    /** mv() is alias of add() */
    public IVec2I mv(double x, double y);
    /** mv() is alias of add() */
    public IVec2I mv(IDoubleI x, IDoubleI y);
    /** mv() is alias of add() */
    public IVec2I mv(IVec2I v);
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */
    public IVec2I cp();
    
    /** cp() is alias of dup().add() */
    public IVec2I cp(double x, double y);
    /** cp() is alias of dup().add() */
    public IVec2I cp(IDoubleI x, IDoubleI y);
    /** cp() is alias of dup().add() */
    public IVec2I cp(IVec2I v);
    
    
    
    // methods creating new instance
    /** create a new instance of difference */
    public IVec2I dif(IVec2I v);
    /** create a new instance of difference */
    public IVec2I dif(double vx, double vy);
    /** alias of dif */
    public IVec2I diff(IVec2I v);
    /** alias of dif */
    public IVec2I diff(double vx, double vy);
    /** create a new instance of midpoint */
    public IVec2I mid(IVec2I v);
    /** create a new instance of midpoint */
    public IVec2I mid(double vx, double vy);
    /** create a new instance of summation */
    public IVec2I sum(IVec2I v);
    /** create a new instance of summation */
    public IVec2I sum(double vx, double vy);
    /** create a new instance of summation */
    public IVec2I sum(IVec2I... v);
    
    /** create a new instance of bisector */
    public IVec2I bisect(IVec2I v);
    /** create a new instance of bisector */
    public IVec2I bisect(double vx, double vy);
    
    /** create a new instance of weighted sum */
    public IVec2I sum(IVec2I v2, double w1, double w2);
    /** create a new instance of weighted sum */
    public IVec2I sum(IVec2I v2, double w2);
    /** create a new instance of weighted sum */
    public IVec2I sum(IVec2I v2, IDoubleI w1, IDoubleI w2);
    /** create a new instance of weighted sum */
    public IVec2I sum(IVec2I v2, IDoubleI w2);

    /** alias of cross */
    public IVecI nml(IVec2I v);
    /** alias of cross */
    public IVecI nml(double vx, double vy);
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVecI nml(IVec2I pt1, IVec2I pt2);
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVecI nml(double vx1, double vy1, double vx2, double vy2);
    
    /** check if the content of vector is valid; no NaN value */
    public boolean isValid();
        
}
