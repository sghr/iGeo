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
/*
  Interactive Geometry
  Copyright (c) 2005 - 2011 Satoru Sugihara
*/

package igeo;

/**
   Entity class of double (1 dimensional vector) to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public class IDouble extends IParameterObject implements IDoubleI, IEntityParameter{
    public double x;
    
    public IDouble(){}
    public IDouble(double x){ this.x=x; }
    public IDouble(IDouble v){ x=v.x; }
    public IDouble(IDoubleI v){ x=v.x(); }
    public IDouble(IIntegerI v){ x=v.x(); }
    
    public IDouble(IServerI s){ super(s); }
    public IDouble(IServerI s, double x){ super(s); this.x=x; }
    public IDouble(IServerI s, IDouble v){ super(s); x=v.x; }
    public IDouble(IServerI s, IDoubleI v){ super(s); x=v.x(); }
    public IDouble(IServerI s, IIntegerI v){ super(s); x=v.x(); }
    
    public double x(){ return x; }
    //public IDouble get(){ return this; }
    public IDouble get(){ return new IDouble(x); }
    public IDouble getX(){ return this; }    
    
    public IDouble dup(){ return new IDouble(x); }

    /** alias of dup() */
    public IDouble cp(){ return dup(); }
    
    /** duplicate and add */
    public IDouble cp(double v){ return dup().add(v); }
    /** duplicate and add */
    public IDouble cp(IDoubleI v){ return dup().add(v); }
    
    
    public IDouble set(double x){ this.x=x; return this; }
    //public IDouble set(IDouble v){ x=v.x; return this; }
    public IDouble set(IDoubleI v){ x=v.x(); return this; }
    public IDouble set(IIntegerI v){ x=v.x(); return this; }
    
    public IDouble add(double x){ this.x+=x; return this; }
    //public IDouble add(IDouble v){ x+=v.x; return this; }
    public IDouble add(IDoubleI v){ x+=v.x(); return this; }
    
    public IDouble sub(double x){ this.x-=x; return this; }    
    //public IDouble sub(IDouble v){ x-=v.x; return this; }
    public IDouble sub(IDoubleI v){ x-=v.x(); return this; }
    
    public IDouble mul(double x){ this.x*=x; return this; }
    //public IDouble mul(IDouble v){ x*=v.x; return this; }
    public IDouble mul(IDoubleI v){ x*=v.x(); return this; }
    
    public IDouble div(double x){ this.x/=x; return this; }
    //public IDouble div(IDouble v){ x/=v.x; return this; }
    public IDouble div(IDoubleI v){ x/=v.x(); return this; }
    
    public IDouble neg(){ x=-x; return this; }
    
    public IDouble inv(){ x=1./x; return this; }
    
    public IDouble abs(){ x = Math.abs(x); return this; }
    
    public IDouble pow(double n){ x = Math.pow(x,n); return this; }
    public IDouble pow(IDoubleI n){ x = Math.pow(x,n.x()); return this; }
    
    public IDouble sq(){ x = x*x; return this; }
    public IDouble sqrt(){ x = Math.sqrt(x); return this; }
    
    public IDouble exp(){ x = Math.exp(x); return this; }
    public IDouble log(){ x = Math.log(x); return this; }
    
    public IDouble sin(){ x=Math.sin(x); return this; }
    public IDouble cos(){ x=Math.cos(x); return this; }
    public IDouble tan(){ x=Math.tan(x); return this; }
    
    public IDouble asin(){ x=Math.asin(x); return this; }
    public IDouble acos(){ x=Math.acos(x); return this; }
    public IDouble atan(){ x=Math.atan(x); return this; }
    public IDouble atan2(double x2){ x=Math.atan2(x,x2); return this; }
    public IDouble atan2(IDoubleI x2){ x=Math.atan2(x,x2.x()); return this; }
    
    public IDouble deg(){ x=Math.toDegrees(x); return this; }
    public IDouble rad(){ x=Math.toRadians(x); return this; }

    public boolean eq(double v){ return eq(v,IConfig.tolerance); }
    public boolean eq(IDouble v){ return eq(v,IConfig.tolerance); }
    public boolean eq(IDoubleI v){ return eq(v.get(),IConfig.tolerance); }
    //public IBool eqR(IDoubleI v){ return new IBool(eq(v.get(),IConfig.tolerance)); }
    public boolean eq(ISwitchE e, IDoubleI v){ return eq(v); }
    public IBool eq(ISwitchR r, IDoubleI v){ return new IBool(eq(v)); }
    
    public boolean eq(double v, double resolution){ return Math.abs(x-v)<=resolution; }
    public boolean eq(IDouble v, double resolution){ return Math.abs(x-v.x)<=resolution; }
    public boolean eq(IDoubleI v, double resolution){ return Math.abs(x-v.x())<=resolution; }
    //public IBool eqR(IDoubleI v, IDoubleI resolution){ return new IBool(eq(v,resolution.x())); }
    public boolean eq(ISwitchE e, IDoubleI v, double resolution){ return eq(v,resolution); }
    public IBool eq(ISwitchR r, IDoubleI v, IDoubleI resolution){ return new IBool(eq(v,resolution.x())); }
    
    public boolean isValid(){
	if(!isValid(x)){ IOut.err("invalid value "+x); return false; }
	return true;
    }
    
    public static boolean isValid(double val){
	if(Double.isNaN(val)) return false;
	if(Double.isInfinite(val)) return false;
	return true;
    }
    
    public static boolean isValid(float val){
	if(Float.isNaN(val)) return false;
	if(Float.isInfinite(val)) return false;
	return true;
    }
    
    //public static boolean eq(double v1, double v2){ return eq(v1,v2,IConfig.tolerance); } //--> IG.java
    //public static boolean eq(double v1, double v2, double resolution){ return Math.abs(v1-v2)<=resolution; } //->IG.java
}
