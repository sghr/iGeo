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

package igeo.gui;

import java.util.ArrayList;
//import java.awt.Color;

import igeo.*;

/**
   Graphic subobject class to draw a surface object by OpenGL.
   It contains ISurfaceGraphicFillGL and  ISurfaceGraphicWireframeGL inside.

   @author Satoru Sugihara
*/
public class IVolumeGraphicGL extends IGraphicObject{
    //public static float defaultColorRed = .5f; //1f;
    //public static float defaultColorGreen = .5f; //1f;
    //public static float defaultColorBlue = .5f; //1f;
    //public static float defaultColorAlpha = 1f;

    //public static float defaultShininess=0.3f; //0.5f; //1f; //5f; //1f; //0.1f; //0.5f; //1f;
    public ISurfaceGeo[] faces; // 6 faces of the volume

    public ISurfaceGraphicFillGL[] fills;
    public ISurfaceGraphicWireframeGL[] wireframes;

    public IVolumeGraphicGL(IVolume vol){
      super(vol);

      fills = new ISurfaceGraphicFillGL[6];
      wireframes = new ISurfaceGraphicWireframeGL[6];
      faces = createFaces(vol);

      // fill and wireframe are instantiated here but actual geometry initialization is done at the first draw by each object
      for(int i=0; i<faces.length; i++){
        wireframes[i] = new ISurfaceGraphicWireframeGL(vol, faces[i]);
        fills[i] = new ISurfaceGraphicFillGL(vol, faces[i]);
      }
    }

/*
    public IVolumeGraphicGL(IVolumeR srf){
      super(srf);
      // fill and wireframe are instantiated here but actual geometry initialization is done at the first draw by each object
      wireframe = new ISurfaceGraphicWireframeGL(srf);
      fill = new ISurfaceGraphicFillGL(srf);
    }
*/

    public IVolumeGraphicGL(IObject obj, IVolumeGeo vol){
      super(obj);



      fills = new ISurfaceGraphicFillGL[6];
      wireframes = new ISurfaceGraphicWireframeGL[6];
      faces = createFaces(vol);

      // fill and wireframe are instantiated here but actual geometry initialization is done at the first draw by each object
      for(int i=0; i<faces.length; i++){
        wireframes[i] = new ISurfaceGraphicWireframeGL(obj, faces[i]);
        fills[i] = new ISurfaceGraphicFillGL(obj, faces[i]);
      }
    }


    public ISurfaceGeo[] createFaces(IVolumeI vol){
      int un = vol.ucpNum();
      int vn = vol.vcpNum();
      int wn = vol.wcpNum();

      IVec[][] uvCps1 = new IVec[un][vn];
      IVec[][] uvCps2 = new IVec[un][vn];
      IVec[][] vwCps1 = new IVec[vn][wn];
      IVec[][] vwCps2 = new IVec[vn][wn];
      IVec[][] wuCps1 = new IVec[wn][un];
      IVec[][] wuCps2 = new IVec[wn][un];

      for(int i=0; i<un; i++){
        for(int j=0; j<vn; j++){
          uvCps1[i][j] = vol.cp(i,j,0).get();
          uvCps2[i][j] = vol.cp(i,j,wn-1).get();
        }
      }
      for(int i=0; i<vn; i++){
        for(int j=0; j<wn; j++){
          vwCps1[i][j] = vol.cp(0,i,j).get();
          vwCps2[i][j] = vol.cp(un-1,i,j).get();
        }
      }
      for(int i=0; i<wn; i++){
        for(int j=0; j<un; j++){
          wuCps1[i][j] = vol.cp(j,0,i).get();
          wuCps2[i][j] = vol.cp(j,vn-1,i).get();
        }
      }

      ISurfaceGeo[] fcs = new ISurfaceGeo[6];
      fcs[0] = new ISurfaceGeo(uvCps1, vol.udeg(), vol.vdeg(), vol.uknots(), vol.vknots(), vol.ustart(), vol.uend(), vol.vstart(), vol.vend());
      fcs[1] = new ISurfaceGeo(uvCps2, vol.udeg(), vol.vdeg(), vol.uknots(), vol.vknots(), vol.ustart(), vol.uend(), vol.vstart(), vol.vend());
      fcs[2] = new ISurfaceGeo(vwCps1, vol.vdeg(), vol.wdeg(), vol.vknots(), vol.wknots(), vol.vstart(), vol.vend(), vol.wstart(), vol.wend());
      fcs[3] = new ISurfaceGeo(vwCps2, vol.vdeg(), vol.wdeg(), vol.vknots(), vol.wknots(), vol.vstart(), vol.vend(), vol.wstart(), vol.wend());
      fcs[4] = new ISurfaceGeo(wuCps1, vol.wdeg(), vol.udeg(), vol.wknots(), vol.uknots(), vol.wstart(), vol.wend(), vol.ustart(), vol.uend());
      fcs[5] = new ISurfaceGeo(wuCps2, vol.wdeg(), vol.udeg(), vol.wknots(), vol.uknots(), vol.wstart(), vol.wend(), vol.ustart(), vol.uend());
      return fcs;
    }

    public void setAttribute(IAttribute attr){
      super.setAttribute(attr);
      if(fills!=null){ for(int i=0; i<fills.length; i++){ fills[i].setAttribute(attr); } }
      if(wireframes!=null){ for(int i=0; i<wireframes.length; i++){ wireframes[i].setAttribute(attr); } }
    }

    public void setColor(IColor c){
      super.setColor(c);
      if(fills!=null){ for(int i=0; i<fills.length; i++){ fills[i].setColor(c); } }
      if(wireframes!=null){ for(int i=0; i<wireframes.length; i++){ wireframes[i].setColor(c); } }
    }


    public void setWeight(float w){
      if(wireframes!=null){ for(int i=0; i<wireframes.length; i++){ wireframes[i].setWeight(w); } }
    }

    public float getWeight(){
      if(wireframes!=null) return wireframes[0].getWeight();
      return -1f;
    }

    @Override public void update(){
      if(fills!=null){ for(int i=0; i<fills.length; i++){ fills[i].update(); } }
      if(wireframes!=null){ for(int i=0; i<wireframes.length; i++){ wireframes[i].update(); } }
    }

    public boolean isDrawable(IGraphicMode m){ return m.isGL(); }

    public void draw(IGraphics g){

      // fill first or wireframe first?
      if(g.view().mode().isFill()){
        for(int i=0; i<fills.length; i++){ fills[i].draw(g); }
      }

      if(g.view().mode().isWireframe()){
        for(int i=0; i<wireframes.length; i++){ wireframes[i].draw(g); }
      }
    }



}
