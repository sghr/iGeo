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

import igeo.gui.*;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;

/**
   A base class of map classes to provide mapping function to set values on a 2D map and extract value out of the map. The value contained on the map is double in the range of 0.0 - 1.0.
   
   @author Satoru Sugihara
*/
public class IMap{
    public static int defaultDensityWidth=100;
    public static int defaultDensityHeight=100;

    public static int defaultExportWidth=256;
    public static int defaultExportHeight=256;
    
    public static final double densityMinDelta = 1.0/10000;
    
    public IDoubleMap uIntegration=null, vIntegration=null;
    
    
    /**
       A main method to get a value of the map.
       This method should be overwritten by child classes to have preferable behavior
       returning value of the map ranging 0.0 - 1.0.
       @param u u coordinates ranges 0.0 - 1.0
       @param v v coordinates ranges 0.0 - 1.0
       @return value of the map.
    */
    public double get(double u, double v){ return 0.0; }
    
    public double get(IVec2I v){ return get(v.x(),v.y()); }
    
    public void initDensityMapU(){ initDensityMapU(defaultDensityWidth, defaultDensityHeight); }
    public void initDensityMapV(){ initDensityMapV(defaultDensityWidth, defaultDensityHeight); }
    
    public void initDensityMapU(int width, int height){
	uIntegration = new IDoubleMap(width+1,height);
	
	for(int j=0; j<height; j++){
	    double sum = 0;
	    uIntegration.set(0,j,sum);
	    for(int i=0; i<width; i++){
		sum += get((double)i/(width-1),(double)j/(height-1));
		uIntegration.set(i+1,j,sum);
	    }
	}
	// normalize
	for(int j=0; j<height; j++){
	    double max = uIntegration.map[width][j];
	    for(int i=1; i<=width; i++){
		if(max>0) uIntegration.map[i][j]/=max;
		else uIntegration.map[i][j]/=(double)i/width;
	    }
	}
    }
    public void initDensityMapV(int width, int height){
	vIntegration = new IDoubleMap(width,height+1);
	
	for(int i=0; i<width; i++){
	    double sum = 0;
	    vIntegration.set(i,0,sum);
	    for(int j=0; j<height; j++){
		sum += get((double)i/(width-1),(double)j/(height-1));
		vIntegration.set(i,j+1,sum);
	    }
	}
	
	// normalize
	for(int i=0; i<width; i++){
	    double max = vIntegration.map[i][height];
	    for(int j=1; j<=height; j++){
		if(max>0) vIntegration.map[i][j]/=max;
		else vIntegration.map[i][j]/=(double)j/height;
	    }
	}
    }
    
    public void scaleDensityMapU(double factor){ uIntegration.scale(factor); }
    public void scaleDensityMapV(double factor){ vIntegration.scale(factor); }
    
    public void matchUDensityWithMap(IMap map, double u, double v){
	matchUDensityWithMap(map,u,u+densityMinDelta,v);
    }
    public void matchUDensityWithMap(IMap map, double u1, double u2, double v){
	double upos11 = this.projectU(u1,v);
	double upos12 = this.projectU(u2,v);
	double upos21 = map.projectU(u1,v);
	double upos22 = map.projectU(u2,v);
	this.scaleDensityMapU((upos22-upos21)/(upos12-upos11));
    }
    
    public void matchVDensityWithMap(IMap map, double u, double v){
	matchVDensityWithMap(map,u,v,v+densityMinDelta);
    }
    public void matchVDensityWithMap(IMap map, double u, double v1, double v2){
	double vpos11 = this.projectU(u,v1);
	double vpos12 = this.projectU(u,v2);
	double vpos21 = map.projectU(u,v1);
	double vpos22 = map.projectU(u,v2);
	this.scaleDensityMapV((vpos22-vpos21)/(vpos12-vpos11));
    }
    
    
    
