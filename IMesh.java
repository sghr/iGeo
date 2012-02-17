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

import java.util.ArrayList;
import java.awt.Color;

import igeo.gui.*;

/**
   Class of polygon mesh object.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IMesh extends IObject implements IMeshI{
    
    public IMeshGeo mesh;
    //public IMeshI mesh;
    
    public IMesh(){ super(); mesh=new IMeshGeo(); initMesh(null); }
    public IMesh(IServerI s){ super(s); mesh=new IMeshGeo(); initMesh(null); }
    
    public IMesh(IMeshGeo m){ super(); mesh=m; initMesh(null); }
    public IMesh(IServerI s, IMeshGeo m){ super(s); mesh=m; initMesh(s); }
    
    public IMesh(IMesh m){ super(m); mesh=m.mesh.dup(); initMesh(m.server); }
    public IMesh(IServerI s, IMesh m){ super(s,m); mesh=m.mesh.dup(); initMesh(s); }
    
    public IMesh(IServerI s, ArrayList<ICurveI> lines){
	super(s); mesh = new IMeshGeo(lines); initMesh(s);
    }
    public IMesh(ArrayList<ICurveI> lines){ this((IServerI)null,lines); }
    
    public IMesh(IServerI s, ArrayList<ICurveI> lines, IMeshType creator){
        super(s); mesh = new IMeshGeo(lines,creator); initMesh(s);
    }
    public IMesh(ArrayList<ICurveI> lines, IMeshType creator){
	this((IServerI)null,lines,creator);
    }
    
    public IMesh(IServerI s, IVec[][] matrix){
	super(s); mesh = new IMeshGeo(matrix); initMesh(s);
    }
    public IMesh(IVec[][] matrix){ this((IServerI)null, matrix); }
    
    public IMesh(IServerI s, IVec[][] matrix, boolean triangulateDir){
	super(s); mesh = new IMeshGeo(matrix,triangulateDir); initMesh(s);
    }
    public IMesh(IVec[][] matrix, boolean triangulateDir){
	this((IServerI)null, matrix,triangulateDir);
    }
    
    public IMesh(IServerI s, IVec[][] matrix, boolean triangulateDir, IMeshType creator){
	super(s); mesh = new IMeshGeo(matrix,triangulateDir,creator); initMesh(s);
    }
    public IMesh(IVec[][] matrix, boolean triangulateDir, IMeshType creator){
	this((IServerI)null, matrix,triangulateDir,creator);
    }
    
    public IMesh(IServerI s, IVec[][] matrix, int unum, int vnum, boolean triangulateDir){
	super(s); mesh = new IMeshGeo(matrix,unum,vnum,triangulateDir); initMesh(s);
    }
    public IMesh(IVec[][] matrix, int unum, int vnum, boolean triangulateDir){
	this((IServerI)null, matrix,unum,vnum,triangulateDir);
    }
    
    public IMesh(IServerI s, IVec[][] matrix, int unum, int vnum, boolean triangulateDir,
		 IMeshType creator){
	super(s); mesh = new IMeshGeo(matrix,unum,vnum,triangulateDir,creator); initMesh(s);
    }
    public IMesh(IVec[][] matrix, int unum, int vnum, boolean triangulateDir, IMeshType creator){
	this((IServerI)null, matrix,unum,vnum,triangulateDir,creator);
    }    
    
    public IMesh(IServerI s, ArrayList<IVertex> v, ArrayList<IEdge> e, ArrayList<IFace> f){
	super(s); mesh = new IMeshGeo(v,e,f); initMesh(s);
    }
    public IMesh(ArrayList<IVertex> v, ArrayList<IEdge> e, ArrayList<IFace> f){
	this((IServerI)null,v,e,f);
    }
    
    public IMesh(IServerI s, IVertex[] vtx, IEdge[] edg,IFace[] fcs){
	super(s); mesh = new IMeshGeo(vtx,edg,fcs); 
    }
    public IMesh(IVertex[] vtx, IEdge[] edg,IFace[] fcs){ this((IServerI)null,vtx,edg,fcs); }
    
    public IMesh(IServerI s, IVec[] vert){ // single face mesh
	super(s); mesh = new IMeshGeo(vert); initMesh(s);
    }
    public IMesh(IVec[] vert){ this((IServerI)null,vert); }
    
    public IMesh(IServerI s, IVertex[] vert){ // single face mesh
	super(s); mesh = new IMeshGeo(vert); initMesh(s);
    }
    public IMesh(IVertex[] vert){ this((IServerI)null,vert); }
    
    public IMesh(IServerI s, IVertex v1, IVertex v2, IVertex v3){
	super(s); mesh = new IMeshGeo(v1,v2,v3); initMesh(s);
    }
    public IMesh(IVertex v1, IVertex v2, IVertex v3){ this((IServerI)null,v1,v2,v3); }
    
    public IMesh(IServerI s, IVertex v1, IVertex v2, IVertex v3, IVertex v4){
	super(s); mesh = new IMeshGeo(v1,v2,v3,v4); initMesh(s);
    }
    public IMesh(IVertex v1, IVertex v2, IVertex v3, IVertex v4){
	this((IServerI)null,v1,v2,v3,v4);
    }

    public IMesh(IServerI s, IVecI v1, IVecI v2, IVecI v3){
	super(s); mesh = new IMeshGeo(v1,v2,v3); initMesh(s);
    }
    public IMesh(IVecI v1, IVecI v2, IVecI v3){ this((IServerI)null,v1,v2,v3); }
    
    public IMesh(IServerI s, IVecI v1, IVecI v2, IVecI v3, IVecI v4){
	super(s); mesh = new IMeshGeo(v1,v2,v3,v4); initMesh(s);
    }
    public IMesh(IVecI v1, IVecI v2, IVecI v3, IVecI v4){
	this((IServerI)null,v1,v2,v3,v4);
    }
    
    public IMesh(IServerI s, double x1, double y1, double z1, double x2, double y2, double z2,
		 double x3, double y3, double z3){
	super(s); mesh = new IMeshGeo(x1,y1,z1,x2,y2,z2,x3,y3,z3); initMesh(s);
    }
    public IMesh(double x1, double y1, double z1, double x2, double y2, double z2,
		 double x3, double y3, double z3){
	this(null,x1,y1,z1,x2,y2,z2,x3,y3,z3);
    }
    
    public IMesh(IServerI s, double x1, double y1, double z1, double x2, double y2, double z2,
		 double x3, double y3, double z3, double x4, double y4, double z4){
	super(s); mesh = new IMeshGeo(x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4); initMesh(s);
    }
    public IMesh(double x1, double y1, double z1, double x2, double y2, double z2,
		 double x3, double y3, double z3, double x4, double y4, double z4){
	this(null,x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4);
    }
    
    
    public IMesh(IServerI s, IFace[] fcs){ super(s); mesh = new IMeshGeo(fcs); initMesh(s); }
    public IMesh(IFace[] fcs){ this((IServerI)null,fcs); }
    
    public IMeshGeo get(){ return mesh; }
    
    public IMesh dup(){ return new IMesh(this); }
    
    public boolean isValid(){ if(mesh==null){ return false; } return mesh.isValid(); }
    
    
    public void initMesh(IServerI s){
	parameter = mesh;
	if(graphics==null) initGraphic(s);
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
        if(m.isGL()) return new IMeshGraphicGL(this); 
        return null;
    }
    
    public int vertexNum(){ return mesh.vertexNum(); }
    public int edgeNum(){ return mesh.edgeNum(); }
    public int faceNum(){ return mesh.faceNum(); }
    
    public int vertexNum(ISwitchE e){ return mesh.vertexNum(e); }
    public int edgeNum(ISwitchE e){ return mesh.edgeNum(e); }
    public int faceNum(ISwitchE e){ return mesh.faceNum(e); }
    
    public IIntegerI vertexNum(ISwitchR r){ return mesh.vertexNum(r); }
    public IIntegerI edgeNum(ISwitchR r){ return mesh.edgeNum(r); }
    public IIntegerI faceNum(ISwitchR r){ return mesh.faceNum(r); }
    
    public IVertex vertex(int i){ return mesh.vertex(i); }
    public IEdge edge(int i){ return mesh.edge(i); }
    public IFace face(int i){ return mesh.face(i); }
    
    public IVertex vertex(IIntegerI i){ return mesh.vertex(i); }
    public IEdge edge(IIntegerI i){ return mesh.edge(i); }
    public IFace face(IIntegerI i){ return mesh.face(i); }
    
    
    // necessary? shold this be here?
    public IMesh addFace(IFace f){ mesh.addFace(f); return this; }
    
    
    
    public IMesh name(String nm){ super.name(nm); return this; }
    public IMesh layer(ILayer l){ super.layer(l); return this; }
        
    public IMesh hide(){ super.hide(); return this; }
    public IMesh show(){ super.show(); return this; }
    
    public IMesh clr(Color c){ super.clr(c); return this; }
    public IMesh clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IMesh clr(int gray){ super.clr(gray); return this; }
    public IMesh clr(float fgray){ super.clr(fgray); return this; }
    public IMesh clr(double dgray){ super.clr(dgray); return this; }
    public IMesh clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IMesh clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IMesh clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IMesh clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IMesh clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IMesh clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IMesh clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IMesh clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IMesh clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IMesh hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IMesh hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IMesh hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IMesh hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IMesh setColor(Color c){ super.setColor(c); return this; }
    public IMesh setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IMesh setColor(int gray){ super.setColor(gray); return this; }
    public IMesh setColor(float fgray){ super.setColor(fgray); return this; }
    public IMesh setColor(double dgray){ super.setColor(dgray); return this; }
    public IMesh setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IMesh setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IMesh setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IMesh setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IMesh setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IMesh setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IMesh setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IMesh setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IMesh setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IMesh setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IMesh setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IMesh setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IMesh setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    


    /*************************************************************************************
     * static mesh geometry operations
     ************************************************************************************/

    /**
       extract all points and connect if it's at same location and create a new mesh.
    */
    public static IMesh join(IMesh[] meshes){
	
	ArrayList<IVertex> vertices = new ArrayList<IVertex>();
	ArrayList<IEdge> edges = new ArrayList<IEdge>();
	ArrayList<IFace> faces = new ArrayList<IFace>();
	
	for(IMesh m : meshes){
	    for(int i=0; i<m.vertexNum(); i++) vertices.add(m.vertex(i));
	    for(int i=0; i<m.edgeNum(); i++) edges.add(m.edge(i));
	    for(int i=0; i<m.faceNum(); i++) faces.add(m.face(i));

	    m.del(); // should it be deleted?
	}
	
	for(int i=0; i<vertices.size(); i++){
	    IVertex v1 = vertices.get(i);
	    for(int j=i+1; j<vertices.size(); j++){
		IVertex v2 = vertices.get(j);
		if(v1 != v2){
		    if(v1.eq(v2)){
			v2.replaceVertex(v1);
			vertices.set(j,v1);
		    }
		}
	    }
	}
	
	
	
	return new IMesh(vertices, edges, faces);
    }
    
    
    /** only setting value to closed. checking no connection of mesh */
    public IMesh close(){ mesh.close(); return this; }
    public boolean isClosed(){ return mesh.isClosed(); }
    
    
    /*******************************************************
     * ITransformable methods
     ******************************************************/
    
    public IMesh add(double x, double y, double z){ mesh.add(x,y,z); return this; }
    public IMesh add(IDoubleI x, IDoubleI y, IDoubleI z){ mesh.add(x,y,z); return this; }
    public IMesh add(IVecI v){ mesh.add(v); return this; }
    public IMesh sub(double x, double y, double z){ mesh.sub(x,y,z); return this; }
    public IMesh sub(IDoubleI x, IDoubleI y, IDoubleI z){ mesh.sub(x,y,z); return this; }
    public IMesh sub(IVecI v){ mesh.sub(v); return this; }
    public IMesh mul(IDoubleI v){ mesh.mul(v); return this; }
    public IMesh mul(double v){ mesh.mul(v); return this; }
    public IMesh div(IDoubleI v){ mesh.div(v); return this; }
    public IMesh div(double v){ mesh.div(v); return this; }
    
    public IMesh neg(){ mesh.neg(); return this; }
    /** alias of neg */
    public IMesh rev(){ return neg(); }
    /** alias of neg */
    public IMesh flip(){ return neg(); }
    
    
    /** scale add */
    public IMesh add(IVecI v, double f){ mesh.add(v,f); return this; }
    /** scale add */
    public IMesh add(IVecI v, IDoubleI f){ mesh.add(v,f); return this; }
    /** scale add alias */
    public IMesh add(double f, IVecI v){ return add(v,f); }
    /** scale add alias */
    public IMesh add(IDoubleI f, IVecI v){ return add(v,f); }
    
    /** rotation around z-axis and origin */
    public IMesh rot(IDoubleI angle){ mesh.rot(angle); return this; }
    public IMesh rot(double angle){ mesh.rot(angle); return this; }
    
    /** rotation around axis vector */
    public IMesh rot(IVecI axis, IDoubleI angle){ mesh.rot(axis,angle); return this; }
    public IMesh rot(IVecI axis, double angle){ mesh.rot(axis,angle); return this; }
    
    /** rotation around axis vector and center */
    public IMesh rot(IVecI center, IVecI axis, IDoubleI angle){ mesh.rot(center,axis,angle); return this; }
    public IMesh rot(IVecI center, IVecI axis, double angle){ mesh.rot(center,axis,angle); return this; }
    
    /** rotate to destination direction vector */
    public IMesh rot(IVecI axis, IVecI destDir){ mesh.rot(axis,destDir); return this; }
    /** rotate to destination point location */
    public IMesh rot(IVecI center, IVecI axis, IVecI destPt){ mesh.rot(center,axis,destPt); return this; }
    
    
    /** rotation on xy-plane around origin; same with rot(IDoubleI) */
    public IMesh rot2(IDoubleI angle){ mesh.rot2(angle); return this; }
    /** rotation on xy-plane around origin; same with rot(double) */
    public IMesh rot2(double angle){ mesh.rot2(angle); return this; }
    
    /** rotation on xy-plane around center */
    public IMesh rot2(IVecI center, IDoubleI angle){ mesh.rot2(center,angle); return this; }
    public IMesh rot2(IVecI center, double angle){ mesh.rot2(center,angle); return this; }
    
    /** rotation on xy-plane to destination direction vector */
    public IMesh rot2(IVecI destDir){ mesh.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */
    public IMesh rot2(IVecI center, IVecI destPt){ mesh.rot2(center,destPt); return this; }
        
    
    /** alias of mul */
    public IMesh scale(IDoubleI f){ return mul(f); }
    public IMesh scale(double f){ return mul(f); }
    public IMesh scale(IVecI center, IDoubleI f){ mesh.scale(center,f); return this; }
    public IMesh scale(IVecI center, double f){ mesh.scale(center,f); return this; }

    
    /** scale only in 1 direction */
    public IMesh scale1d(IVecI axis, double f){ mesh.scale1d(axis,f); return this; }
    public IMesh scale1d(IVecI axis, IDoubleI f){ mesh.scale1d(axis,f); return this; }
    public IMesh scale1d(IVecI center, IVecI axis, double f){ mesh.scale1d(center,axis,f); return this; }
    public IMesh scale1d(IVecI center, IVecI axis, IDoubleI f){ mesh.scale1d(center,axis,f); return this; }
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IMesh ref(IVecI planeDir){ mesh.ref(planeDir); return this; }
    public IMesh ref(IVecI center, IVecI planeDir){ mesh.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    public IMesh mirror(IVecI planeDir){ return ref(planeDir); }
    public IMesh mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    public IMesh shear(double sxy, double syx, double syz,
		       double szy, double szx, double sxz){
	mesh.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IMesh shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	mesh.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IMesh shear(IVecI center, double sxy, double syx, double syz,
		       double szy, double szx, double sxz){
	mesh.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IMesh shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	mesh.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    public IMesh shearXY(double sxy, double syx){ mesh.shearXY(sxy,syx); return this; }
    public IMesh shearXY(IDoubleI sxy, IDoubleI syx){ mesh.shearXY(sxy,syx); return this; }
    public IMesh shearXY(IVecI center, double sxy, double syx){ mesh.shearXY(center,sxy,syx); return this; }
    public IMesh shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){ mesh.shearXY(center,sxy,syx); return this; }
    
    public IMesh shearYZ(double syz, double szy){ mesh.shearYZ(syz,szy); return this; }
    public IMesh shearYZ(IDoubleI syz, IDoubleI szy){ mesh.shearYZ(syz,szy); return this; }
    public IMesh shearYZ(IVecI center, double syz, double szy){ mesh.shearYZ(center,syz,szy); return this; }
    public IMesh shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){ mesh.shearYZ(center,syz,szy); return this; }
    
    public IMesh shearZX(double szx, double sxz){ mesh.shearZX(szx,sxz); return this; }
    public IMesh shearZX(IDoubleI szx, IDoubleI sxz){ mesh.shearZX(szx,sxz); return this; }
    public IMesh shearZX(IVecI center, double szx, double sxz){ mesh.shearZX(center,szx,sxz); return this; }
    public IMesh shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){ mesh.shearZX(center,szx,sxz); return this; }
    
    /** mv() is alias of add() */
    public IMesh mv(double x, double y, double z){ return add(x,y,z); }
    public IMesh mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IMesh mv(IVecI v){ return add(v); }
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IMesh cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IMesh cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IMesh cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IMesh cp(IVecI v){ return dup().add(v); }
    
    
    /** translate() is alias of add() */
    public IMesh translate(double x, double y, double z){ return add(x,y,z); }
    public IMesh translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IMesh translate(IVecI v){ return add(v); }
    
    
    public IMesh transform(IMatrix3I mat){ mesh.transform(mat); return this; }
    public IMesh transform(IMatrix4I mat){ mesh.transform(mat); return this; }
    public IMesh transform(IVecI xvec, IVecI yvec, IVecI zvec){ mesh.transform(xvec,yvec,zvec); return this; }
    public IMesh transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){ mesh.transform(xvec,yvec,zvec,translate); return this; }
    
    
}
