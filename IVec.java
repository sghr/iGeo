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

package igeo;

/**
   Class of 3 dimensional vector.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IVec extends IParameterObject implements IVecI, IEntityParameter{
    
    public final static IVec origin = new IVec(0,0,0);
    public final static IVec xaxis = new IVec(1,0,0);
    public final static IVec yaxis = new IVec(0,1,0);
    public final static IVec zaxis = new IVec(0,0,1);
    
    
    public double x,y,z;
    
    public IVec(){}
    public IVec(double x, double y, double z){ this.x=x; this.y=y; this.z=z; }
    public IVec(IVec v){ x=v.x; y=v.y; z=v.z; }
    public IVec(IVecI v){ IVec u=v.get(); x=u.x; y=u.y; z=u.z; }
    public IVec(IDoubleI x, IDoubleI y, IDoubleI z){
	this.x=x.x(); this.y=y.x(); this.z=z.x();
    }
    public IVec(IVec2I v){ x=v.x(); y=v.y(); z=0; }
    
    public IVec(IServerI s){ super(s); }
    public IVec(IServerI s, double x, double y, double z){ super(s); this.x=x; this.y=y; this.z=z; }
    public IVec(IServerI s, IVec v){ super(s); x=v.x; y=v.y; z=v.z; }
    public IVec(IServerI s, IVecI v){ super(s); IVec u=v.get(); x=u.x; y=u.y; z=u.z; }
    public IVec(IServerI s, IDoubleI x, IDoubleI y, IDoubleI z){
	super(s); this.x=x.x(); this.y=y.x(); this.z=z.x();
    }
    public IVec(IServerI s, IVec2I v){ super(s); x=v.x(); y=v.y(); z=0; }
    
    
    public double x(){ return x; }
    public double y(){ return y; }
    public double z(){ return z; }
    public IVec get(){ return this; }
    //public IVec get(){ return new IVec(x,y,z); }
    
    public IVec dup(){ return new IVec(x,y,z); }
    
    
    public IVec2 to2d(){ return new IVec2(this); }
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
    
    
    /**
       scale add
    */
    public IVec add(IVec v, double f){ x+=f*v.x; y+=f*v.y; z+=f*v.z; return this; }
    public IVec add(IVecI v, double f){ return add(v.get(),f); }
    public IVec add(IVecI v, IDoubleI f){ return add(v.get(),f); }
    
    
    /** dot product in double (P for Primitive)
    */
    public double dot(IVec v){ return x*v.x+y*v.y+z*v.z; }
    public double dot(IVecI v){ return dot(v.get()); }
    //public IDouble dotR(IVecI v){ return new IDouble(dot(v.get())); }
    public double dot(ISwitchE e, IVecI v){ return dot(v); }
    public IDouble dot(ISwitchR r, IVecI v){ return new IDouble(dot(v)); }
    
    /** cross product
	changed to return a new instance, not changing own content. (!!!!) 2011/08/03
    */
    public IVec cross(IVec v){
        //double xt = y*v.z - z*v.y;
        //double yt = z*v.x - x*v.z;
        //double zt = x*v.y - y*v.x;
        //x=xt; y=yt; z=zt; return this;
	return dup().set(y*v.z - z*v.y, 
			 z*v.x - x*v.z,
			 x*v.y - y*v.x);
    }
    public IVec cross(IVecI v){ return cross(v.get()); }

    /** cross product,
	changing its values by itself. no new instance created.
    */
    public IVec icross(IVec v){
        double xt = y*v.z - z*v.y;
        double yt = z*v.x - x*v.z;
        double zt = x*v.y - y*v.x;
        x=xt; y=yt; z=zt; return this;
    }
    
    
    public double len(){ return Math.sqrt(x*x+y*y+z*z); }
    //public IDouble lenR(){ return new IDouble(len()); }
    public double len(ISwitchE e){ return len(); }
    public IDouble len(ISwitchR r){ return new IDouble(len()); }
    
    public double len2(){ return x*x+y*y+z*z; }
    //public IDouble len2R(){ return new IDouble(len2()); }
    public double len2(ISwitchE e){ return len2(); }
    public IDouble len2(ISwitchR r){ return new IDouble(len2()); }
    
    public IVec len(double l){ l /= len(); x*=l; y*=l; z*=l; return this; }
    public IVec len(IDoubleI l){ return len(l.x()); }
    
    public IVec unit(){
	double l=len();
	//if(l==0){ IOut.err("vector length is zero"); return this; } // added 20111002 // removed 20111003
	x/=l; y/=l; z/=l;
	return this;
    }
    
    public double dist(IVec v){
	return Math.sqrt( (x-v.x)*(x-v.x) + (y-v.y)*(y-v.y) + (z-v.z)*(z-v.z) );
    }
    public double dist(IVecI v){ return dist(v.get()); }
    //public IDouble distR(IVecI v){ return new IDouble(dist(v.get())); }
    public double dist(ISwitchE e, IVecI v){ return dist(v); }
    public IDouble dist(ISwitchR r, IVecI v){ return new IDouble(dist(v)); }
    
    public double dist2(IVec v){
	return (x-v.x)*(x-v.x) + (y-v.y)*(y-v.y) + (z-v.z)*(z-v.z);
    }
    public double dist2(IVecI v){ return dist2(v.get()); }
    //public IDouble dist2R(IVecI v){ return new IDouble(dist2(v)); }
    public double dist2(ISwitchE e, IVecI v){ return dist2(v); }
    public IDouble dist2(ISwitchR r, IVecI v){ return new IDouble(dist2(v)); }
    
    
    public boolean eq(IVec v){ return eq(v,IConfig.tolerance); }
    public boolean eq(IVecI v){ return eq(v.get(),IConfig.tolerance); }
    //public IBool eqR(IVecI v){ return new IBool(eq(v.get(),IConfig.tolerance)); }
    public boolean eq(ISwitchE e, IVecI v){ return eq(v); }
    public IBool eq(ISwitchR r, IVecI v){ return new IBool(eq(v)); }
    
    public boolean eq(IVec v, double resolution){ return dist2(v) <= resolution*resolution; }
    public boolean eq(IVecI v, double resolution){ return eq(v.get(),resolution); }
    
    //public IBool eqR(IVecI v, IDoubleI resolution){ return new IBool(eq(v.get(),resolution.x())); }
    //public IBool eqR(IVecI v, IDoubleI resolution){ return new IBool(eq(v.get(),resolution.x())); }
    public boolean eq(ISwitchE e, IVecI v, double resolution){ return eq(v,resolution); }
    public IBool eq(ISwitchR r, IVecI v, IDoubleI resolution){ return new IBool(eq(v,resolution.x())); }
    
    public boolean eqX(IVec v){ return eqX(v,IConfig.tolerance); }
    public boolean eqY(IVec v){ return eqY(v,IConfig.tolerance); }
    public boolean eqZ(IVec v){ return eqZ(v,IConfig.tolerance); }
    public boolean eqX(IVecI v){ return eqX(v,IConfig.tolerance); }
    public boolean eqY(IVecI v){ return eqY(v,IConfig.tolerance); }
    public boolean eqZ(IVecI v){ return eqZ(v,IConfig.tolerance); }
    //public IBool eqXR(IVecI v){ return new IBool(eqX(v,IConfig.tolerance)); }
    //public IBool eqYR(IVecI v){ return new IBool(eqY(v,IConfig.tolerance)); }
    //public IBool eqZR(IVecI v){ return new IBool(eqZ(v,IConfig.tolerance)); }
    public boolean eqX(ISwitchE e, IVecI v){ return eqX(v); }
    public boolean eqY(ISwitchE e, IVecI v){ return eqY(v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return eqZ(v); }
    public IBool eqX(ISwitchR r, IVecI v){ return new IBool(eqX(v)); }
    public IBool eqY(ISwitchR r, IVecI v){ return new IBool(eqY(v)); }
    public IBool eqZ(ISwitchR r, IVecI v){ return new IBool(eqZ(v)); }
    
    public boolean eqX(IVec v, double resolution){ return Math.abs(x-v.x)<=resolution; }
    public boolean eqY(IVec v, double resolution){ return Math.abs(y-v.y)<=resolution; }
    public boolean eqZ(IVec v, double resolution){ return Math.abs(z-v.z)<=resolution; }
    public boolean eqX(IVecI v, double resolution){ return Math.abs(x-v.x())<=resolution; }
    public boolean eqY(IVecI v, double resolution){ return Math.abs(y-v.y())<=resolution; }
    public boolean eqZ(IVecI v, double resolution){ return Math.abs(z-v.z())<=resolution; }
    //public IBool eqXR(IVecI v, IDoubleI resolution){ return new IBool(eqX(v,resolution.x())); }
    //public IBool eqYR(IVecI v, IDoubleI resolution){ return new IBool(eqY(v,resolution.x())); }
    //public IBool eqZR(IVecI v, IDoubleI resolution){ return new IBool(eqZ(v,resolution.x())); }
    public boolean eqX(ISwitchE e, IVecI v, double resolution){ return eqX(v,resolution); }
    public boolean eqY(ISwitchE e, IVecI v, double resolution){ return eqY(v,resolution); }
    public boolean eqZ(ISwitchE e, IVecI v, double resolution){ return eqZ(v,resolution); }
    public IBool eqX(ISwitchR r, IVecI v, IDoubleI resolution){ return new IBool(eqX(v,resolution.x())); }
    public IBool eqY(ISwitchR r, IVecI v, IDoubleI resolution){ return new IBool(eqY(v,resolution.x())); }
    public IBool eqZ(ISwitchR r, IVecI v, IDoubleI resolution){ return new IBool(eqZ(v,resolution.x())); }
    
    
    /**
       angle in double (P for Primitive)
    */
    public double angle(IVec v){
        double len1 = len(); if(len1==0) return 0;
        double len2 = v.len(); if(len2==0) return 0;
        double cos = dot(v)/(len1*len2);
        if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
        return Math.acos(cos);
    }
    
    public double angle(IVecI v){ return angle(v.get()); }
    //public IDouble angleR(IVec v){ return new IDouble(angleP(v)); }
    //public IDouble angleR(IVecI v){ return new IDouble(angle(v.get())); }
    public double angle(ISwitchE e, IVecI v){ return angle(v); }
    public IDouble angle(ISwitchR r, IVecI v){ return new IDouble(angle(v)); }
    
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
    public double angle(IVecI v, IVecI axis){ return angle(v.get(),axis.get()); }
    //public IDouble angleR(IVec v, IVec axis){ return new IDouble(angleP(v,axis)); }
    //public IDouble angleR(IVecI v, IVecI axis){ return new IDouble(angle(v.get(),axis.get())); }
    public double angle(ISwitchE e, IVecI v, IVecI axis){ return angle(v,axis); }
    public IDouble angle(ISwitchR r, IVecI v, IVecI axis){ return new IDouble(angle(v,axis)); }
    
    
    public IVec rot(IVec axis, double angle){
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
    
    public IVec rot(IVecI axis, double angle){ return rot(axis.get(), angle); }
    //public IVec rot(IVecI axis, IDouble angle){ return rot(axis.get(), angle.x); }
    public IVec rot(IVecI axis, IDoubleI angle){ return rot(axis.get(), angle.x()); }
    //
    
    public IVec rot(IVec center, IVec axis, double angle){
	if(center==this) return this;
	return sub(center).rot(axis,angle).add(center);
    }
    public IVec rot(IVecI center, IVecI axis, double angle){
	return rot(center.get(),axis.get(),angle);
    }
    public IVec rot(IVecI center, IVecI axis, IDoubleI angle){
	return rot(center.get(),axis.get(),angle.x());
    }
    
    // test this method later!!! 
    public IVec rot(IVec axis, IVec destDir){
	return rot(axis,destDir.cross(axis).angle(cross(axis)));
    }
    
    public IVec rot(IVecI axis, IVecI destDir){ return rot(axis.get(),destDir.get()); }
    
    public IVec rot(IVec center, IVec axis, IVec destPt){
	if(center==this) return this;
	return sub(center).rot(axis,destPt.diff(center)).add(center);
    }
    
    public IVec rot(IVecI center, IVecI axis, IVecI destPt){
	return rot(center.get(), axis.get(), destPt.get());
    }
    
    /** alias of mul */
    public IVec scale(IDoubleI f){ return mul(f); }
    public IVec scale(double f){ return mul(f); }
    
    public IVec scale(IVec center, double f){
	if(center==this) return this;
	return sub(center).scale(f).add(center);
    }
    public IVec scale(IVecI center, double f){
	return scale(center.get(),f);
    }
    public IVec scale(IVecI center, IDoubleI f){
	return scale(center.get(),f.x());
    }
        
    /** scale only in 1 direction */
    public IVec scale1d(IVec axis, double f){
	IVec n = axis.dup().unit();
	n.mul(this.dot(n));
	IVec t = this.diff(n);
	return this.set(n.mul(f).add(t));
    }
    public IVec scale1d(IVecI axis, double f){ return scale1d(axis.get(),f); }
    public IVec scale1d(IVecI axis, IDoubleI f){ return scale1d(axis.get(),f.x()); }
    public IVec scale1d(IVecI center, IVecI axis, double f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    public IVec scale1d(IVecI center, IVecI axis, IDoubleI f){
	return scale1d(center,axis,f.x());
    }
    
    
    /**
       reflect (mirror) 3 dimensionally to the other side of the plane
    */
    public IVec ref(IVec planeDir){
	//planeDir = planeDir.dup().unit();
	//return add(planeDir.mul(dot(planeDir)*-2));
	return add(planeDir.dup().mul(dot(planeDir)/planeDir.len2()*-2));
    }
    public IVec ref(IVecI planeDir){ return ref(planeDir.get()); }
    public IVec ref(IVec center, IVec planeDir){
	if(center==this) return this;
	return sub(center).ref(planeDir).add(center);
    }
    public IVec ref(IVecI center, IVecI planeDir){
	return ref(center.get(),planeDir.get());
    }
    
    public IVec mirror(IVec planeDir){ return ref(planeDir); }
    public IVec mirror(IVecI planeDir){ return ref(planeDir); }
    public IVec mirror(IVec center, IVec planeDir){ return ref(center,planeDir); }
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
    public IVec mv(double x, double y, double z){ return add(x,y,z); }
    public IVec mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IVec mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IVec cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IVec cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IVec cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IVec cp(IVecI v){ return dup().add(v); }
    
    
    
    /**********************************************************************************
     * methods creating new instance
     *********************************************************************************/
    
    public IVec diff(IVec v){ return dup().sub(v); }
    public IVec diff(IVecI v){ return dup().sub(v); }
    public IVec mid(IVec v){ return dup().add(v).div(2); }
    public IVec mid(IVecI v){ return dup().add(v).div(2); }
    public IVec sum(IVec v){ return dup().add(v); }
    public IVec sum(IVecI v){ return dup().add(v); }
    public IVec sum(IVec... v){
	IVec ret = this.dup(); for(IVec vi: v) ret.add(vi); return ret;
    }
    public IVec sum(IVecI... v){
	IVec ret = this.dup(); for(IVecI vi: v) ret.add(vi); return ret;
    }
    
    public IVec bisect(IVec v){ return dup().unit().add(v.dup().unit()); }
    public IVec bisect(IVecI v){ return bisect(v.get()); }
    
    /**
       weighted sum, creating new instance
    */
    public IVec sum(IVec v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    public IVec sum(IVec v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    
    public IVec sum(IVecI v2, double w1, double w2){ return sum(v2.get(),w1,w2); }
    public IVec sum(IVecI v2, double w2){ return sum(v2.get(),w2); }
    
    public IVec sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return sum(v2.get(),w1.x(),w2.x()); }
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
    public boolean isParallel(IVecI v, double angleReso){
        return Math.abs(dot(v.get())/(len()*v.get().len())) > Math.cos(angleReso);
	
    }
    
    public boolean isStraight(IVecI v1, IVecI v2){
        return isStraight(v1,v2,IConfig.angleTolerance);
    }
    public boolean isStraight(IVecI v1, IVecI v2, double angleReso){
        return v1.get().diff(this).isParallel(v2.get().diff(v1),angleReso);
    }
    
    
    /**
       alias of cross. (not unitized ... ?)
    */
    public IVec nml(IVecI v){ return cross(v); }
    /**
       create normal vector from 3 points of self, pt1 and pt2
    */
    public IVec nml(IVecI pt1, IVecI pt2){
	return this.diff(pt1).cross(this.diff(pt2)).unit();
    }
    public IVec nml(IVec pt1, IVec pt2){
	return this.diff(pt1).icross(this.diff(pt2)).unit();
    }
    
    public static IVecI getNormal(IVecI pt1, IVecI pt2, IVecI pt3){
	return pt1.nml(pt2,pt3);
    }
    
    public static IVec getNormal(IVec pt1, IVec pt2, IVec pt3){
	return pt1.nml(pt2,pt3);
    }
    
    public IVecI getNormal(IVecI pt2, IVecI pt3){ return nml(pt2,pt3); }
    
    public IVec getNormal(IVec pt2, IVec pt3){ return nml(pt2,pt3); }
    
    
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
    
    public IVec projectToLine(IVecI linePt, IVecI lineDir){
	if(linePt==this) return this;
	IVec diff = this.diff(linePt);
	double dot = diff.dot(lineDir)/lineDir.len();
	return diff.set(lineDir.get()).len(dot).add(linePt);
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
        if(opnrm2==0) return null; // added 090422
        coef[2] = this.dot(op)/opnrm2;
        this.sub(op.mul(coef[2]));
	
	IVec v1n = v1.get(); if(v1n==v1) v1n = v1n.dup();
	IVec v2n = v2.get(); if(v2n==v2) v2n = v2n.dup();
	
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
	    IOut.err("two vectors are in same direction");
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
    
    
    public double distanceToPlane(IVecI planeDir, IVecI planePt){
	//return Math.abs(this.diff(planePt).dotP(planeDir.get())/planeDir.get().len());
	return Math.abs(diff(planePt).dot(planeDir)/planeDir.len());
    }
    
    /**
       create a new vector from this point to the line in parpendicular direction.
    */
    public IVec perpendicularVectorToLine(IVecI lineDir, IVecI linePt){
	IVec ldir = lineDir.get().dup();
	IVec diff = linePt.diff(this).get();
	return ldir.mul(-ldir.dot(diff)/ldir.len2()).add(diff);
    }
    
    /**
       create a new vector from line to this point perpendicular to the axis
    */
    public IVec perpendicularVectorToVector(IVecI axisDir){
	IVec ldir = axisDir.get().dup();
	return ldir.mul(-ldir.dot(this)/ldir.len2()).add(this);
    }
    
    
    public boolean isOnPlane(IVecI planePt1, IVecI planePt2, IVecI planePt3){
	return isOnPlane(planePt1,planePt2,planePt3,IConfig.tolerance);
    }
    public boolean isOnPlane(IVecI planeDir, IVecI planePt){
	return isOnPlane(planeDir,planePt,IConfig.tolerance);
    }
    public boolean isOnPlane(IVecI planePt1, IVecI planePt2, IVecI planePt3, double resolution){
	return isOnPlane(getNormal(planePt1,planePt2,planePt3),planePt1,resolution);
    }
    public boolean isOnPlane(IVecI planeDir, IVecI planePt, double resolution){
	return distanceToPlane(planeDir,planePt)<resolution;
    }
    
    
    
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
    
    public static boolean isFlat(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4){
	return pt1.get().isOnPlane(pt2,pt3,pt4);
    }
    
    public static boolean isFlat(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4, double resolution){
	return pt1.get().isOnPlane(pt2,pt3,pt4,resolution);
    }
    
    
    public static boolean isArrayEqual(IVec[] pts1, IVec[] pts2,
				       boolean cyclic, boolean reverse){
	return isArrayEqual(pts1,pts2,cyclic,reverse,IConfig.tolerance);
    }
    public static boolean isArrayEqual(IVec[] pts1, IVec[] pts2,
				       boolean cyclic, boolean reverse, double resolution){
	
	if(pts1.length!=pts2.length) return false;
	int num = pts1.length;
	if(!cyclic){
	    for(int i=0; i<num; i++)
		if(!pts1[i].eq(pts2[i],resolution)) if(!reverse) return false;
	    if(!reverse) return true;
	    // reverse
	    for(int i=0; i<num; i++)
		if(!pts1[i].eq(pts2[num-1-i],resolution)) return false;
	    return true;
	}
	
	// cyclic
	for(int i=0; i<num; i++){
	    boolean same=true;
	    for(int j=0; j<num; j++)
		if(!pts1[j].eq(pts2[(j+i)%num],resolution)) same=false;
	    if(same) return true;
	}
	if(!reverse) return false;
	
	// reverse
	for(int i=0; i<num; i++){
	    boolean same=true;
	    for(int j=0; j<num; j++)
		if(!pts1[j].eq(pts2[(j+i)%num],resolution)) same=false;
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
	    IVec nml = pts[1].get().diff(pts[0]).cross(xaxis); /*default*/
	    if(!nml.eq(origin)) return nml.unit();
	    return pts[1].get().diff(pts[0]).cross(yaxis).unit(); /*default*/
	}
	
        if(n==3) return pts[1].get().diff(pts[0]).cross(pts[2].get().diff(pts[1])).unit();
        
        IVec nml = new IVec();
        for(int i=0; i<n; i++){
            IVec diff1 = pts[(i+1)%n].get().diff(pts[i]);
            IVec diff2 = pts[(i+2)%n].get().diff(pts[(i+1)%n]);
            nml.add(diff1.cross(diff2));
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
		nml.add(pts[i+1][j].diff(pts[i][j]).cross(pts[i][j+1].diff(pts[i][j])));
		nml.add(pts[i+1][j+1].diff(pts[i+1][j]).cross(pts[i][j].diff(pts[i+1][j])));
		nml.add(pts[i][j+1].diff(pts[i+1][j+1]).cross(pts[i+1][j].diff(pts[i+1][j+1])));
		nml.add(pts[i][j].diff(pts[i][j+1]).cross(pts[i+1][j+1].diff(pts[i][j+1])));
	    }
	}
	
	// in case nml is zero
	if(nml.eq(origin)) return new IVec(0,0,1); /* default */
	
        return nml.unit();
    }
    

    
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
    
    
    public static IVecI[] offset(IVecI[] pts, double width, IVecI planeNormal){
	if(pts==null){ IOut.err("pts is null"); return null; }
	
	int num = pts.length;
        IVecI[] pts2 = new IVecI[num];
        
        for(int i=0; i<num; i++) pts2[i] = pts[i].dup();
        if(width==0) return pts2; 
        
        IVecI[] normal = new IVecI[num-1];
        for(int i=0; i<num-1; i++){
            IVecI dir = pts[i+1].diff(pts[i]);
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
            IVecI dir = pts[i+1].diff(pts[i]);
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
	    IVecI off1 = pts[num-1].diff(pts[num-2]).cross(normal[0]).len(width);
	    IVecI off2 = pts[1].diff(pts[0]).cross(normal[0]).len(width);
	    IVecI v = off1.add(off2);
            v.mul(2*width*width/v.len2());
            pts2[0].add(v);
            pts2[num-1].add(v);
	}
        else{
	    IVecI off1 = pts[1].diff(pts[0]).cross(normal[0]).len(width);
	    IVecI off2 = pts[num-1].diff(pts[num-2]).cross(normal[num-1]).len(width);
            pts2[0].add(off1);
            pts2[num-1].add(off2);
        }
	
	for(int i=1; i<num-1; i++){
	    IVecI off1 = pts[i].diff(pts[i-1]).cross(normal[i]).len(width);
	    IVecI off2 = pts[i+1].diff(pts[i]).cross(normal[i]).len(width);
	    
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
	    IVecI off1 = pts[num-1].diff(pts[num-2]).cross(normal[0]).len(width);
	    IVecI off2 = pts[1].diff(pts[0]).cross(normal[0]).len(width);
	    IVecI v = off1.add(off2);
            v.mul(width.dup().pow(2).mul(2).div(v.len2(Ir.i)));
            pts2[0].add(v);
            pts2[num-1].add(v);
        }
        else{
	    IVecI off1 = pts[1].diff(pts[0]).cross(normal[0]).len(width);
	    IVecI off2 = pts[num-1].diff(pts[num-2]).cross(normal[num-1]).len(width);
            pts2[0].add(off1);
            pts2[num-1].add(off2);
        }
	
	for(int i=1; i<num-1; i++){
	    IVecI off1 = pts[i].diff(pts[i-1]).cross(normal[i]).len(width);
	    IVecI off2 = pts[i+1].diff(pts[i]).cross(normal[i]).len(width);
	    
	    IVecI v = off1.add(off2);
            v.mul(width.dup().pow(2).mul(2).div(v.len2(Ir.i)));
            pts2[i].add(v);
        }
        return pts2;
    }
    
    
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
	    IVecI diff = pts[1].diff(pts[0]);
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
		    if(normal.get().isParallel(pts[i+1].diff(pts[i]))){
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
	    //IVecI diff = pts[i+1].diff(pts[i]);
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
	    IVecI diff = pts[1].diff(pts[0]);
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
		    if(normal.get().isParallel(pts[i+1].diff(pts[i]))){
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
	    //IVecI diff = pts[i+1].diff(pts[i]);
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
    
}
