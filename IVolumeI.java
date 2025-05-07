/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2014 Satoru Sugihara

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
public interface IVolumeI extends IVolumeOp, ITransformable{

    /**
       @return fixed value but in wrapper class
    */
    public IVolumeGeo get();

    /**
       @return duplicated instance
    */
    public IVolumeI dup();


    public boolean isValid();


    public IVecI pt(double u, double v, double w);
    public IVecI pt(IDoubleI u, IDoubleI v, IDoubleI w);
    public IVecI pt(IVecI uvw);

    public IVecI utan(double u, double v, double w);
    public IVecI utan(IDoubleI u, IDoubleI v, IDoubleI w);
    public IVecI utan(IVecI uvw);

    public IVecI vtan(double u, double v, double w);
    public IVecI vtan(IDoubleI u, IDoubleI v, IDoubleI w);
    public IVecI vtan(IVecI uvw);

    public IVecI wtan(double u, double v, double w);
    public IVecI wtan(IDoubleI u, IDoubleI v, IDoubleI w);
    public IVecI wtan(IVecI uvw);


    public IVecI cp(int i, int j, int k); // control point
    public IVecI cp(IIntegerI i, IIntegerI j, IIntegerI k);

    public IVecI[][][] cps();

    public IVecI corner(int u, int v, int w);
    public IVecI corner(IIntegerI u, IIntegerI v, IIntegerI w);

    public IVecI cornerCP(int u, int v, int w);
    public IVecI cornerCP(IIntegerI u, IIntegerI v, IIntegerI w);

    public IVecI ep(int i, int j, int k);
    public IVecI ep(IIntegerI i, IIntegerI j, IIntegerI k);

    /** mid in UV parameter (u=0.5, v=0.5, w=0.5) point on a surface */
    public IVecI mid();

    /** returns center of geometry object */
    public IVecI center();




    /** approximate invert projection from 3D location to interanl UVW parameter (closest point on the volume) */
    //public IVecI uvw(IVecI pt);

    /** find approximately closest point in the volume */
    //public IVecI closePt(IVecI pt);

    /** distance to the closest point in the volume */
    //public double dist(IVecI pt);



    public /*IDoubleI*/ double uknot(int i);
    public IDoubleI uknot(IIntegerI i);
    public /*IDoubleI*/ double vknot(int i);
    public IDoubleI vknot(IIntegerI i);
    public /*IDoubleI*/ double wknot(int i);
    public IDoubleI wknot(IIntegerI i);

    public double[] uknots();
    public double[] uknots(ISwitchE e);
    public IDoubleI[] uknots(ISwitchR r);

    public double[] vknots();
    public double[] vknots(ISwitchE e);
    public IDoubleI[] vknots(ISwitchR r);

    public double[] wknots();
    public double[] wknots(ISwitchE e);
    public IDoubleI[] wknots(ISwitchR r);

    public int uknotNum();
    public int vknotNum();
    public int wknotNum();

    public int uknotNum(ISwitchE e);
    public int vknotNum(ISwitchE e);
    public int wknotNum(ISwitchE e);
    public IIntegerI uknotNum(ISwitchR r);
    public IIntegerI vknotNum(ISwitchR r);
    public IIntegerI wknotNum(ISwitchR r);

    /**
       @return returns true if any of control point has non-default(1.0) weight otherwise false.
    */
    public boolean isRational();
    public boolean isRational(ISwitchE e);
    public IBoolI isRational(ISwitchR r);

    public int udeg();
    public int vdeg();
    public int wdeg();
    //public IIntegerI udegR();
    //public IIntegerI vdegR();
    //public IIntegerI wdegR();
    public int udeg(ISwitchE e);
    public int vdeg(ISwitchE e);
    public int wdeg(ISwitchE e);
    public IIntegerI udeg(ISwitchR r);
    public IIntegerI vdeg(ISwitchR r);
    public IIntegerI wdeg(ISwitchR r);

    public int unum();
    public int vnum();
    public int wnum();

    public int unum(ISwitchE e);
    public int vnum(ISwitchE e);
    public int wnum(ISwitchE e);

    public IIntegerI unum(ISwitchR r);
    public IIntegerI vnum(ISwitchR r);
    public IIntegerI wnum(ISwitchR r);

    public int ucpNum(); // equals to unum()
    public int vcpNum(); // equals to vnum()
    public int wcpNum(); // equals to wnum()

