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

import java.util.ArrayList;

/**
   Class of 2 dimensional vector.
   
   @author Satoru Sugihara
*/
public class IVec2 extends IParameterObject implements IVec2I, IEntityParameter{
    public double x, y;
    
    public IVec2(){}
    public IVec2(double x, double y){ this.x=x; this.y=y; }
    public IVec2(IVec2 v){ x=v.x; y=v.y; }
    public IVec2(IVec2I v){ IVec2 u=v.get(); x=u.x; y=u.y; }
    public IVec2(IDoubleI x, IDoubleI y){ this.x=x.x(); this.y=y.x(); }
    public IVec2(IVecI v){ x = v.x(); y = v.y(); }
    public IVec2(IVecI v, IVecI projectionDir){ set(v,projectionDir); }
    public IVec2(IVecI v, IVecI xaxis, IVecI yaxis){ set(v,xaxis,yaxis); }
    public IVec2(IVecI v, IVecI xaxis, IVecI yaxis, IVecI origin){ set(v,xaxis,yaxis,origin); }
    
    public IVec2(IServerI s){ super(s); }
    public IVec2(IServerI s, double x, double y){ super(s); this.x=x; this.y=y; }
    public IVec2(IServerI s, IVec2 v){ super(s); x=v.x; y=v.y; }
    public IVec2(IServerI s, IVec2I v){ super(s); IVec2 u=v.get(); x=u.x; y=u.y; }
    public IVec2(IServerI s, IDoubleI x, IDoubleI y){ super(s); this.x=x.x(); this.y=y.x(); }
    public IVec2(IServerI s, IVecI v){ super(s); x = v.x(); y = v.y(); }
    public IVec2(IServerI s, IVecI v, IVecI projectionDir){ super(s); set(v,projectionDir); }
    public IVec2(IServerI s, IVecI v, IVecI xaxis, IVecI yaxis){ super(s); set(v,xaxis,yaxis); }
    public IVec2(IServerI s, IVecI v, IVecI xaxis, IVecI yaxis, IVecI origin){ super(s); set(v,xaxis,yaxis,origin); }
    
    
    public double x(){ return x; }
    public double y(){ return y; }
    
    /** setting x component */
    public IVec2 x(double vx){ x=vx; return this; }
    /** setting y component */
    public IVec2 y(double vy){ y=vy; return this; }
    
    /** setting x component */
    public IVec2 x(IDoubleI vx){ x=vx.x(); return this; }
    /** setting y component */
    public IVec2 y(IDoubleI vy){ y=vy.x(); return this; }
    
    /** getting x component */
    public double x(ISwitchE e){ return x(); }
    /** getting y component */
    public double y(ISwitchE e){ return y(); }
    
    /** getting x component */
    public IDouble x(ISwitchR r){ return new IDouble(x); }
    /** getting y component */
    public IDouble y(ISwitchR r){ return new IDouble(y); }
    
    
    //public IVec2 get(){ return this; }
    public IVec2 get(){ return new IVec2(this); }
    public IVec2 dup(){ return new IVec2(this); }
    
    public IVec to3d(){ return new IVec(this); }
    public IVec to3d(double z){ return new IVec(x,y,z); }
    public IVec to3d(IDoubleI z){ return new IVec(x,y,z.x()); }
    public IVec4 to4d(){ return new IVec4(x,y,0.); }
    public IVec4 to4d(double z, double w){ return new IVec4(x,y,z,w); }
    public IVec4 to4d(IDoubleI z, IDoubleI w){ return new IVec4(x,y,z.x(),w.x()); }
    
    public IDouble getX(){ return new IDouble(x); }
    public IDouble getY(){ return new IDouble(y); }
    
    public IVec2 set(double x, double y){ this.x=x; this.y=y; return this; }
    public IVec2 set(IVec2 v){ x=v.x; y=v.y; return this; }
    public IVec2 set(IVec2I v){ return set(v.get()); }
    public IVec2 set(IDoubleI x, IDoubleI y){ this.x=x.x(); this.y=y.x(); return this; }
    
    public IVec2 set(IVecI v){ x = v.x(); y = v.y(); return this; }
    public IVec2 set(IVecI v, IVecI projectionDir){
	IVec axis2=null;
	if(projectionDir.get().isParallel(IG.xaxis)){ axis2 = projectionDir.get().cross(IG.yaxis).unit(); }
	else{ axis2 = projectionDir.get().cross(IG.xaxis).unit(); }
	IVec axis1 = axis2.cross(projectionDir).unit();
	return set(v,axis1,axis2);
    }
    public IVec2 set(IVecI v, IVecI xaxis, IVecI yaxis){
	x = v.dot(xaxis); y = v.dot(yaxis); return this;
    }
    public IVec2 set(IVecI v, IVecI xaxis, IVecI yaxis, IVecI origin){
	IVec d = v.get().dif(origin);
	x = d.dot(xaxis); y = d.dot(yaxis); return this;
    }
    //public IVec2 set(IVecI v, IMatrix mat){}
    
    
    public IVec2 add(double x, double y){ this.x+=x; this.y+=y; return this; }
    public IVec2 add(IDoubleI x, IDoubleI y){ this.x+=x.x(); this.y+=y.x(); return this; }
    public IVec2 add(IVec2 v){ x+=v.x; y+=v.y; return this; }
    public IVec2 add(IVec2I v){ return add(v.get()); }
    
