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

import igeo.gui.*;

/**
   Geometry of NURBS surface.
   
   @author Satoru Sugihara
*/
public class ISurfaceGeo extends INurbsGeo implements ISurfaceI, IEntityParameter{
    
    /*protected*/ public IVecI[][] controlPoints;
    
    // degree and knots should not be parameterized
    // because number of control points, degree and knots are intertwined together: 
    // changing them independently doesn't make sense
    /*protected*/ public int udegree, vdegree;
    
    /**
       normalized knot vector (start value in knot vector is 0, end value is 1)
    */
    /*protected*/ public double[] uknots, vknots;
    /*protected*/ public double ustart,uend,vstart,vend;
    
    /**
       flag to determine to use default weight value (1.0)
    */
    /*protected*/ public boolean[][] defaultWeights;
    
    /*protected*/ public IBSplineBasisFunction basisFunctionU, basisFunctionV;
    /*protected*/ public IBSplineBasisFunction derivativeFunctionU, derivativeFunctionV;
    
    
    // trim
    /*protected*/ public ArrayList<ArrayList<ITrimCurve>> innerTrimLoop;
    /*protected*/ public ArrayList<ArrayList<ITrimCurve>> outerTrimLoop;
    /*protected*/ public boolean innerTrimClosed=false;
    /*protected*/ public boolean outerTrimClosed=false;
    
    /** point cache for proximity search */
    public ISurfaceCache uvSearchCache;
    public ITrimCache trimCache;
    
    
    public ISurfaceGeo(){}
    
    public ISurfaceGeo(IVecI[][] cpts, int udegree, int vdegree,
		       double[] uknots, double[] vknots,
		       double ustart, double uend, double vstart, double vend){
	this.ustart=ustart; this.uend=uend;
	this.vstart=vstart; this.vend=vend;
	/*
	if(IConfig.normalizeKnots){
	    normalizeKnots(uknots, ustart, uend);
	    normalizeKnots(vknots, vstart, vend);
	}
	*/
	if(ustart!=0.0||uend!=1.0) normalizeKnots(uknots, ustart, uend);
	if(vstart!=0.0||vend!=1.0) normalizeKnots(vknots, vstart, vend);
	init(cpts, udegree, vdegree, uknots, vknots);
    }
    
    public ISurfaceGeo(IVecI[][] cpts, int udegree, int vdegree,
		       double[] uknots, double[] vknots){
	ustart=uknots[0]; uend=uknots[uknots.length-1];
	vstart=vknots[0]; vend=vknots[vknots.length-1];
	/*
	if(IConfig.normalizeKnots){
	    if(uknots[0]!=0.0 || uknots[uknots.length-1]!=1.0) 
		normalizeKnots(uknots, uknots[0], uknots[uknots.length-1]);
	    if(vknots[0]!=0.0 || vknots[vknots.length-1]!=1.0) 
		normalizeKnots(vknots, vknots[0], vknots[vknots.length-1]);
	}
	*/
	if(uknots[0]!=0.0 || uknots[uknots.length-1]!=1.0) 
	    normalizeKnots(uknots, uknots[0], uknots[uknots.length-1]);
	if(vknots[0]!=0.0 || vknots[vknots.length-1]!=1.0) 
	    normalizeKnots(vknots, vknots[0], vknots[vknots.length-1]);
	init(cpts, udegree, vdegree, uknots, vknots);
    }
    
    public ISurfaceGeo(IVecI[][] cpts, int udegree, int vdegree){
	ustart=0.; uend=1.;
	vstart=0.; vend=1.;
	init(cpts, udegree, vdegree,
	     createKnots(udegree, cpts.length),createKnots(vdegree, cpts[0].length));
    }
    
    public ISurfaceGeo(IVecI[][] cpts){
	ustart=0.; uend=1.;
	vstart=0.; vend=1.;
	init(cpts, 1, 1, createKnots(1, cpts.length), createKnots(1, cpts[0].length));
    }
    
    public ISurfaceGeo(IVecI[][] cpts, int udegree, int vdegree, boolean closeU, boolean closeV){
	ustart=0.; uend=1.;
	vstart=0.; vend=1.;
	init(cpts, udegree, vdegree, closeU, closeV);
    }
    
    public ISurfaceGeo(IVecI[][] cpts, int udegree, int vdegree, boolean closeU, double[] vk){
	ustart=0.; uend=1.;
	vstart=0.; vend=1.;
	init(cpts, udegree, vdegree, closeU, vk);
    }
    
    public ISurfaceGeo(IVecI[][] cpts, int udegree, int vdegree, double[] uk, boolean closeV){
	ustart=0.; uend=1.;
	vstart=0.; vend=1.;
	init(cpts, udegree, vdegree, uk, closeV);
    }
    
    public ISurfaceGeo(IVecI[][] cpts, boolean closeU, boolean closeV){
	ustart=0.; uend=1.;
	vstart=0.; vend=1.;
	init(cpts, 1, 1, closeU, closeV);
    }
    
    public ISurfaceGeo(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4){
	IVecI[][] cpts = new IVecI[2][2];
	cpts[0][0] = pt1; cpts[1][0] = pt2;
	cpts[1][1] = pt3; cpts[0][1] = pt4;
	ustart=0.; uend=1.;
	vstart=0.; vend=1.;
	init(cpts, 1, 1, createKnots(1,2), createKnots(1,2));
    }
    public ISurfaceGeo(IVecI pt1, IVecI pt2, IVecI pt3){
	this(pt1,pt2,pt3,pt1);
    }
    public ISurfaceGeo(double x1, double y1, double z1, double x2, double y2, double z2,
		       double x3, double y3, double z3, double x4, double y4, double z4){
	this(new IVec(x1,y1,z1),new IVec(x2,y2,z2),new IVec(x3,y3,z3),new IVec(x4,y4,z4));
    }
    public ISurfaceGeo(double x1, double y1, double z1, double x2, double y2, double z2,
		       double x3, double y3, double z3){
	this(new IVec(x1,y1,z1),new IVec(x2,y2,z2),new IVec(x3,y3,z3));
    }
    
    public ISurfaceGeo(double[][][] xyzValues){
	this(getPointsFromArray(xyzValues));
    }
    
    public ISurfaceGeo(double[][][] xyzValues, int udeg, int vdeg){
	this(getPointsFromArray(xyzValues),udeg, vdeg);
    }
    
    public ISurfaceGeo(double[][][] xyzValues, boolean closeU, boolean closeV){
	this(getPointsFromArray(xyzValues),closeU,closeV);
    }
    
    public ISurfaceGeo(double[][][] xyzValues, int udeg, int vdeg, boolean closeU, boolean closeV){
	this(getPointsFromArray(xyzValues),udeg, vdeg,closeU,closeV);
    }
    
    /** create surface with outer trim curve */
    
    public ISurfaceGeo(ICurveI trimCurve){
	IVecI[] cpts = new IVecI[trimCurve.num()];
	for(int i=0; i<cpts.length; i++) cpts[i] = trimCurve.cp(i);
	int deg = trimCurve.deg();
	double[] knots = new double[trimCurve.knotNum()];
	for(int i=0; i<knots.length; i++) knots[i] = trimCurve.knot(i);
	initWithPlanarTrim(cpts, deg, knots, !trimCurve.isClosed());
    }
    public ISurfaceGeo(ICurveI trimCurve, ICurveI[] innerTrimCurves){
	IVecI[] cpts = new IVecI[trimCurve.num()];
	for(int i=0; i<cpts.length; i++) cpts[i] = trimCurve.cp(i);
	int deg = trimCurve.deg();
	double[] knots = new double[trimCurve.knotNum()];
	for(int i=0; i<knots.length; i++) knots[i] = trimCurve.knot(i);
	
	int inum = innerTrimCurves.length;
	IVecI[][] innerCpts = new IVecI[inum][];
	int[] innerDeg = new int[inum];
	double[][] innerKnots = new double[inum][];
	boolean[] innerClose = new boolean[inum];
	for(int i=0; i<inum; i++){
	    innerCpts[i] = new IVecI[innerTrimCurves[i].num()];
	    for(int j=0; j<innerCpts[i].length; j++){ innerCpts[i][j] = innerTrimCurves[i].cp(j); }
	    innerDeg[i] = innerTrimCurves[i].deg();
	    innerKnots[i] = new double[innerTrimCurves[i].knotNum()];
	    for(int j=0; j<innerKnots[i].length; j++){ innerKnots[i][j] = innerTrimCurves[i].knot(j); }
	    innerClose[i] = !innerTrimCurves[i].isClosed();
	}
	initWithPlanarTrim(cpts, deg, knots, !trimCurve.isClosed(),
			   innerCpts, innerDeg, innerKnots, innerClose);
    }

    public ISurfaceGeo(ICurveI trimCurve, ICurveI innerTrimCurve){
	this(trimCurve, new ICurveI[]{ innerTrimCurve });
    }
    
    public ISurfaceGeo(ICurveI[] trimCurves){
	if(trimCurves==null || trimCurves.length==0){ IOut.err("no trim curve is provided"); return; }
	initWithPlanarTrims(trimCurves);
    }
    public ISurfaceGeo(IVecI[] trimCrvPts){
	IVecI[] cpts2 = createClosedCP(trimCrvPts,1);
	initWithPlanarTrim(cpts2,1,createClosedKnots(1,cpts2.length),false);
    }
    public ISurfaceGeo(IVecI[] trimCrvPts, int trimCrvDeg){
	IVecI[] cpts2 = createClosedCP(trimCrvPts, trimCrvDeg);
	double[] knots = createClosedKnots(trimCrvDeg,cpts2.length);
	initWithPlanarTrim(cpts2, trimCrvDeg, knots, false);
    }
    public ISurfaceGeo(IVecI[] trimCrvPts, int trimCrvDeg, double[] trimCrvKnots){
	ICurveGeo testCrv = new ICurveGeo(trimCrvPts,trimCrvDeg,trimCrvKnots);
	initWithPlanarTrim(trimCrvPts, trimCrvDeg, trimCrvKnots, !testCrv.isClosed());
    }
    
    
    public ISurfaceGeo(ISurfaceGeo srf){
	// duplicate points
	controlPoints = new IVecI[srf.controlPoints.length][srf.controlPoints[0].length];
	for(int i=0; i<controlPoints.length; i++)
	    for(int j=0; j<controlPoints[0].length; j++)
		controlPoints[i][j] = srf.controlPoints[i][j].dup();
	
	uknots = new double[srf.uknots.length];
	System.arraycopy(srf.uknots, 0, uknots, 0, uknots.length);
	vknots = new double[srf.vknots.length];
	System.arraycopy(srf.vknots, 0, vknots, 0, vknots.length);
	udegree = srf.udegree;
	vdegree = srf.vdegree;
	ustart = srf.ustart;
	uend = srf.uend;
	vstart = srf.vstart;
	vend = srf.vend;
	init(controlPoints, udegree, vdegree, uknots, vknots);
	
	//if(srf.innerTrimLoop!=null) setInnerTrim(srf.innerTrimLoop);
	//if(srf.outerTrimLoop!=null) setOuterTrim(srf.outerTrimLoop);
	
	if(srf.innerTrimLoop!=null){
	    innerTrimLoop = new ArrayList<ArrayList<ITrimCurve>>();
	    for(int i=0; i<srf.innerTrimLoop.size(); i++){
		ArrayList<ITrimCurve> loop = new ArrayList<ITrimCurve>();
		for(int j=0; j<srf.innerTrimLoop.get(i).size(); j++){
		    loop.add(new ITrimCurve(srf.innerTrimLoop.get(i).get(j),this));
		}
		innerTrimLoop.add(loop);
	    }
	}
	if(srf.outerTrimLoop!=null){
	    outerTrimLoop = new ArrayList<ArrayList<ITrimCurve>>();
	    for(int i=0; i<srf.outerTrimLoop.size(); i++){
		ArrayList<ITrimCurve> loop = new ArrayList<ITrimCurve>();
		for(int j=0; j<srf.outerTrimLoop.get(i).size(); j++){
		    loop.add(new ITrimCurve(srf.outerTrimLoop.get(i).get(j),this));
		}
		outerTrimLoop.add(loop);
	    }
	}
    }
    
