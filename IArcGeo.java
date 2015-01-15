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

/**
   Geometry class of an arc.
   Implemented as a type of NURBS curve.
   
   @author Satoru Sugihara
*/
public class IArcGeo extends ICurveGeo{
    public static double maxSegmentAngle = Math.PI/8; //Math.PI/2;
    
    public static int arcDeg(){ return 2; }
    
    public static IVec4[] arcCP(IVec center, IVec normal, IVec startPt, double angle){
	if(angle<0){
	    normal = normal.dup().neg();
	    angle = -angle;
	}
	int segmentNum = (int)(angle/maxSegmentAngle) + 1;
	IVec4[] cpts = new IVec4[segmentNum*2+1];
	IVec radiusVec = startPt.diff(center);
	IVec bisectorVec = radiusVec.dup();
	double radius = radiusVec.len();
        double segmentAngle = angle/segmentNum;
        double bisectorAngle = segmentAngle/2;
        double bisectorLen = radius/Math.cos(bisectorAngle);
        bisectorVec.len(bisectorLen);
        bisectorVec.rot(normal, bisectorAngle);
        for(int i=0; i<cpts.length; i++){
            cpts[i] = center.to4d();
            if(i%2==0){
                cpts[i].add(radiusVec);
                radiusVec.rot(normal, segmentAngle);
            }
            else{
                cpts[i].add(bisectorVec);
                cpts[i].w = Math.cos(bisectorAngle);
                bisectorVec.rot(normal, segmentAngle);
            }
        }
        return cpts;	
    }
    
    
    public static IVec4[] arcCP(IVec center, IVec startPt, IVec endPt, boolean flipArcSide){
	IVec dir1 = startPt.diff(center);
        IVec dir2 = endPt.diff(center);
        IVec normal = null;
        if(dir1.isParallel(dir2)) normal = dir2.cross(dir1.cross(IVec.zaxis));
	else normal = dir2.cross(dir1); // not dir1.dup().cross(dir2); ?
        double angle = dir1.angle(dir2);
        if(angle == 0 || angle == Math.PI){
            if(dir1.x !=0 || dir1.y !=0) normal = dir1.cross(IVec.zaxis);
            else normal = dir1.cross(IVec.xaxis);
        }
	if(flipArcSide){
            angle = Math.PI*2 - angle;
            normal.neg();
        }
	return arcCP(center, normal, startPt, angle);
    }
    
    public static IVec4[] arcCP(IVec center, IVec startPt, IVec midPt, IVec endPt, IVec normal){
        IVec dir1 = startPt.diff(center);
        IVec dir2 = endPt.diff(center);
        IVec mdir = midPt.diff(center);
        double angle = dir1.angle(dir2,normal); 
        double mangle = dir1.angle(mdir,normal);
        
        if( angle>0 && mangle<0 ) angle = -(Math.PI*2 - angle);
        else if( angle<0 && mangle>0) angle = Math.PI*2 + angle;
	
        if(angle<0){ normal.neg(); angle = -angle; }
        normal.neg(); // why?
        return arcCP(center, normal, startPt, angle);
    }    
    
    
    public static double[] arcKnots(double angle){
        if(angle < 0) angle = -angle;
        // angle cannot be negative
        int segmentNum= (int)(angle/(maxSegmentAngle)) + 1;
        double segmentAngle = angle/segmentNum;
        double[] knots = new double[(segmentNum+1)*2+2];
        int k=0;
        double a=0.;
        knots[k]=a; k++;
        knots[k]=a; k++;
        knots[k]=a; k++;
        for(; k<knots.length-1; k++){
            a+=segmentAngle;
            knots[k] = a; k++;
            knots[k] = a; 
        }
        knots[k] = a;
	for(k=0; k<knots.length; k++) knots[k] /= a; // normalize
        return knots;
    }


    public IVecI center;
    public IVecI normal;
    public IVecI startPt;
    public IDoubleI angle;
    
