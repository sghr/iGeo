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
   Class of a vertex of polygon mesh.
   
   @author Satoru Sugihara
*/
public class IVertex implements IVecI{
    
    public ArrayList<IEdge> edges;
    public ArrayList<IFace> faces;
    public ArrayList<IVertex> linkedVertices;
    
    public IVecI pos;
    public IVecI normal;
    public IVec2I texture; // texture coordinates
    
    /** index number in IMeshGeo vertices array list */
    public int index=-1;
    
    //public int index; // index number in parent mesh
    
    public IVertex(){ pos = new IVec(); init(); }
    public IVertex(double x, double y, double z){ pos = new IVec(x,y,z); init(); }

    //public IVertex(IVec p){ pos=p; init(); }
    //public IVertex(IVec4 p){ pos=p; init(); }
    //public IVertex(IVecI p){ pos=p; init(); }
    
    // 20120614 cp() is added to avoid redundant transformation on different vertices but sharing same IVecI pos. 
    public IVertex(IVec p){ pos=p.cp(); init(); }
    public IVertex(IVec4 p){ pos=p.cp(); init(); }
    public IVertex(IVecI p){ pos=p.cp(); init(); }
    
    public IVertex(IVertex v){
	edges = new ArrayList<IEdge>();
	for(int i=0; i<v.edges.size(); i++) edges.add(v.edges.get(i));
	
	faces = new ArrayList<IFace>();
	for(int i=0; i<v.faces.size(); i++) faces.add(v.faces.get(i));
	
	linkedVertices=new ArrayList<IVertex>();
	for(int i=0; i<v.linkedVertices.size(); i++)
	    linkedVertices.add(v.linkedVertices.get(i));
	
	pos = v.pos.dup();
	if(v.normal!=null) normal=v.normal.dup();
	if(v.texture!=null) texture = v.texture.dup();
    }
    
    public void init(){
	edges = new ArrayList<IEdge>();
	faces = new ArrayList<IFace>();
	linkedVertices=new ArrayList<IVertex>();
    }
    
    
    public void del(){
	for(int i=0; i<faces.size(); i++){
	    //faces.get(i).vertices.remove(this);
	    faces.get(i).del();
	    i--;
	}
	for(int i=0; i<edges.size(); i++){
	    edges.get(i).del();
	    i--;
	}
	//for(int i=0; i<linkedVertices.size(); i++){ linkedVertices.get(i).linkedVertices.remove(this); }
    }
    
    public void del(IEdge e){
        edges.remove(e);
        IVertex v = e.vertices[0];
        if(v==this) v = e.vertices[1];
        linkedVertices.remove(v);
    }
    
    public double x(){ return pos.x(); }
    public double y(){ return pos.y(); }
    public double z(){ return pos.z(); }
    
    public IVertex x(double vx){ pos.x(vx); return this; }
    public IVertex y(double vy){ pos.y(vy); return this; }
    public IVertex z(double vz){ pos.z(vz); return this; }
    
    public IVertex x(IDoubleI vx){ pos.x(vx); return this; }
    public IVertex y(IDoubleI vy){ pos.y(vy); return this; }
    public IVertex z(IDoubleI vz){ pos.z(vz); return this; }

    public IVertex x(IVecI vx){ pos.x(vx); return this; }
    public IVertex y(IVecI vy){ pos.y(vy); return this; }
    public IVertex z(IVecI vz){ pos.z(vz); return this; }
    
    public IVertex x(IVec2I vx){ pos.x(vx); return this; }
    public IVertex y(IVec2I vy){ pos.y(vy); return this; }
    
    public double x(ISwitchE e){ return pos.x(e); }
    public double y(ISwitchE e){ return pos.y(e); }
    public double z(ISwitchE e){ return pos.z(e); }
    
    public IDoubleI x(ISwitchR r){ return pos.x(r); }
    public IDoubleI y(ISwitchR r){ return pos.y(r); }
    public IDoubleI z(ISwitchR r){ return pos.z(r); }
    
