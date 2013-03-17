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

import java.util.ArrayList;

import igeo.*;

/**
   Polyline geometry to assist graphic classes of curves and surfaces.
   
   @author Satoru Sugihara
*/
public class IPolyline{
    public IVec[] pts;
    public boolean closed=false;
    
    public IPolyline(){}
    public IPolyline(int num){ pts = new IVec[num]; }
    public IPolyline(IVec[] pts){ this.pts = pts; }
    
    public IPolyline(IVecI pt1, IVecI pt2){
	pts = new IVec[2];
	pts[0] = pt1.get();
	pts[1] = pt2.get();
    }
    public IPolyline(IVec pt1, IVec pt2){
	pts = new IVec[2];
	pts[0] = pt1;
	pts[1] = pt2;
    }
    
    public int num(){ return pts.length; }
    
    public IVec get(int i){ return pts[i]; }
    
    public IVec[] get(){ return pts; }

    public void set(int i, IVecI pt){ pts[i]=pt.get(); }
    public void set(int i, IVec pt){ pts[i]=pt; }
    
    public boolean isClosed(){ return closed; }
    public void close(){
	closed=true;
	// check if there's duplicates in the beginning and the end
	if(pts[0].eq(pts[pts.length-1])){
	    IVec[] pts2 = new IVec[pts.length-1];
	    for(int i=0; i<pts.length-1; i++) pts2[i] = pts[i];
	    pts = pts2;
	}
    }
    public void open(){ closed=false; }
    
    public void append(IPolyline l){ append(l.pts); }
    
    public void append(IPolyline l, boolean reverseDir){ append(l.pts, reverseDir); }
    
    public void append(IVec[] pts2){ append(pts2,false); }
    
    public void append(IVec[] pts2, boolean reverseDir){
	if(pts==null || pts.length==0){
	    pts = new IVec[pts2.length];
	    if(!reverseDir) for(int i=0; i<pts.length; i++) pts[i] = pts2[i];
	    else for(int i=0; i<pts.length; i++) pts[i] = pts2[pts2.length-1-i];
	    return;
	}
	
	if(reverseDir){
	    int startIdx=pts2.length-1;
	    int endIdx = 0;
	    if(pts[pts.length-1].eq(pts2[pts2.length-1])) startIdx=pts2.length-2;
	    if(pts[0].eq(pts2[0])){ endIdx=1; } // remove duplicated closing pt
	    
	    IVec[] pts3 = new IVec[pts.length+startIdx-endIdx+1];
	    
	    for(int i=0; i<pts.length; i++) pts3[i] = pts[i];
	    for(int i=startIdx; i>=endIdx; i--) pts3[pts.length + (startIdx-i) ] = pts2[i];
	    
	    pts = pts3;
	}
	else{
	    int startIdx=0;
	    int endIdx = pts2.length-1;
	    if(pts[pts.length-1].eq(pts2[0])) startIdx=1;
	    if(pts[0].eq(pts2[pts2.length-1])){ endIdx=pts2.length-2; } // remove duplicated closing pt
	    
	    IVec[] pts3 = new IVec[pts.length+endIdx-startIdx+1];
	    for(int i=0; i<pts.length; i++) pts3[i] = pts[i];
	    for(int i=startIdx; i<=endIdx; i++) pts3[i-startIdx+pts.length] = pts2[i];
	    pts = pts3;
	}
    }
    
}
