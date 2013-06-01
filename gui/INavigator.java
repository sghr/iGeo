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

package igeo.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import igeo.*;

/**
   Class to provide 3D navigation function by mouse and keyboard.
   An instance of INavigator is associated with an instance of IView and it's
   retained by each IPane.
   
   @see IView
   @see IPane
   
   @author Satoru Sugihara
*/
public class INavigator{
    
    public final static IMouseEvent defaultRotateButton=new IMouseEvent(MouseEvent.BUTTON1);
    public final static IMouseEvent defaultRotateButton2=new IMouseEvent(MouseEvent.BUTTON1,false,false,true);
    public final static IMouseEvent defaultPanButton=new IMouseEvent(MouseEvent.BUTTON2);
    public final static IMouseEvent defaultPanButton2=new IMouseEvent(MouseEvent.BUTTON1,
									true, false, false);
    public final static IMouseEvent defaultZoomButton=new IMouseEvent(MouseEvent.BUTTON3);
    public final static IMouseEvent defaultZoomButton2=new IMouseEvent(MouseEvent.BUTTON1,
									 false, true, false);
    
    public static double minimumAxonMouseZoomRatio = 0.000001;
    
    public double rotationYawRatio = IConfig.mouseRotationSpeed/180*Math.PI;
    public double rotationPitchRatio = IConfig.mouseRotationSpeed/180*Math.PI;
    //public double persPanRatio = IConfig.mousePerspectivePanSpeed;
    public double persPanRatio = 100.0/IConfig.mousePerspectivePanResolution; //100.0=default size
    public double axonPanRatio = IConfig.mouseAxonometricPanSpeed;
    public double axonZoomRatio= IConfig.mouseAxonometricZoomSpeed/100; // percent to ratio
    //public double persZoomRatio= IConfig.mousePerspectiveZoomSpeed;
    public double persZoomRatio= 100.0/IConfig.mousePerspectiveZoomResolution; //100.0=default size
    public double wheelZoomRatio = IConfig.mouseWheelZoomSpeed;
    
    public double keyRotationYawInc = IConfig.keyRotationSpeed/180*Math.PI;
    public double keyRotationPitchInc = IConfig.keyRotationSpeed/180*Math.PI;
    public double keyPersPanInc = IConfig.keyPerspectivePanSpeed;
    public double keyAxonPanInc = IConfig.keyAxonometricPanSpeed;
    public double keyZoomInc = IConfig.keyZoomSpeed;
    
    /** for orthogonal view not to rotate. lock can be turned off with ALT+drag */
    public boolean rotateLock = false; 
    
    
    public enum DragType{ Rotate, Pan, Zoom };
    
    public IPane pane;
    public IView view;
    
    public int mouseX, mouseY;
    public boolean mousePressed=false;
    
    public IVec viewPos, viewTarget, viewAngle;
    //public double viewYaw, viewPitch, viewRoll;
    public double viewAxonRatio;
    
    
    public DragType dragType = null;
    
    
    public ArrayList<IMouseEvent> rotateButtons;
    public ArrayList<IMouseEvent> panButtons;
    public ArrayList<IMouseEvent> zoomButtons;
    
    public ArrayList<IMouseEvent> rotateUnlockButtons;
    
    public INavigator(IView v, IPane p){
	this(v);
	pane = p;
    }
    public INavigator(IView v){
	view = v;
	rotateButtons=new ArrayList<IMouseEvent>();
	panButtons=new ArrayList<IMouseEvent>();
	zoomButtons=new ArrayList<IMouseEvent>();

	rotateUnlockButtons=new ArrayList<IMouseEvent>();
	
	rotateButtons.add(defaultRotateButton);
	rotateButtons.add(defaultRotateButton2);
	panButtons.add(defaultPanButton);
	panButtons.add(defaultPanButton2);
	zoomButtons.add(defaultZoomButton);
	zoomButtons.add(defaultZoomButton2);
	
	rotateUnlockButtons.add(defaultRotateButton2);
	
    }
    
    
    public void setPane(IPane p){ pane = p; }
    
    public void setView(IView v){ view = v; }
    
    
    public void setRotationRatio(double yawRatio, double pitchRatio){
	rotationYawRatio = yawRatio;
	rotationPitchRatio = pitchRatio;
    }
    public void setPerspectivePanRatio(double panRatio){ persPanRatio = panRatio; }
    public void setAxonometricPanRatio(double axonRatio){ axonPanRatio = axonRatio; }
    public void setPerspectiveZoomRatio(double zoomRatio){ persZoomRatio = zoomRatio; }
    public void setAxonometricZoomRatio(double zoomRatio){ axonZoomRatio = zoomRatio; }
    public void wheelZoomRatio(double zoomRatio){ wheelZoomRatio = zoomRatio; }

    public void setRotateLock(boolean lock){ rotateLock = lock; }
    
