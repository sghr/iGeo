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
   Class of polygon mesh object.
   
   @author Satoru Sugihara
*/
public class IMesh extends IGeometry implements IMeshI{
    
    public IMeshGeo mesh;
    //public IMeshI mesh;
    
    public IMesh(){ super(); mesh=new IMeshGeo(); initMesh(null); }
    public IMesh(IServerI s){ super(s); mesh=new IMeshGeo(); initMesh(s); }
    
    public IMesh(IMeshGeo m){ super(); mesh=m; initMesh(null); }
    public IMesh(IServerI s, IMeshGeo m){ super(s); mesh=m; initMesh(s); }
    
    public IMesh(IMesh m){
	super(m); mesh=m.mesh.dup(); initMesh(m.server); if(m.attr()!=null) attr(m.attr().dup()); 
    }
    public IMesh(IServerI s, IMesh m){ super(s,m); mesh=m.mesh.dup(); initMesh(s); }
    
    
    //public IMesh(IServerI s, ArrayList<ICurveI> lines){ super(s); mesh = new IMeshGeo(lines); initMesh(s); }
    //public IMesh(ArrayList<ICurveI> lines){ this((IServerI)null,lines); }
    
    public IMesh(IServerI s, ICurveI[] lines){
	super(s); mesh = new IMeshGeo(lines); 
	if(lines!=null && lines.length>0 && lines[0] instanceof IObject){
	    if(((IObject)lines[0]).attr()!=null) attr(((IObject)lines[0]).attr().dup());
	}
	initMesh(s);
    }
    public IMesh(ICurveI[] lines){ this((IServerI)null,lines); }
    
    //public IMesh(IServerI s, ArrayList<ICurveI> lines, IMeshType creator){ super(s); mesh = new IMeshGeo(lines,creator); initMesh(s); }
    //public IMesh(ArrayList<ICurveI> lines, IMeshType creator){ this((IServerI)null,lines,creator); }
    
    /** each input surface builds one 3 or 4 point face. trim is ignored. */
    public IMesh(IServerI s, ISurfaceI[] faces){
	super(s); mesh = new IMeshGeo(faces); 
	if(faces!=null && faces.length>0 && faces[0] instanceof IObject){
	    if(((IObject)faces[0]).attr()!=null) attr(((IObject)faces[0]).attr().dup());
	}
	initMesh(s);
    }
    /** each input surface builds one 3 or 4 point face. trim is ignored. */
    public IMesh(ISurfaceI[] faces){ this((IServerI)null,faces); }
    
    
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
	super(s); mesh = new IMeshGeo(vtx,edg,fcs); initMesh(s);
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
    
    
    synchronized public IMeshGeo get(){ return mesh; }
    
    synchronized public IMesh dup(){ return new IMesh(this); }
    
    synchronized public boolean isValid(){ if(mesh==null){ return false; } return mesh.isValid(); }
    
    
    synchronized public void initMesh(IServerI s){
	parameter = mesh;
	if(graphics==null) initGraphic(s);
    }
    
    synchronized public IGraphicObject createGraphic(IGraphicMode m){
	if(m.isNone()) return null;
        if(m.isGraphic3D()) return new IMeshGraphicGL(this); 
        return null;
    }
    
    synchronized public int vertexNum(){ return mesh.vertexNum(); }
    synchronized public int edgeNum(){ return mesh.edgeNum(); }
    synchronized public int faceNum(){ return mesh.faceNum(); }
    
    synchronized public int vertexNum(ISwitchE e){ return mesh.vertexNum(e); }
    synchronized public int edgeNum(ISwitchE e){ return mesh.edgeNum(e); }
    synchronized public int faceNum(ISwitchE e){ return mesh.faceNum(e); }
    
    synchronized public IIntegerI vertexNum(ISwitchR r){ return mesh.vertexNum(r); }
    synchronized public IIntegerI edgeNum(ISwitchR r){ return mesh.edgeNum(r); }
    synchronized public IIntegerI faceNum(ISwitchR r){ return mesh.faceNum(r); }
    
    synchronized public IVertex vertex(int i){ return mesh.vertex(i); }
    synchronized public IEdge edge(int i){ return mesh.edge(i); }
    synchronized public IFace face(int i){ return mesh.face(i); }
    
