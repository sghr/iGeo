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
   Box geometry
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IBox extends IBrep{

    /**********************************************
     * static methods
     **********************************************/
    
    public static ISurfaceGeo[] getBoxFaces(IVecI[][][] corners){
	ISurfaceGeo[] faces = new ISurfaceGeo[6];
	for(int i=0; i<2; i++){
	    faces[i] = new ISurfaceGeo(corners[0][0][i],corners[1-i][i][i],
				       corners[1][1][i],corners[i][1-i][i]);
	    faces[2+i] = new ISurfaceGeo(corners[i][0][0],corners[i][1-i][i],
					 corners[i][1][1],corners[i][i][1-i]);
	    faces[4+i] = new ISurfaceGeo(corners[0][i][0],corners[i][i][1-i],
					 corners[1][i][1],corners[1-i][i][i]);
	}
	return faces;
    }
    /*
    public static ICurveGeo[] getBoxEdges(IVec[][][] corners){
	ICurveGeo edges[] = new ICurveGeo[12];
	for(int i=0; i<2; i++){
	    for(int j=0; j<2; j++){
		edges[i*2+j] = new ICurveGeo(corners[i][j][0],corners[i][j][1]);
		edges[4+i*2+j] = new ICurveGeo(corners[0][i][j],corners[1][i][j]);
		edges[8+i*2+j] = new ICurveGeo(corners[j][0][i],corners[j][1][i]);
	    }
	}
	return edges;
    }
    public static IVec[] getBoxVertices(IVec[][][] corners){
	return new IVec[]{ corners[0][0][0], corners[0][0][1],
			   corners[0][1][0], corners[0][1][1],
			   corners[1][0][0], corners[1][0][1],
			   corners[1][1][0], corners[1][1][1] };
    }
    */
    
    
    /**********************************************
     * object methods
     **********************************************/
    
    public IBox(double x, double y, double z, double size){ this(null,x,y,z,size); }
    public IBox(double x, double y, double z, double w, double h, double d){
	this(null,x,y,z,w,h,d);
    }
    public IBox(IVecI origin, double width, double height, double depth){
	this(null, origin, width, height, depth);
    }
    public IBox(IVecI origin, IVecI xvec, IVecI yvec, IVecI zvec){
	this(null, origin, xvec, yvec, zvec);
    }
    /**
       order of points in the argument is going round at the bottom and going round at the top
    */
    public IBox(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4,
		IVecI pt5, IVecI pt6, IVecI pt7, IVecI pt8 ){
	this(null, new IVecI[][][]{
		 new IVecI[][]{ new IVecI[]{ pt1, pt5 }, new IVecI[]{ pt4, pt8 }},
		 new IVecI[][]{ new IVecI[]{ pt2, pt6 }, new IVecI[]{ pt3, pt7 }}});
    }
    public IBox(IVecI[][][] corners){ this(null, corners); }
    public IBox(IServerI s, double x, double y, double z, double size){
	this(s, new IVec(x,y,z), new IVec(size,0,0), new IVec(0,size,0), new IVec(0,0,size));
    }
    
    public IBox(IServerI s, double x, double y, double z, double w, double h, double d){
	this(s, new IVec(x,y,z), new IVec(w,0,0), new IVec(0,h,0), new IVec(0,0,d));
    }
    
    public IBox(IServerI s, IVecI origin, double width, double height, double depth){
	this(s, origin, new IVec(width,0,0), new IVec(0,height,0), new IVec(0,0,depth));
    }
    
    public IBox(IServerI s, IVecI origin, IVecI xvec, IVecI yvec, IVecI zvec){
	this(s, new IVecI[][][]{
		 new IVecI[][]{
		     new IVecI[]{ origin.dup(), origin.sum(xvec) },
		     new IVecI[]{ origin.sum(yvec), origin.sum(xvec).add(yvec) }
		 },
		 new IVecI[][]{
		     new IVecI[]{ origin.sum(zvec), origin.sum(zvec).add(xvec) },
		     new IVecI[]{ origin.sum(zvec).add(yvec), origin.sum(zvec).add(xvec).add(yvec) }
		 }
	     });
    }

    /**
       order of points in the argument is going round at the bottom and going round at the top
    */
    public IBox(IServerI s, IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4,
		IVecI pt5, IVecI pt6, IVecI pt7, IVecI pt8 ){
	this(s, new IVecI[][][]{
		 new IVecI[][]{ new IVecI[]{ pt1, pt5 }, new IVecI[]{ pt4, pt8 }},
		 new IVecI[][]{ new IVecI[]{ pt2, pt6 }, new IVecI[]{ pt3, pt7 }}});
    }
    
    public IBox(IServerI s, IVecI[][][] corners){
	//super(s, getBoxFaces(corners), getBoxEdges(corners), getBoxVertices(corners));
	super(s, getBoxFaces(corners));
	solid=true;
    }
    public IBox(IBox box){ this(box.server,box); }
    
    public IBox(IServerI s, IBox box){ super(s,box); solid=true; }
    
    
    @Override public IBox dup(){ return new IBox(this); }
    
    
    public IBox name(String nm){ super.name(nm); return this; }
    public IBox layer(ILayer l){ super.layer(l); return this; }
    
    public IBox hide(){ super.hide(); return this; }
    public IBox show(){ super.show(); return this; }
    
    public IBox clr(Color c){ super.clr(c); return this; }
    public IBox clr(int gray){ super.clr(gray); return this; }
    public IBox clr(float fgray){ super.clr(fgray); return this; }
    public IBox clr(double dgray){ super.clr(dgray); return this; }
    public IBox clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IBox clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IBox clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IBox clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IBox clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IBox clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IBox clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IBox clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IBox clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IBox hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IBox hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IBox hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IBox hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }
    
    public IBox setColor(Color c){ super.setColor(c); return this; }
    public IBox setColor(int gray){ super.setColor(gray); return this; }
    public IBox setColor(float fgray){ super.setColor(fgray); return this; }
    public IBox setColor(double dgray){ super.setColor(dgray); return this; }
    public IBox setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IBox setColor(float fgray, int falpha){ super.setColor(fgray,falpha); return this; }
    public IBox setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IBox setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IBox setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IBox setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IBox setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IBox setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IBox setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IBox setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IBox setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IBox setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IBox setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
}
