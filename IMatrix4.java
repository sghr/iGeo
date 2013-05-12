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
   Class of 4x4 numerical matrix.
   
   @author Satoru Sugihara
*/
public class IMatrix4 extends IMatrix implements IMatrix4I{
    
    public IMatrix4(){ super(4,4); setZero(); }
    
    public IMatrix4(double v11, double v12, double v13, double v14,
		    double v21, double v22, double v23, double v24,
		    double v31, double v32, double v33, double v34,
		    double v41, double v42, double v43, double v44){
	super(4,4);
	set(v11,v12,v13,v14,
	    v21,v22,v23,v24,
	    v31,v32,v33,v34,
	    v41,v42,v43,v44);
    }
    
    public IMatrix4(IMatrix4 m){ super(4,4); set(m.val); }

    public IMatrix4(IMatrix3 m, IVec t){
	super(4,4);
	set(m.val[0][0], m.val[0][1], m.val[0][2], t.x,
	    m.val[1][0], m.val[1][1], m.val[1][2], t.y,
	    m.val[2][0], m.val[2][1], m.val[2][2], t.z,
	    0,0,0,0);
    }
    
    public IMatrix4 get(){ return this; }
    
    public IMatrix4 set(double v11, double v12, double v13, double v14,
			double v21, double v22, double v23, double v24,
			double v31, double v32, double v33, double v34,
			double v41, double v42, double v43, double v44){
	/*
	double[][] v = new double[4][4];
	v[0][0] = v11; v[0][1] = v12; v[0][2] = v13; v[0][3] = v14;
	v[1][0] = v21; v[1][1] = v22; v[1][2] = v23; v[1][3] = v24;
	v[2][0] = v31; v[2][1] = v32; v[2][2] = v33; v[2][3] = v34;
	v[3][0] = v41; v[3][1] = v42; v[3][2] = v43; v[3][3] = v44;
	set(v);
	*/
	val[0][0] = v11; val[0][1] = v12; val[0][2] = v13; val[0][3] = v14;
	val[1][0] = v21; val[1][1] = v22; val[1][2] = v23; val[1][3] = v24;
	val[2][0] = v31; val[2][1] = v32; val[2][2] = v33; val[2][3] = v34;
	val[3][0] = v41; val[3][1] = v42; val[3][2] = v43; val[3][3] = v44;
	return this;
    }
    
    public IMatrix4 set(IDoubleI v11, IDoubleI v12, IDoubleI v13, IDoubleI v14,
			IDoubleI v21, IDoubleI v22, IDoubleI v23, IDoubleI v24,
			IDoubleI v31, IDoubleI v32, IDoubleI v33, IDoubleI v34,
			IDoubleI v41, IDoubleI v42, IDoubleI v43, IDoubleI v44){
	return set(v11.x(),v12.x(),v13.x(),v14.x(),
		   v21.x(),v22.x(),v23.x(),v24.x(),
		   v31.x(),v32.x(),v33.x(),v34.x(),
		   v41.x(),v42.x(),v43.x(),v44.x());
    }
    
    public IMatrix4 dup(){ return new IMatrix4(this); }
    public IMatrix4 cp(){ return dup(); }
    
    public double determinant(){
	return
	    det(val[0][0],val[0][1],val[1][0],val[1][1])*
	    det(val[2][2],val[2][3],val[3][2],val[3][3]) +
	    det(val[0][0],val[0][2],val[1][0],val[1][2])*
	    det(val[2][3],val[2][1],val[3][3],val[3][1]) +
	    det(val[0][0],val[0][3],val[1][0],val[1][3])*
	    det(val[2][1],val[2][2],val[3][1],val[3][2]) +
	    det(val[0][1],val[0][2],val[1][1],val[1][2])*
	    det(val[2][0],val[2][3],val[3][0],val[3][3]) +
	    det(val[0][3],val[0][1],val[1][3],val[1][1])*
	    det(val[2][0],val[2][2],val[3][0],val[3][2]) +
	    det(val[0][2],val[0][3],val[1][2],val[1][3])*
	    det(val[2][0],val[2][1],val[3][0],val[3][1]);
    }
    
