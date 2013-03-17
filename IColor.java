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
   Abstracted color class in case ATW not provided.
      
   @author Satoru Sugihara
*/
public class IColor{
    //public float r, g, b, a;
    public float[] rgba;
    
    public IColor(){ rgba = new float[4]; }
    public IColor(float r, float g, float b, float a){
	this();
	set(r,g,b,a);
    }
    public IColor(float r, float g, float b){ this(r,g,b,1f); }
    public IColor(float grey, float a){ this(grey,grey,grey,a); }
    public IColor(float grey){ this(grey,grey,grey); }
    
    public IColor(double r, double g, double b, double a){
	this();
	set(r,g,b,a);
    }
    public IColor(double r, double g, double b){ this(r,g,b,1f); }
    public IColor(double grey, double a){ this(grey,grey,grey,a); }
    public IColor(double grey){ this(grey,grey,grey); }
    
    public IColor(int r, int g, int b, int a){
	this();
	set((float)r/255, (float)g/255, (float)b/255, (float)a/255);
    }
    public IColor(int r, int g, int b){ this(r,g,b,255); }
    public IColor(int grey, int a){ this(grey,grey,grey,a); }
    public IColor(int grey){ this(grey,grey,grey); }
    
    public IColor(IColor c){ this(c.r(), c.g(), c.b(), c.a()); }
    public IColor(IColor c, float alpha){ this(c.r(), c.g(), c.b(), alpha); }
    public IColor(IColor c, double alpha){ this(c.r(), c.g(), c.b(), (float)alpha); }
    public IColor(IColor c, int alpha){ this(c.r(), c.g(), c.b(), (float)alpha/255); }
    
    
    public IColor(Color c){ this(c.getRed(), c.getGreen(), c.getBlue(),c.getAlpha()); }
    public IColor(Color c, float alpha){
	this((float)c.getRed()/255, (float)c.getGreen()/255, (float)c.getBlue()/255, alpha);
    }
    public IColor(Color c, double alpha){ this(c,(float)alpha); }
    public IColor(Color c, int alpha){ this(c.getRed(), c.getGreen(), c.getBlue(),alpha); }
    
    
    
    public IColor set(float r, float g, float b, float a){
	rgba[0] = r; rgba[1] = g; rgba[2] = b; rgba[3] = a; return this;
    }
    public IColor set(double r, double g, double b, double a){
	rgba[0] = (float)r; rgba[1] = (float)g; rgba[2] = (float)b; rgba[3] = (float)a; return this;
    }
    public IColor set(int r, int g, int b, int a){
	rgba[0] = r; rgba[1] = g; rgba[2] = b; rgba[3] = a; return this;
    }
    
    
    public static IColor hsb(float h, float s, float b, float a){
	if(h<0f) h+= (int)(-h+1f); else if(h>1f) h-=(int)h;
	float frac = h*6 - (int)(h*6);
	if(h < 1f/6) return new IColor(b, b*(1f-s*(1f-frac)), b*(1f-s), a);
	if(h < 2f/6) return new IColor(b*(1f-s*frac), b, b*(1f-s), a);
	if(h < 3f/6) return new IColor(b*(1f-s), b, b*(1f-s*(1f-frac)), a);
	if(h < 4f/6) return new IColor(b*(1f-s), b*(1f-s*frac), b, a);
	if(h < 5f/6) return new IColor(b*(1f-s*(1f-frac)),b*(1f-s), b, a);
	return new IColor(b, b*(1f-s), b*(1f-s*frac), a);
    }
    public static IColor hsb(float h, float s, float b){ return hsb(h,s,b,1f); }
    public static IColor hsb(double h, double s, double b, double a){ return hsb((float)h,(float)s,(float)b,(float)a); }
    public static IColor hsb(double h, double s, double b){ return hsb((float)h,(float)s,(float)b,1f); }

    /** returns array of 4 float in the order of RGBA */
    public float[] rgba(){ return rgba; }
    
    /** returns one 32 bit integer in the order of ARGB */
    public int argb(){
	int r = (int)(rgba[0]*255);
	int g = (int)(rgba[1]*255);
	int b = (int)(rgba[2]*255);
	int a = (int)(rgba[3]*255);
	if(r<0) r=0; else if(r>255) r=255;
	if(g<0) g=0; else if(g>255) g=255;
	if(b<0) b=0; else if(b>255) b=255;
	if(a<0) a=0; else if(a>255) a=255;
	return ((a << 24) + (r << 16) + (g << 8) + b);
    }
    /** alias of argb() */
    public int getInt(){ return argb(); }
    /** alias of argb() */
    public int i(){ return argb(); }
    
    public float r(){ return rgba[0]; }
    public float g(){ return rgba[1]; }
    public float b(){ return rgba[2]; }
    public float a(){ return rgba[3]; }
    
    public float red(){ return r(); }
    public float green(){ return g(); }
    public float blue(){ return b(); }
    public float alpha(){ return a(); }
    public float grey(){ return (r()+g()+b())/3; } // brightness
    
    public IColor r(float r){ rgba[0]=r; return this; }
    public IColor g(float g){ rgba[1]=g; return this; }
    public IColor b(float b){ rgba[2]=b; return this; }
    public IColor a(float a){ rgba[3]=a; return this; }
    
    public IColor red(float r){ return r(r); }
    public IColor green(float g){ return g(g); }
    public IColor blue(float b){ return b(b); }
    public IColor alpha(float a){ return a(a); }
    public IColor grey(float grey){ r(grey); g(grey); b(grey); return this; } // brightness
    
    public int getRed(){ return (int)(r()*255); }
    public int getGreen(){ return (int)(g()*255); }
    public int getBlue(){ return (int)(b()*255); }
    public int getAlpha(){ return (int)(a()*255); }
    public int getGrey(){ return (int)((r()+g()+b())*255/3); } // brightness
    
    public Color awt(){
	float r = r();
	float g = g();
	float b = b();
	float a = a();
	if(r<0f){ r=0f; } else if(r>1f){ r=1f; }
	if(g<0f){ g=0f; } else if(g>1f){ g=1f; }
	if(b<0f){ b=0f; } else if(b>1f){ b=1f; }
	if(a<0f){ a=0f; } else if(a>1f){ a=1f; }
	return new Color(r,g,b,a);
    }
    
    public String toString(){
	return "(r="+String.valueOf(rgba[0])+",g="+String.valueOf(rgba[1])+",b="
	    +String.valueOf(rgba[2])+",a="+String.valueOf(rgba[3])+")";
    }
    
    
    public IColor dup(){ return new IColor(this); }
    public IColor cp(){ return dup(); }
    
    public IColor blend(IColor c, float weight){
	rgba[0] = rgba[0]*(1f-weight)+c.r()*weight;
	rgba[1] = rgba[1]*(1f-weight)+c.g()*weight;
	rgba[2] = rgba[2]*(1f-weight)+c.b()*weight;
	rgba[3] = rgba[3]*(1f-weight)+c.a()*weight;
	return this;
    }
    public IColor blend(IColor c){ return blend(c, 0.5f); }
    
    public boolean eq(IColor c){
	return c.r()==r() && c.g()==g() && c.b()==b() && c.a()==a();
    }
    
    public boolean eq(float r, float g, float b, float a){
	return r()==r && g()==g && b()==b && a()==a;
    }
    
    public boolean eq(float r, float g, float b){
	return r()==r && g()==g && b()==b;
    }
    
}
