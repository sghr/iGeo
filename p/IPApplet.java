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

package igeo.p;

import java.awt.*;
import java.util.ArrayList;

import processing.core.*;

import igeo.core.IG;
import igeo.geo.*;
import igeo.util.*;
import igeo.geo.*;
import igeo.gui.*;


/**
   A child class of Processing's PApplet.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IPApplet extends PApplet{
    
    public void setup(){
	//size(600,600, IG.IG3D);
	size(600,600, IG.GL);

	/*
	final double range=200;
	for(int i=0; i<10000; i++){
	    IPoint p = new IPoint(
				    IRandom.get(-range,range),
				    IRandom.get(-range,range),
				    IRandom.get(-range,range));
	    p.color(IRandom.color(64));
	}
	*/
	
	/*
	IG.open("data/test_srf1.obj"); //
	IG.open("data/test_srf2.obj"); //
	IG.open("data/test_srf3.obj"); //
	IG.open("data/test_srf4.obj"); //
	IG.open("data/test_srf5.obj"); //
	IG.open("data/test_srf6.obj"); //
	IG.open("data/test_srf7.obj"); //
	IG.open("data/test_srf8.obj"); //
	IG.open("data/test_srf22.obj"); //
	IG.open("data/test_srf24.obj"); //
	*/
	//IG.open("data/test_trimsrf1.obj"); //
	//IG.open("data/test_trimsrf2.obj"); //
	//IG.open("data/test_trimsrf3.obj"); //
	//IG.open("data/test_srf25.obj"); //
	// //IG.open("data/test_srf26.obj"); //
	// //IG.open("data/test_srf27.obj"); //
	// //IG.open("data/test_srf28.obj"); //
	// //IG.open("data/test_srf29.obj"); //
	// // IG.open("data/test_trimsrf3.obj"); //
	//IG.open("data/test_trimsrf4.obj"); //
	// //IG.open("data/test_crv19.obj"); //
	// //IG.open("data/test_crv20.obj"); //
	// //IG.open("data/test_crv21.obj"); //
	
	// //IG.open("data/test_srf30.obj"); //
	
	// //IG.open("data/test_crv22.obj"); //
	// //IG.open("data/test_crv23.obj"); //
	
	//IG.open("data/test_trimsrf5.obj"); //
	//IG.open("data/test_trimsrf6.obj"); //
	//IG.open("data/test_trimsrf7.obj"); //
	
	//IGOut.p("distToLine : "+(new IVec2(0,0)).distToLine(new IVec2(-1,0),new IVec2(0,2)));
	
	//IG.open("data_ark/ark_liner_line20out1w_frame.obj"); //
	//IG.open("data/test_trimsrf8.obj"); //
	//IG.open("data/test_trimsrf9.obj"); //
	//IG.open("data/test_trimsrf10.obj"); //
	//IG.open("data/test_trimsrf11.obj"); //
	//IG.open("data/test_trimsrf12.obj"); //
	//IG.open("data/test_trimsrf13.obj"); //
	//IG.open("data/test_trimsrf14.obj"); //
	//IG.open("data/test_trimsrf15.obj"); //
	//IG.open("data/test_trimsrf16.obj"); //
	//IG.open("data/test_trimsrf17.obj"); //
	//IG.open("data/test_trimsrf18.obj"); //
	//IG.open("data/test_trimsrf19.obj"); //
	IG.open("data/test_trimsrf20.obj"); //
	
	//IG.open("data/test_out1.obj"); //
	
	
	//for(int i=0; i<100; i++) new IPoint(IRandom.getPoint(0,0,0,10,10,10));
	
	IG.outputFile("data/test_out1.obj");
	
	
	for(int i=0; i<IG.current().server().objectNum(); i++){
	    IG.current().server().getObject(i).setColor(IRandom.getColor(128));
	    //IG.current().server().getObject(i).setColor(IRandom.getColor());
	    //IG.current().server().getObject(i).setColor(new Color(128,128,128,64));//
	    //IG.current().server().getObject(i).setColor(new Color(128,128,128,255));//
	    //IG.current().server().getObject(i).setColor(IGRndom.getColor(30));
	}
	
	//ArrayList<IVec[]> cptsArray= new ArrayList<IVec[]>();
	
	for(int i=0; i<IG.current().server().objectNum(); i++){
	    if(IG.current().server().getObject(i) instanceof ICurve){
		ICurve crv = (ICurve)IG.current().server().getObject(i);
		/*
		IVec2[] pts = new IVec2[crv.cpNum()-1];
		for(int j=0; j<crv.cpNum()-1; j++) pts[j]=new IVec2(crv.cp(j));
		double angle = IVec2.sumOfExteriorAngles(pts,true);
		IGOut.p("curve <cpnum="+crv.cpNum()+">: sum of corner angles = "+
			(angle/Math.PI*180)); //
		*/
		/*
		IVec2I[] pts=null;
		if(crv.isClosed()) pts= new IVec2I[crv.cpNum()-1];
		else  pts= new IVec2I[crv.cpNum()];
		for(int j=0; j<pts.length; j++) pts[j] = new IVec2(crv.cp(j));
		pts = IVec2.removeStraightPoints(pts, crv.isClosed());
		for(IVec2I v:pts){
		    IPoint pt = new IPoint(new IVec(v));
		    pt.setColor(Color.blue);
		    pt.setSize(10);
		}
		*/
		
		/*
		IVec2I[] pts=null;
		if(crv.isClosed()) pts= new IVec2I[crv.cpNum()-1];
		else  pts= new IVec2I[crv.cpNum()];
		for(int j=0; j<pts.length; j++) pts[j] = new IVec2(crv.cp(j));
		
		IVec2[] isct = IVec2.intersectPolylineAndYLine(pts, crv.isClosed(), true, 1.0);
		
		for(int j=0; isct!=null&&j<isct.length; j++){
		    IPoint pt = new IPoint(new IVec(isct[j]));
		    pt.setColor(Color.blue);
		    pt.setSize(10);
		}
		*/
		
		/*
		IVec2I[] pts = new IVec2[crv.num()-1];
		for(int j=0; j<pts.length; j++) pts[j] = new IVec2(crv.cp(j));
		pts = IVec2.removeStraightPoints(pts,true);
		IVec[] cpts = new IVec[pts.length+1];
		for(int j=0; j<pts.length; j++) cpts[j] = new IVec(pts[j]);
		cpts[cpts.length-1]=cpts[0];
		cptsArray.add(cpts);
		//new ICurve(cpts);
		for(int j=0; j<cpts.length-1; j++){
		    IPoint pt = new IPoint(cpts[j]);
		    pt.setColor(Color.red);
		    pt.setSize(5);
		}
		*/
	    }
	}
	
	//for(int i=0; i<cptsArray.size(); i++) new ICurve(cptsArray.get(i)); //
	
	//background(0);
    }
    
    //public void draw(){}
    
    /*
    public void draw(){
	if(true) return; //
	ambientLight(63, 31, 31);
        directionalLight(255,255,255,-1,0,0);
        pointLight(63, 127, 255, mouseX, mouseY, 200);
        spotLight(100, 100, 100, mouseX, mouseY, 200, 0, 0, -1, PI, 2);
        
        camera(mouseX, mouseY, 200, width/2, height/2, 0, 0, 1, 0);
        
        translate(width / 2, height / 2, -20);
        int dim = 18;
        for(int i = -height/2; i < height/2; i += dim*1.4) {
            for(int j = -width/2; j < width/2; j += dim*1.4) {
                pushMatrix();
                translate(i,j);
                rotateX(radians(30));
                rotateY(radians(30));
                box(dim,dim,dim);
                popMatrix();
            }
	}
    }
    */
}
