/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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

import igeo.*;
import igeo.gui.*;

/**
   A child class of Processing's PGraphic to draw on Processing using OpenGL.
   This class also manages the IServer to manage all the objects in iGeo.
   
   @author Satoru Sugihara
   @version 0.7.1.0;
*/
public class PIGraphicsGL extends PGraphicsOpenGL /*implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, FocusListener, ComponentListener*/{
    
    public IPanel panel;
    public IGraphics igg;
    
    /**
       To draw Java2D graphic over OpenGL graphic.
    */
    public Overlay overlay;
    
    /**
       Background color of overlay should be transparent.
    */
    static public Color overlayBG = new Color(0,0,0,0);
    
    /**
       To show iGeo correctly in Processing's basic mode, this needs to be true.
    */
    public boolean overwritePAppletFinish=true;
    public boolean finished=false;
    
    /**
       To show iGeo correctly in Processing's basic mode, this needs to be true.
    */
    public boolean overwritePAppletLoop=true;
    public boolean looping=true;
    
    
    public PIGraphicsGL(){ super(); }
    
    
    /**
       setParent is called by Processing in the initialization process of Processing.
       Here the initialization proces of iGeo is also done.
       @param parent parent PApplet of Processing.
    */
    public void setParent(PApplet parent){
	
	super.setParent(parent);

	// initialize root GUI
	panel = new IGridPanel(0,0,parent.getWidth(),parent.getHeight(),2,2);
	panel.show();
	
	// initialize iGeo 
	IG ig = IG.init(panel);
	
	ig.server().graphicServer().enableGL(); //
	//ig.setBasePath(parent.sketchPath("")); // not sketchPath
	ig.setBasePath(parent.dataPath("")); // for default path to read/write files
	
	parent.addMouseListener(panel);
	parent.addMouseMotionListener(panel);
	parent.addMouseWheelListener(panel);
	parent.addKeyListener(panel);
	parent.addFocusListener(panel);
	parent.addComponentListener(panel);
	
	igg = new IGraphics();
	
	//noSmooth();
	
	if(PIConfig.drawBeforeProcessing) parent.registerPre(this);
	else parent.registerDraw(this);
	parent.registerPost(this);
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
    
    
    public void pre(){ drawIG(); }
    public void draw(){ drawIG(); }
    
    /**
       Drawing all the iGeo objects through IPanel.
       Overlay is also used to draw 2D graphics on top of OpenGL 3D graphics.
    */
    public void drawIG(){
	
	int[] viewport = new int[4];
	gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
	
	gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPushMatrix();
        
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPushMatrix();
	
	if(PIConfig.resetGLDepthBefore) gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
	
	//gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	
	//gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	
	setGLProperties();
	
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
	
	if(PIConfig.resetGLDepthAfter) gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
	
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
    
    
    public void post(){
	if(overwritePAppletFinish) parent.finished=finished;
	if(overwritePAppletLoop) if(looping) parent.loop(); else parent.noLoop();
    }
    
    public void loop(){ if(!looping) looping=true; }
    public void noLoop(){ if(looping) looping=false; }
    
    public void start(){ if(finished) finished=false; }
    public void stop(){ if(!finished) finished=true; }
    
    /*
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
    */
}

