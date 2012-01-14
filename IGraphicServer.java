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

package igeo;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;

import igeo.gui.*;

/**
   A server to contain graphic objects to manage drawing process.
   
   @author Satoru Sugihara
   @version 0.7.0.0
*/
public class IGraphicServer{
    
    //boolean shareObjects = false;
    
    /*
    ArrayList<IGraphicGLWireI> graphicsGLWire;
    ArrayList<IGraphicGLFillI> graphicsGLFill;
    ArrayList<IGraphicGLTransparentI> graphicsGLTrans;
    
    HashMap<IView, ArrayList<IGraphicJavaWireI>> graphicsJavaWire;
    HashMap<IView, ArrayList<IGraphicJavaFillI>> graphicsJavaFill;
    HashMap<IView, ArrayList<IGraphicJavaTransparentI>> graphicsJavaTrans;
    */
    
    
    //public ArrayList<IGraphicObject> graphicsGL;
    //public HashMap<IView, ArrayList<IGraphicObject>> graphicsJava;
    
    public ArrayList<IGraphicI> graphicsGL;
    public HashMap<IView, ArrayList<IGraphicI>> graphicsJava;
    
    
    //ArrayList<IGraphicObject> graphicsGLWire;
    //ArrayList<IGraphicObject> graphicsGLFill;
    //ArrayList<IGraphicObject> graphicsGLTrans;
    //HashMap<IView, ArrayList<IGraphicObject>> graphicsJavaWire;
    //HashMap<IView, ArrayList<IGraphicObject>> graphicsJavaFill;
    //HashMap<IView, ArrayList<IGraphicObject>> graphicsJavaTrans;
    
    public ArrayList<IView> views;
    
    public IServer server;
    
    public IPanel panel;
    
    public IGraphicMode[] modes=null;
    
    public boolean useGL;
    
    
    public IGraphicServer(IServer s, IPanel p){
	server=s;
	panel = p;
	views=new ArrayList<IView>();
	for(int i=0; i<panel.paneNum(); i++) views.add(panel.getPane(i).getView());
    }
    
    public void addView(IView v){ views.add(v); }

    public IView view(int i){ return views.get(i); }
    
    public int viewNum(){ return views.size(); }
    
    
    public void setMode(IGraphicMode m){
	if(views!=null) for(IView v:views) v.setMode(new IGraphicMode(m));
    }
    
    public void enableGL(){ useGL=true; }
    public void disableGL(){ useGL=false; }
    public boolean isGL(){ return useGL; }
    
    
    public void bg(Color c1, Color c2, Color c3, Color c4){
	if(views!=null) for(IView v:views) v.setBGColor(c1,c2,c3,c4);
    }
    public void background(Color c1, Color c2, Color c3, Color c4){ bg(c1,c2,c3,c4); }
    
    
    public void add(IObject e){
	boolean isGL=false;
	for(IView v: views) if(v.mode().isGL()) isGL=true;
	
	if(isGL){
	    //IGraphicObject g = e.getGraphic(views.get(0).mode());
	    IGraphicI g = e.getGraphic(views.get(0).mode());
	    if(g!=null) add(g, views.get(0));
	    else{ IOut.err("graphic of the object is null : "+e); }
	    
	    /*
	    ArrayList<IGraphicMode.DisplayType> dtype = new ArrayList<IGraphicMode.DisplayType>();
	    for(IView v: views){
		if(!dtype.contains(v.mode().getDisplayType())){
		    dtype.add(v.mode().getDisplayType());
		    IGraphicObject g = e.getGraphic(v.mode());
		    if(g!=null) add(g, v);
		}
	    }
	    */
	}
	else{
	    for(IView v: views){
		//IGraphicObject g = e.getGraphic(v.mode());
		IGraphicI g = e.getGraphic(v.mode());
		if(g!=null) add(g, v);
		else{ IOut.err("graphic of the object is null : "+e); }
	    }
	}
    }
    
