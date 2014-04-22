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

import java.lang.reflect.*;
import java.util.ArrayList;
import java.awt.Color;
/**
   Field agent base class.
   @author Satoru Sugihara
*/

abstract public class IField extends IAgent implements IFieldI{
    public ArrayList<Class<? extends IParticleI>> targetClasses;
    
    //public IField(){}
    
    /** make the field applicable only to the specified target class */
    public IField targetClass(Class<? extends IParticleI> targetClass){
	targetClasses = new ArrayList<Class<? extends IParticleI>>();
	targetClasses.add(targetClass);
	return this;
    }
    /** alias */
    public IField target(Class<? extends IParticleI> targetClass){ return targetClass(targetClass); }
    
    
    /** make the field applicable only to the specified target classes */
    public IField targetClass(Class<? extends IParticleI>... targets){
	targetClasses = new ArrayList<Class<? extends IParticleI>>();
	for(Class<? extends IParticleI> tgt : targets){ targetClasses.add(tgt); }
	return this;
    }
    /** alias */
    public IField target(Class<? extends IParticleI>... targets){ return targetClass(targets); }
    
    public boolean isTargetClass(Object obj){
	for(int i=0; i<targetClasses.size(); i++){
	    if(targetClasses.get(i).isInstance(obj)) return true;
	}
	return false;
    }
    
    /** alias */
    public boolean isTarget(Object obj){ return isTargetClass(obj); }
    
    
    public void interact(ArrayList<IDynamics> agents){
	super.interact(agents); // in case of interact(IDynamics) in child class
	for(int i=0; i<agents.size(); i++){
	    if(targetClasses==null && agents.get(i) instanceof IParticleI ||
	       targetClasses!=null && isTargetClass(agents.get(i))){
		applyField((IParticleI)agents.get(i));
	    }
	}
    }
    
    abstract public void applyField(IParticleI particle);
    

    /** default field vector ignoreing velocity */
    public IVal get(IVecI pos, IVecI vel){ return get(pos); }
    
    /**************************************
     * methods of IObject
     *************************************/
    
    public IField name(String nm){ super.name(nm); return this; }
    public IField layer(ILayer l){ super.layer(l); return this; }
    
    public IField hide(){ super.hide(); return this; }
    public IField show(){ super.show(); return this; }
    
    public IField clr(IColor c){ super.clr(c); return this; }
    public IField clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public IField clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public IField clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public IField clr(Color c){ super.clr(c); return this; }
    public IField clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    public IField clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    public IField clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public IField clr(int gray){ super.clr(gray); return this; }
    public IField clr(float fgray){ super.clr(fgray); return this; }
    public IField clr(double dgray){ super.clr(dgray); return this; }
    public IField clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public IField clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public IField clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public IField clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public IField clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public IField clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public IField clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public IField clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public IField clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public IField hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public IField hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public IField hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public IField hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }

    public IField weight(float w){ super.weight(w); return this; }
    public IField weight(double w){ super.weight(w); return this; }
    
    public IField setColor(Color c){ super.setColor(c); return this; }
    public IField setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    public IField setColor(int gray){ super.setColor(gray); return this; }
    public IField setColor(float fgray){ super.setColor(fgray); return this; }
    public IField setColor(double dgray){ super.setColor(dgray); return this; }
    public IField setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public IField setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public IField setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public IField setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public IField setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public IField setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public IField setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public IField setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public IField setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public IField setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public IField setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public IField setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public IField setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    

}
