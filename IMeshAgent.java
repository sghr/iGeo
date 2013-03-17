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
/**
   Agent to iterate mesh faces.
   
   @author Satoru Sugihara
*/
public class IMeshAgent extends IAgent{
    
    public IFace curFace, prevFace;
    //public IEdge curEdge, prevEdge;
    //public IVertex curVertex, prevVertex;
    
    public IMeshAgent(IFace f){ curFace = f; }
    
    
    
    public IFace nextFace(){
	// random edge
	IEdge edge = curFace.edge(IRand.geti(0,curFace.edgeNum()-1));
	return edge.getOtherFace(curFace);
    }

    //debug
    IPoint centerPt;
    
    public void update(){
	IFace f = nextFace();
	
	if(centerPt!=null){ centerPt.del(); }
	
	prevFace = curFace;
	curFace = f;
	
	centerPt = new IPoint(curFace.getCenter()).clr(1.,0,0).size(5);
    }
    
    
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IMeshAgent name(String nm){ super.name(nm); return this; }
    public IMeshAgent layer(ILayer l){ super.layer(l); return this; }
    
    public IMeshAgent hide(){ super.hide(); return this; }
    public IMeshAgent show(){ super.show(); return this; }
    
    public IMeshAgent clr(IColor c){ super.clr(c); return this; }
    public IMeshAgent clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IMeshAgent clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IMeshAgent clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IMeshAgent clr(Color c){ super.clr(c); return this; }
    public IMeshAgent clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IMeshAgent clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public IMeshAgent clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IMeshAgent clr(int gray){ super.clr(gray); return this; }
    public IMeshAgent clr(float fgray){ super.clr(fgray); return this; }
    public IMeshAgent clr(double dgray){ super.clr(dgray); return this; }
    public IMeshAgent clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IMeshAgent clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IMeshAgent clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IMeshAgent clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IMeshAgent clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IMeshAgent clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IMeshAgent clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IMeshAgent clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IMeshAgent clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IMeshAgent hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IMeshAgent hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IMeshAgent hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IMeshAgent hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }

    public IMeshAgent weight(float w){ super.weight(w); return this; }
    public IMeshAgent weight(double w){ super.weight(w); return this; }
    
    public IMeshAgent setColor(Color c){ super.setColor(c); return this; }
    public IMeshAgent setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IMeshAgent setColor(int gray){ super.setColor(gray); return this; }
    public IMeshAgent setColor(float fgray){ super.setColor(fgray); return this; }
    public IMeshAgent setColor(double dgray){ super.setColor(dgray); return this; }
    public IMeshAgent setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IMeshAgent setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IMeshAgent setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IMeshAgent setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IMeshAgent setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IMeshAgent setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IMeshAgent setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IMeshAgent setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IMeshAgent setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IMeshAgent setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IMeshAgent setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IMeshAgent setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IMeshAgent setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    
}
