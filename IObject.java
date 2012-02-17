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
import java.awt.Color;
import igeo.gui.*;

/**
   A base class of iGeo object to be contained by IServer.
   Only instances of sub-classes of IObject can be carried by IServer.
   IObject contains three types of subobjects.
   One IParameterObject, multiple IGraphicObject and multiple IDynamics.
   If you assign IServerI in the constructor, the instance is contained by the server.
   If not the instance is contained by a server found in static IG methods.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
//public interface IElement{
//public class IElement{
public class IObject{
    
    //public int id; // or use String UUID or use object instance's hash code?
    //public String name;
    public IServer server;
    
    /*protected*/ public IParameterObject parameter;
    //IGraphicObject graphic;
    //IDynamics dynamic;
    public ArrayList<IGraphicObject> graphics;
    public ArrayList<IDynamics> dynamics;
    
    //public ILayer layer;
    public IAttribute attribute;
    
    /** IObject is stored in default IServer (through current static IG instance) */
    public IObject(){ initObject(null); }
    
    /** IObject is stored in the IServer via holder */
    public IObject(IServerI holder){ initObject(holder); }
    
    public IObject(IObject e){
	initObject(e.server);
	// geometry might not be ready in subclass
	//if(graphics!=null){ initGraphic(e.server); setColor(e.getColor()); }
	if(e.attribute!=null){ attribute = e.attribute.dup(); }
    }
    
    public IObject(IServerI holder, IObject e){
	initObject(holder);
	// geometry might not be ready in subclass
	//if(graphics!=null){ initGraphic(holder); setColor(e.getColor()); }
	if(e.attribute!=null){ attribute = e.attribute.dup(); }
    }
    /** duplicate object */
    public IObject dup(){ return new IObject(this); }
    
    /** alias of dup() */
    public IObject cp(){ return dup(); }
    
    public void initObject(IServerI holder){
	/*IServer*/ server = null;
	if(holder==null){
	    IG ig = IG.cur();
	    if(ig==null){
		IOut.err("no IG is found. IObject is not stored in a server.");
                return;
            }
            server = ig.server();
        }
        else server = holder.server();
	
	//synchronized(IG.lock){ // already synchronized in add
	server.add(this);
	//}
    }
    
    
    public void initGraphic(IServerI holder){
	/*IServer*/ server = null;
	if(holder==null){
	    IG ig = IG.current();
	    if(ig==null){
		IOut.err("no IG is found. IObject is not stored in a server.");
                return;
            }
            server = ig.server();
        }
        else server = holder.server();
        if(server.isGraphicMode())
	    //synchronized(IG.lock){
	    synchronized(server.server().ig){
		server.graphicServer().add(this);
	    }
    }
    
    public void del(){
	if(server!=null){ server.remove(this); server=null; }
	else if(IG.current()!=null) IG.current().server().remove(this); // necessary?
    }
    
    
    /** checking parameters validity. to be overriden. */
    public boolean isValid(){ return true; }
    
    
    public void setParameter(IParameterObject param){
	if(parameter!=null) IOut.err("parameter is already set. overwrote."); 
	parameter = param;
    }
    
    /*
    public void addGraphic(IGraphicObject graf){
	if(graphics==null) graphics = new ArrayList<IGraphicObject>();
	if(graphics.size()>0){
	    if(!IGConfig.keepMultipleGraphicsInElement){
		for(IGraphicObject g:graphics) server.removeGraphicElement(g);
		graphics.clear();
	    }
	    else{
		graf.setColor(graphics.get(0).getColor());
	    }
	}
	graphics.add(graf);
    }
    
    public void setDynamic(IGDynamicSubelement dyna){
	if(dynamics==null) dynamics = new ArrayList<IGDynamicSubelement>();
	dynamics.add(dyna);
    }
    */
    
    public void addDynamics(IDynamics dyna){
	if(dynamics==null) dynamics = new ArrayList<IDynamics>();
	if(!dynamics.contains(dyna)) dynamics.add(dyna);
	if(server!=null&&server.dynamicServer()!=null) server.dynamicServer().add(dyna);
    }
    
    public IParameterObject getParameter(){ return parameter; }
    public IGraphicObject getGraphic(int i){
	if(graphics==null) return null;
	return graphics.get(i);
    }
    public IDynamics getDynamics(int i){
	if(dynamics==null) return null;
	return dynamics.get(i);
    }
    
    public int graphicsNum(){ if(graphics==null) return 0; return graphics.size(); }
    public int dynamicsNum(){ if(dynamics==null) return 0; return dynamics.size(); }
    
    public void clearGraphics(){ if(graphics!=null) graphics.clear(); }
    public void clearDynamics(){ if(dynamics!=null) dynamics.clear(); }
    
    
    public void deletDynamics(int index){
	if(index<0||index>=dynamics.size()) return;
	if(server!=null && server.dynamicServer!=null)
	    server.dynamicServer.remove(dynamics.get(index));
	dynamics.remove(index);
    }
    public void deleteDynamics(IDynamics dyn){
	if(!dynamics.contains(dyn)) return;
	if(server!=null && server.dynamicServer!=null) server.dynamicServer.remove(dyn);
	dynamics.remove(dyn);
    }
    
    
    public void deleteDynamics(){
	if(server!=null && server.dynamicServer!=null){
	    for(IDynamics dyn:dynamics)
		server.dynamicServer.remove(dyn);
	}
    }
    
    
    //public void setGraphicMode(IGraphicMode m){} //create graphics if necessary
    
    //create graphics if necessary
    public IGraphicObject getGraphic(IGraphicMode m){
	if(graphics==null) graphics = new ArrayList<IGraphicObject>();
	else{
	    for(IGraphicObject gr: graphics)
		if(gr.isDrawable(m)) return gr;
	}
	IGraphicObject gr = createGraphic(m);
	if(attribute!=null) gr.setAttribute(attribute);
	if(gr!=null) graphics.add(gr);
	return gr;
	//return null;
    }
    
    // to be overwritten in subclasses
    public IGraphicObject createGraphic(IGraphicMode m){ 
	return null;
    }
    
    /** delete all graphics */
    public void deleteGraphic(){
	if(server!=null && server.graphicServer!=null){
	    //for(IGraphicObject gr:graphics)
	    for(int i=0; i<graphics.size(); i++)
		server.graphicServer.remove(graphics.get(i));
	}
    }

    /**
       update graphic when control point location changes or some minor change.
    */
    public void updateGraphic(){
	/*
	deleteGraphic();
	if(server!=null && server.graphicServer!=null)
	    synchronized(IG.lock){ server.graphicServer().add(this); }
	*/
	//for(IGraphicObject gr:graphics) gr.update();
	for(int i=0; i<graphics.size(); i++) graphics.get(i).update();
    }
    
    /**
       update whole graphic by deleting current one when there is major change.
    */
    public void resetGraphic(){
	deleteGraphic();
	if(server!=null && server.graphicServer!=null)
	    //synchronized(IG.lock){
	    synchronized(server.server().ig){
		server.graphicServer().add(this);
	    }
    }

    public IServer server(){ return server; }
    
    public String name(){
	if(attribute!=null) return attribute.name;
	return null;
    }
    public IObject name(String name){
	if(attribute==null) attribute = new IAttribute();
	attribute.name = name;
	return this;
    }
    
    /** Get layer of the object */
    public ILayer layer(){
	//return layer;
	if(attribute!=null) return attribute.layer;
	return null;
    }

    /** Set layer by ILayer object */
    public IObject layer(ILayer l){
	if(l==null){
	    if(attribute!=null){ attribute.layer = null; }
	}
	else{
	    if(attribute==null){
		attribute = new IAttribute();
		if(!l.contains(this)) l.add(this);
		attribute.layer = l;
	    }
	    else if(attribute.layer!=l){
		if(!l.contains(this)) l.add(this);
		attribute.layer = l;
	    }
	}
	/*
	if(l==null) layer=null;
	else if(layer!=l){
	    if(!l.contains(this)) l.add(this);
	    layer=l;
	}
	*/
	return this;
    }
    
    /** Set layer by layer name. If the layer specified by the name is not existing in the server, a new layer is automatically created in the server */
    public IObject layer(String layerName){
	if(server!=null) return layer(server.layer(layerName));
	return layer(IG.layer(layerName));
    }
    
    public IAttribute attr(){ return attribute; }
    public IObject attr(IAttribute at){ attribute=at; return this; }
    
    
    // shouldn't these methods be in other graphic class?
    
    
    public boolean visible(){
	if(attribute==null) attribute=new IAttribute(); // default true?
	return attribute.visible;
    }
    public boolean isVisible(){ return visible(); }
    
    public IObject hide(){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.hide();
	if(attribute==null) attribute=new IAttribute();
	attribute.visible=false;
	return this;
    }
    
    public IObject show(){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.show();
	if(attribute==null) attribute=new IAttribute();
	attribute.visible=true;
	return this;
    }

    /** update color of all graphics by the color in attribute */
    public void syncColor(){
	if(attribute!=null && graphics!=null){
	    for(IGraphicObject gr:graphics) gr.setColor(attribute.color);
	}
    }
    
    
    /** @return returns whatever Color of any graphics member. (first found) */
    public Color clr(){
	if(attribute!=null) return attribute.color;
	if(graphics!=null)
	    for(IGraphicObject gr:graphics)
		if(gr.getColor()!=null)
		    return gr.getColor();
	
	return IGraphicObject.getColor(IGraphicObject.defaultRed,
				       IGraphicObject.defaultGreen,
				       IGraphicObject.defaultBlue,
				       IGraphicObject.defaultAlpha);
    }
    
    public int redInt(){ return clr().getRed(); }
    public int greenInt(){ return clr().getGreen(); }
    public int blueInt(){ return clr().getBlue(); }
    public int alphaInt(){ return clr().getAlpha(); }
    
    public double red(){ return ((double)redInt()/255.); }
    public double green(){ return ((double)greenInt()/255.); }
    public double blue(){ return ((double)blueInt()/255.); }
    public double alpha(){ return ((double)alphaInt()/255.); }
    
    
    public IObject clr(Color c){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(c);
	syncColor();
	return this;
    }
    /** to set color, with alpha value overwritten */
    public IObject clr(Color c, int alpha){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(c,alpha);
	syncColor();
	return this;
    }
    public IObject clr(int gray){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(gray);
	syncColor();
	return this;
    }
    public IObject clr(double dgray){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(dgray);
	syncColor();
	return this;
    }
    public IObject clr(float fgray){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(fgray);
	syncColor();
	return this;
    }
    public IObject clr(int gray, int alpha){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(gray,alpha);
	syncColor();
	return this;
    }
    public IObject clr(double dgray, double dalpha){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(dgray, dalpha);
	syncColor();
	return this;
    }
    public IObject clr(float fgray, float falpha){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(fgray, falpha);
	syncColor();
	return this;
    }
    public IObject clr(int r, int g, int b){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(r,g,b);
	syncColor();
	return this;
    }
    public IObject clr(double dr, double dg, double db){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(dr,dg,db);
	syncColor();
	return this;
    }
    public IObject clr(float fr, float fg, float fb){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(fr,fg,fb);
	syncColor();
	return this;
    }
    public IObject clr(int r, int g, int b, int a){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(r,g,b,a);
	syncColor();
	return this;
    }
    public IObject clr(double dr, double dg, double db, double da){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(dr,dg,db,da);
	syncColor();
	return this;
    }
    public IObject clr(float fr, float fg, float fb, float fa){
	if(attribute==null) attribute = new IAttribute();
	attribute.clr(fr,fg,fb,fa);
	syncColor();
	return this;
    }
    public IObject hsb(double dh, double ds, double db, double da){
	if(attribute==null) attribute = new IAttribute();
	attribute.hsb(dh,ds,db,da);
	syncColor();
	return this;
    }
    public IObject hsb(float h, float s, float b, float a){
	if(attribute==null) attribute = new IAttribute();
	attribute.hsb(h,s,b,a);
	syncColor();
	return this;
    }
    public IObject hsb(double dh, double ds, double db){
	if(attribute==null) attribute = new IAttribute();
	attribute.hsb(dh,ds,db);
	syncColor();
	return this;
    }
    public IObject hsb(float h, float s, float b){
	if(attribute==null) attribute = new IAttribute();
	attribute.hsb(h,s,b);
	syncColor();
	return this;
    }
    
    
    
    /** @return returns whatever Color of any graphics member. (first found)
     */
    public Color getColor(){ return clr(); }
    
    public IObject setColor(Color c){ return clr(c); }
    public IObject setColor(Color c, int alpha){ return clr(c,alpha); }
    public IObject setColor(int gray){ return clr(gray); }
    public IObject setColor(float fgray){ return clr(fgray); }
    public IObject setColor(double dgray){ return clr(dgray); }
    public IObject setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IObject setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IObject setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IObject setColor(int r, int g, int b){ return clr(r,g,b); }
    public IObject setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IObject setColor(double dr, double dg, double db){ return clr(dr,dg,db); }
    public IObject setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IObject setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IObject setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IObject setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IObject setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IObject setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IObject setHSBColor(double h, double s, double b){ return hsb(h,s,b); }
    
    
    /*
    public void setGraphic(IGraphicObject graf){
	if(graphic!=null) IOut.p("graphic is already set. overwrote."); 
	graphic = graf;
    }
    public void setDynamic(IGDynamicSubelement dyna){
	if(dynamic!=null) IOut.p("dynamic is already set. overwrote."); 
	dynamic = dyna;
    }
    public void createGraphics(){
	if(graphic!=null) IOut.p("graphic is already set. overwrote."); 
    }
    
    public void createDynamics(){
	if(dynamic!=null) IOut.p("dynamic is already set. overwrote."); 
    }
    */
    
    
    //public IGParameter parameter();
    //public IGraphics graphics();
    //public IGDynamics dynamics();
    
    //public void enableParameter();
    //public void enableGraphics();
    //public void enableDynamics();
    
    //public void register();
    //public void delete();
    
}
