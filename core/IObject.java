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

package igeo.core;

import java.util.ArrayList;
import java.awt.Color;
import igeo.gui.*;


/**
   A base class of iGeo object to be contained by IServer.
   Only instances of sub-classes of IObject can be carried by IServer.
   IObject contains three types of subobjects.
   One IParameterObject, multiple IGraphicObject and multiple IDynamicObject.
   If you assign IServerI in the constructor, the instance is contained by the server.
   If not the instance is contained by a server found in static IG methods.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
//public interface IElement{
//public class IElement{
public class IObject{
    
    public int id; // or use String UUID or use object instance's hash code?
    public String name;
    
    public IServer server;
    
    /*protected*/ public IParameterObject parameter;
    //IGraphicObject graphic;
    //IDynamicObject dynamic;
    public ArrayList<IGraphicObject> graphics;
    public ArrayList<IDynamicObject> dynamics;
    
    public ILayer layer;
    
    /**
       IObject is stored in default IServer (through current static IG instance)
    */
    public IObject(){ initObject(null); }
    
    /**
       IObject is stored in the IServer via holder
    */
    public IObject(IServerI holder){
	initObject(holder);
    }
    
    public IObject(IObject e){
	initObject(e.server);
	
	// geometry might not be ready in subclass
	//if(graphics!=null){ initGraphic(e.server); setColor(e.getColor()); }
    }
    
    public IObject(IServerI holder, IObject e){
	initObject(holder);
	
	// geometry might not be ready in subclass
	//if(graphics!=null){ initGraphic(holder); setColor(e.getColor()); }
    }
    
    public IObject dup(){ return new IObject(this); }
    
    
    public void initObject(IServerI holder){
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
	
	synchronized(IG.lock){
	    server.add(this);
	}
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
	    synchronized(IG.lock){
		server.graphicServer().add(this);
	    }
    }
    
    public void del(){
	if(server!=null) server.remove(this);
	else if(IG.current()!=null) IG.current().server().remove(this);
    }
    
    public IObject hide(){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.hide();
	return this;
    }
    
    public IObject show(){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.show();
	return this;
    }
    
    
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
    
    
    public IParameterObject getParameter(){ return parameter; }
    public IGraphicObject getGraphic(int i){
	if(graphics==null) return null;
	return graphics.get(i);
    }
    public IDynamicObject getDynamics(int i){
	if(dynamics==null) return null;
	return dynamics.get(i);
    }
    
    public int graphicsNum(){ if(graphics==null) return 0; return graphics.size(); }
    public int dynamicsNum(){ if(dynamics==null) return 0; return dynamics.size(); }
        
    public void clearGraphics(){ if(graphics!=null) graphics.clear(); }
    public void clearDynamics(){ if(dynamics!=null) dynamics.clear(); }
    
    //public void setGraphicMode(IGraphicMode m){} //create graphics if necessary
    
    //create graphics if necessary
    public IGraphicObject getGraphic(IGraphicMode m){
	if(graphics==null) graphics = new ArrayList<IGraphicObject>();
	else{
	    for(IGraphicObject gr: graphics)
		if(gr.isDrawable(m)) return gr;
	}
	IGraphicObject gr = createGraphic(m);
	if(gr!=null) graphics.add(gr);
	return gr;
	//return null;
    }
    
    // to be overwritten in subclasses
    public IGraphicObject createGraphic(IGraphicMode m){ 
	return null;
    }
    
    // delete all graphics
    public void deleteGraphic(){
	if(server!=null && server.graphicServer!=null){
	    for(IGraphicObject gr:graphics)
		server.graphicServer.remove(gr);
	}
    }
    
    public void updateGraphic(){
	deleteGraphic();
	if(server!=null && server.graphicServer!=null)
	    synchronized(IG.lock){ server.graphicServer().add(this); }
    }
    
    
    public String name(){ return name; }
    public IObject name(String name){ this.name = name; return this; }
    
    
    public ILayer layer(){ return layer; }
    public IObject layer(ILayer l){ l.add(this); return this; }
    
    
    // shouldn't this be in other graphic class?
    
    /** @return returns whatever Color of any graphics member. (first found)
     */
    public Color clr(){
	if(graphics==null) return null;
	for(IGraphicObject gr:graphics) if(gr.getColor()!=null) return gr.getColor();
	return null;
    }
    
    public IObject clr(Color c){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor(c);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(int gray){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor(gray);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(double dgray){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor((float)dgray);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(float fgray){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor(fgray);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(int gray, int alpha){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor(gray,alpha);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(double dgray, double dalpha){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor((float)dgray,(float)dalpha);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(float fgray, float falpha){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor(fgray,falpha);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(int r, int g, int b){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor(r,g,b);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(double dr, double dg, double db){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor((float)dr,(float)dg,(float)db);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(float fr, float fg, float fb){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor(fr,fg,fb);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(int r, int g, int b, int a){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor(r,g,b,a);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(double dr, double dg, double db, double da){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor((float)dr,(float)dg,(float)db,(float)da);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject clr(float fr, float fg, float fb, float fa){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setColor(fr,fg,fb,fa);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject hsb(double dh, double ds, double db, double da){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setHSBColor((float)dh,(float)ds,(float)db,(float)da);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject hsb(float h, float s, float b, float a){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setHSBColor(h,s,b,a);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject hsb(double dh, double ds, double db){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setHSBColor((float)dh,(float)ds,(float)db);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    public IObject hsb(float h, float s, float b){
	if(graphics!=null) for(IGraphicObject gr:graphics) gr.setHSBColor(h,s,b);
	else IOut.err("no graphics created yet"); //
	return this;
    }
    
    
    /** @return returns whatever Color of any graphics member. (first found)
     */
    public Color getColor(){ return clr(); }
    
    public IObject setColor(Color c){ return clr(c); }
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
