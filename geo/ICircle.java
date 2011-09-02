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

import igeo.core.*;
import igeo.gui.*;

/**
   Circle object.
   It contains ICircleGeo instance inside.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ICircle extends ICurve{
    
    public static int circleDeg(){ return ICircleGeo.circleDeg(); }
    
    public static double[] circleKnots(){ return ICircleGeo.circleKnots(); }
    
    public static IVec4[] circleCP(IVec center, double radius){
        return ICircleGeo.circleCP(center,radius);
    }
    public static IVec4[] circleCP(IVec center, IVec normal, double radius){
        return ICircleGeo.circleCP(center,normal,radius);
    }
    public static IVec4[] circleCP(IVec center, IVec normal, IVec rollDir, double radius){
        return ICircleGeo.circleCP(center,normal,rollDir,radius);
    }
    public static IVec4[] circleCP(IVec center, IVec normal, IVec rollDir,
                                   double xradius, double yradius){
	return ICircleGeo.circleCP(center,normal,rollDir,xradius,yradius);
    }
    
    public static IVec4[] ovalCP(IVec center, IVec xaxis, IVec yaxis){
	return ICircleGeo.ovalCP(center,xaxis,yaxis);
    }
    
    public static IVec[] circleCPApprox(IVec center,IVec normal, IVec rollDir,
                                        double xradius, double yradius){
	return ICircleGeo.circleCPApprox(center,normal,rollDir,xradius,yradius);
    }
    
    public static IVec[] ovalCPApprox(IVec center, IVec xaxis, IVec yaxis){
	return ICircleGeo.ovalCPApprox(center,xaxis,yaxis);
    }
    
    //ICircleGeo circle;
    
    public ICircle(IVecI center, IVecI normal, IDoubleI radius){
	this((IServerI)null,center,normal,radius);
    }
    public ICircle(IVecI center, IVecI normal, double radius){
	this((IServerI)null,center,normal,radius);
    }
    public ICircle(IVecI center, IDoubleI radius){
	this((IServerI)null,center,new IVec(0,0,1),radius);
    }
    public ICircle(IVecI center, double radius){
	this((IServerI)null,center,new IVec(0,0,1),radius);
    }
    public ICircle(double x, double y, double z, double radius){
	this((IServerI)null,new IVec(x,y,z),new IVec(0,0,1),radius);
    }
    
    public ICircle(IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius){
	this((IServerI)null,center,normal,xradius,yradius);
    }
    public ICircle(IVecI center, IVecI normal, double xradius, double yradius){
	this((IServerI)null,center,normal,xradius,yradius);
    }
    public ICircle(IVecI center, IDoubleI xradius, IDoubleI yradius){
	this((IServerI)null,center,new IVec(0,0,1),xradius,yradius);
    }
    public ICircle(IVecI center, double xradius, double yradius){
	this((IServerI)null,center,new IVec(0,0,1),xradius,yradius);
    }
    public ICircle(double x, double y, double z, double xradius, double yradius){
	this((IServerI)null,new IVec(x,y,z),new IVec(0,0,1),xradius,yradius);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, double radius){
	this((IServerI)null,center,normal,rollDir,radius);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius){
	this((IServerI)null,center,normal,rollDir,radius);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius){
	this((IServerI)null,center,normal,rollDir,xradius,yradius);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius){
	this((IServerI)null,center,normal,rollDir,xradius,yradius);
    }
    
    public ICircle(IVecI center, IVecI xradiusVec, IVecI yradiusVec){
	this((IServerI)null,center,xradiusVec,yradiusVec);
    }
    
    
    public ICircle(IVecI center, IVecI normal, IDoubleI radius, boolean approx){
	this((IServerI)null,center,normal,radius,approx);
    }
    public ICircle(IVecI center, IVecI normal, double radius, boolean approx){
	this((IServerI)null,center,normal,radius,approx);
    }
    public ICircle(IVecI center, IDoubleI radius, boolean approx){
	this((IServerI)null,center,new IVec(0,0,1),radius,approx);
    }
    public ICircle(IVecI center, double radius, boolean approx){
	this((IServerI)null,center,new IVec(0,0,1),radius,approx);
    }
    public ICircle(double x, double y, double z, double radius, boolean approx){
	this((IServerI)null,new IVec(x,y,z),new IVec(0,0,1),radius, approx);
    }
    public ICircle(IVecI center, IVecI normal, double xradius, double yradius, boolean approx){
	this((IServerI)null,center,normal,xradius,yradius,approx);
    }
    public ICircle(IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius, boolean approx){
	this((IServerI)null,center,normal,xradius,yradius,approx);
    }
    public ICircle(IVecI center, double xradius, double yradius, boolean approx){
	this((IServerI)null,center,new IVec(0,0,1),xradius,yradius,approx);
    }
    public ICircle(IVecI center, IDoubleI xradius, IDoubleI yradius, boolean approx){
	this((IServerI)null,center,new IVec(0,0,1),xradius,yradius,approx);
    }
    public ICircle(double x, double y, double z, double xradius, double yradius, boolean approx){
	this((IServerI)null,new IVec(x,y,z),new IVec(0,0,1),xradius,yradius,approx);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, double radius, boolean approx){
	this((IServerI)null,center,normal,rollDir,radius,approx);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius, boolean approx){
	this((IServerI)null,center,normal,rollDir,radius,approx);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius, boolean approx){
	this((IServerI)null,center,normal,rollDir,xradius,yradius,approx);
    }
    
    public ICircle(IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius, boolean approx){
	this((IServerI)null,center,normal,rollDir,xradius,yradius,approx);
    }
    
    public ICircle(IVecI center, IVecI xradiusVec, IVecI yradiusVec, boolean approx){
	this((IServerI)null,center,xradiusVec,yradiusVec,approx);
    }
    
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IDoubleI radius){
	super(s, new ICircleGeo(center,normal,radius,radius,false));
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, double radius){
	super(s, new ICircleGeo(center,normal,new IDouble(radius),false));
    }
    public ICircle(IServerI s, IVecI center, IDoubleI radius){
	this(s,center,new IVec(0,0,1),radius);
    }
    public ICircle(IServerI s, IVecI center, double radius){
	this(s,center,new IVec(0,0,1),radius);
    }
    public ICircle(IServerI s, double x, double y, double z, double radius){
	this(s,new IVec(x,y,z),new IVec(0,0,1),radius);
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius){
	super(s, new ICircleGeo(center,normal,xradius,yradius,false));
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, double xradius, double yradius){
	super(s, new ICircleGeo(center,normal,new IDouble(xradius),new IDouble(yradius),false));
    }
    public ICircle(IServerI s, IVecI center, IDoubleI xradius, IDoubleI yradius){
	super(s, new ICircleGeo(center,new IVec(0,0,1),xradius,yradius,false));
    }
    public ICircle(IServerI s, IVecI center, double xradius, double yradius){
	super(s, new ICircleGeo(center,new IVec(0,0,1),new IDouble(xradius),new IDouble(yradius),false));
    }
    public ICircle(IServerI s, double x, double y, double z, double xradius, double yradius){
	this(s,new IVec(x,y,z),new IVec(0,0,1),xradius,yradius);
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, double radius){
	super(s,new ICircleGeo(center,normal,rollDir,radius,false));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius){
	super(s,new ICircleGeo(center,normal,rollDir,radius,false));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius){
	super(s,new ICircleGeo(center,normal,rollDir,xradius,yradius,false));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius){
	super(s,new ICircleGeo(center,normal,rollDir,xradius,yradius,false));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI xradiusVec, IVecI yradiusVec){
	super(s,new ICircleGeo(center,xradiusVec,yradiusVec,false));
    }
    
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IDoubleI radius, boolean approx){
	super(s, new ICircleGeo(center,normal,radius,radius,approx));
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, double radius, boolean approx){
	super(s, new ICircleGeo(center,normal,new IDouble(radius),approx));
    }
    public ICircle(IServerI s, IVecI center, IDoubleI radius, boolean approx){
	this(s,center,new IVec(0,0,1),radius, approx);
    }
    public ICircle(IServerI s, IVecI center, double radius, boolean approx){
	this(s,center,new IVec(0,0,1),radius, approx);
    }
    public ICircle(IServerI s, double x, double y, double z, double radius, boolean approx){
	this(s,new IVec(x,y,z),new IVec(0,0,1),radius,approx);
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, double xradius, double yradius, boolean approx){
	super(s, new ICircleGeo(center,normal,new IDouble(xradius),new IDouble(yradius),approx));
    }
    public ICircle(IServerI s, IVecI center, IVecI normal, IDoubleI xradius, IDoubleI yradius, boolean approx){
	super(s, new ICircleGeo(center,normal,xradius,yradius,approx));
    }
    public ICircle(IServerI s, IVecI center, double xradius, double yradius, boolean approx){
	super(s, new ICircleGeo(center,new IVec(0,0,1),new IDouble(xradius),new IDouble(yradius),approx));
    }
    public ICircle(IServerI s, IVecI center, IDoubleI xradius, IDoubleI yradius, boolean approx){
	super(s, new ICircleGeo(center,new IVec(0,0,1),xradius,yradius,approx));
    }
    public ICircle(IServerI s, double x, double y, double z, double xradius, double yradius,boolean approx){
	this(s,new IVec(x,y,z),new IVec(0,0,1),xradius,yradius,approx);
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, double radius, boolean approx){
	super(s,new ICircleGeo(center,normal,rollDir,radius,approx));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, IDoubleI radius, boolean approx){
	super(s,new ICircleGeo(center,normal,rollDir,radius,approx));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, double xradius, double yradius, boolean approx){
	super(s,new ICircleGeo(center,normal,rollDir,xradius,yradius,approx));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI normal, IVecI rollDir, IDoubleI xradius, IDoubleI yradius, boolean approx){
	super(s,new ICircleGeo(center,normal,rollDir,xradius,yradius,approx));
    }
    
    public ICircle(IServerI s, IVecI center, IVecI xradiusVec, IVecI yradiusVec, boolean approx){
	super(s,new ICircleGeo(center,xradiusVec,yradiusVec,approx));
    }
    
    
}
