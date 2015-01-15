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
   2D vector filed defined by a map
   
   @author Satoru Sugihara
*/

public class I2DMapField extends I2DField{
    
    public I2DMapField(IMap map, IVec corner, double width, double height){
	super(new I2DMapFieldGeo(map,corner,width,height));
    }
    
    public I2DMapField(IMap map, double left, double bottom, double right, double top){
	super(new I2DMapFieldGeo(map,new IVec(left,bottom,0),right-left, top-bottom));
    }
    
    public I2DMapField(String imageFile, IVec corner, double width, double height){
	super(new I2DMapFieldGeo(new IImageMap(imageFile),corner,width,height));
    }
    
    public I2DMapField(String imageFile, double left, double bottom, double right, double top){
	super(new I2DMapFieldGeo(new IImageMap(imageFile),
				 new IVec(left,bottom,0), right-left, top-bottom));
    }
    
    public I2DMapField noDecay(){ super.noDecay(); return this; }
    public I2DMapField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public I2DMapField linear(double threshold){ super.linear(threshold); return this; }
    public I2DMapField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public I2DMapField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public I2DMapField gauss(double threshold){ super.gauss(threshold); return this; }
    public I2DMapField decay(IDecay decay, double threshold){ super.decay(decay,threshold); return this; }
    public I2DMapField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public I2DMapField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public I2DMapField threshold(double t){ super.threshold(t); return this; }
    public I2DMapField intensity(double i){ super.intensity(i); return this; }
}