    public void init(IVecI[][] cpts, int udeg, int vdeg, boolean closeU, boolean closeV){
	if(closeU && closeV){
	    cpts = createClosedCPInV(cpts, vdeg);
	    cpts = createClosedCPInU(cpts, udeg);
	    init(cpts, udeg, vdeg, createClosedKnots(udeg, cpts.length),
		 createClosedKnots(vdeg, cpts[0].length));
	    /*
	    IVecI[][] cpts2 = new IVecI[cpts.length][];
	    for(int i=0; i<cpts.length; i++)
		cpts2[i] = createClosedCP(cpts[i],vdeg);
	    
	    IVecI[][] ucpts = new IVecI[cpts2[0].length][cpts2.length];
	    for(int i=0; i<cpts2[0].length; i++)
		for(int j=0; j<cpts2.length; j++) ucpts[i][j] = cpts2[j][i];
	    IVecI[][] ucpts2 = new IVecI[cpts2[0].length][];
	    for(int i=0; i<cpts2[0].length; i++)
		ucpts2[i] = createClosedCP(ucpts[i],udeg);
	    IVecI[][] cpts3 = new IVecI[ucpts2[0].length][cpts2[0].length];
	    for(int i=0; i<cpts3.length; i++)
		for(int j=0; j<cpts3[0].length; j++) cpts3[i][j] = ucpts2[j][i];
	    
	    init(cpts3, udeg, vdeg, createClosedKnots(udeg, cpts3.length),
		 createClosedKnots(vdeg, cpts3[0].length));
	    */
	}
	else if(closeU){
	    init(cpts, udeg, vdeg, closeU, createKnots(vdeg, cpts[0].length));
	}
	else if(closeV){
	    init(cpts, udeg, vdeg, createKnots(udeg, cpts.length), closeV);
	}
	else{
	    init(cpts, udeg, vdeg,
		 createKnots(udeg, cpts.length),createKnots(vdeg, cpts[0].length));
	}
    }
    public void init(IVecI[][] cpts, int udeg, int vdeg, double[] uk, boolean closeV){
	if(closeV){
	    cpts = createClosedCPInV(cpts, vdeg);
	    init(cpts, udeg, vdeg, uk, createClosedKnots(vdeg, cpts[0].length));
	    /*
	    IVecI[][] cpts2 = new IVecI[cpts.length][];
	    for(int i=0; i<cpts.length; i++)
		cpts2[i] = createClosedCP(cpts[i],vdeg);
	    init(cpts2, udeg, vdeg, uk, createClosedKnots(vdeg, cpts2[0].length));
	    */
	}
	else{
	    init(cpts, udeg, vdeg, uk,createKnots(vdeg,cpts[0].length));
	}
    }
    public void init(IVecI[][] cpts, int udeg, int vdeg, boolean closeU, double[] vk){
	if(closeU){
	    cpts = createClosedCPInU(cpts, udeg);
	    init(cpts, udeg, vdeg, createClosedKnots(udeg, cpts.length), vk);
	    /*
	    IVecI[][] ucpts = new IVecI[cpts[0].length][cpts.length];
	    for(int i=0; i<cpts[0].length; i++)
		for(int j=0; j<cpts.length; j++) ucpts[i][j] = cpts[j][i];
	    IVecI[][] ucpts2 = new IVecI[cpts[0].length][];
	    for(int i=0; i<cpts[0].length; i++)
		ucpts2[i] = createClosedCP(ucpts[i],udeg);
	    IVecI[][] cpts2 = new IVecI[ucpts2[0].length][cpts[0].length];
	    for(int i=0; i<cpts2.length; i++)
		for(int j=0; j<cpts2[0].length; j++) cpts2[i][j] = ucpts2[j][i];
	    init(cpts2, udeg, vdeg, createClosedKnots(udeg, cpts2.length), vk);
	    */
	}
	else{
	    init(cpts, udeg, vdeg, createKnots(udeg,cpts.length),vk);
	}
    }
    
    public void init(IVecI[][] cpts, int udeg, int vdeg, double[] uk, double[] vk){
	
	// check validity (costly?)
	if(IConfig.checkValidControlPoint){ isValidCP(cpts, udeg, vdeg, uk, vk); }
	
	// duplicate of control points is avoided
        if(IConfig.checkDuplicatedControlPoint){ checkDuplicatedCP(cpts); }
        else if(IConfig.checkDuplicatedControlPointOnEdge){ checkDuplicatedCPOnEdge(cpts); }
	
	
	controlPoints = cpts;
	udegree = udeg;
	vdegree = vdeg;
	uknots = uk;
	vknots = vk;
	basisFunctionU = new IBSplineBasisFunction(udeg, uk);
	basisFunctionV = new IBSplineBasisFunction(vdeg, vk);
	
	defaultWeights = new boolean[cpts.length][cpts[0].length];
	for(int i=0; i<cpts.length; i++)
	    for(int j=0; j<cpts[0].length; j++)
		defaultWeights[i][j] = !(cpts[i][j] instanceof IVec4I);
	
    }
    
    
    public void initWithPlanarTrim(IVecI[] cpts, int trimDeg, double[] trimKnots,
				   boolean close){
	
	IVec normal = IVec.averageNormal(cpts);
	IVec[] uvvec = getPlanarUVVectors(cpts);
	IVec uvec = uvvec[0], vvec = uvvec[1];
	
	uvec = normal.cross(uvec).cross(normal).unit();
	vvec = normal.cross(uvec).unit();
	
	IVec origin = cpts[0].get();
	IVec[] uvpts = getPlanarUVPoints(cpts, origin, uvec, vvec);
	
	double[][] uvrange = getPlanarUVRange(uvpts);
	
	IVec[][] cornerPts = new IVec[2][2];
	cornerPts[0][0] = origin.dup().add(uvec, uvrange[0][0]).add(vvec, uvrange[1][0]);
	cornerPts[1][0] = origin.dup().add(uvec, uvrange[0][1]).add(vvec, uvrange[1][0]);
	cornerPts[0][1] = origin.dup().add(uvec, uvrange[0][0]).add(vvec, uvrange[1][1]);
	cornerPts[1][1] = origin.dup().add(uvec, uvrange[0][1]).add(vvec, uvrange[1][1]);
	
	for(int i=0; i<uvpts.length; i++){
	    uvpts[i].x = (uvpts[i].x - uvrange[0][0])/(uvrange[0][1] - uvrange[0][0]);
	    uvpts[i].y = (uvpts[i].y - uvrange[1][0])/(uvrange[1][1] - uvrange[1][0]);
	}
	
	init(cornerPts,1,1,
	     createKnots(1, cornerPts.length),createKnots(1, cornerPts[0].length));
	
	if(!close)
	    addOuterTrimLoop(new ITrimCurve(uvpts, trimDeg, trimKnots, 0., 1.));
	else{
	    addOuterTrimLoop(new ITrimCurve[]{
				 new ITrimCurve(uvpts, trimDeg, trimKnots, 0., 1.),
				 new ITrimCurve(uvpts[uvpts.length-1],uvpts[0])});
	    // adding a straight line to close
	}
    }
    
    
    public void initWithPlanarTrim(IVecI[] cpts, int trimDeg, double[] trimKnots,
				   boolean close,
				   IVecI[][] innerCpts, int[] innerTrimDeg, double[][] innerTrimKnots,
				   boolean[] innerClose){
	
	initWithPlanarTrim(cpts,trimDeg,trimKnots,close);
	if(innerCpts==null || innerTrimDeg==null || innerTrimKnots==null || innerClose==null){
	    IOut.err("inner trim parameter is null"); //
	    return;
	}
	
	if(innerCpts.length!=innerTrimDeg.length ||
	   innerCpts.length!=innerTrimKnots.length ||
	   innerCpts.length!=innerClose.length ){
	    IOut.err("inner trim parameter array length doesn't match");
	    return;
	}
	
	IVec orig = corner(0,0).get();
	IVec uvec = corner(1,0).diff(orig);
	IVec vvec = corner(0,1).diff(orig);
	
	for(int i=0; i<innerCpts.length; i++){
	    IVec[] uvpts = getPlanarUVPoints(innerCpts[i], orig, uvec, vvec);
	    
	    if(!innerClose[i])
		addInnerTrimLoop(new ITrimCurve(uvpts, innerTrimDeg[i], innerTrimKnots[i], 0., 1.));
	    else{
		addInnerTrimLoop(new ITrimCurve[]{
				     new ITrimCurve(uvpts, innerTrimDeg[i], innerTrimKnots[i], 0., 1.),
				     new ITrimCurve(uvpts[uvpts.length-1],uvpts[0])});
		// adding a straight line to close
	    }
	}
	
    }
    
    

    public void initWithPlanarTrims(ICurveI[] curves){
	ArrayList<IVecI> pts = new ArrayList<IVecI>();
	for(int i=0; i<curves.length; i++){
	    for(int j=0; j<curves[i].num(); j++){
		if(pts.size()==0 || !pts.get(pts.size()-1).eq(curves[i].cp(j)))
		    pts.add(curves[i].cp(j));
	    }
	}
	
	IVecI[] cpts = pts.toArray(new IVec[pts.size()]);
	
	IVec normal = IVec.averageNormal(cpts);
	IVec[] uvvec = getPlanarUVVectors(cpts);
	IVec uvec = uvvec[0], vvec = uvvec[1];
	
	uvec = normal.cross(uvec).cross(normal).unit();
	vvec = normal.cross(uvec).unit();
	
	IVec origin = cpts[0].get();
	IVec[] uvpts = getPlanarUVPoints(cpts, origin, uvec, vvec);
	
	double[][] uvrange = getPlanarUVRange(uvpts);
	
	IVec[][] cornerPts = new IVec[2][2];
	cornerPts[0][0] = origin.dup().add(uvec, uvrange[0][0]).add(vvec, uvrange[1][0]);
	cornerPts[1][0] = origin.dup().add(uvec, uvrange[0][1]).add(vvec, uvrange[1][0]);
	cornerPts[0][1] = origin.dup().add(uvec, uvrange[0][0]).add(vvec, uvrange[1][1]);
	cornerPts[1][1] = origin.dup().add(uvec, uvrange[0][1]).add(vvec, uvrange[1][1]);
	
	IVecI[][] pts2 = new IVecI[curves.length][];
	for(int i=0; i<curves.length; i++){
	    pts2[i] = new IVecI[curves[i].num()];
	    for(int j=0; j<curves[i].num(); j++) pts2[i][j] = curves[i].cp(j);
	}
	IVec[][] uvpts2 = new IVec[curves.length][];
	for(int i=0; i<curves.length; i++){
	    uvpts2[i] = getPlanarUVPoints(pts2[i], origin, uvec, vvec);
	}
	
	for(int i=0; i<uvpts2.length; i++){
	    for(int j=0; j<uvpts2[i].length; j++){
		uvpts2[i][j].x=(uvpts2[i][j].x-uvrange[0][0])/(uvrange[0][1]-uvrange[0][0]);
		uvpts2[i][j].y=(uvpts2[i][j].y-uvrange[1][0])/(uvrange[1][1]-uvrange[1][0]);
	    }
	}
	
	init(cornerPts,1,1,
	     createKnots(1, cornerPts.length),createKnots(1, cornerPts[0].length));
	
	ArrayList<ITrimCurve> trimCurves = new ArrayList<ITrimCurve>();
	
	for(int i=0; i<curves.length; i++){
	    double[] knots = new double[curves[i].knotNum()];
	    for(int j=0; j<knots.length; j++){ knots[j] = curves[i].knot(j); }
	    ITrimCurve trim = new ITrimCurve(uvpts2[i],curves[i].deg(),knots,0.,1.);
	    
	    if(i>0 && !trimCurves.get(trimCurves.size()-1).end2d().eq(trim.start2d())){
		// filling a gap with straight line
		ITrimCurve fillLine =
		    new ITrimCurve(trimCurves.get(trimCurves.size()-1).end2d().to3d(),
				   trim.start2d().to3d());
		trimCurves.add(fillLine);
	    }
	    trimCurves.add(trim);
	}
	
	if(trimCurves.size()>1 &&
	   !trimCurves.get(trimCurves.size()-1).end2d().eq(trimCurves.get(0).start2d())){
	    // close the loop with straight line
	    ITrimCurve fillLine =
		new ITrimCurve(trimCurves.get(trimCurves.size()-1).end2d().to3d(),
			       trimCurves.get(0).start2d().to3d());
	    trimCurves.add(fillLine);
	}
	
	addOuterTrimLoop(trimCurves.toArray(new ITrimCurve[trimCurves.size()]));
    }
    
    
    public boolean isValid(){
	return isValidCP(controlPoints, udegree, vdegree, uknots, vknots);
    }
    
