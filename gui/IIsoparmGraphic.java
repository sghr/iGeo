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

import java.util.ArrayList;
import java.awt.Color;

import igeo.geo.*;
import igeo.core.*;


/**
   Class to draw isoparms of a surface.
   This class is mainly used in ISurfaceGrahpicWireframeGL.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IIsoparmGraphic{
    
    //public ISurfaceI surface;
    //public boolean uOrV=true; //true:U, false:V
    //public double param;
    
    //public IPolyline2D[] polyline2;
    public IPolyline[] polyline=null; // this can be null if the whole line is outisde the trim loop
    
    public IIsoparmGraphic(ISurfaceI surf, double param, boolean uOrV){
	//surface = surf;
	//this.param = param;
	//this.uOrV=uOrV;
	//init();
	init(surf,param,uOrV);
    }
    
    public IIsoparmGraphic(ISurfaceI surf, double param, boolean uOrV,
			   ITrimLoopGraphic[] outtrims,
			   ITrimLoopGraphic[] intrims){
	//surface = surf;
	//this.param = param;
	//this.uOrV=uOrV;
	//init(outtrims,intrims);
	init(surf,param,uOrV,outtrims,intrims);
    }
    
    public void init(ISurfaceI surface, double param, boolean uOrV){
	int reso = IConfig.surfaceIsoparmResolution*
	    IConfig.surfaceWireframeResolution;
		
	// polyline2 is not created
	polyline = new IPolyline[1];
	if(uOrV){
	    // u
	    int epnum = surface.uepNum();
	    if(surface.udeg()==1){
		polyline[0] = new IPolyline(epnum);
		for(int i=0; i<epnum; i++)
		    polyline[0].set(i,surface.pt(surface.u(i,0),param));
	    }
	    else{
		polyline[0] = new IPolyline((epnum-1)*reso+1);
		for(int i=0; i<epnum; i++)
		    for(int j=0; j<reso&&(i<epnum-1) || j==0; j++)
			polyline[0].set(i*reso+j,surface.pt(surface.u(i,(double)j/reso),param));
	    }
	}
	else{
	    // v
	    int epnum = surface.vepNum();
	    if(surface.vdeg()==1){
		polyline[0] = new IPolyline(epnum);
		for(int i=0; i<epnum; i++)
		    polyline[0].set(i,surface.pt(param,surface.v(i,0)));
	    }
	    else{
		polyline[0] = new IPolyline((epnum-1)*reso+1);
		for(int i=0; i<epnum; i++)
		    for(int j=0; j<reso&&(i<epnum-1) || j==0; j++)
			polyline[0].set(i*reso+j,surface.pt(param,surface.v(i,(double)j/reso)));
	    }
	}
    }
    
    public void init(ISurfaceI surface, double param, boolean uOrV,
		     ITrimLoopGraphic[] outtrims,
		     ITrimLoopGraphic[] intrims){
	
	if(outtrims==null && intrims==null){ init(surface,param,uOrV); return; }
	
	int reso = IConfig.surfaceIsoparmResolution*
	    IConfig.surfaceWireframeResolution;
	
	IPolyline2D[] outline = null;
	if(outtrims!=null){
	    outline= new IPolyline2D[outtrims.length];
	    for(int i=0; i<outtrims.length; i++) outline[i] = outtrims[i].getPolyline2D();
	}
	
	IPolyline2D[] inline = null;
	if(intrims!=null){
	    inline = new IPolyline2D[intrims.length];
	    for(int i=0; i<intrims.length; i++) inline[i] = intrims[i].getPolyline2D();
	}
	
	IPolyline2D[] lines=null;
	
	if(uOrV) lines = IPolyline2D.xLineInside(param, outline, inline);
	else lines = IPolyline2D.yLineInside(param, outline, inline);
	
	if(lines==null) return; // nothing inside the trim
	
	int epnum = 0;
	if(uOrV) epnum = surface.uepNum(); else epnum = surface.vepNum();
	
	int deg=0;
	if(uOrV) deg = surface.udeg(); else deg = surface.vdeg();
	
	// insert pts
	int insertIdx=0;
	//int minLineIdx=0;
	for(int i=0; i<epnum-1; i++){
	    double prm = 0;
	    for(int j=0; j==0&&deg==1 || j<reso&&deg>1; j++){
		if(i>0||j>0){
		    if(uOrV) prm = surface.u(i,(double)j/reso);
		    else  prm = surface.v(i,(double)j/reso);
		    
		    for(; insertIdx<lines.length &&
			    !( (uOrV &&
				lines[insertIdx].get(lines[insertIdx].num()-1).x>prm)||
			       (!uOrV &&
				lines[insertIdx].get(lines[insertIdx].num()-1).y>prm));
			insertIdx++);
		    
		    if(insertIdx < lines.length &&
		       (uOrV && lines[insertIdx].get(0).x < prm ||
			!uOrV && lines[insertIdx].get(0).y < prm )){
			
			IVec2 ipt=null;
			if(uOrV) ipt = new IVec2(prm, lines[insertIdx].get(0).y);
			else ipt =  new IVec2(lines[insertIdx].get(0).x, prm);
			lines[insertIdx].insert(lines[insertIdx].num()-1,ipt);
		    }
		    
		    /*
		    for(; minLineIdx<lines.length &&
			    ( (uOrV &&
			       lines[minLineIdx].get(lines[minLineIdx].num()-1).x<prm)||
			      (!uOrV &&
			       lines[minLineIdx].get(lines[minLineIdx].num()-1).y<prm));
			minLineIdx++);
		    
		    int k=minLineIdx;
		    for(; k<lines.length &&
			    !( (uOrV &&
				lines[k].get(0).x < prm &&
				lines[k].get(lines[k].num()-1).x > prm)  ||
			       (!uOrV &&
				lines[k].get(0).y < prm &&
				lines[k].get(lines[k].num()-1).y > prm) );
			k++);
		    
		    if(k<lines.length){
			IVec2 ipt=null;
			if(uOrV) ipt = new IVec2(prm, lines[k].get(0).y);
			else ipt =  new IVec2(lines[k].get(0).x, prm);
			lines[k].insert(lines[k].num()-1,ipt);
		    }
		    */
		}
	    }
	}
	
	polyline = new IPolyline[lines.length];
		
	for(int i=0; i<lines.length; i++){
	    polyline[i] = new IPolyline(lines[i].num());
	    for(int j=0; j<lines[i].num(); j++)
		polyline[i].set(j,surface.pt(lines[i].get(j)));
	}
	
	/*
	if( uOrV&&surface.udeg()==1 || !uOrV&&surface.vdeg()==1 ){
	    for(int i=0; i<lines.length; i++)
		polyline[i] = new IPolyline(surface.pt(lines[i].get(0)),
					     surface.pt(lines[i].get(1)));
	    return;
	}
	
	double unit = 1.0/(epnum*reso);
	if(!IConfig.normalizeKnots)
	    if(uOrV) unit*=(surface.uend()-surface.ustart());
	    else unit*=(surface.vend()-surface.vstart());
	
	for(int i=0; i<lines.length; i++){
	    double len=0;
	    if(uOrV) len = lines[i].get(1).x-lines[i].get(0).x;
	    else len = lines[i].get(1).y-lines[i].get(0).y;
	    
	    int div = (int)(len/unit) + 1;
	    
	    polyline[i] = new IPolyline(div+1);
	    for(int j=0; j<=div; j++){
		polyline[i].set(j,surface.pt(lines[i].get(0).sum(lines[i].get(1),(double)j/div)));
	    }
	}
	*/
    }
    
    public int num(){ if(polyline==null) return 0; return polyline.length; }
    
    public IPolyline getLine(int i){ return polyline[i]; }
    
}