    public IVec2 sub(double x, double y){ this.x-=x; this.y-=y; return this; }
    public IVec2 sub(IDoubleI x, IDoubleI y){ this.x-=x.x(); this.y-=y.x(); return this; }
    public IVec2 sub(IVec2 v){ x-=v.x; y-=v.y; return this; }
    public IVec2 sub(IVec2I v){ return sub(v.get()); }
    
    public IVec2 mul(double x){ this.x*=x; this.y*=x; return this; }
    public IVec2 mul(IDouble v){ return mul(v.x); }
    public IVec2 mul(IDoubleI v){ return mul(v.x()); }
    
    public IVec2 div(double x){ this.x/=x; this.y/=x; return this; }
    public IVec2 div(IDouble v){ return div(v.x); }
    public IVec2 div(IDoubleI v){ return div(v.x()); }
    
    public IVec2 neg(){ x=-x; y=-y; return this; }
    /** alias of neg */
    public IVec2 rev(){ return neg(); }
    /** alias of neg */
    public IVec2 flip(){ return neg(); }
    
    /** setting all zero */
    public IVec2 zero(){ x=0; y=0; return this; }
        
    
    /** scale add */
    public IVec2 add(IVec2 v, double f){ x+=v.x*f; y+=v.y*f; return this; }
    /** scale add */
    public IVec2 add(IVec2I v, double f){ return add(v.get(),f); }
    /** scale add */
    public IVec2 add(IVec2I v, IDoubleI f){ add(v.get(),f.x()); return this; }
    
    /** scale add; alias */
    public IVec2 add(double f, IVec2 v){ return add(v,f); }
    /** scale add; alias */
    public IVec2 add(double f, IVec2I v){ return add(v,f); }
    /** scale add; alias */
    public IVec2 add(IDoubleI f, IVec2I v){ return add(v,f); }
    
    
    /** dot product */
    public double dot(IVec2 v){ return x*v.x + y*v.y; }
    /** dot product */
    public double dot(double vx, double vy){ return x*vx + y*vy; }
    /** dot product */
    public double dot(IVec2I v){ return dot(v.get()); }
    /** dot product */
    public double dot(ISwitchE e, IVec2I v){ return dot(v); }
    /** dot product */
    public IDouble dot(ISwitchR r, IVec2I v){ return new IDouble(dot(v)); }
    
    /** cross product in z direction as IVec */
    public IVec cross(IVec2 v){ return new IVec(0, 0, x*v.y - y*v.x); }
    /** cross product in z direction as IVec */
    public IVec cross(double vx, double vy){ return new IVec(0, 0, x*vy - y*vx); }
    /** cross product in z direction as IVec */
    public IVec cross(IVec2I v){ return cross(v.get()); }
    
    /** @return length (norm) of the vector in double */
    public double len(){ return Math.sqrt(x*x+y*y); }
    /** @return length (norm) of the vector in double */
    public double len(ISwitchE e){ return len(); }
    /** @return length (norm) of the vector in double */
    public IDouble len(ISwitchR r){ return new IDouble(len()); }
    
    /** return squared length of the vector in double */
    public double len2(){ return x*x+y*y; }
    /** return squared length of the vector in double */
    public double len2(ISwitchE e){ return len2(); }
    /** return squared length of the vector in double */
    public IDouble len2(ISwitchR r){ return new IDouble(len2()); }

    /** setting length */
    public IVec2 len(IDoubleI l){ return len(l.x()); }
    /** setting length */
    public IVec2 len(double l){ l/=len(); x*=l; y*=l; return this; }
    
    /** unitize (normalize) vector */
    public IVec2 unit(){ double l=len(); x/=l; y/=l; return this; }
    /** rotate vector in Pi/2 */
    public IVec2 ortho(){ double tmp = x; x=-y; y=tmp; return this; }
    
    /** distance of two vectors */    
    public double dist(IVec2 v){ return Math.sqrt((x-v.x)*(x-v.x)+(y-v.y)*(y-v.y)); }
    /** distance of two vectors */    
    public double dist(double vx, double vy){ return Math.sqrt((x-vx)*(x-vx)+(y-vy)*(y-vy)); }
    /** distance of two vectors */
    public double dist(IVec2I v){ return dist(v.get()); }
    /** distance of two vectors */
    public double dist(ISwitchE e, IVec2I v){ return dist(v); }
    /** distance of two vectors */
    public IDouble dist(ISwitchR r, IVec2I v){ return new IDouble(dist(v)); }

    /** squared distance of two vectors */
    public double dist2(IVec2 v){ return (x-v.x)*(x-v.x)+(y-v.y)*(y-v.y); }
    /** squared distance of two vectors */
    public double dist2(double vx, double vy){ return (x-vx)*(x-vx)+(y-vy)*(y-vy); }
    /** squared distance of two vectors */
    public double dist2(IVec2I v){ return dist2(v.get()); }
    /** squared distance of two vectors */
    public double dist2(ISwitchE e, IVec2I v){ return dist2(v); }
    /** squared distance of two vectors */
    public IDouble dist2(ISwitchR r, IVec2I v){ return new IDouble(dist2(v)); }
    
    /** whether location is same or not */
    public boolean eq(IVec2 v){ return eq(v,IConfig.tolerance); }
    /** whether location is same or not */
    public boolean eq(double vx, double vy){ return eq(vx,vy,IConfig.tolerance); }
    /** whether location is same or not */
    public boolean eq(IVec2I v){ return eq(v.get(),IConfig.tolerance); }
    /** whether location is same or not */
    public boolean eq(ISwitchE e, IVec2I v){ return eq(v); }
    /** whether location is same or not */
    public IBool eq(ISwitchR r, IVec2I v){ return new IBool(eq(v)); }
    
