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


package igeo;

import java.awt.Color;
import java.util.Random;

/**
   A class to provide random number function.   

   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IRand{
    static public final long defaultSeed=1;
    
    static protected Random random=null;
    
    static public void init(){
	init(defaultSeed);
    }
    static public void init(long seed){
	random = new Random(seed);
    }
    
    static public void initByTime(){
	random = new Random(System.currentTimeMillis());
    }
    
    static public double get(){
	if(random==null){ init(); }
	return random.nextDouble();
    }
    
    static public double getDouble(double min, double max){ return get(min,max); }
    static public double getDouble(double max){ return get(max); }
    static public double getDouble(){ return get(); }
    
    static public double get(double min, double max){
	if(random==null){ init(); }
	return random.nextDouble()*(max-min)+min;
    }
    
    static public double get(double max){
	if(random==null){ init(); }
	return random.nextDouble()*max;
    }

    static public float getf(){
	if(random==null){ init(); }
	return random.nextFloat();
    }
    static public float getf(float min, float max){
	if(random==null){ init(); }
	return random.nextFloat()*(max-min)+min;
    }
    static public float getf(float max){
	if(random==null){ init(); }
	return random.nextFloat()*max;
    }
    
    static public float getFloat(){ return getf(); }
    static public float getFloat(float min, float max){ return getf(min,max); }
    static public float getFloat(float max){ return getf(max); }
    
    
    static public int geti(int min, int max){
	int r = (int)get(min, max+1);
	if(r>max){ r=max; }
	return r;
    }
    static public int geti(int max){
	int r = (int)get(0, max+1);
	if(r>max){ r=max; }
	return r;
    }
    /** returns 0 or 1 */
    static public int geti(){
	int r = (int)get(0, 2);
	if(r>1){ r=1; }
	return r;
    }
    static public int getInt(int min, int max){ return geti(min,max); }
    static public int getInt(int max){ return geti(max); }
    static public int getInt(){ return geti(); }
    static public int getInteger(int min, int max){ return geti(min,max); }
    static public int getInteger(int max){ return geti(max); }
    static public int getInteger(){ return geti(); }
    
    
    static public IVec pt(IVec min, IVec max){
	return new IVec(get(min.x,max.x),get(min.y,max.y),get(min.z,max.z));
    }
    static public IVec point(IVec min, IVec max){ return pt(min,max); }
    static public IVec getPoint(IVec min, IVec max){ return pt(min,max); }

    static public IVec pt(IVec max){
	return new IVec(get(max.x),get(max.y),get(max.z));
    }
    static public IVec point(IVec max){ return pt(max); }
    static public IVec getPoint(IVec max){ return pt(max); }
    
    static public IVec pt(double minx, double miny, double minz,
			  double maxx, double maxy, double maxz){
	return new IVec(get(minx,maxx),get(miny,maxy),get(minz,maxz));
    }
    static public IVec pt(double maxx, double maxy, double maxz){
	return new IVec(get(maxx),get(maxy),get(maxz));
    }
    static public IVec pt(double min, double max){
	return new IVec(get(min,max),get(min,max),get(min,max));
    }
    static public IVec pt(double max){
	return new IVec(get(0,max),get(0,max),get(0,max));
    }
    static public IVec pt(){ return new IVec(get(),get(),get()); }
    
    static public IVec point(double minx, double miny, double minz,
			     double maxx, double maxy, double maxz){
	return pt(minx,miny,minz,maxx,maxy,maxz);
    }
    static public IVec point(double maxx, double maxy, double maxz){
	return pt(maxx,maxy,maxz);
    }
    static public IVec point(double min, double max){ return pt(min,max); }
    static public IVec point(double max){ return pt(max); }
    static public IVec point(){ return pt(); }
    
    static public IVec getPoint(double minx, double miny, double minz,
				double maxx, double maxy, double maxz){
	return pt(minx,miny,minz,maxx,maxy,maxz);
    }
    static public IVec getPoint(double maxx, double maxy, double maxz){
	return pt(maxx,maxy,maxz);
    }
    static public IVec getPoint(double min, double max){ return pt(min,max); }
    static public IVec getPoint(double max){ return pt(max); }
    static public IVec getPoint(){ return pt(); }
    
    
    static public boolean getPercent(double percent){
	return percent(percent);
    }
    
    static public boolean percent(double percent){
	return get()<(percent/100);
    }
    
    //static public Color color(){ return getColor(); } // PDE doesn't allow this name
    static public Color getColor(){ return clr(); }
    static public Color clr(){
	return new Color(IRandom.getInt(0,255),
			 IRandom.getInt(0,255),
			 IRandom.getInt(0,255));
    }
    
    //static public Color color(int alpha){ getColor(alpha); } // PDE doesn't allow this name
    static public Color getColor(int alpha){ return clr(alpha); }
    static public Color clr(int alpha){
	return new Color(IRandom.getInt(0,255),
			 IRandom.getInt(0,255),
			 IRandom.getInt(0,255),
			 alpha);
    }
    static public Color getColor(float alpha){ return clr(alpha); }
    static public Color clr(float alpha){
	return new Color(IRandom.getFloat(),
			 IRandom.getFloat(),
			 IRandom.getFloat(),
			 alpha);
    }
    
    //static public Color gray(){ return getGray(); }
    static public Color getGrayColor(){ return gray(); }
    static public Color getGray(){ return gray(); }
    static public Color gray(){
	int gray = IRandom.getInt(0,255);
	return new Color(gray,gray,gray);
    }
    //static public Color gray(int alpha){ return getGray(alpha); }
    static public Color getGrayColor(int alpha){ return gray(alpha); }
    static public Color getGray(int alpha){ return gray(alpha); }
    static public Color gray(int alpha){
	int gray = IRandom.getInt(0,255);
	return new Color(gray,gray,gray,alpha);
    }
    static public Color getGrayColor(float alpha){ return gray(alpha); }
    static public Color getGray(float alpha){ return gray(alpha); }
    static public Color gray(float alpha){
	float gray = IRandom.getFloat();
	return new Color(gray,gray,gray,alpha);
    }
}



