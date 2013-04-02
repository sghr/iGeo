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

/** 
    Class of a text object in 3D space
    @author Satoru Sugihara
*/

import igeo.gui.*;
import java.awt.Font;
import java.awt.Color;

import processing.core.PFont; //!


public class IText extends IGeometry{
    public static final double defaultTextSize=1.0;
    
    public ITextGeo text;
    public Font font;
    
    public IText(IServerI s, String str, double fontSize, IVecI pos, IVecI textDir, IVecI textUpDir){
	super(s);
	text = new ITextGeo(str,fontSize,pos,textDir,textUpDir);
	initText(s);
    }
    
    public IText(IServerI s, String str, double fontSize, IVecI pos, IVecI textDir){
	super(s);
	text = new ITextGeo(str,fontSize,pos,textDir);
	initText(s);
    }
    
    public IText(IServerI s, String str, double fontSize, IVecI pos){
	super(s);
	text = new ITextGeo(str,fontSize,pos);
	initText(s);
    }
    
    /**
       @param uvec  direction of text whose length is font size.
       @param vvec  direction of text height whose length is font size.
    */
    public IText(IServerI s, String str, IVecI pos, IVecI uvec, IVecI vvec){
	super(s);
	text = new ITextGeo(str,pos,uvec,vvec);
	initText(s);
    }
    
    public IText(IServerI s, String str, double fontSize,
		 double x, double y, double z,
		 double textDirX, double textDirY, double textDirZ,
		 double textUpDirX, double textUpDirY, double textUpDirZ){
	this(s,str,fontSize,new IVec(x,y,z), new IVec(textDirX,textDirY,textDirZ),
	     new IVec(textUpDirX,textUpDirY,textUpDirZ));
    }
    
    public IText(IServerI s, String str, double fontSize,
		 double x, double y, double z,
		 double textDirX, double textDirY, double textDirZ){
	this(s,str,fontSize,new IVec(x,y,z), new IVec(textDirX,textDirY,textDirZ));
    }
    
    public IText(IServerI s, String str, double fontSize, double x, double y, double z){
	this(s,str,fontSize,new IVec(x,y,z));
    }
    
    public IText(IServerI s, IText text){
	super(s);
	this.text = new ITextGeo(text.text);
	font = text.font; // sharrow copy ; ////font = new Font(text.font);
    }
    
    
    public IText(String str, double fontSize, IVecI pos, IVecI textDir, IVecI textUpDir){
	this((IServerI)null,str,fontSize,pos,textDir,textUpDir);
    }
    
    public IText(String str, double fontSize, IVecI pos, IVecI textDir){
	this((IServerI)null,str,fontSize,pos,textDir);
    }
    
    public IText(String str, double fontSize, IVecI pos){
	this((IServerI)null,str,fontSize,pos);
    }
    
    
    /**
       @param uvec  direction of text whose length is font size.
       @param vvec  direction of text height whose length is font size.
    */
    public IText(String str, IVecI pos, IVecI uvec, IVecI vvec){
	this((IServerI)null,str,pos,uvec,vvec);
    }
    
    public IText(String str, double fontSize,
		 double x, double y, double z,
		 double textDirX, double textDirY, double textDirZ,
		 double textUpDirX, double textUpDirY, double textUpDirZ){
	this((IServerI)null,str,fontSize,new IVec(x,y,z), new IVec(textDirX,textDirY,textDirZ),
	     new IVec(textUpDirX,textUpDirY,textUpDirZ));
    }
    
    public IText(String str, double fontSize,
		 double x, double y, double z,
		 double textDirX, double textDirY, double textDirZ){
	this((IServerI)null,str,fontSize,new IVec(x,y,z), new IVec(textDirX,textDirY,textDirZ));
    }
    
    public IText(String str, double fontSize, double x, double y, double z){
	this((IServerI)null,str,fontSize,new IVec(x,y,z));
    }
    
    
    public IText(IText text){ this(text.server(),text); }
    
    
    public IText dup(){ return new IText(this); }
    /** alias of dup() */
    public IText cp(){ return dup(); }
    
