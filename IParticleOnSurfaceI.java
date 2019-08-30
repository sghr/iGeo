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
   Interface of a particle who sits and moves on a surface
   @author Satoru Sugihara
*/
public interface IParticleOnSurfaceI extends IParticleI{
    
    public ISurfaceI surface();
    
    public IParticleOnSurfaceI uv(double u, double v);
    public IVec2 uv();
    public IParticleOnSurfaceI uvvel(double uvel, double vvel);
    public IVec2 uvvel();
    public IParticleOnSurfaceI uvfrc(double ufrc, double vfrc);
    public IVec2 uvfrc();
        
    public IParticleOnSurfaceI addUVForce(double ufrc, double vfrc);
    public IParticleOnSurfaceI resetUVForce();
    
    public IParticleOnSurfaceI uvpush(double ufrc, double vfrc);
    public IParticleOnSurfaceI uvpull(double ufrc, double vfrf);
    public IParticleOnSurfaceI uvreset();

    public IParticleOnSurfaceI fixU();
    public IParticleOnSurfaceI fixV();
    public IParticleOnSurfaceI unfixU();
    public IParticleOnSurfaceI unfixV();
    public boolean uFixed();
    public boolean vFixed();

}
