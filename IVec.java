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
   Class of 3 dimensional vector.
   
   @author Satoru Sugihara
*/
public class IVec extends IParameterObject implements IVecI, IEntityParameter{
    
    public final static IVec origin = new IVec(0,0,0);
    public final static IVec xaxis = new IVec(1,0,0);
    public final static IVec yaxis = new IVec(0,1,0);
    public final static IVec zaxis = new IVec(0,0,1);
    
    public double x,y,z;
    public IVec(){}
    public IVec(double x, double y, double z){ this.x=x; this.y=y; this.z=z; }
    public IVec(double x, double y){ this.x=x; this.y=y; z=0; }
    public IVec(IVec v){ x=v.x; y=v.y; z=v.z; }
    public IVec(IVecI v){ IVec u=v.get(); x=u.x; y=u.y; z=u.z; }
    public IVec(IDoubleI x, IDoubleI y, IDoubleI z){
	this.x=x.x(); this.y=y.x(); this.z=z.x();
    }
    public IVec(IVec2I v){ x=v.x(); y=v.y(); z=0; }
    
    public IVec(IServerI s){ super(s); }
    public IVec(IServerI s, double x, double y, double z){ super(s); this.x=x; this.y=y; this.z=z; }
    public IVec(IServerI s, double x, double y){ super(s); this.x=x; this.y=y; z=0; }
    public IVec(IServerI s, IVec v){ super(s); x=v.x; y=v.y; z=v.z; }
    public IVec(IServerI s, IVecI v){ super(s); IVec u=v.get(); x=u.x; y=u.y; z=u.z; }
    public IVec(IServerI s, IDoubleI x, IDoubleI y, IDoubleI z){
	super(s); this.x=x.x(); this.y=y.x(); this.z=z.x();
    }
    public IVec(IServerI s, IVec2I v){ super(s); x=v.x(); y=v.y(); z=0; }
    
    
    public double x(){ return x; }
    public double y(){ return y; }
    public double z(){ return z; }

    
    /** setting x component */
    public IVec x(double vx){ x=vx; return this; }
    /** setting y component */
    public IVec y(double vy){ y=vy; return this; }
    /** setting z component */
    public IVec z(double vz){ z=vz; return this; }
    
    /** setting x component */
    public IVec x(IDoubleI vx){ x=vx.x(); return this; }
    /** setting y component */
    public IVec y(IDoubleI vy){ y=vy.x(); return this; }
    /** setting z component */
    public IVec z(IDoubleI vz){ z=vz.x(); return this; }
    
    /** setting x component by x component of input vector*/
    public IVec x(IVecI v){ x(v.x()); return this; }
    /** setting y component by y component of input vector*/
    public IVec y(IVecI v){ y(v.y()); return this; }
    /** setting z component by z component of input vector*/
    public IVec z(IVecI v){ z(v.z()); return this; }
    
    /** setting x component by x component of input vector*/
    public IVec x(IVec2I v){ x(v.x()); return this; }
    /** setting y component by y component of input vector*/
    public IVec y(IVec2I v){ y(v.y()); return this; }
    
    
    /** getting x component */
    public double x(ISwitchE e){ return x(); }
    /** getting y component */
    public double y(ISwitchE e){ return y(); }
    /** getting z component */
    public double z(ISwitchE e){ return z(); }
    
    /** getting x component */
    public IDouble x(ISwitchR r){ return new IDouble(x); }
    /** getting y component */
    public IDouble y(ISwitchR r){ return new IDouble(y); }
    /** getting z component */
    public IDouble z(ISwitchR r){ return new IDouble(z); }
    
    
    
    public IVec get(){ return this; }
    //public IVec get(){ return new IVec(x,y,z); }
    
    public IVec dup(){ return new IVec(x,y,z); }
    
    
    public IVec2 to2d(){ return new IVec2(this); }
    
    public IVec2 to2d(IVecI projectionDir){ return new IVec2(this,projectionDir); } //
    public IVec2 to2d(IVecI xaxis, IVecI yaxis){ return new IVec2(this,xaxis,yaxis); } //
    public IVec2 to2d(IVecI xaxis, IVecI yaxis, IVecI origin){ return new IVec2(this,xaxis,yaxis,origin); } //
    
    public IVec4 to4d(){ return new IVec4(this); }
    public IVec4 to4d(double w){ return new IVec4(this,w); }
    public IVec4 to4d(IDoubleI w){ return new IVec4(this,w); }
    
    
    public IDouble getX(){ return new IDouble(x); }
    public IDouble getY(){ return new IDouble(y); }
    public IDouble getZ(){ return new IDouble(z); }
    
    
    
    public IVec set(double x, double y, double z){ this.x=x; this.y=y; this.z=z; return this; }
    public IVec set(IVec v){ x=v.x; y=v.y; z=v.z; return this; }
    public IVec set(IVecI v){ return set(v.get()); }
    public IVec set(IDoubleI x, IDoubleI y, IDoubleI z){
	this.x=x.x(); this.y=y.x(); this.z=z.x(); return this;
    }
    
    public IVec add(double x, double y, double z){
	this.x+=x; this.y+=y; this.z+=z; return this;
    }
    public IVec add(IDoubleI x, IDoubleI y, IDoubleI z){
	this.x+=x.x(); this.y+=y.x(); this.z+=z.x(); return this;
    }
    public IVec add(IVec v){ x+=v.x; y+=v.y; z+=v.z; return this; }
    public IVec add(IVecI v){ return add(v.get()); }
    
    public IVec sub(double x, double y, double z){
	this.x-=x; this.y-=y; this.z-=z; return this;
    }
    public IVec sub(IDoubleI x, IDoubleI y, IDoubleI z){
	this.x-=x.x(); this.y-=y.x(); this.z-=z.x(); return this;
    }
    public IVec sub(IVec v){ x-=v.x; y-=v.y; z-=v.z; return this; }
    public IVec sub(IVecI v){ return sub(v.get()); }
    
    public IVec mul(double v){ x*=v; y*=v; z*=v; return this; }
    public IVec mul(IDouble v){ x*=v.x; y*=v.x; z*=v.x; return this; }
    public IVec mul(IDoubleI v){ return mul(v.x()); }
    
    public IVec div(double v){ x/=v; y/=v; z/=v; return this; }
    public IVec div(IDouble v){ x/=v.x; y/=v.x; z/=v.x; return this; }
    public IVec div(IDoubleI v){ return div(v.x()); }
    
    public IVec neg(){ x=-x; y=-y; z=-z; return this; }
    /** alias of neg() */
    public IVec rev(){ return neg(); }
    /** alias of neg() */
    public IVec flip(){ return neg(); }
    
    /** setting all zero */
    public IVec zero(){ x=0; y=0; z=0; return this; }
    
    
    /** scale add */
    public IVec add(IVec v, double f){ x+=f*v.x; y+=f*v.y; z+=f*v.z; return this; }
    /** scale add */
    public IVec add(IVecI v, double f){ return add(v.get(),f); }
    /** scale add */
    public IVec add(IVecI v, IDoubleI f){ return add(v.get(),f); }
    
    /** scale add; alias of add(IVec,double) */
    public IVec add(double f, IVec v){ return add(v,f); }
    /** scale add; alias of add(IVecI,double) */
    public IVec add(double f, IVecI v){ return add(v,f); }
    /** scale add; alias of add(IVec,IDouble) */
    public IVec add(IDoubleI f, IVecI v){ return add(v,f); }
    
    
    /** dot product in double */
    public double dot(IVec v){ return x*v.x+y*v.y+z*v.z; }
    /** dot product in double */
    public double dot(IVecI v){ return dot(v.get()); }
    /** dot product in double */
    public double dot(double vx, double vy, double vz){ return x*vx+y*vy+z*vz; }
    /** dot product in double */
    public double dot(ISwitchE e, IVecI v){ return dot(v); }
    /** dot product in IDouble */
    public IDouble dot(ISwitchR r, IVecI v){ return new IDouble(dot(v)); }
    
    /** cross product. returning a new instance, not changing own content! 2011/08/03 */
    public IVec cross(IVec v){
        //double xt = y*v.z - z*v.y;
        //double yt = z*v.x - x*v.z;
        //double zt = x*v.y - y*v.x;
        //x=xt; y=yt; z=zt; return this;
	return new IVec(y*v.z - z*v.y,  z*v.x - x*v.z, x*v.y - y*v.x);
    }
    /** cross product. */
    public IVec cross(double vx, double vy, double vz){ return new IVec(y*vz-z*vy, z*vx-x*vz,x*vy-y*vx); }
        
    public IVec cross(IVecI v){ return cross(v.get()); }

    /** cross product, changing its values by itself. no new instance created. */
    public IVec icross(IVec v){
        double xt = y*v.z - z*v.y;
        double yt = z*v.x - x*v.z;
        double zt = x*v.y - y*v.x;
        x=xt; y=yt; z=zt; return this;
    }
    /** cross product, changing its values by itself. no new instance created. */
    public IVec icross(double vx, double vy, double vz){
        double xt = y*vz - z*vy;
        double yt = z*vx - x*vz;
        double zt = x*vy - y*vx;
        x=xt; y=yt; z=zt; return this;
    }
    /** cross product, changing its values by itself. no new instance created. */
    public IVec icross(IVecI v){ return icross(v.get()); }
    
    /** get length of the vector */
    public double len(){ return Math.sqrt(x*x+y*y+z*z); }
    /** get length of the vector */
    public double len(ISwitchE e){ return len(); }
    /** get length of the vector */
    public IDouble len(ISwitchR r){ return new IDouble(len()); }
    
    /** get squared length of the vector */
    public double len2(){ return x*x+y*y+z*z; }
    /** get squared length of the vector */
    public double len2(ISwitchE e){ return len2(); }
    /** get squared length of the vector */
    public IDouble len2(ISwitchR r){ return new IDouble(len2()); }
    
    /** set length of the vector */
    public IVec len(double l){ l /= len(); x*=l; y*=l; z*=l; return this; }
    /** set length of the vector */
    public IVec len(IDoubleI l){ return len(l.x()); }
    /** unitize the vector */
    public IVec unit(){
	double l=len();
	//if(l==0){ IOut.err("vector length is zero"); return this; } // added 20111002 // removed 20111003
	x/=l; y/=l; z/=l;
	return this;
    }
    
    /** get distance between two vectors */
    public double dist(IVec v){
	return Math.sqrt( (x-v.x)*(x-v.x) + (y-v.y)*(y-v.y) + (z-v.z)*(z-v.z) );
    }
    /** get distance between two vectors */
    public double dist(IVecI v){ return dist(v.get()); }
    /** get distance between two vectors */
    public double dist(double vx, double vy, double vz){
	return Math.sqrt( (x-vx)*(x-vx) + (y-vy)*(y-vy) + (z-vz)*(z-vz) );
    }
    /** get distance between two vectors */
    public double dist(ISwitchE e, IVecI v){ return dist(v); }
    /** get distance between two vectors */
    public IDouble dist(ISwitchR r, IVecI v){ return new IDouble(dist(v)); }

    /** get squared distance between two vectors */
    public double dist2(IVec v){ return (x-v.x)*(x-v.x) + (y-v.y)*(y-v.y) + (z-v.z)*(z-v.z); }
    /** get squared distance between two vectors */
    public double dist2(double vx,double vy,double vz){ return (x-vx)*(x-vx)+(y-vy)*(y-vy)+(z-vz)*(z-vz); }
    /** get squared distance between two vectors */
    public double dist2(IVecI v){ return dist2(v.get()); }
    /** get squared distance between two vectors */
    public double dist2(ISwitchE e, IVecI v){ return dist2(v); }
    /** get squared distance between two vectors */
    public IDouble dist2(ISwitchR r, IVecI v){ return new IDouble(dist2(v)); }
    
    /** check if 2 vectors are same by distance */
    public boolean eq(IVec v){ return eq(v,IConfig.tolerance); }
    /** check if 2 vectors are same by distance */
    public boolean eq(double vx, double vy, double vz){ return eq(vx,vy,vz,IConfig.tolerance); }
    /** check if 2 vectors are same by distance */
    public boolean eq(IVecI v){ return eq(v.get(),IConfig.tolerance); }
    /** check if 2 vectors are same by distance */
    public boolean eq(ISwitchE e, IVecI v){ return eq(v); }
    /** check if 2 vectors are same by distance */
    public IBool eq(ISwitchR r, IVecI v){ return new IBool(eq(v)); }
    /** check if 2 vectors are same by distance with tolerace */
    public boolean eq(IVec v, double tolerance){ return dist2(v) <= tolerance*tolerance; }
    /** check if 2 vectors are same by distance with tolerace */
    public boolean eq(double vx, double vy, double vz, double tolerance){
	return dist2(vx,vy,vz) <= tolerance*tolerance;
    }
    /** check if 2 vectors are same by distance with tolerace */
    public boolean eq(IVecI v, double tolerance){ return eq(v.get(),tolerance); }
    /** check if 2 vectors are same by distance with tolerace */
    public boolean eq(ISwitchE e, IVecI v, double tolerance){ return eq(v,tolerance); }
    /** check if 2 vectors are same by distance with tolerace */
    public IBool eq(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBool(eq(v,tolerance.x())); }

    /** check if 2 vectors are same in X */
    public boolean eqX(IVec v){ return eqX(v,IConfig.tolerance); }
    /** check if 2 vectors are same in Y */
    public boolean eqY(IVec v){ return eqY(v,IConfig.tolerance); }
    /** check if 2 vectors are same in Z */
    public boolean eqZ(IVec v){ return eqZ(v,IConfig.tolerance); }
    /** check if 2 vectors are same in X */
    public boolean eqX(double vx){ return eqX(vx,IConfig.tolerance); }
    /** check if 2 vectors are same in Y */
    public boolean eqY(double vy){ return eqY(vy,IConfig.tolerance); }
    /** check if 2 vectors are same in Z */
    public boolean eqZ(double vz){ return eqZ(vz,IConfig.tolerance); }
    /** check if 2 vectors are same in X */
    public boolean eqX(IVecI v){ return eqX(v,IConfig.tolerance); }
    /** check if 2 vectors are same in Y */
    public boolean eqY(IVecI v){ return eqY(v,IConfig.tolerance); }
    /** check if 2 vectors are same in Z */
    public boolean eqZ(IVecI v){ return eqZ(v,IConfig.tolerance); }
    /** check if 2 vectors are same in X */
    public boolean eqX(ISwitchE e, IVecI v){ return eqX(v); }
    /** check if 2 vectors are same in Y */
    public boolean eqY(ISwitchE e, IVecI v){ return eqY(v); }
    /** check if 2 vectors are same in Z */
    public boolean eqZ(ISwitchE e, IVecI v){ return eqZ(v); }
    /** check if 2 vectors are same in X */
    public IBool eqX(ISwitchR r, IVecI v){ return new IBool(eqX(v)); }
    /** check if 2 vectors are same in Y */
    public IBool eqY(ISwitchR r, IVecI v){ return new IBool(eqY(v)); }
    /** check if 2 vectors are same in Z */
    public IBool eqZ(ISwitchR r, IVecI v){ return new IBool(eqZ(v)); }
    
