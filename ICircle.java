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
   Circle object.
   It contains ICircleGeo instance inside.
   
   @author Satoru Sugihara
*/
public class ICircle extends ICurve{
    
    public static int circleDeg(){ return ICircleGeo.circleDeg(); }
    
    public static double[] circleKnots(){ return ICircleGeo.circleKnots(); }
    
    public static IVec4[] circleCP(IVec center, double radius){
        return ICircleGeo.circleCP(center,radius);
    }
    public static IVec4[] circleCP(IVec center, IVec normal, double radius){
        return ICircleGeo.circleCP(center,normal,radius);
    }
    public static IVec4[] circleCP(IVec center, IVec normal, IVec rollDir, double radius){
        return ICircleGeo.circleCP(center,normal,rollDir,radius);
    }
    public static IVec4[] circleCP(IVec center, IVec normal, IVec rollDir,
                                   double xradius, double yradius){
	return ICircleGeo.circleCP(center,normal,rollDir,xradius,yradius);
    }
    
    public static IVec4[] ovalCP(IVec center, IVec xaxis, IVec yaxis){
	return ICircleGeo.ovalCP(center,xaxis,yaxis);
    }
    
    public static IVec[] circleCPApprox(IVec center,IVec normal, IVec rollDir,
                                        double xradius, double yradius){
	return ICircleGeo.circleCPApprox(center,normal,rollDir,xradius,yradius);
    }
    
    public static IVec[] ovalCPApprox(IVec center, IVec xaxis, IVec yaxis){
	return ICircleGeo.ovalCPApprox(center,xaxis,yaxis);
    }
    
    public static ICircle circumcircle(IVecI pt1, IVecI pt2, IVecI pt3){
	return new ICircle(ICircleGeo.circumcircle(pt1,pt2,pt3));
    }
    
    public static ICircle circumcircle(IServer s, IVecI pt1, IVecI pt2, IVecI pt3){
	return new ICircle(s,ICircleGeo.circumcircle(pt1,pt2,pt3));
    }
    
    
    //ICircleGeo circle;
    
    public ICircle(IVecI center, IVecI normal, IDoubleI radius){
	this((IServerI)null,center,normal,radius);
    }
    public ICircle(IVecI center, IVecI normal, double radius){
	this((IServerI)null,center,normal,radius);
    }
    public ICircle(IVecI center, IDoubleI radius){
	this((IServerI)null,center,new IVec(0,0,1),radius);
    }
    public ICircle(IVecI center, double radius){
	this((IServerI)null,center,new IVec(0,0,1),radius);
    }
    public ICircle(double x, double y, double z, double radius){
	this((IServerI)null,new IVec(x,y,z),new IVec(0,0,1),radius);
    }
    
    public ICircle(IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius){
	this((IServerI)null,center,normal,xradius,yradius);
    }
    public ICircle(IVecI center, IVecI normal, double xradius, double yradius){
	this((IServerI)null,center,normal,xradius,yradius);
    }
    public ICircle(IVecI center, IDoubleI xradius, IDoubleI yradius){
	this((IServerI)null,center,new IVec(0,0,1),xradius,yradius);
    }
    public ICircle(IVecI center, double xradius, double yradius){
	this((IServerI)null,center,new IVec(0,0,1),xradius,yradius);
    }
    public ICircle(double x, double y, double z, double xradius, double yradius){
	this((IServerI)null,new IVec(x,y,z),new IVec(0,0,1),xradius,yradius);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, double radius){
	this((IServerI)null,center,normal,rollDir,radius);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius){
	this((IServerI)null,center,normal,rollDir,radius);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius){
	this((IServerI)null,center,normal,rollDir,xradius,yradius);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius){
	this((IServerI)null,center,normal,rollDir,xradius,yradius);
    }
    