    public IArcGeo(){}
    
    public IArcGeo(IVecI center, IVecI normal, IVecI startPt, double angle){
	init(center,normal,startPt,angle);
    }
    public IArcGeo(IVecI center, IVecI normal, IVecI startPt, IDoubleI angle){
	init(center,normal,startPt,angle);
    }
    public IArcGeo(IVecI center, IVecI startPt, IVecI endPt, IBoolI flipArcSide){
	init(center,startPt,endPt,flipArcSide);
    }
    public IArcGeo(IVecI center, IVecI startPt, IVecI endPt, boolean flipArcSide){
	init(center,startPt,endPt,flipArcSide);
    }
    public IArcGeo(IVecI center, IVecI startPt, IVecI midPt, IVecI endPt, IVecI normal){
	init(center,startPt,midPt,endPt,normal);
    }
    public IArcGeo(IVecI line1Pt1, IVecI line1Pt2, IVecI line2Pt1, IVecI line2Pt2, IDoubleI radius, IBoolI flipArcSide){
	init(line1Pt1,line1Pt2,line2Pt1,line2Pt2,radius,flipArcSide);
    }
    public IArcGeo(IVecI line1Pt1, IVecI line1Pt2, IVecI line2Pt1, IVecI line2Pt2, double radius, boolean flipArcSide){
	init(line1Pt1,line1Pt2,line2Pt1,line2Pt2,radius,flipArcSide);
    }
    public IArcGeo(IVecI sharedLinePt, IVecI line1Pt, IVecI line2Pt, IDoubleI radius, IBoolI flipArcSide){
	init(sharedLinePt,line1Pt,line2Pt,radius,flipArcSide);
    }
    public IArcGeo(IVecI sharedLinePt, IVecI line1Pt, IVecI line2Pt, double radius, boolean flipArcSide){
	init(sharedLinePt,line1Pt,line2Pt,radius,flipArcSide);
    }
    
    public void init(IVecI center, IVecI normal, IVecI startPt, IDoubleI angle){
	this.center = center;
	this.normal = normal;
	this.startPt = startPt;
	this.angle = angle;
	IVec4[] cpts = arcCP(center.get(),normal.get(),startPt.get(),angle.x());
	super.init(cpts,arcDeg(),arcKnots(angle.x()));
    }
    
    public void init(IVecI center, IVecI normal, IVecI startPt, double angle){
	this.center = center;
	this.normal = normal;
	this.startPt = startPt;
	this.angle = new IDouble(angle);
	IVec4[] cpts = arcCP(center.get(),normal.get(),startPt.get(),angle);
	super.init(cpts,arcDeg(),arcKnots(angle));
    }
    
    public void init(IVecI center, IVecI startPt, IVecI endPt, IBoolI flipArcSide){
	init(center,startPt, endPt, flipArcSide.x());
    }
    
    public void init(IVecI center, IVecI startPt, IVecI endPt, boolean flipArcSide){
	IVec dir1 = startPt.get().diff(center);
        IVec dir2 = endPt.get().diff(center);
        if(dir1.isParallel(dir2)) normal = dir2.cross(dir1.cross(IVec.zaxis));
	else normal = dir2.cross(dir1); // not dir1.cross(dir2); ?
        double a = dir1.angle(dir2, normal);
        if(a == 0 || a == Math.PI){
            if(dir1.x !=0 || dir1.y !=0) normal = dir1.cross(IVec.zaxis);
            else normal = dir1.cross(IVec.xaxis);
        }
	if(flipArcSide){
	    if(a>=0){ // added 20141129
		a = Math.PI*2 - a;
		normal.neg();
	    }
	    else{
		a += Math.PI*2;
		//normal.neg();
	    }
        }
	init(center,normal,startPt,new IDouble(a));
    }
    
