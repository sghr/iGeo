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
   Abstract interface of NURBS surface.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public interface ISurfaceI extends ISurfaceOp{
    
    /**
       @return fixed value but in wrapper class
    */
    public ISurfaceGeo get();
    
    /**
       @return duplicated instance
    */
    public ISurfaceI dup();
    
    
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
    
    
    
    public IVecI cp(int i, int j);
    public IVecI cp(IIntegerI i, IIntegerI j);
    
    public IVecI corner(int u, int v);
    public IVecI corner(IIntegerI u, IIntegerI v);
    
    public IVecI cornerCP(int u, int v);
    public IVecI cornerCP(IIntegerI u, IIntegerI v);
    
    public IVecI ep(int i, int j);
    public IVecI ep(IIntegerI i, IIntegerI j);
    
    public /*IDoubleI*/ double uknot(int i);
    public IDoubleI uknot(IIntegerI i);
    public /*IDoubleI*/ double vknot(int i);
    public IDoubleI vknot(IIntegerI i);
    
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
    
    
    //public boolean isUClosed();
    //public boolean isVClosed();
    
}
