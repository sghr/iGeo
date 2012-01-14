/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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

import java.util.ArrayList;
import java.awt.Color;

import igeo.*;

/**
   Graphic subobject class to draw a surface object by OpenGL.
   It contains ISurfaceGraphicFillGL and  ISurfaceGraphicWireframeGL inside.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IBrepGraphicGL extends IGraphicObject{
    
    public ISurfaceGraphicGL[] surfaceGraphics;
    
    public IBrepGraphicGL(IBrep brep){
	super(brep);
	surfaceGraphics = new ISurfaceGraphicGL[brep.surfaces.length];
	for(int i=0; i<brep.surfaces.length; i++){
	    surfaceGraphics[i] = new ISurfaceGraphicGL(brep, brep.surfaces[i]);
	}
    }
    
    public void setColor(Color c){
	super.setColor(c);
	for(ISurfaceGraphicGL g : surfaceGraphics) g.setColor(c);
    }
    
    public boolean isDrawable(IGraphicMode m){ return m.isGL(); }
    
    public void draw(IGraphics g){
	for(ISurfaceGraphicGL gr : surfaceGraphics) gr.draw(g);
    }
    
    public void hide(){
	super.hide();
	for(ISurfaceGraphicGL gr : surfaceGraphics) gr.hide();
	
    }
    public void show(){
	super.show();
	for(ISurfaceGraphicGL gr : surfaceGraphics) gr.show();
    }
    
}
