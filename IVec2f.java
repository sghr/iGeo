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
   Class of 2 dimensional vector in float.
   
   @author Satoru Sugihara
*/
public class IVec2f extends IParameterObject implements IVec2I{
    public float x, y;
    
    public IVec2f(){}
    public IVec2f(float x, float y){ this.x=x; this.y=y; }
    public IVec2f(double x, double y){ this.x=(float)x; this.y=(float)y; }
    public IVec2f(IVec2f v){ x=v.x; y=v.y; }
    public IVec2f(IVec2 v){ x=(float)v.x; y=(float)v.y; }    
    public IVec2f(IVec2I v){ IVec2 u=v.get(); x=(float)u.x; y=(float)u.y; }
    public IVec2f(IDoubleI x, IDoubleI y){ this.x=(float)x.x(); this.y=(float)y.x(); }
    
    public IVec2f(IServerI s){ super(s); }
    public IVec2f(IServerI s, float x, float y){ super(s); this.x=x; this.y=y; }
    public IVec2f(IServerI s, double x, double y){ super(s); this.x=(float)x; this.y=(float)y; }
    public IVec2f(IServerI s, IVec2f v){ super(s); x=v.x; y=v.y; }
    public IVec2f(IServerI s, IVec2 v){ super(s); x=(float)v.x; y=(float)v.y; }    
    public IVec2f(IServerI s, IVec2I v){ super(s); IVec2 u=v.get(); x=(float)u.x; y=(float)u.y; }
    public IVec2f(IServerI s, IDoubleI x, IDoubleI y){ super(s); this.x=(float)x.x(); this.y=(float)y.x(); }
    
    public double x(){ return x; }
    public double y(){ return y; }
    
    /** setting x component */
    public IVec2f x(double vx){ x=(float)vx; return this; }
    /** setting y component */
    public IVec2f y(double vy){ y=(float)vy; return this; }

    /** setting x component */
    public IVec2f x(float vx){ x=vx; return this; }
    /** setting y component */
    public IVec2f y(float vy){ y=vy; return this; }
    
    /** setting x component */
    public IVec2f x(IDoubleI vx){ x=(float)vx.x(); return this; }
    /** setting y component */
    public IVec2f y(IDoubleI vy){ y=(float)vy.x(); return this; }
    
    /** getting x component */
    public double x(ISwitchE e){ return x(); }
    /** getting y component */
    public double y(ISwitchE e){ return y(); }
    
    /** getting x component */
    public IDouble x(ISwitchR r){ return new IDouble(x); }
    /** getting y component */
    public IDouble y(ISwitchR r){ return new IDouble(y); }
    
    
    public IVec2 get(){ return new IVec2(x,y); }
    public IVec2f dup(){ return new IVec2f(this); }
    
    
    public IVec to3d(){ return new IVec(this); }
    public IVec to3d(double z){ return new IVec(x,y,z); }
    public IVec to3d(IDoubleI z){ return new IVec(x,y,z.x()); }
    public IVec4 to4d(){ return new IVec4(x,y,0.); }
    public IVec4 to4d(double z, double w){ return new IVec4(x,y,z,w); }
    public IVec4 to4d(IDoubleI z, IDoubleI w){ return new IVec4(x,y,z.x(),w.x()); }
    
    public IDouble getX(){ return new IDouble(x); }
    public IDouble getY(){ return new IDouble(y); }
    
    public IVec2f set(float x, float y){ this.x=x; this.y=y; return this; }
    public IVec2f set(double x, double y){ this.x=(float)x; this.y=(float)y; return this; }
    public IVec2f set(IVec2f v){ x=v.x; y=v.y; return this; }
    public IVec2f set(IVec2 v){ x=(float)v.x; y=(float)v.y; return this; }
    public IVec2f set(IVec2I v){ return set(v.get()); }
    public IVec2f set(IDoubleI x, IDoubleI y){ this.x=(float)x.x(); this.y=(float)y.x(); return this; }
    
