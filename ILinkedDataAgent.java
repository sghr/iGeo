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

import java.util.ArrayList;

/**
   Agent class with a generic data with links to other data agents
   
   @author Satoru Sugihara
*/
public class ILinkedDataAgent<T extends IArithmeticVal<T,IDoubleI>> extends IDataAgent<T,IDoubleI>{

    public ArrayList<IDataAgent<T,IDoubleI>> links;
    public T nextData;

    public double fric = 0.5;
    
    public ILinkedDataAgent(){ super(); initLinkedDataAgent(); }
    public ILinkedDataAgent(T val){ super(val); initLinkedDataAgent(); }
    public ILinkedDataAgent(IVecI pos){ super(pos); initLinkedDataAgent(); }
    public ILinkedDataAgent(IVecI pos, T val){ super(pos,val); initLinkedDataAgent(); }
    //public ILinkedDataAgent(IObject parent){ super(parent); initLinkedDataAgent(); }
    //public ILinkedDataAgent(T val, IObject parent){ super(val,parent); initLinkedDataAgent(); }

    public void initLinkedDataAgent(){
      links = new ArrayList<IDataAgent<T,IDoubleI>>();
      if(data!=null){
	  nextData = data.dup();
      }
    }

    public ILinkedDataAgent fric(double f){ fric = f; return this; }

    public double fric(){ return fric; }
    
    public ILinkedDataAgent setData(T val){
      super.setData(val);
      nextData = data.dup();
      return this;
    }

    public ILinkedDataAgent connect(IDataAgent<T,IDoubleI> link){
      links.add(link);
      return this;
    }
    
    public void interact(ArrayList<IDynamics> agents){
      if(data==null || links.size()==0){ return; }
      nextData.zero();
      for(int i=0; i<links.size(); i++){
	  nextData.add(links.get(i).getData());
      }
      nextData.div(new IDouble(links.size()));
    }

    public void update(){
      if(data!=null){
          nextData.sub(data);
          nextData.mul(new IDouble(1.0-fric));
	  data.add(nextData);
      }
    }



    /**************************************
     * static method
     *************************************/
    
    public static <R extends IArithmeticVal<R,IDoubleI>> ILinkedDataAgent[] create(IMeshI m){
        ILinkedDataAgent<R>[] agents = new ILinkedDataAgent[m.vertexNum()];
	for(int i=0; i<m.vertexNum(); i++){
	    agents[i] = new ILinkedDataAgent<R>(m.vertex(i).pos(), (R)null);
	}
        for(int i=0; i<m.edgeNum(); i++){
            IVertex v1 = m.edge(i).vertex(0);
            IVertex v2 = m.edge(i).vertex(1);
            int idx1 = -1;
            int idx2 = -1;
            for(int j=0; j<m.vertexNum() && idx1<0; j++){
                 if(m.vertex(j)==v1) idx1 = j;
            }
            for(int j=0; j<m.vertexNum() && idx2<0; j++){
                if(m.vertex(j)==v2) idx2 = j;
            }
            if(idx1>=0 && idx2>=0){
                agents[idx1].connect(agents[idx2]);
                agents[idx2].connect(agents[idx1]);
            }
        }
        return agents;
    }
    

    /**************************************
     * methods of IObject
     *************************************/
    
    public ILinkedDataAgent name(String nm){ super.name(nm); return this; }
    public ILinkedDataAgent layer(ILayer l){ super.layer(l); return this; }
    
    public ILinkedDataAgent hide(){ super.hide(); return this; }
    public ILinkedDataAgent show(){ super.show(); return this; }
    
