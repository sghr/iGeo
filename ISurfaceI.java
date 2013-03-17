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
   Abstract interface of NURBS surface.
   
   @author Satoru Sugihara
*/
public interface ISurfaceI extends ISurfaceOp, ITransformable{
    
    /**
       @return fixed value but in wrapper class
    */
    public ISurfaceGeo get();
    
    /**
       @return duplicated instance
    */
    public ISurfaceI dup();
    
    
    public boolean isValid();
    
    
    public IVecI pt(double u, double v);
    public IVecI pt(IDoubleI u, IDoubleI v);
    public IVecI pt(IVec2I uv);
    
    public IVecI pt(double u, double v, double n); // n is length in normal dir
    public IVecI pt(IDoubleI u, IDoubleI v, IDoubleI n);
    public IVecI pt(IVecI uvn);
    
    
    public IVecI utan(double u, double v);
    public IVecI utan(IDoubleI u, IDoubleI v);
    public IVecI utan(IVec2I uv);
    
    public IVecI vtan(double u, double v);
    public IVecI vtan(IDoubleI u, IDoubleI v);
    public IVecI vtan(IVec2I uv);
    
    public IVecI normal(double u, double v);
    public IVecI normal(IDoubleI u, IDoubleI v);
    public IVecI normal(IVec2I uv);
    
    public IVecI nrml(double u, double v);
    public IVecI nrml(IDoubleI u, IDoubleI v);
    public IVecI nrml(IVec2I uv);
    
    /** alias of normal */
    public IVecI nml(double u, double v);
    public IVecI nml(IDoubleI u, IDoubleI v);
    public IVecI nml(IVec2I uv);
    
    
    
    public IVecI cp(int i, int j);
    public IVecI cp(IIntegerI i, IIntegerI j);
    
    public IVecI[][] cps();
    
    public IVecI corner(int u, int v);
    public IVecI corner(IIntegerI u, IIntegerI v);
    
    public IVecI cornerCP(int u, int v);
    public IVecI cornerCP(IIntegerI u, IIntegerI v);
    
    public IVecI ep(int i, int j);
    public IVecI ep(IIntegerI i, IIntegerI j);
    
    /** mid in UV parameter (u=0.5, v=0.5) point on a surface */
    public IVecI mid();
    
    /** returns center of geometry object */
    public IVecI center();
    
    
    
    
    /** approximate invert projection from 3D location to interanl UV parameter (closest point on surface) */
    public IVec2I uv(IVecI pt);
    
    /** approximate invert projection from 2D location to interanl UV parameter (closest point on surface) */
    public IVec2I uv(IVec2I pt);
    
    /** find approximately closest point on a surface */
    public IVecI closePt(IVecI pt);
    
    /** find approximately closest point on a surface on 2D */
    public IVecI closePt(IVec2I pt);
    
    /** distance to the closest point on a surface */
    public double dist(IVecI pt);
    /** distance to the closest point on a surface on 2D*/
    public double dist(IVec2I pt);

    
    
    public /*IDoubleI*/ double uknot(int i);
    public IDoubleI uknot(IIntegerI i);
    public /*IDoubleI*/ double vknot(int i);
    public IDoubleI vknot(IIntegerI i);
    
    public double[] uknots();
    public double[] uknots(ISwitchE e);
    public IDoubleI[] uknots(ISwitchR r);
    
    public double[] vknots();
    public double[] vknots(ISwitchE e);
    public IDoubleI[] vknots(ISwitchR r);
    
    public int uknotNum();
    public int vknotNum();
    //public IIntegerI uknotNumR();
    //public IIntegerI vknotNumR();
    public int uknotNum(ISwitchE e);
    public int vknotNum(ISwitchE e);
    public IIntegerI uknotNum(ISwitchR r);
    public IIntegerI vknotNum(ISwitchR r);
    
    /**
       @return returns true if any of control point has non-default(1.0) weight otherwise false.
    */
    public boolean isRational();
    public boolean isRational(ISwitchE e);
    public IBoolI isRational(ISwitchR r);
    