    public IVec2f add(float x, float y){ this.x+=x; this.y+=y; return this; }
    public IVec2f add(double x, double y){ this.x+=x; this.y+=y; return this; }
    public IVec2f add(IDoubleI x, IDoubleI y){ this.x+=x.x(); this.y+=y.x(); return this; }
    public IVec2f add(IVec2f v){ x+=v.x; y+=v.y; return this; }
    public IVec2f add(IVec2 v){ x+=v.x; y+=v.y; return this; }
    public IVec2f add(IVec2I v){ return add(v.get()); }
    
    public IVec2f sub(float x, float y){ this.x-=x; this.y-=y; return this; }
    public IVec2f sub(double x, double y){ this.x-=x; this.y-=y; return this; }
    public IVec2f sub(IDoubleI x, IDoubleI y){ this.x-=x.x(); this.y-=y.x(); return this; }
    public IVec2f sub(IVec2f v){ x-=v.x; y-=v.y; return this; }
    public IVec2f sub(IVec2 v){ x-=v.x; y-=v.y; return this; }
    public IVec2f sub(IVec2I v){ return sub(v.get()); }
    
    public IVec2f mul(float x){ this.x*=x; this.y*=x; return this; }
    public IVec2f mul(double x){ this.x*=x; this.y*=x; return this; }
    //public IVec2f mul(IDouble v){ return mul(v.x); }
    public IVec2f mul(IDoubleI v){ return mul(v.x()); }
    
    public IVec2f div(float x){ this.x/=x; this.y/=x; return this; }
    public IVec2f div(double x){ this.x/=x; this.y/=x; return this; }
    //public IVec2f div(IDouble v){ return div(v.x); }
    public IVec2f div(IDoubleI v){ return div(v.x()); }
    
    public IVec2f neg(){ x=-x; y=-y; return this; }
    public IVec2f rev(){ return neg(); }
    public IVec2f flip(){ return neg(); }
    
    public IVec2f zero(){ x=0; y=0; return this; }
    
    
    /** scale add */
    public IVec2f add(IVec2f v, float f){ x+=v.x*f; y+=v.y*f; return this; }
    public IVec2f add(IVec2I v, double f){ return add(v.get(),f); }
    public IVec2f add(IVec2I v, IDoubleI f){ add(v.get(),f.x()); return this; }
    
    /** scale add; alias */
    public IVec2f add(float f, IVec2f v){ return add(v,f); }
    public IVec2f add(double f, IVec2I v){ return add(v,f); }
    public IVec2f add(IDoubleI f, IVec2I v){ return add(v,f); }
    
    
    /**
       @return dot product in double
    */
    public float dot(IVec2f v){ return x*v.x + y*v.y; }
    public double dot(IVec2 v){ return x*v.x + y*v.y; }
    public double dot(double vx, double vy){ return x*vx + y*vy; }
    public float dot(float vx, float vy){ return x*vx + y*vy; }
    public double dot(IVec2I v){ return dot(v.get()); }
    //public IDouble dotR(IVec2f v){ return new IDouble(dot(v)); }
    //public IDouble dotR(IVec2 v){ return new IDouble(dot(v)); }
    //public IDouble dotR(IVec2I v){ return dotR(v.get()); }
    public double dot(ISwitchE e, IVec2I v){ return dot(v); }
    public IDouble dot(ISwitchR r, IVec2I v){ return new IDouble(dot(v)); }
    
    
    /**
       @return cross product in z direction
    */
    public IVec cross(IVec2f v){ return new IVec(0, 0, x*v.y - y*v.x); }
    public IVec cross(IVec2 v){ return new IVec(0, 0, x*v.y - y*v.x); }
    public IVec cross(IVec2I v){ return cross(v.get()); }
    public IVec cross(double vx, double vy){ return new IVec(0, 0, x*vy - y*vx); }
    public IVec cross(float vx, float vy){ return new IVec(0, 0, x*vy - y*vx); }
    
    
    /**
       @return length (norm) of the vector in double
    */
    public double len(){ return Math.sqrt(x*x+y*y); }
    public double len(ISwitchE e){ return len(); }
    public IDouble len(ISwitchR r){ return new IDouble(len()); }
    
    /**
       @return squared length of the vector in double
    */
    public double len2(){ return x*x+y*y; }
    public double len2(ISwitchE e){ return len2(); }
    public IDouble len2(ISwitchR r){ return new IDouble(len2()); }
    
