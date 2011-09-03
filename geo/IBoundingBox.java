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

package igeo.geo;

import java.util.ArrayList;

import igeo.core.*;

/**
   Bounding box described by minimum point and maximum point.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IBoundingBox{
    public IVec min=null, max=null;
    
    public IBoundingBox(){}

    /**
       Calculates bounding box of all the objects in IServer
    */
    public void setObjects(IServer server){ setObjects(server.getObjects()); }
    
    public void compare(IVec p){
	if(min==null||max==null){ init(p); return; }
	if(p.x < min.x) min.x=p.x;
	else if(p.x > max.x) max.x=p.x;
	if(p.y < min.y) min.y=p.y;
	else if(p.y > max.y) max.y=p.y;
	if(p.z < min.z) min.z=p.z;
	else if(p.z > max.z) max.z=p.z;
    }
    
    public void init(IVec p){
	min = new IVec(p);
	max = new IVec(p);
    }
    
    synchronized public void setObjects(ArrayList<IObject> objects){
	
	//IOut.p("objects.size()="+objects.size());
	if(objects.size()>1000) IOut.debug(10, "calculating bounding box of "+objects.size()+" objects"); //
	
	boolean first = true;
	for(IObject e:objects){
	    if(e.visible()){
		if(e instanceof IPoint){
		    IPoint p = (IPoint)e;
		    if(first){ init(p.get()); first=false; }
		    else compare(p.get());
		}
		else if(e instanceof IPointR){
		    IPointR p = (IPointR)e;
		    if(first){ init(p.get()); first=false; }
		    else compare(p.get());
		}
		else if(e instanceof ICurve){
		    ICurve c = (ICurve)e;
		    for(int i=0; i<c.num(); i++)
			if(first){ init(c.cp(i).get()); first=false; }
			else compare(c.cp(i).get());
		}
		else if(e instanceof ICurveR){
		    ICurveR c = (ICurveR)e;
		    for(int i=0; i<c.num(); i++)
			if(first){ init(c.cp(i).get()); first=false; }
			else compare(c.cp(i).get());
		}
		else if(e instanceof ISurface){
		    ISurface s = (ISurface)e;
		    for(int i=0; i<s.unum(); i++){
			for(int j=0; j<s.vnum(); j++){
			    if(first){ init(s.cp(i,j).get()); first=false; }
			    else compare(s.cp(i,j).get());
			}
		    }
		}
		else if(e instanceof ISurfaceR){
		    ISurfaceR s = (ISurfaceR)e;
		    for(int i=0; i<s.unum(); i++){
			for(int j=0; j<s.vnum(); j++){
			    if(first){ init(s.cp(i,j).get()); first=false; }
			    else compare(s.cp(i,j).get());
			}
		    }
		}
		else if(e instanceof IMesh){
		    IMesh m = (IMesh)e;
		    for(int i=0; i<m.vertexNum(); i++){
			if(first){ init(m.vertex(i).get()); first=false; }
			else compare(m.vertex(i).get()); 
		    }
		}
		else if(e instanceof IMeshR){
		    IMeshR m = (IMeshR)e;
		    for(int i=0; i<m.vertexNum(); i++){
			if(first){ init(m.vertex(i).get()); first=false; }
			else compare(m.vertex(i).get()); 
		    }
		}
	    }
	}
	
	if(min.eq(max, IConfig.minimumBoundingBox)){
	    IOut.err("bounding box is too small. minimum size is set");
	    IVec sz = new IVec(IConfig.minimumBoundingBox,
			       IConfig.minimumBoundingBox,
			       IConfig.minimumBoundingBox);
	    sz.div(2);
	    max.set(min).add(sz);
	    min.sub(sz);
	}
	
	
	if(objects.size()>1000) IOut.debug(10, "calculation of bounding box completed");
	
	//IOut.err("min = "+min);
        //IOut.err("max = "+max);
	
	//debug
	//new IPoint(min.x,min.y,min.z);
	//new IPoint(max.x,min.y,min.z);
	//new IPoint(min.x,max.y,min.z);
	//new IPoint(max.x,max.y,min.z);
	//new IPoint(min.x,min.y,max.z);
	//new IPoint(max.x,min.y,max.z);
	//new IPoint(min.x,max.y,max.z);
	//new IPoint(max.x,max.y,max.z);
	
    }
    
    public String toString(){
	//return min!=null?min.toString():"null" + "-" + max!=null?max.toString():"null";
	String s1 = null;
	if(min!=null) s1=min.toString();
	String s2 = null;
	if(max!=null) s2=max.toString();
	return s1+"-"+s2;
    }
    
}
