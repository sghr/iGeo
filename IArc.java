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

/**
   Arc object.
   It contains IArcGeo instance inside.
   
   @author Satoru Sugihara
*/
public class IArc extends ICurve{
    
    public static int arcDeg(){ return IArcGeo.arcDeg(); }
    
    public static IVec4[] arcCP(IVec center, IVec normal, IVec startPt, double angle){
	return IArcGeo.arcCP(center,normal,startPt,angle);
    }
    
    public static IVec4[] arcCP(IVec center, IVec startPt, IVec endPt, boolean flipArcSide){
	return IArcGeo.arcCP(center,startPt,endPt,flipArcSide);
    }
    
    public static IVec4[] arcCP(IVec center, IVec startPt, IVec midPt, IVec endPt, IVec normal){
	return IArcGeo.arcCP(center,startPt,midPt,endPt,normal);
    }
    
    public static double[] arcKnots(double angle){ return IArcGeo.arcKnots(angle); }
    
    //public IArcGeo arc;
    
    public IArc(){}
    
    public IArc(IVecI center, IVecI normal, IVecI startPt, double angle){
	this(null,center,normal,startPt,angle);
    }
    public IArc(IVecI center, IVecI normal, IVecI startPt, IDoubleI angle){
	this(null,center,normal,startPt,angle);
    }
    public IArc(IVecI center, IVecI startPt, double angle){
	this(null,center,new IVec(0,0,1),startPt,angle);
    }
    public IArc(IVecI center, IVecI startPt, IDoubleI angle){
	this(null,center,new IVec(0,0,1),startPt,angle);
    }
    public IArc(double x, double y, double z, double startX, double startY, double startZ, double angle){
	this(null,new IVec(x,y,z),new IVec(0,0,1),new IVec(startX, startY, startZ), angle);
    }
    public IArc(IVecI center, IVecI startPt, IVecI endPt, IBoolI flipArcSide){
	this(null,center,startPt,endPt,flipArcSide);
    }
    public IArc(IVecI center, IVecI startPt, IVecI endPt, boolean flipArcSide){
	this(null,center,startPt,endPt,flipArcSide);
    }
    public IArc(IVecI center, IVecI startPt, IVecI midPt, IVecI endPt, IVecI normal){
	this(null,center,startPt,midPt,endPt,normal);
    }
    
    public IArc(IServerI s, IVecI center, IVecI normal, IVecI startPt, double angle){
	super(s, new IArcGeo(center,normal,startPt,angle));
    }
    public IArc(IServerI s, IVecI center, IVecI normal, IVecI startPt, IDoubleI angle){
	super(s, new IArcGeo(center,normal,startPt,angle));
    }
    public IArc(IServerI s, IVecI center, IVecI startPt, double angle){
	this(s,center,new IVec(0,0,1),startPt,angle);
    }
    public IArc(IServerI s, IVecI center, IVecI startPt, IDoubleI angle){
	this(s,center,new IVec(0,0,1),startPt,angle);
    }
    public IArc(IServerI s, double x, double y, double z, double startX, double startY, double startZ, double angle){
	this(s,new IVec(x,y,z),new IVec(0,0,1),new IVec(startX, startY, startZ), angle);
    }
    public IArc(IServerI s, IVecI center, IVecI startPt, IVecI endPt, IBoolI flipArcSide){
	super(s, new IArcGeo(center,startPt,endPt,flipArcSide));
    }
    public IArc(IServerI s, IVecI center, IVecI startPt, IVecI endPt, boolean flipArcSide){
	super(s, new IArcGeo(center,startPt,endPt,flipArcSide));
    }
    public IArc(IServerI s, IVecI center, IVecI startPt, IVecI midPt, IVecI endPt, IVecI normal){
	super(s, new IArcGeo(center,startPt,midPt,endPt,normal));
    }
    
    
    
    
    /******************************************************************************
     * IObject methods
     ******************************************************************************/
    
    public IArc name(String nm){ super.name(nm); return this; }
    public IArc layer(ILayer l){ super.layer(l); return this; }
    public IArc layer(String l){ super.layer(l); return this; }

    public IArc attr(IAttribute at){ super.attr(at); return this; }
    
    
    public IArc hide(){ super.hide(); return this; }
    public IArc show(){ super.show(); return this; }
    

    public IArc clr(IColor c){ super.clr(c); return this; }
    public IArc clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IArc clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IArc clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    
    public IArc clr(Color c){ super.clr(c); return this; }
    public IArc clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IArc clr(int gray){ super.clr(gray); return this; }
    public IArc clr(float fgray){ super.clr(fgray); return this; }
    public IArc clr(double dgray){ super.clr(dgray); return this; }
    public IArc clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IArc clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IArc clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IArc clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IArc clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IArc clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IArc clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IArc clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IArc clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IArc hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IArc hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IArc hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IArc hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IArc setColor(Color c){ super.setColor(c); return this; }
    public IArc setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IArc setColor(int gray){ super.setColor(gray); return this; }
    public IArc setColor(float fgray){ super.setColor(fgray); return this; }
    public IArc setColor(double dgray){ super.setColor(dgray); return this; }
    public IArc setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IArc setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IArc setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IArc setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IArc setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IArc setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IArc setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IArc setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IArc setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IArc setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IArc setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IArc setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IArc setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public IArc weight(double w){ super.weight(w); return this; }
    public IArc weight(float w){ super.weight(w); return this; }
    
}
