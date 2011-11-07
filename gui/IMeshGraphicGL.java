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
   Graphic subobject class to draw polygon mesh object by OpenGL.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IMeshGraphicGL extends IGraphicObject{
    
    public static float weight = 1f;
    
    public IMeshI mesh;
    public IVec[][] facePts, faceNormal, edgePts;
    
    public IMeshGraphicGL(IMesh m){ super(m); }
    public IMeshGraphicGL(IMeshR m){ super(m); }
    
    public void initMesh(){
	if(parent instanceof IMesh){ mesh = ((IMesh)parent).mesh; }
	else if(parent instanceof IMeshR){ mesh = ((IMeshR)parent).mesh; }
	
	/*
	boolean allTriangles=true, allQuads=true;
	for(int i=0; i<mesh.faceNum()&&(allTriangles||allQuads); i++){
	    int num = mesh.face(i).vertexNum();
	    if(num==3) allQuads==false;
	    else if(num==4) allTriangles=false;
	    else{ allTriangles=false; allQuads==false; }
	}
	*/
	
	facePts = new IVec[mesh.faceNum()][];
	faceNormal = new IVec[mesh.faceNum()][];
	for(int i=0; i<mesh.faceNum(); i++){
	    IFace face = mesh.face(i);
	    facePts[i] = new IVec[face.vertexNum()];
	    faceNormal[i] = new IVec[face.vertexNum()];
	    for(int j=0; j<face.vertexNum(); j++){
		facePts[i][j] = face.getVertex(j).get();
		faceNormal[i][j] = face.getVertex(j).normal().get();
	    }
	}
	
	edgePts = new IVec[mesh.edgeNum()][];
	for(int i=0; i<mesh.edgeNum(); i++){
	    IEdge edge = mesh.edge(i);
	    edgePts[i] = new IVec[2];
	    edgePts[i][0] = edge.getVertex(0).get();
	    edgePts[i][1] = edge.getVertex(1).get();
	}
	
    }
    
    public boolean isDrawable(IGraphicMode m){ return m.isGL(); }
    
    public void draw(IGraphics g){
	
	if(mesh==null) initMesh(); // not initizlized at the constructor // shouldn't it?
	
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
	    
	    if(g.view().mode().isTransparent())
		alpha = (float)transparentModeAlpha;
	    
            if(g.view().mode().isLight()){
                float[] colorf = new float[]{ red, green, blue, alpha };
                gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, colorf, 0);
                gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, colorf, 0);
                //gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, colorf, 0);
                gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS,
                               ISurfaceGraphicGL.defaultShininess);
		gl.glColor4f(red, green, blue, 0f); // ? without this, the color is tinted with the previous object's color
            }
            else{ gl.glColor4f(red, green, blue, alpha); }
	    
	    if(g.view().mode().isFill()){
		int prevNum=0;
		for(int i=0; i<facePts.length; i++){
		    if(facePts[i].length!=prevNum){
			if(i>0) gl.glEnd();
			if(facePts[i].length==3) gl.glBegin(GL.GL_TRIANGLES);
			else if(facePts[i].length==4) gl.glBegin(GL.GL_QUADS);
			else gl.glBegin(GL.GL_POLYGON);
			prevNum = facePts[i].length;
		    }
		    for(int j=0; j<facePts[i].length; j++){
			gl.glNormal3d(faceNormal[i][j].x,faceNormal[i][j].y,faceNormal[i][j].z);
			gl.glVertex3d(facePts[i][j].x,facePts[i][j].y,facePts[i][j].z);
			//gl.glVertex3f((float)facePts[i][j].x,(float)facePts[i][j].y,(float)facePts[i][j].z);
		    }
		}
		if(facePts.length>0) gl.glEnd();
	    }
	    
	    
	    if(g.view().mode().isTransparent()&&g.view().mode().isTransparentWireframe())
		alpha = (float)transparentModeAlpha;
	    else if(color!=null) alpha = (float)color.getAlpha()/255;
	    else alpha = ISurfaceGraphicGL.defaultColorAlpha;
	    
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
	    
	    if(g.view().mode().isWireframe()){
		gl.glBegin(GL.GL_LINES);
		for(int i=0; i<edgePts.length; i++){
		    gl.glVertex3f((float)edgePts[i][0].x,(float)edgePts[i][0].y,(float)edgePts[i][0].z);
		    gl.glVertex3f((float)edgePts[i][1].x,(float)edgePts[i][1].y,(float)edgePts[i][1].z);
		}
		gl.glEnd();
	    }
	    
	    if(g.view().mode().isLight()&&!g.view().mode().isLightWireframe())
		gl.glEnable(GL.GL_LIGHTING);
	}
	
    }
    
}
