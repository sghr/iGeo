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
import java.util.ArrayList;

import igeo.*;

/**
   Base class of OpenGL graphic vertex data collection
   
   @author Satoru Sugihara
*/
public abstract class IGLElement{
    public IVec[] pts;
    public IVec[] normal;
    
    public ArrayList<IVec> tmpPts=null;
    public ArrayList<IVec> tmpNormal=null;
    
    public IGLElement(){}
    public IGLElement(int size){ pts = new IVec[size]; }
    public IGLElement(IVec[] pts){ this.pts = pts; }
    public IGLElement(IVec[] pts, IVec[] nrm){ this.pts = pts; normal=nrm; }
    
    public void setPoint(IVec[] pts){ this.pts=pts; }
    public void setNormal(IVec[] n){ normal=n; }
    
    public void setPointNum(int len){ pts = new IVec[len]; }
    public void setNormalNum(int len){ normal = new IVec[len]; }
    public void setPoint(int i, IVec pt){ pts[i] = pt; }
    public void setNormal(int i, IVec n){ normal[i] = n; }
    
    public int getNum(){ return num(); }
    public int num(){
	if(pts!=null) return pts.length;
	if(tmpPts!=null) return tmpPts.size();
	return 0;
    }
    
    /**
       addPoint(IVec p) and addPoint(IVec p, IVec n) cannot be used together.
       points needs to be set in either of them consistently.
       Or the number of normal doesn't match with points.
     */
    public void addPoint(IVec p){
	if(tmpPts==null){ tmpPts=new ArrayList<IVec>(); }
	tmpPts.add(p);
    }
    
    public void addPoint(IVec p, IVec n){
	if(tmpPts==null){
	    tmpPts=new ArrayList<IVec>();
	    tmpNormal=new ArrayList<IVec>();
	}
	tmpPts.add(p);
	tmpNormal.add(n);
    }
    
    public void finalizePoint(){
	if(tmpPts==null){ IOut.err("tmpPts == null");  return; }
	pts = new IVec[tmpPts.size()];
	for(int i=0; i<tmpPts.size(); i++) pts[i] = tmpPts.get(i);
	tmpPts=null;
	
	if(tmpNormal!=null){
	    if(tmpNormal.size()!=pts.length){
		IOut.err("number of normals doesn't match with number of points");
		return;
	    }
	    
	    normal = new IVec[tmpNormal.size()];
	    for(int i=0; i<tmpNormal.size(); i++) normal[i] = tmpNormal.get(i);
	    tmpNormal=null;
	}
	else{
	    normal = null; // reset
	}
    }
    
    public void drawPoints(GL2 gl){
	if(normal!=null){
	    for(int i=0; i<pts.length; i++){
		gl.glNormal3d(normal[i].x,normal[i].y,normal[i].z);
		gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
		//gl.glVertex3f((float)pts[i].x,(float)pts[i].y,(float)pts[i].z);
	    }
	}
	else{
	    for(int i=0; i<pts.length; i++){
		gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
		//gl.glVertex3f((float)pts[i].x,(float)pts[i].y,(float)pts[i].z);
	    }
	}
    }
    
    public abstract void draw(GL2 gl);
}
