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
   Reference class of 3 dimensional vector to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public class IVecR extends IParameterObject implements IVecI, IReferenceParameter{
    protected IVecOp op;
    
    public IVecR(double x, double y, double z){ op = new IVec(x,y,z); }
    public IVecR(IVecOp v){ op=v; }
    public IVecR(IDoubleI x, IDoubleI y, IDoubleI z){ op = new FromXYZ(x,y,z); }
    
    public IVecR(IServerI s, double x, double y, double z){ super(s); op = new IVec(x,y,z); }
    public IVecR(IServerI s, IVecOp v){ super(s); op=v; }
    public IVecR(IServerI s, IDoubleI x, IDoubleI y, IDoubleI z){ super(s); op = new FromXYZ(x,y,z); }
    
    /** getting x component */
    public double x(){ return op.get().x; }
    /** getting y component */
    public double y(){ return op.get().y; }
    /** getting z component */
    public double z(){ return op.get().z; }
    
    /** setting x component */
    public IVecR x(double vx){ op = new FromX(op,new IDouble(vx)); return this; }
    /** setting y component */
    public IVecR y(double vy){ op = new FromY(op,new IDouble(vy)); return this; }
    /** setting z component */
    public IVecR z(double vz){ op = new FromZ(op,new IDouble(vz)); return this; }
    
    /** setting x component */
    public IVecR x(IDoubleI vx){ op = new FromX(op,vx); return this; }
    /** setting y component */
    public IVecR y(IDoubleI vy){ op = new FromY(op,vy); return this; }
    /** setting z component */
    public IVecR z(IDoubleI vz){ op = new FromZ(op,vz); return this; }

    /** setting x component by x component of input vector*/
    public IVecR x(IVecI v){ op = new FromX(op, v.x((ISwitchR)null)); return this; }
    /** setting y component by y component of input vector*/
    public IVecR y(IVecI v){ op = new FromY(op, v.y((ISwitchR)null)); return this; }
    /** setting z component by z component of input vector*/
    public IVecR z(IVecI v){ op = new FromZ(op, v.z((ISwitchR)null)); return this; }
    
    /** setting x component by x component of input vector*/
    public IVecR x(IVec2I v){ op = new FromX(op, v.x((ISwitchR)null)); return this; }
    /** setting y component by y component of input vector*/
    public IVecR y(IVec2I v){ op = new FromY(op, v.y((ISwitchR)null)); return this; }
    

    
    /** getting x component */
    public double x(ISwitchE e){ return x(); }
    /** getting y component */
    public double y(ISwitchE e){ return y(); }
    /** getting z component */
    public double z(ISwitchE e){ return z(); }
    
    /** getting x component */
    public IDoubleR x(ISwitchR r){ return new IDoubleR(new X(op)); }
    /** getting y component */
    public IDoubleR y(ISwitchR r){ return new IDoubleR(new Y(op)); }
    /** getting z component */
    public IDoubleR z(ISwitchR r){ return new IDoubleR(new Z(op)); }
    
    
    public IVec get(){ return op.get(); }
    
    public IVecR dup(){ return new IVecR(op); }
    
    public IVec2R to2d(){ return new IVec2R(new ToVec2(this)); }
    public IVec2R to2d(IVecI projectionDir){
	return new IVec2R(new ToVec2WithProjection(this,projectionDir));
    }
    public IVec2R to2d(IVecI xaxis, IVecI yaxis){
	return new IVec2R(new ToVec2WithAxis(this,xaxis,yaxis));
    }
    public IVec2R to2d(IVecI xaxis, IVecI yaxis, IVecI origin){
	return new IVec2R(new ToVec2WithAxisAndOrigin(this,xaxis,yaxis,origin));
    }
    
    
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
    
    /** setting all zero */
    public IVecR zero(){ return set(0,0,0); }
    
    /** scale add */
    public IVecR add(IVecI v, double f){ return add(v.dup().mul(f)); }
    public IVecR add(IVecI v, IDoubleI f){ return add(v.dup().mul(f)); }
    /** scale add alias */
    public IVecR add(double f, IVecI v){ return add(v,f); }
    public IVecR add(IDoubleI f, IVecI v){ return add(v,f); }
    
    
    public double dot(IVecI u){ return get().dot(u); }
    public double dot(double ux, double uy, double uz){ return get().dot(ux,uy,uz); }
    public double dot(ISwitchE e, IVecI u){ return dot(u); }
    public IDoubleR dot(ISwitchR r, IVecI u){ return new IDoubleR(new Dot(op,u)); }
    
    
    //public IVecR cross(IVecI u){ op=new Cross(op,u); return this; }
    /** cross is creating a new instance (2011/08/03) */
    public IVecR cross(IVecI u){ return new IVecR(new Cross(op,u)); }
    public IVecR cross(double ux, double uy, double uz){
	return new IVecR(new Cross(op,new IVec(ux,uy,uz)));
    }
    
    public double len(){ return get().len(); }
    public double len(ISwitchE e){ return len(); }
    public IDoubleR len(ISwitchR r){ return new IDoubleR(new Len(op)); }
    
    public double len2(){ return get().len2(); }
    public double len2(ISwitchE e){ return len2(); }
    public IDoubleR len2(ISwitchR r){ return new IDoubleR(new Len2(op)); }
    
    public IVecR len(IDoubleI l){ op=new SetLen(op,l); return this; }
    public IVecR len(double l){ op=new SetLen(op,new IDouble(l)); return this; }
    
    public IVecR unit(){ op=new Unit(op); return this; }
    
    public double dist(IVecI v){ return get().dist(v); }
    public double dist(double vx, double vy, double vz){ return get().dist(vx,vy,vz); }
    public double dist(ISwitchE e, IVecI v){ return dist(v); }
    public IDoubleR dist(ISwitchR r, IVecI v){ return new IDoubleR(new Dist(op, v)); }
    
    public double dist2(IVecI v){ return get().dist2(v); }
    public double dist2(double vx, double vy, double vz){ return get().dist2(vx,vy,vz); }
    public double dist2(ISwitchE e, IVecI v){ return dist2(v); }
    public IDoubleR dist2(ISwitchR r, IVecI v){ return new IDoubleR(new Dist2(op, v)); }
    
    public boolean eq(IVecI v){ return get().eq(v); }
    public boolean eq(double vx, double vy, double vz){ return get().eq(vx,vy,vz); }
    public boolean eq(ISwitchE e, IVecI v){ return eq(v); }
    public IBoolR eq(ISwitchR r, IVecI v){ return new IBoolR(new Eq(op, v)); }
    
    public boolean eq(IVecI v, double tolerance){ return get().eq(v,tolerance); }
    public boolean eq(double vx, double vy, double vz, double tolerance){ return get().eq(vx,vy,vz,tolerance); }
    public boolean eq(ISwitchE e, IVecI v, double tolerance){ return eq(v,tolerance); }
    public IBoolR eq(ISwitchR r, IVecI v, IDoubleI tolerance){
	return new IBoolR(new Eq(op, v, tolerance));
    }
    
    public boolean eqX(IVecI v){ return get().eqX(v); }
    public boolean eqY(IVecI v){ return get().eqY(v); }
    public boolean eqZ(IVecI v){ return get().eqZ(v); }
    public boolean eqX(double vx){ return get().eqX(vx); }
    public boolean eqY(double vy){ return get().eqY(vy); }
    public boolean eqZ(double vz){ return get().eqZ(vz); }
    public boolean eqX(ISwitchE e, IVecI v){ return eqX(v); }
    public boolean eqY(ISwitchE e, IVecI v){ return eqY(v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return eqZ(v); }
    public IBoolR eqX(ISwitchR r, IVecI v){ return new IBoolR(new EqX(op, v)); }
    public IBoolR eqY(ISwitchR r, IVecI v){ return new IBoolR(new EqY(op, v)); }
    public IBoolR eqZ(ISwitchR r, IVecI v){ return new IBoolR(new EqZ(op, v)); }
    
    public boolean eqX(IVecI v, double tolerance){ return get().eqX(v,tolerance); }
    public boolean eqY(IVecI v, double tolerance){ return get().eqY(v,tolerance); }
    public boolean eqZ(IVecI v, double tolerance){ return get().eqZ(v,tolerance); }
    public boolean eqX(double vx, double tolerance){ return get().eqX(vx,tolerance); }
    public boolean eqY(double vy, double tolerance){ return get().eqY(vy,tolerance); }
    public boolean eqZ(double vz, double tolerance){ return get().eqZ(vz,tolerance); }
    public boolean eqX(ISwitchE e, IVecI v, double tolerance){ return eqX(v,tolerance); }
    public boolean eqY(ISwitchE e, IVecI v, double tolerance){ return eqY(v,tolerance); }
    public boolean eqZ(ISwitchE e, IVecI v, double tolerance){ return eqZ(v,tolerance); }
    public IBoolR eqX(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBoolR(new EqX(op,v,tolerance)); }
    public IBoolR eqY(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBoolR(new EqY(op,v,tolerance)); }
    public IBoolR eqZ(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBoolR(new EqZ(op,v,tolerance)); }
    
    
    
    /** @return angle between two vector from 0 to Pi */
    public double angle(IVecI u){ return get().angle(u); }
    public double angle(double ux, double uy, double uz){ return get().angle(ux,uy,uz); }
    public double angle(ISwitchE e, IVecI u){ return angle(u); }
    public IDoubleR angle(ISwitchR r, IVecI u){ return new IDoubleR(new Angle(op,u)); }
    
    /**
       @param axis axis to determin sign of angle following right-handed screw rule.
       @return angle between two vector from -Pi to Pi. Sign follows right-handed screw rule along axis
    */
    public double angle(IVecI u, IVecI axis){ return get().angle(u,axis); }
    public double angle(double ux, double uy, double uz, double axisX, double axisY, double axisZ){
	return get().angle(ux,uy,uz,axisX,axisY,axisZ);
    }
    public double angle(ISwitchE e, IVecI u, IVecI axis){ return angle(u,axis); }
    public IDoubleR angle(ISwitchR r, IVecI u, IVecI axis){
	return new IDoubleR(new Angle(op,u,axis));
    }
    
    public IVecR rot(IDoubleI angle){ op=new Rot2(op,angle); return this; }
    public IVecR rot(double angle){ return rot(new IDouble(angle)); }
    
    public IVecR rot(IVecI axis, IDoubleI angle){
	op=new Rot(op,axis,angle); return this; 
    }
    public IVecR rot(IVecI axis, double angle){ return rot(axis,new IDouble(angle)); }
    public IVecR rot(double axisX, double axisY, double axisZ, double angle){
	return rot(new IVec(axisX,axisY,axisZ),new IDouble(angle));
    }
    
    public IVecR rot(IVecI center, IVecI axis, IDoubleI angle){
	if(center==this){ return this; } return sub(center).rot(axis,angle).add(center);
    }
    public IVecR rot(IVecI center, IVecI axis, double angle){
	return rot(center,axis,new IDouble(angle));
    }
    public IVecR rot(double centerX, double centerY, double centerZ,
		     double axisX, double axisY, double axisZ, double angle){
	return rot(new IVec(centerX,centerY,centerZ),new IVec(axisX,axisY,axisZ),
		   new IDouble(angle));
    }
    
    /** rotate to destination direction vector */
    public IVecR rot(IVecI axis, IVecI destDir){
	return rot(axis, destDir.cross(axis).angle(cross(axis)));
    }
    /** rotate to destination point location */
    public IVecR rot(IVecI center, IVecI axis, IVecI destPt){
	if(center==this) return this;
	return sub(center).rot(axis,destPt.diff(center)).add(center);
    }
    
    /** rotation on xy-plane */
    public IVecR rot2(IDoubleI angle){ return rot(angle); }
    /** rotation on xy-plane */
    public IVecR rot2(double angle){ return rot(angle); }
    /** rotation on xy-plane */
    public IVecR rot2(IVecI center, IDoubleI angle){
	if(center==this){ return this; } return sub(center).rot(angle).add(center);
    }
    /** rotation on xy-plane */
    public IVecR rot2(IVecI center, double angle){ return rot2(center,new IDouble(angle)); }
    /** rotation on xy-plane */
    public IVecR rot2(double centerX, double centerY, double angle){
	return rot2(new IVec(centerX,centerY),new IDouble(angle));
    }
    /** rotation on xy-plane to destination direction vector */
    public IVecR rot2(IVecI destDir){
	return rot(destDir.cross(IVec.zaxis).angle(cross(IVec.zaxis)));
    }
    /** rotation on xy-plane to destination point location */
    public IVecR rot2(IVecI center, IVecI destPt){
	if(center==this){ return this; } return sub(center).rot2(destPt.diff(center)).add(center);
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
    public IVecR scale(double centerX, double centerY, double centerZ, double f){
	return scale(new IVec(centerX,centerY,centerZ),f);
    }
    
    /** scale only in 1 direction */
    public IVecR scale1d(IVecI axis, double f){ return scale1d(axis,new IDouble(f)); }
    public IVecR scale1d(double axisX, double axisY, double axisZ, double f){
	return scale1d(new IVec(axisX,axisY,axisZ),new IDouble(f));
    }
    public IVecR scale1d(IVecI axis, IDoubleI f){
	op = new Scale1d(op, axis, f); return this;
    }
    public IVecR scale1d(IVecI center, IVecI axis, double f){
	return scale1d(center,axis,new IDouble(f));
    }
    public IVecR scale1d(double centerX, double centerY, double centerZ,
			 double axisX, double axisY, double axisZ, double f){
	return scale1d(new IVec(centerX, centerY, centerZ),
		       new IVec(axisX,axisY,axisZ),new IDouble(f));
    }
    public IVecR scale1d(IVecI center, IVecI axis, IDoubleI f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    
    
    /**
       reflect (mirror) 3 dimensionally to the other side of the plane
    */
    public IVecR ref(IVecI planeDir){ op=new Ref(op,planeDir); return this; }
    public IVecR ref(double planeX, double planeY, double planeZ){
	op=new Ref(op,new IVec(planeX,planeY,planeZ)); return this;
    }
    public IVecR ref(IVecI center, IVecI planeDir){
	if(center==this) return this;
	return sub(center).ref(planeDir).add(center);
    }
    public IVecR ref(double centerX, double centerY, double centerZ,
		     double planeX, double planeY, double planeZ){
	return ref(new IVec(centerX,centerY,centerZ),new IVec(planeX,planeY,planeZ));
    }
    public IVecR mirror(IVecI planeDir){ return ref(planeDir); }
    public IVecR mirror(double planeX, double planeY, double planeZ){
	return ref(planeX,planeY,planeZ);
    }
    public IVecR mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    public IVecR mirror(double centerX, double centerY, double centerZ,
			double planeX, double planeY, double planeZ){
	return ref(centerX,centerY,centerZ,planeX,planeY,planeZ);
    }
    
    
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
    public IVecR dif(IVecI v){ return dup().sub(v); }
    public IVecR dif(double vx, double vy, double vz){ return dup().sub(new IVec(vx,vy,vz)); }
    public IVecR diff(IVecI v){ return dif(v); }
    public IVecR diff(double vx, double vy, double vz){ return dif(vx,vy,vz); }
    public IVecR mid(IVecI v){ return dup().add(v).div(2); }
    public IVecR mid(double vx, double vy, double vz){ return dup().add(new IVec(vx,vy,vz)).div(2); }
    public IVecR sum(IVecI v){ return dup().add(v); }
    public IVecR sum(double vx, double vy, double vz){ return dup().add(new IVec(vx,vy,vz)); }
    public IVecR sum(IVecI... v){
	IVecR ret = this.dup();
	for(IVecI vi: v) ret.add(vi);
	return ret;
    }
    
    public IVecR bisect(IVecI v){ return dup().unit().add(v.dup().unit()); }
    public IVecR bisect(double vx, double vy, double vz){
	return dup().unit().add(new IVecR(vx,vy,vz).unit());
    }
    
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
       alias of cross. (not unitized ...)
    */
    public IVecR nml(IVecI v){ return cross(v); }
    /**
       alias of cross. (not unitized ...)
    */
    public IVecR nml(double vx, double vy, double vz){ return cross(vx,vy,vz); }
    /**
       create normal vector from 3 points of self, pt1 and pt2, not unitized
    */
    public IVecR nml(IVecI pt1, IVecI pt2){
	//return this.dif(pt1).cross(this.dif(pt2)).unit();
	return this.dif(pt1).cross(this.dif(pt2));
    }
    /**
       create normal vector from 3 points of self, pt1 and pt2, not unitized
    */
    public IVecR nml(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2){
	return this.dif(vx1,vy1,vz1).cross(this.dif(vx2,vy2,vz2));
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
    
    static public class FromX extends IParameterObject implements IVecOp{
	public IDoubleOp x;
	public IVecOp v;
	public FromX(IVecOp v, IDoubleOp x){ this.v=v; this.x=x; }
	public IVec get(){ return new IVec(x.x(),v.get().y(),v.get().z()); }
    }
    
    static public class FromY extends IParameterObject implements IVecOp{
	public IDoubleOp y;
	public IVecOp v;
	public FromY(IVecOp v, IDoubleOp y){ this.v=v; this.y=y; }
	public IVec get(){ return new IVec(v.get().x(),y.x(),v.get().z()); }
    }
    
    static public class FromZ extends IParameterObject implements IVecOp{
	public IDoubleOp z;
	public IVecOp v;
	public FromZ(IVecOp v, IDoubleOp z){ this.v=v; this.z=z; }
	public IVec get(){ return new IVec(v.get().x(),v.get().y(),z.x()); }
    }
    
    static public class ToVec2 extends IParameterObject implements IVec2Op{
	public IVecOp v;
	public ToVec2(IVecOp v){ this.v=v; }
	public IVec2 get(){ return new IVec2(v.get()); }
    }
    
    static public class ToVec2WithProjection extends IParameterObject implements IVec2Op{
	public IVecOp v, projectionDir;
	public ToVec2WithProjection(IVecOp v, IVecOp proj){ this.v=v; projectionDir=proj; }
	public IVec2 get(){ return new IVec2(v.get(),projectionDir.get()); }
    }
    
    static public class ToVec2WithAxis extends IParameterObject implements IVec2Op{
	public IVecOp v, xaxis, yaxis;
	public ToVec2WithAxis(IVecOp v, IVecOp xaxis, IVecOp yaxis){ this.v=v; this.xaxis=xaxis; this.yaxis=yaxis; }
	public IVec2 get(){ return new IVec2(v.get(),xaxis.get(),yaxis.get()); }
    }
    
    static public class ToVec2WithAxisAndOrigin extends IParameterObject implements IVec2Op{
	public IVecOp v, xaxis, yaxis, origin;
	public ToVec2WithAxisAndOrigin(IVecOp v, IVecOp xaxis, IVecOp yaxis, IVecOp orig){
	    this.v=v; this.xaxis=xaxis; this.yaxis=yaxis; origin=orig;
	}
	public IVec2 get(){ return new IVec2(v.get(),xaxis.get(),yaxis.get(),origin.get()); }
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
    
    static public class Rot2 extends IParameterObject implements IVecOp{
	public IVecOp v;
	public IDoubleOp angle;
	public Rot2(IVecOp u, IDoubleOp a){ v=u; angle=a; }
	public IVec get(){ return v.get().rot(angle.x()); }
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
	public IDoubleOp tolerance=null;
        public Eq(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public Eq(IVecOp v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
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
        public IVecOp v1,v2;
	public IDoubleOp tolerance=null;
        public EqX(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public EqX(IVecOp v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
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
        public IVecOp v1,v2;
	public IDoubleOp tolerance=null;
        public EqY(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public EqY(IVecOp v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
        public boolean x(){
	    if(tolerance==null) return v1.get().eqY(v2.get());
	    return v1.get().eqY(v2.get(),tolerance.x());
	}
        public IBool get(){
	    if(tolerance==null) return v1.get().eqY((Ir)null,v2.get());
	    return v1.get().eqY((Ir)null,v2.get(),tolerance.get());
	}
    }
    
    static public class EqZ extends IParameterObject implements IBoolOp{
        public IVecOp v1,v2;
	public IDoubleOp tolerance=null;
        public EqZ(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public EqZ(IVecOp v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
        public boolean x(){
	    if(tolerance==null) return v1.get().eqZ(v2.get());
	    return v1.get().eqZ(v2.get(),tolerance.x());
	}
        public IBool get(){
	    if(tolerance==null) return v1.get().eqZ((Ir)null,v2.get());
	    return v1.get().eqZ((Ir)null,v2.get(),tolerance.get());
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