    public boolean eqX(IVec v, double tolerance){ return Math.abs(x-v.x)<=tolerance; }
    public boolean eqY(IVec v, double tolerance){ return Math.abs(y-v.y)<=tolerance; }
    public boolean eqZ(IVec v, double tolerance){ return Math.abs(z-v.z)<=tolerance; }
    public boolean eqX(double vx, double tolerance){ return Math.abs(x-vx)<=tolerance; }
    public boolean eqY(double vy, double tolerance){ return Math.abs(y-vy)<=tolerance; }
    public boolean eqZ(double vz, double tolerance){ return Math.abs(z-vz)<=tolerance; }
    public boolean eqX(IVecI v, double tolerance){ return Math.abs(x-v.x())<=tolerance; }
    public boolean eqY(IVecI v, double tolerance){ return Math.abs(y-v.y())<=tolerance; }
    public boolean eqZ(IVecI v, double tolerance){ return Math.abs(z-v.z())<=tolerance; }
    public boolean eqX(ISwitchE e, IVecI v, double tolerance){ return eqX(v,tolerance); }
    public boolean eqY(ISwitchE e, IVecI v, double tolerance){ return eqY(v,tolerance); }
    public boolean eqZ(ISwitchE e, IVecI v, double tolerance){ return eqZ(v,tolerance); }
    public IBool eqX(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBool(eqX(v,tolerance.x())); }
    public IBool eqY(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBool(eqY(v,tolerance.x())); }
    public IBool eqZ(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBool(eqZ(v,tolerance.x())); }
    
    
    /** angle in radian, ranging from 0 to Pi */
    public double angle(IVec v){
        double len1 = len(); if(len1==0) return 0;
        double len2 = v.len(); if(len2==0) return 0;
        double cos = dot(v)/(len1*len2);
        if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
        return Math.acos(cos);
    }
    /** angle in radian, ranging from 0 to Pi */
    public double angle(double vx, double vy, double vz){
        double len1 = len(); if(len1==0) return 0;
        double len2 = Math.sqrt(vx*vx+vy*vy+vz*vz); if(len2==0) return 0;
        double cos = dot(vx,vy,vz)/(len1*len2);
        if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
        return Math.acos(cos);
    }
    
    /** angle in radian, ranging from 0 to Pi */
    public double angle(IVecI v){ return angle(v.get()); }
    /** angle in radian, ranging from 0 to Pi */
    public double angle(ISwitchE e, IVecI v){ return angle(v); }
    /** angle in radian, ranging from 0 to Pi */
    public IDouble angle(ISwitchR r, IVecI v){ return new IDouble(angle(v)); }
    
    /** angle in radian, with reference axis to decide which is negative direction, ranging from -Pi to Pi
	angle is not measured on the plane of axis. axis just define if it's positive angle or negative angle
    */
    public double angle(IVec v, IVec axis){
        //double dot = x*v.x+y*v.y+z*v.z;
        double len1 = len(); if(len1==0) return 0;
        double len2 = v.len(); if(len2==0) return 0;
        double cos = dot(v)/(len1*len2);
	//IVec cross = dup().cross(v);
	IVec cross = cross(v);
        if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
        double angle = Math.acos(cos);
	if(cross.dot(axis)<0) return -angle;
	return angle;
    }
    /** angle in radian, with reference axis to decide which is negative direction, ranging from -Pi to Pi
	angle is not measured on the plane of axis. axis just define if it's positive angle or negative angle
    */
    public double angle(double vx, double vy, double vz, double axisX, double axisY, double axisZ){
        double len1 = len(); if(len1==0) return 0;
        double len2 = Math.sqrt(vx*vx+vy*vy+vz*vz); if(len2==0) return 0;
        double cos = dot(vx,vy,vz)/(len1*len2);
	IVec cross = cross(vx,vy,vz);
        if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
        double angle = Math.acos(cos);
	if(cross.dot(axisX,axisY,axisZ)<0) return -angle;
	return angle;
    }
    /** angle in radian, with reference axis to decide which is negative direction, ranging from -Pi to Pi
	angle is not measured on the plane of axis. axis just define if it's positive angle or negative angle
    */
    public double angle(IVecI v, IVecI axis){ return angle(v.get(),axis.get()); }
    /** angle in radian, with reference axis to decide which is negative direction, ranging from -Pi to Pi
	angle is not measured on the plane of axis. axis just define if it's positive angle or negative angle
    */
    public double angle(ISwitchE e, IVecI v, IVecI axis){ return angle(v,axis); }
    /** angle in radian, with reference axis to decide which is negative direction, ranging from -Pi to Pi
	angle is not measured on the plane of axis. axis just define if it's positive angle or negative angle
    */
    public IDouble angle(ISwitchR r, IVecI v, IVecI axis){ return new IDouble(angle(v,axis)); }
    
    
    /** rotate the vector around the axis */
    public IVec rot(IVec axis, double angle){
	if(axis==null) return rot(angle); // should have null check?
	
        double mat[][] = new double[3][3];
        IVec ax=axis.dup().unit();
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double icos = 1-cos;
        
	// right-handed coordinates
	mat[0][0] = ax.x*ax.x*icos + cos;
        mat[0][1] = ax.x*ax.y*icos - ax.z*sin;
        mat[0][2] = ax.x*ax.z*icos + ax.y*sin;
        mat[1][0] = ax.y*ax.x*icos + ax.z*sin;
        mat[1][1] = ax.y*ax.y*icos + cos;
        mat[1][2] = ax.y*ax.z*icos - ax.x*sin;
        mat[2][0] = ax.z*ax.x*icos - ax.y*sin;
        mat[2][1] = ax.z*ax.y*icos + ax.x*sin;
        mat[2][2] = ax.z*ax.z*icos + cos;
	
	// left-handed coordinates
	// mat[0][0] = ax.x*ax.x*icos + cos;
        // mat[0][1] = ax.x*ax.y*icos + ax.z*sin;
        // mat[0][2] = ax.x*ax.z*icos - ax.y*sin;
        // mat[1][0] = ax.y*ax.x*icos - ax.z*sin;
        // mat[1][1] = ax.y*ax.y*icos + cos;
        // mat[1][2] = ax.y*ax.z*icos + ax.x*sin;
        // mat[2][0] = ax.z*ax.x*icos + ax.y*sin;
        // mat[2][1] = ax.z*ax.y*icos - ax.x*sin;
        // mat[2][2] = ax.z*ax.z*icos + cos;
	
        double xt=x;
        double yt=y;
        x = mat[0][0]*xt + mat[0][1]*yt + mat[0][2]*z;
        y = mat[1][0]*xt + mat[1][1]*yt + mat[1][2]*z;
        z = mat[2][0]*xt + mat[2][1]*yt + mat[2][2]*z;
        return this; 
    }
    
    /** rotate the vector around the axis */
    public IVec rot(double axisX, double axisY, double axisZ, double angle){
        double mat[][] = new double[3][3];
	double len = Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);
	if(len==0) return this; // do nothing
	axisX/=len; axisY/=len; axisZ/=len;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double icos = 1-cos;
        
	// right-handed coordinates
	mat[0][0] = axisX*axisX*icos + cos;
        mat[0][1] = axisX*axisY*icos - axisZ*sin;
        mat[0][2] = axisX*axisZ*icos + axisY*sin;
        mat[1][0] = axisY*axisX*icos + axisZ*sin;
        mat[1][1] = axisY*axisY*icos + cos;
        mat[1][2] = axisY*axisZ*icos - axisX*sin;
        mat[2][0] = axisZ*axisX*icos - axisY*sin;
        mat[2][1] = axisZ*axisY*icos + axisX*sin;
        mat[2][2] = axisZ*axisZ*icos + cos;
	
	// left-handed coordinates
	// mat[0][0] = axisX*axisX*icos + cos;
        // mat[0][1] = axisX*axisY*icos + axisZ*sin;
        // mat[0][2] = axisX*axisZ*icos - axisY*sin;
        // mat[1][0] = axisY*axisX*icos - axisZ*sin;
        // mat[1][1] = axisY*axisY*icos + cos;
        // mat[1][2] = axisY*axisZ*icos + axisX*sin;
        // mat[2][0] = axisZ*axisX*icos + axisY*sin;
        // mat[2][1] = axisZ*axisY*icos - axisX*sin;
        // mat[2][2] = axisZ*axisZ*icos + cos;
	
