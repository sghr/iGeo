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
import java.util.ArrayList;

import igeo.core.*;
import igeo.util.*;

/**
   Class of an agent based on one point, extending IPoint and implements IDynamicObject
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IAgent extends IObject implements IDynamicObject{
    
    public IObject parent=null;
    
    public ArrayList<IDynamicObject> localDynamics;
    
    public int time=0;
    public int lifetime=-1;
    public boolean alive=true;
    
    public IAgent(){ super(); initAgent(); }
    
    public void initAgent(){ super.addDynamics(this); }
    
    /** override IObject.addDynamics to manage dynamics locally.
	Only IAgent is added at IObject */
    public void addDynamics(IDynamicObject dyna){
        if(localDynamics==null) localDynamics = new ArrayList<IDynamicObject>();
        if(!localDynamics.contains(dyna))localDynamics.add(dyna);
    }
    
    // implementation of IDynamicObject
    public IObject parent(){ return parent; }
    public ISubobject parent(IObject parent){ this.parent=parent; return this; }
    
    public boolean alive(){ return alive; }
    
    synchronized public void interact(ArrayList<IDynamicObject> dynamics){
	if(localDynamics!=null) for(IDynamicObject d:localDynamics) d.interact(dynamics); 
    }
    
    synchronized public void update(){
	if(localDynamics!=null) for(IDynamicObject d:localDynamics) d.update();
	
	time++;
	if(lifetime>0&&time>=lifetime){
	    alive=false;
	    del();
	}

	//
	server.update();
    }
    

    /**************************************
     * methods of IObject
     *************************************/
    
    public IAgent name(String nm){ super.name(nm); return this; }
    public IAgent layer(ILayer l){ super.layer(l); return this; }
    
    public IAgent hide(){ super.hide(); return this; }
    public IAgent show(){ super.show(); return this; }
    
    public IAgent clr(Color c){ super.clr(c); return this; }
    public IAgent clr(int gray){ super.clr(gray); return this; }
    public IAgent clr(float fgray){ super.clr(fgray); return this; }
    public IAgent clr(double dgray){ super.clr(dgray); return this; }
    public IAgent clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IAgent clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IAgent clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IAgent clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IAgent clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IAgent clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IAgent clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IAgent clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IAgent clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IAgent hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IAgent hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IAgent hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IAgent hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IAgent setColor(Color c){ super.setColor(c); return this; }
    public IAgent setColor(int gray){ super.setColor(gray); return this; }
    public IAgent setColor(float fgray){ super.setColor(fgray); return this; }
    public IAgent setColor(double dgray){ super.setColor(dgray); return this; }
    public IAgent setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IAgent setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IAgent setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IAgent setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IAgent setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IAgent setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IAgent setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IAgent setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IAgent setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IAgent setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IAgent setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IAgent setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IAgent setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    
}
