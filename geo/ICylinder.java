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

import java.awt.Color;

import igeo.core.*;
import igeo.gui.*;

/**
   Cylinder surface class
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class ICylinder extends ISurface{
    public IVec pt1, pt2;
    public double radius;
    
    public ICylinder(IVec pt1, IVec pt2, double radius){
	this(null,pt1,pt2,radius);
    }
    public ICylinder(IServerI s, IVec pt1, IVec pt2, double radius){
	super(s);
	this.pt1 = pt1; this.pt2 = pt2; this.radius = radius;
	initCylinder(s);
    }
    
    public void initCylinder(IServerI s){
	IVec normal = pt2.diff(pt1);
	IVec[][] cpts = new IVec[2][];
	cpts[0] = ICircleGeo.circleCP(pt1,normal,radius);
	cpts[1] = ICircleGeo.circleCP(pt2,normal,radius);
	surface = new ISurfaceGeo(cpts, 1,ICircleGeo.circleDeg(),
				  INurbsGeo.createKnots(1,2),
				  ICircleGeo.circleKnots());
	//IVec roll = normal.cross(IVec.zaxis);
	//cpts[0] = ICircleGeo.circleCPApprox(pt1,normal,roll,radius,radius);
	//cpts[1] = ICircleGeo.circleCPApprox(pt2,normal,roll,radius,radius);
	//surface = new ISurfaceGeo(cpts, 1, ICircleGeo.circleDeg());
	super.initSurface(s);
    }
    

}
