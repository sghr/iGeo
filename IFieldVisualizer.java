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
import java.awt.Color;

/**
   Visualize field with xyz grid of arrows (IVectorObject)
   
   @author Satoru Sugihara
*/

public class IFieldVisualizer extends IAgent{
    public static int defaultNum = 10;
    public static IColor defaultMinColor = new IColor(0,128,255); //Color.green;
    public static IColor defaultMaxColor = new IColor(255,0,0);
    
    /** corner of grid box */
    public IVec min, max;
    public int xnum,ynum,znum;
    public IVectorObject[][][] arrows;
    public IVec[][][] vectors;
    
    public float arrowSize=IConfig.arrowSize;
    public float arrowWeight=IConfig.strokeWeight;
    
    public boolean autoSizeAdjust=true;
    public float arrowSizeRatio=0.5f; //0.333f; //0.25f;
    
    /** scale of vector length */
    public double scale = 1.0;
    public boolean fixLength=true; //false;
    public double fixedLength=10;
    public boolean autoLengthAdjust=true;
    
    public static double minAutoLengthRatio=0.05;
    
    /** for coloring vector by length */
    public double minLength=0, maxLength=10;
    public IColor minColor=defaultMinColor, maxColor=defaultMaxColor;
    /** adjust minLength/maxLength relative to existing lengths */
    public boolean autoColorAdjust=true;
    
    public double spacing;
    
    // alpha color 0 - 1
    public float alpha = 1f; 
    
    public boolean fieldSet=false;
    public boolean updateAlways=false;
    
    
    public IFieldVisualizer(IVec minCorner, IVec maxCorner){
	min = minCorner; max = maxCorner;
	if(minCorner.eqX(maxCorner)) xnum=1; else xnum = defaultNum; 
	if(minCorner.eqY(maxCorner)) ynum=1; else ynum = defaultNum;
	if(minCorner.eqZ(maxCorner)) znum=1; else znum = defaultNum;
	initVisualizer();
    }
    
    public IFieldVisualizer(IVec minCorner, IVec maxCorner, int xSampleNum, int ySampleNum, int zSampleNum){
	min = minCorner; max = maxCorner;
	xnum = xSampleNum;
	ynum = ySampleNum;
	znum = zSampleNum;
	initVisualizer();
    }
    
    public IFieldVisualizer(IVec corner, double xwidth, double yheight, double zdepth){
	this(corner, corner.cp(xwidth,yheight,zdepth));
    }
    
    public IFieldVisualizer(IVec corner, double xwidth, double yheight, double zdepth, int xSampleNum, int ySampleNum, int zSampleNum){
	this(corner, corner.cp(xwidth,yheight,zdepth), xSampleNum, ySampleNum, zSampleNum);
    }
    
    public IFieldVisualizer(double minx, double miny, double minz, double maxx, double maxy, double maxz){
	this(new IVec(minx,miny,minz), new IVec(maxx,maxy,maxz)); 
    }
    
    public IFieldVisualizer(double minx, double miny, double minz,
			    double maxx, double maxy, double maxz,
			    int xSampleNum, int ySampleNum, int zSampleNum){
	this(new IVec(minx,miny,minz), new IVec(maxx,maxy,maxz),xSampleNum,ySampleNum,zSampleNum);
    }
    
    public IFieldVisualizer(IVec corner, double xwidth, double yheight){
	this(corner,xwidth,yheight,0);
    }
    
    public IFieldVisualizer(IVec corner, double xwidth, double yheight, int xSampleNum, int ySampleNum){
	this(corner, xwidth, yheight,0,xSampleNum,ySampleNum,1);
    }
    
    public IFieldVisualizer(double minx, double miny, double maxx, double maxy){
	this(new IVec(minx,miny,0), new IVec(maxx,maxy,0));
    }
    
    public IFieldVisualizer(double minxyz, double maxxyz){
	this(new IVec(minxyz,minxyz,minxyz), new IVec(maxxyz,maxxyz,maxxyz));
    }
    
