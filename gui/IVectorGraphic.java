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
   Graphic subobject class to draw a vector with an arrowhead
   
   @author Satoru Sugihara
*/
public class IVectorGraphic extends IGraphicObject{
    //public static double defaultSize = 2; //1; //5; //1.0;
    //public static float defaultWeight = 1f;

    public static IVec arrowHeadNormal = new IVec(0,0,1);
    public static IVec arrowHeadNormal2 = new IVec(0,1,0);
    
    public IVectorObject vec=null;
    
    public float size= IConfig.arrowSize; //defaultSize;
    public float weight=IConfig.strokeWeight; //defaultWeight;
    
    public IVectorGraphic(IVectorObject v){
	super(v);
	vec = v;
    }

    /** set arrow head size */
    public void size(double sz){ size=(float)sz; }
    /** get arrow head size */
    public double size(){ return size; }

    /** set arrow line weight */
    public void setWeight(float w){ weight=w; }
    /** get arrow line weight */
    public float getWeight(){ return weight; }
    
    public void draw(IGraphics g){
	
	if(vec==null) return;
	
	//if(g.view().mode().isGL()){
	if(g.type() == IGraphicMode.GraphicType.GL ||
	   g.type() == IGraphicMode.GraphicType.P3D){
	    
	    IVec rt = vec.root.get();
	    IVec v = vec.vec.get();
	    IVec t = null;
	    
	    //if(v.angle(arrowHeadNormal)<IConfig.angleTolerance){ // this excludes 180 deg
	    if(v.isParallel(arrowHeadNormal)){ t = v.cross(arrowHeadNormal2); }
	    else t = v.cross(arrowHeadNormal);
	    t.len(size/2*IConfig.arrowWidthRatio);
	    IVec v2 = vec.vec.get().dup().rev().len(size);
	    
	    IVec[] arrowLine = new IVec[2];
	    IVec[] arrowHead = new IVec[3];
	    arrowLine[0] = rt;
	    arrowLine[1] = rt.dup().add(v);
	    arrowHead[0] = v.dup().add(v2).sub(t).add(rt);
	    arrowHead[1] = v.dup().add(rt);
	    arrowHead[2] = v.dup().add(v2).add(t).add(rt);
	    
	    IGraphics3D g3d = (IGraphics3D)g;
	    
	    g3d.weight(weight); // debug
	    //g3d.pointSize(size);
	    
	    //float red,green,blue,alpha;
	    float[] rgba=null;
	    if(color!=null){
		rgba = color.rgba();
		//red = color.getRed();
		//green = color.getGreen();
		//blue = color.getBlue();
		//alpha = color.getAlpha();
	    }
	    else{
		rgba = IConfig.objectColor.rgba();
		//red = IConfig.objectColor.getRed();
		//green = IConfig.objectColor.getGreen();
		//blue = IConfig.objectColor.getBlue();
		//alpha = IConfig.objectColor.getAlpha();
	    }
	    
	    // setting fill color
	    if(g3d.view().mode().isTransparent()&&g3d.view().mode().isTransparentWireframe()){
		rgba = new float[]{ rgba[0], rgba[1], rgba[2], IConfig.transparentModeAlpha/255f };
		//alpha = IConfig.transparentModeAlpha;
	    }
	    
            if(g3d.view().mode().isTransparent()){
		rgba = new float[]{ rgba[0], rgba[1], rgba[2], IConfig.transparentModeAlpha/255f };
		//alpha = IConfig.transparentModeAlpha;
	    }
	    
            if(g3d.view().mode().isLight()){
                g3d.ambient(rgba);
                g3d.diffuse(rgba);
                //g3d.specular(rgba);
                g3d.shininess(IConfig.shininess);
                g3d.clr(rgba[0]*255,rgba[1]*255,rgba[2]*255,0f); // ? without this, the color is tinted with the previous object's color
            }
            else{ g3d.clr(rgba); }
	    
	    // fill
	    if(g3d.view().mode().isFill()){
		arrowLine[1].add(v2);
		g3d.drawLines(arrowLine);
		if(g3d.view().mode().isTransparent()){
		    g3d.clr(rgba[0]*255, rgba[1]*255, rgba[2]*255, IConfig.transparentModeAlpha);
		}
		else{ g3d.clr(rgba); }
		g3d.drawTriangles(arrowHead);
	    }
	    
	    // setting wireframe color
            if(g3d.view().mode().isTransparent()&&g3d.view().mode().isTransparentWireframe()){
		rgba = new float[]{ rgba[0],rgba[1],rgba[2], IConfig.transparentModeAlpha/255f };
	    }
            else if(color!=null){ rgba[3] = color.a(); }
            else{ rgba = new float[]{ rgba[0],rgba[1],rgba[2], IConfig.objectColor.a() }; }
	    
            if(g3d.view().mode().isLight()&&g3d.view().mode().isLightWireframe()){
                g3d.ambient(rgba);
		g3d.diffuse(rgba);
                //g3d.specular(rgba);
		g3d.shininess(IConfig.shininess);
		g3d.stroke(rgba[0]*255,rgba[1]*255,rgba[2]*255,0f); // ? without this, the color is tinted with the previous object's color // necessary here?
            }
            else{ g3d.stroke(rgba); } // necessary here?
	    
	    if(g3d.view().mode().isLight()&&!g3d.view().mode().isLightWireframe())
		g3d.disableLight();
	    
	    g3d.stroke(rgba);
	    
	    // wireframe
	    if(g3d.view().mode().isWireframe()){
		g3d.stroke(rgba);
		g3d.drawLines(arrowLine);
		if(g3d.view().mode().isFill()){ g3d.drawLineLoop(arrowHead); } // close
		else{ g3d.drawLineStrip(arrowHead); }
	    }
	    
	    if(g3d.view().mode().isLight()&&!g3d.view().mode().isLightWireframe())
		g3d.enableLight();
	    
	    /*
	    GL gl = ((IGraphicsGL)g).getGL();
	    
	    gl.glLineWidth(weight); //
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
		alpha = (float)IConfig.transparentModeAlpha/255f;
	    
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
		    alpha = (float)IConfig.transparentModeAlpha/255f;
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
