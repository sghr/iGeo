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
   Transformable objects.
   
   @author Satoru Sugihara
*/

public abstract class IGeometry extends IObject implements ITransformable{
    
    public IGeometry(){ super(); }
    public IGeometry(IServerI holder){ super(holder); }
    
    public IGeometry(IGeometry e){ super((IObject)e); }
    public IGeometry(IServerI holder, IGeometry e){ super(holder,(IObject)e); }
    
    public IGeometry dup(){ return null; } // need to be overridden
    public IGeometry cp(){ return dup(); } // need to be overridden
    
    
    /** returns center of geometry object */
    abstract public IVecI center(); 
    

    /****************************************************
     * IObject API
     ***************************************************/
    
    
    /** Set layer by ILayer object */
    public IGeometry layer(ILayer l){ super.layer(l); return this; }
    
    /** Set layer by layer name. If the layer specified by the name is not existing in the server, a new layer is automatically created in the server */
    public IGeometry layer(String layerName){ super.layer(layerName); return this; }
    public IGeometry attr(IAttribute at){ super.attr(at); return this; }
    
    public IGeometry hide(){ super.hide(); return this; }
    public IGeometry show(){ super.show(); return this; }
    
    public IGeometry clr(IColor c){ super.clr(c); return this; }
    public IGeometry clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IGeometry clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IGeometry clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IGeometry clr(Color c){ super.clr(c); return this; }
    public IGeometry clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IGeometry clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public IGeometry clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IGeometry clr(int gray){ super.clr(gray); return this; }
    public IGeometry clr(double dgray){ super.clr(dgray); return this; }
    public IGeometry clr(float fgray){ super.clr(fgray); return this; }
    
    public IGeometry clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IGeometry clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IGeometry clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IGeometry clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IGeometry clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IGeometry clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IGeometry clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IGeometry clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IGeometry clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IGeometry hsb(double dh, double ds, double db, double da){ super.hsb(dh,ds,db,da); return this; }
    public IGeometry hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IGeometry hsb(double dh, double ds, double db){ super.hsb(dh,ds,db); return this; }
    public IGeometry hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    
    
    public IGeometry setColor(Color c){ return clr(c); }
    public IGeometry setColor(Color c, int alpha){ return clr(c,alpha); }
    public IGeometry setColor(int gray){ return clr(gray); }
    public IGeometry setColor(float fgray){ return clr(fgray); }
    public IGeometry setColor(double dgray){ return clr(dgray); }
    public IGeometry setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IGeometry setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IGeometry setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IGeometry setColor(int r, int g, int b){ return clr(r,g,b); }
    public IGeometry setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IGeometry setColor(double dr, double dg, double db){ return clr(dr,dg,db); }
    public IGeometry setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IGeometry setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IGeometry setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IGeometry setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IGeometry setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IGeometry setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IGeometry setHSBColor(double h, double s, double b){ return hsb(h,s,b); }
    
    public IGeometry weight(double w){ super.weight(w); return this; }
    public IGeometry weight(float w){ super.weight(w); return this; }
    
    
    /****************************************************
     * ITransformable API
     ***************************************************/
    
    abstract public IGeometry add(double x, double y, double z);
    abstract public IGeometry add(IDoubleI x, IDoubleI y, IDoubleI z);
    abstract public IGeometry add(IVecI v);
    abstract public IGeometry sub(double x, double y, double z);
    abstract public IGeometry sub(IDoubleI x, IDoubleI y, IDoubleI z);
    abstract public IGeometry sub(IVecI v);
    abstract public IGeometry mul(IDoubleI v);
    abstract public IGeometry mul(double v);
    abstract public IGeometry div(IDoubleI v);
    abstract public IGeometry div(double v);
    
    abstract public IGeometry neg();
    /** alias of neg */
    //abstract public IGeometry rev(); // rev is used in curve to revrse u parameter
    /** alias of neg */
    abstract public IGeometry flip();
    
    
    
    /** scale add */
    abstract public IGeometry add(IVecI v, double f);
    /** scale add */
    abstract public IGeometry add(IVecI v, IDoubleI f); 
    /** scale add alias */
    abstract public IGeometry add(double f, IVecI v);
    /** scale add alias */
    abstract public IGeometry add(IDoubleI f, IVecI v); 
    
    /** rotation around z-axis and origin */
    abstract public IGeometry rot(IDoubleI angle);
    abstract public IGeometry rot(double angle);
    
    /** rotation around axis vector */
    abstract public IGeometry rot(IVecI axis, IDoubleI angle);
    abstract public IGeometry rot(IVecI axis, double angle);
    