    public IVec2f len(IDoubleI l){ return len(l.x()); }
    public IVec2f len(double l){ l/=len(); x*=l; y*=l; return this; }
    public IVec2f len(float l){ l/=len(); x*=l; y*=l; return this; }
    
    
    /**
       normalize (unitize) vector
    */
    public IVec2f unit(){ double l=len(); x/=l; y/=l; return this; }
    /**
       rotate vector in Pi/2
    */
    public IVec2f ortho(){ float tmp = x; x=-y; y=tmp; return this; }
    
    
    public double dist(IVec2I v){ return dist(v.get()); }
    public double dist(IVec2 v){ return Math.sqrt((x-v.x)*(x-v.x)+(y-v.y)*(y-v.y)); }
    public double dist(IVec2f v){ return Math.sqrt((x-v.x)*(x-v.x)+(y-v.y)*(y-v.y)); }
    
    public double dist(double vx, double vy){ return Math.sqrt((x-vx)*(x-vx)+(y-vy)*(y-vy)); }
    public double dist(float vx, float vy){ return Math.sqrt((x-vx)*(x-vx)+(y-vy)*(y-vy)); }
    public double dist(ISwitchE e, IVec2I v){ return dist(v); }
    public IDouble dist(ISwitchR r, IVec2I v){ return new IDouble(dist(v)); }
    
    public double dist2(IVec2I v){ return dist2(v.get()); }
    public double dist2(IVec2 v){ return (x-v.x)*(x-v.x)+(y-v.y)*(y-v.y); }
    public float dist2(IVec2f v){ return (x-v.x)*(x-v.x)+(y-v.y)*(y-v.y); }
    public double dist2(double vx, double vy){ return (x-vx)*(x-vx)+(y-vy)*(y-vy); }
    public float dist2(float vx, float vy){ return (x-vx)*(x-vx)+(y-vy)*(y-vy); }
    public double dist2(ISwitchE e, IVec2I v){ return dist2(v); }
    public IDouble dist2(ISwitchR r, IVec2I v){ return new IDouble(dist2(v)); }
    