    public void saveAsJPEG(String filePath){
	filePath = IG.cur().formatOutputFilePath(filePath);
	IOut.debug(0,"saving jpg file: "+filePath);
	BufferedImage img = createImage();
        try{ ImageIO.write(img,"jpeg",new File(filePath)); }
        catch(IOException ioe){ ioe.printStackTrace(); }
    }
    
    public void saveAsJPEG(String filePath, int imgWidth, int imgHeight){
	filePath = IG.cur().formatOutputFilePath(filePath);
	IOut.debug(0,"saving jpg file: "+filePath);
	BufferedImage img = createImage(imgWidth,imgHeight);
        try{ ImageIO.write(img,"jpeg",new File(filePath)); }
        catch(IOException ioe){ ioe.printStackTrace(); }
    }
    
    public void saveAsPNG(String filePath){
	filePath = IG.cur().formatOutputFilePath(filePath);
	IOut.debug(0,"saving png file: "+filePath);
	BufferedImage img = createImage();
        try{ ImageIO.write(img,"png",new File(filePath)); }
        catch(IOException ioe){ ioe.printStackTrace(); }
    }
    
    public void saveAsPNG(String filePath, int imgWidth, int imgHeight){
	filePath = IG.cur().formatOutputFilePath(filePath);
	IOut.debug(0,"saving png file: "+filePath);
	BufferedImage img = createImage(imgWidth,imgHeight);
        try{ ImageIO.write(img,"png",new File(filePath)); }
        catch(IOException ioe){ ioe.printStackTrace(); }
    }
    
    
    
    public BufferedImage createImage(){
	int w = defaultExportWidth;
	int h = defaultExportHeight;
	if(this instanceof IDoubleMap){
	    IDoubleMap dmap = (IDoubleMap)this;
	    w = dmap.width;
	    h = dmap.height;
	}
	return createImage(w,h);
    }
    
    public BufferedImage createImage(int imgWidth, int imgHeight){
	BufferedImage bi = new BufferedImage(imgWidth,imgHeight,BufferedImage.TYPE_INT_RGB);
	for(int i=0; i<imgWidth; i++){
	    for(int j=0; j<imgHeight; j++){
		double val = get( (double)i/(imgWidth-1),  (double)j/(imgHeight-1) );
		int grayval = (int)(val*255);
		if(grayval<0) grayval=0; else if(grayval>255) grayval=255;
		int rgb = grayval*256*256+grayval*256+grayval;
		bi.setRGB(i,j,rgb);
	    }
	}
	return bi;
    }
    
    
    /**
       @param u range 0-1
       @param v range 0-1
    */
    public double projectU(double u, double v){
	if(uIntegration==null) initDensityMapU();
	return uIntegration.get(u,v);
    }
    
    /**
       @param u range 0-1
       @param v range 0-1
    */
    public double projectV(double u, double v){
	if(vIntegration==null) initDensityMapV();
	return vIntegration.get(u,v);
    }
    
