/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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
   Geometry of NURBS curve.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ICurveGeo extends INurbsGeo implements ICurveI, IEntityParameter{
    
    public IVecI[] controlPoints;
    
    // degree and knots should not be parameterized
    // because number of control points, degree and knots are intertwined together: 
    // changing them independently doesn't make sense
    public int degree;
    
    /**
       normalized knot vector (start value in knot vector is 0, end value is 1)
       normalized if IConfig.normalizeKots is true
    */
    public double[] knots;
    /**
       ustart and uend is not normalized, keeping original value
    */
    public double ustart, uend;
    
    /**
       flag to determine to use default weight value (1.0)
    */
    public boolean[] defaultWeights;
    
    public IBSplineBasisFunction basisFunction;
    public IBSplineBasisFunction derivativeFunction;
    
    
    
    public ICurveGeo(){}
    
    public ICurveGeo(IVecI[] cpts, int degree, double[] knots, double ustart, double uend){
	this.ustart=ustart;
	this.uend=uend;
	//if(IConfig.normalizeKnots) normalizeKnots(knots, ustart, uend);
	if(ustart!=0.0 || uend!=1.0) normalizeKnots(knots, ustart, uend);
	init(cpts, degree, knots);
    }
    
    public ICurveGeo(IVecI[] cpts, int degree, double[] knots){
	ustart=knots[0];
	uend=knots[knots.length-1];
	//if(IConfig.normalizeKnots && (knots[0]!=0. || knots[knots.length-1]!=1.)) 
	//  normalizeKnots(knots, knots[0], knots[knots.length-1]);
	if(knots[0]!=0.0 || knots[knots.length-1]!=1.0)
	    normalizeKnots(knots, knots[0], knots[knots.length-1]);
	init(cpts, degree, knots);
    }
    
    public ICurveGeo(IVecI[] cpts, int degree){
	ustart=0.; uend=1.;
	init(cpts, degree, createKnots(degree, cpts.length));
    }
    
    public ICurveGeo(IVecI[] cpts){
	ustart=0.; uend=1.;
	init(cpts, 1, createKnots(1, cpts.length));
    }
    
    public ICurveGeo(IVecI[] cpts, int degree, boolean close){
	ustart=0.; uend=1.;
	init(cpts, degree, close);
    }
    
    public ICurveGeo(IVecI[] cpts, boolean close){
	ustart=0.; uend=1.;
	init(cpts, 1, close);
    }
    
    public ICurveGeo(IVecI pt1, IVecI pt2){
	ustart=0.; uend=1.;
	init(new IVecI[]{ pt1, pt2 }, 1, createKnots(1, 2));
    }
    
    public ICurveGeo(double x1,double y1,double z1,double x2,double y2,double z2){
	this(new IVec(x1,y1,z1),new IVec(x2,y2,z2));
    }
    
    public ICurveGeo(double[][] xyzValues){
	ustart=0.; uend=1.;
	init(getPointsFromArray(xyzValues), 1);
    }
    
    public ICurveGeo(double[][] xyzValues, int degree){
	ustart=0.; uend=1.;
	init(getPointsFromArray(xyzValues), degree);
    }
    
    public ICurveGeo(double[][] xyzValues, boolean close){
	ustart=0.; uend=1.;
	init(getPointsFromArray(xyzValues), 1, close);
    }
    
    public ICurveGeo(double[][] xyzValues, int degree, boolean close){
	ustart=0.; uend=1.;
	init(getPointsFromArray(xyzValues), degree, close);
    }
    
    
    public ICurveGeo(ICurveGeo crv){
	// duplicate points
	controlPoints = new IVecI[crv.controlPoints.length];
	for(int i=0; i<controlPoints.length; i++)
	    controlPoints[i] = crv.controlPoints[i].dup();
	knots = new double[crv.knots.length];
	System.arraycopy(crv.knots, 0, knots, 0, knots.length);
	degree = crv.degree;
	ustart=crv.ustart; uend=crv.uend;
	init(controlPoints, degree, knots);
    }
    
    
    public void init(IVecI[] cpts){
	init(cpts, 1, createKnots(1, cpts.length));
    }
    
    public void init(IVecI[] cpts, int degree){
	init(cpts, degree, createKnots(degree, cpts.length));
    }
    
    public void init(IVecI[] cpts, boolean close){
	init(cpts, 1, close);
    }
    
    public void init(IVecI[] cpts, int degree, boolean close){
	if(close){
	    IVecI[] cpts2 = createClosedCP(cpts,degree);
	    init(cpts2, degree, createClosedKnots(degree, cpts2.length));
	}
	else{
	    init(cpts, degree, createKnots(degree, cpts.length));
	}
    }
    
    public void init(IVecI[] cpts, int degree, double[] knots){
	
	if(IConfig.checkValidControlPoint){ isValidCP(cpts, degree, knots); }
	
	// duplicate of control points is avoided
	if(IConfig.checkDuplicatedControlPoint){ checkDuplicatedCP(cpts); }
	else if(IConfig.checkDuplicatedControlPointOnEdge){ checkDuplicatedCPOnEdge(cpts); }
	
	
	controlPoints = cpts;
	this.degree = degree;
	this.knots = knots;
	basisFunction = new IBSplineBasisFunction(degree, knots);
	
	defaultWeights = new boolean[cpts.length];
	for(int i=0; i<cpts.length; i++){
	    defaultWeights[i] = !(cpts[i] instanceof IVec4I);
	}
    }
    public boolean isValid(){
	return isValidCP(controlPoints, degree, knots);
    }
    
    public static boolean isValidCP(IVecI[] cpts, int deg, double[] knots){
	if(cpts==null){
	    IOut.err("control points are null");
	    return false;
	}
	
	if(knots==null){
	    IOut.err("knots are null");
	    return false;
	}
	
	if(deg <= 0){
	    IOut.err("invalid degree ("+deg+")");
	    return false;
	}
	
	int num = cpts.length;
	
	if(num <= deg){
	    IOut.err("too less control points ("+num+") for degree "+deg+". it needs minimum "+(deg+1));
	    return false;
	}
	
	if(knots.length != (deg+num+1)){
	    IOut.err("knot array length is invalid. it needs to be "+(deg+num+1));
	    return false;
	}
	
	if(!isValidCP(cpts)) return false;
	
	if(!isValidKnots(knots)){
	    IOut.err("knot has invalid value");
	    return false;
	}
	
	return true;
    }
    
    public static boolean isValidCP(IVecI[] cpts){
	for(int i=0; i<cpts.length; i++){
	    if(!cpts[i].isValid()){
		IOut.err("controlPoint at "+i+" is invalid");
		return false;
	    }
	}
	return true;
    }
    
    static public void checkDuplicatedCP(IVecI[] cpts){
	for(int i=0; i<cpts.length; i++)
	    for(int j=i+1; j<cpts.length; j++)
		if(cpts[j]==cpts[i]) cpts[j] = cpts[i].dup();
    }
    
    static public void checkDuplicatedCPOnEdge(IVecI[] cpts){
	if(cpts[0]==cpts[cpts.length-1]) cpts[cpts.length-1] = cpts[0].dup();
    }
    
    static public IVec[] getPointsFromArray(double[][] xyzvalues){
	IVec[] cpts = new IVec[xyzvalues.length];
	for(int i=0; i<cpts.length; i++){
	    if(xyzvalues[i].length==4){
		cpts[i] = new IVec4(xyzvalues[i][0], xyzvalues[i][1], xyzvalues[i][2],xyzvalues[i][3]);
	    }
	    else{
		cpts[i] = new IVec();
		if(xyzvalues[i].length>=1) cpts[i].x = xyzvalues[i][0];
		if(xyzvalues[i].length>=2) cpts[i].y = xyzvalues[i][1];
		if(xyzvalues[i].length>=3) cpts[i].z = xyzvalues[i][2];
	    }
	}
	return cpts;
    }
    
    public ICurveGeo get(){ return this; }
    
    public ICurveGeo dup(){ return new ICurveGeo(this); }
    
    
    public IVec pt(IDoubleI u){ return pt(u.x()); }
    public IVec pt(double u){
	IVec retval = new IVec();
	pt(u, retval);
	return retval;
    }
    public void pt(double u, IVec retval){
	//IOut.p("u="+u); //
        int index = basisFunction.index(u);
        double n[] = basisFunction.eval(index, u);
        double weight=0;
        for(int i=0; i<=degree; i++){
            IVec cpt=controlPoints[index-degree+i].get();
	    double w=1.;
	    if(!defaultWeights[index-degree+i]) w=((IVec4)cpt).w;
            retval.x += cpt.x*w*n[i];
            retval.y += cpt.y*w*n[i];
            retval.z += cpt.z*w*n[i];
            weight += w*n[i];
        }
        retval.x/=weight;
        retval.y/=weight;
        retval.z/=weight;
    }
    
    public IVec tan(IDoubleI u){ return tan(u.x()); }
    public IVec tan(double u){
	IVec retval = new IVec();
	tan(u, retval);
	return retval;
    }
    public void tan(double u, IVec retval){
	if(derivativeFunction==null){
            derivativeFunction=new IBSplineBasisFunction(basisFunction);
            derivativeFunction.differentiate();
        }
        int index = derivativeFunction.index(u);
	
        double dn[] = derivativeFunction.eval(index, u);
        double n[] = basisFunction.eval(index, u);
        
        IVec4 val1 = new IVec4();
        IVec4 val2 = new IVec4();
        
        for(int i=0; i<=degree; i++){
            IVec cpt=controlPoints[index-degree+i].get();
	    
            double w=1.;
            if(!defaultWeights[index-degree+i]) w=((IVec4)cpt).w;
            
            val1.x += cpt.x*w*n[i];
            val1.y += cpt.y*w*n[i];
            val1.z += cpt.z*w*n[i];
            val1.w += w*n[i];
	    
	    val2.x += cpt.x*w*dn[i];
            val2.y += cpt.y*w*dn[i];
            val2.z += cpt.z*w*dn[i];
            val2.w += w*dn[i];
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
    
    /** getting i-th control point */
    public IVecI cp(int i){ return controlPoints[i]/*.get()*/; }
    /** getting i-th control point */
    public IVecI cp(IIntegerI i){ return controlPoints[i.x()]; }
    
    public IVecI[] cps(){ return controlPoints; }
    
    public IVec ep(int i){ return pt(knots[i+degree]); }
    public IVec ep(IIntegerI i){ return pt(knots[i.x()+degree]); }
    
    public IVec start(){ return pt(0.); }
    public IVec end(){ return pt(1.); }
    
    public IVec startCP(){ return controlPoints[0].get(); }
    public IVec endCP(){ return controlPoints[controlPoints.length-1].get(); }
    
    public double knot(int i){ return knots[i]; }
    public IDouble knot(IIntegerI i){ return new IDouble(knots[i.x()]); }

    public double[] knots(){ return knots; }
    public double[] knots(ISwitchE e){ return knots(); }
    public IDouble[] knots(ISwitchR r){
	IDouble[] kn = new IDouble[knots.length];
	for(int i=0; i<knots.length; i++) kn[i] = new IDouble(knots[i]);
	return kn;
    }
    
    public int knotNum(){ return knots.length; }
    //public IInteger knotNumR(){ return new IInteger(knots.length); }
    public int knotNum(ISwitchE e){ return knotNum(); }
    public IInteger knotNum(ISwitchR r){ return new IInteger(knotNum()); }
    
    public int deg(){ return degree; }
    //public IInteger degR(){ return new IInteger(degree); }
    public int deg(ISwitchE e){ return deg(); }
    public IInteger deg(ISwitchR r){ return new IInteger(deg()); }
    
    public int num(){ return controlPoints.length; }
    //public IInteger numR(){ return new IInteger(controlPoints.length); }
    public int num(ISwitchE e){ return num(); }
    public IInteger num(ISwitchR r){ return new IInteger(num()); }
    
    public int cpNum(){ return num(); }
    //public IInteger cpNumR(){ return new IInteger(num()); }
    public int cpNum(ISwitchE e){ return cpNum(); }
    public IInteger cpNum(ISwitchR r){ return new IInteger(cpNum()); }
    
    public int epNum(){ return knots.length-2*degree; }
    //public IInteger epNumR(){ return new IInteger(epNum()); }
    public int epNum(ISwitchE e){ return epNum(); }
    public IInteger epNum(ISwitchR r){ return new IInteger(epNum()); }
    
    public boolean isRational(){
	if(defaultWeights==null){ IOut.err("defaultWeights is null"); return false; }
	for(int i=0; i<defaultWeights.length; i++) if(!defaultWeights[i]) return true; 
	return false;
    }
    public boolean isRational(ISwitchE e){ return isRational(); }
    public IBool isRational(ISwitchR r){ return new IBool(isRational()); }
    
    
    public double len(double resolutionPerEP){
	double ln = 0;
	if(degree==1){
	    for(int i=0; i<num()-1; i++) ln += cp(i+1).dist(cp(i));
	    return ln;
	}
	int epnum = epNum();
	int rnum = (int)(1.0/resolutionPerEP);
	if(rnum<1) rnum=1;
	IVec p1=null, p2=null;
	for(int i=0; i<epnum; i++){
	    for(int j=0; j<rnum && i<epnum-1 || j<=0; j++){
		p2 = pt(u(i,(double)j/rnum));
		if(p1!=null) ln += p2.dist(p1);
		p1 = p2;
	    }
	}
	return ln;
    }
    
    public double len(){
	// default resolution per EP. not refined. ; should it be IConfig.parameterTolerance? Is it too small?
	final double defaultResolution = 0.01; // 1/100
	return len(defaultResolution);
    }
    //public IDouble lenR(){ return null; }
    public double len(ISwitchE e){ return len(); }
    public IDouble len(ISwitchR r){ return new IDouble(len()); }
    
    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    public double u(int epIdx, double epFraction){
        if(epFraction>=0) return knots[epIdx+degree] +
            (knots[epIdx+degree+1]-knots[epIdx+degree])*epFraction;
	return knots[epIdx+degree] +
            (knots[epIdx+degree]-knots[epIdx+degree-1])*epFraction;	
    }
    public IDouble u(IInteger epIdx, IDouble epFraction){
	return new IDouble(u(epIdx.x(),epFraction.x()));
    }
    
    public double ustart(){ return ustart; }
    public double uend(){ return uend; }
    //public IDouble ustartR(){ return new IDouble(ustart); }
    //public IDouble uendR(){ return new IDouble(uend); }
    public double ustart(ISwitchE e){ return ustart(); }
    public double uend(ISwitchE e){ return uend(); }
    public IDouble ustart(ISwitchR r){ return new IDouble(ustart()); }
    public IDouble uend(ISwitchR r){ return new IDouble(uend()); }
    
    
    // not tested; test this
    public boolean isClosed(){
	// check if start and end of parameter match with knots[0] and knots[knots.length-1]
	boolean knotsEndMatch=true;
	/*
	if(IConfig.normalizeKnots){
	    if(knots[0] != 0.0 || knots[knots.length-1] != 1.0) knotsEndMatch=false;
	}
	else{
	    if(knots[0] != ustart || knots[knots.length-1] != uend) knotsEndMatch=false;
	}
	*/
	if(knots[0] != 0.0 || knots[knots.length-1] != 1.0) knotsEndMatch=false;
	
	if(knotsEndMatch){
	    // check by cp
	    if(cp(0).eq(cp(cpNum()-1))) return true;
	    return false;
	}
	
	// check by ep
	//if(ep(0).eq(ep(epNum()-1))) return true;
	//return true;
	
	// check by pt
	if(pt(0.).eq(pt(1.))) return true;
	return false;
    }
    
    public boolean isClosed(ISwitchE e){ return isClosed(); }
    public IBool isClosed(ISwitchR r){ return new IBool(isClosed()); }

    public ICurveGeo rev(){
	synchronized(IG.lock){
	    IVecI[] cpts2 = new IVecI[controlPoints.length];
	    
	    for(int i=0; i<controlPoints.length; i++){
		cpts2[i] = controlPoints[controlPoints.length-1-i];
	    }
	    
	    double[] knots2 = new double[knots.length];
	    for(int i=0; i<knots.length; i++){
		knots2[i] = 1.0 - knots[knots.length-1-i]; // [0-1] to [1-0]
	    }

	    boolean[] defaultWeights2 = new boolean[defaultWeights.length];
	    for(int i=0; i<defaultWeights.length; i++){
		defaultWeights2[i] = defaultWeights[defaultWeights.length-1-i];
	    }
	    
	    
	    controlPoints = cpts2;
	    knots = knots2;
	    defaultWeights = defaultWeights2;
	    if(ustart!=0. || uend!=1.){
		double ustart2 = -uend;
		double uend2 = -ustart;
		ustart = ustart2;
		uend = uend2;
	    }
	    basisFunction = new IBSplineBasisFunction(degree, knots);
	    derivativeFunction = null;
	}
	return this;
    }
    
    
    
    /********************************************************************************
     * transformation methods
     *******************************************************************************/
    
    public ICurveGeo add(double x, double y, double z){
	for(IVecI p:controlPoints){ p.add(x,y,z); } return this;
    }
    public ICurveGeo add(IDoubleI x, IDoubleI y, IDoubleI z){
	for(IVecI p:controlPoints){ p.add(x,y,z); } return this;
    }
    public ICurveGeo add(IVecI v){ for(IVecI p:controlPoints){ p.add(v); } return this; }
    public ICurveGeo sub(double x, double y, double z){
	for(IVecI p:controlPoints){ p.sub(x,y,z); } return this;
    }
    public ICurveGeo sub(IDoubleI x, IDoubleI y, IDoubleI z){
	for(IVecI p:controlPoints){ p.sub(x,y,z); } return this;
    }
    public ICurveGeo sub(IVecI v){ for(IVecI p:controlPoints){ p.sub(v); } return this; }
    public ICurveGeo mul(IDoubleI v){ for(IVecI p:controlPoints){ p.mul(v); } return this; }
    public ICurveGeo mul(double v){ for(IVecI p:controlPoints){ p.mul(v); } return this; }
    public ICurveGeo div(IDoubleI v){ for(IVecI p:controlPoints){ p.div(v); } return this; }
    public ICurveGeo div(double v){ for(IVecI p:controlPoints){ p.div(v); } return this; }
    
    public ICurveGeo neg(){ for(IVecI p:controlPoints){ p.neg(); } return this; }
    /** alias of neg */
    public ICurveGeo flip(){ return neg(); }
    
    /** scale add */
    public ICurveGeo add(IVecI v, double f){ for(IVecI p:controlPoints){ p.add(v,f); } return this; }
    public ICurveGeo add(IVecI v, IDoubleI f){ for(IVecI p:controlPoints){ p.add(v,f); } return this; }
    /** scale add alias */
    public ICurveGeo add(double f, IVecI v){ return add(v,f); }
    public ICurveGeo add(IDoubleI f, IVecI v){ return add(v,f); }
    
    public ICurveGeo rot(IDoubleI angle){ for(IVecI p:controlPoints){ p.rot(angle); } return this; }
    public ICurveGeo rot(double angle){ for(IVecI p:controlPoints){ p.rot(angle); } return this; }
    public ICurveGeo rot(IVecI axis, IDoubleI angle){
	for(IVecI p:controlPoints){ p.rot(axis,angle); } return this;
    }
    public ICurveGeo rot(IVecI axis, double angle){
	for(IVecI p:controlPoints){ p.rot(axis,angle); } return this;
    }
    public ICurveGeo rot(IVecI center, IVecI axis, IDoubleI angle){
	for(IVecI p:controlPoints){ p.rot(center,axis,angle); } return this;
    }
    public ICurveGeo rot(IVecI center, IVecI axis, double angle){
	for(IVecI p:controlPoints){ p.rot(center,axis,angle); } return this;
    }
    /** rotate to destination direction vector */
    public ICurveGeo rot(IVecI axis, IVecI destDir){
	for(IVecI p:controlPoints){ p.rot(axis,destDir); } return this;
    }
    /** rotate to destination point location */
    public ICurveGeo rot(IVecI center, IVecI axis, IVecI destPt){
	for(IVecI p:controlPoints){ p.rot(center,axis,destPt); } return this;
    }

    public ICurveGeo rot2(IDoubleI angle){ return rot(angle); }
    public ICurveGeo rot2(double angle){ return rot(angle); }
    public ICurveGeo rot2(IVecI center, IDoubleI angle){
	for(IVecI p:controlPoints){ p.rot2(center,angle); } return this;
    }
    public ICurveGeo rot2(IVecI center, double angle){
	for(IVecI p:controlPoints){ p.rot2(center,angle); } return this;
    }
    /** rotation on xy-plane to destination direction vector */
    public ICurveGeo rot2(IVecI destDir){ for(IVecI p:controlPoints){ p.rot2(destDir); } return this; }
    /** rotation on xy-plane to destination point location */
    public ICurveGeo rot2(IVecI center, IVecI destPt){
	for(IVecI p:controlPoints){ p.rot2(center,destPt); } return this;
    }
    
    
    /** alias of mul */
    public ICurveGeo scale(IDoubleI f){ return mul(f); }
    public ICurveGeo scale(double f){ return mul(f); }
    public ICurveGeo scale(IVecI center, IDoubleI f){
	for(IVecI p:controlPoints){ p.scale(center,f); } return this;
    }
    public ICurveGeo scale(IVecI center, double f){
	for(IVecI p:controlPoints){ p.scale(center,f); } return this;
    }
    
    /** scale only in 1 direction */
    public ICurveGeo scale1d(IVecI axis, double f){
	for(IVecI p:controlPoints){ p.scale1d(axis,f); } return this;
    }
    public ICurveGeo scale1d(IVecI axis, IDoubleI f){
	for(IVecI p:controlPoints){ p.scale1d(axis,f); } return this;
    }
    public ICurveGeo scale1d(IVecI center, IVecI axis, double f){
	for(IVecI p:controlPoints){ p.scale1d(center,axis,f); } return this;
    }
    public ICurveGeo scale1d(IVecI center, IVecI axis, IDoubleI f){
	for(IVecI p:controlPoints){ p.scale1d(center,axis,f); } return this;
    }
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public ICurveGeo ref(IVecI planeDir){
	for(IVecI p:controlPoints){ p.ref(planeDir); } return this;
    }
    public ICurveGeo ref(IVecI center, IVecI planeDir){
	for(IVecI p:controlPoints){ p.ref(center,planeDir); } return this;
    }
    /** mirror is alias of ref */
    public ICurveGeo mirror(IVecI planeDir){ return ref(planeDir); }
    public ICurveGeo mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    public ICurveGeo shear(double sxy, double syx, double syz,
			   double szy, double szx, double sxz){
	for(IVecI p:controlPoints){ p.shear(sxy,syx,syz,szy,szx,sxz); } return this;
    }
    public ICurveGeo shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			   IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	for(IVecI p:controlPoints){ p.shear(sxy,syx,syz,szy,szx,sxz); } return this;
    }
    public ICurveGeo shear(IVecI center, double sxy, double syx, double syz,
			   double szy, double szx, double sxz){
	for(IVecI p:controlPoints){ p.shear(center,sxy,syx,syz,szy,szx,sxz); } return this;
    }
    public ICurveGeo shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			   IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	for(IVecI p:controlPoints){ p.shear(center,sxy,syx,syz,szy,szx,sxz); } return this;
    }
    
    public ICurveGeo shearXY(double sxy, double syx){
	for(IVecI p:controlPoints){ p.shearXY(sxy,syx); } return this;
    }
    public ICurveGeo shearXY(IDoubleI sxy, IDoubleI syx){
	for(IVecI p:controlPoints){ p.shearXY(sxy,syx); } return this;
    }
    public ICurveGeo shearXY(IVecI center, double sxy, double syx){
	for(IVecI p:controlPoints){ p.shearXY(center,sxy,syx); } return this;
    }
    public ICurveGeo shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	for(IVecI p:controlPoints){ p.shearXY(center,sxy,syx); } return this;
    }
    
    public ICurveGeo shearYZ(double syz, double szy){
	for(IVecI p:controlPoints){ p.shearYZ(syz,szy); } return this;
    }
    public ICurveGeo shearYZ(IDoubleI syz, IDoubleI szy){
	for(IVecI p:controlPoints){ p.shearYZ(syz,szy); } return this;
    }
    public ICurveGeo shearYZ(IVecI center, double syz, double szy){
	for(IVecI p:controlPoints){ p.shearYZ(center,syz,szy); } return this;
    }
    public ICurveGeo shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	for(IVecI p:controlPoints){ p.shearYZ(center,syz,szy); } return this;
    }
    
    public ICurveGeo shearZX(double szx, double sxz){
	for(IVecI p:controlPoints){ p.shearZX(szx,sxz); } return this;
    }
    public ICurveGeo shearZX(IDoubleI szx, IDoubleI sxz){
	for(IVecI p:controlPoints){ p.shearZX(szx,sxz); } return this;
    }
    public ICurveGeo shearZX(IVecI center, double szx, double sxz){
	for(IVecI p:controlPoints){ p.shearZX(center,szx,sxz); } return this;
    }
    public ICurveGeo shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	for(IVecI p:controlPoints){ p.shearZX(center,szx,sxz); } return this;
    }
    
    /** translate is alias of add() */
    public ICurveGeo translate(double x, double y, double z){ return add(x,y,z); }
    public ICurveGeo translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ICurveGeo translate(IVecI v){ return add(v); }
    
    
    public ICurveGeo transform(IMatrix3I mat){
	for(IVecI p:controlPoints){ p.transform(mat); } return this;
    }
    public ICurveGeo transform(IMatrix4I mat){
	for(IVecI p:controlPoints){ p.transform(mat); } return this;
    }
    public ICurveGeo transform(IVecI xvec, IVecI yvec, IVecI zvec){
	for(IVecI p:controlPoints){ p.transform(xvec,yvec,zvec); } return this;
    }
    public ICurveGeo transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	for(IVecI p:controlPoints){ p.transform(xvec,yvec,zvec,translate); } return this;
    }
    
    
    /** mv() is alias of add() */
    public ICurveGeo mv(double x, double y, double z){ return add(x,y,z); }
    public ICurveGeo mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ICurveGeo mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public ICurveGeo cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public ICurveGeo cp(double x, double y, double z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    public ICurveGeo cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    public ICurveGeo cp(IVecI v){ return dup().add(v); }
    
    
    
    
}