    public boolean eq(IVec2I v){ return eq(v.get(),IConfig.tolerance); }
    public boolean eq(IVec2 v){ return eq(v,IConfig.tolerance); }
    public boolean eq(IVec2f v){ return eq(v,IConfig.tolerance); }
    public boolean eq(double vx, double vy){ return eq(vx,vy,IConfig.tolerance); }
    public boolean eq(float vx, float vy){ return eq(vx,vy,IConfig.tolerance); }
    public boolean eq(ISwitchE e, IVec2I v){ return eq(v); }
    public IBool eq(ISwitchR r, IVec2I v){ return new IBool(eq(v)); }
    
    
    public boolean eq(IVec2I v, double tolerance){ return eq(v.get(),tolerance); }
    public boolean eq(IVec2 v, double tolerance){ return dist2(v) <= tolerance*tolerance; }
    public boolean eq(IVec2f v, double tolerance){ return dist2(v) <= tolerance*tolerance; }
    public boolean eq(double vx, double vy, double tolerance){ return dist2(vx,vy) <= tolerance*tolerance; }
    public boolean eq(float vx, float vy, double tolerance){ return dist2(vx,vy) <= tolerance*tolerance; }
    public boolean eq(ISwitchE e, IVec2I v, double tolerance){ return eq(v,tolerance); }
    public IBool eq(ISwitchR r, IVec2I v, IDoubleI tolerance){ return new IBool(eq(v,tolerance.x())); }
    
    
    public boolean eqX(IVec2f v){ return eqX(v,IConfig.tolerance); }
    public boolean eqY(IVec2f v){ return eqY(v,IConfig.tolerance); }
    public boolean eqX(IVec2 v){ return eqX(v,IConfig.tolerance); }
    public boolean eqY(IVec2 v){ return eqY(v,IConfig.tolerance); }
    public boolean eqX(double vx){ return eqX(vx,IConfig.tolerance); }
    public boolean eqY(double vy){ return eqY(vy,IConfig.tolerance); }
    public boolean eqX(float vx){ return eqX(vx,IConfig.tolerance); }
    public boolean eqY(float vy){ return eqY(vy,IConfig.tolerance); }
    public boolean eqX(IVec2I v){ return eqX(v.get(),IConfig.tolerance); }
    public boolean eqY(IVec2I v){ return eqY(v.get(),IConfig.tolerance); }
    public boolean eqX(ISwitchE e, IVec2I v){ return eqX(v); }
    public boolean eqY(ISwitchE e, IVec2I v){ return eqY(v); }
    public IBool eqX(ISwitchR r,IVec2I v){ return new IBool(eqX(v)); }
    public IBool eqY(ISwitchR r,IVec2I v){ return new IBool(eqY(v)); }
    
    
    public boolean eqX(IVec2f v, double tolerance){ return Math.abs(x-v.x)<=tolerance; }
    public boolean eqY(IVec2f v, double tolerance){ return Math.abs(y-v.y)<=tolerance; }
    public boolean eqX(IVec2 v, double tolerance){ return Math.abs(x-v.x)<=tolerance; }
    public boolean eqY(IVec2 v, double tolerance){ return Math.abs(y-v.y)<=tolerance; }
    public boolean eqX(double vx, double tolerance){ return Math.abs(x-vx)<=tolerance; }
    public boolean eqY(double vy, double tolerance){ return Math.abs(y-vy)<=tolerance; }
    public boolean eqX(float vx, double tolerance){ return Math.abs(x-vx)<=tolerance; }
    public boolean eqY(float vy, double tolerance){ return Math.abs(y-vy)<=tolerance; }
    public boolean eqX(IVec2I v, double tolerance){ return Math.abs(x-v.x())<=tolerance; }
    public boolean eqY(IVec2I v, double tolerance){ return Math.abs(y-v.y())<=tolerance; }
    public boolean eqX(ISwitchE e, IVec2I v, double tolerance){ return eqX(v,tolerance); }
    public boolean eqY(ISwitchE e, IVec2I v, double tolerance){ return eqY(v,tolerance); }
    public IBool eqX(ISwitchR r, IVec2I v, IDoubleI tolerance){ return new IBool(eqX(v,tolerance.x())); }
    public IBool eqY(ISwitchR r, IVec2I v, IDoubleI tolerance){ return new IBool(eqY(v,tolerance.x())); }
    
    
    /**
       @return angle of two vector. From -Pi to Pi. Sign follows right-handed screw rule
    */
    public double angle(IVec2 v){
	double dot = x*v.x+y*v.y;
	double len1 = len(); if(len1==0) return 0;
	double len2 = v.len(); if(len2==0) return 0;
	double cross = x*v.y-y*v.x; if(cross==0) return 0;
	double cos = dot/(len1*len2);
	if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
	double angle = Math.acos(cos);
	if(cross<0) return -angle; // negative
	return angle;
    }
    public float angle(IVec2f v){
	float dot = x*v.x+y*v.y;
	float len1 = (float)len(); if(len1==0) return 0;
	float len2 = (float)v.len(); if(len2==0) return 0;
	float cross = x*v.y-y*v.x; if(cross==0) return 0;
	float cos = dot/(len1*len2);
	if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
	float angle = (float)Math.acos(cos);
	if(cross<0) return -angle; // negative
	return angle;
    }
    public double angle(double vx, double vy){
	double dot = x*vx+y*vy;
	double len1 = len(); if(len1==0) return 0;
	double len2 = Math.sqrt(vx*vx+vy*vy); if(len2==0) return 0;
	double cross = x*vy-y*vx; if(cross==0) return 0;
	double cos = dot/(len1*len2);
	if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
	double angle = Math.acos(cos);
	if(cross<0) return -angle; // negative
	return angle;
    }
    public float angle(float vx, float vy){
	float dot = x*vx+y*vy;
	float len1 = (float)len(); if(len1==0) return 0;
	float len2 = (float)Math.sqrt(vx*vx+vy*vy); if(len2==0) return 0;
	float cross = x*vy-y*vx; if(cross==0) return 0;
	float cos = dot/(len1*len2);
	if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
	float angle = (float)Math.acos(cos);
	if(cross<0) return -angle; // negative
	return angle;
    }
    