    /**
       @param u range 0-1
       @param v range 0-1
    */
    public IVec2 project(double u, double v){
	if(uIntegration==null) initDensityMapU();
	if(vIntegration==null) initDensityMapV();
	return new IVec2(uIntegration.get(u,v), vIntegration.get(u,v));
    }
    
    
    /**
       to be defined in sub class
    */
    public void flipU(){}
    /**
       to be defined in sub class
    */
    public void flipV(){}
    
    
    /**
       A subclass of IMap containing 2D array of double to implement a map.
    */
    /*
    public static class DoubleMap extends IMap{
	public boolean interpolation=true;
	public int width, height;
	public double[][] map;
	public DoubleMap(){}
	public DoubleMap(int width, int height){ initMap(width,height); }
	public void initMap(int width, int height){
	    this.width=width; this.height=height;
	    map = new double[width][height];
	}
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public void set(int u, int v, double val){ map[u][v]=val; }
	public void setInterpolation(boolean f){ interpolation=f; }
	public double get(double u, double v){
	    if(interpolation){
		int ui = (int)((width-1)*u);
		int vi = (int)((height-1)*v);
		
		if(ui<0) ui=0; else if(ui>=width) ui=width-1;
		if(vi<0) vi=0; else if(vi>=height) vi=height-1;
		
		double ur = (double)ui/(width-1) - u;
		double vr = (double)vi/(height-1) - v;
		
		if(ur==0 || ui==width-1){
		    if(vr==0 || vi==height-1) return map[ui][vi];
		    return map[ui][vi]*(1-vr) + map[ui][vi+1]*vr;
		}
		
		if(vr==0 || vi==height-1) return map[ui][vi]*(1-ur) + map[ui+1][vi]*ur;
		
		return (map[ui][vi]*(1-ur) + map[ui+1][vi]*ur)*(1-vr) +
		    (map[ui][vi+1]*(1-ur) + map[ui+1][vi+1]*ur)*vr;
	    }
	    int ui = (int)((width-1)*u+0.5);
	    int vi = (int)((height-1)*v+0.5);
	    if(ui<0) ui=0; else if(ui>=width) ui=width-1;
	    if(vi<0) vi=0; else if(vi>=height) vi=height-1;
	    return map[ui][vi];
	}
	
	public void flipU(){
	    double[][] fmap = new double[width][height];
	    for(int i=0; i<width; i++){
		for(int j=0; j<height; j++){
		    fmap[i][j] = map[width-i-1][j];
		}
	    }
	    map = fmap;
	}
	
	public void flipV(){
	    double[][] fmap = new double[width][height];
	    for(int i=0; i<width; i++){
		for(int j=0; j<height; j++){
		    fmap[i][j] = map[i][height-j-1];
		}
	    }
	    map = fmap;
	}
	
	public void scale(double factor){
	    for(int i=0; i<width; i++) for(int j=0; j<height; j++) map[i][j] *= factor;
	}
	
	public void add(double val){
	    for(int i=0; i<width; i++) for(int j=0; j<height; j++) map[i][j] += val;
	}
    }
    */
    