    synchronized public IVertex vertex(IIntegerI i){ return mesh.vertex(i); }
    synchronized public IEdge edge(IIntegerI i){ return mesh.edge(i); }
    synchronized public IFace face(IIntegerI i){ return mesh.face(i); }
    
    /** return all vertices */
    synchronized public ArrayList<IVertex> vertices(){ return mesh.vertices(); }
    /** return all edges */
    synchronized public ArrayList<IEdge> edges(){ return mesh.edges(); }
    /** return all faces */
    synchronized public ArrayList<IFace> faces(){ return mesh.faces(); }
    
    
    /** center of mesh, calculated by average of all vertices */
    synchronized public IVec center(){ return mesh.center(); }
    
    
    // necessary? shold this be here?
    synchronized public IMesh addFace(IFace f){ mesh.addFace(f); return this; }
    synchronized public IMesh addFace(IFace f, boolean checkExistingVertex, boolean checkExistingEdge, boolean checkExistingFace){ mesh.addFace(f,checkExistingVertex,checkExistingEdge,checkExistingFace); return this; }
    
    // OpenGL way of adding mesh faces
    synchronized public IMesh addTriangles(IVertex[] v){ mesh.addTriangles(v); resetGraphic(); return this; }
    synchronized public IMesh addTriangles(IVec[] v){ mesh.addTriangles(v); resetGraphic(); return this; }
    synchronized public IMesh addQuads(IVertex[] v){ mesh.addQuads(v); resetGraphic(); return this; }
    synchronized public IMesh addQuads(IVec[] v){ mesh.addQuads(v); resetGraphic(); return this; }
    synchronized public IMesh addPolygon(IVertex[] v){ mesh.addPolygon(v); resetGraphic(); return this; }
    synchronized public IMesh addPolygon(IVec[] v){ mesh.addPolygon(v); resetGraphic(); return this; }
    synchronized public IMesh addTriangleStrip(IVertex[] v){ mesh.addTriangleStrip(v); resetGraphic(); return this; }
    synchronized public IMesh addTriangleStrip(IVec[] v){ mesh.addTriangleStrip(v); resetGraphic(); return this; }
    synchronized public IMesh addQuadStrip(IVertex[] v){ mesh.addQuadStrip(v); resetGraphic(); return this; }
    synchronized public IMesh addQuadStrip(IVec[] v){ mesh.addQuadStrip(v); resetGraphic(); return this; }
    synchronized public IMesh addTriangleFan(IVertex[] v){ mesh.addTriangleFan(v); resetGraphic(); return this; }
    synchronized public IMesh addTriangleFan(IVec[] v){ mesh.addTriangleFan(v); resetGraphic(); return this; }
    
    
    // so far only for IMeshGeo and IMesh
    /** triangulate quad mesh to triangles */
    public IMesh triangulate(){ mesh.triangulate(); resetGraphic(); return this; }
    /** triangulate quad mesh to triangles */
    public IMesh triangulate(boolean triangulateDir){ mesh.triangulate(triangulateDir); resetGraphic(); return this; }
    
    /** returns naked edges of the mesh */
    public IEdge[] nakedEdges(){ return mesh.nakedEdges(); }

    
    public IMesh subdivide(){ return new IMesh(mesh.subdivide()); }
    
    
    synchronized public IMesh name(String nm){ super.name(nm); return this; }
    synchronized public IMesh layer(ILayer l){ super.layer(l); return this; }
    synchronized public IMesh layer(String l){ super.layer(l); return this; }

    synchronized public IMesh attr(IAttribute at){ super.attr(at); return this; }

        
    synchronized public IMesh hide(){ super.hide(); return this; }
    synchronized public IMesh show(){ super.show(); return this; }
    
