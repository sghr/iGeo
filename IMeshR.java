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
   Reference class of polygon mesh object containing any instance of IMeshI.
   
   @author Satoru Sugihara
*/
public class IMeshR extends IObject implements IMeshI{
    
    public IMeshI mesh;
    
    
    public IMeshR(IMeshI m){ super(); mesh=m; initMesh(null); }
    public IMeshR(IServerI s, IMeshI m){ super(s); mesh=m; initMesh(s); }
    
    public IMeshR(IMeshR m){ super(m); mesh=m.dup(); initMesh(m.server); }
    public IMeshR(IServerI s, IMeshR m){ super(s,m); mesh=m.dup(); initMesh(s); }
    
    
    public void initMesh(IServerI s){
	if(mesh instanceof IMeshGeo) parameter = (IMeshGeo)mesh;
	if(graphics==null) initGraphic(s);
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
        if(m.isGL()) return new IMeshGraphicGL(this); 
        return null;
    }
    
    public IMeshGeo get(){ return mesh.get(); }
    
    public IMeshR dup(){ return new IMeshR(this); }
        
    public int vertexNum(){ return mesh.vertexNum(); }
    public int edgeNum(){ return mesh.edgeNum(); }
    public int faceNum(){ return mesh.faceNum(); }
    
    public int vertexNum(ISwitchE e){ return mesh.vertexNum(e); }
    public int edgeNum(ISwitchE e){ return mesh.edgeNum(e); }
    public int faceNum(ISwitchE e){ return mesh.faceNum(e); }
    
    public IIntegerI vertexNum(ISwitchR r){ return mesh.vertexNum(r); }
    public IIntegerI edgeNum(ISwitchR r){ return mesh.edgeNum(r); }
    public IIntegerI faceNum(ISwitchR r){ return mesh.faceNum(r); }

    /** return all vertices */
    public ArrayList<IVertex> vertices(){ return mesh.vertices(); }
    /** return all edges */
    public ArrayList<IEdge> edges(){ return mesh.edges(); }
    /** return all faces */
    public ArrayList<IFace> faces(){ return mesh.faces(); }

    public IMeshR deleteVertex(int i){ mesh.deleteVertex(i); return this; }
    public IMeshR deleteEdge(int i){ mesh.deleteEdge(i); return this; }
    public IMeshR deleteFace(int i){ mesh.deleteFace(i); return this; }
    
    public IMeshR deleteVertex(IIntegerI i){ mesh.deleteVertex(i); return this; }
    public IMeshR deleteEdge(IIntegerI i){ mesh.deleteEdge(i); return this; }
    public IMeshR deleteFace(IIntegerI i){ mesh.deleteFace(i); return this; }
    
    public IMeshR deleteVertex(IVertex v){ mesh.deleteVertex(v); return this; }
    public IMeshR deleteEdge(IEdge e){ mesh.deleteEdge(e); return this; }
    public IMeshR deleteFace(IFace f){ mesh.deleteFace(f); return this; }
    

    
    public IVertex vertex(int i){ return mesh.vertex(i); }
    public IEdge edge(int i){ return mesh.edge(i); }
    public IFace face(int i){ return mesh.face(i); }
    
    public IVertex vertex(IIntegerI i){ return mesh.vertex(i); }
    public IEdge edge(IIntegerI i){ return mesh.edge(i); }
    public IFace face(IIntegerI i){ return mesh.face(i); }

    public IVecI center(){ return mesh.center(); }
    
    /** only setting value to closed. checking no connection of mesh */
    public IMeshR close(){ mesh.close(); return this; }
    public boolean isClosed(){ return mesh.isClosed(); }
    
    public IMeshR name(String nm){ super.name(nm); return this; }
    public IMeshR layer(ILayer l){ super.layer(l); return this; }
        
    public IMeshR hide(){ super.hide(); return this; }
    public IMeshR show(){ super.show(); return this; }
    
