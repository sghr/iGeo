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
import java.awt.Color;

import igeo.gui.*;

/**
   Class of polygon mesh with collidable ITriangleWall for IParticleI on each face
   
   @author Satoru Sugihara
*/
public class ICollidableMesh extends IMesh{
    
    public ArrayList<ITriangleWall> collidables;
    
    public ICollidableMesh(){ super(); initCollidable(); }
    public ICollidableMesh(IServerI s){ super(s); initCollidable(); }
    public ICollidableMesh(IMeshGeo m){ super(m); initCollidable(); }
    public ICollidableMesh(IServerI s, IMeshGeo m){ super(s,m); initCollidable(); }
    public ICollidableMesh(IMesh m){ super(m); initCollidable(); }
    public ICollidableMesh(IServerI s, IMesh m){ super(s,m); initCollidable(); }
    
    public ICollidableMesh(ICollidableMesh m){ super(m); initCollidable(m); }
    public ICollidableMesh(IServerI s, ICollidableMesh m){ super(s,m); initCollidable(m); }
    
    public ICollidableMesh(IServerI s, ICurveI[] lines){ super(s,lines); initCollidable(); }
    public ICollidableMesh(ICurveI[] lines){ super(lines); initCollidable(); }
    
    /** each input surface builds one 3 or 4 point face. trim is ignored. */
    public ICollidableMesh(IServerI s, ISurfaceI[] faces){ super(s,faces); initCollidable(); }
    /** each input surface builds one 3 or 4 point face. trim is ignored. */
    public ICollidableMesh(ISurfaceI[] faces){ super(faces); initCollidable(); }
    
    public ICollidableMesh(IServerI s, IVec[][] matrix){ super(s,matrix); initCollidable(); }
    public ICollidableMesh(IVec[][] matrix){ super(matrix); initCollidable(); }
    public ICollidableMesh(IServerI s, IVec[][] matrix, boolean triangulateDir){
	super(s,matrix,triangulateDir); initCollidable();
    }
    public ICollidableMesh(IVec[][] matrix, boolean triangulateDir){
	super(matrix,triangulateDir); initCollidable();
    }
    public ICollidableMesh(IServerI s, IVec[][] matrix, boolean triangulateDir, IMeshType creator){
	super(s,matrix,triangulateDir,creator); initCollidable();
    }
    public ICollidableMesh(IVec[][] matrix, boolean triangulateDir, IMeshType creator){
	super(matrix,triangulateDir,creator); initCollidable();
    }
    
    public ICollidableMesh(IServerI s, IVec[][] matrix, int unum, int vnum, boolean triangulateDir){
	super(s,matrix,unum,vnum,triangulateDir); initCollidable();
    }
    public ICollidableMesh(IVec[][] matrix, int unum, int vnum, boolean triangulateDir){
	super(matrix,unum,vnum,triangulateDir); initCollidable();
    }
    
    public ICollidableMesh(IServerI s, IVec[][] matrix, int unum, int vnum, boolean triangulateDir,
			   IMeshType creator){
	super(s,matrix,unum,vnum,triangulateDir,creator); initCollidable();
    }
    public ICollidableMesh(IVec[][] matrix, int unum, int vnum, boolean triangulateDir, IMeshType creator){
	super(matrix,unum,vnum,triangulateDir,creator); initCollidable();
    }    
    
    public ICollidableMesh(IServerI s, ArrayList<IVertex> v, ArrayList<IEdge> e, ArrayList<IFace> f){
	super(s,v,e,f); initCollidable();
    }
    public ICollidableMesh(ArrayList<IVertex> v, ArrayList<IEdge> e, ArrayList<IFace> f){
	super(v,e,f); initCollidable();
    }
    
    public ICollidableMesh(IServerI s, IVertex[] vtx, IEdge[] edg,IFace[] fcs){
	super(s,vtx,edg,fcs); initCollidable();
    }
    public ICollidableMesh(IVertex[] vtx, IEdge[] edg,IFace[] fcs){
	super(vtx,edg,fcs); initCollidable();
    }
    
    public ICollidableMesh(IServerI s, IVec[] vert){ // single face mesh
	super(s,vert); initCollidable();
    }
    public ICollidableMesh(IVec[] vert){ super(vert); initCollidable(); }
    
    public ICollidableMesh(IServerI s, IVertex[] vert){ // single face mesh
	super(s,vert); initCollidable();
    }
    public ICollidableMesh(IVertex[] vert){ super(vert); initCollidable(); }
    
