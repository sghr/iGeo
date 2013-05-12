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
   class with collection of static methods to create various type of curve.
   
   @author Satoru Sugihara
*/
public class ICurveCreator{
    /** state variable of a server to store surfaces created in the methods in this class */
    public static IServerI server = null;
    /** set a server to store surfaces created in the methods in this class */
    public static void server(IServerI s){ server=s; }
    public static IServerI server(){ return server; }
    
    
    public static ICurve curve(IVecI[] cpts, int degree, double[] knots, double ustart, double uend){
	return new ICurve(server,cpts,degree,knots,ustart,uend);
    }
    
    public static ICurve curve(IVecI[] cpts, int degree, double[] knots){
	return new ICurve(server,cpts,degree,knots);
    }
    
    public static ICurve curve(IVecI[] cpts, int degree){
	return new ICurve(server,cpts,degree);
    }
    
    public static ICurve curve(IVecI[] cpts){
	return new ICurve(server,cpts);
    }
    
    public static ICurve curve(IVecI[] cpts, int degree, boolean close){
	return new ICurve(server,cpts,degree,close);
    }
    public static ICurve curve(IVecI[] cpts, boolean close){
	return new ICurve(server,cpts,close);
    }
    public static ICurve curve(IVecI pt1, IVecI pt2){ return new ICurve(server,pt1,pt2); }
    /** this creates a line between a same point */
    public static ICurve curve(IVecI pt){ return new ICurve(server,pt); }
    
    public static ICurve curve(double x1, double y1, double z1, double x2, double y2, double z2){
	return new ICurve(server, x1,y1,z1,x2,y2,z2);
    }
    public static ICurve curve(double[][] xyzValues){ return new ICurve(server, xyzValues); }
    public static ICurve curve(double[][] xyzValues, int degree){
	return new ICurve(server, xyzValues, degree);
    }
    public static ICurve curve(double[][] xyzValues, boolean close){
	return new ICurve(server, xyzValues, close);
    }
    public static ICurve curve(double[][] xyzValues, int degree, boolean close){
	return new ICurve(server, xyzValues, degree, close);
    }
    public static ICurve curve(ICurveI crv){ return new ICurve(server, crv); }
    
    
    public static ICurve rect(IVecI corner, double xwidth, double yheight){
	return rect(corner, new IVec(xwidth,0,0), new IVec(0,yheight,0));
    }
    public static ICurve rect(IVecI corner, IVecI width, IVecI height){
	IVecI[] cpts = new IVecI[5];
	cpts[0] = corner;
	cpts[1] = corner.dup().add(width);
	cpts[2] = corner.dup().add(width).add(height); 
	cpts[3] = corner.dup().add(height);
	cpts[4] = corner.dup();
	return new ICurve(server, cpts);
    }
    
    public static ICurve rect(double x, double y, double z, double xwidth, double yheight){
	return rect(new IVec(x,y,z),xwidth,yheight);
    }
    