    /** whether location is same or not within tolerance */
    public boolean eq(IVec2 v, double tolerance){ return dist2(v) <= tolerance*tolerance; }
    /** whether location is same or not within tolerance */
    public boolean eq(double vx, double vy, double tolerance){ return dist2(vx,vy) <= tolerance*tolerance; }
    /** whether location is same or not within tolerance */
    public boolean eq(IVec2I v, double tolerance){ return eq(v.get(),tolerance); }
    /** whether location is same or not within tolerance */
    public boolean eq(ISwitchE e, IVec2I v, double tolerance){ return eq(v,tolerance); }
    /** whether location is same or not within tolerance */
    public IBool eq(ISwitchR r, IVec2I v, IDoubleI tolerance){ return new IBool(eq(v,tolerance.x())); }    

    /** check if same in X */
    public boolean eqX(IVec2 v){ return eqX(v,IConfig.tolerance); }
    /** check if same in Y */
    public boolean eqY(IVec2 v){ return eqY(v,IConfig.tolerance); }
    /** check if same in X */
    public boolean eqX(double vx){ return eqX(vx,IConfig.tolerance); }
    /** check if same in Y */
    public boolean eqY(double vy){ return eqY(vy,IConfig.tolerance); }
    /** check if same in X */
    public boolean eqX(IVec2I v){ return eqX(v.get(),IConfig.tolerance); }
    /** check if same in Y */
    public boolean eqY(IVec2I v){ return eqY(v.get(),IConfig.tolerance); }
    /** check if same in X */
    public boolean eqX(ISwitchE e, IVec2I v){ return eqX(v); }
    /** check if same in Y */
    public boolean eqY(ISwitchE e, IVec2I v){ return eqY(v); }
    /** check if same in X */
    public IBool eqX(ISwitchR r, IVec2I v){ return new IBool(eqX(v)); }
    /** check if same in Y */
    public IBool eqY(ISwitchR r, IVec2I v){ return new IBool(eqY(v)); }
    
    
    /** check if same in X within tolerance */
    public boolean eqX(IVec2 v, double tolerance){ return Math.abs(v.x-x)<=tolerance; }
    /** check if same in Y within tolerance */
    public boolean eqY(IVec2 v, double tolerance){ return Math.abs(v.y-y)<=tolerance; }
    /** check if same in X within tolerance */
    public boolean eqX(double vx, double tolerance){ return Math.abs(vx-x)<=tolerance; }
    /** check if same in Y within tolerance */
    public boolean eqY(double vy, double tolerance){ return Math.abs(vy-y)<=tolerance; }
    /** check if same in X within tolerance */
    public boolean eqX(IVec2I v, double tolerance){ return Math.abs(v.x()-x)<=tolerance; }
    /** check if same in Y within tolerance */
    public boolean eqY(IVec2I v, double tolerance){ return Math.abs(v.y()-y)<=tolerance; }
    /** check if same in X within tolerance */
    public boolean eqX(ISwitchE e, IVec2I v, double tolerance){ return eqX(v,tolerance); }
    /** check if same in Y within tolerance */
    public boolean eqY(ISwitchE e, IVec2I v, double tolerance){ return eqY(v,tolerance); }
    /** check if same in X within tolerance */
    public IBool eqX(ISwitchR r, IVec2I v, IDoubleI tolerance){ return new IBool(eqX(v,tolerance.x())); }
    /** check if same in Y within tolerance */
    public IBool eqY(ISwitchR r, IVec2I v, IDoubleI tolerance){ return new IBool(eqY(v,tolerance.x())); }
    
    
    /** @return angle of two vector. From -Pi to Pi. Sign follows right-handed screw rule */
    public double angle(IVec2 v){
	double len1 = len(); if(len1==0) return 0;
	double len2 = v.len(); if(len2==0) return 0;
	double cross = x*v.y-y*v.x; //if(cross==0) return 0;
	double cos = (x*v.x+y*v.y)/(len1*len2);
	if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
	double angle = Math.acos(cos);
	if(cross<0) return -angle; // negative
	return angle;
    }
    /** @return angle of two vector. From -Pi to Pi. Sign follows right-handed screw rule */
    public double angle(double vx, double vy){
	double len1 = len(); if(len1==0) return 0;
	double len2 = Math.sqrt(vx*vx+vy*vy); if(len2==0) return 0;
	double cross = x*vy-y*vx; //if(cross==0) return 0;
	double cos = (x*vx+y*vy)/(len1*len2);
	if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
	double angle = Math.acos(cos);
	if(cross<0) return -angle; // negative
	return angle;
    }
    /** @return angle of two vector. From -Pi to Pi. Sign follows right-handed screw rule */
    public double angle(IVec2I v){ return angle(v.get()); }
    /** @return angle of two vector. From -Pi to Pi. Sign follows right-handed screw rule */
    public double angle(ISwitchE e, IVec2I v){ return angle(v); }
    /** @return angle of two vector. From -Pi to Pi. Sign follows right-handed screw rule */
    public IDouble angle(ISwitchR r, IVec2I v){ return new IDouble(angle(v)); }
    
    /** rotate */
    public IVec2 rot(double angle){
	double origx = x;
	x = x*Math.cos(angle) - y*Math.sin(angle);
	y = origx*Math.sin(angle) + y*Math.cos(angle);
	return this;
    }
    /** rotate */
    public IVec2 rot(IDoubleI angle){ return rot(angle.x()); }

