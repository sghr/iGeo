/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

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
import java.awt.event.*;

import javax.media.opengl.*;

import igeo.core.*;
import igeo.geo.IBoundingBox;

/**
   A root GUI object of iGeo managing all IPane instance.
   An instance IG is keyed by IPanel object when it's in Graphic mode.
   
   @author Satoru Sugihara
   @version 0.7.1.0;
*/
public class IPanel extends IComponent implements IServerI, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, FocusListener, ComponentListener{
    
    public ArrayList<IPane> panes;
    
    public IG ig;
    
    public IPane currentMousePane=null;
    
    //public IPane fullScreenPane=null;
    //public int fullPaneOrigX, fullPaneOrigY, fullPaneOrigWidth, fullPaneOrigHeight;
    
    public IBoundingBox boundingBox;
    public int serverStatusCount;
    
    public IPanel(int x, int y, int width, int height){
	super(x,y,width,height);
	panes = new ArrayList<IPane>();
	this.ig = ig;
    }
    
    public void setIG(IG ig){
	this.ig = ig;
	//for(int i=0; i<panes.size(); i++) panes.get(i).setIG(ig);
    }
    
    public IServer server(){ return ig.server(); }
    
    public void addPane(IPane p){
	panes.add(p);
	p.setParent(this);
	//if(ig!=null) p.setIG(ig);
    }
    
    public IPane getPane(int i){ return panes.get(i); }
    
    public int paneNum(){ return panes.size(); }
    
    public void removePane(int i){ panes.remove(i); }
    public void clearPane(){ panes.clear(); }
    
    public void show(){ for(int i=0; i<panes.size(); i++) panes.get(i).show(); }
    public void hide(){ for(int i=0; i<panes.size(); i++) panes.get(i).hide(); }
    
    /** focus on all pane
     */
    public void focus(){ for(int i=0; i<panes.size(); i++) panes.get(i).focus(); }
    
    
    public void setSize(int w, int h){
	int origW = width;
	int origH = height;
	
	for(int i=0; i<panes.size(); i++){
	    int nx = (int)(panes.get(i).getX()*w/origW);
	    int ny = (int)(panes.get(i).getY()*h/origH);
	    int nw = (int)(panes.get(i).getWidth()*w/origW);
	    int nh = (int)(panes.get(i).getHeight()*h/origH);
	    panes.get(i).setBounds(nx,ny,nw,nh);
	}
	width=w;
	height=h;
    }
    
    
    public void draw(IGraphics g){
	for(int i=0; i<panes.size(); i++){
	    synchronized(IG.lock){
		if(panes.get(i).isVisible()){ panes.get(i).draw(g); }
	    }
	}
    }
    
    public IPane getPaneAt(MouseEvent e){
	return getPaneAt(e.getX(),e.getY());
    }
    
