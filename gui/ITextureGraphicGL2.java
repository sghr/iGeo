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

//import javax.media.opengl.*; // Processing 1 & 2
import com.jogamp.opengl.*; // Processing 3

import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.util.texture.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;

import igeo.*;

/**
    Texture graphic class
 */
public class ITextureGraphicGL2 implements ITextureGraphicGL{
    public Texture texture;
    public int textureID;
    public GL gl;

    /** constructor with image file name */
    public ITextureGraphicGL2(String filename){
	init(filename,null);
    }

    /** constructor with image file name */
    public ITextureGraphicGL2(String filename, GL gl){
	init(filename,gl);
    }

    /** constructor with AWT buffered image */
    public ITextureGraphicGL2(BufferedImage image, GL gl){
	init(image, gl);
    }

    public void init(String filename, GL gl){
	if(texture!=null && gl!=null){ texture.destroy(gl); }
	texture = getTexture(filename);
	if(texture!=null){
	    textureID = texture.getTextureObject();
	}
	this.gl = gl;
    }

    public void init(BufferedImage image, GL gl){
	if(texture!=null){ texture.destroy(gl); }
	texture = getTexture(image, gl);
	if(texture!=null){
	    textureID = texture.getTextureObject();
	}
	this.gl = gl;
    }

    public int id(){
	return textureID;
    }

    public static Texture getTexture(BufferedImage image, GL gl){
	try{
	    return  AWTTextureIO.newTexture(gl.getGLProfile(), image, true);
	}catch(Exception e){
	    IG.err("error generating texture");
	    e.printStackTrace();
	}
	return null;
    }

    public static Texture getTexture(String imageFilename){
	IG ig = IG.cur();
	if(ig!=null){
	    String suffix = null;
	    int sidx = imageFilename.lastIndexOf(".");
	    if(sidx>=0){
		suffix = imageFilename.substring(sidx+1);
	    }
	    InputStream is=null;
	    try{
		if(ig.inputWrapper!=null){
		    is = ig.inputWrapper.getStream(imageFilename);
		}
		else{
		    is = new FileInputStream(imageFilename);
		}
		if(is!=null){
		    return TextureIO.newTexture(is, true, suffix);
		}
		else{
		    IOut.err("input stream could not be instantiated");
		}
	    }
	    catch(IOException e){ e.printStackTrace(); }
	}
	else{
	    IOut.err("no IG instance found");
	}
	return null;
    }

    public int width(){ return texture.getWidth(); }
    public int height(){ return texture.getHeight(); }

    public void destroy(GL gl){
	if(texture!=null) texture.destroy(gl);
	this.gl = gl;
    }

    public void destroy(){
	if(texture!=null && gl!=null) texture.destroy(gl);
    }

    /*
    public static Texture getTexture(Image img, GL gl){
	BufferedImage bimg=null;
	if(img instanceof BufferedImage){
	    bimg = (BufferedImage)img;
	}
	else{
	    bimg = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = bimg.createGraphics();
	    g.drawImage(img, 0, 0, null);
	    g.dispose();
	}
	try{
	    return AWTTextureIO.newTexture(gl.getGLProfile(), bimg, true);
	}catch(Exception e){}
	return null;
    }
    */

}
