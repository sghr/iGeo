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
import igeo.util.*;

/**
   class with collection of static methods to create various type of surface.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ISurfaceCreator{
    /** state variable of a server to store surfaces created in the methods in this class */
    public static IServerI server = null;
    /** set a server to store surfaces created in the methods in this class */
    public static void server(IServerI s){ server=s; }
    public static IServerI server(){ return server; }
    
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree,
				   double[] uknots, double[] vknots,
				   double ustart, double uend, double vstart, double vend){
	return new ISurface(server,cpts,udegree,vdegree,uknots,vknots,ustart,uend,vstart,vend);
    }
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree,
				   double[] uknots, double[] vknots){
	return new ISurface(server,cpts,udegree,vdegree,uknots,vknots);
    }
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree){
        return new ISurface(server,cpts,udegree,vdegree);
    }
    
    public static ISurface surface(IVecI[][] cpts){ return new ISurface(server,cpts); }
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree,
				   boolean closeU, boolean closeV){
	return new ISurface(server,cpts,udegree,vdegree,closeU,closeV);
    }
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree,
				   boolean closeU, double[] vk){
	return new ISurface(server,cpts,udegree,vdegree,closeU,vk);
    }
    
    public static ISurface surface(IVecI[][] cpts, int udegree, int vdegree,
				   double[] uk, boolean closeV){
	return new ISurface(server,cpts,udegree,vdegree,uk,closeV);
    }
    
    public static ISurface surface(IVecI[][] cpts, boolean closeU, boolean closeV){
	return new ISurface(server,cpts,closeU,closeV);
    }
    
    public static ISurface surface(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4){
	return new ISurface(server,pt1,pt2,pt3,pt4);
    }
    
    public static ISurface surface(IVecI pt1, IVecI pt2, IVecI pt3){
	return new ISurface(server,pt1,pt2,pt3);
    }
    
    public static ISurface surface(double x1, double y1, double z1,
				   double x2, double y2, double z2,
				   double x3, double y3, double z3,
				   double x4, double y4, double z4){
	return new ISurface(server,x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4);
    }
    
    public static ISurface surface(double x1, double y1, double z1,
				   double x2, double y2, double z2,
				   double x3, double y3, double z3){
	return new ISurface(server,x1,y1,z1,x2,y2,z2,x3,y3,z3);
    }
    
    public static ISurface surface(double[][][] xyzValues){
	return new ISurface(server, xyzValues);
    }
    
    public static ISurface surface(double[][][] xyzValues, int udeg, int vdeg){
	return new ISurface(server, xyzValues, udeg, vdeg);
    }
    
    public static ISurface surface(double[][][] xyzValues, boolean closeU, boolean closeV){
	return new ISurface(server, xyzValues, closeU, closeV);
    }
    
    public static ISurface surface(double[][][] xyzValues, int udeg, int vdeg, boolean closeU, boolean closeV){
	return new ISurface(server, xyzValues, udeg, vdeg, closeU, closeV);
    }
    
    public static ISurface surface(ISurfaceI srf){ return new ISurface(server, srf); }
    
    // planar surface with trim
    public static ISurface surface(ICurveI trimCurve){ return new ISurface(server, trimCurve); }
    public static ISurface surface(ICurveI[] trimCurves){ return new ISurface(server, trimCurves); }
    public static ISurface surface(IVecI[] trimCrvPts){ return new ISurface(server, trimCrvPts); }
    public static ISurface surface(IVecI[] trimCrvPts, int trimCrvDeg){ return new ISurface(server, trimCrvPts, trimCrvDeg); }
    public static ISurface surface(IVecI[] trimCrvPts, int trimCrvDeg, double[] trimCrvKnots){ return new ISurface(server, trimCrvPts, trimCrvDeg, trimCrvKnots); }
    
    
    public static ISphere sphere(double x, double y, double z, double radius){
	return new ISphere(server, x, y, z, radius);
    }
    
    public static ISphere sphere(IVecI center, double radius){
	return new ISphere(server, center, radius);
    }
	
    public static ISphere sphere(IVecI center, IDoubleI radius){
	return new ISphere(server, center, radius);
    }
    
    public static ICylinder cylinder(IVecI pt1, IVecI pt2, double radius){
	return new ICylinder(server, pt1, pt2, radius);
    }
    
    public static ICylinder cylinder(IVecI pt1, IVecI pt2, IDoubleI radius){
	return new ICylinder(server, pt1, pt2, radius);
    }
    
    public static ICylinder cylinder(IVecI pt1, IVecI pt2, double radius1, double radius2){
	return new ICylinder(server, pt1, pt2, radius1, radius2);
    }
    
    public static ICylinder cylinder(IVecI pt1, IVecI pt2, IDoubleI radius1, IDoubleI radius2){
	return new ICylinder(server, pt1, pt2, radius1, radius2);
    }
    
    public static ICylinder cone(IVecI pt1, IVecI pt2, double radius){
	return new ICylinder(server, pt1, pt2, radius, 0);
    }
    
    public static ICylinder cone(IVecI pt1, IVecI pt2, IDoubleI radius){
	return new ICylinder(server, pt1, pt2, radius, new IDouble(0));
    }
    
    public static ISurface plane(IVecI corner, double xwidth, double yheight){
	IVec w = new IVec(xwidth,0,0);
	IVec h = new IVec(0,yheight,0);
	return new ISurface(server, corner, corner.dup().add(w),
			    corner.dup().add(w).add(h), corner.dup().add(h));
    }
    
    public static ISurface plane(IVecI corner, IVecI widthVec, IVecI heightVec){
	return new ISurface(server, corner, corner.dup().add(widthVec),
			    corner.dup().add(widthVec).add(heightVec),
			    corner.dup().add(heightVec));
    }
    
    
    /** one directional extrusion */
    
    public static ISurface extrude(IVecI[] profile, double extrudeDepth){
	return extrude(profile,1,false,IVec.averageNormal(profile).len(extrudeDepth));
    }
    public static ISurface extrude(IVecI[] profile, IDoubleI extrudeDepth){
	return extrude(profile,1,false,new IVecR(IVec.averageNormal(profile)).len(extrudeDepth));
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, double extrudeDepth){
	return extrude(profile,profileDeg,false,IVec.averageNormal(profile).len(extrudeDepth));
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, IDoubleI extrudeDepth){
	return extrude(profile,profileDeg,false,new IVecR(IVec.averageNormal(profile)).len(extrudeDepth));
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, boolean closeProfile, double extrudeDepth){
	return extrude(profile,profileDeg,closeProfile,IVec.averageNormal(profile).len(extrudeDepth));
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, boolean closeProfile, IDoubleI extrudeDepth){
	return extrude(profile,profileDeg,closeProfile,new IVecR(IVec.averageNormal(profile)).len(extrudeDepth));
    }
    
    public static ISurface extrude(IVecI[] profile, IVecI extrudeDir){
	return extrude(profile,1,false,extrudeDir);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, IVecI extrudeDir){
	return extrude(profile,profileDeg,false,extrudeDir);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, boolean closeProfile, IVecI extrudeDir){
	IVecI[] profPts = null;
	double[] profKnots = null;
	if(closeProfile){
	    profPts = INurbsGeo.createClosedCP(profile, profileDeg);
	    profKnots = INurbsGeo.createClosedKnots(profileDeg, profPts.length);
	}
	else{
	    profPts = profile;
	    profKnots = INurbsGeo.createKnots(profileDeg, profPts.length);
	}
	return extrude(profPts, profileDeg, profKnots, extrudeDir);
	/*
	IVecI[][] cpts = new IVecI[2][profPts.length];
	for(int i=0; i<profPts.length; i++){
	    cpts[0][i] = profPts[i];
	    cpts[1][i] = profPts[i].dup().add(extrudeDir);
	}
	if(closeProfile)
	    return new ISurface(cpts, 1, profileDeg, INurbsGeo.createKnots(1,2), profKnots,
				0.0, 1.0, 0.0, 1.0);
	return new ISurface(cpts, 1, profileDeg);
	*/
    }
    
    
    public static ISurface extrude(IVecI[] profile, int profileDeg, double[] profileKnots,
				   IVecI extrudeDir){
	IVecI[][] cpts = new IVecI[2][profile.length];
	for(int i=0; i<profile.length; i++){
	    cpts[0][i] = profile[i];
	    cpts[1][i] = profile[i].dup().add(extrudeDir);
	}
	return new ISurface(server, cpts, 1, profileDeg, INurbsGeo.createKnots(1,2), profileKnots,
			    0.0, 1.0, 0.0, 1.0);
    }
    
    public static ISurface extrude(IVecI[] profile, ICurve rail){
	return extrude(profile,1,false,rail);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, ICurve rail){
	return extrude(profile,profileDeg,false,rail);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, boolean closeProfile,
				   ICurve rail){
	IVecI[] profPts = null;
	double[] profKnots = null;
	if(closeProfile){
	    profPts = INurbsGeo.createClosedCP(profile, profileDeg);
	    profKnots = INurbsGeo.createClosedKnots(profileDeg, profPts.length);
	}
	else{
	    profPts = profile;
	    profKnots = INurbsGeo.createKnots(profileDeg, profPts.length);
	}
	return extrude(profPts,profileDeg,profKnots,rail);
    }
    
    
    public static ISurface extrude(IVecI[] profile, int profileDeg, double[] profileKnots,
				   ICurve rail){
	return extrude(profile,profileDeg,profileKnots,rail.cps(),rail.deg(),rail.knots());
    }
    

    public static ISurface extrude(ICurveI profile, IVecI extrudeDir){
	IVecI[][] cpts = new IVecI[2][profile.num()];
	for(int i=0; i<profile.num(); i++){
	    cpts[0][i] = profile.cp(i);
	    cpts[1][i] = profile.cp(i).dup().add(extrudeDir);
	}
	return new ISurface(server, cpts, 1, profile.deg(), INurbsGeo.createKnots(1,2), profile.knots(),
			    0., 1., 0., 1.); // to have knots not normalized, it needs to put 0.0 and 1.0
    }
    
    /** extrusion along path (profile control points are copied parallely) */
    public static ISurface extrude(IVecI[] profile, IVecI[] rail){
	return extrude(profile,1,false,rail,1,false);
    }
    public static ISurface extrude(IVecI[] profile, int profileDeg, IVecI[] rail, int railDeg){
	return extrude(profile,profileDeg,false,rail,railDeg,false);
    }
    
    public static ISurface extrude(IVecI[] profile, int profileDeg, boolean closeProfile,
				   IVecI[] rail, int railDeg, boolean closeRail){
	IVecI[] profPts = null;
	double[] profKnots = null;
	if(closeProfile){
	    profPts = INurbsGeo.createClosedCP(profile, profileDeg);
	    profKnots = INurbsGeo.createClosedKnots(profileDeg, profPts.length);
	}
	else{
	    profPts = profile;
	    profKnots = INurbsGeo.createKnots(profileDeg,profPts.length);
	}
	
	IVecI[] railPts = null;
	double[] railKnots = null;
	if(closeRail){
	    railPts = INurbsGeo.createClosedCP(rail, railDeg);
	    railKnots = INurbsGeo.createClosedKnots(railDeg, railPts.length);
	}
	else{
	    railPts = rail;
	    railKnots = INurbsGeo.createKnots(railDeg,railPts.length);
	}
	return extrude(profPts, profileDeg, profKnots, railPts, railDeg, railKnots);
	
	/*
	IVecI[][] cpts = new IVec[railPts.length][profPts.length];
	for(int i=0; i<railPts.length; i++){
	    for(int j=0; j<profPts.length; j++){
		cpts[i][j] = railPts[i].dup().add(profPts[j]).sub(profOrig);
	    }
	}
	if(closeProfile || closeRail){
	    if(profKnots==null) profKnots = INurbsGeo.createKnots(profileDeg,profPts.length);
	    if(railKnots==null) railKnots = INurbsGeo.createKnots(railDeg,railPts.length);
	    return new ISurface(cpts,railDeg,profileDeg,railKnots,profKnots,0.0,1.0,0.0,1.0);
	}
	return new ISurface(server, cpts,railDeg,profileDeg);
	*/
    }
    
    public static ISurface extrude(IVecI[] profile, int profileDeg, double[] profileKnots,
				   IVecI[] rail, int railDeg, double[] railKnots){
	IVecI profOrig = profile[0];
	IVecI[][] cpts = new IVec[rail.length][profile.length];
	for(int i=0; i<rail.length; i++){
	    for(int j=0; j<profile.length; j++){
		cpts[i][j] = rail[i].dup().add(profile[j]).sub(profOrig);
	    }
	}
	return new ISurface(server,cpts,railDeg,profileDeg,railKnots,profileKnots,0.0,1.0,0.0,1.0);
	// to have knots not normalized, it needs to put 0.0 and 1.0
    }
    
    
    public static ISurface extrude(ICurveI profile, ICurveI rail){
	return extrude(profile.cps(), profile.deg(), profile.knots(),
		       rail.cps(), rail.deg(), rail.knots());
	/*
	IVecI profOrig = profile.cp(0);
	IVecI[][] cpts = new IVecI[rail.num()][profile.num()];
	for(int i=0; i<rail.num(); i++){
	    for(int j=0; j<profile.num(); j++){
		cpts[i][j] = rail.cp(i).dup().add(profile.cp(j)).sub(profOrig);
	    }
	}
	return new ISurface(cpts, rail.deg(), profile.deg(),rail.knots(),profile.knots(),
	0., 1., 0., 1.); // to have knots not normalized, it needs to put 0.0 and 1.0
	*/
    }
    
    
    /** sweep (profile is redirected perpendicular to rail and centered(actually just on bisector of control points)) */
    public static ISurface sweep(IVecI[] profile, IVecI[] rail){
	return sweep(profile,1,false,null,rail,1,false);
    }
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, IVecI[] rail){
	return sweep(profile,1,false,profileCenter,rail,1,false);
    }
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI[] rail, int railDeg){
	return sweep(profile,profileDeg,false,null,rail,railDeg,false);
    }
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI profileCenter,
				 IVecI[] rail, int railDeg){
	return sweep(profile,profileDeg,false,profileCenter,rail,railDeg,false);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI[] rail, int railDeg, boolean closeRail){
	return sweep(profile,profileDeg, closeProfile, null,  rail, railDeg, closeRail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, 
				 IVecI[] rail, int railDeg, boolean closeRail){
	IVecI[] profPts = null;
	double[] profKnots = null;
	if(closeProfile){
	    profPts = INurbsGeo.createClosedCP(profile, profileDeg);
	    profKnots = INurbsGeo.createClosedKnots(profileDeg, profPts.length);
	}
	else{
	    profPts = profile;
	    profKnots = INurbsGeo.createKnots(profileDeg,profPts.length);
	}
	
	IVecI[] railPts = null;
	double[] railKnots = null;
	if(closeRail){
	    railPts = INurbsGeo.createClosedCP(rail, railDeg);
	    railKnots = INurbsGeo.createClosedKnots(railDeg, railPts.length);
	}
	else{
	    railPts = rail;
	    railKnots = INurbsGeo.createKnots(railDeg,railPts.length);
	}
	return sweep(profPts, profileDeg, profKnots, profileCenter, railPts, railDeg, railKnots);
    }

    public static ISurface sweep(IVecI[] profile, ICurveI rail){
	return sweep(profile, 1, null, null, rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, ICurveI rail){
	return sweep(profile, 1, null, profileCenter, rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, ICurveI rail){
	return sweep(profile, profileDeg, null, null, rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI profileCenter,
				 ICurveI rail){
	return sweep(profile, profileDeg, null, profileCenter, rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, ICurveI rail){
	IVecI[] profPts = null;
	double[] profKnots = null;
	if(closeProfile){
	    profPts = INurbsGeo.createClosedCP(profile, profileDeg);
	    profKnots = INurbsGeo.createClosedKnots(profileDeg, profPts.length);
	}
	else{
	    profPts = profile;
	    profKnots = INurbsGeo.createKnots(profileDeg,profPts.length);
	}
	return sweep(profPts, profileDeg, profKnots, profileCenter, rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile, ICurveI rail){
	return sweep(profile,profileDeg,closeProfile,null,rail);
    }    
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI[] rail){
	return sweep(profile.cps(), profile.deg(), profile.knots(), profileCenter,
		     rail, 1, null);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI[] rail){
	return sweep(profile.cps(), profile.deg(), profile.knots(), null,
		     rail, 1, null);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI[] rail,int railDeg){
	return sweep(profile.cps(), profile.deg(), profile.knots(), profileCenter,
		     rail, railDeg, null);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI[] rail, int railDeg){
	return sweep(profile.cps(), profile.deg(), profile.knots(), null,
		     rail, railDeg, null);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI[] rail,
				 int railDeg, boolean closeRail){
	IVecI[] railPts = null;
	double[] railKnots = null;
	if(closeRail){
	    railPts = INurbsGeo.createClosedCP(rail, railDeg);
	    railKnots = INurbsGeo.createClosedKnots(railDeg, railPts.length);
	}
	else{
	    railPts = rail;
	    railKnots = INurbsGeo.createKnots(railDeg,railPts.length);
	}
	return sweep(profile.cps(), profile.deg(), profile.knots(), profileCenter,
		     railPts, railDeg, railKnots);
    }
        
    public static ISurface sweep(ICurveI profile, IVecI[] rail, int railDeg, boolean closeRail){
	return sweep(profile, null, rail, railDeg, closeRail);
    }
    
    
    public static ISurface sweep(ICurveI profile, ICurveI rail){
	return sweep(profile.cps(), profile.deg(), profile.knots(),
		     rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, ICurveI rail){
	return sweep(profile.cps(), profile.deg(), profile.knots(), profileCenter,
		     rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, double[] profileKnots,
				 IVecI[] rail, int railDeg, double[] railKnots){
	return sweep(profile, profileDeg, profileKnots, null, rail,railDeg,railKnots);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, double[] profileKnots,
				 IVecI profileCenter,
				 IVecI[] rail, int railDeg, double[] railKnots){
	
	if(profile==null || profile.length<=1){
	    IOut.err("profile is null or number of array is too less");
	    return null;
	}
	if(rail==null || rail.length<=1){
	    IOut.err("rail is null or number of array is too less");
	    return null;
	}
	
	if(profileKnots==null) profileKnots = INurbsGeo.createKnots(profileDeg,profile.length);
	if(railKnots==null) railKnots = INurbsGeo.createKnots(railDeg,rail.length);
	
	IVec n = IVec.averageNormal(profile);
	if(profileCenter==null) profileCenter = getCenter(profile,profileDeg);
	else{ profileCenter = profileCenter.dup(); } // profileCenter changes
	
	boolean railClosed=isClosed(rail,railDeg);
	
	IVecI[] ppts = duplicatePoints(profile);
	IVecI[][] cpts = new IVecI[rail.length][];
	
	for(int i=0; i<rail.length; i++){
	    IVecI dir = null;
	    if(i==0){ // start point
		if(!railClosed && i<rail.length-1) dir = rail[i+1].diff(rail[i]);
		else if(railClosed && rail.length-railDeg>0){
		    dir = rail[rail.length-railDeg].diff(rail[rail.length-railDeg-1]);
		}
		
		IVecI[] ppts2 = orient(ppts, profileCenter, n, rail[i], dir);
		if(railClosed){
		    if(i<rail.length-1){ dir = rail[i+1].diff(rail[i]); }
		    ppts2 = orientAndBisect(ppts, profileCenter, n, rail[i], dir);
		}
		//new ICurve(ppts2); //
		cpts[i] = ppts2;
	    }
	    else if(railClosed && i >= rail.length-railDeg){
		cpts[i] = duplicatePoints(cpts[i - (rail.length-railDeg)]);
		//new ICurve(cpts[i]).clr(1.0,0,0); //
	    }
	    else{
		if(i<rail.length-1) dir = rail[i+1].diff(rail[i]);
		else dir = rail[i].diff(rail[i-1]);
		
		IVecI[] bsct = orientAndBisect(ppts, profileCenter, n, rail[i], dir);
		//new ICurve(bsct); //
		cpts[i] = bsct;
	    }
	    
	}
	
	return new ISurface(server,cpts, railDeg, profileDeg, railKnots, profileKnots,
			    0.0, 1.0, 0.0, 1.0);
    }
    
    /****************
     * subroutines for sweep
     ****************/
    
    public static IVecI[] duplicatePoints(IVecI[] pts){
	IVecI[] pts2 = new IVecI[pts.length];
	for(int i=0; i<pts.length; i++){ pts2[i] = pts[i].dup(); }
	return pts2;
    }
    
    public static IVecI[] orient(IVecI[] pts, IVecI center, IVec n, IVecI location, IVecI dir){
	IVec axis = null;
	double angle = 0;
	boolean parallel = true;
	if(dir!=null){
	    axis = n.cross(dir);
	    angle = n.angle(dir,axis);
	    parallel = angle < IConfig.angleResolution;
	}
	
	for(IVecI p:pts){
	    p.sub(center);
	    if(!parallel) p.rot(axis,angle);
	    p.add(location);
	}
	
	if(!parallel) n.rot(axis,angle);
	center.set(location);
	
	return duplicatePoints(pts);
    }
    
    public static IVecI[] orientAndBisect(IVecI[] pts, IVecI center, IVec n, IVecI location, IVecI dir){
	IVec axis = null;
	double angle = 0;
	boolean parallel = true;
	if(dir!=null){
	    axis = n.cross(dir);
	    angle = n.angle(dir,axis);
	    parallel = angle < IConfig.angleResolution;
	}
	
	IVecI[] bsct = duplicatePoints(pts);
	double bangle = angle/2;
	
	for(IVecI p:pts){
	    p.sub(center);
	    if(!parallel) p.rot(axis,angle);
	    p.add(location);
	}
	
	IVec bdir = n.cross(axis);
	if(!parallel) bdir.rot(axis,bangle);
	
	for(IVecI b:bsct){
	    b.sub(center);
	    if(!parallel){
		b.rot(axis,bangle);
		// scale 1d
		if(Math.abs(bangle-Math.PI/2)>=IConfig.angleResolution){
		    double scale = 1.0/Math.abs(Math.cos(bangle));
		    b.scale1d(bdir,scale);
		}
	    }
	    b.add(location);
	}
	
	if(!parallel) n.rot(axis,angle);
	center.set(location);
	
	return bsct;
    }
    
    public static boolean isClosed(IVecI[] profile, int profileDeg){
	boolean closed=true;
	for(int i=0; i<profileDeg && closed; i++){
	    if(!profile[i].eq(profile[profile.length-profileDeg+i])) closed=false;
	}
	return closed;
    }
    
    public static IVec getCenter(IVecI[] profile, int profileDeg){
	boolean closed = isClosed(profile,profileDeg);
	IVec center=new IVec();
	int count=0;
	for(int i=0; i<profile.length&&!closed || i<profile.length-profileDeg&&closed; i++){
	    center.add(profile[i]);
	    count++;
	}
	center.div(count);
	return center;
    }
    
    /*********************
     * pipe
     *********************/
    
    public static ISurface pipe(IVecI[] rail, double radius){
	return pipe(rail, 1, false, radius);
    }
    public static ISurface pipe(IVecI[] rail, int railDeg, double radius){
	return pipe(rail, railDeg, false, radius);
    }
    public static ISurface pipe(IVecI[] rail, int railDeg, boolean close, double radius){
	IVecI[] railPts = null;
	double[] railKnots = null;
	if(close){
	    railPts = INurbsGeo.createClosedCP(rail, railDeg);
	    railKnots = INurbsGeo.createClosedKnots(railDeg, railPts.length);
	}
	else{
	    railPts = rail;
	    railKnots = INurbsGeo.createKnots(railDeg,railPts.length);
	}
	return pipe(railPts, railDeg, railKnots, radius);
    }
    public static ISurface pipe(ICurveI rail, double radius){
	return pipe(rail.cps(),rail.deg(),rail.knots(),radius);
    }
    public static ISurface pipe(IVecI[] rail, int railDeg, double[] railKnots, double radius){
	IVec n = IVec.averageNormal(rail);
	IVec dir = rail[1].get().diff(rail[0]);
	IVec center = rail[0].get();
	IVec[] profile = ICircleGeo.circleCP(center, dir, dir.cross(n), radius);
	int profDeg = ICircleGeo.circleDeg();
	double[] profKnots = ICircleGeo.circleKnots();
	return sweep(profile, profDeg, profKnots, center, rail, railDeg, railKnots);
    }
    
    
    public static ISurface squarePipe(IVecI[] rail, double size){
	return rectPipe(rail, size, size);
    }
    public static ISurface squarePipe(IVecI[] rail, int deg, double size){
	return rectPipe(rail, deg, size, size);
    }
    public static ISurface squarePipe(IVecI[] rail, int deg, boolean close, double size){
	return rectPipe(rail,deg,close,size,size);
    }
    public static ISurface squarePipe(ICurveI rail, double size){ return rectPipe(rail,size,size); }
    public static ISurface squarePipe(IVecI[] rail, int deg, double[] knots, double size){
	return rectPipe(rail,deg,knots,size,size);
    }
    
    
    /*
    public static ISurface rectPipe(IVecI[] rail, double size){
	return rectPipe(rail, size, size);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, double size){ // ambiguous with rectPip(IVecI[],double,double)
	return rectPipe(rail, deg, size, size);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, boolean close, double size){
	return rectPipe(rail,deg,close,size,size);
    }
    public static ISurface rectPipe(ICurveI rail, double size){ return rectPipe(rail,size,size); }
    public static ISurface rectPipe(IVecI[] rail, int deg, double[] knots, double size){
	return rectPipe(rail,deg,knots,size,size);
    }
    */
    
    /**
       @param width size in the direction of offset of rail
       @param height size in the direction of normal of rail
    */
    public static ISurface rectPipe(IVecI[] rail, double width, double height){
	return rectPipe(rail, 1, false, width, height);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, double width, double height){
	return rectPipe(rail, deg, false, width, height);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, boolean close, double width, double height){
	IVecI[] railPts = null;
	double[] railKnots = null;
	if(close){
	    railPts = INurbsGeo.createClosedCP(rail, deg);
	    railKnots = INurbsGeo.createClosedKnots(deg, railPts.length);
	}
	else{
	    railPts = rail;
	    railKnots = INurbsGeo.createKnots(deg,railPts.length);
	}
	return rectPipe(railPts, deg, railKnots, width, height);
    }
    public static ISurface rectPipe(ICurveI rail, double width, double height){
	return rectPipe(rail.cps(),rail.deg(),rail.knots(),width,height);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, double[] knots, double width, double height){
	IVec n = IVec.averageNormal(rail);
	IVec dir = rail[1].get().diff(rail[0]);
	IVec center = rail[0].get();
	IVec wdir = dir.cross(n);
	if(wdir.eq(new IVec(0,0,0))) wdir=new IVec(1,0,0);
	wdir.len(width/2);
	IVec hdir = dir.cross(wdir);
	if(hdir.eq(new IVec(0,0,0))) wdir=new IVec(0,1,0);
	hdir.len(height/2);
	
	IVec[] profile = new IVec[5];
	profile[0] = center.dup().sub(wdir).sub(hdir);
	profile[1] = center.dup().add(wdir).sub(hdir);
	profile[2] = center.dup().add(wdir).add(hdir);
	profile[3] = center.dup().sub(wdir).add(hdir);
	profile[4] = profile[0].dup();
	double[] profKnots = INurbsGeo.createClosedKnots(1,profile.length);
	return sweep(profile, 1, profKnots, center, rail, deg, knots);
    }
    
    
    
    /*********************
     * loft
     *********************/
    
    public static ISurface loft(ICurveI[] curves){
	return loft(curves, 1, false);
    }
    
    public static ISurface loft(ICurveI[] curves, int deg){
	return loft(curves, deg, false);
    }
    
    public static ISurface loft(ICurveI[] curves, int deg, boolean close){
	
	int minCPNum = checkCPNumForLoft(curves);
	if(minCPNum < 2) return null;
	
	IVecI[][] cpts = new IVec[curves.length][minCPNum];
	for(int i=0; i<curves.length; i++)
	    for(int j=0; j<minCPNum; j++) cpts[i][j] = curves[i].cp(j);
	
	double[] uk=null;
	if(close){
	    cpts = INurbsGeo.createClosedCPInU(cpts, deg);
	    uk = INurbsGeo.createClosedKnots(deg, cpts.length);
	}
	else{
	    uk = INurbsGeo.createKnots(deg, cpts.length);
	}
	double[] vk=null;
	int vdeg = curves[0].deg();
	if(curves[0].num()==minCPNum){ vk = curves[0].knots(); }
	else{
	    if(vdeg > minCPNum-1) vdeg = minCPNum-1;
	    vk = INurbsGeo.createKnots(vdeg, minCPNum);
	}
	return new ISurface(server, cpts, deg, vdeg, uk, vk, 0.0, 1.0, 0.0, 1.0);
    }

    
    /** check if number of control points is same and returns minimum number */
    public static int checkCPNumForLoft(ICurveI[] curves){
	if(curves==null||curves.length==0){ IOut.err("no curve is provided"); return 0; }
	int min = curves[0].num();
	boolean same=true;
	for(int i=1; i<curves.length; i++){
	    if(curves[i].num() != min){
		same=false;
		if(curves[i].num() < min) min = curves[i].num();
	    }
	}
	if(!same) IOut.err("num of control points needs to be same for lofting");
	return min;
    }
    
    /** loft with sorted curves in x */
    public static ISurface loftX(ICurveI[] curves){ return loftX(curves, 1, false);}
    public static ISurface loftX(ICurveI[] curves, int deg){ return loftX(curves, deg, false); }
    public static ISurface loftX(ICurveI[] curves, int deg, boolean close){
	ArrayList<ICurveI> crvs = new ArrayList<ICurveI>();
	for(ICurveI c:curves) crvs.add(c);
	ISort.sort(crvs, new XCurveComparator());
	return loft(crvs.toArray(new ICurveI[crvs.size()]), deg, close);
    }
    /** loft with sorted curves in y */
    public static ISurface loftY(ICurveI[] curves){ return loftY(curves, 1, false);}
    public static ISurface loftY(ICurveI[] curves, int deg){ return loftY(curves, deg, false); }
    public static ISurface loftY(ICurveI[] curves, int deg, boolean close){
	ArrayList<ICurveI> crvs = new ArrayList<ICurveI>();
	for(ICurveI c:curves) crvs.add(c);
	ISort.sort(crvs, new YCurveComparator());
	return loft(crvs.toArray(new ICurveI[crvs.size()]), deg, close);
    }
    /** loft with sorted curves in z */
    public static ISurface loftZ(ICurveI[] curves){ return loftZ(curves, 1, false);}
    public static ISurface loftZ(ICurveI[] curves, int deg){ return loftZ(curves, deg, false); }
    public static ISurface loftZ(ICurveI[] curves, int deg, boolean close){
	ArrayList<ICurveI> crvs = new ArrayList<ICurveI>();
	for(ICurveI c:curves) crvs.add(c);
	ISort.sort(crvs, new ZCurveComparator());
	return loft(crvs.toArray(new ICurveI[crvs.size()]), deg, close);
    }
    
    public static IVec getCenter(ICurveI crv){ return getCenter(crv.cps(),crv.deg()); }
    
    public static class XCurveComparator implements IComparator<ICurveI>{
	public int compare(ICurveI crv1, ICurveI crv2){
	    // heavy?
	    double x1 = getCenter(crv1).x;
	    double x2 = getCenter(crv2).x;
	    if(x1<x2) return -1;
	    if(x1>x2) return 1;
	    return 0;
	}
    }
    
    public static class YCurveComparator implements IComparator<ICurveI>{
	public int compare(ICurveI crv1, ICurveI crv2){
	    // heavy?
	    double y1 = getCenter(crv1).y;
	    double y2 = getCenter(crv2).y;
	    if(y1<y2) return -1;
	    if(y1>y2) return 1;
	    return 0;
	}
    }
    
    public static class ZCurveComparator implements IComparator<ICurveI>{
	public int compare(ICurveI crv1, ICurveI crv2){
	    // heavy?
	    double z1 = getCenter(crv1).z;
	    double z2 = getCenter(crv2).z;
	    if(z1<z2) return -1;
	    if(z1>z2) return 1;
	    return 0;
	}
    }
    
    // sphere , cylinder, cone,  ruled surf, loft, extrusion, rect, planarSurf
}
