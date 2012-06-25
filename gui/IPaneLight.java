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
   Light weight implementation of IPane.
   One pane is associated with one IView and INavigator and retained by IPanel.
   
   @see IView
   @see INavigator
   @see IPanel
   
   @author Satoru Sugihara
   @version 0.7.1.0;
*/
public class IPaneLight extends IComponent implements IPane{
    
    //IG ig;
    public IPanel parent;
    public IView view;
    
    public INavigator navigator;
    
    public float borderWidth=0f; //1f;
    public Color borderColor = Color.gray; //Color.gray;
    public Stroke borderStroke = new BasicStroke(borderWidth);
    
    public IPaneLight(int x, int y, int width, int height, IView view, IPanel p){
	super(x,y,width,height);
	this.view = view;
	parent = p;
	
	view.setPane(this);
	navigator = new INavigator(view, this);
    }
    
    public IPaneLight(int x, int y, int width, int height){ super(x,y,width,height); }
    
    public void setPanel(IPanel p){ parent=p; }
    public IPanel getPanel(){ return parent; }
    //public void setIG(IG ig){ this.ig=ig; }
    
    public void setBorderWidth(float b){
	borderWidth=b;
	borderStroke=new BasicStroke(borderWidth);
    }
    public float getBorderWidth(){ return borderWidth; }
    public Stroke getBorderStroke(){ return borderStroke; }
    public void setBorderColor(Color c){ borderColor = c;}
    public Color getBorderColor(){ return borderColor; }
    
    public INavigator navigator(){ return navigator; }
    
    public void setBounds(int x, int y, int w, int h){
	//IOut.debug(10,"x="+x+", y="+y+", width="+w+", height="+h); //
	super.setBounds(x,y,w,h);
	//view.setScreen(x,y,width,height);
	if(view != null) view.setPane(this);
    }
    public void setLocation(int x, int y){
        super.setLocation(x,y);
	if(view != null) view.setPane(this);
    }
    public void setSize(int w, int h){
        super.setSize(w,h);
	if(view != null) view.setPane(this);
    }
    public void setView(IView view){
	this.view = view;
	this.view.setPane(this);
	if(navigator==null){ navigator = new INavigator(this.view, this); }
	else{ navigator.setView(this.view); }
    }
    
    public IView getView(){ return view; }
    
    public void draw(IGraphics g){
	
	//if(g.mode().isGL()){
	if(view!=null){
	    //g.setView(view);
	    
	    //if(objects==null) objectss = parent.ig.server().graphicServer().getObjects(view);
	    //if(objects==null) return;
	    
	    // retrieved every time
	    //ArrayList<IGraphicObject> objects = parent.ig.server().graphicServer().getObjects(view);
	    ArrayList<IGraphicI> objects = parent.ig.server().graphicServer().getObjects(view);
	    
	    g.draw(objects, view); // new algorithm 20120531
	    
	    /*
	    if(view.mode().isGL()){
		view.draw(g.getGL());
		
		if(objects!=null)
		    //for(int i=0; i<objects.size(); i++)
		    for(int i=objects.size()-1; i>=0; i--)
			if(objects.get(i).isVisible()) objects.get(i).draw(g);
		
		if(view.mode().isLight()){
		    g.getGL().glDisable(GL.GL_LIGHTING);
		    g.getGL().glDisable(GL.GL_LIGHT1);
		}
		
		if(g.getGraphics()!=null){ // overlay
		    Graphics2D g2 = g.getGraphics();
		    // border
		    if(borderWidth>0&&(parent.width!=width ||parent.height!=height)){
			g2.setColor(borderColor);
			g2.setStroke(borderStroke);
			//g2.drawRect(x,y,width-1,height-1);
			g2.drawRect(x,y,width,height);
		    }
		}
	    }
	    //else if(g.mode().isJ2D()){
	    else if(view.mode().isJ2D()){
		//g.setView(view);
		//if(objects!=null) for(IGraphicObject go:objects){ go.draw(g); }
		if(objects!=null) for(IGraphicI gi:objects){ gi.draw(g); }
	    }
	    */
	    
	}
	else{
	    IOut.err("view is null"); //
	}
    }
    
    
    /** Focus view on objects */
    public void focus(){
	//if(parent.getBounds()==null) parent.setBounds();
	
	// parent is checking if bounding box is needed to be updated or not.
	parent.setBounds();
	view.focus(parent.getBounds());
    }
    
    /** Focus view on objects */
    public void focus(ArrayList<IObject> e){
	IBounds bb = new IBounds();
	bb.setObjects(e);
	view.focus(bb);
    }
    
    
    public void mousePressed(MouseEvent e){
	navigator.mousePressed(e);
    }
    public void mouseReleased(MouseEvent e){
	navigator.mouseReleased(e);
    }
    public void mouseClicked(MouseEvent e){
	navigator.mouseClicked(e);
    }
    public void mouseEntered(MouseEvent e){
	navigator.mouseEntered(e);
    }
    public void mouseExited(MouseEvent e){
	navigator.mouseExited(e);
    }
    public void mouseMoved(MouseEvent e){
	navigator.mouseMoved(e);
    }
    public void mouseDragged(MouseEvent e){
	navigator.mouseDragged(e);
    }
    
    public void mouseWheelMoved(MouseWheelEvent e){
	navigator.mouseWheelMoved(e);
    }
    
    public void keyPressed(KeyEvent e){
	navigator.keyPressed(e);
    }
    public void keyReleased(KeyEvent e){
	navigator.keyReleased(e);
    }
    public void keyTyped(KeyEvent e){
	navigator.keyTyped(e);
    }
    
    public void focusLost(FocusEvent e){
    }
    public void focusGained(FocusEvent e){
    }
    
    
}