    public /*void*/ IMatrix4 invert(){
	double det = determinant();
	
	set(
	    val[1][1]*det(val[2][2],val[2][3],val[3][2],val[3][3]) +
	    val[1][2]*det(val[2][3],val[2][1],val[3][3],val[3][1]) +
	    val[1][3]*det(val[2][1],val[2][2],val[3][1],val[3][2]),
	    
	    val[2][1]*det(val[0][2],val[0][3],val[3][2],val[3][3])+
	    val[2][2]*det(val[0][3],val[0][1],val[3][3],val[3][1])+
	    val[2][3]*det(val[0][1],val[0][2],val[3][1],val[3][2]),
	    
	    val[3][1]*det(val[0][2],val[0][3],val[1][2],val[1][3])+
	    val[3][2]*det(val[0][3],val[0][1],val[1][3],val[1][1])+
	    val[3][3]*det(val[0][1],val[0][2],val[1][1],val[1][2]),
	    
	    val[0][1]*det(val[1][3],val[1][2],val[2][3],val[2][2])+
	    val[0][2]*det(val[1][1],val[1][3],val[2][1],val[2][3])+
	    val[0][3]*det(val[1][2],val[1][1],val[2][2],val[2][1]),
	    
	    
	    val[1][2]*det(val[2][0],val[2][3],val[3][0],val[3][3]) +
	    val[1][3]*det(val[2][2],val[2][0],val[3][2],val[3][0]) +
	    val[1][0]*det(val[2][3],val[2][2],val[3][3],val[3][2]),
	    
	    val[2][2]*det(val[0][0],val[0][3],val[3][0],val[3][3])+
	    val[2][3]*det(val[0][2],val[0][0],val[3][2],val[3][0])+
	    val[2][0]*det(val[0][3],val[0][2],val[3][3],val[3][2]),
	    
	    val[3][2]*det(val[0][0],val[0][3],val[1][0],val[1][3])+
	    val[3][3]*det(val[0][2],val[0][0],val[1][2],val[1][0])+
	    val[3][0]*det(val[0][3],val[0][2],val[1][3],val[1][2]),
	    
	    val[0][2]*det(val[1][3],val[1][0],val[2][3],val[2][0])+
	    val[0][3]*det(val[1][0],val[1][2],val[2][0],val[2][2])+
	    val[0][0]*det(val[1][2],val[1][3],val[2][2],val[2][3]),
	    
	    
	    val[1][3]*det(val[2][0],val[2][1],val[3][0],val[3][1]) +
	    val[1][0]*det(val[2][1],val[2][3],val[3][1],val[3][3]) +
	    val[1][1]*det(val[2][3],val[2][0],val[3][3],val[3][0]),
	    
	    val[2][3]*det(val[0][0],val[0][1],val[3][0],val[3][1])+
	    val[2][0]*det(val[0][1],val[0][3],val[3][1],val[3][3])+
	    val[2][1]*det(val[0][3],val[0][0],val[3][3],val[3][0]),
	    
	    val[3][3]*det(val[0][0],val[0][1],val[1][0],val[1][1])+
	    val[3][0]*det(val[0][1],val[0][3],val[1][1],val[1][3])+
	    val[3][1]*det(val[0][3],val[0][0],val[1][3],val[1][0]),
	    
	    val[0][3]*det(val[1][1],val[1][0],val[2][1],val[2][0])+
	    val[0][0]*det(val[1][3],val[1][1],val[2][3],val[2][1])+
	    val[0][1]*det(val[1][0],val[1][3],val[2][0],val[2][3]),
	    
	    
	    val[1][0]*det(val[2][2],val[2][1],val[3][2],val[3][1]) +
	    val[1][1]*det(val[2][0],val[2][2],val[3][0],val[3][2]) +
	    val[1][2]*det(val[2][1],val[2][0],val[3][1],val[3][0]),
	    
	    val[2][0]*det(val[0][2],val[0][1],val[3][2],val[3][1])+
	    val[2][1]*det(val[0][0],val[0][2],val[3][0],val[3][2])+
	    val[2][2]*det(val[0][1],val[0][0],val[3][1],val[3][0]),
	    
	    val[3][0]*det(val[0][2],val[0][1],val[1][2],val[1][1])+
	    val[3][1]*det(val[0][0],val[0][2],val[1][0],val[1][2])+
	    val[3][2]*det(val[0][1],val[0][0],val[1][1],val[1][0]),
	    
	    val[0][0]*det(val[1][1],val[1][2],val[2][1],val[2][2])+
	    val[0][1]*det(val[1][2],val[1][0],val[2][2],val[2][0])+
	    val[0][2]*det(val[1][0],val[1][1],val[2][0],val[2][1])
	    );
	
	div(det);
	return this;
    }
    
