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

//import java.awt.Color;
import java.util.ArrayList;

/**
   Class of an agent based on one point.
   
   @author Satoru Sugihara
*/
public class IPointAgent extends IAgent implements IVecI{
    
    public IVec pos;
    public IPoint point;
    
    public IAgentTracker tracker;
    
    public IPointAgent(){ this(new IVec()); show(); }
    public IPointAgent(double x, double y, double z){ super(); pos=new IVec(x,y,z); show(); }
    public IPointAgent(IVec p){ super(); pos=p; show(); }
    public IPointAgent(IVecI p){ super(); pos=p.get(); show(); }
    public IPointAgent(IPointAgent pa){ super(); pos=pa.pos.dup(); show(); }
    
    // out of attached geometries
    public IPointAgent(IGeometry... geometries){
	if(geometries==null||geometries.length==0){
	    pos = new IVec();
	    show();
	}
	else{
	    IVec center = new IVec();
	    for(int i=0; i<geometries.length; i++) center.add(geometries[i].center());
	    center.div(geometries.length);
	    pos = center;
	    show();
	    attach(center, geometries);
	}
	point.hide(); // create and hide? (some methods assume point is non-null).
    }
    
    // out of attached geometries
    public IPointAgent(IVecI geometryOrigin, IGeometry... geometries){
	pos = geometryOrigin.get();
	show();
	attach(geometryOrigin, geometries);
	point.hide(); // create and hide? (some methods assume point is non-null).
    }
    
    
    public IVec position(){ return pos(); }
    public IPointAgent position(IVecI v){ return pos(v); }
    
    public IVec pos(){ return pos; }
    public IPointAgent pos(IVecI v){ pos.set(v); return this; }
    
    
    /*********************************************
     * geometry object attachment to track agent
     ********************************************/
    
    /** attach geometry object to agent to track its location.
	@param geometries one or more geometry objects. object's center is moved to the agent's location.  */
    public IPointAgent attach(IGeometry... geometries){
	if(tracker!=null) tracker.del(); // replace
	tracker = new IAgentTracker(this, geometries);
	return this;
    }
    
    /** attach geometry object to agent to track its location.
	@param geometryOrigin origin of geometry object which is moved to the agent's location.
	@param geometries one or more geometry objects.
    */
    public IPointAgent attach(IVecI geometryOrigin, IGeometry... geometries){
	if(tracker!=null) tracker.del(); // replace
	tracker = new IAgentTracker(this, geometryOrigin, geometries);
	return this;
    }
    
    /**************************************
     * get attached geometries 
     **************************************/
    /** total number of geometries */
    public int geometryNum(){ if(tracker==null){ return 0; } return tracker.geometryNum(); }
    /** returns first geomery */
    public IGeometry geometry(){ if(tracker==null){ return null; } return tracker.geometry(); }
    /** returns n-th geometry */
    public IGeometry geometry(int i){ if(tracker==null){ return null; } return tracker.geometry(i); }
    /** returns all geometries */
    public IGeometry[] geometries(){ if(tracker==null){ return null; } return tracker.geometries(); }
    
    /** returns all contained points */
    public IPoint[] points(){
	if(tracker==null){ return new IPoint[]{ point }; } // return the base point
	IPoint[] pts = tracker.points();
	if(pts==null || pts.length==0){ return new IPoint[]{ point }; } // return the base point
	return pts;
    }
    /** returns first point */
    public IPoint point(){
	if(tracker==null){ return point; } // return the base point
	IPoint pt = tracker.point();
	if(pt==null){ return point; } // return the base point
	return pt;
    }
    /** returns n-th point */
    public IPoint point(int index){
	if(tracker==null){ return point; } // return the base point
	IPoint pt = tracker.point(index);
	if(pt==null){ return point; } // return the base point
	return pt;
    }
    /** returns point num */
    public int pointNum(){
	if(tracker==null){ return 1; } // counting the base point
	int num = tracker.pointNum();
	if(num==0){ return 1; } // counting the base point
	return num;
    }
    

