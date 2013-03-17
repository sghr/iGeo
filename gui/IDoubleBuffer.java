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
import java.awt.event.*;

/**
   AWT component with double buffering
   
   @author Satoru Sugihara
*/
public class IDoubleBuffer{
    Component component;
    Image screen;
    Graphics graphics;
    
    IDoubleBuffer(Component c){ component = c; }
        
    public void initScreen(int w, int h){
	
	screen = component.createImage(w,h);
	graphics = screen.getGraphics();
	
	if(graphics instanceof Graphics2D){
	    Graphics2D g=(Graphics2D)graphics;
	    
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			       RenderingHints.VALUE_ANTIALIAS_ON);
            
            g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
			       RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            /*
	    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
	                       RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
	    */
	    
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			       RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
			       RenderingHints.VALUE_STROKE_NORMALIZE);
            /*
	    g.setRenderingHint(RenderingHints.KEY_DITHERING,
	                       RenderingHints.VALUE_DITHER_DISABLE);
            */
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
			       RenderingHints.VALUE_RENDER_QUALITY);
            
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			       RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	    /*
	    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
	                       RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            */
        }
    }
    
    public Graphics getGraphics(){
	if(screen==null){ initScreen(component.getWidth(),component.getHeight()); }
	return graphics;
    }
    
    public void setSize(int w, int h){ initScreen(w,h); }
    
    public void paint(Graphics g){ g.drawImage(screen,0,0,component); }
    
}