    /*
    public IFieldVisualizer(double minx, double miny,
			    double maxx, double maxy,
			    int xSampleNum, int ySampleNum){ // conflict with this(double,double,double,double,double,double)
	this(new IVec(minx,miny,0), new IVec(maxx,maxy,0),xSampleNum,ySampleNum,1);
    }
    */
    
    
    public IFieldVisualizer colorRange(int minRed, int minGreen, int minBlue,
				       int maxRed, int maxGreen, int maxBlue){
	return colorRange(new IColor(minRed,minGreen,minBlue),
			  new IColor(maxRed,maxGreen,maxBlue));
    }
    
    public IFieldVisualizer colorRange(int minRed, int minGreen, int minBlue, double minLength,
				       int maxRed, int maxGreen, int maxBlue, double maxLength){
	return colorRange(new IColor(minRed,minGreen,minBlue), minLength,
			  new IColor(maxRed,maxGreen,maxBlue), maxLength);
    }
    
    public IFieldVisualizer colorRange(float minRed, float minGreen, float minBlue,
				       float maxRed, float maxGreen, float maxBlue){
	return colorRange(new IColor(minRed,minGreen,minBlue),
			  new IColor(maxRed,maxGreen,maxBlue));
    }
    
    public IFieldVisualizer colorRange(float minRed, float minGreen, float minBlue, double minLength,
				       float maxRed, float maxGreen, float maxBlue, double maxLength){
	return colorRange(new IColor(minRed,minGreen,minBlue), minLength,
			  new IColor(maxRed,maxGreen,maxBlue), maxLength);
    }
    
    public IFieldVisualizer colorRange(Color minColor, double minLength,
				       Color maxColor, double maxLength){
	return colorRange(new IColor(minColor), minLength, new IColor(maxColor), maxLength);
    }
    
    public IFieldVisualizer colorRange(IColor minColor, double minLength,
				       IColor maxColor, double maxLength){
	this.minColor = minColor;
	minLength = minLength;
	this.maxColor = maxColor;
	maxLength = maxLength;
	autoColorAdjust=false;
	
	if(alpha!=1f){
	    minColor = new IColor(minColor.getRed(), minColor.getGreen(), minColor.getBlue(),
				 (int)(alpha*255));
	    maxColor = new IColor(maxColor.getRed(), maxColor.getGreen(), maxColor.getBlue(),
				  (int)(alpha*255));
	}
	
	return this;
    }
    
    public IFieldVisualizer colorRange(Color minColor, Color maxColor){
	return colorRange(new IColor(minColor), new IColor(maxColor));
    }
    public IFieldVisualizer colorRange(IColor minClr, IColor maxClr){
	minColor = minClr;
	maxColor = maxClr;
	autoColorAdjust=true;
	if(alpha!=1f){
	    minColor = new IColor(minColor.getRed(), minColor.getGreen(), minColor.getBlue(),
				  (int)(alpha*255));
	    maxColor = new IColor(maxColor.getRed(), maxColor.getGreen(), maxColor.getBlue(),
				  (int)(alpha*255));
	}
	return this;
    }
    
    public IFieldVisualizer alpha(float a){
	if(a<0f) alpha=0f; else if(a>1f) alpha=1f; else alpha = a;
	
	minColor = new IColor(minColor.getRed(), minColor.getGreen(), minColor.getBlue(),
			      (int)(alpha*255));
	maxColor = new IColor(maxColor.getRed(), maxColor.getGreen(), maxColor.getBlue(),
			      (int)(alpha*255));
	
	return this;
    }
    
    public IFieldVisualizer alpha(int a){ return alpha((float)a/255); }
    
    
    public IColor minColor(){ return minColor; }
    public IColor maxColor(){ return maxColor; }
    public double minLength(){ return minLength; }
    public double maxLength(){ return maxLength; }
    
