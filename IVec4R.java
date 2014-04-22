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
   Reference class of 4 dimensional vector to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public class IVec4R extends IParameterObject implements IVec4I, IVecI, IReferenceParameter{
    protected IVec4Op op;
    
    public IVec4R(IVec4Op v){ op=v; }
    //public IVec4R(IVecOp v){ op=new FromVec(v); }
    public IVec4R(IVecI v){ op=new FromVec(v); }
    
    public IVec4R(IVecI v, double w){ op=new FromVecAndW(op,new IDouble(w)); }
    public IVec4R(IVecI v, IDoubleI w){ op=new FromVecAndW(op,w); }
    
    public IVec4R(double x, double y, double z, double w){ op = new IVec4(x,y,z,w); }
    public IVec4R(double x, double y, double z){ op = new IVec4(x,y,z,1.0); }    
    
    public IVec4R(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w){
	op=new FromXYZW(x,y,z,w);
    }
    public IVec4R(IDoubleI x, IDoubleI y, IDoubleI z){ op = new FromXYZ(x,y,z); }
    
    
    public IVec4R(IServerI s, IVec4Op v){ super(s); op=v; }
    //public IVec4R(IServerI s, IVecOp v, ){ super(s); op=new FromVec(v); }
    public IVec4R(IServerI s, IVecI v){ super(s); op=new FromVec(v); }
    
    public IVec4R(IServerI s, IVecI v, double w){ super(s); op=new FromVecAndW(op,new IDouble(w)); }
    public IVec4R(IServerI s, IVecI v, IDoubleI w){ super(s); op=new FromVecAndW(op,w); }
    
    public IVec4R(IServerI s, double x, double y, double z, double w){ super(s); op = new IVec4(x,y,z,w); }
    public IVec4R(IServerI s, double x, double y, double z){ super(s); op = new IVec4(x,y,z,1.0); }    
    
    public IVec4R(IServerI s, IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w){
	super(s); op=new FromXYZW(x,y,z,w);
    }
    public IVec4R(IServerI s, IDoubleI x, IDoubleI y, IDoubleI z){
	super(s); op = new FromXYZ(x,y,z);
    }
    
    
    public double x(){ return op.get().x; }
    public double y(){ return op.get().y; }
    public double z(){ return op.get().z; }
    public double w(){ return op.get().w; }
        
    
    /** setting x component */
    public IVec4R x(double vx){ op = new FromX(op,new IDouble(vx)); return this; }
    /** setting y component */
    public IVec4R y(double vy){ op = new FromY(op,new IDouble(vy)); return this; }
    /** setting z component */
    public IVec4R z(double vz){ op = new FromZ(op,new IDouble(vz)); return this; }
    /** setting w component */
    public IVec4R w(double vw){ op = new FromW(op,new IDouble(vw)); return this; }
    
    /** setting x component */
    public IVec4R x(IDoubleI vx){ op = new FromX(op,vx); return this; }
    /** setting y component */
    public IVec4R y(IDoubleI vy){ op = new FromY(op,vy); return this; }
    /** setting z component */
    public IVec4R z(IDoubleI vz){ op = new FromZ(op,vz); return this; }
    /** setting w component */
    public IVec4R w(IDoubleI vw){ op = new FromW(op,vw); return this; }

    /** setting x component by x component of input vector*/
    public IVec4R x(IVecI v){ op = new FromX(op, v.x((ISwitchR)null)); return this; }
    /** setting y component by y component of input vector*/
    public IVec4R y(IVecI v){ op = new FromY(op, v.y((ISwitchR)null)); return this; }
    /** setting z component by z component of input vector*/
    public IVec4R z(IVecI v){ op = new FromZ(op, v.z((ISwitchR)null)); return this; }
    /** setting w component by w component of input vector*/
    public IVec4R w(IVec4I v){ op = new FromW(op, v.w((ISwitchR)null)); return this; }
    
    /** setting x component by x component of input vector*/
    public IVec4R x(IVec2I v){ op = new FromX(op, v.x((ISwitchR)null)); return this; }
    /** setting y component by y component of input vector*/
    public IVec4R y(IVec2I v){ op = new FromY(op, v.y((ISwitchR)null)); return this; }
    
    
    /** getting x component */
    public double x(ISwitchE e){ return x(); }
    /** getting y component */
    public double y(ISwitchE e){ return y(); }
    /** getting z component */
    public double z(ISwitchE e){ return z(); }
    /** getting w component */
    public double w(ISwitchE e){ return w(); }
    
    /** getting x component */
    public IDoubleR x(ISwitchR r){ return new IDoubleR(new X(op)); }
    /** getting y component */
    public IDoubleR y(ISwitchR r){ return new IDoubleR(new Y(op)); }
    /** getting z component */
    public IDoubleR z(ISwitchR r){ return new IDoubleR(new Z(op)); }
    /** getting w component */
    public IDoubleR w(ISwitchR r){ return new IDoubleR(new W(op)); }
    
    
    public IVec4 get(){ return op.get(); }
    
    public IVec4R dup(){ return new IVec4R(op); }
    
    
    public IVec2R to2d(){ return new IVec2R(new ToVec2(op)); }
    public IVec2R to2d(IVecI projectionDir){
        return new IVec2R(new ToVec2WithProjection(this,projectionDir));
    }
    public IVec2R to2d(IVecI xaxis, IVecI yaxis){
        return new IVec2R(new ToVec2WithAxis(this,xaxis,yaxis));
    }
    public IVec2R to2d(IVecI xaxis, IVecI yaxis, IVecI origin){
        return new IVec2R(new ToVec2WithAxisAndOrigin(this,xaxis,yaxis,origin));
    }
    
    public IVecR to3d(){ return new IVecR(new ToVec(op)); }
    public IVec4R to4d(){ return dup(); }
    public IVec4R to4d(double w){ return new IVec4R(this,w); }
    public IVec4R to4d(IDoubleI w){ return new IVec4R(this,w); }
    
    
    public IDoubleR getX(){ return new IDoubleR(new X(op)); }
    public IDoubleR getY(){ return new IDoubleR(new Y(op)); }
    public IDoubleR getZ(){ return new IDoubleR(new Z(op)); }
    public IDoubleR getW(){ return new IDoubleR(new W(op)); }
    
    public IVec4Op operator(){ return op; } // for viewer
    
    
    public IVec4R set(double x, double y, double z, double w){ op=new IVec4(x,y,z,w); return this; }
    public IVec4R set(double x, double y, double z){ op=new IVec4(x,y,z,1.0); return this; }
    
    public IVec4R set(IVec4I u){ op=u; return this; }
    public IVec4R set(IVecI u){ op=new FromVec(u); return this; }
    
    
    public IVec4R set(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w){
	op=new FromXYZW(x,y,z,w); return this;
    }
    public IVec4R set(IDoubleI x, IDoubleI y, IDoubleI z){
	op = new FromXYZ(x,y,z); return this;
    }
    public IVec4R set(IVecI v, double w){
	op = new FromVecAndW(v,new IDouble(w)); return this;
    }
    public IVec4R set(IVecI v, IDoubleI w){ op = new FromVecAndW(v,w); return this; }
    
    
    
    public IVec4R add(double x, double y, double z){
	op=new Add(op,new IVec(x,y,z)); return this;
    }
    public IVec4R add(IDoubleI x, IDoubleI y, IDoubleI z){
	op=new Add(op,new IVecR(x,y,z)); return this;
    }
    public IVec4R add(IVecI u){ op=new Add(op,u); return this; }
    
    public IVec4R sub(double x, double y, double z){
	op=new Sub(op,new IVec(x,y,z)); return this;
    }
    public IVec4R sub(IDoubleI x, IDoubleI y, IDoubleI z){
	op=new Sub(op,new IVecR(x,y,z)); return this;
    }
    public IVec4R sub(IVecI u){ op=new Sub(op,u); return this; }
    
    public IVec4R mul(IDoubleI u){ op=new Mul(op,u); return this; }
    public IVec4R mul(double u){ op=new Mul(op,new IDouble(u)); return this; }
    public IVec4R div(IDoubleI u){ op=new Div(op,u); return this; }
    public IVec4R div(double u){ op=new Div(op,new IDouble(u)); return this; }
    public IVec4R neg(){ op=new Neg(op); return this; }
    /** alias of neg() */
    public IVec4R rev(){ return neg(); }
    /** alias of neg() */
    public IVec4R flip(){ return neg(); }
    /** setting x,y,z zero (not w)  */
    public IVec4R zero(){ return set(0,0,0); }

    /** scale add */
    public IVec4R add(IVecI v, double f){ return add(v.dup().mul(f)); }
    /** scale add */
    public IVec4R add(IVecI v, IDoubleI f){ return add(v.dup().mul(f)); }
    
    /** scale add alias */
    public IVec4R add(double f, IVecI v){ return add(v,f); }
    /** scale add alias */
    public IVec4R add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public double dot(IVecI u){ return get().dot(u); }
    public double dot(double ux, double uy, double uz){ return get().dot(ux,uy,uz); }
    public double dot(ISwitchE e, IVecI u){ return dot(u); }
    public IDoubleR dot(ISwitchR r, IVecI u){ return new IDoubleR(new IVecR.Dot(op,u)); }
    
    /** cross now returns a new instance 2011/08/03
     */
    //public IVec4R cross(IVecI u){ op=new Cross(op,u); return this; }
    public IVec4R cross(IVecI u){ return new IVec4R(new Cross(op,u)); }
    public IVec4R cross(double ux, double uy, double uz){ return new IVec4R(new Cross(op,new IVec(ux,uy,uz))); }
    
    public double len(){ return get().len(); }
    public double len(ISwitchE e){ return len(); }
    public IDoubleR len(ISwitchR r){ return new IDoubleR(new IVecR.Len(op)); }
    
    public double len2(){ return get().len2(); }
    public double len2(ISwitchE e){ return len2(); }
    public IDoubleR len2(ISwitchR r){ return new IDoubleR(new IVecR.Len2(op)); }
    
    public IVec4R len(IDoubleI l){ op=new SetLen(op,l); return this; }
    public IVec4R len(double l){ op=new SetLen(op,new IDouble(l)); return this; }
    
    public IVec4R unit(){ op=new Unit(op); return this; }
    
    
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
    
    public boolean eqX(IVecI v){ return get().eqX(v); }
    public boolean eqY(IVecI v){ return get().eqY(v); }
    public boolean eqZ(IVecI v){ return get().eqZ(v); }
    public boolean eqW(IVec4I v){ return get().eqW(v); }
    public boolean eqX(double vx){ return get().eqX(vx); }
    public boolean eqY(double vy){ return get().eqY(vy); }
    public boolean eqZ(double vz){ return get().eqZ(vz); }
    public boolean eqW(double vw){ return get().eqW(vw); }
    public boolean eqX(ISwitchE e, IVecI v){ return eqX(v); }
    public boolean eqY(ISwitchE e, IVecI v){ return eqY(v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return eqZ(v); }
    public boolean eqW(ISwitchE e, IVec4I v){ return eqW(v); }
    public IBoolR eqX(ISwitchR r, IVecI v){ return new IBoolR(new EqX(op, v)); }
    public IBoolR eqY(ISwitchR r, IVecI v){ return new IBoolR(new EqY(op, v)); }
    public IBoolR eqZ(ISwitchR r, IVecI v){ return new IBoolR(new EqZ(op, v)); }
    public IBoolR eqW(ISwitchR r, IVec4I v){ return new IBoolR(new EqW(op, v)); }
    
    public boolean eq(IVecI v, double tolerance){ return get().eq(v,tolerance); }
    public boolean eq(double vx, double vy, double vz, double tolerance){
	return get().eq(vx,vy,vz,tolerance);
    }
    public boolean eq(ISwitchE e, IVecI v, double tolerance){ return eq(v,tolerance); }
    public IBoolR eq(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBoolR(new Eq(op, v, tolerance)); }
    
    public boolean eqX(IVecI v, double tolerance){ return get().eqX(v,tolerance); }
    public boolean eqY(IVecI v, double tolerance){ return get().eqY(v,tolerance); }
    public boolean eqZ(IVecI v, double tolerance){ return get().eqZ(v,tolerance); }
    public boolean eqW(IVec4I v, double tolerance){ return get().eqW(v,tolerance); }
    public boolean eqX(double vx, double tolerance){ return get().eqX(vx,tolerance); }
    public boolean eqY(double vy, double tolerance){ return get().eqY(vy,tolerance); }
    public boolean eqZ(double vz, double tolerance){ return get().eqZ(vz,tolerance); }
    public boolean eqW(double vw, double tolerance){ return get().eqW(vw,tolerance); }
    public boolean eqX(ISwitchE e, IVecI v, double tolerance){ return eqX(v,tolerance); }
    public boolean eqY(ISwitchE e, IVecI v, double tolerance){ return eqY(v,tolerance); }
    public boolean eqZ(ISwitchE e, IVecI v, double tolerance){ return eqZ(v,tolerance); }
    public boolean eqW(ISwitchE e, IVec4I v, double tolerance){ return eqW(v,tolerance); }
    public IBoolR eqX(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBoolR(new EqX(op,v,tolerance)); }
    public IBoolR eqY(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBoolR(new EqY(op,v,tolerance)); }
    public IBoolR eqZ(ISwitchR r, IVecI v, IDoubleI tolerance){ return new IBoolR(new EqZ(op,v,tolerance)); }
    public IBoolR eqW(ISwitchR r, IVec4I v, IDoubleI tolerance){ return new IBoolR(new EqW(op,v,tolerance)); }
    
    public double angle(IVecI u){ return get().angle(u); }
    public double angle(double ux, double uy, double uz){ return get().angle(ux,uy,uz); }
    public double angle(ISwitchE e, IVecI u){ return angle(u); }
    public IDoubleR angle(ISwitchR r, IVecI u){ return new IDoubleR(new IVecR.Angle(op,u)); }
    
    public double angle(IVecI u, IVecI axis){ return get().angle(u,axis); }
    public double angle(double ux, double uy, double uz, double axisX, double axisY, double axisZ){
	return get().angle(ux,uy,uz,axisX,axisY,axisZ);
    }
    public double angle(ISwitchE e, IVecI u, IVecI axis){ return angle(u,axis); }
    public IDoubleR angle(ISwitchR r, IVecI u, IVecI axis){ return new IDoubleR(new IVecR.Angle(op,u,axis)); }
    
    public IVec4R rot(IDoubleI angle){ op=new Rot2(op,angle); return this; }
    public IVec4R rot(double angle){ op=new Rot2(op,new IDouble(angle)); return this; }
    
    public IVec4R rot(IVecI axis, IDoubleI angle){
	op=new Rot(op,axis,angle); return this; 
    }
    public IVec4R rot(IVecI axis, double angle){
	op=new Rot(op,axis,new IDouble(angle)); return this;
    }
    public IVec4R rot(double axisX, double axisY, double axisZ, double angle){
	op=new Rot(op,new IVec(axisX,axisY,axisZ),new IDouble(angle)); return this;
    }
    
    public IVec4R rot(IVecI center, IVecI axis, IDoubleI angle){
	if(center==this) return this;
	return sub(center).rot(axis,angle).add(center);
    }
    public IVec4R rot(IVecI center, IVecI axis, double angle){
	return rot(center,axis,new IDouble(angle));
    }
    public IVec4R rot(double centerX, double centerY, double centerZ,
		      double axisX, double axisY, double axisZ, double angle){
	return rot(new IVec(centerX,centerY,centerZ),new IVec(axisX,axisY,axisZ),new IDouble(angle));
    }
    
    public IVec4R rot(IVecI axis, IVecI destDir){
        return rot(axis, destDir.cross(axis).angle(cross(axis)));
    }
    
    public IVec4R rot(IVecI center, IVecI axis, IVecI destPt){
	if(center==this) return this;
	return sub(center).rot(axis,destPt.diff(center)).add(center);
    }
    
    /** alias of rot(IDoubleI) */
    public IVec4R rot2(IDoubleI angle){ return rot(angle); }
    /** alias of rot(double) */
    public IVec4R rot2(double angle){ return rot(angle); }
    
    public IVec4R rot2(IVecI center, IDoubleI angle){
	if(center==this){ return this; } return sub(center).rot(angle).add(center);
    }
    public IVec4R rot2(IVecI center, double angle){ return rot2(center,new IDouble(angle)); }
    public IVec4R rot2(double centerX, double centerY, double angle){
	return rot2(new IVec(centerX,centerY), new IDouble(angle));
    }
    
    public IVec4R rot2(IVecI destDir){ return rot(destDir.cross(IVec.zaxis).angle(cross(IVec.zaxis))); }
    public IVec4R rot2(IVecI center, IVecI destPt){
	if(center==this){ return this; } return sub(center).rot2(destPt.diff(center)).add(center);
    }
    
    
    /** alias of mul */
    public IVec4R scale(IDoubleI f){ return mul(f); }
    public IVec4R scale(double f){ return mul(f); }
    
    public IVec4R scale(IVecI center, IDoubleI f){
	if(center==this) return this;
	return sub(center).mul(f).add(center);
    }
    public IVec4R scale(IVecI center, double f){ return scale(center,new IDouble(f)); }
    public IVec4R scale(double centerX, double centerY, double centerZ, double f){
	return scale(new IVec(centerX,centerY,centerZ),new IDouble(f));
    }
    
    /** scale only in 1 direction */
    public IVec4R scale1d(IVecI axis, double f){ return scale1d(axis,new IDouble(f)); }
    public IVec4R scale1d(double axisX, double axisY, double axisZ, double f){
	return scale1d(new IVec(axisX,axisY,axisZ),new IDouble(f));
    }
    public IVec4R scale1d(IVecI axis, IDoubleI f){
	op = new Scale1d(op, axis, f); return this;
    }
    public IVec4R scale1d(IVecI center, IVecI axis, double f){
	return scale1d(center,axis,new IDouble(f));
    }
    public IVec4R scale1d(double centerX, double centerY, double centerZ,
			  double axisX, double axisY, double axisZ, double f){
	return scale1d(new IVec(centerX,centerY,centerZ),new IVec(axisX,axisY,axisZ),new IDouble(f));
    }
    public IVec4R scale1d(IVecI center, IVecI axis, IDoubleI f){
	if(center==this) return this;
	return sub(center).scale1d(axis,f).add(center);
    }
    
    
    public IVec4R ref(IVecI planeDir){ op=new Ref(op,planeDir); return this; }
    public IVec4R ref(double planeX, double planeY, double planeZ){
	op=new Ref(op,new IVec(planeX,planeY,planeZ)); return this;
    }
    public IVec4R ref(IVecI center, IVecI planeDir){
        if(center==this) return this;
        return sub(center).ref(planeDir).add(center);
    }
    public IVec4R ref(double centerX, double centerY, double centerZ,
		      double planeX, double planeY, double planeZ){
        return ref(new IVec(centerX,centerY,centerZ),new IVec(planeX,planeY,planeZ));
    }
    public IVec4R mirror(IVecI planeDir){ return ref(planeDir); }
    public IVec4R mirror(double planeX, double planeY, double planeZ){
	return ref(planeX,planeY,planeZ);
    }
    public IVec4R mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    public IVec4R mirror(double centerX, double centerY, double centerZ,
			 double planeX, double planeY, double planeZ){
	return ref(centerX,centerY,centerZ,planeX,planeY,planeZ);
    }
    
    
    /** shear operation */
    public IVec4R shear(double sxy, double syx, double syz, double szy, double szx, double sxz){
	return shear(new IDouble(sxy),new IDouble(syx),new IDouble(syz),
		     new IDouble(szy),new IDouble(szx),new IDouble(sxz));
    }
    public IVec4R shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	op = new Shear(op,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVec4R shear(IVecI center, double sxy, double syx, double syz, double szy, double szx, double sxz){
	if(center==this) return this;
	return sub(center).shear(sxy,syx,syz,szy,szx,sxz).add(center);
    }
    public IVec4R shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz, IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	if(center==this) return this;
	return sub(center).shear(sxy,syx,syz,szy,szx,sxz).add(center);
    }
    
    public IVec4R shearXY(double sxy, double syx){ return shear(sxy,syx,0,0,0,0); }
    public IVec4R shearXY(IDoubleI sxy, IDoubleI syx){ return shear(sxy,syx,null,null,null,null); }
    public IVec4R shearXY(IVecI center, double sxy, double syx){
	if(center==this){ return this; } return sub(center).shearXY(sxy,syx).add(center);
    }
    public IVec4R shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	if(center==this){ return this; } return sub(center).shearXY(sxy,syx).add(center);
    }
    
    public IVec4R shearYZ(double syz, double szy){ return shear(0,0,syz,szy,0,0); }
    public IVec4R shearYZ(IDoubleI syz, IDoubleI szy){ return shear(null,null,syz,szy,null,null); }
    public IVec4R shearYZ(IVecI center, double syz, double szy){
	if(center==this){ return this; } return sub(center).shearYZ(syz,szy).add(center);
    }
    public IVec4R shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	if(center==this){ return this; } return sub(center).shearYZ(syz,szy).add(center);
    }
    
    public IVec4R shearZX(double szx, double sxz){ return shear(0,0,0,0,szx,sxz); }
    public IVec4R shearZX(IDoubleI szx, IDoubleI sxz){ return shear(null,null,null,null,szx,sxz); }
    public IVec4R shearZX(IVecI center, double szx, double sxz){
	if(center==this){ return this; } return sub(center).shearZX(szx,sxz).add(center);
    }
    public IVec4R shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	if(center==this){ return this; } return sub(center).shearZX(szx,sxz).add(center);
    }
    
        
    /** translate is alias of add() */
    public IVec4R translate(double x, double y, double z){ return add(x,y,z); }
    public IVec4R translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IVec4R translate(IVecI v){ return add(v); }
    
    
    public IVec4R transform(IMatrix3I mat){ op = new Transform3(op,mat); return this; }
    public IVec4R transform(IMatrix4I mat){ op = new Transform4(op,mat); return this; }
    public IVec4R transform(IVecI xvec, IVecI yvec, IVecI zvec){
        op = new TransformVec3(op,xvec,yvec,zvec); return this;
    }
    public IVec4R transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
        op = new TransformVec4(op,xvec,yvec,zvec,translate); return this;
    }    
    
    
    /** mv() is alias of add() */
    public IVec4R mv(double x, double y, double z){ return add(x,y,z); }
    public IVec4R mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IVec4R mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IVec4R cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IVec4R cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IVec4R cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IVec4R cp(IVecI v){ return dup().add(v); }
    
    
    
    // methods creating new instance
    public IVec4R dif(IVecI v){ return dup().sub(v); }
    public IVec4R dif(double vx, double vy, double vz){ return dup().sub(new IVec(vx,vy,vz)); }
    public IVec4R diff(IVecI v){ return dif(v); }
    public IVec4R diff(double vx, double vy, double vz){ return dif(vx,vy,vz); }
    public IVec4R mid(IVecI v){ return dup().add(v).div(2); }
    public IVec4R mid(double vx, double vy, double vz){ return dup().add(new IVec(vx,vy,vz)).div(2); }
    public IVec4R sum(IVecI v){ return dup().add(v); }
    public IVec4R sum(double vx, double vy, double vz){ return dup().add(new IVec(vx,vy,vz)); }
    public IVec4R sum(IVecI... v){
	IVec4R ret = this.dup();
	for(IVecI vi: v) ret.add(vi);
	return ret;
    }
    
    public IVec4R bisect(IVecI v){ return dup().unit().add(v.dup().unit()); }
    public IVec4R bisect(double vx, double vy, double vz){
	return dup().unit().add(new IVecR(vx,vy,vz).unit());
    }
    
    
    /**
       weighted sum, creating a new instance
    */
    public IVec4R sum(IVecI v2, IDoubleI w1, IDoubleI w2){
        return dup().mul(w1).add(v2,w2);
    }
    public IVec4R sum(IVecI v2, IDoubleI w2){
        return dup().mul(new IDouble(1.0).sub(w2)).add(v2,w2);
    }
    
    public IVec4R sum(IVecI v2, double w1, double w2){
        return sum(v2,new IDouble(w1),new IDouble(w2));
    }
    public IVec4R sum(IVecI v2, double w2){ return sum(v2,new IDouble(w2)); }    
    

    /** alias of cross. (not unitized ... ) */
    public IVec4R nml(IVecI v){ return cross(v); }
    /** alias of cross. (not unitized ... ) */
    public IVec4R nml(double vx, double vy, double vz){ return cross(new IVec(vx,vy,vz)); }
    /**
       create normal vector from 3 points of self, pt1 and pt2, not unitized
    */
    public IVec4R nml(IVecI pt1, IVecI pt2){
	//return this.diff(pt1).cross(this.diff(pt2)).unit();
	return this.diff(pt1).cross(this.diff(pt2));
    }
    /**
       create normal vector from 3 points of self, pt1 and pt2, not unitized
    */
    public IVec4R nml(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2){
	return this.diff(vx1,vy1,vz1).cross(this.diff(vx2,vy2,vz2));
    }
    
    /** checking x, y, and z is valid number (not Infinite, nor NaN). */
    public boolean isValid(){ return get().isValid(); }
    
    
    
    static public class FromVec extends IParameterObject implements IVec4Op{
	public IVecOp v;
	public FromVec(IVecOp v){ this.v=v; }
	public IVec4 get(){ return new IVec4(v.get()); }
    }
    static public class FromVecAndW extends IParameterObject implements IVec4Op{
	public IVecOp v;
	public IDoubleOp w;
	public FromVecAndW(IVecOp v, IDoubleOp w){ this.v=v; this.w=w; }
	public IVec4 get(){ return new IVec4(v.get(),w.x()); }
    }
    static public class FromX extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IDoubleOp x;
	public FromX(IVec4Op v, IDoubleOp x){ this.v=v; this.x=x; }
	public IVec4 get(){
	    IVec4 vec = v.get();
	    return new IVec4(x.x(),vec.y(),vec.z(),vec.w());
	}
    }
    static public class FromY extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IDoubleOp y;
	public FromY(IVec4Op v, IDoubleOp y){ this.v=v; this.y=y; }
	public IVec4 get(){
	    IVec4 vec = v.get();
	    return new IVec4(vec.x(),y.x(),vec.z(),vec.w());
	}
    }
    static public class FromZ extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IDoubleOp z;
	public FromZ(IVec4Op v, IDoubleOp z){ this.v=v; this.z=z; }
	public IVec4 get(){
	    IVec4 vec = v.get();
	    return new IVec4(vec.x(),vec.y(),z.x(),vec.w());
	}
    }
    static public class FromW extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IDoubleOp w;
	public FromW(IVec4Op v, IDoubleOp w){ this.v=v; this.w=w; }
	public IVec4 get(){
	    IVec4 vec = v.get();
	    return new IVec4(vec.x(),vec.y(),vec.z(),w.x());
	}
    }
    static public class FromXYZ extends IParameterObject implements IVec4Op{
	public IDoubleOp x,y,z;
	public FromXYZ(IDoubleOp x, IDoubleOp y, IDoubleOp z){ this.x=x; this.y=y; this.z=z; }
	public IVec4 get(){ return new IVec4(x.x(),y.x(),z.x(),1.0); }
    }
    static public class FromXYZW extends IParameterObject implements IVec4Op{
	public IDoubleOp x,y,z,w;
	public FromXYZW(IDoubleOp x, IDoubleOp y, IDoubleOp z, IDoubleOp w){
	    this.x=x; this.y=y; this.z=z; this.w=w;
	}
	public IVec4 get(){ return new IVec4(x.x(),y.x(),z.x(),w.x()); }
    }
    static public class ToVec extends IParameterObject implements IVecOp{
	public IVec4Op v;
	public ToVec(IVec4Op v){ this.v=v; }
	public IVec get(){ return new IVec(v.get()); }
    }
    static public class ToVec2 extends IParameterObject implements IVec2Op{
	public IVec4Op v;
	public ToVec2(IVec4Op v){ this.v=v; }
	public IVec2 get(){ return new IVec2(v.get()); }
    }
    static public class ToVec2WithProjection extends IParameterObject implements IVec2Op{
        public IVec4Op v;
	public IVecOp projectionDir;
        public ToVec2WithProjection(IVec4Op v, IVecOp proj){ this.v=v; projectionDir=proj; }
        public IVec2 get(){ return new IVec2(v.get(),projectionDir.get()); }
    }

    static public class ToVec2WithAxis extends IParameterObject implements IVec2Op{
        public IVec4Op v;
	public IVecOp xaxis, yaxis;
        public ToVec2WithAxis(IVec4Op v, IVecOp xaxis, IVecOp yaxis){ this.v=v; this.xaxis=xaxis; this.yaxis=yaxis; }
        public IVec2 get(){ return new IVec2(v.get(),xaxis.get(),yaxis.get()); }
    }

    static public class ToVec2WithAxisAndOrigin extends IParameterObject implements IVec2Op{
        public IVec4Op v;
	public IVecOp xaxis, yaxis, origin;
        public ToVec2WithAxisAndOrigin(IVec4Op v, IVecOp xaxis, IVecOp yaxis, IVecOp orig){
            this.v=v; this.xaxis=xaxis; this.yaxis=yaxis; origin=orig;
        }
        public IVec2 get(){ return new IVec2(v.get(),xaxis.get(),yaxis.get(),origin.get()); }
    }
    
    static public class Add extends IParameterObject implements IVec4Op{
	public IVec4Op v1;
	public IVecOp v2;
	public Add(IVec4Op v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public IVec4 get(){ return (IVec4)v1.get().add(v2.get()); }
    }
    
    static public class Sub extends IParameterObject implements IVec4Op{
	public IVec4Op v1;
	public IVecOp v2;
	public Sub(IVec4Op v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public IVec4 get(){ return (IVec4)v1.get().sub(v2.get()); }
    }
    
    static public class Mul extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IDoubleOp d;
	public Mul(IVec4Op v, IDoubleOp d){ this.v=v; this.d=d; }
	public IVec4 get(){ return (IVec4)v.get().mul(d.x()); }
    }
    
    static public class Div extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IDoubleOp d;
	public Div(IVec4Op v, IDoubleOp d){ this.v=v; this.d=d; }
	public IVec4 get(){ return (IVec4)v.get().div(d.x()); }
    }
    
    static public class Neg extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public Neg(IVec4Op v){ this.v=v; }
	public IVec4 get(){ return (IVec4)v.get().neg(); }
    }
    
    
    /*
    static public class Dot extends IParameterObject implements IDoubleOp{
	IVecOp v1, v2;
	Dot(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().dotD(v2.get()); }
	public IDouble get(){ return v1.get().dot(v2.get()); }
    }
    */
    
    static public class Cross extends IParameterObject implements IVec4Op{
	public IVec4Op v1;
	public IVecOp v2;
	public Cross(IVec4Op v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public IVec4 get(){ return (IVec4)v1.get().cross(v2.get()); }
    }
    
    /*
    static public class Angle implements IDoubleOp{
	IVecOp v1,v2;
	Angle(IVecOp v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().angleD(v2.get()); }
	public IDouble get(){ return v1.get().angle(v2.get()); }
    }
    
    static public class AngleWithAxis implements IDoubleOp{
	IVecOp v1,v2,axis;
	AngleWithAxis(IVecOp v1, IVecOp v2,IVecOp ax){ this.v1=v1; this.v2=v2; axis=ax; }
	public double x(){ return v1.get().angleD(v2.get(),axis.get()); }
	public IDouble get(){ return v1.get().angle(v2.get(),axis.get()); }
    }
    */
    
    static public class Rot extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IVecOp axis;
	public IDoubleOp angle;
	public Rot(IVec4Op u, IVecOp ax, IDoubleOp a){ v=u; axis=ax; angle=a; }
	public IVec4 get(){ return (IVec4)v.get().rot(axis.get(),angle.x()); }
    }
    
    static public class Rot2 extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IDoubleOp angle;
	public Rot2(IVec4Op u, IDoubleOp a){ v=u; angle=a; }
	public IVec4 get(){ return (IVec4)v.get().rot(angle.x()); }
    }
    
    /*
    static public class Len implements IDoubleOp{
	IVecOp v;
	Len(IVecOp v){ this.v=v; }
	public double x(){ return v.get().lenD(); }
	public IDouble get(){ return v.get().len(); }
    }
    
    static public class Len2 implements IDoubleOp{
	IVecOp v;
	Len2(IVecOp v){ this.v=v; }
	public double x(){ return v.get().len2D(); }
	public IDouble get(){ return v.get().len2(); }
    }
    */
    
    static public class X extends IParameterObject implements IDoubleOp{
	public IVec4Op v;
	public X(IVec4Op v){ this.v=v; }
	public double x(){ return v.get().x; }
	public IDouble get(){ return v.get().getX(); }
    }
    static public class Y extends IParameterObject implements IDoubleOp{
	public IVec4Op v;
	public Y(IVec4Op v){ this.v=v; }
	public double x(){ return v.get().y; }
	public IDouble get(){ return v.get().getY(); }
    }
    static public class Z extends IParameterObject implements IDoubleOp{
	public IVec4Op v;
	public Z(IVec4Op v){ this.v=v; }
	public double x(){ return v.get().z; }
	public IDouble get(){ return v.get().getZ(); }
    }
    static public class W extends IParameterObject implements IDoubleOp{
	public IVec4Op v;
	public W(IVec4Op v){ this.v=v; }
	public double x(){ return v.get().w; }
	public IDouble get(){ return v.get().getW(); }
    }
    
    static public class Unit extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public Unit(IVec4Op v){ this.v=v; }
	public IVec4 get(){ return (IVec4)v.get().unit(); }
    }
    
    static public class SetLen extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IDoubleOp l;
	public SetLen(IVec4Op v, IDoubleOp l){ this.v=v; this.l=l; }
	public IVec4 get(){ return (IVec4)v.get().len(l.x()); }
    }
    
    static public class Dist extends IParameterObject implements IDoubleOp{
	public IVec4Op v1;
        public IVecOp v2;
        public Dist(IVec4Op v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
        public double x(){ return v1.get().dist(v2.get()); }
        public IDouble get(){ return v1.get().dist((Ir)null,v2.get()); }
    }
    
    static public class Dist2 extends IParameterObject implements IDoubleOp{
	public IVec4Op v1;
        public IVecOp v2;
        public Dist2(IVec4Op v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
        public double x(){ return v1.get().dist2(v2.get()); }
        public IDouble get(){ return v1.get().dist2((Ir)null,v2.get()); }
    }
    
    static public class Eq extends IParameterObject implements IBoolOp{
	public IVec4Op v1;
        public IVecOp v2;
	public IDoubleOp tolerance=null;
        public Eq(IVec4Op v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public Eq(IVec4Op v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
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
	public IVec4Op v1;
        public IVecOp v2;
	public IDoubleOp tolerance=null;
        public EqX(IVec4Op v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public EqX(IVec4Op v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
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
	public IVec4Op v1;
        public IVecOp v2;
	public IDoubleOp tolerance=null;
        public EqY(IVec4Op v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public EqY(IVec4Op v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
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
	public IVec4Op v1;
        public IVecOp v2;
	public IDoubleOp tolerance=null;
        public EqZ(IVec4Op v1, IVecOp v2){ this.v1=v1; this.v2=v2; }
	public EqZ(IVec4Op v1, IVecOp v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
        public boolean x(){
	    if(tolerance==null) return v1.get().eqZ(v2.get());
	    return v1.get().eqZ(v2.get(),tolerance.x());
	}
        public IBool get(){
	    if(tolerance==null) return v1.get().eqZ((Ir)null,v2.get());
	    return v1.get().eqZ((Ir)null,v2.get(),tolerance.get());
	}
    }
    
    static public class EqW extends IParameterObject implements IBoolOp{
	public IVec4Op v1;
        public IVec4Op v2;
	public IDoubleOp tolerance=null;
        public EqW(IVec4Op v1, IVec4Op v2){ this.v1=v1; this.v2=v2; }
	public EqW(IVec4Op v1, IVec4Op v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; tolerance=reso; }
        public boolean x(){
	    if(tolerance==null) return v1.get().eqW(v2.get());
	    return v1.get().eqW(v2.get(),tolerance.x());
	}
        public IBool get(){
	    if(tolerance==null) return v1.get().eqW((Ir)null,v2.get());
	    return v1.get().eqW((Ir)null,v2.get(),tolerance.get());
	}
    }
    
    static public class Scale1d extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IVecOp axis;
	public IDoubleI factor;
	public Scale1d(IVec4Op v, IVecOp axis, IDoubleI factor){ this.v=v; this.axis=axis; this.factor=factor; }
	public IVec4 get(){ return v.get().scale1d(axis.get(),factor.get()); }
    }
    
    static public class Ref extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IVecOp plane;
	public Ref(IVec4Op v, IVecOp plane){ this.v=v; this.plane=plane; };
	public IVec4 get(){ return (IVec4)v.get().ref(plane.get()); }
    }
    
    static public class Shear extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IDoubleOp sxy, syx, syz, szy, szx, sxz;
	public Shear(IVec4Op v, IDoubleOp sxy, IDoubleOp syx, IDoubleOp syz,
		     IDoubleOp szy, IDoubleOp szx, IDoubleOp sxz){
	    this.v=v; this.sxy = sxy; this.syx = syx; this.syz = syz; this.szy = szy;
	    this.szx = szx; this.sxz = sxz;
	}
	public IVec4 get(){ return v.get().shear(sxy.get(), syx.get(),
						 syz.get(), szy.get(),
						 szx.get(), sxz.get());
	}
    }
    
    static public class Transform3 extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IMatrix3Op mat;
	public Transform3(IVec4Op v, IMatrix3Op mat){ this.v=v; this.mat=mat; };
	public IVec4 get(){ return (IVec4)v.get().transform(mat.get()); }
    }
    
    static public class Transform4 extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IMatrix4Op mat;
	public Transform4(IVec4Op v, IMatrix4Op mat){ this.v=v; this.mat=mat; };
	public IVec4 get(){ return (IVec4)v.get().transform(mat.get()); }
    }
    
    static public class TransformVec3 extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IVecOp x, y, z;
	public TransformVec3(IVec4Op v, IVecOp x, IVecOp y, IVecOp z){
	    this.v=v; this.x=x; this.y=y; this.z=z;
	}
	public IVec4 get(){ return (IVec4)v.get().transform(x.get(),y.get(),z.get()); }
    }
    
    static public class TransformVec4 extends IParameterObject implements IVec4Op{
	public IVec4Op v;
	public IVecOp x, y, z, trans;
	public TransformVec4(IVec4Op v, IVecOp x, IVecOp y, IVecOp z, IVecOp trans){
	    this.v=v; this.x=x; this.y=y; this.z=z; this.trans=trans;
	}
	public IVec4 get(){ return (IVec4)v.get().transform(x.get(),y.get(),z.get(),trans.get()); }
    }
    
    
}

