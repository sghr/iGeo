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
   Class of 2x2 numerical matrix.
   
   @author Satoru Sugihara
*/
public class IMatrix2 extends IMatrix implements IMatrix2I{
    
    public IMatrix2(){ super(2,2);  setZero(); }
    
    public IMatrix2(double v11, double v12,
		    double v21, double v22){ super(2,2); set(v11,v12,v21,v22); }
    
    public IMatrix2(IMatrix2 m){ super(2,2); set(m.val); }
    
    public IMatrix2 get(){ return this; }
    
    public IMatrix2 set(double v11, double v12,
			double v21, double v22){
	val[0][0] = v11; val[0][1] = v12; 
	val[1][0] = v21; val[1][1] = v22; 
	return this;
    }
    
    public IMatrix2 set(IDoubleI v11, IDoubleI v12, 
			IDoubleI v21, IDoubleI v22){
	return set(v11.x(),v12.x(),v21.x(),v22.x());
    }
    
    public IMatrix2 dup(){ return new IMatrix2(this); }
    public IMatrix2 cp(){ return dup(); }
    
    public double determinant(){
	return det(val[0][0],val[0][1],val[1][0],val[1][1]);
    }
    
    
    public IMatrix2 invert(){
	double det = determinant();
	set(val[1][1], -val[0][1],
	    -val[1][0], val[0][0]);
	div(det);
	return this;
    }
    
    /**
       m is applied from right side and update the content of this without
       creating new instance. it returns itself.
    */
    public IMatrix2 mul(IMatrix2 m){
	double v1, v2;
	for(int i=0; i<2; i++){ // row
            //for(int j=0; j<2; j++){ // column
	    v1=0; v2=0; 
	    for(int k=0; k<2; k++){
		v1+=val[i][k]*m.val[k][0];
		v2+=val[i][k]*m.val[k][1];
	    }
	    val[i][0]=v1;
	    val[i][1]=v2;
	}
        return this;
	/*
	IMatrix3 retval = new IMatrix3();
        for(int i=0; i<rowNum; i++){
            for(int j=0; j<m.columnNum; j++){
                retval.val[i][j] = 0;
                for(int k=0; k<columnNum; k++){
                    retval.val[i][j] += val[i][k]*m.val[k][j];
                }
            }
        }
        return retval;
	*/
    }
    
    public IMatrix2 mul(IMatrix2I m){ return mul(m.get()); }
    
    /** vector is treated as vertical vector */
    public IVec2 mul(IVec2I v){ return mul(v.get()); }
    
    /** vector is treated as vertical vector */
    public IVec2 mul(IVec2 v){
	IVec2 ret = new IVec2();
	ret.x = val[0][0]*v.x+val[0][1]*v.y;
	ret.y = val[1][0]*v.x+val[1][1]*v.y;
	return ret;
    }
    
    public static IMatrix2 getRotation(double angle){
	return new IMatrix2(Math.cos(angle), -Math.sin(angle),
			    Math.sin(angle), Math.cos(angle));
    }
    
    
}
