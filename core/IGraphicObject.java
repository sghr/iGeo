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

package igeo.core;

import java.awt.*;
import igeo.gui.*;

/**
   A subobject of IObject to draw the object on displays.
   
   @author Satoru Sugihara
   @version 0.7.0.0
*/
abstract public class IGraphicObject /*extends ISubobject*/ implements ISubobject, IGraphicI{
    
    // should be synchronized with color range in processing when used in processing
    static int colorRange1i = 255;  
    static int colorRange2i = 255;
    static int colorRange3i = 255;
    static int colorRange4i = 255;
    static float colorRange1f = 1f;
    static float colorRange2f = 1f;
    static float colorRange3f = 1f;
    static float colorRange4f = 1f;
    
    static public double transparentModeAlpha=0.4;
    
    public Color color;
    public boolean visible=true;
    
    public IObject parent;
    
    public boolean update=false;
    
    
    public IGraphicObject(IObject p){
	parent = p;
	//parent.server.add(this);
    }
    
    // implementation of ISubobject
    public IObject parent(){ return parent; }
    public ISubobject parent(IObject parent){ this.parent=parent; return this; }
    
    
    abstract public void draw(IGraphics g);
    
    abstract public boolean isDrawable(IGraphicMode mode);
    
    
    public boolean isVisible(){ return visible; }    
    public boolean visible(){ return visible; }
    public void hide(){
	visible=false;
	// mainly to update focus bounding box
	if(parent!=null&&parent.server!=null) parent.server.updateState();
    }
    public void show(){
	visible=true;
	// mainly to update focus bounding box
	if(parent!=null&&parent.server!=null) parent.server.updateState();
    }
    
    /** updating graphic when geometry change. actual update happens when it's drawn.*/
    public void update(){ update=true; }
    
    
    public void setColor(Color c){ color=c; }
    
    public Color getColor(){ return color; }
    
    public void setColor(Color c, int alpha){ setColor(getColor(c,alpha)); }
    public void setColor(int gray){ setColor(getColor(gray)); }
    public void setColor(float fgray){ setColor(getColor(fgray)); }
    public void setColor(int gray, int alpha){ setColor(getColor(gray,alpha)); }
    public void setColor(float fgray, float falpha){ setColor(getColor(fgray,falpha)); }
    public void setColor(int r, int g, int b){ setColor(getColor(r,g,b)); }
    public void setColor(float fr, float fg, float fb){ setColor(getColor(fr,fg,fb)); }
    public void setColor(int r, int g, int b, int a){ setColor(getColor(r,g,b,a)); }
    public void setColor(float fr, float fg, float fb, float fa){ setColor(getColor(fr,fg,fb,fa)); }
    public void setHSBColor(float h, float s, float b, float a){ setColor(getHSBColor(h,s,b,a)); }
    public void setHSBColor(float h, float s, float b){ setColor(getHSBColor(h,s,b)); }
    
    
    public static Color getColor(Color c, int alpha){
	return new Color(c.getRed(),c.getGreen(),c.getBlue(),alpha<0?0:alpha>255?255:alpha);
    }
    
    public static Color getColor(int gray){
	if(colorRange1i==255){
	    if(gray<0) gray=0; else if(gray>255) gray=255;
	    return new Color(gray,gray,gray);
	}
	float fgray = (float)gray/colorRange1i;
	if(fgray<0f) fgray=0f; else if(fgray>1f) fgray=1f;
	return new Color(fgray,fgray,fgray);
    }
    
    public static Color getColor(float fgray){
	fgray /= colorRange1f;
	if(fgray<0f) fgray=0f; else if(fgray>1f) fgray=1f; 
	return new Color(fgray,fgray,fgray);
    }
    
    public static Color getColor(int gray, int alpha){
	if(colorRange1i==255 && colorRange4i==255){
	    if(gray<0) gray=0; else if(gray>255) gray=255;
	    if(alpha<0) alpha=0; else if(alpha>255) alpha=255;
	    return new Color(gray,gray,gray,alpha);
	}
	float fgray = (float)gray/colorRange1i;
	float falpha = (float)alpha/colorRange4i;
	if(fgray<0f) fgray=0f; else if(fgray>1f) fgray=1f;
	if(falpha<0f) falpha=0f; else if(falpha>1f) falpha=1f;
	return new Color(fgray,fgray,fgray,falpha);
    }
    
