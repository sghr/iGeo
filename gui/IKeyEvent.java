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
   Abstracted key event in case no AWT provided
      
   @author Satoru Sugihara
*/
public class IKeyEvent{

    public char character;
    public int keyCode;
    public int keyLocation;
    public boolean actionKey;
    public boolean altDown, controlDown, metaDown, shiftDown;
    
    public IKeyEvent(){}
    public IKeyEvent(int keycode){ keyCode = keycode; }
    public IKeyEvent(KeyEvent e){
	character = e.getKeyChar();
	keyCode = e.getKeyCode();
	keyLocation = e.getKeyLocation();
	actionKey = e.isActionKey();
	altDown = e.isAltDown();
	controlDown = e.isControlDown();
	metaDown = e.isMetaDown();
	shiftDown = e.isShiftDown();
    }
    
    public char getKeyChar(){ return character; }
    public int getKeyCode(){ return keyCode; }
    public int getKeyLocation(){ return keyLocation; }
    public boolean isAltDown(){ return altDown; }
    public boolean isControlDown(){ return controlDown; }
    public boolean isMetaDown(){ return metaDown; }
    public boolean isShiftDown(){ return shiftDown; }
    
    
}
