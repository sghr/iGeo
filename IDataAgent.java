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

package igeo;

/**
   Agent class with a generic data
   
   @author Satoru Sugihara
*/
public class IDataAgent<T extends IArithmeticVal<T,S>,S> extends IPointAgent{
    
    public T data;
    
    public IDataAgent(){ super(); }
    public IDataAgent(T val){ super(); data = val; }
    public IDataAgent(IVecI pos){ super(pos); }
    public IDataAgent(IVecI pos, T val){ super(pos); data=val; }

    //public IDataAgent(IObject parent){ super(parent); }
    //public IDataAgent(T val, IObject parent){ super(parent); data=val; }

    public T getData(){ return data; } // no conflict of "get" with IParameter.get?

    public IDataAgent setData(T v){ data=v; return this; }

    public IDataAgent addData(T v){ data.add(v); return this; }


    /**************************************
     * methods of IObject
     *************************************/
    
    public IDataAgent name(String nm){ super.name(nm); return this; }
    public IDataAgent layer(ILayer l){ super.layer(l); return this; }
    
    public IDataAgent hide(){ super.hide(); return this; }
    public IDataAgent show(){ super.show(); return this; }
    
    public IDataAgent clr(IColor c){ super.clr(c); return this; }
    public IDataAgent clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IDataAgent clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IDataAgent clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IDataAgent clr(IObject o){ super.clr(o); return this; }
    
    //public IDataAgent clr(Color c){ super.clr(c); return this; }
    //public IDataAgent clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    //public IDataAgent clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    //public IDataAgent clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IDataAgent clr(int gray){ super.clr(gray); return this; }
    public IDataAgent clr(float fgray){ super.clr(fgray); return this; }
    public IDataAgent clr(double dgray){ super.clr(dgray); return this; }
    public IDataAgent clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IDataAgent clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IDataAgent clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IDataAgent clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IDataAgent clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IDataAgent clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IDataAgent clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IDataAgent clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IDataAgent clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IDataAgent hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IDataAgent hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IDataAgent hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IDataAgent hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }

    public IDataAgent weight(float w){ super.weight(w); return this; }
    public IDataAgent weight(double w){ super.weight(w); return this; }
    
    public IDataAgent setColor(IColor c){ super.setColor(c); return this; }
    public IDataAgent setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    public IDataAgent setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    public IDataAgent setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    //public IDataAgent setColor(Color c){ super.setColor(c); return this; }
    //public IDataAgent setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    //public IDataAgent setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    //public IDataAgent setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    public IDataAgent setColor(int gray){ super.setColor(gray); return this; }
    public IDataAgent setColor(float fgray){ super.setColor(fgray); return this; }
    public IDataAgent setColor(double dgray){ super.setColor(dgray); return this; }
    public IDataAgent setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IDataAgent setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IDataAgent setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IDataAgent setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IDataAgent setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IDataAgent setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IDataAgent setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IDataAgent setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IDataAgent setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IDataAgent setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IDataAgent setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IDataAgent setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IDataAgent setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
    
}