    public static Color getColor(float fgray, float falpha){
	fgray /= colorRange1f; falpha /= colorRange4f;
	if(fgray<0f) fgray=0f; else if(fgray>1f) fgray=1f;
	if(falpha<0f) falpha=0f; else if(falpha>1f) falpha=1f;
	return new Color(fgray,fgray,fgray,falpha);
    }
    
    public static Color getColor(int r, int g, int b){
	if(colorRange1i==255 && colorRange2i==255 && colorRange3i==255){
	    if(r<0) r=0; else if(r>255) r=255;
	    if(g<0) g=0; else if(g>255) g=255;
	    if(b<0) b=0; else if(b>255) b=255;
	    return new Color(r,g,b);
	}
	float fr,fg,fb;
	fr = (float)r/colorRange1i;
	fg = (float)g/colorRange2i;
	fb = (float)b/colorRange3i;
	if(fr<0f) fr=0f; else if(fr>1f) fr=1f;
	if(fg<0f) fg=0f; else if(fg>1f) fg=1f;
	if(fb<0f) fb=0f; else if(fb>1f) fb=1f;
	return new Color(fr,fg,fb);
    }
    
    public static Color getColor(float fr, float fg, float fb){
	fr /= colorRange1f; fg /= colorRange2f; fg /= colorRange3f;
	if(fr<0f) fr=0f; else if(fr>1f) fr=1f;
	if(fg<0f) fg=0f; else if(fg>1f) fg=1f;
	if(fb<0f) fb=0f; else if(fb>1f) fb=1f;
	return new Color(fr,fg,fb);
    }
    
    public static Color getColor(int r, int g, int b, int a){
	if(colorRange1i==255 && colorRange2i==255 && colorRange3i==255 && colorRange4i==255){
	    if(r<0) r=0; else if(r>255) r=255;
	    if(g<0) g=0; else if(g>255) g=255;
	    if(b<0) b=0; else if(b>255) b=255;
	    if(a<0) a=0; else if(a>255) a=255;
	    return new Color(r,g,b,a);
	}
	float fr,fg,fb,fa;
	fr = (float)r/colorRange1i;
	fg = (float)g/colorRange2i;
	fb = (float)b/colorRange3i;
	fa = (float)b/colorRange4i;
	if(fr<0f) fr=0f; else if(fr>1f) fr=1f;
	if(fg<0f) fg=0f; else if(fg>1f) fg=1f;
	if(fb<0f) fb=0f; else if(fb>1f) fb=1f;
	if(fa<0f) fa=0f; else if(fa>1f) fa=1f;
	return new Color(fr,fg,fb,fa);
    }
    
    public static Color getColor(float fr, float fg, float fb, float fa){
	fr /= colorRange1f; fg /= colorRange2f; fg /= colorRange3f; fa /= colorRange4f;
	if(fr<0f) fr=0f; else if(fr>1f) fr=1f;
	if(fg<0f) fg=0f; else if(fg>1f) fg=1f;
	if(fb<0f) fb=0f; else if(fb>1f) fb=1f;
	if(fa<0f) fa=0f; else if(fa>1f) fa=1f;
	return new Color(fr,fg,fb,fa);
    }
    
    /**
       @param h hue [0-1]
       @param s saturation [0-1]
       @param b brightness [0-1]
       @param a alpha [0-1]
    */
    public static Color getHSBColor(float h, float s, float b, float a){
	int rgb = Color.HSBtoRGB(h,s,b);
	int ai = (int)(a*255);
	if(ai<0) ai=0; else if(ai>255) ai=255;
	return new Color( (rgb>>>16)&0xFF, (rgb>>>8)&0xFF, rgb&0xFF, ai);
    }
    
    /**
       @param h hue [0-1]
       @param s saturation [0-1]
       @param b brightness [0-1]
    */
    public static Color getHSBColor(float h, float s, float b){
	return new Color(Color.HSBtoRGB(h,s,b));
    }
    
    
}
