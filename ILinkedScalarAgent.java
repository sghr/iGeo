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
   Agent class with a generic data with links to other data agents
   
   @author Satoru Sugihara
*/
public class ILinkedScalarAgent extends ILinkedDataAgent<IDoubleI>{
    
    public ILinkedScalarAgent(){ super(); }
    public ILinkedScalarAgent(IDoubleI val){ super(val); }
    public ILinkedScalarAgent(IVecI pos){ super(pos); }
    public ILinkedScalarAgent(IVecI pos, IDoubleI val){ super(pos,val); }
   // public ILinkedScalarAgent(IObject parent){ super(parent); }
   // public ILinkedScalarAgent(IDoubleI val, IObject parent){ super(val,parent); }


    /**************************************
     * static method
     *************************************/
    
    public static ILinkedScalarAgent[] create(IMeshI m){
        ILinkedScalarAgent[] agents = new ILinkedScalarAgent[m.vertexNum()];
	for(int i=0; i<m.vertexNum(); i++){
	    agents[i] = new ILinkedScalarAgent(m.vertex(i).pos(), new IDouble());
	}
        for(int i=0; i<m.edgeNum(); i++){
            IVertex v1 = m.edge(i).vertex(0);
            IVertex v2 = m.edge(i).vertex(1);
            int idx1 = -1;
            int idx2 = -1;
            for(int j=0; j<m.vertexNum() && idx1<0; j++){
                 if(m.vertex(j)==v1) idx1 = j;
            }
            for(int j=0; j<m.vertexNum() && idx2<0; j++){
                if(m.vertex(j)==v2) idx2 = j;
            }
            if(idx1>=0 && idx2>=0){
                agents[idx1].connect(agents[idx2]);
                agents[idx2].connect(agents[idx1]);
            }
        }
        return agents;
    }

}