    public IFieldVisualizer adjustColorAuto(boolean f){ autoColorAdjust=f; return this; }
    public IFieldVisualizer adjustLengthAuto(boolean f){ autoLengthAdjust=f; return this; }
    
    public IFieldVisualizer adjustColorAutomatically(boolean f){ return adjustColorAuto(f); }
    public IFieldVisualizer adjustLengthAutomatically(boolean f){ return adjustLengthAuto(f); }
    
    public IFieldVisualizer adjustSizeAuto(boolean f){ autoSizeAdjust=f; if(f)scale=1;/*!?*/return this; }
    public IFieldVisualizer adjustSizeAutomatically(boolean f){ return adjustSizeAuto(f); }
    public IFieldVisualizer adjustSizeAuto(float ratio){ autoSizeAdjust=true; arrowSizeRatio=ratio; return this; }
    public IFieldVisualizer adjustSizeAutomatically(float ratio){ return adjustSizeAuto(ratio); }
    
    public IFieldVisualizer arrowSizeRatio(float f){ arrowSizeRatio=f; return this; }
    
    public IFieldVisualizer fixLength(double len){ fixedLength = len; fixLength=true; return this; }
    public IFieldVisualizer fixLength(){ fixLength=true; return this; }
    public IFieldVisualizer fixLength(boolean f){ fixLength=f; return this; }
    
    
    /** set vector length scale relative to the length of force */
    public IFieldVisualizer scale(double sc){ scale = sc; return this; }
    public IFieldVisualizer scaleLength(double sc){ return scale(sc); }
    public double scale(){ return scale; }
    public double scaleLength(){ return scale(); }
    
    
    public IFieldVisualizer arrowSize(float sz){ return size(sz); }
    public IFieldVisualizer size(float sz){
	arrowSize=sz; 
	if(arrows!=null){
	    for(int i=0; i<arrows.length; i++){
		for(int j=0; j<arrows[i].length; j++){
		    for(int k=0; k<arrows[i][j].length; k++){
			arrows[i][j][k].size(arrowSize);
		    }
		}
	    }
	}
	return this;
    }
    
    
    public IFieldVisualizer arrowWeight(float w){ return weight(w); }
    public IFieldVisualizer weight(float w){
	arrowWeight=w;
	if(arrows!=null){
	    for(int i=0; i<arrows.length; i++){
		for(int j=0; j<arrows[i].length; j++){
		    for(int k=0; k<arrows[i][j].length; k++){
			arrows[i][j][k].weight(arrowWeight);
		    }
		}
	    }
	}
	return this;
    }
    
    public IFieldVisualizer updateAlways(boolean f){ updateAlways=f; return this; }
    
    
    synchronized public void initVisualizer(){
	
	if(xnum < 1) xnum=1;
	if(ynum < 1) ynum=1;
	if(znum < 1) znum=1;
	
	
	if(arrows!=null){
	    for(int i=0; i<arrows.length; i++){
		for(int j=0; j<arrows[i].length; j++){
		    for(int k=0; k<arrows[i][j].length; k++){
			arrows[i][j][k].del();
			arrows[i][j][k] = null;
		    }
		}
	    }
	}
	
	arrows = new IVectorObject[xnum][ynum][znum];
	vectors = new IVec[xnum][ynum][znum];
	for(int i=0; i<arrows.length; i++){
	    for(int j=0; j<arrows[i].length; j++){
		for(int k=0; k<arrows[i][j].length; k++){
		    IVec pos = gridPos(i,j,k);
		    arrows[i][j][k] = new IVectorObject(server(), new IVec(), pos);
		    arrows[i][j][k].size(arrowSize);
		    arrows[i][j][k].weight(arrowWeight);
		    vectors[i][j][k] = new IVec();
		}
	    }
	}
	
	// set minimum spacing on the grid
	spacing=-1;
	if(xnum>=2){
	    spacing = arrows[0][0][0].root().dist(arrows[1][0][0].root());
	}
	if(ynum>=2){
	    double ydist = arrows[0][0][0].root().dist(arrows[0][1][0].root());
	    if(spacing<0 || ydist < spacing) spacing=ydist; 
	}
	if(znum>=2){
	    double zdist = arrows[0][0][0].root().dist(arrows[0][0][1].root());
	    if(spacing<0 || zdist < spacing) spacing=zdist; 
	}
	if(spacing<0){ spacing = 10; } // default for 1 x 1 x 1 grid
    }
    
