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

package igeo.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
   Abstracted mouse button type.
      
   @author Satoru Sugihara
*/
public class IMouseButton{
    
    public int button;
    public boolean shiftDown;
    public boolean controlDown;
    public boolean altDown;
    
    //int mask;
    
    public IMouseButton(){
	button = MouseEvent.BUTTON1; shiftDown=false; controlDown=false; altDown=false; 
    }
    public IMouseButton(int b){
	button = b; shiftDown=false; controlDown=false; altDown=false; 
    }
    public IMouseButton(int b, boolean shiftDown, boolean controlDown, boolean altDown){
	button=b;
	this.shiftDown = shiftDown;
	this.controlDown = controlDown;
	this.altDown = altDown;
    }
    
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
    
    public boolean match(MouseEvent e){
	
	//IOut.p("e.button="+e.getButton()); //
	//IOut.p("button="+button);//
	//IOut.p("e.shift="+e.isShiftDown());
	//IOut.p("e.control="+e.isControlDown());
	//IOut.p("e.alt="+e.isAltDown());
	//IOut.p("modifiers&MASK="+(e.getModifiers() & (InputEvent.SHIFT_MASK|InputEvent.CTRL_MASK|InputEvent.ALT_MASK)));
	//IOut.p("mask="+mask); //
	
	if(e.getButton()!=button) return false;
	if(shiftDown!=e.isShiftDown()) return false;
	if(controlDown!=e.isControlDown()) return false;
	if(e.getButton()!=MouseEvent.BUTTON2 && // ignore BUTTON2 for ALT
	   altDown!=e.isAltDown()) return false;
	return true;
    }
    
}
