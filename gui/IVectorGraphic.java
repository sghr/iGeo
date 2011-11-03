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

import igeo.geo.*;
import igeo.core.*;

/**
   Graphic subobject class to draw a vector with an arrowhead
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IVectorGraphic extends IGraphicObject{
    public static double defaultSize = 2; //1; //5; //1.0;
    public static float defaultWeight = 1f;

    public static IVec arrowHeadNormal = new IVec(0,0,1);
    public static IVec arrowHeadNormal2 = new IVec(0,1,0);
    
    public IVectorObject vec=null;
    
    public double size=defaultSize;
    public float weight=defaultWeight;
    
    public IVectorGraphic(IVectorObject v){
	super(v);
	vec = v;
    }
    
    public void size(double sz){ size=sz; }
    public double size(){ return size; }
    
    public void draw(IGraphics g){
	
	if(vec==null) return;
	
	if(g.view().mode().isGL()){
	    
	    GL gl = g.getGL();
	    
	    gl.glLineWidth(weight); //
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
	    
	    IVec rt = vec.root.get();
	    IVec v = vec.vec.get();
	    IVec t = null;
	    
	    if(v.angle(arrowHeadNormal)<IConfig.angleTolerance)
		t = v.cross(arrowHeadNormal2);
	    else t = v.cross(arrowHeadNormal);
	    t.len(size/2);
	    IVec v2 = vec.vec.get().dup().rev().len(size);
	    
	    if(g.view().mode().isFill()){
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(rt.x, rt.y, rt.z);
		gl.glVertex3d(v.x+v2.x+rt.x, v.y+v2.y+rt.y, v.z+v2.z+rt.z);
		gl.glEnd();
		if(g.view().mode().isTransparent()){
		    alpha = (float)transparentModeAlpha;
		    gl.glColor4f(red, green, blue, alpha);
		}
		// arrow head
		gl.glBegin(GL.GL_TRIANGLES);
		gl.glVertex3d(v.x+v2.x-t.x+rt.x, v.y+v2.y-t.y+rt.y, v.z+v2.z-t.z+rt.z);
		gl.glVertex3d(v.x+rt.x, v.y+rt.y, v.z+rt.z);
		gl.glVertex3d(v.x+v2.x+t.x+rt.x, v.y+v2.y+t.y+rt.y, v.z+v2.z+t.z+rt.z);
		gl.glEnd();
	    }
	    else{
		gl.glColor4f(red, green, blue, alpha);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(rt.x, rt.y, rt.z);
		gl.glVertex3d(v.x+rt.x, v.y+rt.y, v.z+rt.z);
		gl.glEnd();
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3d(v.x+v2.x-t.x+rt.x, v.y+v2.y-t.y+rt.y, v.z+v2.z-t.z+rt.z);
		gl.glVertex3d(v.x+rt.x, v.y+rt.y, v.z+rt.z);
		gl.glVertex3d(v.x+v2.x+t.x+rt.x, v.y+v2.y+t.y+rt.y, v.z+v2.z+t.z+rt.z);
		gl.glEnd();
	    }
	    
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
