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
   Class of XYZ orientation (not including origin, so not plane or coordinates system

   @author Satoru Sugihara
*/



class IOrient{
    IVec dir, nml; // as xyz coordinates system, dir corresponds to Y and nml corresponds to Z
    boolean righthand; // right hand coordinates system or not
    //IVec translate; // just to implement jumping behavior
    IOrient(IVec d, IVec n, boolean righthandsys){ dir=d; nml=n; righthand=righthandsys; }
    IOrient(IVec d, IVec n){ this(d,n,true); }
    IOrient(IOrient o){ dir=o.dir.cp(); nml=o.nml.cp(); righthand=o.righthand; }
    IOrient cp(){ return new IOrient(this); }
    IVec dir(){ return dir.cp(); }
    IVec front(){ return dir(); }
    IVec back(){ return dir().neg(); }
    IVec nml(){ return nml.cp(); }
    IVec up(){ return nml(); }
    IVec down(){ return nml().neg(); }
    IVec side(){ if(righthand){ return dir.cross(nml); }else{ return nml.cross(dir); } }
    IVec right(){ return side(); }
    IVec left(){ if(righthand){ return nml.cross(dir); }else{ return dir.cross(nml); } }
    IOrient rot(double ang){ dir.rot(nml, ang); return this; }
    IOrient rot(IVec ax, double ang){ dir.rot(ax,ang); nml.rot(ax,ang); return this; }
    IOrient pitch(double ang){
	IVec ax = dir.cross(nml); 
	dir.rot(ax, ang); nml.rot(ax, ang);
	return this;
    }
    IOrient yaw(double ang){ dir.rot(nml, ang); return this; }
    IOrient roll(double ang){ nml.rot(dir, ang); return this; }
    IOrient ref(IVec refNml){
	dir.ref(refNml); nml.ref(refNml); righthand = !righthand;
	return this;
    }
    IOrient flip(){ dir.flip(); righthand = !righthand; return this; }// flip front/back
    IOrient flipNml(){ nml.flip(); righthand = !righthand; return this; }// flip up/down
    IOrient flipSide(){ righthand = !righthand; return this; }// flip left/right
    IOrient mul(double v){ dir.mul(v); return this; }
    IOrient div(double v){ dir.div(v); return this; }
    
    IOrient add(IOrient o){ dir.add(o.dir); nml.add(o.nml()); return this; }
    IOrient add(IOrient o, double f){ dir.add(o.dir, f); nml.add(o.nml(), f); return this; }
    IOrient sum(IOrient o, double f){ dir.mul(1-f).add(o.dir, f); nml.mul(1-f).add(o.nml(), f); return this; }
    IOrient mid(IOrient o){ return sum(o,0.5); }
    
    //IOrient translate(IVec t){ return jump(t); }
    //IOrient jump(IVec move){ translate=move; return this; }
    //IOrient jump(double x, double y, double z){ return jump(new IVec(x,y,z)); }
    //IOrient jump(IOrient or){ return jump(or.dir.cp()); }
    //IOrient jump(double factor){ return jump(dir.cp().mul(factor)); }
    //IOrient jump(){ return jump(dir.cp()); }
}