    public static boolean isValidCP(IVecI[][] cpts, int udeg, int vdeg,
				    double[] uknots, double[] vknots){
	if(cpts==null){
	    IOut.err("control points are null");
	    return false;
	}
	
	if(uknots==null){
	    IOut.err("uknots are null");
	    return false;
	}
	
	if(vknots==null){
	    IOut.err("vknots are null");
	    return false;
	}
	
	if(udeg <= 0){
	    IOut.err("invalid udeg ("+udeg+")");
	    return false;
	}
	
	if(vdeg <= 0){
	    IOut.err("invalid vdeg ("+vdeg+")");
	    return false;
	}
	
	int unum = cpts.length;
	int vnum = cpts[0].length;
	
	for(int i=1; i<unum; i++){
	    if(cpts[i].length!=vnum){
		IOut.err("vnum("+vnum+") in control point array is inconsistent ("+
			 cpts[i].length+")");
		return false;
	    }
	}
	
	if(unum <= udeg){
	    IOut.err("too less control points in u direction ("+unum+") for u degree "+udeg+". it needs minimum "+(udeg+1));
	    return false;
	}
	
	if(vnum <= vdeg){
	    IOut.err("too less control points in v direction ("+vnum+") for v degree "+vdeg+". it needs minimum "+(vdeg+1));
	    return false;
	}
	
	if(uknots.length != (udeg+unum+1)){
	    IOut.err("uknot array length is invalid. it needs to be "+(udeg+unum+1));
	    return false;
	}
	
	if(vknots.length != (vdeg+vnum+1)){
	    IOut.err("vknot array length is invalid. it needs to be "+(vdeg+vnum+1));
	    return false;
	}

	if(!isValidCP(cpts)) return false;
	
	if(!isValidKnots(uknots)){
	    IOut.err("uknot has invalid value");
	    return false;
	}
	if(!isValidKnots(vknots)){
	    IOut.err("uknot has invalid value");
	    return false;
	}
	
	return true;
    }
    
    public static boolean isValidCP(IVecI[][] cpts){
	if(cpts==null){ IOut.err("controlPoint is null"); return false; }
	for(int i=0; i<cpts.length; i++){
	    if(cpts[i]==null){ IOut.err("controlPoint["+i+"] is null"); return false; }
	    for(int j=0; j<cpts[i].length; j++){
		if(cpts[i][j]==null){
		    IOut.err("controlPoint at "+i+","+j+" is null");
		    return false;
		}
		if(!cpts[i][j].isValid()){
		    IOut.err("controlPoint at "+i+","+j+" is invalid");
		    return false;
		}
	    }
	}
	return true;
    }
    
    
    public static void checkDuplicatedCP(IVecI[][] cpts){
	int un = cpts.length;
	int vn = cpts[0].length;
	int num = un*vn;
	for(int i=0; i<num; i++)
	    for(int j=i+1; j<num; j++)
		if(cpts[j/vn][j%vn]==cpts[i/vn][i%vn])
		    cpts[j/vn][j%vn] = cpts[i/vn][i%vn].dup();
    }
    
    public static void checkDuplicatedCP(IVecI[][] cpts, IVecI[] pts){
	for(int i=0; i<cpts.length; i++){
	    for(int j=0; j<cpts[i].length; j++){
		for(int k=0; k<pts.length; k++){
		    if(pts[k] == cpts[i][j]){ pts[k] = pts[k].dup(); }
		}
	    }
	}
    }
    
    public static void checkDuplicatedCPOnEdge(IVecI[][] cpts){
	int un = cpts.length;
	int vn = cpts[0].length;
	// u dir
	for(int i=0; i<un; i++)
	    if(cpts[i][0]==cpts[i][vn-1]) cpts[i][vn-1] = cpts[i][0].dup();
	// v dir
	for(int i=0; i<vn; i++)
	    if(cpts[0][i]==cpts[un-1][i]) cpts[un-1][i] = cpts[0][i].dup();
    }
    
    public static IVec[][] getPointsFromArray(double[][][] xyzvalues){
	IVec[][] cpts = new IVec[xyzvalues.length][xyzvalues[0].length];
        for(int i=0; i<cpts.length; i++){
	    for(int j=0; j<cpts[0].length; j++){
		if(xyzvalues[i][j].length==4){
		    cpts[i][j] = new IVec4(xyzvalues[i][j][0], xyzvalues[i][j][1],
					   xyzvalues[i][j][2], xyzvalues[i][j][3]);
		}
		else{
		    cpts[i][j] = new IVec();
		    if(xyzvalues[i][j].length>=1) cpts[i][j].x = xyzvalues[i][j][0];
		    if(xyzvalues[i][j].length>=2) cpts[i][j].y = xyzvalues[i][j][1];
		    if(xyzvalues[i][j].length>=3) cpts[i][j].z = xyzvalues[i][j][2];
		}
	    }
        }
	return cpts;
    }
    
    /*
    public static IVec getAverageNormal(IVecI[] pts){
	int n = pts.length;
	if(n<3) return null;
	if(n==3) return pts[1].get().diff(pts[0]).cross(pts[2].get().diff(pts[1])).unit();
	
	IVec nrm = new IVec();
	for(int i=0; i<n; i++){
	    IVec diff1 = pts[(i+1)%n].get().diff(pts[i]);
	    IVec diff2 = pts[(i+2)%n].get().diff(pts[(i+1)%n]);
	    nrm.add(diff1.cross(diff2));
	}
	return nrm.unit();
    }
    */
    public static IVec[] getPlanarUVVectors(IVecI[] pts){
        IVec uvec=null, vvec=null;
        int i;
        for(i=0; i<pts.length-1 && uvec==null; i++){
	    IVec p = pts[i+1].get();
	    if(!p.eq(pts[i])) uvec = p.diff(pts[i]);
        }
	for(; i<pts.length-1&&vvec==null; i++){
	    IVec p = pts[i+1].get();
	    if(!p.eq(pts[i])){
		vvec = p.diff(pts[i]);
		if(vvec.isParallel(uvec)) vvec=null;
	    }
        }
	
	if(pts.length < 3){
	    IOut.err("too less trim points ("+pts.length+"). needs to be 3 or more");
	    return null;
	}
	if(vvec==null){
	    IOut.err("trim curve has no width");
	    vvec = uvec.rot(IG.zaxis, Math.PI/2);
	}
	return new IVec[]{ uvec.unit(), uvec.cross(vvec).cross(uvec).unit() };
    }
    
    public static IVec[] getPlanarUVPoints(IVecI[] pts, IVecI origin, IVec uvec, IVec vvec){
	IVec[] uvpts = new IVec[pts.length];
	
	for(int i=0; i<pts.length; i++){
	    IVec diff = pts[i].get().diff(origin);
	    double[] uv = diff.projectTo2Vec(uvec,vvec);
	    if(pts[i] instanceof IVec4I){
		uvpts[i] = new IVec4(uv[0],uv[1],0,((IVec4I)pts[i]).w());
	    }
	    else{ uvpts[i] = new IVec(uv[0],uv[1],0); }
	}
	return uvpts;
    }
    
    public static double[][] getPlanarUVRange(IVec[] uvpts){

	double minu = uvpts[0].x, maxu = uvpts[0].x, minv = uvpts[0].y, maxv = uvpts[0].y;
	for(int i=1; i<uvpts.length; i++){
	    if(uvpts[i].x < minu) minu = uvpts[i].x;
            else if(uvpts[i].x > maxu) maxu = uvpts[i].x;
            if(uvpts[i].y < minv) minv = uvpts[i].y;
            else if(uvpts[i].y > maxv) maxv = uvpts[i].y;
	}
	
	return new double[][]{ new double[]{ minu, maxu }, new double[]{ minv, maxv }};
    }
    
    
    public ISurfaceGeo get(){ return this; }
    
    public ISurfaceGeo dup(){ return new ISurfaceGeo(this); }
        
    public IVec pt(IVec2I v){ IVec2 vec=v.get(); return pt(vec.x,vec.y); }
    public IVec pt(IDoubleI u, IDoubleI v){ return pt(u.x(),v.x()); }
    public IVec pt(double u, double v){
	IVec retval = new IVec();
	pt(u,v,retval);
	return retval;
    }
    public void pt(double u, double v, IVec retval){
        int uindex = basisFunctionU.index(u);
        int vindex = basisFunctionV.index(v);
        double[] nu = basisFunctionU.eval(uindex,u);
        double[] nv = basisFunctionV.eval(vindex,v);
        double weight=0;
        int i,j;
        for(i=0; i<=udegree; i++){
            for(j=0; j<=vdegree; j++){
                IVec cpt = controlPoints[uindex-udegree+i][vindex-vdegree+j].get();
                double w=1.;
                if(!defaultWeights[uindex-udegree+i][vindex-vdegree+j]) w=((IVec4)cpt).w;
                retval.x += cpt.x*w*nu[i]*nv[j];
                retval.y += cpt.y*w*nu[i]*nv[j];
                retval.z += cpt.z*w*nu[i]*nv[j];
                weight += w*nu[i]*nv[j];
            }
        }
        retval.x/=weight;
        retval.y/=weight;
        retval.z/=weight;
    }
    
    /**
       @param u u coordinates in uv parameter space
       @param v v coordinates in uv parameter space
       @param n length in normal direction in 3D space
    */
    public IVec pt(double u, double v, double n){
	IVec retval = new IVec();
	pt(u,v,retval);
	return retval.add(nrml(u,v).len(n));
    }
    public IVec pt(IDoubleI u, IDoubleI v, IDoubleI n){
	return pt(u.x(),v.x(),n.x());
    }
    public IVec pt(IVec v){ return pt(v.x,v.y,v.z); }
    public IVec pt(IVecI v){ IVec vec=v.get(); return pt(vec.x,vec.y,vec.z); }
    
    
    public IVec utan(IVec2I v){ IVec2 vec=v.get(); return utan(vec.x,vec.y); }
    public IVec utan(IDoubleI u, IDoubleI v){ return utan(u.x(),v.x()); }
    public IVec utan(double u, double v){
	IVec retval = new IVec();
	utan(u,v,retval);
	return retval;
    }
    public void utan(double u, double v, IVec retval){
        if(derivativeFunctionU==null){
            derivativeFunctionU=new IBSplineBasisFunction(basisFunctionU);
            derivativeFunctionU.differentiate();
        }
        int uindex = basisFunctionU.index(u);
        int vindex = basisFunctionV.index(v);
        
        double[] nu = basisFunctionU.eval(uindex, u);
        double[] nv = basisFunctionV.eval(vindex, v);
        
        double[] dnu = derivativeFunctionU.eval(uindex, u);
        
        IVec4 val1 = new IVec4();
        IVec4 val2 = new IVec4();
        
        int i,j;
        for(i=0; i<=udegree; i++){
            for(j=0; j<=vdegree; j++){
                IVec cpt=controlPoints[uindex-udegree+i][vindex-vdegree+j].get();
                
                double w=1.;
                if(!defaultWeights[uindex-udegree+i][vindex-vdegree+j]) w=((IVec4)cpt).w;
                
                val1.x += cpt.x*w*nu[i]*nv[j];
                val1.y += cpt.y*w*nu[i]*nv[j];
                val1.z += cpt.z*w*nu[i]*nv[j];
                val1.w += w*nu[i]*nv[j];
                
                val2.x += cpt.x*w*dnu[i]*nv[j];
                val2.y += cpt.y*w*dnu[i]*nv[j];
                val2.z += cpt.z*w*dnu[i]*nv[j];
                val2.w += w*dnu[i]*nv[j];
            }
        }
        val1.x*=val2.w;
        val1.y*=val2.w;
        val1.z*=val2.w;
        
        val2.x*=val1.w;
        val2.y*=val1.w;
        val2.z*=val1.w;
        
        val1.w*=val1.w;
        
	retval.x = (val2.x-val1.x)/val1.w;
        retval.y = (val2.y-val1.y)/val1.w;
        retval.z = (val2.z-val1.z)/val1.w;    	
    }
        
