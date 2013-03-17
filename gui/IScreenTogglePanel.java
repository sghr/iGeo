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
import java.awt.event.*;

import igeo.*;

/**
   A panel class to contain multiple IPane with toggle function
   to switch showing only one pane and showing all panes.
   
   @author Satoru Sugihara
*/
public class IScreenTogglePanel extends IPanel{
    
    public IPane fullScreenPane=null;
    public int fullPaneOrigX, fullPaneOrigY, fullPaneOrigWidth, fullPaneOrigHeight;
    
    public IScreenTogglePanel(int x, int y, int width, int height){ super(x,y,width,height); }
    
    // full screen of a pane inside root panel, not full screen on the display;
    public void enableFullScreen(int paneIdx){
	IPane p = panes.get(paneIdx);
	fullPaneOrigX = (int)p.getX();
	fullPaneOrigY = (int)p.getY();
	fullPaneOrigWidth = p.getWidth();
	fullPaneOrigHeight = p.getHeight();
	fullScreenPane = p;
	for(int i=0; i<panes.size(); i++){
	    if(i!=paneIdx) panes.get(i).setVisible(false);
	}
	fullScreenPane.setBounds(this.x,this.y,this.width,this.height);
	
	currentMousePane = p; // added 20120905
    }
    
    public void enableFullScreen(IPane p){
	int idx = panes.indexOf(p);
	//IOut.err(idx);
	if(idx>=0) enableFullScreen(idx);
	//else{ IOut.err("no pane found"); } //
	
	sizeChanged=true; // added 20120923
    }
    
    public void disableFullScreen(){
	if(fullScreenPane!=null){
	    fullScreenPane.setBounds(fullPaneOrigX,fullPaneOrigY,
				     fullPaneOrigWidth,fullPaneOrigHeight);
	    
	    for(int i=0; i<panes.size(); i++){
		//if(panes.get(i)!=fullScreenPane) panes.get(i).show();
		if(panes.get(i)!=fullScreenPane) panes.get(i).setVisible(true);
	    }
	    fullScreenPane = null;
	    
	    sizeChanged=true; // added 20120923
	}
    }
    
    public boolean isFull(){ return fullScreenPane!=null; }
    
    public void setVisible(boolean v){
	if(v){ // show
	    if(fullScreenPane!=null){ fullScreenPane.setVisible(true); }
	    else{ for(int i=0; i<panes.size(); i++) panes.get(i).setVisible(true); }
	}
	else{ // hide
	    if(fullScreenPane!=null){ fullScreenPane.setVisible(false); }
	    else{ for(int i=0; i<panes.size(); i++) panes.get(i).setVisible(false); }
	}
    }
    
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

	if(fullScreenPane!=null){
	    fullPaneOrigX = (int)(fullPaneOrigX*w/origW);
	    fullPaneOrigY = (int)(fullPaneOrigY*h/origH);
	    fullPaneOrigWidth = (int)(fullPaneOrigWidth*w/origW);
	    fullPaneOrigHeight = (int)(fullPaneOrigHeight*h/origH);
	}
	width=w;
	height=h;
	
	sizeChanged=true;
    }
    
    public void draw(IGraphics g){
	if(fullScreenPane==null){ super.draw(g); }
	else{
	    predraw(g);
	    synchronized(IG.lock){ // shouldn't this be "ig"?
		//if(startDynamicServer) startDynamicServer();
		fullScreenPane.draw(g);
	    }
	    postdraw(g);
	}
    }
    
    
    public void mouseClicked(MouseEvent e){
	super.mouseClicked(e);
	
	IMouseEvent me = new IMouseEvent(e);
	//IOut.p("click count="+e.getClickCount());//
	if(e.getClickCount()>=2 &&e.getClickCount()%2==0){ // double click
	    IPane p = getPaneAt(me);
	    if(fullScreenPane==null){ if(p!=null) enableFullScreen(p); }
	    else disableFullScreen();
	}
    }

    
    public void keyPressed(KeyEvent e){
	// toggle screen by space key
	// shortcut for toggle of full screen is changed to shift+space (to use space for play & stop simulation) 2011/12/24
	if(e.getKeyCode()==KeyEvent.VK_SPACE && e.isShiftDown()){
	//if(e.getKeyCode()==KeyEvent.VK_SPACE){
	    if(fullScreenPane!=null){ disableFullScreen(); }
	    else if(currentMousePane!=null){ enableFullScreen(currentMousePane); }
	}
	super.keyPressed(e);
    }
    
    
}
