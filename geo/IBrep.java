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
import java.util.ArrayList;
import java.awt.Color;


/**
   Class BRep (Boundary Representation) of IObject.
   Implementation of BRep is not complete yet.
   To be completed later.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IBrep extends IObject{
    //public ArrayList<ISurface> surfaces;
    //public ArrayList<ISurfaceGeo> surfaces;
    public ISurfaceGeo[] surfaces;
    
    /** seams defines connection of surfaces (?) */
    //public ArrayList<ICurveGeo> seams;
    //public ICurveGeo[] seams; 
    
    //public ArrayList<IVec> seamPoints;
    //public IVec[] seamPoints;
    
    public boolean solid=false;
    
    
    //public IBrep(){ this((IServerI)null); }
    
    //public IBrep(ISurfaceGeo[] srfs, ICurveGeo[] seams, IVec[] seamPts){ this(null, srfs, seams, seamPts); }
    
    public IBrep(ISurfaceGeo[] srfs){ this(null, srfs); }
    
    //public IBrep(IServerI s){ super(s); surfaces = new ArrayList<ISurfaceGeo>(); }

    /*
    public IBrep(IServerI s, ISurfaceGeo[] srfs, ICurveGeo[] seams, IVec[] seamPts){
	super(s);
	surfaces = srfs;
	this.seams = seams;
	seamPoints = seamPts;
	initBrep(s);
    }
    */
    
    public IBrep(IServerI s, ISurfaceGeo[] srfs){ super(s); surfaces = srfs; initBrep(s); }
    
    public IBrep(IBrep brep){ this(brep.server,brep); }
    
    public IBrep(IServerI s, IBrep brep){
	super(s,brep);
	
	//surfaces = new ArrayList<ISurfaceGeo>(brep.surfaces.size());
	//for(int i=0; i<brep.surfaces.length; i++)surfaces.add(brep.surfaces.get(i).dup()); // deep copy
	if(brep.surfaces!=null){
	    surfaces = new ISurfaceGeo[brep.surfaces.length];
	    for(int i=0; i<surfaces.length; i++) surfaces[i] = brep.surfaces[i].dup(); // deep copy
	}
	/*
	if(brep.seams!=null){
	    seams = new ICurveGeo[brep.seams.length];
	    for(int i=0; i<seams.length; i++) seams[i] = brep.seams[i].dup(); // deep copy
	}
	if(brep.seamPoints!=null){
	    seamPoints = new IVec[brep.seamPoints.length];
	    for(int i=0; i<seamPoints.length; i++) seamPoints[i] = brep.seamPoints[i].dup(); // deep copy
	}
	*/
	initBrep(s);
    }
    
    
    public void initBrep(IServerI s){
	parameter = null; // !!! what should this be? IBrepGeo?
	if(graphics == null) initGraphic(s);
    }
    
    public IGraphicObject createGraphic(IGraphicMode m){
        if(m.isGL()) return new IBrepGraphicGL(this);
        return null;
    }
    
    /*
    public IBrep add(ISurfaceGeo s){ surfaces.add(s); return this; }
    
    public IBrep addSeam(ICurveGeo crv){
	if(seams==null) seams = new ArrayList<ICurveGeo>();
	seams.add(crv);
	return this;
    }
    
    public IBrep addSeamPoint(IVec pt){
	if(seamPoints==null) seamPoints = new ArrayList<IVec>();
	seamPoints.add(pt);
	return this;
    }
    */
    
    public ISurfaceGeo surface(int i){ return surfaces[i]; }
    
    public int surfaceNum(){ return surfaces.length; }
    
    @Override public IBrep dup(){ return new IBrep(this); }
    
    /*
    @Override public void del(){
	super.del();
        for(int i=0;surfaces!=null&&i<surfaces.size();i++) surfaces.get(i).del();
    }
    */
    
    public IBrep name(String nm){ super.name(nm); return this; }
    public IBrep layer(ILayer l){ super.layer(l); return this; }
    
    public IBrep hide(){ super.hide(); return this; }
    public IBrep show(){ super.show(); return this; }
    
    public IBrep clr(Color c){ super.clr(c); return this; }
    public IBrep clr(int gray){ super.clr(gray); return this; }
    public IBrep clr(float fgray){ super.clr(fgray); return this; }
    public IBrep clr(double dgray){ super.clr(dgray); return this; }
    public IBrep clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IBrep clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IBrep clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IBrep clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IBrep clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IBrep clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IBrep clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IBrep clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IBrep clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IBrep hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IBrep hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IBrep hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IBrep hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IBrep setColor(Color c){ super.setColor(c); return this; }
    public IBrep setColor(int gray){ super.setColor(gray); return this; }
    public IBrep setColor(float fgray){ super.setColor(fgray); return this; }
    public IBrep setColor(double dgray){ super.setColor(dgray); return this; }
    public IBrep setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IBrep setColor(float fgray, int falpha){ super.setColor(fgray,falpha); return this; }
    public IBrep setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IBrep setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IBrep setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IBrep setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IBrep setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IBrep setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IBrep setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IBrep setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IBrep setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IBrep setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IBrep setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
}
