/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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

import java.util.ArrayList;

/**
   Agent to iterate mesh faces.
   
   @author Satoru Sugihara
*/
public class IMeshAgent extends IAgent{
    
    public IFace curFace, prevFace;
    //public IEdge curEdge, prevEdge;
    //public IVertex curVertex, prevVertex;
    
    public IMeshAgent(IFace f){ curFace = f; }
    
    
    
    public IFace nextFace(){
	// random edge
	IEdge edge = curFace.edge(IRand.geti(0,curFace.edgeNum()-1));
	return edge.getOtherFace(curFace);
    }

    //debug
    IPoint centerPt;
    
    public void update(){
	IFace f = nextFace();
	
	if(centerPt!=null){ centerPt.del(); }
	
	prevFace = curFace;
	curFace = f;
	
	centerPt = new IPoint(curFace.getCenter()).clr(1.,0,0).size(5);
    }
    
}