    /**
       A subclass of IMap defined by two value to generate gradient map in u direction.
    */
    /*
    public static class ULinearMap extends IMap{
	public double uval1, uval2;
	public ULinearMap(double u1, double u2){ uval1 = u1; uval2 = u2; }
	public double get(double u, double v){ return (uval2-uval1)*u+uval1; }	
	public void flipU(){
	    double tmp = uval1; uval1 = uval2; uval2 = tmp;
	}
    }
    */
    /**
       A subclass of IMap defined by two value to generate gradient map in v direction.
    */
    /*
    public static class VLinearMap extends IMap{
	public double vval1, vval2;
	public VLinearMap(double v1, double v2){ vval1 = v1; vval2 = v2; }
	public double get(double u, double v){ return (vval2-vval1)*v+vval1; }	
	public void flipV(){
	    double tmp = vval1; vval1 = vval2; vval2 = tmp;
	}
    }
    */
    /**
       A subclass of IMap defined by multiple u domain and multiple values.
       A value on the map is calculated by interpolation of assigned values.
    */
    /*
    public static class UPiecewiseLinearMap extends IMap{
	public ArrayList<Double> domains;
	public ArrayList<Double> values;
	public UPiecewiseLinearMap(){
	    domains = new ArrayList<Double>();
	    values = new ArrayList<Double>();
	}
	public void addValue(double domain, double value){
	    domains.add(domain);
	    values.add(value);
	}
	public double get(double u, double v){ return get(u); }
	public double get(double x){
	    if(domains.size()==0) return 0;
	    
	    if(x <= domains.get(0)) return values.get(0);
	    
	    for(int i=0; i<domains.size()-1; i++){
		//if(i==0 && x <= domains.get(i)) return values.get(i);
		if(x>=domains.get(i) && x<domains.get(i+1))
		    return (values.get(i+1)-values.get(i))*
			(x-domains.get(i))/(domains.get(i+1)-domains.get(i)) +
			values.get(i);
		//if(i==domains.size()-2 && x>=domains.get(i+1)) return values.get(i+1);
	    }
	    
	    return values.get(values.size()-1);
	    //return 0;
	}
	public void flipU(){
	    ArrayList<Double> newdomains = new ArrayList<Double>();
	    ArrayList<Double> newvalues = new ArrayList<Double>();
	    for(int i=domains.size()-1; i>=0; i--){
		newdomains.add(1.0-domains.get(i));
		newvalues.add(values.get(i));
	    }
	    domains = newdomains;
	    values = newvalues;
	}
    }
    */
    /**
       A subclass of IMap defined by multiple v domain and multiple values.
       A value on the map is calculated by interpolation of assigned values.
    */
    /*
    public static class VPiecewiseLinearMap extends IMap{
	public ArrayList<Double> domains;
	public ArrayList<Double> values;
	public VPiecewiseLinearMap(){
	    domains = new ArrayList<Double>();
	    values = new ArrayList<Double>();
	}
	public void addValue(double domain, double value){
	    domains.add(domain);
	    values.add(value);
	}
	public double get(double u, double v){ return get(v); }
	public double get(double x){
	    if(domains.size()==0) return 0;
	    
	    if(x <= domains.get(0)) return values.get(0);
	    
	    for(int i=0; i<domains.size()-1; i++){
		//if(i==0 && x <= domains.get(i)) return values.get(i);
		if(x>=domains.get(i) && x<domains.get(i+1))
		    return (values.get(i+1)-values.get(i))*
			(x-domains.get(i))/(domains.get(i+1)-domains.get(i)) +
			values.get(i);
		//if(i==domains.size()-2 && x>=domains.get(i+1)) return values.get(i+1);
	    }
	    
	    return values.get(values.size()-1);
	    //return 0;
	}
	public void flipV(){
	    ArrayList<Double> newdomains = new ArrayList<Double>();
	    ArrayList<Double> newvalues = new ArrayList<Double>();
	    for(int i=domains.size()-1; i>=0; i--){
		newdomains.add(1.0-domains.get(i));
		newvalues.add(values.get(i));
	    }
	    domains = newdomains;
	    values = newvalues;
	}
    }
    */
    
    /**
       A subclass of IMap defined by sine curve in u direction.
    */
    /*
    public static class USineMap extends IMap{
	public double minValue, maxValue;
	public double frequency;
	public double phase;
	
	public USineMap(double minamp, double maxamp, double freq, double phs){
	    minValue = minamp;
	    maxValue = maxamp;
	    frequency = freq;
	    phase = phs;
	}
	
	public double get(double u, double v){
	    return get(u);
	}
	public double get(double x){
	    return (Math.sin( (x-phase)*2*Math.PI*frequency )+1)/2*(maxValue-minValue)+minValue;
	}
    }
    */
    
    /**
       A subclass of IMap defined by sine curve in v direction.
    */
    /*
    public static class VSineMap extends IMap{
	public double minValue, maxValue;
	public double frequency;
	public double phase;
	
	public VSineMap(double minamp, double maxamp, double freq, double phs){
	    minValue = minamp;
	    maxValue = maxamp;
	    frequency = freq;
	    phase = phs;
	}
	
	public double get(double u, double v){ return get(v); }
	public double get(double x){
	    return (Math.sin( (x-phase)*frequency*2*Math.PI)+1)/2*(maxValue-minValue)+minValue;
	}
    }
    */
    