    synchronized public IMesh clr(IColor c){ super.clr(c); return this; }
    synchronized public IMesh clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public IMesh clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    synchronized public IMesh clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    synchronized public IMesh clr(Color c){ super.clr(c); return this; }
    synchronized public IMesh clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public IMesh clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    synchronized public IMesh clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    synchronized public IMesh clr(int gray){ super.clr(gray); return this; }
    synchronized public IMesh clr(float fgray){ super.clr(fgray); return this; }
    synchronized public IMesh clr(double dgray){ super.clr(dgray); return this; }
    synchronized public IMesh clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    synchronized public IMesh clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    synchronized public IMesh clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    synchronized public IMesh clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    synchronized public IMesh clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    synchronized public IMesh clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    synchronized public IMesh clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    synchronized public IMesh clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    synchronized public IMesh clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    synchronized public IMesh hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    synchronized public IMesh hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    synchronized public IMesh hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    synchronized public IMesh hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    synchronized public IMesh setColor(IColor c){ super.setColor(c); return this; }
    synchronized public IMesh setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public IMesh setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    synchronized public IMesh setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    synchronized public IMesh setColor(Color c){ super.setColor(c); return this; }
    synchronized public IMesh setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public IMesh setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    synchronized public IMesh setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    synchronized public IMesh setColor(int gray){ super.setColor(gray); return this; }
    synchronized public IMesh setColor(float fgray){ super.setColor(fgray); return this; }
    synchronized public IMesh setColor(double dgray){ super.setColor(dgray); return this; }
    synchronized public IMesh setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    synchronized public IMesh setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    synchronized public IMesh setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    synchronized public IMesh setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    synchronized public IMesh setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    synchronized public IMesh setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    synchronized public IMesh setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    synchronized public IMesh setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    synchronized public IMesh setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    synchronized public IMesh setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public IMesh setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public IMesh setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    synchronized public IMesh setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    synchronized public IMesh weight(double w){ super.weight(w); return this; }
    synchronized public IMesh weight(float w){ super.weight(w); return this; }
    

    /*************************************************************************************
     * static mesh geometry operations
     ************************************************************************************/

    /**
       extract all points and connect if it's at same location and create a new mesh.
    */
    /*
    synchronized public static IMesh join(IMesh[] meshes){
	
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
    */
    

    /**
       static method to join meshes. it's renamed from join to joinMesh. now method "join" is an instance method
    */
    synchronized public static IMesh joinMesh(IMesh[] meshes){
	IMeshGeo[] meshgeo = new IMeshGeo[meshes.length];
	for(int i=0; i<meshes.length; i++){ meshgeo[i] = meshes[i].mesh; }
	IMesh mesh = new IMesh(IMeshGeo.joinMesh(meshgeo));
	mesh.attr(meshes[0].attr()); // copy attributes
	return mesh;
    }
    
    /** create polyhedron mesh by delaunay triangulation around a center */
    public static IMesh polyhedron(IVertex[] vtx){
	return new IMesh(IMeshGeo.createPolyhedron(vtx));
    }
    /** create polyhedron mesh by delaunay triangulation around a center */
    public static IMesh createPolyhedron(IVertex[] vtx){ return polyhedron(vtx); }

    /** create polyhedron mesh by delaunay triangulation around a center */
    public static IMesh polyhedron(IVecI[] pts){
	IVertex[] vtx = new IVertex[pts.length];
	for(int i=0; i<pts.length; i++){ vtx[i] = new IVertex(pts[i].get()); }
	return new IMesh(IMeshGeo.createPolyhedron(vtx));
    }
    /** create polyhedron mesh by delaunay triangulation around a center */
    public static IMesh createPolyhedron(IVecI[] pts){ return polyhedron(pts); }
    
    
    /** create polyhedron mesh by delaunay triangulation around a center with maximum threshold of edge length */
    public static IMesh polyhedron(IVertex[] vtx, double threshold){
	return new IMesh(IMeshGeo.createPolyhedron(vtx,threshold));
    }
    /** create polyhedron mesh by delaunay triangulation around a center with maximum threshold of edge length */
    public static IMesh createPolyhedron(IVertex[] vtx, double threshold){ return polyhedron(vtx,threshold); }
    
