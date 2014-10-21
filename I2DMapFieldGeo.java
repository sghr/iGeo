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
   2D vector filed defined by a IMap
   
   @author Satoru Sugihara
*/

public class I2DMapFieldGeo extends IFieldGeo implements I2DFieldI{
    
    public IMap map;
    public IDoubleMap dmap; // cast from map
    public IVec corner;
    public double width,height;
    public int mapWidth, mapHeight;

    public int interpolationRange = 5;
    
    public I2DMapFieldGeo(IMap m, IVec cnr, double wid, double hei){
	map = m;
	corner = cnr;
	width = wid;
	height = hei;
	//linearDecay(0);

	if(map instanceof IDoubleMap){
	    dmap = (IDoubleMap)map;
	    mapWidth = dmap.getWidth();
	    mapHeight = dmap.getHeight();
	    
	    if(dmap instanceof IImageMap){
		dmap.flipV(); // upside down
	    }
	    
	}
	else{
	    mapWidth = IMap.defaultDensityWidth;
	    mapHeight = IMap.defaultDensityHeight;
	}
    }
    
    public double udif(int uidx, int vidx){
	if(dmap!=null){
	    return dmap.map[uidx+1][vidx] - dmap.map[uidx][vidx];
	}
	return map.get( (double)(uidx+1)/mapWidth, (double)vidx/mapHeight ) -
	    map.get( (double)uidx/mapWidth, (double)vidx/mapHeight );
    }
    
    public double vdif(int uidx, int vidx){
	if(dmap!=null){
	    return dmap.map[uidx+1][vidx] - dmap.map[uidx][vidx];
	}
	return map.get( (double)(uidx+1)/mapWidth, (double)vidx/mapHeight ) -
	    map.get( (double)uidx/mapWidth, (double)vidx/mapHeight );
    }

    public IVec2 dif(int uidx, int vidx){
	
	if(uidx<0) uidx=0; else if(uidx>=mapWidth-1) uidx=mapWidth-2;
	if(vidx<0) vidx=0; else if(vidx>=mapHeight-1) vidx=mapHeight-2;
	
	// interpolation of 4 cells
	double udif1=0, udif2=0, vdif1=0, vdif2=0;
	if(dmap!=null){
	    udif1 = dmap.map[uidx+1][vidx] - dmap.map[uidx][vidx];
	    udif2 = dmap.map[uidx+1][vidx+1] - dmap.map[uidx][vidx+1];
	    vdif1 = dmap.map[uidx][vidx+1] - dmap.map[uidx][vidx];
	    vdif2 = dmap.map[uidx+1][vidx+1] - dmap.map[uidx+1][vidx];
	}
	else{
	    udif1 = map.get( (double)(uidx+1)/mapWidth, (double)vidx/mapHeight ) -
		map.get( (double)uidx/mapWidth, (double)vidx/mapHeight );
	    udif2 = map.get( (double)(uidx+1)/mapWidth, (double)(vidx+1)/mapHeight ) -
		map.get( (double)uidx/mapWidth, (double)(vidx+1)/mapHeight );
	    vdif1 = map.get( (double)(uidx)/mapWidth, (double)(vidx+1)/mapHeight ) -
		map.get( (double)uidx/mapWidth, (double)(vidx)/mapHeight );
	    vdif2 = map.get( (double)(uidx+1)/mapWidth, (double)(vidx+1)/mapHeight ) -
		map.get( (double)(uidx+1)/mapWidth, (double)(vidx)/mapHeight );
	}

	return new IVec2((udif1+udif2)/2, (vdif1+vdif2)/2);
    }
    
    
    IVec2 interpolateDif(int uidx, int vidx){
	
	double weight=0;
	double u=0;
	double v=0;
	IVec2 vec = new IVec2();
	for(int i=uidx-interpolationRange; i<uidx+interpolationRange; i++){
	    for(int j=vidx-interpolationRange; j<vidx+interpolationRange; j++){
		vec.add(dif(i,j));
		weight += 1 - Math.abs(i-uidx)/(interpolationRange+1);
	    }
	}
	vec.div(weight);
	return vec;
    }
    
    
    /** get original field value out of surface parameter uv */
    public IVec2I get(IVecI pos, double u, double v){
	/*
	int uidx1 = (int)(u*mapWidth);
	int vidx1 = (int)(v*mapHeight);
	if(uidx1<0) uidx1=0; else if(uidx1>=mapWidth){ uidx1=mapWidth-1; }
	if(vidx1<0) vidx1=0; else if(vidx1>=mapHeight){ vidx1=mapHeight-1; }
	*/
	int uidx2 = (int)(u*mapWidth-0.5);
	int vidx2 = (int)(v*mapHeight-0.5);
	if(uidx2<0) uidx2=0; else if(uidx2>=mapWidth-1){ uidx2=mapWidth-2; }
	if(vidx2<0) vidx2=0; else if(vidx2>=mapHeight-1){ vidx2=mapHeight-2; }
		
	//double udif = udif(uidx2, vidx1);
	//double vdif = vdif(uidx1, vidx2);

	return interpolateDif(uidx2,vidx2);
	
	//return dif( uidx2, vidx2);
	
	/*
	double udif=0, vdif=0;
	if(dmap!=null){
	    udif = dmap.map[uidx2+1][vidx1] - dmap.map[uidx2][vidx1];
	    vdif = dmap.map[uidx1][vidx2+1] - dmap.map[uidx1][vidx2];
	}
	else{
	    udif =
		map.get( (double)(uidx2+1)/mapWidth, (double)vidx1/mapHeight ) -
		map.get( (double)uidx2/mapWidth, (double)vidx1/mapHeight );
	    vdif =
		map.get((double)uidx1/mapWidth, (double)(vidx2+1)/mapHeight) -
		map.get((double)uidx1/mapWidth, (double)vidx2/mapHeight);
	}
	*/
	//return new IVec2(udif, vdif);
    }
    