    /**
       A subclass of IMap defined by a bitmap image.
    */
    /*
    public static class ImageMap extends DoubleMap{
	public Image image;
	
	public ImageMap(String imgFile){ initMap(imgFile); }
	public ImageMap(Image img){ initMap(img); }
	public ImageMap(String imgFile, Component mediaComponent){ initMap(imgFile, mediaComponent); }
	
	public void initMap(String imageFile){
	    initMap(IImageLoader.getImage(imageFile));
	}
	public void initMap(String imageFile, Component comp){
	    initMap(IImageLoader.getImage(imageFile, comp));
	}
	public void initMap(Image mapImage){
	    image = mapImage;
	    width = mapImage.getWidth(IImageLoader.observer);
	    height = mapImage.getHeight(IImageLoader.observer);
	    
	    int[] pix = new int[width*height];
	    
	    PixelGrabber pg = new PixelGrabber(mapImage, 0, 0, -1, -1, pix, 0, width);
	    if(pg!=null) try{ pg.grabPixels(); } catch(Exception e){ e.printStackTrace(); }
	    
	    super.initMap(width,height);
	    
	    for(int i=0; i<height; i++)
		for(int j=0; j<width; j++)
		    super.set(j, i, getColorValue(pix, j, i, width));
	}
	
	static public double getColorValue(int[] pixel, int x, int y, int w){
	    int[] color =new int[4];
	    int aRGB = pixel[ ( w * ( y  )) + x ] ;
	    color[0] = ( aRGB >> 24 ) & 0xff ;
	    color[1] = ( aRGB >> 16 ) & 0xff ;
	    color[2] = ( aRGB >> 8 ) & 0xff ;
	    color[3] = ( aRGB ) & 0xff ;
	    
	    // RGBA?
	    // ARGB?
	    
	    // RGBA
	    //return (double)(color[0]+color[1]+color[2])/(3*255)*((double)color[3]/255);
	    // ARGB
	    return (double)(color[1]+color[2]+color[3])/(3*255)*((double)color[0]/255);
	}
	
	public void initDensityMapU(){ initDensityMapU(this.width, this.height); }
	public void initDensityMapV(){ initDensityMapV(this.width, this.height); }
    }
    */
    
    /**
       A subclass of IMap defined by accumulated curvature of a surface
    */
    /*
    public static class SurfaceDensityMap extends IMap{
	public ISurfaceI surface;
	public IVec orig, uvec, vvec;
	
	public SurfaceDensityMap(ISurfaceI surf){
	    surface=surf;
	    initMap();
	}
	
	public void initMap(){
	    orig = surface.corner(0,0).get();
	    uvec = surface.corner(1,0).diff(orig).get();
	    vvec = surface.corner(0,1).diff(orig).get();
	}
	
	public void initDensityMapU(int width, int height){ } // do nothing
	
	public double projectU(double u, double v){
	    IVec pt = surface.pt(u,v).get();
	    pt.sub(orig);
	    double[] coeff = pt.projectTo2Vec(uvec,vvec);
	    return coeff[0];
	}
	
	public double projectV(double u, double v){
	    IVec pt = surface.pt(u,v).get();
	    pt.sub(orig);
	    double[] coeff = pt.projectTo2Vec(uvec,vvec);
	    return coeff[1];
	}
	
	public IVec2 project(double u, double v){
	    IVec pt = surface.pt(u,v).get();
	    pt.sub(orig);
	    double[] coeff = pt.projectTo2Vec(uvec,vvec);
	    return new IVec2(coeff[0],coeff[1]);
	}
	
    }
    */
    