    public ILinkedDataAgent clr(IColor c){ super.clr(c); return this; }
    public ILinkedDataAgent clr(IColor c, int alpha){ super.clr(c,alpha); return this; }
    public ILinkedDataAgent clr(IColor c, float alpha){ super.clr(c,alpha); return this; }
    public ILinkedDataAgent clr(IColor c, double alpha){ super.clr(c,alpha); return this; }
    public ILinkedDataAgent clr(IObject o){ super.clr(o); return this; }
    //public ILinkedDataAgent clr(Color c){ super.clr(c); return this; }
    //public ILinkedDataAgent clr(Color c, int alpha){ super.clr(c,alpha); return this; }
    //public ILinkedDataAgent clr(Color c, float alpha){ super.clr(c,alpha); return this; }
    //public ILinkedDataAgent clr(Color c, double alpha){ super.clr(c,alpha); return this; }
    public ILinkedDataAgent clr(int gray){ super.clr(gray); return this; }
    public ILinkedDataAgent clr(float fgray){ super.clr(fgray); return this; }
    public ILinkedDataAgent clr(double dgray){ super.clr(dgray); return this; }
    public ILinkedDataAgent clr(int gray, int alpha){ super.clr(gray,alpha); return this; }
    public ILinkedDataAgent clr(float fgray, float falpha){ super.clr(fgray,falpha); return this; }
    public ILinkedDataAgent clr(double dgray, double dalpha){ super.clr(dgray,dalpha); return this; }
    public ILinkedDataAgent clr(int r, int g, int b){ super.clr(r,g,b); return this; }
    public ILinkedDataAgent clr(float fr, float fg, float fb){ super.clr(fr,fg,fb); return this; }
    public ILinkedDataAgent clr(double dr, double dg, double db){ super.clr(dr,dg,db); return this; }
    public ILinkedDataAgent clr(int r, int g, int b, int a){ super.clr(r,g,b,a); return this; }
    public ILinkedDataAgent clr(float fr, float fg, float fb, float fa){ super.clr(fr,fg,fb,fa); return this; }
    public ILinkedDataAgent clr(double dr, double dg, double db, double da){ super.clr(dr,dg,db,da); return this; }
    public ILinkedDataAgent hsb(float h, float s, float b, float a){ super.hsb(h,s,b,a); return this; }
    public ILinkedDataAgent hsb(double h, double s, double b, double a){ super.hsb(h,s,b,a); return this; }
    public ILinkedDataAgent hsb(float h, float s, float b){ super.hsb(h,s,b); return this; }
    public ILinkedDataAgent hsb(double h, double s, double b){ super.hsb(h,s,b); return this; }

    public ILinkedDataAgent weight(float w){ super.weight(w); return this; }
    public ILinkedDataAgent weight(double w){ super.weight(w); return this; }
    
    public ILinkedDataAgent setColor(IColor c){ super.setColor(c); return this; }
    public ILinkedDataAgent setColor(IColor c, int alpha){ super.setColor(c,alpha); return this; }
    public ILinkedDataAgent setColor(IColor c, float alpha){ super.setColor(c,alpha); return this; }
    public ILinkedDataAgent setColor(IColor c, double alpha){ super.setColor(c,alpha); return this; }
    //public ILinkedDataAgent setColor(Color c){ super.setColor(c); return this; }
    //public ILinkedDataAgent setColor(Color c, int alpha){ super.setColor(c,alpha); return this; }
    //public ILinkedDataAgent setColor(Color c, float alpha){ super.setColor(c,alpha); return this; }
    //public ILinkedDataAgent setColor(Color c, double alpha){ super.setColor(c,alpha); return this; }
    public ILinkedDataAgent setColor(int gray){ super.setColor(gray); return this; }
    public ILinkedDataAgent setColor(float fgray){ super.setColor(fgray); return this; }
    public ILinkedDataAgent setColor(double dgray){ super.setColor(dgray); return this; }
    public ILinkedDataAgent setColor(int gray, int alpha){ super.setColor(gray,alpha); return this; }
    public ILinkedDataAgent setColor(float fgray, float falpha){ super.setColor(fgray,falpha); return this; }
    public ILinkedDataAgent setColor(double dgray, double dalpha){ super.setColor(dgray,dalpha); return this; }
    public ILinkedDataAgent setColor(int r, int g, int b){ super.setColor(r,g,b); return this; }
    public ILinkedDataAgent setColor(float fr, float fg, float fb){ super.setColor(fr,fg,fb); return this; }
    public ILinkedDataAgent setColor(double dr, double dg, double db){ super.setColor(dr,dg,db); return this; }
    public ILinkedDataAgent setColor(int r, int g, int b, int a){ super.setColor(r,g,b,a); return this; }
    public ILinkedDataAgent setColor(float fr, float fg, float fb, float fa){ super.setColor(fr,fg,fb,fa); return this; }
    public ILinkedDataAgent setColor(double dr, double dg, double db, double da){ super.setColor(dr,dg,db,da); return this; }
    public ILinkedDataAgent setHSBColor(float h, float s, float b, float a){ super.setHSBColor(h,s,b,a); return this; }
    public ILinkedDataAgent setHSBColor(double h, double s, double b, double a){ super.setHSBColor(h,s,b,a); return this; }
    public ILinkedDataAgent setHSBColor(float h, float s, float b){ super.setHSBColor(h,s,b); return this; }
    public ILinkedDataAgent setHSBColor(double h, double s, double b){ super.setHSBColor(h,s,b); return this; }
    
}