    public ICollidableMesh(IServerI s, IVertex v1, IVertex v2, IVertex v3){
	super(s,v1,v2,v3); initCollidable();
    }
    public ICollidableMesh(IVertex v1, IVertex v2, IVertex v3){ super(v1,v2,v3); initCollidable(); }
    
    public ICollidableMesh(IServerI s, IVertex v1, IVertex v2, IVertex v3, IVertex v4){
	super(s,v1,v2,v3,v4); initCollidable();
    }
    public ICollidableMesh(IVertex v1, IVertex v2, IVertex v3, IVertex v4){
	super(v1,v2,v3,v4); initCollidable();
    }
    
    public ICollidableMesh(IServerI s, IVecI v1, IVecI v2, IVecI v3){
	super(s,v1,v2,v3); initCollidable();
    }
    public ICollidableMesh(IVecI v1, IVecI v2, IVecI v3){ super(v1,v2,v3); initCollidable(); }
    
    public ICollidableMesh(IServerI s, IVecI v1, IVecI v2, IVecI v3, IVecI v4){
	super(s,v1,v2,v3,v4); initCollidable();
    }
    public ICollidableMesh(IVecI v1, IVecI v2, IVecI v3, IVecI v4){
	super(v1,v2,v3,v4); initCollidable();
    }
    
    public ICollidableMesh(IServerI s, double x1, double y1, double z1, double x2, double y2, double z2,
			   double x3, double y3, double z3){
	super(s,x1,y1,z1,x2,y2,z2,x3,y3,z3); initCollidable();
    }
    public ICollidableMesh(double x1, double y1, double z1, double x2, double y2, double z2,
			   double x3, double y3, double z3){
	super(x1,y1,z1,x2,y2,z2,x3,y3,z3); initCollidable();
    }
    
    public ICollidableMesh(IServerI s, double x1, double y1, double z1, double x2, double y2, double z2,
			   double x3, double y3, double z3, double x4, double y4, double z4){
	super(s,x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4); initCollidable();
    }
    public ICollidableMesh(double x1, double y1, double z1, double x2, double y2, double z2,
			   double x3, double y3, double z3, double x4, double y4, double z4){
	super(x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4); initCollidable();
    }
    
    public ICollidableMesh(IServerI s, IFace[] fcs){ super(s,fcs); initCollidable(); }
    public ICollidableMesh(IFace[] fcs){ super(fcs); initCollidable(); }
    
    
    synchronized public ICollidableMesh dup(){ return new ICollidableMesh(this); }
    
    synchronized public void initCollidable(){
	mesh.triangulateAll(true, new IMeshType());
	collidables = new ArrayList<ITriangleWall>();
	for(int i=0; i<mesh.faceNum(); i++){
	    IFace f = mesh.face(i);
	    if(f.vertexNum()!=3){
		IOut.err("mesh face is not triangulated");
	    }
	    collidables.add(new ITriangleWall(f.vertex(0).pos(),f.vertex(1).pos(),f.vertex(2).pos()));
	}
    }
    
    synchronized public void initCollidable(ICollidableMesh m){
	// correct?
	collidables = new ArrayList<ITriangleWall>();
	for(int i=0; i<mesh.faceNum(); i++){
	    IFace f = mesh.face(i);
	    if(f.vertexNum()!=3){
		IOut.err("mesh face is not triangulated");
	    }
	    ITriangleWall wall = new ITriangleWall(f.vertex(0).pos(),f.vertex(1).pos(),f.vertex(2).pos());
	    wall.elasticity = m.collidables.get(i).elasticity;
	    wall.friction = m.collidables.get(i).friction;
	    
	    if(m.collidables.get(i).targetClasses!=null){
		wall.targetClasses = new ArrayList<Class<? extends IParticleI>>();
		for(int j=0; j<m.collidables.get(i).targetClasses.size(); j++){
		    wall.targetClasses.add(m.collidables.get(i).targetClasses.get(j));
		}
	    }
	    collidables.add(wall);
	}
    }
    
    
    public ICollidableMesh fric(double friction){
	for(int i=0; i<collidables.size(); i++){ collidables.get(i).fric(friction); }
	return this;
    }
    public ICollidableMesh friction(double friction){ return fric(friction); }
    
    public ICollidableMesh elast(double elasticity){
	for(int i=0; i<collidables.size(); i++){ collidables.get(i).elast(elasticity); }
	return this;
    }
    public ICollidableMesh elasticity(double elasticity){ return elast(elasticity); }
    
    
    
    
    // necessary? shold this be here?
    synchronized public ICollidableMesh addFace(IFace f){ super.addFace(f); return this; }
    synchronized public ICollidableMesh addFace(IFace f, boolean checkExistingVertex, boolean checkExistingEdge, boolean checkExistingFace){
	super.addFace(f,checkExistingVertex,checkExistingEdge,checkExistingFace); return this;
    }
    