    public void init(IVecI center, IVecI startPt, IVecI midPt, IVecI endPt, IVecI normal){
	IVec dir1 = startPt.get().diff(center);
        IVec dir2 = endPt.get().diff(center);
        IVec mdir = midPt.get().diff(center);
        double ang = dir1.angle(dir2,normal); 
        double mangle = dir1.angle(mdir,normal);
        if( ang>0 && mangle<0 ) ang = -(Math.PI*2 - ang);
        else if( ang<0 && mangle>0) ang = Math.PI*2 + ang;
        if(ang<0){ normal.neg(); ang = -ang; }
        normal.neg(); // why?
        init(center,normal,startPt,new IDouble(ang));
    }
    
    
    public void init(IVecI line1Pt1, IVecI line1Pt2, IVecI line2Pt1, IVecI line2Pt2, IDoubleI radius, IBoolI flipArcSide){
	init(line1Pt1, line1Pt2, line2Pt1, line2Pt2, radius.x(), flipArcSide.x());
    }
    
    public void init(IVecI line1Pt1, IVecI line1Pt2, IVecI line2Pt1, IVecI line2Pt2, double radius, boolean flipArcSide){
	IVec itxn = IVec.intersect(line1Pt1.get(), line1Pt2.get(), line2Pt1.get(), line2Pt2.get());
	IVec dir1 = line1Pt2.get().dif(itxn);
	IVec dir2 = line2Pt2.get().dif(itxn);
	if(dir1.len2()<IConfig.tolerance*IConfig.tolerance){
	    dir1 = line1Pt1.get().dif(itxn);
	}
	if(dir2.len2()<IConfig.tolerance*IConfig.tolerance){
	    dir2 = line2Pt1.get().dif(itxn);
	}
	
	//if(itxn!=null){
	if(itxn!=null && IVec.distBetween2Lines(line1Pt1,line1Pt2,line2Pt1,line2Pt2) < IConfig.tolerance){
	    dir1.len(radius).add(itxn);
	    dir2.len(radius).add(itxn);
	    
	    init(itxn, dir1, dir2, flipArcSide);
	    return;
	}
	
	IVec ldir1 = line1Pt2.get().dif(line1Pt1);
	IVec ldir2 = line2Pt2.get().dif(line2Pt1);
	if(!ldir1.isParallel(ldir2)){
	    IVec[] pts = IVec.closestPointsOn2Lines(ldir1, line1Pt1, ldir2, line2Pt1);
	    if(pts!=null && pts.length>=2){
		IVec center = pts[0].mid(pts[1]);
		
		if(ldir1.dot(dir1)<0){ ldir1.neg(); }
		if(ldir2.dot(dir2)<0){ ldir2.neg(); }
		
		IVec[] itxn1 = IVec.intersectLineAndSphere( ldir1, line1Pt1.get(), center, radius );
		IVec[] itxn2 = IVec.intersectLineAndSphere( ldir2, line2Pt1.get(), center, radius );
		if(itxn1==null || itxn2==null){
		    IOut.err("Arc could not be created for non-intersecting lines. too small radius.");
		    return;
		}
		init(center, itxn1[0], itxn2[0], flipArcSide);
		return;
	    }
	    IOut.err("Arc could not be created.");
	    return;
	}
	IOut.err("arc could not be created for parallel lines.");
    }
    
    
    public void init(IVecI sharedLinePt, IVecI line1Pt, IVecI line2Pt, IDoubleI radius, IBoolI flipArcSide){
	init(sharedLinePt, line1Pt, line2Pt, radius.x(), flipArcSide.x());
    }
    
    public void init(IVecI sharedLinePt, IVecI line1Pt, IVecI line2Pt, double radius, boolean flipArcSide){
	IVec dir1 = line1Pt.get().dif(sharedLinePt);
	IVec dir2 = line2Pt.get().dif(sharedLinePt);

	dir1.len(radius).add(sharedLinePt);
	dir2.len(radius).add(sharedLinePt);
	init(sharedLinePt, dir1, dir2, flipArcSide);
    }    
}