    /** get original field value out of surface parameter uv */
    public IVec2I get(IVecI pos, IVecI vel, double u, double v){
	return get(pos,u,v);
    }
    
    /** get 3D vector field value */
    public IVec2I get(IVecI v){ return get(v,(IVecI)null); }
    
    /** get 3D vector field value */
    public IVec2I get(IVecI pos, IVecI vel){
	
	double u = (pos.x()-corner.x)/width;
	double v = (pos.y()-corner.y)/height;
	
	if(u<0 || u>1 || v<0 || v>1){
	    return new IVec2(0,0); // no decay
	}
	
	double r = intensity;
	
	/*
	if(decay == Decay.Linear){
	    double dist = surface.pt(uv).to2d().dist(pos.to2d());
	    if(dist >= threshold &&
	       (uv.x<=0||uv.y<=0||uv.x>=1.0||uv.y>=1.0)) return new IVec2(); // zero
	    if(threshold>0) r *= (threshold-dist)/threshold;
	}
	else if(decay == Decay.Gaussian){
	    double dist = surface.pt(uv).to2d().dist(pos.to2d());
	    if(threshold>0) r *= Math.exp(-2*dist*dist/(threshold*threshold));
	}
	*/
	
	IVec2I vec = get(pos, vel, u, v);
	
	/*
	if(bidirectional && vec.get().dot(vel.to2d()) < 0){ r=-r; }
	if(constantIntensity){
	    double len = vec.len();
	    if(len<IConfig.tolerance){ return vec.zero(); }
	    return vec.len(r);
	}
	*/
	
	return vec.mul(r);
	
    }
    
    /*
    public I2DSurfaceFieldGeo constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    
    public I2DSurfaceFieldGeo bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public I2DSurfaceFieldGeo noDecay(){ super.noDecay(); return this; }
    public I2DSurfaceFieldGeo linearDecay(double threshold){
	super.linearDecay(threshold); return this;
    }
    public I2DSurfaceFieldGeo linear(double threshold){
	super.linear(threshold); return this;
    }
    public I2DSurfaceFieldGeo gaussianDecay(double threshold){
	super.gaussianDecay(threshold); return this;
    }
    public I2DSurfaceFieldGeo gaussian(double threshold){
	super.gaussian(threshold); return this;
    }
    public I2DSurfaceFieldGeo gauss(double threshold){ super.gauss(threshold); return this; }
    public I2DSurfaceFieldGeo threshold(double t){ super.threshold(t); return this; }
    */
    
    public I2DMapFieldGeo intensity(double i){ super.intensity(i); return this; }
    
    public void del(){
    }
}