    public IPane getPaneAt(int x, int y){
	//for(IPane p: panes) if(p.isVisible()&&p.contains(x,y)) return p;
	// to match with drawing order in case they overlap and some panes come to the front
	for(int i=panes.size()-1; i>=0; i--)
	    if(panes.get(i).isVisible()&&panes.get(i).contains(x,y)) return panes.get(i);
	return null;
    }
    
    
    public void mousePressed(MouseEvent e){
	IPane p = getPaneAt(e);
	if(p!=null){
	    currentMousePane = p;
	    p.mousePressed(e);
	}
	else{
	    IOut.err("no pane"); //
	}
    }
    public void mouseReleased(MouseEvent e){
	IPane p=null;
	if(currentMousePane!=null){
	    //p = currentMousePane;
	    currentMousePane.mouseReleased(e);
	    //currentMousePane = getPaneAt(e); // update
	}
	else{
	    p = getPaneAt(e);
	    if(p!=null){
		//currentMousePane = null;
		p.mouseReleased(e);
		currentMousePane = p;
	    }
	}
    }
    public void mouseClicked(MouseEvent e){
	//IOut.p();//
	
	IPane p = getPaneAt(e);
	if(p!=null){
	    p.mouseClicked(e);
	}
	
	//if(fullScreenPane==null){ if(p!=null) enableFullScreen(p); }
	//else disableFullScreen();
	
	currentMousePane = p; // update
    }
    public void mouseEntered(MouseEvent e){
	//IPane p = getPaneAt(e);
	//if(p!=null){ currentMousePane = p; }
	
	//IPane p = getPaneAt(e);
	//if(p!=null){ p.mouseEntered(e); }
    }
    public void mouseExited(MouseEvent e){
	//IPane p = getPaneAt(e);
	//if(p!=null){ p.mouseExited(e); }
    }
    public void mouseMoved(MouseEvent e){
	IPane p = getPaneAt(e);
	if(p!=null){
	    p.mouseMoved(e);
	}
    }
    public void mouseDragged(MouseEvent e){
	IPane p=null;
	if(currentMousePane!=null){ p = currentMousePane; }
	else{ p = getPaneAt(e); }
	if(p!=null){
	    p.mouseDragged(e);
	}
    }
    
    
    public void mouseWheelMoved(MouseWheelEvent e){
	if(currentMousePane!=null){ currentMousePane.mouseWheelMoved(e); }
	/*
	IPane p = getPaneAt(e);
	if(p!=null){
	    currentMousePane=p;
	    currentMousePane.mouseWheelMoved(e);
	}
	*/
    }
    
    
    public void keyPressed(KeyEvent e){
	
	int key = e.getKeyCode();
	boolean shift = e.isShiftDown();
	boolean control = e.isControlDown();
	
	if(key==KeyEvent.VK_F && /*!shift &&*/!control){
	    currentMousePane.focus();
	}
	/*
	else if(key==KeyEvent.VK_F && shift &&!control){
	    setBoundingBox();
	    currentMousePane.focus();
	}
	*/
	else if(key==KeyEvent.VK_S&& !shift &&!control){
	    // fill & wireframe
	    currentMousePane.getView().mode().setDrawMode(true,true,false);
	}
	else if(key==KeyEvent.VK_S&& shift &&!control){
	    // toggle fill shading
	    //currentMousePane.getView().mode().toggleFill();
	    // fill 
	    currentMousePane.getView().mode().setDrawMode(false,true,false);
	}
	else if(key==KeyEvent.VK_W&& !shift &&!control){
	    // wireframe
	    currentMousePane.getView().mode().setDrawMode(true,false,false);
	}
	/*
	else if(key==KeyEvent.VK_W&& shift &&!control){
	    // toggle wireframe
	    currentMousePane.getView().mode().toggleWireframe();
	}
	*/
	else if(key==KeyEvent.VK_T&& !shift &&!control){
	    // transparent fill & wireframe
	    currentMousePane.getView().mode().setDrawMode(true,true,true);
	}
	else if(key==KeyEvent.VK_T&& shift &&!control){
	    // toggle transparency
	    //currentMousePane.getView().mode().toggleTransparent();
	    // transparent fill
	    currentMousePane.getView().mode().setDrawMode(false,true,true);
	}
	//else if(key==KeyEvent.VK_Q && control&& !shift){
	else if( (key==KeyEvent.VK_W || key==KeyEvent.VK_Q)
		 && control&& !shift){ // to match with Processing closing behavior
	    System.exit(0); // temporary.
	}
	else if(key==KeyEvent.VK_S && control&& !shift){
	    ig.save();
	}
	
	if(currentMousePane!=null){ currentMousePane.keyPressed(e); }
    }
    public void keyReleased(KeyEvent e){
	if(currentMousePane!=null){ currentMousePane.keyReleased(e); }
    }
    public void keyTyped(KeyEvent e){
	if(currentMousePane!=null){ currentMousePane.keyTyped(e); }
    }
    
    public void focusLost(FocusEvent e){
    }
    public void focusGained(FocusEvent e){
    }
    
    
    public void componentHidden(ComponentEvent e){
    }
    public void componentMoved(ComponentEvent e){
    }
    public void componentResized(ComponentEvent e){
	int w = e.getComponent().getBounds().width;
	int h = e.getComponent().getBounds().height;
	setSize(w,h);
    }
    public void componentShown(ComponentEvent e){
    }
    
    public IBoundingBox getBoundingBox(){ return boundingBox; } 
    
    public void setBoundingBox(){
	if(boundingBox==null) boundingBox = new IBoundingBox();
	if(ig.server().statusCount!=serverStatusCount){
	    boundingBox.setObjects(ig.server());
	    serverStatusCount = ig.server().statusCount();
	}
    }
    
}