    /** create polyhedron mesh by delaunay triangulation around a center with maximum threshold of edge length */
    public static IMesh polyhedron(IVecI[] pts, double threshold){
	IVertex[] vtx = new IVertex[pts.length];
	for(int i=0; i<pts.length; i++){ vtx[i] = new IVertex(pts[i].get()); }
	return new IMesh(IMeshGeo.createPolyhedron(vtx,threshold));
    }
    /** create polyhedron mesh by delaunay triangulation around a center with maximum threshold of edge length */
    public static IMesh createPolyhedron(IVecI[] pts, double threshold){ return polyhedron(pts,threshold); }
    
    
    /** connect closest vertex */
    public static IMesh connectVertex(IMesh mesh1, IMesh mesh2){
	int n1 = mesh1.vertexNum();
	int n2 = mesh2.vertexNum();
	double mindist=-1;
	int minIdx1=0, minIdx2=0;
	for(int i=0; i<n1; i++){
	    for(int j=0; j<n2; j++){
		double dist = mesh1.vertex(i).dist(mesh2.vertex(j));
		if(mindist<0 || dist<mindist){ mindist = dist; minIdx1=i; minIdx2=j; }
	    }
	}
	return connectVertex(mesh1, mesh1.vertex(minIdx1), mesh2, mesh2.vertex(minIdx2));
    }
    
    /** connect faces over a vertex */
    public static IMesh connectVertex(IMesh mesh1, IVertex v1, IMesh mesh2, IVertex v2){
	IMeshGeo m = IMeshGeo.connectVertex(mesh1.mesh, v1, mesh2.mesh, v2);
	return new IMesh(m);
    }
    
    
    /** only setting value to closed. checking no connection of mesh */
    synchronized public IMesh close(){ mesh.close(); return this; }
    synchronized public boolean isClosed(){ return mesh.isClosed(); }

    synchronized public IMesh deleteVertex(int i){ return deleteVertex(mesh.vertex(i)); }
    synchronized public IMesh deleteEdge(int i){ return deleteEdge(mesh.edge(i)); }
    synchronized public IMesh deleteFace(int i){ return deleteFace(mesh.face(i)); }
    
    synchronized public IMesh deleteVertex(IIntegerI i){ return deleteVertex(i.x()); }
    synchronized public IMesh deleteEdge(IIntegerI i){ return deleteEdge(i.x()); }
    synchronized public IMesh deleteFace(IIntegerI i){ return deleteFace(i.x()); }
    
    synchronized public IMesh deleteVertex(IVertex v){
	mesh.deleteVertex(v);
	if(mesh.vertexNum()==0 || mesh.edgeNum()==0 || mesh.faceNum()==0) del();
	else{ updateGraphic(); }
	return this;
    }
    synchronized public IMesh deleteEdge(IEdge e){
	mesh.deleteEdge(e);
	if(mesh.vertexNum()==0 || mesh.edgeNum()==0 || mesh.faceNum()==0) del();
	else{ updateGraphic(); }
	return this;
    }
    synchronized public IMesh deleteFace(IFace f){
	mesh.deleteFace(f);
	if(mesh.vertexNum()==0 || mesh.edgeNum()==0 || mesh.faceNum()==0) del();
	else{ updateGraphic(); }
	return this;
    }
    
    
    /** remove duplicated edges and vertices */
    //public static IMesh unify(IMesh m){ return new IMesh(IMeshGeo.unify(m.mesh)); }
    
    /** remove uplicated edges and vertices */
    public IMesh removeDuplicates(){
	mesh.removeDuplicates(); return this;
    }
    /** remove uplicated edges and vertices */
    public IMesh removeDuplicates(double tolerance){
	mesh.removeDuplicates(tolerance); return this;
    }
    
    /** join other meshes into the current one and remove duplicated edges and vertices */
    public IMesh join(IMesh[] meshes){ return join(meshes, IConfig.tolerance); }
    
    /** join other meshes into the current one and remove duplicated edges and vertices */
    public IMesh join(IMesh[] meshes, double tolerance){
	IMeshGeo[] meshgeo = new IMeshGeo[meshes.length];
	for(int i=0; i<meshes.length; i++){ meshgeo[i] = meshes[i].mesh; }
	mesh.join(meshgeo, tolerance);
	return this;
    }
    
    
    /**
       update graphic when control point location changes or some minor change.
       This method in IMesh also updates normal vectors.
    */
    synchronized public void updateGraphic(){
	super.updateGraphic();
	if(IConfig.updateMeshNormal){
	    for(int i=0; i<mesh.faceNum(); i++){
		mesh.face(i).calcNormal(); // updates normal internally by vertices location
	    }
	    for(int i=0; i<mesh.vertexNum(); i++){
		mesh.vertex(i).calcNormal(); // updates normal internally by adjacent face normal
	    }
	}
    }
    
    
    
