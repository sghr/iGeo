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
   class with collection of static methods to create various type of surface.
   
   @author Satoru Sugihara
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
    public static ISurface surface(ICurveI trimCurve, ICurveI[] innerTrimCurves){
	return new ISurface(server, trimCurve, innerTrimCurves);
    }
    public static ISurface surface(ICurveI trimCurve, ICurveI innerTrimCurve){
	return new ISurface(server, trimCurve, innerTrimCurve);
    }
    public static ISurface surface(ICurveI[] trimCurves){ return new ISurface(server, trimCurves); }
    public static ISurface surface(IVecI[] trimCrvPts){ return new ISurface(server, trimCrvPts); }
    public static ISurface surface(IVecI[] trimCrvPts, int trimCrvDeg){ return new ISurface(server, trimCrvPts, trimCrvDeg); }
    public static ISurface surface(IVecI[] trimCrvPts, int trimCrvDeg, double[] trimCrvKnots){ return new ISurface(server, trimCrvPts, trimCrvDeg, trimCrvKnots); }
    

    /**
       box
    */
    public static IBox box(double x, double y, double z, double size){
	return new IBox(x,y,z,size);
    }
    public static IBox box(double x, double y, double z, double width, double height, double depth){
	return new IBox(x,y,z,width,height,depth);
    }
    public static IBox box(IVecI origin, double size){ return new IBox(origin,size); }
    public static IBox box(IVecI origin, double width, double height, double depth){
	return new IBox(origin,width,height,depth);
    }
    public static IBox box(IVecI origin, IVecI xvec, IVecI yvec, IVecI zvec){
	return new IBox(origin,xvec,yvec,zvec);
    }
    public static IBox box(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4,
			   IVecI pt5, IVecI pt6, IVecI pt7, IVecI pt8 ){
	return new IBox(pt1,pt2,pt3,pt4,pt5,pt6,pt7,pt8);
    }
    public static IBox box(IVecI[][][] corners){ return new IBox(corners); }
    
    
    /**
       sphere
    */
    
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
	return plane(corner, new IVec(xwidth,0,0), new IVec(0,yheight,0));
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
    
    public static ISurface extrude(ICurveI profile, double extrudeDepth){
	return extrude(profile, IVec.averageNormal(profile.cps()).len(extrudeDepth));
    }
    public static ISurface extrude(ICurveI profile, IDoubleI extrudeDepth){
	return extrude(profile, IVec.averageNormal(profile.cps()).len(extrudeDepth));
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
	return sweep(profile,1,false,null,null,rail,1,false);
    }
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, IVecI[] rail){
	return sweep(profile,1,false,profileCenter,null,rail,1,false);
    }
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, IVecI profileDir, IVecI[] rail){
	return sweep(profile,1,false,profileCenter,profileDir,rail,1,false);
    }
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI[] rail, int railDeg){
	return sweep(profile,profileDeg,false,null,null,rail,railDeg,false);
    }
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI profileCenter,
				 IVecI[] rail, int railDeg){
	return sweep(profile,profileDeg,false,profileCenter,null,rail,railDeg,false);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg,
				 IVecI profileCenter, IVecI profileDir,
				 IVecI[] rail, int railDeg){
	return sweep(profile,profileDeg,false,profileCenter,profileDir,rail,railDeg,false);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI[] rail, int railDeg, boolean closeRail){
	return sweep(profile,profileDeg, closeProfile, null, null, rail, railDeg, closeRail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, 
				 IVecI[] rail, int railDeg, boolean closeRail){
	return sweep(profile,profileDeg,closeProfile,profileCenter,null,rail,railDeg,closeRail);
    }
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, IVecI profileDir,
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
	return sweep(profPts, profileDeg, profKnots, profileCenter, profileDir,
		     railPts, railDeg, railKnots);
    }
    
    public static ISurface sweep(IVecI[] profile, ICurveI rail){
	return sweep(profile, 1, null, null, rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, ICurveI rail){
	return sweep(profile, 1, null, profileCenter, rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, IVecI profileCenter, IVecI profileDir,
				 ICurveI rail){
	return sweep(profile, 1, null, profileCenter, profileDir, rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, ICurveI rail){
	return sweep(profile, profileDeg, null, null, rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, IVecI profileCenter,
				 ICurveI rail){
	return sweep(profile, profileDeg, null, profileCenter, rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg,
				 IVecI profileCenter, IVecI profileDir, ICurveI rail){
	return sweep(profile, profileDeg, null, profileCenter, profileDir,
		     rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile, ICurveI rail){
	return sweep(profile,profileDeg,closeProfile,null,null,rail);
    }    
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, ICurveI rail){
	return sweep(profile,profileDeg,closeProfile,profileCenter,null,rail);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, boolean closeProfile,
				 IVecI profileCenter, IVecI profileDir, ICurveI rail){
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
	return sweep(profPts, profileDeg, profKnots, profileCenter, profileDir,
		     rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(ICurveI profile, IVecI[] rail){
	return sweep(profile.cps(), profile.deg(), profile.knots(), null,
		     rail, 1, null);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI[] rail){
	return sweep(profile.cps(), profile.deg(), profile.knots(), profileCenter,
		     rail, 1, null);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI profileDir,
				 IVecI[] rail){
	return sweep(profile.cps(), profile.deg(), profile.knots(),
		     profileCenter, profileDir, rail, 1, null);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI[] rail, int railDeg){
	return sweep(profile.cps(), profile.deg(), profile.knots(), null,
		     rail, railDeg, null);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI[] rail,int railDeg){
	return sweep(profile.cps(), profile.deg(), profile.knots(), profileCenter,
		     rail, railDeg, null);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI profileDir,
				 IVecI[] rail,int railDeg){
	return sweep(profile.cps(), profile.deg(), profile.knots(),
		     profileCenter, profileDir, rail, railDeg, null);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI[] rail, int railDeg, boolean closeRail){
	return sweep(profile, null, null, rail, railDeg, closeRail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter,
				 IVecI[] rail, int railDeg, boolean closeRail){
	return sweep(profile,profileCenter,null,rail,railDeg,closeRail);
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, IVecI profileDir,
				 IVecI[] rail, int railDeg, boolean closeRail){
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
	return sweep(profile.cps(), profile.deg(), profile.knots(),
		     profileCenter, profileDir, railPts, railDeg, railKnots);
    }
        
    public static ISurface sweep(ICurveI profile, ICurveI rail){
	return sweep(profile.cps(), profile.deg(), profile.knots(),
		     rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(ICurveI profile, IVecI profileCenter, ICurveI rail){
	return sweep(profile.cps(), profile.deg(), profile.knots(), profileCenter, null,
		     rail.cps(), rail.deg(), rail.knots());
    }
    
    /**
       sweep.
       @param profileCenter point on profile to be located at the points of rail
       @param profileDir direction on profile to be aligned with the normal of rail
    */
    public static ISurface sweep(ICurveI profile,
				 IVecI profileCenter,
				 IVecI profileDir,
				 ICurveI rail){
	return sweep(profile.cps(), profile.deg(), profile.knots(),
		     profileCenter, profileDir,
		     rail.cps(), rail.deg(), rail.knots());
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, double[] profileKnots,
				 IVecI[] rail, int railDeg, double[] railKnots){
	return sweep(profile, profileDeg, profileKnots, null, rail,railDeg,railKnots);
    }
    
    public static ISurface sweep(IVecI[] profile, int profileDeg, double[] profileKnots,
				 IVecI profileCenter, 
				 IVecI[] rail, int railDeg, double[] railKnots){
	return sweep(profile,profileDeg,profileKnots,profileCenter,null,
		     rail,railDeg,railKnots);
    }
    
    /**
       sweep.
       @param profileCenter point on profile to be located at the points of rail
       @param profileDir direction on profile to be aligned with the normal of rail
    */
    public static ISurface sweep(IVecI[] profile, int profileDeg, double[] profileKnots,
				 IVecI profileCenter, IVecI profileDir, 
				 IVecI[] rail, int railDeg, double[] railKnots){
	/*
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
	
	IVec profileNormal = IVec.averageNormal(profile);
	if(profileCenter==null) profileCenter = getCenter(profile,profileDeg);
	else{ profileCenter = profileCenter.dup(); } // profileCenter changes
	
	boolean railClosed=isClosed(rail,railDeg);
	
	IVec railNormal = null;
	if(profileDir!=null){ railNormal = IVec.averageNormal(rail); }
		
	IVecI[] ppts = duplicatePoints(profile);
	IVecI[][] cpts = new IVecI[rail.length][];
	
	for(int i=0; i<rail.length; i++){
	    IVecI dir = null;
	    if(i==0){ // start point
		if(!railClosed && i<rail.length-1) dir = rail[i+1].dif(rail[i]);
		else if(railClosed && rail.length-railDeg>0){
		    dir = rail[rail.length-railDeg].dif(rail[rail.length-railDeg-1]);
		}
		
		//IVecI[] ppts2 = orient(ppts, profileCenter, profileNormal, rail[i], dir);
		IVecI[] ppts2 = orient(ppts, profileCenter, profileNormal, profileDir,
				       rail[i], dir, railNormal);
		if(railClosed){
		    if(i<rail.length-1){ dir = rail[i+1].dif(rail[i]); }
		    //ppts2 = orientAndBisect(ppts, profileCenter, profileNormal, rail[i], dir);
		    ppts2 = orientAndBisect(ppts, profileCenter, profileNormal, profileDir,
					    rail[i], dir, railNormal);
		}
		//new ICurve(ppts2); //
		cpts[i] = ppts2;
	    }
	    else if(railClosed && i >= rail.length-railDeg){
		cpts[i] = duplicatePoints(cpts[i - (rail.length-railDeg)]);
		//new ICurve(cpts[i]).clr(1.0,0,0); //
	    }
	    else if(i==rail.length-1){ // last rail point (not railClosed)
		// direction is already set in the previous orientAndBisector
		orient(ppts, profileCenter, rail[i]);
		cpts[i] = ppts;
	    }
	    else{
		if(i<rail.length-1) dir = rail[i+1].dif(rail[i]);
		else dir = rail[i].dif(rail[i-1]);
		
		//IVecI[] bsct = orientAndBisect(ppts, profileCenter, profileNormal, rail[i], dir);
		IVecI[] bsct = orientAndBisect(ppts, profileCenter, profileNormal, profileDir,
					       rail[i], dir, railNormal);
		//new ICurve(bsct); //
		cpts[i] = bsct;
	    }
	}
	*/
	
	IVecI[][] cpts = sweepPoints(profile,profileDeg,profileCenter,profileDir,rail,railDeg);
	if(profileKnots==null) profileKnots = INurbsGeo.createKnots(profileDeg,profile.length);
	if(railKnots==null) railKnots = INurbsGeo.createKnots(railDeg,rail.length);
	return new ISurface(server, cpts, railDeg, profileDeg, railKnots, profileKnots,
			    0.0, 1.0, 0.0, 1.0);
    }
    
    
    /**
       control points for sweep.
       @param profileCenter point on profile to be located at the points of rail
       @param profileDir direction on profile to be aligned with the normal of rail
    */
    public static IVecI[][] sweepPoints(IVecI[] profile, int profileDeg, IVecI profileCenter,
					IVecI profileDir, IVecI[] rail, int railDeg){
	if(profile==null || profile.length<=1){
	    IOut.err("profile is null or number of array is too less");
	    return null;
	}
	if(rail==null || rail.length<=1){
	    IOut.err("rail is null or number of array is too less");
	    return null;
	}
	
	IVec profileNormal = IVec.averageNormal(profile);
	if(profileCenter==null) profileCenter = getCenter(profile,profileDeg);
	else{ profileCenter = profileCenter.dup(); } // profileCenter changes
	
	boolean railClosed=isClosed(rail,railDeg);
	
	IVec railNormal = null;
	if(profileDir!=null){ railNormal = IVec.averageNormal(rail); }
	
	IVecI[] ppts = duplicatePoints(profile);
	IVecI[][] cpts = new IVecI[rail.length][];
	
	for(int i=0; i<rail.length; i++){
	    IVecI dir = null;
	    if(i==0){ // start point
		if(!railClosed && i<rail.length-1) dir = rail[i+1].dif(rail[i]);
		else if(railClosed && rail.length-railDeg>0){
		    dir = rail[rail.length-railDeg].dif(rail[rail.length-railDeg-1]);
		}
		// auto adjust profileNormal to the first rail direction 
		if(profileNormal.dot(dir) < 0){ profileNormal.neg(); } // added 20130525
		
		IVecI[] ppts2 = orient(ppts, profileCenter, profileNormal, profileDir,
				       rail[i], dir, railNormal);
		if(railClosed){
		    if(i<rail.length-1){ dir = rail[i+1].dif(rail[i]); }
		    ppts2 = orientAndBisect(ppts, profileCenter, profileNormal, profileDir,
					    rail[i], dir, railNormal);
		}
		cpts[i] = ppts2;
	    }
	    else if(railClosed && i >= rail.length-railDeg){
		cpts[i] = duplicatePoints(cpts[i - (rail.length-railDeg)]);
	    }
	    else if(i==rail.length-1){ // last rail point (not railClosed)
		// direction is already set in the previous orientAndBisector
		orient(ppts, profileCenter, rail[i]);
		cpts[i] = ppts;
	    }
	    else{
		if(i<rail.length-1) dir = rail[i+1].dif(rail[i]);
		else dir = rail[i].dif(rail[i-1]);
		IVecI[] bsct = orientAndBisect(ppts, profileCenter, profileNormal, profileDir,
					       rail[i], dir, railNormal);
		cpts[i] = bsct;
	    }
	}
	return cpts;
    }
    
    
    /****************
     * subroutines for sweep
     ****************/
    
    public static IVecI[] duplicatePoints(IVecI[] pts){
	IVecI[] pts2 = new IVecI[pts.length];
	for(int i=0; i<pts.length; i++){ pts2[i] = pts[i].dup(); }
	return pts2;
    }
    
    
    public static IVecI[] duplicatePoints(IVecI[] pts, double weight){
	IVecI[] pts2 = new IVecI[pts.length];
	for(int i=0; i<pts.length; i++){
	    double w = 1.0;
	    if(pts[i] instanceof IVec4I){ w = ((IVec4I)pts[i]).w(); }
	    pts2[i] = pts[i].to4d(w*weight);
	}
	return pts2;
    }

    /**
       only moving profile points form profCenter to railPt. points are not duplicated.
    */
    public static IVecI[] orient(IVecI[] profile, IVecI profCenter, IVecI railPt){
	
	IVec diff = railPt.get().dif(profCenter);
	for(IVecI p:profile) p.add(diff);
	profCenter.set(railPt);
	return profile;
    }
    public static IVecI[] orient(IVecI[] profile,
				 IVecI profCenter, IVecI profNml,
				 IVecI railPt, IVecI railDir){
	return orient(profile,profCenter,profNml,null,railPt,railDir,null);
    }
    
    /**
       Orienting profile points by matching profCenter with railPt, profNml with railDir,
       profDir with railNml.
    */
    public static IVecI[] orient(IVecI[] profile,
				 IVecI profCenter, IVecI profNml, IVecI profDir,
				 IVecI railPt, IVecI railDir, IVecI railNml){
	IVec axis = null;
	double angle = 0;
	
	if(railDir!=null){
	    if(!profNml.get().isParallel(railDir)){
		axis = profNml.get().cross(railDir);
		angle = profNml.angle(railDir,axis);
	    }
	    else if(profNml.get().dot(railDir) < 0){ // parallel but opposite // added 20130525
		profNml.neg(); 
	    }
	}
	
	for(IVecI p:profile){
	    p.sub(profCenter);
	    if(axis!=null) p.rot(axis,angle);
	    //p.add(railPt);
	}

	if(axis!=null) profNml.rot(axis,angle);
	if(profDir!=null){
	    if(axis!=null) profDir.rot(axis,angle);
	    if(railNml!=null && !profDir.get().isParallel(railNml)){

		if(profDir.get().dot(railNml) < 0){ // added 20130525
		    angle = profDir.angle(railNml, railDir.get().dup().neg());
		}
		else{
		    angle = profDir.angle(railNml, railDir);
		}
		
		for(IVecI p:profile) p.rot(railDir,angle);
		profDir.rot(railDir,angle);
	    }
	}
	
	for(IVecI p:profile) p.add(railPt);
	profCenter.set(railPt);
	// multiply weight
	if(railPt instanceof IVec4I) return duplicatePoints(profile, ((IVec4I)railPt).w());
	return duplicatePoints(profile);
    }
    
    public static IVecI[] orientAndBisect(IVecI[] profile, IVecI profCenter, IVecI profNml,
					  IVecI railPt, IVecI railDir){
	return orientAndBisect(profile,profCenter,profNml,null,railPt,railDir,null);
    }
    
    public static IVecI[] orientAndBisect(IVecI[] profile,
					  IVecI profCenter, IVecI profNml, IVecI profDir,
					  IVecI railPt, IVecI railDir, IVecI railNml){
	IVec axis = null;
	double angle = 0;
	if(railDir!=null){
	    if(!profNml.get().isParallel(railDir)){
		axis = profNml.get().cross(railDir);
		angle = profNml.angle(railDir,axis);
		//parallel = angle < IConfig.angleTolerance;
	    }
	    else if(profNml.get().dot(railDir) < 0){ // parallel but opposite // added 20130525
		profNml.neg(); // ok?? test it.
	    }
	}
	
	for(IVecI p:profile) p.sub(profCenter);
	
	double bangle = angle/2;
	IVec bisectDir = null;
	if(axis!=null){
	    
	    for(IVecI p:profile){
		//p.sub(profCenter);
		//if(!parallel)
		p.rot(axis,angle);
		//p.add(railPt);
	    }
	    
	    bisectDir = profNml.get().cross(axis);
	    //if(!parallel)
	    bisectDir.rot(axis,bangle);
	    
	    profNml.rot(axis,angle);
	    
	    if(profDir!=null) profDir.rot(axis,angle);
	}
	
	if(profDir!=null && railNml!=null && !profDir.get().isParallel(railNml)){
	    if(profDir.get().dot(railNml) < 0){ // added 20130525
		angle = profDir.angle(railNml, railDir.get().dup().neg());
	    }
	    else{
		angle = profDir.angle(railNml, railDir);
	    }
	    for(IVecI p:profile) p.rot(railDir,angle);
	    profDir.rot(railDir,angle);
	}
	
	IVecI[] bsct = null;
	// multiply weight
	if(railPt instanceof IVec4I) bsct = duplicatePoints(profile, ((IVec4I)railPt).w());
	else bsct = duplicatePoints(profile);
	
	if(axis != null){
	    for(IVecI b:bsct){
		//b.sub(profCenter);
		//if(!parallel){
		//b.rot(axis,bangle);
		b.rot(axis,-bangle);
		// scale 1d
		if(Math.abs(bangle-Math.PI/2)>=IConfig.angleTolerance){
		    double scale = 1.0/Math.abs(Math.cos(bangle));
		    b.scale1d(bisectDir,scale);
		}
		//}
		//b.add(railPt);
	    }
	}
	
	for(IVecI p:profile) p.add(railPt);
	for(IVecI b:bsct) b.add(railPt);
	profCenter.set(railPt);
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
    
    public static ISurface pipe(IVecI pt1, IVecI pt2, double radius){
	return pipe(new IVecI[]{pt1, pt2}, 1, false, radius);
    }
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
	IVec nml = IVec.averageNormal(rail); 
	//IVec dir = rail[1].get().dif(rail[0]);
	IVec dir = rail[0].get().dif(rail[1]);
	IVec center = rail[0].get();
	//IVec roll = dir.cross(n);
	IVec[] profile = ICircleGeo.circleCP(center, dir, nml, radius);
	int profDeg = ICircleGeo.circleDeg();
	double[] profKnots = ICircleGeo.circleKnots();
	//return sweep(profile, profDeg, profKnots, center, rail, railDeg, railKnots);
	return sweep(profile, profDeg, profKnots, center, nml, rail, railDeg, railKnots);
    }
    
    public static ISurface squarePipe(IVecI pt1, IVecI pt2, double size){
	return rectPipe(new IVecI[]{ pt1, pt2 }, size, size);
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
    public static ISurface rectPipe(IVecI pt1, IVecI pt2, double width, double height){
	return rectPipe(new IVecI[]{ pt1, pt2 }, 1, false, width, height);
    }
    public static ISurface rectPipe(IVecI pt1, IVecI pt2,
				    double left, double right, double bottom, double top){
	return rectPipe(new IVecI[]{ pt1, pt2 }, 1, false, left, right, bottom, top);
    }
    public static ISurface rectPipe(IVecI[] rail, double width, double height){
	return rectPipe(rail, 1, false, width, height);
    }
    public static ISurface rectPipe(IVecI[] rail, double left, double right, double bottom, double top){
	return rectPipe(rail, 1, false, left, right, bottom, top);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, double width, double height){
	return rectPipe(rail, deg, false, width, height);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg,
				    double left, double right, double bottom, double top){
	return rectPipe(rail, deg, false, left, right, bottom, top);
    }
    public static ISurface rectPipe(IVecI[] rail, int deg, boolean close, double width, double height){
	return rectPipe(rail,deg,close,-width/2,width/2,-height/2,height/2);
	/*
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
	*/
    }
    
    public static ISurface rectPipe(IVecI[] rail, int deg, boolean close,
				    double left, double right, double bottom, double top){
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
	return rectPipe(railPts, deg, railKnots, left, right, bottom, top);
    }
    
    public static ISurface rectPipe(ICurveI rail, double width, double height){
	return rectPipe(rail.cps(),rail.deg(),rail.knots(),width,height);
    }
    public static ISurface rectPipe(ICurveI rail, double left, double right, double bottom, double top){
	return rectPipe(rail.cps(),rail.deg(),rail.knots(),left,right,bottom,top);
    }
    
    public static ISurface rectPipe(IVecI[] rail, int deg, double[] knots, double width, double height){
	return rectPipe(rail,deg,knots,-width/2,width/2,-height/2,height/2);
	/*
	IVec n = IVec.averageNormal(rail);
	IVec dir = rail[1].get().dif(rail[0]);
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
	//return sweep(profile, 1, profKnots, center, rail, deg, knots);
	return sweep(profile, 1, profKnots, center, hdir,rail, deg, knots);
	*/
    }
    
    
    public static ISurface rectPipe(IVecI[] rail, int deg, double[] knots, double left, double right, double bottom, double top){
	IVec n = IVec.averageNormal(rail); // what if rail.length==2?
	IVec dir = rail[1].get().dif(rail[0]);
	IVec center = rail[0].get();
		
	//IVec wdir = dir.cross(n);
	//if(wdir.eq(IVec.origin)) wdir=new IVec(1,0,0);
	//IVec hdir = dir.cross(wdir);
	//if(hdir.eq(IVec.origin)) wdir=new IVec(0,1,0);
	
	// changed 20130525
	IVec hdir = n;
	IVec wdir = dir.cross(hdir);
	if(wdir.eq(IVec.origin)){ // dir and hdir is parallel
	    if(!hdir.isParallel(IVec.zaxis)){ wdir = dir.cross(IVec.zaxis); }
	    else{ wdir = dir.cross(IVec.xaxis); }
	}
	
	IVec[] profile = new IVec[5];
	profile[0] = center.cp().add(wdir.cp().len(left)).add(hdir.cp().len(bottom));
	profile[1] = center.cp().add(wdir.cp().len(right)).add(hdir.cp().len(bottom));
	//profile[1] = center.cp().add(wdir.cp().len(left)).add(hdir.cp().len(top));
	profile[2] = center.cp().add(wdir.cp().len(right)).add(hdir.cp().len(top));
	profile[3] = center.cp().add(wdir.cp().len(left)).add(hdir.cp().len(top));
	//profile[3] = center.cp().add(wdir.cp().len(right)).add(hdir.cp().len(bottom));
	profile[4] = profile[0].cp();
	double[] profKnots = INurbsGeo.createClosedKnots(1,profile.length);
	//return sweep(profile, 1, profKnots, center, rail, deg, knots);
	return sweep(profile, 1, profKnots, center, hdir, rail, deg, knots);
    }
    
    
    /*********************
     * loft
     *********************/
    
    public static ISurface loft(ICurveI[] curves){
	return loft(curves, 1, false);
    }
    
    public static ISurface loft(ICurveI curve1, ICurveI curve2 ){
	return loft(new ICurveI[]{ curve1, curve2 }, 1, false);
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
    
    
    public static IVecI[][] checkCPNumForLoft(IVecI[][] pts){
	if(pts==null||pts.length==0){ IOut.err("no pts is provided"); return null; }
	int min = pts[0].length;
	boolean same=true;
	for(int i=1; i<pts.length; i++){
	    if(pts[i].length != min){
		same=false;
		if(pts[i].length < min) min = pts[i].length;
	    }
	}
	if(!same) IOut.err("num of control points needs to be same for lofting");
	//return min;
	
	if(same) return pts;
	
	IVecI[][] pts2 = new IVecI[pts.length][min];
	for(int i=0; i<pts.length; i++)
	    for(int j=0; j<min; j++) pts2[i][j] = pts[i][j];
	return pts2;
    }
    
    public static ISurface loft(IVecI[][] pts){
	return surface(checkCPNumForLoft(pts));
    }
    
    public static ISurface loft(IVecI[][] pts, boolean closeLoft, boolean closePts){
	return surface(checkCPNumForLoft(pts),closeLoft, closePts);
    }
    
    public static ISurface loft(IVecI[][] pts, int loftDeg, int ptsDeg){
	return surface(checkCPNumForLoft(pts), loftDeg, ptsDeg);
    }
    
    public static ISurface loft(IVecI[][] pts, int loftDeg, int ptsDeg,
				boolean closeLoft, boolean closePts){
	return surface(checkCPNumForLoft(pts), loftDeg, ptsDeg, closeLoft, closePts);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2){
	IVecI[][] cpts = new IVecI[2][];
	cpts[0] = pts1;
	cpts[1] = pts2;
	return surface(checkCPNumForLoft(cpts));
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, boolean closePts){
	IVecI[][] cpts = new IVecI[2][];
	cpts[0] = pts1;
	cpts[1] = pts2;
	return surface(checkCPNumForLoft(cpts),false,closePts);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, int ptsDeg){
	IVecI[][] cpts = new IVecI[2][];
	cpts[0] = pts1;
	cpts[1] = pts2;
	return surface(checkCPNumForLoft(cpts), 1, ptsDeg);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, int ptsDeg, boolean closePts){
	IVecI[][] cpts = new IVecI[2][];
	cpts[0] = pts1;
	cpts[1] = pts2;
	return surface(checkCPNumForLoft(cpts), 1, ptsDeg, false, closePts);
    }
        
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, IVecI[] pts3){
	IVecI[][] cpts = new IVecI[3][];
	cpts[0] = pts1;
	cpts[1] = pts2;
	cpts[2] = pts3;
	return surface(checkCPNumForLoft(cpts));
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, IVecI[] pts3,
				boolean closePts){
	IVecI[][] cpts = new IVecI[3][];
	cpts[0] = pts1;
	cpts[1] = pts2;
	cpts[2] = pts3;
	return surface(checkCPNumForLoft(cpts),false,closePts);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, IVecI[] pts3, int loftDeg, int ptsDeg){
	IVecI[][] cpts = new IVecI[3][];
	cpts[0] = pts1;
	cpts[1] = pts2;
	cpts[2] = pts3;
	return surface(checkCPNumForLoft(cpts),loftDeg,ptsDeg);
    }
    
    public static ISurface loft(IVecI[] pts1, IVecI[] pts2, IVecI[] pts3, int loftDeg, int ptsDeg, boolean closePts){
	IVecI[][] cpts = new IVecI[3][];
	cpts[0] = pts1;
	cpts[1] = pts2;
	cpts[2] = pts3;
	return surface(checkCPNumForLoft(cpts),loftDeg,ptsDeg,false,closePts);
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
    
    // sphere, cylinder, cone,  ruled surf, loft, extrusion, rect, planarSurf
    
    public static ISurface flatten(ISurfaceI surface, IVecI planeDir, IVecI planePt){
	IVecI[][] cpts = new IVecI[surface.unum()][surface.vnum()];
	for(int i=0; i<surface.unum(); i++)
	    for(int j=0; j<surface.vnum(); j++) cpts[i][j] = surface.cp(i,j);
	IVec.projectToPlane(cpts, planeDir, planePt);
	double[] uk = new double[surface.uknotNum()];
	for(int i=0; i<surface.uknotNum(); i++) uk[i] = surface.uknot(i);
	double[] vk = new double[surface.vknotNum()];
	for(int i=0; i<surface.vknotNum(); i++) vk[i] = surface.vknot(i);
	return surface(cpts,surface.udeg(),surface.vdeg(),uk,vk,0.0,1.0,0.0,1.0);
    }
    
    public static ISurface flatten(ISurfaceI surface, IVecI planeDir){
	IVecI[][] cpts = new IVecI[surface.unum()][surface.vnum()];
	for(int i=0; i<surface.unum(); i++)
	    for(int j=0; j<surface.vnum(); j++) cpts[i][j] = surface.cp(i,j);
	IVec.projectToPlane(cpts, planeDir);
	double[] uk = new double[surface.uknotNum()];
	for(int i=0; i<surface.uknotNum(); i++) uk[i] = surface.uknot(i);
	double[] vk = new double[surface.vknotNum()];
	for(int i=0; i<surface.vknotNum(); i++) vk[i] = surface.vknot(i);
	return surface(cpts,surface.udeg(),surface.vdeg(),uk,vk,0.0,1.0,0.0,1.0);
    }
    
    public static ISurface flatten(ISurfaceI surface){
	IVecI[][] cpts = new IVecI[surface.unum()][surface.vnum()];
	for(int i=0; i<surface.unum(); i++)
	    for(int j=0; j<surface.vnum(); j++) cpts[i][j] = surface.cp(i,j);
	IVec.projectToPlane(cpts);
	double[] uk = new double[surface.uknotNum()];
	for(int i=0; i<surface.uknotNum(); i++) uk[i] = surface.uknot(i);
	double[] vk = new double[surface.vknotNum()];
	for(int i=0; i<surface.vknotNum(); i++) vk[i] = surface.vknot(i);
	return surface(cpts,surface.udeg(),surface.vdeg(),uk,vk,0.0,1.0,0.0,1.0);
    }
    

    /** cap a surface which is cyclically closed in u or v direction. */
    public static IBrep cap(ISurfaceI surface){
	
	boolean uclosed=surface.isUClosed();
	boolean vclosed=surface.isVClosed();
	
	if(uclosed&&vclosed){ // no need to cap
	    IOut.debug(20, "both u and v are closed. no need to cap. returning null.");
	    return null;
	}
	
	if(!uclosed&&!vclosed){ // no way to cap
	    IOut.err("both u and v are open. one of them needs to be closed to be capped. returning null.");
	    return null;
	}
	
	if(uclosed){
	    int num = surface.unum();
	    if(num<4) return null; // if u is closed, this should not happen
	    
	    ISurfaceGeo capSurf1=null, capSurf2=null;
	    
	    int deg = surface.udeg();
	    if(deg==1 && num<=5){ // triangular or rectangular cap
		if(num==4){ // triangle as trimmed planar
		    capSurf1 = new ISurfaceGeo(new IVecI[]{ surface.cp(0,0),surface.cp(2,0),
							    surface.cp(1,0) });
		    capSurf2 = new ISurfaceGeo(new IVecI[]{ surface.cp(0,surface.vnum()-1),
							    surface.cp(1,surface.vnum()-1),
							    surface.cp(2,surface.vnum()-1) });
		}
		else{
		    capSurf1 = new ISurfaceGeo(surface.cp(0,0),surface.cp(3,0),
					       surface.cp(2,0),surface.cp(1,0));
		    capSurf2 = new ISurfaceGeo(surface.cp(0,surface.vnum()-1),
					       surface.cp(1,surface.vnum()-1),
					       surface.cp(2,surface.vnum()-1),
					       surface.cp(3,surface.vnum()-1));
		}
	    }
	    else{
		IVecI[] cps1 = new IVecI[num];
		for(int i=0; i<num; i++){ cps1[i] = surface.cp(num-1-i,0); }
		capSurf1 = profileSurfaceGeo(cps1, deg, surface.uknots());
		
		IVecI[] cps2 = new IVecI[num];
		for(int i=0; i<num; i++){ cps2[i] = surface.cp(i,surface.vnum()-1); }
		capSurf2 = profileSurfaceGeo(cps2, deg, INurbsGeo.invertKnots(surface.uknots()));
	    }
	    
	    if(capSurf1==null || capSurf2==null) return null;
	    IBrep brep = new IBrep(server,new ISurfaceGeo[]{ surface.get(), capSurf1, capSurf2});
	    brep.solid = true; // !!
	    if(surface instanceof IObject){ brep.clr(((IObject)surface).clr()); }
	    return brep;
	}
	
	// vclosed
	int num = surface.vnum();
	if(num<4) return null; // if u is closed, this should not happen
	
	ISurfaceGeo capSurf1=null, capSurf2=null;
	
	int deg = surface.vdeg();
	if(deg==1 && num<=5){ // triangular or rectangular cap
	    if(num==4){ // triangle as trimmed planar
		capSurf1 = new ISurfaceGeo(new IVecI[]{ surface.cp(0,0),surface.cp(0,2),
							surface.cp(0,1)});
		capSurf2 = new ISurfaceGeo(new IVecI[]{ surface.cp(surface.unum()-1,0),
							surface.cp(surface.unum()-1,1),
							surface.cp(surface.unum()-1,2)});
	    }
	    else{
		capSurf1 = new ISurfaceGeo(surface.cp(0,0),surface.cp(0,3),
					   surface.cp(0,2),surface.cp(0,1));
		capSurf2 = new ISurfaceGeo(surface.cp(surface.unum()-1,0),
					   surface.cp(surface.unum()-1,1),
					   surface.cp(surface.unum()-1,2),
					   surface.cp(surface.unum()-1,3));
	    }
	}
	else{
	    IVecI[] cps1 = new IVecI[num];
	    for(int i=0; i<num; i++){ cps1[i] = surface.cp(0,num-1-i); }
	    capSurf1 = profileSurfaceGeo(cps1, deg, surface.vknots());
	    
	    IVecI[] cps2 = new IVecI[num];
	    for(int i=0; i<num; i++){ cps2[i] = surface.cp(surface.unum()-1,i); }
	    capSurf2 = profileSurfaceGeo(cps2, deg, INurbsGeo.invertKnots(surface.vknots()));
	}
	
	if(capSurf1==null || capSurf2==null) return null;
	IBrep brep = new IBrep(server,new ISurfaceGeo[]{ surface.get(), capSurf1, capSurf2});
	brep.solid = true; // !!
	if(surface instanceof IObject){ brep.clr(((IObject)surface).clr()); }
	return brep;
    }
    
    
    /**
       surface defined by closed profile.if the profile is flat, planar surface is created.
       if not lofting profile into the center of the profile
    */
    public static ISurfaceGeo profileSurfaceGeo(IVecI[] cps, int deg, double[] knots){
	if(IVec.isFlat(cps)) return new ISurfaceGeo(cps,deg,knots); // planar surface
	return radialSurfaceGeo(cps,deg,knots);
    }
    /**
       surface defined by closed profile.if the profile is flat, planar surface is created.
       if not lofting profile into the center of the profile
    */
    public static ISurface profileSurface(IVecI[] cps, int deg, double[] knots){
	if(IVec.isFlat(cps)) return surface(cps,deg,knots); // planar surface
	return radialSurface(cps,deg,knots);
    }
    /**
       surface defined by closed profile.if the profile is flat, planar surface is created.
       if not lofting profile into the center of the profile
    */
    public static ISurface profileSurface(ICurveI profile){
	return profileSurface(profile.cps(),profile.deg(),profile.knots());
    }
    /**
       surface by lofting profile into the center of the profile
    */
    public static ISurfaceGeo radialSurfaceGeo(IVecI[] cps, int deg, double[] knots){
	return pointLoftGeo(cps,deg,knots,IVec.center(cps));
    }
    /**
       surface by lofting profile into the center of the profile
    */
    public static ISurface radialSurface(IVecI[] cps, int deg, double[] knots){
	return pointLoft(cps,deg,knots,IVec.center(cps));
    }
    /**
       surface by lofting profile into the center of the profile
    */
    public static ISurface radialSurface(ICurveI profile){
	return radialSurface(profile.cps(),profile.deg(),profile.knots());
    }
    /**
       surface by lofting profile into the specified point
    */
    public static ISurfaceGeo pointLoftGeo(IVecI[] cps, int deg, double[] knots, IVecI center){
	int num = cps.length;
	IVecI[][] cps2 = new IVecI[num][2];
	IVec cnt = center.get();
	for(int i=0; i<num; i++){
	    cps2[i][0] = cps[i];
	    cps2[i][1] = cnt.dup();
	}
	return new ISurfaceGeo(cps2, deg, 1, knots, INurbsGeo.createKnots(1,2));
    }
    /**
       surface by lofting profile into the specified point
    */
    public static ISurface pointLoft(IVecI[] cps, int deg, double[] knots, IVecI center){
	int num = cps.length;
	IVecI[][] cps2 = new IVecI[num][2];
	IVec cnt = center.get();
	for(int i=0; i<num; i++){
	    cps2[i][0] = cps[i];
	    cps2[i][1] = cnt.dup();
	}
	return new ISurface(cps2, deg, 1, knots, INurbsGeo.createKnots(1,2));
    }
    /**
       surface by lofting profile into the specified point
    */
    public static ISurface pointLoft(ICurveI profile, IVecI center){
	return pointLoft(profile.cps(),profile.deg(),profile.knots(),center);
    }    
}
