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
   A super class of misc attributes of IObject, mostly graphic related.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IAttribute{
    
    /** ID integer of the object */
    public int id=-1; // should it be String UUID or object instance's hash code?
    /** object's name */
    public String name;
    
    /** object's layer */
    public ILayer layer;
    
    /** object's color */
    public Color color;
    
    /** object's size, used as point size or etc */
    public double size=1.0;
    
    /** object's line weight */
    public double weight=1.0;
    
    /** object's render material */
    public IMaterial material;
    
    /** visibility switch of the object */
    public boolean visible=true;

    public IAttribute(){}
    
    public IAttribute(IAttribute attr){
	//id=-1;
	//name;
	layer = attr.layer;
	color = attr.color;
	size = attr.size;
	weight = attr.weight;
	material = attr.material;
	visible = attr.visible;
    }
    
    public IAttribute dup(){ return new IAttribute(this); }
    
    public Color clr(){ return color; }
    public IAttribute clr(Color c){ color = c; return this; }
    public IAttribute clr(Color c, int alpha){ color = IGraphicObject.getColor(c,alpha); return this; }
    public IAttribute clr(int gray){ color = IGraphicObject.getColor(gray); return this; }
    public IAttribute clr(float fgray){ color = IGraphicObject.getColor(fgray); return this; }
    public IAttribute clr(double dgray){ clr((float)dgray); return this; }
    public IAttribute clr(int gray, int alpha){ color = IGraphicObject.getColor(gray,alpha); return this; }
    public IAttribute clr(float fgray, float falpha){ color = IGraphicObject.getColor(fgray,falpha); return this; }
    public IAttribute clr(double dgray, double dalpha){ clr((float)dgray,(float)dalpha); return this; }
    public IAttribute clr(int r, int g, int b){ color = IGraphicObject.getColor(r,g,b); return this; }
    public IAttribute clr(float fr, float fg, float fb){ color = IGraphicObject.getColor(fr,fg,fb); return this; }
    public IAttribute clr(double dr, double dg, double db){ clr((float)dr,(float)dg,(float)db); return this; }
    public IAttribute clr(int r, int g, int b, int a){ color = IGraphicObject.getColor(r,g,b,a); return this; }
    public IAttribute clr(float fr, float fg, float fb, float fa){ color = IGraphicObject.getColor(fr,fg,fb,fa); return this; }
    public IAttribute clr(double dr, double dg, double db, double da){ clr((float)dr,(float)dg,(float)db,(float)da); return this; }
    public IAttribute hsb(float h, float s, float b, float a){ color = IGraphicObject.getHSBColor(h,s,b,a); return this; }
    public IAttribute hsb(double h, double s, double b, double a){ hsb((float)h,(float)s,(float)b,(float)a); return this; }
    public IAttribute hsb(float h, float s, float b){ color = IGraphicObject.getHSBColor(h,s,b); return this; }
    public IAttribute hsb(double h, double s, double b){ hsb((float)h,(float)s,(float)b); return this; }
    
    
    public Color getColor(){ return clr(); }
    public IAttribute setColor(Color c){ return clr(c); }
    public IAttribute setColor(int gray){  return clr(gray); }
    public IAttribute setColor(float fgray){  return clr(fgray); }
    public IAttribute setColor(double dgray){ return clr(dgray); }
    public IAttribute setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IAttribute setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IAttribute setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IAttribute setColor(int r, int g, int b){ return clr(r,g,b); }
    public IAttribute setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IAttribute setColor(double dr, double dg, double db){  return clr(dr,dg,db); }
    public IAttribute setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IAttribute setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IAttribute setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IAttribute setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IAttribute setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IAttribute setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IAttribute setHSBColor(double h, double s, double b){ return hsb(h,s,b); }
    
}
