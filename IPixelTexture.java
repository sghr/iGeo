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
    Texture class out of matrix of pixels
 */
public class IPixelTexture extends ITexture{
    public int[] pixels;
    
    /** constructor with pixel size */
    public IPixelTexture(int width, int height){
	super(width,height);
	pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    }
    
    public IPixelTexture(String file, int width, int height){
	super(file, width, height);
	pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    }
    
    
}