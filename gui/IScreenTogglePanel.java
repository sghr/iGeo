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

import igeo.core.*;

/**
   A panel class to contain multiple IPane with toggle function
   to switch showing only one pane and showing all panes.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IScreenTogglePanel extends IPanel{
    
    public IPane fullScreenPane=null;
    public int fullPaneOrigX, fullPaneOrigY, fullPaneOrigWidth, fullPaneOrigHeight;
    
    public IScreenTogglePanel(int x, int y, int width, int height){
	super(x,y,width,height);
    }
    
    // full screen of a pane inside root panel, not full screen on the display;
    public void enableFullScreen(int paneIdx){
	IPane p = panes.get(paneIdx);
	fullPaneOrigX = p.x;
	fullPaneOrigY = p.y;
	fullPaneOrigWidth = p.width;
	fullPaneOrigHeight = p.height;
	fullScreenPane = p;
	
	for(int i=0; i<panes.size(); i++){ if(i!=paneIdx) panes.get(i).hide(); }
	
	//IOut.p("x="+x+", y="+y+", width="+width+", height"+height); //
	fullScreenPane.setBounds(this.x,this.y,this.width,this.height);
    }
    
    public void enableFullScreen(IPane p){
	int idx = panes.indexOf(p);
	if(idx>=0) enableFullScreen(idx);
    }
    
    public void disableFullScreen(){
	if(fullScreenPane!=null){
	    fullScreenPane.setBounds(fullPaneOrigX,fullPaneOrigY,
				     fullPaneOrigWidth,fullPaneOrigHeight);
	    
	    for(int i=0; i<panes.size(); i++){ if(panes.get(i)!=fullScreenPane) panes.get(i).show(); }
	    fullScreenPane = null;
	}
    }
    
    public boolean isFull(){ return fullScreenPane!=null; }
    
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
    }
    
    public void draw(IGraphics g){
	if(fullScreenPane!=null) synchronized(IG.lock){ fullScreenPane.draw(g); }
	else super.draw(g);
    }
    
    
    public void mouseClicked(MouseEvent e){
	super.mouseClicked(e);
	//IOut.p("click count="+e.getClickCount());//
	if(e.getClickCount()>=2 &&e.getClickCount()%2==0){ // double click
	    IPane p = getPaneAt(e);
	    if(fullScreenPane==null){ if(p!=null) enableFullScreen(p); }
	    else disableFullScreen();
	}
    }
    
    public void keyPressed(KeyEvent e){
	// toggle screen by space key
	if(e.getKeyCode()==KeyEvent.VK_SPACE){
	    if(fullScreenPane!=null){ disableFullScreen(); }
	    else if(currentMousePane!=null){ enableFullScreen(currentMousePane); }
	}
	super.keyPressed(e);
    }
    
}
