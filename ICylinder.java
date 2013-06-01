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

import java.awt.Color;

import igeo.gui.*;

/**
   Cylinder surface class
   
   @author Satoru Sugihara
*/
public class ICylinder extends ISurface{
    public IVecI pt1, pt2;
    public IDoubleI radius1, radius2;
    
    public ICylinder(IVecI pt1, IVecI pt2, double radius){
	this(null,pt1,pt2,radius,radius);
    }
    public ICylinder(IServerI s, IVecI pt1, IVecI pt2, double radius){
	this(s,pt1,pt2,radius,radius);
    }
    public ICylinder(IVecI pt1, IVecI pt2, double radius1, double radius2){
	this(null,pt1,pt2,radius1,radius2);
    }
    public ICylinder(IVecI pt1, IVecI pt2, IDoubleI radius){
	this(null,pt1,pt2,radius,radius);
    }
    public ICylinder(IServerI s, IVecI pt1, IVecI pt2, IDoubleI radius){
	this(s,pt1,pt2,radius,radius);
    }
    public ICylinder(IServerI s, IVecI pt1, IVecI pt2, double radius1, double radius2){
	this(s,pt1,pt2,new IDouble(radius1),new IDouble(radius2));
    }
    public ICylinder(IVecI pt1, IVecI pt2, IDoubleI radius1, IDoubleI radius2){
	this(null,pt1,pt2,radius1,radius2);
    }
    public ICylinder(IServerI s, IVecI pt1, IVecI pt2, IDoubleI radius1, IDoubleI radius2){
	super(s);
	this.pt1 = pt1; this.pt2 = pt2;
	this.radius1 = radius1;
	this.radius2 = radius2;
	initCylinder(s);
    }
    
    public void initCylinder(IServerI s){
	IVec p1 = pt1.get();
	IVec p2 = pt2.get();
	IVec normal = p2.diff(p1);
	IVec[][] cpts = new IVec[2][];
	cpts[0] = ICircleGeo.circleCP(p1,normal,radius1.x());
	cpts[1] = ICircleGeo.circleCP(p2,normal,radius2.x());
	surface = new ISurfaceGeo(cpts, 1,ICircleGeo.circleDeg(),
				  INurbsGeo.createKnots(1,2),
				  ICircleGeo.circleKnots());
	//IVec roll = normal.cross(IVec.zaxis);
	//cpts[0] = ICircleGeo.circleCPApprox(pt1,normal,roll,radius1,radius1);
	//cpts[1] = ICircleGeo.circleCPApprox(pt2,normal,roll,radius2,radius2);
	//surface = new ISurfaceGeo(cpts, 1, ICircleGeo.circleDeg());
	super.initSurface(s);
    }
    

    
    /******************************************************************************
     * IObject methods
     ******************************************************************************/
    
    public ICylinder name(String nm){ super.name(nm); return this; }
    public ICylinder layer(ILayer l){ super.layer(l); return this; }
    public ICylinder layer(String l){ super.layer(l); return this; }

    public ICylinder attr(IAttribute at){ super.attr(at); return this; }
    
    
    public ICylinder hide(){ super.hide(); return this; }
    public ICylinder show(){ super.show(); return this; }
    
    public ICylinder clr(IColor c){ super.clr(c); return this; }
    public ICylinder clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public ICylinder clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public ICylinder clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    
    public ICylinder clr(Color c){ super.clr(c); return this; }
    public ICylinder clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public ICylinder clr(int gray){ super.clr(gray); return this; }
    public ICylinder clr(float fgray){ super.clr(fgray); return this; }
    public ICylinder clr(double dgray){ super.clr(dgray); return this; }
    public ICylinder clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public ICylinder clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public ICylinder clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public ICylinder clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public ICylinder clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public ICylinder clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public ICylinder clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public ICylinder clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public ICylinder clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public ICylinder hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public ICylinder hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public ICylinder hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public ICylinder hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public ICylinder setColor(Color c){ super.setColor(c); return this; }
    public ICylinder setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public ICylinder setColor(int gray){ super.setColor(gray); return this; }
    public ICylinder setColor(float fgray){ super.setColor(fgray); return this; }
    public ICylinder setColor(double dgray){ super.setColor(dgray); return this; }
    public ICylinder setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public ICylinder setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public ICylinder setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public ICylinder setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public ICylinder setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public ICylinder setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public ICylinder setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public ICylinder setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public ICylinder setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public ICylinder setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public ICylinder setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public ICylinder setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public ICylinder setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public ICylinder weight(double w){ super.weight(w); return this; }
    public ICylinder weight(float w){ super.weight(w); return this; }
    
    

}
