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

import java.awt.image.*;
import java.awt.*;

import igeo.*;
import igeo.gui.*;

/** 
    Abstract texture class (wrapping ITextureGL2)
 */
public class ITexture{
    
    public ITextureGraphicGL glTexture;
    
    public String filename;
    public BufferedImage image;
    
    public boolean needUpdate = false;
    
    /** constructor with image file name */
    public ITexture(String filename){
	this.filename = filename;
	//image = getBufferedImage(IImageLoader.getImage(filename));
    }
    
    public ITexture(String filename, int width, int height){
	//this.filename = filename;
	image = getBufferedImage((BufferedImage)IImageLoader.getImage(filename),width,height);
    }
    
    /** constructor with AWT image */
    public ITexture(Image img){ image = getBufferedImage(img); }
    
    /** constructor with AWT buffered image */
    public ITexture(BufferedImage img){ image = img; }
    
    /** constructor with pixel size */
    public ITexture(int width, int height){ image =getBufferedImage(width,height); }
    
    public int width(){
	if(glTexture==null) return 0;
	if(image!=null) return image.getWidth();
	return glTexture.width();
    }
    public int height(){
	if(glTexture==null) return 0;
	if(image!=null) return image.getHeight();
	return glTexture.height();
    }
    
    public BufferedImage getBufferedImage(Image image){
	BufferedImage bimg = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = bimg.createGraphics();
	g.drawImage(image, 0, 0, null);
	g.dispose();
	return bimg;
    }
    
    public BufferedImage getBufferedImage(Image image, int width, int height){
	BufferedImage bimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = bimg.createGraphics();
	g.drawImage(image, 0, 0, width, height, null);
	g.dispose();
	return bimg;
    }
    
    public BufferedImage getBufferedImage(String imageFile){
	BufferedImage bimg = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = bimg.createGraphics();
	g.drawImage(image, 0, 0, null);
	g.dispose();
	return bimg;
    }
    
    public BufferedImage getBufferedImage(int width, int height){
	return new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
    }
    
    public int id(IGraphicsGL g){
	if(glTexture!=null){
	    if(needUpdate){
		if(image!=null){
		    glTexture.init(image,g.getGL());
		}
		else if(filename!=null){
		    glTexture.init(filename,g.getGL());
		}
		needUpdate=false;
	    }
	    return glTexture.id();
	}
	
	if(image!=null){
	    //glTexture = new ITextureGL2(image, g.getGL());
	    glTexture = g.getTextureGraphic(image);
	    return glTexture.id();
	}
	if(filename!=null){
	    //glTexture = new ITextureGL2(filename);
	    glTexture = g.getTextureGraphic(filename);
	    return glTexture.id();
	}
	return 0;
    }
    
    public void update(){
	needUpdate = true;
    }
    
    public void destroy(){
	if(glTexture!=null) glTexture.destroy();
    }
    /*
    public Texture getTexture(IGraphicsGL gr){
	GL gl = gr.getGL();
	
	BufferedImage bimg = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = bimg.createGraphics();
	g.setBackground(Color.cyan);
	g.clearRect(0,0,100,100);
	g.setColor(Color.blue);
	g.fill(new Rectangle(10,10,80,80));
	g.setColor(Color.yellow);
	g.fill(new Rectangle(20,20,10,10));
	g.dispose();
	try{
	    return AWTTextureIO.newTexture(gl.getGLProfile(), bimg, true);
	}catch(Exception e){
	    IG.err("error generating texture");
	    e.printStackTrace();
	}
	return null;
    }
    */
    
}