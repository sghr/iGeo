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

import igeo.core.*;
import java.util.ArrayList;
import java.awt.Color;


/**
   Class BRep (Boundary Representation) of IObject.
   Implementation of BRep is not complete yet.
   To be completed later.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IBrep extends IObject{
    public ArrayList<ISurface> surfaces;
    
    public IBrep(){ surfaces = new ArrayList<ISurface>(); }
    
    public IBrep add(ISurface s){ surfaces.add(s); return this; }
    
    public ISurface getSurface(int i){ return surfaces.get(i); }
    
    public int surfaceNum(){ return surfaces.size(); }
    
    
    public IBrep name(String nm){
	super.name(nm);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).name(nm+"_s"+i);
	return this;
    }
    public IBrep layer(ILayer l){
	super.layer(l);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).layer(l);
	return this;
    }
    
    public IBrep hide(){
	super.hide();
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).hide();
	return this;
    }
    public IBrep show(){
	super.show();
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).hide();
	return this;
    }
    
    public IBrep clr(Color c){
	super.clr(c);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(c);
	return this;
    }
    public IBrep clr(int gray){
	super.clr(gray);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(gray);
	return this;
    }
    public IBrep clr(float fgray){
	super.clr(fgray);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(fgray);
	return this;
    }
    public IBrep clr(double dgray){
	super.clr(dgray);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(dgray);
	return this;
    }
    public IBrep clr(int gray, int alpha){
	super.clr(gray,alpha);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(gray,alpha);
	return this;
    }
    public IBrep clr(float fgray, float falpha){
	super.clr(fgray,falpha);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(fgray,falpha);
	return this;
    }
    public IBrep clr(double dgray, double dalpha){
	super.clr(dgray,dalpha);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(dgray,dalpha);
	return this;
    }
    public IBrep clr(int r, int g, int b){
	super.clr(r,g,b);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(r,g,b);
	return this;
    }
    public IBrep clr(float fr, float fg, float fb){
	super.clr(fr,fg,fb);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(fr,fg,fb);
	return this;
    }
    public IBrep clr(double dr, double dg, double db){
	super.clr(dr,dg,db);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(dr,dg,db);
	return this;
    }
    public IBrep clr(int r, int g, int b, int a){
	super.clr(r,g,b,a);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(r,g,b,a);
	return this;
    }
    public IBrep clr(float fr, float fg, float fb, float fa){
	super.clr(fr,fg,fb,fa);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(fr,fg,fb,fa);
	return this;
    }
    public IBrep clr(double dr, double dg, double db, double da){
	super.clr(dr,dg,db,da);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).clr(dr,dg,db,da);
	return this;
    }
    public IBrep hsb(float h, float s, float b, float a){
	super.hsb(h,s,b,a);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).hsb(h,s,b,a);
	return this;
    }
    public IBrep hsb(double h, double s, double b, double a){
	super.hsb(h,s,b,a);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).hsb(h,s,b,a);
	return this;
    }
    public IBrep hsb(float h, float s, float b){
	super.hsb(h,s,b);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).hsb(h,s,b);
	return this;
    }
    public IBrep hsb(double h, double s, double b){
	super.hsb(h,s,b);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).hsb(h,s,b);
	return this;
    }
    
    
    public IBrep setColor(Color c){
	super.setColor(c);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(c);
	return this;
    }
    public IBrep setColor(int gray){
	super.setColor(gray);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(gray);
	return this;
    }
    public IBrep setColor(float fgray){
	super.setColor(fgray);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(fgray);
	return this;
    }
    public IBrep setColor(double dgray){
	super.setColor(dgray);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(dgray);
	return this;
    }
    public IBrep setColor(int gray, int alpha){
	super.setColor(gray,alpha);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(gray,alpha);
	return this;
    }
    public IBrep setColor(float fgray, int falpha){
	super.setColor(fgray,falpha);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(fgray,falpha);
	return this;
    }
    public IBrep setColor(double dgray, double dalpha){
	super.setColor(dgray,dalpha);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(dgray,dalpha);
	return this;
    }
    public IBrep setColor(int r, int g, int b){
	super.setColor(r,g,b);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(r,g,b);
	return this;
    }
    public IBrep setColor(float fr, float fg, float fb){
	super.setColor(fr,fg,fb);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(fr,fg,fb);
	return this;
    }
    public IBrep setColor(double dr, double dg, double db){
	super.setColor(dr,dg,db);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(dr,dg,db);
	return this;
    }
    public IBrep setColor(int r, int g, int b, int a){
	super.setColor(r,g,b,a);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(r,g,b,a);
	return this;
    }
    public IBrep setColor(float fr, float fg, float fb, float fa){
	super.setColor(fr,fg,fb,fa);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(fr,fg,fb,fa);
	return this;
    }
    public IBrep setColor(double dr, double dg, double db, double da){
	super.setColor(dr,dg,db,da);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setColor(dr,dg,db,da);
	return this;
    }
    public IBrep setHSBColor(float h, float s, float b, float a){
	super.setHSBColor(h,s,b,a);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setHSBColor(h,s,b,a);
	return this;
    }
    public IBrep setHSBColor(double h, double s, double b, double a){
	super.setHSBColor(h,s,b,a);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setHSBColor(h,s,b,a);
	return this;
    }
    public IBrep setHSBColor(float h, float s, float b){
	super.setHSBColor(h,s,b);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setHSBColor(h,s,b);
	return this;
    }
    public IBrep setHSBColor(double h, double s, double b){
	super.setHSBColor(h,s,b);
	for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).setHSBColor(h,s,b);
	return this;
    }
    
    
    
}