    public void initText(IServerI s){
	parameter = text;
	if(graphics==null) initGraphic(s);
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
	if(m.isNone()) return null;
	if(m.isGraphic3D()) return new ITextGraphicGL(this);
	return null;
    }
    
    /** 
	@param i 0 is left corner, 1 is right corner
	@param j 0 is bottom corner, 1 is top corner
    */
    public IVec corner(int i, int j){
	for(int k=0; k<graphicsNum(); k++){
	    if(getGraphic(k) instanceof ITextGraphicGL){
		IVec c = ((ITextGraphicGL)getGraphic(k)).corner(i,j);
		if(c!=null) return c;
	    }
	}
	return pos().cp();
    }
    
    
    public Font font(){ return font; }
    public IText font(Font f){
	font=f;
	updateGraphic();
	return this;
    }
    public IText font(PFont f){ return font(f.getFont()); }
    public IText font(String fontName, int fontStyle, int fontPixelResolution){
	return font(new Font(fontName, fontStyle, fontPixelResolution)); 
    }
    public IText font(String fontName, int fontStyle){
	return font(new Font(fontName, fontStyle, ITextGraphicGL.defaultFontResolution)); 
    }
    public IText font(String fontName){
	return font(new Font(fontName, Font.PLAIN, ITextGraphicGL.defaultFontResolution)); 
    }
    
    
    
    public IVec pos(){ return text.pos().get(); } // IVec not IVecI?
    public IVec uvec(){ return text.uvec().get(); } // IVec not IVecI?
    public IVec vvec(){ return text.vvec().get(); } // IVec not IVecI?
    
    public IText pos(IVecI v){ text.pos(v); return this; }
    public IText uvec(IVecI v){ text.uvec(v); return this; }
    public IText vvec(IVecI v){ text.vvec(v); return this; }
    
    public String text(){ return text.text(); }
    public IText text(String txt){
	text.text(txt);
	updateGraphic();
	return this;
    }
    
    public IText alignLeft(){ text.alignLeft(); return this; }
    public IText alignCenter(){ text.alignCenter(); return this; }
    public IText alignRight(){ text.alignRight(); return this; }
    public IText alignTop(){ text.alignTop(); return this; }
    public IText alignMiddle(){ text.alignMiddle(); return this; }
    public IText alignBottom(){ text.alignBottom(); return this; }
    
    public boolean isAlignLeft(){ return text.isAlignLeft(); }
    public boolean isAlignCenter(){ return text.isAlignCenter(); }
    public boolean isAlignRight(){ return text.isAlignRight(); }
    public boolean isAlignTop(){ return text.isAlignTop(); }
    public boolean isAlignMiddle(){ return text.isAlignMiddle(); }
    public boolean isAlignBottom(){ return text.isAlignBottom(); }
    
    
    
    /****************************************************
     * IObject API
     ***************************************************/
    
    /** Set layer by ILayer object */
    public IText layer(ILayer l){ super.layer(l); return this; }
    
    /** Set layer by layer name. If the layer specified by the name is not existing in the server, a new layer is automatically created in the server */
    public IText layer(String layerName){ super.layer(layerName); return this; }
    public IText attr(IAttribute at){ super.attr(at); return this; }
    
    public IText hide(){ super.hide(); return this; }
    public IText show(){ super.show(); return this; }
    
    public IText clr(IColor c){ super.clr(c); return this; }
    public IText clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IText clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IText clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IText clr(Color c){ super.clr(c); return this; }
    public IText clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IText clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public IText clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IText clr(int gray){ super.clr(gray); return this; }
    public IText clr(double dgray){ super.clr(dgray); return this; }
    public IText clr(float fgray){ super.clr(fgray); return this; }
    
    public IText clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IText clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IText clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IText clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IText clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IText clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IText clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IText clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IText clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IText hsb(double dh, double ds, double db, double da){ super.hsb(dh,ds,db,da); return this; }
    public IText hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IText hsb(double dh, double ds, double db){ super.hsb(dh,ds,db); return this; }
    public IText hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    
    