    public int ucpNum(ISwitchE e); // equals to unum()
    public int vcpNum(ISwitchE e); // equals to vnum()
    public int wcpNum(ISwitchE e); // equals to wnum()

    public IIntegerI ucpNum(ISwitchR r); // equals to unum()
    public IIntegerI vcpNum(ISwitchR r); // equals to vnum()
    public IIntegerI wcpNum(ISwitchR r); // equals to wnum()

    public int uepNum();
    public int vepNum();
    public int wepNum();

    public int uepNum(ISwitchE e);
    public int vepNum(ISwitchE e);
    public int wepNum(ISwitchE e);

    public IIntegerI uepNum(ISwitchR r);
    public IIntegerI vepNum(ISwitchR r);
    public IIntegerI wepNum(ISwitchR r);


    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double u(int epIdx, double epFraction);
    public IDoubleI u(IIntegerI epIdx, IDoubleI epFraction);

    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double v(int epIdx, double epFraction);
    public IDoubleI v(IIntegerI epIdx, IDoubleI epFraction);

    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double w(int epIdx, double epFraction);
    public IDoubleI w(IIntegerI epIdx, IDoubleI epFraction);

    /**
       original knots start value and end value
    */
    public double ustart();
    public double uend();
    public double vstart();
    public double vend();
    public double wstart();
    public double wend();

    public double ustart(ISwitchE e);
    public double uend(ISwitchE e);
    public double vstart(ISwitchE e);
    public double vend(ISwitchE e);
    public double wstart(ISwitchE e);
    public double wend(ISwitchE e);

    public IDoubleI ustart(ISwitchR r);
    public IDoubleI uend(ISwitchR r);
    public IDoubleI vstart(ISwitchR r);
    public IDoubleI vend(ISwitchR r);
    public IDoubleI wstart(ISwitchR r);
    public IDoubleI wend(ISwitchR r);

    /** reverse U parameter internally without creating a new instance */
    //public IVolumeI revU();
    /** reverse V parameter internally without creating a new instance */
    //public IVolumeI revV();
    /** reverse W parameter internally without creating a new instance */
    //public IVolumeI revW();
    /** reverse both U and V parameter internally without creating a new instance */
    //public IVolumeI revUVW();

    /** alias of revU() */
    //public IVolumeI flipU();
    /** alias of revV() */
    //public IVolumeI flipV();
    /** alias of revUVW() */
    //public IVolumeI flipUVW();

    /** swap U and V parameter */
    //public IVolumeI swapUV();

    /* the system of trim surfaces is pending.
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



    //   @param trim trim curve needs to be closed
    public IVolumeI addInnerTrimLoop(ITrimCurveI trim);
    public IVolumeI addOuterTrimLoop(ITrimCurveI trim);

    // input trim curves is deleted as default if it inherits IObject like ICurve or ICurveR
    public IVolumeI addInnerTrimLoop(ICurveI trim);
    public IVolumeI addOuterTrimLoop(ICurveI trim);

    public IVolumeI addInnerTrimLoop(ICurveI trim, boolean deleteInput);
    public IVolumeI addOuterTrimLoop(ICurveI trim, boolean deleteInput);

    //   @param trim array of trim curves need to form closed loop
    public IVolumeI addInnerTrimLoop(ITrimCurveI[] trim);
    public IVolumeI addOuterTrimLoop(ITrimCurveI[] trim);


    // input trim curves is deleted as default if it inherits IObject like ICurve or ICurveR
    public IVolumeI addInnerTrimLoop(ICurveI[] trim);
    public IVolumeI addOuterTrimLoop(ICurveI[] trim);

    public IVolumeI addInnerTrimLoop(ICurveI[] trim, boolean deleteInput);
    public IVolumeI addOuterTrimLoop(ICurveI[] trim, boolean deleteInput);


    public IVolumeI clearInnerTrim();
    public IVolumeI clearOuterTrim();
    public IVolumeI clearTrim();


    //  check if it has default rectangular outer trim
    public boolean hasDefaultTrim();
    //public IBoolI hasDefaultTrimR();
    public boolean hasDefaultTrim(ISwitchE e);
    public IBoolI hasDefaultTrim(ISwitchR r);
    */

    public boolean isUClosed();
    public boolean isUClosed(ISwitchE e);
    public IBoolI isUClosed(ISwitchR r);

    public boolean isVClosed();
    public boolean isVClosed(ISwitchE e);
    public IBoolI isVClosed(ISwitchR r);

