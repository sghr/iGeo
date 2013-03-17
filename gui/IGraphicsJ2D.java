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
import java.util.ArrayList;

import igeo.IG;
import igeo.IGraphicI;
import igeo.IColor;
import igeo.gui.IGraphicMode;

/**
   Class of Graphics to draw 3D geometry (OpenGL or P3D)
   @author Satoru Sugihara
*/
public class IGraphicsJ2D implements IGraphics2D{
    
    // J2D
    // point
    //public void drawPoint(IVec2 p);
    // line
    //public void drawLines(IVec2[] p);
    //public void drawLines(IVec2[] p);
    // bezier
    // polygon
    // surface?
    
    // stroke colorp

    public IView view;
    public boolean firstDraw=true;
    
    public IGraphicMode.GraphicType type(){ return IGraphicMode.GraphicType.J2D; }

    public IView view(){ return view; }
    
    public void draw(ArrayList<IGraphicI> objects, IView v){
	view = v; // current view
    }

    public void clr(IColor c){}
    public void clr(float[] rgba){}
    public void clr(float r, float g, float b, float a){}
    public void clr(float r, float g, float b){}
    public void stroke(IColor c){}
    public void stroke(float[] rgba){}
    public void stroke(float r, float g, float b, float a){}
    public void stroke(float r, float g, float b){}
    public void weight(float w){}
    
    public boolean firstDraw(){ return firstDraw; }
    public void firstDraw(boolean f){ firstDraw=f; }
}