    public IText setColor(Color c){ return clr(c); }
    public IText setColor(Color c, int alpha){ return clr(c,alpha); }
    public IText setColor(int gray){ return clr(gray); }
    public IText setColor(float fgray){ return clr(fgray); }
    public IText setColor(double dgray){ return clr(dgray); }
    public IText setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IText setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IText setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IText setColor(int r, int g, int b){ return clr(r,g,b); }
    public IText setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IText setColor(double dr, double dg, double db){ return clr(dr,dg,db); }
    public IText setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IText setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IText setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IText setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IText setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IText setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IText setHSBColor(double h, double s, double b){ return hsb(h,s,b); }
    
    public IText weight(double w){ super.weight(w); return this; }
    public IText weight(float w){ super.weight(w); return this; }
    
    
    /****************************************************
     * ITransformable API
     ***************************************************/
    
    public IText add(double x, double y, double z){ text.add(x,y,z); return this; }
    public IText add(IDoubleI x, IDoubleI y, IDoubleI z){ text.add(x,y,z); return this; }
    public IText add(IVecI v){ text.add(v); return this; }
    public IText sub(double x, double y, double z){ text.sub(x,y,z); return this; }
    public IText sub(IDoubleI x, IDoubleI y, IDoubleI z){ text.sub(x,y,z); return this; }
    public IText sub(IVecI v){ text.sub(v); return this; }
    public IText mul(IDoubleI v){ text.mul(v); return this; }
    public IText mul(double v){ text.mul(v); return this; }
    public IText div(IDoubleI v){ text.div(v); return this; }
    public IText div(double v){ text.div(v); return this; }
    
    public IText neg(){ text.neg(); return this; }
    /** alias of neg */
    public IText rev(){ return neg(); }
    /** alias of neg */
    public IText flip(){ return neg(); }
        
    
    /** scale add */
    public IText add(IVecI v, double f){ text.add(v,f); return this; }
    /** scale add */
    public IText add(IVecI v, IDoubleI f){ text.add(v,f); return this; }
    /** scale add alias */
    public IText add(double f, IVecI v){ text.add(v,f); return this; }
    /** scale add alias */
    public IText add(IDoubleI f, IVecI v){ text.add(v,f); return this; }
    
    /** rotation around z-axis and origin */
    public IText rot(IDoubleI angle){ text.rot(angle); return this; }
    public IText rot(double angle){ text.rot(angle); return this; }
    
    /** rotation around axis vector */
    public IText rot(IVecI axis, IDoubleI angle){ text.rot(axis,angle); return this; }
    public IText rot(IVecI axis, double angle){ text.rot(axis,angle); return this; }
    
    /** rotation around axis vector and center */
    public IText rot(IVecI center, IVecI axis, IDoubleI angle){ text.rot(center,axis,angle); return this; }
    public IText rot(IVecI center, IVecI axis, double angle){ text.rot(center,axis,angle); return this; }
    
    /** rotate to destination direction vector */
    public IText rot(IVecI axis, IVecI destDir){ text.rot(axis,destDir); return this; }
    /** rotate to destination point location */
    public IText rot(IVecI center, IVecI axis, IVecI destPt){ text.rot(center,axis,destPt); return this; }
    
    
    /** rotation on xy-plane around origin; same with rot(IDoubleI) */
    public IText rot2(IDoubleI angle){ text.rot2(angle); return this; }
    /** rotation on xy-plane around origin; same with rot(double) */
    public IText rot2(double angle){ text.rot2(angle); return this; }
    
    /** rotation on xy-plane around center */
    public IText rot2(IVecI center, IDoubleI angle){ text.rot2(center,angle); return this; }
    public IText rot2(IVecI center, double angle){ text.rot2(center,angle); return this; }
    
