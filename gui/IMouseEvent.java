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

/**
   Abstracted mouse event (x,y and button type).
      
   @author Satoru Sugihara
*/
public class IMouseEvent{

    final static public int Button1 = 1; //MouseEvent.BUTTON1; // if it doesn't have awt?
    final static public int Button2 = 2; //MouseEvent.BUTTON2; 
    final static public int Button3 = 3; //MouseEvent.BUTTON3;
    
    public int button;
    public boolean shiftDown;
    public boolean controlDown;
    public boolean altDown;

    public float mouseX, mouseY;
    
    //int mask;
    
    public IMouseEvent(){
	button = Button1; /*MouseEvent.BUTTON1;*/ shiftDown=false; controlDown=false; altDown=false; 
    }
    public IMouseEvent(int b){
	button = b; shiftDown=false; controlDown=false; altDown=false; 
    }
    public IMouseEvent(int b, boolean shiftDown, boolean controlDown, boolean altDown){
	button=b;
	this.shiftDown = shiftDown;
	this.controlDown = controlDown;
	this.altDown = altDown;
    }
    
    public IMouseEvent(float x, float y){
	this();
	mouseX = x;
	mouseY = y;
    }
    public IMouseEvent(int b, float x, float y){
	this(b);
	mouseX = x;
	mouseY = y;
    }
    public IMouseEvent(int b, boolean shiftDown, boolean controlDown, boolean altDown, float x, float y){
	this(b,shiftDown,controlDown,altDown);
	mouseX = x;
	mouseY = y;
    }
    
    public IMouseEvent(MouseEvent e){
	button = e.getButton();
	shiftDown = e.isShiftDown();
	controlDown = e.isControlDown();
	altDown = e.isAltDown();
	
	mouseX = e.getX();
	mouseY = e.getY();
    }
    
    public int getX(){ return (int)mouseX; }
    public int getY(){ return (int)mouseY; }
    
    /*
    // SHIFT_DOWN or SHIFT_DOWN_MASK?
    // -> SHIFT_DOWN.
    // let's not use this
    boolean isShiftDown(){ return (mask & InputEvent.SHIFT_MASK)!=0; }
    boolean isControlDown(){ return (mask & InputEvent.CTRL_MASK)!=0; }
    boolean isAltDown(){ return (mask & InputEvent.ALT_MASK)!=0; }
    */
    public boolean isShiftDown(){ return shiftDown; }
    public boolean isControlDown(){ return controlDown; }
    public boolean isAltDown(){ return altDown; }
    
    /** check button matching */
    public boolean match(MouseEvent e){
	if(e.getButton()!=button) return false;
	if(shiftDown!=e.isShiftDown()) return false;
	if(controlDown!=e.isControlDown()) return false;
	if(e.getButton()!=MouseEvent.BUTTON2 && // ignore BUTTON2 for ALT
	   altDown!=e.isAltDown()) return false;
	return true;
    }
    
    /** check button matching */
    public boolean match(IMouseEvent e){
	if(e.button!=button) return false;
	if(shiftDown!=e.shiftDown) return false;
	if(controlDown!=e.controlDown) return false;
	if(e.button!=Button2 && // ignore BUTTON2 for ALT
	   altDown!=e.isAltDown()) return false;
	return true;
    }
    
}