    public IVec get(){ return pos.get(); }
    
    /** passing position field */
    public IVecI pos(){ return pos; }

    
    public IVertex dup(){ return new IVertex(this); }
    
    /*
    public IVertex dup(){ // just point, not link nor edges, faces
	IVertex v = new IVertex(pos.dup());
	v.normal = this.normal.dup();
	v.texture = this.texture.dup();
	return v;
    }
    */
    
    public IVec2I to2d(){ return pos.to2d(); }
    public IVec2I to2d(IVecI projectionDir){ return pos.to2d(projectionDir); }
    public IVec2I to2d(IVecI xaxis, IVecI yaxis){ return pos.to2d(xaxis,yaxis); }
    public IVec2I to2d(IVecI xaxis, IVecI yaxis, IVecI origin){ return pos.to2d(xaxis,yaxis,origin); }
    
    
    public IVec4I to4d(){ return pos.to4d(); }
    public IVec4I to4d(double w){ return pos.to4d(w); }
    public IVec4I to4d(IDoubleI w){ return pos.to4d(w); }
    
    public IDoubleI getX(){ return pos.getX(); }
    public IDoubleI getY(){ return pos.getY(); }
    public IDoubleI getZ(){ return pos.getZ(); }
    
    
    public IVertex set(IVecI v){ pos.set(v); return this; }
    public IVertex set(double x, double y, double z){ pos.set(x,y,z); return this; }
    public IVertex set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IVertex add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IVertex add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IVertex add(IVecI v){ pos.add(v); return this; }
    public IVertex sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IVertex sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IVertex sub(IVecI v){ pos.sub(v); return this; }
    public IVertex mul(IDoubleI v){ pos.mul(v); return this; }
    public IVertex mul(double v){ pos.mul(v); return this; }
    public IVertex div(IDoubleI v){ pos.div(v); return this; }
    public IVertex div(double v){ pos.div(v); return this; }
    public IVertex neg(){ pos.neg(); return this; }
    public IVertex rev(){ return neg(); }
    public IVertex flip(){ return neg(); }
    
    public IVertex zero(){ pos.zero(); return this; }

    /** scale add */
    public IVertex add(IVecI v, double f){ pos.add(v,f); return this; }
    /** scale add */
    public IVertex add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    /** scale add alias */
    public IVertex add(double f, IVecI v){ return add(v,f); }
    /** scale add alias */
    public IVertex add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public double dot(IVecI v){ return pos.dot(v); }
    public double dot(double vx, double vy, double vz){ return pos.dot(vx,vy,vz); }
    public double dot(ISwitchE e, IVecI v){ return pos.dot(e,v); }
    public IDoubleI dot(ISwitchR r, IVecI v){ return pos.dot(r,v); }
    
    // returns IVecI, not IVertex (2011/10/12)
    public IVecI cross(IVecI v){ return pos.cross(v); }
    public IVecI cross(double vx, double vy, double vz){ return pos.cross(vx,vy,vz); }
    //public IVertex cross(IVecI v){ return dup().set(pos.cross(v)); }
    
    public double len(){ return pos.len(); }
    public double len(ISwitchE e){ return pos.len(e); }
    public IDoubleI len(ISwitchR r){ return pos.len(r); }
    
    public double len2(){ return pos.len2(); }
    public double len2(ISwitchE e){ return pos.len2(e); }
    public IDoubleI len2(ISwitchR r){ return pos.len2(r); }
    
    public IVertex len(IDoubleI l){ pos.len(l); return this; }
    public IVertex len(double l){ pos.len(l); return this; }
   
    public IVertex unit(){ pos.unit(); return this; }
    
    public double dist(IVecI v){ return pos.dist(v); }
    public double dist(double vx, double vy, double vz){ return pos.dist(vx,vy,vz); }
    public double dist(ISwitchE e, IVecI v){ return pos.dist(e,v); }
    public IDoubleI dist(ISwitchR r, IVecI v){ return pos.dist(r,v); }
    