    public ICircle(IVecI center, IVecI xradiusVec, IVecI yradiusVec){
	this((IServerI)null,center,xradiusVec,yradiusVec);
    }
    
    
    public ICircle(IVecI center, IVecI normal, IDoubleI radius, boolean approx){
	this((IServerI)null,center,normal,radius,approx);
    }
    public ICircle(IVecI center, IVecI normal, double radius, boolean approx){
	this((IServerI)null,center,normal,radius,approx);
    }
    public ICircle(IVecI center, IDoubleI radius, boolean approx){
	this((IServerI)null,center,new IVec(0,0,1),radius,approx);
    }
    public ICircle(IVecI center, double radius, boolean approx){
	this((IServerI)null,center,new IVec(0,0,1),radius,approx);
    }
    public ICircle(double x, double y, double z, double radius, boolean approx){
	this((IServerI)null,new IVec(x,y,z),new IVec(0,0,1),radius, approx);
    }
    public ICircle(IVecI center, IVecI normal, double xradius, double yradius, boolean approx){
	this((IServerI)null,center,normal,xradius,yradius,approx);
    }
    public ICircle(IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius, boolean approx){
	this((IServerI)null,center,normal,xradius,yradius,approx);
    }
    public ICircle(IVecI center, double xradius, double yradius, boolean approx){
	this((IServerI)null,center,new IVec(0,0,1),xradius,yradius,approx);
    }
    public ICircle(IVecI center, IDoubleI xradius, IDoubleI yradius, boolean approx){
	this((IServerI)null,center,new IVec(0,0,1),xradius,yradius,approx);
    }
    public ICircle(double x, double y, double z, double xradius, double yradius, boolean approx){
	this((IServerI)null,new IVec(x,y,z),new IVec(0,0,1),xradius,yradius,approx);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, double radius, boolean approx){
	this((IServerI)null,center,normal,rollDir,radius,approx);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius, boolean approx){
	this((IServerI)null,center,normal,rollDir,radius,approx);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius, boolean approx){
	this((IServerI)null,center,normal,rollDir,xradius,yradius,approx);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius, boolean approx){
	this((IServerI)null,center,normal,rollDir,xradius,yradius,approx);
    }
    
    public ICircle(IVecI center, IVecI xradiusVec, IVecI yradiusVec, boolean approx){
	this((IServerI)null,center,xradiusVec,yradiusVec,approx);
    }
    