    /*******************************************************
     * ITransformable methods
     ******************************************************/
    
    synchronized public IMesh add(double x, double y, double z){ mesh.add(x,y,z); return this; }
    synchronized public IMesh add(IDoubleI x, IDoubleI y, IDoubleI z){ mesh.add(x,y,z); return this; }
    synchronized public IMesh add(IVecI v){ mesh.add(v); return this; }
    synchronized public IMesh sub(double x, double y, double z){ mesh.sub(x,y,z); return this; }
    synchronized public IMesh sub(IDoubleI x, IDoubleI y, IDoubleI z){ mesh.sub(x,y,z); return this; }
    synchronized public IMesh sub(IVecI v){ mesh.sub(v); return this; }
    synchronized public IMesh mul(IDoubleI v){ mesh.mul(v); return this; }
    synchronized public IMesh mul(double v){ mesh.mul(v); return this; }
    synchronized public IMesh div(IDoubleI v){ mesh.div(v); return this; }
    synchronized public IMesh div(double v){ mesh.div(v); return this; }
    
    synchronized public IMesh neg(){ mesh.neg(); return this; }
    /** alias of neg */
    synchronized public IMesh rev(){ return neg(); }
    /** alias of neg */
    synchronized public IMesh flip(){ return neg(); }
    
    
    /** scale add */
    synchronized public IMesh add(IVecI v, double f){ mesh.add(v,f); return this; }
    /** scale add */
    synchronized public IMesh add(IVecI v, IDoubleI f){ mesh.add(v,f); return this; }
    /** scale add alias */
    synchronized public IMesh add(double f, IVecI v){ return add(v,f); }
    /** scale add alias */
    synchronized public IMesh add(IDoubleI f, IVecI v){ return add(v,f); }
    
    /** rotation around z-axis and origin */
    synchronized public IMesh rot(IDoubleI angle){ mesh.rot(angle); return this; }
    synchronized public IMesh rot(double angle){ mesh.rot(angle); return this; }
    
    /** rotation around axis vector */
    synchronized public IMesh rot(IVecI axis, IDoubleI angle){ mesh.rot(axis,angle); return this; }
    synchronized public IMesh rot(IVecI axis, double angle){ mesh.rot(axis,angle); return this; }
    
    /** rotation around axis vector and center */
    synchronized public IMesh rot(IVecI center, IVecI axis, IDoubleI angle){ mesh.rot(center,axis,angle); return this; }
    synchronized public IMesh rot(IVecI center, IVecI axis, double angle){ mesh.rot(center,axis,angle); return this; }
    
    /** rotate to destination direction vector */
    synchronized public IMesh rot(IVecI axis, IVecI destDir){ mesh.rot(axis,destDir); return this; }
    /** rotate to destination point location */
    synchronized public IMesh rot(IVecI center, IVecI axis, IVecI destPt){ mesh.rot(center,axis,destPt); return this; }
    
    
    /** rotation on xy-plane around origin; same with rot(IDoubleI) */
    synchronized public IMesh rot2(IDoubleI angle){ mesh.rot2(angle); return this; }
    /** rotation on xy-plane around origin; same with rot(double) */
    synchronized public IMesh rot2(double angle){ mesh.rot2(angle); return this; }
    
    /** rotation on xy-plane around center */
    synchronized public IMesh rot2(IVecI center, IDoubleI angle){ mesh.rot2(center,angle); return this; }
    synchronized public IMesh rot2(IVecI center, double angle){ mesh.rot2(center,angle); return this; }
    
