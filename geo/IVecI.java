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
   Abstract interface of 3 dimensional vector.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public interface IVecI extends IVecOp, ITransformable{
    //public static final IVec xaxis = new IVec(1,0,0);
    //public static final IVec yaxis = new IVec(0,1,0);
    //public static final IVec zaxis = new IVec(0,0,1);
    
    public double x();
    public double y();
    public double z();
    public IVec get();
    
    public IVecI dup();
    
    public IVec2I to2d();
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
    
    /**
       scale add
    */
    public IVecI add(IVecI v, double f);
    public IVecI add(IVecI v, IDoubleI f); 
    
    
    //public IDoubleI dot(IVecI v);
    public double dot(IVecI v);
    //public IDoubleI dotR(IVecI v);
    public double dot(ISwitchE e, IVecI v);
    public IDoubleI dot(ISwitchR r, IVecI v);
    
    /** cross product creates a new instance 
    */
    public IVecI cross(IVecI v);
    
    //public IDoubleI len();
    //public IDoubleI len2();
    public double len();
    //public IDoubleI lenR();
    public double len(ISwitchE e);
    public IDoubleI len(ISwitchR r);
    /**
       square of length
    */
    public double len2();
    //public IDoubleI len2R();
    public double len2(ISwitchE e);
    public IDoubleI len2(ISwitchR r);
    
    
    //public IVecI setLen(IDoubleI l); // -> len(double l)
    //public IVecI setLen(double l); // -> len(double l);
    
    public IVecI len(IDoubleI l);
    public IVecI len(double l);
    
    //public IVecI normalize();
    public IVecI unit();
    
    //public IDoubleI dist(IVecI v);
    public double dist(IVecI v);
    //public IDoubleI distR(IVecI v);
    public double dist(ISwitchE e, IVecI v);
    public IDoubleI dist(ISwitchR r, IVecI v);
    /**
       squared distance 
    */
    //public IDoubleI dist2(IVecI v);
    public double dist2(IVecI v);
    //public IDoubleI dist2R(IVecI v);
    public double dist2(ISwitchE e, IVecI v);
    public IDoubleI dist2(ISwitchR r, IVecI v);
    
    /**
       whether location is same or not
    */
    //public IBoolI equals(IVecI v);
    public boolean eq(IVecI v);
    //public IBoolI eqR(IVecI v);
    public boolean eq(ISwitchE e, IVecI v);
    public IBoolI eq(ISwitchR r, IVecI v);
    
    public boolean eq(IVecI v, double resolution);
    //public IBoolI eqR(IVecI v, IDoubleI resolution);
    public boolean eq(ISwitchE e, IVecI v, double resolution);
    public IBoolI eq(ISwitchR r, IVecI v, IDoubleI resolution);
    
    public boolean eqX(IVecI v);
    public boolean eqY(IVecI v);
    public boolean eqZ(IVecI v);
    //public IBoolI eqXR(IVecI v);
    //public IBoolI eqYR(IVecI v);
    //public IBoolI eqZR(IVecI v);
    public boolean eqX(ISwitchE e, IVecI v);
    public boolean eqY(ISwitchE e, IVecI v);
    public boolean eqZ(ISwitchE e, IVecI v);
    public IBoolI eqX(ISwitchR r, IVecI v);
    public IBoolI eqY(ISwitchR r, IVecI v);
    public IBoolI eqZ(ISwitchR r, IVecI v);
    
    
    public boolean eqX(IVecI v, double resolution);
    public boolean eqY(IVecI v, double resolution);
    public boolean eqZ(IVecI v, double resolution);
    //public IBoolI eqXR(IVecI v, IDoubleI resolution);
    //public IBoolI eqYR(IVecI v, IDoubleI resolution);
    //public IBoolI eqZR(IVecI v, IDoubleI resolution);
    public boolean eqX(ISwitchE e, IVecI v, double resolution);
    public boolean eqY(ISwitchE e, IVecI v, double resolution);
    public boolean eqZ(ISwitchE e, IVecI v, double resolution);
    public IBoolI eqX(ISwitchR r, IVecI v, IDoubleI resolution);
    public IBoolI eqY(ISwitchR r, IVecI v, IDoubleI resolution);
    public IBoolI eqZ(ISwitchR r, IVecI v, IDoubleI resolution);
    
    
    /**
       @return angle between two vector from 0 to Pi
    */
    //public IDoubleI angle(IVecI v);
    public double angle(IVecI v);
    //public IDoubleI angleR(IVecI v);
    public double angle(ISwitchE e, IVecI v);
    public IDoubleI angle(ISwitchR r, IVecI v);
    
    /**
       @param axis axis to determin sign of angle following right-handed screw rule.
       @return angle between two vector from -Pi to Pi. Sign follows right-handed screw rule along axis
    */
    //public IDoubleI angle(IVecI v, IVecI axis);
    public double angle(IVecI v, IVecI axis);
    //public IDoubleI angleR(IVecI v, IVecI axis);
    public double angle(ISwitchE e, IVecI v, IVecI axis);
    public IDoubleI angle(ISwitchR r, IVecI v, IVecI axis);
    
    
    public IVecI rot(IVecI axis, IDoubleI angle);
    public IVecI rot(IVecI axis, double angle);
    
    public IVecI rot(IVecI center, IVecI axis, IDoubleI angle);
    public IVecI rot(IVecI center, IVecI axis, double angle);
    
    /** rotate to destination direction vector */
    public IVecI rot(IVecI axis, IVecI destDir);
    /** rotate to destination point location */
    public IVecI rot(IVecI center, IVecI axis, IVecI destPt);
    
    
    /** alias of mul */
    public IVecI scale(IDoubleI f);
    public IVecI scale(double f);
    public IVecI scale(IVecI center, IDoubleI f);
    public IVecI scale(IVecI center, double f);
    
    
    /** scale only in 1 direction */
    public IVecI scale1d(IVecI axis, double f);
    public IVecI scale1d(IVecI axis, IDoubleI f);
    public IVecI scale1d(IVecI center, IVecI axis, double f);
    public IVecI scale1d(IVecI center, IVecI axis, IDoubleI f);
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IVecI ref(IVecI planeDir);
    public IVecI ref(IVecI center, IVecI planeDir);
    public IVecI mirror(IVecI planeDir);
    public IVecI mirror(IVecI center, IVecI planeDir);
    
    
    /** shear operation */
    public IVecI shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz);
    public IVecI shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    public IVecI shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz);
    public IVecI shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    
    public IVecI shearXY(double sxy, double syx);
    public IVecI shearXY(IDoubleI sxy, IDoubleI syx);
    public IVecI shearXY(IVecI center, double sxy, double syx);
    public IVecI shearXY(IVecI center, IDoubleI sxy, IDoubleI syx);
    
    public IVecI shearYZ(double syz, double szy);
    public IVecI shearYZ(IDoubleI syz, IDoubleI szy);
    public IVecI shearYZ(IVecI center, double syz, double szy);
    public IVecI shearYZ(IVecI center, IDoubleI syz, IDoubleI szy);
    
    public IVecI shearZX(double szx, double sxz);
    public IVecI shearZX(IDoubleI szx, IDoubleI sxz);
    public IVecI shearZX(IVecI center, double szx, double sxz);
    public IVecI shearZX(IVecI center, IDoubleI szx, IDoubleI sxz);
    
    
    /** translate is alias of add() */
    public IVecI translate(double x, double y, double z);
    public IVecI translate(IDoubleI x, IDoubleI y, IDoubleI z);
    public IVecI translate(IVecI v);
    
    
    public IVecI transform(IMatrix3I mat);
    public IVecI transform(IMatrix4I mat);
    public IVecI transform(IVecI xvec, IVecI yvec, IVecI zvec);
    public IVecI transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate);
    
    
    /** mv() is alias of add() */
    public IVecI mv(double x, double y, double z);
    public IVecI mv(IDoubleI x, IDoubleI y, IDoubleI z);
    public IVecI mv(IVecI v);
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IVecI cp();
    
    /** cp() is alias of dup().add() */
    public IVecI cp(double x, double y, double z);
    public IVecI cp(IDoubleI x, IDoubleI y, IDoubleI z);
    public IVecI cp(IVecI v);
    
        
    // methods creating new instance
    public IVecI diff(IVecI v);
    public IVecI mid(IVecI v);
    public IVecI sum(IVecI v);
    public IVecI sum(IVecI... v);
    
    public IVecI bisect(IVecI v);
    
    /**
       weighted sum
    */
    public IVecI sum(IVecI v2, double w1, double w2);
    public IVecI sum(IVecI v2, double w2);
    
    public IVecI sum(IVecI v2, IDoubleI w1, IDoubleI w2);
    public IVecI sum(IVecI v2, IDoubleI w2);
    
    
    /**
       alias of cross
    */
    public IVecI nml(IVecI v);
    /**
       create normal vector from 3 points of self, pt1 and pt2
    */
    public IVecI nml(IVecI pt1, IVecI pt2);
    
    
    public boolean isValid();
}