    // OpenGL way of adding mesh faces
    synchronized public ICollidableMesh addTriangles(IVertex[] v){ super.addTriangles(v); return this; }
    synchronized public ICollidableMesh addTriangles(IVec[] v){ super.addTriangles(v); return this; }
    synchronized public ICollidableMesh addQuads(IVertex[] v){ super.addQuads(v); return this; }
    synchronized public ICollidableMesh addQuads(IVec[] v){ super.addQuads(v); return this; }
    synchronized public ICollidableMesh addPolygon(IVertex[] v){ super.addPolygon(v);  return this; }
    synchronized public ICollidableMesh addPolygon(IVec[] v){ super.addPolygon(v); return this; }
    synchronized public ICollidableMesh addTriangleStrip(IVertex[] v){ super.addTriangleStrip(v); return this; }
    synchronized public ICollidableMesh addTriangleStrip(IVec[] v){ super.addTriangleStrip(v); return this; }
    synchronized public ICollidableMesh addQuadStrip(IVertex[] v){ super.addQuadStrip(v); return this; }
    synchronized public ICollidableMesh addQuadStrip(IVec[] v){ super.addQuadStrip(v); return this; }
    synchronized public ICollidableMesh addTriangleFan(IVertex[] v){ super.addTriangleFan(v); return this; }
    synchronized public ICollidableMesh addTriangleFan(IVec[] v){ super.addTriangleFan(v); return this; }
    
    synchronized public ICollidableMesh name(String nm){ super.name(nm); return this; }
    synchronized public ICollidableMesh layer(ILayer l){ super.layer(l); return this; }
    synchronized public ICollidableMesh layer(String l){ super.layer(l); return this; }

    synchronized public ICollidableMesh attr(IAttribute at){ super.attr(at); return this; }

        
    synchronized public ICollidableMesh hide(){ super.hide(); return this; }
    synchronized public ICollidableMesh show(){ super.show(); return this; }
    
    synchronized public ICollidableMesh clr(Color c){ super.clr(c); return this; }
    synchronized public ICollidableMesh clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public ICollidableMesh clr(int gray){ super.clr(gray); return this; }
    synchronized public ICollidableMesh clr(float fgray){ super.clr(fgray); return this; }
    synchronized public ICollidableMesh clr(double dgray){ super.clr(dgray); return this; }
    synchronized public ICollidableMesh clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    synchronized public ICollidableMesh clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    synchronized public ICollidableMesh clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    synchronized public ICollidableMesh clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    synchronized public ICollidableMesh clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    synchronized public ICollidableMesh clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    synchronized public ICollidableMesh clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    synchronized public ICollidableMesh clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    synchronized public ICollidableMesh clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    synchronized public ICollidableMesh hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    synchronized public ICollidableMesh hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    synchronized public ICollidableMesh hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    synchronized public ICollidableMesh hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    synchronized public ICollidableMesh setColor(Color c){ super.setColor(c); return this; }
    synchronized public ICollidableMesh setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public ICollidableMesh setColor(int gray){ super.setColor(gray); return this; }
    synchronized public ICollidableMesh setColor(float fgray){ super.setColor(fgray); return this; }
    synchronized public ICollidableMesh setColor(double dgray){ super.setColor(dgray); return this; }
    synchronized public ICollidableMesh setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    synchronized public ICollidableMesh setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    synchronized public ICollidableMesh setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    synchronized public ICollidableMesh setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    synchronized public ICollidableMesh setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    synchronized public ICollidableMesh setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    synchronized public ICollidableMesh setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    synchronized public ICollidableMesh setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    synchronized public ICollidableMesh setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    synchronized public ICollidableMesh setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public ICollidableMesh setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public ICollidableMesh setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    synchronized public ICollidableMesh setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    synchronized public ICollidableMesh weight(double w){ super.weight(w); return this; }
    synchronized public ICollidableMesh weight(float w){ super.weight(w); return this; }
    
    /** only setting value to closed. checking no connection of mesh */
    synchronized public ICollidableMesh close(){ mesh.close(); return this; }
    
    synchronized public ICollidableMesh deleteVertex(int i){ super.deleteVertex(i); return this; }
    synchronized public ICollidableMesh deleteEdge(int i){ super.deleteEdge(i); return this; }
    synchronized public ICollidableMesh deleteFace(int i){ super.deleteFace(i); return this; }
    