    public IMeshR clr(IColor c){ super.clr(c); return this; }
    public IMeshR clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IMeshR clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IMeshR clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IMeshR clr(Color c){ super.clr(c); return this; }
    public IMeshR clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IMeshR clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public IMeshR clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IMeshR clr(int gray){ super.clr(gray); return this; }
    public IMeshR clr(float fgray){ super.clr(fgray); return this; }
    public IMeshR clr(double dgray){ super.clr(dgray); return this; }
    public IMeshR clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IMeshR clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IMeshR clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IMeshR clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IMeshR clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IMeshR clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IMeshR clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IMeshR clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IMeshR clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IMeshR hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IMeshR hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IMeshR hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IMeshR hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IMeshR setColor(Color c){ super.setColor(c); return this; }
    public IMeshR setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IMeshR setColor(int gray){ super.setColor(gray); return this; }
    public IMeshR setColor(float fgray){ super.setColor(fgray); return this; }
    public IMeshR setColor(double dgray){ super.setColor(dgray); return this; }
    public IMeshR setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IMeshR setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IMeshR setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IMeshR setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IMeshR setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IMeshR setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IMeshR setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IMeshR setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IMeshR setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IMeshR setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IMeshR setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IMeshR setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IMeshR setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public IMeshR weight(double w){ super.weight(w); return this; }
    public IMeshR weight(float w){ super.weight(w); return this; }
    
    
    /*******************************************************
     * ITransformable methods
     ******************************************************/
    
    public IMeshR add(double x, double y, double z){ mesh.add(x,y,z); return this; }
    public IMeshR add(IDoubleI x, IDoubleI y, IDoubleI z){ mesh.add(x,y,z); return this; }
    public IMeshR add(IVecI v){ mesh.add(v); return this; }
    public IMeshR sub(double x, double y, double z){ mesh.sub(x,y,z); return this; }
    public IMeshR sub(IDoubleI x, IDoubleI y, IDoubleI z){ mesh.sub(x,y,z); return this; }
    public IMeshR sub(IVecI v){ mesh.sub(v); return this; }
    public IMeshR mul(IDoubleI v){ mesh.mul(v); return this; }
    public IMeshR mul(double v){ mesh.mul(v); return this; }
    public IMeshR div(IDoubleI v){ mesh.div(v); return this; }
    public IMeshR div(double v){ mesh.div(v); return this; }
    
    public IMeshR neg(){ mesh.neg(); return this; }
    /** alias of neg */
    public IMeshR rev(){ return neg(); }
    /** alias of neg */
    public IMeshR flip(){ return neg(); }
    
    
    /** scale add */
    public IMeshR add(IVecI v, double f){ mesh.add(v,f); return this; }
    /** scale add */
    public IMeshR add(IVecI v, IDoubleI f){ mesh.add(v,f); return this; }
    /** scale add alias */
    public IMeshR add(double f, IVecI v){ return add(v,f); }
    /** scale add alias */
    public IMeshR add(IDoubleI f, IVecI v){ return add(v,f); }
    
    /** rotation around z-axis and origin */
    public IMeshR rot(IDoubleI angle){ mesh.rot(angle); return this; }
    public IMeshR rot(double angle){ mesh.rot(angle); return this; }
    
    /** rotation around axis vector */
    public IMeshR rot(IVecI axis, IDoubleI angle){ mesh.rot(axis,angle); return this; }
    public IMeshR rot(IVecI axis, double angle){ mesh.rot(axis,angle); return this; }
    
    /** rotation around axis vector and center */
    public IMeshR rot(IVecI center, IVecI axis, IDoubleI angle){ mesh.rot(center,axis,angle); return this; }
    public IMeshR rot(IVecI center, IVecI axis, double angle){ mesh.rot(center,axis,angle); return this; }
    
    /** rotate to destination direction vector */
    public IMeshR rot(IVecI axis, IVecI destDir){ mesh.rot(axis,destDir); return this; }
    /** rotate to destination point location */
    public IMeshR rot(IVecI center, IVecI axis, IVecI destPt){ mesh.rot(center,axis,destPt); return this; }
    
    
    /** rotation on xy-plane around origin; same with rot(IDoubleI) */
    public IMeshR rot2(IDoubleI angle){ mesh.rot2(angle); return this; }
    /** rotation on xy-plane around origin; same with rot(double) */
    public IMeshR rot2(double angle){ mesh.rot2(angle); return this; }
    
