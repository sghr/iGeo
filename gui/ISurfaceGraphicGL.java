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

import java.util.ArrayList;
//import java.awt.Color;

import igeo.*;

/**
   Graphic subobject class to draw a surface object by OpenGL.
   It contains ISurfaceGraphicFillGL and  ISurfaceGraphicWireframeGL inside.
   
   @author Satoru Sugihara
*/
public class ISurfaceGraphicGL extends IGraphicObject{
    //public static float defaultColorRed = .5f; //1f;
    //public static float defaultColorGreen = .5f; //1f;
    //public static float defaultColorBlue = .5f; //1f;
    //public static float defaultColorAlpha = 1f;
    
    //public static float defaultShininess=0.3f; //0.5f; //1f; //5f; //1f; //0.1f; //0.5f; //1f;
    
    public ISurfaceGraphicFillGL fill;
    public ISurfaceGraphicWireframeGL wireframe;
    
    public ISurfaceGraphicGL(ISurface srf){
	super(srf);
	
	// fill and wireframe are instantiated here but actual geometry initialization is done at the first draw by each object
	wireframe = new ISurfaceGraphicWireframeGL(srf);
	fill = new ISurfaceGraphicFillGL(srf);
    }
    public ISurfaceGraphicGL(ISurfaceR srf){
	super(srf);
	
	// fill and wireframe are instantiated here but actual geometry initialization is done at the first draw by each object
	wireframe = new ISurfaceGraphicWireframeGL(srf);
	fill = new ISurfaceGraphicFillGL(srf);	
    }
    
    public ISurfaceGraphicGL(IObject obj, ISurfaceGeo srf){
	super(obj);
	// fill and wireframe are instantiated here but actual geometry initialization is done at the first draw by each object
	wireframe = new ISurfaceGraphicWireframeGL(obj,srf);
	fill = new ISurfaceGraphicFillGL(obj,srf);
    }
    
    public void setAttribute(IAttribute attr){
	super.setAttribute(attr);
	if(fill!=null) fill.setAttribute(attr);
	if(wireframe!=null) wireframe.setAttribute(attr);
    }
    
    public void setColor(IColor c){
	super.setColor(c);
	if(fill!=null) fill.setColor(c);
	if(wireframe!=null) wireframe.setColor(c);
    }
    
    
    public void setWeight(float w){
	if(wireframe!=null) wireframe.setWeight(w);
    }
    public float getWeight(){
	if(wireframe!=null) return wireframe.getWeight();
	return -1f;
    }
    
    @Override public void update(){
	if(fill!=null) fill.update();
	if(wireframe!=null) wireframe.update();
    }
    
    public boolean isDrawable(IGraphicMode m){ return m.isGL(); }
    
    public void draw(IGraphics g){
	
	// fill first or wireframe first?
	
	if(g.view().mode().isFill()){
	    /*
	    if(fill==null){
		if(parent instanceof ISurface)
		    fill = new ISurfaceGraphicFillGL((ISurface)parent);
		else if(parent instanceof ISurfaceR)
		    fill = new ISurfaceGraphicFillGL((ISurfaceR)parent);
		fill.setColor(color);
	    }
	    */
	    fill.draw(g);
	}

	if(g.view().mode().isWireframe()){
	    /*
	    if(wireframe==null){
		if(parent instanceof ISurface)
		    wireframe = new ISurfaceGraphicWireframeGL((ISurface)parent);
		else if(parent instanceof ISurfaceR)
		    wireframe = new ISurfaceGraphicWireframeGL((ISurfaceR)parent);
		wireframe.setColor(color);
	    }
	    */
	    wireframe.draw(g);
	}
    }
    
    
    
}