    synchronized public ICollidableMesh deleteVertex(IIntegerI i){ super.deleteVertex(i); return this; }
    synchronized public ICollidableMesh deleteEdge(IIntegerI i){ super.deleteEdge(i); return this; }
    synchronized public ICollidableMesh deleteFace(IIntegerI i){ super.deleteFace(i); return this; }
    
    synchronized public ICollidableMesh deleteVertex(IVertex v){ super.deleteVertex(v); return this; }
    synchronized public ICollidableMesh deleteEdge(IEdge e){ super.deleteEdge(e); return this; }
    synchronized public ICollidableMesh deleteFace(IFace f){ super.deleteFace(f); return this; }
    
    
    /*******************************************************
     * ITransformable methods
     ******************************************************/
    
    synchronized public ICollidableMesh add(double x, double y, double z){ super.add(x,y,z); return this; }
    synchronized public ICollidableMesh add(IDoubleI x, IDoubleI y, IDoubleI z){ super.add(x,y,z); return this; }
    synchronized public ICollidableMesh add(IVecI v){ super.add(v); return this; }
    synchronized public ICollidableMesh sub(double x, double y, double z){ super.sub(x,y,z); return this; }
    synchronized public ICollidableMesh sub(IDoubleI x, IDoubleI y, IDoubleI z){ super.sub(x,y,z); return this; }
    synchronized public ICollidableMesh sub(IVecI v){ super.sub(v); return this; }
    synchronized public ICollidableMesh mul(IDoubleI v){ super.mul(v); return this; }
    synchronized public ICollidableMesh mul(double v){ super.mul(v); return this; }
    synchronized public ICollidableMesh div(IDoubleI v){ super.div(v); return this; }
    synchronized public ICollidableMesh div(double v){ super.div(v); return this; }
    
    synchronized public ICollidableMesh neg(){ super.neg(); return this; }
    /** alias of neg */
    synchronized public ICollidableMesh rev(){ return neg(); }
    /** alias of neg */
    synchronized public ICollidableMesh flip(){ return neg(); }
    
    
    /** scale add */
    synchronized public ICollidableMesh add(IVecI v, double f){ super.add(v,f); return this; }
    /** scale add */
    synchronized public ICollidableMesh add(IVecI v, IDoubleI f){ super.add(v,f); return this; }
    /** scale add alias */
    synchronized public ICollidableMesh add(double f, IVecI v){ return add(v,f); }
    /** scale add alias */
    synchronized public ICollidableMesh add(IDoubleI f, IVecI v){ return add(v,f); }
    
    /** rotation around z-axis and origin */
    synchronized public ICollidableMesh rot(IDoubleI angle){ super.rot(angle); return this; }
    synchronized public ICollidableMesh rot(double angle){ super.rot(angle); return this; }
    
    /** rotation around axis vector */
    synchronized public ICollidableMesh rot(IVecI axis, IDoubleI angle){ super.rot(axis,angle); return this; }
    synchronized public ICollidableMesh rot(IVecI axis, double angle){ super.rot(axis,angle); return this; }
    
    /** rotation around axis vector and center */
    synchronized public ICollidableMesh rot(IVecI center, IVecI axis, IDoubleI angle){ super.rot(center,axis,angle); return this; }
    synchronized public ICollidableMesh rot(IVecI center, IVecI axis, double angle){ super.rot(center,axis,angle); return this; }
    
    /** rotate to destination direction vector */
    synchronized public ICollidableMesh rot(IVecI axis, IVecI destDir){ super.rot(axis,destDir); return this; }
    /** rotate to destination point location */
    synchronized public ICollidableMesh rot(IVecI center, IVecI axis, IVecI destPt){ super.rot(center,axis,destPt); return this; }
    
    
    /** rotation on xy-plane around origin; same with rot(IDoubleI) */
    synchronized public ICollidableMesh rot2(IDoubleI angle){ super.rot2(angle); return this; }
    /** rotation on xy-plane around origin; same with rot(double) */
    synchronized public ICollidableMesh rot2(double angle){ super.rot2(angle); return this; }
    
    /** rotation on xy-plane around center */
    synchronized public ICollidableMesh rot2(IVecI center, IDoubleI angle){ super.rot2(center,angle); return this; }
    synchronized public ICollidableMesh rot2(IVecI center, double angle){ super.rot2(center,angle); return this; }
    