    /**
       A subclass of IMap defined by depth of surface in the assigned depth direction.
    */
    /*
    public static class SurfaceDepthMap extends IMap{
	public ISurfaceI surface;
	public IVec orig, uvec, vvec;
	//double minDepth,maxDepth;
	
	// maxDepthDir is a normal vector
	public IVec minDepthPt=null, depthDir=null;
	public double maxDepth=0;
	
	// @param surf surface should be rectangle
	public SurfaceDepthMap(ISurfaceI surf){
	    surface=surf;
	    initMap();
	}
	
	public SurfaceDepthMap(ISurfaceI surf, IVec depthDir){
	    surface=surf;
	    this.depthDir = depthDir;
	    initMap();
	}
	
	public SurfaceDepthMap(ISurfaceI surf, IVec depthDir, IVec minPt, IVec maxPt){
	    surface=surf;
	    this.depthDir = depthDir.dup().unit();
	    this.minDepthPt = minPt;
	    this.maxDepth = maxPt.diff(minPt).dot(this.depthDir);
	}
	
	public SurfaceDepthMap(ISurfaceI surf, ICurveI measureLine){
	    surface=surf;
	    
	    minDepthPt = measureLine.start().get();
	    depthDir = measureLine.end().get().diff(minDepthPt);
	    maxDepth = depthDir.len();
	    depthDir.unit();
	    
	    initMap();
	}
	
	public void initMap(){
	    if(minDepthPt==null||depthDir==null){
		orig = surface.corner(0,0).get();
		uvec = surface.corner(1,0).get().diff(orig);
		vvec = surface.corner(0,1).get().diff(orig);
		
		minDepthPt = orig;
		if(depthDir==null) depthDir = uvec.cross(vvec).unit();
		
		double minz=0,maxz=0;
		// check limit by control points
		for(int i=0; i<surface.unum(); i++){
		    for(int j=0; j<surface.vnum(); j++){
			double depth = getDepth(surface.cp(i,j).get());
			if(i==0&&j==0) minz = maxz = depth;
			else{
			    if(depth<minz) minz=depth;
			    if(depth>maxz) maxz=depth;
			}
		    }
		}
		//minDepth=minz;
		//maxDepth=maxz;
		
		maxDepth = maxz-minz;
		if(minz<0) minDepthPt=depthDir.dup().len(minz).add(minDepthPt);
		
	    }
	}
	
	public double getDepth(double u, double v){
	    return getDepth(surface.pt(u,v).get());
	}
	
	public double getDepth(IVec pt){
	    IVec diff = pt.diff(minDepthPt);
	    return depthDir.dot(diff);
	}
	
	public double get(double u, double v){
	    IVec diff = surface.pt(u,u).get().diff(minDepthPt);
	    return depthDir.dot(diff)/maxDepth;
	}
    }
    */
    
    /**
       A subclass of IMap defined by z depth of surface. 
    */
    /*
    public static class SurfaceZDepthMap extends SurfaceDepthMap{
	public SurfaceZDepthMap(ISurfaceI surf, double minZ, double maxZ){
	    super(surf, new IVec(0,0,1), new IVec(0,0,minZ), new IVec(0,0,maxZ));
	}
	
	public SurfaceZDepthMap(ISurfaceI surf){
	    super(surf, new IVec(0,0,1), new IVec(0,0,0), new IVec(0,0,1));
	    
	    final int usampleNum=20;
	    final int vsampleNum=20;
	    double minz=0,maxz=0;
	    // check limit by control points
	    for(int i=0; i<usampleNum; i++){
		for(int j=0; j<vsampleNum; j++){
		    double z = surface.pt((double)i/usampleNum,(double)j/vsampleNum).z();
		    if(i==0&&j==0) minz = maxz = z;
		    else{
			if(z<minz) minz=z;
			if(z>maxz) maxz=z;
		    }
		}
	    }
	    minDepthPt.set(0,0,minz);
	    maxDepth = maxz-minz;
	}
    }
    */
    
    /**
       A subclass of IMap defined by another map and parameter shift in u direction in a stripe way in v direction
    */
    /*
    public static class UStripeShiftMap extends IMap{
	public ArrayList<Double> domains;
	public ArrayList<Double> shiftAmounts;
	
	public IMap origMap;
	public UStripeShiftMap(IMap originalMap){
	    origMap = originalMap;
	    domains = new ArrayList<Double>();
	    shiftAmounts = new ArrayList<Double>();
	}
	
	public UStripeShiftMap(IMap originalMap, double shift){
	    origMap = originalMap;
	    domains = new ArrayList<Double>();
	    shiftAmounts = new ArrayList<Double>();
	    addShift(0,shift);
	    addShift(1,shift);
	}
	
	public void addShift(double domain, double shiftAmount){
	    domains.add(domain);
	    shiftAmounts.add(shiftAmount);
	}
	
	public double get(double u, double v){
	    double ushift=getShift(v);
	    if(u+ushift<0) return origMap.get(0,v);
	    if(u+ushift>1) return origMap.get(1,v);
	    return origMap.get(u+ushift,v);
	}
	
	public double getShift(double x){
	    for(int i=0; i<domains.size()-1; i++){
		if(i==0 && x <= domains.get(i)) return 0;
		if(x>=domains.get(i) && x<domains.get(i+1))
		    return shiftAmounts.get(i);
		if(i==domains.size()-2 && x>=domains.get(i+1))
		    return shiftAmounts.get(i+1);
	    }
	    return 0;
	}
	
    }
    */
    
