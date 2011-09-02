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
   Reference class of polygon mesh object containing any instance of IMeshI.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IMeshR extends IObject implements IMeshI{
    
    public IMeshI mesh;
    
    
    public IMeshR(IMeshI m){ super(); mesh=m; initMesh(null); }
    public IMeshR(IServerI s, IMeshI m){ super(s); mesh=m; initMesh(s); }
    
    public void initMesh(IServerI s){
	if(mesh instanceof IMeshGeo) parameter = (IMeshGeo)mesh;
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
    
    
    public IMeshR name(String nm){ super.name(nm); return this; }
    public IMeshR layer(ILayer l){ super.layer(l); return this; }
        
    public IMeshR hide(){ super.hide(); return this; }
    public IMeshR show(){ super.show(); return this; }
    
    public IMeshR clr(Color c){ super.clr(c); return this; }
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
    
    
}