    public double dist2(IVecI v){ return pos.dist2(v); }
    public double dist2(double vx, double vy, double vz){ return pos.dist2(vx,vy,vz); }
    public double dist2(ISwitchE e, IVecI v){ return pos.dist2(e,v); }
    public IDoubleI dist2(ISwitchR r, IVecI v){ return pos.dist2(r,v); }
    
    
    public boolean eq(IVecI v){ return pos.eq(v); }
    public boolean eq(double vx, double vy, double vz){ return pos.eq(vx,vy,vz); }
    public boolean eq(ISwitchE e, IVecI v){ return pos.eq(e,v); }
    public IBoolI eq(ISwitchR r, IVecI v){ return pos.eq(r,v); }
    
    public boolean eq(IVecI v, double tolerance){ return pos.eq(v,tolerance); }
    public boolean eq(double vx, double vy, double vz, double tolerance){
	return pos.eq(vx,vy,vz,tolerance);
    }
    public boolean eq(ISwitchE e, IVecI v, double tolerance){ return pos.eq(e,v,tolerance); }
    public IBoolI eq(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eq(r,v,tolerance); }
    
    public boolean eqX(IVecI v){ return pos.eqX(v); }
    public boolean eqY(IVecI v){ return pos.eqY(v); }
    public boolean eqZ(IVecI v){ return pos.eqZ(v); }
    public boolean eqX(double vx){ return pos.eqX(vx); }
    public boolean eqY(double vy){ return pos.eqY(vy); }
    public boolean eqZ(double vz){ return pos.eqZ(vz); }
    public boolean eqX(ISwitchE e, IVecI v){ return pos.eqX(e,v); }
    public boolean eqY(ISwitchE e, IVecI v){ return pos.eqY(e,v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return pos.eqZ(e,v); }
    public IBoolI eqX(ISwitchR r, IVecI v){ return pos.eqX(r,v); }
    public IBoolI eqY(ISwitchR r, IVecI v){ return pos.eqY(r,v); }
    public IBoolI eqZ(ISwitchR r, IVecI v){ return pos.eqZ(r,v); }
    
    
    public boolean eqX(IVecI v, double tolerance){ return pos.eqX(v,tolerance); }
    public boolean eqY(IVecI v, double tolerance){ return pos.eqY(v,tolerance); }
    public boolean eqZ(IVecI v, double tolerance){ return pos.eqZ(v,tolerance); }
    public boolean eqX(double vx, double tolerance){ return pos.eqX(vx,tolerance); }
    public boolean eqY(double vy, double tolerance){ return pos.eqY(vy,tolerance); }
    public boolean eqZ(double vz, double tolerance){ return pos.eqZ(vz,tolerance); }
    public boolean eqX(ISwitchE e, IVecI v, double tolerance){ return pos.eqX(e,v,tolerance); }
    public boolean eqY(ISwitchE e, IVecI v, double tolerance){ return pos.eqY(e,v,tolerance); }
    public boolean eqZ(ISwitchE e, IVecI v, double tolerance){ return pos.eqZ(e,v,tolerance); }
    public IBoolI eqX(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqX(r,v,tolerance); }
    public IBoolI eqY(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqY(r,v,tolerance); }
    public IBoolI eqZ(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqZ(r,v,tolerance); }
    
    
    public double angle(IVecI v){ return pos.angle(v); }
    public double angle(double vx, double vy, double vz){ return pos.angle(vx,vy,vz); }
    public double angle(ISwitchE e, IVecI v){ return pos.angle(e,v); }
    public IDoubleI angle(ISwitchR r, IVecI v){ return pos.angle(r,v); }
    
    public double angle(IVecI v, IVecI axis){ return pos.angle(v,axis); }
    public double angle(double vx, double vy, double vz, double axisX, double axisY, double axisZ){
	return pos.angle(vx,vy,vz,axisX,axisY,axisZ);
    }
    public double angle(ISwitchE e, IVecI v, IVecI axis){ return pos.angle(e,v,axis); }
    public IDoubleI angle(ISwitchR r, IVecI v, IVecI axis){ return pos.angle(r,v,axis); }
    
    public IVertex rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IVertex rot(double angle){ pos.rot(angle); return this; }
    public IVertex rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IVertex rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IVertex rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    public IVertex rot(IVecI center, IVecI axis, IDoubleI angle){ pos.rot(center,axis,angle); return this; }
    public IVertex rot(IVecI center, IVecI axis, double angle){ pos.rot(center,axis,angle); return this; }
    public IVertex rot(double centerX, double centerY, double centerZ,
		       double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX, centerY, centerZ, axisX, axisY, axisZ, angle); return this;
    }
    
