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

package igeo;

import igeo.gui.IPolyline2D;
import igeo.gui.ITrimLoopGraphic;

/**
   Point cache of a surface trim curves, for checking a point is inside the trim or not
   
   @author Satoru Sugihara
*/
public class ITrimCache{
    
    public ISurfaceGeo surface;
    public int resolution;
    public int udeg, vdeg;
    public IPolyline2D[] outTrims;
    public IPolyline2D[] inTrims;
    
    
    public ITrimCache(ISurfaceGeo surf, int resolution){
	surface = surf;
	this.resolution = resolution;
	init();
    }
    
    public ITrimCache(ISurfaceGeo surf){
	this(surf,IConfig.segmentResolution);
    }
    
    public void init(){
	if(surface.hasOuterTrim()){
	    outTrims = new IPolyline2D[surface.outerTrimLoopNum()];
	    for(int i=0; i<surface.outerTrimLoopNum(); i++){
		ITrimLoopGraphic tg = new ITrimLoopGraphic(surface.outerTrimLoop(i),true,resolution);
		outTrims[i] = tg.getPolyline2D();
	    }
	}
	
	if(surface.hasInnerTrim()){
	    inTrims = new IPolyline2D[surface.innerTrimLoopNum()];
	    for(int i=0; i<surface.innerTrimLoopNum(); i++){
		ITrimLoopGraphic tg = new ITrimLoopGraphic(surface.innerTrimLoop(i),true,resolution);
		inTrims[i] = tg.getPolyline2D();
	    }
	}
    }
    
    public boolean isInside(IVec2I v){ return isInside(v.get()); }
    public boolean isInside(double u, double v){ return isInside(new IVec2(u,v)); }
    
    /** check a 2d point is inside the trim curves. the condition where out-trim loop is inside in-trim loop is ignored and it'd be false. */
    public boolean isInside(IVec2 v){
	
	if(inTrims!=null){
	    for(int i=0; i<inTrims.length; i++) if(v.isInside(inTrims[i].get())) return false; // inside intrim
	}
	
	if(outTrims!=null){
	    for(int i=0; i<outTrims.length; i++) if(v.isInside(outTrims[i].get())) return true; // inside outtrim
	    if(outTrims.length>0) return false;
	}
	
	if(v.x < 0. || v.x > 1. || v.y < 0. || v.y > 1.) return false;
	
	return true;
    }
    
}
