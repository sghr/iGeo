/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

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

package igeo.geo;

import java.util.ArrayList;

import igeo.core.*;
import igeo.util.*;

/**
   Class of an agent based on one point.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IPointAgent extends IAgent{
    
    public IVec pos;
    public IPoint point;
    
    public IPointAgent(){ this(new IVec()); show(); }
    public IPointAgent(double x, double y, double z){ super(); pos=new IVec(x,y,z); show(); }
    public IPointAgent(IVec p){ super(); pos=p; show(); }
    public IPointAgent(IVecI p){ super(); pos=p.get(); show(); }
    
    public IPointAgent show(){
	if(point==null){ point = new IPoint(pos).clr(super.clr()); }
	else{ point.show(); }
	return this;
    }
    
    public IPointAgent hide(){ if(point!=null) hide(); return this; }
    
}
