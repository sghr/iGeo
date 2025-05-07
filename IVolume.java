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

import java.awt.Color;

import igeo.gui.*;

/**
   Class of NURBS volume object.
   It contains IVolumeGeo instance inside.

   @author Satoru Sugihara
*/
public class IVolume extends IGeometry implements IVolumeI{
    public IVolumeGeo volume;
    //public IVolumeI surface; // public?

    public IVolume(){ /*surface = new IVolumeGeo();*/ }

    public IVolume(IVecI[][][] cpts, int udegree, int vdegree, int wdegree,
		       double[] uknots, double[] vknots, double[] wknots,
		       double ustart, double uend, double vstart, double vend, double wstart, double wend){
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree, uknots, vknots, wknots, ustart, uend, vstart, vend, wstart, wend);
      initVolume(null);
    }

    public IVolume(IVecI[][][] cpts, int udegree, int vdegree, int wdegree,
		       double[] uknots, double[] vknots, double[] wknots){
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree, uknots, vknots, wknots);
      initVolume(null);
    }

    public IVolume(IVecI[][][] cpts, int udegree, int vdegree, int wdegree){
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree);
      initVolume(null);
    }

    public IVolume(IVecI[][][] cpts){
      volume = new IVolumeGeo(cpts);
      initVolume(null);
    }

    public IVolume(IVecI[][][] cpts, int udegree, int vdegree, int wdegree, boolean closeU, boolean closeV, boolean closeW){
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree,closeU,closeV,closeW);
      initVolume(null);
    }

    public IVolume(IVecI[][][] cpts, int udegree, int vdegree, int wdegree, boolean closeU, double[] vk, double[] wk){
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree,closeU,vk,wk);
      initVolume(null);
    }

    public IVolume(IVecI[][][] cpts, int udegree, int vdegree, int wdegree, double[] uk, boolean closeV, double[] wk){
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree,uk,closeV,wk);
      initVolume(null);
    }

    public IVolume(IVecI[][][] cpts, int udegree, int vdegree, int wdegree, double[] uk, double[] vk, boolean closeW){
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree,uk,vk,closeW);
      initVolume(null);
    }

    public IVolume(IVecI[][][] cpts, boolean closeU, boolean closeV, boolean closeW){
      volume = new IVolumeGeo(cpts, closeU, closeV, closeW);
      initVolume(null);
    }

    public IVolume(IVecI pt111, IVecI pt211, IVecI pt121, IVecI pt221, IVecI pt112, IVecI pt212, IVecI pt122, IVecI pt222){
      volume = new IVolumeGeo(pt111,pt211,pt121,pt221,pt112,pt212,pt112,pt222);
      initVolume(null);
    }

    public IVolume(double x111, double y111, double z111, double x211, double y211, double z211,
                    double x121, double y121, double z121, double x221, double y221, double z221,
                    double x112, double y112, double z112, double x212, double y212, double z212,
                    double x122, double y122, double z122, double x222, double y222, double z222){
      volume = new IVolumeGeo(x111, y111, z111, x211, y211, z211, x121, y121, z121, x221, y221, z221,
                      x112, y112, z112, x212, y212, z212, x122, y122, z122, x222, y222, z222);
      initVolume(null);
    }

    public IVolume(double[][][][] xyzValues){
      volume = new IVolumeGeo(xyzValues);
      initVolume(null);
    }

    public IVolume(double[][][][] xyzValues, int udeg, int vdeg, int wdeg){
      volume = new IVolumeGeo(xyzValues,udeg,vdeg,wdeg);
      initVolume(null);
    }

    public IVolume(double[][][][] xyzValues, boolean closeU, boolean closeV, boolean closeW){
      volume = new IVolumeGeo(xyzValues,closeU,closeV,closeW);
      initVolume(null);
    }

    public IVolume(double[][][][] xyzValues, int udeg, int vdeg, int wdeg, boolean closeU, boolean closeV, boolean closeW){
      volume = new IVolumeGeo(xyzValues,udeg,vdeg,wdeg,closeU,closeV,closeW);
      initVolume(null);
    }


    public IVolume(IVolumeGeo vol){ volume = vol; initVolume(null); }

    public IVolume(IVolumeI vol){ volume = vol.get(); initVolume(null); }


    public IVolume(IServerI s){ super(s); /*surface = new IVolumeGeo();*/ /*initSurface(s);*/ }


    public IVolume(IServerI s, IVecI[][][] cpts, int udegree, int vdegree, int wdegree,
		       double[] uknots, double[] vknots, double[] wknots,
		       double ustart, double uend, double vstart, double vend, double wstart, double wend){
      super(s);
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree, uknots, vknots, wknots, ustart, uend, vstart, vend, wstart, wend);
      initVolume(s);
    }

    public IVolume(IServerI s, IVecI[][][] cpts, int udegree, int vdegree, int wdegree,
		       double[] uknots, double[] vknots, double[] wknots){
      super(s);
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree, uknots, vknots, wknots);
      initVolume(s);
    }

    public IVolume(IServerI s, IVecI[][][] cpts, int udegree, int vdegree, int wdegree){
      super(s);
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree);
      initVolume(s);
    }

    public IVolume(IServerI s, IVecI[][][] cpts){
      super(s);
      volume = new IVolumeGeo(cpts);
      initVolume(s);
    }

    public IVolume(IServerI s, IVecI[][][] cpts, int udegree, int vdegree, int wdegree, boolean closeU, boolean closeV, boolean closeW){
      super(s);
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree,closeU,closeV,closeW);
      initVolume(s);
    }

    public IVolume(IServerI s, IVecI[][][] cpts, int udegree, int vdegree, int wdegree, boolean closeU, double[] vk, double[] wk){
      super(s);
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree,closeU,vk,wk);
      initVolume(s);
    }

    public IVolume(IServerI s, IVecI[][][] cpts, int udegree, int vdegree, int wdegree, double[] uk, boolean closeV, double[] wk){
      super(s);
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree,uk,closeV,wk);
      initVolume(s);
    }

    public IVolume(IServerI s, IVecI[][][] cpts, int udegree, int vdegree, int wdegree, double[] uk, double[] vk, boolean closeW){
      super(s);
      volume = new IVolumeGeo(cpts, udegree, vdegree, wdegree,uk,vk,closeW);
      initVolume(s);
    }

    public IVolume(IServerI s, IVecI[][][] cpts, boolean closeU, boolean closeV, boolean closeW){
      super(s);
      volume = new IVolumeGeo(cpts, closeU, closeV, closeW);
      initVolume(s);
    }

    public IVolume(IServerI s, IVecI pt111, IVecI pt211, IVecI pt121, IVecI pt221, IVecI pt112, IVecI pt212, IVecI pt122, IVecI pt222){
      super(s);
      volume = new IVolumeGeo(pt111,pt211,pt121,pt221,pt112,pt212,pt112,pt222);
      initVolume(s);
    }

    public IVolume(IServerI s, double x111, double y111, double z111, double x211, double y211, double z211,
                    double x121, double y121, double z121, double x221, double y221, double z221,
                    double x112, double y112, double z112, double x212, double y212, double z212,
                    double x122, double y122, double z122, double x222, double y222, double z222){
      super(s);
      volume = new IVolumeGeo(x111, y111, z111, x211, y211, z211, x121, y121, z121, x221, y221, z221,
                      x112, y112, z112, x212, y212, z212, x122, y122, z122, x222, y222, z222);
      initVolume(s);
    }

    public IVolume(IServerI s, double[][][][] xyzValues){
      super(s);
      volume = new IVolumeGeo(xyzValues);
      initVolume(s);
    }

    public IVolume(IServerI s, double[][][][] xyzValues, int udeg, int vdeg, int wdeg){
      super(s);
      volume = new IVolumeGeo(xyzValues,udeg,vdeg,wdeg);
      initVolume(s);
    }

    public IVolume(IServerI s, double[][][][] xyzValues, boolean closeU, boolean closeV, boolean closeW){
      super(s);
      volume = new IVolumeGeo(xyzValues,closeU,closeV,closeW);
      initVolume(s);
    }

    public IVolume(IServerI s, double[][][][] xyzValues, int udeg, int vdeg, int wdeg, boolean closeU, boolean closeV, boolean closeW){
      super(s);
      volume = new IVolumeGeo(xyzValues,udeg,vdeg,wdeg,closeU,closeV,closeW);
      initVolume(s);
    }

    public IVolume(IServerI s, IVolumeGeo vol){ super(s); volume = vol; initVolume(s); }

    public IVolume(IServerI s, IVolumeI vol){ super(s); volume = vol.get(); initVolume(s); }


    public IVolume(IVolume vol){
      super(vol);
      //surface = new IVolumeGeo(srf.surface.get());
      volume = vol.volume.dup();
      initVolume(vol.server);
      //setColor(srf.getColor());
    }

    public IVolume(IServerI s, IVolume vol){
      super(s,vol);
      //surface = new IVolumeGeo(srf.surface.get());
      volume = vol.volume.dup();
      initVolume(s);
      //setColor(srf.getColor());
    }

    public void initVolume(IServerI s){
      if(!isValid()){ // added 20141129
        IOut.err("Volume is invalid. Deleted");
        del();
        return;
      }
      if(volume instanceof IVolumeGeo){
        parameter = (IVolumeGeo)volume;
      }
      if(graphics==null) initGraphic(s);
    }

    public IGraphicObject createGraphic(IGraphicMode m){
      if(m.isNone()) return null;
      //if(m.isGL()){
      if(m.isGraphic3D()){
        return new IVolumeGraphicGL(this);
      }
      return null;
    }


    synchronized public boolean isValid(){ if(volume==null){ return false; } return volume.isValid(); }

    synchronized public IVolumeGeo get(){ return volume.get(); } // ?

    synchronized public IVolume dup(){ return new IVolume(this); }

    /**
       @param u u coordinates in uvw parameter space
       @param v v coordinates in uvw parameter space
       @param w w coordinates in uvw parameter space
    */
    synchronized public IVec pt(double u, double v, double w){ return volume.pt(u,v,w); }
    synchronized public IVec pt(IDoubleI u, IDoubleI v, IDoubleI w){ return volume.pt(u,v,w); }
    synchronized public IVec pt(IVecI uvw){ return volume.pt(uvw); }

    synchronized public IVec utan(double u, double v, double w){ return volume.utan(u,v,w); }
    synchronized public IVec utan(IDoubleI u, IDoubleI v, IDoubleI w){ return volume.utan(u,v,w); }
    synchronized public IVec utan(IVecI uvw){ return volume.utan(uvw); }

    synchronized public IVec vtan(double u, double v, double w){ return volume.vtan(u,v,w); }
    synchronized public IVec vtan(IDoubleI u, IDoubleI v, IDoubleI w){ return volume.vtan(u,v,w); }
    synchronized public IVec vtan(IVecI uvw){ return volume.vtan(uvw); }

    synchronized public IVec wtan(double u, double v, double w){ return volume.wtan(u,v,w); }
    synchronized public IVec wtan(IDoubleI u, IDoubleI v, IDoubleI w){ return volume.wtan(u,v,w); }
    synchronized public IVec wtan(IVecI uvw){ return volume.wtan(uvw); }

    synchronized public IVec4 nml4(double u, double v, double w){ return volume.nml4(u,v,w); }


    /** getting control point at i, j, k */
    synchronized public IVec cp(int i, int j, int k){ return volume.cp(i,j,k).get(); }
    /** getting control point at i, j, k */
    synchronized public IVecI cp(IIntegerI i, IIntegerI j, IIntegerI k){ return volume.cp(i,j,k); }

    synchronized public IVecI[][][] cps(){ return volume.cps(); }

    /** getting edit point at i, j, k */
    synchronized public IVec ep(int i, int j, int k){ return volume.ep(i,j,k); }
    /** getting edit point at i, j, k */
    synchronized public IVec ep(IIntegerI i, IIntegerI j, IIntegerI k){ return volume.ep(i,j,k); }

    synchronized public IVec corner(int i, int j, int k){ return volume.corner(i,j,k); }
    synchronized public IVec corner(IIntegerI i, IIntegerI j, IIntegerI k){ return volume.corner(i,j,k); }
    synchronized public IVec cornerCP(int i, int j, int k){ return volume.cornerCP(i,j,k); }
    synchronized public IVecI cornerCP(IIntegerI i, IIntegerI j, IIntegerI k){ return volume.cornerCP(i,j,k); }



    /** mid in UV parameter (u=0.5, v=0.5) point on a surface */
    synchronized public IVec mid(){ return volume.mid(); }

    /** returns center of geometry object */
    synchronized public IVec center(){ return volume.center(); }



    synchronized public double uknot(int i){ return volume.uknot(i); }
    synchronized public IDouble uknot(IIntegerI i){ return volume.uknot(i); }
    synchronized public double vknot(int i){ return volume.vknot(i); }
    synchronized public IDouble vknot(IIntegerI i){ return volume.vknot(i); }
    synchronized public double wknot(int i){ return volume.wknot(i); }
    synchronized public IDouble wknot(IIntegerI i){ return volume.wknot(i); }

    synchronized public double[] uknots(){ return volume.uknots(); }
    synchronized public double[] vknots(){ return volume.vknots(); }
    synchronized public double[] wknots(){ return volume.wknots(); }
    synchronized public double[] uknots(ISwitchE e){ return uknots(); }
    synchronized public double[] vknots(ISwitchE e){ return vknots(); }
    synchronized public double[] wknots(ISwitchE e){ return wknots(); }
    synchronized public IDoubleI[] uknots(ISwitchR r){ return volume.uknots(r); }
    synchronized public IDoubleI[] vknots(ISwitchR r){ return volume.vknots(r); }
    synchronized public IDoubleI[] wknots(ISwitchR r){ return volume.wknots(r); }

    synchronized public int uknotNum(){ return volume.uknotNum(); }
    synchronized public int vknotNum(){ return volume.vknotNum(); }
    synchronized public int wknotNum(){ return volume.wknotNum(); }

    synchronized public int uknotNum(ISwitchE e){ return uknotNum(); }
    synchronized public int vknotNum(ISwitchE e){ return vknotNum(); }
    synchronized public int wknotNum(ISwitchE e){ return wknotNum(); }
    synchronized public IInteger uknotNum(ISwitchR r){ return new IInteger(uknotNum()); }
    synchronized public IInteger vknotNum(ISwitchR r){ return new IInteger(vknotNum()); }
    synchronized public IInteger wknotNum(ISwitchR r){ return new IInteger(wknotNum()); }

    synchronized public boolean isRational(){ return volume.isRational(); }
    synchronized public boolean isRational(ISwitchE e){ return volume.isRational(); }
    synchronized public IBool isRational(ISwitchR r){ return new IBool(isRational()); }


    synchronized public int udeg(){ return volume.udeg(); }
    synchronized public int vdeg(){ return volume.vdeg(); }
    synchronized public int wdeg(){ return volume.wdeg(); }
    synchronized public int udeg(ISwitchE e){ return udeg(); }
    synchronized public int vdeg(ISwitchE e){ return vdeg(); }
    synchronized public int wdeg(ISwitchE e){ return wdeg(); }
    synchronized public IInteger udeg(ISwitchR r){ return new IInteger(udeg()); }
    synchronized public IInteger vdeg(ISwitchR r){ return new IInteger(vdeg()); }
    synchronized public IInteger wdeg(ISwitchR r){ return new IInteger(wdeg()); }

    synchronized public int unum(){ return volume.unum(); }
    synchronized public int vnum(){ return volume.vnum(); }
    synchronized public int wnum(){ return volume.wnum(); }
    synchronized public int unum(ISwitchE e){ return unum(); }
    synchronized public int vnum(ISwitchE e){ return vnum(); }
    synchronized public int wnum(ISwitchE e){ return wnum(); }
    synchronized public IInteger unum(ISwitchR r){ return new IInteger(unum()); }
    synchronized public IInteger vnum(ISwitchR r){ return new IInteger(vnum()); }
    synchronized public IInteger wnum(ISwitchR r){ return new IInteger(wnum()); }

    synchronized public int ucpNum(){ return volume.ucpNum(); } // equals to unum()
    synchronized public int vcpNum(){ return volume.vcpNum(); } // equals to vnum()
    synchronized public int wcpNum(){ return volume.wcpNum(); } // equals to wnum()

    synchronized public int ucpNum(ISwitchE e){ return ucpNum(); }
    synchronized public int vcpNum(ISwitchE e){ return vcpNum(); }
    synchronized public int wcpNum(ISwitchE e){ return wcpNum(); }
    synchronized public IInteger ucpNum(ISwitchR r){ return new IInteger(ucpNum()); }
    synchronized public IInteger vcpNum(ISwitchR r){ return new IInteger(vcpNum()); }
    synchronized public IInteger wcpNum(ISwitchR r){ return new IInteger(wcpNum()); }

    synchronized public int uepNum(){ return volume.uepNum(); }
    synchronized public int vepNum(){ return volume.vepNum(); }
    synchronized public int wepNum(){ return volume.wepNum(); }
    synchronized public int uepNum(ISwitchE e){ return uepNum(); }
    synchronized public int vepNum(ISwitchE e){ return vepNum(); }
    synchronized public int wepNum(ISwitchE e){ return wepNum(); }
    synchronized public IInteger uepNum(ISwitchR r){ return new IInteger(uepNum()); }
    synchronized public IInteger vepNum(ISwitchR r){ return new IInteger(vepNum()); }
    synchronized public IInteger wepNum(ISwitchR r){ return new IInteger(wepNum()); }

    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    synchronized public double u(int epIdx, double epFraction){ return volume.u(epIdx,epFraction); }
    synchronized public IDouble u(IIntegerI epIdx, IDoubleI epFraction){ return volume.u(epIdx,epFraction); }

    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    synchronized public double v(int epIdx, double epFraction){ return volume.v(epIdx,epFraction); }
    synchronized public IDouble v(IIntegerI epIdx, IDoubleI epFraction){ return volume.v(epIdx,epFraction); }

    // epIdx: 0-epNum, epFraction: 0-1 or -1-0
    synchronized public double w(int epIdx, double epFraction){ return volume.w(epIdx,epFraction); }
    synchronized public IDouble w(IIntegerI epIdx, IDoubleI epFraction){ return volume.w(epIdx,epFraction); }

    synchronized public double ustart(){ return volume.ustart(); }
    synchronized public double uend(){ return volume.uend(); }
    synchronized public double vstart(){ return volume.vstart(); }
    synchronized public double vend(){ return volume.vend(); }
    synchronized public double wstart(){ return volume.wstart(); }
    synchronized public double wend(){ return volume.wend(); }
    synchronized public double ustart(ISwitchE e){ return ustart(); }
    synchronized public double uend(ISwitchE e){ return uend(); }
    synchronized public double vstart(ISwitchE e){ return vstart(); }
    synchronized public double vend(ISwitchE e){ return vend(); }
    synchronized public double wstart(ISwitchE e){ return wstart(); }
    synchronized public double wend(ISwitchE e){ return wend(); }
    synchronized public IDouble ustart(ISwitchR r){ return new IDouble(ustart()); }
    synchronized public IDouble uend(ISwitchR r){ return new IDouble(uend()); }
    synchronized public IDouble vstart(ISwitchR r){ return new IDouble(vstart()); }
    synchronized public IDouble vend(ISwitchR r){ return new IDouble(vend()); }
    synchronized public IDouble wstart(ISwitchR r){ return new IDouble(wstart()); }
    synchronized public IDouble wend(ISwitchR r){ return new IDouble(wend()); }


    synchronized public boolean isUClosed(){ return volume.isUClosed(); }
    synchronized public boolean isUClosed(ISwitchE e){ return volume.isUClosed(); }
    synchronized public IBool isUClosed(ISwitchR r){ return volume.isUClosed(r); }

    synchronized public boolean isVClosed(){ return volume.isVClosed(); }
    synchronized public boolean isVClosed(ISwitchE e){ return volume.isVClosed(); }
    synchronized public IBool isVClosed(ISwitchR r){ return volume.isVClosed(r); }

    synchronized public boolean isWClosed(){ return volume.isWClosed(); }
    synchronized public boolean isWClosed(ISwitchE e){ return volume.isWClosed(); }
    synchronized public IBool isWClosed(ISwitchR r){ return volume.isWClosed(r); }


    /*********************************************************************************
     * transformation methods; API of ITransformable interface
     *********************************************************************************/

    synchronized public IVolume add(double x, double y, double z){ volume.add(x,y,z); return this; }
    synchronized public IVolume add(IDoubleI x, IDoubleI y, IDoubleI z){ volume.add(x,y,z); return this; }
    synchronized public IVolume add(IVecI v){ volume.add(v); return this; }
    synchronized public IVolume sub(double x, double y, double z){ volume.sub(x,y,z); return this; }
    synchronized public IVolume sub(IDoubleI x, IDoubleI y, IDoubleI z){ volume.sub(x,y,z); return this; }
    synchronized public IVolume sub(IVecI v){ volume.sub(v); return this; }
    synchronized public IVolume mul(IDoubleI v){ volume.mul(v); return this; }
    synchronized public IVolume mul(double v){ volume.mul(v); return this; }
    synchronized public IVolume div(IDoubleI v){ volume.div(v); return this; }
    synchronized public IVolume div(double v){ volume.div(v); return this; }

    synchronized public IVolume neg(){ volume.neg(); return this; }
    /** alias of neg */
    synchronized public IVolume flip(){ return neg(); }


    /** scale add */
    synchronized public IVolume add(IVecI v, double f){ volume.add(v,f); return this; }
    synchronized public IVolume add(IVecI v, IDoubleI f){ volume.add(v,f); return this; }
    /** scale add alias */
    synchronized public IVolume add(double f, IVecI v){ return add(v,f); }
    synchronized public IVolume add(IDoubleI f, IVecI v){ return add(v,f); }

    synchronized public IVolume rot(IDoubleI angle){ volume.rot(angle); return this; }
    synchronized public IVolume rot(double angle){ volume.rot(angle); return this; }

    synchronized public IVolume rot(IVecI axis, IDoubleI angle){ volume.rot(axis,angle); return this; }
    synchronized public IVolume rot(IVecI axis, double angle){ volume.rot(axis,angle); return this; }

    synchronized public IVolume rot(IVecI center, IVecI axis, IDoubleI angle){ volume.rot(center,axis,angle); return this; }
    synchronized public IVolume rot(IVecI center, IVecI axis, double angle){ volume.rot(center,axis,angle); return this; }

    /** rotate to destination direction vector */
    synchronized public IVolume rot(IVecI axis, IVecI destDir){ volume.rot(axis,destDir); return this; }
    /** rotate to destination point location */
    synchronized public IVolume rot(IVecI center, IVecI axis, IVecI destPt){ volume.rot(center,axis,destPt); return this; }

    synchronized public IVolume rot2(IDoubleI angle){ return rot(angle); }
    synchronized public IVolume rot2(double angle){ return rot(angle); }
    synchronized public IVolume rot2(IVecI center, IDoubleI angle){ volume.rot2(center,angle); return this; }
    synchronized public IVolume rot2(IVecI center, double angle){ volume.rot2(center,angle); return this; }

    /** rotation on xy-plane to destination direction vector */
    synchronized public IVolume rot2(IVecI destDir){ volume.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */
    synchronized public IVolume rot2(IVecI center, IVecI destPt){ volume.rot2(center,destPt); return this; }


    /** alias of mul */
    synchronized public IVolume scale(IDoubleI f){ return mul(f); }
    synchronized public IVolume scale(double f){ return mul(f); }
    synchronized public IVolume scale(IVecI center, IDoubleI f){ volume.scale(center,f); return this; }
    synchronized public IVolume scale(IVecI center, double f){ volume.scale(center,f); return this; }

    /** scale only in 1 direction */
    synchronized public IVolume scale1d(IVecI axis, double f){ volume.scale1d(axis,f); return this; }
    synchronized public IVolume scale1d(IVecI axis, IDoubleI f){ volume.scale1d(axis,f); return this; }
    synchronized public IVolume scale1d(IVecI center, IVecI axis, double f){
	volume.scale1d(center,axis,f); return this;
    }
    synchronized public IVolume scale1d(IVecI center, IVecI axis, IDoubleI f){
	volume.scale1d(center,axis,f); return this;
    }

    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    synchronized public IVolume ref(IVecI planeDir){ volume.ref(planeDir); return this; }
    synchronized public IVolume ref(IVecI center, IVecI planeDir){ volume.ref(center,planeDir); return this; }
    /** mirror is alias of ref */
    synchronized public IVolume mirror(IVecI planeDir){ return ref(planeDir); }
    synchronized public IVolume mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }


    /** shear operation */
    synchronized public IVolume shear(double sxy, double syx, double syz,
			  double szy, double szx, double sxz){
      volume.shear(sxy,syx,syz,szy,szx,sxz);
      return this;
    }

    synchronized public IVolume shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			  IDoubleI szy, IDoubleI szx, IDoubleI sxz){
      volume.shear(sxy,syx,syz,szy,szx,sxz);
      return this;
    }
    synchronized public IVolume shear(IVecI center, double sxy, double syx, double syz,
			  double szy, double szx, double sxz){
	    volume.shear(center,sxy,syx,syz,szy,szx,sxz);
      return this;
    }
    synchronized public IVolume shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			  IDoubleI szy, IDoubleI szx, IDoubleI sxz){
    	volume.shear(center,sxy,syx,syz,szy,szx,sxz);
      return this;
    }

    synchronized public IVolume shearXY(double sxy, double syx){
      volume.shearXY(sxy,syx); return this;
    }
    synchronized public IVolume shearXY(IDoubleI sxy, IDoubleI syx){
      volume.shearXY(sxy,syx); return this;
    }
    synchronized public IVolume shearXY(IVecI center, double sxy, double syx){
      volume.shearXY(center,sxy,syx); return this;
    }
    synchronized public IVolume shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
      volume.shearXY(center,sxy,syx); return this;
    }

    synchronized public IVolume shearYZ(double syz, double szy){
      volume.shearYZ(syz,szy); return this;
    }
    synchronized public IVolume shearYZ(IDoubleI syz, IDoubleI szy){
      volume.shearYZ(syz,szy); return this;
    }
    synchronized public IVolume shearYZ(IVecI center, double syz, double szy){
      volume.shearYZ(center,syz,szy); return this;
    }
    synchronized public IVolume shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
      volume.shearYZ(center,syz,szy); return this;
    }

    synchronized public IVolume shearZX(double szx, double sxz){
      volume.shearZX(szx,sxz); return this;
    }
    synchronized public IVolume shearZX(IDoubleI szx, IDoubleI sxz){
      volume.shearZX(szx,sxz); return this;
    }
    synchronized public IVolume shearZX(IVecI center, double szx, double sxz){
      volume.shearZX(center,szx,sxz); return this;
    }
    synchronized public IVolume shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
      volume.shearZX(center,szx,sxz); return this;
    }


    /** mv() is alias of add() */
    synchronized public IVolume mv(double x, double y, double z){ return add(x,y,z); }
    synchronized public IVolume mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    synchronized public IVolume mv(IVecI v){ return add(v); }

    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */
    synchronized public IVolume cp(){ return dup(); }

    /** cp() is alias of dup().add() */
    synchronized public IVolume cp(double x, double y, double z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    synchronized public IVolume cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    /** cp() is alias of dup().add() */
    synchronized public IVolume cp(IVecI v){ return dup().add(v); }

    /** translate() is alias of add() */
    synchronized public IVolume translate(double x, double y, double z){ return add(x,y,z); }
    synchronized public IVolume translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    synchronized public IVolume translate(IVecI v){ return add(v); }

    synchronized public IVolume transform(IMatrix3I mat){ volume.transform(mat); return this; }
    synchronized public IVolume transform(IMatrix4I mat){ volume.transform(mat); return this; }
    synchronized public IVolume transform(IVecI xvec, IVecI yvec, IVecI zvec){
      volume.transform(xvec,yvec,zvec); return this;
    }
    synchronized public IVolume transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
      volume.transform(xvec,yvec,zvec,translate); return this;
    }


    /*********************************************************************************
     * methods of IObject
     *********************************************************************************/

    synchronized public IVolume name(String nm){ super.name(nm); return this; }
    synchronized public IVolume layer(ILayer l){ super.layer(l); return this; }
    synchronized public IVolume layer(String l){ super.layer(l); return this; }

    synchronized public IVolume attr(IAttribute at){ super.attr(at); return this; }


    synchronized public IVolume hide(){ super.hide(); return this; }
    synchronized public IVolume show(){ super.show(); return this; }


    synchronized public IVolume clr(IColor c){ super.clr(c); return this; }
    synchronized public IVolume clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public IVolume clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    synchronized public IVolume clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    synchronized public IVolume clr(IObject o){ super.clr(o); return this; }
    synchronized public IVolume clr(Color c){ super.clr(c); return this; }
    synchronized public IVolume clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    synchronized public IVolume clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    synchronized public IVolume clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    synchronized public IVolume clr(int gray){ super.clr(gray); return this; }
    synchronized public IVolume clr(float fgray){ super.clr(fgray); return this; }
    synchronized public IVolume clr(double dgray){ super.clr(dgray); return this; }
    synchronized public IVolume clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    synchronized public IVolume clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    synchronized public IVolume clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    synchronized public IVolume clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    synchronized public IVolume clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    synchronized public IVolume clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    synchronized public IVolume clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    synchronized public IVolume clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    synchronized public IVolume clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    synchronized public IVolume hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    synchronized public IVolume hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    synchronized public IVolume hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    synchronized public IVolume hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }

    synchronized public IVolume setColor(IColor c){ super.setColor(c); return this; }
    synchronized public IVolume setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public IVolume setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    synchronized public IVolume setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    synchronized public IVolume setColor(Color c){ super.setColor(c); return this; }
    synchronized public IVolume setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    synchronized public IVolume setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    synchronized public IVolume setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    synchronized public IVolume setColor(int gray){ super.setColor(gray); return this; }
    synchronized public IVolume setColor(float fgray){ super.setColor(fgray); return this; }
    synchronized public IVolume setColor(double dgray){ super.setColor(dgray); return this; }
    synchronized public IVolume setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    synchronized public IVolume setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    synchronized public IVolume setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    synchronized public IVolume setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    synchronized public IVolume setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    synchronized public IVolume setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    synchronized public IVolume setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    synchronized public IVolume setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    synchronized public IVolume setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    synchronized public IVolume setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public IVolume setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    synchronized public IVolume setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    synchronized public IVolume setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }

    synchronized public IVolume weight(double w){ super.weight(w); return this; }
    synchronized public IVolume weight(float w){ super.weight(w); return this; }

}
