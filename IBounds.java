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

import java.util.ArrayList;

/**
   Bounding box described by minimum point and maximum point.
   
   @author Satoru Sugihara
*/
public class IBounds{
    public IVec min=null, max=null;
    
    public IBounds(){}

    public IBounds(IVec p){ init(p); }
    
    public IBounds(IObject obj){ compare(obj); }
    
    public IBounds(IVec min, IVec max){ this.min=min; this.max=max; }
    
    public IBounds(IVec corner, double xwidth, double yheight, double zdepth){
	init(corner);
	compare(corner.add(xwidth,yheight,zdepth));
    }
    
    public IBounds(double x, double y, double z, double xwidth, double yheight, double zdepth){
	IVec p = new IVec(x,y,z);
	init(p);
	compare(p.add(xwidth,yheight,zdepth));
    }
    
    public IBounds(IObject[] objects){
	for(int i=0; i<objects.length; i++){ compare(objects[i]); }
    }
    
    public IBounds(ArrayList<IObject> objects){
	for(int i=0; i<objects.size(); i++){ compare(objects.get(i)); }
    }
    
    
    public IVec min(){ return min; }
    public IVec getMin(){ return min(); }
    public IVec max(){ return max; }
    public IVec getMax(){ return max(); }
    
    public double minX(){ return min.x; }
    public double minY(){ return min.y; }
    public double minZ(){ return min.z; }
    public double maxX(){ return max.x; }
    public double maxY(){ return max.y; }
    public double maxZ(){ return max.z; }
    public double width(){ return max.x - min.x; }
    public double height(){ return max.y - min.y; }
    public double depth(){ return max.z - min.z; }
    
    public IVec size(){
	if(min==null||max==null) return null;
	return max.dif(min);
    }
    public IVec getSize(){ return size(); }

    public IVec center(){
	if(min==null||max==null) return null;
	return max.mid(min);
    }
    public IVec getCenter(){ return center(); }
    
    
    public void init(IVec p){
	min = new IVec(p);
	max = new IVec(p);
    }
    
    public void init(){ min=max=null; }
    
    public void compare(IVec p){
	if(p==null || !p.isValid()) return;
	if(min==null||max==null){ init(p); return; }
	if(p.x < min.x) min.x=p.x;
	else if(p.x > max.x) max.x=p.x;
	if(p.y < min.y) min.y=p.y;
	else if(p.y > max.y) max.y=p.y;
	if(p.z < min.z) min.z=p.z;
	else if(p.z > max.z) max.z=p.z;
    }
    
    public void compare(IObject e){
	synchronized(e){
	    
	    if(!e.visible()) return; // if e is in the middle of constructor, this should be false.
	    
	    if(e instanceof IPoint){
		IPoint p = (IPoint)e;
		compare(p.get());
	    }
	    else if(e instanceof IPointR){
		IPointR p = (IPointR)e;
		compare(p.get());
	    }
	    else if(e instanceof ICurve){
		ICurve c = (ICurve)e;
		for(int i=0; i<c.num(); i++) compare(c.cp(i).get());
	    }
	    else if(e instanceof ICurveR){
		ICurveR c = (ICurveR)e;
		for(int i=0; i<c.num(); i++) compare(c.cp(i).get());
	    }
	    else if(e instanceof ISurface){
		ISurface s = (ISurface)e;
		for(int i=0; i<s.unum(); i++){
		    for(int j=0; j<s.vnum(); j++){ compare(s.cp(i,j).get()); }
		}
	    }
	    else if(e instanceof ISurfaceR){
		ISurfaceR s = (ISurfaceR)e;
		for(int i=0; i<s.unum(); i++){
		    for(int j=0; j<s.vnum(); j++){ compare(s.cp(i,j).get()); }
		}
	    }
	    else if(e instanceof IMesh){
		IMesh m = (IMesh)e;
		for(int i=0; i<m.vertexNum(); i++) compare(m.vertex(i).get());
	    }
	    else if(e instanceof IMeshR){
		IMeshR m = (IMeshR)e;
		for(int i=0; i<m.vertexNum(); i++){ compare(m.vertex(i).get()); }
	    }
	    else if(e instanceof IBrep){
		IBrep b = (IBrep)e;
		for(int i=0; i<b.surfaces.length; i++){
		    for(int j=0; j<b.surfaces[i].unum(); j++){
			for(int k=0; k<b.surfaces[i].vnum(); k++){ compare(b.surfaces[i].cp(j,k).get()); }
		    }
		}
	    }
	    else if(e instanceof IVectorObject){
		IVectorObject vobj = (IVectorObject)e;
		compare(vobj.vec.get());
		compare(vobj.root.get());
	    }
	    else if(e instanceof IText){
		IText txt = (IText)e;
		compare(txt.corner(0,0));
		compare(txt.corner(1,0));
		compare(txt.corner(0,1));
		compare(txt.corner(1,1));
	    }
	}
    }
    
    
    /** Calculates bounding box of all the visible objects in IServer */
    public void setObjects(IServer server){ setObjects(server.getAllObjects()); }
    
