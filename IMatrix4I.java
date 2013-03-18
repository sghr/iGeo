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
   Abstract interface of 4x4 numerical matrix.
   
   @author Satoru Sugihara
*/
public interface IMatrix4I extends IMatrixI, IMatrix4Op{
    
    public IMatrix4 get();
    
    public IMatrix4I dup();
    public IMatrix4I cp();
    
    public IMatrix4I set(double v11, double v12, double v13, double v14,
			 double v21, double v22, double v23, double v24,
			 double v31, double v32, double v33, double v34,
			 double v41, double v42, double v43, double v44);
    
    public IMatrix4I set(IDoubleI v11, IDoubleI v12, IDoubleI v13, IDoubleI v14,
			 IDoubleI v21, IDoubleI v22, IDoubleI v23, IDoubleI v24,
			 IDoubleI v31, IDoubleI v32, IDoubleI v33, IDoubleI v34,
			 IDoubleI v41, IDoubleI v42, IDoubleI v43, IDoubleI v44);
    
    public IMatrix4I mul(IMatrix4I m);
    
    public IVec4I mul(IVec4I m);
    
    public IVecI mul(IVecI m);
    /**
       same with mul(IVecI)
    */
    public IVecI transform(IVecI m);
    
    /** get matrix without translation part */
    public IMatrix3I matrix3();
    /** get only translate vector */
    public IVecI translate();
    
    
    
}