    /** returns all contained curves */
    public ICurve[] curves(){ if(tracker==null){ return null; } return tracker.curves(); }
    /** returns first curve */
    public ICurve curve(){ if(tracker==null){ return null; } return tracker.curve(); }
    /** returns n-th curve */
    public ICurve curve(int index){ if(tracker==null){ return null; } return tracker.curve(index); }
    /** returns curve num */
    public int curveNum(){ if(tracker==null){ return 0; } return tracker.curveNum(); }


    /** returns all contained surfaces */
    public ISurface[] surfaces(){ if(tracker==null){ return null; } return tracker.surfaces(); }
    /** returns first surface */
    public ISurface surface(){ if(tracker==null){ return null; } return tracker.surface(); }
    /** returns n-th surface */
    public ISurface surface(int index){ if(tracker==null){ return null; } return tracker.surface(index); }
    /** returns surface num */
    public int surfaceNum(){ if(tracker==null){ return 0; } return tracker.surfaceNum(); }
    
    /** returns all contained breps */
    public IBrep[] breps(){ if(tracker==null){ return null; } return tracker.breps(); }
    /** returns first brep */
    public IBrep brep(){ if(tracker==null){ return null; } return tracker.brep(); }
    /** returns n-th surface */
    public IBrep brep(int index){ if(tracker==null){ return null; } return tracker.brep(index); }
    /** returns brep num */
    public int brepNum(){ if(tracker==null){ return 0; } return tracker.brepNum(); }
    
    /** returns all contained meshes */
    public IMesh[] meshes(){ if(tracker==null){ return null; } return tracker.meshes(); }
    /** returns first mesh */
    public IMesh mesh(){ if(tracker==null){ return null; } return tracker.mesh(); }
    /** returns n-th surface */
    public IMesh mesh(int index){ if(tracker==null){ return null; } return tracker.mesh(index); }
    /** returns mesh num */
    public int meshNum(){ if(tracker==null){ return 0; } return tracker.meshNum(); }
    
    
    
    
    /**************************************
     * methods of IVecI
     *************************************/
    
    public double x(){ return pos.x(); }
    public double y(){ return pos.y(); }
    public double z(){ return pos.z(); }
    
    public IPointAgent x(double vx){ pos.x(vx); return this; }
    public IPointAgent y(double vy){ pos.y(vy); return this; }
    public IPointAgent z(double vz){ pos.z(vz); return this; }
    
    public IPointAgent x(IDoubleI vx){ pos.x(vx); return this; }
    public IPointAgent y(IDoubleI vy){ pos.y(vy); return this; }
    public IPointAgent z(IDoubleI vz){ pos.z(vz); return this; }

    /** setting x component by x component of input vector*/
    public IPointAgent x(IVecI v){ pos.x(v); return this; }
    /** setting y component by y component of input vector*/
    public IPointAgent y(IVecI v){ pos.y(v); return this; }
    /** setting z component by z component of input vector*/
    public IPointAgent z(IVecI v){ pos.z(v); return this; }
    
    /** setting x component by x component of input vector*/
    public IPointAgent x(IVec2I v){ pos.x(v); return this; }
    /** setting y component by y component of input vector*/
    public IPointAgent y(IVec2I v){ pos.y(v); return this; }


    
    public double x(ISwitchE e){ return pos.x(e); }
    public double y(ISwitchE e){ return pos.y(e); }
    public double z(ISwitchE e){ return pos.z(e); }
    
    public IDouble x(ISwitchR r){ return pos.x(r); }
    public IDouble y(ISwitchR r){ return pos.y(r); }
    public IDouble z(ISwitchR r){ return pos.z(r); }
    
    
    public IVec get(){ return pos.get(); }
    
    public IPointAgent dup(){ return new IPointAgent(this); }
    
