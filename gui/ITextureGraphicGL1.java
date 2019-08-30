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

import javax.media.opengl.*; // Processing 1 & 2

import java.awt.geom.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;

import igeo.*;

/** 
    Texture graphic class for processing 1 (not implemented yet)
 */
public class ITextureGraphicGL1 implements ITextureGraphicGL{
    /** constructor with image file name */
    public ITextureGraphicGL1(String filename){
	init(filename,null);
    }

    /** constructor with image file name */
    public ITextureGraphicGL1(String filename, GL gl){
	init(filename,gl);
    }
    
    /** constructor with AWT buffered image */
    public ITextureGraphicGL1(BufferedImage image, GL gl){
	init(image, gl);
    }
    public void init(String filename, GL gl){}
    public void init(BufferedImage image, GL gl){}
    public int id(){ return 0; }
    public int width(){ return 0; }
    public int height(){ return 0; }
    public void destroy(GL gl){}
    public void destroy(){}
}