    /**
       m is applied from right side and update the content of this without
       creating new instance. it returns itself.
    */
    public IMatrix4 mul(IMatrix4 m){
	double v1, v2, v3, v4;
	for(int i=0; i<4; i++){ // row
            //for(int j=0; j<4; j++){ // column
	    v1=0; v2=0; v3=0; v4=0;
	    for(int k=0; k<4; k++){
		v1+=val[i][k]*m.val[k][0];
		v2+=val[i][k]*m.val[k][1];
		v3+=val[i][k]*m.val[k][2];
		v4+=val[i][k]*m.val[k][3];
	    }
	    val[i][0]=v1;
	    val[i][1]=v2;
	    val[i][2]=v3;
	    val[i][3]=v4;
	}
        return this;
	
	/*
        IMatrix4 retval = new IMatrix4();
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

    public IMatrix4 mul(IMatrix4I m){ return mul(m.get()); }
    
    
    /** vector is treated as vertical vector */
    public IVec4 mul(IVec4I v){ return mul(v.get()); }
    
    /** vector is treated as vertical vector */
    public IVec4 mul(IVec4 v){
	IVec4 ret = new IVec4();
	ret.x=val[0][0]*v.x+val[0][1]*v.y+val[0][2]*v.z+val[0][3]*v.w;
	ret.y=val[1][0]*v.x+val[1][1]*v.y+val[1][2]*v.z+val[1][3]*v.w;
	ret.z=val[2][0]*v.x+val[2][1]*v.y+val[2][2]*v.z+val[2][3]*v.w;
	ret.w=val[3][0]*v.x+val[3][1]*v.y+val[3][2]*v.z+val[3][3]*v.w;
	return ret;
    }
    
    /** vector is treated as vertical vector */
    public IVec mul(IVecI v){ return mul(v.get()); }
    
    /** vector is treated as vertical vector */
    public IVec mul(IVec v){
	IVec ret = new IVec();
	ret.x = val[0][0]*v.x+val[0][1]*v.y+val[0][2]*v.z+val[0][3];
	ret.y = val[1][0]*v.x+val[1][1]*v.y+val[1][2]*v.z+val[1][3];
	ret.z = val[2][0]*v.x+val[2][1]*v.y+val[2][2]*v.z+val[2][3];
	return ret;
    }
    
    /** applying transformation matrix to 3D vector */
    public IVec transform(IVecI v){ return mul(v); }
    
    /** get matrix without translation part */
    public IMatrix3 matrix3(){
	return new IMatrix3(val[0][0], val[0][1], val[0][2],
			    val[1][0], val[1][1], val[1][2],
			    val[2][0], val[2][1], val[2][2]);
    }
    /** alias of matrix3() */
    public IMatrix3 getMatrix3(){ return matrix3(); }
    
    /** get only translate vector */
    public IVec translate(){
	return new IVec(val[0][3], val[1][3], val[2][3]);
    }
    /** alias of translate() */
    public IVec getTranslateVector(){ return translate(); }
    
    
    
    public static IMatrix4 getXRotation(double angle){
	return new IMatrix4(1, 0, 0, 0,
			     0, Math.cos(angle), -Math.sin(angle), 0, 
			     0, Math.sin(angle), Math.cos(angle), 0, 
			     0, 0, 0, 1);
    }
    public static IMatrix4 getYRotation(double angle){
	return new IMatrix4(Math.cos(angle), 0, Math.sin(angle), 0,
			     0, 1, 0, 0, 
			     -Math.sin(angle), 0, Math.cos(angle), 0, 
			     0, 0, 0, 1);
    }
    public static IMatrix4 getZRotation(double angle){
	return new IMatrix4(Math.cos(angle), -Math.sin(angle), 0, 0,
			     Math.sin(angle), Math.cos(angle), 0, 0, 
			     0, 0, 1, 0,
			     0, 0, 0, 1);
    }
    
    public static IMatrix4 getRotation(IVec axis, double angle){
	IVec a = axis.dup().unit();
	
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        double ic = 1-c;
	
	return new IMatrix4(ic*a.x*a.x+c,     a.x*a.y*ic-a.z*s, a.x*a.z*ic+a.y*s, 0,
			     a.x*a.y*ic+a.z*s, a.y*a.y*ic+c,     a.y*a.z*ic-a.x*s, 0,
			     a.x*a.z*ic-a.y*s, a.y*a.z*ic+a.x*s, a.z*a.z*ic+c,     0,
			     0,                0,                0,                0);
	// is not the last number 1?
    }
    
    
    public static IMatrix4 getScale(double scale){
	return new IMatrix4(scale, 0, 0, 0,
			     0, scale, 0, 0,
			     0, 0, scale, 0,
			     0, 0, 0, 1);
    }
    public static IMatrix4 getTranslate(double x, double y, double z){
	return new IMatrix4(1,0,0,x,
			     0,1,0,y,
			     0,0,1,z,
			     0,0,0,1);
    }
    public static IMatrix4 getTranslate(IVec p){
	return new IMatrix4(1,0,0,p.x,
			     0,1,0,p.y,
			     0,0,1,p.z,
			     0,0,0,1);
    }
    
    // mapping coordinates from xyz (origin 0) coordinates to xvector-yvectro-zvector (origin translate) coordinates
    public static IMatrix4 getTransform(IVec xvector,
					IVec yvector,
					IVec zvector,
					IVec translate){
	return new IMatrix4(xvector.x, yvector.x, zvector.x, translate.x,
			    xvector.y, yvector.y, zvector.y, translate.y,
			    xvector.z, yvector.z, zvector.z, translate.z,
			    0,0,0,1);
    }
    
    public static IMatrix4 convertCoordinates(IVec xvec1, IVec yvec1, IVec zvec1, IVec orig1,
					      IVec xvec2, IVec yvec2, IVec zvec2, IVec orig2){
	IMatrix4 mat1 = getTransform(xvec1,yvec1,zvec1,orig1);
	IMatrix4 mat2 = getTransform(xvec2,yvec2,zvec2,orig2);
	mat1.invert();
	return mat2.mul(mat1);
    }
    
    
    // rotating triangle onto a plane
    public static IMatrix4 getAlignmentOnPlane(IVec trianglePt1, IVec trianglePt2, IVec trianglePt3,
					 IVec planePt1, IVec planePt2, IVec planePt3){
	
	IVec triangleVec1 = trianglePt2.diff(trianglePt1);
	IVec triangleVec2 = trianglePt3.diff(trianglePt1);
	IVec zvec1 = triangleVec1.cross(triangleVec2);
	if(zvec1.len()==0){
	    zvec1 = triangleVec1.cross(IVec.zaxis);
	    if(zvec1.len()==0) zvec1=triangleVec1.cross(IVec.xaxis);
	}
	zvec1.unit();
	IVec xvec1 = triangleVec1;
	xvec1.unit();
	IVec yvec1 = zvec1.cross(xvec1);
	yvec1.unit();
	
	IVec planeVec1 = planePt2.diff(planePt1);
	IVec planeVec2 = planePt3.diff(planePt1);
	IVec zvec2 = planeVec1.cross(planeVec2);
	if(zvec2.len()==0){
	    zvec2 = planeVec1.cross(IVec.zaxis);
	    if(zvec2.len()==0) zvec2=planeVec1.cross(IVec.xaxis);
	}
	zvec2.unit();
	IVec xvec2 = planeVec1;
	xvec2.unit();
	IVec yvec2 = zvec2.cross(xvec2);
	yvec2.unit();
	
	return convertCoordinates(xvec1, yvec1, zvec1, trianglePt1,
				  xvec2, yvec2, zvec2, planePt1);
    }
    
}
