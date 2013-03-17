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
   A subclass of IMap containing 2D array of double to implement a map.
   
   @author Satoru Sugihara
*/

public class IDoubleMap extends IMap{
    public boolean interpolation=true;
    public int width, height;
    public double[][] map;
    
    public IDoubleMap(){}
    public IDoubleMap(int width, int height){ initMap(width,height); }
    
    public void initMap(int width, int height){
	this.width=width; this.height=height;
	map = new double[width][height];
    }
    public int width(){ return width; }
    public int getWidth(){ return width(); }
    public int height(){ return height; }
    public int getHeight(){ return height(); }
    public void set(int u, int v, double val){ map[u][v]=val; }
    public void setInterpolation(boolean f){ interpolation=f; }
    public void enableInterpolation(){ interpolation=true; }
    public void disableInterpolation(){ interpolation=false; }
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
