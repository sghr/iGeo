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
   Class of numerical matrix in specified size .
   
   @author Satoru Sugihara
*/
public class IMatrix implements IMatrixI{
    
    public double val[][];
    public int rowNum;
    public int columnNum;
    
    public IMatrix(int rownum, int columnnum){
	rowNum = rownum;
	columnNum = columnnum;
	val = new double[rowNum][columnNum];
    }
    
    public IMatrix(IMatrix m){
	rowNum = m.rowNum;
	columnNum = m.columnNum;
	val = new double[rowNum][columnNum];
	set(m);
    }

    public IMatrix get(){ return this; }
    
    public IMatrix dup(){ return new IMatrix(this); }
    public IMatrix cp(){ return dup(); }
    
    public int rowNum(){ return rowNum; }
    public int columnNum(){ return columnNum; }
    //public IInteger rowNumR(){ return new IInteger(rowNum()); }
    //public IInteger columnNumR(){ return new IInteger(columnNum()); }
    public int rowNum(ISwitchE e){ return rowNum(); }
    public int columnNum(ISwitchE e){ return columnNum(); }
    public IInteger rowNum(ISwitchR r){ return new IInteger(rowNum()); }
    public IInteger columnNum(ISwitchR r){ return new IInteger(columnNum()); }
    
    
    public /*void*/ IMatrix setZero(){
	for(int i=0; i<rowNum; i++)
	    for(int j=0; j<columnNum; j++) val[i][j] = 0;
	return this;
    }

    public IMatrix zero(){ return setZero(); }
    
    public /*void*/ IMatrix setId(){
	for(int i=0; i<rowNum; i++)
	    for(int j=0; j<columnNum; j++)
		if(i==j) val[i][j] = 1;
		else val[i][j] = 0;
	return this;
    }
    
    public IMatrix id(){ return setId(); }
    
    
    public double get(int row, int column){ return val[row][column]; }
    //public IDouble getR(IIntegerI row, IIntegerI column){ return new IDouble(get(row.x(),column.x())); }
    public double get(ISwitchE e, int row, int column){ return get(row,column); }
    public IDouble get(ISwitchR r, IIntegerI row, IIntegerI column){
	return new IDouble(get(row.x(),column.x()));
    }
    
    
    public /*void*/ IMatrix set(double[][] v){
	for(int i=0; i<rowNum; i++)
	    for(int j=0; j<columnNum; j++) val[i][j] = v[i][j];
	return this;
    }
    
    
    public /*void*/ IMatrix set(int row, int column, double v){
	val[row][column] = v;
	return this;
    }

    public IMatrix set(IIntegerI row, IIntegerI column, IDoubleI v){
	return set(row.x(),column.x(),v.x());
    }
    
    public IMatrix set(IDoubleI[][] v){
	double[][] val = new double[v.length][v[0].length];
	return set(val);
    }
    
    
    
    public /*void*/ IMatrix set(IMatrix m){
	// row num and column num should be same
	// no check here
	for(int i=0; i<rowNum; i++)
	    for(int j=0; j<columnNum; j++) val[i][j] = m.val[i][j];
	return this;
    }
    
    public IMatrix set(IMatrixI m){ return set(m.get()); }
    
    
    public /*void*/ IMatrix setRange(IMatrix m, int rowStart, int rowLen,
			 int columnStart, int columnLen){
	// row num and column num should not exceed the boundary
	// no check here
	for(int i=0; i<rowLen; i++)
	    for(int j=0; j<columnLen; j++)
		val[i+rowStart][j+columnStart] = m.val[i+rowStart][j+columnStart];
	return this;
    }
    
    
    public /*void*/IMatrix add(IMatrix m){
	// row num and column num should be same
	// no check here
	for(int i=0; i<rowNum; i++)
	    for(int j=0; j<columnNum; j++) val[i][j] += m.val[i][j];
	return this;
    }

    public IMatrix add(IMatrixI m){ return add(m.get()); }
    
    public /*void*/IMatrix sub(IMatrix m){
	// row num and column num should be same
	// no check here
	for(int i=0; i<rowNum; i++)
	    for(int j=0; j<columnNum; j++) val[i][j] -= m.val[i][j];
	return this;
    }

    public IMatrix sub(IMatrixI m){ return sub(m.get()); }
    
    public /*void*/ IMatrix div(double v){
	for(int i=0; i<rowNum; i++)
	    for(int j=0; j<columnNum; j++) val[i][j] /= v;
	return this;
    }
    public IMatrix div(IDoubleI v){ return div(v.x()); }
    
    
    public /*void*/ IMatrix mul(double v){
	for(int i=0; i<rowNum; i++)
	    for(int j=0; j<columnNum; j++) val[i][j] *= v;
	return this;
    }
    
    public IMatrix mul(IDoubleI v){ return mul(v.x()); }
    
    
    
    /**
       currently mul returns new instance different from this with different row x column num.
       but it should change the content without generating new instance.
    */
    public IMatrix mul(IMatrix m){
	if(this.columnNum != m.rowNum){ return null; } // !
	
	IMatrix retval = new IMatrix(rowNum, m.columnNum);
	//retval.setZero();
	for(int i=0; i<rowNum; i++){
	    for(int j=0; j<m.columnNum; j++){
		retval.val[i][j] = 0;
		for(int k=0; k<columnNum; k++){
		    retval.val[i][j] += val[i][k]*m.val[k][j];
		}
	    }
	}
	return retval;
    }
    
    public IMatrix mul(IMatrixI m){ return mul(m.get()); }
    
    
    static public double det(double v11, double v12,
			     double v21, double v22){
	return v11*v22-v12*v21;
    }
    
    // defined in subclass
    //public double getDeterminant(){ return 0; }
    public double determinant(){ return 0; }
    //public IDouble determinantR(){ return new IDouble(determinant()); }
    public double determinant(ISwitchE e){ return determinant(); }
    public IDouble determinant(ISwitchR r){ return new IDouble(determinant()); }
    
    
    // defined in subclass
    public IMatrix invert(){ return null; }
    
    
    public String toString(){
	if(val==null) return super.toString();
	String str = new String();
	for(int i=0; i<rowNum; i++){
	    for(int j=0; j<columnNum; j++){
		str += String.valueOf(val[i][j]);
		if(j==(columnNum-1)) str += "\n";
		else str += " ";
	    }
	}
	return str;
    }
    
    
    public double[] toArray(boolean perRow){
	double[] ret = new double[rowNum*columnNum];
	for(int i=0; i<rowNum; i++){
	    for(int j=0; j<columnNum; j++){
		if(perRow) ret[j*rowNum+i] = val[i][j];
		else ret[i*columnNum+j] = val[i][j];
	    }
	}
	return ret;
    }
    
    public double[] toArray(){ return toArray(true); }
    
}
