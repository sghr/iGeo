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

import igeo.IG;
import igeo.IGraphicI;
import igeo.IColor;
import igeo.IConfig;

/**
   Class of Graphics like java.awt.Graphics to wrap all possible graphic mode
   (Currently Java AWT and OpenGL).
   
   @author Satoru Sugihara
*/
public interface IGraphics{
    
    //public IView view;
    //public Color color = IConfig.objectColor; // default
    //public double weight = IConfig.strokeWeight; // default
    
    //IGraphicMode mode=defaultMode;
    
    
    //public void setGL(GL gl){ this.gl=gl; }
    //public void setGraphics(Graphics2D g){ this.g=g; }
    
    //public void setView(IView v){ view = v; }
    
    //public boolean isTypeGL(){ return type==Type.GL_WIREFRAME || type==Type.GL_FILL; }
    //public boolean isTypeJava(){ return type==Type.JAVA_WIREFRAME || type==Type.JAVA_FILL; }
    //public void setType(Type t){ type=t; }
    //public Type getType(){ return type; }
    
    //public IGraphicMode mode(){ return mode; }
    
    //public abstract GL getGL(); // temporary
    //public abstract Graphics2D getGraphics(); // temporary
    
    //public IView view(){ return view; }
    
    public IView view();
    
    public void draw(ArrayList<IGraphicI> objects, IView view);

    //public void setSize(int w, int h);
    
    // should be here? or better to use IGraphicMode?
    public abstract IGraphicMode.GraphicType type();
    
    /** fill color */
    //public abstract void clr(Color c); //{ color = c; }
    public abstract void clr(IColor c); //{ color = c; }
    /** fill color (float 0-255)*/
    public abstract void clr(float r, float g, float b, float a);
    /** fill color (float 0-255)*/
    public abstract void clr(float r, float g, float b);
    
    /** fill color (array of 4 float in order of  r, g, b, a)*/
    public abstract void clr(float[] rgba);
    
    /** stroke (line) color */
    //public abstract void stroke(Color c);
    public abstract void stroke(IColor c);
    /** stroke color (float 0-255)*/
    public abstract void stroke(float r, float g, float b, float a);
    /** stroke color (float 0-255)*/
    public abstract void stroke(float r, float g, float b);
    /** stroke color (array of 4 float in order of  r, g, b, a)*/
    public abstract void stroke(float[] rgba);
    
    
    public abstract void weight(float w); //{ weight = w; }
    
    //public void diffuse(Color c)
    //public void ambient(Color c)
    //public void specular(Color c)
    //public void emmisive(Color c)
    //public void shininess(double s)
    
    //public void enableLight();
    //public void disableLight();
    
    //public void drawPoint(IVec p);
    //public void drawPoints(IVec[] p);
    //public void drawLines(IVec[] p);
    //public void drawLineStrip(IVec[] p);
    //public void drawLineLoop(IVec[] p);
    //public void drawPolygon(IVec[] p, IVec[] n);
    //public void drawQuads(IVec[] p, IVec[] n);
    //public void drawQuadStrip(IVec[] p, IVec[] n);
    //public void drawTriangles(IVec[] p, IVec[] n);
    //public void drawTriangleStrip(IVec[] p, IVec[] n);
    //public void drawTriangleFan(IVec[] p, IVec[] n);
    
    
    // J2D
    // point
    //public void drawPoint(IVec2 p);
    // line
    //public void drawLines(IVec2[] p);
    //public void drawLines(IVec2[] p);
    // bezier
    // polygon
    // surface?
    
    //public void stroke(double w)
    
    /** check if this is the first time frame to draw */
    public boolean firstDraw();
    /** set the first draw flag */
    public void firstDraw(boolean f);
    
}
