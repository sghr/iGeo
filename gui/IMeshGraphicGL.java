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
    public IColor[][] vertexColor;

    public IMesh meshObj=null; // if mesh is IMesh
    
    public IMeshGraphicGL(IMesh m){ super(m); }
    public IMeshGraphicGL(IMeshR m){ super(m); }
    
    public void initMesh(){
	synchronized(parent){
	    if(mesh==null){
		if(parent instanceof IMesh){
		    mesh = ((IMesh)parent).mesh;
		    meshObj = (IMesh)parent;
		}
		else if(parent instanceof IMeshR){
		    mesh = ((IMeshR)parent).mesh;
		}
	    }
	
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
	    if(meshObj!=null && meshObj.enableVertexColor){
		vertexColor = new IColor[mesh.faceNum()][];
	    }
	    for(int i=0; i<mesh.faceNum(); i++){
		IFace face = mesh.face(i);
		facePts[i] = new IVec[face.vertexNum()];
		faceNormal[i] = new IVec[face.vertexNum()];
		
		if(meshObj!=null && meshObj.enableVertexColor){
		    vertexColor[i] = new IColor[face.vertexNum()];
		}
		for(int j=0; j<face.vertexNum(); j++){
		    facePts[i][j] = face.getVertex(j).get();
		    faceNormal[i][j] = face.getVertex(j).normal().get();
		    
		    if(meshObj!=null && meshObj.enableVertexColor){
			vertexColor[i][j] = face.getVertex(j).clr();
			
			IG.p(vertexColor[i][j]); //
			
			if(vertexColor[i][j]==null){
			    vertexColor[i][j]=color;
			    if(vertexColor[i][j]==null){
				vertexColor[i][j] = IConfig.objectColor;
			    }
			}
		    }
		    
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
	if(mesh==null || update) initMesh(); // not initizlized at the constructor // shouldn't it?
	
	if(g.type() == IGraphicMode.GraphicType.GL||
	   g.type() == IGraphicMode.GraphicType.P3D){
	    
	    IGraphics3D g3d = (IGraphics3D)g;
	    
	    //float red,green,blue,alpha;
	    float[] rgba=null;
	    if(color!=null){
		rgba = color.rgba();
	    }
	    else{
		rgba = IConfig.objectColor.rgba();
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
	    g3d.clr(rgba);
	    
	    if(g3d.view().mode().isFill()){
		
		int prevNum=0;
		for(int i=0; i<facePts.length; i++){
		    if(meshObj!=null && meshObj.enableFaceColor){
			IColor clr = mesh.face(i).clr();
			if(clr==null){ clr=color; }
			if(clr==null){ clr=IConfig.objectColor; }
			float[] frgba = null; 
			if(g3d.view().mode().isTransparent()){
			    frgba = clr.rgba(IConfig.transparentModeAlpha/255f);
			}
			else{
			    frgba = clr.rgba;
			}
			g3d.clr(frgba);
			if(facePts[i].length==3){
			    g3d.drawTriangles(facePts[i],faceNormal[i]);
			}
			else if(facePts[i].length==4){
			    g3d.drawQuads(facePts[i],faceNormal[i]);
			}
			else{
			    g3d.drawPolygon(facePts[i],faceNormal[i]);
			}
		    }
		    else if(meshObj!=null && meshObj.enableVertexColor){
			
			for(int j=0; j<mesh.face(i).vertexNum(); j++){
			    vertexColor[i][j] = mesh.face(i).getVertex(j).clr();
			    if(vertexColor[i][j]==null){
				vertexColor[i][j]=color;
				if(vertexColor[i][j]==null){
				    vertexColor[i][j] = IConfig.objectColor;
				}
			    }
			}
			
			float alpha = -1;
			if(g3d.view().mode().isTransparent()){
			    alpha = IConfig.transparentModeAlpha/255f;
			}
			if(facePts[i].length==3){
			    g3d.drawTriangles(facePts[i],faceNormal[i],vertexColor[i],alpha,g3d.view().mode().isLight());
			}
			else if(facePts[i].length==4){
			    g3d.drawQuads(facePts[i],faceNormal[i],vertexColor[i],alpha,g3d.view().mode().isLight());
			}
			else{
			    g3d.drawPolygon(facePts[i],faceNormal[i],vertexColor[i],alpha,g3d.view().mode().isLight());
			}
		    }
		    else{
			if(facePts[i].length==3){
			    g3d.drawTriangles(facePts[i],faceNormal[i]);
			}
			else if(facePts[i].length==4){
			    g3d.drawQuads(facePts[i],faceNormal[i]);
			}
			else{
			    g3d.drawPolygon(facePts[i],faceNormal[i]);
			}
		    }
		    
		    // test
		    //g3d.clr(new float[]{ IRand.getFloat(1), IRand.getFloat(1), IRand.getFloat(1), IRand.getFloat(1) });
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
	    
		
	}
    }
    
}