    public IVec vtan(IVec2I v){ IVec2 vec=v.get(); return vtan(vec.x,vec.y); }
    public IVec vtan(IDoubleI u, IDoubleI v){ return vtan(u.x(),v.x()); }
    public IVec vtan(double u, double v){
	IVec retval = new IVec();
	vtan(u,v,retval);
	return retval;
    }
    public void vtan(double u, double v, IVec retval){
        if(derivativeFunctionV==null){
            derivativeFunctionV=new IBSplineBasisFunction(basisFunctionV);
            derivativeFunctionV.differentiate();
        }
        int uindex = basisFunctionU.index(u);
        int vindex = basisFunctionV.index(v);
        
        double[] nu = basisFunctionU.eval(uindex, u);
        double[] nv = basisFunctionV.eval(vindex, v);
        
        double[] dnv = derivativeFunctionV.eval(vindex, v);
        
        IVec4 val1 = new IVec4();
        IVec4 val2 = new IVec4();
        
        int i,j;
        for(i=0; i<=udegree; i++){
            for(j=0; j<=vdegree; j++){
                IVec cpt=controlPoints[uindex-udegree+i][vindex-vdegree+j].get();
                
                double w=1.;
                if(!defaultWeights[uindex-udegree+i][vindex-vdegree+j]) w=((IVec4)cpt).w;
                
                val1.x += cpt.x*w*nu[i]*nv[j];
                val1.y += cpt.y*w*nu[i]*nv[j];
                val1.z += cpt.z*w*nu[i]*nv[j];
                val1.w += w*nu[i]*nv[j];
                
                val2.x += cpt.x*w*nu[i]*dnv[j];
                val2.y += cpt.y*w*nu[i]*dnv[j];
                val2.z += cpt.z*w*nu[i]*dnv[j];
                val2.w += w*nu[i]*dnv[j];
            }
        }
	val1.x*=val2.w;
        val1.y*=val2.w;
        val1.z*=val2.w;
        
        val2.x*=val1.w;
        val2.y*=val1.w;
        val2.z*=val1.w;
        
        val1.w*=val1.w;
        
        retval.x = (val2.x-val1.x)/val1.w;
        retval.y = (val2.y-val1.y)/val1.w;
        retval.z = (val2.z-val1.z)/val1.w;    	
    }
    
    
    public IVec nml(IVec2I v){ IVec2 vec=v.get(); return nml(vec.x,vec.y); }
    public IVec nml(IDoubleI u, IDoubleI v){ return nml(u.x(),v.x()); }
    public IVec nml(double u, double v){
	IVec retval = new IVec();
	nml(u,v,retval);
	return retval;
    }
    public void nml(double u, double v, IVec retval){
	IVec vt = new IVec();
	utan(u,v,retval);
	vtan(u,v,vt);
	if(retval.len()>0 && vt.len()>0) retval.icross(vt);
	else if(retval.len()>0){ // vtan == 0
	    if(v>0.5){ utan(u,0,vt); retval.icross(vt).neg(); }
	    else{ utan(u,1.,vt); retval.icross(vt); }
	}
	else if(vt.len()>0){ // utan == 0
	    if(u>0.5){ vtan(0,v,retval); retval.icross(vt); }
	    else{ vtan(1.,v,retval); retval.icross(vt).neg(); }
	}
	else IOut.debug(10,"normal is zero"); //
    }
    
    public IVec nrml(IVec2I v){ IVec2 vec=v.get(); return nml(vec.x,vec.y); }
    public IVec nrml(IDoubleI u, IDoubleI v){ return nml(u.x(),v.x()); }
    public IVec nrml(double u, double v){
	IVec retval = new IVec();
	nml(u,v,retval);
	return retval;
    }
    public void nrml(double u, double v, IVec retval){ nml(u,v,retval); }
    
    public IVec normal(IVec2I v){ IVec2 vec=v.get(); return nml(vec.x,vec.y); }
    public IVec normal(IDoubleI u, IDoubleI v){ return nml(u.x(),v.x()); }
    public IVec normal(double u, double v){
	IVec retval = new IVec();
	nml(u,v,retval);
	return retval;
    }
    public void normal(double u, double v, IVec retval){ nml(u,v,retval); }
    
    /** getting control point at i and j */
    public IVecI cp(int i, int j){ return controlPoints[i][j]/*.get()*/; }
    /** getting control point at i and j */
    public IVecI cp(IIntegerI i, IIntegerI j){ return controlPoints[i.x()][j.x()]; }
    
    public IVecI[][] cps(){ return controlPoints; }
    
    
    public IVec corner(int u, int v){
	if(u!=0) u=1;
	if(v!=0) v=1;
	return pt((double)u,(double)v);
    }
    
    public IVec corner(IIntegerI u, IIntegerI v){
	return corner(u.x(),v.x());
    }
    
    public IVec cornerCP(int u, int v){
	if(u!=0) u=ucpNum()-1;
	if(v!=0) v=vcpNum()-1;
	return controlPoints[u][v].get();
    }
    
    public IVecI cornerCP(IIntegerI u, IIntegerI v){
	int ui=0, vi=0;
	if(u.x()!=0) ui=ucpNum()-1;
	if(v.x()!=0) vi=vcpNum()-1;
	return controlPoints[ui][vi];
    }
    
    
    
    // adding CP after instantiated
    
    /** add control point at the end and rebuild the surface
	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    public ISurfaceGeo addUCP(IVecI[] pts){
	if(pts.length != vnum()){
	    IOut.err("vnum ("+vnum()+") and point array size ("+pts.length+") don't match.");
	    return this;
	}
	
	for(int i=0; i<pts.length; i++){
	    if(pts[i]==null || !pts[i].isValid()){
		IOut.err("input pts["+i+"] is invalid. not added");
		return this;
	    }
	}
	
	// in case of first addition after the surface is instantiated with one row.
	if(controlPoints.length==2){
	    boolean allSame=true;
	    for(int i=0; i<controlPoints[0].length && allSame; i++){
		if(controlPoints[0][i].x()!=controlPoints[1][i].x() ||
		   controlPoints[0][i].y()!=controlPoints[1][i].y() ||
		   controlPoints[0][i].z()!=controlPoints[1][i].z()){ // replace the second point with new one
		    allSame=false;
		}
	    }
	    if(allSame){
		if(IConfig.checkDuplicatedControlPoint){
		    checkDuplicatedCP(controlPoints, pts);
		}
		for(int i=0; i<controlPoints[0].length; i++){
		    controlPoints[1][i] = pts[i];
		    defaultWeights[1][i] = !(pts[i] instanceof IVec4I);
		}
		return this;
	    }
	}
	
	if(IConfig.checkDuplicatedControlPoint){
	    checkDuplicatedCP(controlPoints, pts);
	}
	int unum = controlPoints.length;
	int vnum = controlPoints[0].length;
	//check if it started with duplicated 2 points and if so, remove one of them.
	//if( num==2 && controlPoints[0].eq(controlPoints[1])){ num=1; } // isn't taken care of already above?
	
	IVecI[][] controlPoints2 = new IVecI[unum+1][vnum];
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum; j++){ controlPoints2[i][j] = controlPoints[i][j]; }
	}
	
	for(int j=0; j<vnum; j++){ controlPoints2[unum][j] = pts[j]; }
	
	// rebuild knots; because it's adding, ignoring the case of closed curve.
	double[] uknots2  =createKnots(udegree, unum+1);
	IBSplineBasisFunction basisFunctionU2 = new IBSplineBasisFunction(udegree, uknots2);
	boolean[][] defaultWeights2 = new boolean[unum+1][vnum];
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum; j++){ defaultWeights2[i][j] = defaultWeights[i][j]; }
	}
	
	for(int j=0; j<vnum; j++){ defaultWeights2[unum][j] = !(pts[j] instanceof IVec4I); }
	
	controlPoints = controlPoints2;
	uknots = uknots2;
	basisFunctionU = basisFunctionU2;
	defaultWeights = defaultWeights2;
	return this;
    }
    
    
    /** add control point at the end and rebuild the surface
	note that a knots is rebuilt with default equal intervals
	and destroy original knot intervals if variable, like circle.
    */
    public ISurfaceGeo addVCP(IVecI[] pts){
	if(pts.length != unum()){
	    IOut.err("unum ("+unum()+") and point array size ("+pts.length+") don't match.");
	    return this;
	}
	
	for(int i=0; i<pts.length; i++){
	    if(pts[i]==null || !pts[i].isValid()){
		IOut.err("input pts["+i+"] is invalid. not added");
		return this;
	    }
	}
	
	// in case of first addition after the surface is instantiated with one row.
	if(controlPoints[0].length==2){
	    boolean allSame=true;
	    for(int i=0; i<controlPoints.length && allSame; i++){
		if(controlPoints[i][0].x()!=controlPoints[i][1].x() ||
		   controlPoints[i][0].y()!=controlPoints[i][1].y() ||
		   controlPoints[i][0].z()!=controlPoints[i][1].z()){ // replace the second point with new one
		    allSame=false;
		}
	    }
	    if(allSame){
		if(IConfig.checkDuplicatedControlPoint){
		    checkDuplicatedCP(controlPoints, pts);
		}
		for(int i=0; i<controlPoints.length; i++){
		    controlPoints[i][1] = pts[i];
		    defaultWeights[i][1] = !(pts[i] instanceof IVec4I);
		}
		return this;
	    }
	}
	
	if(IConfig.checkDuplicatedControlPoint){
	    checkDuplicatedCP(controlPoints, pts);
	}
	int unum = controlPoints.length;
	int vnum = controlPoints[0].length;
	//check if it started with duplicated 2 points and if so, remove one of them.
	//if( num==2 && controlPoints[0].eq(controlPoints[1])){ num=1; } // isn't taken care of already above?
	
	IVecI[][] controlPoints2 = new IVecI[unum][vnum+1];
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum; j++){ controlPoints2[i][j] = controlPoints[i][j]; }
	}
	
	for(int i=0; i<unum; i++){ controlPoints2[i][vnum] = pts[i]; }
	
	// rebuild knots; because it's adding, ignoring the case of closed curve.
	double[] vknots2  =createKnots(vdegree, vnum+1);
	IBSplineBasisFunction basisFunctionV2 = new IBSplineBasisFunction(vdegree, vknots2);
	boolean[][] defaultWeights2 = new boolean[unum][vnum+1];
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum; j++){ defaultWeights2[i][j] = defaultWeights[i][j]; }
	}
	
	for(int i=0; i<unum; i++){ defaultWeights2[i][vnum] = !(pts[i] instanceof IVec4I); }
	
	controlPoints = controlPoints2;
	vknots = vknots2;
	basisFunctionV = basisFunctionV2;
	defaultWeights = defaultWeights2;
	return this;
    }
    
    
    // not tested yet.
    /** getting edit point at i and j */
    public IVec ep(int i, int j){
	return pt(uknots[i+udegree], vknots[j+vdegree]);
    }
    /** getting edit point at i and j */
    public IVec ep(IIntegerI i, IIntegerI j){
	return pt(uknots[i.x()+udegree], vknots[j.x()+vdegree]);
    }
    
    
    /** mid in UV parameter (u=0.5, v=0.5) point on a surface */
    public IVec mid(){ return pt(0.5, 0.5); }
    
    /** returns center of geometry object */
    public IVec center(){
	int unum = ucpNum();
	int vnum = vcpNum();
	int ucount = unum;
	int vcount = vnum;
        // check if start and end of parameter match with knots[0] and knots[knots.length-1]
	if(vknots[0] != 0.0 || vknots[vknots.length-1] != 1.0){
            // check by cp
	    boolean vclosed = true;
	    for(int u=0; u<unum && vclosed; u++){
		if(!cp(u,0).eq(cp(u, vnum-vdeg()))) vclosed = false;
	    }
	    if(vclosed){ vcount = vnum-vdeg(); }
        }
	else{
	    boolean vclosed = true;
	    for(int u=0; u<=unum && vclosed; u++){ // dividing by cp num?
		if(!pt((double)u/unum, 0.).eq(pt((double)u/unum, 1.))) vclosed = false;
	    }
	    if(vclosed){ vcount = vnum-1; }
	}
	
	// check if start and end of parameter match with knots[0] and knots[knots.length-1]
	if(uknots[0] != 0.0 || uknots[uknots.length-1] != 1.0){
            // check by cp
	    boolean uclosed = true;
	    for(int v=0; v<vnum && uclosed; v++){
		if(!cp(0,v).eq(cp(unum-udeg(),v))) uclosed = false;
	    }
	    if(uclosed){ ucount = unum-udeg(); }
        }
	else{
	    boolean uclosed = true;
	    for(int v=0; v<=vnum && uclosed; v++){ // dividing by cp num?
		if(!pt(0., (double)v/vnum).eq(pt(1., (double)v/vnum))) uclosed = false;
	    }
	    if(uclosed){ ucount = unum-1; }
	}
	
	IVec cnt = new IVec();
	for(int i=0; i<ucount; i++){ for(int j=0; j<vcount; j++){ cnt.add(cp(i,j)); } }
	cnt.div(ucount*vcount);
	return cnt;
    }
    
    
    /** approximate invert projection from 3D location to interanl UV parameter (closest point on surface) */
    synchronized public IVec2 uv(IVecI pt){
	if(uvSearchCache==null){ uvSearchCache = new ISurfaceCache(this); }
	return uvSearchCache.uv(pt.get());
    }
