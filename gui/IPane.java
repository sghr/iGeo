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
    
    public float getX();
    public float getY();
    public int getWidth();
    public int getHeight();
    
    public boolean isVisible();
    public void setVisible(boolean v);
    //public void hide();
    //public void show();
    
    public boolean contains(int x, int y);
    
    public void setPanel(IPanelI p);
    public IPanelI getPanel();
    
    public void setBorderWidth(float b);
    public float getBorderWidth();
    //public Stroke getBorderStroke();
    public void setBorderColor(int r, int g, int b, int a);
    //public void setBorderColor(Color c);
    //public Color getBorderColor();
    public int getBorderColor();
    public INavigator navigator();
    public void setBounds(int x, int y, int w, int h);
    public void setView(IView view);
    public IView getView();
    public void draw(IGraphics g);
	
    /** Focus view on objects */
    public void focus();
    
    /** Focus view on objects */
    public void focus(ArrayList<IObject> e);

    
    public void mousePressed(IMouseEvent e);
    public void mouseReleased(IMouseEvent e);
    public void mouseClicked(IMouseEvent e);
    public void mouseEntered(IMouseEvent e);
    public void mouseExited(IMouseEvent e);
    public void mouseMoved(IMouseEvent e);
    public void mouseDragged(IMouseEvent e);
    public void mouseWheelMoved(IMouseWheelEvent e);
    public void keyPressed(IKeyEvent e);
    public void keyReleased(IKeyEvent e);
    public void keyTyped(IKeyEvent e);

    //public void focusLost(FocusEvent e);
    //public void focusGained(FocusEvent e);
    
    
}
