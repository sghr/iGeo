/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2013 Satoru Sugihara

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
import java.awt.geom.*;
import java.awt.*;
import java.util.*;

//import com.sun.opengl.util.j2d.Overlay;
import com.jogamp.opengl.util.awt.Overlay; // processing 2.0

import igeo.*;
import igeo.gui.*;


/**
   A child class of Processing's PGraphic to draw on Processing using OpenGL.
   This class also manages the IServer to manage all the objects in iGeo.
   
   @author Satoru Sugihara
*/
public class PIGraphicsGL extends PGraphics3D /*PGraphicsOpenGL*/ 
				   //implements IGraphics3D
    /*implements IPanelAdapter*/ /*MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, FocusListener,*/ /*ComponentListener*/
{
    
    public IPanel panel;
    public IGraphics3D igg;
    
    /** To draw Java2D graphic over OpenGL graphic. */
    public Overlay overlay;
    
    /** Background color of overlay should be transparent. */
    static public Color overlayBG = new Color(0,0,0,0);
    
    /** To show iGeo correctly in Processing's basic mode, this needs to be true. */
    public boolean overwritePAppletFinish=true;
    public boolean finished=false;
    
    /** To show iGeo correctly in Processing's basic mode, this needs to be true. */
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
	panel.setVisible(true); 
	
	panel.setParent(parent);
	//panel.setAdapter(this);
	
	// initialize iGeo 
	IG ig = IG.init(panel);
	ig.server().graphicServer().enableGL(); //
	//ig.setBasePath(parent.sketchPath("")); // not sketchPath
	
	ig.setOnline(parent.online);
	
	if(!parent.online){ // only when running local
	    ig.setBasePath(parent.dataPath("")); // for default path to read/write files
	}
	
	ig.setInputWrapper(new PIInput(parent));
	

	parent.removeMouseListener(parent);
	parent.removeMouseMotionListener(parent);
	parent.removeMouseWheelListener(parent);
	parent.removeKeyListener(parent);
	parent.removeFocusListener(parent);
	//parent.removeComponentListener(parent);
	
	parent.addMouseListener(panel);
	parent.addMouseMotionListener(panel);
	parent.addMouseWheelListener(panel);
	parent.addKeyListener(panel);
	parent.addFocusListener(panel);
	parent.addComponentListener(panel);
	
	
	if(parent.frame!=null){
	    parent.frame.addWindowListener(panel);
	}
	
	
	//igg = new IGraphics();
	//igg = new IGraphicsGL();
	//igg = this;
	igg = new IGraphicsGL2();
	
	//noSmooth();
	
	
	if(PIConfig.drawBeforeProcessing){ parent.registerPre(this); }
	else{ parent.registerDraw(this); }
	parent.registerPost(this);
	
	
	if(PIConfig.resizable && parent.frame!=null){ // frame seems to be null in exported applet
	    parent.frame.setResizable(true);
	}
	
	//super.hints[DISABLE_OPENGL_2X_SMOOTH]=true;  //
	//super.hints[ENABLE_OPENGL_4X_SMOOTH]=true;  //



	// shader test
	//String vertexShader = "#version 110\n attribute vec2 position;\n varying vec2 texcoord;\n void main(){\n gl_Position = vec4(position, 0.0, 1.0);\n texcoord = position * vec2(0.5) + vec2(0.5);\n }";
	//String fragmentShader = "#version 110\n uniform float fade_factor;\n uniform sampler2D textures[2];\n varying vec2 texcoord;\n void main(){\n gl_FragColor = mix( texture2D(textures[0], texcoord), texture2D(textures[1], texcoord), fade_factor); }";
	
    }
    
    @Override
    public void initPrimary(){
	super.initPrimary();
	
	pgl.canvas.addMouseListener(panel);
	pgl.canvas.addMouseMotionListener(panel);
	pgl.canvas.addMouseWheelListener(panel);
	pgl.canvas.addKeyListener(panel);
	pgl.canvas.addFocusListener(panel);
	pgl.canvas.addComponentListener(panel);
    }
    
    
    /* // debug
    public void setGLProperties(){
	
	GL2 gl = pgl.gl.getGL2(); // processing 2.0
	
	gl.glEnable(GL2.GL_MULTISAMPLE); //
	gl.glEnable(GL2.GL_POINT_SMOOTH); //
	gl.glEnable(GL2.GL_LINE_SMOOTH); //
	gl.glEnable(GL2.GL_POLYGON_SMOOTH); //
	
	gl.glEnable(GL2.GL_ALPHA_TEST); //
	//gl.glEnable(GL2.GL_BLEND); //
	//gl.glDisable(GL2.GL_BLEND); //
	//gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA); //
	//gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE); //
	
	gl.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST); //
	gl.glHint(GL2.GL_POINT_SMOOTH_HINT, GL2.GL_NICEST); //
	gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST); //
	
	//gl.glEnable(GL2.GL_NORMALIZE); //
	//gl.glEnable(GL2.GL_AUTO_NORMAL); //
	//gl.glShadeModel(GL2.GL_SMOOTH); //
	
	//gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, 1); //
	
	//gl.glEnable(GL2.GL_DEPTH_TEST); // already enabled in super
	//gl.glDisable(GL2.GL_DEPTH_TEST); // ? for transparency
	
	//gl.glEnable(GL2.GL_LIGHTING); // test!
	//gl.glEnable(GL2.GL_LIGHT1); // test!
	
    }
    */
    
    public void pre(){ drawIG(); }
    public void draw(){ drawIG(); }
    

    /**
       Drawing all the iGeo objects through IPanel.
       Overlay is also used to draw 2D graphics on top of OpenGL 3D graphics.
    */
    public synchronized void drawIG(){

	((IGraphicsGL2)igg).setGL(pgl.gl);
	
	super.beginPGL();
	
	panel.draw(igg);
	
	super.endPGL();

    }
    
    public void setOverlay(){ // doing nothing for now
	/*
	if(pgl.canvas instanceof GLDrawable){
	    //overlay = new Overlay(drawable); // processing 1.5
	    overlay = new Overlay((GLDrawable)pgl.canvas); // processing 2.0
	    igg.setGraphics(overlay.createGraphics());
	    igg.getGraphics().setBackground(overlayBG);
	}
	*/
    }
    
    public void setSize(int w, int h){
	super.setSize(w,h);
	//setOverlay(); // update overlay
	//panel.setSize(w,h); //
    }
        
    public void post(){
	if(overwritePAppletFinish) parent.finished=finished;
	if(overwritePAppletLoop) if(looping) parent.loop(); else parent.noLoop();
    }
    
    public void loop(){ if(!looping) looping=true; }
    public void noLoop(){ if(looping) looping=false; }
    
    public void start(){ if(finished) finished=false; }
    public void stop(){ if(!finished) finished=true; }
    
}

