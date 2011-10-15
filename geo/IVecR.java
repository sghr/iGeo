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

package igeo.geo;

import igeo.core.IParameterObject;
import igeo.core.IServerI;

/**
   Reference class of 3 dimensional vector to be used as IParameterObject.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IVecR extends IParameterObject implements IVecI, IReferenceParameter{
    protected IVecOp op;
    
    public IVecR(double x, double y, double z){ op = new IVec(x,y,z); }
    public IVecR(IVecOp v){ op=v; }
    public IVecR(IDoubleI x, IDoubleI y, IDoubleI z){ op = new FromXYZ(x,y,z); }
    
    public IVecR(IServerI s, double x, double y, double z){ super(s); op = new IVec(x,y,z); }
    public IVecR(IServerI s, IVecOp v){ super(s); op=v; }
    public IVecR(IServerI s, IDoubleI x, IDoubleI y, IDoubleI z){ super(s); op = new FromXYZ(x,y,z); }
    
    public double x(){ return op.get().x; }
    public double y(){ return op.get().y; }
    public double z(){ return op.get().z; }
    public IVec get(){ return op.get(); }

    public IVecR dup(){ return new IVecR(op); }
    
    public IVec2R to2d(){ return new IVec2R(new ToVec2(this)); }
    public IVec4R to4d(){ return new IVec4R(this); }
    public IVec4R to4d(double w){ return new IVec4R(this, w); }
    public IVec4R to4d(IDoubleI w){ return new IVec4R(this, w); }
    
    public IDoubleR getX(){ return new IDoubleR(new X(op)); }
    public IDoubleR getY(){ return new IDoubleR(new Y(op)); }
    public IDoubleR getZ(){ return new IDoubleR(new Z(op)); }
    
    public IVecOp operator(){ return op; } // for viewer
    
    
    
    public IVecR set(IVecI u){ op=u; return this; }
    public IVecR set(double x, double y, double z){ op = new IVec(x,y,z); return this; }
    public IVecR set(IDoubleI x, IDoubleI y, IDoubleI z){
	op = new FromXYZ(x,y,z); return this;
    }
    
    public IVecR add(double x, double y, double z){
	op=new Add(op,new IVec(x,y,z)); return this;
    }
    public IVecR add(IDoubleI x, IDoubleI y, IDoubleI z){
	op=new Add(op,new IVecR(x,y,z)); return this;
    }
    public IVecR add(IVecI u){ op=new Add(op,u); return this; }
    
    public IVecR sub(double x, double y, double z){
	op=new Sub(op,new IVec(x,y,z)); return this;
    }
    public IVecR sub(IDoubleI x, IDoubleI y, IDoubleI z){
	op=new Sub(op,new IVecR(x,y,z)); return this;
    }
    public IVecR sub(IVecI u){ op=new Sub(op,u); return this; }
    public IVecR mul(IDoubleI u){ op=new Mul(op,u); return this; }
    public IVecR mul(double u){ op=new Mul(op,new IDouble(u)); return this; }
    public IVecR div(IDoubleI u){ op=new Div(op,u); return this; }
    public IVecR div(double u){ op=new Div(op,new IDouble(u)); return this; }
    public IVecR neg(){ op=new Neg(op); return this; }
    /** alias of neg() */
    public IVecR rev(){ return neg(); }
    /** alias of neg() */
    public IVecR flip(){ return neg(); }
    
    
    public IVecR add(IVecI v, double f){ return add(v.dup().mul(f)); }
    public IVecR add(IVecI v, IDoubleI f){ return add(v.dup().mul(f)); }
    
    public double dot(IVecI u){ return get().dot(u); }
    //public IDoubleR dotR(IVecI u){ return new IDoubleR(new Dot(op,u)); }
    public double dot(ISwitchE e, IVecI u){ return dot(u); }
    public IDoubleR dot(ISwitchR r, IVecI u){ return new IDoubleR(new Dot(op,u)); }
    
    
    /** cross is now creating a new instance (2011/08/03)
     */
    //public IVecR cross(IVecI u){ op=new Cross(op,u); return this; }
    public IVecR cross(IVecI u){ return new IVecR(new Cross(op,u)); }
    
    
    public double len(){ return get().len(); }
    //public IDoubleR lenR(){ return new IDoubleR(new Len(op)); }
    public double len(ISwitchE e){ return len(); }
    public IDoubleR len(ISwitchR r){ return new IDoubleR(new Len(op)); }
    
    public double len2(){ return get().len2(); }
    //public IDoubleR len2R(){ return new IDoubleR(new Len2(op)); }
    public double len2(ISwitchE e){ return len2(); }
    public IDoubleR len2(ISwitchR r){ return new IDoubleR(new Len2(op)); }
    
    public IVecR len(IDoubleI l){ op=new SetLen(op,l); return this; }
    public IVecR len(double l){ op=new SetLen(op,new IDouble(l)); return this; }
    
    public IVecR unit(){ op=new Unit(op); return this; }
    
    public double dist(IVecI v){ return get().dist(v); }
    //public IDoubleR distR(IVecI v){ return new IDoubleR(new Dist(op, v)); }
    public double dist(ISwitchE e, IVecI v){ return dist(v); }
    public IDoubleR dist(ISwitchR r, IVecI v){ return new IDoubleR(new Dist(op, v)); }
    
    public double dist2(IVecI v){ return get().dist2(v); }
    //public IDoubleR dist2R(IVecI v){ return new IDoubleR(new Dist2(op, v)); }
    public double dist2(ISwitchE e, IVecI v){ return dist2(v); }
    public IDoubleR dist2(ISwitchR r, IVecI v){ return new IDoubleR(new Dist2(op, v)); }
    
    public boolean eq(IVecI v){ return get().eq(v); }
    //public IBoolR eqR(IVecI v){ return new IBoolR(new Eq(op, v)); }
    public boolean eq(ISwitchE e, IVecI v){ return eq(v); }
    public IBoolR eq(ISwitchR r, IVecI v){ return new IBoolR(new Eq(op, v)); }
    
    public boolean eq(IVecI v, double resolution){ return get().eq(v,resolution); }
    //public IBoolR eqR(IVecI v, IDoubleI resolution){return new IBoolR(new Eq(op, v, resolution));}
    public boolean eq(ISwitchE e, IVecI v, double resolution){ return eq(v,resolution); }
    public IBoolR eq(ISwitchR r, IVecI v, IDoubleI resolution){
	return new IBoolR(new Eq(op, v, resolution));
    }
    
    public boolean eqX(IVecI v){ return get().eqX(v); }
    public boolean eqY(IVecI v){ return get().eqY(v); }
    public boolean eqZ(IVecI v){ return get().eqZ(v); }
    //public IBoolR eqXR(IVecI v){ return new IBoolR(new EqX(op, v)); }
    //public IBoolR eqYR(IVecI v){ return new IBoolR(new EqY(op, v)); }
    //public IBoolR eqZR(IVecI v){ return new IBoolR(new EqZ(op, v)); }
    public boolean eqX(ISwitchE e, IVecI v){ return eqX(v); }
    public boolean eqY(ISwitchE e, IVecI v){ return eqY(v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return eqZ(v); }
    public IBoolR eqX(ISwitchR r, IVecI v){ return new IBoolR(new EqX(op, v)); }
    public IBoolR eqY(ISwitchR r, IVecI v){ return new IBoolR(new EqY(op, v)); }
    public IBoolR eqZ(ISwitchR r, IVecI v){ return new IBoolR(new EqZ(op, v)); }
    
    public boolean eqX(IVecI v, double resolution){ return get().eqX(v,resolution); }
    public boolean eqY(IVecI v, double resolution){ return get().eqY(v,resolution); }
    public boolean eqZ(IVecI v, double resolution){ return get().eqZ(v,resolution); }
    //public IBoolR eqXR(IVecI v, IDoubleI resolution){ return new IBoolR(new EqX(op,v,resolution)); }
    //public IBoolR eqYR(IVecI v, IDoubleI resolution){ return new IBoolR(new EqY(op,v,resolution)); }
    //public IBoolR eqZR(IVecI v, IDoubleI resolution){ return new IBoolR(new EqZ(op,v,resolution)); }
    public boolean eqX(ISwitchE e, IVecI v, double resolution){ return eqX(v,resolution); }
    public boolean eqY(ISwitchE e, IVecI v, double resolution){ return eqY(v,resolution); }
    public boolean eqZ(ISwitchE e, IVecI v, double resolution){ return eqZ(v,resolution); }
    public IBoolR eqX(ISwitchR r, IVecI v, IDoubleI resolution){ return new IBoolR(new EqX(op,v,resolution)); }
    public IBoolR eqY(ISwitchR r, IVecI v, IDoubleI resolution){ return new IBoolR(new EqY(op,v,resolution)); }
    public IBoolR eqZ(ISwitchR r, IVecI v, IDoubleI resolution){ return new IBoolR(new EqZ(op,v,resolution)); }
    
    
    
    /**
       @return angle between two vector from 0 to Pi
    */
    public double angle(IVecI u){ return get().angle(u); }
    //public IDoubleR angleR(IVecI u){ return new IDoubleR(new Angle(op,u)); }
    public double angle(ISwitchE e, IVecI u){ return angle(u); }
    public IDoubleR angle(ISwitchR r, IVecI u){ return new IDoubleR(new Angle(op,u)); }
    
    /**
       @param axis axis to determin sign of angle following right-handed screw rule.
       @return angle between two vector from -Pi to Pi. Sign follows right-handed screw rule along axis
    */
    public double angle(IVecI u, IVecI axis){ return get().angle(u,axis); }
    //public IDoubleR angleR(IVecI u, IVecI axis){ return new IDoubleR(new Angle(op,u,axis)); }
    public double angle(ISwitchE e, IVecI u, IVecI axis){ return angle(u,axis); }
    public IDoubleR angle(ISwitchR r, IVecI u, IVecI axis){
	return new IDoubleR(new Angle(op,u,axis));
    }
    
    public IVecR rot(IVecI axis, IDoubleI angle){
	op=new Rot(op,axis,angle); return this; 
    }
    public IVecR rot(IVecI axis, double angle){ return rot(axis,new IDouble(angle)); }
    
    public IVecR rot(IVecI center, IVecI axis, IDoubleI angle){
	if(center==this) return this;
	return sub(center).rot(axis,angle).add(center);
    }
    public IVecR rot(IVecI center, IVecI axis, double angle){
	return rot(center,axis,new IDouble(angle));
    }
    
    /**
       rotate to destination direction vector
    */
    public IVecR rot(IVecI axis, IVecI destDir){
	return rot(axis, destDir.cross(axis).angle(cross(axis)));
    }
    /**
       rotate to destination point location
    */
    public IVecR rot(IVecI center, IVecI axis, IVecI destPt){
	if(center==this) return this;
	return sub(center).rot(axis,destPt.diff(center)).add(center);
    }
    
    /** alias of mul */
    public IVecR scale(IDoubleI f){ return mul(f); }
    public IVecR scale(double f){ return mul(f); }
    public IVecR scale(IVecI center, IDoubleI f){
	if(center==this) return this;
	return sub(center).mul(f).add(center);
    }
    public IVecR scale(IVecI center, double f){
	if(center==this) return this;
	return sub(center).mul(f).add(center);
    }
    
    /** scale only in 1 direction */
    public IVecR scale1d(IVecI axis, double f){ return scale1d(axis,new IDouble(f)); }
    public IVecR scale1d(IVecI axis, IDoubleI f){
	op = new Scale1d(op, axis, f); return this;
    }
    public IVecR scale1d(IVecI center, IVecI axis, double f){
	return scale1d(center,axis,new IDouble(f));
    }
    public IVecR scale1d(IVecI center, IVecI axis, IDoubleI f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    
    
    /**
       reflect (mirror) 3 dimensionally to the other side of the plane
    */
    public IVecR ref(IVecI planeDir){ op=new Ref(op,planeDir); return this; }
    public IVecR ref(IVecI center, IVecI planeDir){
	if(center==this) return this;
	return sub(center).ref(planeDir).add(center);
    }
    public IVecR mirror(IVecI planeDir){ return ref(planeDir); }
    public IVecR mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    public IVecR shear(double sxy, double syx, double syz, double szy, double szx, double sxz){
	return shear(new IDouble(sxy),new IDouble(syx),new IDouble(syz),
		     new IDouble(szy),new IDouble(szx),new IDouble(sxz));
    }
    public IVecR shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	op = new Shear(op,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVecR shear(IVecI center, double sxy, double syx, double syz, double szy, double szx, double sxz){
	if(center==this) return this;
	return sub(center).shear(sxy,syx,syz,szy,szx,sxz).add(center);
    }
    public IVecR shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz, IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	if(center==this) return this;
	return sub(center).shear(sxy,syx,syz,szy,szx,sxz).add(center);
    }
    
    public IVecR shearXY(double sxy, double syx){ return shear(sxy,syx,0,0,0,0); }
    public IVecR shearXY(IDoubleI sxy, IDoubleI syx){ return shear(sxy,syx,null,null,null,null); }
    public IVecR shearXY(IVecI center, double sxy, double syx){
	if(center==this){ return this; } return sub(center).shearXY(sxy,syx).add(center);
    }
    public IVecR shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	if(center==this){ return this; } return sub(center).shearXY(sxy,syx).add(center);
    }
    
    public IVecR shearYZ(double syz, double szy){ return shear(0,0,syz,szy,0,0); }
    public IVecR shearYZ(IDoubleI syz, IDoubleI szy){ return shear(null,null,syz,szy,null,null); }
    public IVecR shearYZ(IVecI center, double syz, double szy){
	if(center==this){ return this; } return sub(center).shearYZ(syz,szy).add(center);
    }
    public IVecR shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	if(center==this){ return this; } return sub(center).shearYZ(syz,szy).add(center);
    }
    
    public IVecR shearZX(double szx, double sxz){ return shear(0,0,0,0,szx,sxz); }
    public IVecR shearZX(IDoubleI szx, IDoubleI sxz){ return shear(null,null,null,null,szx,sxz); }
    public IVecR shearZX(IVecI center, double szx, double sxz){
	if(center==this){ return this; } return sub(center).shearZX(szx,sxz).add(center);
    }
    public IVecR shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	if(center==this){ return this; } return sub(center).shearZX(szx,sxz).add(center);
    }
    
    
    
    /** translate is alias of add() */
    public IVecR translate(double x, double y, double z){ return add(x,y,z); }
    public IVecR translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IVecR translate(IVecI v){ return add(v); }
    
    
    /**
       transform with matrix
    */
    public IVecR transform(IMatrix3I mat){ op = new Transform3(op,mat); return this; }
    public IVecR transform(IMatrix4I mat){ op = new Transform4(op,mat); return this; }
    public IVecR transform(IVecI xvec, IVecI yvec, IVecI zvec){
	op = new TransformVec3(op,xvec,yvec,zvec); return this;
    }
    public IVecR transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	op = new TransformVec4(op,xvec,yvec,zvec,translate); return this;
    }
    
    
    /** mv() is alias of add() */
    public IVecR mv(double x, double y, double z){ return add(x,y,z); }
    public IVecR mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IVecR mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IVecR cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IVecR cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IVecR cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IVecR cp(IVecI v){ return dup().add(v); }
    
    
    /********************************************************************************
     * methods creating new instance
     ********************************************************************************/
    public IVecR diff(IVecI v){ return dup().sub(v); }
    public IVecR mid(IVecI v){ return dup().add(v).div(2); }
    public IVecR sum(IVecI v){ return dup().add(v); }
    public IVecR sum(IVecI... v){
	IVecR ret = this.dup();
	for(IVecI vi: v) ret.add(vi);
	return ret;
    }
    
    public IVecR bisect(IVecI v){ return dup().unit().add(v.dup().unit()); }
    
    
    /**
       weighted sum, creating a new instance
    */
    public IVecR sum(IVecI v2, IDoubleI w1, IDoubleI w2){
	return dup().mul(w1).add(v2,w2);
    }
    public IVecR sum(IVecI v2, IDoubleI w2){
	return dup().mul((new IDouble(1.0)).sub(w2)).add(v2,w2);
    }
    
    public IVecR sum(IVecI v2, double w1, double w2){
	return sum(v2,new IDouble(w1),new IDouble(w2));
    }
    public IVecR sum(IVecI v2, double w2){ return sum(v2,new IDouble(w2)); }
    
    
    /**
       alias of cross. (not unitized ... ?)
    */
    public IVecR nml(IVecI v){ return cross(v); }
    /**
       create normal vector from 3 points of self, pt1 and pt2
    */
    public IVecR nml(IVecI pt1, IVecI pt2){
	return this.diff(pt1).cross(this.diff(pt2)).unit();
    }
    
    /** checking x, y, and z is valid number (not Infinite, nor NaN). */
    public boolean isValid(){ return get().isValid(); }
    
    // /////////////////////////////////
    // subclasses
    // /////////////////////////////////
    
    static public class Add extends IParameterObject implements IVecOp{
	public IVecOp v1, v2;
	public Add(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public IVec get(){ return v1.get().add(v2.get()); }
    }
    
    static public class Sub extends IParameterObject implements IVecOp{
	public IVecOp v1, v2;
	public Sub(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public IVec get(){ return v1.get().sub(v2.get()); }
    }
    
    static public class Mul extends IParameterObject implements IVecOp{
	public IVecOp v;
	public IDoubleOp d;
	public Mul(IVecOp v, IDoubleOp d){ this.v=v; this.d=d; }
	public IVec get(){ return v.get().mul(d.x()); }
    }
    
    static public class Div extends IParameterObject implements IVecOp{
	public IVecOp v;
	public IDoubleOp d;
	public Div(IVecOp v, IDoubleOp d){ this.v=v; this.d=d; }
	public IVec get(){ return v.get().div(d.x()); }
    }
    
    static public class Neg extends IParameterObject implements IVecOp{
	public IVecOp v;
	public Neg(IVecOp v){ this.v=v; }
	public IVec get(){ return v.get().neg(); }
    }
    
    static public class FromXYZ extends IParameterObject implements IVecOp{
	public IDoubleOp x,y,z;
	public FromXYZ(IDoubleOp x, IDoubleOp y, IDoubleOp z){ this.x=x; this.y=y; this.z=z; }
	public IVec get(){ return new IVec(x.x(),y.x(),z.x()); }
    }
    
    static public class ToVec2 extends IParameterObject implements IVec2Op{
	public IVecOp v;
	public ToVec2(IVecOp v){ this.v=v; }
	public IVec2 get(){ return new IVec2(v.get()); }
    }
    
    static public class Dot extends IParameterObject implements IDoubleOp{
	public IVecOp v1, v2;
	public Dot(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().dot(v2.get()); }
	public IDouble get(){ return v1.get().dot((Ir)null,v2.get()); }
    }
    static public class Cross extends IParameterObject implements IVecOp{
	public IVecOp v1, v2;
	public Cross(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public IVec get(){ return v1.get().cross(v2.get()); }
    }

    static public class Angle extends IParameterObject implements IDoubleOp{
	public IVecOp v1,v2,axis=null;
	public Angle(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public Angle(IVecOp v1, IVecOp v2, IVecOp axis){ this.v1=v1; this.v2=v2; this.axis=axis; }
	public double x(){
	    if(axis==null) return v1.get().angle(v2.get());
	    return v1.get().angle(v2.get(),axis.get());
	}
	public IDouble get(){
	    if(axis==null) return v1.get().angle((Ir)null,v2.get());
	    return v1.get().angle((Ir)null,v2.get(),axis.get());
	}
    }
    /*
    static public class AngleWithAxis extends IParameterObject implements IDoubleOp{
	public IVecOp v1,v2,axis;
	public AngleWithAxis(IVecOp v1, IVecOp v2,IVecOp ax){ this.v1=v1; this.v2=v2; axis=ax; }
	public double x(){ return v1.get().angle(v2.get(),axis.get()); }
	public IDouble get(){ return v1.get().angle((Ir)null,v2.get(),axis.get()); }
    }
    */
    
    static public class Rot extends IParameterObject implements IVecOp{
	public IVecOp v,axis;
	public IDoubleOp angle;
	public Rot(IVecOp u, IVecOp ax, IDoubleOp a){ v=u; axis=ax; angle=a; }
	public IVec get(){ return v.get().rot(axis.get(),angle.x()); }
    }
    
    static public class Len extends IParameterObject implements IDoubleOp{
	public IVecOp v;
	public Len(IVecOp v){ this.v=v; }
	public double x(){ return v.get().len(); }
	public IDouble get(){ return v.get().len((Ir)null); }
    }
    
    static public class Len2 extends IParameterObject implements IDoubleOp{
	public IVecOp v;
	public Len2(IVecOp v){ this.v=v; }
	public double x(){ return v.get().len2(); }
	public IDouble get(){ return v.get().len2((Ir)null); }
    }
    
    static public class X extends IParameterObject implements IDoubleOp{
	public IVecOp v;
	public X(IVecOp v){ this.v=v; }
	public double x(){ return v.get().x; }
	public IDouble get(){ return v.get().getX(); }
    }
    
    static public class Y extends IParameterObject implements IDoubleOp{
	public IVecOp v;
	public Y(IVecOp v){ this.v=v; }
	public double x(){ return v.get().y; }
	public IDouble get(){ return v.get().getY(); }
    }

    static public class Z extends IParameterObject implements IDoubleOp{
	public IVecOp v;
	public Z(IVecOp v){ this.v=v; }
	public double x(){ return v.get().z; }
	public IDouble get(){ return v.get().getZ(); }
    }
    
    static public class Unit extends IParameterObject implements IVecOp{
	public IVecOp v;
	public Unit(IVecOp v){ this.v=v; }
	public IVec get(){ return v.get().unit(); }
    }
    
    static public class SetLen extends IParameterObject implements IVecOp{
	public IVecOp v;
	public IDoubleOp l;
	public SetLen(IVecOp v, IDoubleOp l){ this.v=v; this.l=l; }
	public IVec get(){ return v.get().len(l.x()); }
    }
    
    
    static public class Dist extends IParameterObject implements IDoubleOp{
        public IVecOp v1,v2;
        public Dist(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
        public double x(){ return v1.get().dist(v2.get()); }
        public IDouble get(){ return v1.get().dist((Ir)null,v2.get()); }
    }
    
    static public class Dist2 extends IParameterObject implements IDoubleOp{
        public IVecOp v1,v2;
        public Dist2(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
        public double x(){ return v1.get().dist2(v2.get()); }
        public IDouble get(){ return v1.get().dist2((Ir)null,v2.get()); }
    }
    
    static public class Eq extends IParameterObject implements IBoolOp{
        public IVecOp v1,v2;
	public IDoubleOp resolution=null;
        public Eq(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public Eq(IVecOp v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; resolution=reso; }
        public boolean x(){
	    if(resolution==null) return v1.get().eq(v2.get());
	    return v1.get().eq(v2.get(),resolution.x());
	}
        public IBool get(){
	    if(resolution==null) return v1.get().eq((Ir)null,v2.get());
	    return v1.get().eq((Ir)null,v2.get(),resolution.get());
	}
    }
    
    static public class EqX extends IParameterObject implements IBoolOp{
        public IVecOp v1,v2;
	public IDoubleOp resolution=null;
        public EqX(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public EqX(IVecOp v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; resolution=reso; }
        public boolean x(){
	    if(resolution==null) return v1.get().eqX(v2.get());
	    return v1.get().eqX(v2.get(),resolution.x());
	}
        public IBool get(){
	    if(resolution==null) return v1.get().eqX((Ir)null,v2.get());
	    return v1.get().eqX((Ir)null,v2.get(),resolution.get());
	}
    }
    
    static public class EqY extends IParameterObject implements IBoolOp{
        public IVecOp v1,v2;
	public IDoubleOp resolution=null;
        public EqY(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public EqY(IVecOp v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; resolution=reso; }
        public boolean x(){
	    if(resolution==null) return v1.get().eqY(v2.get());
	    return v1.get().eqY(v2.get(),resolution.x());
	}
        public IBool get(){
	    if(resolution==null) return v1.get().eqY((Ir)null,v2.get());
	    return v1.get().eqY((Ir)null,v2.get(),resolution.get());
	}
    }
    
    static public class EqZ extends IParameterObject implements IBoolOp{
        public IVecOp v1,v2;
	public IDoubleOp resolution=null;
        public EqZ(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public EqZ(IVecOp v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; resolution=reso; }
        public boolean x(){
	    if(resolution==null) return v1.get().eqZ(v2.get());
	    return v1.get().eqZ(v2.get(),resolution.x());
	}
        public IBool get(){
	    if(resolution==null) return v1.get().eqZ((Ir)null,v2.get());
	    return v1.get().eqZ((Ir)null,v2.get(),resolution.get());
	}
    }
    
    static public class Scale1d extends IParameterObject implements IVecOp{
	public IVecOp v, axis;
	public IDoubleI factor;
	public Scale1d(IVecOp v, IVecOp axis, IDoubleI factor){ this.v=v; this.axis=axis; this.factor=factor; }
	public IVec get(){ return v.get().scale1d(axis.get(),factor.get()); }
    }
    
    static public class Ref extends IParameterObject implements IVecOp{
	public IVecOp v, plane;
	public Ref(IVecOp v, IVecOp plane){ this.v=v; this.plane=plane; }
	public IVec get(){ return v.get().ref(plane.get()); }
    }
    
    static public class Shear extends IParameterObject implements IVecOp{
	public IVecOp v;
	public IDoubleOp sxy, syx, syz, szy, szx, sxz;
	public Shear(IVecOp v, IDoubleOp sxy, IDoubleOp syx, IDoubleOp syz,
		     IDoubleOp szy, IDoubleOp szx, IDoubleOp sxz){
	    this.v=v; this.sxy = sxy; this.syx = syx; this.syz = syz; this.szy = szy;
	    this.szx = szx; this.sxz = sxz;
	}
	public IVec get(){ return v.get().shear(sxy.get(), syx.get(),
						syz.get(), szy.get(),
						szx.get(), sxz.get());
	}
    }
    
    static public class Transform3 extends IParameterObject implements IVecOp{
	public IVecOp v;
	public IMatrix3Op mat;
	public Transform3(IVecOp v, IMatrix3Op m){ this.v=v; mat=m; }
	public IVec get(){ return v.get().transform(mat.get()); }
    }
    
    static public class Transform4 extends IParameterObject implements IVecOp{
	public IVecOp v;
	public IMatrix4Op mat;
	public Transform4(IVecOp v, IMatrix4Op m){ this.v=v; mat=m; }
	public IVec get(){ return v.get().transform(mat.get()); }
    }
    
    static public class TransformVec3 extends IParameterObject implements IVecOp{
	public IVecOp v, x, y, z;
	public TransformVec3(IVecOp v, IVecOp x, IVecOp y, IVecOp z){
	    this.v=v; this.x=x; this.y=y; this.z=z;
	}
	public IVec get(){ return v.get().transform(x.get(),y.get(),z.get()); }
    }
    
    static public class TransformVec4 extends IParameterObject implements IVecOp{
	public IVecOp v, x, y, z, trans;
	public TransformVec4(IVecOp v, IVecOp x, IVecOp y, IVecOp z, IVecOp trans){
	    this.v=v; this.x=x; this.y=y; this.z=z; this.trans=trans;
	}
	public IVec get(){ return v.get().transform(x.get(),y.get(),z.get(),trans.get()); }
    }
    
    
}