    public double angle(IVec2I v){ return angle(v.get()); }
    public double angle(ISwitchE e, IVec2I v){ return angle(v); }
    public IDouble angle(ISwitchR r, IVec2I v){ return new IDouble(angle(v)); }
    
    
    public IVec2f rot(double angle){
	float origx = x;
	x = (float)(x*Math.cos(angle) - y*Math.sin(angle));
	y = (float)(origx*Math.sin(angle) + y*Math.cos(angle));
	return this;
    }
    public IVec2f rot(float angle){
	float origx = x;
	x = x*(float)Math.cos(angle) - y*(float)Math.sin(angle);
	y = origx*(float)Math.sin(angle) + y*(float)Math.cos(angle);
	return this;
    }
    public IVec2f rot(IDoubleI angle){ return rot(angle.x()); }
    
    public IVec2f rot(IVec2f center, double angle){
	if(center==this) return this;
	return sub(center).rot(angle).add(center);
    }
    public IVec2f rot(IVec2 center, double angle){
	return sub(center).rot(angle).add(center);
    }
    public IVec2f rot(double centerX, double centerY, double angle){
	return sub(centerX,centerY).rot(angle).add(centerX,centerY);
    }
    public IVec2f rot(float centerX, float centerY, float angle){
	return sub(centerX,centerY).rot(angle).add(centerX,centerY);
    }
    public IVec2f rot(IVec2I center, double angle){ return rot(center.get(),angle); }
    public IVec2f rot(IVec2I center, IDoubleI angle){ return rot(center.get(),angle.x()); }    
    
