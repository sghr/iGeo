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

import igeo.*;

/**
   Interface of a root GUI object of iGeo managing all IPane instance.
   An instance IG is keyed by IPanel object when it's in Graphic mode.
   
   @author Satoru Sugihara
*/
public interface IPanelI extends IServerI /*, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, FocusListener, ComponentListener*/{
    
    
    public void setIG(IG ig);
    public IG getIG();
    public IServer server();
    public void addPane(IPane p);
    public IPane pane(int i);
    public int paneNum();
    public void removePane(int i);
    public void clearPane();
    public void setVisible(boolean v);
    /** focus on all pane */
    public void focus(); 
    
    public void setSize(int w, int h);
    public void startDynamicServer();
    public void draw(IGraphics g);
    public void predraw(IGraphics g);
    public void postdraw(IGraphics g);
    //public IPane getPaneAt(MouseEvent e);
    public IPane getPaneAt(int x, int y);
    /** returns current pane; if null, it returns first pane. */
    public IPane currentPane();
    
    /*
    public void mousePressed(IMouseEvent e);
    public void mouseReleased(IMouseEvent e);
    public void mouseClicked(IMouseEvent e);
    public void mouseEntered(IMouseEvent e);
    public void mouseExited(IMouseEvent e);
    public void mouseMoved(IMouseEvent e);
    public void mouseDragged(IMouseEvent e);
    */
    /*
    public void mouseWheelMoved(MouseWheelEvent e);
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
    public void keyTyped(KeyEvent e);
    
    
    public void focusLost(FocusEvent e);
    public void focusGained(FocusEvent e);
    
    public void componentHidden(ComponentEvent e);
    public void componentMoved(ComponentEvent e);
    public void componentResized(ComponentEvent e);
    public void componentShown(ComponentEvent e);
    */
    
    public IBounds getBounds();
    public void setBounds();
    public void saveDialog();

    public float getX();
    public float getY();
    public int getWidth();
    public int getHeight();

    public void skipAutoFocus();
}
