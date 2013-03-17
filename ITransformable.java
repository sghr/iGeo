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
   Abstract interface of transformable geometry.
   
   @author Satoru Sugihara
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
    /** alias of neg */
    //public ITransformable rev(); // rev is used in curve to revrse u parameter
    /** alias of neg */
    public ITransformable flip();
    
    
    
    /** scale add */
    public ITransformable add(IVecI v, double f);
    /** scale add */
    public ITransformable add(IVecI v, IDoubleI f); 
    /** scale add alias */
    public ITransformable add(double f, IVecI v);
    /** scale add alias */
    public ITransformable add(IDoubleI f, IVecI v); 
    
    /** rotation around z-axis and origin */
    public ITransformable rot(IDoubleI angle);
    public ITransformable rot(double angle);
    
    /** rotation around axis vector */
    public ITransformable rot(IVecI axis, IDoubleI angle);
    public ITransformable rot(IVecI axis, double angle);
    
    /** rotation around axis vector and center */
    public ITransformable rot(IVecI center, IVecI axis, IDoubleI angle);
    public ITransformable rot(IVecI center, IVecI axis, double angle);
    
    /** rotate to destination direction vector */
    public ITransformable rot(IVecI axis, IVecI destDir);
    /** rotate to destination point location */    
    public ITransformable rot(IVecI center, IVecI axis, IVecI destPt);
    
    
    /** rotation on xy-plane around origin; same with rot(IDoubleI) */
    public ITransformable rot2(IDoubleI angle);
    /** rotation on xy-plane around origin; same with rot(double) */
    public ITransformable rot2(double angle);
    
    /** rotation on xy-plane around center */
    public ITransformable rot2(IVecI center, IDoubleI angle);
    public ITransformable rot2(IVecI center, double angle);
    
    /** rotation on xy-plane to destination direction vector */
    public ITransformable rot2(IVecI destDir);
    /** rotation on xy-plane to destination point location */    
    public ITransformable rot2(IVecI center, IVecI destPt);
    
    
    
    /** alias of mul */
    public ITransformable scale(IDoubleI f);
    public ITransformable scale(double f);
    public ITransformable scale(IVecI center, IDoubleI f);
    public ITransformable scale(IVecI center, double f);

    
    /** scale only in 1 direction */
    public ITransformable scale1d(IVecI axis, double f);
    public ITransformable scale1d(IVecI axis, IDoubleI f);
    public ITransformable scale1d(IVecI center, IVecI axis, double f);
    public ITransformable scale1d(IVecI center, IVecI axis, IDoubleI f);
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public ITransformable ref(IVecI planeDir);
    public ITransformable ref(IVecI center, IVecI planeDir);
    /** mirror is alias of ref */
    public ITransformable mirror(IVecI planeDir);
    public ITransformable mirror(IVecI center, IVecI planeDir);
    
    
    /** shear operation */
    public ITransformable shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz);
    public ITransformable shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    public ITransformable shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz);
    public ITransformable shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    
    public ITransformable shearXY(double sxy, double syx);
    public ITransformable shearXY(IDoubleI sxy, IDoubleI syx);
    public ITransformable shearXY(IVecI center, double sxy, double syx);
    public ITransformable shearXY(IVecI center, IDoubleI sxy, IDoubleI syx);
    
    public ITransformable shearYZ(double syz, double szy);
    public ITransformable shearYZ(IDoubleI syz, IDoubleI szy);
    public ITransformable shearYZ(IVecI center, double syz, double szy);
    public ITransformable shearYZ(IVecI center, IDoubleI syz, IDoubleI szy);
    
    public ITransformable shearZX(double szx, double sxz);
    public ITransformable shearZX(IDoubleI szx, IDoubleI sxz);
    public ITransformable shearZX(IVecI center, double szx, double sxz);
    public ITransformable shearZX(IVecI center, IDoubleI szx, IDoubleI sxz);
    
    /** mv() is alias of add() */
    public ITransformable mv(double x, double y, double z);
    public ITransformable mv(IDoubleI x, IDoubleI y, IDoubleI z);
    public ITransformable mv(IVecI v);
    
    
    /** duplicate the instance */
    public ITransformable dup();
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public ITransformable cp();
    
    /** cp() is alias of dup().add() */
    public ITransformable cp(double x, double y, double z);
    public ITransformable cp(IDoubleI x, IDoubleI y, IDoubleI z);
    public ITransformable cp(IVecI v);
    
    
    /** translate() is alias of add() */
    public ITransformable translate(double x, double y, double z);
    public ITransformable translate(IDoubleI x, IDoubleI y, IDoubleI z);
    public ITransformable translate(IVecI v);
    
    
    public ITransformable transform(IMatrix3I mat);
    public ITransformable transform(IMatrix4I mat);
    public ITransformable transform(IVecI xvec, IVecI yvec, IVecI zvec);
    public ITransformable transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate);
    
    
}
