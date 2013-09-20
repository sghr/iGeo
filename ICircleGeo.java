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
   Geometry class of a circle.
   Implemented as a type of NURBS curve.
   
   @author Satoru Sugihara
*/
public class ICircleGeo extends ICurveGeo{
    
    public static int circleDeg(){ return 2; }
    
    public static double[] circleKnots(){
	return new double[]{ 0.,0.,0.,.25,.25,.5,.5,.75,.75,1.,1.,1. };
    }
    
    public static IVec4[] circleCP(IVec center, double radius){
	return circleCP(center,IVec.zaxis,null,radius,radius);
    }
    public static IVec4[] circleCP(IVec center, IVec normal, double radius){
	return circleCP(center,normal,null,radius,radius);
    }
    public static IVec4[] circleCP(IVec center, IVec normal,
				   IVec rollDir, double radius){
	return circleCP(center,normal,rollDir,radius,radius);
    }
    public static IVec4[] circleCP(IVec center, IVec normal, IVec rollDir,
				   double xradius, double yradius){
	//IOut.err("center = "+center);
	//IOut.err("normal = "+normal);
	//IOut.err("rollDir = "+rollDir);
	//IOut.err("xradius = "+xradius);
	//IOut.err("yradius = "+yradius);
	
	if(rollDir==null) rollDir=IVec.zaxis;
	if(normal.cross(rollDir).len2()==0){
	    if(rollDir.cross(IVec.zaxis).len2()==0) rollDir = IVec.xaxis;
	    else rollDir = IVec.zaxis;
	}
	
	//IOut.p("normal = "+normal);
	//IOut.p("rollDir = "+rollDir);
	
	IVec y = normal.cross(rollDir);
	IVec x = y.cross(normal);
	//IOut.p("x = "+x);
	//IOut.p("y = "+y);
	
	x.len(xradius);
	y.len(yradius);
	
	IVec4[] cpts = new IVec4[9];
	for(int i=0; i<cpts.length-1; i++) cpts[i] = center.to4d();
	final double sqrt2 = Math.sqrt(2)/2;
	cpts[0].add(x);
	cpts[1].add(x).add(y).w = sqrt2;
	cpts[2].add(y);
	cpts[3].sub(x).add(y).w = sqrt2;
	cpts[4].sub(x);
	cpts[5].sub(x).sub(y).w = sqrt2;
	cpts[6].sub(y);
	cpts[7].add(x).sub(y).w = sqrt2;
	cpts[8] = cpts[0].dup();
	
	//for(int i=0; i<cpts.length-1; i++) IOut.p("cpts["+i+"]="+cpts[i]); //
	
	return cpts;
    }
    
    public static IVec4[] ovalCP(IVec center, IVec xaxis, IVec yaxis){
        IVec4[] cpts = new IVec4[9];
        for(int i=0; i<cpts.length-1; i++){ cpts[i]=center.to4d(); }
	final double sqrt2 = Math.sqrt(2)/2;
        cpts[0].add(xaxis);
        cpts[1].add(xaxis).add(yaxis).w=sqrt2;
        cpts[2].add(yaxis);
        cpts[3].sub(xaxis).add(yaxis).w=sqrt2;
        cpts[4].sub(xaxis);
        cpts[5].sub(xaxis).sub(yaxis).w=sqrt2;
        cpts[6].sub(yaxis);
        cpts[7].add(xaxis).sub(yaxis).w=sqrt2;
        cpts[8] = cpts[0].dup();
        return cpts;
    }	
    
    
    /**
       for some platform where regular circle cp with duplicates cannot be
       shown properly. (ex. AI)
       knots for these control points can be uniform.
    */
    public static IVec[] circleCPApprox(IVec center,
					IVec normal,
					IVec rollDir,
					double xradius,
					double yradius){
	if(rollDir==null) rollDir=IVec.zaxis;
	if(normal.cross(rollDir).len2()==0){
	    if(rollDir.cross(IVec.zaxis).len2()==0) rollDir = IVec.xaxis;
	    else rollDir = IVec.zaxis;
	}
	
	IVec y = normal.cross(rollDir);
	IVec x = y.cross(normal);
	x.len(xradius);
	y.len(yradius);
	
	IVec[] cpts = new IVec[10];
	for(int i=0; i<cpts.length-1; i++) cpts[i] = center.dup();
	final double r = Math.sqrt(2) -1;
	cpts[0].add(x);
        cpts[1].add(x).add(y.dup().mul(r)); 
        cpts[2].add(y).add(x.dup().mul(r));
        cpts[3].add(y).sub(x.dup().mul(r));
        cpts[4].sub(x).add(y.dup().mul(r));
        cpts[5].sub(x).sub(y.dup().mul(r));
        cpts[6].sub(y).sub(x.dup().mul(r));
        cpts[7].sub(y).add(x.dup().mul(r));
        cpts[8].add(x).sub(y.dup().mul(r));
        cpts[9] = cpts[0].dup();
	return cpts;
    }
    
