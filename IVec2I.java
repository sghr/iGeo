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

package igeo;

/**
   Abstract interface of 2 dimensional vector.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public interface IVec2I extends IVec2Op{
    public double x();
    public double y();
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
    public IVec2I rev();
    
    public IVec2I add(IVec2I v, double f);
    public IVec2I add(IVec2I v, IDoubleI f);
    
    public double dot(IVec2I v);
    //public IDoubleI dotR(IVec2I v);
    public double dot(ISwitchE e, IVec2I v);
    public IDoubleI dot(ISwitchR r, IVec2I v);
    
    public IVecI cross(IVec2I v);
    
    public double len();
    //public IDoubleI lenR();
    public double len(ISwitchE e);
    public IDoubleI len(ISwitchR r);
    
    public double len2();
    //public IDoubleI len2R();
    public double len2(ISwitchE e);
    public IDoubleI len2(ISwitchR r);
    
    public IVec2I len(IDoubleI l);
    public IVec2I len(double l);
    
    public IVec2I unit();
    /**
       rotate 90 deg
    */
    public IVec2I ortho();
    
    /** distance
     */
    public double dist(IVec2I v);
    //public IDoubleI distR(IVec2I v);
    public double dist(ISwitchE e, IVec2I v);
    public IDoubleI dist(ISwitchR r, IVec2I v);
    
    /** squared distance 
    */
    public double dist2(IVec2I v);
    //public IDoubleI dist2R(IVec2I v);
    public double dist2(ISwitchE e, IVec2I v);
    public IDoubleI dist2(ISwitchR r, IVec2I v);
    
    /**
       whether location is same or not
    */
    public boolean eq(IVec2I v);
    //public IBoolI eqR(IVec2I v);
    public boolean eq(ISwitchE e, IVec2I v);
    public IBoolI eq(ISwitchR r, IVec2I v);
    
    public boolean eq(IVec2I v, double resolution);
    //public IBoolI eqR(IVec2I v, IDoubleI resolution);
    public boolean eq(ISwitchE e, IVec2I v, double resolution);
    public IBoolI eq(ISwitchR r, IVec2I v, IDoubleI resolution);
    
    public boolean eqX(IVec2I v);
    public boolean eqY(IVec2I v);
    //public IBoolI eqXR(IVec2I v);
    //public IBoolI eqYR(IVec2I v);
    public boolean eqX(ISwitchE e, IVec2I v);
    public boolean eqY(ISwitchE e, IVec2I v);
    public IBoolI eqX(ISwitchR r, IVec2I v);
    public IBoolI eqY(ISwitchR r, IVec2I v);
    
    public boolean eqX(IVec2I v, double resolution);
    public boolean eqY(IVec2I v, double resolution);
    //public IBoolI eqXR(IVec2I v, IDoubleI resolution);
    //public IBoolI eqYR(IVec2I v, IDoubleI resolution);
    public boolean eqX(ISwitchE e, IVec2I v, double resolution);
    public boolean eqY(ISwitchE e, IVec2I v, double resolution);
    public IBoolI eqX(ISwitchR r, IVec2I v, IDoubleI resolution);
    public IBoolI eqY(ISwitchR r, IVec2I v, IDoubleI resolution);
    
    
    public double angle(IVec2I v);
    //public IDoubleI angleR(IVec2I v);
    public double angle(ISwitchE e, IVec2I v);
    public IDoubleI angle(ISwitchR r, IVec2I v);
    
    public IVec2I rot(double angle);
    public IVec2I rot(IDoubleI angle);
    
    public IVec2I rot(IVec2I center, double angle);
    public IVec2I rot(IVec2I center, IDoubleI angle);
    
    public IVec2I rot(IVec2I destDir);
    public IVec2I rot(IVec2I center, IVec2I destPt);
    
    public IVec2I scale(IDoubleI f);
    public IVec2I scale(double f);
    public IVec2I scale(IVec2I center, IDoubleI f);
    public IVec2I scale(IVec2I center, double f);
    
    /**
       reflect (mirror) 2 dimensionally to the other side of the line
    */
    public IVec2I ref(IVec2I lineDir);
    public IVec2I ref(IVec2I center, IVec2I lineDir);
    public IVec2I mirror(IVec2I lineDir);
    public IVec2I mirror(IVec2I center, IVec2I lineDir);
    
    
    //public IVec2I transform(IMatrix2I mat);
    //public IVec2I transform(IMatrix3I mat);
    public IVec2I transform(IVec2I xvec, IVec2I yvec);
    public IVec2I transform(IVec2I xvec, IVec2I yvec, IVec2I translate);
    
    
    // methods creating new instance
    public IVec2I diff(IVec2I v);
    public IVec2I mid(IVec2I v);
    public IVec2I sum(IVec2I v);
    public IVec2I sum(IVec2I... v);
    
    public IVec2I bisect(IVec2I v);
    
    /**
       weighted sum
    */
    public IVec2I sum(IVec2I v2, double w1, double w2);
    public IVec2I sum(IVec2I v2, double w2);
    
    public IVec2I sum(IVec2I v2, IDoubleI w1, IDoubleI w2);
    public IVec2I sum(IVec2I v2, IDoubleI w2);
    
    
}
