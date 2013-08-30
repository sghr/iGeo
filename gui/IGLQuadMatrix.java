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
//package igeo.gl;

import javax.media.opengl.*;

import igeo.*;

/**
   Objectified OpenGL drawing process of matrix of quad geometries by quad strips.
   
   @author Satoru Sugihara
*/
public class IGLQuadMatrix extends IGLElement{
    public int width, height;
    
    private IGLQuadMatrix(){}
    private IGLQuadMatrix(int width, int height){
	super(width*height);
	this.width=width;
	this.height=height;
    }
    private IGLQuadMatrix(IVec[][] ptmatrix){
	super(ptmatrix.length*ptmatrix[0].length);
	width=ptmatrix.length;
	height=ptmatrix[0].length;
	for(int i=0; i<height; i++){
	    for(int j=0; j<width; j++){
		pts[i*width+j] = ptmatrix[j][i];
	    }
	}
    }
    
    private IGLQuadMatrix(IVec[][] ptmatrix, IVec[][] normalMatrix){
	super(ptmatrix.length*ptmatrix[0].length);
	setNormalNum(ptmatrix.length*ptmatrix[0].length);
	
	width=ptmatrix.length;
	height=ptmatrix[0].length;
	for(int i=0; i<height; i++){
	    for(int j=0; j<width; j++){
		pts[i*width+j] = ptmatrix[j][i];
		normal[i*width+j] = normalMatrix[j][i];
	    }
	}
    }
    
    
    public int width(){ return width; }
    public int height(){ return height; }
    
    public void setPoint(int x, int y, IVec pt){ pts[y*width+x] = pt; }
    
    public void setPoint(int x, int y, IVec pt, IVec nrm){
	pts[y*width+x] = pt;
	if(normal==null){ setNormalNum(width*height); }
	normal[y*width+x] = nrm;
    }
    
    public void draw(GL2 gl){
	if(normal!=null){
	    for(int i=0; i<height-1; i++){
		gl.glBegin(GL2.GL_QUAD_STRIP);
		for(int j=0; j<width; j++){
		    IVec n1 = normal[i*width+j];
		    gl.glNormal3d(n1.x,n1.y,n1.z);
		    IVec pt1 = pts[i*width+j];
		    gl.glVertex3d(pt1.x,pt1.y,pt1.z);
		    //gl.glVertex3f((float)pt1.x,(float)pt1.y,(float)pt1.z);
		    IVec n2 = normal[(i+1)*width+j];
		    gl.glNormal3d(n2.x,n2.y,n2.z);
		    IVec pt2 = pts[(i+1)*width+j];
		    gl.glVertex3d(pt2.x,pt2.y,pt2.z);
		    //gl.glVertex3f((float)pt2.x,(float)pt2.y,(float)pt2.z);
		}
		gl.glEnd();
	    }
	}
	else{
	    for(int i=0; i<height-1; i++){
		gl.glBegin(GL2.GL_QUAD_STRIP);
		for(int j=0; j<width; j++){
		    IVec pt1 = pts[i*width+j];
		    gl.glVertex3d(pt1.x,pt1.y,pt1.z);
		    //gl.glVertex3f((float)pt1.x,(float)pt1.y,(float)pt1.z);
		    IVec pt2 = pts[(i+1)*width+j];
		    gl.glVertex3d(pt2.x,pt2.y,pt2.z);
		    //gl.glVertex3f((float)pt2.x,(float)pt2.y,(float)pt2.z);
		}
		gl.glEnd();
	    }
	}
    }
    
    
}
