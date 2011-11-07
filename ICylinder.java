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

import java.awt.Color;

import igeo.gui.*;

/**
   Cylinder surface class
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ICylinder extends ISurface{
    public IVecI pt1, pt2;
    public IDoubleI radius1, radius2;
    
    public ICylinder(IVecI pt1, IVecI pt2, double radius){
	this(null,pt1,pt2,radius,radius);
    }
    public ICylinder(IServerI s, IVecI pt1, IVecI pt2, double radius){
	this(s,pt1,pt2,radius,radius);
    }
    public ICylinder(IVecI pt1, IVecI pt2, double radius1, double radius2){
	this(null,pt1,pt2,radius1,radius2);
    }
    public ICylinder(IVecI pt1, IVecI pt2, IDoubleI radius){
	this(null,pt1,pt2,radius,radius);
    }
    public ICylinder(IServerI s, IVecI pt1, IVecI pt2, IDoubleI radius){
	this(s,pt1,pt2,radius,radius);
    }
    public ICylinder(IServerI s, IVecI pt1, IVecI pt2, double radius1, double radius2){
	this(s,pt1,pt2,new IDouble(radius1),new IDouble(radius2));
    }
    public ICylinder(IVecI pt1, IVecI pt2, IDoubleI radius1, IDoubleI radius2){
	this(null,pt1,pt2,radius1,radius2);
    }
    public ICylinder(IServerI s, IVecI pt1, IVecI pt2, IDoubleI radius1, IDoubleI radius2){
	super(s);
	this.pt1 = pt1; this.pt2 = pt2;
	this.radius1 = radius1;
	this.radius2 = radius2;
	initCylinder(s);
    }
    
    public void initCylinder(IServerI s){
	IVec p1 = pt1.get();
	IVec p2 = pt2.get();
	IVec normal = p2.diff(p1);
	IVec[][] cpts = new IVec[2][];
	cpts[0] = ICircleGeo.circleCP(p1,normal,radius1.x());
	cpts[1] = ICircleGeo.circleCP(p2,normal,radius2.x());
	surface = new ISurfaceGeo(cpts, 1,ICircleGeo.circleDeg(),
				  INurbsGeo.createKnots(1,2),
				  ICircleGeo.circleKnots());
	//IVec roll = normal.cross(IVec.zaxis);
	//cpts[0] = ICircleGeo.circleCPApprox(pt1,normal,roll,radius1,radius1);
	//cpts[1] = ICircleGeo.circleCPApprox(pt2,normal,roll,radius2,radius2);
	//surface = new ISurfaceGeo(cpts, 1, ICircleGeo.circleDeg());
	super.initSurface(s);
    }
    

}