    public IVec2 to2d(){ return pos.to2d(); }
    public IVec2 to2d(IVecI projectionDir){ return pos.to2d(projectionDir); }
    public IVec2 to2d(IVecI xaxis, IVecI yaxis){ return pos.to2d(xaxis,yaxis); }
    public IVec2 to2d(IVecI xaxis, IVecI yaxis, IVecI origin){ return pos.to2d(xaxis,yaxis,origin); }
    
    public IVec4 to4d(){ return pos.to4d(); }
    public IVec4 to4d(double w){ return pos.to4d(w); }
    public IVec4 to4d(IDoubleI w){ return pos.to4d(w); }
    
    public IDouble getX(){ return pos.getX(); }
    public IDouble getY(){ return pos.getY(); }
    public IDouble getZ(){ return pos.getZ(); }
    
    public IPointAgent set(IVecI v){ pos.set(v); return this; }
    public IPointAgent set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IPointAgent set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IPointAgent add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IPointAgent add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IPointAgent add(IVecI v){ pos.add(v); return this; }
    
    public IPointAgent sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IPointAgent sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IPointAgent sub(IVecI v){ pos.sub(v); return this; }
    public IPointAgent mul(IDoubleI v){ pos.mul(v); return this; }
    public IPointAgent mul(double v){ pos.mul(v); return this; }
    public IPointAgent div(IDoubleI v){ pos.div(v); return this; }
    public IPointAgent div(double v){ pos.div(v); return this; }
    public IPointAgent neg(){ pos.neg(); return this; }
    public IPointAgent rev(){ return neg(); }
    public IPointAgent flip(){ return neg(); }

    public IPointAgent zero(){ pos.zero(); return this; }
    
    public IPointAgent add(IVecI v, double f){ pos.add(v,f); return this; }
    public IPointAgent add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IPointAgent add(double f, IVecI v){ return add(v,f); }
    public IPointAgent add(IDoubleI f, IVecI v){ return add(v,f); }
    
    
    public double dot(IVecI v){ return pos.dot(v); }
    public double dot(double vx, double vy, double vz){ return pos.dot(vx,vy,vz); }
    public double dot(ISwitchE e, IVecI v){ return pos.dot(e,v); }
    public IDouble dot(ISwitchR r, IVecI v){ return pos.dot(r,v); }
    
    // returns IVec
    public IVec cross(IVecI v){ return pos.cross(v); }
    public IVec cross(double vx, double vy, double vz){ return pos.cross(vx,vy,vz); }
    
    public double len(){ return pos.len(); }
    public double len(ISwitchE e){ return pos.len(e); }
    public IDouble len(ISwitchR r){ return pos.len(r); }
    
    public double len2(){ return pos.len2(); }
    public double len2(ISwitchE e){ return pos.len2(e); }
    public IDouble len2(ISwitchR r){ return pos.len2(r); }
    
    public IPointAgent len(IDoubleI l){ pos.len(l); return this; }
    public IPointAgent len(double l){ pos.len(l); return this; }
    
    public IPointAgent unit(){ pos.unit(); return this; }
    
    public double dist(IVecI v){ return pos.dist(v); }
    public double dist(double vx, double vy, double vz){ return pos.dist(vx,vy,vz); }
    public double dist(ISwitchE e, IVecI v){ return pos.dist(e,v); }
    public IDouble dist(ISwitchR r, IVecI v){ return pos.dist(r,v); }
    
    public double dist2(IVecI v){ return pos.dist2(v); }
    public double dist2(double vx, double vy, double vz){ return pos.dist2(vx,vy,vz); }
    public double dist2(ISwitchE e, IVecI v){ return pos.dist2(e,v); }
    public IDouble dist2(ISwitchR r, IVecI v){ return pos.dist2(r,v); }
    
