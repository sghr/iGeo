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
   A subclass of IMap defined by accumulated curvature of a surface
   
   @author Satoru Sugihara
*/
public class ISurfaceDensityMap extends IMap{
    public ISurfaceI surface;
    public IVec orig, uvec, vvec;
    
    /**
       @param surf surface should be rectangle
    */
    public ISurfaceDensityMap(ISurfaceI surf){
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


    
