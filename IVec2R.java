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
   Reference class of 2 dimensional vector to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public class IVec2R extends IParameterObject implements IVec2I, IReferenceParameter{
    protected IVec2Op op;
    
    public IVec2R(IVec2Op v){ op=v; }
    public IVec2R(double x, double y){ op=new IVec2(x,y); }
    public IVec2R(IDoubleI x, IDoubleI y){ op = new FromXY(x,y); }
    
    public IVec2R(IServerI s, IVec2Op v){ super(s); op=v; }
    public IVec2R(IServerI s, double x, double y){ super(s); op=new IVec2(x,y); }
    public IVec2R(IServerI s, IDoubleI x, IDoubleI y){ super(s); op = new FromXY(x,y); }
    
    
    /** getting x component */
    public double x(){ return op.get().x; }
    /** getting y component */
    public double y(){ return op.get().y; }
    
    /** setting x component */
    public IVec2R x(double vx){ op = new FromX(op,new IDouble(vx)); return this; }
    /** setting y component */
    public IVec2R y(double vy){ op = new FromY(op,new IDouble(vy)); return this; }
    
    /** setting x component */
    public IVec2R x(IDoubleI vx){ op = new FromX(op,vx); return this; }
    /** setting y component */
    public IVec2R y(IDoubleI vy){ op = new FromY(op,vy); return this; }
    
    /** getting x component */
    public double x(ISwitchE e){ return x(); }
    /** getting y component */
    public double y(ISwitchE e){ return y(); }
    
    /** getting x component */
    public IDoubleR x(ISwitchR r){ return new IDoubleR(new X(op)); }
    /** getting y component */
    public IDoubleR y(ISwitchR r){ return new IDoubleR(new Y(op)); }
    

    public IVec2 get(){ return op.get(); }
    
    public IDoubleR getX(){ return new IDoubleR(new X(op)); }
    public IDoubleR getY(){ return new IDoubleR(new Y(op)); }
    
    public IVec2Op operator(){ return op; } // for viewer
    
    public IVec2R dup(){ return new IVec2R(op); }
    
    public IVecR to3d(){ return new IVecR(new ToVec3(this)); }
    public IVecR to3d(double z){ return new IVecR(new ToVec3(this,new IDouble(z))); }
    public IVecR to3d(IDoubleI z){ return new IVecR(new ToVec3(this,z)); }
    public IVec4R to4d(){ return new IVec4R(new ToVec4(this)); }
    public IVec4R to4d(double z, double w){ return new IVec4R(new ToVec4(this,new IDouble(z),new IDouble(w))); }
    public IVec4R to4d(IDoubleI z, IDoubleI w){ return new IVec4R(new ToVec4(this,z,w)); }
    
    public IVec2R set(IVec2I u){ op=u; return this; }
    public IVec2R set(double x, double y){ op=new IVec2(x,y); return this; }
    public IVec2R set(IDoubleI x, IDoubleI y){ op = new FromXY(x,y); return this; }
    
    public IVec2R add(double x, double y){ op = new Add(op,new IVec2(x,y)); return this; }
    public IVec2R add(IDoubleI x, IDoubleI y){ op = new Add(op,new IVec2R(x,y)); return this; }
    public IVec2R add(IVec2I u){ op = new Add(op,u); return this; }
    public IVec2R sub(double x, double y){ op = new Sub(op,new IVec2(x,y)); return this; }
    public IVec2R sub(IDoubleI x, IDoubleI y){ op = new Sub(op,new IVec2R(x,y)); return this; }
    public IVec2R sub(IVec2I u){ op = new Sub(op,u); return this; }
    public IVec2R mul(IDoubleI u){ op = new Mul(op,u); return this; }
    public IVec2R mul(double u){ op = new Mul(op, new IDouble(u)); return this; }
    public IVec2R div(IDoubleI u){ op = new Div(op,u); return this; }
    public IVec2R div(double u){ op = new Div(op, new IDouble(u)); return this; }
    public IVec2R neg(){ op = new Neg(op); return this; }
    /** alias of neg() */
    public IVec2R rev(){ return neg(); }
    /** alias of neg() */
    public IVec2R flip(){ return neg(); }

    /** setting all zero */
    public IVec2R zero(){ return set(0,0); }
    
    /** scale add */
    public IVec2R add(IVec2I v, double f){ return add(v.dup().mul(f)); }
    public IVec2R add(IVec2I v, IDoubleI f){ return add(v.dup().mul(f)); }
    /** scale add alias */
    public IVec2R add(double f, IVec2I v){ return add(v,f); }
    public IVec2R add(IDoubleI f, IVec2I v){ return add(v,f); }
    
    public double dot(IVec2I u){ return get().dot(u); }
    public double dot(double ux, double uy){ return get().dot(ux,uy); }
    public double dot(ISwitchE e, IVec2I u){ return dot(u); }
    public IDoubleR dot(ISwitchR r, IVec2I u){ return new IDoubleR(new Dot(op,u)); }
    
    public IVecR cross(IVec2I u){ return new IVecR(new Cross(op,u)); }
    public IVecR cross(double ux, double uy){ return new IVecR(new Cross(op,new IVec2(ux,uy))); }
    
    public double len(){ return get().len(); }
    public double len(ISwitchE e){ return len(); }
    public IDoubleR len(ISwitchR r){ return new IDoubleR(new Len(op)); }
    
    public double len2(){ return get().len2(); }
    public double len2(ISwitchE e){ return len2(); }
    public IDoubleR len2(ISwitchR r){ return new IDoubleR(new Len2(op)); }
    
    public IVec2R len(IDoubleI l){ op = new SetLen(op,l); return this; }
    public IVec2R len(double l){ op = new SetLen(op,new IDouble(l)); return this; }
    
    public IVec2R unit(){ op = new Unit(op); return this; }
    public IVec2R ortho(){ op = new Ortho(op); return this; }
        
    
    public double dist(IVec2I v){ return get().dist(v); }
    public double dist(double vx, double vy){ return get().dist(vx,vy); }
    public double dist(ISwitchE e, IVec2I v){ return dist(v); }
    public IDoubleR dist(ISwitchR r, IVec2I v){ return new IDoubleR(new Dist(op, v)); }
    
    /**
       squared distance 
    */
    public double dist2(IVec2I v){ return get().dist2(v); }
    public double dist2(double vx, double vy){ return get().dist2(vx,vy); }
    public double dist2(ISwitchE e, IVec2I v){ return dist2(v); }
    public IDoubleR dist2(ISwitchR r, IVec2I v){ return new IDoubleR(new Dist2(op, v)); }
    
    /**
       whether location is same or not
    */
    public boolean eq(IVec2I v){ return get().eq(v); }
    public boolean eq(double vx, double vy){ return get().eq(vx,vy); }
    public boolean eq(ISwitchE e, IVec2I v){ return eq(v); }
    public IBoolR eq(ISwitchR r, IVec2I v){ return new IBoolR(new Eq(op, v)); }
    
    public boolean eq(IVec2I v, double tolerance){ return get().eq(v,tolerance); }
    public boolean eq(double vx, double vy, double tolerance){ return get().eq(vx,vy,tolerance); }
    public boolean eq(ISwitchE e, IVec2I v, double tolerance){ return eq(v,tolerance); }
    public IBoolR eq(ISwitchR r, IVec2I v, IDoubleI tolerance){ return new IBoolR(new Eq(op,v,tolerance)); }
    
    public boolean eqX(IVec2I v){ return get().eqX(v); }
    public boolean eqY(IVec2I v){ return get().eqY(v); }
    public boolean eqX(double vx){ return get().eqX(vx); }
    public boolean eqY(double vy){ return get().eqY(vy); }
    public boolean eqX(ISwitchE e, IVec2I v){ return eqX(v); }
    public boolean eqY(ISwitchE e, IVec2I v){ return eqY(v); }
    public IBoolR eqX(ISwitchR r, IVec2I v){ return new IBoolR(new EqX(op,v)); }
    public IBoolR eqY(ISwitchR r, IVec2I v){ return new IBoolR(new EqY(op,v)); }
    
    public boolean eqX(IVec2I v, double tolerance){ return get().eqX(v,tolerance); }
    public boolean eqY(IVec2I v, double tolerance){ return get().eqY(v,tolerance); }
    public boolean eqX(double vx, double tolerance){ return get().eqX(vx,tolerance); }
    public boolean eqY(double vy, double tolerance){ return get().eqY(vy,tolerance); }
    public boolean eqX(ISwitchE e, IVec2I v, double tolerance){ return eqX(v,tolerance); }
    public boolean eqY(ISwitchE e, IVec2I v, double tolerance){ return eqY(v,tolerance); }
    public IBoolR eqX(ISwitchR r, IVec2I v, IDoubleI tolerance){ return new IBoolR(new EqX(op,v,tolerance)); }
    public IBoolR eqY(ISwitchR r, IVec2I v, IDoubleI tolerance){ return new IBoolR(new EqY(op,v,tolerance)); }
    
    
    public double angle(IVec2I u){ return get().angle(u); }
    public double angle(double ux, double uy){ return get().angle(ux,uy); }
    public double angle(ISwitchE e, IVec2I u){ return angle(u); }
    public IDoubleR angle(ISwitchR r, IVec2I u){ return new IDoubleR(new Angle(op,u)); }
    
    public IVec2R rot(double angle){ op = new Rot(op,new IDouble(angle)); return this; }
    public IVec2R rot(IDoubleI angle){ op = new Rot(op,angle); return this; }
    
    public IVec2R rot(IVec2I center, double angle){
	if(center==this) return this;
	return sub(center).rot(angle).add(center);
    }
    public IVec2R rot(double centerX, double centerY, double angle){
	return rot(new IVec2(centerX,centerY),angle);
    }
    public IVec2R rot(IVec2I center, IDoubleI angle){
	if(center==this) return this;
	return sub(center).rot(angle).add(center);
    }
    
    // to be tested. 
    public IVec2R rot(IVec2I destDir){ return rot(angle(destDir)); }
    public IVec2R rot(IVec2I center, IVec2I destPt){
	if(center==this) return this;
	return sub(center).rot(destPt.diff(center)).add(center);
    }
    
    public IVec2R scale(double f){ return mul(f); }
    public IVec2R scale(IDoubleI f){ return mul(f); }
    public IVec2R scale(IVec2I center, double f){
	if(center==this) return this;
	return sub(center).mul(f).add(center);
    }
    public IVec2R scale(double centerX, double centerY, double f){
	return scale(new IVec2(centerX,centerY),f);
    }
    public IVec2R scale(IVec2I center, IDoubleI f){
	if(center==this) return this;
	return sub(center).mul(f).add(center);
    }
    
    
    /** scale only in 1 direction */
    public IVec2R scale1d(IVec2I axis, double f){ return scale1d(axis,new IDouble(f)); }
    public IVec2R scale1d(double axisX, double axisY, double f){
        return scale1d(new IVec2(axisX,axisY),new IDouble(f));
    }
    public IVec2R scale1d(IVec2I axis, IDoubleI f){
        op = new Scale1d(op, axis, f); return this;
    }
    public IVec2R scale1d(IVec2I center, IVec2I axis, double f){
        return scale1d(center,axis,new IDouble(f));
    }
    public IVec2R scale1d(double centerX, double centerY, double axisX, double axisY, double f){
        return scale1d(new IVec2(centerX, centerY),new IVec2(axisX,axisY),new IDouble(f));
    }
    public IVec2R scale1d(IVec2I center, IVec2I axis, IDoubleI f){
        if(center==this) return this;
        return sub(center).scale1d(axis,f).add(center);
    }
    
    
    /**
       reflect (mirror) 2 dimensionally to the other side of the line
    */
    public IVec2R ref(IVec2I lineDir){ op = new Ref(op, lineDir); return this; }
    public IVec2R ref(double lineX, double lineY){ return ref(new IVec2(lineX,lineY)); }
    public IVec2R ref(IVec2I linePt, IVec2I lineDir){
	if(linePt==this) return this;
	return sub(linePt).ref(lineDir).add(linePt);
    }
    public IVec2R ref(double linePtX, double linePtY, double lineDirX, double lineDirY){
	return ref(new IVec2(linePtX,linePtY), new IVec2(lineDirX,lineDirY));
    }
    public IVec2R mirror(IVec2I lineDir){ return ref(lineDir); }
    public IVec2R mirror(double lineX, double lineY){ return ref(lineX,lineY); }
    public IVec2R mirror(IVec2I linePt, IVec2I lineDir){ return ref(linePt,lineDir); }
    public IVec2R mirror(double linePtX, double linePtY, double lineDirX, double lineDirY){
	return ref(linePtX,linePtY,lineDirX,lineDirY);
    }
    
    /** shear operation on XY*/
    public IVec2R shear(double sxy, double syx){ return shear(new IDouble(sxy), new IDouble(syx)); }
    /** shear operation on XY*/
    public IVec2R shear(IDoubleI sxy, IDoubleI syx){ op = new Shear(op, sxy, syx); return this; }
    /** shear operation on XY*/
    public IVec2R shear(IVec2I center, double sxy, double syx){
	if(center==this) return this;
	return sub(center).shear(sxy,syx).add(center);
    }
    /** shear operation on XY*/
    public IVec2R shear(IVec2I center, IDoubleI sxy, IDoubleI syx){
	if(center==this) return this;
	return sub(center).shear(sxy,syx).add(center);
    }
    
    /** alias of add() */
    public IVec2R translate(double x, double y){ return add(x,y); }
    /** alias of add() */
    public IVec2R translate(IDoubleI x, IDoubleI y){ return add(x,y); }
    /** alias of add() */
    public IVec2R translate(IVec2I v){ return add(v); }
    
    
    public IVec2R transform(IMatrix2I mat){ op = new Transform2(op,mat); return this; }
    public IVec2R transform(IMatrix3I mat){ op = new Transform3(op,mat); return this; }
    public IVec2R transform(IVec2I xvec, IVec2I yvec){
	op = new TransformVec2(op,xvec,yvec); return this;
    }
    public IVec2R transform(IVec2I xvec, IVec2I yvec, IVec2I translate){
	op = new TransformVec3(op,xvec,yvec,translate); return this; 
    }
    
    /** mv() is alias of add() */
    public IVec2R mv(double x, double y){ return add(x,y); }
    /** mv() is alias of add() */
    public IVec2R mv(IDoubleI x, IDoubleI y){ return add(x,y); }
    /** mv() is alias of add() */
    public IVec2R mv(IVec2I v){ return add(v); }
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */
    public IVec2R cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IVec2R cp(double x, double y){ return dup().add(x,y); }
    /** cp() is alias of dup().add() */
    public IVec2R cp(IDoubleI x, IDoubleI y){ return dup().add(x,y); }
    /** cp() is alias of dup().add() */
    public IVec2R cp(IVec2I v){ return dup().add(v); }
    
    
    
    public IVec2R dif(IVec2I v){ return dup().sub(v); }
    public IVec2R dif(double vx, double vy){ return dup().sub(new IVec2(vx,vy)); }
    public IVec2R diff(IVec2I v){ return dif(v); }
    public IVec2R diff(double vx, double vy){ return dif(vx,vy); }
    public IVec2R mid(IVec2I v){ return dup().add(v).div(2); }
    public IVec2R mid(double vx, double vy){ return dup().add(new IVec2(vx,vy)).div(2); }
    public IVec2R sum(IVec2I v){ return dup().add(v); }
    public IVec2R sum(double vx, double vy){ return dup().add(new IVec2(vx,vy)); }
    public IVec2R sum(IVec2I... v){
	IVec2R ret = dup();
	for(IVec2I vi : v) ret.add(vi);
	return ret;
    }
    public IVec2R bisect(IVec2I v){ return dup().unit().add(v.dup().unit()); }
    public IVec2R bisect(double vx, double vy){ return dup().unit().add(new IVec2R(vx,vy).unit()); }
    
    /** weighted sum */
    public IVec2R sum(IVec2I v2, double w1, double w2){
	return sum(v2,new IDouble(w1),new IDouble(w2));
    }
    public IVec2R sum(IVec2I v2, double w2){
	return sum(v2,new IDouble(w2));
    }
    
    public IVec2R sum(IVec2I v2, IDoubleI w1, IDoubleI w2){
	return dup().mul(w1).add(v2,w2);
    }
    public IVec2R sum(IVec2I v2, IDoubleI w2){
	return dup().mul((new IDouble(1.0)).sub(w2)).add(v2,w2);
    }
    
    /** alias of cross */
    public IVecR nml(IVec2I v){ return cross(v); }
    /** alias of cross */
    public IVecR nml(double vx, double vy){ return cross(vx,vy); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVecR nml(IVec2I pt1, IVec2I pt2){ return this.dif(pt1).cross(this.dif(pt2)); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVecR nml(double vx1, double vy1, double vx2, double vy2){
	return this.dif(vx1,vy1).cross(this.dif(vx2,vy2)); 
    }
    /** checking x, y is valid number (not Infinite, nor NaN). */
    public boolean isValid(){ return get().isValid(); }

    
    static public class ToVec3 extends IParameterObject implements IVecOp{
	public IVec2Op v;
	public IDoubleOp z;
	public ToVec3(IVec2Op v){ this.v=v; z=new IDouble(0.); }
	public ToVec3(IVec2Op v, IDoubleOp z){ this.v=v; this.z=z; }
	public IVec get(){ IVec2 v2=v.get(); return new IVec(v2.x,v2.y,z.x()); }
    }
    
    static public class ToVec4 extends IParameterObject implements IVec4Op{
	public IVec2Op v;
	public IDoubleOp z, w;
	public ToVec4(IVec2Op v){ this.v=v; z=new IDouble(0.); w=new IDouble(1.); }
	public ToVec4(IVec2Op v, IDoubleOp z, IDoubleOp w){ this.v=v; this.z=z; this.w=w; }
	public IVec4 get(){ IVec2 v2=v.get(); return new IVec4(v2.x,v2.y,z.x(),w.x()); }
    }
    
    static public class ToVec2 extends IParameterObject implements IVec2Op{
        public IVecOp v;
        public ToVec2(IVecOp v){ this.v=v; }
        public IVec2 get(){ return new IVec2(v.get()); }
    }
    
    static public class Add extends IParameterObject implements IVec2Op{
	public IVec2Op v1, v2;
	public Add(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public IVec2 get(){ return v1.get().add(v2.get()); }
    }
    static public class Sub extends IParameterObject implements IVec2Op{
	public IVec2Op v1,v2;
	public Sub(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public IVec2 get(){ return v1.get().sub(v2.get()); }
    }
    static public class Mul extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IDoubleOp d;
	public Mul(IVec2Op v, IDoubleOp d){ this.v=v; this.d=d; }
	public IVec2 get(){ return v.get().mul(d.x()); }
    }
    static public class Div extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IDoubleOp d;
	public Div(IVec2Op v, IDoubleOp d){ this.v=v; this.d=d; }
	public IVec2 get(){ return v.get().div(d.x()); }
    }
    static public class Neg extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public Neg(IVec2Op v){ this.v=v; }
	public IVec2 get(){ return v.get().neg(); }
    }
    static public class Dot extends IParameterObject implements IDoubleOp{
	public IVec2Op v1,v2;
	public Dot(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().dot(v2.get()); }
	public IDouble get(){ return v1.get().dot((Ir)null,v2.get()); }
    }
    static public class Cross extends IParameterObject implements IVecOp{
	public IVec2Op v1,v2;
	public Cross(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public IVec get(){ return v1.get().cross(v2.get()); }
    }
    static public class Angle extends IParameterObject implements IDoubleOp{
	public IVec2Op v1,v2;
	public Angle(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().angle(v2.get()); }
	public IDouble get(){ return v1.get().angle((Ir)null,v2.get()); }
    }
    static public class Rot extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IDoubleOp angle;
	public Rot(IVec2Op v, IDoubleOp angle){ this.v=v; this.angle=angle; }
	public IVec2 get(){ return v.get().rot(angle.x()); }
    }
    static public class FromXY extends IParameterObject implements IVec2Op{
	public IDoubleOp x, y;
	public FromXY(IDoubleOp x, IDoubleOp y){ this.x=x; this.y=y; }
	public IVec2 get(){ return new IVec2(x.x(), y.x()); }
    }
    static public class FromX extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IDoubleOp x;
	public FromX(IVec2Op v, IDoubleOp x){ this.v=v; this.x=x; }
	public IVec2 get(){ return new IVec2(x.x(), v.get().y()); }
    }
    static public class FromY extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IDoubleOp y;
	public FromY(IVec2Op v, IDoubleOp y){ this.v=v; this.y=y; }
	public IVec2 get(){ return new IVec2(v.get().x(), y.x()); }
    }
    static public class Len extends IParameterObject implements IDoubleOp{
	public IVec2Op v;
	public Len(IVec2Op v){ this.v=v; }
	public double x(){ return v.get().len(); }
	public IDouble get(){ return v.get().len((Ir)null); }
    }
    static public class Len2 extends IParameterObject implements IDoubleOp{
	public IVec2Op v;
	public Len2(IVec2Op v){ this.v=v; }
	public double x(){ return v.get().len2(); }
	public IDouble get(){ return v.get().len2((Ir)null); }
    }
    static public class X extends IParameterObject implements IDoubleOp{
	public IVec2Op v;
	public X(IVec2Op v){ this.v=v; }
	public double x(){ return v.get().x; }
	public IDouble get(){ return v.get().getX(); }
    }
    static public class Y extends IParameterObject implements IDoubleOp{
	public IVec2Op v;
	public Y(IVec2Op v){ this.v=v; }
	public double x(){ return v.get().y; }
	public IDouble get(){ return v.get().getY(); }
    }
    static public class Unit extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public Unit(IVec2Op v){ this.v=v; }
	public IVec2 get(){ return v.get().unit(); }
    }
    static public class Ortho extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public Ortho(IVec2Op v){ this.v=v; }
	public IVec2 get(){ return v.get().ortho(); }
    }
    
    static public class SetLen extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IDoubleOp l;
	public SetLen(IVec2Op v,  IDoubleOp l){ this.v=v; this.l=l; }
	public IVec2 get(){ return v.get().len(l.x()); }
    }
    
    static public class Dist extends IParameterObject implements IDoubleOp{
	public IVec2Op v1,v2;
	public Dist(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().dist(v2.get()); }
	public IDouble get(){ return v1.get().dist((Ir)null,v2.get()); }
    }
    
    static public class Dist2 extends IParameterObject implements IDoubleOp{
	public IVec2Op v1,v2;
	public Dist2(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().dist2(v2.get()); }
	public IDouble get(){ return v1.get().dist2((Ir)null,v2.get()); }
    }
    
    static public class Eq extends IParameterObject implements IBoolOp{
	public IVec2Op v1,v2;
	public IDoubleOp tolerance=null;
	public Eq(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public Eq(IVec2Op v1, IVec2Op v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
	public boolean x(){ 
	    if(tolerance==null) return v1.get().eq(v2.get());
	    return v1.get().eq(v2.get(),tolerance.x());
	}
	public IBool get(){
	    if(tolerance==null) return v1.get().eq((Ir)null,v2.get());
	    return v1.get().eq((Ir)null,v2.get(),tolerance.get());
	}
    }
    
    static public class EqX extends IParameterObject implements IBoolOp{
	public IVec2Op v1,v2;
	public IDoubleOp tolerance=null;
	public EqX(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public EqX(IVec2Op v1, IVec2Op v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
	public boolean x(){
	    if(tolerance==null) return v1.get().eqX(v2.get());
	    return v1.get().eqX(v2.get(),tolerance.x());
	}
	public IBool get(){
	    if(tolerance==null) return v1.get().eqX((Ir)null,v2.get());
	    return v1.get().eqX((Ir)null,v2.get(),tolerance.get());
	}
    }
    static public class EqY extends IParameterObject implements IBoolOp{
	public IVec2Op v1,v2;
	public IDoubleOp tolerance=null;
	public EqY(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public EqY(IVec2Op v1, IVec2Op v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
	public boolean x(){
	    if(tolerance==null) return v1.get().eqY(v2.get());
	    return v1.get().eqY(v2.get(),tolerance.x());
	}
	public IBool get(){
	    if(tolerance==null) return v1.get().eqY((Ir)null,v2.get());
	    return v1.get().eqY((Ir)null,v2.get(),tolerance.get());
	}
    }
    

    static public class Scale1d extends IParameterObject implements IVec2Op{
        public IVec2Op v, axis;
        public IDoubleI factor;
        public Scale1d(IVec2Op v, IVec2Op axis, IDoubleI factor){ this.v=v; this.axis=axis; this.factor=factor; }
        public IVec2 get(){ return v.get().scale1d(axis.get(),factor.get()); }
    }
    
    static public class Ref extends IParameterObject implements IVec2Op{
	public IVec2Op v, lineDir;
	public Ref(IVec2Op v,  IVec2Op lineDir){ this.v=v; this.lineDir=lineDir; }
	public IVec2 get(){ return v.get().ref(lineDir.get()); }
    }
    
    static public class Shear extends IParameterObject implements IVec2Op{
        public IVec2Op v;
        public IDoubleOp sxy, syx;
        public Shear(IVec2Op v, IDoubleOp sxy, IDoubleOp syx){ this.v=v; this.sxy = sxy; this.syx = syx; }
        public IVec2 get(){ return v.get().shear(sxy.get(), syx.get()); }
    }
    
    static public class Transform2 extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IMatrix2Op mat;
	public Transform2(IVec2Op v,  IMatrix2Op m){ this.v=v; mat=m; }
	public IVec2 get(){ return v.get().transform(mat.get()); }
    }
    
    static public class Transform3 extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IMatrix3Op mat;
	public Transform3(IVec2Op v,  IMatrix3Op m){ this.v=v; mat=m; }
	public IVec2 get(){ return v.get().transform(mat.get()); }
    }
    
    static public class TransformVec2 extends IParameterObject implements IVec2Op{
	public IVec2Op v, xvec, yvec;
	public TransformVec2(IVec2Op v,  IVec2Op xvec, IVec2Op yvec){ this.v=v; this.xvec=xvec; this.yvec=yvec; }
	public IVec2 get(){ return v.get().transform(xvec.get(),yvec.get()); }
    }
    
    static public class TransformVec3 extends IParameterObject implements IVec2Op{
	public IVec2Op v, xvec, yvec, translate;
	public TransformVec3(IVec2Op v,  IVec2Op xvec, IVec2Op yvec, IVec2Op translate){
	    this.v=v; this.xvec=xvec; this.yvec=yvec; this.translate=translate;
	}
	public IVec2 get(){ return v.get().transform(xvec.get(),yvec.get(),translate.get()); }
    }
    
    
    // reflection is not used because of the performance cost of try-catch    
    /*
    static public class Operator implements IVec2Op{
	String method;
	Operator(String method, IVec2Op v){ super(v); this.method=method; }
	public IVec2 get(){
	    try{
		Method m = IVec2.class.getMethod(method, (Class[])null);
		return (IVec2) m.invoke(v.get(), (Object[])null);
	    }catch(Exception e){ e.printStackTrace(); }
	    return null;
	}
    }
    
    static public class OperatorV2 implements IVec2Op{
	IVec2Op v2;
	String method;
	OperatorV2(String method, IVec2Op v, IVec2Op v2){
	    super(v); this.method=method; this.v2=v2;
	}
	public IVec2 get(){
	    try{
		Method m = IVec2.class.getMethod(method, new Class[]{IVec2.class} );
		return (IVec2) m.invoke(v.get(), new Object[]{v2.get()} );
	    }catch(Exception e){ e.printStackTrace(); }
	    return null;
	}
    }
    */
    
}
