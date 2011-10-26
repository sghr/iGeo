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
//import igeo.gl.*;

/**
   Graphic subobject class to draw a curve object by OpenGL
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ICurveGraphicGL extends IGraphicObject{
    public /*static*/ float weight = 1f;
    
    public ICurveI curve; // parent
    
    public IGLLineStrip polyline;
    
    public ICurveGraphicGL(ICurve crv){
	super(crv);
	//curve = crv.curve;
	//init();
    }
    
    public ICurveGraphicGL(ICurveR crv){
	super(crv);
	//curve = crv.curve;
	//init();
    }
    
    public void initCurve(){
	if(curve==null){ // added in 2011/10/18
	    if(parent instanceof ICurve){ curve = ((ICurve)parent).curve; }
	    else if(parent instanceof ICurveR){ curve = ((ICurveR)parent).curve; }
	}
	
	IVec[] pts=null;
	
	if(curve.deg()==1){
	    int num = curve.num();
	    if(polyline!=null && polyline.pts!=null && polyline.pts.length==num){
		pts = polyline.pts;
	    }
	    else{ pts = new IVec[num]; }
	    for(int i=0; i<num; i++) pts[i] = curve.cp(i).get();
	}
	else{
	    int reso = IConfig.curveGraphicResolution;
	    int epnum = curve.epNum() ;
	    int num = (epnum-1)*reso+1;
	    if(polyline!=null && polyline.pts!=null && polyline.pts.length==num){
		pts = polyline.pts;
	    }
	    else{ pts = new IVec[num]; }
	    for(int i=0; i<epnum; i++){
		for(int j=0; j<reso; j++){
		    if(i<epnum-1 || j==0){
			pts[i*reso + j] = curve.pt(curve.u(i,(double)j/reso)).get();
		    }
		}
	    }
	}
	
	if(polyline==null || polyline.pts != pts){ polyline = new IGLLineStrip(pts); }
	
	if(update) update=false;
    }
    
    public boolean isDrawable(IGraphicMode m){ return m.isGL(); }
    
    public void draw(IGraphics g){
	
	if(curve==null || update && curve.deg()>1 )
	    initCurve(); // not initizlized at the constructor // shouldn't it?
	
	GL gl = g.getGL();
	if(gl!=null){
	    gl.glLineWidth(weight);
	    //gl.glLineStipple(0,(short)0xFFFF);
	    
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
	    
	    gl.glColor4f(red, green, blue, alpha);
	    polyline.draw(gl);
	    //gl.glBegin(GL.GL_LINE_STRIP);
	    //for(int i=0; i<pts.length; i++){
		//gl.glVertex3d(pts[i].x, pts[i].y, pts[i].z);
		//gl.glVertex3f((float)pts[i].x, (float)pts[i].y, (float)pts[i].z);
	    //}
	    //gl.glEnd();
	    
	    if(g.view().mode().isLight()&&!g.view().mode().isLightWireframe())
		gl.glEnable(GL.GL_LIGHTING);
	}
	
	
    }
    
}