    /** rotation on xy-plane to destination direction vector */
    synchronized public ICollidableMesh rot2(IVecI destDir){ super.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */
    synchronized public ICollidableMesh rot2(IVecI center, IVecI destPt){ super.rot2(center,destPt); return this; }
        
    
    /** alias of mul */
    synchronized public ICollidableMesh scale(IDoubleI f){ return mul(f); }
    synchronized public ICollidableMesh scale(double f){ return mul(f); }
    synchronized public ICollidableMesh scale(IVecI center, IDoubleI f){ super.scale(center,f); return this; }
    synchronized public ICollidableMesh scale(IVecI center, double f){ super.scale(center,f); return this; }

    
    /** scale only in 1 direction */
    synchronized public ICollidableMesh scale1d(IVecI axis, double f){ super.scale1d(axis,f); return this; }
    synchronized public ICollidableMesh scale1d(IVecI axis, IDoubleI f){ super.scale1d(axis,f); return this; }
    synchronized public ICollidableMesh scale1d(IVecI center, IVecI axis, double f){ super.scale1d(center,axis,f); return this; }
    synchronized public ICollidableMesh scale1d(IVecI center, IVecI axis, IDoubleI f){ super.scale1d(center,axis,f); return this; }
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    synchronized public ICollidableMesh ref(IVecI planeDir){ super.ref(planeDir); return this; }
    synchronized public ICollidableMesh ref(IVecI center, IVecI planeDir){ super.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    synchronized public ICollidableMesh mirror(IVecI planeDir){ return ref(planeDir); }
    synchronized public ICollidableMesh mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    synchronized public ICollidableMesh shear(double sxy, double syx, double syz,
		       double szy, double szx, double sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public ICollidableMesh shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public ICollidableMesh shear(IVecI center, double sxy, double syx, double syz,
		       double szy, double szx, double sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public ICollidableMesh shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	super.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    synchronized public ICollidableMesh shearXY(double sxy, double syx){ super.shearXY(sxy,syx); return this; }
    synchronized public ICollidableMesh shearXY(IDoubleI sxy, IDoubleI syx){ super.shearXY(sxy,syx); return this; }
    synchronized public ICollidableMesh shearXY(IVecI center, double sxy, double syx){ super.shearXY(center,sxy,syx); return this; }
    synchronized public ICollidableMesh shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){ super.shearXY(center,sxy,syx); return this; }
    
    synchronized public ICollidableMesh shearYZ(double syz, double szy){ super.shearYZ(syz,szy); return this; }
    synchronized public ICollidableMesh shearYZ(IDoubleI syz, IDoubleI szy){ super.shearYZ(syz,szy); return this; }
    synchronized public ICollidableMesh shearYZ(IVecI center, double syz, double szy){ super.shearYZ(center,syz,szy); return this; }
    synchronized public ICollidableMesh shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){ super.shearYZ(center,syz,szy); return this; }
    
    synchronized public ICollidableMesh shearZX(double szx, double sxz){ super.shearZX(szx,sxz); return this; }
    synchronized public ICollidableMesh shearZX(IDoubleI szx, IDoubleI sxz){ super.shearZX(szx,sxz); return this; }
    synchronized public ICollidableMesh shearZX(IVecI center, double szx, double sxz){ super.shearZX(center,szx,sxz); return this; }
    synchronized public ICollidableMesh shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){ super.shearZX(center,szx,sxz); return this; }
    
    /** mv() is alias of add() */
    synchronized public ICollidableMesh mv(double x, double y, double z){ return add(x,y,z); }
    synchronized public ICollidableMesh mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    synchronized public ICollidableMesh mv(IVecI v){ return add(v); }
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    synchronized public ICollidableMesh cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    synchronized public ICollidableMesh cp(double x, double y, double z){ return dup().add(x,y,z); }
    synchronized public ICollidableMesh cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    synchronized public ICollidableMesh cp(IVecI v){ return dup().add(v); }
    
    
    /** translate() is alias of add() */
    synchronized public ICollidableMesh translate(double x, double y, double z){ super.translate(x,y,z); return this; }
    synchronized public ICollidableMesh translate(IDoubleI x, IDoubleI y, IDoubleI z){ super.translate(x,y,z); return this; }
    synchronized public ICollidableMesh translate(IVecI v){ super.translate(v); return this; }
    
    
    synchronized public ICollidableMesh transform(IMatrix3I mat){ super.transform(mat); return this; }
    synchronized public ICollidableMesh transform(IMatrix4I mat){ super.transform(mat); return this; }
    synchronized public ICollidableMesh transform(IVecI xvec, IVecI yvec, IVecI zvec){ super.transform(xvec,yvec,zvec); return this; }
    synchronized public ICollidableMesh transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){ super.transform(xvec,yvec,zvec,translate); return this; }
    
    
}
