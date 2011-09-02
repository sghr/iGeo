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

import igeo.gui.*;
import igeo.core.*;

/**
   Geometry of NURBS surface.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
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
	if(ustart!=0.0||uend!=0.0) normalizeKnots(uknots, ustart, uend);
	if(vstart!=0.0||vend!=0.0) normalizeKnots(vknots, vstart, vend);
	init(cpts, udegree, vdegree, uknots, vknots);
    }
    
    public ISurfaceGeo(IVecI[][] cpts, int udegree, int vdegree,
		       double[] uknots, double[] vknots){
	ustart=uknots[0]; uend=uknots[uknots.length-1];
	vstart=vknots[0]; vend=vknots[vknots.length-1];
	/*
	if(IConfig.normalizeKnots){
	    if(uknots[0]!=0. || uknots[uknots.length-1]!=1.) 
		normalizeKnots(uknots, uknots[0], uknots[uknots.length-1]);
	    if(vknots[0]!=0. || vknots[vknots.length-1]!=1.) 
		normalizeKnots(vknots, vknots[0], vknots[vknots.length-1]);
	}
	*/
	if(uknots[0]!=0. || uknots[uknots.length-1]!=1.) 
	    normalizeKnots(uknots, uknots[0], uknots[uknots.length-1]);
	if(vknots[0]!=0. || vknots[vknots.length-1]!=1.) 
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
		    loop.add(new ITrimCurve(srf.innerTrimLoop.get(i).get(j)).surface(this));
		}
		innerTrimLoop.add(loop);
	    }
	}
	if(srf.outerTrimLoop!=null){
	    outerTrimLoop = new ArrayList<ArrayList<ITrimCurve>>();
	    for(int i=0; i<srf.outerTrimLoop.size(); i++){
		ArrayList<ITrimCurve> loop = new ArrayList<ITrimCurve>();
		for(int j=0; j<srf.outerTrimLoop.get(i).size(); j++){
		    loop.add(new ITrimCurve(srf.outerTrimLoop.get(i).get(j)).surface(this));
		}
		outerTrimLoop.add(loop);
	    }
	}
    }
    
    public void init(IVecI[][] cpts, int udeg, int vdeg, boolean closeU, boolean closeV){
	if(closeU && closeV){
	    IVecI[][] cpts2 = new IVecI[cpts.length][];
	    for(int i=0; i<cpts.length; i++)
		cpts2[i] = createClosedControlPoints(cpts[i],vdeg);
	    
	    IVecI[][] ucpts = new IVecI[cpts2[0].length][cpts2.length];
	    for(int i=0; i<cpts2[0].length; i++)
		for(int j=0; j<cpts2.length; j++) ucpts[i][j] = cpts2[j][i];
	    IVecI[][] ucpts2 = new IVecI[cpts2[0].length][];
	    for(int i=0; i<cpts2[0].length; i++)
		ucpts2[i] = createClosedControlPoints(ucpts[i],udeg);
	    IVecI[][] cpts3 = new IVecI[ucpts2[0].length][cpts2[0].length];
	    for(int i=0; i<cpts3.length; i++)
		for(int j=0; j<cpts3[0].length; j++) cpts3[i][j] = ucpts2[j][i];
	    
	    init(cpts3, udeg, vdeg, createClosedKnots(udeg, cpts3.length),
		 createClosedKnots(vdeg, cpts3[0].length));
	}
	else if(closeU){
	    init(cpts, udeg, vdeg, closeU, createKnots(vdeg, cpts[0].length));
	}
	else if(closeV){
	    init(cpts, udeg, vdeg, createKnots(udeg, cpts.length), closeV);
	}
	else{
	    init(cpts, udegree, vdegree,
		 createKnots(udegree, cpts.length),createKnots(vdegree, cpts[0].length));
	}
    }
    public void init(IVecI[][] cpts, int udeg, int vdeg, double[] uk, boolean closeV){
	if(closeV){
	    IVecI[][] cpts2 = new IVecI[cpts.length][];
	    for(int i=0; i<cpts.length; i++)
		cpts2[i] = createClosedControlPoints(cpts[i],vdeg);
	    init(cpts2, udeg, vdeg, uk, createClosedKnots(vdeg, cpts2[0].length));
	}
	else{
	    init(cpts, udeg, vdeg, uk,createKnots(vdeg,cpts[0].length));
	}
    }
    public void init(IVecI[][] cpts, int udeg, int vdeg, boolean closeU, double[] vk){
	if(closeU){
	    IVecI[][] ucpts = new IVecI[cpts[0].length][cpts.length];
	    for(int i=0; i<cpts[0].length; i++)
		for(int j=0; j<cpts.length; j++) ucpts[i][j] = cpts[j][i];
	    IVecI[][] ucpts2 = new IVecI[cpts[0].length][];
	    for(int i=0; i<cpts[0].length; i++)
		ucpts2[i] = createClosedControlPoints(ucpts[i],udeg);
	    IVecI[][] cpts2 = new IVecI[ucpts2[0].length][cpts[0].length];
	    for(int i=0; i<cpts2.length; i++)
		for(int j=0; j<cpts2[0].length; j++) cpts2[i][j] = ucpts2[j][i];
	    init(cpts2, udeg, vdeg, createClosedKnots(udeg, cpts2.length), vk);
	}
	else{
	    init(cpts, udeg, vdeg, createKnots(udeg,cpts.length),vk);
	}
    }
    
    public void init(IVecI[][] cpts, int udeg, int vdeg, double[] uk, double[] vk){
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
        
        double nu[] = basisFunctionU.eval(uindex, u);
        double nv[] = basisFunctionV.eval(vindex, v);
        
        double dnu[] = derivativeFunctionU.eval(uindex, u);
        
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
        
        double nu[] = basisFunctionU.eval(uindex, u);
        double nv[] = basisFunctionV.eval(vindex, v);
        
        double dnv[] = derivativeFunctionV.eval(vindex, v);
        
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
    
    
    public IVec normal(IVec2I v){ IVec2 vec=v.get(); return normal(vec.x,vec.y); }
    public IVec normal(IDoubleI u, IDoubleI v){ return normal(u.x(),v.x()); }
    public IVec normal(double u, double v){
	IVec retval = new IVec();
	normal(u,v,retval);
	return retval;
    }
    public void normal(double u, double v, IVec retval){
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
	else IOut.p("normal is zero"); //
    }
    
    public IVec cp(int i, int j){ return controlPoints[i][j].get(); }
    public IVecI cp(IIntegerI i, IIntegerI j){ return controlPoints[i.x()][j.x()]; }
    
    public IVec corner(int u, int v){
	if(u!=0) u=1;
	if(v!=0) v=1;
	return pt(u,v);
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
    
    // not tested yet.
    public IVec ep(int i, int j){
	return pt(uknots[i+udegree], vknots[j+vdegree]);
    }
    public IVec ep(IIntegerI i, IIntegerI j){
	return pt(uknots[i.x()+udegree], vknots[j.x()+vdegree]);
    }
    
    
    public double uknot(int i){ return uknots[i]; }
    public IDouble uknot(IIntegerI i){ return new IDouble(uknots[i.x()]); }
    public double vknot(int i){ return vknots[i]; }
    public IDouble vknot(IIntegerI i){ return new IDouble(vknots[i.x()]); }
    
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
    
    public ISurfaceGeo clearInnerTrim(){
	for(int i=0; innerTrimLoop!=null &&i<innerTrimLoop.size(); i++){
	    ArrayList<ITrimCurve> loop=innerTrimLoop.get(i);
	    //for(int j=0; loop!=null&&j<loop.size(); j++) loop.get(j).del();
	    loop.clear();
	}
	innerTrimLoop.clear();
	return this;
    }
    public ISurfaceGeo clearOuterTrim(){
	for(int i=0; outerTrimLoop!=null &&i<outerTrimLoop.size(); i++){
	    ArrayList<ITrimCurve> loop=outerTrimLoop.get(i);
	    //for(int j=0; loop!=null&&j<loop.size(); j++) loop.get(j).del();
	    loop.clear();
	}
	outerTrimLoop.clear();
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
	ITrimLoopGraphic loop = new ITrimLoopGraphic(crv,true,IConfig.surfaceTrimEdgeResolution);
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
	crvg.setup2D(IConfig.surfaceTrimEdgeResolution);
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
	if(!checkTrimLoop(loop)) return this;
	
	if(innerTrimLoop==null)
	    innerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	ArrayList<ITrimCurve> l = new ArrayList<ITrimCurve>();
	for(int i=0;loop!=null&&i<loop.length;i++) l.add(loop[i].surface(this));
	innerTrimLoop.add(l);
	closeInnerTrim();
	return this;
    }
    public ISurfaceGeo addInnerTrimLoop(ITrimCurveI loop){
	return addInnerTrimLoop(loop.get());
    }
    public ISurfaceGeo addInnerTrimLoop(ITrimCurve loop){
	if(!checkTrimLoop(loop)) return this;
	
	if(innerTrimLoop==null)
	    innerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	ArrayList<ITrimCurve> l = new ArrayList<ITrimCurve>();
	if(!loop.isClosed()){ IOut.err("trim loop is not closed"); } 
	l.add(loop.surface(this));
	innerTrimLoop.add(l);
	closeInnerTrim();
	return this;
    }
    
    public ISurfaceGeo addInnerTrimLoop(ICurveI[] trim){
	return addInnerTrimLoop(trim,true);
    }
    public ISurfaceGeo addInnerTrimLoop(ICurveI[] trim, boolean deleteInput){
	if(trim==null || trim.length==0){ IOut.err("no trim input"); return this; }
	ITrimCurve[] trimCrvs = new ITrimCurve[trim.length];
	for(int i=0; i<trim.length; i++){
	    trimCrvs[i] = new ITrimCurve(trim[i]).surface(this);
	    if(deleteInput && trim[i] instanceof IObject) ((IObject)trim[i]).del();
	}
	return addInnerTrimLoop(trimCrvs);
    }
    public ISurfaceGeo addInnerTrimLoop(ICurveI trim){
	return addInnerTrimLoop(trim,true);
    }
    public ISurfaceGeo addInnerTrimLoop(ICurveI trim, boolean deleteInput){
	if(trim==null){ IOut.err("no trim input"); return this; }
	ITrimCurve trimCrv =new ITrimCurve(trim).surface(this);
	if(deleteInput && trim instanceof IObject) ((IObject)trim).del();
	return addInnerTrimLoop(trimCrv);
    }
        
    public ISurfaceGeo addInnerTrim(ITrimCurve trimCrv){
	if(!checkTrim(trimCrv)) return this;
	
	if(innerTrimLoop==null){
	    innerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	    innerTrimLoop.add(new ArrayList<ITrimCurve>());
	}
	else if(innerTrimClosed){
	    innerTrimLoop.add(new ArrayList<ITrimCurve>());
	    innerTrimClosed=false;
	}
	innerTrimLoop.get(innerTrimLoop.size()-1).add(trimCrv.surface(this));
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
	if(!checkTrimLoop(loop)) return this;
	if(outerTrimLoop==null)
	    outerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	ArrayList<ITrimCurve> l = new ArrayList<ITrimCurve>();
	for(int i=0;loop!=null&&i<loop.length;i++) l.add(loop[i].surface(this));
	outerTrimLoop.add(l);
	closeOuterTrim();
	return this;
    }
    
    public ISurfaceGeo addOuterTrimLoop(ITrimCurveI loop){
	return addOuterTrimLoop(loop.get());
    }
    public ISurfaceGeo addOuterTrimLoop(ITrimCurve loop){
	if(!checkTrimLoop(loop)) return this;
	if(outerTrimLoop==null)
	    outerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	ArrayList<ITrimCurve> l = new ArrayList<ITrimCurve>();
	if(!loop.isClosed()){ IOut.err("trim loop is not closed"); } 
	l.add(loop.surface(this));
	outerTrimLoop.add(l);
	closeOuterTrim();
	return this;
    }
    
    public ISurfaceGeo addOuterTrimLoop(ICurveI[] trim){
	return addOuterTrimLoop(trim,true);
    }
    public ISurfaceGeo addOuterTrimLoop(ICurveI[] trim, boolean deleteInput){
	if(trim==null || trim.length==0){ IOut.err("no trim input"); return this; }
	ITrimCurve[] trimCrvs = new ITrimCurve[trim.length];
	for(int i=0; i<trim.length; i++){
	    trimCrvs[i] = new ITrimCurve(trim[i]).surface(this);
	    if(deleteInput && trim[i] instanceof IObject) ((IObject)trim[i]).del();
	}
	return addOuterTrimLoop(trimCrvs);
    }
    public ISurfaceGeo addOuterTrimLoop(ICurveI trim){
	return addOuterTrimLoop(trim,true);
    }
    public ISurfaceGeo addOuterTrimLoop(ICurveI trim, boolean deleteInput){
	if(trim==null){ IOut.err("no trim input"); return this; }
	ITrimCurve trimCrv =new ITrimCurve(trim).surface(this);
	if(deleteInput && trim instanceof IObject) ((IObject)trim).del();
	return addOuterTrimLoop(trimCrv);
    }
    
    public ISurfaceGeo addOuterTrim(ITrimCurve trimCrv){
	if(!checkTrim(trimCrv)) return this;
	if(outerTrimLoop==null){
	    outerTrimLoop=new ArrayList<ArrayList<ITrimCurve>>();
	    outerTrimLoop.add(new ArrayList<ITrimCurve>());
	}
	else if(outerTrimClosed){
	    outerTrimLoop.add(new ArrayList<ITrimCurve>());
	    outerTrimClosed=false;
	}
	outerTrimLoop.get(outerTrimLoop.size()-1).add(trimCrv.surface(this));
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
	    //IOut.p("trim line num = 4"); //
	    for(int i=0; i<outerTrimLoop.get(0).size(); i++){
		/*
		IOut.p("trim line ("+i+") = { "+
			outerTrimLoop.get(0).get(i).cp(0).toString()+" , "+
			outerTrimLoop.get(0).get(i).cp(1).toString()+" }"); //
		*/
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
	    //IOut.p("trim line num = 1"); //
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
    
}
