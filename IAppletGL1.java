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
import java.awt.image.*;
import java.applet.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.geom.*;

import javax.media.opengl.*;


import igeo.gui.*;

public class IAppletGL1 extends Applet implements Runnable /*, IPanelAdapter*/{
    
    public static int framerate=25;
    
    public boolean running;
    
    public Thread thread;
    public Image offscreen=null;
    public Graphics offg=null;
    
    public Frame parent=null;
    
    public boolean runningAsApplication=false;
    
    public IPanelI panel;
    public IG ig;
    public IGraphicsGL igg;
    
    public IAppletGL1(){}
    
    
    public void init(){
	
	//IG.p("width="+getWidth()+", height="+getHeight());
	
	panel = new IGridPanel(0,0,getWidth(),getHeight(),2,2);
	panel.setVisible(true);
	panel.setParent(this);
	//panel.setAdapter(this);
	
	ig = IG.init(panel);
	ig.server().graphicServer().enableGL();
	
	addMouseListener(panel);
	addMouseMotionListener(panel);
	addMouseWheelListener(panel);
	addKeyListener(panel);
	addFocusListener(panel);
	addComponentListener(panel);
	//addWindowListener(panel); // ?
	
	igg = new IGraphicsGL1();
	
	enableEvents(AWTEvent.MOUSE_EVENT_MASK |
		     AWTEvent.MOUSE_MOTION_EVENT_MASK|
		     AWTEvent.MOUSE_WHEEL_EVENT_MASK
		     /*|AWTEvent.KEY_EVENT_MASK*/
		     );
	
	initScreen(); // is it ok to put here?
	
	addComponentListener(new ComponentListener(){
		public void componentHidden(ComponentEvent e){}
		public void componentMoved(ComponentEvent e){}
		public void componentShown(ComponentEvent e){}
		public void componentResized(ComponentEvent e){
		    setSize(getSize());
		}
	    });
	
	setLayout(null);
	/*
	//GL
	GLProfile.initSingleton();
	final GLCanvas glcanvas = new GLCanvas();
	glcanvas.addGLEventListener(new GLEventListener(){
		//@Override public void reshape( GLAutoDrawable glautodrawable, int x, int y, int w, int h){}
		@Override public void init( GLAutoDrawable glautodrawable){}
		@Override public void dispose( GLAutoDrawable glautodrawable){}
		@Override public void display( GLAutoDrawable glautodrawable){
		    glautodrawable.getGL().getGL2();
		    glautodrawable.getWidth();
		    glautodrawable.glHeight();
		}
		@Override public void displayChanged(GLAutoDrawable glautodrawable, boolean f1, boolean f2){}
		
	    });
	glcanvas.setSize(getSize());
	add(glcanvas);
	//glanimationcontrol = new FPSAnimator(glacanvas, 30);
	*/
	//final GLCanvas glcanvas = new GLCanvas();
	//IG.p(GL.GL_VERSION);
	//IG.p("Jogl package = "+Package.getPackage("javax.media.opengl"));
	//IG.p("JOGL version: "+ Package.getPackage("javax.media.opengl").getImplementationVersion());
    }
    
    public void initScreen(){
	offscreen = createImage(getWidth(),getHeight());
	if(offscreen!=null){ offg = offscreen.getGraphics(); }
    }
    
    
    public void initObjects(){
	/*
	IGCore.init();
	
	// PHA
	IGView.view = new IGView(100,100,200,0, 0, Math.PI/8,10, 1000,w,h, IGView.defaultPerspectiveScale);
	IGView.view.setTarget(-50,-50,200);
	    
	IGView.view.update();
	*/
    }
    
    
    
    public void setSize(Dimension d){
	
	super.setSize(d);
	
	if(offg!=null){
	    offscreen = createImage(getWidth(),getHeight());
	    offg = offscreen.getGraphics();
	}
	/*
	if(IGView.view!=null){
	    IGView.view.setScreenSize(w,h);
	    IGView.view.update();
	}
	IGCore.setDisplaySize(d);
	*/
    }
    
    
    public void paint(Graphics g){
	if(offg==null){
	    initScreen();
	    //initObjects();
	    return;
	}
	
	//offg.setColor(getBackground());
	offg.setColor(new Color(128,0,0));
	offg.fillRect(0,0,getWidth(),getHeight());
	
	//IGCore.paint(offg);
	
	g.drawImage(offscreen,0,0,getWidth(),getHeight(),this);
    }
    
    
    public void update(Graphics g){ paint(g); }
    
    public void start(){
	initObjects();
	running=true;
	if(thread==null){ thread = new Thread(this); }
	thread.start();
    }
    
    public void stop(){
	if(thread!=null){ running=false; thread.stop(); thread=null; }
    }
    
    
    public void run(){
	try{
	    while(running){
		
		try{ Thread.sleep(framerate); }
		catch(InterruptedException e){
		    e.printStackTrace();
		}
		repaint();
		updateObjects();
		
	    }
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
    
    public void updateObjects(){
	//IGCore.update();
    }
    
    public void processMouseEvent(MouseEvent e){
    }
    
    public void processMouseMotionEvent(MouseEvent e){
    }
    
    public void processMouseWheelEvent(MouseWheelEvent e){
    }
    
}