    public static IVec[] ovalCPApprox(IVec center, IVec xaxis, IVec yaxis){
        IVec[] cpts = new IVec[10];
        final double r = Math.sqrt(2)-1.0;
        for(int i=0; i<cpts.length-1; i++) cpts[i]=center.dup();
        cpts[0].add(xaxis);
        cpts[1].add(xaxis).add(yaxis.dup().mul(r));
        cpts[2].add(yaxis).add(xaxis.dup().mul(r));
        cpts[3].add(yaxis).sub(xaxis.dup().mul(r));
        cpts[4].sub(xaxis).add(yaxis.dup().mul(r));
        cpts[5].sub(xaxis).sub(yaxis.dup().mul(r));
        cpts[6].sub(yaxis).sub(xaxis.dup().mul(r));
        cpts[7].sub(yaxis).add(xaxis.dup().mul(r));
        cpts[8].add(xaxis).sub(yaxis.dup().mul(r));
        cpts[9] = cpts[0].dup();
        return cpts;
    }
    
    
    public static ICircleGeo circumcircle(IVecI pt1, IVecI pt2, IVecI pt3){
	IVec center = IVec.circumcenter(pt1.get(),pt2.get(),pt3.get());
	double rad = center.dist(pt1);
	IVec nml = pt1.get().nml(pt2,pt3);
	return new ICircleGeo(center,nml,rad);
    }
    
    
    
    public IVecI center;
    public IVecI normal;
    public IDoubleI xradius, yradius;
    
    
    public ICircleGeo(IVecI center, IVecI normal, IDoubleI radius){
	this(center,normal,radius,radius,false);
    }
    public ICircleGeo(IVecI center, IVecI normal, double radius){
	this(center,normal,new IDouble(radius),false);
    }
    public ICircleGeo(IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius){
	this(center,normal,xradius,yradius,false);
    }
    
    public ICircleGeo(IVecI center, IVecI normal, double xradius, double yradius){
	this(center,normal,new IDouble(xradius),new IDouble(yradius),false);
    }
    
    public ICircleGeo(IVecI center, IVecI normal, IVecI rollDir, double radius){
	this(center,normal,rollDir,new IDouble(radius),false);
    }
    
    public ICircleGeo(IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius){
	this(center,normal,rollDir,radius,radius,false);
    }
    
    public ICircleGeo(IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius){
	this(center,normal,rollDir,new IDouble(xradius),new IDouble(yradius),false);
    }
    
    public ICircleGeo(IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius){
	this(center,normal,rollDir,xradius,yradius,false);
    }
    
    public ICircleGeo(IVecI center, IVecI xradiusVec, IVecI yradiusVec){
	this(center,xradiusVec,yradiusVec,false);
    }
    
    
    
    public ICircleGeo(IVecI center, IVecI normal, IDoubleI radius, boolean approx){
	this(center,normal,radius,radius,approx);
    }
    public ICircleGeo(IVecI center, IVecI normal, double radius, boolean approx){
	this(center,normal,new IDouble(radius),approx);
    }
    public ICircleGeo(IVecI center, IVecI normal, double xradius, double yradius, boolean approx){
	this(center,normal,new IDouble(xradius),new IDouble(yradius),approx);
    }
    public ICircleGeo(IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius, boolean approx){
	super();
	this.center = center;
	this.normal = normal;
	this.xradius = xradius;
	this.yradius = yradius;
	if(approx) initApprox();
	else init();
    }
    
    
    public ICircleGeo(IVecI center, IVecI normal, IVecI rollDir, double radius, boolean approx){
	this(center,normal,rollDir,new IDouble(radius),approx);
    }
    
    public ICircleGeo(IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius, boolean approx){
	this(center,normal,rollDir,radius,radius,approx);
    }
    
    public ICircleGeo(IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius, boolean approx){
	this(center,normal,rollDir,new IDouble(xradius),new IDouble(yradius),approx);
    }
    
    /**
       @param rollDir direction of start point
    */
    public ICircleGeo(IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius, boolean approx){    
	if(approx){
	    IVec[] cpts = circleCPApprox(center.get(),normal.get(),rollDir.get(),xradius.x(),yradius.x());
	    super.init(cpts,circleDeg());
	}
	else{
	    IVec4[] cpts = circleCP(center.get(), normal.get(), null, xradius.x(), yradius.x());
	    super.init(cpts,circleDeg(),circleKnots());
	}
    }
    
    public ICircleGeo(IVecI center, IVecI xradiusVec, IVecI yradiusVec, boolean approx){
	if(approx){
	    IVec[] cpts = ovalCPApprox(center.get(),xradiusVec.get(),yradiusVec.get());
	    super.init(cpts,circleDeg());
	}
	else{
	    IVec4[] cpts = ovalCP(center.get(),xradiusVec.get(),yradiusVec.get());
	    super.init(cpts,circleDeg(),circleKnots());
	}
    }
    
    
    public void init(){
	IVec4[] cpts = circleCP(center.get(), normal.get(), null, xradius.x(), yradius.x());
	super.init(cpts,circleDeg(),circleKnots());
    }
    
    public void initApprox(){
	IVec[] cpts = circleCPApprox(center.get(),normal.get(),null,xradius.x(),yradius.x());
	super.init(cpts,circleDeg());
    }
    
    /*
    // for debug
    public void pt(double u, IVec retval){
	//super.pt(u,retval);

	IOut.p("retval = "+retval); //
	
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

	    IOut.p("cpt["+i+"] = "+cpt);
	    IOut.p("i="+i+", w="+w+", n="+n[i]);
	    IOut.p("retval = "+retval);
	    IOut.p("weight = "+weight);
        }
        retval.x/=weight;
        retval.y/=weight;
        retval.z/=weight;
	
	IOut.p("weight = "+weight);
	IOut.p("u="+u+", retval = "+retval); //
	
    }
    */
}