    public boolean isWClosed();
    public boolean isWClosed(ISwitchE e);
    public IBoolI isWClosed(ISwitchR r);



    /**************************************************************************************
     * transformation methods; API of ITransformable interface
     *************************************************************************************/

    public IVolumeI add(double x, double y, double z);
    public IVolumeI add(IDoubleI x, IDoubleI y, IDoubleI z);
    public IVolumeI add(IVecI v);
    public IVolumeI sub(double x, double y, double z);
    public IVolumeI sub(IDoubleI x, IDoubleI y, IDoubleI z);
    public IVolumeI sub(IVecI v);
    public IVolumeI mul(IDoubleI v);
    public IVolumeI mul(double v);
    public IVolumeI div(IDoubleI v);
    public IVolumeI div(double v);

    public IVolumeI neg();
    /** same with neg */
    //public IVolumeI rev(); // rev is used in curve to revrse u parameter

    public IVolumeI flip();



    /** scale add */
    public IVolumeI add(IVecI v, double f);
    public IVolumeI add(IVecI v, IDoubleI f);

    public IVolumeI rot(IVecI axis, IDoubleI angle);
    public IVolumeI rot(IVecI axis, double angle);

    public IVolumeI rot(IVecI center, IVecI axis, IDoubleI angle);
    public IVolumeI rot(IVecI center, IVecI axis, double angle);

    /** rotate to destination direction vector */
    public IVolumeI rot(IVecI axis, IVecI destDir);
    /** rotate to destination point location */
    public IVolumeI rot(IVecI center, IVecI axis, IVecI destPt);


    /** alias of mul */
    public IVolumeI scale(IDoubleI f);
    public IVolumeI scale(double f);
    public IVolumeI scale(IVecI center, IDoubleI f);
    public IVolumeI scale(IVecI center, double f);

    /** scale only in 1 direction */
    public IVolumeI scale1d(IVecI axis, double f);
    public IVolumeI scale1d(IVecI axis, IDoubleI f);
    public IVolumeI scale1d(IVecI center, IVecI axis, double f);
    public IVolumeI scale1d(IVecI center, IVecI axis, IDoubleI f);

    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IVolumeI ref(IVecI planeDir);
    public IVolumeI ref(IVecI center, IVecI planeDir);
    /** mirror is alias of ref */
    public IVolumeI mirror(IVecI planeDir);
    public IVolumeI mirror(IVecI center, IVecI planeDir);


    /** shear operation */
    public IVolumeI shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz);
    public IVolumeI shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    public IVolumeI shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz);
    public IVolumeI shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz);

    public IVolumeI shearXY(double sxy, double syx);
    public IVolumeI shearXY(IDoubleI sxy, IDoubleI syx);
    public IVolumeI shearXY(IVecI center, double sxy, double syx);
    public IVolumeI shearXY(IVecI center, IDoubleI sxy, IDoubleI syx);

    public IVolumeI shearYZ(double syz, double szy);
    public IVolumeI shearYZ(IDoubleI syz, IDoubleI szy);
    public IVolumeI shearYZ(IVecI center, double syz, double szy);
    public IVolumeI shearYZ(IVecI center, IDoubleI syz, IDoubleI szy);

    public IVolumeI shearZX(double szx, double sxz);
    public IVolumeI shearZX(IDoubleI szx, IDoubleI sxz);
    public IVolumeI shearZX(IVecI center, double szx, double sxz);
    public IVolumeI shearZX(IVecI center, IDoubleI szx, IDoubleI sxz);

    /** mv() is alias of add() */
    public IVolumeI mv(double x, double y, double z);
    public IVolumeI mv(IDoubleI x, IDoubleI y, IDoubleI z);
    public IVolumeI mv(IVecI v);


    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */
    public IVolumeI cp();

    /** cp() is alias of dup().add() */
    public IVolumeI cp(double x, double y, double z);
    public IVolumeI cp(IDoubleI x, IDoubleI y, IDoubleI z);
    public IVolumeI cp(IVecI v);


    /** translate() is alias of add() */
    public IVolumeI translate(double x, double y, double z);
    public IVolumeI translate(IDoubleI x, IDoubleI y, IDoubleI z);
    public IVolumeI translate(IVecI v);


    public IVolumeI transform(IMatrix3I mat);
    public IVolumeI transform(IMatrix4I mat);
    public IVolumeI transform(IVecI xvec, IVecI yvec, IVecI zvec);
    public IVolumeI transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate);


}