    /**
       A subclass of IMap defined by another map and parameter shift in v direction in a stripe way in u direction
    */
    /*
    public static class VStripeShiftMap extends IMap{
	public ArrayList<Double> domains;
	public ArrayList<Double> shiftAmounts;
	
	public IMap origMap;
	public VStripeShiftMap(IMap originalMap){
	    origMap = originalMap;
	    domains = new ArrayList<Double>();
	    shiftAmounts = new ArrayList<Double>();
	}
	
	public VStripeShiftMap(IMap originalMap, double shift){
	    origMap = originalMap;
	    domains = new ArrayList<Double>();
	    shiftAmounts = new ArrayList<Double>();
	    addShift(0,shift);
	    addShift(1,shift);
	}
	
	public void addShift(double domain, double shiftAmount){
	    domains.add(domain);
	    shiftAmounts.add(shiftAmount);
	}
	
	public double get(double u, double v){
	    double vshift=getShift(u);
	    if(v+vshift<0) return origMap.get(u,0);
	    if(v+vshift>1) return origMap.get(u,1);
	    return origMap.get(u,v+vshift);
	}
	
	public double getShift(double x){
	    for(int i=0; i<domains.size()-1; i++){
		if(i==0 && x <= domains.get(i)) return 0;
		if(x>=domains.get(i) && x<domains.get(i+1))
		    return shiftAmounts.get(i);
		if(i==domains.size()-2 && x>=domains.get(i+1))
		    return shiftAmounts.get(i+1);
	    }
	    return 0;
	}
	
    }
    */
    
    /**
       A subclass of IMap defined by addition of two maps.
       Output value is crampped with 0.0 - 1.0.
    */
    /*
    public static class AddedMap extends IMap{
	public IMap map1, map2;
	public AddedMap(IMap m1,IMap m2){ map1=m1; map2=m2; }
	public double get(double u, double v){
	    double val = map1.get(u,v) + map2.get(u,v);
	    if(val>1) return 1;
	    if(val<0) return 0;
	    return val;
	}
	public void flipU(){ map1.flipU(); map2.flipU(); } //is it ok to modify the original?
	public void flipV(){ map1.flipV(); map2.flipV(); } //is it ok to modify the original?
    }
    */
    
    /**
       A subclass of IMap defined by subtraction of two maps.
       Output value is crampped with 0.0 - 1.0.
    */
    /*
    public static class SubtractedMap extends IMap{
	public IMap map1, map2;
	public SubtractedMap(IMap m1,IMap m2){ map1=m1; map2=m2; }
	public double get(double u, double v){
	    double val = map1.get(u,v) - map2.get(u,v);
	    if(val>1) return 1;
	    if(val<0) return 0;
	    return val;
	}
	public void flipU(){ map1.flipU(); map2.flipU(); } //is it ok to modify the original?
	public void flipV(){ map1.flipV(); map2.flipV(); } //is it ok to modify the original?
    }
    */
    
    /**
       A subclass of IMap defined by multiplication of two maps.
       Output value is NOT crampped.
    */
    /*
    public static class MultipliedMap extends IMap{
	public IMap map1, map2;
	public MultipliedMap(IMap m1,IMap m2){ map1=m1; map2=m2; }
	public double get(double u, double v){ return map1.get(u,v)*map2.get(u,v); }
	public void flipU(){ map1.flipU(); map2.flipU(); } //is it ok to modify the original?
	public void flipV(){ map1.flipV(); map2.flipV(); } //is it ok to modify the original?
    }
    */
    
