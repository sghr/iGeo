/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

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

import javax.media.opengl.*;

import igeo.*;

/**
   Graphic subobject class to draw point object
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IPointGraphic extends IGraphicObject{
    public static double defaultSize = 5; //1.0;
    //IPoint pt;
    public IVecI pt=null;
    
    public double size=defaultSize;
    
    //public IPointGraphic(IPoint p){ super(p); parent = p; pt = p; }
    public IPointGraphic(IPoint p){
	super(p);
	initPoint(p.pos);
    }
    
    public IPointGraphic(IPointR p){
	super(p);
	initPoint(p.pos);
    }
    
    public void initPoint(IVecI p){ pt = p; }
    
    public void initPoint(){
	if(parent instanceof IPoint) pt = ((IPoint)parent).pos;
	else if(parent instanceof IPointR) pt = ((IPointR)parent).pos;
    }
    
    public void size(double sz){ size=sz; }
    public double size(){ return size; }
    
    public void draw(IGraphics g){
	
	if(pt==null) initPoint(); // if drawn before finishing constructor
	
	if(g.view().mode().isGL()){
	    
	    GL gl = g.getGL();
	    
	    //gl.glLineWidth(0.01f); //
	    //gl.glPointSize(0.1f); //
	    gl.glPointSize((float)size); //
	    
	    float red = ISurfaceGraphicGL.defaultColorRed;
            float green = ISurfaceGraphicGL.defaultColorGreen;
            float blue = ISurfaceGraphicGL.defaultColorBlue;
            float alpha = ISurfaceGraphicGL.defaultColorAlpha;
            if(color!=null){
                red = (float)color.getRed()/255;
                green = (float)color.getGreen()/255;
                blue = (float)color.getBlue()/255;
                alpha = (float)color.getAlpha()/255;
            }
	    
	    if(g.view().mode().isTransparent()&&g.view().mode().isTransparentWireframe())
		 alpha = (float)transparentModeAlpha;
	    
            if(g.view().mode().isLight()&&g.view().mode().isLightWireframe()){
                float[] colorf = new float[]{ red, green, blue, alpha };
                gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, colorf, 0);
                gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, colorf, 0);
                //gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, colorf, 0);
                gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS,
                               ISurfaceGraphicGL.defaultShininess);
		gl.glColor4f(red, green, blue, 0f); // ? without this, the color is tinted with the previous object's color
            }
            else{ gl.glColor4f(red, green, blue, alpha); }
	    
	    if(g.view().mode().isLight()&&!g.view().mode().isLightWireframe())
		gl.glDisable(GL.GL_LIGHTING);
	    
	    gl.glBegin(GL.GL_POINTS);
	    gl.glColor4f(red, green, blue, alpha);
	    
	    
	    gl.glVertex3d(pt.x(), pt.y(), pt.z());
	    //gl.glVertex3f((float)pt.pos.x(), (float)pt.pos.y(), (float)pt.pos.z()+10f);
	    gl.glEnd();
	    
	    if(g.view().mode().isLight()&&!g.view().mode().isLightWireframe())
		gl.glEnable(GL.GL_LIGHTING);
	}
	else if(g.view().mode().isJava()){
	    
	    // ...to be implemented
	    
	}
	
    }
    
    public boolean isDrawable(IGraphicMode m){
	return m.isGL(); // currently GL only
    }
    
}
