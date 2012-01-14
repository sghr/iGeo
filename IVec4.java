/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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
   Class of 4 dimensional vector.
   4 dimensional vector is mainly used for control points of NURBS geometry
   to include weights
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IVec4 extends IVec implements IVec4I, IEntityParameter{
    
    public double w;
    
    public IVec4(){}
    public IVec4(double x, double y, double z, double w){
	this.x=x; this.y=y; this.z=z; this.w=w;
    }
    public IVec4(double x, double y, double z){
	this.x=x; this.y=y; this.z=z; this.w=1.0;
    }
    public IVec4(IVec4 v){ x=v.x; y=v.y; z=v.z; w=v.w; }
    public IVec4(IVec4I v){ IVec4 u=v.get(); x=u.x; y=u.y; z=u.z; w=u.w; }
    
    public IVec4(IVec v){ x=v.x; y=v.y; z=v.z; w=1.0; }
    public IVec4(IVecI v){ IVec u=v.get(); x=u.x; y=u.y; z=u.z; w=1.0; }
    
    public IVec4(IVec v, double w){ x=v.x; y=v.y; z=v.z; this.w=w; }
    public IVec4(IVecI v, double w){ IVec u=v.get(); x=u.x; y=u.y; z=u.z; this.w=w; }
    public IVec4(IVecI v, IDoubleI w){ IVec u=v.get(); x=u.x; y=u.y; z=u.z; this.w=w.x(); }
    
    public IVec4(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w){
	this.x=x.x(); this.y=y.x(); this.z=z.x(); this.w=w.x();
    }
    public IVec4(IDoubleI x, IDoubleI y, IDoubleI z){
	this.x=x.x(); this.y=y.x(); this.z=z.x(); this.w=1.0;
    }
    
    
    public IVec4(IServerI s){ super(s); }
    public IVec4(IServerI s, double x, double y, double z, double w){
	super(s); this.x=x; this.y=y; this.z=z; this.w=w;
    }
    public IVec4(IServerI s, double x, double y, double z){
	super(s); this.x=x; this.y=y; this.z=z; this.w=1.0;
    }
    public IVec4(IServerI s, IVec4 v){ super(s); x=v.x; y=v.y; z=v.z; w=v.w; }
    public IVec4(IServerI s, IVec4I v){ super(s); IVec4 u=v.get(); x=u.x; y=u.y; z=u.z; w=u.w; }
    
    public IVec4(IServerI s, IVec v){ super(s); x=v.x; y=v.y; z=v.z; w=1.0; }
    public IVec4(IServerI s, IVecI v){ super(s); IVec u=v.get(); x=u.x; y=u.y; z=u.z; w=1.0; }
    
    public IVec4(IServerI s, IVec v, double w){ super(s); x=v.x; y=v.y; z=v.z; this.w=w; }
    public IVec4(IServerI s, IVecI v, double w){ super(s); IVec u=v.get(); x=u.x; y=u.y; z=u.z; this.w=w; }
    public IVec4(IServerI s, IVecI v, IDoubleI w){ super(s); IVec u=v.get(); x=u.x; y=u.y; z=u.z; this.w=w.x(); }
    
    public IVec4(IServerI s, IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w){
	super(s);
	this.x=x.x(); this.y=y.x(); this.z=z.x(); this.w=w.x();
    }
    public IVec4(IServerI s, IDoubleI x, IDoubleI y, IDoubleI z){
	super(s);
	this.x=x.x(); this.y=y.x(); this.z=z.x(); this.w=1.0;
    }
    
    
    public double w(){ return w; }
    //public IVec4 get(){ return this; } // why not this ??
    public IVec4 get(){ return new IVec4(x,y,z,w); } // why this ???
    public IVec4 dup(){ return new IVec4(x,y,z,w); }
    
    public IVec to3d(){ return new IVec(this); }
    
    public IVec2 to2d(){ return new IVec2(this); }
    public IVec4 to4d(){ return new IVec4(this); }
    public IVec4 to4d(double w){ return new IVec4(this,w); }
    public IVec4 to4d(IDoubleI w){ return new IVec4(this,w); }
    
    
    public IDouble getW(){ return new IDouble(w); }
    
    public IVec4 set(double x, double y, double z, double w){
	this.x=x; this.y=y; this.z=z; this.w=w; return this;
    }
    public IVec4 set(IDoubleI x, IDoubleI y, IDoubleI z, IDoubleI w){
	this.x=x.x(); this.y=y.x(); this.z=z.x(); this.w=w.x(); return this;
    }
    public IVec4 set(IVec4 v){ x=v.x; y=v.y; z=v.z; w=v.w; return this; }
    public IVec4 set(IVec v, double w){ x=v.x; y=v.y; z=v.z; this.w=w; return this; }
    
    public IVec4 set(IVec4I v){ IVec4 u=v.get(); x=u.x; y=u.y; z=u.z; w=u.w; return this; }
    public IVec4 set(IVecI v, double w){
	IVec u=v.get(); x=u.x; y=u.y; z=u.z; this.w=w; return this;
    }
    public IVec4 set(IVecI v, IDoubleI w){
	IVec u=v.get(); x=u.x; y=u.y; z=u.z; this.w=w.x(); return this;
    }
    
    public IVec4 set(IVec v){ x=v.x; y=v.y; z=v.z; return this; }
    public IVec4 set(IVecI v){ IVec u=v.get();x=u.x;y=u.y;z=u.z;return this; }
    public IVec4 set(double x, double y, double z){
	this.x=x; this.y=y; this.z=z; return this;
    }
    public IVec4 set(IDoubleI x, IDoubleI y, IDoubleI z){
	this.x=x.x(); this.y=y.x(); this.z=z.x(); return this;
    }
    
    public IVec4 add(double x, double y, double z){ super.add(x,y,z); return this; }
    public IVec4 add(IDoubleI x, IDoubleI y, IDoubleI z){ super.add(x,y,z); return this; }
    public IVec4 add(IVec v){ super.add(v); return this; }
    public IVec4 add(IVecI v){ super.add(v); return this; }
    public IVec4 sub(double x, double y, double z){ super.sub(x,y,z); return this; }
    public IVec4 sub(IDoubleI x, IDoubleI y, IDoubleI z){ super.sub(x,y,z); return this; }
    public IVec4 sub(IVec v){ super.sub(v); return this; }
    public IVec4 sub(IVecI v){ super.sub(v); return this; }
    public IVec4 mul(IDoubleI v){ super.mul(v); return this; }
    public IVec4 mul(double v){ super.mul(v); return this; }
    public IVec4 div(IDoubleI v){ super.div(v); return this; }
    public IVec4 div(double v){ super.div(v); return this; }
    /** note that it's not negating w */
    public IVec4 neg(){ super.neg(); return this; }
    /** note that it's not negating w */
    public IVec4 rev(){ neg(); return this; }
    /** note that it's not negating w */
    public IVec4 flip(){ neg(); return this; }
    /** note that w is not set zero */
    public IVec4 zero(){ super.zero(); return this; }

    /** scale add */
    public IVec4 add(IVec v, double f){ super.add(v,f); return this; }
    /** scale add */
    public IVec4 add(IVecI v, double f){ super.add(v,f); return this; }
    /** scale add */
    public IVec4 add(IVecI v, IDoubleI f){ super.add(v,f); return this; }

    /** scale add alias */
    public IVec4 add(double f, IVec v){ return add(v,f); }
    /** scale add alias */
    public IVec4 add(double f, IVecI v){ return add(v,f); }
    /** scale add alias */
    public IVec4 add(IDoubleI f, IVecI v){ return add(v,f); }
    
    
    public IVec4 len(IDoubleI l){ super.len(l); return this; }
    public IVec4 len(double l){ super.len(l); return this; }
    
    public IVec4 unit(){ super.unit(); return this; }
    
    /** cross returns a new instance
     */
    public IVec4 cross(IVec v){
        return dup().set(y*v.z - z*v.y, 
                         z*v.x - x*v.z,
                         x*v.y - y*v.x);
    }
    public IVec4 cross(IVecI v){ return cross(v.get()); }
    
    
    
    public boolean eqW(IVec4I v){ return eqW(v,IConfig.tolerance); }
    public boolean eqW(IVec4I v, double resolution){ return Math.abs(w-v.w())<=resolution; }
    public boolean eqW(IVec4 v, double resolution){ return Math.abs(w-v.w)<=resolution; }
    //public IBool eqWR(IVec4I v){ return new IBool(eqW(v,IConfig.tolerance)); }
    //public IBool eqWR(IVec4I v, IDoubleI resolution){ return new IBool(eqW(v,resolution.x())); }
    public boolean eqW(ISwitchE e, IVec4I v){ return eqW(v); }
    public boolean eqW(ISwitchE e, IVec4I v, double resolution){ return eqW(v,resolution); }
    public IBool eqW(ISwitchR r, IVec4I v){ return new IBool(eqW(v)); }
    public IBool eqW(ISwitchR r, IVec4I v, IDoubleI resolution){ return new IBool(eqW(v,resolution.x())); }
    
    public IVec4 rot(IDoubleI angle){ super.rot(angle); return this; }
    public IVec4 rot(double angle){ super.rot(angle); return this; }
    
    public IVec4 rot(IVecI axis, IDoubleI angle){ super.rot(axis,angle); return this; }
    public IVec4 rot(IVecI axis, double angle){ super.rot(axis,angle); return this; }
    public IVec4 rot(IVec axis, double angle){ super.rot(axis,angle); return this; }
    public IVec4 rot(IVecI center, IVecI axis, IDoubleI angle){
	super.rot(center,axis,angle); return this;
    }
    public IVec4 rot(IVecI center, IVecI axis, double angle){
	super.rot(center,axis,angle); return this;
    }
    public IVec4 rot(IVec center, IVec axis, double angle){
	super.rot(center,axis,angle); return this;
    }

    public IVec4 rot(IVec axis, IVec destDir){ super.rot(axis,destDir); return this; }
    public IVec4 rot(IVecI axis, IVecI destDir){ super.rot(axis,destDir); return this; }
    public IVec4 rot(IVecI center, IVecI axis, IVecI destPt){
	super.rot(center,axis,destPt); return this;
    }
    public IVec4 rot(IVec center, IVec axis, IVec destPt){
	super.rot(center,axis,destPt); return this;
    }
    

    /** alias of rot(IDoubleI) */
    public IVec4 rot2(IDoubleI angle){ return rot(angle); }
    /** alias of rot(double) */
    public IVec4 rot2(double angle){ return rot(angle); }
    
    public IVec4 rot2(IVecI center, IDoubleI angle){ super.rot2(center,angle); return this; }
    public IVec4 rot2(IVecI center, double angle){ super.rot2(center,angle); return this; }
    public IVec4 rot2(IVec center, double angle){ super.rot2(center,angle); return this; }
    
    public IVec4 rot2(IVec destDir){ super.rot2(destDir); return this; }
    public IVec4 rot2(IVecI destDir){ super.rot2(destDir); return this; }
    public IVec4 rot2(IVecI center, IVecI destPt){ super.rot2(center,destPt); return this; }
    public IVec4 rot2(IVec center, IVec destPt){ super.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    public IVec4 scale(IDoubleI f){ super.scale(f); return this; }
    public IVec4 scale(double f){ super.scale(f); return this; }

    public IVec4 scale(IVecI center, IDoubleI f){ super.scale(center,f); return this; }
    public IVec4 scale(IVecI center, double f){ super.scale(center,f); return this; }
    public IVec4 scale(IVec center, double f){ super.scale(center,f); return this; }
    
    
    /** scale only in 1 direction */
    public IVec4 scale1d(IVec axis, double f){ super.scale1d(axis,f); return this; }
    public IVec4 scale1d(IVecI axis, double f){ super.scale1d(axis,f); return this; }
    public IVec4 scale1d(IVecI axis, IDoubleI f){ super.scale1d(axis,f); return this; }
    public IVec4 scale1d(IVecI center, IVecI axis, double f){
	super.scale1d(center,axis,f); return this;
    }
    public IVec4 scale1d(IVecI center, IVecI axis, IDoubleI f){
	super.scale1d(center,axis,f); return this;
    }
    
    public IVec4 ref(IVec planeDir){ super.ref(planeDir); return this; }
    public IVec4 ref(IVecI planeDir){ super.ref(planeDir); return this; }
    public IVec4 ref(IVec center, IVec planeDir){ super.ref(center,planeDir); return this; }
    public IVec4 ref(IVecI center, IVecI planeDir){ super.ref(center,planeDir); return this; }
    
    public IVec4 mirror(IVec planeDir){ super.ref(planeDir); return this; }
    public IVec4 mirror(IVecI planeDir){ super.ref(planeDir); return this; }
    public IVec4 mirror(IVec center, IVec planeDir){ super.ref(center,planeDir); return this; }
    public IVec4 mirror(IVecI center, IVecI planeDir){ super.ref(center,planeDir); return this; }
    
    
    public IVec4 shear(double sxy, double syx, double syz, double szy, double szx, double sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVec4 shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		      IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVec4 shear(IVecI center, double sxy, double syx, double syz, double szy, double szx, double sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVec4 shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz, IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    
    public IVec4 shearXY(double sxy, double syx){ super.shearXY(sxy,syx); return this; }
    public IVec4 shearXY(IDoubleI sxy, IDoubleI syx){ super.shearXY(sxy,syx); return this; }
    public IVec4 shearXY(IVecI center, double sxy, double syx){
	super.shearXY(center,sxy,syx); return this;
    }
    public IVec4 shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	super.shearXY(center,sxy,syx); return this;
    }
    
    public IVec4 shearYZ(double syz, double szy){ super.shearYZ(syz,szy); return this; }
    public IVec4 shearYZ(IDoubleI syz, IDoubleI szy){ super.shearYZ(syz,szy); return this; }
    public IVec4 shearYZ(IVecI center, double syz, double szy){
	super.shearYZ(center,syz,szy); return this;
    }
    public IVec4 shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	super.shearYZ(center,syz,szy); return this;
    }
    
    public IVec4 shearZX(double szx, double sxz){ super.shearZX(szx,sxz); return this; }
    public IVec4 shearZX(IDoubleI szx, IDoubleI sxz){ super.shearZX(szx,sxz); return this; }
    public IVec4 shearZX(IVecI center, double szx, double sxz){
	super.shearZX(center,szx,sxz); return this;
    }
    public IVec4 shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	super.shearZX(center,szx,sxz); return this;
    }
    
    public IVec4 translate(double x, double y, double z){ return add(x,y,z); }
    public IVec4 translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IVec4 translate(IVecI v){ return add(v); }
        
    
    public IVec4 transform(IMatrix3I mat){ super.transform(mat); return this; }
    public IVec4 transform(IMatrix4I mat){ super.transform(mat); return this; }
    public IVec4 transform(IVec xvec, IVec yvec, IVec zvec){
	super.transform(xvec,yvec,zvec); return this;
    }
    public IVec4 transform(IVecI xvec, IVecI yvec, IVecI zvec){
	super.transform(xvec,yvec,zvec); return this;
    }
    public IVec4 transform(IVec xvec, IVec yvec, IVec zvec, IVec translate){
	super.transform(xvec,yvec,zvec,translate); return this;
    }
    public IVec4 transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	super.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    
    /** mv() is alias of add() */
    public IVec4 mv(double x, double y, double z){ return add(x,y,z); }
    public IVec4 mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IVec4 mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IVec4 cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IVec4 cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IVec4 cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IVec4 cp(IVecI v){ return dup().add(v); }
    
    
    // methods creating new instance
    // use these carefully. w is set to the object's w (not input or addition with input's)
    public IVec4 dif(IVec v){ return dup().sub(v); }
    public IVec4 dif(IVecI v){ return dup().sub(v); }
    public IVec4 diff(IVec v){ return dif(v); }
    public IVec4 diff(IVecI v){ return dif(v); }
    public IVec4 mid(IVec v){ return dup().add(v).div(2); }
    public IVec4 mid(IVecI v){ return dup().add(v).div(2); }
    public IVec4 sum(IVec v){ return dup().add(v); }
    public IVec4 sum(IVecI v){ return dup().add(v); }
    public IVec4 sum(IVec... v){
	IVec4 ret = this.dup();
	for(IVec vi:v) ret.add(vi);
	return ret;
    }
    public IVec4 sum(IVecI... v){
	IVec4 ret = this.dup();
	for(IVecI vi:v) ret.add(vi);
	return ret;
    }
    
    public IVec4 bisect(IVec v){ return dup().unit().add(v.dup().unit()); }
    public IVec4 bisect(IVecI v){ return bisect(v.get()); }
    
    
    public IVec4 sum(IVec v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    public IVec4 sum(IVec v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    public IVec4 sum(IVecI v2, double w1, double w2){ return sum(v2.get(),w1,w2); }
    public IVec4 sum(IVecI v2, double w2){ return sum(v2,w2); }
    public IVec4 sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return sum(v2.get(),w1.x(),w2.x()); }
    public IVec4 sum(IVecI v2, IDoubleI w2){ return sum(v2.get(),w2.x()); }
    
    /** alias of cross. (not unitized ... ?) */
    public IVec4 nml(IVecI v){ return cross(v); }

    /** create normal vector from 3 points of self, pt1 and pt2 */
    public IVec nml(IVecI pt1, IVecI pt2){
	return this.diff(pt1).cross(this.diff(pt2)).unit();
    }
    
    /** checking x, y, and z is valid number (not Infinite, nor NaN). */
    public boolean isValid(){
	if(!IDouble.isValid(x)){ IOut.err("invalid x ("+x+")"); return false; }
	if(!IDouble.isValid(y)){ IOut.err("invalid y ("+y+")"); return false; }
	if(!IDouble.isValid(z)){ IOut.err("invalid z ("+z+")"); return false; }
	if(!IDouble.isValid(w)){ IOut.err("invalid w ("+w+")"); return false; }
	return true;
    }
    
    
        
    public String toString(){
	return "("+String.valueOf(x)+","+String.valueOf(y)+","+String.valueOf(z)+
	    ","+String.valueOf(w)+")";
    }
    
    
}