    /** rotation around axis vector and center */
    abstract public IGeometry rot(IVecI center, IVecI axis, IDoubleI angle);
    abstract public IGeometry rot(IVecI center, IVecI axis, double angle);
    
    /** rotate to destination direction vector */
    abstract public IGeometry rot(IVecI axis, IVecI destDir);
    /** rotate to destination point location */    
    abstract public IGeometry rot(IVecI center, IVecI axis, IVecI destPt);
    
    
    /** rotation on xy-plane around origin; same with rot(IDoubleI) */
    abstract public IGeometry rot2(IDoubleI angle);
    /** rotation on xy-plane around origin; same with rot(double) */
    abstract public IGeometry rot2(double angle);
    
    /** rotation on xy-plane around center */
    abstract public IGeometry rot2(IVecI center, IDoubleI angle);
    abstract public IGeometry rot2(IVecI center, double angle);
    
    /** rotation on xy-plane to destination direction vector */
    abstract public IGeometry rot2(IVecI destDir);
    /** rotation on xy-plane to destination point location */    
    abstract public IGeometry rot2(IVecI center, IVecI destPt);
    
    
    
    /** alias of mul */
    abstract public IGeometry scale(IDoubleI f);
    abstract public IGeometry scale(double f);
    abstract public IGeometry scale(IVecI center, IDoubleI f);
    abstract public IGeometry scale(IVecI center, double f);

    
    /** scale only in 1 direction */
    abstract public IGeometry scale1d(IVecI axis, double f);
    abstract public IGeometry scale1d(IVecI axis, IDoubleI f);
    abstract public IGeometry scale1d(IVecI center, IVecI axis, double f);
    abstract public IGeometry scale1d(IVecI center, IVecI axis, IDoubleI f);
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    abstract public IGeometry ref(IVecI planeDir);
    abstract public IGeometry ref(IVecI center, IVecI planeDir);
    /** mirror is alias of ref */
    abstract public IGeometry mirror(IVecI planeDir);
    abstract public IGeometry mirror(IVecI center, IVecI planeDir);
    
    
    /** shear operation */
    abstract public IGeometry shear(double sxy, double syx, double syz,
				double szy, double szx, double sxz);
    abstract public IGeometry shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    abstract public IGeometry shear(IVecI center, double sxy, double syx, double syz,
				double szy, double szx, double sxz);
    abstract public IGeometry shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
				IDoubleI szy, IDoubleI szx, IDoubleI sxz);
    
    abstract public IGeometry shearXY(double sxy, double syx);
    abstract public IGeometry shearXY(IDoubleI sxy, IDoubleI syx);
    abstract public IGeometry shearXY(IVecI center, double sxy, double syx);
    abstract public IGeometry shearXY(IVecI center, IDoubleI sxy, IDoubleI syx);
    
    abstract public IGeometry shearYZ(double syz, double szy);
    abstract public IGeometry shearYZ(IDoubleI syz, IDoubleI szy);
    abstract public IGeometry shearYZ(IVecI center, double syz, double szy);
    abstract public IGeometry shearYZ(IVecI center, IDoubleI syz, IDoubleI szy);
    
    abstract public IGeometry shearZX(double szx, double sxz);
    abstract public IGeometry shearZX(IDoubleI szx, IDoubleI sxz);
    abstract public IGeometry shearZX(IVecI center, double szx, double sxz);
    abstract public IGeometry shearZX(IVecI center, IDoubleI szx, IDoubleI sxz);
    
    /** mv() is alias of add() */
    abstract public IGeometry mv(double x, double y, double z);
    abstract public IGeometry mv(IDoubleI x, IDoubleI y, IDoubleI z);
    abstract public IGeometry mv(IVecI v);
    
    
    /** cp() is alias of dup().add() */
    abstract public IGeometry cp(double x, double y, double z);
    abstract public IGeometry cp(IDoubleI x, IDoubleI y, IDoubleI z);
    abstract public IGeometry cp(IVecI v);
    
    
    /** translate() is alias of add() */
    abstract public IGeometry translate(double x, double y, double z);
    abstract public IGeometry translate(IDoubleI x, IDoubleI y, IDoubleI z);
    abstract public IGeometry translate(IVecI v);
    
    
    abstract public IGeometry transform(IMatrix3I mat);
    abstract public IGeometry transform(IMatrix4I mat);
    abstract public IGeometry transform(IVecI xvec, IVecI yvec, IVecI zvec);
    abstract public IGeometry transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate);
    
}
