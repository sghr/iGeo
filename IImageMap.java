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

import igeo.gui.*;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

/**
   A subclass of IMap defined by a bitmap image.
   
   @author Satoru Sugihara
*/
public class IImageMap extends IDoubleMap{
    public Image image;
    
    public int[][] colorMap;
    
    public IImageMap(String imgFile){ initMap(imgFile); }
    public IImageMap(Image img){ initMap(img); }
    public IImageMap(String imgFile, Component mediaComponent){ initMap(imgFile, mediaComponent); }
    
    public void initMap(String imageFile){
	initMap(IImageLoader.getImage(imageFile));
    }
    public void initMap(String imageFile, Component comp){
	initMap(IImageLoader.getImage(imageFile, comp));
    }
    public void initMap(Image mapImage){
	image = mapImage;
	width = mapImage.getWidth(IImageLoader.observer);
	height = mapImage.getHeight(IImageLoader.observer);
	
	int[] pix = new int[width*height];
	
	PixelGrabber pg = new PixelGrabber(mapImage, 0, 0, -1, -1, pix, 0, width);
	if(pg!=null) try{ pg.grabPixels(); } catch(Exception e){ e.printStackTrace(); }
	
	super.initMap(width,height);

	colorMap = new int[width][height]; // added 20111005
	
	for(int i=0; i<height; i++){
	    for(int j=0; j<width; j++){
		super.set(j, i, getColorValue(pix, j, i, width));
		
		colorMap[j][i]=pix[width*i+j];
	    }
	}
    }
    /*
    public void initColorMap(){
	int[] pix = new int[width*height];
	
	PixelGrabber pg = new PixelGrabber(image, 0, 0, -1, -1, pix, 0, width);
	if(pg!=null) try{ pg.grabPixels(); } catch(Exception e){ e.printStackTrace(); }
	
	colorMap = new int[width][height];
	
	for(int i=0; i<height; i++) for(int j=0; j<width; j++) colorMap[j][i]=pix[width*i+j];
    }
    */
    static public Color getColor(int aRGB){
	// ARGB
	int a = ( aRGB >> 24 ) & 0xff ;
	int r = ( aRGB >> 16 ) & 0xff ;
	int g = ( aRGB >> 8 ) & 0xff ;
	int b = ( aRGB ) & 0xff ;
	return new Color(r,g,b,a);
    }
    
    static public double getColorValue(int[] pixel, int x, int y, int w){
	int[] color =new int[4];
	int aRGB = pixel[ w*y + x ] ;
	color[0] = ( aRGB >> 24 ) & 0xff ;
	color[1] = ( aRGB >> 16 ) & 0xff ;
	color[2] = ( aRGB >> 8 ) & 0xff ;
	color[3] = ( aRGB ) & 0xff ;
	
	// RGBA?
	// ARGB?
	
	// RGBA
	//return (double)(color[0]+color[1]+color[2])/(3*255)*((double)color[3]/255);
	// ARGB
	return (double)(color[1]+color[2]+color[3])/(3*255)*((double)color[0]/255);
    }
    
    public void initDensityMapU(){ initDensityMapU(this.width, this.height); }
    public void initDensityMapV(){ initDensityMapV(this.width, this.height); }

    public Color getColor(double u, double v){ return clr(u,v); }
    public Color clr(double u, double v){
	if(interpolation){
            int ui = (int)((width-1)*u);
            int vi = (int)((height-1)*v);
            if(ui<0) ui=0; else if(ui>=width) ui=width-1;
            if(vi<0) vi=0; else if(vi>=height) vi=height-1;
	    
            double ur = (double)ui/(width-1) - u;
            double vr = (double)vi/(height-1) - v;
            
            if(ur==0 || ui==width-1){
                if(vr==0 || vi==height-1) return getColor(colorMap[ui][vi]);
		Color color1 = getColor(colorMap[ui][vi]);
		Color color2 = getColor(colorMap[ui][vi+1]);
		int r = (int)(color1.getRed()*(1-vr) + color2.getRed()*vr);
		int g = (int)(color1.getGreen()*(1-vr) + color2.getGreen()*vr);
		int b = (int)(color1.getBlue()*(1-vr) + color2.getBlue()*vr);
		int a = (int)(color1.getAlpha()*(1-vr) + color2.getAlpha()*vr);
		if(r<0) r=0; else if(r>255) r=255;
		if(g<0) g=0; else if(g>255) g=255;
		if(b<0) b=0; else if(b>255) b=255;
		if(a<0) a=0; else if(a>255) a=255;
		return new Color(r,g,b,a);
            }
            
            if(vr==0 || vi==height-1){
		Color color1 = getColor(colorMap[ui][vi]);
		Color color2 = getColor(colorMap[ui+1][vi]);
		int r = (int)(color1.getRed()*(1-ur) + color2.getRed()*ur);
		int g = (int)(color1.getGreen()*(1-ur) + color2.getGreen()*ur);
		int b = (int)(color1.getBlue()*(1-ur) + color2.getBlue()*ur);
		int a = (int)(color1.getAlpha()*(1-ur) + color2.getAlpha()*ur);
		if(r<0) r=0; else if(r>255) r=255;
		if(g<0) g=0; else if(g>255) g=255;
		if(b<0) b=0; else if(b>255) b=255;
		if(a<0) a=0; else if(a>255) a=255;
		return new Color(r,g,b,a);
	    }
	    
	    Color color11 = getColor(colorMap[ui][vi]);
	    Color color21 = getColor(colorMap[ui+1][vi]);
	    Color color12 = getColor(colorMap[ui][vi+1]);
	    Color color22 = getColor(colorMap[ui+1][vi+1]);
	    
	    int r = (int)((color11.getRed()*(1-ur) + color21.getRed()*ur)*(1-vr) +
			  (color12.getRed()*(1-ur) + color22.getRed()*ur)*vr);
	    int g = (int)((color11.getGreen()*(1-ur) + color21.getGreen()*ur)*(1-vr) +
			  (color12.getGreen()*(1-ur) + color22.getGreen()*ur)*vr);
	    int b = (int)((color11.getBlue()*(1-ur) + color21.getBlue()*ur)*(1-vr) +
			  (color12.getBlue()*(1-ur) + color22.getBlue()*ur)*vr);
	    int a = (int)((color11.getAlpha()*(1-ur) + color21.getAlpha()*ur)*(1-vr) +
			  (color12.getAlpha()*(1-ur) + color22.getAlpha()*ur)*vr);
	    
	    if(r<0) r=0; else if(r>255) r=255;
	    if(g<0) g=0; else if(g>255) g=255;
	    if(b<0) b=0; else if(b>255) b=255;
	    if(a<0) a=0; else if(a>255) a=255;
	    return new Color(r,g,b,a);
        }
        int ui = (int)((width-1)*u+0.5);
        int vi = (int)((height-1)*v+0.5);
        if(ui<0) ui=0; else if(ui>=width) ui=width-1;
        if(vi<0) vi=0; else if(vi>=height) vi=height-1;
        return getColor(colorMap[ui][vi]);
    }
	
}
