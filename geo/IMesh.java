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

import java.util.ArrayList;
import java.awt.Color;

import igeo.core.*;
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
    
    public IMesh(IServerI s, ArrayList<ICurveI> lines, IMeshCreator creator){
        super(s); mesh = new IMeshGeo(lines,creator); initMesh(s);
    }
    public IMesh(ArrayList<ICurveI> lines, IMeshCreator creator){
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
    
    public IMesh(IServerI s, IVec[][] matrix, boolean triangulateDir, IMeshCreator creator){
	super(s); mesh = new IMeshGeo(matrix,triangulateDir,creator); initMesh(s);
    }
    public IMesh(IVec[][] matrix, boolean triangulateDir, IMeshCreator creator){
	this((IServerI)null, matrix,triangulateDir,creator);
    }
    
    public IMesh(IServerI s, IVec[][] matrix, int unum, int vnum, boolean triangulateDir){
	super(s); mesh = new IMeshGeo(matrix,unum,vnum,triangulateDir); initMesh(s);
    }
    public IMesh(IVec[][] matrix, int unum, int vnum, boolean triangulateDir){
	this((IServerI)null, matrix,unum,vnum,triangulateDir);
    }
    
    public IMesh(IServerI s, IVec[][] matrix, int unum, int vnum, boolean triangulateDir,
		 IMeshCreator creator){
	super(s); mesh = new IMeshGeo(matrix,unum,vnum,triangulateDir,creator); initMesh(s);
    }
    public IMesh(IVec[][] matrix, int unum, int vnum, boolean triangulateDir, IMeshCreator creator){
	this((IServerI)null, matrix,unum,vnum,triangulateDir,creator);
    }    
    
    public IMesh(IServerI s, ArrayList<IVertex> v, ArrayList<IEdge> e, ArrayList<IFace> f){
	super(s); mesh = new IMeshGeo(v,e,f); initMesh(s);
    }
    public IMesh(ArrayList<IVertex> v, ArrayList<IEdge> e, ArrayList<IFace> f){
	this((IServerI)null,v,e,f);
    }
    
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
    
    
}
