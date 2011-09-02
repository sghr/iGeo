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
   Abstract interface of NURBS curve.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public interface ICurveI extends ICurveOp{
    
    /**
       @return fixed value but in wrapper class
    */
    public ICurveGeo get();
    
    /**
       @return duplicated instance
    */
    public ICurveI dup();
    
    
    public IVecI pt(double u);
    public IVecI pt(IDoubleI u);
    
    public IVecI tan(double u);
    public IVecI tan(IDoubleI u);
    
    public IVecI cp(int i);
    public IVecI cp(IIntegerI i);
    
    public IVecI ep(int i);
    public IVecI ep(IIntegerI i);
    
    
    public double knot(int i);
    public IDoubleI knot(IIntegerI i);
    
    public int knotNum();
    //public IIntegerI knotNumR();
    public int knotNum(ISwitchE e);
    public IIntegerI knotNum(ISwitchR r);

    /**
       @return returns true if any of control point has non-default(1.0) weight otherwise false.
    */
    public boolean isRational();
    public boolean isRational(ISwitchE e);
    public IBoolI isRational(ISwitchR r);
    
    public int deg();
    //public IIntegerI degR();
    public int deg(ISwitchE e);
    public IIntegerI deg(ISwitchR r);
    
    public int num(); // equals to cpNum
    //public IIntegerI numR(); // equals to cpNum
    public int num(ISwitchE e); // equals to cpNum
    public IIntegerI num(ISwitchR r); // equals to cpNum
    
    public int cpNum(); //
    //public IIntegerI cpNumR(); //
    public int cpNum(ISwitchE e); //
    public IIntegerI cpNum(ISwitchR r); //
    
    public IVecI start();
    public IVecI end();
    public IVecI startCP();
    public IVecI endCP();
    
    
    public int epNum();
    //public IIntegerI epNumR();
    public int epNum(ISwitchE e);
    public IIntegerI epNum(ISwitchR r);
    
    public double len();
    //public IDouble lenR();
    public double len(ISwitchE e);
    public IDouble len(ISwitchR r);
    
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double u(int epIdx, double epFraction);
    public IDoubleI u(IInteger epIdx, IDouble epFraction);
    
    public double ustart();
    public double uend();
    //public IDoubleI ustartR();
    //public IDoubleI uendR();
    public double ustart(ISwitchE e);
    public double uend(ISwitchE e);
    public IDoubleI ustart(ISwitchR r);
    public IDoubleI uend(ISwitchR r);
    
    
    public boolean isClosed();
    //public IBoolI isClosedR();
    public boolean isClosed(ISwitchE e);
    public IBoolI isClosed(ISwitchR r);
    
    /** reverse self curve ; not creating a new object
     */
    public ICurveI rev();
    
}