    public boolean eq(IVecI v){ return pos.eq(v); }
    public boolean eq(double vx, double vy, double vz){ return pos.eq(vx,vy,vz); }
    public boolean eq(ISwitchE e, IVecI v){ return pos.eq(e,v); }
    public IBool eq(ISwitchR r, IVecI v){ return pos.eq(r,v); }
    
    public boolean eq(IVecI v, double tolerance){ return pos.eq(v,tolerance); }
    public boolean eq(double vx, double vy, double vz, double tolerance){
	return pos.eq(vx,vy,vz,tolerance);
    }
    public boolean eq(ISwitchE e, IVecI v, double tolerance){ return pos.eq(e,v,tolerance); }
    public IBool eq(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eq(r,v,tolerance); }
    
    public boolean eqX(IVecI v){ return pos.eqX(v); }
    public boolean eqY(IVecI v){ return pos.eqY(v); }
    public boolean eqZ(IVecI v){ return pos.eqZ(v); }
    public boolean eqX(double vx){ return pos.eqX(vx); }
    public boolean eqY(double vy){ return pos.eqY(vy); }
    public boolean eqZ(double vz){ return pos.eqZ(vz); }
    public boolean eqX(ISwitchE e, IVecI v){ return pos.eqX(e,v); }
    public boolean eqY(ISwitchE e, IVecI v){ return pos.eqY(e,v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return pos.eqZ(e,v); }
    public IBool eqX(ISwitchR r, IVecI v){ return pos.eqX(r,v); }
    public IBool eqY(ISwitchR r, IVecI v){ return pos.eqY(r,v); }
    public IBool eqZ(ISwitchR r, IVecI v){ return pos.eqZ(r,v); }
    
    public boolean eqX(IVecI v, double tolerance){ return pos.eqX(v,tolerance); }
    public boolean eqY(IVecI v, double tolerance){ return pos.eqY(v,tolerance); }
    public boolean eqZ(IVecI v, double tolerance){ return pos.eqZ(v,tolerance); }
    public boolean eqX(double vx, double tolerance){ return pos.eqX(vx,tolerance); }
    public boolean eqY(double vy, double tolerance){ return pos.eqY(vy,tolerance); }
    public boolean eqZ(double vz, double tolerance){ return pos.eqZ(vz,tolerance); }
    public boolean eqX(ISwitchE e, IVecI v, double tolerance){ return pos.eqX(e,v,tolerance); }
    public boolean eqY(ISwitchE e, IVecI v, double tolerance){ return pos.eqY(e,v,tolerance); }
    public boolean eqZ(ISwitchE e, IVecI v, double tolerance){ return pos.eqZ(e,v,tolerance); }
    public IBool eqX(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqX(r,v,tolerance); }
    public IBool eqY(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqY(r,v,tolerance); }
    public IBool eqZ(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqZ(r,v,tolerance); }
    
    public double angle(IVecI v){ return pos.angle(v); }
    public double angle(double vx, double vy, double vz){ return pos.angle(vx,vy,vz); }
    public double angle(ISwitchE e, IVecI v){ return pos.angle(e,v); }
    public IDouble angle(ISwitchR r, IVecI v){ return pos.angle(r,v); }
    
    public double angle(IVecI v, IVecI axis){ return pos.angle(v,axis); }
    public double angle(double vx, double vy, double vz, double axisX, double axisY, double axisZ){
	return pos.angle(vx,vy,vz,axisX,axisY,axisZ);
    }
    public double angle(ISwitchE e, IVecI v, IVecI axis){ return pos.angle(e,v,axis); }
    public IDouble angle(ISwitchR r, IVecI v, IVecI axis){ return pos.angle(r,v,axis); }
    
    public IPointAgent rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IPointAgent rot(double angle){ pos.rot(angle); return this; }
    
    public IPointAgent rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IPointAgent rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IPointAgent rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IPointAgent rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IPointAgent rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IPointAgent rot(double centerX, double centerY, double centerZ,
			   double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX, centerY, centerZ, axisX, axisY, axisZ, angle); return this;
    }
    