        double xt=x;
        double yt=y;
        x = mat[0][0]*xt + mat[0][1]*yt + mat[0][2]*z;
        y = mat[1][0]*xt + mat[1][1]*yt + mat[1][2]*z;
        z = mat[2][0]*xt + mat[2][1]*yt + mat[2][2]*z;
        return this; 
    }
    
    public IVec rot(IVecI axis, double angle){ return rot(axis.get(), angle); }
    //public IVec rot(IVecI axis, IDouble angle){ return rot(axis.get(), angle.x); }
    public IVec rot(IVecI axis, IDoubleI angle){ return rot(axis.get(), angle.x()); }
    
    
    /** rotation on xy-plane */
    public IVec rot(double angle){
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double xt=x;
        x = cos*xt -sin*y;
        y = sin*xt + cos*y;
        return this; 
    }
    /** rotation on xy-plane */
    public IVec rot(IDoubleI angle){ return rot(angle.x()); }
    
    
    public IVec rot(IVec center, IVec axis, double angle){
	if(center==this) return this;
	return sub(center).rot(axis,angle).add(center);
    }
    public IVec rot(double centerX, double centerY, double centerZ, double axisX, double axisY, double axisZ, double angle){
	return sub(centerX,centerY,centerZ).rot(axisX,axisY,axisZ,angle).add(centerX,centerY,centerZ);
    }
    public IVec rot(IVecI center, IVecI axis, double angle){
	return rot(center.get(),axis.get(),angle);
    }
    public IVec rot(IVecI center, IVecI axis, IDoubleI angle){
	return rot(center.get(),axis.get(),angle.x());
    }
    
    // test this method later!!!
    /** rotation around axis towards destination direction */
    public IVec rot(IVec axis, IVec destDir){
	return rot(axis,destDir.cross(axis).angle(cross(axis)));
    }
    /** rotation around axis towards destination direction */
    public IVec rot(IVecI axis, IVecI destDir){ return rot(axis.get(),destDir.get()); }
    /** rotation around axis and center towards destination point */
    public IVec rot(IVec center, IVec axis, IVec destPt){
	if(center==this) return this;
	return sub(center).rot(axis,destPt.dif(center)).add(center);
    }
    /** rotation around axis and center towards destination point */
    public IVec rot(IVecI center, IVecI axis, IVecI destPt){
	return rot(center.get(), axis.get(), destPt.get());
    }
    
    
    /** rotation on xy-plane; alias of rot(double) */
    public IVec rot2(double angle){ return rot(angle); }
    /** rotation on xy-plane; alias of rot(IDoubleI) */
    public IVec rot2(IDoubleI angle){ return rot(angle); }
    
    /** rotation on xy-plane */
    public IVec rot2(IVec center, double angle){
	if(center==this){ return this; } return sub(center).rot(angle).add(center);
    }
    /** rotation on xy-plane */
    public IVec rot2(double centerX, double centerY, double angle){
	return sub(centerX,centerY,0).rot(angle).add(centerX,centerY,0);
    }
    /** rotation on xy-plane */
    public IVec rot2(IVecI center, double angle){ return rot2(center.get(),angle); }
    /** rotation on xy-plane */
    public IVec rot2(IVecI center, IDoubleI angle){ return rot2(center.get(),angle.x()); }
    
    // test this method later!!!
    /** rotation on xy-plane towards destDir */ 
    public IVec rot2(IVec destDir){ return rot(destDir.cross(zaxis).angle(cross(zaxis))); }
    /** rotation on xy-plane towards destDir */
    public IVec rot2(IVecI destDir){ return rot2(destDir.get()); }
    /** rotation on xy-plane towards destPt */
    public IVec rot2(IVec center, IVec destPt){
	if(center==this){ return this; } return sub(center).rot2(destPt.dif(center)).add(center);
    }
    /** rotation on xy-plane towards destPt */
    public IVec rot2(IVecI center, IVecI destPt){ return rot2(center.get(), destPt.get()); }
    
    
    
    /** alias of mul */
    public IVec scale(IDoubleI f){ return mul(f); }
    public IVec scale(double f){ return mul(f); }

    /** scale from the center */
    public IVec scale(IVec center, double f){
	if(center==this) return this;
	return sub(center).scale(f).add(center);
    }
    /** scale from the center */
    public IVec scale(double centerX, double centerY, double centerZ, double f){
	return sub(centerX,centerY,centerZ).scale(f).add(centerX, centerY, centerZ);
    }
    /** scale from the center */
    public IVec scale(IVecI center, double f){
	return scale(center.get(),f);
    }
    /** scale from the center */
    public IVec scale(IVecI center, IDoubleI f){
	return scale(center.get(),f.x());
    }
    
    /** scale only in 1 direction */
    public IVec scale1d(IVec axis, double f){
	//IVec n = axis.dup().unit();
	//n.mul(this.dot(n));
	//IVec t = this.dif(n);
	//return this.set(n.mul(f).add(t));
	return this.add(axis, this.dot(axis)/axis.len2()*(f-1.));
    }
    /** scale only in 1 direction */
    public IVec scale1d(double axisX, double axisY, double axisZ, double f){
	//double len = Math.sqrt(axisX*axisX+axisY*axisY+axisZ*axisZ);
	//axisX/=len; axisY/=len; axisZ/=len;
	//double dt = dot(axisX,axisY,axisZ);
	//axisX*=dt; axisY*=dt; axisZ*=dt;
	//x = axisX*f + x-axisX;
	//y = axisY*f + y-axisY;
	//z = axisZ*f + z-axisZ;
	double d = this.dot(axisX,axisY,axisZ)/(axisX*axisX+axisY*axisY+axisZ*axisZ)*(f-1.);
	x+=axisX*d; y+=axisY*d; z+=axisZ*d;
	return this;
    }
    
    /** scale only in 1 direction */
    public IVec scale1d(IVecI axis, double f){ return scale1d(axis.get(),f); }
    /** scale only in 1 direction */
    public IVec scale1d(IVecI axis, IDoubleI f){ return scale1d(axis.get(),f.x()); }
    /** scale only in 1 direction */
    public IVec scale1d(IVec center, IVec axis, double f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    /** scale only in 1 direction */
    public IVec scale1d(IVecI center, IVecI axis, double f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    /** scale only in 1 direction */
    public IVec scale1d(double centerX, double centerY, double centerZ,
			double axisX, double axisY, double axisZ, double f){
	return sub(centerX,centerY,centerZ).scale1d(axisX,axisY,axisZ,f).add(centerX,centerY,centerZ);
    }
    /** scale only in 1 direction */
    public IVec scale1d(IVecI center, IVecI axis, IDoubleI f){ return scale1d(center,axis,f.x()); }
    
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IVec ref(IVec planeDir){
	//planeDir = planeDir.dup().unit();
	//return add(planeDir.mul(dot(planeDir)*-2));
	return add(planeDir.dup().mul(dot(planeDir)/planeDir.len2()*-2));
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IVec ref(double planeX, double planeY, double planeZ){
	double d = dot(planeX,planeY,planeZ)/(planeX*planeX+planeY*planeY+planeZ*planeZ)*-2;
	x += planeX*d;
	y += planeY*d;
	z += planeZ*d;
	return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IVec ref(IVecI planeDir){ return ref(planeDir.get()); }
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane at the center */
    public IVec ref(IVec center, IVec planeDir){
	if(center==this) return this;
	return sub(center).ref(planeDir).add(center);
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane at the center */
    public IVec ref(double centerX, double centerY, double centerZ, double planeX, double planeY, double planeZ){
	return sub(centerX,centerY,centerZ).ref(planeX,planeY,planeZ).add(centerX,centerY,centerZ);
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane at the center */
    public IVec ref(IVecI center, IVecI planeDir){
	return ref(center.get(),planeDir.get());
    }
    
    /** alias of ref */
    public IVec mirror(IVec planeDir){ return ref(planeDir); }
    /** alias of ref */
    public IVec mirror(double planeX, double planeY, double planeZ){ return ref(planeX,planeY,planeZ); }
    /** alias of ref */
    public IVec mirror(IVecI planeDir){ return ref(planeDir); }
    /** alias of ref */
    public IVec mirror(IVec center, IVec planeDir){ return ref(center,planeDir); }
    /** alias of ref */
    public IVec mirror(double centerX, double centerY, double centerZ, double planeX, double planeY, double planeZ){
	return ref(centerX,centerY,centerZ,planeX,planeY,planeZ);
    }
    /** alias of ref */
    public IVec mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    public IVec shear(double sxy, double syx, double syz, double szy, double szx, double sxz){
	double tx,ty,tz;
	tx =     x + sxy*y + sxz*z;
	ty = syx*x +     y + syz*z;
	tz = szx*x + szy*y +     z;
	x = tx; y = ty; z = tz;
	return this;
    }
    public IVec shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		      IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	return shear((sxy==null)?0:sxy.x(), (syx==null)?0:syx.x(),
		     (syz==null)?0:syz.x(), (szy==null)?0:szy.x(),
		     (szx==null)?0:szx.x(), (sxz==null)?0:sxz.x());
	// should I really accept null as zero?
    }
    public IVec shear(IVecI center, double sxy, double syx, double syz, double szy, double szx, double sxz){
	if(center==this) return this;
	return sub(center).shear(sxy,syx,syz,szy,szx,sxz).add(center);
    }
    public IVec shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz, IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	return shear(center,sxy.x(),syx.x(),syz.x(),szy.x(),szx.x(),sxz.x());
    }
    
    
    public IVec shearXY(double sxy, double syx){ return shear(sxy,syx,0,0,0,0); }
    public IVec shearXY(IDoubleI sxy, IDoubleI syx){ return shearXY(sxy.x(),syx.x()); }
    public IVec shearXY(IVecI center, double sxy, double syx){
	if(center==this){ return this; } return sub(center).shearXY(sxy,syx).add(center);
    }
    public IVec shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	return shearXY(center,sxy.x(),syx.x());
    }
    
    public IVec shearYZ(double syz, double szy){ return shear(0,0,syz,szy,0,0); }
    public IVec shearYZ(IDoubleI syz, IDoubleI szy){ return shearYZ(syz.x(),szy.x()); }
    public IVec shearYZ(IVecI center, double syz, double szy){
	if(center==this){ return this; } return sub(center).shearYZ(syz,szy).add(center);
    }
    public IVec shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	return shearYZ(center,syz.x(),szy.x());
    }
    
    public IVec shearZX(double szx, double sxz){ return shear(0,0,0,0,szx,sxz); }
    public IVec shearZX(IDoubleI szx, IDoubleI sxz){ return shearZX(szx.x(),sxz.x()); }
    public IVec shearZX(IVecI center, double szx, double sxz){
	if(center==this){ return this; } return sub(center).shearZX(szx,sxz).add(center);
    }
    public IVec shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	return shearZX(center,szx.x(),sxz.x());
    }
    
    public IVec translate(double x, double y, double z){ return add(x,y,z); }
    public IVec translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IVec translate(IVecI v){ return add(v); }
    
    
    
    public IVec transform(IMatrix3I mat){ return set(mat.mul(this)); }
    public IVec transform(IMatrix4I mat){ return set(mat.mul(this)); }
    
    public IVec transform(IVec xvec, IVec yvec, IVec zvec){
        double tx, ty, tz;
        tx = xvec.x*x + yvec.x*y + zvec.x*z;
        ty = xvec.y*x + yvec.y*y + zvec.y*z;
        tz = xvec.z*x + yvec.z*y + zvec.z*z;
        x = tx; y = ty; z = tz;
        return this;
    }

    public IVec transform(IVecI xvec, IVecI yvec, IVecI zvec){
	return transform(xvec.get(),yvec.get(),zvec.get());
    }
    
    public IVec transform(IVec xvec, IVec yvec, IVec zvec, IVec translate){
	return transform(xvec,yvec,zvec).add(translate);
	//double tx, ty, tz;
        //tx = xvec.x*x + yvec.x*y + zvec.x*z + translate.x;
        //ty = xvec.y*x + yvec.y*y + zvec.y*z + translate.y;
        //tz = xvec.z*x + yvec.z*y + zvec.z*z + translate.z;
        //x = tx; y = ty; z = tz;
        //return this;
    }
    
    public IVec transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	return transform(xvec.get(),yvec.get(),zvec.get(),translate.get());
    }
    
    
    /** mv() is alias of add() */
    public IVec mv(double vx, double vy, double vz){ return add(vx,vy,vz); }
    public IVec mv(IDoubleI vx, IDoubleI vy, IDoubleI vz){ return add(vx,vy,vz); }
    public IVec mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IVec cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IVec cp(IVec v){
	//return dup().add(v);
	return new IVec(x+v.x, y+v.y, z+v.z); 
    }
    public IVec cp(double vx, double vy, double vz){
	return new IVec(x+vx, y+vy, z+vz); 
    }
    public IVec cp(IDoubleI vx, IDoubleI vy, IDoubleI vz){ return dup().add(vx,vy,vz); }
    public IVec cp(IVecI v){ return dup().add(v); }
    
    
    
    /**********************************************************************************
     * methods creating new instance
     *********************************************************************************/
    /** create a new instance of difference */
    public IVec dif(IVec v){
	//return dup().sub(v);
	return new IVec(x-v.x, y-v.y, z-v.z);
    }
    /** create a new instance of difference */
    public IVec dif(double vx, double vy, double vz){
	//return dup().sub(vx,vy,vz);
	return new IVec(x-vx, y-vy, z-vz);
    }
    /** create a new instance of difference */
    public IVec dif(IVecI v){ return dup().sub(v); }
    /** create a new instance of difference */
    public IVec diff(IVec v){ return dif(v); }
    /** create a new instance of difference */
    public IVec diff(double vx, double vy, double vz){ return dif(vx,vy,vz); }
    /** create a new instance of difference */
    public IVec diff(IVecI v){ return dif(v); }
    /** create a new instance of midpoint */
    public IVec mid(IVec v){
	//return dup().add(v).div(2);
	return new IVec( (x+v.x)/2, (y+v.y)/2, (z+v.z)/2 );
    }
    /** create a new instance of midpoint */
    public IVec mid(double vx, double vy, double vz){
	//return dup().add(vx,vy,vz).div(2);
	return new IVec( (x+vx)/2, (y+vy)/2, (z+vz)/2 );
    }
    /** create a new instance of midpoint */
    public IVec mid(IVecI v){ return dup().add(v).div(2); }
    /** create a new instance of summation */
    public IVec sum(IVec v){
	//return dup().add(v);
	return new IVec(x+v.x, y+v.y, z+v.z);
    }
    /** create a new instance of summation */
    public IVec sum(double vx, double vy, double vz){
	return new IVec(x+vx, y+vy, z+vz);
    }
    /** create a new instance of summation */
    public IVec sum(IVecI v){ return dup().add(v); }
    /** create a new instance of summation */
    public IVec sum(IVec... v){
	IVec ret = this.dup(); for(IVec vi: v) ret.add(vi); return ret;
    }
    /** create a new instance of summation */
    public IVec sum(IVecI... v){
	IVec ret = this.dup(); for(IVecI vi: v) ret.add(vi); return ret;
    }
    
    /** create a new instance of bisector */
    public IVec bisect(IVec v){ return dup().unit().add(v.dup().unit()); }
    /** create a new instance of bisector */
    public IVec bisect(double vx, double vy, double vz){
	double len = Math.sqrt(vx*vx + vy*vy + vz*vz);
	return dup().unit().add(vx/len,vy/len,vz/len);
    }
    /** create a new instance of bisector */
    public IVec bisect(IVecI v){ return bisect(v.get()); }
    
    /**
       weighted sum, creating new instance
    */
    /** create a new instance of weighted summation */
    public IVec sum(IVec v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    /** create a new instance of weighted summation */
    public IVec sum(IVec v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }

    /** create a new instance of weighted summation */
    public IVec sum(IVecI v2, double w1, double w2){ return sum(v2.get(),w1,w2); }
    /** create a new instance of weighted summation */
    public IVec sum(IVecI v2, double w2){ return sum(v2.get(),w2); }
    
    /** create a new instance of weighted summation */
    public IVec sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return sum(v2.get(),w1.x(),w2.x()); }
    /** create a new instance of weighted summation */
    public IVec sum(IVecI v2, IDoubleI w2){ return sum(v2.get(),w2.x()); }
    
    
    
    /** checking x, y, and z is valid number (not Infinite, nor NaN). */
    public boolean isValid(){
	if(!IDouble.isValid(x)){ IOut.err("invalid x ("+x+")"); return false; }
	if(!IDouble.isValid(y)){ IOut.err("invalid y ("+y+")"); return false; }
	if(!IDouble.isValid(z)){ IOut.err("invalid z ("+z+")"); return false; }
	return true;
    }
    
    public String toString(){ return "("+String.valueOf(x)+","+String.valueOf(y)+","+String.valueOf(z)+")"; }
    
    
    public boolean isParallel(IVecI v){ return isParallel(v, IConfig.angleTolerance); }
    public boolean isParallel(IVecI v, double angleTolerance){
        return Math.abs(dot(v.get())/(len()*v.get().len())) > Math.cos(angleTolerance);
    }
    
    public boolean isPerpendicular(IVecI v){ return isPerpendicular(v, IConfig.angleTolerance); }
    public boolean isPerpendicular(IVecI v, double angleTolerance){
	return Math.abs(dot(v)) < len()*v.len()*Math.cos(angleTolerance);
    }
    
    public boolean isStraight(IVecI v1, IVecI v2){
        //return isStraight(v1,v2,IConfig.angleTolerance);
	return isStraight(v1,v2,IConfig.tolerance);
    }
    
    public boolean isStraight(IVecI v1, IVecI v2, double tolerance){
        //return v1.get().dif(this).isParallel(v2.get().dif(v1),angleReso);
	return distToLine(v2.get().dif(v1),v1)<tolerance; // should it calculate mean line dir, not only v1&v2?
    }

    /** determine if it's on & inside the line  */
    public boolean isOnLine(IVecI v1, IVecI v2){ return isOnLine(v1,v2,IConfig.tolerance); }
    /** determine if it's on & inside the line  */
    public boolean isOnLine(IVecI v1, IVecI v2, double tolerance){
	if(!isStraight(v1,v2,tolerance)) return false;
	IVec dif1 = dif(v1);
	IVec dif2 = v2.get().dif(v1);
	double len2 = dif2.len();
	double dt = dif1.dot(dif2)/len2;
	if(dt < -tolerance || dt > len2+tolerance) return false;
	return true;
    }
    
    /** alias of isOnLine */
    public boolean isOnSegment(IVecI v1, IVecI v2){ return isOnLine(v1,v2); }
    /** alias of isOnLine */
    public boolean isOnSegment(IVecI v1, IVecI v2, double tolerance){ return isOnLine(v1,v2,tolerance); }
    
    
    public boolean isInside2d(IVecI[] pts){
	// should IVec2 has isInside(projectionDir, IVecI[] pts)?
	IVec2I[] pts2 = new IVec2I[pts.length];
	for(int i=0; i<pts.length; i++) pts2[i] = pts[i].to2d();
	return isInside2d(pts2);
    }
    public boolean isInside2d(IVec2I[] pts){ return to2d().isInside(pts); }
    
    public boolean isInside2d(IVecI projectionDir, IVecI[] pts){
	// should IVec2 has isInside(projectionDir, IVecI[] pts)?
	IVec2I[] pts2 = new IVec2I[pts.length];
	for(int i=0; i<pts.length; i++) pts2[i] = pts[i].to2d(projectionDir);
	return isInside2d(projectionDir, pts2);
    }
    public boolean isInside2d(IVecI projectionDir, IVec2I[] pts){
	return to2d(projectionDir).isInside(pts);
    }
    
    public boolean isInsideTriangle(IVecI pt1, IVecI pt2, IVecI pt3){
	double[] coef = this.dif(pt1).projectTo2Vec(pt2.get().dif(pt1), pt3.get().dif(pt1));
	//orig = coef[0] * v1 + coef[1] * v2 + coef[2] * v1.cross(v2); (this is already projected)
	if(coef[0]<0 || coef[0]>1 || coef[1] < 0 || coef[1]>1) return false;
	return true;
    }
    
    
    /** alias of cross. (not unitized) */
    public IVec nml(IVecI v){ return cross(v); }
    /** alias of cross. (not unitized) */
    public IVec nml(IVec v){ return cross(v); }
    /** alias of cross. (not unitized) */
    public IVec nml(double vx, double vy, double vz){ return cross(vx,vy,vz); }
    
    /** create normal vector from 3 points of self, pt1 and pt2 (not unitized) */
    public IVec nml(IVecI pt1, IVecI pt2){
	//return this.dif(pt1).cross(this.dif(pt2)).unit();
	return this.dif(pt1).icross(this.dif(pt2));
    }
    /** create normal vector from 3 points of self, pt1 and pt2 (not unitized) */
    public IVec nml(IVec pt1, IVec pt2){
	//return this.dif(pt1).icross(this.dif(pt2)).unit();
	return this.dif(pt1).icross(this.dif(pt2));
    }
    /** create normal vector from 3 points of self, pt1 and pt2 (not unitized) */
    public IVec nml(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2){
	return this.dif(vx1,vy1,vz1).icross(this.dif(vx2,vy2,vz2));
    }
    
    /** create normal vector from 3 points */
    public static IVecI nml(IVecI pt1, IVecI pt2, IVecI pt3){ return pt1.nml(pt2,pt3); }
    /** create normal vector from 3 points */
    public static IVec nml(IVec pt1, IVec pt2, IVec pt3){ return pt1.nml(pt2,pt3); }
    /** create normal vector from 3 points */
    public static IVec nml(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2,
			   double vx3, double vy3, double vz3){
	return new IVec(vx1,vy1,vz1).nml(vx2,vy2,vz2,vx3,vy3,vz3);
    }
    /** create normal vector from 3 points */
    public static IVecI getNormal(IVecI pt1, IVecI pt2, IVecI pt3){ return nml(pt1,pt2,pt3); }
    /** create normal vector from 3 points */
    public static IVec getNormal(IVec pt1, IVec pt2, IVec pt3){ return nml(pt1,pt2,pt3); }
    public static IVec getNormal(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2,
				 double vx3, double vy3, double vz3){
	return nml(vx1,vy1,vz1,vx2,vy2,vz2,vx3,vy3,vz3);
    }
    
    /** alias of nml */
    public IVecI getNormal(IVecI pt2, IVecI pt3){ return nml(pt2,pt3); }
    /** alias of nml */
    public IVec getNormal(IVec pt2, IVec pt3){ return nml(pt2,pt3); }
    
    
    public IVec projectToPlane(IVecI planeNormal){
        return projectToPlane(planeNormal,planeNormal);
    }

    public IVec projectToPlane(IVecI projectDir, IVecI planeNormal){
	double ipRatio = dot(planeNormal)/projectDir.dot(planeNormal);
	x -= ipRatio*projectDir.x();
	y -= ipRatio*projectDir.y();
	z -= ipRatio*projectDir.z();
	return this;
    }
    
    
    public IVec projectToPlane(IVecI projectDir, IVecI planeNormal, IVecI planePoint){
	if(planePoint==this) return this;
	return sub(planePoint).projectToPlane(projectDir,planeNormal).add(planePoint);
    }

    
    public IVec projectToTriangle(IVecI pt1, IVecI pt2, IVecI pt3){
	IVec dir1 = pt2.get().dif(pt1);
	IVec dir2 = pt3.get().dif(pt1);
	double[] coef = sub(pt1).projectTo2Vec(dir1,dir2);
	//orig = coef[0] * v1 + coef[1] * v2 + coef[2] * v1.cross(v2); (this is already projected)
	add(pt1);
	if( coef[0] >= 0 ){
	    if(coef[1] >= 0){
		if( (coef[0]+coef[1]) <= 1 ){ // inside triangle
		    return this;
		}
		return projectToSegment(pt2,pt3);
	    }
	    return projectToSegment(pt1, pt2);
	}
	return projectToSegment(pt1, pt3);
    }
    
    
    /** project to an infinite line. the vector changes itself.  */
    // create new point? or set it to itself? -> itself
    public IVec projectToLine(IVecI linePt, IVecI lineDir){
	if(linePt==this) return this;
	//IVec diff = this.dif(linePt);
	//double dot = diff.dot(lineDir)/lineDir.len();
	//return diff.set(lineDir.get()).len(dot).add(linePt);
	double dot = sub(linePt).dot(lineDir)/lineDir.len2();
	return set(lineDir).mul(dot).add(linePt);
    }
    
    /** project to a line segment. this project itself */
    public IVec projectToSegment(IVecI linePt1, IVecI linePt2){
	if(linePt1==this || linePt2==this) return this;
	IVec lineDir = linePt2.get().dif(linePt1);
	double dot = sub(linePt1).dot(lineDir)/lineDir.len2();
	if(dot<0.0) dot=0.0; else if(dot>1.0) dot=1.0;
	return set(lineDir).mul(dot).add(linePt1);
    }
    
    /** project the vector to the plane defined by two input vector and decompose vector to two vector and another perpendicular vector and returns coefficient of them.
	relationship of them is like below.
	this = return[0] * v1 + return[1] * v2 + return[2] * v1.cross(v2);
	
	@return array of three double number, first is coefficient of uvec, second is of vvec and third is of uvec.cross(vvec)
    */
    public double[] projectTo2Vec(IVecI v1, IVecI v2){
	double coef[] = new double[3];
        // this = coef[0]*v1 + coef[1]*v2 + coef[2]*(v1 x v2)
        
        // project to a plane defined by v1 and v2
        IVec op = v1.get().cross(v2);
        double opnrm2 = op.len2();
        if(opnrm2==0){ // added 090422 // when v1 and v2 are parallel or one of them zero
	    //if(v1.len2()==0){}
	    //else if(v2.len2()==0){} 
	    return null;
	}
        coef[2] = this.dot(op)/opnrm2;
        this.sub(op.mul(coef[2]));
	
	IVec v1n = v1.get(); if(v1n==v1) v1n = v1n.dup();
	IVec v2n = v2.get(); if(v2n==v2) v2n = v2n.dup();
	
	v1n.unit();
	v2n.unit();
	
        double ip12 = v1n.dot(v2n);
        double iip122 = 1-ip12*ip12;
        if(iip122==0) return null; // added 090422
	
	double ip1 = this.dot(v1n);
        double ip2 = this.dot(v2n);
        coef[0] = ((ip1-ip2*ip12)/iip122)/v1.len();
	coef[1] = ((ip2-ip1*ip12)/iip122)/v2.len();
	return coef;
    }
    
    
    /**
       project this vector to the input vector and returns projection coefficient.
       @param v a vector for this vector to project on.
    */
    public double projectToVec(IVecI v){
	double coef = this.dot(v)/v.len2();
	this.set(v).mul(coef);
	return coef;
    }
    
    /** decompose the vector to two input vectors and another perpendicular vector of those two and returns three decomposed vector. the vector itself doensn't change.
	this = return[0] + return[1] + return[2];
	
	@return array of three decomposed vectors
    */
    public IVec[] decompose(IVecI uaxis, IVecI vaxis){
	
	IVec uvec = uaxis.get(); if(uvec==uaxis) uvec = uvec.dup();
	IVec vvec = vaxis.get(); if(vvec==vaxis) vvec = vvec.dup();
        IVec wvec = uvec.cross(vvec);
	double wlen = wvec.len();
	if(wlen==0){
	    IOut.err("two vectors are parallel");
	    return null;
	}
	
	wvec.div(wlen);
	wvec.mul(this.dot(wvec));
	IVec v = this.dup().sub(wvec);
	
	uvec.unit();
	vvec.unit();
	
        double ip12 = uvec.dot(vvec);
        double iip122 = 1-ip12*ip12;
        if(iip122==0){
	    IOut.err("two vectors are in same direction");
	    return null; // added 090422
	}
        double ip1 = v.dot(uvec);
        double ip2 = v.dot(vvec);
	
	uvec.mul((ip1-ip2*ip12)/iip122);
	vvec.mul((ip2-ip1*ip12)/iip122);
	
	return new IVec[]{ uvec, vvec, wvec };
    }
    
    
    /** distance to an infinite line */
    public double distToLine(IVecI lineDir, IVecI linePt){
	return perpendicularVecToLine(lineDir,linePt).len();
    }
    
    /** distance to a line dir */
    public double distToLine(IVecI lineDir){
	return perpendicularVecToLine(lineDir).len();
    }
    
    /** distance to a line segment */
    public double distToSegment(IVecI linePt1, IVecI linePt2){
	//IVec lineDir = linePt2.get().dif(linePt1);
	//IVec projPt = perpendicularVecToLine(lineDir,linePt1).add(this);
	//double ratio = projPt.sub(linePt1).dot(lineDir)/lineDir.len2();
	//if(ratio<=0.0) return dist(linePt1);
	//if(ratio>=1.0) return dist(linePt2);
	//return dist(projPt.add(linePt1));
	if(linePt1==this || linePt2==this) return 0;
	IVec lineDir = linePt2.get().dif(linePt1);
	double len2 = lineDir.len2();
	if(len2==0) return dist(linePt1); // linePt1 and linePt2 are same point // added 20140401
	IVec dif = this.dif(linePt1);
	double dot = dif.dot(lineDir)/len2;
	if(dot<0.0) dot=0.0; else if(dot>1.0) dot=1.0;
	return lineDir.mul(dot).dist(dif);
    }
    
    /** ratio of projected point between two points (line segment). 0.0 is at linePt1, 1.0 is at linePt2. */
    public double ratioOnSegment(IVecI linePt1, IVecI linePt2){
	IVec lineDir = linePt2.get().dif(linePt1);
	double len2 = lineDir.len2();
	if(lineDir.len2()==0){ // linePt1 and linePt2 are same
	    return 0; // just return one number
	}
	return perpendicularVecToLine(lineDir,linePt1).add(this).sub(linePt1).dot(lineDir)/lineDir.len2();
    }
    
    /** distance to a plane */
    //public double distanceToPlane(IVecI planeDir, IVecI planePt){
    public double distToPlane(IVecI planeDir, IVecI planePt){
	//return Math.abs(this.dif(planePt).dotP(planeDir.get())/planeDir.get().len());
	double plen = planeDir.len();
	if(plen==0){ return dist(planePt); }
	return Math.abs(dif(planePt).dot(planeDir)/plen);
    }

    /** distance to a triangle */
    public double distToTriangle(IVecI pt1, IVecI pt2, IVecI pt3){
	IVec dir1 = pt2.get().dif(pt1);
	IVec dir2 = pt3.get().dif(pt1);

	if(dir1.len2()==0){
	    if(dir2.len()==0){ // all same point
		return dist(pt1);
	    }
	    return distToSegment(pt1,pt3);
	}
	if(dir2.len2()==0){
	    return distToSegment(pt1,pt2);
	}
	IVec cross = dir1.cross(dir2);
	if(cross.len2()==0){
	    if(dir1.dot(dir2)>=0){ // same dir
		if(dir1.len2()>dir2.len2()) return distToSegment(pt1,pt2);
		return distToSegment(pt1,pt3);
	    }
	    // dir1/dir2 opposite 
	    return distToSegment(pt2,pt3);
	}
	
	double[] coef = dif(pt1).projectTo2Vec(dir1,dir2);
	
	//this = coef[0] * v1 + coef[1] * v2 + coef[2] * v1.cross(v2);
	if( coef[0] >= 0 ){
	    if(coef[1] >= 0){
		if( (coef[0]+coef[1]) <= 1 ){ // inside triangle
		    //return dir1.icross(dir2).len()*Math.abs(coef[2]);
		    return cross.len()*Math.abs(coef[2]);
		}
		return distToSegment(pt2,pt3);
	    }
	    return distToSegment(pt1, pt2);
	}
	return distToSegment(pt1, pt3);
    }
    
    
    /**
       create a new vector from this point to the line in parpendicular direction.
    */
    public IVec perpendicularVecToLine(IVecI lineDir, IVecI linePt){
	double len2 = lineDir.len2();
	if(len2==0){
	    IOut.err("line direction is zero");
	    return linePt.get().dif(this);
	}
	IVec ldir = lineDir.get().cp();
	IVec dif = dif(linePt);
	return ldir.mul(ldir.dot(dif)/len2).sub(dif);
    }
    
    /**
       create a new vector from line to this point perpendicular to the line dir
    */
    public IVec perpendicularVecToLine(IVecI lineDir){
	IVec ldir = lineDir.get().dup();
	double len2 = ldir.len2();
	if(len2==0){
	    IOut.err("lineDir is a zero vector"); 
	    return new IVec(); // zero vector
	}
	//return ldir.mul(-ldir.dot(this)/ldir.len2()).add(this);
	return ldir.mul(ldir.dot(this)/len2).sub(this);
    }
    
    
    public boolean isOnPlane(IVecI planePt1, IVecI planePt2, IVecI planePt3){
	return isOnPlane(planePt1,planePt2,planePt3,IConfig.tolerance);
    }
    public boolean isOnPlane(IVecI planeDir, IVecI planePt){
	return isOnPlane(planeDir,planePt,IConfig.tolerance);
    }
    public boolean isOnPlane(IVecI planePt1, IVecI planePt2, IVecI planePt3, double tolerance){
	return isOnPlane(getNormal(planePt1,planePt2,planePt3),planePt1,tolerance);
    }
    public boolean isOnPlane(IVecI planeDir, IVecI planePt, double tolerance){
	return distToPlane(planeDir,planePt)<tolerance;
    }
    
    
    public boolean isOnTriangle(IVecI pt1, IVecI pt2, IVecI pt3, double tolerance){
	IVec dir1 = pt2.get().dif(pt1);
	IVec dir2 = pt3.get().dif(pt1);
	double[] coef = dif(pt1).projectTo2Vec(dir1,dir2);
	//this = coef[0] * v1 + coef[1] * v2 + coef[2] * v1.cross(v2);
	double len1 = dir1.len();
	double len2 = dir2.len();
	if(coef[0]*len1 < -tolerance) return false;
	if(coef[1]*len2 < -tolerance) return false;
	if((coef[0]+coef[1]) > 1.0 + tolerance/(len1>len2?len1:len2)) return false; // use larger length
	if(dir1.icross(dir2).len()*coef[2] > tolerance) return false;
	return true;
    }
    
    public boolean isOnTriangle(IVecI pt1, IVecI pt2, IVecI pt3){ return isOnTriangle(pt1,pt2,pt3,IConfig.tolerance); }
    
    
    /** visualize a vector as an arrow.
	@param root A root position of the arrow.
    */
    public IVectorObject show(IVecI root){ return new IVectorObject(this, root); }
    /** visualize a vector as an arrow. The root position of the arrow is the origin.
     */
    public IVectorObject show(){ return new IVectorObject(this); }
    
    /** visualize a vector as an arrow.
	@param s A server to store the object.
	@param root A root position of the arrow.
    */
    public IVectorObject show(IServerI s, IVecI root){
	return new IVectorObject(s, this, root);
    }
    /** visualize a vector as an arrow. The root position of the arrow is the origin.
	@param s A server to store the object.
     */
    public IVectorObject show(IServerI s){ return new IVectorObject(s, this); }
    
    
    
    /*************************************************************
     * static methods
     *************************************************************/
    
    public static boolean isStraight(IVecI[] pts, double tolerance){
	if(pts==null || pts.length<3 ){
	    IOut.err("input points should be >= 3. currently "+pts==null?0:pts.length);
	    return false;
	}
	IVec p0 = pts[0].get();
	int idx=pts.length-1;
	for(; idx>=0 && p0.eq(pts[idx],tolerance); idx--); // not idential with p0
	if(idx<=0){ return false; } // when all points are same location, let's say it's not straight
	
	IVec dir = pts[idx].get().dif(p0); // from start point to end point
	for(int i=1; i<idx-1; i++){ // should it calculate mean line dir?
	    if(pts[i].get().distToLine(dir, p0) >= tolerance) return false;
	} 
	return true;
    }
    
    public static boolean isStraight(IVecI[] pts){ return isStraight(pts, IConfig.tolerance); }
    
    public static boolean isFlat(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4){
	return pt1.get().isOnPlane(pt2,pt3,pt4);
    }
    
    public static boolean isFlat(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4, double tolerance){
	return pt1.get().isOnPlane(pt2,pt3,pt4,tolerance);
    }
    
    public static boolean isFlat(IVecI[] pts){ return isFlat(pts,IConfig.tolerance); }
    
    public static boolean isFlat(IVecI[] pts, double tolerance){
	if(pts.length<=3) return true;
	if(pts.length==4) return isFlat(pts[0],pts[1],pts[2],pts[3],tolerance);
	IVec pt1 = pts[0].get();
	IVecI pt2 = pts[1];
	int i;
	for(i=2; i<pts.length && pt1.eq(pt2,tolerance); i++){ pt2 = pts[i]; }
	if(i>=pts.length-2) return true; // one point or straight line or triangle
	//i++;
	//if(i==pts.length-1) return true; // triangle
	
	IVecI pt3 = pts[i];
	for(i++; i<pts.length && pt1.isStraight(pt2,pt3,tolerance); i++){ pt3 = pts[i]; }
	if(i>=pts.length-1) return true; // straight line
	
	for(i++; i<pts.length; i++){
	    if(!pt1.isOnPlane(pt2,pt3,pts[i],tolerance)){ return false; }
	}
	return true;
    }
    
    public static boolean isArrayEqual(IVec[] pts1, IVec[] pts2,
				       boolean cyclic, boolean reverse){
	return isArrayEqual(pts1,pts2,cyclic,reverse,IConfig.tolerance);
    }
    public static boolean isArrayEqual(IVec[] pts1, IVec[] pts2,
				       boolean cyclic, boolean reverse, double tolerance){
	
	if(pts1.length!=pts2.length) return false;
	int num = pts1.length;
	if(!cyclic){
	    for(int i=0; i<num; i++)
		if(!pts1[i].eq(pts2[i],tolerance)) if(!reverse) return false;
	    if(!reverse) return true;
	    // reverse
	    for(int i=0; i<num; i++)
		if(!pts1[i].eq(pts2[num-1-i],tolerance)) return false;
	    return true;
	}
	
	// cyclic
	for(int i=0; i<num; i++){
	    boolean same=true;
	    for(int j=0; j<num; j++)
		if(!pts1[j].eq(pts2[(j+i)%num],tolerance)) same=false;
	    if(same) return true;
	}
	if(!reverse) return false;
	
	// reverse
	for(int i=0; i<num; i++){
	    boolean same=true;
	    for(int j=0; j<num; j++)
		if(!pts1[j].eq(pts2[(j+i)%num],tolerance)) same=false;
	    if(same) return true;
	}
	return false;
    }

    /**
       average normal of point array in form of a polyline.
       It return IVec, not IVecI. (Should it return IVecI?)
    */
    public static IVec averageNormal(IVecI[] pts){
	if(pts==null){ IOut.err("pts is null"); return new IVec(0,0,1); /*default*/ }
	
	int n = pts.length;
	if(n<=2){
	    //IOut.err("number of pts is too small: "+pts.length);
	    if(n<=1) return new IVec(0,0,1);
	    IVec dif = pts[1].get().dif(pts[0]);
	    if(dif.isParallel(xaxis)){
		return dif.icross(yaxis).unit();
	    }
	    return dif.icross(xaxis).unit();
	    //IVec nml = pts[1].get().dif(pts[0]).cross(xaxis); /*default*/
	    //if(!nml.eq(origin)) return nml.unit();
	    //return pts[1].get().dif(pts[0]).cross(yaxis).unit(); /*default*/
	}
	
        if(n==3) return pts[1].get().dif(pts[0]).cross(pts[2].get().dif(pts[1])).unit();
        
        IVec nml = new IVec();
        for(int i=0; i<n; i++){
            IVec diff1 = pts[(i+1)%n].get().dif(pts[i]);
            IVec diff2 = pts[(i+2)%n].get().dif(pts[(i+1)%n]);
            //nml.add(diff1.cross(diff2));
	    diff1.icross(diff2);
	    if(diff1.dot(nml)<0){ diff1.neg(); } // when convex & concave
	    nml.add(diff1);
        }
	
	// when all the points are on a stragiht line
	if(nml.eq(origin)) return new IVec(0,0,1); /* default */
	
        return nml.unit();
    }
    
    
    /**
       average normal of point array in form of a matrix.
       It return IVec, not IVecI. (Should it return IVecI?)
    */
    public static IVec averageNormal(IVecI[][] pts){
	if(pts==null || pts.length==0){
	    IOut.err("pts is null"); return new IVec(0,0,1); /*default*/
	}
	
	if(pts.length==1) return averageNormal(pts[0]);
	
	if(pts[0].length==0){
	    IOut.err("pts is null"); return new IVec(0,0,1); /*default*/
	}
	
	if(pts[0].length==1){
	    IVecI[] pts2 = new IVecI[pts.length];
	    for(int i=0; i<pts.length; i++) pts2[i] = pts[i][0];
	    return averageNormal(pts2);
	}
	
	IVec nml = new IVec();
	
	for(int i=0; i<pts.length-1; i++){
	    for(int j=0; j<pts[i].length-1; j++){
		nml.add(pts[i+1][j].dif(pts[i][j]).cross(pts[i][j+1].dif(pts[i][j])));
		nml.add(pts[i+1][j+1].dif(pts[i+1][j]).cross(pts[i][j].dif(pts[i+1][j])));
		nml.add(pts[i][j+1].dif(pts[i+1][j+1]).cross(pts[i+1][j].dif(pts[i+1][j+1])));
		nml.add(pts[i][j].dif(pts[i][j+1]).cross(pts[i+1][j+1].dif(pts[i][j+1])));
	    }
	}
	
	// in case nml is zero
	if(nml.eq(origin)) return new IVec(0,0,1); /* default */
	
        return nml.unit();
    }

    /** calculating centetr of multiple points */
    public static IVec center(IVecI... v){
	IVec cnt = new IVec(v[0]);
	for(int i=1; i<v.length; i++){ cnt.add(v[i]); }
	return cnt.div(v.length);
    }
    
    /** calculate circumcenter; center of circumcircle */
    public static IVec circumcenter(IVecI pt1, IVecI pt2, IVecI pt3){
	IVec d1 = pt2.get().dif(pt1);
	IVec d2 = pt3.get().dif(pt1);
	//IVec[] itxn = intersectPlane(d1, pt1.get().mid(pt2), d2, pt1.get().mid(pt3));
	//if(itxn==null) return null; // 2 dirs are pararell
	//return intersectPlaneAndLine(d1.cross(d2), pt1.get(), itxn[0], itxn[1]);
	
	IVec n = d1.nml(d2);
	d1.rot(n,Math.PI/2);
	d2.rot(n,Math.PI/2);
	IVec mpt1 = pt1.get().mid(pt2);
	IVec mpt2 = pt1.get().mid(pt3);
	IVec itxn = IVec.intersect(mpt1, mpt1.cp(d1), mpt2, mpt2.cp(d2));
	return itxn;
    }

    /** area of a triangle */
    public static double area(IVecI pt1, IVecI pt2, IVecI pt3){
	IVec d1 = pt2.get().dif(pt1);
	IVec d2 = pt3.get().dif(pt1);
	return d1.icross(d2).len()/2;
    }
    
    
    public static IVec[] toIVecArray(IVecI[] array){
	if(array==null) return null;
	IVec[] ret = new IVec[array.length];
	for(int i=0; i<array.length; i++){ ret[i] = array[i].get(); }
	return ret;
    }
    
    
    
    public static IVecI[] offsetNormal(IVecI[] pts, int ptNum, boolean close){
	if(pts==null){ IOut.err("pts is null"); return null; }
	if(pts.length==1){ IOut.err("pts has only one point"); return null; }
	
	IVecI[] normals = new IVecI[ptNum];
	if(ptNum==2){
	    IVec normal = new IVec(0,0,1); // default
	    IVecI diff = pts[1].dif(pts[0]);
	    if(normal.isParallel(diff)){ normal = new IVec(1,0,0); } // another default
	    normals[0] = diff.cross(normal); //normal;
	    normals[1] = diff.cross(normal); //normal;
	    return normals;
	}
	IVecI normal=null, n0=null;
	for(int i=0; i<ptNum; i++){
	    if(i==0){
		if(close){
		    if(!pts[ptNum-2].get().isStraight(pts[i],pts[i+1])){
			normal = pts[ptNum-2].nml(pts[i],pts[i+1]);
		    }
		}
		for(int j=i; j<ptNum-2 && normal==null; j++){
		    if(!pts[j].get().isStraight(pts[j+1],pts[j+2])){
			normal = pts[j].nml(pts[j+1],pts[j+2]);
		    }
		}
		// if all straight
		if(normal==null){
		    normal = new IVec(0,0,1); // default;
		    if(normal.get().isParallel(pts[i+1].dif(pts[i]))){
			normal = new IVec(1,0,0); // default;
		    }
		}
		n0=normal; // to be used at the end in case closed
	    }
	    else if(i<ptNum-1){
		if(!pts[i-1].get().isStraight(pts[i],pts[i+1])){
		    normal = pts[i-1].nml(pts[i],pts[i+1]);
		} //else{} // use previous normal
	    }
	    else{ // i==ptNum-1
		if(close){ normal = n0; } //else{} // use previous normal
	    }
	    normals[i] = normal;
	}
	// align normals // necessary?
	for(int i=1; i<normals.length; i++){
	    if(!close || i<normals.length-1){
		if( normals[i].dot(normals[i-1]) < 0 ) normals[i].neg();
	    }
	}
	return normals;
    }
    
    
    /*************
      offset without normal vector. normal vectors are automatically generated
    *************/
    public static IVecI[] offset(IVecI[] pts, double width){
	if(pts[0].eq(pts[pts.length-1])){
	    IVecI[] ret = offset(pts, pts.length-1,pts.length, width, null, true);
	    ret[pts.length-1] = ret[0].dup();
	    return ret;
	}
	return offset(pts,pts.length,pts.length,width,null,false);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width){
	if(pts[0].eq(pts[pts.length-1])){
	    IVecI[] ret = offset(pts, pts.length-1,pts.length, width, null, true);
	    ret[pts.length-1] = ret[0].dup();
	    return ret;
	}
	return offset(pts,pts.length,pts.length,width,null,false);
    }
    
    public static IVecI[] offset(IVecI[] pts, double width, boolean closed){
	return offset(pts,pts.length,pts.length,width,null,closed);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, boolean closed){
	return offset(pts,pts.length,pts.length,width,null,closed);
    }
    
    // IVec version instead of IVecI
    public static IVec[] offset(IVec[] pts, double width){
	return toIVecArray(offset((IVecI[])pts,width));
    }
    public static IVec[] offset(IVec[] pts, double width, boolean closed){
	return toIVecArray(offset((IVecI[])pts,width,closed));
    }
    
    
    /*************
      offset with one normal vector
    *************/
    public static IVecI[] offset(IVecI[] pts, double width, IVecI planeNormal){
	return offset(pts,width,new IVecI[]{ planeNormal });
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, IVecI planeNormal){
	return offset(pts,width,new IVecI[]{ planeNormal });
    }
    
    public static IVecI[] offset(IVecI[] pts, double width, IVecI planeNormal, boolean closed){
	return offset(pts,width,new IVecI[]{ planeNormal },closed);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, IVecI planeNormal, boolean closed){
	return offset(pts,width,new IVecI[]{ planeNormal },closed);
    }
    
    // IVec version instead of IVecI
    public static IVec[] offset(IVec[] pts, double width, IVecI planeNormal){
	return toIVecArray(offset((IVecI[])pts,width,planeNormal));
    }
    public static IVec[] offset(IVec[] pts, double width, IVecI planeNormal, boolean closed){
	return toIVecArray(offset((IVecI[])pts,width,planeNormal,closed));
    }
    
    
    /*************
      offset with array of normal vectors
    *************/
    public static IVecI[] offset(IVecI[] pts, double width, IVecI[] normals){
	if(pts[0].eq(pts[pts.length-1])){
	    IVecI[] ret = offset(pts, pts.length-1,pts.length, width, normals, true);
	    ret[pts.length-1] = ret[0].dup();
	    return ret;
	}
	return offset(pts, width, normals, false);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, IVecI[] normals){
	if(pts[0].eq(pts[pts.length-1])){
	    IVecI[] ret = offset(pts, pts.length-1,pts.length, width, normals, true);
	    ret[pts.length-1] = ret[0].dup();
	    return ret;
	}
	return offset(pts, width, normals, false);
    }
    
    public static IVecI[] offset(IVecI[] pts, double width, IVecI[] normals, boolean closed){
	return offset(pts,pts.length,pts.length,width,normals,closed);
    }
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, IVecI[] normals, boolean closed){
	return offset(pts,pts.length,pts.length,width,normals,closed);
    }
    
    // IVec version instead of IVecI
    public static IVec[] offset(IVec[] pts, double width, IVecI[] normals){
	return toIVecArray(offset((IVecI[])pts,width,normals));
    }
    public static IVec[] offset(IVec[] pts, double width, IVecI[] normals, boolean closed){
	return toIVecArray(offset((IVecI[])pts,width,normals,closed));
    }
    
    
    
    /**
       offset points
       @param pts points to be offset
       @param ptNum point num to be offset (equals to pts.length or less)
       @param returnNum return array length (cannot be less than ptNum; when more than ptNum, the remaining is filled with null )
       @param width offset width
       @param normals normal vector at each point (number can be less than ptNum; if so, the last member is used for the rest). if normals is null, it's automatically set by offsetNormal method
       @param closed closed polyline or not
    */
    public static IVecI[] offset(IVecI[] pts, int ptNum, int returnNum, double width, IVecI[] normals, boolean closed){
	if(ptNum<2){ IOut.err("ptNum ("+ptNum+") cannot be less than 2"); }
	else if(ptNum>pts.length){ IOut.err("ptNum ("+ptNum+") cannot be more than pts.length ("+pts.length+")"); }
	if(returnNum<ptNum){ returnNum = ptNum; }
	
	IVecI[] pts2 = new IVec[returnNum];
	for(int i=0; i<ptNum; i++) pts2[i] = pts[i].dup();
	if(width==0) return pts2;
	
	if(normals==null || normals.length==0){ normals = offsetNormal(pts, ptNum, closed); }
	
	if(closed){
	    for(int i=0, nidx=0; i<ptNum; i++){
		IVecI off1 = pts[i].dif(pts[(i-1+ptNum)%ptNum]).cross(normals[nidx]).len(width);
		IVecI off2 = pts[(i+1)%ptNum].dif(pts[i]).cross(normals[nidx]).len(width);
		IVecI v = off1.add(off2);
		v.mul(2*width*width/v.len2());
		pts2[i].add(v);
		if(nidx<normals.length-1){ nidx++; }
	    }
	}
	else{
	    int nidx=0;
	    IVecI off0 = pts[1].dif(pts[0]).cross(normals[nidx]).len(width);
	    pts2[0].add(off0);
	    if(nidx<normals.length-1){ nidx++; }
	    for(int i=1; i<ptNum-1; i++){
		IVecI off1 = pts[i].dif(pts[i-1]).cross(normals[nidx]).len(width);
		IVecI off2 = pts[i+1].dif(pts[i]).cross(normals[nidx]).len(width);
		IVecI v = off1.add(off2);
		v.mul(2*width*width/v.len2());
		pts2[i].add(v);
		if(nidx<normals.length-1){ nidx++; }
	    }
	    IVecI off3 = pts[ptNum-1].dif(pts[ptNum-2]).cross(normals[nidx]).len(width);
            pts2[ptNum-1].add(off3);
	}
	return pts2;
    }
    
    /**
       offset points
       @param pts points to be offset
       @param ptNum point num to be offset (equals to pts.length or less)
       @param returnNum return array length (cannot be less than ptNum; when more than ptNum, the remaining is filled with null )
       @param width offset width
       @param normals normal vector at each point (number can be less than ptNum; if so, the last member is used for the rest)
       @param closed closed polyline or not
    */
    public static IVecI[] offset(IVecI[] pts, int ptNum, int returnNum, IDoubleI width, IVecI[] normals, boolean closed){
	if(ptNum<2){ IOut.err("ptNum ("+ptNum+") cannot be less than 2"); }
	else if(ptNum>pts.length){ IOut.err("ptNum ("+ptNum+") cannot be more than pts.length ("+pts.length+")"); }
	if(returnNum<ptNum){ returnNum = ptNum; }
	
	IVecI[] pts2 = new IVec[returnNum];
	for(int i=0; i<ptNum; i++) pts2[i] = pts[i].dup();
	if(width.x()==0) return pts2;
	
	if(normals==null){ normals = offsetNormal(pts, ptNum, closed); }
	
	if(closed){
	    for(int i=0, nidx=0; i<ptNum; i++){
		IVecI off1 = pts[i].dif(pts[(i-1+ptNum)%ptNum]).cross(normals[nidx]).len(width);
		IVecI off2 = pts[(i+1)%ptNum].dif(pts[i]).cross(normals[nidx]).len(width);
		IVecI v = off1.add(off2);
		v.mul(width.dup().pow(2).mul(2).div(v.len2(Ir.i)));
		pts2[i].add(v);
		if(nidx<normals.length-1){ nidx++; }
	    }
	}
	else{
	    int nidx=0;
	    IVecI off0 = pts[1].dif(pts[0]).cross(normals[nidx]).len(width);
	    pts2[0].add(off0);
	    if(nidx<normals.length-1){ nidx++; }
	    for(int i=1; i<ptNum-1; i++){
		IVecI off1 = pts[i].dif(pts[i-1]).cross(normals[nidx]).len(width);
		IVecI off2 = pts[i+1].dif(pts[i]).cross(normals[nidx]).len(width);
		IVecI v = off1.add(off2);
		v.mul(width.dup().pow(2).mul(2).div(v.len2(Ir.i)));
		pts2[i].add(v);
		if(nidx<normals.length-1){ nidx++; }
	    }
	    IVecI off3 = pts[ptNum-1].dif(pts[ptNum-2]).cross(normals[nidx]).len(width);
            pts2[ptNum-1].add(off3);
	}
	return pts2;
    }
    
    
    /*****
	  Old offset methods
    *****/
    /*
    public static IVec[] offset(IVec[] pts, double width, IVecI planeNormal){
	IVecI[] out = offset((IVecI[])pts,width,planeNormal);
	if(out==null) return null;
	IVec[] retval = new IVec[out.length];
	for(int i=0; i<out.length; i++) retval[i] = out[i].get();
	return retval;
    }
    
    public static IVec[] offset(IVec[] pts, double width, IVecI planeNormal, boolean close){
	IVecI[] out = offset((IVecI[])pts,width,planeNormal,close);
	if(out==null) return null;
	IVec[] retval = new IVec[out.length];
	for(int i=0; i<out.length; i++) retval[i] = out[i].get();
	return retval;
    }
    
    public static IVecI[] offset(IVecI[] pts, double width, IVecI planeNormal, boolean close){
	if(!close || pts[0].eq(pts[pts.length-1]) ){ return offset(pts,width,planeNormal); }
	IVecI[] pts2 = new IVecI[pts.length+1];
	for(int i=0; i<pts.length; i++) pts2[i] = pts[i];
	pts2[pts.length] = pts[0].dup();
	IVecI[] out = offset(pts2,width,planeNormal);
	if(out==null) return out;
	IVecI[] retval = new IVecI[pts.length];
	for(int i=0; i<pts.length; i++) retval[i] = out[i]; // one less
	return retval;
    }
    
    public static IVecI[] offset(IVecI[] pts, double width, IVecI planeNormal){
	if(pts==null){ IOut.err("pts is null"); return null; }
	int num = pts.length;
        IVecI[] pts2 = new IVecI[num];
        for(int i=0; i<num; i++) pts2[i] = pts[i].dup();
        if(width==0) return pts2; 
        
        IVecI[] normal = new IVecI[num-1];
        for(int i=0; i<num-1; i++){
            IVecI dir = pts[i+1].dif(pts[i]);
            normal[i] = dir.cross(planeNormal);
            normal[i].len(width);
        }
        if(pts2[0].eq(pts[num-1])){ // in case of closed curve 
            IVecI v = normal[num-2].dup().add(normal[0]);
            v.mul(2*width*width/v.len2());
            pts2[0].add(v);
            pts2[num-1].add(v);
        }
        else{
            pts2[0].add(normal[0]);
            pts2[num-1].add(normal[num-2]);
        }
	for(int i=1; i<num-1; i++){
            IVecI v = normal[i-1].dup().add(normal[i]);
            v.mul(2*width*width/v.len2());
            pts2[i].add(v);
        }
        return pts2;
    }
    
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, IVecI planeNormal, boolean close){
	if(!close || pts[0].eq(pts[pts.length-1]) ){
	    return offset(pts,width,planeNormal);
	}
	IVecI[] pts2 = new IVecI[pts.length+1];
	for(int i=0; i<pts.length; i++) pts2[i] = pts[i];
	pts2[pts.length] = pts[0].dup();
	IVecI[] out = offset(pts2,width,planeNormal);
	if(out==null) return out;
	IVecI[] retval = new IVecI[pts.length];
	for(int i=0; i<pts.length; i++) retval[i] = out[i]; // one less
	return retval;
    }
    
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, IVecI planeNormal){
	if(pts==null){ IOut.err("pts is null"); return null; }
	int num = pts.length;
        IVecI[] pts2 = new IVecI[num];
        for(int i=0; i<num; i++) pts2[i] = pts[i].dup();
        if(width.x()==0) return pts2; 
        
        IVecI[] normal = new IVecI[num-1];
        for(int i=0; i<num-1; i++){
            IVecI dir = pts[i+1].dif(pts[i]);
            normal[i] = dir.cross(planeNormal);
            normal[i].len(width);
        }
        if(pts2[0].eq(pts[num-1])){ // in case of closed curve 
            IVecI v = normal[num-2].dup().add(normal[0]);
            v.mul(width.dup().pow(2).mul(2).div(v.len2(Ir.i)));
            pts2[0].add(v);
            pts2[num-1].add(v);
        }
        else{
            pts2[0].add(normal[0]);
            pts2[num-1].add(normal[num-2]);
        }
	for(int i=1; i<num-1; i++){
            IVecI v = normal[i-1].dup().add(normal[i]);
            v.mul(width.dup().pow(2).mul(2).div(v.len2(Ir.i)));
            pts2[i].add(v);
        }
        return pts2;
    }
    
    public static IVecI[] offset(IVecI[] pts, IVecI[] normal, double width){
	if(normal.length<pts.length){ IOut.err("normal array size ("+normal.length+") doesn't match with pts."); }
	int num = pts.length;
        IVecI[] pts2 = new IVecI[num];
        for(int i=0; i<num; i++) pts2[i] = pts[i].dup();
        if(width==0) return pts2; 
	
        if(pts[0].eq(pts[num-1])){ // in case of closed curve 
	    IVecI off1 = pts[num-1].dif(pts[num-2]).cross(normal[0]).len(width);
	    IVecI off2 = pts[1].dif(pts[0]).cross(normal[0]).len(width);
	    IVecI v = off1.add(off2);
            v.mul(2*width*width/v.len2());
            pts2[0].add(v);
            pts2[num-1].add(v);
	}
        else{
	    IVecI off1 = pts[1].dif(pts[0]).cross(normal[0]).len(width);
	    IVecI off2 = pts[num-1].dif(pts[num-2]).cross(normal[num-1]).len(width);
            pts2[0].add(off1);
            pts2[num-1].add(off2);
        }
	for(int i=1; i<num-1; i++){
	    IVecI off1 = pts[i].dif(pts[i-1]).cross(normal[i]).len(width);
	    IVecI off2 = pts[i+1].dif(pts[i]).cross(normal[i]).len(width);
	    IVecI v = off1.add(off2);
            v.mul(2*width*width/v.len2());
            pts2[i].add(v);
        }
        return pts2;
    }
    
    public static IVecI[] offset(IVecI[] pts, IVecI[] normal, IDoubleI width){
	if(normal.length<pts.length){ IOut.err("normal array size ("+normal.length+") doesn't match with pts."); }
	int num = pts.length;
        IVecI[] pts2 = new IVecI[num];
        for(int i=0; i<num; i++) pts2[i] = pts[i].dup();
        if(width.x()==0) return pts2; 
	
        if(pts[0].eq(pts[num-1])){ // in case of closed curve 
	    IVecI off1 = pts[num-1].dif(pts[num-2]).cross(normal[0]).len(width);
	    IVecI off2 = pts[1].dif(pts[0]).cross(normal[0]).len(width);
	    IVecI v = off1.add(off2);
            v.mul(width.dup().pow(2).mul(2).div(v.len2(Ir.i)));
            pts2[0].add(v);
            pts2[num-1].add(v);
        }
        else{
	    IVecI off1 = pts[1].dif(pts[0]).cross(normal[0]).len(width);
	    IVecI off2 = pts[num-1].dif(pts[num-2]).cross(normal[num-1]).len(width);
            pts2[0].add(off1);
            pts2[num-1].add(off2);
        }
	for(int i=1; i<num-1; i++){
	    IVecI off1 = pts[i].dif(pts[i-1]).cross(normal[i]).len(width);
	    IVecI off2 = pts[i+1].dif(pts[i]).cross(normal[i]).len(width);
	    
	    IVecI v = off1.add(off2);
            v.mul(width.dup().pow(2).mul(2).div(v.len2(Ir.i)));
            pts2[i].add(v);
        }
        return pts2;
    }
    
    public static IVec[] offset(IVec[] pts, double width){
	IVecI[] out = offset((IVecI[])pts,width);
	if(out==null) return null;
	IVec[] retval = new IVec[out.length];
	for(int i=0; i<out.length; i++) retval[i] = out[i].get();
	return retval;
    }
    
    public static IVec[] offset(IVec[] pts, double width, boolean close){
	IVecI[] out = offset((IVecI[])pts,width,close);
	if(out==null) return null;
	IVec[] retval = new IVec[out.length];
	for(int i=0; i<out.length; i++) retval[i] = out[i].get();
	return retval;
    }
    
    public static IVecI[] offset(IVecI[] pts, double width, boolean close){
	if(!close || pts[0].eq(pts[pts.length-1]) ){
	    return offset(pts,width);
	}
	IVecI[] pts2 = new IVecI[pts.length+1];
	for(int i=0; i<pts.length; i++) pts2[i] = pts[i];
	pts2[pts.length] = pts[0].dup();
	IVecI[] out = offset(pts2,width);
	if(out==null) return out;
	IVecI[] retval = new IVecI[pts.length];
	for(int i=0; i<pts.length; i++) retval[i] = out[i]; // one less
	return retval;
    }
    
    public static IVecI[] offset(IVecI[] pts, double width){
	if(pts==null){ IOut.err("pts is null"); return null; }
	if(pts.length==1){ IOut.err("pts has only one point"); return null; }
	
	//IVecI[] offsetDir = new IVecI[pts.length-1];
	IVecI[] normals = new IVecI[pts.length];
	
	if(pts.length==2){
	    IVec normal = new IVec(0,0,1); // default
	    IVecI diff = pts[1].dif(pts[0]);
	    if(normal.isParallel(diff)){
		normal = new IVec(1,0,0); // another default
	    }
	    //offsetDir[0] = diff.cross(normal);
	    normals[0] = normal;
	    normals[1] = normal;
	    //return offset(pts,offsetDir,width);
	    return offset(pts,normals,width);
	}
	
	boolean close=false;
	if(pts[0].eq(pts[pts.length-1])) close=true;
			
	IVecI normal=null, n0=null;
	for(int i=0; i<pts.length; i++){
	    if(i==0){
		if(close){
		    if(!pts[pts.length-2].get().isStraight(pts[i],pts[i+1])){
			normal = pts[pts.length-2].nml(pts[i],pts[i+1]);
		    }
		}
		for(int j=i; j<pts.length-2 && normal==null; j++){
		    if(!pts[j].get().isStraight(pts[j+1],pts[j+2])){
			normal = pts[j].nml(pts[j+1],pts[j+2]);
		    }
		}
		// if all straight
		if(normal==null){
		    normal = new IVec(0,0,1); // default;
		    if(normal.get().isParallel(pts[i+1].dif(pts[i]))){
			normal = new IVec(1,0,0); // default;
		    }
		}
		n0=normal; // to be used at the end in case closed
	    }
	    else if(i<pts.length-1){
		if(!pts[i-1].get().isStraight(pts[i],pts[i+1])){
		    normal = pts[i-1].nml(pts[i],pts[i+1]);
		}
		else{} // use previous normal
	    }
	    else{ // i==pts.length==1
		if(close) normal = n0;
		else{} // use previous normal
	    }
	    //IVecI diff = pts[i+1].dif(pts[i]);
	    //offsetDir[i] = diff.cross(normal);
	    normals[i] = normal;
	}
	
	// align normals
	for(int i=1; i<normals.length; i++){
	    if(!close || i<normals.length-1){
		if( normals[i].dot(normals[i-1]) < 0 ) normals[i].neg();
	    }
	}
	//return offset(pts, offsetDir, width);
	return offset(pts, normals, width);
    }
    
    public static IVecI[] offset(IVecI[] pts, IDoubleI width, boolean close){
	if(!close || pts[0].eq(pts[pts.length-1]) ){
	    return offset(pts,width);
	}
	IVecI[] pts2 = new IVecI[pts.length+1];
	for(int i=0; i<pts.length; i++) pts2[i] = pts[i];
	pts2[pts.length] = pts[0].dup();
	IVecI[] out = offset(pts2,width);
	if(out==null) return out;
	IVecI[] retval = new IVecI[pts.length];
	for(int i=0; i<pts.length; i++) retval[i] = out[i]; // one less
	return retval;
    }
    
    
    public static IVecI[] offset(IVecI[] pts, IDoubleI width){
	if(pts==null){ IOut.err("pts is null"); return null; }
	if(pts.length==1){ IOut.err("pts has only one point"); return null; }
	
	//IVecI[] offsetDir = new IVecI[pts.length-1];
	IVecI[] normals = new IVecI[pts.length];
	
	if(pts.length==2){
	    IVec normal = new IVec(0,0,1); // default
	    IVecI diff = pts[1].dif(pts[0]);
	    if(normal.isParallel(diff)){
		normal = new IVec(1,0,0); // another default
	    }
	    //offsetDir[0] = diff.cross(normal);
	    normals[0] = normal;
	    normals[1] = normal;
	    //return offset(pts,offsetDir,width);
	    return offset(pts,normals,width);
	}
	
	boolean close=false;
	if(pts[0].eq(pts[pts.length-1])) close=true;
	
	IVecI normal=null, n0=null;
	for(int i=0; i<pts.length; i++){
	    if(i==0){
		if(close){
		    if(!pts[pts.length-2].get().isStraight(pts[i],pts[i+1])){
			normal = pts[pts.length-2].nml(pts[i],pts[i+1]);
		    }
		}
		for(int j=i; j<pts.length-2 && normal==null; j++){
		    if(!pts[j].get().isStraight(pts[j+1],pts[j+2])){
			normal = pts[j].nml(pts[j+1],pts[j+2]);
		    }
		}
		// if all straight
		if(normal==null){
		    normal = new IVec(0,0,1); // default;
		    if(normal.get().isParallel(pts[i+1].dif(pts[i]))){
			normal = new IVec(1,0,0); // default;
		    }
		}
		n0=normal; // to be used at the end in case closed
	    }
	    else if(i<pts.length-1){
		if(!pts[i-1].get().isStraight(pts[i],pts[i+1])){
		    normal = pts[i-1].nml(pts[i],pts[i+1]);
		}
		else{} // use previous normal
	    }
	    else{ // i==pts.length==1
		if(close) normal = n0;
		else{} // use previous normal
	    }
	    //IVecI diff = pts[i+1].dif(pts[i]);
	    //offsetDir[i] = diff.cross(normal);
	    normals[i] = normal;
	}
	// align normals
	for(int i=1; i<normals.length; i++){
	    if(!close || i<normals.length-1){
		if( normals[i].dot(normals[i-1]) < 0 ) normals[i].neg();
	    }
	}
	//return offset(pts, offsetDir, width);
	return offset(pts, normals, width);
    }
    */
    
    /*****
	  Old old offset methods
    *****/
    /*
    public static IVecI[] offset(IVecI[] pts, IVecI[] offsetDir, double width){
	if(offsetDir.length<pts.length-1){ IOut.err("offsetDir length is too small ("+offsetDir.length+"). needs to be more than pts.length-1"); }
	
	int num = pts.length;
        IVecI[] pts2 = new IVecI[num];
        
        for(int i=0; i<num; i++) pts2[i] = pts[i].dup();
        if(width==0) return pts2; 
        
        IVecI[] off = new IVecI[num-1];
        for(int i=0; i<num-1; i++) off[i] = offsetDir[i].dup().len(width);
	
        if(pts2[0].eq(pts[num-1])){ // in case of closed curve 
            IVecI v = off[num-2].dup().add(off[0]);
            v.mul(2*width*width/v.len2());
            pts2[0].add(v);
            pts2[num-1].add(v);
        }
        else{
            pts2[0].add(off[0]);
            pts2[num-1].add(off[num-2]);
        }
	
	for(int i=1; i<num-1; i++){
            IVecI v = off[i-1].dup().add(off[i]);
            v.mul(2*width*width/v.len2());
            pts2[i].add(v);
        }
        return pts2;
    }
    */
    
    /*
    public static IVecI[] offset(IVecI[] pts, IVecI[] tangents,
				 double width,IVecI planeNormal){
	if(pts==null){ IOut.err("pts is null"); return null; }
	
        int num = pts.length;
        IVecI[] pts2 = new IVecI[num];
        IVecI[] normal = new IVecI[num];
        for(int i=0; i<num; i++){
            normal[i] = tangents[i].cross(planeNormal);
            normal[i].len(width);
            pts2[i] = pts[i].dup();
            pts2[i].add(normal[i]);
        }
        return pts2;
    }
    public static IVecI[] offset(IVecI[] pts, IVecI[] tangents,
				 IDoubleI width,IVecI planeNormal){
	if(pts==null){ IOut.err("pts is null"); return null; }
	
        int num = pts.length;
        IVecI[] pts2 = new IVecI[num];
        IVecI[] normal = new IVecI[num];
        for(int i=0; i<num; i++){
            normal[i] = tangents[i].cross(planeNormal);
            normal[i].len(width);
            pts2[i] = pts[i].dup();
            pts2[i].add(normal[i]);
        }
        return pts2;
    }
    */
    

    
    /**
       project point a plane. input IVecI point pt is changed.
       doing same thing with projectToPlane(IVecI,IVecI,IVecI) but this is for IVecI.
    */
    public static IVecI projectToPlane(IVecI pt, IVecI projectDir, IVecI planeNormal, IVecI planePt){
	if(planePt==pt) return pt;
	pt.sub(planePt);
	IDoubleI ip = pt.dot(Ir.i, planeNormal).div(projectDir.dot(Ir.i, planeNormal));
	return pt.sub( projectDir.dup().mul(ip) ).add(planePt);
    }
    
    /**
       project point array to a plane. input points are changed.
    */
    public static void projectToPlane(IVecI[] pts, IVecI projectDir, IVecI planeNormal, IVecI planePt){
	for(IVecI p : pts) projectToPlane(p,projectDir,planeNormal,planePt); 
    }
    
    /**
       project point array to a plane. input points are changed.
    */
    public static void projectToPlane(IVecI[][] pts, IVecI projectDir, IVecI planeNormal, IVecI planePt){
	for(IVecI[] ps:pts) for(IVecI p:ps) projectToPlane(p,projectDir,planeNormal,planePt); 
    }
    
    /**
       project point array to a plane. input points are changed.
    */
    public static void projectToPlane(IVecI[] pts, IVecI planeNormal, IVecI planePt){
	projectToPlane(pts,planeNormal,planeNormal,planePt);
    }
    
    /**
       project point array to a plane. input points are changed.
    */
    public static void projectToPlane(IVecI[][] pts, IVecI planeNormal, IVecI planePt){
	projectToPlane(pts,planeNormal,planeNormal,planePt);
    }
    
    
    /**
       project point array to a plane. input points are changed.
       projection plane point is the first element in the array. (should it be center?)
    */
    public static void projectToPlane(IVecI[] pts, IVecI planeNormal){
	projectToPlane(pts,planeNormal,pts[0]);
    }
    
    /**
       project point array to a plane. input points are changed.
       projection plane point is the first element in the array. (should it be center?)
    */
    public static void projectToPlane(IVecI[][] pts, IVecI planeNormal){
	projectToPlane(pts,planeNormal,pts[0][0]);
    }
    
    
    /**
       project point array to a plane. input points are changed.
       projection plane normal is average normal of pts.
       projection plane point is the first element in the array. (should it be center?)
    */
    public static void projectToPlane(IVecI[] pts){
	if(pts==null || pts.length==0) return;
	projectToPlane(pts,averageNormal(pts),pts[0]);
    }
    
    /**
       project point array to a plane. input points are changed.
       projection plane normal is average normal of pts.
       projection plane point is the first element in the array. (should it be center?)
    */
    public static void projectToPlane(IVecI[][] pts){
	if(pts==null || pts.length==0 || pts[0].length==0) return;
	projectToPlane(pts,averageNormal(pts),pts[0][0]);
    }
    
    /** intersection of two infinite lines. */
    public static IVec intersect(IVecI line1Pt1, IVecI line1Pt2,
				 IVecI line2Pt1, IVecI line2Pt2){
	return intersect(line1Pt1, line1Pt2, line2Pt1, line2Pt2, IConfig.tolerance);
    }
    /** intersection of two infinite lines. */
    public static IVec intersect(IVecI line1Pt1, IVecI line1Pt2,
				 IVecI line2Pt1, IVecI line2Pt2, double tolerance){
	return intersect(line1Pt1.get(),line1Pt2.get(),line2Pt1.get(),line2Pt2.get(),
			 tolerance);
    }
    
    /** intersection of two infinite lines. */
    public static IVec intersect(IVec line1Pt1, IVec line1Pt2,
				 IVec line2Pt1, IVec line2Pt2){
	return intersect(line1Pt1, line1Pt2, line2Pt1, line2Pt2, IConfig.tolerance);
    }
    /** intersection of two infinite lines.
     *
     * If two lines are skew and 4 points are not on the same plane and 
     * the gap is less than tolerance, intersection points is projected onto 
     * the line. If the gap is too big or lines are parallel, it returns null.
     * If tolerance is negative, it accepts any gap of two lines and returns a close point on one line.
     @return intersection point or null if it cannot find intersection
    */
    public static IVec intersect(IVec line1Pt1, IVec line1Pt2,
				 IVec line2Pt1, IVec line2Pt2, double tolerance){
	
	if(line1Pt1==line2Pt1 || line1Pt1==line2Pt2) return line1Pt1.dup();
	if(line1Pt2==line2Pt1 || line1Pt2==line2Pt2) return line1Pt2.dup();
	
	if(tolerance>=0){ // added 20140316
	    // added 20091023 judging by tolerance 
	    if(line1Pt1.eq(line2Pt1,tolerance)) return line1Pt1.dup();
	    if(line1Pt1.eq(line2Pt2,tolerance)) return line1Pt1.dup();
	    if(line1Pt2.eq(line2Pt1,tolerance)) return line1Pt2.dup();
	    if(line1Pt2.eq(line2Pt2,tolerance)) return line1Pt2.dup();
        }
	
        IVec dir1 = line1Pt2.dif(line1Pt1);
        IVec dir2 = line2Pt2.dif(line2Pt1);
	
	IVec dif = line2Pt1.dif(line1Pt1);
	//if(dir1.isParallel(dif)) return line2Pt1.dup();
	
	// optimizing by inlining & reuse variables in projectTo2Vec
	
        // project to a plane defined by v1 and v2
        IVec op = dir1.cross(dir2);
	double oplen = op.len();
	
	//if(oplen==0){ // parallel lines // zero tolerance for direction
	if(tolerance<0 && oplen==0){
	    return line1Pt1.dup(); // is this ok? // 20140316
	}
	else if(oplen < tolerance*tolerance){ // parallel lines
	    dir1.unit();
	    if(dir1.mul(dif.dot(dir1)).sub(dif).len() > tolerance) return null;
	    return line1Pt1.dup();
	}
	op.div(oplen); // unitize
	double gap = dif.dot(op);
	
	if(tolerance>=0 && gap > tolerance) return null; // too much gap in vertical dir
	
	dif.sub(op.mul(gap));
	
	dir1.unit();
	dir2.unit();
	
        double ip12 = dir1.dot(dir2);
        double iip122 = 1-ip12*ip12;
        if(iip122==0) return null; // added 090422
	double ip1 = dif.dot(dir1);
        double ip2 = dif.dot(dir2);
	
	return dir1.mul((ip1-ip2*ip12)/iip122).add(line1Pt1);
	
	/*
	IVec ldir1 = line1Pt2.dif(line1Pt1);
        IVec ldir2 = line2Pt2.dif(line2Pt1);
	
	IVec diff = line2Pt1.dif(line1Pt1);
	if(ldir1.isParallel(diff)) return line2Pt1.dup();

	double coef[] = diff.projectTo2Vec(ldir1,ldir2);

	if(coef==null) return null;
	if(coef[2]*ldir1.cross(ldir2).len()>tolerance) return null;
	return ldir1.mul(coef[0]).add(line1Pt1);
	*/
	
	/* old definition. 
	IVec ldir1 = line1Pt2.dif(line1Pt1);
        IVec ldir2 = line2Pt2.dif(line2Pt1);
	
        IVec baseDir = line1Pt1.dif(line2Pt1);
	// 
        if(ldir1.isParallel(baseDir)) return line2Pt1.dup(); // added 20100710 // no tolerance?
	double coeff[] = ldir2.projectTo2Vec(ldir1,baseDir);
	
	if(coeff==null || coeff[1]==0) return null;
	
	//ldir1.mul(coeff[0]);
	ldir1.mul(coeff[0]/coeff[1]);
	ldir1.add(line1Pt1);
        return ldir1;
	*/
    }
    
    //intersect
    //intersectInfLine
    //intersectInfLines
    //intersectInfiniteLine
    //intersectInfiniteLines
    
    //intersectLine
    //intersectLines
    //intersectSeg
    //intersectSegment
    //intersectSegments
    
    
    /** intersection of two infinite lines with tolerance.
	if the intersection point is off more than the tolrance, it returns null.
    */
    /*
    public static IVec intersect(IVec line1Pt1, IVec line1Pt2,
				 IVec line2Pt1, IVec line2Pt2, double tolerance){
	// added 20091023 judging by tolerance 
        if(line1Pt1.dist(line2Pt1)<tolerance) return line1Pt1.dup();
        if(line1Pt1.dist(line2Pt2)<tolerance) return line1Pt1.dup();
        if(line1Pt2.dist(line2Pt1)<tolerance) return line1Pt2.dup();
        if(line1Pt2.dist(line2Pt2)<tolerance) return line1Pt2.dup();
        
        IVec ldir1 = line1Pt2.dif(line1Pt1);
        IVec ldir2 = line2Pt2.dif(line2Pt1);
        
        IVec baseDir = line2Pt1.dif(line1Pt1);
        
        if(ldir1.isParallel(baseDir)) return line2Pt1.dup(); // added 20100710
	
        double coeff[] = ldir2.projectTo2Vec(ldir1,baseDir);
	
	if(coeff==null || coeff[1]==0) return null;
        
        double x = coeff[0]/coeff[1];
        ldir1.mul(x);
        ldir1.add(line1Pt1);
        return ldir1;
    }
    */
    
    // ////////////////////////////////
    
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfiniteLines(IVecI line1Pt1, IVecI line1Pt2,
					      IVecI line2Pt1, IVecI line2Pt2){
	return intersect(line1Pt1, line1Pt2, line2Pt1, line2Pt2);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfiniteLines(IVecI line1Pt1, IVecI line1Pt2,
					      IVecI line2Pt1, IVecI line2Pt2, double tolerance){
	return intersect(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfiniteLines(IVec line1Pt1, IVec line1Pt2,
					      IVec line2Pt1, IVec line2Pt2){
	return intersect(line1Pt1, line1Pt2, line2Pt1, line2Pt2);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfiniteLines(IVec line1Pt1, IVec line1Pt2,
					      IVec line2Pt1, IVec line2Pt2, double tolerance){
	return intersect(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    
    // ////////////////////////////////
    
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfiniteLine(IVecI line1Pt1, IVecI line1Pt2,
					     IVecI line2Pt1, IVecI line2Pt2){
	return intersect(line1Pt1, line1Pt2, line2Pt1, line2Pt2);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfiniteLine(IVecI line1Pt1, IVecI line1Pt2,
					     IVecI line2Pt1, IVecI line2Pt2, double tolerance){
	return intersect(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfiniteLine(IVec line1Pt1, IVec line1Pt2,
					     IVec line2Pt1, IVec line2Pt2){
	return intersect(line1Pt1, line1Pt2, line2Pt1, line2Pt2);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfiniteLine(IVec line1Pt1, IVec line1Pt2,
					     IVec line2Pt1, IVec line2Pt2, double tolerance){
	return intersect(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    
    
    // ////////////////////////////////
    
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfLines(IVecI line1Pt1, IVecI line1Pt2,
					 IVecI line2Pt1, IVecI line2Pt2){
	return intersect(line1Pt1, line1Pt2, line2Pt1, line2Pt2);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfLines(IVecI line1Pt1, IVecI line1Pt2,
					 IVecI line2Pt1, IVecI line2Pt2, double tolerance){
	return intersect(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfLines(IVec line1Pt1, IVec line1Pt2,
					 IVec line2Pt1, IVec line2Pt2){
	return intersect(line1Pt1, line1Pt2, line2Pt1, line2Pt2);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfLines(IVec line1Pt1, IVec line1Pt2,
					 IVec line2Pt1, IVec line2Pt2, double tolerance){
	return intersect(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    
    // ////////////////////////////////
    
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfLine(IVecI line1Pt1, IVecI line1Pt2,
					IVecI line2Pt1, IVecI line2Pt2){
	return intersect(line1Pt1, line1Pt2, line2Pt1, line2Pt2);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfLine(IVecI line1Pt1, IVecI line1Pt2,
					IVecI line2Pt1, IVecI line2Pt2, double tolerance){
	return intersect(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfLine(IVec line1Pt1, IVec line1Pt2,
					IVec line2Pt1, IVec line2Pt2){
	return intersect(line1Pt1, line1Pt2, line2Pt1, line2Pt2);
    }
    /** intersect infinite lines; alias of intersect() */
    public static IVec intersectInfLine(IVec line1Pt1, IVec line1Pt2,
					IVec line2Pt1, IVec line2Pt2, double tolerance){
	return intersect(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    
    
    
    // ///////////////////////////////////
    
    public static IVec intersectSegment(IVecI line1Pt1, IVecI line1Pt2,
					IVecI line2Pt1, IVecI line2Pt2){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2,IConfig.tolerance);
    }
    
    public static IVec intersectSegment(IVecI line1Pt1, IVecI line1Pt2,
					IVecI line2Pt1, IVecI line2Pt2, double tolerance){
	return intersectSegment(line1Pt1.get(),line1Pt2.get(),
				line2Pt1.get(),line2Pt2.get(),tolerance);
    }
    
    /**
       intersection of line segments.
       If intersection point is not on line, it returns null.
    */
    public static IVec intersectSegment(IVec line1Pt1, IVec line1Pt2,
					IVec line2Pt1, IVec line2Pt2){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2,IConfig.tolerance);
    }
    
    /**
       intersection of line segments.
       If intersection point is not on line, it returns null.
       @return intersection point or null if it cannot find intersection
    */
    public static IVec intersectSegment(IVec line1Pt1, IVec line1Pt2,
					IVec line2Pt1, IVec line2Pt2, double tolerance){
	// in case of same instances
	if(line1Pt1==line2Pt1 || line1Pt1==line2Pt2) return line1Pt1.dup();
	if(line1Pt2==line2Pt1 || line1Pt2==line2Pt2) return line1Pt2.dup();
	
	double min1x = Math.min(line1Pt1.x,line1Pt2.x);
	double min1y = Math.min(line1Pt1.y,line1Pt2.y);
	double min1z = Math.min(line1Pt1.z,line1Pt2.z);
	double max1x = Math.max(line1Pt1.x,line1Pt2.x);
	double max1y = Math.max(line1Pt1.y,line1Pt2.y);
	double max1z = Math.max(line1Pt1.z,line1Pt2.z);
	double min2x = Math.min(line2Pt1.x,line2Pt2.x);
	double min2y = Math.min(line2Pt1.y,line2Pt2.y);
	double min2z = Math.min(line2Pt1.z,line2Pt2.z);
	double max2x = Math.max(line2Pt1.x,line2Pt2.x);
	double max2y = Math.max(line2Pt1.y,line2Pt2.y);
	double max2z = Math.max(line2Pt1.z,line2Pt2.z);
	
        // check bounding region
        if(min1x > max2x + tolerance || max1x < min2x - tolerance ||
	   min1y > max2y + tolerance || max1y < min2y - tolerance ||
           min1z > max2z + tolerance || max1z < min2z - tolerance ) return null;
        
	// judging by tolerance 
        if(line1Pt1.eq(line2Pt1,tolerance)) return line1Pt1.dup();
        if(line1Pt1.eq(line2Pt2,tolerance)) return line1Pt1.dup();
        if(line1Pt2.eq(line2Pt1,tolerance)) return line1Pt2.dup();
        if(line1Pt2.eq(line2Pt2,tolerance)) return line1Pt2.dup();
	
	IVec dir1 = line1Pt2.dif(line1Pt1);
        IVec dir2 = line2Pt2.dif(line2Pt1);
        
	IVec dif = line2Pt1.dif(line1Pt1);
	
	
	/*
	if(dir1.isParallel(dif)){
	    double len1 = dir1.len(); 
	    double ip = dir1.dot(dif)/len1;
	    if(ip < -tolerance) return null; // out in the opposite dir
	    if( ip > len1+tolerance ) return null; // out of line1Pt2
	    return line2Pt1.dup();
	}
	*/
	
	// optimizing by inlining & reuse variables in projectTo2Vec
	
        // project to a plane defined by v1 and v2
        IVec op = dir1.cross(dir2);
	double oplen = op.len();
	
	//if(oplen==0) return null; // parallel lines
	if(oplen < tolerance*tolerance){ // parallel lines
	    dir1.unit();
	    if(dir1.dup().mul(dif.dot(dir1)).sub(dif).len() > tolerance){
		return null; // too much gap
	    }
	    
	    // now parallel and close but overwapping?
	    IVec dif12 = line2Pt2.dif(line1Pt1);
	    IVec dif21 = line2Pt1.dif(line1Pt2);
	    double ip11 = dir1.dot(dif);
	    double ip12 = dir1.dot(dif12);
	    
	    if( ip11 <= tolerance && ip12 >= -tolerance ||
		ip11 >= -tolerance && ip12 <= tolerance ) return line1Pt1.dup();
	    
	    double ip21 = dir1.dot(dif21);
	    if( ip11 >= -tolerance && ip21 <= tolerance ||
		ip11 <= tolerance && ip21 >= -tolerance ) return line2Pt1.dup();
	    
	    return null; // no overlap
	}
	
	
	op.div(oplen); // unitized
	double gap = dif.dot(op);
	
	if(gap > tolerance) return null; // too much gap in vertical dir
	
	dif.sub(op.mul(gap));

	double len1 = dir1.len();
	double len2 = dir2.len();
	dir1.div(len1); // unitized
	dir2.div(len2); // unitized
	
        double ip12 = dir1.dot(dir2);
        double iip122 = 1-ip12*ip12;
        if(iip122==0) return null; // added 090422
	double ip1 = dif.dot(dir1);
        double ip2 = dif.dot(dir2);
	
	double ilen1 = (ip1-ip2*ip12)/iip122;
	if(ilen1 < -tolerance || ilen1 > len1+tolerance) return null; // out of segment 1
	
	double ilen2 = (ip2-ip1*ip12)/iip122;
	if(-ilen2 < -tolerance || -ilen2 > len2+tolerance) return null; // out of segment 2
	
	return dir1.mul(ilen1).add(line1Pt1);
	
        /*
        IVec dif = line1Pt1.dif(line2Pt1);
	
        // same direction
        //if(dir1.cross(dir2).len2()<=0) return null;
	if(dir1.isParallel(dir2)) return null; // wrong.
	
        double coeff[] = dir2.projectTo2Vec(dir1,dif);
	
	//if(coeff==null) return null;
	if(coeff==null || coeff[1]==0) return null;
	
        double x = coeff[0]/coeff[1];
	
        if(x<0 || x>1){ return null; }
        dir1.mul(x);
        dir1.add(line1Pt1);
        return dir1;
	*/
    }
    
    
    
    // ///////////////////////////////////
    /** intersection of line segments; alias of intersectSegment */
    public static IVec intersectSegments(IVecI line1Pt1, IVecI line1Pt2,
					 IVecI line2Pt1, IVecI line2Pt2){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2);
    }
    public static IVec intersectSegments(IVecI line1Pt1, IVecI line1Pt2,
					 IVecI line2Pt1, IVecI line2Pt2, double tolerance){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    public static IVec intersectSegments(IVec line1Pt1, IVec line1Pt2,
					 IVec line2Pt1, IVec line2Pt2){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2);
    }
    public static IVec intersectSegments(IVec line1Pt1, IVec line1Pt2,
					 IVec line2Pt1, IVec line2Pt2, double tolerance){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    
    
    // ///////////////////////////////////
    /** intersection of line segments; alias of intersectSegment */
    public static IVec intersectSeg(IVecI line1Pt1, IVecI line1Pt2,
				    IVecI line2Pt1, IVecI line2Pt2){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2);
    }
    public static IVec intersectSeg(IVecI line1Pt1, IVecI line1Pt2,
				    IVecI line2Pt1, IVecI line2Pt2, double tolerance){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    public static IVec intersectSeg(IVec line1Pt1, IVec line1Pt2,
				    IVec line2Pt1, IVec line2Pt2){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2);
    }
    public static IVec intersectSeg(IVec line1Pt1, IVec line1Pt2,
				    IVec line2Pt1, IVec line2Pt2, double tolerance){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    
    // ///////////////////////////////////
    /** intersection of line segments; alias of intersectSegment */
    public static IVec intersectLines(IVecI line1Pt1, IVecI line1Pt2,
				      IVecI line2Pt1, IVecI line2Pt2){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2);
    }
    public static IVec intersectLines(IVecI line1Pt1, IVecI line1Pt2,
				      IVecI line2Pt1, IVecI line2Pt2, double tolerance){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    public static IVec intersectLines(IVec line1Pt1, IVec line1Pt2,
				      IVec line2Pt1, IVec line2Pt2){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2);
    }
    public static IVec intersectLines(IVec line1Pt1, IVec line1Pt2,
				      IVec line2Pt1, IVec line2Pt2, double tolerance){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    
    // ///////////////////////////////////
    /** intersection of line segments; alias of intersectSegment */
    public static IVec intersectLine(IVecI line1Pt1, IVecI line1Pt2,
				     IVecI line2Pt1, IVecI line2Pt2){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2);
    }
    public static IVec intersectLine(IVecI line1Pt1, IVecI line1Pt2,
				     IVecI line2Pt1, IVecI line2Pt2, double tolerance){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    public static IVec intersectLine(IVec line1Pt1, IVec line1Pt2,
				     IVec line2Pt1, IVec line2Pt2){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2);
    }
    public static IVec intersectLine(IVec line1Pt1, IVec line1Pt2,
				     IVec line2Pt1, IVec line2Pt2, double tolerance){
	return intersectSegment(line1Pt1,line1Pt2,line2Pt1,line2Pt2,tolerance);
    }
    
    /** intersection of two planes.
	@return array of two IVec. first is intersection line direction. second is a point on intersection line. or null if no intersection found
    */
    public static IVec[] intersectPlane(IVec plane1Dir, IVec plane1Pt, IVec plane2Dir, IVec plane2Pt){
	IVec ldir = plane1Dir.cross(plane2Dir);
	if(ldir.len2()<IConfig.tolerance*IConfig.tolerance) return null; // plane is parallel
	IVec t = plane1Dir.cross(ldir);
	t.mul( plane2Dir.dot(plane2Pt.dif(plane1Pt))/plane2Dir.dot(t) );
	t.add(plane1Pt); //t.add(plane1Dir); // bug!!!
	return new IVec[]{ ldir, t };
    }
    
    /** intersection of two planes at the origin.
	@return intersection direction vector. when it's parallel, return value would be zero vector.
    */
    public static IVec intersectPlane(IVec plane1Dir, IVec plane2Dir){
	return plane1Dir.cross(plane2Dir);
    }
    
    /** intersection of two planes
	@return array of two IVec. first is intersection line direction. second is a point on intersection line. or null if no intersection found
    */
    public static IVec[] intersectPlane(IVec plane1Pt1, IVec plane1Pt2, IVec plane1Pt3,
					IVec plane2Pt1, IVec plane2Pt2, IVec plane2Pt3){
	return intersectPlane(IVec.nml(plane1Pt1, plane1Pt2, plane1Pt3), plane1Pt1,
			      IVec.nml(plane2Pt1, plane2Pt2, plane2Pt3), plane2Pt1);
    }
    
    /** intersection of one plane and one infinite line */
    public static IVec intersectPlaneAndLine(IVec planeDir, IVec planePt,
					     IVec lineDir, IVec linePt){
	IVec p = lineDir.dup();
	p.mul( planeDir.dot(planePt.dif(linePt)) / planeDir.dot(lineDir) );
	p.add(linePt);
	return p;
    }
    
    /** intersection of one plane at the origin and one infinite line */
    public static IVec intersectPlaneAndLine(IVec planeDir, IVec lineDir, IVec linePt){
	IVec p = lineDir.dup();
	p.mul( -planeDir.dot(linePt) / planeDir.dot(lineDir) );
	p.add(linePt);
	return p;
    }
    
    /** intersection of one plane and one infinite line */
    public static IVec intersectPlaneAndLine(IVec planePt1, IVec planePt2, IVec planePt3,
					     IVec linePt1, IVec linePt2){
	return intersectPlaneAndLine(IVec.nml(planePt1,planePt2,planePt3), planePt1,
				     linePt2.dif(linePt1),linePt1);
    }
    
    
    /** intersection of one plane and one line segment. if the segment is not intersecting with the plane, it returns null. */
    public static IVec intersectPlaneAndSegment(IVec planeDir, IVec planePt,
						IVec linePt1, IVec linePt2){
	IVec ldir = linePt2.dif(linePt1);
	double r = planeDir.dot(planePt.dif(linePt1))/planeDir.dot(ldir);
	if(r < 0 || r > 1){ return null; }
	return ldir.mul(r).add(linePt1);
    }
    
    /** intersection of one plane at the origin and one line segment. if the segment is not intersecting with the plane, it returns null. */
    public static IVec intersectPlaneAndSegment(IVec planeDir, IVec linePt1, IVec linePt2){
	IVec ldir = linePt2.dif(linePt1);
	double r = -planeDir.dot(linePt1)/planeDir.dot(ldir);
	if(r < 0 || r > 1){ return null; }
	return ldir.mul(r).add(linePt1);
    }
    
    /** bisector plane of two planes
     @return an array of two IVec; the first is plane normal direction, the second is plane point. */
    public static IVec[] bisectPlane(IVec planeDir1, IVec planePt1, IVec planeDir2, IVec planePt2){
	IVec[] isct = intersectPlane(planeDir1, planePt1, planeDir2, planePt2);
	if(isct==null) return null;
	IVec bsct = planeDir1.dup().unit();
	
	if(planeDir1.dot(planeDir2)>0) bsct.add(planeDir2.dup().unit());
	else bsct.sub(planeDir2.dup().unit());
	
	bsct.unit();
	return new IVec[]{ bsct, isct[1] };
    }

    /** intersection circle of a plane and a sphere.
	@return IVec[0] : normal or an intersection circle and the length of the vector is radius
	        IVec[1] : center point of the intersection circle
    */
    public static IVec[] intersectPlaneAndSphere(IVec planeDir, IVec planePt,
						 IVec sphereCenter, double sphereRadius){
	IVec circleCenter = sphereCenter.dup().projectToPlane(planeDir, planeDir, planePt);
	double dist = circleCenter.dist(sphereCenter);
	if(dist > sphereRadius) return null;
	if(dist == sphereRadius) return new IVec[]{ new IVec(0,0,0), circleCenter };
	double radius = Math.sqrt( sphereRadius*sphereRadius - dist*dist );
	IVec circleNormal = planeDir.dup().len(radius);
	return new IVec[]{ circleNormal, circleCenter };
    }
    
    public static IVec[] intersectCircleAndSphere(IVec circleNormal, IVec circleCenter, double circleRadius,
						  IVec sphereCenter, double sphereRadius){
	IVec[] circle2 = intersectPlaneAndSphere(circleNormal, circleCenter,
						 sphereCenter, sphereRadius);
	if(circle2==null) return null;
	IVec[] isct = intersectCircle(circleNormal,circleCenter,circleRadius,circle2[0],circle2[1],circle2[0].len());
	return isct;
    }
    
    /** intersection circle of two spheres
	@return null if no intersection or 
                IVec[0] : normal vector of an intersection circle whose length is radius
	        IVec[1] : center of the intersection circle
     */
    public static IVec[] intersectSphere(IVec sphereCenter1, double radius1, IVec sphereCenter2, double radius2){
	IVec dif = sphereCenter2.dif(sphereCenter1);
	double dist = dif.len();
	if(dist > (radius1+radius2) ||
	   dist < radius1 && (radius1-dist) > radius2  ||
	   dist < radius2 && (radius2-dist) > radius1 ) return null;
	
	double len = ( dist*dist + radius1*radius1 - radius2*radius2)/(2*dist);
	IVec circleCenter = dif.dup().len(len).add(sphereCenter1);
	double rad = radius1*radius1 - len*len;
	if(rad < 0) return null;
	
	IVec circleNormal = dif.len(Math.sqrt(rad));
	return new IVec[]{ circleNormal, circleCenter };
    }
    
    public static IVec[] intersectCircle(IVec circleNormal1, IVec circleCenter1, double radius1,
					 IVec circleNormal2, IVec circleCenter2, double radius2){
	if(!circleNormal1.isParallel(circleNormal2)) return null;
	IVec[] isct = intersectSphere(circleCenter1, radius1, circleCenter2, radius2);
	if(isct==null) return null;
	IVec dif = circleCenter2.dif(circleCenter1);
	if(!dif.isPerpendicular(circleNormal1)) return null;
	double rad = isct[0].len();
	dif.rot(circleNormal1, Math.PI/2);
	dif.len(rad);
	return new IVec[]{ isct[1].dup().add(dif), isct[1].sub(dif) };
    }
    
    
    /** measure angle of polyline of pt1, pt2 and pt3 at pt2, which is equivalent to measure angle between vectors from pt2 to pt1 and pt2 to pt3. */
    public static double angle(IVecI pt1, IVecI pt2, IVecI pt3){
	return pt1.dif(pt2).angle(pt3.dif(pt2));
    }
    
}