    public int udeg();
    public int vdeg();
    //public IIntegerI udegR();
    //public IIntegerI vdegR();
    public int udeg(ISwitchE e);
    public int vdeg(ISwitchE e);
    public IIntegerI udeg(ISwitchR r);
    public IIntegerI vdeg(ISwitchR r);
    
    public int unum();
    public int vnum();
    //public IIntegerI unumR();
    //public IIntegerI vnumR();
    public int unum(ISwitchE e);
    public int vnum(ISwitchE e);
    public IIntegerI unum(ISwitchR r);
    public IIntegerI vnum(ISwitchR r);
    
    //public IDouble len();
    
    public int ucpNum(); // equals to unum()
    public int vcpNum(); // equals to unum()
    //public IIntegerI ucpNumR(); // equals to unum()
    //public IIntegerI vcpNumR(); // equals to unum()
    public int ucpNum(ISwitchE e); // equals to unum()
    public int vcpNum(ISwitchE e); // equals to unum()
    public IIntegerI ucpNum(ISwitchR r); // equals to unum()
    public IIntegerI vcpNum(ISwitchR r); // equals to unum()
    
    public int uepNum();
    public int vepNum();
    //public IIntegerI uepNumR();
    //public IIntegerI vepNumR();
    public int uepNum(ISwitchE e);
    public int vepNum(ISwitchE e);
    public IIntegerI uepNum(ISwitchR r);
    public IIntegerI vepNum(ISwitchR r);
    
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double u(int epIdx, double epFraction);
    public IDoubleI u(IIntegerI epIdx, IDoubleI epFraction);
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double v(int epIdx, double epFraction);
    public IDoubleI v(IIntegerI epIdx, IDoubleI epFraction);
    
    /**
       original knots start value and end value
    */
    public double ustart();
    public double uend();
    public double vstart();
    public double vend();
    //public IDoubleI ustartR();
    //public IDoubleI uendR();
    //public IDoubleI vstartR();
    //public IDoubleI vendR();
    public double ustart(ISwitchE e);
    public double uend(ISwitchE e);
    public double vstart(ISwitchE e);
    public double vend(ISwitchE e);
    public IDoubleI ustart(ISwitchR r);
    public IDoubleI uend(ISwitchR r);
    public IDoubleI vstart(ISwitchR r);
    public IDoubleI vend(ISwitchR r);
    
    /** reverse U parameter internally without creating a new instance */
    public ISurfaceI revU();
    /** reverse V parameter internally without creating a new instance */
    public ISurfaceI revV();
    /** reverse both U and V parameter internally without creating a new instance */
    public ISurfaceI revUV();
    /** reverse normal direction by reversing V direction (UV and normal is dependent */
    public ISurfaceI revN();
    
    /** alias of revU() */
    public ISurfaceI flipU();
    /** alias of revV() */
    public ISurfaceI flipV();
    /** alias of revUV() */
    public ISurfaceI flipUV();
    /** alias of flipN() */
    public ISurfaceI flipN();
    
    /** swap U and V parameter */
    public ISurfaceI swapUV();
    
    
    
    
    // trim
    public boolean hasTrim();
    //public IBoolI hasTrimR();
    public boolean hasTrim(ISwitchE e);
    public IBoolI hasTrim(ISwitchR r);
    
    public boolean hasInnerTrim();
    //public IBoolI hasInnerTrimR();
    public boolean hasInnerTrim(ISwitchE e);
    public IBoolI hasInnerTrim(ISwitchR r);
    
    public boolean hasOuterTrim();
    //public IBoolI hasOuterTrimR();
    public boolean hasOuterTrim(ISwitchE e);
    public IBoolI hasOuterTrim(ISwitchR r);
    
    public int innerTrimLoopNum();
    //public IIntegerI innerTrimLoopNumR();
    public int innerTrimLoopNum(ISwitchE e);
    public IIntegerI innerTrimLoopNum(ISwitchR r);
    
