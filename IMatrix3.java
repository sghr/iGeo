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
   Class of 3x3 numerical matrix.
   
   @author Satoru Sugihara
*/
public class IMatrix3 extends IMatrix implements IMatrix3I{
    
    public IMatrix3(){
	super(3,3);
	setZero();
    }
    
    public IMatrix3(double v11, double v12, double v13,
		    double v21, double v22, double v23,
		    double v31, double v32, double v33){
	super(3,3);
	set(v11,v12,v13,
	    v21,v22,v23,
	    v31,v32,v33);
    }
    
    public IMatrix3(IMatrix3 m){
	super(3,3);
	set(m.val);
    }

    public IMatrix3 get(){ return this; }
    
    public IMatrix3 set(double v11, double v12, double v13,
			double v21, double v22, double v23,
			double v31, double v32, double v33){
	/*
	double[][] v = new double[3][3];
	v[0][0] = v11; v[0][1] = v12; v[0][2] = v13;
	v[1][0] = v21; v[1][1] = v22; v[1][2] = v23;
	v[2][0] = v31; v[2][1] = v32; v[2][2] = v33;
	set(v);
	*/
	val[0][0] = v11; val[0][1] = v12; val[0][2] = v13;
	val[1][0] = v21; val[1][1] = v22; val[1][2] = v23;
	val[2][0] = v31; val[2][1] = v32; val[2][2] = v33;
	
	return this;
    }
    
    public IMatrix3 set(IDoubleI v11, IDoubleI v12, IDoubleI v13,
			IDoubleI v21, IDoubleI v22, IDoubleI v23,
			IDoubleI v31, IDoubleI v32, IDoubleI v33){
	return set(v11.x(),v12.x(),v13.x(),
		   v21.x(),v22.x(),v23.x(),
		   v31.x(),v32.x(),v33.x());
    }
    
    
    public IMatrix3 dup(){ return new IMatrix3(this); }
    public IMatrix3 cp(){ return dup(); }
    
    public double determinant(){
	return val[0][0]*det(val[1][1],val[1][2],val[2][1],val[2][2])
	    +val[0][1]*det(val[1][2],val[1][0],val[2][2],val[2][0])
	    +val[0][2]*det(val[1][0],val[1][1],val[2][0],val[2][1]);
    }
    
        
    public /*void*/ IMatrix3 invert(){
	double det = determinant();
	
	set(
	    val[1][1]*val[2][2] - val[1][2]*val[2][1],
	    val[0][2]*val[2][1] - val[0][1]*val[2][2],
	    val[0][1]*val[1][2] - val[0][2]*val[1][1],
	    
	    val[1][2]*val[2][0] - val[1][0]*val[2][2],
	    val[0][0]*val[2][2] - val[0][2]*val[2][0],
	    val[0][2]*val[1][0] - val[0][0]*val[1][2],
	    
	    val[1][0]*val[2][1] - val[1][1]*val[2][0],
	    val[0][1]*val[2][0] - val[0][0]*val[2][1],
	    val[0][0]*val[1][1] - val[0][1]*val[1][0]
	    );
	
	div(det);
	return this;
    }
    
    /**
       m is applied from right side and update the content of this without
       creating new instance. it returns itself.
    */
    public IMatrix3 mul(IMatrix3 m){
	double v1, v2, v3;
	for(int i=0; i<3; i++){ // row
            //for(int j=0; j<3; j++){ // column
	    v1=0; v2=0; v3=0;
	    for(int k=0; k<3; k++){
		v1+=val[i][k]*m.val[k][0];
		v2+=val[i][k]*m.val[k][1];
		v3+=val[i][k]*m.val[k][2];
	    }
	    val[i][0]=v1;
	    val[i][1]=v2;
	    val[i][2]=v3;
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
    
    public IMatrix3 mul(IMatrix3I m){ return mul(m.get()); }
    
    /** vector is treated as vertical vector */
    public IVec mul(IVecI v){ return mul(v.get()); }
    
    /** vector is treated as vertical vector */
    public IVec mul(IVec v){
	IVec ret = new IVec();
	ret.x = val[0][0]*v.x+val[0][1]*v.y+val[0][2]*v.z;
	ret.y = val[1][0]*v.x+val[1][1]*v.y+val[1][2]*v.z;
	ret.z = val[2][0]*v.x+val[2][1]*v.y+val[2][2]*v.z;
	return ret;
    }
    
    /** vector is treated as vertical vector */
    public IVec2 mul(IVec2I v){ return mul(v.get()); }
    
    /** vector is treated as vertical vector */
    public IVec2 mul(IVec2 v){
	IVec2 ret = new IVec2();
	ret.x = val[0][0]*v.x+val[0][1]*v.y+val[0][2];
	ret.y = val[1][0]*v.x+val[1][1]*v.y+val[1][2];
	return ret;
    }
    
    public static IMatrix3 getXRotation(double angle){
	return new IMatrix3(1, 0, 0,
			    0, Math.cos(angle), -Math.sin(angle),
			    0, Math.sin(angle), Math.cos(angle));
    }
    public static IMatrix3 getYRotation(double angle){
	return new IMatrix3(Math.cos(angle), 0, Math.sin(angle),
			    0, 1, 0,
			    -Math.sin(angle), 0, Math.cos(angle));
    }
    public static IMatrix3 getZRotation(double angle){
	return new IMatrix3(Math.cos(angle), -Math.sin(angle), 0,
			    Math.sin(angle), Math.cos(angle), 0,
			    0, 0, 1);
    }
    public static IMatrix3 getRotation(IVec axis, double angle){
	IVec a = axis.dup().unit();
	
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        double ic = 1-c;
	
	return new IMatrix3
	    (ic*a.x*a.x+c,     a.x*a.y*ic-a.z*s, a.x*a.z*ic+a.y*s,
	     a.x*a.y*ic+a.z*s, a.y*a.y*ic+c,     a.y*a.z*ic-a.x*s,
	     a.x*a.z*ic-a.y*s, a.y*a.z*ic+a.x*s, a.z*a.z*ic+c);
    }
    
    public static IMatrix3 getTranslate(double x, double y){
	return new IMatrix3(1,0,x,
			    0,1,y,
			    0,0,1);

    }
    public static IMatrix3 getTranslate(IVec p){
	return new IMatrix3(1,0,p.x,
			    0,1,p.y,
			    0,0,1);
    }
    
    
}