    public IPointAgent rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IPointAgent rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IPointAgent rot2(IDoubleI angle){ return rot(angle); }
    public IPointAgent rot2(double angle){ return rot(angle); }
    public IPointAgent rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IPointAgent rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IPointAgent rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    public IPointAgent rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    public IPointAgent rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    public IPointAgent scale(IDoubleI f){ pos.scale(f); return this; }
    public IPointAgent scale(double f){ pos.scale(f); return this; }
    
    public IPointAgent scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IPointAgent scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IPointAgent scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IPointAgent scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IPointAgent scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IPointAgent scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IPointAgent scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IPointAgent scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IPointAgent scale1d(double centerX, double centerY, double centerZ,
			       double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    public IPointAgent ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IPointAgent ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IPointAgent ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    public IPointAgent ref(double centerX, double centerY, double centerZ,
			   double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IPointAgent mirror(IVecI planeDir){ return ref(planeDir); }
    public IPointAgent mirror(double planeX,double planeY,double planeZ){
	return ref(planeX,planeY,planeZ);
    }
    public IPointAgent mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    public IPointAgent mirror(double centerX, double centerY, double centerZ,
			      double planeX, double planeY, double planeZ){
	return ref(centerX,centerY,centerZ,planeX,planeY,planeZ);
    }
    
    
    public IPointAgent shear(double sxy, double syx, double syz,
			     double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IPointAgent shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			     IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IPointAgent shear(IVecI center, double sxy, double syx, double syz,
			     double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IPointAgent shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			     IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IPointAgent shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IPointAgent shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IPointAgent shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IPointAgent shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IPointAgent shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IPointAgent shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IPointAgent shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IPointAgent shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IPointAgent shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IPointAgent shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IPointAgent shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IPointAgent shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    public IPointAgent translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IPointAgent translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IPointAgent translate(IVecI v){ pos.translate(v); return this; }
    
    public IPointAgent transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IPointAgent transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IPointAgent transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IPointAgent transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    public IPointAgent mv(double x, double y, double z){ return add(x,y,z); }
    public IPointAgent mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IPointAgent mv(IVecI v){ return add(v); }
    
    public IPointAgent cp(){ return dup(); }
    public IPointAgent cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IPointAgent cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IPointAgent cp(IVecI v){ return dup().add(v); }
    
    // returns IVec, not IPointAgent
    public IVec dif(IVecI v){ return pos.dif(v); }
    public IVec dif(double vx, double vy, double vz){ return pos.dif(vx,vy,vz); }
    public IVec diff(IVecI v){ return dif(v); }
    public IVec diff(double vx, double vy, double vz){ return dif(vx,vy,vz); }
    
    public IVec mid(IVecI v){ return pos.mid(v); }
    public IVec mid(double vx, double vy, double vz){ return pos.mid(vx,vy,vz); }
    public IVec sum(IVecI v){ return pos.sum(v); }
    public IVec sum(double vx, double vy, double vz){ return pos.sum(vx,vy,vz); }
    
    public IVec sum(IVecI... v){ return pos.sum(v); }
    public IVec bisect(IVecI v){ return pos.bisect(v); }
    public IVec bisect(double vx, double vy, double vz){ return pos.bisect(vx,vy,vz); }
    
    public IVec sum(IVecI v2, double w1, double w2){ return pos.sum(v2,w1,w2); }
    public IVec sum(IVecI v2, double w2){ return pos.sum(v2,w2); }
    public IVec sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return sum(v2,w1,w2); }
    public IVec sum(IVecI v2, IDoubleI w2){ return sum(v2,w2); }
    
    public IVec nml(IVecI v){ return pos.nml(v); }
    public IVec nml(double vx, double vy, double vz){ return pos.nml(vx,vy,vz); }
    public IVec nml(IVecI pt1, IVecI pt2){ return pos.nml(pt1,pt2); }
    public IVec nml(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2){
	return pos.nml(vx1,vy1,vz1,vx2,vy2,vz2);
    }
    
    public boolean isValid(){ if(pos==null){ return false; } return pos.isValid(); }
    
    public String toString(){ if(pos==null){ return super.toString(); } return pos.toString(); }
    
    
    /**************************************
     * methods of IPoint
     *************************************/
    public IPointAgent setSize(double sz){ return size(sz); }
    public IPointAgent size(double sz){ point.size(sz); return this; }
    public double getSize(){ return point.size(); }
    public double size(){ return point.size(); }
    
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IPointAgent name(String nm){ super.name(nm); point.name(nm); return this; }
    public IPointAgent layer(ILayer l){ super.layer(l); point.layer(l); return this; }
    
    
    public IPointAgent show(){
	if(point==null){ point = new IPoint(pos).clr(super.clr()); }
	else{ point.show(); }
	if(tracker!=null){ tracker.show(); }
	super.show();
	return this;
    }
    public IPointAgent hide(){
	if(point!=null) point.hide();
	if(tracker!=null) tracker.hide();
	super.hide();
	return this;
    }
    
    public IPointAgent showPoint(){
	if(point==null){ point = new IPoint(pos).clr(super.clr()); } else{ point.show(); }
	super.show(); return this;
    }
    public IPointAgent hidePoint(){ 
	if(point!=null) point.hide();
	//super.hide(); // point agent might have other geometry and as a parent it's visible.
	return this; 
    }
    
    /** show attached geometry */
    public IPointAgent showGeometry(){ if(tracker!=null){ tracker.show(); } return this; }
    /** hide attached geometry */
    public IPointAgent hideGeometry(){ if(tracker!=null){ tracker.hide(); } return this; }
    
    
    public void del(){ if(tracker!=null) tracker.del(); point.del(); super.del(); } //
    
    /** stop agent with option of deleting/keeping the geometry the agent owns */
    public void del(boolean deleteGeometry){ 
	if(deleteGeometry){
	    if(tracker!=null) tracker.del(); point.del();
	}
	super.del();
    }
    
    
    public IPointAgent clr(IColor c){ super.clr(c); point.clr(c); if(tracker!=null){ tracker.clr(c); } return this; }
    public IPointAgent clr(IColor c, int alpha){ super.clr(c,alpha); point.clr(c,alpha); if(tracker!=null){ tracker.clr(c,alpha); } return this; }
    public IPointAgent clr(IColor c, float alpha){ super.clr(c,alpha); point.clr(c,alpha); if(tracker!=null){ tracker.clr(c,alpha); } return this; }
    public IPointAgent clr(IColor c, double alpha){ super.clr(c,alpha); point.clr(c,alpha); if(tracker!=null){ tracker.clr(c,alpha); } return this; }
    //public IPointAgent clr(Color c){ super.clr(c); point.clr(c); return this; }
    //public IPointAgent clr(Color c, int alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    //public IPointAgent clr(Color c, float alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    //public IPointAgent clr(Color c, double alpha){ super.clr(c,alpha); point.clr(c,alpha); return this; }
    public IPointAgent clr(int gray){ super.clr(gray); point.clr(gray); if(tracker!=null){ tracker.clr(gray); } return this; }
    public IPointAgent clr(float fgray){ super.clr(fgray); point.clr(fgray); if(tracker!=null){ tracker.clr(fgray); } return this; }
    public IPointAgent clr(double dgray){ super.clr(dgray); point.clr(dgray); if(tracker!=null){ tracker.clr(dgray); } return this; }
    public IPointAgent clr(int gray, int alpha){ super.clr(gray,alpha); point.clr(gray,alpha); if(tracker!=null){ tracker.clr(gray,alpha); } return this; }
    public IPointAgent clr(float fgray, float falpha){ super.clr(fgray,falpha); point.clr(fgray,falpha); if(tracker!=null){ tracker.clr(fgray,falpha); } return this; }
    public IPointAgent clr(double dgray, double dalpha){ super.clr(dgray,dalpha); point.clr(dgray,dalpha); if(tracker!=null){ tracker.clr(dgray,dalpha); } return this; }
    public IPointAgent clr(int r, int g, int b){ super.clr(r,g,b); point.clr(r,g,b); if(tracker!=null){ tracker.clr(r,g,b); } return this; }
    public IPointAgent clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); point.clr(fr,fg,fb); if(tracker!=null){ tracker.clr(fr,fg,fb); } return this; }
    public IPointAgent clr(double dr, double dg, double db){ super.clr(dr,dg,db); point.clr(dr,dg,db); if(tracker!=null){ tracker.clr(dr,dg,db); } return this; }
    public IPointAgent clr(int r, int g, int b, int a){
	super.clr(r,g,b,a); point.clr(r,g,b,a); if(tracker!=null){ tracker.clr(r,g,b,a); } return this;
    }
    public IPointAgent clr(float fr, float fg, float fb, float fa){
	super.clr(fr,fg,fb,fa); point.clr(fr,fg,fb,fa); if(tracker!=null){ tracker.clr(fr,fg,fb,fa); } return this;
    }
    public IPointAgent clr(double dr, double dg, double db, double da){
	super.clr(dr,dg,db,da); point.clr(dr,dg,db,da); if(tracker!=null){ tracker.clr(dr,dg,db,da); } return this;
    }
    public IPointAgent hsb(float h, float s, float b, float a){
	super.hsb(h,s,b,a); point.hsb(h,s,b,a); if(tracker!=null){ tracker.hsb(h,s,b,a); } return this;
    }
    public IPointAgent hsb(double h, double s, double b, double a){
	super.hsb(h,s,b,a); point.hsb(h,s,b,a); if(tracker!=null){ tracker.hsb(h,s,b,a); } return this;
    }
    public IPointAgent hsb(float h, float s, float b){
	super.hsb(h,s,b); point.hsb(h,s,b); if(tracker!=null){ tracker.clr(h,s,b); } return this;
    }
    public IPointAgent hsb(double h, double s, double b){
	super.hsb(h,s,b); point.hsb(h,s,b); if(tracker!=null){ tracker.clr(h,s,b); } return this;
    }
    
    public IPointAgent setColor(IColor c){ return clr(c); }
    public IPointAgent setColor(IColor c, int alpha){ return clr(c,alpha); }
    public IPointAgent setColor(IColor c, float alpha){ return clr(c,alpha); }
    public IPointAgent setColor(IColor c, double alpha){ return clr(c,alpha); }
    //public IPointAgent setColor(Color c){ return clr(c); }
    //public IPointAgent setColor(Color c, int alpha){ return clr(c,alpha); }
    //public IPointAgent setColor(Color c, float alpha){ return clr(c,alpha); }
    //public IPointAgent setColor(Color c, double alpha){ return clr(c,alpha); }
    public IPointAgent setColor(int gray){ return clr(gray); }
    public IPointAgent setColor(float fgray){ return clr(fgray); }
    public IPointAgent setColor(double dgray){ return clr(dgray); }
    public IPointAgent setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IPointAgent setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IPointAgent setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IPointAgent setColor(int r, int g, int b){ return clr(r,g,b); }
    public IPointAgent setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IPointAgent setColor(double dr, double dg, double db){ return clr(dr,dg,db); }
    public IPointAgent setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IPointAgent setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IPointAgent setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IPointAgent setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IPointAgent setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IPointAgent setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IPointAgent setHSBColor(double h, double s, double b){ return hsb(h,s,b); }
    
    public IPointAgent weight(double w){ super.weight(w); point.weight(w); return this; }
    public IPointAgent weight(float w){ super.weight(w); point.weight(w); return this; }
}
