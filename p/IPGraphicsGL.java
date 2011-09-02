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

import processing.core.*;
import processing.opengl.*;

import javax.media.opengl.*;

import java.awt.event.*;
import java.awt.*;

import com.sun.opengl.util.j2d.Overlay;

import igeo.core.*;
import igeo.gui.*;
import igeo.geo.*;


/**
   A child class of Processing's PGraphic to draw on Processing using OpenGL.
   This class also manages the IServer to manage all the objects in iGeo.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IPGraphicsGL extends PGraphicsOpenGL implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, FocusListener, ComponentListener{
    
    IRootPanel panel;
    IGraphics igg;
    
    Overlay overlay;
    static final Color overlayBG = new Color(0,0,0,0);
    
    boolean overwritePAppletFinish=true;
    boolean finished=false;
    
    boolean overwritePAppletLoop=true;
    boolean looping=true;
    
    public IPGraphicsGL(){ super(); }


    /**
       setParent is called by Processing in the initialization process of Processing.
       Here the initialization proces of iGeo is also done.
       @param parent parent PApplet of Processing.
    */
    public void setParent(PApplet parent){
	
	super.setParent(parent);
	
	/*
	parent.addMouseListener(this);
	parent.addMouseMotionListener(this);
	parent.addKeyListener(this);
	parent.addFocusListener(this);
	*/
	
	/*
	// tmp
	views = new IGView[2];
	
	//views[0] = new IGView(-1,-1,1,Math.PI/4,Math.PI/4,0, 0,0,parent.getWidth()/2,parent.getHeight());
	views[0] = new IGView(-2,-2,2,Math.PI/4,Math.PI/4,0, 0,0,parent.getWidth()/2,parent.getHeight());
	views[0].enableGL();
	views[0].setAxonometricRatio(0.01); //
	//views[0].setPerspectiveRatio(0.2); //
	views[0].setPerspectiveAngle(60*Math.PI/180); //
	views[0].perspective();
	
	
	//views[1] = new IGView(0,0,-1,0,-Math.PI/2,0, parent.getWidth()/2,0,parent.getWidth()/2,parent.getHeight());
	views[1] = new IGView(0,0,-2,0,-Math.PI/2,0, parent.getWidth()/2,0,parent.getWidth()/2,parent.getHeight());
	views[1].enableGL();
	views[1].setAxonometricRatio(0.01); //
	//views[1].setPerspectiveRatio(0.2); //
	views[1].setPerspectiveAngle(60*Math.PI/180); //
	views[1].perspective();
	*/
	
	//viewManager = new IGViewManager(0,0,parent.getWidth(),parent.getHeight());
	//viewManager.showDividedView();
	
	panel = new IGridPanel(0,0,parent.getWidth(),parent.getHeight(),2,2);
	
	panel.show();
	
	IG ig = IG.init(panel);
	
	ig.server().graphicServer().enableGL(); //
	
	
	parent.addMouseListener(panel);
	parent.addMouseMotionListener(panel);
	parent.addMouseWheelListener(panel);
	parent.addKeyListener(panel);
	parent.addFocusListener(panel);
	parent.addComponentListener(panel);
	
	igg = new IGraphics();
	
	//new IPoint(ig,0,0,0);
	
	//final double range=200;
	/*
	for(int i=0; i<100; i++){
	    IPoint p = new IPoint(ig,
				  IRandom.get(-range,range),
				  IRandom.get(-range,range),
				  IRandom.get(-range,range));
	    p.setColor(IRandom.getColor());
	}
	*/
	/*
	for(int i=0; i<10; i++){
	    for(int j=0; j<10; j++){
		//IGCurve crv = new IGCurve(new IVec(i*20-100,j*10-50,-100),new IVec(i*10-50,j*20-100,100));
		IGCurve crv = new IGCurve
		    (new IVec(IGRandom.get(-range,range),
				IGRandom.get(-range,range),
				IGRandom.get(-range,range)),
		     new IVec(IGRandom.get(-range,range),
				IGRandom.get(-range,range),
				IGRandom.get(-range,range)));
		crv.setColor(IGRandom.getColor(64));
	    }
	}
	*/
	
	/*
	for(int i=0; i<10; i++){
	    IGCurve crv = new IGCurve(new IVec[]{new IVec(0,i*10,0),
						   new IVec(0,i*10,100),
						   new IVec(100,i*10,100),
						   new IVec(100,i*10,0)},
				      3);
	    crv.setColor(IGRandom.getColor());
	}
	*/
	/*
	IGSurface srf = new IGSurface(new IVec(0,0,0),
				      new IVec(100,10,0),
				      new IVec(100,10,100),
				      new IVec(10,0,100));
	//srf.setColor(IGRandom.getColor());
	*/
	/*
	for(int i=0; i<10; i++){
	    IGSurface srf = new IGSurface(new IVec(0+2*i,0+10*i,0),
					  new IVec(100+2*i,15+10*i,0),
					  new IVec(100+2*i,20+10*i,100),
					  new IVec(10+2*i,-10+10*i,100));
	    srf.setColor(IGRandom.getColor(32));
	}
	*/
	
	/*
	for(int k=0; k<10; k++){
	    int unum=6;
	    int vnum=8;
	    IVec[][] cpts = new IVec[unum][vnum];
	    double spacing = 40;
	    for(int i=0; i<unum; i++){
		for(int j=0; j<vnum; j++){
		    cpts[i][j] = new IVec(spacing*i-100+k*2, spacing*j-100+k*2,IGRandom.get(-50,50)+k*spacing/2-80);
		}
	    }
	    //(new IGSurface(cpts, 3, 3)).setColor(new Color(255,255,255,32));
	    (new IGSurface(cpts, 3, 3)).setColor(IGRandom.getColor(128));
	}
	*/
	
	
	//overlay = new Overlay(drawable); //
	
	
	//noSmooth();
	    
    }
    
    public void setGLProperties(){
	gl.glEnable(GL.GL_MULTISAMPLE); //
	gl.glEnable(GL.GL_POINT_SMOOTH); //
	gl.glEnable(GL.GL_LINE_SMOOTH); //
	gl.glEnable(GL.GL_POLYGON_SMOOTH); //
	
	gl.glEnable(GL.GL_ALPHA_TEST); //
	//gl.glEnable(GL.GL_BLEND); //
	//gl.glDisable(GL.GL_BLEND); //
	//gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); //
	//gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE); //
	
	gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST); //
	gl.glHint(GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST); //
	gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST); //
	
	//gl.glEnable(GL.GL_NORMALIZE); //
	//gl.glEnable(GL.GL_AUTO_NORMAL); //
	//gl.glShadeModel(GL.GL_SMOOTH); //
	
	//gl.glLightModeli(GL.GL_LIGHT_MODEL_TWO_SIDE, 1); //
	
	//gl.glEnable(GL.GL_DEPTH_TEST); // already enabled in super
	//gl.glDisable(GL.GL_DEPTH_TEST); // ? for transparency
	
	//gl.glEnable(GL.GL_LIGHTING); // test!
	//gl.glEnable(GL.GL_LIGHT1); // test!
    }
    
    /**
       Drawing all the iGeo objects through IRootPanel.
       Overlay is also used to draw 2D graphics on top of OpenGL 3D graphics.
    */
    public void beginDraw(){
	//IOut.p(""); //
	
	super.beginDraw();
	
	int[] viewport = new int[4];
	gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
	
	gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPushMatrix();
        
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPushMatrix();
	
	//gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	
	//gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	
	setGLProperties();
	
	//for(int i=0; i<views.length; i++){ views[i].beginGLView(gl); drawGeometry(gl); }
	/*
	gl.glEnable(GL.GL_MULTISAMPLE); //
	gl.glEnable(GL.GL_POINT_SMOOTH); //
	gl.glEnable(GL.GL_LINE_SMOOTH); //
	gl.glEnable(GL.GL_POLYGON_SMOOTH); //
	
	gl.glEnable(GL.GL_ALPHA_TEST); //
	//gl.glEnable(GL.GL_BLEND); //
	//gl.glDisable(GL.GL_BLEND); //
	//gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); //
	//gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE); //
	
	gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST); //
	gl.glHint(GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST); //
	gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST); //
	
	//gl.glEnable(GL.GL_DEPTH_TEST); // already enabled in super
	//gl.glDisable(GL.GL_DEPTH_TEST); // ? for transparency
	*/
	
	
	if(igg.getGraphics()==null){
	    overlay = new Overlay(drawable); //
	    igg.setGraphics(overlay.createGraphics());
	    igg.getGraphics().setBackground(overlayBG);
	}
	//igg.setGraphics(overlay.createGraphics());
	
	igg.getGraphics().clearRect(0,0,parent.getWidth(),parent.getHeight()); //
	
	
	//overlay = new Overlay(drawable); //
	//Graphics2D g = overlay.createGraphics();
	//igg.setGraphics(g);
	
	igg.setGL(gl);
	
	panel.draw(igg);
	
	//gl.glClear(GL.GL_DEPTH_BUFFER_BIT); // necessary?
	
	gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
	
	gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPopMatrix();
	
	
	// bring the original viewport back
	gl.glViewport(viewport[0], viewport[1], viewport[2], viewport[3]);
	
	
	overlay.markDirty(0,0,parent.getWidth(),parent.getHeight());
	overlay.drawAll();
	
	//g.dispose();
	//igg.getGraphics().dispose();
    }
    
    public void endDraw(){
	super.endDraw();
	if(overwritePAppletFinish) parent.finished=finished;
	if(overwritePAppletLoop) if(looping) parent.loop(); else parent.noLoop();
    }
    
    public void loop(){ if(!looping) looping=true; }
    public void noLoop(){ if(looping) looping=false; }
    
    public void start(){ if(finished) finished=false; }
    public void stop(){ if(!finished) finished=true; }
    
    /*
    //only for debug
    public void drawGeometry(GL gl){
	gl.glScalef(100f,100f,100f); //
	gl.glBegin(GL.GL_TRIANGLES);
	
        // Front
        gl.glColor3f(0.0f, 1.0f, 1.0f); 
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f); 
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        //gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        
        // Right Side Facing Front
        gl.glColor3f(0.0f, 1.0f, 1.0f); 
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f); 
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        //gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); 
        gl.glVertex3f(0.0f, -1.0f, -1.0f);
        
        // Left Side Facing Front
        gl.glColor3f(0.0f, 1.0f, 1.0f); 
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f); 
        gl.glVertex3f(0.0f, -1.0f, -1.0f);
        //gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); 
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        
        // Bottom
        //gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); 
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glColor3f(0.1f, 0.1f, 0.1f); 
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glColor3f(0.2f, 0.2f, 0.2f); 
        gl.glVertex3f(0.0f, -1.0f, -1.0f);
        
        gl.glEnd();
    }
    */
    
    
    public void mousePressed(MouseEvent e){
    }
    public void mouseReleased(MouseEvent e){
    }
    public void mouseClicked(MouseEvent e){
    }
    public void mouseEntered(MouseEvent e){
    }
    public void mouseExited(MouseEvent e){
    }
    public void mouseMoved(MouseEvent e){
    }
    public void mouseDragged(MouseEvent e){
    }
    
    public void mouseWheelMoved(MouseWheelEvent e){
    }
    
    public void keyPressed(KeyEvent e){
    }
    public void keyReleased(KeyEvent e){
    }
    public void keyTyped(KeyEvent e){
    }
    
    public void focusLost(FocusEvent e){
    }
    public void focusGained(FocusEvent e){
    }
    
    public void componentHidden(ComponentEvent e){
    }
    public void componentMoved(ComponentEvent e){
    }
    public void componentResized(ComponentEvent e){
    }
    public void componentShown(ComponentEvent e){
    }
    
}

