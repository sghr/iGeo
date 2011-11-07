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

package igeo;

/**
   Arc object.
   It contains IArcGeo instance inside.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IArc extends ICurve{
    
    public static int arcDeg(){ return IArcGeo.arcDeg(); }
    
    public static IVec4[] arcCP(IVec center, IVec normal, IVec startPt, double angle){
	return IArcGeo.arcCP(center,normal,startPt,angle);
    }
    
    public static IVec4[] arcCP(IVec center, IVec startPt, IVec endPt, boolean flipArcSide){
	return IArcGeo.arcCP(center,startPt,endPt,flipArcSide);
    }
    
    public static IVec4[] arcCP(IVec center, IVec startPt, IVec midPt, IVec endPt, IVec normal){
	return IArcGeo.arcCP(center,startPt,midPt,endPt,normal);
    }
    
    public static double[] arcKnots(double angle){ return IArcGeo.arcKnots(angle); }
    
    //public IArcGeo arc;
    
    public IArc(){}
    
    public IArc(IVecI center, IVecI normal, IVecI startPt, double angle){
	this(null,center,normal,startPt,angle);
    }
    public IArc(IVecI center, IVecI normal, IVecI startPt, IDoubleI angle){
	this(null,center,normal,startPt,angle);
    }
    public IArc(IVecI center, IVecI startPt, double angle){
	this(null,center,new IVec(0,0,1),startPt,angle);
    }
    public IArc(IVecI center, IVecI startPt, IDoubleI angle){
	this(null,center,new IVec(0,0,1),startPt,angle);
    }
    public IArc(double x, double y, double z, double startX, double startY, double startZ, double angle){
	this(null,new IVec(x,y,z),new IVec(0,0,1),new IVec(startX, startY, startZ), angle);
    }
    public IArc(IVecI center, IVecI startPt, IVecI endPt, IBoolI flipArcSide){
	this(null,center,startPt,endPt,flipArcSide);
    }
    public IArc(IVecI center, IVecI startPt, IVecI endPt, boolean flipArcSide){
	this(null,center,startPt,endPt,flipArcSide);
    }
    public IArc(IVecI center, IVecI startPt, IVecI midPt, IVecI endPt, IVecI normal){
	this(null,center,startPt,midPt,endPt,normal);
    }
    
    public IArc(IServerI s, IVecI center, IVecI normal, IVecI startPt, double angle){
	super(s, new IArcGeo(center,normal,startPt,angle));
    }
    public IArc(IServerI s, IVecI center, IVecI normal, IVecI startPt, IDoubleI angle){
	super(s, new IArcGeo(center,normal,startPt,angle));
    }
    public IArc(IServerI s, IVecI center, IVecI startPt, double angle){
	this(s,center,new IVec(0,0,1),startPt,angle);
    }
    public IArc(IServerI s, IVecI center, IVecI startPt, IDoubleI angle){
	this(s,center,new IVec(0,0,1),startPt,angle);
    }
    public IArc(IServerI s, double x, double y, double z, double startX, double startY, double startZ, double angle){
	this(s,new IVec(x,y,z),new IVec(0,0,1),new IVec(startX, startY, startZ), angle);
    }
    public IArc(IServerI s, IVecI center, IVecI startPt, IVecI endPt, IBoolI flipArcSide){
	super(s, new IArcGeo(center,startPt,endPt,flipArcSide));
    }
    public IArc(IServerI s, IVecI center, IVecI startPt, IVecI endPt, boolean flipArcSide){
	super(s, new IArcGeo(center,startPt,endPt,flipArcSide));
    }
    public IArc(IServerI s, IVecI center, IVecI startPt, IVecI midPt, IVecI endPt, IVecI normal){
	super(s, new IArcGeo(center,startPt,midPt,endPt,normal));
    }
    
    
    
    
    
    
}
