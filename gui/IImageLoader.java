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

package igeo.gui;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

import igeo.*;

/**
   Class to provide function to load image from external file into Java AWT Image object.
   
   @author Satoru Sugihara
*/
public class IImageLoader implements ImageObserver{
    public static final IImageLoader observer=new IImageLoader();

    public static boolean convertFilePath=true;
    
    public static Image getImage(String filename){
	return getImage(filename, new Container());
	//return getImage(filename, null);
    }
    
    public static Image getImage(String filename, Component component){
	
	IOut.debug(10,"opening image of "+filename); //
	
	InputStream is = null;
	if(convertFilePath){
	    IG ig = IG.current();
	    if(ig!=null && ig.inputWrapper!=null){
		is = ig.inputWrapper.getStream(filename);
	    }
	    else{ // should be removed
		File f = new File(filename);
		if(!f.isAbsolute()){
		    ig = IG.current();
		    if(ig!=null && ig.getBasePath()!=null){
			filename = ig.getBasePath() + File.separator + filename;
		    }
		}
		
		File file = new File(filename);
		if(!file.exists()){
		    IOut.err("file does not exist: "+filename); //
		    return null;
		}
		try{
		    is = new FileInputStream(filename);
		}
		catch(IOException e){
		    IOut.err("IOException"); //
		    e.printStackTrace();
		    return null;
		}
	    }
	}
	
	if(is==null){
	    IOut.err("InputStream is null"); //
	    return null;
	}

	BufferedImage img=null;
	try{
	    img = ImageIO.read(is);
	}
	catch(Exception e){
	    IOut.err("ImageIO exception"); //
	    e.printStackTrace();
	    return null;
	}
	
	return img;
	
	/*
	MediaTracker mt = new MediaTracker(component);
	Image image=null;
	File file = new File(filename);
	if(!file.exists()){
	    IOut.err("file does not exist: "+filename); //
	    return null;
	}
	image = Toolkit.getDefaultToolkit().getImage(filename);
	mt.addImage(image, 0);
	try{ mt.waitForID(0); }
	catch(Exception e){ e.printStackTrace(); }
	if(image==null){
	    IOut.err("failed to open "+filename); //
	}
	return(image);
	*/
    }
    
    /* to be implemented
    public static Image getImage(URL url, Component component){
    }
    */
    
    public static int getWidth(Image image){
	return image.getWidth(observer);
    }
    
    public static int getHeight(Image image){
	return image.getHeight(observer);
    }
    
    public static int[] getPixelBuffer(Image image){
	int w = image.getWidth(observer);
	int h = image.getHeight(observer);
	int[] pix = new int[w*h];
	PixelGrabber pg = new PixelGrabber(image, 0, 0, -1, -1, pix, 0, w);
	if(pg==null) return null;
	try{ pg.grabPixels(); } catch(Exception e){ e.printStackTrace(); }
	return pix;
    }
    
    public static int[][] getPixelMatrix(Image image){
	int w = image.getWidth(observer);
	int h = image.getHeight(observer);
	int[] pix = new int[w*h];
	PixelGrabber pg = new PixelGrabber(image, 0, 0, -1, -1, pix, 0, w);
	if(pg==null) return null;
	try{ pg.grabPixels(); } catch(Exception e){ e.printStackTrace(); }
	int[][] mat = new int[w][h];
	
	for(int y=0; y<h; y++)
	    for(int x=0; x<w; x++)
		mat[x][y] = pix[w*y+x];
	return mat;
    }
    
    public static byte[] getRGBBytes(Image image){
	int[] pix = getPixelBuffer(image);
	// pix is ARGB format
	byte[] buf = new byte[pix.length*3];
	for(int i=0; i<pix.length; i++){
	    buf[i*3] = (byte)((pix[i]>>16)&0xff);
	    buf[i*3+1] = (byte)((pix[i]>>8)&0xff);
	    buf[i*3+2] = (byte)(pix[i]&0xff);
	}
	return buf;
    }
    
    public static byte[] getARGBBytes(Image image){
	int[] pix = getPixelBuffer(image);
	byte[] buf = new byte[pix.length*4];
	for(int i=0; i<pix.length; i++){
	    buf[i*4] = (byte)((pix[i]>>24)&0xff);
	    buf[i*4+1] = (byte)((pix[i]>>16)&0xff);
	    buf[i*4+2] = (byte)((pix[i]>>8)&0xff);
	    buf[i*4+3] = (byte)(pix[i]&0xff);
	}
	return buf;
    }
    
    public static Color[][] getPixels(Image image){
	int w = image.getWidth(observer);
	int h = image.getHeight(observer);
	int[] pix = new int[w*h];
	PixelGrabber pg = new PixelGrabber(image, 0, 0, -1, -1, pix, 0, w);
	if(pg==null) return null;
	try{ pg.grabPixels(); } catch(Exception e){ e.printStackTrace(); }
	return getPixels(pix,w,h);
    }
    
    
    public static Color[][] getPixels(int[] pixelBuf, int width, int height){
	Color[][] pixel = new Color[width][height];
	for(int i=0; i<height; i++)
	    for(int j=0; j<width; j++) pixel[j][i] = getColor(pixelBuf,j,i,width);
	return pixel;
    }
    
    public static Color getColor(int[] pixel, int x, int y, int w){
	int aRGB = pixel[(w*y) + x];
	int a = (aRGB>>24)&0xff;
	int r = (aRGB>>16)&0xff;
	int g = (aRGB>>8)&0xff;
	int b = aRGB&0xff;
	return new Color(r,g,b,a);
    }
    
    public static BufferedImage createImage(int[][] pixel){
	int w = pixel.length;
	int h = pixel[0].length;
	BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
	for(int y=0; y<h; y++)
	    for(int x=0; x<w; x++)
		image.setRGB(x,y,pixel[x][y]);
	return image;
    }
    
    // interface ImageObserver implemetation
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height){
	return false; // should this be true or false?
    }
    
    /*
    public static void main(String[] argv){
	// test
	if(argv.length>0){
	    Image img =  getImage(argv[0]);
	    IOut.p("img = "+img);
	    Color[][] pix = getPixels(img);
	    for(int i=0; i<img.getWidth(observer); i++){
		IOut.p(i+": "+ pix[i][0]);
	    }
	    IOut.p("width = "+img.getWidth(observer));
	    IOut.p("height = "+img.getHeight(observer));
	}
    }
    */
    
}
