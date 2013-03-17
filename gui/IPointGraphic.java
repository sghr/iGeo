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

//import javax.media.opengl.*;

import igeo.*;

/**
   Graphic subobject class to draw point object
   
   @author Satoru Sugihara
*/
public class IPointGraphic extends IGraphicObject{
    //public static double defaultSize = 5; //1.0;
    //IPoint pt;
    public IVecI pt=null;
    
    public float weight=IConfig.pointSize; //defaultSize;
    
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
    
    synchronized public void initPoint(){
	synchronized(parent){
	    if(parent instanceof IPoint) pt = ((IPoint)parent).pos;
	    else if(parent instanceof IPointR) pt = ((IPointR)parent).pos;
	}
    }
    
    public void size(float sz){ weight(sz); }
    public double size(){ return weight(); }
    
    public void weight(float w){ weight=w; }
    public float weight(){ return weight; }
    public void setWeight(float w){ weight(w); }
    public float getWeight(){ return weight(); }
    
    
    synchronized public void draw(IGraphics g){
	
	if(pt==null) initPoint(); // if drawn before finishing constructor
	
	//if(g.view().mode().isGL()){
	if(g.type() == IGraphicMode.GraphicType.GL ||
	   g.type() == IGraphicMode.GraphicType.P3D){
	    
	    IGraphics3D g3d = (IGraphics3D)g;
	    
	    //g3d.pointSize(size);
	    g3d.pointSize(weight);
	    
	    //float red,green,blue,alpha;
	    float[] rgba = null;
	    if(color!=null){
		rgba = color.rgba();
		//red = (float)color.getRed();
		//green = (float)color.getGreen();
		//blue = (float)color.getBlue();
		//alpha = (float)color.getAlpha();
	    }
	    else{
		rgba = IConfig.objectColor.rgba();
		//red = (float)IConfig.objectColor.getRed();
		//green = (float)IConfig.objectColor.getGreen();
		//blue = (float)IConfig.objectColor.getBlue();
		//alpha = (float)IConfig.objectColor.getAlpha();
	    }
	    
	    if(g3d.view().mode().isTransparent()&&g3d.view().mode().isTransparentWireframe())
		rgba = new float[]{ rgba[0], rgba[1], rgba[2], IConfig.transparentModeAlpha/255f };
		//alpha = IConfig.transparentModeAlpha;
	    
            if(g3d.view().mode().isLight()&&g3d.view().mode().isLightWireframe()){
		g3d.ambient(rgba);
		g3d.diffuse(rgba);
		//g3d.specular(red,green,blue,alpha);
		g3d.shininess(IConfig.shininess);
		g3d.clr(rgba[0]*255,rgba[1]*255,rgba[2]*255,0f); // ? without this, the color is tinted with the previous object's color
            }
	    
	    if(g3d.view().mode().isLight()&&!g3d.view().mode().isLightWireframe())
		g3d.disableLight();
	    
	    //g3d.clr(red,green,blue,alpha);
	    g3d.stroke(rgba); // in PIGraphicsP3D, it should be stroke color; in IGraphicsGL, clr and stroke is same.
	    
	    g3d.drawPoint(pt.get());
	    
	    if(g3d.view().mode().isLight()&&!g.view().mode().isLightWireframe())
		g3d.enableLight();
	    
	    
	    /*
	    GL gl = ((IGraphicsGL)g).getGL();
	    
	    //gl.glLineWidth(0.01f); //
	    //gl.glPointSize(0.1f); //
	    gl.glPointSize((float)size); //
	    
	    float red,green,blue,alpha;
	    //float red = defaultRed;
	    //float green = defaultGreen;
	    //float blue = defaultBlue;
	    //float alpha = defaultAlpha;
	    if(color!=null){
		red = (float)color.getRed()/255;
		green = (float)color.getGreen()/255;
		blue = (float)color.getBlue()/255;
		alpha = (float)color.getAlpha()/255;
	    }
	    else{
		red = (float)IConfig.objectColor.getRed()/255;
		green = (float)IConfig.objectColor.getGreen()/255;
		blue = (float)IConfig.objectColor.getBlue()/255;
		alpha = (float)IConfig.objectColor.getAlpha()/255;
	    }
	    
	    if(g.view().mode().isTransparent()&&g.view().mode().isTransparentWireframe())
		alpha = (float)IConfig.transparentModeAlpha/255;
	    
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

	    */
	}
	else if(g.view().mode().isJ2D()){
	    
	    // ...to be implemented
	    
	}
	
    }
    
    public boolean isDrawable(IGraphicMode m){
	//return m.isGL(); // currently GL only
	return m.isGraphic3D(); 
    }
    
}