    /** rotate around center */
    public IVec2 rot(IVec2 center, double angle){
	if(center==this) return this;
	return sub(center).rot(angle).add(center);
    }
    /** rotate around center */
    public IVec2 rot(double centerX, double centerY, double angle){
	return sub(centerX,centerY).rot(angle).add(centerX,centerY);
    }
    /** rotate around center */
    public IVec2 rot(IVec2I center, double angle){ return rot(center.get(),angle); }
    /** rotate around center */
    public IVec2 rot(IVec2I center, IDoubleI angle){ return rot(center.get(),angle.x()); }
    
    // to be tested !!! (direction of rotation)
    /** rotate towards destination direction */
    public IVec2 rot(IVec2 destDir){ return rot(angle(destDir)); }
    /** rotate towards destination direction */
    public IVec2 rot(IVec2I destDir){ return rot(destDir.get()); }
    /** rotate around center towards destination point */
    public IVec2 rot(IVec2 center, IVec2 destPt){
	if(center==this) return this;
	return sub(center).rot(destPt.diff(center)).add(center);
    }
    /** rotate around center towards destination point */
    public IVec2 rot(IVec2I center, IVec2I destPt){ return rot(center.get(),destPt.get()); }
    
    /** alias of mul */
    public IVec2 scale(double f){ return mul(f); }
    /** alias of mul */
    public IVec2 scale(IDoubleI f){ return mul(f); }
    /** scale around center */
    public IVec2 scale(IVec2 center, double f){
	if(center==this) return this;
	return sub(center).mul(f).add(center);
    }
    /** scale around center */
    public IVec2 scale(double centerX, double centerY, double f){
	return sub(centerX,centerY).mul(f).add(centerX,centerY);
    }
    /** scale around center */
    public IVec2 scale(IVec2I center, double f){ return scale(center.get(),f); }
    /** scale around center */
    public IVec2 scale(IVec2I center, IDoubleI f){ return scale(center.get(),f.x()); }
    
    
    /** scale only in 1 direction */
    public IVec2 scale1d(IVec2 axis, double f){
	IVec2 n = axis.dup().unit();
        n.mul(this.dot(n));
        IVec2 t = this.dif(n);
        return this.set(n.mul(f).add(t));
    }
    /** scale only in 1 direction */
    public IVec2 scale1d(IVec2I axis, double f){ return scale1d(axis.get(),f); }
    /** scale only in 1 direction */
    public IVec2 scale1d(IVec2I axis, IDoubleI f){ return scale1d(axis.get(),f.x()); }
    /** scale only in 1 direction */
    public IVec2 scale1d(double axisX, double axisY, double f){
        double len = Math.sqrt(axisX*axisX+axisY*axisY);
        axisX/=len; axisY/=len; 
        double dt = dot(axisX,axisY);
        axisX*=dt; axisY*=dt;
        x = axisX*f + x-axisX;
        y = axisY*f + y-axisY;
        return this;
    }
    
