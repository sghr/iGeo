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
   Objectified OpenGL triangle drawing process.
   
   @author Satoru Sugihara
*/
public class IGLTriangles extends IGLElement{
    
    private IGLTriangles(IVec[][] triangles){
	super(triangles.length*3);
	for(int i=0; i<triangles.length; i++){
	    pts[i*3] = triangles[i][0];
	    pts[i*3+1] = triangles[i][1];
	    pts[i*3+2] = triangles[i][2];
	}
    }
    
    private IGLTriangles(IVec[][] triangles, IVec[][] normals){
	super(triangles.length*3);
	setNormalNum(triangles.length*3);
	for(int i=0; i<triangles.length; i++){
	    pts[i*3] = triangles[i][0];
	    pts[i*3+1] = triangles[i][1];
	    pts[i*3+2] = triangles[i][2];
	    
	    normal[i*3] = normals[i][0];
	    normal[i*3+1] = normals[i][1];
	    normal[i*3+2] = normals[i][2];
	}
    }
    
    public int triangleNum(){ if(pts==null) return 0; return pts.length/3; }
    
    public void setPoint(int triangleIdx, int vertexIdx, IVec pt){
	pts[triangleIdx*3 + vertexIdx] = pt;
    }
    public void setPoint(int triangleIdx, int vertexIdx, IVec pt, IVec nml){
	pts[triangleIdx*3 + vertexIdx] = pt;
	normal[triangleIdx*3 + vertexIdx] = nml;
    }
    
    public void draw(GL2 gl){
	gl.glBegin(GL2.GL_TRIANGLES);
	drawPoints(gl);
	gl.glEnd();
    }
    
    
}
