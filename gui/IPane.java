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

//import javax.media.opengl.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import igeo.*;

/**
   A pane object to provide one rectangular area to draw objects.
   One pane is associated with one IView and INavigator and retained by IPanel.
   
   @see IView
   @see INavigator
   @see IPanel
   
   @author Satoru Sugihara
*/
public interface IPane{
    
    //public void setBounds(IComponent c);
    public void setLocation(int x, int y);
    public void setSize(int width, int height);
    
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();
    
    public boolean isVisible();
    public void setVisible(boolean v);
    //public void hide();
    //public void show();
    
    public boolean contains(int x, int y);
    
    public void setPanel(IPanel p);
    public IPanel getPanel();
    
    public void setBorderWidth(float b);
    public float getBorderWidth();
    public Stroke getBorderStroke();
    public void setBorderColor(Color c);
    public Color getBorderColor();
    public INavigator navigator();
    public void setBounds(int x, int y, int w, int h);
    public void setView(IView view);
    public IView getView();
    public void draw(IGraphics g);
	
    /** Focus view on objects */
    public void focus();
    
    /** Focus view on objects */
    public void focus(ArrayList<IObject> e);
    
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);
    public void mouseClicked(MouseEvent e);
    public void mouseEntered(MouseEvent e);
    public void mouseExited(MouseEvent e);
    public void mouseMoved(MouseEvent e);
    public void mouseDragged(MouseEvent e);
    public void mouseWheelMoved(MouseWheelEvent e);
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
    public void keyTyped(KeyEvent e);
    public void focusLost(FocusEvent e);
    public void focusGained(FocusEvent e);
    
}