    //public void add(IGraphicObject e, IView view){
    public void add(IGraphicI e, IView view){
	if(view.mode().isGL()){
	    //if(graphicsGL==null) graphicsGL = new ArrayList<IGraphicObject>();
	    if(graphicsGL==null) graphicsGL = new ArrayList<IGraphicI>();
	    graphicsGL.add(e);
	}
	else if(view.mode().isJava()){
	    if(graphicsJava==null)
		//graphicsJava = new HashMap<IView, ArrayList<IGraphicObject>>();
		graphicsJava = new HashMap<IView, ArrayList<IGraphicI>>();
	    //ArrayList<IGraphicObject> objects = graphicsJava.get(view);
	    ArrayList<IGraphicI> objects = graphicsJava.get(view);
	    if(objects==null){
		//objects = new ArrayList<IGraphicObject>();
		objects = new ArrayList<IGraphicI>();
		graphicsJava.put(view, objects);
	    }
	    objects.add(e);
	}
	
	/*
	if(view.mode().isGL()){
	    if(view.mode().isWireframe()){
		if(graphicsGLWire==null) graphicsGLWire = new ArrayList<IGraphicObject>();
		graphicsGLWire.add(e);
	    }
	    else if(view.mode().isFill()){
		if(graphicsGLFill==null) graphicsGLFill = new ArrayList<IGraphicObject>();
		graphicsGLFill.add(e);
	    }
	    else if(view.mode().isTransparent()){
		if(graphicsGLTrans==null) graphicsGLTrans = new ArrayList<IGraphicObject>();
		graphicsGLTrans.add(e);
	    }
	}
	else if(view.mode().isJava()){
	    if(view.mode().isWireframe()){
		if(graphicsJavaWire==null)
		    graphicsJavaWire = new HashMap<IView, ArrayList<IGraphicObject>>();
		ArrayList<IGraphicObject> objects = graphicsJavaWire.get(view);
		if(objects==null){
		    objects = new ArrayList<IGraphicObject>();
		    graphicsJavaWire.put(view, objects);
		}
		objects.add(e);
	    }
	    else if(view.mode().isFill()){
		if(graphicsJavaFill==null)
		    graphicsJavaFill = new HashMap<IView, ArrayList<IGraphicObject>>();
		ArrayList<IGraphicObject> objects = graphicsJavaFill.get(view);
		if(objects==null){
		    objects = new ArrayList<IGraphicObject>();
		    graphicsJavaFill.put(view, objects);
		}
		objects.add(e);
	    }
	    else if(view.mode().isTransparent()){
		if(graphicsJavaTrans==null)
		    graphicsJavaTrans = new HashMap<IView, ArrayList<IGraphicObject>>();
		ArrayList<IGraphicObject> objects = graphicsJavaTrans.get(view);
		if(objects==null){
		    objects = new ArrayList<IGraphicObject>();
		    graphicsJavaTrans.put(view, objects);
		}
		objects.add(e);
	    }
	}
	*/
    }
    
    
    //public ArrayList<IGraphicObject> getObjects(IView view){
    public ArrayList<IGraphicI> getObjects(IView view){
	if(view.mode().isGL()) return graphicsGL;
	if(view.mode().isJava()) return graphicsJava.get(view);
	return null;
	/*
	if(//shareObjects ||
	   view.mode().isGL()){
	    if(view.mode().isWireframe()) return graphicsGLWire;
	    if(view.mode().isFill()) return graphicsGLFill;
	    if(view.mode().isTransparent()) return graphicsGLTrans;
	}
	else if(view.mode().isJava()){
	    if(view.mode().isWireframe()) return graphicsJavaWire.get(view);
	    if(view.mode().isFill()) return graphicsJavaFill.get(view);
	    if(view.mode().isTransparent()) return graphicsJavaTrans.get(view);
	}
	return null;
	*/
    }
    
    
    //public void remove(IGraphicObject g){
    public void remove(IGraphicI g){
	// search and remove
	if(modes==null) modes = IGraphicMode.getAllModes();
	for(IGraphicMode m : modes){
	    if(g.isDrawable(m)){
		if(m.isGL()){ graphicsGL.remove(g); }
		else if(m.isJava()){
		    for(IView v: views)
			if(graphicsJava.get(v)!=null) graphicsJava.get(v).remove(g);
		}
		
		/*
		if(m.isGL()){
		    if(m.isWireframe()) graphicsGLWire.remove(g);
		    else if(m.isFill()) graphicsGLFill.remove(g);
		    else if(m.isTransparent()) graphicsGLTrans.remove(g);
		}
		else if(m.isJava()){
		    if(m.isWireframe()){
			for(IView v: views)
			    if(graphicsJavaWire.get(v)!=null)
				graphicsJavaWire.get(v).remove(g);
		    }
		    else if(m.isFill()){
			for(IView v: views)
			    if(graphicsJavaFill.get(v)!=null)
				graphicsJavaFill.get(v).remove(g);
		    }
		    else if(m.isTransparent()){
			for(IView v: views)
			    if(graphicsJavaTrans.get(v)!=null)
				graphicsJavaTrans.get(v).remove(g);
		    }
		}
		*/
	    }
	}
    }
    
    
    /** remove all the graphic objects
     */
    public void clearObjects(){
	if(graphicsGL!=null){ graphicsGL.clear(); graphicsGL = null; }
	if(graphicsJava!=null){
	    for(IView v:views){
		if(graphicsJava.get(v)!=null) graphicsJava.get(v).clear();
	    }
	    graphicsJava.clear();
	    graphicsJava=null;
	}
	
	/*
	if(graphicsGLWire!=null) graphicsGLWire.clear();
	if(graphicsGLFill!=null) graphicsGLFill.clear();
	if(graphicsGLTrans!=null) graphicsGLTrans.clear();
	for(IView v:views){
	    if(graphicsJavaWire!=null) graphicsJavaWire.get(v).clear();
	    if(graphicsJavaFill!=null) graphicsJavaFill.get(v).clear();
	    if(graphicsJavaTrans!=null) graphicsJavaTrans.get(v).clear();
	}
	*/
    }
    
    
    /** remove all views.
	don't clearViews before clearObjects
     */
    public void clearViews(){ views.clear(); }
    
    
}
