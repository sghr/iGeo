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

import processing.core.PFont; //!


public class IText extends IObject{
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
    
    
    public void initText(IServerI s){
	parameter = text;
	if(graphics==null) initGraphic(s);
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
	if(m.isNone()) return null;
	if(m.isGraphic3D()) return new ITextGraphicGL(this);
	return null;
    }
    
    public IVec corner(int i, int j){
	for(int k=0; k<graphicsNum(); k++){
	    if(getGraphic(k) instanceof ITextGraphicGL){
		return ((ITextGraphicGL)getGraphic(k)).corner(i,j);
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
    
}
