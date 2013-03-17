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
   Abstract interface of numerical matrix.
   
   @author Satoru Sugihara
*/
public interface IMatrixI extends IMatrixOp{
    
    //public IMatrix get();
    
    public IMatrixI dup();
    /** alias of dup() */
    public IMatrixI cp();
    
    public int rowNum();
    public int columnNum();
    //public IIntegerI rowNumR();
    //public IIntegerI columnNumR();
    public int rowNum(ISwitchE e);
    public int columnNum(ISwitchE e);
    public IIntegerI rowNum(ISwitchR r);
    public IIntegerI columnNum(ISwitchR r);
    
    public IMatrixI setZero();
    public IMatrixI setId();
    
    public double get(int row, int column);
    //public IDoubleI getR(IIntegerI row, IIntegerI column);
    public double get(ISwitchE e, int row, int column);
    public IDoubleI get(ISwitchR r, IIntegerI row, IIntegerI column);
    
    public IMatrixI set(double[][] v);
    public IMatrixI set(IDoubleI[][] v);
    
    public IMatrix set(int row, int column, double value);
    public IMatrix set(IIntegerI row, IIntegerI column, IDoubleI value);
    
    public IMatrixI set(IMatrixI m);
    
    public IMatrixI add(IMatrixI m);
    public IMatrixI sub(IMatrixI m);
    
    public IMatrixI div(double v);
    public IMatrixI div(IDoubleI v);
    
    public IMatrixI mul(double v);
    public IMatrixI mul(IDoubleI v);
    
    public IMatrixI mul(IMatrixI m);

    
    public double determinant();
    //public IDoubleI determinantR();
    public double determinant(ISwitchE e);
    public IDoubleI determinant(ISwitchR r);
    
    public IMatrixI invert();
    
}