    public int innerTrimNum(int i);
    public IIntegerI innerTrimNum(IIntegerI i);
    public ITrimCurveI[] innerTrimLoop(int i);
    public ITrimCurveI[] innerTrimLoop(IIntegerI i);
    public ITrimCurveI innerTrim(int i, int j);
    public ITrimCurveI innerTrim(IIntegerI i, IIntegerI j);
    
    public int outerTrimLoopNum();
    //public IIntegerI outerTrimLoopNumR();
    public int outerTrimLoopNum(ISwitchE e);
    public IIntegerI outerTrimLoopNum(ISwitchR r);
    
    public int outerTrimNum(int i);
    public IIntegerI outerTrimNum(IIntegerI i);
    public ITrimCurveI[] outerTrimLoop(int i);
    public ITrimCurveI[] outerTrimLoop(IIntegerI i);
    public ITrimCurveI outerTrim(int i, int j);
    public ITrimCurveI outerTrim(IIntegerI i, IIntegerI j);

    
    /**
       @param trim trim curve needs to be closed
    */
    public ISurfaceI addInnerTrimLoop(ITrimCurveI trim);
    public ISurfaceI addOuterTrimLoop(ITrimCurveI trim);
    
    /** input trim curves is deleted as default if it inherits IObject like ICurve or ICurveR
    */
    public ISurfaceI addInnerTrimLoop(ICurveI trim);
    public ISurfaceI addOuterTrimLoop(ICurveI trim);
    
    public ISurfaceI addInnerTrimLoop(ICurveI trim, boolean deleteInput);
    public ISurfaceI addOuterTrimLoop(ICurveI trim, boolean deleteInput);
    
    /**
       @param trim array of trim curves need to form closed loop
    */
    public ISurfaceI addInnerTrimLoop(ITrimCurveI[] trim);
    public ISurfaceI addOuterTrimLoop(ITrimCurveI[] trim);


    /** input trim curves is deleted as default if it inherits IObject like ICurve or ICurveR
    */
    public ISurfaceI addInnerTrimLoop(ICurveI[] trim);
    public ISurfaceI addOuterTrimLoop(ICurveI[] trim);
    
    public ISurfaceI addInnerTrimLoop(ICurveI[] trim, boolean deleteInput);
    public ISurfaceI addOuterTrimLoop(ICurveI[] trim, boolean deleteInput);
    
    
    public ISurfaceI clearInnerTrim();
    public ISurfaceI clearOuterTrim();
    public ISurfaceI clearTrim();
    
    
    /**
       check if it has default rectangular outer trim
    */
    public boolean hasDefaultTrim();
    //public IBoolI hasDefaultTrimR();
    public boolean hasDefaultTrim(ISwitchE e);
    public IBoolI hasDefaultTrim(ISwitchR r);
    
    public boolean isFlat();
    //public IBoolI isFlatR();
    public boolean isFlat(ISwitchE e);
    public IBoolI isFlat(ISwitchR r);
    
    
    public boolean isUClosed();
    public boolean isUClosed(ISwitchE e);
    public IBoolI isUClosed(ISwitchR r);
    
    public boolean isVClosed();
    public boolean isVClosed(ISwitchE e);
    public IBoolI isVClosed(ISwitchR r);
    
    
    //public boolean isInsideTrim(double u, double v);
    //public boolean isInsideTrim(IVec2 v);
    //public boolean isInsideTrim(IVec2I v);
    
    
    
    /**************************************************************************************
     * transformation methods; API of ITransformable interface
     *************************************************************************************/
    
    public ISurfaceI add(double x, double y, double z);
    public ISurfaceI add(IDoubleI x, IDoubleI y, IDoubleI z);
    public ISurfaceI add(IVecI v);
    public ISurfaceI sub(double x, double y, double z);
    public ISurfaceI sub(IDoubleI x, IDoubleI y, IDoubleI z);
    public ISurfaceI sub(IVecI v);
    public ISurfaceI mul(IDoubleI v);
    public ISurfaceI mul(double v);
    public ISurfaceI div(IDoubleI v);
    public ISurfaceI div(double v);
    