    /** scale only in 1 direction from a center */
    public IVec2 scale1d(IVec2 center, IVec2 axis, double f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    /** scale only in 1 direction from a center */
    public IVec2 scale1d(IVec2I center, IVec2I axis, double f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    /** scale only in 1 direction from a center */
    public IVec2 scale1d(IVec2I center, IVec2I axis, IDoubleI f){ return scale1d(center,axis,f.x()); }
    /** scale only in 1 direction from a center */
    public IVec2 scale1d(double centerX, double centerY,  double axisX, double axisY, double f){
	return sub(centerX,centerY).scale1d(axisX,axisY,f).add(centerX,centerY);
    }
    
    
    
    /** reflect (mirror) 2 dimensionally to the other side of the line
	@param lineDir direction of reflection line
    */
    public IVec2 ref(IVec2 lineDir){
	return rev().add(lineDir.dup().mul(dot(lineDir)/lineDir.len2()*-2));
    }
    /** reflect (mirror) 2 dimensionally to the other side of the line */
    // please test this method
    public IVec2 ref(double lineX, double lineY){
	double d = dot(lineX,lineY)/(lineX*lineX+lineY*lineY)*-2;
	x = -x + lineX*d;
	y = -y + lineY*d;
	return this;
    }
    /** reflect (mirror) 2 dimensionally to the other side of the line
	@param lineDir direction of reflection line
    */
    public IVec2 ref(IVec2I lineDir){ return ref(lineDir.get()); }
    /** reflect (mirror) 2 dimensionally to the other side of the line
	@param linePt start point of reflection line
	@param lineDir direction of reflection line
    */
    public IVec2 ref(IVec2 linePt, IVec2 lineDir){
	if(linePt==this) return this;
	return sub(linePt).ref(lineDir).add(linePt);
    }
    /** reflect (mirror) 2 dimensionally to the other side of the line at line point */
    public IVec2 ref(double linePtX, double linePtY, double lineDirX, double lineDirY){
	return sub(linePtX,linePtY).ref(lineDirX,lineDirY).add(linePtX,linePtY);
    }
    /** reflect (mirror) 2 dimensionally to the other side of the line
	@param linePt start point of reflection line
	@param lineDir direction of reflection line
    */
    public IVec2 ref(IVec2I linePt, IVec2I lineDir){ return ref(linePt.get(),lineDir.get()); }
    
    /** reflect (mirror) 2 dimensionally to the other side of the line
	@param lineDir direction of reflection line
    */
    public IVec2 mirror(IVec2 lineDir){ return ref(lineDir); }
    /** reflect (mirror) 2 dimensionally to the other side of the line */
    public IVec2 mirror(double lineX, double lineY){ return ref(lineX,lineY); }
    /** reflect (mirror) 2 dimensionally to the other side of the line
	@param lineDir direction of reflection line
    */
    public IVec2 mirror(IVec2I lineDir){ return ref(lineDir.get()); }
    /** reflect (mirror) 2 dimensionally to the other side of the line
	@param linePt start point of reflection line
	@param lineDir direction of reflection line
    */
    public IVec2 mirror(IVec2 linePt, IVec2 lineDir){ return ref(linePt,lineDir); }
    /** reflect (mirror) 2 dimensionally to the other side of the line at line point */
    public IVec2 mirror(double linePtX, double linePtY, double lineDirX, double lineDirY){
	return ref(linePtX,linePtY,lineDirX,lineDirY);
    }
    /** reflect (mirror) 2 dimensionally to the other side of the line
	@param linePt start point of reflection line
	@param lineDir direction of reflection line
    */
    public IVec2 mirror(IVec2I linePt, IVec2I lineDir){ return ref(linePt,lineDir); }
    
    
    
    /** shear operation on XY*/
    public IVec2 shear(double sxy, double syx){
        double tx,ty;
        tx =     x + sxy*y;
	ty = syx*x +     y;
        x = tx; y = ty;
        return this;
    }
    /** shear operation on XY*/
    public IVec2 shear(IDoubleI sxy, IDoubleI syx){
	return shear((sxy==null)?0:sxy.x(), (syx==null)?0:syx.x());
    }
    /** shear operation on XY*/
    public IVec2 shear(IVec2I center, double sxy, double syx){
	if(center==this) return this;
	return sub(center).shear(sxy,syx).add(center);
    }
    /** shear operation on XY*/
    public IVec2 shear(IVec2I center, IDoubleI sxy, IDoubleI syx){
	return shear(center,sxy.x(),syx.x());
    }
    
    
    /** alias of add() */
    public IVec2 translate(double x, double y){ return add(x,y); }
    /** alias of add() */
    public IVec2 translate(IDoubleI x, IDoubleI y){ return add(x,y); }
    /** alias of add() */
    public IVec2 translate(IVec2 v){ return add(v); }
    /** alias of add() */
    public IVec2 translate(IVec2I v){ return add(v); }
    
    
    /** transform with 2x2 transform matrix */
    public IVec2 transform(IMatrix2I mat){ return set(mat.mul(this)); }
    
    /** transform with 3x3 transform matrix */
    public IVec2 transform(IMatrix3I mat){ return set(mat.mul(this)); }
    
    /** transform with transform vectors */
    public IVec2 transform(IVec2 xvec, IVec2 yvec){
	double tx,ty;
	tx = xvec.x*x + yvec.x*y;
	ty = xvec.y*x + yvec.y*y;
	x = tx; y = ty;
	return this;
    }
    /** transform with transform vectors */
    public IVec2 transform(IVec2I xvec, IVec2I yvec){ return transform(xvec.get(),yvec.get()); }
    /** transform with transform vectors */
    public IVec2 transform(IVec2 xvec, IVec2 yvec, IVec2 translate){
	return transform(xvec,yvec).add(translate);
    }
    /** transform with transform vectors */
    public IVec2 transform(IVec2I xvec, IVec2I yvec, IVec2I translate){
	return transform(xvec.get(),yvec.get(),translate.get());
    }
    
    
    /** mv() is alias of add() */
    public IVec2 mv(double x, double y){ return add(x,y); }
    /** mv() is alias of add() */
    public IVec2 mv(IDoubleI x, IDoubleI y){ return add(x,y); }
    /** mv() is alias of add() */
    public IVec2 mv(IVec2 v){ return add(v); }
    /** mv() is alias of add() */
    public IVec2 mv(IVec2I v){ return add(v); }
    
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */
    public IVec2 cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IVec2 cp(double x, double y){ return dup().add(x,y); }
    /** cp() is alias of dup().add() */
    public IVec2 cp(IDoubleI x, IDoubleI y){ return dup().add(x,y); }
    /** cp() is alias of dup().add() */
    public IVec2 cp(IVec2 v){ return dup().add(v); }
    /** cp() is alias of dup().add() */
    public IVec2 cp(IVec2I v){ return dup().add(v); }
    
    
    // methods creating new instance
    /** create a new instance of difference */
    public IVec2 dif(IVec2 v){
	//return dup().sub(v);
	return new IVec2(x-v.x, y-v.y);
    }
    /** create a new instance of difference */
    public IVec2 dif(double vx, double vy){
	//return dup().sub(vx,vy);
	return new IVec2(x-vx, y-vy);
    }
    /** create a new instance of difference */
    public IVec2 dif(IVec2I v){ return dup().sub(v); }
    /** alias of dif */
    public IVec2 diff(IVec2 v){ return dif(v); }
    /** alias of dif */
    public IVec2 diff(double vx, double vy){ return dif(vx,vy); }
    /** alias of dif */
    public IVec2 diff(IVec2I v){ return dif(v); }
    /** create a new instance of midpoint */
    public IVec2 mid(IVec2 v){
	//return dup().add(v).div(2);
	return new IVec2( (x+v.x)/2, (y+v.y)/2 );
    }
    /** create a new instance of midpoint */
    public IVec2 mid(double vx, double vy){
	//return dup().add(v).div(2);
	return new IVec2( (x+vx)/2, (y+vy)/2 );
    }
    /** create a new instance of midpoint */
    public IVec2 mid(IVec2I v){ return dup().add(v).div(2); }
    /** create a new instance of summation */
    public IVec2 sum(IVec2 v){ return dup().add(v); }
    /** create a new instance of summation */
    public IVec2 sum(double vx, double vy){ return dup().add(vx,vy); }
    /** create a new instance of summation */
    public IVec2 sum(IVec2I v){ return dup().add(v); }
    /** create a new instance of summation */
    public IVec2 sum(IVec2I... v){
        IVec2 ret = this.dup();
        for(IVec2I vi: v) ret.add(vi);
        return ret;
    }
    /** create a new instance of bisector */
    public IVec2 bisect(IVec2 v){ return dup().unit().add(v.dup().unit()); }
    /** create a new instance of bisector */
    public IVec2 bisect(double vx, double vy){
	double len = Math.sqrt(vx*vx+vy*vy);
	return dup().unit().add(vx/len, vy/len);
    }
    /** create a new instance of bisector */
    public IVec2 bisect(IVec2I v){ return bisect(v.get()); }
    
    /** create a new instance of weighted sum */
    public IVec2 sum(IVec2 v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    /** create a new instance of weighted sum */
    public IVec2 sum(IVec2I v2, double w1, double w2){ return sum(v2.get(),w1,w2); }
    /** create a new instance of weighted sum */
    public IVec2 sum(IVec2I v2, IDoubleI w1, IDoubleI w2){ return sum(v2.get(),w1.x(),w2.x()); }
    /** create a new instance of weighted sum */
    public IVec2 sum(IVec2 v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    /** create a new instance of weighted sum */
    public IVec2 sum(IVec2I v2, double w2){ return sum(v2.get(),w2); }
    /** create a new instance of weighted sum */
    public IVec2 sum(IVec2I v2, IDoubleI w2){ return sum(v2.get(),w2.get()); }

    /** alias of cross */
    public IVec nml(IVec2 v){ return cross(v); }
    /** alias of cross */
    public IVec nml(IVec2I v){ return cross(v); }
    /** alias of cross */
    public IVecI nml(double vx, double vy){ return cross(vx,vy); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVecI nml(IVec2 pt1, IVec2 pt2){ return this.dif(pt1).cross(this.dif(pt2)); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVecI nml(double vx1, double vy1, double vx2, double vy2){
	return this.dif(vx1,vy1).cross(this.dif(vx2,vy2)); 
    }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVecI nml(IVec2I pt1, IVec2I pt2){ return this.dif(pt1).cross(this.dif(pt2)); }
    
    
    /** check if two vectors are parallel */
    public boolean isParallel(IVec2I v){ return isParallel(v, IConfig.angleTolerance); }
    /** check if two vectors are parallel */
    public boolean isParallel(IVec2I v, double angleReso){
	//return dup().normalize().diff(v.get().dup().normalize()).len() < IConfig.tolerance;
	//return dot(v.get())/(len()*v.get().len()) > Math.cos(IConfig.angleTolerance);
	// opposite directions is regarded as parallel too
	return Math.abs(dot(v.get())/(len()*v.get().len())) > Math.cos(angleReso);
    }
    
    /** check if three locations are on straight line */
    public boolean isStraight(IVec2I v1, IVec2I v2){
	//return this.diff(v1).isParallel(this.diff(v2));
	//return v1.get().diff(this).isParallel(v2.get().diff(v1));
	return isStraight(v1,v2,IConfig.angleTolerance);
    }
    /** check if three locations are on straight line */
    public boolean isStraight(IVec2I v1, IVec2I v2, double angleReso){
	return v1.get().diff(this).isParallel(v2.get().diff(v1),angleReso);
    }
    
    //
    /** intersection of two infinite lines */
    public static IVec2 intersect(IVec2I pt1, IVec2I dir1,
				  IVec2I pt2, IVec2I dir2){
	double det = dir2.x()*dir1.y() - dir1.x()*dir2.y();
	if(det==0) return null; // parallel
	IVec2 pt = new IVec2(pt1.x()*dir1.y()*dir2.x() -
			       pt2.x()*dir1.x()*dir2.y() -
			       (pt1.y()-pt2.y())*dir1.x()*dir2.x(),
			       pt2.y()*dir1.y()*dir2.x() -
			       pt1.y()*dir1.x()*dir2.y() +
			       (pt1.x()-pt2.x())*dir1.y()*dir2.y());
	pt.div(det);
	return pt;
    }
    
    
    public static IVec2 intersectYLine(IVec2 linePt, IVec2 lineDir, double xOfYLine){
        if(lineDir.x==0) return null; // parallel
        return new IVec2( xOfYLine, linePt.y - (linePt.x-xOfYLine)*lineDir.y/lineDir.x);
    }
    
    public static IVec2 intersectXLine(IVec2 linePt, IVec2 lineDir, double yOfXLine){
	if(lineDir.y==0) return null; // parallel
	return new IVec2(linePt.x  - (linePt.y-yOfXLine)*lineDir.x/lineDir.y, yOfXLine );
    }
    
    
    public static IVec2 intersectSegment(IVec2I line1pt1, IVec2I line1pt2,
					  IVec2I line2pt1, IVec2I line2pt2){
	if(Math.max(line1pt1.x(),line1pt2.x()) < Math.min(line2pt1.x(),line2pt2.x()) ||
	   Math.max(line2pt1.x(),line2pt2.x()) < Math.min(line1pt1.x(),line1pt2.x()) ||
	   Math.max(line1pt1.y(),line1pt2.y()) < Math.min(line2pt1.y(),line2pt2.y()) ||
	   Math.max(line2pt1.y(),line2pt2.y()) < Math.min(line1pt1.y(),line1pt2.y())){
	    return null;
	}
	
	IVec2 isct = intersect(line1pt1, line1pt2.get().dif(line1pt1),
				line2pt1, line2pt2.get().dif(line2pt1));

	if(isct==null) return null;

	if(!isct.isBetween(line1pt1,line1pt2) ||
	   !isct.isBetween(line2pt1,line2pt2) ) return null;
	
	return isct;
    }

    public static IVec2 intersectPolyline(IVec2I p1, IVec2I p2, IVec2I[] pts, boolean closed){
	for(int i=0; i<pts.length-1 || closed&&(i<pts.length); i++){
	    IVec2 isct = intersectSegment(p1,p2,pts[i],pts[(i+1)%pts.length]);
	    if(isct!=null) return isct;
	}
	return null;
    }
    
    public static IVec2 intersectPolyline(IVec2I p1, IVec2I p2, IVec2I[] pts){
	return intersectPolyline(p1,p2,pts,false);
    }
    
    public static IVec2 intersectPolygon(IVec2I p1, IVec2I p2, IVec2I[] pts){
	return intersectPolyline(p1,p2,pts,true);
    }
    
    
    public static IVec2 intersectSegmentAndXLine(IVec2I linePt1, IVec2I linePt2,
						  double y){
	if(Math.max(linePt1.y(),linePt2.y()) < y ||
	   Math.min(linePt1.y(),linePt2.y()) > y ) return null;
	IVec2 diff = linePt2.get().dif(linePt1);
	if(diff.y==0) return linePt1.get().dup();
	diff.mul((y-linePt1.y())/diff.y);
	diff.add(linePt1);
	return diff;
    }
    
    public static IVec2 intersectSegmentAndYLine(IVec2I linePt1, IVec2I linePt2,
						  double x){
	if(Math.max(linePt1.x(),linePt2.x()) < x ||
	   Math.min(linePt1.x(),linePt2.x()) > x ) return null;
	IVec2 diff = linePt2.get().dif(linePt1);
	if(diff.x==0) return linePt1.get().dup();
	diff.mul((x-linePt1.x())/diff.x);
	diff.add(linePt1);
	return diff;
    }
    
    
    /**
       remove points which are on straight line of adjacents
    */
    public static IVec2I[] removeStraightPoints(IVec2I[] pts, boolean closed){
	// remove redundant point on straight
	int num = pts.length;
	if(num<=2) return pts;
	ArrayList<IVec2I> pts2 = new ArrayList<IVec2I>();
	pts2.add(pts[0]);
	for(int i=1; !closed&&i<num-1 || closed&&i<num; i++){
	    if(!pts2.get(pts2.size()-1).get().isStraight(pts[i], pts[(i+1)%num])) pts2.add(pts[i]);
	}
	
	if(pts2.size()==1) return pts2.toArray(new IVec2I[pts2.size()]); // only one point
	
	if(closed && pts2.get(pts2.size()-1).get().isStraight(pts2.get(0), pts2.get(1)))
	    pts2.remove(0);
	if(pts2.size()==num) return pts;
	IVec2I[] retval = new IVec2I[pts2.size()];
	for(int i=0; i<pts2.size(); i++) retval[i] = pts2.get(i);
	return retval;
    }
    
    
    public static IVec2I[] removeDuplicatedPoints(IVec2I[] pts, boolean closed){
	int num = pts.length;
	ArrayList<IVec2I> pts2 = new ArrayList<IVec2I>();
	pts2.add(pts[0]);
	for(int i=1; i<num; i++){
	    if(!pts2.get(pts2.size()-1).get().eq(pts[i])){
		if(closed && i==num-1){
		    if(!pts2.get(0).get().eq(pts[i])) pts2.add(pts[i]);
		}
		else pts2.add(pts[i]);
	    }
	}
	return pts2.toArray(new IVec2I[pts2.size()]);
    }
    
    
    /**
       determine if the point is insde the polygon defined by the argument
       
    */
    public boolean isInside(IVec2I[] pts){
    	
	double angle=0;
	int n = pts.length;
	for(int i=0; i<n; i++){
	    IVec2 v1 = pts[i].get().dif(this);
	    IVec2 v2 = pts[(i+1)%n].get().dif(this);
	    double a = v1.angle(v2);
	    if( Math.abs(a-Math.PI) < IConfig.angleTolerance ||
		Math.abs(a+Math.PI) < IConfig.angleTolerance ||
		v1.len()< IConfig.tolerance ||
		v2.len()< IConfig.tolerance ){
		// point is on the edge
		return true;
	    }
	    angle += a;
	}
	
	int idx = (int)(Math.abs(angle/(2*Math.PI)) + 0.5);
	
	if( idx%2==0 ){ return false; } // outside
	
	return true; // inside
	
	/*
	final double minAngle = 0.1;
	if(Math.abs(angle)<minAngle) return true;
	return false;
	*/
    }
    
    
    /**
       determine if the point is insde the polygon defined by the argument
       true if the point is on the edge of polygon
    */
    public boolean isInside(IVec2[] pts){
    	
	double angle=0;
	int n = pts.length;
	for(int i=0; i<n; i++){
	    IVec2 v1 = pts[i].dif(this);
	    IVec2 v2 = pts[(i+1)%n].dif(this);
	    
	    double a = v1.angle(v2);
	    if( Math.abs(a-Math.PI) < IConfig.angleTolerance ||
		Math.abs(a+Math.PI) < IConfig.angleTolerance ||
		v1.len()< IConfig.tolerance ||
		v2.len()< IConfig.tolerance ){
		// point is on the edge
		return true;
	    }
	    angle += a;
	}
	
	int idx = (int)(Math.abs(angle/(2*Math.PI)) + 0.5);
	
	if( idx%2==0 ){
	    return false; // outside
	}
	
	//IOut.p("true");
	return true; // inside
    }
    
    
    /** checking x, y is valid number (not Infinite, nor NaN). */
    public boolean isValid(){
	if(!IDouble.isValid(x)){ IOut.err("invalid x ("+x+")"); return false; }
	if(!IDouble.isValid(y)){ IOut.err("invalid y ("+y+")"); return false; }
	return true;
    }
    
    public String toString(){
	return "("+String.valueOf(x)+","+String.valueOf(y)+")";
    }
    
    
    public double distToLine(IVec2 pt1, IVec2 pt2){
	double xdiff = pt2.x-pt1.x;
	double ydiff = pt2.y-pt1.y;
	return Math.abs(ydiff*this.x +- xdiff*this.y - pt1.x*pt2.y + pt2.x*pt1.y)/
	    Math.sqrt(ydiff*ydiff + xdiff*xdiff);
    }
    
    public boolean isOnLine(IVec2 pt1, IVec2 pt2){
	return distToLine(pt1,pt2)<IConfig.tolerance;
    }
    
    public boolean isBetween(IVec2I pt1, IVec2I pt2){
	IVec2 diff1 = dif(pt1);
	IVec2 diff2 = pt2.get().dif(pt1);
	double ip = diff1.dot(diff2);
	if(ip<0) return false;
	if(ip>diff2.len2()) return false;
	return true;
    }
    
    
    /** project the vector to the plane defined by two input vector and decompose vector to two vector and another perpendicular vector and returns coefficient of them.
	relationship of them is like below.
	this = return[0] * v1 + return[1] * v2 ;
	@return array of three double number, first is coefficient of uvec, second is of vvec
    */
    public double[] projectTo2Vec(IVec2I v1, IVec2I v2){
	double coef[] = new double[2];
        // project to a plane defined by v1 and v2
        
	IVec2 v1n = v1.get(); if(v1n==v1) v1n = v1n.dup();
	IVec2 v2n = v2.get(); if(v2n==v2) v2n = v2n.dup();
	
	v1n.unit();
	v2n.unit();
	
        double ip12 = v1n.dot(v2n);
        double iip122 = 1-ip12*ip12;
        if(iip122==0) return null; 
	
	double ip1 = this.dot(v1n);
        double ip2 = this.dot(v2n);
        coef[0] = ((ip1-ip2*ip12)/iip122)/v1.len();
	coef[1] = ((ip2-ip1*ip12)/iip122)/v2.len();
	return coef;
    }

    
    /**
       create a new vector from this point to the line in parpendicular direction.
    */
    public IVec2 perpendicularVecToLine(IVec2I lineDir, IVec2I linePt){
	double len2 = lineDir.len2();
	if(len2==0){ IOut.err("line direction is zero"); }
	IVec2 ldir = lineDir.get().dup();
	IVec2 dif = dif(linePt);
	return ldir.mul(ldir.dot(dif)/len2).sub(dif);
    }
    
    /**
       create a new vector from line to this point perpendicular to the line dir
    */
    public IVec2 perpendicularVecToLine(IVec2I lineDir){
	IVec2 ldir = lineDir.get().dup();
	return ldir.mul(ldir.dot(this)/ldir.len2()).sub(this);
    }

    /** distance to an infinite line */
    public double distToLine(IVec2I lineDir, IVec2I linePt){
	return perpendicularVecToLine(lineDir,linePt).len();
    }
    
    /** distance to a line dir */
    public double distToLine(IVec2I lineDir){
	return perpendicularVecToLine(lineDir).len();
    }
    
    /** distance to a line segment */
    public double distToSegment(IVec2I linePt1, IVec2I linePt2){
	if(linePt1==this || linePt2==this) return 0;
	IVec2 lineDir = linePt2.get().dif(linePt1);
	IVec2 dif = this.dif(linePt1);
	double dot = dif.dot(lineDir)/lineDir.len2();
	if(dot<0.0) dot=0.0; else if(dot>1.0) dot=1.0;
	return lineDir.mul(dot).dist(dif);
    }
    
    /** ratio of projected point between two points (line segment). 0.0 is at linePt1, 1.0 is at linePt2. */
    public double ratioOnSegment(IVec2I linePt1, IVec2I linePt2){
	IVec2 lineDir = linePt2.get().dif(linePt1);
	return perpendicularVecToLine(lineDir,linePt1).add(this).sub(linePt1).dot(lineDir)/lineDir.len2();
    }
    
    
    /** distance to a triangle */
    public double distToTriangle(IVec2I pt1, IVec2I pt2, IVec2I pt3){
        IVec2 dir1 = pt2.get().dif(pt1);
        IVec2 dir2 = pt3.get().dif(pt1);
        double[] coef = dif(pt1).projectTo2Vec(dir1,dir2);
        if( coef[0] >= 0 ){
            if(coef[1] >= 0){
                if( (coef[0]+coef[1]) <= 1 ){ // inside triangle
                    return 0;
                }
                return distToSegment(pt2,pt3);
            }
            return distToSegment(pt1, pt2);
        }
        return distToSegment(pt1, pt3);
    }
    
}