    /** rotation on xy-plane around center */
    public IMeshR rot2(IVecI center, IDoubleI angle){ mesh.rot2(center,angle); return this; }
    public IMeshR rot2(IVecI center, double angle){ mesh.rot2(center,angle); return this; }
    
    /** rotation on xy-plane to destination direction vector */
    public IMeshR rot2(IVecI destDir){ mesh.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */
    public IMeshR rot2(IVecI center, IVecI destPt){ mesh.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    public IMeshR scale(IDoubleI f){ return mul(f); }
    public IMeshR scale(double f){ return mul(f); }
    public IMeshR scale(IVecI center, IDoubleI f){ mesh.scale(center,f); return this; }
    public IMeshR scale(IVecI center, double f){ mesh.scale(center,f); return this; }
    
    
    /** scale only in 1 direction */
    public IMeshR scale1d(IVecI axis, double f){ mesh.scale1d(axis,f); return this; }
    public IMeshR scale1d(IVecI axis, IDoubleI f){ mesh.scale1d(axis,f); return this; }
    public IMeshR scale1d(IVecI center, IVecI axis, double f){ mesh.scale1d(center,axis,f); return this; }
    public IMeshR scale1d(IVecI center, IVecI axis, IDoubleI f){ mesh.scale1d(center,axis,f); return this; }
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IMeshR ref(IVecI planeDir){ mesh.ref(planeDir); return this; }
    public IMeshR ref(IVecI center, IVecI planeDir){ mesh.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    public IMeshR mirror(IVecI planeDir){ return ref(planeDir); }
    public IMeshR mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    public IMeshR shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	mesh.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IMeshR shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	mesh.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IMeshR shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	mesh.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IMeshR shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	mesh.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    public IMeshR shearXY(double sxy, double syx){ mesh.shearXY(sxy,syx); return this; }
    public IMeshR shearXY(IDoubleI sxy, IDoubleI syx){ mesh.shearXY(sxy,syx); return this; }
    public IMeshR shearXY(IVecI center, double sxy, double syx){ mesh.shearXY(center,sxy,syx); return this; }
    public IMeshR shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){ mesh.shearXY(center,sxy,syx); return this; }
    
    public IMeshR shearYZ(double syz, double szy){ mesh.shearYZ(syz,szy); return this; }
    public IMeshR shearYZ(IDoubleI syz, IDoubleI szy){ mesh.shearYZ(syz,szy); return this; }
    public IMeshR shearYZ(IVecI center, double syz, double szy){ mesh.shearYZ(center,syz,szy); return this; }
    public IMeshR shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){ mesh.shearYZ(center,syz,szy); return this; }
    
    public IMeshR shearZX(double szx, double sxz){ mesh.shearZX(szx,sxz); return this; }
    public IMeshR shearZX(IDoubleI szx, IDoubleI sxz){ mesh.shearZX(szx,sxz); return this; }
    public IMeshR shearZX(IVecI center, double szx, double sxz){ mesh.shearZX(center,szx,sxz); return this; }
    public IMeshR shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){ mesh.shearZX(center,szx,sxz); return this; }
    
    /** mv() is alias of add() */
    public IMeshR mv(double x, double y, double z){ return add(x,y,z); }
    public IMeshR mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IMeshR mv(IVecI v){ return add(v); }
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IMeshR cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IMeshR cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IMeshR cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IMeshR cp(IVecI v){ return dup().add(v); }
    
    
    /** translate() is alias of add() */
    public IMeshR translate(double x, double y, double z){ return add(x,y,z); }
    public IMeshR translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IMeshR translate(IVecI v){ return add(v); }
    
    
    public IMeshR transform(IMatrix3I mat){ mesh.transform(mat); return this; }
    public IMeshR transform(IMatrix4I mat){ mesh.transform(mat); return this; }
    public IMeshR transform(IVecI xvec, IVecI yvec, IVecI zvec){ mesh.transform(xvec,yvec,zvec); return this; }
    public IMeshR transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){ mesh.transform(xvec,yvec,zvec,translate); return this; }
    
    
}
