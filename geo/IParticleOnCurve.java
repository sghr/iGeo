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
   Class of an implementation of IDynamicObject to have physical attributes of point.
   It has attributes of position, velocity, acceleration, force, and mass.
   Position is provided from outside to be linked.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IParticleOnCurve extends IParticle{
    
    ICurveI curve;
    double upos;
    double uvel;
    double uacc;
    double ufrc;
    
    IVec utan;
    
    public IParticleOnCurve(ICurveI curve){ this(curve,0); }
    public IParticleOnCurve(ICurveI curve, double u){ super(curve.pt(u).get()); upos=u; this.curve=curve; }
    
    synchronized public void interact(IDynamicObject obj){
	
    }
    synchronized public void update(){
	//super.update();
	
	if(fixed || curve==null) return;
	
	utan = curve.tan(upos).get();
	
	if(frc.projectToVec(utan) > 0) ufrc = frc.len()/utan.len();
	else ufrc = -frc.len()/utan.len();
	
	uvel += ufrc/mass*IConfig.dynamicsUpdateSpeed/1000.0;
	uvel *= friction;
	
	// out of range of u 0.0-1.0
	if( (upos + uvel*IConfig.dynamicsUpdateSpeed/1000.0) < 0.0 ){
	    if( curve.isClosed() ){ // cyclic
		upos += uvel*IConfig.dynamicsUpdateSpeed/1000.0;
		upos -= Math.floor(upos); // fit within 0.0 - 1.0.
	    }
	    else{ upos=0.0; uvel=0.0; }
	}
	else if( (upos + uvel*IConfig.dynamicsUpdateSpeed/1000.0) > 1.0 ){
	    if( curve.isClosed() ){ // cyclic
		upos += uvel*IConfig.dynamicsUpdateSpeed/1000.0;
		upos -= Math.floor(upos); // fit within 0.0 - 1.0.
	    }
	    else{ upos=1.0; uvel=0.0; }
	}
	else{ upos += uvel*IConfig.dynamicsUpdateSpeed/1000.0; }
	
	pos.set(curve.pt(upos));
	
	frc.set(0,0,0);
	
    }
    
}
