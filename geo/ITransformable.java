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
   Abstract interface of transformable geometry.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public interface ITransformable{
    
    public ITransformable add(double x, double y, double z);
    public ITransformable add(IDoubleI x, IDoubleI y, IDoubleI z);
    public ITransformable add(IVecI v);
    public ITransformable sub(double x, double y, double z);
    public ITransformable sub(IDoubleI x, IDoubleI y, IDoubleI z);
    public ITransformable sub(IVecI v);
    public ITransformable mul(IDoubleI v);
    public ITransformable mul(double v);
    public ITransformable div(IDoubleI v);
    public ITransformable div(double v);
    
    public ITransformable neg();
    /** same with neg */
    public ITransformable rev();
    
    /** scale add */
    public ITransformable add(IVecI v, double f);
    public ITransformable add(IVecI v, IDoubleI f); 
    
    public ITransformable rot(IVecI axis, IDoubleI angle);
    public ITransformable rot(IVecI axis, double angle);
    
    public ITransformable rot(IVecI center, IVecI axis, IDoubleI angle);
    public ITransformable rot(IVecI center, IVecI axis, double angle);
    
    /** rotate to destination direction vector */
    public ITransformable rot(IVecI axis, IVecI destDir);
    /** rotate to destination point location */    
    public ITransformable rot(IVecI center, IVecI axis, IVecI destPt);
    
    
    /** same with mul */
    public ITransformable scale(IDoubleI f);
    public ITransformable scale(double f);
    
    public ITransformable scale(IVecI center, IDoubleI f);
    public ITransformable scale(IVecI center, double f);
    
    /**
       reflect(mirror) 3 dimensionally to the other side of the plane
    */
    public ITransformable ref(IVecI planeDir);
    public ITransformable ref(IVecI center, IVecI planeDir);
    /** mirror is alias of ref */
    public ITransformable mirror(IVecI planeDir);
    public ITransformable mirror(IVecI center, IVecI planeDir);
    
    
    /** shear operation */
    public IVec shear(double sxy, double syx, double syz,
		      double szy, double szx, double sxz);
    public IVec shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		      IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    public IVec shear(IVecI center, double sxy, double syx, double syz,
		      double szy, double szx, double sxz);
    public IVec shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		      IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    
    
    /** translate is alias of add() */
    public ITransformable translate(double x, double y, double z);
    public ITransformable translate(IDoubleI x, IDoubleI y, IDoubleI z);
    public ITransformable translate(IVecI v);
    
    
    public ITransformable transform(IMatrix3I mat);
    public ITransformable transform(IMatrix4I mat);
    public ITransformable transform(IVecI xvec, IVecI yvec, IVecI zvec);
    public ITransformable transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate);
    
    
}
