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
   Abstract interface of 2x2 numerical matrix.
   
   @author Satoru Sugihara
*/
public interface IMatrix2I extends IMatrixI, IMatrix2Op{
    
    public IMatrix2 get();
    
    public IMatrix2I dup();
    public IMatrix2I cp();
    
    public IMatrix2I set(double v11, double v12,
			 double v21, double v22);
    
    public IMatrix2I set(IDoubleI v11, IDoubleI v12,
			 IDoubleI v21, IDoubleI v22);
    
    public IMatrix2I mul(IMatrix2I m);
    
    public IVec2I mul(IVec2I m);
    
}