    /**
       A subclass of IMap defined by taking larger value of two maps.
    */
    /*
    public static class MaxMap extends IMap{
	public IMap map1, map2;
	public MaxMap(IMap m1,IMap m2){ map1=m1; map2=m2; }
	public double get(double u, double v){
	    double v1 = map1.get(u,v);
	    double v2 = map2.get(u,v);
	    if(v1>v2) return v1;
	    return v2;
	}
	public void flipU(){ map1.flipU(); map2.flipU(); } //is it ok to modify the original?
	public void flipV(){ map1.flipV(); map2.flipV(); } //is it ok to modify the original?
    }
    */
    
    /**
       A subclass of IMap defined by taking smaller value of two maps.
    */
    /*
    public static class MinMap extends IMap{
	public IMap map1, map2;
	public MinMap(IMap m1,IMap m2){ map1=m1; map2=m2; }
	public double get(double u, double v){
	    double v1 = map1.get(u,v);
	    double v2 = map2.get(u,v);
	    if(v1<v2) return v1;
	    return v2;
	}
	public void flipU(){ map1.flipU(); map2.flipU(); } //is it ok to modify the original?
	public void flipV(){ map1.flipV(); map2.flipV(); } //is it ok to modify the original?
    }
    */
    
    /**
       A subclass of IMap defined by inverting value from 0 to 1 and 1 to 0.
    */
    /*
    public static class InvertMap extends IMap{
	public IMap map;
	public InvertMap(IMap m){ map = m; }
	public double get(double u, double v){ return 1.0 - map.get(u,v); }
	public void flipU(){ map.flipU(); } //is it ok to modify the original?
	public void flipV(){ map.flipV(); } //is it ok to modify the original?
    }
    */
    
    /**
       A subclass of IMap defined by scaling another map.
       Output value is crampped with 0.0 - 1.0.
    */
    /*
    public static class ScaledMap extends IMap{
	public IMap map;
	public double scaleFactor;
	public ScaledMap(IMap m, double factor){ map = m; scaleFactor = factor; }
	public double get(double u, double v){ return map.get(u,v)*scaleFactor; }
	public void flipU(){ map.flipU(); } //is it ok to modify the original?
	public void flipV(){ map.flipV(); } //is it ok to modify the original?
    }
    */
    
    /**
       A subclass of IMap defined by flipping another map in u direction.
    */
    /*
    public static class UFlippedMap extends IMap{
	public IMap map;
	public UFlippedMap(IMap m){ map = m; }
	public double get(double u, double v){ return map.get(1-u,v); }
	public void flipU(){ map.flipU(); } //is it ok to modify the original?
	public void flipV(){ map.flipV(); } //is it ok to modify the original?
    }
    */
    
    /**
       A subclass of IMap defined by flipping another map in v direction.
    */
    /*
    public static class VFlippedMap extends IMap{
	public IMap map;
	public VFlippedMap(IMap m){ map = m; }
	public double get(double u, double v){ return map.get(u,1-v); }
	public void flipU(){ map.flipU(); } //is it ok to modify the original?
	public void flipV(){ map.flipV(); } //is it ok to modify the original?
    }
    */
    
    /**
       A subclass of IMap defined by extracting part of another map.
    */
    /*
    public static class SubMap extends IMap{
	public IMap map;
	public double u1,u2,v1,v2;
	public SubMap(IMap mp, double ustart, double uend, double vstart, double vend){
	    map=mp;
	    u1=ustart;
	    u2=uend;
	    v1=vstart;
	    v2=vend;
	}
	public double get(double u, double v){ return map.get((u2-u1)*u+u1,(v2-v1)*v+v1); }
    }
    */
    
    /**
       A subclass of IMap defined by one constant number
    */
    /*
    public static class ConstantMap extends IMap{
	public double value;
	public ConstantMap(double val){ value=val; }
	public double get(double u, double v){ return value; }
    }
    */
    
}
