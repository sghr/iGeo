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
   Class of an implementation of IDynamicObject to have physical attributes of point.
   It has attributes of position, velocity, acceleration, force, and mass.
   Position is provided from outside to be linked.
   
   @author Satoru Sugihara
*/
public interface IParticleOnCurveI extends IParticleI{
    
    public ICurveI curve();
    
    public IParticleOnCurveI uposition(double u);
    public IParticleOnCurveI upos(double u);
    public double uposition();
    public double upos();
    public IParticleOnCurveI uvelocity(double uv);
    public IParticleOnCurveI uvel(double uv);
    public double uvelocity();
    public double uvel();
    public IParticleOnCurveI uforce(double uf);
    public IParticleOnCurveI ufrc(double uf);
    public double uforce();
    public double ufrc();
    
    public IParticleOnCurveI addUForce(double uforce);
    public IParticleOnCurveI resetUForce();
    
    public IParticleOnCurveI upush(double uforce);
    public IParticleOnCurveI upull(double uforce);
    public IParticleOnCurveI ureset();
    
}