    /** rotation on xy-plane to destination direction vector */
    public IText rot2(IVecI destDir){ text.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */    
    public IText rot2(IVecI center, IVecI destPt){ text.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    public IText scale(IDoubleI f){ text.scale(f); return this; }
    public IText scale(double f){ text.scale(f); return this; }
    public IText scale(IVecI center, IDoubleI f){ text.scale(center,f); return this; }
    public IText scale(IVecI center, double f){ text.scale(center,f); return this; }
    
    
    /** scale only in 1 direction */
    public IText scale1d(IVecI axis, double f){ text.scale1d(axis,f); return this; }
    public IText scale1d(IVecI axis, IDoubleI f){ text.scale1d(axis,f); return this; }
    public IText scale1d(IVecI center, IVecI axis, double f){ text.scale1d(center,axis,f); return this; }
    public IText scale1d(IVecI center, IVecI axis, IDoubleI f){ text.scale1d(center,axis,f); return this; }
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IText ref(IVecI planeDir){ text.ref(planeDir); return this; }
    public IText ref(IVecI center, IVecI planeDir){ text.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    public IText mirror(IVecI planeDir){ return ref(planeDir); }
    public IText mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    public IText shear(double sxy, double syx, double syz,
		       double szy, double szx, double sxz){
	text.shear(sxy,syx,syz,szy,szx,sxz); return this; 
    }
    public IText shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	text.shear(sxy,syx,syz,szy,szx,sxz); return this; 
    }
    public IText shear(IVecI center, double sxy, double syx, double syz,
		       double szy, double szx, double sxz){
	text.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IText shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
		       IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	text.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IText shearXY(double sxy, double syx){ text.shearXY(sxy,syx); return this; }
    public IText shearXY(IDoubleI sxy, IDoubleI syx){ text.shearXY(sxy,syx); return this; }
    public IText shearXY(IVecI center, double sxy, double syx){ text.shearXY(center,sxy,syx); return this; }
    public IText shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){ text.shearXY(center,sxy,syx); return this; }
    
    public IText shearYZ(double syz, double szy){ text.shearYZ(syz,szy); return this; }
    public IText shearYZ(IDoubleI syz, IDoubleI szy){ text.shearYZ(syz,szy); return this; }
    public IText shearYZ(IVecI center, double syz, double szy){ text.shearYZ(center,syz,szy); return this; }
    public IText shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){ text.shearYZ(center,syz,szy); return this; }
    
    public IText shearZX(double szx, double sxz){ text.shearZX(szx,sxz); return this; }
    public IText shearZX(IDoubleI szx, IDoubleI sxz){ text.shearZX(szx,sxz); return this; }
    public IText shearZX(IVecI center, double szx, double sxz){ text.shearZX(center,szx,sxz); return this; }
    public IText shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){ text.shearZX(center,szx,sxz); return this; }
    
    /** mv() is alias of add() */
    public IText mv(double x, double y, double z){ return add(x,y,z); }
    public IText mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IText mv(IVecI v){ return add(v); }
    
    
    /** cp() is alias of dup().add() */
    public IText cp(double x, double y, double z){ return cp().add(x,y,z); }
    public IText cp(IDoubleI x, IDoubleI y, IDoubleI z){ return cp().add(x,y,z); }
    public IText cp(IVecI v){ return cp().add(v); }
    
    
    /** translate() is alias of add() */
    public IText translate(double x, double y, double z){ return add(x,y,z); }
    public IText translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IText translate(IVecI v){ return add(v); }
    
    public IText transform(IMatrix3I mat){ text.transform(mat); return this; }
    public IText transform(IMatrix4I mat){ text.transform(mat); return this; }
    public IText transform(IVecI xvec, IVecI yvec, IVecI zvec){ text.transform(xvec,yvec,zvec); return this; }
    public IText transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){ text.transform(xvec,yvec,zvec,translate); return this; }
    
    /** returns center of geometry object */
    public IVec center(){ return corner(0,0).add(corner(1,0)).add(corner(0,1)).add(corner(1,1)).div(4); }
    
}