    public IVertex rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    public IVertex rot(IVecI center, IVecI axis, IVecI destPt){ pos.rot(center,axis,destPt); return this; }
    public IVertex rot2(IDoubleI angle){ return rot(angle); }
    public IVertex rot2(double angle){ return rot(angle); }
    public IVertex rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IVertex rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IVertex rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    public IVertex rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    public IVertex rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    public IVertex scale(IDoubleI f){ pos.scale(f); return this; }
    public IVertex scale(double f){ pos.scale(f); return this; }
    public IVertex scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IVertex scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IVertex scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IVertex scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IVertex scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IVertex scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IVertex scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IVertex scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IVertex scale1d(double centerX, double centerY, double centerZ,
			   double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    
    public IVertex ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IVertex ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IVertex ref(IVecI center, IVecI planeDir){ pos.ref(center,planeDir); return this; }
    public IVertex ref(double centerX, double centerY, double centerZ,
		       double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    public IVertex mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    public IVertex mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    public IVertex mirror(IVecI center, IVecI planeDir){ pos.ref(center,planeDir); return this; }
    public IVertex mirror(double centerX, double centerY, double centerZ,
			  double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    /** shear operation */
    public IVertex shear(double sxy, double syx, double syz,
			 double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVertex shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			 IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVertex shear(IVecI center, double sxy, double syx, double syz,
			 double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IVertex shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			 IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IVertex shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IVertex shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IVertex shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IVertex shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IVertex shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IVertex shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IVertex shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IVertex shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IVertex shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IVertex shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IVertex shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IVertex shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public IVertex translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IVertex translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IVertex translate(IVecI v){ pos.translate(v); return this; }
        
    
    public IVertex transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IVertex transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IVertex transform(IVecI xvec, IVecI yvec, IVecI zvec){ pos.transform(xvec,yvec,zvec); return this; }
    public IVertex transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){ pos.transform(xvec,yvec,zvec,translate); return this; }
    
    
    /** mv() is alias of add() */
    public IVertex mv(double x, double y, double z){ return add(x,y,z); }
    public IVertex mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IVertex mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IVertex cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IVertex cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IVertex cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IVertex cp(IVecI v){ return dup().add(v); }
    
    
    // returns IVecI not IVertex (2011/10/12)
    //public IVertex diff(IVecI v){ return dup().sub(v); }
    public IVecI dif(IVecI v){ return pos.dif(v); }
    public IVecI dif(double vx, double vy, double vz){ return pos.dif(vx,vy,vz); }
    public IVecI diff(IVecI v){ return dif(v); }
    public IVecI diff(double vx, double vy, double vz){ return dif(vx,vy,vz); }
    //public IVertex mid(IVecI v){ return dup().add(v).div(2); }
    public IVecI mid(IVecI v){ return pos.mid(v); }
    public IVecI mid(double vx, double vy, double vz){ return pos.mid(vx,vy,vz); }
    //public IVertex sum(IVecI v){ return dup().add(v); }
    public IVecI sum(IVecI v){ return dup().add(v); }
    public IVecI sum(double vx, double vy, double vz){ return dup().add(vx,vy,vz); }
    //public IVertex sum(IVecI... v){IVertex ret=this.dup();for(IVecI vi:v)ret.add(vi);return ret; }
    public IVecI sum(IVecI... v){ return pos.sum(v); }
    
    
    //public IVertex bisect(IVecI v){ return dup().unit().add(v.dup().unit()); }
    public IVecI bisect(IVecI v){ return pos.bisect(v); }
    public IVecI bisect(double vx, double vy, double vz){ return pos.bisect(vx,vy,vz); }
    
    //public IVertex sum(IVecI v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    public IVecI sum(IVecI v2, double w1, double w2){ return pos.sum(v2,w1,w2); }
    //public IVertex sum(IVecI v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    public IVecI sum(IVecI v2, double w2){ return pos.sum(v2,w2); }
    
    //public IVertex sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return dup().mul(w1).add(v2,w2); }
    public IVecI sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return pos.sum(v2,w1,w2); }
    //public IVertex sum(IVecI v2, IDoubleI w2){ return dup().mul((new IDouble(1.0)).sub(w2)).add(v2,w2); }
    public IVecI sum(IVecI v2, IDoubleI w2){ return pos.sum(v2,w2); }
    
    
    /** alias of cross. (not unitized ... ?) returns IVecI */
    //public IVertex nml(IVecI v){ return cross(v); }
    public IVecI nml(IVecI v){ return pos.nml(v); } // coliding with nml(IVec) setting normal dir of vertex !!!!!!!!!!!!!!
    // IVertex.nml is different function from what IVecI intend - updated ; nml -> setNml
    public IVecI nml(double vx, double vy, double vz){ return pos.nml(vx,vy,vz); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    //public IVertex nml(IVecI pt1, IVecI pt2){ return this.diff(pt1).cross(this.diff(pt2)).unit(); }
    public IVecI nml(IVecI pt1, IVecI pt2){ return pos.nml(pt1,pt2); }
    public IVecI nml(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2){
	return pos.nml(vx1,vy1,vz1,vx2,vy2,vz2);
    }
    
    
    
    public IVec getAverageNormal(){
	if(faces==null || faces.size()==0) return new IVec(0,0,1); // default
	IVec n=new IVec(0,0,0);
	for(int i=0; i<faces.size(); i++)
	    n.add(faces.get(i).getAverageNormal());
	n.unit();
	return n;
    }
    
    public void calcNormal(){
	normal = getAverageNormal();
    }
    
    /** getting normal (alias) */
    public IVecI normal(){ return nml(); }
    /** getting normal */
    //public IVecI nrml(){ return nml(); }
    /** getting normal */
    public IVecI nml(){
	if(normal==null) calcNormal();
	return normal;
    }
    /** getting normal (alias) */
    public IVecI getNormal(){ return nml(); }
    /** getting normal (alias) */
    public IVecI getNml(){ return nml(); }
    
    
    //public IVecI nrml(){ return normal.dup(); } //1. why dup? 2. crash if normal is null.
    
    // setting normal : nml(IVecI) is taken by IVecI. instead setNml(IVecI) or setNormal(IVecI)
    public IVertex setNormal(IVecI n){ return setNml(n); }
    public IVertex setNormal(double x, double y, double z){ return setNml(x,y,z); }
    public IVertex setNml(IVecI n){ normal = n; return this; }
    public IVertex setNml(double x, double y, double z){ return setNml(new IVec(x,y,z)); }
    
    //public IVertex normal(IVec n){ return nml(n); }
    //public IVertex normal(double x, double y, double z){ return nml(x,y,z); }
    //public IVertex nrml(IVec n){ return nml(n); }
    //public IVertex nrml(double x, double y, double z){ return nml(x,y,z); }
    /** setting normal; this nml has different function from IVecI's nml */
    //public IVertex nml(IVec n){ normal = n; return this; }
    /** setting normal; this nml has different function from IVecI's nml */
    //public IVertex nml(double x, double y, double z){ return nml(new IVec(x,y,z)); }
    
    
    public boolean isValid(){ if(pos==null){ return false; } return pos.isValid(); }
    
    
    public IVec2I texture(){ return texture; }
    public IVertex texture(IVec2I t){ texture = t; return this; }
    public IVertex texture(double u, double v){ texture(new IVec2(u,v)); return this; }
    
    //public int getIndex(){ return index; }
    //public void setIndex(int i){ index=i; }
    
    public void addEdge(IEdge e){
	edges.add(e);
	if(e.getVertex(0)==this) linkedVertices.add(e.getVertex(1)); 
	else linkedVertices.add(e.getVertex(0));
    }
    public IEdge getEdge(int i){ return edge(i); }
    public IEdge edge(int i){ return edges.get(i); }
    public int edgeNum(){ return edges.size(); }
    
    public IEdge getEdgeTo(IVertex v){
	for(int i=0; i<edges.size(); i++){
	    IEdge e = edges.get(i);
	    if( e.getOtherVertex(this) == v ) return e;
	}
	return null;
    }
    
    public IEdge createEdgeTo(IVertex v){
	IEdge e = getEdgeTo(v);
	if(e==null) e = new IEdge(this, v);
	return e;
    }
    
    public void addFace(IFace f){ faces.add(f); }

    public IFace getFace(int i){ return face(i); }
    public IFace face(int i){ return faces.get(i); }
    public int faceNum(){ return faces.size(); }
    
    //public IVertex getLinkedVertex(int i){ return linkedVertices.get(i); }
    public IVertex linkedVertex(int i){ return linkedVertices.get(i); }
    
    public int linkedVertexNum(){ return linkedVertices.size(); }
    
    public void replaceLinkedVertex(IVertex oldVertex, IVertex newVertex){
	int index = linkedVertices.indexOf(oldVertex);
	if(index<0){
	    IOut.err("no such linked vertex in this vertex"); 
	    return; 
	}
	linkedVertices.set(index, newVertex);
    }
    
    
    // replace itself reconnect to existing edges and linked vertices
    public void replaceVertex(IVertex newVertex){
	
	ArrayList<IVertex> overlappingVertices=new ArrayList<IVertex>();
	for(int i=0; i<edges.size(); i++){
	    IVertex v1 = edges.get(i).getOtherVertex(this);
	    for(int j=0; j<newVertex.edges.size(); j++){
		IVertex v2 = newVertex.edges.get(j).getOtherVertex(newVertex);
		if(v1==v2){
		    if(!overlappingVertices.contains(v1)){ overlappingVertices.add(v1); }
		}
	    }
	}
	
	ArrayList<IEdge> deletingEdges=new ArrayList<IEdge>();
	for(int i=0; i<edges.size(); i++){
	    IEdge e = edges.get(i);
	    boolean overlap=false;
	    for(int j=0; j<overlappingVertices.size()&&!overlap; j++){
		if(e.contains(overlappingVertices.get(j))) overlap=true;
	    }
	    
	    if(e.contains(newVertex)) overlap=true;
	    
	    if(overlap){ deletingEdges.add(e); }
	    else{
		e.replaceVertex(this,newVertex);
		newVertex.addEdge(e);
	    }
	}
	for(int i=0; i<deletingEdges.size(); i++){ deletingEdges.get(i).del(); }
	
	for(int i=0; i<linkedVertices.size(); i++){
	    IVertex v = linkedVertices.get(i);
	    boolean overlap=false;
	    for(int j=0; j<overlappingVertices.size()&&!overlap; j++){
		if(v==overlappingVertices.get(j)) overlap=true;
	    }
	    if(v==newVertex) overlap=true;
	    
	    if(!overlap){ v.replaceLinkedVertex(this,newVertex); }
	}
	for(int i=0; i<faces.size(); i++){
	    IFace f = faces.get(i);
	    // what if overlapping
	    f.replaceVertex(this,newVertex);
	    newVertex.addFace(f);
	}
    }
    
    public IEdge[] getOtherEdges(IEdge edge){
	IEdge otherEdges[] = new IEdge[edges.size()-1];
	
	if(!edges.contains(edge)){
	    IOut.err("no such edge in this vertex");
	    return null;
	}
	
	for(int i=0, j=0; i<edges.size(); i++){
	    if(edges.get(i)!=edge){
		otherEdges[j] = edges.get(i);
		j++;
	    }
	}
	return  otherEdges;
    }

    public String toString(){
	return "IVertex: "+pos+ " (normal="+normal+", texture="+texture+")";
    }
    
    
    /** list of vertices are sorted by tracing edge connection. ideally vertices are connected as a circle by edges
	this works only when all triangulated. quad mesh might not work.
    */
    public static ArrayList<IVertex> sortByEdge(ArrayList<IVertex> vtx, int startIdx){
	if(startIdx<0 || startIdx>=vtx.size()){
	    IOut.err("start index is out of range.");
	    return null;
	}
	int num = vtx.size();
	ArrayList<IVertex> ret = new ArrayList<IVertex>();
	boolean[] taken = new boolean[num];
	for(int i=0; i<num; i++){ taken[i] = false; }
	
	ret.add(vtx.get(startIdx));
	taken[startIdx] = true;
	
	for(int i=0; ret.size()<vtx.size() && i<vtx.size()*2 /*?*/; i++){
	    int idx = findLinkedVertexInArray(ret.get(ret.size()-1), vtx, taken);
	    if(idx<0){
		IOut.err("given vertices are not connected");
		return ret; // return null; // ?
	    }
	    ret.add(vtx.get(idx));
	    taken[idx] = true;
	}
	
	if(ret.size()==vtx.size()) return ret; // sccessful
	
	IOut.err("sort failed");
	return ret; // return null; // ?
    }
    
    /** list of vertices are sorted by tracing edge connection. ideally vertices are connected as a circle by edges
	this works only when all triangulated. quad mesh might not work.
    */
    public static ArrayList<IVertex> sortByEdge(ArrayList<IVertex> vtx, IVertex v){
	return sortByEdge(vtx,vtx.indexOf(v));
    }
    
    /** list of vertices are sorted by tracing edge connection. ideally vertices are connected as a circle by edges
	this works only when all triangulated. quad mesh might not work.
    */
    public static ArrayList<IVertex> sortByEdge(ArrayList<IVertex> vtx){
	return sortByEdge(vtx,0);
    }
    
    /** returns vertex index in arraylist */
    public static int findLinkedVertexInArray(IVertex v, ArrayList<IVertex> vlist, boolean[] skiplist){
	for(int i=0; i<vlist.size(); i++){
	    if(!skiplist[i] && v.linkedVertices.contains(vlist.get(i))){ return i; }
	}
	return -1;
    }
    
    public static class ZComparator implements IComparator<IVertex>{
	public int compare(IVertex v1, IVertex v2){
	    double z1=v1.pos.z();
	    double z2=v2.pos.z(); 
	    if(z1<z2) return 1;
	    if(z1>z2) return -1;
	    return 0;
	}
    }
    
    public static class ZYXComparator implements IComparator<IVertex>{
	public int compare(IVertex v1, IVertex v2){
	    IVec p1 = v1.pos.get();
	    IVec p2 = v2.pos.get();
	    if(p1.z<p2.z-IConfig.tolerance) return 1;
	    if(p1.z>p2.z+IConfig.tolerance) return -1;
	    
	    // same z
	    if(p1.y<p2.y-IConfig.tolerance) return 1;
	    if(p1.y>p2.y+IConfig.tolerance) return -1;
	    
	    // same z & y
	    if(p1.x<p2.x-IConfig.tolerance) return 1;
	    if(p1.x>p2.x+IConfig.tolerance) return -1;
	    
	    return 0;
	}
    }
    
    
    
}
