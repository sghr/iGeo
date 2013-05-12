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
   A super class of misc attributes of IObject, mostly graphic related.
   
   @author Satoru Sugihara
*/
public class IAttribute{
    
    /** ID integer of the object */
    public int id=-1; // should it be String UUID or object instance's hash code?
    /** object's name */
    public String name;
    
    /** object's layer */
    public ILayer layer;
    
    /** object's color */
    public IColor color;
    
    /** object's stroek color; for wireframe + fill surface */
    public IColor stroke;
    
    /** object's size, used as point size or etc */
    public /*double*/ float size=1f;
    
    /** object's line weight */
    public /*double*/ float weight=1f;
    
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
    public IAttribute cp(){ return dup(); }
    
    public IAttribute set(IAttribute attr){
	layer = attr.layer;
	color = attr.color;
	size = attr.size;
	weight = attr.weight;
	material = attr.material;
	visible = attr.visible;
	return this;
    }
    
    public IAttribute merge(IAttribute attr){
	if(layer==null) layer = attr.layer;
	if(color==null) color = attr.color;
	if(size!=1f) size = attr.size; // not default value
	if(weight!=1f) weight = attr.weight; // not default value
	if(material==null) material = attr.material;
	if(!visible) visible = attr.visible; // not default value
	return this;
    }
    
    public void show(){ visible=true; }
    public void hide(){ visible=false; }
    public IAttribute visible(boolean v){ visible=v; return this; }
    public boolean visible(){ return visible; }
    public boolean isVisible(){ return visible(); }
    
    public Color color(){ return color.awt(); }
    public Color awtColor(){ return color(); }
    
    public IColor clr(){ return color; }
    public IAttribute clr(IColor c){ color = c; return this; }
    public IAttribute clr(IColor c, int alpha){ color = new IColor(c,alpha); return this; }
    public IAttribute clr(IColor c, float alpha){ color = new IColor(c,alpha); return this; }
    public IAttribute clr(IColor c, double alpha){ color = new IColor(c,alpha); return this; }
    
    public IAttribute clr(Color c){ color = new IColor(c); return this; }
    public IAttribute clr(Color c, int alpha){ color = new IColor(c,alpha); return this; }
    public IAttribute clr(Color c, float alpha){ color = new IColor(c,alpha); return this; }
    public IAttribute clr(Color c, double alpha){ color = new IColor(c,alpha); return this; }
    
    
    public IAttribute clr(int gray){ color = new IColor(gray); return this; }
    public IAttribute clr(float fgray){ color = new IColor(fgray); return this; }
    public IAttribute clr(double dgray){ clr((float)dgray); return this; }
    public IAttribute clr(int gray, int alpha){ color = new IColor(gray,alpha); return this; }
    public IAttribute clr(float fgray, float falpha){ color = new IColor(fgray,falpha); return this; }
    public IAttribute clr(double dgray, double dalpha){ clr((float)dgray,(float)dalpha); return this; }
    public IAttribute clr(int r, int g, int b){ color = new IColor(r,g,b); return this; }
    public IAttribute clr(float fr, float fg, float fb){ color = new IColor(fr,fg,fb); return this; }
    public IAttribute clr(double dr, double dg, double db){ clr((float)dr,(float)dg,(float)db); return this; }
    public IAttribute clr(int r, int g, int b, int a){ color = new IColor(r,g,b,a); return this; }
    public IAttribute clr(float fr, float fg, float fb, float fa){ color = new IColor(fr,fg,fb,fa); return this; }
    public IAttribute clr(double dr, double dg, double db, double da){ clr((float)dr,(float)dg,(float)db,(float)da); return this; }
    public IAttribute hsb(float h, float s, float b, float a){ color = IColor.hsb(h,s,b,a); return this; }
    public IAttribute hsb(double h, double s, double b, double a){ return hsb((float)h,(float)s,(float)b,(float)a); }
    public IAttribute hsb(float h, float s, float b){ color = IColor.hsb(h,s,b); return this; }
    public IAttribute hsb(double h, double s, double b){ return hsb((float)h,(float)s,(float)b); }
    
    
    public float weight(){ return weight; }
    public IAttribute weight(float w){ weight=w; return this; }
    public IAttribute weight(double w){ weight=(float)w; return this; }
    
    
    public Color getAWTColor(){ return color(); }
    public IColor getColor(){ return clr(); }
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
    
    
    public IAttribute setWeight(float w){ return weight(w); }
    
    
    public String toString(){
	return "id="+id+
	    ", name="+name+
	    ", layer="+layer+
	    ", color="+color+
	    ", stroke="+stroke+
	    ", size="+size+
	    ", weight="+weight+
	    ", material="+material+
	    ", visible="+visible;
    }
}
