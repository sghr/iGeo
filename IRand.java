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

//import java.awt.Color;
import java.util.Random;
import java.util.List;

/**
   A class to provide random number function.   

   @author Satoru Sugihara
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
	if(max>min){
	    int r = (int)get(min, max+1);
	    if(r>max){ r=max; } 
	    return r;
	}
	int r = (int)get(max, min+1);
	if(r>min){ r=min; } 
	return r;
    }
    static public int geti(int max){ return geti(0,max); }
    
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
    
    /** get one element out of array */
    static public <T> T get(T[] array){
	if(array==null) return null;
	return array[geti(0,array.length-1)];
    }
    
    /** get one element out of array */
    static public <T> T get(List<T> array){
	if(array==null) return null;
	return array.get(geti(0,array.size()-1));
    }
    
    
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
    static public IVec pt(double minx, double miny, double maxx, double maxy){
	return new IVec(get(minx,maxx),get(miny,maxy),0);
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
    static public IVec point(double minx, double miny, double maxx, double maxy){
	return pt(minx,miny,maxx,maxy);
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
    static public IVec getPoint(double minx, double miny, double maxx, double maxy){
	return pt(minx,miny,maxx,maxy);
    }
    static public IVec getPoint(double maxx, double maxy, double maxz){
	return pt(maxx,maxy,maxz);
    }
    static public IVec getPoint(double min, double max){ return pt(min,max); }
    static public IVec getPoint(double max){ return pt(max); }
    static public IVec getPoint(){ return pt(); }
    
    
    /**
       random point on XY plane
    */
    static public IVec pt2(double minx, double miny, double maxx, double maxy){
	return new IVec(get(minx,maxx),get(miny,maxy),0);
    }
    static public IVec pt2(double min, double max){
	return new IVec(get(min,max),get(min,max),0);
    }
    static public IVec pt2(double max){ return new IVec(get(0,max),get(0,max),0); }
    static public IVec pt2(){ return new IVec(get(),get(),0); }
    
    static public IVec point2(double minx, double miny, double maxx, double maxy){
	return pt2(minx,miny,maxx,maxy);
    }
    static public IVec point2(double min, double max){ return pt2(min,max); }
    static public IVec point2(double max){ return pt2(max); }
    static public IVec point2(){ return pt2(); }    
    
    static public IVec getPoint2(double minx, double miny, double maxx, double maxy){
	return pt2(minx,miny,maxx,maxy);
    }
    static public IVec getPoint2(double min, double max){ return pt2(min,max); }
    static public IVec getPoint2(double max){ return pt2(max); }
    static public IVec getPoint2(){ return pt2(); }    
    
    
    /**
       random direction in 3D
    */
    static public IVec dir(){ return dir(1.0); }
    static public IVec dir(double length){
	IVec v = new IVec(length,0,0);
	v.rot(IG.yaxis, get(-Math.PI,Math.PI));
	v.rot(IG.zaxis, get(0, 2*Math.PI));
	return v;
	//IVec vec = pt(-1,1);
	//double l = vec.len();
	//while(l < IConfig.tolerance){ vec = pt(-1,1); l = vec.len(); }
	//return vec.mul(length/l);
    }
    static public IVec dir(IVecI perpendicularAxis){
	return dir(perpendicularAxis, 1.0);
    }
    static public IVec dir(IVecI perpendicularAxis, double length){
	IVec vec = null;
	if(!IVec.zaxis.isParallel(perpendicularAxis))
	    vec = IVec.zaxis.cross(perpendicularAxis);
	else vec = IVec.xaxis.cross(perpendicularAxis);
	return vec.rot(perpendicularAxis, get(Math.PI*2)).len(length);
    }
    static public IVec dir(IVecI axis, double length, double angleRange){
	IVec ax2 = null;
	if(!IVec.zaxis.isParallel(axis))
	    ax2 = IVec.zaxis.cross(axis);
	else ax2 = IVec.xaxis.cross(axis);
	IVec v = axis.get().cp().len(length).rot(ax2, get(angleRange));
	return v.rot(axis, get(Math.PI*2));
    }
    
    static public IVec direction(){ return dir(); }
    static public IVec direction(double length){ return dir(length); }
    static public IVec direction(IVecI perpendicularAxis){
	return dir(perpendicularAxis);
    }
    static public IVec direction(IVecI perpendicularAxis, double length){
	return dir(perpendicularAxis,length);
    }
    static public IVec direction(IVecI axis, double length, double angleRange){
	return dir(axis,length,angleRange);
    }
    
    static public IVec getDirection(){ return dir(); }
    static public IVec getDirection(double length){ return dir(length); }
    static public IVec getDirection(IVecI perpendicularAxis){
	return dir(perpendicularAxis);
    }
    static public IVec getDirection(IVecI perpendicularAxis, double length){
	return dir(perpendicularAxis,length);
    }
    static public IVec getDirection(IVecI axis, double length, double angleRange){
	return dir(axis,length,angleRange);
    }
    
    
    /**
       random direction on XY plane
    */
    static public IVec dir2(){ return dir2(1.0); }
    static public IVec dir2(double length){
	double a = get(Math.PI*2);
	return new IVec(length*Math.cos(a), length*Math.sin(a), 0);
    }
    static public IVec dir2(IVecI axis, double length, double angleRange){
	return axis.get().cp().len(length).rot(IG.zaxis, get(-angleRange, angleRange));
    }
    
    static public IVec direction2(){ return dir2(); }
    static public IVec direction2(double length){ return dir2(length); }
    static public IVec direction2(IVecI axis, double length, double angleRange){
	return dir2(axis,length,angleRange);
    }
    static public IVec getDirection2(){ return dir2(); }
    static public IVec getDirection2(double length){ return dir2(length); }
    static public IVec getDirection2(IVecI axis, double length, double angleRange){
	return dir2(axis,length,angleRange);
    }
    
    
    static public boolean getPercent(double percent){
	return percent(percent);
    }
    static public boolean percent(double percent){
	if(percent>=100) return true;
	if(percent<=0) return false;
	return get()<(percent/100);
    }
    static public boolean pct(double percent){return percent(percent); }
    
    
    //static public IColor color(){ return getColor(); } // PDE doesn't allow this name
    static public IColor getColor(){ return clr(); }
    static public IColor clr(){
	return new IColor(getInt(0,255),getInt(0,255),getInt(0,255));
    }
    
    static protected int limit255(int value){
	if(value<0) return 0;
	if(value>255) return 255;
	return value;
    }
    static protected float limit1(float value){
	if(value<0f) return 0f;
	if(value>1f) return 1f;
	return value;
    }
    
    //static public IColor color(int alpha){ getColor(alpha); } // PDE doesn't allow this name
    static public IColor getColor(int alpha){ return clr(alpha); }
    static public IColor clr(int alpha){
	return new IColor(getInt(0,255),getInt(0,255),getInt(0,255),limit255(alpha));
    }
    static public IColor getColor(int minRed, int maxRed,
				  int minGreen, int maxGreen,
				  int minBlue, int maxBlue,
				  int minAlpha, int maxAlpha){
	return clr(minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue,minAlpha,maxAlpha);
    }
    static public IColor clr(int minRed, int maxRed,
			     int minGreen, int maxGreen,
			     int minBlue, int maxBlue,
			     int minAlpha, int maxAlpha){
	return new IColor(limit255(getInt(minRed, maxRed)),
			  limit255(getInt(minGreen, maxGreen)),
			  limit255(getInt(minBlue, maxBlue)),
			  limit255(getInt(minAlpha, maxAlpha)));
    }
    
    /** add random numbers to each component of color */
    static public IColor getColor(IColor c, 
				  int minRed, int maxRed,
				  int minGreen, int maxGreen,
				  int minBlue, int maxBlue,
				  int minAlpha, int maxAlpha){
	return clr(c,minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue,minAlpha,maxAlpha);
    }
    /** add random numbers to each component of color */
    static public IColor clr(IColor c, 
			     int minRed, int maxRed,
			     int minGreen, int maxGreen,
			     int minBlue, int maxBlue,
			     int minAlpha, int maxAlpha){
	return new IColor(limit255(c.getRed()+getInt(minRed, maxRed)),
			  limit255(c.getGreen()+getInt(minGreen, maxGreen)),
			  limit255(c.getBlue()+getInt(minBlue, maxBlue)),
			  limit255(c.getAlpha()+getInt(minAlpha, maxAlpha)));
    }
    
    static public IColor getColor(int minRed, int maxRed,
				  int minGreen, int maxGreen,
				  int minBlue, int maxBlue,
				  int alpha){
	return clr(minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue,alpha);
    }
    static public IColor clr(int minRed, int maxRed,
			     int minGreen, int maxGreen,
			     int minBlue, int maxBlue,
			     int alpha){
	return new IColor(limit255(getInt(minRed, maxRed)),
			  limit255(getInt(minGreen, maxGreen)),
			  limit255(getInt(minBlue, maxBlue)),
			  limit255(alpha));
    }
    
    /** add random numbers to each component of color */
    static public IColor getColor(IColor c, int minRed, int maxRed,
				  int minGreen, int maxGreen,
				  int minBlue, int maxBlue,
				  int alpha){
	return clr(c,minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue,alpha);
    }
    /** add random numbers to each component of color */
    static public IColor clr(IColor c, int minRed, int maxRed,
			     int minGreen, int maxGreen,
			     int minBlue, int maxBlue,
			     int alpha){
	return new IColor(limit255(c.getRed()+getInt(minRed, maxRed)),
			  limit255(c.getGreen()+getInt(minGreen, maxGreen)),
			  limit255(c.getBlue()+getInt(minBlue, maxBlue)),
			  limit255(alpha));
    }


    static public IColor getColor(int minRed, int maxRed,
				  int minGreen, int maxGreen,
				  int minBlue, int maxBlue){
	return clr(minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue);
    }
    static public IColor clr(int minRed, int maxRed,
			     int minGreen, int maxGreen,
			     int minBlue, int maxBlue){
	return new IColor(limit255(getInt(minRed, maxRed)),
			  limit255(getInt(minGreen, maxGreen)),
			  limit255(getInt(minBlue, maxBlue)));
    }
    
    /** add random numbers to each component of color */
    static public IColor getColor(IColor c, int minRed, int maxRed,
				  int minGreen, int maxGreen,
				  int minBlue, int maxBlue){
	return clr(c,minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue);
    }
    /** add random numbers to each component of color */
    static public IColor clr(IColor c, int minRed, int maxRed,
			     int minGreen, int maxGreen,
			     int minBlue, int maxBlue){
	return new IColor(limit255(c.getRed()+getInt(minRed, maxRed)),
			  limit255(c.getGreen()+getInt(minGreen, maxGreen)),
			  limit255(c.getBlue()+getInt(minBlue, maxBlue)));
    }
    
    
    static public IColor getColor(int maxRed, int maxGreen, int maxBlue, int alpha){
	return clr(maxRed,maxGreen,maxBlue,alpha);
    }
    static public IColor clr(int maxRed, int maxGreen, int maxBlue,
			     int alpha){
	return new IColor(limit255(getInt(maxRed)),limit255(getInt(maxGreen)),limit255(getInt(maxBlue)),
			 limit255(alpha));
    }

    /** add random numbers to each component of color */
    static public IColor getColor(IColor c, int maxRed, int maxGreen, int maxBlue, int alpha){
	return clr(c,maxRed,maxGreen,maxBlue,alpha);
    }
    /** add random numbers to each component of color */
    static public IColor clr(IColor c, int maxRed, int maxGreen, int maxBlue,
			     int alpha){
	return new IColor(limit255(c.getRed()+getInt(maxRed)),
			  limit255(c.getGreen()+getInt(maxGreen)),
			  limit255(c.getBlue()+getInt(maxBlue)),
			  limit255(alpha));
    }
    
    static public IColor getColor(int maxRed, int maxGreen, int maxBlue){
	return clr(maxRed,maxGreen,maxBlue);
    }
    static public IColor clr(int maxRed, int maxGreen, int maxBlue){
	return new IColor(limit255(getInt(maxRed)),limit255(getInt(maxGreen)),limit255(getInt(maxBlue)));
    }
    /** add random numbers to each component of color */
    static public IColor getColor(IColor c, int maxRed, int maxGreen, int maxBlue){
	return clr(c,maxRed,maxGreen,maxBlue);
    }
    /** add random numbers to each component of color */
    static public IColor clr(IColor c, int maxRed, int maxGreen, int maxBlue){
	return new IColor(limit255(c.getRed()+getInt(maxRed)),
			  limit255(c.getGreen()+getInt(maxGreen)),
			  limit255(c.getBlue()+getInt(maxBlue)));
    }
    
    static public IColor getColor(float alpha){ return clr(alpha); }
    static public IColor clr(float alpha){
	return new IColor(getFloat(),getFloat(),getFloat(),limit1(alpha));
    }
    
    
    static public IColor getColor(float minRed, float maxRed,
				  float minGreen, float maxGreen,
				  float minBlue, float maxBlue,
				  float minAlpha, float maxAlpha){
	return clr(minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue,minAlpha,maxAlpha);
    }
    static public IColor clr(float minRed, float maxRed,
			     float minGreen, float maxGreen,
			     float minBlue, float maxBlue,
			     float minAlpha, float maxAlpha){
	return new IColor(limit1(getFloat(minRed, maxRed)),
			  limit1(getFloat(minGreen, maxGreen)),
			  limit1(getFloat(minBlue, maxBlue)),
			  limit1(getFloat(minAlpha, maxAlpha)));
    }
    /** add random numbers to each component of color */
    static public IColor getColor(IColor c, float minRed, float maxRed,
				  float minGreen, float maxGreen,
				  float minBlue, float maxBlue,
				  float minAlpha, float maxAlpha){
	return clr(c,minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue,minAlpha,maxAlpha);
    }
    /** add random numbers to each component of color */
    static public IColor clr(IColor c, float minRed, float maxRed,
			     float minGreen, float maxGreen,
			     float minBlue, float maxBlue,
			     float minAlpha, float maxAlpha){
	return new IColor(limit1((float)c.getRed()/255f+getFloat(minRed, maxRed)),
			  limit1((float)c.getGreen()/255f+getFloat(minGreen, maxGreen)),
			  limit1((float)c.getBlue()/255f+getFloat(minBlue, maxBlue)),
			  limit1((float)c.getAlpha()/255f+getFloat(minAlpha, maxAlpha)));
    }
    
    static public IColor getColor(float minRed, float maxRed,
				  float minGreen, float maxGreen,
				  float minBlue, float maxBlue,
				  float alpha){
	return clr(minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue,alpha);
    }
    static public IColor clr(float minRed, float maxRed,
			     float minGreen, float maxGreen,
			     float minBlue, float maxBlue,
			     float alpha){
	return new IColor(limit1(getFloat(minRed, maxRed)),
			  limit1(getFloat(minGreen, maxGreen)),
			  limit1(getFloat(minBlue, maxBlue)),
			  limit1(alpha));
    }
    /** add random numbers to each component of color */
    static public IColor getColor(IColor c, float minRed, float maxRed,
				  float minGreen, float maxGreen,
				  float minBlue, float maxBlue,
				  float alpha){
	return clr(c,minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue,alpha);
    }
    /** add random numbers to each component of color */
    static public IColor clr(IColor c, float minRed, float maxRed,
			     float minGreen, float maxGreen,
			     float minBlue, float maxBlue,
			     float alpha){
	return new IColor(limit1((float)c.getRed()/255f+getFloat(minRed, maxRed)),
			  limit1((float)c.getGreen()/255f+getFloat(minGreen, maxGreen)),
			  limit1((float)c.getBlue()/255f+getFloat(minBlue, maxBlue)),
			  limit1(alpha));
    }
    
    static public IColor getColor(float minRed, float maxRed,
				  float minGreen, float maxGreen,
				  float minBlue, float maxBlue){
	return clr(minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue);
    }
    static public IColor clr(float minRed, float maxRed,
			     float minGreen, float maxGreen,
			     float minBlue, float maxBlue){
	return new IColor(limit1(getFloat(minRed, maxRed)),
			  limit1(getFloat(minGreen, maxGreen)),
			  limit1(getFloat(minBlue, maxBlue)));
    }
    /** add random numbers to each component of color */
    static public IColor getColor(IColor c, float minRed, float maxRed,
				  float minGreen, float maxGreen,
				  float minBlue, float maxBlue){
	return clr(c,minRed,maxRed,minGreen,maxGreen,minBlue,maxBlue);
    }
    /** add random numbers to each component of color */
    static public IColor clr(IColor c, float minRed, float maxRed,
			    float minGreen, float maxGreen,
			    float minBlue, float maxBlue){
	return new IColor(limit1((float)c.getRed()/255f+getFloat(minRed, maxRed)),
			  limit1((float)c.getGreen()/255f+getFloat(minGreen, maxGreen)),
			  limit1((float)c.getBlue()/255f+getFloat(minBlue, maxBlue)));
    }
    
    static public IColor getColor(float maxRed,float maxGreen,float maxBlue,float alpha){
	return clr(maxRed,maxGreen,maxBlue,alpha);
    }
    static public IColor clr(float maxRed, float maxGreen, float maxBlue,
			     float alpha){
	return new IColor(limit1(getFloat(maxRed)),limit1(getFloat(maxGreen)),limit1(getFloat(maxBlue)),
			  limit1(alpha));
    }
    /** add random numbers to each component of color */
    static public IColor getColor(IColor c, float maxRed,float maxGreen,float maxBlue,float alpha){
	return clr(c,maxRed,maxGreen,maxBlue,alpha);
    }
    /** add random numbers to each component of color */
    static public IColor clr(IColor c, float maxRed, float maxGreen, float maxBlue,
			    float alpha){
	return new IColor(limit1((float)c.getRed()/255f+getFloat(maxRed)),
			 limit1((float)c.getGreen()/255f+getFloat(maxGreen)),
			 limit1((float)c.getBlue()/255f+getFloat(maxBlue)),
			 limit1(alpha));
    }
    
    static public IColor getIColor(float maxRed,float maxGreen,float maxBlue){
	return clr(maxRed,maxGreen,maxBlue);
    }
    static public IColor clr(float maxRed, float maxGreen, float maxBlue){
	return new IColor(limit1(getFloat(maxRed)),limit1(getFloat(maxGreen)),limit1(getFloat(maxBlue)));
    }
    /** add random numbers to each component of color */
    static public IColor getIColor(IColor c, float maxRed,float maxGreen,float maxBlue){
	return clr(c,maxRed,maxGreen,maxBlue);
    }
    /** add random numbers to each component of color */
    static public IColor clr(IColor c, float maxRed, float maxGreen, float maxBlue){
	return new IColor(limit1((float)c.getRed()/255f+getFloat(maxRed)),
			 limit1((float)c.getGreen()/255f+getFloat(maxGreen)),
			 limit1((float)c.getBlue()/255f+getFloat(maxBlue)));
    }
    
    
    //static public IColor gray(){ return getGray(); }
    static public IColor getGrayIColor(){ return gray(); }
    static public IColor getGray(){ return gray(); }
    static public IColor gray(){
	int gray = getInt(0,255);
	return new IColor(gray,gray,gray);
    }
    //static public IColor gray(int alpha){ return getGray(alpha); }
    static public IColor getGrayIColor(int alpha){ return gray(alpha); }
    static public IColor getGray(int alpha){ return gray(alpha); }
    static public IColor gray(int alpha){
	int gray = getInt(0,255);
	return new IColor(gray,gray,gray,limit255(alpha));
    }
    static public IColor getGrayIColor(float alpha){ return gray(alpha); }
    static public IColor getGray(float alpha){ return gray(alpha); }
    static public IColor gray(float alpha){
	float gray = getFloat();
	return new IColor(gray,gray,gray,limit1(alpha));
    }

    static public IColor getGrayIColor(float minGray, float maxGray, float minAlpha, float maxAlpha){
	return gray(minGray,maxGray,minAlpha,maxAlpha);
    }
    static public IColor getGray(float minGray, float maxGray, float minAlpha, float maxAlpha){
	return gray(minGray,maxGray,minAlpha,maxAlpha);
    }
    static public IColor gray(float minGray, float maxGray, float minAlpha, float maxAlpha){
	float gray = limit1(getFloat(minGray,maxGray));
	return new IColor(gray,gray,gray,limit1(getFloat(minAlpha,maxAlpha)));
    }
    
    static public IColor getGrayIColor(float minGray, float maxGray, float alpha){
	return gray(minGray,maxGray,alpha);
    }
    static public IColor getGray(float minGray, float maxGray, float alpha){
	return gray(minGray,maxGray,alpha);
    }
    static public IColor gray(float minGray, float maxGray, float alpha){
	float gray = limit1(getFloat(minGray,maxGray));
	return new IColor(gray,gray,gray,limit1(alpha));
    }
    
    
    static public IColor getGrayIColor(float minGray, float maxGray){
	return gray(minGray,maxGray);
    }
    static public IColor getGray(float minGray, float maxGray){
	return gray(minGray,maxGray);
    }
    static public IColor gray(float minGray, float maxGray){
	float gray = limit1(getFloat(minGray,maxGray));
	return new IColor(gray,gray,gray);
    }
    
    
}



