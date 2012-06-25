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
   Transformable objects.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/

public abstract class IGeometry extends IObject implements ITransformable{
    
    public IGeometry(){ super(); }
    public IGeometry(IServerI holder){ super(holder); }
    
    public IGeometry(IGeometry e){ super((IObject)e); }
    public IGeometry(IServerI holder, IGeometry e){ super(holder,(IObject)e); }
    
    public IGeometry dup(){ return null; } // need to be overridden
    public IGeometry cp(){ return dup(); } // need to be overridden
    
    /** Set layer by ILayer object */
    public IGeometry layer(ILayer l){ super.layer(l); return this; }
    
    /** Set layer by layer name. If the layer specified by the name is not existing in the server, a new layer is automatically created in the server */
    public IGeometry layer(String layerName){ super.layer(layerName); return this; }
    public IGeometry attr(IAttribute at){ super.attr(at); return this; }
    
    public IGeometry hide(){ super.hide(); return this; }
    public IGeometry show(){ super.show(); return this; }
    
    public IGeometry clr(Color c){ super.clr(c); return this; }
    public IGeometry clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IGeometry clr(int gray){ super.clr(gray); return this; }
    public IGeometry clr(double dgray){ super.clr(dgray); return this; }
    public IGeometry clr(float fgray){ super.clr(fgray); return this; }
    
    public IGeometry clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IGeometry clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IGeometry clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IGeometry clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IGeometry clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IGeometry clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IGeometry clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IGeometry clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IGeometry clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IGeometry hsb(double dh, double ds, double db, double da){ super.hsb(dh,ds,db,da); return this; }
    public IGeometry hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IGeometry hsb(double dh, double ds, double db){ super.hsb(dh,ds,db); return this; }
    public IGeometry hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    
    
    public IGeometry setColor(Color c){ return clr(c); }
    public IGeometry setColor(Color c, int alpha){ return clr(c,alpha); }
    public IGeometry setColor(int gray){ return clr(gray); }
    public IGeometry setColor(float fgray){ return clr(fgray); }
    public IGeometry setColor(double dgray){ return clr(dgray); }
    public IGeometry setColor(int gray, int alpha){ return clr(gray,alpha); }
    public IGeometry setColor(float fgray, float falpha){ return clr(fgray,falpha); }
    public IGeometry setColor(double dgray, double dalpha){ return clr(dgray,dalpha); }
    public IGeometry setColor(int r, int g, int b){ return clr(r,g,b); }
    public IGeometry setColor(float fr, float fg, float fb){ return clr(fr,fg,fb); }
    public IGeometry setColor(double dr, double dg, double db){ return clr(dr,dg,db); }
    public IGeometry setColor(int r, int g, int b, int a){ return clr(r,g,b,a); }
    public IGeometry setColor(float fr, float fg, float fb, float fa){ return clr(fr,fg,fb,fa); }
    public IGeometry setColor(double dr, double dg, double db, double da){ return clr(dr,dg,db,da); }
    public IGeometry setHSBColor(float h, float s, float b, float a){ return hsb(h,s,b,a); }
    public IGeometry setHSBColor(double h, double s, double b, double a){ return hsb(h,s,b,a); }
    public IGeometry setHSBColor(float h, float s, float b){ return hsb(h,s,b); }
    public IGeometry setHSBColor(double h, double s, double b){ return hsb(h,s,b); }
    
    public IGeometry weight(double w){ super.weight(w); return this; }
    public IGeometry weight(float w){ super.weight(w); return this; }
        
}