/** approximate invert projection from 2D location to interanl UV parameter (closest point on surface) */
    synchronized public IVec2 uv(IVec2I pt){
	if(uvSearchCache==null){ uvSearchCache = new ISurfaceCache(this); }
	return uvSearchCache.uv(pt.get());
    }
    
    
    
    /** find approximately closest point on a surface */
    public IVec closePt(IVecI pt){ return pt(uv(pt)); }
    
    /** find approximately closest point on a surface on 2D */
    public IVec closePt(IVec2I pt){return pt(uv(pt)); }
    
    /** distance to the closest point on a surface */
    public double dist(IVecI pt){ return closePt(pt).dist(pt); }
    /** distance to the closest point on a surface on 2D*/
    public double dist(IVec2I pt){ return closePt(pt).to2d().dist(pt); }
    
    
    
    
    public double uknot(int i){ return uknots[i]; }
    public IDouble uknot(IIntegerI i){ return new IDouble(uknots[i.x()]); }
    public double vknot(int i){ return vknots[i]; }
    public IDouble vknot(IIntegerI i){ return new IDouble(vknots[i.x()]); }
    
    public double[] uknots(){ return uknots; }
    public double[] uknots(ISwitchE e){ return uknots(); }
    public IDouble[] uknots(ISwitchR r){
	IDouble[] uk = new IDouble[uknots.length];
	for(int i=0; i<uknots.length; i++) uk[i] = new IDouble(uknots[i]);
	return uk;
    }
    public double[] vknots(){ return vknots; }
    public double[] vknots(ISwitchE e){ return vknots(); }
    public IDouble[] vknots(ISwitchR r){
	IDouble[] vk = new IDouble[vknots.length];
	for(int i=0; i<vknots.length; i++) vk[i] = new IDouble(vknots[i]);
	return vk;
    }
    
    public int uknotNum(){ return uknots.length; }
    public int vknotNum(){ return vknots.length; }
    //public IInteger uknotNumR(){ return new IInteger(uknots.length); }
    //public IInteger vknotNumR(){ return new IInteger(vknots.length); }
    public int uknotNum(ISwitchE e){ return uknotNum(); }
    public int vknotNum(ISwitchE e){ return vknotNum(); }
    public IInteger uknotNum(ISwitchR r){ return new IInteger(uknotNum()); }
    public IInteger vknotNum(ISwitchR r){ return new IInteger(vknotNum()); }
    
    public boolean isRational(){
	if(defaultWeights==null){
	    IOut.err("defaultWeights is null"); return false;
	}
	for(int i=0; i<defaultWeights.length; i++)
	    for(int j=0; j<defaultWeights[i].length; j++)
		if(!defaultWeights[i][j]) return true;
	return false;
    }
    public boolean isRational(ISwitchE e){ return isRational(); }
    public IBool isRational(ISwitchR r){ return new IBool(isRational()); }
        
    public int udeg(){ return udegree; }
    public int vdeg(){ return vdegree; }
    //public IInteger udegR(){ return new IInteger(udegree); }
    //public IInteger vdegR(){ return new IInteger(vdegree); }
    public int udeg(ISwitchE e){ return udeg(); }
    public int vdeg(ISwitchE e){ return vdeg(); }
    public IInteger udeg(ISwitchR r){ return new IInteger(udeg()); }
    public IInteger vdeg(ISwitchR r){ return new IInteger(vdeg()); }
    
    //public IInteger num(){ return new IInteger(controlPoints.length); }
    public int unum(){ return controlPoints.length; }
    public int vnum(){ return controlPoints[0].length; }
    //public IInteger unumR(){ return new IInteger(controlPoints.length); }
    //public IInteger vnumR(){ return new IInteger(controlPoints[0].length); }
    public int unum(ISwitchE e){ return unum(); }
    public int vnum(ISwitchE e){ return vnum(); }
    public IInteger unum(ISwitchR r){ return new IInteger(unum()); }
    public IInteger vnum(ISwitchR r){ return new IInteger(vnum()); }
    
    //public IDouble len(){ return null; }
    
    public int ucpNum(){ return unum(); } // equals to unum()
    public int vcpNum(){ return vnum(); } // equals to vnum()
    //public IInteger ucpNumR(){ return unumR(); } // equals to unum()
    //public IInteger vcpNumR(){ return vnumR(); } // equals to unum()
    public int ucpNum(ISwitchE e){ return ucpNum(); }
    public int vcpNum(ISwitchE e){ return vcpNum(); }
    public IInteger ucpNum(ISwitchR r){ return new IInteger(ucpNum()); }
    public IInteger vcpNum(ISwitchR r){ return new IInteger(vcpNum()); }
    
    public int uepNum(){ return uknots.length-2*udegree; }
    public int vepNum(){ return vknots.length-2*vdegree; }
    //public IInteger uepNumR(){ return new IInteger(uepNum()); }
    //public IInteger vepNumR(){ return new IInteger(vepNum()); }
    public int uepNum(ISwitchE e){ return uepNum(); }
    public int vepNum(ISwitchE e){ return vepNum(); }
    public IInteger uepNum(ISwitchR r){ return new IInteger(uepNum()); }
    public IInteger vepNum(ISwitchR r){ return new IInteger(vepNum()); }
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double u(int epIdx, double epFraction){
        if(epFraction>=0) return uknots[epIdx+udegree] +
            (uknots[epIdx+udegree+1]-uknots[epIdx+udegree])*epFraction;
        return uknots[epIdx+udegree] +
            (uknots[epIdx+udegree]-uknots[epIdx+udegree-1])*epFraction;     
    }
    public IDouble u(IIntegerI epIdx, IDoubleI epFraction){
	return new IDouble(u(epIdx.x(),epFraction.x()));
    }
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double v(int epIdx, double epFraction){
        if(epFraction>=0) return vknots[epIdx+vdegree] +
            (vknots[epIdx+vdegree+1]-vknots[epIdx+vdegree])*epFraction;
        return vknots[epIdx+vdegree] +
            (vknots[epIdx+vdegree]-vknots[epIdx+vdegree-1])*epFraction;     
    }
    public IDouble v(IIntegerI epIdx, IDoubleI epFraction){
	return new IDouble(v(epIdx.x(),epFraction.x()));
    }
    
    public double ustart(){ return ustart; }
    public double uend(){ return uend; }
    public double vstart(){ return vstart; }
    public double vend(){ return vend; }
    //public IDouble ustartR(){ return new IDouble(ustart); }
    //public IDouble uendR(){ return new IDouble(uend); }
    //public IDouble vstartR(){ return new IDouble(vstart); }
    //public IDouble vendR(){ return new IDouble(vend); }
    public double ustart(ISwitchE e){ return ustart(); }
    public double uend(ISwitchE e){ return uend(); }
    public double vstart(ISwitchE e){ return vstart(); }
    public double vend(ISwitchE e){ return vend(); }
    public IDouble ustart(ISwitchR r){ return new IDouble(ustart()); }
    public IDouble uend(ISwitchR r){ return new IDouble(uend()); }
    public IDouble vstart(ISwitchR r){ return new IDouble(vstart()); }
    public IDouble vend(ISwitchR r){ return new IDouble(vend()); }
    
    
    /** reverse U parameter. it changes internal parameter without creating a new instance.*/
    public ISurfaceGeo revU(){
        synchronized(IG.lock){
            IVecI[][] cpts2 = new IVecI[controlPoints.length][controlPoints[0].length];
            
            for(int i=0; i<controlPoints.length; i++)
		for(int j=0; j<controlPoints[i].length; j++)
		    cpts2[i][j] = controlPoints[controlPoints.length-1-i][j];
	    
            double[] uknots2 = new double[uknots.length];
            for(int i=0; i<uknots.length; i++)
                uknots2[i] = 1.0 - uknots[uknots.length-1-i]; // [0-1] to [1-0]
            
            boolean[][] defaultWeights2 = new boolean[defaultWeights.length][defaultWeights[0].length];
            for(int i=0; i<defaultWeights.length; i++)
		for(int j=0; j<defaultWeights[i].length; j++)
		    defaultWeights2[i][j] = defaultWeights[defaultWeights.length-1-i][j];
	    
	    controlPoints = cpts2;
            uknots = uknots2;
            defaultWeights = defaultWeights2;
            if(ustart!=0. || uend!=1.){
                double ustart2 = -uend;
                double uend2 = -ustart;
                ustart = ustart2;
                uend = uend2;
            }
            basisFunctionU = new IBSplineBasisFunction(udegree, uknots);
            derivativeFunctionU = null;
        }
        return this;
    }
    
    /** reverse V parameter. it changes internal parameter without creating a new instance.*/
    public ISurfaceGeo revV(){
        synchronized(IG.lock){
            IVecI[][] cpts2 = new IVecI[controlPoints.length][controlPoints[0].length];
            
            for(int i=0; i<controlPoints.length; i++)
		for(int j=0; j<controlPoints[i].length; j++)
		    cpts2[i][j] = controlPoints[i][controlPoints[i].length-1-j];
	    
            double[] vknots2 = new double[vknots.length];
            for(int i=0; i<vknots.length; i++)
                vknots2[i] = 1.0 - vknots[vknots.length-1-i]; // [0-1] to [1-0]
	    
            boolean[][] defaultWeights2 = new boolean[defaultWeights.length][defaultWeights[0].length];
            for(int i=0; i<defaultWeights.length; i++)
		for(int j=0; j<defaultWeights[i].length; j++)
		    defaultWeights2[i][j] = defaultWeights[i][defaultWeights[i].length-1-j];
	    
            controlPoints = cpts2;
            vknots = vknots2;
            defaultWeights = defaultWeights2;
            if(vstart!=0. || vend!=1.){
                double vstart2 = -vend;
                double vend2 = -vstart;
                vstart = vstart2;
                vend = vend2;
            }
            basisFunctionV = new IBSplineBasisFunction(vdegree, vknots);
            derivativeFunctionV = null;
        }
        return this;
    }
    
    /** reverse U and V parameter at the same time*/
    public ISurfaceGeo revUV(){
        synchronized(IG.lock){
            IVecI[][] cpts2 = new IVecI[controlPoints.length][controlPoints[0].length];
            
            for(int i=0; i<controlPoints.length; i++)
		for(int j=0; j<controlPoints[i].length; j++)
		    cpts2[i][j] =
			controlPoints[controlPoints.length-1-i][controlPoints[i].length-1-j];
	    
            double[] uknots2 = new double[uknots.length];
            for(int i=0; i<uknots.length; i++)
                uknots2[i] = 1.0 - uknots[uknots.length-1-i]; // [0-1] to [1-0]
	    
            double[] vknots2 = new double[vknots.length];
            for(int i=0; i<vknots.length; i++)
                vknots2[i] = 1.0 - vknots[vknots.length-1-i]; // [0-1] to [1-0]
	    
            boolean[][] defaultWeights2 = new boolean[defaultWeights.length][defaultWeights[0].length];
            for(int i=0; i<defaultWeights.length; i++)
		for(int j=0; j<defaultWeights[i].length; j++)
		    defaultWeights2[i][j] =
			defaultWeights[defaultWeights.length-1-i][defaultWeights[i].length-1-j];
	    
            controlPoints = cpts2;
	    uknots = uknots2;
            vknots = vknots2;
            defaultWeights = defaultWeights2;
            if(ustart!=0. || uend!=1.){
                double ustart2 = -uend;
                double uend2 = -ustart;
                ustart = ustart2;
                uend = uend2;
            }
            if(vstart!=0. || vend!=1.){
                double vstart2 = -vend;
                double vend2 = -vstart;
                vstart = vstart2;
                vend = vend2;
            }
            basisFunctionU = new IBSplineBasisFunction(udegree, uknots);
            derivativeFunctionU = null;
            basisFunctionV = new IBSplineBasisFunction(vdegree, vknots);
            derivativeFunctionV = null;
        }
        return this;
    }
    
    /** reverse normal direction. just reversing V direction. the normal direction is not
	independent from U and V direction. */
    public ISurfaceGeo revN(){ return revV(); }

    
    /** alias of revU() */
    public ISurfaceGeo flipU(){ return revU(); }
    /** alias of revV() */
    public ISurfaceGeo flipV(){ return revV(); }
    /** alias of revUV() */
    public ISurfaceGeo flipUV(){ return revUV(); }
    /** alias of flipN() */
    public ISurfaceGeo flipN(){ return revN(); }
    
    
    /** swap U and V parameter */
    public ISurfaceGeo swapUV(){
        synchronized(IG.lock){
            IVecI[][] cpts2 = new IVecI[controlPoints[0].length][controlPoints.length];
            
            for(int i=0; i<controlPoints.length; i++)
		for(int j=0; j<controlPoints[i].length; j++)
		    cpts2[j][i] = controlPoints[i][j];
	    
            double[] uknots2 = vknots;
            double[] vknots2 = uknots;
	    
            boolean[][] defaultWeights2 = new boolean[defaultWeights[0].length][defaultWeights.length];
            for(int i=0; i<defaultWeights.length; i++)
		for(int j=0; j<defaultWeights[i].length; j++)
		    defaultWeights2[j][i] = defaultWeights[i][j];
	    
            controlPoints = cpts2;
	    uknots = uknots2;
            vknots = vknots2;
            defaultWeights = defaultWeights2;
	    
	    double tmp;
	    tmp = ustart;
	    ustart = vstart;
	    vstart = tmp;
	    
	    tmp = uend;
	    uend = vend;
	    vend = tmp;
	    
	    IBSplineBasisFunction tmpFunc;
	    tmpFunc = basisFunctionU;
	    basisFunctionU = basisFunctionV;
	    basisFunctionV = tmpFunc;
	    
	    tmpFunc = derivativeFunctionU;
	    derivativeFunctionU = derivativeFunctionV;
	    derivativeFunctionV = tmpFunc;
        }
        return this;
    }
    
    
    
    public ISurfaceGeo clearInnerTrim(){
	for(int i=0; innerTrimLoop!=null &&i<innerTrimLoop.size(); i++){
	    ArrayList<ITrimCurve> loop=innerTrimLoop.get(i);
	    //for(int j=0; loop!=null&&j<loop.size(); j++) loop.get(j).del();
	    loop.clear();
	}
	if(innerTrimLoop!=null) innerTrimLoop.clear();
	return this;
    }
    public ISurfaceGeo clearOuterTrim(){
	for(int i=0; outerTrimLoop!=null &&i<outerTrimLoop.size(); i++){
	    ArrayList<ITrimCurve> loop=outerTrimLoop.get(i);
	    //for(int j=0; loop!=null&&j<loop.size(); j++) loop.get(j).del();
	    loop.clear();
	}
	if(outerTrimLoop!=null) outerTrimLoop.clear();
	return this;
    }
    public ISurfaceGeo clearTrim(){
	clearInnerTrim();
	clearOuterTrim();
	return this;
    }
    /*
    public void setInnerTrim(ArrayList<ArrayList<ITrimCurve>> loops){
	clearInnerTrim();
	for(int i=0;loops!=null&&i<loops.size();i++) addInnerTrim(loops.get(i));
    }
    
    public void addInnerTrim(ArrayList<ITrimCurve> loops){
	for(int i=0;loops!=null&&i<loops.size();i++) addInnerTrim(loops.get(i));
	closeInnerTrim();
    }
    */
    
    
    static public boolean isTrimLoopInsideBoundary(ITrimCurveI[] crv,
						   double u1, double v1,
						   double u2, double v2){
	boolean anyOutside=false;
	for(int i=0; i<crv.length; i++)
	    if(!isTrimCurveCPInsideBoundary(crv[i],u1,v1,u2,v2)) anyOutside=true;
	if(!anyOutside) return true;
	// outer/inner doesn't matter
	ITrimLoopGraphic loop = new ITrimLoopGraphic(crv,true,IConfig.trimSegmentResolution);
	IPolyline2D polyline = loop.getPolyline2D();
	IVec2 min = polyline.getMinBoundary();
	IVec2 max = polyline.getMaxBoundary();
	if(min.x < u1 || min.y < v1 || max.x > u2 || max.y > v2){
	    IOut.err("trim curve is outside boundary : min = "+min+", max = "+max);
	    return false;
	}
	return true;
    }
    
    static public boolean isTrimCurveInsideBoundary(ITrimCurveI crv,
						    double u1, double v1,
						    double u2, double v2){
	if(!isTrimCurveCPInsideBoundary(crv,u1,v1,u2,v2)) return false;
	ITrimCurveGraphic crvg = new ITrimCurveGraphic(crv);
	crvg.setup2D(IConfig.trimSegmentResolution);
	IPolyline2D polyline = crvg.polyline2;
	IVec2 min = polyline.getMinBoundary();
	IVec2 max = polyline.getMaxBoundary();
	if(min.x < u1 || min.y < v1 || max.x > u2 || max.y > v2){
	    IOut.err("trim curve is outside boundary : min = "+min+", max = "+max);
	    return false;
	}
	return true;
    }
    
    static public boolean isTrimCurveCPInsideBoundary(ITrimCurveI crv, double u1, double v1,
						      double u2, double v2){
	for(int i=0; i<crv.num(); i++){
	    if(crv.cp(i).get().x < u1 || crv.cp(i).get().x > u2 ||
	       crv.cp(i).get().y < v1 || crv.cp(i).get().y > v2 ){
		//IOut.err("trim curve CP is outside boundary : "+crv.cp(i).get());
		return false;
	    }
	}
	return true;
    }
    
    static public boolean isTrimLoopClosed(ITrimCurveI[] crv){
	//IOut.err("crv[0].start2d() = "+crv[0].start2d());
	//IOut.err("crv[crv.length-1].end2d() = "+crv[crv.length-1].end2d()); 
	return crv[0].start2d().eq(crv[crv.length-1].end2d());
    }
    
    public boolean checkTrimLoop(ITrimCurve[] loop){
	// check closed, been inside boundary (0-1), loop direction? etc
	if(loop==null){
	    IOut.err("trim loop is null");
	    return false;
	}
	if(loop.length==0){
	    IOut.err("trim loop curve number is zero");
	    return false;
	}
	if(!isTrimLoopInsideBoundary(loop,0.,0.,1.,1.)){
	    IOut.err("trim loop is outside boundary of surface");
	    return false;
	}
	if(!isTrimLoopClosed(loop)){
	    IOut.err("trim loop is not closed");
	    return false;
	}
	return true;
    }
    
    public boolean checkTrimLoop(ITrimCurve loop){
	if(loop==null){
	    IOut.err("trim loop is null");
	    return false;
	}
	if(!isTrimLoopInsideBoundary(new ITrimCurve[]{loop},0.,0.,1.,1.)){
	    IOut.err("trim loop is outside boundary of surface");
	    return false;
	}
	if(!loop.isClosed()){
	    IOut.err("trim loop is not closed");
	    return false;
	}
	return true; 
    }
    public boolean checkTrim(ITrimCurve curve){
	if(curve==null){
	    IOut.err("trim loop is null");
	    return false;
	}
	if(!isTrimCurveInsideBoundary(curve,0.,0.,1.,1.)){
	    IOut.err("trim loop is outside boundary of surface");
	    return false;
	}
	
	return true; 
    }
    
    
    public ISurfaceGeo addDefaultOuterTrimLoop(){
	if(hasOuterTrim()){
	    IOut.err("the surface already has outer trim");
	    return this;
	}
	
	ITrimCurve[] loop = new ITrimCurve[4];
	loop[0] = new ITrimCurve(new IVec(0.,0.,0.),new IVec(1.,0.,0.));
	loop[1] = new ITrimCurve(new IVec(1.,0.,0.),new IVec(1.,1.,0.));
	loop[2] = new ITrimCurve(new IVec(1.,1.,0.),new IVec(0.,1.,0.));
	loop[3] = new ITrimCurve(new IVec(0.,1.,0.),new IVec(0.,0.,0.));
	addOuterTrimLoop(loop);
	return this;
    }
    
    
    public ISurfaceGeo addInnerTrimLoop(ITrimCurveI[] loop){
	ITrimCurve[] trimLoop = new ITrimCurve[loop.length];
	for(int i=0; i<loop.length; i++) trimLoop[i] = loop[i].get();
	return addInnerTrimLoop(trimLoop);
    }
    public ISurfaceGeo addInnerTrimLoop(ITrimCurve[] loop){
	for(int i=0; loop!=null&&i<loop.length; i++) loop[i].surface(this);
	
	if(!checkTrimLoop(loop)) return this;
	if(innerTrimLoop==null) innerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	ArrayList<ITrimCurve> l = new ArrayList<ITrimCurve>();
	//for(int i=0;loop!=null&&i<loop.length;i++) l.add(loop[i].surface(this));
	for(int i=0;loop!=null&&i<loop.length;i++) l.add(loop[i]);
	innerTrimLoop.add(l);
	closeInnerTrim();
	return this;
    }
    
    public ISurfaceGeo addInnerTrimLoop(ITrimCurveI loop){ return addInnerTrimLoop(loop.get()); }
    
    public ISurfaceGeo addInnerTrimLoop(ITrimCurve loop){
	if(loop!=null) loop.surface(this);
	
	if(!checkTrimLoop(loop)) return this;
	if(innerTrimLoop==null) innerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	ArrayList<ITrimCurve> l = new ArrayList<ITrimCurve>();
	if(!loop.isClosed()){ IOut.err("trim loop is not closed"); } 
	//l.add(loop.surface(this));
	l.add(loop);
	innerTrimLoop.add(l);
	closeInnerTrim();
	return this;
    }
    
    public ISurfaceGeo addInnerTrimLoop(ICurveI[] trim){ return addInnerTrimLoop(trim,true); }
    
    public ISurfaceGeo addInnerTrimLoop(ICurveI[] trim, boolean deleteInput){
	if(trim==null || trim.length==0){ IOut.err("no trim input"); return this; }
	ITrimCurve[] trimCrvs = new ITrimCurve[trim.length];
	for(int i=0; i<trim.length; i++){
	    trimCrvs[i] = new ITrimCurve(trim[i]).surface(this);
	    if(deleteInput && trim[i] instanceof IObject) ((IObject)trim[i]).del();
	}
	return addInnerTrimLoop(trimCrvs);
    }
    
    public ISurfaceGeo addInnerTrimLoop(ICurveI trim){ return addInnerTrimLoop(trim,true); }
    
    public ISurfaceGeo addInnerTrimLoop(ICurveI trim, boolean deleteInput){
	if(trim==null){ IOut.err("no trim input"); return this; }
	ITrimCurve trimCrv =new ITrimCurve(trim).surface(this);
	if(deleteInput && trim instanceof IObject) ((IObject)trim).del();
	return addInnerTrimLoop(trimCrv);
    }
    
    public ISurfaceGeo addInnerTrim(ITrimCurve trimCrv){
	if(trimCrv!=null) trimCrv.surface(this);
	
	if(!checkTrim(trimCrv)) return this;
	
	if(innerTrimLoop==null){
	    innerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	    innerTrimLoop.add(new ArrayList<ITrimCurve>());
	}
	else if(innerTrimClosed){
	    innerTrimLoop.add(new ArrayList<ITrimCurve>());
	    innerTrimClosed=false;
	}
	//innerTrimLoop.get(innerTrimLoop.size()-1).add(trimCrv.surface(this));
	innerTrimLoop.get(innerTrimLoop.size()-1).add(trimCrv);
	return this;
    }
    
    
    public ISurfaceGeo closeInnerTrim(){ innerTrimClosed=true; return this; }
    
    
    /*
    public void setOuterTrim(ArrayList<ArrayList<ITrimCurve>> loops){
	clearOuterTrim();
	for(int i=0;loops!=null&&i<loops.size();i++) addOuterTrim(loops.get(i));
    }
    
    public void addOuterTrim(ArrayList<ITrimCurve> loops){
	for(int i=0;loops!=null&&i<loops.size();i++) addOuterTrim(loops.get(i));
	closeOuterTrim();
    }
    */
    
    public ISurfaceGeo addOuterTrimLoop(ITrimCurveI[] loop){
	ITrimCurve[] trimLoop = new ITrimCurve[loop.length];
	for(int i=0; i<loop.length; i++) trimLoop[i] = loop[i].get();
	return addOuterTrimLoop(trimLoop);
    }
    public ISurfaceGeo addOuterTrimLoop(ITrimCurve[] loop){
	for(int i=0; loop!=null&&i<loop.length; i++) loop[i].surface(this);
	
	if(!checkTrimLoop(loop)) return this;
	if(outerTrimLoop==null) outerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	ArrayList<ITrimCurve> l = new ArrayList<ITrimCurve>();
	//for(int i=0;loop!=null&&i<loop.length;i++) l.add(loop[i].surface(this));
	for(int i=0;loop!=null&&i<loop.length;i++) l.add(loop[i]);
	outerTrimLoop.add(l);
	closeOuterTrim();
	return this;
    }
    
    public ISurfaceGeo addOuterTrimLoop(ITrimCurveI loop){ return addOuterTrimLoop(loop.get()); }
    
    public ISurfaceGeo addOuterTrimLoop(ITrimCurve loop){
	if(loop!=null) loop.surface(this);
	
	if(!checkTrimLoop(loop)) return this;
	if(outerTrimLoop==null) outerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	ArrayList<ITrimCurve> l = new ArrayList<ITrimCurve>();
	if(!loop.isClosed()){ IOut.err("trim loop is not closed"); } 
	//l.add(loop.surface(this));
	l.add(loop);
	outerTrimLoop.add(l);
	closeOuterTrim();
	return this;
    }
    
    public ISurfaceGeo addOuterTrimLoop(ICurveI[] trim){ return addOuterTrimLoop(trim,true); }
    
    public ISurfaceGeo addOuterTrimLoop(ICurveI[] trim, boolean deleteInput){
	if(trim==null || trim.length==0){ IOut.err("no trim input"); return this; }
	ITrimCurve[] trimCrvs = new ITrimCurve[trim.length];
	for(int i=0; i<trim.length; i++){
	    trimCrvs[i] = new ITrimCurve(trim[i]).surface(this);
	    if(deleteInput && trim[i] instanceof IObject) ((IObject)trim[i]).del();
	}
	return addOuterTrimLoop(trimCrvs);
    }
    
    public ISurfaceGeo addOuterTrimLoop(ICurveI trim){ return addOuterTrimLoop(trim,true); }
    
    public ISurfaceGeo addOuterTrimLoop(ICurveI trim, boolean deleteInput){
	if(trim==null){ IOut.err("no trim input"); return this; }
	ITrimCurve trimCrv =new ITrimCurve(trim).surface(this);
	if(deleteInput && trim instanceof IObject) ((IObject)trim).del();
	return addOuterTrimLoop(trimCrv);
    }
    
    public ISurfaceGeo addOuterTrim(ITrimCurve trimCrv){
	if(trimCrv!=null) trimCrv.surface(this);
	
	if(!checkTrim(trimCrv)) return this;
	if(outerTrimLoop==null){
	    outerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	    outerTrimLoop.add(new ArrayList<ITrimCurve>());
	}
	else if(outerTrimClosed){
	    outerTrimLoop.add(new ArrayList<ITrimCurve>());
	    outerTrimClosed=false;
	}
	//outerTrimLoop.get(outerTrimLoop.size()-1).add(trimCrv.surface(this));
	outerTrimLoop.get(outerTrimLoop.size()-1).add(trimCrv);
	return this;
    }
    
    public ISurfaceGeo closeOuterTrim(){ outerTrimClosed=true; return this; }
    
    public boolean hasTrim(){ return hasInnerTrim()||hasOuterTrim(); }
    public boolean hasInnerTrim(){
	return innerTrimLoop!=null && innerTrimLoop.size()>0;
    }
    public boolean hasOuterTrim(){
	return outerTrimLoop!=null && outerTrimLoop.size()>0;
    }
    //public IBool hasTrimR(){ return new IBool(hasTrim()); }
    //public IBool hasInnerTrimR(){ return new IBool(hasInnerTrim()); }
    //public IBool hasOuterTrimR(){ return new IBool(hasOuterTrim()); }
    public boolean hasTrim(ISwitchE e){ return hasTrim(); }
    public boolean hasInnerTrim(ISwitchE e){ return hasInnerTrim(); }
    public boolean hasOuterTrim(ISwitchE e){ return hasOuterTrim(); }
    public IBool hasTrim(ISwitchR r){ return new IBool(hasTrim()); }
    public IBool hasInnerTrim(ISwitchR r){ return new IBool(hasInnerTrim()); }
    public IBool hasOuterTrim(ISwitchR r){ return new IBool(hasOuterTrim()); }
    
    public int innerTrimLoopNum(){
	if(innerTrimLoop==null) return 0;
	return innerTrimLoop.size();
    }
    public int outerTrimLoopNum(){
	if(outerTrimLoop==null) return 0;
	return outerTrimLoop.size();
    }
    //public IInteger innerTrimLoopNumR(){ return new IInteger(innerTrimLoopNum()); }
    //public IInteger outerTrimLoopNumR(){ return new IInteger(outerTrimLoopNum()); }
    public int innerTrimLoopNum(ISwitchE e){ return innerTrimLoopNum(); }
    public int outerTrimLoopNum(ISwitchE e){ return outerTrimLoopNum(); }
    public IInteger innerTrimLoopNum(ISwitchR r){ return new IInteger(innerTrimLoopNum()); }
    public IInteger outerTrimLoopNum(ISwitchR r){ return new IInteger(outerTrimLoopNum()); }
    
    
    public int innerTrimNum(int i){
	if(innerTrimLoop==null) return 0;
	return innerTrimLoop.get(i).size();
    }
    public IInteger innerTrimNum(IIntegerI i){ return new IInteger(innerTrimNum(i.x())); }
    
    public int outerTrimNum(int i){
	if(outerTrimLoop==null) return 0;
	return outerTrimLoop.get(i).size();
    }
    public IInteger outerTrimNum(IIntegerI i){ return new IInteger(outerTrimNum(i.x())); }
    
    public ITrimCurveI[] innerTrimLoop(int i){
	return innerTrimLoop.get(i).toArray(new ITrimCurve[innerTrimLoop.get(i).size()]);
    }
    public ITrimCurveI[] innerTrimLoop(IIntegerI i){ return innerTrimLoop(i.x()); }
    
    public ITrimCurveI[] outerTrimLoop(int i){
	return outerTrimLoop.get(i).toArray(new ITrimCurve[outerTrimLoop.get(i).size()]);
    }
    public ITrimCurveI[] outerTrimLoop(IIntegerI i){ return outerTrimLoop(i.x()); }
    
    public ITrimCurveI innerTrim(int i, int j){
	return innerTrimLoop.get(i).get(j);
    }
    public ITrimCurveI innerTrim(IIntegerI i, IIntegerI j){ return innerTrim(i.x(),j.x()); }
    
    public ITrimCurveI outerTrim(int i, int j){
	return outerTrimLoop.get(i).get(j);
    }
    public ITrimCurveI outerTrim(IIntegerI i, IIntegerI j){ return outerTrim(i.x(),j.x()); }
    
    
    /**
       default trim is rectangular outer trim at the exact boundary of untrimmed surface
    */
    public boolean hasDefaultTrim(){
	if(outerTrimLoopNum()!=1) return false;
	
	IVec[] cpts = new IVec[4];
	// in case of 4 lines
	if(outerTrimLoop.get(0).size()==4){
	    for(int i=0; i<outerTrimLoop.get(0).size(); i++){
		// line?
		if(outerTrimLoop.get(0).get(i).deg()!=1 ||
		   outerTrimLoop.get(0).get(i).num()!=2 ) return false;
		// end points connected?
		if(!outerTrimLoop.get(0).get(i).cp(1).get().eq
		   (outerTrimLoop.get(0).get((i+1)%4).cp(0))) return false;
		cpts[i] = outerTrimLoop.get(0).get(i).cp(0).get();
	    }
	}
	else if(outerTrimLoop.get(0).size()==1){
	    if(outerTrimLoop.get(0).get(0).deg()!=1 ||
	       outerTrimLoop.get(0).get(0).num()!=5 ) return false;
	    for(int i=0; i<4; i++) cpts[i] = outerTrimLoop.get(0).get(0).cp(i).get();
	}
	else return false;
	
	final double resolution = 0.0; // !!!
	
	if(IVec.isArrayEqual(cpts, new IVec[]{ new IVec(0.,0.,0.),
					       new IVec(1.,0.,0.),
					       new IVec(1.,1.,0.),
					       new IVec(0.,1.,0.) },
			     true, true, resolution)) return true;
	return false;
    }
    //public IBool hasDefaultTrimR(){ return new IBool(hasDefaultTrim()); }
    public boolean hasDefaultTrim(ISwitchE e){ return hasDefaultTrim(); }
    public IBool hasDefaultTrim(ISwitchR r){ return new IBool(hasDefaultTrim()); }
    
    /** is the surface planar and flat */
    public boolean isFlat(){
	IVecI planePt = corner(0,0);
	IVecI planeDir = planePt.get().getNormal(corner(1,0),corner(0,1));
	
	if(!corner(1,1).get().isOnPlane(planeDir,planePt)) return false;
	for(int i=1; i<ucpNum()-1; i++)
	    for(int j=1; j<vcpNum()-1; j++)
		if(!cp(i,j).get().isOnPlane(planeDir,planePt)) return false;
	
	return true;
    }
    //public IBool isFlatR(){ return new IBool(isFlat()); }
    public boolean isFlat(ISwitchE e){ return isFlat(); }
    public IBool isFlat(ISwitchR r){ return new IBool(isFlat()); }
    
    /** is the surface closed in u direction */
    public boolean isUClosed(){
	int vnum = vcpNum();
        // check if start and end of parameter match with knots[0] and knots[knots.length-1]
	if(uknots[0] != 0.0 || uknots[uknots.length-1] != 1.0){
            // check by cp
	    for(int v=0; v<vnum; v++){
		//if(!cp(0,v).eq(cp(ucpNum()-1, v))) return false;
		if(!cp(0,v).eq(cp(ucpNum()-udegree, v))) return false; // added 20120310
	    }
	    return true;
        }
        // check by pt
	for(int v=0; v<=vnum; v++){ // dividing by cp num?
	    if(!pt(0., (double)v/vnum).eq(pt(1.,(double)v/vnum))) return false;
	}
        return true;
    }
    
    public boolean isUClosed(ISwitchE e){ return isUClosed(); }
    public IBool isUClosed(ISwitchR r){ return new IBool(isUClosed()); }
    
    /** is the surface closed in v direction */
    public boolean isVClosed(){
	int unum = ucpNum();
        // check if start and end of parameter match with knots[0] and knots[knots.length-1]
	if(vknots[0] != 0.0 || vknots[vknots.length-1] != 1.0){
            // check by cp
	    for(int u=0; u<unum; u++){
		//if(!cp(u,0).eq(cp(u, vcpNum()-1))) return false;
		if(!cp(u,0).eq(cp(u, vcpNum()-vdegree))) return false; // added 20120310
	    }
	    return true;
        }
        // check by pt
	for(int u=0; u<=unum; u++){ // dividing by cp num?
	    if(!pt((double)u/unum, 0.).eq(pt((double)u/unum, 1.))) return false;
	}
        return true;
    }
    public boolean isVClosed(ISwitchE e){ return isVClosed(); }
    public IBool isVClosed(ISwitchR r){ return new IBool(isVClosed()); }
    
    
    public boolean isInsideTrim(IVec2I v){ return isInsideTrim(v.get()); }
    public boolean isInsideTrim(double u, double v){ return isInsideTrim(new IVec2(u,v)); }
    
    /** check if a u-v point is inside trim curves. the condition where out-trim loop is inside in-trim loop is ignored and it'd be false. */
    public boolean isInsideTrim(IVec2 v){
	if(trimCache==null){
	    if(!hasTrim()){
		if(v.x<0. || v.x>1. || v.y<0. || v.y>1.) return false;
		return true;
	    }
	    trimCache = new ITrimCache(this);
	}
	return trimCache.isInside(v);
    }
    
    
    synchronized public ISurfaceGeo updateCache(){ uvSearchCache = new ISurfaceCache(this); return this; }
    
    /**********************************************************************************
     * transformation methods; API of ITransformable interface
     *********************************************************************************/
    
    public ISurfaceGeo add(double x, double y, double z){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.add(x,y,z);
	return this;
    }
    public ISurfaceGeo add(IDoubleI x, IDoubleI y, IDoubleI z){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.add(x,y,z);
	return this;
    }
    public ISurfaceGeo add(IVecI v){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.add(v);
	return this;
    }
    public ISurfaceGeo sub(double x, double y, double z){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.sub(x,y,z);
	return this;
    }
    public ISurfaceGeo sub(IDoubleI x, IDoubleI y, IDoubleI z){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.sub(x,y,z);
	return this;
    }
    public ISurfaceGeo sub(IVecI v){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.sub(v);
	return this;
    }
    public ISurfaceGeo mul(IDoubleI v){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.mul(v);
	return this;
    }
    public ISurfaceGeo mul(double v){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.mul(v);
	return this;
    }
    public ISurfaceGeo div(IDoubleI v){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.div(v);
	return this;
    }
    public ISurfaceGeo div(double v){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.div(v);
	return this;
    }
    
    public ISurfaceGeo neg(){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.neg();
	return this;
    }
    /** alias of neg */
    public ISurfaceGeo flip(){ return neg(); }
    
    
    /** scale add */
    public ISurfaceGeo add(IVecI v, double f){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.add(v,f);
	return this;
    }	
    public ISurfaceGeo add(IVecI v, IDoubleI f){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.add(v,f);
	return this;
    }
    /** scale add alias */
    public ISurfaceGeo add(double f, IVecI v){ return add(v,f); }
    public ISurfaceGeo add(IDoubleI f, IVecI v){ return add(v,f); }
    
    
    public ISurfaceGeo rot(IDoubleI angle){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot(angle);
	return this;
    }
    public ISurfaceGeo rot(double angle){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot(angle);
	return this;
    }
    
    public ISurfaceGeo rot(IVecI axis, IDoubleI angle){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot(axis,angle);
	return this;
    }
    public ISurfaceGeo rot(IVecI axis, double angle){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot(axis,angle);
	return this;
    }
    
    public ISurfaceGeo rot(IVecI center, IVecI axis, IDoubleI angle){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot(center,axis,angle);
	return this;
    }
    public ISurfaceGeo rot(IVecI center, IVecI axis, double angle){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot(center,axis,angle);
	return this;
    }
    
    /** rotate to destination direction vector */
    public ISurfaceGeo rot(IVecI axis, IVecI destDir){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot(axis,destDir);
	return this;
    }
    /** rotate to destination point location */    
    public ISurfaceGeo rot(IVecI center, IVecI axis, IVecI destPt){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot(center,axis,destPt);
	return this;
    }
    
    public ISurfaceGeo rot2(IDoubleI angle){ return rot(angle); }
    public ISurfaceGeo rot2(double angle){ return rot(angle); }
    public ISurfaceGeo rot2(IVecI center, IDoubleI angle){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot2(center,angle);
	return this;
    }
    public ISurfaceGeo rot2(IVecI center, double angle){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot2(center,angle);
	return this;
    }
    
    /** rotation on xy-plane to destination direction vector */
    public ISurfaceGeo rot2(IVecI destDir){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot2(destDir);
	return this;
    }
    /** rotation on xy-plane to destination point location */    
    public ISurfaceGeo rot2(IVecI center, IVecI destPt){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.rot2(center,destPt);
	return this;
    }
    
    
    /** alias of mul */
    public ISurfaceGeo scale(IDoubleI f){ return mul(f); }
    public ISurfaceGeo scale(double f){ return mul(f); }
    
    public ISurfaceGeo scale(IVecI center, IDoubleI f){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.scale(center,f); 
	return this;
    }
    public ISurfaceGeo scale(IVecI center, double f){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.scale(center,f); 
	return this;
    }
    
    /** scale only in 1 direction */
    public ISurfaceGeo scale1d(IVecI axis, double f){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.scale1d(axis,f);
	return this;
    }
    public ISurfaceGeo scale1d(IVecI axis, IDoubleI f){ 
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.scale1d(axis,f);
	return this;
    }
    public ISurfaceGeo scale1d(IVecI center, IVecI axis, double f){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.scale1d(center,axis,f);
	return this;
    }
    public ISurfaceGeo scale1d(IVecI center, IVecI axis, IDoubleI f){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.scale1d(center,axis,f);
	return this;
    }
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public ISurfaceGeo ref(IVecI planeDir){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.ref(planeDir);
	return this;
    }
    public ISurfaceGeo ref(IVecI center, IVecI planeDir){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.ref(center,planeDir);
	return this;
    }
    /** mirror is alias of ref */
    public ISurfaceGeo mirror(IVecI planeDir){ return ref(planeDir); }
    public ISurfaceGeo mirror(IVecI center, IVecI planeDir){ return ref(planeDir); }
    
    
    /** shear operation */
    public ISurfaceGeo shear(double sxy, double syx, double syz,
			     double szy, double szx, double sxz){
	for(IVecI[] cpts : controlPoints)
	    for(IVecI p : cpts) p.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public ISurfaceGeo shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			     IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	for(IVecI[] cpts : controlPoints)
	    for(IVecI p : cpts) p.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public ISurfaceGeo shear(IVecI center, double sxy, double syx, double syz,
			     double szy, double szx, double sxz){
	for(IVecI[] cpts : controlPoints)
	    for(IVecI p : cpts) p.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public ISurfaceGeo shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			     IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	for(IVecI[] cpts : controlPoints)
	    for(IVecI p : cpts) p.shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    public ISurfaceGeo shearXY(double sxy, double syx){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearXY(sxy,syx);
	return this;
    }
    public ISurfaceGeo shearXY(IDoubleI sxy, IDoubleI syx){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearXY(sxy,syx);
	return this;
    }
    public ISurfaceGeo shearXY(IVecI center, double sxy, double syx){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearXY(center,sxy,syx);
	return this;
    }
    public ISurfaceGeo shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearXY(center,sxy,syx);
	return this;
    }
    public ISurfaceGeo shearYZ(double syz, double szy){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearYZ(syz,szy);
	return this;
    }
    public ISurfaceGeo shearYZ(IDoubleI syz, IDoubleI szy){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearYZ(syz,szy);
	return this;
    }
    public ISurfaceGeo shearYZ(IVecI center, double syz, double szy){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearYZ(center,syz,szy);
	return this;
    }
    public ISurfaceGeo shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearYZ(center,syz,szy);
	return this;
    }
    
    public ISurfaceGeo shearZX(double szx, double sxz){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearZX(szx,sxz);
	return this;
    }
    public ISurfaceGeo shearZX(IDoubleI szx, IDoubleI sxz){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearZX(szx,sxz);
	return this;
    }
    public ISurfaceGeo shearZX(IVecI center, double szx, double sxz){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearZX(center,szx,sxz);
	return this;
    }	
    public ISurfaceGeo shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.shearZX(center,szx,sxz);
	return this;
    }
    
    /** mv() is alias of add() */
    public ISurfaceGeo mv(double x, double y, double z){ return add(x,y,z); }
    public ISurfaceGeo mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ISurfaceGeo mv(IVecI v){ return add(v); }
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public ISurfaceGeo cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public ISurfaceGeo cp(double x, double y, double z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    public ISurfaceGeo cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    public ISurfaceGeo cp(IVecI v){ return dup().add(v); }
    
    
    /** translate() is alias of add() */
    public ISurfaceGeo translate(double x, double y, double z){ return add(x,y,z); }
    public ISurfaceGeo translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ISurfaceGeo translate(IVecI v){ return add(v); }
    
    
    public ISurfaceGeo transform(IMatrix3I mat){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.transform(mat); 
	return this;
    }
    public ISurfaceGeo transform(IMatrix4I mat){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.transform(mat); 
	return this;
    }
    public ISurfaceGeo transform(IVecI xvec, IVecI yvec, IVecI zvec){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.transform(xvec,yvec,zvec); 
	return this;
    }
    public ISurfaceGeo transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	for(IVecI[] cpts : controlPoints) for(IVecI p : cpts) p.transform(xvec,yvec,zvec,translate); 
	return this;
    }
    
    
}
