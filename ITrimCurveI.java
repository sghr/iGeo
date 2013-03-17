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
   Interface of trim curve of surface.
   A trim curve is either outer trim curve (outside edge) or inner trim curve (edge of hole). 
   
   @author Satoru Sugihara
*/
public interface ITrimCurveI extends ICurveI{
    
    /** set surface
     */
    public ITrimCurveI surface(ISurfaceI srf);
    /** get surface
     */
    public ISurfaceI surface();

    
    public ITrimCurve get();
    
    /** get trim curve in 3d space mapped via the surface */
    public ICurveGeo get3d();
    
    // get3() ?
    
    /** it returns uv coordinates. */
    public IVec2I pt2(double u);
    /** alias */ // should this be deleted?
    public IVec2I pt2d(double u);
    
    /** it returns uv coordinates. */
    public IVec2I pt2(IDoubleI u);
    /** alias */ // should this be deleted?
    public IVec2I pt2d(IDoubleI u);
    
    public IVec2I start2();
    /** alias */ // should this be deleted?
    public IVec2I start2d();
    
    public IVec2I end2();
    /** alias */ // should this be deleted?
    public IVec2I end2d();
    
    public IVec2I startCP2();
    /** alias */ // should this be deleted?
    public IVec2I startCP2d();
    
    public IVec2I endCP2();
    /** alias */ // should this be deleted?
    public IVec2I endCP2d();
    
    public ITrimCurveI rev();
    
}
