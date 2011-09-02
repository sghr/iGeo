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
   Class of 2 dimensional vector.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IVec2 extends IParameterObject implements IVec2I, IEntityParameter{
    public double x, y;
    
    public IVec2(){}
    public IVec2(double x, double y){ this.x=x; this.y=y; }
    public IVec2(IVec2 v){ x=v.x; y=v.y; }
    public IVec2(IVec2I v){ IVec2 u=v.get(); x=u.x; y=u.y; }
    public IVec2(IDoubleI x, IDoubleI y){ this.x=x.x(); this.y=y.x(); }
    public IVec2(IVecI v){ x = v.x(); y = v.y(); }
    
    public IVec2(IServerI s){ super(s); }
    public IVec2(IServerI s, double x, double y){ super(s); this.x=x; this.y=y; }
    public IVec2(IServerI s, IVec2 v){ super(s); x=v.x; y=v.y; }
    public IVec2(IServerI s, IVec2I v){ super(s); IVec2 u=v.get(); x=u.x; y=u.y; }
    public IVec2(IServerI s, IDoubleI x, IDoubleI y){ super(s); this.x=x.x(); this.y=y.x(); }
    public IVec2(IServerI s, IVecI v){ super(s); x = v.x(); y = v.y(); }
    
    
    public double x(){ return x; }
    public double y(){ return y; }
    //public IVec2 get(){ return this; }
    public IVec2 get(){ return new IVec2(this); }
    public IVec2 dup(){ return new IVec2(this); }
    
    public IVec to3d(){ return new IVec(this); }
    public IVec to3d(double z){ return new IVec(x,y,z); }
    public IVec to3d(IDoubleI z){ return new IVec(x,y,z.x()); }
    public IVec4 to4d(){ return new IVec4(x,y,0.); }
    public IVec4 to4d(double z, double w){ return new IVec4(x,y,z,w); }
    public IVec4 to4d(IDoubleI z, IDoubleI w){ return new IVec4(x,y,z.x(),w.x()); }
    
    public IDouble getX(){ return new IDouble(x); }
    public IDouble getY(){ return new IDouble(y); }
    
    public IVec2 set(double x, double y){ this.x=x; this.y=y; return this; }
    public IVec2 set(IVec2 v){ x=v.x; y=v.y; return this; }
    public IVec2 set(IVec2I v){ return set(v.get()); }
    public IVec2 set(IDoubleI x, IDoubleI y){ this.x=x.x(); this.y=y.x(); return this; }
    
    public IVec2 add(IVec2 v){ x+=v.x; y+=v.y; return this; }
    public IVec2 add(IVec2I v){ return add(v.get()); }
    
    public IVec2 sub(IVec2 v){ x-=v.x; y-=v.y; return this; }
    public IVec2 sub(IVec2I v){ return sub(v.get()); }
    
    public IVec2 mul(double x){ this.x*=x; this.y*=x; return this; }
    public IVec2 mul(IDouble v){ return mul(v.x); }
    public IVec2 mul(IDoubleI v){ return mul(v.x()); }
    
    public IVec2 div(double x){ this.x/=x; this.y/=x; return this; }
    public IVec2 div(IDouble v){ return div(v.x); }
    public IVec2 div(IDoubleI v){ return div(v.x()); }
    
    public IVec2 neg(){ x=-x; y=-y; return this; }

    public IVec2 rev(){ return neg(); }

    /**
       scale add
    */
    public IVec2 add(IVec2 v, double f){ x+=v.x*f; y+=v.y*f; return this; }
    public IVec2 add(IVec2I v, double f){ return add(v.get(),f); }
    public IVec2 add(IVec2I v, IDoubleI f){ add(v.get(),f.x()); return this; }
    
    
    /**
       @return dot product in double (P for Primitive)
    */
    public double dot(IVec2 v){ return x*v.x + y*v.y; }
    public double dot(IVec2I v){ return dot(v.get()); }
    //public IDouble dotR(IVec2 v){ return new IDouble(dot(v)); }
    //public IDouble dotR(IVec2I v){ return dotR(v.get()); }
    public double dot(ISwitchE e, IVec2I v){ return dot(v); }
    public IDouble dot(ISwitchR r, IVec2I v){ return new IDouble(dot(v)); }
    
    /**
       @return cross product in z direction
    */
    public IVec cross(IVec2 v){ return new IVec(0, 0, x*v.y - y*v.x); }
    public IVec cross(IVec2I v){ return cross(v.get()); }
    
    /**
       @return length (norm) of the vector in double (P for Primitive)
    */
    public double len(){ return Math.sqrt(x*x+y*y); }
    //public IDouble lenR(){ return new IDouble(len()); }
    public double len(ISwitchE e){ return len(); }
    public IDouble len(ISwitchR r){ return new IDouble(len()); }
    
    /**
       @return squared length of the vector in double (P for Primitive)
    */
    public double len2(){ return x*x+y*y; }
    //public IDouble len2R(){ return new IDouble(len2()); }
    public double len2(ISwitchE e){ return len2(); }
    public IDouble len2(ISwitchR r){ return new IDouble(len2()); }
    
    public IVec2 len(IDoubleI l){ return len(l.x()); }
    public IVec2 len(double l){ l/=len(); x*=l; y*=l; return this; }
    
    /**
       normalize (unitize) vector
    */
    public IVec2 unit(){ double l=len(); x/=l; y/=l; return this; }
    /**
       rotate vector in Pi/2
    */
    public IVec2 ortho(){ double tmp = x; x=-y; y=tmp; return this; }
    
    
    
    public double dist(IVec2 v){
	return Math.sqrt( (x-v.x)*(x-v.x) + (y-v.y)*(y-v.y) );
    }
    public double dist(IVec2I v){ return dist(v.get()); }
    //public IDouble distR(IVec2I v){ return new IDouble(dist(v)); }
    public double dist(ISwitchE e, IVec2I v){ return dist(v); }
    public IDouble dist(ISwitchR r, IVec2I v){ return new IDouble(dist(v)); }
    
    public double dist2(IVec2 v){
	return (x-v.x)*(x-v.x) + (y-v.y)*(y-v.y);
    }
    public double dist2(IVec2I v){ return dist2(v.get()); }
    //public IDouble dist2R(IVec2I v){ return new IDouble(dist2(v)); }
    public double dist2(ISwitchE e, IVec2I v){ return dist2(v); }
    public IDouble dist2(ISwitchR r, IVec2I v){ return new IDouble(dist2(v)); }
    
    /**
       whether location is same or not
    */
    public boolean eq(IVec2 v){ return eq(v,IConfig.lengthResolution); }
    public boolean eq(IVec2I v){ return eq(v.get(),IConfig.lengthResolution); }
    //public IBool eqR(IVec2I v){ return new IBool(eq(v.get(),IConfig.lengthResolution)); }
    public boolean eq(ISwitchE e, IVec2I v){ return eq(v); }
    public IBool eq(ISwitchR r, IVec2I v){ return new IBool(eq(v)); }
    
    public boolean eq(IVec2 v, double resolution){ return dist2(v) <= resolution*resolution; }
    public boolean eq(IVec2I v, double resolution){ return eq(v.get(),resolution); }
    //public IBool eqR(IVec2I v, IDoubleI resolution){ return new IBool(eq(v.get(),resolution.x())); }    
    public boolean eq(ISwitchE e, IVec2I v, double resolution){ return eq(v,resolution); }
    public IBool eq(ISwitchR r, IVec2I v, IDoubleI resolution){ return new IBool(eq(v,resolution.x())); }    
    
    public boolean eqX(IVec2 v){ return eqX(v,IConfig.lengthResolution); }
    public boolean eqY(IVec2 v){ return eqY(v,IConfig.lengthResolution); }
    public boolean eqX(IVec2I v){ return eqX(v.get(),IConfig.lengthResolution); }
    public boolean eqY(IVec2I v){ return eqY(v.get(),IConfig.lengthResolution); }
    //public IBool eqXR(IVec2I v){ return new IBool(eqX(v.get(),IConfig.lengthResolution)); }
    //public IBool eqYR(IVec2I v){ return new IBool(eqY(v.get(),IConfig.lengthResolution)); }
    public boolean eqX(ISwitchE e, IVec2I v){ return eqX(v); }
    public boolean eqY(ISwitchE e, IVec2I v){ return eqY(v); }
    public IBool eqX(ISwitchR r, IVec2I v){ return new IBool(eqX(v)); }
    public IBool eqY(ISwitchR r, IVec2I v){ return new IBool(eqY(v)); }
    
    public boolean eqX(IVec2 v, double resolution){ return Math.abs(v.x-x)<=resolution; }
    public boolean eqY(IVec2 v, double resolution){ return Math.abs(v.y-y)<=resolution; }
    public boolean eqX(IVec2I v, double resolution){ return Math.abs(v.x()-x)<=resolution; }
    public boolean eqY(IVec2I v, double resolution){ return Math.abs(v.y()-y)<=resolution; }
    //public IBool eqXR(IVec2I v, IDoubleI resolution){ return new IBool(eqX(v,resolution.x())); }
    //public IBool eqYR(IVec2I v, IDoubleI resolution){ return new IBool(eqY(v,resolution.x())); }
    public boolean eqX(ISwitchE e, IVec2I v, double resolution){ return eqX(v,resolution); }
    public boolean eqY(ISwitchE e, IVec2I v, double resolution){ return eqY(v,resolution); }
    public IBool eqX(ISwitchR r, IVec2I v, IDoubleI resolution){ return new IBool(eqX(v,resolution.x())); }
    public IBool eqY(ISwitchR r, IVec2I v, IDoubleI resolution){ return new IBool(eqY(v,resolution.x())); }
    
    
    /**
       @return angle of two vector. From -Pi to Pi. Sign follows right-handed screw rule (P for Primitive)
    */
    public double angle(IVec2 v){
	double dot = x*v.x+y*v.y;
	double len1 = len(); if(len1==0) return 0;
	double len2 = v.len(); if(len2==0) return 0;
	double cross = x*v.y-y*v.x; //if(cross==0) return 0;
	double cos = dot/(len1*len2);
	if(cos>1.) cos=1; else if(cos<-1.) cos=-1; // in case of rounding error
	double angle = Math.acos(cos);
	if(cross<0) return -angle; // negative
	return angle;
    }
    public double angle(IVec2I v){ return angle(v.get()); }
    //public IDouble angleR(IVec2 v){ return new IDouble(angle(v)); }
    //public IDouble angleR(IVec2I v){ return angleR(v.get()); }
    public double angle(ISwitchE e, IVec2I v){ return angle(v); }
    //public IDouble angleR(IVec2 v){ return new IDouble(angle(v)); }
    public IDouble angle(ISwitchR r, IVec2I v){ return new IDouble(angle(v)); }
    
    
    public IVec2 rot(double angle){
	double origx = x;
	x = x*Math.cos(angle) - y*Math.sin(angle);
	y = origx*Math.sin(angle) + y*Math.cos(angle);
	return this;
    }
    //public IVec2 rot(IDouble angle){ return rot(angle.x); }
    public IVec2 rot(IDoubleI angle){ return rot(angle.x()); }
    
    public IVec2 rot(IVec2 center, double angle){
	if(center==this) return this;
	return sub(center).rot(angle).add(center);
    }
    public IVec2 rot(IVec2I center, double angle){ return rot(center.get(),angle); }
    public IVec2 rot(IVec2I center, IDoubleI angle){ return rot(center.get(),angle.x()); }
    
    // to be tested !!! (direction of rotation)
    public IVec2 rot(IVec2 destDir){ return rot(angle(destDir)); }
    public IVec2 rot(IVec2I destDir){ return rot(destDir.get()); }
    public IVec2 rot(IVec2 center, IVec2 destPt){
	if(center==this) return this;
	return sub(center).rot(destPt.diff(center)).add(center);
    }
    public IVec2 rot(IVec2I center, IVec2I destPt){ return rot(center.get(),destPt.get()); }
    
    
    public IVec2 scale(double f){ return mul(f); }
    public IVec2 scale(IDoubleI f){ return mul(f); }
    public IVec2 scale(IVec2 center, double f){
	if(center==this) return this;
	return sub(center).mul(f).add(center);
    }
    public IVec2 scale(IVec2I center, double f){ return scale(center.get(),f); }
    public IVec2 scale(IVec2I center, IDoubleI f){ return scale(center.get(),f.x()); }
    
    /**
       mirror/reflect/flip 3 dimensionally to the other side of the plane
       @param planeDir direction perpendicular to mirroring line
    */
    public IVec2 mirror(IVec2 planeDir){
	//planeDir = planeDir.dup().unit();
	//return add(planeDir.mul(dot(planeDir)*-2));
	return add(planeDir.dup().mul(dot(planeDir)/planeDir.len2()*-2));
    }
    public IVec2 mirror(IVec2I planeDir){ return mirror(planeDir.get()); }
    public IVec2 mirror(IVec2 center, IVec2 planeDir){
	if(center==this) return this;
	return sub(center).mirror(planeDir).add(center);
    }
    public IVec2 mirror(IVec2I center, IVec2I planeDir){ return mirror(center.get(),planeDir.get()); }
    
    
    //public IVec2I transform(IMatrix2I mat);
    //public IVec2I transform(IMatrix3I mat);
    public IVec2 transform(IVec2 xvec, IVec2 yvec){
	double tx,ty;
	tx = xvec.x*x + yvec.x*y;
	ty = xvec.y*x + yvec.y*y;
	x = tx; y = ty;
	return this;
    }
    public IVec2 transform(IVec2I xvec, IVec2I yvec){ return transform(xvec.get(),yvec.get()); }
    public IVec2 transform(IVec2 xvec, IVec2 yvec, IVec2 translate){
	return transform(xvec,yvec).add(translate);
    }
    public IVec2 transform(IVec2I xvec, IVec2I yvec, IVec2I translate){
	return transform(xvec.get(),yvec.get(),translate.get());
    }
    
    
    // methods creating new instance
    public IVec2 diff(IVec2I v){ return dup().sub(v); }
    public IVec2 mid(IVec2I v){ return dup().add(v).div(2); }
    public IVec2 sum(IVec2I v){ return dup().add(v); }
    public IVec2 sum(IVec2I... v){
        IVec2 ret = this.dup();
        for(IVec2I vi: v) ret.add(vi);
        return ret;
    }
    
    public IVec2 bisect(IVec2 v){ return dup().unit().add(v.dup().unit()); }
    public IVec2 bisect(IVec2I v){ return bisect(v.get()); }
    
    /**
       weighted sum
    */
    public IVec2 sum(IVec2 v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    public IVec2 sum(IVec2I v2, double w1, double w2){ return sum(v2.get(),w1,w2); }
    public IVec2 sum(IVec2I v2, IDoubleI w1, IDoubleI w2){ return sum(v2.get(),w1.x(),w2.x()); }
    public IVec2 sum(IVec2 v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    public IVec2 sum(IVec2I v2, double w2){ return sum(v2.get(),w2); }
    public IVec2 sum(IVec2I v2, IDoubleI w2){ return sum(v2.get(),w2.get()); }
    
    
    public boolean isParallel(IVec2I v){ return isParallel(v, IConfig.angleResolution); }
    public boolean isParallel(IVec2I v, double angleReso){
	//return dup().normalize().diff(v.get().dup().normalize()).len() < IConfig.lengthResolution;
	//return dot(v.get())/(len()*v.get().len()) > Math.cos(IConfig.angleResolution);
	// opposite directions is regarded as parallel too
	return Math.abs(dot(v.get())/(len()*v.get().len())) > Math.cos(angleReso);
	
    }
    
    public boolean isStraight(IVec2I v1, IVec2I v2){
	//return this.diff(v1).isParallel(this.diff(v2));
	//return v1.get().diff(this).isParallel(v2.get().diff(v1));
	return isStraight(v1,v2,IConfig.angleResolution);
    }
    public boolean isStraight(IVec2I v1, IVec2I v2, double angleReso){
	return v1.get().diff(this).isParallel(v2.get().diff(v1),angleReso);
    }
    
    //public boolean isSameX(IVec2I v){ return Math.abs(x-v.x())<IConfig.lengthResolution; }
    //public boolean isSameY(IVec2I v){ return Math.abs(y-v.y())<IConfig.lengthResolution; }
    
    //
    public static IVec2 intersect(IVec2I pt1, IVec2I dir1,
				  IVec2I pt2, IVec2I dir2){
	double det = dir2.x()*dir1.y() - dir1.x()*dir2.y();
	if(det==0) return null; // parallel
	IVec2 pt = new IVec2(pt1.x()*dir1.y()*dir2.x() -
			       pt2.x()*dir1.x()*dir2.y() -
			       (pt1.y()-pt2.y())*dir1.x()*dir2.x(),
			       pt2.y()*dir1.y()*dir2.x() -
			       pt1.y()*dir1.x()*dir2.y() +
			       (pt1.x()-pt2.x())*dir1.y()*dir2.y());
	pt.div(det);
	return pt;
    }
    
    
    public static IVec2 intersectYLine(IVec2 linePt, IVec2 lineDir, double xOfYLine){
        if(lineDir.x==0) return null; // parallel
        return new IVec2( xOfYLine, linePt.y - (linePt.x-xOfYLine)*lineDir.y/lineDir.x);
    }
    
    public static IVec2 intersectXLine(IVec2 linePt, IVec2 lineDir, double yOfXLine){
	if(lineDir.y==0) return null; // parallel
	return new IVec2(linePt.x  - (linePt.y-yOfXLine)*lineDir.x/lineDir.y, yOfXLine );
    }
    
    
    public static IVec2 intersectSegment(IVec2I line1pt1, IVec2I line1pt2,
					  IVec2I line2pt1, IVec2I line2pt2){
	if(Math.max(line1pt1.x(),line1pt2.x()) < Math.min(line2pt1.x(),line2pt2.x()) ||
	   Math.max(line2pt1.x(),line2pt2.x()) < Math.min(line1pt1.x(),line1pt2.x()) ||
	   Math.max(line1pt1.y(),line1pt2.y()) < Math.min(line2pt1.y(),line2pt2.y()) ||
	   Math.max(line2pt1.y(),line2pt2.y()) < Math.min(line1pt1.y(),line1pt2.y())){
	    return null;
	}
	
	IVec2 isct = intersect(line1pt1, line1pt2.get().diff(line1pt1),
				line2pt1, line2pt2.get().diff(line2pt1));

	if(isct==null) return null;

	if(!isct.isBetween(line1pt1,line1pt2) ||
	   !isct.isBetween(line2pt1,line2pt2) ) return null;
	
	return isct;
    }

    public static IVec2 intersectPolyline(IVec2I p1, IVec2I p2, IVec2I[] pts, boolean closed){
	for(int i=0; i<pts.length-1 || closed&&(i<pts.length); i++){
	    IVec2 isct = intersectSegment(p1,p2,pts[i],pts[(i+1)%pts.length]);
	    if(isct!=null) return isct;
	}
	return null;
    }
    
    public static IVec2 intersectPolyline(IVec2I p1, IVec2I p2, IVec2I[] pts){
	return intersectPolyline(p1,p2,pts,false);
    }
    
    public static IVec2 intersectPolygon(IVec2I p1, IVec2I p2, IVec2I[] pts){
	return intersectPolyline(p1,p2,pts,true);
    }
    
    
    public static IVec2 intersectSegmentAndXLine(IVec2I linePt1, IVec2I linePt2,
						  double y){
	if(Math.max(linePt1.y(),linePt2.y()) < y ||
	   Math.min(linePt1.y(),linePt2.y()) > y ) return null;
	IVec2 diff = linePt2.get().diff(linePt1);
	if(diff.y==0) return linePt1.get().dup();
	diff.mul((y-linePt1.y())/diff.y);
	diff.add(linePt1);
	return diff;
    }
    
    public static IVec2 intersectSegmentAndYLine(IVec2I linePt1, IVec2I linePt2,
						  double x){
	if(Math.max(linePt1.x(),linePt2.x()) < x ||
	   Math.min(linePt1.x(),linePt2.x()) > x ) return null;
	IVec2 diff = linePt2.get().diff(linePt1);
	if(diff.x==0) return linePt1.get().dup();
	diff.mul((x-linePt1.x())/diff.x);
	diff.add(linePt1);
	return diff;
    }
    
    
    /**
       remove points which are on straight line of adjacents
    */
    public static IVec2I[] removeStraightPoints(IVec2I[] pts, boolean closed){
	IOut.p();
	// remove redundant point on straight
	int num = pts.length;
	if(num<=2) return pts;
	ArrayList<IVec2I> pts2 = new ArrayList<IVec2I>();
	pts2.add(pts[0]);
	for(int i=1; !closed&&i<num-1 || closed&&i<num; i++)
	    if(!pts2.get(pts2.size()-1).get().isStraight(pts[i], pts[(i+1)%num])) pts2.add(pts[i]);
	if(closed && pts2.get(pts2.size()-1).get().isStraight(pts2.get(0), pts2.get(1)))
	    pts2.remove(0);
	if(pts2.size()==num) return pts;
	IVec2I[] retval = new IVec2I[pts2.size()];
	for(int i=0; i<pts2.size(); i++) retval[i] = pts2.get(i);
	return retval;
    }
    
    
    public static IVec2I[] removeDuplicatedPoints(IVec2I[] pts, boolean closed){
	int num = pts.length;
	ArrayList<IVec2I> pts2 = new ArrayList<IVec2I>();
	pts2.add(pts[0]);
	for(int i=1; i<num; i++){
	    if(!pts2.get(pts2.size()-1).get().eq(pts[i])){
		if(closed && i==num-1){
		    if(!pts2.get(0).get().eq(pts[i])) pts2.add(pts[i]);
		}
		else pts2.add(pts[i]);
	    }
	}
	return pts2.toArray(new IVec2I[pts2.size()]);
    }
    
    
    /**
       determine if the point is insde the polygon defined by the argument
       
    */
    public boolean isInside(IVec2I[] pts){
	
	double angle=0;
	int n = pts.length;
	for(int i=0; i<n; i++){
	    IVec2 v1 = pts[i].get().diff(this);
	    IVec2 v2 = pts[(i+1)%n].get().diff(this);
	    angle += v1.angle(v2);
	}
	
	final double minAngle = 0.1;
	if(Math.abs(angle)<minAngle) return true;
	return false;
    }
    
    
    /**
       determine if the point is insde the polygon defined by the argument
       true if the point is on the edge of polygon
    */
    public boolean isInside(IVec2[] pts){
	
	//IOut.p("this="+this);
	//for(int i=0;i<pts.length; i++) IOut.p("pts["+i+"]="+pts[i]);
	
	
	double angle=0;
	int n = pts.length;
	for(int i=0; i<n; i++){
	    IVec2 v1 = pts[i].diff(this);
	    IVec2 v2 = pts[(i+1)%n].diff(this);
	    
	    double a = v1.angle(v2);
	    if( Math.abs(a-Math.PI) < IConfig.angleResolution ||
		Math.abs(a+Math.PI) < IConfig.angleResolution ||
		v1.len()< IConfig.lengthResolution ||
		v2.len()< IConfig.lengthResolution ){
		// point is on the edge
		//IOut.p("true (on the edge)"); //
		
		return true;
	    }
	    angle += a;
	}
	
	//IOut.p("angle = "+angle); //
	
	int idx = (int)(Math.abs(angle/(2*Math.PI)) + 0.5);
	
	if( idx%2==0 ){
	    //IOut.p("false");
	    //IOut.p("this="+this);
	    //for(int i=0;i<pts.length; i++) IOut.p("pts["+i+"]="+pts[i]);
	    //IOut.p("angle = "+angle); //
	    
	    return false; // outside
	}
	
	//IOut.p("true");
	return true; // inside
	
    }
    
    public String toString(){
	return "("+String.valueOf(x)+","+String.valueOf(y)+")";
    }
    
    
    public double distToLine(IVec2 pt1, IVec2 pt2){
	double xdiff = pt2.x-pt1.x;
	double ydiff = pt2.y-pt1.y;
	return Math.abs(ydiff*this.x +- xdiff*this.y - pt1.x*pt2.y + pt2.x*pt1.y)/
	    Math.sqrt(ydiff*ydiff + xdiff*xdiff);
    }
    
    public boolean isOnLine(IVec2 pt1, IVec2 pt2){
	return distToLine(pt1,pt2)<IConfig.lengthResolution;
    }
    
    public boolean isBetween(IVec2I pt1, IVec2I pt2){
	IVec2 diff1 = diff(pt1);
	IVec2 diff2 = pt2.get().diff(pt1);
	double ip = diff1.dot(diff2);
	if(ip<0) return false;
	if(ip>diff2.len2()) return false;
	return true;
    }
    
}