    // to be tested !!! (direction of rotation)
    public IVec2f rot(IVec2f destDir){ return rot(angle(destDir)); }
    public IVec2f rot(IVec2 destDir){ return rot(angle(destDir)); }
    public IVec2f rot(IVec2I destDir){ return rot(destDir.get()); }
    public IVec2f rot(IVec2f center, IVec2f destPt){
	if(center==this) return this;
	return sub(center).rot(destPt.diff(center)).add(center);
    }
    public IVec2f rot(IVec2 center, IVec2 destPt){
	return sub(center).rot(destPt.diff(center)).add(center);
    }
    public IVec2f rot(IVec2I center, IVec2I destPt){ return rot(center.get(),destPt.get()); }
    
    
    public IVec2f scale(double f){ return mul(f); }
    public IVec2f scale(float f){ return mul(f); }
    public IVec2f scale(IDoubleI f){ return mul(f); }
    public IVec2f scale(IVec2f center, double f){
	if(center==this) return this;
	return sub(center).mul(f).add(center);
    }
    public IVec2f scale(IVec2 center, double f){
	return sub(center).mul(f).add(center);
    }
    public IVec2f scale(IVec2I center, double f){ return scale(center.get(),f); }
    public IVec2f scale(IVec2I center, IDoubleI f){ return scale(center.get(),f.x()); }
    public IVec2f scale(double centerX, double centerY, double f){
	return sub(centerX,centerY).mul(f).add(centerX,centerY);
    }
    public IVec2f scale(float centerX, float centerY, float f){
	return sub(centerX,centerY).mul(f).add(centerX,centerY);
    }
    
    
    /** scale only in 1 direction */
    public IVec2f scale1d(IVec2f axis, float f){
	IVec2f n = axis.dup().unit();
        n.mul(this.dot(n));
        IVec2f t = this.dif(n);
        return this.set(n.mul(f).add(t));
    }
    /** scale only in 1 direction */
    public IVec2f scale1d(IVec2 axis, double f){
	IVec2 n = axis.dup().unit();
        n.mul(this.dot(n));
        IVec2f t = this.dif(n);
        return this.set(n.mul(f).add(t));
    }
    /** scale only in 1 direction */
    public IVec2f scale1d(IVec2I axis, double f){ return scale1d(axis.get(),f); }
    /** scale only in 1 direction */
    public IVec2f scale1d(IVec2I axis, IDoubleI f){ return scale1d(axis.get(),f.x()); }
    /** scale only in 1 direction */
    public IVec2f scale1d(float axisX, float axisY, float f){
        float len = (float)Math.sqrt(axisX*axisX+axisY*axisY);
        axisX/=len; axisY/=len; 
        float dt = dot(axisX,axisY);
        axisX*=dt; axisY*=dt;
        x = axisX*f + x-axisX;
        y = axisY*f + y-axisY;
        return this;
    }
    /** scale only in 1 direction */
    public IVec2f scale1d(double axisX, double axisY, double f){
	return scale1d((float)axisX,(float)axisY,(float)f);
    }
    /** scale only in 1 direction from a center */
    public IVec2f scale1d(IVec2f center, IVec2f axis, double f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    /** scale only in 1 direction from a center */
    public IVec2f scale1d(IVec2I center, IVec2I axis, double f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    /** scale only in 1 direction from a center */
    public IVec2f scale1d(IVec2I center, IVec2I axis, IDoubleI f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    /** scale only in 1 direction from a center */
    public IVec2f scale1d(float centerX, float centerY, float axisX, float axisY, float f){
	return sub(centerX,centerY).scale1d(axisX,axisY,f).add(centerX,centerY);
    }
    /** scale only in 1 direction from a center */
    public IVec2f scale1d(double centerX, double centerY, double axisX, double axisY, double f){
	return sub(centerX,centerY).scale1d(axisX,axisY,f).add(centerX,centerY);
    }
    
    
    /**
       reflect (mirror) 2 dimensionally to the other side of the line
       @param lineDir direction of reflection line
    */
    public IVec2f ref(IVec2f lineDir){
        return rev().add(lineDir.dup().mul(dot(lineDir)/lineDir.len2()*-2));
    }
    public IVec2f ref(IVec2 lineDir){
	return rev().add(lineDir.dup().mul(dot(lineDir)/lineDir.len2()*-2));
    }
    public IVec2f ref(double lineX, double lineY){
	double d = dot(lineX,lineY)/(lineX*lineX+lineY*lineY)*-2;
	x = -x + (float)(lineX*d);
	y = -y + (float)(lineY*d);
        return this;
    }
    public IVec2f ref(float lineX, float lineY){
	float d = dot(lineX,lineY)/(lineX*lineX+lineY*lineY)*-2;
	x = -x + lineX*d;
	y = -y + lineY*d;
        return this;
    }
    public IVec2f ref(IVec2I lineDir){ return ref(lineDir.get()); }
    public IVec2f ref(IVec2f linePt, IVec2f lineDir){ //public IVec2f ref(IVec2f linePt, IVec2 lineDir){
	if(linePt==this) return this;
	return sub(linePt).ref(lineDir).add(linePt);
    }
    public IVec2f ref(IVec2 linePt, IVec2 lineDir){
	return sub(linePt).ref(lineDir).add(linePt);
    }
    public IVec2f ref(double linePtX, double linePtY, double lineDirX, double lineDirY){
	return sub(linePtX,linePtY).ref(lineDirX,lineDirY).add(linePtX,linePtY);
    }
    public IVec2f ref(float linePtX, float linePtY, float lineDirX, float lineDirY){
	return sub(linePtX,linePtY).ref(lineDirX,lineDirY).add(linePtX,linePtY);
    }
    public IVec2f ref(IVec2I linePt, IVec2I lineDir){ return ref(linePt.get(),lineDir.get()); }
    
    public IVec2f mirror(IVec2f lineDir){ return ref(lineDir); }
    public IVec2f mirror(IVec2 lineDir){ return ref(lineDir); }
    public IVec2f mirror(IVec2I lineDir){ return ref(lineDir.get()); }
    public IVec2f mirror(double lineX, double lineY){ return ref(lineX,lineY); }
    public IVec2f mirror(float lineX, float lineY){ return ref(lineX,lineY); }
    //public IVec2f mirror(IVec2f linePt, IVec2 lineDir){ return ref(linePt,lineDir); }
    public IVec2f mirror(IVec2f linePt, IVec2f lineDir){ return ref(linePt,lineDir); }
    public IVec2f mirror(IVec2 linePt, IVec2 lineDir){ return ref(linePt,lineDir); }
    public IVec2f mirror(IVec2I linePt, IVec2I lineDir){ return ref(linePt.get(),lineDir.get()); }
    public IVec2f mirror(double linePtX, double linePtY, double lineDirX, double lineDirY){
	return ref(linePtX,linePtY,lineDirX,lineDirY);
    }
    public IVec2f mirror(float linePtX, float linePtY, float lineDirX, float lineDirY){
	return ref(linePtX,linePtY,lineDirX,lineDirY);
    }
    
    /** shear operation on XY*/
    public IVec2f shear(float sxy, float syx){
	float tx,ty;
        tx =     x + sxy*y;
        ty = syx*x +     y;
        x = tx; y = ty;
        return this;
    }
    /** shear operation on XY*/
    public IVec2f shear(double sxy, double syx){ return shear((float)sxy,(float)syx); }
    /** shear operation on XY*/
    public IVec2f shear(IDoubleI sxy, IDoubleI syx){ return shear(sxy.x(),syx.x()); }
    /** shear operation on XY*/
    public IVec2f shear(IVec2f center, float sxy, float syx){
	if(center==this) return this;
	return sub(center).shear(sxy,syx).add(center);
    }
    /** shear operation on XY*/
    public IVec2f shear(IVec2I center, double sxy, double syx){
	if(center==this) return this;
	return sub(center).shear(sxy,syx).add(center);
    }
    /** shear operation on XY*/
    public IVec2f shear(IVec2I center, IDoubleI sxy, IDoubleI syx){
	return shear(center,sxy.x(),syx.x());
    }
    
    
    /** alias of add() */
    public IVec2f translate(float x, float y){ return add(x,y); }
    /** alias of add() */
    public IVec2f translate(double x, double y){ return add(x,y); }
    /** alias of add() */
    public IVec2f translate(IDoubleI x, IDoubleI y){ return add(x,y); }
    /** alias of add() */
    public IVec2f translate(IVec2f v){ return add(v); }
    /** alias of add() */
    public IVec2f translate(IVec2I v){ return add(v); }
    
    
    public IVec2f transform(IMatrix2I mat){ return set(mat.mul(this)); }
    
    public IVec2f transform(IMatrix3I mat){ return set(mat.mul(this)); }
    
    public IVec2f transform(IVec2f xvec, IVec2f yvec){
	float tx,ty;
	tx = xvec.x*x + yvec.x*y;
	ty = xvec.y*x + yvec.y*y;
	x = tx; y = ty;
	return this;
    }
    public IVec2f transform(IVec2 xvec, IVec2 yvec){
	double tx,ty;
	tx = xvec.x*x + yvec.x*y;
	ty = xvec.y*x + yvec.y*y;
	x = (float)tx; y = (float)ty;
	return this;
    }
    public IVec2f transform(IVec2I xvec, IVec2I yvec){ return transform(xvec.get(),yvec.get()); }
    public IVec2f transform(IVec2f xvec, IVec2f yvec, IVec2f translate){
	return transform(xvec,yvec).add(translate);
    }
    public IVec2f transform(IVec2 xvec, IVec2 yvec, IVec2 translate){
	return transform(xvec,yvec).add(translate);
    }
    public IVec2f transform(IVec2I xvec, IVec2I yvec, IVec2I translate){
	return transform(xvec.get(),yvec.get(),translate.get());
    }
    
    
    /** mv() is alias of add() */
    public IVec2f mv(float x, float y){ return add(x,y); }
    /** mv() is alias of add() */
    public IVec2f mv(double x, double y){ return add(x,y); }
    /** mv() is alias of add() */
    public IVec2f mv(IDoubleI x, IDoubleI y){ return add(x,y); }
    /** mv() is alias of add() */
    public IVec2f mv(IVec2f v){ return add(v); }
    /** mv() is alias of add() */
    public IVec2f mv(IVec2I v){ return add(v); }
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */
    public IVec2f cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IVec2f cp(float x, float y){ return dup().add(x,y); }
    /** cp() is alias of dup().add() */
    public IVec2f cp(double x, double y){ return dup().add(x,y); }
    /** cp() is alias of dup().add() */
    public IVec2f cp(IDoubleI x, IDoubleI y){ return dup().add(x,y); }
    /** cp() is alias of dup().add() */
    public IVec2f cp(IVec2f v){ return dup().add(v); }
    /** cp() is alias of dup().add() */
    public IVec2f cp(IVec2I v){ return dup().add(v); }
    
    
    // methods creating new instance
    public IVec2f dif(IVec2I v){ return dup().sub(v); }
    public IVec2f dif(IVec2f v){ return new IVec2f(x-v.x, y-v.y); }
    public IVec2f dif(double vx, double vy){ return new IVec2f(x-vx, y-vy); }
    public IVec2f dif(float vx, float vy){ return new IVec2f(x-vx, y-vy); }
        
    public IVec2f diff(IVec2I v){ return dif(v); }
    public IVec2f diff(IVec2f v){ return dif(v); }
    public IVec2f diff(double vx, double vy){ return dif(vx,vy); }
    public IVec2f diff(float vx, float vy){ return dif(vx,vy); }
    
    public IVec2f mid(IVec2I v){ return dup().add(v).div(2); }
    public IVec2f mid(IVec2f v){ return new IVec2f((x+v.x)/2, (y+v.y)/2); }
    public IVec2f mid(double vx, double vy){ return new IVec2f((x+vx)/2, (y+vy)/2); }
    public IVec2f mid(float vx, float vy){ return new IVec2f((x+vx)/2, (y+vy)/2); }
    
    public IVec2f sum(IVec2I v){ return dup().add(v); }
    public IVec2f sum(IVec2f v){ return new IVec2f(x+v.x, y+v.y); }
    public IVec2f sum(double vx, double vy){ return new IVec2f(x+vx, y+vy); }
    public IVec2f sum(float vx, float vy){ return new IVec2f(x+vx, y+vy); }
    
    public IVec2f sum(IVec2I... v){
        IVec2f ret = this.dup();
        for(IVec2I vi: v) ret.add(vi);
        return ret;
    }
    
    public IVec2f bisect(IVec2f v){ return dup().unit().add(v.dup().unit()); }
    public IVec2f bisect(IVec2 v){ return dup().unit().add(v.dup().unit()); }
    public IVec2f bisect(IVec2I v){ return bisect(v.get()); }
    public IVec2f bisect(double vx, double vy){
	double len = Math.sqrt(vx*vx+vy*vy);
	return dup().unit().add(vx/len,vy/len);
    }
    
    /**
       weighted sum
    */
    public IVec2f sum(IVec2f v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    public IVec2f sum(IVec2 v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    public IVec2f sum(IVec2I v2, double w1, double w2){ return sum(v2.get(),w1,w2); }
    public IVec2f sum(IVec2I v2, IDoubleI w1, IDoubleI w2){ return sum(v2.get(),w1.x(),w2.x()); }
    public IVec2f sum(IVec2f v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    public IVec2f sum(IVec2 v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    public IVec2f sum(IVec2I v2, double w2){ return sum(v2.get(),w2); }
    public IVec2f sum(IVec2I v2, IDoubleI w2){ return sum(v2.get(),w2.get()); }

    
    /** alias of cross */
    public IVec nml(IVec2I v){ return cross(v); }
    /** alias of cross */
    public IVec nml(double vx, double vy){ return cross(vx,vy); }
    /** alias of cross */
    public IVec nml(float vx, float vy){ return cross(vx,vy); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVec nml(IVec2I pt1, IVec2I pt2){
	return this.dif(pt1).cross(this.dif(pt2));
    }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVec nml(double vx1, double vy1, double vx2, double vy2){
	return this.dif(vx1,vy1).cross(this.dif(vx2,vy2));
    }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVec nml(float vx1, float vy1, float vx2, float vy2){
	return this.dif(vx1,vy1).cross(this.dif(vx2,vy2));
    }
    
    /** checking x, y is valid number (not Infinite, nor NaN). */
    public boolean isValid(){
        if(!IDouble.isValid(x)){ IOut.err("invalid x ("+x+")"); return false; }
        if(!IDouble.isValid(y)){ IOut.err("invalid y ("+y+")"); return false; }
        return true;
    }
    
}
