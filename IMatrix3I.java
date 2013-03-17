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
   Abstract interface of 3x3 numerical matrix.
   
   @author Satoru Sugihara
*/
public interface IMatrix3I extends IMatrixI, IMatrix3Op{
    
    public IMatrix3 get();
    
    public IMatrix3I dup();
    public IMatrix3I cp();
    
    public IMatrix3I set(double v11, double v12, double v13,
			 double v21, double v22, double v23,
			 double v31, double v32, double v33);
    
    public IMatrix3I set(IDoubleI v11, IDoubleI v12, IDoubleI v13,
			 IDoubleI v21, IDoubleI v22, IDoubleI v23,
			 IDoubleI v31, IDoubleI v32, IDoubleI v33);
    
    public IMatrix3I mul(IMatrix3I m);
    
    public IVecI mul(IVecI m);
    
    public IVec2I mul(IVec2I m);
    
}