    public ICircle(ICircleGeo cir){ super(cir); }
    
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IDoubleI radius){
	super(s, new ICircleGeo(center,normal,radius,radius,false));
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, double radius){
	super(s, new ICircleGeo(center,normal,new IDouble(radius),false));
    }
    public ICircle(IServerI s, IVecI center, IDoubleI radius){
	this(s,center,new IVec(0,0,1),radius);
    }
    public ICircle(IServerI s, IVecI center, double radius){
	this(s,center,new IVec(0,0,1),radius);
    }
    public ICircle(IServerI s, double x, double y, double z, double radius){
	this(s,new IVec(x,y,z),new IVec(0,0,1),radius);
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius){
	super(s, new ICircleGeo(center,normal,xradius,yradius,false));
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, double xradius, double yradius){
	super(s, new ICircleGeo(center,normal,new IDouble(xradius),new IDouble(yradius),false));
    }
    public ICircle(IServerI s, IVecI center, IDoubleI xradius, IDoubleI yradius){
	super(s, new ICircleGeo(center,new IVec(0,0,1),xradius,yradius,false));
    }
    public ICircle(IServerI s, IVecI center, double xradius, double yradius){
	super(s, new ICircleGeo(center,new IVec(0,0,1),new IDouble(xradius),new IDouble(yradius),false));
    }
    public ICircle(IServerI s, double x, double y, double z, double xradius, double yradius){
	this(s,new IVec(x,y,z),new IVec(0,0,1),xradius,yradius);
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, double radius){
	super(s,new ICircleGeo(center,normal,rollDir,radius,false));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius){
	super(s,new ICircleGeo(center,normal,rollDir,radius,false));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius){
	super(s,new ICircleGeo(center,normal,rollDir,xradius,yradius,false));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius){
	super(s,new ICircleGeo(center,normal,rollDir,xradius,yradius,false));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI xradiusVec, IVecI yradiusVec){
	super(s,new ICircleGeo(center,xradiusVec,yradiusVec,false));
    }
    
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IDoubleI radius, boolean approx){
	super(s, new ICircleGeo(center,normal,radius,radius,approx));
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, double radius, boolean approx){
	super(s, new ICircleGeo(center,normal,new IDouble(radius),approx));
    }
    public ICircle(IServerI s, IVecI center, IDoubleI radius, boolean approx){
	this(s,center,new IVec(0,0,1),radius, approx);
    }
    public ICircle(IServerI s, IVecI center, double radius, boolean approx){
	this(s,center,new IVec(0,0,1),radius, approx);
    }
    public ICircle(IServerI s, double x, double y, double z, double radius, boolean approx){
	this(s,new IVec(x,y,z),new IVec(0,0,1),radius,approx);
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, double xradius, double yradius, boolean approx){
	super(s, new ICircleGeo(center,normal,new IDouble(xradius),new IDouble(yradius),approx));
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius, boolean approx){
	super(s, new ICircleGeo(center,normal,xradius,yradius,approx));
    }
    public ICircle(IServerI s, IVecI center, double xradius, double yradius, boolean approx){
	super(s, new ICircleGeo(center,new IVec(0,0,1),new IDouble(xradius),new IDouble(yradius),approx));
    }
    public ICircle(IServerI s, IVecI center, IDoubleI xradius, IDoubleI yradius, boolean approx){
	super(s, new ICircleGeo(center,new IVec(0,0,1),xradius,yradius,approx));
    }
    public ICircle(IServerI s, double x, double y, double z, double xradius, double yradius,boolean approx){
	this(s,new IVec(x,y,z),new IVec(0,0,1),xradius,yradius,approx);
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, double radius, boolean approx){
	super(s,new ICircleGeo(center,normal,rollDir,radius,approx));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius, boolean approx){
	super(s,new ICircleGeo(center,normal,rollDir,radius,approx));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius, boolean approx){
	super(s,new ICircleGeo(center,normal,rollDir,xradius,yradius,approx));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius, boolean approx){
	super(s,new ICircleGeo(center,normal,rollDir,xradius,yradius,approx));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI xradiusVec, IVecI yradiusVec, boolean approx){
	super(s,new ICircleGeo(center,xradiusVec,yradiusVec,approx));
    }
    
    public ICircle(IServerI s, ICircleGeo cir){ super(s,cir); }
    
    
    
    // name(), layer(), clr() etc.
    
    public ICircle name(String nm){ super.name(nm); return this; }
    public ICircle layer(ILayer l){ super.layer(l); return this; }
    
    public ICircle hide(){ super.hide(); return this; }
    public ICircle show(){ super.show(); return this; }
    
    public ICircle clr(IColor c){ super.clr(c); return this; }
    public ICircle clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public ICircle clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public ICircle clr(IColor c, double alpha){ super.clr(c,alpha); return this; }

    public ICircle clr(Color c){ super.clr(c); return this; }
    public ICircle clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public ICircle clr(int gray){ super.clr(gray); return this; }
    public ICircle clr(float fgray){ super.clr(fgray); return this; }
    public ICircle clr(double dgray){ super.clr(dgray); return this; }
    public ICircle clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public ICircle clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public ICircle clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public ICircle clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public ICircle clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public ICircle clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public ICircle clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public ICircle clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public ICircle clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public ICircle hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public ICircle hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public ICircle hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public ICircle hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public ICircle setColor(Color c){ super.setColor(c); return this; }
    public ICircle setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public ICircle setColor(int gray){ super.setColor(gray); return this; }
    public ICircle setColor(float fgray){ super.setColor(fgray); return this; }
    public ICircle setColor(double dgray){ super.setColor(dgray); return this; }
    public ICircle setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public ICircle setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public ICircle setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public ICircle setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public ICircle setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public ICircle setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public ICircle setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public ICircle setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public ICircle setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public ICircle setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public ICircle setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public ICircle setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public ICircle setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    public ICircle weight(double w){ super.weight(w); return this; }
    public ICircle weight(float w){ super.weight(w); return this; }

}