    public static ICircle circle(IVecI center, IVecI normal, IDoubleI radius){
	return new ICircle(server, center, normal, radius);
    }
    public static ICircle circle(IVecI center, IVecI normal, double radius){
        return new ICircle(server, center, normal, radius);
    }
    public static ICircle circle(IVecI center, IDoubleI radius){
	return new ICircle(server,center,radius);
    }
    public static ICircle circle(IVecI center, double radius){
        return new ICircle(server,center,radius);
    }
    public static ICircle circle(double x, double y, double z, double radius){
        return new ICircle(server,x,y,z,radius);
    }
    public static ICircle circle(IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius){
        return new ICircle(server,center, normal, xradius, yradius);
    }
    public static ICircle circle(IVecI center, IVecI normal, double xradius, double yradius){
        return new ICircle(server,center, normal, xradius, yradius);
    }
    public static ICircle circle(IVecI center, IDoubleI xradius, IDoubleI yradius){
        return new ICircle(server,center, xradius, yradius);
    }
    public static ICircle circle(IVecI center, double xradius, double yradius){
        return new ICircle(server,center, xradius, yradius);
    }
    public static ICircle circle(double x, double y, double z, double xradius, double yradius){
        return new ICircle(server,x,y,z,xradius,yradius);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, double radius){
        return new ICircle(server,center,normal,rollDir,radius);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius){
        return new ICircle(server, center, normal, rollDir, radius);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius){
        return new ICircle(server, center, normal, rollDir, xradius, yradius);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius){
        return new ICircle(server, center, normal, rollDir, xradius, yradius);
    }
    public static ICircle circle(IVecI center, IVecI xradiusVec, IVecI yradiusVec){
        return new ICircle(server, center, xradiusVec, yradiusVec);
    }
    public static ICircle circle(IVecI center, IVecI normal, IDoubleI radius, boolean approx){
        return new ICircle(server,center,normal,radius,approx);
    }
    public static ICircle circle(IVecI center, IVecI normal, double radius, boolean approx){
        return new ICircle(server,center,normal,radius,approx);
    }
    public static ICircle circle(IVecI center, IDoubleI radius, boolean approx){
        return new ICircle(server,center,radius,approx);
    }
    public static ICircle circle(IVecI center, double radius, boolean approx){
        return new ICircle(server,center,radius,approx);
    }
    public static ICircle circle(double x, double y, double z, double radius, boolean approx){
        return new ICircle(server,x,y,z,radius,approx);
    }
    public static ICircle circle(IVecI center, IVecI normal, double xradius, double yradius, boolean approx){
	return new ICircle(server,center,normal,xradius,yradius,approx);
	
    }
    public static ICircle circle(IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius, boolean approx){
        return new ICircle(server,center,normal,xradius,yradius,approx);
    }
    public static ICircle circle(IVecI center, double xradius, double yradius, boolean approx){
        return new ICircle(server,center,xradius,yradius,approx);
    }
    public static ICircle circle(IVecI center, IDoubleI xradius, IDoubleI yradius, boolean approx){
        return new ICircle(server,center,xradius,yradius,approx);
    }
    public static ICircle circle(double x, double y, double z, double xradius, double yradius, boolean approx){
        return new ICircle(server,x,y,z,xradius,yradius,approx);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, double radius, boolean approx){
        return new ICircle(server,center,normal,rollDir,radius,approx);
    }
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius, boolean approx){
	return new ICircle(server,center,normal,rollDir,radius,approx);
    }
    
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius, boolean approx){
        return new ICircle(server,center,normal,rollDir,xradius,yradius,approx);
    }
    
    public static ICircle circle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius, boolean approx){
        return new ICircle(server,center,normal,rollDir,xradius,yradius,approx);
    }
    
    public static ICircle circle(IVecI center, IVecI xradiusVec, IVecI yradiusVec, boolean approx){
	return new ICircle(server,center,xradiusVec,yradiusVec,approx);
    }
    
    
    public static ICircle ellipse(IVecI center, IVecI xradiusVec, IVecI yradiusVec){
	return new ICircle(server,center,xradiusVec,yradiusVec);
    }
    public static ICircle ellipse(IVecI center, IDoubleI xradius, IDoubleI yradius){
        return new ICircle(server,center, xradius, yradius);
    }
    public static ICircle ellipse(IVecI center, double xradius, double yradius){
        return new ICircle(server,center, xradius, yradius);
    }
    public static ICircle ellipse(double x, double y, double z, double xradius, double yradius){
        return new ICircle(server,x,y,z,xradius,yradius);
    }
    
    
    
    // rect, circle, oval, arc,
    public static IArc arc(IVecI center, IVecI normal, IVecI startPt, double angle){
        return new IArc(server, center, normal, startPt, angle);
    }
    public static IArc arc(IVecI center, IVecI normal, IVecI startPt, IDoubleI angle){
        return new IArc(server, center, normal, startPt, angle);
    }
    public static IArc arc(IVecI center, IVecI startPt, double angle){
        return new IArc(server, center, startPt, angle);
    }
    public static IArc arc(IVecI center, IVecI startPt, IDoubleI angle){
        return new IArc(server, center, startPt, angle);
    }
    public static IArc arc(double x, double y, double z, double startX, double startY, double startZ, double angle){
        return new IArc(server,x,y,z,startX,startY,startZ,angle);
    }
    public static IArc arc(IVecI center, IVecI startPt, IVecI endPt, IBoolI flipArcSide){
        return new IArc(server,center,startPt,endPt,flipArcSide);
    }
    public static IArc arc(IVecI center, IVecI startPt, IVecI endPt, boolean flipArcSide){
        return new IArc(server,center,startPt,endPt,flipArcSide);
    }
    public static IArc arc(IVecI center, IVecI startPt, IVecI midPt, IVecI endPt, IVecI normal){
        return new IArc(server,center,startPt,midPt,endPt,normal);
    }
    
    public static ICurve offset(ICurveI curve, double width, IVecI planeNormal){
	double[] knots = new double[curve.knotNum()];
	for(int i=0; i<knots.length; i++) knots[i] = curve.knot(i);
	if(!curve.isClosed()){
	    return curve(IVec.offset(curve.cps(),width,planeNormal,false),
			 curve.deg(), knots, 0.0, 1.0);
	}
	// closed
	int cpnum = curve.num() - curve.get().overlapCPNum();
	IVecI[] cpts = IVec.offset(curve.cps(), cpnum, curve.num(), width,
				   new IVecI[]{planeNormal}, true);
	for(int i=cpnum; i<cpts.length; i++){ cpts[i] = cpts[i-cpnum].dup(); }
	return curve(cpts, curve.deg(), knots, 0.0, 1.0);
	/*
	IVecI[] cpts = new IVec[curve.num()-curve.deg()+1];
	for(int i=0; i<cpts.length; i++) cpts[i] = curve.cp(i);
	IVecI[] cpts2 = IVec.offset(cpts,width,planeNormal);
	if(curve.deg()==1) return curve(cpts2, curve.deg(), knots, 0.0, 1.0);
	IVecI[] cpts3 = new IVec[curve.num()];
	for(int i=0; i<cpts3.length; i++){
	    if(i<cpts2.length){ cpts3[i] = cpts2[i]; }
	    else{ cpts3[i] = cpts2[i%(cpts2.length-1)].dup(); } // -1 is to skip degree 1 overlapping point
	}
	return curve(cpts3, curve.deg(), knots, 0.0, 1.0);
	*/
    }
    
    public static ICurve offset(ICurveI curve, IDoubleI width, IVecI planeNormal){
	double[] knots = new double[curve.knotNum()];
	for(int i=0; i<knots.length; i++) knots[i] = curve.knot(i);
	if(!curve.isClosed()){
	    return curve(IVec.offset(curve.cps(),width,planeNormal,false),
			 curve.deg(), knots, 0.0, 1.0);
	}
	// closed
	int cpnum = curve.num() - curve.get().overlapCPNum();
	IVecI[] cpts = IVec.offset(curve.cps(), cpnum, curve.num(), width,
				   new IVecI[]{planeNormal}, true);
	for(int i=cpnum; i<cpts.length; i++){ cpts[i] = cpts[i-cpnum].dup(); }
	return curve(cpts, curve.deg(), knots, 0.0, 1.0);
	/*
	IVecI[] cpts = new IVec[curve.num()-curve.deg()+1];
	for(int i=0; i<cpts.length; i++) cpts[i] = curve.cp(i);
	IVecI[] cpts2 = IVec.offset(cpts,width,planeNormal);
	if(curve.deg()==1) return curve(cpts2, curve.deg(), knots, 0.0, 1.0);
	IVecI[] cpts3 = new IVec[curve.num()];
	for(int i=0; i<cpts3.length; i++){
	    if(i<cpts2.length){ cpts3[i] = cpts2[i]; }
	    else{ cpts3[i] = cpts2[i%(cpts2.length-1)].dup(); } // -1 is to skip degree 1 overlapping point
	}
	return curve(cpts3, curve.deg(), knots, 0.0, 1.0);
	*/
    }
    
    public static ICurve offset(ICurveI curve, double width){
	double[] knots = new double[curve.knotNum()];
	for(int i=0; i<knots.length; i++) knots[i] = curve.knot(i);
	if(!curve.isClosed()){
	    return curve(IVec.offset(curve.cps(),width,false),curve.deg(),knots, 0.0, 1.0);
	}
	// closed
	int cpnum = curve.num() - curve.get().overlapCPNum();
	IVecI[] cpts = IVec.offset(curve.cps(), cpnum, curve.num(), width, null, true);
	for(int i=cpnum; i<cpts.length; i++){ cpts[i] = cpts[i-cpnum].dup(); }
	return curve(cpts, curve.deg(), knots, 0.0, 1.0);
	/*
	IVecI[] cpts = new IVec[curve.num()-curve.deg()+1];
	for(int i=0; i<cpts.length; i++) cpts[i] = curve.cp(i);
	IVecI[] cpts2 = IVec.offset(cpts,width);
	if(curve.deg()==1){ return curve(cpts2, curve.deg(), knots, 0.0, 1.0); }
	IVecI[] cpts3 = new IVec[curve.num()];
	for(int i=0; i<cpts3.length; i++){
	    if(i<cpts2.length){ cpts3[i] = cpts2[i]; }
	    else{ cpts3[i] = cpts2[i%(cpts2.length-1)].dup(); } // -1 is to skip degree 1 overlapping point
	}
	return curve(cpts3, curve.deg(), knots, 0.0, 1.0);
	*/
    }
    
    public static ICurve offset(ICurveI curve, IDoubleI width){
	double[] knots = new double[curve.knotNum()];
	for(int i=0; i<knots.length; i++) knots[i] = curve.knot(i);
	if(!curve.isClosed()){
	    return curve(IVec.offset(curve.cps(),width),curve.deg(),knots, 0.0, 1.0);
	}
	// closed
	int cpnum = curve.num() - curve.get().overlapCPNum();
	IVecI[] cpts = IVec.offset(curve.cps(), cpnum, curve.num(), width, null, true);
	for(int i=cpnum; i<cpts.length; i++){ cpts[i] = cpts[i-cpnum].dup(); }
	return curve(cpts, curve.deg(), knots, 0.0, 1.0);
	/*
	IVecI[] cpts = new IVec[curve.num()-curve.deg()+1];
	for(int i=0; i<cpts.length; i++) cpts[i] = curve.cp(i);
	IVecI[] cpts2 = IVec.offset(cpts,width);
	if(curve.deg()==1) return curve(cpts2, curve.deg(), knots, 0.0, 1.0);
	IVecI[] cpts3 = new IVec[curve.num()];
	for(int i=0; i<cpts3.length; i++){
	    if(i<cpts2.length){ cpts3[i] = cpts2[i]; }
	    else{ cpts3[i] = cpts2[i%(cpts2.length-1)].dup(); } // -1 is to skip degree 1 overlapping point
	}
	return curve(cpts3, curve.deg(), knots, 0.0, 1.0);
	*/
    }
    
    public static ICurve flatten(ICurveI curve, IVecI planeDir, IVecI planePt){
	IVecI[] cpts = new IVecI[curve.num()];
	for(int i=0; i<curve.num(); i++){ cpts[i] = curve.cp(i).dup(); }
	IVec.projectToPlane(cpts, planeDir, planePt);
	double[] knots = new double[curve.knotNum()];
	for(int i=0; i<knots.length; i++) knots[i] = curve.knot(i);
	return curve(cpts, curve.deg(), knots, 0.0, 1.0);
    }
    
    public static ICurve flatten(ICurveI curve, IVecI planeDir){
	IVecI[] cpts = new IVecI[curve.num()];
	for(int i=0; i<curve.num(); i++){ cpts[i] = curve.cp(i).dup(); }
	IVec.projectToPlane(cpts, planeDir);
	double[] knots = new double[curve.knotNum()];
	for(int i=0; i<knots.length; i++) knots[i] = curve.knot(i);
	return curve(cpts, curve.deg(), knots, 0.0, 1.0);
    }
    
    public static ICurve flatten(ICurveI curve){
	IVecI[] cpts = new IVecI[curve.num()];
	for(int i=0; i<curve.num(); i++){ cpts[i] = curve.cp(i).dup(); }
	IVec.projectToPlane(cpts);
	double[] knots = new double[curve.knotNum()];
	for(int i=0; i<knots.length; i++) knots[i] = curve.knot(i);
	return curve(cpts, curve.deg(), knots, 0.0, 1.0);
    }
    
}