    public IVec gridPos(int i, int j, int k){
	IVec dif = max.dif(min);
	if(xnum>1) dif.x *= (double)i/(xnum-1); else dif.x=0;
	if(ynum>1) dif.y *= (double)j/(ynum-1); else dif.y=0;
	if(znum>1) dif.z *= (double)k/(znum-1); else dif.z=0; 
	return dif.add(min);
    }
    
    synchronized public void interact(ArrayList<IDynamics> agents){
	
	if(!fieldSet || updateAlways){
	    
	    // reset
	    for(int i=0; i<vectors.length; i++){
		for(int j=0; j<vectors[i].length; j++){
		    for(int k=0; k<vectors[i][j].length; k++){
			vectors[i][j][k].zero();
		    }
		}
	    }
	    
	    for(int a=0; a<agents.size(); a++){
		if(agents.get(a) instanceof I3DFieldI){
		    I3DFieldI field = (I3DFieldI)agents.get(a);
		    for(int i=0; i<vectors.length; i++){
			for(int j=0; j<vectors[i].length; j++){
			    for(int k=0; k<vectors[i][j].length; k++){
				// add field force
				vectors[i][j][k].add(field.get(arrows[i][j][k].root()));
			    }
			}
		    }
		}
		else if(agents.get(a) instanceof I2DFieldI){ // not clean way to do this. I2DField.applyField is issue.
		    I2DFieldI field = (I2DFieldI)agents.get(a);
		    
		    for(int i=0; i<vectors.length; i++){
			for(int j=0; j<vectors[i].length; j++){
			    for(int k=0; k<vectors[i][j].length; k++){
				// add field force
				vectors[i][j][k].add(field.get(arrows[i][j][k].root()).to3d());
			    }
			}
		    }
		}
	    }
	    
	    if(autoColorAdjust || autoLengthAdjust){
		for(int i=0; i<vectors.length; i++){
		    for(int j=0; j<vectors[i].length; j++){
			for(int k=0; k<vectors[i][j].length; k++){
			    double len = vectors[i][j][k].len();
			    if(i==0&&j==0&&k==0){ minLength = maxLength = len; }
			    else{
				if(len<minLength) minLength = len;
				if(len>maxLength) maxLength = len;
			    }
			}
		    }
		}
	    }
	    
	    for(int i=0; i<arrows.length; i++){
		for(int j=0; j<arrows[i].length; j++){
		    for(int k=0; k<arrows[i][j].length; k++){
			
			// minLength/maxLength is for the original length, not scaled one.
			if(minColor != null && maxColor != null){
			    double len = vectors[i][j][k].len();
			    if(IG.eq(minLength,maxLength)){ arrows[i][j][k].clr(maxColor); }
			    else if(len<=minLength) arrows[i][j][k].clr(minColor);
			    else if(len>=maxLength) arrows[i][j][k].clr(maxColor);
			    else{
				double r = (len-minLength)/(maxLength-minLength);
				int red = (int)((maxColor.getRed()-minColor.getRed())*r+minColor.getRed());
				int green = (int)((maxColor.getGreen()-minColor.getGreen())*r+minColor.getGreen());
				int blue = (int)((maxColor.getBlue()-minColor.getBlue())*r+minColor.getBlue());
				int alp = (int)((maxColor.getAlpha()-minColor.getAlpha())*r+minColor.getAlpha());
				arrows[i][j][k].clr(red,green,blue,alp);
			    }
			}
			if(vectors[i][j][k].len() > IConfig.tolerance){
			    
			    if(fixLength){
				if(autoLengthAdjust){ vectors[i][j][k].len(spacing*scale); }
				else{ vectors[i][j][k].len(fixedLength); }
			    }
			    else{
				if(autoLengthAdjust){
				    double r = 1.0;
				    if(!IG.eq(minLength,maxLength)){
					double len = vectors[i][j][k].len();
					r = (len-minLength)/(maxLength-minLength);
				    }
				    r*=scale;
				    if(r<minAutoLengthRatio){ r = minAutoLengthRatio; }
				    vectors[i][j][k].len(r*spacing);
				}
				else{ vectors[i][j][k].mul(scale); }
			    }
			    if(autoSizeAdjust){
				arrows[i][j][k].size(vectors[i][j][k].len()*arrowSizeRatio);
			    }
			    arrows[i][j][k].vector().set(vectors[i][j][k]);
			    arrows[i][j][k].updateGraphic();
			    
			    if(!arrows[i][j][k].visible()) arrows[i][j][k].show();
			}
			else{
			    arrows[i][j][k].hide();
			}
		    }
		}
	    }
	    
	    fieldSet=true;
	    
	}
	
    }
    
    
    public IFieldVisualizer updateFiled(){ fieldSet=false; return this; }
    
    
    /***************************************
     * IParticleI implementation; mostly ignored.
     **************************************/
    /*
    public double mass(){ return 0; }
    public IParticleI mass(double m){ return this; }
    public IVec position(){ return pos(); }
    public IVec pos(){ return null; }
    public IParticleI position(IVecI v){ return pos(v); }
    public IParticleI pos(IVecI v){ return this; }
    public IVec velocity(){ return vel(); }
    public IVec vel(){ return null; }
    public IParticleI velocity(IVecI v){ return vel(v); }
    public IParticleI vel(IVecI v){ return this; }
    public IVec acceleration(){ return acc(); }
    public IVec acc(){ return null; }
    public IVec force(){ return frc(); }
    public IVec frc(){ return null; }
    public IParticleI force(IVecI v){ return frc(v); }
    public IParticleI frc(IVecI v){ return this; }
    public double friction(){ return fric(); }
    public double fric(){ return 0; }
    public IParticleI friction(double f){ return fric(f); }
    public IParticleI fric(double f){ return this; }
    public double decay(){ return fric(); }
    public IParticleI decay(double d){ return fric(d); }
        
    // these methods are used for visualization
    public IParticleI push(IVecI f);
    public IParticleI push(double fx, double fy, double fz);
    public IParticleI pull(IVecI f);
    public IParticleI pull(double fx, double fy, double fz);
    public IParticleI addForce(IVecI f);
    public IParticleI addForce(double fx, double fy, double fz);
    public IParticleI reset();
    public IParticleI resetForce();
    
    
    public IParticleI fix(){ return this; }
    public IParticleI unfix(){ return this; }
    public boolean fixed(){ return false; }
    
    public IParticleI skipUpdateOnce(boolean f){ return this; }
    public boolean skipUpdateOnce(){ return false; }
    
    public IParticleI target(IObject targetObj){ return this; }
    public int targetNum(){ return 0; }
    public IObject target(int i){ return null; }
    public ArrayList<IObject> targets(){ return null; }
    public IParticleI  removeTarget(int i){ return this; }
    public IParticleI  removeTarget(IObject obj){ return this; }
    public void updateTarget(){ }
    */

    /**************************************
     * methods of IObject
     *************************************/
    
    public IFieldVisualizer name(String nm){ super.name(nm); return this; }
    public IFieldVisualizer layer(ILayer l){ super.layer(l); return this; }
    
    public IFieldVisualizer hide(){ super.hide(); return this; }
    public IFieldVisualizer show(){ super.show(); return this; }
    
    
}