    public void setRatioByBounds(IBounds bounds){
	if(bounds==null || bounds.min==null || bounds.max==null) return;
	
	double avgSize = (bounds.width()+bounds.height()+bounds.depth())/3;
	
	if(avgSize==0){ // just point
	    return; // do nothing
	}
	
	if(avgSize < IConfig.tolerance){ avgSize = IConfig.tolerance; }
	
	persZoomRatio = avgSize / IConfig.mousePerspectiveZoomResolution;
	persPanRatio = avgSize / IConfig.mousePerspectivePanResolution;
    }
    
    public void updateRotationByMouse(int x, int y){
	if(mousePressed){
	    int xdiff = x-mouseX;
	    int ydiff = y-mouseY;
	    
	    //final double yawRatio=-0.005; //-0.01; //0.01;
	    //final double pitchRatio=0.005; //0.01; //-0.01;
	    //final double yawRatio = - IConfig.mouseRotationSpeed/180*Math.PI; // negated
	    //final double pitchRatio = IConfig.mouseRotationSpeed/180*Math.PI; // negated
	    
	    double yawdiff = -xdiff*rotationYawRatio; // negated
	    double pitchdiff = ydiff*rotationPitchRatio;
	    
	    updateRotation(yawdiff,pitchdiff);
	    /*
	    double yaw = viewAngle.x+yawdiff;
	    double pitch = viewAngle.y+pitchdiff;
	    
	    yaw = yaw-Math.floor(yaw/(2*Math.PI))*2*Math.PI;
	    pitch = pitch-Math.floor(pitch/(2*Math.PI))*2*Math.PI;
	    
	    view.setAngle(yaw, pitch); //
	    view.update();
	    */
	}
    }
    
    public void updateRotation(double yawdiff, double pitchdiff){
	double yaw = viewAngle.x+yawdiff;
	double pitch = viewAngle.y+pitchdiff;
	
	yaw = yaw-Math.floor(yaw/(2*Math.PI))*2*Math.PI;
	pitch = pitch-Math.floor(pitch/(2*Math.PI))*2*Math.PI;
	
	view.setAngle(yaw, pitch); //
	view.update();
    }
    
    public void updatePanByMouse(int x, int y){
	if(mousePressed){
	    int xdiff = x-mouseX;
	    int ydiff = y-mouseY;
	    
	    //double panRatio = 0.25; //-0.25; //-0.5; //-2;
	    double panRatio;
	    if(view.isAxonometric()){
		panRatio = view.getAxonometricRatio() * axonPanRatio;
	    }
	    else{
		panRatio = persPanRatio;
	    }
	    
	    updatePan(-xdiff*panRatio, ydiff*panRatio);
	    /*
	    IVec xdir = view.rightDirection();
	    IVec ydir = view.upDirection();
	    xdir.len(-xdiff*panRatio);
	    ydir.len(ydiff*panRatio);
	    xdir.add(ydir);
	    
	    view.setLocation(viewPos.dup().add(xdir));
	    view.setTarget(viewTarget.dup().add(xdir));
	    
	    view.update();
	    */
	}
    }
    
    public void updatePan(double xmove, double ymove){
	IVec xdir = view.rightDirection();
	IVec ydir = view.upDirection();
	xdir.len(xmove).add(ydir.len(ymove));
	
	view.setLocation(viewPos.dup().add(xdir));
	view.setTarget(viewTarget.dup().add(xdir));
	view.update();
    }
    
    public void updateZoomByMouse(int x, int y){
	if(mousePressed){
	    updateZoom(y-mouseY);
	}
    }
    
    public void updateZoom(double diff){
	if(view.isAxonometric()){
	    double axonRatio;
	    if(diff>0) axonRatio = (1.+diff*axonZoomRatio)*viewAxonRatio;
	    else axonRatio = 1./(1.-diff*axonZoomRatio)*viewAxonRatio;
	    
	    if(axonRatio<minimumAxonMouseZoomRatio){
		axonRatio = minimumAxonMouseZoomRatio;
	    }
	    view.setAxonometricRatio(axonRatio);
	    //view.update(); //
	}
	else{
	    double moveDist = -diff*persZoomRatio;
	    //view.moveForward(moveDist);
	    IVec dir = view.frontDirection().len(moveDist);
	    view.setLocation(viewPos.dup().add(dir));
	    //view.setTarget(viewTarget.dup().add(dir));
	    view.update();
	}
    }
    
    /*
    public void updateZoom(double zoomInc){
	if(view.isAxonometric()){
	    double axonRatio;
	    if(zoomInc>0) axonRatio=(1.+zoomInc*axonZoomRatio)*view.getAxonometricRatio();
	    else axonRatio=1./(1.-zoomInc*axonZoomRatio)*view.getAxonometricRatio();
	    
	    if(axonRatio<minimumAxoncMouseZoomRatio){
		axonRatio = minimumAxonMouseZoomRatio;
	    }
	    view.setAxonometricRatio(axonRatio); 
	}
	else{
	    double moveDist = -zoomInc*view.getPerspectiveRatio();
	    IVec dir = view.frontDirection();
	    dir.len(moveDist);
	    IVec pos = view.location();
	    view.setLocation(pos.add(dir));
	    IVec tgt = view.target();
	    //view.setTarget(tgt.add(dir));
	    view.update();
	}
    }
    */
    