    public ISurfaceI neg();
    /** same with neg */
    //public ISurfaceI rev(); // rev is used in curve to revrse u parameter
    
    public ISurfaceI flip();
    
    
    
    /** scale add */
    public ISurfaceI add(IVecI v, double f);
    public ISurfaceI add(IVecI v, IDoubleI f); 
    
    public ISurfaceI rot(IVecI axis, IDoubleI angle);
    public ISurfaceI rot(IVecI axis, double angle);
    
    public ISurfaceI rot(IVecI center, IVecI axis, IDoubleI angle);
    public ISurfaceI rot(IVecI center, IVecI axis, double angle);
    
    /** rotate to destination direction vector */
    public ISurfaceI rot(IVecI axis, IVecI destDir);
    /** rotate to destination point location */    
    public ISurfaceI rot(IVecI center, IVecI axis, IVecI destPt);
    
    
    /** alias of mul */
    public ISurfaceI scale(IDoubleI f);
    public ISurfaceI scale(double f);
    public ISurfaceI scale(IVecI center, IDoubleI f);
    public ISurfaceI scale(IVecI center, double f);
    
    /** scale only in 1 direction */
    public ISurfaceI scale1d(IVecI axis, double f);
    public ISurfaceI scale1d(IVecI axis, IDoubleI f);
    public ISurfaceI scale1d(IVecI center, IVecI axis, double f);
    public ISurfaceI scale1d(IVecI center, IVecI axis, IDoubleI f);
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public ISurfaceI ref(IVecI planeDir);
    public ISurfaceI ref(IVecI center, IVecI planeDir);
    /** mirror is alias of ref */
    public ISurfaceI mirror(IVecI planeDir);
    public ISurfaceI mirror(IVecI center, IVecI planeDir);
    
    
    /** shear operation */
    public ISurfaceI shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz);
    public ISurfaceI shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    public ISurfaceI shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz);
    public ISurfaceI shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    
    public ISurfaceI shearXY(double sxy, double syx);
    public ISurfaceI shearXY(IDoubleI sxy, IDoubleI syx);
    public ISurfaceI shearXY(IVecI center, double sxy, double syx);
    public ISurfaceI shearXY(IVecI center, IDoubleI sxy, IDoubleI syx);
    
    public ISurfaceI shearYZ(double syz, double szy);
    public ISurfaceI shearYZ(IDoubleI syz, IDoubleI szy);
    public ISurfaceI shearYZ(IVecI center, double syz, double szy);
    public ISurfaceI shearYZ(IVecI center, IDoubleI syz, IDoubleI szy);
    
    public ISurfaceI shearZX(double szx, double sxz);
    public ISurfaceI shearZX(IDoubleI szx, IDoubleI sxz);
    public ISurfaceI shearZX(IVecI center, double szx, double sxz);
    public ISurfaceI shearZX(IVecI center, IDoubleI szx, IDoubleI sxz);
    
    /** mv() is alias of add() */
    public ISurfaceI mv(double x, double y, double z);
    public ISurfaceI mv(IDoubleI x, IDoubleI y, IDoubleI z);
    public ISurfaceI mv(IVecI v);
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public ISurfaceI cp();
    
    /** cp() is alias of dup().add() */
    public ISurfaceI cp(double x, double y, double z);
    public ISurfaceI cp(IDoubleI x, IDoubleI y, IDoubleI z);
    public ISurfaceI cp(IVecI v);
    
    
    /** translate() is alias of add() */
    public ISurfaceI translate(double x, double y, double z);
    public ISurfaceI translate(IDoubleI x, IDoubleI y, IDoubleI z);
    public ISurfaceI translate(IVecI v);
    
    
    public ISurfaceI transform(IMatrix3I mat);
    public ISurfaceI transform(IMatrix4I mat);
    public ISurfaceI transform(IVecI xvec, IVecI yvec, IVecI zvec);
    public ISurfaceI transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate);
    
    
}