    synchronized public void setObject(IObject object){
	ArrayList<IObject> objects = new ArrayList<IObject>();
	objects.add(object);
	setObjects(objects);
    }
    
    synchronized public void setObjects(ArrayList<IObject> objects){
	//IOut.err("objects.size()="+objects.size());
	if(objects.size()>1000) IOut.debug(10, "calculating bounding box of "+objects.size()+" objects"); //
	
	//boolean first = true;
	init();
	synchronized(objects){
	    //for(IObject e:objects){
	    for(int n=0; n<objects.size(); n++){
		IObject e = objects.get(n);
		
		if(e.visible()){
		    compare(e);
		    
		    /*
		    if(e instanceof IPoint){
			IPoint p = (IPoint)e;
			compare(p.get());
			//if(first){ init(p.get()); first=false; }
			//else compare(p.get());
		    }
		    else if(e instanceof IPointR){
			IPointR p = (IPointR)e;
			compare(p.get());
			//if(first){ init(p.get()); first=false; }
			//else compare(p.get());
		    }
		    else if(e instanceof ICurve){
			ICurve c = (ICurve)e;
			for(int i=0; i<c.num(); i++)
			    compare(c.cp(i).get());
			//if(first){ init(c.cp(i).get()); first=false; }
			//else compare(c.cp(i).get());
		    }
		    else if(e instanceof ICurveR){
			ICurveR c = (ICurveR)e;
			for(int i=0; i<c.num(); i++)
			    compare(c.cp(i).get());
			//if(first){ init(c.cp(i).get()); first=false; }
			//else compare(c.cp(i).get());
		    }
		    else if(e instanceof ISurface){
			ISurface s = (ISurface)e;
			for(int i=0; i<s.unum(); i++){
			    for(int j=0; j<s.vnum(); j++){
				compare(s.cp(i,j).get());
				//if(first){ init(s.cp(i,j).get()); first=false; }
				//else compare(s.cp(i,j).get());
			    }
			}
		    }
		    else if(e instanceof ISurfaceR){
			ISurfaceR s = (ISurfaceR)e;
			for(int i=0; i<s.unum(); i++){
			    for(int j=0; j<s.vnum(); j++){
				compare(s.cp(i,j).get());
				//if(first){ init(s.cp(i,j).get()); first=false; }
				//else compare(s.cp(i,j).get());
			    }
			}
		    }
		    else if(e instanceof IMesh){
			IMesh m = (IMesh)e;
			for(int i=0; i<m.vertexNum(); i++){
			    compare(m.vertex(i).get()); 
			    //if(first){ init(m.vertex(i).get()); first=false; }
			    //else compare(m.vertex(i).get()); 
			}
		    }
		    else if(e instanceof IMeshR){
			IMeshR m = (IMeshR)e;
			for(int i=0; i<m.vertexNum(); i++){
			    compare(m.vertex(i).get()); 
			    //if(first){ init(m.vertex(i).get()); first=false; }
			    //else compare(m.vertex(i).get()); 
			}
		    }
		    else if(e instanceof IBrep){
			IBrep b = (IBrep)e;
			for(int i=0; i<b.surfaces.length; i++){
			    for(int j=0; j<b.surfaces[i].unum(); j++){
				for(int k=0; k<b.surfaces[i].vnum(); k++){
				    compare(b.surfaces[i].cp(j,k).get());
				    //if(first){ init(s.cp(i,j).get()); first=false; }
				    //else compare(s.cp(i,j).get());
				}
			    }
			}
		    }
		    else if(e instanceof IVectorObject){
			IVectorObject vobj = (IVectorObject)e;
			compare(vobj.vec.get());
			compare(vobj.root.get());
			//if(first){
			//	init(vobj.vec.get()); first=false;
			//	compare(vobj.root.get());
			//}
			//else{
			//	compare(vobj.vec.get());
			//	compare(vobj.root.get());
			//}
		    }
		    */
		}
	    }
	}
	
	if(min!=null && max!=null && min.eq(max, IConfig.tolerance)){
	    
	    if(min.x==max.x && min.y==max.y && min.z==max.z){ // just a point
		// keep the size zero
	    }
	    else{
		IOut.err("bounding box is too small. minimum size is set");
		IVec sz = new IVec(IConfig.tolerance,
				   IConfig.tolerance,
				   IConfig.tolerance);
		sz.div(2);
		max.set(min).add(sz);
		min.sub(sz);
	    }
	}
	
	if(objects.size()>1000) IOut.debug(10, "calculation of bounding box completed");
	IOut.debug(100, this);
	
    }
    
    public String toString(){
	//return min!=null?min.toString():"null" + "-" + max!=null?max.toString():"null";
	String s1 = "null";
	if(min!=null) s1=min.toString();
	String s2 = "null";
	if(max!=null) s2=max.toString();
	return s1+"-"+s2;
    }
    
}
