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
package igeo;

import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import com.sun.opengl.util.*;


//import com.jogamp.opengl.util.*; // processing 2.0
//import javax.media.opengl.awt.*; // processing 2.0

import igeo.gui.*;

public class IApplicationGL1 implements GLEventListener /*, IPanelAdapter*/{
    
    public static void main(String[] args){
	
	Frame frame = new Frame(); 
	frame.setSize(1024,768);
	
	GLCanvas canvas = new GLCanvas();
	IApplicationGL1 application = new IApplicationGL1();
	application.setFrame(frame);
	canvas.addGLEventListener(application);
	frame.add(canvas);
	
	final Animator animator = new Animator(canvas);
	frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		    new Thread(new Runnable() {
			    public void run() {
				animator.stop();
				System.exit(0);
			    }
			}).start();
		}
	    });
	frame.setVisible(true);
	animator.start();
    }
    
    
    public IPanelI panel;
    public IG ig;
    public IGraphicsGL igg;
    public Frame frame;
    
    public void init(GLAutoDrawable drawable){
	GL gl = drawable.getGL();
	setGLProperties(gl);
	
	panel = new IGridPanel(0,0,drawable.getWidth(),drawable.getHeight(),2,2);
	panel.setVisible(true);
	panel.setParent(frame);
	//panel.setAdapter(this);
	
	ig = IG.init(panel);
	ig.server().graphicServer().enableGL();
	
	igg = new IGraphicsGL1();
	
	((Component)drawable).addMouseListener((MouseListener)panel);
	((Component)drawable).addMouseMotionListener((MouseMotionListener)panel);
	((Component)drawable).addMouseWheelListener((MouseWheelListener)panel);
	((Component)drawable).addKeyListener((KeyListener)panel);
	((Component)drawable).addFocusListener((FocusListener)panel);
	((Component)drawable).addComponentListener((ComponentListener)panel);
	frame.addWindowListener(panel);
	
	initObjects(); // init geometry objects
    }
    
    public void setFrame(Frame f){ frame = f; }
    
    // copied from PIGraphicsGL.java
    public void setGLProperties(GL gl){
        gl.glEnable(GL.GL_MULTISAMPLE); 
        //gl.glEnable(GL.GL_POINT_SMOOTH); 
        gl.glEnable(GL.GL_LINE_SMOOTH); 
        //gl.glEnable(GL.GL_POLYGON_SMOOTH); 
	
        //gl.glEnable(GL.GL_ALPHA_TEST); 
	
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST); 
        //gl.glHint(GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST); 
        //gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST); 
    }
    
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
    
    public void display(GLAutoDrawable drawable) {
	GL gl = drawable.getGL();
	if ((drawable instanceof GLJPanel) &&
	    !((GLJPanel) drawable).isOpaque() &&
	    ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {
	    gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
	} else {
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	}
	igg.setGL(gl);
	panel.draw(igg);
    }

    
    public void dispose(GLAutoDrawable drawable){} // added 20130731
    
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
    
    public void initObjects(){
	// test
	for(int i=0; i<10; i++){
	    for(int j=0; j<10; j++){
		new ISurface(i*10,j*10,0, (i+1)*10,j*10,0, (i+1)*10,(j+1)*10, i+j, i*10, (j+1)*10, 0).clr(i*0.1,j*0.1,0);
	    }
	}
	for(int i=0; i<100; i++){
	    new IBoidTrajectory(IRand.pt(100)){ public void update(){ push(IRand.pt(-500,500)); }}.clr(IRand.gray());
	}
	IG.focus();
    }
    
    public void close(){} // doing nothing
    
}
