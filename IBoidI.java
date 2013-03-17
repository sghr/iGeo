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
   Interface API of Boid.
   
   @author Satoru Sugihara
*/
public interface IBoidI extends IParticleI{
    
    public double cohDist();
    public double cohesionDist();
    public IBoidI cohDist(double dist);
    public IBoidI cohesionDist(double dist);
    public double cohRatio();
    public double cohesionRatio();
    public IBoidI cohRatio(double ratio);
    public IBoidI cohesionRatio(double ratio);
    public double cohLimit();
    public double cohesionLimit();
    public IBoidI cohLimit(double limit);
    public IBoidI cohesionLimit(double limit);
    
    public IBoidI coh(double ratio, double dist);
    public IBoidI cohesion(double ratio, double dist);
    
    public double sepDist();
    public double separationDist();
    public IBoidI sepDist(double dist);
    public IBoidI separationDist(double dist);
    public double sepRatio();
    public double separationRatio();
    public IBoidI sepRatio(double ratio);
    public IBoidI separationRatio(double ratio);
    public double sepLimit();
    public double separationLimit();
    public IBoidI sepLimit(double limit);
    public IBoidI separationLimit(double limit);

    public IBoidI sep(double ratio, double dist);
    public IBoidI separation(double ratio, double dist);
    
    public double aliDist();
    public double alignmentDist();
    public IBoidI aliDist(double dist);
    public IBoidI alignmentDist(double dist);
    public double aliRatio();
    public double alignmentRatio();
    public IBoidI aliRatio(double ratio);
    public IBoidI alignmentRatio(double ratio);
    public double aliLimit();
    public double alignmentLimit();
    public IBoidI aliLimit(double limit);
    public IBoidI alignmentLimit(double limit);
    
    public IBoidI ali(double ratio, double dist);
    public IBoidI alignment(double ratio, double dist);
    
    public IBoidI parameter(double cohRatio, double cohDist, double sepRatio, double sepDist, 
			    double aliRatio, double aliDist);
    public IBoidI param(double cohRatio, double cohDist, double sepRatio, double sepDist, double aliRatio, double aliDist);

    
}