    /** rotation on xy-plane to destination direction vector */
    synchronized public IMesh rot2(IVecI destDir){ mesh.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */
    synchronized public IMesh rot2(IVecI center, IVecI destPt){ mesh.rot2(center,destPt); return this; }
        
    
    /** alias of mul */
    synchronized public IMesh scale(IDoubleI f){ return mul(f); }
    synchronized public IMesh scale(double f){ return mul(f); }
    synchronized public IMesh scale(IVecI center, IDoubleI f){ mesh.scale(center,f); return this; }
    synchronized public IMesh scale(IVecI center, double f){ mesh.scale(center,f); return this; }

    
    /** scale only in 1 direction */
    synchronized public IMesh scale1d(IVecI axis, double f){ mesh.scale1d(axis,f); return this; }
    synchronized public IMesh scale1d(IVecI axis, IDoubleI f){ mesh.scale1d(axis,f); return this; }
    synchronized public IMesh scale1d(IVecI center, IVecI axis, double f){ mesh.scale1d(center,axis,f); return this; }
    synchronized public IMesh scale1d(IVecI center, IVecI axis, IDoubleI f){ mesh.scale1d(center,axis,f); return this; }
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    synchronized public IMesh ref(IVecI planeDir){ mesh.ref(planeDir); return this; }
    synchronized public IMesh ref(IVecI center, IVecI planeDir){ mesh.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    synchronized public IMesh mirror(IVecI planeDir){ return ref(planeDir); }
    synchronized public IMesh mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    synchronized public IMesh shear(double sxy, double syx, double syz,
		       double szy, double szx, double sxz){
	mesh.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public IMesh shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	mesh.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public IMesh shear(IVecI center, double sxy, double syx, double syz,
		       double szy, double szx, double sxz){
	mesh.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    synchronized public IMesh shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	mesh.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    synchronized public IMesh shearXY(double sxy, double syx){ mesh.shearXY(sxy,syx); return this; }
    synchronized public IMesh shearXY(IDoubleI sxy, IDoubleI syx){ mesh.shearXY(sxy,syx); return this; }
    synchronized public IMesh shearXY(IVecI center, double sxy, double syx){ mesh.shearXY(center,sxy,syx); return this; }
    synchronized public IMesh shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){ mesh.shearXY(center,sxy,syx); return this; }
    
    synchronized public IMesh shearYZ(double syz, double szy){ mesh.shearYZ(syz,szy); return this; }
    synchronized public IMesh shearYZ(IDoubleI syz, IDoubleI szy){ mesh.shearYZ(syz,szy); return this; }
    synchronized public IMesh shearYZ(IVecI center, double syz, double szy){ mesh.shearYZ(center,syz,szy); return this; }
    synchronized public IMesh shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){ mesh.shearYZ(center,syz,szy); return this; }
    
    synchronized public IMesh shearZX(double szx, double sxz){ mesh.shearZX(szx,sxz); return this; }
    synchronized public IMesh shearZX(IDoubleI szx, IDoubleI sxz){ mesh.shearZX(szx,sxz); return this; }
    synchronized public IMesh shearZX(IVecI center, double szx, double sxz){ mesh.shearZX(center,szx,sxz); return this; }
    synchronized public IMesh shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){ mesh.shearZX(center,szx,sxz); return this; }
    
    /** mv() is alias of add() */
    synchronized public IMesh mv(double x, double y, double z){ return add(x,y,z); }
    synchronized public IMesh mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    synchronized public IMesh mv(IVecI v){ return add(v); }
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    synchronized public IMesh cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    synchronized public IMesh cp(double x, double y, double z){ return dup().add(x,y,z); }
    synchronized public IMesh cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    synchronized public IMesh cp(IVecI v){ return dup().add(v); }
    
    
    /** translate() is alias of add() */
    synchronized public IMesh translate(double x, double y, double z){ mesh.translate(x,y,z); return this; }
    synchronized public IMesh translate(IDoubleI x, IDoubleI y, IDoubleI z){ mesh.translate(x,y,z); return this; }
    synchronized public IMesh translate(IVecI v){ mesh.translate(v); return this; }
    
    
    synchronized public IMesh transform(IMatrix3I mat){ mesh.transform(mat); return this; }
    synchronized public IMesh transform(IMatrix4I mat){ mesh.transform(mat); return this; }
    synchronized public IMesh transform(IVecI xvec, IVecI yvec, IVecI zvec){ mesh.transform(xvec,yvec,zvec); return this; }
    synchronized public IMesh transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){ mesh.transform(xvec,yvec,zvec,translate); return this; }
    
    
}