    public DragType getDragType(IMouseEvent e){
	if(rotateLock){
	    for(IMouseEvent b:rotateUnlockButtons) if(b.match(e)){
		rotateLock=false;
		return DragType.Rotate;
	    }
	    // when locked, rotate becomes panning
	    for(IMouseEvent b:rotateButtons) if(b.match(e)) return DragType.Pan;
	}
	else{
	    for(IMouseEvent b:rotateButtons) if(b.match(e)) return DragType.Rotate;
	}
	for(IMouseEvent b:panButtons) if(b.match(e)) return DragType.Pan;
	for(IMouseEvent b:zoomButtons) if(b.match(e)) return DragType.Zoom;
	return null;
    }
    
    public void mousePressed(IMouseEvent e){
	mouseX = e.getX();
	mouseY = e.getY();
	mousePressed=true;
	
	//if(e.isShiftDown()) dragType = DragType.Pan;
	//else if(e.isControlDown()) dragType = DragType.Zoom;
	//else dragType = DragType.Rotate;
	
	dragType = getDragType(e);
	
	// all?
	viewPos = view.location();
	viewTarget = view.target();
	viewAngle = view.getAngles();
	viewAxonRatio = view.getAxonometricRatio();
    }
    
    public void mouseReleased(IMouseEvent e){
	if(dragType==DragType.Rotate){ updateRotationByMouse(e.getX(), e.getY()); }
	else if(dragType==DragType.Pan){ updatePanByMouse(e.getX(), e.getY()); }
	else if(dragType==DragType.Zoom){ updateZoomByMouse(e.getX(), e.getY()); }
	mousePressed=false;
    }
    public void mouseClicked(IMouseEvent e){
    }
    public void mouseEntered(IMouseEvent e){
    }
    public void mouseExited(IMouseEvent e){
    }
    public void mouseMoved(IMouseEvent e){
    }
    public void mouseDragged(IMouseEvent e){
	if(dragType==DragType.Rotate){ updateRotationByMouse(e.getX(), e.getY()); }
	else if(dragType==DragType.Pan){ updatePanByMouse(e.getX(), e.getY()); }
	else if(dragType==DragType.Zoom){ updateZoomByMouse(e.getX(), e.getY()); }
    }
    
    public void mouseWheelMoved(IMouseWheelEvent e){
	//final double zoomCoeff=40; //10; //5;
	
	viewPos = view.location();
	//viewTarget = view.target();
	//viewAngle = view.getAngles();
	viewAxonRatio = view.getAxonometricRatio();
	updateZoom(wheelZoomRatio*e.getWheelRotation());
    }
    
    public void keyPressed(IKeyEvent e){
	int key = e.getKeyCode();
	boolean shift=e.isShiftDown();
	boolean control=e.isControlDown();
	
	// arrow key control
	if(!shift&&!control){ // rotate
	    
	    if(key==KeyEvent.VK_UP){
		viewAngle = view.getAngles();
		updateRotation(0, -keyRotationPitchInc);
	    }
	    else if(key==KeyEvent.VK_DOWN){
		viewAngle = view.getAngles();
		updateRotation(0, keyRotationPitchInc);
	    }
	    else if(key==KeyEvent.VK_LEFT){
		viewAngle = view.getAngles();
		updateRotation(keyRotationYawInc, 0);
	    }
	    else if(key==KeyEvent.VK_RIGHT){
		viewAngle = view.getAngles();
		updateRotation(-keyRotationYawInc, 0);
	    }
	}
	else if(shift&&!control){ // pan
	    double panInc;
	    if(view.isAxonometric()) panInc = view.getAxonometricRatio()*keyAxonPanInc;
	    else panInc = keyPersPanInc;
	    
	    if(key==KeyEvent.VK_UP){
		viewPos = view.location();
		viewTarget = view.target();
		updatePan(0, -panInc);
	    }
	    else if(key==KeyEvent.VK_DOWN){
		viewPos = view.location();
		viewTarget = view.target();
		updatePan(0, panInc);
	    }
	    else if(key==KeyEvent.VK_LEFT){
		viewPos = view.location();
		viewTarget = view.target();
		updatePan(panInc,0);
	    }
	    else if(key==KeyEvent.VK_RIGHT){
		viewPos = view.location();
		viewTarget = view.target();
		updatePan(-panInc,0);
	    }
	    
	}
	else if(!shift&&control){ // zoom
	    
	    if(key==KeyEvent.VK_UP){
		viewPos = view.location();
		viewAxonRatio = view.getAxonometricRatio();
		updateZoom(-keyZoomInc);
	    }
	    else if(key==KeyEvent.VK_DOWN){
		viewPos = view.location();
		viewAxonRatio = view.getAxonometricRatio();
		updateZoom(keyZoomInc);
	    }
	    
	}
	
    }
    public void keyReleased(IKeyEvent e){
    }
    public void keyTyped(IKeyEvent e){
    }
    
    /*
    public void focusLost(FocusEvent e){
    }
    public void focusGained(FocusEvent e){
    }
    */
    
}
