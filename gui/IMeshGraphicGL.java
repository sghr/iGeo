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
   Graphic subobject class to draw polygon mesh object by OpenGL.
   
   @author Satoru Sugihara
*/
public class IMeshGraphicGL extends IGraphicObject{
    
    public static float weight = IConfig.strokeWeight; //1f;
    
    public IMeshI mesh;
    public IVec[][] facePts, faceNormal, edgePts;
    
    public IMeshGraphicGL(IMesh m){ super(m); }
    public IMeshGraphicGL(IMeshR m){ super(m); }
    
    public void initMesh(){
	synchronized(parent){
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
    }
    
    public void setWeight(float w){ weight=w; }
    public float getWeight(){ return weight; }
    
    public boolean isDrawable(IGraphicMode m){
	//return m.isGL();
	return m.isGraphic3D();
    }
    
    public void draw(IGraphics g){
	// how about update?
	if(mesh==null) initMesh(); // not initizlized at the constructor // shouldn't it?
	

	if(g.type() == IGraphicMode.GraphicType.GL||
	   g.type() == IGraphicMode.GraphicType.P3D){
	    
	    IGraphics3D g3d = (IGraphics3D)g;
	    
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
	    
	    if(g3d.view().mode().isTransparent()){
		rgba = new float[]{ rgba[0], rgba[1], rgba[2], IConfig.transparentModeAlpha/255f };
	    }
	    
	    if(g3d.view().mode().isLight()){
		g3d.ambient(rgba);
		g3d.diffuse(rgba);
		//g3d.specular(rgba);
		g3d.shininess(IConfig.shininess);
		g3d.clr(rgba[0]*255,rgba[1]*255,rgba[2]*255,0f); // ? without this, the color is tinted with the previous object's color
	    }
	    //else{ g3d.clr(rgba); }
	    g3d.clr(rgba);
	    
	    if(g3d.view().mode().isFill()){
		int prevNum=0;
		for(int i=0; i<facePts.length; i++){
		    if(facePts[i].length==3){ g3d.drawTriangles(facePts[i],faceNormal[i]); }
		    else if(facePts[i].length==4){ g3d.drawQuads(facePts[i],faceNormal[i]); }
		    else{ g3d.drawPolygon(facePts[i],faceNormal[i]); }
		}
	    }
	    
	    
	    if(g3d.view().mode().isTransparent()&&g3d.view().mode().isTransparentWireframe()){
		rgba = new float[]{ rgba[0], rgba[1], rgba[2], IConfig.transparentModeAlpha/255f };
		//alpha = IConfig.transparentModeAlpha;
	    }
	    else if(color!=null){
		rgba[3] = color.a();
		//alpha = (float)color.getAlpha();
	    }
	    else{
		rgba = new float[]{ rgba[0], rgba[1], rgba[2], IConfig.objectColor.a() };
		//alpha = (float)IConfig.objectColor.getAlpha();
	    }
	    
	    if(g.view().mode().isLight()&&g.view().mode().isLightWireframe()){
		g3d.ambient(rgba);
		g3d.diffuse(rgba);
		//g3d.specular(rgba);
		g3d.shininess(IConfig.shininess);		
		g3d.stroke(rgba[0]*255,rgba[1]*255,rgba[2]*255,0f); // ? without this, the color is tinted with the previous object's color
	    }
	    //else{ g3d.stroke(rgba); }
	    
	    if(g3d.view().mode().isLight()&&!g3d.view().mode().isLightWireframe())
		g3d.disableLight();
	    
	    if(g3d.view().mode().isWireframe()){
		g3d.weight(weight);
		g3d.stroke(rgba);
		for(int i=0; i<edgePts.length; i++){ g3d.drawLines(edgePts[i]); }
	    }
	    
	    if(g3d.view().mode().isLight()&&!g3d.view().mode().isLightWireframe())
		g3d.enableLight();
	    
	    ///////////
	    
	    /*
	    GL gl = ((IGraphicsGL)g).getGL();
	    //GL gl = g.getGL();
	    
	    if(gl!=null){
		
		gl.glLineWidth(weight);
		//gl.glLineStipple(0,(short)0xFFFF);
		
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
		
		if(g.view().mode().isTransparent())
		    alpha = (float)IConfig.transparentModeAlpha/255f;
		
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
		    alpha = IConfig.transparentModeAlpha/255f;
		else if(color!=null) alpha = (float)color.getAlpha()/255;
		//else alpha = defaultAlpha;
		else alpha = (float)IConfig.objectColor.getAlpha()/255;
		
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
	    */
		
	}
    }
    
}
